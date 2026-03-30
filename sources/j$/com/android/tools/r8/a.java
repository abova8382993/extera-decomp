package j$.com.android.tools.r8;

import j$.time.ZoneOffset;
import j$.time.c;
import j$.time.chrono.ChronoLocalDateTime;
import j$.time.chrono.ChronoZonedDateTime;
import j$.time.chrono.f0;
import j$.time.chrono.h;
import j$.time.chrono.l;
import j$.time.chrono.u;
import j$.time.chrono.z;
import j$.time.temporal.m;
import j$.time.temporal.n;
import j$.time.temporal.q;
import j$.time.temporal.r;
import j$.time.temporal.t;
import j$.util.Objects;
import j$.util.OptionalInt;
import j$.util.Spliterator;
import j$.util.b0;
import j$.util.c0;
import j$.util.concurrent.ConcurrentHashMap;
import j$.util.concurrent.k;
import j$.util.d0;
import j$.util.function.b;
import j$.util.function.d;
import j$.util.function.g;
import j$.util.h0;
import j$.util.l0;
import j$.util.s1;
import j$.util.t0;
import j$.util.y;
import j$.util.y0;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import org.mvel2.asm.signature.SignatureVisitor;
import sun.misc.Unsafe;

/* JADX INFO: loaded from: classes2.dex */
public abstract /* synthetic */ class a {
    public static /* synthetic */ long O(long j, int i) {
        long j2 = i;
        int iNumberOfLeadingZeros = Long.numberOfLeadingZeros(~j2) + Long.numberOfLeadingZeros(j2) + Long.numberOfLeadingZeros(~j) + Long.numberOfLeadingZeros(j);
        if (iNumberOfLeadingZeros > 65) {
            return j * j2;
        }
        if (iNumberOfLeadingZeros >= 64) {
            if ((j2 != Long.MIN_VALUE) | (j >= 0)) {
                long j3 = j * j2;
                if (j == 0 || j3 / j == j2) {
                    return j3;
                }
            }
        }
        throw new ArithmeticException();
    }

    public static /* synthetic */ long P(long j, long j2) {
        long j3 = j + j2;
        if (((j2 ^ j) < 0) || ((j ^ j3) >= 0)) {
            return j3;
        }
        throw new ArithmeticException();
    }

    public static /* synthetic */ Map.Entry Q(Object obj, Object obj2) {
        return new AbstractMap.SimpleImmutableEntry(Objects.requireNonNull(obj), Objects.requireNonNull(obj2));
    }

    public static /* synthetic */ boolean R(Unsafe unsafe, Object obj, long j, k kVar) {
        while (true) {
            Unsafe unsafe2 = unsafe;
            Object obj2 = obj;
            long j2 = j;
            k kVar2 = kVar;
            if (unsafe2.compareAndSwapObject(obj2, j2, (Object) null, kVar2)) {
                return true;
            }
            if (unsafe2.getObject(obj2, j2) != null) {
                return false;
            }
            unsafe = unsafe2;
            obj = obj2;
            j = j2;
            kVar = kVar2;
        }
    }

    public static /* synthetic */ long S(long j, long j2) {
        long j3 = j % j2;
        if (j3 == 0) {
            return 0L;
        }
        return (((j ^ j2) >> 63) | 1) > 0 ? j3 : j3 + j2;
    }

    public static /* synthetic */ long T(long j, long j2) {
        long j3 = j / j2;
        return (j - (j2 * j3) != 0 && (((j ^ j2) >> 63) | 1) < 0) ? j3 - 1 : j3;
    }

    public static /* synthetic */ long U(long j, long j2) {
        long j3 = j - j2;
        if (((j2 ^ j) >= 0) || ((j ^ j3) >= 0)) {
            return j3;
        }
        throw new ArithmeticException();
    }

    public static Optional I(j$.util.Optional optional) {
        if (optional == null) {
            return null;
        }
        if (optional.isPresent()) {
            return Optional.of(optional.get());
        }
        return Optional.empty();
    }

    public static j$.util.Optional E(Optional optional) {
        if (optional == null) {
            return null;
        }
        if (optional.isPresent()) {
            return j$.util.Optional.of(optional.get());
        }
        return j$.util.Optional.empty();
    }

    public static b0 F(OptionalDouble optionalDouble) {
        if (optionalDouble == null) {
            return null;
        }
        if (!optionalDouble.isPresent()) {
            return b0.c;
        }
        return new b0(optionalDouble.getAsDouble());
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [j$.util.function.b] */
    public static b b(final DoubleConsumer doubleConsumer, final DoubleConsumer doubleConsumer2) {
        Objects.requireNonNull(doubleConsumer2);
        return new DoubleConsumer() { // from class: j$.util.function.b
            public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer3) {
                return j$.com.android.tools.r8.a.b(this, doubleConsumer3);
            }

            @Override // java.util.function.DoubleConsumer
            public final void accept(double d) {
                doubleConsumer.accept(d);
                doubleConsumer2.accept(d);
            }
        };
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [j$.util.function.d] */
    public static d c(final IntConsumer intConsumer, final IntConsumer intConsumer2) {
        Objects.requireNonNull(intConsumer2);
        return new IntConsumer() { // from class: j$.util.function.d
            public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer3) {
                return j$.com.android.tools.r8.a.c(this, intConsumer3);
            }

            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                intConsumer.accept(i);
                intConsumer2.accept(i);
            }
        };
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [j$.util.function.g] */
    public static g d(final LongConsumer longConsumer, final LongConsumer longConsumer2) {
        Objects.requireNonNull(longConsumer2);
        return new LongConsumer() { // from class: j$.util.function.g
            public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer3) {
                return j$.com.android.tools.r8.a.d(this, longConsumer3);
            }

            @Override // java.util.function.LongConsumer
            public final void accept(long j) {
                longConsumer.accept(j);
                longConsumer2.accept(j);
            }
        };
    }

    public static c0 H(OptionalLong optionalLong) {
        if (optionalLong == null) {
            return null;
        }
        if (!optionalLong.isPresent()) {
            return c0.c;
        }
        return new c0(optionalLong.getAsLong());
    }

    public static String D(long j, String str, Locale locale) {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str, locale);
        simpleDateFormat.setTimeZone(timeZone);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(timeZone);
        calendar.set(0, (int) j, 0, 0, 0, 0);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static OptionalInt G(java.util.OptionalInt optionalInt) {
        if (optionalInt == null) {
            return null;
        }
        if (!optionalInt.isPresent()) {
            return OptionalInt.c;
        }
        return new OptionalInt(optionalInt.getAsInt());
    }

    public static void i(ConcurrentMap concurrentMap, BiConsumer biConsumer) {
        Objects.requireNonNull(biConsumer);
        for (Map.Entry entry : concurrentMap.entrySet()) {
            try {
                biConsumer.accept(entry.getKey(), entry.getValue());
            } catch (IllegalStateException unused) {
            }
        }
    }

    public static String V(Object obj, Object obj2) {
        String string;
        String string2;
        String str = "null";
        if (obj == null || (string = obj.toString()) == null) {
            string = "null";
        }
        int length = string.length();
        if (obj2 != null && (string2 = obj2.toString()) != null) {
            str = string2;
        }
        int length2 = str.length();
        char[] cArr = new char[length + length2 + 1];
        string.getChars(0, length, cArr, 0);
        cArr[length] = SignatureVisitor.INSTANCEOF;
        str.getChars(0, length2, cArr, length + 1);
        return new String(cArr);
    }

    public static String C(long j, String str, Locale locale) {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str, locale);
        simpleDateFormat.setTimeZone(timeZone);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(timeZone);
        calendar.set(2016, 1, (int) j, 0, 0, 0);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static void M(Iterator it, Consumer consumer) {
        if (it instanceof y) {
            ((y) it).forEachRemaining(consumer);
            return;
        }
        Objects.requireNonNull(consumer);
        while (it.hasNext()) {
            consumer.accept(it.next());
        }
    }

    public static OptionalDouble J(b0 b0Var) {
        if (b0Var == null) {
            return null;
        }
        boolean z = b0Var.a;
        if (!z) {
            return OptionalDouble.empty();
        }
        if (z) {
            return OptionalDouble.of(b0Var.b);
        }
        throw new NoSuchElementException("No value present");
    }

    public static java.util.OptionalInt K(OptionalInt optionalInt) {
        if (optionalInt == null) {
            return null;
        }
        boolean z = optionalInt.a;
        if (!z) {
            return java.util.OptionalInt.empty();
        }
        if (z) {
            return java.util.OptionalInt.of(optionalInt.b);
        }
        throw new NoSuchElementException("No value present");
    }

    public static OptionalLong L(c0 c0Var) {
        if (c0Var == null) {
            return null;
        }
        boolean z = c0Var.a;
        if (!z) {
            return OptionalLong.empty();
        }
        if (z) {
            return OptionalLong.of(c0Var.b);
        }
        throw new NoSuchElementException("No value present");
    }

    public static boolean s(l lVar, q qVar) {
        return qVar instanceof j$.time.temporal.a ? qVar == j$.time.temporal.a.ERA : qVar != null && qVar.h(lVar);
    }

    public static j$.time.chrono.k N(n nVar) {
        Objects.requireNonNull(nVar, "temporal");
        return (j$.time.chrono.k) Objects.requireNonNullElse((j$.time.chrono.k) nVar.k(r.b), j$.time.chrono.r.c);
    }

    public static int m(ChronoZonedDateTime chronoZonedDateTime, q qVar) {
        if (qVar instanceof j$.time.temporal.a) {
            int i = h.a[((j$.time.temporal.a) qVar).ordinal()];
            if (i == 1) {
                throw new t("Invalid field 'InstantSeconds' for get() method, use getLong() instead");
            }
            if (i == 2) {
                return chronoZonedDateTime.g().getTotalSeconds();
            }
            return chronoZonedDateTime.o().h(qVar);
        }
        return r.a(chronoZonedDateTime, qVar);
    }

    public static int n(l lVar, q qVar) {
        if (qVar == j$.time.temporal.a.ERA) {
            return lVar.getValue();
        }
        return r.a(lVar, qVar);
    }

    public static long p(l lVar, q qVar) {
        if (qVar == j$.time.temporal.a.ERA) {
            return lVar.getValue();
        }
        if (qVar instanceof j$.time.temporal.a) {
            throw new t(c.a("Unsupported field: ", qVar));
        }
        return qVar.k(lVar);
    }

    public static j$.time.chrono.k W(String str) {
        ConcurrentHashMap concurrentHashMap = j$.time.chrono.a.a;
        Objects.requireNonNull(str, "id");
        while (true) {
            ConcurrentHashMap concurrentHashMap2 = j$.time.chrono.a.a;
            j$.time.chrono.k kVar = (j$.time.chrono.k) concurrentHashMap2.get(str);
            if (kVar == null) {
                kVar = (j$.time.chrono.k) j$.time.chrono.a.b.get(str);
            }
            if (kVar != null) {
                return kVar;
            }
            if (concurrentHashMap2.get("ISO") != null) {
                for (j$.time.chrono.k kVar2 : ServiceLoader.load(j$.time.chrono.k.class)) {
                    if (str.equals(kVar2.getId()) || str.equals(kVar2.m())) {
                        return kVar2;
                    }
                }
                throw new j$.time.b("Unknown chronology: " + str);
            }
            j$.time.chrono.n nVar = j$.time.chrono.n.l;
            nVar.getClass();
            j$.time.chrono.a.h(nVar, "Hijrah-umalqura");
            u uVar = u.c;
            uVar.getClass();
            j$.time.chrono.a.h(uVar, "Japanese");
            z zVar = z.c;
            zVar.getClass();
            j$.time.chrono.a.h(zVar, "Minguo");
            f0 f0Var = f0.c;
            f0Var.getClass();
            j$.time.chrono.a.h(f0Var, "ThaiBuddhist");
            try {
                for (j$.time.chrono.a aVar : Arrays.asList(new j$.time.chrono.a[0])) {
                    if (!aVar.getId().equals("ISO")) {
                        j$.time.chrono.a.h(aVar, aVar.getId());
                    }
                }
                j$.time.chrono.r rVar = j$.time.chrono.r.c;
                rVar.getClass();
                j$.time.chrono.a.h(rVar, "ISO");
            } catch (Throwable th) {
                throw new ServiceConfigurationError(th.getMessage(), th);
            }
        }
    }

    public static Object w(l lVar, j$.time.format.a aVar) {
        if (aVar == r.c) {
            return j$.time.temporal.b.ERAS;
        }
        return r.c(lVar, aVar);
    }

    public static Object u(ChronoLocalDateTime chronoLocalDateTime, j$.time.format.a aVar) {
        if (aVar == r.a || aVar == r.e || aVar == r.d) {
            return null;
        }
        if (aVar == r.g) {
            return chronoLocalDateTime.b();
        }
        if (aVar == r.b) {
            return chronoLocalDateTime.a();
        }
        if (aVar == r.c) {
            return j$.time.temporal.b.NANOS;
        }
        return aVar.a(chronoLocalDateTime);
    }

    public static boolean r(j$.time.chrono.b bVar, q qVar) {
        if (qVar instanceof j$.time.temporal.a) {
            return ((j$.time.temporal.a) qVar).isDateBased();
        }
        return qVar != null && qVar.h(bVar);
    }

    public static long o(Spliterator spliterator) {
        if ((spliterator.characteristics() & 64) == 0) {
            return -1L;
        }
        return spliterator.estimateSize();
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0019, code lost:
    
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object h(java.util.concurrent.ConcurrentMap r2, java.lang.Object r3, java.util.function.BiFunction r4) {
        /*
        L0:
            java.lang.Object r0 = r2.get(r3)
        L4:
            java.lang.Object r1 = r4.apply(r3, r0)
            if (r1 == 0) goto L1a
            if (r0 == 0) goto L13
            boolean r0 = r2.replace(r3, r0, r1)
            if (r0 == 0) goto L0
            goto L19
        L13:
            java.lang.Object r0 = r2.putIfAbsent(r3, r1)
            if (r0 != 0) goto L4
        L19:
            return r1
        L1a:
            if (r0 == 0) goto L22
            boolean r0 = r2.remove(r3, r0)
            if (r0 == 0) goto L0
        L22:
            r2 = 0
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.com.android.tools.r8.a.h(java.util.concurrent.ConcurrentMap, java.lang.Object, java.util.function.BiFunction):java.lang.Object");
    }

    public static boolean q(Spliterator spliterator, int i) {
        return (spliterator.characteristics() & i) == i;
    }

    public static long x(ChronoLocalDateTime chronoLocalDateTime, ZoneOffset zoneOffset) {
        Objects.requireNonNull(zoneOffset, "offset");
        return ((chronoLocalDateTime.f().x() * 86400) + ((long) chronoLocalDateTime.b().N())) - ((long) zoneOffset.getTotalSeconds());
    }

    public static Object v(ChronoZonedDateTime chronoZonedDateTime, j$.time.format.a aVar) {
        if (aVar == r.e || aVar == r.a) {
            return chronoZonedDateTime.u();
        }
        if (aVar == r.d) {
            return chronoZonedDateTime.g();
        }
        if (aVar == r.g) {
            return chronoZonedDateTime.b();
        }
        if (aVar == r.b) {
            return chronoZonedDateTime.a();
        }
        if (aVar == r.c) {
            return j$.time.temporal.b.NANOS;
        }
        return aVar.a(chronoZonedDateTime);
    }

    public static int f(ChronoLocalDateTime chronoLocalDateTime, ChronoLocalDateTime chronoLocalDateTime2) {
        int iB = chronoLocalDateTime.f().compareTo(chronoLocalDateTime2.f());
        return (iB == 0 && (iB = chronoLocalDateTime.b().compareTo(chronoLocalDateTime2.b())) == 0) ? ((j$.time.chrono.a) chronoLocalDateTime.a()).getId().compareTo(chronoLocalDateTime2.a().getId()) : iB;
    }

    public static Object t(j$.time.chrono.b bVar, j$.time.format.a aVar) {
        if (aVar == r.a || aVar == r.e || aVar == r.d || aVar == r.g) {
            return null;
        }
        if (aVar == r.b) {
            return bVar.a();
        }
        if (aVar == r.c) {
            return j$.time.temporal.b.DAYS;
        }
        return aVar.a(bVar);
    }

    public static m a(j$.time.chrono.b bVar, m mVar) {
        return mVar.c(bVar.x(), j$.time.temporal.a.EPOCH_DAY);
    }

    public static long y(ChronoZonedDateTime chronoZonedDateTime) {
        return ((chronoZonedDateTime.f().x() * 86400) + ((long) chronoZonedDateTime.b().N())) - ((long) chronoZonedDateTime.g().getTotalSeconds());
    }

    public static int g(ChronoZonedDateTime chronoZonedDateTime, ChronoZonedDateTime chronoZonedDateTime2) {
        int iCompare = Long.compare(chronoZonedDateTime.C(), chronoZonedDateTime2.C());
        return (iCompare == 0 && (iCompare = chronoZonedDateTime.b().d - chronoZonedDateTime2.b().d) == 0 && (iCompare = chronoZonedDateTime.o().z(chronoZonedDateTime2.o())) == 0 && (iCompare = chronoZonedDateTime.u().getId().compareTo(chronoZonedDateTime2.u().getId())) == 0) ? ((j$.time.chrono.a) chronoZonedDateTime.a()).getId().compareTo(chronoZonedDateTime2.a().getId()) : iCompare;
    }

    public static boolean A(Spliterator.OfInt ofInt, Consumer consumer) {
        if (consumer instanceof IntConsumer) {
            return ofInt.tryAdvance((IntConsumer) consumer);
        }
        if (s1.a) {
            s1.a(ofInt.getClass(), "{0} calling Spliterator.OfInt.tryAdvance((IntConsumer) action::accept)");
            throw null;
        }
        Objects.requireNonNull(consumer);
        return ofInt.tryAdvance((IntConsumer) new h0(consumer, 0));
    }

    public static void k(Spliterator.OfInt ofInt, Consumer consumer) {
        if (consumer instanceof IntConsumer) {
            ofInt.forEachRemaining((IntConsumer) consumer);
        } else {
            if (s1.a) {
                s1.a(ofInt.getClass(), "{0} calling Spliterator.OfInt.forEachRemaining((IntConsumer) action::accept)");
                throw null;
            }
            Objects.requireNonNull(consumer);
            ofInt.forEachRemaining((IntConsumer) new h0(consumer, 0));
        }
    }

    public static int e(j$.time.chrono.b bVar, j$.time.chrono.b bVar2) {
        int iCompare = Long.compare(bVar.x(), bVar2.x());
        if (iCompare != 0) {
            return iCompare;
        }
        return ((j$.time.chrono.a) bVar.a()).getId().compareTo(bVar2.a().getId());
    }

    public static boolean B(y0 y0Var, Consumer consumer) {
        if (consumer instanceof LongConsumer) {
            return y0Var.tryAdvance((LongConsumer) consumer);
        }
        if (s1.a) {
            s1.a(y0Var.getClass(), "{0} calling Spliterator.OfLong.tryAdvance((LongConsumer) action::accept)");
            throw null;
        }
        Objects.requireNonNull(consumer);
        return y0Var.tryAdvance((LongConsumer) new l0(consumer, 0));
    }

    public static void l(y0 y0Var, Consumer consumer) {
        if (consumer instanceof LongConsumer) {
            y0Var.forEachRemaining((LongConsumer) consumer);
        } else {
            if (s1.a) {
                s1.a(y0Var.getClass(), "{0} calling Spliterator.OfLong.forEachRemaining((LongConsumer) action::accept)");
                throw null;
            }
            Objects.requireNonNull(consumer);
            y0Var.forEachRemaining((LongConsumer) new l0(consumer, 0));
        }
    }

    public static boolean z(t0 t0Var, Consumer consumer) {
        if (consumer instanceof DoubleConsumer) {
            return t0Var.tryAdvance((DoubleConsumer) consumer);
        }
        if (s1.a) {
            s1.a(t0Var.getClass(), "{0} calling Spliterator.OfDouble.tryAdvance((DoubleConsumer) action::accept)");
            throw null;
        }
        Objects.requireNonNull(consumer);
        return t0Var.tryAdvance((DoubleConsumer) new d0(consumer, 0));
    }

    public static void j(t0 t0Var, Consumer consumer) {
        if (consumer instanceof DoubleConsumer) {
            t0Var.forEachRemaining((DoubleConsumer) consumer);
        } else {
            if (s1.a) {
                s1.a(t0Var.getClass(), "{0} calling Spliterator.OfDouble.forEachRemaining((DoubleConsumer) action::accept)");
                throw null;
            }
            Objects.requireNonNull(consumer);
            t0Var.forEachRemaining((DoubleConsumer) new d0(consumer, 0));
        }
    }

    public Spliterator trySplit() {
        return null;
    }

    public boolean tryAdvance(Object obj) {
        Objects.requireNonNull(obj);
        return false;
    }

    public void forEachRemaining(Object obj) {
        Objects.requireNonNull(obj);
    }

    public long estimateSize() {
        return 0L;
    }

    public int characteristics() {
        return 16448;
    }
}
