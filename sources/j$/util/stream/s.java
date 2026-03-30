package j$.util.stream;

import java.util.function.Consumer;

/* JADX INFO: loaded from: classes2.dex */
public final class s extends f5 {
    public final /* synthetic */ int s;
    public final /* synthetic */ Object t;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ s(b bVar, int i, Object obj, int i2) {
        super(bVar, i);
        this.s = i2;
        this.t = obj;
    }

    @Override // j$.util.stream.b
    public final o5 D0(int i, o5 o5Var) {
        switch (this.s) {
            case 0:
                return new r(this, o5Var, 0);
            case 1:
                return new u0(this, o5Var, 0);
            case 2:
                return new d1(this, o5Var, 0);
            case 3:
                return new n(this, o5Var, 1);
            case 4:
                return new n(this, o5Var, 2);
            case 5:
                return new n(this, o5Var, 3);
            default:
                return new m(this, o5Var);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public s(g5 g5Var, Consumer consumer) {
        super(g5Var, 0);
        this.s = 3;
        this.t = consumer;
    }
}
