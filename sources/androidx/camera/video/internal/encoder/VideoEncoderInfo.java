package androidx.camera.video.internal.encoder;

import android.util.Range;
import kotlin.Metadata;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\bf\u0018\u00002\u00020\u0001:\u0001\u0016J\b\u0010\u0002\u001a\u00020\u0003H&J\u0018\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H&J\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016J\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\nH&J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\nH&J\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\n2\u0006\u0010\u0007\u001a\u00020\u0006H&J\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\n2\u0006\u0010\u0005\u001a\u00020\u0006H&R\u0012\u0010\u000e\u001a\u00020\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0011\u001a\u00020\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0010R\u0018\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00060\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0017À\u0006\u0001"}, m877d2 = {"Landroidx/camera/video/internal/encoder/VideoEncoderInfo;", "Landroidx/camera/video/internal/encoder/EncoderInfo;", "canSwapWidthHeight", _UrlKt.FRAGMENT_ENCODE_SET, "isSizeSupported", "width", _UrlKt.FRAGMENT_ENCODE_SET, "height", "isSizeSupportedAllowSwapping", "getSupportedWidths", "Landroid/util/Range;", "getSupportedHeights", "getSupportedWidthsFor", "getSupportedHeightsFor", "widthAlignment", "getWidthAlignment", "()I", "heightAlignment", "getHeightAlignment", "supportedBitrateRange", "getSupportedBitrateRange", "()Landroid/util/Range;", "Finder", "camera-video"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
public interface VideoEncoderInfo extends EncoderInfo {

    @Metadata(m876d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bæ\u0080\u0001\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0006À\u0006\u0001"}, m877d2 = {"Landroidx/camera/video/internal/encoder/VideoEncoderInfo$Finder;", _UrlKt.FRAGMENT_ENCODE_SET, "find", "Landroidx/camera/video/internal/encoder/VideoEncoderInfo;", "mimeType", _UrlKt.FRAGMENT_ENCODE_SET, "camera-video"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    public interface Finder {
        VideoEncoderInfo find(String mimeType);
    }

    boolean canSwapWidthHeight();

    int getHeightAlignment();

    Range<Integer> getSupportedBitrateRange();

    Range<Integer> getSupportedHeights();

    Range<Integer> getSupportedHeightsFor(int width);

    Range<Integer> getSupportedWidths();

    Range<Integer> getSupportedWidthsFor(int height);

    int getWidthAlignment();

    boolean isSizeSupported(int width, int height);

    default boolean isSizeSupportedAllowSwapping(int width, int height) {
        if (isSizeSupported(width, height)) {
            return true;
        }
        return canSwapWidthHeight() && isSizeSupported(height, width);
    }
}
