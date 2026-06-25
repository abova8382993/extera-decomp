package androidx.camera.video.internal.utils;

import android.util.Range;
import android.util.Size;
import androidx.camera.core.impl.EncoderProfilesProxy;
import androidx.camera.video.internal.config.VideoConfigUtil;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J&\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0007J\u0014\u0010\f\u001a\u0004\u0018\u00010\u00052\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0007¨\u0006\u000f"}, m877d2 = {"Landroidx/camera/video/internal/utils/EncoderProfilesUtil;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "deriveVideoProfile", "Landroidx/camera/core/impl/EncoderProfilesProxy$VideoProfileProxy;", "baseVideoProfile", "newResolution", "Landroid/util/Size;", "bitrateRangeToClamp", "Landroid/util/Range;", _UrlKt.FRAGMENT_ENCODE_SET, "getFirstVideoProfile", "encoderProfiles", "Landroidx/camera/core/impl/EncoderProfilesProxy;", "camera-video"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
public final class EncoderProfilesUtil {
    public static final EncoderProfilesUtil INSTANCE = new EncoderProfilesUtil();

    private EncoderProfilesUtil() {
    }

    @JvmStatic
    public static final EncoderProfilesProxy.VideoProfileProxy deriveVideoProfile(EncoderProfilesProxy.VideoProfileProxy baseVideoProfile, Size newResolution, Range<Integer> bitrateRangeToClamp) {
        return EncoderProfilesProxy.VideoProfileProxy.create(baseVideoProfile.getCodec(), baseVideoProfile.getMediaType(), ((Number) bitrateRangeToClamp.clamp(Integer.valueOf(VideoConfigUtil.scaleBitrate(baseVideoProfile.getBitrate(), baseVideoProfile.getBitDepth(), baseVideoProfile.getBitDepth(), baseVideoProfile.getFrameRate(), baseVideoProfile.getFrameRate(), newResolution.getWidth(), baseVideoProfile.getWidth(), newResolution.getHeight(), baseVideoProfile.getHeight())))).intValue(), baseVideoProfile.getFrameRate(), newResolution.getWidth(), newResolution.getHeight(), baseVideoProfile.getProfile(), baseVideoProfile.getBitDepth(), baseVideoProfile.getChromaSubsampling(), baseVideoProfile.getHdrFormat());
    }

    @JvmStatic
    public static final EncoderProfilesProxy.VideoProfileProxy getFirstVideoProfile(EncoderProfilesProxy encoderProfiles) {
        List<EncoderProfilesProxy.VideoProfileProxy> videoProfiles;
        if (encoderProfiles == null || (videoProfiles = encoderProfiles.getVideoProfiles()) == null) {
            return null;
        }
        return (EncoderProfilesProxy.VideoProfileProxy) CollectionsKt.firstOrNull((List) videoProfiles);
    }
}
