package p026j$.time.zone;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import p026j$.time.LocalDateTime;
import p026j$.time.ZoneOffset;

/* JADX INFO: renamed from: j$.time.zone.b */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2391b implements Comparable, Serializable {

    /* JADX INFO: renamed from: e */
    public static final /* synthetic */ int f991e = 0;
    private static final long serialVersionUID = -6946044323557704546L;

    /* JADX INFO: renamed from: a */
    public final long f992a;

    /* JADX INFO: renamed from: b */
    public final LocalDateTime f993b;

    /* JADX INFO: renamed from: c */
    public final ZoneOffset f994c;

    /* JADX INFO: renamed from: d */
    public final ZoneOffset f995d;

    public C2391b(long j, ZoneOffset zoneOffset, ZoneOffset zoneOffset2) {
        this.f992a = j;
        this.f993b = LocalDateTime.m619P(j, 0, zoneOffset);
        this.f994c = zoneOffset;
        this.f995d = zoneOffset2;
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new C2390a((byte) 2, this);
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        return Long.compare(this.f992a, ((C2391b) obj).f992a);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof C2391b) {
            C2391b c2391b = (C2391b) obj;
            if (this.f992a == c2391b.f992a && this.f994c.equals(c2391b.f994c) && this.f995d.equals(c2391b.f995d)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Integer.rotateLeft(this.f995d.f751b, 16) ^ (this.f993b.hashCode() ^ this.f994c.f751b);
    }

    /* JADX INFO: renamed from: t */
    public final boolean m868t() {
        return this.f995d.getTotalSeconds() > this.f994c.getTotalSeconds();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Transition[");
        sb.append(m868t() ? "Gap" : "Overlap");
        sb.append(" at ");
        sb.append(this.f993b);
        sb.append(this.f994c);
        sb.append(" to ");
        sb.append(this.f995d);
        sb.append(']');
        return sb.toString();
    }

    public C2391b(LocalDateTime localDateTime, ZoneOffset zoneOffset, ZoneOffset zoneOffset2) {
        this.f992a = localDateTime.toEpochSecond(zoneOffset);
        this.f993b = localDateTime;
        this.f994c = zoneOffset;
        this.f995d = zoneOffset2;
    }
}
