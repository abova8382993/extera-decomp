package org.webrtc;

import android.content.Context;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes7.dex */
public class ContextUtils {
    private static final String TAG = "ContextUtils";
    private static Context applicationContext;

    public static void initialize(Context context) {
        if (context == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Application context cannot be null for ContextUtils.initialize.");
        } else {
            applicationContext = context;
        }
    }

    @Deprecated
    public static Context getApplicationContext() {
        return applicationContext;
    }
}
