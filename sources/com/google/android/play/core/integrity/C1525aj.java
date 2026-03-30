package com.google.android.play.core.integrity;

import android.content.Context;
import com.google.android.play.integrity.internal.AbstractC1580ae;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.aj */
/* JADX INFO: loaded from: classes4.dex */
final class C1525aj {

    /* JADX INFO: renamed from: a */
    private static C1567s f421a;

    /* JADX INFO: renamed from: a */
    static synchronized C1567s m353a(Context context) {
        try {
            if (f421a == null) {
                C1565q c1565q = new C1565q(null);
                c1565q.m377a(AbstractC1580ae.m407a(context));
                f421a = c1565q.mo352b();
            }
        } catch (Throwable th) {
            throw th;
        }
        return f421a;
    }
}
