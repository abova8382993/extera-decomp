package com.android.p006dx.p007cf.attrib;

import com.android.p006dx.rop.cst.CstString;
import com.android.p006dx.rop.cst.CstType;
import com.android.p006dx.util.FixedSizeList;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
public final class InnerClassList extends FixedSizeList {
    public InnerClassList(int i) {
        super(i);
    }

    public Item get(int i) {
        return (Item) get0(i);
    }

    public void set(int i, CstType cstType, CstType cstType2, CstString cstString, int i2) {
        set0(i, new Item(cstType, cstType2, cstString, i2));
    }

    public static class Item {
        private final int accessFlags;
        private final CstType innerClass;
        private final CstString innerName;
        private final CstType outerClass;

        public Item(CstType cstType, CstType cstType2, CstString cstString, int i) {
            if (cstType == null) {
                g$$ExternalSyntheticBUOutline2.m208m("innerClass == null");
                throw null;
            }
            this.innerClass = cstType;
            this.outerClass = cstType2;
            this.innerName = cstString;
            this.accessFlags = i;
        }

        public CstType getInnerClass() {
            return this.innerClass;
        }

        public CstType getOuterClass() {
            return this.outerClass;
        }

        public CstString getInnerName() {
            return this.innerName;
        }

        public int getAccessFlags() {
            return this.accessFlags;
        }
    }
}
