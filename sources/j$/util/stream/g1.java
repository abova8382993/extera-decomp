package j$.util.stream;

import java.util.function.LongConsumer;

/* JADX INFO: loaded from: classes2.dex */
public final class g1 extends j1 {
    public final /* synthetic */ int s;
    public final /* synthetic */ Object t;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ g1(b bVar, int i, Object obj, int i2) {
        super(bVar, i);
        this.s = i2;
        this.t = obj;
    }

    @Override // j$.util.stream.b
    public final o5 D0(int i, o5 o5Var) {
        switch (this.s) {
            case 0:
                return new f1(this, o5Var);
            case 1:
                return new d1(this, o5Var, 5);
            case 2:
                return new a5(this, o5Var);
            default:
                return new n(this, o5Var, 5);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public g1(k1 k1Var, LongConsumer longConsumer) {
        super(k1Var, 0);
        this.s = 1;
        this.t = longConsumer;
    }
}
