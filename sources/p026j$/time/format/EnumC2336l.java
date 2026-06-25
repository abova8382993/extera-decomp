package p026j$.time.format;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: renamed from: j$.time.format.l */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class EnumC2336l implements InterfaceC2329e {
    public static final EnumC2336l INSENSITIVE;
    public static final EnumC2336l LENIENT;
    public static final EnumC2336l SENSITIVE;
    public static final EnumC2336l STRICT;

    /* JADX INFO: renamed from: a */
    public static final /* synthetic */ EnumC2336l[] f863a;

    static {
        EnumC2336l enumC2336l = new EnumC2336l("SENSITIVE", 0);
        SENSITIVE = enumC2336l;
        EnumC2336l enumC2336l2 = new EnumC2336l("INSENSITIVE", 1);
        INSENSITIVE = enumC2336l2;
        EnumC2336l enumC2336l3 = new EnumC2336l("STRICT", 2);
        STRICT = enumC2336l3;
        EnumC2336l enumC2336l4 = new EnumC2336l("LENIENT", 3);
        LENIENT = enumC2336l4;
        f863a = new EnumC2336l[]{enumC2336l, enumC2336l2, enumC2336l3, enumC2336l4};
    }

    public static EnumC2336l valueOf(String str) {
        return (EnumC2336l) Enum.valueOf(EnumC2336l.class, str);
    }

    public static EnumC2336l[] values() {
        return (EnumC2336l[]) f863a.clone();
    }

    @Override // p026j$.time.format.InterfaceC2329e
    /* JADX INFO: renamed from: B */
    public final int mo748B(C2342r c2342r, CharSequence charSequence, int i) {
        int iOrdinal = ordinal();
        if (iOrdinal == 0) {
            c2342r.f884b = true;
            return i;
        }
        if (iOrdinal == 1) {
            c2342r.f884b = false;
            return i;
        }
        if (iOrdinal == 2) {
            c2342r.f885c = true;
            return i;
        }
        if (iOrdinal != 3) {
            return i;
        }
        c2342r.f885c = false;
        return i;
    }

    @Override // p026j$.time.format.InterfaceC2329e
    /* JADX INFO: renamed from: t */
    public final boolean mo749t(C2344t c2344t, StringBuilder sb) {
        return true;
    }

    @Override // java.lang.Enum
    public final String toString() {
        int iOrdinal = ordinal();
        if (iOrdinal == 0) {
            return "ParseCaseSensitive(true)";
        }
        if (iOrdinal == 1) {
            return "ParseCaseSensitive(false)";
        }
        if (iOrdinal == 2) {
            return "ParseStrict(true)";
        }
        if (iOrdinal == 3) {
            return "ParseStrict(false)";
        }
        throw new IllegalStateException("Unreachable");
    }
}
