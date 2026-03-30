package com.google.android.play.integrity.internal;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.aj */
/* JADX INFO: loaded from: classes4.dex */
public final class C1585aj implements InterfaceC1584ai {

    /* JADX INFO: renamed from: a */
    private static final C1585aj f516a = new C1585aj(null);

    /* JADX INFO: renamed from: b */
    private final Object f517b;

    private C1585aj(Object obj) {
        this.f517b = obj;
    }

    /* JADX INFO: renamed from: b */
    public static InterfaceC1584ai m411b(Object obj) {
        if (obj != null) {
            return new C1585aj(obj);
        }
        throw new NullPointerException("instance cannot be null");
    }

    @Override // com.google.android.play.integrity.internal.InterfaceC1587al
    /* JADX INFO: renamed from: a */
    public final Object mo351a() {
        return this.f517b;
    }
}
