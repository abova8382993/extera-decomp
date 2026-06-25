package com.google.android.play.core.integrity;

import android.content.Context;
import com.google.android.play.integrity.internal.AbstractC1792ak;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.q */
/* JADX INFO: loaded from: classes5.dex */
final class C1771q implements InterfaceC1730ai {

    /* JADX INFO: renamed from: a */
    private Context f578a;

    private C1771q() {
    }

    public /* synthetic */ C1771q(C1770p c1770p) {
    }

    /* JADX INFO: renamed from: a */
    public final C1771q m438a(Context context) {
        context.getClass();
        this.f578a = context;
        return this;
    }

    @Override // com.google.android.play.core.integrity.InterfaceC1730ai
    /* JADX INFO: renamed from: b */
    public final C1773s mo413b() {
        AbstractC1792ak.m473a(this.f578a, Context.class);
        return new C1773s(this.f578a, null);
    }
}
