package p026j$.time.chrono;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Objects;
import kotlin.time.DurationKt;
import p026j$.time.C2351g;
import p026j$.time.C2354j;
import p026j$.time.ZoneId;
import p026j$.time.temporal.C2384t;
import p026j$.time.temporal.ChronoUnit;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2377m;
import p026j$.time.temporal.InterfaceC2380p;
import p026j$.time.temporal.InterfaceC2382r;
import p026j$.time.temporal.Temporal;

/* JADX INFO: renamed from: j$.time.chrono.f */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2295f implements ChronoLocalDateTime, Temporal, InterfaceC2377m, Serializable {
    private static final long serialVersionUID = 4556003607393004514L;

    /* JADX INFO: renamed from: a */
    public final transient InterfaceC2287b f768a;

    /* JADX INFO: renamed from: b */
    public final transient C2354j f769b;

    public C2295f(InterfaceC2287b interfaceC2287b, C2354j c2354j) {
        Objects.requireNonNull(c2354j, "time");
        this.f768a = interfaceC2287b;
        this.f769b = c2354j;
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    /* JADX INFO: renamed from: t */
    public static C2295f m695t(InterfaceC2304k interfaceC2304k, Temporal temporal) {
        C2295f c2295f = (C2295f) temporal;
        if (interfaceC2304k.equals(c2295f.m676f())) {
            return c2295f;
        }
        C2351g.m800e("Chronology mismatch, required: ", interfaceC2304k.getId(), c2295f.m676f().getId());
        return null;
    }

    private Object writeReplace() {
        return new C2292d0((byte) 2, this);
    }

    @Override // p026j$.time.chrono.ChronoLocalDateTime, p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: B */
    public final C2295f mo583b(long j, InterfaceC2382r interfaceC2382r) {
        if (!(interfaceC2382r instanceof ChronoUnit)) {
            return m695t(this.f768a.mo607f(), interfaceC2382r.mo834t(this, j));
        }
        switch (AbstractC2293e.f766a[((ChronoUnit) interfaceC2382r).ordinal()]) {
            case 1:
                return m697G(this.f768a, 0L, 0L, 0L, j);
            case 2:
                C2295f c2295fM699P = m699P(this.f768a.mo583b(j / 86400000000L, (InterfaceC2382r) ChronoUnit.DAYS), this.f769b);
                return c2295fM699P.m697G(c2295fM699P.f768a, 0L, 0L, 0L, (j % 86400000000L) * 1000);
            case 3:
                C2295f c2295fM699P2 = m699P(this.f768a.mo583b(j / DurationKt.MILLIS_IN_DAY, (InterfaceC2382r) ChronoUnit.DAYS), this.f769b);
                return c2295fM699P2.m697G(c2295fM699P2.f768a, 0L, 0L, 0L, (j % DurationKt.MILLIS_IN_DAY) * 1000000);
            case 4:
                return m697G(this.f768a, 0L, 0L, j, 0L);
            case 5:
                return m697G(this.f768a, 0L, j, 0L, 0L);
            case 6:
                return m697G(this.f768a, j, 0L, 0L, 0L);
            case 7:
                C2295f c2295fM699P3 = m699P(this.f768a.mo583b(j / 256, (InterfaceC2382r) ChronoUnit.DAYS), this.f769b);
                return c2295fM699P3.m697G(c2295fM699P3.f768a, (j % 256) * 12, 0L, 0L, 0L);
            default:
                return m699P(this.f768a.mo583b(j, interfaceC2382r), this.f769b);
        }
    }

    @Override // p026j$.time.chrono.ChronoLocalDateTime
    /* JADX INFO: renamed from: F */
    public final ChronoZonedDateTime mo620F(ZoneId zoneId) {
        return C2303j.m715B(zoneId, null, this);
    }

    /* JADX INFO: renamed from: G */
    public final C2295f m697G(InterfaceC2287b interfaceC2287b, long j, long j2, long j3, long j4) {
        long j5 = j | j2 | j3 | j4;
        C2354j c2354j = this.f769b;
        if (j5 == 0) {
            return m699P(interfaceC2287b, c2354j);
        }
        long j6 = j / 24;
        long jM816d0 = c2354j.m816d0();
        long j7 = ((j % 24) * 3600000000000L) + ((j2 % 1440) * 60000000000L) + ((j3 % 86400) * 1000000000) + (j4 % 86400000000000L) + jM816d0;
        long jFloorDiv = Math.floorDiv(j7, 86400000000000L) + j6 + (j2 / 1440) + (j3 / 86400) + (j4 / 86400000000000L);
        long jFloorMod = Math.floorMod(j7, 86400000000000L);
        return m699P(interfaceC2287b.mo583b(jFloorDiv, (InterfaceC2382r) ChronoUnit.DAYS), jFloorMod == jM816d0 ? this.f769b : C2354j.m808V(jFloorMod));
    }

    @Override // p026j$.time.chrono.ChronoLocalDateTime, p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: I */
    public final C2295f mo582a(long j, InterfaceC2380p interfaceC2380p) {
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return m695t(this.f768a.mo607f(), interfaceC2380p.mo838V(this, j));
        }
        boolean zM840Y = ((EnumC2365a) interfaceC2380p).m840Y();
        InterfaceC2287b interfaceC2287b = this.f768a;
        return zM840Y ? m699P(interfaceC2287b, this.f769b.mo582a(j, interfaceC2380p)) : m699P(interfaceC2287b.mo582a(j, interfaceC2380p), this.f769b);
    }

    /* JADX INFO: renamed from: P */
    public final C2295f m699P(Temporal temporal, C2354j c2354j) {
        InterfaceC2287b interfaceC2287b = this.f768a;
        return (interfaceC2287b == temporal && this.f769b == c2354j) ? this : new C2295f(AbstractC2291d.m693t(interfaceC2287b.mo607f(), temporal), c2354j);
    }

    @Override // p026j$.time.chrono.ChronoLocalDateTime
    /* JADX INFO: renamed from: V, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public final C2295f mo666j(InterfaceC2377m interfaceC2377m) {
        if (interfaceC2377m instanceof InterfaceC2287b) {
            return m699P((InterfaceC2287b) interfaceC2377m, this.f769b);
        }
        boolean z = interfaceC2377m instanceof C2354j;
        InterfaceC2287b interfaceC2287b = this.f768a;
        return z ? m699P(interfaceC2287b, (C2354j) interfaceC2377m) : interfaceC2377m instanceof C2295f ? m695t(interfaceC2287b.mo607f(), (C2295f) interfaceC2377m) : m695t(interfaceC2287b.mo607f(), (C2295f) interfaceC2377m.mo569e(this));
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ChronoLocalDateTime) && compareTo((ChronoLocalDateTime) obj) == 0;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: g */
    public final int mo570g(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p instanceof EnumC2365a ? ((EnumC2365a) interfaceC2380p).m840Y() ? this.f769b.mo570g(interfaceC2380p) : this.f768a.mo570g(interfaceC2380p) : mo573m(interfaceC2380p).m849a(mo572k(interfaceC2380p), interfaceC2380p);
    }

    public final int hashCode() {
        return this.f769b.hashCode() ^ this.f768a.hashCode();
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: i */
    public final boolean mo571i(InterfaceC2380p interfaceC2380p) {
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return interfaceC2380p != null && interfaceC2380p.mo841t(this);
        }
        EnumC2365a enumC2365a = (EnumC2365a) interfaceC2380p;
        return enumC2365a.isDateBased() || enumC2365a.m840Y();
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: k */
    public final long mo572k(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p instanceof EnumC2365a ? ((EnumC2365a) interfaceC2380p).m840Y() ? this.f769b.mo572k(interfaceC2380p) : this.f768a.mo572k(interfaceC2380p) : interfaceC2380p.mo837P(this);
    }

    @Override // p026j$.time.chrono.ChronoLocalDateTime
    /* JADX INFO: renamed from: l */
    public final C2354j mo630l() {
        return this.f769b;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: m */
    public final C2384t mo573m(InterfaceC2380p interfaceC2380p) {
        if (interfaceC2380p instanceof EnumC2365a) {
            return (((EnumC2365a) interfaceC2380p).m840Y() ? this.f769b : this.f768a).mo573m(interfaceC2380p);
        }
        return interfaceC2380p.mo835B(this);
    }

    @Override // p026j$.time.chrono.ChronoLocalDateTime
    /* JADX INFO: renamed from: n */
    public final InterfaceC2287b mo631n() {
        return this.f768a;
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: o */
    public final long mo586o(Temporal temporal, InterfaceC2382r interfaceC2382r) {
        Objects.requireNonNull(temporal, "endExclusive");
        ChronoLocalDateTime chronoLocalDateTimeMo720M = m676f().mo720M(temporal);
        if (!(interfaceC2382r instanceof ChronoUnit)) {
            Objects.requireNonNull(interfaceC2382r, "unit");
            return interfaceC2382r.between(this, chronoLocalDateTimeMo720M);
        }
        ChronoUnit chronoUnit = (ChronoUnit) interfaceC2382r;
        ChronoUnit chronoUnit2 = ChronoUnit.DAYS;
        if (chronoUnit.compareTo(chronoUnit2) >= 0) {
            InterfaceC2287b interfaceC2287bMo631n = chronoLocalDateTimeMo720M.mo631n();
            if (chronoLocalDateTimeMo720M.mo630l().compareTo(this.f769b) < 0) {
                interfaceC2287bMo631n = interfaceC2287bMo631n.mo584c(1L, (InterfaceC2382r) chronoUnit2);
            }
            return this.f768a.mo586o(interfaceC2287bMo631n, interfaceC2382r);
        }
        EnumC2365a enumC2365a = EnumC2365a.EPOCH_DAY;
        long jMo572k = chronoLocalDateTimeMo720M.mo572k(enumC2365a) - this.f768a.mo572k(enumC2365a);
        switch (AbstractC2293e.f766a[chronoUnit.ordinal()]) {
            case 1:
                jMo572k = Math.multiplyExact(jMo572k, 86400000000000L);
                break;
            case 2:
                jMo572k = Math.multiplyExact(jMo572k, 86400000000L);
                break;
            case 3:
                jMo572k = Math.multiplyExact(jMo572k, DurationKt.MILLIS_IN_DAY);
                break;
            case 4:
                jMo572k = Math.multiplyExact(jMo572k, 86400L);
                break;
            case 5:
                jMo572k = Math.multiplyExact(jMo572k, 1440L);
                break;
            case 6:
                jMo572k = Math.multiplyExact(jMo572k, 24L);
                break;
            case 7:
                jMo572k = Math.multiplyExact(jMo572k, 2L);
                break;
        }
        return Math.addExact(jMo572k, this.f769b.mo586o(chronoLocalDateTimeMo720M.mo630l(), interfaceC2382r));
    }

    public final String toString() {
        return this.f768a.toString() + "T" + this.f769b.toString();
    }
}
