package org.mvel2.compiler;

import com.android.p006dx.Constants$$ExternalSyntheticBUOutline0;
import java.lang.reflect.Field;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import kotlin.coroutines.Continuation;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;
import org.mvel2.ast.Negation$$ExternalSyntheticBUOutline0;
import org.mvel2.ast.Sign$$ExternalSyntheticBUOutline0;
import org.mvel2.optimizers.AbstractOptimizer;
import org.mvel2.optimizers.impl.refl.nodes.WithAccessor;
import org.mvel2.util.ParseTools;
import org.mvel2.util.PropertyTools;

/* JADX INFO: loaded from: classes.dex */
public class PropertyVerifier extends AbstractOptimizer {
    private static final int COL = 2;
    private static final int DONE = -1;
    private static final int METH = 1;
    private static final int NORM = 0;
    private static final int WITH = 3;
    private boolean classLiteral;
    private Class ctx;
    private boolean deepProperty;
    private boolean first;
    private boolean fqcn;
    private List<String> inputs;
    private boolean methodCall;
    private Map<String, Type> paramTypes;
    private boolean resolvedExternally;

    public PropertyVerifier(char[] cArr, ParserContext parserContext) {
        this.inputs = new LinkedList();
        this.first = false;
        this.classLiteral = false;
        this.methodCall = false;
        this.deepProperty = false;
        this.fqcn = false;
        this.ctx = null;
        this.expr = cArr;
        int length = cArr.length;
        this.end = length;
        this.length = length;
        this.pCtx = parserContext;
    }

    public PropertyVerifier(char[] cArr, int i, int i2, ParserContext parserContext) {
        this.inputs = new LinkedList();
        this.first = false;
        this.classLiteral = false;
        this.methodCall = false;
        this.deepProperty = false;
        this.fqcn = false;
        this.ctx = null;
        this.expr = cArr;
        this.start = i;
        this.length = i2;
        this.end = i + i2;
        this.pCtx = parserContext;
    }

    public PropertyVerifier(String str, ParserContext parserContext) {
        this.inputs = new LinkedList();
        this.first = false;
        this.classLiteral = false;
        this.methodCall = false;
        this.deepProperty = false;
        this.fqcn = false;
        this.ctx = null;
        char[] charArray = str.toCharArray();
        this.expr = charArray;
        int length = charArray.length;
        this.end = length;
        this.length = length;
        this.pCtx = parserContext;
    }

    public PropertyVerifier(String str, ParserContext parserContext, Class cls) {
        this.inputs = new LinkedList();
        this.first = false;
        this.classLiteral = false;
        this.methodCall = false;
        this.deepProperty = false;
        this.fqcn = false;
        this.ctx = null;
        char[] charArray = str.toCharArray();
        this.expr = charArray;
        int length = charArray.length;
        this.length = length;
        this.end = length;
        if (str.length() > 0 && str.charAt(0) == '.') {
            this.start = 1;
            this.f1065st = 1;
            this.cursor = 1;
        }
        this.pCtx = parserContext;
        this.ctx = cls;
    }

    public List<String> getInputs() {
        return this.inputs;
    }

    public void setInputs(List<String> list) {
        this.inputs = list;
    }

    public Class analyze() {
        this.cursor = this.start;
        this.resolvedExternally = true;
        if (this.ctx == null) {
            this.ctx = Object.class;
            this.first = true;
        }
        while (this.cursor < this.end) {
            this.classLiteral = false;
            int iNextSubToken = nextSubToken();
            if (iNextSubToken == 0) {
                this.ctx = getBeanProperty(this.ctx, capture());
            } else if (iNextSubToken == 1) {
                this.ctx = getMethod(this.ctx, capture());
            } else if (iNextSubToken == 2) {
                this.ctx = getCollectionProperty(this.ctx, capture());
            } else if (iNextSubToken == 3) {
                this.ctx = getWithProperty(this.ctx);
            }
            if (this.cursor < this.length && !this.first) {
                this.deepProperty = true;
            }
            this.first = false;
        }
        return this.ctx;
    }

    private void recordTypeParmsForProperty(String str) {
        if (this.pCtx.isStrictTypeEnforcement()) {
            ParserContext parserContext = this.pCtx;
            parserContext.setLastTypeParameters(parserContext.getTypeParametersAsArray(str));
        }
    }

    private Class getBeanProperty(Class cls, String str) {
        byte b2;
        if (this.first) {
            boolean zHasVarOrInput = this.pCtx.hasVarOrInput(str);
            ParserContext parserContext = this.pCtx;
            if (zHasVarOrInput) {
                if (parserContext.isStrictTypeEnforcement()) {
                    recordTypeParmsForProperty(str);
                }
                return this.pCtx.getVarOrInputType(str);
            }
            if (parserContext.hasImport(str)) {
                this.resolvedExternally = false;
                return this.pCtx.getImport(str);
            }
            if (!this.pCtx.isStrongTyping()) {
                return Object.class;
            }
            if (this.pCtx.hasVarOrInput("this")) {
                if (this.pCtx.isStrictTypeEnforcement()) {
                    recordTypeParmsForProperty("this");
                }
                cls = this.pCtx.getVarOrInputType("this");
                this.resolvedExternally = false;
            }
        }
        this.f1065st = this.cursor;
        Member fieldOrAccessor = cls != null ? PropertyTools.getFieldOrAccessor(cls, str) : null;
        if (MVEL.COMPILER_OPT_SUPPORT_JAVA_STYLE_CLASS_LITERALS && "class".equals(str)) {
            return Class.class;
        }
        if (fieldOrAccessor instanceof Field) {
            if (this.pCtx.isStrictTypeEnforcement()) {
                Field field = (Field) fieldOrAccessor;
                if (field.getGenericType() != null) {
                    if (field.getGenericType() instanceof ParameterizedType) {
                        ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                        this.pCtx.setLastTypeParameters(parameterizedType.getActualTypeArguments());
                        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                        TypeVariable<Class<?>>[] typeParameters = type2Class(parameterizedType.getRawType()).getTypeParameters();
                        if (actualTypeArguments.length > 0 && this.paramTypes == null) {
                            this.paramTypes = new HashMap();
                        }
                        for (int i = 0; i < actualTypeArguments.length; i++) {
                            this.paramTypes.put(typeParameters[i].toString(), actualTypeArguments[i]);
                        }
                    } else if (field.getGenericType() instanceof TypeVariable) {
                        Type typeRemove = this.paramTypes.remove(((TypeVariable) field.getGenericType()).getName());
                        if (typeRemove != null && (typeRemove instanceof Class)) {
                            return (Class) typeRemove;
                        }
                    }
                }
                return field.getType();
            }
            return ((Field) fieldOrAccessor).getType();
        }
        if (fieldOrAccessor != null) {
            return getReturnType(cls, (Method) fieldOrAccessor);
        }
        ParserContext parserContext2 = this.pCtx;
        if (parserContext2 != null && this.first && parserContext2.hasImport(str) && this.pCtx.getImport(str) != null) {
            return this.pCtx.getImport(str);
        }
        ParserContext parserContext3 = this.pCtx;
        if (parserContext3 != null && parserContext3.getLastTypeParameters() != null && this.pCtx.getLastTypeParameters().length != 0) {
            if (!Collection.class.isAssignableFrom(cls)) {
                b2 = Map.class.isAssignableFrom(cls) ? (byte) 1 : (byte) 0;
            }
            Type type = this.pCtx.getLastTypeParameters()[b2];
            this.pCtx.setLastTypeParameters(null);
            return type instanceof ParameterizedType ? Object.class : (Class) type;
        }
        if (this.pCtx != null && "length".equals(str) && cls.isArray()) {
            return Integer.class;
        }
        Object objTryStaticAccess = tryStaticAccess();
        if (objTryStaticAccess != null) {
            this.fqcn = true;
            this.resolvedExternally = false;
            if (objTryStaticAccess instanceof Class) {
                boolean z = (MVEL.COMPILER_OPT_SUPPORT_JAVA_STYLE_CLASS_LITERALS && new String(this.expr, this.end - 6, 6).equals(".class")) ? false : true;
                this.classLiteral = z;
                return z ? (Class) objTryStaticAccess : Class.class;
            }
            if (objTryStaticAccess instanceof Field) {
                try {
                    return ((Field) objTryStaticAccess).get(null).getClass();
                } catch (Exception e) {
                    Negation$$ExternalSyntheticBUOutline0.m1011m("in verifier: ", this.expr, this.start, e);
                    return null;
                }
            }
            try {
                return ((Method) objTryStaticAccess).getReturnType();
            } catch (Exception e2) {
                Negation$$ExternalSyntheticBUOutline0.m1011m("in verifier: ", this.expr, this.start, e2);
                return null;
            }
        }
        if (cls != null) {
            try {
                return ParseTools.findClass(this.variableFactory, cls.getName() + "$" + str, this.pCtx);
            } catch (ClassNotFoundException unused) {
            }
        }
        ParserContext parserContext4 = this.pCtx;
        if (parserContext4 == null || parserContext4.getParserConfiguration() == null ? MVEL.COMPILER_OPT_ALLOW_NAKED_METH_CALL : this.pCtx.getParserConfiguration().isAllowNakedMethCall()) {
            Class method = this.getMethod(cls, str);
            if (method != Object.class) {
                return method;
            }
        }
        if (!this.pCtx.isStrictTypeEnforcement()) {
            return Object.class;
        }
        Sign$$ExternalSyntheticBUOutline0.m1013m("unqualified type in strict mode for: " + str, this.expr, this.tkStart);
        return null;
    }

    private Class getReturnType(Class cls, Method method) {
        Class<?> declaringClass = method.getDeclaringClass();
        if (cls == declaringClass) {
            return returnGenericType(method);
        }
        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType instanceof TypeVariable) {
            String name = ((TypeVariable) genericReturnType).getName();
            Type genericSuperclass = cls.getGenericSuperclass();
            Class<? super Object> superclass = cls.getSuperclass();
            while (superclass != null && superclass != declaringClass) {
                genericSuperclass = superclass.getGenericSuperclass();
                superclass = superclass.getSuperclass();
            }
            if (superclass == null) {
                return returnGenericType(method);
            }
            if (genericSuperclass instanceof ParameterizedType) {
                TypeVariable<Class<? super Object>>[] typeParameters = superclass.getTypeParameters();
                int i = 0;
                while (true) {
                    if (i >= typeParameters.length) {
                        i = -1;
                        break;
                    }
                    if (typeParameters[i].getName().equals(name)) {
                        break;
                    }
                    i++;
                }
                if (i < 0) {
                    return returnGenericType(method);
                }
                Type type = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[i];
                return type instanceof Class ? (Class) type : returnGenericType(method);
            }
        }
        return returnGenericType(method);
    }

    private void recordParametricReturnedType(Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            this.pCtx.setLastTypeParameters(parameterizedType.getActualTypeArguments());
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            TypeVariable<Class<?>>[] typeParameters = type2Class(parameterizedType.getRawType()).getTypeParameters();
            if (actualTypeArguments.length > 0 && this.paramTypes == null) {
                this.paramTypes = new HashMap();
            }
            for (int i = 0; i < actualTypeArguments.length; i++) {
                this.paramTypes.put(typeParameters[i].toString(), actualTypeArguments[i]);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [void] */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.Object, java.lang.String, kotlin.coroutines.Continuation] */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    private Class<?> returnGenericType(Method method) {
        Type genericReturnType = method.getGenericReturnType();
        recordParametricReturnedType(genericReturnType);
        ?? string = genericReturnType.toString();
        if (genericReturnType instanceof ParameterizedType) {
            this.pCtx.setLastTypeParameters(((ParameterizedType) genericReturnType).getActualTypeArguments());
        }
        Map<String, Type> map = this.paramTypes;
        if (map != 0 && map.probeCoroutineSuspended((Continuation<?>) string) != 0) {
            return type2Class(this.paramTypes.get(string));
        }
        return method.getReturnType();
    }

    private Class getCollectionProperty(Class cls, String str) {
        Class<Object> cls2 = Object.class;
        Class subComponentType = cls;
        if (this.first) {
            boolean zHasVarOrInput = this.pCtx.hasVarOrInput(str);
            ParserContext parserContext = this.pCtx;
            if (zHasVarOrInput) {
                subComponentType = ParseTools.getSubComponentType(parserContext.getVarOrInputType(str));
            } else if (parserContext.hasImport(str)) {
                this.resolvedExternally = false;
                subComponentType = ParseTools.getSubComponentType(this.pCtx.getImport(str));
            } else {
                subComponentType = cls2;
            }
        }
        Class componentType = cls2;
        if (this.pCtx.isStrictTypeEnforcement()) {
            Class beanProperty = subComponentType;
            if (str.length() != 0) {
                beanProperty = getBeanProperty(subComponentType, str);
            }
            if (Map.class.isAssignableFrom(beanProperty)) {
                Type type = cls2;
                if (this.pCtx.getLastTypeParameters() != null) {
                    type = cls2;
                    if (this.pCtx.getLastTypeParameters().length != 0) {
                        type = this.pCtx.getLastTypeParameters()[1];
                    }
                }
                componentType = type2Class(type);
            } else if (Collection.class.isAssignableFrom(beanProperty)) {
                componentType = cls2;
                if (this.pCtx.getLastTypeParameters() != null) {
                    componentType = cls2;
                    if (this.pCtx.getLastTypeParameters().length != 0) {
                        componentType = type2Class(this.pCtx.getLastTypeParameters()[0]);
                    }
                }
            } else if (beanProperty.isArray()) {
                componentType = beanProperty.getComponentType();
            } else {
                if (this.pCtx.isStrongTyping()) {
                    Sign$$ExternalSyntheticBUOutline0.m1013m("unknown collection type: " + beanProperty + "; property=" + str, this.expr, this.start);
                    return null;
                }
                componentType = beanProperty;
            }
        }
        this.cursor++;
        skipWhitespace();
        int i = this.cursor;
        if (scanTo(']')) {
            addFatalError("unterminated [ in token");
        }
        MVEL.analysisCompile(new String(this.expr, i, this.cursor - i), this.pCtx);
        this.cursor++;
        return componentType;
    }

    /* JADX WARN: Removed duplicated region for block: B:211:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x00b0  */
    /* JADX WARN: Type inference failed for: r13v14, types: [java.util.HashMap, java.util.Map, kotlin.coroutines.jvm.internal.DebugProbesKt] */
    /* JADX WARN: Type inference failed for: r2v16, types: [void] */
    /* JADX WARN: Type inference failed for: r2v17, types: [void] */
    /* JADX WARN: Type inference failed for: r5v18, types: [java.lang.Object, java.lang.String, kotlin.coroutines.Continuation] */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.Class getMethod(java.lang.Class r21, java.lang.String r22) {
        /*
            Method dump skipped, instruction units count: 964
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.compiler.PropertyVerifier.getMethod(java.lang.Class, java.lang.String):java.lang.Class");
    }

    private static Class<?> type2Class(Type type) {
        if (type == null) {
            return null;
        }
        if (type instanceof Class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            return type2Class(((ParameterizedType) type).getRawType());
        }
        if (type instanceof TypeVariable) {
            GenericDeclaration genericDeclaration = ((TypeVariable) type).getGenericDeclaration();
            return genericDeclaration instanceof Method ? ((Method) genericDeclaration).getReturnType() : Object.class;
        }
        Constants$$ExternalSyntheticBUOutline0.m216m("Unknown type ", type);
        return null;
    }

    private Class getWithProperty(Class cls) {
        String strTrim = new String(this.expr, 0, this.cursor - 1).trim();
        int i = this.cursor;
        int i2 = i + 1;
        int iBalancedCaptureWithLineAccounting = ParseTools.balancedCaptureWithLineAccounting(this.expr, i, this.end, '{', this.pCtx);
        ParserContext parserContext = this.pCtx;
        char[] cArr = this.expr;
        this.cursor = iBalancedCaptureWithLineAccounting + 1;
        new WithAccessor(parserContext, strTrim, cArr, i2, iBalancedCaptureWithLineAccounting - i2, cls);
        return cls;
    }

    public boolean isResolvedExternally() {
        return this.resolvedExternally;
    }

    public boolean isClassLiteral() {
        return this.classLiteral;
    }

    public boolean isDeepProperty() {
        return this.deepProperty;
    }

    public boolean isInput() {
        return this.resolvedExternally && !this.methodCall;
    }

    public boolean isMethodCall() {
        return this.methodCall;
    }

    public boolean isFqcn() {
        return this.fqcn;
    }

    public Class getCtx() {
        return this.ctx;
    }

    public void setCtx(Class cls) {
        this.ctx = cls;
    }
}
