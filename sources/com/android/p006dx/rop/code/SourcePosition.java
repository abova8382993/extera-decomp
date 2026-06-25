package com.android.p006dx.rop.code;

import com.android.p006dx.rop.cst.CstString;
import com.android.p006dx.util.Hex;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes4.dex */
public final class SourcePosition {
    public static final SourcePosition NO_INFO = new SourcePosition(null, -1, -1);
    private final int address;
    private final int line;
    private final CstString sourceFile;

    public SourcePosition(CstString cstString, int i, int i2) {
        if (i < -1) {
            g$$ExternalSyntheticBUOutline1.m207m("address < -1");
            throw null;
        }
        if (i2 < -1) {
            g$$ExternalSyntheticBUOutline1.m207m("line < -1");
            throw null;
        }
        this.sourceFile = cstString;
        this.address = i;
        this.line = i2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(50);
        CstString cstString = this.sourceFile;
        if (cstString != null) {
            sb.append(cstString.toHuman());
            sb.append(":");
        }
        int i = this.line;
        if (i >= 0) {
            sb.append(i);
        }
        sb.append('@');
        int i2 = this.address;
        if (i2 < 0) {
            sb.append("????");
        } else {
            sb.append(Hex.m231u2(i2));
        }
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SourcePosition)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        SourcePosition sourcePosition = (SourcePosition) obj;
        return this.address == sourcePosition.address && sameLineAndFile(sourcePosition);
    }

    public int hashCode() {
        return this.sourceFile.hashCode() + this.address + this.line;
    }

    public boolean sameLine(SourcePosition sourcePosition) {
        return this.line == sourcePosition.line;
    }

    public boolean sameLineAndFile(SourcePosition sourcePosition) {
        if (this.line != sourcePosition.line) {
            return false;
        }
        CstString cstString = this.sourceFile;
        CstString cstString2 = sourcePosition.sourceFile;
        if (cstString != cstString2) {
            return cstString != null && cstString.equals(cstString2);
        }
        return true;
    }

    public CstString getSourceFile() {
        return this.sourceFile;
    }

    public int getAddress() {
        return this.address;
    }

    public int getLine() {
        return this.line;
    }
}
