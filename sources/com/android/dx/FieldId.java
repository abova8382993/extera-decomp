package com.android.dx;

import com.android.dx.rop.cst.CstFieldRef;
import com.android.dx.rop.cst.CstNat;
import com.android.dx.rop.cst.CstString;

/* JADX INFO: loaded from: classes4.dex */
public final class FieldId {
    final CstFieldRef constant;
    final TypeId declaringType;
    final String name;
    final CstNat nat;
    final TypeId type;

    FieldId(TypeId typeId, TypeId typeId2, String str) {
        if (typeId == null || typeId2 == null || str == null) {
            throw null;
        }
        this.declaringType = typeId;
        this.type = typeId2;
        this.name = str;
        CstNat cstNat = new CstNat(new CstString(str), new CstString(typeId2.name));
        this.nat = cstNat;
        this.constant = new CstFieldRef(typeId.constant, cstNat);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FieldId)) {
            return false;
        }
        FieldId fieldId = (FieldId) obj;
        return fieldId.declaringType.equals(this.declaringType) && fieldId.name.equals(this.name);
    }

    public int hashCode() {
        return this.declaringType.hashCode() + (this.name.hashCode() * 37);
    }

    public String toString() {
        return this.declaringType + "." + this.name;
    }
}
