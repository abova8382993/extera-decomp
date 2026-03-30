package com.android.dx.rop.code;

import com.android.dx.rop.code.Insn;
import com.android.dx.util.FixedSizeList;

/* JADX INFO: loaded from: classes4.dex */
public final class InsnList extends FixedSizeList {
    public InsnList(int i) {
        super(i);
    }

    public Insn get(int i) {
        return (Insn) get0(i);
    }

    public void set(int i, Insn insn) {
        set0(i, insn);
    }

    public Insn getLast() {
        return get(size() - 1);
    }

    public void forEach(Insn.Visitor visitor) {
        int size = size();
        for (int i = 0; i < size; i++) {
            get(i).accept(visitor);
        }
    }
}
