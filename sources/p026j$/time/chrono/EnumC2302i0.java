package p026j$.time.chrono;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: renamed from: j$.time.chrono.i0 */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class EnumC2302i0 implements InterfaceC2305l {

    /* JADX INFO: renamed from: BE */
    public static final EnumC2302i0 f780BE;
    public static final EnumC2302i0 BEFORE_BE;

    /* JADX INFO: renamed from: a */
    public static final /* synthetic */ EnumC2302i0[] f781a;

    static {
        EnumC2302i0 enumC2302i0 = new EnumC2302i0("BEFORE_BE", 0);
        BEFORE_BE = enumC2302i0;
        EnumC2302i0 enumC2302i02 = new EnumC2302i0("BE", 1);
        f780BE = enumC2302i02;
        f781a = new EnumC2302i0[]{enumC2302i0, enumC2302i02};
    }

    public static EnumC2302i0 valueOf(String str) {
        return (EnumC2302i0) Enum.valueOf(EnumC2302i0.class, str);
    }

    public static EnumC2302i0[] values() {
        return (EnumC2302i0[]) f781a.clone();
    }

    @Override // p026j$.time.chrono.InterfaceC2305l
    public final int getValue() {
        return ordinal();
    }
}
