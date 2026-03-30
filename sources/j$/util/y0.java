package j$.util;

import java.util.function.LongConsumer;

/* JADX INFO: loaded from: classes2.dex */
public interface y0 extends b1 {
    void forEachRemaining(LongConsumer longConsumer);

    boolean tryAdvance(LongConsumer longConsumer);

    @Override // j$.util.b1, j$.util.Spliterator
    y0 trySplit();
}
