package j$.util.stream;

import j$.util.Spliterator;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;

/* JADX INFO: loaded from: classes2.dex */
public final class r8 extends z0 {
    public final /* synthetic */ IntPredicate s;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public r8(b1 b1Var, int i, IntPredicate intPredicate) {
        super(b1Var, i);
        this.s = intPredicate;
    }

    @Override // j$.util.stream.b
    public final Spliterator B0(b bVar, Spliterator spliterator) {
        return c7.ORDERED.k(bVar.m) ? A0(bVar, spliterator, new c1(16)).spliterator() : new e9((Spliterator.OfInt) bVar.u0(spliterator), this.s);
    }

    @Override // j$.util.stream.b
    public final h2 A0(x3 x3Var, Spliterator spliterator, IntFunction intFunction) {
        return (h2) new b9(this, x3Var, spliterator, intFunction).invoke();
    }

    @Override // j$.util.stream.b
    public final o5 D0(int i, o5 o5Var) {
        return new q8(this, o5Var);
    }
}
