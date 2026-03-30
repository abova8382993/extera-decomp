package com.google.android.play.integrity.internal;

/* JADX INFO: loaded from: classes4.dex */
public abstract class ak {
    public static void a(Object obj, Class cls) {
        if (obj == null) {
            throw new IllegalStateException(String.valueOf(cls.getCanonicalName()).concat(" must be set"));
        }
    }
}
