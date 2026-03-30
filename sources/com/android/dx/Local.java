package com.android.dx;

import com.android.dx.rop.code.RegisterSpec;

/* JADX INFO: loaded from: classes4.dex */
public final class Local {
    private final Code code;
    private int reg = -1;
    private RegisterSpec spec;
    final TypeId type;

    private Local(Code code, TypeId typeId) {
        this.code = code;
        this.type = typeId;
    }

    static Local get(Code code, TypeId typeId) {
        return new Local(code, typeId);
    }

    int initialize(int i) {
        this.reg = i;
        this.spec = RegisterSpec.make(i, this.type.ropType);
        return size();
    }

    int size() {
        return this.type.ropType.getCategory();
    }

    RegisterSpec spec() {
        if (this.spec == null) {
            this.code.initializeLocals();
            if (this.spec == null) {
                throw new AssertionError();
            }
        }
        return this.spec;
    }

    public TypeId getType() {
        return this.type;
    }

    public String toString() {
        return "v" + this.reg + "(" + this.type + ")";
    }
}
