package p026j$.sun.nio.p027cs;

import java.nio.CharBuffer;
import java.nio.charset.CoderResult;

/* JADX INFO: renamed from: j$.sun.nio.cs.e */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2281e {

    /* JADX INFO: renamed from: a */
    public CoderResult f717a;

    /* JADX INFO: renamed from: b */
    public boolean f718b;

    /* JADX INFO: renamed from: a */
    public final int m565a(char c2, CharBuffer charBuffer) {
        if (!Character.isHighSurrogate(c2)) {
            if (Character.isLowSurrogate(c2)) {
                this.f717a = CoderResult.malformedForLength(1);
                return -1;
            }
            this.f718b = false;
            this.f717a = null;
            return c2;
        }
        if (!charBuffer.hasRemaining()) {
            this.f717a = CoderResult.UNDERFLOW;
            return -1;
        }
        char c3 = charBuffer.get();
        if (!Character.isLowSurrogate(c3)) {
            this.f717a = CoderResult.malformedForLength(1);
            return -1;
        }
        int codePoint = Character.toCodePoint(c2, c3);
        this.f718b = true;
        this.f717a = null;
        return codePoint;
    }

    /* JADX INFO: renamed from: b */
    public final int m566b(char c2, char[] cArr, int i, int i2) {
        if (!Character.isHighSurrogate(c2)) {
            if (Character.isLowSurrogate(c2)) {
                this.f717a = CoderResult.malformedForLength(1);
                return -1;
            }
            this.f718b = false;
            this.f717a = null;
            return c2;
        }
        if (i2 - i < 2) {
            this.f717a = CoderResult.UNDERFLOW;
            return -1;
        }
        char c3 = cArr[i + 1];
        if (!Character.isLowSurrogate(c3)) {
            this.f717a = CoderResult.malformedForLength(1);
            return -1;
        }
        int codePoint = Character.toCodePoint(c2, c3);
        this.f718b = true;
        this.f717a = null;
        return codePoint;
    }
}
