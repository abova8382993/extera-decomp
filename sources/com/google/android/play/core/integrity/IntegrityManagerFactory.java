package com.google.android.play.core.integrity;

import android.content.Context;

/* JADX INFO: loaded from: classes5.dex */
public class IntegrityManagerFactory {
    private IntegrityManagerFactory() {
    }

    public static IntegrityManager create(Context context) {
        return C1776v.m440a(context).m437a();
    }

    public static StandardIntegrityManager createStandard(Context context) {
        return C1731aj.m414a(context).m439a();
    }
}
