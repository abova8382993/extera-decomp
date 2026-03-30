package j$.util.stream;

import j$.util.Spliterator;
import java.util.concurrent.CountedCompleter;
import java.util.function.IntFunction;

/* JADX INFO: loaded from: classes2.dex */
public final class a9 extends e {
    public final b h;
    public final IntFunction i;
    public final boolean j;
    public long k;
    public long l;

    @Override // j$.util.stream.e, java.util.concurrent.CountedCompleter
    public final void onCompletion(CountedCompleter countedCompleter) {
        e eVar = this.d;
        if (eVar != null) {
            if (this.j) {
                a9 a9Var = (a9) eVar;
                long j = a9Var.l;
                this.l = j;
                if (j == a9Var.k) {
                    this.l = j + ((a9) this.e).l;
                }
            }
            a9 a9Var2 = (a9) eVar;
            long j2 = a9Var2.k;
            a9 a9Var3 = (a9) this.e;
            this.k = j2 + a9Var3.k;
            h2 h2VarY = a9Var2.k == 0 ? (h2) a9Var3.f : a9Var3.k == 0 ? (h2) a9Var2.f : x3.Y(this.h.z0(), (h2) ((a9) this.d).f, (h2) ((a9) this.e).f);
            if (b() && this.j) {
                h2VarY = h2VarY.e(this.l, h2VarY.count(), this.i);
            }
            this.f = h2VarY;
        }
        super.onCompletion(countedCompleter);
    }

    public a9(b bVar, x3 x3Var, Spliterator spliterator, IntFunction intFunction) {
        super(x3Var, spliterator);
        this.h = bVar;
        this.i = intFunction;
        this.j = c7.ORDERED.k(((b) x3Var).m);
    }

    public a9(a9 a9Var, Spliterator spliterator) {
        super(a9Var, spliterator);
        this.h = a9Var.h;
        this.i = a9Var.i;
        this.j = a9Var.j;
    }

    @Override // j$.util.stream.e
    public final e c(Spliterator spliterator) {
        return new a9(this, spliterator);
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x001c  */
    @Override // j$.util.stream.e
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object a() {
        /*
            r5 = this;
            boolean r0 = r5.b()
            if (r0 != 0) goto L1c
            boolean r1 = r5.j
            if (r1 == 0) goto L1c
            j$.util.stream.c7 r1 = j$.util.stream.c7.SIZED
            j$.util.stream.b r2 = r5.h
            int r3 = r2.j
            int r1 = r1.e
            r3 = r3 & r1
            if (r3 != r1) goto L1c
            j$.util.Spliterator r1 = r5.b
            long r1 = r2.e0(r1)
            goto L1e
        L1c:
            r1 = -1
        L1e:
            j$.util.stream.x3 r3 = r5.a
            java.util.function.IntFunction r4 = r5.i
            j$.util.stream.z1 r1 = r3.p0(r1, r4)
            j$.util.stream.b r2 = r5.h
            j$.util.stream.y8 r2 = (j$.util.stream.y8) r2
            boolean r3 = r5.j
            if (r3 == 0) goto L32
            if (r0 != 0) goto L32
            r0 = 1
            goto L33
        L32:
            r0 = 0
        L33:
            j$.util.stream.z8 r0 = r2.h(r1, r0)
            j$.util.stream.x3 r2 = r5.a
            j$.util.Spliterator r3 = r5.b
            r2.s0(r3, r0)
            j$.util.stream.h2 r1 = r1.build()
            long r2 = r1.count()
            r5.k = r2
            long r2 = r0.s()
            r5.l = r2
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.stream.a9.a():java.lang.Object");
    }
}
