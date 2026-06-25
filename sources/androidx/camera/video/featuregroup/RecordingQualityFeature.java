package androidx.camera.video.featuregroup;

import androidx.camera.core.featuregroup.GroupableFeature;
import androidx.camera.core.featuregroup.impl.feature.FeatureTypeInternal;
import androidx.camera.video.Quality;
import kotlin.Metadata;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\f\u001a\u00020\rH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u000e"}, m877d2 = {"Landroidx/camera/video/featuregroup/RecordingQualityFeature;", "Landroidx/camera/core/featuregroup/GroupableFeature;", "quality", "Landroidx/camera/video/Quality;", "<init>", "(Landroidx/camera/video/Quality;)V", "getQuality", "()Landroidx/camera/video/Quality;", "featureTypeInternal", "Landroidx/camera/core/featuregroup/impl/feature/FeatureTypeInternal;", "getFeatureTypeInternal", "()Landroidx/camera/core/featuregroup/impl/feature/FeatureTypeInternal;", "toString", _UrlKt.FRAGMENT_ENCODE_SET, "camera-video"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
public final class RecordingQualityFeature extends GroupableFeature {
    private final FeatureTypeInternal featureTypeInternal = FeatureTypeInternal.RECORDING_QUALITY;
    private final Quality quality;

    public RecordingQualityFeature(Quality quality) {
        this.quality = quality;
    }

    public final Quality getQuality() {
        return this.quality;
    }

    @Override // androidx.camera.core.featuregroup.GroupableFeature
    public FeatureTypeInternal getFeatureTypeInternal() {
        return this.featureTypeInternal;
    }

    public String toString() {
        return "RecordingQualityFeature(quality=" + this.quality + ')';
    }
}
