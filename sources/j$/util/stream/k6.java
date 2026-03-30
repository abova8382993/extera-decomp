package j$.util.stream;

import j$.util.Comparator;
import j$.util.Objects;
import j$.util.Spliterator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.IntFunction;

/* JADX INFO: loaded from: classes2.dex */
public final class k6 extends e5 {
    public final boolean s;
    public final Comparator t;

    public k6(g5 g5Var) {
        super(g5Var, c7.q | c7.o);
        this.s = true;
        this.t = Comparator.CC.naturalOrder();
    }

    public k6(g5 g5Var, java.util.Comparator comparator) {
        super(g5Var, c7.q | c7.p);
        this.s = false;
        this.t = (java.util.Comparator) Objects.requireNonNull(comparator);
    }

    @Override // j$.util.stream.b
    public final o5 D0(int i, o5 o5Var) {
        Objects.requireNonNull(o5Var);
        if (c7.SORTED.k(i) && this.s) {
            return o5Var;
        }
        if (c7.SIZED.k(i)) {
            return new p6(o5Var, this.t);
        }
        return new l6(o5Var, this.t);
    }

    @Override // j$.util.stream.b
    public final h2 A0(x3 x3Var, Spliterator spliterator, IntFunction intFunction) {
        if (c7.SORTED.k(((b) x3Var).m) && this.s) {
            return x3Var.d0(spliterator, false, intFunction);
        }
        Object[] objArrG = x3Var.d0(spliterator, true, intFunction).g(intFunction);
        Arrays.sort(objArrG, this.t);
        return new k2(objArrG);
    }
}
