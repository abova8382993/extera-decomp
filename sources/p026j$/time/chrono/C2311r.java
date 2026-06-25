package p026j$.time.chrono;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import p026j$.time.AbstractC2283b;
import p026j$.time.C2351g;
import p026j$.time.C2364t;
import p026j$.time.EnumC2356l;
import p026j$.time.Instant;
import p026j$.time.LocalDate;
import p026j$.time.LocalDateTime;
import p026j$.time.ZoneId;
import p026j$.time.ZonedDateTime;
import p026j$.time.format.EnumC2350z;
import p026j$.time.temporal.C2384t;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2376l;

/* JADX INFO: renamed from: j$.time.chrono.r */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2311r extends AbstractC2285a implements Serializable {

    /* JADX INFO: renamed from: c */
    public static final C2311r f803c = new C2311r();
    private static final long serialVersionUID = -1440403870442975015L;

    private C2311r() {
    }

    /* JADX INFO: renamed from: X */
    public static boolean m733X(long j) {
        if ((3 & j) == 0) {
            return j % 100 != 0 || j % 400 == 0;
        }
        return false;
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: A */
    public final List mo701A() {
        return AbstractC2283b.m674c(EnumC2312s.values());
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: C */
    public final InterfaceC2305l mo702C(int i) {
        if (i == 0) {
            return EnumC2312s.BCE;
        }
        if (i == 1) {
            return EnumC2312s.f804CE;
        }
        C2351g.m797b("Invalid era: ", i);
        return null;
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: D */
    public final int mo703D(InterfaceC2305l interfaceC2305l, int i) {
        if (interfaceC2305l instanceof EnumC2312s) {
            return interfaceC2305l == EnumC2312s.f804CE ? i : 1 - i;
        }
        throw new ClassCastException("Era must be IsoEra");
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: H */
    public final InterfaceC2287b mo704H(InterfaceC2376l interfaceC2376l) {
        return LocalDate.m588G(interfaceC2376l);
    }

    @Override // p026j$.time.chrono.AbstractC2285a
    /* JADX INFO: renamed from: I */
    public final void mo683I(Map map, EnumC2350z enumC2350z) {
        EnumC2365a enumC2365a = EnumC2365a.PROLEPTIC_MONTH;
        Long l = (Long) map.remove(enumC2365a);
        if (l != null) {
            if (enumC2350z != EnumC2350z.LENIENT) {
                enumC2365a.m839X(l.longValue());
            }
            AbstractC2285a.m682t(map, EnumC2365a.MONTH_OF_YEAR, ((int) Math.floorMod(l.longValue(), 12L)) + 1);
            AbstractC2285a.m682t(map, EnumC2365a.YEAR, Math.floorDiv(l.longValue(), 12L));
        }
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: L */
    public final InterfaceC2287b mo705L() {
        return LocalDate.m588G(LocalDate.m589c0(AbstractC2283b.m675d()));
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: M */
    public final ChronoLocalDateTime mo720M(InterfaceC2376l interfaceC2376l) {
        return LocalDateTime.m617B(interfaceC2376l);
    }

    @Override // p026j$.time.chrono.AbstractC2285a
    /* JADX INFO: renamed from: P */
    public final InterfaceC2287b mo684P(Map map, EnumC2350z enumC2350z) {
        EnumC2365a enumC2365a = EnumC2365a.YEAR;
        int iM849a = enumC2365a.f942b.m849a(((Long) map.remove(enumC2365a)).longValue(), enumC2365a);
        boolean z = true;
        if (enumC2350z == EnumC2350z.LENIENT) {
            return LocalDate.m593of(iM849a, 1, 1).m609g0(Math.subtractExact(((Long) map.remove(EnumC2365a.MONTH_OF_YEAR)).longValue(), 1L)).plusDays(Math.subtractExact(((Long) map.remove(EnumC2365a.DAY_OF_MONTH)).longValue(), 1L));
        }
        EnumC2365a enumC2365a2 = EnumC2365a.MONTH_OF_YEAR;
        int iM849a2 = enumC2365a2.f942b.m849a(((Long) map.remove(enumC2365a2)).longValue(), enumC2365a2);
        EnumC2365a enumC2365a3 = EnumC2365a.DAY_OF_MONTH;
        int iM849a3 = enumC2365a3.f942b.m849a(((Long) map.remove(enumC2365a3)).longValue(), enumC2365a3);
        if (enumC2350z == EnumC2350z.SMART) {
            if (iM849a2 == 4 || iM849a2 == 6 || iM849a2 == 9 || iM849a2 == 11) {
                iM849a3 = Math.min(iM849a3, 30);
            } else if (iM849a2 == 2) {
                EnumC2356l enumC2356l = EnumC2356l.FEBRUARY;
                long j = iM849a;
                int i = C2364t.f936b;
                if ((3 & j) != 0 || (j % 100 == 0 && j % 400 != 0)) {
                    z = false;
                }
                iM849a3 = Math.min(iM849a3, enumC2356l.m823B(z));
            }
        }
        return LocalDate.m593of(iM849a, iM849a2, iM849a3);
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: Q */
    public final InterfaceC2287b mo706Q(int i, int i2, int i3) {
        return LocalDate.m593of(i, i2, i3);
    }

    @Override // p026j$.time.chrono.AbstractC2285a, p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: S */
    public final InterfaceC2287b mo685S(Map map, EnumC2350z enumC2350z) {
        return (LocalDate) super.mo685S(map, enumC2350z);
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: T */
    public final ChronoZonedDateTime mo707T(Instant instant, ZoneId zoneId) {
        Objects.requireNonNull(instant, "instant");
        Objects.requireNonNull(zoneId, "zone");
        return ZonedDateTime.m659t(instant.getEpochSecond(), instant.getNano(), zoneId);
    }

    @Override // p026j$.time.chrono.AbstractC2285a
    /* JADX INFO: renamed from: V */
    public final InterfaceC2287b mo686V(Map map, EnumC2350z enumC2350z) {
        EnumC2365a enumC2365a = EnumC2365a.YEAR_OF_ERA;
        Long l = (Long) map.remove(enumC2365a);
        if (l != null) {
            if (enumC2350z != EnumC2350z.LENIENT) {
                enumC2365a.m839X(l.longValue());
            }
            Long l2 = (Long) map.remove(EnumC2365a.ERA);
            if (l2 == null) {
                EnumC2365a enumC2365a2 = EnumC2365a.YEAR;
                Long l3 = (Long) map.get(enumC2365a2);
                if (enumC2350z != EnumC2350z.STRICT) {
                    AbstractC2285a.m682t(map, enumC2365a2, (l3 == null || l3.longValue() > 0) ? l.longValue() : Math.subtractExact(1L, l.longValue()));
                } else if (l3 != null) {
                    long jLongValue = l3.longValue();
                    long jLongValue2 = l.longValue();
                    if (jLongValue <= 0) {
                        jLongValue2 = Math.subtractExact(1L, jLongValue2);
                    }
                    AbstractC2285a.m682t(map, enumC2365a2, jLongValue2);
                } else {
                    map.put(enumC2365a, l);
                }
            } else if (l2.longValue() == 1) {
                AbstractC2285a.m682t(map, EnumC2365a.YEAR, l.longValue());
            } else {
                if (l2.longValue() != 0) {
                    C2351g.m804i("Invalid value for era: ", l2);
                    return null;
                }
                AbstractC2285a.m682t(map, EnumC2365a.YEAR, Math.subtractExact(1L, l.longValue()));
            }
        } else {
            EnumC2365a enumC2365a3 = EnumC2365a.ERA;
            if (map.containsKey(enumC2365a3)) {
                enumC2365a3.m839X(((Long) map.get(enumC2365a3)).longValue());
            }
        }
        return null;
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    public final String getId() {
        return "ISO";
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: r */
    public final InterfaceC2287b mo708r(long j) {
        return LocalDate.m590d0(j);
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: v */
    public final String mo709v() {
        return "iso8601";
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: w */
    public final ChronoZonedDateTime mo721w(InterfaceC2376l interfaceC2376l) {
        return ZonedDateTime.m657B(interfaceC2376l);
    }

    public Object writeReplace() {
        return new C2292d0((byte) 1, this);
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: x */
    public final InterfaceC2287b mo710x(int i, int i2) {
        return LocalDate.m591e0(i, i2);
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: z */
    public final C2384t mo711z(EnumC2365a enumC2365a) {
        return enumC2365a.f942b;
    }
}
