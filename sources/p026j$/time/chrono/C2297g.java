package p026j$.time.chrono;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import p026j$.time.AbstractC2283b;
import p026j$.time.C2351g;
import p026j$.time.temporal.AbstractC2381q;
import p026j$.time.temporal.C2384t;
import p026j$.time.temporal.ChronoUnit;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2379o;
import p026j$.time.temporal.Temporal;

/* JADX INFO: renamed from: j$.time.chrono.g */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2297g implements InterfaceC2379o, Serializable {

    /* JADX INFO: renamed from: e */
    public static final /* synthetic */ int f771e = 0;
    private static final long serialVersionUID = 57387258289L;

    /* JADX INFO: renamed from: a */
    public final InterfaceC2304k f772a;

    /* JADX INFO: renamed from: b */
    public final int f773b;

    /* JADX INFO: renamed from: c */
    public final int f774c;

    /* JADX INFO: renamed from: d */
    public final int f775d;

    static {
        AbstractC2283b.m674c(new Object[]{ChronoUnit.YEARS, ChronoUnit.MONTHS, ChronoUnit.DAYS});
    }

    public C2297g(InterfaceC2304k interfaceC2304k, int i, int i2, int i3) {
        this.f772a = interfaceC2304k;
        this.f773b = i;
        this.f774c = i2;
        this.f775d = i3;
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof C2297g) {
            C2297g c2297g = (C2297g) obj;
            if (this.f773b == c2297g.f773b && this.f774c == c2297g.f774c && this.f775d == c2297g.f775d && this.f772a.equals(c2297g.f772a)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return this.f772a.hashCode() ^ (Integer.rotateLeft(this.f775d, 16) + (Integer.rotateLeft(this.f774c, 8) + this.f773b));
    }

    @Override // p026j$.time.temporal.InterfaceC2379o
    /* JADX INFO: renamed from: t */
    public final Temporal mo576t(Temporal temporal) {
        InterfaceC2304k interfaceC2304k = (InterfaceC2304k) temporal.mo568d(AbstractC2381q.f959b);
        if (interfaceC2304k != null && !this.f772a.equals(interfaceC2304k)) {
            C2351g.m801f("Chronology mismatch, expected: ", this.f772a.getId(), ", actual: ", interfaceC2304k.getId());
            return null;
        }
        if (this.f774c == 0) {
            int i = this.f773b;
            if (i != 0) {
                temporal = temporal.mo583b(i, ChronoUnit.YEARS);
            }
        } else {
            C2384t c2384tMo711z = this.f772a.mo711z(EnumC2365a.MONTH_OF_YEAR);
            long j = (c2384tMo711z.f965a == c2384tMo711z.f966b && c2384tMo711z.f967c == c2384tMo711z.f968d && c2384tMo711z.m852d()) ? (c2384tMo711z.f968d - c2384tMo711z.f965a) + 1 : -1L;
            int i2 = this.f773b;
            if (j > 0) {
                temporal = temporal.mo583b((((long) i2) * j) + ((long) this.f774c), ChronoUnit.MONTHS);
            } else {
                if (i2 != 0) {
                    temporal = temporal.mo583b(i2, ChronoUnit.YEARS);
                }
                temporal = temporal.mo583b(this.f774c, ChronoUnit.MONTHS);
            }
        }
        int i3 = this.f775d;
        return i3 != 0 ? temporal.mo583b(i3, ChronoUnit.DAYS) : temporal;
    }

    public final String toString() {
        if (this.f773b == 0 && this.f774c == 0 && this.f775d == 0) {
            return this.f772a.toString() + " P0D";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.f772a.toString());
        sb.append(" P");
        int i = this.f773b;
        if (i != 0) {
            sb.append(i);
            sb.append('Y');
        }
        int i2 = this.f774c;
        if (i2 != 0) {
            sb.append(i2);
            sb.append('M');
        }
        int i3 = this.f775d;
        if (i3 != 0) {
            sb.append(i3);
            sb.append('D');
        }
        return sb.toString();
    }

    public Object writeReplace() {
        return new C2292d0((byte) 9, this);
    }
}
