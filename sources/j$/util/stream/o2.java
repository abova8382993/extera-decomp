package j$.util.stream;

import j$.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;

/* JADX INFO: loaded from: classes2.dex */
public final class o2 extends r2 implements b2 {
    @Override // j$.util.stream.h2
    public final /* synthetic */ h2 e(long j, long j2, IntFunction intFunction) {
        return x3.P(this, j, j2);
    }

    @Override // j$.util.stream.h2
    public final /* synthetic */ void forEach(Consumer consumer) {
        x3.M(this, consumer);
    }

    @Override // j$.util.stream.h2
    public final /* synthetic */ void f(Object[] objArr, int i) {
        x3.J(this, (Double[]) objArr, i);
    }

    @Override // j$.util.stream.g2
    public final Object newArray(int i) {
        return new double[i];
    }

    @Override // j$.util.stream.h2
    public final Spliterator spliterator() {
        return new f3(this);
    }

    @Override // j$.util.stream.h2
    public final j$.util.b1 spliterator() {
        return new f3(this);
    }
}
