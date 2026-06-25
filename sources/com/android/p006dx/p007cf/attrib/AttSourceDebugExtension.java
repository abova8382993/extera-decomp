package com.android.p006dx.p007cf.attrib;

import com.android.p006dx.rop.cst.CstString;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
public final class AttSourceDebugExtension extends BaseAttribute {
    public static final String ATTRIBUTE_NAME = "SourceDebugExtension";
    private final CstString smapString;

    public AttSourceDebugExtension(CstString cstString) {
        super(ATTRIBUTE_NAME);
        if (cstString == null) {
            g$$ExternalSyntheticBUOutline2.m208m("smapString == null");
            throw null;
        }
        this.smapString = cstString;
    }

    @Override // com.android.p006dx.p007cf.iface.Attribute
    public int byteLength() {
        return this.smapString.getUtf8Size() + 6;
    }

    public CstString getSmapString() {
        return this.smapString;
    }
}
