package p026j$.time.chrono;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.util.Objects;
import p026j$.time.AbstractC2320d;
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

/* JADX INFO: renamed from: j$.time.chrono.b0 */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2288b0 extends AbstractC2291d {
    private static final long serialVersionUID = 1300372329181994526L;

    /* JADX INFO: renamed from: a */
    public final transient LocalDate f761a;

    public C2288b0(LocalDate localDate) {
        Objects.requireNonNull(localDate, "isoDate");
        this.f761a = localDate;
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new C2292d0((byte) 7, this);
    }

    @Override // p026j$.time.chrono.AbstractC2291d
    /* JADX INFO: renamed from: G */
    public final InterfaceC2287b mo687G(long j) {
        return m692Y(this.f761a.plusDays(j));
    }

    @Override // p026j$.time.chrono.AbstractC2291d
    /* JADX INFO: renamed from: I */
    public final InterfaceC2287b mo688I(long j) {
        return m692Y(this.f761a.m609g0(j));
    }

    @Override // p026j$.time.chrono.InterfaceC2287b
    /* JADX INFO: renamed from: J */
    public final long mo595J() {
        return this.f761a.mo595J();
    }

    @Override // p026j$.time.chrono.InterfaceC2287b
    /* JADX INFO: renamed from: K */
    public final ChronoLocalDateTime mo596K(C2354j c2354j) {
        return new C2295f(this, c2354j);
    }

    @Override // p026j$.time.chrono.InterfaceC2287b
    /* JADX INFO: renamed from: N */
    public final InterfaceC2305l mo597N() {
        return m690V() >= 1 ? EnumC2290c0.ROC : EnumC2290c0.BEFORE_ROC;
    }

    @Override // p026j$.time.chrono.AbstractC2291d
    /* JADX INFO: renamed from: P */
    public final InterfaceC2287b mo689P(long j) {
        return m692Y(this.f761a.m611i0(j));
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.chrono.InterfaceC2287b
    /* JADX INFO: renamed from: R */
    public final InterfaceC2287b mo599R(InterfaceC2379o interfaceC2379o) {
        return (C2288b0) super.mo599R(interfaceC2379o);
    }

    /* JADX INFO: renamed from: V */
    public final int m690V() {
        return this.f761a.getYear() - 1911;
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x0049  */
    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: X */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final p026j$.time.chrono.C2288b0 mo582a(long r8, p026j$.time.temporal.InterfaceC2380p r10) {
        /*
            r7 = this;
            boolean r0 = r10 instanceof p026j$.time.temporal.EnumC2365a
            if (r0 == 0) goto L9f
            r0 = r10
            j$.time.temporal.a r0 = (p026j$.time.temporal.EnumC2365a) r0
            long r1 = r7.mo572k(r0)
            int r1 = (r1 > r8 ? 1 : (r1 == r8 ? 0 : -1))
            if (r1 != 0) goto L10
            return r7
        L10:
            int[] r1 = p026j$.time.chrono.AbstractC2286a0.f760a
            int r2 = r0.ordinal()
            r2 = r1[r2]
            r3 = 7
            r4 = 6
            r5 = 4
            if (r2 == r5) goto L49
            r6 = 5
            if (r2 == r6) goto L25
            if (r2 == r4) goto L49
            if (r2 == r3) goto L49
            goto L5f
        L25:
            j$.time.chrono.z r10 = p026j$.time.chrono.C2319z.f819c
            j$.time.temporal.t r10 = r10.mo711z(r0)
            r10.m850b(r8, r0)
            int r10 = r7.m690V()
            long r0 = (long) r10
            r2 = 12
            long r0 = r0 * r2
            j$.time.LocalDate r10 = r7.f761a
            short r2 = r10.f730b
            long r2 = (long) r2
            long r0 = r0 + r2
            r2 = 1
            long r0 = r0 - r2
            long r8 = r8 - r0
            j$.time.LocalDate r8 = r10.m609g0(r8)
            j$.time.chrono.b0 r7 = r7.m692Y(r8)
            return r7
        L49:
            j$.time.chrono.z r2 = p026j$.time.chrono.C2319z.f819c
            j$.time.temporal.t r2 = r2.mo711z(r0)
            int r2 = r2.m849a(r8, r0)
            int r0 = r0.ordinal()
            r0 = r1[r0]
            if (r0 == r5) goto L88
            if (r0 == r4) goto L7b
            if (r0 == r3) goto L6a
        L5f:
            j$.time.LocalDate r0 = r7.f761a
            j$.time.LocalDate r8 = r0.mo582a(r8, r10)
            j$.time.chrono.b0 r7 = r7.m692Y(r8)
            return r7
        L6a:
            j$.time.LocalDate r8 = r7.f761a
            int r9 = r7.m690V()
            int r9 = 1912 - r9
            j$.time.LocalDate r8 = r8.m615m0(r9)
            j$.time.chrono.b0 r7 = r7.m692Y(r8)
            return r7
        L7b:
            j$.time.LocalDate r8 = r7.f761a
            int r2 = r2 + 1911
            j$.time.LocalDate r8 = r8.m615m0(r2)
            j$.time.chrono.b0 r7 = r7.m692Y(r8)
            return r7
        L88:
            j$.time.LocalDate r8 = r7.f761a
            int r9 = r7.m690V()
            r10 = 1
            if (r9 < r10) goto L94
            int r2 = r2 + 1911
            goto L96
        L94:
            int r2 = 1912 - r2
        L96:
            j$.time.LocalDate r8 = r8.m615m0(r2)
            j$.time.chrono.b0 r7 = r7.m692Y(r8)
            return r7
        L9f:
            j$.time.chrono.b r7 = super.mo582a(r8, r10)
            j$.time.chrono.b0 r7 = (p026j$.time.chrono.C2288b0) r7
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: p026j$.time.chrono.C2288b0.mo582a(long, j$.time.temporal.p):j$.time.chrono.b0");
    }

    /* JADX INFO: renamed from: Y */
    public final C2288b0 m692Y(LocalDate localDate) {
        return localDate.equals(this.f761a) ? this : new C2288b0(localDate);
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.chrono.InterfaceC2287b, p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: b */
    public final InterfaceC2287b mo583b(long j, InterfaceC2382r interfaceC2382r) {
        return (C2288b0) super.mo583b(j, interfaceC2382r);
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.chrono.InterfaceC2287b, p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: c */
    public final InterfaceC2287b mo584c(long j, InterfaceC2382r interfaceC2382r) {
        return (C2288b0) super.mo584c(j, interfaceC2382r);
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.chrono.InterfaceC2287b
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof C2288b0) {
            return this.f761a.equals(((C2288b0) obj).f761a);
        }
        return false;
    }

    @Override // p026j$.time.chrono.InterfaceC2287b
    /* JADX INFO: renamed from: f */
    public final InterfaceC2304k mo607f() {
        return C2319z.f819c;
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: h */
    public final Temporal mo666j(LocalDate localDate) {
        return (C2288b0) super.mo666j(localDate);
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.chrono.InterfaceC2287b
    public final int hashCode() {
        C2319z.f819c.getClass();
        return this.f761a.hashCode() ^ (-1990173233);
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.chrono.InterfaceC2287b
    /* JADX INFO: renamed from: j */
    public final InterfaceC2287b mo666j(InterfaceC2377m interfaceC2377m) {
        return (C2288b0) super.mo666j(interfaceC2377m);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: k */
    public final long mo572k(InterfaceC2380p interfaceC2380p) {
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return interfaceC2380p.mo837P(this);
        }
        int i = AbstractC2286a0.f760a[((EnumC2365a) interfaceC2380p).ordinal()];
        if (i == 4) {
            int iM690V = m690V();
            if (iM690V < 1) {
                iM690V = 1 - iM690V;
            }
            return iM690V;
        }
        if (i == 5) {
            return ((((long) m690V()) * 12) + ((long) this.f761a.f730b)) - 1;
        }
        if (i == 6) {
            return m690V();
        }
        if (i != 7) {
            return this.f761a.mo572k(interfaceC2380p);
        }
        return m690V() < 1 ? 0 : 1;
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
        int i = AbstractC2286a0.f760a[enumC2365a.ordinal()];
        if (i == 1 || i == 2 || i == 3) {
            return this.f761a.mo573m(interfaceC2380p);
        }
        if (i != 4) {
            return C2319z.f819c.mo711z(enumC2365a);
        }
        C2384t c2384t = EnumC2365a.YEAR.f942b;
        return C2384t.m847f(1L, m690V() <= 0 ? (-c2384t.f965a) + 1912 : c2384t.f968d - 1911);
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: b */
    public final Temporal mo583b(long j, InterfaceC2382r interfaceC2382r) {
        return (C2288b0) super.mo583b(j, interfaceC2382r);
    }

    @Override // p026j$.time.chrono.AbstractC2291d, p026j$.time.chrono.InterfaceC2287b, p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: c */
    public final Temporal mo584c(long j, InterfaceC2382r interfaceC2382r) {
        return (C2288b0) super.mo584c(j, interfaceC2382r);
    }
}
