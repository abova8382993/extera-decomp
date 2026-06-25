package p026j$.time;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Objects;
import kotlin.jvm.internal.LongCompanionObject;
import p026j$.time.temporal.AbstractC2381q;
import p026j$.time.temporal.C2384t;
import p026j$.time.temporal.ChronoUnit;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2377m;
import p026j$.time.temporal.InterfaceC2380p;
import p026j$.time.temporal.InterfaceC2382r;
import p026j$.time.temporal.Temporal;

/* JADX INFO: renamed from: j$.time.q */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2361q implements Temporal, InterfaceC2377m, Comparable, Serializable {

    /* JADX INFO: renamed from: c */
    public static final /* synthetic */ int f929c = 0;
    private static final long serialVersionUID = 7264499704384272492L;

    /* JADX INFO: renamed from: a */
    public final C2354j f930a;

    /* JADX INFO: renamed from: b */
    public final ZoneOffset f931b;

    static {
        C2354j c2354j = C2354j.f912e;
        ZoneOffset zoneOffset = ZoneOffset.f750g;
        c2354j.getClass();
        new C2361q(c2354j, zoneOffset);
        C2354j c2354j2 = C2354j.f913f;
        ZoneOffset zoneOffset2 = ZoneOffset.f749f;
        c2354j2.getClass();
        new C2361q(c2354j2, zoneOffset2);
    }

    public C2361q(C2354j c2354j, ZoneOffset zoneOffset) {
        Objects.requireNonNull(c2354j, "time");
        this.f930a = c2354j;
        Objects.requireNonNull(zoneOffset, "offset");
        this.f931b = zoneOffset;
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new C2362r((byte) 9, this);
    }

    /* JADX INFO: renamed from: B */
    public final long m826B() {
        return this.f930a.m816d0() - (((long) this.f931b.getTotalSeconds()) * 1000000000);
    }

    /* JADX INFO: renamed from: G */
    public final C2361q m827G(C2354j c2354j, ZoneOffset zoneOffset) {
        return (this.f930a == c2354j && this.f931b.equals(zoneOffset)) ? this : new C2361q(c2354j, zoneOffset);
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: a */
    public final Temporal mo582a(long j, InterfaceC2380p interfaceC2380p) {
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return (C2361q) interfaceC2380p.mo838V(this, j);
        }
        EnumC2365a enumC2365a = EnumC2365a.OFFSET_SECONDS;
        C2354j c2354j = this.f930a;
        if (interfaceC2380p != enumC2365a) {
            return m827G(c2354j.mo582a(j, interfaceC2380p), this.f931b);
        }
        EnumC2365a enumC2365a2 = (EnumC2365a) interfaceC2380p;
        return m827G(c2354j, ZoneOffset.m653Z(enumC2365a2.f942b.m849a(j, enumC2365a2)));
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
        C2361q c2361q = (C2361q) obj;
        if (this.f931b.equals(c2361q.f931b)) {
            return this.f930a.compareTo(c2361q.f930a);
        }
        int iCompare = Long.compare(m826B(), c2361q.m826B());
        return iCompare == 0 ? this.f930a.compareTo(c2361q.f930a) : iCompare;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: d */
    public final Object mo568d(C2388x c2388x) {
        if (c2388x == AbstractC2381q.f961d || c2388x == AbstractC2381q.f962e) {
            return this.f931b;
        }
        if (((c2388x == AbstractC2381q.f958a) || (c2388x == AbstractC2381q.f959b)) || c2388x == AbstractC2381q.f963f) {
            return null;
        }
        return c2388x == AbstractC2381q.f964g ? this.f930a : c2388x == AbstractC2381q.f960c ? ChronoUnit.NANOS : c2388x.m855m(this);
    }

    @Override // p026j$.time.temporal.InterfaceC2377m
    /* JADX INFO: renamed from: e */
    public final Temporal mo569e(Temporal temporal) {
        return temporal.mo582a(this.f930a.m816d0(), EnumC2365a.NANO_OF_DAY).mo582a(this.f931b.getTotalSeconds(), EnumC2365a.OFFSET_SECONDS);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof C2361q) {
            C2361q c2361q = (C2361q) obj;
            if (this.f930a.equals(c2361q.f930a) && this.f931b.equals(c2361q.f931b)) {
                return true;
            }
        }
        return false;
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: h */
    public final Temporal mo666j(LocalDate localDate) {
        return (C2361q) localDate.mo569e(this);
    }

    public final int hashCode() {
        return this.f931b.f751b ^ this.f930a.hashCode();
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: i */
    public final boolean mo571i(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p instanceof EnumC2365a ? ((EnumC2365a) interfaceC2380p).m840Y() || interfaceC2380p == EnumC2365a.OFFSET_SECONDS : interfaceC2380p != null && interfaceC2380p.mo841t(this);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: k */
    public final long mo572k(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p instanceof EnumC2365a ? interfaceC2380p == EnumC2365a.OFFSET_SECONDS ? this.f931b.getTotalSeconds() : this.f930a.mo572k(interfaceC2380p) : interfaceC2380p.mo837P(this);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: m */
    public final C2384t mo573m(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p instanceof EnumC2365a ? interfaceC2380p == EnumC2365a.OFFSET_SECONDS ? ((EnumC2365a) interfaceC2380p).f942b : this.f930a.mo573m(interfaceC2380p) : interfaceC2380p.mo835B(this);
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: o */
    public final long mo586o(Temporal temporal, InterfaceC2382r interfaceC2382r) {
        C2361q c2361q;
        if (temporal instanceof C2361q) {
            c2361q = (C2361q) temporal;
        } else {
            try {
                c2361q = new C2361q(C2354j.m806G(temporal), ZoneOffset.m650V(temporal));
            } catch (C2284c e) {
                C2351g.m802g("Unable to obtain OffsetTime from TemporalAccessor: ", temporal, temporal.getClass().getName(), e);
                return 0L;
            }
        }
        if (!(interfaceC2382r instanceof ChronoUnit)) {
            return interfaceC2382r.between(this, c2361q);
        }
        long jM826B = c2361q.m826B() - m826B();
        switch (AbstractC2360p.f928a[((ChronoUnit) interfaceC2382r).ordinal()]) {
            case 1:
                return jM826B;
            case 2:
                return jM826B / 1000;
            case 3:
                return jM826B / 1000000;
            case 4:
                return jM826B / 1000000000;
            case 5:
                return jM826B / 60000000000L;
            case 6:
                return jM826B / 3600000000000L;
            case 7:
                return jM826B / 43200000000000L;
            default:
                C2351g.m799d("Unsupported unit: ", interfaceC2382r);
                return 0L;
        }
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: t, reason: merged with bridge method [inline-methods] */
    public final C2361q mo583b(long j, InterfaceC2382r interfaceC2382r) {
        return interfaceC2382r instanceof ChronoUnit ? m827G(this.f930a.mo583b(j, interfaceC2382r), this.f931b) : (C2361q) interfaceC2382r.mo834t(this, j);
    }

    public final String toString() {
        return this.f930a.toString() + this.f931b.f752c;
    }
}
