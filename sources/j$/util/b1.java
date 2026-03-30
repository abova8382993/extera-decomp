package j$.util;

/* JADX INFO: loaded from: classes2.dex */
public interface b1 extends Spliterator {
    void forEachRemaining(Object obj);

    boolean tryAdvance(Object obj);

    @Override // j$.util.Spliterator
    b1 trySplit();
}
