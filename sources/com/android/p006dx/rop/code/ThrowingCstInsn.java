package com.android.p006dx.rop.code;

import com.android.p006dx.rop.code.Insn;
import com.android.p006dx.rop.cst.Constant;
import com.android.p006dx.rop.cst.CstString;
import com.android.p006dx.rop.type.Type;
import com.android.p006dx.rop.type.TypeList;
import com.sun.jna.IntegerType$$ExternalSyntheticBUOutline0;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
public final class ThrowingCstInsn extends CstInsn {
    private final TypeList catches;

    public ThrowingCstInsn(Rop rop, SourcePosition sourcePosition, RegisterSpecList registerSpecList, TypeList typeList, Constant constant) {
        super(rop, sourcePosition, null, registerSpecList, constant);
        if (rop.getBranchingness() != 6) {
            IntegerType$$ExternalSyntheticBUOutline0.m547m("opcode with invalid branchingness: ", rop.getBranchingness());
            throw null;
        }
        if (typeList == null) {
            g$$ExternalSyntheticBUOutline2.m208m("catches == null");
            throw null;
        }
        this.catches = typeList;
    }

    @Override // com.android.p006dx.rop.code.CstInsn, com.android.p006dx.rop.code.Insn
    public String getInlineString() {
        Constant constant = getConstant();
        String human = constant.toHuman();
        if (constant instanceof CstString) {
            human = ((CstString) constant).toQuoted();
        }
        return human + " " + ThrowingInsn.toCatchString(this.catches);
    }

    @Override // com.android.p006dx.rop.code.Insn
    public TypeList getCatches() {
        return this.catches;
    }

    @Override // com.android.p006dx.rop.code.Insn
    public void accept(Insn.Visitor visitor) {
        visitor.visitThrowingCstInsn(this);
    }

    @Override // com.android.p006dx.rop.code.Insn
    public Insn withAddedCatch(Type type) {
        return new ThrowingCstInsn(getOpcode(), getPosition(), getSources(), this.catches.withAddedType(type), getConstant());
    }

    @Override // com.android.p006dx.rop.code.Insn
    public Insn withRegisterOffset(int i) {
        return new ThrowingCstInsn(getOpcode(), getPosition(), getSources().withOffset(i), this.catches, getConstant());
    }

    @Override // com.android.p006dx.rop.code.Insn
    public Insn withNewRegisters(RegisterSpec registerSpec, RegisterSpecList registerSpecList) {
        return new ThrowingCstInsn(getOpcode(), getPosition(), registerSpecList, this.catches, getConstant());
    }
}
