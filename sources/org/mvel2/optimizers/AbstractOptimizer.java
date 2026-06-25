package org.mvel2.optimizers;

import org.mvel2.ParserContext;
import org.mvel2.ast.Sign$$ExternalSyntheticBUOutline0;
import org.mvel2.compiler.AbstractParser;
import org.mvel2.util.ParseTools;

/* JADX INFO: loaded from: classes.dex */
public class AbstractOptimizer extends AbstractParser {
    protected static final int BEAN = 0;
    protected static final int COL = 2;
    protected static final int METH = 1;
    protected static final int WITH = 3;
    protected boolean collection;
    protected Class currType;
    protected boolean nullSafe;
    protected boolean staticAccess;
    protected int tkStart;

    public AbstractOptimizer() {
        this.collection = false;
        this.nullSafe = false;
        this.currType = null;
        this.staticAccess = false;
    }

    public AbstractOptimizer(ParserContext parserContext) {
        super(parserContext);
        this.collection = false;
        this.nullSafe = false;
        this.currType = null;
        this.staticAccess = false;
    }

    /* JADX WARN: Removed duplicated region for block: B:297:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object tryStaticAccess() {
        /*
            Method dump skipped, instruction units count: 334
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.optimizers.AbstractOptimizer.tryStaticAccess():java.lang.Object");
    }

    public int nextSubToken() {
        int i;
        skipWhitespace();
        this.nullSafe = false;
        char[] cArr = this.expr;
        int i2 = this.cursor;
        this.tkStart = i2;
        char c2 = cArr[i2];
        if (c2 == '.') {
            int i3 = this.start;
            if (i3 + 1 != this.end) {
                int i4 = i2 + 1;
                this.tkStart = i4;
                this.cursor = i4;
                char c3 = cArr[i4];
                if (c3 == '?') {
                    skipWhitespace();
                    int i5 = this.tkStart + 1;
                    this.tkStart = i5;
                    this.cursor = i5;
                    if (i5 == this.end) {
                        Sign$$ExternalSyntheticBUOutline0.m1013m("unexpected end of statement", this.expr, this.start);
                        return 0;
                    }
                    this.nullSafe = true;
                    this.fields = -1;
                } else {
                    if (c3 == '{') {
                        return 3;
                    }
                    if (ParseTools.isWhitespace(c3)) {
                        skipWhitespace();
                        this.tkStart = this.cursor;
                    }
                }
            } else {
                Sign$$ExternalSyntheticBUOutline0.m1013m("unexpected end of statement", cArr, i3);
                return 0;
            }
        } else if (c2 != '?') {
            if (c2 == '[') {
                return 2;
            }
            if (c2 == '{' && cArr[i2 - 1] == '.') {
                return 3;
            }
        } else if (this.start == i2) {
            this.tkStart = i2 + 1;
            this.cursor = i2 + 1;
            this.nullSafe = true;
        }
        do {
            i = this.cursor + 1;
            this.cursor = i;
            if (i >= this.end) {
                break;
            }
        } while (ParseTools.isIdentifierPart(this.expr[i]));
        skipWhitespace();
        int i6 = this.cursor;
        if (i6 >= this.end) {
            return 0;
        }
        char c4 = this.expr[i6];
        if (c4 != '(') {
            return c4 != '[' ? 0 : 2;
        }
        return 1;
    }

    public String capture() {
        char[] cArr = this.expr;
        int iTrimRight = trimRight(this.tkStart);
        this.tkStart = iTrimRight;
        return new String(cArr, iTrimRight, trimLeft(this.cursor) - this.tkStart);
    }

    public void whiteSpaceSkip() {
        if (this.cursor < this.length) {
            while (ParseTools.isWhitespace(this.expr[this.cursor])) {
                int i = this.cursor + 1;
                this.cursor = i;
                if (i == this.length) {
                    return;
                }
            }
        }
    }

    public boolean scanTo(char c2) {
        while (true) {
            int i = this.cursor;
            int i2 = this.end;
            if (i >= i2) {
                return true;
            }
            char[] cArr = this.expr;
            char c3 = cArr[i];
            if (c3 == '\"' || c3 == '\'') {
                this.cursor = ParseTools.captureStringLiteral(c3, cArr, i, i2);
            }
            char[] cArr2 = this.expr;
            int i3 = this.cursor;
            if (cArr2[i3] == c2) {
                return false;
            }
            this.cursor = i3 + 1;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x0027  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x002a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int findLastUnion() {
        /*
            r7 = this;
            int r0 = r7.start
            int r1 = r7.length
            int r0 = r0 + r1
            r1 = 1
            int r0 = r0 - r1
            r2 = -1
            r3 = 0
            r4 = r2
        La:
            int r5 = r7.start
            if (r0 == r5) goto L3b
            char[] r5 = r7.expr
            char r5 = r5[r0]
            r6 = 46
            if (r5 == r6) goto L32
            r6 = 91
            if (r5 == r6) goto L2a
            r6 = 93
            if (r5 == r6) goto L27
            r6 = 123(0x7b, float:1.72E-43)
            if (r5 == r6) goto L2a
            r6 = 125(0x7d, float:1.75E-43)
            if (r5 == r6) goto L27
            goto L35
        L27:
            int r3 = r3 + 1
            goto L35
        L2a:
            int r3 = r3 + (-1)
            if (r3 != 0) goto L35
            r7.collection = r1
        L30:
            r4 = r0
            goto L35
        L32:
            if (r3 != 0) goto L35
            goto L30
        L35:
            if (r4 == r2) goto L38
            return r4
        L38:
            int r0 = r0 + (-1)
            goto La
        L3b:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.optimizers.AbstractOptimizer.findLastUnion():int");
    }
}
