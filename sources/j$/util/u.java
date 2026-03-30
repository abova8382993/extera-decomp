package j$.util;

import java.util.RandomAccess;

/* JADX INFO: loaded from: classes2.dex */
public final class u extends p implements RandomAccess {
    private static final long serialVersionUID = -2542308836966382001L;

    @Override // j$.util.p, java.util.List
    public final java.util.List subList(int i, int i2) {
        return new u(this.b.subList(i, i2));
    }

    private Object writeReplace() {
        return new p(this.b);
    }
}
