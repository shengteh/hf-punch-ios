package my.haifu.punch;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;

/* JADX INFO: compiled from: ReminderReceiver.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u00042\b\u0010\u0014\u001a\u0004\u0018\u00010\u0004J0\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001bH\u0002J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u0018\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010!\u001a\u00020\u00042\u0006\u0010\"\u001a\u00020\u000bJ2\u0010#\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010$\u001a\u00020\b2\u0006\u0010%\u001a\u00020\u00042\u0006\u0010&\u001a\u00020\u00042\b\u0010'\u001a\u0004\u0018\u00010\u0004H\u0003J.\u0010(\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082T¢\u0006\u0002\n\u0000R\u001b\u0010\n\u001a\u00020\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR\u001b\u0010\u0010\u001a\u00020\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u000f\u001a\u0004\b\u0011\u0010\r¨\u0006)"}, d2 = {"Lmy/haifu/punch/ReminderCheck;", "", "()V", "CHANNEL", "", "JSON_TYPE", "Lokhttp3/MediaType;", "NOTIF_IN", "", "NOTIF_OUT", "alarmHttp", "Lokhttp3/OkHttpClient;", "getAlarmHttp", "()Lokhttp3/OkHttpClient;", "alarmHttp$delegate", "Lkotlin/Lazy;", "manualHttp", "getManualHttp", "manualHttp$delegate", "clockText", "t", "decide", "ctx", "Landroid/content/Context;", ReminderScheduler.EXTRA_KIND, ReminderScheduler.EXTRA_SLOT, "isRetry", "", "fromAlarm", "ensureChannel", "", "fetchStatus", "Lorg/json/JSONObject;", "token", "http", "post", "id", "title", "text", "site", "run", "app_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReminderCheck {
    public static final String CHANNEL = "hf-punch-reminders";
    private static final int NOTIF_IN = 7102;
    private static final int NOTIF_OUT = 7101;
    public static final ReminderCheck INSTANCE = new ReminderCheck();
    private static final MediaType JSON_TYPE = MediaType.INSTANCE.get("application/json; charset=utf-8");

    /* JADX INFO: renamed from: alarmHttp$delegate, reason: from kotlin metadata */
    private static final Lazy alarmHttp = LazyKt.lazy(new Function0<OkHttpClient>() { // from class: my.haifu.punch.ReminderCheck$alarmHttp$2
        @Override // kotlin.jvm.functions.Function0
        public final OkHttpClient invoke() {
            return new OkHttpClient.Builder().connectTimeout(8L, TimeUnit.SECONDS).readTimeout(8L, TimeUnit.SECONDS).callTimeout(9L, TimeUnit.SECONDS).build();
        }
    });

    /* JADX INFO: renamed from: manualHttp$delegate, reason: from kotlin metadata */
    private static final Lazy manualHttp = LazyKt.lazy(new Function0<OkHttpClient>() { // from class: my.haifu.punch.ReminderCheck$manualHttp$2
        @Override // kotlin.jvm.functions.Function0
        public final OkHttpClient invoke() {
            return new OkHttpClient.Builder().connectTimeout(15L, TimeUnit.SECONDS).readTimeout(15L, TimeUnit.SECONDS).callTimeout(30L, TimeUnit.SECONDS).build();
        }
    });

    private ReminderCheck() {
    }

    private final OkHttpClient getAlarmHttp() {
        return (OkHttpClient) alarmHttp.getValue();
    }

    private final OkHttpClient getManualHttp() {
        return (OkHttpClient) manualHttp.getValue();
    }

    public final String run(Context ctx, String kind, String slot, boolean isRetry, boolean fromAlarm) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(slot, "slot");
        String strDecide = decide(ctx, kind, slot, isRetry, fromAlarm);
        String str = slot;
        if (str.length() == 0) {
            str = "-";
        }
        Log.i(PrefsKt.TAG, "reminder " + kind + " " + ((Object) str) + (isRetry ? " (retry)" : "") + ": " + strDecide);
        return strDecide;
    }

    private final String decide(Context ctx, String kind, String slot, boolean isRetry, boolean fromAlarm) {
        String strJsonStr;
        String str = Prefs.INSTANCE.token(ctx);
        if (str == null) {
            return "no worker on this phone";
        }
        if (Intrinsics.areEqual(kind, ReminderScheduler.KIND_MORNING)) {
            if (Prefs.INSTANCE.morning(ctx) == null) {
                return "morning reminder is off";
            }
            if (Calendar.getInstance(ReminderScheduler.INSTANCE.getTZ()).get(7) == 1) {
                return "Sunday — no morning reminder";
            }
        }
        JSONObject jSONObjectFetchStatus = fetchStatus(str, fromAlarm ? getAlarmHttp() : getManualHttp());
        String str2 = "";
        if (jSONObjectFetchStatus == null || !jSONObjectFetchStatus.optBoolean("ok", false)) {
            if (jSONObjectFetchStatus == null || (strJsonStr = PrefsKt.jsonStr(jSONObjectFetchStatus, "code", 60)) == null) {
                strJsonStr = "network error";
            }
            if (!isRetry) {
                ReminderScheduler.INSTANCE.scheduleRetry(ctx, kind, slot);
            }
            if (isRetry) {
                str2 = " — giving up";
            }
            return "status check failed (" + strJsonStr + ")" + str2;
        }
        String strJsonStr2 = PrefsKt.jsonStr(jSONObjectFetchStatus, "state", 20);
        str2 = strJsonStr2 != null ? strJsonStr2 : "";
        String strJsonStr3 = PrefsKt.jsonStr(jSONObjectFetchStatus, "site_name", 120);
        if (Intrinsics.areEqual(kind, ReminderScheduler.KIND_EVENING) && Intrinsics.areEqual(str2, "in")) {
            String strClockText = clockText(PrefsKt.jsonStr(jSONObjectFetchStatus, "in_at_local", 60));
            if (strClockText == null) {
                strClockText = "—";
            }
            String string = ctx.getString(R.string.remind_out_title);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            String string2 = ctx.getString(R.string.remind_out_text, strClockText);
            Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
            return post(ctx, NOTIF_OUT, string, string2, strJsonStr3);
        }
        if (Intrinsics.areEqual(kind, ReminderScheduler.KIND_MORNING) && Intrinsics.areEqual(str2, "out")) {
            String string3 = ctx.getString(R.string.remind_in_title);
            Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
            String string4 = ctx.getString(R.string.remind_in_text);
            Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
            return post(ctx, NOTIF_IN, string3, string4, strJsonStr3);
        }
        return "state=" + str2 + " — no reminder needed";
    }

    public final JSONObject fetchStatus(String token, OkHttpClient http) {
        JSONObject jSONObject;
        Intrinsics.checkNotNullParameter(token, "token");
        Intrinsics.checkNotNullParameter(http, "http");
        try {
            RequestBody.Companion companion = RequestBody.INSTANCE;
            String string = new JSONObject().put("p_device_token", token).toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            Response responseExecute = http.newCall(new Request.Builder().url("https://fwuftjunybbxlhwauxta.supabase.co/rest/v1/rpc/get_my_status").header("apikey", BuildConfig.SUPABASE_KEY).header("Authorization", "Bearer sb_publishable_mkW7DcSITuXpOGtZHTGFjQ_ZNczYv1t").post(companion.create(string, JSON_TYPE)).build()).execute();
            try {
                Response response = responseExecute;
                if (response.isSuccessful()) {
                    ResponseBody responseBodyBody = response.body();
                    String strString = responseBodyBody != null ? responseBodyBody.string() : null;
                    if (strString == null) {
                        strString = "";
                    }
                    jSONObject = strString.length() > 262144 ? null : new JSONObject(strString);
                }
                CloseableKt.closeFinally(responseExecute, null);
                return jSONObject;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(responseExecute, th);
                    throw th2;
                }
            }
        } catch (Throwable unused) {
            return null;
        }
    }

    public final String clockText(String t) {
        String str = t;
        if (str == null || StringsKt.isBlank(str)) {
            return null;
        }
        String string = StringsKt.trim((CharSequence) str).toString();
        String str2 = string;
        MatchResult matchResultFind$default = Regex.find$default(new Regex("T(\\d{1,2}):(\\d{2})"), str2, 0, 2, null);
        if (matchResultFind$default == null && (matchResultFind$default = Regex.find$default(new Regex("(\\d{1,2}):(\\d{2})"), str2, 0, 2, null)) == null) {
            return StringsKt.take(string, 24);
        }
        int i = Integer.parseInt(matchResultFind$default.getGroupValues().get(1));
        int i2 = Integer.parseInt(matchResultFind$default.getGroupValues().get(2));
        Locale US = Locale.US;
        Intrinsics.checkNotNullExpressionValue(US, "US");
        String lowerCase = string.toLowerCase(US);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        String str3 = lowerCase;
        if (StringsKt.contains$default((CharSequence) str3, (CharSequence) "pm", false, 2, (Object) null) && i < 12) {
            i += 12;
        }
        if (StringsKt.contains$default((CharSequence) str3, (CharSequence) "am", false, 2, (Object) null) && i == 12) {
            i = 0;
        }
        int i3 = i % 12;
        if (i3 == 0) {
            i3 = 12;
        }
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str4 = String.format(Locale.US, "%d:%02d %s", Arrays.copyOf(new Object[]{Integer.valueOf(i3), Integer.valueOf(i2), i % 24 < 12 ? "AM" : "PM"}, 3));
        Intrinsics.checkNotNullExpressionValue(str4, "format(...)");
        return str4;
    }

    private final String post(Context ctx, int id, String title, String text, String site) {
        String str;
        if (Build.VERSION.SDK_INT >= 33 && ContextCompat.checkSelfPermission(ctx, "android.permission.POST_NOTIFICATIONS") != 0) {
            return "should remind, but notifications are not allowed on this phone";
        }
        ensureChannel(ctx);
        String str2 = site;
        if (str2 == null || StringsKt.isBlank(str2)) {
            str = text;
        } else {
            str = text + "\n" + site;
        }
        Notification notificationBuild = new NotificationCompat.Builder(ctx, CHANNEL).setSmallIcon(R.drawable.ic_stat_punch).setColor(-13668388).setContentTitle(title).setContentText(text).setStyle(new NotificationCompat.BigTextStyle().bigText(str)).setPriority(1).setCategory(NotificationCompat.CATEGORY_REMINDER).setVisibility(1).setDefaults(-1).setAutoCancel(true).setContentIntent(PendingIntent.getActivity(ctx, id, new Intent(ctx, (Class<?>) MainActivity.class).addFlags(335544320), ReminderScheduler.INSTANCE.immutableFlag() | 134217728)).build();
        Intrinsics.checkNotNullExpressionValue(notificationBuild, "build(...)");
        try {
            NotificationManagerCompat.from(ctx).notify(id, notificationBuild);
            return "notified: " + title;
        } catch (SecurityException e) {
            return "notify refused: " + e.getMessage();
        }
    }

    private final void ensureChannel(Context ctx) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        Object systemService = ctx.getSystemService("notification");
        NotificationManager notificationManager = systemService instanceof NotificationManager ? (NotificationManager) systemService : null;
        if (notificationManager != null && notificationManager.getNotificationChannel(CHANNEL) == null) {
            MainActivity$$ExternalSyntheticApiModelOutline0.m();
            NotificationChannel notificationChannelM = MainActivity$$ExternalSyntheticApiModelOutline0.m(CHANNEL, ctx.getString(R.string.channel_reminders), 4);
            notificationChannelM.enableVibration(true);
            notificationChannelM.setShowBadge(true);
            notificationManager.createNotificationChannel(notificationChannelM);
        }
    }
}
