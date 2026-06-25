package com.android.p006dx.p007cf.attrib;

import com.android.p006dx.rop.cst.CstNat;
import com.android.p006dx.rop.cst.CstType;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
public final class AttEnclosingMethod extends BaseAttribute {
    public static final String ATTRIBUTE_NAME = "EnclosingMethod";
    private final CstNat method;
    private final CstType type;

    @Override // com.android.p006dx.p007cf.iface.Attribute
    public int byteLength() {
        return 10;
    }

    public AttEnclosingMethod(CstType cstType, CstNat cstNat) {
        super(ATTRIBUTE_NAME);
        if (cstType == null) {
            g$$ExternalSyntheticBUOutline2.m208m("type == null");
            throw null;
        }
        this.type = cstType;
        this.method = cstNat;
    }

    public CstType getEnclosingClass() {
        return this.type;
    }

    public CstNat getMethod() {
        return this.method;
    }
}
