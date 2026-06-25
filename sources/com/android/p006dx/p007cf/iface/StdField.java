package com.android.p006dx.p007cf.iface;

import com.android.p006dx.p007cf.attrib.AttConstantValue;
import com.android.p006dx.rop.cst.CstNat;
import com.android.p006dx.rop.cst.CstType;
import com.android.p006dx.rop.cst.TypedConstant;

/* JADX INFO: loaded from: classes4.dex */
public final class StdField extends StdMember implements Field {
    public StdField(CstType cstType, int i, CstNat cstNat, AttributeList attributeList) {
        super(cstType, i, cstNat, attributeList);
    }

    @Override // com.android.p006dx.p007cf.iface.Field
    public TypedConstant getConstantValue() {
        AttConstantValue attConstantValue = (AttConstantValue) getAttributes().findFirst(AttConstantValue.ATTRIBUTE_NAME);
        if (attConstantValue == null) {
            return null;
        }
        return attConstantValue.getConstantValue();
    }
}
