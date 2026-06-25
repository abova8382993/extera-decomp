package p026j$.time;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/* JADX INFO: renamed from: j$.time.a */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2282a extends AbstractC2283b implements Serializable {

    /* JADX INFO: renamed from: b */
    public static final C2282a f756b;
    private static final long serialVersionUID = 6740630888130243051L;

    /* JADX INFO: renamed from: a */
    public final ZoneId f757a;

    static {
        System.currentTimeMillis();
        f756b = new C2282a(ZoneOffset.UTC);
    }

    public C2282a(ZoneId zoneId) {
        this.f757a = zoneId;
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
    }

    public final boolean equals(Object obj) {
        if (obj instanceof C2282a) {
            return this.f757a.equals(((C2282a) obj).f757a);
        }
        return false;
    }

    public final int hashCode() {
        return this.f757a.hashCode() + 1;
    }

    public final String toString() {
        return "SystemClock[" + this.f757a + "]";
    }
}
