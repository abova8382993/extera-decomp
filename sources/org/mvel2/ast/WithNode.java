package org.mvel2.ast;

import java.io.Serializable;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;
import org.mvel2.compiler.ExecutableStatement;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.util.ParseTools;
import org.mvel2.util.PropertyTools;

/* JADX INFO: loaded from: classes5.dex */
public class WithNode extends BlockNode implements NestedStatement {
    protected String nestParm;
    protected ParmValuePair[] withExpressions;

    public WithNode(char[] cArr, int i, int i2, int i3, int i4, int i5, ParserContext parserContext) {
        super(parserContext);
        this.expr = cArr;
        this.start = i;
        this.offset = i2;
        this.nestParm = ParseTools.createStringTrimmed(cArr, i, i2);
        this.blockStart = i3;
        this.blockOffset = i4;
        if ((i5 & 16) != 0) {
            parserContext.setBlockSymbols(true);
            ExecutableStatement executableStatement = (ExecutableStatement) ParseTools.subCompileExpression(cArr, i, i2, parserContext);
            this.compiledBlock = executableStatement;
            Class knownEgressType = executableStatement.getKnownEgressType();
            this.egressType = knownEgressType;
            this.withExpressions = compileWithExpressions(cArr, i3, i4, this.nestParm, knownEgressType, parserContext);
            parserContext.setBlockSymbols(false);
        }
    }

    @Override // org.mvel2.ast.ASTNode
    public Object getReducedValueAccelerated(Object obj, Object obj2, VariableResolverFactory variableResolverFactory) {
        Object value = this.compiledBlock.getValue(obj, obj2, variableResolverFactory);
        if (value == null) {
            Sign$$ExternalSyntheticBUOutline0.m1013m("with-block against null pointer", this.expr, this.start);
            return null;
        }
        for (ParmValuePair parmValuePair : this.withExpressions) {
            parmValuePair.eval(value, variableResolverFactory);
        }
        return value;
    }

    @Override // org.mvel2.ast.ASTNode
    public Object getReducedValue(Object obj, Object obj2, VariableResolverFactory variableResolverFactory) {
        String str = this.nestParm;
        char[] cArr = this.expr;
        int i = this.blockStart;
        int i2 = this.blockOffset;
        Object objEval = MVEL.eval(cArr, this.start, this.offset, obj, variableResolverFactory);
        ParseTools.parseWithExpressions(str, cArr, i, i2, objEval, variableResolverFactory);
        return objEval;
    }

    /* JADX WARN: Code restructure failed: missing block: B:134:0x0168, code lost:
    
        continue;
     */
    /* JADX WARN: Removed duplicated region for block: B:107:0x01ba A[Catch: CompileException -> 0x01a2, TryCatch #0 {CompileException -> 0x01a2, blocks: (B:93:0x0176, B:97:0x017f, B:99:0x0184, B:103:0x01ab, B:102:0x01a4, B:104:0x01b2, B:105:0x01b7, B:107:0x01ba, B:109:0x01d8, B:108:0x01c1), top: B:115:0x0176 }] */
    /* JADX WARN: Removed duplicated region for block: B:108:0x01c1 A[Catch: CompileException -> 0x01a2, TryCatch #0 {CompileException -> 0x01a2, blocks: (B:93:0x0176, B:97:0x017f, B:99:0x0184, B:103:0x01ab, B:102:0x01a4, B:104:0x01b2, B:105:0x01b7, B:107:0x01ba, B:109:0x01d8, B:108:0x01c1), top: B:115:0x0176 }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0120 A[PHI: r9
  0x0120: PHI (r9v8 int) = (r9v6 int), (r9v9 int) binds: [B:75:0x0148, B:62:0x011e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0155  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static org.mvel2.ast.WithNode.ParmValuePair[] compileWithExpressions(char[] r19, int r20, int r21, java.lang.String r22, java.lang.Class r23, org.mvel2.ParserContext r24) {
        /*
            Method dump skipped, instruction units count: 516
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.ast.WithNode.compileWithExpressions(char[], int, int, java.lang.String, java.lang.Class, org.mvel2.ParserContext):org.mvel2.ast.WithNode$ParmValuePair[]");
    }

    @Override // org.mvel2.ast.NestedStatement
    public ExecutableStatement getNestedStatement() {
        return this.compiledBlock;
    }

    public ParmValuePair[] getWithExpressions() {
        return this.withExpressions;
    }

    public static final class ParmValuePair implements Serializable {
        private Serializable setExpression;
        private ExecutableStatement statement;

        public ParmValuePair(String str, ExecutableStatement executableStatement, Class cls, ParserContext parserContext) {
            if (str != null && str.length() != 0) {
                this.setExpression = MVEL.compileSetExpression(str, cls != null ? PropertyTools.getReturnType(cls, str, parserContext) : Object.class, parserContext);
            }
            this.statement = executableStatement;
        }

        public Serializable getSetExpression() {
            return this.setExpression;
        }

        public ExecutableStatement getStatement() {
            return this.statement;
        }

        public void eval(Object obj, VariableResolverFactory variableResolverFactory) {
            Serializable serializable = this.setExpression;
            ExecutableStatement executableStatement = this.statement;
            if (serializable == null) {
                executableStatement.getValue(obj, variableResolverFactory);
            } else {
                MVEL.executeSetExpression(serializable, obj, variableResolverFactory, executableStatement.getValue(obj, variableResolverFactory));
            }
        }
    }
}
