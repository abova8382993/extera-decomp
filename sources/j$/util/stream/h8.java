package j$.util.stream;

import j$.util.Objects;
import j$.util.Spliterator;

/* JADX INFO: loaded from: classes2.dex */
public abstract class h8 {
    public static d5 b(Spliterator spliterator, boolean z) {
        Objects.requireNonNull(spliterator);
        return new d5(spliterator, c7.j(spliterator), z);
    }

    public static y0 a(Spliterator.OfInt ofInt) {
        return new y0(ofInt, c7.j(ofInt), false);
    }
}
