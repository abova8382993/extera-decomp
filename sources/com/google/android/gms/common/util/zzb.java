package com.google.android.gms.common.util;

import org.telegram.messenger.MediaDataController;

/* JADX INFO: loaded from: classes4.dex */
public abstract class zzb {
    public static int zza(int i) {
        if (i == -1) {
            return -1;
        }
        return i / MediaDataController.MAX_STYLE_RUNS_COUNT;
    }
}
