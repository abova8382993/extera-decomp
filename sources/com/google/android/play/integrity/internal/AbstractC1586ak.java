package com.google.android.play.integrity.internal;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.ak */
/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractC1586ak {
    /* JADX INFO: renamed from: a */
    public static void m412a(Object obj, Class cls) {
        if (obj == null) {
            throw new IllegalStateException(String.valueOf(cls.getCanonicalName()).concat(" must be set"));
        }
    }
}
