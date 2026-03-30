package j$.util.stream;

import j$.util.Spliterator;
import java.util.function.LongConsumer;

/* JADX INFO: loaded from: classes2.dex */
public final class q3 extends s3 implements n5 {
    public final long[] h;

    @Override // java.util.function.Consumer
    /* JADX INFO: renamed from: accept */
    public final /* bridge */ /* synthetic */ void v(Object obj) {
        v((Long) obj);
    }

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return j$.com.android.tools.r8.a.d(this, longConsumer);
    }

    @Override // j$.util.stream.n5
    public final /* synthetic */ void v(Long l) {
        x3.E(this, l);
    }

    public q3(Spliterator spliterator, x3 x3Var, long[] jArr) {
        super(spliterator, x3Var, jArr.length);
        this.h = jArr;
    }

    public q3(q3 q3Var, Spliterator spliterator, long j, long j2) {
        super(q3Var, spliterator, j, j2, q3Var.h.length);
        this.h = q3Var.h;
    }

    @Override // j$.util.stream.s3
    public final s3 a(Spliterator spliterator, long j, long j2) {
        return new q3(this, spliterator, j, j2);
    }

    @Override // j$.util.stream.s3, j$.util.stream.o5
    public final void accept(long j) {
        int i = this.f;
        if (i >= this.g) {
            throw new IndexOutOfBoundsException(Integer.toString(this.f));
        }
        long[] jArr = this.h;
        this.f = i + 1;
        jArr[i] = j;
    }
}
