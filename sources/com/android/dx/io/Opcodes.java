package com.android.dx.io;

import com.sun.jna.Function;

/* JADX INFO: loaded from: classes4.dex */
public abstract class Opcodes {
    public static boolean isValidShape(int i) {
        int i2;
        if (i < -1) {
            return false;
        }
        return i == -1 || (i2 = i & Function.USE_VARARGS) == 0 || i2 == 255 || (i & 65280) == 0;
    }
}
