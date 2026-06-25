package p005c;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

/* JADX INFO: renamed from: c.e */
/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractC0905e {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: a */
    public static final Object m204a(Member member, Object obj, Object[] objArr) {
        if (objArr == null) {
            objArr = AbstractC0906f.f84a;
        }
        AccessibleObject accessibleObject = (AccessibleObject) member;
        if (!accessibleObject.isAccessible()) {
            accessibleObject.setAccessible(true);
        }
        return member instanceof Method ? ((Method) member).invoke(obj, objArr) : ((Constructor) member).newInstance(objArr);
    }
}
