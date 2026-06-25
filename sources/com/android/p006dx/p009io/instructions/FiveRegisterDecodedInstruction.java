package com.android.p006dx.p009io.instructions;

import com.android.p006dx.p009io.IndexType;

/* JADX INFO: loaded from: classes4.dex */
public final class FiveRegisterDecodedInstruction extends DecodedInstruction {

    /* JADX INFO: renamed from: a */
    private final int f100a;

    /* JADX INFO: renamed from: b */
    private final int f101b;

    /* JADX INFO: renamed from: c */
    private final int f102c;

    /* JADX INFO: renamed from: d */
    private final int f103d;

    /* JADX INFO: renamed from: e */
    private final int f104e;

    @Override // com.android.p006dx.p009io.instructions.DecodedInstruction
    public int getRegisterCount() {
        return 5;
    }

    public FiveRegisterDecodedInstruction(InstructionCodec instructionCodec, int i, int i2, IndexType indexType, int i3, long j, int i4, int i5, int i6, int i7, int i8) {
        super(instructionCodec, i, i2, indexType, i3, j);
        this.f100a = i4;
        this.f101b = i5;
        this.f102c = i6;
        this.f103d = i7;
        this.f104e = i8;
    }

    @Override // com.android.p006dx.p009io.instructions.DecodedInstruction
    public int getA() {
        return this.f100a;
    }

    @Override // com.android.p006dx.p009io.instructions.DecodedInstruction
    public int getB() {
        return this.f101b;
    }

    @Override // com.android.p006dx.p009io.instructions.DecodedInstruction
    public int getC() {
        return this.f102c;
    }

    @Override // com.android.p006dx.p009io.instructions.DecodedInstruction
    public int getD() {
        return this.f103d;
    }

    @Override // com.android.p006dx.p009io.instructions.DecodedInstruction
    public int getE() {
        return this.f104e;
    }

    @Override // com.android.p006dx.p009io.instructions.DecodedInstruction
    public DecodedInstruction withIndex(int i) {
        return new FiveRegisterDecodedInstruction(getFormat(), getOpcode(), i, getIndexType(), getTarget(), getLiteral(), this.f100a, this.f101b, this.f102c, this.f103d, this.f104e);
    }
}
