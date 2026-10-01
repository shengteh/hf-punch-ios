import Foundation
import UserNotifications
import BackgroundTasks

/// Punch reminders — iOS port of ReminderScheduler + ReminderCheck.
///
/// Android uses exact AlarmManager slots that run a live Supabase check at fire time.
/// iOS gives us no exact background execution without push, so the honest port is:
/// every app open (workers open the app to punch anyway) + a best-effort BGAppRefresh
/// fetch `get_my_status` and (re)schedule UNCalendarNotificationTrigger notifications
/// for the next 7 days. Fresh state, no push server required.
final class ReminderService {
    static let shared = ReminderService()
    private let center = UNUserNotificationCenter.current()
    private let idsKey = "hf_scheduled_ids"

    // MARK: Public

    func start() {
        center.requestAuthorization(options: [.alert, .sound, .badge]) { _, _ in }
        refresh(nil)
    }

    func refresh(_ completion: ((Bool) -> Void)?) {
        guard let token = Prefs.token else {
            clearScheduled()
            completion?(false)
            return
        }
        StatusService.shared.fetchStatus(token: token) { [weak self] status in
            guard let self else { completion?(false); return }
            self.clearScheduled()
            guard let status, status.ok else {
                completion?(false)
                return
            }
            self.schedule(status: status)
            completion?(true)
        }
    }

    func clearScheduled() {
        let ids = UserDefaults.standard.stringArray(forKey: idsKey) ?? []
        if !ids.isEmpty {
            center.removePendingNotificationRequests(withIdentifiers: ids)
        }
        UserDefaults.standard.set([String](), forKey: idsKey)
    }

    func scheduleBackgroundRefresh() {
        let req = BGAppRefreshTaskRequest(identifier: "my.haifu.punch.refresh")
        req.earliestBeginDate = Date(timeIntervalSinceNow: 6 * 3600)
        try? BGTaskScheduler.shared.submit(req) // no-op if one is already queued
    }

    // MARK: Scheduling

    private func schedule(status: PunchStatus) {
        var ids: [String] = []
        let cal = Calendar.current
        let now = Date()

        // Evening slots: remind to punch out when still clocked in.
        if status.state == "in" {
            let clock = clockText(status.inAtLocal) ?? "—"
            var text = "You are still clocked in (since \(clock))."
            if let site = status.siteName, !site.isEmpty { text += "\n\(site)" }
            for slot in Prefs.reminders {
                guard let comps = Self.parseHHmm(slot) else { continue }
                for dayOffset in 0..<7 {
                    guard let date = cal.date(byAdding: .day, value: dayOffset, to: now) else { continue }
                    guard let fireDate = cal.date(bySettingHour: comps.hour, minute: comps.minute, second: 0, of: date) else { continue }
                    guard fireDate > now else { continue }
                    let id = "hf-rem-out-\(slot)-\(dayOffset)"
                    post(id: id, title: "Punch out reminder", body: text, fireDate: fireDate)
                    ids.append(id)
                }
            }
        }

        // Morning slot (only if enabled): remind to punch in when clocked out. Skips Sundays.
        if status.state == "out", let morning = Prefs.morning,
           let comps = Self.parseHHmm(morning) {
            var text = "You have not clocked in yet today."
            if let site = status.siteName, !site.isEmpty { text += "\n\(site)" }
            for dayOffset in 0..<7 {
                guard let date = cal.date(byAdding: .day, value: dayOffset, to: now) else { continue }
                guard cal.component(.weekday, from: date) != 1 else { continue } // Sunday — no morning reminder
                guard let fireDate = cal.date(bySettingHour: comps.hour, minute: comps.minute, second: 0, of: date) else { continue }
                guard fireDate > now else { continue }
                let id = "hf-rem-in-\(dayOffset)"
                post(id: id, title: "Punch in reminder", body: text, fireDate: fireDate)
                ids.append(id)
            }
        }

        UserDefaults.standard.set(ids, forKey: idsKey)
    }

    private func post(id: String, title: String, body: String, fireDate: Date) {
        let content = UNMutableNotificationContent()
        content.title = title
        content.body = body
        content.sound = .default
        content.interruptionLevel = .timeSensitive

        let comps = Calendar.current.dateComponents([.year, .month, .day, .hour, .minute], from: fireDate)
        let trigger = UNCalendarNotificationTrigger(dateMatching: comps, repeats: false)
        center.add(UNNotificationRequest(identifier: id, content: content, trigger: trigger))
    }

    // MARK: Helpers

    /// "HH:mm" -> (hour, minute)
    static func parseHHmm(_ s: String) -> (hour: Int, minute: Int)? {
        let parts = s.split(separator: ":")
        guard parts.count == 2, let h = Int(parts[0]), let m = Int(parts[1]),
              (0...23).contains(h), (0...59).contains(m) else { return nil }
        return (h, m)
    }

    /// Ports Android ReminderCheck.clockText(): extracts "h:MM AM/PM" from an ISO-ish timestamp.
    func clockText(_ t: String?) -> String? {
        guard let t, !t.trimmingCharacters(in: .whitespaces).isEmpty else { return nil }
        let trimmed = t.trimmingCharacters(in: .whitespaces)
        var hour: Int?, minute: Int?
        if let m = Self.firstMatch("T(\\d{1,2}):(\\d{2})", in: trimmed) ?? Self.firstMatch("(\\d{1,2}):(\\d{2})", in: trimmed) {
            hour = m.0; minute = m.1
        } else {
            return String(trimmed.prefix(24))
        }
        guard var h = hour, let mm = minute else { return nil }
        let lower = trimmed.lowercased()
        if lower.contains("pm") && h < 12 { h += 12 }
        if lower.contains("am") && h == 12 { h = 0 }
        let display = h % 12 == 0 ? 12 : h % 12
        let meridiem = (h % 24) < 12 ? "AM" : "PM"
        return String(format: "%d:%02d %@", display, mm, meridiem)
    }

    private static func firstMatch(_ pattern: String, in s: String) -> (Int, Int)? {
        guard let regex = try? NSRegularExpression(pattern: pattern),
              let m = regex.firstMatch(in: s, range: NSRange(s.startIndex..., in: s)),
              m.numberOfRanges >= 3,
              let a = Range(m.range(at: 1), in: s), let b = Range(m.range(at: 2), in: s),
              let h = Int(s[a]), let mm = Int(s[b])
        else { return nil }
        return (h, mm)
    }
}
