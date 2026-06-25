package com.android.p006dx.dex.file;

import com.android.p006dx.rop.cst.Constant;
import com.android.p006dx.rop.cst.CstBaseMethodRef;
import com.android.p006dx.util.AnnotatedOutput;
import com.android.p006dx.util.Hex;
import java.util.Collection;
import java.util.TreeMap;
import p005c.g$$ExternalSyntheticBUOutline1;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
public final class MethodIdsSection extends MemberIdsSection {
    private final TreeMap<CstBaseMethodRef, MethodIdItem> methodIds;

    public MethodIdsSection(DexFile dexFile) {
        super("method_ids", dexFile);
        this.methodIds = new TreeMap<>();
    }

    @Override // com.android.p006dx.dex.file.Section
    public Collection<? extends Item> items() {
        return this.methodIds.values();
    }

    @Override // com.android.p006dx.dex.file.UniformItemSection
    public IndexedItem get(Constant constant) {
        if (constant == null) {
            g$$ExternalSyntheticBUOutline2.m208m("cst == null");
            return null;
        }
        throwIfNotPrepared();
        MethodIdItem methodIdItem = this.methodIds.get((CstBaseMethodRef) constant);
        if (methodIdItem != null) {
            return methodIdItem;
        }
        g$$ExternalSyntheticBUOutline1.m207m("not found");
        return null;
    }

    public void writeHeaderPart(AnnotatedOutput annotatedOutput) {
        throwIfNotPrepared();
        int size = this.methodIds.size();
        int fileOffset = size == 0 ? 0 : getFileOffset();
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(4, "method_ids_size: " + Hex.m233u4(size));
            annotatedOutput.annotate(4, "method_ids_off:  " + Hex.m233u4(fileOffset));
        }
        annotatedOutput.writeInt(size);
        annotatedOutput.writeInt(fileOffset);
    }

    public synchronized MethodIdItem intern(CstBaseMethodRef cstBaseMethodRef) {
        MethodIdItem methodIdItem;
        if (cstBaseMethodRef == null) {
            throw new NullPointerException("method == null");
        }
        throwIfPrepared();
        methodIdItem = this.methodIds.get(cstBaseMethodRef);
        if (methodIdItem == null) {
            methodIdItem = new MethodIdItem(cstBaseMethodRef);
            this.methodIds.put(cstBaseMethodRef, methodIdItem);
        }
        return methodIdItem;
    }

    public int indexOf(CstBaseMethodRef cstBaseMethodRef) {
        if (cstBaseMethodRef == null) {
            g$$ExternalSyntheticBUOutline2.m208m("ref == null");
            return 0;
        }
        throwIfNotPrepared();
        MethodIdItem methodIdItem = this.methodIds.get(cstBaseMethodRef);
        if (methodIdItem == null) {
            g$$ExternalSyntheticBUOutline1.m207m("not found");
            return 0;
        }
        return methodIdItem.getIndex();
    }
}
