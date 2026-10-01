package my.haifu.punch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ReminderReceiver.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lmy/haifu/punch/ReminderReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "app_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReminderReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(intent, "intent");
        if (Intrinsics.areEqual(intent.getAction(), ReminderScheduler.ACTION)) {
            final Context applicationContext = context.getApplicationContext();
            String stringExtra = intent.getStringExtra(ReminderScheduler.EXTRA_KIND);
            if (stringExtra == null) {
                stringExtra = ReminderScheduler.KIND_EVENING;
            }
            final String str = stringExtra;
            String stringExtra2 = intent.getStringExtra(ReminderScheduler.EXTRA_SLOT);
            if (stringExtra2 == null) {
                stringExtra2 = "";
            }
            final String str2 = stringExtra2;
            final boolean booleanExtra = intent.getBooleanExtra(ReminderScheduler.EXTRA_RETRY, false);
            final BroadcastReceiver.PendingResult pendingResultGoAsync = goAsync();
            new Thread(new Runnable() { // from class: my.haifu.punch.ReminderReceiver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ReminderReceiver.onReceive$lambda$0(booleanExtra, applicationContext, str, str2, pendingResultGoAsync);
                }
            }, "hf-reminder").start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onReceive$lambda$0(boolean z, Context context, String kind, String slot, BroadcastReceiver.PendingResult pendingResult) {
        Intrinsics.checkNotNullParameter(kind, "$kind");
        Intrinsics.checkNotNullParameter(slot, "$slot");
        if (!z) {
            try {
                ReminderScheduler reminderScheduler = ReminderScheduler.INSTANCE;
                Intrinsics.checkNotNull(context);
                reminderScheduler.scheduleAll(context);
            } catch (Throwable th) {
                try {
                    Log.w(PrefsKt.TAG, "reminder check crashed: " + th);
                } finally {
                    pendingResult.finish();
                }
            }
        }
        ReminderCheck reminderCheck = ReminderCheck.INSTANCE;
        Intrinsics.checkNotNull(context);
        reminderCheck.run(context, kind, slot, z, true);
    }
}
