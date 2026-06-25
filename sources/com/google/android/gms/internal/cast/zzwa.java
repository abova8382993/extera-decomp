package com.google.android.gms.internal.cast;

import com.google.common.util.concurrent.ListenableFuture;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/* JADX INFO: loaded from: classes4.dex */
public abstract class zzwa<V> extends zzwb<V> {

    final class zza {
        static final zza zza;
        static final zza zzb;
        final boolean zzc;
        final Throwable zzd;

        static {
            if (zzwb.zzc) {
                zzb = null;
                zza = null;
            } else {
                zzb = new zza(false, null);
                zza = new zza(true, null);
            }
        }

        public zza(boolean z, Throwable th) {
            this.zzc = z;
            this.zzd = th;
        }
    }

    final class zzc {
        static final zzc zza = new zzc(new Throwable("Failure occurred while trying to finish a future.") { // from class: com.google.android.gms.internal.cast.zzwa.zzc.1
            public C13731(String str) {
                super("Failure occurred while trying to finish a future.");
            }

            @Override // java.lang.Throwable
            public final Throwable fillInStackTrace() {
                return this;
            }
        });
        final Throwable zzb;

        /* JADX INFO: renamed from: com.google.android.gms.internal.cast.zzwa$zzc$1 */
        public class C13731 extends Throwable {
            public C13731(String str) {
                super("Failure occurred while trying to finish a future.");
            }

            @Override // java.lang.Throwable
            public final Throwable fillInStackTrace() {
                return this;
            }
        }

        public zzc(Throwable th) {
            th.getClass();
            this.zzb = th;
        }
    }

    final class zzd {
        static final zzd zza = new zzd();
        zzd next;
        final Runnable zzb;
        final Executor zzc;

        public zzd() {
            this.zzb = null;
            this.zzc = null;
        }

        public zzd(Runnable runnable, Executor executor) {
            this.zzb = runnable;
            this.zzc = executor;
        }
    }

    abstract class zzf<V> extends zzwa<V> implements ListenableFuture {
    }

    public static Object zza(Object obj) throws ExecutionException {
        if (obj instanceof zza) {
            Throwable th = ((zza) obj).zzd;
            CancellationException cancellationException = new CancellationException("Task was cancelled.");
            cancellationException.initCause(th);
            throw cancellationException;
        }
        if (obj instanceof zzc) {
            throw new ExecutionException(((zzc) obj).zzb);
        }
        if (obj == zzwb.zza) {
            return null;
        }
        return obj;
    }

    public static boolean zzb(Object obj) {
        return true;
    }

    private static Object zzp(Future future) {
        Object obj;
        boolean z = false;
        while (true) {
            try {
                obj = future.get();
                break;
            } catch (InterruptedException unused) {
                z = true;
            } catch (Throwable th) {
                if (z) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
        return obj;
    }

    private static void zzq(zzwa zzwaVar, boolean z) {
        zzwaVar.zzk();
        zzwaVar.zze();
        zzd zzdVarZzi = zzwaVar.zzi(zzd.zza);
        zzd zzdVar = null;
        while (zzdVarZzi != null) {
            zzd zzdVar2 = zzdVarZzi.next;
            zzdVarZzi.next = zzdVar;
            zzdVar = zzdVarZzi;
            zzdVarZzi = zzdVar2;
        }
        while (zzdVar != null) {
            Runnable runnable = zzdVar.zzb;
            zzd zzdVar3 = zzdVar.next;
            Objects.requireNonNull(runnable);
            Executor executor = zzdVar.zzc;
            Objects.requireNonNull(executor);
            zzs(runnable, executor);
            zzdVar = zzdVar3;
        }
    }

    private final void zzr(StringBuilder sb) {
        try {
            Object objZzp = zzp(this);
            sb.append("SUCCESS, result=[");
            if (objZzp == null) {
                sb.append("null");
            } else if (objZzp == this) {
                sb.append("this future");
            } else {
                sb.append(objZzp.getClass().getName());
                sb.append("@");
                sb.append(Integer.toHexString(System.identityHashCode(objZzp)));
            }
            sb.append("]");
        } catch (CancellationException unused) {
            sb.append("CANCELLED");
        } catch (ExecutionException e) {
            sb.append("FAILURE, cause=[");
            sb.append(e.getCause());
            sb.append("]");
        } catch (Exception e2) {
            sb.append("UNKNOWN, cause=[");
            sb.append(e2.getClass());
            sb.append(" thrown from get()]");
        }
    }

    private static void zzs(Runnable runnable, Executor executor) {
        try {
            executor.execute(runnable);
        } catch (Exception e) {
            Logger loggerZza = zzwb.zzb.zza();
            Level level = Level.SEVERE;
            String strValueOf = String.valueOf(runnable);
            String strValueOf2 = String.valueOf(executor);
            StringBuilder sb = new StringBuilder(strValueOf.length() + 57 + strValueOf2.length());
            sb.append("RuntimeException while executing runnable ");
            sb.append(strValueOf);
            sb.append(" with executor ");
            sb.append(strValueOf2);
            loggerZza.logp(level, "com.google.common.util.concurrent.AbstractFuture", "executeListener", sb.toString(), (Throwable) e);
        }
    }

    @Override // com.google.common.util.concurrent.ListenableFuture
    public final void addListener(Runnable runnable, Executor executor) {
        zzd zzdVar;
        zzhd.zza(runnable, "Runnable was null.");
        zzhd.zza(executor, "Executor was null.");
        if (!isDone() && (zzdVar = this.listenersField) != zzd.zza) {
            zzd zzdVar2 = new zzd(runnable, executor);
            do {
                zzdVar2.next = zzdVar;
                if (zzh(zzdVar, zzdVar2)) {
                    return;
                } else {
                    zzdVar = this.listenersField;
                }
            } while (zzdVar != zzd.zza);
        }
        zzs(runnable, executor);
    }

    @Override // java.util.concurrent.Future
    public final boolean cancel(boolean z) {
        zza zzaVar;
        Object obj = this.valueField;
        if (obj == null) {
            if (zzwb.zzc) {
                zzaVar = new zza(z, new CancellationException("Future.cancel() was called."));
            } else {
                zzaVar = z ? zza.zza : zza.zzb;
                Objects.requireNonNull(zzaVar);
            }
            while (!zzwb.zzj(this, obj, zzaVar)) {
                obj = this.valueField;
                if (zzb(obj)) {
                }
            }
            zzq(this, z);
            return true;
        }
        return false;
    }

    @Override // java.util.concurrent.Future
    public final Object get() {
        return zzm();
    }

    @Override // java.util.concurrent.Future
    public final boolean isCancelled() {
        return this.valueField instanceof zza;
    }

    @Override // java.util.concurrent.Future
    public final boolean isDone() {
        Object obj = this.valueField;
        return (obj != null) & zzb(obj);
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x006f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String toString() {
        /*
            r6 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.Class r1 = r6.getClass()
            java.lang.String r1 = r1.getName()
            java.lang.String r2 = "com.google.common.util.concurrent."
            boolean r1 = r1.startsWith(r2)
            if (r1 == 0) goto L21
            java.lang.Class r1 = r6.getClass()
            java.lang.String r1 = r1.getSimpleName()
            r0.append(r1)
            goto L2c
        L21:
            java.lang.Class r1 = r6.getClass()
            java.lang.String r1 = r1.getName()
            r0.append(r1)
        L2c:
            r1 = 64
            r0.append(r1)
            int r1 = java.lang.System.identityHashCode(r6)
            java.lang.String r1 = java.lang.Integer.toHexString(r1)
            r0.append(r1)
            java.lang.String r1 = "[status="
            r0.append(r1)
            java.lang.Object r1 = r6.valueField
            boolean r1 = r1 instanceof com.google.android.gms.internal.cast.zzwa.zza
            java.lang.String r2 = "]"
            if (r1 == 0) goto L4f
            java.lang.String r6 = "CANCELLED"
            r0.append(r6)
            goto La0
        L4f:
            boolean r1 = r6.isDone()
            if (r1 == 0) goto L59
            r6.zzr(r0)
            goto La0
        L59:
            int r1 = r0.length()
            java.lang.String r3 = "PENDING"
            r0.append(r3)
            java.lang.String r3 = r6.zzg()     // Catch: java.lang.Throwable -> L71
            r4 = 0
            if (r3 == 0) goto L6f
            boolean r5 = r3.isEmpty()     // Catch: java.lang.Throwable -> L71
            if (r5 == 0) goto L83
        L6f:
            r3 = r4
            goto L83
        L71:
            r3 = move-exception
            com.google.android.gms.internal.cast.zzwu.zza(r3)
            java.lang.Class r3 = r3.getClass()
            java.lang.String r3 = java.lang.String.valueOf(r3)
            java.lang.String r4 = "Exception thrown from implementation: "
            java.lang.String r3 = r4.concat(r3)
        L83:
            if (r3 == 0) goto L90
            java.lang.String r4 = ", info=["
            r0.append(r4)
            r0.append(r3)
            r0.append(r2)
        L90:
            boolean r3 = r6.isDone()
            if (r3 == 0) goto La0
            int r3 = r0.length()
            r0.delete(r1, r3)
            r6.zzr(r0)
        La0:
            r0.append(r2)
            java.lang.String r6 = r0.toString()
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzwa.toString():java.lang.String");
    }

    public final boolean zzc(Object obj) {
        if (obj == null) {
            obj = zzwb.zza;
        }
        if (!zzwb.zzj(this, null, obj)) {
            return false;
        }
        zzq(this, false);
        return true;
    }

    public final boolean zzd(Throwable th) {
        if (!zzwb.zzj(this, null, new zzc(th))) {
            return false;
        }
        zzq(this, false);
        return true;
    }

    public void zze() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String zzg() {
        if (!(this instanceof ScheduledFuture)) {
            return null;
        }
        long delay = ((ScheduledFuture) this).getDelay(TimeUnit.MILLISECONDS);
        StringBuilder sb = new StringBuilder(String.valueOf(delay).length() + 21);
        sb.append("remaining delay=[");
        sb.append(delay);
        sb.append(" ms]");
        return sb.toString();
    }

    @Override // java.util.concurrent.Future
    public final Object get(long j, TimeUnit timeUnit) {
        return zzl(j, timeUnit);
    }
}
