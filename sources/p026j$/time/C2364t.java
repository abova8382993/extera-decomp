package p026j$.time;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;
import kotlin.jvm.internal.LongCompanionObject;
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

/* JADX INFO: renamed from: j$.time.t */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2364t implements Temporal, InterfaceC2377m, Comparable, Serializable {

    /* JADX INFO: renamed from: b */
    public static final /* synthetic */ int f936b = 0;
    private static final long serialVersionUID = -23038383694477807L;

    /* JADX INFO: renamed from: a */
    public final int f937a;

    static {
        C2341q c2341q = new C2341q();
        c2341q.m772h(EnumC2365a.YEAR, 4, 10, EnumC2324a0.EXCEEDS_PAD);
        c2341q.m776l(Locale.getDefault(), EnumC2350z.SMART, null);
    }

    public C2364t(int i) {
        this.f937a = i;
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    /* JADX INFO: renamed from: t */
    public static C2364t m830t(int i) {
        EnumC2365a.YEAR.m839X(i);
        return new C2364t(i);
    }

    private Object writeReplace() {
        return new C2362r((byte) 11, this);
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: B, reason: merged with bridge method [inline-methods] */
    public final C2364t mo583b(long j, InterfaceC2382r interfaceC2382r) {
        if (!(interfaceC2382r instanceof ChronoUnit)) {
            return (C2364t) interfaceC2382r.mo834t(this, j);
        }
        int i = AbstractC2363s.f935b[((ChronoUnit) interfaceC2382r).ordinal()];
        if (i == 1) {
            return m832G(j);
        }
        if (i == 2) {
            return m832G(Math.multiplyExact(j, 10L));
        }
        if (i == 3) {
            return m832G(Math.multiplyExact(j, 100L));
        }
        if (i == 4) {
            return m832G(Math.multiplyExact(j, 1000L));
        }
        if (i == 5) {
            EnumC2365a enumC2365a = EnumC2365a.ERA;
            return mo582a(Math.addExact(mo572k(enumC2365a), j), enumC2365a);
        }
        C2351g.m799d("Unsupported unit: ", interfaceC2382r);
        return null;
    }

    /* JADX INFO: renamed from: G */
    public final C2364t m832G(long j) {
        if (j == 0) {
            return this;
        }
        EnumC2365a enumC2365a = EnumC2365a.YEAR;
        return m830t(enumC2365a.f942b.m849a(((long) this.f937a) + j, enumC2365a));
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: I, reason: merged with bridge method [inline-methods] */
    public final C2364t mo582a(long j, InterfaceC2380p interfaceC2380p) {
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return (C2364t) interfaceC2380p.mo838V(this, j);
        }
        EnumC2365a enumC2365a = (EnumC2365a) interfaceC2380p;
        enumC2365a.m839X(j);
        int i = AbstractC2363s.f934a[enumC2365a.ordinal()];
        if (i == 1) {
            if (this.f937a < 1) {
                j = 1 - j;
            }
            return m830t((int) j);
        }
        if (i == 2) {
            return m830t((int) j);
        }
        if (i == 3) {
            return mo572k(EnumC2365a.ERA) == j ? this : m830t(1 - this.f937a);
        }
        throw new C2383s(AbstractC2320d.m741a("Unsupported field: ", interfaceC2380p));
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
    public final int compareTo(Object obj) {
        return this.f937a - ((C2364t) obj).f937a;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: d */
    public final Object mo568d(C2388x c2388x) {
        return c2388x == AbstractC2381q.f959b ? C2311r.f803c : c2388x == AbstractC2381q.f960c ? ChronoUnit.YEARS : super.mo568d(c2388x);
    }

    @Override // p026j$.time.temporal.InterfaceC2377m
    /* JADX INFO: renamed from: e */
    public final Temporal mo569e(Temporal temporal) {
        if (InterfaceC2304k.m719s(temporal).equals(C2311r.f803c)) {
            return temporal.mo582a(this.f937a, EnumC2365a.YEAR);
        }
        C2351g.m796a("Adjustment only supported on ISO date-time");
        return null;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof C2364t) && this.f937a == ((C2364t) obj).f937a;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: g */
    public final int mo570g(InterfaceC2380p interfaceC2380p) {
        return mo573m(interfaceC2380p).m849a(mo572k(interfaceC2380p), interfaceC2380p);
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: h */
    public final Temporal mo666j(LocalDate localDate) {
        return (C2364t) localDate.mo569e(this);
    }

    public final int hashCode() {
        return this.f937a;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: i */
    public final boolean mo571i(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p instanceof EnumC2365a ? interfaceC2380p == EnumC2365a.YEAR || interfaceC2380p == EnumC2365a.YEAR_OF_ERA || interfaceC2380p == EnumC2365a.ERA : interfaceC2380p != null && interfaceC2380p.mo841t(this);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: k */
    public final long mo572k(InterfaceC2380p interfaceC2380p) {
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return interfaceC2380p.mo837P(this);
        }
        int i = AbstractC2363s.f934a[((EnumC2365a) interfaceC2380p).ordinal()];
        if (i == 1) {
            int i2 = this.f937a;
            if (i2 < 1) {
                i2 = 1 - i2;
            }
            return i2;
        }
        if (i == 2) {
            return this.f937a;
        }
        if (i == 3) {
            return this.f937a < 1 ? 0 : 1;
        }
        throw new C2383s(AbstractC2320d.m741a("Unsupported field: ", interfaceC2380p));
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: m */
    public final C2384t mo573m(InterfaceC2380p interfaceC2380p) {
        if (interfaceC2380p == EnumC2365a.YEAR_OF_ERA) {
            return C2384t.m847f(1L, this.f937a <= 0 ? 1000000000L : 999999999L);
        }
        return super.mo573m(interfaceC2380p);
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: o */
    public final long mo586o(Temporal temporal, InterfaceC2382r interfaceC2382r) {
        C2364t c2364tM830t;
        if (temporal instanceof C2364t) {
            c2364tM830t = (C2364t) temporal;
        } else {
            Objects.requireNonNull(temporal, "temporal");
            try {
                if (!C2311r.f803c.equals(InterfaceC2304k.m719s(temporal))) {
                    temporal = LocalDate.m588G(temporal);
                }
                c2364tM830t = m830t(temporal.mo570g(EnumC2365a.YEAR));
            } catch (C2284c e) {
                C2351g.m802g("Unable to obtain Year from TemporalAccessor: ", temporal, temporal.getClass().getName(), e);
                return 0L;
            }
        }
        if (!(interfaceC2382r instanceof ChronoUnit)) {
            return interfaceC2382r.between(this, c2364tM830t);
        }
        long j = ((long) c2364tM830t.f937a) - ((long) this.f937a);
        int i = AbstractC2363s.f935b[((ChronoUnit) interfaceC2382r).ordinal()];
        if (i == 1) {
            return j;
        }
        if (i == 2) {
            return j / 10;
        }
        if (i == 3) {
            return j / 100;
        }
        if (i == 4) {
            return j / 1000;
        }
        if (i == 5) {
            EnumC2365a enumC2365a = EnumC2365a.ERA;
            return c2364tM830t.mo572k(enumC2365a) - mo572k(enumC2365a);
        }
        C2351g.m799d("Unsupported unit: ", interfaceC2382r);
        return 0L;
    }

    public final String toString() {
        return Integer.toString(this.f937a);
    }
}
