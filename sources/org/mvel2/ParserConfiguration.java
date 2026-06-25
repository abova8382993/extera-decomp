package org.mvel2;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.coroutines.Continuation;
import org.mvel2.ast.Proto;
import org.mvel2.compiler.AbstractParser;
import org.mvel2.integration.Interceptor;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.integration.impl.ClassImportResolverFactory;
import org.mvel2.integration.impl.StackResetResolverFactory;
import org.mvel2.util.MethodStub;
import org.mvel2.util.ParseTools;
import org.webrtc.GlShader$$ExternalSyntheticBUOutline1;
import p005c.a$$ExternalSyntheticBUOutline0;

/* JADX INFO: loaded from: classes.dex */
public class ParserConfiguration implements Serializable {
    protected transient ClassLoader classLoader;
    protected Map<String, Interceptor> interceptors;
    protected HashSet<String> packageImports;
    private VariableResolverFactory threadUnsafeVariableResolverFactory;
    protected final Map<String, Object> imports = new ConcurrentHashMap();
    private final transient Set<String> nonValidImports = Collections.newSetFromMap(new ConcurrentHashMap());
    private boolean allowNakedMethCall = MVEL.COMPILER_OPT_ALLOW_NAKED_METH_CALL;
    private boolean allowBootstrapBypass = true;

    public ParserConfiguration() {
    }

    public ParserConfiguration(Map<String, Object> map, Map<String, Interceptor> map2) {
        addAllImports(map);
        this.interceptors = map2;
    }

    public ParserConfiguration(Map<String, Object> map, HashSet<String> hashSet, Map<String, Interceptor> map2) {
        addAllImports(map);
        this.packageImports = hashSet;
        this.interceptors = map2;
    }

    public HashSet<String> getPackageImports() {
        return this.packageImports;
    }

    public void setPackageImports(HashSet<String> hashSet) {
        this.packageImports = hashSet;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [void] */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public Class getImport(String str) {
        if (this.imports.probeCoroutineSuspended((Continuation<?>) str) == 0 || !(this.imports.get(str) instanceof Class)) {
            return (Class) (AbstractParser.LITERALS.get(str) instanceof Class ? AbstractParser.LITERALS.get(str) : null);
        }
        return (Class) this.imports.get(str);
    }

    public MethodStub getStaticImport(String str) {
        return (MethodStub) this.imports.get(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [void] */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public Object getStaticOrClassImport(String str) {
        return this.imports.probeCoroutineSuspended((Continuation<?>) str) != 0 ? this.imports.get(str) : AbstractParser.LITERALS.get(str);
    }

    public void addPackageImport(String str) {
        if (this.packageImports == null) {
            this.packageImports = new LinkedHashSet();
        }
        this.packageImports.add(str);
        if (addClassMemberStaticImports(str)) {
            return;
        }
        this.packageImports.add(str);
    }

    private boolean addClassMemberStaticImports(String str) {
        Class<?> cls;
        try {
            cls = Class.forName(str);
        } catch (ClassNotFoundException unused) {
        } catch (IllegalAccessException e) {
            a$$ExternalSyntheticBUOutline0.m201m("error adding static imports for: ", str, e);
        }
        if (cls.isEnum()) {
            for (Enum r2 : EnumSet.allOf(cls)) {
                this.imports.put(r2.name(), r2);
            }
            return true;
        }
        for (Field field : cls.getDeclaredFields()) {
            if ((field.getModifiers() & 9) == 9) {
                this.imports.put(field.getName(), field.get(null));
            }
        }
        return false;
    }

    public void addAllImports(Map<String, Object> map) {
        if (map == null) {
            return;
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            boolean z = value instanceof Method;
            Map<String, Object> map2 = this.imports;
            if (z) {
                map2.put(entry.getKey(), new MethodStub((Method) value));
            } else {
                map2.put(entry.getKey(), value);
            }
        }
    }

    private boolean checkForDynamicImport(String str) {
        if (this.packageImports == null || !Character.isJavaIdentifierStart(str.charAt(0)) || this.nonValidImports.contains(str)) {
            return false;
        }
        Iterator<String> it = this.packageImports.iterator();
        Class clsForNameWithInner = null;
        int i = 0;
        while (it.hasNext()) {
            try {
                clsForNameWithInner = ParseTools.forNameWithInner(it.next() + "." + str, getClassLoader());
                i++;
            } catch (Throwable unused) {
            }
        }
        if (i > 1) {
            GlShader$$ExternalSyntheticBUOutline1.m1250m("ambiguous class name: ".concat(str));
            return false;
        }
        if (i == 1) {
            addImport(str, clsForNameWithInner);
            return true;
        }
        cacheNegativeHitForDynamicImport(str);
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [void] */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public boolean hasImport(String str) {
        return this.imports.probeCoroutineSuspended((Continuation<?>) str) != 0 || AbstractParser.CLASS_LITERALS.containsKey(str) || checkForDynamicImport(str);
    }

    public void addImport(Class cls) {
        addImport(cls.getSimpleName(), cls);
    }

    public void addImport(String str, Class cls) {
        this.imports.put(str, cls);
    }

    public void addImport(String str, Proto proto) {
        this.imports.put(str, proto);
    }

    public void addImport(String str, Method method) {
        addImport(str, new MethodStub(method));
    }

    public void addImport(String str, MethodStub methodStub) {
        this.imports.put(str, methodStub);
    }

    public Map<String, Interceptor> getInterceptors() {
        return this.interceptors;
    }

    public void setInterceptors(Map<String, Interceptor> map) {
        this.interceptors = map;
    }

    public Map<String, Object> getImports() {
        return this.imports;
    }

    public void setImports(Map<String, Object> map) {
        if (map == null) {
            return;
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof Class) {
                addImport(entry.getKey(), (Class) value);
            } else if (value instanceof Method) {
                addImport(entry.getKey(), (Method) value);
            } else if (value instanceof MethodStub) {
                addImport(entry.getKey(), (MethodStub) value);
            } else if (value instanceof Proto) {
                addImport(entry.getKey(), (Proto) entry.getValue());
            } else {
                throw new RuntimeException("invalid element in imports map: " + entry.getKey() + " (" + value + ")");
            }
        }
    }

    public boolean hasImports() {
        if (!this.imports.isEmpty()) {
            return true;
        }
        HashSet<String> hashSet = this.packageImports;
        return (hashSet == null || hashSet.size() == 0) ? false : true;
    }

    public ClassLoader getClassLoader() {
        ClassLoader classLoader = this.classLoader;
        if (classLoader != null) {
            return classLoader;
        }
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        this.classLoader = contextClassLoader;
        return contextClassLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [java.util.Map<java.lang.String, java.lang.Object>, kotlin.coroutines.Continuation, kotlin.coroutines.jvm.internal.DebugProbesKt] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.util.ConcurrentModificationException
    	at java.base/java.util.ArrayList$Itr.checkForComodification(ArrayList.java:1095)
    	at java.base/java.util.ArrayList$Itr.next(ArrayList.java:1049)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:358)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    /*  JADX ERROR: JadxRuntimeException in pass: FinishTypeInference
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r0v1 boolean
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:236)
        	at jadx.core.dex.visitors.typeinference.FinishTypeInference.lambda$visit$0(FinishTypeInference.java:27)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        	at jadx.core.dex.visitors.typeinference.FinishTypeInference.visit(FinishTypeInference.java:22)
        */
    public void setAllImports(java.util.Map<java.lang.String, java.lang.Object> r2) {
        /*
            r1 = this;
            java.util.Map<java.lang.String, java.lang.Object> r0 = r1.imports
            r0.probeCoroutineCreated(r0)
            if (r2 == 0) goto Lc
            java.util.Map<java.lang.String, java.lang.Object> r1 = r1.imports
            r1.putAll(r2)
        Lc:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.ParserConfiguration.setAllImports(java.util.Map):void");
    }

    public void setImports(HashMap<String, Object> map) {
        setAllImports(map);
    }

    private void cacheNegativeHitForDynamicImport(String str) {
        this.nonValidImports.add(str);
    }

    public void flushCaches() {
        this.nonValidImports.clear();
    }

    public boolean isAllowNakedMethCall() {
        return this.allowNakedMethCall;
    }

    public void setAllowNakedMethCall(boolean z) {
        this.allowNakedMethCall = z;
    }

    public boolean isAllowBootstrapBypass() {
        return this.allowBootstrapBypass;
    }

    public void setAllowBootstrapBypass(boolean z) {
        this.allowBootstrapBypass = z;
    }

    public VariableResolverFactory getVariableFactory(VariableResolverFactory variableResolverFactory) {
        if (MVEL.RUNTIME_OPT_THREAD_UNSAFE) {
            VariableResolverFactory variableResolverFactory2 = this.threadUnsafeVariableResolverFactory;
            if (variableResolverFactory2 == null) {
                this.threadUnsafeVariableResolverFactory = createVariableResolverFactory(variableResolverFactory);
            } else if (variableResolverFactory2 instanceof StackResetResolverFactory) {
                ((StackResetResolverFactory) variableResolverFactory2).setDelegate(variableResolverFactory);
            } else {
                variableResolverFactory2.setNextFactory(variableResolverFactory);
            }
            return this.threadUnsafeVariableResolverFactory;
        }
        return createVariableResolverFactory(variableResolverFactory);
    }

    private VariableResolverFactory createVariableResolverFactory(VariableResolverFactory variableResolverFactory) {
        return hasImports() ? new ClassImportResolverFactory(this, variableResolverFactory, true) : new StackResetResolverFactory(variableResolverFactory);
    }
}
