package androidx.camera.video.internal.workaround;

import android.util.Range;
import android.util.Size;
import androidx.camera.core.Logger;
import androidx.camera.video.internal.compat.quirk.DeviceQuirks;
import androidx.camera.video.internal.compat.quirk.MediaCodecInfoReportIncorrectInfoQuirk;
import androidx.camera.video.internal.encoder.VideoEncoderInfo;
import java.util.HashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SourceDebugExtension;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010#\n\u0002\b\u000b\u0018\u0000 '2\u00020\u0001:\u0001'B\u0011\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0004\b\u0003\u0010\u0004J\u0017\u0010\b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0005H\u0002¢\u0006\u0004\b\b\u0010\tJ\u000f\u0010\u000b\u001a\u00020\nH\u0016¢\u0006\u0004\b\u000b\u0010\fJ\u001f\u0010\u0010\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\rH\u0016¢\u0006\u0004\b\u0010\u0010\u0011J\u0015\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\r0\u0012H\u0016¢\u0006\u0004\b\u0013\u0010\u0014J\u0015\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\r0\u0012H\u0016¢\u0006\u0004\b\u0015\u0010\u0014J\u001d\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\r0\u00122\u0006\u0010\u000f\u001a\u00020\rH\u0016¢\u0006\u0004\b\u0016\u0010\u0017J\u001d\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\r0\u00122\u0006\u0010\u000e\u001a\u00020\rH\u0016¢\u0006\u0004\b\u0018\u0010\u0017R\u0014\u0010\u0002\u001a\u00020\u00018\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0002\u0010\u0019R\u001a\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\r0\u00128\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\r0\u00128\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u001c\u0010\u001bR\u001a\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00050\u001d8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u001e\u0010\u001fR\u0014\u0010\"\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b \u0010!R\u0014\u0010$\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010!R\u001a\u0010&\u001a\b\u0012\u0004\u0012\u00020\r0\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b%\u0010\u0014¨\u0006("}, m877d2 = {"Landroidx/camera/video/internal/workaround/VideoEncoderInfoWrapper;", "Landroidx/camera/video/internal/encoder/VideoEncoderInfo;", "videoEncoderInfo", "<init>", "(Landroidx/camera/video/internal/encoder/VideoEncoderInfo;)V", "Landroid/util/Size;", "size", _UrlKt.FRAGMENT_ENCODE_SET, "addExtraSupportedSize", "(Landroid/util/Size;)V", _UrlKt.FRAGMENT_ENCODE_SET, "canSwapWidthHeight", "()Z", _UrlKt.FRAGMENT_ENCODE_SET, "width", "height", "isSizeSupported", "(II)Z", "Landroid/util/Range;", "getSupportedWidths", "()Landroid/util/Range;", "getSupportedHeights", "getSupportedWidthsFor", "(I)Landroid/util/Range;", "getSupportedHeightsFor", "Landroidx/camera/video/internal/encoder/VideoEncoderInfo;", "_supportedWidths", "Landroid/util/Range;", "_supportedHeights", _UrlKt.FRAGMENT_ENCODE_SET, "extraSupportedSizes", "Ljava/util/Set;", "getWidthAlignment", "()I", "widthAlignment", "getHeightAlignment", "heightAlignment", "getSupportedBitrateRange", "supportedBitrateRange", "Companion", "camera-video"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nVideoEncoderInfoWrapper.kt\nKotlin\n*S Kotlin\n*F\n+ 1 VideoEncoderInfoWrapper.kt\nandroidx/camera/video/internal/workaround/VideoEncoderInfoWrapper\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,186:1\n1761#2,3:187\n*S KotlinDebug\n*F\n+ 1 VideoEncoderInfoWrapper.kt\nandroidx/camera/video/internal/workaround/VideoEncoderInfoWrapper\n*L\n70#1:187,3\n*E\n"})
public final class VideoEncoderInfoWrapper implements VideoEncoderInfo {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Range<Integer> _supportedHeights;
    private final Range<Integer> _supportedWidths;
    private final Set<Size> extraSupportedSizes;
    private final VideoEncoderInfo videoEncoderInfo;

    public /* synthetic */ VideoEncoderInfoWrapper(VideoEncoderInfo videoEncoderInfo, DefaultConstructorMarker defaultConstructorMarker) {
        this(videoEncoderInfo);
    }

    @JvmStatic
    public static final VideoEncoderInfo from(VideoEncoderInfo videoEncoderInfo, Size size) {
        return INSTANCE.from(videoEncoderInfo, size);
    }

    private VideoEncoderInfoWrapper(VideoEncoderInfo videoEncoderInfo) {
        this.videoEncoderInfo = videoEncoderInfo;
        HashSet hashSet = new HashSet();
        this.extraSupportedSizes = hashSet;
        int widthAlignment = videoEncoderInfo.getWidthAlignment();
        this._supportedWidths = Range.create(Integer.valueOf(widthAlignment), Integer.valueOf(((int) Math.ceil(4096.0d / ((double) widthAlignment))) * widthAlignment));
        int heightAlignment = videoEncoderInfo.getHeightAlignment();
        this._supportedHeights = Range.create(Integer.valueOf(heightAlignment), Integer.valueOf(((int) Math.ceil(2160.0d / ((double) heightAlignment))) * heightAlignment));
        hashSet.addAll(MediaCodecInfoReportIncorrectInfoQuirk.getExtraSupportedSizes());
    }

    @Override // androidx.camera.video.internal.encoder.VideoEncoderInfo
    public boolean canSwapWidthHeight() {
        return this.videoEncoderInfo.canSwapWidthHeight();
    }

    @Override // androidx.camera.video.internal.encoder.VideoEncoderInfo
    public boolean isSizeSupported(int width, int height) {
        if (this.videoEncoderInfo.isSizeSupported(width, height)) {
            return true;
        }
        Set<Size> set = this.extraSupportedSizes;
        if (set == null || !set.isEmpty()) {
            for (Size size : set) {
                if (size.getWidth() == width && size.getHeight() == height) {
                    return true;
                }
            }
        }
        return this._supportedWidths.contains(Integer.valueOf(width)) && this._supportedHeights.contains(Integer.valueOf(height)) && width % this.videoEncoderInfo.getWidthAlignment() == 0 && height % this.videoEncoderInfo.getHeightAlignment() == 0;
    }

    @Override // androidx.camera.video.internal.encoder.VideoEncoderInfo
    public Range<Integer> getSupportedWidths() {
        return this._supportedWidths;
    }

    @Override // androidx.camera.video.internal.encoder.VideoEncoderInfo
    public Range<Integer> getSupportedHeights() {
        return this._supportedHeights;
    }

    @Override // androidx.camera.video.internal.encoder.VideoEncoderInfo
    public Range<Integer> getSupportedWidthsFor(int height) {
        if (!this._supportedHeights.contains(Integer.valueOf(height)) || height % this.videoEncoderInfo.getHeightAlignment() != 0) {
            StringBuilder sb = new StringBuilder("Not supported height: ");
            sb.append(height);
            sb.append(" which is not in ");
            sb.append(this._supportedHeights);
            int heightAlignment = this.videoEncoderInfo.getHeightAlignment();
            sb.append(" or can not be divided by alignment ");
            sb.append(heightAlignment);
            throw new IllegalArgumentException(sb.toString().toString());
        }
        return this._supportedWidths;
    }

    @Override // androidx.camera.video.internal.encoder.VideoEncoderInfo
    public Range<Integer> getSupportedHeightsFor(int width) {
        if (!this._supportedWidths.contains(Integer.valueOf(width)) || width % this.videoEncoderInfo.getWidthAlignment() != 0) {
            StringBuilder sb = new StringBuilder("Not supported width: ");
            sb.append(width);
            sb.append(" which is not in ");
            sb.append(this._supportedWidths);
            int widthAlignment = this.videoEncoderInfo.getWidthAlignment();
            sb.append(" or can not be divided by alignment ");
            sb.append(widthAlignment);
            throw new IllegalArgumentException(sb.toString().toString());
        }
        return this._supportedHeights;
    }

    @Override // androidx.camera.video.internal.encoder.VideoEncoderInfo
    public int getWidthAlignment() {
        return this.videoEncoderInfo.getWidthAlignment();
    }

    @Override // androidx.camera.video.internal.encoder.VideoEncoderInfo
    public int getHeightAlignment() {
        return this.videoEncoderInfo.getHeightAlignment();
    }

    @Override // androidx.camera.video.internal.encoder.VideoEncoderInfo
    public Range<Integer> getSupportedBitrateRange() {
        return this.videoEncoderInfo.getSupportedBitrateRange();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addExtraSupportedSize(Size size) {
        this.extraSupportedSizes.add(size);
    }

    @Metadata(m876d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001a\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000e"}, m877d2 = {"Landroidx/camera/video/internal/workaround/VideoEncoderInfoWrapper$Companion;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "TAG", _UrlKt.FRAGMENT_ENCODE_SET, "WIDTH_4KDCI", _UrlKt.FRAGMENT_ENCODE_SET, "HEIGHT_4KDCI", "from", "Landroidx/camera/video/internal/encoder/VideoEncoderInfo;", "videoEncoderInfo", "validSizeToCheck", "Landroid/util/Size;", "camera-video"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final VideoEncoderInfo from(VideoEncoderInfo videoEncoderInfo, Size validSizeToCheck) {
            if (!(videoEncoderInfo instanceof VideoEncoderInfoWrapper)) {
                if (DeviceQuirks.get(MediaCodecInfoReportIncorrectInfoQuirk.class) == null) {
                    if (validSizeToCheck != null && !videoEncoderInfo.isSizeSupportedAllowSwapping(validSizeToCheck.getWidth(), validSizeToCheck.getHeight())) {
                        Logger.m79w("VideoEncoderInfoWrapper", "Detected that the device does not support a size " + validSizeToCheck + " that should be valid in widths/heights = " + videoEncoderInfo.getSupportedWidths() + '/' + videoEncoderInfo.getSupportedHeights());
                        videoEncoderInfo = new VideoEncoderInfoWrapper(videoEncoderInfo, null);
                    }
                } else {
                    videoEncoderInfo = new VideoEncoderInfoWrapper(videoEncoderInfo, null);
                }
            }
            if (validSizeToCheck != null && (videoEncoderInfo instanceof VideoEncoderInfoWrapper)) {
                ((VideoEncoderInfoWrapper) videoEncoderInfo).addExtraSupportedSize(validSizeToCheck);
            }
            return videoEncoderInfo;
        }
    }
}
