package com.google.android.gms.internal.cast;

import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.cast.internal.Logger;
import java.math.BigInteger;
import java.util.Map;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
public final class zzp {
    private static final Logger zza = new Logger("ApplicationAnalyticsUtils");
    private static final String zzb = "22.3.1";
    private final String zzc;
    private final Map zzd;
    private final Map zze;

    public zzp(Bundle bundle, String str) {
        this.zzc = str;
        this.zzd = zzaz.zza(bundle, "com.google.android.gms.cast.DICTIONARY_CAST_STATUS_CODES_TO_APP_SESSION_ERROR");
        this.zze = zzaz.zza(bundle, "com.google.android.gms.cast.DICTIONARY_CAST_STATUS_CODES_TO_APP_SESSION_CHANGE_REASON");
    }

    private final zzqq zzh(zzo zzoVar) {
        long jLongValue;
        zzqq zzqqVarZzc = zzqr.zzc();
        zzqqVarZzc.zza(zzoVar.zzd);
        int i = zzoVar.zze;
        zzoVar.zze = i + 1;
        zzqqVarZzc.zzg(i);
        String str = zzoVar.zzc;
        if (str != null) {
            zzqqVarZzc.zzf(str);
        }
        zzur zzurVarZza = zzus.zza();
        if (!TextUtils.isEmpty(zzoVar.zzh)) {
            zzqqVarZzc.zzb(zzoVar.zzh);
            zzurVarZza.zza(zzoVar.zzh);
        }
        if (!TextUtils.isEmpty(zzoVar.zzi)) {
            zzurVarZza.zzb(zzoVar.zzi);
        }
        if (!TextUtils.isEmpty(zzoVar.zzj)) {
            zzurVarZza.zzc(zzoVar.zzj);
        }
        if (!TextUtils.isEmpty(zzoVar.zzk)) {
            zzurVarZza.zzd(zzoVar.zzk);
        }
        if (!TextUtils.isEmpty(zzoVar.zzl)) {
            zzurVarZza.zze(zzoVar.zzl);
        }
        if (!TextUtils.isEmpty(zzoVar.zzm)) {
            zzurVarZza.zzf(zzoVar.zzm);
        }
        zzurVarZza.zzg(zzco.zza(zzoVar.zzn));
        zzqqVarZzc.zzn((zzus) zzurVarZza.zzu());
        zzqb zzqbVarZza = zzqc.zza();
        zzqbVarZza.zzb(zzb);
        zzqbVarZza.zza(this.zzc);
        zzqqVarZzc.zzl((zzqc) zzqbVarZza.zzu());
        zzqf zzqfVarZza = zzqg.zza();
        if (zzoVar.zzb != null) {
            zzro zzroVarZza = zzrp.zza();
            zzroVarZza.zza(zzoVar.zzb);
            zzqfVarZza.zza((zzrp) zzroVarZza.zzu());
        }
        zzqfVarZza.zzb(false);
        String str2 = zzoVar.zzf;
        if (str2 != null) {
            try {
                String strReplace = str2.replace("-", _UrlKt.FRAGMENT_ENCODE_SET);
                jLongValue = new BigInteger(strReplace.substring(0, Math.min(16, strReplace.length())), 16).longValue();
            } catch (NumberFormatException e) {
                zza.m340w(e, "receiverSessionId %s is not valid for hash", str2);
                jLongValue = 0;
            }
            zzqfVarZza.zzc(jLongValue);
        }
        zzqfVarZza.zzf(zzoVar.zzg);
        zzqfVarZza.zzg(zzoVar.zzb());
        zzqfVarZza.zzj(zzoVar.zzo);
        zzqqVarZzc.zzj(zzqfVarZza);
        return zzqqVarZzc;
    }

    private static void zzi(zzqq zzqqVar, boolean z) {
        zzqf zzqfVarZzc = zzqg.zzc(zzqqVar.zzh());
        zzqfVarZzc.zzb(z);
        zzqqVar.zzj(zzqfVarZzc);
    }

    public final zzqr zza(zzo zzoVar) {
        return (zzqr) zzh(zzoVar).zzu();
    }

    public final zzqr zzb(zzo zzoVar) {
        zzqq zzqqVarZzh = zzh(zzoVar);
        if (zzoVar.zzp == 1) {
            zzqf zzqfVarZzc = zzqg.zzc(zzqqVarZzh.zzh());
            zzqfVarZzc.zzd(17);
            zzqqVarZzh.zzi((zzqg) zzqfVarZzc.zzu());
        }
        return (zzqr) zzqqVarZzh.zzu();
    }

    public final zzqr zzc(zzo zzoVar) {
        zzqq zzqqVarZzh = zzh(zzoVar);
        zzqf zzqfVarZzc = zzqg.zzc(zzqqVarZzh.zzh());
        zzqfVarZzc.zzd(10);
        zzqqVarZzh.zzi((zzqg) zzqfVarZzc.zzu());
        zzi(zzqqVarZzh, true);
        return (zzqr) zzqqVarZzh.zzu();
    }

    public final zzqr zzd(zzo zzoVar, boolean z) {
        zzqq zzqqVarZzh = zzh(zzoVar);
        zzi(zzqqVarZzh, z);
        return (zzqr) zzqqVarZzh.zzu();
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0051  */
    @org.checkerframework.dataflow.qual.Pure
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.android.gms.internal.cast.zzqr zze(com.google.android.gms.internal.cast.zzo r5, int r6) {
        /*
            r4 = this;
            com.google.android.gms.internal.cast.zzqq r5 = r4.zzh(r5)
            com.google.android.gms.internal.cast.zzqg r0 = r5.zzh()
            com.google.android.gms.internal.cast.zzqf r0 = com.google.android.gms.internal.cast.zzqg.zzc(r0)
            java.util.Map r1 = r4.zze
            if (r1 == 0) goto L2c
            java.lang.Integer r2 = java.lang.Integer.valueOf(r6)
            boolean r3 = r1.containsKey(r2)
            if (r3 != 0) goto L1b
            goto L2c
        L1b:
            java.lang.Object r1 = r1.get(r2)
            java.lang.Integer r1 = (java.lang.Integer) r1
            java.lang.Object r1 = com.google.android.gms.common.internal.Preconditions.checkNotNull(r1)
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            goto L2e
        L2c:
            int r1 = r6 + 10000
        L2e:
            r0.zzd(r1)
            java.util.Map r4 = r4.zzd
            if (r4 == 0) goto L51
            java.lang.Integer r1 = java.lang.Integer.valueOf(r6)
            boolean r2 = r4.containsKey(r1)
            if (r2 != 0) goto L40
            goto L51
        L40:
            java.lang.Object r4 = r4.get(r1)
            java.lang.Integer r4 = (java.lang.Integer) r4
            java.lang.Object r4 = com.google.android.gms.common.internal.Preconditions.checkNotNull(r4)
            java.lang.Integer r4 = (java.lang.Integer) r4
            int r4 = r4.intValue()
            goto L53
        L51:
            int r4 = r6 + 10000
        L53:
            r0.zze(r4)
            com.google.android.gms.internal.cast.zzyd r4 = r0.zzu()
            com.google.android.gms.internal.cast.zzqg r4 = (com.google.android.gms.internal.cast.zzqg) r4
            r5.zzi(r4)
            com.google.android.gms.internal.cast.zzyd r4 = r5.zzu()
            com.google.android.gms.internal.cast.zzqr r4 = (com.google.android.gms.internal.cast.zzqr) r4
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzp.zze(com.google.android.gms.internal.cast.zzo, int):com.google.android.gms.internal.cast.zzqr");
    }

    public final zzqr zzf(zzo zzoVar, int i) {
        zzqq zzqqVarZzh = zzh(zzoVar);
        zzqf zzqfVarZzc = zzqg.zzc(zzqqVarZzh.zzh());
        zzqfVarZzc.zzh(i);
        zzqqVarZzh.zzi((zzqg) zzqfVarZzc.zzu());
        return (zzqr) zzqqVarZzh.zzu();
    }

    public final zzqr zzg(zzo zzoVar, int i, int i2) {
        zzqq zzqqVarZzh = zzh(zzoVar);
        zzqf zzqfVarZzc = zzqg.zzc(zzqqVarZzh.zzh());
        zzqfVarZzc.zzh(i);
        zzqfVarZzc.zzi(i2);
        zzqqVarZzh.zzi((zzqg) zzqfVarZzc.zzu());
        return (zzqr) zzqqVarZzh.zzu();
    }
}
