package p026j$.util;

import java.util.Arrays;
import kotlin.UByte;
import org.mvel2.asm.signature.SignatureVisitor;

/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public class Base64 {

    /* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
    public static class Decoder {

        /* JADX INFO: renamed from: a */
        public static final int[] f1012a;

        /* JADX INFO: renamed from: b */
        public static final int[] f1013b;

        /* JADX INFO: renamed from: c */
        public static final Decoder f1014c;

        static {
            int[] iArr = new int[256];
            f1012a = iArr;
            Arrays.fill(iArr, -1);
            for (int i = 0; i < 64; i++) {
                f1012a[Encoder.f1015a[i]] = i;
            }
            f1012a[61] = -2;
            int[] iArr2 = new int[256];
            f1013b = iArr2;
            Arrays.fill(iArr2, -1);
            for (int i2 = 0; i2 < 64; i2++) {
                f1013b[Encoder.f1016b[i2]] = i2;
            }
            f1013b[61] = -2;
            f1014c = new Decoder();
        }

        /* JADX WARN: Code restructure failed: missing block: B:45:0x00bf, code lost:
        
            if (r9 != 18) goto L56;
         */
        /* JADX WARN: Code restructure failed: missing block: B:56:0x0109, code lost:
        
            if (r9 != 6) goto L58;
         */
        /* JADX WARN: Code restructure failed: missing block: B:57:0x010b, code lost:
        
            r5[r10] = (byte) (r11 >> 16);
            r10 = r10 + 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:58:0x0116, code lost:
        
            if (r9 != 0) goto L60;
         */
        /* JADX WARN: Code restructure failed: missing block: B:59:0x0118, code lost:
        
            r0 = r10 + 1;
            r5[r10] = (byte) (r11 >> 16);
            r10 = r10 + 2;
            r5[r0] = (byte) (r11 >> 8);
         */
        /* JADX WARN: Code restructure failed: missing block: B:61:0x0129, code lost:
        
            if (r9 == 12) goto L69;
         */
        /* JADX WARN: Code restructure failed: missing block: B:62:0x012b, code lost:
        
            if (r8 < r6) goto L67;
         */
        /* JADX WARN: Code restructure failed: missing block: B:63:0x012d, code lost:
        
            if (r10 == r1) goto L66;
         */
        /* JADX WARN: Code restructure failed: missing block: B:65:0x0133, code lost:
        
            return java.util.Arrays.copyOf(r5, r10);
         */
        /* JADX WARN: Code restructure failed: missing block: B:66:0x0134, code lost:
        
            return r5;
         */
        /* JADX WARN: Code restructure failed: missing block: B:67:0x0135, code lost:
        
            p026j$.time.C2351g.m803h("Input byte array has incorrect ending byte at ", r8);
         */
        /* JADX WARN: Code restructure failed: missing block: B:68:0x013b, code lost:
        
            return null;
         */
        /* JADX WARN: Code restructure failed: missing block: B:70:0x0143, code lost:
        
            throw new java.lang.IllegalArgumentException("Last unit does not have enough valid bits");
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public byte[] decode(java.lang.String r20) {
            /*
                Method dump skipped, instruction units count: 332
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: j$.util.Base64.Decoder.decode(java.lang.String):byte[]");
        }
    }

    /* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
    public static class Encoder {

        /* JADX INFO: renamed from: a */
        public static final char[] f1015a = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', SignatureVisitor.EXTENDS, '/'};

        /* JADX INFO: renamed from: b */
        public static final char[] f1016b = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', SignatureVisitor.SUPER, '_'};

        /* JADX INFO: renamed from: c */
        public static final Encoder f1017c = new Encoder();

        public String encodeToString(byte[] bArr) {
            int length = ((bArr.length + 2) / 3) * 4;
            byte[] bArrCopyOf = new byte[length];
            int length2 = bArr.length;
            int i = (length2 / 3) * 3;
            int i2 = 0;
            int i3 = 0;
            while (true) {
                char[] cArr = f1015a;
                if (i2 >= i) {
                    if (i2 < length2) {
                        int i4 = i2 + 1;
                        int i5 = bArr[i2] & UByte.MAX_VALUE;
                        int i6 = i3 + 1;
                        bArrCopyOf[i3] = (byte) cArr[i5 >> 2];
                        if (i4 == length2) {
                            bArrCopyOf[i6] = (byte) cArr[(i5 << 4) & 63];
                            int i7 = i3 + 3;
                            bArrCopyOf[i3 + 2] = kotlin.p028io.encoding.Base64.padSymbol;
                            i3 += 4;
                            bArrCopyOf[i7] = kotlin.p028io.encoding.Base64.padSymbol;
                        } else {
                            int i8 = bArr[i4] & UByte.MAX_VALUE;
                            bArrCopyOf[i6] = (byte) cArr[((i5 << 4) & 63) | (i8 >> 4)];
                            int i9 = i3 + 3;
                            bArrCopyOf[i3 + 2] = (byte) cArr[(i8 << 2) & 63];
                            i3 += 4;
                            bArrCopyOf[i9] = kotlin.p028io.encoding.Base64.padSymbol;
                        }
                    }
                    if (i3 != length) {
                        bArrCopyOf = Arrays.copyOf(bArrCopyOf, i3);
                    }
                    return new String(bArrCopyOf, 0, 0, bArrCopyOf.length);
                }
                int iMin = Math.min(i2 + i, i);
                int i10 = i2;
                int i11 = i3;
                while (i10 < iMin) {
                    int i12 = i10 + 2;
                    int i13 = ((bArr[i10 + 1] & UByte.MAX_VALUE) << 8) | ((bArr[i10] & UByte.MAX_VALUE) << 16);
                    i10 += 3;
                    int i14 = i13 | (bArr[i12] & UByte.MAX_VALUE);
                    bArrCopyOf[i11] = (byte) cArr[(i14 >>> 18) & 63];
                    bArrCopyOf[i11 + 1] = (byte) cArr[(i14 >>> 12) & 63];
                    int i15 = i11 + 3;
                    bArrCopyOf[i11 + 2] = (byte) cArr[(i14 >>> 6) & 63];
                    i11 += 4;
                    bArrCopyOf[i15] = (byte) cArr[i14 & 63];
                }
                int i16 = ((iMin - i2) / 3) * 4;
                i3 += i16;
                if (i16 == -1 && iMin < length2) {
                    throw null;
                }
                i2 = iMin;
            }
        }
    }

    public static Decoder getDecoder() {
        return Decoder.f1014c;
    }

    public static Encoder getEncoder() {
        return Encoder.f1017c;
    }
}
