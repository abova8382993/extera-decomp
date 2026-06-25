package com.android.p006dx.p007cf.direct;

import com.android.p006dx.p007cf.iface.AttributeList;
import com.android.p006dx.p007cf.iface.Member;
import com.android.p006dx.p007cf.iface.StdMethod;
import com.android.p006dx.p007cf.iface.StdMethodList;
import com.android.p006dx.rop.code.AccessFlags;
import com.android.p006dx.rop.cst.CstNat;
import com.android.p006dx.rop.cst.CstType;

/* JADX INFO: loaded from: classes4.dex */
final class MethodListParser extends MemberListParser {
    private final StdMethodList methods;

    @Override // com.android.p006dx.p007cf.direct.MemberListParser
    public int getAttributeContext() {
        return 2;
    }

    public MethodListParser(DirectClassFile directClassFile, CstType cstType, int i, AttributeFactory attributeFactory) {
        super(directClassFile, cstType, i, attributeFactory);
        this.methods = new StdMethodList(getCount());
    }

    public StdMethodList getList() {
        parseIfNecessary();
        return this.methods;
    }

    @Override // com.android.p006dx.p007cf.direct.MemberListParser
    public String humanName() {
        return "method";
    }

    @Override // com.android.p006dx.p007cf.direct.MemberListParser
    public String humanAccessFlags(int i) {
        return AccessFlags.methodString(i);
    }

    @Override // com.android.p006dx.p007cf.direct.MemberListParser
    public Member set(int i, int i2, CstNat cstNat, AttributeList attributeList) {
        StdMethod stdMethod = new StdMethod(getDefiner(), i2, cstNat, attributeList);
        this.methods.set(i, stdMethod);
        return stdMethod;
    }
}
