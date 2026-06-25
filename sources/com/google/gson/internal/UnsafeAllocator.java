package com.google.gson.internal;

import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import okio.Buffer$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes.dex */
public abstract class UnsafeAllocator {
    public static final UnsafeAllocator INSTANCE = create();

    public abstract <T> T newInstance(Class<T> cls);

    public static void assertInstantiable(Class<?> cls) {
        String strCheckInstantiable = ConstructorConstructor.checkInstantiable(cls);
        if (strCheckInstantiable == null) {
            return;
        }
        Buffer$$ExternalSyntheticBUOutline2.m976m("UnsafeAllocator is used for non-instantiable type: ".concat(strCheckInstantiable));
    }

    private static UnsafeAllocator create() {
        try {
            try {
                try {
                    Class<?> cls = Class.forName("sun.misc.Unsafe");
                    Field declaredField = cls.getDeclaredField("theUnsafe");
                    declaredField.setAccessible(true);
                    return new UnsafeAllocator() { // from class: com.google.gson.internal.UnsafeAllocator.1
                        final /* synthetic */ Method val$allocateInstance;
                        final /* synthetic */ Object val$unsafe;

                        public C19871(Method method, Object obj) {
                            method = method;
                            obj = obj;
                        }

                        @Override // com.google.gson.internal.UnsafeAllocator
                        public <T> T newInstance(Class<T> cls2) {
                            UnsafeAllocator.assertInstantiable(cls2);
                            return (T) method.invoke(obj, cls2);
                        }
                    };
                } catch (Exception unused) {
                    return new UnsafeAllocator() { // from class: com.google.gson.internal.UnsafeAllocator.4
                        @Override // com.google.gson.internal.UnsafeAllocator
                        public <T> T newInstance(Class<T> cls2) {
                            throw new UnsupportedOperationException("Cannot allocate " + cls2 + ". Usage of JDK sun.misc.Unsafe is enabled, but it could not be used. Make sure your runtime is configured correctly.");
                        }
                    };
                }
            } catch (Exception unused2) {
                Method declaredMethod = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", Class.class);
                declaredMethod.setAccessible(true);
                int iIntValue = ((Integer) declaredMethod.invoke(null, Object.class)).intValue();
                Method declaredMethod2 = ObjectStreamClass.class.getDeclaredMethod("newInstance", Class.class, Integer.TYPE);
                declaredMethod2.setAccessible(true);
                return new UnsafeAllocator() { // from class: com.google.gson.internal.UnsafeAllocator.2
                    final /* synthetic */ int val$constructorId;
                    final /* synthetic */ Method val$newInstance;

                    public C19882(Method declaredMethod22, int iIntValue2) {
                        method = declaredMethod22;
                        i = iIntValue2;
                    }

                    @Override // com.google.gson.internal.UnsafeAllocator
                    public <T> T newInstance(Class<T> cls2) {
                        UnsafeAllocator.assertInstantiable(cls2);
                        return (T) method.invoke(null, cls2, Integer.valueOf(i));
                    }
                };
            }
        } catch (Exception unused3) {
            Method declaredMethod3 = ObjectInputStream.class.getDeclaredMethod("newInstance", Class.class, Class.class);
            declaredMethod3.setAccessible(true);
            return new UnsafeAllocator() { // from class: com.google.gson.internal.UnsafeAllocator.3
                final /* synthetic */ Method val$newInstance;

                public C19893(Method declaredMethod32) {
                    method = declaredMethod32;
                }

                @Override // com.google.gson.internal.UnsafeAllocator
                public <T> T newInstance(Class<T> cls2) {
                    UnsafeAllocator.assertInstantiable(cls2);
                    return (T) method.invoke(null, cls2, Object.class);
                }
            };
        }
    }

    /* JADX INFO: renamed from: com.google.gson.internal.UnsafeAllocator$1 */
    public class C19871 extends UnsafeAllocator {
        final /* synthetic */ Method val$allocateInstance;
        final /* synthetic */ Object val$unsafe;

        public C19871(Method method, Object obj) {
            method = method;
            obj = obj;
        }

        @Override // com.google.gson.internal.UnsafeAllocator
        public <T> T newInstance(Class<T> cls2) {
            UnsafeAllocator.assertInstantiable(cls2);
            return (T) method.invoke(obj, cls2);
        }
    }

    /* JADX INFO: renamed from: com.google.gson.internal.UnsafeAllocator$2 */
    /* JADX INFO: loaded from: classes5.dex */
    public class C19882 extends UnsafeAllocator {
        final /* synthetic */ int val$constructorId;
        final /* synthetic */ Method val$newInstance;

        public C19882(Method declaredMethod22, int iIntValue2) {
            method = declaredMethod22;
            i = iIntValue2;
        }

        @Override // com.google.gson.internal.UnsafeAllocator
        public <T> T newInstance(Class<T> cls2) {
            UnsafeAllocator.assertInstantiable(cls2);
            return (T) method.invoke(null, cls2, Integer.valueOf(i));
        }
    }

    /* JADX INFO: renamed from: com.google.gson.internal.UnsafeAllocator$3 */
    /* JADX INFO: loaded from: classes5.dex */
    public class C19893 extends UnsafeAllocator {
        final /* synthetic */ Method val$newInstance;

        public C19893(Method declaredMethod32) {
            method = declaredMethod32;
        }

        @Override // com.google.gson.internal.UnsafeAllocator
        public <T> T newInstance(Class<T> cls2) {
            UnsafeAllocator.assertInstantiable(cls2);
            return (T) method.invoke(null, cls2, Object.class);
        }
    }

    /* JADX INFO: renamed from: com.google.gson.internal.UnsafeAllocator$4 */
    /* JADX INFO: loaded from: classes5.dex */
    public class C19904 extends UnsafeAllocator {
        @Override // com.google.gson.internal.UnsafeAllocator
        public <T> T newInstance(Class<T> cls2) {
            throw new UnsupportedOperationException("Cannot allocate " + cls2 + ". Usage of JDK sun.misc.Unsafe is enabled, but it could not be used. Make sure your runtime is configured correctly.");
        }
    }
}
