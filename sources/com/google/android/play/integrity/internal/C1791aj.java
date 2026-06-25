package com.google.android.play.integrity.internal;

import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.aj */
/* JADX INFO: loaded from: classes5.dex */
public final class C1791aj implements InterfaceC1790ai {

    /* JADX INFO: renamed from: a */
    private static final C1791aj f613a = new C1791aj(null);

    /* JADX INFO: renamed from: b */
    private final Object f614b;

    private C1791aj(Object obj) {
        this.f614b = obj;
    }

    /* JADX INFO: renamed from: b */
    public static InterfaceC1790ai m472b(Object obj) {
        if (obj != null) {
            return new C1791aj(obj);
        }
        g$$ExternalSyntheticBUOutline2.m208m("instance cannot be null");
        return null;
    }

    @Override // com.google.android.play.integrity.internal.InterfaceC1793al
    /* JADX INFO: renamed from: a */
    public final Object mo412a() {
        return this.f614b;
    }
}
