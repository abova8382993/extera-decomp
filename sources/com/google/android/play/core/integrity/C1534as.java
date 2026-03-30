package com.google.android.play.core.integrity;

import android.os.RemoteException;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.InterfaceC1595i;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.as */
/* JADX INFO: loaded from: classes4.dex */
final class C1534as extends AbstractC1538aw {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ String f435a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ long f436b;

    /* JADX INFO: renamed from: c */
    final /* synthetic */ long f437c;

    /* JADX INFO: renamed from: d */
    final /* synthetic */ TaskCompletionSource f438d;

    /* JADX INFO: renamed from: e */
    final /* synthetic */ C1539ax f439e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C1534as(C1539ax c1539ax, TaskCompletionSource taskCompletionSource, String str, long j, long j2, TaskCompletionSource taskCompletionSource2) {
        super(c1539ax, taskCompletionSource);
        this.f439e = c1539ax;
        this.f435a = str;
        this.f436b = j;
        this.f437c = j2;
        this.f438d = taskCompletionSource2;
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1604r
    /* JADX INFO: renamed from: b */
    protected final void mo346b() {
        if (C1539ax.m364g(this.f439e)) {
            super.mo345a(new StandardIntegrityException(-2, null));
            return;
        }
        try {
            C1539ax c1539ax = this.f439e;
            ((InterfaceC1595i) c1539ax.f445a.m403e()).mo422c(C1539ax.m360a(c1539ax, this.f435a, this.f436b, this.f437c), new BinderC1536au(this.f439e, this.f438d));
        } catch (RemoteException e) {
            this.f439e.f446b.m429b(e, "requestExpressIntegrityToken(%s, %s)", this.f435a, Long.valueOf(this.f436b));
            this.f438d.trySetException(new StandardIntegrityException(-100, e));
        }
    }
}
