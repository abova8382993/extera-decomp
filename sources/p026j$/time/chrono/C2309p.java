package p026j$.time.chrono;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import p026j$.time.AbstractC2320d;
import p026j$.time.C2284c;
import p026j$.time.C2351g;
import p026j$.time.C2354j;
import p026j$.time.LocalDate;
import p026j$.time.temporal.C2383s;
import p026j$.time.temporal.C2384t;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2377m;
import p026j$.time.temporal.InterfaceC2379o;
import p026j$.time.temporal.InterfaceC2380p;
import p026j$.time.temporal.InterfaceC2382r;
import p026j$.time.temporal.Temporal;

/* JADX INFO: renamed from: j$.time.chrono.p */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2309p extends AbstractC2291d {
    private static final long serialVersionUID = -5207853542612002020L;

    /* JADX INFO: renamed from: a */
    public final transient C2307n f797a;

    /* JADX INFO: renamed from: b */
    public final transient int f798b;

    /* JADX INFO: renamed from: c */
    public final transient int f799c;

    /* JADX INFO: renamed from: d */
    public final transient int f800d;

    public C2309p(C2307n c2307n, long j) {
        int i = (int) j;
        c2307n.m723X();
        if (i < c2307n.f789e || i >= c2307n.f790f) {
            C2351g.m796a("Hijrah date out of range");
            throw null;
        }
        int iBinarySearch = Arrays.binarySearch(c2307n.f788d, i);
        iBinarySearch = iBinarySearch < 0 ? (-iBinarySearch) - 2 : iBinarySearch;
        int i2 = c2307n.f791g;
        int[] iArr = {(iBinarySearch + i2) / 12, ((i2 + iBinarySearch) % 12) + 1, (i - c2307n.f788d[iBinarySearch]) + 1};
        this.f797a = c2307n;
        this.f798b = iArr[0];
        this.f799c = iArr[1];
        this.f800d = iArr[2];
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new C2292d0((byte) 6, this);
    }

    @Override // p026j$.time.chrono.InterfaceC2287b
    /* JADX INFO: renamed from: J */
    public final long mo595J() {
        return this.f797a.m725Z(this.f798b, this.f799c, this.f800d);
    }

    @Override // p026j$.time.chrono.InterfaceC2287b
    /* JADX INFO: renamed from: K */
    public final ChronoLocalDateTime mo596K(C2354j c2354j) {
        return new C2295f(this, c2354j);
    }

    @Override // p026j$.time.chrono.InterfaceC2287b
    /* JADX INFO: renamed from: N */
    public final InterfaceC2305l mo597N() {
        return EnumC2310q.f801AH;
    }

    @Override // p026j$.time.chrono.AbstractC2291d
    /* JADX INFO: renamed from: P */
    public final InterfaceC2287b mo689P(long j) {
        return j == 0 ? this : m731Z(Math.addExact(this.f798b, (int) j), this.f799c, this.f800d);
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.chrono.InterfaceC2287b
    /* JADX INFO: renamed from: R */
    public final InterfaceC2287b mo599R(InterfaceC2379o interfaceC2379o) {
        return (C2309p) super.mo599R(interfaceC2379o);
    }

    /* JADX INFO: renamed from: V */
    public final int m728V() {
        return this.f797a.m727c0(this.f798b, this.f799c - 1) + this.f800d;
    }

    @Override // p026j$.time.chrono.AbstractC2291d
    /* JADX INFO: renamed from: X */
    public final C2309p mo687G(long j) {
        return new C2309p(this.f797a, mo595J() + j);
    }

    @Override // p026j$.time.chrono.AbstractC2291d
    /* JADX INFO: renamed from: Y */
    public final C2309p mo688I(long j) {
        if (j == 0) {
            return this;
        }
        long j2 = (((long) this.f798b) * 12) + ((long) (this.f799c - 1)) + j;
        C2307n c2307n = this.f797a;
        long jFloorDiv = Math.floorDiv(j2, 12L);
        int i = c2307n.f791g;
        if (jFloorDiv >= i / 12 && jFloorDiv <= (((c2307n.f788d.length - 1) + i) / 12) - 1) {
            return m731Z((int) jFloorDiv, ((int) Math.floorMod(j2, 12L)) + 1, this.f800d);
        }
        throw new C2284c("Invalid Hijrah year: " + jFloorDiv);
    }

    /* JADX INFO: renamed from: Z */
    public final C2309p m731Z(int i, int i2, int i3) {
        int iM726a0 = this.f797a.m726a0(i, i2);
        if (i3 > iM726a0) {
            i3 = iM726a0;
        }
        return new C2309p(this.f797a, i, i2, i3);
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: a0 */
    public final C2309p mo582a(long j, InterfaceC2380p interfaceC2380p) {
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return (C2309p) super.mo582a(j, interfaceC2380p);
        }
        EnumC2365a enumC2365a = (EnumC2365a) interfaceC2380p;
        this.f797a.mo711z(enumC2365a).m850b(j, enumC2365a);
        int i = (int) j;
        switch (AbstractC2308o.f796a[enumC2365a.ordinal()]) {
            case 1:
                return m731Z(this.f798b, this.f799c, i);
            case 2:
                return mo687G(Math.min(i, this.f797a.m727c0(this.f798b, 12)) - m728V());
            case 3:
                return mo687G((j - mo572k(EnumC2365a.ALIGNED_WEEK_OF_MONTH)) * 7);
            case 4:
                return mo687G(j - ((long) (((int) Math.floorMod(mo595J() + 3, 7L)) + 1)));
            case 5:
                return mo687G(j - mo572k(EnumC2365a.ALIGNED_DAY_OF_WEEK_IN_MONTH));
            case 6:
                return mo687G(j - mo572k(EnumC2365a.ALIGNED_DAY_OF_WEEK_IN_YEAR));
            case 7:
                return new C2309p(this.f797a, j);
            case 8:
                return mo687G((j - mo572k(EnumC2365a.ALIGNED_WEEK_OF_YEAR)) * 7);
            case 9:
                return m731Z(this.f798b, i, this.f800d);
            case 10:
                return mo688I(j - (((((long) this.f798b) * 12) + ((long) this.f799c)) - 1));
            case 11:
                if (this.f798b < 1) {
                    i = 1 - i;
                }
                return m731Z(i, this.f799c, this.f800d);
            case 12:
                return m731Z(i, this.f799c, this.f800d);
            case 13:
                return m731Z(1 - this.f798b, this.f799c, this.f800d);
            default:
                throw new C2383s(AbstractC2320d.m741a("Unsupported field: ", interfaceC2380p));
        }
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.chrono.InterfaceC2287b, p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: b */
    public final InterfaceC2287b mo583b(long j, InterfaceC2382r interfaceC2382r) {
        return (C2309p) super.mo583b(j, interfaceC2382r);
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.chrono.InterfaceC2287b, p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: c */
    public final InterfaceC2287b mo584c(long j, InterfaceC2382r interfaceC2382r) {
        return (C2309p) super.mo584c(j, interfaceC2382r);
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.chrono.InterfaceC2287b
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof C2309p) {
            C2309p c2309p = (C2309p) obj;
            if (this.f798b == c2309p.f798b && this.f799c == c2309p.f799c && this.f800d == c2309p.f800d && this.f797a.equals(c2309p.f797a)) {
                return true;
            }
        }
        return false;
    }

    @Override // p026j$.time.chrono.InterfaceC2287b
    /* JADX INFO: renamed from: f */
    public final InterfaceC2304k mo607f() {
        return this.f797a;
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: h */
    public final Temporal mo666j(LocalDate localDate) {
        return (C2309p) super.mo666j(localDate);
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.chrono.InterfaceC2287b
    public final int hashCode() {
        int i = this.f798b;
        int i2 = this.f799c;
        int i3 = this.f800d;
        this.f797a.getClass();
        return ((i & (-2048)) ^ 2100100019) ^ (((i << 11) + (i2 << 6)) + i3);
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.chrono.InterfaceC2287b
    /* JADX INFO: renamed from: j */
    public final InterfaceC2287b mo666j(InterfaceC2377m interfaceC2377m) {
        return (C2309p) super.mo666j(interfaceC2377m);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: k */
    public final long mo572k(InterfaceC2380p interfaceC2380p) {
        int iM728V;
        int iFloorMod;
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return interfaceC2380p.mo837P(this);
        }
        switch (AbstractC2308o.f796a[((EnumC2365a) interfaceC2380p).ordinal()]) {
            case 1:
                iM728V = this.f800d;
                return iM728V;
            case 2:
                iM728V = m728V();
                return iM728V;
            case 3:
                iFloorMod = (this.f800d - 1) / 7;
                iM728V = iFloorMod + 1;
                return iM728V;
            case 4:
                iFloorMod = (int) Math.floorMod(mo595J() + 3, 7L);
                iM728V = iFloorMod + 1;
                return iM728V;
            case 5:
                iFloorMod = (this.f800d - 1) % 7;
                iM728V = iFloorMod + 1;
                return iM728V;
            case 6:
                iFloorMod = (m728V() - 1) % 7;
                iM728V = iFloorMod + 1;
                return iM728V;
            case 7:
                return mo595J();
            case 8:
                iFloorMod = (m728V() - 1) / 7;
                iM728V = iFloorMod + 1;
                return iM728V;
            case 9:
                iM728V = this.f799c;
                return iM728V;
            case 10:
                return ((((long) this.f798b) * 12) + ((long) this.f799c)) - 1;
            case 11:
                iM728V = this.f798b;
                return iM728V;
            case 12:
                iM728V = this.f798b;
                return iM728V;
            case 13:
                return this.f798b <= 1 ? 0 : 1;
            default:
                throw new C2383s(AbstractC2320d.m741a("Unsupported field: ", interfaceC2380p));
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
        int i = AbstractC2308o.f796a[enumC2365a.ordinal()];
        return i != 1 ? i != 2 ? i != 3 ? this.f797a.mo711z(enumC2365a) : C2384t.m847f(1L, 5L) : C2384t.m847f(1L, this.f797a.m727c0(this.f798b, 12)) : C2384t.m847f(1L, this.f797a.m726a0(this.f798b, this.f799c));
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: b */
    public final Temporal mo583b(long j, InterfaceC2382r interfaceC2382r) {
        return (C2309p) super.mo583b(j, interfaceC2382r);
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.chrono.InterfaceC2287b, p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: c */
    public final Temporal mo584c(long j, InterfaceC2382r interfaceC2382r) {
        return (C2309p) super.mo584c(j, interfaceC2382r);
    }

    public C2309p(C2307n c2307n, int i, int i2, int i3) {
        c2307n.m725Z(i, i2, i3);
        this.f797a = c2307n;
        this.f798b = i;
        this.f799c = i2;
        this.f800d = i3;
    }
}
