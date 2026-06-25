package com.google.android.gms.internal.clearcut;

import android.support.v4.media.session.MediaSessionCompat$$ExternalSyntheticThrowCCEIfNotNull0;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
final class zzdu<T> implements zzef<T> {
    private final zzdo zzmn;
    private final boolean zzmo;
    private final zzex<?, ?> zzmx;
    private final zzbu<?> zzmy;

    private zzdu(zzex<?, ?> zzexVar, zzbu<?> zzbuVar, zzdo zzdoVar) {
        this.zzmx = zzexVar;
        this.zzmo = zzbuVar.zze(zzdoVar);
        this.zzmy = zzbuVar;
        this.zzmn = zzdoVar;
    }

    public static <T> zzdu<T> zza(zzex<?, ?> zzexVar, zzbu<?> zzbuVar, zzdo zzdoVar) {
        return new zzdu<>(zzexVar, zzbuVar, zzdoVar);
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final boolean equals(T t, T t2) {
        if (!this.zzmx.zzq(t).equals(this.zzmx.zzq(t2))) {
            return false;
        }
        if (this.zzmo) {
            return this.zzmy.zza(t).equals(this.zzmy.zza(t2));
        }
        return true;
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final int hashCode(T t) {
        int iHashCode = this.zzmx.zzq(t).hashCode();
        return this.zzmo ? (iHashCode * 53) + this.zzmy.zza(t).hashCode() : iHashCode;
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final T newInstance() {
        return (T) this.zzmn.zzbd().zzbi();
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final void zza(T t, zzfr zzfrVar) {
        Iterator<Map.Entry<?, Object>> it = this.zzmy.zza(t).iterator();
        if (it.hasNext()) {
            MediaSessionCompat$$ExternalSyntheticThrowCCEIfNotNull0.m2m(it.next().getKey());
            throw null;
        }
        zzex<?, ?> zzexVar = this.zzmx;
        zzexVar.zzc(zzexVar.zzq(t), zzfrVar);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0064 A[EDGE_INSN: B:109:0x0064->B:86:0x0064 BREAK  A[LOOP:1: B:72:0x0034->B:112:0x0034], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x005e  */
    @Override // com.google.android.gms.internal.clearcut.zzef
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zza(T r7, byte[] r8, int r9, int r10, com.google.android.gms.internal.clearcut.zzay r11) throws com.google.android.gms.internal.clearcut.zzco {
        /*
            r6 = this;
            com.google.android.gms.internal.clearcut.zzcg r7 = (com.google.android.gms.internal.clearcut.zzcg) r7
            com.google.android.gms.internal.clearcut.zzey r6 = r7.zzjp
            com.google.android.gms.internal.clearcut.zzey r0 = com.google.android.gms.internal.clearcut.zzey.zzea()
            if (r6 != r0) goto L10
            com.google.android.gms.internal.clearcut.zzey r6 = com.google.android.gms.internal.clearcut.zzey.zzeb()
            r7.zzjp = r6
        L10:
            r4 = r6
        L11:
            if (r9 >= r10) goto L70
            int r2 = com.google.android.gms.internal.clearcut.zzax.zza(r8, r9, r11)
            int r0 = r11.zzfd
            r6 = 11
            r7 = 2
            if (r0 == r6) goto L2f
            r6 = r0 & 7
            r1 = r8
            r3 = r10
            r5 = r11
            if (r6 != r7) goto L2a
            int r9 = com.google.android.gms.internal.clearcut.zzax.zza(r0, r1, r2, r3, r4, r5)
            goto L11
        L2a:
            int r9 = com.google.android.gms.internal.clearcut.zzax.zza(r0, r1, r2, r3, r5)
            goto L11
        L2f:
            r1 = r8
            r3 = r10
            r5 = r11
            r6 = 0
            r8 = 0
        L34:
            if (r2 >= r3) goto L63
            int r9 = com.google.android.gms.internal.clearcut.zzax.zza(r1, r2, r5)
            int r10 = r5.zzfd
            int r11 = r10 >>> 3
            r0 = r10 & 7
            if (r11 == r7) goto L51
            r2 = 3
            if (r11 == r2) goto L46
            goto L5a
        L46:
            if (r0 != r7) goto L5a
            int r2 = com.google.android.gms.internal.clearcut.zzax.zze(r1, r9, r5)
            java.lang.Object r8 = r5.zzff
            com.google.android.gms.internal.clearcut.zzbb r8 = (com.google.android.gms.internal.clearcut.zzbb) r8
            goto L34
        L51:
            if (r0 != 0) goto L5a
            int r2 = com.google.android.gms.internal.clearcut.zzax.zza(r1, r9, r5)
            int r6 = r5.zzfd
            goto L34
        L5a:
            r11 = 12
            if (r10 == r11) goto L64
            int r2 = com.google.android.gms.internal.clearcut.zzax.zza(r10, r1, r9, r3, r5)
            goto L34
        L63:
            r9 = r2
        L64:
            if (r8 == 0) goto L6c
            int r6 = r6 << 3
            r6 = r6 | r7
            r4.zzb(r6, r8)
        L6c:
            r8 = r1
            r10 = r3
            r11 = r5
            goto L11
        L70:
            r3 = r10
            if (r9 != r3) goto L74
            return
        L74:
            com.google.android.gms.internal.clearcut.zzco r6 = com.google.android.gms.internal.clearcut.zzco.zzbo()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzdu.zza(java.lang.Object, byte[], int, int, com.google.android.gms.internal.clearcut.zzay):void");
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final void zzc(T t) {
        this.zzmx.zzc(t);
        this.zzmy.zzc(t);
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final void zzc(T t, T t2) {
        zzeh.zza(this.zzmx, t, t2);
        if (this.zzmo) {
            zzeh.zza(this.zzmy, t, t2);
        }
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final int zzm(T t) {
        zzex<?, ?> zzexVar = this.zzmx;
        int iZzr = zzexVar.zzr(zzexVar.zzq(t));
        return this.zzmo ? iZzr + this.zzmy.zza(t).zzat() : iZzr;
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final boolean zzo(T t) {
        return this.zzmy.zza(t).isInitialized();
    }
}
