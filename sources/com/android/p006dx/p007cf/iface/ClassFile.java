package com.android.p006dx.p007cf.iface;

import com.android.p006dx.p007cf.code.BootstrapMethodsList;
import com.android.p006dx.rop.cst.ConstantPool;
import com.android.p006dx.rop.cst.CstString;
import com.android.p006dx.rop.cst.CstType;
import com.android.p006dx.rop.type.TypeList;

/* JADX INFO: loaded from: classes4.dex */
public interface ClassFile extends HasAttribute {
    int getAccessFlags();

    @Override // com.android.p006dx.p007cf.iface.HasAttribute
    AttributeList getAttributes();

    BootstrapMethodsList getBootstrapMethods();

    ConstantPool getConstantPool();

    FieldList getFields();

    TypeList getInterfaces();

    int getMagic();

    int getMajorVersion();

    MethodList getMethods();

    int getMinorVersion();

    CstString getSourceFile();

    CstType getSuperclass();

    CstType getThisClass();
}
