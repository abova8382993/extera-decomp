package com.exteragram.messenger.plugins.hooks;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import org.lsposed.lsparanoid.Deobfuscator$exteraGramDev$TMessagesProj;
import org.mvel2.MVEL;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
public final /* synthetic */ class HookFilter$getCompiledExpression$compiled$1 extends FunctionReferenceImpl implements Function1<String, Serializable> {
    public static final HookFilter$getCompiledExpression$compiled$1 INSTANCE = new HookFilter$getCompiledExpression$compiled$1();

    public HookFilter$getCompiledExpression$compiled$1() {
        super(1, MVEL.class, Deobfuscator$exteraGramDev$TMessagesProj.getString(-74900311627577L), Deobfuscator$exteraGramDev$TMessagesProj.getString(-74977621038905L), 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Serializable invoke(String str) {
        return MVEL.compileExpression(str);
    }
}
