package com.google.android.play.integrity.internal;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.ah */
/* JADX INFO: loaded from: classes4.dex */
public final class C1583ah implements InterfaceC1587al {

    /* JADX INFO: renamed from: a */
    private static final Object f513a = new Object();

    /* JADX INFO: renamed from: b */
    private volatile InterfaceC1587al f514b;

    /* JADX INFO: renamed from: c */
    private volatile Object f515c = f513a;

    private C1583ah(InterfaceC1587al interfaceC1587al) {
        this.f514b = interfaceC1587al;
    }

    /* JADX INFO: renamed from: b */
    public static InterfaceC1587al m410b(InterfaceC1587al interfaceC1587al) {
        return interfaceC1587al instanceof C1583ah ? interfaceC1587al : new C1583ah(interfaceC1587al);
    }

    @Override // com.google.android.play.integrity.internal.InterfaceC1587al
    /* JADX INFO: renamed from: a */
    public final Object mo351a() {
        Object objMo351a;
        Object obj = this.f515c;
        Object obj2 = f513a;
        if (obj != obj2) {
            return obj;
        }
        synchronized (this) {
            try {
                objMo351a = this.f515c;
                if (objMo351a == obj2) {
                    objMo351a = this.f514b.mo351a();
                    Object obj3 = this.f515c;
                    if (obj3 != obj2 && obj3 != objMo351a) {
                        throw new IllegalStateException("Scoped provider was invoked recursively returning different results: " + obj3 + " & " + objMo351a + ". This is likely due to a circular dependency.");
                    }
                    this.f515c = objMo351a;
                    this.f514b = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return objMo351a;
    }
}
