package io.noties.markwon.html.jsoup.helper;

import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes5.dex */
public abstract class Validate {
    public static void notNull(Object obj) {
        if (obj != null) {
            return;
        }
        g$$ExternalSyntheticBUOutline1.m207m("Object must not be null");
    }

    public static void isTrue(boolean z) {
        if (z) {
            return;
        }
        g$$ExternalSyntheticBUOutline1.m207m("Must be true");
    }

    public static void isFalse(boolean z) {
        if (z) {
            g$$ExternalSyntheticBUOutline1.m207m("Must be false");
        }
    }

    public static void isFalse(boolean z, String str) {
        if (z) {
            g$$ExternalSyntheticBUOutline1.m207m(str);
        }
    }

    public static void notEmpty(String str) {
        if (str == null || str.length() == 0) {
            g$$ExternalSyntheticBUOutline1.m207m("String must not be empty");
        }
    }

    public static void fail(String str) {
        throw new IllegalArgumentException(str);
    }
}
