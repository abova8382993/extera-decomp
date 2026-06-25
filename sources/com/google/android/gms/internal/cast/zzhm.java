package com.google.android.gms.internal.cast;

import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
abstract class zzhm {
    public static void zza(Object obj, Object obj2) {
        if (obj == null) {
            g$$ExternalSyntheticBUOutline2.m208m("null key in entry: null=".concat(String.valueOf(obj2)));
            return;
        }
        if (obj2 != null) {
            return;
        }
        String string = obj.toString();
        StringBuilder sb = new StringBuilder(string.length() + 26);
        sb.append("null value in entry: ");
        sb.append(string);
        sb.append("=null");
        throw new NullPointerException(sb.toString());
    }
}
