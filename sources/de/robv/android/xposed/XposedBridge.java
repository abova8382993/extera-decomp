package de.robv.android.xposed;

import android.util.Log;
import com.sun.jna.Callback;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.Unhook;
import dev.exterahook.runtime.bridge.HookBridgeProvider;
import dev.exterahook.runtime.bridge.JniBridgeBindings;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import kotlin.Unit;
import okhttp3.internal.url._UrlKt;
import org.lsposed.hiddenapibypass.HiddenApiBypass;
import org.mvel2.util.Make$Map$$ExternalSyntheticBUOutline0;
import p000a.C0002c;
import p000a.InterfaceC0000a;
import p004b.C0898a;
import p005c.AbstractC0905e;
import p005c.AbstractC0907g;
import p005c.C0904d;
import p005c.InterfaceC0902b;
import p005c.InterfaceC0903c;
import p005c.g$$ExternalSyntheticBUOutline1;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes.dex */
public class XposedBridge {
    private static final Object[] EMPTY_ARRAY = new Object[0];
    private static final String TAG = "exteraHook-XposedBridge";
    private static final Method callbackMethod;
    private static final InterfaceC0000a hookBridge;
    private static final C0904d hookRegistry;

    public static final class CopyOnWriteSortedSet<E> {
        private volatile transient Object[] elements = XposedBridge.EMPTY_ARRAY;

        private int indexOf(Object[] objArr, Object obj) {
            for (int i = 0; i < objArr.length; i++) {
                if (obj.equals(objArr[i])) {
                    return i;
                }
            }
            return -1;
        }

        private int insertionIndex(Object[] objArr, E e) {
            for (Object obj : objArr) {
                if (e.equals(obj)) {
                    return -1;
                }
            }
            Comparable comparable = (Comparable) e;
            for (int i = 0; i < objArr.length; i++) {
                if (comparable.compareTo(objArr[i]) < 0) {
                    return i;
                }
            }
            return objArr.length;
        }

        public synchronized boolean add(E e) {
            Object[] objArr = this.elements;
            int iInsertionIndex = insertionIndex(objArr, e);
            if (iInsertionIndex < 0) {
                return false;
            }
            Object[] objArr2 = new Object[objArr.length + 1];
            System.arraycopy(objArr, 0, objArr2, 0, iInsertionIndex);
            objArr2[iInsertionIndex] = e;
            System.arraycopy(objArr, iInsertionIndex, objArr2, iInsertionIndex + 1, objArr.length - iInsertionIndex);
            this.elements = objArr2;
            return true;
        }

        public Object[] getSnapshot() {
            return this.elements;
        }

        public synchronized boolean remove(E e) {
            Object[] objArr = this.elements;
            int iIndexOf = indexOf(objArr, e);
            if (iIndexOf == -1) {
                return false;
            }
            Object[] objArr2 = new Object[objArr.length - 1];
            System.arraycopy(objArr, 0, objArr2, 0, iIndexOf);
            System.arraycopy(objArr, iIndexOf + 1, objArr2, iIndexOf, (objArr.length - iIndexOf) - 1);
            this.elements = objArr2;
            return true;
        }

        public int size() {
            return this.elements.length;
        }
    }

    public static class HookInfo {
        private Member backup;
        final CopyOnWriteSortedSet<XC_MethodHook> callbacks = new CopyOnWriteSortedSet<>();
        private final boolean isStatic;
        private final Member method;
        private final Class<?> returnType;

        public HookInfo(Member member) {
            this.method = member;
            this.isStatic = Modifier.isStatic(member.getModifiers());
            if (member instanceof Method) {
                Class<?> returnType = ((Method) member).getReturnType();
                if (!returnType.isPrimitive()) {
                    this.returnType = returnType;
                    return;
                }
            }
            this.returnType = null;
        }

        public Object callback(Object[] objArr) {
            return HookCallbackDispatcher.dispatch(this, objArr);
        }

        public Member getBackup() {
            return this.backup;
        }

        public CopyOnWriteSortedSet<XC_MethodHook> getCallbacks() {
            return this.callbacks;
        }

        public Member getMethod() {
            return this.method;
        }

        public Class<?> getReturnType() {
            return this.returnType;
        }

        public boolean isStaticHook() {
            return this.isStatic;
        }

        public void setBackup(Member member) {
            this.backup = member;
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$-10kFl-2JjBfEwc-KLdb607Wxxs */
    public static Member m3484$r8$lambda$10kFl2JjBfEwcKLdb607Wxxs(Object obj, Member member) {
        InterfaceC0000a interfaceC0000aBridge = bridge();
        Method method = callbackMethod;
        C0002c c0002c = (C0002c) interfaceC0000aBridge;
        c0002c.getClass();
        c0002c.m1a();
        return JniBridgeBindings.hook0(obj, member, method);
    }

    static {
        try {
            callbackMethod = HookInfo.class.getMethod(Callback.METHOD_NAME, Object[].class);
            hookRegistry = new C0904d();
            hookBridge = HookBridgeProvider.createDefault();
        } catch (Throwable th) {
            Make$Map$$ExternalSyntheticBUOutline0.m1024m("Failed to initialize callback bridge", th);
        }
    }

    private static InterfaceC0000a bridge() {
        return hookBridge;
    }

    public static boolean deoptimizeMethod(Member member) {
        AbstractC0907g.m205a(member);
        C0002c c0002c = (C0002c) bridge();
        c0002c.getClass();
        c0002c.m1a();
        return JniBridgeBindings.deoptimize0(member);
    }

    public static boolean disableHiddenApiRestrictions() {
        boolean zDisableHiddenApiRestrictions;
        C0898a c0898a = ((C0002c) bridge()).f1b;
        boolean z = true;
        if (c0898a.f81b) {
            return true;
        }
        synchronized (c0898a) {
            if (!c0898a.f81b) {
                if (HiddenApiBypass.addHiddenApiExemptions(_UrlKt.FRAGMENT_ENCODE_SET)) {
                    zDisableHiddenApiRestrictions = true;
                } else {
                    c0898a.f80a.f82a.m209a();
                    zDisableHiddenApiRestrictions = JniBridgeBindings.disableHiddenApiRestrictions();
                }
                if (zDisableHiddenApiRestrictions) {
                    c0898a.f81b = true;
                }
                z = zDisableHiddenApiRestrictions;
            }
        }
        return z;
    }

    public static boolean disableProfileSaver() {
        ((C0002c) bridge()).m1a();
        return JniBridgeBindings.disableProfileSaver0();
    }

    public static Set<XC_MethodHook.Unhook> hookAllConstructors(Class<?> cls, XC_MethodHook xC_MethodHook) {
        HashSet hashSet = new HashSet();
        for (Constructor<?> constructor : cls.getDeclaredConstructors()) {
            hashSet.add(hookMethod(constructor, xC_MethodHook));
        }
        return hashSet;
    }

    public static Set<XC_MethodHook.Unhook> hookAllMethods(Class<?> cls, String str, XC_MethodHook xC_MethodHook) {
        HashSet hashSet = new HashSet();
        for (Method method : cls.getDeclaredMethods()) {
            if (method.getName().equals(str)) {
                hashSet.add(hookMethod(method, xC_MethodHook));
            }
        }
        return hashSet;
    }

    public static XC_MethodHook.Unhook hookMethod(Member member, XC_MethodHook xC_MethodHook) {
        AbstractC0907g.m205a(member);
        if (xC_MethodHook == null) {
            g$$ExternalSyntheticBUOutline2.m208m("callback must not be null");
            return null;
        }
        C0904d c0904d = hookRegistry;
        InterfaceC0902b interfaceC0902b = new InterfaceC0902b() { // from class: de.robv.android.xposed.XposedBridge$$ExternalSyntheticLambda0
            @Override // p005c.InterfaceC0902b
            /* JADX INFO: renamed from: a */
            public final Member mo202a(Object obj, Member member2) {
                return XposedBridge.m3484$r8$lambda$10kFl2JjBfEwcKLdb607Wxxs(obj, member2);
            }
        };
        c0904d.getClass();
        synchronized (c0904d.f83a) {
            try {
                HookInfo hookInfo = (HookInfo) c0904d.f83a.get(member);
                if (hookInfo == null) {
                    hookInfo = new HookInfo(member);
                    Member memberMo202a = interfaceC0902b.mo202a(hookInfo, member);
                    if (memberMo202a == null) {
                        throw new IllegalStateException("Failed to hook method");
                    }
                    hookInfo.setBackup(memberMo202a);
                    c0904d.f83a.put(member, hookInfo);
                }
                hookInfo.getCallbacks().add(xC_MethodHook);
            } catch (Throwable th) {
                throw th;
            }
        }
        return xC_MethodHook.new Unhook(member);
    }

    public static <S, T extends S> boolean invokeConstructor(T t, Constructor<S> constructor, Object... objArr) {
        if (t == null) {
            g$$ExternalSyntheticBUOutline2.m208m("instance");
            return false;
        }
        if (constructor == null) {
            g$$ExternalSyntheticBUOutline2.m208m("constructor");
            return false;
        }
        if (constructor.isVarArgs()) {
            g$$ExternalSyntheticBUOutline1.m207m("varargs parameters are not supported");
            return false;
        }
        if (objArr.length == 0) {
            objArr = null;
        }
        C0002c c0002c = (C0002c) bridge();
        c0002c.getClass();
        c0002c.m1a();
        return JniBridgeBindings.invokeConstructor0(t, constructor, objArr);
    }

    public static Object invokeOriginalMethod(Member member, Object obj, Object[] objArr) {
        HookInfo hookInfo;
        if (objArr == null) {
            objArr = EMPTY_ARRAY;
        }
        C0904d c0904d = hookRegistry;
        c0904d.getClass();
        synchronized (c0904d.f83a) {
            hookInfo = (HookInfo) c0904d.f83a.get(member);
        }
        try {
            if (hookInfo != null) {
                return AbstractC0905e.m204a(hookInfo.getBackup(), obj, objArr);
            }
            AbstractC0907g.m205a(member);
            return AbstractC0905e.m204a(member, obj, objArr);
        } catch (InstantiationException unused) {
            g$$ExternalSyntheticBUOutline1.m207m("The class this Constructor belongs to is abstract and cannot be instantiated");
            return null;
        }
    }

    public static boolean isHooked(Member member) {
        boolean zContainsKey;
        C0904d c0904d = hookRegistry;
        c0904d.getClass();
        synchronized (c0904d.f83a) {
            zContainsKey = c0904d.f83a.containsKey(member);
        }
        return zContainsKey;
    }

    private static boolean isHooked0(Member member) {
        C0002c c0002c = (C0002c) bridge();
        c0002c.getClass();
        c0002c.m1a();
        return JniBridgeBindings.isHooked0(member);
    }

    public static void log(Throwable th) {
        Log.e(TAG, "Uncaught Exception", th);
    }

    @Deprecated
    public static void unhookMethod(Member member, XC_MethodHook xC_MethodHook) {
        C0904d c0904d = hookRegistry;
        final InterfaceC0000a interfaceC0000aBridge = bridge();
        Objects.requireNonNull(interfaceC0000aBridge);
        InterfaceC0903c interfaceC0903c = new InterfaceC0903c() { // from class: de.robv.android.xposed.XposedBridge$$ExternalSyntheticLambda1
            @Override // p005c.InterfaceC0903c
            /* JADX INFO: renamed from: a */
            public final boolean mo203a(Member member2) {
                return interfaceC0000aBridge.mo0a(member2);
            }
        };
        c0904d.getClass();
        synchronized (c0904d.f83a) {
            try {
                HookInfo hookInfo = (HookInfo) c0904d.f83a.get(member);
                if (hookInfo == null) {
                    return;
                }
                hookInfo.getCallbacks().remove(xC_MethodHook);
                if (hookInfo.getCallbacks().size() == 0) {
                    c0904d.f83a.remove(member);
                    interfaceC0903c.mo203a(member);
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static <T> T allocateInstance(Class<T> cls) {
        if (cls != null) {
            C0002c c0002c = (C0002c) bridge();
            c0002c.getClass();
            c0002c.m1a();
            return (T) JniBridgeBindings.allocateInstance0(cls);
        }
        g$$ExternalSyntheticBUOutline2.m208m("clazz");
        return null;
    }

    public static boolean makeClassInheritable(Class<?> cls) {
        if (cls != null) {
            C0002c c0002c = (C0002c) bridge();
            c0002c.getClass();
            c0002c.m1a();
            return JniBridgeBindings.makeClassInheritable0(cls);
        }
        g$$ExternalSyntheticBUOutline2.m208m("class must not be null");
        return false;
    }
}
