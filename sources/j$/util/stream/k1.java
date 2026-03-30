package j$.util.stream;

import j$.util.Objects;
import j$.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes2.dex */
public abstract class k1 extends b implements LongStream {
    @Override // j$.util.stream.LongStream
    public final j$.util.c0 findAny() {
        return (j$.util.c0) v0(i0.d);
    }

    @Override // j$.util.stream.LongStream
    public final j$.util.c0 findFirst() {
        return (j$.util.c0) v0(i0.c);
    }

    @Override // j$.util.stream.LongStream
    public final LongStream sorted() {
        return new j6(this, c7.q | c7.o, 0);
    }

    public void forEach(LongConsumer longConsumer) {
        Objects.requireNonNull(longConsumer);
        v0(new p0(longConsumer, false));
    }

    public void forEachOrdered(LongConsumer longConsumer) {
        Objects.requireNonNull(longConsumer);
        v0(new p0(longConsumer, true));
    }

    public static j$.util.y0 H0(Spliterator spliterator) {
        if (spliterator instanceof j$.util.y0) {
            return (j$.util.y0) spliterator;
        }
        if (n8.a) {
            n8.a(b.class, "using LongStream.adapt(Spliterator<Long> s)");
            throw null;
        }
        throw new UnsupportedOperationException("LongStream.adapt(Spliterator<Long> s)");
    }

    @Override // j$.util.stream.b
    public final d7 z0() {
        return d7.LONG_VALUE;
    }

    @Override // j$.util.stream.b
    public final h2 x0(b bVar, Spliterator spliterator, boolean z, IntFunction intFunction) {
        return x3.X(bVar, spliterator, z);
    }

    @Override // j$.util.stream.b
    public final Spliterator G0(b bVar, Supplier supplier, boolean z) {
        return new r7(bVar, supplier, z);
    }

    @Override // j$.util.stream.b
    public final boolean y0(Spliterator spliterator, o5 o5Var) {
        LongConsumer l0Var;
        boolean zP;
        j$.util.y0 y0VarH0 = H0(spliterator);
        if (o5Var instanceof LongConsumer) {
            l0Var = (LongConsumer) o5Var;
        } else {
            if (n8.a) {
                n8.a(b.class, "using LongStream.adapt(Sink<Long> s)");
                throw null;
            }
            Objects.requireNonNull(o5Var);
            l0Var = new j$.util.l0(o5Var, 1);
        }
        do {
            zP = o5Var.p();
            if (zP) {
                break;
            }
        } while (y0VarH0.tryAdvance(l0Var));
        return zP;
    }

    @Override // j$.util.stream.x3
    public final z1 p0(long j, IntFunction intFunction) {
        return x3.l0(j);
    }

    @Override // j$.util.stream.BaseStream
    public final j$.util.o0 iterator() {
        j$.util.y0 y0VarSpliterator = spliterator();
        Objects.requireNonNull(y0VarSpliterator);
        return new j$.util.f1(y0VarSpliterator);
    }

    @Override // j$.util.stream.b, j$.util.stream.BaseStream
    public final j$.util.y0 spliterator() {
        return H0(super.spliterator());
    }

    @Override // j$.util.stream.LongStream
    public final e0 asDoubleStream() {
        return new t(this, c7.n, 5);
    }

    @Override // j$.util.stream.LongStream
    public final Stream boxed() {
        return new s(this, 0, new p(26), 2);
    }

    @Override // j$.util.stream.LongStream
    public final LongStream e() {
        Objects.requireNonNull(null);
        return new v(this, c7.p | c7.n, 3);
    }

    @Override // j$.util.stream.LongStream
    public final Stream mapToObj(LongFunction longFunction) {
        Objects.requireNonNull(longFunction);
        return new s(this, c7.p | c7.n, longFunction, 2);
    }

    @Override // j$.util.stream.LongStream
    public final IntStream x() {
        Objects.requireNonNull(null);
        return new u(this, c7.p | c7.n, 2);
    }

    @Override // j$.util.stream.LongStream
    public final e0 j() {
        Objects.requireNonNull(null);
        return new t(this, c7.p | c7.n, 6);
    }

    @Override // j$.util.stream.LongStream
    public final LongStream a(j$.time.s sVar) {
        Objects.requireNonNull(sVar);
        return new g1(this, c7.p | c7.n | c7.t, sVar, 0);
    }

    @Override // j$.util.stream.LongStream
    public final LongStream c() {
        Objects.requireNonNull(null);
        return new v(this, c7.t, 5);
    }

    @Override // j$.util.stream.LongStream
    public final LongStream peek(LongConsumer longConsumer) {
        Objects.requireNonNull(longConsumer);
        return new g1(this, longConsumer);
    }

    @Override // j$.util.stream.LongStream
    public final LongStream limit(long j) {
        if (j < 0) {
            throw new IllegalArgumentException(Long.toString(j));
        }
        return z5.g(this, 0L, j);
    }

    @Override // j$.util.stream.LongStream
    public final LongStream skip(long j) {
        if (j >= 0) {
            return j == 0 ? this : z5.g(this, j, -1L);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    @Override // j$.util.stream.LongStream
    public final LongStream b() {
        int i = j9.a;
        Objects.requireNonNull(null);
        return new j6(this, j9.a, 1);
    }

    @Override // j$.util.stream.LongStream
    public final LongStream d() {
        int i = j9.a;
        Objects.requireNonNull(null);
        return new j6(this, j9.b, 2);
    }

    @Override // j$.util.stream.LongStream
    public final LongStream distinct() {
        return ((g5) boxed()).distinct().mapToLong(new p(23));
    }

    @Override // j$.util.stream.LongStream
    public final long sum() {
        return reduce(0L, new c1(0));
    }

    @Override // j$.util.stream.LongStream
    public final j$.util.c0 min() {
        return reduce(new p(22));
    }

    @Override // j$.util.stream.LongStream
    public final j$.util.c0 max() {
        return reduce(new p(29));
    }

    @Override // j$.util.stream.LongStream
    public final j$.util.b0 average() {
        long j = ((long[]) collect(new j(14), new p(27), new p(28)))[0];
        return j > 0 ? new j$.util.b0(r0[1] / j) : j$.util.b0.c;
    }

    @Override // j$.util.stream.LongStream
    public final long reduce(long j, LongBinaryOperator longBinaryOperator) {
        Objects.requireNonNull(longBinaryOperator);
        return ((Long) v0(new y3(d7.LONG_VALUE, longBinaryOperator, j))).longValue();
    }

    @Override // j$.util.stream.LongStream
    public final j$.util.z summaryStatistics() {
        return (j$.util.z) collect(new j(4), new p(21), new p(24));
    }

    @Override // j$.util.stream.LongStream
    public final Object collect(Supplier supplier, ObjLongConsumer objLongConsumer, BiConsumer biConsumer) {
        Objects.requireNonNull(biConsumer);
        q qVar = new q(biConsumer, 2);
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(objLongConsumer);
        Objects.requireNonNull(qVar);
        return v0(new c4(d7.LONG_VALUE, qVar, objLongConsumer, supplier, 0));
    }

    @Override // j$.util.stream.LongStream
    public final boolean v() {
        return ((Boolean) v0(x3.o0(u1.ANY))).booleanValue();
    }

    @Override // j$.util.stream.LongStream
    public final boolean m() {
        return ((Boolean) v0(x3.o0(u1.ALL))).booleanValue();
    }

    @Override // j$.util.stream.LongStream
    public final j$.util.c0 reduce(LongBinaryOperator longBinaryOperator) {
        Objects.requireNonNull(longBinaryOperator);
        return (j$.util.c0) v0(new a4(d7.LONG_VALUE, longBinaryOperator, 0));
    }

    @Override // j$.util.stream.LongStream
    public final boolean p() {
        return ((Boolean) v0(x3.o0(u1.NONE))).booleanValue();
    }

    @Override // j$.util.stream.LongStream
    public final long[] toArray() {
        return (long[]) x3.i0((f2) w0(new p(25))).b();
    }

    @Override // j$.util.stream.LongStream
    public final long count() {
        return ((Long) v0(new e4(0))).longValue();
    }
}
