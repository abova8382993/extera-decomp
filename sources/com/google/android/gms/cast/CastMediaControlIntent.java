package com.google.android.gms.cast;

import java.util.Collection;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes.dex */
public abstract class CastMediaControlIntent {
    public static String categoryForCast(String str) {
        if (str == null) {
            g$$ExternalSyntheticBUOutline1.m207m("applicationId cannot be null");
            return null;
        }
        zzs zzsVar = new zzs(null);
        zzsVar.zzb(str);
        return zzsVar.zzd().zza();
    }

    public static String categoryForCast(String str, Collection<String> collection) {
        if (str == null) {
            g$$ExternalSyntheticBUOutline1.m207m("applicationId cannot be null");
            return null;
        }
        if (collection != null) {
            zzs zzsVar = new zzs(null);
            zzsVar.zzb(str);
            zzsVar.zzc(collection);
            return zzsVar.zzd().zza();
        }
        g$$ExternalSyntheticBUOutline1.m207m("namespaces cannot be null");
        return null;
    }
}
