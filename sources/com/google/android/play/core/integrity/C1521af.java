package com.google.android.play.core.integrity;

import android.content.Context;
import com.google.android.play.integrity.internal.C1603q;
import com.google.android.play.integrity.internal.InterfaceC1584ai;
import com.google.android.play.integrity.internal.InterfaceC1587al;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.af */
/* JADX INFO: loaded from: classes4.dex */
public final class C1521af implements InterfaceC1584ai {

    /* JADX INFO: renamed from: a */
    private final InterfaceC1587al f417a;

    /* JADX INFO: renamed from: b */
    private final InterfaceC1587al f418b;

    public C1521af(InterfaceC1587al interfaceC1587al, InterfaceC1587al interfaceC1587al2) {
        this.f417a = interfaceC1587al;
        this.f418b = interfaceC1587al2;
    }

    @Override // com.google.android.play.integrity.internal.InterfaceC1587al
    /* JADX INFO: renamed from: a */
    public final /* bridge */ /* synthetic */ Object mo351a() {
        return new C1519ad((Context) this.f417a.mo351a(), (C1603q) this.f418b.mo351a());
    }
}
