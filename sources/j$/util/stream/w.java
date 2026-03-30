package j$.util.stream;

import j$.util.Objects;
import java.util.function.DoubleConsumer;

/* JADX INFO: loaded from: classes2.dex */
public final class w extends h5 {
    public boolean b;
    public final j$.util.d0 c;
    public final /* synthetic */ x d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public w(x xVar, o5 o5Var) {
        super(o5Var);
        this.d = xVar;
        o5 o5Var2 = this.a;
        Objects.requireNonNull(o5Var2);
        this.c = new j$.util.d0(o5Var2, 1);
    }

    @Override // j$.util.stream.h5, j$.util.stream.o5
    public final void m(long j) {
        this.a.m(-1L);
    }

    @Override // j$.util.stream.l5, j$.util.stream.o5
    public final void accept(double d) {
        e0 e0Var = (e0) ((j$.time.s) this.d.t).apply(d);
        if (e0Var != null) {
            try {
                boolean z = this.b;
                j$.util.d0 d0Var = this.c;
                if (!z) {
                    e0Var.sequential().forEach(d0Var);
                } else {
                    j$.util.t0 t0VarSpliterator = e0Var.sequential().spliterator();
                    while (!this.a.p() && t0VarSpliterator.tryAdvance((DoubleConsumer) d0Var)) {
                    }
                }
            } catch (Throwable th) {
                try {
                    e0Var.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        if (e0Var != null) {
            e0Var.close();
        }
    }

    @Override // j$.util.stream.h5, j$.util.stream.o5
    public final boolean p() {
        this.b = true;
        return this.a.p();
    }
}
