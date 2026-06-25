package com.google.android.play.core.integrity;

import android.content.Context;
import com.google.android.play.integrity.internal.AbstractC1786ae;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.aj */
/* JADX INFO: loaded from: classes5.dex */
final class C1731aj {

    /* JADX INFO: renamed from: a */
    private static C1773s f518a;

    /* JADX INFO: renamed from: a */
    public static synchronized C1773s m414a(Context context) {
        try {
            if (f518a == null) {
                C1771q c1771q = new C1771q(null);
                c1771q.m438a(AbstractC1786ae.m468a(context));
                f518a = c1771q.mo413b();
            }
        } catch (Throwable th) {
            throw th;
        }
        return f518a;
    }
}
