package com.android.dx.dex.file;

import com.android.dex.util.ExceptionWithContext;
import com.android.dx.util.AnnotatedOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/* JADX INFO: loaded from: classes4.dex */
public final class MixedItemSection extends Section {
    private static final Comparator TYPE_SORTER = new Comparator() { // from class: com.android.dx.dex.file.MixedItemSection.1
        @Override // java.util.Comparator
        public int compare(OffsettedItem offsettedItem, OffsettedItem offsettedItem2) {
            return offsettedItem.itemType().compareTo(offsettedItem2.itemType());
        }
    };
    private final HashMap interns;
    private final ArrayList items;
    private final SortType sort;
    private int writeSize;

    enum SortType {
        NONE,
        TYPE,
        INSTANCE
    }

    public MixedItemSection(String str, DexFile dexFile, int i, SortType sortType) {
        super(str, dexFile, i);
        this.items = new ArrayList(100);
        this.interns = new HashMap(100);
        this.sort = sortType;
        this.writeSize = -1;
    }

    @Override // com.android.dx.dex.file.Section
    public Collection items() {
        return this.items;
    }

    @Override // com.android.dx.dex.file.Section
    public int writeSize() {
        throwIfNotPrepared();
        return this.writeSize;
    }

    @Override // com.android.dx.dex.file.Section
    public int getAbsoluteItemOffset(Item item) {
        return ((OffsettedItem) item).getAbsoluteOffset();
    }

    public void add(OffsettedItem offsettedItem) {
        throwIfPrepared();
        try {
            if (offsettedItem.getAlignment() > getAlignment()) {
                throw new IllegalArgumentException("incompatible item alignment");
            }
            this.items.add(offsettedItem);
        } catch (NullPointerException unused) {
            throw new NullPointerException("item == null");
        }
    }

    public synchronized OffsettedItem intern(OffsettedItem offsettedItem) {
        throwIfPrepared();
        OffsettedItem offsettedItem2 = (OffsettedItem) this.interns.get(offsettedItem);
        if (offsettedItem2 != null) {
            return offsettedItem2;
        }
        add(offsettedItem);
        this.interns.put(offsettedItem, offsettedItem);
        return offsettedItem;
    }

    public void writeIndexAnnotation(AnnotatedOutput annotatedOutput, ItemType itemType, String str) {
        throwIfNotPrepared();
        TreeMap treeMap = new TreeMap();
        ArrayList arrayList = this.items;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            OffsettedItem offsettedItem = (OffsettedItem) obj;
            if (offsettedItem.itemType() == itemType) {
                treeMap.put(offsettedItem.toHuman(), offsettedItem);
            }
        }
        if (treeMap.size() == 0) {
            return;
        }
        annotatedOutput.annotate(0, str);
        for (Map.Entry entry : treeMap.entrySet()) {
            annotatedOutput.annotate(0, ((OffsettedItem) entry.getValue()).offsetString() + ' ' + ((String) entry.getKey()) + '\n');
        }
    }

    @Override // com.android.dx.dex.file.Section
    protected void prepare0() {
        DexFile file = getFile();
        int i = 0;
        while (true) {
            int size = this.items.size();
            if (i >= size) {
                return;
            }
            while (i < size) {
                ((OffsettedItem) this.items.get(i)).addContents(file);
                i++;
            }
        }
    }

    public void placeItems() {
        throwIfNotPrepared();
        int i = AnonymousClass2.$SwitchMap$com$android$dx$dex$file$MixedItemSection$SortType[this.sort.ordinal()];
        if (i == 1) {
            Collections.sort(this.items);
        } else if (i == 2) {
            Collections.sort(this.items, TYPE_SORTER);
        }
        int size = this.items.size();
        int iWriteSize = 0;
        for (int i2 = 0; i2 < size; i2++) {
            OffsettedItem offsettedItem = (OffsettedItem) this.items.get(i2);
            try {
                int iPlace = offsettedItem.place(this, iWriteSize);
                if (iPlace < iWriteSize) {
                    throw new RuntimeException("bogus place() result for " + offsettedItem);
                }
                iWriteSize = offsettedItem.writeSize() + iPlace;
            } catch (RuntimeException e) {
                throw ExceptionWithContext.withContext(e, "...while placing " + offsettedItem);
            }
        }
        this.writeSize = iWriteSize;
    }

    /* JADX INFO: renamed from: com.android.dx.dex.file.MixedItemSection$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$android$dx$dex$file$MixedItemSection$SortType;

        static {
            int[] iArr = new int[SortType.values().length];
            $SwitchMap$com$android$dx$dex$file$MixedItemSection$SortType = iArr;
            try {
                iArr[SortType.INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$dx$dex$file$MixedItemSection$SortType[SortType.TYPE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @Override // com.android.dx.dex.file.Section
    protected void writeTo0(AnnotatedOutput annotatedOutput) {
        boolean zAnnotates = annotatedOutput.annotates();
        DexFile file = getFile();
        ArrayList arrayList = this.items;
        int size = arrayList.size();
        boolean z = true;
        int iWriteSize = 0;
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            OffsettedItem offsettedItem = (OffsettedItem) obj;
            if (zAnnotates) {
                if (z) {
                    z = false;
                } else {
                    annotatedOutput.annotate(0, "\n");
                }
            }
            int alignment = offsettedItem.getAlignment() - 1;
            int i2 = (~alignment) & (iWriteSize + alignment);
            if (iWriteSize != i2) {
                annotatedOutput.writeZeroes(i2 - iWriteSize);
                iWriteSize = i2;
            }
            offsettedItem.writeTo(file, annotatedOutput);
            iWriteSize += offsettedItem.writeSize();
        }
        if (iWriteSize != this.writeSize) {
            throw new RuntimeException("output size mismatch");
        }
    }
}
