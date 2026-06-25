package okio.internal;

import com.android.p006dx.rop.code.RegisterSpec;
import java.io.EOFException;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.LongCompanionObject;
import kotlin.jvm.internal.SourceDebugExtension;
import okhttp3.internal.url._UrlKt;
import okio.Buffer;
import okio.Buffer$$ExternalSyntheticBUOutline3;
import okio.ByteString;
import okio.Options;
import okio.Segment;
import okio.Segment$$ExternalSyntheticBUOutline1;
import okio.SegmentedByteString;
import okio._JvmPlatformKt;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: renamed from: okio.internal.-Buffer */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0016\n\u0002\b\u0003\u001a7\u0010\t\u001a\u00020\b2\u0006\u0010\u0001\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0000¢\u0006\u0004\b\t\u0010\n\u001a\u001b\u0010\u000f\u001a\u00020\u000e*\u00020\u000b2\u0006\u0010\r\u001a\u00020\fH\u0000¢\u0006\u0004\b\u000f\u0010\u0010\u001a%\u0010\u0014\u001a\u00020\u0002*\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00112\b\b\u0002\u0010\u0013\u001a\u00020\bH\u0000¢\u0006\u0004\b\u0014\u0010\u0015\u001a\u0017\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0016\u001a\u00020\fH\u0002¢\u0006\u0004\b\u0017\u0010\u0018\u001aA\u0010\u001d\u001a\u00020\f*\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\f2\b\b\u0002\u0010\u001b\u001a\u00020\f2\b\b\u0002\u0010\u0006\u001a\u00020\u00022\b\b\u0002\u0010\u001c\u001a\u00020\u0002H\u0000¢\u0006\u0004\b\u001d\u0010\u001e\u001a\u001b\u0010!\u001a\u00020\u001f*\u00020\u000b2\u0006\u0010 \u001a\u00020\u001fH\u0000¢\u0006\u0004\b!\u0010\"\"\u001a\u0010#\u001a\u00020\u00048\u0000X\u0080\u0004¢\u0006\f\n\u0004\b#\u0010$\u001a\u0004\b%\u0010&\"\u0014\u0010(\u001a\u00020'8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b(\u0010)¨\u0006*"}, m877d2 = {"Lokio/Segment;", "segment", _UrlKt.FRAGMENT_ENCODE_SET, "segmentPos", _UrlKt.FRAGMENT_ENCODE_SET, "bytes", "bytesOffset", "bytesLimit", _UrlKt.FRAGMENT_ENCODE_SET, "rangeEquals", "(Lokio/Segment;I[BII)Z", "Lokio/Buffer;", _UrlKt.FRAGMENT_ENCODE_SET, "newline", _UrlKt.FRAGMENT_ENCODE_SET, "readUtf8Line", "(Lokio/Buffer;J)Ljava/lang/String;", "Lokio/Options;", "options", "selectTruncated", "selectPrefix", "(Lokio/Buffer;Lokio/Options;Z)I", RegisterSpec.PREFIX, "countDigitsIn", "(J)I", "Lokio/ByteString;", "fromIndex", "toIndex", "byteCount", "commonIndexOf", "(Lokio/Buffer;Lokio/ByteString;JJII)J", "Lokio/Buffer$UnsafeCursor;", "unsafeCursor", "commonReadAndWriteUnsafe", "(Lokio/Buffer;Lokio/Buffer$UnsafeCursor;)Lokio/Buffer$UnsafeCursor;", "HEX_DIGIT_BYTES", "[B", "getHEX_DIGIT_BYTES", "()[B", _UrlKt.FRAGMENT_ENCODE_SET, "DigitCountToLargestValue", "[J", "okio"}, m878k = 2, m879mv = {2, 2, 0}, m881xi = 48)
@JvmName(name = "-Buffer")
@SourceDebugExtension({"SMAP\nBuffer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Buffer.kt\nokio/internal/-Buffer\n+ 2 Util.kt\nokio/-SegmentedByteString\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,1712:1\n110#1,20:1735\n110#1,20:1768\n110#1:1788\n112#1,18:1790\n110#1,20:1808\n73#2:1713\n73#2:1714\n73#2:1715\n73#2:1716\n73#2:1717\n73#2:1718\n73#2:1719\n73#2:1720\n73#2:1721\n73#2:1722\n73#2:1723\n73#2:1724\n82#2:1725\n82#2:1726\n76#2:1727\n76#2:1728\n76#2:1729\n76#2:1730\n76#2:1731\n76#2:1732\n76#2:1733\n76#2:1734\n85#2:1755\n88#2:1757\n73#2:1758\n73#2:1759\n73#2:1760\n73#2:1761\n73#2:1762\n73#2:1763\n73#2:1764\n73#2:1765\n73#2:1766\n73#2:1767\n88#2:1789\n85#2:1828\n1#3:1756\n*S KotlinDebug\n*F\n+ 1 Buffer.kt\nokio/internal/-Buffer\n*L\n413#1:1735,20\n1262#1:1768,20\n1305#1:1788\n1305#1:1790,18\n1341#1:1808,20\n176#1:1713\n200#1:1714\n319#1:1715\n324#1:1716\n347#1:1717\n348#1:1718\n349#1:1719\n350#1:1720\n356#1:1721\n357#1:1722\n358#1:1723\n359#1:1724\n383#1:1725\n384#1:1726\n390#1:1727\n391#1:1728\n392#1:1729\n393#1:1730\n394#1:1731\n395#1:1732\n396#1:1733\n397#1:1734\n425#1:1755\n858#1:1757\n876#1:1758\n878#1:1759\n882#1:1760\n884#1:1761\n888#1:1762\n890#1:1763\n894#1:1764\n896#1:1765\n916#1:1766\n919#1:1767\n1317#1:1789\n1658#1:1828\n*E\n"})
public abstract class Buffer {
    private static final byte[] HEX_DIGIT_BYTES = _JvmPlatformKt.asUtf8ToByteArray("0123456789abcdef");
    private static final long[] DigitCountToLargestValue = {-1, 9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, 9999999999L, 99999999999L, 999999999999L, 9999999999999L, 99999999999999L, 999999999999999L, 9999999999999999L, 99999999999999999L, 999999999999999999L, LongCompanionObject.MAX_VALUE};

    public static final byte[] getHEX_DIGIT_BYTES() {
        return HEX_DIGIT_BYTES;
    }

    public static final boolean rangeEquals(Segment segment, int i, byte[] bArr, int i2, int i3) {
        int i4 = segment.limit;
        byte[] bArr2 = segment.data;
        while (i2 < i3) {
            if (i == i4) {
                segment = segment.next;
                byte[] bArr3 = segment.data;
                bArr2 = bArr3;
                i = segment.pos;
                i4 = segment.limit;
            }
            if (bArr2[i] != bArr[i2]) {
                return false;
            }
            i++;
            i2++;
        }
        return true;
    }

    public static final String readUtf8Line(okio.Buffer buffer, long j) throws EOFException {
        if (j > 0) {
            long j2 = j - 1;
            if (buffer.getByte(j2) == 13) {
                String utf8 = buffer.readUtf8(j2);
                buffer.skip(2L);
                return utf8;
            }
        }
        String utf82 = buffer.readUtf8(j);
        buffer.skip(1L);
        return utf82;
    }

    public static /* synthetic */ int selectPrefix$default(okio.Buffer buffer, Options options, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return selectPrefix(buffer, options, z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:82:0x0052, code lost:
    
        if (r19 == false) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0054, code lost:
    
        return -2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0075, code lost:
    
        return r10;
     */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0090 A[LOOP:0: B:63:0x001a->B:101:0x0090, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:107:0x008f A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final int selectPrefix(okio.Buffer r17, okio.Options r18, boolean r19) {
        /*
            r0 = r17
            okio.Segment r0 = r0.head
            r1 = -2
            r2 = -1
            if (r0 != 0) goto Lc
            if (r19 == 0) goto Lb
            return r1
        Lb:
            return r2
        Lc:
            byte[] r3 = r0.data
            int r4 = r0.pos
            int r5 = r0.limit
            int[] r6 = r18.getTrie()
            r7 = 0
            r9 = r0
            r10 = r2
            r8 = r7
        L1a:
            int r11 = r8 + 1
            r12 = r6[r8]
            int r8 = r8 + 2
            r11 = r6[r11]
            if (r11 == r2) goto L25
            r10 = r11
        L25:
            if (r9 != 0) goto L28
            goto L52
        L28:
            r11 = 0
            if (r12 >= 0) goto L6b
            int r12 = r12 * (-1)
            int r13 = r8 + r12
        L2f:
            int r12 = r4 + 1
            r4 = r3[r4]
            r4 = r4 & 255(0xff, float:3.57E-43)
            int r14 = r8 + 1
            r8 = r6[r8]
            if (r4 == r8) goto L3c
            goto L75
        L3c:
            if (r14 != r13) goto L40
            r4 = 1
            goto L41
        L40:
            r4 = r7
        L41:
            if (r12 != r5) goto L5b
            okio.Segment r3 = r9.next
            int r5 = r3.pos
            byte[] r8 = r3.data
            int r9 = r3.limit
            if (r3 != r0) goto L55
            if (r4 == 0) goto L52
            r3 = r8
            r8 = r11
            goto L5e
        L52:
            if (r19 == 0) goto L75
            return r1
        L55:
            r16 = r8
            r8 = r3
            r3 = r16
            goto L5e
        L5b:
            r8 = r9
            r9 = r5
            r5 = r12
        L5e:
            if (r4 == 0) goto L66
            r4 = r6[r14]
            r13 = r5
            r5 = r9
            r9 = r8
            goto L8d
        L66:
            r4 = r5
            r5 = r9
            r9 = r8
            r8 = r14
            goto L2f
        L6b:
            int r13 = r4 + 1
            r4 = r3[r4]
            r4 = r4 & 255(0xff, float:3.57E-43)
            int r14 = r8 + r12
        L73:
            if (r8 != r14) goto L76
        L75:
            return r10
        L76:
            r15 = r6[r8]
            if (r4 != r15) goto L93
            int r8 = r8 + r12
            r4 = r6[r8]
            if (r13 != r5) goto L8d
            okio.Segment r9 = r9.next
            int r3 = r9.pos
            byte[] r5 = r9.data
            int r8 = r9.limit
            r13 = r3
            r3 = r5
            r5 = r8
            if (r9 != r0) goto L8d
            r9 = r11
        L8d:
            if (r4 < 0) goto L90
            return r4
        L90:
            int r8 = -r4
            r4 = r13
            goto L1a
        L93:
            int r8 = r8 + 1
            goto L73
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.internal.Buffer.selectPrefix(okio.Buffer, okio.Options, boolean):int");
    }

    public static final int countDigitsIn(long j) {
        int iNumberOfLeadingZeros = ((64 - Long.numberOfLeadingZeros(j)) * 10) >>> 5;
        return iNumberOfLeadingZeros + (j > DigitCountToLargestValue[iNumberOfLeadingZeros] ? 1 : 0);
    }

    public static /* synthetic */ long commonIndexOf$default(okio.Buffer buffer, ByteString byteString, long j, long j2, int i, int i2, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            j2 = LongCompanionObject.MAX_VALUE;
        }
        return commonIndexOf(buffer, byteString, j, j2, (i3 & 8) != 0 ? 0 : i, (i3 & 16) != 0 ? byteString.size() : i2);
    }

    public static final long commonIndexOf(okio.Buffer buffer, ByteString byteString, long j, long j2, int i, int i2) {
        Segment segment;
        int i3;
        long j3 = j;
        long size = j2;
        long j4 = i2;
        SegmentedByteString.checkOffsetAndCount(byteString.size(), i, j4);
        long size2 = 0;
        if (i2 <= 0) {
            g$$ExternalSyntheticBUOutline1.m207m("byteCount == 0");
            return 0L;
        }
        if (j3 < 0) {
            Buffer$$ExternalSyntheticBUOutline3.m977m("fromIndex < 0: ", j3);
            return 0L;
        }
        if (j3 > size) {
            throw new IllegalArgumentException(("fromIndex > toIndex: " + j3 + " > " + size).toString());
        }
        if (size > buffer.getSize()) {
            size = buffer.getSize();
        }
        long j5 = -1;
        if (j3 == size || (segment = buffer.head) == null) {
            return -1L;
        }
        if (buffer.getSize() - j3 < j3) {
            size2 = buffer.getSize();
            while (size2 > j3) {
                segment = segment.prev;
                size2 -= (long) (segment.limit - segment.pos);
                j5 = j5;
            }
            long j6 = j5;
            byte[] bArrInternalArray$okio = byteString.internalArray$okio();
            byte b2 = bArrInternalArray$okio[i];
            long jMin = Math.min(size, (buffer.getSize() - j4) + 1);
            while (size2 < jMin) {
                byte[] bArr = segment.data;
                int iMin = (int) Math.min(segment.limit, (((long) segment.pos) + jMin) - size2);
                i3 = (int) ((((long) segment.pos) + j3) - size2);
                while (i3 < iMin) {
                    if (bArr[i3] != b2 || !rangeEquals(segment, i3 + 1, bArrInternalArray$okio, i + 1, i2)) {
                        i3++;
                    }
                }
                size2 += (long) (segment.limit - segment.pos);
                segment = segment.next;
                j3 = size2;
            }
            return j6;
        }
        while (true) {
            long j7 = ((long) (segment.limit - segment.pos)) + size2;
            if (j7 > j3) {
                break;
            }
            segment = segment.next;
            size2 = j7;
        }
        byte[] bArrInternalArray$okio2 = byteString.internalArray$okio();
        byte b3 = bArrInternalArray$okio2[i];
        long jMin2 = Math.min(size, (buffer.getSize() - j4) + 1);
        while (size2 < jMin2) {
            byte[] bArr2 = segment.data;
            int iMin2 = (int) Math.min(segment.limit, (((long) segment.pos) + jMin2) - size2);
            i3 = (int) ((((long) segment.pos) + j3) - size2);
            while (i3 < iMin2) {
                if (bArr2[i3] != b3 || !rangeEquals(segment, i3 + 1, bArrInternalArray$okio2, i + 1, i2)) {
                    i3++;
                }
            }
            size2 += (long) (segment.limit - segment.pos);
            segment = segment.next;
            j3 = size2;
        }
        return -1L;
        return ((long) (i3 - segment.pos)) + size2;
    }

    public static final Buffer.UnsafeCursor commonReadAndWriteUnsafe(okio.Buffer buffer, Buffer.UnsafeCursor unsafeCursor) {
        Buffer.UnsafeCursor unsafeCursorResolveDefaultParameter = SegmentedByteString.resolveDefaultParameter(unsafeCursor);
        if (unsafeCursorResolveDefaultParameter.buffer != null) {
            Segment$$ExternalSyntheticBUOutline1.m992m("already attached to a buffer");
            return null;
        }
        unsafeCursorResolveDefaultParameter.buffer = buffer;
        unsafeCursorResolveDefaultParameter.readWrite = true;
        return unsafeCursorResolveDefaultParameter;
    }
}
