package com.android.p006dx.p009io.instructions;

import com.android.p006dx.p009io.IndexType;

/* JADX INFO: loaded from: classes4.dex */
public final class FourRegisterDecodedInstruction extends DecodedInstruction {

    /* JADX INFO: renamed from: a */
    private final int f105a;

    /* JADX INFO: renamed from: b */
    private final int f106b;

    /* JADX INFO: renamed from: c */
    private final int f107c;

    /* JADX INFO: renamed from: d */
    private final int f108d;

    @Override // com.android.p006dx.p009io.instructions.DecodedInstruction
    public int getRegisterCount() {
        return 4;
    }

    public FourRegisterDecodedInstruction(InstructionCodec instructionCodec, int i, int i2, IndexType indexType, int i3, long j, int i4, int i5, int i6, int i7) {
        super(instructionCodec, i, i2, indexType, i3, j);
        this.f105a = i4;
        this.f106b = i5;
        this.f107c = i6;
        this.f108d = i7;
    }

    @Override // com.android.p006dx.p009io.instructions.DecodedInstruction
    public int getA() {
        return this.f105a;
    }

    @Override // com.android.p006dx.p009io.instructions.DecodedInstruction
    public int getB() {
        return this.f106b;
    }

    @Override // com.android.p006dx.p009io.instructions.DecodedInstruction
    public int getC() {
        return this.f107c;
    }

    @Override // com.android.p006dx.p009io.instructions.DecodedInstruction
    public int getD() {
        return this.f108d;
    }

    @Override // com.android.p006dx.p009io.instructions.DecodedInstruction
    public DecodedInstruction withIndex(int i) {
        return new FourRegisterDecodedInstruction(getFormat(), getOpcode(), i, getIndexType(), getTarget(), getLiteral(), this.f105a, this.f106b, this.f107c, this.f108d);
    }
}
