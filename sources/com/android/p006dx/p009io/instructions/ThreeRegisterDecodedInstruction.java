package com.android.p006dx.p009io.instructions;

import com.android.p006dx.p009io.IndexType;

/* JADX INFO: loaded from: classes4.dex */
public final class ThreeRegisterDecodedInstruction extends DecodedInstruction {

    /* JADX INFO: renamed from: a */
    private final int f112a;

    /* JADX INFO: renamed from: b */
    private final int f113b;

    /* JADX INFO: renamed from: c */
    private final int f114c;

    @Override // com.android.p006dx.p009io.instructions.DecodedInstruction
    public int getRegisterCount() {
        return 3;
    }

    public ThreeRegisterDecodedInstruction(InstructionCodec instructionCodec, int i, int i2, IndexType indexType, int i3, long j, int i4, int i5, int i6) {
        super(instructionCodec, i, i2, indexType, i3, j);
        this.f112a = i4;
        this.f113b = i5;
        this.f114c = i6;
    }

    @Override // com.android.p006dx.p009io.instructions.DecodedInstruction
    public int getA() {
        return this.f112a;
    }

    @Override // com.android.p006dx.p009io.instructions.DecodedInstruction
    public int getB() {
        return this.f113b;
    }

    @Override // com.android.p006dx.p009io.instructions.DecodedInstruction
    public int getC() {
        return this.f114c;
    }

    @Override // com.android.p006dx.p009io.instructions.DecodedInstruction
    public DecodedInstruction withIndex(int i) {
        return new ThreeRegisterDecodedInstruction(getFormat(), getOpcode(), i, getIndexType(), getTarget(), getLiteral(), this.f112a, this.f113b, this.f114c);
    }
}
