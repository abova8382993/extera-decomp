package j$.util.stream;

import java.util.Arrays;

/* JADX INFO: loaded from: classes2.dex */
public final class e6 extends a6 {
    public s6 c;

    @Override // j$.util.stream.h5, j$.util.stream.o5
    public final void m(long j) {
        s6 s6Var;
        if (j >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        if (j <= 0) {
            s6Var = new s6();
        } else {
            s6Var = new s6((int) j);
        }
        this.c = s6Var;
    }

    @Override // j$.util.stream.h5, j$.util.stream.o5
    public final void end() {
        double[] dArr = (double[]) this.c.b();
        Arrays.sort(dArr);
        long length = dArr.length;
        o5 o5Var = this.a;
        o5Var.m(length);
        int i = 0;
        if (!this.b) {
            int length2 = dArr.length;
            while (i < length2) {
                o5Var.accept(dArr[i]);
                i++;
            }
        } else {
            int length3 = dArr.length;
            while (i < length3) {
                double d = dArr[i];
                if (o5Var.p()) {
                    break;
                }
                o5Var.accept(d);
                i++;
            }
        }
        o5Var.end();
    }

    @Override // j$.util.stream.l5, j$.util.stream.o5
    public final void accept(double d) {
        this.c.accept(d);
    }
}
