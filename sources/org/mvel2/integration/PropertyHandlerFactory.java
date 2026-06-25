package org.mvel2.integration;

import androidx.camera.camera2.adapter.CameraInternalAdapter;
import java.util.HashMap;
import java.util.Map;
import kotlin.coroutines.Continuation;

/* JADX INFO: loaded from: classes.dex */
public class PropertyHandlerFactory {
    protected static PropertyHandler nullMethodHandler;
    protected static PropertyHandler nullPropertyHandler;
    protected static Map<Class, PropertyHandler> propertyHandlerClass = new HashMap();

    public static PropertyHandler getPropertyHandler(Class cls) {
        return propertyHandlerClass.get(cls);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [void] */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.lang.Class, java.lang.Object, kotlin.coroutines.Continuation] */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.lang.Class] */
    /* JADX WARN: Type inference failed for: r3v3, types: [void] */
    /* JADX WARN: Type inference failed for: r7v1, types: [void] */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public static boolean hasPropertyHandler(Class cls) {
        if (cls == 0) {
            return false;
        }
        if (propertyHandlerClass.probeCoroutineSuspended((Continuation<?>) cls) != 0) {
            return true;
        }
        ?? superclass = cls;
        do {
            if (cls != superclass && propertyHandlerClass.probeCoroutineSuspended((Continuation<?>) superclass) != 0) {
                Map<Class, PropertyHandler> map = propertyHandlerClass;
                map.put(cls, map.get(superclass));
                return true;
            }
            for (CameraInternalAdapter.C01072 c01072 : superclass.getInterfaces()) {
                if (propertyHandlerClass.probeCoroutineSuspended(c01072) != 0) {
                    Map<Class, PropertyHandler> map2 = propertyHandlerClass;
                    map2.put(cls, map2.get(c01072));
                    return true;
                }
            }
            superclass = superclass.getSuperclass();
            if (superclass == 0) {
                break;
            }
        } while (superclass != Object.class);
        return false;
    }

    public static void registerPropertyHandler(Class cls, PropertyHandler propertyHandler) {
        propertyHandlerClass.put(cls, propertyHandler);
    }

    public static void setNullPropertyHandler(PropertyHandler propertyHandler) {
        nullPropertyHandler = propertyHandler;
    }

    public static boolean hasNullPropertyHandler() {
        return nullPropertyHandler != null;
    }

    public static PropertyHandler getNullPropertyHandler() {
        return nullPropertyHandler;
    }

    public static void setNullMethodHandler(PropertyHandler propertyHandler) {
        nullMethodHandler = propertyHandler;
    }

    public static boolean hasNullMethodHandler() {
        return nullMethodHandler != null;
    }

    public static PropertyHandler getNullMethodHandler() {
        return nullMethodHandler;
    }

    public static void unregisterPropertyHandler(Class cls) {
        propertyHandlerClass.remove(cls);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [java.util.Map<java.lang.Class, org.mvel2.integration.PropertyHandler>, kotlin.coroutines.Continuation, kotlin.coroutines.jvm.internal.DebugProbesKt] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.util.ConcurrentModificationException
    	at java.base/java.util.ArrayList$Itr.checkForComodification(ArrayList.java:1095)
    	at java.base/java.util.ArrayList$Itr.next(ArrayList.java:1049)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:358)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    /*  JADX ERROR: JadxRuntimeException in pass: FinishTypeInference
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r0v2 boolean
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:236)
        	at jadx.core.dex.visitors.typeinference.FinishTypeInference.lambda$visit$0(FinishTypeInference.java:27)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        	at jadx.core.dex.visitors.typeinference.FinishTypeInference.visit(FinishTypeInference.java:22)
        */
    public static void disposeAll() {
        /*
            r0 = 0
            org.mvel2.integration.PropertyHandlerFactory.nullMethodHandler = r0
            org.mvel2.integration.PropertyHandlerFactory.nullPropertyHandler = r0
            java.util.Map<java.lang.Class, org.mvel2.integration.PropertyHandler> r0 = org.mvel2.integration.PropertyHandlerFactory.propertyHandlerClass
            r0.probeCoroutineCreated(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.integration.PropertyHandlerFactory.disposeAll():void");
    }
}
