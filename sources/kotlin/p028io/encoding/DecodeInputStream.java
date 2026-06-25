package kotlin.p028io.encoding;

import java.io.IOException;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.collections.ArraysKt;
import okhttp3.internal.url._UrlKt;
import okio.Segment$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes5.dex */
@ExperimentalEncodingApi
@Metadata(m876d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u000b\b\u0003\u0018\u00002\u00020\u0001B\u0019\bF\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\b\u0010\u0014\u001a\u00020\u000fH\u0016J \u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u000fH\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J*\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u000fH\u0082\u0080\u0004J\"\u0010\u001f\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u000fH\u0082\u0080\u0004J\n\u0010 \u001a\u00020\u0019H\u0082\u0080\u0004J\n\u0010!\u001a\u00020\u0019H\u0082\u0080\u0004J\u0012\u0010\"\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u000fH\u0082\u0080\u0004J\n\u0010#\u001a\u00020\u000fH\u0082\u0080\u0004R\u000f\u0010\u0002\u001a\u00020\u0001X\u0082\u0084\b¢\u0006\u0002\n\u0000R\u000f\u0010\u0003\u001a\u00020\u0004X\u0082\u0084\b¢\u0006\u0002\n\u0000R\u000f\u0010\u0007\u001a\u00020\bX\u0082\u008e\b¢\u0006\u0002\n\u0000R\u000f\u0010\t\u001a\u00020\bX\u0082\u008e\b¢\u0006\u0002\n\u0000R\u000f\u0010\n\u001a\u00020\u000bX\u0082\u0084\b¢\u0006\u0002\n\u0000R\u000f\u0010\f\u001a\u00020\u000bX\u0082\u0084\b¢\u0006\u0002\n\u0000R\u000f\u0010\r\u001a\u00020\u000bX\u0082\u0084\b¢\u0006\u0002\n\u0000R\u000f\u0010\u000e\u001a\u00020\u000fX\u0082\u008e\b¢\u0006\u0002\n\u0000R\u000f\u0010\u0010\u001a\u00020\u000fX\u0082\u008e\b¢\u0006\u0002\n\u0000R\u0015\u0010\u0011\u001a\u00020\u000f8BX\u0082\u0084\b¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013¨\u0006$"}, m877d2 = {"Lkotlin/io/encoding/DecodeInputStream;", "Ljava/io/InputStream;", "input", "base64", "Lkotlin/io/encoding/Base64;", "<init>", "(Ljava/io/InputStream;Lkotlin/io/encoding/Base64;)V", "isClosed", _UrlKt.FRAGMENT_ENCODE_SET, "isEOF", "singleByteBuffer", _UrlKt.FRAGMENT_ENCODE_SET, "symbolBuffer", "byteBuffer", "byteBufferStartIndex", _UrlKt.FRAGMENT_ENCODE_SET, "byteBufferEndIndex", "byteBufferLength", "getByteBufferLength", "()I", "read", "destination", "offset", "length", "close", _UrlKt.FRAGMENT_ENCODE_SET, "decodeSymbolBufferInto", "dst", "dstOffset", "dstEndIndex", "symbolBufferLength", "copyByteBufferInto", "resetByteBufferIfEmpty", "shiftByteBufferToStartIfNeeded", "handlePaddingSymbol", "readNextSymbol", "kotlin-stdlib"}, m878k = 1, m879mv = {2, 3, 0}, m881xi = 48)
final class DecodeInputStream extends InputStream {
    private final Base64 base64;
    private int byteBufferEndIndex;
    private int byteBufferStartIndex;
    private final InputStream input;
    private boolean isClosed;
    private boolean isEOF;
    private final byte[] singleByteBuffer = new byte[1];
    private final byte[] symbolBuffer = new byte[1024];
    private final byte[] byteBuffer = new byte[1024];

    public DecodeInputStream(InputStream inputStream, Base64 base64) {
        this.input = inputStream;
        this.base64 = base64;
    }

    private final int getByteBufferLength() {
        return this.byteBufferEndIndex - this.byteBufferStartIndex;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        int i = this.byteBufferStartIndex;
        if (i < this.byteBufferEndIndex) {
            int i2 = this.byteBuffer[i] & UByte.MAX_VALUE;
            this.byteBufferStartIndex = i + 1;
            resetByteBufferIfEmpty();
            return i2;
        }
        int i3 = read(this.singleByteBuffer, 0, 1);
        if (i3 == -1) {
            return -1;
        }
        if (i3 == 1) {
            return this.singleByteBuffer[0] & UByte.MAX_VALUE;
        }
        Segment$$ExternalSyntheticBUOutline1.m992m("Unreachable");
        return 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:102:0x0070, code lost:
    
        if (r2 != r11) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x0072, code lost:
    
        if (r4 == false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x0074, code lost:
    
        return -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x0076, code lost:
    
        return r2 - r11;
     */
    @Override // java.io.InputStream
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int read(byte[] r10, int r11, int r12) throws java.io.IOException {
        /*
            r9 = this;
            r0 = 0
            if (r11 < 0) goto L7d
            if (r12 < 0) goto L7d
            int r1 = r11 + r12
            int r2 = r10.length
            if (r1 > r2) goto L7d
            boolean r2 = r9.isClosed
            if (r2 != 0) goto L77
            boolean r2 = r9.isEOF
            r3 = -1
            if (r2 == 0) goto L14
            return r3
        L14:
            if (r12 != 0) goto L17
            return r0
        L17:
            int r2 = r9.getByteBufferLength()
            if (r2 < r12) goto L21
            r9.copyByteBufferInto(r10, r11, r12)
            return r12
        L21:
            int r2 = r9.getByteBufferLength()
            int r12 = r12 - r2
            int r12 = r12 + 2
            int r12 = r12 / 3
            int r12 = r12 * 4
            r2 = r11
        L2d:
            boolean r4 = r9.isEOF
            if (r4 != 0) goto L70
            if (r12 <= 0) goto L70
            byte[] r4 = r9.symbolBuffer
            int r4 = r4.length
            int r4 = java.lang.Math.min(r4, r12)
            r5 = r0
        L3b:
            boolean r6 = r9.isEOF
            if (r6 != 0) goto L5e
            if (r5 >= r4) goto L5e
            int r6 = r9.readNextSymbol()
            r7 = 1
            if (r6 == r3) goto L5b
            r8 = 61
            if (r6 == r8) goto L54
            byte[] r7 = r9.symbolBuffer
            byte r6 = (byte) r6
            r7[r5] = r6
            int r5 = r5 + 1
            goto L3b
        L54:
            int r5 = r9.handlePaddingSymbol(r5)
            r9.isEOF = r7
            goto L3b
        L5b:
            r9.isEOF = r7
            goto L3b
        L5e:
            if (r6 != 0) goto L69
            if (r5 != r4) goto L63
            goto L69
        L63:
            java.lang.String r9 = "Check failed."
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r9)
            return r0
        L69:
            int r12 = r12 - r5
            int r4 = r9.decodeSymbolBufferInto(r10, r2, r1, r5)
            int r2 = r2 + r4
            goto L2d
        L70:
            if (r2 != r11) goto L75
            if (r4 == 0) goto L75
            return r3
        L75:
            int r2 = r2 - r11
            return r2
        L77:
            java.lang.String r9 = "The input stream is closed."
            org.vosk.Model$$ExternalSyntheticBUOutline0.m1247m(r9)
            return r0
        L7d:
            java.lang.String r5 = ", buffer size: "
            int r6 = r10.length
            java.lang.String r1 = "offset: "
            java.lang.String r3 = ", length: "
            r2 = r11
            r4 = r12
            com.android.p006dx.util.Hex$$ExternalSyntheticBUOutline0.m235m(r1, r2, r3, r4, r5, r6)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p028io.encoding.DecodeInputStream.read(byte[], int, int):int");
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.isClosed) {
            return;
        }
        this.isClosed = true;
        this.input.close();
    }

    private final int decodeSymbolBufferInto(byte[] dst, int dstOffset, int dstEndIndex, int symbolBufferLength) {
        int i = this.byteBufferEndIndex;
        this.byteBufferEndIndex = i + this.base64.decodeIntoByteArray(this.symbolBuffer, this.byteBuffer, i, 0, symbolBufferLength);
        int iMin = Math.min(getByteBufferLength(), dstEndIndex - dstOffset);
        copyByteBufferInto(dst, dstOffset, iMin);
        shiftByteBufferToStartIfNeeded();
        return iMin;
    }

    private final void copyByteBufferInto(byte[] dst, int dstOffset, int length) {
        byte[] bArr = this.byteBuffer;
        int i = this.byteBufferStartIndex;
        ArraysKt.copyInto(bArr, dst, dstOffset, i, i + length);
        this.byteBufferStartIndex += length;
        resetByteBufferIfEmpty();
    }

    private final void resetByteBufferIfEmpty() {
        if (this.byteBufferStartIndex == this.byteBufferEndIndex) {
            this.byteBufferStartIndex = 0;
            this.byteBufferEndIndex = 0;
        }
    }

    private final void shiftByteBufferToStartIfNeeded() {
        byte[] bArr = this.byteBuffer;
        int length = bArr.length;
        int i = this.byteBufferEndIndex;
        if ((this.symbolBuffer.length / 4) * 3 > length - i) {
            ArraysKt.copyInto(bArr, bArr, 0, this.byteBufferStartIndex, i);
            this.byteBufferEndIndex -= this.byteBufferStartIndex;
            this.byteBufferStartIndex = 0;
        }
    }

    private final int handlePaddingSymbol(int symbolBufferLength) throws IOException {
        this.symbolBuffer[symbolBufferLength] = Base64.padSymbol;
        if ((symbolBufferLength & 3) != 2) {
            return symbolBufferLength + 1;
        }
        int nextSymbol = readNextSymbol();
        if (nextSymbol >= 0) {
            this.symbolBuffer[symbolBufferLength + 1] = (byte) nextSymbol;
        }
        return symbolBufferLength + 2;
    }

    private final int readNextSymbol() throws IOException {
        int i;
        if (!this.base64.getIsMimeScheme()) {
            return this.input.read();
        }
        do {
            i = this.input.read();
            if (i == -1) {
                break;
            }
        } while (!Base64Kt.isInMimeAlphabet(i));
        return i;
    }
}
