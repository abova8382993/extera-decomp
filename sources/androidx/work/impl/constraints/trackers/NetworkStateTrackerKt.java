package androidx.work.impl.constraints.trackers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import androidx.work.Logger;
import androidx.work.impl.constraints.NetworkState;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: loaded from: classes.dex */
public abstract class NetworkStateTrackerKt {
    private static final String TAG;

    public static final ConstraintTracker NetworkStateTracker(Context context, TaskExecutor taskExecutor) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(taskExecutor, "taskExecutor");
        if (Build.VERSION.SDK_INT >= 24) {
            return new NetworkStateTracker24(context, taskExecutor);
        }
        return new NetworkStateTrackerPre24(context, taskExecutor);
    }

    static {
        String strTagWithPrefix = Logger.tagWithPrefix("NetworkStateTracker");
        Intrinsics.checkNotNullExpressionValue(strTagWithPrefix, "tagWithPrefix(...)");
        TAG = strTagWithPrefix;
    }

    public static final boolean isActiveNetworkValidated(ConnectivityManager connectivityManager) {
        Intrinsics.checkNotNullParameter(connectivityManager, "<this>");
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

    /* JADX WARN: Removed duplicated region for block: B:11:0x001b  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x002e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.work.impl.constraints.NetworkState getActiveNetworkState(android.net.ConnectivityManager r7, boolean r8) {
        /*
            java.lang.String r0 = "connectivityManager"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r7, r0)
            android.net.NetworkInfo r0 = r7.getActiveNetworkInfo()     // Catch: java.lang.SecurityException -> L3a
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L1b
            boolean r3 = r0.isConnected()     // Catch: java.lang.SecurityException -> L17
            if (r3 == 0) goto L1b
            r3 = r1
            r1 = r2
            r4 = r1
            goto L1d
        L17:
            r0 = move-exception
            r7 = r0
            r5 = r8
            goto L3d
        L1b:
            r3 = r1
            r4 = r2
        L1d:
            boolean r2 = isActiveNetworkValidated(r7)     // Catch: java.lang.SecurityException -> L3a
            boolean r7 = androidx.core.net.ConnectivityManagerCompat.isActiveNetworkMetered(r7)     // Catch: java.lang.SecurityException -> L3a
            if (r0 == 0) goto L2e
            boolean r0 = r0.isRoaming()     // Catch: java.lang.SecurityException -> L17
            if (r0 != 0) goto L2e
            goto L2f
        L2e:
            r4 = r3
        L2f:
            androidx.work.impl.constraints.NetworkState r0 = new androidx.work.impl.constraints.NetworkState     // Catch: java.lang.SecurityException -> L3a
            r3 = r7
            r5 = r8
            r0.<init>(r1, r2, r3, r4, r5)     // Catch: java.lang.SecurityException -> L37
            return r0
        L37:
            r0 = move-exception
        L38:
            r7 = r0
            goto L3d
        L3a:
            r0 = move-exception
            r5 = r8
            goto L38
        L3d:
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

    public static final NetworkState getActiveNetworkState(NetworkCapabilities capabilities, boolean z) {
        Intrinsics.checkNotNullParameter(capabilities, "capabilities");
        return new NetworkState(capabilities.hasCapability(12), capabilities.hasCapability(16), !capabilities.hasCapability(11), capabilities.hasCapability(18), z);
    }
}
