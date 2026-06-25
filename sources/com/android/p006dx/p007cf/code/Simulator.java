package com.android.p006dx.p007cf.code;

import com.android.p006dx.dex.DexOptions;
import com.android.p006dx.p007cf.code.BytecodeArray;
import com.android.p006dx.rop.cst.Constant;
import com.android.p006dx.rop.cst.CstMethodRef;
import com.android.p006dx.rop.cst.CstType;
import com.android.p006dx.rop.type.Type;
import com.android.p006dx.util.Hex;
import java.util.ArrayList;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
public class Simulator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String LOCAL_MISMATCH_ERROR = "This is symptomatic of .class transformation tools that ignore local variable information.";
    private final BytecodeArray code;
    private final DexOptions dexOptions;
    private final LocalVariableList localVariables;
    private final Machine machine;
    private ConcreteMethod method;
    private final SimVisitor visitor;

    public Simulator(Machine machine, ConcreteMethod concreteMethod, DexOptions dexOptions) {
        if (machine == null) {
            g$$ExternalSyntheticBUOutline2.m208m("machine == null");
            throw null;
        }
        if (concreteMethod == null) {
            g$$ExternalSyntheticBUOutline2.m208m("method == null");
            throw null;
        }
        if (dexOptions == null) {
            g$$ExternalSyntheticBUOutline2.m208m("dexOptions == null");
            throw null;
        }
        this.machine = machine;
        this.code = concreteMethod.getCode();
        this.method = concreteMethod;
        this.localVariables = concreteMethod.getLocalVariables();
        this.visitor = new SimVisitor();
        this.dexOptions = dexOptions;
        if (concreteMethod.isDefaultOrStaticInterfaceMethod()) {
            checkInterfaceMethodDeclaration(concreteMethod);
        }
    }

    public void simulate(ByteBlock byteBlock, Frame frame) {
        int end = byteBlock.getEnd();
        this.visitor.setFrame(frame);
        try {
            int start = byteBlock.getStart();
            while (start < end) {
                int instruction = this.code.parseInstruction(start, this.visitor);
                this.visitor.setPreviousOffset(start);
                start += instruction;
            }
        } catch (SimException e) {
            frame.annotate(e);
            throw e;
        }
    }

    public int simulate(int i, Frame frame) {
        this.visitor.setFrame(frame);
        return this.code.parseInstruction(i, this.visitor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static SimException illegalTos() {
        return new SimException("stack mismatch: illegal top-of-stack for opcode");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Type requiredArrayTypeFor(Type type, Type type2) {
        Type type3;
        Type type4 = Type.KNOWN_NULL;
        return type2 == type4 ? type.isReference() ? type4 : type.getArrayType() : (type == Type.OBJECT && type2.isArray() && type2.getComponentType().isReference()) ? type2 : (type == Type.BYTE && type2 == (type3 = Type.BOOLEAN_ARRAY)) ? type3 : type.getArrayType();
    }

    public class SimVisitor implements BytecodeArray.Visitor {
        private Frame frame = null;
        private final Machine machine;
        private int previousOffset;

        public SimVisitor() {
            this.machine = Simulator.this.machine;
        }

        public void setFrame(Frame frame) {
            if (frame == null) {
                g$$ExternalSyntheticBUOutline2.m208m("frame == null");
            } else {
                this.frame = frame;
            }
        }

        @Override // com.android.dx.cf.code.BytecodeArray.Visitor
        public void visitInvalid(int i, int i2, int i3) {
            throw new SimException("invalid opcode " + Hex.m230u1(i));
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Removed duplicated region for block: B:116:0x02b8  */
        /* JADX WARN: Removed duplicated region for block: B:138:0x034d  */
        @Override // com.android.dx.cf.code.BytecodeArray.Visitor
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void visitNoArgs(int r9, int r10, int r11, com.android.p006dx.rop.type.Type r12) {
            /*
                Method dump skipped, instruction units count: 998
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.dx.cf.code.Simulator.SimVisitor.visitNoArgs(int, int, int, com.android.dx.rop.type.Type):void");
        }

        private void checkReturnType(Type type) {
            Type returnType = this.machine.getPrototype().getReturnType();
            if (Merger.isPossiblyAssignableFrom(returnType, type)) {
                return;
            }
            Simulator.this.fail("return type mismatch: prototype indicates " + returnType.toHuman() + ", but encountered type " + type.toHuman());
        }

        /* JADX WARN: Removed duplicated region for block: B:31:0x0084  */
        /* JADX WARN: Removed duplicated region for block: B:32:0x0086  */
        @Override // com.android.dx.cf.code.BytecodeArray.Visitor
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void visitLocal(int r7, int r8, int r9, int r10, com.android.p006dx.rop.type.Type r11, int r12) {
            /*
                r6 = this;
                r0 = 54
                if (r7 != r0) goto L7
                int r1 = r8 + r9
                goto L8
            L7:
                r1 = r8
            L8:
                com.android.dx.cf.code.Simulator r2 = com.android.p006dx.p007cf.code.Simulator.this
                com.android.dx.cf.code.LocalVariableList r2 = com.android.p006dx.p007cf.code.Simulator.access$400(r2)
                com.android.dx.cf.code.LocalVariableList$Item r1 = r2.pcAndIndexToLocal(r1, r10)
                r2 = 0
                if (r1 == 0) goto L26
                com.android.dx.rop.type.Type r3 = r1.getType()
                int r4 = r3.getBasicFrameType()
                int r5 = r11.getBasicFrameType()
                if (r4 == r5) goto L27
                r3 = r11
                r1 = r2
                goto L27
            L26:
                r3 = r11
            L27:
                r4 = 21
                if (r7 == r4) goto L79
                if (r7 == r0) goto L60
                r0 = 132(0x84, float:1.85E-43)
                if (r7 == r0) goto L39
                r12 = 169(0xa9, float:2.37E-43)
                if (r7 == r12) goto L79
                r6.visitInvalid(r7, r8, r9)
                return
            L39:
                if (r1 != 0) goto L3c
                goto L40
            L3c:
                com.android.dx.rop.code.LocalItem r2 = r1.getLocalItem()
            L40:
                com.android.dx.cf.code.Machine r9 = r6.machine
                com.android.dx.cf.code.Frame r0 = r6.frame
                r9.localArg(r0, r10)
                com.android.dx.cf.code.Machine r9 = r6.machine
                r9.localTarget(r10, r3, r2)
                com.android.dx.cf.code.Machine r9 = r6.machine
                r9.auxType(r11)
                com.android.dx.cf.code.Machine r9 = r6.machine
                r9.auxIntArg(r12)
                com.android.dx.cf.code.Machine r9 = r6.machine
                com.android.dx.rop.cst.CstInteger r10 = com.android.p006dx.rop.cst.CstInteger.make(r12)
                r9.auxCstArg(r10)
                goto L8f
            L60:
                if (r1 != 0) goto L63
                goto L67
            L63:
                com.android.dx.rop.code.LocalItem r2 = r1.getLocalItem()
            L67:
                com.android.dx.cf.code.Machine r9 = r6.machine
                com.android.dx.cf.code.Frame r12 = r6.frame
                r9.popArgs(r12, r11)
                com.android.dx.cf.code.Machine r9 = r6.machine
                r9.auxType(r11)
                com.android.dx.cf.code.Machine r9 = r6.machine
                r9.localTarget(r10, r3, r2)
                goto L8f
            L79:
                com.android.dx.cf.code.Machine r9 = r6.machine
                com.android.dx.cf.code.Frame r12 = r6.frame
                r9.localArg(r12, r10)
                com.android.dx.cf.code.Machine r9 = r6.machine
                if (r1 == 0) goto L86
                r10 = 1
                goto L87
            L86:
                r10 = 0
            L87:
                r9.localInfo(r10)
                com.android.dx.cf.code.Machine r9 = r6.machine
                r9.auxType(r11)
            L8f:
                com.android.dx.cf.code.Machine r9 = r6.machine
                com.android.dx.cf.code.Frame r6 = r6.frame
                r9.run(r6, r8, r7)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.dx.cf.code.Simulator.SimVisitor.visitLocal(int, int, int, int, com.android.dx.rop.type.Type, int):void");
        }

        /* JADX WARN: Removed duplicated region for block: B:32:0x0091  */
        /* JADX WARN: Type inference fix 'apply assigned field type' failed
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
        	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
        	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
         */
        @Override // com.android.dx.cf.code.BytecodeArray.Visitor
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void visitConstant(int r4, int r5, int r6, com.android.p006dx.rop.cst.Constant r7, int r8) {
            /*
                Method dump skipped, instruction units count: 236
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.dx.cf.code.Simulator.SimVisitor.visitConstant(int, int, int, com.android.dx.rop.cst.Constant, int):void");
        }

        @Override // com.android.dx.cf.code.BytecodeArray.Visitor
        public void visitBranch(int i, int i2, int i3, int i4) {
            switch (i) {
                case 153:
                case 154:
                case 155:
                case 156:
                case 157:
                case 158:
                    this.machine.popArgs(this.frame, Type.INT);
                    this.machine.auxTargetArg(i4);
                    this.machine.run(this.frame, i2, i);
                    break;
                case 159:
                case 160:
                case 161:
                case 162:
                case 163:
                case 164:
                    Machine machine = this.machine;
                    Frame frame = this.frame;
                    Type type = Type.INT;
                    machine.popArgs(frame, type, type);
                    this.machine.auxTargetArg(i4);
                    this.machine.run(this.frame, i2, i);
                    break;
                case 165:
                case 166:
                    Machine machine2 = this.machine;
                    Frame frame2 = this.frame;
                    Type type2 = Type.OBJECT;
                    machine2.popArgs(frame2, type2, type2);
                    this.machine.auxTargetArg(i4);
                    this.machine.run(this.frame, i2, i);
                    break;
                default:
                    switch (i) {
                        case 198:
                        case 199:
                            this.machine.popArgs(this.frame, Type.OBJECT);
                            break;
                        case 200:
                        case 201:
                            break;
                        default:
                            visitInvalid(i, i2, i3);
                            break;
                    }
                    this.machine.auxTargetArg(i4);
                    this.machine.run(this.frame, i2, i);
                case 167:
                case 168:
                    this.machine.clearArgs();
                    this.machine.auxTargetArg(i4);
                    this.machine.run(this.frame, i2, i);
                    break;
            }
        }

        @Override // com.android.dx.cf.code.BytecodeArray.Visitor
        public void visitSwitch(int i, int i2, int i3, SwitchList switchList, int i4) {
            this.machine.popArgs(this.frame, Type.INT);
            this.machine.auxIntArg(i4);
            this.machine.auxSwitchArg(switchList);
            this.machine.run(this.frame, i2, i);
        }

        @Override // com.android.dx.cf.code.BytecodeArray.Visitor
        public void visitNewarray(int i, int i2, CstType cstType, ArrayList<Constant> arrayList) {
            this.machine.popArgs(this.frame, Type.INT);
            this.machine.auxInitValues(arrayList);
            this.machine.auxCstArg(cstType);
            this.machine.run(this.frame, i, 188);
        }

        @Override // com.android.dx.cf.code.BytecodeArray.Visitor
        public void setPreviousOffset(int i) {
            this.previousOffset = i;
        }

        @Override // com.android.dx.cf.code.BytecodeArray.Visitor
        public int getPreviousOffset() {
            return this.previousOffset;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkConstMethodHandleSupported(Constant constant) {
        if (this.dexOptions.apiIsSupported(28)) {
            return;
        }
        fail(String.format("invalid constant type %s requires --min-sdk-version >= %d (currently %d)", constant.typeName(), 28, Integer.valueOf(this.dexOptions.minSdkVersion)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkInvokeDynamicSupported(int i) {
        if (this.dexOptions.apiIsSupported(26)) {
            return;
        }
        fail(String.format("invalid opcode %02x - invokedynamic requires --min-sdk-version >= %d (currently %d)", Integer.valueOf(i), 26, Integer.valueOf(this.dexOptions.minSdkVersion)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkInvokeInterfaceSupported(int i, CstMethodRef cstMethodRef) {
        if (i == 185 || this.dexOptions.apiIsSupported(24)) {
            return;
        }
        DexOptions dexOptions = this.dexOptions;
        boolean zApiIsSupported = dexOptions.allowAllInterfaceMethodInvokes;
        if (i == 184) {
            zApiIsSupported &= dexOptions.apiIsSupported(21);
        }
        String str = i == 184 ? "static" : "default";
        if (zApiIsSupported) {
            warn(String.format("invoking a %s interface method %s.%s strictly requires --min-sdk-version >= %d (experimental at current API level %d)", str, cstMethodRef.getDefiningClass().toHuman(), cstMethodRef.getNat().toHuman(), 24, Integer.valueOf(this.dexOptions.minSdkVersion)));
        } else {
            fail(String.format("invoking a %s interface method %s.%s strictly requires --min-sdk-version >= %d (blocked at current API level %d)", str, cstMethodRef.getDefiningClass().toHuman(), cstMethodRef.getNat().toHuman(), 24, Integer.valueOf(this.dexOptions.minSdkVersion)));
        }
    }

    private void checkInterfaceMethodDeclaration(ConcreteMethod concreteMethod) {
        if (this.dexOptions.apiIsSupported(24)) {
            return;
        }
        warn(String.format("defining a %s interface method requires --min-sdk-version >= %d (currently %d) for interface methods: %s.%s", concreteMethod.isStaticMethod() ? "static" : "default", 24, Integer.valueOf(this.dexOptions.minSdkVersion), concreteMethod.getDefiningClass().toHuman(), concreteMethod.getNat().toHuman()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkInvokeSignaturePolymorphic(int i) {
        if (!this.dexOptions.apiIsSupported(26)) {
            fail(String.format("invoking a signature-polymorphic requires --min-sdk-version >= %d (currently %d)", 26, Integer.valueOf(this.dexOptions.minSdkVersion)));
        } else if (i != 182) {
            fail("Unsupported signature polymorphic invocation (" + ByteOps.opName(i) + ")");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fail(String str) {
        throw new SimException(String.format("ERROR in %s.%s: %s", this.method.getDefiningClass().toHuman(), this.method.getNat().toHuman(), str));
    }

    private void warn(String str) {
        this.dexOptions.err.println(String.format("WARNING in %s.%s: %s", this.method.getDefiningClass().toHuman(), this.method.getNat().toHuman(), str));
    }
}
