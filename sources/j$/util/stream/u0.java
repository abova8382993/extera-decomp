package j$.util.stream;

import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;

/* JADX INFO: loaded from: classes2.dex */
public final class u0 extends i5 {
    public final /* synthetic */ int b;
    public final /* synthetic */ b c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ u0(b bVar, o5 o5Var, int i) {
        super(o5Var);
        this.b = i;
        this.c = bVar;
    }

    @Override // j$.util.stream.i5, j$.util.stream.o5
    public void m(long j) {
        switch (this.b) {
            case 5:
                this.a.m(-1L);
                break;
            default:
                super.m(j);
                break;
        }
    }

    @Override // j$.util.stream.m5, j$.util.stream.o5
    public final void accept(int i) {
        switch (this.b) {
            case 0:
                this.a.accept(((IntFunction) ((s) this.c).t).apply(i));
                return;
            case 1:
                ((IntConsumer) ((v0) this.c).t).accept(i);
                this.a.accept(i);
                return;
            case 2:
                this.a.accept(((IntUnaryOperator) ((v0) this.c).t).applyAsInt(i));
                return;
            case 3:
                ((v) this.c).getClass();
                IntToLongFunction intToLongFunction = null;
                intToLongFunction.applyAsLong(i);
                throw null;
            case 4:
                ((t) this.c).getClass();
                IntToDoubleFunction intToDoubleFunction = null;
                intToDoubleFunction.applyAsDouble(i);
                throw null;
            default:
                if (((IntPredicate) ((v0) this.c).t).test(i)) {
                    this.a.accept(i);
                    return;
                }
                return;
        }
    }
}
