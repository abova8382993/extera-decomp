package com.android.dx.util;

import com.android.dex.util.ByteOutput;

/* JADX INFO: loaded from: classes4.dex */
public interface Output extends ByteOutput {
    void alignTo(int i);

    void assertCursor(int i);

    int getCursor();

    void write(ByteArray byteArray);

    void write(byte[] bArr);

    @Override // com.android.dex.util.ByteOutput
    void writeByte(int i);

    void writeInt(int i);

    void writeShort(int i);

    int writeUleb128(int i);

    void writeZeroes(int i);
}
