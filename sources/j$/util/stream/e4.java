package j$.util.stream;

import j$.util.Spliterator;

/* JADX INFO: loaded from: classes2.dex */
public final class e4 extends x3 {
    public final /* synthetic */ int h;

    public /* synthetic */ e4(int i) {
        this.h = i;
    }

    @Override // j$.util.stream.x3
    public final s4 r0() {
        switch (this.h) {
            case 0:
                return new w4();
            case 1:
                return new u4();
            case 2:
                return new x4();
            default:
                return new v4();
        }
    }

    @Override // j$.util.stream.x3, j$.util.stream.l8
    public final Object f(b bVar, Spliterator spliterator) {
        switch (this.h) {
            case 0:
                if (!c7.SIZED.k(bVar.m)) {
                }
                break;
            case 1:
                if (!c7.SIZED.k(bVar.m)) {
                }
                break;
            case 2:
                if (!c7.SIZED.k(bVar.m)) {
                }
                break;
            default:
                if (!c7.SIZED.k(bVar.m)) {
                }
                break;
        }
        return (Long) super.f(bVar, spliterator);
    }

    @Override // j$.util.stream.x3, j$.util.stream.l8
    public final Object i(x3 x3Var, Spliterator spliterator) {
        switch (this.h) {
            case 0:
                if (!c7.SIZED.k(((b) x3Var).m)) {
                }
                break;
            case 1:
                if (!c7.SIZED.k(((b) x3Var).m)) {
                }
                break;
            case 2:
                if (!c7.SIZED.k(((b) x3Var).m)) {
                }
                break;
            default:
                if (!c7.SIZED.k(((b) x3Var).m)) {
                }
                break;
        }
        return (Long) super.i(x3Var, spliterator);
    }

    @Override // j$.util.stream.x3, j$.util.stream.l8
    public final int u() {
        switch (this.h) {
        }
        return c7.r;
    }
}
