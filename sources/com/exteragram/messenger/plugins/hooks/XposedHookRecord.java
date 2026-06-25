package com.exteragram.messenger.plugins.hooks;

import androidx.camera.camera2.config.UseCaseGraphContext$$ExternalSyntheticAutoCloseableDispatcher0;
import com.sun.jna.Callback;
import de.robv.android.xposed.XC_MethodHook;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.url._UrlKt;
import org.lsposed.lsparanoid.Deobfuscator$exteraGramDev$TMessagesProj;
import org.telegram.messenger.FileLog;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u0015\u0012\f\u0010\u0002\u001a\b\u0018\u00010\u0003R\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\b\u0010\t\u001a\u00020\nH\u0016J\u0012\u0010\u000b\u001a\u00020\b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0016J\u0013\u0010\u000e\u001a\u00020\b2\b\u0010\u000f\u001a\u0004\u0018\u00010\rH\u0096\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u0014\u0010\u0002\u001a\b\u0018\u00010\u0003R\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, m877d2 = {"Lcom/exteragram/messenger/plugins/hooks/XposedHookRecord;", "Lcom/exteragram/messenger/plugins/hooks/HookRecord;", "unhookObject", "Lde/robv/android/xposed/XC_MethodHook$Unhook;", "Lde/robv/android/xposed/XC_MethodHook;", "<init>", "(Lde/robv/android/xposed/XC_MethodHook$Unhook;)V", "cleanedUp", _UrlKt.FRAGMENT_ENCODE_SET, "cleanup", _UrlKt.FRAGMENT_ENCODE_SET, "matches", "criteria", _UrlKt.FRAGMENT_ENCODE_SET, "equals", "other", "hashCode", _UrlKt.FRAGMENT_ENCODE_SET, "Companion", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
public final class XposedHookRecord implements HookRecord {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Map<AutoCloseable, Integer> callbackReferences = Collections.synchronizedMap(new IdentityHashMap());
    private volatile boolean cleanedUp;
    private final XC_MethodHook.Unhook unhookObject;

    public XposedHookRecord(XC_MethodHook.Unhook unhook) {
        this.unhookObject = unhook;
        Companion companion = INSTANCE;
        XC_MethodHook callback = unhook != null ? unhook.getCallback() : null;
        companion.retainCallback(callback instanceof AutoCloseable ? (AutoCloseable) callback : null);
    }

    @Override // com.exteragram.messenger.plugins.hooks.HookRecord
    public void cleanup() {
        long j;
        if (this.cleanedUp) {
            return;
        }
        this.cleanedUp = true;
        XC_MethodHook.Unhook unhook = this.unhookObject;
        if (unhook == null) {
            return;
        }
        try {
            unhook.unhook();
            try {
                Companion companion = INSTANCE;
                Object callback = unhook.getCallback();
                companion.releaseCallback(callback instanceof AutoCloseable ? (AutoCloseable) callback : null);
            } catch (Throwable th) {
                th = th;
                j = -73573166733113L;
                FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(j), th);
            }
        } catch (Throwable th2) {
            try {
                FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-73753555359545L), th2);
                try {
                    Companion companion2 = INSTANCE;
                    Object callback2 = unhook.getCallback();
                    companion2.releaseCallback(callback2 instanceof AutoCloseable ? (AutoCloseable) callback2 : null);
                } catch (Throwable th3) {
                    th = th3;
                    j = -73903879214905L;
                    FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(j), th);
                }
            } catch (Throwable th4) {
                try {
                    Companion companion3 = INSTANCE;
                    Object callback3 = unhook.getCallback();
                    companion3.releaseCallback(callback3 instanceof AutoCloseable ? (AutoCloseable) callback3 : null);
                } catch (Throwable th5) {
                    FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-74084267841337L), th5);
                }
                throw th4;
            }
        }
    }

    @Override // com.exteragram.messenger.plugins.hooks.HookRecord
    public boolean matches(Object criteria) {
        return (criteria instanceof XC_MethodHook.Unhook) && this.unhookObject == criteria;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return other != null && Intrinsics.areEqual(XposedHookRecord.class, other.getClass()) && this.unhookObject == ((XposedHookRecord) other).unhookObject;
    }

    public int hashCode() {
        XC_MethodHook.Unhook unhook = this.unhookObject;
        if (unhook != null) {
            return unhook.hashCode();
        }
        return 0;
    }

    /* JADX INFO: loaded from: classes4.dex */
    @Metadata(m876d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0010$\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u000b\u001a\u00020\f2\u000e\u0010\r\u001a\n\u0018\u00010\u0006j\u0004\u0018\u0001`\u0007H\u0002J\u0018\u0010\u000e\u001a\u00020\f2\u000e\u0010\r\u001a\n\u0018\u00010\u0006j\u0004\u0018\u0001`\u0007H\u0002R^\u0010\u0004\u001aR\u0012\u0014\u0012\u0012 \b*\b\u0018\u00010\u0006j\u0002`\u00070\u0006j\u0002`\u0007\u0012\f\u0012\n \b*\u0004\u0018\u00010\t0\t \b*(\u0012\u0014\u0012\u0012 \b*\b\u0018\u00010\u0006j\u0002`\u00070\u0006j\u0002`\u0007\u0012\f\u0012\n \b*\u0004\u0018\u00010\t0\t\u0018\u00010\n0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, m877d2 = {"Lcom/exteragram/messenger/plugins/hooks/XposedHookRecord$Companion;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "callbackReferences", _UrlKt.FRAGMENT_ENCODE_SET, "Ljava/lang/AutoCloseable;", "Lkotlin/AutoCloseable;", "kotlin.jvm.PlatformType", _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, "retainCallback", _UrlKt.FRAGMENT_ENCODE_SET, Callback.METHOD_NAME, "releaseCallback", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void retainCallback(AutoCloseable callback) {
            if (callback == null) {
                return;
            }
            Map map = XposedHookRecord.callbackReferences;
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-74264656467769L);
            synchronized (map) {
                try {
                    Map map2 = XposedHookRecord.callbackReferences;
                    Deobfuscator$exteraGramDev$TMessagesProj.getString(-74423570257721L);
                    Integer num = (Integer) XposedHookRecord.callbackReferences.get(callback);
                    map2.put(callback, Integer.valueOf((num != null ? num.intValue() : 0) + 1));
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void releaseCallback(AutoCloseable callback) throws Exception {
            boolean z;
            if (callback == null) {
                return;
            }
            Map map = XposedHookRecord.callbackReferences;
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-74582484047673L);
            synchronized (map) {
                try {
                    Integer num = (Integer) XposedHookRecord.callbackReferences.get(callback);
                    z = false;
                    int iIntValue = num != null ? num.intValue() : 0;
                    if (iIntValue <= 1) {
                        XposedHookRecord.callbackReferences.remove(callback);
                        z = true;
                    } else {
                        Map map2 = XposedHookRecord.callbackReferences;
                        Deobfuscator$exteraGramDev$TMessagesProj.getString(-74741397837625L);
                        map2.put(callback, Integer.valueOf(iIntValue - 1));
                    }
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (z) {
                UseCaseGraphContext$$ExternalSyntheticAutoCloseableDispatcher0.m22m(callback);
            }
        }
    }
}
