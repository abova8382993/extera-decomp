package com.google.android.play.core.integrity;

import android.content.Context;
import com.google.android.play.integrity.internal.AbstractC1586ak;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.q */
/* JADX INFO: loaded from: classes4.dex */
final class C1565q implements InterfaceC1524ai {

    /* JADX INFO: renamed from: a */
    private Context f481a;

    private C1565q() {
    }

    /* synthetic */ C1565q(C1564p c1564p) {
    }

    /* JADX INFO: renamed from: a */
    public final C1565q m377a(Context context) {
        context.getClass();
        this.f481a = context;
        return this;
    }

    @Override // com.google.android.play.core.integrity.InterfaceC1524ai
    /* JADX INFO: renamed from: b */
    public final C1567s mo352b() {
        AbstractC1586ak.m412a(this.f481a, Context.class);
        return new C1567s(this.f481a, null);
    }
}
