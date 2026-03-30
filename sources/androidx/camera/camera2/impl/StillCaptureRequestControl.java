package androidx.camera.camera2.impl;

import android.util.Log;
import androidx.camera.camera2.adapter.CoroutineAdaptersKt;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Logger;
import java.util.LinkedList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.AwaitKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexKt;

/* JADX INFO: loaded from: classes3.dex */
public final class StillCaptureRequestControl implements UseCaseCameraControl {
    private UseCaseCameraRequestControl _requestControl;
    private final FlashControl flashControl;
    private final Mutex mutex;
    private final LinkedList pendingRequests;
    private final UseCaseThreads threads;

    public static final class CaptureRequest {
        public abstract List getCaptureConfigs();

        public abstract int getCaptureMode();

        public abstract int getFlashType();

        public abstract CompletableDeferred getResult();
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.StillCaptureRequestControl$submitRequest$1 */
    static final class C01691 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        C01691(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return StillCaptureRequestControl.this.submitRequest(null, null, this);
        }
    }

    public StillCaptureRequestControl(FlashControl flashControl, UseCaseThreads threads) {
        Intrinsics.checkNotNullParameter(flashControl, "flashControl");
        Intrinsics.checkNotNullParameter(threads, "threads");
        this.flashControl = flashControl;
        this.threads = threads;
        this.mutex = MutexKt.Mutex$default(false, 1, null);
        this.pendingRequests = new LinkedList();
    }

    public UseCaseCameraRequestControl getRequestControl() {
        return this._requestControl;
    }

    @Override // androidx.camera.camera2.impl.UseCaseCameraControl
    public void setRequestControl(UseCaseCameraRequestControl useCaseCameraRequestControl) {
        this._requestControl = useCaseCameraRequestControl;
        trySubmitPendingRequests();
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.StillCaptureRequestControl$reset$1 */
    static final class C01681 extends SuspendLambda implements Function2 {
        Object L$0;
        Object L$1;
        int label;

        C01681(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return StillCaptureRequestControl.this.new C01681(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01681) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Mutex mutex;
            StillCaptureRequestControl stillCaptureRequestControl;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                mutex = StillCaptureRequestControl.this.mutex;
                StillCaptureRequestControl stillCaptureRequestControl2 = StillCaptureRequestControl.this;
                this.L$0 = mutex;
                this.L$1 = stillCaptureRequestControl2;
                this.label = 1;
                if (mutex.lock(null, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                stillCaptureRequestControl = stillCaptureRequestControl2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                stillCaptureRequestControl = (StillCaptureRequestControl) this.L$1;
                mutex = (Mutex) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            while (!stillCaptureRequestControl.pendingRequests.isEmpty()) {
                try {
                } catch (Throwable th) {
                    mutex.unlock(null);
                    throw th;
                }
            }
            Unit unit = Unit.INSTANCE;
            mutex.unlock(null);
            return Unit.INSTANCE;
        }
    }

    @Override // androidx.camera.camera2.impl.UseCaseCameraControl
    public void reset() {
        BuildersKt__Builders_commonKt.launch$default(this.threads.getSequentialScope(), null, null, new C01681(null), 3, null);
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.StillCaptureRequestControl$trySubmitPendingRequests$1 */
    static final class C01711 extends SuspendLambda implements Function2 {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        int label;

        C01711(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return StillCaptureRequestControl.this.new C01711(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01711) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:32:0x009c A[Catch: all -> 0x0033, LOOP:0: B:30:0x0092->B:32:0x009c, LOOP_END, TryCatch #0 {all -> 0x0033, blocks: (B:8:0x002a, B:30:0x0092, B:32:0x009c, B:33:0x00a7), top: B:39:0x002a }] */
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
                r2 = 3
                r3 = 2
                r4 = 1
                r5 = 0
                if (r1 == 0) goto L57
                if (r1 == r4) goto L4f
                if (r1 == r3) goto L3e
                if (r1 != r2) goto L36
                java.lang.Object r0 = r7.L$5
                androidx.camera.camera2.impl.StillCaptureRequestControl r0 = (androidx.camera.camera2.impl.StillCaptureRequestControl) r0
                java.lang.Object r1 = r7.L$4
                androidx.camera.camera2.impl.UseCaseCameraRequestControl r1 = (androidx.camera.camera2.impl.UseCaseCameraRequestControl) r1
                java.lang.Object r2 = r7.L$3
                androidx.camera.camera2.impl.StillCaptureRequestControl$CaptureRequest r2 = (androidx.camera.camera2.impl.StillCaptureRequestControl.CaptureRequest) r2
                java.lang.Object r3 = r7.L$2
                androidx.camera.camera2.impl.StillCaptureRequestControl r3 = (androidx.camera.camera2.impl.StillCaptureRequestControl) r3
                java.lang.Object r4 = r7.L$1
                kotlinx.coroutines.sync.Mutex r4 = (kotlinx.coroutines.sync.Mutex) r4
                java.lang.Object r6 = r7.L$0
                androidx.camera.camera2.impl.UseCaseCameraRequestControl r6 = (androidx.camera.camera2.impl.UseCaseCameraRequestControl) r6
                kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L33
                kotlinx.coroutines.Deferred r8 = (kotlinx.coroutines.Deferred) r8     // Catch: java.lang.Throwable -> L33
                androidx.camera.camera2.impl.StillCaptureRequestControl.access$propagateResultOrEnqueueRequest(r0, r8, r2, r1)     // Catch: java.lang.Throwable -> L33
                goto L92
            L33:
                r8 = move-exception
                goto Lad
            L36:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r0)
                throw r8
            L3e:
                java.lang.Object r0 = r7.L$2
                androidx.camera.camera2.impl.StillCaptureRequestControl r0 = (androidx.camera.camera2.impl.StillCaptureRequestControl) r0
                java.lang.Object r1 = r7.L$1
                kotlinx.coroutines.sync.Mutex r1 = (kotlinx.coroutines.sync.Mutex) r1
                java.lang.Object r2 = r7.L$0
                androidx.camera.camera2.impl.UseCaseCameraRequestControl r2 = (androidx.camera.camera2.impl.UseCaseCameraRequestControl) r2
                kotlin.ResultKt.throwOnFailure(r8)
                r3 = r0
                goto L91
            L4f:
                java.lang.Object r1 = r7.L$0
                androidx.camera.camera2.impl.UseCaseCameraRequestControl r1 = (androidx.camera.camera2.impl.UseCaseCameraRequestControl) r1
                kotlin.ResultKt.throwOnFailure(r8)
                goto L70
            L57:
                kotlin.ResultKt.throwOnFailure(r8)
                androidx.camera.camera2.impl.StillCaptureRequestControl r8 = androidx.camera.camera2.impl.StillCaptureRequestControl.this
                androidx.camera.camera2.impl.UseCaseCameraRequestControl r1 = r8.getRequestControl()
                if (r1 != 0) goto L65
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            L65:
                r7.L$0 = r1
                r7.label = r4
                java.lang.Object r8 = r1.awaitSurfaceSetup(r7)
                if (r8 != r0) goto L70
                goto L8e
            L70:
                java.lang.Boolean r8 = (java.lang.Boolean) r8
                boolean r8 = r8.booleanValue()
                if (r8 == 0) goto Lb1
                androidx.camera.camera2.impl.StillCaptureRequestControl r8 = androidx.camera.camera2.impl.StillCaptureRequestControl.this
                kotlinx.coroutines.sync.Mutex r8 = androidx.camera.camera2.impl.StillCaptureRequestControl.access$getMutex$p(r8)
                androidx.camera.camera2.impl.StillCaptureRequestControl r2 = androidx.camera.camera2.impl.StillCaptureRequestControl.this
                r7.L$0 = r1
                r7.L$1 = r8
                r7.L$2 = r2
                r7.label = r3
                java.lang.Object r1 = r8.lock(r5, r7)
                if (r1 != r0) goto L8f
            L8e:
                return r0
            L8f:
                r1 = r8
                r3 = r2
            L91:
                r4 = r1
            L92:
                java.util.LinkedList r8 = androidx.camera.camera2.impl.StillCaptureRequestControl.access$getPendingRequests$p(r3)     // Catch: java.lang.Throwable -> L33
                boolean r8 = r8.isEmpty()     // Catch: java.lang.Throwable -> L33
                if (r8 != 0) goto La7
                java.util.LinkedList r8 = androidx.camera.camera2.impl.StillCaptureRequestControl.access$getPendingRequests$p(r3)     // Catch: java.lang.Throwable -> L33
                java.lang.Object r8 = r8.poll()     // Catch: java.lang.Throwable -> L33
                androidx.camera.camera2.impl.StillCaptureRequestControl$CaptureRequest r8 = (androidx.camera.camera2.impl.StillCaptureRequestControl.CaptureRequest) r8     // Catch: java.lang.Throwable -> L33
                goto L92
            La7:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L33
                r4.unlock(r5)
                goto Lb1
            Lad:
                r4.unlock(r5)
                throw r8
            Lb1:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.StillCaptureRequestControl.C01711.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    private final void trySubmitPendingRequests() {
        BuildersKt__Builders_commonKt.launch$default(this.threads.getSequentialScope(), null, null, new C01711(null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object submitRequest(androidx.camera.camera2.impl.StillCaptureRequestControl.CaptureRequest r7, androidx.camera.camera2.impl.UseCaseCameraRequestControl r8, kotlin.coroutines.Continuation r9) {
        /*
            r6 = this;
            boolean r0 = r9 instanceof androidx.camera.camera2.impl.StillCaptureRequestControl.C01691
            if (r0 == 0) goto L13
            r0 = r9
            androidx.camera.camera2.impl.StillCaptureRequestControl$submitRequest$1 r0 = (androidx.camera.camera2.impl.StillCaptureRequestControl.C01691) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.camera.camera2.impl.StillCaptureRequestControl$submitRequest$1 r0 = new androidx.camera.camera2.impl.StillCaptureRequestControl$submitRequest$1
            r0.<init>(r9)
        L18:
            java.lang.Object r9 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            java.lang.String r3 = "CXCP"
            r4 = 1
            if (r2 == 0) goto L3c
            if (r2 != r4) goto L34
            java.lang.Object r7 = r0.L$1
            r8 = r7
            androidx.camera.camera2.impl.UseCaseCameraRequestControl r8 = (androidx.camera.camera2.impl.UseCaseCameraRequestControl) r8
            java.lang.Object r7 = r0.L$0
            androidx.camera.camera2.impl.StillCaptureRequestControl$CaptureRequest r7 = (androidx.camera.camera2.impl.StillCaptureRequestControl.CaptureRequest) r7
            kotlin.ResultKt.throwOnFailure(r9)
            goto L76
        L34:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L3c:
            kotlin.ResultKt.throwOnFailure(r9)
            androidx.camera.camera2.impl.Camera2Logger r9 = androidx.camera.camera2.impl.Camera2Logger.INSTANCE
            boolean r9 = androidx.camera.core.Logger.isDebugEnabled(r3)
            if (r9 == 0) goto L67
            java.lang.String r9 = androidx.camera.camera2.impl.Camera2Logger.access$getTRUNCATED_TAG$p()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r5 = "StillCaptureRequestControl: submitting "
            r2.append(r5)
            r2.append(r7)
            java.lang.String r5 = " at "
            r2.append(r5)
            r2.append(r8)
            java.lang.String r2 = r2.toString()
            android.util.Log.d(r9, r2)
        L67:
            androidx.camera.camera2.impl.FlashControl r9 = r6.flashControl
            r0.L$0 = r7
            r0.L$1 = r8
            r0.label = r4
            java.lang.Object r9 = r9.awaitFlashModeUpdate(r0)
            if (r9 != r1) goto L76
            return r1
        L76:
            java.lang.Number r9 = (java.lang.Number) r9
            int r9 = r9.intValue()
            androidx.camera.camera2.impl.Camera2Logger r0 = androidx.camera.camera2.impl.Camera2Logger.INSTANCE
            boolean r0 = androidx.camera.core.Logger.isDebugEnabled(r3)
            if (r0 == 0) goto L8d
            java.lang.String r0 = androidx.camera.camera2.impl.Camera2Logger.access$getTRUNCATED_TAG$p()
            java.lang.String r1 = "StillCaptureRequestControl: Issuing single capture"
            android.util.Log.d(r0, r1)
        L8d:
            java.util.List r0 = r7.getCaptureConfigs()
            int r1 = r7.getCaptureMode()
            int r2 = r7.getFlashType()
            java.util.List r8 = r8.issueSingleCaptureAsync(r0, r1, r2, r9)
            androidx.camera.camera2.impl.UseCaseThreads r9 = r6.threads
            kotlinx.coroutines.CoroutineScope r0 = r9.getSequentialScope()
            androidx.camera.camera2.impl.StillCaptureRequestControl$submitRequest$4 r3 = new androidx.camera.camera2.impl.StillCaptureRequestControl$submitRequest$4
            r9 = 0
            r3.<init>(r8, r7, r9)
            r4 = 3
            r5 = 0
            r1 = 0
            r2 = 0
            kotlinx.coroutines.Deferred r7 = kotlinx.coroutines.BuildersKt.async$default(r0, r1, r2, r3, r4, r5)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.StillCaptureRequestControl.submitRequest(androidx.camera.camera2.impl.StillCaptureRequestControl$CaptureRequest, androidx.camera.camera2.impl.UseCaseCameraRequestControl, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.StillCaptureRequestControl$submitRequest$4 */
    static final class C01704 extends SuspendLambda implements Function2 {
        final /* synthetic */ List $deferredList;
        final /* synthetic */ CaptureRequest $request;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01704(List list, CaptureRequest captureRequest, Continuation continuation) {
            super(2, continuation);
            this.$deferredList = list;
            this.$request = captureRequest;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C01704(this.$deferredList, this.$request, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01704) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
                CaptureRequest captureRequest = this.$request;
                if (Logger.isDebugEnabled("CXCP")) {
                    Log.d(Camera2Logger.TRUNCATED_TAG, "StillCaptureRequestControl: Waiting for deferred list from " + captureRequest);
                }
                List list = this.$deferredList;
                this.label = 1;
                obj = AwaitKt.awaitAll(list, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            CaptureRequest captureRequest2 = this.$request;
            Camera2Logger camera2Logger2 = Camera2Logger.INSTANCE;
            if (Logger.isDebugEnabled("CXCP")) {
                Log.d(Camera2Logger.TRUNCATED_TAG, "StillCaptureRequestControl: Waiting for deferred list from " + captureRequest2 + " done");
            }
            return obj;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void propagateResultOrEnqueueRequest(final Deferred deferred, final CaptureRequest captureRequest, final UseCaseCameraRequestControl useCaseCameraRequestControl) {
        deferred.invokeOnCompletion(new Function1() { // from class: androidx.camera.camera2.impl.StillCaptureRequestControl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return StillCaptureRequestControl.propagateResultOrEnqueueRequest$lambda$0(this.f$0, deferred, captureRequest, useCaseCameraRequestControl, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit propagateResultOrEnqueueRequest$lambda$0(StillCaptureRequestControl stillCaptureRequestControl, Deferred deferred, CaptureRequest captureRequest, UseCaseCameraRequestControl useCaseCameraRequestControl, Throwable th) {
        if ((th instanceof ImageCaptureException) && ((ImageCaptureException) th).getImageCaptureError() == 3) {
            BuildersKt__Builders_commonKt.launch$default(stillCaptureRequestControl.threads.getSequentialScope(), null, null, new StillCaptureRequestControl$propagateResultOrEnqueueRequest$1$1(stillCaptureRequestControl, useCaseCameraRequestControl, captureRequest, null), 3, null);
        } else {
            CoroutineAdaptersKt.propagateCompletion(deferred, captureRequest.getResult(), th);
        }
        return Unit.INSTANCE;
    }
}
