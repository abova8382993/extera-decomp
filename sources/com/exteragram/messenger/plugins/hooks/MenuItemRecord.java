package com.exteragram.messenger.plugins.hooks;

import android.content.Context;
import android.text.TextUtils;
import com.chaquo.python.PyObject;
import com.exteragram.messenger.plugins.utils.PyObjectUtils;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import okhttp3.internal.url._UrlKt;
import org.lsposed.lsparanoid.Deobfuscator$exteraGramDev$TMessagesProj;
import org.mvel2.MVEL;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\b\u0002\b\u0007\u0018\u0000 *2\u00020\u0001:\u0001*B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0006\u0010!\u001a\u00020\"J\u0010\u0010#\u001a\u00020\"2\b\u0010$\u001a\u0004\u0018\u00010\u0001J\u0013\u0010%\u001a\u00020\u000b2\b\u0010&\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010'\u001a\u00020\u0018H\u0016J\u001c\u0010(\u001a\u00020\u000b2\u0014\u0010$\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0001\u0018\u00010)R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\tR\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\tR\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\tR\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\tR\u0011\u0010\u0017\u001a\u00020\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0013\u0010\u001b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\tR\u0013\u0010\u001d\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\tR\u0011\u0010\u001f\u001a\u00020\u0018¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u001a¨\u0006+"}, m877d2 = {"Lcom/exteragram/messenger/plugins/hooks/MenuItemRecord;", _UrlKt.FRAGMENT_ENCODE_SET, "pluginId", _UrlKt.FRAGMENT_ENCODE_SET, "pyData", "Lcom/chaquo/python/PyObject;", "<init>", "(Ljava/lang/String;Lcom/chaquo/python/PyObject;)V", "getPluginId", "()Ljava/lang/String;", "cleanedUp", _UrlKt.FRAGMENT_ENCODE_SET, "itemId", "getItemId", "menuType", "getMenuType", "text", "getText", "onClickCallback", "getOnClickCallback", "()Lcom/chaquo/python/PyObject;", "iconName", "getIconName", "iconResId", _UrlKt.FRAGMENT_ENCODE_SET, "getIconResId", "()I", "subtext", "getSubtext", "conditionString", "getConditionString", "priority", "getPriority", "cleanup", _UrlKt.FRAGMENT_ENCODE_SET, "executeClick", "contextData", "equals", "other", "hashCode", "checkCondition", _UrlKt.FRAGMENT_ENCODE_SET, "Companion", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nMenuItemRecord.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MenuItemRecord.kt\ncom/exteragram/messenger/plugins/hooks/MenuItemRecord\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,143:1\n1#2:144\n*E\n"})
public final class MenuItemRecord {
    private static final ConcurrentHashMap<String, Serializable> mvelExpressionCache = new ConcurrentHashMap<>();
    private volatile boolean cleanedUp;
    private final String conditionString;
    private final String iconName;
    private final int iconResId;
    private final String itemId;
    private final String menuType;
    private final PyObject onClickCallback;
    private final String pluginId;
    private final int priority;
    private final String subtext;
    private final String text;

    public MenuItemRecord(String str, PyObject pyObject) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-71163690080057L);
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-71202344785721L);
        this.pluginId = str;
        this.menuType = PyObjectUtils.getString(pyObject, Deobfuscator$exteraGramDev$TMessagesProj.getString(-71232409556793L), null, true);
        this.text = PyObjectUtils.getString(pyObject, Deobfuscator$exteraGramDev$TMessagesProj.getString(-71275359229753L), null, true);
        this.onClickCallback = pyObject.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-71296834066233L), Deobfuscator$exteraGramDev$TMessagesProj.getString(-71314013935417L));
        String string = PyObjectUtils.getString(pyObject, Deobfuscator$exteraGramDev$TMessagesProj.getString(-71352668641081L), null, true);
        string = (string == null || string.length() == 0) ? null : string;
        if (string == null) {
            string = UUID.randomUUID().toString();
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-71387028379449L);
        }
        this.itemId = string;
        String string2 = PyObjectUtils.getString(pyObject, Deobfuscator$exteraGramDev$TMessagesProj.getString(-71447157921593L), null, true);
        this.iconName = string2;
        this.subtext = PyObjectUtils.getString(pyObject, Deobfuscator$exteraGramDev$TMessagesProj.getString(-71468632758073L), null, true);
        this.conditionString = PyObjectUtils.getString(pyObject, Deobfuscator$exteraGramDev$TMessagesProj.getString(-71502992496441L), null, true);
        int identifier = 0;
        this.priority = PyObjectUtils.getInt(pyObject, Deobfuscator$exteraGramDev$TMessagesProj.getString(-71545942169401L), 0, true);
        if (!TextUtils.isEmpty(string2)) {
            try {
                Context context = ApplicationLoader.applicationContext;
                Deobfuscator$exteraGramDev$TMessagesProj.getString(-71584596875065L);
                identifier = context.getResources().getIdentifier(string2, Deobfuscator$exteraGramDev$TMessagesProj.getString(-71666201253689L), context.getPackageName());
            } catch (Exception unused) {
            }
        }
        this.iconResId = identifier;
        if (TextUtils.isEmpty(this.menuType) || TextUtils.isEmpty(this.text) || this.onClickCallback == null) {
            g$$ExternalSyntheticBUOutline1.m207m(Deobfuscator$exteraGramDev$TMessagesProj.getString(-71704855959353L));
            throw null;
        }
    }

    public final String getPluginId() {
        return this.pluginId;
    }

    public final String getItemId() {
        return this.itemId;
    }

    public final String getMenuType() {
        return this.menuType;
    }

    public final String getText() {
        return this.text;
    }

    public final PyObject getOnClickCallback() {
        return this.onClickCallback;
    }

    public final String getIconName() {
        return this.iconName;
    }

    public final int getIconResId() {
        return this.iconResId;
    }

    public final String getSubtext() {
        return this.subtext;
    }

    public final String getConditionString() {
        return this.conditionString;
    }

    public final int getPriority() {
        return this.priority;
    }

    public final void cleanup() {
        if (this.cleanedUp) {
            return;
        }
        this.cleanedUp = true;
        try {
            PyObject pyObject = this.onClickCallback;
            if (pyObject != null) {
                pyObject.close();
            }
        } catch (Exception unused) {
        }
    }

    public final void executeClick(Object contextData) {
        PyObject pyObjectCall;
        if (this.cleanedUp) {
            return;
        }
        try {
            PyObject pyObject = this.onClickCallback;
            if (pyObject == null || (pyObjectCall = pyObject.call(contextData)) == null) {
                return;
            }
            pyObjectCall.close();
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other != null && Intrinsics.areEqual(MenuItemRecord.class, other.getClass())) {
            MenuItemRecord menuItemRecord = (MenuItemRecord) other;
            if (Intrinsics.areEqual(this.itemId, menuItemRecord.itemId) && Intrinsics.areEqual(this.pluginId, menuItemRecord.pluginId)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (this.itemId.hashCode() * 31) + this.pluginId.hashCode();
    }

    public final boolean checkCondition(Map<String, ? extends Object> contextData) {
        String str = this.conditionString;
        if (str == null || str.length() == 0 || contextData == null) {
            return true;
        }
        try {
            ConcurrentHashMap<String, Serializable> concurrentHashMap = mvelExpressionCache;
            final MenuItemRecord$checkCondition$compiled$1 menuItemRecord$checkCondition$compiled$1 = MenuItemRecord$checkCondition$compiled$1.INSTANCE;
            Serializable serializableComputeIfAbsent = concurrentHashMap.computeIfAbsent(str, new Function() { // from class: com.exteragram.messenger.plugins.hooks.MenuItemRecord$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return MenuItemRecord.$r8$lambda$hr1hlLIaQobH5EJk748ZU6CcfyA(menuItemRecord$checkCondition$compiled$1, obj);
                }
            });
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-72035568441145L);
            Boolean bool = (Boolean) MVEL.executeExpression((Object) serializableComputeIfAbsent, (Map) contextData, Boolean.TYPE);
            if (bool != null) {
                return bool.booleanValue();
            }
        } catch (Exception unused) {
        }
        return false;
    }

    public static Serializable $r8$lambda$hr1hlLIaQobH5EJk748ZU6CcfyA(Function1 function1, Object obj) {
        return (Serializable) function1.invoke(obj);
    }
}
