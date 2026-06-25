package com.android.p006dx.rop.type;

import com.android.p006dx.util.ToHuman;

/* JADX INFO: loaded from: classes4.dex */
public interface TypeBearer extends ToHuman {
    int getBasicFrameType();

    int getBasicType();

    TypeBearer getFrameType();

    Type getType();

    boolean isConstant();
}
