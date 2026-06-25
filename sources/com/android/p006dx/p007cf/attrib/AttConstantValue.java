package com.android.p006dx.p007cf.attrib;

import com.android.p006dx.rop.cst.CstDouble;
import com.android.p006dx.rop.cst.CstFloat;
import com.android.p006dx.rop.cst.CstInteger;
import com.android.p006dx.rop.cst.CstLong;
import com.android.p006dx.rop.cst.CstString;
import com.android.p006dx.rop.cst.TypedConstant;
import p005c.g$$ExternalSyntheticBUOutline1;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
public final class AttConstantValue extends BaseAttribute {
    public static final String ATTRIBUTE_NAME = "ConstantValue";
    private final TypedConstant constantValue;

    @Override // com.android.p006dx.p007cf.iface.Attribute
    public int byteLength() {
        return 8;
    }

    public AttConstantValue(TypedConstant typedConstant) {
        super(ATTRIBUTE_NAME);
        if ((typedConstant instanceof CstString) || (typedConstant instanceof CstInteger) || (typedConstant instanceof CstLong) || (typedConstant instanceof CstFloat) || (typedConstant instanceof CstDouble)) {
            this.constantValue = typedConstant;
        } else {
            if (typedConstant == null) {
                g$$ExternalSyntheticBUOutline2.m208m("constantValue == null");
                throw null;
            }
            g$$ExternalSyntheticBUOutline1.m207m("bad type for constantValue");
            throw null;
        }
    }

    public TypedConstant getConstantValue() {
        return this.constantValue;
    }
}
