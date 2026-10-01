/**
 * HF Punch — UDID Registration Worker
 *
 * Flow: worker opens /udid in Safari → taps the button → iOS downloads the
 * profile → Settings → Profile Downloaded → Install → iOS POSTs the UDID to
 * /udid/submit → stored in KV → redirected to a thank-you page.
 *
 * CI later reads /udid/list?t=SECRET, registers UDIDs via the App Store
 * Connect API, and builds an ad-hoc provisioning profile containing them.
 */

const LANDING = `<!doctype html>
<html><head><meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>HF Punch — Register Phone</title>
<style>
body{font-family:-apple-system,sans-serif;background:#0e1116;color:#e8eaf0;
display:flex;min-height:100vh;align-items:center;justify-content:center;margin:0}
.card{max-width:420px;padding:32px;background:#171c24;border-radius:16px;
box-shadow:0 8px 32px rgba(0,0,0,.4);text-align:center}
h1{font-size:22px;margin:0 0 12px}p{font-size:15px;line-height:1.5;color:#aab2c0}
a.btn{display:block;margin-top:20px;padding:14px;background:#2f7cf6;color:#fff;
text-decoration:none;border-radius:12px;font-weight:600;font-size:17px}
small{display:block;margin-top:16px;color:#6b7280;line-height:1.4}
</style></head><body><div class="card">
<h1>📱 Register your iPhone</h1>
<p>This one-time step lets the HF Punch app install on your phone.</p>
<a class="btn" href="/udid/mobileconfig">Download Registration Profile</a>
<p style="margin-top:20px;font-size:14px">After downloading:<br>
<b>Settings → General → VPN &amp; Device Management<br>→ Profile Downloaded → Install</b></p>
<small>The profile sends only your phone's device ID, then you can delete it.
It is unsigned, so iOS will warn you — that's normal.</small>
</div></body></html>`;

const THANKS = (udidTail) => `<!doctype html>
<html><head><meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>HF Punch — Registered</title>
<style>
body{font-family:-apple-system,sans-serif;background:#0e1116;color:#e8eaf0;
display:flex;min-height:100vh;align-items:center;justify-content:center;margin:0}
.card{max-width:420px;padding:32px;background:#171c24;border-radius:16px;text-align:center}
h1{font-size:22px;margin:0 0 12px}p{font-size:15px;line-height:1.5;color:#aab2c0}
code{background:#0c0f14;padding:4px 8px;border-radius:6px}
</style></head><body><div class="card">
<h1>✅ Phone registered</h1>
<p>Your device ID ends in <code>…${udidTail}</code></p>
<p>Tell your supervisor, then wait for the <b>Install HF Punch</b> link in the
WhatsApp group. You can now delete the profile
(Settings → General → VPN &amp; Device Management).</p>
</div></body></html>`;

function mobileConfig(origin) {
  return `<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
<plist version="1.0">
<dict>
  <key>PayloadContent</key>
  <dict>
    <key>URL</key>
    <string>${origin}/udid/submit</string>
    <key>DeviceAttributes</key>
    <array>
      <string>UDID</string>
      <string>DEVICE_NAME</string>
      <string>PRODUCT</string>
      <string>VERSION</string>
      <string>SERIAL</string>
    </array>
  </dict>
  <key>PayloadOrganization</key>
  <string>HF Punch</string>
  <key>PayloadDisplayName</key>
  <string>HF Punch Worker Registration</string>
  <key>PayloadDescription</key>
  <string>Registers this phone so the HF Punch app can install. Remove after install.</string>
  <key>PayloadVersion</key>
  <integer>1</integer>
  <key>PayloadUUID</key>
  <string>B7F3A9C2-4E5D-4A6B-8C1D-9E0F2A3B4C5D</string>
  <key>PayloadIdentifier</key>
  <string>my.haifu.punch.udid</string>
  <key>PayloadType</key>
  <string>Profile Service</string>
  <key>PayloadRemovalDisallowed</key>
  <false/>
</dict>
</plist>`;
}

function grab(text, key) {
  const m = text.match(new RegExp(key + '\\s*=\\s*"?([^"\\r\\n]+)"?'));
  return m ? m[1].trim() : null;
}

export default {
  async fetch(request, env) {
    const url = new URL(request.url);
    const path = url.pathname.replace(/\/+$/, '') || '/';
    const method = request.method;

    if (method === 'GET' && path === '/udid') {
      return new Response(LANDING, { headers: { 'Content-Type': 'text/html; charset=utf-8' } });
    }

    if (method === 'GET' && path === '/udid/mobileconfig') {
      return new Response(mobileConfig(url.origin), {
        headers: { 'Content-Type': 'application/x-apple-asam-config' },
      });
    }

    if (method === 'POST' && path === '/udid/submit') {
      const text = await request.text();
      const udid = grab(text, 'UDID');
      if (!udid || !/^[0-9A-Fa-f-]{25,64}$/.test(udid)) {
        return new Response('Bad payload', { status: 400 });
      }
      const record = {
        udid,
        name: grab(text, 'DEVICE_NAME') || 'iPhone',
        product: grab(text, 'PRODUCT') || 'ios',
        version: grab(text, 'VERSION') || '',
        serial: grab(text, 'SERIAL') || '',
        ts: Date.now(),
      };
      await env.UDIDS.put('udid:' + udid, JSON.stringify(record));
      const tail = udid.slice(-6);
      return new Response(THANKS(escape(tail)), {
        status: 302,
        headers: { Location: url.origin + '/udid/thanks?d=' + encodeURIComponent(tail) },
      });
    }

    if (method === 'GET' && path === '/udid/thanks') {
      const tail = url.searchParams.get('d') || '······';
      return new Response(THANKS(escape(tail)), {
        headers: { 'Content-Type': 'text/html; charset=utf-8' },
      });
    }

    if (method === 'GET' && path === '/udid/count') {
      const list = await env.UDIDS.list({ prefix: 'udid:' });
      return Response.json({ count: list.keys.length });
    }

    if (method === 'GET' && path === '/udid/list') {
      const token = url.searchParams.get('t');
      if (!env.TOKEN || token !== env.TOKEN) {
        return Response.json({ error: 'unauthorized' }, { status: 401 });
      }
      const list = await env.UDIDS.list({ prefix: 'udid:' });
      const devices = await Promise.all(
        list.keys.map(async (k) => JSON.parse(await env.UDIDS.get(k.name)))
      );
      devices.sort((a, b) => a.ts - b.ts);
      return Response.json({ devices });
    }

    return new Response('HF Punch UDID service\n\n/udid        worker registration page\n/udid/count  registered count\n', {
      status: 404,
      headers: { 'Content-Type': 'text/plain; charset=utf-8' },
    });
  },
};
