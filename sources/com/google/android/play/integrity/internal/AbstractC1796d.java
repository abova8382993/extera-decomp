package com.google.android.play.integrity.internal;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.d */
/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractC1796d {
    /* JADX INFO: renamed from: a */
    public static final List m478a(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            AbstractC1798f abstractC1798f = (AbstractC1798f) it.next();
            Bundle bundle = new Bundle();
            bundle.putInt("event_type", abstractC1798f.mo480a());
            bundle.putLong("event_timestamp", abstractC1798f.mo481b());
            arrayList.add(bundle);
        }
        return arrayList;
    }

    /* JADX INFO: renamed from: b */
    public static final void m479b(int i, List list) {
        list.add(AbstractC1798f.m482c(i, System.currentTimeMillis()));
    }
}
