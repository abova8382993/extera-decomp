package com.android.p006dx.dex.code;

import com.android.p006dx.rop.code.RegisterSpecList;
import com.android.p006dx.rop.code.SourcePosition;

/* JADX INFO: loaded from: classes4.dex */
public final class SimpleInsn extends FixedSizeInsn {
    @Override // com.android.p006dx.dex.code.DalvInsn
    public String argString() {
        return null;
    }

    public SimpleInsn(Dop dop, SourcePosition sourcePosition, RegisterSpecList registerSpecList) {
        super(dop, sourcePosition, registerSpecList);
    }

    @Override // com.android.p006dx.dex.code.DalvInsn
    public DalvInsn withOpcode(Dop dop) {
        return new SimpleInsn(dop, getPosition(), getRegisters());
    }

    @Override // com.android.p006dx.dex.code.DalvInsn
    public DalvInsn withRegisters(RegisterSpecList registerSpecList) {
        return new SimpleInsn(getOpcode(), getPosition(), registerSpecList);
    }
}
