package androidx.camera.core.internal.utils;

/* JADX INFO: loaded from: classes4.dex */
public interface RingBuffer<T> {

    public interface OnRemoveCallback<T> {
        void onRemove(T t);
    }

    T dequeue();

    boolean isEmpty();
}
