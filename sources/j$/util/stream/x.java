package j$.util.stream;

import java.util.function.DoubleConsumer;

/* JADX INFO: loaded from: classes2.dex */
public final class x extends a0 {
    public final /* synthetic */ int s;
    public final /* synthetic */ Object t;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ x(b bVar, int i, Object obj, int i2) {
        super(bVar, i);
        this.s = i2;
        this.t = obj;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public x(b0 b0Var, DoubleConsumer doubleConsumer) {
        super(b0Var, 0);
        this.s = 1;
        this.t = doubleConsumer;
    }

    @Override // j$.util.stream.b
    public final o5 D0(int i, o5 o5Var) {
        switch (this.s) {
            case 0:
                return new w(this, o5Var);
            case 1:
                return new r(this, o5Var, 5);
            case 2:
                return new n(this, o5Var, 6);
            default:
                return new a5(this, o5Var);
        }
    }
}
