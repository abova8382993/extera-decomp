package com.google.android.play.core.integrity;

import android.os.RemoteException;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.InterfaceC1801i;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.as */
/* JADX INFO: loaded from: classes5.dex */
final class C1740as extends AbstractC1744aw {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ String f532a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ long f533b;

    /* JADX INFO: renamed from: c */
    final /* synthetic */ long f534c;

    /* JADX INFO: renamed from: d */
    final /* synthetic */ TaskCompletionSource f535d;

    /* JADX INFO: renamed from: e */
    final /* synthetic */ C1745ax f536e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1740as(C1745ax c1745ax, TaskCompletionSource taskCompletionSource, String str, long j, long j2, TaskCompletionSource taskCompletionSource2) {
        super(c1745ax, taskCompletionSource);
        this.f536e = c1745ax;
        this.f532a = str;
        this.f533b = j;
        this.f534c = j2;
        this.f535d = taskCompletionSource2;
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1810r
    /* JADX INFO: renamed from: b */
    public final void mo407b() {
        if (C1745ax.m425g(this.f536e)) {
            super.mo406a(new StandardIntegrityException(-2, null));
            return;
        }
        try {
            C1745ax c1745ax = this.f536e;
            ((InterfaceC1801i) c1745ax.f542a.m464e()).mo483c(C1745ax.m421a(c1745ax, this.f532a, this.f533b, this.f534c), new BinderC1742au(this.f536e, this.f535d));
        } catch (RemoteException e) {
            this.f536e.f543b.m490b(e, "requestExpressIntegrityToken(%s, %s)", this.f532a, Long.valueOf(this.f533b));
            this.f535d.trySetException(new StandardIntegrityException(-100, e));
        }
    }
}
