package my.haifu.punch;

import android.webkit.JavascriptInterface;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;

/* JADX INFO: compiled from: ShellBridge.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u0006H\u0007J\b\u0010\n\u001a\u00020\bH\u0007J\b\u0010\u000b\u001a\u00020\bH\u0007J\u0012\u0010\f\u001a\u00020\u00062\b\u0010\r\u001a\u0004\u0018\u00010\u0006H\u0007R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lmy/haifu/punch/ShellBridge;", "", "host", "Lmy/haifu/punch/MainActivity;", "(Lmy/haifu/punch/MainActivity;)V", "getVersion", "", "onWorker", "", "json", "openAppSettings", "openLocationSettings", "requestLocation", "reqId", "app_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ShellBridge {
    private final MainActivity host;

    public ShellBridge(MainActivity host) {
        Intrinsics.checkNotNullParameter(host, "host");
        this.host = host;
    }

    @JavascriptInterface
    public final String getVersion() {
        return "1.0 (1)";
    }

    @JavascriptInterface
    public final String requestLocation(String reqId) {
        if (!this.host.getOriginOk()) {
            return "";
        }
        if (reqId == null) {
            reqId = "";
        }
        final String strTake = StringsKt.take(reqId, 64);
        ShellLocation.INSTANCE.request(this.host, new Function1<JSONObject, Unit>() { // from class: my.haifu.punch.ShellBridge.requestLocation.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(JSONObject jSONObject) {
                invoke2(jSONObject);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(JSONObject result) {
                Intrinsics.checkNotNullParameter(result, "result");
                MainActivity mainActivity = ShellBridge.this.host;
                String str = strTake;
                String string = result.toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                mainActivity.deliverLocation(str, string);
            }
        });
        return "";
    }

    @JavascriptInterface
    public final void onWorker(String json) {
        String string;
        if (this.host.getOriginOk() && json != null && json.length() <= 4096) {
            try {
                JSONObject jSONObject = new JSONObject(json);
                if (jSONObject.has("token")) {
                    if (jSONObject.isNull("token")) {
                        this.host.applyWorker(null, null, null);
                        return;
                    }
                    Object objOpt = jSONObject.opt("token");
                    String str = objOpt instanceof String ? (String) objOpt : null;
                    if (str == null || (string = StringsKt.trim((CharSequence) str).toString()) == null || !PrefsKt.validToken(string)) {
                        return;
                    }
                    this.host.applyWorker(string, PrefsKt.jsonStr(jSONObject, "full_name", 120), PrefsKt.jsonStr(jSONObject, "staff_id", 64));
                }
            } catch (Throwable unused) {
            }
        }
    }

    @JavascriptInterface
    public final void openLocationSettings() {
        if (this.host.getOriginOk()) {
            this.host.runOnUiThread(new Runnable() { // from class: my.haifu.punch.ShellBridge$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ShellBridge.openLocationSettings$lambda$0(this.f$0);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void openLocationSettings$lambda$0(ShellBridge this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.host.openLocationSettings();
    }

    @JavascriptInterface
    public final void openAppSettings() {
        if (this.host.getOriginOk()) {
            this.host.runOnUiThread(new Runnable() { // from class: my.haifu.punch.ShellBridge$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ShellBridge.openAppSettings$lambda$1(this.f$0);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void openAppSettings$lambda$1(ShellBridge this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.host.openAppSettings();
    }
}
