package com.google.android.gms.internal.measurement;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes4.dex */
public final class zzol implements zzoh {
    private final AtomicBoolean zza = new AtomicBoolean(false);
    private final ConcurrentMap zzb;
    private final ConcurrentMap zzc;

    public zzol() {
        new ConcurrentHashMap();
        this.zzb = new ConcurrentHashMap();
        new ConcurrentHashMap();
        this.zzc = new ConcurrentHashMap();
    }

    /* JADX WARN: Removed duplicated region for block: B:42:? A[LOOP:1: B:12:0x004e->B:42:?, LOOP_END, SYNTHETIC] */
    @Override // com.google.android.gms.internal.measurement.zzoh
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zza(com.google.android.gms.internal.measurement.zzacr r10, java.util.Set r11, java.lang.String r12) {
        /*
            r9 = this;
            boolean r0 = r11.isEmpty()
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L1c
            java.util.concurrent.atomic.AtomicBoolean r0 = r9.zza
            boolean r0 = r0.getAndSet(r2)
            if (r0 != 0) goto L1c
            com.google.android.gms.internal.measurement.zzbu r0 = com.google.android.gms.internal.measurement.zzbu.zza()
            com.google.android.gms.internal.measurement.zzoj r3 = new com.google.android.gms.internal.measurement.zzoj
            r3.<init>(r9, r1)
            r0.zzb(r3)
        L1c:
            byte[] r10 = r10.zzm()
            java.util.concurrent.ConcurrentMap r0 = r9.zzb
            com.google.android.gms.internal.measurement.zzoi r3 = new com.google.android.gms.internal.measurement.zzoi
            r3.<init>()
            r0.compute(r12, r3)
            java.util.Iterator r11 = r11.iterator()
        L2e:
            boolean r0 = r11.hasNext()
            if (r0 == 0) goto Lbe
            java.lang.Object r0 = r11.next()
            java.lang.String r0 = (java.lang.String) r0
            java.util.concurrent.ConcurrentMap r3 = r9.zzc
            java.util.concurrent.atomic.AtomicReference r4 = new java.util.concurrent.atomic.AtomicReference
            com.google.android.gms.internal.measurement.zzok r5 = new com.google.android.gms.internal.measurement.zzok
            r5.<init>(r12, r10, r1)
            r4.<init>(r5)
            java.lang.Object r0 = r3.putIfAbsent(r0, r4)
            java.util.concurrent.atomic.AtomicReference r0 = (java.util.concurrent.atomic.AtomicReference) r0
            if (r0 == 0) goto L2e
        L4e:
            java.lang.Object r3 = r0.get()
            boolean r4 = r3 instanceof com.google.android.gms.internal.measurement.zzok
            r5 = 0
            if (r4 == 0) goto L86
            r4 = r3
            com.google.android.gms.internal.measurement.zzok r4 = (com.google.android.gms.internal.measurement.zzok) r4
            java.lang.String r6 = r4.zza()
            boolean r6 = r12.equals(r6)
            if (r6 == 0) goto L68
            r4.zzb(r10, r5)
            goto L2e
        L68:
            com.google.android.gms.internal.measurement.zzok r6 = new com.google.android.gms.internal.measurement.zzok
            r6.<init>(r12, r10, r1)
            java.lang.String r7 = r4.zza()
            int r7 = r12.compareTo(r7)
            r8 = 2
            if (r7 >= 0) goto L7f
            com.google.android.gms.internal.measurement.zzok[] r7 = new com.google.android.gms.internal.measurement.zzok[r8]
            r7[r5] = r6
            r7[r2] = r4
            goto Lb6
        L7f:
            com.google.android.gms.internal.measurement.zzok[] r7 = new com.google.android.gms.internal.measurement.zzok[r8]
            r7[r5] = r4
            r7[r2] = r6
            goto Lb6
        L86:
            r4 = r3
            com.google.android.gms.internal.measurement.zzok[] r4 = (com.google.android.gms.internal.measurement.zzok[]) r4
            int r6 = java.util.Arrays.binarySearch(r4, r12)
            if (r6 < 0) goto L95
            r0 = r4[r6]
            r0.zzb(r10, r5)
            goto L2e
        L95:
            int r6 = ~r6
            int r7 = r4.length
            int r8 = r7 + 1
            int r7 = r7 - r6
            if (r7 != 0) goto La4
            java.lang.Object[] r4 = java.util.Arrays.copyOf(r4, r8)
            com.google.android.gms.internal.measurement.zzok[] r4 = (com.google.android.gms.internal.measurement.zzok[]) r4
            r7 = r4
            goto Laf
        La4:
            com.google.android.gms.internal.measurement.zzok[] r8 = new com.google.android.gms.internal.measurement.zzok[r8]
            java.lang.System.arraycopy(r4, r5, r8, r5, r6)
            int r5 = r6 + 1
            java.lang.System.arraycopy(r4, r6, r8, r5, r7)
            r7 = r8
        Laf:
            com.google.android.gms.internal.measurement.zzok r4 = new com.google.android.gms.internal.measurement.zzok
            r4.<init>(r12, r10, r1)
            r7[r6] = r4
        Lb6:
            boolean r3 = com.google.android.exoplayer2.mediacodec.AbstractC1302xa830b2f.m312m(r0, r3, r7)
            if (r3 == 0) goto L4e
            goto L2e
        Lbe:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzol.zza(com.google.android.gms.internal.measurement.zzacr, java.util.Set, java.lang.String):void");
    }
}
