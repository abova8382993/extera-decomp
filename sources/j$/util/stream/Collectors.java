package j$.util.stream;

import j$.util.Map;
import j$.util.function.BiConsumer$CC;
import j$.util.stream.Collector;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes2.dex */
public final class Collectors {
    public static final Set a;
    public static final Set b;
    public static final Set c;

    static {
        Collector.Characteristics characteristics = Collector.Characteristics.CONCURRENT;
        Collector.Characteristics characteristics2 = Collector.Characteristics.UNORDERED;
        Collector.Characteristics characteristics3 = Collector.Characteristics.IDENTITY_FINISH;
        Collections.unmodifiableSet(EnumSet.of(characteristics, characteristics2, characteristics3));
        Collections.unmodifiableSet(EnumSet.of(characteristics, characteristics2));
        a = Collections.unmodifiableSet(EnumSet.of(characteristics3));
        b = Collections.unmodifiableSet(EnumSet.of(characteristics2, characteristics3));
        c = Collections.EMPTY_SET;
        Collections.unmodifiableSet(EnumSet.of(characteristics2));
    }

    public static <T, C extends Collection<T>> Collector<T, ?, C> toCollection(Supplier<C> supplier) {
        return new l(supplier, new j$.time.format.a(10), new j$.time.format.a(11), a);
    }

    public static <T> Collector<T, ?, List<T>> toList() {
        return new l(new j(1), new j$.time.format.a(12), new j$.time.format.a(14), a);
    }

    public static <T> Collector<T, ?, Set<T>> toSet() {
        return new l(new j(3), new j$.time.format.a(13), new j$.time.format.a(19), b);
    }

    public static Collector<CharSequence, ?, String> joining() {
        return new l(new j(5), new j$.time.format.a(20), new j$.time.format.a(21), new j$.time.format.a(22), c);
    }

    public static Collector<CharSequence, ?, String> joining(CharSequence charSequence) {
        return new l(new a(2, charSequence), new j$.time.format.a(16), new j$.time.format.a(17), new j$.time.format.a(18), c);
    }

    public static void a(double[] dArr, double d) {
        double d2 = d - dArr[1];
        double d3 = dArr[0];
        double d4 = d3 + d2;
        dArr[1] = (d4 - d3) - d2;
        dArr[0] = d4;
    }

    public static <T, K, U, M extends Map<K, U>> Collector<T, ?, M> toMap(final Function<? super T, ? extends K> function, final Function<? super T, ? extends U> function2, final BinaryOperator<U> binaryOperator, Supplier<M> supplier) {
        return new l(supplier, new BiConsumer() { // from class: j$.util.stream.k
            public final /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
                return BiConsumer$CC.$default$andThen(this, biConsumer);
            }

            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                Set set = Collectors.a;
                Map.EL.a((java.util.Map) obj, function.apply(obj2), function2.apply(obj2), binaryOperator);
            }
        }, new j$.time.s(4, binaryOperator), a);
    }
}
