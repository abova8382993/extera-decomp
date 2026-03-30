package com.android.dx.rop.code;

import com.android.dx.rop.code.Insn;
import com.android.dx.util.Hex;
import com.android.dx.util.IntList;
import com.android.dx.util.LabeledItem;
import com.android.dx.util.LabeledList;

/* JADX INFO: loaded from: classes4.dex */
public final class BasicBlockList extends LabeledList {
    private int regCount;

    public BasicBlockList(int i) {
        super(i);
        this.regCount = -1;
    }

    public BasicBlock get(int i) {
        return (BasicBlock) get0(i);
    }

    public void set(int i, BasicBlock basicBlock) {
        super.set(i, (LabeledItem) basicBlock);
        this.regCount = -1;
    }

    public int getRegCount() {
        if (this.regCount == -1) {
            RegCountVisitor regCountVisitor = new RegCountVisitor();
            forEachInsn(regCountVisitor);
            this.regCount = regCountVisitor.getRegCount();
        }
        return this.regCount;
    }

    public int getInstructionCount() {
        int size = size();
        int size2 = 0;
        for (int i = 0; i < size; i++) {
            BasicBlock basicBlock = (BasicBlock) getOrNull0(i);
            if (basicBlock != null) {
                size2 += basicBlock.getInsns().size();
            }
        }
        return size2;
    }

    public BasicBlock labelToBlock(int i) {
        int iIndexOfLabel = indexOfLabel(i);
        if (iIndexOfLabel < 0) {
            throw new IllegalArgumentException("no such label: " + Hex.u2(i));
        }
        return get(iIndexOfLabel);
    }

    public void forEachInsn(Insn.Visitor visitor) {
        int size = size();
        for (int i = 0; i < size; i++) {
            get(i).getInsns().forEach(visitor);
        }
    }

    public BasicBlock preferredSuccessorOf(BasicBlock basicBlock) {
        int primarySuccessor = basicBlock.getPrimarySuccessor();
        IntList successors = basicBlock.getSuccessors();
        int size = successors.size();
        if (size == 0) {
            return null;
        }
        if (size == 1) {
            return labelToBlock(successors.get(0));
        }
        if (primarySuccessor != -1) {
            return labelToBlock(primarySuccessor);
        }
        return labelToBlock(successors.get(0));
    }

    private static class RegCountVisitor implements Insn.Visitor {
        private int regCount = 0;

        public int getRegCount() {
            return this.regCount;
        }

        @Override // com.android.dx.rop.code.Insn.Visitor
        public void visitPlainInsn(PlainInsn plainInsn) {
            visit(plainInsn);
        }

        @Override // com.android.dx.rop.code.Insn.Visitor
        public void visitPlainCstInsn(PlainCstInsn plainCstInsn) {
            visit(plainCstInsn);
        }

        @Override // com.android.dx.rop.code.Insn.Visitor
        public void visitThrowingCstInsn(ThrowingCstInsn throwingCstInsn) {
            visit(throwingCstInsn);
        }

        @Override // com.android.dx.rop.code.Insn.Visitor
        public void visitThrowingInsn(ThrowingInsn throwingInsn) {
            visit(throwingInsn);
        }

        private void visit(Insn insn) {
            RegisterSpec result = insn.getResult();
            if (result != null) {
                processReg(result);
            }
            RegisterSpecList sources = insn.getSources();
            int size = sources.size();
            for (int i = 0; i < size; i++) {
                processReg(sources.get(i));
            }
        }

        private void processReg(RegisterSpec registerSpec) {
            int nextReg = registerSpec.getNextReg();
            if (nextReg > this.regCount) {
                this.regCount = nextReg;
            }
        }
    }
}
