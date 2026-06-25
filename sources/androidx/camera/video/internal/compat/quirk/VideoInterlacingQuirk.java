package androidx.camera.video.internal.compat.quirk;

import android.annotation.SuppressLint;
import android.os.Build;
import androidx.camera.core.internal.compat.quirk.SurfaceProcessingQuirk;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.text.StringsKt;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\bÇ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\f\u001a\u00020\nH\u0007R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u000b¨\u0006\r"}, m877d2 = {"Landroidx/camera/video/internal/compat/quirk/VideoInterlacingQuirk;", "Landroidx/camera/core/internal/compat/quirk/SurfaceProcessingQuirk;", "<init>", "()V", "DEVICE_MODELS", _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, "getDEVICE_MODELS", "()Ljava/util/List;", "isSamsungS6", _UrlKt.FRAGMENT_ENCODE_SET, "()Z", "load", "camera-video"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SuppressLint({"CameraXQuirksClassDetector"})
public final class VideoInterlacingQuirk implements SurfaceProcessingQuirk {
    public static final VideoInterlacingQuirk INSTANCE = new VideoInterlacingQuirk();

    private VideoInterlacingQuirk() {
    }

    private final List<String> getDEVICE_MODELS() {
        return CollectionsKt.listOf("SM-N9208");
    }

    private final boolean isSamsungS6() {
        return StringsKt.equals(Build.BRAND, "Samsung", true) && StringsKt.startsWith(Build.PRODUCT, "zeroflte", true);
    }

    @JvmStatic
    public static final boolean load() {
        VideoInterlacingQuirk videoInterlacingQuirk = INSTANCE;
        return videoInterlacingQuirk.getDEVICE_MODELS().contains(Build.MODEL.toUpperCase(Locale.getDefault())) || videoInterlacingQuirk.isSamsungS6();
    }
}
