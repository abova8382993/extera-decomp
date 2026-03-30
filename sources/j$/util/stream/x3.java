package j$.util.stream;

import j$.util.Objects;
import j$.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.LongConsumer;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes2.dex */
public abstract class x3 implements l8 {
    public static final z2 a = new z2();
    public static final x2 b = new x2();
    public static final y2 c = new y2();
    public static final w2 d = new w2();
    public static final int[] e = new int[0];
    public static final long[] f = new long[0];
    public static final double[] g = new double[0];

    public abstract void Z(Spliterator spliterator, o5 o5Var);

    public abstract boolean a0(Spliterator spliterator, o5 o5Var);

    public abstract h2 d0(Spliterator spliterator, boolean z, IntFunction intFunction);

    public abstract long e0(Spliterator spliterator);

    public abstract z1 p0(long j, IntFunction intFunction);

    public abstract s4 r0();

    public abstract o5 s0(Spliterator spliterator, o5 o5Var);

    public abstract o5 t0(o5 o5Var);

    @Override // j$.util.stream.l8
    public /* synthetic */ int u() {
        return 0;
    }

    public abstract Spliterator u0(Spliterator spliterator);

    public static j$.time.s j0(Function function) {
        j$.time.s sVar = new j$.time.s(6);
        sVar.b = function;
        return sVar;
    }

    public static j$.util.concurrent.s q0(u1 u1Var, Predicate predicate) {
        Objects.requireNonNull(predicate);
        Objects.requireNonNull(u1Var);
        return new j$.util.concurrent.s(d7.REFERENCE, u1Var, new o1(u1Var, predicate, 1));
    }

    public static a3 c0(d7 d7Var) {
        int i = i2.a[d7Var.ordinal()];
        if (i == 1) {
            return a;
        }
        if (i == 2) {
            return b;
        }
        if (i == 3) {
            return c;
        }
        if (i == 4) {
            return d;
        }
        throw new IllegalStateException("Unknown shape " + d7Var);
    }

    public static j$.util.concurrent.s n0(u1 u1Var, IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        Objects.requireNonNull(u1Var);
        return new j$.util.concurrent.s(d7.INT_VALUE, u1Var, new o1(u1Var, intPredicate, 0));
    }

    public static h2 S(h2 h2Var, long j, long j2, IntFunction intFunction) {
        if (j == 0 && j2 == h2Var.count()) {
            return h2Var;
        }
        Spliterator spliterator = h2Var.spliterator();
        long j3 = j2 - j;
        z1 z1VarT = T(j3, intFunction);
        z1VarT.m(j3);
        for (int i = 0; i < j && spliterator.tryAdvance(new c1(1)); i++) {
        }
        if (j2 == h2Var.count()) {
            spliterator.forEachRemaining(z1VarT);
        } else {
            for (int i2 = 0; i2 < j3 && spliterator.tryAdvance(z1VarT); i2++) {
            }
        }
        z1VarT.end();
        return z1VarT.build();
    }

    public static j2 Y(d7 d7Var, h2 h2Var, h2 h2Var2) {
        int i = i2.a[d7Var.ordinal()];
        if (i == 1) {
            return new s2(h2Var, h2Var2);
        }
        if (i == 2) {
            return new p2((d2) h2Var, (d2) h2Var2);
        }
        if (i == 3) {
            return new q2((f2) h2Var, (f2) h2Var2);
        }
        if (i != 4) {
            throw new IllegalStateException("Unknown shape " + d7Var);
        }
        return new o2((b2) h2Var, (b2) h2Var2);
    }

    public static j$.util.concurrent.s o0(u1 u1Var) {
        Objects.requireNonNull(null);
        Objects.requireNonNull(u1Var);
        return new j$.util.concurrent.s(d7.LONG_VALUE, u1Var, new n1(u1Var, 0));
    }

    public static void G() {
        throw new IllegalStateException("called wrong accept method");
    }

    public static j$.util.concurrent.s m0(u1 u1Var) {
        Objects.requireNonNull(null);
        Objects.requireNonNull(u1Var);
        return new j$.util.concurrent.s(d7.DOUBLE_VALUE, u1Var, new n1(u1Var, 1));
    }

    public static void H() {
        throw new IllegalStateException("called wrong accept method");
    }

    public static z1 T(long j, IntFunction intFunction) {
        if (j >= 0 && j < 2147483639) {
            return new b3(j, intFunction);
        }
        return new t3();
    }

    public static void z() {
        throw new IllegalStateException("called wrong accept method");
    }

    public static void C(m5 m5Var, Integer num) {
        if (n8.a) {
            n8.a(m5Var.getClass(), "{0} calling Sink.OfInt.accept(Integer)");
            throw null;
        }
        m5Var.accept(num.intValue());
    }

    public static void E(n5 n5Var, Long l) {
        if (n8.a) {
            n8.a(n5Var.getClass(), "{0} calling Sink.OfLong.accept(Long)");
            throw null;
        }
        n5Var.accept(l.longValue());
    }

    public static x1 k0(long j) {
        if (j < 0 || j >= 2147483639) {
            return new e3();
        }
        return new d3(j);
    }

    public static void A(l5 l5Var, Double d2) {
        if (n8.a) {
            n8.a(l5Var.getClass(), "{0} calling Sink.OfDouble.accept(Double)");
            throw null;
        }
        l5Var.accept(d2.doubleValue());
    }

    public static y1 l0(long j) {
        if (j < 0 || j >= 2147483639) {
            return new n3();
        }
        return new m3(j);
    }

    public static Object[] I(g2 g2Var, IntFunction intFunction) {
        if (n8.a) {
            n8.a(g2Var.getClass(), "{0} calling Node.OfPrimitive.asArray");
            throw null;
        }
        if (g2Var.count() >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        Object[] objArr = (Object[]) intFunction.apply((int) g2Var.count());
        g2Var.f(objArr, 0);
        return objArr;
    }

    public static w1 b0(long j) {
        if (j < 0 || j >= 2147483639) {
            return new v2();
        }
        return new u2(j);
    }

    public static h2 U(x3 x3Var, Spliterator spliterator, boolean z, IntFunction intFunction) {
        long jE0 = x3Var.e0(spliterator);
        if (jE0 < 0 || !spliterator.hasCharacteristics(16384)) {
            m0 m0Var = new m0();
            m0Var.a = intFunction;
            h2 h2Var = (h2) new m2(x3Var, spliterator, m0Var, new c1(9), 3).invoke();
            return z ? f0(h2Var, intFunction) : h2Var;
        }
        if (jE0 >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        Object[] objArr = (Object[]) intFunction.apply((int) jE0);
        new r3(spliterator, x3Var, objArr).invoke();
        return new k2(objArr);
    }

    public static void N(d2 d2Var, Consumer consumer) {
        if (consumer instanceof IntConsumer) {
            d2Var.d((IntConsumer) consumer);
        } else {
            if (n8.a) {
                n8.a(d2Var.getClass(), "{0} calling Node.OfInt.forEachRemaining(Consumer)");
                throw null;
            }
            ((Spliterator.OfInt) d2Var.spliterator()).forEachRemaining(consumer);
        }
    }

    public static void K(d2 d2Var, Integer[] numArr, int i) {
        if (n8.a) {
            n8.a(d2Var.getClass(), "{0} calling Node.OfInt.copyInto(Integer[], int)");
            throw null;
        }
        int[] iArr = (int[]) d2Var.b();
        for (int i2 = 0; i2 < iArr.length; i2++) {
            numArr[i + i2] = Integer.valueOf(iArr[i2]);
        }
    }

    public static d2 Q(d2 d2Var, long j, long j2) {
        if (j == 0 && j2 == d2Var.count()) {
            return d2Var;
        }
        long j3 = j2 - j;
        Spliterator.OfInt ofInt = (Spliterator.OfInt) d2Var.spliterator();
        x1 x1VarK0 = k0(j3);
        x1VarK0.m(j3);
        for (int i = 0; i < j && ofInt.tryAdvance((IntConsumer) new c2(0)); i++) {
        }
        if (j2 == d2Var.count()) {
            ofInt.forEachRemaining((IntConsumer) x1VarK0);
        } else {
            for (int i2 = 0; i2 < j3 && ofInt.tryAdvance((IntConsumer) x1VarK0); i2++) {
            }
        }
        x1VarK0.end();
        return x1VarK0.build();
    }

    public static d2 W(x3 x3Var, Spliterator spliterator, boolean z) {
        long jE0 = x3Var.e0(spliterator);
        if (jE0 < 0 || !spliterator.hasCharacteristics(16384)) {
            d2 d2Var = (d2) new m2(x3Var, spliterator, new c1(5), new c1(6), 1).invoke();
            return z ? h0(d2Var) : d2Var;
        }
        if (jE0 >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        int[] iArr = new int[(int) jE0];
        new p3(spliterator, x3Var, iArr).invoke();
        return new c3(iArr);
    }

    public static f2 X(x3 x3Var, Spliterator spliterator, boolean z) {
        long jE0 = x3Var.e0(spliterator);
        if (jE0 < 0 || !spliterator.hasCharacteristics(16384)) {
            f2 f2Var = (f2) new m2(x3Var, spliterator, new c1(7), new c1(8), 2).invoke();
            return z ? i0(f2Var) : f2Var;
        }
        if (jE0 >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        long[] jArr = new long[(int) jE0];
        new q3(spliterator, x3Var, jArr).invoke();
        return new l3(jArr);
    }

    public static void O(f2 f2Var, Consumer consumer) {
        if (consumer instanceof LongConsumer) {
            f2Var.d((LongConsumer) consumer);
        } else {
            if (n8.a) {
                n8.a(f2Var.getClass(), "{0} calling Node.OfLong.forEachRemaining(Consumer)");
                throw null;
            }
            ((j$.util.y0) f2Var.spliterator()).forEachRemaining(consumer);
        }
    }

    public static void L(f2 f2Var, Long[] lArr, int i) {
        if (n8.a) {
            n8.a(f2Var.getClass(), "{0} calling Node.OfInt.copyInto(Long[], int)");
            throw null;
        }
        long[] jArr = (long[]) f2Var.b();
        for (int i2 = 0; i2 < jArr.length; i2++) {
            lArr[i + i2] = Long.valueOf(jArr[i2]);
        }
    }

    public static f2 R(f2 f2Var, long j, long j2) {
        if (j == 0 && j2 == f2Var.count()) {
            return f2Var;
        }
        long j3 = j2 - j;
        j$.util.y0 y0Var = (j$.util.y0) f2Var.spliterator();
        y1 y1VarL0 = l0(j3);
        y1VarL0.m(j3);
        for (int i = 0; i < j && y0Var.tryAdvance((LongConsumer) new e2(0)); i++) {
        }
        if (j2 == f2Var.count()) {
            y0Var.forEachRemaining((LongConsumer) y1VarL0);
        } else {
            for (int i2 = 0; i2 < j3 && y0Var.tryAdvance((LongConsumer) y1VarL0); i2++) {
            }
        }
        y1VarL0.end();
        return y1VarL0.build();
    }

    public static b2 V(x3 x3Var, Spliterator spliterator, boolean z) {
        long jE0 = x3Var.e0(spliterator);
        if (jE0 < 0 || !spliterator.hasCharacteristics(16384)) {
            b2 b2Var = (b2) new m2(x3Var, spliterator, new c1(3), new c1(4), 0).invoke();
            return z ? g0(b2Var) : b2Var;
        }
        if (jE0 >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        double[] dArr = new double[(int) jE0];
        new o3(spliterator, x3Var, dArr).invoke();
        return new t2(dArr);
    }

    public static h2 f0(h2 h2Var, IntFunction intFunction) {
        if (h2Var.h() <= 0) {
            return h2Var;
        }
        long jCount = h2Var.count();
        if (jCount >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        Object[] objArr = (Object[]) intFunction.apply((int) jCount);
        new w3(h2Var, objArr, 1).invoke();
        return new k2(objArr);
    }

    public static void M(b2 b2Var, Consumer consumer) {
        if (consumer instanceof DoubleConsumer) {
            b2Var.d((DoubleConsumer) consumer);
        } else {
            if (n8.a) {
                n8.a(b2Var.getClass(), "{0} calling Node.OfLong.forEachRemaining(Consumer)");
                throw null;
            }
            ((j$.util.t0) b2Var.spliterator()).forEachRemaining(consumer);
        }
    }

    public static d2 h0(d2 d2Var) {
        if (d2Var.h() <= 0) {
            return d2Var;
        }
        long jCount = d2Var.count();
        if (jCount >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        int[] iArr = new int[(int) jCount];
        new v3(d2Var, iArr, 0).invoke();
        return new c3(iArr);
    }

    public static void J(b2 b2Var, Double[] dArr, int i) {
        if (n8.a) {
            n8.a(b2Var.getClass(), "{0} calling Node.OfDouble.copyInto(Double[], int)");
            throw null;
        }
        double[] dArr2 = (double[]) b2Var.b();
        for (int i2 = 0; i2 < dArr2.length; i2++) {
            dArr[i + i2] = Double.valueOf(dArr2[i2]);
        }
    }

    public static b2 P(b2 b2Var, long j, long j2) {
        if (j == 0 && j2 == b2Var.count()) {
            return b2Var;
        }
        long j3 = j2 - j;
        j$.util.t0 t0Var = (j$.util.t0) b2Var.spliterator();
        w1 w1VarB0 = b0(j3);
        w1VarB0.m(j3);
        for (int i = 0; i < j && t0Var.tryAdvance((DoubleConsumer) new a2(0)); i++) {
        }
        if (j2 == b2Var.count()) {
            t0Var.forEachRemaining((DoubleConsumer) w1VarB0);
        } else {
            for (int i2 = 0; i2 < j3 && t0Var.tryAdvance((DoubleConsumer) w1VarB0); i2++) {
            }
        }
        w1VarB0.end();
        return w1VarB0.build();
    }

    public static f2 i0(f2 f2Var) {
        if (f2Var.h() <= 0) {
            return f2Var;
        }
        long jCount = f2Var.count();
        if (jCount >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        long[] jArr = new long[(int) jCount];
        new u3(f2Var, jArr, 0).invoke();
        return new l3(jArr);
    }

    public static b2 g0(b2 b2Var) {
        if (b2Var.h() <= 0) {
            return b2Var;
        }
        long jCount = b2Var.count();
        if (jCount >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        double[] dArr = new double[(int) jCount];
        new u3(b2Var, dArr, 0).invoke();
        return new t2(dArr);
    }

    @Override // j$.util.stream.l8
    public Object f(b bVar, Spliterator spliterator) {
        s4 s4VarR0 = r0();
        bVar.s0(spliterator, s4VarR0);
        return s4VarR0.get();
    }

    @Override // j$.util.stream.l8
    public Object i(x3 x3Var, Spliterator spliterator) {
        return ((s4) new z4(this, x3Var, spliterator).invoke()).get();
    }
}
