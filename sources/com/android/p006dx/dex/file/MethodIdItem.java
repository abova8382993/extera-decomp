package com.android.p006dx.dex.file;

import com.android.p006dx.rop.cst.CstBaseMethodRef;

/* JADX INFO: loaded from: classes4.dex */
public final class MethodIdItem extends MemberIdItem {
    public MethodIdItem(CstBaseMethodRef cstBaseMethodRef) {
        super(cstBaseMethodRef);
    }

    @Override // com.android.p006dx.dex.file.Item
    public ItemType itemType() {
        return ItemType.TYPE_METHOD_ID_ITEM;
    }

    @Override // com.android.p006dx.dex.file.MemberIdItem, com.android.p006dx.dex.file.IdItem, com.android.p006dx.dex.file.Item
    public void addContents(DexFile dexFile) {
        super.addContents(dexFile);
        dexFile.getProtoIds().intern(getMethodRef().getPrototype());
    }

    public CstBaseMethodRef getMethodRef() {
        return (CstBaseMethodRef) getRef();
    }

    @Override // com.android.p006dx.dex.file.MemberIdItem
    public int getTypoidIdx(DexFile dexFile) {
        return dexFile.getProtoIds().indexOf(getMethodRef().getPrototype());
    }

    @Override // com.android.p006dx.dex.file.MemberIdItem
    public String getTypoidName() {
        return "proto_idx";
    }
}
