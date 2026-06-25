package p026j$.time.chrono;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: renamed from: j$.time.chrono.c0 */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class EnumC2290c0 implements InterfaceC2305l {
    public static final EnumC2290c0 BEFORE_ROC;
    public static final EnumC2290c0 ROC;

    /* JADX INFO: renamed from: a */
    public static final /* synthetic */ EnumC2290c0[] f763a;

    static {
        EnumC2290c0 enumC2290c0 = new EnumC2290c0("BEFORE_ROC", 0);
        BEFORE_ROC = enumC2290c0;
        EnumC2290c0 enumC2290c02 = new EnumC2290c0("ROC", 1);
        ROC = enumC2290c02;
        f763a = new EnumC2290c0[]{enumC2290c0, enumC2290c02};
    }

    public static EnumC2290c0 valueOf(String str) {
        return (EnumC2290c0) Enum.valueOf(EnumC2290c0.class, str);
    }

    public static EnumC2290c0[] values() {
        return (EnumC2290c0[]) f763a.clone();
    }

    @Override // p026j$.time.chrono.InterfaceC2305l
    public final int getValue() {
        return ordinal();
    }
}
