package p026j$.time;

import p026j$.time.temporal.C2383s;

/* JADX INFO: renamed from: j$.time.g */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class C2351g {
    /* JADX INFO: renamed from: a */
    public static /* synthetic */ void m796a(String str) {
        throw new C2284c(str);
    }

    /* JADX INFO: renamed from: b */
    public static /* synthetic */ void m797b(String str, int i) {
        throw new C2284c(str + i);
    }

    /* JADX INFO: renamed from: c */
    public static /* synthetic */ void m798c(String str, int i, Object obj) {
        throw new C2284c(str + i + obj);
    }

    /* JADX INFO: renamed from: d */
    public static /* synthetic */ void m799d(String str, Object obj) {
        throw new C2383s(str + obj);
    }

    /* JADX INFO: renamed from: e */
    public static /* synthetic */ void m800e(String str, Object obj, Object obj2) {
        throw new ClassCastException(str + obj + ((Object) ", actual: ") + obj2);
    }

    /* JADX INFO: renamed from: f */
    public static /* synthetic */ void m801f(String str, Object obj, Object obj2, Object obj3) {
        throw new C2284c(str + obj + obj2 + obj3);
    }

    /* JADX INFO: renamed from: g */
    public static /* synthetic */ void m802g(String str, Object obj, Object obj2, Throwable th) {
        throw new C2284c(str + obj + ((Object) " of type ") + obj2, th);
    }

    /* JADX INFO: renamed from: h */
    public static /* synthetic */ void m803h(String str, int i) {
        throw new IllegalArgumentException(str + i);
    }

    /* JADX INFO: renamed from: i */
    public static /* synthetic */ void m804i(String str, Object obj) {
        throw new C2284c(str + obj);
    }
}
