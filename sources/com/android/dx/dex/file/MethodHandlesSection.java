package com.android.dx.dex.file;

import com.android.dx.rop.cst.CstMethodHandle;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

/* JADX INFO: loaded from: classes4.dex */
public final class MethodHandlesSection extends UniformItemSection {
    private final TreeMap methodHandles;

    public MethodHandlesSection(DexFile dexFile) {
        super("method_handles", dexFile, 8);
        this.methodHandles = new TreeMap();
    }

    @Override // com.android.dx.dex.file.UniformItemSection
    protected void orderItems() {
        Iterator it = this.methodHandles.values().iterator();
        int i = 0;
        while (it.hasNext()) {
            ((MethodHandleItem) it.next()).setIndex(i);
            i++;
        }
    }

    @Override // com.android.dx.dex.file.Section
    public Collection items() {
        return this.methodHandles.values();
    }

    int indexOf(CstMethodHandle cstMethodHandle) {
        return ((MethodHandleItem) this.methodHandles.get(cstMethodHandle)).getIndex();
    }
}
