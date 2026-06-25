package com.google.android.play.core.integrity;

import android.content.Context;
import com.google.android.play.integrity.internal.AbstractC1786ae;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.v */
/* JADX INFO: loaded from: classes5.dex */
final class C1776v {

    /* JADX INFO: renamed from: a */
    private static C1769o f587a;

    /* JADX INFO: renamed from: a */
    public static synchronized C1769o m440a(Context context) {
        try {
            if (f587a == null) {
                C1765m c1765m = new C1765m(null);
                c1765m.m433a(AbstractC1786ae.m468a(context));
                f587a = c1765m.mo434b();
            }
        } catch (Throwable th) {
            throw th;
        }
        return f587a;
    }
}
