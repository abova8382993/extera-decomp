package p026j$.time;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import kotlin.jvm.internal.LongCompanionObject;
import p026j$.time.chrono.ChronoZonedDateTime;
import p026j$.time.format.DateTimeFormatter;
import p026j$.time.format.DateTimeParseException;
import p026j$.time.temporal.AbstractC2381q;
import p026j$.time.temporal.C2383s;
import p026j$.time.temporal.C2384t;
import p026j$.time.temporal.ChronoUnit;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2376l;
import p026j$.time.temporal.InterfaceC2377m;
import p026j$.time.temporal.InterfaceC2380p;
import p026j$.time.temporal.InterfaceC2382r;
import p026j$.time.temporal.Temporal;
import p026j$.time.zone.C2391b;
import p026j$.time.zone.ZoneRules;

/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class ZonedDateTime implements Temporal, ChronoZonedDateTime<LocalDate>, Serializable {
    private static final long serialVersionUID = -6260982410461394882L;

    /* JADX INFO: renamed from: a */
    public final LocalDateTime f753a;

    /* JADX INFO: renamed from: b */
    public final ZoneOffset f754b;

    /* JADX INFO: renamed from: c */
    public final ZoneId f755c;

    public ZonedDateTime(LocalDateTime localDateTime, ZoneId zoneId, ZoneOffset zoneOffset) {
        this.f753a = localDateTime;
        this.f754b = zoneOffset;
        this.f755c = zoneId;
    }

    /* JADX INFO: renamed from: B */
    public static ZonedDateTime m657B(InterfaceC2376l interfaceC2376l) {
        if (interfaceC2376l instanceof ZonedDateTime) {
            return (ZonedDateTime) interfaceC2376l;
        }
        try {
            ZoneId zoneIdM648t = ZoneId.m648t(interfaceC2376l);
            EnumC2365a enumC2365a = EnumC2365a.INSTANT_SECONDS;
            return interfaceC2376l.mo571i(enumC2365a) ? m659t(interfaceC2376l.mo572k(enumC2365a), interfaceC2376l.mo570g(EnumC2365a.NANO_OF_SECOND), zoneIdM648t) : m658G(LocalDateTime.m618I(LocalDate.m588G(interfaceC2376l), C2354j.m806G(interfaceC2376l)), zoneIdM648t, null);
        } catch (C2284c e) {
            C2351g.m802g("Unable to obtain ZonedDateTime from TemporalAccessor: ", interfaceC2376l, interfaceC2376l.getClass().getName(), e);
            return null;
        }
    }

    /* JADX INFO: renamed from: G */
    public static ZonedDateTime m658G(LocalDateTime localDateTime, ZoneId zoneId, ZoneOffset zoneOffset) {
        Objects.requireNonNull(localDateTime, "localDateTime");
        Objects.requireNonNull(zoneId, "zone");
        if (zoneId instanceof ZoneOffset) {
            return new ZonedDateTime(localDateTime, zoneId, (ZoneOffset) zoneId);
        }
        ZoneRules rules = zoneId.getRules();
        List listM862f = rules.m862f(localDateTime);
        if (listM862f.size() == 1) {
            zoneOffset = (ZoneOffset) listM862f.get(0);
        } else if (listM862f.size() == 0) {
            C2391b c2391bM861e = rules.m861e(localDateTime);
            localDateTime = localDateTime.m624X(Duration.ofSeconds(c2391bM861e.f995d.getTotalSeconds() - c2391bM861e.f994c.getTotalSeconds()).getSeconds());
            zoneOffset = c2391bM861e.f995d;
        } else if (zoneOffset == null || !listM862f.contains(zoneOffset)) {
            zoneOffset = (ZoneOffset) listM862f.get(0);
            Objects.requireNonNull(zoneOffset, "offset");
        }
        return new ZonedDateTime(localDateTime, zoneId, zoneOffset);
    }

    public static ZonedDateTime parse(CharSequence charSequence, DateTimeFormatter dateTimeFormatter) {
        String string;
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        C2388x c2388x = new C2388x(0);
        Objects.requireNonNull(charSequence, "text");
        try {
            return (ZonedDateTime) dateTimeFormatter.m743b(charSequence).mo568d(c2388x);
        } catch (DateTimeParseException e) {
            throw e;
        } catch (RuntimeException e2) {
            if (charSequence.length() > 64) {
                string = charSequence.subSequence(0, 64).toString() + "...";
            } else {
                string = charSequence.toString();
            }
            DateTimeParseException dateTimeParseException = new DateTimeParseException("Text '" + string + "' could not be parsed: " + e2.getMessage(), e2);
            charSequence.toString();
            throw dateTimeParseException;
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    /* JADX INFO: renamed from: t */
    public static ZonedDateTime m659t(long j, int i, ZoneId zoneId) {
        ZoneOffset offset = zoneId.getRules().getOffset(Instant.ofEpochSecond(j, i));
        return new ZonedDateTime(LocalDateTime.m619P(j, i, offset), zoneId, offset);
    }

    private Object writeReplace() {
        return new C2362r((byte) 6, this);
    }

    @Override // p026j$.time.chrono.ChronoZonedDateTime
    /* JADX INFO: renamed from: E */
    public final ChronoZonedDateTime mo660E(ZoneId zoneId) {
        Objects.requireNonNull(zoneId, "zone");
        return this.f755c.equals(zoneId) ? this : m658G(this.f753a, zoneId, this.f754b);
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: I */
    public final ZonedDateTime mo583b(long j, InterfaceC2382r interfaceC2382r) {
        if (!(interfaceC2382r instanceof ChronoUnit)) {
            return (ZonedDateTime) interfaceC2382r.mo834t(this, j);
        }
        ChronoUnit chronoUnit = (ChronoUnit) interfaceC2382r;
        boolean z = chronoUnit.compareTo(ChronoUnit.DAYS) >= 0 && chronoUnit != ChronoUnit.FOREVER;
        LocalDateTime localDateTime = this.f753a;
        if (z) {
            return m658G(localDateTime.mo583b(j, interfaceC2382r), this.f755c, this.f754b);
        }
        LocalDateTime localDateTimeMo583b = localDateTime.mo583b(j, interfaceC2382r);
        ZoneOffset zoneOffset = this.f754b;
        ZoneId zoneId = this.f755c;
        Objects.requireNonNull(localDateTimeMo583b, "localDateTime");
        Objects.requireNonNull(zoneOffset, "offset");
        Objects.requireNonNull(zoneId, "zone");
        return zoneId.getRules().m862f(localDateTimeMo583b).contains(zoneOffset) ? new ZonedDateTime(localDateTimeMo583b, zoneId, zoneOffset) : m659t(localDateTimeMo583b.toEpochSecond(zoneOffset), localDateTimeMo583b.f735b.f919d, zoneId);
    }

    /* JADX INFO: renamed from: P */
    public final ZonedDateTime m662P(ZoneOffset zoneOffset) {
        return (zoneOffset.equals(this.f754b) || !this.f755c.getRules().m862f(this.f753a).contains(zoneOffset)) ? this : new ZonedDateTime(this.f753a, this.f755c, zoneOffset);
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: V */
    public final ZonedDateTime mo582a(long j, InterfaceC2380p interfaceC2380p) {
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return (ZonedDateTime) interfaceC2380p.mo838V(this, j);
        }
        EnumC2365a enumC2365a = (EnumC2365a) interfaceC2380p;
        int i = AbstractC2389y.f976a[enumC2365a.ordinal()];
        return i != 1 ? i != 2 ? m658G(this.f753a.mo582a(j, interfaceC2380p), this.f755c, this.f754b) : m662P(ZoneOffset.m653Z(enumC2365a.f942b.m849a(j, enumC2365a))) : m659t(j, this.f753a.f735b.f919d, this.f755c);
    }

    @Override // p026j$.time.chrono.ChronoZonedDateTime
    /* JADX INFO: renamed from: X, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public final ZonedDateTime mo666j(InterfaceC2377m interfaceC2377m) {
        if (interfaceC2377m instanceof LocalDate) {
            return m658G(LocalDateTime.m618I((LocalDate) interfaceC2377m, this.f753a.f735b), this.f755c, this.f754b);
        }
        if (interfaceC2377m instanceof C2354j) {
            return m658G(LocalDateTime.m618I(this.f753a.f734a, (C2354j) interfaceC2377m), this.f755c, this.f754b);
        }
        if (interfaceC2377m instanceof LocalDateTime) {
            return m658G((LocalDateTime) interfaceC2377m, this.f755c, this.f754b);
        }
        if (interfaceC2377m instanceof OffsetDateTime) {
            OffsetDateTime offsetDateTime = (OffsetDateTime) interfaceC2377m;
            return m658G(offsetDateTime.toLocalDateTime(), this.f755c, offsetDateTime.f738b);
        }
        if (!(interfaceC2377m instanceof Instant)) {
            return interfaceC2377m instanceof ZoneOffset ? m662P((ZoneOffset) interfaceC2377m) : (ZonedDateTime) interfaceC2377m.mo569e(this);
        }
        Instant instant = (Instant) interfaceC2377m;
        return m659t(instant.getEpochSecond(), instant.getNano(), this.f755c);
    }

    @Override // p026j$.time.chrono.ChronoZonedDateTime
    /* JADX INFO: renamed from: Y */
    public final ZonedDateTime mo670q(ZoneId zoneId) {
        Objects.requireNonNull(zoneId, "zone");
        return this.f755c.equals(zoneId) ? this : m659t(this.f753a.toEpochSecond(this.f754b), this.f753a.f735b.f919d, zoneId);
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: c */
    public final ChronoZonedDateTime mo584c(long j, InterfaceC2382r interfaceC2382r) {
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
        return c2388x == AbstractC2381q.f963f ? mo668n() : super.mo568d(c2388x);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ZonedDateTime)) {
            return false;
        }
        ZonedDateTime zonedDateTime = (ZonedDateTime) obj;
        return this.f753a.equals(zonedDateTime.f753a) && this.f754b.equals(zonedDateTime.f754b) && this.f755c.equals(zonedDateTime.f755c);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: g */
    public final int mo570g(InterfaceC2380p interfaceC2380p) {
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return super.mo570g(interfaceC2380p);
        }
        int i = AbstractC2389y.f976a[((EnumC2365a) interfaceC2380p).ordinal()];
        if (i != 1) {
            return i != 2 ? this.f753a.mo570g(interfaceC2380p) : this.f754b.getTotalSeconds();
        }
        throw new C2383s("Invalid field 'InstantSeconds' for get() method, use getLong() instead");
    }

    @Override // p026j$.time.chrono.ChronoZonedDateTime
    public ZoneId getZone() {
        return this.f755c;
    }

    public final int hashCode() {
        return Integer.rotateLeft(this.f755c.hashCode(), 3) ^ (this.f753a.hashCode() ^ this.f754b.f751b);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: i */
    public final boolean mo571i(InterfaceC2380p interfaceC2380p) {
        if (interfaceC2380p instanceof EnumC2365a) {
            return true;
        }
        return interfaceC2380p != null && interfaceC2380p.mo841t(this);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: k */
    public final long mo572k(InterfaceC2380p interfaceC2380p) {
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return interfaceC2380p.mo837P(this);
        }
        int i = AbstractC2389y.f976a[((EnumC2365a) interfaceC2380p).ordinal()];
        return i != 1 ? i != 2 ? this.f753a.mo572k(interfaceC2380p) : this.f754b.getTotalSeconds() : m677W();
    }

    @Override // p026j$.time.chrono.ChronoZonedDateTime
    /* JADX INFO: renamed from: l */
    public final C2354j mo667l() {
        return this.f753a.f735b;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: m */
    public final C2384t mo573m(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p instanceof EnumC2365a ? (interfaceC2380p == EnumC2365a.INSTANT_SECONDS || interfaceC2380p == EnumC2365a.OFFSET_SECONDS) ? ((EnumC2365a) interfaceC2380p).f942b : this.f753a.mo573m(interfaceC2380p) : interfaceC2380p.mo835B(this);
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: o */
    public final long mo586o(Temporal temporal, InterfaceC2382r interfaceC2382r) {
        ZonedDateTime zonedDateTimeM657B = m657B(temporal);
        if (!(interfaceC2382r instanceof ChronoUnit)) {
            return interfaceC2382r.between(this, zonedDateTimeM657B);
        }
        ZonedDateTime zonedDateTimeMo670q = zonedDateTimeM657B.mo670q(this.f755c);
        ChronoUnit chronoUnit = (ChronoUnit) interfaceC2382r;
        return (chronoUnit.compareTo(ChronoUnit.DAYS) < 0 || chronoUnit == ChronoUnit.FOREVER) ? new OffsetDateTime(this.f753a, this.f754b).mo586o(new OffsetDateTime(zonedDateTimeMo670q.f753a, zonedDateTimeMo670q.f754b), interfaceC2382r) : this.f753a.mo586o(zonedDateTimeMo670q.f753a, interfaceC2382r);
    }

    @Override // p026j$.time.chrono.ChronoZonedDateTime
    /* JADX INFO: renamed from: p */
    public final ZoneOffset mo669p() {
        return this.f754b;
    }

    @Override // p026j$.time.chrono.ChronoZonedDateTime
    /* JADX INFO: renamed from: toLocalDate */
    public LocalDate mo668n() {
        return this.f753a.f734a;
    }

    @Override // p026j$.time.chrono.ChronoZonedDateTime
    /* JADX INFO: renamed from: toLocalDateTime */
    public LocalDateTime mo671y() {
        return this.f753a;
    }

    public final String toString() {
        String str = this.f753a.toString() + this.f754b.f752c;
        ZoneOffset zoneOffset = this.f754b;
        ZoneId zoneId = this.f755c;
        if (zoneOffset == zoneId) {
            return str;
        }
        return str + "[" + zoneId.toString() + "]";
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
