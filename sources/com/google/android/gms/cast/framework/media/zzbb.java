package com.google.android.gms.cast.framework.media;

import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.common.api.Status;
import java.util.Objects;

/* JADX INFO: loaded from: classes4.dex */
final class zzbb implements RemoteMediaClient.MediaChannelResult {
    final /* synthetic */ Status zza;

    public zzbb(zzbc zzbcVar, Status status) {
        this.zza = status;
        Objects.requireNonNull(zzbcVar);
    }

    @Override // com.google.android.gms.common.api.Result
    public final Status getStatus() {
        return this.zza;
    }
}
