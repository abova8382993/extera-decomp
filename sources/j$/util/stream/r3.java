package j$.util.stream;

import j$.util.Spliterator;

/* JADX INFO: loaded from: classes2.dex */
public final class r3 extends s3 {
    public final Object[] h;

    public r3(Spliterator spliterator, x3 x3Var, Object[] objArr) {
        super(spliterator, x3Var, objArr.length);
        this.h = objArr;
    }

    public r3(r3 r3Var, Spliterator spliterator, long j, long j2) {
        super(r3Var, spliterator, j, j2, r3Var.h.length);
        this.h = r3Var.h;
    }

    @Override // j$.util.stream.s3
    public final s3 a(Spliterator spliterator, long j, long j2) {
        return new r3(this, spliterator, j, j2);
    }

    @Override // java.util.function.Consumer
    /* JADX INFO: renamed from: accept */
    public final void v(Object obj) {
        int i = this.f;
        if (i >= this.g) {
            throw new IndexOutOfBoundsException(Integer.toString(this.f));
        }
        Object[] objArr = this.h;
        this.f = i + 1;
        objArr[i] = obj;
    }
}
