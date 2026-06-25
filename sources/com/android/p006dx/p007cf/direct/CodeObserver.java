package com.android.p006dx.p007cf.direct;

import com.android.p006dx.p007cf.code.ByteOps;
import com.android.p006dx.p007cf.code.BytecodeArray;
import com.android.p006dx.p007cf.code.SwitchList;
import com.android.p006dx.p007cf.iface.ParseObserver;
import com.android.p006dx.rop.cst.Constant;
import com.android.p006dx.rop.cst.CstDouble;
import com.android.p006dx.rop.cst.CstFloat;
import com.android.p006dx.rop.cst.CstInteger;
import com.android.p006dx.rop.cst.CstKnownNull;
import com.android.p006dx.rop.cst.CstLong;
import com.android.p006dx.rop.cst.CstType;
import com.android.p006dx.rop.type.Type;
import com.android.p006dx.util.ByteArray;
import com.android.p006dx.util.Hex;
import java.util.ArrayList;
import okhttp3.internal.url._UrlKt;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
public class CodeObserver implements BytecodeArray.Visitor {
    private final ByteArray bytes;
    private final ParseObserver observer;

    @Override // com.android.dx.cf.code.BytecodeArray.Visitor
    public int getPreviousOffset() {
        return -1;
    }

    @Override // com.android.dx.cf.code.BytecodeArray.Visitor
    public void setPreviousOffset(int i) {
    }

    public CodeObserver(ByteArray byteArray, ParseObserver parseObserver) {
        if (byteArray == null) {
            g$$ExternalSyntheticBUOutline2.m208m("bytes == null");
            throw null;
        }
        if (parseObserver == null) {
            g$$ExternalSyntheticBUOutline2.m208m("observer == null");
            throw null;
        }
        this.bytes = byteArray;
        this.observer = parseObserver;
    }

    @Override // com.android.dx.cf.code.BytecodeArray.Visitor
    public void visitInvalid(int i, int i2, int i3) {
        this.observer.parsed(this.bytes, i2, i3, header(i2));
    }

    @Override // com.android.dx.cf.code.BytecodeArray.Visitor
    public void visitNoArgs(int i, int i2, int i3, Type type) {
        this.observer.parsed(this.bytes, i2, i3, header(i2));
    }

    @Override // com.android.dx.cf.code.BytecodeArray.Visitor
    public void visitLocal(int i, int i2, int i3, int i4, Type type, int i5) {
        String string;
        String strM230u1 = i3 <= 3 ? Hex.m230u1(i4) : Hex.m231u2(i4);
        boolean z = i3 == 1;
        String strConcat = _UrlKt.FRAGMENT_ENCODE_SET;
        if (i != 132) {
            string = _UrlKt.FRAGMENT_ENCODE_SET;
        } else {
            StringBuilder sb = new StringBuilder(", #");
            sb.append(i3 <= 3 ? Hex.m226s1(i5) : Hex.m227s2(i5));
            string = sb.toString();
        }
        if (type.isCategory2()) {
            strConcat = (z ? "," : " //").concat(" category-2");
        }
        ParseObserver parseObserver = this.observer;
        ByteArray byteArray = this.bytes;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(header(i2));
        sb2.append(z ? " // " : " ");
        sb2.append(strM230u1);
        sb2.append(string);
        sb2.append(strConcat);
        parseObserver.parsed(byteArray, i2, i3, sb2.toString());
    }

    @Override // com.android.dx.cf.code.BytecodeArray.Visitor
    public void visitConstant(int i, int i2, int i3, Constant constant, int i4) {
        String str;
        if (constant instanceof CstKnownNull) {
            visitNoArgs(i, i2, i3, null);
            return;
        }
        if (constant instanceof CstInteger) {
            visitLiteralInt(i, i2, i3, i4);
            return;
        }
        if (constant instanceof CstLong) {
            visitLiteralLong(i, i2, i3, ((CstLong) constant).getValue());
            return;
        }
        if (constant instanceof CstFloat) {
            visitLiteralFloat(i, i2, i3, ((CstFloat) constant).getIntBits());
            return;
        }
        if (constant instanceof CstDouble) {
            visitLiteralDouble(i, i2, i3, ((CstDouble) constant).getLongBits());
            return;
        }
        if (i4 == 0) {
            str = _UrlKt.FRAGMENT_ENCODE_SET;
        } else if (i == 197) {
            str = ", " + Hex.m230u1(i4);
        } else {
            str = ", " + Hex.m231u2(i4);
        }
        this.observer.parsed(this.bytes, i2, i3, header(i2) + " " + constant + str);
    }

    @Override // com.android.dx.cf.code.BytecodeArray.Visitor
    public void visitBranch(int i, int i2, int i3, int i4) {
        String strM231u2 = i3 <= 3 ? Hex.m231u2(i4) : Hex.m233u4(i4);
        this.observer.parsed(this.bytes, i2, i3, header(i2) + " " + strM231u2);
    }

    @Override // com.android.dx.cf.code.BytecodeArray.Visitor
    public void visitSwitch(int i, int i2, int i3, SwitchList switchList, int i4) {
        int size = switchList.size();
        StringBuilder sb = new StringBuilder((size * 20) + 100);
        sb.append(header(i2));
        if (i4 != 0) {
            sb.append(" // padding: " + Hex.m233u4(i4));
        }
        sb.append('\n');
        for (int i5 = 0; i5 < size; i5++) {
            sb.append("  ");
            sb.append(Hex.m228s4(switchList.getValue(i5)));
            sb.append(": ");
            sb.append(Hex.m231u2(switchList.getTarget(i5)));
            sb.append('\n');
        }
        sb.append("  default: ");
        sb.append(Hex.m231u2(switchList.getDefaultTarget()));
        this.observer.parsed(this.bytes, i2, i3, sb.toString());
    }

    @Override // com.android.dx.cf.code.BytecodeArray.Visitor
    public void visitNewarray(int i, int i2, CstType cstType, ArrayList<Constant> arrayList) {
        String str = i2 == 1 ? " // " : " ";
        String human = cstType.getClassType().getComponentType().toHuman();
        this.observer.parsed(this.bytes, i, i2, header(i) + str + human);
    }

    private String header(int i) {
        int unsignedByte = this.bytes.getUnsignedByte(i);
        String strOpName = ByteOps.opName(unsignedByte);
        if (unsignedByte == 196) {
            strOpName = strOpName + " " + ByteOps.opName(this.bytes.getUnsignedByte(i + 1));
        }
        return Hex.m231u2(i) + ": " + strOpName;
    }

    private void visitLiteralInt(int i, int i2, int i3, int i4) {
        String str;
        String str2 = i3 == 1 ? " // " : " ";
        int unsignedByte = this.bytes.getUnsignedByte(i2);
        if (i3 == 1 || unsignedByte == 16) {
            str = "#" + Hex.m226s1(i4);
        } else if (unsignedByte == 17) {
            str = "#" + Hex.m227s2(i4);
        } else {
            str = "#" + Hex.m228s4(i4);
        }
        this.observer.parsed(this.bytes, i2, i3, header(i2) + str2 + str);
    }

    private void visitLiteralLong(int i, int i2, int i3, long j) {
        String strM229s8;
        String str = i3 == 1 ? " // " : " #";
        if (i3 == 1) {
            strM229s8 = Hex.m226s1((int) j);
        } else {
            strM229s8 = Hex.m229s8(j);
        }
        this.observer.parsed(this.bytes, i2, i3, header(i2) + str + strM229s8);
    }

    private void visitLiteralFloat(int i, int i2, int i3, int i4) {
        String str;
        if (i3 != 1) {
            str = " #" + Hex.m233u4(i4);
        } else {
            str = _UrlKt.FRAGMENT_ENCODE_SET;
        }
        this.observer.parsed(this.bytes, i2, i3, header(i2) + str + " // " + Float.intBitsToFloat(i4));
    }

    private void visitLiteralDouble(int i, int i2, int i3, long j) {
        String str;
        if (i3 != 1) {
            str = " #" + Hex.m234u8(j);
        } else {
            str = _UrlKt.FRAGMENT_ENCODE_SET;
        }
        this.observer.parsed(this.bytes, i2, i3, header(i2) + str + " // " + Double.longBitsToDouble(j));
    }
}
