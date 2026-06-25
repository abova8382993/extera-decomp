package p026j$.time.format;

/* JADX INFO: renamed from: j$.time.format.c */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2327c implements InterfaceC2329e {

    /* JADX INFO: renamed from: a */
    public final char f842a;

    public C2327c(char c2) {
        this.f842a = c2;
    }

    @Override // p026j$.time.format.InterfaceC2329e
    /* JADX INFO: renamed from: B */
    public final int mo748B(C2342r c2342r, CharSequence charSequence, int i) {
        if (i == charSequence.length()) {
            return ~i;
        }
        char cCharAt = charSequence.charAt(i);
        char c2 = this.f842a;
        return (cCharAt == c2 || (!c2342r.f884b && (Character.toUpperCase(cCharAt) == Character.toUpperCase(c2) || Character.toLowerCase(cCharAt) == Character.toLowerCase(c2)))) ? i + 1 : ~i;
    }

    @Override // p026j$.time.format.InterfaceC2329e
    /* JADX INFO: renamed from: t */
    public final boolean mo749t(C2344t c2344t, StringBuilder sb) {
        sb.append(this.f842a);
        return true;
    }

    public final String toString() {
        char c2 = this.f842a;
        if (c2 == '\'') {
            return "''";
        }
        return "'" + c2 + "'";
    }
}
