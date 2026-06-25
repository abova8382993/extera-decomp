package p026j$.time;

import de.robv.android.xposed.callbacks.XCallback;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Objects;
import kotlin.jvm.internal.LongCompanionObject;
import org.mvel2.asm.signature.SignatureVisitor;
import p026j$.time.chrono.C2311r;
import p026j$.time.chrono.ChronoLocalDateTime;
import p026j$.time.chrono.EnumC2312s;
import p026j$.time.chrono.InterfaceC2287b;
import p026j$.time.chrono.InterfaceC2304k;
import p026j$.time.chrono.InterfaceC2305l;
import p026j$.time.temporal.AbstractC2381q;
import p026j$.time.temporal.C2383s;
import p026j$.time.temporal.C2384t;
import p026j$.time.temporal.ChronoUnit;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2376l;
import p026j$.time.temporal.InterfaceC2377m;
import p026j$.time.temporal.InterfaceC2379o;
import p026j$.time.temporal.InterfaceC2380p;
import p026j$.time.temporal.InterfaceC2382r;
import p026j$.time.temporal.Temporal;
import p026j$.time.zone.C2391b;

/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class LocalDate implements Temporal, InterfaceC2377m, InterfaceC2287b, Serializable {

    /* JADX INFO: renamed from: d */
    public static final LocalDate f727d = m593of(-999999999, 1, 1);

    /* JADX INFO: renamed from: e */
    public static final LocalDate f728e = m593of(999999999, 12, 31);
    private static final long serialVersionUID = 2942565459149668126L;

    /* JADX INFO: renamed from: a */
    public final int f729a;

    /* JADX INFO: renamed from: b */
    public final short f730b;

    /* JADX INFO: renamed from: c */
    public final short f731c;

    static {
        m593of(1970, 1, 1);
    }

    public LocalDate(int i, int i2, int i3) {
        this.f729a = i;
        this.f730b = (short) i2;
        this.f731c = (short) i3;
    }

    /* JADX INFO: renamed from: B */
    public static LocalDate m587B(int i, int i2, int i3) {
        int i4 = 28;
        if (i3 > 28) {
            if (i2 != 2) {
                i4 = (i2 == 4 || i2 == 6 || i2 == 9 || i2 == 11) ? 30 : 31;
            } else {
                C2311r.f803c.getClass();
                if (C2311r.m733X(i)) {
                    i4 = 29;
                }
            }
            if (i3 > i4) {
                if (i3 == 29) {
                    C2351g.m798c("Invalid date 'February 29' as '", i, "' is not a leap year");
                    return null;
                }
                throw new C2284c("Invalid date '" + EnumC2356l.m822I(i2).name() + " " + i3 + "'");
            }
        }
        return new LocalDate(i, i2, i3);
    }

    /* JADX INFO: renamed from: G */
    public static LocalDate m588G(InterfaceC2376l interfaceC2376l) {
        Objects.requireNonNull(interfaceC2376l, "temporal");
        LocalDate localDate = (LocalDate) interfaceC2376l.mo568d(AbstractC2381q.f963f);
        if (localDate != null) {
            return localDate;
        }
        C2351g.m801f("Unable to obtain LocalDate from TemporalAccessor: ", interfaceC2376l, " of type ", interfaceC2376l.getClass().getName());
        return null;
    }

    /* JADX INFO: renamed from: c0 */
    public static LocalDate m589c0(C2282a c2282a) {
        Instant instantOfEpochMilli = Instant.ofEpochMilli(System.currentTimeMillis());
        ZoneId zoneId = c2282a.f757a;
        Objects.requireNonNull(instantOfEpochMilli, "instant");
        Objects.requireNonNull(zoneId, "zone");
        return m590d0(Math.floorDiv(instantOfEpochMilli.getEpochSecond() + ((long) zoneId.getRules().getOffset(instantOfEpochMilli).getTotalSeconds()), 86400L));
    }

    /* JADX INFO: renamed from: d0 */
    public static LocalDate m590d0(long j) {
        long j2;
        EnumC2365a.EPOCH_DAY.m839X(j);
        long j3 = 719468 + j;
        if (j3 < 0) {
            long j4 = ((j + 719469) / 146097) - 1;
            j2 = j4 * 400;
            j3 += (-j4) * 146097;
        } else {
            j2 = 0;
        }
        long j5 = ((j3 * 400) + 591) / 146097;
        long j6 = j3 - ((j5 / 400) + (((j5 / 4) + (j5 * 365)) - (j5 / 100)));
        if (j6 < 0) {
            j5--;
            j6 = j3 - ((j5 / 400) + (((j5 / 4) + (365 * j5)) - (j5 / 100)));
        }
        int i = (int) j6;
        int i2 = ((i * 5) + 2) / 153;
        int i3 = ((i2 + 2) % 12) + 1;
        int i4 = (i - (((i2 * 306) + 5) / 10)) + 1;
        long j7 = j5 + j2 + ((long) (i2 / 10));
        EnumC2365a enumC2365a = EnumC2365a.YEAR;
        return new LocalDate(enumC2365a.f942b.m849a(j7, enumC2365a), i3, i4);
    }

    /* JADX INFO: renamed from: e0 */
    public static LocalDate m591e0(int i, int i2) {
        long j = i;
        EnumC2365a.YEAR.m839X(j);
        EnumC2365a.DAY_OF_YEAR.m839X(i2);
        C2311r.f803c.getClass();
        boolean zM733X = C2311r.m733X(j);
        if (i2 == 366 && !zM733X) {
            C2351g.m798c("Invalid date 'DayOfYear 366' as '", i, "' is not a leap year");
            return null;
        }
        EnumC2356l enumC2356lM822I = EnumC2356l.m822I(((i2 - 1) / 31) + 1);
        if (i2 > (enumC2356lM822I.m823B(zM733X) + enumC2356lM822I.m825t(zM733X)) - 1) {
            enumC2356lM822I = EnumC2356l.f921a[(enumC2356lM822I.ordinal() + 13) % 12];
        }
        return new LocalDate(i, enumC2356lM822I.getValue(), (i2 - enumC2356lM822I.m825t(zM733X)) + 1);
    }

    /* JADX INFO: renamed from: j0 */
    public static LocalDate m592j0(int i, int i2, int i3) {
        if (i2 == 2) {
            C2311r.f803c.getClass();
            i3 = Math.min(i3, C2311r.m733X((long) i) ? 29 : 28);
        } else if (i2 == 4 || i2 == 6 || i2 == 9 || i2 == 11) {
            i3 = Math.min(i3, 30);
        }
        return new LocalDate(i, i2, i3);
    }

    public static LocalDate now() {
        return m589c0(AbstractC2283b.m675d());
    }

    /* JADX INFO: renamed from: of */
    public static LocalDate m593of(int i, int i2, int i3) {
        EnumC2365a.YEAR.m839X(i);
        EnumC2365a.MONTH_OF_YEAR.m839X(i2);
        EnumC2365a.DAY_OF_MONTH.m839X(i3);
        return m587B(i, i2, i3);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new C2362r((byte) 3, this);
    }

    /* JADX INFO: renamed from: I */
    public final int m594I(InterfaceC2380p interfaceC2380p) {
        switch (AbstractC2322f.f822a[((EnumC2365a) interfaceC2380p).ordinal()]) {
            case 1:
                return this.f731c;
            case 2:
                return m601V();
            case 3:
                return ((this.f731c - 1) / 7) + 1;
            case 4:
                int i = this.f729a;
                return i >= 1 ? i : 1 - i;
            case 5:
                return m598P().getValue();
            case 6:
                return ((this.f731c - 1) % 7) + 1;
            case 7:
                return ((m601V() - 1) % 7) + 1;
            case 8:
                throw new C2383s("Invalid field 'EpochDay' for get() method, use getLong() instead");
            case 9:
                return ((m601V() - 1) / 7) + 1;
            case 10:
                return this.f730b;
            case 11:
                throw new C2383s("Invalid field 'ProlepticMonth' for get() method, use getLong() instead");
            case 12:
                return this.f729a;
            case 13:
                return this.f729a >= 1 ? 1 : 0;
            default:
                throw new C2383s(AbstractC2320d.m741a("Unsupported field: ", interfaceC2380p));
        }
    }

    @Override // p026j$.time.chrono.InterfaceC2287b
    /* JADX INFO: renamed from: J */
    public final long mo595J() {
        long j = this.f729a;
        long j2 = this.f730b;
        long j3 = 365 * j;
        long j4 = (((367 * j2) - 362) / 12) + (j >= 0 ? ((j + 399) / 400) + (((3 + j) / 4) - ((99 + j) / 100)) + j3 : j3 - ((j / (-400)) + ((j / (-4)) - (j / (-100))))) + ((long) (this.f731c - 1));
        if (j2 > 2) {
            j4 = !m604Z() ? j4 - 2 : j4 - 1;
        }
        return j4 - 719528;
    }

    @Override // p026j$.time.chrono.InterfaceC2287b
    /* JADX INFO: renamed from: K */
    public final ChronoLocalDateTime mo596K(C2354j c2354j) {
        return LocalDateTime.m618I(this, c2354j);
    }

    @Override // p026j$.time.chrono.InterfaceC2287b
    /* JADX INFO: renamed from: N */
    public final InterfaceC2305l mo597N() {
        return getYear() >= 1 ? EnumC2312s.f804CE : EnumC2312s.BCE;
    }

    /* JADX INFO: renamed from: P */
    public final DayOfWeek m598P() {
        return DayOfWeek.m567t(((int) Math.floorMod(mo595J() + 3, 7L)) + 1);
    }

    @Override // p026j$.time.chrono.InterfaceC2287b
    /* JADX INFO: renamed from: R */
    public final InterfaceC2287b mo599R(InterfaceC2379o interfaceC2379o) {
        if (interfaceC2379o != null) {
            Period period = (Period) interfaceC2379o;
            return m609g0((((long) period.f740a) * 12) + ((long) period.f741b)).plusDays(period.f742c);
        }
        Objects.requireNonNull(interfaceC2379o, "amountToAdd");
        return (LocalDate) ((Period) interfaceC2379o).mo576t(this);
    }

    @Override // p026j$.time.chrono.InterfaceC2287b, java.lang.Comparable
    /* JADX INFO: renamed from: U */
    public final int compareTo(InterfaceC2287b interfaceC2287b) {
        return interfaceC2287b instanceof LocalDate ? m616t((LocalDate) interfaceC2287b) : super.compareTo(interfaceC2287b);
    }

    /* JADX INFO: renamed from: V */
    public final int m601V() {
        return (EnumC2356l.m822I(this.f730b).m825t(m604Z()) + this.f731c) - 1;
    }

    /* JADX INFO: renamed from: X */
    public final long m602X() {
        return ((((long) this.f729a) * 12) + ((long) this.f730b)) - 1;
    }

    /* JADX INFO: renamed from: Y */
    public final boolean m603Y(InterfaceC2287b interfaceC2287b) {
        return interfaceC2287b instanceof LocalDate ? m616t((LocalDate) interfaceC2287b) < 0 : mo595J() < interfaceC2287b.mo595J();
    }

    /* JADX INFO: renamed from: Z */
    public final boolean m604Z() {
        C2311r c2311r = C2311r.f803c;
        long j = this.f729a;
        c2311r.getClass();
        return C2311r.m733X(j);
    }

    /* JADX INFO: renamed from: a0 */
    public final int m605a0() {
        short s = this.f730b;
        return s != 2 ? (s == 4 || s == 6 || s == 9 || s == 11) ? 30 : 31 : m604Z() ? 29 : 28;
    }

    public ZonedDateTime atStartOfDay(ZoneId zoneId) {
        C2391b c2391bM861e;
        Objects.requireNonNull(zoneId, "zone");
        LocalDateTime localDateTimeM618I = LocalDateTime.m618I(this, C2354j.f914g);
        if (!(zoneId instanceof ZoneOffset) && (c2391bM861e = zoneId.getRules().m861e(localDateTimeM618I)) != null && c2391bM861e.m868t()) {
            localDateTimeM618I = c2391bM861e.f993b.m624X(c2391bM861e.f995d.getTotalSeconds() - c2391bM861e.f994c.getTotalSeconds());
        }
        return ZonedDateTime.m658G(localDateTimeM618I, zoneId, null);
    }

    /* JADX INFO: renamed from: b0 */
    public final long m606b0(LocalDate localDate) {
        return (((localDate.m602X() * 32) + ((long) localDate.f731c)) - ((m602X() * 32) + ((long) this.f731c))) / 32;
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

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: d */
    public final Object mo568d(C2388x c2388x) {
        return c2388x == AbstractC2381q.f963f ? this : super.mo568d(c2388x);
    }

    @Override // p026j$.time.chrono.InterfaceC2287b
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof LocalDate) && m616t((LocalDate) obj) == 0;
    }

    @Override // p026j$.time.chrono.InterfaceC2287b
    /* JADX INFO: renamed from: f */
    public final InterfaceC2304k mo607f() {
        return C2311r.f803c;
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: f0 */
    public final LocalDate mo583b(long j, InterfaceC2382r interfaceC2382r) {
        if (!(interfaceC2382r instanceof ChronoUnit)) {
            return (LocalDate) interfaceC2382r.mo834t(this, j);
        }
        switch (AbstractC2322f.f823b[((ChronoUnit) interfaceC2382r).ordinal()]) {
            case 1:
                return plusDays(j);
            case 2:
                return m610h0(j);
            case 3:
                return m609g0(j);
            case 4:
                return m611i0(j);
            case 5:
                return m611i0(Math.multiplyExact(j, 10L));
            case 6:
                return m611i0(Math.multiplyExact(j, 100L));
            case 7:
                return m611i0(Math.multiplyExact(j, 1000L));
            case 8:
                EnumC2365a enumC2365a = EnumC2365a.ERA;
                return mo582a(Math.addExact(mo572k(enumC2365a), j), enumC2365a);
            default:
                C2351g.m799d("Unsupported unit: ", interfaceC2382r);
                return null;
        }
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: g */
    public final int mo570g(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p instanceof EnumC2365a ? m594I(interfaceC2380p) : super.mo570g(interfaceC2380p);
    }

    /* JADX INFO: renamed from: g0 */
    public final LocalDate m609g0(long j) {
        if (j == 0) {
            return this;
        }
        long j2 = (((long) this.f729a) * 12) + ((long) (this.f730b - 1)) + j;
        EnumC2365a enumC2365a = EnumC2365a.YEAR;
        return m592j0(enumC2365a.f942b.m849a(Math.floorDiv(j2, 12L), enumC2365a), ((int) Math.floorMod(j2, 12L)) + 1, this.f731c);
    }

    public int getYear() {
        return this.f729a;
    }

    /* JADX INFO: renamed from: h0 */
    public final LocalDate m610h0(long j) {
        return plusDays(Math.multiplyExact(j, 7L));
    }

    @Override // p026j$.time.chrono.InterfaceC2287b
    public final int hashCode() {
        int i = this.f729a;
        return (i & (-2048)) ^ (((i << 11) + (this.f730b << 6)) + this.f731c);
    }

    /* JADX INFO: renamed from: i0 */
    public final LocalDate m611i0(long j) {
        if (j == 0) {
            return this;
        }
        EnumC2365a enumC2365a = EnumC2365a.YEAR;
        return m592j0(enumC2365a.f942b.m849a(((long) this.f729a) + j, enumC2365a), this.f730b, this.f731c);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: k */
    public final long mo572k(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p instanceof EnumC2365a ? interfaceC2380p == EnumC2365a.EPOCH_DAY ? mo595J() : interfaceC2380p == EnumC2365a.PROLEPTIC_MONTH ? m602X() : m594I(interfaceC2380p) : interfaceC2380p.mo837P(this);
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: k0 */
    public final LocalDate mo582a(long j, InterfaceC2380p interfaceC2380p) {
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return (LocalDate) interfaceC2380p.mo838V(this, j);
        }
        EnumC2365a enumC2365a = (EnumC2365a) interfaceC2380p;
        enumC2365a.m839X(j);
        switch (AbstractC2322f.f822a[enumC2365a.ordinal()]) {
            case 1:
                int i = (int) j;
                if (this.f731c != i) {
                    return m593of(this.f729a, this.f730b, i);
                }
                return this;
            case 2:
                int i2 = (int) j;
                if (m601V() != i2) {
                    return m591e0(this.f729a, i2);
                }
                return this;
            case 3:
                return m610h0(j - mo572k(EnumC2365a.ALIGNED_WEEK_OF_MONTH));
            case 4:
                if (this.f729a < 1) {
                    j = 1 - j;
                }
                return m615m0((int) j);
            case 5:
                return plusDays(j - ((long) m598P().getValue()));
            case 6:
                return plusDays(j - mo572k(EnumC2365a.ALIGNED_DAY_OF_WEEK_IN_MONTH));
            case 7:
                return plusDays(j - mo572k(EnumC2365a.ALIGNED_DAY_OF_WEEK_IN_YEAR));
            case 8:
                return m590d0(j);
            case 9:
                return m610h0(j - mo572k(EnumC2365a.ALIGNED_WEEK_OF_YEAR));
            case 10:
                int i3 = (int) j;
                if (this.f730b != i3) {
                    EnumC2365a.MONTH_OF_YEAR.m839X(i3);
                    return m592j0(this.f729a, i3, this.f731c);
                }
                return this;
            case 11:
                return m609g0(j - m602X());
            case 12:
                return m615m0((int) j);
            case 13:
                if (mo572k(EnumC2365a.ERA) != j) {
                    return m615m0(1 - this.f729a);
                }
                return this;
            default:
                throw new C2383s(AbstractC2320d.m741a("Unsupported field: ", interfaceC2380p));
        }
    }

    @Override // p026j$.time.chrono.InterfaceC2287b
    /* JADX INFO: renamed from: l0 */
    public final LocalDate mo666j(InterfaceC2377m interfaceC2377m) {
        return interfaceC2377m instanceof LocalDate ? (LocalDate) interfaceC2377m : (LocalDate) interfaceC2377m.mo569e(this);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: m */
    public final C2384t mo573m(InterfaceC2380p interfaceC2380p) {
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return interfaceC2380p.mo835B(this);
        }
        EnumC2365a enumC2365a = (EnumC2365a) interfaceC2380p;
        if (!enumC2365a.isDateBased()) {
            throw new C2383s(AbstractC2320d.m741a("Unsupported field: ", interfaceC2380p));
        }
        int i = AbstractC2322f.f822a[enumC2365a.ordinal()];
        if (i == 1) {
            return C2384t.m847f(1L, m605a0());
        }
        if (i == 2) {
            return C2384t.m847f(1L, m604Z() ? 366 : 365);
        }
        if (i != 3) {
            return i != 4 ? enumC2365a.f942b : getYear() <= 0 ? C2384t.m847f(1L, 1000000000L) : C2384t.m847f(1L, 999999999L);
        }
        return C2384t.m847f(1L, (EnumC2356l.m822I(this.f730b) != EnumC2356l.FEBRUARY || m604Z()) ? 5L : 4L);
    }

    /* JADX INFO: renamed from: m0 */
    public final LocalDate m615m0(int i) {
        if (this.f729a == i) {
            return this;
        }
        EnumC2365a.YEAR.m839X(i);
        return m592j0(i, this.f730b, this.f731c);
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: o */
    public final long mo586o(Temporal temporal, InterfaceC2382r interfaceC2382r) {
        LocalDate localDateM588G = m588G(temporal);
        if (!(interfaceC2382r instanceof ChronoUnit)) {
            return interfaceC2382r.between(this, localDateM588G);
        }
        switch (AbstractC2322f.f823b[((ChronoUnit) interfaceC2382r).ordinal()]) {
            case 1:
                return localDateM588G.mo595J() - mo595J();
            case 2:
                return (localDateM588G.mo595J() - mo595J()) / 7;
            case 3:
                return m606b0(localDateM588G);
            case 4:
                return m606b0(localDateM588G) / 12;
            case 5:
                return m606b0(localDateM588G) / 120;
            case 6:
                return m606b0(localDateM588G) / 1200;
            case 7:
                return m606b0(localDateM588G) / 12000;
            case 8:
                EnumC2365a enumC2365a = EnumC2365a.ERA;
                return localDateM588G.mo572k(enumC2365a) - mo572k(enumC2365a);
            default:
                C2351g.m799d("Unsupported unit: ", interfaceC2382r);
                return 0L;
        }
    }

    public LocalDate plusDays(long j) {
        if (j == 0) {
            return this;
        }
        long j2 = ((long) this.f731c) + j;
        if (j2 > 0) {
            if (j2 <= 28) {
                return new LocalDate(this.f729a, this.f730b, (int) j2);
            }
            if (j2 <= 59) {
                long jM605a0 = m605a0();
                if (j2 <= jM605a0) {
                    return new LocalDate(this.f729a, this.f730b, (int) j2);
                }
                short s = this.f730b;
                if (s < 12) {
                    return new LocalDate(this.f729a, s + 1, (int) (j2 - jM605a0));
                }
                EnumC2365a.YEAR.m839X(this.f729a + 1);
                return new LocalDate(this.f729a + 1, 1, (int) (j2 - jM605a0));
            }
        }
        return m590d0(Math.addExact(mo595J(), j));
    }

    /* JADX INFO: renamed from: t */
    public final int m616t(LocalDate localDate) {
        int i = this.f729a - localDate.f729a;
        return (i == 0 && (i = this.f730b - localDate.f730b) == 0) ? this.f731c - localDate.f731c : i;
    }

    @Override // p026j$.time.chrono.InterfaceC2287b
    public final String toString() {
        int i = this.f729a;
        short s = this.f730b;
        short s2 = this.f731c;
        int iAbs = Math.abs(i);
        StringBuilder sb = new StringBuilder(10);
        if (iAbs >= 1000) {
            if (i > 9999) {
                sb.append(SignatureVisitor.EXTENDS);
            }
            sb.append(i);
        } else if (i < 0) {
            sb.append(i + XCallback.PRIORITY_LOWEST);
            sb.deleteCharAt(1);
        } else {
            sb.append(i + XCallback.PRIORITY_HIGHEST);
            sb.deleteCharAt(0);
        }
        sb.append(s < 10 ? "-0" : "-");
        sb.append((int) s);
        sb.append(s2 < 10 ? "-0" : "-");
        sb.append((int) s2);
        return sb.toString();
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: c */
    public final InterfaceC2287b mo584c(long j, InterfaceC2382r interfaceC2382r) {
        return mo583b(-1L, interfaceC2382r);
    }

    public LocalDateTime atStartOfDay() {
        return LocalDateTime.m618I(this, C2354j.f914g);
    }
}
