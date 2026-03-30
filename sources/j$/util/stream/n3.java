package j$.util.stream;

import j$.util.Spliterator;
import j$.util.function.Consumer$CC;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.LongConsumer;

/* JADX INFO: loaded from: classes2.dex */
public final class n3 extends w6 implements f2, y1 {
    @Override // j$.util.stream.o5
    public final /* synthetic */ void accept(double d) {
        x3.z();
        throw null;
    }

    @Override // j$.util.stream.o5
    public final /* synthetic */ void accept(int i) {
        x3.G();
        throw null;
    }

    @Override // java.util.function.Consumer
    /* JADX INFO: renamed from: accept */
    public final /* bridge */ /* synthetic */ void v(Object obj) {
        v((Long) obj);
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    @Override // j$.util.stream.y1, j$.util.stream.z1
    public final f2 build() {
        return this;
    }

    @Override // j$.util.stream.z1
    public final h2 build() {
        return this;
    }

    @Override // j$.util.stream.h2
    public final /* synthetic */ h2 e(long j, long j2, IntFunction intFunction) {
        return x3.R(this, j, j2);
    }

    @Override // j$.util.stream.o5
    public final void end() {
    }

    @Override // j$.util.stream.h2
    public final /* synthetic */ Object[] g(IntFunction intFunction) {
        return x3.I(this, intFunction);
    }

    @Override // j$.util.stream.h2
    public final /* synthetic */ int h() {
        return 0;
    }

    @Override // j$.util.stream.o5
    public final /* synthetic */ boolean p() {
        return false;
    }

    @Override // j$.util.stream.n5
    public final /* synthetic */ void v(Long l) {
        x3.E(this, l);
    }

    @Override // j$.util.stream.h2
    public final /* bridge */ /* synthetic */ h2 a(int i) {
        a(i);
        throw null;
    }

    @Override // j$.util.stream.g2, j$.util.stream.h2
    public final g2 a(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // j$.util.stream.h2
    public final /* synthetic */ void f(Object[] objArr, int i) {
        x3.L(this, (Long[]) objArr, i);
    }

    @Override // j$.util.stream.y6, j$.util.stream.g2
    public final void c(int i, Object obj) {
        super.c(i, (long[]) obj);
    }

    @Override // j$.util.stream.y6, j$.util.stream.g2
    public final void d(Object obj) {
        super.d((LongConsumer) obj);
    }

    @Override // j$.util.stream.w6, j$.util.stream.y6, java.lang.Iterable, j$.util.stream.h2
    public final Spliterator spliterator() {
        return super.spliterator();
    }

    @Override // j$.util.stream.w6, j$.util.stream.y6, java.lang.Iterable, j$.util.stream.h2
    public final j$.util.b1 spliterator() {
        return super.spliterator();
    }

    @Override // j$.util.stream.o5
    public final void m(long j) {
        clear();
        l(j);
    }

    @Override // j$.util.stream.y6, j$.util.stream.g2
    public final Object b() {
        return (long[]) super.b();
    }
}
