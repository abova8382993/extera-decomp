package j$.util.stream;

import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.LongBinaryOperator;

/* JADX INFO: loaded from: classes2.dex */
public final class a4 extends x3 {
    public final /* synthetic */ int h;
    public final /* synthetic */ Object i;

    public /* synthetic */ a4(d7 d7Var, Object obj, int i) {
        this.h = i;
        this.i = obj;
    }

    @Override // j$.util.stream.x3
    public final s4 r0() {
        switch (this.h) {
            case 0:
                return new r4((LongBinaryOperator) this.i);
            case 1:
                return new d4((DoubleBinaryOperator) this.i);
            case 2:
                return new i4((BinaryOperator) this.i);
            default:
                return new o4((IntBinaryOperator) this.i);
        }
    }
}
