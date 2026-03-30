package j$.util.stream;

import j$.util.Spliterator;
import java.util.function.IntFunction;

/* JADX INFO: loaded from: classes2.dex */
public final class u5 extends i1 {
    public final /* synthetic */ long s;
    public final /* synthetic */ long t;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public u5(k1 k1Var, int i, long j, long j2) {
        super(k1Var, i);
        this.s = j;
        this.t = j2;
    }

    @Override // j$.util.stream.b
    public final Spliterator B0(b bVar, Spliterator spliterator) {
        long jE0 = bVar.e0(spliterator);
        if (jE0 > 0 && spliterator.hasCharacteristics(16384)) {
            j$.util.y0 y0Var = (j$.util.y0) bVar.u0(spliterator);
            long j = this.s;
            return new u7(y0Var, j, z5.c(j, this.t));
        }
        if (c7.ORDERED.k(bVar.m)) {
            return ((h2) new y5(this, bVar, spliterator, new c1(12), this.s, this.t).invoke()).spliterator();
        }
        j$.util.y0 y0Var2 = (j$.util.y0) bVar.u0(spliterator);
        long j2 = this.s;
        long j3 = this.t;
        if (j2 <= jE0) {
            long jMin = jE0 - j2;
            if (j3 >= 0) {
                jMin = Math.min(j3, jMin);
            }
            j3 = jMin;
            j2 = 0;
        }
        return new a8(y0Var2, j2, j3);
    }

    @Override // j$.util.stream.b
    public final h2 A0(x3 x3Var, Spliterator spliterator, IntFunction intFunction) {
        long jMin;
        long j;
        long jE0 = x3Var.e0(spliterator);
        if (jE0 > 0 && spliterator.hasCharacteristics(16384)) {
            b bVar = (b) x3Var;
            while (bVar.l > 0) {
                bVar = bVar.i;
            }
            return x3.X(x3Var, z5.b(bVar.z0(), spliterator, this.s, this.t), true);
        }
        if (!c7.ORDERED.k(((b) x3Var).m)) {
            j$.util.y0 y0Var = (j$.util.y0) x3Var.u0(spliterator);
            long j2 = this.s;
            long j3 = this.t;
            if (j2 <= jE0) {
                long j4 = jE0 - j2;
                jMin = j3 >= 0 ? Math.min(j3, j4) : j4;
                j = 0;
            } else {
                jMin = j3;
                j = j2;
            }
            return x3.X(this, new a8(y0Var, j, jMin), true);
        }
        return (h2) new y5(this, x3Var, spliterator, intFunction, this.s, this.t).invoke();
    }

    @Override // j$.util.stream.b
    public final o5 D0(int i, o5 o5Var) {
        return new t5(this, o5Var);
    }
}
