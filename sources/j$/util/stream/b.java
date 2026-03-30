package j$.util.stream;

import j$.util.Objects;
import j$.util.Spliterator;
import java.util.function.IntFunction;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes2.dex */
public abstract class b extends x3 implements BaseStream {
    public final b h;
    public final b i;
    public final int j;
    public final b k;
    public int l;
    public int m;
    public Spliterator n;
    public boolean o;
    public final boolean p;
    public Runnable q;
    public boolean r;

    public abstract boolean C0();

    public abstract o5 D0(int i, o5 o5Var);

    public abstract Spliterator G0(b bVar, Supplier supplier, boolean z);

    public abstract h2 x0(b bVar, Spliterator spliterator, boolean z, IntFunction intFunction);

    public abstract boolean y0(Spliterator spliterator, o5 o5Var);

    public abstract d7 z0();

    public b(Spliterator spliterator, int i, boolean z) {
        this.i = null;
        this.n = spliterator;
        this.h = this;
        int i2 = c7.g & i;
        this.j = i2;
        this.m = (~(i2 << 1)) & c7.l;
        this.l = 0;
        this.r = z;
    }

    public b(b bVar, int i) {
        if (bVar.o) {
            throw new IllegalStateException("stream has already been operated upon or closed");
        }
        bVar.o = true;
        bVar.k = this;
        this.i = bVar;
        this.j = c7.h & i;
        this.m = c7.h(i, bVar.m);
        b bVar2 = bVar.h;
        this.h = bVar2;
        if (C0()) {
            bVar2.p = true;
        }
        this.l = bVar.l + 1;
    }

    public final Object v0(l8 l8Var) {
        if (this.o) {
            throw new IllegalStateException("stream has already been operated upon or closed");
        }
        this.o = true;
        if (this.h.r) {
            return l8Var.i(this, E0(l8Var.u()));
        }
        return l8Var.f(this, E0(l8Var.u()));
    }

    public final h2 w0(IntFunction intFunction) {
        if (this.o) {
            throw new IllegalStateException("stream has already been operated upon or closed");
        }
        this.o = true;
        if (this.h.r && this.i != null && C0()) {
            this.l = 0;
            b bVar = this.i;
            return A0(bVar, bVar.E0(0), intFunction);
        }
        return d0(E0(0), true, intFunction);
    }

    public final Spliterator F0() {
        b bVar = this.h;
        if (this != bVar) {
            throw new IllegalStateException();
        }
        if (this.o) {
            throw new IllegalStateException("stream has already been operated upon or closed");
        }
        this.o = true;
        Spliterator spliterator = bVar.n;
        if (spliterator != null) {
            bVar.n = null;
            return spliterator;
        }
        throw new IllegalStateException("source already consumed or closed");
    }

    public final BaseStream sequential() {
        this.h.r = false;
        return this;
    }

    public final BaseStream parallel() {
        this.h.r = true;
        return this;
    }

    @Override // j$.util.stream.BaseStream, java.lang.AutoCloseable
    public final void close() {
        this.o = true;
        this.n = null;
        b bVar = this.h;
        Runnable runnable = bVar.q;
        if (runnable != null) {
            bVar.q = null;
            runnable.run();
        }
    }

    @Override // j$.util.stream.BaseStream
    public final BaseStream onClose(Runnable runnable) {
        if (this.o) {
            throw new IllegalStateException("stream has already been operated upon or closed");
        }
        Objects.requireNonNull(runnable);
        b bVar = this.h;
        Runnable runnable2 = bVar.q;
        if (runnable2 != null) {
            runnable = new i8(0, runnable2, runnable);
        }
        bVar.q = runnable;
        return this;
    }

    public Spliterator spliterator() {
        if (this.o) {
            throw new IllegalStateException("stream has already been operated upon or closed");
        }
        this.o = true;
        b bVar = this.h;
        if (this == bVar) {
            Spliterator spliterator = bVar.n;
            if (spliterator != null) {
                bVar.n = null;
                return spliterator;
            }
            throw new IllegalStateException("source already consumed or closed");
        }
        return G0(this, new a(0, this), bVar.r);
    }

    @Override // j$.util.stream.x3
    public final h2 d0(Spliterator spliterator, boolean z, IntFunction intFunction) {
        if (this.h.r) {
            return x0(this, spliterator, z, intFunction);
        }
        z1 z1VarP0 = p0(e0(spliterator), intFunction);
        s0(spliterator, z1VarP0);
        return z1VarP0.build();
    }

    @Override // j$.util.stream.BaseStream
    public final boolean isParallel() {
        return this.h.r;
    }

    public final Spliterator E0(int i) {
        int i2;
        int i3;
        b bVar = this.h;
        Spliterator spliteratorB0 = bVar.n;
        if (spliteratorB0 != null) {
            bVar.n = null;
            if (bVar.r && bVar.p) {
                b bVar2 = bVar.k;
                int i4 = 1;
                while (bVar != this) {
                    int i5 = bVar2.j;
                    if (bVar2.C0()) {
                        if (c7.SHORT_CIRCUIT.k(i5)) {
                            i5 &= ~c7.u;
                        }
                        spliteratorB0 = bVar2.B0(bVar, spliteratorB0);
                        if (spliteratorB0.hasCharacteristics(64)) {
                            i2 = (~c7.t) & i5;
                            i3 = c7.s;
                        } else {
                            i2 = (~c7.s) & i5;
                            i3 = c7.t;
                        }
                        i5 = i2 | i3;
                        i4 = 0;
                    }
                    int i6 = i4 + 1;
                    bVar2.l = i4;
                    bVar2.m = c7.h(i5, bVar.m);
                    b bVar3 = bVar2;
                    bVar2 = bVar2.k;
                    bVar = bVar3;
                    i4 = i6;
                }
            }
            if (i != 0) {
                this.m = c7.h(i, this.m);
            }
            return spliteratorB0;
        }
        throw new IllegalStateException("source already consumed or closed");
    }

    @Override // j$.util.stream.x3
    public final long e0(Spliterator spliterator) {
        if (c7.SIZED.k(this.m)) {
            return spliterator.getExactSizeIfKnown();
        }
        return -1L;
    }

    @Override // j$.util.stream.x3
    public final o5 s0(Spliterator spliterator, o5 o5Var) {
        Z(spliterator, t0((o5) Objects.requireNonNull(o5Var)));
        return o5Var;
    }

    @Override // j$.util.stream.x3
    public final void Z(Spliterator spliterator, o5 o5Var) {
        Objects.requireNonNull(o5Var);
        if (!c7.SHORT_CIRCUIT.k(this.m)) {
            o5Var.m(spliterator.getExactSizeIfKnown());
            spliterator.forEachRemaining(o5Var);
            o5Var.end();
            return;
        }
        a0(spliterator, o5Var);
    }

    @Override // j$.util.stream.x3
    public final boolean a0(Spliterator spliterator, o5 o5Var) {
        b bVar = this;
        while (bVar.l > 0) {
            bVar = bVar.i;
        }
        o5Var.m(spliterator.getExactSizeIfKnown());
        boolean zY0 = bVar.y0(spliterator, o5Var);
        o5Var.end();
        return zY0;
    }

    @Override // j$.util.stream.x3
    public final o5 t0(o5 o5Var) {
        Objects.requireNonNull(o5Var);
        for (b bVar = this; bVar.l > 0; bVar = bVar.i) {
            o5Var = bVar.D0(bVar.i.m, o5Var);
        }
        return o5Var;
    }

    @Override // j$.util.stream.x3
    public final Spliterator u0(Spliterator spliterator) {
        return this.l == 0 ? spliterator : G0(this, new a(1, spliterator), this.h.r);
    }

    public h2 A0(x3 x3Var, Spliterator spliterator, IntFunction intFunction) {
        throw new UnsupportedOperationException("Parallel evaluation is not supported");
    }

    public Spliterator B0(b bVar, Spliterator spliterator) {
        return A0(bVar, spliterator, new j$.time.format.a(9)).spliterator();
    }
}
