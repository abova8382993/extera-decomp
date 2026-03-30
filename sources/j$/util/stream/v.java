package j$.util.stream;

/* JADX INFO: loaded from: classes2.dex */
public final class v extends j1 {
    public final /* synthetic */ int s;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ v(b bVar, int i, int i2) {
        super(bVar, i);
        this.s = i2;
    }

    @Override // j$.util.stream.b
    public final o5 D0(int i, o5 o5Var) {
        switch (this.s) {
            case 0:
                return new r(this, o5Var, 3);
            case 1:
                return new w0(0, o5Var);
            case 2:
                return new u0(this, o5Var, 3);
            case 3:
                return new d1(this, o5Var, 1);
            case 4:
                return o5Var;
            default:
                return new d1(this, o5Var, 4);
        }
    }
}
