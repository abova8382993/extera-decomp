package j$.util.stream;

/* JADX INFO: loaded from: classes2.dex */
public final class r5 extends i5 {
    public long b;
    public long c;
    public final /* synthetic */ s5 d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public r5(s5 s5Var, o5 o5Var) {
        super(o5Var);
        this.d = s5Var;
        this.b = s5Var.s;
        long j = s5Var.t;
        this.c = j < 0 ? Long.MAX_VALUE : j;
    }

    @Override // j$.util.stream.i5, j$.util.stream.o5
    public final void m(long j) {
        this.a.m(z5.a(j, this.d.s, this.c));
    }

    @Override // j$.util.stream.m5, j$.util.stream.o5
    public final void accept(int i) {
        long j = this.b;
        if (j == 0) {
            long j2 = this.c;
            if (j2 > 0) {
                this.c = j2 - 1;
                this.a.accept(i);
                return;
            }
            return;
        }
        this.b = j - 1;
    }

    @Override // j$.util.stream.i5, j$.util.stream.o5
    public final boolean p() {
        return this.c == 0 || this.a.p();
    }
}
