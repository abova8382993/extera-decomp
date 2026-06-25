package com.google.android.play.integrity.internal;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.z */
/* JADX INFO: loaded from: classes5.dex */
final class C1818z extends AbstractRunnableC1810r {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ ServiceConnectionC1783ab f629a;

    public C1818z(ServiceConnectionC1783ab serviceConnectionC1783ab) {
        this.f629a = serviceConnectionC1783ab;
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1810r
    /* JADX INFO: renamed from: b */
    public final void mo407b() {
        C1784ac.m460s(this.f629a.f593a);
        this.f629a.f593a.f608o = null;
        this.f629a.f593a.f601h = false;
    }
}
