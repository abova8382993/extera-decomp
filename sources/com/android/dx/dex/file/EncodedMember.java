package com.android.dx.dex.file;

import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.ToHuman;

/* JADX INFO: loaded from: classes4.dex */
public abstract class EncodedMember implements ToHuman {
    private final int accessFlags;

    public abstract int encode(DexFile dexFile, AnnotatedOutput annotatedOutput, int i, int i2);

    public EncodedMember(int i) {
        this.accessFlags = i;
    }

    public final int getAccessFlags() {
        return this.accessFlags;
    }
}
