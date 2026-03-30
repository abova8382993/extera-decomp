package j$.util.stream;

import j$.util.function.Consumer$CC;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes2.dex */
public abstract class y4 extends t4 implements s4 {
    public long b;

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

    @Override // j$.util.stream.o5
    public final void m(long j) {
        this.b = 0L;
    }
}
