package org.telegram.p035ui.Components;

import android.graphics.Bitmap;
import android.os.Build;
import org.telegram.messenger.AnimatedFileDrawableStream;

/* JADX INFO: loaded from: classes7.dex */
public class AnimatedFileNative {
    private static native long nCreateDecoder(String str, int[] iArr, int i, long j, Object obj, boolean z);

    private static native void nDestroyDecoder(long j);

    private static native int nGetFrameAtTime(long j, long j2, Bitmap bitmap, int[] iArr, AnimatedFileDrawable animatedFileDrawable);

    private static native int nGetVideoFrame(long j, Bitmap bitmap, int[] iArr, boolean z, float f, float f2, boolean z2, AnimatedFileDrawable animatedFileDrawable);

    private static native void nGetVideoInfo(int i, String str, int[] iArr, long j);

    private static native void nPrepareToSeek(long j);

    private static native void nSeekToMs(long j, long j2, int[] iArr, boolean z, AnimatedFileDrawable animatedFileDrawable);

    private static native void nStopDecoder(long j);

    public static long createDecoder(String str, int[] iArr, int i, long j, AnimatedFileDrawableStream animatedFileDrawableStream, boolean z) {
        return nCreateDecoder(str, iArr, i, j, animatedFileDrawableStream, z);
    }

    public static void destroyDecoder(long j) {
        nDestroyDecoder(j);
    }

    public static void stopDecoder(long j) {
        nStopDecoder(j);
    }

    public static int getVideoFrame(long j, Bitmap bitmap, int[] iArr, boolean z, float f, float f2, boolean z2, AnimatedFileDrawable animatedFileDrawable) {
        return nGetVideoFrame(j, bitmap, iArr, z, f, f2, z2, animatedFileDrawable);
    }

    public static void seekToMs(long j, long j2, int[] iArr, boolean z, AnimatedFileDrawable animatedFileDrawable) {
        nSeekToMs(j, j2, iArr, z, animatedFileDrawable);
    }

    public static int getFrameAtTime(long j, long j2, Bitmap bitmap, int[] iArr, AnimatedFileDrawable animatedFileDrawable) {
        return nGetFrameAtTime(j, j2, bitmap, iArr, animatedFileDrawable);
    }

    public static void prepareToSeek(long j) {
        nPrepareToSeek(j);
    }

    public static void getVideoInfo(String str, int[] iArr, long j) {
        nGetVideoInfo(Build.VERSION.SDK_INT, str, iArr, j);
    }
}
