package com.google.android.gms.internal.play_billing;

import org.mvel2.util.Make$Map$$ExternalSyntheticBUOutline0;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes.dex */
final class zzhf implements zzik {
    private static final zzhf zza = new zzhf();

    private zzhf() {
    }

    public static zzhf zza() {
        return zza;
    }

    @Override // com.google.android.gms.internal.play_billing.zzik
    public final zzij zzb(Class cls) {
        if (!zzhk.class.isAssignableFrom(cls)) {
            g$$ExternalSyntheticBUOutline1.m207m("Unsupported message type: ".concat(cls.getName()));
            return null;
        }
        try {
            return (zzij) zzhk.zzo(cls.asSubclass(zzhk.class)).zzd(3, null, null);
        } catch (Exception e) {
            Make$Map$$ExternalSyntheticBUOutline0.m1024m("Unable to get message info for ".concat(cls.getName()), e);
            return null;
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzik
    public final boolean zzc(Class cls) {
        return zzhk.class.isAssignableFrom(cls);
    }
}
