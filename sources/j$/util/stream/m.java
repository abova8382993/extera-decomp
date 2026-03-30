package j$.util.stream;

import j$.util.Spliterator;

/* JADX INFO: loaded from: classes2.dex */
public final class m extends k5 {
    public final /* synthetic */ int b = 0;
    public boolean c;
    public Object d;

    public /* synthetic */ m(o5 o5Var) {
        super(o5Var);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public m(o8 o8Var, o5 o5Var) {
        super(o5Var);
        this.d = o8Var;
        this.c = true;
    }

    @Override // j$.util.stream.k5, j$.util.stream.o5
    public final void m(long j) {
        switch (this.b) {
            case 0:
                this.c = false;
                this.d = null;
                this.a.m(-1L);
                break;
            case 1:
                this.a.m(-1L);
                break;
            default:
                this.a.m(-1L);
                break;
        }
    }

    @Override // java.util.function.Consumer
    /* JADX INFO: renamed from: accept */
    public final void v(Object obj) {
        switch (this.b) {
            case 0:
                o5 o5Var = this.a;
                if (obj == null) {
                    if (this.c) {
                        return;
                    }
                    this.c = true;
                    this.d = null;
                    o5Var.v((Object) null);
                    return;
                }
                Object obj2 = this.d;
                if (obj2 == null || !obj.equals(obj2)) {
                    this.d = obj;
                    o5Var.v(obj);
                    return;
                }
                return;
            case 1:
                Stream stream = (Stream) ((j$.time.s) ((s) this.d).t).apply(obj);
                if (stream != null) {
                    try {
                        boolean z = this.c;
                        o5 o5Var2 = this.a;
                        if (!z) {
                            ((Stream) stream.sequential()).forEach(o5Var2);
                        } else {
                            Spliterator spliterator = ((Stream) stream.sequential()).spliterator();
                            while (!o5Var2.p() && spliterator.tryAdvance(o5Var2)) {
                            }
                        }
                    } catch (Throwable th) {
                        try {
                            stream.close();
                            break;
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                    break;
                }
                if (stream != null) {
                    stream.close();
                    return;
                }
                return;
            default:
                if (this.c) {
                    boolean zTest = ((o8) this.d).t.test(obj);
                    this.c = zTest;
                    if (zTest) {
                        this.a.v(obj);
                        return;
                    }
                    return;
                }
                return;
        }
    }

    @Override // j$.util.stream.k5, j$.util.stream.o5
    public boolean p() {
        switch (this.b) {
            case 1:
                this.c = true;
                return this.a.p();
            case 2:
                return !this.c || this.a.p();
            default:
                return super.p();
        }
    }

    @Override // j$.util.stream.k5, j$.util.stream.o5
    public void end() {
        switch (this.b) {
            case 0:
                this.c = false;
                this.d = null;
                this.a.end();
                break;
            default:
                super.end();
                break;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public m(s sVar, o5 o5Var) {
        super(o5Var);
        this.d = sVar;
    }
}
