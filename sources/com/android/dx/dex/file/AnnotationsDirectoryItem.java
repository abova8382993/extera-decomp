package com.android.dx.dex.file;

import androidx.appcompat.app.WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/* JADX INFO: loaded from: classes4.dex */
public final class AnnotationsDirectoryItem extends OffsettedItem {
    private ArrayList fieldAnnotations;
    private ArrayList methodAnnotations;
    private ArrayList parameterAnnotations;

    public int hashCode() {
        return 0;
    }

    public boolean isInternable() {
        return false;
    }

    public AnnotationsDirectoryItem() {
        super(4, -1);
        this.fieldAnnotations = null;
        this.methodAnnotations = null;
        this.parameterAnnotations = null;
    }

    @Override // com.android.dx.dex.file.Item
    public ItemType itemType() {
        return ItemType.TYPE_ANNOTATIONS_DIRECTORY_ITEM;
    }

    public boolean isEmpty() {
        return this.fieldAnnotations == null && this.methodAnnotations == null && this.parameterAnnotations == null;
    }

    @Override // com.android.dx.dex.file.OffsettedItem
    public int compareTo0(OffsettedItem offsettedItem) {
        if (!isInternable()) {
            throw new UnsupportedOperationException("uninternable instance");
        }
        ((AnnotationsDirectoryItem) offsettedItem).getClass();
        throw null;
    }

    @Override // com.android.dx.dex.file.Item
    public void addContents(DexFile dexFile) {
        dexFile.getWordData();
        ArrayList arrayList = this.fieldAnnotations;
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            if (it.hasNext()) {
                WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
                throw null;
            }
        }
        ArrayList arrayList2 = this.methodAnnotations;
        if (arrayList2 != null) {
            Iterator it2 = arrayList2.iterator();
            if (it2.hasNext()) {
                WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it2.next());
                throw null;
            }
        }
        ArrayList arrayList3 = this.parameterAnnotations;
        if (arrayList3 != null) {
            Iterator it3 = arrayList3.iterator();
            if (it3.hasNext()) {
                WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it3.next());
                throw null;
            }
        }
    }

    @Override // com.android.dx.dex.file.OffsettedItem
    public String toHuman() {
        throw new RuntimeException("unsupported");
    }

    @Override // com.android.dx.dex.file.OffsettedItem
    protected void place0(Section section, int i) {
        setWriteSize(((listSize(this.fieldAnnotations) + listSize(this.methodAnnotations) + listSize(this.parameterAnnotations)) * 8) + 16);
    }

    @Override // com.android.dx.dex.file.OffsettedItem
    protected void writeTo0(DexFile dexFile, AnnotatedOutput annotatedOutput) {
        boolean zAnnotates = annotatedOutput.annotates();
        int absoluteOffsetOr0 = OffsettedItem.getAbsoluteOffsetOr0(null);
        int iListSize = listSize(this.fieldAnnotations);
        int iListSize2 = listSize(this.methodAnnotations);
        int iListSize3 = listSize(this.parameterAnnotations);
        if (zAnnotates) {
            annotatedOutput.annotate(0, offsetString() + " annotations directory");
            annotatedOutput.annotate(4, "  class_annotations_off: " + Hex.u4(absoluteOffsetOr0));
            annotatedOutput.annotate(4, "  fields_size:           " + Hex.u4(iListSize));
            annotatedOutput.annotate(4, "  methods_size:          " + Hex.u4(iListSize2));
            annotatedOutput.annotate(4, "  parameters_size:       " + Hex.u4(iListSize3));
        }
        annotatedOutput.writeInt(absoluteOffsetOr0);
        annotatedOutput.writeInt(iListSize);
        annotatedOutput.writeInt(iListSize2);
        annotatedOutput.writeInt(iListSize3);
        if (iListSize != 0) {
            Collections.sort(this.fieldAnnotations);
            if (zAnnotates) {
                annotatedOutput.annotate(0, "  fields:");
            }
            Iterator it = this.fieldAnnotations.iterator();
            if (it.hasNext()) {
                WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
                throw null;
            }
        }
        if (iListSize2 != 0) {
            Collections.sort(this.methodAnnotations);
            if (zAnnotates) {
                annotatedOutput.annotate(0, "  methods:");
            }
            Iterator it2 = this.methodAnnotations.iterator();
            if (it2.hasNext()) {
                WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it2.next());
                throw null;
            }
        }
        if (iListSize3 != 0) {
            Collections.sort(this.parameterAnnotations);
            if (zAnnotates) {
                annotatedOutput.annotate(0, "  parameters:");
            }
            Iterator it3 = this.parameterAnnotations.iterator();
            if (it3.hasNext()) {
                WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it3.next());
                throw null;
            }
        }
    }

    private static int listSize(ArrayList arrayList) {
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }
}
