package dagger.internal;

import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes5.dex */
public abstract class Preconditions {
    public static <T> T checkNotNull(T t) {
        t.getClass();
        return t;
    }

    public static <T> T checkNotNull(T t, String str) {
        if (t != null) {
            return t;
        }
        g$$ExternalSyntheticBUOutline2.m208m(str);
        return null;
    }

    public static <T> T checkNotNullFromProvides(T t) {
        if (t != null) {
            return t;
        }
        g$$ExternalSyntheticBUOutline2.m208m("Cannot return null from a non-@Nullable @Provides method");
        return null;
    }

    public static <T> void checkBuilderRequirement(T t, Class<T> cls) {
        if (t != null) {
            return;
        }
        Preconditions$$ExternalSyntheticBUOutline0.m560m(cls.getCanonicalName(), " must be set");
    }
}
