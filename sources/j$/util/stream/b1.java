package j$.util.stream;

import j$.util.Objects;
import j$.util.OptionalInt;
import j$.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes2.dex */
public abstract class b1 extends b implements IntStream {
    @Override // j$.util.stream.IntStream
    public final OptionalInt findAny() {
        return (OptionalInt) v0(h0.d);
    }

    @Override // j$.util.stream.IntStream
    public final OptionalInt findFirst() {
        return (OptionalInt) v0(h0.c);
    }

    @Override // j$.util.stream.IntStream
    public final IntStream sorted() {
        return new i6(this, c7.q | c7.o);
    }

    @Override // j$.util.stream.IntStream
    public void forEach(IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        v0(new o0(intConsumer, false));
    }

    @Override // j$.util.stream.IntStream
    public void forEachOrdered(IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        v0(new o0(intConsumer, true));
    }

    public static Spliterator.OfInt H0(Spliterator spliterator) {
        if (spliterator instanceof Spliterator.OfInt) {
            return (Spliterator.OfInt) spliterator;
        }
        if (n8.a) {
            n8.a(b.class, "using IntStream.adapt(Spliterator<Integer> s)");
            throw null;
        }
        throw new UnsupportedOperationException("IntStream.adapt(Spliterator<Integer> s)");
    }

    @Override // j$.util.stream.b
    public final d7 z0() {
        return d7.INT_VALUE;
    }

    @Override // j$.util.stream.b
    public final h2 x0(b bVar, Spliterator spliterator, boolean z, IntFunction intFunction) {
        return x3.W(bVar, spliterator, z);
    }

    @Override // j$.util.stream.b
    public final Spliterator G0(b bVar, Supplier supplier, boolean z) {
        return new p7(bVar, supplier, z);
    }

    @Override // j$.util.stream.b
    public final boolean y0(Spliterator spliterator, o5 o5Var) {
        IntConsumer h0Var;
        boolean zP;
        Spliterator.OfInt ofIntH0 = H0(spliterator);
        if (o5Var instanceof IntConsumer) {
            h0Var = (IntConsumer) o5Var;
        } else {
            if (n8.a) {
                n8.a(b.class, "using IntStream.adapt(Sink<Integer> s)");
                throw null;
            }
            Objects.requireNonNull(o5Var);
            h0Var = new j$.util.h0(o5Var, 1);
        }
        do {
            zP = o5Var.p();
            if (zP) {
                break;
            }
        } while (ofIntH0.tryAdvance(h0Var));
        return zP;
    }

    @Override // j$.util.stream.x3
    public final z1 p0(long j, IntFunction intFunction) {
        return x3.k0(j);
    }

    @Override // j$.util.stream.BaseStream
    public final j$.util.k0 iterator() {
        Spliterator.OfInt ofIntSpliterator = spliterator();
        Objects.requireNonNull(ofIntSpliterator);
        return new j$.util.e1(ofIntSpliterator);
    }

    @Override // j$.util.stream.b, j$.util.stream.BaseStream
    public final Spliterator.OfInt spliterator() {
        return H0(super.spliterator());
    }

    @Override // j$.util.stream.IntStream
    public final LongStream asLongStream() {
        return new v(this, 0, 1);
    }

    @Override // j$.util.stream.IntStream
    public final e0 asDoubleStream() {
        return new t(this, 0, 3);
    }

    @Override // j$.util.stream.IntStream
    public final Stream boxed() {
        return new s(this, 0, new p(13), 1);
    }

    @Override // j$.util.stream.IntStream
    public final IntStream map(IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        return new v0(this, c7.p | c7.n, intUnaryOperator, 1);
    }

    @Override // j$.util.stream.IntStream
    public final Stream mapToObj(IntFunction intFunction) {
        Objects.requireNonNull(intFunction);
        return new s(this, c7.p | c7.n, intFunction, 1);
    }

    @Override // j$.util.stream.IntStream
    public final LongStream k() {
        Objects.requireNonNull(null);
        return new v(this, c7.p | c7.n, 2);
    }

    @Override // j$.util.stream.IntStream
    public final e0 g() {
        Objects.requireNonNull(null);
        return new t(this, c7.p | c7.n, 4);
    }

    @Override // j$.util.stream.IntStream
    public final int reduce(int i, IntBinaryOperator intBinaryOperator) {
        Objects.requireNonNull(intBinaryOperator);
        return ((Integer) v0(new n4(d7.INT_VALUE, intBinaryOperator, i))).intValue();
    }

    @Override // j$.util.stream.IntStream
    public final IntStream o(m0 m0Var) {
        Objects.requireNonNull(m0Var);
        return new v0(this, c7.p | c7.n | c7.t, m0Var, 2);
    }

    @Override // j$.util.stream.IntStream
    public final OptionalInt reduce(IntBinaryOperator intBinaryOperator) {
        Objects.requireNonNull(intBinaryOperator);
        return (OptionalInt) v0(new a4(d7.INT_VALUE, intBinaryOperator, 3));
    }

    @Override // j$.util.stream.IntStream
    public final IntStream filter(IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        return new v0(this, c7.t, intPredicate, 3);
    }

    @Override // j$.util.stream.IntStream
    public final IntStream peek(IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        return new v0(this, intConsumer);
    }

    @Override // j$.util.stream.IntStream
    public final IntStream limit(long j) {
        if (j < 0) {
            throw new IllegalArgumentException(Long.toString(j));
        }
        return z5.f(this, 0L, j);
    }

    @Override // j$.util.stream.IntStream
    public final IntStream skip(long j) {
        if (j >= 0) {
            return j == 0 ? this : z5.f(this, j, -1L);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    @Override // j$.util.stream.IntStream
    public final IntStream takeWhile(IntPredicate intPredicate) {
        int i = j9.a;
        Objects.requireNonNull(intPredicate);
        return new r8(this, j9.a, intPredicate);
    }

    @Override // j$.util.stream.IntStream
    public final IntStream dropWhile(IntPredicate intPredicate) {
        int i = j9.a;
        Objects.requireNonNull(intPredicate);
        return new t8(this, j9.b, intPredicate);
    }

    @Override // j$.util.stream.IntStream
    public final long count() {
        return ((Long) v0(new e4(3))).longValue();
    }

    @Override // j$.util.stream.IntStream
    public final IntStream distinct() {
        return ((g5) boxed()).distinct().mapToInt(new p(12));
    }

    @Override // j$.util.stream.IntStream
    public final int sum() {
        return reduce(0, new p(17));
    }

    @Override // j$.util.stream.IntStream
    public final OptionalInt min() {
        return reduce(new p(14));
    }

    @Override // j$.util.stream.IntStream
    public final OptionalInt max() {
        return reduce(new p(18));
    }

    @Override // j$.util.stream.IntStream
    public final j$.util.b0 average() {
        long j = ((long[]) collect(new j(13), new p(19), new p(20)))[0];
        return j > 0 ? new j$.util.b0(r0[1] / j) : j$.util.b0.c;
    }

    @Override // j$.util.stream.IntStream
    public final j$.util.x summaryStatistics() {
        return (j$.util.x) collect(new j(2), new p(15), new p(16));
    }

    @Override // j$.util.stream.IntStream
    public final Object collect(Supplier supplier, ObjIntConsumer objIntConsumer, BiConsumer biConsumer) {
        Objects.requireNonNull(biConsumer);
        q qVar = new q(biConsumer, 1);
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(objIntConsumer);
        Objects.requireNonNull(qVar);
        return v0(new c4(d7.INT_VALUE, qVar, objIntConsumer, supplier, 4));
    }

    @Override // j$.util.stream.IntStream
    public final boolean anyMatch(IntPredicate intPredicate) {
        return ((Boolean) v0(x3.n0(u1.ANY, intPredicate))).booleanValue();
    }

    @Override // j$.util.stream.IntStream
    public final boolean allMatch(IntPredicate intPredicate) {
        return ((Boolean) v0(x3.n0(u1.ALL, intPredicate))).booleanValue();
    }

    @Override // j$.util.stream.IntStream
    public final boolean noneMatch(IntPredicate intPredicate) {
        return ((Boolean) v0(x3.n0(u1.NONE, intPredicate))).booleanValue();
    }

    @Override // j$.util.stream.IntStream
    public final int[] toArray() {
        return (int[]) x3.h0((d2) w0(new p(11))).b();
    }
}
