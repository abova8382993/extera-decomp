package j$.util.stream;

import java.util.function.LongPredicate;

/* JADX INFO: loaded from: classes2.dex */
public final class u8 extends j5 {
    public final boolean b;

    public u8(j6 j6Var, o5 o5Var) {
        super(o5Var);
        this.b = true;
    }

    @Override // j$.util.stream.j5, j$.util.stream.o5
    public final void m(long j) {
        this.a.m(-1L);
    }

    @Override // j$.util.stream.n5, j$.util.stream.o5
    public final void accept(long j) {
        if (this.b) {
            LongPredicate longPredicate = null;
            longPredicate.test(j);
            throw null;
        }
    }

    @Override // j$.util.stream.j5, j$.util.stream.o5
    public final boolean p() {
        return !this.b || this.a.p();
    }
}
