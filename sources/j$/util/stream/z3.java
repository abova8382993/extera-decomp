package j$.util.stream;

import j$.util.function.Consumer$CC;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes2.dex */
public final class z3 extends t4 implements s4, n5 {
    public final /* synthetic */ Supplier b;
    public final /* synthetic */ ObjLongConsumer c;
    public final /* synthetic */ q d;

    @Override // j$.util.stream.o5
    public final /* synthetic */ void accept(double d) {
        x3.z();
        throw null;
    }

    @Override // j$.util.stream.o5
    public final /* synthetic */ void accept(int i) {
        x3.G();
        throw null;
    }

    @Override // java.util.function.Consumer
    /* JADX INFO: renamed from: accept */
    public final /* bridge */ /* synthetic */ void v(Object obj) {
        v((Long) obj);
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return j$.com.android.tools.r8.a.d(this, longConsumer);
    }

    @Override // j$.util.stream.o5
    public final /* synthetic */ void end() {
    }

    @Override // j$.util.stream.o5
    public final /* synthetic */ boolean p() {
        return false;
    }

    @Override // j$.util.stream.n5
    public final /* synthetic */ void v(Long l) {
        x3.E(this, l);
    }

    @Override // j$.util.stream.s4
    public final void t(s4 s4Var) {
        this.a = this.d.apply(this.a, ((z3) s4Var).a);
    }

    @Override // j$.util.stream.o5
    public final void m(long j) {
        this.a = this.b.get();
    }

    @Override // j$.util.stream.o5
    public final void accept(long j) {
        this.c.accept(this.a, j);
    }

    public z3(Supplier supplier, ObjLongConsumer objLongConsumer, q qVar) {
        this.b = supplier;
        this.c = objLongConsumer;
        this.d = qVar;
    }
}
