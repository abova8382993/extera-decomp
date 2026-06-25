package com.android.p006dx.dex.code;

import com.android.dex.Dex$$ExternalSyntheticBUOutline2;
import com.android.p006dx.dex.DexOptions;
import com.android.p006dx.dex.code.DalvCode;
import com.android.p006dx.rop.code.LocalItem;
import com.android.p006dx.rop.code.RegisterSpec;
import com.android.p006dx.rop.code.RegisterSpecList;
import com.android.p006dx.rop.code.RegisterSpecSet;
import com.android.p006dx.rop.cst.Constant;
import com.android.p006dx.rop.cst.CstMemberRef;
import com.android.p006dx.rop.cst.CstString;
import com.android.p006dx.rop.cst.CstType;
import com.android.p006dx.rop.type.Type;
import com.android.p006dx.ssa.BasicRegisterMapper;
import java.util.ArrayList;
import java.util.HashSet;
import okio.ByteString$$ExternalSyntheticBUOutline0;
import okio.Segment$$ExternalSyntheticBUOutline1;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes4.dex */
public final class OutputFinisher {
    private final DexOptions dexOptions;
    private ArrayList<DalvInsn> insns;
    private final int paramSize;
    private int reservedParameterCount;
    private final int unreservedRegCount;
    private int reservedCount = -1;
    private boolean hasAnyPositionInfo = false;
    private boolean hasAnyLocalInfo = false;

    public OutputFinisher(DexOptions dexOptions, int i, int i2, int i3) {
        this.dexOptions = dexOptions;
        this.unreservedRegCount = i2;
        this.insns = new ArrayList<>(i);
        this.paramSize = i3;
    }

    public boolean hasAnyPositionInfo() {
        return this.hasAnyPositionInfo;
    }

    public boolean hasAnyLocalInfo() {
        return this.hasAnyLocalInfo;
    }

    private static boolean hasLocalInfo(DalvInsn dalvInsn) {
        if (dalvInsn instanceof LocalSnapshot) {
            RegisterSpecSet locals = ((LocalSnapshot) dalvInsn).getLocals();
            int size = locals.size();
            for (int i = 0; i < size; i++) {
                if (hasLocalInfo(locals.get(i))) {
                    return true;
                }
            }
        } else if ((dalvInsn instanceof LocalStart) && hasLocalInfo(((LocalStart) dalvInsn).getLocal())) {
            return true;
        }
        return false;
    }

    private static boolean hasLocalInfo(RegisterSpec registerSpec) {
        return (registerSpec == null || registerSpec.getLocalItem().getName() == null) ? false : true;
    }

    public HashSet<Constant> getAllConstants() {
        HashSet<Constant> hashSet = new HashSet<>(20);
        ArrayList<DalvInsn> arrayList = this.insns;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            DalvInsn dalvInsn = arrayList.get(i);
            i++;
            addConstants(hashSet, dalvInsn);
        }
        return hashSet;
    }

    private static void addConstants(HashSet<Constant> hashSet, DalvInsn dalvInsn) {
        if (dalvInsn instanceof CstInsn) {
            hashSet.add(((CstInsn) dalvInsn).getConstant());
            return;
        }
        int i = 0;
        if (dalvInsn instanceof MultiCstInsn) {
            MultiCstInsn multiCstInsn = (MultiCstInsn) dalvInsn;
            while (i < multiCstInsn.getNumberOfConstants()) {
                hashSet.add(multiCstInsn.getConstant(i));
                i++;
            }
            return;
        }
        if (dalvInsn instanceof LocalSnapshot) {
            RegisterSpecSet locals = ((LocalSnapshot) dalvInsn).getLocals();
            int size = locals.size();
            while (i < size) {
                addConstants(hashSet, locals.get(i));
                i++;
            }
            return;
        }
        if (dalvInsn instanceof LocalStart) {
            addConstants(hashSet, ((LocalStart) dalvInsn).getLocal());
        }
    }

    private static void addConstants(HashSet<Constant> hashSet, RegisterSpec registerSpec) {
        if (registerSpec == null) {
            return;
        }
        LocalItem localItem = registerSpec.getLocalItem();
        CstString name = localItem.getName();
        CstString signature = localItem.getSignature();
        Type type = registerSpec.getType();
        if (type != Type.KNOWN_NULL) {
            hashSet.add(CstType.intern(type));
        } else {
            hashSet.add(CstType.intern(Type.OBJECT));
        }
        if (name != null) {
            hashSet.add(name);
        }
        if (signature != null) {
            hashSet.add(signature);
        }
    }

    public void add(DalvInsn dalvInsn) {
        this.insns.add(dalvInsn);
        updateInfo(dalvInsn);
    }

    public void insert(int i, DalvInsn dalvInsn) {
        this.insns.add(i, dalvInsn);
        updateInfo(dalvInsn);
    }

    private void updateInfo(DalvInsn dalvInsn) {
        if (!this.hasAnyPositionInfo && dalvInsn.getPosition().getLine() >= 0) {
            this.hasAnyPositionInfo = true;
        }
        if (this.hasAnyLocalInfo || !hasLocalInfo(dalvInsn)) {
            return;
        }
        this.hasAnyLocalInfo = true;
    }

    public void reverseBranch(int i, CodeAddress codeAddress) {
        int size = (this.insns.size() - i) - 1;
        try {
            this.insns.set(size, ((TargetInsn) this.insns.get(size)).withNewTargetAndReversed(codeAddress));
        } catch (ClassCastException unused) {
            g$$ExternalSyntheticBUOutline1.m207m("non-reversible instruction");
        } catch (IndexOutOfBoundsException unused2) {
            g$$ExternalSyntheticBUOutline1.m207m("too few instructions");
        }
    }

    public void assignIndices(DalvCode.AssignIndicesCallback assignIndicesCallback) {
        ArrayList<DalvInsn> arrayList = this.insns;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            DalvInsn dalvInsn = arrayList.get(i);
            i++;
            DalvInsn dalvInsn2 = dalvInsn;
            if (dalvInsn2 instanceof CstInsn) {
                assignIndices((CstInsn) dalvInsn2, assignIndicesCallback);
            } else if (dalvInsn2 instanceof MultiCstInsn) {
                assignIndices((MultiCstInsn) dalvInsn2, assignIndicesCallback);
            }
        }
    }

    private static void assignIndices(CstInsn cstInsn, DalvCode.AssignIndicesCallback assignIndicesCallback) {
        int index;
        Constant constant = cstInsn.getConstant();
        int index2 = assignIndicesCallback.getIndex(constant);
        if (index2 >= 0) {
            cstInsn.setIndex(index2);
        }
        if (!(constant instanceof CstMemberRef) || (index = assignIndicesCallback.getIndex(((CstMemberRef) constant).getDefiningClass())) < 0) {
            return;
        }
        cstInsn.setClassIndex(index);
    }

    private static void assignIndices(MultiCstInsn multiCstInsn, DalvCode.AssignIndicesCallback assignIndicesCallback) {
        for (int i = 0; i < multiCstInsn.getNumberOfConstants(); i++) {
            Constant constant = multiCstInsn.getConstant(i);
            multiCstInsn.setIndex(i, assignIndicesCallback.getIndex(constant));
            if (constant instanceof CstMemberRef) {
                multiCstInsn.setClassIndex(assignIndicesCallback.getIndex(((CstMemberRef) constant).getDefiningClass()));
            }
        }
    }

    public DalvInsnList finishProcessingAndGetList() {
        if (this.reservedCount >= 0) {
            ByteString$$ExternalSyntheticBUOutline0.m979m("already processed");
            return null;
        }
        Dop[] dopArrMakeOpcodesArray = makeOpcodesArray();
        reserveRegisters(dopArrMakeOpcodesArray);
        if (this.dexOptions.ALIGN_64BIT_REGS_IN_OUTPUT_FINISHER) {
            align64bits(dopArrMakeOpcodesArray);
        }
        massageInstructions(dopArrMakeOpcodesArray);
        assignAddressesAndFixBranches();
        return DalvInsnList.makeImmutable(this.insns, this.reservedCount + this.unreservedRegCount + this.reservedParameterCount);
    }

    private Dop[] makeOpcodesArray() {
        int size = this.insns.size();
        Dop[] dopArr = new Dop[size];
        for (int i = 0; i < size; i++) {
            dopArr[i] = this.insns.get(i).getOpcode();
        }
        return dopArr;
    }

    private boolean reserveRegisters(Dop[] dopArr) {
        int i = this.reservedCount;
        if (i < 0) {
            i = 0;
        }
        boolean z = false;
        while (true) {
            int iCalculateReservedCount = calculateReservedCount(dopArr);
            if (i < iCalculateReservedCount) {
                int i2 = iCalculateReservedCount - i;
                int size = this.insns.size();
                for (int i3 = 0; i3 < size; i3++) {
                    DalvInsn dalvInsn = this.insns.get(i3);
                    if (!(dalvInsn instanceof CodeAddress)) {
                        this.insns.set(i3, dalvInsn.withRegisterOffset(i2));
                    }
                }
                z = true;
                i = iCalculateReservedCount;
            } else {
                this.reservedCount = i;
                return z;
            }
        }
    }

    private int calculateReservedCount(Dop[] dopArr) {
        int size = this.insns.size();
        int i = this.reservedCount;
        for (int i2 = 0; i2 < size; i2++) {
            DalvInsn dalvInsn = this.insns.get(i2);
            Dop dop = dopArr[i2];
            Dop dopFindOpcodeForInsn = findOpcodeForInsn(dalvInsn, dop);
            if (dopFindOpcodeForInsn == null) {
                int minimumRegisterRequirement = dalvInsn.getMinimumRegisterRequirement(findExpandedOpcodeForInsn(dalvInsn).getFormat().compatibleRegs(dalvInsn));
                if (minimumRegisterRequirement > i) {
                    i = minimumRegisterRequirement;
                }
            } else {
                if (dop == dopFindOpcodeForInsn) {
                }
            }
            dopArr[i2] = dopFindOpcodeForInsn;
        }
        return i;
    }

    private Dop findOpcodeForInsn(DalvInsn dalvInsn, Dop dop) {
        while (dop != null && (!dop.getFormat().isCompatible(dalvInsn) || (this.dexOptions.forceJumbo && dop.getOpcode() == 26))) {
            dop = Dops.getNextOrNull(dop, this.dexOptions);
        }
        return dop;
    }

    private Dop findExpandedOpcodeForInsn(DalvInsn dalvInsn) {
        Dop dopFindOpcodeForInsn = findOpcodeForInsn(dalvInsn.getLowRegVersion(), dalvInsn.getOpcode());
        if (dopFindOpcodeForInsn != null) {
            return dopFindOpcodeForInsn;
        }
        Dex$$ExternalSyntheticBUOutline2.m212m("No expanded opcode for ", dalvInsn);
        return null;
    }

    private void massageInstructions(Dop[] dopArr) {
        if (this.reservedCount == 0) {
            int size = this.insns.size();
            for (int i = 0; i < size; i++) {
                DalvInsn dalvInsn = this.insns.get(i);
                Dop opcode = dalvInsn.getOpcode();
                Dop dop = dopArr[i];
                if (opcode != dop) {
                    this.insns.set(i, dalvInsn.withOpcode(dop));
                }
            }
            return;
        }
        this.insns = performExpansion(dopArr);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0053  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.util.ArrayList<com.android.p006dx.dex.code.DalvInsn> performExpansion(com.android.p006dx.dex.code.Dop[] r13) {
        /*
            r12 = this;
            java.util.ArrayList<com.android.dx.dex.code.DalvInsn> r0 = r12.insns
            int r0 = r0.size()
            java.util.ArrayList r1 = new java.util.ArrayList
            int r2 = r0 * 2
            r1.<init>(r2)
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r3 = 0
            r4 = r3
        L14:
            if (r4 >= r0) goto L89
            java.util.ArrayList<com.android.dx.dex.code.DalvInsn> r5 = r12.insns
            java.lang.Object r5 = r5.get(r4)
            com.android.dx.dex.code.DalvInsn r5 = (com.android.p006dx.dex.code.DalvInsn) r5
            com.android.dx.dex.code.Dop r6 = r5.getOpcode()
            r7 = r13[r4]
            if (r7 == 0) goto L29
            r8 = 0
            r10 = r8
            goto L42
        L29:
            com.android.dx.dex.code.Dop r7 = r12.findExpandedOpcodeForInsn(r5)
            com.android.dx.dex.code.InsnFormat r8 = r7.getFormat()
            java.util.BitSet r8 = r8.compatibleRegs(r5)
            com.android.dx.dex.code.DalvInsn r9 = r5.expandedPrefix(r8)
            com.android.dx.dex.code.DalvInsn r10 = r5.expandedSuffix(r8)
            com.android.dx.dex.code.DalvInsn r5 = r5.expandedVersion(r8)
            r8 = r9
        L42:
            boolean r9 = r5 instanceof com.android.p006dx.dex.code.CodeAddress
            if (r9 == 0) goto L53
            r9 = r5
            com.android.dx.dex.code.CodeAddress r9 = (com.android.p006dx.dex.code.CodeAddress) r9
            boolean r11 = r9.getBindsClosely()
            if (r11 == 0) goto L53
            r2.add(r9)
            goto L86
        L53:
            if (r8 == 0) goto L58
            r1.add(r8)
        L58:
            boolean r8 = r5 instanceof com.android.p006dx.dex.code.ZeroSizeInsn
            if (r8 != 0) goto L78
            int r8 = r2.size()
            if (r8 <= 0) goto L78
            int r8 = r2.size()
            r9 = r3
        L67:
            if (r9 >= r8) goto L75
            java.lang.Object r11 = r2.get(r9)
            int r9 = r9 + 1
            com.android.dx.dex.code.CodeAddress r11 = (com.android.p006dx.dex.code.CodeAddress) r11
            r1.add(r11)
            goto L67
        L75:
            r2.clear()
        L78:
            if (r7 == r6) goto L7e
            com.android.dx.dex.code.DalvInsn r5 = r5.withOpcode(r7)
        L7e:
            r1.add(r5)
            if (r10 == 0) goto L86
            r1.add(r10)
        L86:
            int r4 = r4 + 1
            goto L14
        L89:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p006dx.dex.code.OutputFinisher.performExpansion(com.android.dx.dex.code.Dop[]):java.util.ArrayList");
    }

    private void assignAddressesAndFixBranches() {
        do {
            assignAddresses();
        } while (fixBranches());
    }

    private void assignAddresses() {
        int size = this.insns.size();
        int iCodeSize = 0;
        for (int i = 0; i < size; i++) {
            DalvInsn dalvInsn = this.insns.get(i);
            dalvInsn.setAddress(iCodeSize);
            iCodeSize += dalvInsn.codeSize();
        }
    }

    private boolean fixBranches() {
        int size = this.insns.size();
        int i = 0;
        boolean z = false;
        while (i < size) {
            DalvInsn dalvInsn = this.insns.get(i);
            if (dalvInsn instanceof TargetInsn) {
                Dop opcode = dalvInsn.getOpcode();
                TargetInsn targetInsn = (TargetInsn) dalvInsn;
                if (opcode.getFormat().branchFits(targetInsn)) {
                    continue;
                } else {
                    if (opcode.getFamily() == 40) {
                        Dop dopFindOpcodeForInsn = findOpcodeForInsn(dalvInsn, opcode);
                        if (dopFindOpcodeForInsn == null) {
                            ByteString$$ExternalSyntheticBUOutline0.m979m("method too long");
                            return false;
                        }
                        this.insns.set(i, dalvInsn.withOpcode(dopFindOpcodeForInsn));
                    } else {
                        try {
                            int i2 = i + 1;
                            CodeAddress codeAddress = (CodeAddress) this.insns.get(i2);
                            this.insns.set(i, new TargetInsn(Dops.GOTO, targetInsn.getPosition(), RegisterSpecList.EMPTY, targetInsn.getTarget()));
                            this.insns.add(i, targetInsn.withNewTargetAndReversed(codeAddress));
                            size++;
                            i = i2;
                        } catch (ClassCastException unused) {
                            Segment$$ExternalSyntheticBUOutline1.m992m("unpaired TargetInsn");
                            return false;
                        } catch (IndexOutOfBoundsException unused2) {
                            Segment$$ExternalSyntheticBUOutline1.m992m("unpaired TargetInsn (dangling)");
                            return false;
                        }
                    }
                    z = true;
                }
            }
            i++;
        }
        return z;
    }

    private void align64bits(Dop[] dopArr) {
        do {
            int i = ((this.unreservedRegCount + this.reservedCount) + this.reservedParameterCount) - this.paramSize;
            ArrayList<DalvInsn> arrayList = this.insns;
            int size = arrayList.size();
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            int i6 = 0;
            while (i6 < size) {
                DalvInsn dalvInsn = arrayList.get(i6);
                i6++;
                RegisterSpecList registers = dalvInsn.getRegisters();
                for (int i7 = 0; i7 < registers.size(); i7++) {
                    RegisterSpec registerSpec = registers.get(i7);
                    if (registerSpec.isCategory2()) {
                        boolean z = registerSpec.getReg() >= i;
                        if (registerSpec.isEvenRegister()) {
                            if (z) {
                                i3++;
                            } else {
                                i5++;
                            }
                        } else if (z) {
                            i2++;
                        } else {
                            i4++;
                        }
                    }
                }
            }
            if (i2 > i3 && i4 > i5) {
                addReservedRegisters(1);
            } else if (i2 > i3) {
                addReservedParameters(1);
            } else {
                if (i4 <= i5) {
                    return;
                }
                addReservedRegisters(1);
                if (this.paramSize != 0 && i3 > i2) {
                    addReservedParameters(1);
                }
            }
        } while (reserveRegisters(dopArr));
    }

    private void addReservedParameters(int i) {
        shiftParameters(i);
        this.reservedParameterCount += i;
    }

    private void addReservedRegisters(int i) {
        shiftAllRegisters(i);
        this.reservedCount += i;
    }

    private void shiftAllRegisters(int i) {
        int size = this.insns.size();
        for (int i2 = 0; i2 < size; i2++) {
            DalvInsn dalvInsn = this.insns.get(i2);
            if (!(dalvInsn instanceof CodeAddress)) {
                this.insns.set(i2, dalvInsn.withRegisterOffset(i));
            }
        }
    }

    private void shiftParameters(int i) {
        int size = this.insns.size();
        int i2 = this.unreservedRegCount + this.reservedCount + this.reservedParameterCount;
        int i3 = i2 - this.paramSize;
        BasicRegisterMapper basicRegisterMapper = new BasicRegisterMapper(i2);
        for (int i4 = 0; i4 < i2; i4++) {
            if (i4 >= i3) {
                basicRegisterMapper.addMapping(i4, i4 + i, 1);
            } else {
                basicRegisterMapper.addMapping(i4, i4, 1);
            }
        }
        for (int i5 = 0; i5 < size; i5++) {
            DalvInsn dalvInsn = this.insns.get(i5);
            if (!(dalvInsn instanceof CodeAddress)) {
                this.insns.set(i5, dalvInsn.withMapper(basicRegisterMapper));
            }
        }
    }
}
