package com.android.dx.dex.file;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public final class HeaderSection extends UniformItemSection {
    private final List list;

    @Override // com.android.dx.dex.file.UniformItemSection
    protected void orderItems() {
    }

    public HeaderSection(DexFile dexFile) {
        super(null, dexFile, 4);
        HeaderItem headerItem = new HeaderItem();
        headerItem.setIndex(0);
        this.list = Collections.singletonList(headerItem);
    }

    @Override // com.android.dx.dex.file.Section
    public Collection items() {
        return this.list;
    }
}
