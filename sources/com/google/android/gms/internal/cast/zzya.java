package com.google.android.gms.internal.cast;

import com.google.android.gms.internal.cast.zzya;
import com.google.android.gms.internal.cast.zzyd;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes4.dex */
public abstract class zzya<MessageType extends zzyd<MessageType, BuilderType>, BuilderType extends zzya<MessageType, BuilderType>> extends zzwy<MessageType, BuilderType> {
    protected zzyd zza;
    private final zzyd zzb;

    public zzya(MessageType messagetype) {
        this.zzb = messagetype;
        if (messagetype.zzv()) {
            g$$ExternalSyntheticBUOutline1.m207m("Default instance must be immutable.");
            throw null;
        }
        this.zza = messagetype.zzy();
    }

    private static void zza(Object obj, Object obj2) {
        zzzp.zza().zzb(obj.getClass()).zzd(obj, obj2);
    }

    public final void zzp() {
        if (this.zza.zzv()) {
            return;
        }
        zzq();
    }

    public void zzq() {
        zzyd zzydVarZzy = this.zzb.zzy();
        zza(zzydVarZzy, this.zza);
        this.zza = zzydVarZzy;
    }

    /* JADX INFO: renamed from: zzs */
    public final zzya clone() {
        zzya zzyaVar = (zzya) this.zzb.zzb(5, null, null);
        zzyaVar.zza = zzw();
        return zzyaVar;
    }

    @Override // com.google.android.gms.internal.cast.zzzh
    /* JADX INFO: renamed from: zzt */
    public MessageType zzw() {
        boolean zZzv = this.zza.zzv();
        MessageType messagetype = (MessageType) this.zza;
        if (!zZzv) {
            return messagetype;
        }
        messagetype.zzA();
        return (MessageType) this.zza;
    }

    public final MessageType zzu() {
        MessageType messagetype = (MessageType) zzw();
        if (messagetype.zzr()) {
            return messagetype;
        }
        throw new zzaac(messagetype);
    }

    public final zzya zzv(zzyd zzydVar) {
        if (!this.zzb.equals(zzydVar)) {
            if (!this.zza.zzv()) {
                zzq();
            }
            zza(this.zza, zzydVar);
        }
        return this;
    }
}
