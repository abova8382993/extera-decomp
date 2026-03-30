package com.google.android.play.integrity.internal;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.d */
/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractC1590d {
    /* JADX INFO: renamed from: a */
    public static final List m417a(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            AbstractC1592f abstractC1592f = (AbstractC1592f) it.next();
            Bundle bundle = new Bundle();
            bundle.putInt("event_type", abstractC1592f.mo419a());
            bundle.putLong("event_timestamp", abstractC1592f.mo420b());
            arrayList.add(bundle);
        }
        return arrayList;
    }

    /* JADX INFO: renamed from: b */
    public static final void m418b(int i, List list) {
        list.add(AbstractC1592f.m421c(i, System.currentTimeMillis()));
    }
}
