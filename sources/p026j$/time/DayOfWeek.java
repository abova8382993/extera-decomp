package p026j$.time;

import java.util.Locale;
import java.util.Objects;
import p026j$.time.format.C2338n;
import p026j$.time.format.C2341q;
import p026j$.time.format.C2347w;
import p026j$.time.format.EnumC2350z;
import p026j$.time.format.TextStyle;
import p026j$.time.temporal.AbstractC2381q;
import p026j$.time.temporal.C2383s;
import p026j$.time.temporal.C2384t;
import p026j$.time.temporal.ChronoUnit;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2376l;
import p026j$.time.temporal.InterfaceC2377m;
import p026j$.time.temporal.InterfaceC2380p;
import p026j$.time.temporal.Temporal;

/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class DayOfWeek extends Enum<DayOfWeek> implements InterfaceC2376l, InterfaceC2377m {
    public static final DayOfWeek FRIDAY;
    public static final DayOfWeek MONDAY;
    public static final DayOfWeek SATURDAY;
    public static final DayOfWeek SUNDAY;
    public static final DayOfWeek THURSDAY;
    public static final DayOfWeek TUESDAY;
    public static final DayOfWeek WEDNESDAY;

    /* JADX INFO: renamed from: a */
    public static final DayOfWeek[] f719a;

    /* JADX INFO: renamed from: b */
    public static final /* synthetic */ DayOfWeek[] f720b;

    static {
        DayOfWeek dayOfWeek = new DayOfWeek("MONDAY", 0);
        MONDAY = dayOfWeek;
        DayOfWeek dayOfWeek2 = new DayOfWeek("TUESDAY", 1);
        TUESDAY = dayOfWeek2;
        DayOfWeek dayOfWeek3 = new DayOfWeek("WEDNESDAY", 2);
        WEDNESDAY = dayOfWeek3;
        DayOfWeek dayOfWeek4 = new DayOfWeek("THURSDAY", 3);
        THURSDAY = dayOfWeek4;
        DayOfWeek dayOfWeek5 = new DayOfWeek("FRIDAY", 4);
        FRIDAY = dayOfWeek5;
        DayOfWeek dayOfWeek6 = new DayOfWeek("SATURDAY", 5);
        SATURDAY = dayOfWeek6;
        DayOfWeek dayOfWeek7 = new DayOfWeek("SUNDAY", 6);
        SUNDAY = dayOfWeek7;
        f720b = new DayOfWeek[]{dayOfWeek, dayOfWeek2, dayOfWeek3, dayOfWeek4, dayOfWeek5, dayOfWeek6, dayOfWeek7};
        f719a = values();
    }

    /* JADX INFO: renamed from: t */
    public static DayOfWeek m567t(int i) {
        if (i >= 1 && i <= 7) {
            return f719a[i - 1];
        }
        C2351g.m797b("Invalid value for DayOfWeek: ", i);
        return null;
    }

    public static DayOfWeek valueOf(String str) {
        return (DayOfWeek) Enum.valueOf(DayOfWeek.class, str);
    }

    public static DayOfWeek[] values() {
        return (DayOfWeek[]) f720b.clone();
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: d */
    public final Object mo568d(C2388x c2388x) {
        return c2388x == AbstractC2381q.f960c ? ChronoUnit.DAYS : super.mo568d(c2388x);
    }

    @Override // p026j$.time.temporal.InterfaceC2377m
    /* JADX INFO: renamed from: e */
    public final Temporal mo569e(Temporal temporal) {
        return temporal.mo582a(getValue(), EnumC2365a.DAY_OF_WEEK);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: g */
    public final int mo570g(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p == EnumC2365a.DAY_OF_WEEK ? getValue() : super.mo570g(interfaceC2380p);
    }

    public String getDisplayName(TextStyle textStyle, Locale locale) {
        C2341q c2341q = new C2341q();
        EnumC2365a enumC2365a = EnumC2365a.DAY_OF_WEEK;
        Objects.requireNonNull(enumC2365a, "field");
        Objects.requireNonNull(textStyle, "textStyle");
        c2341q.m766b(new C2338n(enumC2365a, textStyle, C2347w.f898c));
        return c2341q.m776l(locale, EnumC2350z.SMART, null).m742a(this);
    }

    public final int getValue() {
        return ordinal() + 1;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: i */
    public final boolean mo571i(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p instanceof EnumC2365a ? interfaceC2380p == EnumC2365a.DAY_OF_WEEK : interfaceC2380p != null && interfaceC2380p.mo841t(this);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: k */
    public final long mo572k(InterfaceC2380p interfaceC2380p) {
        if (interfaceC2380p == EnumC2365a.DAY_OF_WEEK) {
            return getValue();
        }
        if (interfaceC2380p instanceof EnumC2365a) {
            throw new C2383s(AbstractC2320d.m741a("Unsupported field: ", interfaceC2380p));
        }
        return interfaceC2380p.mo837P(this);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: m */
    public final C2384t mo573m(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p == EnumC2365a.DAY_OF_WEEK ? interfaceC2380p.mo836I() : super.mo573m(interfaceC2380p);
    }
}
