package com.google.android.play.core.integrity;

import android.content.Context;
import com.google.android.play.integrity.internal.C1789ah;
import com.google.android.play.integrity.internal.C1791aj;
import com.google.android.play.integrity.internal.InterfaceC1790ai;
import com.google.android.play.integrity.internal.InterfaceC1793al;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.s */
/* JADX INFO: loaded from: classes5.dex */
final class C1773s {

    /* JADX INFO: renamed from: a */
    private final C1773s f579a = this;

    /* JADX INFO: renamed from: b */
    private final InterfaceC1793al f580b;

    /* JADX INFO: renamed from: c */
    private final InterfaceC1793al f581c;

    /* JADX INFO: renamed from: d */
    private final InterfaceC1793al f582d;

    /* JADX INFO: renamed from: e */
    private final InterfaceC1793al f583e;

    /* JADX INFO: renamed from: f */
    private final InterfaceC1793al f584f;

    public /* synthetic */ C1773s(Context context, C1772r c1772r) {
        InterfaceC1790ai interfaceC1790aiM472b = C1791aj.m472b(context);
        this.f580b = interfaceC1790aiM472b;
        InterfaceC1793al interfaceC1793alM471b = C1789ah.m471b(C1735an.f525a);
        this.f581c = interfaceC1793alM471b;
        InterfaceC1793al interfaceC1793alM471b2 = C1789ah.m471b(new C1747az(interfaceC1790aiM472b, interfaceC1793alM471b));
        this.f582d = interfaceC1793alM471b2;
        InterfaceC1793al interfaceC1793alM471b3 = C1789ah.m471b(new C1753be(interfaceC1793alM471b2));
        this.f583e = interfaceC1793alM471b3;
        this.f584f = C1789ah.m471b(new C1734am(interfaceC1793alM471b2, interfaceC1793alM471b3));
    }

    /* JADX INFO: renamed from: a */
    public final StandardIntegrityManager m439a() {
        return (StandardIntegrityManager) this.f584f.mo412a();
    }
}
