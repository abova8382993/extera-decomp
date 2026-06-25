package androidx.camera.video.internal.compat.quirk;

import android.annotation.SuppressLint;
import android.os.Build;
import androidx.camera.core.impl.Quirk;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.text.StringsKt;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\bÇ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0007R\u0014\u0010\u0006\u001a\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, m877d2 = {"Landroidx/camera/video/internal/compat/quirk/GLProcessingStuckOnCodecFlushQuirk;", "Landroidx/camera/core/impl/Quirk;", "<init>", "()V", "load", _UrlKt.FRAGMENT_ENCODE_SET, "isPositivoTwist2Pro", "()Z", "camera-video"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SuppressLint({"CameraXQuirksClassDetector"})
public final class GLProcessingStuckOnCodecFlushQuirk implements Quirk {
    public static final GLProcessingStuckOnCodecFlushQuirk INSTANCE = new GLProcessingStuckOnCodecFlushQuirk();

    private GLProcessingStuckOnCodecFlushQuirk() {
    }

    @JvmStatic
    public static final boolean load() {
        return INSTANCE.isPositivoTwist2Pro();
    }

    private final boolean isPositivoTwist2Pro() {
        return StringsKt.equals("positivo", Build.BRAND, true) && StringsKt.equals("twist 2 pro", Build.MODEL, true);
    }
}
