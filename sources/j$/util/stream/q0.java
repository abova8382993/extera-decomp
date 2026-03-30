package j$.util.stream;

import j$.util.Spliterator;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes2.dex */
public final class q0 extends r0 {
    public final Consumer b;

    @Override // java.util.function.Supplier
    public final /* bridge */ /* synthetic */ Object get() {
        return null;
    }

    @Override // j$.util.stream.l8
    public final Object f(b bVar, Spliterator spliterator) {
        bVar.s0(spliterator, this);
        return null;
    }

    @Override // j$.util.stream.l8
    public final /* bridge */ /* synthetic */ Object i(x3 x3Var, Spliterator spliterator) {
        a(x3Var, spliterator);
        return null;
    }

    public q0(Consumer consumer, boolean z) {
        super(z);
        this.b = consumer;
    }

    @Override // java.util.function.Consumer
    /* JADX INFO: renamed from: accept */
    public final void v(Object obj) {
        this.b.v(obj);
    }
}
