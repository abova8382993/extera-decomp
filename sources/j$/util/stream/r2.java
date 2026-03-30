package j$.util.stream;

import java.util.function.IntFunction;

/* JADX INFO: loaded from: classes2.dex */
public abstract class r2 extends j2 implements g2 {
    @Override // j$.util.stream.h2
    public final /* synthetic */ Object[] g(IntFunction intFunction) {
        return x3.I(this, intFunction);
    }

    @Override // j$.util.stream.g2
    public final void d(Object obj) {
        ((g2) this.a).d(obj);
        ((g2) this.b).d(obj);
    }

    @Override // j$.util.stream.g2
    public final void c(int i, Object obj) {
        h2 h2Var = this.a;
        ((g2) h2Var).c(i, obj);
        ((g2) this.b).c(i + ((int) ((g2) h2Var).count()), obj);
    }

    @Override // j$.util.stream.g2
    public final Object b() {
        long j = this.c;
        if (j >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        Object objNewArray = newArray((int) j);
        c(0, objNewArray);
        return objNewArray;
    }

    public final String toString() {
        long j = this.c;
        return j < 32 ? String.format("%s[%s.%s]", getClass().getName(), this.a, this.b) : String.format("%s[size=%d]", getClass().getName(), Long.valueOf(j));
    }
}
