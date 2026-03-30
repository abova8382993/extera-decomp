package com.google.android.play.core.integrity;

import android.content.Context;
import com.google.android.play.integrity.internal.C1583ah;
import com.google.android.play.integrity.internal.C1585aj;
import com.google.android.play.integrity.internal.InterfaceC1584ai;
import com.google.android.play.integrity.internal.InterfaceC1587al;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.o */
/* JADX INFO: loaded from: classes4.dex */
final class C1563o {

    /* JADX INFO: renamed from: a */
    private final C1563o f476a = this;

    /* JADX INFO: renamed from: b */
    private final InterfaceC1587al f477b;

    /* JADX INFO: renamed from: c */
    private final InterfaceC1587al f478c;

    /* JADX INFO: renamed from: d */
    private final InterfaceC1587al f479d;

    /* JADX INFO: renamed from: e */
    private final InterfaceC1587al f480e;

    /* synthetic */ C1563o(Context context, C1562n c1562n) {
        InterfaceC1584ai interfaceC1584aiM411b = C1585aj.m411b(context);
        this.f477b = interfaceC1584aiM411b;
        InterfaceC1587al interfaceC1587alM410b = C1583ah.m410b(C1573y.f493a);
        this.f478c = interfaceC1587alM410b;
        InterfaceC1587al interfaceC1587alM410b2 = C1583ah.m410b(new C1521af(interfaceC1584aiM411b, interfaceC1587alM410b));
        this.f479d = interfaceC1587alM410b2;
        this.f480e = C1583ah.m410b(new C1572x(interfaceC1587alM410b2));
    }

    /* JADX INFO: renamed from: a */
    public final IntegrityManager m376a() {
        return (IntegrityManager) this.f480e.mo351a();
    }
}
