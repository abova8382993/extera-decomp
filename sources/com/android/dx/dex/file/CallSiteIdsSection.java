package com.android.dx.dex.file;

import androidx.appcompat.app.WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

/* JADX INFO: loaded from: classes4.dex */
public final class CallSiteIdsSection extends UniformItemSection {
    private final TreeMap callSiteIds;
    private final TreeMap callSites;

    public CallSiteIdsSection(DexFile dexFile) {
        super("call_site_ids", dexFile, 4);
        this.callSiteIds = new TreeMap();
        this.callSites = new TreeMap();
    }

    @Override // com.android.dx.dex.file.UniformItemSection
    protected void orderItems() {
        Iterator it = this.callSiteIds.values().iterator();
        if (it.hasNext()) {
            WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
            throw null;
        }
    }

    @Override // com.android.dx.dex.file.Section
    public Collection items() {
        return this.callSiteIds.values();
    }
}
