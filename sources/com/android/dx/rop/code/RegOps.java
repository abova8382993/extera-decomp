package com.android.dx.rop.code;

import com.android.dx.util.Hex;
import org.mvel2.Operator;

/* JADX INFO: loaded from: classes4.dex */
public abstract class RegOps {
    public static String opName(int i) {
        switch (i) {
            case 1:
                return "nop";
            case 2:
                return "move";
            case 3:
                return "move-param";
            case 4:
                return "move-exception";
            case 5:
                return "const";
            case 6:
                return "goto";
            case 7:
                return "if-eq";
            case 8:
                return "if-ne";
            case 9:
                return "if-lt";
            case 10:
                return "if-ge";
            case 11:
                return "if-le";
            case 12:
                return "if-gt";
            case 13:
                return "switch";
            case 14:
                return "add";
            case 15:
                return "sub";
            case 16:
                return "mul";
            case 17:
                return "div";
            case 18:
                return "rem";
            case 19:
                return "neg";
            case 20:
                return "and";
            case 21:
                return "or";
            case 22:
                return "xor";
            case 23:
                return "shl";
            case 24:
                return "shr";
            case 25:
                return "ushr";
            case 26:
                return "not";
            case 27:
                return "cmpl";
            case 28:
                return "cmpg";
            case 29:
                return "conv";
            case 30:
                return "to-byte";
            case 31:
                return "to-char";
            case 32:
                return "to-short";
            case 33:
                return "return";
            case 34:
                return "array-length";
            case Operator.PROJECTION /* 35 */:
                return "throw";
            case Operator.CONVERTABLE_TO /* 36 */:
                return "monitor-enter";
            case Operator.END_OF_STMT /* 37 */:
                return "monitor-exit";
            case Operator.FOREACH /* 38 */:
                return "aget";
            case Operator.IF /* 39 */:
                return "aput";
            case Operator.ELSE /* 40 */:
                return "new-instance";
            case Operator.WHILE /* 41 */:
                return "new-array";
            case Operator.UNTIL /* 42 */:
                return "filled-new-array";
            case Operator.FOR /* 43 */:
                return "check-cast";
            case Operator.SWITCH /* 44 */:
                return "instance-of";
            case Operator.DO /* 45 */:
                return "get-field";
            case 46:
                return "get-static";
            case 47:
                return "put-field";
            case 48:
                return "put-static";
            case 49:
                return "invoke-static";
            case 50:
                return "invoke-virtual";
            case 51:
                return "invoke-super";
            case 52:
                return "invoke-direct";
            case 53:
                return "invoke-interface";
            case 54:
            default:
                return "unknown-" + Hex.u1(i);
            case 55:
                return "move-result";
            case 56:
                return "move-result-pseudo";
            case 57:
                return "fill-array-data";
            case 58:
                return "invoke-polymorphic";
            case 59:
                return "invoke-custom";
        }
    }
}
