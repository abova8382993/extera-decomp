package j$.util.stream;

import j$.util.Spliterator;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes2.dex */
public final class u7 extends v7 implements j$.util.y0 {
    @Override // j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        j$.com.android.tools.r8.a.l(this, consumer);
    }

    @Override // j$.util.Spliterator
    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return j$.com.android.tools.r8.a.B(this, consumer);
    }

    @Override // j$.util.stream.x7
    public final Spliterator a(Spliterator spliterator, long j, long j2, long j3, long j4) {
        return new u7((j$.util.y0) spliterator, j, j2, j3, j4);
    }

    @Override // j$.util.stream.v7
    public final Object b() {
        return new e2(1);
    }
}
