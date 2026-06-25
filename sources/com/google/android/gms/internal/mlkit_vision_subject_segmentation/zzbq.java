package com.google.android.gms.internal.mlkit_vision_subject_segmentation;

import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes5.dex */
final class zzbq extends zzax {
    static final zzax zza = new zzbq(null, new Object[0], 0);
    final transient Object[] zzb;
    private final transient int zzc;

    private zzbq(@CheckForNull Object obj, Object[] objArr, int i) {
        this.zzb = objArr;
        this.zzc = i;
    }

    public static zzbq zzg(int i, Object[] objArr, zzaw zzawVar) {
        Object obj = objArr[0];
        obj.getClass();
        Object obj2 = objArr[1];
        obj2.getClass();
        zzab.zzb(obj, obj2);
        return new zzbq(null, objArr, 1);
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x0003  */
    @Override // com.google.android.gms.internal.mlkit_vision_subject_segmentation.zzax, java.util.Map
    @javax.annotation.CheckForNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object get(@javax.annotation.CheckForNull java.lang.Object r4) {
        /*
            r3 = this;
            r0 = 0
            if (r4 != 0) goto L5
        L3:
            r3 = r0
            goto L1d
        L5:
            int r1 = r3.zzc
            java.lang.Object[] r3 = r3.zzb
            r2 = 1
            if (r1 != r2) goto L3
            r1 = 0
            r1 = r3[r1]
            r1.getClass()
            boolean r4 = r1.equals(r4)
            if (r4 == 0) goto L3
            r3 = r3[r2]
            r3.getClass()
        L1d:
            if (r3 != 0) goto L20
            return r0
        L20:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.mlkit_vision_subject_segmentation.zzbq.get(java.lang.Object):java.lang.Object");
    }

    @Override // java.util.Map
    public final int size() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_subject_segmentation.zzax
    public final zzaq zza() {
        return new zzbp(this.zzb, 1, this.zzc);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_subject_segmentation.zzax
    public final zzay zzd() {
        return new zzbn(this, this.zzb, 0, this.zzc);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_subject_segmentation.zzax
    public final zzay zze() {
        return new zzbo(this, new zzbp(this.zzb, 0, this.zzc));
    }
}
