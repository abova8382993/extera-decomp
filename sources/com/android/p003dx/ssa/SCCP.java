package com.android.p003dx.ssa;

import com.android.p003dx.rop.code.PlainInsn;
import com.android.p003dx.rop.code.RegisterSpec;
import com.android.p003dx.rop.code.RegisterSpecList;
import com.android.p003dx.rop.code.Rops;
import com.android.p003dx.rop.cst.Constant;
import com.android.p003dx.rop.cst.TypedConstant;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;

/* JADX INFO: loaded from: classes4.dex */
public class SCCP {
    private static final int CONSTANT = 1;
    private static final int TOP = 0;
    private static final int VARYING = 2;
    private final ArrayList<SsaInsn> branchWorklist;
    private final ArrayList<SsaBasicBlock> cfgPhiWorklist;
    private final ArrayList<SsaBasicBlock> cfgWorklist;
    private final BitSet executableBlocks;
    private final Constant[] latticeConstants;
    private final int[] latticeValues;
    private final int regCount;
    private final SsaMethod ssaMeth;
    private final ArrayList<SsaInsn> ssaWorklist;
    private final ArrayList<SsaInsn> varyingWorklist;

    private SCCP(SsaMethod ssaMethod) {
        this.ssaMeth = ssaMethod;
        int regCount = ssaMethod.getRegCount();
        this.regCount = regCount;
        this.latticeValues = new int[regCount];
        this.latticeConstants = new Constant[regCount];
        this.cfgWorklist = new ArrayList<>();
        this.cfgPhiWorklist = new ArrayList<>();
        this.executableBlocks = new BitSet(ssaMethod.getBlocks().size());
        this.ssaWorklist = new ArrayList<>();
        this.varyingWorklist = new ArrayList<>();
        this.branchWorklist = new ArrayList<>();
        for (int i = 0; i < this.regCount; i++) {
            this.latticeValues[i] = 0;
            this.latticeConstants[i] = null;
        }
    }

    public static void process(SsaMethod ssaMethod) {
        new SCCP(ssaMethod).run();
    }

    private void addBlockToWorklist(SsaBasicBlock ssaBasicBlock) {
        if (!this.executableBlocks.get(ssaBasicBlock.getIndex())) {
            this.cfgWorklist.add(ssaBasicBlock);
            this.executableBlocks.set(ssaBasicBlock.getIndex());
        } else {
            this.cfgPhiWorklist.add(ssaBasicBlock);
        }
    }

    private void addUsersToWorklist(int i, int i2) {
        if (i2 == 2) {
            Iterator<SsaInsn> it = this.ssaMeth.getUseListForRegister(i).iterator();
            while (it.hasNext()) {
                this.varyingWorklist.add(it.next());
            }
            return;
        }
        Iterator<SsaInsn> it2 = this.ssaMeth.getUseListForRegister(i).iterator();
        while (it2.hasNext()) {
            this.ssaWorklist.add(it2.next());
        }
    }

    private boolean setLatticeValueTo(int i, int i2, Constant constant) {
        if (i2 != 1) {
            int[] iArr = this.latticeValues;
            if (iArr[i] == i2) {
                return false;
            }
            iArr[i] = i2;
            return true;
        }
        if (this.latticeValues[i] == i2 && this.latticeConstants[i].equals(constant)) {
            return false;
        }
        this.latticeValues[i] = i2;
        this.latticeConstants[i] = constant;
        return true;
    }

    private void simulatePhi(PhiInsn phiInsn) {
        int reg = phiInsn.getResult().getReg();
        int i = 2;
        if (this.latticeValues[reg] == 2) {
            return;
        }
        RegisterSpecList sources = phiInsn.getSources();
        int size = sources.size();
        int i2 = 0;
        Constant constant = null;
        int i3 = 0;
        while (true) {
            if (i2 >= size) {
                i = i3;
                break;
            }
            int iPredBlockIndexForSourcesIndex = phiInsn.predBlockIndexForSourcesIndex(i2);
            int reg2 = sources.get(i2).getReg();
            int i4 = this.latticeValues[reg2];
            if (this.executableBlocks.get(iPredBlockIndexForSourcesIndex)) {
                if (i4 != 1) {
                    i = i4;
                    break;
                } else if (constant == null) {
                    constant = this.latticeConstants[reg2];
                    i3 = 1;
                } else if (!this.latticeConstants[reg2].equals(constant)) {
                    break;
                }
            }
            i2++;
        }
        if (setLatticeValueTo(reg, i, constant)) {
            addUsersToWorklist(reg, i);
        }
    }

    private void simulateBlock(SsaBasicBlock ssaBasicBlock) {
        ArrayList<SsaInsn> insns = ssaBasicBlock.getInsns();
        int size = insns.size();
        int i = 0;
        while (i < size) {
            SsaInsn ssaInsn = insns.get(i);
            i++;
            SsaInsn ssaInsn2 = ssaInsn;
            if (ssaInsn2 instanceof PhiInsn) {
                simulatePhi((PhiInsn) ssaInsn2);
            } else {
                simulateStmt(ssaInsn2);
            }
        }
    }

    private void simulatePhiBlock(SsaBasicBlock ssaBasicBlock) {
        ArrayList<SsaInsn> insns = ssaBasicBlock.getInsns();
        int size = insns.size();
        int i = 0;
        while (i < size) {
            SsaInsn ssaInsn = insns.get(i);
            i++;
            SsaInsn ssaInsn2 = ssaInsn;
            if (!(ssaInsn2 instanceof PhiInsn)) {
                return;
            } else {
                simulatePhi((PhiInsn) ssaInsn2);
            }
        }
    }

    private static String latticeValName(int i) {
        if (i == 0) {
            return "TOP";
        }
        if (i == 1) {
            return "CONSTANT";
        }
        if (i == 2) {
            return "VARYING";
        }
        return "UNKNOWN";
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x007c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void simulateBranch(com.android.p003dx.ssa.SsaInsn r10) {
        /*
            Method dump skipped, instruction units count: 316
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p003dx.ssa.SCCP.simulateBranch(com.android.dx.ssa.SsaInsn):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x0098 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0099  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.android.p003dx.rop.cst.Constant simulateMath(com.android.p003dx.ssa.SsaInsn r8, int r9) {
        /*
            r7 = this;
            com.android.dx.rop.code.Insn r0 = r8.getOriginalRopInsn()
            com.android.dx.rop.code.Rop r1 = r8.getOpcode()
            int r1 = r1.getOpcode()
            com.android.dx.rop.code.RegisterSpecList r8 = r8.getSources()
            r2 = 0
            com.android.dx.rop.code.RegisterSpec r3 = r8.get(r2)
            int r3 = r3.getReg()
            int[] r4 = r7.latticeValues
            r4 = r4[r3]
            r5 = 0
            r6 = 1
            if (r4 == r6) goto L23
            r3 = r5
            goto L27
        L23:
            com.android.dx.rop.cst.Constant[] r4 = r7.latticeConstants
            r3 = r4[r3]
        L27:
            int r4 = r8.size()
            if (r4 != r6) goto L34
            com.android.dx.rop.code.CstInsn r0 = (com.android.p003dx.rop.code.CstInsn) r0
            com.android.dx.rop.cst.Constant r0 = r0.getConstant()
            goto L48
        L34:
            com.android.dx.rop.code.RegisterSpec r0 = r8.get(r6)
            int r0 = r0.getReg()
            int[] r4 = r7.latticeValues
            r4 = r4[r0]
            if (r4 == r6) goto L44
            r0 = r5
            goto L48
        L44:
            com.android.dx.rop.cst.Constant[] r4 = r7.latticeConstants
            r0 = r4[r0]
        L48:
            if (r3 == 0) goto L9e
            if (r0 != 0) goto L4d
            goto L9e
        L4d:
            r4 = 6
            if (r9 == r4) goto L51
            return r5
        L51:
            com.android.dx.rop.cst.CstInteger r3 = (com.android.p003dx.rop.cst.CstInteger) r3
            int r9 = r3.getValue()
            com.android.dx.rop.cst.CstInteger r0 = (com.android.p003dx.rop.cst.CstInteger) r0
            int r0 = r0.getValue()
            switch(r1) {
                case 14: goto L94;
                case 15: goto L89;
                case 16: goto L87;
                case 17: goto L82;
                case 18: goto L7a;
                case 19: goto L60;
                case 20: goto L77;
                case 21: goto L74;
                case 22: goto L71;
                case 23: goto L6e;
                case 24: goto L6b;
                case 25: goto L68;
                default: goto L60;
            }
        L60:
            java.lang.RuntimeException r8 = new java.lang.RuntimeException
            java.lang.String r9 = "Unexpected op"
            r8.<init>(r9)
            throw r8
        L68:
            int r8 = r9 >>> r0
            goto L96
        L6b:
            int r8 = r9 >> r0
            goto L96
        L6e:
            int r8 = r9 << r0
            goto L96
        L71:
            r8 = r9 ^ r0
            goto L96
        L74:
            r8 = r9 | r0
            goto L96
        L77:
            r8 = r9 & r0
            goto L96
        L7a:
            if (r0 != 0) goto L7f
        L7c:
            r8 = r2
            r2 = r6
            goto L96
        L7f:
            int r9 = r9 % r0
        L80:
            r8 = r9
            goto L96
        L82:
            if (r0 != 0) goto L85
            goto L7c
        L85:
            int r9 = r9 / r0
            goto L80
        L87:
            int r9 = r9 * r0
            goto L80
        L89:
            int r8 = r8.size()
            if (r8 != r6) goto L92
            int r0 = r0 - r9
            r8 = r0
            goto L96
        L92:
            int r9 = r9 - r0
            goto L80
        L94:
            int r9 = r9 + r0
            goto L80
        L96:
            if (r2 == 0) goto L99
            return r5
        L99:
            com.android.dx.rop.cst.CstInteger r8 = com.android.p003dx.rop.cst.CstInteger.make(r8)
            return r8
        L9e:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p003dx.ssa.SCCP.simulateMath(com.android.dx.ssa.SsaInsn, int):com.android.dx.rop.cst.Constant");
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:40:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void simulateStmt(com.android.p003dx.ssa.SsaInsn r8) {
        /*
            r7 = this;
            com.android.dx.rop.code.Insn r0 = r8.getOriginalRopInsn()
            com.android.dx.rop.code.Rop r1 = r0.getOpcode()
            int r1 = r1.getBranchingness()
            r2 = 1
            if (r1 != r2) goto L19
            com.android.dx.rop.code.Rop r1 = r0.getOpcode()
            boolean r1 = r1.isCallLike()
            if (r1 == 0) goto L1c
        L19:
            r7.simulateBranch(r8)
        L1c:
            com.android.dx.rop.code.Rop r1 = r8.getOpcode()
            int r1 = r1.getOpcode()
            com.android.dx.rop.code.RegisterSpec r3 = r8.getResult()
            r4 = 0
            if (r3 != 0) goto L49
            r3 = 17
            if (r1 == r3) goto L33
            r3 = 18
            if (r1 != r3) goto La9
        L33:
            com.android.dx.ssa.SsaBasicBlock r3 = r8.getBlock()
            com.android.dx.ssa.SsaBasicBlock r3 = r3.getPrimarySuccessor()
            java.util.ArrayList r3 = r3.getInsns()
            java.lang.Object r3 = r3.get(r4)
            com.android.dx.ssa.SsaInsn r3 = (com.android.p003dx.ssa.SsaInsn) r3
            com.android.dx.rop.code.RegisterSpec r3 = r3.getResult()
        L49:
            int r5 = r3.getReg()
            r6 = 2
            if (r1 == r6) goto L7f
            r4 = 5
            if (r1 == r4) goto L78
            r0 = 56
            if (r1 == r0) goto L6b
            switch(r1) {
                case 14: goto L5e;
                case 15: goto L5e;
                case 16: goto L5e;
                case 17: goto L5e;
                case 18: goto L5e;
                default: goto L5a;
            }
        L5a:
            switch(r1) {
                case 20: goto L5e;
                case 21: goto L5e;
                case 22: goto L5e;
                case 23: goto L5e;
                case 24: goto L5e;
                case 25: goto L5e;
                default: goto L5d;
            }
        L5d:
            goto L9e
        L5e:
            int r0 = r3.getBasicType()
            com.android.dx.rop.cst.Constant r8 = r7.simulateMath(r8, r0)
            if (r8 == 0) goto L69
            goto La0
        L69:
            r2 = r6
            goto La0
        L6b:
            int[] r8 = r7.latticeValues
            r8 = r8[r5]
            if (r8 != r2) goto L9e
            com.android.dx.rop.cst.Constant[] r0 = r7.latticeConstants
            r0 = r0[r5]
            r2 = r8
            r8 = r0
            goto La0
        L78:
            com.android.dx.rop.code.CstInsn r0 = (com.android.p003dx.rop.code.CstInsn) r0
            com.android.dx.rop.cst.Constant r8 = r0.getConstant()
            goto La0
        L7f:
            com.android.dx.rop.code.RegisterSpecList r0 = r8.getSources()
            int r0 = r0.size()
            if (r0 != r2) goto L9e
            com.android.dx.rop.code.RegisterSpecList r8 = r8.getSources()
            com.android.dx.rop.code.RegisterSpec r8 = r8.get(r4)
            int r8 = r8.getReg()
            int[] r0 = r7.latticeValues
            r2 = r0[r8]
            com.android.dx.rop.cst.Constant[] r0 = r7.latticeConstants
            r8 = r0[r8]
            goto La0
        L9e:
            r8 = 0
            goto L69
        La0:
            boolean r8 = r7.setLatticeValueTo(r5, r2, r8)
            if (r8 == 0) goto La9
            r7.addUsersToWorklist(r5, r2)
        La9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p003dx.ssa.SCCP.simulateStmt(com.android.dx.ssa.SsaInsn):void");
    }

    private void run() {
        addBlockToWorklist(this.ssaMeth.getEntryBlock());
        while (true) {
            if (!this.cfgWorklist.isEmpty() || !this.cfgPhiWorklist.isEmpty() || !this.ssaWorklist.isEmpty() || !this.varyingWorklist.isEmpty()) {
                while (!this.cfgWorklist.isEmpty()) {
                    simulateBlock(this.cfgWorklist.remove(this.cfgWorklist.size() - 1));
                }
                while (!this.cfgPhiWorklist.isEmpty()) {
                    simulatePhiBlock(this.cfgPhiWorklist.remove(this.cfgPhiWorklist.size() - 1));
                }
                while (!this.varyingWorklist.isEmpty()) {
                    SsaInsn ssaInsnRemove = this.varyingWorklist.remove(this.varyingWorklist.size() - 1);
                    if (this.executableBlocks.get(ssaInsnRemove.getBlock().getIndex())) {
                        if (ssaInsnRemove instanceof PhiInsn) {
                            simulatePhi((PhiInsn) ssaInsnRemove);
                        } else {
                            simulateStmt(ssaInsnRemove);
                        }
                    }
                }
                while (!this.ssaWorklist.isEmpty()) {
                    SsaInsn ssaInsnRemove2 = this.ssaWorklist.remove(this.ssaWorklist.size() - 1);
                    if (this.executableBlocks.get(ssaInsnRemove2.getBlock().getIndex())) {
                        if (ssaInsnRemove2 instanceof PhiInsn) {
                            simulatePhi((PhiInsn) ssaInsnRemove2);
                        } else {
                            simulateStmt(ssaInsnRemove2);
                        }
                    }
                }
            } else {
                replaceConstants();
                replaceBranches();
                return;
            }
        }
    }

    private void replaceConstants() {
        for (int i = 0; i < this.regCount; i++) {
            if (this.latticeValues[i] == 1 && (this.latticeConstants[i] instanceof TypedConstant)) {
                SsaInsn definitionForRegister = this.ssaMeth.getDefinitionForRegister(i);
                if (!definitionForRegister.getResult().getTypeBearer().isConstant()) {
                    definitionForRegister.setResult(definitionForRegister.getResult().withType((TypedConstant) this.latticeConstants[i]));
                    for (SsaInsn ssaInsn : this.ssaMeth.getUseListForRegister(i)) {
                        if (!ssaInsn.isPhiOrMove()) {
                            NormalSsaInsn normalSsaInsn = (NormalSsaInsn) ssaInsn;
                            RegisterSpecList sources = ssaInsn.getSources();
                            int iIndexOfRegister = sources.indexOfRegister(i);
                            normalSsaInsn.changeOneSource(iIndexOfRegister, sources.get(iIndexOfRegister).withType((TypedConstant) this.latticeConstants[i]));
                        }
                    }
                }
            }
        }
    }

    private void replaceBranches() {
        ArrayList<SsaInsn> arrayList = this.branchWorklist;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            SsaInsn ssaInsn = arrayList.get(i);
            i++;
            SsaInsn ssaInsn2 = ssaInsn;
            SsaBasicBlock block = ssaInsn2.getBlock();
            int size2 = block.getSuccessorList().size();
            int i2 = -1;
            for (int i3 = 0; i3 < size2; i3++) {
                int i4 = block.getSuccessorList().get(i3);
                if (!this.executableBlocks.get(i4)) {
                    i2 = i4;
                }
            }
            if (size2 == 2 && i2 != -1) {
                block.replaceLastInsn(new PlainInsn(Rops.GOTO, ssaInsn2.getOriginalRopInsn().getPosition(), (RegisterSpec) null, RegisterSpecList.EMPTY));
                block.removeSuccessor(i2);
            }
        }
    }
}
