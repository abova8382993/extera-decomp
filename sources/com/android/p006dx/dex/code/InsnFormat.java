package com.android.p006dx.dex.code;

import com.android.p006dx.rop.code.RegisterSpec;
import com.android.p006dx.rop.code.RegisterSpecList;
import com.android.p006dx.rop.cst.CstInteger;
import com.android.p006dx.rop.cst.CstKnownNull;
import com.android.p006dx.rop.cst.CstLiteral64;
import com.android.p006dx.rop.cst.CstLiteralBits;
import com.android.p006dx.util.AnnotatedOutput;
import com.android.p006dx.util.Hex;
import java.util.BitSet;
import org.webrtc.GlShader$$ExternalSyntheticBUOutline1;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes4.dex */
public abstract class InsnFormat {
    public static final boolean ALLOW_EXTENDED_OPCODES = true;

    public static boolean signedFitsInByte(int i) {
        return ((byte) i) == i;
    }

    public static boolean signedFitsInNibble(int i) {
        return i >= -8 && i <= 7;
    }

    public static boolean signedFitsInShort(int i) {
        return ((short) i) == i;
    }

    public static boolean unsignedFitsInByte(int i) {
        return i == (i & 255);
    }

    public static boolean unsignedFitsInNibble(int i) {
        return i == (i & 15);
    }

    public static boolean unsignedFitsInShort(int i) {
        return i == (65535 & i);
    }

    public boolean branchFits(TargetInsn targetInsn) {
        return false;
    }

    public abstract int codeSize();

    public abstract String insnArgString(DalvInsn dalvInsn);

    public abstract String insnCommentString(DalvInsn dalvInsn, boolean z);

    public abstract boolean isCompatible(DalvInsn dalvInsn);

    public abstract void writeTo(AnnotatedOutput annotatedOutput, DalvInsn dalvInsn);

    public final String listingString(DalvInsn dalvInsn, boolean z) {
        String name = dalvInsn.getOpcode().getName();
        String strInsnArgString = insnArgString(dalvInsn);
        String strInsnCommentString = insnCommentString(dalvInsn, z);
        StringBuilder sb = new StringBuilder(100);
        sb.append(name);
        if (strInsnArgString.length() != 0) {
            sb.append(' ');
            sb.append(strInsnArgString);
        }
        if (strInsnCommentString.length() != 0) {
            sb.append(" // ");
            sb.append(strInsnCommentString);
        }
        return sb.toString();
    }

    public BitSet compatibleRegs(DalvInsn dalvInsn) {
        return new BitSet();
    }

    public static String regListString(RegisterSpecList registerSpecList) {
        int size = registerSpecList.size();
        StringBuilder sb = new StringBuilder((size * 5) + 2);
        sb.append('{');
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(registerSpecList.get(i).regString());
        }
        sb.append('}');
        return sb.toString();
    }

    public static String regRangeString(RegisterSpecList registerSpecList) {
        int size = registerSpecList.size();
        StringBuilder sb = new StringBuilder(30);
        sb.append("{");
        if (size != 0) {
            if (size == 1) {
                sb.append(registerSpecList.get(0).regString());
            } else {
                RegisterSpec registerSpecWithOffset = registerSpecList.get(size - 1);
                if (registerSpecWithOffset.getCategory() == 2) {
                    registerSpecWithOffset = registerSpecWithOffset.withOffset(1);
                }
                sb.append(registerSpecList.get(0).regString());
                sb.append("..");
                sb.append(registerSpecWithOffset.regString());
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public static String literalBitsString(CstLiteralBits cstLiteralBits) {
        StringBuilder sb = new StringBuilder(100);
        sb.append('#');
        if (cstLiteralBits instanceof CstKnownNull) {
            sb.append("null");
        } else {
            sb.append(cstLiteralBits.typeName());
            sb.append(' ');
            sb.append(cstLiteralBits.toHuman());
        }
        return sb.toString();
    }

    public static String literalBitsComment(CstLiteralBits cstLiteralBits, int i) {
        long intBits;
        StringBuilder sb = new StringBuilder(20);
        sb.append("#");
        if (cstLiteralBits instanceof CstLiteral64) {
            intBits = ((CstLiteral64) cstLiteralBits).getLongBits();
        } else {
            intBits = cstLiteralBits.getIntBits();
        }
        if (i == 4) {
            sb.append(Hex.uNibble((int) intBits));
        } else if (i == 8) {
            sb.append(Hex.m230u1((int) intBits));
        } else if (i == 16) {
            sb.append(Hex.m231u2((int) intBits));
        } else if (i == 32) {
            sb.append(Hex.m233u4((int) intBits));
        } else if (i == 64) {
            sb.append(Hex.m234u8(intBits));
        } else {
            GlShader$$ExternalSyntheticBUOutline1.m1250m("shouldn't happen");
            return null;
        }
        return sb.toString();
    }

    public static String branchString(DalvInsn dalvInsn) {
        int targetAddress = ((TargetInsn) dalvInsn).getTargetAddress();
        return targetAddress == ((char) targetAddress) ? Hex.m231u2(targetAddress) : Hex.m233u4(targetAddress);
    }

    public static String branchComment(DalvInsn dalvInsn) {
        int targetOffset = ((TargetInsn) dalvInsn).getTargetOffset();
        return targetOffset == ((short) targetOffset) ? Hex.m227s2(targetOffset) : Hex.m228s4(targetOffset);
    }

    public static boolean isRegListSequential(RegisterSpecList registerSpecList) {
        int size = registerSpecList.size();
        if (size < 2) {
            return true;
        }
        int reg = registerSpecList.get(0).getReg();
        for (int i = 0; i < size; i++) {
            RegisterSpec registerSpec = registerSpecList.get(i);
            if (registerSpec.getReg() != reg) {
                return false;
            }
            reg += registerSpec.getCategory();
        }
        return true;
    }

    public static int argIndex(DalvInsn dalvInsn) {
        int value = ((CstInteger) ((CstInsn) dalvInsn).getConstant()).getValue();
        if (value >= 0) {
            return value;
        }
        g$$ExternalSyntheticBUOutline1.m207m("bogus insn");
        return 0;
    }

    public static short opcodeUnit(DalvInsn dalvInsn, int i) {
        if ((i & 255) != i) {
            g$$ExternalSyntheticBUOutline1.m207m("arg out of range 0..255");
            return (short) 0;
        }
        int opcode = dalvInsn.getOpcode().getOpcode();
        if ((opcode & 255) == opcode) {
            return (short) (opcode | (i << 8));
        }
        g$$ExternalSyntheticBUOutline1.m207m("opcode out of range 0..255");
        return (short) 0;
    }

    public static short opcodeUnit(DalvInsn dalvInsn) {
        int opcode = dalvInsn.getOpcode().getOpcode();
        if (opcode >= 256 && opcode <= 65535) {
            return (short) opcode;
        }
        g$$ExternalSyntheticBUOutline1.m207m("opcode out of range 0..65535");
        return (short) 0;
    }

    public static short codeUnit(int i, int i2) {
        if ((i & 255) != i) {
            g$$ExternalSyntheticBUOutline1.m207m("low out of range 0..255");
            return (short) 0;
        }
        if ((i2 & 255) == i2) {
            return (short) (i | (i2 << 8));
        }
        g$$ExternalSyntheticBUOutline1.m207m("high out of range 0..255");
        return (short) 0;
    }

    public static short codeUnit(int i, int i2, int i3, int i4) {
        if ((i & 15) != i) {
            g$$ExternalSyntheticBUOutline1.m207m("n0 out of range 0..15");
            return (short) 0;
        }
        if ((i2 & 15) != i2) {
            g$$ExternalSyntheticBUOutline1.m207m("n1 out of range 0..15");
            return (short) 0;
        }
        if ((i3 & 15) != i3) {
            g$$ExternalSyntheticBUOutline1.m207m("n2 out of range 0..15");
            return (short) 0;
        }
        if ((i4 & 15) == i4) {
            return (short) (i | (i2 << 4) | (i3 << 8) | (i4 << 12));
        }
        g$$ExternalSyntheticBUOutline1.m207m("n3 out of range 0..15");
        return (short) 0;
    }

    public static int makeByte(int i, int i2) {
        if ((i & 15) != i) {
            g$$ExternalSyntheticBUOutline1.m207m("low out of range 0..15");
            return 0;
        }
        if ((i2 & 15) == i2) {
            return i | (i2 << 4);
        }
        g$$ExternalSyntheticBUOutline1.m207m("high out of range 0..15");
        return 0;
    }

    public static void write(AnnotatedOutput annotatedOutput, short s) {
        annotatedOutput.writeShort(s);
    }

    public static void write(AnnotatedOutput annotatedOutput, short s, short s2) {
        annotatedOutput.writeShort(s);
        annotatedOutput.writeShort(s2);
    }

    public static void write(AnnotatedOutput annotatedOutput, short s, short s2, short s3) {
        annotatedOutput.writeShort(s);
        annotatedOutput.writeShort(s2);
        annotatedOutput.writeShort(s3);
    }

    public static void write(AnnotatedOutput annotatedOutput, short s, short s2, short s3, short s4) {
        annotatedOutput.writeShort(s);
        annotatedOutput.writeShort(s2);
        annotatedOutput.writeShort(s3);
        annotatedOutput.writeShort(s4);
    }

    public static void write(AnnotatedOutput annotatedOutput, short s, short s2, short s3, short s4, short s5) {
        annotatedOutput.writeShort(s);
        annotatedOutput.writeShort(s2);
        annotatedOutput.writeShort(s3);
        annotatedOutput.writeShort(s4);
        annotatedOutput.writeShort(s5);
    }

    public static void write(AnnotatedOutput annotatedOutput, short s, int i) {
        write(annotatedOutput, s, (short) i, (short) (i >> 16));
    }

    public static void write(AnnotatedOutput annotatedOutput, short s, int i, short s2) {
        write(annotatedOutput, s, (short) i, (short) (i >> 16), s2);
    }

    public static void write(AnnotatedOutput annotatedOutput, short s, int i, short s2, short s3) {
        write(annotatedOutput, s, (short) i, (short) (i >> 16), s2, s3);
    }

    public static void write(AnnotatedOutput annotatedOutput, short s, long j) {
        write(annotatedOutput, s, (short) j, (short) (j >> 16), (short) (j >> 32), (short) (j >> 48));
    }
}
