package com.android.dx.util;

/* JADX INFO: loaded from: classes4.dex */
public abstract class Bits {
    public static int[] makeBitSet(int i) {
        return new int[(i + 31) >> 5];
    }

    public static boolean get(int[] iArr, int i) {
        return (iArr[i >> 5] & (1 << (i & 31))) != 0;
    }

    public static void set(int[] iArr, int i) {
        int i2 = i >> 5;
        iArr[i2] = (1 << (i & 31)) | iArr[i2];
    }

    public static void clear(int[] iArr, int i) {
        int i2 = i >> 5;
        iArr[i2] = (~(1 << (i & 31))) & iArr[i2];
    }

    public static int findFirst(int[] iArr, int i) {
        int iFindFirst;
        int length = iArr.length;
        int i2 = i & 31;
        int i3 = i >> 5;
        while (i3 < length) {
            int i4 = iArr[i3];
            if (i4 != 0 && (iFindFirst = findFirst(i4, i2)) >= 0) {
                return (i3 << 5) + iFindFirst;
            }
            i3++;
            i2 = 0;
        }
        return -1;
    }

    public static int findFirst(int i, int i2) {
        int iNumberOfTrailingZeros = Integer.numberOfTrailingZeros(i & (~((1 << i2) - 1)));
        if (iNumberOfTrailingZeros == 32) {
            return -1;
        }
        return iNumberOfTrailingZeros;
    }
}
