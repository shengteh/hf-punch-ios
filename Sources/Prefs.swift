import Foundation

/// Build constants — mirrored from the Android BuildConfig.java of my.haifu.punch.
enum Config {
    static let baseUrl = URL(string: "https://hf-punch-system.yfong0426.workers.dev/")!
    static let baseHost = "hf-punch-system.yfong0426.workers.dev"
    static let supabaseUrl = "https://fwuftjunybbxlhwauxta.supabase.co"
    static let supabaseKey = "sb_publishable_mkW7DcSITuXpOGtZHTGFjQ_ZNczYv1t"
    static let appVersion = "1.0 (1)"
    static let userAgentSuffix = "HFPunchApp/1.0"
}

/// Persisted worker state — mirrors Android Prefs (SharedPreferences file "hf_punch_shell").
enum Prefs {
    private static let d = UserDefaults.standard

    static let maxReminders = 6
    static let defaultReminders = ["17:10", "20:30"]

    static var token: String? { d.string(forKey: "hf_punch_token") }
    static var fullName: String? { d.string(forKey: "hf_punch_full_name") }
    static var staffId: String? { d.string(forKey: "hf_punch_staff_id") }
    /// "HH:mm" or nil = morning reminder off (Android default: off).
    static var morning: String? { d.string(forKey: "hf_punch_morning") }
    static var reminders: [String] {
        let arr = d.stringArray(forKey: "hf_punch_reminders") ?? defaultReminders
        return Array(arr.prefix(maxReminders))
    }

    static func validToken(_ t: String) -> Bool {
        let s = t.trimmingCharacters(in: .whitespacesAndNewlines)
        return s.count >= 8 && s.count <= 512
    }

    static func applyWorker(token: String?, fullName: String?, staffId: String?) {
        if let token = token, validToken(token) {
            d.set(token.trimmingCharacters(in: .whitespacesAndNewlines), forKey: "hf_punch_token")
            if let n = fullName { d.set(String(n.prefix(120)), forKey: "hf_punch_full_name") }
            if let s = staffId { d.set(String(s.prefix(64)), forKey: "hf_punch_staff_id") }
        } else {
            clearWorker()
        }
    }

    static func clearWorker() {
        for k in ["hf_punch_token", "hf_punch_full_name", "hf_punch_staff_id"] {
            d.removeObject(forKey: k)
        }
    }
}
