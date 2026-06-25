package com.google.android.gms.cast.framework.media.internal;

import android.os.RemoteException;
import com.google.android.gms.cast.framework.media.NotificationOptions;
import com.google.android.gms.cast.internal.Logger;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public abstract class zzt {
    private static final Logger zza = new Logger("MediaSessionUtils");

    public static List zzb(com.google.android.gms.cast.framework.media.zzg zzgVar) {
        try {
            return zzgVar.zzf();
        } catch (RemoteException e) {
            zza.m336e(e, "Unable to call %s on %s.", "getNotificationActions", com.google.android.gms.cast.framework.media.zzg.class.getSimpleName());
            return null;
        }
    }

    public static int[] zzc(com.google.android.gms.cast.framework.media.zzg zzgVar) {
        try {
            return zzgVar.zzg();
        } catch (RemoteException e) {
            zza.m336e(e, "Unable to call %s on %s.", "getCompactViewActionIndices", com.google.android.gms.cast.framework.media.zzg.class.getSimpleName());
            return null;
        }
    }

    public static int zzd(NotificationOptions notificationOptions, long j) {
        return j == 10000 ? notificationOptions.getForward10DrawableResId() : j != 30000 ? notificationOptions.getForwardDrawableResId() : notificationOptions.getForward30DrawableResId();
    }

    public static int zze(NotificationOptions notificationOptions, long j) {
        return j == 10000 ? notificationOptions.zzg() : j != 30000 ? notificationOptions.zzf() : notificationOptions.zzh();
    }

    public static int zzf(NotificationOptions notificationOptions, long j) {
        return j == 10000 ? notificationOptions.getRewind10DrawableResId() : j != 30000 ? notificationOptions.getRewindDrawableResId() : notificationOptions.getRewind30DrawableResId();
    }

    public static int zzg(NotificationOptions notificationOptions, long j) {
        return j == 10000 ? notificationOptions.zzj() : j != 30000 ? notificationOptions.zzi() : notificationOptions.zzk();
    }
}
