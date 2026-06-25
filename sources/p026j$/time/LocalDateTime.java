package p026j$.time;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Objects;
import kotlin.jvm.internal.LongCompanionObject;
import kotlin.time.DurationKt;
import p026j$.time.chrono.ChronoLocalDateTime;
import p026j$.time.chrono.ChronoZonedDateTime;
import p026j$.time.chrono.InterfaceC2287b;
import p026j$.time.format.DateTimeFormatter;
import p026j$.time.temporal.AbstractC2381q;
import p026j$.time.temporal.C2384t;
import p026j$.time.temporal.ChronoUnit;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2376l;
import p026j$.time.temporal.InterfaceC2377m;
import p026j$.time.temporal.InterfaceC2380p;
import p026j$.time.temporal.InterfaceC2382r;
import p026j$.time.temporal.Temporal;

/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class LocalDateTime implements Temporal, InterfaceC2377m, ChronoLocalDateTime<LocalDate>, Serializable {

    /* JADX INFO: renamed from: c */
    public static final LocalDateTime f732c = m618I(LocalDate.f727d, C2354j.f912e);

    /* JADX INFO: renamed from: d */
    public static final LocalDateTime f733d = m618I(LocalDate.f728e, C2354j.f913f);
    private static final long serialVersionUID = 6207766400415563566L;

    /* JADX INFO: renamed from: a */
    public final LocalDate f734a;

    /* JADX INFO: renamed from: b */
    public final C2354j f735b;

    public LocalDateTime(LocalDate localDate, C2354j c2354j) {
        this.f734a = localDate;
        this.f735b = c2354j;
    }

    /* JADX INFO: renamed from: B */
    public static LocalDateTime m617B(InterfaceC2376l interfaceC2376l) {
        if (interfaceC2376l instanceof LocalDateTime) {
            return (LocalDateTime) interfaceC2376l;
        }
        if (interfaceC2376l instanceof ZonedDateTime) {
            return ((ZonedDateTime) interfaceC2376l).mo671y();
        }
        if (interfaceC2376l instanceof OffsetDateTime) {
            return ((OffsetDateTime) interfaceC2376l).toLocalDateTime();
        }
        try {
            return new LocalDateTime(LocalDate.m588G(interfaceC2376l), C2354j.m806G(interfaceC2376l));
        } catch (C2284c e) {
            C2351g.m802g("Unable to obtain LocalDateTime from TemporalAccessor: ", interfaceC2376l, interfaceC2376l.getClass().getName(), e);
            return null;
        }
    }

    /* JADX INFO: renamed from: I */
    public static LocalDateTime m618I(LocalDate localDate, C2354j c2354j) {
        Objects.requireNonNull(localDate, "date");
        Objects.requireNonNull(c2354j, "time");
        return new LocalDateTime(localDate, c2354j);
    }

    /* JADX INFO: renamed from: P */
    public static LocalDateTime m619P(long j, int i, ZoneOffset zoneOffset) {
        Objects.requireNonNull(zoneOffset, "offset");
        long j2 = i;
        EnumC2365a.NANO_OF_SECOND.m839X(j2);
        long totalSeconds = j + ((long) zoneOffset.getTotalSeconds());
        return new LocalDateTime(LocalDate.m590d0(Math.floorDiv(totalSeconds, 86400L)), C2354j.m808V((((long) ((int) Math.floorMod(totalSeconds, 86400L))) * 1000000000) + j2));
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new C2362r((byte) 5, this);
    }

    @Override // p026j$.time.chrono.ChronoLocalDateTime
    /* JADX INFO: renamed from: F */
    public final ChronoZonedDateTime mo620F(ZoneId zoneId) {
        return ZonedDateTime.m658G(this, zoneId, null);
    }

    /* JADX INFO: renamed from: G */
    public final boolean m621G(ChronoLocalDateTime chronoLocalDateTime) {
        if (chronoLocalDateTime instanceof LocalDateTime) {
            return m632t((LocalDateTime) chronoLocalDateTime) < 0;
        }
        long jMo595J = this.f734a.mo595J();
        long jMo595J2 = chronoLocalDateTime.mo631n().mo595J();
        if (jMo595J >= jMo595J2) {
            return jMo595J == jMo595J2 && this.f735b.m816d0() < chronoLocalDateTime.mo630l().m816d0();
        }
        return true;
    }

    @Override // p026j$.time.chrono.ChronoLocalDateTime, java.lang.Comparable
    /* JADX INFO: renamed from: O */
    public final int compareTo(ChronoLocalDateTime chronoLocalDateTime) {
        return chronoLocalDateTime instanceof LocalDateTime ? m632t((LocalDateTime) chronoLocalDateTime) : super.compareTo(chronoLocalDateTime);
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: V */
    public final LocalDateTime mo583b(long j, InterfaceC2382r interfaceC2382r) {
        if (!(interfaceC2382r instanceof ChronoUnit)) {
            return (LocalDateTime) interfaceC2382r.mo834t(this, j);
        }
        switch (AbstractC2352h.f909a[((ChronoUnit) interfaceC2382r).ordinal()]) {
            case 1:
                return m625Y(this.f734a, 0L, 0L, 0L, j);
            case 2:
                LocalDateTime localDateTimeM627a0 = m627a0(this.f734a.plusDays(j / 86400000000L), this.f735b);
                return localDateTimeM627a0.m625Y(localDateTimeM627a0.f734a, 0L, 0L, 0L, (j % 86400000000L) * 1000);
            case 3:
                LocalDateTime localDateTimeM627a02 = m627a0(this.f734a.plusDays(j / DurationKt.MILLIS_IN_DAY), this.f735b);
                return localDateTimeM627a02.m625Y(localDateTimeM627a02.f734a, 0L, 0L, 0L, (j % DurationKt.MILLIS_IN_DAY) * 1000000);
            case 4:
                return m624X(j);
            case 5:
                return m625Y(this.f734a, 0L, j, 0L, 0L);
            case 6:
                return m625Y(this.f734a, j, 0L, 0L, 0L);
            case 7:
                LocalDateTime localDateTimeM627a03 = m627a0(this.f734a.plusDays(j / 256), this.f735b);
                return localDateTimeM627a03.m625Y(localDateTimeM627a03.f734a, (j % 256) * 12, 0L, 0L, 0L);
            default:
                return m627a0(this.f734a.mo583b(j, interfaceC2382r), this.f735b);
        }
    }

    /* JADX INFO: renamed from: X */
    public final LocalDateTime m624X(long j) {
        return m625Y(this.f734a, 0L, 0L, j, 0L);
    }

    /* JADX INFO: renamed from: Y */
    public final LocalDateTime m625Y(LocalDate localDate, long j, long j2, long j3, long j4) {
        long j5 = j | j2 | j3 | j4;
        C2354j c2354j = this.f735b;
        if (j5 == 0) {
            return m627a0(localDate, c2354j);
        }
        long j6 = j / 24;
        long jM816d0 = c2354j.m816d0();
        long j7 = ((j % 24) * 3600000000000L) + ((j2 % 1440) * 60000000000L) + ((j3 % 86400) * 1000000000) + (j4 % 86400000000000L) + jM816d0;
        long jFloorDiv = Math.floorDiv(j7, 86400000000000L) + j6 + (j2 / 1440) + (j3 / 86400) + (j4 / 86400000000000L);
        long jFloorMod = Math.floorMod(j7, 86400000000000L);
        return m627a0(localDate.plusDays(jFloorDiv), jFloorMod == jM816d0 ? this.f735b : C2354j.m808V(jFloorMod));
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: Z */
    public final LocalDateTime mo582a(long j, InterfaceC2380p interfaceC2380p) {
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return (LocalDateTime) interfaceC2380p.mo838V(this, j);
        }
        boolean zM840Y = ((EnumC2365a) interfaceC2380p).m840Y();
        LocalDate localDate = this.f734a;
        return zM840Y ? m627a0(localDate, this.f735b.mo582a(j, interfaceC2380p)) : m627a0(localDate.mo582a(j, interfaceC2380p), this.f735b);
    }

    /* JADX INFO: renamed from: a0 */
    public final LocalDateTime m627a0(LocalDate localDate, C2354j c2354j) {
        return (this.f734a == localDate && this.f735b == c2354j) ? this : new LocalDateTime(localDate, c2354j);
    }

    @Override // p026j$.time.chrono.ChronoLocalDateTime
    /* JADX INFO: renamed from: b0, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public final LocalDateTime mo666j(InterfaceC2377m interfaceC2377m) {
        return interfaceC2377m instanceof LocalDate ? m627a0((LocalDate) interfaceC2377m, this.f735b) : interfaceC2377m instanceof C2354j ? m627a0(this.f734a, (C2354j) interfaceC2377m) : interfaceC2377m instanceof LocalDateTime ? (LocalDateTime) interfaceC2377m : (LocalDateTime) interfaceC2377m.mo569e(this);
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: c */
    public final ChronoLocalDateTime mo584c(long j, InterfaceC2382r interfaceC2382r) {
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
        return c2388x == AbstractC2381q.f963f ? this.f734a : super.mo568d(c2388x);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof LocalDateTime) {
            LocalDateTime localDateTime = (LocalDateTime) obj;
            if (this.f734a.equals(localDateTime.f734a) && this.f735b.equals(localDateTime.f735b)) {
                return true;
            }
        }
        return false;
    }

    public String format(DateTimeFormatter dateTimeFormatter) {
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        return dateTimeFormatter.m742a(this);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: g */
    public final int mo570g(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p instanceof EnumC2365a ? ((EnumC2365a) interfaceC2380p).m840Y() ? this.f735b.mo570g(interfaceC2380p) : this.f734a.mo570g(interfaceC2380p) : super.mo570g(interfaceC2380p);
    }

    public final int hashCode() {
        return this.f735b.hashCode() ^ this.f734a.hashCode();
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
        return interfaceC2380p instanceof EnumC2365a ? ((EnumC2365a) interfaceC2380p).m840Y() ? this.f735b.mo572k(interfaceC2380p) : this.f734a.mo572k(interfaceC2380p) : interfaceC2380p.mo837P(this);
    }

    @Override // p026j$.time.chrono.ChronoLocalDateTime
    /* JADX INFO: renamed from: l */
    public final C2354j mo630l() {
        return this.f735b;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: m */
    public final C2384t mo573m(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p instanceof EnumC2365a ? ((EnumC2365a) interfaceC2380p).m840Y() ? this.f735b.mo573m(interfaceC2380p) : this.f734a.mo573m(interfaceC2380p) : interfaceC2380p.mo835B(this);
    }

    @Override // p026j$.time.chrono.ChronoLocalDateTime
    /* JADX INFO: renamed from: n */
    public final InterfaceC2287b mo631n() {
        return this.f734a;
    }

    /* JADX WARN: Removed duplicated region for block: B:77:0x00bf  */
    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: o */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final long mo586o(p026j$.time.temporal.Temporal r11, p026j$.time.temporal.InterfaceC2382r r12) {
        /*
            Method dump skipped, instruction units count: 278
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: p026j$.time.LocalDateTime.mo586o(j$.time.temporal.Temporal, j$.time.temporal.r):long");
    }

    /* JADX INFO: renamed from: t */
    public final int m632t(LocalDateTime localDateTime) {
        int iM616t = this.f734a.m616t(localDateTime.f734a);
        return iM616t == 0 ? this.f735b.compareTo(localDateTime.f735b) : iM616t;
    }

    public final String toString() {
        return this.f734a.toString() + "T" + this.f735b.toString();
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
}
