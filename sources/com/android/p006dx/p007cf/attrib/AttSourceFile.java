package com.android.p006dx.p007cf.attrib;

import com.android.p006dx.rop.cst.CstString;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
public final class AttSourceFile extends BaseAttribute {
    public static final String ATTRIBUTE_NAME = "SourceFile";
    private final CstString sourceFile;

    @Override // com.android.p006dx.p007cf.iface.Attribute
    public int byteLength() {
        return 8;
    }

    public AttSourceFile(CstString cstString) {
        super(ATTRIBUTE_NAME);
        if (cstString == null) {
            g$$ExternalSyntheticBUOutline2.m208m("sourceFile == null");
            throw null;
        }
        this.sourceFile = cstString;
    }

    public CstString getSourceFile() {
        return this.sourceFile;
    }
}
