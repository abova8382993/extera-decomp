package com.google.android.play.core.integrity;

import android.content.Context;
import com.google.android.play.integrity.internal.C1809q;
import com.google.android.play.integrity.internal.InterfaceC1790ai;
import com.google.android.play.integrity.internal.InterfaceC1793al;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.af */
/* JADX INFO: loaded from: classes5.dex */
public final class C1727af implements InterfaceC1790ai {

    /* JADX INFO: renamed from: a */
    private final InterfaceC1793al f514a;

    /* JADX INFO: renamed from: b */
    private final InterfaceC1793al f515b;

    public C1727af(InterfaceC1793al interfaceC1793al, InterfaceC1793al interfaceC1793al2) {
        this.f514a = interfaceC1793al;
        this.f515b = interfaceC1793al2;
    }

    @Override // com.google.android.play.integrity.internal.InterfaceC1793al
    /* JADX INFO: renamed from: a */
    public final /* bridge */ /* synthetic */ Object mo412a() {
        return new C1725ad((Context) this.f514a.mo412a(), (C1809q) this.f515b.mo412a());
    }
}
