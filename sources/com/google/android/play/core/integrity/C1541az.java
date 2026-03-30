package com.google.android.play.core.integrity;

import android.content.Context;
import com.google.android.play.integrity.internal.C1603q;
import com.google.android.play.integrity.internal.InterfaceC1584ai;
import com.google.android.play.integrity.internal.InterfaceC1587al;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.az */
/* JADX INFO: loaded from: classes4.dex */
public final class C1541az implements InterfaceC1584ai {

    /* JADX INFO: renamed from: a */
    private final InterfaceC1587al f450a;

    /* JADX INFO: renamed from: b */
    private final InterfaceC1587al f451b;

    public C1541az(InterfaceC1587al interfaceC1587al, InterfaceC1587al interfaceC1587al2) {
        this.f450a = interfaceC1587al;
        this.f451b = interfaceC1587al2;
    }

    @Override // com.google.android.play.integrity.internal.InterfaceC1587al
    /* JADX INFO: renamed from: a */
    public final /* bridge */ /* synthetic */ Object mo351a() {
        return new C1539ax((Context) this.f450a.mo351a(), (C1603q) this.f451b.mo351a());
    }
}
