package com.google.android.gms.cast.framework.media;

import android.support.v4.media.session.MediaSessionCompat$$ExternalSyntheticThrowCCEIfNotNull0;
import com.google.android.gms.cast.MediaError;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.internal.zzao;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes4.dex */
final class zzbe implements zzao {
    final /* synthetic */ RemoteMediaClient zza;

    public /* synthetic */ zzbe(RemoteMediaClient remoteMediaClient, byte[] bArr) {
        Objects.requireNonNull(remoteMediaClient);
        this.zza = remoteMediaClient;
    }

    private final void zzn() {
        this.zza.zzw();
    }

    @Override // com.google.android.gms.cast.internal.zzao
    public final void zza() {
        zzn();
        RemoteMediaClient remoteMediaClient = this.zza;
        remoteMediaClient.zzp();
        Iterator it = remoteMediaClient.zzu().iterator();
        if (it.hasNext()) {
            MediaSessionCompat$$ExternalSyntheticThrowCCEIfNotNull0.m2m(it.next());
            throw null;
        }
        Iterator it2 = remoteMediaClient.zzv().iterator();
        while (it2.hasNext()) {
            ((RemoteMediaClient.Callback) it2.next()).onStatusUpdated();
        }
    }

    @Override // com.google.android.gms.cast.internal.zzao
    public final void zzb() {
        zzn();
        RemoteMediaClient remoteMediaClient = this.zza;
        Iterator it = remoteMediaClient.zzu().iterator();
        if (it.hasNext()) {
            MediaSessionCompat$$ExternalSyntheticThrowCCEIfNotNull0.m2m(it.next());
            throw null;
        }
        Iterator it2 = remoteMediaClient.zzv().iterator();
        while (it2.hasNext()) {
            ((RemoteMediaClient.Callback) it2.next()).onMetadataUpdated();
        }
    }

    @Override // com.google.android.gms.cast.internal.zzao
    public final void zzc() {
        RemoteMediaClient remoteMediaClient = this.zza;
        Iterator it = remoteMediaClient.zzu().iterator();
        if (it.hasNext()) {
            MediaSessionCompat$$ExternalSyntheticThrowCCEIfNotNull0.m2m(it.next());
            throw null;
        }
        Iterator it2 = remoteMediaClient.zzv().iterator();
        while (it2.hasNext()) {
            ((RemoteMediaClient.Callback) it2.next()).onQueueStatusUpdated();
        }
    }

    @Override // com.google.android.gms.cast.internal.zzao
    public final void zzd() {
        RemoteMediaClient remoteMediaClient = this.zza;
        Iterator it = remoteMediaClient.zzu().iterator();
        if (it.hasNext()) {
            MediaSessionCompat$$ExternalSyntheticThrowCCEIfNotNull0.m2m(it.next());
            throw null;
        }
        Iterator it2 = remoteMediaClient.zzv().iterator();
        while (it2.hasNext()) {
            ((RemoteMediaClient.Callback) it2.next()).onPreloadStatusUpdated();
        }
    }

    @Override // com.google.android.gms.cast.internal.zzao
    public final void zze() {
        RemoteMediaClient remoteMediaClient = this.zza;
        Iterator it = remoteMediaClient.zzu().iterator();
        if (it.hasNext()) {
            MediaSessionCompat$$ExternalSyntheticThrowCCEIfNotNull0.m2m(it.next());
            throw null;
        }
        Iterator it2 = remoteMediaClient.zzv().iterator();
        while (it2.hasNext()) {
            ((RemoteMediaClient.Callback) it2.next()).onAdBreakStatusUpdated();
        }
    }

    @Override // com.google.android.gms.cast.internal.zzao
    public final void zzf(MediaError mediaError) {
        Iterator it = this.zza.zzv().iterator();
        while (it.hasNext()) {
            ((RemoteMediaClient.Callback) it.next()).onMediaError(mediaError);
        }
    }

    @Override // com.google.android.gms.cast.internal.zzao
    public final void zzg(int[] iArr) {
        Iterator it = this.zza.zzv().iterator();
        while (it.hasNext()) {
            ((RemoteMediaClient.Callback) it.next()).zzb(iArr);
        }
    }

    @Override // com.google.android.gms.cast.internal.zzao
    public final void zzh(int[] iArr, int i) {
        Iterator it = this.zza.zzv().iterator();
        while (it.hasNext()) {
            ((RemoteMediaClient.Callback) it.next()).zzc(iArr, i);
        }
    }

    @Override // com.google.android.gms.cast.internal.zzao
    public final void zzi(int[] iArr) {
        Iterator it = this.zza.zzv().iterator();
        while (it.hasNext()) {
            ((RemoteMediaClient.Callback) it.next()).zzd(iArr);
        }
    }

    @Override // com.google.android.gms.cast.internal.zzao
    public final void zzj(int[] iArr) {
        Iterator it = this.zza.zzv().iterator();
        while (it.hasNext()) {
            ((RemoteMediaClient.Callback) it.next()).zze(iArr);
        }
    }

    @Override // com.google.android.gms.cast.internal.zzao
    public final void zzk(MediaQueueItem[] mediaQueueItemArr) {
        Iterator it = this.zza.zzv().iterator();
        while (it.hasNext()) {
            ((RemoteMediaClient.Callback) it.next()).zzf(mediaQueueItemArr);
        }
    }

    @Override // com.google.android.gms.cast.internal.zzao
    public final void zzl(List list, List list2, int i) {
        Iterator it = this.zza.zzv().iterator();
        while (it.hasNext()) {
            ((RemoteMediaClient.Callback) it.next()).zzg(list, list2, i);
        }
    }

    @Override // com.google.android.gms.cast.internal.zzao
    public final void zzm() {
        Iterator it = this.zza.zzv().iterator();
        while (it.hasNext()) {
            ((RemoteMediaClient.Callback) it.next()).zzh();
        }
    }
}
