package p026j$.time;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Objects;
import kotlin.jvm.internal.LongCompanionObject;
import p026j$.time.chrono.C2311r;
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
public final class OffsetDateTime implements Temporal, InterfaceC2377m, Comparable<OffsetDateTime>, Serializable {

    /* JADX INFO: renamed from: c */
    public static final /* synthetic */ int f736c = 0;
    private static final long serialVersionUID = 2287754244819255394L;

    /* JADX INFO: renamed from: a */
    public final LocalDateTime f737a;

    /* JADX INFO: renamed from: b */
    public final ZoneOffset f738b;

    static {
        LocalDateTime localDateTime = LocalDateTime.f732c;
        ZoneOffset zoneOffset = ZoneOffset.f750g;
        localDateTime.getClass();
        new OffsetDateTime(localDateTime, zoneOffset);
        LocalDateTime localDateTime2 = LocalDateTime.f733d;
        ZoneOffset zoneOffset2 = ZoneOffset.f749f;
        localDateTime2.getClass();
        new OffsetDateTime(localDateTime2, zoneOffset2);
    }

    public OffsetDateTime(LocalDateTime localDateTime, ZoneOffset zoneOffset) {
        Objects.requireNonNull(localDateTime, "dateTime");
        this.f737a = localDateTime;
        Objects.requireNonNull(zoneOffset, "offset");
        this.f738b = zoneOffset;
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    /* JADX INFO: renamed from: t */
    public static OffsetDateTime m633t(Instant instant, ZoneId zoneId) {
        Objects.requireNonNull(instant, "instant");
        Objects.requireNonNull(zoneId, "zone");
        ZoneOffset offset = zoneId.getRules().getOffset(instant);
        return new OffsetDateTime(LocalDateTime.m619P(instant.getEpochSecond(), instant.getNano(), offset), offset);
    }

    private Object writeReplace() {
        return new C2362r((byte) 10, this);
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: B */
    public final OffsetDateTime mo583b(long j, InterfaceC2382r interfaceC2382r) {
        return interfaceC2382r instanceof ChronoUnit ? m635G(this.f737a.mo583b(j, interfaceC2382r), this.f738b) : (OffsetDateTime) interfaceC2382r.mo834t(this, j);
    }

    /* JADX INFO: renamed from: G */
    public final OffsetDateTime m635G(LocalDateTime localDateTime, ZoneOffset zoneOffset) {
        return (this.f737a == localDateTime && this.f738b.equals(zoneOffset)) ? this : new OffsetDateTime(localDateTime, zoneOffset);
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: a */
    public final Temporal mo582a(long j, InterfaceC2380p interfaceC2380p) {
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return (OffsetDateTime) interfaceC2380p.mo838V(this, j);
        }
        EnumC2365a enumC2365a = (EnumC2365a) interfaceC2380p;
        int i = AbstractC2359o.f927a[enumC2365a.ordinal()];
        LocalDateTime localDateTime = this.f737a;
        return i != 1 ? i != 2 ? m635G(localDateTime.mo582a(j, interfaceC2380p), this.f738b) : m635G(localDateTime, ZoneOffset.m653Z(enumC2365a.f942b.m849a(j, enumC2365a))) : m633t(Instant.ofEpochSecond(j, localDateTime.f735b.f919d), this.f738b);
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
    public final int compareTo(OffsetDateTime offsetDateTime) {
        int iCompare;
        OffsetDateTime offsetDateTime2 = offsetDateTime;
        if (this.f738b.equals(offsetDateTime2.f738b)) {
            iCompare = toLocalDateTime().compareTo(offsetDateTime2.toLocalDateTime());
        } else {
            iCompare = Long.compare(this.f737a.toEpochSecond(this.f738b), offsetDateTime2.f737a.toEpochSecond(offsetDateTime2.f738b));
            if (iCompare == 0) {
                iCompare = this.f737a.f735b.f919d - offsetDateTime2.f737a.f735b.f919d;
            }
        }
        return iCompare == 0 ? toLocalDateTime().compareTo(offsetDateTime2.toLocalDateTime()) : iCompare;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: d */
    public final Object mo568d(C2388x c2388x) {
        if (c2388x == AbstractC2381q.f961d || c2388x == AbstractC2381q.f962e) {
            return this.f738b;
        }
        if (c2388x == AbstractC2381q.f958a) {
            return null;
        }
        return c2388x == AbstractC2381q.f963f ? this.f737a.f734a : c2388x == AbstractC2381q.f964g ? this.f737a.f735b : c2388x == AbstractC2381q.f959b ? C2311r.f803c : c2388x == AbstractC2381q.f960c ? ChronoUnit.NANOS : c2388x.m855m(this);
    }

    @Override // p026j$.time.temporal.InterfaceC2377m
    /* JADX INFO: renamed from: e */
    public final Temporal mo569e(Temporal temporal) {
        return temporal.mo582a(this.f737a.f734a.mo595J(), EnumC2365a.EPOCH_DAY).mo582a(this.f737a.f735b.m816d0(), EnumC2365a.NANO_OF_DAY).mo582a(this.f738b.getTotalSeconds(), EnumC2365a.OFFSET_SECONDS);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof OffsetDateTime) {
            OffsetDateTime offsetDateTime = (OffsetDateTime) obj;
            if (this.f737a.equals(offsetDateTime.f737a) && this.f738b.equals(offsetDateTime.f738b)) {
                return true;
            }
        }
        return false;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: g */
    public final int mo570g(InterfaceC2380p interfaceC2380p) {
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return super.mo570g(interfaceC2380p);
        }
        int i = AbstractC2359o.f927a[((EnumC2365a) interfaceC2380p).ordinal()];
        if (i != 1) {
            return i != 2 ? this.f737a.mo570g(interfaceC2380p) : this.f738b.getTotalSeconds();
        }
        throw new C2383s("Invalid field 'InstantSeconds' for get() method, use getLong() instead");
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: h */
    public final Temporal mo666j(LocalDate localDate) {
        return localDate == null ? (OffsetDateTime) localDate.mo569e(this) : m635G(this.f737a.mo585h(localDate), this.f738b);
    }

    public final int hashCode() {
        return this.f738b.f751b ^ this.f737a.hashCode();
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
        int i = AbstractC2359o.f927a[((EnumC2365a) interfaceC2380p).ordinal()];
        return i != 1 ? i != 2 ? this.f737a.mo572k(interfaceC2380p) : this.f738b.getTotalSeconds() : this.f737a.toEpochSecond(this.f738b);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: m */
    public final C2384t mo573m(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p instanceof EnumC2365a ? (interfaceC2380p == EnumC2365a.INSTANT_SECONDS || interfaceC2380p == EnumC2365a.OFFSET_SECONDS) ? ((EnumC2365a) interfaceC2380p).f942b : this.f737a.mo573m(interfaceC2380p) : interfaceC2380p.mo835B(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v11, types: [j$.time.OffsetDateTime] */
    /* JADX WARN: Type inference failed for: r4v13 */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v4 */
    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: o */
    public final long mo586o(Temporal temporal, InterfaceC2382r interfaceC2382r) {
        if (temporal instanceof OffsetDateTime) {
            temporal = (OffsetDateTime) temporal;
        } else {
            try {
                ZoneOffset zoneOffsetM650V = ZoneOffset.m650V(temporal);
                LocalDate localDate = (LocalDate) temporal.mo568d(AbstractC2381q.f963f);
                C2354j c2354j = (C2354j) temporal.mo568d(AbstractC2381q.f964g);
                temporal = (localDate == null || c2354j == null) ? m633t(Instant.m577B(temporal), zoneOffsetM650V) : new OffsetDateTime(LocalDateTime.m618I(localDate, c2354j), zoneOffsetM650V);
            } catch (C2284c e) {
                C2351g.m802g("Unable to obtain OffsetDateTime from TemporalAccessor: ", temporal, temporal.getClass().getName(), e);
                return 0L;
            }
        }
        if (!(interfaceC2382r instanceof ChronoUnit)) {
            return interfaceC2382r.between(this, temporal);
        }
        ZoneOffset zoneOffset = this.f738b;
        boolean zEquals = zoneOffset.equals(temporal.f738b);
        OffsetDateTime offsetDateTime = temporal;
        if (!zEquals) {
            offsetDateTime = new OffsetDateTime(temporal.f737a.m624X(zoneOffset.getTotalSeconds() - temporal.f738b.getTotalSeconds()), zoneOffset);
        }
        return this.f737a.mo586o(offsetDateTime.f737a, interfaceC2382r);
    }

    public LocalDateTime toLocalDateTime() {
        return this.f737a;
    }

    public final String toString() {
        return this.f737a.toString() + this.f738b.f752c;
    }
}
