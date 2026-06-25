package org.mvel2.util;

import org.mvel2.DataConversion;
import org.mvel2.ParserContext;
import org.mvel2.ast.Sign$$ExternalSyntheticBUOutline0;
import org.mvel2.compiler.ExecutableStatement;

/* JADX INFO: loaded from: classes5.dex */
public class CollectionParser {
    public static final int ARRAY = 1;
    private static final Object[] EMPTY_ARRAY = new Object[0];
    public static final int LIST = 0;
    public static final int MAP = 2;
    private Class colType;
    private int cursor;
    private int end;
    private ParserContext pCtx;
    private char[] property;
    private int start;
    private int type;

    public CollectionParser() {
    }

    public CollectionParser(int i) {
        this.type = i;
    }

    public Object parseCollection(char[] cArr, int i, int i2, boolean z, ParserContext parserContext) {
        this.property = cArr;
        this.pCtx = parserContext;
        this.end = i2 + i;
        while (i < this.end && ParseTools.isWhitespace(cArr[i])) {
            i++;
        }
        this.cursor = i;
        this.start = i;
        return parseCollection(z);
    }

    public Object parseCollection(char[] cArr, int i, int i2, boolean z, Class cls, ParserContext parserContext) {
        if (cls != null) {
            this.colType = ParseTools.getBaseComponentType(cls);
        }
        this.property = cArr;
        this.end = i2 + i;
        while (i < this.end && ParseTools.isWhitespace(cArr[i])) {
            i++;
        }
        this.cursor = i;
        this.start = i;
        this.pCtx = parserContext;
        return parseCollection(z);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0147  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.Object parseCollection(boolean r22) {
        /*
            Method dump skipped, instruction units count: 428
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.util.CollectionParser.parseCollection(boolean):java.lang.Object");
    }

    private void subCompile(int i, int i2) {
        Class cls = this.colType;
        char[] cArr = this.property;
        if (cls == null) {
            ParseTools.subCompileExpression(cArr, i, i2, this.pCtx);
            return;
        }
        Class knownEgressType = ((ExecutableStatement) ParseTools.subCompileExpression(cArr, i, i2, this.pCtx)).getKnownEgressType();
        if (knownEgressType == null || ReflectionUtil.isAssignableFrom(this.colType, knownEgressType)) {
            return;
        }
        if (isStrongType() || !DataConversion.canConvert(knownEgressType, this.colType)) {
            Sign$$ExternalSyntheticBUOutline0.m1013m("expected type: " + this.colType.getName() + "; but found: " + knownEgressType.getName(), this.property, this.cursor);
        }
    }

    private boolean isStrongType() {
        ParserContext parserContext = this.pCtx;
        return parserContext != null && parserContext.isStrongTyping();
    }

    public int getCursor() {
        return this.cursor;
    }
}
