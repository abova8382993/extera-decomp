package j$.util.stream;

import j$.util.Spliterator;
import java.util.concurrent.CountedCompleter;
import java.util.function.BinaryOperator;
import java.util.function.LongFunction;

/* JADX INFO: loaded from: classes2.dex */
public class n2 extends e {
    public final x3 h;
    public final LongFunction i;
    public final BinaryOperator j;

    @Override // j$.util.stream.e, java.util.concurrent.CountedCompleter
    public final void onCompletion(CountedCompleter countedCompleter) {
        e eVar = this.d;
        if (eVar != null) {
            this.f = (h2) this.j.apply((h2) ((n2) eVar).f, (h2) ((n2) this.e).f);
        }
        super.onCompletion(countedCompleter);
    }

    public n2(x3 x3Var, Spliterator spliterator, LongFunction longFunction, BinaryOperator binaryOperator) {
        super(x3Var, spliterator);
        this.h = x3Var;
        this.i = longFunction;
        this.j = binaryOperator;
    }

    public n2(n2 n2Var, Spliterator spliterator) {
        super(n2Var, spliterator);
        this.h = n2Var.h;
        this.i = n2Var.i;
        this.j = n2Var.j;
    }

    @Override // j$.util.stream.e
    public e c(Spliterator spliterator) {
        return new n2(this, spliterator);
    }

    @Override // j$.util.stream.e
    /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
    public final h2 a() {
        z1 z1Var = (z1) this.i.apply(this.h.e0(this.b));
        this.h.s0(this.b, z1Var);
        return z1Var.build();
    }
}
