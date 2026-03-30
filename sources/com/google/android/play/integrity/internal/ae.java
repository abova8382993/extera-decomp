package com.google.android.play.integrity.internal;

import android.content.Context;

/* JADX INFO: loaded from: classes4.dex */
public abstract class ae {
    public static Context a(Context context) {
        Context applicationContext = context.getApplicationContext();
        return applicationContext != null ? applicationContext : context;
    }
}
