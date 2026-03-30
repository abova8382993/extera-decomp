package j$.util.stream;

import java.util.Comparator;

/* JADX INFO: loaded from: classes2.dex */
public abstract class d6 extends k5 {
    public final Comparator b;
    public boolean c;

    public d6(o5 o5Var, Comparator comparator) {
        super(o5Var);
        this.b = comparator;
    }

    @Override // j$.util.stream.k5, j$.util.stream.o5
    public final boolean p() {
        this.c = true;
        return false;
    }
}
