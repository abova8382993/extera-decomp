package com.android.p003dx.p004cf.code;

import com.android.dex.util.ExceptionWithContext;
import com.android.p003dx.rop.code.RegisterSpec;
import com.android.p003dx.rop.type.Type;
import com.android.p003dx.rop.type.TypeBearer;
import com.android.p003dx.util.Hex;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class LocalsArraySet extends LocalsArray {
    private final OneLocalsArray primary;
    private final ArrayList<LocalsArray> secondaries;

    public LocalsArraySet(int i) {
        super(i != 0);
        this.primary = new OneLocalsArray(i);
        this.secondaries = new ArrayList<>();
    }

    public LocalsArraySet(OneLocalsArray oneLocalsArray, ArrayList<LocalsArray> arrayList) {
        super(oneLocalsArray.getMaxLocals() > 0);
        this.primary = oneLocalsArray;
        this.secondaries = arrayList;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private LocalsArraySet(LocalsArraySet localsArraySet) {
        super(localsArraySet.getMaxLocals() > 0);
        this.primary = localsArraySet.primary.copy();
        this.secondaries = new ArrayList<>(localsArraySet.secondaries.size());
        int size = localsArraySet.secondaries.size();
        for (int i = 0; i < size; i++) {
            LocalsArray localsArray = localsArraySet.secondaries.get(i);
            if (localsArray == null) {
                this.secondaries.add(null);
            } else {
                this.secondaries.add(localsArray.copy());
            }
        }
    }

    @Override // com.android.p003dx.util.MutabilityControl
    public void setImmutable() {
        this.primary.setImmutable();
        ArrayList<LocalsArray> arrayList = this.secondaries;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            LocalsArray localsArray = arrayList.get(i);
            i++;
            LocalsArray localsArray2 = localsArray;
            if (localsArray2 != null) {
                localsArray2.setImmutable();
            }
        }
        super.setImmutable();
    }

    @Override // com.android.p003dx.p004cf.code.LocalsArray
    public LocalsArray copy() {
        return new LocalsArraySet(this);
    }

    @Override // com.android.p003dx.p004cf.code.LocalsArray
    public void annotate(ExceptionWithContext exceptionWithContext) {
        exceptionWithContext.addContext("(locals array set; primary)");
        this.primary.annotate(exceptionWithContext);
        int size = this.secondaries.size();
        for (int i = 0; i < size; i++) {
            LocalsArray localsArray = this.secondaries.get(i);
            if (localsArray != null) {
                exceptionWithContext.addContext("(locals array set: primary for caller " + Hex.m212u2(i) + ')');
                localsArray.getPrimary().annotate(exceptionWithContext);
            }
        }
    }

    @Override // com.android.p003dx.util.ToHuman
    public String toHuman() {
        StringBuilder sb = new StringBuilder();
        sb.append("(locals array set; primary)\n");
        sb.append(getPrimary().toHuman());
        sb.append('\n');
        int size = this.secondaries.size();
        for (int i = 0; i < size; i++) {
            LocalsArray localsArray = this.secondaries.get(i);
            if (localsArray != null) {
                sb.append("(locals array set: primary for caller " + Hex.m212u2(i) + ")\n");
                sb.append(localsArray.getPrimary().toHuman());
                sb.append('\n');
            }
        }
        return sb.toString();
    }

    @Override // com.android.p003dx.p004cf.code.LocalsArray
    public void makeInitialized(Type type) {
        if (this.primary.getMaxLocals() == 0) {
            return;
        }
        throwIfImmutable();
        this.primary.makeInitialized(type);
        ArrayList<LocalsArray> arrayList = this.secondaries;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            LocalsArray localsArray = arrayList.get(i);
            i++;
            LocalsArray localsArray2 = localsArray;
            if (localsArray2 != null) {
                localsArray2.makeInitialized(type);
            }
        }
    }

    @Override // com.android.p003dx.p004cf.code.LocalsArray
    public int getMaxLocals() {
        return this.primary.getMaxLocals();
    }

    @Override // com.android.p003dx.p004cf.code.LocalsArray
    public void set(int i, TypeBearer typeBearer) {
        throwIfImmutable();
        this.primary.set(i, typeBearer);
        ArrayList<LocalsArray> arrayList = this.secondaries;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            LocalsArray localsArray = arrayList.get(i2);
            i2++;
            LocalsArray localsArray2 = localsArray;
            if (localsArray2 != null) {
                localsArray2.set(i, typeBearer);
            }
        }
    }

    @Override // com.android.p003dx.p004cf.code.LocalsArray
    public void set(RegisterSpec registerSpec) {
        set(registerSpec.getReg(), registerSpec);
    }

    @Override // com.android.p003dx.p004cf.code.LocalsArray
    public void invalidate(int i) {
        throwIfImmutable();
        this.primary.invalidate(i);
        ArrayList<LocalsArray> arrayList = this.secondaries;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            LocalsArray localsArray = arrayList.get(i2);
            i2++;
            LocalsArray localsArray2 = localsArray;
            if (localsArray2 != null) {
                localsArray2.invalidate(i);
            }
        }
    }

    @Override // com.android.p003dx.p004cf.code.LocalsArray
    public TypeBearer getOrNull(int i) {
        return this.primary.getOrNull(i);
    }

    @Override // com.android.p003dx.p004cf.code.LocalsArray
    public TypeBearer get(int i) {
        return this.primary.get(i);
    }

    @Override // com.android.p003dx.p004cf.code.LocalsArray
    public TypeBearer getCategory1(int i) {
        return this.primary.getCategory1(i);
    }

    @Override // com.android.p003dx.p004cf.code.LocalsArray
    public TypeBearer getCategory2(int i) {
        return this.primary.getCategory2(i);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0046  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.android.p003dx.p004cf.code.LocalsArraySet mergeWithSet(com.android.p003dx.p004cf.code.LocalsArraySet r14) {
        /*
            r13 = this;
            com.android.dx.cf.code.OneLocalsArray r0 = r13.primary
            com.android.dx.cf.code.OneLocalsArray r1 = r14.getPrimary()
            com.android.dx.cf.code.OneLocalsArray r0 = r0.merge(r1)
            java.util.ArrayList<com.android.dx.cf.code.LocalsArray> r1 = r13.secondaries
            int r1 = r1.size()
            java.util.ArrayList<com.android.dx.cf.code.LocalsArray> r2 = r14.secondaries
            int r2 = r2.size()
            int r3 = java.lang.Math.max(r1, r2)
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>(r3)
            r5 = 0
            r6 = r5
            r7 = r6
        L22:
            if (r6 >= r3) goto L74
            r8 = 0
            if (r6 >= r1) goto L30
            java.util.ArrayList<com.android.dx.cf.code.LocalsArray> r9 = r13.secondaries
            java.lang.Object r9 = r9.get(r6)
            com.android.dx.cf.code.LocalsArray r9 = (com.android.p003dx.p004cf.code.LocalsArray) r9
            goto L31
        L30:
            r9 = r8
        L31:
            if (r6 >= r2) goto L3c
            java.util.ArrayList<com.android.dx.cf.code.LocalsArray> r10 = r14.secondaries
            java.lang.Object r10 = r10.get(r6)
            com.android.dx.cf.code.LocalsArray r10 = (com.android.p003dx.p004cf.code.LocalsArray) r10
            goto L3d
        L3c:
            r10 = r8
        L3d:
            if (r9 != r10) goto L40
            goto L46
        L40:
            if (r9 != 0) goto L44
            r8 = r10
            goto L66
        L44:
            if (r10 != 0) goto L48
        L46:
            r8 = r9
            goto L66
        L48:
            com.android.dx.cf.code.LocalsArray r8 = r9.merge(r10)     // Catch: com.android.p003dx.p004cf.code.SimException -> L4d
            goto L66
        L4d:
            r10 = move-exception
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "Merging locals set for caller block "
            r11.append(r12)
            java.lang.String r12 = com.android.p003dx.util.Hex.m212u2(r6)
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            r10.addContext(r11)
        L66:
            if (r7 != 0) goto L6d
            if (r9 == r8) goto L6b
            goto L6d
        L6b:
            r7 = r5
            goto L6e
        L6d:
            r7 = 1
        L6e:
            r4.add(r8)
            int r6 = r6 + 1
            goto L22
        L74:
            com.android.dx.cf.code.OneLocalsArray r14 = r13.primary
            if (r14 != r0) goto L7b
            if (r7 != 0) goto L7b
            return r13
        L7b:
            com.android.dx.cf.code.LocalsArraySet r14 = new com.android.dx.cf.code.LocalsArraySet
            r14.<init>(r0, r4)
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p003dx.p004cf.code.LocalsArraySet.mergeWithSet(com.android.dx.cf.code.LocalsArraySet):com.android.dx.cf.code.LocalsArraySet");
    }

    private LocalsArraySet mergeWithOne(OneLocalsArray oneLocalsArray) {
        LocalsArray localsArrayMerge;
        OneLocalsArray oneLocalsArrayMerge = this.primary.merge(oneLocalsArray.getPrimary());
        ArrayList arrayList = new ArrayList(this.secondaries.size());
        int size = this.secondaries.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            LocalsArray localsArray = this.secondaries.get(i);
            if (localsArray != null) {
                try {
                    localsArrayMerge = localsArray.merge(oneLocalsArray);
                } catch (SimException e) {
                    e.addContext("Merging one locals against caller block " + Hex.m212u2(i));
                    localsArrayMerge = null;
                }
            } else {
                localsArrayMerge = null;
            }
            z = z || localsArray != localsArrayMerge;
            arrayList.add(localsArrayMerge);
        }
        return (this.primary != oneLocalsArrayMerge || z) ? new LocalsArraySet(oneLocalsArrayMerge, arrayList) : this;
    }

    @Override // com.android.p003dx.p004cf.code.LocalsArray
    public LocalsArraySet merge(LocalsArray localsArray) {
        LocalsArraySet localsArraySetMergeWithOne;
        try {
            if (localsArray instanceof LocalsArraySet) {
                localsArraySetMergeWithOne = mergeWithSet((LocalsArraySet) localsArray);
            } else {
                localsArraySetMergeWithOne = mergeWithOne((OneLocalsArray) localsArray);
            }
            localsArraySetMergeWithOne.setImmutable();
            return localsArraySetMergeWithOne;
        } catch (SimException e) {
            e.addContext("underlay locals:");
            annotate(e);
            e.addContext("overlay locals:");
            localsArray.annotate(e);
            throw e;
        }
    }

    private LocalsArray getSecondaryForLabel(int i) {
        if (i >= this.secondaries.size()) {
            return null;
        }
        return this.secondaries.get(i);
    }

    @Override // com.android.p003dx.p004cf.code.LocalsArray
    public LocalsArraySet mergeWithSubroutineCaller(LocalsArray localsArray, int i) {
        LocalsArray localsArray2;
        LocalsArray secondaryForLabel = getSecondaryForLabel(i);
        OneLocalsArray oneLocalsArrayMerge = this.primary.merge(localsArray.getPrimary());
        if (secondaryForLabel == localsArray) {
            localsArray = secondaryForLabel;
        } else if (secondaryForLabel != null) {
            localsArray = secondaryForLabel.merge(localsArray);
        }
        if (localsArray == secondaryForLabel && oneLocalsArrayMerge == this.primary) {
            return this;
        }
        int size = this.secondaries.size();
        int iMax = Math.max(i + 1, size);
        ArrayList arrayList = new ArrayList(iMax);
        int i2 = 0;
        OneLocalsArray oneLocalsArrayMerge2 = null;
        while (i2 < iMax) {
            if (i2 == i) {
                localsArray2 = localsArray;
            } else {
                localsArray2 = i2 < size ? this.secondaries.get(i2) : null;
            }
            if (localsArray2 != null) {
                if (oneLocalsArrayMerge2 == null) {
                    oneLocalsArrayMerge2 = localsArray2.getPrimary();
                } else {
                    oneLocalsArrayMerge2 = oneLocalsArrayMerge2.merge(localsArray2.getPrimary());
                }
            }
            arrayList.add(localsArray2);
            i2++;
        }
        LocalsArraySet localsArraySet = new LocalsArraySet(oneLocalsArrayMerge2, arrayList);
        localsArraySet.setImmutable();
        return localsArraySet;
    }

    public LocalsArray subArrayForLabel(int i) {
        return getSecondaryForLabel(i);
    }

    @Override // com.android.p003dx.p004cf.code.LocalsArray
    protected OneLocalsArray getPrimary() {
        return this.primary;
    }
}
