package com.android.p006dx.dex.file;

import com.android.p006dx.rop.cst.CstType;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
public abstract class IdItem extends IndexedItem {
    private final CstType type;

    public IdItem(CstType cstType) {
        if (cstType == null) {
            g$$ExternalSyntheticBUOutline2.m208m("type == null");
            throw null;
        }
        this.type = cstType;
    }

    @Override // com.android.p006dx.dex.file.Item
    public void addContents(DexFile dexFile) {
        dexFile.getTypeIds().intern(this.type);
    }

    public final CstType getDefiningClass() {
        return this.type;
    }
}
