package p026j$.time.format;

/* JADX INFO: renamed from: j$.time.format.m */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2337m implements InterfaceC2329e {

    /* JADX INFO: renamed from: a */
    public final String f864a;

    public C2337m(String str) {
        this.f864a = str;
    }

    @Override // p026j$.time.format.InterfaceC2329e
    /* JADX INFO: renamed from: B */
    public final int mo748B(C2342r c2342r, CharSequence charSequence, int i) {
        if (i > charSequence.length() || i < 0) {
            throw new IndexOutOfBoundsException();
        }
        String str = this.f864a;
        return !c2342r.m783g(charSequence, i, str, 0, str.length()) ? ~i : str.length() + i;
    }

    @Override // p026j$.time.format.InterfaceC2329e
    /* JADX INFO: renamed from: t */
    public final boolean mo749t(C2344t c2344t, StringBuilder sb) {
        sb.append(this.f864a);
        return true;
    }

    public final String toString() {
        return "'" + this.f864a.replace("'", "''") + "'";
    }
}
