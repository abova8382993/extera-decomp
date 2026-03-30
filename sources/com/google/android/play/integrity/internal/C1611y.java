package com.google.android.play.integrity.internal;

import android.os.IBinder;
import android.os.IInterface;
import java.util.Iterator;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.y */
/* JADX INFO: loaded from: classes4.dex */
final class C1611y extends AbstractRunnableC1604r {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ IBinder f530a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ ServiceConnectionC1577ab f531b;

    C1611y(ServiceConnectionC1577ab serviceConnectionC1577ab, IBinder iBinder) {
        this.f531b = serviceConnectionC1577ab;
        this.f530a = iBinder;
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1604r
    /* JADX INFO: renamed from: b */
    public final void mo346b() {
        C1578ac c1578ac = this.f531b.f496a;
        c1578ac.f511o = (IInterface) c1578ac.f506j.mo344a(this.f530a);
        C1578ac.m398r(this.f531b.f496a);
        this.f531b.f496a.f504h = false;
        Iterator it = this.f531b.f496a.f501e.iterator();
        while (it.hasNext()) {
            ((Runnable) it.next()).run();
        }
        this.f531b.f496a.f501e.clear();
    }
}
