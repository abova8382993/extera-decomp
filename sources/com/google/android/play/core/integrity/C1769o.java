package com.google.android.play.core.integrity;

import android.content.Context;
import com.google.android.play.integrity.internal.C1789ah;
import com.google.android.play.integrity.internal.C1791aj;
import com.google.android.play.integrity.internal.InterfaceC1790ai;
import com.google.android.play.integrity.internal.InterfaceC1793al;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.o */
/* JADX INFO: loaded from: classes5.dex */
final class C1769o {

    /* JADX INFO: renamed from: a */
    private final C1769o f573a = this;

    /* JADX INFO: renamed from: b */
    private final InterfaceC1793al f574b;

    /* JADX INFO: renamed from: c */
    private final InterfaceC1793al f575c;

    /* JADX INFO: renamed from: d */
    private final InterfaceC1793al f576d;

    /* JADX INFO: renamed from: e */
    private final InterfaceC1793al f577e;

    public /* synthetic */ C1769o(Context context, C1768n c1768n) {
        InterfaceC1790ai interfaceC1790aiM472b = C1791aj.m472b(context);
        this.f574b = interfaceC1790aiM472b;
        InterfaceC1793al interfaceC1793alM471b = C1789ah.m471b(C1779y.f590a);
        this.f575c = interfaceC1793alM471b;
        InterfaceC1793al interfaceC1793alM471b2 = C1789ah.m471b(new C1727af(interfaceC1790aiM472b, interfaceC1793alM471b));
        this.f576d = interfaceC1793alM471b2;
        this.f577e = C1789ah.m471b(new C1778x(interfaceC1793alM471b2));
    }

    /* JADX INFO: renamed from: a */
    public final IntegrityManager m437a() {
        return (IntegrityManager) this.f577e.mo412a();
    }
}
