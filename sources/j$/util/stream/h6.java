package j$.util.stream;

import j$.util.Objects;
import j$.util.Spliterator;
import java.util.Arrays;
import java.util.function.IntFunction;

/* JADX INFO: loaded from: classes2.dex */
public final class h6 extends z implements y8 {
    public final /* synthetic */ int s;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ h6(b bVar, int i, int i2) {
        super(bVar, i);
        this.s = i2;
    }

    @Override // j$.util.stream.b
    public Spliterator B0(b bVar, Spliterator spliterator) {
        switch (this.s) {
            case 1:
                return c7.ORDERED.k(bVar.m) ? A0(bVar, spliterator, new c1(20)).spliterator() : new c9((j$.util.t0) bVar.u0(spliterator), 1);
            case 2:
                return c7.ORDERED.k(bVar.m) ? A0(bVar, spliterator, new c1(21)).spliterator() : new c9((j$.util.t0) bVar.u0(spliterator), 0);
            default:
                return super.B0(bVar, spliterator);
        }
    }

    @Override // j$.util.stream.b
    public final h2 A0(x3 x3Var, Spliterator spliterator, IntFunction intFunction) {
        switch (this.s) {
            case 0:
                if (c7.SORTED.k(((b) x3Var).m)) {
                    return x3Var.d0(spliterator, false, intFunction);
                }
                double[] dArr = (double[]) ((b2) x3Var.d0(spliterator, true, intFunction)).b();
                Arrays.sort(dArr);
                return new t2(dArr);
            case 1:
                return (h2) new b9(this, x3Var, spliterator, intFunction).invoke();
            default:
                return (h2) new a9(this, x3Var, spliterator, intFunction).invoke();
        }
    }

    @Override // j$.util.stream.b
    public final o5 D0(int i, o5 o5Var) {
        switch (this.s) {
            case 0:
                Objects.requireNonNull(o5Var);
                if (c7.SORTED.k(i)) {
                    return o5Var;
                }
                return c7.SIZED.k(i) ? new m6(o5Var) : new e6(o5Var);
            case 1:
                return new w8(this, o5Var);
            default:
                return new x8(this, o5Var, false);
        }
    }

    @Override // j$.util.stream.y8
    public z8 h(z1 z1Var, boolean z) {
        return new x8(this, z1Var, z);
    }
}
