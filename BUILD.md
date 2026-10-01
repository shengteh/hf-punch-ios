# HF Punch — iOS build & install guide

iOS port of the `my.haifu.punch` Android shell. The real UI lives on
`https://hf-punch-system.yfong0426.workers.dev/` — this app is a WKWebView shell
speaking the same `HFShell` JS bridge, so the existing web frontend works unmodified.

## What's inside

| File | Role |
|------|------|
| `Sources/ShellViewController.swift` | WKWebView shell + `HFShell` bridge (getVersion, requestLocation, onWorker, openLocationSettings, openAppSettings) + origin lock + offline retry screen |
| `Sources/LocationService.swift` | One-shot GPS: 20s window, ≤50m early-out, Android JSON parity (`{ok, lat, lng, accuracy, mock, stale, age_s, provider}`) |
| `Sources/StatusService.swift` | Supabase RPC `get_my_status` |
| `Sources/ReminderService.swift` | Punch in/out reminders (evening slots 17:10 & 20:30 default, morning optional, Sundays skipped) |
| `Sources/Prefs.swift` | Config constants + persisted worker state (mirrors Android Prefs) |
| `Sources/AppDelegate.swift` | App lifecycle, notification permission, BGAppRefresh |
| `project.yml` | XcodeGen project spec (generates HFPunch.xcodeproj) |
| `.github/workflows/build-ipa.yml` | CI: builds unsigned IPA on a GitHub-hosted Mac |
| `recovered-android-source/` | jadx decompilation of your APK, kept for reference |

## Build the IPA (no Mac needed)

1. Create a new GitHub repo (private is fine) and push this folder:
   ```powershell
   cd $HOME\hf-punch-ios
   git init
   git add .
   git commit -m "HF Punch iOS shell"
   git remote add origin https://github.com/YOU/hf-punch-ios.git
   git push -u origin main
   ```
2. The **Build unsigned IPA** action runs automatically on push (or trigger it from
   the Actions tab → *Build unsigned IPA* → *Run workflow*).
3. When it finishes, download the `HFPunch-unsigned-ipa` artifact — that's your IPA.

## Install on iPhone (no App Store)

**Sideloadly** (free, on this PC):
1. Install Sideloadly from https://sideloadly.io
2. Plug the iPhone in via USB, trust the computer
3. Drag `HFPunch-unsigned.ipa` into Sideloadly
4. Enter your Apple ID + password (creates a free personal signing cert)
5. Install. On the phone: Settings → General → VPN & Device Management → tap your
   Apple ID → **Trust**.

Free Apple ID caveats: 3 apps max, expires every 7 days — re-plug and click Install
again in Sideloadly to renew (data is kept).

**TrollStore** (permanent, no signing, no expiry) — only if the iPhone runs iOS
14.0–16.6.1 / 17.0 beta. Check the phone's iOS version first.

**$99/yr Developer account** — 1-year signing, unlimited devices with registered
UDIDs, and **TestFlight**: invite your whole HFBP crew by email/phone with zero
App Store listing. That's the clean long-term play for distributing to workers.

## Notes

- UpdateManager was intentionally not ported — iOS apps can't self-install updates,
  and the UI is served from your Workers so content updates go live instantly anyway.
- The `morning` reminder and custom evening slots are configurable in
  `Sources/Prefs.swift` defaults; on Android they were set via intents.
- The Supabase key embedded here is the same publishable client key already
  shipping inside your APK — it's designed to be public.
