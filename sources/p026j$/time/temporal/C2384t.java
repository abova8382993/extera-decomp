package p026j$.time.temporal;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import p026j$.time.C2351g;

/* JADX INFO: renamed from: j$.time.temporal.t */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2384t implements Serializable {
    private static final long serialVersionUID = -7317881728594519368L;

    /* JADX INFO: renamed from: a */
    public final long f965a;

    /* JADX INFO: renamed from: b */
    public final long f966b;

    /* JADX INFO: renamed from: c */
    public final long f967c;

    /* JADX INFO: renamed from: d */
    public final long f968d;

    public C2384t(long j, long j2, long j3, long j4) {
        this.f965a = j;
        this.f966b = j2;
        this.f967c = j3;
        this.f968d = j4;
    }

    /* JADX INFO: renamed from: f */
    public static C2384t m847f(long j, long j2) {
        if (j <= j2) {
            return new C2384t(j, j, j2, j2);
        }
        throw new IllegalArgumentException("Minimum value must be less than maximum value");
    }

    /* JADX INFO: renamed from: g */
    public static C2384t m848g(long j, long j2) {
        if (j > j2) {
            throw new IllegalArgumentException("Smallest maximum value must be less than largest maximum value");
        }
        if (1 <= j2) {
            return new C2384t(1L, 1L, j, j2);
        }
        throw new IllegalArgumentException("Minimum value must be less than maximum value");
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        long j = this.f965a;
        long j2 = this.f966b;
        if (j > j2) {
            throw new InvalidObjectException("Smallest minimum value must be less than largest minimum value");
        }
        long j3 = this.f967c;
        long j4 = this.f968d;
        if (j3 > j4) {
            throw new InvalidObjectException("Smallest maximum value must be less than largest maximum value");
        }
        if (j2 > j4) {
            throw new InvalidObjectException("Minimum value must be less than maximum value");
        }
    }

    /* JADX INFO: renamed from: a */
    public final int m849a(long j, InterfaceC2380p interfaceC2380p) {
        if (m852d() && m853e(j)) {
            return (int) j;
        }
        C2351g.m796a(m851c(j, interfaceC2380p));
        return 0;
    }

    /* JADX INFO: renamed from: b */
    public final void m850b(long j, InterfaceC2380p interfaceC2380p) {
        if (m853e(j)) {
            return;
        }
        C2351g.m796a(m851c(j, interfaceC2380p));
    }

    /* JADX INFO: renamed from: c */
    public final String m851c(long j, InterfaceC2380p interfaceC2380p) {
        if (interfaceC2380p == null) {
            return "Invalid value (valid values " + this + "): " + j;
        }
        return "Invalid value for " + interfaceC2380p + " (valid values " + this + "): " + j;
    }

    /* JADX INFO: renamed from: d */
    public final boolean m852d() {
        return this.f965a >= -2147483648L && this.f968d <= 2147483647L;
    }

    /* JADX INFO: renamed from: e */
    public final boolean m853e(long j) {
        return j >= this.f965a && j <= this.f968d;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof C2384t) {
            C2384t c2384t = (C2384t) obj;
            if (this.f965a == c2384t.f965a && this.f966b == c2384t.f966b && this.f967c == c2384t.f967c && this.f968d == c2384t.f968d) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        long j = this.f965a;
        long j2 = this.f966b;
        long j3 = j + (j2 << 16) + (j2 >> 48);
        long j4 = this.f967c;
        long j5 = j3 + (j4 << 32) + (j4 >> 32);
        long j6 = this.f968d;
        long j7 = j5 + (j6 << 48) + (j6 >> 16);
        return (int) (j7 ^ (j7 >>> 32));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.f965a);
        if (this.f965a != this.f966b) {
            sb.append('/');
            sb.append(this.f966b);
        }
        sb.append(" - ");
        sb.append(this.f967c);
        if (this.f967c != this.f968d) {
            sb.append('/');
            sb.append(this.f968d);
        }
        return sb.toString();
    }
}
