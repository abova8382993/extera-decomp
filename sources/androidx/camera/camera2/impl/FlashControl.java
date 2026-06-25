package androidx.camera.camera2.impl;

import android.util.Log;
import androidx.camera.camera2.adapter.CoroutineAdaptersKt;
import androidx.camera.camera2.compat.workaround.UseFlashModeTorchFor3aUpdate;
import androidx.camera.camera2.impl.TorchControl;
import androidx.camera.core.CameraControl;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Logger;
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
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import okhttp3.internal.url._UrlKt;
import okio.Segment$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes3.dex */
@Metadata(m876d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0007\u0018\u00002\u00020\u0001B1\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\u000b\u001a\u00020\n¢\u0006\u0004\b\f\u0010\rJ\u000f\u0010\u000f\u001a\u00020\u000eH\u0002¢\u0006\u0004\b\u000f\u0010\u0010J\u001e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00132\u0006\u0010\u0012\u001a\u00020\u0011H\u0082@¢\u0006\u0004\b\u0014\u0010\u0015J\u0017\u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\u0013H\u0002¢\u0006\u0004\b\u0016\u0010\u0017J\u0017\u0010\u0018\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\u0013H\u0002¢\u0006\u0004\b\u0018\u0010\u0017J\u000f\u0010\u0019\u001a\u00020\u000eH\u0016¢\u0006\u0004\b\u0019\u0010\u0010J%\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00132\u0006\u0010\u001b\u001a\u00020\u001a2\b\b\u0002\u0010\u001d\u001a\u00020\u001c¢\u0006\u0004\b\u001e\u0010\u001fJ\u0017\u0010\"\u001a\u00020\u000e2\b\u0010!\u001a\u0004\u0018\u00010 ¢\u0006\u0004\b\"\u0010#J\u0010\u0010$\u001a\u00020\u000eH\u0086@¢\u0006\u0004\b$\u0010%J\u0010\u0010&\u001a\u00020\u000eH\u0086@¢\u0006\u0004\b&\u0010%J\u0010\u0010'\u001a\u00020\u001aH\u0086@¢\u0006\u0004\b'\u0010%R\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010(R\u0014\u0010\u0005\u001a\u00020\u00048\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0005\u0010)R\u0014\u0010\u0007\u001a\u00020\u00068\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0007\u0010*R\u0014\u0010\t\u001a\u00020\b8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\t\u0010+R\u0014\u0010\u000b\u001a\u00020\n8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u000b\u0010,R\u0018\u0010.\u001a\u0004\u0018\u00010-8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b.\u0010/R\u001c\u00100\u001a\u00020\u001a8\u0002@\u0002X\u0082\u000e¢\u0006\f\n\u0004\b0\u00101\u0012\u0004\b2\u0010\u0010R*\u0010\u001b\u001a\u00020\u001a2\u0006\u00103\u001a\u00020\u001a8F@BX\u0086\u000e¢\u0006\u0012\n\u0004\b\u001b\u00101\u0012\u0004\b6\u0010\u0010\u001a\u0004\b4\u00105R\u0018\u00107\u001a\u0004\u0018\u00010 8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b7\u00108R(\u0010!\u001a\u0004\u0018\u00010 2\b\u00103\u001a\u0004\u0018\u00010 8F@BX\u0086\u000e¢\u0006\f\n\u0004\b!\u00108\u001a\u0004\b9\u0010:R\u001e\u0010<\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010;8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b<\u0010=R0\u0010>\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00132\f\u00103\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00138F@BX\u0086\u000e¢\u0006\f\n\u0004\b>\u0010?\u001a\u0004\b@\u0010\u0017R(\u0010E\u001a\u0004\u0018\u00010-2\b\u00103\u001a\u0004\u0018\u00010-8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bA\u0010B\"\u0004\bC\u0010D¨\u0006F"}, m877d2 = {"Landroidx/camera/camera2/impl/FlashControl;", "Landroidx/camera/camera2/impl/UseCaseCameraControl;", "Landroidx/camera/camera2/impl/CameraProperties;", "cameraProperties", "Landroidx/camera/camera2/impl/State3AControl;", "state3AControl", "Landroidx/camera/camera2/impl/UseCaseThreads;", "threads", "Landroidx/camera/camera2/impl/TorchControl;", "torchControl", "Landroidx/camera/camera2/compat/workaround/UseFlashModeTorchFor3aUpdate;", "useFlashModeTorchFor3aUpdate", "<init>", "(Landroidx/camera/camera2/impl/CameraProperties;Landroidx/camera/camera2/impl/State3AControl;Landroidx/camera/camera2/impl/UseCaseThreads;Landroidx/camera/camera2/impl/TorchControl;Landroidx/camera/camera2/compat/workaround/UseFlashModeTorchFor3aUpdate;)V", _UrlKt.FRAGMENT_ENCODE_SET, "stopRunningTask", "()V", _UrlKt.FRAGMENT_ENCODE_SET, "timeoutMillis", "Lkotlinx/coroutines/Deferred;", "applyScreenFlash", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setExternalFlashAeModeAsync", "()Lkotlinx/coroutines/Deferred;", "setTorchForScreenFlash", "reset", _UrlKt.FRAGMENT_ENCODE_SET, "flashMode", _UrlKt.FRAGMENT_ENCODE_SET, "cancelPreviousTask", "setFlashAsync", "(IZ)Lkotlinx/coroutines/Deferred;", "Landroidx/camera/core/ImageCapture$ScreenFlash;", "screenFlash", "setScreenFlash", "(Landroidx/camera/core/ImageCapture$ScreenFlash;)V", "startScreenFlashCaptureTasks", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "stopScreenFlashCaptureTasks", "awaitFlashModeUpdate", "Landroidx/camera/camera2/impl/CameraProperties;", "Landroidx/camera/camera2/impl/State3AControl;", "Landroidx/camera/camera2/impl/UseCaseThreads;", "Landroidx/camera/camera2/impl/TorchControl;", "Landroidx/camera/camera2/compat/workaround/UseFlashModeTorchFor3aUpdate;", "Landroidx/camera/camera2/impl/UseCaseCameraRequestControl;", "_requestControl", "Landroidx/camera/camera2/impl/UseCaseCameraRequestControl;", "_flashMode", "I", "get_flashMode$annotations", "value", "getFlashMode", "()I", "getFlashMode$annotations", "_screenFlash", "Landroidx/camera/core/ImageCapture$ScreenFlash;", "getScreenFlash", "()Landroidx/camera/core/ImageCapture$ScreenFlash;", "Lkotlinx/coroutines/CompletableDeferred;", "_updateSignal", "Lkotlinx/coroutines/CompletableDeferred;", "updateSignal", "Lkotlinx/coroutines/Deferred;", "getUpdateSignal", "getRequestControl", "()Landroidx/camera/camera2/impl/UseCaseCameraRequestControl;", "setRequestControl", "(Landroidx/camera/camera2/impl/UseCaseCameraRequestControl;)V", "requestControl", "camera-camera2"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nFlashControl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FlashControl.kt\nandroidx/camera/camera2/impl/FlashControl\n+ 2 Camera2Logger.kt\nandroidx/camera/camera2/impl/Camera2Logger\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,296:1\n85#2,4:297\n85#2,4:302\n85#2,4:306\n85#2,4:310\n85#2,4:314\n85#2,4:318\n85#2,4:322\n85#2,4:326\n85#2,4:330\n1#3:301\n*S KotlinDebug\n*F\n+ 1 FlashControl.kt\nandroidx/camera/camera2/impl/FlashControl\n*L\n97#1:297,4\n207#1:302,4\n217#1:306,4\n239#1:310,4\n250#1:314,4\n280#1:318,4\n285#1:322,4\n221#1:326,4\n254#1:330,4\n*E\n"})
public final class FlashControl implements UseCaseCameraControl {
    private UseCaseCameraRequestControl _requestControl;
    private CompletableDeferred<Unit> _updateSignal;
    private final CameraProperties cameraProperties;
    private final State3AControl state3AControl;
    private final UseCaseThreads threads;
    private final TorchControl torchControl;
    private final UseFlashModeTorchFor3aUpdate useFlashModeTorchFor3aUpdate;
    private volatile int _flashMode = 2;
    private int flashMode = this._flashMode;
    private volatile ImageCapture.ScreenFlash _screenFlash;
    private ImageCapture.ScreenFlash screenFlash = this._screenFlash;
    private Deferred<Unit> updateSignal = CompletableDeferredKt.CompletableDeferred(Unit.INSTANCE);

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.FlashControl$applyScreenFlash$1 */
    @Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.FlashControl", m896f = "FlashControl.kt", m897i = {0, 0}, m898l = {171}, m899m = "applyScreenFlash", m900n = {"onApplyCompletedSignal", "timeoutMillis"}, m902s = {"L$0", "J$0"}, m903v = 1)
    public static final class C01571 extends ContinuationImpl {
        long J$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C01571(Continuation<? super C01571> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlashControl.this.applyScreenFlash(0L, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.FlashControl$awaitFlashModeUpdate$1 */
    @Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.FlashControl", m896f = "FlashControl.kt", m897i = {0}, m898l = {284}, m899m = "awaitFlashModeUpdate", m900n = {"initialFlashMode"}, m902s = {"I$0"}, m903v = 1)
    public static final class C01601 extends ContinuationImpl {
        int I$0;
        int label;
        /* synthetic */ Object result;

        public C01601(Continuation<? super C01601> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlashControl.this.awaitFlashModeUpdate(this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.FlashControl$startScreenFlashCaptureTasks$1 */
    @Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.FlashControl", m896f = "FlashControl.kt", m897i = {0}, m898l = {149, 160}, m899m = "startScreenFlashCaptureTasks", m900n = {"pendingTasks"}, m902s = {"L$0"}, m903v = 1)
    public static final class C01611 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C01611(Continuation<? super C01611> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlashControl.this.startScreenFlashCaptureTasks(this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.FlashControl$stopScreenFlashCaptureTasks$1 */
    @Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.FlashControl", m896f = "FlashControl.kt", m897i = {}, m898l = {260}, m899m = "stopScreenFlashCaptureTasks", m900n = {}, m902s = {}, m903v = 1)
    public static final class C01621 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C01621(Continuation<? super C01621> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlashControl.this.stopScreenFlashCaptureTasks(this);
        }
    }

    public FlashControl(CameraProperties cameraProperties, State3AControl state3AControl, UseCaseThreads useCaseThreads, TorchControl torchControl, UseFlashModeTorchFor3aUpdate useFlashModeTorchFor3aUpdate) {
        this.cameraProperties = cameraProperties;
        this.state3AControl = state3AControl;
        this.threads = useCaseThreads;
        this.torchControl = torchControl;
        this.useFlashModeTorchFor3aUpdate = useFlashModeTorchFor3aUpdate;
    }

    /* JADX INFO: renamed from: getRequestControl, reason: from getter */
    public UseCaseCameraRequestControl get_requestControl() {
        return this._requestControl;
    }

    @Override // androidx.camera.camera2.impl.UseCaseCameraControl
    public void setRequestControl(UseCaseCameraRequestControl useCaseCameraRequestControl) {
        this._requestControl = useCaseCameraRequestControl;
        setFlashAsync(this._flashMode, false);
    }

    @Override // androidx.camera.camera2.impl.UseCaseCameraControl
    public void reset() {
        this._flashMode = 2;
        this._screenFlash = null;
        stopRunningTask();
        setFlashAsync$default(this, 2, false, 2, null);
    }

    /* JADX INFO: renamed from: getFlashMode, reason: from getter */
    public final int get_flashMode() {
        return this._flashMode;
    }

    /* JADX INFO: renamed from: getScreenFlash, reason: from getter */
    public final ImageCapture.ScreenFlash get_screenFlash() {
        return this._screenFlash;
    }

    public final Deferred<Unit> getUpdateSignal() {
        CompletableDeferred<Unit> completableDeferred = this._updateSignal;
        return completableDeferred != null ? completableDeferred : CompletableDeferredKt.CompletableDeferred(Unit.INSTANCE);
    }

    public static /* synthetic */ Deferred setFlashAsync$default(FlashControl flashControl, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        return flashControl.setFlashAsync(i, z);
    }

    public final Deferred<Unit> setFlashAsync(int flashMode, boolean cancelPreviousTask) {
        Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
        if (Logger.isDebugEnabled("CXCP")) {
            Log.d(Camera2Logger.TRUNCATED_TAG, "setFlashAsync: flashMode = " + flashMode + ", requestControl = " + get_requestControl());
        }
        CompletableDeferred<Unit> completableDeferredCompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default(null, 1, null);
        if (get_requestControl() != null) {
            this._flashMode = flashMode;
            if (cancelPreviousTask) {
                stopRunningTask();
            } else {
                CompletableDeferred<Unit> completableDeferred = this._updateSignal;
                if (completableDeferred != null) {
                    CoroutineAdaptersKt.propagateTo(completableDeferredCompletableDeferred$default, completableDeferred);
                }
            }
            this._updateSignal = completableDeferredCompletableDeferred$default;
            CoroutineAdaptersKt.propagateTo(this.state3AControl.setFlashModeAsync(flashMode), completableDeferredCompletableDeferred$default);
            return completableDeferredCompletableDeferred$default;
        }
        completableDeferredCompletableDeferred$default.completeExceptionally(new CameraControl.OperationCanceledException("Camera is not active."));
        return completableDeferredCompletableDeferred$default;
    }

    private final void stopRunningTask() {
        CompletableDeferred<Unit> completableDeferred = this._updateSignal;
        if (completableDeferred != null) {
            completableDeferred.completeExceptionally(new CameraControl.OperationCanceledException("There is a new flash mode being set or camera was closed"));
        }
        this._updateSignal = null;
    }

    public final void setScreenFlash(ImageCapture.ScreenFlash screenFlash) {
        this._screenFlash = screenFlash;
    }

    /* JADX WARN: Code restructure failed: missing block: B:58:0x0080, code lost:
    
        if (kotlinx.coroutines.AwaitKt.awaitAll(r5, r0) == r1) goto L59;
     */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object startScreenFlashCaptureTasks(kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
        /*
            r7 = this;
            boolean r0 = r8 instanceof androidx.camera.camera2.impl.FlashControl.C01611
            if (r0 == 0) goto L13
            r0 = r8
            androidx.camera.camera2.impl.FlashControl$startScreenFlashCaptureTasks$1 r0 = (androidx.camera.camera2.impl.FlashControl.C01611) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.camera.camera2.impl.FlashControl$startScreenFlashCaptureTasks$1 r0 = new androidx.camera.camera2.impl.FlashControl$startScreenFlashCaptureTasks$1
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            r4 = 2
            r5 = 1
            if (r2 == 0) goto L3f
            if (r2 == r5) goto L33
            if (r2 != r4) goto L2d
            kotlin.ResultKt.throwOnFailure(r8)
            goto L83
        L2d:
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r7)
            return r3
        L33:
            java.lang.Object r2 = r0.L$1
            java.util.List r2 = (java.util.List) r2
            java.lang.Object r5 = r0.L$0
            java.util.List r5 = (java.util.List) r5
            kotlin.ResultKt.throwOnFailure(r8)
            goto L57
        L3f:
            kotlin.ResultKt.throwOnFailure(r8)
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r0.L$0 = r2
            r0.L$1 = r2
            r0.label = r5
            r5 = 3000(0xbb8, double:1.482E-320)
            java.lang.Object r8 = r7.applyScreenFlash(r5, r0)
            if (r8 != r1) goto L56
            goto L82
        L56:
            r5 = r2
        L57:
            r2.add(r8)
            kotlinx.coroutines.Deferred r8 = r7.setExternalFlashAeModeAsync()
            if (r8 == 0) goto L67
            boolean r8 = r5.add(r8)
            kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r8)
        L67:
            kotlinx.coroutines.Deferred r7 = r7.setTorchForScreenFlash()
            if (r7 == 0) goto L74
            boolean r7 = r5.add(r7)
            kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r7)
        L74:
            java.util.Collection r5 = (java.util.Collection) r5
            r0.L$0 = r3
            r0.L$1 = r3
            r0.label = r4
            java.lang.Object r7 = kotlinx.coroutines.AwaitKt.awaitAll(r5, r0)
            if (r7 != r1) goto L83
        L82:
            return r1
        L83:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.FlashControl.startScreenFlashCaptureTasks(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object applyScreenFlash(long r12, kotlin.coroutines.Continuation<? super kotlinx.coroutines.Deferred<kotlin.Unit>> r14) {
        /*
            r11 = this;
            boolean r0 = r14 instanceof androidx.camera.camera2.impl.FlashControl.C01571
            if (r0 == 0) goto L13
            r0 = r14
            androidx.camera.camera2.impl.FlashControl$applyScreenFlash$1 r0 = (androidx.camera.camera2.impl.FlashControl.C01571) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.camera.camera2.impl.FlashControl$applyScreenFlash$1 r0 = new androidx.camera.camera2.impl.FlashControl$applyScreenFlash$1
            r0.<init>(r14)
        L18:
            java.lang.Object r14 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L37
            if (r2 != r4) goto L31
            long r12 = r0.J$0
            java.lang.Object r0 = r0.L$0
            kotlinx.coroutines.CompletableDeferred r0 = (kotlinx.coroutines.CompletableDeferred) r0
            kotlin.ResultKt.throwOnFailure(r14)
            r8 = r11
            goto L5e
        L31:
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r11)
            return r3
        L37:
            kotlin.ResultKt.throwOnFailure(r14)
            kotlinx.coroutines.CompletableDeferred r14 = kotlinx.coroutines.CompletableDeferredKt.CompletableDeferred$default(r3, r4, r3)
            androidx.camera.camera2.impl.FlashControl$$ExternalSyntheticLambda1 r9 = new androidx.camera.camera2.impl.FlashControl$$ExternalSyntheticLambda1
            r9.<init>()
            kotlinx.coroutines.MainCoroutineDispatcher r2 = kotlinx.coroutines.Dispatchers.getMain()
            androidx.camera.camera2.impl.FlashControl$applyScreenFlash$2 r5 = new androidx.camera.camera2.impl.FlashControl$applyScreenFlash$2
            r10 = 0
            r8 = r11
            r6 = r12
            r5.<init>(r6, r8, r9, r10)
            r0.L$0 = r14
            r0.J$0 = r6
            r0.label = r4
            java.lang.Object r11 = kotlinx.coroutines.BuildersKt.withContext(r2, r5, r0)
            if (r11 != r1) goto L5c
            return r1
        L5c:
            r0 = r14
            r12 = r6
        L5e:
            androidx.camera.camera2.impl.UseCaseThreads r11 = r8.threads
            kotlinx.coroutines.CoroutineScope r4 = r11.getScope()
            androidx.camera.camera2.impl.FlashControl$applyScreenFlash$3 r7 = new androidx.camera.camera2.impl.FlashControl$applyScreenFlash$3
            r7.<init>(r0, r12, r3)
            r8 = 3
            r9 = 0
            r5 = 0
            r6 = 0
            kotlinx.coroutines.Deferred r11 = kotlinx.coroutines.BuildersKt.async$default(r4, r5, r6, r7, r8, r9)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.FlashControl.applyScreenFlash(long, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.FlashControl$applyScreenFlash$2 */
    @Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.FlashControl$applyScreenFlash$2", m896f = "FlashControl.kt", m897i = {}, m898l = {}, m899m = "invokeSuspend", m900n = {}, m902s = {}, m903v = 1)
    @SourceDebugExtension({"SMAP\nFlashControl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FlashControl.kt\nandroidx/camera/camera2/impl/FlashControl$applyScreenFlash$2\n+ 2 Camera2Logger.kt\nandroidx/camera/camera2/impl/Camera2Logger\n*L\n1#1,296:1\n85#2,4:297\n*S KotlinDebug\n*F\n+ 1 FlashControl.kt\nandroidx/camera/camera2/impl/FlashControl$applyScreenFlash$2\n*L\n174#1:297,4\n*E\n"})
    public static final class C01582 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ ImageCapture.ScreenFlashListener $screenFlashListener;
        final /* synthetic */ long $timeoutMillis;
        int label;
        final /* synthetic */ FlashControl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01582(long j, FlashControl flashControl, ImageCapture.ScreenFlashListener screenFlashListener, Continuation<? super C01582> continuation) {
            super(2, continuation);
            this.$timeoutMillis = j;
            this.this$0 = flashControl;
            this.$screenFlashListener = screenFlashListener;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C01582(this.$timeoutMillis, this.this$0, this.$screenFlashListener, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C01582) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                Segment$$ExternalSyntheticBUOutline1.m992m("call to 'resume' before 'invoke' with coroutine");
                return null;
            }
            ResultKt.throwOnFailure(obj);
            long jCurrentTimeMillis = System.currentTimeMillis() + this.$timeoutMillis;
            ImageCapture.ScreenFlash screenFlash = this.this$0.get_screenFlash();
            if (screenFlash != null) {
                screenFlash.apply(jCurrentTimeMillis, this.$screenFlashListener);
            }
            Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
            if (Logger.isDebugEnabled("CXCP")) {
                Log.d(Camera2Logger.TRUNCATED_TAG, "applyScreenFlash: ScreenFlash.apply() invoked, expirationTimeMillis = " + jCurrentTimeMillis);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.FlashControl$applyScreenFlash$3 */
    @Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.FlashControl$applyScreenFlash$3", m896f = "FlashControl.kt", m897i = {}, m898l = {188}, m899m = "invokeSuspend", m900n = {}, m902s = {}, m903v = 1)
    @SourceDebugExtension({"SMAP\nFlashControl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FlashControl.kt\nandroidx/camera/camera2/impl/FlashControl$applyScreenFlash$3\n+ 2 Camera2Logger.kt\nandroidx/camera/camera2/impl/Camera2Logger\n*L\n1#1,296:1\n85#2,4:297\n85#2,4:301\n119#2,4:305\n*S KotlinDebug\n*F\n+ 1 FlashControl.kt\nandroidx/camera/camera2/impl/FlashControl$applyScreenFlash$3\n*L\n181#1:297,4\n189#1:301,4\n191#1:305,4\n*E\n"})
    public static final class C01593 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ CompletableDeferred<Unit> $onApplyCompletedSignal;
        final /* synthetic */ long $timeoutMillis;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01593(CompletableDeferred<Unit> completableDeferred, long j, Continuation<? super C01593> continuation) {
            super(2, continuation);
            this.$onApplyCompletedSignal = completableDeferred;
            this.$timeoutMillis = j;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C01593(this.$onApplyCompletedSignal, this.$timeoutMillis, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C01593) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
                if (Logger.isDebugEnabled("CXCP")) {
                    Log.d(Camera2Logger.TRUNCATED_TAG, "applyScreenFlash: Waiting for ScreenFlashListener to be completed");
                }
                CompletableDeferred<Unit> completableDeferred = this.$onApplyCompletedSignal;
                long j = this.$timeoutMillis;
                this.label = 1;
                obj = CoroutineAdaptersKt.awaitUntil(completableDeferred, j, this);
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
            if (((Boolean) obj).booleanValue()) {
                Camera2Logger camera2Logger2 = Camera2Logger.INSTANCE;
                if (Logger.isDebugEnabled("CXCP")) {
                    Log.d(Camera2Logger.TRUNCATED_TAG, "applyScreenFlash: ScreenFlashListener completed");
                }
            } else {
                Camera2Logger camera2Logger3 = Camera2Logger.INSTANCE;
                long j2 = this.$timeoutMillis;
                if (Logger.isWarnEnabled("CXCP")) {
                    Log.w(Camera2Logger.TRUNCATED_TAG, "applyScreenFlash: ScreenFlashListener completion timed out after " + j2 + " ms");
                }
            }
            return Unit.INSTANCE;
        }
    }

    private final Deferred<Unit> setExternalFlashAeModeAsync() {
        boolean zIsExternalFlashAeModeSupported = CameraMetadataIntegrationKt.isExternalFlashAeModeSupported(this.cameraProperties.getMetadata());
        Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
        if (Logger.isDebugEnabled("CXCP")) {
            Log.d(Camera2Logger.TRUNCATED_TAG, "setExternalFlashAeModeAsync: isExternalFlashAeModeSupported = " + zIsExternalFlashAeModeSupported);
        }
        if (!zIsExternalFlashAeModeSupported) {
            return null;
        }
        Deferred<Unit> tryExternalFlashAeModeAsync = this.state3AControl.setTryExternalFlashAeModeAsync(true);
        if (Logger.isDebugEnabled("CXCP")) {
            Log.d(Camera2Logger.TRUNCATED_TAG, "setExternalFlashAeModeAsync: need to wait for state3AControl.updateSignal");
        }
        tryExternalFlashAeModeAsync.invokeOnCompletion(new Function1() { // from class: androidx.camera.camera2.impl.FlashControl$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return FlashControl.setExternalFlashAeModeAsync$lambda$1$1((Throwable) obj);
            }
        });
        return tryExternalFlashAeModeAsync;
    }

    public static final Unit setExternalFlashAeModeAsync$lambda$1$1(Throwable th) {
        Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
        if (Logger.isDebugEnabled("CXCP")) {
            Log.d(Camera2Logger.TRUNCATED_TAG, "setExternalFlashAeModeAsync: state3AControl.updateSignal completed");
        }
        return Unit.INSTANCE;
    }

    private final Deferred<Unit> setTorchForScreenFlash() {
        boolean zShouldUseFlashModeTorch = this.useFlashModeTorchFor3aUpdate.shouldUseFlashModeTorch();
        Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
        if (Logger.isDebugEnabled("CXCP")) {
            Log.d(Camera2Logger.TRUNCATED_TAG, "setTorchIfRequired: shouldUseFlashModeTorch = " + zShouldUseFlashModeTorch);
        }
        if (!zShouldUseFlashModeTorch) {
            return null;
        }
        Deferred<Unit> deferredM1346setTorchAsyncOup_wC0$camera_camera2$default = TorchControl.m1346setTorchAsyncOup_wC0$camera_camera2$default(this.torchControl, TorchControl.TorchMode.INSTANCE.m1358getUSED_AS_FLASHIRs_R8(), false, true, 2, null);
        if (Logger.isDebugEnabled("CXCP")) {
            Log.d(Camera2Logger.TRUNCATED_TAG, "setTorchIfRequired: need to wait for torch control to be completed");
        }
        deferredM1346setTorchAsyncOup_wC0$camera_camera2$default.invokeOnCompletion(new Function1() { // from class: androidx.camera.camera2.impl.FlashControl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return FlashControl.setTorchForScreenFlash$lambda$1$1((Throwable) obj);
            }
        });
        return deferredM1346setTorchAsyncOup_wC0$camera_camera2$default;
    }

    public static final Unit setTorchForScreenFlash$lambda$1$1(Throwable th) {
        Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
        if (Logger.isDebugEnabled("CXCP")) {
            Log.d(Camera2Logger.TRUNCATED_TAG, "setTorchIfRequired: torch control completed");
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.FlashControl$stopScreenFlashCaptureTasks$2 */
    @Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.FlashControl$stopScreenFlashCaptureTasks$2", m896f = "FlashControl.kt", m897i = {}, m898l = {}, m899m = "invokeSuspend", m900n = {}, m902s = {}, m903v = 1)
    @SourceDebugExtension({"SMAP\nFlashControl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FlashControl.kt\nandroidx/camera/camera2/impl/FlashControl$stopScreenFlashCaptureTasks$2\n+ 2 Camera2Logger.kt\nandroidx/camera/camera2/impl/Camera2Logger\n*L\n1#1,296:1\n85#2,4:297\n*S KotlinDebug\n*F\n+ 1 FlashControl.kt\nandroidx/camera/camera2/impl/FlashControl$stopScreenFlashCaptureTasks$2\n*L\n262#1:297,4\n*E\n"})
    public static final class C01632 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C01632(Continuation<? super C01632> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return FlashControl.this.new C01632(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C01632) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                Segment$$ExternalSyntheticBUOutline1.m992m("call to 'resume' before 'invoke' with coroutine");
                return null;
            }
            ResultKt.throwOnFailure(obj);
            ImageCapture.ScreenFlash screenFlash = FlashControl.this.get_screenFlash();
            if (screenFlash != null) {
                screenFlash.clear();
            }
            Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
            if (Logger.isDebugEnabled("CXCP")) {
                Log.d(Camera2Logger.TRUNCATED_TAG, "screenFlashPostCapture: ScreenFlash.clear() invoked");
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object stopScreenFlashCaptureTasks(kotlin.coroutines.Continuation<? super kotlin.Unit> r7) {
        /*
            r6 = this;
            boolean r0 = r7 instanceof androidx.camera.camera2.impl.FlashControl.C01621
            if (r0 == 0) goto L13
            r0 = r7
            androidx.camera.camera2.impl.FlashControl$stopScreenFlashCaptureTasks$1 r0 = (androidx.camera.camera2.impl.FlashControl.C01621) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.camera.camera2.impl.FlashControl$stopScreenFlashCaptureTasks$1 r0 = new androidx.camera.camera2.impl.FlashControl$stopScreenFlashCaptureTasks$1
            r0.<init>(r7)
        L18:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L30
            if (r2 != r4) goto L2a
            kotlin.ResultKt.throwOnFailure(r7)
            goto L45
        L2a:
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r6)
            return r3
        L30:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlinx.coroutines.MainCoroutineDispatcher r7 = kotlinx.coroutines.Dispatchers.getMain()
            androidx.camera.camera2.impl.FlashControl$stopScreenFlashCaptureTasks$2 r2 = new androidx.camera.camera2.impl.FlashControl$stopScreenFlashCaptureTasks$2
            r2.<init>(r3)
            r0.label = r4
            java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r7, r2, r0)
            if (r7 != r1) goto L45
            return r1
        L45:
            androidx.camera.camera2.impl.CameraProperties r7 = r6.cameraProperties
            androidx.camera.camera2.pipe.CameraMetadata r7 = r7.getMetadata()
            boolean r7 = androidx.camera.camera2.impl.CameraMetadataIntegrationKt.isExternalFlashAeModeSupported(r7)
            if (r7 == 0) goto L57
            androidx.camera.camera2.impl.State3AControl r7 = r6.state3AControl
            r0 = 0
            r7.setTryExternalFlashAeModeAsync(r0)
        L57:
            androidx.camera.camera2.compat.workaround.UseFlashModeTorchFor3aUpdate r7 = r6.useFlashModeTorchFor3aUpdate
            boolean r7 = r7.shouldUseFlashModeTorch()
            if (r7 == 0) goto L6e
            androidx.camera.camera2.impl.TorchControl r0 = r6.torchControl
            androidx.camera.camera2.impl.TorchControl$TorchMode$Companion r6 = androidx.camera.camera2.impl.TorchControl.TorchMode.INSTANCE
            int r1 = r6.m1356getOFFIRs_R8()
            r4 = 2
            r5 = 0
            r2 = 0
            r3 = 1
            androidx.camera.camera2.impl.TorchControl.m1346setTorchAsyncOup_wC0$camera_camera2$default(r0, r1, r2, r3, r4, r5)
        L6e:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.FlashControl.stopScreenFlashCaptureTasks(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object awaitFlashModeUpdate(kotlin.coroutines.Continuation<? super java.lang.Integer> r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof androidx.camera.camera2.impl.FlashControl.C01601
            if (r0 == 0) goto L13
            r0 = r6
            androidx.camera.camera2.impl.FlashControl$awaitFlashModeUpdate$1 r0 = (androidx.camera.camera2.impl.FlashControl.C01601) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.camera.camera2.impl.FlashControl$awaitFlashModeUpdate$1 r0 = new androidx.camera.camera2.impl.FlashControl$awaitFlashModeUpdate$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            java.lang.String r3 = "CXCP"
            r4 = 1
            if (r2 == 0) goto L34
            if (r2 != r4) goto L2d
            int r5 = r0.I$0
            kotlin.ResultKt.throwOnFailure(r6)
            goto L5c
        L2d:
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r5)
            r5 = 0
            return r5
        L34:
            kotlin.ResultKt.throwOnFailure(r6)
            androidx.camera.camera2.impl.Camera2Logger r6 = androidx.camera.camera2.impl.Camera2Logger.INSTANCE
            boolean r6 = androidx.camera.core.Logger.isDebugEnabled(r3)
            if (r6 == 0) goto L48
            java.lang.String r6 = androidx.camera.camera2.impl.Camera2Logger.access$getTRUNCATED_TAG$p()
            java.lang.String r2 = "FlashControl: Waiting for any ongoing update to be completed"
            android.util.Log.d(r6, r2)
        L48:
            int r6 = r5.get_flashMode()
            kotlinx.coroutines.Deferred r5 = r5.getUpdateSignal()
            r0.I$0 = r6
            r0.label = r4
            java.lang.Object r5 = r5.join(r0)
            if (r5 != r1) goto L5b
            return r1
        L5b:
            r5 = r6
        L5c:
            androidx.camera.camera2.impl.Camera2Logger r6 = androidx.camera.camera2.impl.Camera2Logger.INSTANCE
            boolean r6 = androidx.camera.core.Logger.isDebugEnabled(r3)
            if (r6 == 0) goto L79
            java.lang.String r6 = androidx.camera.camera2.impl.Camera2Logger.access$getTRUNCATED_TAG$p()
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "awaitFlashModeUpdate: initialFlashMode = "
            r0.<init>(r1)
            r0.append(r5)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r6, r0)
        L79:
            java.lang.Integer r5 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r5)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.FlashControl.awaitFlashModeUpdate(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
