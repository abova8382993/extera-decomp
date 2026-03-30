package j$.util.stream;

/* JADX INFO: loaded from: classes2.dex */
public final class p5 extends k5 {
    public long b;
    public long c;
    public final /* synthetic */ q5 d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public p5(q5 q5Var, o5 o5Var) {
        super(o5Var);
        this.d = q5Var;
        this.b = q5Var.s;
        long j = q5Var.t;
        this.c = j < 0 ? Long.MAX_VALUE : j;
    }

    @Override // j$.util.stream.k5, j$.util.stream.o5
    public final void m(long j) {
        this.a.m(z5.a(j, this.d.s, this.c));
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        long j = this.b;
        if (j == 0) {
            long j2 = this.c;
            if (j2 > 0) {
                this.c = j2 - 1;
                this.a.accept(obj);
                return;
            }
            return;
        }
        this.b = j - 1;
    }

    @Override // j$.util.stream.k5, j$.util.stream.o5
    public final boolean p() {
        return this.c == 0 || this.a.p();
    }
}
