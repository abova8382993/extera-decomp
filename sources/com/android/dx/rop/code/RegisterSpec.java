package com.android.dx.rop.code;

import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeBearer;
import com.android.dx.util.ToHuman;
import de.robv.android.xposed.callbacks.XCallback;
import j$.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes4.dex */
public final class RegisterSpec implements TypeBearer, ToHuman, Comparable {
    private final int reg;
    private final TypeBearer type;
    private static final ConcurrentHashMap theInterns = new ConcurrentHashMap(XCallback.PRIORITY_HIGHEST, 0.75f);
    private static final ThreadLocal theInterningItem = new ThreadLocal() { // from class: com.android.dx.rop.code.RegisterSpec.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public ForComparison initialValue() {
            return new ForComparison();
        }
    };

    public LocalItem getLocalItem() {
        return null;
    }

    private static RegisterSpec intern(int i, TypeBearer typeBearer, LocalItem localItem) {
        ForComparison forComparison = (ForComparison) theInterningItem.get();
        forComparison.set(i, typeBearer, localItem);
        ConcurrentHashMap concurrentHashMap = theInterns;
        RegisterSpec registerSpec = (RegisterSpec) concurrentHashMap.get(forComparison);
        if (registerSpec == null) {
            registerSpec = forComparison.toRegisterSpec();
            RegisterSpec registerSpec2 = (RegisterSpec) concurrentHashMap.putIfAbsent(registerSpec, registerSpec);
            if (registerSpec2 != null) {
                return registerSpec2;
            }
        }
        return registerSpec;
    }

    public static RegisterSpec make(int i, TypeBearer typeBearer) {
        return intern(i, typeBearer, null);
    }

    public static RegisterSpec makeLocalOptional(int i, TypeBearer typeBearer, LocalItem localItem) {
        return intern(i, typeBearer, localItem);
    }

    public static String regString(int i) {
        return "v" + i;
    }

    private RegisterSpec(int i, TypeBearer typeBearer, LocalItem localItem) {
        if (i < 0) {
            throw new IllegalArgumentException("reg < 0");
        }
        if (typeBearer == null) {
            throw new NullPointerException("type == null");
        }
        this.reg = i;
        this.type = typeBearer;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RegisterSpec)) {
            if (!(obj instanceof ForComparison)) {
                return false;
            }
            ForComparison forComparison = (ForComparison) obj;
            int i = forComparison.reg;
            TypeBearer typeBearer = forComparison.type;
            ForComparison.access$300(forComparison);
            return equals(i, typeBearer, null);
        }
        RegisterSpec registerSpec = (RegisterSpec) obj;
        return equals(registerSpec.reg, registerSpec.type, null);
    }

    public boolean equalsUsingSimpleType(RegisterSpec registerSpec) {
        return matchesVariable(registerSpec) && this.reg == registerSpec.reg;
    }

    public boolean matchesVariable(RegisterSpec registerSpec) {
        return registerSpec != null && this.type.getType().equals(registerSpec.type.getType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean equals(int i, TypeBearer typeBearer, LocalItem localItem) {
        return this.reg == i && this.type.equals(typeBearer);
    }

    @Override // java.lang.Comparable
    public int compareTo(RegisterSpec registerSpec) {
        int iCompareTo;
        int i = this.reg;
        int i2 = registerSpec.reg;
        if (i < i2) {
            return -1;
        }
        if (i > i2) {
            return 1;
        }
        if (this == registerSpec || (iCompareTo = this.type.getType().compareTo(registerSpec.type.getType())) == 0) {
            return 0;
        }
        return iCompareTo;
    }

    public int hashCode() {
        return hashCodeOf(this.reg, this.type, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int hashCodeOf(int i, TypeBearer typeBearer, LocalItem localItem) {
        return (typeBearer.hashCode() * 31) + i;
    }

    public String toString() {
        return toString0(false);
    }

    @Override // com.android.dx.util.ToHuman
    public String toHuman() {
        return toString0(true);
    }

    @Override // com.android.dx.rop.type.TypeBearer
    public Type getType() {
        return this.type.getType();
    }

    @Override // com.android.dx.rop.type.TypeBearer
    public final int getBasicType() {
        return this.type.getBasicType();
    }

    @Override // com.android.dx.rop.type.TypeBearer
    public final int getBasicFrameType() {
        return this.type.getBasicFrameType();
    }

    public int getReg() {
        return this.reg;
    }

    public int getNextReg() {
        return this.reg + getCategory();
    }

    public int getCategory() {
        return this.type.getType().getCategory();
    }

    public boolean isCategory2() {
        return this.type.getType().isCategory2();
    }

    public String regString() {
        return regString(this.reg);
    }

    public RegisterSpec withReg(int i) {
        return this.reg == i ? this : makeLocalOptional(i, this.type, null);
    }

    public RegisterSpec withType(TypeBearer typeBearer) {
        return makeLocalOptional(this.reg, typeBearer, null);
    }

    public RegisterSpec withOffset(int i) {
        return i == 0 ? this : withReg(this.reg + i);
    }

    public boolean isEvenRegister() {
        return (getReg() & 1) == 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0037  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String toString0(boolean r4) {
        /*
            r3 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r1 = 40
            r0.<init>(r1)
            java.lang.String r1 = r3.regString()
            r0.append(r1)
            java.lang.String r1 = ":"
            r0.append(r1)
            com.android.dx.rop.type.TypeBearer r1 = r3.type
            com.android.dx.rop.type.Type r1 = r1.getType()
            r0.append(r1)
            com.android.dx.rop.type.TypeBearer r2 = r3.type
            if (r1 == r2) goto L4c
            java.lang.String r1 = "="
            r0.append(r1)
            if (r4 == 0) goto L37
            com.android.dx.rop.type.TypeBearer r1 = r3.type
            boolean r2 = r1 instanceof com.android.dx.rop.cst.CstString
            if (r2 == 0) goto L37
            com.android.dx.rop.cst.CstString r1 = (com.android.dx.rop.cst.CstString) r1
            java.lang.String r4 = r1.toQuoted()
            r0.append(r4)
            goto L4c
        L37:
            if (r4 == 0) goto L47
            com.android.dx.rop.type.TypeBearer r4 = r3.type
            boolean r1 = r4 instanceof com.android.dx.rop.cst.Constant
            if (r1 == 0) goto L47
            java.lang.String r4 = r4.toHuman()
            r0.append(r4)
            goto L4c
        L47:
            com.android.dx.rop.type.TypeBearer r4 = r3.type
            r0.append(r4)
        L4c:
            java.lang.String r4 = r0.toString()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.dx.rop.code.RegisterSpec.toString0(boolean):java.lang.String");
    }

    private static class ForComparison {
        private int reg;
        private TypeBearer type;

        private ForComparison() {
        }

        static /* synthetic */ LocalItem access$300(ForComparison forComparison) {
            forComparison.getClass();
            return null;
        }

        public void set(int i, TypeBearer typeBearer, LocalItem localItem) {
            this.reg = i;
            this.type = typeBearer;
        }

        public RegisterSpec toRegisterSpec() {
            return new RegisterSpec(this.reg, this.type, null);
        }

        public boolean equals(Object obj) {
            if (obj instanceof RegisterSpec) {
                return ((RegisterSpec) obj).equals(this.reg, this.type, null);
            }
            return false;
        }

        public int hashCode() {
            return RegisterSpec.hashCodeOf(this.reg, this.type, null);
        }
    }
}
