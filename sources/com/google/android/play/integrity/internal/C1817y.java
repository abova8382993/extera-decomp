package com.google.android.play.integrity.internal;

import android.os.IBinder;
import android.os.IInterface;
import java.util.Iterator;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.y */
/* JADX INFO: loaded from: classes5.dex */
final class C1817y extends AbstractRunnableC1810r {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ IBinder f627a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ ServiceConnectionC1783ab f628b;

    public C1817y(ServiceConnectionC1783ab serviceConnectionC1783ab, IBinder iBinder) {
        this.f628b = serviceConnectionC1783ab;
        this.f627a = iBinder;
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1810r
    /* JADX INFO: renamed from: b */
    public final void mo407b() {
        C1784ac c1784ac = this.f628b.f593a;
        c1784ac.f608o = (IInterface) c1784ac.f603j.mo405a(this.f627a);
        C1784ac.m459r(this.f628b.f593a);
        this.f628b.f593a.f601h = false;
        Iterator it = this.f628b.f593a.f598e.iterator();
        while (it.hasNext()) {
            ((Runnable) it.next()).run();
        }
        this.f628b.f593a.f598e.clear();
    }
}
