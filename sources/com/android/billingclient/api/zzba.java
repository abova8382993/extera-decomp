package com.android.billingclient.api;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.google.android.gms.internal.play_billing.zze;
import com.google.android.gms.internal.play_billing.zzjx;
import com.google.android.gms.internal.play_billing.zzjz;
import com.google.android.gms.internal.play_billing.zzke;
import com.google.android.gms.internal.play_billing.zzki;
import com.google.android.gms.internal.play_billing.zzkl;
import com.google.android.gms.internal.play_billing.zzlu;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes4.dex */
final class zzba implements ServiceConnection {
    final /* synthetic */ BillingClientImpl zza;
    private final BillingClientStateListener zzb;

    /* synthetic */ zzba(BillingClientImpl billingClientImpl, BillingClientStateListener billingClientStateListener, zzbl zzblVar) {
        this.zza = billingClientImpl;
        this.zzb = billingClientStateListener;
    }

    private final void zzc(BillingResult billingResult) {
        synchronized (this.zza.zza) {
            try {
                if (this.zza.zzb == 3) {
                    return;
                }
                this.zzb.onBillingSetupFinished(billingResult);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.content.ServiceConnection
    public final void onBindingDied(ComponentName componentName) {
        zze.zzl("BillingClient", "Billing service died.");
        try {
            if (BillingClientImpl.zzaq(this.zza)) {
                zzch zzchVar = this.zza.zzg;
                zzjx zzjxVarZzc = zzjz.zzc();
                zzjxVarZzc.zzn(6);
                zzke zzkeVarZzc = zzki.zzc();
                zzkeVarZzc.zzo(122);
                zzjxVarZzc.zza(zzkeVarZzc);
                zzchVar.zza((zzjz) zzjxVarZzc.zzf());
            } else {
                this.zza.zzg.zze(zzkl.zzB());
            }
        } catch (Throwable th) {
            zze.zzm("BillingClient", "Unable to log.", th);
        }
        synchronized (this.zza.zza) {
            if (this.zza.zzb != 3 && this.zza.zzb != 0) {
                this.zza.zzaJ(0);
                this.zza.zzaL();
                this.zzb.onBillingServiceDisconnected();
            }
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        zze.zzk("BillingClient", "Billing service connected.");
        synchronized (this.zza.zza) {
            try {
                if (this.zza.zzb == 3) {
                    return;
                }
                this.zza.zzh = com.google.android.gms.internal.play_billing.zzam.zzu(iBinder);
                BillingClientImpl billingClientImpl = this.zza;
                if (BillingClientImpl.zzE(new Callable() { // from class: com.android.billingclient.api.zzay
                    @Override // java.util.concurrent.Callable
                    public final Object call() {
                        this.zza.zza();
                        return null;
                    }
                }, 30000L, new Runnable() { // from class: com.android.billingclient.api.zzaz
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.zza.zzb();
                    }
                }, billingClientImpl.zzax(), billingClientImpl.zzaD()) == null) {
                    BillingClientImpl billingClientImpl2 = this.zza;
                    BillingResult billingResultZzaA = billingClientImpl2.zzaA();
                    billingClientImpl2.zzbe(25, 6, billingResultZzaA);
                    zzc(billingResultZzaA);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        zze.zzl("BillingClient", "Billing service disconnected.");
        try {
            if (BillingClientImpl.zzaq(this.zza)) {
                zzch zzchVar = this.zza.zzg;
                zzjx zzjxVarZzc = zzjz.zzc();
                zzjxVarZzc.zzn(6);
                zzke zzkeVarZzc = zzki.zzc();
                zzkeVarZzc.zzo(121);
                zzjxVarZzc.zza(zzkeVarZzc);
                zzchVar.zza((zzjz) zzjxVarZzc.zzf());
            } else {
                this.zza.zzg.zzg(zzlu.zzB());
            }
        } catch (Throwable th) {
            zze.zzm("BillingClient", "Unable to log.", th);
        }
        synchronized (this.zza.zza) {
            try {
                if (this.zza.zzb == 3) {
                    return;
                }
                this.zza.zzaJ(0);
                this.zzb.onBillingServiceDisconnected();
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:162:0x0273  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0278  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final /* synthetic */ java.lang.Object zza() {
        /*
            Method dump skipped, instruction units count: 776
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.billingclient.api.zzba.zza():java.lang.Object");
    }

    final /* synthetic */ void zzb() {
        this.zza.zzaJ(0);
        BillingResult billingResult = zzcj.zzn;
        this.zza.zzbe(24, 6, billingResult);
        zzc(billingResult);
    }
}
