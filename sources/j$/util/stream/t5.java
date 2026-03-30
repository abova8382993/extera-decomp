package j$.util.stream;

/* JADX INFO: loaded from: classes2.dex */
public final class t5 extends j5 {
    public long b;
    public long c;
    public final /* synthetic */ u5 d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public t5(u5 u5Var, o5 o5Var) {
        super(o5Var);
        this.d = u5Var;
        this.b = u5Var.s;
        long j = u5Var.t;
        this.c = j < 0 ? Long.MAX_VALUE : j;
    }

    @Override // j$.util.stream.j5, j$.util.stream.o5
    public final void m(long j) {
        this.a.m(z5.a(j, this.d.s, this.c));
    }

    @Override // j$.util.stream.n5, j$.util.stream.o5
    public final void accept(long j) {
        long j2 = this.b;
        if (j2 == 0) {
            long j3 = this.c;
            if (j3 > 0) {
                this.c = j3 - 1;
                this.a.accept(j);
                return;
            }
            return;
        }
        this.b = j2 - 1;
    }

    @Override // j$.util.stream.j5, j$.util.stream.o5
    public final boolean p() {
        return this.c == 0 || this.a.p();
    }
}
