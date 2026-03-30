package j$.util.stream;

import j$.util.Objects;
import j$.util.Optional;
import j$.util.Spliterator;
import j$.util.stream.Collector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/* JADX INFO: loaded from: classes2.dex */
public abstract class g5 extends b implements Stream {
    @Override // j$.util.stream.Stream
    public final Stream sorted() {
        return new k6(this);
    }

    @Override // j$.util.stream.Stream
    public final Stream distinct() {
        return new o(this, c7.m | c7.t);
    }

    @Override // j$.util.stream.Stream
    public final Optional min(Comparator comparator) {
        Objects.requireNonNull(comparator);
        return reduce(new j$.util.function.a(comparator, 1));
    }

    @Override // j$.util.stream.Stream
    public final Optional findAny() {
        return (Optional) v0(j0.d);
    }

    @Override // j$.util.stream.Stream
    public final Optional findFirst() {
        return (Optional) v0(j0.c);
    }

    @Override // j$.util.stream.Stream
    public final Stream sorted(Comparator comparator) {
        return new k6(this, comparator);
    }

    @Override // j$.util.stream.Stream
    public final Object reduce(Object obj, BiFunction biFunction, BinaryOperator binaryOperator) {
        Objects.requireNonNull(biFunction);
        Objects.requireNonNull(binaryOperator);
        return v0(new c4(d7.REFERENCE, binaryOperator, biFunction, obj, 2));
    }

    @Override // j$.util.stream.Stream
    public final Object reduce(Object obj, BinaryOperator binaryOperator) {
        Objects.requireNonNull(binaryOperator);
        Objects.requireNonNull(binaryOperator);
        return v0(new c4(d7.REFERENCE, binaryOperator, binaryOperator, obj, 2));
    }

    public void forEach(Consumer consumer) {
        Objects.requireNonNull(consumer);
        v0(new q0(consumer, false));
    }

    public void forEachOrdered(Consumer consumer) {
        Objects.requireNonNull(consumer);
        v0(new q0(consumer, true));
    }

    @Override // j$.util.stream.Stream
    public final Optional max(Comparator comparator) {
        Objects.requireNonNull(comparator);
        return reduce(new j$.util.function.a(comparator, 0));
    }

    @Override // j$.util.stream.b
    public final d7 z0() {
        return d7.REFERENCE;
    }

    @Override // j$.util.stream.Stream
    public final Optional reduce(BinaryOperator binaryOperator) {
        Objects.requireNonNull(binaryOperator);
        return (Optional) v0(new a4(d7.REFERENCE, binaryOperator, 2));
    }

    @Override // j$.util.stream.b
    public final h2 x0(b bVar, Spliterator spliterator, boolean z, IntFunction intFunction) {
        return x3.U(bVar, spliterator, z, intFunction);
    }

    @Override // j$.util.stream.b
    public final Spliterator G0(b bVar, Supplier supplier, boolean z) {
        return new g8(bVar, supplier, z);
    }

    @Override // j$.util.stream.b
    public final boolean y0(Spliterator spliterator, o5 o5Var) {
        boolean zP;
        do {
            zP = o5Var.p();
            if (zP) {
                break;
            }
        } while (spliterator.tryAdvance(o5Var));
        return zP;
    }

    @Override // j$.util.stream.x3
    public final z1 p0(long j, IntFunction intFunction) {
        return x3.T(j, intFunction);
    }

    @Override // j$.util.stream.BaseStream
    public final Iterator iterator() {
        Spliterator spliterator = spliterator();
        Objects.requireNonNull(spliterator);
        return new j$.util.d1(spliterator);
    }

    @Override // j$.util.stream.Stream
    public final Stream filter(Predicate predicate) {
        Objects.requireNonNull(predicate);
        return new s(this, c7.t, predicate, 4);
    }

    @Override // j$.util.stream.Stream
    public final Stream map(Function function) {
        Objects.requireNonNull(function);
        return new s(this, c7.p | c7.n, function, 5);
    }

    @Override // j$.util.stream.Stream
    public final IntStream mapToInt(ToIntFunction toIntFunction) {
        Objects.requireNonNull(toIntFunction);
        return new v0(this, c7.p | c7.n, toIntFunction, 4);
    }

    @Override // j$.util.stream.Stream
    public final Object collect(Supplier supplier, BiConsumer biConsumer, BiConsumer biConsumer2) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(biConsumer);
        Objects.requireNonNull(biConsumer2);
        return v0(new c4(d7.REFERENCE, biConsumer2, biConsumer, supplier, 3));
    }

    @Override // j$.util.stream.Stream
    public final LongStream mapToLong(ToLongFunction toLongFunction) {
        Objects.requireNonNull(toLongFunction);
        return new g1(this, c7.p | c7.n, toLongFunction, 3);
    }

    @Override // j$.util.stream.Stream
    public final e0 mapToDouble(ToDoubleFunction toDoubleFunction) {
        Objects.requireNonNull(toDoubleFunction);
        return new x(this, c7.p | c7.n, toDoubleFunction, 2);
    }

    @Override // j$.util.stream.Stream
    public final long count() {
        return ((Long) v0(new e4(2))).longValue();
    }

    @Override // j$.util.stream.Stream
    public final Stream a(j$.time.s sVar) {
        Objects.requireNonNull(sVar);
        return new s(this, c7.p | c7.n | c7.t, sVar, 6);
    }

    @Override // j$.util.stream.Stream
    public final IntStream s(j$.time.s sVar) {
        Objects.requireNonNull(sVar);
        return new v0(this, c7.p | c7.n | c7.t, sVar, 5);
    }

    @Override // j$.util.stream.Stream
    public final e0 t(j$.time.s sVar) {
        Objects.requireNonNull(sVar);
        return new x(this, c7.p | c7.n | c7.t, sVar, 3);
    }

    @Override // j$.util.stream.Stream
    public final Object collect(Collector collector) {
        Collector collector2;
        Object objV0;
        if (!this.h.r || !collector.characteristics().contains(Collector.Characteristics.CONCURRENT) || (c7.ORDERED.k(this.m) && !collector.characteristics().contains(Collector.Characteristics.UNORDERED))) {
            Supplier supplier = ((Collector) Objects.requireNonNull(collector)).supplier();
            collector2 = collector;
            objV0 = v0(new j4(d7.REFERENCE, collector.combiner(), collector.accumulator(), supplier, collector2));
        } else {
            objV0 = collector.supplier().get();
            forEach(new j$.util.concurrent.s(6, collector.accumulator(), objV0));
            collector2 = collector;
        }
        return collector2.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH) ? objV0 : collector2.finisher().apply(objV0);
    }

    @Override // j$.util.stream.Stream
    public final LongStream n(j$.time.s sVar) {
        Objects.requireNonNull(sVar);
        return new g1(this, c7.p | c7.n | c7.t, sVar, 2);
    }

    @Override // j$.util.stream.Stream
    public final Stream peek(Consumer consumer) {
        Objects.requireNonNull(consumer);
        return new s(this, consumer);
    }

    @Override // j$.util.stream.Stream
    public final Stream limit(long j) {
        if (j < 0) {
            throw new IllegalArgumentException(Long.toString(j));
        }
        return z5.h(this, 0L, j);
    }

    @Override // j$.util.stream.Stream
    public final Stream skip(long j) {
        if (j >= 0) {
            return j == 0 ? this : z5.h(this, j, -1L);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    @Override // j$.util.stream.Stream
    public final Stream takeWhile(Predicate predicate) {
        int i = j9.a;
        Objects.requireNonNull(predicate);
        return new o8(this, j9.a, predicate, 0);
    }

    @Override // j$.util.stream.Stream
    public final Stream dropWhile(Predicate predicate) {
        int i = j9.a;
        Objects.requireNonNull(predicate);
        return new o8(this, j9.b, predicate, 1);
    }

    @Override // j$.util.stream.Stream
    public final Object[] toArray(IntFunction intFunction) {
        return x3.f0(w0(intFunction), intFunction).g(intFunction);
    }

    @Override // j$.util.stream.Stream
    public final Object[] toArray() {
        return toArray(new c1(10));
    }

    @Override // j$.util.stream.Stream
    public final boolean anyMatch(Predicate predicate) {
        return ((Boolean) v0(x3.q0(u1.ANY, predicate))).booleanValue();
    }

    @Override // j$.util.stream.Stream
    public final boolean allMatch(Predicate predicate) {
        return ((Boolean) v0(x3.q0(u1.ALL, predicate))).booleanValue();
    }

    @Override // j$.util.stream.Stream
    public final boolean noneMatch(Predicate predicate) {
        return ((Boolean) v0(x3.q0(u1.NONE, predicate))).booleanValue();
    }

    @Override // j$.util.stream.Stream
    public final List toList() {
        return Collections.unmodifiableList(new ArrayList(Arrays.asList(toArray())));
    }
}
