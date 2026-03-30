package j$.util.stream;

import java.util.function.Supplier;

/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class n1 implements Supplier {
    public final /* synthetic */ int a;
    public final /* synthetic */ u1 b;

    public /* synthetic */ n1(u1 u1Var, int i) {
        this.a = i;
        this.b = u1Var;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        switch (this.a) {
            case 0:
                return new r1(this.b);
            default:
                return new s1(this.b);
        }
    }
}
