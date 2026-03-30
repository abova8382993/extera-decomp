package j$.util.stream;

import j$.util.Optional;

/* JADX INFO: loaded from: classes2.dex */
public final class j0 extends k0 {
    public static final f0 c;
    public static final f0 d;

    @Override // java.util.function.Supplier
    public final Object get() {
        if (this.a) {
            return Optional.of(this.b);
        }
        return null;
    }

    static {
        d7 d7Var = d7.REFERENCE;
        c = new f0(true, d7Var, Optional.empty(), new p(9), new j(12));
        d = new f0(false, d7Var, Optional.empty(), new p(9), new j(12));
    }
}
