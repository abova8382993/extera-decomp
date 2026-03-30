package androidx.camera.camera2.internal;

import androidx.camera.core.Logger;

/* JADX INFO: loaded from: classes3.dex */
public abstract class LensFacingUtil {
    public static int getCameraSelectorLensFacing(int i) {
        if (i == 0) {
            return 0;
        }
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                Logger.w("LensFacingUtil", "The given lens facing integer: " + i + " can not be recognized.");
                return -1;
            }
        }
        return i2;
    }
}
