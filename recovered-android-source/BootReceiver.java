package my.haifu.punch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BootReceiver.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lmy/haifu/punch/BootReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "app_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BootReceiver extends BroadcastReceiver {
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(intent, "intent");
        String action = intent.getAction();
        if (action != null) {
            switch (action.hashCode()) {
                case -1787487905:
                    if (!action.equals("android.intent.action.QUICKBOOT_POWERON")) {
                        return;
                    }
                    break;
                case -1417835046:
                    if (!action.equals("com.htc.intent.action.QUICKBOOT_POWERON")) {
                        return;
                    }
                    break;
                case 502473491:
                    if (!action.equals("android.intent.action.TIMEZONE_CHANGED")) {
                        return;
                    }
                    break;
                case 505380757:
                    if (!action.equals("android.intent.action.TIME_SET")) {
                        return;
                    }
                    break;
                case 798292259:
                    if (!action.equals("android.intent.action.BOOT_COMPLETED")) {
                        return;
                    }
                    break;
                case 1737074039:
                    if (!action.equals("android.intent.action.MY_PACKAGE_REPLACED")) {
                        return;
                    }
                    break;
                default:
                    return;
            }
            Log.i(PrefsKt.TAG, "re-arming reminders after " + intent.getAction());
            try {
                ReminderScheduler.INSTANCE.scheduleAll(context);
            } catch (Throwable th) {
                Log.w(PrefsKt.TAG, "re-arm failed: " + th);
            }
        }
    }
}
