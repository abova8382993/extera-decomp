package com.android.p006dx.p007cf.iface;

import com.android.p006dx.rop.cst.CstNat;
import com.android.p006dx.rop.cst.CstString;
import com.android.p006dx.rop.cst.CstType;

/* JADX INFO: loaded from: classes4.dex */
public interface Member extends HasAttribute {
    int getAccessFlags();

    @Override // com.android.p006dx.p007cf.iface.HasAttribute
    AttributeList getAttributes();

    CstType getDefiningClass();

    CstString getDescriptor();

    CstString getName();

    CstNat getNat();
}
