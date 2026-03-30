package j$.util.stream;

import j$.util.Spliterator;

/* JADX INFO: loaded from: classes2.dex */
public abstract class z5 {
    public static long c(long j, long j2) {
        long j3 = j2 >= 0 ? j + j2 : Long.MAX_VALUE;
        if (j3 >= 0) {
            return j3;
        }
        return Long.MAX_VALUE;
    }

    public static long a(long j, long j2, long j3) {
        if (j >= 0) {
            return Math.max(-1L, Math.min(j - j2, j3));
        }
        return -1L;
    }

    public static Spliterator b(d7 d7Var, Spliterator spliterator, long j, long j2) {
        long jC = c(j, j2);
        int i = x5.a[d7Var.ordinal()];
        if (i == 1) {
            return new w7(spliterator, j, jC);
        }
        if (i == 2) {
            return new t7((Spliterator.OfInt) spliterator, j, jC);
        }
        if (i == 3) {
            return new u7((j$.util.y0) spliterator, j, jC);
        }
        if (i != 4) {
            throw new IllegalStateException("Unknown shape " + d7Var);
        }
        return new s7((j$.util.t0) spliterator, j, jC);
    }

    public static q5 h(g5 g5Var, long j, long j2) {
        if (j < 0) {
            throw new IllegalArgumentException("Skip must be non-negative: " + j);
        }
        return new q5(g5Var, d(j2), j, j2);
    }

    public static s5 f(b1 b1Var, long j, long j2) {
        if (j < 0) {
            throw new IllegalArgumentException("Skip must be non-negative: " + j);
        }
        return new s5(b1Var, d(j2), j, j2);
    }

    public static u5 g(k1 k1Var, long j, long j2) {
        if (j < 0) {
            throw new IllegalArgumentException("Skip must be non-negative: " + j);
        }
        return new u5(k1Var, d(j2), j, j2);
    }

    public static w5 e(b0 b0Var, long j, long j2) {
        if (j < 0) {
            throw new IllegalArgumentException("Skip must be non-negative: " + j);
        }
        return new w5(b0Var, d(j2), j, j2);
    }

    public static int d(long j) {
        return (j != -1 ? c7.u : 0) | c7.t;
    }
}
