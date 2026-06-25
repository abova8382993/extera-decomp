package p026j$.time.chrono;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import p026j$.time.AbstractC2283b;
import p026j$.time.C2284c;
import p026j$.time.C2351g;
import p026j$.time.C2388x;
import p026j$.time.Instant;
import p026j$.time.LocalDate;
import p026j$.time.ZoneId;
import p026j$.time.format.EnumC2350z;
import p026j$.time.temporal.C2384t;
import p026j$.time.temporal.ChronoUnit;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2376l;

/* JADX INFO: renamed from: j$.time.chrono.u */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2314u extends AbstractC2285a implements Serializable {

    /* JADX INFO: renamed from: c */
    public static final C2314u f807c = new C2314u();
    private static final long serialVersionUID = 459996390165777884L;

    private C2314u() {
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: A */
    public final List mo701A() {
        C2317x[] c2317xArr = C2317x.f814e;
        return AbstractC2283b.m674c((C2317x[]) Arrays.copyOf(c2317xArr, c2317xArr.length));
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: C */
    public final InterfaceC2305l mo702C(int i) {
        return C2317x.m739t(i);
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: D */
    public final int mo703D(InterfaceC2305l interfaceC2305l, int i) {
        if (!(interfaceC2305l instanceof C2317x)) {
            throw new ClassCastException("Era must be JapaneseEra");
        }
        C2317x c2317x = (C2317x) interfaceC2305l;
        int year = (c2317x.f816b.getYear() + i) - 1;
        if (i == 1 || (year >= -999999999 && year <= 999999999 && year >= c2317x.f816b.getYear() && interfaceC2305l == C2317x.m738r(LocalDate.m593of(year, 1, 1)))) {
            return year;
        }
        C2351g.m796a("Invalid yearOfEra value");
        return 0;
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: H */
    public final InterfaceC2287b mo704H(InterfaceC2376l interfaceC2376l) {
        return interfaceC2376l instanceof C2316w ? (C2316w) interfaceC2376l : new C2316w(LocalDate.m588G(interfaceC2376l));
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: L */
    public final InterfaceC2287b mo705L() {
        return new C2316w(LocalDate.m588G(LocalDate.m589c0(AbstractC2283b.m675d())));
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: Q */
    public final InterfaceC2287b mo706Q(int i, int i2, int i3) {
        return new C2316w(LocalDate.m593of(i, i2, i3));
    }

    @Override // p026j$.time.chrono.AbstractC2285a, p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: S */
    public final InterfaceC2287b mo685S(Map map, EnumC2350z enumC2350z) {
        return (C2316w) super.mo685S(map, enumC2350z);
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: T */
    public final ChronoZonedDateTime mo707T(Instant instant, ZoneId zoneId) {
        return C2303j.m716G(this, instant, zoneId);
    }

    @Override // p026j$.time.chrono.AbstractC2285a
    /* JADX INFO: renamed from: V */
    public final InterfaceC2287b mo686V(Map map, EnumC2350z enumC2350z) {
        C2316w c2316wM737Z;
        EnumC2365a enumC2365a = EnumC2365a.ERA;
        Long l = (Long) map.get(enumC2365a);
        C2317x c2317xM739t = l != null ? C2317x.m739t(mo711z(enumC2365a).m849a(l.longValue(), enumC2365a)) : null;
        EnumC2365a enumC2365a2 = EnumC2365a.YEAR_OF_ERA;
        Long l2 = (Long) map.get(enumC2365a2);
        int iM849a = l2 != null ? mo711z(enumC2365a2).m849a(l2.longValue(), enumC2365a2) : 0;
        if (c2317xM739t == null && l2 != null && !map.containsKey(EnumC2365a.YEAR) && enumC2350z != EnumC2350z.STRICT) {
            C2317x[] c2317xArr = C2317x.f814e;
            c2317xM739t = ((C2317x[]) Arrays.copyOf(c2317xArr, c2317xArr.length))[((C2317x[]) Arrays.copyOf(c2317xArr, c2317xArr.length)).length - 1];
        }
        if (l2 != null && c2317xM739t != null) {
            EnumC2365a enumC2365a3 = EnumC2365a.MONTH_OF_YEAR;
            if (map.containsKey(enumC2365a3)) {
                EnumC2365a enumC2365a4 = EnumC2365a.DAY_OF_MONTH;
                if (map.containsKey(enumC2365a4)) {
                    map.remove(enumC2365a);
                    map.remove(enumC2365a2);
                    if (enumC2350z == EnumC2350z.LENIENT) {
                        return new C2316w(LocalDate.m593of((c2317xM739t.f816b.getYear() + iM849a) - 1, 1, 1)).m734V(Math.subtractExact(((Long) map.remove(enumC2365a3)).longValue(), 1L), ChronoUnit.MONTHS).m734V(Math.subtractExact(((Long) map.remove(enumC2365a4)).longValue(), 1L), ChronoUnit.DAYS);
                    }
                    int iM849a2 = mo711z(enumC2365a3).m849a(((Long) map.remove(enumC2365a3)).longValue(), enumC2365a3);
                    int iM849a3 = mo711z(enumC2365a4).m849a(((Long) map.remove(enumC2365a4)).longValue(), enumC2365a4);
                    if (enumC2350z != EnumC2350z.SMART) {
                        LocalDate localDate = C2316w.f809d;
                        LocalDate localDateM593of = LocalDate.m593of((c2317xM739t.f816b.getYear() + iM849a) - 1, iM849a2, iM849a3);
                        if (!localDateM593of.m603Y(c2317xM739t.f816b) && c2317xM739t == C2317x.m738r(localDateM593of)) {
                            return new C2316w(c2317xM739t, iM849a, localDateM593of);
                        }
                        C2351g.m796a("year, month, and day not valid for Era");
                        return null;
                    }
                    if (iM849a < 1) {
                        C2351g.m797b("Invalid YearOfEra: ", iM849a);
                        return null;
                    }
                    int year = (c2317xM739t.f816b.getYear() + iM849a) - 1;
                    try {
                        c2316wM737Z = new C2316w(LocalDate.m593of(year, iM849a2, iM849a3));
                    } catch (C2284c unused) {
                        c2316wM737Z = new C2316w(LocalDate.m593of(year, iM849a2, 1)).m737Z(new C2388x(2));
                    }
                    if (c2316wM737Z.f811b == c2317xM739t || c2316wM737Z.mo570g(EnumC2365a.YEAR_OF_ERA) <= 1 || iM849a <= 1) {
                        return c2316wM737Z;
                    }
                    throw new C2284c("Invalid YearOfEra for Era: " + c2317xM739t + " " + iM849a);
                }
            }
            EnumC2365a enumC2365a5 = EnumC2365a.DAY_OF_YEAR;
            if (map.containsKey(enumC2365a5)) {
                map.remove(enumC2365a);
                map.remove(enumC2365a2);
                if (enumC2350z == EnumC2350z.LENIENT) {
                    return new C2316w(LocalDate.m591e0((c2317xM739t.f816b.getYear() + iM849a) - 1, 1)).m734V(Math.subtractExact(((Long) map.remove(enumC2365a5)).longValue(), 1L), ChronoUnit.DAYS);
                }
                int iM849a4 = mo711z(enumC2365a5).m849a(((Long) map.remove(enumC2365a5)).longValue(), enumC2365a5);
                LocalDate localDate2 = C2316w.f809d;
                LocalDate localDate3 = c2317xM739t.f816b;
                LocalDate localDateM591e0 = iM849a == 1 ? LocalDate.m591e0(localDate3.getYear(), (c2317xM739t.f816b.m601V() + iM849a4) - 1) : LocalDate.m591e0((localDate3.getYear() + iM849a) - 1, iM849a4);
                if (!localDateM591e0.m603Y(c2317xM739t.f816b) && c2317xM739t == C2317x.m738r(localDateM591e0)) {
                    return new C2316w(c2317xM739t, iM849a, localDateM591e0);
                }
                C2351g.m796a("Invalid parameters");
            }
        }
        return null;
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    public final String getId() {
        return "Japanese";
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: r */
    public final InterfaceC2287b mo708r(long j) {
        return new C2316w(LocalDate.m590d0(j));
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: v */
    public final String mo709v() {
        return "japanese";
    }

    public Object writeReplace() {
        return new C2292d0((byte) 1, this);
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: x */
    public final InterfaceC2287b mo710x(int i, int i2) {
        return new C2316w(LocalDate.m591e0(i, i2));
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: z */
    public final C2384t mo711z(EnumC2365a enumC2365a) {
        switch (AbstractC2313t.f806a[enumC2365a.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
                C2351g.m799d("Unsupported field: ", enumC2365a);
                return null;
            case 5:
                C2317x[] c2317xArr = C2317x.f814e;
                int year = c2317xArr[c2317xArr.length - 1].f816b.getYear();
                int year2 = 1000000000 - c2317xArr[c2317xArr.length - 1].f816b.getYear();
                int year3 = c2317xArr[0].f816b.getYear();
                int i = 1;
                while (true) {
                    C2317x[] c2317xArr2 = C2317x.f814e;
                    if (i >= c2317xArr2.length) {
                        return C2384t.m848g(year2, 999999999 - year);
                    }
                    C2317x c2317x = c2317xArr2[i];
                    year2 = Math.min(year2, (c2317x.f816b.getYear() - year3) + 1);
                    year3 = c2317x.f816b.getYear();
                    i++;
                }
                break;
            case 6:
                C2317x c2317x2 = C2317x.f813d;
                long jMin = EnumC2365a.DAY_OF_YEAR.f942b.f967c;
                for (C2317x c2317x3 : C2317x.f814e) {
                    jMin = Math.min(jMin, ((c2317x3.f816b.m604Z() ? 366 : 365) - c2317x3.f816b.m601V()) + 1);
                    if (c2317x3.m740s() != null) {
                        jMin = Math.min(jMin, c2317x3.m740s().f816b.m601V() - 1);
                    }
                }
                return C2384t.m848g(jMin, EnumC2365a.DAY_OF_YEAR.f942b.f968d);
            case 7:
                return C2384t.m847f(C2316w.f809d.getYear(), 999999999L);
            case 8:
                long j = C2317x.f813d.f815a;
                C2317x[] c2317xArr3 = C2317x.f814e;
                return C2384t.m847f(j, c2317xArr3[c2317xArr3.length - 1].f815a);
            default:
                return enumC2365a.f942b;
        }
    }
}
