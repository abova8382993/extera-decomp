package j$.util.stream;

/* JADX INFO: loaded from: classes2.dex */
public abstract class f5 extends g5 {
    @Override // j$.util.stream.b
    public final boolean C0() {
        return false;
    }

    @Override // j$.util.stream.BaseStream
    public final BaseStream unordered() {
        return !c7.ORDERED.k(this.m) ? this : new b5(this, c7.r);
    }
}
