package androidx.camera.video.internal.workaround;

import android.util.Size;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.EncoderProfilesProvider;
import androidx.camera.core.impl.EncoderProfilesProxy;
import androidx.camera.video.Quality;
import androidx.camera.video.internal.encoder.VideoEncoderInfo;
import com.sun.jna.Native$$ExternalSyntheticBUOutline5;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.MediaController;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010%\n\u0002\b\u0004\u0018\u0000 F2\u00020\u0001:\u0001FB%\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0004\b\t\u0010\nJ\u0019\u0010\u000e\u001a\u0004\u0018\u00010\r2\u0006\u0010\f\u001a\u00020\u000bH\u0002¢\u0006\u0004\b\u000e\u0010\u000fJ\u0019\u0010\u0010\u001a\u0004\u0018\u00010\r2\u0006\u0010\f\u001a\u00020\u000bH\u0002¢\u0006\u0004\b\u0010\u0010\u000fJ\u0019\u0010\u0012\u001a\u0004\u0018\u00010\u00112\u0006\u0010\f\u001a\u00020\u000bH\u0002¢\u0006\u0004\b\u0012\u0010\u0013J)\u0010\u0017\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u000bH\u0002¢\u0006\u0004\b\u0017\u0010\u0018J3\u0010\u001e\u001a\u00020\r2\b\b\u0002\u0010\u0019\u001a\u00020\u000b2\b\b\u0002\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u001cH\u0002¢\u0006\u0004\b\u001e\u0010\u001fJm\u0010(\u001a\u00020\u00112\b\b\u0002\u0010 \u001a\u00020\u000b2\b\b\u0002\u0010\"\u001a\u00020!2\u0006\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u000b2\b\b\u0002\u0010#\u001a\u00020\u000b2\b\b\u0002\u0010$\u001a\u00020\u000b2\b\b\u0002\u0010%\u001a\u00020\u000b2\b\b\u0002\u0010&\u001a\u00020\u000b2\b\b\u0002\u0010'\u001a\u00020\u000bH\u0002¢\u0006\u0004\b(\u0010)JK\u0010-\u001a\u00020\u001c2\b\b\u0002\u0010 \u001a\u00020\u000b2\b\b\u0002\u0010\"\u001a\u00020!2\b\b\u0002\u0010*\u001a\u00020\u000b2\b\b\u0002\u0010+\u001a\u00020\u000b2\b\b\u0002\u0010,\u001a\u00020\u000b2\b\b\u0002\u0010$\u001a\u00020\u000bH\u0002¢\u0006\u0004\b-\u0010.J\u0013\u0010/\u001a\u00020\u000b*\u00020\u0005H\u0002¢\u0006\u0004\b/\u00100J#\u00102\u001a\u0004\u0018\u000101*\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\f\u001a\u00020\u000bH\u0002¢\u0006\u0004\b2\u00103J\u0017\u00105\u001a\u0002042\u0006\u0010\f\u001a\u00020\u000bH\u0016¢\u0006\u0004\b5\u00106J\u0019\u00107\u001a\u0004\u0018\u00010\r2\u0006\u0010\f\u001a\u00020\u000bH\u0016¢\u0006\u0004\b7\u0010\u000fR\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0003\u00108R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0006\u00109R\u0014\u0010\b\u001a\u00020\u00078\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\b\u0010:R=\u0010B\u001a$\u0012\f\u0012\n =*\u0004\u0018\u00010<0< =*\u0010\u0012\f\u0012\n =*\u0004\u0018\u00010<0<0\u00040;8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b>\u0010?\u001a\u0004\b@\u0010AR\"\u0010D\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\r0C8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bD\u0010E¨\u0006G"}, m877d2 = {"Landroidx/camera/video/internal/workaround/DefaultEncoderProfilesProvider;", "Landroidx/camera/core/impl/EncoderProfilesProvider;", "Landroidx/camera/core/impl/CameraInfoInternal;", "cameraInfo", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/video/Quality;", "targetQualities", "Landroidx/camera/video/internal/encoder/VideoEncoderInfo$Finder;", "videoEncoderInfoFinder", "<init>", "(Landroidx/camera/core/impl/CameraInfoInternal;Ljava/util/List;Landroidx/camera/video/internal/encoder/VideoEncoderInfo$Finder;)V", _UrlKt.FRAGMENT_ENCODE_SET, "quality", "Landroidx/camera/core/impl/EncoderProfilesProxy;", "getProfileInternal", "(I)Landroidx/camera/core/impl/EncoderProfilesProxy;", "generateEncoderProfiles", "Landroidx/camera/core/impl/EncoderProfilesProxy$VideoProfileProxy;", "generateVideoProfiles", "(I)Landroidx/camera/core/impl/EncoderProfilesProxy$VideoProfileProxy;", "width", "height", "bitrate", "resolveVideoProfile", "(III)Landroidx/camera/core/impl/EncoderProfilesProxy$VideoProfileProxy;", "defaultDurationSeconds", "recommendedFileFormat", "videoProfile", "Landroidx/camera/core/impl/EncoderProfilesProxy$AudioProfileProxy;", "audioProfile", "createDefaultEncoderProfiles", "(IILandroidx/camera/core/impl/EncoderProfilesProxy$VideoProfileProxy;Landroidx/camera/core/impl/EncoderProfilesProxy$AudioProfileProxy;)Landroidx/camera/core/impl/EncoderProfilesProxy;", "codec", _UrlKt.FRAGMENT_ENCODE_SET, "mimeType", "frameRate", "profile", "bitDepth", "chromaSubsampling", "hdrFormat", "createDefaultVideoProfile", "(ILjava/lang/String;IIIIIIII)Landroidx/camera/core/impl/EncoderProfilesProxy$VideoProfileProxy;", "bitRate", "sampleRate", "channels", "createDefaultAudioProfile", "(ILjava/lang/String;IIII)Landroidx/camera/core/impl/EncoderProfilesProxy$AudioProfileProxy;", "getTypicalBitrate", "(Landroidx/camera/video/Quality;)I", "Landroidx/camera/video/Quality$ConstantQuality;", "find", "(Ljava/util/List;I)Landroidx/camera/video/Quality$ConstantQuality;", _UrlKt.FRAGMENT_ENCODE_SET, "hasProfile", "(I)Z", "getAll", "Landroidx/camera/core/impl/CameraInfoInternal;", "Ljava/util/List;", "Landroidx/camera/video/internal/encoder/VideoEncoderInfo$Finder;", _UrlKt.FRAGMENT_ENCODE_SET, "Landroid/util/Size;", "kotlin.jvm.PlatformType", "supportedSizes$delegate", "Lkotlin/Lazy;", "getSupportedSizes", "()Ljava/util/List;", "supportedSizes", _UrlKt.FRAGMENT_ENCODE_SET, "encoderProfilesMap", "Ljava/util/Map;", "Companion", "camera-video"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nDefaultEncoderProfilesProvider.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DefaultEncoderProfilesProvider.kt\nandroidx/camera/video/internal/workaround/DefaultEncoderProfilesProvider\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,238:1\n1#2:239\n*E\n"})
public final class DefaultEncoderProfilesProvider implements EncoderProfilesProvider {
    private final CameraInfoInternal cameraInfo;
    private final List<Quality> targetQualities;
    private final VideoEncoderInfo.Finder videoEncoderInfoFinder;

    /* JADX INFO: renamed from: supportedSizes$delegate, reason: from kotlin metadata */
    private final Lazy supportedSizes = LazyKt.lazy(new Function0() { // from class: androidx.camera.video.internal.workaround.DefaultEncoderProfilesProvider$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return this.f$0.cameraInfo.getSupportedResolutions(34);
        }
    });
    private final Map<Integer, EncoderProfilesProxy> encoderProfilesMap = new LinkedHashMap();

    /* JADX WARN: Multi-variable type inference failed */
    public DefaultEncoderProfilesProvider(CameraInfoInternal cameraInfoInternal, List<? extends Quality> list, VideoEncoderInfo.Finder finder) {
        this.cameraInfo = cameraInfoInternal;
        this.targetQualities = list;
        this.videoEncoderInfoFinder = finder;
    }

    private final List<Size> getSupportedSizes() {
        return (List) this.supportedSizes.getValue();
    }

    @Override // androidx.camera.core.impl.EncoderProfilesProvider
    public boolean hasProfile(int quality) {
        return getProfileInternal(quality) != null;
    }

    @Override // androidx.camera.core.impl.EncoderProfilesProvider
    public EncoderProfilesProxy getAll(int quality) {
        return getProfileInternal(quality);
    }

    private final EncoderProfilesProxy getProfileInternal(int quality) {
        if (this.encoderProfilesMap.containsKey(Integer.valueOf(quality))) {
            return this.encoderProfilesMap.get(Integer.valueOf(quality));
        }
        EncoderProfilesProxy encoderProfilesProxyGenerateEncoderProfiles = generateEncoderProfiles(quality);
        this.encoderProfilesMap.put(Integer.valueOf(quality), encoderProfilesProxyGenerateEncoderProfiles);
        return encoderProfilesProxyGenerateEncoderProfiles;
    }

    private final EncoderProfilesProxy generateEncoderProfiles(int quality) {
        EncoderProfilesProxy.VideoProfileProxy videoProfileProxyGenerateVideoProfiles = generateVideoProfiles(quality);
        if (videoProfileProxyGenerateVideoProfiles == null) {
            return null;
        }
        return createDefaultEncoderProfiles$default(this, 0, 0, videoProfileProxyGenerateVideoProfiles, createDefaultAudioProfile$default(this, 0, null, 0, 0, 0, 0, 63, null), 3, null);
    }

    private final EncoderProfilesProxy.VideoProfileProxy generateVideoProfiles(int quality) {
        EncoderProfilesProxy.VideoProfileProxy videoProfileProxyResolveVideoProfile;
        Quality.ConstantQuality constantQualityFind = find(this.targetQualities, quality);
        if (constantQualityFind == null) {
            return null;
        }
        for (Size size : constantQualityFind.getTypicalSizes()) {
            if (getSupportedSizes().contains(size) && (videoProfileProxyResolveVideoProfile = resolveVideoProfile(size.getWidth(), size.getHeight(), getTypicalBitrate(constantQualityFind))) != null) {
                return videoProfileProxyResolveVideoProfile;
            }
        }
        return null;
    }

    private final EncoderProfilesProxy.VideoProfileProxy resolveVideoProfile(int width, int height, int bitrate) {
        EncoderProfilesProxy.VideoProfileProxy videoProfileProxyCreateDefaultVideoProfile$default = createDefaultVideoProfile$default(this, 0, null, width, height, bitrate, 0, 0, 0, 0, 0, 995, null);
        VideoEncoderInfo videoEncoderInfoFind = this.videoEncoderInfoFinder.find(videoProfileProxyCreateDefaultVideoProfile$default.getMediaType());
        if (videoEncoderInfoFind == null || !videoEncoderInfoFind.isSizeSupportedAllowSwapping(width, height)) {
            return null;
        }
        Integer num = (Integer) videoEncoderInfoFind.getSupportedBitrateRange().clamp(Integer.valueOf(bitrate));
        return (num != null && num.intValue() == bitrate) ? videoProfileProxyCreateDefaultVideoProfile$default : createDefaultVideoProfile$default(this, 0, null, width, height, num.intValue(), 0, 0, 0, 0, 0, 995, null);
    }

    public static /* synthetic */ EncoderProfilesProxy createDefaultEncoderProfiles$default(DefaultEncoderProfilesProvider defaultEncoderProfilesProvider, int i, int i2, EncoderProfilesProxy.VideoProfileProxy videoProfileProxy, EncoderProfilesProxy.AudioProfileProxy audioProfileProxy, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 60;
        }
        if ((i3 & 2) != 0) {
            i2 = 2;
        }
        return defaultEncoderProfilesProvider.createDefaultEncoderProfiles(i, i2, videoProfileProxy, audioProfileProxy);
    }

    private final EncoderProfilesProxy createDefaultEncoderProfiles(int defaultDurationSeconds, int recommendedFileFormat, EncoderProfilesProxy.VideoProfileProxy videoProfile, EncoderProfilesProxy.AudioProfileProxy audioProfile) {
        return EncoderProfilesProxy.ImmutableEncoderProfilesProxy.create(defaultDurationSeconds, recommendedFileFormat, CollectionsKt.listOf(audioProfile), CollectionsKt.listOf(videoProfile));
    }

    public static /* synthetic */ EncoderProfilesProxy.VideoProfileProxy createDefaultVideoProfile$default(DefaultEncoderProfilesProvider defaultEncoderProfilesProvider, int i, String str, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, Object obj) {
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        String str2;
        DefaultEncoderProfilesProvider defaultEncoderProfilesProvider2;
        if ((i10 & 1) != 0) {
            i = 2;
        }
        if ((i10 & 2) != 0) {
            str = MediaController.VIDEO_MIME_TYPE;
        }
        if ((i10 & 32) != 0) {
            i5 = 30;
        }
        if ((i10 & 64) != 0) {
            i6 = -1;
        }
        if ((i10 & 128) != 0) {
            i7 = 8;
        }
        if ((i10 & 256) != 0) {
            i8 = 0;
        }
        if ((i10 & 512) != 0) {
            i11 = 0;
            i14 = i7;
            i12 = i8;
            i16 = i5;
            i13 = i6;
            i18 = i3;
            i15 = i4;
            str2 = str;
            i17 = i2;
            defaultEncoderProfilesProvider2 = defaultEncoderProfilesProvider;
            i19 = i;
        } else {
            i11 = i9;
            i12 = i8;
            i13 = i6;
            i14 = i7;
            i15 = i4;
            i16 = i5;
            i17 = i2;
            i18 = i3;
            i19 = i;
            str2 = str;
            defaultEncoderProfilesProvider2 = defaultEncoderProfilesProvider;
        }
        return defaultEncoderProfilesProvider2.createDefaultVideoProfile(i19, str2, i17, i18, i15, i16, i13, i14, i12, i11);
    }

    private final EncoderProfilesProxy.VideoProfileProxy createDefaultVideoProfile(int codec, String mimeType, int width, int height, int bitrate, int frameRate, int profile, int bitDepth, int chromaSubsampling, int hdrFormat) {
        return EncoderProfilesProxy.VideoProfileProxy.create(codec, mimeType, bitrate, frameRate, width, height, profile, bitDepth, chromaSubsampling, hdrFormat);
    }

    public static /* synthetic */ EncoderProfilesProxy.AudioProfileProxy createDefaultAudioProfile$default(DefaultEncoderProfilesProvider defaultEncoderProfilesProvider, int i, String str, int i2, int i3, int i4, int i5, int i6, Object obj) {
        if ((i6 & 1) != 0) {
            i = 3;
        }
        if ((i6 & 2) != 0) {
            str = MediaController.AUDIO_MIME_TYPE;
        }
        if ((i6 & 4) != 0) {
            i2 = 96000;
        }
        if ((i6 & 8) != 0) {
            i3 = 44100;
        }
        if ((i6 & 16) != 0) {
            i4 = 1;
        }
        if ((i6 & 32) != 0) {
            i5 = 2;
        }
        int i7 = i4;
        int i8 = i5;
        return defaultEncoderProfilesProvider.createDefaultAudioProfile(i, str, i2, i3, i7, i8);
    }

    private final EncoderProfilesProxy.AudioProfileProxy createDefaultAudioProfile(int codec, String mimeType, int bitRate, int sampleRate, int channels, int profile) {
        return EncoderProfilesProxy.AudioProfileProxy.create(codec, mimeType, bitRate, sampleRate, channels, profile);
    }

    private final int getTypicalBitrate(Quality quality) {
        if (Intrinsics.areEqual(quality, Quality.UHD)) {
            return 40000000;
        }
        if (Intrinsics.areEqual(quality, Quality.FHD)) {
            return 10000000;
        }
        if (Intrinsics.areEqual(quality, Quality.f30HD)) {
            return 4000000;
        }
        if (Intrinsics.areEqual(quality, Quality.f31SD)) {
            return 2000000;
        }
        Native$$ExternalSyntheticBUOutline5.m554m("Undefined bitrate for quality: ", quality);
        return 0;
    }

    private final Quality.ConstantQuality find(List<? extends Quality> list, int i) {
        Object next;
        Iterator<T> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (((Quality.ConstantQuality) ((Quality) next)).getQualityValue(1) == i) {
                break;
            }
        }
        if (next instanceof Quality.ConstantQuality) {
            return (Quality.ConstantQuality) next;
        }
        return null;
    }
}
