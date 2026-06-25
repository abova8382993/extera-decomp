package p026j$.time.format;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: renamed from: j$.time.format.a0 */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class EnumC2324a0 {
    public static final EnumC2324a0 ALWAYS;
    public static final EnumC2324a0 EXCEEDS_PAD;
    public static final EnumC2324a0 NEVER;
    public static final EnumC2324a0 NORMAL;
    public static final EnumC2324a0 NOT_NEGATIVE;

    /* JADX INFO: renamed from: a */
    public static final /* synthetic */ EnumC2324a0[] f833a;

    static {
        EnumC2324a0 enumC2324a0 = new EnumC2324a0("NORMAL", 0);
        NORMAL = enumC2324a0;
        EnumC2324a0 enumC2324a02 = new EnumC2324a0("ALWAYS", 1);
        ALWAYS = enumC2324a02;
        EnumC2324a0 enumC2324a03 = new EnumC2324a0("NEVER", 2);
        NEVER = enumC2324a03;
        EnumC2324a0 enumC2324a04 = new EnumC2324a0("NOT_NEGATIVE", 3);
        NOT_NEGATIVE = enumC2324a04;
        EnumC2324a0 enumC2324a05 = new EnumC2324a0("EXCEEDS_PAD", 4);
        EXCEEDS_PAD = enumC2324a05;
        f833a = new EnumC2324a0[]{enumC2324a0, enumC2324a02, enumC2324a03, enumC2324a04, enumC2324a05};
    }

    public static EnumC2324a0 valueOf(String str) {
        return (EnumC2324a0) Enum.valueOf(EnumC2324a0.class, str);
    }

    public static EnumC2324a0[] values() {
        return (EnumC2324a0[]) f833a.clone();
    }
}
