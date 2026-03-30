package j$.util.stream;

/* JADX INFO: loaded from: classes2.dex */
public final class u extends a1 {
    public final /* synthetic */ int s;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ u(b bVar, int i, int i2) {
        super(bVar, i);
        this.s = i2;
    }

    @Override // j$.util.stream.b
    public final o5 D0(int i, o5 o5Var) {
        switch (this.s) {
            case 0:
                return new r(this, o5Var, 2);
            case 1:
                return o5Var;
            default:
                return new d1(this, o5Var, 2);
        }
    }
}
