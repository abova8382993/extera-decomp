package androidx.camera.camera2.pipe.compat;

import androidx.camera.camera2.pipe.core.DurationNs;
import kotlin.Metadata;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0003\"\u0014\u0010\u0001\u001a\u00020\u00008\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0001\u0010\u0002\"\u0014\u0010\u0003\u001a\u00020\u00008\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010\u0002\"\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00000\u00048\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, m877d2 = {"Landroidx/camera/camera2/pipe/core/DurationNs;", "defaultCameraRetryTimeoutNs", "J", "activeResumeCameraRetryTimeoutNs", _UrlKt.FRAGMENT_ENCODE_SET, "activeResumeCameraRetryThresholds", "[Landroidx/camera/camera2/pipe/core/DurationNs;", "camera-camera2-pipe"}, m878k = 2, m879mv = {2, 1, 0}, m881xi = 48)
public abstract class RetryingCameraStateOpenerKt {
    private static final long defaultCameraRetryTimeoutNs = DurationNs.m1765constructorimpl(RealConnection.IDLE_CONNECTION_HEALTHY_NS);
    private static final long activeResumeCameraRetryTimeoutNs = DurationNs.m1765constructorimpl(1800000000000L);
    private static final DurationNs[] activeResumeCameraRetryThresholds = {DurationNs.m1763boximpl(DurationNs.m1765constructorimpl(120000000000L)), DurationNs.m1763boximpl(DurationNs.m1765constructorimpl(300000000000L))};
}
