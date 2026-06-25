package com.android.p006dx.p007cf.attrib;

import com.android.p006dx.rop.cst.CstString;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
public final class AttSignature extends BaseAttribute {
    public static final String ATTRIBUTE_NAME = "Signature";
    private final CstString signature;

    @Override // com.android.p006dx.p007cf.iface.Attribute
    public int byteLength() {
        return 8;
    }

    public AttSignature(CstString cstString) {
        super(ATTRIBUTE_NAME);
        if (cstString == null) {
            g$$ExternalSyntheticBUOutline2.m208m("signature == null");
            throw null;
        }
        this.signature = cstString;
    }

    public CstString getSignature() {
        return this.signature;
    }
}
