package com.android.p006dx.p007cf.direct;

import com.android.p006dx.p007cf.iface.AttributeList;
import com.android.p006dx.p007cf.iface.Member;
import com.android.p006dx.p007cf.iface.StdField;
import com.android.p006dx.p007cf.iface.StdFieldList;
import com.android.p006dx.rop.code.AccessFlags;
import com.android.p006dx.rop.cst.CstNat;
import com.android.p006dx.rop.cst.CstType;

/* JADX INFO: loaded from: classes4.dex */
final class FieldListParser extends MemberListParser {
    private final StdFieldList fields;

    @Override // com.android.p006dx.p007cf.direct.MemberListParser
    public int getAttributeContext() {
        return 1;
    }

    public FieldListParser(DirectClassFile directClassFile, CstType cstType, int i, AttributeFactory attributeFactory) {
        super(directClassFile, cstType, i, attributeFactory);
        this.fields = new StdFieldList(getCount());
    }

    public StdFieldList getList() {
        parseIfNecessary();
        return this.fields;
    }

    @Override // com.android.p006dx.p007cf.direct.MemberListParser
    public String humanName() {
        return "field";
    }

    @Override // com.android.p006dx.p007cf.direct.MemberListParser
    public String humanAccessFlags(int i) {
        return AccessFlags.fieldString(i);
    }

    @Override // com.android.p006dx.p007cf.direct.MemberListParser
    public Member set(int i, int i2, CstNat cstNat, AttributeList attributeList) {
        StdField stdField = new StdField(getDefiner(), i2, cstNat, attributeList);
        this.fields.set(i, stdField);
        return stdField;
    }
}
