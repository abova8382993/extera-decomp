package j$.util.stream;

import j$.util.Spliterator;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes2.dex */
public final class l0 extends c {
    public final f0 j;
    public final boolean k;

    public l0(f0 f0Var, boolean z, b bVar, Spliterator spliterator) {
        super(bVar, spliterator);
        this.k = z;
        this.j = f0Var;
    }

    public l0(l0 l0Var, Spliterator spliterator) {
        super(l0Var, spliterator);
        this.k = l0Var.k;
        this.j = l0Var.j;
    }

    @Override // j$.util.stream.e
    public final e c(Spliterator spliterator) {
        return new l0(this, spliterator);
    }

    @Override // j$.util.stream.c
    public final Object h() {
        return this.j.b;
    }

    @Override // j$.util.stream.e
    public final Object a() {
        x3 x3Var = this.a;
        m8 m8Var = (m8) this.j.d.get();
        x3Var.s0(this.b, m8Var);
        Object obj = m8Var.get();
        if (this.k) {
            if (obj != null) {
                e eVar = this;
                while (eVar != null) {
                    e eVar2 = (e) eVar.getCompleter();
                    if (eVar2 != null && eVar2.d != eVar) {
                        g();
                        return obj;
                    }
                    eVar = eVar2;
                }
                AtomicReference atomicReference = this.h;
                while (!atomicReference.compareAndSet(null, obj) && atomicReference.get() == null) {
                }
                return obj;
            }
        } else if (obj != null) {
            AtomicReference atomicReference2 = this.h;
            while (!atomicReference2.compareAndSet(null, obj) && atomicReference2.get() == null) {
            }
        }
        return null;
    }

    @Override // j$.util.stream.e, java.util.concurrent.CountedCompleter
    public final void onCompletion(CountedCompleter countedCompleter) {
        if (this.k) {
            l0 l0Var = (l0) this.d;
            l0 l0Var2 = null;
            while (true) {
                if (l0Var != l0Var2) {
                    Object objI = l0Var.i();
                    if (objI != null && this.j.c.test(objI)) {
                        d(objI);
                        e eVar = this;
                        while (true) {
                            if (eVar != null) {
                                e eVar2 = (e) eVar.getCompleter();
                                if (eVar2 != null && eVar2.d != eVar) {
                                    g();
                                    break;
                                }
                                eVar = eVar2;
                            } else {
                                AtomicReference atomicReference = this.h;
                                while (!atomicReference.compareAndSet(null, objI) && atomicReference.get() == null) {
                                }
                            }
                        }
                    } else {
                        l0Var2 = l0Var;
                        l0Var = (l0) this.e;
                    }
                } else {
                    break;
                }
            }
        }
        super.onCompletion(countedCompleter);
    }
}
