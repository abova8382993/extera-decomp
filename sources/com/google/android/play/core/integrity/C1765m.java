package com.google.android.play.core.integrity;

import android.content.Context;
import com.google.android.play.integrity.internal.AbstractC1792ak;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.m */
/* JADX INFO: loaded from: classes5.dex */
final class C1765m implements InterfaceC1774t {

    /* JADX INFO: renamed from: a */
    private Context f568a;

    private C1765m() {
    }

    public /* synthetic */ C1765m(C1764l c1764l) {
    }

    /* JADX INFO: renamed from: a */
    public final C1765m m433a(Context context) {
        context.getClass();
        this.f568a = context;
        return this;
    }

    @Override // com.google.android.play.core.integrity.InterfaceC1774t
    /* JADX INFO: renamed from: b */
    public final C1769o mo434b() {
        AbstractC1792ak.m473a(this.f568a, Context.class);
        return new C1769o(this.f568a, null);
    }
}
