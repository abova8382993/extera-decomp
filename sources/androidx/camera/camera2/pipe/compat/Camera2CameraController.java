package androidx.camera.camera2.pipe.compat;

import androidx.camera.camera2.config.UseCaseGraphContext$$ExternalSyntheticAutoCloseableDispatcher0;
import androidx.camera.camera2.pipe.CameraController;
import androidx.camera.camera2.pipe.CameraError;
import androidx.camera.camera2.pipe.CameraGraph;
import androidx.camera.camera2.pipe.CameraGraphId;
import androidx.camera.camera2.pipe.CameraId;
import androidx.camera.camera2.pipe.CameraSurfaceManager;
import androidx.camera.camera2.pipe.ConcurrentCameraGraphs;
import androidx.camera.camera2.pipe.StrictMode;
import androidx.camera.camera2.pipe.SurfaceTracker;
import androidx.camera.camera2.pipe.core.DurationNs;
import androidx.camera.camera2.pipe.core.Log;
import androidx.camera.camera2.pipe.core.Threads;
import androidx.camera.camera2.pipe.core.TimeSource;
import androidx.camera.camera2.pipe.core.TimestampNs;
import androidx.camera.camera2.pipe.graph.GraphListener;
import androidx.camera.camera2.pipe.graph.StreamGraphImpl;
import androidx.camera.camera2.pipe.internal.CameraStatusMonitor;
import java.util.Map;
import java.util.Set;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.StateFlow;

/* JADX INFO: loaded from: classes3.dex */
public final class Camera2CameraController implements CameraController {
    public static final Companion Companion = new Companion(null);
    private static final long PRIORITIES_CHANGED_THRESHOLD_NS = DurationNs.m1880constructorimpl(200000000);
    private boolean _isForeground;
    private final Camera2DeviceManager camera2DeviceManager;
    private final Camera2Quirks camera2Quirks;
    private CameraStatusMonitor.CameraStatus cameraAvailability;
    private Job cameraAvailabilityJob;
    private final CameraGraphId cameraGraphId;
    private Job cameraPrioritiesJob;
    private final CameraStatusMonitor cameraStatusMonitor;
    private final CameraSurfaceManager cameraSurfaceManager;
    private final Camera2CaptureSequenceProcessorFactory captureSequenceProcessorFactory;
    private final CaptureSessionFactory captureSessionFactory;
    private final CompletableDeferred closedDeferred;
    private final ConcurrentSessionSequencer concurrentSessionSequencer;
    private CameraController.ControllerState controllerState;
    private VirtualCamera currentCamera;
    private Job currentCameraStateJob;
    private CaptureSessionState currentSession;
    private Map currentSurfaceMap;
    private final CameraGraph.Config graphConfig;
    private final GraphListener graphListener;
    private CameraError lastCameraError;
    private TimestampNs lastCameraPrioritiesChangedTs;
    private final Object lock;
    private Job restartJob;
    private final CoroutineScope scope;
    private final ShutdownListener shutdownListener;
    private final StreamGraphImpl streamGraph;
    private final StrictMode strictMode;
    private final SurfaceTracker surfaceTracker;
    private final Threads threads;
    private final TimeSource timeSource;

    public interface ShutdownListener {
        void onControllerClosed(CameraController cameraController);
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.pipe.compat.Camera2CameraController$awaitClosed$1 */
    static final class C01951 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C01951(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return Camera2CameraController.this.awaitClosed(this);
        }
    }

    public Camera2CameraController(CoroutineScope scope, Threads threads, StrictMode strictMode, CameraGraph.Config graphConfig, GraphListener graphListener, SurfaceTracker surfaceTracker, CameraStatusMonitor cameraStatusMonitor, CaptureSessionFactory captureSessionFactory, Camera2CaptureSequenceProcessorFactory captureSequenceProcessorFactory, Camera2DeviceManager camera2DeviceManager, CameraSurfaceManager cameraSurfaceManager, Camera2Quirks camera2Quirks, TimeSource timeSource, CameraGraphId cameraGraphId, ShutdownListener shutdownListener, StreamGraphImpl streamGraph, ConcurrentSessionSequencers concurrentSessionSequencers) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(threads, "threads");
        Intrinsics.checkNotNullParameter(strictMode, "strictMode");
        Intrinsics.checkNotNullParameter(graphConfig, "graphConfig");
        Intrinsics.checkNotNullParameter(graphListener, "graphListener");
        Intrinsics.checkNotNullParameter(surfaceTracker, "surfaceTracker");
        Intrinsics.checkNotNullParameter(cameraStatusMonitor, "cameraStatusMonitor");
        Intrinsics.checkNotNullParameter(captureSessionFactory, "captureSessionFactory");
        Intrinsics.checkNotNullParameter(captureSequenceProcessorFactory, "captureSequenceProcessorFactory");
        Intrinsics.checkNotNullParameter(camera2DeviceManager, "camera2DeviceManager");
        Intrinsics.checkNotNullParameter(cameraSurfaceManager, "cameraSurfaceManager");
        Intrinsics.checkNotNullParameter(camera2Quirks, "camera2Quirks");
        Intrinsics.checkNotNullParameter(timeSource, "timeSource");
        Intrinsics.checkNotNullParameter(cameraGraphId, "cameraGraphId");
        Intrinsics.checkNotNullParameter(shutdownListener, "shutdownListener");
        Intrinsics.checkNotNullParameter(streamGraph, "streamGraph");
        Intrinsics.checkNotNullParameter(concurrentSessionSequencers, "concurrentSessionSequencers");
        this.scope = scope;
        this.threads = threads;
        this.strictMode = strictMode;
        this.graphConfig = graphConfig;
        this.graphListener = graphListener;
        this.surfaceTracker = surfaceTracker;
        this.cameraStatusMonitor = cameraStatusMonitor;
        this.captureSessionFactory = captureSessionFactory;
        this.captureSequenceProcessorFactory = captureSequenceProcessorFactory;
        this.camera2DeviceManager = camera2DeviceManager;
        this.cameraSurfaceManager = cameraSurfaceManager;
        this.camera2Quirks = camera2Quirks;
        this.timeSource = timeSource;
        this.cameraGraphId = cameraGraphId;
        this.shutdownListener = shutdownListener;
        this.streamGraph = streamGraph;
        this.lock = new Object();
        this._isForeground = true;
        this.controllerState = CameraController.ControllerState.STOPPED.INSTANCE;
        this.cameraAvailability = new CameraStatusMonitor.CameraStatus.CameraUnavailable(m1814getCameraIdDz_R5H8(), null);
        ConcurrentCameraGraphs concurrentCameraGraphs$camera_camera2_pipe = graphConfig.getConcurrentCameraGraphs$camera_camera2_pipe();
        this.concurrentSessionSequencer = concurrentCameraGraphs$camera_camera2_pipe != null ? concurrentSessionSequencers.getSequencer(getCameraGraphId(), concurrentCameraGraphs$camera_camera2_pipe) : null;
        this.closedDeferred = CompletableDeferredKt.CompletableDeferred$default(null, 1, null);
        this.cameraAvailabilityJob = BuildersKt__Builders_commonKt.launch$default(scope, null, null, new C01931(null), 3, null);
        this.cameraPrioritiesJob = BuildersKt__Builders_commonKt.launch$default(scope, null, null, new C01942(null), 3, null);
    }

    public CameraGraphId getCameraGraphId() {
        return this.cameraGraphId;
    }

    /* JADX INFO: renamed from: getCameraId-Dz_R5H8 */
    public String m1814getCameraIdDz_R5H8() {
        return this.graphConfig.m1575getCameraDz_R5H8();
    }

    public boolean isForeground() {
        boolean z;
        synchronized (this.lock) {
            z = this._isForeground;
        }
        return z;
    }

    @Override // androidx.camera.camera2.pipe.CameraController
    public void setForeground(boolean z) {
        synchronized (this.lock) {
            this._isForeground = z;
            Unit unit = Unit.INSTANCE;
        }
    }

    public final CameraController.ControllerState getControllerState$camera_camera2_pipe() {
        return this.controllerState;
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.pipe.compat.Camera2CameraController$1 */
    static final class C01931 extends SuspendLambda implements Function2 {
        int label;

        C01931(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return Camera2CameraController.this.new C01931(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01931) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                StateFlow cameraAvailability = Camera2CameraController.this.cameraStatusMonitor.getCameraAvailability();
                AnonymousClass1 anonymousClass1 = new FlowCollector() { // from class: androidx.camera.camera2.pipe.compat.Camera2CameraController.1.1
                    AnonymousClass1() {
                    }

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(CameraStatusMonitor.CameraStatus cameraStatus, Continuation continuation) {
                        if (cameraStatus instanceof CameraStatusMonitor.CameraStatus.CameraAvailable) {
                            if (CameraId.m1605equalsimpl0(((CameraStatusMonitor.CameraStatus.CameraAvailable) cameraStatus).m1935getCameraIdDz_R5H8(), camera2CameraController.m1814getCameraIdDz_R5H8())) {
                                camera2CameraController.onCameraStatusChanged(cameraStatus);
                            } else {
                                throw new IllegalStateException("Check failed.");
                            }
                        } else if (cameraStatus instanceof CameraStatusMonitor.CameraStatus.CameraUnavailable) {
                            if (CameraId.m1605equalsimpl0(((CameraStatusMonitor.CameraStatus.CameraUnavailable) cameraStatus).m1936getCameraIdDz_R5H8(), camera2CameraController.m1814getCameraIdDz_R5H8())) {
                                camera2CameraController.onCameraStatusChanged(cameraStatus);
                            } else {
                                throw new IllegalStateException("Check failed.");
                            }
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (cameraAvailability.collect(anonymousClass1, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }

        /* JADX INFO: renamed from: androidx.camera.camera2.pipe.compat.Camera2CameraController$1$1 */
        static final class AnonymousClass1 implements FlowCollector {
            AnonymousClass1() {
            }

            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(CameraStatusMonitor.CameraStatus cameraStatus, Continuation continuation) {
                if (cameraStatus instanceof CameraStatusMonitor.CameraStatus.CameraAvailable) {
                    if (CameraId.m1605equalsimpl0(((CameraStatusMonitor.CameraStatus.CameraAvailable) cameraStatus).m1935getCameraIdDz_R5H8(), camera2CameraController.m1814getCameraIdDz_R5H8())) {
                        camera2CameraController.onCameraStatusChanged(cameraStatus);
                    } else {
                        throw new IllegalStateException("Check failed.");
                    }
                } else if (cameraStatus instanceof CameraStatusMonitor.CameraStatus.CameraUnavailable) {
                    if (CameraId.m1605equalsimpl0(((CameraStatusMonitor.CameraStatus.CameraUnavailable) cameraStatus).m1936getCameraIdDz_R5H8(), camera2CameraController.m1814getCameraIdDz_R5H8())) {
                        camera2CameraController.onCameraStatusChanged(cameraStatus);
                    } else {
                        throw new IllegalStateException("Check failed.");
                    }
                }
                return Unit.INSTANCE;
            }
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.pipe.compat.Camera2CameraController$2 */
    static final class C01942 extends SuspendLambda implements Function2 {
        int label;

        C01942(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return Camera2CameraController.this.new C01942(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01942) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SharedFlow cameraPriorities = Camera2CameraController.this.cameraStatusMonitor.getCameraPriorities();
                AnonymousClass1 anonymousClass1 = new FlowCollector() { // from class: androidx.camera.camera2.pipe.compat.Camera2CameraController.2.1
                    AnonymousClass1() {
                    }

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Unit unit, Continuation continuation) {
                        camera2CameraController.onCameraStatusChanged(CameraStatusMonitor.CameraStatus.CameraPrioritiesChanged.INSTANCE);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (cameraPriorities.collect(anonymousClass1, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }

        /* JADX INFO: renamed from: androidx.camera.camera2.pipe.compat.Camera2CameraController$2$1 */
        static final class AnonymousClass1 implements FlowCollector {
            AnonymousClass1() {
            }

            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Unit unit, Continuation continuation) {
                camera2CameraController.onCameraStatusChanged(CameraStatusMonitor.CameraStatus.CameraPrioritiesChanged.INSTANCE);
                return Unit.INSTANCE;
            }
        }
    }

    @Override // androidx.camera.camera2.pipe.CameraController
    public void start() {
        synchronized (this.lock) {
            startLocked();
            Unit unit = Unit.INSTANCE;
        }
    }

    private final void tryRestart() {
        long jMo1888nowvQl9yQU = this.timeSource.mo1888nowvQl9yQU();
        if (Companion.m1815shouldRestartX9Wt83s$camera_camera2_pipe(this.controllerState, this.lastCameraError, this.cameraAvailability, this.lastCameraPrioritiesChangedTs, jMo1888nowvQl9yQU)) {
            long j = this.graphConfig.getFlags().getEnableRestartDelays() ? 700L : 0L;
            Job job = this.restartJob;
            if (job != null) {
                Job.DefaultImpls.cancel$default(job, null, 1, null);
            }
            this.restartJob = BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new C01982(j, this, null), 3, null);
            return;
        }
        if (Log.INSTANCE.getDEBUG_LOGGABLE()) {
            android.util.Log.d("CXCP", this + ": Not restarting. Controller state = " + getControllerState$camera_camera2_pipe() + ", last camera error = " + this.lastCameraError + ", camera availability = " + this.cameraAvailability + ", last camera priorities changed = " + this.lastCameraPrioritiesChangedTs + ", current timestamp = " + ((Object) TimestampNs.m1895toStringimpl(jMo1888nowvQl9yQU)) + '.');
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.pipe.compat.Camera2CameraController$tryRestart$2 */
    static final class C01982 extends SuspendLambda implements Function2 {
        final /* synthetic */ long $delayMs;
        int label;
        final /* synthetic */ Camera2CameraController this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01982(long j, Camera2CameraController camera2CameraController, Continuation continuation) {
            super(2, continuation);
            this.$delayMs = j;
            this.this$0 = camera2CameraController;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C01982(this.$delayMs, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01982) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                long j = this.$delayMs;
                this.label = 1;
                if (DelayKt.delay(j, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            Object obj2 = this.this$0.lock;
            Camera2CameraController camera2CameraController = this.this$0;
            synchronized (obj2) {
                try {
                    if (!camera2CameraController.isClosed() && !Intrinsics.areEqual(camera2CameraController.getControllerState$camera_camera2_pipe(), CameraController.ControllerState.STOPPING.INSTANCE) && !Intrinsics.areEqual(camera2CameraController.getControllerState$camera_camera2_pipe(), CameraController.ControllerState.STOPPED.INSTANCE)) {
                        if (Log.INSTANCE.getDEBUG_LOGGABLE()) {
                            android.util.Log.d("CXCP", "Restarting " + camera2CameraController + "...");
                        }
                        camera2CameraController.surfaceTracker.registerAllSurfaces();
                        camera2CameraController.stopLocked();
                        camera2CameraController.startLocked();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return Unit.INSTANCE;
        }
    }

    public final void startLocked() {
        Set of;
        if (isClosed()) {
            if (Log.INSTANCE.getINFO_LOGGABLE()) {
                android.util.Log.i("CXCP", "Ignoring start(): " + this + " is already closed");
                return;
            }
            return;
        }
        CameraController.ControllerState controllerState = this.controllerState;
        CameraController.ControllerState.STARTED started = CameraController.ControllerState.STARTED.INSTANCE;
        if (Intrinsics.areEqual(controllerState, started)) {
            if (Log.INSTANCE.getWARN_LOGGABLE()) {
                android.util.Log.w("CXCP", "Ignoring start(): " + this + " is already started");
                return;
            }
            return;
        }
        this.lastCameraError = null;
        String strM1575getCameraDz_R5H8 = this.graphConfig.m1575getCameraDz_R5H8();
        ConcurrentCameraGraphs concurrentCameraGraphs$camera_camera2_pipe = this.graphConfig.getConcurrentCameraGraphs$camera_camera2_pipe();
        if (concurrentCameraGraphs$camera_camera2_pipe == null || (of = concurrentCameraGraphs$camera_camera2_pipe.getCameraIds()) == null) {
            of = SetsKt.setOf(CameraId.m1602boximpl(strM1575getCameraDz_R5H8));
        }
        VirtualCamera virtualCameraMo1831openzDSwpeU = this.camera2DeviceManager.mo1831openzDSwpeU(strM1575getCameraDz_R5H8, CollectionsKt.toList(SetsKt.minus(of, CameraId.m1602boximpl(strM1575getCameraDz_R5H8))), this.graphListener, false, new Function1() { // from class: androidx.camera.camera2.pipe.compat.Camera2CameraController$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(Camera2CameraController.startLocked$lambda$2(this.f$0, (Unit) obj));
            }
        });
        if (virtualCameraMo1831openzDSwpeU == null) {
            if (Log.INSTANCE.getERROR_LOGGABLE()) {
                android.util.Log.e("CXCP", "Failed to start " + this + ": Open request submission failed");
                return;
            }
            return;
        }
        if (this.currentCamera != null) {
            throw new IllegalStateException("Check failed.");
        }
        if (this.currentSession != null) {
            throw new IllegalStateException("Check failed.");
        }
        this.currentCamera = virtualCameraMo1831openzDSwpeU;
        CaptureSessionState captureSessionState = new CaptureSessionState(this.graphListener, this.captureSessionFactory, this.captureSequenceProcessorFactory, this.cameraSurfaceManager, this.timeSource, this.graphConfig.getFlags(), this.concurrentSessionSequencer, this.streamGraph, this.strictMode, this.threads, this.scope);
        this.currentSession = captureSessionState;
        Map map = this.currentSurfaceMap;
        if (map != null) {
            captureSessionState.configureSurfaceMap(map);
        }
        this.controllerState = started;
        if (Log.INSTANCE.getDEBUG_LOGGABLE()) {
            android.util.Log.d("CXCP", "Started " + this);
        }
        Job job = this.currentCameraStateJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, null, 1, null);
        }
        this.currentCameraStateJob = BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new C01975(null), 3, null);
    }

    public static final boolean startLocked$lambda$2(Camera2CameraController camera2CameraController, Unit unit) {
        Intrinsics.checkNotNullParameter(unit, "<unused var>");
        return camera2CameraController.isForeground();
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.pipe.compat.Camera2CameraController$startLocked$5 */
    static final class C01975 extends SuspendLambda implements Function2 {
        int label;

        C01975(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return Camera2CameraController.this.new C01975(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01975) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Camera2CameraController camera2CameraController = Camera2CameraController.this;
                this.label = 1;
                if (camera2CameraController.bindSessionToCamera(this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public final void stopLocked() {
        if (isClosed()) {
            if (Log.INSTANCE.getWARN_LOGGABLE()) {
                android.util.Log.w("CXCP", "Ignoring stop(): " + this + " is already closed");
                return;
            }
            return;
        }
        CameraController.ControllerState controllerState = this.controllerState;
        CameraController.ControllerState.STOPPING stopping = CameraController.ControllerState.STOPPING.INSTANCE;
        if (Intrinsics.areEqual(controllerState, stopping) || Intrinsics.areEqual(this.controllerState, CameraController.ControllerState.STOPPED.INSTANCE)) {
            if (Log.INSTANCE.getWARN_LOGGABLE()) {
                android.util.Log.w("CXCP", "Ignoring stop(): " + this + " already stopping or stopped");
                return;
            }
            return;
        }
        VirtualCamera virtualCamera = this.currentCamera;
        CaptureSessionState captureSessionState = this.currentSession;
        this.currentCamera = null;
        this.currentSession = null;
        this.controllerState = stopping;
        if (Log.INSTANCE.getDEBUG_LOGGABLE()) {
            android.util.Log.d("CXCP", "Stopping " + this);
        }
        detachSessionAndCamera(captureSessionState, virtualCamera);
    }

    public final void onCameraStatusChanged(CameraStatusMonitor.CameraStatus cameraStatus) {
        if (Log.INSTANCE.getDEBUG_LOGGABLE()) {
            android.util.Log.d("CXCP", this + " (" + ((Object) CameraId.m1607toStringimpl(m1814getCameraIdDz_R5H8())) + ") camera status changed: " + cameraStatus);
        }
        synchronized (this.lock) {
            try {
                if (isClosed()) {
                    return;
                }
                if ((cameraStatus instanceof CameraStatusMonitor.CameraStatus.CameraAvailable) || (cameraStatus instanceof CameraStatusMonitor.CameraStatus.CameraUnavailable)) {
                    this.cameraAvailability = cameraStatus;
                } else if (cameraStatus instanceof CameraStatusMonitor.CameraStatus.CameraPrioritiesChanged) {
                    this.lastCameraPrioritiesChangedTs = TimestampNs.m1890boximpl(this.timeSource.mo1888nowvQl9yQU());
                }
                tryRestart();
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // androidx.camera.camera2.pipe.CameraController
    public void close() {
        synchronized (this.lock) {
            try {
                if (isClosed()) {
                    return;
                }
                this.controllerState = CameraController.ControllerState.CLOSING.INSTANCE;
                Log log = Log.INSTANCE;
                if (log.getDEBUG_LOGGABLE()) {
                    android.util.Log.d("CXCP", "Closed " + this);
                }
                VirtualCamera virtualCamera = this.currentCamera;
                CaptureSessionState captureSessionState = this.currentSession;
                this.currentCamera = null;
                this.currentSession = null;
                Job job = this.restartJob;
                if (job != null) {
                    Job.DefaultImpls.cancel$default(job, null, 1, null);
                }
                Job job2 = this.currentCameraStateJob;
                if (job2 != null) {
                    Job.DefaultImpls.cancel$default(job2, null, 1, null);
                }
                this.currentCameraStateJob = null;
                Job job3 = this.cameraAvailabilityJob;
                if (job3 != null) {
                    Job.DefaultImpls.cancel$default(job3, null, 1, null);
                }
                this.cameraAvailabilityJob = null;
                Job job4 = this.cameraPrioritiesJob;
                if (job4 != null) {
                    Job.DefaultImpls.cancel$default(job4, null, 1, null);
                }
                this.cameraPrioritiesJob = null;
                UseCaseGraphContext$$ExternalSyntheticAutoCloseableDispatcher0.m22m(this.cameraStatusMonitor);
                detachSessionAndCamera(captureSessionState, virtualCamera);
                if (this.graphConfig.getFlags().getCloseCameraDeviceOnClose() || this.camera2Quirks.m70x552c1673(m1814getCameraIdDz_R5H8())) {
                    if (log.getDEBUG_LOGGABLE()) {
                        android.util.Log.d("CXCP", "Quirk: Closing " + ((Object) CameraId.m1607toStringimpl(m1814getCameraIdDz_R5H8())) + " during " + this + "#close");
                    }
                    this.camera2DeviceManager.mo1830closeEfqyGwQ(m1814getCameraIdDz_R5H8());
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x0013  */
    @Override // androidx.camera.camera2.pipe.CameraController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object awaitClosed(kotlin.coroutines.Continuation r7) {
        /*
            r6 = this;
            boolean r0 = r7 instanceof androidx.camera.camera2.pipe.compat.Camera2CameraController.C01951
            if (r0 == 0) goto L13
            r0 = r7
            androidx.camera.camera2.pipe.compat.Camera2CameraController$awaitClosed$1 r0 = (androidx.camera.camera2.pipe.compat.Camera2CameraController.C01951) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.camera.camera2.pipe.compat.Camera2CameraController$awaitClosed$1 r0 = new androidx.camera.camera2.pipe.compat.Camera2CameraController$awaitClosed$1
            r0.<init>(r7)
        L18:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r7)
            goto Lc0
        L2a:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r0)
            throw r7
        L32:
            kotlin.ResultKt.throwOnFailure(r7)
            androidx.camera.camera2.pipe.core.Log r7 = androidx.camera.camera2.pipe.core.Log.INSTANCE
            boolean r2 = r7.getDEBUG_LOGGABLE()
            if (r2 == 0) goto L53
            java.lang.String r2 = "CXCP"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r6)
            java.lang.String r5 = "#awaitClosed"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            android.util.Log.d(r2, r4)
        L53:
            java.lang.Object r2 = r6.lock
            monitor-enter(r2)
            androidx.camera.camera2.pipe.CameraController$ControllerState r4 = r6.controllerState     // Catch: java.lang.Throwable -> L7d
            androidx.camera.camera2.pipe.CameraController$ControllerState$CLOSED r5 = androidx.camera.camera2.pipe.CameraController.ControllerState.CLOSED.INSTANCE     // Catch: java.lang.Throwable -> L7d
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r5)     // Catch: java.lang.Throwable -> L7d
            if (r4 == 0) goto L85
            boolean r7 = r7.getDEBUG_LOGGABLE()     // Catch: java.lang.Throwable -> L7d
            if (r7 == 0) goto L7f
            java.lang.String r7 = "CXCP"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7d
            r0.<init>()     // Catch: java.lang.Throwable -> L7d
            r0.append(r6)     // Catch: java.lang.Throwable -> L7d
            java.lang.String r1 = "#awaitClosed: Controller is already closed."
            r0.append(r1)     // Catch: java.lang.Throwable -> L7d
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> L7d
            android.util.Log.d(r7, r0)     // Catch: java.lang.Throwable -> L7d
            goto L7f
        L7d:
            r7 = move-exception
            goto Lc5
        L7f:
            java.lang.Boolean r7 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r3)     // Catch: java.lang.Throwable -> L7d
            monitor-exit(r2)
            return r7
        L85:
            androidx.camera.camera2.pipe.CameraController$ControllerState r4 = r6.controllerState     // Catch: java.lang.Throwable -> L7d
            androidx.camera.camera2.pipe.CameraController$ControllerState$CLOSING r5 = androidx.camera.camera2.pipe.CameraController.ControllerState.CLOSING.INSTANCE     // Catch: java.lang.Throwable -> L7d
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r5)     // Catch: java.lang.Throwable -> L7d
            if (r4 != 0) goto Lb2
            boolean r7 = r7.getWARN_LOGGABLE()     // Catch: java.lang.Throwable -> L7d
            if (r7 == 0) goto Lab
            java.lang.String r7 = "CXCP"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7d
            r0.<init>()     // Catch: java.lang.Throwable -> L7d
            r0.append(r6)     // Catch: java.lang.Throwable -> L7d
            java.lang.String r1 = "#awaitClosed: Controller isn't closing!"
            r0.append(r1)     // Catch: java.lang.Throwable -> L7d
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> L7d
            android.util.Log.w(r7, r0)     // Catch: java.lang.Throwable -> L7d
        Lab:
            r7 = 0
            java.lang.Boolean r7 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r7)     // Catch: java.lang.Throwable -> L7d
            monitor-exit(r2)
            return r7
        Lb2:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L7d
            monitor-exit(r2)
            kotlinx.coroutines.CompletableDeferred r7 = r6.closedDeferred
            r0.label = r3
            java.lang.Object r7 = r7.await(r0)
            if (r7 != r1) goto Lc0
            return r1
        Lc0:
            java.lang.Boolean r7 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r3)
            return r7
        Lc5:
            monitor-exit(r2)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.compat.Camera2CameraController.awaitClosed(kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // androidx.camera.camera2.pipe.CameraController
    public void updateSurfaceMap(Map surfaceMap) {
        Intrinsics.checkNotNullParameter(surfaceMap, "surfaceMap");
        synchronized (this.lock) {
            if (isClosed()) {
                return;
            }
            this.currentSurfaceMap = surfaceMap;
            CaptureSessionState captureSessionState = this.currentSession;
            if (captureSessionState != null) {
                captureSessionState.configureSurfaceMap(surfaceMap);
            }
        }
    }

    public String toString() {
        return "Camera2CameraController(" + getCameraGraphId() + ')';
    }

    public final Object bindSessionToCamera(Continuation continuation) {
        VirtualCamera virtualCamera;
        CaptureSessionState captureSessionState;
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        synchronized (this.lock) {
            virtualCamera = this.currentCamera;
            captureSessionState = this.currentSession;
            ref$ObjectRef.element = captureSessionState;
            Unit unit = Unit.INSTANCE;
        }
        if (virtualCamera != null && captureSessionState != null) {
            Object objCollect = virtualCamera.getState().collect(new FlowCollector() { // from class: androidx.camera.camera2.pipe.compat.Camera2CameraController.bindSessionToCamera.3
                final /* synthetic */ Camera2CameraController this$0;

                C01963(Camera2CameraController this) {
                    camera2CameraController = this;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(CameraState cameraState, Continuation continuation2) {
                    if (cameraState instanceof CameraStateOpen) {
                        ((CaptureSessionState) ref$ObjectRef.element).setCameraDevice(((CameraStateOpen) cameraState).getCameraDevice());
                    } else if (cameraState instanceof CameraStateClosing) {
                        ((CaptureSessionState) ref$ObjectRef.element).shutdown();
                    } else if (cameraState instanceof CameraStateClosed) {
                        ((CaptureSessionState) ref$ObjectRef.element).shutdown();
                        camera2CameraController.onStateClosed((CameraStateClosed) cameraState);
                    }
                    return Unit.INSTANCE;
                }
            }, continuation);
            return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.pipe.compat.Camera2CameraController$bindSessionToCamera$3 */
    static final class C01963 implements FlowCollector {
        final /* synthetic */ Camera2CameraController this$0;

        C01963(Camera2CameraController this) {
            camera2CameraController = this;
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        public final Object emit(CameraState cameraState, Continuation continuation2) {
            if (cameraState instanceof CameraStateOpen) {
                ((CaptureSessionState) ref$ObjectRef.element).setCameraDevice(((CameraStateOpen) cameraState).getCameraDevice());
            } else if (cameraState instanceof CameraStateClosing) {
                ((CaptureSessionState) ref$ObjectRef.element).shutdown();
            } else if (cameraState instanceof CameraStateClosed) {
                ((CaptureSessionState) ref$ObjectRef.element).shutdown();
                camera2CameraController.onStateClosed((CameraStateClosed) cameraState);
            }
            return Unit.INSTANCE;
        }
    }

    public final void onStateClosed(CameraStateClosed cameraStateClosed) {
        synchronized (this.lock) {
            try {
                if (isClosed()) {
                    return;
                }
                if (cameraStateClosed.m1845getCameraErrorCodemVEW8x0() != null) {
                    this.lastCameraError = cameraStateClosed.m1845getCameraErrorCodemVEW8x0();
                    if (CameraError.m1555isDisconnectedimpl(cameraStateClosed.m1845getCameraErrorCodemVEW8x0().m1557unboximpl())) {
                        this.controllerState = CameraController.ControllerState.DISCONNECTED.INSTANCE;
                        if (Log.INSTANCE.getDEBUG_LOGGABLE()) {
                            android.util.Log.d("CXCP", this + " is disconnected");
                        }
                    } else {
                        this.controllerState = CameraController.ControllerState.ERROR.INSTANCE;
                        if (Log.INSTANCE.getDEBUG_LOGGABLE()) {
                            android.util.Log.d("CXCP", this + " encountered error: " + ((Object) CameraError.m1556toStringimpl(cameraStateClosed.m1845getCameraErrorCodemVEW8x0().m1557unboximpl())));
                        }
                    }
                } else {
                    this.controllerState = CameraController.ControllerState.STOPPED.INSTANCE;
                }
                this.surfaceTracker.unregisterAllSurfaces();
                tryRestart();
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private final void detachSessionAndCamera(CaptureSessionState captureSessionState, VirtualCamera virtualCamera) {
        Job jobLaunch$default = BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new Camera2CameraController$detachSessionAndCamera$job$1(captureSessionState, virtualCamera, null), 3, null);
        if (Intrinsics.areEqual(this.controllerState, CameraController.ControllerState.CLOSING.INSTANCE)) {
            jobLaunch$default.invokeOnCompletion(new Function1() { // from class: androidx.camera.camera2.pipe.compat.Camera2CameraController$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return Camera2CameraController.detachSessionAndCamera$lambda$0(this.f$0, (Throwable) obj);
                }
            });
        }
    }

    public static final Unit detachSessionAndCamera$lambda$0(Camera2CameraController camera2CameraController, Throwable th) {
        synchronized (camera2CameraController.lock) {
            try {
                camera2CameraController.controllerState = CameraController.ControllerState.CLOSED.INSTANCE;
                if (Log.INSTANCE.getDEBUG_LOGGABLE()) {
                    android.util.Log.d("CXCP", camera2CameraController + " is closed");
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th2) {
                throw th2;
            }
        }
        camera2CameraController.shutdownListener.onControllerClosed(camera2CameraController);
        CompletableDeferred completableDeferred = camera2CameraController.closedDeferred;
        Unit unit2 = Unit.INSTANCE;
        completableDeferred.complete(unit2);
        CoroutineScopeKt.cancel$default(camera2CameraController.scope, null, 1, null);
        return unit2;
    }

    public final boolean isClosed() {
        return Intrinsics.areEqual(this.controllerState, CameraController.ControllerState.CLOSING.INSTANCE) || Intrinsics.areEqual(this.controllerState, CameraController.ControllerState.CLOSED.INSTANCE);
    }

    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Removed duplicated region for block: B:57:0x0026  */
        /* JADX INFO: renamed from: shouldRestart-X9Wt83s$camera_camera2_pipe */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean m1815shouldRestartX9Wt83s$camera_camera2_pipe(androidx.camera.camera2.pipe.CameraController.ControllerState r5, androidx.camera.camera2.pipe.CameraError r6, androidx.camera.camera2.pipe.internal.CameraStatusMonitor.CameraStatus r7, androidx.camera.camera2.pipe.core.TimestampNs r8, long r9) {
            /*
                r4 = this;
                java.lang.String r0 = "controllerState"
                kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r0)
                java.lang.String r0 = "cameraAvailability"
                kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r7, r0)
                boolean r7 = r7 instanceof androidx.camera.camera2.pipe.internal.CameraStatusMonitor.CameraStatus.CameraAvailable
                r0 = 1
                r1 = 0
                if (r7 == 0) goto L26
                androidx.camera.camera2.pipe.CameraError$Companion r7 = androidx.camera.camera2.pipe.CameraError.Companion
                int r7 = r7.m1562getERROR_CAMERA_DISABLEDv7Vf74A()
                if (r6 != 0) goto L1a
                r7 = r1
                goto L22
            L1a:
                int r2 = r6.m1557unboximpl()
                boolean r7 = androidx.camera.camera2.pipe.CameraError.m1553equalsimpl0(r2, r7)
            L22:
                if (r7 != 0) goto L26
                r7 = r0
                goto L27
            L26:
                r7 = r1
            L27:
                if (r8 != 0) goto L2b
            L29:
                r8 = r1
                goto L3f
            L2b:
                long r2 = r8.m1896unboximpl()
                long r9 = r9 - r2
                long r8 = androidx.camera.camera2.pipe.core.DurationNs.m1880constructorimpl(r9)
                long r2 = androidx.camera.camera2.pipe.compat.Camera2CameraController.access$getPRIORITIES_CHANGED_THRESHOLD_NS$cp()
                int r8 = androidx.camera.camera2.pipe.core.DurationNs.m1879compareTozYRVrok(r8, r2)
                if (r8 > 0) goto L29
                r8 = r0
            L3f:
                androidx.camera.camera2.pipe.CameraController$ControllerState$DISCONNECTED r9 = androidx.camera.camera2.pipe.CameraController.ControllerState.DISCONNECTED.INSTANCE
                boolean r9 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r9)
                if (r9 == 0) goto L6b
                if (r7 != 0) goto L6a
                if (r8 == 0) goto L4c
                goto L6a
            L4c:
                int r5 = android.os.Build.VERSION.SDK_INT
                r6 = 29
                if (r6 > r5) goto L58
                r6 = 33
                if (r5 >= r6) goto L58
                r5 = r0
                goto L59
            L58:
                r5 = r1
            L59:
                if (r5 == 0) goto L9c
                androidx.camera.camera2.pipe.core.Log r5 = androidx.camera.camera2.pipe.core.Log.INSTANCE
                boolean r5 = r5.getDEBUG_LOGGABLE()
                if (r5 == 0) goto L6a
                java.lang.String r5 = "CXCP"
                java.lang.String r6 = "Quirk for multi-resume activated: Kicking off restart."
                android.util.Log.d(r5, r6)
            L6a:
                return r0
            L6b:
                androidx.camera.camera2.pipe.CameraController$ControllerState$ERROR r8 = androidx.camera.camera2.pipe.CameraController.ControllerState.ERROR.INSTANCE
                boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r8)
                if (r5 == 0) goto L9c
                if (r7 == 0) goto L9c
                androidx.camera.camera2.pipe.CameraError$Companion r5 = androidx.camera.camera2.pipe.CameraError.Companion
                int r7 = r5.m1570getERROR_GRAPH_CONFIGv7Vf74A()
                if (r6 != 0) goto L7f
                r7 = r1
                goto L87
            L7f:
                int r8 = r6.m1557unboximpl()
                boolean r7 = androidx.camera.camera2.pipe.CameraError.m1553equalsimpl0(r8, r7)
            L87:
                if (r7 != 0) goto L9c
                int r5 = r5.m1572getERROR_SECURITY_EXCEPTIONv7Vf74A()
                if (r6 != 0) goto L91
                r5 = r1
                goto L99
            L91:
                int r6 = r6.m1557unboximpl()
                boolean r5 = androidx.camera.camera2.pipe.CameraError.m1553equalsimpl0(r6, r5)
            L99:
                if (r5 != 0) goto L9c
                return r0
            L9c:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.compat.Camera2CameraController.Companion.m1815shouldRestartX9Wt83s$camera_camera2_pipe(androidx.camera.camera2.pipe.CameraController$ControllerState, androidx.camera.camera2.pipe.CameraError, androidx.camera.camera2.pipe.internal.CameraStatusMonitor$CameraStatus, androidx.camera.camera2.pipe.core.TimestampNs, long):boolean");
        }
    }
}
