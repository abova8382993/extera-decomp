package com.google.android.gms.internal.mlkit_language_id_common;

import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes.dex */
final class zzad extends zzw {
    static final zzw zza = new zzad(null, new Object[0], 0);
    final transient Object[] zzb;
    private final transient int zzc;

    private zzad(@CheckForNull Object obj, Object[] objArr, int i) {
        this.zzb = objArr;
        this.zzc = i;
    }

    public static zzad zzg(int i, Object[] objArr, zzv zzvVar) {
        Object obj = objArr[0];
        obj.getClass();
        Object obj2 = objArr[1];
        obj2.getClass();
        zzn.zza(obj, obj2);
        return new zzad(null, objArr, 1);
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x0007  */
    @Override // com.google.android.gms.internal.mlkit_language_id_common.zzw, java.util.Map
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.mlkit_language_id_common.zzad.get(java.lang.Object):java.lang.Object");
    }

    @Override // java.util.Map
    public final int size() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_language_id_common.zzw
    public final zzq zza() {
        return new zzac(this.zzb, 1, this.zzc);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id_common.zzw
    public final zzx zzd() {
        return new zzaa(this, this.zzb, 0, this.zzc);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id_common.zzw
    public final zzx zze() {
        return new zzab(this, new zzac(this.zzb, 0, this.zzc));
    }
}
