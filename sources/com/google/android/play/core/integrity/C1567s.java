package com.google.android.play.core.integrity;

import android.content.Context;
import com.google.android.play.integrity.internal.C1583ah;
import com.google.android.play.integrity.internal.C1585aj;
import com.google.android.play.integrity.internal.InterfaceC1584ai;
import com.google.android.play.integrity.internal.InterfaceC1587al;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.s */
/* JADX INFO: loaded from: classes4.dex */
final class C1567s {

    /* JADX INFO: renamed from: a */
    private final C1567s f482a = this;

    /* JADX INFO: renamed from: b */
    private final InterfaceC1587al f483b;

    /* JADX INFO: renamed from: c */
    private final InterfaceC1587al f484c;

    /* JADX INFO: renamed from: d */
    private final InterfaceC1587al f485d;

    /* JADX INFO: renamed from: e */
    private final InterfaceC1587al f486e;

    /* JADX INFO: renamed from: f */
    private final InterfaceC1587al f487f;

    /* synthetic */ C1567s(Context context, C1566r c1566r) {
        InterfaceC1584ai interfaceC1584aiM411b = C1585aj.m411b(context);
        this.f483b = interfaceC1584aiM411b;
        InterfaceC1587al interfaceC1587alM410b = C1583ah.m410b(C1529an.f428a);
        this.f484c = interfaceC1587alM410b;
        InterfaceC1587al interfaceC1587alM410b2 = C1583ah.m410b(new C1541az(interfaceC1584aiM411b, interfaceC1587alM410b));
        this.f485d = interfaceC1587alM410b2;
        InterfaceC1587al interfaceC1587alM410b3 = C1583ah.m410b(new C1547be(interfaceC1587alM410b2));
        this.f486e = interfaceC1587alM410b3;
        this.f487f = C1583ah.m410b(new C1528am(interfaceC1587alM410b2, interfaceC1587alM410b3));
    }

    /* JADX INFO: renamed from: a */
    public final StandardIntegrityManager m378a() {
        return (StandardIntegrityManager) this.f487f.mo351a();
    }
}
