package j$.util.stream;

import j$.util.Spliterator;
import j$.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountedCompleter;

/* JADX INFO: loaded from: classes2.dex */
public final class s0 extends CountedCompleter {
    public final x3 a;
    public Spliterator b;
    public final long c;
    public final ConcurrentHashMap d;
    public final r0 e;
    public final s0 f;
    public h2 g;

    public s0(x3 x3Var, Spliterator spliterator, r0 r0Var) {
        super(null);
        this.a = x3Var;
        this.b = spliterator;
        this.c = e.e(spliterator.estimateSize());
        this.d = new ConcurrentHashMap(Math.max(16, e.g << 1));
        this.e = r0Var;
        this.f = null;
    }

    public s0(s0 s0Var, Spliterator spliterator, s0 s0Var2) {
        super(s0Var);
        this.a = s0Var.a;
        this.b = spliterator;
        this.c = s0Var.c;
        this.d = s0Var.d;
        this.e = s0Var.e;
        this.f = s0Var2;
    }

    @Override // java.util.concurrent.CountedCompleter
    public final void compute() {
        Spliterator spliteratorTrySplit;
        Spliterator spliterator = this.b;
        long j = this.c;
        boolean z = false;
        s0 s0Var = this;
        while (spliterator.estimateSize() > j && (spliteratorTrySplit = spliterator.trySplit()) != null) {
            s0 s0Var2 = new s0(s0Var, spliteratorTrySplit, s0Var.f);
            s0 s0Var3 = new s0(s0Var, spliterator, s0Var2);
            s0Var.addToPendingCount(1);
            s0Var3.addToPendingCount(1);
            s0Var.d.put(s0Var2, s0Var3);
            if (s0Var.f != null) {
                s0Var2.addToPendingCount(1);
                if (s0Var.d.replace(s0Var.f, s0Var, s0Var2)) {
                    s0Var.addToPendingCount(-1);
                } else {
                    s0Var2.addToPendingCount(-1);
                }
            }
            if (z) {
                spliterator = spliteratorTrySplit;
                s0Var = s0Var2;
                s0Var2 = s0Var3;
            } else {
                s0Var = s0Var3;
            }
            z = !z;
            s0Var2.fork();
        }
        if (s0Var.getPendingCount() > 0) {
            p pVar = new p(10);
            x3 x3Var = s0Var.a;
            z1 z1VarP0 = x3Var.p0(x3Var.e0(spliterator), pVar);
            s0Var.a.s0(spliterator, z1VarP0);
            s0Var.g = z1VarP0.build();
            s0Var.b = null;
        }
        s0Var.tryComplete();
    }

    @Override // java.util.concurrent.CountedCompleter
    public final void onCompletion(CountedCompleter countedCompleter) {
        h2 h2Var = this.g;
        if (h2Var != null) {
            h2Var.forEach(this.e);
            this.g = null;
        } else {
            Spliterator spliterator = this.b;
            if (spliterator != null) {
                this.a.s0(spliterator, this.e);
                this.b = null;
            }
        }
        s0 s0Var = (s0) this.d.remove(this);
        if (s0Var != null) {
            s0Var.tryComplete();
        }
    }
}
