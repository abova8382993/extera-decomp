package j$.util.stream;

import j$.util.Objects;
import j$.util.Spliterator;
import java.util.Arrays;
import java.util.function.IntFunction;

/* JADX INFO: loaded from: classes2.dex */
public final class i6 extends z0 {
    @Override // j$.util.stream.b
    public final o5 D0(int i, o5 o5Var) {
        Objects.requireNonNull(o5Var);
        return c7.SORTED.k(i) ? o5Var : c7.SIZED.k(i) ? new n6(o5Var) : new f6(o5Var);
    }

    @Override // j$.util.stream.b
    public final h2 A0(x3 x3Var, Spliterator spliterator, IntFunction intFunction) {
        if (c7.SORTED.k(((b) x3Var).m)) {
            return x3Var.d0(spliterator, false, intFunction);
        }
        int[] iArr = (int[]) ((d2) x3Var.d0(spliterator, true, intFunction)).b();
        Arrays.sort(iArr);
        return new c3(iArr);
    }
}
