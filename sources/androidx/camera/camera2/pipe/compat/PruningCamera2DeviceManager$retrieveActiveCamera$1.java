package androidx.camera.camera2.pipe.compat;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
@DebugMetadata(m895c = "androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager", m896f = "Camera2DeviceManager.kt", m897i = {0, 0, 0, 1, 1}, m898l = {526, 533}, m899m = "retrieveActiveCamera-RzXb1QE", m900n = {"cameraId", "requestOpen", "activeCamera", "cameraId", "requestOpen"}, m902s = {"L$0", "L$1", "L$3", "L$0", "L$1"}, m903v = 1)
public final class PruningCamera2DeviceManager$retrieveActiveCamera$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ PruningCamera2DeviceManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PruningCamera2DeviceManager$retrieveActiveCamera$1(PruningCamera2DeviceManager pruningCamera2DeviceManager, Continuation<? super PruningCamera2DeviceManager$retrieveActiveCamera$1> continuation) {
        super(continuation);
        this.this$0 = pruningCamera2DeviceManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.m1738retrieveActiveCameraRzXb1QE(null, null, this);
    }
}
