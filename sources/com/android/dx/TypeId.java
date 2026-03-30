package com.android.dx;

import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.type.Type;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public final class TypeId {
    public static final TypeId BOOLEAN;
    public static final TypeId BYTE;
    public static final TypeId CHAR;
    public static final TypeId DOUBLE;
    public static final TypeId FLOAT;
    public static final TypeId INT;
    public static final TypeId LONG;
    public static final TypeId OBJECT;
    private static final Map PRIMITIVE_TO_TYPE;
    public static final TypeId SHORT;
    public static final TypeId STRING;
    public static final TypeId VOID;
    final CstType constant;
    final String name;
    final Type ropType;

    static {
        TypeId typeId = new TypeId(Type.BOOLEAN);
        BOOLEAN = typeId;
        TypeId typeId2 = new TypeId(Type.BYTE);
        BYTE = typeId2;
        TypeId typeId3 = new TypeId(Type.CHAR);
        CHAR = typeId3;
        TypeId typeId4 = new TypeId(Type.DOUBLE);
        DOUBLE = typeId4;
        TypeId typeId5 = new TypeId(Type.FLOAT);
        FLOAT = typeId5;
        TypeId typeId6 = new TypeId(Type.INT);
        INT = typeId6;
        TypeId typeId7 = new TypeId(Type.LONG);
        LONG = typeId7;
        TypeId typeId8 = new TypeId(Type.SHORT);
        SHORT = typeId8;
        TypeId typeId9 = new TypeId(Type.VOID);
        VOID = typeId9;
        OBJECT = new TypeId(Type.OBJECT);
        STRING = new TypeId(Type.STRING);
        HashMap map = new HashMap();
        PRIMITIVE_TO_TYPE = map;
        map.put(Boolean.TYPE, typeId);
        map.put(Byte.TYPE, typeId2);
        map.put(Character.TYPE, typeId3);
        map.put(Double.TYPE, typeId4);
        map.put(Float.TYPE, typeId5);
        map.put(Integer.TYPE, typeId6);
        map.put(Long.TYPE, typeId7);
        map.put(Short.TYPE, typeId8);
        map.put(Void.TYPE, typeId9);
    }

    TypeId(Type type) {
        this(type.getDescriptor(), type);
    }

    TypeId(String str, Type type) {
        if (str == null || type == null) {
            throw null;
        }
        this.name = str;
        this.ropType = type;
        this.constant = CstType.intern(type);
    }

    public static TypeId get(String str) {
        return new TypeId(str, Type.internReturnType(str));
    }

    public static TypeId get(Class cls) {
        if (cls.isPrimitive()) {
            return (TypeId) PRIMITIVE_TO_TYPE.get(cls);
        }
        String strReplace = cls.getName().replace('.', '/');
        if (!cls.isArray()) {
            strReplace = 'L' + strReplace + ';';
        }
        return get(strReplace);
    }

    public FieldId getField(TypeId typeId, String str) {
        return new FieldId(this, typeId, str);
    }

    public MethodId getConstructor(TypeId... typeIdArr) {
        return new MethodId(this, VOID, "<init>", new TypeList(typeIdArr));
    }

    public MethodId getMethod(TypeId typeId, String str, TypeId... typeIdArr) {
        return new MethodId(this, typeId, str, new TypeList(typeIdArr));
    }

    public boolean equals(Object obj) {
        return (obj instanceof TypeId) && ((TypeId) obj).name.equals(this.name);
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public String toString() {
        return this.name;
    }
}
