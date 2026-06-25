package p026j$.time.chrono;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import p026j$.time.AbstractC2320d;
import p026j$.time.C2351g;
import p026j$.time.C2354j;
import p026j$.time.C2388x;
import p026j$.time.LocalDate;
import p026j$.time.temporal.C2383s;
import p026j$.time.temporal.C2384t;
import p026j$.time.temporal.ChronoUnit;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2377m;
import p026j$.time.temporal.InterfaceC2379o;
import p026j$.time.temporal.InterfaceC2380p;
import p026j$.time.temporal.InterfaceC2382r;
import p026j$.time.temporal.Temporal;

/* JADX INFO: renamed from: j$.time.chrono.w */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2316w extends AbstractC2291d {

    /* JADX INFO: renamed from: d */
    public static final LocalDate f809d = LocalDate.m593of(1873, 1, 1);
    private static final long serialVersionUID = -305327627230580483L;

    /* JADX INFO: renamed from: a */
    public final transient LocalDate f810a;

    /* JADX INFO: renamed from: b */
    public final transient C2317x f811b;

    /* JADX INFO: renamed from: c */
    public final transient int f812c;

    public C2316w(LocalDate localDate) {
        if (localDate.m603Y(f809d)) {
            C2351g.m796a("JapaneseDate before Meiji 6 is not supported");
            throw null;
        }
        C2317x c2317xM738r = C2317x.m738r(localDate);
        this.f811b = c2317xM738r;
        this.f812c = (localDate.getYear() - c2317xM738r.f816b.getYear()) + 1;
        this.f810a = localDate;
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new C2292d0((byte) 4, this);
    }

    @Override // p026j$.time.chrono.AbstractC2291d
    /* JADX INFO: renamed from: G */
    public final InterfaceC2287b mo687G(long j) {
        return m736Y(this.f810a.plusDays(j));
    }

    @Override // p026j$.time.chrono.AbstractC2291d
    /* JADX INFO: renamed from: I */
    public final InterfaceC2287b mo688I(long j) {
        return m736Y(this.f810a.m609g0(j));
    }

    @Override // p026j$.time.chrono.InterfaceC2287b
    /* JADX INFO: renamed from: J */
    public final long mo595J() {
        return this.f810a.mo595J();
    }

    @Override // p026j$.time.chrono.InterfaceC2287b
    /* JADX INFO: renamed from: K */
    public final ChronoLocalDateTime mo596K(C2354j c2354j) {
        return new C2295f(this, c2354j);
    }

    @Override // p026j$.time.chrono.InterfaceC2287b
    /* JADX INFO: renamed from: N */
    public final InterfaceC2305l mo597N() {
        return this.f811b;
    }

    @Override // p026j$.time.chrono.AbstractC2291d
    /* JADX INFO: renamed from: P */
    public final InterfaceC2287b mo689P(long j) {
        return m736Y(this.f810a.m611i0(j));
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.chrono.InterfaceC2287b
    /* JADX INFO: renamed from: R */
    public final InterfaceC2287b mo599R(InterfaceC2379o interfaceC2379o) {
        return (C2316w) super.mo599R(interfaceC2379o);
    }

    /* JADX INFO: renamed from: V */
    public final C2316w m734V(long j, ChronoUnit chronoUnit) {
        return (C2316w) super.mo583b(j, (InterfaceC2382r) chronoUnit);
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: X */
    public final C2316w mo582a(long j, InterfaceC2380p interfaceC2380p) {
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return (C2316w) super.mo582a(j, interfaceC2380p);
        }
        EnumC2365a enumC2365a = (EnumC2365a) interfaceC2380p;
        if (mo572k(enumC2365a) == j) {
            return this;
        }
        int[] iArr = AbstractC2315v.f808a;
        int i = iArr[enumC2365a.ordinal()];
        if (i == 3 || i == 8 || i == 9) {
            C2314u c2314u = C2314u.f807c;
            int iM849a = c2314u.mo711z(enumC2365a).m849a(j, enumC2365a);
            int i2 = iArr[enumC2365a.ordinal()];
            if (i2 == 3) {
                return m736Y(this.f810a.m615m0(c2314u.mo703D(this.f811b, iM849a)));
            }
            if (i2 == 8) {
                return m736Y(this.f810a.m615m0(c2314u.mo703D(C2317x.m739t(iM849a), this.f812c)));
            }
            if (i2 == 9) {
                return m736Y(this.f810a.m615m0(iM849a));
            }
        }
        return m736Y(this.f810a.mo582a(j, interfaceC2380p));
    }

    /* JADX INFO: renamed from: Y */
    public final C2316w m736Y(LocalDate localDate) {
        return localDate.equals(this.f810a) ? this : new C2316w(localDate);
    }

    /* JADX INFO: renamed from: Z */
    public final C2316w m737Z(C2388x c2388x) {
        return (C2316w) super.mo666j(c2388x);
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.chrono.InterfaceC2287b, p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: b */
    public final InterfaceC2287b mo583b(long j, InterfaceC2382r interfaceC2382r) {
        return (C2316w) super.mo583b(j, interfaceC2382r);
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.chrono.InterfaceC2287b, p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: c */
    public final InterfaceC2287b mo584c(long j, InterfaceC2382r interfaceC2382r) {
        return (C2316w) super.mo584c(j, interfaceC2382r);
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.chrono.InterfaceC2287b
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof C2316w) {
            return this.f810a.equals(((C2316w) obj).f810a);
        }
        return false;
    }

    @Override // p026j$.time.chrono.InterfaceC2287b
    /* JADX INFO: renamed from: f */
    public final InterfaceC2304k mo607f() {
        return C2314u.f807c;
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: h */
    public final Temporal mo666j(LocalDate localDate) {
        return (C2316w) super.mo666j(localDate);
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.chrono.InterfaceC2287b
    public final int hashCode() {
        C2314u.f807c.getClass();
        return this.f810a.hashCode() ^ (-688086063);
    }

    @Override // p026j$.time.chrono.InterfaceC2287b, p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: i */
    public final boolean mo571i(InterfaceC2380p interfaceC2380p) {
        if (interfaceC2380p == EnumC2365a.ALIGNED_DAY_OF_WEEK_IN_MONTH || interfaceC2380p == EnumC2365a.ALIGNED_DAY_OF_WEEK_IN_YEAR || interfaceC2380p == EnumC2365a.ALIGNED_WEEK_OF_MONTH || interfaceC2380p == EnumC2365a.ALIGNED_WEEK_OF_YEAR) {
            return false;
        }
        return interfaceC2380p instanceof EnumC2365a ? ((EnumC2365a) interfaceC2380p).isDateBased() : interfaceC2380p != null && interfaceC2380p.mo841t(this);
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.chrono.InterfaceC2287b
    /* JADX INFO: renamed from: j */
    public final InterfaceC2287b mo666j(InterfaceC2377m interfaceC2377m) {
        return (C2316w) super.mo666j(interfaceC2377m);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: k */
    public final long mo572k(InterfaceC2380p interfaceC2380p) {
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return interfaceC2380p.mo837P(this);
        }
        switch (AbstractC2315v.f808a[((EnumC2365a) interfaceC2380p).ordinal()]) {
            case 2:
                int i = this.f812c;
                LocalDate localDate = this.f810a;
                return i == 1 ? (localDate.m601V() - this.f811b.f816b.m601V()) + 1 : localDate.m601V();
            case 3:
                return this.f812c;
            case 4:
            case 5:
            case 6:
            case 7:
                throw new C2383s(AbstractC2320d.m741a("Unsupported field: ", interfaceC2380p));
            case 8:
                return this.f811b.f815a;
            default:
                return this.f810a.mo572k(interfaceC2380p);
        }
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: m */
    public final C2384t mo573m(InterfaceC2380p interfaceC2380p) {
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return interfaceC2380p.mo835B(this);
        }
        if (!mo571i(interfaceC2380p)) {
            throw new C2383s(AbstractC2320d.m741a("Unsupported field: ", interfaceC2380p));
        }
        EnumC2365a enumC2365a = (EnumC2365a) interfaceC2380p;
        int i = AbstractC2315v.f808a[enumC2365a.ordinal()];
        if (i == 1) {
            return C2384t.m847f(1L, this.f810a.m605a0());
        }
        if (i != 2) {
            if (i != 3) {
                return C2314u.f807c.mo711z(enumC2365a);
            }
            int year = this.f811b.f816b.getYear();
            return this.f811b.m740s() != null ? C2384t.m847f(1L, (r5.f816b.getYear() - year) + 1) : C2384t.m847f(1L, 999999999 - year);
        }
        C2317x c2317xM740s = this.f811b.m740s();
        int iM601V = (c2317xM740s == null || c2317xM740s.f816b.getYear() != this.f810a.getYear()) ? this.f810a.m604Z() ? 366 : 365 : c2317xM740s.f816b.m601V() - 1;
        if (this.f812c == 1) {
            iM601V -= this.f811b.f816b.m601V() - 1;
        }
        return C2384t.m847f(1L, iM601V);
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: b */
    public final Temporal mo583b(long j, InterfaceC2382r interfaceC2382r) {
        return (C2316w) super.mo583b(j, interfaceC2382r);
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.chrono.InterfaceC2287b, p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: c */
    public final Temporal mo584c(long j, InterfaceC2382r interfaceC2382r) {
        return (C2316w) super.mo584c(j, interfaceC2382r);
    }

    public C2316w(C2317x c2317x, int i, LocalDate localDate) {
        if (!localDate.m603Y(f809d)) {
            this.f811b = c2317x;
            this.f812c = i;
            this.f810a = localDate;
            return;
        }
        C2351g.m796a("JapaneseDate before Meiji 6 is not supported");
        throw null;
    }
}
