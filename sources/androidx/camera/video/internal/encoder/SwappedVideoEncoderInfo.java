package androidx.camera.video.internal.encoder;

import android.util.Range;
import kotlin.Metadata;
import okhttp3.internal.url._UrlKt;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0011\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0004\b\u0003\u0010\u0004J\u001f\u0010\t\u001a\u00020\b2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u0005H\u0016¢\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u000b\u001a\u00020\b2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u0005H\u0016¢\u0006\u0004\b\u000b\u0010\nJ\u0015\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\fH\u0016¢\u0006\u0004\b\r\u0010\u000eJ\u0015\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\fH\u0016¢\u0006\u0004\b\u000f\u0010\u000eJ\u001d\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\f2\u0006\u0010\u0007\u001a\u00020\u0005H\u0016¢\u0006\u0004\b\u0010\u0010\u0011J\u001d\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\f2\u0006\u0010\u0006\u001a\u00020\u0005H\u0016¢\u0006\u0004\b\u0012\u0010\u0011J\u0010\u0010\u0013\u001a\u00020\bH\u0096\u0001¢\u0006\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0002\u001a\u00020\u00018\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0002\u0010\u0015R\u0014\u0010\u0018\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u001a\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0017R\u001a\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00050\f8\u0016X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u000e¨\u0006\u001d"}, m877d2 = {"Landroidx/camera/video/internal/encoder/SwappedVideoEncoderInfo;", "Landroidx/camera/video/internal/encoder/VideoEncoderInfo;", "videoEncoderInfo", "<init>", "(Landroidx/camera/video/internal/encoder/VideoEncoderInfo;)V", _UrlKt.FRAGMENT_ENCODE_SET, "width", "height", _UrlKt.FRAGMENT_ENCODE_SET, "isSizeSupported", "(II)Z", "isSizeSupportedAllowSwapping", "Landroid/util/Range;", "getSupportedWidths", "()Landroid/util/Range;", "getSupportedHeights", "getSupportedWidthsFor", "(I)Landroid/util/Range;", "getSupportedHeightsFor", "canSwapWidthHeight", "()Z", "Landroidx/camera/video/internal/encoder/VideoEncoderInfo;", "getWidthAlignment", "()I", "widthAlignment", "getHeightAlignment", "heightAlignment", "getSupportedBitrateRange", "supportedBitrateRange", "camera-video"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
public final class SwappedVideoEncoderInfo implements VideoEncoderInfo {
    private final VideoEncoderInfo videoEncoderInfo;

    @Override // androidx.camera.video.internal.encoder.VideoEncoderInfo
    public boolean canSwapWidthHeight() {
        return this.videoEncoderInfo.canSwapWidthHeight();
    }

    @Override // androidx.camera.video.internal.encoder.VideoEncoderInfo
    public Range<Integer> getSupportedBitrateRange() {
        return this.videoEncoderInfo.getSupportedBitrateRange();
    }

    public SwappedVideoEncoderInfo(VideoEncoderInfo videoEncoderInfo) {
        this.videoEncoderInfo = videoEncoderInfo;
        if (videoEncoderInfo.canSwapWidthHeight()) {
            return;
        }
        g$$ExternalSyntheticBUOutline1.m207m("Failed requirement.");
        throw null;
    }

    @Override // androidx.camera.video.internal.encoder.VideoEncoderInfo
    public boolean isSizeSupported(int width, int height) {
        return this.videoEncoderInfo.isSizeSupported(height, width);
    }

    @Override // androidx.camera.video.internal.encoder.VideoEncoderInfo
    public boolean isSizeSupportedAllowSwapping(int width, int height) {
        return this.videoEncoderInfo.isSizeSupportedAllowSwapping(height, width);
    }

    @Override // androidx.camera.video.internal.encoder.VideoEncoderInfo
    public Range<Integer> getSupportedWidths() {
        return this.videoEncoderInfo.getSupportedHeights();
    }

    @Override // androidx.camera.video.internal.encoder.VideoEncoderInfo
    public Range<Integer> getSupportedHeights() {
        return this.videoEncoderInfo.getSupportedWidths();
    }

    @Override // androidx.camera.video.internal.encoder.VideoEncoderInfo
    public Range<Integer> getSupportedWidthsFor(int height) {
        return this.videoEncoderInfo.getSupportedHeightsFor(height);
    }

    @Override // androidx.camera.video.internal.encoder.VideoEncoderInfo
    public Range<Integer> getSupportedHeightsFor(int width) {
        return this.videoEncoderInfo.getSupportedWidthsFor(width);
    }

    @Override // androidx.camera.video.internal.encoder.VideoEncoderInfo
    public int getWidthAlignment() {
        return this.videoEncoderInfo.getHeightAlignment();
    }

    @Override // androidx.camera.video.internal.encoder.VideoEncoderInfo
    public int getHeightAlignment() {
        return this.videoEncoderInfo.getWidthAlignment();
    }
}
