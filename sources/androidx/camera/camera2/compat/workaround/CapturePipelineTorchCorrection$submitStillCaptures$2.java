package androidx.camera.camera2.compat.workaround;

import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: loaded from: classes3.dex */
final class CapturePipelineTorchCorrection$submitStillCaptures$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ List $deferredResults;
    int label;
    final /* synthetic */ CapturePipelineTorchCorrection this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CapturePipelineTorchCorrection$submitStillCaptures$2(List list, CapturePipelineTorchCorrection capturePipelineTorchCorrection, Continuation continuation) {
        super(2, continuation);
        this.$deferredResults = list;
        this.this$0 = capturePipelineTorchCorrection;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CapturePipelineTorchCorrection$submitStillCaptures$2(this.$deferredResults, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((CapturePipelineTorchCorrection$submitStillCaptures$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x007f, code lost:
    
        if (r12.join(r11) != r0) goto L24;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r11.label
            java.lang.String r2 = "CXCP"
            r3 = 3
            r4 = 2
            r5 = 1
            if (r1 == 0) goto L27
            if (r1 == r5) goto L23
            if (r1 == r4) goto L1f
            if (r1 != r3) goto L17
            kotlin.ResultKt.throwOnFailure(r12)
            goto L82
        L17:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r0)
            throw r12
        L1f:
            kotlin.ResultKt.throwOnFailure(r12)
            goto L65
        L23:
            kotlin.ResultKt.throwOnFailure(r12)
            goto L37
        L27:
            kotlin.ResultKt.throwOnFailure(r12)
            java.util.List r12 = r11.$deferredResults
            java.util.Collection r12 = (java.util.Collection) r12
            r11.label = r5
            java.lang.Object r12 = kotlinx.coroutines.AwaitKt.joinAll(r12, r11)
            if (r12 != r0) goto L37
            goto L81
        L37:
            androidx.camera.camera2.impl.Camera2Logger r12 = androidx.camera.camera2.impl.Camera2Logger.INSTANCE
            boolean r12 = androidx.camera.core.Logger.isDebugEnabled(r2)
            if (r12 == 0) goto L48
            java.lang.String r12 = androidx.camera.camera2.impl.Camera2Logger.access$getTRUNCATED_TAG$p()
            java.lang.String r1 = "Re-enable Torch to correct the Torch state"
            android.util.Log.d(r12, r1)
        L48:
            androidx.camera.camera2.compat.workaround.CapturePipelineTorchCorrection r12 = r11.this$0
            androidx.camera.camera2.impl.TorchControl r5 = androidx.camera.camera2.compat.workaround.CapturePipelineTorchCorrection.access$getTorchControl$p(r12)
            androidx.camera.camera2.impl.TorchControl$TorchMode$Companion r12 = androidx.camera.camera2.impl.TorchControl.TorchMode.Companion
            int r6 = r12.m1462getOFFIRs_R8()
            r9 = 6
            r10 = 0
            r7 = 0
            r8 = 0
            kotlinx.coroutines.Deferred r12 = androidx.camera.camera2.impl.TorchControl.m1452setTorchAsyncOup_wC0$camera_camera2$default(r5, r6, r7, r8, r9, r10)
            r11.label = r4
            java.lang.Object r12 = r12.join(r11)
            if (r12 != r0) goto L65
            goto L81
        L65:
            androidx.camera.camera2.compat.workaround.CapturePipelineTorchCorrection r12 = r11.this$0
            androidx.camera.camera2.impl.TorchControl r4 = androidx.camera.camera2.compat.workaround.CapturePipelineTorchCorrection.access$getTorchControl$p(r12)
            androidx.camera.camera2.impl.TorchControl$TorchMode$Companion r12 = androidx.camera.camera2.impl.TorchControl.TorchMode.Companion
            int r5 = r12.m1464getUSED_AS_FLASHIRs_R8()
            r8 = 6
            r9 = 0
            r6 = 0
            r7 = 0
            kotlinx.coroutines.Deferred r12 = androidx.camera.camera2.impl.TorchControl.m1452setTorchAsyncOup_wC0$camera_camera2$default(r4, r5, r6, r7, r8, r9)
            r11.label = r3
            java.lang.Object r12 = r12.join(r11)
            if (r12 != r0) goto L82
        L81:
            return r0
        L82:
            androidx.camera.camera2.impl.Camera2Logger r12 = androidx.camera.camera2.impl.Camera2Logger.INSTANCE
            boolean r12 = androidx.camera.core.Logger.isDebugEnabled(r2)
            if (r12 == 0) goto L93
            java.lang.String r12 = androidx.camera.camera2.impl.Camera2Logger.access$getTRUNCATED_TAG$p()
            java.lang.String r0 = "Re-enable Torch to correct the Torch state, done"
            android.util.Log.d(r12, r0)
        L93:
            kotlin.Unit r12 = kotlin.Unit.INSTANCE
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.compat.workaround.CapturePipelineTorchCorrection$submitStillCaptures$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
