package com.google.android.gms.stats;

import android.content.Context;
import android.os.PowerManager;
import android.os.WorkSource;
import android.support.v4.media.session.MediaSessionCompat$$ExternalSyntheticThrowCCEIfNotNull0;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.common.util.WorkSourceUtil;
import com.google.android.gms.internal.stats.zzh;
import com.google.android.gms.internal.stats.zzi;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.concurrent.ThreadSafe;
import kotlin.jvm.internal.LongCompanionObject;

/* JADX INFO: loaded from: classes.dex */
@ThreadSafe
public class WakeLock {
    private static final long zzb = 31622400000L;
    private static volatile ScheduledExecutorService zzc = null;
    private static final Object zzd = new Object();
    private static volatile zzd zze = new zzb();
    com.google.android.gms.internal.stats.zzb zza;
    private final Object zzf;
    private final PowerManager.WakeLock zzg;
    private int zzh;
    private Future<?> zzi;
    private long zzj;
    private final Set<Object> zzk;
    private boolean zzl;
    private int zzm;
    private Clock zzn;
    private WorkSource zzo;
    private final String zzp;
    private final String zzq;
    private final Context zzr;
    private final Map<String, zzc> zzs;
    private AtomicInteger zzt;
    private final ScheduledExecutorService zzu;

    public WakeLock(Context context, int i, String str) {
        String packageName = context.getPackageName();
        this.zzf = new Object();
        this.zzh = 0;
        this.zzk = new HashSet();
        this.zzl = true;
        this.zzn = DefaultClock.getInstance();
        this.zzs = new HashMap();
        this.zzt = new AtomicInteger(0);
        Preconditions.checkNotNull(context, "WakeLock: context must not be null");
        Preconditions.checkNotEmpty(str, "WakeLock: wakeLockName must not be empty");
        this.zzr = context.getApplicationContext();
        this.zzq = str;
        this.zza = null;
        if ("com.google.android.gms".equals(context.getPackageName())) {
            this.zzp = str;
        } else {
            String strValueOf = String.valueOf(str);
            this.zzp = strValueOf.length() != 0 ? "*gcore*:".concat(strValueOf) : new String("*gcore*:");
        }
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        if (powerManager == null) {
            StringBuilder sb = new StringBuilder(29);
            sb.append((CharSequence) "expected a non-null reference", 0, 29);
            throw new zzi(sb.toString());
        }
        PowerManager.WakeLock wakeLockNewWakeLock = powerManager.newWakeLock(i, str);
        this.zzg = wakeLockNewWakeLock;
        if (WorkSourceUtil.hasWorkSourcePermission(context)) {
            WorkSource workSourceFromPackage = WorkSourceUtil.fromPackage(context, Strings.isEmptyOrWhitespace(packageName) ? context.getPackageName() : packageName);
            this.zzo = workSourceFromPackage;
            if (workSourceFromPackage != null) {
                zze(wakeLockNewWakeLock, workSourceFromPackage);
            }
        }
        ScheduledExecutorService scheduledExecutorServiceUnconfigurableScheduledExecutorService = zzc;
        if (scheduledExecutorServiceUnconfigurableScheduledExecutorService == null) {
            synchronized (zzd) {
                try {
                    scheduledExecutorServiceUnconfigurableScheduledExecutorService = zzc;
                    if (scheduledExecutorServiceUnconfigurableScheduledExecutorService == null) {
                        zzh.zza();
                        scheduledExecutorServiceUnconfigurableScheduledExecutorService = Executors.unconfigurableScheduledExecutorService(Executors.newScheduledThreadPool(1));
                        zzc = scheduledExecutorServiceUnconfigurableScheduledExecutorService;
                    }
                } finally {
                }
            }
        }
        this.zzu = scheduledExecutorServiceUnconfigurableScheduledExecutorService;
    }

    public static /* synthetic */ void zza(WakeLock wakeLock) {
        synchronized (wakeLock.zzf) {
            try {
                if (wakeLock.isHeld()) {
                    Log.e("WakeLock", String.valueOf(wakeLock.zzp).concat(" ** IS FORCE-RELEASED ON TIMEOUT **"));
                    wakeLock.zzc();
                    if (wakeLock.isHeld()) {
                        wakeLock.zzh = 1;
                        wakeLock.zzd(0);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private final String zzb(String str) {
        if (this.zzl) {
            TextUtils.isEmpty(null);
        }
        return null;
    }

    private final void zzc() {
        if (this.zzk.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList(this.zzk);
        this.zzk.clear();
        if (arrayList.size() <= 0) {
            return;
        }
        MediaSessionCompat$$ExternalSyntheticThrowCCEIfNotNull0.m2m(arrayList.get(0));
        throw null;
    }

    /* JADX WARN: Type inference failed for: r0v5, types: [java.util.Map<java.lang.String, com.google.android.gms.stats.zzc>, kotlin.coroutines.Continuation, kotlin.coroutines.jvm.internal.DebugProbesKt] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.util.ConcurrentModificationException
    	at java.base/java.util.ArrayList$Itr.checkForComodification(ArrayList.java:1095)
    	at java.base/java.util.ArrayList$Itr.next(ArrayList.java:1049)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:358)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    /*  JADX ERROR: JadxRuntimeException in pass: FinishTypeInference
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r0v17 boolean
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:236)
        	at jadx.core.dex.visitors.typeinference.FinishTypeInference.lambda$visit$0(FinishTypeInference.java:27)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        	at jadx.core.dex.visitors.typeinference.FinishTypeInference.visit(FinishTypeInference.java:22)
        */
    private final void zzd(int r6) {
        /*
            r5 = this;
            java.lang.Object r6 = r5.zzf
            monitor-enter(r6)
            boolean r0 = r5.isHeld()     // Catch: java.lang.Throwable -> Lb
            if (r0 != 0) goto Le
            monitor-exit(r6)     // Catch: java.lang.Throwable -> Lb
            return
        Lb:
            r5 = move-exception
            goto La7
        Le:
            boolean r0 = r5.zzl     // Catch: java.lang.Throwable -> Lb
            r1 = 0
            if (r0 == 0) goto L1e
            int r0 = r5.zzh     // Catch: java.lang.Throwable -> Lb
            int r0 = r0 + (-1)
            r5.zzh = r0     // Catch: java.lang.Throwable -> Lb
            if (r0 > 0) goto L1c
            goto L20
        L1c:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> Lb
            return
        L1e:
            r5.zzh = r1     // Catch: java.lang.Throwable -> Lb
        L20:
            r5.zzc()     // Catch: java.lang.Throwable -> Lb
            java.util.Map<java.lang.String, com.google.android.gms.stats.zzc> r0 = r5.zzs     // Catch: java.lang.Throwable -> Lb
            java.util.Collection r0 = r0.values()     // Catch: java.lang.Throwable -> Lb
            java.util.Iterator r0 = r0.iterator()     // Catch: java.lang.Throwable -> Lb
        L2d:
            boolean r2 = r0.hasNext()     // Catch: java.lang.Throwable -> Lb
            if (r2 == 0) goto L3c
            java.lang.Object r2 = r0.next()     // Catch: java.lang.Throwable -> Lb
            com.google.android.gms.stats.zzc r2 = (com.google.android.gms.stats.zzc) r2     // Catch: java.lang.Throwable -> Lb
            r2.zza = r1     // Catch: java.lang.Throwable -> Lb
            goto L2d
        L3c:
            java.util.Map<java.lang.String, com.google.android.gms.stats.zzc> r0 = r5.zzs     // Catch: java.lang.Throwable -> Lb
            r0.probeCoroutineCreated(r0)     // Catch: java.lang.Throwable -> Lb
            java.util.concurrent.Future<?> r0 = r5.zzi     // Catch: java.lang.Throwable -> Lb
            r2 = 0
            if (r0 == 0) goto L4f
            r0.cancel(r1)     // Catch: java.lang.Throwable -> Lb
            r5.zzi = r2     // Catch: java.lang.Throwable -> Lb
            r3 = 0
            r5.zzj = r3     // Catch: java.lang.Throwable -> Lb
        L4f:
            r5.zzm = r1     // Catch: java.lang.Throwable -> Lb
            android.os.PowerManager$WakeLock r0 = r5.zzg     // Catch: java.lang.Throwable -> Lb
            boolean r0 = r0.isHeld()     // Catch: java.lang.Throwable -> Lb
            if (r0 == 0) goto L94
            android.os.PowerManager$WakeLock r0 = r5.zzg     // Catch: java.lang.Throwable -> L65 java.lang.RuntimeException -> L67
            r0.release()     // Catch: java.lang.Throwable -> L65 java.lang.RuntimeException -> L67
            com.google.android.gms.internal.stats.zzb r0 = r5.zza     // Catch: java.lang.Throwable -> Lb
            if (r0 == 0) goto La5
            r5.zza = r2     // Catch: java.lang.Throwable -> Lb
            goto La5
        L65:
            r0 = move-exception
            goto L8d
        L67:
            r0 = move-exception
            java.lang.Class r1 = r0.getClass()     // Catch: java.lang.Throwable -> L65
            java.lang.Class<java.lang.RuntimeException> r3 = java.lang.RuntimeException.class
            boolean r1 = r1.equals(r3)     // Catch: java.lang.Throwable -> L65
            if (r1 == 0) goto L8c
            java.lang.String r1 = "WakeLock"
            java.lang.String r3 = r5.zzp     // Catch: java.lang.Throwable -> L65
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch: java.lang.Throwable -> L65
            java.lang.String r4 = " failed to release!"
            java.lang.String r3 = r3.concat(r4)     // Catch: java.lang.Throwable -> L65
            android.util.Log.e(r1, r3, r0)     // Catch: java.lang.Throwable -> L65
            com.google.android.gms.internal.stats.zzb r0 = r5.zza     // Catch: java.lang.Throwable -> Lb
            if (r0 == 0) goto La5
            r5.zza = r2     // Catch: java.lang.Throwable -> Lb
            goto La5
        L8c:
            throw r0     // Catch: java.lang.Throwable -> L65
        L8d:
            com.google.android.gms.internal.stats.zzb r1 = r5.zza     // Catch: java.lang.Throwable -> Lb
            if (r1 == 0) goto L93
            r5.zza = r2     // Catch: java.lang.Throwable -> Lb
        L93:
            throw r0     // Catch: java.lang.Throwable -> Lb
        L94:
            java.lang.String r0 = "WakeLock"
            java.lang.String r5 = r5.zzp     // Catch: java.lang.Throwable -> Lb
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch: java.lang.Throwable -> Lb
            java.lang.String r1 = " should be held!"
            java.lang.String r5 = r5.concat(r1)     // Catch: java.lang.Throwable -> Lb
            android.util.Log.e(r0, r5)     // Catch: java.lang.Throwable -> Lb
        La5:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> Lb
            return
        La7:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> Lb
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.stats.WakeLock.zzd(int):void");
    }

    private static void zze(PowerManager.WakeLock wakeLock, WorkSource workSource) {
        try {
            wakeLock.setWorkSource(workSource);
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            Log.wtf("WakeLock", e.toString());
        }
    }

    public void acquire(long j) {
        this.zzt.incrementAndGet();
        long j2 = zzb;
        long j3 = LongCompanionObject.MAX_VALUE;
        long jMax = Math.max(Math.min(LongCompanionObject.MAX_VALUE, j2), 1L);
        if (j > 0) {
            jMax = Math.min(j, jMax);
        }
        synchronized (this.zzf) {
            try {
                if (!isHeld()) {
                    this.zza = com.google.android.gms.internal.stats.zzb.zza(false, null);
                    this.zzg.acquire();
                    this.zzn.elapsedRealtime();
                }
                this.zzh++;
                this.zzm++;
                zzb(null);
                zzc zzcVar = this.zzs.get(null);
                if (zzcVar == null) {
                    zzcVar = new zzc(null);
                    this.zzs.put(null, zzcVar);
                }
                zzcVar.zza++;
                long jElapsedRealtime = this.zzn.elapsedRealtime();
                if (LongCompanionObject.MAX_VALUE - jElapsedRealtime > jMax) {
                    j3 = jElapsedRealtime + jMax;
                }
                if (j3 > this.zzj) {
                    this.zzj = j3;
                    Future<?> future = this.zzi;
                    if (future != null) {
                        future.cancel(false);
                    }
                    this.zzi = this.zzu.schedule(new Runnable() { // from class: com.google.android.gms.stats.zza
                        @Override // java.lang.Runnable
                        public final void run() {
                            WakeLock.zza(this.zza);
                        }
                    }, jMax, TimeUnit.MILLISECONDS);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public boolean isHeld() {
        boolean z;
        synchronized (this.zzf) {
            z = this.zzh > 0;
        }
        return z;
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [java.util.Map<java.lang.String, com.google.android.gms.stats.zzc>, kotlin.coroutines.jvm.internal.DebugProbesKt] */
    /* JADX WARN: Type inference failed for: r2v1, types: [void] */
    public void release() {
        if (this.zzt.decrementAndGet() < 0) {
            Log.e("WakeLock", String.valueOf(this.zzp).concat(" release without a matched acquire!"));
        }
        synchronized (this.zzf) {
            try {
                zzb(null);
                if (this.zzs.probeCoroutineSuspended(null) != 0) {
                    zzc zzcVar = this.zzs.get(null);
                    if (zzcVar != null) {
                        int i = zzcVar.zza - 1;
                        zzcVar.zza = i;
                        if (i == 0) {
                            this.zzs.remove(null);
                        }
                    }
                } else {
                    Log.w("WakeLock", String.valueOf(this.zzp).concat(" counter does not exist"));
                }
                zzd(0);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void setReferenceCounted(boolean z) {
        synchronized (this.zzf) {
            this.zzl = z;
        }
    }
}
