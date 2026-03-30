package j$.util.stream;

import j$.util.Objects;
import java.util.function.DoubleConsumer;
import java.util.function.LongConsumer;

/* JADX INFO: loaded from: classes2.dex */
public final class a5 extends k5 {
    public final /* synthetic */ int b = 0;
    public boolean c;
    public final Object d;
    public final /* synthetic */ b e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public a5(x xVar, o5 o5Var) {
        super(o5Var);
        this.e = xVar;
        o5 o5Var2 = this.a;
        Objects.requireNonNull(o5Var2);
        this.d = new j$.util.d0(o5Var2, 1);
    }

    @Override // j$.util.stream.k5, j$.util.stream.o5
    public final void m(long j) {
        switch (this.b) {
            case 0:
                this.a.m(-1L);
                break;
            default:
                this.a.m(-1L);
                break;
        }
    }

    @Override // java.util.function.Consumer
    /* JADX INFO: renamed from: accept */
    public final void v(Object obj) {
        switch (this.b) {
            case 0:
                j$.util.l0 l0Var = (j$.util.l0) this.d;
                LongStream longStream = (LongStream) ((j$.time.s) ((g1) this.e).t).apply(obj);
                if (longStream != null) {
                    try {
                        if (!this.c) {
                            longStream.sequential().forEach(l0Var);
                        } else {
                            j$.util.y0 y0VarSpliterator = longStream.sequential().spliterator();
                            while (!this.a.p() && y0VarSpliterator.tryAdvance((LongConsumer) l0Var)) {
                            }
                        }
                    } catch (Throwable th) {
                        try {
                            longStream.close();
                            break;
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                    break;
                }
                if (longStream != null) {
                    longStream.close();
                    return;
                }
                return;
            default:
                j$.util.d0 d0Var = (j$.util.d0) this.d;
                e0 e0Var = (e0) ((j$.time.s) ((x) this.e).t).apply(obj);
                if (e0Var != null) {
                    try {
                        if (!this.c) {
                            e0Var.sequential().forEach(d0Var);
                        } else {
                            j$.util.t0 t0VarSpliterator = e0Var.sequential().spliterator();
                            while (!this.a.p() && t0VarSpliterator.tryAdvance((DoubleConsumer) d0Var)) {
                            }
                        }
                    } catch (Throwable th3) {
                        try {
                            e0Var.close();
                            break;
                        } catch (Throwable th4) {
                            th3.addSuppressed(th4);
                        }
                        throw th3;
                    }
                    break;
                }
                if (e0Var != null) {
                    e0Var.close();
                    return;
                }
                return;
        }
    }

    @Override // j$.util.stream.k5, j$.util.stream.o5
    public final boolean p() {
        switch (this.b) {
            case 0:
                this.c = true;
                break;
            default:
                this.c = true;
                break;
        }
        return this.a.p();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public a5(g1 g1Var, o5 o5Var) {
        super(o5Var);
        this.e = g1Var;
        o5 o5Var2 = this.a;
        Objects.requireNonNull(o5Var2);
        this.d = new j$.util.l0(o5Var2, 1);
    }
}
