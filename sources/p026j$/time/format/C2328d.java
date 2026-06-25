package p026j$.time.format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: renamed from: j$.time.format.d */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2328d implements InterfaceC2329e {

    /* JADX INFO: renamed from: a */
    public final InterfaceC2329e[] f843a;

    /* JADX INFO: renamed from: b */
    public final boolean f844b;

    /* JADX WARN: Illegal instructions before constructor call */
    public C2328d(List list, boolean z) {
        ArrayList arrayList = (ArrayList) list;
        this((InterfaceC2329e[]) arrayList.toArray(new InterfaceC2329e[arrayList.size()]), z);
    }

    @Override // p026j$.time.format.InterfaceC2329e
    /* JADX INFO: renamed from: B */
    public final int mo748B(C2342r c2342r, CharSequence charSequence, int i) {
        boolean z = this.f844b;
        InterfaceC2329e[] interfaceC2329eArr = this.f843a;
        int i2 = 0;
        if (!z) {
            int length = interfaceC2329eArr.length;
            while (i2 < length) {
                i = interfaceC2329eArr[i2].mo748B(c2342r, charSequence, i);
                if (i < 0) {
                    return i;
                }
                i2++;
            }
            return i;
        }
        ArrayList arrayList = c2342r.f886d;
        C2349y c2349yM779c = c2342r.m779c();
        c2349yM779c.getClass();
        C2349y c2349y = new C2349y();
        ((HashMap) c2349y.f900a).putAll(c2349yM779c.f900a);
        c2349y.f901b = c2349yM779c.f901b;
        c2349y.f902c = c2349yM779c.f902c;
        c2349y.f903d = c2349yM779c.f903d;
        arrayList.add(c2349y);
        int length2 = interfaceC2329eArr.length;
        int iMo748B = i;
        while (i2 < length2) {
            iMo748B = interfaceC2329eArr[i2].mo748B(c2342r, charSequence, iMo748B);
            if (iMo748B < 0) {
                c2342r.f886d.remove(r6.size() - 1);
                return i;
            }
            i2++;
        }
        c2342r.f886d.remove(r6.size() - 2);
        return iMo748B;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0026, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x002c, code lost:
    
        if (r2 != false) goto L11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x002f, code lost:
    
        return true;
     */
    @Override // p026j$.time.format.InterfaceC2329e
    /* JADX INFO: renamed from: t */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean mo749t(p026j$.time.format.C2344t r7, java.lang.StringBuilder r8) {
        /*
            r6 = this;
            int r0 = r8.length()
            r1 = 1
            boolean r2 = r6.f844b
            if (r2 == 0) goto Le
            int r3 = r7.f893c
            int r3 = r3 + r1
            r7.f893c = r3
        Le:
            j$.time.format.e[] r6 = r6.f843a     // Catch: java.lang.Throwable -> L27
            int r3 = r6.length     // Catch: java.lang.Throwable -> L27
            r4 = 0
        L12:
            if (r4 >= r3) goto L2c
            r5 = r6[r4]     // Catch: java.lang.Throwable -> L27
            boolean r5 = r5.mo749t(r7, r8)     // Catch: java.lang.Throwable -> L27
            if (r5 != 0) goto L29
            r8.setLength(r0)     // Catch: java.lang.Throwable -> L27
            if (r2 == 0) goto L2f
        L21:
            int r6 = r7.f893c
            int r6 = r6 - r1
            r7.f893c = r6
            return r1
        L27:
            r6 = move-exception
            goto L30
        L29:
            int r4 = r4 + 1
            goto L12
        L2c:
            if (r2 == 0) goto L2f
            goto L21
        L2f:
            return r1
        L30:
            if (r2 == 0) goto L37
            int r8 = r7.f893c
            int r8 = r8 - r1
            r7.f893c = r8
        L37:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: p026j$.time.format.C2328d.mo749t(j$.time.format.t, java.lang.StringBuilder):boolean");
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        InterfaceC2329e[] interfaceC2329eArr = this.f843a;
        if (interfaceC2329eArr != null) {
            boolean z = this.f844b;
            sb.append(z ? "[" : "(");
            for (InterfaceC2329e interfaceC2329e : interfaceC2329eArr) {
                sb.append(interfaceC2329e);
            }
            sb.append(z ? "]" : ")");
        }
        return sb.toString();
    }

    public C2328d(InterfaceC2329e[] interfaceC2329eArr, boolean z) {
        this.f843a = interfaceC2329eArr;
        this.f844b = z;
    }
}
