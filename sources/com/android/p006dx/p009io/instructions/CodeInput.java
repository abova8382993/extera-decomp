package com.android.p006dx.p009io.instructions;

/* JADX INFO: loaded from: classes4.dex */
public interface CodeInput extends CodeCursor {
    boolean hasMore();

    int read();

    int readInt();

    long readLong();
}
