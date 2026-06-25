package com.google.android.play.core.integrity;

import android.os.RemoteException;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.InterfaceC1801i;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.ar */
/* JADX INFO: loaded from: classes5.dex */
final class C1739ar extends AbstractC1744aw {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ long f529a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ TaskCompletionSource f530b;

    /* JADX INFO: renamed from: c */
    final /* synthetic */ C1745ax f531c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1739ar(C1745ax c1745ax, TaskCompletionSource taskCompletionSource, long j, TaskCompletionSource taskCompletionSource2) {
        super(c1745ax, taskCompletionSource);
        this.f531c = c1745ax;
        this.f529a = j;
        this.f530b = taskCompletionSource2;
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1810r
    /* JADX INFO: renamed from: b */
    public final void mo407b() {
        if (C1745ax.m425g(this.f531c)) {
            super.mo406a(new StandardIntegrityException(-2, null));
            return;
        }
        try {
            C1745ax c1745ax = this.f531c;
            ((InterfaceC1801i) c1745ax.f542a.m464e()).mo484d(C1745ax.m422b(c1745ax, this.f529a), new BinderC1743av(this.f531c, this.f530b));
        } catch (RemoteException e) {
            this.f531c.f543b.m490b(e, "warmUpIntegrityToken(%s)", Long.valueOf(this.f529a));
            this.f530b.trySetException(new StandardIntegrityException(-100, e));
        }
    }
}
