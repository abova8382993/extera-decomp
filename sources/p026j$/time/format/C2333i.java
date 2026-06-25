package p026j$.time.format;

import okhttp3.internal.url._UrlKt;
import org.mvel2.MVEL;
import p026j$.time.C2351g;
import p026j$.time.temporal.EnumC2365a;

/* JADX INFO: renamed from: j$.time.format.i */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2333i implements InterfaceC2329e {

    /* JADX INFO: renamed from: d */
    public static final String[] f852d = {"+HH", "+HHmm", "+HH:mm", "+HHMM", "+HH:MM", "+HHMMss", "+HH:MM:ss", "+HHMMSS", "+HH:MM:SS", "+HHmmss", "+HH:mm:ss", "+H", "+Hmm", "+H:mm", "+HMM", "+H:MM", "+HMMss", "+H:MM:ss", "+HMMSS", "+H:MM:SS", "+Hmmss", "+H:mm:ss"};

    /* JADX INFO: renamed from: e */
    public static final C2333i f853e = new C2333i("+HH:MM:ss", "Z");

    /* JADX INFO: renamed from: f */
    public static final C2333i f854f = new C2333i("+HH:MM:ss", MVEL.VERSION_SUB);

    /* JADX INFO: renamed from: a */
    public final String f855a;

    /* JADX INFO: renamed from: b */
    public final int f856b;

    /* JADX INFO: renamed from: c */
    public final int f857c;

    public C2333i(String str, String str2) {
        for (int i = 0; i < 22; i++) {
            if (f852d[i].equals(str)) {
                this.f856b = i;
                this.f857c = i % 11;
                this.f855a = str2;
                return;
            }
        }
        throw new IllegalArgumentException("Invalid zone offset pattern: ".concat(str));
    }

    /* JADX INFO: renamed from: a */
    public static void m753a(boolean z, int i, StringBuilder sb) {
        sb.append(z ? ":" : _UrlKt.FRAGMENT_ENCODE_SET);
        sb.append((char) ((i / 10) + 48));
        sb.append((char) ((i % 10) + 48));
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0026  */
    /* JADX INFO: renamed from: b */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean m754b(java.lang.CharSequence r5, boolean r6, int r7, int[] r8) {
        /*
            r0 = 0
            r1 = r8[r0]
            r2 = 1
            if (r1 >= 0) goto L7
            return r2
        L7:
            if (r6 == 0) goto L1d
            if (r7 == r2) goto L1d
            int r6 = r1 + 1
            int r3 = r5.length()
            if (r6 > r3) goto L4e
            char r1 = r5.charAt(r1)
            r3 = 58
            if (r1 == r3) goto L1c
            goto L4e
        L1c:
            r1 = r6
        L1d:
            int r6 = r1 + 2
            int r3 = r5.length()
            if (r6 <= r3) goto L26
            goto L4e
        L26:
            int r3 = r1 + 1
            char r1 = r5.charAt(r1)
            char r5 = r5.charAt(r3)
            r3 = 48
            if (r1 < r3) goto L4e
            r4 = 57
            if (r1 > r4) goto L4e
            if (r5 < r3) goto L4e
            if (r5 <= r4) goto L3d
            goto L4e
        L3d:
            int r1 = r1 - r3
            int r1 = r1 * 10
            int r5 = r5 - r3
            int r5 = r5 + r1
            if (r5 < 0) goto L4e
            r1 = 59
            if (r5 <= r1) goto L49
            goto L4e
        L49:
            r8[r7] = r5
            r8[r0] = r6
            return r2
        L4e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p026j$.time.format.C2333i.m754b(java.lang.CharSequence, boolean, int, int[]):boolean");
    }

    /* JADX INFO: renamed from: c */
    public static void m755c(CharSequence charSequence, boolean z, int[] iArr) {
        if (!z) {
            m757e(charSequence, 1, 2, iArr);
        } else {
            if (m754b(charSequence, false, 1, iArr)) {
                return;
            }
            iArr[0] = ~iArr[0];
        }
    }

    /* JADX INFO: renamed from: d */
    public static void m756d(CharSequence charSequence, boolean z, boolean z2, int[] iArr) {
        if (m754b(charSequence, z, 2, iArr) || !z2) {
            return;
        }
        iArr[0] = ~iArr[0];
    }

    /* JADX INFO: renamed from: e */
    public static void m757e(CharSequence charSequence, int i, int i2, int[] iArr) {
        int i3;
        char cCharAt;
        int i4 = iArr[0];
        char[] cArr = new char[i2];
        int i5 = 0;
        int i6 = 0;
        while (i5 < i2 && (i3 = i4 + 1) <= charSequence.length() && (cCharAt = charSequence.charAt(i4)) >= '0' && cCharAt <= '9') {
            cArr[i5] = cCharAt;
            i6++;
            i5++;
            i4 = i3;
        }
        if (i6 < i) {
            iArr[0] = ~iArr[0];
            return;
        }
        switch (i6) {
            case 1:
                iArr[1] = cArr[0] - '0';
                break;
            case 2:
                iArr[1] = (cArr[1] - '0') + ((cArr[0] - '0') * 10);
                break;
            case 3:
                iArr[1] = cArr[0] - '0';
                iArr[2] = (cArr[2] - '0') + ((cArr[1] - '0') * 10);
                break;
            case 4:
                iArr[1] = (cArr[1] - '0') + ((cArr[0] - '0') * 10);
                iArr[2] = (cArr[3] - '0') + ((cArr[2] - '0') * 10);
                break;
            case 5:
                iArr[1] = cArr[0] - '0';
                iArr[2] = (cArr[2] - '0') + ((cArr[1] - '0') * 10);
                iArr[3] = (cArr[4] - '0') + ((cArr[3] - '0') * 10);
                break;
            case 6:
                iArr[1] = (cArr[1] - '0') + ((cArr[0] - '0') * 10);
                iArr[2] = (cArr[3] - '0') + ((cArr[2] - '0') * 10);
                iArr[3] = (cArr[5] - '0') + ((cArr[4] - '0') * 10);
                break;
        }
        iArr[0] = i4;
    }

    @Override // p026j$.time.format.InterfaceC2329e
    /* JADX INFO: renamed from: B */
    public final int mo748B(C2342r c2342r, CharSequence charSequence, int i) {
        C2342r c2342r2;
        int i2;
        CharSequence charSequence2;
        int i3;
        int i4;
        int i5;
        int i6;
        int length = charSequence.length();
        int length2 = this.f855a.length();
        if (length2 != 0) {
            c2342r2 = c2342r;
            i2 = i;
            if (i2 == length) {
                return ~i2;
            }
            charSequence2 = charSequence;
            if (c2342r2.m783g(charSequence2, i2, this.f855a, 0, length2)) {
                return c2342r2.m782f(EnumC2365a.OFFSET_SECONDS, 0L, i2, i2 + length2);
            }
        } else {
            if (i == length) {
                return c2342r.m782f(EnumC2365a.OFFSET_SECONDS, 0L, i, i);
            }
            c2342r2 = c2342r;
            charSequence2 = charSequence;
            i2 = i;
        }
        char cCharAt = charSequence2.charAt(i2);
        if (cCharAt == '+' || cCharAt == '-') {
            int i7 = cCharAt == '-' ? -1 : 1;
            int i8 = this.f857c;
            boolean z = i8 > 0 && i8 % 2 == 0;
            int i9 = this.f856b;
            boolean z2 = i9 < 11;
            int[] iArr = new int[4];
            iArr[0] = i2 + 1;
            if (!c2342r2.f885c) {
                if (z2) {
                    if (z || (i9 == 0 && length > (i6 = i2 + 3) && charSequence2.charAt(i6) == ':')) {
                        i9 = 10;
                        z = true;
                    } else {
                        i9 = 9;
                    }
                } else if (z || (i9 == 11 && length > (i5 = i2 + 3) && (charSequence2.charAt(i2 + 2) == ':' || charSequence2.charAt(i5) == ':'))) {
                    i9 = 21;
                    z = true;
                } else {
                    i9 = 20;
                }
            }
            switch (i9) {
                case 0:
                case 11:
                    m755c(charSequence2, z2, iArr);
                    break;
                case 1:
                case 2:
                case 13:
                    m755c(charSequence2, z2, iArr);
                    m756d(charSequence2, z, false, iArr);
                    break;
                case 3:
                case 4:
                case 15:
                    m755c(charSequence2, z2, iArr);
                    m756d(charSequence2, z, true, iArr);
                    break;
                case 5:
                case 6:
                case 17:
                    m755c(charSequence2, z2, iArr);
                    m756d(charSequence2, z, true, iArr);
                    m754b(charSequence2, z, 3, iArr);
                    break;
                case 7:
                case 8:
                case 19:
                    m755c(charSequence2, z2, iArr);
                    m756d(charSequence2, z, true, iArr);
                    if (!m754b(charSequence2, z, 3, iArr)) {
                        iArr[0] = ~iArr[0];
                    }
                    break;
                case 9:
                case 10:
                case 21:
                    m755c(charSequence2, z2, iArr);
                    if (m754b(charSequence2, z, 2, iArr)) {
                        m754b(charSequence2, z, 3, iArr);
                    }
                    break;
                case 12:
                    m757e(charSequence2, 1, 4, iArr);
                    break;
                case 14:
                    m757e(charSequence2, 3, 4, iArr);
                    break;
                case 16:
                    m757e(charSequence2, 3, 6, iArr);
                    break;
                case 18:
                    m757e(charSequence2, 5, 6, iArr);
                    break;
                case 20:
                    m757e(charSequence2, 1, 6, iArr);
                    break;
            }
            int i10 = iArr[0];
            if (i10 > 0) {
                int i11 = iArr[1];
                if (i11 <= 23 && (i3 = iArr[2]) <= 59 && (i4 = iArr[3]) <= 59) {
                    return c2342r2.m782f(EnumC2365a.OFFSET_SECONDS, ((long) i7) * ((((long) i3) * 60) + (((long) i11) * 3600) + ((long) i4)), i2, i10);
                }
                C2351g.m796a("Value out of range: Hour[0-23], Minute[0-59], Second[0-59]");
                return 0;
            }
        }
        return length2 == 0 ? c2342r2.m782f(EnumC2365a.OFFSET_SECONDS, 0L, i2, i2) : ~i2;
    }

    @Override // p026j$.time.format.InterfaceC2329e
    /* JADX INFO: renamed from: t */
    public final boolean mo749t(C2344t c2344t, StringBuilder sb) {
        Long lM784a = c2344t.m784a(EnumC2365a.OFFSET_SECONDS);
        boolean z = false;
        if (lM784a == null) {
            return false;
        }
        int intExact = Math.toIntExact(lM784a.longValue());
        String str = this.f855a;
        if (intExact == 0) {
            sb.append(str);
            return true;
        }
        int iAbs = Math.abs((intExact / 3600) % 100);
        int iAbs2 = Math.abs((intExact / 60) % 60);
        int iAbs3 = Math.abs(intExact % 60);
        int length = sb.length();
        sb.append(intExact < 0 ? "-" : "+");
        if (this.f856b >= 11 && iAbs < 10) {
            sb.append((char) (iAbs + 48));
        } else {
            m753a(false, iAbs, sb);
        }
        int i = this.f857c;
        if ((i >= 3 && i <= 8) || ((i >= 9 && iAbs3 > 0) || (i >= 1 && iAbs2 > 0))) {
            m753a(i > 0 && i % 2 == 0, iAbs2, sb);
            iAbs += iAbs2;
            if (i == 7 || i == 8 || (i >= 5 && iAbs3 > 0)) {
                if (i > 0 && i % 2 == 0) {
                    z = true;
                }
                m753a(z, iAbs3, sb);
                iAbs += iAbs3;
            }
        }
        if (iAbs == 0) {
            sb.setLength(length);
            sb.append(str);
        }
        return true;
    }

    public final String toString() {
        String strReplace = this.f855a.replace("'", "''");
        return "Offset(" + f852d[this.f856b] + ",'" + strReplace + "')";
    }
}
