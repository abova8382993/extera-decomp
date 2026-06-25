package com.android.p006dx.dex.file;

import com.android.p006dx.rop.cst.Constant;
import com.android.p006dx.rop.cst.CstType;
import com.android.p006dx.rop.type.Type;
import com.android.p006dx.rop.type.TypeList;
import com.android.p006dx.util.AnnotatedOutput;
import com.android.p006dx.util.Hex;
import com.sun.jna.Native$$ExternalSyntheticBUOutline5;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;
import org.mvel2.MVEL$$ExternalSyntheticBUOutline0;
import p005c.g$$ExternalSyntheticBUOutline1;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
public final class ClassDefsSection extends UniformItemSection {
    private final TreeMap<Type, ClassDefItem> classDefs;
    private ArrayList<ClassDefItem> orderedDefs;

    public ClassDefsSection(DexFile dexFile) {
        super("class_defs", dexFile, 4);
        this.classDefs = new TreeMap<>();
        this.orderedDefs = null;
    }

    @Override // com.android.p006dx.dex.file.Section
    public Collection<? extends Item> items() {
        ArrayList<ClassDefItem> arrayList = this.orderedDefs;
        return arrayList != null ? arrayList : this.classDefs.values();
    }

    @Override // com.android.p006dx.dex.file.UniformItemSection
    public IndexedItem get(Constant constant) {
        if (constant == null) {
            g$$ExternalSyntheticBUOutline2.m208m("cst == null");
            return null;
        }
        throwIfNotPrepared();
        ClassDefItem classDefItem = this.classDefs.get(((CstType) constant).getClassType());
        if (classDefItem != null) {
            return classDefItem;
        }
        g$$ExternalSyntheticBUOutline1.m207m("not found");
        return null;
    }

    public void writeHeaderPart(AnnotatedOutput annotatedOutput) {
        throwIfNotPrepared();
        int size = this.classDefs.size();
        int fileOffset = size == 0 ? 0 : getFileOffset();
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(4, "class_defs_size: " + Hex.m233u4(size));
            annotatedOutput.annotate(4, "class_defs_off:  " + Hex.m233u4(fileOffset));
        }
        annotatedOutput.writeInt(size);
        annotatedOutput.writeInt(fileOffset);
    }

    public void add(ClassDefItem classDefItem) {
        try {
            Type classType = classDefItem.getThisClass().getClassType();
            throwIfPrepared();
            if (this.classDefs.get(classType) != null) {
                Native$$ExternalSyntheticBUOutline5.m554m("already added: ", classType);
            } else {
                this.classDefs.put(classType, classDefItem);
            }
        } catch (NullPointerException unused) {
            g$$ExternalSyntheticBUOutline2.m208m("clazz == null");
        }
    }

    @Override // com.android.p006dx.dex.file.UniformItemSection
    public void orderItems() {
        int size = this.classDefs.size();
        this.orderedDefs = new ArrayList<>(size);
        Iterator<Type> it = this.classDefs.keySet().iterator();
        int iOrderItems0 = 0;
        while (it.hasNext()) {
            iOrderItems0 = orderItems0(it.next(), iOrderItems0, size - iOrderItems0);
        }
    }

    private int orderItems0(Type type, int i, int i2) {
        ClassDefItem classDefItem = this.classDefs.get(type);
        if (classDefItem == null || classDefItem.hasIndex()) {
            return i;
        }
        if (i2 < 0) {
            MVEL$$ExternalSyntheticBUOutline0.m1006m("class circularity with ", type);
            return 0;
        }
        int i3 = i2 - 1;
        CstType superclass = classDefItem.getSuperclass();
        if (superclass != null) {
            i = orderItems0(superclass.getClassType(), i, i3);
        }
        TypeList interfaces = classDefItem.getInterfaces();
        int size = interfaces.size();
        for (int i4 = 0; i4 < size; i4++) {
            i = orderItems0(interfaces.getType(i4), i, i3);
        }
        classDefItem.setIndex(i);
        this.orderedDefs.add(classDefItem);
        return i + 1;
    }
}
