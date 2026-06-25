package com.android.p006dx.p009io.instructions;

import com.android.p006dx.merge.DexMerger$$ExternalSyntheticBUOutline0;
import com.android.p006dx.p009io.IndexType;
import com.android.p006dx.p009io.OpcodeInfo;
import com.android.p006dx.util.Hex;
import java.util.Arrays;
import okio.ByteString$$ExternalSyntheticBUOutline0;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes4.dex */
public enum InstructionCodec {
    FORMAT_00X { // from class: com.android.dx.io.instructions.InstructionCodec.1
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new ZeroRegisterDecodedInstruction(this, i, 0, null, 0, 0L);
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(decodedInstruction.getOpcodeUnit());
        }
    },
    FORMAT_10X { // from class: com.android.dx.io.instructions.InstructionCodec.2
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new ZeroRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, InstructionCodec.byte1(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(decodedInstruction.getOpcodeUnit());
        }
    },
    FORMAT_12X { // from class: com.android.dx.io.instructions.InstructionCodec.3
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new TwoRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, 0L, InstructionCodec.nibble2(i), InstructionCodec.nibble3(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcodeUnit(), InstructionCodec.makeByte(decodedInstruction.getA(), decodedInstruction.getB())));
        }
    },
    FORMAT_11N { // from class: com.android.dx.io.instructions.InstructionCodec.4
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new OneRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, (InstructionCodec.nibble3(i) << 28) >> 28, InstructionCodec.nibble2(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcodeUnit(), InstructionCodec.makeByte(decodedInstruction.getA(), decodedInstruction.getLiteralNibble())));
        }
    },
    FORMAT_11X { // from class: com.android.dx.io.instructions.InstructionCodec.5
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new OneRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, 0L, InstructionCodec.byte1(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()));
        }
    },
    FORMAT_10T { // from class: com.android.dx.io.instructions.InstructionCodec.6
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new ZeroRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, (codeInput.cursor() - 1) + ((byte) InstructionCodec.byte1(i)), 0L);
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getTargetByte(codeOutput.cursor())));
        }
    },
    FORMAT_20T { // from class: com.android.dx.io.instructions.InstructionCodec.7
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new ZeroRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, (codeInput.cursor() - 1) + ((short) codeInput.read()), InstructionCodec.byte1(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(decodedInstruction.getOpcodeUnit(), decodedInstruction.getTargetUnit(codeOutput.cursor()));
        }
    },
    FORMAT_20BC { // from class: com.android.dx.io.instructions.InstructionCodec.8
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new ZeroRegisterDecodedInstruction(this, InstructionCodec.byte0(i), codeInput.read(), IndexType.VARIES, 0, InstructionCodec.byte1(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getLiteralByte()), decodedInstruction.getIndexUnit());
        }
    },
    FORMAT_22X { // from class: com.android.dx.io.instructions.InstructionCodec.9
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new TwoRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, 0L, InstructionCodec.byte1(i), codeInput.read());
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), decodedInstruction.getBUnit());
        }
    },
    FORMAT_21T { // from class: com.android.dx.io.instructions.InstructionCodec.10
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new OneRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, (codeInput.cursor() - 1) + ((short) codeInput.read()), 0L, InstructionCodec.byte1(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), decodedInstruction.getTargetUnit(codeOutput.cursor()));
        }
    },
    FORMAT_21S { // from class: com.android.dx.io.instructions.InstructionCodec.11
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new OneRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, (short) codeInput.read(), InstructionCodec.byte1(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), decodedInstruction.getLiteralUnit());
        }
    },
    FORMAT_21H { // from class: com.android.dx.io.instructions.InstructionCodec.12
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            int iByte0 = InstructionCodec.byte0(i);
            return new OneRegisterDecodedInstruction(this, iByte0, 0, null, 0, ((long) ((short) codeInput.read())) << (iByte0 == 21 ? (char) 16 : '0'), InstructionCodec.byte1(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            int opcode = decodedInstruction.getOpcode();
            codeOutput.write(InstructionCodec.codeUnit(opcode, decodedInstruction.getA()), (short) (decodedInstruction.getLiteral() >> (opcode == 21 ? (char) 16 : '0')));
        }
    },
    FORMAT_21C { // from class: com.android.dx.io.instructions.InstructionCodec.13
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            int iByte0 = InstructionCodec.byte0(i);
            return new OneRegisterDecodedInstruction(this, iByte0, codeInput.read(), OpcodeInfo.getIndexType(iByte0), 0, 0L, InstructionCodec.byte1(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), decodedInstruction.getIndexUnit());
        }
    },
    FORMAT_23X { // from class: com.android.dx.io.instructions.InstructionCodec.14
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            int iByte0 = InstructionCodec.byte0(i);
            int iByte1 = InstructionCodec.byte1(i);
            int i2 = codeInput.read();
            return new ThreeRegisterDecodedInstruction(this, iByte0, 0, null, 0, 0L, iByte1, InstructionCodec.byte0(i2), InstructionCodec.byte1(i2));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), InstructionCodec.codeUnit(decodedInstruction.getB(), decodedInstruction.getC()));
        }
    },
    FORMAT_22B { // from class: com.android.dx.io.instructions.InstructionCodec.15
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new TwoRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, (byte) InstructionCodec.byte1(r11), InstructionCodec.byte1(i), InstructionCodec.byte0(codeInput.read()));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), InstructionCodec.codeUnit(decodedInstruction.getB(), decodedInstruction.getLiteralByte()));
        }
    },
    FORMAT_22T { // from class: com.android.dx.io.instructions.InstructionCodec.16
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new TwoRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, (codeInput.cursor() - 1) + ((short) codeInput.read()), 0L, InstructionCodec.nibble2(i), InstructionCodec.nibble3(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), InstructionCodec.makeByte(decodedInstruction.getA(), decodedInstruction.getB())), decodedInstruction.getTargetUnit(codeOutput.cursor()));
        }
    },
    FORMAT_22S { // from class: com.android.dx.io.instructions.InstructionCodec.17
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new TwoRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, (short) codeInput.read(), InstructionCodec.nibble2(i), InstructionCodec.nibble3(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), InstructionCodec.makeByte(decodedInstruction.getA(), decodedInstruction.getB())), decodedInstruction.getLiteralUnit());
        }
    },
    FORMAT_22C { // from class: com.android.dx.io.instructions.InstructionCodec.18
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            int iByte0 = InstructionCodec.byte0(i);
            return new TwoRegisterDecodedInstruction(this, iByte0, codeInput.read(), OpcodeInfo.getIndexType(iByte0), 0, 0L, InstructionCodec.nibble2(i), InstructionCodec.nibble3(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), InstructionCodec.makeByte(decodedInstruction.getA(), decodedInstruction.getB())), decodedInstruction.getIndexUnit());
        }
    },
    FORMAT_22CS { // from class: com.android.dx.io.instructions.InstructionCodec.19
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new TwoRegisterDecodedInstruction(this, InstructionCodec.byte0(i), codeInput.read(), IndexType.FIELD_OFFSET, 0, 0L, InstructionCodec.nibble2(i), InstructionCodec.nibble3(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), InstructionCodec.makeByte(decodedInstruction.getA(), decodedInstruction.getB())), decodedInstruction.getIndexUnit());
        }
    },
    FORMAT_30T { // from class: com.android.dx.io.instructions.InstructionCodec.20
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new ZeroRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, (codeInput.cursor() - 1) + codeInput.readInt(), InstructionCodec.byte1(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            int target = decodedInstruction.getTarget(codeOutput.cursor());
            codeOutput.write(decodedInstruction.getOpcodeUnit(), InstructionCodec.unit0(target), InstructionCodec.unit1(target));
        }
    },
    FORMAT_32X { // from class: com.android.dx.io.instructions.InstructionCodec.21
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new TwoRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, InstructionCodec.byte1(i), codeInput.read(), codeInput.read());
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(decodedInstruction.getOpcodeUnit(), decodedInstruction.getAUnit(), decodedInstruction.getBUnit());
        }
    },
    FORMAT_31I { // from class: com.android.dx.io.instructions.InstructionCodec.22
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new OneRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, codeInput.readInt(), InstructionCodec.byte1(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            int literalInt = decodedInstruction.getLiteralInt();
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), InstructionCodec.unit0(literalInt), InstructionCodec.unit1(literalInt));
        }
    },
    FORMAT_31T { // from class: com.android.dx.io.instructions.InstructionCodec.23
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            int iCursor = codeInput.cursor() - 1;
            int iByte0 = InstructionCodec.byte0(i);
            int iByte1 = InstructionCodec.byte1(i);
            int i2 = iCursor + codeInput.readInt();
            if (iByte0 == 43 || iByte0 == 44) {
                codeInput.setBaseAddress(i2, iCursor);
            }
            return new OneRegisterDecodedInstruction(this, iByte0, 0, null, i2, 0L, iByte1);
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            int target = decodedInstruction.getTarget(codeOutput.cursor());
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), InstructionCodec.unit0(target), InstructionCodec.unit1(target));
        }
    },
    FORMAT_31C { // from class: com.android.dx.io.instructions.InstructionCodec.24
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            int iByte0 = InstructionCodec.byte0(i);
            return new OneRegisterDecodedInstruction(this, iByte0, codeInput.readInt(), OpcodeInfo.getIndexType(iByte0), 0, 0L, InstructionCodec.byte1(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            int index = decodedInstruction.getIndex();
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), InstructionCodec.unit0(index), InstructionCodec.unit1(index));
        }
    },
    FORMAT_35C { // from class: com.android.dx.io.instructions.InstructionCodec.25
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return InstructionCodec.decodeRegisterList(this, i, codeInput);
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            InstructionCodec.encodeRegisterList(decodedInstruction, codeOutput);
        }
    },
    FORMAT_35MS { // from class: com.android.dx.io.instructions.InstructionCodec.26
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return InstructionCodec.decodeRegisterList(this, i, codeInput);
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            InstructionCodec.encodeRegisterList(decodedInstruction, codeOutput);
        }
    },
    FORMAT_35MI { // from class: com.android.dx.io.instructions.InstructionCodec.27
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return InstructionCodec.decodeRegisterList(this, i, codeInput);
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            InstructionCodec.encodeRegisterList(decodedInstruction, codeOutput);
        }
    },
    FORMAT_3RC { // from class: com.android.dx.io.instructions.InstructionCodec.28
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return InstructionCodec.decodeRegisterRange(this, i, codeInput);
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            InstructionCodec.encodeRegisterRange(decodedInstruction, codeOutput);
        }
    },
    FORMAT_3RMS { // from class: com.android.dx.io.instructions.InstructionCodec.29
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return InstructionCodec.decodeRegisterRange(this, i, codeInput);
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            InstructionCodec.encodeRegisterRange(decodedInstruction, codeOutput);
        }
    },
    FORMAT_3RMI { // from class: com.android.dx.io.instructions.InstructionCodec.30
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return InstructionCodec.decodeRegisterRange(this, i, codeInput);
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            InstructionCodec.encodeRegisterRange(decodedInstruction, codeOutput);
        }
    },
    FORMAT_51L { // from class: com.android.dx.io.instructions.InstructionCodec.31
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new OneRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, codeInput.readLong(), InstructionCodec.byte1(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            long literal = decodedInstruction.getLiteral();
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), InstructionCodec.unit0(literal), InstructionCodec.unit1(literal), InstructionCodec.unit2(literal), InstructionCodec.unit3(literal));
        }
    },
    FORMAT_45CC { // from class: com.android.dx.io.instructions.InstructionCodec.32
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            int iByte0 = InstructionCodec.byte0(i);
            if (iByte0 == 250) {
                int iNibble2 = InstructionCodec.nibble2(i);
                int iNibble3 = InstructionCodec.nibble3(i);
                int i2 = codeInput.read();
                int i3 = codeInput.read();
                int iNibble0 = InstructionCodec.nibble0(i3);
                int iNibble1 = InstructionCodec.nibble1(i3);
                int iNibble22 = InstructionCodec.nibble2(i3);
                int iNibble32 = InstructionCodec.nibble3(i3);
                int i4 = codeInput.read();
                IndexType indexType = OpcodeInfo.getIndexType(iByte0);
                if (iNibble3 < 1 || iNibble3 > 5) {
                    DexMerger$$ExternalSyntheticBUOutline0.m223m("bogus registerCount: ", Hex.uNibble(iNibble3));
                    return null;
                }
                return new InvokePolymorphicDecodedInstruction(this, iByte0, i2, indexType, i4, Arrays.copyOfRange(new int[]{iNibble0, iNibble1, iNibble22, iNibble32, iNibble2}, 0, iNibble3));
            }
            ByteString$$ExternalSyntheticBUOutline0.m979m(String.valueOf(iByte0));
            return null;
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            InvokePolymorphicDecodedInstruction invokePolymorphicDecodedInstruction = (InvokePolymorphicDecodedInstruction) decodedInstruction;
            codeOutput.write(InstructionCodec.codeUnit(invokePolymorphicDecodedInstruction.getOpcode(), InstructionCodec.makeByte(invokePolymorphicDecodedInstruction.getG(), invokePolymorphicDecodedInstruction.getRegisterCount())), invokePolymorphicDecodedInstruction.getIndexUnit(), InstructionCodec.codeUnit(invokePolymorphicDecodedInstruction.getC(), invokePolymorphicDecodedInstruction.getD(), invokePolymorphicDecodedInstruction.getE(), invokePolymorphicDecodedInstruction.getF()), invokePolymorphicDecodedInstruction.getProtoIndex());
        }
    },
    FORMAT_4RCC { // from class: com.android.dx.io.instructions.InstructionCodec.33
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            int iByte0 = InstructionCodec.byte0(i);
            if (iByte0 == 251) {
                int iByte1 = InstructionCodec.byte1(i);
                return new InvokePolymorphicRangeDecodedInstruction(this, iByte0, codeInput.read(), OpcodeInfo.getIndexType(iByte0), codeInput.read(), iByte1, codeInput.read());
            }
            ByteString$$ExternalSyntheticBUOutline0.m979m(String.valueOf(iByte0));
            return null;
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getRegisterCount()), decodedInstruction.getIndexUnit(), decodedInstruction.getCUnit(), decodedInstruction.getProtoIndex());
        }
    },
    FORMAT_PACKED_SWITCH_PAYLOAD { // from class: com.android.dx.io.instructions.InstructionCodec.34
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            int iBaseAddressForCursor = codeInput.baseAddressForCursor() - 1;
            int i2 = codeInput.read();
            int i3 = codeInput.readInt();
            int[] iArr = new int[i2];
            for (int i4 = 0; i4 < i2; i4++) {
                iArr[i4] = codeInput.readInt() + iBaseAddressForCursor;
            }
            return new PackedSwitchPayloadDecodedInstruction(this, i, i3, iArr);
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            PackedSwitchPayloadDecodedInstruction packedSwitchPayloadDecodedInstruction = (PackedSwitchPayloadDecodedInstruction) decodedInstruction;
            int[] targets = packedSwitchPayloadDecodedInstruction.getTargets();
            int iBaseAddressForCursor = codeOutput.baseAddressForCursor();
            codeOutput.write(packedSwitchPayloadDecodedInstruction.getOpcodeUnit());
            codeOutput.write(InstructionCodec.asUnsignedUnit(targets.length));
            codeOutput.writeInt(packedSwitchPayloadDecodedInstruction.getFirstKey());
            for (int i : targets) {
                codeOutput.writeInt(i - iBaseAddressForCursor);
            }
        }
    },
    FORMAT_SPARSE_SWITCH_PAYLOAD { // from class: com.android.dx.io.instructions.InstructionCodec.35
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            int iBaseAddressForCursor = codeInput.baseAddressForCursor() - 1;
            int i2 = codeInput.read();
            int[] iArr = new int[i2];
            int[] iArr2 = new int[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                iArr[i3] = codeInput.readInt();
            }
            for (int i4 = 0; i4 < i2; i4++) {
                iArr2[i4] = codeInput.readInt() + iBaseAddressForCursor;
            }
            return new SparseSwitchPayloadDecodedInstruction(this, i, iArr, iArr2);
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            SparseSwitchPayloadDecodedInstruction sparseSwitchPayloadDecodedInstruction = (SparseSwitchPayloadDecodedInstruction) decodedInstruction;
            int[] keys = sparseSwitchPayloadDecodedInstruction.getKeys();
            int[] targets = sparseSwitchPayloadDecodedInstruction.getTargets();
            int iBaseAddressForCursor = codeOutput.baseAddressForCursor();
            codeOutput.write(sparseSwitchPayloadDecodedInstruction.getOpcodeUnit());
            codeOutput.write(InstructionCodec.asUnsignedUnit(targets.length));
            for (int i : keys) {
                codeOutput.writeInt(i);
            }
            for (int i2 : targets) {
                codeOutput.writeInt(i2 - iBaseAddressForCursor);
            }
        }
    },
    FORMAT_FILL_ARRAY_DATA_PAYLOAD { // from class: com.android.dx.io.instructions.InstructionCodec.36
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            int i2 = codeInput.read();
            int i3 = codeInput.readInt();
            int i4 = 0;
            if (i2 == 1) {
                byte[] bArr = new byte[i3];
                boolean z = true;
                int i5 = 0;
                while (i4 < i3) {
                    if (z) {
                        i5 = codeInput.read();
                    }
                    bArr[i4] = (byte) (i5 & 255);
                    i5 >>= 8;
                    i4++;
                    z = !z;
                }
                return new FillArrayDataPayloadDecodedInstruction((InstructionCodec) this, i, bArr);
            }
            if (i2 == 2) {
                short[] sArr = new short[i3];
                while (i4 < i3) {
                    sArr[i4] = (short) codeInput.read();
                    i4++;
                }
                return new FillArrayDataPayloadDecodedInstruction((InstructionCodec) this, i, sArr);
            }
            if (i2 == 4) {
                int[] iArr = new int[i3];
                while (i4 < i3) {
                    iArr[i4] = codeInput.readInt();
                    i4++;
                }
                return new FillArrayDataPayloadDecodedInstruction((InstructionCodec) this, i, iArr);
            }
            if (i2 == 8) {
                long[] jArr = new long[i3];
                while (i4 < i3) {
                    jArr[i4] = codeInput.readLong();
                    i4++;
                }
                return new FillArrayDataPayloadDecodedInstruction(this, i, jArr);
            }
            DexMerger$$ExternalSyntheticBUOutline0.m223m("bogus element_width: ", Hex.m231u2(i2));
            return null;
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            FillArrayDataPayloadDecodedInstruction fillArrayDataPayloadDecodedInstruction = (FillArrayDataPayloadDecodedInstruction) decodedInstruction;
            short elementWidthUnit = fillArrayDataPayloadDecodedInstruction.getElementWidthUnit();
            Object data = fillArrayDataPayloadDecodedInstruction.getData();
            codeOutput.write(fillArrayDataPayloadDecodedInstruction.getOpcodeUnit());
            codeOutput.write(elementWidthUnit);
            codeOutput.writeInt(fillArrayDataPayloadDecodedInstruction.getSize());
            if (elementWidthUnit == 1) {
                codeOutput.write((byte[]) data);
                return;
            }
            if (elementWidthUnit == 2) {
                codeOutput.write((short[]) data);
                return;
            }
            if (elementWidthUnit == 4) {
                codeOutput.write((int[]) data);
            } else if (elementWidthUnit == 8) {
                codeOutput.write((long[]) data);
            } else {
                DexMerger$$ExternalSyntheticBUOutline0.m223m("bogus element_width: ", Hex.m231u2(elementWidthUnit));
            }
        }
    };

    public static int byte0(int i) {
        return i & 255;
    }

    public static int byte1(int i) {
        return (i >> 8) & 255;
    }

    private static int byte2(int i) {
        return (i >> 16) & 255;
    }

    private static int byte3(int i) {
        return i >>> 24;
    }

    public static int nibble0(int i) {
        return i & 15;
    }

    public static int nibble1(int i) {
        return (i >> 4) & 15;
    }

    public static int nibble2(int i) {
        return (i >> 8) & 15;
    }

    public static int nibble3(int i) {
        return (i >> 12) & 15;
    }

    public static short unit0(int i) {
        return (short) i;
    }

    public static short unit0(long j) {
        return (short) j;
    }

    public static short unit1(int i) {
        return (short) (i >> 16);
    }

    public static short unit1(long j) {
        return (short) (j >> 16);
    }

    public static short unit2(long j) {
        return (short) (j >> 32);
    }

    public static short unit3(long j) {
        return (short) (j >> 48);
    }

    public abstract DecodedInstruction decode(int i, CodeInput codeInput);

    public abstract void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput);

    /* synthetic */ InstructionCodec(C09511 c09511) {
        this();
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$1 */
    public enum C09511 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new ZeroRegisterDecodedInstruction(this, i, 0, null, 0, 0L);
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(decodedInstruction.getOpcodeUnit());
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$2 */
    public enum C09622 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new ZeroRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, InstructionCodec.byte1(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(decodedInstruction.getOpcodeUnit());
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$3 */
    public enum C09733 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new TwoRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, 0L, InstructionCodec.nibble2(i), InstructionCodec.nibble3(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcodeUnit(), InstructionCodec.makeByte(decodedInstruction.getA(), decodedInstruction.getB())));
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$4 */
    public enum C09814 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new OneRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, (InstructionCodec.nibble3(i) << 28) >> 28, InstructionCodec.nibble2(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcodeUnit(), InstructionCodec.makeByte(decodedInstruction.getA(), decodedInstruction.getLiteralNibble())));
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$5 */
    public enum C09825 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new OneRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, 0L, InstructionCodec.byte1(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()));
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$6 */
    public enum C09836 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new ZeroRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, (codeInput.cursor() - 1) + ((byte) InstructionCodec.byte1(i)), 0L);
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getTargetByte(codeOutput.cursor())));
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$7 */
    public enum C09847 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new ZeroRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, (codeInput.cursor() - 1) + ((short) codeInput.read()), InstructionCodec.byte1(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(decodedInstruction.getOpcodeUnit(), decodedInstruction.getTargetUnit(codeOutput.cursor()));
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$8 */
    public enum C09858 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new ZeroRegisterDecodedInstruction(this, InstructionCodec.byte0(i), codeInput.read(), IndexType.VARIES, 0, InstructionCodec.byte1(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getLiteralByte()), decodedInstruction.getIndexUnit());
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$9 */
    public enum C09869 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new TwoRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, 0L, InstructionCodec.byte1(i), codeInput.read());
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), decodedInstruction.getBUnit());
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$10 */
    public enum C095210 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new OneRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, (codeInput.cursor() - 1) + ((short) codeInput.read()), 0L, InstructionCodec.byte1(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), decodedInstruction.getTargetUnit(codeOutput.cursor()));
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$11 */
    public enum C095311 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new OneRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, (short) codeInput.read(), InstructionCodec.byte1(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), decodedInstruction.getLiteralUnit());
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$12 */
    public enum C095412 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            int iByte0 = InstructionCodec.byte0(i);
            return new OneRegisterDecodedInstruction(this, iByte0, 0, null, 0, ((long) ((short) codeInput.read())) << (iByte0 == 21 ? (char) 16 : '0'), InstructionCodec.byte1(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            int opcode = decodedInstruction.getOpcode();
            codeOutput.write(InstructionCodec.codeUnit(opcode, decodedInstruction.getA()), (short) (decodedInstruction.getLiteral() >> (opcode == 21 ? (char) 16 : '0')));
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$13 */
    public enum C095513 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            int iByte0 = InstructionCodec.byte0(i);
            return new OneRegisterDecodedInstruction(this, iByte0, codeInput.read(), OpcodeInfo.getIndexType(iByte0), 0, 0L, InstructionCodec.byte1(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), decodedInstruction.getIndexUnit());
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$14 */
    public enum C095614 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            int iByte0 = InstructionCodec.byte0(i);
            int iByte1 = InstructionCodec.byte1(i);
            int i2 = codeInput.read();
            return new ThreeRegisterDecodedInstruction(this, iByte0, 0, null, 0, 0L, iByte1, InstructionCodec.byte0(i2), InstructionCodec.byte1(i2));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), InstructionCodec.codeUnit(decodedInstruction.getB(), decodedInstruction.getC()));
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$15 */
    public enum C095715 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new TwoRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, (byte) InstructionCodec.byte1(r11), InstructionCodec.byte1(i), InstructionCodec.byte0(codeInput.read()));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), InstructionCodec.codeUnit(decodedInstruction.getB(), decodedInstruction.getLiteralByte()));
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$16 */
    public enum C095816 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new TwoRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, (codeInput.cursor() - 1) + ((short) codeInput.read()), 0L, InstructionCodec.nibble2(i), InstructionCodec.nibble3(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), InstructionCodec.makeByte(decodedInstruction.getA(), decodedInstruction.getB())), decodedInstruction.getTargetUnit(codeOutput.cursor()));
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$17 */
    public enum C095917 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new TwoRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, (short) codeInput.read(), InstructionCodec.nibble2(i), InstructionCodec.nibble3(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), InstructionCodec.makeByte(decodedInstruction.getA(), decodedInstruction.getB())), decodedInstruction.getLiteralUnit());
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$18 */
    public enum C096018 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            int iByte0 = InstructionCodec.byte0(i);
            return new TwoRegisterDecodedInstruction(this, iByte0, codeInput.read(), OpcodeInfo.getIndexType(iByte0), 0, 0L, InstructionCodec.nibble2(i), InstructionCodec.nibble3(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), InstructionCodec.makeByte(decodedInstruction.getA(), decodedInstruction.getB())), decodedInstruction.getIndexUnit());
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$19 */
    public enum C096119 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new TwoRegisterDecodedInstruction(this, InstructionCodec.byte0(i), codeInput.read(), IndexType.FIELD_OFFSET, 0, 0L, InstructionCodec.nibble2(i), InstructionCodec.nibble3(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), InstructionCodec.makeByte(decodedInstruction.getA(), decodedInstruction.getB())), decodedInstruction.getIndexUnit());
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$20 */
    public enum C096320 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new ZeroRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, (codeInput.cursor() - 1) + codeInput.readInt(), InstructionCodec.byte1(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            int target = decodedInstruction.getTarget(codeOutput.cursor());
            codeOutput.write(decodedInstruction.getOpcodeUnit(), InstructionCodec.unit0(target), InstructionCodec.unit1(target));
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$21 */
    public enum C096421 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new TwoRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, InstructionCodec.byte1(i), codeInput.read(), codeInput.read());
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(decodedInstruction.getOpcodeUnit(), decodedInstruction.getAUnit(), decodedInstruction.getBUnit());
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$22 */
    public enum C096522 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new OneRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, codeInput.readInt(), InstructionCodec.byte1(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            int literalInt = decodedInstruction.getLiteralInt();
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), InstructionCodec.unit0(literalInt), InstructionCodec.unit1(literalInt));
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$23 */
    public enum C096623 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            int iCursor = codeInput.cursor() - 1;
            int iByte0 = InstructionCodec.byte0(i);
            int iByte1 = InstructionCodec.byte1(i);
            int i2 = iCursor + codeInput.readInt();
            if (iByte0 == 43 || iByte0 == 44) {
                codeInput.setBaseAddress(i2, iCursor);
            }
            return new OneRegisterDecodedInstruction(this, iByte0, 0, null, i2, 0L, iByte1);
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            int target = decodedInstruction.getTarget(codeOutput.cursor());
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), InstructionCodec.unit0(target), InstructionCodec.unit1(target));
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$24 */
    public enum C096724 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            int iByte0 = InstructionCodec.byte0(i);
            return new OneRegisterDecodedInstruction(this, iByte0, codeInput.readInt(), OpcodeInfo.getIndexType(iByte0), 0, 0L, InstructionCodec.byte1(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            int index = decodedInstruction.getIndex();
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), InstructionCodec.unit0(index), InstructionCodec.unit1(index));
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$25 */
    public enum C096825 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return InstructionCodec.decodeRegisterList(this, i, codeInput);
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            InstructionCodec.encodeRegisterList(decodedInstruction, codeOutput);
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$26 */
    public enum C096926 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return InstructionCodec.decodeRegisterList(this, i, codeInput);
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            InstructionCodec.encodeRegisterList(decodedInstruction, codeOutput);
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$27 */
    public enum C097027 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return InstructionCodec.decodeRegisterList(this, i, codeInput);
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            InstructionCodec.encodeRegisterList(decodedInstruction, codeOutput);
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$28 */
    public enum C097128 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return InstructionCodec.decodeRegisterRange(this, i, codeInput);
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            InstructionCodec.encodeRegisterRange(decodedInstruction, codeOutput);
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$29 */
    public enum C097229 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return InstructionCodec.decodeRegisterRange(this, i, codeInput);
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            InstructionCodec.encodeRegisterRange(decodedInstruction, codeOutput);
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$30 */
    public enum C097430 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return InstructionCodec.decodeRegisterRange(this, i, codeInput);
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            InstructionCodec.encodeRegisterRange(decodedInstruction, codeOutput);
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$31 */
    public enum C097531 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            return new OneRegisterDecodedInstruction(this, InstructionCodec.byte0(i), 0, null, 0, codeInput.readLong(), InstructionCodec.byte1(i));
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            long literal = decodedInstruction.getLiteral();
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getA()), InstructionCodec.unit0(literal), InstructionCodec.unit1(literal), InstructionCodec.unit2(literal), InstructionCodec.unit3(literal));
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$32 */
    public enum C097632 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            int iByte0 = InstructionCodec.byte0(i);
            if (iByte0 == 250) {
                int iNibble2 = InstructionCodec.nibble2(i);
                int iNibble3 = InstructionCodec.nibble3(i);
                int i2 = codeInput.read();
                int i3 = codeInput.read();
                int iNibble0 = InstructionCodec.nibble0(i3);
                int iNibble1 = InstructionCodec.nibble1(i3);
                int iNibble22 = InstructionCodec.nibble2(i3);
                int iNibble32 = InstructionCodec.nibble3(i3);
                int i4 = codeInput.read();
                IndexType indexType = OpcodeInfo.getIndexType(iByte0);
                if (iNibble3 < 1 || iNibble3 > 5) {
                    DexMerger$$ExternalSyntheticBUOutline0.m223m("bogus registerCount: ", Hex.uNibble(iNibble3));
                    return null;
                }
                return new InvokePolymorphicDecodedInstruction(this, iByte0, i2, indexType, i4, Arrays.copyOfRange(new int[]{iNibble0, iNibble1, iNibble22, iNibble32, iNibble2}, 0, iNibble3));
            }
            ByteString$$ExternalSyntheticBUOutline0.m979m(String.valueOf(iByte0));
            return null;
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            InvokePolymorphicDecodedInstruction invokePolymorphicDecodedInstruction = (InvokePolymorphicDecodedInstruction) decodedInstruction;
            codeOutput.write(InstructionCodec.codeUnit(invokePolymorphicDecodedInstruction.getOpcode(), InstructionCodec.makeByte(invokePolymorphicDecodedInstruction.getG(), invokePolymorphicDecodedInstruction.getRegisterCount())), invokePolymorphicDecodedInstruction.getIndexUnit(), InstructionCodec.codeUnit(invokePolymorphicDecodedInstruction.getC(), invokePolymorphicDecodedInstruction.getD(), invokePolymorphicDecodedInstruction.getE(), invokePolymorphicDecodedInstruction.getF()), invokePolymorphicDecodedInstruction.getProtoIndex());
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$33 */
    public enum C097733 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            int iByte0 = InstructionCodec.byte0(i);
            if (iByte0 == 251) {
                int iByte1 = InstructionCodec.byte1(i);
                return new InvokePolymorphicRangeDecodedInstruction(this, iByte0, codeInput.read(), OpcodeInfo.getIndexType(iByte0), codeInput.read(), iByte1, codeInput.read());
            }
            ByteString$$ExternalSyntheticBUOutline0.m979m(String.valueOf(iByte0));
            return null;
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            codeOutput.write(InstructionCodec.codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getRegisterCount()), decodedInstruction.getIndexUnit(), decodedInstruction.getCUnit(), decodedInstruction.getProtoIndex());
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$34 */
    public enum C097834 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            int iBaseAddressForCursor = codeInput.baseAddressForCursor() - 1;
            int i2 = codeInput.read();
            int i3 = codeInput.readInt();
            int[] iArr = new int[i2];
            for (int i4 = 0; i4 < i2; i4++) {
                iArr[i4] = codeInput.readInt() + iBaseAddressForCursor;
            }
            return new PackedSwitchPayloadDecodedInstruction(this, i, i3, iArr);
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            PackedSwitchPayloadDecodedInstruction packedSwitchPayloadDecodedInstruction = (PackedSwitchPayloadDecodedInstruction) decodedInstruction;
            int[] targets = packedSwitchPayloadDecodedInstruction.getTargets();
            int iBaseAddressForCursor = codeOutput.baseAddressForCursor();
            codeOutput.write(packedSwitchPayloadDecodedInstruction.getOpcodeUnit());
            codeOutput.write(InstructionCodec.asUnsignedUnit(targets.length));
            codeOutput.writeInt(packedSwitchPayloadDecodedInstruction.getFirstKey());
            for (int i : targets) {
                codeOutput.writeInt(i - iBaseAddressForCursor);
            }
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$35 */
    public enum C097935 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            int iBaseAddressForCursor = codeInput.baseAddressForCursor() - 1;
            int i2 = codeInput.read();
            int[] iArr = new int[i2];
            int[] iArr2 = new int[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                iArr[i3] = codeInput.readInt();
            }
            for (int i4 = 0; i4 < i2; i4++) {
                iArr2[i4] = codeInput.readInt() + iBaseAddressForCursor;
            }
            return new SparseSwitchPayloadDecodedInstruction(this, i, iArr, iArr2);
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            SparseSwitchPayloadDecodedInstruction sparseSwitchPayloadDecodedInstruction = (SparseSwitchPayloadDecodedInstruction) decodedInstruction;
            int[] keys = sparseSwitchPayloadDecodedInstruction.getKeys();
            int[] targets = sparseSwitchPayloadDecodedInstruction.getTargets();
            int iBaseAddressForCursor = codeOutput.baseAddressForCursor();
            codeOutput.write(sparseSwitchPayloadDecodedInstruction.getOpcodeUnit());
            codeOutput.write(InstructionCodec.asUnsignedUnit(targets.length));
            for (int i : keys) {
                codeOutput.writeInt(i);
            }
            for (int i2 : targets) {
                codeOutput.writeInt(i2 - iBaseAddressForCursor);
            }
        }
    }

    /* JADX INFO: renamed from: com.android.dx.io.instructions.InstructionCodec$36 */
    public enum C098036 extends InstructionCodec {
        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public DecodedInstruction decode(int i, CodeInput codeInput) {
            int i2 = codeInput.read();
            int i3 = codeInput.readInt();
            int i4 = 0;
            if (i2 == 1) {
                byte[] bArr = new byte[i3];
                boolean z = true;
                int i5 = 0;
                while (i4 < i3) {
                    if (z) {
                        i5 = codeInput.read();
                    }
                    bArr[i4] = (byte) (i5 & 255);
                    i5 >>= 8;
                    i4++;
                    z = !z;
                }
                return new FillArrayDataPayloadDecodedInstruction((InstructionCodec) this, i, bArr);
            }
            if (i2 == 2) {
                short[] sArr = new short[i3];
                while (i4 < i3) {
                    sArr[i4] = (short) codeInput.read();
                    i4++;
                }
                return new FillArrayDataPayloadDecodedInstruction((InstructionCodec) this, i, sArr);
            }
            if (i2 == 4) {
                int[] iArr = new int[i3];
                while (i4 < i3) {
                    iArr[i4] = codeInput.readInt();
                    i4++;
                }
                return new FillArrayDataPayloadDecodedInstruction((InstructionCodec) this, i, iArr);
            }
            if (i2 == 8) {
                long[] jArr = new long[i3];
                while (i4 < i3) {
                    jArr[i4] = codeInput.readLong();
                    i4++;
                }
                return new FillArrayDataPayloadDecodedInstruction(this, i, jArr);
            }
            DexMerger$$ExternalSyntheticBUOutline0.m223m("bogus element_width: ", Hex.m231u2(i2));
            return null;
        }

        @Override // com.android.p006dx.p009io.instructions.InstructionCodec
        public void encode(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
            FillArrayDataPayloadDecodedInstruction fillArrayDataPayloadDecodedInstruction = (FillArrayDataPayloadDecodedInstruction) decodedInstruction;
            short elementWidthUnit = fillArrayDataPayloadDecodedInstruction.getElementWidthUnit();
            Object data = fillArrayDataPayloadDecodedInstruction.getData();
            codeOutput.write(fillArrayDataPayloadDecodedInstruction.getOpcodeUnit());
            codeOutput.write(elementWidthUnit);
            codeOutput.writeInt(fillArrayDataPayloadDecodedInstruction.getSize());
            if (elementWidthUnit == 1) {
                codeOutput.write((byte[]) data);
                return;
            }
            if (elementWidthUnit == 2) {
                codeOutput.write((short[]) data);
                return;
            }
            if (elementWidthUnit == 4) {
                codeOutput.write((int[]) data);
            } else if (elementWidthUnit == 8) {
                codeOutput.write((long[]) data);
            } else {
                DexMerger$$ExternalSyntheticBUOutline0.m223m("bogus element_width: ", Hex.m231u2(elementWidthUnit));
            }
        }
    }

    public static DecodedInstruction decodeRegisterList(InstructionCodec instructionCodec, int i, CodeInput codeInput) {
        int iByte0 = byte0(i);
        int iNibble2 = nibble2(i);
        int iNibble3 = nibble3(i);
        int i2 = codeInput.read();
        int i3 = codeInput.read();
        int iNibble0 = nibble0(i3);
        int iNibble1 = nibble1(i3);
        int iNibble22 = nibble2(i3);
        int iNibble32 = nibble3(i3);
        IndexType indexType = OpcodeInfo.getIndexType(iByte0);
        if (iNibble3 == 0) {
            return new ZeroRegisterDecodedInstruction(instructionCodec, iByte0, i2, indexType, 0, 0L);
        }
        if (iNibble3 == 1) {
            return new OneRegisterDecodedInstruction(instructionCodec, iByte0, i2, indexType, 0, 0L, iNibble0);
        }
        if (iNibble3 == 2) {
            return new TwoRegisterDecodedInstruction(instructionCodec, iByte0, i2, indexType, 0, 0L, iNibble0, iNibble1);
        }
        if (iNibble3 == 3) {
            return new ThreeRegisterDecodedInstruction(instructionCodec, iByte0, i2, indexType, 0, 0L, iNibble0, iNibble1, iNibble22);
        }
        if (iNibble3 == 4) {
            return new FourRegisterDecodedInstruction(instructionCodec, iByte0, i2, indexType, 0, 0L, iNibble0, iNibble1, iNibble22, iNibble32);
        }
        if (iNibble3 == 5) {
            return new FiveRegisterDecodedInstruction(instructionCodec, iByte0, i2, indexType, 0, 0L, iNibble0, iNibble1, iNibble22, iNibble32, iNibble2);
        }
        DexMerger$$ExternalSyntheticBUOutline0.m223m("bogus registerCount: ", Hex.uNibble(iNibble3));
        return null;
    }

    public static void encodeRegisterList(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
        codeOutput.write(codeUnit(decodedInstruction.getOpcode(), makeByte(decodedInstruction.getE(), decodedInstruction.getRegisterCount())), decodedInstruction.getIndexUnit(), codeUnit(decodedInstruction.getA(), decodedInstruction.getB(), decodedInstruction.getC(), decodedInstruction.getD()));
    }

    public static DecodedInstruction decodeRegisterRange(InstructionCodec instructionCodec, int i, CodeInput codeInput) {
        int iByte0 = byte0(i);
        int iByte1 = byte1(i);
        return new RegisterRangeDecodedInstruction(instructionCodec, iByte0, codeInput.read(), OpcodeInfo.getIndexType(iByte0), 0, 0L, codeInput.read(), iByte1);
    }

    public static void encodeRegisterRange(DecodedInstruction decodedInstruction, CodeOutput codeOutput) {
        codeOutput.write(codeUnit(decodedInstruction.getOpcode(), decodedInstruction.getRegisterCount()), decodedInstruction.getIndexUnit(), decodedInstruction.getAUnit());
    }

    public static short codeUnit(int i, int i2) {
        if ((i & (-256)) != 0) {
            g$$ExternalSyntheticBUOutline1.m207m("bogus lowByte");
            return (short) 0;
        }
        if ((i2 & (-256)) == 0) {
            return (short) (i | (i2 << 8));
        }
        g$$ExternalSyntheticBUOutline1.m207m("bogus highByte");
        return (short) 0;
    }

    public static short codeUnit(int i, int i2, int i3, int i4) {
        if ((i & (-16)) != 0) {
            g$$ExternalSyntheticBUOutline1.m207m("bogus nibble0");
            return (short) 0;
        }
        if ((i2 & (-16)) != 0) {
            g$$ExternalSyntheticBUOutline1.m207m("bogus nibble1");
            return (short) 0;
        }
        if ((i3 & (-16)) != 0) {
            g$$ExternalSyntheticBUOutline1.m207m("bogus nibble2");
            return (short) 0;
        }
        if ((i4 & (-16)) == 0) {
            return (short) (i | (i2 << 4) | (i3 << 8) | (i4 << 12));
        }
        g$$ExternalSyntheticBUOutline1.m207m("bogus nibble3");
        return (short) 0;
    }

    public static int makeByte(int i, int i2) {
        if ((i & (-16)) != 0) {
            g$$ExternalSyntheticBUOutline1.m207m("bogus lowNibble");
            return 0;
        }
        if ((i2 & (-16)) == 0) {
            return i | (i2 << 4);
        }
        g$$ExternalSyntheticBUOutline1.m207m("bogus highNibble");
        return 0;
    }

    public static short asUnsignedUnit(int i) {
        if (((-65536) & i) == 0) {
            return (short) i;
        }
        g$$ExternalSyntheticBUOutline1.m207m("bogus unsigned code unit");
        return (short) 0;
    }
}
