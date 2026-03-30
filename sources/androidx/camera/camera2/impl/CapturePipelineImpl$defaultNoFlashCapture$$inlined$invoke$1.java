package androidx.camera.camera2.impl;

import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: loaded from: classes3.dex */
public final class CapturePipelineImpl$defaultNoFlashCapture$$inlined$invoke$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ List $captureSignal;
    final /* synthetic */ boolean $lock3ARequired$inlined;
    int label;
    final /* synthetic */ CapturePipelineImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CapturePipelineImpl$defaultNoFlashCapture$$inlined$invoke$1(List list, Continuation continuation, boolean z, CapturePipelineImpl capturePipelineImpl) {
        super(2, continuation);
        this.$captureSignal = list;
        this.$lock3ARequired$inlined = z;
        this.this$0 = capturePipelineImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CapturePipelineImpl$defaultNoFlashCapture$$inlined$invoke$1(this.$captureSignal, continuation, this.$lock3ARequired$inlined, this.this$0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((CapturePipelineImpl$defaultNoFlashCapture$$inlined$invoke$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0071, code lost:
    
        if (r8.unlockAf(r5, r7) == r0) goto L26;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r7.label
            r2 = 2
            r3 = 1
            java.lang.String r4 = "CXCP"
            if (r1 == 0) goto L20
            if (r1 == r3) goto L1c
            if (r1 != r2) goto L14
            kotlin.ResultKt.throwOnFailure(r8)
            goto L74
        L14:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r0)
            throw r8
        L1c:
            kotlin.ResultKt.throwOnFailure(r8)
            goto L41
        L20:
            kotlin.ResultKt.throwOnFailure(r8)
            androidx.camera.camera2.impl.Camera2Logger r8 = androidx.camera.camera2.impl.Camera2Logger.INSTANCE
            boolean r8 = androidx.camera.core.Logger.isDebugEnabled(r4)
            if (r8 == 0) goto L34
            java.lang.String r8 = androidx.camera.camera2.impl.Camera2Logger.access$getTRUNCATED_TAG$p()
            java.lang.String r1 = "CapturePipeline#List<PipelineTask>.invoke: Waiting for POST_CAPTURE signal"
            android.util.Log.d(r8, r1)
        L34:
            java.util.List r8 = r7.$captureSignal
            java.util.Collection r8 = (java.util.Collection) r8
            r7.label = r3
            java.lang.Object r8 = kotlinx.coroutines.AwaitKt.joinAll(r8, r7)
            if (r8 != r0) goto L41
            goto L73
        L41:
            androidx.camera.camera2.impl.Camera2Logger r8 = androidx.camera.camera2.impl.Camera2Logger.INSTANCE
            boolean r8 = androidx.camera.core.Logger.isDebugEnabled(r4)
            if (r8 == 0) goto L52
            java.lang.String r8 = androidx.camera.camera2.impl.Camera2Logger.access$getTRUNCATED_TAG$p()
            java.lang.String r1 = "CapturePipeline#List<PipelineTask>.invoke: Waiting for POST_CAPTURE signal done"
            android.util.Log.d(r8, r1)
        L52:
            boolean r8 = r7.$lock3ARequired$inlined
            if (r8 == 0) goto L85
            boolean r8 = androidx.camera.core.Logger.isDebugEnabled(r4)
            if (r8 == 0) goto L65
            java.lang.String r8 = androidx.camera.camera2.impl.Camera2Logger.access$getTRUNCATED_TAG$p()
            java.lang.String r1 = "CapturePipeline#defaultNoFlashCapture: Unlocking 3A"
            android.util.Log.d(r8, r1)
        L65:
            androidx.camera.camera2.impl.CapturePipelineImpl r8 = r7.this$0
            long r5 = androidx.camera.camera2.impl.CapturePipelineKt.access$getCHECK_3A_TIMEOUT_IN_NS$p()
            r7.label = r2
            java.lang.Object r8 = androidx.camera.camera2.impl.CapturePipelineImpl.access$unlockAf(r8, r5, r7)
            if (r8 != r0) goto L74
        L73:
            return r0
        L74:
            androidx.camera.camera2.impl.Camera2Logger r8 = androidx.camera.camera2.impl.Camera2Logger.INSTANCE
            boolean r8 = androidx.camera.core.Logger.isDebugEnabled(r4)
            if (r8 == 0) goto L85
            java.lang.String r8 = androidx.camera.camera2.impl.Camera2Logger.access$getTRUNCATED_TAG$p()
            java.lang.String r0 = "CapturePipeline#defaultNoFlashCapture: Unlocking 3A done"
            android.util.Log.d(r8, r0)
        L85:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl$defaultNoFlashCapture$$inlined$invoke$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
