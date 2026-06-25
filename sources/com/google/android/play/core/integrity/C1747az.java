package com.google.android.play.core.integrity;

import android.content.Context;
import com.google.android.play.integrity.internal.C1809q;
import com.google.android.play.integrity.internal.InterfaceC1790ai;
import com.google.android.play.integrity.internal.InterfaceC1793al;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.az */
/* JADX INFO: loaded from: classes5.dex */
public final class C1747az implements InterfaceC1790ai {

    /* JADX INFO: renamed from: a */
    private final InterfaceC1793al f547a;

    /* JADX INFO: renamed from: b */
    private final InterfaceC1793al f548b;

    public C1747az(InterfaceC1793al interfaceC1793al, InterfaceC1793al interfaceC1793al2) {
        this.f547a = interfaceC1793al;
        this.f548b = interfaceC1793al2;
    }

    @Override // com.google.android.play.integrity.internal.InterfaceC1793al
    /* JADX INFO: renamed from: a */
    public final /* bridge */ /* synthetic */ Object mo412a() {
        return new C1745ax((Context) this.f547a.mo412a(), (C1809q) this.f548b.mo412a());
    }
}
