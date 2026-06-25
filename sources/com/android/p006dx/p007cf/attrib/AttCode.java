package com.android.p006dx.p007cf.attrib;

import com.android.p006dx.p007cf.code.ByteCatchList;
import com.android.p006dx.p007cf.code.BytecodeArray;
import com.android.p006dx.p007cf.iface.AttributeList;
import com.android.p006dx.util.MutabilityException;
import p005c.g$$ExternalSyntheticBUOutline1;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
public final class AttCode extends BaseAttribute {
    public static final String ATTRIBUTE_NAME = "Code";
    private final AttributeList attributes;
    private final ByteCatchList catches;
    private final BytecodeArray code;
    private final int maxLocals;
    private final int maxStack;

    public AttCode(int i, int i2, BytecodeArray bytecodeArray, ByteCatchList byteCatchList, AttributeList attributeList) {
        super(ATTRIBUTE_NAME);
        if (i < 0) {
            g$$ExternalSyntheticBUOutline1.m207m("maxStack < 0");
            throw null;
        }
        if (i2 < 0) {
            g$$ExternalSyntheticBUOutline1.m207m("maxLocals < 0");
            throw null;
        }
        if (bytecodeArray == null) {
            g$$ExternalSyntheticBUOutline2.m208m("code == null");
            throw null;
        }
        try {
            if (byteCatchList.isMutable()) {
                throw new MutabilityException("catches.isMutable()");
            }
            try {
                if (attributeList.isMutable()) {
                    throw new MutabilityException("attributes.isMutable()");
                }
                this.maxStack = i;
                this.maxLocals = i2;
                this.code = bytecodeArray;
                this.catches = byteCatchList;
                this.attributes = attributeList;
            } catch (NullPointerException unused) {
                g$$ExternalSyntheticBUOutline2.m208m("attributes == null");
                throw null;
            }
        } catch (NullPointerException unused2) {
            g$$ExternalSyntheticBUOutline2.m208m("catches == null");
            throw null;
        }
    }

    @Override // com.android.p006dx.p007cf.iface.Attribute
    public int byteLength() {
        return this.code.byteLength() + 10 + this.catches.byteLength() + this.attributes.byteLength();
    }

    public int getMaxStack() {
        return this.maxStack;
    }

    public int getMaxLocals() {
        return this.maxLocals;
    }

    public BytecodeArray getCode() {
        return this.code;
    }

    public ByteCatchList getCatches() {
        return this.catches;
    }

    public AttributeList getAttributes() {
        return this.attributes;
    }
}
