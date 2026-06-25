package com.google.android.gms.internal.mlkit_language_id_common;

import java.util.Objects;
import okhttp3.internal.http.RealInterceptorChain$$ExternalSyntheticBUOutline0;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes.dex */
abstract class zzn {
    public static void zza(Object obj, Object obj2) {
        if (obj == null) {
            Objects.toString(obj2);
            g$$ExternalSyntheticBUOutline2.m208m("null key in entry: null=".concat(String.valueOf(obj2)));
        } else {
            if (obj2 != null) {
                return;
            }
            RealInterceptorChain$$ExternalSyntheticBUOutline0.m969m("null value in entry: ", obj, "=null");
        }
    }
}
