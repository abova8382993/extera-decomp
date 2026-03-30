package com.google.android.play.core.integrity;

import android.content.Context;
import com.google.android.play.integrity.internal.AbstractC1580ae;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.v */
/* JADX INFO: loaded from: classes4.dex */
final class C1570v {

    /* JADX INFO: renamed from: a */
    private static C1563o f490a;

    /* JADX INFO: renamed from: a */
    static synchronized C1563o m379a(Context context) {
        try {
            if (f490a == null) {
                C1559m c1559m = new C1559m(null);
                c1559m.m372a(AbstractC1580ae.m407a(context));
                f490a = c1559m.mo373b();
            }
        } catch (Throwable th) {
            throw th;
        }
        return f490a;
    }
}
