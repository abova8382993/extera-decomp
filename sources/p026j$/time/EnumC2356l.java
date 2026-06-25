package p026j$.time;

import com.android.p006dx.p009io.Opcodes;
import p026j$.time.chrono.C2311r;
import p026j$.time.chrono.InterfaceC2304k;
import p026j$.time.temporal.AbstractC2381q;
import p026j$.time.temporal.C2383s;
import p026j$.time.temporal.C2384t;
import p026j$.time.temporal.ChronoUnit;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2376l;
import p026j$.time.temporal.InterfaceC2377m;
import p026j$.time.temporal.InterfaceC2380p;
import p026j$.time.temporal.Temporal;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: renamed from: j$.time.l */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class EnumC2356l implements InterfaceC2376l, InterfaceC2377m {
    public static final EnumC2356l APRIL;
    public static final EnumC2356l AUGUST;
    public static final EnumC2356l DECEMBER;
    public static final EnumC2356l FEBRUARY;
    public static final EnumC2356l JANUARY;
    public static final EnumC2356l JULY;
    public static final EnumC2356l JUNE;
    public static final EnumC2356l MARCH;
    public static final EnumC2356l MAY;
    public static final EnumC2356l NOVEMBER;
    public static final EnumC2356l OCTOBER;
    public static final EnumC2356l SEPTEMBER;

    /* JADX INFO: renamed from: a */
    public static final EnumC2356l[] f921a;

    /* JADX INFO: renamed from: b */
    public static final /* synthetic */ EnumC2356l[] f922b;

    static {
        EnumC2356l enumC2356l = new EnumC2356l("JANUARY", 0);
        JANUARY = enumC2356l;
        EnumC2356l enumC2356l2 = new EnumC2356l("FEBRUARY", 1);
        FEBRUARY = enumC2356l2;
        EnumC2356l enumC2356l3 = new EnumC2356l("MARCH", 2);
        MARCH = enumC2356l3;
        EnumC2356l enumC2356l4 = new EnumC2356l("APRIL", 3);
        APRIL = enumC2356l4;
        EnumC2356l enumC2356l5 = new EnumC2356l("MAY", 4);
        MAY = enumC2356l5;
        EnumC2356l enumC2356l6 = new EnumC2356l("JUNE", 5);
        JUNE = enumC2356l6;
        EnumC2356l enumC2356l7 = new EnumC2356l("JULY", 6);
        JULY = enumC2356l7;
        EnumC2356l enumC2356l8 = new EnumC2356l("AUGUST", 7);
        AUGUST = enumC2356l8;
        EnumC2356l enumC2356l9 = new EnumC2356l("SEPTEMBER", 8);
        SEPTEMBER = enumC2356l9;
        EnumC2356l enumC2356l10 = new EnumC2356l("OCTOBER", 9);
        OCTOBER = enumC2356l10;
        EnumC2356l enumC2356l11 = new EnumC2356l("NOVEMBER", 10);
        NOVEMBER = enumC2356l11;
        EnumC2356l enumC2356l12 = new EnumC2356l("DECEMBER", 11);
        DECEMBER = enumC2356l12;
        f922b = new EnumC2356l[]{enumC2356l, enumC2356l2, enumC2356l3, enumC2356l4, enumC2356l5, enumC2356l6, enumC2356l7, enumC2356l8, enumC2356l9, enumC2356l10, enumC2356l11, enumC2356l12};
        f921a = values();
    }

    /* JADX INFO: renamed from: I */
    public static EnumC2356l m822I(int i) {
        if (i >= 1 && i <= 12) {
            return f921a[i - 1];
        }
        C2351g.m797b("Invalid value for MonthOfYear: ", i);
        return null;
    }

    public static EnumC2356l valueOf(String str) {
        return (EnumC2356l) Enum.valueOf(EnumC2356l.class, str);
    }

    public static EnumC2356l[] values() {
        return (EnumC2356l[]) f922b.clone();
    }

    /* JADX INFO: renamed from: B */
    public final int m823B(boolean z) {
        int i = AbstractC2355k.f920a[ordinal()];
        return i != 1 ? (i == 2 || i == 3 || i == 4 || i == 5) ? 30 : 31 : z ? 29 : 28;
    }

    /* JADX INFO: renamed from: G */
    public final int m824G() {
        int i = AbstractC2355k.f920a[ordinal()];
        if (i != 1) {
            return (i == 2 || i == 3 || i == 4 || i == 5) ? 30 : 31;
        }
        return 29;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: d */
    public final Object mo568d(C2388x c2388x) {
        return c2388x == AbstractC2381q.f959b ? C2311r.f803c : c2388x == AbstractC2381q.f960c ? ChronoUnit.MONTHS : super.mo568d(c2388x);
    }

    @Override // p026j$.time.temporal.InterfaceC2377m
    /* JADX INFO: renamed from: e */
    public final Temporal mo569e(Temporal temporal) {
        if (InterfaceC2304k.m719s(temporal).equals(C2311r.f803c)) {
            return temporal.mo582a(getValue(), EnumC2365a.MONTH_OF_YEAR);
        }
        C2351g.m796a("Adjustment only supported on ISO date-time");
        return null;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: g */
    public final int mo570g(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p == EnumC2365a.MONTH_OF_YEAR ? getValue() : super.mo570g(interfaceC2380p);
    }

    public final int getValue() {
        return ordinal() + 1;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: i */
    public final boolean mo571i(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p instanceof EnumC2365a ? interfaceC2380p == EnumC2365a.MONTH_OF_YEAR : interfaceC2380p != null && interfaceC2380p.mo841t(this);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: k */
    public final long mo572k(InterfaceC2380p interfaceC2380p) {
        if (interfaceC2380p == EnumC2365a.MONTH_OF_YEAR) {
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
        return interfaceC2380p == EnumC2365a.MONTH_OF_YEAR ? interfaceC2380p.mo836I() : super.mo573m(interfaceC2380p);
    }

    /* JADX INFO: renamed from: t */
    public final int m825t(boolean z) {
        switch (AbstractC2355k.f920a[ordinal()]) {
            case 1:
                return 32;
            case 2:
                return (z ? 1 : 0) + 91;
            case 3:
                return (z ? 1 : 0) + 152;
            case 4:
                return (z ? 1 : 0) + 244;
            case 5:
                return (z ? 1 : 0) + 305;
            case 6:
                return 1;
            case 7:
                return (z ? 1 : 0) + 60;
            case 8:
                return (z ? 1 : 0) + 121;
            case 9:
                return (z ? 1 : 0) + 182;
            case 10:
                return (z ? 1 : 0) + Opcodes.AND_INT_LIT16;
            case 11:
                return (z ? 1 : 0) + 274;
            default:
                return (z ? 1 : 0) + 335;
        }
    }
}
