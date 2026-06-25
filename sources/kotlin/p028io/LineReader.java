package kotlin.p028io;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import kotlin.Metadata;
import kotlin.jvm.internal.SourceDebugExtension;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes5.dex */
@Metadata(m876d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\bÀ\u0002\u0018\u00002\u00020\u0001B\t\bB¢\u0006\u0004\b\u0002\u0010\u0003J\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0086\u0080\u0004J\u0012\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\tH\u0082\u0080\u0004J\n\u0010\u001d\u001a\u00020\u0005H\u0082\u0080\u0004J\u001a\u0010\u001e\u001a\u00020\u00052\u0006\u0010\u001f\u001a\u00020\u00052\u0006\u0010 \u001a\u00020\u0005H\u0082\u0080\u0004J\u0012\u0010!\u001a\u00020\"2\u0006\u0010\u0019\u001a\u00020\u001aH\u0082\u0080\u0004J\n\u0010#\u001a\u00020\"H\u0082\u0080\u0004J\n\u0010$\u001a\u00020\"H\u0082\u0080\u0004R\u000f\u0010\u0004\u001a\u00020\u0005X\u0082Ô\b¢\u0006\u0002\n\u0000R\u000f\u0010\u0006\u001a\u00020\u0007X\u0082®\b¢\u0006\u0002\n\u0000R\u000f\u0010\b\u001a\u00020\tX\u0082\u008e\b¢\u0006\u0002\n\u0000R\u000f\u0010\n\u001a\u00020\u000bX\u0082\u0084\b¢\u0006\u0002\n\u0000R\u000f\u0010\f\u001a\u00020\rX\u0082\u0084\b¢\u0006\u0002\n\u0000R\u000f\u0010\u000e\u001a\u00020\u000fX\u0082\u0084\b¢\u0006\u0002\n\u0000R\u000f\u0010\u0010\u001a\u00020\u0011X\u0082\u0084\b¢\u0006\u0002\n\u0000R\u0013\u0010\u0012\u001a\u00060\u0013j\u0002`\u0014X\u0082\u0084\b¢\u0006\u0002\n\u0000¨\u0006%"}, m877d2 = {"Lkotlin/io/LineReader;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "BUFFER_SIZE", _UrlKt.FRAGMENT_ENCODE_SET, "decoder", "Ljava/nio/charset/CharsetDecoder;", "directEOL", _UrlKt.FRAGMENT_ENCODE_SET, "bytes", _UrlKt.FRAGMENT_ENCODE_SET, "chars", _UrlKt.FRAGMENT_ENCODE_SET, "byteBuf", "Ljava/nio/ByteBuffer;", "charBuf", "Ljava/nio/CharBuffer;", "sb", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "readLine", _UrlKt.FRAGMENT_ENCODE_SET, "inputStream", "Ljava/io/InputStream;", "charset", "Ljava/nio/charset/Charset;", "decode", "endOfInput", "compactBytes", "decodeEndOfInput", "nBytes", "nChars", "updateCharset", _UrlKt.FRAGMENT_ENCODE_SET, "resetAll", "trimStringBuilder", "kotlin-stdlib"}, m878k = 1, m879mv = {2, 3, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nConsole.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Console.kt\nkotlin/io/LineReader\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,302:1\n1#2:303\n*E\n"})
public final class LineReader {
    private static final int BUFFER_SIZE = 32;
    private static final ByteBuffer byteBuf;
    private static final byte[] bytes;
    private static final CharBuffer charBuf;
    private static final char[] chars;
    private static CharsetDecoder decoder;
    private static boolean directEOL;
    public static final LineReader INSTANCE = new LineReader();
    private static final StringBuilder sb = new StringBuilder();

    private LineReader() {
    }

    static {
        byte[] bArr = new byte[32];
        bytes = bArr;
        char[] cArr = new char[32];
        chars = cArr;
        byteBuf = ByteBuffer.wrap(bArr);
        charBuf = CharBuffer.wrap(cArr);
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x0069, code lost:
    
        if (r10 <= 0) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x006b, code lost:
    
        r0 = kotlin.p028io.LineReader.chars;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0071, code lost:
    
        if (r0[r10 - 1] != '\n') goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0073, code lost:
    
        r1 = r10 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0075, code lost:
    
        if (r1 <= 0) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x007d, code lost:
    
        if (r0[r10 - 2] != '\r') goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x007f, code lost:
    
        r10 = r10 - 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0082, code lost:
    
        r10 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0083, code lost:
    
        r0 = kotlin.p028io.LineReader.sb;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0089, code lost:
    
        if (r0.length() != 0) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0093, code lost:
    
        return new java.lang.String(kotlin.p028io.LineReader.chars, 0, r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0094, code lost:
    
        r0.append(kotlin.p028io.LineReader.chars, 0, r10);
        r10 = r0.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00a1, code lost:
    
        if (r0.length() <= 32) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00a3, code lost:
    
        trimStringBuilder();
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00a6, code lost:
    
        r0.setLength(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00aa, code lost:
    
        return r10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized java.lang.String readLine(java.io.InputStream r10, java.nio.charset.Charset r11) {
        /*
            r9 = this;
            monitor-enter(r9)
            java.nio.charset.CharsetDecoder r0 = kotlin.p028io.LineReader.decoder     // Catch: java.lang.Throwable -> L14
            r1 = 0
            if (r0 == 0) goto L17
            if (r0 != 0) goto L9
            r0 = r1
        L9:
            java.nio.charset.Charset r0 = r0.charset()     // Catch: java.lang.Throwable -> L14
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r11)     // Catch: java.lang.Throwable -> L14
            if (r0 != 0) goto L1a
            goto L17
        L14:
            r10 = move-exception
            goto Lb1
        L17:
            r9.updateCharset(r11)     // Catch: java.lang.Throwable -> L14
        L1a:
            r11 = 0
            r0 = r11
            r2 = r0
        L1d:
            int r3 = r10.read()     // Catch: java.lang.Throwable -> L14
            r4 = 32
            r5 = -1
            r6 = 10
            if (r3 != r5) goto L3b
            java.lang.StringBuilder r10 = kotlin.p028io.LineReader.sb     // Catch: java.lang.Throwable -> L14
            int r10 = r10.length()     // Catch: java.lang.Throwable -> L14
            if (r10 != 0) goto L36
            if (r0 != 0) goto L36
            if (r2 != 0) goto L36
            monitor-exit(r9)
            return r1
        L36:
            int r10 = r9.decodeEndOfInput(r0, r2)     // Catch: java.lang.Throwable -> L14
            goto L69
        L3b:
            byte[] r5 = kotlin.p028io.LineReader.bytes     // Catch: java.lang.Throwable -> L14
            int r7 = r0 + 1
            byte r8 = (byte) r3     // Catch: java.lang.Throwable -> L14
            r5[r0] = r8     // Catch: java.lang.Throwable -> L14
            if (r3 == r6) goto L4d
            if (r7 == r4) goto L4d
            boolean r0 = kotlin.p028io.LineReader.directEOL     // Catch: java.lang.Throwable -> L14
            if (r0 != 0) goto L4b
            goto L4d
        L4b:
            r0 = r7
            goto L1d
        L4d:
            java.nio.ByteBuffer r0 = kotlin.p028io.LineReader.byteBuf     // Catch: java.lang.Throwable -> L14
            r0.limit(r7)     // Catch: java.lang.Throwable -> L14
            java.nio.CharBuffer r3 = kotlin.p028io.LineReader.charBuf     // Catch: java.lang.Throwable -> L14
            r3.position(r2)     // Catch: java.lang.Throwable -> L14
            int r2 = r9.decode(r11)     // Catch: java.lang.Throwable -> L14
            if (r2 <= 0) goto Lab
            char[] r3 = kotlin.p028io.LineReader.chars     // Catch: java.lang.Throwable -> L14
            int r5 = r2 + (-1)
            char r3 = r3[r5]     // Catch: java.lang.Throwable -> L14
            if (r3 != r6) goto Lab
            r0.position(r11)     // Catch: java.lang.Throwable -> L14
            r10 = r2
        L69:
            if (r10 <= 0) goto L83
            char[] r0 = kotlin.p028io.LineReader.chars     // Catch: java.lang.Throwable -> L14
            int r1 = r10 + (-1)
            char r1 = r0[r1]     // Catch: java.lang.Throwable -> L14
            if (r1 != r6) goto L83
            int r1 = r10 + (-1)
            if (r1 <= 0) goto L82
            int r2 = r10 + (-2)
            char r0 = r0[r2]     // Catch: java.lang.Throwable -> L14
            r2 = 13
            if (r0 != r2) goto L82
            int r10 = r10 + (-2)
            goto L83
        L82:
            r10 = r1
        L83:
            java.lang.StringBuilder r0 = kotlin.p028io.LineReader.sb     // Catch: java.lang.Throwable -> L14
            int r1 = r0.length()     // Catch: java.lang.Throwable -> L14
            if (r1 != 0) goto L94
            java.lang.String r0 = new java.lang.String     // Catch: java.lang.Throwable -> L14
            char[] r1 = kotlin.p028io.LineReader.chars     // Catch: java.lang.Throwable -> L14
            r0.<init>(r1, r11, r10)     // Catch: java.lang.Throwable -> L14
            monitor-exit(r9)
            return r0
        L94:
            char[] r1 = kotlin.p028io.LineReader.chars     // Catch: java.lang.Throwable -> L14
            r0.append(r1, r11, r10)     // Catch: java.lang.Throwable -> L14
            java.lang.String r10 = r0.toString()     // Catch: java.lang.Throwable -> L14
            int r1 = r0.length()     // Catch: java.lang.Throwable -> L14
            if (r1 <= r4) goto La6
            r9.trimStringBuilder()     // Catch: java.lang.Throwable -> L14
        La6:
            r0.setLength(r11)     // Catch: java.lang.Throwable -> L14
            monitor-exit(r9)
            return r10
        Lab:
            int r0 = r9.compactBytes()     // Catch: java.lang.Throwable -> L14
            goto L1d
        Lb1:
            monitor-exit(r9)     // Catch: java.lang.Throwable -> L14
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p028io.LineReader.readLine(java.io.InputStream, java.nio.charset.Charset):java.lang.String");
    }

    private final int decode(boolean endOfInput) throws CharacterCodingException {
        while (true) {
            CharsetDecoder charsetDecoder = decoder;
            if (charsetDecoder == null) {
                charsetDecoder = null;
            }
            ByteBuffer byteBuffer = byteBuf;
            CharBuffer charBuffer = charBuf;
            CoderResult coderResultDecode = charsetDecoder.decode(byteBuffer, charBuffer, endOfInput);
            if (coderResultDecode.isError()) {
                resetAll();
                coderResultDecode.throwException();
            }
            int iPosition = charBuffer.position();
            if (!coderResultDecode.isOverflow()) {
                return iPosition;
            }
            StringBuilder sb2 = sb;
            char[] cArr = chars;
            int i = iPosition - 1;
            sb2.append(cArr, 0, i);
            charBuffer.position(0);
            charBuffer.limit(32);
            charBuffer.put(cArr[i]);
        }
    }

    private final int compactBytes() {
        ByteBuffer byteBuffer = byteBuf;
        byteBuffer.compact();
        int iPosition = byteBuffer.position();
        byteBuffer.position(0);
        return iPosition;
    }

    private final int decodeEndOfInput(int nBytes, int nChars) throws CharacterCodingException {
        ByteBuffer byteBuffer = byteBuf;
        byteBuffer.limit(nBytes);
        charBuf.position(nChars);
        int iDecode = decode(true);
        CharsetDecoder charsetDecoder = decoder;
        if (charsetDecoder == null) {
            charsetDecoder = null;
        }
        charsetDecoder.reset();
        byteBuffer.position(0);
        return iDecode;
    }

    private final void updateCharset(Charset charset) {
        decoder = charset.newDecoder();
        ByteBuffer byteBuffer = byteBuf;
        byteBuffer.clear();
        CharBuffer charBuffer = charBuf;
        charBuffer.clear();
        byteBuffer.put((byte) 10);
        byteBuffer.flip();
        CharsetDecoder charsetDecoder = decoder;
        if (charsetDecoder == null) {
            charsetDecoder = null;
        }
        boolean z = false;
        charsetDecoder.decode(byteBuffer, charBuffer, false);
        if (charBuffer.position() == 1 && charBuffer.get(0) == '\n') {
            z = true;
        }
        directEOL = z;
        resetAll();
    }

    private final void resetAll() {
        CharsetDecoder charsetDecoder = decoder;
        if (charsetDecoder == null) {
            charsetDecoder = null;
        }
        charsetDecoder.reset();
        byteBuf.position(0);
        sb.setLength(0);
    }

    private final void trimStringBuilder() {
        StringBuilder sb2 = sb;
        sb2.setLength(32);
        sb2.trimToSize();
    }
}
