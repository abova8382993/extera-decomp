package p026j$.time.zone;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: renamed from: j$.time.zone.d */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class EnumC2393d {
    public static final EnumC2393d STANDARD;
    public static final EnumC2393d UTC;
    public static final EnumC2393d WALL;

    /* JADX INFO: renamed from: a */
    public static final /* synthetic */ EnumC2393d[] f997a;

    static {
        EnumC2393d enumC2393d = new EnumC2393d("UTC", 0);
        UTC = enumC2393d;
        EnumC2393d enumC2393d2 = new EnumC2393d("WALL", 1);
        WALL = enumC2393d2;
        EnumC2393d enumC2393d3 = new EnumC2393d("STANDARD", 2);
        STANDARD = enumC2393d3;
        f997a = new EnumC2393d[]{enumC2393d, enumC2393d2, enumC2393d3};
    }

    public static EnumC2393d valueOf(String str) {
        return (EnumC2393d) Enum.valueOf(EnumC2393d.class, str);
    }

    public static EnumC2393d[] values() {
        return (EnumC2393d[]) f997a.clone();
    }
}
