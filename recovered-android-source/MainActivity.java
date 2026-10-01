package my.haifu.punch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.GeolocationPermissions;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;
import org.json.JSONTokener;

/* JADX INFO: compiled from: MainActivity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000[\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u0012*\u0001\u0016\u0018\u0000 B2\u00020\u0001:\u0004@ABCB\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u001a\u001a\u00020\u00062\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0002J$\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u00042\b\u0010 \u001a\u0004\u0018\u00010\u00042\b\u0010!\u001a\u0004\u0018\u00010\u0004J\b\u0010\"\u001a\u00020\u001eH\u0002J\u0006\u0010#\u001a\u00020\u0006J\u0016\u0010$\u001a\u00020\u001e2\u0006\u0010%\u001a\u00020\u00042\u0006\u0010&\u001a\u00020\u0004J\u0010\u0010'\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u0004H\u0002J\u0012\u0010)\u001a\u00020\u001e2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0002J\b\u0010*\u001a\u00020\u0006H\u0002J\b\u0010+\u001a\u00020\u001eH\u0002J\u001a\u0010,\u001a\u00020\u001e2\u0006\u0010-\u001a\u00020\u00192\b\u0010.\u001a\u0004\u0018\u00010\u0004H\u0002J\b\u0010/\u001a\u00020\u001eH\u0003J\u0012\u00100\u001a\u00020\u001e2\b\u00101\u001a\u0004\u0018\u000102H\u0014J\b\u00103\u001a\u00020\u001eH\u0014J\u0010\u00104\u001a\u00020\u001e2\u0006\u00105\u001a\u00020\u001cH\u0014J\b\u00106\u001a\u00020\u001eH\u0014J\b\u00107\u001a\u00020\u001eH\u0014J\u0006\u00108\u001a\u00020\u001eJ\u0006\u00109\u001a\u00020\u001eJ\u0010\u0010:\u001a\u00020\u001e2\u0006\u0010-\u001a\u00020\u0019H\u0002J\b\u0010;\u001a\u00020\u001eH\u0002J\b\u0010<\u001a\u00020\u001eH\u0002J\u0012\u0010=\u001a\u00020\u00062\b\u0010.\u001a\u0004\u0018\u00010\u0004H\u0002J\u0010\u0010>\u001a\u00020\u001e2\u0006\u0010?\u001a\u00020\u0019H\u0003R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R(\u0010\u000e\u001a\u001c\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u0004 \u0011*\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00100\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0017R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006D"}, d2 = {"Lmy/haifu/punch/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "allowedOrigin", "", "cacheAttempt", "", "errorThisLoad", "locMsg", "Lmy/haifu/punch/MainActivity$Msg;", "needsReload", "netMsg", "originOk", "permDialogOpen", "permLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "", "kotlin.jvm.PlatformType", "strip", "Landroid/widget/TextView;", "updMsg", "updateListener", "my/haifu/punch/MainActivity$updateListener$1", "Lmy/haifu/punch/MainActivity$updateListener$1;", "web", "Landroid/webkit/WebView;", "applyOverrides", "i", "Landroid/content/Intent;", "applyWorker", "", "token", "name", "staffId", "askPermissions", "bridgeAllowed", "deliverLocation", "reqId", "json", "granted", "p", "handleActions", "hasLocationPermission", "loadHome", "mainFrameFailed", "view", "url", "maybeAskBattery", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onNewIntent", "intent", "onPause", "onResume", "openAppSettings", "openLocationSettings", "readTokenFallback", "refreshLocationStrip", "renderStrip", "sameOrigin", "setupWeb", "w", "Chrome", "Client", "Companion", "Msg", "app_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MainActivity extends AppCompatActivity {
    private static final String COARSE = "android.permission.ACCESS_COARSE_LOCATION";
    public static final String EXTRA_BASE_URL = "base_url";
    public static final String EXTRA_CHECK_UPDATE = "check_update_now";
    public static final String EXTRA_CLEAR = "clear_overrides";
    public static final String EXTRA_RUN_REMINDER = "run_reminder_now";
    public static final String EXTRA_UPDATE_URL = "update_url";
    public static final String EXTRA_WV_DEBUG = "webview_debug";
    private static final String FINE = "android.permission.ACCESS_FINE_LOCATION";
    private static final String FLAG_BATTERY_ASKED = "battery_asked";
    private static final String FLAG_LOC_ASKED = "loc_asked";
    private static final String FLAG_NOTIF_ASKED = "notif_asked";
    private String allowedOrigin = "";
    private boolean cacheAttempt;
    private boolean errorThisLoad;
    private Msg locMsg;
    private boolean needsReload;
    private Msg netMsg;
    private volatile boolean originOk;
    private boolean permDialogOpen;
    private final ActivityResultLauncher<String[]> permLauncher;
    private TextView strip;
    private Msg updMsg;
    private final MainActivity$updateListener$1 updateListener;
    private WebView web;

    /* JADX WARN: Type inference failed for: r0v4, types: [my.haifu.punch.MainActivity$updateListener$1] */
    public MainActivity() {
        ActivityResultLauncher<String[]> activityResultLauncherRegisterForActivityResult = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: my.haifu.punch.MainActivity$$ExternalSyntheticLambda1
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                MainActivity.permLauncher$lambda$0(this.f$0, (Map) obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(activityResultLauncherRegisterForActivityResult, "registerForActivityResult(...)");
        this.permLauncher = activityResultLauncherRegisterForActivityResult;
        this.updateListener = new UpdateManager.Listener() { // from class: my.haifu.punch.MainActivity$updateListener$1
            @Override // my.haifu.punch.UpdateManager.Listener
            public void onUpdateMessage(String text) {
                final MainActivity mainActivity = this.this$0;
                mainActivity.updMsg = text != null ? new MainActivity.Msg(text, new Function0<Unit>() { // from class: my.haifu.punch.MainActivity$updateListener$1$onUpdateMessage$1$1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        UpdateManager.INSTANCE.onStripTap(mainActivity);
                    }
                }) : null;
                this.this$0.renderStrip();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: MainActivity.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lmy/haifu/punch/MainActivity$Msg;", "", "text", "", "action", "Lkotlin/Function0;", "", "(Ljava/lang/String;Lkotlin/jvm/functions/Function0;)V", "getAction", "()Lkotlin/jvm/functions/Function0;", "getText", "()Ljava/lang/String;", "app_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class Msg {
        private final Function0<Unit> action;
        private final String text;

        public Msg(String text, Function0<Unit> action) {
            Intrinsics.checkNotNullParameter(text, "text");
            Intrinsics.checkNotNullParameter(action, "action");
            this.text = text;
            this.action = action;
        }

        public final Function0<Unit> getAction() {
            return this.action;
        }

        public final String getText() {
            return this.text;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void permLauncher$lambda$0(MainActivity this$0, Map map) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.permDialogOpen = false;
        this$0.refreshLocationStrip();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        WebView webView;
        super.onCreate(savedInstanceState);
        applyOverrides(getIntent());
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.strip);
        textView.setOnClickListener(new View.OnClickListener() { // from class: my.haifu.punch.MainActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.onCreate$lambda$2$lambda$1(this.f$0, view);
            }
        });
        this.strip = textView;
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback() { // from class: my.haifu.punch.MainActivity.onCreate.2
            {
                super(true);
            }

            @Override // androidx.activity.OnBackPressedCallback
            public void handleOnBackPressed() {
                WebView webView2 = MainActivity.this.web;
                if (webView2 == null || !webView2.canGoBack()) {
                    MainActivity.this.finish();
                } else {
                    webView2.goBack();
                }
            }
        });
        MainActivity mainActivity = this;
        ReminderScheduler.INSTANCE.scheduleAll(mainActivity);
        UpdateManager.INSTANCE.onAppStart(mainActivity);
        UpdateManager.INSTANCE.setListener(this.updateListener);
        UpdateManager.INSTANCE.startPeriodic(mainActivity);
        try {
            webView = new WebView(this);
        } catch (Throwable th) {
            Log.e(PrefsKt.TAG, "WebView unavailable: " + th);
            String string = getString(R.string.webview_missing);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            this.netMsg = new Msg(string, new Function0<Unit>() { // from class: my.haifu.punch.MainActivity$onCreate$w$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    this.this$0.recreate();
                }
            });
            renderStrip();
            webView = null;
        }
        if (webView != null) {
            this.web = webView;
            ((FrameLayout) findViewById(R.id.web_container)).addView(webView, new FrameLayout.LayoutParams(-1, -1));
            setupWeb(webView);
            loadHome();
        }
        askPermissions();
        handleActions(getIntent());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$2$lambda$1(MainActivity this$0, View view) {
        Function0<Unit> action;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Msg msg = this$0.locMsg;
        if (msg == null && (msg = this$0.updMsg) == null) {
            msg = this$0.netMsg;
        }
        if (msg == null || (action = msg.getAction()) == null) {
            return;
        }
        action.invoke();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        super.onNewIntent(intent);
        setIntent(intent);
        if (applyOverrides(intent)) {
            WebView.setWebContentsDebuggingEnabled(Prefs.INSTANCE.webviewDebug(this));
            loadHome();
        }
        handleActions(intent);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        WebView webView = this.web;
        if (webView != null) {
            webView.onResume();
        }
        refreshLocationStrip();
        UpdateManager.INSTANCE.onResume(this);
        if (this.needsReload) {
            loadHome();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        WebView webView = this.web;
        if (webView != null) {
            webView.onPause();
        }
        super.onPause();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        UpdateManager.INSTANCE.clearListener(this.updateListener);
        WebView webView = this.web;
        if (webView != null) {
            ViewParent parent = webView.getParent();
            ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
            if (viewGroup != null) {
                viewGroup.removeView(webView);
            }
            webView.stopLoading();
            webView.destroy();
        }
        this.web = null;
        super.onDestroy();
    }

    private final void setupWeb(WebView w) {
        WebSettings settings = w.getSettings();
        Intrinsics.checkNotNullExpressionValue(settings, "getSettings(...)");
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setGeolocationEnabled(true);
        if (Build.VERSION.SDK_INT < 24) {
            settings.setGeolocationDatabasePath(getFilesDir().getPath());
        }
        settings.setMixedContentMode(1);
        settings.setCacheMode(-1);
        settings.setAllowFileAccess(false);
        settings.setAllowContentAccess(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
        settings.setSupportMultipleWindows(false);
        String userAgentString = settings.getUserAgentString();
        if (userAgentString == null) {
            userAgentString = "";
        }
        settings.setUserAgentString(userAgentString + " HFPunchApp/1.0");
        WebView.setWebContentsDebuggingEnabled(Prefs.INSTANCE.webviewDebug(this));
        w.addJavascriptInterface(new ShellBridge(this), "HFShell");
        w.setWebViewClient(new Client());
        w.setWebChromeClient(new Chrome());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadHome() {
        WebView webView = this.web;
        if (webView == null) {
            return;
        }
        String strBaseUrl = Prefs.INSTANCE.baseUrl(this);
        String strOriginOf = PrefsKt.originOf(strBaseUrl);
        if (strOriginOf == null) {
            strOriginOf = "";
        }
        this.allowedOrigin = strOriginOf;
        this.cacheAttempt = false;
        this.needsReload = false;
        if (this.netMsg != null) {
            this.netMsg = null;
            renderStrip();
        }
        webView.getSettings().setCacheMode(-1);
        webView.loadUrl(strBaseUrl);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean sameOrigin(String url) {
        return this.allowedOrigin.length() > 0 && Intrinsics.areEqual(PrefsKt.originOf(url), this.allowedOrigin);
    }

    /* JADX INFO: compiled from: MainActivity.kt */
    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u001a\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016J$\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016J \u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J,\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\b2\b\u0010\u0017\u001a\u0004\u0018\u00010\bH\u0017J\u0018\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u0010\u0010\u001b\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0018\u0010\u001c\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0018\u0010\u001c\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0017¨\u0006\u001d"}, d2 = {"Lmy/haifu/punch/MainActivity$Client;", "Landroid/webkit/WebViewClient;", "(Lmy/haifu/punch/MainActivity;)V", "doUpdateVisitedHistory", "", "view", "Landroid/webkit/WebView;", "url", "", "isReload", "", "onPageFinished", "onPageStarted", "favicon", "Landroid/graphics/Bitmap;", "onReceivedError", "request", "Landroid/webkit/WebResourceRequest;", "error", "Landroid/webkit/WebResourceError;", "errorCode", "", "description", "failingUrl", "onRenderProcessGone", "detail", "Landroid/webkit/RenderProcessGoneDetail;", "route", "shouldOverrideUrlLoading", "app_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class Client extends WebViewClient {
        public Client() {
        }

        @Override // android.webkit.WebViewClient
        @Deprecated(message = "Deprecated in Java")
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Intrinsics.checkNotNullParameter(view, "view");
            Intrinsics.checkNotNullParameter(url, "url");
            return route(url);
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            Intrinsics.checkNotNullParameter(view, "view");
            Intrinsics.checkNotNullParameter(request, "request");
            if (!request.isForMainFrame()) {
                return false;
            }
            String string = request.getUrl().toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return route(string);
        }

        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        private final boolean route(String url) {
            String lowerCase;
            if (MainActivity.this.sameOrigin(url)) {
                return false;
            }
            Uri uri = Uri.parse(url);
            String scheme = uri.getScheme();
            if (scheme != null) {
                lowerCase = scheme.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            } else {
                lowerCase = null;
            }
            if (lowerCase == null) {
                return true;
            }
            switch (lowerCase.hashCode()) {
                case -1081572750:
                    if (!lowerCase.equals("mailto")) {
                        return true;
                    }
                    break;
                case 102225:
                    if (!lowerCase.equals("geo")) {
                        return true;
                    }
                    break;
                case 114009:
                    if (!lowerCase.equals("sms")) {
                        return true;
                    }
                    break;
                case 114715:
                    if (!lowerCase.equals("tel")) {
                        return true;
                    }
                    break;
                case 3213448:
                    if (!lowerCase.equals("http")) {
                        return true;
                    }
                    break;
                case 99617003:
                    if (!lowerCase.equals("https")) {
                        return true;
                    }
                    break;
                case 1934780818:
                    if (!lowerCase.equals("whatsapp")) {
                        return true;
                    }
                    break;
                default:
                    return true;
            }
            MainActivity mainActivity = MainActivity.this;
            try {
                Result.Companion companion = Result.INSTANCE;
                Client client = this;
                mainActivity.startActivity(new Intent("android.intent.action.VIEW", uri));
                Result.m189constructorimpl(Unit.INSTANCE);
                return true;
            } catch (Throwable th) {
                Result.Companion companion2 = Result.INSTANCE;
                Result.m189constructorimpl(ResultKt.createFailure(th));
                return true;
            }
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Intrinsics.checkNotNullParameter(view, "view");
            MainActivity mainActivity = MainActivity.this;
            mainActivity.originOk = mainActivity.sameOrigin(url);
            MainActivity.this.errorThisLoad = false;
        }

        @Override // android.webkit.WebViewClient
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            Intrinsics.checkNotNullParameter(view, "view");
            MainActivity mainActivity = MainActivity.this;
            mainActivity.originOk = mainActivity.sameOrigin(url);
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            Intrinsics.checkNotNullParameter(view, "view");
            MainActivity mainActivity = MainActivity.this;
            mainActivity.originOk = mainActivity.sameOrigin(url);
            if (MainActivity.this.errorThisLoad) {
                return;
            }
            if (MainActivity.this.cacheAttempt) {
                MainActivity.this.cacheAttempt = false;
                MainActivity.this.needsReload = true;
                view.getSettings().setCacheMode(-1);
            } else if (MainActivity.this.netMsg != null) {
                MainActivity.this.netMsg = null;
                MainActivity.this.renderStrip();
            }
            if (MainActivity.this.originOk) {
                MainActivity.this.readTokenFallback(view);
            }
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Intrinsics.checkNotNullParameter(view, "view");
            Intrinsics.checkNotNullParameter(request, "request");
            Intrinsics.checkNotNullParameter(error, "error");
            if (request.isForMainFrame()) {
                MainActivity.this.mainFrameFailed(view, request.getUrl().toString());
            }
        }

        @Override // android.webkit.WebViewClient
        @Deprecated(message = "Deprecated in Java")
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Intrinsics.checkNotNullParameter(view, "view");
            if (Build.VERSION.SDK_INT < 23) {
                MainActivity.this.mainFrameFailed(view, failingUrl);
            }
        }

        @Override // android.webkit.WebViewClient
        public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {
            Intrinsics.checkNotNullParameter(view, "view");
            Intrinsics.checkNotNullParameter(detail, "detail");
            Log.w(PrefsKt.TAG, "WebView renderer gone — recreating");
            if (view != MainActivity.this.web) {
                return true;
            }
            ViewParent parent = view.getParent();
            ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
            if (viewGroup != null) {
                viewGroup.removeView(view);
            }
            view.destroy();
            MainActivity.this.web = null;
            MainActivity.this.recreate();
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void mainFrameFailed(final WebView view, final String url) {
        if (url == null || !sameOrigin(url)) {
            return;
        }
        this.errorThisLoad = true;
        this.needsReload = true;
        if (!this.cacheAttempt) {
            this.cacheAttempt = true;
            view.post(new Runnable() { // from class: my.haifu.punch.MainActivity$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    MainActivity.mainFrameFailed$lambda$4(this.f$0, view, url);
                }
            });
            return;
        }
        this.cacheAttempt = false;
        view.getSettings().setCacheMode(-1);
        String string = getString(R.string.strip_offline);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        this.netMsg = new Msg(string, new Function0<Unit>() { // from class: my.haifu.punch.MainActivity.mainFrameFailed.2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                MainActivity.this.loadHome();
            }
        });
        renderStrip();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mainFrameFailed$lambda$4(MainActivity this$0, WebView view, String str) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(view, "$view");
        if (this$0.web == view) {
            view.getSettings().setCacheMode(1);
            view.loadUrl(str);
        }
    }

    /* JADX INFO: compiled from: MainActivity.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016¨\u0006\t"}, d2 = {"Lmy/haifu/punch/MainActivity$Chrome;", "Landroid/webkit/WebChromeClient;", "(Lmy/haifu/punch/MainActivity;)V", "onGeolocationPermissionsShowPrompt", "", "origin", "", "callback", "Landroid/webkit/GeolocationPermissions$Callback;", "app_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class Chrome extends WebChromeClient {
        public Chrome() {
        }

        @Override // android.webkit.WebChromeClient
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            boolean z = origin != null && MainActivity.this.allowedOrigin.length() > 0 && Intrinsics.areEqual(PrefsKt.originOf(origin), MainActivity.this.allowedOrigin);
            if (callback != null) {
                callback.invoke(origin, z, z);
            }
            if (!z || MainActivity.this.hasLocationPermission()) {
                return;
            }
            MainActivity.this.askPermissions();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void readTokenFallback(WebView view) {
        view.evaluateJavascript("(function(){try{return JSON.stringify({t:localStorage.getItem('hf_punch_token'),w:localStorage.getItem('hf_punch_worker')});}catch(e){return null;}})()", new ValueCallback() { // from class: my.haifu.punch.MainActivity$$ExternalSyntheticLambda6
            @Override // android.webkit.ValueCallback
            public final void onReceiveValue(Object obj) {
                MainActivity.readTokenFallback$lambda$8(this.f$0, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:51:0x00b8  */
    public static final void readTokenFallback$lambda$8(MainActivity this$0, String str) {
        String string;
        String str2;
        Object objM189constructorimpl;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        try {
            if (this$0.originOk) {
                if (str == null) {
                    str = "null";
                }
                Object objNextValue = new JSONTokener(str).nextValue();
                String strJsonStr = null;
                String str3 = objNextValue instanceof String ? (String) objNextValue : null;
                if (str3 != null && str3.length() <= 8192) {
                    JSONObject jSONObject = new JSONObject(str3);
                    Object objOpt = jSONObject.opt("t");
                    String str4 = objOpt instanceof String ? (String) objOpt : null;
                    if (str4 != null && (string = StringsKt.trim((CharSequence) str4).toString()) != null && PrefsKt.validToken(string) && !Intrinsics.areEqual(string, Prefs.INSTANCE.token(this$0))) {
                        Object objOpt2 = jSONObject.opt("w");
                        String str5 = objOpt2 instanceof String ? (String) objOpt2 : null;
                        if (str5 == null) {
                            str2 = null;
                        } else {
                            if (str5.length() >= 4096) {
                                str5 = null;
                            }
                            if (str5 != null) {
                                try {
                                    Result.Companion companion = Result.INSTANCE;
                                    JSONObject jSONObject2 = new JSONObject(str5);
                                    String strJsonStr2 = PrefsKt.jsonStr(jSONObject2, "full_name", 120);
                                    try {
                                        strJsonStr = PrefsKt.jsonStr(jSONObject2, "staff_id", 64);
                                        objM189constructorimpl = Result.m189constructorimpl(Unit.INSTANCE);
                                        strJsonStr = strJsonStr2;
                                        str2 = strJsonStr;
                                    } catch (Throwable th) {
                                        th = th;
                                        String str6 = strJsonStr;
                                        strJsonStr = strJsonStr2;
                                        str2 = str6;
                                        Result.Companion companion2 = Result.INSTANCE;
                                        objM189constructorimpl = Result.m189constructorimpl(ResultKt.createFailure(th));
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    str2 = null;
                                }
                                Result.m188boximpl(objM189constructorimpl);
                            } else {
                                str2 = null;
                            }
                        }
                        Log.i(PrefsKt.TAG, "worker token picked up from the page's storage");
                        this$0.applyWorker(string, strJsonStr, str2);
                    }
                }
            }
        } catch (Throwable unused) {
        }
    }

    /* JADX INFO: renamed from: bridgeAllowed, reason: from getter */
    public final boolean getOriginOk() {
        return this.originOk;
    }

    public final void deliverLocation(final String reqId, final String json) {
        Intrinsics.checkNotNullParameter(reqId, "reqId");
        Intrinsics.checkNotNullParameter(json, "json");
        runOnUiThread(new Runnable() { // from class: my.haifu.punch.MainActivity$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.deliverLocation$lambda$9(this.f$0, reqId, json);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void deliverLocation$lambda$9(MainActivity this$0, String reqId, String json) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(reqId, "$reqId");
        Intrinsics.checkNotNullParameter(json, "$json");
        WebView webView = this$0.web;
        if (webView != null && this$0.originOk) {
            webView.evaluateJavascript("(function(){if(typeof window.__hfShellLocation==='function'){window.__hfShellLocation(" + JSONObject.quote(reqId) + "," + JSONObject.quote(json) + ");}})();", null);
        }
    }

    public final void applyWorker(String token, String name, String staffId) {
        Context applicationContext = getApplicationContext();
        if (token == null) {
            Prefs prefs = Prefs.INSTANCE;
            Intrinsics.checkNotNull(applicationContext);
            prefs.clearWorker(applicationContext);
            ReminderScheduler.INSTANCE.cancelAll(applicationContext);
            Log.i(PrefsKt.TAG, "worker signed out — reminders cancelled");
            return;
        }
        Prefs prefs2 = Prefs.INSTANCE;
        Intrinsics.checkNotNull(applicationContext);
        boolean zAreEqual = Intrinsics.areEqual(token, prefs2.token(applicationContext));
        Prefs prefs3 = Prefs.INSTANCE;
        String strStaffId = null;
        if (name == null) {
            name = zAreEqual ? Prefs.INSTANCE.fullName(applicationContext) : null;
        }
        if (staffId != null) {
            strStaffId = staffId;
        } else if (zAreEqual) {
            strStaffId = Prefs.INSTANCE.staffId(applicationContext);
        }
        prefs3.setWorker(applicationContext, token, name, strStaffId);
        if (!zAreEqual) {
            if (staffId == null) {
                staffId = "?";
            }
            Log.i(PrefsKt.TAG, "worker set (staff " + staffId + ")");
        }
        ReminderScheduler.INSTANCE.scheduleAll(applicationContext);
        runOnUiThread(new Runnable() { // from class: my.haifu.punch.MainActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.applyWorker$lambda$10(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void applyWorker$lambda$10(MainActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.maybeAskBattery();
    }

    public final void openLocationSettings() {
        try {
            startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
        } catch (Throwable unused) {
            openAppSettings();
        }
    }

    public final void openAppSettings() {
        try {
            Result.Companion companion = Result.INSTANCE;
            MainActivity mainActivity = this;
            startActivity(new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.fromParts("package", getPackageName(), null)));
            Result.m189constructorimpl(Unit.INSTANCE);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            Result.m189constructorimpl(ResultKt.createFailure(th));
        }
    }

    private final boolean granted(String p) {
        return ContextCompat.checkSelfPermission(this, p) == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean hasLocationPermission() {
        return granted(FINE) || granted(COARSE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$ArrayArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public final void askPermissions() {
        if (this.permDialogOpen) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        if (!hasLocationPermission()) {
            MainActivity mainActivity = this;
            if (!Prefs.INSTANCE.flag(mainActivity, FLAG_LOC_ASKED) || ActivityCompat.shouldShowRequestPermissionRationale(this, FINE)) {
                ArrayList arrayList2 = arrayList;
                arrayList2.add(FINE);
                arrayList2.add(COARSE);
                Prefs.INSTANCE.setFlag(mainActivity, FLAG_LOC_ASKED);
            }
        }
        if (Build.VERSION.SDK_INT >= 33 && !granted("android.permission.POST_NOTIFICATIONS")) {
            MainActivity mainActivity2 = this;
            if (!Prefs.INSTANCE.flag(mainActivity2, FLAG_NOTIF_ASKED)) {
                arrayList.add("android.permission.POST_NOTIFICATIONS");
                Prefs.INSTANCE.setFlag(mainActivity2, FLAG_NOTIF_ASKED);
            }
        }
        ArrayList arrayList3 = arrayList;
        if (!arrayList3.isEmpty()) {
            this.permDialogOpen = true;
            this.permLauncher.launch(arrayList3.toArray(new String[0]));
        }
        refreshLocationStrip();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void refreshLocationStrip() {
        Msg msg;
        if (this.permDialogOpen || hasLocationPermission()) {
            msg = null;
        } else {
            String string = getString(R.string.strip_location_denied);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            msg = new Msg(string, new Function0<Unit>() { // from class: my.haifu.punch.MainActivity.refreshLocationStrip.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, MainActivity.FINE)) {
                        MainActivity.this.permDialogOpen = true;
                        MainActivity.this.permLauncher.launch(new String[]{MainActivity.FINE, MainActivity.COARSE});
                        MainActivity.this.refreshLocationStrip();
                        return;
                    }
                    MainActivity.this.openAppSettings();
                }
            });
        }
        this.locMsg = msg;
        renderStrip();
    }

    private final void maybeAskBattery() {
        if (Build.VERSION.SDK_INT < 23 || isFinishing()) {
            return;
        }
        MainActivity mainActivity = this;
        if (Prefs.INSTANCE.flag(mainActivity, FLAG_BATTERY_ASKED)) {
            return;
        }
        Prefs.INSTANCE.setFlag(mainActivity, FLAG_BATTERY_ASKED);
        Object systemService = getSystemService("power");
        PowerManager powerManager = systemService instanceof PowerManager ? (PowerManager) systemService : null;
        if (powerManager == null || powerManager.isIgnoringBatteryOptimizations(getPackageName())) {
            return;
        }
        try {
            try {
                startActivity(new Intent("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS", Uri.parse("package:" + getPackageName())));
            } catch (Throwable unused) {
                Result.Companion companion = Result.INSTANCE;
                startActivity(new Intent("android.settings.IGNORE_BATTERY_OPTIMIZATION_SETTINGS"));
                Result.m189constructorimpl(Unit.INSTANCE);
            }
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            Result.m189constructorimpl(ResultKt.createFailure(th));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderStrip() {
        TextView textView = this.strip;
        if (textView == null) {
            return;
        }
        Msg msg = this.locMsg;
        if (msg == null && (msg = this.updMsg) == null) {
            msg = this.netMsg;
        }
        if (msg == null) {
            textView.setVisibility(8);
        } else {
            textView.setText(msg.getText());
            textView.setVisibility(0);
        }
    }

    private final boolean applyOverrides(Intent i) {
        boolean z;
        String string;
        String string2;
        boolean z2 = false;
        if (i == null) {
            return false;
        }
        try {
            boolean z3 = true;
            if (i.getBooleanExtra(EXTRA_CLEAR, false)) {
                Prefs.INSTANCE.clearOverrides(this);
                Log.i(PrefsKt.TAG, "overrides cleared");
                z = true;
            } else {
                z = false;
            }
            try {
                String stringExtra = i.getStringExtra(EXTRA_BASE_URL);
                if (stringExtra != null && (string2 = StringsKt.trim((CharSequence) stringExtra).toString()) != null) {
                    if (PrefsKt.isAllowedOverride(string2)) {
                        Prefs.INSTANCE.setBaseUrl(this, string2);
                        Log.i(PrefsKt.TAG, "base_url → " + string2);
                    } else {
                        Log.w(PrefsKt.TAG, "base_url refused (not this site or the emulator loopback): " + string2);
                        z3 = z;
                    }
                    z = z3;
                }
                String stringExtra2 = i.getStringExtra(EXTRA_UPDATE_URL);
                if (stringExtra2 != null && (string = StringsKt.trim((CharSequence) stringExtra2).toString()) != null) {
                    if (PrefsKt.isAllowedOverride(string)) {
                        Prefs.INSTANCE.setUpdateUrl(this, string);
                        Log.i(PrefsKt.TAG, "update_url → " + string);
                    } else {
                        Log.w(PrefsKt.TAG, "update_url refused (not this site or the emulator loopback): " + string);
                    }
                }
                if (i.hasExtra(EXTRA_WV_DEBUG)) {
                    Prefs.INSTANCE.setWebviewDebug(this, i.getBooleanExtra(EXTRA_WV_DEBUG, false));
                }
                Iterator it = CollectionsKt.listOf((Object[]) new String[]{EXTRA_CLEAR, EXTRA_BASE_URL, EXTRA_UPDATE_URL, EXTRA_WV_DEBUG}).iterator();
                while (it.hasNext()) {
                    i.removeExtra((String) it.next());
                }
                return z;
            } catch (Throwable th) {
                th = th;
                z2 = z;
                Log.w(PrefsKt.TAG, "bad intent extras: " + th);
                return z2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private final void handleActions(Intent i) {
        if (i == null) {
            return;
        }
        try {
            if (i.getBooleanExtra(EXTRA_RUN_REMINDER, false)) {
                i.removeExtra(EXTRA_RUN_REMINDER);
                final Context applicationContext = getApplicationContext();
                new Thread(new Runnable() { // from class: my.haifu.punch.MainActivity$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainActivity.handleActions$lambda$15(applicationContext);
                    }
                }, "hf-reminder-now").start();
            }
            if (i.getBooleanExtra(EXTRA_CHECK_UPDATE, false)) {
                i.removeExtra(EXTRA_CHECK_UPDATE);
                UpdateManager.INSTANCE.checkNow(this);
            }
        } catch (Throwable th) {
            Log.w(PrefsKt.TAG, "bad intent extras: " + th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleActions$lambda$15(Context context) {
        try {
            ReminderCheck reminderCheck = ReminderCheck.INSTANCE;
            Intrinsics.checkNotNull(context);
            reminderCheck.run(context, ReminderScheduler.KIND_EVENING, "17:10", false, false);
        } catch (Throwable th) {
            Log.w(PrefsKt.TAG, "run_reminder_now crashed: " + th);
        }
    }
}
