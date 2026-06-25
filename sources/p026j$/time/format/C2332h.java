package p026j$.time.format;

import java.math.BigInteger;
import okhttp3.internal.connection.RealConnection;
import org.mvel2.asm.signature.SignatureVisitor;
import p026j$.time.C2284c;
import p026j$.time.temporal.InterfaceC2380p;

/* JADX INFO: renamed from: j$.time.format.h */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public class C2332h implements InterfaceC2329e {

    /* JADX INFO: renamed from: f */
    public static final long[] f846f = {0, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000, RealConnection.IDLE_CONNECTION_HEALTHY_NS};

    /* JADX INFO: renamed from: a */
    public final InterfaceC2380p f847a;

    /* JADX INFO: renamed from: b */
    public final int f848b;

    /* JADX INFO: renamed from: c */
    public final int f849c;

    /* JADX INFO: renamed from: d */
    public final EnumC2324a0 f850d;

    /* JADX INFO: renamed from: e */
    public final int f851e;

    public C2332h(InterfaceC2380p interfaceC2380p, int i, int i2, EnumC2324a0 enumC2324a0) {
        this.f847a = interfaceC2380p;
        this.f848b = i;
        this.f849c = i2;
        this.f850d = enumC2324a0;
        this.f851e = 0;
    }

    @Override // p026j$.time.format.InterfaceC2329e
    /* JADX INFO: renamed from: B */
    public int mo748B(C2342r c2342r, CharSequence charSequence, int i) {
        int i2;
        boolean z;
        boolean z2;
        BigInteger bigIntegerAdd;
        boolean z3;
        boolean z4;
        int i3;
        long j;
        long j2;
        int i4;
        int i5;
        DateTimeFormatter dateTimeFormatter;
        boolean z5;
        int length = charSequence.length();
        if (i == length) {
            return ~i;
        }
        char cCharAt = charSequence.charAt(i);
        DateTimeFormatter dateTimeFormatter2 = c2342r.f883a;
        dateTimeFormatter2.f827c.getClass();
        int i6 = this.f849c;
        EnumC2324a0 enumC2324a0 = this.f850d;
        int i7 = this.f848b;
        int i8 = 0;
        boolean z6 = true;
        if (cCharAt == '+') {
            boolean z7 = c2342r.f885c;
            boolean z8 = i7 == i6;
            int iOrdinal = enumC2324a0.ordinal();
            if (iOrdinal == 0 ? z7 : !(iOrdinal == 1 || iOrdinal == 4 || (!z7 && !z8))) {
                return ~i;
            }
            i2 = i + 1;
            z = false;
            z2 = true;
        } else {
            dateTimeFormatter2.f827c.getClass();
            if (cCharAt == '-') {
                boolean z9 = c2342r.f885c;
                boolean z10 = i7 == i6;
                int iOrdinal2 = enumC2324a0.ordinal();
                if (iOrdinal2 != 0 && iOrdinal2 != 1 && iOrdinal2 != 4 && (z9 || z10)) {
                    return ~i;
                }
                i2 = i + 1;
                z2 = false;
                z = true;
            } else {
                if (enumC2324a0 == EnumC2324a0.ALWAYS && c2342r.f885c) {
                    return ~i;
                }
                i2 = i;
                z = false;
                z2 = false;
            }
        }
        int i9 = (c2342r.f885c || mo750a(c2342r)) ? i7 : 1;
        int i10 = i2 + i9;
        if (i10 > length) {
            return ~i2;
        }
        if (!c2342r.f885c && !mo750a(c2342r)) {
            i6 = 9;
        }
        int i11 = this.f851e;
        int iMax = Math.max(i11, 0) + i6;
        while (true) {
            bigIntegerAdd = null;
            if (i8 >= 2) {
                z3 = z;
                z4 = z2;
                i3 = i2;
                j = 0;
                break;
            }
            int iMin = Math.min(i2 + iMax, length);
            boolean z11 = z6;
            j2 = 0;
            i4 = i2;
            while (true) {
                if (i4 >= iMin) {
                    z3 = z;
                    i5 = length;
                    break;
                }
                int i12 = i4 + 1;
                char cCharAt2 = charSequence.charAt(i4);
                z3 = z;
                dateTimeFormatter2.f827c.getClass();
                int i13 = cCharAt2 - '0';
                i5 = length;
                if (i13 < 0 || i13 > 9) {
                    i13 = -1;
                }
                if (i13 >= 0) {
                    if (i12 - i2 > 18) {
                        if (bigIntegerAdd == null) {
                            bigIntegerAdd = BigInteger.valueOf(j2);
                        }
                        dateTimeFormatter = dateTimeFormatter2;
                        z5 = z2;
                        bigIntegerAdd = bigIntegerAdd.multiply(BigInteger.TEN).add(BigInteger.valueOf(i13));
                    } else {
                        dateTimeFormatter = dateTimeFormatter2;
                        z5 = z2;
                        j2 = (j2 * 10) + ((long) i13);
                    }
                    i4 = i12;
                    length = i5;
                    z = z3;
                    dateTimeFormatter2 = dateTimeFormatter;
                    z2 = z5;
                } else if (i4 < i10) {
                    return ~i2;
                }
            }
            DateTimeFormatter dateTimeFormatter3 = dateTimeFormatter2;
            z4 = z2;
            if (i11 <= 0 || i8 != 0) {
                break;
            }
            int iMax2 = Math.max(i9, (i4 - i2) - i11);
            i8++;
            z6 = z11;
            length = i5;
            dateTimeFormatter2 = dateTimeFormatter3;
            z2 = z4;
            iMax = iMax2;
            z = z3;
        }
        i3 = i4;
        j = j2;
        BigInteger bigIntegerDivide = bigIntegerAdd;
        if (z3) {
            if (bigIntegerDivide != null) {
                if (bigIntegerDivide.equals(BigInteger.ZERO) && c2342r.f885c) {
                    return ~(i2 - 1);
                }
                bigIntegerDivide = bigIntegerDivide.negate();
            } else {
                if (j == 0 && c2342r.f885c) {
                    return ~(i2 - 1);
                }
                j = -j;
            }
        } else if (enumC2324a0 == EnumC2324a0.EXCEEDS_PAD && c2342r.f885c) {
            int i14 = i3 - i2;
            if (z4) {
                if (i14 <= i7) {
                    return ~(i2 - 1);
                }
            } else if (i14 > i7) {
                return ~i2;
            }
        }
        if (bigIntegerDivide == null) {
            return c2342r.m782f(this.f847a, j, i2, i3);
        }
        if (bigIntegerDivide.bitLength() > 63) {
            bigIntegerDivide = bigIntegerDivide.divide(BigInteger.TEN);
            i3--;
        }
        return c2342r.m782f(this.f847a, bigIntegerDivide.longValue(), i2, i3);
    }

    /* JADX INFO: renamed from: a */
    public boolean mo750a(C2342r c2342r) {
        int i = this.f851e;
        if (i != -1) {
            return i > 0 && this.f848b == this.f849c && this.f850d == EnumC2324a0.NOT_NEGATIVE;
        }
        return true;
    }

    /* JADX INFO: renamed from: b */
    public C2332h mo751b() {
        if (this.f851e == -1) {
            return this;
        }
        return new C2332h(this.f847a, this.f848b, this.f849c, this.f850d, -1);
    }

    /* JADX INFO: renamed from: c */
    public C2332h mo752c(int i) {
        return new C2332h(this.f847a, this.f848b, this.f849c, this.f850d, this.f851e + i);
    }

    @Override // p026j$.time.format.InterfaceC2329e
    /* JADX INFO: renamed from: t */
    public boolean mo749t(C2344t c2344t, StringBuilder sb) {
        InterfaceC2380p interfaceC2380p = this.f847a;
        Long lM784a = c2344t.m784a(interfaceC2380p);
        if (lM784a == null) {
            return false;
        }
        long jLongValue = lM784a.longValue();
        C2348x c2348x = c2344t.f892b.f827c;
        String string = jLongValue == Long.MIN_VALUE ? "9223372036854775808" : Long.toString(Math.abs(jLongValue));
        int length = string.length();
        int i = this.f849c;
        if (length > i) {
            throw new C2284c("Field " + interfaceC2380p + " cannot be printed as the value " + jLongValue + " exceeds the maximum print width of " + i);
        }
        c2348x.getClass();
        int i2 = this.f848b;
        EnumC2324a0 enumC2324a0 = this.f850d;
        if (jLongValue >= 0) {
            int i3 = AbstractC2325b.f834a[enumC2324a0.ordinal()];
            if (i3 != 1) {
                if (i3 == 2) {
                    sb.append(SignatureVisitor.EXTENDS);
                }
            } else if (i2 < 19 && jLongValue >= f846f[i2]) {
                sb.append(SignatureVisitor.EXTENDS);
            }
        } else {
            int i4 = AbstractC2325b.f834a[enumC2324a0.ordinal()];
            if (i4 == 1 || i4 == 2 || i4 == 3) {
                sb.append(SignatureVisitor.SUPER);
            } else if (i4 == 4) {
                throw new C2284c("Field " + interfaceC2380p + " cannot be printed as the value " + jLongValue + " cannot be negative according to the SignStyle");
            }
        }
        for (int i5 = 0; i5 < i2 - string.length(); i5++) {
            sb.append('0');
        }
        sb.append(string);
        return true;
    }

    public String toString() {
        int i = this.f849c;
        InterfaceC2380p interfaceC2380p = this.f847a;
        EnumC2324a0 enumC2324a0 = this.f850d;
        int i2 = this.f848b;
        if (i2 == 1 && i == 19 && enumC2324a0 == EnumC2324a0.NORMAL) {
            return "Value(" + interfaceC2380p + ")";
        }
        if (i2 == i && enumC2324a0 == EnumC2324a0.NOT_NEGATIVE) {
            return "Value(" + interfaceC2380p + "," + i2 + ")";
        }
        return "Value(" + interfaceC2380p + "," + i2 + "," + i + "," + enumC2324a0 + ")";
    }

    public C2332h(InterfaceC2380p interfaceC2380p, int i, int i2, EnumC2324a0 enumC2324a0, int i3) {
        this.f847a = interfaceC2380p;
        this.f848b = i;
        this.f849c = i2;
        this.f850d = enumC2324a0;
        this.f851e = i3;
    }
}
