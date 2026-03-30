package com.android.dx.dex.file;

import com.android.dex.DexIndexOverflowException;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.type.Type;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

/* JADX INFO: loaded from: classes4.dex */
public final class TypeIdsSection extends UniformItemSection {
    private final TreeMap typeIds;

    public TypeIdsSection(DexFile dexFile) {
        super("type_ids", dexFile, 4);
        this.typeIds = new TreeMap();
    }

    @Override // com.android.dx.dex.file.Section
    public Collection items() {
        return this.typeIds.values();
    }

    public IndexedItem get(Constant constant) {
        if (constant == null) {
            throw new NullPointerException("cst == null");
        }
        throwIfNotPrepared();
        IndexedItem indexedItem = (IndexedItem) this.typeIds.get(((CstType) constant).getClassType());
        if (indexedItem != null) {
            return indexedItem;
        }
        throw new IllegalArgumentException("not found: " + constant);
    }

    public void writeHeaderPart(AnnotatedOutput annotatedOutput) {
        throwIfNotPrepared();
        int size = this.typeIds.size();
        int fileOffset = size == 0 ? 0 : getFileOffset();
        if (size > 65536) {
            throw new DexIndexOverflowException(String.format("Too many type identifiers to fit in one dex file: %1$d; max is %2$d.%nYou may try using multi-dex. If multi-dex is enabled then the list of classes for the main dex list is too large.", Integer.valueOf(items().size()), 65536));
        }
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(4, "type_ids_size:   " + Hex.u4(size));
            annotatedOutput.annotate(4, "type_ids_off:    " + Hex.u4(fileOffset));
        }
        annotatedOutput.writeInt(size);
        annotatedOutput.writeInt(fileOffset);
    }

    public synchronized TypeIdItem intern(Type type) {
        TypeIdItem typeIdItem;
        if (type == null) {
            throw new NullPointerException("type == null");
        }
        throwIfPrepared();
        typeIdItem = (TypeIdItem) this.typeIds.get(type);
        if (typeIdItem == null) {
            typeIdItem = new TypeIdItem(new CstType(type));
            this.typeIds.put(type, typeIdItem);
        }
        return typeIdItem;
    }

    public synchronized TypeIdItem intern(CstType cstType) {
        TypeIdItem typeIdItem;
        if (cstType == null) {
            throw new NullPointerException("type == null");
        }
        throwIfPrepared();
        Type classType = cstType.getClassType();
        typeIdItem = (TypeIdItem) this.typeIds.get(classType);
        if (typeIdItem == null) {
            typeIdItem = new TypeIdItem(cstType);
            this.typeIds.put(classType, typeIdItem);
        }
        return typeIdItem;
    }

    public int indexOf(Type type) {
        if (type == null) {
            throw new NullPointerException("type == null");
        }
        throwIfNotPrepared();
        TypeIdItem typeIdItem = (TypeIdItem) this.typeIds.get(type);
        if (typeIdItem == null) {
            throw new IllegalArgumentException("not found: " + type);
        }
        return typeIdItem.getIndex();
    }

    public int indexOf(CstType cstType) {
        if (cstType == null) {
            throw new NullPointerException("type == null");
        }
        return indexOf(cstType.getClassType());
    }

    @Override // com.android.dx.dex.file.UniformItemSection
    protected void orderItems() {
        Iterator it = items().iterator();
        int i = 0;
        while (it.hasNext()) {
            ((TypeIdItem) it.next()).setIndex(i);
            i++;
        }
    }
}
