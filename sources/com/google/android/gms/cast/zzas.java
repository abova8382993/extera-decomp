package com.google.android.gms.cast;

import com.google.android.gms.cast.internal.zzah;
import com.google.android.gms.cast.internal.zzy;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.internal.cast.zzff;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes4.dex */
final /* synthetic */ class zzas implements RemoteCall {
    static final /* synthetic */ zzas zza = new zzas();

    private /* synthetic */ zzas() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.RemoteCall
    public final /* synthetic */ void accept(Object obj, Object obj2) {
        zzy zzyVar = (zzy) obj;
        int i = zzbm.$r8$clinit;
        ((zzah) zzyVar.getService()).zzr(zzff.zza(zzyVar.getContext()));
        ((TaskCompletionSource) obj2).setResult(Boolean.TRUE);
    }
}
