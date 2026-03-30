package j$.util.stream;

import j$.util.DesugarArrays;
import java.util.function.Consumer;
import java.util.function.LongConsumer;

/* JADX INFO: loaded from: classes2.dex */
public final class v6 extends x6 implements j$.util.y0 {
    public final /* synthetic */ w6 g;

    @Override // j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        j$.com.android.tools.r8.a.l(this, consumer);
    }

    @Override // j$.util.Spliterator
    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return j$.com.android.tools.r8.a.B(this, consumer);
    }

    @Override // j$.util.stream.x6
    public final void a(int i, Object obj, Object obj2) {
        ((LongConsumer) obj2).accept(((long[]) obj)[i]);
    }

    @Override // j$.util.stream.x6
    public final j$.util.b1 b(Object obj, int i, int i2) {
        return DesugarArrays.b((long[]) obj, i, i2 + i);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public v6(w6 w6Var, int i, int i2, int i3, int i4) {
        super(w6Var, i, i2, i3, i4);
        this.g = w6Var;
    }

    @Override // j$.util.stream.x6
    public final j$.util.b1 c(int i, int i2, int i3, int i4) {
        return new v6(this.g, i, i2, i3, i4);
    }
}
