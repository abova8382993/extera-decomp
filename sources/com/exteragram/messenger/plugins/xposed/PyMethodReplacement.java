package com.exteragram.messenger.plugins.xposed;

import com.chaquo.python.PyException;
import com.chaquo.python.PyObject;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import kotlin.Metadata;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.RandomKt$$ExternalSyntheticBUOutline0;
import kotlin.text.StringsKt;
import okhttp3.internal.url._UrlKt;
import org.lsposed.lsparanoid.Deobfuscator$exteraGramDev$TMessagesProj;
import org.telegram.messenger.FileLog;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\b\u0007\u0018\u00002\u00020\u00012\u00060\u0002j\u0002`\u0003B\u001b\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\b\u0010\tB#\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0004\b\b\u0010\fJ\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001a"}, m877d2 = {"Lcom/exteragram/messenger/plugins/xposed/PyMethodReplacement;", "Lde/robv/android/xposed/XC_MethodReplacement;", "Ljava/lang/AutoCloseable;", "Lkotlin/AutoCloseable;", "pluginId", _UrlKt.FRAGMENT_ENCODE_SET, "pythonCallback", "Lcom/chaquo/python/PyObject;", "<init>", "(Ljava/lang/String;Lcom/chaquo/python/PyObject;)V", "priority", _UrlKt.FRAGMENT_ENCODE_SET, "(Ljava/lang/String;Lcom/chaquo/python/PyObject;I)V", "replaceHook", "disabled", _UrlKt.FRAGMENT_ENCODE_SET, "closed", "replaceHookedMethod", _UrlKt.FRAGMENT_ENCODE_SET, "param", "Lde/robv/android/xposed/XC_MethodHook$MethodHookParam;", "close", _UrlKt.FRAGMENT_ENCODE_SET, "handleHookError", "t", _UrlKt.FRAGMENT_ENCODE_SET, "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nPyMethodReplacement.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PyMethodReplacement.kt\ncom/exteragram/messenger/plugins/xposed/PyMethodReplacement\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,85:1\n1#2:86\n*E\n"})
public final class PyMethodReplacement extends XC_MethodReplacement implements AutoCloseable {
    private volatile boolean closed;
    private volatile boolean disabled;
    private final String pluginId;
    private final PyObject pythonCallback;
    private final PyObject replaceHook;

    public PyMethodReplacement(String str, PyObject pyObject) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-49143392753465L);
        if (pyObject == null) {
            RandomKt$$ExternalSyntheticBUOutline0.m936m(Deobfuscator$exteraGramDev$TMessagesProj.getString(-49182047459129L));
            throw null;
        }
        if (!pyObject.containsKey(Deobfuscator$exteraGramDev$TMessagesProj.getString(-49345256216377L))) {
            RandomKt$$ExternalSyntheticBUOutline0.m936m(Deobfuscator$exteraGramDev$TMessagesProj.getString(-49439745496889L));
            throw null;
        }
        this.pluginId = str;
        this.pythonCallback = pyObject;
        Object obj = pyObject.get((Object) Deobfuscator$exteraGramDev$TMessagesProj.getString(-49753278109497L));
        if (obj != null) {
            this.replaceHook = (PyObject) obj;
        } else {
            RandomKt$$ExternalSyntheticBUOutline0.m936m(Deobfuscator$exteraGramDev$TMessagesProj.getString(-49847767390009L));
            throw null;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PyMethodReplacement(String str, PyObject pyObject, int i) {
        super(i);
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-49955141572409L);
        if (pyObject == null) {
            RandomKt$$ExternalSyntheticBUOutline0.m936m(Deobfuscator$exteraGramDev$TMessagesProj.getString(-49993796278073L));
            throw null;
        }
        if (!pyObject.containsKey(Deobfuscator$exteraGramDev$TMessagesProj.getString(-50157005035321L))) {
            RandomKt$$ExternalSyntheticBUOutline0.m936m(Deobfuscator$exteraGramDev$TMessagesProj.getString(-50251494315833L));
            throw null;
        }
        this.pluginId = str;
        this.pythonCallback = pyObject;
        Object obj = pyObject.get((Object) Deobfuscator$exteraGramDev$TMessagesProj.getString(-50565026928441L));
        if (obj != null) {
            this.replaceHook = (PyObject) obj;
        } else {
            RandomKt$$ExternalSyntheticBUOutline0.m936m(Deobfuscator$exteraGramDev$TMessagesProj.getString(-50659516208953L));
            throw null;
        }
    }

    @Override // de.robv.android.xposed.XC_MethodReplacement
    public Object replaceHookedMethod(XC_MethodHook.MethodHookParam param) {
        PyObject pyObjectCall;
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-50766890391353L);
        Object java = null;
        if (this.disabled) {
            return null;
        }
        try {
            pyObjectCall = this.replaceHook.call(param);
            if (pyObjectCall != null) {
                try {
                    java = pyObjectCall.toJava(Object.class);
                } catch (Throwable th) {
                    th = th;
                    try {
                        handleHookError(th);
                        if (pyObjectCall != null) {
                            pyObjectCall.close();
                        }
                        return null;
                    } finally {
                        if (pyObjectCall != null) {
                            pyObjectCall.close();
                        }
                    }
                }
            }
            return java;
        } catch (Throwable th2) {
            th = th2;
            pyObjectCall = null;
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        if (this.closed) {
            return;
        }
        this.closed = true;
        this.disabled = true;
        this.replaceHook.close();
    }

    private final void handleHookError(Throwable t) {
        if ((t instanceof PyException) && t.getMessage() != null && StringsKt.contains$default((CharSequence) t.getMessage(), (CharSequence) Deobfuscator$exteraGramDev$TMessagesProj.getString(-50792660195129L), false, 2, (Object) null)) {
            this.disabled = true;
            FileLog.m1046e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-50822724966201L) + this.pluginId);
            return;
        }
        FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-51033178363705L) + this.pluginId + Deobfuscator$exteraGramDev$TMessagesProj.getString(-51071833069369L) + t.getMessage(), t);
    }
}
