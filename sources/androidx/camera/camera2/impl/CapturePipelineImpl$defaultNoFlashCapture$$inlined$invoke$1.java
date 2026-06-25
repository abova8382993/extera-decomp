package androidx.camera.camera2.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.CoroutineScope;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes3.dex */
@Metadata(m876d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, m877d2 = {"Lkotlinx/coroutines/CoroutineScope;", _UrlKt.FRAGMENT_ENCODE_SET, "<anonymous>", "(Lkotlinx/coroutines/CoroutineScope;)V"}, m878k = 3, m879mv = {2, 1, 0})
@DebugMetadata(m895c = "androidx.camera.camera2.impl.CapturePipelineImpl$defaultNoFlashCapture$$inlined$invoke$1", m896f = "CapturePipeline.kt", m897i = {}, m898l = {312, 885}, m899m = "invokeSuspend", m900n = {}, m902s = {}, m903v = 1)
@SourceDebugExtension({"SMAP\nCapturePipeline.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CapturePipeline.kt\nandroidx/camera/camera2/impl/CapturePipelineImpl$invoke$7$1\n+ 2 Camera2Logger.kt\nandroidx/camera/camera2/impl/Camera2Logger\n+ 3 CapturePipeline.kt\nandroidx/camera/camera2/impl/CapturePipelineImpl\n*L\n1#1,870:1\n85#2,4:871\n85#2,4:875\n85#2,4:881\n85#2,4:887\n383#3,2:879\n385#3,2:885\n388#3:891\n*S KotlinDebug\n*F\n+ 1 CapturePipeline.kt\nandroidx/camera/camera2/impl/CapturePipelineImpl$invoke$7$1\n+ 2 CapturePipeline.kt\nandroidx/camera/camera2/impl/CapturePipelineImpl\n*L\n308#1:871,4\n313#1:875,4\n384#2:881,4\n386#2:887,4\n*E\n"})
public final class CapturePipelineImpl$defaultNoFlashCapture$$inlined$invoke$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
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
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new CapturePipelineImpl$defaultNoFlashCapture$$inlined$invoke$1(this.$captureSignal, continuation, this.$lock3ARequired$inlined, this.this$0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((CapturePipelineImpl$defaultNoFlashCapture$$inlined$invoke$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0070, code lost:
    
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
            if (r1 == 0) goto L1f
            if (r1 == r3) goto L1b
            if (r1 != r2) goto L14
            kotlin.ResultKt.throwOnFailure(r8)
            goto L73
        L14:
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r7)
            r7 = 0
            return r7
        L1b:
            kotlin.ResultKt.throwOnFailure(r8)
            goto L40
        L1f:
            kotlin.ResultKt.throwOnFailure(r8)
            androidx.camera.camera2.impl.Camera2Logger r8 = androidx.camera.camera2.impl.Camera2Logger.INSTANCE
            boolean r8 = androidx.camera.core.Logger.isDebugEnabled(r4)
            if (r8 == 0) goto L33
            java.lang.String r8 = androidx.camera.camera2.impl.Camera2Logger.access$getTRUNCATED_TAG$p()
            java.lang.String r1 = "CapturePipeline#List<PipelineTask>.invoke: Waiting for POST_CAPTURE signal"
            android.util.Log.d(r8, r1)
        L33:
            java.util.List r8 = r7.$captureSignal
            java.util.Collection r8 = (java.util.Collection) r8
            r7.label = r3
            java.lang.Object r8 = kotlinx.coroutines.AwaitKt.joinAll(r8, r7)
            if (r8 != r0) goto L40
            goto L72
        L40:
            androidx.camera.camera2.impl.Camera2Logger r8 = androidx.camera.camera2.impl.Camera2Logger.INSTANCE
            boolean r8 = androidx.camera.core.Logger.isDebugEnabled(r4)
            if (r8 == 0) goto L51
            java.lang.String r8 = androidx.camera.camera2.impl.Camera2Logger.access$getTRUNCATED_TAG$p()
            java.lang.String r1 = "CapturePipeline#List<PipelineTask>.invoke: Waiting for POST_CAPTURE signal done"
            android.util.Log.d(r8, r1)
        L51:
            boolean r8 = r7.$lock3ARequired$inlined
            if (r8 == 0) goto L84
            boolean r8 = androidx.camera.core.Logger.isDebugEnabled(r4)
            if (r8 == 0) goto L64
            java.lang.String r8 = androidx.camera.camera2.impl.Camera2Logger.access$getTRUNCATED_TAG$p()
            java.lang.String r1 = "CapturePipeline#defaultNoFlashCapture: Unlocking 3A"
            android.util.Log.d(r8, r1)
        L64:
            androidx.camera.camera2.impl.CapturePipelineImpl r8 = r7.this$0
            long r5 = androidx.camera.camera2.impl.CapturePipelineKt.access$getCHECK_3A_TIMEOUT_IN_NS$p()
            r7.label = r2
            java.lang.Object r7 = androidx.camera.camera2.impl.CapturePipelineImpl.access$unlockAf(r8, r5, r7)
            if (r7 != r0) goto L73
        L72:
            return r0
        L73:
            androidx.camera.camera2.impl.Camera2Logger r7 = androidx.camera.camera2.impl.Camera2Logger.INSTANCE
            boolean r7 = androidx.camera.core.Logger.isDebugEnabled(r4)
            if (r7 == 0) goto L84
            java.lang.String r7 = androidx.camera.camera2.impl.Camera2Logger.access$getTRUNCATED_TAG$p()
            java.lang.String r8 = "CapturePipeline#defaultNoFlashCapture: Unlocking 3A done"
            android.util.Log.d(r7, r8)
        L84:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl$defaultNoFlashCapture$$inlined$invoke$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
