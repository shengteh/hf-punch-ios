# DEPLOY — one-time setup for over-the-air signing

Everything runs from this Windows PC. No Mac at any point.
End state: workers tap a link in Safari → app installs → good for 1 year.

## 0. Make the GitHub repo public

OTA install links (`itms-services://`) need an anonymous HTTPS URL for the IPA —
GitHub Release assets on a **private** repo require auth and the install fails.

This is safe: an ad-hoc signed IPA only runs on phones whose UDIDs are inside
the provisioning profile. Strangers downloading it get "Unable to install".

## 1. Create your signing certificate (Windows + openssl, no Mac)

Open PowerShell in a scratch folder:

```powershell
# private key + CSR
openssl req -newkey rsa:2048 -nodes -keyout hf.key -out hf.csr `
  -subj "/CN=HF Punch Distribution/O=HF Punch/C=MY"

# ...after step below downloads hf.cer:
# pack key + cert into one .p12
openssl x509 -inform der -in hf.cer -out hf.pem
openssl pkcs12 -export -out cert.p12 -inkey hf.key -in hf.pem -passout pass:YOUR_P12_PASSWORD
```

Between the two blocks:
1. Go to `developer.apple.com` → Certificates → **+**
2. Choose **Apple Distribution** → upload `hf.csr` → download `hf.cer`

Keep `cert.p12` and the password — they go into GitHub secrets next.

## 2. Create an App Store Connect API key

1. `appstoreconnect.apple.com` → Users and Access → **Integrations** (or Keys)
2. **Team Keys** → Generate → role **Developer** (enough for devices/profiles/certs)
3. Write down **Key ID** and **Issuer ID**, download the `.p8` file (one time only!)

## 3. Deploy the UDID registration worker

Needs Node.js (install the LTS from nodejs.org if `node -v` fails):

```powershell
cd $HOME\hf-punch-ios\worker
npm install
npx wrangler login            # opens browser, sign in with Cloudflare
npx wrangler kv namespace create UDIDS
# paste the printed id into wrangler.toml (replace REPLACE_WITH_KV_NAMESPACE_ID)
npx wrangler deploy
npx wrangler secret put TOKEN   # paste a random string, e.g.:
#   -join((48..57)+(65..90)+(97..122) | Get-Random -Count 40 | % {[char]$_})
```

Note the worker URL it prints, e.g. `https://hf-punch-udid.<your-subdomain>.workers.dev`.
Test it: open `<url>/udid` on the iPhone — that's the page workers use.

## 4. Fill the GitHub secrets

Repo → Settings → Secrets and variables → Actions → New repository secret.
Base64 helpers (PowerShell):

```powershell
[Convert]::ToBase64String((Get-Content cert.p12 -AsByteStream)) | Set-Clipboard
# paste as CERT_P12_B64

[Convert]::ToBase64String((Get-Content AuthKey_XXXXXX.p8 -AsByteStream)) | Set-Clipboard
# paste as ASC_KEY_P8_B64
```

| Secret | Value |
|--------|-------|
| `CERT_P12_B64` | base64 of `cert.p12` |
| `CERT_PASSWORD` | the p12 password you chose |
| `ASC_KEY_ID` | Key ID from step 2 |
| `ASC_ISSUER_ID` | Issuer ID from step 2 |
| `ASC_KEY_P8_B64` | base64 of the `.p8` key |
| `UDID_WORKER_URL` | `https://hf-punch-udid.<subdomain>.workers.dev` |
| `UDID_WORKER_TOKEN` | the TOKEN you set on the worker |

## 5. Ship it

Push (or Actions → **Build and sign IPA** → Run workflow). ~12 minutes later the
Release page has `install.html` — that link is the installer.

**WhatsApp message for the crew:**

> 📲 HF Punch for iPhone — two taps:
> 1️⃣ First time only — register your phone: `<UDID_WORKER_URL>/udid`
> (install the profile in Settings, then delete it)
> 2️⃣ Install the app: `<release install.html URL>` (open in Safari)
>
> Phone says "not registered"? Do step 1, wait for the next build (Monday), then step 2.

## Day-to-day

- **New worker joins** → they tap the register link → next build (weekly cron,
  or trigger manually) includes their phone → they tap install
- **App UI changes** → deploy the web frontend to Workers — the app updates
  itself, nothing to rebuild
- **Shell (Swift) changes** → push → crew taps install again over the old build
- **Certificate/profile** → renews automatically every build (fresh profile each
  run). The p12 itself lasts until you revoke it — Apple Distribution is 5+ years
- **100-phone limit** → dev accounts allow 100 iPhone UDIDs/year; you are at 0
