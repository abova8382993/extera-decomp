package com.android.p006dx.p007cf.iface;

/* JADX INFO: loaded from: classes4.dex */
public interface AttributeList {
    int byteLength();

    Attribute findFirst(String str);

    Attribute findNext(Attribute attribute);

    Attribute get(int i);

    boolean isMutable();

    int size();
}
