package p005c;

/* JADX INFO: renamed from: c.a */
/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractC0901a {
    /* JADX INFO: renamed from: a */
    public static void m200a(String str) {
        try {
            System.loadLibrary(str);
        } catch (Throwable th) {
            a$$ExternalSyntheticBUOutline0.m201m("Failed to load native library: ", str, th);
        }
    }
}
