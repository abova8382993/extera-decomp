package com.google.android.recaptcha.internal;

import com.google.android.recaptcha.internal.zzge;
import com.google.android.recaptcha.internal.zzgf;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes5.dex */
public abstract class zzge<MessageType extends zzgf<MessageType, BuilderType>, BuilderType extends zzge<MessageType, BuilderType>> implements zzkd {
    @Override // 
    public abstract zzge zza();

    public abstract zzge zzb(zzgf zzgfVar);

    @Override // com.google.android.recaptcha.internal.zzkd
    public final /* bridge */ /* synthetic */ zzkd zzc(zzke zzkeVar) {
        if (zzY().getClass().isInstance(zzkeVar)) {
            return zzb((zzgf) zzkeVar);
        }
        g$$ExternalSyntheticBUOutline1.m207m("mergeFrom(MessageLite) can only merge messages of the same type.");
        return null;
    }
}
