package androidx.camera.video.internal.config;

import android.util.Range;
import android.util.Rational;
import android.util.Size;
import androidx.camera.camera2.impl.DisplayInfoManager$$ExternalSyntheticBUOutline0;
import androidx.camera.core.DynamicRange;
import androidx.camera.core.Logger;
import androidx.camera.core.SurfaceRequest;
import androidx.camera.core.impl.EncoderProfilesProxy;
import androidx.camera.core.impl.Timebase;
import androidx.camera.video.MediaSpec;
import androidx.camera.video.VideoSpec;
import androidx.camera.video.internal.VideoValidatedEncoderProfilesProxy;
import androidx.camera.video.internal.compat.quirk.DeviceQuirks;
import androidx.camera.video.internal.compat.quirk.MediaCodecDefaultDataSpaceQuirk;
import androidx.camera.video.internal.encoder.VideoEncoderConfig;
import androidx.camera.video.internal.encoder.VideoEncoderDataSpace;
import androidx.camera.video.internal.utils.DynamicRangeUtil;
import androidx.core.util.Supplier;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.MediaController;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0010\n\u0002\u0010\"\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0010$\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J)\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\b\u0010\t\u001a\u0004\u0018\u00010\bH\u0007¢\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\u000e\u0010\u000fJE\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0007\u001a\u00020\u00062\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017H\u0007¢\u0006\u0004\b\u001b\u0010\u001cJ\u001f\u0010 \u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020\u001eH\u0007¢\u0006\u0004\b \u0010!JW\u0010+\u001a\u00020\u00182\u0006\u0010\"\u001a\u00020\u00182\u0006\u0010#\u001a\u00020\u00182\u0006\u0010$\u001a\u00020\u00182\u0006\u0010%\u001a\u00020\u00182\u0006\u0010&\u001a\u00020\u00182\u0006\u0010'\u001a\u00020\u00182\u0006\u0010(\u001a\u00020\u00182\u0006\u0010)\u001a\u00020\u00182\u0006\u0010*\u001a\u00020\u0018H\u0007¢\u0006\u0004\b+\u0010,J\u0017\u0010-\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b-\u0010\u000fJ\u001b\u00100\u001a\b\u0012\u0004\u0012\u00020\u00060/2\u0006\u0010.\u001a\u00020\r¢\u0006\u0004\b0\u00101J\u001d\u00105\u001a\u0002042\u0006\u00102\u001a\u00020\r2\u0006\u00103\u001a\u00020\u0018¢\u0006\u0004\b5\u00106J%\u0010;\u001a\u0002082\u0006\u0010\u0014\u001a\u00020\u00132\f\u00107\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017H\u0000¢\u0006\u0004\b9\u0010:R,\u0010>\u001a\u001a\u0012\u0004\u0012\u00020\r\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u0002040=0<8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b>\u0010?¨\u0006@"}, m877d2 = {"Landroidx/camera/video/internal/config/VideoConfigUtil;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "Landroidx/camera/video/MediaSpec;", "mediaSpec", "Landroidx/camera/core/DynamicRange;", "dynamicRange", "Landroidx/camera/video/internal/VideoValidatedEncoderProfilesProxy;", "encoderProfiles", "Landroidx/camera/video/internal/config/VideoMimeInfo;", "resolveVideoMimeInfo", "(Landroidx/camera/video/MediaSpec;Landroidx/camera/core/DynamicRange;Landroidx/camera/video/internal/VideoValidatedEncoderProfilesProxy;)Landroidx/camera/video/internal/config/VideoMimeInfo;", _UrlKt.FRAGMENT_ENCODE_SET, "getDynamicRangeDefaultMimeOrThrow", "(Landroidx/camera/core/DynamicRange;)Ljava/lang/String;", "videoMimeInfo", "Landroidx/camera/core/impl/Timebase;", "inputTimebase", "Landroidx/camera/video/VideoSpec;", "videoSpec", "Landroid/util/Size;", "surfaceSize", "Landroid/util/Range;", _UrlKt.FRAGMENT_ENCODE_SET, "expectedFrameRateRange", "Landroidx/camera/video/internal/encoder/VideoEncoderConfig;", "resolveVideoEncoderConfig", "(Landroidx/camera/video/internal/config/VideoMimeInfo;Landroidx/camera/core/impl/Timebase;Landroidx/camera/video/VideoSpec;Landroid/util/Size;Landroidx/camera/core/DynamicRange;Landroid/util/Range;)Landroidx/camera/video/internal/encoder/VideoEncoderConfig;", "config", _UrlKt.FRAGMENT_ENCODE_SET, "hasGlProcessing", "workaroundDataSpaceIfRequired", "(Landroidx/camera/video/internal/encoder/VideoEncoderConfig;Z)Landroidx/camera/video/internal/encoder/VideoEncoderConfig;", "baseBitrate", "actualBitDepth", "baseBitDepth", "actualFrameRate", "baseFrameRate", "actualWidth", "baseWidth", "actualHeight", "baseHeight", "scaleBitrate", "(IIIIIIIII)I", "getDynamicRangeDefaultMime", "mime", _UrlKt.FRAGMENT_ENCODE_SET, "getDynamicRangesForMime", "(Ljava/lang/String;)Ljava/util/Set;", "mimeType", "codecProfileLevel", "Landroidx/camera/video/internal/encoder/VideoEncoderDataSpace;", "mimeAndProfileToEncoderDataSpace", "(Ljava/lang/String;I)Landroidx/camera/video/internal/encoder/VideoEncoderDataSpace;", "expectedCaptureFrameRateRange", "Landroidx/camera/video/internal/config/CaptureEncodeRates;", "resolveFrameRates$camera_video", "(Landroidx/camera/video/VideoSpec;Landroid/util/Range;)Landroidx/camera/video/internal/config/CaptureEncodeRates;", "resolveFrameRates", _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, "MIME_TO_DATA_SPACE_MAP", "Ljava/util/Map;", "camera-video"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nVideoConfigUtil.kt\nKotlin\n*S Kotlin\n*F\n+ 1 VideoConfigUtil.kt\nandroidx/camera/video/internal/config/VideoConfigUtil\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,486:1\n295#2,2:487\n*S KotlinDebug\n*F\n+ 1 VideoConfigUtil.kt\nandroidx/camera/video/internal/config/VideoConfigUtil\n*L\n144#1:487,2\n*E\n"})
public final class VideoConfigUtil {
    public static final VideoConfigUtil INSTANCE = new VideoConfigUtil();
    private static final Map<String, Map<Integer, VideoEncoderDataSpace>> MIME_TO_DATA_SPACE_MAP;

    private VideoConfigUtil() {
    }

    static {
        VideoEncoderDataSpace videoEncoderDataSpace = VideoEncoderDataSpace.ENCODER_DATA_SPACE_UNSPECIFIED;
        Pair pairM884to = TuplesKt.m884to(1, videoEncoderDataSpace);
        VideoEncoderDataSpace videoEncoderDataSpace2 = VideoEncoderDataSpace.ENCODER_DATA_SPACE_BT2020_HLG;
        Pair pairM884to2 = TuplesKt.m884to(2, videoEncoderDataSpace2);
        VideoEncoderDataSpace videoEncoderDataSpace3 = VideoEncoderDataSpace.ENCODER_DATA_SPACE_BT2020_PQ;
        MIME_TO_DATA_SPACE_MAP = MapsKt.mutableMapOf(TuplesKt.m884to("video/hevc", MapsKt.mapOf(pairM884to, pairM884to2, TuplesKt.m884to(4096, videoEncoderDataSpace3), TuplesKt.m884to(8192, videoEncoderDataSpace3))), TuplesKt.m884to("video/av01", MapsKt.mapOf(TuplesKt.m884to(1, videoEncoderDataSpace), TuplesKt.m884to(2, videoEncoderDataSpace2), TuplesKt.m884to(4096, videoEncoderDataSpace3), TuplesKt.m884to(8192, videoEncoderDataSpace3))), TuplesKt.m884to("video/x-vnd.on2.vp9", MapsKt.mapOf(TuplesKt.m884to(1, videoEncoderDataSpace), TuplesKt.m884to(4, videoEncoderDataSpace2), TuplesKt.m884to(4096, videoEncoderDataSpace3), TuplesKt.m884to(16384, videoEncoderDataSpace3), TuplesKt.m884to(2, videoEncoderDataSpace), TuplesKt.m884to(8, videoEncoderDataSpace2), TuplesKt.m884to(8192, videoEncoderDataSpace3), TuplesKt.m884to(32768, videoEncoderDataSpace3))), TuplesKt.m884to("video/dolby-vision", MapsKt.mapOf(TuplesKt.m884to(256, videoEncoderDataSpace2), TuplesKt.m884to(512, VideoEncoderDataSpace.ENCODER_DATA_SPACE_BT709))));
    }

    @JvmStatic
    public static final VideoMimeInfo resolveVideoMimeInfo(MediaSpec mediaSpec, DynamicRange dynamicRange, VideoValidatedEncoderProfilesProxy encoderProfiles) {
        EncoderProfilesProxy.VideoProfileProxy next;
        if (!dynamicRange.isFullySpecified()) {
            DisplayInfoManager$$ExternalSyntheticBUOutline0.m28m("Dynamic range must be a fully specified dynamic range [provided dynamic range: ", dynamicRange, 93);
            return null;
        }
        String strOutputFormatToVideoMime = MediaSpec.INSTANCE.outputFormatToVideoMime(mediaSpec.getOutputFormat());
        if (encoderProfiles != null) {
            Set<Integer> setDynamicRangeToVideoProfileHdrFormats = DynamicRangeUtil.dynamicRangeToVideoProfileHdrFormats(dynamicRange);
            Set<Integer> setDynamicRangeToVideoProfileBitDepth = DynamicRangeUtil.dynamicRangeToVideoProfileBitDepth(dynamicRange);
            Iterator<EncoderProfilesProxy.VideoProfileProxy> it = encoderProfiles.getVideoProfiles().iterator();
            while (it.hasNext()) {
                next = it.next();
                if (setDynamicRangeToVideoProfileHdrFormats.contains(Integer.valueOf(next.getHdrFormat())) && setDynamicRangeToVideoProfileBitDepth.contains(Integer.valueOf(next.getBitDepth()))) {
                    String mediaType = next.getMediaType();
                    if (Intrinsics.areEqual(strOutputFormatToVideoMime, mediaType)) {
                        Logger.m74d("VideoConfigUtil", "MediaSpec video mime matches EncoderProfiles. Using EncoderProfiles to derive VIDEO settings [mime type: " + strOutputFormatToVideoMime + ']');
                    } else if (mediaSpec.getOutputFormat() == -1) {
                        Logger.m74d("VideoConfigUtil", "MediaSpec contains OUTPUT_FORMAT_UNSPECIFIED. Using CamcorderProfile to derive VIDEO settings [mime type: " + strOutputFormatToVideoMime + ", dynamic range: " + dynamicRange + ']');
                    }
                    strOutputFormatToVideoMime = mediaType;
                    break;
                }
            }
            next = null;
        } else {
            next = null;
        }
        if (next == null) {
            if (mediaSpec.getOutputFormat() == -1) {
                strOutputFormatToVideoMime = INSTANCE.getDynamicRangeDefaultMimeOrThrow(dynamicRange);
            }
            if (encoderProfiles == null) {
                Logger.m74d("VideoConfigUtil", "No EncoderProfiles present. May rely on fallback defaults to derive VIDEO settings [chosen mime type: " + strOutputFormatToVideoMime + ", dynamic range: " + dynamicRange + ']');
            } else {
                Logger.m74d("VideoConfigUtil", "No video EncoderProfile is compatible with requested output format and dynamic range. May rely on fallback defaults to derive VIDEO settings [chosen mime type: " + strOutputFormatToVideoMime + ", dynamic range: " + dynamicRange + ']');
            }
        }
        return new VideoMimeInfo(strOutputFormatToVideoMime, 0, next, 2, null);
    }

    private final String getDynamicRangeDefaultMimeOrThrow(DynamicRange dynamicRange) {
        String dynamicRangeDefaultMime = getDynamicRangeDefaultMime(dynamicRange);
        if (dynamicRangeDefaultMime != null) {
            return dynamicRangeDefaultMime;
        }
        throw new UnsupportedOperationException("Unsupported dynamic range: " + dynamicRange + "\nNo supported default mime type available.");
    }

    public final String getDynamicRangeDefaultMime(DynamicRange dynamicRange) {
        int encoding = dynamicRange.getEncoding();
        if (encoding == 1) {
            return MediaController.VIDEO_MIME_TYPE;
        }
        if (encoding == 3 || encoding == 4 || encoding == 5) {
            return "video/hevc";
        }
        if (encoding != 6) {
            return null;
        }
        return "video/dolby-vision";
    }

    public final Set<DynamicRange> getDynamicRangesForMime(String mime) {
        return DynamicRangeFormatComboRegistry.INSTANCE.getDynamicRangesForVideoMime(mime);
    }

    @JvmStatic
    public static final VideoEncoderConfig resolveVideoEncoderConfig(VideoMimeInfo videoMimeInfo, Timebase inputTimebase, VideoSpec videoSpec, Size surfaceSize, DynamicRange dynamicRange, Range<Integer> expectedFrameRateRange) {
        Supplier videoEncoderConfigDefaultResolver;
        if (videoMimeInfo.getCompatibleVideoProfile() != null) {
            videoEncoderConfigDefaultResolver = new VideoEncoderConfigVideoProfileResolver(videoMimeInfo.getMimeType(), inputTimebase, videoSpec, surfaceSize, videoMimeInfo.getCompatibleVideoProfile(), dynamicRange, expectedFrameRateRange);
        } else {
            videoEncoderConfigDefaultResolver = new VideoEncoderConfigDefaultResolver(videoMimeInfo.getMimeType(), inputTimebase, videoSpec, surfaceSize, dynamicRange, expectedFrameRateRange);
        }
        return (VideoEncoderConfig) videoEncoderConfigDefaultResolver.get();
    }

    @JvmStatic
    public static final VideoEncoderConfig workaroundDataSpaceIfRequired(VideoEncoderConfig config, boolean hasGlProcessing) {
        if (!Intrinsics.areEqual(config.getDataSpace(), VideoEncoderDataSpace.ENCODER_DATA_SPACE_UNSPECIFIED)) {
            return config;
        }
        MediaCodecDefaultDataSpaceQuirk mediaCodecDefaultDataSpaceQuirk = (MediaCodecDefaultDataSpaceQuirk) DeviceQuirks.get(MediaCodecDefaultDataSpaceQuirk.class);
        if (!hasGlProcessing || mediaCodecDefaultDataSpaceQuirk == null) {
            return config;
        }
        return config.toBuilder().setDataSpace(mediaCodecDefaultDataSpaceQuirk.getSuggestedDataSpace()).build();
    }

    @JvmStatic
    public static final int scaleBitrate(int baseBitrate, int actualBitDepth, int baseBitDepth, int actualFrameRate, int baseFrameRate, int actualWidth, int baseWidth, int actualHeight, int baseHeight) {
        String str;
        int iDoubleValue = (int) (((double) baseBitrate) * new Rational(actualBitDepth, baseBitDepth).doubleValue() * new Rational(actualFrameRate, baseFrameRate).doubleValue() * new Rational(actualWidth, baseWidth).doubleValue() * new Rational(actualHeight, baseHeight).doubleValue());
        if (Logger.isDebugEnabled("VideoConfigUtil")) {
            str = "Base Bitrate(" + baseBitrate + "bps) * Bit Depth Ratio (" + actualBitDepth + " / " + baseBitDepth + ") * Frame Rate Ratio(" + actualFrameRate + " / " + baseFrameRate + ") * Width Ratio(" + actualWidth + " / " + baseWidth + ") * Height Ratio(" + actualHeight + " / " + baseHeight + ") = " + iDoubleValue;
        } else {
            str = _UrlKt.FRAGMENT_ENCODE_SET;
        }
        Logger.m74d("VideoConfigUtil", str);
        return iDoubleValue;
    }

    public final VideoEncoderDataSpace mimeAndProfileToEncoderDataSpace(String mimeType, int codecProfileLevel) {
        VideoEncoderDataSpace videoEncoderDataSpace;
        Map<Integer, VideoEncoderDataSpace> map = MIME_TO_DATA_SPACE_MAP.get(mimeType);
        if (map != null && (videoEncoderDataSpace = map.get(Integer.valueOf(codecProfileLevel))) != null) {
            return videoEncoderDataSpace;
        }
        Logger.m79w("VideoConfigUtil", "Unsupported mime type " + mimeType + " or profile level " + codecProfileLevel + ". Data space is unspecified.");
        return VideoEncoderDataSpace.ENCODER_DATA_SPACE_UNSPECIFIED;
    }

    public final CaptureEncodeRates resolveFrameRates$camera_video(VideoSpec videoSpec, Range<Integer> expectedCaptureFrameRateRange) {
        String strValueOf;
        Range<Integer> range = SurfaceRequest.FRAME_RATE_RANGE_UNSPECIFIED;
        int iIntValue = Intrinsics.areEqual(expectedCaptureFrameRateRange, range) ? 30 : ((Number) expectedCaptureFrameRateRange.getUpper()).intValue();
        int encodeFrameRate = videoSpec.getEncodeFrameRate() != 0 ? videoSpec.getEncodeFrameRate() : iIntValue;
        StringBuilder sb = new StringBuilder("Resolved capture/encode frame rate ");
        sb.append(iIntValue);
        sb.append("fps/");
        sb.append(encodeFrameRate);
        sb.append("fps, [Expected operating range: ");
        if (Intrinsics.areEqual(expectedCaptureFrameRateRange, range)) {
            strValueOf = "<UNSPECIFIED>";
        } else {
            strValueOf = String.valueOf(expectedCaptureFrameRateRange);
        }
        sb.append(strValueOf);
        sb.append(']');
        Logger.m74d("VideoConfigUtil", sb.toString());
        return new CaptureEncodeRates(iIntValue, encodeFrameRate);
    }
}
