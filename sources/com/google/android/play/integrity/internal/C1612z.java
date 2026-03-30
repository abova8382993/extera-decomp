package com.google.android.play.integrity.internal;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.z */
/* JADX INFO: loaded from: classes4.dex */
final class C1612z extends AbstractRunnableC1604r {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ ServiceConnectionC1577ab f532a;

    C1612z(ServiceConnectionC1577ab serviceConnectionC1577ab) {
        this.f532a = serviceConnectionC1577ab;
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1604r
    /* JADX INFO: renamed from: b */
    public final void mo346b() {
        C1578ac.m399s(this.f532a.f496a);
        this.f532a.f496a.f511o = null;
        this.f532a.f496a.f504h = false;
    }
}
