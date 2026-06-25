package com.google.android.play.integrity.internal;

import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.u */
/* JADX INFO: loaded from: classes5.dex */
final class C1813u extends AbstractRunnableC1810r {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ TaskCompletionSource f623a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ AbstractRunnableC1810r f624b;

    /* JADX INFO: renamed from: c */
    final /* synthetic */ C1784ac f625c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1813u(C1784ac c1784ac, TaskCompletionSource taskCompletionSource, TaskCompletionSource taskCompletionSource2, AbstractRunnableC1810r abstractRunnableC1810r) {
        super(taskCompletionSource);
        this.f625c = c1784ac;
        this.f623a = taskCompletionSource2;
        this.f624b = abstractRunnableC1810r;
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1810r
    /* JADX INFO: renamed from: b */
    public final void mo407b() {
        synchronized (this.f625c.f600g) {
            try {
                C1784ac.m456o(this.f625c, this.f623a);
                if (this.f625c.f606m.getAndIncrement() > 0) {
                    this.f625c.f596c.m491c("Already connected to the service.", new Object[0]);
                }
                C1784ac.m458q(this.f625c, this.f624b);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
