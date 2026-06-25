package androidx.camera.video;

import androidx.camera.core.DynamicRange;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.EncoderProfilesProvider;
import androidx.camera.core.impl.Quirks;
import androidx.camera.video.internal.BackupHdrProfileEncoderProfilesProvider;
import androidx.camera.video.internal.QualityExploredEncoderProfilesProvider;
import androidx.camera.video.internal.compat.quirk.DeviceQuirks;
import androidx.camera.video.internal.encoder.VideoEncoderInfo;
import androidx.camera.video.internal.workaround.DefaultEncoderProfilesProvider;
import androidx.camera.video.internal.workaround.QualityAddedEncoderProfilesProvider;
import androidx.camera.video.internal.workaround.QualityResolutionModifiedEncoderProfilesProvider;
import androidx.camera.video.internal.workaround.QualityValidatedEncoderProfilesProvider;
import java.util.Collections;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.SourceDebugExtension;
import okhttp3.internal.url._UrlKt;
import okio.Utf8$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\bÀ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J-\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\t¢\u0006\u0004\b\f\u0010\rR\u0018\u0010\u000f\u001a\u00020\u000e*\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, m877d2 = {"Landroidx/camera/video/EncoderProfilesProviderResolver;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "Landroidx/camera/core/impl/CameraInfoInternal;", "cameraInfo", _UrlKt.FRAGMENT_ENCODE_SET, "videoCapabilitiesSource", "qualitySource", "Landroidx/camera/video/internal/encoder/VideoEncoderInfo$Finder;", "videoEncoderInfoFinder", "Landroidx/camera/core/impl/EncoderProfilesProvider;", "resolve", "(Landroidx/camera/core/impl/CameraInfoInternal;IILandroidx/camera/video/internal/encoder/VideoEncoderInfo$Finder;)Landroidx/camera/core/impl/EncoderProfilesProvider;", _UrlKt.FRAGMENT_ENCODE_SET, "isHlg10Supported", "(Landroidx/camera/core/impl/CameraInfoInternal;)Z", "camera-video"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nEncoderProfilesProviderResolver.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EncoderProfilesProviderResolver.kt\nandroidx/camera/video/EncoderProfilesProviderResolver\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,125:1\n1761#2,3:126\n*S KotlinDebug\n*F\n+ 1 EncoderProfilesProviderResolver.kt\nandroidx/camera/video/EncoderProfilesProviderResolver\n*L\n120#1:126,3\n*E\n"})
public final class EncoderProfilesProviderResolver {
    public static final EncoderProfilesProviderResolver INSTANCE = new EncoderProfilesProviderResolver();

    private EncoderProfilesProviderResolver() {
    }

    public final EncoderProfilesProvider resolve(CameraInfoInternal cameraInfo, int videoCapabilitiesSource, int qualitySource, VideoEncoderInfo.Finder videoEncoderInfoFinder) {
        VideoEncoderInfo.Finder finder;
        if (videoCapabilitiesSource != 0 && videoCapabilitiesSource != 1) {
            Utf8$$ExternalSyntheticBUOutline1.m995m("Not a supported video capabilities source: ", videoCapabilitiesSource);
            return null;
        }
        EncoderProfilesProvider encoderProfilesProvider = cameraInfo.getEncoderProfilesProvider();
        if (qualitySource == 2) {
            return !cameraInfo.isHighSpeedSupported() ? EncoderProfilesProvider.EMPTY : encoderProfilesProvider;
        }
        if (!CapabilitiesByQuality.containsSupportedQuality(encoderProfilesProvider, qualitySource)) {
            Logger.m79w("EncoderProfilesResolver", "Camera EncoderProfilesProvider doesn't contain any supported Quality.");
            encoderProfilesProvider = new DefaultEncoderProfilesProvider(cameraInfo, CollectionsKt.listOf((Object[]) new Quality[]{Quality.FHD, Quality.f30HD, Quality.f31SD}), videoEncoderInfoFinder);
        }
        Quirks all = DeviceQuirks.getAll();
        EncoderProfilesProvider qualityAddedEncoderProfilesProvider = new QualityAddedEncoderProfilesProvider(encoderProfilesProvider, all, cameraInfo, videoEncoderInfoFinder);
        if (videoCapabilitiesSource == 1) {
            finder = videoEncoderInfoFinder;
            qualityAddedEncoderProfilesProvider = new QualityExploredEncoderProfilesProvider(qualityAddedEncoderProfilesProvider, Quality.getSortedQualities(), Collections.singleton(DynamicRange.SDR), cameraInfo.getSupportedResolutions(34), finder);
        } else {
            finder = videoEncoderInfoFinder;
        }
        EncoderProfilesProvider qualityResolutionModifiedEncoderProfilesProvider = new QualityResolutionModifiedEncoderProfilesProvider(qualityAddedEncoderProfilesProvider, all);
        if (isHlg10Supported(cameraInfo)) {
            qualityResolutionModifiedEncoderProfilesProvider = new BackupHdrProfileEncoderProfilesProvider(qualityResolutionModifiedEncoderProfilesProvider, finder);
        }
        return new QualityValidatedEncoderProfilesProvider(qualityResolutionModifiedEncoderProfilesProvider, cameraInfo, all);
    }

    private final boolean isHlg10Supported(CameraInfoInternal cameraInfoInternal) {
        Set<DynamicRange> supportedDynamicRanges = cameraInfoInternal.getSupportedDynamicRanges();
        if (supportedDynamicRanges != null && supportedDynamicRanges.isEmpty()) {
            return false;
        }
        for (DynamicRange dynamicRange : supportedDynamicRanges) {
            if (dynamicRange.getEncoding() == 3 && dynamicRange.getBitDepth() == 10) {
                return true;
            }
        }
        return false;
    }
}
