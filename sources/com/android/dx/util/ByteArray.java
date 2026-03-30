package com.android.dx.util;

/* JADX INFO: loaded from: classes4.dex */
public final class ByteArray {
    private final byte[] bytes;
    private final int size;
    private final int start;

    public ByteArray(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            throw new NullPointerException("bytes == null");
        }
        if (i < 0) {
            throw new IllegalArgumentException("start < 0");
        }
        if (i2 < i) {
            throw new IllegalArgumentException("end < start");
        }
        if (i2 > bArr.length) {
            throw new IllegalArgumentException("end > bytes.length");
        }
        this.bytes = bArr;
        this.start = i;
        this.size = i2 - i;
    }

    public ByteArray(byte[] bArr) {
        this(bArr, 0, bArr.length);
    }

    public int size() {
        return this.size;
    }

    public void getBytes(byte[] bArr, int i) {
        int length = bArr.length - i;
        int i2 = this.size;
        if (length < i2) {
            throw new IndexOutOfBoundsException("(out.length - offset) < size()");
        }
        System.arraycopy(this.bytes, this.start, bArr, i, i2);
    }
}
