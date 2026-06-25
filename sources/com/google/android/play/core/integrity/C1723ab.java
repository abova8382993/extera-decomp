package com.google.android.play.core.integrity;

import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.AbstractRunnableC1810r;
import com.google.android.play.integrity.internal.C1785ad;
import com.google.android.play.integrity.internal.InterfaceC1806n;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.ab */
/* JADX INFO: loaded from: classes5.dex */
final class C1723ab extends AbstractRunnableC1810r {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ byte[] f502a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ Long f503b;

    /* JADX INFO: renamed from: c */
    final /* synthetic */ TaskCompletionSource f504c;

    /* JADX INFO: renamed from: d */
    final /* synthetic */ IntegrityTokenRequest f505d;

    /* JADX INFO: renamed from: e */
    final /* synthetic */ C1725ad f506e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1723ab(C1725ad c1725ad, TaskCompletionSource taskCompletionSource, byte[] bArr, Long l, Parcelable parcelable, TaskCompletionSource taskCompletionSource2, IntegrityTokenRequest integrityTokenRequest) {
        super(taskCompletionSource);
        this.f506e = c1725ad;
        this.f502a = bArr;
        this.f503b = l;
        this.f504c = taskCompletionSource2;
        this.f505d = integrityTokenRequest;
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1810r
    /* JADX INFO: renamed from: a */
    public final void mo406a(Exception exc) {
        if (exc instanceof C1785ad) {
            super.mo406a(new IntegrityServiceException(-9, exc));
        } else {
            super.mo406a(exc);
        }
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1810r
    /* JADX INFO: renamed from: b */
    public final void mo407b() {
        try {
            ((InterfaceC1806n) this.f506e.f510a.m464e()).mo486c(C1725ad.m409a(this.f506e, this.f502a, this.f503b, null), new BinderC1724ac(this.f506e, this.f504c));
        } catch (RemoteException e) {
            this.f506e.f511b.m490b(e, "requestIntegrityToken(%s)", this.f505d);
            this.f504c.trySetException(new IntegrityServiceException(-100, e));
        }
    }
}
