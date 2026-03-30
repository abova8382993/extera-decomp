package j$.util.stream;

import java.util.function.DoubleConsumer;

/* JADX INFO: loaded from: classes2.dex */
public final class g0 extends k0 implements l5 {
    public static final f0 c;
    public static final f0 d;

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return j$.com.android.tools.r8.a.b(this, doubleConsumer);
    }

    @Override // j$.util.stream.k0, j$.util.stream.o5
    public final void accept(double d2) {
        v(Double.valueOf(d2));
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        if (this.a) {
            return new j$.util.b0(((Double) this.b).doubleValue());
        }
        return null;
    }

    static {
        d7 d7Var = d7.DOUBLE_VALUE;
        p pVar = new p(6);
        j jVar = new j(9);
        j$.util.b0 b0Var = j$.util.b0.c;
        c = new f0(true, d7Var, b0Var, pVar, jVar);
        d = new f0(false, d7Var, b0Var, new p(6), new j(9));
    }
}
