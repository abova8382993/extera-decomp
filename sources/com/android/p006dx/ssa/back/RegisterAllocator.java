package com.android.p006dx.ssa.back;

import com.android.p006dx.rop.code.PlainInsn;
import com.android.p006dx.rop.code.RegisterSpec;
import com.android.p006dx.rop.code.RegisterSpecList;
import com.android.p006dx.rop.code.Rops;
import com.android.p006dx.rop.code.SourcePosition;
import com.android.p006dx.ssa.NormalSsaInsn;
import com.android.p006dx.ssa.RegisterMapper;
import com.android.p006dx.ssa.SsaBasicBlock;
import com.android.p006dx.ssa.SsaInsn;
import com.android.p006dx.ssa.SsaMethod;
import com.android.p006dx.util.IntIterator;
import java.util.ArrayList;
import okio.Buffer$$ExternalSyntheticBUOutline4;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes4.dex */
public abstract class RegisterAllocator {
    protected final InterferenceGraph interference;
    protected final SsaMethod ssaMeth;

    public abstract RegisterMapper allocateRegisters();

    public abstract boolean wantsParamsMovedHigh();

    public RegisterAllocator(SsaMethod ssaMethod, InterferenceGraph interferenceGraph) {
        this.ssaMeth = ssaMethod;
        this.interference = interferenceGraph;
    }

    public final int getCategoryForSsaReg(int i) {
        SsaInsn definitionForRegister = this.ssaMeth.getDefinitionForRegister(i);
        if (definitionForRegister == null) {
            return 1;
        }
        return definitionForRegister.getResult().getCategory();
    }

    public final RegisterSpec getDefinitionSpecForSsaReg(int i) {
        SsaInsn definitionForRegister = this.ssaMeth.getDefinitionForRegister(i);
        if (definitionForRegister == null) {
            return null;
        }
        return definitionForRegister.getResult();
    }

    public boolean isDefinitionMoveParam(int i) {
        SsaInsn definitionForRegister = this.ssaMeth.getDefinitionForRegister(i);
        return (definitionForRegister instanceof NormalSsaInsn) && ((NormalSsaInsn) definitionForRegister).getOpcode().getOpcode() == 3;
    }

    public final RegisterSpec insertMoveBefore(SsaInsn ssaInsn, RegisterSpec registerSpec) {
        SsaBasicBlock block = ssaInsn.getBlock();
        ArrayList<SsaInsn> insns = block.getInsns();
        int iIndexOf = insns.indexOf(ssaInsn);
        if (iIndexOf < 0) {
            g$$ExternalSyntheticBUOutline1.m207m("specified insn is not in this block");
            return null;
        }
        if (iIndexOf != insns.size() - 1) {
            Buffer$$ExternalSyntheticBUOutline4.m978m("Adding move here not supported:", ssaInsn.toHuman());
            return null;
        }
        RegisterSpec registerSpecMake = RegisterSpec.make(this.ssaMeth.makeNewSsaReg(), registerSpec.getTypeBearer());
        insns.add(iIndexOf, SsaInsn.makeFromRop(new PlainInsn(Rops.opMove(registerSpecMake.getType()), SourcePosition.NO_INFO, registerSpecMake, RegisterSpecList.make(registerSpec)), block));
        int reg = registerSpecMake.getReg();
        IntIterator it = block.getLiveOutRegs().iterator();
        while (it.hasNext()) {
            this.interference.add(reg, it.next());
        }
        RegisterSpecList sources = ssaInsn.getSources();
        int size = sources.size();
        for (int i = 0; i < size; i++) {
            this.interference.add(reg, sources.get(i).getReg());
        }
        this.ssaMeth.onInsnsChanged();
        return registerSpecMake;
    }
}
