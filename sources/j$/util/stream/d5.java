package j$.util.stream;

import java.util.function.Consumer;

/* JADX INFO: loaded from: classes2.dex */
public final class d5 extends g5 {
    @Override // j$.util.stream.g5, j$.util.stream.Stream
    public final void forEach(Consumer consumer) {
        if (!this.h.r) {
            F0().forEachRemaining(consumer);
        } else {
            super.forEach(consumer);
        }
    }

    @Override // j$.util.stream.g5, j$.util.stream.Stream
    public final void forEachOrdered(Consumer consumer) {
        if (!this.h.r) {
            F0().forEachRemaining(consumer);
        } else {
            super.forEachOrdered(consumer);
        }
    }

    @Override // j$.util.stream.BaseStream
    public final BaseStream unordered() {
        return !c7.ORDERED.k(this.m) ? this : new b5(this, c7.r);
    }

    @Override // j$.util.stream.b
    public final boolean C0() {
        throw new UnsupportedOperationException();
    }

    @Override // j$.util.stream.b
    public final o5 D0(int i, o5 o5Var) {
        throw new UnsupportedOperationException();
    }
}
