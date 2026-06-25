package p026j$.time;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Objects;
import kotlin.jvm.internal.LongCompanionObject;
import kotlin.time.DurationKt;
import org.telegram.messenger.MediaDataController;
import p026j$.time.format.DateTimeFormatter;
import p026j$.time.temporal.AbstractC2381q;
import p026j$.time.temporal.C2383s;
import p026j$.time.temporal.ChronoUnit;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2376l;
import p026j$.time.temporal.InterfaceC2377m;
import p026j$.time.temporal.InterfaceC2380p;
import p026j$.time.temporal.InterfaceC2382r;
import p026j$.time.temporal.Temporal;

/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class Instant implements Temporal, InterfaceC2377m, Comparable<Instant>, Serializable {

    /* JADX INFO: renamed from: c */
    public static final Instant f724c = new Instant(0, 0);
    private static final long serialVersionUID = -665713676816604388L;

    /* JADX INFO: renamed from: a */
    public final long f725a;

    /* JADX INFO: renamed from: b */
    public final int f726b;

    static {
        ofEpochSecond(-31557014167219200L, 0L);
        ofEpochSecond(31556889864403199L, 999999999L);
    }

    public Instant(long j, int i) {
        this.f725a = j;
        this.f726b = i;
    }

    /* JADX INFO: renamed from: B */
    public static Instant m577B(InterfaceC2376l interfaceC2376l) {
        if (interfaceC2376l instanceof Instant) {
            return (Instant) interfaceC2376l;
        }
        Objects.requireNonNull(interfaceC2376l, "temporal");
        try {
            return ofEpochSecond(interfaceC2376l.mo572k(EnumC2365a.INSTANT_SECONDS), interfaceC2376l.mo570g(EnumC2365a.NANO_OF_SECOND));
        } catch (C2284c e) {
            C2351g.m802g("Unable to obtain Instant from TemporalAccessor: ", interfaceC2376l, interfaceC2376l.getClass().getName(), e);
            return null;
        }
    }

    public static Instant now() {
        C2282a.f756b.getClass();
        return ofEpochMilli(System.currentTimeMillis());
    }

    public static Instant ofEpochMilli(long j) {
        return m578t(Math.floorDiv(j, 1000L), ((int) Math.floorMod(j, 1000L)) * DurationKt.NANOS_IN_MILLIS);
    }

    public static Instant ofEpochSecond(long j, long j2) {
        return m578t(Math.addExact(j, Math.floorDiv(j2, 1000000000L)), (int) Math.floorMod(j2, 1000000000L));
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    /* JADX INFO: renamed from: t */
    public static Instant m578t(long j, int i) {
        if ((((long) i) | j) == 0) {
            return f724c;
        }
        if (j >= -31557014167219200L && j <= 31556889864403199L) {
            return new Instant(j, i);
        }
        C2351g.m796a("Instant exceeds minimum or maximum instant");
        return null;
    }

    private Object writeReplace() {
        return new C2362r((byte) 2, this);
    }

    /* JADX INFO: renamed from: G */
    public final Instant m579G(long j, long j2) {
        if ((j | j2) == 0) {
            return this;
        }
        return ofEpochSecond(Math.addExact(Math.addExact(this.f725a, j), j2 / 1000000000), ((long) this.f726b) + (j2 % 1000000000));
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: I, reason: merged with bridge method [inline-methods] */
    public final Instant mo583b(long j, InterfaceC2382r interfaceC2382r) {
        if (!(interfaceC2382r instanceof ChronoUnit)) {
            return (Instant) interfaceC2382r.mo834t(this, j);
        }
        switch (AbstractC2321e.f821b[((ChronoUnit) interfaceC2382r).ordinal()]) {
            case 1:
                return m579G(0L, j);
            case 2:
                return m579G(j / 1000000, (j % 1000000) * 1000);
            case 3:
                return m579G(j / 1000, (j % 1000) * 1000000);
            case 4:
                return plusSeconds(j);
            case 5:
                return plusSeconds(Math.multiplyExact(j, 60L));
            case 6:
                return plusSeconds(Math.multiplyExact(j, 3600L));
            case 7:
                return plusSeconds(Math.multiplyExact(j, 43200L));
            case 8:
                return plusSeconds(Math.multiplyExact(j, 86400L));
            default:
                C2351g.m799d("Unsupported unit: ", interfaceC2382r);
                return null;
        }
    }

    /* JADX INFO: renamed from: P */
    public final long m581P(Instant instant) {
        long jSubtractExact = Math.subtractExact(instant.f725a, this.f725a);
        long j = instant.f726b - this.f726b;
        return (jSubtractExact <= 0 || j >= 0) ? (jSubtractExact >= 0 || j <= 0) ? jSubtractExact : jSubtractExact + 1 : jSubtractExact - 1;
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: a */
    public final Temporal mo582a(long j, InterfaceC2380p interfaceC2380p) {
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return (Instant) interfaceC2380p.mo838V(this, j);
        }
        EnumC2365a enumC2365a = (EnumC2365a) interfaceC2380p;
        enumC2365a.m839X(j);
        int i = AbstractC2321e.f820a[enumC2365a.ordinal()];
        if (i == 1) {
            return j != ((long) this.f726b) ? m578t(this.f725a, (int) j) : this;
        }
        if (i == 2) {
            int i2 = ((int) j) * MediaDataController.MAX_STYLE_RUNS_COUNT;
            return i2 != this.f726b ? m578t(this.f725a, i2) : this;
        }
        if (i == 3) {
            int i3 = ((int) j) * DurationKt.NANOS_IN_MILLIS;
            return i3 != this.f726b ? m578t(this.f725a, i3) : this;
        }
        if (i == 4) {
            return j != this.f725a ? m578t(j, this.f726b) : this;
        }
        throw new C2383s(AbstractC2320d.m741a("Unsupported field: ", interfaceC2380p));
    }

    public OffsetDateTime atOffset(ZoneOffset zoneOffset) {
        return OffsetDateTime.m633t(this, zoneOffset);
    }

    public ZonedDateTime atZone(ZoneId zoneId) {
        Objects.requireNonNull(zoneId, "zone");
        return ZonedDateTime.m659t(getEpochSecond(), getNano(), zoneId);
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
    public final int compareTo(Instant instant) {
        Instant instant2 = instant;
        int iCompare = Long.compare(this.f725a, instant2.f725a);
        return iCompare != 0 ? iCompare : this.f726b - instant2.f726b;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: d */
    public final Object mo568d(C2388x c2388x) {
        if (c2388x == AbstractC2381q.f960c) {
            return ChronoUnit.NANOS;
        }
        if (c2388x == AbstractC2381q.f959b || c2388x == AbstractC2381q.f958a || c2388x == AbstractC2381q.f962e || c2388x == AbstractC2381q.f961d || c2388x == AbstractC2381q.f963f || c2388x == AbstractC2381q.f964g) {
            return null;
        }
        return c2388x.m855m(this);
    }

    @Override // p026j$.time.temporal.InterfaceC2377m
    /* JADX INFO: renamed from: e */
    public final Temporal mo569e(Temporal temporal) {
        return temporal.mo582a(this.f725a, EnumC2365a.INSTANT_SECONDS).mo582a(this.f726b, EnumC2365a.NANO_OF_SECOND);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Instant) {
            Instant instant = (Instant) obj;
            if (this.f725a == instant.f725a && this.f726b == instant.f726b) {
                return true;
            }
        }
        return false;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: g */
    public final int mo570g(InterfaceC2380p interfaceC2380p) {
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return super.mo573m(interfaceC2380p).m849a(interfaceC2380p.mo837P(this), interfaceC2380p);
        }
        int i = AbstractC2321e.f820a[((EnumC2365a) interfaceC2380p).ordinal()];
        if (i == 1) {
            return this.f726b;
        }
        if (i == 2) {
            return this.f726b / MediaDataController.MAX_STYLE_RUNS_COUNT;
        }
        if (i == 3) {
            return this.f726b / DurationKt.NANOS_IN_MILLIS;
        }
        if (i == 4) {
            EnumC2365a enumC2365a = EnumC2365a.INSTANT_SECONDS;
            enumC2365a.f942b.m849a(this.f725a, enumC2365a);
        }
        throw new C2383s(AbstractC2320d.m741a("Unsupported field: ", interfaceC2380p));
    }

    public long getEpochSecond() {
        return this.f725a;
    }

    public int getNano() {
        return this.f726b;
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: h */
    public final Temporal mo612j(LocalDate localDate) {
        return (Instant) localDate.mo569e(this);
    }

    public final int hashCode() {
        long j = this.f725a;
        return (this.f726b * 51) + ((int) (j ^ (j >>> 32)));
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: i */
    public final boolean mo571i(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p instanceof EnumC2365a ? interfaceC2380p == EnumC2365a.INSTANT_SECONDS || interfaceC2380p == EnumC2365a.NANO_OF_SECOND || interfaceC2380p == EnumC2365a.MICRO_OF_SECOND || interfaceC2380p == EnumC2365a.MILLI_OF_SECOND : interfaceC2380p != null && interfaceC2380p.mo841t(this);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: k */
    public final long mo572k(InterfaceC2380p interfaceC2380p) {
        int i;
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return interfaceC2380p.mo837P(this);
        }
        int i2 = AbstractC2321e.f820a[((EnumC2365a) interfaceC2380p).ordinal()];
        if (i2 == 1) {
            i = this.f726b;
        } else if (i2 == 2) {
            i = this.f726b / MediaDataController.MAX_STYLE_RUNS_COUNT;
        } else {
            if (i2 != 3) {
                if (i2 == 4) {
                    return this.f725a;
                }
                throw new C2383s(AbstractC2320d.m741a("Unsupported field: ", interfaceC2380p));
            }
            i = this.f726b / DurationKt.NANOS_IN_MILLIS;
        }
        return i;
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: o */
    public final long mo586o(Temporal temporal, InterfaceC2382r interfaceC2382r) {
        Instant instantM577B = m577B(temporal);
        if (!(interfaceC2382r instanceof ChronoUnit)) {
            return interfaceC2382r.between(this, instantM577B);
        }
        switch (AbstractC2321e.f821b[((ChronoUnit) interfaceC2382r).ordinal()]) {
            case 1:
                return Math.addExact(Math.multiplyExact(Math.subtractExact(instantM577B.f725a, this.f725a), 1000000000L), instantM577B.f726b - this.f726b);
            case 2:
                return Math.addExact(Math.multiplyExact(Math.subtractExact(instantM577B.f725a, this.f725a), 1000000000L), instantM577B.f726b - this.f726b) / 1000;
            case 3:
                return Math.subtractExact(instantM577B.toEpochMilli(), toEpochMilli());
            case 4:
                return m581P(instantM577B);
            case 5:
                return m581P(instantM577B) / 60;
            case 6:
                return m581P(instantM577B) / 3600;
            case 7:
                return m581P(instantM577B) / 43200;
            case 8:
                return m581P(instantM577B) / 86400;
            default:
                C2351g.m799d("Unsupported unit: ", interfaceC2382r);
                return 0L;
        }
    }

    public Instant plusSeconds(long j) {
        return m579G(j, 0L);
    }

    public long toEpochMilli() {
        long j = this.f725a;
        return (j >= 0 || this.f726b <= 0) ? Math.addExact(Math.multiplyExact(j, 1000L), this.f726b / DurationKt.NANOS_IN_MILLIS) : Math.addExact(Math.multiplyExact(j + 1, 1000L), (this.f726b / DurationKt.NANOS_IN_MILLIS) - 1000);
    }

    public String toString() {
        return DateTimeFormatter.f824f.m742a(this);
    }
}
