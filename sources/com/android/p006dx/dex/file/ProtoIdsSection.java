package com.android.p006dx.dex.file;

import com.android.p006dx.rop.cst.Constant;
import com.android.p006dx.rop.cst.CstProtoRef;
import com.android.p006dx.rop.type.Prototype;
import com.android.p006dx.util.AnnotatedOutput;
import com.android.p006dx.util.Hex;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;
import okio.ByteString$$ExternalSyntheticBUOutline0;
import p005c.g$$ExternalSyntheticBUOutline1;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
public final class ProtoIdsSection extends UniformItemSection {
    private final TreeMap<Prototype, ProtoIdItem> protoIds;

    public ProtoIdsSection(DexFile dexFile) {
        super("proto_ids", dexFile, 4);
        this.protoIds = new TreeMap<>();
    }

    @Override // com.android.p006dx.dex.file.Section
    public Collection<? extends Item> items() {
        return this.protoIds.values();
    }

    @Override // com.android.p006dx.dex.file.UniformItemSection
    public IndexedItem get(Constant constant) {
        if (constant == null) {
            g$$ExternalSyntheticBUOutline2.m208m("cst == null");
            return null;
        }
        if (!(constant instanceof CstProtoRef)) {
            g$$ExternalSyntheticBUOutline1.m207m("cst not instance of CstProtoRef");
            return null;
        }
        throwIfNotPrepared();
        ProtoIdItem protoIdItem = this.protoIds.get(((CstProtoRef) constant).getPrototype());
        if (protoIdItem != null) {
            return protoIdItem;
        }
        g$$ExternalSyntheticBUOutline1.m207m("not found");
        return null;
    }

    public void writeHeaderPart(AnnotatedOutput annotatedOutput) {
        throwIfNotPrepared();
        int size = this.protoIds.size();
        int fileOffset = size == 0 ? 0 : getFileOffset();
        if (size > 65536) {
            ByteString$$ExternalSyntheticBUOutline0.m979m("too many proto ids");
            return;
        }
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(4, "proto_ids_size:  " + Hex.m233u4(size));
            annotatedOutput.annotate(4, "proto_ids_off:   " + Hex.m233u4(fileOffset));
        }
        annotatedOutput.writeInt(size);
        annotatedOutput.writeInt(fileOffset);
    }

    public synchronized ProtoIdItem intern(Prototype prototype) {
        ProtoIdItem protoIdItem;
        if (prototype == null) {
            throw new NullPointerException("prototype == null");
        }
        throwIfPrepared();
        protoIdItem = this.protoIds.get(prototype);
        if (protoIdItem == null) {
            protoIdItem = new ProtoIdItem(prototype);
            this.protoIds.put(prototype, protoIdItem);
        }
        return protoIdItem;
    }

    public int indexOf(Prototype prototype) {
        if (prototype == null) {
            g$$ExternalSyntheticBUOutline2.m208m("prototype == null");
            return 0;
        }
        throwIfNotPrepared();
        ProtoIdItem protoIdItem = this.protoIds.get(prototype);
        if (protoIdItem == null) {
            g$$ExternalSyntheticBUOutline1.m207m("not found");
            return 0;
        }
        return protoIdItem.getIndex();
    }

    @Override // com.android.p006dx.dex.file.UniformItemSection
    public void orderItems() {
        Iterator<? extends Item> it = items().iterator();
        int i = 0;
        while (it.hasNext()) {
            ((ProtoIdItem) it.next()).setIndex(i);
            i++;
        }
    }
}
