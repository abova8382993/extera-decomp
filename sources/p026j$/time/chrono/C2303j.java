package p026j$.time.chrono;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import p026j$.time.C2351g;
import p026j$.time.Duration;
import p026j$.time.Instant;
import p026j$.time.LocalDateTime;
import p026j$.time.ZoneId;
import p026j$.time.ZoneOffset;
import p026j$.time.temporal.ChronoUnit;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2380p;
import p026j$.time.temporal.InterfaceC2382r;
import p026j$.time.temporal.Temporal;
import p026j$.time.zone.C2391b;
import p026j$.time.zone.ZoneRules;

/* JADX INFO: renamed from: j$.time.chrono.j */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2303j implements ChronoZonedDateTime, Serializable {
    private static final long serialVersionUID = -5261813987200935591L;

    /* JADX INFO: renamed from: a */
    public final transient C2295f f782a;

    /* JADX INFO: renamed from: b */
    public final transient ZoneOffset f783b;

    /* JADX INFO: renamed from: c */
    public final transient ZoneId f784c;

    public C2303j(ZoneId zoneId, ZoneOffset zoneOffset, C2295f c2295f) {
        Objects.requireNonNull(c2295f, "dateTime");
        this.f782a = c2295f;
        Objects.requireNonNull(zoneOffset, "offset");
        this.f783b = zoneOffset;
        Objects.requireNonNull(zoneId, "zone");
        this.f784c = zoneId;
    }

    /* JADX INFO: renamed from: B */
    public static C2303j m715B(ZoneId zoneId, ZoneOffset zoneOffset, C2295f c2295f) {
        Objects.requireNonNull(c2295f, "localDateTime");
        Objects.requireNonNull(zoneId, "zone");
        if (zoneId instanceof ZoneOffset) {
            return new C2303j(zoneId, (ZoneOffset) zoneId, c2295f);
        }
        ZoneRules rules = zoneId.getRules();
        LocalDateTime localDateTimeM617B = LocalDateTime.m617B(c2295f);
        List listM862f = rules.m862f(localDateTimeM617B);
        if (listM862f.size() == 1) {
            zoneOffset = (ZoneOffset) listM862f.get(0);
        } else if (listM862f.size() == 0) {
            C2391b c2391bM861e = rules.m861e(localDateTimeM617B);
            c2295f = c2295f.m697G(c2295f.f768a, 0L, 0L, Duration.ofSeconds(c2391bM861e.f995d.getTotalSeconds() - c2391bM861e.f994c.getTotalSeconds()).getSeconds(), 0L);
            zoneOffset = c2391bM861e.f995d;
        } else {
            if (zoneOffset == null || !listM862f.contains(zoneOffset)) {
                zoneOffset = (ZoneOffset) listM862f.get(0);
            }
            c2295f = c2295f;
        }
        Objects.requireNonNull(zoneOffset, "offset");
        return new C2303j(zoneId, zoneOffset, c2295f);
    }

    /* JADX INFO: renamed from: G */
    public static C2303j m716G(InterfaceC2304k interfaceC2304k, Instant instant, ZoneId zoneId) {
        ZoneOffset offset = zoneId.getRules().getOffset(instant);
        Objects.requireNonNull(offset, "offset");
        return new C2303j(zoneId, offset, (C2295f) interfaceC2304k.mo720M(LocalDateTime.m619P(instant.getEpochSecond(), instant.getNano(), offset)));
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    /* JADX INFO: renamed from: t */
    public static C2303j m717t(InterfaceC2304k interfaceC2304k, Temporal temporal) {
        C2303j c2303j = (C2303j) temporal;
        if (interfaceC2304k.equals(c2303j.m678f())) {
            return c2303j;
        }
        C2351g.m800e("Chronology mismatch, required: ", interfaceC2304k.getId(), c2303j.m678f().getId());
        return null;
    }

    private Object writeReplace() {
        return new C2292d0((byte) 3, this);
    }

    @Override // p026j$.time.chrono.ChronoZonedDateTime
    /* JADX INFO: renamed from: E */
    public final ChronoZonedDateTime mo660E(ZoneId zoneId) {
        return m715B(zoneId, this.f783b, this.f782a);
    }

    @Override // p026j$.time.chrono.ChronoZonedDateTime, p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: a */
    public final ChronoZonedDateTime mo582a(long j, InterfaceC2380p interfaceC2380p) {
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return m717t(m678f(), interfaceC2380p.mo838V(this, j));
        }
        EnumC2365a enumC2365a = (EnumC2365a) interfaceC2380p;
        int i = AbstractC2301i.f779a[enumC2365a.ordinal()];
        if (i == 1) {
            return mo583b(j - m677W(), (InterfaceC2382r) ChronoUnit.SECONDS);
        }
        if (i != 2) {
            return m715B(this.f784c, this.f783b, this.f782a.mo582a(j, interfaceC2380p));
        }
        return m716G(m678f(), this.f782a.toInstant(ZoneOffset.m653Z(enumC2365a.f942b.m849a(j, enumC2365a))), this.f784c);
    }

    @Override // p026j$.time.chrono.ChronoZonedDateTime, p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: b */
    public final ChronoZonedDateTime mo583b(long j, InterfaceC2382r interfaceC2382r) {
        return interfaceC2382r instanceof ChronoUnit ? mo666j(this.f782a.mo583b(j, interfaceC2382r)) : m717t(m678f(), interfaceC2382r.mo834t(this, j));
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ChronoZonedDateTime) && compareTo((ChronoZonedDateTime) obj) == 0;
    }

    @Override // p026j$.time.chrono.ChronoZonedDateTime
    public final ZoneId getZone() {
        return this.f784c;
    }

    public final int hashCode() {
        return Integer.rotateLeft(this.f784c.hashCode(), 3) ^ (this.f782a.hashCode() ^ this.f783b.f751b);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: i */
    public final boolean mo571i(InterfaceC2380p interfaceC2380p) {
        if (interfaceC2380p instanceof EnumC2365a) {
            return true;
        }
        return interfaceC2380p != null && interfaceC2380p.mo841t(this);
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: o */
    public final long mo586o(Temporal temporal, InterfaceC2382r interfaceC2382r) {
        Objects.requireNonNull(temporal, "endExclusive");
        ChronoZonedDateTime chronoZonedDateTimeMo721w = m678f().mo721w(temporal);
        if (interfaceC2382r instanceof ChronoUnit) {
            return this.f782a.mo586o(chronoZonedDateTimeMo721w.mo670q(this.f783b).mo671y(), interfaceC2382r);
        }
        Objects.requireNonNull(interfaceC2382r, "unit");
        return interfaceC2382r.between(this, chronoZonedDateTimeMo721w);
    }

    @Override // p026j$.time.chrono.ChronoZonedDateTime
    /* JADX INFO: renamed from: p */
    public final ZoneOffset mo669p() {
        return this.f783b;
    }

    @Override // p026j$.time.chrono.ChronoZonedDateTime
    /* JADX INFO: renamed from: q */
    public final ChronoZonedDateTime mo670q(ZoneId zoneId) {
        Objects.requireNonNull(zoneId, "zone");
        if (this.f784c.equals(zoneId)) {
            return this;
        }
        return m716G(m678f(), this.f782a.toInstant(this.f783b), zoneId);
    }

    public final String toString() {
        String str = this.f782a.toString() + this.f783b.f752c;
        ZoneOffset zoneOffset = this.f783b;
        ZoneId zoneId = this.f784c;
        if (zoneOffset == zoneId) {
            return str;
        }
        return str + "[" + zoneId.toString() + "]";
    }

    @Override // p026j$.time.chrono.ChronoZonedDateTime
    /* JADX INFO: renamed from: y */
    public final ChronoLocalDateTime mo671y() {
        return this.f782a;
    }
}
