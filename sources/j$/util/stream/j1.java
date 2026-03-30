package j$.util.stream;

import j$.util.Spliterator;

/* JADX INFO: loaded from: classes2.dex */
public abstract class j1 extends k1 {
    @Override // j$.util.stream.b
    public final boolean C0() {
        return false;
    }

    @Override // j$.util.stream.b, j$.util.stream.BaseStream
    public final LongStream sequential() {
        this.h.r = false;
        return this;
    }

    @Override // j$.util.stream.b, j$.util.stream.BaseStream
    public final LongStream parallel() {
        this.h.r = true;
        return this;
    }

    @Override // j$.util.stream.BaseStream
    public final BaseStream unordered() {
        return !c7.ORDERED.k(this.m) ? this : new v(this, c7.r, 4);
    }

    @Override // j$.util.stream.b, j$.util.stream.BaseStream
    public final /* bridge */ /* synthetic */ Spliterator spliterator() {
        return spliterator();
    }
}
