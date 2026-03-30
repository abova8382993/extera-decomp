package j$.util.stream;

import j$.util.function.Consumer$CC;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;

/* JADX INFO: loaded from: classes2.dex */
public final class b4 implements s4, l5 {
    public double a;
    public final /* synthetic */ double b;
    public final /* synthetic */ DoubleBinaryOperator c;

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

    public b4(double d, DoubleBinaryOperator doubleBinaryOperator) {
        this.b = d;
        this.c = doubleBinaryOperator;
    }

    @Override // j$.util.stream.s4
    public final void t(s4 s4Var) {
        accept(((b4) s4Var).a);
    }

    @Override // j$.util.stream.o5
    public final void m(long j) {
        this.a = this.b;
    }

    @Override // j$.util.stream.o5
    public final void accept(double d) {
        this.a = this.c.applyAsDouble(this.a, d);
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return Double.valueOf(this.a);
    }
}
