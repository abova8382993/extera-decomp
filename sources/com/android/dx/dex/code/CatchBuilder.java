package com.android.dx.dex.code;

import java.util.HashSet;

/* JADX INFO: loaded from: classes4.dex */
public interface CatchBuilder {
    CatchTable build();

    HashSet getCatchTypes();

    boolean hasAnyCatches();
}
