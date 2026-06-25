package com.android.p006dx.p007cf.code;

import com.android.p006dx.rop.cst.Constant;
import com.android.p006dx.rop.cst.CstDouble;
import com.android.p006dx.rop.cst.CstFloat;
import com.android.p006dx.rop.cst.CstInteger;
import com.android.p006dx.rop.cst.CstLong;
import com.android.p006dx.rop.cst.CstMethodHandle;
import com.android.p006dx.rop.cst.CstProtoRef;
import com.android.p006dx.rop.cst.CstString;
import com.android.p006dx.rop.cst.CstType;
import com.android.p006dx.util.FixedSizeList;
import com.sun.jna.Native$$ExternalSyntheticBUOutline5;

/* JADX INFO: loaded from: classes4.dex */
public class BootstrapMethodArgumentsList extends FixedSizeList {
    public BootstrapMethodArgumentsList(int i) {
        super(i);
    }

    public Constant get(int i) {
        return (Constant) get0(i);
    }

    public void set(int i, Constant constant) {
        if ((constant instanceof CstString) || (constant instanceof CstType) || (constant instanceof CstInteger) || (constant instanceof CstLong) || (constant instanceof CstFloat) || (constant instanceof CstDouble) || (constant instanceof CstMethodHandle) || (constant instanceof CstProtoRef)) {
            set0(i, constant);
        } else {
            Native$$ExternalSyntheticBUOutline5.m554m("bad type for bootstrap argument: ", constant.getClass());
        }
    }
}
