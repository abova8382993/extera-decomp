package com.google.android.play.core.integrity;

import android.content.Context;

/* JADX INFO: loaded from: classes4.dex */
final class aj {
    private static s a;

    static synchronized s a(Context context) {
        try {
            if (a == null) {
                q qVar = new q(null);
                qVar.a(com.google.android.play.integrity.internal.ae.a(context));
                a = qVar.b();
            }
        } catch (Throwable th) {
            throw th;
        }
        return a;
    }
}
