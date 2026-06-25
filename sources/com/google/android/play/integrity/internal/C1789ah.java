package com.google.android.play.integrity.internal;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.ah */
/* JADX INFO: loaded from: classes5.dex */
public final class C1789ah implements InterfaceC1793al {

    /* JADX INFO: renamed from: a */
    private static final Object f610a = new Object();

    /* JADX INFO: renamed from: b */
    private volatile InterfaceC1793al f611b;

    /* JADX INFO: renamed from: c */
    private volatile Object f612c = f610a;

    private C1789ah(InterfaceC1793al interfaceC1793al) {
        this.f611b = interfaceC1793al;
    }

    /* JADX INFO: renamed from: b */
    public static InterfaceC1793al m471b(InterfaceC1793al interfaceC1793al) {
        return interfaceC1793al instanceof C1789ah ? interfaceC1793al : new C1789ah(interfaceC1793al);
    }

    @Override // com.google.android.play.integrity.internal.InterfaceC1793al
    /* JADX INFO: renamed from: a */
    public final Object mo412a() {
        Object objMo412a;
        Object obj = this.f612c;
        Object obj2 = f610a;
        if (obj != obj2) {
            return obj;
        }
        synchronized (this) {
            try {
                objMo412a = this.f612c;
                if (objMo412a == obj2) {
                    objMo412a = this.f611b.mo412a();
                    Object obj3 = this.f612c;
                    if (obj3 != obj2 && obj3 != objMo412a) {
                        throw new IllegalStateException("Scoped provider was invoked recursively returning different results: " + obj3 + " & " + objMo412a + ". This is likely due to a circular dependency.");
                    }
                    this.f612c = objMo412a;
                    this.f611b = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return objMo412a;
    }
}
