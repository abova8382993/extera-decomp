package com.exteragram.messenger.debug;

import android.content.SharedPreferences;
import com.exteragram.messenger.backup.PreferencesUtils;
import com.exteragram.messenger.config.BasePref;
import com.exteragram.messenger.config.BooleanPref;
import com.exteragram.messenger.config.IntegerPref;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.MutablePropertyReference0Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.ClosedRange;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KProperty;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000(\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0015\n\u0002\b\n\u001a\r\u0010\u0001\u001a\u00020\u0000¢\u0006\u0004\b\u0001\u0010\u0002\u001a\r\u0010\u0003\u001a\u00020\u0000¢\u0006\u0004\b\u0003\u0010\u0002\"\u0017\u0010\u0005\u001a\u00020\u00048\u0006¢\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\b\"\u0017\u0010\n\u001a\u00020\t8\u0006¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"+\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015\"\u0017\u0010\u0018\u001a\u00020\u00178\u0006¢\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\"+\u0010 \u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u00008F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u001c\u0010\u0011\u001a\u0004\b\u001d\u0010\u0002\"\u0004\b\u001e\u0010\u001f¨\u0006!"}, m877d2 = {_UrlKt.FRAGMENT_ENCODE_SET, "getSectionRadiusDp", "()I", "getSanitizedSectionRadiusOption", "Landroid/content/SharedPreferences;", "preferences", "Landroid/content/SharedPreferences;", "getPreferences", "()Landroid/content/SharedPreferences;", "Landroid/content/SharedPreferences$Editor;", "editor", "Landroid/content/SharedPreferences$Editor;", "getEditor", "()Landroid/content/SharedPreferences$Editor;", _UrlKt.FRAGMENT_ENCODE_SET, "<set-?>", "debugCameraMetrics$delegate", "Lcom/exteragram/messenger/config/BasePref;", "getDebugCameraMetrics", "()Z", "setDebugCameraMetrics", "(Z)V", "debugCameraMetrics", _UrlKt.FRAGMENT_ENCODE_SET, "sectionRadiusOptions", "[I", "getSectionRadiusOptions", "()[I", "debugSectionRadiusOption$delegate", "getDebugSectionRadiusOption", "setDebugSectionRadiusOption", "(I)V", "debugSectionRadiusOption", "TMessagesProj"}, m878k = 2, m879mv = {2, 2, 0}, m881xi = 48)
@JvmName(name = "DebugConfig")
@SourceDebugExtension({"SMAP\nDebugConfig.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DebugConfig.kt\ncom/exteragram/messenger/debug/DebugConfig\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,36:1\n1#2:37\n*E\n"})
public abstract class DebugConfig {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties;
    private static final BasePref debugCameraMetrics$delegate;
    private static final BasePref debugSectionRadiusOption$delegate;
    private static final SharedPreferences.Editor editor;
    private static final SharedPreferences preferences;
    private static final int[] sectionRadiusOptions;

    static {
        KProperty<?>[] kPropertyArr = {Reflection.mutableProperty0(new MutablePropertyReference0Impl(DebugConfig.class, "debugCameraMetrics", "getDebugCameraMetrics()Z", 1)), Reflection.mutableProperty0(new MutablePropertyReference0Impl(DebugConfig.class, "debugSectionRadiusOption", "getDebugSectionRadiusOption()I", 1))};
        $$delegatedProperties = kPropertyArr;
        SharedPreferences preferences2 = PreferencesUtils.getPreferences("debugconfig");
        preferences = preferences2;
        editor = preferences2.edit();
        debugCameraMetrics$delegate = new BooleanPref(false, null, 2, null).provideDelegate(null, kPropertyArr[0]);
        sectionRadiusOptions = new int[]{16, 20, 24, 28};
        debugSectionRadiusOption$delegate = new IntegerPref(1, null, 2, null).provideDelegate(null, kPropertyArr[1]);
    }

    public static final boolean getDebugCameraMetrics() {
        return ((Boolean) debugCameraMetrics$delegate.getValue(null, $$delegatedProperties[0])).booleanValue();
    }

    public static final void setDebugCameraMetrics(boolean z) {
        debugCameraMetrics$delegate.setValue(null, $$delegatedProperties[0], Boolean.valueOf(z));
    }

    public static final int[] getSectionRadiusOptions() {
        return sectionRadiusOptions;
    }

    public static final int getDebugSectionRadiusOption() {
        return ((Number) debugSectionRadiusOption$delegate.getValue(null, $$delegatedProperties[1])).intValue();
    }

    public static final void setDebugSectionRadiusOption(int i) {
        debugSectionRadiusOption$delegate.setValue(null, $$delegatedProperties[1], Integer.valueOf(i));
    }

    public static final int getSectionRadiusDp() {
        int[] iArr = sectionRadiusOptions;
        int debugSectionRadiusOption = getDebugSectionRadiusOption();
        return (debugSectionRadiusOption < 0 || debugSectionRadiusOption >= iArr.length) ? iArr[0] : iArr[debugSectionRadiusOption];
    }

    public static final int getSanitizedSectionRadiusOption() {
        return RangesKt.coerceIn(getDebugSectionRadiusOption(), (ClosedRange<Integer>) ArraysKt.getIndices(sectionRadiusOptions));
    }
}
