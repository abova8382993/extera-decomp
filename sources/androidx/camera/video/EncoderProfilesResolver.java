package androidx.camera.video;

import android.util.Size;
import androidx.camera.core.DynamicRange;
import androidx.camera.core.impl.DynamicRanges;
import androidx.camera.core.impl.EncoderProfilesProvider;
import androidx.camera.video.internal.DynamicRangeMatchedEncoderProfilesProvider;
import androidx.camera.video.internal.VideoValidatedEncoderProfilesProxy;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.SourceDebugExtension;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010%\n\u0002\b\b\b\u0000\u0018\u0000 &2\u00020\u0001:\u0001&B%\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0004\b\t\u0010\nJ\u0019\u0010\r\u001a\u0004\u0018\u00010\f2\u0006\u0010\u000b\u001a\u00020\u0007H\u0002¢\u0006\u0004\b\r\u0010\u000eJ\u001b\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u000b\u001a\u00020\u0007¢\u0006\u0004\b\u0011\u0010\u0012J\u001f\u0010\u0015\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\u0007¢\u0006\u0004\b\u0015\u0010\u0016J\u001f\u0010\u0018\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\u0007¢\u0006\u0004\b\u0018\u0010\u0019J\u001f\u0010\u001b\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u000b\u001a\u00020\u0007¢\u0006\u0004\b\u001b\u0010\u001cR\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010\u001dR\u0014\u0010\u0005\u001a\u00020\u00048\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0005\u0010\u001eR \u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\f0\u001f8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b \u0010!R\"\u0010\"\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\f0\u001f8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\"\u0010!R\u001d\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068\u0006¢\u0006\f\n\u0004\b\b\u0010#\u001a\u0004\b$\u0010%¨\u0006'"}, m877d2 = {"Landroidx/camera/video/EncoderProfilesResolver;", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/core/impl/EncoderProfilesProvider;", "hostProfilesProvider", _UrlKt.FRAGMENT_ENCODE_SET, "qualitySource", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/core/DynamicRange;", "supportedDynamicRanges", "<init>", "(Landroidx/camera/core/impl/EncoderProfilesProvider;ILjava/util/Set;)V", "dynamicRange", "Landroidx/camera/video/CapabilitiesByQuality;", "getCapabilities", "(Landroidx/camera/core/DynamicRange;)Landroidx/camera/video/CapabilitiesByQuality;", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/video/Quality;", "getSupportedQualities", "(Landroidx/camera/core/DynamicRange;)Ljava/util/List;", "quality", "Landroid/util/Size;", "getResolution", "(Landroidx/camera/video/Quality;Landroidx/camera/core/DynamicRange;)Landroid/util/Size;", "Landroidx/camera/video/internal/VideoValidatedEncoderProfilesProxy;", "getProfiles", "(Landroidx/camera/video/Quality;Landroidx/camera/core/DynamicRange;)Landroidx/camera/video/internal/VideoValidatedEncoderProfilesProxy;", "size", "findNearestHigherSupportedEncoderProfilesFor", "(Landroid/util/Size;Landroidx/camera/core/DynamicRange;)Landroidx/camera/video/internal/VideoValidatedEncoderProfilesProxy;", "Landroidx/camera/core/impl/EncoderProfilesProvider;", "I", _UrlKt.FRAGMENT_ENCODE_SET, "fullySpecifiedMap", "Ljava/util/Map;", "nonFullySpecifiedMap", "Ljava/util/Set;", "getSupportedDynamicRanges", "()Ljava/util/Set;", "Companion", "camera-video"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nEncoderProfilesResolver.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EncoderProfilesResolver.kt\nandroidx/camera/video/EncoderProfilesResolver\n+ 2 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,114:1\n384#2,7:115\n*S KotlinDebug\n*F\n+ 1 EncoderProfilesResolver.kt\nandroidx/camera/video/EncoderProfilesResolver\n*L\n103#1:115,7\n*E\n"})
public final class EncoderProfilesResolver {

    @JvmField
    public static final EncoderProfilesResolver EMPTY = new EncoderProfilesResolver(EncoderProfilesProvider.EMPTY, 1, SetsKt.emptySet());
    private final EncoderProfilesProvider hostProfilesProvider;
    private final int qualitySource;
    private final Set<DynamicRange> supportedDynamicRanges;
    private final Map<DynamicRange, CapabilitiesByQuality> fullySpecifiedMap = new LinkedHashMap();
    private final Map<DynamicRange, CapabilitiesByQuality> nonFullySpecifiedMap = new LinkedHashMap();

    public EncoderProfilesResolver(EncoderProfilesProvider encoderProfilesProvider, int i, Set<DynamicRange> set) {
        this.hostProfilesProvider = encoderProfilesProvider;
        this.qualitySource = i;
        for (DynamicRange dynamicRange : set) {
            CapabilitiesByQuality capabilitiesByQuality = new CapabilitiesByQuality(new DynamicRangeMatchedEncoderProfilesProvider(this.hostProfilesProvider, dynamicRange), this.qualitySource);
            if (!capabilitiesByQuality.getSupportedQualities().isEmpty()) {
                this.fullySpecifiedMap.put(dynamicRange, capabilitiesByQuality);
            }
        }
        this.supportedDynamicRanges = this.fullySpecifiedMap.keySet();
    }

    public final Set<DynamicRange> getSupportedDynamicRanges() {
        return this.supportedDynamicRanges;
    }

    public final List<Quality> getSupportedQualities(DynamicRange dynamicRange) {
        List<Quality> supportedQualities;
        CapabilitiesByQuality capabilities = getCapabilities(dynamicRange);
        return (capabilities == null || (supportedQualities = capabilities.getSupportedQualities()) == null) ? CollectionsKt.emptyList() : supportedQualities;
    }

    public final Size getResolution(Quality quality, DynamicRange dynamicRange) {
        CapabilitiesByQuality capabilities = getCapabilities(dynamicRange);
        if (capabilities != null) {
            return capabilities.getResolution(quality);
        }
        return null;
    }

    public final VideoValidatedEncoderProfilesProxy getProfiles(Quality quality, DynamicRange dynamicRange) {
        CapabilitiesByQuality capabilities = getCapabilities(dynamicRange);
        if (capabilities != null) {
            return capabilities.getProfiles(quality);
        }
        return null;
    }

    public final VideoValidatedEncoderProfilesProxy findNearestHigherSupportedEncoderProfilesFor(Size size, DynamicRange dynamicRange) {
        CapabilitiesByQuality capabilities = getCapabilities(dynamicRange);
        if (capabilities != null) {
            return capabilities.findNearestHigherSupportedEncoderProfilesFor(size);
        }
        return null;
    }

    private final CapabilitiesByQuality getCapabilities(DynamicRange dynamicRange) {
        if (dynamicRange.isFullySpecified()) {
            return this.fullySpecifiedMap.get(dynamicRange);
        }
        Map<DynamicRange, CapabilitiesByQuality> map = this.nonFullySpecifiedMap;
        CapabilitiesByQuality capabilitiesByQuality = map.get(dynamicRange);
        if (capabilitiesByQuality == null) {
            capabilitiesByQuality = DynamicRanges.canResolve(dynamicRange, this.fullySpecifiedMap.keySet()) ? new CapabilitiesByQuality(new DynamicRangeMatchedEncoderProfilesProvider(this.hostProfilesProvider, dynamicRange), this.qualitySource) : null;
            map.put(dynamicRange, capabilitiesByQuality);
        }
        return capabilitiesByQuality;
    }
}
