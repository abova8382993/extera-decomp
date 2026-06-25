package org.mvel2.integration.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import kotlin.coroutines.Continuation;
import org.mvel2.UnresolveablePropertyException;
import org.mvel2.ast.Proto$ProtoContextFactory$$ExternalSyntheticBUOutline0;
import org.mvel2.integration.VariableResolver;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.util.StaticFieldStub$$ExternalSyntheticBUOutline0;

/* JADX INFO: loaded from: classes.dex */
public class CachingMapVariableResolverFactory extends BaseVariableResolverFactory {
    protected Map<String, Object> variables;

    public CachingMapVariableResolverFactory(Map map) {
        this.variables = map;
    }

    @Override // org.mvel2.integration.VariableResolverFactory
    public VariableResolver createVariable(String str, Object obj) {
        try {
            VariableResolver variableResolver = getVariableResolver(str);
            variableResolver.setValue(obj);
            return variableResolver;
        } catch (UnresolveablePropertyException unused) {
            SimpleSTValueResolver simpleSTValueResolver = new SimpleSTValueResolver(obj, null, true);
            addResolver(str, simpleSTValueResolver);
            return simpleSTValueResolver;
        }
    }

    @Override // org.mvel2.integration.VariableResolverFactory
    public VariableResolver createVariable(String str, Object obj, Class<?> cls) {
        VariableResolver variableResolver;
        try {
            variableResolver = getVariableResolver(str);
        } catch (UnresolveablePropertyException unused) {
            variableResolver = null;
        }
        if (variableResolver != null && variableResolver.getType() != null) {
            StaticFieldStub$$ExternalSyntheticBUOutline0.m1029m("variable already defined within scope: ", variableResolver.getType(), " ", str);
            return null;
        }
        SimpleSTValueResolver simpleSTValueResolver = new SimpleSTValueResolver(obj, cls, true);
        addResolver(str, simpleSTValueResolver);
        return simpleSTValueResolver;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v4, types: [void] */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // org.mvel2.integration.impl.BaseVariableResolverFactory, org.mvel2.integration.VariableResolverFactory
    public VariableResolver getVariableResolver(String str) {
        VariableResolver variableResolver = this.variableResolvers.get(str);
        if (variableResolver != null) {
            return variableResolver;
        }
        if (this.variables.probeCoroutineSuspended((Continuation<?>) str) != 0) {
            Map<String, VariableResolver> map = this.variableResolvers;
            SimpleSTValueResolver simpleSTValueResolver = new SimpleSTValueResolver(this.variables.get(str), null);
            map.put(str, simpleSTValueResolver);
            return simpleSTValueResolver;
        }
        VariableResolverFactory variableResolverFactory = this.nextFactory;
        if (variableResolverFactory != null) {
            return variableResolverFactory.getVariableResolver(str);
        }
        Proto$ProtoContextFactory$$ExternalSyntheticBUOutline0.m1012m(str);
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [void] */
    /* JADX WARN: Type inference failed for: r0v3, types: [void] */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // org.mvel2.integration.VariableResolverFactory
    public boolean isResolveable(String str) {
        if (this.variableResolvers.probeCoroutineSuspended((Continuation<?>) str) != 0) {
            return true;
        }
        Map<String, Object> map = this.variables;
        if (map != 0 && map.probeCoroutineSuspended((Continuation<?>) str) != 0) {
            return true;
        }
        VariableResolverFactory variableResolverFactory = this.nextFactory;
        return variableResolverFactory != null && variableResolverFactory.isResolveable(str);
    }

    public VariableResolver addResolver(String str, VariableResolver variableResolver) {
        this.variableResolvers.put(str, variableResolver);
        return variableResolver;
    }

    public void externalize() {
        for (Map.Entry<String, VariableResolver> entry : this.variableResolvers.entrySet()) {
            if (entry.getValue().getFlags() == -1) {
                this.variables.put(entry.getKey(), entry.getValue().getValue());
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [boolean, void] */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // org.mvel2.integration.VariableResolverFactory
    public boolean isTarget(String str) {
        return this.variableResolvers.probeCoroutineSuspended((Continuation<?>) str);
    }

    @Override // org.mvel2.integration.impl.BaseVariableResolverFactory, org.mvel2.integration.VariableResolverFactory
    public Set<String> getKnownVariables() {
        VariableResolverFactory variableResolverFactory = this.nextFactory;
        Map<String, Object> map = this.variables;
        if (variableResolverFactory == null) {
            if (map != null) {
                return new HashSet(this.variables.keySet());
            }
            return new HashSet(0);
        }
        if (map != null) {
            return new HashSet(this.variables.keySet());
        }
        return new HashSet(0);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [java.util.Map<java.lang.String, org.mvel2.integration.VariableResolver>, kotlin.coroutines.Continuation, kotlin.coroutines.jvm.internal.DebugProbesKt] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.util.Map<java.lang.String, java.lang.Object>, kotlin.coroutines.jvm.internal.DebugProbesKt] */
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
    public void clear() {
        /*
            r1 = this;
            java.util.Map<java.lang.String, org.mvel2.integration.VariableResolver> r0 = r1.variableResolvers
            r0.probeCoroutineCreated(r0)
            java.util.Map<java.lang.String, java.lang.Object> r1 = r1.variables
            r1.probeCoroutineCreated(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.integration.impl.CachingMapVariableResolverFactory.clear():void");
    }
}
