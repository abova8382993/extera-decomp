package j$.util.stream;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/* JADX INFO: loaded from: classes2.dex */
public final class n extends k5 {
    public final /* synthetic */ int b;
    public Object c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ n(b bVar, o5 o5Var, int i) {
        super(o5Var);
        this.b = i;
        this.c = bVar;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ n(o5 o5Var) {
        super(o5Var);
        this.b = 0;
    }

    @Override // j$.util.stream.k5, j$.util.stream.o5
    public void end() {
        switch (this.b) {
            case 0:
                this.c = null;
                this.a.end();
                break;
            default:
                super.end();
                break;
        }
    }

    @Override // j$.util.stream.k5, j$.util.stream.o5
    public void m(long j) {
        switch (this.b) {
            case 0:
                this.c = new HashSet();
                this.a.m(-1L);
                break;
            case 1:
            default:
                super.m(j);
                break;
            case 2:
                this.a.m(-1L);
                break;
        }
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.b) {
            case 0:
                if (!((Set) this.c).contains(obj)) {
                    ((Set) this.c).add(obj);
                    this.a.accept(obj);
                }
                break;
            case 1:
                ((Consumer) ((s) this.c).t).accept(obj);
                this.a.accept(obj);
                break;
            case 2:
                if (((Predicate) ((s) this.c).t).test(obj)) {
                    this.a.accept(obj);
                }
                break;
            case 3:
                this.a.accept(((Function) ((s) this.c).t).apply(obj));
                break;
            case 4:
                this.a.accept(((ToIntFunction) ((v0) this.c).t).applyAsInt(obj));
                break;
            case 5:
                this.a.accept(((ToLongFunction) ((g1) this.c).t).applyAsLong(obj));
                break;
            default:
                this.a.accept(((ToDoubleFunction) ((x) this.c).t).applyAsDouble(obj));
                break;
        }
    }
}
