package com.android.dx.dex;

import com.android.dex.DexFormat;
import java.io.PrintStream;

/* JADX INFO: loaded from: classes4.dex */
public final class DexOptions {
    public boolean ALIGN_64BIT_REGS_IN_OUTPUT_FINISHER = true;
    public int minSdkVersion = 13;
    public boolean forceJumbo = false;
    public boolean allowAllInterfaceMethodInvokes = false;
    public final PrintStream err = System.err;

    public String getMagic() {
        return DexFormat.apiToMagic(this.minSdkVersion);
    }

    public boolean apiIsSupported(int i) {
        return this.minSdkVersion >= i;
    }
}
