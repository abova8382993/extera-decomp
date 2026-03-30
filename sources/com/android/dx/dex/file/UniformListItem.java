package com.android.dx.dex.file;

import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public final class UniformListItem extends OffsettedItem {
    private final ItemType itemType;
    private final List items;

    public UniformListItem(ItemType itemType, List list) {
        super(getAlignment(list), writeSize(list));
        if (itemType == null) {
            throw new NullPointerException("itemType == null");
        }
        this.items = list;
        this.itemType = itemType;
    }

    private static int getAlignment(List list) {
        try {
            return Math.max(4, ((OffsettedItem) list.get(0)).getAlignment());
        } catch (IndexOutOfBoundsException unused) {
            throw new IllegalArgumentException("items.size() == 0");
        } catch (NullPointerException unused2) {
            throw new NullPointerException("items == null");
        }
    }

    private static int writeSize(List list) {
        return (list.size() * ((OffsettedItem) list.get(0)).writeSize()) + getAlignment(list);
    }

    @Override // com.android.dx.dex.file.Item
    public ItemType itemType() {
        return this.itemType;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(100);
        sb.append(UniformListItem.class.getName());
        sb.append(this.items);
        return sb.toString();
    }

    @Override // com.android.dx.dex.file.Item
    public void addContents(DexFile dexFile) {
        Iterator it = this.items.iterator();
        while (it.hasNext()) {
            ((OffsettedItem) it.next()).addContents(dexFile);
        }
    }

    @Override // com.android.dx.dex.file.OffsettedItem
    public final String toHuman() {
        StringBuilder sb = new StringBuilder(100);
        sb.append("{");
        boolean z = true;
        for (OffsettedItem offsettedItem : this.items) {
            if (z) {
                z = false;
            } else {
                sb.append(", ");
            }
            sb.append(offsettedItem.toHuman());
        }
        sb.append("}");
        return sb.toString();
    }

    @Override // com.android.dx.dex.file.OffsettedItem
    protected void place0(Section section, int i) {
        int iHeaderSize = i + headerSize();
        boolean z = true;
        int i2 = -1;
        int alignment = -1;
        for (OffsettedItem offsettedItem : this.items) {
            int iWriteSize = offsettedItem.writeSize();
            if (z) {
                alignment = offsettedItem.getAlignment();
                z = false;
                i2 = iWriteSize;
            } else {
                if (iWriteSize != i2) {
                    throw new UnsupportedOperationException("item size mismatch");
                }
                if (offsettedItem.getAlignment() != alignment) {
                    throw new UnsupportedOperationException("item alignment mismatch");
                }
            }
            iHeaderSize = offsettedItem.place(section, iHeaderSize) + iWriteSize;
        }
    }

    @Override // com.android.dx.dex.file.OffsettedItem
    protected void writeTo0(DexFile dexFile, AnnotatedOutput annotatedOutput) {
        int size = this.items.size();
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(0, offsetString() + " " + typeName());
            StringBuilder sb = new StringBuilder();
            sb.append("  size: ");
            sb.append(Hex.u4(size));
            annotatedOutput.annotate(4, sb.toString());
        }
        annotatedOutput.writeInt(size);
        Iterator it = this.items.iterator();
        while (it.hasNext()) {
            ((OffsettedItem) it.next()).writeTo(dexFile, annotatedOutput);
        }
    }

    private int headerSize() {
        return getAlignment();
    }
}
