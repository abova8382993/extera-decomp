package androidx.camera.camera2.impl;

import android.util.Log;
import androidx.camera.camera2.adapter.CaptureConfigAdapter;
import androidx.camera.camera2.adapter.CaptureResultAdapter;
import androidx.camera.camera2.compat.workaround.FlashAvailabilityCheckerKt;
import androidx.camera.camera2.compat.workaround.UseTorchAsFlash;
import androidx.camera.camera2.config.UseCaseGraphContext;
import androidx.camera.camera2.pipe.FrameInfo;
import androidx.camera.camera2.pipe.FrameMetadata;
import androidx.camera.camera2.pipe.Metadata;
import androidx.camera.camera2.pipe.Request;
import androidx.camera.camera2.pipe.RequestFailure;
import androidx.camera.camera2.pipe.RequestMetadata;
import androidx.camera.camera2.pipe.RequestNumber;
import androidx.camera.camera2.pipe.RequestTemplate;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.CameraCaptureResult;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.ConvergenceUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Provider;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;

/* JADX INFO: loaded from: classes3.dex */
public final class CapturePipelineImpl implements CapturePipeline {
    private final CaptureConfigAdapter configAdapter;
    private final CapturePipelineImpl$emptyRequestMetadata$1 emptyRequestMetadata;
    private final FlashControl flashControl;
    private FrameMetadata frameMetadata;
    private final Lazy hasFlashUnit$delegate;
    private final ComboRequestListener requestListener;
    private int template;
    private final UseCaseThreads threads;
    private final TorchControl torchControl;
    private final Lazy useCaseCameraState$delegate;
    private final Provider useCaseCameraStateProvider;
    private final UseCaseGraphContext useCaseGraphContext;
    private final UseTorchAsFlash useTorchAsFlash;
    private final VideoUsageControl videoUsageControl;

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$aePreCaptureApplyCapture$1 */
    static final class C01251 extends ContinuationImpl {
        int I$0;
        long J$0;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        C01251(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CapturePipelineImpl.this.aePreCaptureApplyCapture(null, 0L, 0, null, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$defaultCapture$1 */
    static final class C01261 extends ContinuationImpl {
        int I$0;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        C01261(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CapturePipelineImpl.this.defaultCapture(null, 0, 0, null, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$defaultNoFlashCapture$1 */
    static final class C01271 extends ContinuationImpl {
        int I$0;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        C01271(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CapturePipelineImpl.this.defaultNoFlashCapture(null, 0, null, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$getFrameMetadata$1 */
    static final class C01281 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C01281(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CapturePipelineImpl.this.getFrameMetadata(this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$invokeCaptureTasks$1 */
    static final class C01291 extends ContinuationImpl {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        C01291(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CapturePipelineImpl.this.invokeCaptureTasks(null, 0, 0, 0, null, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$invokeScreenFlashPostCaptureTasks$1 */
    static final class C01301 extends ContinuationImpl {
        int I$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C01301(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CapturePipelineImpl.this.invokeScreenFlashPostCaptureTasks(0, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$invokeScreenFlashPreCaptureTasks$1 */
    static final class C01311 extends ContinuationImpl {
        int I$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C01311(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CapturePipelineImpl.this.invokeScreenFlashPreCaptureTasks(0, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$isPhysicalFlashRequired$1 */
    static final class C01321 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C01321(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CapturePipelineImpl.this.isPhysicalFlashRequired(0, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$lockAf$1 */
    static final class C01341 extends ContinuationImpl {
        long J$0;
        Object L$0;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        C01341(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CapturePipelineImpl.this.lockAf(0L, false, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$screenFlashCapture$1 */
    static final class C01351 extends ContinuationImpl {
        int I$0;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        C01351(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CapturePipelineImpl.this.screenFlashCapture(null, 0, null, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$torchApplyCapture$1 */
    static final class C01371 extends ContinuationImpl {
        int I$0;
        int I$1;
        int I$2;
        long J$0;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        C01371(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CapturePipelineImpl.this.torchApplyCapture(null, 0, 0L, null, false, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$torchAsFlashCapture$1 */
    static final class C01381 extends ContinuationImpl {
        int I$0;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        C01381(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CapturePipelineImpl.this.torchAsFlashCapture(null, 0, 0, null, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$unlockAf$1 */
    static final class C01391 extends ContinuationImpl {
        long J$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C01391(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CapturePipelineImpl.this.unlockAf(0L, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$waitForResult$1 */
    static final class C01401 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C01401(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CapturePipelineImpl.this.waitForResult(0L, null, this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean waitForResult$lambda$0(FrameInfo frameInfo) {
        Intrinsics.checkNotNullParameter(frameInfo, "<unused var>");
        return true;
    }

    /* JADX WARN: Type inference failed for: r2v6, types: [androidx.camera.camera2.impl.CapturePipelineImpl$emptyRequestMetadata$1] */
    public CapturePipelineImpl(CaptureConfigAdapter configAdapter, FlashControl flashControl, TorchControl torchControl, VideoUsageControl videoUsageControl, UseCaseThreads threads, ComboRequestListener requestListener, UseTorchAsFlash useTorchAsFlash, final CameraProperties cameraProperties, Provider useCaseCameraStateProvider, UseCaseGraphContext useCaseGraphContext) {
        Intrinsics.checkNotNullParameter(configAdapter, "configAdapter");
        Intrinsics.checkNotNullParameter(flashControl, "flashControl");
        Intrinsics.checkNotNullParameter(torchControl, "torchControl");
        Intrinsics.checkNotNullParameter(videoUsageControl, "videoUsageControl");
        Intrinsics.checkNotNullParameter(threads, "threads");
        Intrinsics.checkNotNullParameter(requestListener, "requestListener");
        Intrinsics.checkNotNullParameter(useTorchAsFlash, "useTorchAsFlash");
        Intrinsics.checkNotNullParameter(cameraProperties, "cameraProperties");
        Intrinsics.checkNotNullParameter(useCaseCameraStateProvider, "useCaseCameraStateProvider");
        Intrinsics.checkNotNullParameter(useCaseGraphContext, "useCaseGraphContext");
        this.configAdapter = configAdapter;
        this.flashControl = flashControl;
        this.torchControl = torchControl;
        this.videoUsageControl = videoUsageControl;
        this.threads = threads;
        this.requestListener = requestListener;
        this.useTorchAsFlash = useTorchAsFlash;
        this.useCaseCameraStateProvider = useCaseCameraStateProvider;
        this.useCaseGraphContext = useCaseGraphContext;
        this.hasFlashUnit$delegate = LazyKt.lazy(new Function0() { // from class: androidx.camera.camera2.impl.CapturePipelineImpl$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(CapturePipelineImpl.hasFlashUnit_delegate$lambda$0(cameraProperties));
            }
        });
        this.useCaseCameraState$delegate = LazyKt.lazy(new Function0() { // from class: androidx.camera.camera2.impl.CapturePipelineImpl$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CapturePipelineImpl.useCaseCameraState_delegate$lambda$0(this.f$0);
            }
        });
        this.template = 1;
        this.emptyRequestMetadata = new RequestMetadata() { // from class: androidx.camera.camera2.impl.CapturePipelineImpl$emptyRequestMetadata$1
            private final int template = RequestTemplate.m1754constructorimpl(0);
            private final Map streams = MapsKt.emptyMap();
            private final boolean repeating = true;
            private final Request request = new Request(CollectionsKt.emptyList(), null, null, null, null, null, 62, null);
            private final long requestNumber = RequestNumber.m1748constructorimpl(0);

            @Override // androidx.camera.camera2.pipe.Metadata
            public Object get(Metadata.Key key) {
                Intrinsics.checkNotNullParameter(key, "key");
                return null;
            }

            @Override // androidx.camera.camera2.pipe.Metadata
            public Object getOrDefault(Metadata.Key key, Object obj) {
                Intrinsics.checkNotNullParameter(key, "key");
                return obj;
            }

            @Override // androidx.camera.camera2.pipe.UnsafeWrapper
            public Object unwrapAs(KClass type) {
                Intrinsics.checkNotNullParameter(type, "type");
                return null;
            }

            @Override // androidx.camera.camera2.pipe.RequestMetadata
            public Map getStreams() {
                return this.streams;
            }

            @Override // androidx.camera.camera2.pipe.RequestMetadata
            public boolean getRepeating() {
                return this.repeating;
            }

            @Override // androidx.camera.camera2.pipe.RequestMetadata
            public Request getRequest() {
                return this.request;
            }

            @Override // androidx.camera.camera2.pipe.RequestMetadata
            /* JADX INFO: renamed from: getRequestNumber-my6kx4g, reason: not valid java name */
            public long mo1442getRequestNumbermy6kx4g() {
                return this.requestNumber;
            }
        };
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    private static final class PipelineTask {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ PipelineTask[] $VALUES;
        public static final PipelineTask PRE_CAPTURE = new PipelineTask("PRE_CAPTURE", 0);
        public static final PipelineTask MAIN_CAPTURE = new PipelineTask("MAIN_CAPTURE", 1);
        public static final PipelineTask POST_CAPTURE = new PipelineTask("POST_CAPTURE", 2);

        private static final /* synthetic */ PipelineTask[] $values() {
            return new PipelineTask[]{PRE_CAPTURE, MAIN_CAPTURE, POST_CAPTURE};
        }

        public static PipelineTask valueOf(String str) {
            return (PipelineTask) Enum.valueOf(PipelineTask.class, str);
        }

        public static PipelineTask[] values() {
            return (PipelineTask[]) $VALUES.clone();
        }

        private PipelineTask(String str, int i) {
        }

        static {
            PipelineTask[] pipelineTaskArr$values = $values();
            $VALUES = pipelineTaskArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(pipelineTaskArr$values);
        }
    }

    private static final class MainCaptureParams {
        private final List configs;
        private final int requestTemplate;
        private final Config sessionConfigOptions;

        public /* synthetic */ MainCaptureParams(List list, int i, Config config, DefaultConstructorMarker defaultConstructorMarker) {
            this(list, i, config);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof MainCaptureParams)) {
                return false;
            }
            MainCaptureParams mainCaptureParams = (MainCaptureParams) obj;
            return Intrinsics.areEqual(this.configs, mainCaptureParams.configs) && RequestTemplate.m1756equalsimpl0(this.requestTemplate, mainCaptureParams.requestTemplate) && Intrinsics.areEqual(this.sessionConfigOptions, mainCaptureParams.sessionConfigOptions);
        }

        public int hashCode() {
            return (((this.configs.hashCode() * 31) + RequestTemplate.m1758hashCodeimpl(this.requestTemplate)) * 31) + this.sessionConfigOptions.hashCode();
        }

        public String toString() {
            return "MainCaptureParams(configs=" + this.configs + ", requestTemplate=" + ((Object) RequestTemplate.m1759toStringimpl(this.requestTemplate)) + ", sessionConfigOptions=" + this.sessionConfigOptions + ')';
        }

        private MainCaptureParams(List configs, int i, Config sessionConfigOptions) {
            Intrinsics.checkNotNullParameter(configs, "configs");
            Intrinsics.checkNotNullParameter(sessionConfigOptions, "sessionConfigOptions");
            this.configs = configs;
            this.requestTemplate = i;
            this.sessionConfigOptions = sessionConfigOptions;
        }

        public final List getConfigs() {
            return this.configs;
        }

        /* JADX INFO: renamed from: getRequestTemplate-fGx8uWA, reason: not valid java name */
        public final int m1441getRequestTemplatefGx8uWA() {
            return this.requestTemplate;
        }

        public final Config getSessionConfigOptions() {
            return this.sessionConfigOptions;
        }
    }

    private final boolean getHasFlashUnit() {
        return ((Boolean) this.hasFlashUnit$delegate.getValue()).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean hasFlashUnit_delegate$lambda$0(CameraProperties cameraProperties) {
        return FlashAvailabilityCheckerKt.isFlashAvailable$default(cameraProperties, false, 1, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final UseCaseCameraState getUseCaseCameraState() {
        return (UseCaseCameraState) this.useCaseCameraState$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final UseCaseCameraState useCaseCameraState_delegate$lambda$0(CapturePipelineImpl capturePipelineImpl) {
        return (UseCaseCameraState) capturePipelineImpl.useCaseCameraStateProvider.get();
    }

    public int getTemplate() {
        return this.template;
    }

    @Override // androidx.camera.camera2.impl.CapturePipeline
    public void setTemplate(int i) {
        this.template = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:32:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getFrameMetadata(kotlin.coroutines.Continuation r10) {
        /*
            r9 = this;
            boolean r0 = r10 instanceof androidx.camera.camera2.impl.CapturePipelineImpl.C01281
            if (r0 == 0) goto L14
            r0 = r10
            androidx.camera.camera2.impl.CapturePipelineImpl$getFrameMetadata$1 r0 = (androidx.camera.camera2.impl.CapturePipelineImpl.C01281) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L14
            int r1 = r1 - r2
            r0.label = r1
        L12:
            r5 = r0
            goto L1a
        L14:
            androidx.camera.camera2.impl.CapturePipelineImpl$getFrameMetadata$1 r0 = new androidx.camera.camera2.impl.CapturePipelineImpl$getFrameMetadata$1
            r0.<init>(r10)
            goto L12
        L1a:
            java.lang.Object r10 = r5.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r5.label
            java.lang.String r8 = "CXCP"
            r2 = 1
            if (r1 == 0) goto L3a
            if (r1 != r2) goto L32
            java.lang.Object r0 = r5.L$0
            androidx.camera.camera2.impl.CapturePipelineImpl r0 = (androidx.camera.camera2.impl.CapturePipelineImpl) r0
            kotlin.ResultKt.throwOnFailure(r10)
            r1 = r9
            goto L67
        L32:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r0)
            throw r10
        L3a:
            kotlin.ResultKt.throwOnFailure(r10)
            androidx.camera.camera2.pipe.FrameMetadata r10 = r9.frameMetadata
            if (r10 != 0) goto L74
            androidx.camera.camera2.impl.Camera2Logger r10 = androidx.camera.camera2.impl.Camera2Logger.INSTANCE
            boolean r10 = androidx.camera.core.Logger.isDebugEnabled(r8)
            if (r10 == 0) goto L52
            java.lang.String r10 = androidx.camera.camera2.impl.Camera2Logger.access$getTRUNCATED_TAG$p()
            java.lang.String r1 = "getFrameMetadata: waiting for result"
            android.util.Log.d(r10, r1)
        L52:
            r10 = r2
            long r2 = androidx.camera.camera2.impl.CapturePipelineKt.access$getCHECK_FLASH_REQUIRED_TIMEOUT_IN_NS$p()
            r5.L$0 = r9
            r5.label = r10
            r4 = 0
            r6 = 2
            r7 = 0
            r1 = r9
            java.lang.Object r10 = waitForResult$default(r1, r2, r4, r5, r6, r7)
            if (r10 != r0) goto L66
            return r0
        L66:
            r0 = r1
        L67:
            androidx.camera.camera2.pipe.FrameInfo r10 = (androidx.camera.camera2.pipe.FrameInfo) r10
            if (r10 == 0) goto L70
            androidx.camera.camera2.pipe.FrameMetadata r10 = r10.getMetadata()
            goto L71
        L70:
            r10 = 0
        L71:
            r0.frameMetadata = r10
            goto L75
        L74:
            r1 = r9
        L75:
            androidx.camera.camera2.impl.Camera2Logger r10 = androidx.camera.camera2.impl.Camera2Logger.INSTANCE
            boolean r10 = androidx.camera.core.Logger.isDebugEnabled(r8)
            if (r10 == 0) goto L99
            java.lang.String r10 = androidx.camera.camera2.impl.Camera2Logger.access$getTRUNCATED_TAG$p()
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "getFrameMetadata: frameMetadata = "
            r0.append(r2)
            androidx.camera.camera2.pipe.FrameMetadata r2 = access$getFrameMetadata$p(r9)
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r10, r0)
        L99:
            androidx.camera.camera2.pipe.FrameMetadata r10 = r1.frameMetadata
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl.getFrameMetadata(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeCaptureTasks(java.util.List r10, int r11, int r12, int r13, androidx.camera.camera2.impl.CapturePipelineImpl.MainCaptureParams r14, kotlin.coroutines.Continuation r15) {
        /*
            Method dump skipped, instruction units count: 240
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl.invokeCaptureTasks(java.util.List, int, int, int, androidx.camera.camera2.impl.CapturePipelineImpl$MainCaptureParams, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // androidx.camera.camera2.impl.CapturePipeline
    /* JADX INFO: renamed from: submitStillCaptures-BvXKQx0 */
    public Object mo1413submitStillCapturesBvXKQx0(List list, int i, Config config, int i2, int i3, int i4, Continuation continuation) {
        return invokeCaptureTasks(CollectionsKt.listOf((Object[]) new PipelineTask[]{PipelineTask.PRE_CAPTURE, PipelineTask.MAIN_CAPTURE, PipelineTask.POST_CAPTURE}), i2, i4, i3, new MainCaptureParams(list, i, config, null), continuation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00bd A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00be A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object torchAsFlashCapture(androidx.camera.camera2.impl.CapturePipelineImpl.MainCaptureParams r10, int r11, int r12, java.util.List r13, kotlin.coroutines.Continuation r14) {
        /*
            r9 = this;
            boolean r0 = r14 instanceof androidx.camera.camera2.impl.CapturePipelineImpl.C01381
            if (r0 == 0) goto L14
            r0 = r14
            androidx.camera.camera2.impl.CapturePipelineImpl$torchAsFlashCapture$1 r0 = (androidx.camera.camera2.impl.CapturePipelineImpl.C01381) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L14
            int r1 = r1 - r2
            r0.label = r1
        L12:
            r8 = r0
            goto L1a
        L14:
            androidx.camera.camera2.impl.CapturePipelineImpl$torchAsFlashCapture$1 r0 = new androidx.camera.camera2.impl.CapturePipelineImpl$torchAsFlashCapture$1
            r0.<init>(r14)
            goto L12
        L1a:
            java.lang.Object r14 = r8.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r8.label
            r2 = 3
            r3 = 2
            r4 = 1
            r5 = 0
            if (r1 == 0) goto L4e
            if (r1 == r4) goto L3e
            if (r1 == r3) goto L3a
            if (r1 != r2) goto L32
            kotlin.ResultKt.throwOnFailure(r14)
            return r14
        L32:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L3a:
            kotlin.ResultKt.throwOnFailure(r14)
            return r14
        L3e:
            int r11 = r8.I$0
            java.lang.Object r10 = r8.L$1
            r13 = r10
            java.util.List r13 = (java.util.List) r13
            java.lang.Object r10 = r8.L$0
            androidx.camera.camera2.impl.CapturePipelineImpl$MainCaptureParams r10 = (androidx.camera.camera2.impl.CapturePipelineImpl.MainCaptureParams) r10
            kotlin.ResultKt.throwOnFailure(r14)
        L4c:
            r6 = r13
            goto L7a
        L4e:
            kotlin.ResultKt.throwOnFailure(r14)
            androidx.camera.camera2.impl.Camera2Logger r14 = androidx.camera.camera2.impl.Camera2Logger.INSTANCE
            java.lang.String r14 = "CXCP"
            boolean r14 = androidx.camera.core.Logger.isDebugEnabled(r14)
            if (r14 == 0) goto L64
            java.lang.String r14 = androidx.camera.camera2.impl.Camera2Logger.access$getTRUNCATED_TAG$p()
            java.lang.String r1 = "CapturePipeline#torchAsFlashCapture"
            android.util.Log.d(r14, r1)
        L64:
            boolean r14 = r9.getHasFlashUnit()
            if (r14 == 0) goto Laf
            r8.L$0 = r10
            r8.L$1 = r13
            r8.I$0 = r11
            r8.label = r4
            java.lang.Object r14 = r9.isPhysicalFlashRequired(r12, r8)
            if (r14 != r0) goto L4c
            r1 = r9
            goto Lbd
        L7a:
            java.lang.Boolean r14 = (java.lang.Boolean) r14
            boolean r12 = r14.booleanValue()
            if (r12 == 0) goto Lad
            r12 = r4
            r14 = r5
            long r4 = androidx.camera.camera2.impl.CapturePipelineKt.access$getCHECK_3A_WITH_FLASH_TIMEOUT_IN_NS$p()
            androidx.camera.camera2.compat.workaround.UseTorchAsFlash r13 = r9.useTorchAsFlash
            boolean r13 = r13.shouldDisableAePrecapture()
            if (r13 != 0) goto L9a
            androidx.camera.camera2.impl.VideoUsageControl r13 = r9.videoUsageControl
            boolean r13 = r13.isInVideoUsage()
            if (r13 != 0) goto L9a
        L98:
            r7 = r12
            goto L9c
        L9a:
            r12 = 0
            goto L98
        L9c:
            r8.L$0 = r14
            r8.L$1 = r14
            r8.label = r3
            r1 = r9
            r2 = r10
            r3 = r11
            java.lang.Object r10 = r1.torchApplyCapture(r2, r3, r4, r6, r7, r8)
            if (r10 != r0) goto Lac
            goto Lbd
        Lac:
            return r10
        Lad:
            r3 = r11
            r13 = r6
        Laf:
            r1 = r9
            r14 = r5
            r8.L$0 = r14
            r8.L$1 = r14
            r8.label = r2
            java.lang.Object r10 = r9.defaultNoFlashCapture(r10, r11, r13, r8)
            if (r10 != r0) goto Lbe
        Lbd:
            return r0
        Lbe:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl.torchAsFlashCapture(androidx.camera.camera2.impl.CapturePipelineImpl$MainCaptureParams, int, int, java.util.List, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00ad A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object defaultCapture(androidx.camera.camera2.impl.CapturePipelineImpl.MainCaptureParams r9, int r10, int r11, java.util.List r12, kotlin.coroutines.Continuation r13) {
        /*
            r8 = this;
            boolean r0 = r13 instanceof androidx.camera.camera2.impl.CapturePipelineImpl.C01261
            if (r0 == 0) goto L14
            r0 = r13
            androidx.camera.camera2.impl.CapturePipelineImpl$defaultCapture$1 r0 = (androidx.camera.camera2.impl.CapturePipelineImpl.C01261) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L14
            int r1 = r1 - r2
            r0.label = r1
        L12:
            r7 = r0
            goto L1a
        L14:
            androidx.camera.camera2.impl.CapturePipelineImpl$defaultCapture$1 r0 = new androidx.camera.camera2.impl.CapturePipelineImpl$defaultCapture$1
            r0.<init>(r13)
            goto L12
        L1a:
            java.lang.Object r13 = r7.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r7.label
            r2 = 4
            r3 = 3
            r4 = 2
            r5 = 1
            if (r1 == 0) goto L56
            if (r1 == r5) goto L44
            if (r1 == r4) goto L40
            if (r1 == r3) goto L3c
            if (r1 != r2) goto L34
            kotlin.ResultKt.throwOnFailure(r13)
            return r13
        L34:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L3c:
            kotlin.ResultKt.throwOnFailure(r13)
            return r13
        L40:
            kotlin.ResultKt.throwOnFailure(r13)
            return r13
        L44:
            int r10 = r7.I$0
            java.lang.Object r9 = r7.L$1
            r12 = r9
            java.util.List r12 = (java.util.List) r12
            java.lang.Object r9 = r7.L$0
            androidx.camera.camera2.impl.CapturePipelineImpl$MainCaptureParams r9 = (androidx.camera.camera2.impl.CapturePipelineImpl.MainCaptureParams) r9
            kotlin.ResultKt.throwOnFailure(r13)
        L52:
            r2 = r9
            r5 = r10
            r6 = r12
            goto L6f
        L56:
            kotlin.ResultKt.throwOnFailure(r13)
            boolean r13 = r8.getHasFlashUnit()
            if (r13 == 0) goto La4
            r7.L$0 = r9
            r7.L$1 = r12
            r7.I$0 = r10
            r7.label = r5
            java.lang.Object r13 = r8.isPhysicalFlashRequired(r11, r7)
            if (r13 != r0) goto L52
        L6d:
            r1 = r8
            goto Lad
        L6f:
            java.lang.Boolean r13 = (java.lang.Boolean) r13
            boolean r9 = r13.booleanValue()
            if (r9 == 0) goto L7c
            long r10 = androidx.camera.camera2.impl.CapturePipelineKt.access$getCHECK_3A_WITH_FLASH_TIMEOUT_IN_NS$p()
            goto L80
        L7c:
            long r10 = androidx.camera.camera2.impl.CapturePipelineKt.access$getCHECK_3A_TIMEOUT_IN_NS$p()
        L80:
            r12 = 0
            if (r9 != 0) goto L94
            if (r5 != 0) goto L86
            goto L94
        L86:
            r7.L$0 = r12
            r7.L$1 = r12
            r7.label = r3
            java.lang.Object r9 = r8.defaultNoFlashCapture(r2, r5, r6, r7)
            if (r9 != r0) goto L93
            goto L6d
        L93:
            return r9
        L94:
            r7.L$0 = r12
            r7.L$1 = r12
            r7.label = r4
            r1 = r8
            r3 = r10
            java.lang.Object r9 = r1.aePreCaptureApplyCapture(r2, r3, r5, r6, r7)
            if (r9 != r0) goto La3
            goto Lad
        La3:
            return r9
        La4:
            r1 = r8
            r7.label = r2
            java.lang.Object r9 = r8.defaultNoFlashCapture(r9, r10, r12, r7)
            if (r9 != r0) goto Lae
        Lad:
            return r0
        Lae:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl.defaultCapture(androidx.camera.camera2.impl.CapturePipelineImpl$MainCaptureParams, int, int, java.util.List, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object defaultNoFlashCapture(androidx.camera.camera2.impl.CapturePipelineImpl.MainCaptureParams r17, int r18, java.util.List r19, kotlin.coroutines.Continuation r20) {
        /*
            Method dump skipped, instruction units count: 329
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl.defaultNoFlashCapture(androidx.camera.camera2.impl.CapturePipelineImpl$MainCaptureParams, int, java.util.List, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:111:0x02f0  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x033a  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0350  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x036f  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x039c  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x03ac  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01bf  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01db  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x021f  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0223  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0226  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0258  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x027d  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x027f  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x028e  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x02b4  */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v26 */
    /* JADX WARN: Type inference failed for: r7v3, types: [java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r7v33 */
    /* JADX WARN: Type inference failed for: r7v35, types: [java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r7v49 */
    /* JADX WARN: Type inference failed for: r7v50 */
    /* JADX WARN: Type inference failed for: r7v51 */
    /* JADX WARN: Type inference failed for: r7v52 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object torchApplyCapture(androidx.camera.camera2.impl.CapturePipelineImpl.MainCaptureParams r26, int r27, long r28, java.util.List r30, boolean r31, kotlin.coroutines.Continuation r32) {
        /*
            Method dump skipped, instruction units count: 1002
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl.torchApplyCapture(androidx.camera.camera2.impl.CapturePipelineImpl$MainCaptureParams, int, long, java.util.List, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't wrap try/catch for region: R(16:0|2|(2:4|(1:6)(1:8))(0)|7|9|(4:(1:(1:(1:(14:14|108|15|68|(1:70)|71|72|(1:74)|75|85|(3:87|(1:89)|(3:91|(1:93)|94)(2:95|96))(1:97)|98|(1:100)|101)(2:19|20))(7:21|102|22|23|64|(12:67|68|(0)|71|72|(0)|75|85|(0)(0)|98|(0)|101)|66))(1:26))(6:27|(1:29)|30|(1:32)|33|(7:35|(1:37)|38|(1:40)|41|(1:44)|66)(6:84|85|(0)(0)|98|(0)|101))|104|60|(2:62|66)(4:63|64|(0)|66))|45|106|46|(2:110|48)|52|(1:54)(1:55)|(1:57)|58|59|(2:(1:113)|(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x01ab, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x01ac, code lost:
    
        r15 = r7;
     */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0201  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x017a  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0186 A[Catch: all -> 0x004c, TryCatch #3 {all -> 0x004c, blocks: (B:15:0x0047, B:68:0x017e, B:70:0x0186, B:71:0x018f), top: B:108:0x0047 }] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x019b  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01c1  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x001a  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01f0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object aePreCaptureApplyCapture(androidx.camera.camera2.impl.CapturePipelineImpl.MainCaptureParams r22, long r23, int r25, java.util.List r26, kotlin.coroutines.Continuation r27) {
        /*
            Method dump skipped, instruction units count: 544
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl.aePreCaptureApplyCapture(androidx.camera.camera2.impl.CapturePipelineImpl$MainCaptureParams, long, int, java.util.List, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00f5  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object screenFlashCapture(androidx.camera.camera2.impl.CapturePipelineImpl.MainCaptureParams r9, int r10, java.util.List r11, kotlin.coroutines.Continuation r12) {
        /*
            Method dump skipped, instruction units count: 264
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl.screenFlashCapture(androidx.camera.camera2.impl.CapturePipelineImpl$MainCaptureParams, int, java.util.List, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x00bf, code lost:
    
        if (r15 != r0) goto L50;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:36:0x008a A[Catch: all -> 0x0094, TryCatch #0 {all -> 0x0094, blocks: (B:34:0x007f, B:36:0x008a, B:39:0x009a, B:43:0x00a2), top: B:60:0x007f }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
    /* JADX WARN: Type inference failed for: r14v0, types: [int] */
    /* JADX WARN: Type inference failed for: r14v1 */
    /* JADX WARN: Type inference failed for: r14v10, types: [java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r14v19 */
    /* JADX WARN: Type inference failed for: r14v2, types: [java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r14v20 */
    /* JADX WARN: Type inference failed for: r14v22 */
    /* JADX WARN: Type inference failed for: r14v23 */
    /* JADX WARN: Type inference failed for: r14v24 */
    /* JADX WARN: Type inference failed for: r14v25 */
    /* JADX WARN: Type inference failed for: r14v26 */
    /* JADX WARN: Type inference failed for: r14v3, types: [int] */
    /* JADX WARN: Type inference failed for: r14v4 */
    /* JADX WARN: Type inference failed for: r14v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeScreenFlashPreCaptureTasks(int r14, kotlin.coroutines.Continuation r15) {
        /*
            Method dump skipped, instruction units count: 243
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl.invokeScreenFlashPreCaptureTasks(int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x008c A[Catch: all -> 0x0096, TryCatch #0 {all -> 0x0096, blocks: (B:32:0x0081, B:34:0x008c, B:40:0x009f), top: B:54:0x0081 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00b3 A[Catch: all -> 0x0036, TryCatch #2 {all -> 0x0036, blocks: (B:14:0x0031, B:44:0x00ab, B:46:0x00b3, B:47:0x00bc), top: B:58:0x0031 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeScreenFlashPostCaptureTasks(int r10, kotlin.coroutines.Continuation r11) {
        /*
            Method dump skipped, instruction units count: 203
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl.invokeScreenFlashPostCaptureTasks(int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't wrap try/catch for region: R(10:0|2|(2:4|(1:6)(1:7))(0)|8|(7:(1:(1:(1:(2:13|14)(2:15|16))(7:17|50|18|19|33|34|(1:55)(1:37)))(1:22))(2:23|(2:25|36)(1:26))|46|30|(2:32|54)|33|34|(0)(0))|27|48|28|29|(2:(0)|(1:53))) */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00db, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00dc, code lost:
    
        r2 = r7;
     */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00d6 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:55:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object lockAf(long r26, boolean r28, kotlin.coroutines.Continuation r29) {
        /*
            Method dump skipped, instruction units count: 228
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl.lockAf(long, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final Function1 getConvergeCondition(final boolean z) {
        return new Function1() { // from class: androidx.camera.camera2.impl.CapturePipelineImpl$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(CapturePipelineImpl.getConvergeCondition$lambda$0(this.f$0, z, (FrameMetadata) obj));
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean getConvergeCondition$lambda$0(CapturePipelineImpl capturePipelineImpl, boolean z, FrameMetadata frameMetadata) {
        Intrinsics.checkNotNullParameter(frameMetadata, "frameMetadata");
        return ConvergenceUtils.is3AConverged(capturePipelineImpl.toCameraCaptureResult(frameMetadata), z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final CameraCaptureResult toCameraCaptureResult(final FrameMetadata frameMetadata) {
        return new CaptureResultAdapter(this.emptyRequestMetadata, frameMetadata.mo1641getFrameNumberUgla2oM(), new FrameInfo(frameMetadata, this) { // from class: androidx.camera.camera2.impl.CapturePipelineImpl$toCameraCaptureResult$frameInfo$1
            private final String camera;
            private final FrameMetadata frameMetadata;
            private final long frameNumber;
            private final FrameMetadata metadata;
            private final RequestMetadata requestMetadata;

            @Override // androidx.camera.camera2.pipe.UnsafeWrapper
            public Object unwrapAs(KClass type) {
                Intrinsics.checkNotNullParameter(type, "type");
                return null;
            }

            {
                this.frameMetadata = frameMetadata;
                this.metadata = frameMetadata;
                this.camera = frameMetadata.mo1640getCameraDz_R5H8();
                this.frameNumber = frameMetadata.mo1641getFrameNumberUgla2oM();
                this.requestMetadata = this.emptyRequestMetadata;
            }

            @Override // androidx.camera.camera2.pipe.FrameInfo
            public FrameMetadata getMetadata() {
                return this.metadata;
            }
        }, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0093 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0018  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object unlockAf(long r17, kotlin.coroutines.Continuation r19) {
        /*
            r16 = this;
            r1 = r16
            r0 = r19
            boolean r2 = r0 instanceof androidx.camera.camera2.impl.CapturePipelineImpl.C01391
            if (r2 == 0) goto L18
            r2 = r0
            androidx.camera.camera2.impl.CapturePipelineImpl$unlockAf$1 r2 = (androidx.camera.camera2.impl.CapturePipelineImpl.C01391) r2
            int r3 = r2.label
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            r5 = r3 & r4
            if (r5 == 0) goto L18
            int r3 = r3 - r4
            r2.label = r3
        L16:
            r11 = r2
            goto L1e
        L18:
            androidx.camera.camera2.impl.CapturePipelineImpl$unlockAf$1 r2 = new androidx.camera.camera2.impl.CapturePipelineImpl$unlockAf$1
            r2.<init>(r0)
            goto L16
        L1e:
            java.lang.Object r0 = r11.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r11.label
            r14 = 3
            r4 = 2
            r5 = 1
            if (r3 == 0) goto L4f
            if (r3 == r5) goto L48
            if (r3 == r4) goto L3d
            if (r3 != r14) goto L35
            kotlin.ResultKt.throwOnFailure(r0)
            return r0
        L35:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L3d:
            java.lang.Object r3 = r11.L$0
            java.lang.AutoCloseable r3 = (java.lang.AutoCloseable) r3
            kotlin.ResultKt.throwOnFailure(r0)     // Catch: java.lang.Throwable -> L45
            goto L82
        L45:
            r0 = move-exception
            r2 = r0
            goto L97
        L48:
            long r6 = r11.J$0
            kotlin.ResultKt.throwOnFailure(r0)
        L4d:
            r9 = r6
            goto L65
        L4f:
            kotlin.ResultKt.throwOnFailure(r0)
            androidx.camera.camera2.config.UseCaseGraphContext r0 = r1.useCaseGraphContext
            androidx.camera.camera2.pipe.CameraGraph r0 = r0.getGraph()
            r6 = r17
            r11.J$0 = r6
            r11.label = r5
            java.lang.Object r0 = r0.acquireSession(r11)
            if (r0 != r2) goto L4d
            goto L92
        L65:
            r15 = r0
            java.lang.AutoCloseable r15 = (java.lang.AutoCloseable) r15
            r3 = r15
            androidx.camera.camera2.pipe.CameraGraph$Session r3 = (androidx.camera.camera2.pipe.CameraGraph.Session) r3     // Catch: java.lang.Throwable -> L94
            java.lang.Boolean r5 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r5)     // Catch: java.lang.Throwable -> L94
            r11.L$0 = r15     // Catch: java.lang.Throwable -> L94
            r11.label = r4     // Catch: java.lang.Throwable -> L94
            r4 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r12 = 29
            r13 = 0
            java.lang.Object r0 = androidx.camera.camera2.pipe.CameraGraph.Session.CC.unlock3A$default(r3, r4, r5, r6, r7, r8, r9, r11, r12, r13)     // Catch: java.lang.Throwable -> L94
            if (r0 != r2) goto L81
            goto L92
        L81:
            r3 = r15
        L82:
            kotlinx.coroutines.Deferred r0 = (kotlinx.coroutines.Deferred) r0     // Catch: java.lang.Throwable -> L45
            r4 = 0
            kotlin.jdk7.AutoCloseableKt.closeFinally(r3, r4)
            r11.L$0 = r4
            r11.label = r14
            java.lang.Object r0 = r0.await(r11)
            if (r0 != r2) goto L93
        L92:
            return r2
        L93:
            return r0
        L94:
            r0 = move-exception
            r2 = r0
            r3 = r15
        L97:
            throw r2     // Catch: java.lang.Throwable -> L98
        L98:
            r0 = move-exception
            kotlin.jdk7.AutoCloseableKt.closeFinally(r3, r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl.unlockAf(long, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final List submitRequestInternal(MainCaptureParams mainCaptureParams) {
        Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
        if (Logger.isDebugEnabled("CXCP")) {
            Log.d(Camera2Logger.TRUNCATED_TAG, "CapturePipeline#submitRequestInternal; Submitting " + mainCaptureParams.getConfigs() + " with CameraPipe");
        }
        ArrayList arrayList = new ArrayList();
        List configs = mainCaptureParams.getConfigs();
        ArrayList arrayList2 = new ArrayList();
        Iterator it = configs.iterator();
        while (true) {
            Request requestM1385mapToRequestnAberiA = null;
            if (!it.hasNext()) {
                break;
            }
            CaptureConfig captureConfig = (CaptureConfig) it.next();
            final CompletableDeferred completableDeferredCompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default(null, 1, null);
            arrayList.add(completableDeferredCompletableDeferred$default);
            try {
                requestM1385mapToRequestnAberiA = this.configAdapter.m1385mapToRequestnAberiA(captureConfig, mainCaptureParams.m1441getRequestTemplatefGx8uWA(), mainCaptureParams.getSessionConfigOptions(), CollectionsKt.listOf(new Request.Listener() { // from class: androidx.camera.camera2.impl.CapturePipelineImpl$submitRequestInternal$requests$1$1
                    @Override // androidx.camera.camera2.pipe.Request.Listener
                    /* JADX INFO: renamed from: onBufferLost-DlC0U5Y */
                    public /* synthetic */ void mo1387onBufferLostDlC0U5Y(RequestMetadata requestMetadata, long j, int i) {
                        Intrinsics.checkNotNullParameter(requestMetadata, "requestMetadata");
                    }

                    @Override // androidx.camera.camera2.pipe.Request.Listener
                    /* JADX INFO: renamed from: onBufferLost-iiEMlm4 */
                    public /* synthetic */ void mo1388onBufferLostiiEMlm4(RequestMetadata requestMetadata, long j, int i, int i2) {
                        Intrinsics.checkNotNullParameter(requestMetadata, "requestMetadata");
                    }

                    @Override // androidx.camera.camera2.pipe.Request.Listener
                    public /* synthetic */ void onCaptureProgress(RequestMetadata requestMetadata, int i) {
                        Intrinsics.checkNotNullParameter(requestMetadata, "requestMetadata");
                    }

                    @Override // androidx.camera.camera2.pipe.Request.Listener
                    /* JADX INFO: renamed from: onComplete-CcXjc1I */
                    public /* synthetic */ void mo1389onCompleteCcXjc1I(RequestMetadata requestMetadata, long j, FrameInfo frameInfo) {
                        Request.Listener.CC.m1740$default$onCompleteCcXjc1I(this, requestMetadata, j, frameInfo);
                    }

                    @Override // androidx.camera.camera2.pipe.Request.Listener
                    /* JADX INFO: renamed from: onPartialCaptureResult-CcXjc1I */
                    public /* synthetic */ void mo1391onPartialCaptureResultCcXjc1I(RequestMetadata requestMetadata, long j, FrameMetadata frameMetadata) {
                        Request.Listener.CC.m1742$default$onPartialCaptureResultCcXjc1I(this, requestMetadata, j, frameMetadata);
                    }

                    @Override // androidx.camera.camera2.pipe.Request.Listener
                    /* JADX INFO: renamed from: onReadoutStarted-mP9r-9w */
                    public /* synthetic */ void mo1392onReadoutStartedmP9r9w(RequestMetadata requestMetadata, long j, long j2) {
                        Intrinsics.checkNotNullParameter(requestMetadata, "requestMetadata");
                    }

                    @Override // androidx.camera.camera2.pipe.Request.Listener
                    public /* synthetic */ void onRequestSequenceAborted(RequestMetadata requestMetadata) {
                        Intrinsics.checkNotNullParameter(requestMetadata, "requestMetadata");
                    }

                    @Override // androidx.camera.camera2.pipe.Request.Listener
                    /* JADX INFO: renamed from: onRequestSequenceCompleted-RuT0dZU */
                    public /* synthetic */ void mo1393onRequestSequenceCompletedRuT0dZU(RequestMetadata requestMetadata, long j) {
                        Intrinsics.checkNotNullParameter(requestMetadata, "requestMetadata");
                    }

                    @Override // androidx.camera.camera2.pipe.Request.Listener
                    public /* synthetic */ void onRequestSequenceCreated(RequestMetadata requestMetadata) {
                        Intrinsics.checkNotNullParameter(requestMetadata, "requestMetadata");
                    }

                    @Override // androidx.camera.camera2.pipe.Request.Listener
                    public /* synthetic */ void onRequestSequenceSubmitted(RequestMetadata requestMetadata) {
                        Intrinsics.checkNotNullParameter(requestMetadata, "requestMetadata");
                    }

                    @Override // androidx.camera.camera2.pipe.Request.Listener
                    /* JADX INFO: renamed from: onStarted-uGKBvU4 */
                    public /* synthetic */ void mo1394onStarteduGKBvU4(RequestMetadata requestMetadata, long j, long j2) {
                        Intrinsics.checkNotNullParameter(requestMetadata, "requestMetadata");
                    }

                    @Override // androidx.camera.camera2.pipe.Request.Listener
                    public void onAborted(Request request) {
                        Intrinsics.checkNotNullParameter(request, "request");
                        completableDeferredCompletableDeferred$default.completeExceptionally(new ImageCaptureException(3, "Capture request is cancelled because camera is closed", null));
                    }

                    @Override // androidx.camera.camera2.pipe.Request.Listener
                    /* JADX INFO: renamed from: onTotalCaptureResult-CcXjc1I */
                    public void mo1395onTotalCaptureResultCcXjc1I(RequestMetadata requestMetadata, long j, FrameInfo totalCaptureResult) {
                        Intrinsics.checkNotNullParameter(requestMetadata, "requestMetadata");
                        Intrinsics.checkNotNullParameter(totalCaptureResult, "totalCaptureResult");
                        completableDeferredCompletableDeferred$default.complete(null);
                    }

                    @Override // androidx.camera.camera2.pipe.Request.Listener
                    /* JADX INFO: renamed from: onFailed-CcXjc1I */
                    public void mo1390onFailedCcXjc1I(RequestMetadata requestMetadata, long j, RequestFailure requestFailure) {
                        Intrinsics.checkNotNullParameter(requestMetadata, "requestMetadata");
                        Intrinsics.checkNotNullParameter(requestFailure, "requestFailure");
                        completableDeferredCompletableDeferred$default.completeExceptionally(new ImageCaptureException(2, "Capture request failed with reason " + requestFailure.getReason(), null));
                    }
                }));
            } catch (IllegalStateException e) {
                Camera2Logger camera2Logger2 = Camera2Logger.INSTANCE;
                if (Logger.isInfoEnabled("CXCP")) {
                    Log.i(Camera2Logger.TRUNCATED_TAG, "CapturePipeline#submitRequestInternal: configAdapter.mapToRequest failed!", e);
                }
                completableDeferredCompletableDeferred$default.completeExceptionally(new ImageCaptureException(2, "Capture request failed with reason " + e.getMessage(), e));
            }
            if (requestM1385mapToRequestnAberiA != null) {
                arrayList2.add(requestM1385mapToRequestnAberiA);
            }
        }
        if (arrayList2.isEmpty()) {
            return arrayList;
        }
        BuildersKt__Builders_commonKt.launch$default(this.threads.getSequentialScope(), null, null, new C0136x5c55b0eb(null, this, arrayList, arrayList2), 3, null);
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object isPhysicalFlashRequired(int r6, kotlin.coroutines.Continuation r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof androidx.camera.camera2.impl.CapturePipelineImpl.C01321
            if (r0 == 0) goto L13
            r0 = r7
            androidx.camera.camera2.impl.CapturePipelineImpl$isPhysicalFlashRequired$1 r0 = (androidx.camera.camera2.impl.CapturePipelineImpl.C01321) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.camera.camera2.impl.CapturePipelineImpl$isPhysicalFlashRequired$1 r0 = new androidx.camera.camera2.impl.CapturePipelineImpl$isPhysicalFlashRequired$1
            r0.<init>(r7)
        L18:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L32
            if (r2 != r4) goto L2a
            kotlin.ResultKt.throwOnFailure(r7)
            goto L51
        L2a:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L32:
            kotlin.ResultKt.throwOnFailure(r7)
            if (r6 == 0) goto L48
            if (r6 == r4) goto L46
            r7 = 2
            if (r6 == r7) goto L6d
            r7 = 3
            if (r6 != r7) goto L40
            goto L6d
        L40:
            java.lang.AssertionError r7 = new java.lang.AssertionError
            r7.<init>(r6)
            throw r7
        L46:
            r3 = r4
            goto L6d
        L48:
            r0.label = r4
            java.lang.Object r7 = r5.getFrameMetadata(r0)
            if (r7 != r1) goto L51
            return r1
        L51:
            androidx.camera.camera2.pipe.FrameMetadata r7 = (androidx.camera.camera2.pipe.FrameMetadata) r7
            if (r7 == 0) goto L6d
            android.hardware.camera2.CaptureResult$Key r6 = android.hardware.camera2.CaptureResult.CONTROL_AE_STATE
            java.lang.String r0 = "CONTROL_AE_STATE"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r6, r0)
            java.lang.Object r6 = r7.get(r6)
            java.lang.Integer r6 = (java.lang.Integer) r6
            if (r6 != 0) goto L65
            goto L6d
        L65:
            int r6 = r6.intValue()
            r7 = 4
            if (r6 != r7) goto L6d
            goto L46
        L6d:
            java.lang.Boolean r6 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r3)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl.isPhysicalFlashRequired(int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object waitForResult(long r12, kotlin.jvm.functions.Function1 r14, kotlin.coroutines.Continuation r15) {
        /*
            r11 = this;
            boolean r0 = r15 instanceof androidx.camera.camera2.impl.CapturePipelineImpl.C01401
            if (r0 == 0) goto L13
            r0 = r15
            androidx.camera.camera2.impl.CapturePipelineImpl$waitForResult$1 r0 = (androidx.camera.camera2.impl.CapturePipelineImpl.C01401) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.camera.camera2.impl.CapturePipelineImpl$waitForResult$1 r0 = new androidx.camera.camera2.impl.CapturePipelineImpl$waitForResult$1
            r0.<init>(r15)
        L18:
            java.lang.Object r15 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L35
            if (r2 != r3) goto L2d
            java.lang.Object r12 = r0.L$0
            androidx.camera.camera2.impl.ResultListener r12 = (androidx.camera.camera2.impl.ResultListener) r12
            kotlin.ResultKt.throwOnFailure(r15)
            goto L74
        L2d:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L35:
            kotlin.ResultKt.throwOnFailure(r15)
            androidx.camera.camera2.impl.ResultListener r15 = new androidx.camera.camera2.impl.ResultListener
            r15.<init>(r12, r14)
            androidx.camera.camera2.impl.ComboRequestListener r14 = r11.requestListener
            androidx.camera.camera2.impl.UseCaseThreads r2 = r11.threads
            java.util.concurrent.Executor r2 = r2.getSequentialExecutor()
            r14.addListener(r15, r2)
            androidx.camera.camera2.impl.UseCaseThreads r14 = r11.threads
            kotlinx.coroutines.CoroutineScope r4 = r14.getSequentialScope()
            androidx.camera.camera2.impl.CapturePipelineImpl$waitForResult$resultListener$1$1 r7 = new androidx.camera.camera2.impl.CapturePipelineImpl$waitForResult$resultListener$1$1
            r14 = 0
            r7.<init>(r15, r11, r14)
            r8 = 3
            r9 = 0
            r5 = 0
            r6 = 0
            kotlinx.coroutines.BuildersKt.launch$default(r4, r5, r6, r7, r8, r9)
            java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.NANOSECONDS
            long r12 = r2.toMillis(r12)
            androidx.camera.camera2.impl.CapturePipelineImpl$waitForResult$3 r2 = new androidx.camera.camera2.impl.CapturePipelineImpl$waitForResult$3
            r2.<init>(r15, r14)
            r0.L$0 = r15
            r0.label = r3
            java.lang.Object r12 = kotlinx.coroutines.TimeoutKt.withTimeoutOrNull(r12, r2, r0)
            if (r12 != r1) goto L71
            return r1
        L71:
            r10 = r15
            r15 = r12
            r12 = r10
        L74:
            r13 = r15
            androidx.camera.camera2.pipe.FrameInfo r13 = (androidx.camera.camera2.pipe.FrameInfo) r13
            if (r13 != 0) goto L7e
            androidx.camera.camera2.impl.ComboRequestListener r13 = r11.requestListener
            r13.removeListener(r12)
        L7e:
            return r15
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl.waitForResult(long, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    static /* synthetic */ Object waitForResult$default(CapturePipelineImpl capturePipelineImpl, long j, Function1 function1, Continuation continuation, int i, Object obj) {
        if ((i & 2) != 0) {
            function1 = new Function1() { // from class: androidx.camera.camera2.impl.CapturePipelineImpl$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    return Boolean.valueOf(CapturePipelineImpl.waitForResult$lambda$0((FrameInfo) obj2));
                }
            };
        }
        return capturePipelineImpl.waitForResult(j, function1, continuation);
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$waitForResult$3 */
    static final class C01413 extends SuspendLambda implements Function2 {
        final /* synthetic */ ResultListener $resultListener;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01413(ResultListener resultListener, Continuation continuation) {
            super(2, continuation);
            this.$resultListener = resultListener;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C01413(this.$resultListener, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01413) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            ResultKt.throwOnFailure(obj);
            Deferred result = this.$resultListener.getResult();
            this.label = 1;
            Object objAwait = result.await(this);
            return objAwait == coroutine_suspended ? coroutine_suspended : objAwait;
        }
    }

    private final Object isTorchAsFlash(int i, Continuation continuation) {
        if (getTemplate() != 3 && i != 1) {
            return this.useTorchAsFlash.shouldUseTorchAsFlash(new C01332(null), continuation);
        }
        return Boxing.boxBoolean(true);
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$isTorchAsFlash$2 */
    static final class C01332 extends SuspendLambda implements Function1 {
        int label;

        C01332(Continuation continuation) {
            super(1, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Continuation continuation) {
            return CapturePipelineImpl.this.new C01332(continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Continuation continuation) {
            return ((C01332) create(continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            ResultKt.throwOnFailure(obj);
            CapturePipelineImpl capturePipelineImpl = CapturePipelineImpl.this;
            this.label = 1;
            Object frameMetadata = capturePipelineImpl.getFrameMetadata(this);
            return frameMetadata == coroutine_suspended ? coroutine_suspended : frameMetadata;
        }
    }
}
