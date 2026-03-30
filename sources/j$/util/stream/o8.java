package j$.util.stream;

import j$.util.Spliterator;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes2.dex */
public final class o8 extends e5 implements y8 {
    public final /* synthetic */ int s;
    public final /* synthetic */ Predicate t;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ o8(g5 g5Var, int i, Predicate predicate, int i2) {
        super(g5Var, i);
        this.s = i2;
        this.t = predicate;
    }

    @Override // j$.util.stream.b
    public final Spliterator B0(b bVar, Spliterator spliterator) {
        switch (this.s) {
            case 0:
                return c7.ORDERED.k(bVar.m) ? A0(bVar, spliterator, new c1(2)).spliterator() : new h9(bVar.u0(spliterator), this.t, 1);
            default:
                return c7.ORDERED.k(bVar.m) ? A0(bVar, spliterator, new c1(2)).spliterator() : new h9(bVar.u0(spliterator), this.t, 0);
        }
    }

    @Override // j$.util.stream.b
    public final h2 A0(x3 x3Var, Spliterator spliterator, IntFunction intFunction) {
        switch (this.s) {
            case 0:
                return (h2) new b9(this, x3Var, spliterator, intFunction).invoke();
            default:
                return (h2) new a9(this, x3Var, spliterator, intFunction).invoke();
        }
    }

    @Override // j$.util.stream.b
    public final o5 D0(int i, o5 o5Var) {
        switch (this.s) {
            case 0:
                return new m(this, o5Var);
            default:
                return new p8(this, o5Var, false);
        }
    }

    @Override // j$.util.stream.y8
    public z8 h(z1 z1Var, boolean z) {
        return new p8(this, z1Var, z);
    }
}
