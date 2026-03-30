package j$.util.stream;

import j$.util.Spliterator;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes2.dex */
public final class v1 extends c {
    public final j$.util.concurrent.s j;

    public v1(j$.util.concurrent.s sVar, b bVar, Spliterator spliterator) {
        super(bVar, spliterator);
        this.j = sVar;
    }

    public v1(v1 v1Var, Spliterator spliterator) {
        super(v1Var, spliterator);
        this.j = v1Var.j;
    }

    @Override // j$.util.stream.e
    public final e c(Spliterator spliterator) {
        return new v1(this, spliterator);
    }

    @Override // j$.util.stream.e
    public final Object a() {
        x3 x3Var = this.a;
        t1 t1Var = (t1) ((Supplier) this.j.c).get();
        x3Var.s0(this.b, t1Var);
        boolean z = t1Var.b;
        if (z == ((u1) this.j.b).b) {
            Boolean boolValueOf = Boolean.valueOf(z);
            AtomicReference atomicReference = this.h;
            while (!atomicReference.compareAndSet(null, boolValueOf) && atomicReference.get() == null) {
            }
        }
        return null;
    }

    @Override // j$.util.stream.c
    public final Object h() {
        return Boolean.valueOf(!((u1) this.j.b).b);
    }
}
