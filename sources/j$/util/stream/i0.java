package j$.util.stream;

import java.util.function.LongConsumer;

/* JADX INFO: loaded from: classes2.dex */
public final class i0 extends k0 implements n5 {
    public static final f0 c;
    public static final f0 d;

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return j$.com.android.tools.r8.a.d(this, longConsumer);
    }

    @Override // j$.util.stream.k0, j$.util.stream.o5
    public final void accept(long j) {
        v(Long.valueOf(j));
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        if (this.a) {
            return new j$.util.c0(((Long) this.b).longValue());
        }
        return null;
    }

    static {
        d7 d7Var = d7.LONG_VALUE;
        p pVar = new p(8);
        j jVar = new j(11);
        j$.util.c0 c0Var = j$.util.c0.c;
        c = new f0(true, d7Var, c0Var, pVar, jVar);
        d = new f0(false, d7Var, c0Var, new p(8), new j(11));
    }
}
