package p026j$.time.chrono;

import java.io.Serializable;
import java.util.Objects;
import p026j$.time.AbstractC2320d;
import p026j$.time.C2351g;
import p026j$.time.temporal.C2383s;
import p026j$.time.temporal.ChronoUnit;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2377m;
import p026j$.time.temporal.InterfaceC2379o;
import p026j$.time.temporal.InterfaceC2380p;
import p026j$.time.temporal.InterfaceC2382r;
import p026j$.time.temporal.Temporal;

/* JADX INFO: renamed from: j$.time.chrono.d */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public abstract class AbstractC2291d implements InterfaceC2287b, Temporal, InterfaceC2377m, Serializable {
    private static final long serialVersionUID = 6282433883239719096L;

    /* JADX INFO: renamed from: t */
    public static InterfaceC2287b m693t(InterfaceC2304k interfaceC2304k, Temporal temporal) {
        InterfaceC2287b interfaceC2287b = (InterfaceC2287b) temporal;
        if (interfaceC2304k.equals(interfaceC2287b.mo607f())) {
            return interfaceC2287b;
        }
        C2351g.m800e("Chronology mismatch, expected: ", interfaceC2304k.getId(), interfaceC2287b.mo607f().getId());
        return null;
    }

    /* JADX INFO: renamed from: B */
    public final long m694B(InterfaceC2287b interfaceC2287b) {
        if (mo607f().mo711z(EnumC2365a.MONTH_OF_YEAR).f968d != 12) {
            throw new IllegalStateException("ChronoLocalDateImpl only supports Chronologies with 12 months per year");
        }
        EnumC2365a enumC2365a = EnumC2365a.PROLEPTIC_MONTH;
        long jMo572k = mo572k(enumC2365a) * 32;
        EnumC2365a enumC2365a2 = EnumC2365a.DAY_OF_MONTH;
        return (((interfaceC2287b.mo572k(enumC2365a) * 32) + ((long) interfaceC2287b.mo570g(enumC2365a2))) - (jMo572k + ((long) mo570g(enumC2365a2)))) / 32;
    }

    /* JADX INFO: renamed from: G */
    public abstract InterfaceC2287b mo687G(long j);

    /* JADX INFO: renamed from: I */
    public abstract InterfaceC2287b mo688I(long j);

    /* JADX INFO: renamed from: P */
    public abstract InterfaceC2287b mo689P(long j);

    @Override // p026j$.time.chrono.InterfaceC2287b
    /* JADX INFO: renamed from: R */
    public InterfaceC2287b mo599R(InterfaceC2379o interfaceC2379o) {
        return m693t(mo607f(), interfaceC2379o.mo576t(this));
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: a */
    public InterfaceC2287b mo582a(long j, InterfaceC2380p interfaceC2380p) {
        if (interfaceC2380p instanceof EnumC2365a) {
            throw new C2383s(AbstractC2320d.m741a("Unsupported field: ", interfaceC2380p));
        }
        return m693t(mo607f(), interfaceC2380p.mo838V(this, j));
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: b */
    public InterfaceC2287b mo583b(long j, InterfaceC2382r interfaceC2382r) {
        boolean z = interfaceC2382r instanceof ChronoUnit;
        if (!z) {
            if (!z) {
                return m693t(mo607f(), interfaceC2382r.mo834t(this, j));
            }
            C2351g.m799d("Unsupported unit: ", interfaceC2382r);
            return null;
        }
        switch (AbstractC2289c.f762a[((ChronoUnit) interfaceC2382r).ordinal()]) {
            case 1:
                return mo687G(j);
            case 2:
                return mo687G(Math.multiplyExact(j, 7L));
            case 3:
                return mo688I(j);
            case 4:
                return mo689P(j);
            case 5:
                return mo689P(Math.multiplyExact(j, 10L));
            case 6:
                return mo689P(Math.multiplyExact(j, 100L));
            case 7:
                return mo689P(Math.multiplyExact(j, 1000L));
            case 8:
                EnumC2365a enumC2365a = EnumC2365a.ERA;
                return mo582a(Math.addExact(mo572k(enumC2365a), j), (InterfaceC2380p) enumC2365a);
            default:
                C2351g.m799d("Unsupported unit: ", interfaceC2382r);
                return null;
        }
    }

    @Override // p026j$.time.chrono.InterfaceC2287b, p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: c */
    public /* bridge */ /* synthetic */ Temporal mo584c(long j, InterfaceC2382r interfaceC2382r) {
        return mo584c(j, interfaceC2382r);
    }

    @Override // p026j$.time.chrono.InterfaceC2287b
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof InterfaceC2287b) && compareTo((InterfaceC2287b) obj) == 0;
    }

    @Override // p026j$.time.chrono.InterfaceC2287b
    public int hashCode() {
        long jMo595J = mo595J();
        return mo607f().hashCode() ^ ((int) (jMo595J ^ (jMo595J >>> 32)));
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: j, reason: merged with bridge method [inline-methods] */
    public InterfaceC2287b mo666j(InterfaceC2377m interfaceC2377m) {
        return m693t(mo607f(), interfaceC2377m.mo569e(this));
    }

    @Override // p026j$.time.chrono.InterfaceC2287b, p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: o */
    public final long mo586o(Temporal temporal, InterfaceC2382r interfaceC2382r) {
        Objects.requireNonNull(temporal, "endExclusive");
        InterfaceC2287b interfaceC2287bMo704H = mo607f().mo704H(temporal);
        if (!(interfaceC2382r instanceof ChronoUnit)) {
            Objects.requireNonNull(interfaceC2382r, "unit");
            return interfaceC2382r.between(this, interfaceC2287bMo704H);
        }
        switch (AbstractC2289c.f762a[((ChronoUnit) interfaceC2382r).ordinal()]) {
            case 1:
                return interfaceC2287bMo704H.mo595J() - mo595J();
            case 2:
                return (interfaceC2287bMo704H.mo595J() - mo595J()) / 7;
            case 3:
                return m694B(interfaceC2287bMo704H);
            case 4:
                return m694B(interfaceC2287bMo704H) / 12;
            case 5:
                return m694B(interfaceC2287bMo704H) / 120;
            case 6:
                return m694B(interfaceC2287bMo704H) / 1200;
            case 7:
                return m694B(interfaceC2287bMo704H) / 12000;
            case 8:
                EnumC2365a enumC2365a = EnumC2365a.ERA;
                return interfaceC2287bMo704H.mo572k(enumC2365a) - mo572k(enumC2365a);
            default:
                C2351g.m799d("Unsupported unit: ", interfaceC2382r);
                return 0L;
        }
    }

    @Override // p026j$.time.chrono.InterfaceC2287b
    public final String toString() {
        long jMo572k = mo572k(EnumC2365a.YEAR_OF_ERA);
        long jMo572k2 = mo572k(EnumC2365a.MONTH_OF_YEAR);
        long jMo572k3 = mo572k(EnumC2365a.DAY_OF_MONTH);
        StringBuilder sb = new StringBuilder(30);
        sb.append(mo607f().toString());
        sb.append(" ");
        sb.append(mo597N());
        sb.append(" ");
        sb.append(jMo572k);
        sb.append(jMo572k2 < 10 ? "-0" : "-");
        sb.append(jMo572k2);
        sb.append(jMo572k3 < 10 ? "-0" : "-");
        sb.append(jMo572k3);
        return sb.toString();
    }
}
