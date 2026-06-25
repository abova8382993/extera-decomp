package p026j$.time.chrono;

import p026j$.time.C2354j;
import p026j$.time.C2388x;
import p026j$.time.Instant;
import p026j$.time.ZoneId;
import p026j$.time.ZoneOffset;
import p026j$.time.chrono.InterfaceC2287b;
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
public interface ChronoZonedDateTime<D extends InterfaceC2287b> extends Temporal, Comparable<ChronoZonedDateTime<?>> {
    /* JADX INFO: renamed from: E */
    ChronoZonedDateTime mo660E(ZoneId zoneId);

    /* JADX INFO: renamed from: W */
    default long m677W() {
        return ((mo668n().mo595J() * 86400) + ((long) mo667l().m817e0())) - ((long) mo669p().getTotalSeconds());
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: a */
    ChronoZonedDateTime mo582a(long j, InterfaceC2380p interfaceC2380p);

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: b */
    ChronoZonedDateTime mo583b(long j, InterfaceC2382r interfaceC2382r);

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: c */
    default ChronoZonedDateTime mo584c(long j, InterfaceC2382r interfaceC2382r) {
        return C2303j.m717t(m678f(), super.mo584c(j, interfaceC2382r));
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: d */
    default Object mo568d(C2388x c2388x) {
        return (c2388x == AbstractC2381q.f962e || c2388x == AbstractC2381q.f958a) ? getZone() : c2388x == AbstractC2381q.f961d ? mo669p() : c2388x == AbstractC2381q.f964g ? mo667l() : c2388x == AbstractC2381q.f959b ? m678f() : c2388x == AbstractC2381q.f960c ? ChronoUnit.NANOS : c2388x.m855m(this);
    }

    /* JADX INFO: renamed from: f */
    default InterfaceC2304k m678f() {
        return mo668n().mo607f();
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: g */
    default int mo570g(InterfaceC2380p interfaceC2380p) {
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return super.mo570g(interfaceC2380p);
        }
        int i = AbstractC2299h.f777a[((EnumC2365a) interfaceC2380p).ordinal()];
        if (i != 1) {
            return i != 2 ? mo671y().mo570g(interfaceC2380p) : mo669p().getTotalSeconds();
        }
        throw new C2383s("Invalid field 'InstantSeconds' for get() method, use getLong() instead");
    }

    ZoneId getZone();

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: j, reason: merged with bridge method [inline-methods] */
    default ChronoZonedDateTime mo666j(InterfaceC2377m interfaceC2377m) {
        return C2303j.m717t(m678f(), interfaceC2377m.mo569e(this));
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: k */
    default long mo572k(InterfaceC2380p interfaceC2380p) {
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return interfaceC2380p.mo837P(this);
        }
        int i = AbstractC2299h.f777a[((EnumC2365a) interfaceC2380p).ordinal()];
        return i != 1 ? i != 2 ? mo671y().mo572k(interfaceC2380p) : mo669p().getTotalSeconds() : m677W();
    }

    /* JADX INFO: renamed from: l */
    default C2354j mo667l() {
        return mo671y().mo630l();
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: m */
    default C2384t mo573m(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p instanceof EnumC2365a ? (interfaceC2380p == EnumC2365a.INSTANT_SECONDS || interfaceC2380p == EnumC2365a.OFFSET_SECONDS) ? ((EnumC2365a) interfaceC2380p).f942b : mo671y().mo573m(interfaceC2380p) : interfaceC2380p.mo835B(this);
    }

    /* JADX INFO: renamed from: n */
    default InterfaceC2287b mo668n() {
        return mo671y().mo631n();
    }

    /* JADX INFO: renamed from: p */
    ZoneOffset mo669p();

    /* JADX INFO: renamed from: q */
    ChronoZonedDateTime mo670q(ZoneId zoneId);

    default Instant toInstant() {
        return Instant.ofEpochSecond(m677W(), mo667l().f919d);
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: u */
    default int compareTo(ChronoZonedDateTime chronoZonedDateTime) {
        int iCompare = Long.compare(m677W(), chronoZonedDateTime.m677W());
        return (iCompare == 0 && (iCompare = mo667l().f919d - chronoZonedDateTime.mo667l().f919d) == 0 && (iCompare = mo671y().compareTo(chronoZonedDateTime.mo671y())) == 0 && (iCompare = getZone().getId().compareTo(chronoZonedDateTime.getZone().getId())) == 0) ? ((AbstractC2285a) m678f()).getId().compareTo(chronoZonedDateTime.m678f().getId()) : iCompare;
    }

    /* JADX INFO: renamed from: y */
    ChronoLocalDateTime mo671y();
}
