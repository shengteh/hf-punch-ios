package my.haifu.punch;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: ReminderScheduler.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u000bH\u0002J\u000e\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u0018\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0006\u0010\u001f\u001a\u00020\u000bJ\u0016\u0010 \u001a\u00020\u00112\u0006\u0010!\u001a\u00020\u00042\u0006\u0010\"\u001a\u00020\u0011J0\u0010#\u001a\u00020$2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u000b2\u0006\u0010%\u001a\u00020\u00042\u0006\u0010&\u001a\u00020\u00042\u0006\u0010'\u001a\u00020(H\u0002J\u000e\u0010)\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u001e\u0010*\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010%\u001a\u00020\u00042\u0006\u0010&\u001a\u00020\u0004J \u0010+\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010,\u001a\u00020\u00112\u0006\u0010-\u001a\u00020$H\u0003J\u0010\u0010.\u001a\u00020\u00042\u0006\u0010,\u001a\u00020\u0011H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000bX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000bX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082T¢\u0006\u0002\n\u0000R\u0011\u0010\u0012\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015¨\u0006/"}, d2 = {"Lmy/haifu/punch/ReminderScheduler;", "", "()V", "ACTION", "", "EXTRA_KIND", "EXTRA_RETRY", "EXTRA_SLOT", "KIND_EVENING", "KIND_MORNING", "MAX_EVENING", "", "RC_EVENING", "RC_MORNING", "RC_RETRY_EVENING", "RC_RETRY_MORNING", "RETRY_DELAY_MS", "", "TZ", "Ljava/util/TimeZone;", "getTZ", "()Ljava/util/TimeZone;", "cancel", "", "ctx", "Landroid/content/Context;", "am", "Landroid/app/AlarmManager;", "rc", "cancelAll", "cancelSlots", "immutableFlag", "nextAt", "hhmm", "now", "pending", "Landroid/app/PendingIntent;", ReminderScheduler.EXTRA_KIND, ReminderScheduler.EXTRA_SLOT, ReminderScheduler.EXTRA_RETRY, "", "scheduleAll", "scheduleRetry", "set", "at", "pi", "stamp", "app_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReminderScheduler {
    public static final String ACTION = "my.haifu.punch.REMINDER";
    public static final String EXTRA_KIND = "kind";
    public static final String EXTRA_RETRY = "retry";
    public static final String EXTRA_SLOT = "slot";
    public static final ReminderScheduler INSTANCE = new ReminderScheduler();
    public static final String KIND_EVENING = "evening";
    public static final String KIND_MORNING = "morning";
    private static final int MAX_EVENING = 6;
    private static final int RC_EVENING = 100;
    private static final int RC_MORNING = 200;
    private static final int RC_RETRY_EVENING = 300;
    private static final int RC_RETRY_MORNING = 301;
    private static final long RETRY_DELAY_MS = 600000;
    private static final TimeZone TZ;

    private ReminderScheduler() {
    }

    static {
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Kuala_Lumpur");
        Intrinsics.checkNotNullExpressionValue(timeZone, "getTimeZone(...)");
        TZ = timeZone;
    }

    public final TimeZone getTZ() {
        return TZ;
    }

    public final synchronized void scheduleAll(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Context applicationContext = ctx.getApplicationContext();
        Object systemService = applicationContext.getSystemService(NotificationCompat.CATEGORY_ALARM);
        AlarmManager alarmManager = systemService instanceof AlarmManager ? (AlarmManager) systemService : null;
        if (alarmManager == null) {
            return;
        }
        Intrinsics.checkNotNull(applicationContext);
        cancelSlots(applicationContext, alarmManager);
        if (Prefs.INSTANCE.token(applicationContext) == null) {
            Log.i(PrefsKt.TAG, "reminders: no worker on this phone — nothing armed");
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        ArrayList arrayList = new ArrayList();
        Iterator it = CollectionsKt.take(Prefs.INSTANCE.reminders(applicationContext), 6).iterator();
        int i = 0;
        while (it.hasNext()) {
            Object next = it.next();
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            String str = (String) next;
            ReminderScheduler reminderScheduler = INSTANCE;
            long jNextAt = reminderScheduler.nextAt(str, jCurrentTimeMillis);
            reminderScheduler.set(alarmManager, jNextAt, reminderScheduler.pending(applicationContext, i + 100, KIND_EVENING, str, false));
            arrayList.add(str + "→" + reminderScheduler.stamp(jNextAt));
            it = it;
            i = i2;
        }
        String strMorning = Prefs.INSTANCE.morning(applicationContext);
        if (strMorning != null) {
            ReminderScheduler reminderScheduler2 = INSTANCE;
            long jNextAt2 = reminderScheduler2.nextAt(strMorning, jCurrentTimeMillis);
            reminderScheduler2.set(alarmManager, jNextAt2, reminderScheduler2.pending(applicationContext, RC_MORNING, KIND_MORNING, strMorning, false));
            arrayList.add("morning " + strMorning + "→" + reminderScheduler2.stamp(jNextAt2));
        }
        Log.i(PrefsKt.TAG, "reminders armed: " + (arrayList.isEmpty() ? "none" : CollectionsKt.joinToString$default(arrayList, ", ", null, null, 0, null, null, 62, null)));
    }

    public final synchronized void cancelAll(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Context applicationContext = ctx.getApplicationContext();
        Object systemService = applicationContext.getSystemService(NotificationCompat.CATEGORY_ALARM);
        AlarmManager alarmManager = systemService instanceof AlarmManager ? (AlarmManager) systemService : null;
        if (alarmManager == null) {
            return;
        }
        Intrinsics.checkNotNull(applicationContext);
        cancelSlots(applicationContext, alarmManager);
        cancel(applicationContext, alarmManager, RC_RETRY_EVENING);
        cancel(applicationContext, alarmManager, RC_RETRY_MORNING);
    }

    public final void scheduleRetry(Context ctx, String kind, String slot) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(slot, "slot");
        Context applicationContext = ctx.getApplicationContext();
        Object systemService = applicationContext.getSystemService(NotificationCompat.CATEGORY_ALARM);
        AlarmManager alarmManager = systemService instanceof AlarmManager ? (AlarmManager) systemService : null;
        if (alarmManager == null) {
            return;
        }
        int i = Intrinsics.areEqual(kind, KIND_MORNING) ? RC_RETRY_MORNING : RC_RETRY_EVENING;
        long jCurrentTimeMillis = System.currentTimeMillis() + RETRY_DELAY_MS;
        Intrinsics.checkNotNull(applicationContext);
        set(alarmManager, jCurrentTimeMillis, pending(applicationContext, i, kind, slot, true));
        Log.i(PrefsKt.TAG, "reminder " + kind + " " + slot + ": status check failed — one retry in 10 min");
    }

    public final long nextAt(String hhmm, long now) {
        Intrinsics.checkNotNullParameter(hhmm, "hhmm");
        List listSplit$default = StringsKt.split$default((CharSequence) hhmm, new String[]{":"}, false, 0, 6, (Object) null);
        Calendar calendar = Calendar.getInstance(TZ);
        calendar.setTimeInMillis(now);
        calendar.set(11, Integer.parseInt((String) listSplit$default.get(0)));
        calendar.set(12, Integer.parseInt((String) listSplit$default.get(1)));
        calendar.set(13, 0);
        calendar.set(14, 0);
        if (calendar.getTimeInMillis() <= now + 30000) {
            calendar.add(6, 1);
        }
        return calendar.getTimeInMillis();
    }

    private final void cancelSlots(Context ctx, AlarmManager am) {
        for (int i = 0; i < 6; i++) {
            cancel(ctx, am, i + 100);
        }
        cancel(ctx, am, RC_MORNING);
    }

    private final void cancel(Context ctx, AlarmManager am, int rc) {
        Intent action = new Intent(ctx, (Class<?>) ReminderReceiver.class).setAction(ACTION);
        Intrinsics.checkNotNullExpressionValue(action, "setAction(...)");
        PendingIntent broadcast = PendingIntent.getBroadcast(ctx, rc, action, 536870912 | immutableFlag());
        if (broadcast != null) {
            am.cancel(broadcast);
            broadcast.cancel();
        }
    }

    private final PendingIntent pending(Context ctx, int rc, String kind, String slot, boolean retry) {
        Intent intentPutExtra = new Intent(ctx, (Class<?>) ReminderReceiver.class).setAction(ACTION).putExtra(EXTRA_KIND, kind).putExtra(EXTRA_SLOT, slot).putExtra(EXTRA_RETRY, retry);
        Intrinsics.checkNotNullExpressionValue(intentPutExtra, "putExtra(...)");
        PendingIntent broadcast = PendingIntent.getBroadcast(ctx, rc, intentPutExtra, 134217728 | immutableFlag());
        Intrinsics.checkNotNullExpressionValue(broadcast, "getBroadcast(...)");
        return broadcast;
    }

    private final void set(AlarmManager am, long at, PendingIntent pi) {
        try {
            try {
                if (Build.VERSION.SDK_INT >= 31) {
                    am.setAndAllowWhileIdle(0, at, pi);
                } else if (Build.VERSION.SDK_INT >= 23) {
                    am.setExactAndAllowWhileIdle(0, at, pi);
                } else {
                    am.setExact(0, at, pi);
                }
            } catch (Throwable unused) {
                am.set(0, at, pi);
            }
        } catch (Throwable unused2) {
        }
    }

    public final int immutableFlag() {
        if (Build.VERSION.SDK_INT >= 23) {
            return AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL;
        }
        return 0;
    }

    private final String stamp(long at) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd/MM HH:mm", Locale.US);
        simpleDateFormat.setTimeZone(TZ);
        String str = simpleDateFormat.format(new Date(at));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return str;
    }
}
