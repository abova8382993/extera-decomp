package com.google.android.gms.internal.measurement;

import org.mvel2.util.Make$Map$$ExternalSyntheticBUOutline0;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes4.dex */
final class zzado implements zzafa {
    private static final zzado zza = new zzado();

    private zzado() {
    }

    public static zzado zza() {
        return zza;
    }

    @Override // com.google.android.gms.internal.measurement.zzafa
    public final boolean zzb(Class cls) {
        return zzadu.class.isAssignableFrom(cls);
    }

    @Override // com.google.android.gms.internal.measurement.zzafa
    public final zzaez zzc(Class cls) {
        if (!zzadu.class.isAssignableFrom(cls)) {
            g$$ExternalSyntheticBUOutline1.m207m("Unsupported message type: ".concat(cls.getName()));
            return null;
        }
        try {
            return (zzaez) zzadu.zzcr(cls.asSubclass(zzadu.class)).zzg(3, null, null);
        } catch (Exception e) {
            Make$Map$$ExternalSyntheticBUOutline0.m1024m("Unable to get message info for ".concat(cls.getName()), e);
            return null;
        }
    }
}
