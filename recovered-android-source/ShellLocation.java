package my.haifu.punch;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ShellLocation.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0013\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u000eH\u0002J\u0010\u0010\u001f\u001a\u00020\u00192\u0006\u0010 \u001a\u00020!H\u0002J\u0010\u0010\"\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020\u000eH\u0002J \u0010#\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020\u000e2\u0006\u0010$\u001a\u00020\u00062\u0006\u0010%\u001a\u00020&H\u0002J\u0018\u0010'\u001a\u00020&2\u0006\u0010(\u001a\u00020\u00122\u0006\u0010)\u001a\u00020\u0006H\u0002J\u0010\u0010*\u001a\u00020\u00182\u0006\u0010+\u001a\u00020\u0006H\u0002J\u0010\u0010,\u001a\u00020\u00192\u0006\u0010-\u001a\u00020\u0018H\u0002J\u0012\u0010.\u001a\u0004\u0018\u00010\u000e2\u0006\u0010(\u001a\u00020\u0012H\u0002J\u0018\u0010/\u001a\u00020&2\u0006\u0010 \u001a\u00020!2\u0006\u0010)\u001a\u00020\u0006H\u0002J\u0018\u00100\u001a\u00020&2\u0006\u00101\u001a\u00020\u000e2\u0006\u00102\u001a\u00020\u000eH\u0002J\u0010\u00103\u001a\u00020&2\u0006\u0010\u001e\u001a\u00020\u000eH\u0002J\u0012\u00104\u001a\u00020\u00062\b\u0010$\u001a\u0004\u0018\u00010\u0006H\u0002J\b\u00105\u001a\u00020\u0019H\u0002J\"\u00106\u001a\u00020\u00192\u0006\u0010 \u001a\u00020!2\u0012\u00107\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00190\u0017J$\u00108\u001a\u00020\u00192\u0006\u0010 \u001a\u00020!2\u0012\u00107\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00190\u0017H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R6\u0010\u0015\u001a*\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00190\u00170\u0016j\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00190\u0017`\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00069"}, d2 = {"Lmy/haifu/punch/ShellLocation;", "", "()V", "FRESH_MS", "", "FUSED", "", "GOOD_ACCURACY_M", "", "MAX_WAITERS", "", "STALE_MAX_MS", "WINDOW_MS", "best", "Landroid/location/Location;", "listener", "Landroid/location/LocationListener;", "lm", "Landroid/location/LocationManager;", "main", "Landroid/os/Handler;", "waiters", "Ljava/util/ArrayList;", "Lkotlin/Function1;", "Lorg/json/JSONObject;", "", "Lkotlin/collections/ArrayList;", "windowEnd", "Ljava/lang/Runnable;", "ageMs", "loc", "begin", "ctx", "Landroid/content/Context;", "consider", "deliver", "provider", "stale", "", "enabled", "m", "p", "fail", "reason", "finish", "result", "freshestLastKnown", "granted", "isBetter", "a", "b", "isMock", "label", "onWindowClosed", "request", "cb", "start", "app_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ShellLocation {
    private static final long FRESH_MS = 30000;
    private static final String FUSED = "fused";
    private static final float GOOD_ACCURACY_M = 50.0f;
    private static final int MAX_WAITERS = 16;
    private static final long STALE_MAX_MS = 600000;
    private static final long WINDOW_MS = 20000;
    private static Location best;
    private static LocationListener listener;
    private static LocationManager lm;
    public static final ShellLocation INSTANCE = new ShellLocation();
    private static final Handler main = new Handler(Looper.getMainLooper());
    private static final ArrayList<Function1<JSONObject, Unit>> waiters = new ArrayList<>();
    private static final Runnable windowEnd = new Runnable() { // from class: my.haifu.punch.ShellLocation$$ExternalSyntheticLambda2
        @Override // java.lang.Runnable
        public final void run() throws JSONException {
            ShellLocation.windowEnd$lambda$0();
        }
    };

    private ShellLocation() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void windowEnd$lambda$0() throws JSONException {
        INSTANCE.onWindowClosed();
    }

    public final void request(Context ctx, final Function1<? super JSONObject, Unit> cb) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(cb, "cb");
        final Context applicationContext = ctx.getApplicationContext();
        main.post(new Runnable() { // from class: my.haifu.punch.ShellLocation$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ShellLocation.request$lambda$1(applicationContext, cb);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void request$lambda$1(Context context, Function1 cb) {
        Intrinsics.checkNotNullParameter(cb, "$cb");
        ShellLocation shellLocation = INSTANCE;
        Intrinsics.checkNotNull(context);
        shellLocation.start(context, cb);
    }

    private final void start(Context ctx, Function1<? super JSONObject, Unit> cb) {
        ArrayList<Function1<JSONObject, Unit>> arrayList = waiters;
        if (arrayList.size() >= 16) {
            cb.invoke(fail("unavailable"));
            return;
        }
        arrayList.add(cb);
        if (arrayList.size() > 1) {
            return;
        }
        try {
            begin(ctx);
        } catch (SecurityException unused) {
            finish(fail("denied"));
        } catch (Throwable unused2) {
            finish(fail("unavailable"));
        }
    }

    private final boolean granted(Context ctx, String p) {
        return ContextCompat.checkSelfPermission(ctx, p) == 0;
    }

    private final void begin(Context ctx) throws JSONException {
        List<String> listEmptyList;
        boolean zGranted = granted(ctx, "android.permission.ACCESS_FINE_LOCATION");
        boolean zGranted2 = granted(ctx, "android.permission.ACCESS_COARSE_LOCATION");
        String str = "denied";
        if (!zGranted && !zGranted2) {
            finish(fail("denied"));
            return;
        }
        Object systemService = ctx.getSystemService("location");
        LocationManager locationManager = systemService instanceof LocationManager ? (LocationManager) systemService : null;
        if (locationManager == null) {
            finish(fail("unavailable"));
            return;
        }
        if (Build.VERSION.SDK_INT >= 28 && !locationManager.isLocationEnabled()) {
            finish(fail("unavailable"));
            return;
        }
        try {
            listEmptyList = locationManager.getAllProviders();
            Intrinsics.checkNotNull(listEmptyList);
        } catch (Throwable unused) {
            listEmptyList = CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        if (zGranted && listEmptyList.contains("gps") && enabled(locationManager, "gps")) {
            arrayList.add("gps");
        }
        if (listEmptyList.contains("network") && enabled(locationManager, "network")) {
            arrayList.add("network");
        }
        if (Build.VERSION.SDK_INT >= 31 && listEmptyList.contains(FUSED) && enabled(locationManager, FUSED)) {
            arrayList.add(FUSED);
        }
        if (arrayList.isEmpty()) {
            finish(fail("unavailable"));
            return;
        }
        Location locationFreshestLastKnown = freshestLastKnown(locationManager);
        if (locationFreshestLastKnown != null && ageMs(locationFreshestLastKnown) <= FRESH_MS) {
            deliver(locationFreshestLastKnown, "cached", false);
            return;
        }
        lm = locationManager;
        best = null;
        LocationListener locationListener = new LocationListener() { // from class: my.haifu.punch.ShellLocation$begin$l$1
            @Override // android.location.LocationListener
            public void onProviderDisabled(String provider) {
                Intrinsics.checkNotNullParameter(provider, "provider");
            }

            @Override // android.location.LocationListener
            public void onProviderEnabled(String provider) {
                Intrinsics.checkNotNullParameter(provider, "provider");
            }

            @Override // android.location.LocationListener
            @Deprecated(message = "Deprecated in Java")
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override // android.location.LocationListener
            public void onLocationChanged(Location location) throws JSONException {
                Intrinsics.checkNotNullParameter(location, "location");
                ShellLocation.INSTANCE.consider(location);
            }
        };
        listener = locationListener;
        Iterator it = arrayList.iterator();
        int i = 0;
        boolean z = false;
        while (it.hasNext()) {
            try {
                locationManager.requestLocationUpdates((String) it.next(), 1000L, 0.0f, locationListener, Looper.getMainLooper());
                i++;
            } catch (IllegalArgumentException unused2) {
            } catch (SecurityException unused3) {
                z = true;
            }
        }
        if (i == 0) {
            if (!z) {
                str = "unavailable";
            }
            finish(fail(str));
            return;
        }
        main.postDelayed(windowEnd, WINDOW_MS);
    }

    private final boolean enabled(LocationManager m, String p) {
        try {
            return m.isProviderEnabled(p);
        } catch (Throwable unused) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void consider(Location loc) throws JSONException {
        if (listener == null) {
            return;
        }
        if (loc.hasAccuracy() && loc.getAccuracy() <= GOOD_ACCURACY_M) {
            deliver(loc, label(loc.getProvider()), false);
            return;
        }
        Location location = best;
        if (location == null || isBetter(loc, location)) {
            best = loc;
        }
    }

    private final boolean isBetter(Location a, Location b) {
        if (a.hasAccuracy() && !b.hasAccuracy()) {
            return true;
        }
        if (a.hasAccuracy() || !b.hasAccuracy()) {
            if (a.hasAccuracy() && b.hasAccuracy()) {
                if (a.getAccuracy() < b.getAccuracy()) {
                    return true;
                }
            } else if (ageMs(a) < ageMs(b)) {
                return true;
            }
        }
        return false;
    }

    private final void onWindowClosed() throws JSONException {
        if (listener == null) {
            return;
        }
        Location location = best;
        if (location != null) {
            deliver(location, label(location.getProvider()), false);
            return;
        }
        LocationManager locationManager = lm;
        Location locationFreshestLastKnown = locationManager != null ? INSTANCE.freshestLastKnown(locationManager) : null;
        if (locationFreshestLastKnown != null && ageMs(locationFreshestLastKnown) <= STALE_MAX_MS) {
            deliver(locationFreshestLastKnown, "cached", true);
        } else {
            finish(fail("timeout"));
        }
    }

    private final Location freshestLastKnown(LocationManager m) {
        List<String> listEmptyList;
        Location lastKnownLocation;
        try {
            listEmptyList = m.getProviders(true);
            Intrinsics.checkNotNull(listEmptyList);
        } catch (Throwable unused) {
            listEmptyList = CollectionsKt.emptyList();
        }
        Iterator<String> it = listEmptyList.iterator();
        Location location = null;
        while (it.hasNext()) {
            try {
                lastKnownLocation = m.getLastKnownLocation(it.next());
            } catch (Throwable unused2) {
                lastKnownLocation = null;
            }
            if (lastKnownLocation != null && (location == null || ageMs(lastKnownLocation) < ageMs(location))) {
                location = lastKnownLocation;
            }
        }
        return location;
    }

    private final long ageMs(Location loc) {
        long jCurrentTimeMillis;
        long elapsedRealtimeNanos = loc.getElapsedRealtimeNanos();
        if (elapsedRealtimeNanos > 0) {
            jCurrentTimeMillis = (SystemClock.elapsedRealtimeNanos() - elapsedRealtimeNanos) / 1000000;
        } else {
            jCurrentTimeMillis = System.currentTimeMillis() - loc.getTime();
        }
        if (jCurrentTimeMillis < 0) {
            return 0L;
        }
        return jCurrentTimeMillis;
    }

    private final String label(String provider) {
        if (Intrinsics.areEqual(provider, "gps")) {
            return "gps";
        }
        if (Intrinsics.areEqual(provider, "network")) {
            return "network";
        }
        return FUSED;
    }

    private final boolean isMock(Location loc) {
        return Build.VERSION.SDK_INT >= 31 ? loc.isMock() : loc.isFromMockProvider();
    }

    private final void deliver(Location loc, String provider, boolean stale) throws JSONException {
        if (isMock(loc)) {
            finish(fail("mock"));
            return;
        }
        JSONObject jSONObjectPut = new JSONObject().put("ok", true).put("lat", loc.getLatitude()).put("lng", loc.getLongitude()).put("accuracy", loc.hasAccuracy() ? Double.valueOf(Math.round(((double) loc.getAccuracy()) * 10.0d) / 10.0d) : JSONObject.NULL).put("mock", false).put("stale", stale).put("age_s", ageMs(loc) / 1000).put("provider", provider);
        Intrinsics.checkNotNull(jSONObjectPut);
        finish(jSONObjectPut);
    }

    private final JSONObject fail(String reason) throws JSONException {
        JSONObject jSONObjectPut = new JSONObject().put("ok", false).put("reason", reason);
        Intrinsics.checkNotNullExpressionValue(jSONObjectPut, "put(...)");
        return jSONObjectPut;
    }

    private final void finish(JSONObject result) {
        main.removeCallbacks(windowEnd);
        LocationListener locationListener = listener;
        listener = null;
        if (locationListener != null) {
            try {
                LocationManager locationManager = lm;
                if (locationManager != null) {
                    locationManager.removeUpdates(locationListener);
                }
            } catch (Throwable unused) {
            }
        }
        lm = null;
        best = null;
        ArrayList<Function1<JSONObject, Unit>> arrayList = waiters;
        ArrayList arrayList2 = new ArrayList(arrayList);
        arrayList.clear();
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            try {
                ((Function1) it.next()).invoke(result);
            } catch (Throwable unused2) {
            }
        }
    }
}
