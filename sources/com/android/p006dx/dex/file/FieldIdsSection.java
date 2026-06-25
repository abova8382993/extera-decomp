package com.android.p006dx.dex.file;

import com.android.p006dx.rop.cst.Constant;
import com.android.p006dx.rop.cst.CstFieldRef;
import com.android.p006dx.util.AnnotatedOutput;
import com.android.p006dx.util.Hex;
import java.util.Collection;
import java.util.TreeMap;
import p005c.g$$ExternalSyntheticBUOutline1;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
public final class FieldIdsSection extends MemberIdsSection {
    private final TreeMap<CstFieldRef, FieldIdItem> fieldIds;

    public FieldIdsSection(DexFile dexFile) {
        super("field_ids", dexFile);
        this.fieldIds = new TreeMap<>();
    }

    @Override // com.android.p006dx.dex.file.Section
    public Collection<? extends Item> items() {
        return this.fieldIds.values();
    }

    @Override // com.android.p006dx.dex.file.UniformItemSection
    public IndexedItem get(Constant constant) {
        if (constant == null) {
            g$$ExternalSyntheticBUOutline2.m208m("cst == null");
            return null;
        }
        throwIfNotPrepared();
        FieldIdItem fieldIdItem = this.fieldIds.get((CstFieldRef) constant);
        if (fieldIdItem != null) {
            return fieldIdItem;
        }
        g$$ExternalSyntheticBUOutline1.m207m("not found");
        return null;
    }

    public void writeHeaderPart(AnnotatedOutput annotatedOutput) {
        throwIfNotPrepared();
        int size = this.fieldIds.size();
        int fileOffset = size == 0 ? 0 : getFileOffset();
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(4, "field_ids_size:  " + Hex.m233u4(size));
            annotatedOutput.annotate(4, "field_ids_off:   " + Hex.m233u4(fileOffset));
        }
        annotatedOutput.writeInt(size);
        annotatedOutput.writeInt(fileOffset);
    }

    public synchronized FieldIdItem intern(CstFieldRef cstFieldRef) {
        FieldIdItem fieldIdItem;
        if (cstFieldRef == null) {
            throw new NullPointerException("field == null");
        }
        throwIfPrepared();
        fieldIdItem = this.fieldIds.get(cstFieldRef);
        if (fieldIdItem == null) {
            fieldIdItem = new FieldIdItem(cstFieldRef);
            this.fieldIds.put(cstFieldRef, fieldIdItem);
        }
        return fieldIdItem;
    }

    public int indexOf(CstFieldRef cstFieldRef) {
        if (cstFieldRef == null) {
            g$$ExternalSyntheticBUOutline2.m208m("ref == null");
            return 0;
        }
        throwIfNotPrepared();
        FieldIdItem fieldIdItem = this.fieldIds.get(cstFieldRef);
        if (fieldIdItem == null) {
            g$$ExternalSyntheticBUOutline1.m207m("not found");
            return 0;
        }
        return fieldIdItem.getIndex();
    }
}
