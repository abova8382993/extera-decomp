package j$.util.stream;

import j$.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;

/* JADX INFO: loaded from: classes2.dex */
public final class p2 extends r2 implements d2 {
    @Override // j$.util.stream.h2
    public final /* synthetic */ h2 e(long j, long j2, IntFunction intFunction) {
        return x3.Q(this, j, j2);
    }

    @Override // j$.util.stream.h2
    public final /* synthetic */ void forEach(Consumer consumer) {
        x3.N(this, consumer);
    }

    @Override // j$.util.stream.h2
    public final /* synthetic */ void f(Object[] objArr, int i) {
        x3.K(this, (Integer[]) objArr, i);
    }

    @Override // j$.util.stream.g2
    public final Object newArray(int i) {
        return new int[i];
    }

    @Override // j$.util.stream.h2
    public final Spliterator spliterator() {
        return new g3(this);
    }

    @Override // j$.util.stream.h2
    public final j$.util.b1 spliterator() {
        return new g3(this);
    }
}
