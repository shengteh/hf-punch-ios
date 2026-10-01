#!/bin/bash
# HF Punch — sign + OTA release pipeline
# Runs on a GitHub macOS runner. Requires: zsign, jq, curl, openssl, python3, gh
#
# What it does:
#   1. Authenticates to the App Store Connect API (ES256 JWT from a Team Key .p8)
#   2. Pulls registered UDIDs from the UDID worker
#   3. Registers any that aren't in the developer account yet
#   4. Creates an ad-hoc provisioning profile containing every device
#   5. Resigns the unsigned IPA with the developer certificate (zsign)
#   6. Publishes a GitHub Release with the IPA, manifest.plist and install page
set -euo pipefail

BUNDLE_ID="my.haifu.punch"
BUNDLE_NAME="HF Punch"
PROFILE_BASE="HF Punch AdHoc"
UNSIGNED_IPA="$1"   # path to HFPunch-unsigned.ipa

: "${ASC_KEY_ID:?secret missing}"
: "${ASC_ISSUER_ID:?secret missing}"
: "${ASC_KEY_P8_B64:?secret missing}"
: "${CERT_P12_B64:?secret missing}"
: "${CERT_PASSWORD:?secret missing}"
: "${UDID_WORKER_URL:?secret missing}"
: "${UDID_WORKER_TOKEN:?secret missing}"
: "${GH_TOKEN:?token missing}"
REPO="${GITHUB_REPOSITORY:?}"

py_b64d() { python3 -c "import base64,sys;sys.stdout.buffer.write(base64.b64decode(sys.stdin.read().strip()))"; }
b64url()  { python3 -c "import base64,sys;print(base64.urlsafe_b64encode(sys.stdin.buffer.read()).decode().rstrip('='))"; }

echo "==> preparing key material"
echo "$ASC_KEY_P8_B64" | py_b64d > asc.pem
echo "$CERT_P12_B64"  | py_b64d > cert.p12

echo "==> building App Store Connect JWT"
NOW=$(date +%s)
HDR=$(printf '{"alg":"ES256","kid":"%s"}' "$ASC_KEY_ID" | b64url)
CLM=$(printf '{"iss":"%s","iat":%d,"exp":%d,"aud":"appstoreconnect-v1"}' "$ASC_ISSUER_ID" "$NOW" "$((NOW + 900))" | b64url)
SIG=$(printf '%s.%s' "$HDR" "$CLM" | openssl dgst -sha256 -sign asc.pem -binary | python3 -c "
import sys
d = sys.stdin.buffer.read()
# DER ECDSA signature -> JOSE raw r||s (64 bytes)
def parse(b):
    assert b[0] == 0x30
    i = 2
    assert b[i] == 0x02; l1 = b[i+1]; r = b[i+2:i+2+l1]; i += 2 + l1
    assert b[i] == 0x02; l2 = b[i+1]; s = b[i+2:i+2+l2]
    return r, s
r, s = parse(d)
def fix(x): x = x.lstrip(b'\x00'); return x.rjust(32, b'\x00') if len(x) < 32 else x[-32:]
import base64
print(base64.urlsafe_b64encode(fix(r) + fix(s)).decode().rstrip('='))
")
TOKEN="$HDR.$CLM.$SIG"

asc_get()  { curl -g -sS -f -H "Authorization: Bearer $TOKEN" "https://api.appstoreconnect.apple.com$1"; }
asc_post() { curl -g -sS -f -X POST -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json" -d "$2" "https://api.appstoreconnect.apple.com$1"; }

echo "==> finding signing certificate id"
P12_SERIAL=$(openssl pkcs12 -in cert.p12 -passin "pass:$CERT_PASSWORD" -nokeys -clcerts 2>/dev/null \
  | openssl x509 -noout -serial | cut -d= -f2 | tr 'A-F' 'a-f' | sed 's/^0*//')
CERT_ID=$(asc_get "/v1/certificates?limit=100" | jq -r --arg s "$P12_SERIAL" \
  '.data[] | select((.attributes.serialNumber | ascii_downcase | ltrimstr("0")) == $s) | .id' | head -1)
if [ -z "$CERT_ID" ]; then
  echo "    serial match failed — falling back to newest Apple Development/Distribution cert"
  CERT_ID=$(asc_get "/v1/certificates?limit=100" | jq -r \
    '.data[] | select(.attributes.certificateType=="APPLE_DEVELOPMENT" or .attributes.certificateType=="APPLE_DISTRIBUTION") | .id' | head -1)
fi
[ -n "$CERT_ID" ] || { echo "ERROR: no matching certificate in the developer account"; exit 1; }
echo "    cert id: $CERT_ID"

echo "==> syncing device UDIDs"
EXISTING=$(asc_get "/v1/devices?filter[platform]=IOS&limit=200" | jq -r '.data[].attributes.udid' || true)
WORKER_DEVICES=$(curl -sS "$UDID_WORKER_URL/udid/list?t=$UDID_WORKER_TOKEN")
echo "$WORKER_DEVICES" | jq -c '.devices[]?' | while read -r dev; do
  udid=$(echo "$dev" | jq -r '.udid')
  name=$(echo "$dev" | jq -r '(.name // "iPhone") + " " + (.product // "")' | head -c 60)
  if echo "$EXISTING" | grep -qi "^$udid$"; then
    echo "    already registered: $udid"
  else
    echo "    registering: $udid ($name)"
    asc_post /v1/devices "{\"data\":{\"type\":\"devices\",\"attributes\":{\"name\":\"$name\",\"platform\":\"IOS\",\"udid\":\"$udid\"}}}" > /dev/null \
      || echo "    WARNING: register failed for $udid (already there?)"
  fi
done

DEVICE_IDS_JSON=$(asc_get "/v1/devices?filter[platform]=IOS&limit=200" \
  | jq -c '[.data[] | {type: "devices", id: .id}]')
DEVICE_COUNT=$(echo "$DEVICE_IDS_JSON" | jq 'length')
echo "    profile will include $DEVICE_COUNT device(s)"

echo "==> ensuring bundle id $BUNDLE_ID"
BID=$(asc_get "/v1/bundleIds?filter[identifier]=$BUNDLE_ID" | jq -r '.data[0].id // empty')
if [ -z "$BID" ]; then
  BID=$(asc_post /v1/bundleIds "{\"data\":{\"type\":\"bundleIds\",\"attributes\":{\"identifier\":\"$BUNDLE_ID\",\"name\":\"$BUNDLE_NAME\",\"platform\":\"IOS\"}}}" | jq -r '.data.id')
  echo "    created bundle id: $BID"
else
  echo "    bundle id exists: $BID"
fi

echo "==> creating ad-hoc provisioning profile"
STAMP=$(date -u +%Y%m%d-%H%M)
PROFILE_JSON=$(asc_post /v1/profiles "{\"data\":{\"type\":\"profiles\",\"attributes\":{\"name\":\"$PROFILE_BASE $STAMP\",\"profileType\":\"IOS_APP_ADHOC\"},\"relationships\":{\"bundleId\":{\"data\":{\"type\":\"bundleIds\",\"id\":\"$BID\"}},\"certificates\":{\"data\":[{\"type\":\"certificates\",\"id\":\"$CERT_ID\"}]},\"devices\":{\"data\":$DEVICE_IDS_JSON}}}}")
echo "$PROFILE_JSON" | jq -r '.data.attributes.profileContent' | py_b64d > provision.mobileprovision
echo "    profile created: $(echo "$PROFILE_JSON" | jq -r '.data.attributes.name')"

echo "==> resigning IPA with zsign"
zsign -k cert.p12 -p "$CERT_PASSWORD" -m provision.mobileprovision -o HFPunch.ipa "$UNSIGNED_IPA"
ls -la HFPunch.ipa

echo "==> publishing GitHub Release"
VER="v$(date -u +%Y.%m.%d-%H%M)"
ASSET_BASE="https://github.com/$REPO/releases/download/$VER"
MANIFEST_URL_ENC=$(python3 -c "import urllib.parse;print(urllib.parse.quote('$ASSET_BASE/manifest.plist', safe=''))")

cat > manifest.plist <<PLIST
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
<plist version="1.0">
<dict>
  <key>items</key>
  <array>
    <dict>
      <key>assets</key>
      <array>
        <dict>
          <key>kind</key><string>software-package</string>
          <key>url</key><string>$ASSET_BASE/HFPunch.ipa</string>
        </dict>
      </array>
      <key>metadata</key>
      <dict>
        <key>bundle-identifier</key><string>$BUNDLE_ID</string>
        <key>bundle-version</key><string>$VER</string>
        <key>kind</key><string>software</string>
        <key>title</key><string>$BUNDLE_NAME</string>
      </dict>
    </dict>
  </array>
</dict>
</plist>
PLIST

cat > install.html <<HTML
<!doctype html>
<html><head><meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>Install HF Punch</title>
<style>
body{font-family:-apple-system,sans-serif;background:#0e1116;color:#e8eaf0;
display:flex;min-height:100vh;align-items:center;justify-content:center;margin:0}
.card{max-width:420px;padding:32px;background:#171c24;border-radius:16px;text-align:center}
h1{font-size:22px;margin:0 0 12px}p{font-size:15px;line-height:1.5;color:#aab2c0}
a.btn{display:block;margin-top:20px;padding:14px;background:#2f7cf6;color:#fff;
text-decoration:none;border-radius:12px;font-weight:600;font-size:17px}
a.alt{display:block;margin-top:10px;padding:12px;background:#1f2630;color:#9db8e8;
text-decoration:none;border-radius:12px}
small{display:block;margin-top:14px;color:#6b7280;line-height:1.4}
</style></head><body><div class="card">
<h1>📲 Install HF Punch</h1>
<p>Open this page in <b>Safari on the iPhone</b> that was registered, then tap:</p>
<a class="btn" href="itms-services://?action=download-manifest&amp;url=$MANIFEST_URL_ENC">Install HF Punch</a>
<a class="alt" href="$UDID_WORKER_URL/udid">Phone not registered yet? Tap here</a>
<small>Build $VER · valid for 1 year on registered phones.<br>
"Not registered" error = your phone isn't registered yet — use the second link.</small>
</div></body></html>
HTML

gh release create "$VER" HFPunch.ipa manifest.plist install.html \
  --title "HF Punch $VER" \
  --notes "Signed ad-hoc build for all registered phones ($DEVICE_COUNT devices).

- Install: open [install.html]($ASSET_BASE/install.html) in Safari on a registered iPhone
- Register a new phone: [$UDID_WORKER_URL/udid]($UDID_WORKER_URL/udid)" >/dev/null

echo "==> DONE"
echo "    Release:  https://github.com/$REPO/releases/tag/$VER"
echo "    Install:  $ASSET_BASE/install.html"
