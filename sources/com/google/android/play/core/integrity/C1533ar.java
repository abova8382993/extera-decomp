package com.google.android.play.core.integrity;

import android.os.RemoteException;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.InterfaceC1595i;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.ar */
/* JADX INFO: loaded from: classes4.dex */
final class C1533ar extends AbstractC1538aw {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ long f432a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ TaskCompletionSource f433b;

    /* JADX INFO: renamed from: c */
    final /* synthetic */ C1539ax f434c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C1533ar(C1539ax c1539ax, TaskCompletionSource taskCompletionSource, long j, TaskCompletionSource taskCompletionSource2) {
        super(c1539ax, taskCompletionSource);
        this.f434c = c1539ax;
        this.f432a = j;
        this.f433b = taskCompletionSource2;
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1604r
    /* JADX INFO: renamed from: b */
    protected final void mo346b() {
        if (C1539ax.m364g(this.f434c)) {
            super.mo345a(new StandardIntegrityException(-2, null));
            return;
        }
        try {
            C1539ax c1539ax = this.f434c;
            ((InterfaceC1595i) c1539ax.f445a.m403e()).mo423d(C1539ax.m361b(c1539ax, this.f432a), new BinderC1537av(this.f434c, this.f433b));
        } catch (RemoteException e) {
            this.f434c.f446b.m429b(e, "warmUpIntegrityToken(%s)", Long.valueOf(this.f432a));
            this.f433b.trySetException(new StandardIntegrityException(-100, e));
        }
    }
}
