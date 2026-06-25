package com.google.android.gms.cast.framework;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.framework.media.CastMediaOptions;
import com.google.android.gms.cast.framework.media.NotificationOptions;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.internal.Logger;
import com.google.android.gms.cast.zzq;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.cast.zzbx;
import com.google.android.gms.tasks.Task;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.checkerframework.dataflow.qual.Pure;

/* JADX INFO: loaded from: classes4.dex */
public class CastSession extends Session {
    private static final Logger zzb = new Logger("CastSession");
    private final Context zzc;
    private final Set zzd;
    private final zzal zze;
    private final CastOptions zzf;
    private final zzbx zzg;
    private final com.google.android.gms.cast.framework.media.internal.zzs zzh;
    private zzq zzi;
    private RemoteMediaClient zzj;
    private CastDevice zzk;
    private Cast.ApplicationConnectionResult zzl;
    private zzs zzm;

    public CastSession(Context context, String str, String str2, CastOptions castOptions, zzbx zzbxVar, com.google.android.gms.cast.framework.media.internal.zzs zzsVar) {
        super(context, str, str2);
        this.zzd = new HashSet();
        this.zzc = context.getApplicationContext();
        this.zzf = castOptions;
        this.zzg = zzbxVar;
        this.zzh = zzsVar;
        this.zze = com.google.android.gms.internal.cast.zzay.zzc(context, castOptions, zzn(), new zzv(this, null));
    }

    private final void zzo(Bundle bundle) {
        CastDevice fromBundle = CastDevice.getFromBundle(bundle);
        this.zzk = fromBundle;
        if (fromBundle == null) {
            if (isResuming()) {
                notifyFailedToResumeSession(2153);
                return;
            } else {
                notifyFailedToStartSession(2151);
                return;
            }
        }
        zzq zzqVar = this.zzi;
        if (zzqVar != null) {
            zzqVar.zzd();
            this.zzi = null;
        }
        zzb.m333d("Acquiring a connection to Google Play Services for %s", this.zzk);
        CastDevice castDevice = (CastDevice) Preconditions.checkNotNull(this.zzk);
        Bundle bundle2 = new Bundle();
        CastOptions castOptions = this.zzf;
        CastMediaOptions castMediaOptions = castOptions == null ? null : castOptions.getCastMediaOptions();
        NotificationOptions notificationOptions = castMediaOptions == null ? null : castMediaOptions.getNotificationOptions();
        boolean z = castMediaOptions != null && castMediaOptions.zza();
        bundle2.putBoolean("com.google.android.gms.cast.EXTRA_CAST_FRAMEWORK_NOTIFICATION_ENABLED", notificationOptions != null);
        bundle2.putBoolean("com.google.android.gms.cast.EXTRA_CAST_REMOTE_CONTROL_NOTIFICATION_ENABLED", z);
        zzbx zzbxVar = this.zzg;
        bundle2.putBoolean("com.google.android.gms.cast.EXTRA_CAST_ALWAYS_FOLLOW_SESSION_ENABLED", zzbxVar.zzo());
        bundle2.putBoolean("com.google.android.gms.cast.EXTRA_USE_ROUTE_CONNECTION", zzbxVar.zzq());
        Cast.CastOptions.Builder builder = new Cast.CastOptions.Builder(castDevice, new zzw(this, null));
        builder.zza(bundle2);
        zzq zzqVarZza = Cast.zza(this.zzc, builder.build());
        zzqVarZza.zzb(new zzz(this, null));
        this.zzi = zzqVarZza;
        zzqVarZza.zzc();
    }

    @Override // com.google.android.gms.cast.framework.Session
    public void end(boolean z) {
        zzal zzalVar = this.zze;
        if (zzalVar != null) {
            try {
                zzalVar.zzj(z, 0);
            } catch (RemoteException e) {
                zzb.m334d(e, "Unable to call %s on %s.", "disconnectFromDevice", zzal.class.getSimpleName());
            }
            notifySessionEnded(0);
        }
    }

    @Pure
    public CastDevice getCastDevice() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return this.zzk;
    }

    public RemoteMediaClient getRemoteMediaClient() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return this.zzj;
    }

    @Override // com.google.android.gms.cast.framework.Session
    public long getSessionRemainingTimeMs() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        RemoteMediaClient remoteMediaClient = this.zzj;
        if (remoteMediaClient == null) {
            return 0L;
        }
        return remoteMediaClient.getStreamDuration() - this.zzj.getApproximateStreamPosition();
    }

    @Override // com.google.android.gms.cast.framework.Session
    public void onResuming(Bundle bundle) {
        this.zzk = CastDevice.getFromBundle(bundle);
    }

    @Override // com.google.android.gms.cast.framework.Session
    public void onStarting(Bundle bundle) {
        this.zzk = CastDevice.getFromBundle(bundle);
    }

    @Override // com.google.android.gms.cast.framework.Session
    public void resume(Bundle bundle) {
        zzo(bundle);
    }

    @Override // com.google.android.gms.cast.framework.Session
    public void start(Bundle bundle) {
        zzo(bundle);
    }

    @Override // com.google.android.gms.cast.framework.Session
    public final void zza(Bundle bundle) {
        CastDevice castDevice;
        CastDevice castDevice2;
        CastDevice fromBundle = CastDevice.getFromBundle(bundle);
        if (fromBundle == null || fromBundle.equals(this.zzk)) {
            return;
        }
        boolean z = false;
        if (!TextUtils.isEmpty(fromBundle.getFriendlyName()) && ((castDevice2 = this.zzk) == null || !TextUtils.equals(castDevice2.getFriendlyName(), fromBundle.getFriendlyName()))) {
            z = true;
        }
        this.zzk = fromBundle;
        zzb.m333d("update to device (%s) with name %s", fromBundle, true != z ? "unchanged" : "changed");
        if (!z || (castDevice = this.zzk) == null) {
            return;
        }
        com.google.android.gms.cast.framework.media.internal.zzs zzsVar = this.zzh;
        if (zzsVar != null) {
            zzsVar.zzc(castDevice);
        }
        Iterator it = new HashSet(this.zzd).iterator();
        while (it.hasNext()) {
            ((Cast.Listener) it.next()).onDeviceNameChanged();
        }
        zzs zzsVar2 = this.zzm;
        if (zzsVar2 != null) {
            zzsVar2.zzd();
        }
    }

    public final void zzb(zzs zzsVar) {
        this.zzm = zzsVar;
    }

    public final boolean zzd() {
        return this.zzg.zzo();
    }

    public final /* synthetic */ void zze(String str, Task task) {
        zzal zzalVar = this.zze;
        if (zzalVar == null) {
            return;
        }
        try {
            if (task.isSuccessful()) {
                Cast.ApplicationConnectionResult applicationConnectionResult = (Cast.ApplicationConnectionResult) task.getResult();
                this.zzl = applicationConnectionResult;
                if (applicationConnectionResult.getStatus() != null && applicationConnectionResult.getStatus().isSuccess()) {
                    zzb.m333d("%s() -> success result", str);
                    RemoteMediaClient remoteMediaClient = new RemoteMediaClient(new com.google.android.gms.cast.internal.zzar(null));
                    this.zzj = remoteMediaClient;
                    remoteMediaClient.zza(this.zzi);
                    this.zzj.registerCallback(new zzr(this));
                    this.zzj.zzb();
                    this.zzh.zza(this.zzj, getCastDevice());
                    zzalVar.zzh((ApplicationMetadata) Preconditions.checkNotNull(applicationConnectionResult.getApplicationMetadata()), applicationConnectionResult.getApplicationStatus(), (String) Preconditions.checkNotNull(applicationConnectionResult.getSessionId()), applicationConnectionResult.getWasLaunched());
                    return;
                }
                if (applicationConnectionResult.getStatus() != null) {
                    zzb.m333d("%s() -> failure result", str);
                    zzalVar.zzi(applicationConnectionResult.getStatus().getStatusCode());
                    return;
                }
            } else {
                Exception exception = task.getException();
                if (exception instanceof ApiException) {
                    zzalVar.zzi(((ApiException) exception).getStatusCode());
                    return;
                }
            }
            zzalVar.zzi(2476);
        } catch (RemoteException e) {
            zzb.m334d(e, "Unable to call %s on %s.", "methods", zzal.class.getSimpleName());
        }
    }

    public final /* synthetic */ void zzf(int i) {
        this.zzh.zzb(i);
        zzq zzqVar = this.zzi;
        if (zzqVar != null) {
            zzqVar.zzd();
            this.zzi = null;
        }
        this.zzk = null;
        RemoteMediaClient remoteMediaClient = this.zzj;
        if (remoteMediaClient != null) {
            remoteMediaClient.zza(null);
            this.zzj = null;
        }
        this.zzl = null;
    }

    public final /* synthetic */ Set zzh() {
        return this.zzd;
    }

    public final /* synthetic */ zzal zzi() {
        return this.zze;
    }

    public final /* synthetic */ zzq zzj() {
        return this.zzi;
    }

    public final /* synthetic */ RemoteMediaClient zzk() {
        return this.zzj;
    }

    public final /* synthetic */ zzs zzl() {
        return this.zzm;
    }
}
