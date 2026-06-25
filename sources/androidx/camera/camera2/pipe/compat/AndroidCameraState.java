package androidx.camera.camera2.pipe.compat;

import android.hardware.camera2.CameraDevice;
import android.os.Trace;
import androidx.camera.camera2.pipe.CameraError;
import androidx.camera.camera2.pipe.CameraId;
import androidx.camera.camera2.pipe.CameraInterop;
import androidx.camera.camera2.pipe.CameraMetadata;
import androidx.camera.camera2.pipe.core.Debug;
import androidx.camera.camera2.pipe.core.DurationNs;
import androidx.camera.camera2.pipe.core.Log;
import androidx.camera.camera2.pipe.core.SystemTimeSource;
import androidx.camera.camera2.pipe.core.Threads;
import androidx.camera.camera2.pipe.core.TimeSource;
import androidx.camera.camera2.pipe.core.TimestampNs;
import androidx.camera.camera2.pipe.core.Timestamps;
import androidx.camera.camera2.pipe.internal.CameraErrorListener;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import okhttp3.internal.url._UrlKt;
import okio.Segment$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000´\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u000f\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001:\u0001oBo\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u0012\u0006\u0010\u0013\u001a\u00020\u0012\u0012\u0006\u0010\u0015\u001a\u00020\u0014\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0001\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0017¢\u0006\u0004\b\u0019\u0010\u001aJ\u001f\u0010\"\u001a\u00020\u001f2\u0006\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001e\u001a\u00020\u001dH\u0002¢\u0006\u0004\b \u0010!J!\u0010\"\u001a\u00020\u001f2\b\u0010$\u001a\u0004\u0018\u00010#2\u0006\u0010&\u001a\u00020%H\u0002¢\u0006\u0004\b\"\u0010'J\u0017\u0010*\u001a\u00020)2\u0006\u0010(\u001a\u00020%H\u0002¢\u0006\u0004\b*\u0010+J%\u0010/\u001a\u00020,*\u00020\u00102\u0006\u0010\u0003\u001a\u00020\u00022\b\u0010\u001e\u001a\u0004\u0018\u00010\u001dH\u0002¢\u0006\u0004\b-\u0010.J%\u00101\u001a\u00020,*\u00020\u00102\u0006\u0010\u0003\u001a\u00020\u00022\b\u0010\u001e\u001a\u0004\u0018\u00010\u001dH\u0002¢\u0006\u0004\b0\u0010.J\r\u00102\u001a\u00020\u001f¢\u0006\u0004\b2\u00103J\u0010\u00104\u001a\u00020\u001fH\u0086@¢\u0006\u0004\b4\u00105J\u0017\u0010:\u001a\u00020,2\u0006\u00107\u001a\u000206H\u0000¢\u0006\u0004\b8\u00109J\u0017\u0010;\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020#H\u0016¢\u0006\u0004\b;\u0010<J\u0017\u0010=\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020#H\u0016¢\u0006\u0004\b=\u0010<J\u001f\u0010?\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020#2\u0006\u0010>\u001a\u00020\u0006H\u0016¢\u0006\u0004\b?\u0010@J\u0017\u0010A\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020#H\u0016¢\u0006\u0004\bA\u0010<J\u0017\u0010C\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020#H\u0000¢\u0006\u0004\bB\u0010<J\u0017\u0010\"\u001a\u00020\u001f2\u0006\u0010\u001c\u001a\u00020\u001bH\u0000¢\u0006\u0004\bD\u0010EJ\u000f\u0010G\u001a\u00020FH\u0016¢\u0006\u0004\bG\u0010HR\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n\u0004\b\u0003\u0010I\u001a\u0004\bJ\u0010HR\u0017\u0010\u0005\u001a\u00020\u00048\u0006¢\u0006\f\n\u0004\b\u0005\u0010K\u001a\u0004\bL\u0010MR\u0014\u0010\u0007\u001a\u00020\u00068\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0007\u0010NR\u0014\u0010\t\u001a\u00020\b8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\t\u0010OR\u0014\u0010\u000b\u001a\u00020\n8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u000b\u0010PR\u0014\u0010\r\u001a\u00020\f8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\r\u0010QR\u0014\u0010\u000f\u001a\u00020\u000e8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u000f\u0010RR\u0014\u0010\u0011\u001a\u00020\u00108\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0011\u0010SR\u0014\u0010\u0013\u001a\u00020\u00128\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0013\u0010TR\u0014\u0010\u0015\u001a\u00020\u00148\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0015\u0010UR\u0016\u0010\u0016\u001a\u0004\u0018\u00010\u00018\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0016\u0010VR\u0016\u0010\u0018\u001a\u0004\u0018\u00010\u00178\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0018\u0010WR\u0014\u0010X\u001a\u00020\u00068\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bX\u0010NR\u0014\u0010Z\u001a\u00020Y8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bZ\u0010[R\u0016\u0010\\\u001a\u00020,8\u0002@\u0002X\u0083\u000e¢\u0006\u0006\n\u0004\b\\\u0010]R\u0018\u0010^\u001a\u0004\u0018\u00010%8\u0002@\u0002X\u0083\u000e¢\u0006\u0006\n\u0004\b^\u0010_R\u0016\u0010`\u001a\u00020,8\u0002@\u0002X\u0083\u000e¢\u0006\u0006\n\u0004\b`\u0010]R\u0014\u0010b\u001a\u00020a8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bb\u0010cR\u0014\u0010d\u001a\u00020\b8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bd\u0010OR\u0018\u0010e\u001a\u0004\u0018\u00010\b8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\be\u0010fR\u001a\u0010i\u001a\b\u0012\u0004\u0012\u00020h0g8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bi\u0010jR\u0017\u0010n\u001a\b\u0012\u0004\u0012\u00020h0k8F¢\u0006\u0006\u001a\u0004\bl\u0010m¨\u0006p"}, m877d2 = {"Landroidx/camera/camera2/pipe/compat/AndroidCameraState;", "Landroid/hardware/camera2/CameraDevice$StateCallback;", "Landroidx/camera/camera2/pipe/CameraId;", "cameraId", "Landroidx/camera/camera2/pipe/CameraMetadata;", "metadata", _UrlKt.FRAGMENT_ENCODE_SET, "attemptNumber", "Landroidx/camera/camera2/pipe/core/TimestampNs;", "attemptTimestampNanos", "Landroidx/camera/camera2/pipe/core/TimeSource;", "timeSource", "Landroidx/camera/camera2/pipe/internal/CameraErrorListener;", "cameraErrorListener", "Landroidx/camera/camera2/pipe/compat/Camera2DeviceCloser;", "camera2DeviceCloser", "Landroidx/camera/camera2/pipe/compat/Camera2Quirks;", "camera2Quirks", "Landroidx/camera/camera2/pipe/core/Threads;", "threads", "Landroidx/camera/camera2/pipe/compat/AudioRestrictionController;", "audioRestrictionController", "interopCameraDeviceStateCallback", "Landroidx/camera/camera2/pipe/CameraInterop$CaptureSessionListener;", "interopCaptureSessionListener", "<init>", "(Ljava/lang/String;Landroidx/camera/camera2/pipe/CameraMetadata;IJLandroidx/camera/camera2/pipe/core/TimeSource;Landroidx/camera/camera2/pipe/internal/CameraErrorListener;Landroidx/camera/camera2/pipe/compat/Camera2DeviceCloser;Landroidx/camera/camera2/pipe/compat/Camera2Quirks;Landroidx/camera/camera2/pipe/core/Threads;Landroidx/camera/camera2/pipe/compat/AudioRestrictionController;Landroid/hardware/camera2/CameraDevice$StateCallback;Landroidx/camera/camera2/pipe/CameraInterop$CaptureSessionListener;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", _UrlKt.FRAGMENT_ENCODE_SET, "throwable", "Landroidx/camera/camera2/pipe/CameraError;", "cameraError", _UrlKt.FRAGMENT_ENCODE_SET, "closeWith-8PWMtlg", "(Ljava/lang/Throwable;I)V", "closeWith", "Landroid/hardware/camera2/CameraDevice;", "cameraDevice", "Landroidx/camera/camera2/pipe/compat/AndroidCameraState$ClosingInfo;", "closeRequest", "(Landroid/hardware/camera2/CameraDevice;Landroidx/camera/camera2/pipe/compat/AndroidCameraState$ClosingInfo;)V", "closingInfo", "Landroidx/camera/camera2/pipe/compat/CameraStateClosed;", "computeClosedState", "(Landroidx/camera/camera2/pipe/compat/AndroidCameraState$ClosingInfo;)Landroidx/camera/camera2/pipe/compat/CameraStateClosed;", _UrlKt.FRAGMENT_ENCODE_SET, "shouldReopenCameraWhenClosing-_z0IXec", "(Landroidx/camera/camera2/pipe/compat/Camera2Quirks;Ljava/lang/String;Landroidx/camera/camera2/pipe/CameraError;)Z", "shouldReopenCameraWhenClosing", "shouldCreateEmptyCaptureSessionBeforeClosing-_z0IXec", "shouldCreateEmptyCaptureSessionBeforeClosing", "close", "()V", "awaitClosed", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", _UrlKt.FRAGMENT_ENCODE_SET, "timeoutMillis", "awaitCameraDeviceClosed$camera_camera2_pipe", "(J)Z", "awaitCameraDeviceClosed", "onOpened", "(Landroid/hardware/camera2/CameraDevice;)V", "onDisconnected", "errorCode", "onError", "(Landroid/hardware/camera2/CameraDevice;I)V", "onClosed", "onFinalized$camera_camera2_pipe", "onFinalized", "closeWith$camera_camera2_pipe", "(Ljava/lang/Throwable;)V", _UrlKt.FRAGMENT_ENCODE_SET, "toString", "()Ljava/lang/String;", "Ljava/lang/String;", "getCameraId-Dz_R5H8", "Landroidx/camera/camera2/pipe/CameraMetadata;", "getMetadata", "()Landroidx/camera/camera2/pipe/CameraMetadata;", "I", "J", "Landroidx/camera/camera2/pipe/core/TimeSource;", "Landroidx/camera/camera2/pipe/internal/CameraErrorListener;", "Landroidx/camera/camera2/pipe/compat/Camera2DeviceCloser;", "Landroidx/camera/camera2/pipe/compat/Camera2Quirks;", "Landroidx/camera/camera2/pipe/core/Threads;", "Landroidx/camera/camera2/pipe/compat/AudioRestrictionController;", "Landroid/hardware/camera2/CameraDevice$StateCallback;", "Landroidx/camera/camera2/pipe/CameraInterop$CaptureSessionListener;", "debugId", _UrlKt.FRAGMENT_ENCODE_SET, "lock", "Ljava/lang/Object;", "opening", "Z", "pendingClose", "Landroidx/camera/camera2/pipe/compat/AndroidCameraState$ClosingInfo;", "shouldDelayFinalizing", "Ljava/util/concurrent/CountDownLatch;", "cameraDeviceClosed", "Ljava/util/concurrent/CountDownLatch;", "requestTimestampNanos", "openTimestampNanos", "Landroidx/camera/camera2/pipe/core/TimestampNs;", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Landroidx/camera/camera2/pipe/compat/CameraState;", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "state", "ClosingInfo", "camera-camera2-pipe"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nVirtualCamera.kt\nKotlin\n*S Kotlin\n*F\n+ 1 VirtualCamera.kt\nandroidx/camera/camera2/pipe/compat/AndroidCameraState\n+ 2 Log.kt\nandroidx/camera/camera2/pipe/core/Log\n+ 3 Timestamps.kt\nandroidx/camera/camera2/pipe/core/Timestamps\n+ 4 Debug.kt\nandroidx/camera/camera2/pipe/core/Debug\n+ 5 Timestamps.kt\nandroidx/camera/camera2/pipe/core/TimestampNs\n+ 6 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,585:1\n59#2,2:586\n59#2:594\n60#2:603\n50#2,2:612\n50#2,2:622\n50#2,2:628\n59#2,2:630\n50#2,2:636\n70#3:588\n70#3:589\n74#3,2:597\n74#3,2:599\n74#3,2:601\n70#3:643\n71#4,4:590\n78#4,4:604\n71#4,4:608\n78#4,4:614\n71#4,4:618\n78#4,4:624\n71#4,4:632\n78#4,4:638\n29#5:595\n29#5:596\n29#5:644\n29#5:645\n29#5:646\n29#5:647\n1#6:642\n*S KotlinDebug\n*F\n+ 1 VirtualCamera.kt\nandroidx/camera/camera2/pipe/compat/AndroidCameraState\n*L\n275#1:586,2\n309#1:594\n309#1:603\n392#1:612,2\n409#1:622,2\n422#1:628,2\n427#1:630,2\n436#1:636,2\n280#1:588\n305#1:589\n313#1:597,2\n315#1:599,2\n316#1:601,2\n520#1:643\n308#1:590,4\n386#1:604,4\n391#1:608,4\n403#1:614,4\n408#1:618,4\n417#1:624,4\n435#1:632,4\n440#1:638,4\n310#1:595\n311#1:596\n523#1:644\n524#1:645\n530#1:646\n533#1:647\n*E\n"})
public final class AndroidCameraState extends CameraDevice.StateCallback {
    private final MutableStateFlow<CameraState> _state;
    private final int attemptNumber;
    private final long attemptTimestampNanos;
    private final AudioRestrictionController audioRestrictionController;
    private final Camera2DeviceCloser camera2DeviceCloser;
    private final Camera2Quirks camera2Quirks;
    private final CountDownLatch cameraDeviceClosed;
    private final CameraErrorListener cameraErrorListener;
    private final String cameraId;
    private final int debugId;
    private final CameraDevice.StateCallback interopCameraDeviceStateCallback;
    private final CameraInterop.CaptureSessionListener interopCaptureSessionListener;
    private final Object lock;
    private final CameraMetadata metadata;
    private TimestampNs openTimestampNanos;
    private boolean opening;
    private ClosingInfo pendingClose;
    private final long requestTimestampNanos;
    private boolean shouldDelayFinalizing;
    private final Threads threads;
    private final TimeSource timeSource;

    public /* synthetic */ AndroidCameraState(String str, CameraMetadata cameraMetadata, int i, long j, TimeSource timeSource, CameraErrorListener cameraErrorListener, Camera2DeviceCloser camera2DeviceCloser, Camera2Quirks camera2Quirks, Threads threads, AudioRestrictionController audioRestrictionController, CameraDevice.StateCallback stateCallback, CameraInterop.CaptureSessionListener captureSessionListener, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, cameraMetadata, i, j, timeSource, cameraErrorListener, camera2DeviceCloser, camera2Quirks, threads, audioRestrictionController, stateCallback, captureSessionListener);
    }

    private AndroidCameraState(String str, CameraMetadata cameraMetadata, int i, long j, TimeSource timeSource, CameraErrorListener cameraErrorListener, Camera2DeviceCloser camera2DeviceCloser, Camera2Quirks camera2Quirks, Threads threads, AudioRestrictionController audioRestrictionController, CameraDevice.StateCallback stateCallback, CameraInterop.CaptureSessionListener captureSessionListener) {
        this.cameraId = str;
        this.metadata = cameraMetadata;
        this.attemptNumber = i;
        this.attemptTimestampNanos = j;
        this.timeSource = timeSource;
        this.cameraErrorListener = cameraErrorListener;
        this.camera2DeviceCloser = camera2DeviceCloser;
        this.camera2Quirks = camera2Quirks;
        this.threads = threads;
        this.audioRestrictionController = audioRestrictionController;
        this.interopCameraDeviceStateCallback = stateCallback;
        this.interopCaptureSessionListener = captureSessionListener;
        this.debugId = VirtualCameraKt.getAndroidCameraDebugIds().incrementAndGet();
        this.lock = new Object();
        this.cameraDeviceClosed = new CountDownLatch(1);
        this._state = StateFlowKt.MutableStateFlow(CameraStateUnopened.INSTANCE);
        if (Log.INSTANCE.getINFO_LOGGABLE()) {
            android.util.Log.i("CXCP", "Opening " + ((Object) CameraId.m1501toStringimpl(getCameraId())));
        }
        if (i != 1) {
            Timestamps timestamps = Timestamps.INSTANCE;
            j = timeSource.mo1773nowvQl9yQU();
        }
        this.requestTimestampNanos = j;
    }

    /* JADX INFO: renamed from: getCameraId-Dz_R5H8, reason: from getter */
    public final String getCameraId() {
        return this.cameraId;
    }

    public final StateFlow<CameraState> getState() {
        return this._state;
    }

    public final void close() {
        CameraState value = this._state.getValue();
        CameraDeviceWrapper cameraDevice = value instanceof CameraStateOpen ? ((CameraStateOpen) value).getCameraDevice() : null;
        closeWith(cameraDevice != null ? (CameraDevice) cameraDevice.unwrapAs(Reflection.getOrCreateKotlinClass(CameraDevice.class)) : null, new ClosingInfo(ClosedReason.APP_CLOSED, 0L, null, null, 14, null));
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.pipe.compat.AndroidCameraState$awaitClosed$2 */
    @Metadata(m876d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "it", "Landroidx/camera/camera2/pipe/compat/CameraState;"}, m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.pipe.compat.AndroidCameraState$awaitClosed$2", m896f = "VirtualCamera.kt", m897i = {}, m898l = {}, m899m = "invokeSuspend", m900n = {}, m902s = {}, m903v = 1)
    public static final class C01842 extends SuspendLambda implements Function2<CameraState, Continuation<? super Boolean>, Object> {
        /* synthetic */ Object L$0;
        int label;

        public C01842(Continuation<? super C01842> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C01842 c01842 = new C01842(continuation);
            c01842.L$0 = obj;
            return c01842;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CameraState cameraState, Continuation<? super Boolean> continuation) {
            return ((C01842) create(cameraState, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                return Boxing.boxBoolean(((CameraState) this.L$0) instanceof CameraStateClosed);
            }
            Segment$$ExternalSyntheticBUOutline1.m992m("call to 'resume' before 'invoke' with coroutine");
            return null;
        }
    }

    public final Object awaitClosed(Continuation<? super Unit> continuation) {
        Object objFirst = FlowKt.first(getState(), new C01842(null), continuation);
        return objFirst == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objFirst : Unit.INSTANCE;
    }

    public final boolean awaitCameraDeviceClosed$camera_camera2_pipe(long timeoutMillis) {
        return this.cameraDeviceClosed.await(timeoutMillis, TimeUnit.MILLISECONDS);
    }

    @Override // android.hardware.camera2.CameraDevice.StateCallback
    public void onOpened(CameraDevice cameraDevice) {
        ClosingInfo closingInfo;
        ClosingInfo closingInfo2;
        String str;
        if (!Intrinsics.areEqual(cameraDevice.getId(), this.cameraId)) {
            Segment$$ExternalSyntheticBUOutline1.m992m("Check failed.");
            return;
        }
        Timestamps timestamps = Timestamps.INSTANCE;
        long jMo1773nowvQl9yQU = this.timeSource.mo1773nowvQl9yQU();
        this.openTimestampNanos = TimestampNs.m1775boximpl(jMo1773nowvQl9yQU);
        Debug debug = Debug.INSTANCE;
        Trace.beginSection(((Object) CameraId.m1501toStringimpl(getCameraId())) + "#onOpened");
        if (Log.INSTANCE.getINFO_LOGGABLE()) {
            long jM1765constructorimpl = DurationNs.m1765constructorimpl(jMo1773nowvQl9yQU - this.requestTimestampNanos);
            long jM1765constructorimpl2 = DurationNs.m1765constructorimpl(jMo1773nowvQl9yQU - this.attemptTimestampNanos);
            if (this.attemptNumber == 1) {
                str = "Opened " + ((Object) CameraId.m1501toStringimpl(getCameraId())) + " in " + String.format(null, "%.3f ms", Arrays.copyOf(new Object[]{Double.valueOf(jM1765constructorimpl / 1000000.0d)}, 1));
            } else {
                str = "Opened " + ((Object) CameraId.m1501toStringimpl(getCameraId())) + " in " + String.format(null, "%.3f ms", Arrays.copyOf(new Object[]{Double.valueOf(jM1765constructorimpl / 1000000.0d)}, 1)) + " (" + String.format(null, "%.3f ms", Arrays.copyOf(new Object[]{Double.valueOf(jM1765constructorimpl2 / 1000000.0d)}, 1)) + " total) after " + this.attemptNumber + " attempts.";
            }
            android.util.Log.i("CXCP", str);
        }
        synchronized (this.lock) {
            closingInfo = this.pendingClose;
            if (closingInfo == null) {
                this.opening = true;
            }
        }
        CameraDevice.StateCallback stateCallback = this.interopCameraDeviceStateCallback;
        if (stateCallback != null) {
            stateCallback.onOpened(cameraDevice);
        }
        if (closingInfo != null) {
            Camera2DeviceCloser.closeCamera$default(this.camera2DeviceCloser, null, cameraDevice, this, this.audioRestrictionController, m1685shouldReopenCameraWhenClosing_z0IXec(this.camera2Quirks, this.cameraId, closingInfo.getErrorCode()), m1684shouldCreateEmptyCaptureSessionBeforeClosing_z0IXec(this.camera2Quirks, this.cameraId, closingInfo.getErrorCode()), 1, null);
            return;
        }
        AndroidCameraDevice androidCameraDevice = new AndroidCameraDevice(this.metadata, cameraDevice, this.cameraId, this.cameraErrorListener, this.interopCaptureSessionListener, this.threads, null);
        this.audioRestrictionController.addListener(androidCameraDevice);
        this._state.setValue(new CameraStateOpen(androidCameraDevice));
        synchronized (this.lock) {
            this.opening = false;
            closingInfo2 = this.pendingClose;
        }
        if (closingInfo2 != null) {
            this._state.setValue(new CameraStateClosing(closingInfo2.getErrorCode(), null));
            this.camera2DeviceCloser.closeCamera(androidCameraDevice, cameraDevice, this, this.audioRestrictionController, m1685shouldReopenCameraWhenClosing_z0IXec(this.camera2Quirks, this.cameraId, closingInfo2.getErrorCode()), m1684shouldCreateEmptyCaptureSessionBeforeClosing_z0IXec(this.camera2Quirks, this.cameraId, closingInfo2.getErrorCode()));
            this._state.setValue(computeClosedState(closingInfo2));
        }
        Trace.endSection();
    }

    @Override // android.hardware.camera2.CameraDevice.StateCallback
    public void onDisconnected(CameraDevice cameraDevice) {
        if (!Intrinsics.areEqual(cameraDevice.getId(), this.cameraId)) {
            Segment$$ExternalSyntheticBUOutline1.m992m("Check failed.");
            return;
        }
        Debug debug = Debug.INSTANCE;
        Trace.beginSection(((Object) CameraId.m1501toStringimpl(getCameraId())) + "#onDisconnected");
        if (Log.INSTANCE.getDEBUG_LOGGABLE()) {
            android.util.Log.d("CXCP", ((Object) CameraId.m1501toStringimpl(getCameraId())) + ": onDisconnected");
        }
        this.cameraDeviceClosed.countDown();
        closeWith(cameraDevice, new ClosingInfo(ClosedReason.CAMERA2_DISCONNECTED, 0L, CameraError.m1444boximpl(CameraError.INSTANCE.m1457getERROR_CAMERA_DISCONNECTEDv7Vf74A()), null, 10, null));
        CameraDevice.StateCallback stateCallback = this.interopCameraDeviceStateCallback;
        if (stateCallback != null) {
            stateCallback.onDisconnected(cameraDevice);
        }
        Trace.endSection();
    }

    @Override // android.hardware.camera2.CameraDevice.StateCallback
    public void onError(CameraDevice cameraDevice, int errorCode) {
        if (!Intrinsics.areEqual(cameraDevice.getId(), this.cameraId)) {
            Segment$$ExternalSyntheticBUOutline1.m992m("Check failed.");
            return;
        }
        Debug debug = Debug.INSTANCE;
        Trace.beginSection(((Object) CameraId.m1501toStringimpl(getCameraId())) + "#onError-" + errorCode);
        if (Log.INSTANCE.getDEBUG_LOGGABLE()) {
            android.util.Log.d("CXCP", ((Object) CameraId.m1501toStringimpl(getCameraId())) + ": onError " + errorCode);
        }
        this.cameraDeviceClosed.countDown();
        closeWith(cameraDevice, new ClosingInfo(ClosedReason.CAMERA2_ERROR, 0L, CameraError.m1444boximpl(CameraError.INSTANCE.m1452fromPVuDhNw$camera_camera2_pipe(errorCode)), null, 10, null));
        CameraDevice.StateCallback stateCallback = this.interopCameraDeviceStateCallback;
        if (stateCallback != null) {
            stateCallback.onError(cameraDevice, errorCode);
        }
        Trace.endSection();
    }

    @Override // android.hardware.camera2.CameraDevice.StateCallback
    public void onClosed(CameraDevice cameraDevice) {
        if (!Intrinsics.areEqual(cameraDevice.getId(), this.cameraId)) {
            Segment$$ExternalSyntheticBUOutline1.m992m("Check failed.");
            return;
        }
        Log log = Log.INSTANCE;
        if (log.getDEBUG_LOGGABLE()) {
            android.util.Log.d("CXCP", ((Object) CameraId.m1501toStringimpl(getCameraId())) + ": onClosed");
        }
        this.cameraDeviceClosed.countDown();
        synchronized (this.lock) {
            if (!this.shouldDelayFinalizing) {
                Unit unit = Unit.INSTANCE;
                onFinalized$camera_camera2_pipe(cameraDevice);
                return;
            }
            if (log.getINFO_LOGGABLE()) {
                android.util.Log.i("CXCP", this + "#onClosed: Delaying finalizing.");
            }
        }
    }

    public final void onFinalized$camera_camera2_pipe(CameraDevice cameraDevice) {
        Debug debug = Debug.INSTANCE;
        Trace.beginSection(((Object) CameraId.m1501toStringimpl(getCameraId())) + "#onFinalized");
        if (Log.INSTANCE.getDEBUG_LOGGABLE()) {
            android.util.Log.d("CXCP", this + ": onFinalized");
        }
        closeWith(cameraDevice, new ClosingInfo(ClosedReason.CAMERA2_CLOSED, 0L, null, null, 14, null));
        CameraDevice.StateCallback stateCallback = this.interopCameraDeviceStateCallback;
        if (stateCallback != null) {
            stateCallback.onClosed(cameraDevice);
        }
        Trace.endSection();
    }

    public final void closeWith$camera_camera2_pipe(Throwable throwable) {
        CameraError.Companion companion = CameraError.INSTANCE;
        int iM1454fromPVuDhNw$camera_camera2_pipe = companion.m1454fromPVuDhNw$camera_camera2_pipe(throwable);
        if (CameraError.m1447equalsimpl0(iM1454fromPVuDhNw$camera_camera2_pipe, companion.m1467getERROR_UNDETERMINEDv7Vf74A())) {
            return;
        }
        m1683closeWith8PWMtlg(throwable, iM1454fromPVuDhNw$camera_camera2_pipe);
    }

    /* JADX INFO: renamed from: closeWith-8PWMtlg */
    private final void m1683closeWith8PWMtlg(Throwable throwable, int cameraError) {
        closeWith(null, new ClosingInfo(ClosedReason.CAMERA2_EXCEPTION, 0L, CameraError.m1444boximpl(cameraError), throwable, 2, null));
    }

    /* JADX WARN: Removed duplicated region for block: B:63:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void closeWith(android.hardware.camera2.CameraDevice r11, androidx.camera.camera2.pipe.compat.AndroidCameraState.ClosingInfo r12) {
        /*
            r10 = this;
            kotlinx.coroutines.flow.MutableStateFlow<androidx.camera.camera2.pipe.compat.CameraState> r0 = r10._state
            java.lang.Object r0 = r0.getValue()
            androidx.camera.camera2.pipe.compat.CameraState r0 = (androidx.camera.camera2.pipe.compat.CameraState) r0
            boolean r1 = r0 instanceof androidx.camera.camera2.pipe.compat.CameraStateOpen
            r2 = 0
            if (r1 == 0) goto L15
            androidx.camera.camera2.pipe.compat.CameraStateOpen r0 = (androidx.camera.camera2.pipe.compat.CameraStateOpen) r0
            androidx.camera.camera2.pipe.compat.CameraDeviceWrapper r0 = r0.getCameraDevice()
            r4 = r0
            goto L16
        L15:
            r4 = r2
        L16:
            java.lang.Object r1 = r10.lock
            monitor-enter(r1)
            androidx.camera.camera2.pipe.compat.AndroidCameraState$ClosingInfo r0 = r10.pendingClose     // Catch: java.lang.Throwable -> L24
            if (r0 != 0) goto L28
            r10.pendingClose = r12     // Catch: java.lang.Throwable -> L24
            boolean r0 = r10.opening     // Catch: java.lang.Throwable -> L24
            if (r0 != 0) goto L28
            goto L29
        L24:
            r0 = move-exception
            r10 = r0
            goto L9d
        L28:
            r12 = r2
        L29:
            monitor-exit(r1)
            if (r12 == 0) goto L9c
            androidx.camera.camera2.pipe.CameraError r0 = r12.getErrorCode()
            if (r0 == 0) goto L4a
            androidx.camera.camera2.pipe.compat.ClosedReason r0 = r12.getReason()
            androidx.camera.camera2.pipe.compat.ClosedReason r1 = androidx.camera.camera2.pipe.compat.ClosedReason.CAMERA2_EXCEPTION
            if (r0 == r1) goto L4a
            androidx.camera.camera2.pipe.internal.CameraErrorListener r0 = r10.cameraErrorListener
            java.lang.String r1 = r10.cameraId
            androidx.camera.camera2.pipe.CameraError r3 = r12.getErrorCode()
            int r3 = r3.getValue()
            r5 = 0
            r0.mo1716onCameraError3M5Xam4(r1, r3, r5)
        L4a:
            kotlinx.coroutines.flow.MutableStateFlow<androidx.camera.camera2.pipe.compat.CameraState> r0 = r10._state
            androidx.camera.camera2.pipe.compat.CameraStateClosing r1 = new androidx.camera.camera2.pipe.compat.CameraStateClosing
            androidx.camera.camera2.pipe.CameraError r3 = r12.getErrorCode()
            r1.<init>(r3, r2)
            r0.setValue(r1)
            androidx.camera.camera2.pipe.compat.ClosedReason r0 = r12.getReason()
            androidx.camera.camera2.pipe.compat.ClosedReason r1 = androidx.camera.camera2.pipe.compat.ClosedReason.CAMERA2_CLOSED
            if (r0 == r1) goto L92
            androidx.camera.camera2.pipe.compat.Camera2Quirks r0 = r10.camera2Quirks
            java.lang.String r1 = r10.cameraId
            androidx.camera.camera2.pipe.CameraError r2 = r12.getErrorCode()
            boolean r8 = r10.m1685shouldReopenCameraWhenClosing_z0IXec(r0, r1, r2)
            if (r8 == 0) goto L7c
            java.lang.Object r1 = r10.lock
            monitor-enter(r1)
            r0 = 1
            r10.shouldDelayFinalizing = r0     // Catch: java.lang.Throwable -> L78
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L78
            monitor-exit(r1)
            goto L7c
        L78:
            r0 = move-exception
            r10 = r0
            monitor-exit(r1)
            throw r10
        L7c:
            androidx.camera.camera2.pipe.compat.Camera2DeviceCloser r3 = r10.camera2DeviceCloser
            androidx.camera.camera2.pipe.compat.AudioRestrictionController r7 = r10.audioRestrictionController
            androidx.camera.camera2.pipe.compat.Camera2Quirks r0 = r10.camera2Quirks
            java.lang.String r1 = r10.cameraId
            androidx.camera.camera2.pipe.CameraError r2 = r12.getErrorCode()
            boolean r9 = r10.m1684shouldCreateEmptyCaptureSessionBeforeClosing_z0IXec(r0, r1, r2)
            r6 = r10
            r5 = r11
            r3.closeCamera(r4, r5, r6, r7, r8, r9)
            goto L93
        L92:
            r6 = r10
        L93:
            kotlinx.coroutines.flow.MutableStateFlow<androidx.camera.camera2.pipe.compat.CameraState> r10 = r6._state
            androidx.camera.camera2.pipe.compat.CameraStateClosed r11 = r6.computeClosedState(r12)
            r10.setValue(r11)
        L9c:
            return
        L9d:
            monitor-exit(r1)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.compat.AndroidCameraState.closeWith(android.hardware.camera2.CameraDevice, androidx.camera.camera2.pipe.compat.AndroidCameraState$ClosingInfo):void");
    }

    private final CameraStateClosed computeClosedState(ClosingInfo closingInfo) {
        Timestamps timestamps = Timestamps.INSTANCE;
        long jMo1773nowvQl9yQU = this.timeSource.mo1773nowvQl9yQU();
        TimestampNs timestampNs = this.openTimestampNanos;
        long closingTimestamp = closingInfo.getClosingTimestamp();
        DurationNs durationNsM1763boximpl = timestampNs != null ? DurationNs.m1763boximpl(DurationNs.m1765constructorimpl(timestampNs.getValue() - this.attemptTimestampNanos)) : null;
        DurationNs durationNsM1763boximpl2 = timestampNs != null ? DurationNs.m1763boximpl(DurationNs.m1765constructorimpl(timestampNs.getValue() - this.requestTimestampNanos)) : null;
        DurationNs durationNsM1763boximpl3 = timestampNs != null ? DurationNs.m1763boximpl(DurationNs.m1765constructorimpl(closingTimestamp - timestampNs.getValue())) : null;
        long jM1765constructorimpl = DurationNs.m1765constructorimpl(jMo1773nowvQl9yQU - closingTimestamp);
        return new CameraStateClosed(this.cameraId, closingInfo.getReason(), Integer.valueOf(this.attemptNumber - 1), durationNsM1763boximpl, closingInfo.getException(), durationNsM1763boximpl2, durationNsM1763boximpl3, DurationNs.m1763boximpl(jM1765constructorimpl), closingInfo.getErrorCode(), null);
    }

    @Metadata(m876d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u000f\b\u0082\b\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0004\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\b¢\u0006\u0004\b\n\u0010\u000bJ\u0010\u0010\r\u001a\u00020\fHÖ\u0001¢\u0006\u0004\b\r\u0010\u000eJ\u0010\u0010\u0010\u001a\u00020\u000fHÖ\u0001¢\u0006\u0004\b\u0010\u0010\u0011J\u001a\u0010\u0014\u001a\u00020\u00132\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n\u0004\b\u0003\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R\u0017\u0010\u0005\u001a\u00020\u00048\u0006¢\u0006\f\n\u0004\b\u0005\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\u0019\u0010\u0007\u001a\u0004\u0018\u00010\u00068\u0006¢\u0006\f\n\u0004\b\u0007\u0010\u001c\u001a\u0004\b\u001d\u0010\u001eR\u0019\u0010\t\u001a\u0004\u0018\u00010\b8\u0006¢\u0006\f\n\u0004\b\t\u0010\u001f\u001a\u0004\b \u0010!¨\u0006\""}, m877d2 = {"Landroidx/camera/camera2/pipe/compat/AndroidCameraState$ClosingInfo;", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/camera2/pipe/compat/ClosedReason;", "reason", "Landroidx/camera/camera2/pipe/core/TimestampNs;", "closingTimestamp", "Landroidx/camera/camera2/pipe/CameraError;", "errorCode", _UrlKt.FRAGMENT_ENCODE_SET, "exception", "<init>", "(Landroidx/camera/camera2/pipe/compat/ClosedReason;JLandroidx/camera/camera2/pipe/CameraError;Ljava/lang/Throwable;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", _UrlKt.FRAGMENT_ENCODE_SET, "toString", "()Ljava/lang/String;", _UrlKt.FRAGMENT_ENCODE_SET, "hashCode", "()I", "other", _UrlKt.FRAGMENT_ENCODE_SET, "equals", "(Ljava/lang/Object;)Z", "Landroidx/camera/camera2/pipe/compat/ClosedReason;", "getReason", "()Landroidx/camera/camera2/pipe/compat/ClosedReason;", "J", "getClosingTimestamp-vQl9yQU", "()J", "Landroidx/camera/camera2/pipe/CameraError;", "getErrorCode-mVEW8x0", "()Landroidx/camera/camera2/pipe/CameraError;", "Ljava/lang/Throwable;", "getException", "()Ljava/lang/Throwable;", "camera-camera2-pipe"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    @SourceDebugExtension({"SMAP\nVirtualCamera.kt\nKotlin\n*S Kotlin\n*F\n+ 1 VirtualCamera.kt\nandroidx/camera/camera2/pipe/compat/AndroidCameraState$ClosingInfo\n+ 2 Timestamps.kt\nandroidx/camera/camera2/pipe/core/Timestamps\n*L\n1#1,585:1\n70#2:586\n*S KotlinDebug\n*F\n+ 1 VirtualCamera.kt\nandroidx/camera/camera2/pipe/compat/AndroidCameraState$ClosingInfo\n*L\n550#1:586\n*E\n"})
    public static final /* data */ class ClosingInfo {
        private final long closingTimestamp;
        private final CameraError errorCode;
        private final Throwable exception;
        private final ClosedReason reason;

        public /* synthetic */ ClosingInfo(ClosedReason closedReason, long j, CameraError cameraError, Throwable th, DefaultConstructorMarker defaultConstructorMarker) {
            this(closedReason, j, cameraError, th);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ClosingInfo)) {
                return false;
            }
            ClosingInfo closingInfo = (ClosingInfo) other;
            return this.reason == closingInfo.reason && TimestampNs.m1778equalsimpl0(this.closingTimestamp, closingInfo.closingTimestamp) && Intrinsics.areEqual(this.errorCode, closingInfo.errorCode) && Intrinsics.areEqual(this.exception, closingInfo.exception);
        }

        public int hashCode() {
            int iHashCode = ((this.reason.hashCode() * 31) + TimestampNs.m1779hashCodeimpl(this.closingTimestamp)) * 31;
            CameraError cameraError = this.errorCode;
            int iM1448hashCodeimpl = (iHashCode + (cameraError == null ? 0 : CameraError.m1448hashCodeimpl(cameraError.getValue()))) * 31;
            Throwable th = this.exception;
            return iM1448hashCodeimpl + (th != null ? th.hashCode() : 0);
        }

        public String toString() {
            return "ClosingInfo(reason=" + this.reason + ", closingTimestamp=" + ((Object) TimestampNs.m1780toStringimpl(this.closingTimestamp)) + ", errorCode=" + this.errorCode + ", exception=" + this.exception + ')';
        }

        private ClosingInfo(ClosedReason closedReason, long j, CameraError cameraError, Throwable th) {
            this.reason = closedReason;
            this.closingTimestamp = j;
            this.errorCode = cameraError;
            this.exception = th;
        }

        public final ClosedReason getReason() {
            return this.reason;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public /* synthetic */ ClosingInfo(ClosedReason closedReason, long j, CameraError cameraError, Throwable th, int i, DefaultConstructorMarker defaultConstructorMarker) {
            if ((i & 2) != 0) {
                Timestamps timestamps = Timestamps.INSTANCE;
                j = new SystemTimeSource().mo1773nowvQl9yQU();
            }
            this(closedReason, j, (i & 4) != 0 ? null : cameraError, (i & 8) != 0 ? null : th, null);
        }

        /* JADX INFO: renamed from: getClosingTimestamp-vQl9yQU, reason: from getter */
        public final long getClosingTimestamp() {
            return this.closingTimestamp;
        }

        /* JADX INFO: renamed from: getErrorCode-mVEW8x0, reason: from getter */
        public final CameraError getErrorCode() {
            return this.errorCode;
        }

        public final Throwable getException() {
            return this.exception;
        }
    }

    /* JADX INFO: renamed from: shouldReopenCameraWhenClosing-_z0IXec */
    private final boolean m1685shouldReopenCameraWhenClosing_z0IXec(Camera2Quirks camera2Quirks, String str, CameraError cameraError) {
        return m1684shouldCreateEmptyCaptureSessionBeforeClosing_z0IXec(camera2Quirks, str, cameraError) && camera2Quirks.m66x552c1673(str);
    }

    /* JADX INFO: renamed from: shouldCreateEmptyCaptureSessionBeforeClosing-_z0IXec */
    private final boolean m1684shouldCreateEmptyCaptureSessionBeforeClosing_z0IXec(Camera2Quirks camera2Quirks, String str, CameraError cameraError) {
        return camera2Quirks.m67xfcf3eba9(str) && cameraError == null;
    }

    public String toString() {
        return "CameraState-" + this.debugId;
    }
}
