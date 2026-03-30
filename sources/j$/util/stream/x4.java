package j$.util.stream;

/* JADX INFO: loaded from: classes2.dex */
public final class x4 extends y4 {
    @Override // j$.util.stream.t4, java.util.function.Supplier
    public final Object get() {
        return Long.valueOf(this.b);
    }

    @Override // j$.util.stream.s4
    public final void t(s4 s4Var) {
        this.b += ((y4) s4Var).b;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.b++;
    }
}
