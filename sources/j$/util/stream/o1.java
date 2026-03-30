package j$.util.stream;

import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class o1 implements Supplier {
    public final /* synthetic */ int a;
    public final /* synthetic */ u1 b;
    public final /* synthetic */ Object c;

    public /* synthetic */ o1(u1 u1Var, Object obj, int i) {
        this.a = i;
        this.b = u1Var;
        this.c = obj;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        switch (this.a) {
            case 0:
                return new q1(this.b, (IntPredicate) this.c);
            default:
                return new p1(this.b, (Predicate) this.c);
        }
    }
}
