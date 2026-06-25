package com.google.android.play.integrity.internal;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.v */
/* JADX INFO: loaded from: classes5.dex */
final class C1814v extends AbstractRunnableC1810r {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ C1784ac f626a;

    public C1814v(C1784ac c1784ac) {
        this.f626a = c1784ac;
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1810r
    /* JADX INFO: renamed from: b */
    public final void mo407b() {
        synchronized (this.f626a.f600g) {
            try {
                if (this.f626a.f606m.get() > 0 && this.f626a.f606m.decrementAndGet() > 0) {
                    this.f626a.f596c.m491c("Leaving the connection open for other ongoing calls.", new Object[0]);
                    return;
                }
                C1784ac c1784ac = this.f626a;
                if (c1784ac.f608o != null) {
                    c1784ac.f596c.m491c("Unbind from service.", new Object[0]);
                    C1784ac c1784ac2 = this.f626a;
                    c1784ac2.f595b.unbindService(c1784ac2.f607n);
                    this.f626a.f601h = false;
                    this.f626a.f608o = null;
                    this.f626a.f607n = null;
                }
                this.f626a.m462x();
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
