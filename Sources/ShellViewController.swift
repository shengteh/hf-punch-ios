import UIKit
import WebKit

/// WKWebView shell hosting the HF Punch web app.
/// Speaks the exact same JS bridge as the Android build:
///   window.HFShell.getVersion() / requestLocation(reqId) / onWorker(json)
///   / openLocationSettings() / openAppSettings()
/// Location results are delivered via window.__hfShellLocation(reqId, json).
final class ShellViewController: UIViewController, WKNavigationDelegate, WKScriptMessageHandler {
    private var webView: WKWebView!
    private var errorView: UIView!
    private var errorLabel: UILabel!
    private var originOk = false

    /// Injected at document start so `window.HFShell` exists before any page script runs.
    /// Routes bridge calls through the WKWebView message channel (iOS has no addJavascriptInterface).
    private static let bridgeShim = """
    (function(){
      if (window.HFShell) { return; }
      function send(o){ try { window.webkit.messageHandlers.HFShell.postMessage(o); } catch(e){} }
      window.HFShell = {
        getVersion: function(){ return '\(Config.appVersion)'; },
        requestLocation: function(reqId){
          send({cmd:'requestLocation', reqId: reqId == null ? '' : String(reqId).substring(0,64)});
          return '';
        },
        onWorker: function(json){
          var s = (typeof json === 'string') ? json : JSON.stringify(json);
          if (s && s.length <= 4096) { send({cmd:'onWorker', json: s}); }
        },
        openLocationSettings: function(){ send({cmd:'openLocationSettings'}); },
        openAppSettings: function(){ send({cmd:'openAppSettings'}); }
      };
    })();
    """

    // MARK: Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = .systemBackground

        let content = WKUserContentController()
        content.addUserScript(WKUserScript(
            source: Self.bridgeShim,
            injectionTime: .atDocumentStart,
            forMainFrameOnly: true
        ))
        content.add(self, contentWorld: .page, name: "HFShell")

        let cfg = WKWebViewConfiguration()
        cfg.userContentController = content
        cfg.websiteDataStore = .default() // persistent localStorage (hf_punch_token / hf_punch_worker)

        webView = WKWebView(frame: .zero, configuration: cfg)
        webView.translatesAutoresizingMaskIntoConstraints = false
        webView.navigationDelegate = self
        // Same fingerprint the web app checks for (Android: userAgent + " HFPunchApp/1.0")
        webView.customUserAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 17_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148 \(Config.userAgentSuffix)"
        webView.allowsBackForwardNavigationGestures = false
        view.addSubview(webView)

        buildErrorOverlay()
        NSLayoutConstraint.activate([
            webView.topAnchor.constraint(equalTo: view.topAnchor),
            webView.bottomAnchor.constraint(equalTo: view.bottomAnchor),
            webView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            webView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
        ])

        loadHome()
    }

    @objc private func loadHome() {
        setErrorVisible(false)
        webView.load(URLRequest(url: Config.baseUrl))
    }

    // MARK: Origin lock (parity with Android sameOrigin/originOk)

    private func isSameOrigin(_ url: URL?) -> Bool {
        guard let url else { return false }
        return url.host?.lowercased() == Config.baseHost
    }

    // MARK: WKNavigationDelegate

    func webView(_ webView: WKWebView, decidePolicyFor navigationAction: WKNavigationAction, decisionHandler: @escaping (WKNavigationActionPolicy) -> Void) {
        guard let url = navigationAction.request.url else {
            decisionHandler(.cancel); return
        }
        if let scheme = url.scheme?.lowercased() {
            if scheme == "about" || scheme == "data" || scheme == "blob" {
                decisionHandler(.allow); return
            }
        }
        if isSameOrigin(url) {
            decisionHandler(.allow)
        } else {
            // Android blocks every other origin; iOS: external links open in Safari.
            if url.scheme == "http" || url.scheme == "https" {
                UIApplication.shared.open(url)
            }
            decisionHandler(.cancel)
        }
    }

    func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
        originOk = isSameOrigin(webView.url)
        setErrorVisible(false)
    }

    func webView(_ webView: WKWebView, didFail navigation: WKNavigation!, withError error: Error) {
        // Main-frame failure — show the retry screen (parity with mainFrameFailed).
        originOk = false
        if let wf = error as NSError?, wf.domain == NSURLErrorDomain {
            errorLabel.text = "Cannot reach HF Punch.\nCheck your connection and try again."
        } else {
            errorLabel.text = "Cannot reach HF Punch.\n\(error.localizedDescription)"
        }
        setErrorVisible(true)
    }

    // MARK: HFShell bridge

    func userContentController(_ userContentController: WKUserContentController, didReceive message: WKScriptMessage) {
        guard originOk, let body = message.body as? [String: Any], let cmd = body["cmd"] as? String else { return }
        switch cmd {
        case "requestLocation":
            let reqId = (body["reqId"] as? String) ?? ""
            LocationService.shared.request { [weak self] json in
                self?.deliverLocation(reqId: reqId, json: json)
            }
        case "onWorker":
            handleOnWorker((body["json"] as? String) ?? "")
        case "openLocationSettings", "openAppSettings":
            // iOS has a single Settings page per app; both land there.
            if let url = URL(string: UIApplication.openSettingsURLString) {
                UIApplication.shared.open(url)
            }
        default:
            break
        }
    }

    private func handleOnWorker(_ jsonText: String) {
        guard let data = jsonText.data(using: .utf8),
              let obj = (try? JSONSerialization.jsonObject(with: data)) as? [String: Any],
              obj.keys.contains("token")
        else { return }

        guard let rawToken = obj["token"] as? String else {
            // token: null — worker signed out
            Prefs.applyWorker(token: nil, fullName: nil, staffId: nil)
            ReminderService.shared.clearScheduled()
            return
        }
        let token = rawToken.trimmingCharacters(in: .whitespacesAndNewlines)
        guard Prefs.validToken(token) else { return }
        Prefs.applyWorker(
            token: token,
            fullName: obj["full_name"] as? String,
            staffId: obj["staff_id"] as? String
        )
        ReminderService.shared.refresh(nil)
    }

    private func deliverLocation(reqId: String, json: String) {
        let reqIdEsc = Self.jsStringLiteral(String(reqId.prefix(64)))
        let js = "(function(){if(typeof window.__hfShellLocation==='function'){window.__hfShellLocation(\(reqIdEsc),\(json));}})();"
        guard originOk else { return }
        webView.evaluateJavaScript(js, completionHandler: nil)
    }

    /// JSON text is a valid JS literal, but reqId needs proper string escaping.
    private static func jsStringLiteral(_ s: String) -> String {
        let escaped = s
            .replacingOccurrences(of: "\\", with: "\\\\")
            .replacingOccurrences(of: "\"", with: "\\\"")
            .replacingOccurrences(of: "\n", with: "\\n")
            .replacingOccurrences(of: "\r", with: "\\r")
        return "\"\(escaped)\""
    }

    // MARK: Offline overlay

    private func buildErrorOverlay() {
        errorView = UIView()
        errorView.backgroundColor = .systemBackground
        errorView.isHidden = true
        errorView.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(errorView)

        errorLabel = UILabel()
        errorLabel.text = "Cannot reach HF Punch.\nCheck your connection and try again."
        errorLabel.textAlignment = .center
        errorLabel.numberOfLines = 0
        errorLabel.textColor = .secondaryLabel
        errorLabel.font = .systemFont(ofSize: 16, weight: .medium)
        errorLabel.translatesAutoresizingMaskIntoConstraints = false
        errorView.addSubview(errorLabel)

        let retry = UIButton(type: .system)
        retry.setTitle("Retry", for: .normal)
        retry.titleLabel?.font = .systemFont(ofSize: 17, weight: .semibold)
        retry.addTarget(self, action: #selector(retryTapped), for: .touchUpInside)
        retry.translatesAutoresizingMaskIntoConstraints = false
        errorView.addSubview(retry)

        NSLayoutConstraint.activate([
            errorView.topAnchor.constraint(equalTo: view.topAnchor),
            errorView.bottomAnchor.constraint(equalTo: view.bottomAnchor),
            errorView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            errorView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            errorLabel.centerXAnchor.constraint(equalTo: errorView.centerXAnchor),
            errorLabel.centerYAnchor.constraint(equalTo: errorView.centerYAnchor, constant: -24),
            errorLabel.leadingAnchor.constraint(greaterThanOrEqualTo: errorView.leadingAnchor, constant: 32),
            errorLabel.trailingAnchor.constraint(lessThanOrEqualTo: errorView.trailingAnchor, constant: -32),
            retry.centerXAnchor.constraint(equalTo: errorView.centerXAnchor),
            retry.topAnchor.constraint(equalTo: errorLabel.bottomAnchor, constant: 20),
        ])
    }

    @objc private func retryTapped() { loadHome() }

    private func setErrorVisible(_ visible: Bool) {
        errorView.isHidden = !visible
    }
}
