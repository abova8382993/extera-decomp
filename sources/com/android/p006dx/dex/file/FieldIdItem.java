package com.android.p006dx.dex.file;

import com.android.p006dx.rop.cst.CstFieldRef;

/* JADX INFO: loaded from: classes4.dex */
public final class FieldIdItem extends MemberIdItem {
    public FieldIdItem(CstFieldRef cstFieldRef) {
        super(cstFieldRef);
    }

    @Override // com.android.p006dx.dex.file.Item
    public ItemType itemType() {
        return ItemType.TYPE_FIELD_ID_ITEM;
    }

    @Override // com.android.p006dx.dex.file.MemberIdItem, com.android.p006dx.dex.file.IdItem, com.android.p006dx.dex.file.Item
    public void addContents(DexFile dexFile) {
        super.addContents(dexFile);
        dexFile.getTypeIds().intern(getFieldRef().getType());
    }

    public CstFieldRef getFieldRef() {
        return (CstFieldRef) getRef();
    }

    @Override // com.android.p006dx.dex.file.MemberIdItem
    public int getTypoidIdx(DexFile dexFile) {
        return dexFile.getTypeIds().indexOf(getFieldRef().getType());
    }

    @Override // com.android.p006dx.dex.file.MemberIdItem
    public String getTypoidName() {
        return "type_idx";
    }
}
