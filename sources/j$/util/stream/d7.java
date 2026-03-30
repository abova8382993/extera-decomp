package j$.util.stream;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: loaded from: classes2.dex */
public final class d7 {
    public static final d7 DOUBLE_VALUE;
    public static final d7 INT_VALUE;
    public static final d7 LONG_VALUE;
    public static final d7 REFERENCE;
    public static final /* synthetic */ d7[] a;

    public static d7 valueOf(String str) {
        return (d7) Enum.valueOf(d7.class, str);
    }

    public static d7[] values() {
        return (d7[]) a.clone();
    }

    static {
        d7 d7Var = new d7("REFERENCE", 0);
        REFERENCE = d7Var;
        d7 d7Var2 = new d7("INT_VALUE", 1);
        INT_VALUE = d7Var2;
        d7 d7Var3 = new d7("LONG_VALUE", 2);
        LONG_VALUE = d7Var3;
        d7 d7Var4 = new d7("DOUBLE_VALUE", 3);
        DOUBLE_VALUE = d7Var4;
        a = new d7[]{d7Var, d7Var2, d7Var3, d7Var4};
    }
}
