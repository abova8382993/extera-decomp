package p026j$.time.format;

/* JADX INFO: renamed from: j$.time.format.j */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2334j extends C2335k {
    @Override // p026j$.time.format.C2335k
    /* JADX INFO: renamed from: b */
    public final boolean mo758b(char c2, char c3) {
        return C2342r.m777b(c2, c3);
    }

    @Override // p026j$.time.format.C2335k
    /* JADX INFO: renamed from: d */
    public final C2335k mo759d(String str, String str2, C2335k c2335k) {
        return new C2334j(str, str2, c2335k);
    }

    @Override // p026j$.time.format.C2335k
    /* JADX INFO: renamed from: e */
    public final boolean mo760e(CharSequence charSequence, int i, int i2) {
        int length = this.f858a.length();
        if (length > i2 - i) {
            return false;
        }
        int i3 = 0;
        while (true) {
            int i4 = length - 1;
            if (length <= 0) {
                return true;
            }
            int i5 = i3 + 1;
            int i6 = i + 1;
            if (!C2342r.m777b(this.f858a.charAt(i3), charSequence.charAt(i))) {
                return false;
            }
            i = i6;
            length = i4;
            i3 = i5;
        }
    }
}
