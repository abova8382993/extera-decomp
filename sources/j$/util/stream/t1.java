package j$.util.stream;

import j$.util.function.Consumer$CC;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes2.dex */
public abstract class t1 implements o5 {
    public boolean a;
    public boolean b;

    @Override // j$.util.stream.o5
    public /* synthetic */ void accept(double d) {
        x3.z();
        throw null;
    }

    @Override // j$.util.stream.o5
    public /* synthetic */ void accept(int i) {
        x3.G();
        throw null;
    }

    @Override // j$.util.stream.o5
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
    public final /* synthetic */ void m(long j) {
    }

    public t1(u1 u1Var) {
        this.b = !u1Var.b;
    }

    @Override // j$.util.stream.o5
    public final boolean p() {
        return this.a;
    }
}
