package j$.util.stream;

import j$.util.Spliterator;
import java.util.function.BinaryOperator;
import java.util.function.LongFunction;

/* JADX INFO: loaded from: classes2.dex */
public final class m2 extends n2 {
    public final /* synthetic */ int k;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ m2(x3 x3Var, Spliterator spliterator, LongFunction longFunction, BinaryOperator binaryOperator, int i) {
        super(x3Var, spliterator, longFunction, binaryOperator);
        this.k = i;
    }

    @Override // j$.util.stream.n2, j$.util.stream.e
    public final e c(Spliterator spliterator) {
        switch (this.k) {
        }
        return new n2(this, spliterator);
    }

    @Override // j$.util.stream.n2, j$.util.stream.e
    public final /* bridge */ /* synthetic */ Object a() {
        switch (this.k) {
        }
        return a();
    }
}
