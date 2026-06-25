package p026j$.time.chrono;

import java.util.Objects;
import p026j$.time.C2354j;
import p026j$.time.C2388x;
import p026j$.time.Instant;
import p026j$.time.ZoneId;
import p026j$.time.ZoneOffset;
import p026j$.time.chrono.InterfaceC2287b;
import p026j$.time.temporal.AbstractC2381q;
import p026j$.time.temporal.ChronoUnit;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2377m;
import p026j$.time.temporal.InterfaceC2380p;
import p026j$.time.temporal.InterfaceC2382r;
import p026j$.time.temporal.Temporal;

/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public interface ChronoLocalDateTime<D extends InterfaceC2287b> extends Temporal, InterfaceC2377m, Comparable<ChronoLocalDateTime<?>> {
    /* JADX INFO: renamed from: F */
    ChronoZonedDateTime mo620F(ZoneId zoneId);

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: O */
    default int compareTo(ChronoLocalDateTime chronoLocalDateTime) {
        int iCompareTo = mo631n().compareTo(chronoLocalDateTime.mo631n());
        return (iCompareTo == 0 && (iCompareTo = mo630l().compareTo(chronoLocalDateTime.mo630l())) == 0) ? ((AbstractC2285a) m676f()).getId().compareTo(chronoLocalDateTime.m676f().getId()) : iCompareTo;
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: a */
    ChronoLocalDateTime mo582a(long j, InterfaceC2380p interfaceC2380p);

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: b */
    ChronoLocalDateTime mo583b(long j, InterfaceC2382r interfaceC2382r);

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: c */
    default ChronoLocalDateTime mo584c(long j, InterfaceC2382r interfaceC2382r) {
        return C2295f.m695t(m676f(), super.mo584c(j, interfaceC2382r));
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: d */
    default Object mo568d(C2388x c2388x) {
        if (c2388x == AbstractC2381q.f958a || c2388x == AbstractC2381q.f962e || c2388x == AbstractC2381q.f961d) {
            return null;
        }
        return c2388x == AbstractC2381q.f964g ? mo630l() : c2388x == AbstractC2381q.f959b ? m676f() : c2388x == AbstractC2381q.f960c ? ChronoUnit.NANOS : c2388x.m855m(this);
    }

    @Override // p026j$.time.temporal.InterfaceC2377m
    /* JADX INFO: renamed from: e */
    default Temporal mo569e(Temporal temporal) {
        return temporal.mo582a(mo631n().mo595J(), EnumC2365a.EPOCH_DAY).mo582a(mo630l().m816d0(), EnumC2365a.NANO_OF_DAY);
    }

    /* JADX INFO: renamed from: f */
    default InterfaceC2304k m676f() {
        return mo631n().mo607f();
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: j */
    default ChronoLocalDateTime mo666j(InterfaceC2377m interfaceC2377m) {
        return C2295f.m695t(m676f(), interfaceC2377m.mo569e(this));
    }

    /* JADX INFO: renamed from: l */
    C2354j mo630l();

    /* JADX INFO: renamed from: n */
    InterfaceC2287b mo631n();

    default long toEpochSecond(ZoneOffset zoneOffset) {
        Objects.requireNonNull(zoneOffset, "offset");
        return ((mo631n().mo595J() * 86400) + ((long) mo630l().m817e0())) - ((long) zoneOffset.getTotalSeconds());
    }

    default Instant toInstant(ZoneOffset zoneOffset) {
        return Instant.ofEpochSecond(toEpochSecond(zoneOffset), mo630l().f919d);
    }
}
