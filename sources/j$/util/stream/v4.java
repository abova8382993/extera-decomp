package j$.util.stream;

import java.util.function.IntConsumer;

/* JADX INFO: loaded from: classes2.dex */
public final class v4 extends y4 implements m5 {
    @Override // java.util.function.Consumer
    /* JADX INFO: renamed from: accept */
    public final /* bridge */ /* synthetic */ void v(Object obj) {
        n((Integer) obj);
    }

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return j$.com.android.tools.r8.a.c(this, intConsumer);
    }

    @Override // j$.util.stream.m5
    public final /* synthetic */ void n(Integer num) {
        x3.C(this, num);
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
    public final void accept(int i) {
        this.b++;
    }
}
