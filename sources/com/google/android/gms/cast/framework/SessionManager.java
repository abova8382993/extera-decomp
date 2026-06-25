package com.google.android.gms.cast.framework;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.cast.internal.Logger;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes.dex */
public class SessionManager {
    private static final Logger zza = new Logger("SessionManager");
    private final zzax zzb;
    private final Context zzc;

    public SessionManager(zzax zzaxVar, Context context) {
        this.zzb = zzaxVar;
        this.zzc = context;
    }

    public void endCurrentSession(boolean z) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        try {
            zza.m337i("End session for %s", this.zzc.getPackageName());
            this.zzb.zzj(true, z);
        } catch (RemoteException e) {
            zza.m334d(e, "Unable to call %s on %s.", "endCurrentSession", zzax.class.getSimpleName());
        }
    }

    public CastSession getCurrentCastSession() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        Session currentSession = getCurrentSession();
        if (currentSession == null || !(currentSession instanceof CastSession)) {
            return null;
        }
        return (CastSession) currentSession;
    }

    public Session getCurrentSession() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        try {
            return (Session) ObjectWrapper.unwrap(this.zzb.zze());
        } catch (RemoteException e) {
            zza.m334d(e, "Unable to call %s on %s.", "getWrappedCurrentSession", zzax.class.getSimpleName());
            return null;
        }
    }

    public final void zzb(CastStateListener castStateListener) {
        Preconditions.checkNotNull(castStateListener);
        try {
            this.zzb.zzh(new zzaa(castStateListener));
        } catch (RemoteException e) {
            zza.m334d(e, "Unable to call %s on %s.", "addCastStateListener", zzax.class.getSimpleName());
        }
    }

    public final IObjectWrapper zzd() {
        try {
            return this.zzb.zzk();
        } catch (RemoteException e) {
            zza.m334d(e, "Unable to call %s on %s.", "getWrappedThis", zzax.class.getSimpleName());
            return null;
        }
    }

    public <T extends Session> void addSessionManagerListener(SessionManagerListener<T> sessionManagerListener, Class<T> cls) {
        if (sessionManagerListener == null) {
            g$$ExternalSyntheticBUOutline2.m208m("SessionManagerListener can't be null");
            return;
        }
        Preconditions.checkNotNull(cls);
        Preconditions.checkMainThread("Must be called from the main thread.");
        try {
            this.zzb.zzf(new zzbh(sessionManagerListener, cls));
        } catch (RemoteException e) {
            zza.m334d(e, "Unable to call %s on %s.", "addSessionManagerListener", zzax.class.getSimpleName());
        }
    }
}
