package com.google.android.gms.internal.play_billing;

import com.google.android.gms.internal.fido.zzbv$$ExternalSyntheticBUOutline0;
import com.google.common.collect.CollectPreconditions$$ExternalSyntheticBUOutline0;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes5.dex */
abstract class zzbw {
    public static int zza(int i, String str) {
        if (i >= 0) {
            return i;
        }
        CollectPreconditions$$ExternalSyntheticBUOutline0.m507m(str, i);
        return 0;
    }

    public static void zzb(Object obj, Object obj2) {
        if (obj == null) {
            g$$ExternalSyntheticBUOutline2.m208m("null key in entry: null=".concat(String.valueOf(obj2)));
        } else {
            if (obj2 != null) {
                return;
            }
            zzbv$$ExternalSyntheticBUOutline0.m363m(obj);
        }
    }
}
