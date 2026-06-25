package p026j$.time;

import de.robv.android.xposed.callbacks.XCallback;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;
import kotlin.jvm.internal.LongCompanionObject;
import org.mvel2.asm.signature.SignatureVisitor;
import p026j$.time.chrono.C2311r;
import p026j$.time.chrono.InterfaceC2304k;
import p026j$.time.format.C2341q;
import p026j$.time.format.EnumC2324a0;
import p026j$.time.format.EnumC2350z;
import p026j$.time.temporal.AbstractC2381q;
import p026j$.time.temporal.C2383s;
import p026j$.time.temporal.C2384t;
import p026j$.time.temporal.ChronoUnit;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2377m;
import p026j$.time.temporal.InterfaceC2380p;
import p026j$.time.temporal.InterfaceC2382r;
import p026j$.time.temporal.Temporal;

/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class YearMonth implements Temporal, InterfaceC2377m, Comparable<YearMonth>, Serializable {

    /* JADX INFO: renamed from: c */
    public static final /* synthetic */ int f743c = 0;
    private static final long serialVersionUID = 4183400860270640070L;

    /* JADX INFO: renamed from: a */
    public final int f744a;

    /* JADX INFO: renamed from: b */
    public final int f745b;

    static {
        C2341q c2341q = new C2341q();
        c2341q.m772h(EnumC2365a.YEAR, 4, 10, EnumC2324a0.EXCEEDS_PAD);
        c2341q.m767c(SignatureVisitor.SUPER);
        c2341q.m771g(EnumC2365a.MONTH_OF_YEAR, 2);
        c2341q.m776l(Locale.getDefault(), EnumC2350z.SMART, null);
    }

    public YearMonth(int i, int i2) {
        this.f744a = i;
        this.f745b = i2;
    }

    /* JADX INFO: renamed from: of */
    public static YearMonth m637of(int i, int i2) {
        EnumC2365a.YEAR.m839X(i);
        EnumC2365a.MONTH_OF_YEAR.m839X(i2);
        return new YearMonth(i, i2);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new C2362r((byte) 12, this);
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: B, reason: merged with bridge method [inline-methods] */
    public final YearMonth mo583b(long j, InterfaceC2382r interfaceC2382r) {
        if (!(interfaceC2382r instanceof ChronoUnit)) {
            return (YearMonth) interfaceC2382r.mo834t(this, j);
        }
        switch (AbstractC2385u.f970b[((ChronoUnit) interfaceC2382r).ordinal()]) {
            case 1:
                return m639G(j);
            case 2:
                return m640I(j);
            case 3:
                return m640I(Math.multiplyExact(j, 10L));
            case 4:
                return m640I(Math.multiplyExact(j, 100L));
            case 5:
                return m640I(Math.multiplyExact(j, 1000L));
            case 6:
                EnumC2365a enumC2365a = EnumC2365a.ERA;
                return mo582a(Math.addExact(mo572k(enumC2365a), j), enumC2365a);
            default:
                C2351g.m799d("Unsupported unit: ", interfaceC2382r);
                return null;
        }
    }

    /* JADX INFO: renamed from: G */
    public final YearMonth m639G(long j) {
        if (j == 0) {
            return this;
        }
        long j2 = (((long) this.f744a) * 12) + ((long) (this.f745b - 1)) + j;
        EnumC2365a enumC2365a = EnumC2365a.YEAR;
        return m641P(enumC2365a.f942b.m849a(Math.floorDiv(j2, 12L), enumC2365a), ((int) Math.floorMod(j2, 12L)) + 1);
    }

    /* JADX INFO: renamed from: I */
    public final YearMonth m640I(long j) {
        if (j == 0) {
            return this;
        }
        EnumC2365a enumC2365a = EnumC2365a.YEAR;
        return m641P(enumC2365a.f942b.m849a(((long) this.f744a) + j, enumC2365a), this.f745b);
    }

    /* JADX INFO: renamed from: P */
    public final YearMonth m641P(int i, int i2) {
        return (this.f744a == i && this.f745b == i2) ? this : new YearMonth(i, i2);
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: V, reason: merged with bridge method [inline-methods] */
    public final YearMonth mo582a(long j, InterfaceC2380p interfaceC2380p) {
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return (YearMonth) interfaceC2380p.mo838V(this, j);
        }
        EnumC2365a enumC2365a = (EnumC2365a) interfaceC2380p;
        enumC2365a.m839X(j);
        int i = AbstractC2385u.f969a[enumC2365a.ordinal()];
        if (i == 1) {
            int i2 = (int) j;
            EnumC2365a.MONTH_OF_YEAR.m839X(i2);
            return m641P(this.f744a, i2);
        }
        if (i == 2) {
            return m639G(j - m643t());
        }
        if (i == 3) {
            if (this.f744a < 1) {
                j = 1 - j;
            }
            int i3 = (int) j;
            EnumC2365a.YEAR.m839X(i3);
            return m641P(i3, this.f745b);
        }
        if (i == 4) {
            int i4 = (int) j;
            EnumC2365a.YEAR.m839X(i4);
            return m641P(i4, this.f745b);
        }
        if (i != 5) {
            throw new C2383s(AbstractC2320d.m741a("Unsupported field: ", interfaceC2380p));
        }
        if (mo572k(EnumC2365a.ERA) == j) {
            return this;
        }
        int i5 = 1 - this.f744a;
        EnumC2365a.YEAR.m839X(i5);
        return m641P(i5, this.f745b);
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: c */
    public final Temporal mo584c(long j, InterfaceC2382r interfaceC2382r) {
        long j2;
        if (j == Long.MIN_VALUE) {
            this = mo583b(LongCompanionObject.MAX_VALUE, interfaceC2382r);
            j2 = 1;
        } else {
            j2 = -j;
        }
        return this.mo583b(j2, interfaceC2382r);
    }

    @Override // java.lang.Comparable
    public final int compareTo(YearMonth yearMonth) {
        YearMonth yearMonth2 = yearMonth;
        int i = this.f744a - yearMonth2.f744a;
        return i == 0 ? this.f745b - yearMonth2.f745b : i;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: d */
    public final Object mo568d(C2388x c2388x) {
        return c2388x == AbstractC2381q.f959b ? C2311r.f803c : c2388x == AbstractC2381q.f960c ? ChronoUnit.MONTHS : super.mo568d(c2388x);
    }

    @Override // p026j$.time.temporal.InterfaceC2377m
    /* JADX INFO: renamed from: e */
    public final Temporal mo569e(Temporal temporal) {
        if (InterfaceC2304k.m719s(temporal).equals(C2311r.f803c)) {
            return temporal.mo582a(m643t(), EnumC2365a.PROLEPTIC_MONTH);
        }
        C2351g.m796a("Adjustment only supported on ISO date-time");
        return null;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof YearMonth) {
            YearMonth yearMonth = (YearMonth) obj;
            if (this.f744a == yearMonth.f744a && this.f745b == yearMonth.f745b) {
                return true;
            }
        }
        return false;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: g */
    public final int mo570g(InterfaceC2380p interfaceC2380p) {
        return mo573m(interfaceC2380p).m849a(mo572k(interfaceC2380p), interfaceC2380p);
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: h */
    public final Temporal mo666j(LocalDate localDate) {
        return (YearMonth) localDate.mo569e(this);
    }

    public final int hashCode() {
        return (this.f745b << 27) ^ this.f744a;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: i */
    public final boolean mo571i(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p instanceof EnumC2365a ? interfaceC2380p == EnumC2365a.YEAR || interfaceC2380p == EnumC2365a.MONTH_OF_YEAR || interfaceC2380p == EnumC2365a.PROLEPTIC_MONTH || interfaceC2380p == EnumC2365a.YEAR_OF_ERA || interfaceC2380p == EnumC2365a.ERA : interfaceC2380p != null && interfaceC2380p.mo841t(this);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: k */
    public final long mo572k(InterfaceC2380p interfaceC2380p) {
        int i;
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return interfaceC2380p.mo837P(this);
        }
        int i2 = AbstractC2385u.f969a[((EnumC2365a) interfaceC2380p).ordinal()];
        if (i2 == 1) {
            i = this.f745b;
        } else {
            if (i2 == 2) {
                return m643t();
            }
            if (i2 == 3) {
                int i3 = this.f744a;
                if (i3 < 1) {
                    i3 = 1 - i3;
                }
                return i3;
            }
            if (i2 != 4) {
                if (i2 == 5) {
                    return this.f744a < 1 ? 0 : 1;
                }
                throw new C2383s(AbstractC2320d.m741a("Unsupported field: ", interfaceC2380p));
            }
            i = this.f744a;
        }
        return i;
    }

    public int lengthOfMonth() {
        EnumC2356l enumC2356lM822I = EnumC2356l.m822I(this.f745b);
        C2311r c2311r = C2311r.f803c;
        long j = this.f744a;
        c2311r.getClass();
        return enumC2356lM822I.m823B(C2311r.m733X(j));
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: m */
    public final C2384t mo573m(InterfaceC2380p interfaceC2380p) {
        if (interfaceC2380p == EnumC2365a.YEAR_OF_ERA) {
            return C2384t.m847f(1L, this.f744a <= 0 ? 1000000000L : 999999999L);
        }
        return super.mo573m(interfaceC2380p);
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: o */
    public final long mo586o(Temporal temporal, InterfaceC2382r interfaceC2382r) {
        YearMonth yearMonthM637of;
        if (temporal instanceof YearMonth) {
            yearMonthM637of = (YearMonth) temporal;
        } else {
            Objects.requireNonNull(temporal, "temporal");
            try {
                if (!C2311r.f803c.equals(InterfaceC2304k.m719s(temporal))) {
                    temporal = LocalDate.m588G(temporal);
                }
                yearMonthM637of = m637of(temporal.mo570g(EnumC2365a.YEAR), temporal.mo570g(EnumC2365a.MONTH_OF_YEAR));
            } catch (C2284c e) {
                C2351g.m802g("Unable to obtain YearMonth from TemporalAccessor: ", temporal, temporal.getClass().getName(), e);
                return 0L;
            }
        }
        if (!(interfaceC2382r instanceof ChronoUnit)) {
            return interfaceC2382r.between(this, yearMonthM637of);
        }
        long jM643t = yearMonthM637of.m643t() - m643t();
        switch (AbstractC2385u.f970b[((ChronoUnit) interfaceC2382r).ordinal()]) {
            case 1:
                return jM643t;
            case 2:
                return jM643t / 12;
            case 3:
                return jM643t / 120;
            case 4:
                return jM643t / 1200;
            case 5:
                return jM643t / 12000;
            case 6:
                EnumC2365a enumC2365a = EnumC2365a.ERA;
                return yearMonthM637of.mo572k(enumC2365a) - mo572k(enumC2365a);
            default:
                C2351g.m799d("Unsupported unit: ", interfaceC2382r);
                return 0L;
        }
    }

    /* JADX INFO: renamed from: t */
    public final long m643t() {
        return ((((long) this.f744a) * 12) + ((long) this.f745b)) - 1;
    }

    public final String toString() {
        int iAbs = Math.abs(this.f744a);
        StringBuilder sb = new StringBuilder(9);
        int i = this.f744a;
        if (iAbs >= 1000) {
            sb.append(i);
        } else if (i < 0) {
            sb.append(i + XCallback.PRIORITY_LOWEST);
            sb.deleteCharAt(1);
        } else {
            sb.append(i + XCallback.PRIORITY_HIGHEST);
            sb.deleteCharAt(0);
        }
        sb.append(this.f745b < 10 ? "-0" : "-");
        sb.append(this.f745b);
        return sb.toString();
    }
}
