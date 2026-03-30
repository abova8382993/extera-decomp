package j$.util.stream;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: loaded from: classes2.dex */
public final class b7 {
    public static final b7 OP;
    public static final b7 SPLITERATOR;
    public static final b7 STREAM;
    public static final b7 TERMINAL_OP;
    public static final b7 UPSTREAM_TERMINAL_OP;
    public static final /* synthetic */ b7[] a;

    public static b7 valueOf(String str) {
        return (b7) Enum.valueOf(b7.class, str);
    }

    public static b7[] values() {
        return (b7[]) a.clone();
    }

    static {
        b7 b7Var = new b7("SPLITERATOR", 0);
        SPLITERATOR = b7Var;
        b7 b7Var2 = new b7("STREAM", 1);
        STREAM = b7Var2;
        b7 b7Var3 = new b7("OP", 2);
        OP = b7Var3;
        b7 b7Var4 = new b7("TERMINAL_OP", 3);
        TERMINAL_OP = b7Var4;
        b7 b7Var5 = new b7("UPSTREAM_TERMINAL_OP", 4);
        UPSTREAM_TERMINAL_OP = b7Var5;
        a = new b7[]{b7Var, b7Var2, b7Var3, b7Var4, b7Var5};
    }
}
