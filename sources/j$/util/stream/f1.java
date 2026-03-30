package j$.util.stream;

import j$.util.Objects;
import java.util.function.LongConsumer;

/* JADX INFO: loaded from: classes2.dex */
public final class f1 extends j5 {
    public boolean b;
    public final j$.util.l0 c;
    public final /* synthetic */ g1 d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public f1(g1 g1Var, o5 o5Var) {
        super(o5Var);
        this.d = g1Var;
        o5 o5Var2 = this.a;
        Objects.requireNonNull(o5Var2);
        this.c = new j$.util.l0(o5Var2, 1);
    }

    @Override // j$.util.stream.j5, j$.util.stream.o5
    public final void m(long j) {
        this.a.m(-1L);
    }

    @Override // j$.util.stream.n5, j$.util.stream.o5
    public final void accept(long j) {
        LongStream longStream = (LongStream) ((j$.time.s) this.d.t).apply(j);
        if (longStream != null) {
            try {
                boolean z = this.b;
                j$.util.l0 l0Var = this.c;
                if (!z) {
                    longStream.sequential().forEach(l0Var);
                } else {
                    j$.util.y0 y0VarSpliterator = longStream.sequential().spliterator();
                    while (!this.a.p() && y0VarSpliterator.tryAdvance((LongConsumer) l0Var)) {
                    }
                }
            } catch (Throwable th) {
                try {
                    longStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        if (longStream != null) {
            longStream.close();
        }
    }

    @Override // j$.util.stream.j5, j$.util.stream.o5
    public final boolean p() {
        this.b = true;
        return this.a.p();
    }
}
