import UIKit
import BackgroundTasks

/// iOS shell for HF Punch — mirrors the Android `my.haifu.punch` WebView shell.
/// Real UI lives on the Cloudflare Workers site; this app hosts it in a WKWebView,
/// exposes the same `HFShell` JS bridge, and schedules punch reminders.
@main
final class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil
    ) -> Bool {
        let shell = ShellViewController()
        window = UIWindow(frame: UIScreen.main.bounds)
        window?.rootViewController = shell
        window?.makeKeyAndVisible()

        ReminderService.shared.start()

        BGTaskScheduler.shared.register(forTaskWithIdentifier: "my.haifu.punch.refresh", using: nil) { task in
            ReminderService.shared.refresh { _ in
                task.setTaskCompleted(success: true)
            }
            ReminderService.shared.scheduleBackgroundRefresh()
        }
        return true
    }

    func applicationDidBecomeActive(_ application: UIApplication) {
        // Workers open this app daily to punch; every open re-syncs reminders.
        ReminderService.shared.refresh(nil)
        ReminderService.shared.scheduleBackgroundRefresh()
    }
}
