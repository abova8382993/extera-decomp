package com.android.dx.dex.file;

import com.android.dx.util.AnnotatedOutput;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: loaded from: classes4.dex */
public abstract class UniformItemSection extends Section {
    protected abstract void orderItems();

    public UniformItemSection(String str, DexFile dexFile, int i) {
        super(str, dexFile, i);
    }

    @Override // com.android.dx.dex.file.Section
    public final int writeSize() {
        Collection collectionItems = items();
        int size = collectionItems.size();
        if (size == 0) {
            return 0;
        }
        return size * ((Item) collectionItems.iterator().next()).writeSize();
    }

    @Override // com.android.dx.dex.file.Section
    protected final void prepare0() {
        DexFile file = getFile();
        orderItems();
        Iterator it = items().iterator();
        while (it.hasNext()) {
            ((Item) it.next()).addContents(file);
        }
    }

    @Override // com.android.dx.dex.file.Section
    protected final void writeTo0(AnnotatedOutput annotatedOutput) {
        DexFile file = getFile();
        int alignment = getAlignment();
        Iterator it = items().iterator();
        while (it.hasNext()) {
            ((Item) it.next()).writeTo(file, annotatedOutput);
            annotatedOutput.alignTo(alignment);
        }
    }

    @Override // com.android.dx.dex.file.Section
    public final int getAbsoluteItemOffset(Item item) {
        IndexedItem indexedItem = (IndexedItem) item;
        return getAbsoluteOffset(indexedItem.getIndex() * indexedItem.writeSize());
    }
}
