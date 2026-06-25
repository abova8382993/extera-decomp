package com.exteragram.messenger.plugins.xposed;

import com.chaquo.python.PyException;
import com.chaquo.python.PyObject;
import com.exteragram.messenger.plugins.hooks.HookFilter;
import de.robv.android.xposed.XC_MethodHook;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.RandomKt$$ExternalSyntheticBUOutline0;
import kotlin.text.StringsKt;
import okhttp3.internal.url._UrlKt;
import org.lsposed.lsparanoid.Deobfuscator$exteraGramDev$TMessagesProj;
import org.telegram.messenger.FileLog;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0003\n\u0000\b\u0007\u0018\u00002\u00020\u00012\u00060\u0002j\u0002`\u0003B\u001b\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\b\u0010\tB#\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0004\b\b\u0010\fJ\u001e\u0010\u0017\u001a\u00020\u00182\u0016\u0010\u000f\u001a\u0012\u0012\u0004\u0012\u00020\u00110\u0010j\b\u0012\u0004\u0012\u00020\u0011`\u0012J\u001e\u0010\u0019\u001a\u00020\u00182\u0016\u0010\u0013\u001a\u0012\u0012\u0004\u0012\u00020\u00110\u0010j\b\u0012\u0004\u0012\u00020\u0011`\u0012J\u0016\u0010\u001a\u001a\u0012\u0012\u0004\u0012\u00020\u00110\u0010j\b\u0012\u0004\u0012\u00020\u0011`\u0012J\u0016\u0010\u001b\u001a\u0012\u0012\u0004\u0012\u00020\u00110\u0010j\b\u0012\u0004\u0012\u00020\u0011`\u0012J\u0010\u0010\u001c\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0010\u0010\u001f\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010 \u001a\u00020\u0018H\u0016J0\u0010!\u001a\u00020\u00152\u0016\u0010\"\u001a\u0012\u0012\u0004\u0012\u00020\u00110\u0010j\b\u0012\u0004\u0012\u00020\u0011`\u00122\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010#\u001a\u00020\u0015H\u0002J\u001a\u0010$\u001a\u0004\u0018\u00010\u00072\u0006\u0010%\u001a\u00020\u00072\u0006\u0010&\u001a\u00020\u0005H\u0002J\u0018\u0010'\u001a\u00020\u00182\u0006\u0010(\u001a\u00020\u00052\u0006\u0010)\u001a\u00020*H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u000f\u001a\u0012\u0012\u0004\u0012\u00020\u00110\u0010j\b\u0012\u0004\u0012\u00020\u0011`\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0013\u001a\u0012\u0012\u0004\u0012\u00020\u00110\u0010j\b\u0012\u0004\u0012\u00020\u0011`\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006+"}, m877d2 = {"Lcom/exteragram/messenger/plugins/xposed/PyMethodHook;", "Lde/robv/android/xposed/XC_MethodHook;", "Ljava/lang/AutoCloseable;", "Lkotlin/AutoCloseable;", "pluginId", _UrlKt.FRAGMENT_ENCODE_SET, "pythonCallback", "Lcom/chaquo/python/PyObject;", "<init>", "(Ljava/lang/String;Lcom/chaquo/python/PyObject;)V", "priority", _UrlKt.FRAGMENT_ENCODE_SET, "(Ljava/lang/String;Lcom/chaquo/python/PyObject;I)V", "beforeHook", "afterHook", "beforeHookedFilters", "Ljava/util/ArrayList;", "Lcom/exteragram/messenger/plugins/hooks/HookFilter;", "Lkotlin/collections/ArrayList;", "afterHookedFilters", "disabled", _UrlKt.FRAGMENT_ENCODE_SET, "closed", "setBeforeHookedFilters", _UrlKt.FRAGMENT_ENCODE_SET, "setAfterHookedFilters", "getBeforeHookedFilters", "getAfterHookedFilters", "beforeHookedMethod", "param", "Lde/robv/android/xposed/XC_MethodHook$MethodHookParam;", "afterHookedMethod", "close", "executeFilters", "filters", "isBefore", "getCallbackIfPresent", "callbackObject", "name", "handleHookError", "hookMethodName", "t", _UrlKt.FRAGMENT_ENCODE_SET, "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nPyMethodHook.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PyMethodHook.kt\ncom/exteragram/messenger/plugins/xposed/PyMethodHook\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,134:1\n1#2:135\n*E\n"})
public final class PyMethodHook extends XC_MethodHook implements AutoCloseable {
    private final PyObject afterHook;
    private ArrayList<HookFilter> afterHookedFilters;
    private final PyObject beforeHook;
    private ArrayList<HookFilter> beforeHookedFilters;
    private volatile boolean closed;
    private volatile boolean disabled;
    private final String pluginId;
    private final PyObject pythonCallback;

    public PyMethodHook(String str, PyObject pyObject) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-51222156924729L);
        this.beforeHookedFilters = new ArrayList<>();
        this.afterHookedFilters = new ArrayList<>();
        if (pyObject == null) {
            RandomKt$$ExternalSyntheticBUOutline0.m936m(Deobfuscator$exteraGramDev$TMessagesProj.getString(-51260811630393L));
            throw null;
        }
        this.pluginId = str;
        this.pythonCallback = pyObject;
        this.beforeHook = getCallbackIfPresent(pyObject, Deobfuscator$exteraGramDev$TMessagesProj.getString(-51424020387641L));
        this.afterHook = getCallbackIfPresent(pyObject, Deobfuscator$exteraGramDev$TMessagesProj.getString(-51514214700857L));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PyMethodHook(String str, PyObject pyObject, int i) {
        super(i);
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-51600114046777L);
        this.beforeHookedFilters = new ArrayList<>();
        this.afterHookedFilters = new ArrayList<>();
        if (pyObject == null) {
            RandomKt$$ExternalSyntheticBUOutline0.m936m(Deobfuscator$exteraGramDev$TMessagesProj.getString(-51638768752441L));
            throw null;
        }
        this.pluginId = str;
        this.pythonCallback = pyObject;
        this.beforeHook = getCallbackIfPresent(pyObject, Deobfuscator$exteraGramDev$TMessagesProj.getString(-51801977509689L));
        this.afterHook = getCallbackIfPresent(pyObject, Deobfuscator$exteraGramDev$TMessagesProj.getString(-51892171822905L));
    }

    public final void setBeforeHookedFilters(ArrayList<HookFilter> beforeHookedFilters) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-51978071168825L);
        this.beforeHookedFilters = beforeHookedFilters;
    }

    public final void setAfterHookedFilters(ArrayList<HookFilter> afterHookedFilters) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-52063970514745L);
        this.afterHookedFilters = afterHookedFilters;
    }

    public final ArrayList<HookFilter> getBeforeHookedFilters() {
        return this.beforeHookedFilters;
    }

    public final ArrayList<HookFilter> getAfterHookedFilters() {
        return this.afterHookedFilters;
    }

    @Override // de.robv.android.xposed.XC_MethodHook
    public void beforeHookedMethod(XC_MethodHook.MethodHookParam param) {
        PyObject pyObject;
        PyObject pyObjectCall;
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-52145574893369L);
        if (this.disabled || (pyObject = this.beforeHook) == null) {
            return;
        }
        try {
            if (executeFilters(this.beforeHookedFilters, param, true) && (pyObjectCall = pyObject.call(param)) != null) {
                pyObjectCall.close();
            }
        } catch (Throwable th) {
            handleHookError(Deobfuscator$exteraGramDev$TMessagesProj.getString(-52171344697145L), th);
        }
    }

    @Override // de.robv.android.xposed.XC_MethodHook
    public void afterHookedMethod(XC_MethodHook.MethodHookParam param) {
        PyObject pyObject;
        PyObject pyObjectCall;
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-52252949075769L);
        if (this.disabled || (pyObject = this.afterHook) == null) {
            return;
        }
        try {
            if (executeFilters(this.afterHookedFilters, param, false) && (pyObjectCall = pyObject.call(param)) != null) {
                pyObjectCall.close();
            }
        } catch (Throwable th) {
            handleHookError(Deobfuscator$exteraGramDev$TMessagesProj.getString(-52278718879545L), th);
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        if (this.closed) {
            return;
        }
        this.closed = true;
        this.disabled = true;
        PyObject pyObject = this.beforeHook;
        if (pyObject != null) {
            pyObject.close();
        }
        PyObject pyObject2 = this.afterHook;
        if (pyObject2 != null) {
            pyObject2.close();
        }
    }

    private final boolean executeFilters(ArrayList<HookFilter> filters, XC_MethodHook.MethodHookParam param, boolean isBefore) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-52356028290873L);
        for (HookFilter hookFilter : filters) {
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-52416157833017L);
            if (!hookFilter.execute(param, isBefore)) {
                return false;
            }
        }
        return true;
    }

    private final PyObject getCallbackIfPresent(PyObject callbackObject, String name) {
        if (callbackObject.containsKey(name)) {
            return (PyObject) callbackObject.get((Object) name);
        }
        return null;
    }

    private final void handleHookError(String hookMethodName, Throwable t) {
        if ((t instanceof PyException) && t.getMessage() != null && StringsKt.contains$default((CharSequence) t.getMessage(), (CharSequence) Deobfuscator$exteraGramDev$TMessagesProj.getString(-52459107505977L), false, 2, (Object) null)) {
            this.disabled = true;
            FileLog.m1046e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-52489172277049L) + this.pluginId);
            return;
        }
        FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-52699625674553L) + this.pluginId + Deobfuscator$exteraGramDev$TMessagesProj.getString(-52738280380217L) + hookMethodName + Deobfuscator$exteraGramDev$TMessagesProj.getString(-52798409922361L) + t.getMessage(), t);
    }
}
