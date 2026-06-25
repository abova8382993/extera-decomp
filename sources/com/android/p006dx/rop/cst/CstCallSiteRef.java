package com.android.p006dx.rop.cst;

import com.android.p006dx.rop.type.Prototype;
import com.android.p006dx.rop.type.Type;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
public class CstCallSiteRef extends Constant {

    /* JADX INFO: renamed from: id */
    private final int f118id;
    private final CstInvokeDynamic invokeDynamic;

    @Override // com.android.p006dx.rop.cst.Constant
    public boolean isCategory2() {
        return false;
    }

    public CstCallSiteRef(CstInvokeDynamic cstInvokeDynamic, int i) {
        if (cstInvokeDynamic == null) {
            g$$ExternalSyntheticBUOutline2.m208m("invokeDynamic == null");
            throw null;
        }
        this.invokeDynamic = cstInvokeDynamic;
        this.f118id = i;
    }

    @Override // com.android.p006dx.rop.cst.Constant
    public String typeName() {
        return "CallSiteRef";
    }

    @Override // com.android.p006dx.rop.cst.Constant
    public int compareTo0(Constant constant) {
        CstCallSiteRef cstCallSiteRef = (CstCallSiteRef) constant;
        int iCompareTo = this.invokeDynamic.compareTo((Constant) cstCallSiteRef.invokeDynamic);
        return iCompareTo != 0 ? iCompareTo : Integer.compare(this.f118id, cstCallSiteRef.f118id);
    }

    @Override // com.android.p006dx.util.ToHuman
    public String toHuman() {
        return getCallSite().toHuman();
    }

    public String toString() {
        return getCallSite().toString();
    }

    public Prototype getPrototype() {
        return this.invokeDynamic.getPrototype();
    }

    public Type getReturnType() {
        return this.invokeDynamic.getReturnType();
    }

    public CstCallSite getCallSite() {
        return this.invokeDynamic.getCallSite();
    }
}
