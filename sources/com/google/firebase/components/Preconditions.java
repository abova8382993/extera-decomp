package com.google.firebase.components;

import okio.Segment$$ExternalSyntheticBUOutline1;
import p005c.g$$ExternalSyntheticBUOutline1;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes.dex */
public abstract class Preconditions {
    public static void checkArgument(boolean z, String str) {
        if (z) {
            return;
        }
        g$$ExternalSyntheticBUOutline1.m207m(str);
    }

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

    public static void checkState(boolean z, String str) {
        if (z) {
            return;
        }
        Segment$$ExternalSyntheticBUOutline1.m992m(str);
    }
}
