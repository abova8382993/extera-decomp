package p026j$.time.format;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: renamed from: j$.time.format.z */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class EnumC2350z {
    public static final EnumC2350z LENIENT;
    public static final EnumC2350z SMART;
    public static final EnumC2350z STRICT;

    /* JADX INFO: renamed from: a */
    public static final /* synthetic */ EnumC2350z[] f908a;

    static {
        EnumC2350z enumC2350z = new EnumC2350z("STRICT", 0);
        STRICT = enumC2350z;
        EnumC2350z enumC2350z2 = new EnumC2350z("SMART", 1);
        SMART = enumC2350z2;
        EnumC2350z enumC2350z3 = new EnumC2350z("LENIENT", 2);
        LENIENT = enumC2350z3;
        f908a = new EnumC2350z[]{enumC2350z, enumC2350z2, enumC2350z3};
    }

    public static EnumC2350z valueOf(String str) {
        return (EnumC2350z) Enum.valueOf(EnumC2350z.class, str);
    }

    public static EnumC2350z[] values() {
        return (EnumC2350z[]) f908a.clone();
    }
}
