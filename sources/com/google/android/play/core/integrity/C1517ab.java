package com.google.android.play.core.integrity;

import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.AbstractRunnableC1604r;
import com.google.android.play.integrity.internal.C1579ad;
import com.google.android.play.integrity.internal.InterfaceC1600n;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.ab */
/* JADX INFO: loaded from: classes4.dex */
final class C1517ab extends AbstractRunnableC1604r {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ byte[] f405a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ Long f406b;

    /* JADX INFO: renamed from: c */
    final /* synthetic */ TaskCompletionSource f407c;

    /* JADX INFO: renamed from: d */
    final /* synthetic */ IntegrityTokenRequest f408d;

    /* JADX INFO: renamed from: e */
    final /* synthetic */ C1519ad f409e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C1517ab(C1519ad c1519ad, TaskCompletionSource taskCompletionSource, byte[] bArr, Long l, Parcelable parcelable, TaskCompletionSource taskCompletionSource2, IntegrityTokenRequest integrityTokenRequest) {
        super(taskCompletionSource);
        this.f409e = c1519ad;
        this.f405a = bArr;
        this.f406b = l;
        this.f407c = taskCompletionSource2;
        this.f408d = integrityTokenRequest;
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1604r
    /* JADX INFO: renamed from: a */
    public final void mo345a(Exception exc) {
        if (exc instanceof C1579ad) {
            super.mo345a(new IntegrityServiceException(-9, exc));
        } else {
            super.mo345a(exc);
        }
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1604r
    /* JADX INFO: renamed from: b */
    protected final void mo346b() {
        try {
            ((InterfaceC1600n) this.f409e.f413a.m403e()).mo425c(C1519ad.m348a(this.f409e, this.f405a, this.f406b, null), new BinderC1518ac(this.f409e, this.f407c));
        } catch (RemoteException e) {
            this.f409e.f414b.m429b(e, "requestIntegrityToken(%s)", this.f408d);
            this.f407c.trySetException(new IntegrityServiceException(-100, e));
        }
    }
}
