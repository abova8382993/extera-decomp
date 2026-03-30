package j$.util.stream;

import j$.util.Spliterator;
import java.util.concurrent.CountedCompleter;
import java.util.function.IntFunction;

/* JADX INFO: loaded from: classes2.dex */
public final class y5 extends c {
    public final b j;
    public final IntFunction k;
    public final long l;
    public final long m;
    public long n;
    public volatile boolean o;

    @Override // j$.util.stream.c
    public final void f() {
        this.i = true;
        if (this.o) {
            d(x3.c0(this.j.z0()));
        }
    }

    @Override // j$.util.stream.e, java.util.concurrent.CountedCompleter
    public final void onCompletion(CountedCompleter countedCompleter) {
        y5 y5Var;
        h2 h2VarC0;
        e eVar = this.d;
        if (eVar != null) {
            this.n = ((y5) eVar).n + ((y5) this.e).n;
            if (this.i) {
                this.n = 0L;
                h2VarC0 = x3.c0(this.j.z0());
            } else {
                h2VarC0 = this.n == 0 ? x3.c0(this.j.z0()) : ((y5) this.d).n == 0 ? (h2) ((y5) this.e).i() : x3.Y(this.j.z0(), (h2) ((y5) this.d).i(), (h2) ((y5) this.e).i());
            }
            h2 h2VarE = h2VarC0;
            if (b()) {
                h2VarE = h2VarE.e(this.l, this.m >= 0 ? Math.min(h2VarE.count(), this.l + this.m) : this.n, this.k);
            }
            d(h2VarE);
            this.o = true;
        }
        if (this.m >= 0 && !b()) {
            long j = this.l + this.m;
            long j2 = this.o ? this.n : j(j);
            if (j2 >= j) {
                g();
            } else {
                y5 y5Var2 = (y5) ((e) getCompleter());
                Object obj = this;
                while (true) {
                    if (y5Var2 == null) {
                        if (j2 >= j) {
                            break;
                        }
                    } else {
                        if (obj == y5Var2.e && (y5Var = (y5) y5Var2.d) != null) {
                            long j3 = y5Var.j(j) + j2;
                            if (j3 >= j) {
                                break;
                            } else {
                                j2 = j3;
                            }
                        }
                        obj = y5Var2;
                        y5Var2 = (y5) ((e) y5Var2.getCompleter());
                    }
                }
                g();
            }
        }
        super.onCompletion(countedCompleter);
    }

    public y5(b bVar, x3 x3Var, Spliterator spliterator, IntFunction intFunction, long j, long j2) {
        super(x3Var, spliterator);
        this.j = bVar;
        this.k = intFunction;
        this.l = j;
        this.m = j2;
    }

    public y5(y5 y5Var, Spliterator spliterator) {
        super(y5Var, spliterator);
        this.j = y5Var.j;
        this.k = y5Var.k;
        this.l = y5Var.l;
        this.m = y5Var.m;
    }

    @Override // j$.util.stream.e
    public final e c(Spliterator spliterator) {
        return new y5(this, spliterator);
    }

    @Override // j$.util.stream.c
    public final Object h() {
        return x3.c0(this.j.z0());
    }

    @Override // j$.util.stream.e
    public final Object a() {
        if (b()) {
            c7 c7Var = c7.SIZED;
            b bVar = this.j;
            int i = bVar.j;
            int i2 = c7Var.e;
            z1 z1VarP0 = this.j.p0((i & i2) == i2 ? bVar.e0(this.b) : -1L, this.k);
            o5 o5VarD0 = this.j.D0(((b) this.a).m, z1VarP0);
            x3 x3Var = this.a;
            x3Var.a0(this.b, x3Var.t0(o5VarD0));
            return z1VarP0.build();
        }
        z1 z1VarP02 = this.j.p0(-1L, this.k);
        if (this.l == 0) {
            o5 o5VarD02 = this.j.D0(((b) this.a).m, z1VarP02);
            x3 x3Var2 = this.a;
            x3Var2.a0(this.b, x3Var2.t0(o5VarD02));
        } else {
            this.a.s0(this.b, z1VarP02);
        }
        h2 h2VarBuild = z1VarP02.build();
        this.n = h2VarBuild.count();
        this.o = true;
        this.b = null;
        return h2VarBuild;
    }

    public final long j(long j) {
        if (this.o) {
            return this.n;
        }
        y5 y5Var = (y5) this.d;
        y5 y5Var2 = (y5) this.e;
        if (y5Var == null || y5Var2 == null) {
            return this.n;
        }
        long j2 = y5Var.j(j);
        return j2 >= j ? j2 : y5Var2.j(j) + j2;
    }
}
