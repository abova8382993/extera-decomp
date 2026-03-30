package j$.util.stream;

import j$.util.Spliterator;

/* JADX INFO: loaded from: classes2.dex */
public abstract class z extends b0 {
    @Override // j$.util.stream.b
    public final boolean C0() {
        return true;
    }

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

    @Override // j$.util.stream.BaseStream
    public final BaseStream unordered() {
        return !c7.ORDERED.k(this.m) ? this : new t(this, c7.r, 1);
    }

    @Override // j$.util.stream.b, j$.util.stream.BaseStream
    public final /* bridge */ /* synthetic */ Spliterator spliterator() {
        return spliterator();
    }
}
