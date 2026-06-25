package com.google.android.gms.internal.mlkit_vision_label;

import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes4.dex */
final class zzbz extends zzbg {
    static final zzbg zza = new zzbz(null, new Object[0], 0);
    final transient Object[] zzb;
    private final transient int zzc;

    private zzbz(@CheckForNull Object obj, Object[] objArr, int i) {
        this.zzb = objArr;
        this.zzc = i;
    }

    public static zzbz zzg(int i, Object[] objArr, zzbf zzbfVar) {
        Object obj = objArr[0];
        obj.getClass();
        Object obj2 = objArr[1];
        obj2.getClass();
        zzal.zzb(obj, obj2);
        return new zzbz(null, objArr, 1);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0007  */
    @Override // com.google.android.gms.internal.mlkit_vision_label.zzbg, java.util.Map
    @javax.annotation.CheckForNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object get(@javax.annotation.CheckForNull java.lang.Object r4) {
        /*
            r3 = this;
            java.lang.Object[] r0 = r3.zzb
            int r3 = r3.zzc
            r1 = 0
            if (r4 != 0) goto L9
        L7:
            r3 = r1
            goto L1d
        L9:
            r2 = 1
            if (r3 != r2) goto L7
            r3 = 0
            r3 = r0[r3]
            r3.getClass()
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L7
            r3 = r0[r2]
            r3.getClass()
        L1d:
            if (r3 != 0) goto L20
            return r1
        L20:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.mlkit_vision_label.zzbz.get(java.lang.Object):java.lang.Object");
    }

    @Override // java.util.Map
    public final int size() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_label.zzbg
    public final zzaz zza() {
        return new zzby(this.zzb, 1, this.zzc);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_label.zzbg
    public final zzbh zzd() {
        return new zzbw(this, this.zzb, 0, this.zzc);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_label.zzbg
    public final zzbh zze() {
        return new zzbx(this, new zzby(this.zzb, 0, this.zzc));
    }
}
