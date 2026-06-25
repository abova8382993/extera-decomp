package com.exteragram.messenger.plugins.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import com.exteragram.messenger.plugins.Plugin;
import com.exteragram.messenger.plugins.PluginsController;
import com.exteragram.messenger.plugins.pip.PipController;
import com.exteragram.messenger.plugins.utils.PluginsWatchdog;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import okhttp3.internal.url._UrlKt;
import org.lsposed.lsparanoid.Deobfuscator$exteraGramDev$TMessagesProj;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.p035ui.ActionBar.AlertDialog;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.LaunchActivity;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u0000 \u001d2\u00020\u0001:\u0002\u001c\u001dB\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\u0010\u001a\u00020\u0011J\u0006\u0010\u0012\u001a\u00020\u0011J\u0010\u0010\u0013\u001a\u00020\u00112\b\u0010\u0014\u001a\u0004\u0018\u00010\rJ\u000e\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\rJ\u0018\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\r2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018J\u0018\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\r2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018J\u0010\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\rH\u0002J\u0012\u0010\u001b\u001a\u00020\u00112\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, m877d2 = {"Lcom/exteragram/messenger/plugins/utils/PluginsWatchdog;", _UrlKt.FRAGMENT_ENCODE_SET, "controller", "Lcom/exteragram/messenger/plugins/PluginsController;", "<init>", "(Lcom/exteragram/messenger/plugins/PluginsController;)V", "executingPlugins", "Ljava/util/concurrent/ConcurrentHashMap;", "Ljava/lang/Thread;", "Lcom/exteragram/messenger/plugins/utils/PluginsWatchdog$ExecutionInfo;", "scheduler", "Ljava/util/concurrent/ScheduledExecutorService;", "lastReportedFrozenPluginId", _UrlKt.FRAGMENT_ENCODE_SET, "watchdogRunnable", "Ljava/lang/Runnable;", "start", _UrlKt.FRAGMENT_ENCODE_SET, "stop", "onPluginExecutionStarted", "pluginId", "onPluginExecutionFinished", "forceDisablePlugin", "activity", "Landroid/app/Activity;", "forceDeletePlugin", "disablePluginPref", "restartApp", "ExecutionInfo", "Companion", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nPluginsWatchdog.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PluginsWatchdog.kt\ncom/exteragram/messenger/plugins/utils/PluginsWatchdog\n+ 2 SharedPreferences.kt\nandroidx/core/content/SharedPreferencesKt\n*L\n1#1,207:1\n41#2,12:208\n*S KotlinDebug\n*F\n+ 1 PluginsWatchdog.kt\ncom/exteragram/messenger/plugins/utils/PluginsWatchdog\n*L\n142#1:208,12\n*E\n"})
public final class PluginsWatchdog {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final PluginsController controller;
    private final ConcurrentHashMap<Thread, ExecutionInfo> executingPlugins;
    private String lastReportedFrozenPluginId;
    private ScheduledExecutorService scheduler;
    private final Runnable watchdogRunnable;

    @JvmStatic
    public static final void showNotRespondingAlert(Plugin plugin) {
        INSTANCE.showNotRespondingAlert(plugin);
    }

    public PluginsWatchdog(PluginsController pluginsController) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-39943572805433L);
        this.controller = pluginsController;
        this.executingPlugins = new ConcurrentHashMap<>();
        this.watchdogRunnable = new Runnable() { // from class: com.exteragram.messenger.plugins.utils.PluginsWatchdog$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                PluginsWatchdog.m2590$r8$lambda$QpEjSv2C4kv1sbI51Cm7Qio1o(this.f$0);
            }
        };
    }

    @Metadata(m876d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"}, m877d2 = {"Lcom/exteragram/messenger/plugins/utils/PluginsWatchdog$ExecutionInfo;", _UrlKt.FRAGMENT_ENCODE_SET, "pluginId", _UrlKt.FRAGMENT_ENCODE_SET, "startTime", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "(Ljava/lang/String;J)V", "getPluginId", "()Ljava/lang/String;", "getStartTime", "()J", "component1", "component2", "copy", "equals", _UrlKt.FRAGMENT_ENCODE_SET, "other", "hashCode", _UrlKt.FRAGMENT_ENCODE_SET, "toString", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
    public static final /* data */ class ExecutionInfo {
        private final String pluginId;
        private final long startTime;

        public static /* synthetic */ ExecutionInfo copy$default(ExecutionInfo executionInfo, String str, long j, int i, Object obj) {
            if ((i & 1) != 0) {
                str = executionInfo.pluginId;
            }
            if ((i & 2) != 0) {
                j = executionInfo.startTime;
            }
            return executionInfo.copy(str, j);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getPluginId() {
            return this.pluginId;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final long getStartTime() {
            return this.startTime;
        }

        public final ExecutionInfo copy(String pluginId, long startTime) {
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-41137573713721L);
            return new ExecutionInfo(pluginId, startTime);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ExecutionInfo)) {
                return false;
            }
            ExecutionInfo executionInfo = (ExecutionInfo) other;
            return Intrinsics.areEqual(this.pluginId, executionInfo.pluginId) && this.startTime == executionInfo.startTime;
        }

        public int hashCode() {
            return (this.pluginId.hashCode() * 31) + Long.hashCode(this.startTime);
        }

        public String toString() {
            return Deobfuscator$exteraGramDev$TMessagesProj.getString(-41176228419385L) + this.pluginId + Deobfuscator$exteraGramDev$TMessagesProj.getString(-41279307634489L) + this.startTime + ')';
        }

        public ExecutionInfo(String str, long j) {
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-41098919008057L);
            this.pluginId = str;
            this.startTime = j;
        }

        public final String getPluginId() {
            return this.pluginId;
        }

        public final long getStartTime() {
            return this.startTime;
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$QpE-jSv2C4kv1sbI5-1Cm7Qio1o, reason: not valid java name */
    public static void m2590$r8$lambda$QpEjSv2C4kv1sbI51Cm7Qio1o(PluginsWatchdog pluginsWatchdog) {
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            long j = 0;
            String pluginId = null;
            for (ExecutionInfo executionInfo : pluginsWatchdog.executingPlugins.values()) {
                Deobfuscator$exteraGramDev$TMessagesProj.getString(-40416019207993L);
                ExecutionInfo executionInfo2 = executionInfo;
                long startTime = jCurrentTimeMillis - executionInfo2.getStartTime();
                if (startTime > 5000 && startTime > j) {
                    pluginId = executionInfo2.getPluginId();
                    j = startTime;
                }
            }
            String str = pluginsWatchdog.lastReportedFrozenPluginId;
            if (pluginId != null) {
                if (Intrinsics.areEqual(pluginId, str)) {
                    return;
                }
                pluginsWatchdog.lastReportedFrozenPluginId = pluginId;
                Plugin plugin = pluginsWatchdog.controller.getPlugins().get(pluginId);
                if (plugin != null) {
                    plugin.setNotResponding(true);
                }
                NotificationCenter.getGlobalInstance().postNotificationNameOnUIThread(NotificationCenter.pluginIsNotResponding, new Object[0]);
                return;
            }
            if (str != null) {
                Plugin plugin2 = pluginsWatchdog.controller.getPlugins().get(pluginsWatchdog.lastReportedFrozenPluginId);
                if (plugin2 != null) {
                    plugin2.setNotResponding(false);
                }
                NotificationCenter.getGlobalInstance().postNotificationNameOnUIThread(NotificationCenter.pluginIsNotResponding, new Object[0]);
                pluginsWatchdog.lastReportedFrozenPluginId = null;
            }
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
    }

    public final void start() {
        ScheduledExecutorService scheduledExecutorService = this.scheduler;
        if (scheduledExecutorService == null || scheduledExecutorService.isShutdown()) {
            ScheduledExecutorService scheduledExecutorServiceNewSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
            scheduledExecutorServiceNewSingleThreadScheduledExecutor.scheduleWithFixedDelay(this.watchdogRunnable, 1L, 1L, TimeUnit.SECONDS);
            this.scheduler = scheduledExecutorServiceNewSingleThreadScheduledExecutor;
        }
    }

    public final void stop() {
        ScheduledExecutorService scheduledExecutorService = this.scheduler;
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdownNow();
        }
        this.scheduler = null;
        this.lastReportedFrozenPluginId = null;
        this.executingPlugins.clear();
    }

    public final void onPluginExecutionStarted(String pluginId) {
        if (pluginId == null) {
            return;
        }
        this.executingPlugins.put(Thread.currentThread(), new ExecutionInfo(pluginId, System.currentTimeMillis()));
    }

    public final void onPluginExecutionFinished(String pluginId) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-39990817445689L);
        Thread threadCurrentThread = Thread.currentThread();
        ExecutionInfo executionInfo = this.executingPlugins.get(threadCurrentThread);
        if (executionInfo == null || !Intrinsics.areEqual(executionInfo.getPluginId(), pluginId)) {
            return;
        }
        this.executingPlugins.remove(threadCurrentThread);
        if (Intrinsics.areEqual(pluginId, this.lastReportedFrozenPluginId)) {
            Plugin plugin = this.controller.getPlugins().get(pluginId);
            if (plugin != null) {
                plugin.setNotResponding(false);
            }
            this.lastReportedFrozenPluginId = null;
            NotificationCenter.getGlobalInstance().postNotificationNameOnUIThread(NotificationCenter.pluginIsNotResponding, new Object[0]);
        }
    }

    public final void forceDisablePlugin(String pluginId, Activity activity) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-40029472151353L);
        disablePluginPref(pluginId);
        restartApp(activity);
    }

    public final void forceDeletePlugin(String pluginId, Activity activity) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-40068126857017L);
        disablePluginPref(pluginId);
        PluginsController.INSTANCE.setPluginPinned(pluginId, false);
        this.controller.cleanupPlugin(pluginId);
        try {
            PipController.INSTANCE.uninstallDependencies(pluginId);
        } catch (Exception e) {
            FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-40106781562681L) + pluginId, e);
        }
        this.controller.clearPluginSettingsPreferences(pluginId, true);
        new File(this.controller.getPluginsDir(), pluginId + Deobfuscator$exteraGramDev$TMessagesProj.getString(-40330119862073L)).delete();
        this.controller.getPlugins().remove(pluginId);
        this.controller.notifyPluginsChanged();
        restartApp(activity);
    }

    private final void disablePluginPref(String pluginId) {
        SharedPreferences.Editor editorEdit = this.controller.getPreferences().edit();
        editorEdit.putBoolean(Deobfuscator$exteraGramDev$TMessagesProj.getString(-40347299731257L) + pluginId, false);
        editorEdit.apply();
    }

    private final void restartApp(final Activity activity) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.plugins.utils.PluginsWatchdog$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                PluginsWatchdog.m2589$r8$lambda$C6oS2ADJb9prdSSfDfoeRfN2sA(activity);
            }
        }, 200L);
    }

    /* JADX INFO: renamed from: $r8$lambda$C6oS2ADJb9prd-SSfDfoeRfN2sA, reason: not valid java name */
    public static void m2589$r8$lambda$C6oS2ADJb9prdSSfDfoeRfN2sA(Activity activity) {
        if (activity != null) {
            PackageManager packageManager = activity.getPackageManager();
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-40458968880953L);
            Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(activity.getPackageName());
            activity.finishAffinity();
            if (launchIntentForPackage != null) {
                activity.startActivity(launchIntentForPackage);
            }
        }
        System.exit(0);
        throw new RuntimeException(Deobfuscator$exteraGramDev$TMessagesProj.getString(-40557753128761L));
    }

    /* JADX INFO: loaded from: classes4.dex */
    @Metadata(m876d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007¨\u0006\b"}, m877d2 = {"Lcom/exteragram/messenger/plugins/utils/PluginsWatchdog$Companion;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "showNotRespondingAlert", _UrlKt.FRAGMENT_ENCODE_SET, "plugin", "Lcom/exteragram/messenger/plugins/Plugin;", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final void showNotRespondingAlert(final Plugin plugin) {
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-42494783379257L);
            BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
            if (safeLastFragment == null) {
                return;
            }
            final Activity parentActivity = safeLastFragment.getParentActivity();
            AlertDialog alertDialogCreate = new AlertDialog.Builder(parentActivity, safeLastFragment.getResourceProvider()).setTitle(LocaleController.formatString(C2797R.string.PluginIsNotRespondingAlert, plugin.getName())).setItems(new String[]{LocaleController.getString(C2797R.string.WaitMore), LocaleController.getString(C2797R.string.Disable), LocaleController.getString(C2797R.string.Delete)}, new int[]{C2797R.drawable.msg_recent, C2797R.drawable.msg_block, C2797R.drawable.msg_delete}, new DialogInterface.OnClickListener() { // from class: com.exteragram.messenger.plugins.utils.PluginsWatchdog$Companion$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    PluginsWatchdog.Companion.$r8$lambda$YVUu_yybweI20Wq2RsPMfENiUXc(plugin, parentActivity, dialogInterface, i);
                }
            }).create();
            alertDialogCreate.show();
            alertDialogCreate.setItemColor(alertDialogCreate.getItemsCount() - 1, Theme.getColor(Theme.key_text_RedBold), Theme.getColor(Theme.key_text_RedRegular));
        }

        public static void $r8$lambda$YVUu_yybweI20Wq2RsPMfENiUXc(Plugin plugin, Activity activity, DialogInterface dialogInterface, int i) {
            if (i == 1) {
                PluginsController.INSTANCE.getInstance().getWatchdog().forceDisablePlugin(plugin.getId(), activity);
            } else {
                if (i != 2) {
                    return;
                }
                PluginsController.INSTANCE.getInstance().getWatchdog().forceDeletePlugin(plugin.getId(), activity);
            }
        }
    }
}
