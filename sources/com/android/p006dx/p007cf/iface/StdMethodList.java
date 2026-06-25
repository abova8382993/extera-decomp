package com.android.p006dx.p007cf.iface;

import com.android.p006dx.util.FixedSizeList;

/* JADX INFO: loaded from: classes4.dex */
public final class StdMethodList extends FixedSizeList implements MethodList {
    public StdMethodList(int i) {
        super(i);
    }

    @Override // com.android.p006dx.p007cf.iface.MethodList
    public Method get(int i) {
        return (Method) get0(i);
    }

    public void set(int i, Method method) {
        set0(i, method);
    }
}
