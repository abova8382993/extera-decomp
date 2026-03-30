package com.exteragram.messenger.utils.p014ui;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.google.android.material.color.MaterialColors;
import java.util.HashMap;
import java.util.regex.Pattern;
import kotlin.jvm.internal.Intrinsics;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.C2702R;
import org.telegram.messenger.FileLog;
import org.telegram.p026ui.ActionBar.Theme;

/* JADX INFO: loaded from: classes.dex */
public final class MonetUtils {
    private static final HashMap COLOR_MAP;
    public static final MonetUtils INSTANCE;
    private static final Pattern PARAM_PATTERN;
    private static final OverlayChangeReceiver overlayChangeReceiver;

    private MonetUtils() {
    }

    static {
        MonetUtils monetUtils = new MonetUtils();
        INSTANCE = monetUtils;
        HashMap map = new HashMap();
        COLOR_MAP = map;
        PARAM_PATTERN = Pattern.compile("^([^(]+)\\(([^)]+)\\)?$");
        overlayChangeReceiver = new OverlayChangeReceiver();
        map.put("mBlack", Integer.valueOf(C2702R.color.black));
        map.put("mWhite", Integer.valueOf(C2702R.color.white));
        map.put("mRed200", Integer.valueOf(C2702R.color.mRed200));
        map.put("mRed500", Integer.valueOf(C2702R.color.mRed500));
        map.put("mRed800", Integer.valueOf(C2702R.color.mRed800));
        map.put("mGreen200", Integer.valueOf(C2702R.color.mGreen200));
        map.put("mGreen500", Integer.valueOf(C2702R.color.mGreen500));
        map.put("mGreen800", Integer.valueOf(C2702R.color.mGreen800));
        monetUtils.initSystemColors();
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x00c4 A[PHI: r15
  0x00c4: PHI (r15v2 'colorString' java.lang.String) = (r15v0 'colorString' java.lang.String), (r15v5 'colorString' java.lang.String) binds: [B:6:0x001a, B:16:0x0038] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final int getColor(java.lang.String r15) {
        /*
            Method dump skipped, instruction units count: 288
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.utils.p014ui.MonetUtils.getColor(java.lang.String):int");
    }

    public static final int harmonize(int i) {
        return MaterialColors.harmonize(i, ApplicationLoader.applicationContext.getColor(R.color.system_accent1_600));
    }

    public static final void registerReceiver(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            overlayChangeReceiver.register(context);
        } catch (Exception e) {
            FileLog.m1093e(e);
        }
    }

    public static final void unregisterReceiver(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            overlayChangeReceiver.unregister(context);
        } catch (Exception unused) {
        }
    }

    private static final class OverlayChangeReceiver extends BroadcastReceiver {
        private boolean isRegistered;

        public final void register(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            if (this.isRegistered) {
                return;
            }
            IntentFilter intentFilter = new IntentFilter("android.intent.action.OVERLAY_CHANGED");
            intentFilter.addDataScheme("package");
            intentFilter.addDataSchemeSpecificPart("android", 0);
            context.registerReceiver(this, intentFilter);
            this.isRegistered = true;
        }

        public final void unregister(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            if (this.isRegistered) {
                context.unregisterReceiver(this);
                this.isRegistered = false;
            }
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(intent, "intent");
            if (Intrinsics.areEqual("android.intent.action.OVERLAY_CHANGED", intent.getAction()) && Theme.isCurrentThemeMonet()) {
                Theme.applyTheme(Theme.getActiveTheme(), Theme.isCurrentThemeNight());
            }
        }
    }

    private final void initSystemColors() {
        HashMap map = COLOR_MAP;
        map.put("a1_10", Integer.valueOf(R.color.system_accent1_10));
        map.put("a1_50", Integer.valueOf(R.color.system_accent1_50));
        map.put("a1_100", Integer.valueOf(R.color.system_accent1_100));
        map.put("a1_200", Integer.valueOf(R.color.system_accent1_200));
        map.put("a1_300", Integer.valueOf(R.color.system_accent1_300));
        map.put("a1_400", Integer.valueOf(R.color.system_accent1_400));
        map.put("a1_500", Integer.valueOf(R.color.system_accent1_500));
        map.put("a1_600", Integer.valueOf(R.color.system_accent1_600));
        map.put("a1_700", Integer.valueOf(R.color.system_accent1_700));
        map.put("a1_800", Integer.valueOf(R.color.system_accent1_800));
        map.put("a1_900", Integer.valueOf(R.color.system_accent1_900));
        map.put("a2_10", Integer.valueOf(R.color.system_accent2_10));
        map.put("a2_50", Integer.valueOf(R.color.system_accent2_50));
        map.put("a2_100", Integer.valueOf(R.color.system_accent2_100));
        map.put("a2_200", Integer.valueOf(R.color.system_accent2_200));
        map.put("a2_300", Integer.valueOf(R.color.system_accent2_300));
        map.put("a2_400", Integer.valueOf(R.color.system_accent2_400));
        map.put("a2_500", Integer.valueOf(R.color.system_accent2_500));
        map.put("a2_600", Integer.valueOf(R.color.system_accent2_600));
        map.put("a2_700", Integer.valueOf(R.color.system_accent2_700));
        map.put("a2_800", Integer.valueOf(R.color.system_accent2_800));
        map.put("a2_900", Integer.valueOf(R.color.system_accent2_900));
        map.put("a3_10", Integer.valueOf(R.color.system_accent3_10));
        map.put("a3_50", Integer.valueOf(R.color.system_accent3_50));
        map.put("a3_100", Integer.valueOf(R.color.system_accent3_100));
        map.put("a3_200", Integer.valueOf(R.color.system_accent3_200));
        map.put("a3_300", Integer.valueOf(R.color.system_accent3_300));
        map.put("a3_400", Integer.valueOf(R.color.system_accent3_400));
        map.put("a3_500", Integer.valueOf(R.color.system_accent3_500));
        map.put("a3_600", Integer.valueOf(R.color.system_accent3_600));
        map.put("a3_700", Integer.valueOf(R.color.system_accent3_700));
        map.put("a3_800", Integer.valueOf(R.color.system_accent3_800));
        map.put("a3_900", Integer.valueOf(R.color.system_accent3_900));
        map.put("n1_10", Integer.valueOf(R.color.system_neutral1_10));
        map.put("n1_50", Integer.valueOf(R.color.system_neutral1_50));
        map.put("n1_100", Integer.valueOf(R.color.system_neutral1_100));
        map.put("n1_200", Integer.valueOf(R.color.system_neutral1_200));
        map.put("n1_300", Integer.valueOf(R.color.system_neutral1_300));
        map.put("n1_400", Integer.valueOf(R.color.system_neutral1_400));
        map.put("n1_500", Integer.valueOf(R.color.system_neutral1_500));
        map.put("n1_600", Integer.valueOf(R.color.system_neutral1_600));
        map.put("n1_700", Integer.valueOf(R.color.system_neutral1_700));
        map.put("n1_800", Integer.valueOf(R.color.system_neutral1_800));
        map.put("n1_900", Integer.valueOf(R.color.system_neutral1_900));
        map.put("n2_10", Integer.valueOf(R.color.system_neutral2_10));
        map.put("n2_50", Integer.valueOf(R.color.system_neutral2_50));
        map.put("n2_100", Integer.valueOf(R.color.system_neutral2_100));
        map.put("n2_200", Integer.valueOf(R.color.system_neutral2_200));
        map.put("n2_300", Integer.valueOf(R.color.system_neutral2_300));
        map.put("n2_400", Integer.valueOf(R.color.system_neutral2_400));
        map.put("n2_500", Integer.valueOf(R.color.system_neutral2_500));
        map.put("n2_600", Integer.valueOf(R.color.system_neutral2_600));
        map.put("n2_700", Integer.valueOf(R.color.system_neutral2_700));
        map.put("n2_800", Integer.valueOf(R.color.system_neutral2_800));
        map.put("n2_900", Integer.valueOf(R.color.system_neutral2_900));
    }
}
