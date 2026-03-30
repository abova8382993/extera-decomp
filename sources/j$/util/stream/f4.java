package j$.util.stream;

import j$.util.function.Consumer$CC;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes2.dex */
public final class f4 extends t4 implements s4, l5 {
    public final /* synthetic */ Supplier b;
    public final /* synthetic */ ObjDoubleConsumer c;
    public final /* synthetic */ q d;

    @Override // j$.util.stream.l5
    public final /* synthetic */ void A(Double d) {
        x3.A(this, d);
    }

    @Override // j$.util.stream.o5
    public final /* synthetic */ void accept(int i) {
        x3.G();
        throw null;
    }

    @Override // j$.util.stream.o5
    public final /* synthetic */ void accept(long j) {
        x3.H();
        throw null;
    }

    @Override // java.util.function.Consumer
    /* JADX INFO: renamed from: accept */
    public final /* bridge */ /* synthetic */ void v(Object obj) {
        A((Double) obj);
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return j$.com.android.tools.r8.a.b(this, doubleConsumer);
    }

    @Override // j$.util.stream.o5
    public final /* synthetic */ void end() {
    }

    @Override // j$.util.stream.o5
    public final /* synthetic */ boolean p() {
        return false;
    }

    @Override // j$.util.stream.s4
    public final void t(s4 s4Var) {
        this.a = this.d.apply(this.a, ((f4) s4Var).a);
    }

    @Override // j$.util.stream.o5
    public final void m(long j) {
        this.a = this.b.get();
    }

    @Override // j$.util.stream.o5
    public final void accept(double d) {
        this.c.accept(this.a, d);
    }

    public f4(Supplier supplier, ObjDoubleConsumer objDoubleConsumer, q qVar) {
        this.b = supplier;
        this.c = objDoubleConsumer;
        this.d = qVar;
    }
}
