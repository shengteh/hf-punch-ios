import Foundation

/// Status payload from Supabase RPC `get_my_status`.
struct PunchStatus {
    let ok: Bool
    let state: String?       // "in" | "out"
    let siteName: String?
    let inAtLocal: String?
    let code: String?        // error code when ok == false
}

/// Calls Supabase `get_my_status(p_device_token)` — same contract as Android ReminderCheck.
final class StatusService {
    static let shared = StatusService()
    private let session: URLSession

    private init() {
        let cfg = URLSessionConfiguration.ephemeral
        cfg.timeoutIntervalForRequest = 8
        cfg.timeoutIntervalForResource = 12
        session = URLSession(configuration: cfg)
    }

    func fetchStatus(token: String, completion: @escaping (PunchStatus?) -> Void) {
        guard let url = URL(string: Config.supabaseUrl + "/rest/v1/rpc/get_my_status") else {
            completion(nil); return
        }
        var req = URLRequest(url: url)
        req.httpMethod = "POST"
        req.setValue("application/json; charset=utf-8", forHTTPHeaderField: "Content-Type")
        req.setValue(Config.supabaseKey, forHTTPHeaderField: "apikey")
        req.setValue("Bearer " + Config.supabaseKey, forHTTPHeaderField: "Authorization")
        req.httpBody = try? JSONSerialization.data(withJSONObject: ["p_device_token": token])

        session.dataTask(with: req) { data, resp, _ in
            let parsed = Self.parse(data: data, resp: resp)
            DispatchQueue.main.async { completion(parsed) }
        }.resume()
    }

    private static func parse(data: Data?, resp: URLResponse?) -> PunchStatus? {
        guard let data,
              (resp as? HTTPURLResponse)?.statusCode == 200,
              data.count <= 262_144,
              let obj = (try? JSONSerialization.jsonObject(with: data)) as? [String: Any]
        else { return nil }
        return PunchStatus(
            ok: obj["ok"] as? Bool ?? false,
            state: (obj["state"] as? String)?.prefix(20).description,
            siteName: (obj["site_name"] as? String)?.prefix(120).description,
            inAtLocal: (obj["in_at_local"] as? String)?.prefix(60).description,
            code: (obj["code"] as? String)?.prefix(60).description
        )
    }
}
