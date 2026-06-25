package androidx.camera.video;

import android.util.LruCache;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.impl.AdapterCameraInfo;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.video.internal.encoder.VideoEncoderInfo;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001:\u0001\u0013B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J.\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\f2\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0007J(\u0010\u0010\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\nH\u0002R\u001c\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00058\u0002X\u0083\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014²\u0006\n\u0010\u0010\u001a\u00020\u0007X\u008a\u0084\u0002"}, m877d2 = {"Landroidx/camera/video/EncoderProfilesResolverFactory;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "cache", "Landroid/util/LruCache;", "Landroidx/camera/video/EncoderProfilesResolverFactory$CacheKey;", "Landroidx/camera/video/EncoderProfilesResolver;", "getResolver", "cameraInfo", "Landroidx/camera/core/CameraInfo;", "videoRecordingType", _UrlKt.FRAGMENT_ENCODE_SET, "videoCapabilitiesSource", "videoEncoderInfoFinder", "Landroidx/camera/video/internal/encoder/VideoEncoderInfo$Finder;", "createResolver", "shouldSkipCache", _UrlKt.FRAGMENT_ENCODE_SET, "CacheKey", "camera-video"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nEncoderProfilesResolverFactory.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EncoderProfilesResolverFactory.kt\nandroidx/camera/video/EncoderProfilesResolverFactory\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,125:1\n1#2:126\n*E\n"})
public final class EncoderProfilesResolverFactory {
    public static final EncoderProfilesResolverFactory INSTANCE = new EncoderProfilesResolverFactory();
    private static final LruCache<CacheKey, EncoderProfilesResolver> cache = new LruCache<>(16);

    private EncoderProfilesResolverFactory() {
    }

    @JvmStatic
    public static final EncoderProfilesResolver getResolver(final CameraInfo cameraInfo, final int videoRecordingType, final int videoCapabilitiesSource, final VideoEncoderInfo.Finder videoEncoderInfoFinder) {
        EncoderProfilesResolver resolver$lambda$1;
        Lazy lazy = LazyKt.lazy(new Function0() { // from class: androidx.camera.video.EncoderProfilesResolverFactory$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return EncoderProfilesResolverFactory.INSTANCE.createResolver(cameraInfo, videoRecordingType, videoCapabilitiesSource, videoEncoderInfoFinder);
            }
        });
        if (INSTANCE.shouldSkipCache(cameraInfo)) {
            return getResolver$lambda$1(lazy);
        }
        AdapterCameraInfo adapterCameraInfo = (AdapterCameraInfo) cameraInfo;
        CacheKey cacheKey = new CacheKey(adapterCameraInfo.getCameraId(), adapterCameraInfo.getCameraConfig(), videoRecordingType, videoCapabilitiesSource, videoEncoderInfoFinder);
        LruCache<CacheKey, EncoderProfilesResolver> lruCache = cache;
        synchronized (lruCache) {
            resolver$lambda$1 = lruCache.get(cacheKey);
            if (resolver$lambda$1 == null) {
                resolver$lambda$1 = getResolver$lambda$1(lazy);
                lruCache.put(cacheKey, resolver$lambda$1);
            }
        }
        return resolver$lambda$1;
    }

    private static final EncoderProfilesResolver getResolver$lambda$1(Lazy<EncoderProfilesResolver> lazy) {
        return lazy.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final EncoderProfilesResolver createResolver(CameraInfo cameraInfo, int videoRecordingType, int videoCapabilitiesSource, VideoEncoderInfo.Finder videoEncoderInfoFinder) {
        CameraInfoInternal cameraInfoInternal = (CameraInfoInternal) cameraInfo;
        int i = videoRecordingType != 2 ? 1 : 2;
        return new EncoderProfilesResolver(EncoderProfilesProviderResolver.INSTANCE.resolve(cameraInfoInternal, videoCapabilitiesSource, i, videoEncoderInfoFinder), i, cameraInfoInternal.getSupportedDynamicRanges());
    }

    private final boolean shouldSkipCache(CameraInfo cameraInfo) {
        if (cameraInfo instanceof AdapterCameraInfo) {
            AdapterCameraInfo adapterCameraInfo = (AdapterCameraInfo) cameraInfo;
            if (!adapterCameraInfo.isExternalCamera() && adapterCameraInfo.getLensFacing() != -1) {
                return false;
            }
        }
        return true;
    }

    @Metadata(m876d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u000e\b\u0082\b\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0001\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\b¢\u0006\u0004\b\n\u0010\u000bJ\u0010\u0010\f\u001a\u00020\u0002HÖ\u0001¢\u0006\u0004\b\f\u0010\rJ\u0010\u0010\u000e\u001a\u00020\u0005HÖ\u0001¢\u0006\u0004\b\u000e\u0010\u000fJ\u001a\u0010\u0012\u001a\u00020\u00112\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n\u0004\b\u0003\u0010\u0014\u001a\u0004\b\u0015\u0010\rR\u0017\u0010\u0004\u001a\u00020\u00018\u0006¢\u0006\f\n\u0004\b\u0004\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R\u0017\u0010\u0006\u001a\u00020\u00058\u0006¢\u0006\f\n\u0004\b\u0006\u0010\u0019\u001a\u0004\b\u001a\u0010\u000fR\u0017\u0010\u0007\u001a\u00020\u00058\u0006¢\u0006\f\n\u0004\b\u0007\u0010\u0019\u001a\u0004\b\u001b\u0010\u000fR\u0017\u0010\t\u001a\u00020\b8\u0006¢\u0006\f\n\u0004\b\t\u0010\u001c\u001a\u0004\b\u001d\u0010\u001e¨\u0006\u001f"}, m877d2 = {"Landroidx/camera/video/EncoderProfilesResolverFactory$CacheKey;", _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, "cameraId", "cameraConfig", _UrlKt.FRAGMENT_ENCODE_SET, "videoRecordingType", "videoCapabilitiesSource", "Landroidx/camera/video/internal/encoder/VideoEncoderInfo$Finder;", "videoEncoderInfoFinder", "<init>", "(Ljava/lang/String;Ljava/lang/Object;IILandroidx/camera/video/internal/encoder/VideoEncoderInfo$Finder;)V", "toString", "()Ljava/lang/String;", "hashCode", "()I", "other", _UrlKt.FRAGMENT_ENCODE_SET, "equals", "(Ljava/lang/Object;)Z", "Ljava/lang/String;", "getCameraId", "Ljava/lang/Object;", "getCameraConfig", "()Ljava/lang/Object;", "I", "getVideoRecordingType", "getVideoCapabilitiesSource", "Landroidx/camera/video/internal/encoder/VideoEncoderInfo$Finder;", "getVideoEncoderInfoFinder", "()Landroidx/camera/video/internal/encoder/VideoEncoderInfo$Finder;", "camera-video"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    public static final /* data */ class CacheKey {
        private final Object cameraConfig;
        private final String cameraId;
        private final int videoCapabilitiesSource;
        private final VideoEncoderInfo.Finder videoEncoderInfoFinder;
        private final int videoRecordingType;

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof CacheKey)) {
                return false;
            }
            CacheKey cacheKey = (CacheKey) other;
            return Intrinsics.areEqual(this.cameraId, cacheKey.cameraId) && Intrinsics.areEqual(this.cameraConfig, cacheKey.cameraConfig) && this.videoRecordingType == cacheKey.videoRecordingType && this.videoCapabilitiesSource == cacheKey.videoCapabilitiesSource && Intrinsics.areEqual(this.videoEncoderInfoFinder, cacheKey.videoEncoderInfoFinder);
        }

        public int hashCode() {
            return (((((((this.cameraId.hashCode() * 31) + this.cameraConfig.hashCode()) * 31) + Integer.hashCode(this.videoRecordingType)) * 31) + Integer.hashCode(this.videoCapabilitiesSource)) * 31) + this.videoEncoderInfoFinder.hashCode();
        }

        public String toString() {
            return "CacheKey(cameraId=" + this.cameraId + ", cameraConfig=" + this.cameraConfig + ", videoRecordingType=" + this.videoRecordingType + ", videoCapabilitiesSource=" + this.videoCapabilitiesSource + ", videoEncoderInfoFinder=" + this.videoEncoderInfoFinder + ')';
        }

        public CacheKey(String str, Object obj, int i, int i2, VideoEncoderInfo.Finder finder) {
            this.cameraId = str;
            this.cameraConfig = obj;
            this.videoRecordingType = i;
            this.videoCapabilitiesSource = i2;
            this.videoEncoderInfoFinder = finder;
        }
    }
}
