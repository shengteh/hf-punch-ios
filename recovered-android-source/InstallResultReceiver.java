package my.haifu.punch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: InstallResultReceiver.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\n"}, d2 = {"Lmy/haifu/punch/InstallResultReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "Companion", "app_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InstallResultReceiver extends BroadcastReceiver {
    public static final String ACTION_RESULT = "my.haifu.punch.INSTALL_RESULT";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Intent intent2;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(intent, "intent");
        if (Intrinsics.areEqual(intent.getAction(), ACTION_RESULT)) {
            int intExtra = intent.getIntExtra("android.content.pm.extra.STATUS", Integer.MIN_VALUE);
            if (intExtra != -1) {
                if (intExtra == 0) {
                    UpdateManager.INSTANCE.onInstalled(context);
                    return;
                }
                UpdateManager updateManager = UpdateManager.INSTANCE;
                String stringExtra = intent.getStringExtra("android.content.pm.extra.STATUS_MESSAGE");
                if (stringExtra == null) {
                    stringExtra = "install failed (status " + intExtra + ")";
                }
                Intrinsics.checkNotNull(stringExtra);
                updateManager.onInstallFailed(context, intExtra, stringExtra);
                return;
            }
            if (Build.VERSION.SDK_INT >= 33) {
                intent2 = (Intent) intent.getParcelableExtra("android.intent.extra.INTENT", Intent.class);
            } else {
                intent2 = (Intent) intent.getParcelableExtra("android.intent.extra.INTENT");
            }
            if (intent2 != null) {
                intent2.addFlags(268435456);
            }
            UpdateManager.INSTANCE.onPendingUserAction(context, intent2);
            if (intent2 != null) {
                try {
                    Result.Companion companion = Result.INSTANCE;
                    InstallResultReceiver installResultReceiver = this;
                    context.startActivity(intent2);
                    Result.m189constructorimpl(Unit.INSTANCE);
                } catch (Throwable th) {
                    Result.Companion companion2 = Result.INSTANCE;
                    Result.m189constructorimpl(ResultKt.createFailure(th));
                }
            }
        }
    }
}
