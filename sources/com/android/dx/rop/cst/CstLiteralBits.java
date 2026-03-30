package com.android.dx.rop.cst;

/* JADX INFO: loaded from: classes4.dex */
public abstract class CstLiteralBits extends TypedConstant {
    public abstract boolean fitsInInt();

    public abstract int getIntBits();

    public abstract long getLongBits();
}
