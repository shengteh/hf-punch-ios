package my.haifu.punch;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlinx.coroutines.DebugKt;
import org.json.JSONArray;

/* JADX INFO: compiled from: Prefs.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000fJ\u0016\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0005J\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000e\u001a\u00020\u000fJ\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000e\u001a\u00020\u000fJ\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0019\u001a\u00020\u0005J\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u001b\u001a\u00020\u001cJ\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u000e\u001a\u00020\u000fJ\u0016\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020\u0005J\u0016\u0010 \u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0005J\u0018\u0010!\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\"\u001a\u0004\u0018\u00010\u0005J\u001c\u0010#\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004J\u0016\u0010%\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020\u0005J\u0016\u0010&\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010'\u001a\u00020\u0014J*\u0010(\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010)\u001a\u00020\u00052\b\u0010\u0016\u001a\u0004\u0018\u00010\u00052\b\u0010*\u001a\u0004\u0018\u00010\u0005J\u0010\u0010+\u001a\u00020,2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010*\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000e\u001a\u00020\u000fJ\u0010\u0010)\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010-\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010.\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\u000fR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082T¢\u0006\u0002\n\u0000¨\u0006/"}, d2 = {"Lmy/haifu/punch/Prefs;", "", "()V", "DEFAULT_REMINDERS", "", "", "getDEFAULT_REMINDERS", "()Ljava/util/List;", "FILE", "HHMM", "Lkotlin/text/Regex;", "MAX_REMINDERS", "", "baseUrl", "ctx", "Landroid/content/Context;", "clearOverrides", "", "clearWorker", "flag", "", "name", "fullName", ReminderScheduler.KIND_MORNING, "normalizeTime", "v", "parseTimes", "arr", "Lorg/json/JSONArray;", "reminders", "setBaseUrl", "url", "setFlag", "setMorning", "hhmm", "setReminders", "list", "setUpdateUrl", "setWebviewDebug", DebugKt.DEBUG_PROPERTY_VALUE_ON, "setWorker", "token", "staffId", "sp", "Landroid/content/SharedPreferences;", "updateUrl", "webviewDebug", "app_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Prefs {
    private static final String FILE = "hf_punch_shell";
    private static final int MAX_REMINDERS = 6;
    public static final Prefs INSTANCE = new Prefs();
    private static final List<String> DEFAULT_REMINDERS = CollectionsKt.listOf((Object[]) new String[]{"17:10", "20:30"});
    private static final Regex HHMM = new Regex("^([01]?\\d|2[0-3]):([0-5]\\d)$");

    private Prefs() {
    }

    public final List<String> getDEFAULT_REMINDERS() {
        return DEFAULT_REMINDERS;
    }

    private final SharedPreferences sp(Context ctx) {
        SharedPreferences sharedPreferences = ctx.getApplicationContext().getSharedPreferences(FILE, 0);
        Intrinsics.checkNotNullExpressionValue(sharedPreferences, "getSharedPreferences(...)");
        return sharedPreferences;
    }

    public final String token(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        String string = sp(ctx).getString("token", null);
        if (string == null || !(!StringsKt.isBlank(string))) {
            return null;
        }
        return string;
    }

    public final String fullName(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return sp(ctx).getString("full_name", null);
    }

    public final String staffId(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return sp(ctx).getString("staff_id", null);
    }

    public final void setWorker(Context ctx, String token, String fullName, String staffId) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(token, "token");
        sp(ctx).edit().putString("token", token).putString("full_name", fullName).putString("staff_id", staffId).apply();
    }

    public final void clearWorker(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        sp(ctx).edit().remove("token").remove("full_name").remove("staff_id").apply();
    }

    public final String baseUrl(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        String str = null;
        String string = sp(ctx).getString(MainActivity.EXTRA_BASE_URL, null);
        if (string != null && PrefsKt.isAllowedOverride(string)) {
            str = string;
        }
        return str == null ? BuildConfig.BASE_URL : str;
    }

    public final String updateUrl(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        String str = null;
        String string = sp(ctx).getString(MainActivity.EXTRA_UPDATE_URL, null);
        if (string != null && PrefsKt.isAllowedOverride(string)) {
            str = string;
        }
        return str == null ? BuildConfig.UPDATE_URL : str;
    }

    public final void setBaseUrl(Context ctx, String url) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(url, "url");
        sp(ctx).edit().putString(MainActivity.EXTRA_BASE_URL, url).apply();
    }

    public final void setUpdateUrl(Context ctx, String url) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(url, "url");
        sp(ctx).edit().putString(MainActivity.EXTRA_UPDATE_URL, url).apply();
    }

    public final boolean webviewDebug(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return sp(ctx).getBoolean(MainActivity.EXTRA_WV_DEBUG, false);
    }

    public final void setWebviewDebug(Context ctx, boolean on) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        sp(ctx).edit().putBoolean(MainActivity.EXTRA_WV_DEBUG, on).apply();
    }

    public final void clearOverrides(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        sp(ctx).edit().remove(MainActivity.EXTRA_BASE_URL).remove(MainActivity.EXTRA_UPDATE_URL).remove(MainActivity.EXTRA_WV_DEBUG).apply();
    }

    public final List<String> reminders(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        String string = sp(ctx).getString("reminders", null);
        if (string == null) {
            return DEFAULT_REMINDERS;
        }
        try {
            return parseTimes(new JSONArray(string));
        } catch (Throwable unused) {
            return DEFAULT_REMINDERS;
        }
    }

    public final void setReminders(Context ctx, List<String> list) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(list, "list");
        sp(ctx).edit().putString("reminders", new JSONArray((Collection) list).toString()).apply();
    }

    public final String morning(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        String string = sp(ctx).getString(ReminderScheduler.KIND_MORNING, null);
        if (string != null) {
            return INSTANCE.normalizeTime(string);
        }
        return null;
    }

    public final void setMorning(Context ctx, String hhmm) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        SharedPreferences.Editor editorEdit = sp(ctx).edit();
        if (hhmm == null) {
            editorEdit.remove(ReminderScheduler.KIND_MORNING);
        } else {
            editorEdit.putString(ReminderScheduler.KIND_MORNING, hhmm);
        }
        editorEdit.apply();
    }

    public final List<String> parseTimes(JSONArray arr) {
        String strNormalizeTime;
        Intrinsics.checkNotNullParameter(arr, "arr");
        ArrayList arrayList = new ArrayList();
        int iMin = Math.min(arr.length(), 24);
        for (int i = 0; i < iMin; i++) {
            Object objOpt = arr.opt(i);
            String str = objOpt instanceof String ? (String) objOpt : null;
            if (str != null && (strNormalizeTime = INSTANCE.normalizeTime(str)) != null) {
                if (!arrayList.contains(strNormalizeTime)) {
                    arrayList.add(strNormalizeTime);
                }
                if (arrayList.size() >= 6) {
                    break;
                }
            }
        }
        return arrayList;
    }

    public final String normalizeTime(String v) {
        Intrinsics.checkNotNullParameter(v, "v");
        MatchResult matchResultMatchEntire = HHMM.matchEntire(StringsKt.trim((CharSequence) v).toString());
        if (matchResultMatchEntire == null) {
            return null;
        }
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format(Locale.US, "%02d:%s", Arrays.copyOf(new Object[]{Integer.valueOf(Integer.parseInt(matchResultMatchEntire.getGroupValues().get(1))), matchResultMatchEntire.getGroupValues().get(2)}, 2));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return str;
    }

    public final boolean flag(Context ctx, String name) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(name, "name");
        return sp(ctx).getBoolean(name, false);
    }

    public final void setFlag(Context ctx, String name) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(name, "name");
        sp(ctx).edit().putBoolean(name, true).apply();
    }
}
