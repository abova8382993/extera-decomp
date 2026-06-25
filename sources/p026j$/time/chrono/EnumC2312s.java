package p026j$.time.chrono;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: renamed from: j$.time.chrono.s */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class EnumC2312s implements InterfaceC2305l {
    public static final EnumC2312s BCE;

    /* JADX INFO: renamed from: CE */
    public static final EnumC2312s f804CE;

    /* JADX INFO: renamed from: a */
    public static final /* synthetic */ EnumC2312s[] f805a;

    static {
        EnumC2312s enumC2312s = new EnumC2312s("BCE", 0);
        BCE = enumC2312s;
        EnumC2312s enumC2312s2 = new EnumC2312s("CE", 1);
        f804CE = enumC2312s2;
        f805a = new EnumC2312s[]{enumC2312s, enumC2312s2};
    }

    public static EnumC2312s valueOf(String str) {
        return (EnumC2312s) Enum.valueOf(EnumC2312s.class, str);
    }

    public static EnumC2312s[] values() {
        return (EnumC2312s[]) f805a.clone();
    }

    @Override // p026j$.time.chrono.InterfaceC2305l
    public final int getValue() {
        return ordinal();
    }
}
