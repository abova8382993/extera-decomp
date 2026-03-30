package j$.util.stream;

import j$.util.Objects;
import j$.util.Spliterator;
import java.util.function.IntConsumer;

/* JADX INFO: loaded from: classes2.dex */
public final class c5 extends k5 {
    public boolean b;
    public final j$.util.h0 c;
    public final /* synthetic */ v0 d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public c5(v0 v0Var, o5 o5Var) {
        super(o5Var);
        this.d = v0Var;
        o5 o5Var2 = this.a;
        Objects.requireNonNull(o5Var2);
        this.c = new j$.util.h0(o5Var2, 1);
    }

    @Override // j$.util.stream.k5, j$.util.stream.o5
    public final void m(long j) {
        this.a.m(-1L);
    }

    @Override // java.util.function.Consumer
    /* JADX INFO: renamed from: accept */
    public final void v(Object obj) {
        IntStream intStream = (IntStream) ((j$.time.s) this.d.t).apply(obj);
        if (intStream != null) {
            try {
                boolean z = this.b;
                j$.util.h0 h0Var = this.c;
                if (!z) {
                    intStream.sequential().forEach(h0Var);
                } else {
                    Spliterator.OfInt ofIntSpliterator = intStream.sequential().spliterator();
                    while (!this.a.p() && ofIntSpliterator.tryAdvance((IntConsumer) h0Var)) {
                    }
                }
            } catch (Throwable th) {
                try {
                    intStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        if (intStream != null) {
            intStream.close();
        }
    }

    @Override // j$.util.stream.k5, j$.util.stream.o5
    public final boolean p() {
        this.b = true;
        return this.a.p();
    }
}
