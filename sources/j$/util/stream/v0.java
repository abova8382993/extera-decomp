package j$.util.stream;

import java.util.function.IntConsumer;

/* JADX INFO: loaded from: classes2.dex */
public final class v0 extends a1 {
    public final /* synthetic */ int s;
    public final /* synthetic */ Object t;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ v0(b bVar, int i, Object obj, int i2) {
        super(bVar, i);
        this.s = i2;
        this.t = obj;
    }

    @Override // j$.util.stream.b
    public final o5 D0(int i, o5 o5Var) {
        switch (this.s) {
            case 0:
                return new u0(this, o5Var, 1);
            case 1:
                return new u0(this, o5Var, 2);
            case 2:
                return new x0(this, o5Var);
            case 3:
                return new u0(this, o5Var, 5);
            case 4:
                return new n(this, o5Var, 4);
            default:
                return new c5(this, o5Var);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public v0(b1 b1Var, IntConsumer intConsumer) {
        super(b1Var, 0);
        this.s = 0;
        this.t = intConsumer;
    }
}
