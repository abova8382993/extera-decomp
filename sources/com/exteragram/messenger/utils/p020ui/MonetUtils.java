package com.exteragram.messenger.utils.p020ui;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import com.google.android.material.color.MaterialColors;
import java.util.HashMap;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.url._UrlKt;
import org.lsposed.lsparanoid.Deobfuscator$exteraGramDev$TMessagesProj;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.FileLog;
import org.telegram.p035ui.ActionBar.Theme;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bÇ\u0002\u0018\u00002\u00020\u0001:\u0001$B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u0006H\u0007J\b\u0010\u0017\u001a\u00020\u0018H\u0007J\u0018\u0010\u0019\u001a\u00020\u00072\u0006\u0010\u001a\u001a\u00020\u00072\u0006\u0010\u001b\u001a\u00020\u0018H\u0007J\u0012\u0010\u001c\u001a\u00020\u00072\b\b\u0001\u0010\u001d\u001a\u00020\u0007H\u0007J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0007J\u0010\u0010\"\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0007J\b\u0010#\u001a\u00020\u001fH\u0003R*\u0010\u0004\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007`\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\nX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000bR\u0016\u0010\f\u001a\n \u000e*\u0004\u0018\u00010\r0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000¨\u0006%"}, m877d2 = {"Lcom/exteragram/messenger/utils/ui/MonetUtils;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "COLOR_MAP", "Ljava/util/HashMap;", _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlin/collections/HashMap;", "ACCENT_PREFIXES", _UrlKt.FRAGMENT_ENCODE_SET, "[Ljava/lang/String;", "PARAM_PATTERN", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "ACTION_OVERLAY_CHANGED", "overlayChangeReceiver", "Lcom/exteragram/messenger/utils/ui/MonetUtils$OverlayChangeReceiver;", "KEY_ALPHA", "KEY_SATURATION", "KEY_LIGHTNESS", "getColor", "colorString", "isSupported", _UrlKt.FRAGMENT_ENCODE_SET, "getSystemAccentColor", "index", "isDark", "harmonize", "color", "registerReceiver", _UrlKt.FRAGMENT_ENCODE_SET, "context", "Landroid/content/Context;", "unregisterReceiver", "initSystemColors", "OverlayChangeReceiver", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
public final class MonetUtils {
    private static final String[] ACCENT_PREFIXES;
    private static final HashMap<String, Integer> COLOR_MAP;
    public static final MonetUtils INSTANCE;
    private static final Pattern PARAM_PATTERN;
    private static final OverlayChangeReceiver overlayChangeReceiver;
    private static final String ACTION_OVERLAY_CHANGED = Deobfuscator$exteraGramDev$TMessagesProj.getString(-80221776107321L);
    private static final String KEY_ALPHA = Deobfuscator$exteraGramDev$TMessagesProj.getString(-80384984864569L);
    private static final String KEY_SATURATION = Deobfuscator$exteraGramDev$TMessagesProj.getString(-80393574799161L);
    private static final String KEY_LIGHTNESS = Deobfuscator$exteraGramDev$TMessagesProj.getString(-80402164733753L);

    private MonetUtils() {
    }

    static {
        MonetUtils monetUtils = new MonetUtils();
        INSTANCE = monetUtils;
        HashMap<String, Integer> map = new HashMap<>();
        COLOR_MAP = map;
        ACCENT_PREFIXES = new String[]{Deobfuscator$exteraGramDev$TMessagesProj.getString(-80410754668345L), Deobfuscator$exteraGramDev$TMessagesProj.getString(-80427934537529L), Deobfuscator$exteraGramDev$TMessagesProj.getString(-80445114406713L)};
        PARAM_PATTERN = Pattern.compile(Deobfuscator$exteraGramDev$TMessagesProj.getString(-80462294275897L));
        overlayChangeReceiver = new OverlayChangeReceiver();
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-80556783556409L), Integer.valueOf(C2797R.color.black));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-80586848327481L), Integer.valueOf(C2797R.color.white));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-80616913098553L), Integer.valueOf(C2797R.color.mRed200));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-80651272836921L), Integer.valueOf(C2797R.color.mRed500));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-80685632575289L), Integer.valueOf(C2797R.color.mRed800));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-80719992313657L), Integer.valueOf(C2797R.color.mGreen200));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-80762941986617L), Integer.valueOf(C2797R.color.mGreen500));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-80805891659577L), Integer.valueOf(C2797R.color.mGreen800));
        if (isSupported()) {
            monetUtils.initSystemColors();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x00ea A[PHI: r15
  0x00ea: PHI (r15v2 'colorString' java.lang.String) = (r15v0 'colorString' java.lang.String), (r15v5 'colorString' java.lang.String) binds: [B:6:0x001d, B:16:0x003b] A[DONT_GENERATE, DONT_INLINE]] */
    @kotlin.jvm.JvmStatic
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final int getColor(java.lang.String r15) {
        /*
            Method dump skipped, instruction units count: 338
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.utils.p020ui.MonetUtils.getColor(java.lang.String):int");
    }

    @JvmStatic
    public static final boolean isSupported() {
        return Build.VERSION.SDK_INT >= 31;
    }

    @JvmStatic
    public static final int getSystemAccentColor(int index, boolean isDark) {
        if (!isSupported() || index < 0) {
            return 0;
        }
        String[] strArr = ACCENT_PREFIXES;
        if (index >= strArr.length) {
            return 0;
        }
        return getColor(strArr[index] + (isDark ? 200 : 600));
    }

    @JvmStatic
    public static final int harmonize(int color) {
        return !isSupported() ? color : MaterialColors.harmonize(color, ApplicationLoader.applicationContext.getColor(R.color.system_accent1_600));
    }

    @JvmStatic
    public static final void registerReceiver(Context context) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-78542443894585L);
        try {
            overlayChangeReceiver.register(context);
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
    }

    @JvmStatic
    public static final void unregisterReceiver(Context context) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-78576803632953L);
        try {
            overlayChangeReceiver.unregister(context);
        } catch (Exception unused) {
        }
    }

    @Metadata(m876d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\n\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u0018\u0010\u000b\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"}, m877d2 = {"Lcom/exteragram/messenger/utils/ui/MonetUtils$OverlayChangeReceiver;", "Landroid/content/BroadcastReceiver;", "<init>", "()V", "isRegistered", _UrlKt.FRAGMENT_ENCODE_SET, "register", _UrlKt.FRAGMENT_ENCODE_SET, "context", "Landroid/content/Context;", "unregister", "onReceive", "intent", "Landroid/content/Intent;", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
    public static final class OverlayChangeReceiver extends BroadcastReceiver {
        private boolean isRegistered;

        public final void register(Context context) {
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-77893903832889L);
            if (this.isRegistered) {
                return;
            }
            IntentFilter intentFilter = new IntentFilter(Deobfuscator$exteraGramDev$TMessagesProj.getString(-77928263571257L));
            intentFilter.addDataScheme(Deobfuscator$exteraGramDev$TMessagesProj.getString(-78091472328505L));
            intentFilter.addDataSchemeSpecificPart(Deobfuscator$exteraGramDev$TMessagesProj.getString(-78125832066873L), 0);
            context.registerReceiver(this, intentFilter);
            this.isRegistered = true;
        }

        public final void unregister(Context context) {
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-78160191805241L);
            if (this.isRegistered) {
                context.unregisterReceiver(this);
                this.isRegistered = false;
            }
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-78194551543609L);
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-78228911281977L);
            if (Intrinsics.areEqual(Deobfuscator$exteraGramDev$TMessagesProj.getString(-78258976053049L), intent.getAction())) {
                Theme.refreshMonetColors();
                if (Theme.isCurrentThemeMonet() || Theme.isCurrentAccentMonet()) {
                    Theme.applyTheme(Theme.getActiveTheme(), Theme.isCurrentThemeNight());
                }
            }
        }
    }

    private final void initSystemColors() {
        HashMap<String, Integer> map = COLOR_MAP;
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-78611163371321L), Integer.valueOf(R.color.system_accent1_10));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-78636933175097L), Integer.valueOf(R.color.system_accent1_50));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-78662702978873L), Integer.valueOf(R.color.system_accent1_100));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-78692767749945L), Integer.valueOf(R.color.system_accent1_200));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-78722832521017L), Integer.valueOf(R.color.system_accent1_300));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-78752897292089L), Integer.valueOf(R.color.system_accent1_400));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-78782962063161L), Integer.valueOf(R.color.system_accent1_500));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-78813026834233L), Integer.valueOf(R.color.system_accent1_600));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-78843091605305L), Integer.valueOf(R.color.system_accent1_700));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-78873156376377L), Integer.valueOf(R.color.system_accent1_800));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-78903221147449L), Integer.valueOf(R.color.system_accent1_900));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-78933285918521L), Integer.valueOf(R.color.system_accent2_10));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-78959055722297L), Integer.valueOf(R.color.system_accent2_50));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-78984825526073L), Integer.valueOf(R.color.system_accent2_100));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79014890297145L), Integer.valueOf(R.color.system_accent2_200));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79044955068217L), Integer.valueOf(R.color.system_accent2_300));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79075019839289L), Integer.valueOf(R.color.system_accent2_400));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79105084610361L), Integer.valueOf(R.color.system_accent2_500));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79135149381433L), Integer.valueOf(R.color.system_accent2_600));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79165214152505L), Integer.valueOf(R.color.system_accent2_700));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79195278923577L), Integer.valueOf(R.color.system_accent2_800));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79225343694649L), Integer.valueOf(R.color.system_accent2_900));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79255408465721L), Integer.valueOf(R.color.system_accent3_10));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79281178269497L), Integer.valueOf(R.color.system_accent3_50));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79306948073273L), Integer.valueOf(R.color.system_accent3_100));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79337012844345L), Integer.valueOf(R.color.system_accent3_200));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79367077615417L), Integer.valueOf(R.color.system_accent3_300));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79397142386489L), Integer.valueOf(R.color.system_accent3_400));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79427207157561L), Integer.valueOf(R.color.system_accent3_500));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79457271928633L), Integer.valueOf(R.color.system_accent3_600));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79487336699705L), Integer.valueOf(R.color.system_accent3_700));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79517401470777L), Integer.valueOf(R.color.system_accent3_800));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79547466241849L), Integer.valueOf(R.color.system_accent3_900));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79577531012921L), Integer.valueOf(R.color.system_neutral1_10));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79603300816697L), Integer.valueOf(R.color.system_neutral1_50));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79629070620473L), Integer.valueOf(R.color.system_neutral1_100));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79659135391545L), Integer.valueOf(R.color.system_neutral1_200));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79689200162617L), Integer.valueOf(R.color.system_neutral1_300));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79719264933689L), Integer.valueOf(R.color.system_neutral1_400));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79749329704761L), Integer.valueOf(R.color.system_neutral1_500));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79779394475833L), Integer.valueOf(R.color.system_neutral1_600));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79809459246905L), Integer.valueOf(R.color.system_neutral1_700));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79839524017977L), Integer.valueOf(R.color.system_neutral1_800));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79869588789049L), Integer.valueOf(R.color.system_neutral1_900));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79899653560121L), Integer.valueOf(R.color.system_neutral2_10));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79925423363897L), Integer.valueOf(R.color.system_neutral2_50));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79951193167673L), Integer.valueOf(R.color.system_neutral2_100));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-79981257938745L), Integer.valueOf(R.color.system_neutral2_200));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-80011322709817L), Integer.valueOf(R.color.system_neutral2_300));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-80041387480889L), Integer.valueOf(R.color.system_neutral2_400));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-80071452251961L), Integer.valueOf(R.color.system_neutral2_500));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-80101517023033L), Integer.valueOf(R.color.system_neutral2_600));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-80131581794105L), Integer.valueOf(R.color.system_neutral2_700));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-80161646565177L), Integer.valueOf(R.color.system_neutral2_800));
        map.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-80191711336249L), Integer.valueOf(R.color.system_neutral2_900));
    }
}
