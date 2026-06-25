package com.android.p006dx.p007cf.attrib;

import com.android.p006dx.rop.cst.Constant;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
public final class AttAnnotationDefault extends BaseAttribute {
    public static final String ATTRIBUTE_NAME = "AnnotationDefault";
    private final int byteLength;
    private final Constant value;

    public AttAnnotationDefault(Constant constant, int i) {
        super(ATTRIBUTE_NAME);
        if (constant == null) {
            g$$ExternalSyntheticBUOutline2.m208m("value == null");
            throw null;
        }
        this.value = constant;
        this.byteLength = i;
    }

    @Override // com.android.p006dx.p007cf.iface.Attribute
    public int byteLength() {
        return this.byteLength + 6;
    }

    public Constant getValue() {
        return this.value;
    }
}
