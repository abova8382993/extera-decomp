package p005c;

/* JADX INFO: renamed from: c.h */
/* JADX INFO: loaded from: classes4.dex */
public final class C0908h {

    /* JADX INFO: renamed from: a */
    public final String f85a;

    /* JADX INFO: renamed from: b */
    public volatile boolean f86b;

    public C0908h(String str, boolean z) {
        this.f85a = str;
        this.f86b = z;
    }

    /* JADX INFO: renamed from: a */
    public final synchronized void m209a() {
        if (this.f86b) {
            return;
        }
        String str = this.f85a;
        if (str == null) {
            this.f86b = true;
        } else {
            AbstractC0901a.m200a(str);
            this.f86b = true;
        }
    }
}
