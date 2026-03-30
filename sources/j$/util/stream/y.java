package j$.util.stream;

import j$.util.Spliterator;
import java.util.function.DoubleConsumer;

/* JADX INFO: loaded from: classes2.dex */
public final class y extends b0 {
    @Override // j$.util.stream.b, j$.util.stream.BaseStream
    public final e0 sequential() {
        this.h.r = false;
        return this;
    }

    @Override // j$.util.stream.b, j$.util.stream.BaseStream
    public final e0 parallel() {
        this.h.r = true;
        return this;
    }

    @Override // j$.util.stream.b0, j$.util.stream.e0
    public final void forEach(DoubleConsumer doubleConsumer) {
        if (this.h.r) {
            super.forEach(doubleConsumer);
        } else {
            b0.H0(F0()).forEachRemaining(doubleConsumer);
        }
    }

    @Override // j$.util.stream.b0, j$.util.stream.e0
    public final void forEachOrdered(DoubleConsumer doubleConsumer) {
        if (this.h.r) {
            super.forEachOrdered(doubleConsumer);
        } else {
            b0.H0(F0()).forEachRemaining(doubleConsumer);
        }
    }

    @Override // j$.util.stream.BaseStream
    public final BaseStream unordered() {
        return !c7.ORDERED.k(this.m) ? this : new t(this, c7.r, 1);
    }

    @Override // j$.util.stream.b, j$.util.stream.BaseStream
    public final /* bridge */ /* synthetic */ Spliterator spliterator() {
        return spliterator();
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
