package p026j$.time.chrono;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import p026j$.time.C2284c;
import p026j$.time.C2351g;
import p026j$.time.C2388x;
import p026j$.time.DayOfWeek;
import p026j$.time.format.EnumC2350z;
import p026j$.time.temporal.C2378n;
import p026j$.time.temporal.ChronoUnit;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2380p;
import p026j$.time.temporal.InterfaceC2382r;

/* JADX INFO: renamed from: j$.time.chrono.a */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public abstract class AbstractC2285a implements InterfaceC2304k {

    /* JADX INFO: renamed from: a */
    public static final ConcurrentHashMap f758a = new ConcurrentHashMap();

    /* JADX INFO: renamed from: b */
    public static final ConcurrentHashMap f759b = new ConcurrentHashMap();

    static {
        new Locale("ja", "JP", "JP");
    }

    /* JADX INFO: renamed from: B */
    public static InterfaceC2304k m680B(InterfaceC2304k interfaceC2304k, String str) {
        String strMo709v;
        InterfaceC2304k interfaceC2304k2 = (InterfaceC2304k) f758a.putIfAbsent(str, interfaceC2304k);
        if (interfaceC2304k2 == null && (strMo709v = interfaceC2304k.mo709v()) != null) {
            f759b.putIfAbsent(strMo709v, interfaceC2304k);
        }
        return interfaceC2304k2;
    }

    /* JADX INFO: renamed from: G */
    public static InterfaceC2287b m681G(InterfaceC2287b interfaceC2287b, long j, long j2, long j3) {
        long j4;
        InterfaceC2287b interfaceC2287bMo583b = interfaceC2287b.mo583b(j, (InterfaceC2382r) ChronoUnit.MONTHS);
        ChronoUnit chronoUnit = ChronoUnit.WEEKS;
        InterfaceC2287b interfaceC2287bMo583b2 = interfaceC2287bMo583b.mo583b(j2, (InterfaceC2382r) chronoUnit);
        if (j3 <= 7) {
            if (j3 < 1) {
                interfaceC2287bMo583b2 = interfaceC2287bMo583b2.mo583b(Math.subtractExact(j3, 7L) / 7, (InterfaceC2382r) chronoUnit);
                j4 = (j3 + 6) % 7;
            }
            return interfaceC2287bMo583b2.mo666j(new C2378n(DayOfWeek.m567t((int) j3).getValue(), 0));
        }
        long j5 = j3 - 1;
        interfaceC2287bMo583b2 = interfaceC2287bMo583b2.mo583b(j5 / 7, (InterfaceC2382r) chronoUnit);
        j4 = j5 % 7;
        j3 = j4 + 1;
        return interfaceC2287bMo583b2.mo666j(new C2378n(DayOfWeek.m567t((int) j3).getValue(), 0));
    }

    /* JADX INFO: renamed from: t */
    public static void m682t(Map map, EnumC2365a enumC2365a, long j) {
        Long l = (Long) map.get(enumC2365a);
        if (l == null || l.longValue() == j) {
            map.put(enumC2365a, Long.valueOf(j));
            return;
        }
        throw new C2284c("Conflict found: " + enumC2365a + " " + l + " differs from " + enumC2365a + " " + j);
    }

    /* JADX INFO: renamed from: I */
    public void mo683I(Map map, EnumC2350z enumC2350z) {
        EnumC2365a enumC2365a = EnumC2365a.PROLEPTIC_MONTH;
        Long l = (Long) map.remove(enumC2365a);
        if (l != null) {
            if (enumC2350z != EnumC2350z.LENIENT) {
                enumC2365a.m839X(l.longValue());
            }
            InterfaceC2287b interfaceC2287bMo582a = mo705L().mo582a(1L, (InterfaceC2380p) EnumC2365a.DAY_OF_MONTH).mo582a(l.longValue(), (InterfaceC2380p) enumC2365a);
            m682t(map, EnumC2365a.MONTH_OF_YEAR, interfaceC2287bMo582a.mo570g(r6));
            m682t(map, EnumC2365a.YEAR, interfaceC2287bMo582a.mo570g(r6));
        }
    }

    /* JADX INFO: renamed from: P */
    public InterfaceC2287b mo684P(Map map, EnumC2350z enumC2350z) {
        EnumC2365a enumC2365a = EnumC2365a.YEAR;
        int iM849a = mo711z(enumC2365a).m849a(((Long) map.remove(enumC2365a)).longValue(), enumC2365a);
        if (enumC2350z == EnumC2350z.LENIENT) {
            long jSubtractExact = Math.subtractExact(((Long) map.remove(EnumC2365a.MONTH_OF_YEAR)).longValue(), 1L);
            return mo706Q(iM849a, 1, 1).mo583b(jSubtractExact, (InterfaceC2382r) ChronoUnit.MONTHS).mo583b(Math.subtractExact(((Long) map.remove(EnumC2365a.DAY_OF_MONTH)).longValue(), 1L), (InterfaceC2382r) ChronoUnit.DAYS);
        }
        EnumC2365a enumC2365a2 = EnumC2365a.MONTH_OF_YEAR;
        int iM849a2 = mo711z(enumC2365a2).m849a(((Long) map.remove(enumC2365a2)).longValue(), enumC2365a2);
        EnumC2365a enumC2365a3 = EnumC2365a.DAY_OF_MONTH;
        int iM849a3 = mo711z(enumC2365a3).m849a(((Long) map.remove(enumC2365a3)).longValue(), enumC2365a3);
        if (enumC2350z != EnumC2350z.SMART) {
            return mo706Q(iM849a, iM849a2, iM849a3);
        }
        try {
            return mo706Q(iM849a, iM849a2, iM849a3);
        } catch (C2284c unused) {
            return this.mo706Q(iM849a, iM849a2, 1).mo666j(new C2388x(2));
        }
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: S */
    public InterfaceC2287b mo685S(Map map, EnumC2350z enumC2350z) {
        EnumC2365a enumC2365a = EnumC2365a.EPOCH_DAY;
        if (map.containsKey(enumC2365a)) {
            return mo708r(((Long) map.remove(enumC2365a)).longValue());
        }
        mo683I(map, enumC2350z);
        InterfaceC2287b interfaceC2287bMo686V = mo686V(map, enumC2350z);
        if (interfaceC2287bMo686V != null) {
            return interfaceC2287bMo686V;
        }
        EnumC2365a enumC2365a2 = EnumC2365a.YEAR;
        if (map.containsKey(enumC2365a2)) {
            EnumC2365a enumC2365a3 = EnumC2365a.MONTH_OF_YEAR;
            if (map.containsKey(enumC2365a3)) {
                if (map.containsKey(EnumC2365a.DAY_OF_MONTH)) {
                    return mo684P(map, enumC2350z);
                }
                EnumC2365a enumC2365a4 = EnumC2365a.ALIGNED_WEEK_OF_MONTH;
                if (map.containsKey(enumC2365a4)) {
                    EnumC2365a enumC2365a5 = EnumC2365a.ALIGNED_DAY_OF_WEEK_IN_MONTH;
                    if (map.containsKey(enumC2365a5)) {
                        int iM849a = mo711z(enumC2365a2).m849a(((Long) map.remove(enumC2365a2)).longValue(), enumC2365a2);
                        if (enumC2350z == EnumC2350z.LENIENT) {
                            long jSubtractExact = Math.subtractExact(((Long) map.remove(enumC2365a3)).longValue(), 1L);
                            return mo706Q(iM849a, 1, 1).mo583b(jSubtractExact, (InterfaceC2382r) ChronoUnit.MONTHS).mo583b(Math.subtractExact(((Long) map.remove(enumC2365a4)).longValue(), 1L), (InterfaceC2382r) ChronoUnit.WEEKS).mo583b(Math.subtractExact(((Long) map.remove(enumC2365a5)).longValue(), 1L), (InterfaceC2382r) ChronoUnit.DAYS);
                        }
                        int iM849a2 = mo711z(enumC2365a3).m849a(((Long) map.remove(enumC2365a3)).longValue(), enumC2365a3);
                        int iM849a3 = mo711z(enumC2365a4).m849a(((Long) map.remove(enumC2365a4)).longValue(), enumC2365a4);
                        InterfaceC2287b interfaceC2287bMo583b = mo706Q(iM849a, iM849a2, 1).mo583b((mo711z(enumC2365a5).m849a(((Long) map.remove(enumC2365a5)).longValue(), enumC2365a5) - 1) + ((iM849a3 - 1) * 7), (InterfaceC2382r) ChronoUnit.DAYS);
                        if (enumC2350z != EnumC2350z.STRICT || interfaceC2287bMo583b.mo570g(enumC2365a3) == iM849a2) {
                            return interfaceC2287bMo583b;
                        }
                        C2351g.m796a("Strict mode rejected resolved date as it is in a different month");
                        return null;
                    }
                    EnumC2365a enumC2365a6 = EnumC2365a.DAY_OF_WEEK;
                    if (map.containsKey(enumC2365a6)) {
                        int iM849a4 = mo711z(enumC2365a2).m849a(((Long) map.remove(enumC2365a2)).longValue(), enumC2365a2);
                        if (enumC2350z == EnumC2350z.LENIENT) {
                            return m681G(mo706Q(iM849a4, 1, 1), Math.subtractExact(((Long) map.remove(enumC2365a3)).longValue(), 1L), Math.subtractExact(((Long) map.remove(enumC2365a4)).longValue(), 1L), Math.subtractExact(((Long) map.remove(enumC2365a6)).longValue(), 1L));
                        }
                        int iM849a5 = mo711z(enumC2365a3).m849a(((Long) map.remove(enumC2365a3)).longValue(), enumC2365a3);
                        InterfaceC2287b interfaceC2287bMo666j = mo706Q(iM849a4, iM849a5, 1).mo583b((mo711z(enumC2365a4).m849a(((Long) map.remove(enumC2365a4)).longValue(), enumC2365a4) - 1) * 7, (InterfaceC2382r) ChronoUnit.DAYS).mo666j(new C2378n(DayOfWeek.m567t(mo711z(enumC2365a6).m849a(((Long) map.remove(enumC2365a6)).longValue(), enumC2365a6)).getValue(), 0));
                        if (enumC2350z != EnumC2350z.STRICT || interfaceC2287bMo666j.mo570g(enumC2365a3) == iM849a5) {
                            return interfaceC2287bMo666j;
                        }
                        C2351g.m796a("Strict mode rejected resolved date as it is in a different month");
                        return null;
                    }
                }
            }
            EnumC2365a enumC2365a7 = EnumC2365a.DAY_OF_YEAR;
            if (map.containsKey(enumC2365a7)) {
                int iM849a6 = mo711z(enumC2365a2).m849a(((Long) map.remove(enumC2365a2)).longValue(), enumC2365a2);
                if (enumC2350z != EnumC2350z.LENIENT) {
                    return mo710x(iM849a6, mo711z(enumC2365a7).m849a(((Long) map.remove(enumC2365a7)).longValue(), enumC2365a7));
                }
                return mo710x(iM849a6, 1).mo583b(Math.subtractExact(((Long) map.remove(enumC2365a7)).longValue(), 1L), (InterfaceC2382r) ChronoUnit.DAYS);
            }
            EnumC2365a enumC2365a8 = EnumC2365a.ALIGNED_WEEK_OF_YEAR;
            if (map.containsKey(enumC2365a8)) {
                EnumC2365a enumC2365a9 = EnumC2365a.ALIGNED_DAY_OF_WEEK_IN_YEAR;
                if (map.containsKey(enumC2365a9)) {
                    int iM849a7 = mo711z(enumC2365a2).m849a(((Long) map.remove(enumC2365a2)).longValue(), enumC2365a2);
                    if (enumC2350z == EnumC2350z.LENIENT) {
                        return mo710x(iM849a7, 1).mo583b(Math.subtractExact(((Long) map.remove(enumC2365a8)).longValue(), 1L), (InterfaceC2382r) ChronoUnit.WEEKS).mo583b(Math.subtractExact(((Long) map.remove(enumC2365a9)).longValue(), 1L), (InterfaceC2382r) ChronoUnit.DAYS);
                    }
                    int iM849a8 = mo711z(enumC2365a8).m849a(((Long) map.remove(enumC2365a8)).longValue(), enumC2365a8);
                    InterfaceC2287b interfaceC2287bMo583b2 = mo710x(iM849a7, 1).mo583b((mo711z(enumC2365a9).m849a(((Long) map.remove(enumC2365a9)).longValue(), enumC2365a9) - 1) + ((iM849a8 - 1) * 7), (InterfaceC2382r) ChronoUnit.DAYS);
                    if (enumC2350z != EnumC2350z.STRICT || interfaceC2287bMo583b2.mo570g(enumC2365a2) == iM849a7) {
                        return interfaceC2287bMo583b2;
                    }
                    C2351g.m796a("Strict mode rejected resolved date as it is in a different year");
                    return null;
                }
                EnumC2365a enumC2365a10 = EnumC2365a.DAY_OF_WEEK;
                if (map.containsKey(enumC2365a10)) {
                    int iM849a9 = mo711z(enumC2365a2).m849a(((Long) map.remove(enumC2365a2)).longValue(), enumC2365a2);
                    if (enumC2350z == EnumC2350z.LENIENT) {
                        return m681G(mo710x(iM849a9, 1), 0L, Math.subtractExact(((Long) map.remove(enumC2365a8)).longValue(), 1L), Math.subtractExact(((Long) map.remove(enumC2365a10)).longValue(), 1L));
                    }
                    InterfaceC2287b interfaceC2287bMo666j2 = mo710x(iM849a9, 1).mo583b((mo711z(enumC2365a8).m849a(((Long) map.remove(enumC2365a8)).longValue(), enumC2365a8) - 1) * 7, (InterfaceC2382r) ChronoUnit.DAYS).mo666j(new C2378n(DayOfWeek.m567t(mo711z(enumC2365a10).m849a(((Long) map.remove(enumC2365a10)).longValue(), enumC2365a10)).getValue(), 0));
                    if (enumC2350z != EnumC2350z.STRICT || interfaceC2287bMo666j2.mo570g(enumC2365a2) == iM849a9) {
                        return interfaceC2287bMo666j2;
                    }
                    C2351g.m796a("Strict mode rejected resolved date as it is in a different year");
                    return null;
                }
            }
        }
        return null;
    }

    /* JADX INFO: renamed from: V */
    public InterfaceC2287b mo686V(Map map, EnumC2350z enumC2350z) {
        EnumC2365a enumC2365a = EnumC2365a.YEAR_OF_ERA;
        Long l = (Long) map.remove(enumC2365a);
        if (l == null) {
            EnumC2365a enumC2365a2 = EnumC2365a.ERA;
            if (!map.containsKey(enumC2365a2)) {
                return null;
            }
            mo711z(enumC2365a2).m850b(((Long) map.get(enumC2365a2)).longValue(), enumC2365a2);
            return null;
        }
        Long l2 = (Long) map.remove(EnumC2365a.ERA);
        int iM849a = enumC2350z != EnumC2350z.LENIENT ? mo711z(enumC2365a).m849a(l.longValue(), enumC2365a) : Math.toIntExact(l.longValue());
        if (l2 != null) {
            m682t(map, EnumC2365a.YEAR, mo703D(mo702C(mo711z(r2).m849a(l2.longValue(), r2)), iM849a));
            return null;
        }
        EnumC2365a enumC2365a3 = EnumC2365a.YEAR;
        if (map.containsKey(enumC2365a3)) {
            m682t(map, enumC2365a3, mo703D(mo710x(mo711z(enumC2365a3).m849a(((Long) map.get(enumC2365a3)).longValue(), enumC2365a3), 1).mo597N(), iM849a));
            return null;
        }
        if (enumC2350z == EnumC2350z.STRICT) {
            map.put(enumC2365a, l);
            return null;
        }
        if (mo701A().isEmpty()) {
            m682t(map, enumC2365a3, iM849a);
            return null;
        }
        m682t(map, enumC2365a3, mo703D((InterfaceC2305l) r9.get(r9.size() - 1), iM849a));
        return null;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        return getId().compareTo(((InterfaceC2304k) obj).getId());
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof AbstractC2285a) && getId().compareTo(((AbstractC2285a) obj).getId()) == 0;
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    public final int hashCode() {
        return getId().hashCode() ^ getClass().hashCode();
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    public final String toString() {
        return getId();
    }
}
