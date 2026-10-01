package my.haifu.punch;

import android.net.Uri;
import java.util.Locale;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;

/* JADX INFO: compiled from: Prefs.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\u001a\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0002\u001a\"\u0010\t\u001a\u0004\u0018\u00010\u00022\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00022\b\b\u0002\u0010\r\u001a\u00020\u000e\u001a\u0012\u0010\u000f\u001a\u0004\u0018\u00010\u00022\b\u0010\b\u001a\u0004\u0018\u00010\u0002\u001a\u000e\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u0002\"\u0017\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004\"\u000e\u0010\u0005\u001a\u00020\u0002X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"LOCAL_HOSTS", "", "", "getLOCAL_HOSTS", "()Ljava/util/Set;", "TAG", "isAllowedOverride", "", "url", "jsonStr", "o", "Lorg/json/JSONObject;", "key", "max", "", "originOf", "validToken", "t", "app_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PrefsKt {
    private static final Set<String> LOCAL_HOSTS = SetsKt.setOf((Object[]) new String[]{"10.0.2.2", "127.0.0.1", "localhost"});
    public static final String TAG = "HFPunch";

    public static final Set<String> getLOCAL_HOSTS() {
        return LOCAL_HOSTS;
    }

    public static final String originOf(String str) {
        String host;
        String str2 = str;
        if (str2 != null && !StringsKt.isBlank(str2)) {
            try {
                Uri uri = Uri.parse(StringsKt.trim((CharSequence) str).toString());
                String scheme = uri.getScheme();
                if (scheme != null) {
                    Locale US = Locale.US;
                    Intrinsics.checkNotNullExpressionValue(US, "US");
                    String lowerCase = scheme.toLowerCase(US);
                    Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                    if (lowerCase != null && ((Intrinsics.areEqual(lowerCase, "http") || Intrinsics.areEqual(lowerCase, "https")) && (host = uri.getHost()) != null)) {
                        Locale US2 = Locale.US;
                        Intrinsics.checkNotNullExpressionValue(US2, "US");
                        String lowerCase2 = host.toLowerCase(US2);
                        Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
                        if (lowerCase2 != null) {
                            if (lowerCase2.length() <= 0) {
                                lowerCase2 = null;
                            }
                            if (lowerCase2 != null) {
                                int port = uri.getPort();
                                int i = Intrinsics.areEqual(lowerCase, "https") ? 443 : 80;
                                if (port == -1 || port == i) {
                                    return lowerCase + "://" + lowerCase2;
                                }
                                return lowerCase + "://" + lowerCase2 + ":" + port;
                            }
                        }
                    }
                }
            } catch (Throwable unused) {
            }
        }
        return null;
    }

    public static final boolean isAllowedOverride(String url) {
        String host;
        String lowerCase;
        Intrinsics.checkNotNullParameter(url, "url");
        if (url.length() > 512) {
            return false;
        }
        try {
            Uri uri = Uri.parse(StringsKt.trim((CharSequence) url).toString());
            String scheme = uri.getScheme();
            if (scheme == null) {
                return false;
            }
            Locale US = Locale.US;
            Intrinsics.checkNotNullExpressionValue(US, "US");
            String lowerCase2 = scheme.toLowerCase(US);
            Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
            if (lowerCase2 == null || (host = uri.getHost()) == null) {
                return false;
            }
            Locale US2 = Locale.US;
            Intrinsics.checkNotNullExpressionValue(US2, "US");
            String lowerCase3 = host.toLowerCase(US2);
            Intrinsics.checkNotNullExpressionValue(lowerCase3, "toLowerCase(...)");
            if (lowerCase3 == null) {
                return false;
            }
            if (LOCAL_HOSTS.contains(lowerCase3)) {
                return Intrinsics.areEqual(lowerCase2, "http") || Intrinsics.areEqual(lowerCase2, "https");
            }
            if (!Intrinsics.areEqual(lowerCase2, "https")) {
                return false;
            }
            String host2 = Uri.parse(BuildConfig.BASE_URL).getHost();
            if (host2 != null) {
                Locale US3 = Locale.US;
                Intrinsics.checkNotNullExpressionValue(US3, "US");
                lowerCase = host2.toLowerCase(US3);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            } else {
                lowerCase = null;
            }
            return Intrinsics.areEqual(lowerCase3, lowerCase);
        } catch (Throwable unused) {
            return false;
        }
    }

    public static final boolean validToken(String t) {
        Intrinsics.checkNotNullParameter(t, "t");
        int length = t.length();
        if (8 > length || length >= 513) {
            return false;
        }
        String str = t;
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if ('!' > cCharAt || cCharAt >= 127 || cCharAt == '\"' || cCharAt == '\\') {
                return false;
            }
        }
        return true;
    }

    public static final String jsonStr(JSONObject o, String key, int i) {
        Intrinsics.checkNotNullParameter(o, "o");
        Intrinsics.checkNotNullParameter(key, "key");
        Object objOpt = o.opt(key);
        if (objOpt instanceof String) {
            String strTake = StringsKt.take(StringsKt.trim((CharSequence) objOpt).toString(), i);
            return strTake.length() != 0 ? strTake : null;
        }
        if (objOpt instanceof Number) {
            return StringsKt.take(objOpt.toString(), i);
        }
        return null;
    }

    public static /* synthetic */ String jsonStr$default(JSONObject jSONObject, String str, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            i = 200;
        }
        return jsonStr(jSONObject, str, i);
    }
}
