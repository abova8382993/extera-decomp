package j$.util.stream;

import j$.util.Objects;
import j$.util.Spliterator;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.IntFunction;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes2.dex */
public abstract class b0 extends b implements e0 {
    @Override // j$.util.stream.e0
    public final j$.util.b0 findAny() {
        return (j$.util.b0) v0(g0.d);
    }

    @Override // j$.util.stream.e0
    public final j$.util.b0 findFirst() {
        return (j$.util.b0) v0(g0.c);
    }

    @Override // j$.util.stream.e0
    public final e0 sorted() {
        return new h6(this, c7.q | c7.o, 0);
    }

    public static j$.util.t0 H0(Spliterator spliterator) {
        if (spliterator instanceof j$.util.t0) {
            return (j$.util.t0) spliterator;
        }
        if (n8.a) {
            n8.a(b.class, "using DoubleStream.adapt(Spliterator<Double> s)");
            throw null;
        }
        throw new UnsupportedOperationException("DoubleStream.adapt(Spliterator<Double> s)");
    }

    @Override // j$.util.stream.e0
    public void forEach(DoubleConsumer doubleConsumer) {
        Objects.requireNonNull(doubleConsumer);
        v0(new n0(doubleConsumer, false));
    }

    @Override // j$.util.stream.e0
    public void forEachOrdered(DoubleConsumer doubleConsumer) {
        Objects.requireNonNull(doubleConsumer);
        v0(new n0(doubleConsumer, true));
    }

    @Override // j$.util.stream.b
    public final d7 z0() {
        return d7.DOUBLE_VALUE;
    }

    @Override // j$.util.stream.b
    public final h2 x0(b bVar, Spliterator spliterator, boolean z, IntFunction intFunction) {
        return x3.V(bVar, spliterator, z);
    }

    @Override // j$.util.stream.b
    public final Spliterator G0(b bVar, Supplier supplier, boolean z) {
        return new n7(bVar, supplier, z);
    }

    @Override // j$.util.stream.b
    public final boolean y0(Spliterator spliterator, o5 o5Var) {
        DoubleConsumer d0Var;
        boolean zP;
        j$.util.t0 t0VarH0 = H0(spliterator);
        if (o5Var instanceof DoubleConsumer) {
            d0Var = (DoubleConsumer) o5Var;
        } else {
            if (n8.a) {
                n8.a(b.class, "using DoubleStream.adapt(Sink<Double> s)");
                throw null;
            }
            Objects.requireNonNull(o5Var);
            d0Var = new j$.util.d0(o5Var, 1);
        }
        do {
            zP = o5Var.p();
            if (zP) {
                break;
            }
        } while (t0VarH0.tryAdvance(d0Var));
        return zP;
    }

    @Override // j$.util.stream.x3
    public final z1 p0(long j, IntFunction intFunction) {
        return x3.b0(j);
    }

    @Override // j$.util.stream.BaseStream
    public final j$.util.g0 iterator() {
        j$.util.t0 t0VarSpliterator = spliterator();
        Objects.requireNonNull(t0VarSpliterator);
        return new j$.util.g1(t0VarSpliterator);
    }

    @Override // j$.util.stream.b, j$.util.stream.BaseStream
    public final j$.util.t0 spliterator() {
        return H0(super.spliterator());
    }

    @Override // j$.util.stream.e0
    public final Stream boxed() {
        return new s(this, 0, new p(1), 0);
    }

    @Override // j$.util.stream.e0
    public final e0 e() {
        Objects.requireNonNull(null);
        return new t(this, c7.p | c7.n, 0);
    }

    @Override // j$.util.stream.e0
    public final Stream mapToObj(DoubleFunction doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        return new s(this, c7.p | c7.n, doubleFunction, 0);
    }

    @Override // j$.util.stream.e0
    public final IntStream w() {
        Objects.requireNonNull(null);
        return new u(this, c7.p | c7.n, 0);
    }

    @Override // j$.util.stream.e0
    public final LongStream r() {
        Objects.requireNonNull(null);
        return new v(this, c7.p | c7.n, 0);
    }

    @Override // j$.util.stream.e0
    public final e0 a(j$.time.s sVar) {
        Objects.requireNonNull(sVar);
        return new x(this, c7.p | c7.n | c7.t, sVar, 0);
    }

    @Override // j$.util.stream.e0
    public final e0 c() {
        Objects.requireNonNull(null);
        return new t(this, c7.t, 2);
    }

    @Override // j$.util.stream.e0
    public final e0 peek(DoubleConsumer doubleConsumer) {
        Objects.requireNonNull(doubleConsumer);
        return new x(this, doubleConsumer);
    }

    @Override // j$.util.stream.e0
    public final e0 limit(long j) {
        if (j < 0) {
            throw new IllegalArgumentException(Long.toString(j));
        }
        return z5.e(this, 0L, j);
    }

    @Override // j$.util.stream.e0
    public final e0 skip(long j) {
        if (j >= 0) {
            return j == 0 ? this : z5.e(this, j, -1L);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    @Override // j$.util.stream.e0
    public final e0 b() {
        int i = j9.a;
        Objects.requireNonNull(null);
        return new h6(this, j9.a, 1);
    }

    @Override // j$.util.stream.e0
    public final e0 d() {
        int i = j9.a;
        Objects.requireNonNull(null);
        return new h6(this, j9.b, 2);
    }

    @Override // j$.util.stream.e0
    public final e0 distinct() {
        return ((g5) boxed()).distinct().mapToDouble(new p(2));
    }

    @Override // j$.util.stream.e0
    public final double sum() {
        double[] dArr = (double[]) collect(new j(8), new p(5), new j$.time.format.a(25));
        Set set = Collectors.a;
        double d = dArr[0] + dArr[1];
        double d2 = dArr[dArr.length - 1];
        return (Double.isNaN(d) && Double.isInfinite(d2)) ? d2 : d;
    }

    @Override // j$.util.stream.e0
    public final j$.util.b0 min() {
        return reduce(new j$.time.format.a(26));
    }

    @Override // j$.util.stream.e0
    public final j$.util.b0 max() {
        return reduce(new p(4));
    }

    @Override // j$.util.stream.e0
    public final j$.util.b0 average() {
        double[] dArr = (double[]) collect(new j(7), new j$.time.format.a(27), new j$.time.format.a(28));
        if (dArr[2] <= 0.0d) {
            return j$.util.b0.c;
        }
        Set set = Collectors.a;
        double d = dArr[0] + dArr[1];
        double d2 = dArr[dArr.length - 1];
        if (Double.isNaN(d) && Double.isInfinite(d2)) {
            d = d2;
        }
        return new j$.util.b0(d / dArr[2]);
    }

    @Override // j$.util.stream.e0
    public final j$.util.w summaryStatistics() {
        return (j$.util.w) collect(new j(0), new j$.time.format.a(29), new p(0));
    }

    @Override // j$.util.stream.e0
    public final Object collect(Supplier supplier, ObjDoubleConsumer objDoubleConsumer, BiConsumer biConsumer) {
        Objects.requireNonNull(biConsumer);
        q qVar = new q(biConsumer, 0);
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(objDoubleConsumer);
        Objects.requireNonNull(qVar);
        return v0(new c4(d7.DOUBLE_VALUE, qVar, objDoubleConsumer, supplier, 1));
    }

    @Override // j$.util.stream.e0
    public final boolean l() {
        return ((Boolean) v0(x3.m0(u1.ANY))).booleanValue();
    }

    @Override // j$.util.stream.e0
    public final boolean q() {
        return ((Boolean) v0(x3.m0(u1.ALL))).booleanValue();
    }

    @Override // j$.util.stream.e0
    public final boolean y() {
        return ((Boolean) v0(x3.m0(u1.NONE))).booleanValue();
    }

    @Override // j$.util.stream.e0
    public final double[] toArray() {
        return (double[]) x3.g0((b2) w0(new p(3))).b();
    }

    @Override // j$.util.stream.e0
    public final double reduce(double d, DoubleBinaryOperator doubleBinaryOperator) {
        Objects.requireNonNull(doubleBinaryOperator);
        return ((Double) v0(new g4(d7.DOUBLE_VALUE, doubleBinaryOperator, d))).doubleValue();
    }

    @Override // j$.util.stream.e0
    public final j$.util.b0 reduce(DoubleBinaryOperator doubleBinaryOperator) {
        Objects.requireNonNull(doubleBinaryOperator);
        return (j$.util.b0) v0(new a4(d7.DOUBLE_VALUE, doubleBinaryOperator, 1));
    }

    @Override // j$.util.stream.e0
    public final long count() {
        return ((Long) v0(new e4(1))).longValue();
    }
}
