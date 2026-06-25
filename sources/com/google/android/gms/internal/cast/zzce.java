package com.google.android.gms.internal.cast;

import android.annotation.TargetApi;
import android.os.Handler;
import android.os.Looper;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.mediarouter.media.MediaRouter;
import androidx.mediarouter.media.RouteListingPreference;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.SessionState;
import com.google.android.gms.cast.framework.CastOptions;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.SessionManager;
import com.google.android.gms.cast.framework.SessionTransferCallback;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.internal.Logger;
import com.google.android.gms.common.internal.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: classes4.dex */
@TargetApi(30)
public final class zzce {
    public static final /* synthetic */ int $r8$clinit = 0;
    private static final Logger zzb = new Logger("SessionTransController");
    private final CastOptions zzc;
    private boolean zzh;
    private SessionManager zzi;
    private CallbackToFutureAdapter.Completer zzj;
    private SessionState zzk;
    private final Set zzd = Collections.synchronizedSet(new HashSet());
    private int zzg = 0;
    private final Handler zze = new zzfk(Looper.getMainLooper());
    private final Runnable zzf = new Runnable() { // from class: com.google.android.gms.internal.cast.zzcd
        @Override // java.lang.Runnable
        public final /* synthetic */ void run() {
            this.zza.zzh();
        }
    };

    public zzce(CastOptions castOptions) {
        this.zzc = castOptions;
    }

    /* JADX INFO: renamed from: zzq */
    public final void zzl() {
        ((Handler) Preconditions.checkNotNull(this.zze)).removeCallbacks((Runnable) Preconditions.checkNotNull(this.zzf));
        this.zzg = 0;
        this.zzk = null;
    }

    private final void zzr(int i) {
        CallbackToFutureAdapter.Completer completer = this.zzj;
        if (completer != null) {
            completer.setCancelled();
        }
        zzb.m333d("notify failed transfer with type = %d, reason = %d", Integer.valueOf(this.zzg), Integer.valueOf(i));
        Iterator it = new HashSet(this.zzd).iterator();
        while (it.hasNext()) {
            ((SessionTransferCallback) it.next()).onTransferFailed(this.zzg, i);
        }
        zzl();
    }

    private final RemoteMediaClient zzs() {
        SessionManager sessionManager = this.zzi;
        if (sessionManager == null) {
            zzb.m333d("skip transferring as SessionManager is null", new Object[0]);
            return null;
        }
        CastSession currentCastSession = sessionManager.getCurrentCastSession();
        if (currentCastSession != null) {
            return currentCastSession.getRemoteMediaClient();
        }
        zzb.m333d("skip transferring as CastSession is null", new Object[0]);
        return null;
    }

    public final void zza(SessionManager sessionManager) {
        this.zzi = sessionManager;
        ((Handler) Preconditions.checkNotNull(this.zze)).post(new Runnable() { // from class: com.google.android.gms.internal.cast.zzca
            @Override // java.lang.Runnable
            public final /* synthetic */ void run() {
                this.zza.zzi();
            }
        });
    }

    public final void zzb(boolean z) {
        this.zzh = z;
    }

    public final void zzc(SessionTransferCallback sessionTransferCallback) {
        zzb.m333d("register callback = %s", sessionTransferCallback);
        Preconditions.checkMainThread("Must be called from the main thread.");
        Preconditions.checkNotNull(sessionTransferCallback);
        this.zzd.add(sessionTransferCallback);
    }

    public final void zzf(MediaRouter mediaRouter) {
        if (zzg()) {
            SessionManager sessionManager = this.zzi;
            if ((sessionManager != null ? sessionManager.getCurrentCastSession() : null) == null) {
                mediaRouter.setRouteListingPreference(null);
                return;
            }
            ArrayList arrayList = new ArrayList();
            for (MediaRouter.RouteInfo routeInfo : mediaRouter.getRoutes()) {
                if (CastDevice.getFromBundle(routeInfo.getExtras()) != null) {
                    arrayList.add(new RouteListingPreference.Item.Builder(routeInfo.getId()).setFlags(0).build());
                }
            }
            zzb.m333d("updateRouteListingPreference with %d available routes", Integer.valueOf(arrayList.size()));
            mediaRouter.setRouteListingPreference(new RouteListingPreference.Builder().setItems(arrayList).build());
        }
    }

    public final boolean zzg() {
        return this.zzh && this.zzc.zzj();
    }

    public final /* synthetic */ void zzh() {
        zzb.m337i("transfer with type = %d has timed out", Integer.valueOf(this.zzg));
        zzr(101);
    }

    public final /* synthetic */ void zzi() {
        ((SessionManager) Preconditions.checkNotNull(this.zzi)).addSessionManagerListener(new zzbz(this, null), CastSession.class);
    }

    public final /* synthetic */ void zzm() {
        int i = this.zzg;
        if (i == 0) {
            zzb.m333d("No need to notify transferred if the transfer type is unknown", new Object[0]);
            return;
        }
        SessionState sessionState = this.zzk;
        if (sessionState == null) {
            zzb.m333d("No need to notify with null sessionState", new Object[0]);
            return;
        }
        zzb.m333d("notify transferred with type = %d, sessionState = %s", Integer.valueOf(i), this.zzk);
        Iterator it = new HashSet(this.zzd).iterator();
        while (it.hasNext()) {
            ((SessionTransferCallback) it.next()).onTransferred(this.zzg, sessionState);
        }
    }

    public final /* synthetic */ void zzn() {
        if (this.zzk == null) {
            zzb.m333d("skip restoring session state due to null SessionState", new Object[0]);
            return;
        }
        RemoteMediaClient remoteMediaClientZzs = zzs();
        if (remoteMediaClientZzs == null) {
            zzb.m333d("skip restoring session state due to null RemoteMediaClient", new Object[0]);
        } else {
            zzb.m333d("resume SessionState to current session", new Object[0]);
            remoteMediaClientZzs.zzg(this.zzk);
        }
    }

    public final /* synthetic */ int zzp() {
        return this.zzg;
    }
}
