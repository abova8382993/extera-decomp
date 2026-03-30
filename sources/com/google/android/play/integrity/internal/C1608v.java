package com.google.android.play.integrity.internal;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.v */
/* JADX INFO: loaded from: classes4.dex */
final class C1608v extends AbstractRunnableC1604r {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ C1578ac f529a;

    C1608v(C1578ac c1578ac) {
        this.f529a = c1578ac;
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1604r
    /* JADX INFO: renamed from: b */
    public final void mo346b() {
        synchronized (this.f529a.f503g) {
            try {
                if (this.f529a.f509m.get() > 0 && this.f529a.f509m.decrementAndGet() > 0) {
                    this.f529a.f499c.m430c("Leaving the connection open for other ongoing calls.", new Object[0]);
                    return;
                }
                C1578ac c1578ac = this.f529a;
                if (c1578ac.f511o != null) {
                    c1578ac.f499c.m430c("Unbind from service.", new Object[0]);
                    C1578ac c1578ac2 = this.f529a;
                    c1578ac2.f498b.unbindService(c1578ac2.f510n);
                    this.f529a.f504h = false;
                    this.f529a.f511o = null;
                    this.f529a.f510n = null;
                }
                this.f529a.m401x();
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
