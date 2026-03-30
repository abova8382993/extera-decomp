package androidx.camera.core.internal.compat;

import android.media.ImageWriter;
import android.view.Surface;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ImageWriterCompat {
    public static ImageWriter newInstance(Surface surface, int i) {
        return ImageWriterCompatApi23Impl.newInstance(surface, i);
    }
}
