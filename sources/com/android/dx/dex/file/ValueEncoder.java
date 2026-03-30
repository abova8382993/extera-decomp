package com.android.dx.dex.file;

import androidx.appcompat.app.WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.dex.EncodedValueCodec;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstArray;
import com.android.dx.rop.cst.CstBoolean;
import com.android.dx.rop.cst.CstByte;
import com.android.dx.rop.cst.CstChar;
import com.android.dx.rop.cst.CstDouble;
import com.android.dx.rop.cst.CstFieldRef;
import com.android.dx.rop.cst.CstFloat;
import com.android.dx.rop.cst.CstInteger;
import com.android.dx.rop.cst.CstKnownNull;
import com.android.dx.rop.cst.CstLiteralBits;
import com.android.dx.rop.cst.CstLong;
import com.android.dx.rop.cst.CstMethodRef;
import com.android.dx.rop.cst.CstShort;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.cst.CstType;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;

/* JADX INFO: loaded from: classes4.dex */
public final class ValueEncoder {
    private final DexFile file;
    private final AnnotatedOutput out;

    public ValueEncoder(DexFile dexFile, AnnotatedOutput annotatedOutput) {
        if (dexFile == null) {
            throw new NullPointerException("file == null");
        }
        if (annotatedOutput == null) {
            throw new NullPointerException("out == null");
        }
        this.file = dexFile;
        this.out = annotatedOutput;
    }

    public void writeConstant(Constant constant) {
        int iConstantToValueType = constantToValueType(constant);
        if (iConstantToValueType != 0 && iConstantToValueType != 6 && iConstantToValueType != 2) {
            if (iConstantToValueType == 3) {
                EncodedValueCodec.writeUnsignedIntegralValue(this.out, iConstantToValueType, ((CstLiteralBits) constant).getLongBits());
                return;
            }
            if (iConstantToValueType != 4) {
                if (iConstantToValueType == 16) {
                    EncodedValueCodec.writeRightZeroExtendedValue(this.out, iConstantToValueType, ((CstFloat) constant).getLongBits() << 32);
                    return;
                }
                if (iConstantToValueType == 17) {
                    EncodedValueCodec.writeRightZeroExtendedValue(this.out, iConstantToValueType, ((CstDouble) constant).getLongBits());
                    return;
                }
                switch (iConstantToValueType) {
                    case 21:
                        this.file.getProtoIds();
                        WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(constant);
                        throw null;
                    case 22:
                        MethodHandlesSection methodHandles = this.file.getMethodHandles();
                        WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(constant);
                        EncodedValueCodec.writeUnsignedIntegralValue(this.out, iConstantToValueType, methodHandles.indexOf(null));
                        return;
                    case 23:
                        EncodedValueCodec.writeUnsignedIntegralValue(this.out, iConstantToValueType, this.file.getStringIds().indexOf((CstString) constant));
                        return;
                    case 24:
                        EncodedValueCodec.writeUnsignedIntegralValue(this.out, iConstantToValueType, this.file.getTypeIds().indexOf((CstType) constant));
                        return;
                    case 25:
                        EncodedValueCodec.writeUnsignedIntegralValue(this.out, iConstantToValueType, this.file.getFieldIds().indexOf((CstFieldRef) constant));
                        return;
                    case 26:
                        EncodedValueCodec.writeUnsignedIntegralValue(this.out, iConstantToValueType, this.file.getMethodIds().indexOf((CstMethodRef) constant));
                        return;
                    case 27:
                        WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(constant);
                        throw null;
                    case 28:
                        this.out.writeByte(iConstantToValueType);
                        writeArray((CstArray) constant, false);
                        return;
                    case 29:
                        this.out.writeByte(iConstantToValueType);
                        WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(constant);
                        throw null;
                    case 30:
                        this.out.writeByte(iConstantToValueType);
                        return;
                    case 31:
                        this.out.writeByte((((CstBoolean) constant).getIntBits() << 5) | iConstantToValueType);
                        return;
                    default:
                        throw new RuntimeException("Shouldn't happen");
                }
            }
        }
        EncodedValueCodec.writeSignedIntegralValue(this.out, iConstantToValueType, ((CstLiteralBits) constant).getLongBits());
    }

    private static int constantToValueType(Constant constant) {
        if (constant instanceof CstByte) {
            return 0;
        }
        if (constant instanceof CstShort) {
            return 2;
        }
        if (constant instanceof CstChar) {
            return 3;
        }
        if (constant instanceof CstInteger) {
            return 4;
        }
        if (constant instanceof CstLong) {
            return 6;
        }
        if (constant instanceof CstFloat) {
            return 16;
        }
        if (constant instanceof CstDouble) {
            return 17;
        }
        if (constant instanceof CstString) {
            return 23;
        }
        if (constant instanceof CstType) {
            return 24;
        }
        if (constant instanceof CstFieldRef) {
            return 25;
        }
        if (constant instanceof CstMethodRef) {
            return 26;
        }
        if (constant instanceof CstArray) {
            return 28;
        }
        if (constant instanceof CstKnownNull) {
            return 30;
        }
        if (constant instanceof CstBoolean) {
            return 31;
        }
        throw new RuntimeException("Shouldn't happen");
    }

    public void writeArray(CstArray cstArray, boolean z) {
        boolean z2 = z && this.out.annotates();
        CstArray.List list = cstArray.getList();
        int size = list.size();
        if (z2) {
            this.out.annotate("  size: " + Hex.u4(size));
        }
        this.out.writeUleb128(size);
        for (int i = 0; i < size; i++) {
            Constant constant = list.get(i);
            if (z2) {
                this.out.annotate("  [" + Integer.toHexString(i) + "] " + constantToHuman(constant));
            }
            writeConstant(constant);
        }
        if (z2) {
            this.out.endAnnotation();
        }
    }

    public static String constantToHuman(Constant constant) {
        if (constantToValueType(constant) == 30) {
            return "null";
        }
        return constant.typeName() + ' ' + constant.toHuman();
    }

    public static void addContents(DexFile dexFile, Constant constant) {
        if (constant instanceof CstArray) {
            CstArray.List list = ((CstArray) constant).getList();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                addContents(dexFile, list.get(i));
            }
            return;
        }
        dexFile.internIfAppropriate(constant);
    }
}
