package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: classes5.dex */
public final class zzfx {
    private static final Object zze = new Object();
    private final String zza;
    private final zzbo zzb;
    private final Object zzc;
    private final Object zzd = new Object();
    private volatile Object zzf = null;
    private volatile Object zzg = null;

    public /* synthetic */ zzfx(String str, Object obj, Object obj2, zzbo zzboVar, byte[] bArr) {
        this.zza = str;
        this.zzc = obj;
        this.zzb = zzboVar;
    }

    public final String zza() {
        return this.zza;
    }

    /* JADX WARN: Removed duplicated region for block: B:62:0x0061 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object zzb(java.lang.Object r4) {
        /*
            r3 = this;
            java.lang.Object r0 = r3.zzd
            monitor-enter(r0)
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L68
            if (r4 == 0) goto L7
            return r4
        L7:
            com.google.android.gms.measurement.internal.zzae r4 = com.google.android.gms.measurement.internal.zzfr.zza
            if (r4 != 0) goto Le
            java.lang.Object r3 = r3.zzc
            return r3
        Le:
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzfx.zze
            monitor-enter(r4)
            boolean r0 = com.google.android.gms.measurement.internal.zzae.zza()     // Catch: java.lang.Throwable -> L1e
            if (r0 == 0) goto L24
            java.lang.Object r0 = r3.zzg     // Catch: java.lang.Throwable -> L1e
            if (r0 != 0) goto L20
            java.lang.Object r3 = r3.zzc     // Catch: java.lang.Throwable -> L1e
            goto L22
        L1e:
            r3 = move-exception
            goto L66
        L20:
            java.lang.Object r3 = r3.zzg     // Catch: java.lang.Throwable -> L1e
        L22:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L1e
            return r3
        L24:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L1e
            java.util.List r4 = com.google.android.gms.measurement.internal.zzfy.zzb()     // Catch: java.lang.SecurityException -> L5a
            java.util.Iterator r4 = r4.iterator()     // Catch: java.lang.SecurityException -> L5a
        L2d:
            boolean r0 = r4.hasNext()     // Catch: java.lang.SecurityException -> L5a
            if (r0 == 0) goto L5a
            java.lang.Object r0 = r4.next()     // Catch: java.lang.SecurityException -> L5a
            com.google.android.gms.measurement.internal.zzfx r0 = (com.google.android.gms.measurement.internal.zzfx) r0     // Catch: java.lang.SecurityException -> L5a
            boolean r1 = com.google.android.gms.measurement.internal.zzae.zza()     // Catch: java.lang.SecurityException -> L5a
            if (r1 != 0) goto L52
            r1 = 0
            com.google.android.gms.measurement.internal.zzbo r2 = r0.zzb     // Catch: java.lang.IllegalStateException -> L48 java.lang.SecurityException -> L5a
            if (r2 == 0) goto L48
            java.lang.Object r1 = r2.zza()     // Catch: java.lang.IllegalStateException -> L48 java.lang.SecurityException -> L5a
        L48:
            java.lang.Object r2 = com.google.android.gms.measurement.internal.zzfx.zze     // Catch: java.lang.SecurityException -> L5a
            monitor-enter(r2)     // Catch: java.lang.SecurityException -> L5a
            r0.zzg = r1     // Catch: java.lang.Throwable -> L4f
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L4f
            goto L2d
        L4f:
            r4 = move-exception
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L4f
            throw r4     // Catch: java.lang.SecurityException -> L5a
        L52:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException     // Catch: java.lang.SecurityException -> L5a
            java.lang.String r0 = "Refreshing flag cache must be done on a worker thread."
            r4.<init>(r0)     // Catch: java.lang.SecurityException -> L5a
            throw r4     // Catch: java.lang.SecurityException -> L5a
        L5a:
            com.google.android.gms.measurement.internal.zzbo r4 = r3.zzb
            if (r4 != 0) goto L61
        L5e:
            java.lang.Object r3 = r3.zzc
            goto L65
        L61:
            java.lang.Object r3 = r4.zza()     // Catch: java.lang.Throwable -> L5e
        L65:
            return r3
        L66:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L1e
            throw r3
        L68:
            r3 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L68
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzfx.zzb(java.lang.Object):java.lang.Object");
    }
}
