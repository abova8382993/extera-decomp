package j$.util.stream;

import java.util.Arrays;

/* JADX INFO: loaded from: classes2.dex */
public final class g6 extends c6 {
    public w6 c;

    @Override // j$.util.stream.j5, j$.util.stream.o5
    public final void m(long j) {
        w6 w6Var;
        if (j >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        if (j <= 0) {
            w6Var = new w6();
        } else {
            w6Var = new w6((int) j);
        }
        this.c = w6Var;
    }

    @Override // j$.util.stream.j5, j$.util.stream.o5
    public final void end() {
        long[] jArr = (long[]) this.c.b();
        Arrays.sort(jArr);
        long length = jArr.length;
        o5 o5Var = this.a;
        o5Var.m(length);
        int i = 0;
        if (!this.b) {
            int length2 = jArr.length;
            while (i < length2) {
                o5Var.accept(jArr[i]);
                i++;
            }
        } else {
            int length3 = jArr.length;
            while (i < length3) {
                long j = jArr[i];
                if (o5Var.p()) {
                    break;
                }
                o5Var.accept(j);
                i++;
            }
        }
        o5Var.end();
    }

    @Override // j$.util.stream.n5, j$.util.stream.o5
    public final void accept(long j) {
        this.c.accept(j);
    }
}
