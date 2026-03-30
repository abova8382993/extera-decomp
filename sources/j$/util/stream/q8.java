package j$.util.stream;

/* JADX INFO: loaded from: classes2.dex */
public final class q8 extends i5 {
    public boolean b;
    public final /* synthetic */ r8 c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public q8(r8 r8Var, o5 o5Var) {
        super(o5Var);
        this.c = r8Var;
        this.b = true;
    }

    @Override // j$.util.stream.i5, j$.util.stream.o5
    public final void m(long j) {
        this.a.m(-1L);
    }

    @Override // j$.util.stream.m5, j$.util.stream.o5
    public final void accept(int i) {
        if (this.b) {
            boolean zTest = this.c.s.test(i);
            this.b = zTest;
            if (zTest) {
                this.a.accept(i);
            }
        }
    }

    @Override // j$.util.stream.i5, j$.util.stream.o5
    public final boolean p() {
        return !this.b || this.a.p();
    }
}
