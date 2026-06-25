package com.google.android.gms.internal.measurement;

import android.util.Log;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
public final class zzpg {
    private volatile zzqs zzd;
    private final zzlk zze;
    private final String zzf;
    private final String zzg;
    private final boolean zzh;
    private final ImmutableSet zzi;
    private final zzps zzj;
    private final zzqt zzk;
    private static final zzpe zzc = new zzpe(null);
    static final zzon zza = new zzon(zzox.zza, false, false, false, false, ImmutableSet.m519of());

    public /* synthetic */ zzpg(zzlk zzlkVar, zzon zzonVar, String str, byte[] bArr) {
        this.zze = zzlkVar;
        String strZza = zzonVar.zza(zzlkVar.zzc());
        this.zzf = strZza;
        this.zzg = _UrlKt.FRAGMENT_ENCODE_SET;
        this.zzh = zzonVar.zzb();
        this.zzi = zzonVar.zzc();
        this.zzd = null;
        this.zzj = new zzps();
        this.zzk = new zzqt(zzlkVar, strZza, _UrlKt.FRAGMENT_ENCODE_SET, false);
    }

    public static zzpe zzd() {
        return zzc;
    }

    /* JADX WARN: Removed duplicated region for block: B:76:0x00a4 A[Catch: all -> 0x0052, TryCatch #0 {all -> 0x0052, blocks: (B:49:0x0005, B:51:0x0009, B:53:0x0013, B:72:0x009a, B:74:0x009e, B:76:0x00a4, B:56:0x001e, B:58:0x002b, B:60:0x0033, B:62:0x003d, B:65:0x0054, B:67:0x0079, B:68:0x0085, B:70:0x008d, B:78:0x00a8, B:79:0x00ab, B:80:0x00ac, B:52:0x000d), top: B:85:0x0005, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final com.google.android.gms.internal.measurement.zzqs zzq() {
        /*
            r6 = this;
            com.google.android.gms.internal.measurement.zzqs r0 = r6.zzd
            if (r0 != 0) goto Lb0
            monitor-enter(r6)
            com.google.android.gms.internal.measurement.zzqs r0 = r6.zzd     // Catch: java.lang.Throwable -> L52
            if (r0 != 0) goto Lac
            android.os.StrictMode$ThreadPolicy r0 = android.os.StrictMode.allowThreadDiskWrites()     // Catch: java.lang.Throwable -> L52
            com.google.android.gms.internal.measurement.zzqt r1 = r6.zzk     // Catch: java.lang.Throwable -> La7
            com.google.android.gms.internal.measurement.zzqs r1 = r1.zza()     // Catch: java.lang.Throwable -> La7
            android.os.StrictMode.setThreadPolicy(r0)     // Catch: java.lang.Throwable -> L52
            boolean r0 = r1.zzk()     // Catch: java.lang.Throwable -> L52
            if (r0 == 0) goto L1e
            goto L99
        L1e:
            com.google.android.gms.internal.measurement.zzlk r0 = r6.zze     // Catch: java.lang.Throwable -> L52
            com.google.android.gms.internal.measurement.zzrf r2 = r0.zzd()     // Catch: java.lang.Throwable -> L52
            r2.zza()     // Catch: java.lang.Throwable -> L52
            boolean r2 = r6.zzh     // Catch: java.lang.Throwable -> L52
            if (r2 != 0) goto L54
            com.google.android.gms.internal.measurement.zzqt r2 = r6.zzk     // Catch: java.lang.Throwable -> L52
            boolean r2 = r2.zzb()     // Catch: java.lang.Throwable -> L52
            if (r2 != 0) goto L54
            java.lang.String r2 = r1.zzd()     // Catch: java.lang.Throwable -> L52
            boolean r2 = r2.isEmpty()     // Catch: java.lang.Throwable -> L52
            if (r2 == 0) goto L54
            com.google.common.util.concurrent.ListeningScheduledExecutorService r0 = r0.zzg()     // Catch: java.lang.Throwable -> L52
            com.google.android.gms.internal.measurement.zzop r2 = new com.google.android.gms.internal.measurement.zzop     // Catch: java.lang.Throwable -> L52
            r2.<init>()     // Catch: java.lang.Throwable -> L52
            r0.execute(r2)     // Catch: java.lang.Throwable -> L52
            com.google.android.gms.internal.measurement.zzqv r0 = com.google.android.gms.internal.measurement.zzqv.zzi()     // Catch: java.lang.Throwable -> L52
            com.google.android.gms.internal.measurement.zzqs r0 = com.google.android.gms.internal.measurement.zzqs.zzb(r0, r1)     // Catch: java.lang.Throwable -> L52
            goto L9a
        L52:
            r0 = move-exception
            goto Lae
        L54:
            com.google.common.util.concurrent.ListeningScheduledExecutorService r2 = r0.zzg()     // Catch: java.lang.Throwable -> L52
            com.google.android.gms.internal.measurement.zzoy r3 = new com.google.android.gms.internal.measurement.zzoy     // Catch: java.lang.Throwable -> L52
            r3.<init>()     // Catch: java.lang.Throwable -> L52
            r2.execute(r3)     // Catch: java.lang.Throwable -> L52
            com.google.android.gms.internal.measurement.zzoh r2 = r0.zzk()     // Catch: java.lang.Throwable -> L52
            com.google.android.gms.internal.measurement.zzacr r3 = r1.zze()     // Catch: java.lang.Throwable -> L52
            com.google.common.collect.ImmutableSet r4 = r6.zzi     // Catch: java.lang.Throwable -> L52
            java.lang.String r5 = r6.zzf     // Catch: java.lang.Throwable -> L52
            r2.zza(r3, r4, r5)     // Catch: java.lang.Throwable -> L52
            java.lang.String r2 = r6.zzg     // Catch: java.lang.Throwable -> L52
            java.lang.String r3 = ""
            boolean r2 = r2.equals(r3)     // Catch: java.lang.Throwable -> L52
            if (r2 != 0) goto L85
            com.google.common.util.concurrent.ListeningScheduledExecutorService r2 = r0.zzg()     // Catch: java.lang.Throwable -> L52
            com.google.android.gms.internal.measurement.zzoq r3 = new com.google.android.gms.internal.measurement.zzoq     // Catch: java.lang.Throwable -> L52
            r3.<init>()     // Catch: java.lang.Throwable -> L52
            r2.execute(r3)     // Catch: java.lang.Throwable -> L52
        L85:
            com.google.android.gms.internal.measurement.zzqt r2 = r6.zzk     // Catch: java.lang.Throwable -> L52
            boolean r2 = r2.zzb()     // Catch: java.lang.Throwable -> L52
            if (r2 == 0) goto L99
            com.google.common.util.concurrent.ListeningScheduledExecutorService r0 = r0.zzg()     // Catch: java.lang.Throwable -> L52
            com.google.android.gms.internal.measurement.zzor r2 = new com.google.android.gms.internal.measurement.zzor     // Catch: java.lang.Throwable -> L52
            r2.<init>()     // Catch: java.lang.Throwable -> L52
            r0.execute(r2)     // Catch: java.lang.Throwable -> L52
        L99:
            r0 = r1
        L9a:
            boolean r1 = r6.zzh     // Catch: java.lang.Throwable -> L52
            if (r1 == 0) goto La4
            boolean r1 = r0.zzj()     // Catch: java.lang.Throwable -> L52
            if (r1 != 0) goto Lac
        La4:
            r6.zzd = r0     // Catch: java.lang.Throwable -> L52
            goto Lac
        La7:
            r1 = move-exception
            android.os.StrictMode.setThreadPolicy(r0)     // Catch: java.lang.Throwable -> L52
            throw r1     // Catch: java.lang.Throwable -> L52
        Lac:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L52
            return r0
        Lae:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L52
            throw r0
        Lb0:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzpg.zzq():com.google.android.gms.internal.measurement.zzqs");
    }

    /* JADX INFO: renamed from: zzr, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public final void zzg() {
        final zzqt zzqtVar = this.zzk;
        final ListenableFuture listenableFutureZzd = zzqtVar.zzd(this.zzg);
        AsyncFunction asyncFunction = new AsyncFunction() { // from class: com.google.android.gms.internal.measurement.zzpf
            @Override // com.google.common.util.concurrent.AsyncFunction
            public final /* synthetic */ ListenableFuture apply(Object obj) {
                return zzqtVar.zzc((zzqv) obj);
            }
        };
        zzlk zzlkVar = this.zze;
        Futures.transformAsync(listenableFutureZzd, asyncFunction, zzlkVar.zzg()).addListener(new Runnable() { // from class: com.google.android.gms.internal.measurement.zzov
            @Override // java.lang.Runnable
            public final /* synthetic */ void run() {
                this.zza.zzm(listenableFutureZzd);
            }
        }, zzlkVar.zzg());
    }

    /* JADX WARN: Removed duplicated region for block: B:61:0x0038 A[Catch: CancellationException -> 0x001d, ExecutionException -> 0x001f, TryCatch #3 {CancellationException -> 0x001d, ExecutionException -> 0x001f, blocks: (B:44:0x0000, B:46:0x0018, B:59:0x002a, B:61:0x0038, B:63:0x0040, B:69:0x004e, B:71:0x0052, B:53:0x0021, B:74:0x0070, B:55:0x0024, B:58:0x0029, B:67:0x0046, B:68:0x004d), top: B:80:0x0000, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:83:? A[RETURN, SYNTHETIC] */
    /* JADX INFO: renamed from: zzs */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final /* synthetic */ void zzm(com.google.common.util.concurrent.ListenableFuture r4) {
        /*
            r3 = this;
            java.lang.Object r4 = com.google.common.util.concurrent.Futures.getDone(r4)     // Catch: java.util.concurrent.CancellationException -> L1d java.util.concurrent.ExecutionException -> L1f
            com.google.android.gms.internal.measurement.zzqv r4 = (com.google.android.gms.internal.measurement.zzqv) r4     // Catch: java.util.concurrent.CancellationException -> L1d java.util.concurrent.ExecutionException -> L1f
            com.google.android.gms.internal.measurement.zzqr r0 = new com.google.android.gms.internal.measurement.zzqr     // Catch: java.util.concurrent.CancellationException -> L1d java.util.concurrent.ExecutionException -> L1f
            r1 = 6
            r2 = 2
            r0.<init>(r1, r2)     // Catch: java.util.concurrent.CancellationException -> L1d java.util.concurrent.ExecutionException -> L1f
            com.google.android.gms.internal.measurement.zzqs r0 = com.google.android.gms.internal.measurement.zzqs.zza(r4, r0)     // Catch: java.util.concurrent.CancellationException -> L1d java.util.concurrent.ExecutionException -> L1f
            boolean r1 = r3.zzh     // Catch: java.util.concurrent.CancellationException -> L1d java.util.concurrent.ExecutionException -> L1f
            com.google.common.base.Preconditions.checkNotNull(r0)     // Catch: java.util.concurrent.CancellationException -> L1d java.util.concurrent.ExecutionException -> L1f
            if (r1 != 0) goto L21
            com.google.android.gms.internal.measurement.zzqs r2 = r3.zzd     // Catch: java.util.concurrent.CancellationException -> L1d java.util.concurrent.ExecutionException -> L1f
            if (r2 != 0) goto L2a
            goto L21
        L1d:
            r4 = move-exception
            goto L71
        L1f:
            r4 = move-exception
            goto L71
        L21:
            monitor-enter(r3)     // Catch: java.util.concurrent.CancellationException -> L1d java.util.concurrent.ExecutionException -> L1f
            if (r1 != 0) goto L46
            com.google.android.gms.internal.measurement.zzqs r2 = r3.zzd     // Catch: java.lang.Throwable -> L44
            if (r2 != 0) goto L29
            goto L46
        L29:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L44
        L2a:
            com.google.common.collect.ImmutableMap r1 = r2.zzf()     // Catch: java.util.concurrent.CancellationException -> L1d java.util.concurrent.ExecutionException -> L1f
            com.google.common.collect.ImmutableMap r0 = r0.zzf()     // Catch: java.util.concurrent.CancellationException -> L1d java.util.concurrent.ExecutionException -> L1f
            boolean r0 = r1.equals(r0)     // Catch: java.util.concurrent.CancellationException -> L1d java.util.concurrent.ExecutionException -> L1f
            if (r0 != 0) goto L4e
            com.google.android.gms.internal.measurement.zzlk r4 = r3.zze     // Catch: java.util.concurrent.CancellationException -> L1d java.util.concurrent.ExecutionException -> L1f
            com.google.android.gms.internal.measurement.zzqm r4 = r4.zzj()     // Catch: java.util.concurrent.CancellationException -> L1d java.util.concurrent.ExecutionException -> L1f
            if (r4 == 0) goto La0
            r4.zza()     // Catch: java.util.concurrent.CancellationException -> L1d java.util.concurrent.ExecutionException -> L1f
            return
        L44:
            r4 = move-exception
            goto L6f
        L46:
            r3.zzd = r0     // Catch: java.lang.Throwable -> L44
            com.google.android.gms.internal.measurement.zzps r0 = r3.zzj     // Catch: java.lang.Throwable -> L44
            r0.zzb()     // Catch: java.lang.Throwable -> L44
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L44
        L4e:
            boolean r0 = r3.zzh     // Catch: java.util.concurrent.CancellationException -> L1d java.util.concurrent.ExecutionException -> L1f
            if (r0 == 0) goto La0
            com.google.android.gms.internal.measurement.zzlk r0 = r3.zze     // Catch: java.util.concurrent.CancellationException -> L1d java.util.concurrent.ExecutionException -> L1f
            com.google.android.gms.internal.measurement.zzmj r1 = r0.zzh()     // Catch: java.util.concurrent.CancellationException -> L1d java.util.concurrent.ExecutionException -> L1f
            java.lang.String r4 = r4.zza()     // Catch: java.util.concurrent.CancellationException -> L1d java.util.concurrent.ExecutionException -> L1f
            com.google.common.util.concurrent.ListenableFuture r4 = r1.zzb(r4)     // Catch: java.util.concurrent.CancellationException -> L1d java.util.concurrent.ExecutionException -> L1f
            java.lang.Class<java.lang.Throwable> r1 = java.lang.Throwable.class
            com.google.android.gms.internal.measurement.zzow r2 = new com.google.android.gms.internal.measurement.zzow     // Catch: java.util.concurrent.CancellationException -> L1d java.util.concurrent.ExecutionException -> L1f
            r2.<init>()     // Catch: java.util.concurrent.CancellationException -> L1d java.util.concurrent.ExecutionException -> L1f
            com.google.common.util.concurrent.ListeningScheduledExecutorService r0 = r0.zzg()     // Catch: java.util.concurrent.CancellationException -> L1d java.util.concurrent.ExecutionException -> L1f
            com.google.common.util.concurrent.Futures.catching(r4, r1, r2, r0)     // Catch: java.util.concurrent.CancellationException -> L1d java.util.concurrent.ExecutionException -> L1f
            return
        L6f:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L44
            throw r4     // Catch: java.util.concurrent.CancellationException -> L1d java.util.concurrent.ExecutionException -> L1f
        L71:
            java.lang.Throwable r0 = r4.getCause()
            boolean r0 = r0 instanceof java.lang.SecurityException
            if (r0 != 0) goto La0
            java.lang.String r3 = r3.zzf
            java.lang.String r0 = java.lang.String.valueOf(r3)
            int r0 = r0.length()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            int r0 = r0 + 64
            r1.<init>(r0)
            java.lang.String r0 = "Unable to update local snapshot for "
            r1.append(r0)
            r1.append(r3)
            java.lang.String r3 = ", may result in stale flags."
            r1.append(r3)
            java.lang.String r3 = r1.toString()
            java.lang.String r0 = "FlagStore"
            android.util.Log.w(r0, r3, r4)
        La0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzpg.zzm(com.google.common.util.concurrent.ListenableFuture):void");
    }

    public final Object zza(String str, boolean z) {
        return zzq().zzf().get(str);
    }

    public final String zzb() {
        return this.zzf;
    }

    public final zzps zzc() {
        return this.zzj;
    }

    public final /* synthetic */ ListenableFuture zzf() {
        ListenableFuture listenableFutureZzb;
        zzqs zzqsVarZzq = zzq();
        String strZzd = zzqsVarZzq.zzd();
        zzlk zzlkVar = this.zze;
        zzqn zzqnVarZzc = zzlkVar.zzd().zzc(false);
        if (zzqnVarZzc.zze()) {
            if (Strings.isNullOrEmpty(strZzd) && !zzqnVarZzc.zzd()) {
                return Futures.immediateVoidFuture();
            }
            zzmb zzmbVarZzb = zzme.zzb();
            zzmbVarZzb.zzb(zzqsVarZzq.zzi());
            if (!Strings.isNullOrEmpty(strZzd)) {
                zzmbVarZzb.zza(strZzd);
            }
            if (zzqnVarZzc.zzd()) {
                zzmbVarZzb.zzc(this.zzf);
            }
            listenableFutureZzb = zzlkVar.zzh().zzc((zzme) zzmbVarZzb.zzbd());
        } else {
            if (Strings.isNullOrEmpty(strZzd)) {
                return Futures.immediateVoidFuture();
            }
            listenableFutureZzb = zzlkVar.zzh().zzb(strZzd);
        }
        return Futures.catchingAsync(listenableFutureZzb, zzmk.class, new AsyncFunction() { // from class: com.google.android.gms.internal.measurement.zzos
            @Override // com.google.common.util.concurrent.AsyncFunction
            public final /* synthetic */ ListenableFuture apply(Object obj) {
                return this.zza.zzj((zzmk) obj);
            }
        }, zzlkVar.zzg());
    }

    public final /* synthetic */ void zzh() {
        zzlk zzlkVar = this.zze;
        final ListenableFuture listenableFutureZza = zzpu.zza(zzlkVar, this.zzf, this.zzg);
        listenableFutureZza.addListener(new Runnable() { // from class: com.google.android.gms.internal.measurement.zzot
            @Override // java.lang.Runnable
            public final /* synthetic */ void run() {
                this.zza.zzk(listenableFutureZza);
            }
        }, zzlkVar.zzg());
    }

    public final /* synthetic */ void zzi() {
        this.zze.zzf().zza(zzabz.FILE, this.zzh, zzou.zza);
    }

    public final /* synthetic */ ListenableFuture zzj(zzmk zzmkVar) {
        int iZza = zzmkVar.zza();
        if ((iZza == 29501 || iZza == 29537 || iZza == 29538 || iZza == 29539 || iZza == 29540 || iZza == 29541 || iZza == 29542 || iZza == 29543 || iZza == 29544) && !this.zzk.zzb()) {
            zzg();
        }
        return Futures.immediateVoidFuture();
    }

    public final /* synthetic */ void zzk(ListenableFuture listenableFuture) {
        try {
            Futures.getDone(listenableFuture);
        } catch (Exception e) {
            String str = this.zzf;
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 73);
            sb.append("Failed to store account on flag read for: ");
            sb.append(str);
            sb.append(" which may lead to stale flags.");
            Log.w("FlagStore", sb.toString(), e);
        }
    }

    public final /* synthetic */ Void zzn(Throwable th) {
        Log.w("FlagStore", "Failed to commit to updated flags for ".concat(String.valueOf(this.zzf)), th);
        return null;
    }

    public final /* synthetic */ boolean zzo() {
        if (!this.zzh) {
            return true;
        }
        zzqs zzqsVar = this.zzd;
        if (zzqsVar == null) {
            return false;
        }
        if (!zzqsVar.zzh() && !zzqsVar.zzg() && !this.zzk.zzb()) {
            return false;
        }
        synchronized (this) {
            try {
                zzqs zzqsVar2 = this.zzd;
                if (zzqsVar2 != null && (zzqsVar2.zzh() || zzqsVar2.zzg() || this.zzk.zzb())) {
                    this.zzd = null;
                    this.zzj.zzb();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return false;
    }
}
