package j$.util.stream;

import java.util.function.DoubleConsumer;

/* JADX INFO: loaded from: classes2.dex */
public final class u4 extends y4 implements l5 {
    @Override // j$.util.stream.l5
    public final /* synthetic */ void A(Double d) {
        x3.A(this, d);
    }

    @Override // java.util.function.Consumer
    /* JADX INFO: renamed from: accept */
    public final /* bridge */ /* synthetic */ void v(Object obj) {
        A((Double) obj);
    }

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return j$.com.android.tools.r8.a.b(this, doubleConsumer);
    }

    @Override // j$.util.stream.t4, java.util.function.Supplier
    public final Object get() {
        return Long.valueOf(this.b);
    }

    @Override // j$.util.stream.s4
    public final void t(s4 s4Var) {
        this.b += ((y4) s4Var).b;
    }

    @Override // j$.util.stream.y4, j$.util.stream.o5
    public final void accept(double d) {
        this.b++;
    }
}
