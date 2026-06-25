package p026j$.time;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.regex.Pattern;
import p026j$.time.chrono.C2311r;
import p026j$.time.chrono.InterfaceC2304k;
import p026j$.time.temporal.AbstractC2381q;
import p026j$.time.temporal.ChronoUnit;
import p026j$.time.temporal.InterfaceC2379o;
import p026j$.time.temporal.Temporal;

/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class Period implements InterfaceC2379o, Serializable {

    /* JADX INFO: renamed from: d */
    public static final Period f739d = new Period(0, 0, 0);
    private static final long serialVersionUID = -3587258372562876L;

    /* JADX INFO: renamed from: a */
    public final int f740a;

    /* JADX INFO: renamed from: b */
    public final int f741b;

    /* JADX INFO: renamed from: c */
    public final int f742c;

    static {
        Pattern.compile("([-+]?)P(?:([-+]?[0-9]+)Y)?(?:([-+]?[0-9]+)M)?(?:([-+]?[0-9]+)W)?(?:([-+]?[0-9]+)D)?", 2);
        AbstractC2283b.m674c(new Object[]{ChronoUnit.YEARS, ChronoUnit.MONTHS, ChronoUnit.DAYS});
    }

    public Period(int i, int i2, int i3) {
        this.f740a = i;
        this.f741b = i2;
        this.f742c = i3;
    }

    /* JADX INFO: renamed from: a */
    public static Period m636a(int i, int i2, int i3) {
        return ((i | i2) | i3) == 0 ? f739d : new Period(i, i2, i3);
    }

    public static Period between(LocalDate localDate, LocalDate localDate2) {
        localDate.getClass();
        LocalDate localDateM588G = LocalDate.m588G(localDate2);
        long jM602X = localDateM588G.m602X() - localDate.m602X();
        int iM605a0 = localDateM588G.f731c - localDate.f731c;
        if (jM602X > 0 && iM605a0 < 0) {
            jM602X--;
            iM605a0 = (int) (localDateM588G.mo595J() - localDate.m609g0(jM602X).mo595J());
        } else if (jM602X < 0 && iM605a0 > 0) {
            jM602X++;
            iM605a0 -= localDateM588G.m605a0();
        }
        return m636a(Math.toIntExact(jM602X / 12), (int) (jM602X % 12), iM605a0);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new C2362r((byte) 14, this);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Period) {
            Period period = (Period) obj;
            if (this.f740a == period.f740a && this.f741b == period.f741b && this.f742c == period.f742c) {
                return true;
            }
        }
        return false;
    }

    public int getYears() {
        return this.f740a;
    }

    public final int hashCode() {
        return Integer.rotateLeft(this.f742c, 16) + Integer.rotateLeft(this.f741b, 8) + this.f740a;
    }

    @Override // p026j$.time.temporal.InterfaceC2379o
    /* JADX INFO: renamed from: t */
    public final Temporal mo576t(Temporal temporal) {
        InterfaceC2304k interfaceC2304k = (InterfaceC2304k) temporal.mo568d(AbstractC2381q.f959b);
        if (interfaceC2304k != null && !C2311r.f803c.equals(interfaceC2304k)) {
            throw new C2284c("Chronology mismatch, expected: ISO, actual: " + interfaceC2304k.getId());
        }
        int i = this.f741b;
        int i2 = this.f740a;
        if (i != 0) {
            long j = (((long) i2) * 12) + ((long) i);
            if (j != 0) {
                temporal = temporal.mo583b(j, ChronoUnit.MONTHS);
            }
        } else if (i2 != 0) {
            temporal = temporal.mo583b(i2, ChronoUnit.YEARS);
        }
        int i3 = this.f742c;
        return i3 != 0 ? temporal.mo583b(i3, ChronoUnit.DAYS) : temporal;
    }

    public final String toString() {
        if (this == f739d) {
            return "P0D";
        }
        StringBuilder sb = new StringBuilder("P");
        int i = this.f740a;
        if (i != 0) {
            sb.append(i);
            sb.append('Y');
        }
        int i2 = this.f741b;
        if (i2 != 0) {
            sb.append(i2);
            sb.append('M');
        }
        int i3 = this.f742c;
        if (i3 != 0) {
            sb.append(i3);
            sb.append('D');
        }
        return sb.toString();
    }
}
