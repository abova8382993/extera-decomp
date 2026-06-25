package com.android.p006dx;

import com.android.p006dx.rop.code.Rop;
import com.android.p006dx.rop.code.Rops;

/* JADX INFO: loaded from: classes4.dex */
public enum UnaryOp {
    NOT { // from class: com.android.dx.UnaryOp.1
        @Override // com.android.p006dx.UnaryOp
        public Rop rop(TypeId<?> typeId) {
            return Rops.opNot(typeId.ropType);
        }
    },
    NEGATE { // from class: com.android.dx.UnaryOp.2
        @Override // com.android.p006dx.UnaryOp
        public Rop rop(TypeId<?> typeId) {
            return Rops.opNeg(typeId.ropType);
        }
    };

    public abstract Rop rop(TypeId<?> typeId);
}
