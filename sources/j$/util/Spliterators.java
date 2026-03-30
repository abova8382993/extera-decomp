package j$.util;

import j$.util.Spliterator;

/* JADX INFO: loaded from: classes2.dex */
public final class Spliterators {
    public static final m1 a = new m1();
    public static final k1 b = new k1();
    public static final l1 c = new l1();
    public static final j1 d = new j1();

    public static Spliterator.OfInt spliterator(int[] iArr, int i, int i2, int i3) {
        a(((int[]) Objects.requireNonNull(iArr)).length, i, i2);
        return new n1(iArr, i, i2, i3);
    }

    public static void a(int i, int i2, int i3) {
        if (i2 <= i3) {
            if (i2 < 0) {
                throw new ArrayIndexOutOfBoundsException(i2);
            }
            if (i3 > i) {
                throw new ArrayIndexOutOfBoundsException(i3);
            }
            return;
        }
        throw new ArrayIndexOutOfBoundsException("origin(" + i2 + ") > fence(" + i3 + ")");
    }

    public static <T> Spliterator<T> spliterator(java.util.Collection<? extends T> collection, int i) {
        return new o1((java.util.Collection) Objects.requireNonNull(collection), i);
    }
}
