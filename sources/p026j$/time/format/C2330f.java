package p026j$.time.format;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Objects;
import okhttp3.internal.url._UrlKt;
import p026j$.time.AbstractC2320d;
import p026j$.time.temporal.C2384t;
import p026j$.time.temporal.InterfaceC2380p;

/* JADX INFO: renamed from: j$.time.format.f */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2330f extends C2332h {

    /* JADX INFO: renamed from: g */
    public final boolean f845g;

    public C2330f(InterfaceC2380p interfaceC2380p) {
        this(interfaceC2380p, 0, 9, true, 0);
        Objects.requireNonNull(interfaceC2380p, "field");
        C2384t c2384tMo836I = interfaceC2380p.mo836I();
        if (c2384tMo836I.f965a != c2384tMo836I.f966b || c2384tMo836I.f967c != c2384tMo836I.f968d) {
            throw new IllegalArgumentException(AbstractC2320d.m741a("Field must have a fixed set of values: ", interfaceC2380p));
        }
    }

    @Override // p026j$.time.format.C2332h, p026j$.time.format.InterfaceC2329e
    /* JADX INFO: renamed from: B */
    public final int mo748B(C2342r c2342r, CharSequence charSequence, int i) {
        boolean z = c2342r.f885c;
        DateTimeFormatter dateTimeFormatter = c2342r.f883a;
        int i2 = (z || mo750a(c2342r)) ? this.f848b : 0;
        int i3 = (c2342r.f885c || mo750a(c2342r)) ? this.f849c : 9;
        int length = charSequence.length();
        if (i != length) {
            if (this.f845g) {
                char cCharAt = charSequence.charAt(i);
                dateTimeFormatter.f827c.getClass();
                if (cCharAt == '.') {
                    i++;
                } else if (i2 > 0) {
                    return ~i;
                }
            }
            int i4 = i;
            int i5 = i2 + i4;
            if (i5 > length) {
                return ~i4;
            }
            int iMin = Math.min(i3 + i4, length);
            int i6 = 0;
            int i7 = i4;
            while (true) {
                if (i7 >= iMin) {
                    break;
                }
                int i8 = i7 + 1;
                char cCharAt2 = charSequence.charAt(i7);
                dateTimeFormatter.f827c.getClass();
                int i9 = cCharAt2 - '0';
                if (i9 < 0 || i9 > 9) {
                    i9 = -1;
                }
                if (i9 >= 0) {
                    i6 = (i6 * 10) + i9;
                    i7 = i8;
                } else if (i8 < i5) {
                    return ~i4;
                }
            }
            BigDecimal bigDecimalMovePointLeft = new BigDecimal(i6).movePointLeft(i7 - i4);
            C2384t c2384tMo836I = this.f847a.mo836I();
            BigDecimal bigDecimalValueOf = BigDecimal.valueOf(c2384tMo836I.f965a);
            return c2342r.m782f(this.f847a, bigDecimalMovePointLeft.multiply(BigDecimal.valueOf(c2384tMo836I.f968d).subtract(bigDecimalValueOf).add(BigDecimal.ONE)).setScale(0, RoundingMode.FLOOR).add(bigDecimalValueOf).longValueExact(), i4, i7);
        }
        if (i2 > 0) {
            return ~i;
        }
        return i;
    }

    @Override // p026j$.time.format.C2332h
    /* JADX INFO: renamed from: a */
    public final boolean mo750a(C2342r c2342r) {
        return c2342r.f885c && this.f848b == this.f849c && !this.f845g;
    }

    @Override // p026j$.time.format.C2332h
    /* JADX INFO: renamed from: b */
    public final C2332h mo751b() {
        if (this.f851e == -1) {
            return this;
        }
        return new C2330f(this.f847a, this.f848b, this.f849c, this.f845g, -1);
    }

    @Override // p026j$.time.format.C2332h
    /* JADX INFO: renamed from: c */
    public final C2332h mo752c(int i) {
        return new C2330f(this.f847a, this.f848b, this.f849c, this.f845g, this.f851e + i);
    }

    @Override // p026j$.time.format.C2332h, p026j$.time.format.InterfaceC2329e
    /* JADX INFO: renamed from: t */
    public final boolean mo749t(C2344t c2344t, StringBuilder sb) {
        InterfaceC2380p interfaceC2380p = this.f847a;
        Long lM784a = c2344t.m784a(interfaceC2380p);
        if (lM784a == null) {
            return false;
        }
        C2348x c2348x = c2344t.f892b.f827c;
        long jLongValue = lM784a.longValue();
        C2384t c2384tMo836I = interfaceC2380p.mo836I();
        c2384tMo836I.m850b(jLongValue, interfaceC2380p);
        BigDecimal bigDecimalValueOf = BigDecimal.valueOf(c2384tMo836I.f965a);
        BigDecimal bigDecimalAdd = BigDecimal.valueOf(c2384tMo836I.f968d).subtract(bigDecimalValueOf).add(BigDecimal.ONE);
        BigDecimal bigDecimalSubtract = BigDecimal.valueOf(jLongValue).subtract(bigDecimalValueOf);
        RoundingMode roundingMode = RoundingMode.FLOOR;
        BigDecimal bigDecimalDivide = bigDecimalSubtract.divide(bigDecimalAdd, 9, roundingMode);
        BigDecimal bigDecimal = BigDecimal.ZERO;
        if (bigDecimalDivide.compareTo(bigDecimal) != 0) {
            bigDecimal = bigDecimalDivide.signum() == 0 ? new BigDecimal(BigInteger.ZERO, 0) : bigDecimalDivide.stripTrailingZeros();
        }
        int iScale = bigDecimal.scale();
        boolean z = this.f845g;
        int i = this.f848b;
        if (iScale != 0) {
            String strSubstring = bigDecimal.setScale(Math.min(Math.max(bigDecimal.scale(), i), this.f849c), roundingMode).toPlainString().substring(2);
            c2348x.getClass();
            if (z) {
                sb.append('.');
            }
            sb.append(strSubstring);
            return true;
        }
        if (i > 0) {
            if (z) {
                c2348x.getClass();
                sb.append('.');
            }
            for (int i2 = 0; i2 < i; i2++) {
                c2348x.getClass();
                sb.append('0');
            }
        }
        return true;
    }

    @Override // p026j$.time.format.C2332h
    public final String toString() {
        return "Fraction(" + this.f847a + "," + this.f848b + "," + this.f849c + (this.f845g ? ",DecimalPoint" : _UrlKt.FRAGMENT_ENCODE_SET) + ")";
    }

    public C2330f(InterfaceC2380p interfaceC2380p, int i, int i2, boolean z, int i3) {
        super(interfaceC2380p, i, i2, EnumC2324a0.NOT_NEGATIVE, i3);
        this.f845g = z;
    }
}
