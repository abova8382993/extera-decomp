package com.android.p006dx.p007cf.attrib;

import com.android.p006dx.p007cf.iface.Attribute;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseAttribute implements Attribute {
    private final String name;

    public BaseAttribute(String str) {
        if (str == null) {
            g$$ExternalSyntheticBUOutline2.m208m("name == null");
            throw null;
        }
        this.name = str;
    }

    @Override // com.android.p006dx.p007cf.iface.Attribute
    public String getName() {
        return this.name;
    }
}
