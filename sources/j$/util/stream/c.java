package j$.util.stream;

import j$.util.Spliterator;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes2.dex */
public abstract class c extends e {
    public final AtomicReference h;
    public volatile boolean i;

    public abstract Object h();

    public c(x3 x3Var, Spliterator spliterator) {
        super(x3Var, spliterator);
        this.h = new AtomicReference(null);
    }

    public c(c cVar, Spliterator spliterator) {
        super(cVar, spliterator);
        this.h = cVar.h;
    }

    @Override // j$.util.stream.e, java.util.concurrent.CountedCompleter
    public final void compute() {
        Object objH;
        Spliterator spliteratorTrySplit;
        Spliterator spliterator = this.b;
        long jEstimateSize = spliterator.estimateSize();
        long jE = this.c;
        if (jE == 0) {
            jE = e.e(jEstimateSize);
            this.c = jE;
        }
        AtomicReference atomicReference = this.h;
        boolean z = false;
        c cVar = this;
        while (true) {
            objH = atomicReference.get();
            if (objH != null) {
                break;
            }
            boolean z2 = cVar.i;
            if (!z2) {
                CountedCompleter<?> completer = cVar.getCompleter();
                while (true) {
                    c cVar2 = (c) ((e) completer);
                    if (z2 || cVar2 == null) {
                        break;
                    }
                    z2 = cVar2.i;
                    completer = cVar2.getCompleter();
                }
            }
            if (z2) {
                objH = cVar.h();
                break;
            }
            if (jEstimateSize <= jE || (spliteratorTrySplit = spliterator.trySplit()) == null) {
                break;
            }
            c cVar3 = (c) cVar.c(spliteratorTrySplit);
            cVar.d = cVar3;
            c cVar4 = (c) cVar.c(spliterator);
            cVar.e = cVar4;
            cVar.setPendingCount(1);
            if (z) {
                spliterator = spliteratorTrySplit;
                cVar = cVar3;
                cVar3 = cVar4;
            } else {
                cVar = cVar4;
            }
            z = !z;
            cVar3.fork();
            jEstimateSize = spliterator.estimateSize();
        }
        objH = cVar.a();
        cVar.d(objH);
        cVar.tryComplete();
    }

    @Override // j$.util.stream.e
    public final void d(Object obj) {
        if (!b()) {
            this.f = obj;
        } else if (obj != null) {
            AtomicReference atomicReference = this.h;
            while (!atomicReference.compareAndSet(null, obj) && atomicReference.get() == null) {
            }
        }
    }

    @Override // j$.util.stream.e, java.util.concurrent.CountedCompleter, java.util.concurrent.ForkJoinTask
    public final Object getRawResult() {
        return i();
    }

    public final Object i() {
        if (b()) {
            Object obj = this.h.get();
            return obj == null ? h() : obj;
        }
        return this.f;
    }

    public void f() {
        this.i = true;
    }

    public final void g() {
        c cVar = this;
        for (c cVar2 = (c) ((e) getCompleter()); cVar2 != null; cVar2 = (c) ((e) cVar2.getCompleter())) {
            if (cVar2.d == cVar) {
                c cVar3 = (c) cVar2.e;
                if (!cVar3.i) {
                    cVar3.f();
                }
            }
            cVar = cVar2;
        }
    }
}
