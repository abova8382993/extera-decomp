package androidx.camera.video.internal;

import kotlin.Metadata;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\t\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0001\u0005J\u000f\u0010\u0003\u001a\u00020\u0002H&¢\u0006\u0004\b\u0003\u0010\u0004ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0006À\u0006\u0001"}, m877d2 = {"Landroidx/camera/video/internal/OutputStorage;", _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, "getAvailableBytes", "()J", "Factory", "camera-video"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
public interface OutputStorage {

    @Metadata(m876d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\bf\u0018\u00002\u00020\u0001ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0002À\u0006\u0001"}, m877d2 = {"Landroidx/camera/video/internal/OutputStorage$Factory;", _UrlKt.FRAGMENT_ENCODE_SET, "camera-video"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    public interface Factory {
    }

    long getAvailableBytes();
}
