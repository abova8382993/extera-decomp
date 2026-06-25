package androidx.work.impl.constraints.trackers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import androidx.work.Logger;
import androidx.work.impl.constraints.NetworkState;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import kotlin.Metadata;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\u001a%\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0001\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0002H\u0007¢\u0006\u0004\b\u0006\u0010\u0007\u001a\u001f\u0010\f\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\nH\u0000¢\u0006\u0004\b\f\u0010\r\"\u0014\u0010\u000f\u001a\u00020\u000e8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u000f\u0010\u0010\"\u0018\u0010\u0011\u001a\u00020\n*\u00020\b8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, m877d2 = {"Landroid/content/Context;", "context", "Landroidx/work/impl/utils/taskexecutor/TaskExecutor;", "taskExecutor", "Landroidx/work/impl/constraints/trackers/ConstraintTracker;", "Landroidx/work/impl/constraints/NetworkState;", "NetworkStateTracker", "(Landroid/content/Context;Landroidx/work/impl/utils/taskexecutor/TaskExecutor;)Landroidx/work/impl/constraints/trackers/ConstraintTracker;", "Landroid/net/ConnectivityManager;", "connectivityManager", _UrlKt.FRAGMENT_ENCODE_SET, "isBlocked", "getActiveNetworkState", "(Landroid/net/ConnectivityManager;Z)Landroidx/work/impl/constraints/NetworkState;", _UrlKt.FRAGMENT_ENCODE_SET, "TAG", "Ljava/lang/String;", "isActiveNetworkValidated", "(Landroid/net/ConnectivityManager;)Z", "work-runtime_release"}, m878k = 2, m879mv = {2, 1, 0}, m881xi = 48)
public abstract class NetworkStateTrackerKt {
    private static final String TAG = Logger.tagWithPrefix("NetworkStateTracker");

    public static final ConstraintTracker<NetworkState> NetworkStateTracker(Context context, TaskExecutor taskExecutor) {
        return new NetworkStateTrackerPre28(context, taskExecutor);
    }

    public static final boolean isActiveNetworkValidated(ConnectivityManager connectivityManager) {
        try {
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (networkCapabilities != null) {
                return networkCapabilities.hasCapability(16);
            }
            return false;
        } catch (SecurityException e) {
            Logger.get().error(TAG, "Unable to validate active network", e);
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0016  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0029  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.work.impl.constraints.NetworkState getActiveNetworkState(android.net.ConnectivityManager r7, boolean r8) {
        /*
            android.net.NetworkInfo r0 = r7.getActiveNetworkInfo()     // Catch: java.lang.SecurityException -> L35
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L16
            boolean r3 = r0.isConnected()     // Catch: java.lang.SecurityException -> L12
            if (r3 == 0) goto L16
            r3 = r1
            r1 = r2
            r4 = r1
            goto L18
        L12:
            r0 = move-exception
            r7 = r0
            r5 = r8
            goto L38
        L16:
            r3 = r1
            r4 = r2
        L18:
            boolean r2 = isActiveNetworkValidated(r7)     // Catch: java.lang.SecurityException -> L35
            boolean r7 = androidx.core.net.ConnectivityManagerCompat.isActiveNetworkMetered(r7)     // Catch: java.lang.SecurityException -> L35
            if (r0 == 0) goto L29
            boolean r0 = r0.isRoaming()     // Catch: java.lang.SecurityException -> L12
            if (r0 != 0) goto L29
            goto L2a
        L29:
            r4 = r3
        L2a:
            androidx.work.impl.constraints.NetworkState r0 = new androidx.work.impl.constraints.NetworkState     // Catch: java.lang.SecurityException -> L35
            r3 = r7
            r5 = r8
            r0.<init>(r1, r2, r3, r4, r5)     // Catch: java.lang.SecurityException -> L32
            return r0
        L32:
            r0 = move-exception
        L33:
            r7 = r0
            goto L38
        L35:
            r0 = move-exception
            r5 = r8
            goto L33
        L38:
            androidx.work.Logger r8 = androidx.work.Logger.get()
            java.lang.String r0 = androidx.work.impl.constraints.trackers.NetworkStateTrackerKt.TAG
            java.lang.String r1 = "Unable to get active network state"
            r8.error(r0, r1, r7)
            androidx.work.impl.constraints.NetworkState r1 = new androidx.work.impl.constraints.NetworkState
            r4 = 0
            r6 = r5
            r5 = 1
            r2 = 0
            r3 = 0
            r1.<init>(r2, r3, r4, r5, r6)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.work.impl.constraints.trackers.NetworkStateTrackerKt.getActiveNetworkState(android.net.ConnectivityManager, boolean):androidx.work.impl.constraints.NetworkState");
    }
}
