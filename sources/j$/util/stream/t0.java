package j$.util.stream;

import j$.util.Spliterator;
import java.util.concurrent.CountedCompleter;

/* JADX INFO: loaded from: classes2.dex */
public final class t0 extends CountedCompleter {
    public Spliterator a;
    public final o5 b;
    public final x3 c;
    public long d;

    public t0(x3 x3Var, Spliterator spliterator, o5 o5Var) {
        super(null);
        this.b = o5Var;
        this.c = x3Var;
        this.a = spliterator;
        this.d = 0L;
    }

    public t0(t0 t0Var, Spliterator spliterator) {
        super(t0Var);
        this.a = spliterator;
        this.b = t0Var.b;
        this.d = t0Var.d;
        this.c = t0Var.c;
    }

    @Override // java.util.concurrent.CountedCompleter
    public final void compute() {
        Spliterator spliteratorTrySplit;
        Spliterator spliterator = this.a;
        long jEstimateSize = spliterator.estimateSize();
        long jE = this.d;
        if (jE == 0) {
            jE = e.e(jEstimateSize);
            this.d = jE;
        }
        boolean zK = c7.SHORT_CIRCUIT.k(((b) this.c).m);
        o5 o5Var = this.b;
        boolean z = false;
        t0 t0Var = this;
        while (true) {
            if (zK && o5Var.p()) {
                break;
            }
            if (jEstimateSize <= jE || (spliteratorTrySplit = spliterator.trySplit()) == null) {
                break;
            }
            t0 t0Var2 = new t0(t0Var, spliteratorTrySplit);
            t0Var.addToPendingCount(1);
            if (z) {
                spliterator = spliteratorTrySplit;
            } else {
                t0 t0Var3 = t0Var;
                t0Var = t0Var2;
                t0Var2 = t0Var3;
            }
            z = !z;
            t0Var.fork();
            t0Var = t0Var2;
            jEstimateSize = spliterator.estimateSize();
        }
        t0Var.c.Z(spliterator, o5Var);
        t0Var.a = null;
        t0Var.propagateCompletion();
    }
}
