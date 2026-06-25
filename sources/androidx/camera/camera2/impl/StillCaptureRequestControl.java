package androidx.camera.camera2.impl;

import android.util.Log;
import androidx.camera.camera2.adapter.CoroutineAdaptersKt;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.CaptureConfig;
import com.android.p006dx.p009io.Opcodes;
import java.util.LinkedList;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.AwaitKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexKt;
import okhttp3.internal.url._UrlKt;
import okio.Segment$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes3.dex */
@Metadata(m876d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\b\u0007\u0018\u00002\u00020\u0001:\u0001(B\u0019\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0006\u0010\u0007J\u000f\u0010\t\u001a\u00020\bH\u0002¢\u0006\u0004\b\t\u0010\nJ.\u0010\u0012\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u00100\u000f2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\rH\u0082@¢\u0006\u0004\b\u0012\u0010\u0013J1\u0010\u0016\u001a\u00020\b*\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u00100\u000f2\u0006\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\rH\u0002¢\u0006\u0004\b\u0016\u0010\u0017J\u000f\u0010\u0018\u001a\u00020\bH\u0016¢\u0006\u0004\b\u0018\u0010\nR\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010\u0019R\u0014\u0010\u0005\u001a\u00020\u00048\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0005\u0010\u001aR\u0014\u0010\u001c\u001a\u00020\u001b8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u001c\u0010\u001dR\u0018\u0010\u001e\u001a\u0004\u0018\u00010\r8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u001e\u0010\u001fR\u001a\u0010!\u001a\b\u0012\u0004\u0012\u00020\u000b0 8\u0002X\u0083\u0004¢\u0006\u0006\n\u0004\b!\u0010\"R(\u0010\u000e\u001a\u0004\u0018\u00010\r2\b\u0010#\u001a\u0004\u0018\u00010\r8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'¨\u0006)"}, m877d2 = {"Landroidx/camera/camera2/impl/StillCaptureRequestControl;", "Landroidx/camera/camera2/impl/UseCaseCameraControl;", "Landroidx/camera/camera2/impl/FlashControl;", "flashControl", "Landroidx/camera/camera2/impl/UseCaseThreads;", "threads", "<init>", "(Landroidx/camera/camera2/impl/FlashControl;Landroidx/camera/camera2/impl/UseCaseThreads;)V", _UrlKt.FRAGMENT_ENCODE_SET, "trySubmitPendingRequests", "()V", "Landroidx/camera/camera2/impl/StillCaptureRequestControl$CaptureRequest;", "request", "Landroidx/camera/camera2/impl/UseCaseCameraRequestControl;", "requestControl", "Lkotlinx/coroutines/Deferred;", _UrlKt.FRAGMENT_ENCODE_SET, "Ljava/lang/Void;", "submitRequest", "(Landroidx/camera/camera2/impl/StillCaptureRequestControl$CaptureRequest;Landroidx/camera/camera2/impl/UseCaseCameraRequestControl;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "submittedRequest", "currentRequestControl", "propagateResultOrEnqueueRequest", "(Lkotlinx/coroutines/Deferred;Landroidx/camera/camera2/impl/StillCaptureRequestControl$CaptureRequest;Landroidx/camera/camera2/impl/UseCaseCameraRequestControl;)V", "reset", "Landroidx/camera/camera2/impl/FlashControl;", "Landroidx/camera/camera2/impl/UseCaseThreads;", "Lkotlinx/coroutines/sync/Mutex;", "mutex", "Lkotlinx/coroutines/sync/Mutex;", "_requestControl", "Landroidx/camera/camera2/impl/UseCaseCameraRequestControl;", "Ljava/util/LinkedList;", "pendingRequests", "Ljava/util/LinkedList;", "value", "getRequestControl", "()Landroidx/camera/camera2/impl/UseCaseCameraRequestControl;", "setRequestControl", "(Landroidx/camera/camera2/impl/UseCaseCameraRequestControl;)V", "CaptureRequest", "camera-camera2"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nStillCaptureRequestControl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StillCaptureRequestControl.kt\nandroidx/camera/camera2/impl/StillCaptureRequestControl\n+ 2 Camera2Logger.kt\nandroidx/camera/camera2/impl/Camera2Logger\n*L\n1#1,216:1\n85#2,4:217\n85#2,4:221\n*S KotlinDebug\n*F\n+ 1 StillCaptureRequestControl.kt\nandroidx/camera/camera2/impl/StillCaptureRequestControl\n*L\n140#1:217,4\n145#1:221,4\n*E\n"})
public final class StillCaptureRequestControl implements UseCaseCameraControl {
    private UseCaseCameraRequestControl _requestControl;
    private final FlashControl flashControl;
    private final Mutex mutex = MutexKt.Mutex$default(false, 1, null);
    private final LinkedList<CaptureRequest> pendingRequests = new LinkedList<>();
    private final UseCaseThreads threads;

    @Metadata(m876d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\b\u0018\u00002\u00020\u0001R\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006¢\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\t\u001a\u00020\b8\u0006¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\r\u001a\u00020\b8\u0006¢\u0006\f\n\u0004\b\r\u0010\n\u001a\u0004\b\u000e\u0010\fR%\u0010\u0011\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u00020\u000f8\u0006¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0015"}, m877d2 = {"Landroidx/camera/camera2/impl/StillCaptureRequestControl$CaptureRequest;", _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/core/impl/CaptureConfig;", "captureConfigs", "Ljava/util/List;", "getCaptureConfigs", "()Ljava/util/List;", _UrlKt.FRAGMENT_ENCODE_SET, "captureMode", "I", "getCaptureMode", "()I", "flashType", "getFlashType", "Lkotlinx/coroutines/CompletableDeferred;", "Ljava/lang/Void;", "result", "Lkotlinx/coroutines/CompletableDeferred;", "getResult", "()Lkotlinx/coroutines/CompletableDeferred;", "camera-camera2"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    public static final /* data */ class CaptureRequest {
        public abstract List<CaptureConfig> getCaptureConfigs();

        public abstract int getCaptureMode();

        public abstract int getFlashType();

        public abstract CompletableDeferred<List<Void>> getResult();
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.StillCaptureRequestControl$submitRequest$1 */
    @Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.StillCaptureRequestControl", m896f = "StillCaptureRequestControl.kt", m897i = {0, 0}, m898l = {144}, m899m = "submitRequest", m900n = {"request", "requestControl"}, m902s = {"L$0", "L$1"}, m903v = 1)
    public static final class C01701 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C01701(Continuation<? super C01701> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return StillCaptureRequestControl.this.submitRequest(null, null, this);
        }
    }

    public StillCaptureRequestControl(FlashControl flashControl, UseCaseThreads useCaseThreads) {
        this.flashControl = flashControl;
        this.threads = useCaseThreads;
    }

    /* JADX INFO: renamed from: getRequestControl, reason: from getter */
    public UseCaseCameraRequestControl get_requestControl() {
        return this._requestControl;
    }

    @Override // androidx.camera.camera2.impl.UseCaseCameraControl
    public void setRequestControl(UseCaseCameraRequestControl useCaseCameraRequestControl) {
        this._requestControl = useCaseCameraRequestControl;
        trySubmitPendingRequests();
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.StillCaptureRequestControl$reset$1 */
    @Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.StillCaptureRequestControl$reset$1", m896f = "StillCaptureRequestControl.kt", m897i = {0}, m898l = {Opcodes.OR_INT_LIT8}, m899m = "invokeSuspend", m900n = {"$this$withLock_u24default$iv"}, m902s = {"L$0"}, m903v = 1)
    @SourceDebugExtension({"SMAP\nStillCaptureRequestControl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StillCaptureRequestControl.kt\nandroidx/camera/camera2/impl/StillCaptureRequestControl$reset$1\n+ 2 Mutex.kt\nkotlinx/coroutines/sync/MutexKt\n*L\n1#1,216:1\n116#2,11:217\n*S KotlinDebug\n*F\n+ 1 StillCaptureRequestControl.kt\nandroidx/camera/camera2/impl/StillCaptureRequestControl$reset$1\n*L\n72#1:217,11\n*E\n"})
    public static final class C01691 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        Object L$0;
        Object L$1;
        int label;

        public C01691(Continuation<? super C01691> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return StillCaptureRequestControl.this.new C01691(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C01691) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Mutex mutex;
            StillCaptureRequestControl stillCaptureRequestControl;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Mutex mutex2 = StillCaptureRequestControl.this.mutex;
                StillCaptureRequestControl stillCaptureRequestControl2 = StillCaptureRequestControl.this;
                this.L$0 = mutex2;
                this.L$1 = stillCaptureRequestControl2;
                this.label = 1;
                if (mutex2.lock(null, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                mutex = mutex2;
                stillCaptureRequestControl = stillCaptureRequestControl2;
            } else {
                if (i != 1) {
                    Segment$$ExternalSyntheticBUOutline1.m992m("call to 'resume' before 'invoke' with coroutine");
                    return null;
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
        BuildersKt__Builders_commonKt.launch$default(this.threads.getSequentialScope(), null, null, new C01691(null), 3, null);
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.StillCaptureRequestControl$trySubmitPendingRequests$1 */
    @Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.StillCaptureRequestControl$trySubmitPendingRequests$1", m896f = "StillCaptureRequestControl.kt", m897i = {0, 1, 1, 2, 2, 2, 2}, m898l = {118, Opcodes.OR_INT_LIT8, 123}, m899m = "invokeSuspend", m900n = {"requestControl", "requestControl", "$this$withLock_u24default$iv", "requestControl", "$this$withLock_u24default$iv", "request", "requestControl"}, m902s = {"L$0", "L$0", "L$1", "L$0", "L$1", "L$3", "L$4"}, m903v = 1)
    @SourceDebugExtension({"SMAP\nStillCaptureRequestControl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StillCaptureRequestControl.kt\nandroidx/camera/camera2/impl/StillCaptureRequestControl$trySubmitPendingRequests$1\n+ 2 Mutex.kt\nkotlinx/coroutines/sync/MutexKt\n*L\n1#1,216:1\n116#2,11:217\n*S KotlinDebug\n*F\n+ 1 StillCaptureRequestControl.kt\nandroidx/camera/camera2/impl/StillCaptureRequestControl$trySubmitPendingRequests$1\n*L\n119#1:217,11\n*E\n"})
    public static final class C01721 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        int label;

        public C01721(Continuation<? super C01721> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return StillCaptureRequestControl.this.new C01721(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C01721) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:32:0x0099 A[Catch: all -> 0x0033, LOOP:0: B:30:0x008f->B:32:0x0099, LOOP_END, TryCatch #0 {all -> 0x0033, blocks: (B:9:0x002a, B:30:0x008f, B:32:0x0099, B:33:0x00a4), top: B:39:0x002a }] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r7) {
            /*
                r6 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r6.label
                r2 = 2
                r3 = 1
                r4 = 0
                if (r1 == 0) goto L56
                if (r1 == r3) goto L4e
                if (r1 == r2) goto L3c
                r0 = 3
                if (r1 != r0) goto L36
                java.lang.Object r0 = r6.L$5
                androidx.camera.camera2.impl.StillCaptureRequestControl r0 = (androidx.camera.camera2.impl.StillCaptureRequestControl) r0
                java.lang.Object r1 = r6.L$4
                androidx.camera.camera2.impl.UseCaseCameraRequestControl r1 = (androidx.camera.camera2.impl.UseCaseCameraRequestControl) r1
                java.lang.Object r2 = r6.L$3
                androidx.camera.camera2.impl.StillCaptureRequestControl$CaptureRequest r2 = (androidx.camera.camera2.impl.StillCaptureRequestControl.CaptureRequest) r2
                java.lang.Object r3 = r6.L$2
                androidx.camera.camera2.impl.StillCaptureRequestControl r3 = (androidx.camera.camera2.impl.StillCaptureRequestControl) r3
                java.lang.Object r5 = r6.L$1
                kotlinx.coroutines.sync.Mutex r5 = (kotlinx.coroutines.sync.Mutex) r5
                java.lang.Object r6 = r6.L$0
                androidx.camera.camera2.impl.UseCaseCameraRequestControl r6 = (androidx.camera.camera2.impl.UseCaseCameraRequestControl) r6
                kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L33
                kotlinx.coroutines.Deferred r7 = (kotlinx.coroutines.Deferred) r7     // Catch: java.lang.Throwable -> L33
                androidx.camera.camera2.impl.StillCaptureRequestControl.access$propagateResultOrEnqueueRequest(r0, r7, r2, r1)     // Catch: java.lang.Throwable -> L33
                goto L8f
            L33:
                r6 = move-exception
                goto Laa
            L36:
                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                okio.Segment$$ExternalSyntheticBUOutline1.m992m(r6)
                return r4
            L3c:
                java.lang.Object r0 = r6.L$2
                androidx.camera.camera2.impl.StillCaptureRequestControl r0 = (androidx.camera.camera2.impl.StillCaptureRequestControl) r0
                java.lang.Object r1 = r6.L$1
                kotlinx.coroutines.sync.Mutex r1 = (kotlinx.coroutines.sync.Mutex) r1
                java.lang.Object r6 = r6.L$0
                androidx.camera.camera2.impl.UseCaseCameraRequestControl r6 = (androidx.camera.camera2.impl.UseCaseCameraRequestControl) r6
                kotlin.ResultKt.throwOnFailure(r7)
                r3 = r0
                r5 = r1
                goto L8f
            L4e:
                java.lang.Object r1 = r6.L$0
                androidx.camera.camera2.impl.UseCaseCameraRequestControl r1 = (androidx.camera.camera2.impl.UseCaseCameraRequestControl) r1
                kotlin.ResultKt.throwOnFailure(r7)
                goto L6f
            L56:
                kotlin.ResultKt.throwOnFailure(r7)
                androidx.camera.camera2.impl.StillCaptureRequestControl r7 = androidx.camera.camera2.impl.StillCaptureRequestControl.this
                androidx.camera.camera2.impl.UseCaseCameraRequestControl r1 = r7.get_requestControl()
                if (r1 != 0) goto L64
                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                return r6
            L64:
                r6.L$0 = r1
                r6.label = r3
                java.lang.Object r7 = r1.awaitSurfaceSetup(r6)
                if (r7 != r0) goto L6f
                goto L8d
            L6f:
                java.lang.Boolean r7 = (java.lang.Boolean) r7
                boolean r7 = r7.booleanValue()
                if (r7 == 0) goto Lae
                androidx.camera.camera2.impl.StillCaptureRequestControl r7 = androidx.camera.camera2.impl.StillCaptureRequestControl.this
                kotlinx.coroutines.sync.Mutex r7 = androidx.camera.camera2.impl.StillCaptureRequestControl.access$getMutex$p(r7)
                androidx.camera.camera2.impl.StillCaptureRequestControl r3 = androidx.camera.camera2.impl.StillCaptureRequestControl.this
                r6.L$0 = r1
                r6.L$1 = r7
                r6.L$2 = r3
                r6.label = r2
                java.lang.Object r6 = r7.lock(r4, r6)
                if (r6 != r0) goto L8e
            L8d:
                return r0
            L8e:
                r5 = r7
            L8f:
                java.util.LinkedList r6 = androidx.camera.camera2.impl.StillCaptureRequestControl.access$getPendingRequests$p(r3)     // Catch: java.lang.Throwable -> L33
                boolean r6 = r6.isEmpty()     // Catch: java.lang.Throwable -> L33
                if (r6 != 0) goto La4
                java.util.LinkedList r6 = androidx.camera.camera2.impl.StillCaptureRequestControl.access$getPendingRequests$p(r3)     // Catch: java.lang.Throwable -> L33
                java.lang.Object r6 = r6.poll()     // Catch: java.lang.Throwable -> L33
                androidx.camera.camera2.impl.StillCaptureRequestControl$CaptureRequest r6 = (androidx.camera.camera2.impl.StillCaptureRequestControl.CaptureRequest) r6     // Catch: java.lang.Throwable -> L33
                goto L8f
            La4:
                kotlin.Unit r6 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L33
                r5.unlock(r4)
                goto Lae
            Laa:
                r5.unlock(r4)
                throw r6
            Lae:
                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.StillCaptureRequestControl.C01721.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    private final void trySubmitPendingRequests() {
        BuildersKt__Builders_commonKt.launch$default(this.threads.getSequentialScope(), null, null, new C01721(null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object submitRequest(androidx.camera.camera2.impl.StillCaptureRequestControl.CaptureRequest r11, androidx.camera.camera2.impl.UseCaseCameraRequestControl r12, kotlin.coroutines.Continuation<? super kotlinx.coroutines.Deferred<? extends java.util.List<java.lang.Void>>> r13) {
        /*
            r10 = this;
            boolean r0 = r13 instanceof androidx.camera.camera2.impl.StillCaptureRequestControl.C01701
            if (r0 == 0) goto L13
            r0 = r13
            androidx.camera.camera2.impl.StillCaptureRequestControl$submitRequest$1 r0 = (androidx.camera.camera2.impl.StillCaptureRequestControl.C01701) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.camera.camera2.impl.StillCaptureRequestControl$submitRequest$1 r0 = new androidx.camera.camera2.impl.StillCaptureRequestControl$submitRequest$1
            r0.<init>(r13)
        L18:
            java.lang.Object r13 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            java.lang.String r4 = "CXCP"
            r5 = 1
            if (r2 == 0) goto L3b
            if (r2 != r5) goto L35
            java.lang.Object r11 = r0.L$1
            r12 = r11
            androidx.camera.camera2.impl.UseCaseCameraRequestControl r12 = (androidx.camera.camera2.impl.UseCaseCameraRequestControl) r12
            java.lang.Object r11 = r0.L$0
            androidx.camera.camera2.impl.StillCaptureRequestControl$CaptureRequest r11 = (androidx.camera.camera2.impl.StillCaptureRequestControl.CaptureRequest) r11
            kotlin.ResultKt.throwOnFailure(r13)
            goto L72
        L35:
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r10)
            return r3
        L3b:
            kotlin.ResultKt.throwOnFailure(r13)
            androidx.camera.camera2.impl.Camera2Logger r13 = androidx.camera.camera2.impl.Camera2Logger.INSTANCE
            boolean r13 = androidx.camera.core.Logger.isDebugEnabled(r4)
            if (r13 == 0) goto L63
            java.lang.String r13 = androidx.camera.camera2.impl.Camera2Logger.access$getTRUNCATED_TAG$p()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r6 = "StillCaptureRequestControl: submitting "
            r2.<init>(r6)
            r2.append(r11)
            java.lang.String r6 = " at "
            r2.append(r6)
            r2.append(r12)
            java.lang.String r2 = r2.toString()
            android.util.Log.d(r13, r2)
        L63:
            androidx.camera.camera2.impl.FlashControl r13 = r10.flashControl
            r0.L$0 = r11
            r0.L$1 = r12
            r0.label = r5
            java.lang.Object r13 = r13.awaitFlashModeUpdate(r0)
            if (r13 != r1) goto L72
            return r1
        L72:
            java.lang.Number r13 = (java.lang.Number) r13
            int r13 = r13.intValue()
            androidx.camera.camera2.impl.Camera2Logger r0 = androidx.camera.camera2.impl.Camera2Logger.INSTANCE
            boolean r0 = androidx.camera.core.Logger.isDebugEnabled(r4)
            if (r0 == 0) goto L89
            java.lang.String r0 = androidx.camera.camera2.impl.Camera2Logger.access$getTRUNCATED_TAG$p()
            java.lang.String r1 = "StillCaptureRequestControl: Issuing single capture"
            android.util.Log.d(r0, r1)
        L89:
            java.util.List r0 = r11.getCaptureConfigs()
            int r1 = r11.getCaptureMode()
            int r2 = r11.getFlashType()
            java.util.List r12 = r12.issueSingleCaptureAsync(r0, r1, r2, r13)
            androidx.camera.camera2.impl.UseCaseThreads r10 = r10.threads
            kotlinx.coroutines.CoroutineScope r4 = r10.getSequentialScope()
            androidx.camera.camera2.impl.StillCaptureRequestControl$submitRequest$4 r7 = new androidx.camera.camera2.impl.StillCaptureRequestControl$submitRequest$4
            r7.<init>(r12, r11, r3)
            r8 = 3
            r9 = 0
            r5 = 0
            r6 = 0
            kotlinx.coroutines.Deferred r10 = kotlinx.coroutines.BuildersKt.async$default(r4, r5, r6, r7, r8, r9)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.StillCaptureRequestControl.submitRequest(androidx.camera.camera2.impl.StillCaptureRequestControl$CaptureRequest, androidx.camera.camera2.impl.UseCaseCameraRequestControl, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.StillCaptureRequestControl$submitRequest$4 */
    @Metadata(m876d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001*\u00020\u0003H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Ljava/lang/Void;", "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.StillCaptureRequestControl$submitRequest$4", m896f = "StillCaptureRequestControl.kt", m897i = {}, m898l = {160}, m899m = "invokeSuspend", m900n = {}, m902s = {}, m903v = 1)
    @SourceDebugExtension({"SMAP\nStillCaptureRequestControl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StillCaptureRequestControl.kt\nandroidx/camera/camera2/impl/StillCaptureRequestControl$submitRequest$4\n+ 2 Camera2Logger.kt\nandroidx/camera/camera2/impl/Camera2Logger\n*L\n1#1,216:1\n85#2,4:217\n85#2,4:221\n*S KotlinDebug\n*F\n+ 1 StillCaptureRequestControl.kt\nandroidx/camera/camera2/impl/StillCaptureRequestControl$submitRequest$4\n*L\n157#1:217,4\n161#1:221,4\n*E\n"})
    public static final class C01714 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super List<? extends Void>>, Object> {
        final /* synthetic */ List<Deferred<Void>> $deferredList;
        final /* synthetic */ CaptureRequest $request;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public C01714(List<? extends Deferred<Void>> list, CaptureRequest captureRequest, Continuation<? super C01714> continuation) {
            super(2, continuation);
            this.$deferredList = list;
            this.$request = captureRequest;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C01714(this.$deferredList, this.$request, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(CoroutineScope coroutineScope, Continuation<? super List<? extends Void>> continuation) {
            return invoke2(coroutineScope, (Continuation<? super List<Void>>) continuation);
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final Object invoke2(CoroutineScope coroutineScope, Continuation<? super List<Void>> continuation) {
            return ((C01714) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
                List<Deferred<Void>> list = this.$deferredList;
                this.label = 1;
                obj = AwaitKt.awaitAll(list, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    Segment$$ExternalSyntheticBUOutline1.m992m("call to 'resume' before 'invoke' with coroutine");
                    return null;
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
    public final void propagateResultOrEnqueueRequest(final Deferred<? extends List<Void>> deferred, final CaptureRequest captureRequest, final UseCaseCameraRequestControl useCaseCameraRequestControl) {
        deferred.invokeOnCompletion(new Function1() { // from class: androidx.camera.camera2.impl.StillCaptureRequestControl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return StillCaptureRequestControl.$r8$lambda$d94nr3kKLaWRlrxlGv5M4rvfiJA(this.f$0, deferred, captureRequest, useCaseCameraRequestControl, (Throwable) obj);
            }
        });
    }

    public static Unit $r8$lambda$d94nr3kKLaWRlrxlGv5M4rvfiJA(StillCaptureRequestControl stillCaptureRequestControl, Deferred deferred, CaptureRequest captureRequest, UseCaseCameraRequestControl useCaseCameraRequestControl, Throwable th) {
        if ((th instanceof ImageCaptureException) && ((ImageCaptureException) th).getImageCaptureError() == 3) {
            BuildersKt__Builders_commonKt.launch$default(stillCaptureRequestControl.threads.getSequentialScope(), null, null, new StillCaptureRequestControl$propagateResultOrEnqueueRequest$1$1(stillCaptureRequestControl, useCaseCameraRequestControl, captureRequest, null), 3, null);
        } else {
            CoroutineAdaptersKt.propagateCompletion(deferred, captureRequest.getResult(), th);
        }
        return Unit.INSTANCE;
    }
}
