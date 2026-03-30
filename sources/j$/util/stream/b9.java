package j$.util.stream;

import j$.util.Spliterator;
import java.util.function.IntFunction;

/* JADX INFO: loaded from: classes2.dex */
public final class b9 extends c {
    public final b j;
    public final IntFunction k;
    public final boolean l;
    public long m;
    public boolean n;
    public volatile boolean o;

    @Override // j$.util.stream.c
    public final void f() {
        this.i = true;
        if (this.l && this.o) {
            d(x3.c0(this.j.z0()));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0041  */
    @Override // j$.util.stream.e, java.util.concurrent.CountedCompleter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCompletion(java.util.concurrent.CountedCompleter r9) {
        /*
            r8 = this;
            j$.util.stream.e r0 = r8.d
            if (r0 != 0) goto L6
            goto L8b
        L6:
            j$.util.stream.b9 r0 = (j$.util.stream.b9) r0
            boolean r0 = r0.n
            j$.util.stream.e r1 = r8.e
            j$.util.stream.b9 r1 = (j$.util.stream.b9) r1
            boolean r1 = r1.n
            r0 = r0 | r1
            r8.n = r0
            boolean r0 = r8.l
            r1 = 0
            if (r0 == 0) goto L2a
            boolean r0 = r8.i
            if (r0 == 0) goto L2a
            r8.m = r1
            j$.util.stream.b r0 = r8.j
            j$.util.stream.d7 r0 = r0.z0()
            j$.util.stream.a3 r0 = j$.util.stream.x3.c0(r0)
            goto L88
        L2a:
            boolean r0 = r8.l
            if (r0 == 0) goto L41
            j$.util.stream.e r0 = r8.d
            j$.util.stream.b9 r0 = (j$.util.stream.b9) r0
            boolean r3 = r0.n
            if (r3 == 0) goto L41
            long r1 = r0.m
            r8.m = r1
            java.lang.Object r0 = r0.i()
            j$.util.stream.h2 r0 = (j$.util.stream.h2) r0
            goto L88
        L41:
            j$.util.stream.e r0 = r8.d
            j$.util.stream.b9 r0 = (j$.util.stream.b9) r0
            long r3 = r0.m
            j$.util.stream.e r5 = r8.e
            j$.util.stream.b9 r5 = (j$.util.stream.b9) r5
            long r6 = r5.m
            long r3 = r3 + r6
            r8.m = r3
            long r3 = r0.m
            int r3 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r3 != 0) goto L5d
            java.lang.Object r0 = r5.i()
            j$.util.stream.h2 r0 = (j$.util.stream.h2) r0
            goto L88
        L5d:
            long r3 = r5.m
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 != 0) goto L6a
            java.lang.Object r0 = r0.i()
            j$.util.stream.h2 r0 = (j$.util.stream.h2) r0
            goto L88
        L6a:
            j$.util.stream.b r0 = r8.j
            j$.util.stream.d7 r0 = r0.z0()
            j$.util.stream.e r1 = r8.d
            j$.util.stream.b9 r1 = (j$.util.stream.b9) r1
            java.lang.Object r1 = r1.i()
            j$.util.stream.h2 r1 = (j$.util.stream.h2) r1
            j$.util.stream.e r2 = r8.e
            j$.util.stream.b9 r2 = (j$.util.stream.b9) r2
            java.lang.Object r2 = r2.i()
            j$.util.stream.h2 r2 = (j$.util.stream.h2) r2
            j$.util.stream.j2 r0 = j$.util.stream.x3.Y(r0, r1, r2)
        L88:
            r8.d(r0)
        L8b:
            r0 = 1
            r8.o = r0
            super.onCompletion(r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.stream.b9.onCompletion(java.util.concurrent.CountedCompleter):void");
    }

    public b9(b bVar, x3 x3Var, Spliterator spliterator, IntFunction intFunction) {
        super(x3Var, spliterator);
        this.j = bVar;
        this.k = intFunction;
        this.l = c7.ORDERED.k(((b) x3Var).m);
    }

    public b9(b9 b9Var, Spliterator spliterator) {
        super(b9Var, spliterator);
        this.j = b9Var.j;
        this.k = b9Var.k;
        this.l = b9Var.l;
    }

    @Override // j$.util.stream.e
    public final e c(Spliterator spliterator) {
        return new b9(this, spliterator);
    }

    @Override // j$.util.stream.c
    public final Object h() {
        return x3.c0(this.j.z0());
    }

    @Override // j$.util.stream.e
    public final Object a() {
        z1 z1VarP0 = this.a.p0(-1L, this.k);
        o5 o5VarD0 = this.j.D0(((b) this.a).m, z1VarP0);
        x3 x3Var = this.a;
        boolean zA0 = x3Var.a0(this.b, x3Var.t0(o5VarD0));
        this.n = zA0;
        if (zA0) {
            g();
        }
        h2 h2VarBuild = z1VarP0.build();
        this.m = h2VarBuild.count();
        return h2VarBuild;
    }
}
