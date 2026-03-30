package j$.util.stream;

import java.util.Arrays;

/* JADX INFO: loaded from: classes2.dex */
public final class p6 extends d6 {
    public Object[] d;
    public int e;

    @Override // j$.util.stream.k5, j$.util.stream.o5
    public final void m(long j) {
        if (j >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        this.d = new Object[(int) j];
    }

    @Override // j$.util.stream.k5, j$.util.stream.o5
    public final void end() {
        int i = 0;
        Arrays.sort(this.d, 0, this.e, this.b);
        long j = this.e;
        o5 o5Var = this.a;
        o5Var.m(j);
        if (!this.c) {
            while (i < this.e) {
                o5Var.accept(this.d[i]);
                i++;
            }
        } else {
            while (i < this.e && !o5Var.p()) {
                o5Var.accept(this.d[i]);
                i++;
            }
        }
        o5Var.end();
        this.d = null;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Object[] objArr = this.d;
        int i = this.e;
        this.e = i + 1;
        objArr[i] = obj;
    }
}
