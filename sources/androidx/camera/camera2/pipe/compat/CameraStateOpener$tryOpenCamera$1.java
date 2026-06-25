package androidx.camera.camera2.pipe.compat;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
@DebugMetadata(m895c = "androidx.camera.camera2.pipe.compat.CameraStateOpener", m896f = "RetryingCameraStateOpener.kt", m897i = {0, 0, 0, 0, 0}, m898l = {236, 275}, m899m = "tryOpenCamera-7pD7j80$camera_camera2_pipe", m900n = {"cameraId", "camera2DeviceCloser", "audioRestrictionController", "attempts", "requestTimestamp"}, m902s = {"L$0", "L$1", "L$2", "I$0", "J$0"}, m903v = 1)
public final class CameraStateOpener$tryOpenCamera$1 extends ContinuationImpl {
    int I$0;
    long J$0;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ CameraStateOpener this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CameraStateOpener$tryOpenCamera$1(CameraStateOpener cameraStateOpener, Continuation<? super CameraStateOpener$tryOpenCamera$1> continuation) {
        super(continuation);
        this.this$0 = cameraStateOpener;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.m1731tryOpenCamera7pD7j80$camera_camera2_pipe(null, 0, 0L, null, null, this);
    }
}
