package androidx.camera.camera2.compat.workaround;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes3.dex */
@Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
@DebugMetadata(m895c = "androidx.camera.camera2.compat.workaround.CapturePipelineTorchCorrection$submitStillCaptures$2", m896f = "CapturePipelineTorchCorrection.kt", m897i = {}, m898l = {86, 88, 89}, m899m = "invokeSuspend", m900n = {}, m902s = {}, m903v = 1)
@SourceDebugExtension({"SMAP\nCapturePipelineTorchCorrection.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CapturePipelineTorchCorrection.kt\nandroidx/camera/camera2/compat/workaround/CapturePipelineTorchCorrection$submitStillCaptures$2\n+ 2 Camera2Logger.kt\nandroidx/camera/camera2/impl/Camera2Logger\n*L\n1#1,131:1\n85#2,4:132\n85#2,4:136\n*S KotlinDebug\n*F\n+ 1 CapturePipelineTorchCorrection.kt\nandroidx/camera/camera2/compat/workaround/CapturePipelineTorchCorrection$submitStillCaptures$2\n*L\n87#1:132,4\n90#1:136,4\n*E\n"})
public final class CapturePipelineTorchCorrection$submitStillCaptures$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ List<Deferred<Void>> $deferredResults;
    int label;
    final /* synthetic */ CapturePipelineTorchCorrection this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public CapturePipelineTorchCorrection$submitStillCaptures$2(List<? extends Deferred<Void>> list, CapturePipelineTorchCorrection capturePipelineTorchCorrection, Continuation<? super CapturePipelineTorchCorrection$submitStillCaptures$2> continuation) {
        super(2, continuation);
        this.$deferredResults = list;
        this.this$0 = capturePipelineTorchCorrection;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new CapturePipelineTorchCorrection$submitStillCaptures$2(this.$deferredResults, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((CapturePipelineTorchCorrection$submitStillCaptures$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x007e, code lost:
    
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
            if (r1 == 0) goto L26
            if (r1 == r5) goto L22
            if (r1 == r4) goto L1e
            if (r1 != r3) goto L17
            kotlin.ResultKt.throwOnFailure(r12)
            goto L81
        L17:
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r11)
            r11 = 0
            return r11
        L1e:
            kotlin.ResultKt.throwOnFailure(r12)
            goto L64
        L22:
            kotlin.ResultKt.throwOnFailure(r12)
            goto L36
        L26:
            kotlin.ResultKt.throwOnFailure(r12)
            java.util.List<kotlinx.coroutines.Deferred<java.lang.Void>> r12 = r11.$deferredResults
            java.util.Collection r12 = (java.util.Collection) r12
            r11.label = r5
            java.lang.Object r12 = kotlinx.coroutines.AwaitKt.joinAll(r12, r11)
            if (r12 != r0) goto L36
            goto L80
        L36:
            androidx.camera.camera2.impl.Camera2Logger r12 = androidx.camera.camera2.impl.Camera2Logger.INSTANCE
            boolean r12 = androidx.camera.core.Logger.isDebugEnabled(r2)
            if (r12 == 0) goto L47
            java.lang.String r12 = androidx.camera.camera2.impl.Camera2Logger.access$getTRUNCATED_TAG$p()
            java.lang.String r1 = "Re-enable Torch to correct the Torch state"
            android.util.Log.d(r12, r1)
        L47:
            androidx.camera.camera2.compat.workaround.CapturePipelineTorchCorrection r12 = r11.this$0
            androidx.camera.camera2.impl.TorchControl r5 = androidx.camera.camera2.compat.workaround.CapturePipelineTorchCorrection.access$getTorchControl$p(r12)
            androidx.camera.camera2.impl.TorchControl$TorchMode$Companion r12 = androidx.camera.camera2.impl.TorchControl.TorchMode.INSTANCE
            int r6 = r12.m1356getOFFIRs_R8()
            r9 = 6
            r10 = 0
            r7 = 0
            r8 = 0
            kotlinx.coroutines.Deferred r12 = androidx.camera.camera2.impl.TorchControl.m1346setTorchAsyncOup_wC0$camera_camera2$default(r5, r6, r7, r8, r9, r10)
            r11.label = r4
            java.lang.Object r12 = r12.join(r11)
            if (r12 != r0) goto L64
            goto L80
        L64:
            androidx.camera.camera2.compat.workaround.CapturePipelineTorchCorrection r12 = r11.this$0
            androidx.camera.camera2.impl.TorchControl r4 = androidx.camera.camera2.compat.workaround.CapturePipelineTorchCorrection.access$getTorchControl$p(r12)
            androidx.camera.camera2.impl.TorchControl$TorchMode$Companion r12 = androidx.camera.camera2.impl.TorchControl.TorchMode.INSTANCE
            int r5 = r12.m1358getUSED_AS_FLASHIRs_R8()
            r8 = 6
            r9 = 0
            r6 = 0
            r7 = 0
            kotlinx.coroutines.Deferred r12 = androidx.camera.camera2.impl.TorchControl.m1346setTorchAsyncOup_wC0$camera_camera2$default(r4, r5, r6, r7, r8, r9)
            r11.label = r3
            java.lang.Object r11 = r12.join(r11)
            if (r11 != r0) goto L81
        L80:
            return r0
        L81:
            androidx.camera.camera2.impl.Camera2Logger r11 = androidx.camera.camera2.impl.Camera2Logger.INSTANCE
            boolean r11 = androidx.camera.core.Logger.isDebugEnabled(r2)
            if (r11 == 0) goto L92
            java.lang.String r11 = androidx.camera.camera2.impl.Camera2Logger.access$getTRUNCATED_TAG$p()
            java.lang.String r12 = "Re-enable Torch to correct the Torch state, done"
            android.util.Log.d(r11, r12)
        L92:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.compat.workaround.CapturePipelineTorchCorrection$submitStillCaptures$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
