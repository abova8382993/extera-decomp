package org.telegram.messenger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.maps.yandex.YandexLocationProvider;
import com.exteragram.messenger.maps.yandex.YandexMapsProvider;
import com.exteragram.messenger.plugins.PluginsController;
import com.exteragram.messenger.plugins.utils.NativeCrashHandler;
import com.exteragram.messenger.updater.UpdateAppAlertDialog;
import com.exteragram.messenger.updater.UpdateLayout;
import com.exteragram.messenger.updater.UpdaterUtils;
import com.exteragram.messenger.utils.chats.ChatUtils;
import com.exteragram.messenger.utils.network.RemoteUtils;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import java.io.File;
import java.lang.Thread;
import java.util.Locale;
import org.json.JSONObject;
import org.lsposed.lsparanoid.Deobfuscator$exteraGramDev$TMessagesProj;
import org.telegram.messenger.PushListenerController;
import org.telegram.messenger.voip.VideoCapturerDevice;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.Components.AlertsCreator;
import org.telegram.p035ui.Components.ForegroundDetector;
import org.telegram.p035ui.Components.ItemOptions;
import org.telegram.p035ui.IUpdateLayout;
import org.telegram.p035ui.LauncherIconController;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC;

/* JADX INFO: loaded from: classes.dex */
public class ApplicationLoader extends Application {

    @SuppressLint({"StaticFieldLeak"})
    public static volatile Context applicationContext;
    public static volatile Handler applicationHandler;
    public static ApplicationLoader applicationLoaderInstance;
    public static boolean canDrawOverlays;
    private static ConnectivityManager connectivityManager;
    public static volatile NetworkInfo currentNetworkInfo;
    private static FirebaseAnalytics firebaseAnalytics;
    private static FirebaseCrashlytics firebaseCrashlytics;
    private static long lastNetworkCheckTypeTime;
    private static ILocationServiceProvider locationServiceProvider;
    public static volatile long mainInterfacePausedStageQueueTime;
    private static IMapsProvider mapsProvider;
    private static volatile ConnectivityManager.NetworkCallback networkCallback;
    private static PushListenerController.IPushListenerServiceProvider pushProvider;
    public static long startTime;
    private static final String PREF_PROXY_AUTO_DISABLED_BY_VPN = Deobfuscator$exteraGramDev$TMessagesProj.getString(-87939832338233L);
    private static final String PREF_PROXY_AUTO_RESTORE_CALLS = Deobfuscator$exteraGramDev$TMessagesProj.getString(-88055796455225L);
    private static volatile boolean applicationInited = false;
    private static int lastKnownNetworkType = -1;
    private static volatile boolean exteraHookEmbeddedConfigured = false;
    public static volatile boolean isScreenOn = false;
    public static volatile boolean mainInterfacePaused = true;
    public static volatile boolean mainInterfaceStopped = true;
    public static volatile boolean externalInterfacePaused = true;
    public static volatile boolean mainInterfacePausedStageQueue = true;
    private static long lastNetworkCheck = -1;

    public static boolean isAndroidTestEnvironment() {
        return false;
    }

    public void addItemOptions(ItemOptions itemOptions) {
    }

    public void cancelDownloadingUpdate() {
    }

    public boolean checkRequestPermissionResult(int i, String[] strArr, int[] iArr) {
        return false;
    }

    public void checkUpdate(boolean z, Runnable runnable) {
    }

    public boolean consumePush(int i, JSONObject jSONObject) {
        return false;
    }

    public void downloadUpdate() {
    }

    public File getDownloadedUpdateFile() {
        return null;
    }

    public float getDownloadingUpdateProgress() {
        return 0.0f;
    }

    public BetaUpdate getUpdate() {
        return null;
    }

    public boolean isAndroidTestEnv() {
        return false;
    }

    public boolean isBeta() {
        return false;
    }

    public boolean isCustomUpdate() {
        return false;
    }

    public boolean isDownloadingUpdate() {
        return false;
    }

    public boolean isStandalone() {
        return false;
    }

    public boolean onPause() {
        return false;
    }

    public void onResume() {
    }

    public boolean onSuggestionClick(String str) {
        return false;
    }

    public boolean onSuggestionFill(String str, CharSequence[] charSequenceArr, boolean[] zArr) {
        return false;
    }

    public BaseFragment openSettings(int i) {
        return null;
    }

    public TLRPC.Update parseTLUpdate(int i) {
        return null;
    }

    public void processUpdate(int i, TLRPC.Update update) {
    }

    public boolean showCustomUpdateAppPopup(Context context, BetaUpdate betaUpdate, int i) {
        return false;
    }

    @Override // android.content.ContextWrapper
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    public static ILocationServiceProvider getLocationServiceProvider() {
        if (locationServiceProvider == null) {
            ILocationServiceProvider iLocationServiceProviderOnCreateLocationServiceProvider = applicationLoaderInstance.onCreateLocationServiceProvider();
            locationServiceProvider = iLocationServiceProviderOnCreateLocationServiceProvider;
            iLocationServiceProviderOnCreateLocationServiceProvider.init(applicationContext);
        }
        return locationServiceProvider;
    }

    public ILocationServiceProvider onCreateLocationServiceProvider() {
        return (allowToUseYandexMaps() && ExteraConfig.getUseYandexMaps()) ? new YandexLocationProvider() : new GoogleLocationProvider();
    }

    public static IMapsProvider getMapsProvider() {
        if (mapsProvider == null) {
            mapsProvider = applicationLoaderInstance.onCreateMapsProvider();
        }
        return mapsProvider;
    }

    public static void updateMapsProvider() {
        mapsProvider = applicationLoaderInstance.onCreateMapsProvider();
        ILocationServiceProvider iLocationServiceProviderOnCreateLocationServiceProvider = applicationLoaderInstance.onCreateLocationServiceProvider();
        locationServiceProvider = iLocationServiceProviderOnCreateLocationServiceProvider;
        iLocationServiceProviderOnCreateLocationServiceProvider.init(applicationContext);
    }

    public IMapsProvider onCreateMapsProvider() {
        return (allowToUseYandexMaps() && ExteraConfig.getUseYandexMaps()) ? new YandexMapsProvider() : new GoogleMapsProvider();
    }

    public boolean allowToUseYandexMaps() {
        if (YandexMapsProvider.isSupported() && Build.VERSION.SDK_INT >= 26) {
            return !RemoteUtils.getBooleanConfigValue(Deobfuscator$exteraGramDev$TMessagesProj.getString(-82257590605625L), false).booleanValue() || ChatUtils.getInstance().isRussianUser() || ChatUtils.getInstance().isFragmentUser();
        }
        return false;
    }

    public static PushListenerController.IPushListenerServiceProvider getPushProvider() {
        if (pushProvider == null) {
            pushProvider = applicationLoaderInstance.onCreatePushProvider();
        }
        return pushProvider;
    }

    public PushListenerController.IPushListenerServiceProvider onCreatePushProvider() {
        return PushListenerController.GooglePushListenerServiceProvider.INSTANCE;
    }

    public static String getApplicationId() {
        return Deobfuscator$exteraGramDev$TMessagesProj.getString(-82343489951545L);
    }

    public static File getFilesDirFixed() {
        for (int i = 0; i < 10; i++) {
            File filesDir = applicationContext.getFilesDir();
            if (filesDir != null) {
                return filesDir;
            }
        }
        try {
            File file = new File(applicationContext.getApplicationInfo().dataDir, Deobfuscator$exteraGramDev$TMessagesProj.getString(-82450864133945L));
            file.mkdirs();
            return file;
        } catch (Exception e) {
            FileLog.m1048e(e);
            return new File(Deobfuscator$exteraGramDev$TMessagesProj.getString(-82476633937721L));
        }
    }

    public static File getFilesDirFixed(String str) {
        try {
            File file = new File(getFilesDirFixed(), str);
            file.mkdirs();
            return file;
        } catch (Exception e) {
            FileLog.m1048e(e);
            return null;
        }
    }

    public static void postInitApplication() {
        if (applicationInited || applicationContext == null) {
            return;
        }
        applicationInited = true;
        NativeLoader.initNativeLibs(applicationContext);
        try {
            LocaleController.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            connectivityManager = (ConnectivityManager) applicationContext.getSystemService(Deobfuscator$exteraGramDev$TMessagesProj.getString(-82657022564153L));
            applicationContext.registerReceiver(new BroadcastReceiver() { // from class: org.telegram.messenger.ApplicationLoader.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    try {
                        ApplicationLoader.currentNetworkInfo = ApplicationLoader.connectivityManager.getActiveNetworkInfo();
                    } catch (Throwable unused) {
                    }
                    ApplicationLoader.checkProxyForVpnState();
                    boolean zIsConnectionSlow = ApplicationLoader.isConnectionSlow();
                    for (int i = 0; i < 16; i++) {
                        ConnectionsManager.getInstance(i).checkConnection();
                        FileLoader.getInstance(i).onNetworkChanged(zIsConnectionSlow);
                    }
                }
            }, new IntentFilter(Deobfuscator$exteraGramDev$TMessagesProj.getString(-82712857139001L)));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            IntentFilter intentFilter = new IntentFilter(Deobfuscator$exteraGramDev$TMessagesProj.getString(-82871770928953L));
            intentFilter.addAction(Deobfuscator$exteraGramDev$TMessagesProj.getString(-83009209882425L));
            applicationContext.registerReceiver(new ScreenReceiver(), intentFilter);
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        try {
            isScreenOn = ((PowerManager) applicationContext.getSystemService(Deobfuscator$exteraGramDev$TMessagesProj.getString(-83150943803193L))).isScreenOn();
            if (BuildVars.LOGS_ENABLED) {
                FileLog.m1045d(Deobfuscator$exteraGramDev$TMessagesProj.getString(-83176713606969L) + isScreenOn);
            }
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        SharedConfig.loadConfig();
        SharedPrefsHelper.init(applicationContext);
        for (int i = 0; i < 16; i++) {
            UserConfig.getInstance(i).loadConfig();
            MessagesController.getInstance(i);
            if (i == 0) {
                SharedConfig.pushStringStatus = Deobfuscator$exteraGramDev$TMessagesProj.getString(-83245433083705L) + ConnectionsManager.getInstance(i).getCurrentTime() + Deobfuscator$exteraGramDev$TMessagesProj.getString(-83369987135289L);
            } else {
                ConnectionsManager.getInstance(i);
            }
            TLRPC.User currentUser = UserConfig.getInstance(i).getCurrentUser();
            if (currentUser != null) {
                MessagesController.getInstance(i).putUser(currentUser, true);
                SendMessagesHelper.getInstance(i).checkUnsentMessages();
            }
        }
        checkProxyForVpnState();
        ((ApplicationLoader) applicationContext).initPushServices();
        if (BuildVars.LOGS_ENABLED) {
            FileLog.m1045d(Deobfuscator$exteraGramDev$TMessagesProj.getString(-83382872037177L));
        }
        MediaController.getInstance();
        for (int i2 = 0; i2 < 16; i2++) {
            ContactsController.getInstance(i2).checkAppAccount();
            DownloadController.getInstance(i2);
        }
        BillingController.getInstance().lambda$onBillingServiceDisconnected$12();
    }

    /* JADX INFO: renamed from: org.telegram.messenger.ApplicationLoader$1 */
    public class C27221 extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            try {
                ApplicationLoader.currentNetworkInfo = ApplicationLoader.connectivityManager.getActiveNetworkInfo();
            } catch (Throwable unused) {
            }
            ApplicationLoader.checkProxyForVpnState();
            boolean zIsConnectionSlow = ApplicationLoader.isConnectionSlow();
            for (int i = 0; i < 16; i++) {
                ConnectionsManager.getInstance(i).checkConnection();
                FileLoader.getInstance(i).onNetworkChanged(zIsConnectionSlow);
            }
        }
    }

    @Override // android.app.Application
    public void onCreate() {
        String str;
        applicationLoaderInstance = this;
        try {
            applicationContext = getApplicationContext();
        } catch (Throwable unused) {
        }
        super.onCreate();
        if (BuildVars.LOGS_ENABLED) {
            StringBuilder sb = new StringBuilder();
            sb.append(Deobfuscator$exteraGramDev$TMessagesProj.getString(-83434411644729L));
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            startTime = jElapsedRealtime;
            sb.append(jElapsedRealtime);
            FileLog.m1045d(sb.toString());
            try {
                PackageInfo packageInfo = applicationContext.getPackageManager().getPackageInfo(applicationContext.getPackageName(), 0);
                int i = packageInfo.versionCode % 10;
                if (i == 1 || i == 2) {
                    str = Deobfuscator$exteraGramDev$TMessagesProj.getString(-83511721056057L) + Build.CPU_ABI + Deobfuscator$exteraGramDev$TMessagesProj.getString(-83576145565497L) + Build.CPU_ABI2;
                } else {
                    str = Deobfuscator$exteraGramDev$TMessagesProj.getString(-83584735500089L) + Build.CPU_ABI + Deobfuscator$exteraGramDev$TMessagesProj.getString(-83631980140345L) + Build.CPU_ABI2;
                }
                FileLog.m1045d(Deobfuscator$exteraGramDev$TMessagesProj.getString(-83640570074937L) + String.format(Locale.US, Deobfuscator$exteraGramDev$TMessagesProj.getString(-83709289551673L), packageInfo.versionName, Integer.valueOf(packageInfo.versionCode / 10), Integer.valueOf(packageInfo.versionCode % 10), str));
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
            FileLog.m1045d(Deobfuscator$exteraGramDev$TMessagesProj.getString(-83778009028409L) + Build.MANUFACTURER + Deobfuscator$exteraGramDev$TMessagesProj.getString(-83876793276217L) + Build.DEVICE + Deobfuscator$exteraGramDev$TMessagesProj.getString(-83919742949177L) + Build.MODEL + Deobfuscator$exteraGramDev$TMessagesProj.getString(-83958397654841L) + Build.PRODUCT);
        }
        if (applicationContext == null) {
            applicationContext = getApplicationContext();
        }
        NativeLoader.initNativeLibs(applicationContext);
        NativeCrashHandler.init(NativeCrashHandler.getCrashFlagPath());
        try {
            ConnectionsManager.native_setJava(false);
            configureEmbeddedExteraHook();
            PluginsController.applyArtOpts();
            new ForegroundDetector(this) { // from class: org.telegram.messenger.ApplicationLoader.2
                public C27232(Application this) {
                    super(this);
                }

                @Override // org.telegram.p035ui.Components.ForegroundDetector, android.app.Application.ActivityLifecycleCallbacks
                public void onActivityStarted(Activity activity) {
                    boolean zIsBackground = isBackground();
                    super.onActivityStarted(activity);
                    if (zIsBackground) {
                        ApplicationLoader.ensureCurrentNetworkGet(true);
                    }
                }
            };
            if (BuildVars.LOGS_ENABLED) {
                FileLog.m1045d(Deobfuscator$exteraGramDev$TMessagesProj.getString(-84198915823417L) + (SystemClock.elapsedRealtime() - startTime));
            }
            if (BuildVars.DEBUG_VERSION || ExteraConfig.getPluginsEngine()) {
                final Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
                Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { // from class: org.telegram.messenger.ApplicationLoader$$ExternalSyntheticLambda1
                    @Override // java.lang.Thread.UncaughtExceptionHandler
                    public final void uncaughtException(Thread thread, Throwable th) {
                        ApplicationLoader.$r8$lambda$NY8LiAdXEiNm7oQtKqIv0_Zxl0c(defaultUncaughtExceptionHandler, thread, th);
                    }
                });
            }
            applicationHandler = new Handler(applicationContext.getMainLooper());
            RemoteUtils.initCached();
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.ApplicationLoader$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    ApplicationLoader.startPushService();
                }
            });
            LauncherIconController.tryFixLauncherIconIfNeeded();
            ProxyRotationController.init();
            ProxyPingController.init();
            ((ApplicationLoader) applicationContext).initFirebase();
        } catch (UnsatisfiedLinkError unused2) {
            throw new RuntimeException(Deobfuscator$exteraGramDev$TMessagesProj.getString(-84005642295097L) + Build.CPU_ABI + Deobfuscator$exteraGramDev$TMessagesProj.getString(-84130196346681L) + NativeLoader.getAbiFolder());
        }
    }

    /* JADX INFO: renamed from: org.telegram.messenger.ApplicationLoader$2 */
    public class C27232 extends ForegroundDetector {
        public C27232(Application this) {
            super(this);
        }

        @Override // org.telegram.p035ui.Components.ForegroundDetector, android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
            boolean zIsBackground = isBackground();
            super.onActivityStarted(activity);
            if (zIsBackground) {
                ApplicationLoader.ensureCurrentNetworkGet(true);
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$NY8LiAdXEiNm7oQtKqIv0_Zxl0c(Thread.UncaughtExceptionHandler uncaughtExceptionHandler, Thread thread, Throwable th) {
        String stackTraceString = Log.getStackTraceString(th);
        try {
            if (ExteraConfig.getPluginsEngine()) {
                SharedPreferences.Editor editorEdit = PluginsController.getInstance().getPreferences().edit();
                editorEdit.putBoolean(Deobfuscator$exteraGramDev$TMessagesProj.getString(-87600529921849L), true).apply();
                String str = null;
                for (String str2 : PluginsController.getInstance().getPlugins().keySet()) {
                    if (stackTraceString.contains(str2)) {
                        str = str2;
                    }
                }
                if (str != null) {
                    editorEdit.putString(Deobfuscator$exteraGramDev$TMessagesProj.getString(-87643479594809L), str).apply();
                    FileLog.m1046e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-87720789006137L) + str);
                }
            }
            ((ClipboardManager) applicationContext.getSystemService(Deobfuscator$exteraGramDev$TMessagesProj.getString(-87871112861497L))).setPrimaryClip(ClipData.newPlainText(Deobfuscator$exteraGramDev$TMessagesProj.getString(-87914062534457L), stackTraceString));
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
        FileLog.m1046e(stackTraceString);
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(thread, th);
        }
    }

    private static void configureEmbeddedExteraHook() {
        if (exteraHookEmbeddedConfigured) {
            return;
        }
        synchronized (ApplicationLoader.class) {
            if (exteraHookEmbeddedConfigured) {
                return;
            }
            try {
                Class.forName(Deobfuscator$exteraGramDev$TMessagesProj.getString(-84276225234745L)).getMethod(Deobfuscator$exteraGramDev$TMessagesProj.getString(-84486678632249L), null).invoke(null, null);
                exteraHookEmbeddedConfigured = true;
            } catch (Throwable th) {
                throw new RuntimeException(Deobfuscator$exteraGramDev$TMessagesProj.getString(-84576872945465L), th);
            }
        }
    }

    @Override // android.app.Application
    public void onTerminate() {
        if (ExteraConfig.getUseYandexMaps()) {
            YandexMapsProvider.terminate();
        }
        super.onTerminate();
    }

    public static void startPushService() {
        boolean z;
        SharedPreferences globalNotificationsSettings = MessagesController.getGlobalNotificationsSettings();
        if (globalNotificationsSettings.contains(Deobfuscator$exteraGramDev$TMessagesProj.getString(-84783031375673L))) {
            z = globalNotificationsSettings.getBoolean(Deobfuscator$exteraGramDev$TMessagesProj.getString(-84834570983225L), true);
        } else {
            z = MessagesController.getMainSettings(UserConfig.selectedAccount).getBoolean(Deobfuscator$exteraGramDev$TMessagesProj.getString(-84886110590777L), false);
        }
        if (z) {
            try {
                applicationContext.startService(new Intent(applicationContext, (Class<?>) NotificationsService.class));
            } catch (Throwable unused) {
            }
        } else {
            applicationContext.stopService(new Intent(applicationContext, (Class<?>) NotificationsService.class));
        }
    }

    @Override // android.app.Application, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        try {
            LocaleController.getInstance().onDeviceConfigurationChange(configuration);
            AndroidUtilities.checkDisplaySize(applicationContext, configuration);
            VideoCapturerDevice.checkScreenCapturerSize();
            AndroidUtilities.resetTabletFlag();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initFirebase() {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.ApplicationLoader$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$initFirebase$1();
            }
        });
    }

    public /* synthetic */ void lambda$initFirebase$1() {
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        FirebaseCrashlytics firebaseCrashlytics2 = FirebaseCrashlytics.getInstance();
        firebaseCrashlytics = firebaseCrashlytics2;
        firebaseCrashlytics2.setCustomKey(Deobfuscator$exteraGramDev$TMessagesProj.getString(-87510335608633L), Deobfuscator$exteraGramDev$TMessagesProj.getString(BuildVars.IS_LITE_VERSION ? -87557580248889L : -87579055085369L));
        firebaseAnalytics.setAnalyticsCollectionEnabled(ExteraConfig.getUseGoogleAnalytics());
        firebaseCrashlytics.setCrashlyticsCollectionEnabled(ExteraConfig.getUseGoogleCrashlytics());
    }

    public static FirebaseAnalytics getFirebaseAnalytics() {
        return firebaseAnalytics;
    }

    public static FirebaseCrashlytics getFirebaseCrashlytics() {
        return firebaseCrashlytics;
    }

    private void initPushServices() {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.ApplicationLoader$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ApplicationLoader.$r8$lambda$Dg08OQoz1Ha7gWZHgwTNhzwKwfw();
            }
        }, 1000L);
    }

    public static /* synthetic */ void $r8$lambda$Dg08OQoz1Ha7gWZHgwTNhzwKwfw() {
        if (getPushProvider().hasServices()) {
            getPushProvider().onRequestPushToken();
            return;
        }
        if (BuildVars.LOGS_ENABLED) {
            FileLog.m1045d(Deobfuscator$exteraGramDev$TMessagesProj.getString(-87295587243833L) + getPushProvider().getLogTitle() + Deobfuscator$exteraGramDev$TMessagesProj.getString(-87338536916793L));
        }
        SharedConfig.pushStringStatus = Deobfuscator$exteraGramDev$TMessagesProj.getString(-87390076524345L);
        PushListenerController.sendRegistrationToServer(getPushProvider().getPushType(), null);
    }

    private boolean checkPlayServices() {
        try {
            return GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == 0;
        } catch (Exception e) {
            FileLog.m1048e(e);
            return true;
        }
    }

    private static void ensureCurrentNetworkGet() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        ensureCurrentNetworkGet(jCurrentTimeMillis - lastNetworkCheck > 5000);
        lastNetworkCheck = jCurrentTimeMillis;
    }

    public static void ensureCurrentNetworkGet(boolean z) {
        if (z || currentNetworkInfo == null) {
            try {
                if (connectivityManager == null) {
                    connectivityManager = (ConnectivityManager) applicationContext.getSystemService(Deobfuscator$exteraGramDev$TMessagesProj.getString(-84959125034809L));
                }
                currentNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkCallback == null) {
                    networkCallback = new ConnectivityManager.NetworkCallback() { // from class: org.telegram.messenger.ApplicationLoader.3
                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public void onAvailable(Network network) {
                            ApplicationLoader.lastKnownNetworkType = -1;
                            ApplicationLoader.checkProxyForVpnState();
                        }

                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                            ApplicationLoader.lastKnownNetworkType = -1;
                            ApplicationLoader.checkProxyForVpnState();
                        }

                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public void onLost(Network network) {
                            ApplicationLoader.lastKnownNetworkType = -1;
                            ApplicationLoader.checkProxyForVpnState();
                        }
                    };
                    connectivityManager.registerDefaultNetworkCallback(networkCallback);
                }
            } catch (Throwable unused) {
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.messenger.ApplicationLoader$3 */
    public class C27243 extends ConnectivityManager.NetworkCallback {
        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(Network network) {
            ApplicationLoader.lastKnownNetworkType = -1;
            ApplicationLoader.checkProxyForVpnState();
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            ApplicationLoader.lastKnownNetworkType = -1;
            ApplicationLoader.checkProxyForVpnState();
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(Network network) {
            ApplicationLoader.lastKnownNetworkType = -1;
            ApplicationLoader.checkProxyForVpnState();
        }
    }

    public static void checkProxyForVpnState() {
        if (applicationContext == null) {
            return;
        }
        try {
            boolean zIsVpnEnabled = isVpnEnabled();
            SharedPreferences globalMainSettings = MessagesController.getGlobalMainSettings();
            boolean z = globalMainSettings.getBoolean(Deobfuscator$exteraGramDev$TMessagesProj.getString(-85014959609657L), false);
            if (!ExteraConfig.getDoNotUseProxyWithVpn()) {
                if (z && !zIsVpnEnabled) {
                    String string = globalMainSettings.getString(Deobfuscator$exteraGramDev$TMessagesProj.getString(-85130923726649L), Deobfuscator$exteraGramDev$TMessagesProj.getString(-85169578432313L));
                    int i = globalMainSettings.getInt(Deobfuscator$exteraGramDev$TMessagesProj.getString(-85173873399609L), 1080);
                    String string2 = globalMainSettings.getString(Deobfuscator$exteraGramDev$TMessagesProj.getString(-85221118039865L), Deobfuscator$exteraGramDev$TMessagesProj.getString(-85268362680121L));
                    String string3 = globalMainSettings.getString(Deobfuscator$exteraGramDev$TMessagesProj.getString(-85272657647417L), Deobfuscator$exteraGramDev$TMessagesProj.getString(-85319902287673L));
                    String string4 = globalMainSettings.getString(Deobfuscator$exteraGramDev$TMessagesProj.getString(-85324197254969L), Deobfuscator$exteraGramDev$TMessagesProj.getString(-85380031829817L));
                    boolean z2 = globalMainSettings.getBoolean(Deobfuscator$exteraGramDev$TMessagesProj.getString(-85384326797113L), false);
                    SharedPreferences.Editor editorRemove = globalMainSettings.edit().putBoolean(Deobfuscator$exteraGramDev$TMessagesProj.getString(-85491700979513L), false).remove(Deobfuscator$exteraGramDev$TMessagesProj.getString(-85607665096505L));
                    if (!TextUtils.isEmpty(string)) {
                        editorRemove.putBoolean(Deobfuscator$exteraGramDev$TMessagesProj.getString(-85715039278905L), true);
                        editorRemove.putBoolean(Deobfuscator$exteraGramDev$TMessagesProj.getString(-85775168821049L), z2);
                    }
                    editorRemove.apply();
                    if (TextUtils.isEmpty(string)) {
                        return;
                    }
                    ConnectionsManager.setProxySettings(true, string, i, string2, string3, string4);
                    NotificationCenter.getGlobalInstance().postNotificationNameOnUIThread(NotificationCenter.proxySettingsChanged, new Object[0]);
                    return;
                }
                return;
            }
            if (zIsVpnEnabled) {
                if (z) {
                    return;
                }
                boolean z3 = globalMainSettings.getBoolean(Deobfuscator$exteraGramDev$TMessagesProj.getString(-85861068166969L), false);
                String string5 = globalMainSettings.getString(Deobfuscator$exteraGramDev$TMessagesProj.getString(-85921197709113L), Deobfuscator$exteraGramDev$TMessagesProj.getString(-85959852414777L));
                if (z3 && !TextUtils.isEmpty(string5)) {
                    globalMainSettings.edit().putBoolean(Deobfuscator$exteraGramDev$TMessagesProj.getString(-86050046727993L), true).putBoolean(Deobfuscator$exteraGramDev$TMessagesProj.getString(-86166010844985L), globalMainSettings.getBoolean(Deobfuscator$exteraGramDev$TMessagesProj.getString(-85964147382073L), false)).putBoolean(Deobfuscator$exteraGramDev$TMessagesProj.getString(-86273385027385L), false).apply();
                    ConnectionsManager.setProxySettings(false, Deobfuscator$exteraGramDev$TMessagesProj.getString(-86333514569529L), 0, Deobfuscator$exteraGramDev$TMessagesProj.getString(-86337809536825L), Deobfuscator$exteraGramDev$TMessagesProj.getString(-86342104504121L), Deobfuscator$exteraGramDev$TMessagesProj.getString(-86346399471417L));
                    NotificationCenter.getGlobalInstance().postNotificationNameOnUIThread(NotificationCenter.proxySettingsChanged, new Object[0]);
                    return;
                }
                return;
            }
            if (z) {
                String string6 = globalMainSettings.getString(Deobfuscator$exteraGramDev$TMessagesProj.getString(-86350694438713L), Deobfuscator$exteraGramDev$TMessagesProj.getString(-86389349144377L));
                int i2 = globalMainSettings.getInt(Deobfuscator$exteraGramDev$TMessagesProj.getString(-86393644111673L), 1080);
                String string7 = globalMainSettings.getString(Deobfuscator$exteraGramDev$TMessagesProj.getString(-86440888751929L), Deobfuscator$exteraGramDev$TMessagesProj.getString(-86488133392185L));
                String string8 = globalMainSettings.getString(Deobfuscator$exteraGramDev$TMessagesProj.getString(-86492428359481L), Deobfuscator$exteraGramDev$TMessagesProj.getString(-86539672999737L));
                String string9 = globalMainSettings.getString(Deobfuscator$exteraGramDev$TMessagesProj.getString(-86543967967033L), Deobfuscator$exteraGramDev$TMessagesProj.getString(-86599802541881L));
                boolean z4 = globalMainSettings.getBoolean(Deobfuscator$exteraGramDev$TMessagesProj.getString(-86604097509177L), false);
                SharedPreferences.Editor editorRemove2 = globalMainSettings.edit().putBoolean(Deobfuscator$exteraGramDev$TMessagesProj.getString(-86711471691577L), false).remove(Deobfuscator$exteraGramDev$TMessagesProj.getString(-86827435808569L));
                if (!TextUtils.isEmpty(string6)) {
                    editorRemove2.putBoolean(Deobfuscator$exteraGramDev$TMessagesProj.getString(-86934809990969L), true);
                    editorRemove2.putBoolean(Deobfuscator$exteraGramDev$TMessagesProj.getString(-86994939533113L), z4);
                }
                editorRemove2.apply();
                if (TextUtils.isEmpty(string6)) {
                    return;
                }
                ConnectionsManager.setProxySettings(true, string6, i2, string7, string8, string9);
                NotificationCenter.getGlobalInstance().postNotificationNameOnUIThread(NotificationCenter.proxySettingsChanged, new Object[0]);
            }
        } catch (Throwable th) {
            FileLog.m1048e(th);
        }
    }

    private static boolean isVpnEnabled() {
        NetworkCapabilities networkCapabilities;
        try {
            if (connectivityManager == null) {
                connectivityManager = (ConnectivityManager) applicationContext.getSystemService(Deobfuscator$exteraGramDev$TMessagesProj.getString(-87080838879033L));
            }
            ConnectivityManager connectivityManager2 = connectivityManager;
            if (connectivityManager2 == null) {
                return false;
            }
            Network activeNetwork = connectivityManager2.getActiveNetwork();
            if (activeNetwork != null && (networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)) != null && networkCapabilities.hasTransport(4)) {
                return true;
            }
            ensureCurrentNetworkGet(false);
            if (currentNetworkInfo != null) {
                if (currentNetworkInfo.getType() == 17) {
                    return true;
                }
            }
        } catch (Throwable unused) {
        }
        return false;
    }

    public static boolean isRoaming() {
        try {
            ensureCurrentNetworkGet(false);
            if (currentNetworkInfo != null) {
                if (currentNetworkInfo.isRoaming()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            FileLog.m1048e(e);
            return false;
        }
    }

    public static boolean isConnectedOrConnectingToWiFi() {
        try {
            ensureCurrentNetworkGet(false);
            if (currentNetworkInfo != null && (currentNetworkInfo.getType() == 1 || currentNetworkInfo.getType() == 9)) {
                NetworkInfo.State state = currentNetworkInfo.getState();
                if (state != NetworkInfo.State.CONNECTED && state != NetworkInfo.State.CONNECTING) {
                    if (state == NetworkInfo.State.SUSPENDED) {
                    }
                }
                return true;
            }
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
        return false;
    }

    public static boolean isConnectedToWiFi() {
        try {
            ensureCurrentNetworkGet(false);
            if (currentNetworkInfo != null && (currentNetworkInfo.getType() == 1 || currentNetworkInfo.getType() == 9)) {
                if (currentNetworkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
        return false;
    }

    public static boolean isConnectionSlow() {
        try {
            ensureCurrentNetworkGet(false);
            if (currentNetworkInfo != null && currentNetworkInfo.getType() == 0) {
                int subtype = currentNetworkInfo.getSubtype();
                if (subtype == 1 || subtype == 2 || subtype == 4 || subtype == 7 || subtype == 11) {
                    return true;
                }
            }
        } catch (Throwable unused) {
        }
        return false;
    }

    public static int getAutodownloadNetworkType() {
        try {
            ensureCurrentNetworkGet(false);
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
        if (currentNetworkInfo == null) {
            return 0;
        }
        if (currentNetworkInfo.getType() != 1 && currentNetworkInfo.getType() != 9) {
            return currentNetworkInfo.isRoaming() ? 2 : 0;
        }
        int i = lastKnownNetworkType;
        if ((i == 0 || i == 1) && System.currentTimeMillis() - lastNetworkCheckTypeTime < 5000) {
            return lastKnownNetworkType;
        }
        if (connectivityManager.isActiveNetworkMetered()) {
            lastKnownNetworkType = 0;
        } else {
            lastKnownNetworkType = 1;
        }
        lastNetworkCheckTypeTime = System.currentTimeMillis();
        return lastKnownNetworkType;
    }

    public static int getCurrentNetworkType() {
        if (isConnectedOrConnectingToWiFi()) {
            return 1;
        }
        return isRoaming() ? 2 : 0;
    }

    public static boolean isNetworkOnlineFast() {
        try {
            ensureCurrentNetworkGet(false);
            if (currentNetworkInfo != null && !currentNetworkInfo.isConnectedOrConnecting() && !currentNetworkInfo.isAvailable()) {
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(0);
                if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                    return true;
                }
                NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(1);
                if (networkInfo2 != null) {
                    if (networkInfo2.isConnectedOrConnecting()) {
                        return true;
                    }
                }
                return false;
            }
            return true;
        } catch (Exception e) {
            FileLog.m1048e(e);
            return true;
        }
    }

    public static boolean isNetworkOnlineRealtime() {
        try {
            ConnectivityManager connectivityManager2 = (ConnectivityManager) applicationContext.getSystemService(Deobfuscator$exteraGramDev$TMessagesProj.getString(-87136673453881L));
            NetworkInfo activeNetworkInfo = connectivityManager2.getActiveNetworkInfo();
            if (activeNetworkInfo == null || (!activeNetworkInfo.isConnectedOrConnecting() && !activeNetworkInfo.isAvailable())) {
                NetworkInfo networkInfo = connectivityManager2.getNetworkInfo(0);
                if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                    return true;
                }
                NetworkInfo networkInfo2 = connectivityManager2.getNetworkInfo(1);
                if (networkInfo2 != null) {
                    if (networkInfo2.isConnectedOrConnecting()) {
                        return true;
                    }
                }
                return false;
            }
            return true;
        } catch (Exception e) {
            FileLog.m1048e(e);
            return true;
        }
    }

    public static boolean isNetworkOnline() {
        boolean zIsNetworkOnlineRealtime = isNetworkOnlineRealtime();
        if (BuildVars.DEBUG_PRIVATE_VERSION && zIsNetworkOnlineRealtime != isNetworkOnlineFast()) {
            FileLog.m1045d(Deobfuscator$exteraGramDev$TMessagesProj.getString(-87192508028729L));
        }
        return zIsNetworkOnlineRealtime;
    }

    public boolean checkApkInstallPermissions(Context context) {
        if (Build.VERSION.SDK_INT < 26 || applicationContext.getPackageManager().canRequestPackageInstalls()) {
            return true;
        }
        AlertsCreator.createApkRestrictedDialog(context, null).show();
        return false;
    }

    public boolean openApkInstall(Activity activity, TLRPC.Document document) {
        UpdaterUtils.installUpdate(activity, document);
        return true;
    }

    public boolean showUpdateAppPopup(Activity activity, TLRPC.TL_help_appUpdate tL_help_appUpdate, int i) {
        try {
            new UpdateAppAlertDialog(activity, tL_help_appUpdate, i).show();
            return true;
        } catch (Exception e) {
            FileLog.m1048e(e);
            return true;
        }
    }

    public IUpdateLayout takeUpdateLayout(Activity activity, ViewGroup viewGroup) {
        return new UpdateLayout(activity, viewGroup);
    }
}
