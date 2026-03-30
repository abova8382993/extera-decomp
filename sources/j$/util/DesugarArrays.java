package j$.util;

import j$.util.stream.IntStream;
import j$.util.stream.LongStream;
import j$.util.stream.Stream;
import j$.util.stream.c7;
import j$.util.stream.h8;

/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class DesugarArrays {
    public static h1 a(Object[] objArr, int i, int i2) {
        Spliterators.a(((Object[]) Objects.requireNonNull(objArr)).length, i, i2);
        return new h1(objArr, i, i2, 1040);
    }

    public static p1 b(long[] jArr, int i, int i2) {
        Spliterators.a(((long[]) Objects.requireNonNull(jArr)).length, i, i2);
        return new p1(jArr, i, i2, 1040);
    }

    public static <T> Stream<T> stream(T[] tArr) {
        return h8.b(a(tArr, 0, tArr.length), false);
    }

    public static IntStream stream(int[] iArr) {
        return h8.a(Spliterators.spliterator(iArr, 0, iArr.length, 1040));
    }

    public static LongStream stream(long[] jArr) {
        p1 p1VarB = b(jArr, 0, jArr.length);
        return new j$.util.stream.h1(p1VarB, c7.j(p1VarB), false);
    }
}
