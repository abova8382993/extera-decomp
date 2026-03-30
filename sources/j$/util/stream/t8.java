package j$.util.stream;

import j$.util.Spliterator;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;

/* JADX INFO: loaded from: classes2.dex */
public final class t8 extends z0 implements y8 {
    public final /* synthetic */ IntPredicate s;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public t8(b1 b1Var, int i, IntPredicate intPredicate) {
        super(b1Var, i);
        this.s = intPredicate;
    }

    @Override // j$.util.stream.b
    public final Spliterator B0(b bVar, Spliterator spliterator) {
        return c7.ORDERED.k(bVar.m) ? A0(bVar, spliterator, new c1(17)).spliterator() : new d9((Spliterator.OfInt) bVar.u0(spliterator), this.s);
    }

    @Override // j$.util.stream.b
    public final h2 A0(x3 x3Var, Spliterator spliterator, IntFunction intFunction) {
        return (h2) new a9(this, x3Var, spliterator, intFunction).invoke();
    }

    @Override // j$.util.stream.b
    public final o5 D0(int i, o5 o5Var) {
        return new s8(this, o5Var, false);
    }

    @Override // j$.util.stream.y8
    public final z8 h(z1 z1Var, boolean z) {
        return new s8(this, z1Var, z);
    }
}
