package com.google.android.play.core.integrity;

import android.content.Context;
import com.google.android.play.integrity.internal.AbstractC1586ak;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.m */
/* JADX INFO: loaded from: classes4.dex */
final class C1559m implements InterfaceC1568t {

    /* JADX INFO: renamed from: a */
    private Context f471a;

    private C1559m() {
    }

    /* synthetic */ C1559m(C1558l c1558l) {
    }

    /* JADX INFO: renamed from: a */
    public final C1559m m372a(Context context) {
        context.getClass();
        this.f471a = context;
        return this;
    }

    @Override // com.google.android.play.core.integrity.InterfaceC1568t
    /* JADX INFO: renamed from: b */
    public final C1563o mo373b() {
        AbstractC1586ak.m412a(this.f471a, Context.class);
        return new C1563o(this.f471a, null);
    }
}
