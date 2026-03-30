package j$.util.stream;

import j$.util.OptionalInt;
import java.util.function.IntConsumer;

/* JADX INFO: loaded from: classes2.dex */
public final class h0 extends k0 implements m5 {
    public static final f0 c;
    public static final f0 d;

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return j$.com.android.tools.r8.a.c(this, intConsumer);
    }

    @Override // j$.util.stream.k0, j$.util.stream.o5
    public final void accept(int i) {
        v(Integer.valueOf(i));
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        if (this.a) {
            return new OptionalInt(((Integer) this.b).intValue());
        }
        return null;
    }

    static {
        d7 d7Var = d7.INT_VALUE;
        p pVar = new p(7);
        j jVar = new j(10);
        OptionalInt optionalInt = OptionalInt.c;
        c = new f0(true, d7Var, optionalInt, pVar, jVar);
        d = new f0(false, d7Var, optionalInt, new p(7), new j(10));
    }
}
