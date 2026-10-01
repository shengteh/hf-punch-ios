package my.haifu.punch;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: compiled from: UpdateManager.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u000b\bÆ\u0002\u0018\u00002\u00020\u0001:\u0003PQRB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u000e2\u0006\u0010+\u001a\u00020,H\u0002J\u0010\u0010-\u001a\u00020\u001f2\u0006\u0010*\u001a\u00020\u000eH\u0002J\u0010\u0010.\u001a\u00020)2\u0006\u0010*\u001a\u00020\u000eH\u0002J\u000e\u0010/\u001a\u00020)2\u0006\u0010*\u001a\u00020\u000eJ\u000e\u00100\u001a\u00020)2\u0006\u00101\u001a\u00020\u0016J\u0018\u00102\u001a\u00020)2\u0006\u0010*\u001a\u00020\u000e2\u0006\u00103\u001a\u00020\u001cH\u0002J\n\u00104\u001a\u0004\u0018\u00010\u0004H\u0002J\u001a\u00105\u001a\u0004\u0018\u00010\u001c2\u0006\u0010*\u001a\u00020\u000e2\u0006\u00106\u001a\u000207H\u0002J\u0010\u00108\u001a\u00020\u00042\u0006\u00109\u001a\u00020:H\u0002J\u001a\u0010;\u001a\u0004\u0018\u00010\u001c2\u0006\u0010*\u001a\u00020\u000e2\u0006\u0010<\u001a\u00020\u0004H\u0002J\u0010\u0010=\u001a\u0002072\u0006\u0010*\u001a\u00020\u000eH\u0002J\u0018\u0010>\u001a\u00020)2\u0006\u0010*\u001a\u00020\u000e2\u0006\u00103\u001a\u00020\u001cH\u0002J\u000e\u0010?\u001a\u00020)2\u0006\u0010*\u001a\u00020\u000eJ\u001e\u0010@\u001a\u00020)2\u0006\u0010*\u001a\u00020\u000e2\u0006\u0010A\u001a\u00020\t2\u0006\u0010<\u001a\u00020\u0004J\u000e\u0010B\u001a\u00020)2\u0006\u0010*\u001a\u00020\u000eJ\u0018\u0010C\u001a\u00020)2\u0006\u0010*\u001a\u00020\u000e2\b\u0010D\u001a\u0004\u0018\u00010\u001aJ\u000e\u0010E\u001a\u00020)2\u0006\u0010*\u001a\u00020\u000eJ\u000e\u0010F\u001a\u00020)2\u0006\u0010G\u001a\u00020HJ\u0010\u0010I\u001a\u00020)2\u0006\u0010G\u001a\u00020HH\u0002J\u000e\u0010J\u001a\u00020)2\u0006\u00101\u001a\u00020\u0016J\u0010\u0010K\u001a\u00020\u00042\u0006\u0010L\u001a\u00020\u001cH\u0002J\u001a\u0010M\u001a\u00020)2\b\u00109\u001a\u0004\u0018\u00010\u00042\u0006\u0010N\u001a\u00020#H\u0002J\u000e\u0010O\u001a\u00020)2\u0006\u0010*\u001a\u00020\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u000f\u001a\u00020\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020#X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010$\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010%\u001a\n '*\u0004\u0018\u00010&0&X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006S"}, d2 = {"Lmy/haifu/punch/UpdateManager;", "", "()V", "APK_NAME", "", "INITIAL_DELAY_MS", "", "MAX_APK_BYTES", "MAX_JSON_BYTES", "", "PERIOD_MS", "SHA_RE", "Lkotlin/text/Regex;", "app", "Landroid/content/Context;", "http", "Lokhttp3/OkHttpClient;", "getHttp", "()Lokhttp3/OkHttpClient;", "http$delegate", "Lkotlin/Lazy;", "listener", "Lmy/haifu/punch/UpdateManager$Listener;", "main", "Landroid/os/Handler;", "pendingConfirm", "Landroid/content/Intent;", "readyApk", "Ljava/io/File;", "readyCode", "required", "", "running", "started", "tap", "Lmy/haifu/punch/UpdateManager$Tap;", "text", "work", "Ljava/util/concurrent/ExecutorService;", "kotlin.jvm.PlatformType", "applyRemoteConfig", "", "ctx", "o", "Lorg/json/JSONObject;", "canInstall", "check", "checkNow", "clearListener", "l", "commitSession", "apk", "composed", "download", "meta", "Lmy/haifu/punch/UpdateManager$Meta;", "errText", "t", "", "failed", NotificationCompat.CATEGORY_MESSAGE, "fetchLatest", "install", "onAppStart", "onInstallFailed", NotificationCompat.CATEGORY_STATUS, "onInstalled", "onPendingUserAction", "confirm", "onResume", "onStripTap", "act", "Landroid/app/Activity;", "openUnknownSources", "setListener", "sha256", "f", "show", "action", "startPeriodic", "Listener", "Meta", "Tap", "app_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UpdateManager {
    private static final String APK_NAME = "hf-punch-update.apk";
    private static final long INITIAL_DELAY_MS = 5000;
    private static final long MAX_APK_BYTES = 83886080;
    private static final int MAX_JSON_BYTES = 65536;
    private static final long PERIOD_MS = 86400000;
    private static volatile Context app;
    private static volatile Listener listener;
    private static volatile Intent pendingConfirm;
    private static volatile File readyApk;
    private static volatile int readyCode;
    private static volatile boolean required;
    private static volatile boolean running;
    private static volatile boolean started;
    private static volatile String text;
    public static final UpdateManager INSTANCE = new UpdateManager();
    private static final Regex SHA_RE = new Regex("^[0-9a-f]{64}$");

    /* JADX INFO: renamed from: http$delegate, reason: from kotlin metadata */
    private static final Lazy http = LazyKt.lazy(new Function0<OkHttpClient>() { // from class: my.haifu.punch.UpdateManager$http$2
        @Override // kotlin.jvm.functions.Function0
        public final OkHttpClient invoke() {
            return new OkHttpClient.Builder().connectTimeout(15L, TimeUnit.SECONDS).readTimeout(60L, TimeUnit.SECONDS).callTimeout(15L, TimeUnit.MINUTES).build();
        }
    });
    private static final ExecutorService work = Executors.newSingleThreadExecutor(new ThreadFactory() { // from class: my.haifu.punch.UpdateManager$$ExternalSyntheticLambda6
        @Override // java.util.concurrent.ThreadFactory
        public final Thread newThread(Runnable runnable) {
            return UpdateManager.work$lambda$0(runnable);
        }
    });
    private static final Handler main = new Handler(Looper.getMainLooper());
    private static volatile Tap tap = Tap.CHECK;

    /* JADX INFO: compiled from: UpdateManager.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&¨\u0006\u0006"}, d2 = {"Lmy/haifu/punch/UpdateManager$Listener;", "", "onUpdateMessage", "", "text", "", "app_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface Listener {
        void onUpdateMessage(String text);
    }

    /* JADX INFO: compiled from: UpdateManager.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lmy/haifu/punch/UpdateManager$Tap;", "", "(Ljava/lang/String;I)V", "CHECK", "ALLOW_INSTALLS", "INSTALL", "app_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private enum Tap {
        CHECK,
        ALLOW_INSTALLS,
        INSTALL;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<Tap> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: UpdateManager.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Tap.values().length];
            try {
                iArr[Tap.ALLOW_INSTALLS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Tap.INSTALL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Tap.CHECK.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private UpdateManager() {
    }

    private final OkHttpClient getHttp() {
        return (OkHttpClient) http.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Thread work$lambda$0(Runnable runnable) {
        return new Thread(runnable, "hf-update");
    }

    public final void setListener(Listener l) {
        Intrinsics.checkNotNullParameter(l, "l");
        listener = l;
        l.onUpdateMessage(composed());
    }

    public final void clearListener(Listener l) {
        Intrinsics.checkNotNullParameter(l, "l");
        if (listener == l) {
            listener = null;
        }
    }

    public final void onAppStart(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        app = ctx.getApplicationContext();
        if (running || readyApk != null) {
            return;
        }
        try {
            Result.Companion companion = Result.INSTANCE;
            UpdateManager updateManager = this;
            Result.m189constructorimpl(Boolean.valueOf(new File(ctx.getCacheDir(), APK_NAME).delete()));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            Result.m189constructorimpl(ResultKt.createFailure(th));
        }
    }

    public final void startPeriodic(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        app = ctx.getApplicationContext();
        if (started) {
            return;
        }
        started = true;
        main.postDelayed(new AnonymousClass1(), INITIAL_DELAY_MS);
    }

    /* JADX INFO: renamed from: my.haifu.punch.UpdateManager$startPeriodic$1, reason: invalid class name */
    /* JADX INFO: compiled from: UpdateManager.kt */
    @Metadata(d1 = {"\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016¨\u0006\u0004"}, d2 = {"my/haifu/punch/UpdateManager$startPeriodic$1", "Ljava/lang/Runnable;", "run", "", "app_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AnonymousClass1 implements Runnable {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void run$lambda$1$lambda$0(Context c) {
            Intrinsics.checkNotNullParameter(c, "$c");
            UpdateManager.INSTANCE.check(c);
        }

        @Override // java.lang.Runnable
        public void run() {
            final Context context = UpdateManager.app;
            if (context != null) {
                UpdateManager.work.execute(new Runnable() { // from class: my.haifu.punch.UpdateManager$startPeriodic$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        UpdateManager.AnonymousClass1.run$lambda$1$lambda$0(context);
                    }
                });
            }
            UpdateManager.main.postDelayed(this, UpdateManager.PERIOD_MS);
        }
    }

    public final void checkNow(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        final Context applicationContext = ctx.getApplicationContext();
        app = applicationContext;
        work.execute(new Runnable() { // from class: my.haifu.punch.UpdateManager$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                UpdateManager.checkNow$lambda$2(applicationContext);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkNow$lambda$2(Context context) {
        UpdateManager updateManager = INSTANCE;
        Intrinsics.checkNotNull(context);
        updateManager.check(context);
    }

    public final void onResume(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        final File file = readyApk;
        if (file != null && tap == Tap.ALLOW_INSTALLS && canInstall(ctx)) {
            final Context applicationContext = ctx.getApplicationContext();
            work.execute(new Runnable() { // from class: my.haifu.punch.UpdateManager$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    UpdateManager.onResume$lambda$3(applicationContext, file);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onResume$lambda$3(Context context, File apk) {
        Intrinsics.checkNotNullParameter(apk, "$apk");
        UpdateManager updateManager = INSTANCE;
        Intrinsics.checkNotNull(context);
        updateManager.install(context, apk);
    }

    public final void onStripTap(Activity act) {
        Object objM189constructorimpl;
        Intrinsics.checkNotNullParameter(act, "act");
        final Context applicationContext = act.getApplicationContext();
        int i = WhenMappings.$EnumSwitchMapping$0[tap.ordinal()];
        if (i == 1) {
            openUnknownSources(act);
            return;
        }
        if (i != 2) {
            if (i != 3) {
                return;
            }
            Intrinsics.checkNotNull(applicationContext);
            checkNow(applicationContext);
            return;
        }
        Intent intent = pendingConfirm;
        if (intent != null) {
            try {
                Result.Companion companion = Result.INSTANCE;
                UpdateManager updateManager = this;
                act.startActivity(intent);
                objM189constructorimpl = Result.m189constructorimpl(Unit.INSTANCE);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.INSTANCE;
                objM189constructorimpl = Result.m189constructorimpl(ResultKt.createFailure(th));
            }
            if (Result.m196isSuccessimpl(objM189constructorimpl)) {
                return;
            }
        }
        pendingConfirm = null;
        final File file = readyApk;
        if (file != null && file.exists()) {
            work.execute(new Runnable() { // from class: my.haifu.punch.UpdateManager$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    UpdateManager.onStripTap$lambda$5(applicationContext, file);
                }
            });
        } else {
            Intrinsics.checkNotNull(applicationContext);
            checkNow(applicationContext);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onStripTap$lambda$5(Context context, File file) {
        UpdateManager updateManager = INSTANCE;
        Intrinsics.checkNotNull(context);
        updateManager.install(context, file);
    }

    public final void onPendingUserAction(Context ctx, Intent confirm) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        app = ctx.getApplicationContext();
        pendingConfirm = confirm;
        Log.i(PrefsKt.TAG, "update: waiting for the person to tap Update");
        show(ctx.getString(R.string.upd_install), Tap.INSTALL);
    }

    public final void onInstalled(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        app = ctx.getApplicationContext();
        pendingConfirm = null;
        readyApk = null;
        Log.i(PrefsKt.TAG, "update: installed");
        show(null, Tap.CHECK);
    }

    public final void onInstallFailed(Context ctx, int status, String msg) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(msg, "msg");
        app = ctx.getApplicationContext();
        pendingConfirm = null;
        Log.w(PrefsKt.TAG, "update: install status " + status + " — " + msg);
        if (status == 3) {
            show(ctx.getString(R.string.upd_install), Tap.INSTALL);
            return;
        }
        String string = ctx.getString(R.string.upd_failed, StringsKt.take(StringsKt.replace$default(msg, "\n", " ", false, 4, (Object) null), 120));
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        if (Build.VERSION.SDK_INT < 26) {
            string = string + " " + ctx.getString(R.string.upd_unknown_sources_hint);
        }
        show(string, Tap.INSTALL);
    }

    /* JADX INFO: compiled from: UpdateManager.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\b\u0002\u0018\u00002\u00020\u0001BA\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u000b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016¨\u0006\u001b"}, d2 = {"Lmy/haifu/punch/UpdateManager$Meta;", "", "versionCode", "", "versionName", "", "url", "Lokhttp3/HttpUrl;", "sha", "bytes", "", "minSupported", "json", "Lorg/json/JSONObject;", "(ILjava/lang/String;Lokhttp3/HttpUrl;Ljava/lang/String;JILorg/json/JSONObject;)V", "getBytes", "()J", "getJson", "()Lorg/json/JSONObject;", "getMinSupported", "()I", "getSha", "()Ljava/lang/String;", "getUrl", "()Lokhttp3/HttpUrl;", "getVersionCode", "getVersionName", "app_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Meta {
        private final long bytes;
        private final JSONObject json;
        private final int minSupported;
        private final String sha;
        private final HttpUrl url;
        private final int versionCode;
        private final String versionName;

        public Meta(int i, String versionName, HttpUrl httpUrl, String str, long j, int i2, JSONObject json) {
            Intrinsics.checkNotNullParameter(versionName, "versionName");
            Intrinsics.checkNotNullParameter(json, "json");
            this.versionCode = i;
            this.versionName = versionName;
            this.url = httpUrl;
            this.sha = str;
            this.bytes = j;
            this.minSupported = i2;
            this.json = json;
        }

        public final int getVersionCode() {
            return this.versionCode;
        }

        public final String getVersionName() {
            return this.versionName;
        }

        public final HttpUrl getUrl() {
            return this.url;
        }

        public final String getSha() {
            return this.sha;
        }

        public final long getBytes() {
            return this.bytes;
        }

        public final int getMinSupported() {
            return this.minSupported;
        }

        public final JSONObject getJson() {
            return this.json;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void check(Context ctx) {
        try {
            try {
                Meta metaFetchLatest = fetchLatest(ctx);
                applyRemoteConfig(ctx, metaFetchLatest.getJson());
                required = metaFetchLatest.getMinSupported() > 1;
                if (metaFetchLatest.getVersionCode() <= 1) {
                    Log.i(PrefsKt.TAG, "update: up to date (latest " + metaFetchLatest.getVersionCode() + ", installed 1)");
                    show(null, Tap.CHECK);
                    return;
                }
                Log.i(PrefsKt.TAG, "update: " + metaFetchLatest.getVersionName() + " (" + metaFetchLatest.getVersionCode() + ") available, installed 1");
                File file = readyApk;
                if (file != null && file.exists() && readyCode == metaFetchLatest.getVersionCode()) {
                    install(ctx, file);
                    return;
                }
                if (running) {
                    return;
                }
                running = true;
                try {
                    File fileDownload = download(ctx, metaFetchLatest);
                    if (fileDownload == null) {
                        return;
                    }
                    readyApk = fileDownload;
                    readyCode = metaFetchLatest.getVersionCode();
                    install(ctx, fileDownload);
                } finally {
                    running = false;
                }
            } catch (Throwable th) {
                Log.w(PrefsKt.TAG, "update: check failed — " + errText(th));
                if (required) {
                    show(text, tap);
                }
            }
        } catch (Throwable th2) {
            Log.w(PrefsKt.TAG, "update: unexpected — " + errText(th2));
        }
    }

    /* JADX WARN: Code duplicated, block: B:33:0x00be  */
    private final Meta fetchLatest(Context ctx) throws IOException {
        HttpUrl httpUrlResolve;
        String str;
        String string;
        String string2;
        HttpUrl httpUrl = HttpUrl.INSTANCE.get(Prefs.INSTANCE.updateUrl(ctx));
        Response responseExecute = getHttp().newCall(new Request.Builder().url(httpUrl).header("Cache-Control", "no-cache").get().build()).execute();
        try {
            Response response = responseExecute;
            if (!response.isSuccessful()) {
                throw new IOException("latest.json HTTP " + response.code());
            }
            ResponseBody responseBodyBody = response.body();
            if (responseBodyBody == null) {
                throw new IOException("latest.json is empty");
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            InputStream inputStreamByteStream = responseBodyBody.byteStream();
            try {
                InputStream inputStream = inputStreamByteStream;
                byte[] bArr = new byte[8192];
                do {
                    int i = inputStream.read(bArr);
                    if (i >= 0) {
                        byteArrayOutputStream.write(bArr, 0, i);
                    } else {
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(inputStreamByteStream, null);
                        JSONObject jSONObject = new JSONObject(byteArrayOutputStream.toString("UTF-8"));
                        int iOptInt = jSONObject.optInt("versionCode", -1);
                        if (iOptInt < 1) {
                            throw new IOException("latest.json has no valid versionCode");
                        }
                        Object objOpt = jSONObject.opt("url");
                        String str2 = objOpt instanceof String ? (String) objOpt : null;
                        if (str2 == null || (string2 = StringsKt.trim((CharSequence) str2).toString()) == null) {
                            httpUrlResolve = null;
                        } else {
                            if (string2.length() <= 0) {
                                string2 = null;
                            }
                            if (string2 != null) {
                                httpUrlResolve = httpUrl.resolve(string2);
                            } else {
                                httpUrlResolve = null;
                            }
                        }
                        String strJsonStr = PrefsKt.jsonStr(jSONObject, "versionName", 40);
                        if (strJsonStr == null) {
                            strJsonStr = String.valueOf(iOptInt);
                        }
                        String str3 = strJsonStr;
                        Object objOpt2 = jSONObject.opt("sha256");
                        String str4 = objOpt2 instanceof String ? (String) objOpt2 : null;
                        if (str4 == null || (string = StringsKt.trim((CharSequence) str4).toString()) == null) {
                            str = null;
                        } else {
                            String lowerCase = string.toLowerCase(Locale.ROOT);
                            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                            str = lowerCase;
                        }
                        Meta meta = new Meta(iOptInt, str3, httpUrlResolve, str, jSONObject.optLong("bytes", -1L), jSONObject.optInt("minSupported", 0), jSONObject);
                        CloseableKt.closeFinally(responseExecute, null);
                        return meta;
                    }
                } while (byteArrayOutputStream.size() <= 65536);
                throw new IOException("latest.json is too big");
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(inputStreamByteStream, th);
                    throw th2;
                }
            }
        } catch (Throwable th3) {
            try {
                throw th3;
            } catch (Throwable th4) {
                CloseableKt.closeFinally(responseExecute, th3);
                throw th4;
            }
        }
    }

    private final void applyRemoteConfig(Context ctx, JSONObject o) {
        String strNormalizeTime;
        JSONArray jSONArrayOptJSONArray = o.optJSONArray("reminders");
        if (jSONArrayOptJSONArray != null) {
            List<String> times = Prefs.INSTANCE.parseTimes(jSONArrayOptJSONArray);
            if (jSONArrayOptJSONArray.length() == 0 || (!times.isEmpty())) {
                Prefs.INSTANCE.setReminders(ctx, times);
            }
        }
        if (o.has(ReminderScheduler.KIND_MORNING)) {
            Object objOpt = o.opt(ReminderScheduler.KIND_MORNING);
            if (objOpt == null || Intrinsics.areEqual(objOpt, JSONObject.NULL)) {
                Prefs.INSTANCE.setMorning(ctx, null);
            } else {
                boolean z = objOpt instanceof String;
                if (z && StringsKt.isBlank((CharSequence) objOpt)) {
                    Prefs.INSTANCE.setMorning(ctx, null);
                } else if (z && (strNormalizeTime = Prefs.INSTANCE.normalizeTime((String) objOpt)) != null) {
                    Prefs.INSTANCE.setMorning(ctx, strNormalizeTime);
                }
            }
        }
        ReminderScheduler.INSTANCE.scheduleAll(ctx);
    }

    private final File download(Context ctx, Meta meta) {
        HttpUrl url = meta.getUrl();
        if (url == null) {
            return failed(ctx, "latest.json has no url");
        }
        String sha = meta.getSha();
        if (sha != null) {
            if (!SHA_RE.matches(sha)) {
                sha = null;
            }
            if (sha != null) {
                if (!url.getIsHttps() && !PrefsKt.getLOCAL_HOSTS().contains(url.host())) {
                    return failed(ctx, "APK url must be https");
                }
                File file = new File(ctx.getCacheDir(), APK_NAME);
                file.delete();
                Log.i(PrefsKt.TAG, "update: downloading " + url);
                try {
                    Response responseExecute = getHttp().newCall(new Request.Builder().url(url).get().build()).execute();
                    try {
                        Response response = responseExecute;
                        if (!response.isSuccessful()) {
                            Log.w(PrefsKt.TAG, "update: download HTTP " + response.code());
                            CloseableKt.closeFinally(responseExecute, null);
                            return null;
                        }
                        ResponseBody responseBodyBody = response.body();
                        if (responseBodyBody == null) {
                            CloseableKt.closeFinally(responseExecute, null);
                            return null;
                        }
                        String str = "APK too big";
                        if (responseBodyBody.getContentLength() > MAX_APK_BYTES) {
                            File fileFailed = INSTANCE.failed(ctx, "APK too big");
                            CloseableKt.closeFinally(responseExecute, null);
                            return fileFailed;
                        }
                        InputStream inputStreamByteStream = responseBodyBody.byteStream();
                        try {
                            InputStream inputStream = inputStreamByteStream;
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            try {
                                FileOutputStream fileOutputStream2 = fileOutputStream;
                                byte[] bArr = new byte[65536];
                                long j = 0;
                                while (true) {
                                    int i = inputStream.read(bArr);
                                    if (i < 0) {
                                        Unit unit = Unit.INSTANCE;
                                        CloseableKt.closeFinally(fileOutputStream, null);
                                        Unit unit2 = Unit.INSTANCE;
                                        CloseableKt.closeFinally(inputStreamByteStream, null);
                                        Unit unit3 = Unit.INSTANCE;
                                        CloseableKt.closeFinally(responseExecute, null);
                                        long length = file.length();
                                        if (meta.getBytes() > 0 && length != meta.getBytes()) {
                                            file.delete();
                                            return failed(ctx, "size mismatch (expected " + meta.getBytes() + ", got " + length + ")");
                                        }
                                        if (!StringsKt.equals(sha256(file), sha, true)) {
                                            file.delete();
                                            return failed(ctx, "checksum mismatch");
                                        }
                                        Log.i(PrefsKt.TAG, "update: verified " + length + " bytes, sha256 ok");
                                        return file;
                                    }
                                    String str2 = str;
                                    j += (long) i;
                                    if (j > MAX_APK_BYTES) {
                                        throw new IOException(str2);
                                    }
                                    fileOutputStream2.write(bArr, 0, i);
                                    str = str2;
                                }
                            } catch (Throwable th) {
                                try {
                                    throw th;
                                } catch (Throwable th2) {
                                    CloseableKt.closeFinally(fileOutputStream, th);
                                    throw th2;
                                }
                            }
                        } catch (Throwable th3) {
                            try {
                                throw th3;
                            } catch (Throwable th4) {
                                CloseableKt.closeFinally(inputStreamByteStream, th3);
                                throw th4;
                            }
                        }
                    } catch (Throwable th5) {
                        try {
                            throw th5;
                        } catch (Throwable th6) {
                            CloseableKt.closeFinally(responseExecute, th5);
                            throw th6;
                        }
                    }
                } catch (IOException e) {
                    Log.w(PrefsKt.TAG, "update: download failed — " + errText(e));
                    file.delete();
                    return null;
                }
                Log.w(PrefsKt.TAG, "update: download failed — " + errText(e));
                file.delete();
                return null;
            }
        }
        return failed(ctx, "latest.json has no valid sha256");
    }

    private final boolean canInstall(Context ctx) {
        return Build.VERSION.SDK_INT < 26 || ctx.getPackageManager().canRequestPackageInstalls();
    }

    private final void install(Context ctx, File apk) {
        try {
            if (!canInstall(ctx)) {
                Log.i(PrefsKt.TAG, "update: waiting for 'Install unknown apps' to be allowed for this app");
                show(ctx.getString(R.string.upd_allow), Tap.ALLOW_INSTALLS);
            } else {
                commitSession(ctx, apk);
                Log.i(PrefsKt.TAG, "update: install session committed");
                show(ctx.getString(R.string.upd_install), Tap.INSTALL);
            }
        } catch (Throwable th) {
            onInstallFailed(ctx, 1, errText(th));
        }
    }

    private final void commitSession(Context ctx, File apk) throws IOException {
        PackageInstaller packageInstaller = ctx.getPackageManager().getPackageInstaller();
        Intrinsics.checkNotNullExpressionValue(packageInstaller, "getPackageInstaller(...)");
        for (PackageInstaller.SessionInfo sessionInfo : packageInstaller.getMySessions()) {
            try {
                Result.Companion companion = Result.INSTANCE;
                UpdateManager updateManager = this;
                packageInstaller.abandonSession(sessionInfo.getSessionId());
                Result.m189constructorimpl(Unit.INSTANCE);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.INSTANCE;
                Result.m189constructorimpl(ResultKt.createFailure(th));
            }
        }
        PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(1);
        sessionParams.setAppPackageName(ctx.getPackageName());
        sessionParams.setSize(apk.length());
        if (Build.VERSION.SDK_INT >= 26) {
            sessionParams.setInstallReason(4);
        }
        int iCreateSession = packageInstaller.createSession(sessionParams);
        PackageInstaller.Session sessionOpenSession = packageInstaller.openSession(iCreateSession);
        Intrinsics.checkNotNullExpressionValue(sessionOpenSession, "openSession(...)");
        try {
            OutputStream outputStreamOpenWrite = sessionOpenSession.openWrite("hf-punch", 0L, apk.length());
            try {
                OutputStream outputStream = outputStreamOpenWrite;
                FileInputStream fileInputStream = new FileInputStream(apk);
                try {
                    Intrinsics.checkNotNull(outputStream);
                    ByteStreamsKt.copyTo(fileInputStream, outputStream, 65536);
                    CloseableKt.closeFinally(fileInputStream, null);
                    sessionOpenSession.fsync(outputStream);
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(outputStreamOpenWrite, null);
                    Intent action = new Intent(ctx, (Class<?>) InstallResultReceiver.class).setAction(InstallResultReceiver.ACTION_RESULT);
                    Intrinsics.checkNotNullExpressionValue(action, "setAction(...)");
                    sessionOpenSession.commit(PendingIntent.getBroadcast(ctx, iCreateSession, action, (Build.VERSION.SDK_INT >= 31 ? 33554432 : 0) | 134217728).getIntentSender());
                    sessionOpenSession.close();
                } catch (Throwable th2) {
                    try {
                        throw th2;
                    } catch (Throwable th3) {
                        CloseableKt.closeFinally(fileInputStream, th2);
                        throw th3;
                    }
                }
            } catch (Throwable th4) {
                try {
                    throw th4;
                } catch (Throwable th5) {
                    CloseableKt.closeFinally(outputStreamOpenWrite, th4);
                    throw th5;
                }
            }
        } catch (Throwable th6) {
            sessionOpenSession.close();
            throw th6;
        }
    }

    private final void openUnknownSources(Activity act) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        try {
            Result.Companion companion = Result.INSTANCE;
            UpdateManager updateManager = this;
            act.startActivity(new Intent("android.settings.MANAGE_UNKNOWN_APP_SOURCES", Uri.parse("package:" + act.getPackageName())));
            Result.m189constructorimpl(Unit.INSTANCE);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            Result.m189constructorimpl(ResultKt.createFailure(th));
        }
    }

    private final File failed(Context ctx, String msg) {
        Log.w(PrefsKt.TAG, "update: " + msg);
        show(ctx.getString(R.string.upd_failed, msg), Tap.CHECK);
        return null;
    }

    private final void show(String t, Tap action) {
        text = t;
        tap = action;
        main.post(new Runnable() { // from class: my.haifu.punch.UpdateManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                UpdateManager.show$lambda$19();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void show$lambda$19() {
        Listener listener2 = listener;
        if (listener2 != null) {
            listener2.onUpdateMessage(INSTANCE.composed());
        }
    }

    private final String composed() {
        Context context;
        StringBuilder sb;
        String str;
        String string = text;
        if (!required || (context = app) == null) {
            return string;
        }
        String string2 = context.getString(R.string.upd_required);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        if (string == null) {
            string = context.getString(R.string.upd_tap_check);
            sb = new StringBuilder();
            sb.append(string2);
            str = " — ";
        } else {
            sb = new StringBuilder();
            sb.append(string2);
            str = ". ";
        }
        sb.append(str);
        sb.append(string);
        return sb.toString();
    }

    private final String sha256(File f) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        FileInputStream fileInputStream = new FileInputStream(f);
        try {
            FileInputStream fileInputStream2 = fileInputStream;
            byte[] bArr = new byte[65536];
            while (true) {
                int i = fileInputStream2.read(bArr);
                if (i >= 0) {
                    messageDigest.update(bArr, 0, i);
                } else {
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(fileInputStream, null);
                    byte[] bArrDigest = messageDigest.digest();
                    Intrinsics.checkNotNullExpressionValue(bArrDigest, "digest(...)");
                    return ArraysKt.joinToString$default(bArrDigest, (CharSequence) "", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) new Function1<Byte, CharSequence>() { // from class: my.haifu.punch.UpdateManager.sha256.2
                        public final CharSequence invoke(byte b) {
                            String str = String.format("%02x", Arrays.copyOf(new Object[]{Byte.valueOf(b)}, 1));
                            Intrinsics.checkNotNullExpressionValue(str, "format(...)");
                            return str;
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ CharSequence invoke(Byte b) {
                            return invoke(b.byteValue());
                        }
                    }, 30, (Object) null);
                }
            }
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(fileInputStream, th);
                throw th2;
            }
        }
    }

    private final String errText(Throwable t) {
        String message = t.getMessage();
        if (message == null) {
            message = t.toString();
        }
        return StringsKt.take(StringsKt.trim((CharSequence) StringsKt.replace$default(message, "\n", " ", false, 4, (Object) null)).toString(), 160);
    }
}
