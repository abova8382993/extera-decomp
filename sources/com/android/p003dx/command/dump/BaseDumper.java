package com.android.p003dx.command.dump;

import com.android.p003dx.dex.DexOptions;
import com.android.p003dx.p004cf.code.ConcreteMethod;
import com.android.p003dx.p004cf.iface.Member;
import com.android.p003dx.p004cf.iface.ParseObserver;
import com.android.p003dx.util.ByteArray;
import com.android.p003dx.util.Hex;
import com.android.p003dx.util.IndentingWriter;
import com.android.p003dx.util.TwoColumnOutput;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseDumper implements ParseObserver {
    protected Args args;
    private final byte[] bytes;
    protected final DexOptions dexOptions;
    private final String filePath;
    private final int hexCols;
    private int indent;
    private final PrintStream out;
    private final boolean rawBytes;
    private int readBytes;
    private String separator;
    private final boolean strictParse;
    private final int width;

    @Override // com.android.p003dx.p004cf.iface.ParseObserver
    public void endParsingMember(ByteArray byteArray, int i, String str, String str2, Member member) {
    }

    @Override // com.android.p003dx.p004cf.iface.ParseObserver
    public void startParsingMember(ByteArray byteArray, int i, String str, String str2) {
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x003b A[PHI: r2
  0x003b: PHI (r2v8 int) = (r2v6 int), (r2v7 int) binds: [B:10:0x0039, B:13:0x003f] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public BaseDumper(byte[] r1, java.io.PrintStream r2, java.lang.String r3, com.android.p003dx.command.dump.Args r4) {
        /*
            r0 = this;
            r0.<init>()
            r0.bytes = r1
            boolean r1 = r4.rawBytes
            r0.rawBytes = r1
            r0.out = r2
            int r2 = r4.width
            if (r2 > 0) goto L11
            r2 = 79
        L11:
            r0.width = r2
            r0.filePath = r3
            boolean r3 = r4.strictParse
            r0.strictParse = r3
            r3 = 0
            r0.indent = r3
            if (r1 == 0) goto L21
            java.lang.String r1 = "|"
            goto L23
        L21:
            java.lang.String r1 = ""
        L23:
            r0.separator = r1
            r0.readBytes = r3
            r0.args = r4
            com.android.dx.dex.DexOptions r1 = new com.android.dx.dex.DexOptions
            r1.<init>()
            r0.dexOptions = r1
            int r2 = r2 + (-5)
            int r2 = r2 / 15
            int r2 = r2 + 1
            r1 = r2 & (-2)
            r2 = 6
            if (r1 >= r2) goto L3d
        L3b:
            r1 = r2
            goto L42
        L3d:
            r2 = 10
            if (r1 <= r2) goto L42
            goto L3b
        L42:
            r0.hexCols = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p003dx.command.dump.BaseDumper.<init>(byte[], java.io.PrintStream, java.lang.String, com.android.dx.command.dump.Args):void");
    }

    static int computeParamWidth(ConcreteMethod concreteMethod, boolean z) {
        return concreteMethod.getEffectiveDescriptor().getParameterTypes().getWordCount();
    }

    @Override // com.android.p003dx.p004cf.iface.ParseObserver
    public void changeIndent(int i) {
        this.indent += i;
        this.separator = this.rawBytes ? "|" : _UrlKt.FRAGMENT_ENCODE_SET;
        for (int i2 = 0; i2 < this.indent; i2++) {
            this.separator += "  ";
        }
    }

    @Override // com.android.p003dx.p004cf.iface.ParseObserver
    public void parsed(ByteArray byteArray, int i, int i2, String str) {
        print(twoColumns(getRawBytes() ? hexDump(byteArray.underlyingOffset(i), i2) : _UrlKt.FRAGMENT_ENCODE_SET, str));
        this.readBytes += i2;
    }

    protected final int getReadBytes() {
        return this.readBytes;
    }

    protected final byte[] getBytes() {
        return this.bytes;
    }

    protected final String getFilePath() {
        return this.filePath;
    }

    protected final boolean getStrictParse() {
        return this.strictParse;
    }

    protected final void print(String str) {
        this.out.print(str);
    }

    protected final void println(String str) {
        this.out.println(str);
    }

    protected final boolean getRawBytes() {
        return this.rawBytes;
    }

    protected final int getWidth1() {
        if (!this.rawBytes) {
            return 0;
        }
        int i = this.hexCols;
        return (i * 2) + 5 + (i / 2);
    }

    protected final int getWidth2() {
        return (this.width - (this.rawBytes ? getWidth1() + 1 : 0)) - (this.indent * 2);
    }

    protected final String hexDump(int i, int i2) {
        return Hex.dump(this.bytes, i, i2, i, this.hexCols, 4);
    }

    protected final String twoColumns(String str, String str2) {
        int width1 = getWidth1();
        int width2 = getWidth2();
        try {
            if (width1 == 0) {
                int length = str2.length();
                StringWriter stringWriter = new StringWriter(length * 2);
                IndentingWriter indentingWriter = new IndentingWriter(stringWriter, width2, this.separator);
                indentingWriter.write(str2);
                if (length == 0 || str2.charAt(length - 1) != '\n') {
                    indentingWriter.write(10);
                }
                indentingWriter.flush();
                return stringWriter.toString();
            }
            return TwoColumnOutput.toString(str, width1, this.separator, str2, width2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
