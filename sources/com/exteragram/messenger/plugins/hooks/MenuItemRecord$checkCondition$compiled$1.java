package com.exteragram.messenger.plugins.hooks;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import org.lsposed.lsparanoid.Deobfuscator$exteraGramDev$TMessagesProj;
import org.mvel2.MVEL;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
public final /* synthetic */ class MenuItemRecord$checkCondition$compiled$1 extends FunctionReferenceImpl implements Function1<String, Serializable> {
    public static final MenuItemRecord$checkCondition$compiled$1 INSTANCE = new MenuItemRecord$checkCondition$compiled$1();

    public MenuItemRecord$checkCondition$compiled$1() {
        super(1, MVEL.class, Deobfuscator$exteraGramDev$TMessagesProj.getString(-75235319076665L), Deobfuscator$exteraGramDev$TMessagesProj.getString(-75312628487993L), 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Serializable invoke(String str) {
        return MVEL.compileExpression(str);
    }
}
