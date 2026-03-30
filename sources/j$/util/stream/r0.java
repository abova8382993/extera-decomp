package j$.util.stream;

import j$.util.Spliterator;
import j$.util.function.Consumer$CC;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes2.dex */
public abstract class r0 implements l8, m8 {
    public final boolean a;

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
    public final /* synthetic */ void m(long j) {
    }

    @Override // j$.util.stream.o5
    public final /* synthetic */ boolean p() {
        return false;
    }

    public r0(boolean z) {
        this.a = z;
    }

    @Override // j$.util.stream.l8
    public final int u() {
        if (this.a) {
            return 0;
        }
        return c7.r;
    }

    public final void a(x3 x3Var, Spliterator spliterator) {
        if (this.a) {
            new s0(x3Var, spliterator, this).invoke();
        } else {
            new t0(x3Var, spliterator, x3Var.t0(this)).invoke();
        }
    }
}
