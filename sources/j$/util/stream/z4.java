package j$.util.stream;

import j$.util.Spliterator;
import java.util.concurrent.CountedCompleter;

/* JADX INFO: loaded from: classes2.dex */
public final class z4 extends e {
    public final x3 h;

    @Override // j$.util.stream.e, java.util.concurrent.CountedCompleter
    public final void onCompletion(CountedCompleter countedCompleter) {
        e eVar = this.d;
        if (eVar != null) {
            s4 s4Var = (s4) ((z4) eVar).f;
            s4Var.t((s4) ((z4) this.e).f);
            this.f = s4Var;
        }
        super.onCompletion(countedCompleter);
    }

    public z4(x3 x3Var, x3 x3Var2, Spliterator spliterator) {
        super(x3Var2, spliterator);
        this.h = x3Var;
    }

    public z4(z4 z4Var, Spliterator spliterator) {
        super(z4Var, spliterator);
        this.h = z4Var.h;
    }

    @Override // j$.util.stream.e
    public final e c(Spliterator spliterator) {
        return new z4(this, spliterator);
    }

    @Override // j$.util.stream.e
    public final Object a() {
        x3 x3Var = this.a;
        s4 s4VarR0 = this.h.r0();
        x3Var.s0(this.b, s4VarR0);
        return s4VarR0;
    }
}
