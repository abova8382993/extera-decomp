package j$.util.stream;

import j$.util.Spliterator;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes2.dex */
public final class f0 implements l8 {
    public final int a;
    public final Object b;
    public final Predicate c;
    public final Supplier d;

    public f0(boolean z, d7 d7Var, Object obj, Predicate predicate, Supplier supplier) {
        this.a = (z ? 0 : c7.r) | c7.u;
        this.b = obj;
        this.c = predicate;
        this.d = supplier;
    }

    @Override // j$.util.stream.l8
    public final int u() {
        return this.a;
    }

    @Override // j$.util.stream.l8
    public final Object f(b bVar, Spliterator spliterator) {
        m8 m8Var = (m8) this.d.get();
        bVar.s0(spliterator, m8Var);
        Object obj = m8Var.get();
        return obj != null ? obj : this.b;
    }

    @Override // j$.util.stream.l8
    public final Object i(x3 x3Var, Spliterator spliterator) {
        b bVar = (b) x3Var;
        return new l0(this, c7.ORDERED.k(bVar.m), bVar, spliterator).invoke();
    }
}
