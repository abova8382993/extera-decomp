package com.android.p006dx.p007cf.attrib;

import com.android.p006dx.rop.cst.ConstantPool;
import com.android.p006dx.util.ByteArray;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
public final class RawAttribute extends BaseAttribute {
    private final ByteArray data;
    private final ConstantPool pool;

    public RawAttribute(String str, ByteArray byteArray, ConstantPool constantPool) {
        super(str);
        if (byteArray == null) {
            g$$ExternalSyntheticBUOutline2.m208m("data == null");
            throw null;
        }
        this.data = byteArray;
        this.pool = constantPool;
    }

    public RawAttribute(String str, ByteArray byteArray, int i, int i2, ConstantPool constantPool) {
        this(str, byteArray.slice(i, i2 + i), constantPool);
    }

    public ByteArray getData() {
        return this.data;
    }

    @Override // com.android.p006dx.p007cf.iface.Attribute
    public int byteLength() {
        return this.data.size() + 6;
    }

    public ConstantPool getPool() {
        return this.pool;
    }
}
