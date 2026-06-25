package com.android.p006dx;

import com.android.p006dx.rop.code.Rop;
import com.android.p006dx.rop.code.Rops;
import com.android.p006dx.rop.type.TypeList;

/* JADX INFO: loaded from: classes4.dex */
public enum Comparison {
    LT { // from class: com.android.dx.Comparison.1
        @Override // com.android.p006dx.Comparison
        public Rop rop(TypeList typeList) {
            return Rops.opIfLt(typeList);
        }
    },
    LE { // from class: com.android.dx.Comparison.2
        @Override // com.android.p006dx.Comparison
        public Rop rop(TypeList typeList) {
            return Rops.opIfLe(typeList);
        }
    },
    EQ { // from class: com.android.dx.Comparison.3
        @Override // com.android.p006dx.Comparison
        public Rop rop(TypeList typeList) {
            return Rops.opIfEq(typeList);
        }
    },
    GE { // from class: com.android.dx.Comparison.4
        @Override // com.android.p006dx.Comparison
        public Rop rop(TypeList typeList) {
            return Rops.opIfGe(typeList);
        }
    },
    GT { // from class: com.android.dx.Comparison.5
        @Override // com.android.p006dx.Comparison
        public Rop rop(TypeList typeList) {
            return Rops.opIfGt(typeList);
        }
    },
    NE { // from class: com.android.dx.Comparison.6
        @Override // com.android.p006dx.Comparison
        public Rop rop(TypeList typeList) {
            return Rops.opIfNe(typeList);
        }
    };

    public abstract Rop rop(TypeList typeList);
}
