package com.android.p006dx.rop.annotation;

import com.android.p006dx.util.FixedSizeList;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes4.dex */
public final class AnnotationsList extends FixedSizeList {
    public static final AnnotationsList EMPTY = new AnnotationsList(0);

    public static AnnotationsList combine(AnnotationsList annotationsList, AnnotationsList annotationsList2) {
        int size = annotationsList.size();
        if (size != annotationsList2.size()) {
            g$$ExternalSyntheticBUOutline1.m207m("list1.size() != list2.size()");
            return null;
        }
        AnnotationsList annotationsList3 = new AnnotationsList(size);
        for (int i = 0; i < size; i++) {
            annotationsList3.set(i, Annotations.combine(annotationsList.get(i), annotationsList2.get(i)));
        }
        annotationsList3.setImmutable();
        return annotationsList3;
    }

    public AnnotationsList(int i) {
        super(i);
    }

    public Annotations get(int i) {
        return (Annotations) get0(i);
    }

    public void set(int i, Annotations annotations) {
        annotations.throwIfMutable();
        set0(i, annotations);
    }
}
