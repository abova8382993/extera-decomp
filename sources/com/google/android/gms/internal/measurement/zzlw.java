package com.google.android.gms.internal.measurement;

import com.google.common.base.Preconditions;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes4.dex */
public abstract class zzlw {
    public static final /* synthetic */ int $r8$clinit = 0;
    private static final Object zzb = new Object();

    @Nullable
    private static volatile zzlt zzc;
    private static final AtomicInteger zze;

    static {
        new AtomicReference();
        Preconditions.checkNotNull(zzlu.zza, "BuildInfo must be non-null");
        zze = new AtomicInteger();
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0044, code lost:
    
        r3 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0049, code lost:
    
        throw r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void zza(final android.content.Context r3) {
        /*
            com.google.android.gms.internal.measurement.zzlt r0 = com.google.android.gms.internal.measurement.zzlw.zzc
            if (r0 != 0) goto L4a
            if (r3 != 0) goto L7
            goto L4a
        L7:
            java.lang.Object r0 = com.google.android.gms.internal.measurement.zzlw.zzb
            monitor-enter(r0)
            com.google.android.gms.internal.measurement.zzlt r1 = com.google.android.gms.internal.measurement.zzlw.zzc     // Catch: java.lang.Throwable -> L44
            if (r1 != 0) goto L46
            monitor-enter(r0)     // Catch: java.lang.Throwable -> L44
            com.google.android.gms.internal.measurement.zzlt r1 = com.google.android.gms.internal.measurement.zzlw.zzc     // Catch: java.lang.Throwable -> L21
            android.content.Context r2 = r3.getApplicationContext()     // Catch: java.lang.Throwable -> L21
            if (r2 == 0) goto L18
            r3 = r2
        L18:
            if (r1 == 0) goto L23
            android.content.Context r2 = r1.zza()     // Catch: java.lang.Throwable -> L21
            if (r2 == r3) goto L40
            goto L23
        L21:
            r3 = move-exception
            goto L42
        L23:
            if (r1 == 0) goto L2b
            com.google.android.gms.internal.measurement.zzld.zza()     // Catch: java.lang.Throwable -> L21
            com.google.android.gms.internal.measurement.zzma.zza()     // Catch: java.lang.Throwable -> L21
        L2b:
            com.google.android.gms.internal.measurement.zzlv r1 = new com.google.android.gms.internal.measurement.zzlv     // Catch: java.lang.Throwable -> L21
            r1.<init>()     // Catch: java.lang.Throwable -> L21
            com.google.common.base.Supplier r1 = com.google.common.base.Suppliers.memoize(r1)     // Catch: java.lang.Throwable -> L21
            com.google.android.gms.internal.measurement.zzlc r2 = new com.google.android.gms.internal.measurement.zzlc     // Catch: java.lang.Throwable -> L21
            r2.<init>(r3, r1)     // Catch: java.lang.Throwable -> L21
            com.google.android.gms.internal.measurement.zzlw.zzc = r2     // Catch: java.lang.Throwable -> L21
            java.util.concurrent.atomic.AtomicInteger r3 = com.google.android.gms.internal.measurement.zzlw.zze     // Catch: java.lang.Throwable -> L21
            r3.incrementAndGet()     // Catch: java.lang.Throwable -> L21
        L40:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L21
            goto L46
        L42:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L21
            throw r3     // Catch: java.lang.Throwable -> L44
        L44:
            r3 = move-exception
            goto L48
        L46:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L44
            return
        L48:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L44
            throw r3
        L4a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzlw.zza(android.content.Context):void");
    }
}
