package j$.util.stream;

import j$.util.Spliterator;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinPool;

/* JADX INFO: loaded from: classes2.dex */
public abstract class e extends CountedCompleter {
    public static final int g = ForkJoinPool.getCommonPoolParallelism() << 2;
    public final x3 a;
    public Spliterator b;
    public long c;
    public e d;
    public e e;
    public Object f;

    public abstract Object a();

    public abstract e c(Spliterator spliterator);

    public e(x3 x3Var, Spliterator spliterator) {
        super(null);
        this.a = x3Var;
        this.b = spliterator;
        this.c = 0L;
    }

    public e(e eVar, Spliterator spliterator) {
        super(eVar);
        this.b = spliterator;
        this.a = eVar.a;
        this.c = eVar.c;
    }

    public static long e(long j) {
        long j2 = j / ((long) g);
        if (j2 > 0) {
            return j2;
        }
        return 1L;
    }

    @Override // java.util.concurrent.CountedCompleter, java.util.concurrent.ForkJoinTask
    public Object getRawResult() {
        return this.f;
    }

    @Override // java.util.concurrent.CountedCompleter, java.util.concurrent.ForkJoinTask
    public final void setRawResult(Object obj) {
        if (obj != null) {
            throw new IllegalStateException();
        }
    }

    public void d(Object obj) {
        this.f = obj;
    }

    public final boolean b() {
        return ((e) getCompleter()) == null;
    }

    @Override // java.util.concurrent.CountedCompleter
    public void compute() {
        Spliterator spliteratorTrySplit;
        Spliterator spliterator = this.b;
        long jEstimateSize = spliterator.estimateSize();
        long jE = this.c;
        if (jE == 0) {
            jE = e(jEstimateSize);
            this.c = jE;
        }
        boolean z = false;
        e eVar = this;
        while (jEstimateSize > jE && (spliteratorTrySplit = spliterator.trySplit()) != null) {
            e eVarC = eVar.c(spliteratorTrySplit);
            eVar.d = eVarC;
            e eVarC2 = eVar.c(spliterator);
            eVar.e = eVarC2;
            eVar.setPendingCount(1);
            if (z) {
                spliterator = spliteratorTrySplit;
                eVar = eVarC;
                eVarC = eVarC2;
            } else {
                eVar = eVarC2;
            }
            z = !z;
            eVarC.fork();
            jEstimateSize = spliterator.estimateSize();
        }
        eVar.d(eVar.a());
        eVar.tryComplete();
    }

    @Override // java.util.concurrent.CountedCompleter
    public void onCompletion(CountedCompleter countedCompleter) {
        this.b = null;
        this.e = null;
        this.d = null;
    }
}
