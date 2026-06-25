package p026j$.time.temporal;

import java.util.Map;
import p026j$.time.C2351g;
import p026j$.time.DayOfWeek;
import p026j$.time.LocalDate;
import p026j$.time.chrono.C2311r;
import p026j$.time.chrono.InterfaceC2304k;
import p026j$.time.format.C2349y;
import p026j$.time.format.EnumC2350z;

/* JADX INFO: renamed from: j$.time.temporal.g */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public abstract class EnumC2371g extends Enum implements InterfaceC2380p {
    public static final EnumC2371g DAY_OF_QUARTER;
    public static final EnumC2371g QUARTER_OF_YEAR;
    public static final EnumC2371g WEEK_BASED_YEAR;
    public static final EnumC2371g WEEK_OF_WEEK_BASED_YEAR;

    /* JADX INFO: renamed from: a */
    public static final int[] f944a;

    /* JADX INFO: renamed from: b */
    public static final /* synthetic */ EnumC2371g[] f945b;

    static {
        EnumC2371g enumC2371g = new EnumC2371g() { // from class: j$.time.temporal.c
            @Override // p026j$.time.temporal.InterfaceC2380p
            /* JADX INFO: renamed from: B */
            public final C2384t mo835B(InterfaceC2376l interfaceC2376l) {
                if (!mo841t(interfaceC2376l)) {
                    throw new C2383s("Unsupported field: DayOfQuarter");
                }
                long jMo572k = interfaceC2376l.mo572k(EnumC2371g.QUARTER_OF_YEAR);
                if (jMo572k != 1) {
                    return jMo572k == 2 ? C2384t.m847f(1L, 91L) : (jMo572k == 3 || jMo572k == 4) ? C2384t.m847f(1L, 92L) : mo836I();
                }
                long jMo572k2 = interfaceC2376l.mo572k(EnumC2365a.YEAR);
                C2311r.f803c.getClass();
                return C2311r.m733X(jMo572k2) ? C2384t.m847f(1L, 91L) : C2384t.m847f(1L, 90L);
            }

            @Override // p026j$.time.temporal.InterfaceC2380p
            /* JADX INFO: renamed from: G */
            public final InterfaceC2376l mo842G(Map map, C2349y c2349y, EnumC2350z enumC2350z) {
                LocalDate localDateM593of;
                long jSubtractExact;
                EnumC2365a enumC2365a = EnumC2365a.YEAR;
                Long l = (Long) map.get(enumC2365a);
                InterfaceC2380p interfaceC2380p = EnumC2371g.QUARTER_OF_YEAR;
                Long l2 = (Long) map.get(interfaceC2380p);
                if (l != null && l2 != null) {
                    int iM849a = enumC2365a.f942b.m849a(l.longValue(), enumC2365a);
                    long jLongValue = ((Long) map.get(EnumC2371g.DAY_OF_QUARTER)).longValue();
                    EnumC2371g enumC2371g2 = AbstractC2373i.f948a;
                    if (InterfaceC2304k.m719s(c2349y).equals(C2311r.f803c)) {
                        if (enumC2350z == EnumC2350z.LENIENT) {
                            localDateM593of = LocalDate.m593of(iM849a, 1, 1).m609g0(Math.multiplyExact(Math.subtractExact(l2.longValue(), 1L), 3L));
                            jSubtractExact = Math.subtractExact(jLongValue, 1L);
                        } else {
                            localDateM593of = LocalDate.m593of(iM849a, ((interfaceC2380p.mo836I().m849a(l2.longValue(), interfaceC2380p) - 1) * 3) + 1, 1);
                            if (jLongValue < 1 || jLongValue > 90) {
                                if (enumC2350z == EnumC2350z.STRICT) {
                                    mo835B(localDateM593of).m850b(jLongValue, this);
                                } else {
                                    mo836I().m850b(jLongValue, this);
                                }
                            }
                            jSubtractExact = jLongValue - 1;
                        }
                        map.remove(this);
                        map.remove(enumC2365a);
                        map.remove(interfaceC2380p);
                        return localDateM593of.plusDays(jSubtractExact);
                    }
                    C2351g.m796a("Resolve requires IsoChronology");
                }
                return null;
            }

            @Override // p026j$.time.temporal.InterfaceC2380p
            /* JADX INFO: renamed from: I */
            public final C2384t mo836I() {
                return C2384t.m848g(90L, 92L);
            }

            @Override // p026j$.time.temporal.InterfaceC2380p
            /* JADX INFO: renamed from: P */
            public final long mo837P(InterfaceC2376l interfaceC2376l) {
                if (!mo841t(interfaceC2376l)) {
                    throw new C2383s("Unsupported field: DayOfQuarter");
                }
                int iMo570g = interfaceC2376l.mo570g(EnumC2365a.DAY_OF_YEAR);
                int iMo570g2 = interfaceC2376l.mo570g(EnumC2365a.MONTH_OF_YEAR);
                long jMo572k = interfaceC2376l.mo572k(EnumC2365a.YEAR);
                int i = (iMo570g2 - 1) / 3;
                C2311r.f803c.getClass();
                return iMo570g - EnumC2371g.f944a[i + (C2311r.m733X(jMo572k) ? 4 : 0)];
            }

            @Override // p026j$.time.temporal.InterfaceC2380p
            /* JADX INFO: renamed from: V */
            public final Temporal mo838V(Temporal temporal, long j) {
                long jMo837P = mo837P(temporal);
                mo836I().m850b(j, this);
                EnumC2365a enumC2365a = EnumC2365a.DAY_OF_YEAR;
                return temporal.mo582a((j - jMo837P) + temporal.mo572k(enumC2365a), enumC2365a);
            }

            @Override // p026j$.time.temporal.InterfaceC2380p
            /* JADX INFO: renamed from: t */
            public final boolean mo841t(InterfaceC2376l interfaceC2376l) {
                if (!interfaceC2376l.mo571i(EnumC2365a.DAY_OF_YEAR) || !interfaceC2376l.mo571i(EnumC2365a.MONTH_OF_YEAR) || !interfaceC2376l.mo571i(EnumC2365a.YEAR)) {
                    return false;
                }
                EnumC2371g enumC2371g2 = AbstractC2373i.f948a;
                return InterfaceC2304k.m719s(interfaceC2376l).equals(C2311r.f803c);
            }

            @Override // java.lang.Enum
            public final String toString() {
                return "DayOfQuarter";
            }
        };
        DAY_OF_QUARTER = enumC2371g;
        EnumC2371g enumC2371g2 = new EnumC2371g() { // from class: j$.time.temporal.d
            @Override // p026j$.time.temporal.InterfaceC2380p
            /* JADX INFO: renamed from: B */
            public final C2384t mo835B(InterfaceC2376l interfaceC2376l) {
                if (mo841t(interfaceC2376l)) {
                    return mo836I();
                }
                throw new C2383s("Unsupported field: QuarterOfYear");
            }

            @Override // p026j$.time.temporal.InterfaceC2380p
            /* JADX INFO: renamed from: I */
            public final C2384t mo836I() {
                return C2384t.m847f(1L, 4L);
            }

            @Override // p026j$.time.temporal.InterfaceC2380p
            /* JADX INFO: renamed from: P */
            public final long mo837P(InterfaceC2376l interfaceC2376l) {
                if (mo841t(interfaceC2376l)) {
                    return (interfaceC2376l.mo572k(EnumC2365a.MONTH_OF_YEAR) + 2) / 3;
                }
                throw new C2383s("Unsupported field: QuarterOfYear");
            }

            @Override // p026j$.time.temporal.InterfaceC2380p
            /* JADX INFO: renamed from: V */
            public final Temporal mo838V(Temporal temporal, long j) {
                long jMo837P = mo837P(temporal);
                mo836I().m850b(j, this);
                EnumC2365a enumC2365a = EnumC2365a.MONTH_OF_YEAR;
                return temporal.mo582a(((j - jMo837P) * 3) + temporal.mo572k(enumC2365a), enumC2365a);
            }

            @Override // p026j$.time.temporal.InterfaceC2380p
            /* JADX INFO: renamed from: t */
            public final boolean mo841t(InterfaceC2376l interfaceC2376l) {
                if (!interfaceC2376l.mo571i(EnumC2365a.MONTH_OF_YEAR)) {
                    return false;
                }
                EnumC2371g enumC2371g3 = AbstractC2373i.f948a;
                return InterfaceC2304k.m719s(interfaceC2376l).equals(C2311r.f803c);
            }

            @Override // java.lang.Enum
            public final String toString() {
                return "QuarterOfYear";
            }
        };
        QUARTER_OF_YEAR = enumC2371g2;
        EnumC2371g enumC2371g3 = new EnumC2371g() { // from class: j$.time.temporal.e
            @Override // p026j$.time.temporal.InterfaceC2380p
            /* JADX INFO: renamed from: B */
            public final C2384t mo835B(InterfaceC2376l interfaceC2376l) {
                if (mo841t(interfaceC2376l)) {
                    return EnumC2371g.m846a0(LocalDate.m588G(interfaceC2376l));
                }
                throw new C2383s("Unsupported field: WeekOfWeekBasedYear");
            }

            @Override // p026j$.time.temporal.InterfaceC2380p
            /* JADX INFO: renamed from: G */
            public final InterfaceC2376l mo842G(Map map, C2349y c2349y, EnumC2350z enumC2350z) {
                LocalDate localDateMo582a;
                long j;
                InterfaceC2380p interfaceC2380p = EnumC2371g.WEEK_BASED_YEAR;
                Long l = (Long) map.get(interfaceC2380p);
                EnumC2365a enumC2365a = EnumC2365a.DAY_OF_WEEK;
                Long l2 = (Long) map.get(enumC2365a);
                if (l != null && l2 != null) {
                    int iM849a = interfaceC2380p.mo836I().m849a(l.longValue(), interfaceC2380p);
                    long jLongValue = ((Long) map.get(EnumC2371g.WEEK_OF_WEEK_BASED_YEAR)).longValue();
                    EnumC2371g enumC2371g4 = AbstractC2373i.f948a;
                    if (InterfaceC2304k.m719s(c2349y).equals(C2311r.f803c)) {
                        LocalDate localDateM593of = LocalDate.m593of(iM849a, 1, 4);
                        if (enumC2350z == EnumC2350z.LENIENT) {
                            long jLongValue2 = l2.longValue();
                            if (jLongValue2 > 7) {
                                long j2 = jLongValue2 - 1;
                                localDateM593of = localDateM593of.m610h0(j2 / 7);
                                j = j2 % 7;
                            } else {
                                if (jLongValue2 < 1) {
                                    localDateM593of = localDateM593of.m610h0(Math.subtractExact(jLongValue2, 7L) / 7);
                                    j = (jLongValue2 + 6) % 7;
                                }
                                localDateMo582a = localDateM593of.m610h0(Math.subtractExact(jLongValue, 1L)).mo582a(jLongValue2, enumC2365a);
                            }
                            jLongValue2 = j + 1;
                            localDateMo582a = localDateM593of.m610h0(Math.subtractExact(jLongValue, 1L)).mo582a(jLongValue2, enumC2365a);
                        } else {
                            int iM849a2 = enumC2365a.f942b.m849a(l2.longValue(), enumC2365a);
                            if (jLongValue < 1 || jLongValue > 52) {
                                if (enumC2350z == EnumC2350z.STRICT) {
                                    EnumC2371g.m846a0(localDateM593of).m850b(jLongValue, this);
                                } else {
                                    mo836I().m850b(jLongValue, this);
                                }
                            }
                            localDateMo582a = localDateM593of.m610h0(jLongValue - 1).mo582a(iM849a2, enumC2365a);
                        }
                        map.remove(this);
                        map.remove(interfaceC2380p);
                        map.remove(enumC2365a);
                        return localDateMo582a;
                    }
                    C2351g.m796a("Resolve requires IsoChronology");
                }
                return null;
            }

            @Override // p026j$.time.temporal.InterfaceC2380p
            /* JADX INFO: renamed from: I */
            public final C2384t mo836I() {
                return C2384t.m848g(52L, 53L);
            }

            @Override // p026j$.time.temporal.InterfaceC2380p
            /* JADX INFO: renamed from: P */
            public final long mo837P(InterfaceC2376l interfaceC2376l) {
                if (mo841t(interfaceC2376l)) {
                    return EnumC2371g.m843X(LocalDate.m588G(interfaceC2376l));
                }
                throw new C2383s("Unsupported field: WeekOfWeekBasedYear");
            }

            @Override // p026j$.time.temporal.InterfaceC2380p
            /* JADX INFO: renamed from: V */
            public final Temporal mo838V(Temporal temporal, long j) {
                mo836I().m850b(j, this);
                return temporal.mo583b(Math.subtractExact(j, mo837P(temporal)), ChronoUnit.WEEKS);
            }

            @Override // p026j$.time.temporal.InterfaceC2380p
            /* JADX INFO: renamed from: t */
            public final boolean mo841t(InterfaceC2376l interfaceC2376l) {
                if (!interfaceC2376l.mo571i(EnumC2365a.EPOCH_DAY)) {
                    return false;
                }
                EnumC2371g enumC2371g4 = AbstractC2373i.f948a;
                return InterfaceC2304k.m719s(interfaceC2376l).equals(C2311r.f803c);
            }

            @Override // java.lang.Enum
            public final String toString() {
                return "WeekOfWeekBasedYear";
            }
        };
        WEEK_OF_WEEK_BASED_YEAR = enumC2371g3;
        EnumC2371g enumC2371g4 = new EnumC2371g() { // from class: j$.time.temporal.f
            @Override // p026j$.time.temporal.InterfaceC2380p
            /* JADX INFO: renamed from: B */
            public final C2384t mo835B(InterfaceC2376l interfaceC2376l) {
                if (mo841t(interfaceC2376l)) {
                    return EnumC2365a.YEAR.f942b;
                }
                throw new C2383s("Unsupported field: WeekBasedYear");
            }

            @Override // p026j$.time.temporal.InterfaceC2380p
            /* JADX INFO: renamed from: I */
            public final C2384t mo836I() {
                return EnumC2365a.YEAR.f942b;
            }

            @Override // p026j$.time.temporal.InterfaceC2380p
            /* JADX INFO: renamed from: P */
            public final long mo837P(InterfaceC2376l interfaceC2376l) {
                if (mo841t(interfaceC2376l)) {
                    return EnumC2371g.m844Y(LocalDate.m588G(interfaceC2376l));
                }
                throw new C2383s("Unsupported field: WeekBasedYear");
            }

            @Override // p026j$.time.temporal.InterfaceC2380p
            /* JADX INFO: renamed from: V */
            public final Temporal mo838V(Temporal temporal, long j) {
                if (!mo841t(temporal)) {
                    throw new C2383s("Unsupported field: WeekBasedYear");
                }
                int iM849a = EnumC2365a.YEAR.f942b.m849a(j, EnumC2371g.WEEK_BASED_YEAR);
                LocalDate localDateM588G = LocalDate.m588G(temporal);
                int iMo570g = localDateM588G.mo570g(EnumC2365a.DAY_OF_WEEK);
                int iM843X = EnumC2371g.m843X(localDateM588G);
                if (iM843X == 53 && EnumC2371g.m845Z(iM849a) == 52) {
                    iM843X = 52;
                }
                return temporal.mo666j(LocalDate.m593of(iM849a, 1, 4).plusDays(((iM843X - 1) * 7) + (iMo570g - r3.mo570g(r6))));
            }

            @Override // p026j$.time.temporal.InterfaceC2380p
            /* JADX INFO: renamed from: t */
            public final boolean mo841t(InterfaceC2376l interfaceC2376l) {
                if (!interfaceC2376l.mo571i(EnumC2365a.EPOCH_DAY)) {
                    return false;
                }
                EnumC2371g enumC2371g5 = AbstractC2373i.f948a;
                return InterfaceC2304k.m719s(interfaceC2376l).equals(C2311r.f803c);
            }

            @Override // java.lang.Enum
            public final String toString() {
                return "WeekBasedYear";
            }
        };
        WEEK_BASED_YEAR = enumC2371g4;
        f945b = new EnumC2371g[]{enumC2371g, enumC2371g2, enumC2371g3, enumC2371g4};
        f944a = new int[]{0, 90, 181, 273, 0, 91, 182, 274};
    }

    /* JADX INFO: renamed from: X */
    public static int m843X(LocalDate localDate) {
        int iOrdinal = localDate.m598P().ordinal();
        int iM601V = localDate.m601V() - 1;
        int i = (3 - iOrdinal) + iM601V;
        int i2 = i - ((i / 7) * 7);
        int i3 = i2 - 3;
        if (i3 < -3) {
            i3 = i2 + 4;
        }
        if (iM601V < i3) {
            if (localDate.m601V() != 180) {
                localDate = LocalDate.m591e0(localDate.f729a, 180);
            }
            return (int) m846a0(localDate.m611i0(-1L)).f968d;
        }
        int i4 = ((iM601V - i3) / 7) + 1;
        if (i4 != 53 || i3 == -3 || (i3 == -2 && localDate.m604Z())) {
            return i4;
        }
        return 1;
    }

    /* JADX INFO: renamed from: Y */
    public static int m844Y(LocalDate localDate) {
        int year = localDate.getYear();
        int iM601V = localDate.m601V();
        if (iM601V <= 3) {
            return iM601V - localDate.m598P().ordinal() < -2 ? year - 1 : year;
        }
        if (iM601V >= 363) {
            return ((iM601V - 363) - (localDate.m604Z() ? 1 : 0)) - localDate.m598P().ordinal() >= 0 ? year + 1 : year;
        }
        return year;
    }

    /* JADX INFO: renamed from: Z */
    public static int m845Z(int i) {
        LocalDate localDateM593of = LocalDate.m593of(i, 1, 1);
        if (localDateM593of.m598P() != DayOfWeek.THURSDAY) {
            return (localDateM593of.m598P() == DayOfWeek.WEDNESDAY && localDateM593of.m604Z()) ? 53 : 52;
        }
        return 53;
    }

    /* JADX INFO: renamed from: a0 */
    public static C2384t m846a0(LocalDate localDate) {
        return C2384t.m847f(1L, m845Z(m844Y(localDate)));
    }

    public static EnumC2371g valueOf(String str) {
        return (EnumC2371g) Enum.valueOf(EnumC2371g.class, str);
    }

    public static EnumC2371g[] values() {
        return (EnumC2371g[]) f945b.clone();
    }

    @Override // p026j$.time.temporal.InterfaceC2380p
    public final boolean isDateBased() {
        return true;
    }
}
