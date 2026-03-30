package j$.util.stream;

import j$.util.Spliterator;
import j$.util.function.Consumer$CC;
import java.util.concurrent.CountedCompleter;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes2.dex */
public abstract class s3 extends CountedCompleter implements o5 {
    public final Spliterator a;
    public final x3 b;
    public final long c;
    public final long d;
    public final long e;
    public int f;
    public int g;

    public abstract s3 a(Spliterator spliterator, long j, long j2);

    public /* synthetic */ void accept(double d) {
        x3.z();
        throw null;
    }

    public /* synthetic */ void accept(int i) {
        x3.G();
        throw null;
    }

    public /* synthetic */ void accept(long j) {
        x3.H();
        throw null;
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    @Override // j$.util.stream.o5
    public final /* synthetic */ void end() {
    }

    @Override // j$.util.stream.o5
    public final /* synthetic */ boolean p() {
        return false;
    }

    public s3(Spliterator spliterator, x3 x3Var, int i) {
        this.a = spliterator;
        this.b = x3Var;
        this.c = e.e(spliterator.estimateSize());
        this.d = 0L;
        this.e = i;
    }

    public s3(s3 s3Var, Spliterator spliterator, long j, long j2, int i) {
        super(s3Var);
        this.a = spliterator;
        this.b = s3Var.b;
        this.c = s3Var.c;
        this.d = j;
        this.e = j2;
        if (j < 0 || j2 < 0 || (j + j2) - 1 >= i) {
            throw new IllegalArgumentException(String.format("offset and length interval [%d, %d + %d) is not within array size interval [0, %d)", Long.valueOf(j), Long.valueOf(j), Long.valueOf(j2), Integer.valueOf(i)));
        }
    }

    @Override // java.util.concurrent.CountedCompleter
    public final void compute() {
        Spliterator spliteratorTrySplit;
        Spliterator spliterator = this.a;
        s3 s3VarA = this;
        while (spliterator.estimateSize() > s3VarA.c && (spliteratorTrySplit = spliterator.trySplit()) != null) {
            s3VarA.setPendingCount(1);
            long jEstimateSize = spliteratorTrySplit.estimateSize();
            s3 s3Var = s3VarA;
            s3Var.a(spliteratorTrySplit, s3VarA.d, jEstimateSize).fork();
            s3VarA = s3Var.a(spliterator, s3Var.d + jEstimateSize, s3Var.e - jEstimateSize);
        }
        s3 s3Var2 = s3VarA;
        s3Var2.b.s0(spliterator, s3Var2);
        s3Var2.propagateCompletion();
    }

    @Override // j$.util.stream.o5
    public final void m(long j) {
        long j2 = this.e;
        if (j > j2) {
            throw new IllegalStateException("size passed to Sink.begin exceeds array length");
        }
        int i = (int) this.d;
        this.f = i;
        this.g = i + ((int) j2);
    }
}
