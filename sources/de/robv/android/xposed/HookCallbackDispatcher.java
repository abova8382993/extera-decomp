package de.robv.android.xposed;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import java.lang.reflect.InvocationTargetException;
import p005c.AbstractC0905e;

/* JADX INFO: loaded from: classes5.dex */
final class HookCallbackDispatcher {
    private static final Object[] EMPTY_ARGS = new Object[0];

    private HookCallbackDispatcher() {
    }

    public static Object dispatch(XposedBridge.HookInfo hookInfo, Object[] objArr) throws Throwable {
        XC_MethodHook.MethodHookParam methodHookParam = new XC_MethodHook.MethodHookParam();
        methodHookParam.method = hookInfo.getMethod();
        if (hookInfo.isStaticHook()) {
            methodHookParam.thisObject = null;
            methodHookParam.args = objArr;
        } else {
            methodHookParam.thisObject = objArr[0];
            int length = objArr.length - 1;
            if (length == 0) {
                methodHookParam.args = EMPTY_ARGS;
            } else {
                Object[] objArr2 = new Object[length];
                methodHookParam.args = objArr2;
                System.arraycopy(objArr, 1, objArr2, 0, length);
            }
        }
        Object[] snapshot = hookInfo.getCallbacks().getSnapshot();
        int length2 = snapshot.length;
        if (length2 == 0) {
            try {
                return AbstractC0905e.m204a(hookInfo.getBackup(), methodHookParam.thisObject, methodHookParam.args);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        }
        int i = 0;
        while (true) {
            try {
                ((XC_MethodHook) snapshot[i]).beforeHookedMethod(methodHookParam);
            } catch (Throwable th) {
                XposedBridge.log(th);
                methodHookParam.setResult(null);
                methodHookParam.returnEarly = false;
            }
            if (methodHookParam.returnEarly) {
                i++;
                break;
            }
            i++;
            if (i >= length2) {
                break;
            }
        }
        if (!methodHookParam.returnEarly) {
            try {
                methodHookParam.setResult(AbstractC0905e.m204a(hookInfo.getBackup(), methodHookParam.thisObject, methodHookParam.args));
            } catch (InvocationTargetException e2) {
                methodHookParam.setThrowable(e2.getCause());
            }
        }
        int i2 = i - 1;
        do {
            Object result = methodHookParam.getResult();
            Throwable throwable = methodHookParam.getThrowable();
            try {
                ((XC_MethodHook) snapshot[i2]).afterHookedMethod(methodHookParam);
            } catch (Throwable th2) {
                XposedBridge.log(th2);
                if (throwable == null) {
                    methodHookParam.setResult(result);
                } else {
                    methodHookParam.setThrowable(throwable);
                }
            }
            i2--;
        } while (i2 >= 0);
        Object resultOrThrowable = methodHookParam.getResultOrThrowable();
        return hookInfo.getReturnType() != null ? hookInfo.getReturnType().cast(resultOrThrowable) : resultOrThrowable;
    }
}
