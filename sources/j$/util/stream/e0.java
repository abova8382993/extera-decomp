package j$.util.stream;

import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes2.dex */
public interface e0 extends BaseStream {
    e0 a(j$.time.s sVar);

    j$.util.b0 average();

    e0 b();

    Stream boxed();

    e0 c();

    Object collect(Supplier supplier, ObjDoubleConsumer objDoubleConsumer, BiConsumer biConsumer);

    long count();

    e0 d();

    e0 distinct();

    e0 e();

    j$.util.b0 findAny();

    j$.util.b0 findFirst();

    void forEach(DoubleConsumer doubleConsumer);

    void forEachOrdered(DoubleConsumer doubleConsumer);

    @Override // j$.util.stream.BaseStream
    j$.util.g0 iterator();

    boolean l();

    e0 limit(long j);

    Stream mapToObj(DoubleFunction doubleFunction);

    j$.util.b0 max();

    j$.util.b0 min();

    @Override // j$.util.stream.BaseStream
    e0 parallel();

    e0 peek(DoubleConsumer doubleConsumer);

    boolean q();

    LongStream r();

    double reduce(double d, DoubleBinaryOperator doubleBinaryOperator);

    j$.util.b0 reduce(DoubleBinaryOperator doubleBinaryOperator);

    @Override // j$.util.stream.BaseStream
    e0 sequential();

    e0 skip(long j);

    e0 sorted();

    @Override // j$.util.stream.BaseStream
    j$.util.t0 spliterator();

    double sum();

    j$.util.w summaryStatistics();

    double[] toArray();

    IntStream w();

    boolean y();
}
