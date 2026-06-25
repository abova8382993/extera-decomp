package androidx.camera.video;

import android.util.Size;
import androidx.camera.core.DynamicRange;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.DynamicRanges;
import androidx.camera.video.Quality;
import androidx.camera.video.internal.config.VideoConfigUtil;
import androidx.camera.video.internal.encoder.VideoEncoderInfo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0000\u0018\u00002\u00020\u0001:\u0001!B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\b\u0010\tJ\u0015\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016¢\u0006\u0004\b\f\u0010\rJ\u001d\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u000e\u001a\u00020\u000bH\u0016¢\u0006\u0004\b\u0011\u0010\u0012J!\u0010\u0015\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u000e\u001a\u00020\u000bH\u0016¢\u0006\u0004\b\u0015\u0010\u0016J\u000f\u0010\u0017\u001a\u00020\u0002H\u0016¢\u0006\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010\u0019R\u0014\u0010\u0005\u001a\u00020\u00048\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0005\u0010\u001aR\u001b\u0010 \u001a\u00020\u001b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001f¨\u0006\""}, m877d2 = {"Landroidx/camera/video/MimeMatchedVideoCapabilities;", "Landroidx/camera/video/VideoCapabilities;", _UrlKt.FRAGMENT_ENCODE_SET, "mime", "Landroidx/camera/core/impl/CameraInfoInternal;", "cameraInfo", "Landroidx/camera/video/internal/encoder/VideoEncoderInfo$Finder;", "videoEncoderInfoFinder", "<init>", "(Ljava/lang/String;Landroidx/camera/core/impl/CameraInfoInternal;Landroidx/camera/video/internal/encoder/VideoEncoderInfo$Finder;)V", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/core/DynamicRange;", "getSupportedDynamicRanges", "()Ljava/util/Set;", "dynamicRange", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/video/Quality;", "getSupportedQualities", "(Landroidx/camera/core/DynamicRange;)Ljava/util/List;", "quality", "Landroid/util/Size;", "getResolution", "(Landroidx/camera/video/Quality;Landroidx/camera/core/DynamicRange;)Landroid/util/Size;", "toString", "()Ljava/lang/String;", "Ljava/lang/String;", "Landroidx/camera/core/impl/CameraInfoInternal;", "Landroidx/camera/video/MimeMatchedVideoCapabilities$ValidatedData;", "validatedData$delegate", "Lkotlin/Lazy;", "getValidatedData", "()Landroidx/camera/video/MimeMatchedVideoCapabilities$ValidatedData;", "validatedData", "ValidatedData", "camera-video"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nMimeMatchedVideoCapabilities.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MimeMatchedVideoCapabilities.kt\nandroidx/camera/video/MimeMatchedVideoCapabilities\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,107:1\n808#2,11:108\n1617#2,9:119\n1869#2:128\n295#2,2:129\n1870#2:133\n1626#2:134\n1#3:131\n1#3:132\n*S KotlinDebug\n*F\n+ 1 MimeMatchedVideoCapabilities.kt\nandroidx/camera/video/MimeMatchedVideoCapabilities\n*L\n62#1:108,11\n63#1:119,9\n63#1:128\n65#1:129,2\n63#1:133\n63#1:134\n63#1:132\n*E\n"})
public final class MimeMatchedVideoCapabilities implements VideoCapabilities {
    private final CameraInfoInternal cameraInfo;
    private final String mime;

    /* JADX INFO: renamed from: validatedData$delegate, reason: from kotlin metadata */
    private final Lazy validatedData;

    public MimeMatchedVideoCapabilities(String str, CameraInfoInternal cameraInfoInternal, final VideoEncoderInfo.Finder finder) {
        this.mime = str;
        this.cameraInfo = cameraInfoInternal;
        this.validatedData = LazyKt.lazy(new Function0() { // from class: androidx.camera.video.MimeMatchedVideoCapabilities$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return MimeMatchedVideoCapabilities.$r8$lambda$n5XMUnTC0mYqj_QZDJcpYORFhXc(finder, this);
            }
        });
    }

    @Metadata(m876d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\t\b\u0082\b\u0018\u00002\u00020\u0001B-\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\u0014\b\u0002\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\u0004\b\t\u0010\nJ\u0010\u0010\f\u001a\u00020\u000bHÖ\u0001¢\u0006\u0004\b\f\u0010\rJ\u0010\u0010\u000f\u001a\u00020\u000eHÖ\u0001¢\u0006\u0004\b\u000f\u0010\u0010J\u001a\u0010\u0013\u001a\u00020\u00122\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u0013\u0010\u0014R\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006¢\u0006\f\n\u0004\b\u0004\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R#\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00058\u0006¢\u0006\f\n\u0004\b\b\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001b"}, m877d2 = {"Landroidx/camera/video/MimeMatchedVideoCapabilities$ValidatedData;", _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/core/DynamicRange;", "dynamicRanges", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/video/Quality;", "Landroid/util/Size;", "qualityToSizeMap", "<init>", "(Ljava/util/Set;Ljava/util/Map;)V", _UrlKt.FRAGMENT_ENCODE_SET, "toString", "()Ljava/lang/String;", _UrlKt.FRAGMENT_ENCODE_SET, "hashCode", "()I", "other", _UrlKt.FRAGMENT_ENCODE_SET, "equals", "(Ljava/lang/Object;)Z", "Ljava/util/Set;", "getDynamicRanges", "()Ljava/util/Set;", "Ljava/util/Map;", "getQualityToSizeMap", "()Ljava/util/Map;", "camera-video"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    public static final /* data */ class ValidatedData {
        private final Set<DynamicRange> dynamicRanges;
        private final Map<Quality, Size> qualityToSizeMap;

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ValidatedData)) {
                return false;
            }
            ValidatedData validatedData = (ValidatedData) other;
            return Intrinsics.areEqual(this.dynamicRanges, validatedData.dynamicRanges) && Intrinsics.areEqual(this.qualityToSizeMap, validatedData.qualityToSizeMap);
        }

        public int hashCode() {
            return (this.dynamicRanges.hashCode() * 31) + this.qualityToSizeMap.hashCode();
        }

        public String toString() {
            return "ValidatedData(dynamicRanges=" + this.dynamicRanges + ", qualityToSizeMap=" + this.qualityToSizeMap + ')';
        }

        public ValidatedData(Set<DynamicRange> set, Map<Quality, Size> map) {
            this.dynamicRanges = set;
            this.qualityToSizeMap = map;
        }

        public /* synthetic */ ValidatedData(Set set, Map map, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? SetsKt.emptySet() : set, (i & 2) != 0 ? MapsKt.emptyMap() : map);
        }

        public final Set<DynamicRange> getDynamicRanges() {
            return this.dynamicRanges;
        }

        public final Map<Quality, Size> getQualityToSizeMap() {
            return this.qualityToSizeMap;
        }
    }

    private final ValidatedData getValidatedData() {
        return (ValidatedData) this.validatedData.getValue();
    }

    public static ValidatedData $r8$lambda$n5XMUnTC0mYqj_QZDJcpYORFhXc(VideoEncoderInfo.Finder finder, MimeMatchedVideoCapabilities mimeMatchedVideoCapabilities) {
        Object next;
        VideoEncoderInfo videoEncoderInfoFind = finder.find(mimeMatchedVideoCapabilities.mime);
        int i = 3;
        Set set = null;
        byte b2 = 0;
        byte b3 = 0;
        byte b4 = 0;
        byte b5 = 0;
        byte b6 = 0;
        byte b7 = 0;
        byte b8 = 0;
        byte b9 = 0;
        byte b10 = 0;
        byte b11 = 0;
        byte b12 = 0;
        if (videoEncoderInfoFind == null) {
            return new ValidatedData(set, b12 == true ? 1 : 0, i, b11 == true ? 1 : 0);
        }
        Set<DynamicRange> supportedDynamicRanges = mimeMatchedVideoCapabilities.cameraInfo.getSupportedDynamicRanges();
        if (supportedDynamicRanges.isEmpty()) {
            return new ValidatedData(b10 == true ? 1 : 0, b9 == true ? 1 : 0, i, b8 == true ? 1 : 0);
        }
        Set setIntersect = CollectionsKt.intersect(supportedDynamicRanges, VideoConfigUtil.INSTANCE.getDynamicRangesForMime(mimeMatchedVideoCapabilities.mime));
        if (setIntersect.isEmpty()) {
            return new ValidatedData(b7 == true ? 1 : 0, b6 == true ? 1 : 0, i, b5 == true ? 1 : 0);
        }
        HashSet hashSet = CollectionsKt.toHashSet(mimeMatchedVideoCapabilities.cameraInfo.getSupportedResolutions(34));
        List<Quality> sortedQualities = Quality.getSortedQualities();
        ArrayList arrayList = new ArrayList();
        for (Object obj : sortedQualities) {
            if (obj instanceof Quality.ConstantQuality) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj2 = arrayList.get(i2);
            i2++;
            Quality.ConstantQuality constantQuality = (Quality.ConstantQuality) obj2;
            Iterator<T> it = constantQuality.getTypicalSizes().iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                Size size2 = (Size) next;
                if (hashSet.contains(size2) && videoEncoderInfoFind.isSizeSupported(size2.getWidth(), size2.getHeight())) {
                    break;
                }
            }
            Size size3 = (Size) next;
            Pair pairM884to = size3 != null ? TuplesKt.m884to(constantQuality, size3) : null;
            if (pairM884to != null) {
                arrayList2.add(pairM884to);
            }
        }
        Map map = MapsKt.toMap(arrayList2);
        if (map.isEmpty()) {
            return new ValidatedData(b4 == true ? 1 : 0, b3 == true ? 1 : 0, i, b2 == true ? 1 : 0);
        }
        return new ValidatedData(setIntersect, map);
    }

    @Override // androidx.camera.video.VideoCapabilities
    public Set<DynamicRange> getSupportedDynamicRanges() {
        return getValidatedData().getDynamicRanges();
    }

    @Override // androidx.camera.video.VideoCapabilities
    public List<Quality> getSupportedQualities(DynamicRange dynamicRange) {
        if (DynamicRanges.canResolve(dynamicRange, getValidatedData().getDynamicRanges())) {
            return CollectionsKt.toList(getValidatedData().getQualityToSizeMap().keySet());
        }
        return CollectionsKt.emptyList();
    }

    @Override // androidx.camera.video.VideoCapabilities
    public Size getResolution(Quality quality, DynamicRange dynamicRange) {
        if (DynamicRanges.canResolve(dynamicRange, getValidatedData().getDynamicRanges())) {
            return getValidatedData().getQualityToSizeMap().get(quality);
        }
        return null;
    }

    public String toString() {
        return "MimeMatchedVideoCapabilities(mime=" + this.mime + ", cameraInfo=" + this.cameraInfo + ')';
    }
}
