package com.google.android.play.integrity.internal;

import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.u */
/* JADX INFO: loaded from: classes4.dex */
final class C1607u extends AbstractRunnableC1604r {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ TaskCompletionSource f526a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ AbstractRunnableC1604r f527b;

    /* JADX INFO: renamed from: c */
    final /* synthetic */ C1578ac f528c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C1607u(C1578ac c1578ac, TaskCompletionSource taskCompletionSource, TaskCompletionSource taskCompletionSource2, AbstractRunnableC1604r abstractRunnableC1604r) {
        super(taskCompletionSource);
        this.f528c = c1578ac;
        this.f526a = taskCompletionSource2;
        this.f527b = abstractRunnableC1604r;
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1604r
    /* JADX INFO: renamed from: b */
    public final void mo346b() {
        synchronized (this.f528c.f503g) {
            try {
                C1578ac.m395o(this.f528c, this.f526a);
                if (this.f528c.f509m.getAndIncrement() > 0) {
                    this.f528c.f499c.m430c("Already connected to the service.", new Object[0]);
                }
                C1578ac.m397q(this.f528c, this.f527b);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
