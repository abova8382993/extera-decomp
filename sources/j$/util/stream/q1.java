package j$.util.stream;

import java.util.function.IntConsumer;
import java.util.function.IntPredicate;

/* JADX INFO: loaded from: classes2.dex */
public final class q1 extends t1 implements m5 {
    public final /* synthetic */ u1 c;
    public final /* synthetic */ IntPredicate d;

    @Override // java.util.function.Consumer
    public final /* bridge */ /* synthetic */ void accept(Object obj) {
        n((Integer) obj);
    }

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return j$.com.android.tools.r8.a.c(this, intConsumer);
    }

    @Override // j$.util.stream.m5
    public final /* synthetic */ void n(Integer num) {
        x3.C(this, num);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public q1(u1 u1Var, IntPredicate intPredicate) {
        super(u1Var);
        this.c = u1Var;
        this.d = intPredicate;
    }

    @Override // j$.util.stream.t1, j$.util.stream.o5
    public final void accept(int i) {
        if (this.a) {
            return;
        }
        boolean zTest = this.d.test(i);
        u1 u1Var = this.c;
        if (zTest == u1Var.a) {
            this.a = true;
            this.b = u1Var.b;
        }
    }
}
