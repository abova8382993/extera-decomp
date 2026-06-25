package com.android.p006dx.p009io.instructions;

import java.io.EOFException;
import okio.Buffer$$ExternalSyntheticBUOutline1;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
public final class ShortArrayCodeInput extends BaseCodeCursor implements CodeInput {
    private final short[] array;

    public ShortArrayCodeInput(short[] sArr) {
        if (sArr == null) {
            g$$ExternalSyntheticBUOutline2.m208m("array == null");
            throw null;
        }
        this.array = sArr;
    }

    @Override // com.android.p006dx.p009io.instructions.CodeInput
    public boolean hasMore() {
        return cursor() < this.array.length;
    }

    @Override // com.android.p006dx.p009io.instructions.CodeInput
    public int read() throws EOFException {
        try {
            short s = this.array[cursor()];
            advance(1);
            return 65535 & s;
        } catch (ArrayIndexOutOfBoundsException unused) {
            Buffer$$ExternalSyntheticBUOutline1.m975m();
            return 0;
        }
    }

    @Override // com.android.p006dx.p009io.instructions.CodeInput
    public int readInt() throws EOFException {
        return (read() << 16) | read();
    }

    @Override // com.android.p006dx.p009io.instructions.CodeInput
    public long readLong() {
        return ((long) read()) | (((long) read()) << 16) | (((long) read()) << 32) | (((long) read()) << 48);
    }
}
