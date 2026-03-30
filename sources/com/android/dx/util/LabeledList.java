package com.android.dx.util;

/* JADX INFO: loaded from: classes4.dex */
public abstract class LabeledList extends FixedSizeList {
    private final IntList labelToIndex;

    public LabeledList(int i) {
        super(i);
        this.labelToIndex = new IntList(i);
    }

    public final int getMaxLabel() {
        int size = this.labelToIndex.size() - 1;
        while (size >= 0 && this.labelToIndex.get(size) < 0) {
            size--;
        }
        int i = size + 1;
        this.labelToIndex.shrink(i);
        return i;
    }

    private void removeLabel(int i) {
        this.labelToIndex.set(i, -1);
    }

    private void addLabelIndex(int i, int i2) {
        int size = this.labelToIndex.size();
        for (int i3 = 0; i3 <= i - size; i3++) {
            this.labelToIndex.add(-1);
        }
        this.labelToIndex.set(i, i2);
    }

    public final int indexOfLabel(int i) {
        if (i >= this.labelToIndex.size()) {
            return -1;
        }
        return this.labelToIndex.get(i);
    }

    protected void set(int i, LabeledItem labeledItem) {
        LabeledItem labeledItem2 = (LabeledItem) getOrNull0(i);
        set0(i, labeledItem);
        if (labeledItem2 != null) {
            removeLabel(labeledItem2.getLabel());
        }
        if (labeledItem != null) {
            addLabelIndex(labeledItem.getLabel(), i);
        }
    }
}
