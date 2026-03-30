package j$.util.stream;

import java.util.concurrent.CountedCompleter;

/* JADX INFO: loaded from: classes2.dex */
public class w3 extends CountedCompleter {
    public final h2 a;
    public final int b;
    public final /* synthetic */ int c;
    public final Object d;

    public w3(h2 h2Var, Object obj, int i) {
        this.c = i;
        this.a = h2Var;
        this.b = 0;
        this.d = obj;
    }

    public w3(w3 w3Var, h2 h2Var, int i, byte b) {
        super(w3Var);
        this.a = h2Var;
        this.b = i;
    }

    @Override // java.util.concurrent.CountedCompleter
    public final void compute() {
        w3 w3VarA = this;
        while (w3VarA.a.h() != 0) {
            w3VarA.setPendingCount(w3VarA.a.h() - 1);
            int i = 0;
            int iCount = 0;
            while (i < w3VarA.a.h() - 1) {
                w3 w3VarA2 = w3VarA.a(i, w3VarA.b + iCount);
                iCount = (int) (w3VarA2.a.count() + ((long) iCount));
                w3VarA2.fork();
                i++;
            }
            w3VarA = w3VarA.a(i, w3VarA.b + iCount);
        }
        switch (w3VarA.c) {
            case 0:
                ((g2) w3VarA.a).c(w3VarA.b, w3VarA.d);
                break;
            default:
                w3VarA.a.f((Object[]) w3VarA.d, w3VarA.b);
                break;
        }
        w3VarA.propagateCompletion();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public w3(w3 w3Var, h2 h2Var, int i) {
        this(w3Var, h2Var, i, (byte) 0);
        this.c = 1;
        this.d = (Object[]) w3Var.d;
    }

    public final w3 a(int i, int i2) {
        switch (this.c) {
            case 0:
                return new w3(this, ((g2) this.a).a(i), i2);
            default:
                return new w3(this, this.a.a(i), i2);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public w3(w3 w3Var, g2 g2Var, int i) {
        this(w3Var, g2Var, i, (byte) 0);
        this.c = 0;
        this.d = w3Var.d;
    }
}
