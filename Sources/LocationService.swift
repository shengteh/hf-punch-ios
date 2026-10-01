import Foundation
import CoreLocation

/// One-shot location fetch mirroring Android ShellLocation:
/// - up to 20s window, early-out when a fix with accuracy <= 50m arrives
/// - delivers the best fix; fails with {ok:false, reason:...} otherwise
/// - JSON payload shape: {ok, lat, lng, accuracy, mock, stale, age_s, provider}
final class LocationService: NSObject, CLLocationManagerDelegate {
    static let shared = LocationService()

    private let lm = CLLocationManager()
    private var pending: ((String) -> Void)?
    private var best: CLLocation?
    private var timer: Timer?

    private let windowS: TimeInterval = 20
    private let goodAccuracy: Double = 50
    private let staleMax: TimeInterval = 600

    override private init() {
        super.init()
        lm.delegate = self
        lm.desiredAccuracy = kCLLocationAccuracyBest
    }

    // MARK: Public

    func request(completion: @escaping (String) -> Void) {
        DispatchQueue.main.async {
            guard self.pending == nil else { return } // MAX_WAITERS guard: one at a time on iOS
            switch self.lm.authorizationStatus {
            case .denied, .restricted:
                completion(Self.fail("denied")); return
            case .notDetermined:
                self.lm.requestWhenInUseAuthorization()
                // Authorization callback arrives later; try anyway with the 20s window.
            default:
                break
            }
            self.pending = completion
            self.best = nil
            self.lm.startUpdatingLocation()
            self.timer = Timer.scheduledTimer(withTimeInterval: self.windowS, repeats: false) { [weak self] _ in
                self?.finish()
            }
        }
    }

    // MARK: CLLocationManagerDelegate

    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        for loc in locations where loc.horizontalAccuracy >= 0 && CLLocationCoordinate2DIsValid(loc.coordinate) {
            if best == nil || loc.horizontalAccuracy < best!.horizontalAccuracy {
                best = loc
            }
        }
        if let b = best, b.horizontalAccuracy <= goodAccuracy {
            finish()
        }
    }

    func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
        if pending != nil {
            deliver(Self.fail("unavailable"))
        }
    }

    // MARK: Internals

    private func finish() {
        guard pending != nil else { return }
        if let b = best {
            let age = Date().timeIntervalSince(b.timestamp)
            deliver(payload(b, stale: age > staleMax))
        } else {
            deliver(Self.fail("timeout"))
        }
    }

    private func deliver(_ json: String) {
        timer?.invalidate(); timer = nil
        lm.stopUpdatingLocation()
        let cb = pending
        pending = nil
        cb?(json)
    }

    private func payload(_ loc: CLLocation, stale: Bool) -> String {
        let age = max(0, Int(Date().timeIntervalSince(loc.timestamp)))
        let acc = loc.horizontalAccuracy >= 0 ? (loc.horizontalAccuracy * 10).rounded() / 10 : nil
        var obj: [String: Any] = [
            "ok": true,
            "lat": loc.coordinate.latitude,
            "lng": loc.coordinate.longitude,
            "mock": false,                 // iOS exposes no mock flag; always false
            "stale": stale,
            "age_s": age,
            "provider": "gps"
        ]
        obj["accuracy"] = acc ?? NSNull()
        guard let data = try? JSONSerialization.data(withJSONObject: obj),
              let text = String(data: data, encoding: .utf8) else {
            return Self.fail("encode")
        }
        return text
    }

    private static func fail(_ reason: String) -> String {
        let obj: [String: Any] = ["ok": false, "reason": reason]
        return (try? JSONSerialization.data(withJSONObject: obj))
            .flatMap { String(data: $0, encoding: .utf8) } ?? "{\"ok\":false,\"reason\":\"encode\"}"
    }
}
