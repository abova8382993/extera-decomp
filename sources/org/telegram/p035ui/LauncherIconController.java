package org.telegram.p035ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import com.exteragram.messenger.utils.AppUtils;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.C2797R;

/* JADX INFO: loaded from: classes3.dex */
public abstract class LauncherIconController {
    public static void tryFixLauncherIconIfNeeded() {
        for (LauncherIcon launcherIcon : LauncherIcon.values()) {
            if (isEnabled(launcherIcon)) {
                LauncherIcon launcherIcon2 = LauncherIcon.MONET;
                if (launcherIcon == launcherIcon2) {
                    setIcon(LauncherIcon.DEFAULT);
                    setIcon(launcherIcon2);
                    return;
                }
                return;
            }
        }
        setIcon(LauncherIcon.DEFAULT);
    }

    public static boolean isEnabled(LauncherIcon launcherIcon) {
        Context context = ApplicationLoader.applicationContext;
        int componentEnabledSetting = context.getPackageManager().getComponentEnabledSetting(launcherIcon.getComponentName(context));
        return componentEnabledSetting == 1 || (componentEnabledSetting == 0 && launcherIcon == LauncherIcon.DEFAULT);
    }

    public static void setIcon(LauncherIcon launcherIcon) {
        Context context = ApplicationLoader.applicationContext;
        PackageManager packageManager = context.getPackageManager();
        LauncherIcon[] launcherIconArrValues = LauncherIcon.values();
        int length = launcherIconArrValues.length;
        for (int i = 0; i < length; i++) {
            LauncherIcon launcherIcon2 = launcherIconArrValues[i];
            packageManager.setComponentEnabledSetting(launcherIcon2.getComponentName(context), launcherIcon2 == launcherIcon ? 1 : 2, 1);
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    public static final class LauncherIcon {
        private static final /* synthetic */ LauncherIcon[] $VALUES;
        public static final LauncherIcon AMETHYST;
        public static final LauncherIcon AURORA;
        public static final LauncherIcon CYBERPUNK;
        public static final LauncherIcon DEFAULT;
        public static final LauncherIcon DSGN480;
        public static final LauncherIcon EDITOR;
        public static final LauncherIcon GOOGLE;
        public static final LauncherIcon ICEAGE;
        public static final LauncherIcon INVINCIBLE;
        public static final LauncherIcon MONET;
        public static final LauncherIcon ORBIT;
        public static final LauncherIcon SAPPHIRE;
        public static final LauncherIcon SPACE;
        public static final LauncherIcon SUNSET;
        public static final LauncherIcon SUS;
        public static final LauncherIcon WINTER;
        public final int background;
        private ComponentName componentName;
        public final int foreground;
        public final boolean hidden;
        public final String key;
        public final boolean premium;
        public final int title;

        private static /* synthetic */ LauncherIcon[] $values() {
            return new LauncherIcon[]{DEFAULT, WINTER, MONET, ORBIT, AURORA, SUNSET, ICEAGE, EDITOR, SPACE, SAPPHIRE, AMETHYST, DSGN480, CYBERPUNK, GOOGLE, INVINCIBLE, SUS};
        }

        public static LauncherIcon valueOf(String str) {
            return (LauncherIcon) Enum.valueOf(LauncherIcon.class, str);
        }

        public static LauncherIcon[] values() {
            return (LauncherIcon[]) $VALUES.clone();
        }

        static {
            DEFAULT = new LauncherIcon("DEFAULT", 0, "DefaultIcon", BuildVars.isBetaApp() ? C2797R.mipmap.ic_launcher_beta_background : C2797R.color.ic_background, BuildVars.isBetaApp() ? C2797R.mipmap.ic_launcher_beta_foreground : C2797R.mipmap.ic_launcher_foreground, C2797R.string.AppIconDefault);
            boolean z = true;
            WINTER = new LauncherIcon("WINTER", 1, "WinterIcon", C2797R.mipmap.ic_launcher_winter_background, C2797R.mipmap.ic_launcher_foreground, C2797R.string.AppIconWinter, !AppUtils.isWinter());
            int i = C2797R.color.ic_background_monet;
            int i2 = C2797R.drawable.ic_foreground_monet;
            int i3 = C2797R.string.AppIconMonet;
            int i4 = Build.VERSION.SDK_INT;
            if (i4 >= 31 && i4 <= 32) {
                z = false;
            }
            MONET = new LauncherIcon("MONET", 2, "MonetIcon", i, i2, i3, z);
            ORBIT = new LauncherIcon("ORBIT", 3, "OrbitIcon", C2797R.color.ic_background, C2797R.mipmap.ic_launcher_orbit_foreground, C2797R.string.AppIconOrbit);
            AURORA = new LauncherIcon("AURORA", 4, "AuroraIcon", C2797R.mipmap.ic_launcher_aurora_background, C2797R.mipmap.ic_launcher_aurora_foreground, C2797R.string.AppIconAurora);
            SUNSET = new LauncherIcon("SUNSET", 5, "SunsetIcon", C2797R.mipmap.ic_launcher_sunset_background, C2797R.mipmap.ic_launcher_sunset_foreground, C2797R.string.AppIconSunset);
            ICEAGE = new LauncherIcon("ICEAGE", 6, "IceAgeIcon", C2797R.mipmap.ic_launcher_ice_age_background, C2797R.mipmap.ic_launcher_ice_age_foreground, C2797R.string.AppIconIceAge);
            EDITOR = new LauncherIcon("EDITOR", 7, "EditorIcon", C2797R.mipmap.ic_launcher_editor_background, C2797R.mipmap.ic_launcher_editor_foreground, C2797R.string.AppIconEditor);
            SPACE = new LauncherIcon("SPACE", 8, "SpaceIcon", C2797R.mipmap.ic_launcher_space_background, C2797R.mipmap.ic_launcher_space_foreground, C2797R.string.AppIconSpace);
            SAPPHIRE = new LauncherIcon("SAPPHIRE", 9, "SapphireIcon", C2797R.mipmap.ic_launcher_sapphire_background, C2797R.mipmap.ic_launcher_sapphire_foreground, C2797R.string.AppIconSapphire);
            AMETHYST = new LauncherIcon("AMETHYST", 10, "AmethystIcon", C2797R.mipmap.ic_launcher_amethyst_background, C2797R.mipmap.ic_launcher_amethyst_foreground, C2797R.string.AppIconAmethyst);
            DSGN480 = new LauncherIcon("DSGN480", 11, "Dsgn480Icon", C2797R.mipmap.ic_launcher_480dsgn_background, C2797R.mipmap.ic_launcher_480dsgn_foreground, C2797R.string.AppIcon480DSGN);
            CYBERPUNK = new LauncherIcon("CYBERPUNK", 12, "CyberpunkIcon", C2797R.color.ic_background_cyberpunk, C2797R.mipmap.ic_launcher_cyberpunk_foreground, C2797R.string.AppIconCyberpunk);
            GOOGLE = new LauncherIcon("GOOGLE", 13, "GoogleIcon", C2797R.color.white, C2797R.mipmap.ic_launcher_google_foreground, C2797R.string.AppIconGoogle);
            INVINCIBLE = new LauncherIcon("INVINCIBLE", 14, "InvincibleIcon", C2797R.mipmap.ic_launcher_invincible_background, C2797R.mipmap.ic_launcher_invincible_foreground, C2797R.string.AppIconInvincible);
            SUS = new LauncherIcon("SUS", 15, "SusIcon", C2797R.color.ic_background_sus, C2797R.mipmap.ic_launcher_sus_foreground, C2797R.string.AppIconSus);
            $VALUES = $values();
        }

        public ComponentName getComponentName(Context context) {
            if (this.componentName == null) {
                this.componentName = new ComponentName(context.getPackageName(), "com.exteragram.messenger." + this.key);
            }
            return this.componentName;
        }

        private LauncherIcon(String str, int i, String str2, int i2, int i3, int i4) {
            this(str, i, str2, i2, i3, i4, false);
        }

        private LauncherIcon(String str, int i, String str2, int i2, int i3, int i4, boolean z) {
            this.key = str2;
            this.background = i2;
            this.foreground = i3;
            this.title = i4;
            this.premium = false;
            this.hidden = z;
        }
    }
}
