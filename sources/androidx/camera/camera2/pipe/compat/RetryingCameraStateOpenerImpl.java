package androidx.camera.camera2.pipe.compat;

import android.os.Build;
import androidx.camera.camera2.pipe.CameraError;
import androidx.camera.camera2.pipe.CameraId;
import androidx.camera.camera2.pipe.CameraPipe;
import androidx.camera.camera2.pipe.core.DurationNs;
import androidx.camera.camera2.pipe.core.Log;
import androidx.camera.camera2.pipe.core.Threads;
import androidx.camera.camera2.pipe.core.TimeSource;
import androidx.camera.camera2.pipe.internal.CameraErrorListener;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0001\u0018\u0000 %2\u00020\u0001:\u0001%BK\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0004\b\u0012\u0010\u0013J4\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u001d0\u001bH\u0096@¢\u0006\u0004\b\u001e\u0010\u001fJ\u001f\u0010 \u001a\u00020!2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016¢\u0006\u0004\b\"\u0010#J\b\u0010$\u001a\u00020\u001cH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"}, m877d2 = {"Landroidx/camera/camera2/pipe/compat/RetryingCameraStateOpenerImpl;", "Landroidx/camera/camera2/pipe/compat/RetryingCameraStateOpener;", "cameraStateOpener", "Landroidx/camera/camera2/pipe/compat/CameraStateOpener;", "cameraErrorListener", "Landroidx/camera/camera2/pipe/internal/CameraErrorListener;", "cameraAvailabilityMonitor", "Landroidx/camera/camera2/pipe/compat/CameraAvailabilityMonitor;", "timeSource", "Landroidx/camera/camera2/pipe/core/TimeSource;", "devicePolicyManager", "Landroidx/camera/camera2/pipe/compat/DevicePolicyManagerWrapper;", "audioRestrictionController", "Landroidx/camera/camera2/pipe/compat/AudioRestrictionController;", "cameraInteropConfig", "Landroidx/camera/camera2/pipe/CameraPipe$CameraInteropConfig;", "threads", "Landroidx/camera/camera2/pipe/core/Threads;", "<init>", "(Landroidx/camera/camera2/pipe/compat/CameraStateOpener;Landroidx/camera/camera2/pipe/internal/CameraErrorListener;Landroidx/camera/camera2/pipe/compat/CameraAvailabilityMonitor;Landroidx/camera/camera2/pipe/core/TimeSource;Landroidx/camera/camera2/pipe/compat/DevicePolicyManagerWrapper;Landroidx/camera/camera2/pipe/compat/AudioRestrictionController;Landroidx/camera/camera2/pipe/CameraPipe$CameraInteropConfig;Landroidx/camera/camera2/pipe/core/Threads;)V", "openCameraWithRetry", "Landroidx/camera/camera2/pipe/compat/OpenCameraResult;", "cameraId", "Landroidx/camera/camera2/pipe/CameraId;", "camera2DeviceCloser", "Landroidx/camera/camera2/pipe/compat/Camera2DeviceCloser;", "isForegroundObserver", "Lkotlin/Function1;", _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, "openCameraWithRetry-aeCOTgg", "(Ljava/lang/String;Landroidx/camera/camera2/pipe/compat/Camera2DeviceCloser;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "openAndAwaitCameraWithRetry", "Landroidx/camera/camera2/pipe/compat/AwaitOpenCameraResult;", "openAndAwaitCameraWithRetry-0r8Bogc", "(Ljava/lang/String;Landroidx/camera/camera2/pipe/compat/Camera2DeviceCloser;)Landroidx/camera/camera2/pipe/compat/AwaitOpenCameraResult;", "cancelOpen", "Companion", "camera-camera2-pipe"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nRetryingCameraStateOpener.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RetryingCameraStateOpener.kt\nandroidx/camera/camera2/pipe/compat/RetryingCameraStateOpenerImpl\n+ 2 Timestamps.kt\nandroidx/camera/camera2/pipe/core/Timestamps\n+ 3 Timestamps.kt\nandroidx/camera/camera2/pipe/core/TimestampNs\n+ 4 Log.kt\nandroidx/camera/camera2/pipe/core/Log\n*L\n1#1,665:1\n70#2:666\n70#2:667\n70#2:672\n74#2,2:674\n29#3:668\n29#3:673\n71#4,2:669\n82#4:671\n83#4:676\n50#4,2:677\n50#4,2:679\n*S KotlinDebug\n*F\n+ 1 RetryingCameraStateOpener.kt\nandroidx/camera/camera2/pipe/compat/RetryingCameraStateOpenerImpl\n*L\n415#1:666\n430#1:667\n467#1:672\n467#1:674,2\n430#1:668\n467#1:673\n440#1:669,2\n465#1:671\n465#1:676\n484#1:677,2\n495#1:679,2\n*E\n"})
public final class RetryingCameraStateOpenerImpl implements RetryingCameraStateOpener {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final AudioRestrictionController audioRestrictionController;
    private final CameraAvailabilityMonitor cameraAvailabilityMonitor;
    private final CameraErrorListener cameraErrorListener;
    private final CameraPipe.CameraInteropConfig cameraInteropConfig;
    private final CameraStateOpener cameraStateOpener;
    private final DevicePolicyManagerWrapper devicePolicyManager;
    private final Threads threads;
    private final TimeSource timeSource;

    public RetryingCameraStateOpenerImpl(CameraStateOpener cameraStateOpener, CameraErrorListener cameraErrorListener, CameraAvailabilityMonitor cameraAvailabilityMonitor, TimeSource timeSource, DevicePolicyManagerWrapper devicePolicyManagerWrapper, AudioRestrictionController audioRestrictionController, CameraPipe.CameraInteropConfig cameraInteropConfig, Threads threads) {
        this.cameraStateOpener = cameraStateOpener;
        this.cameraErrorListener = cameraErrorListener;
        this.cameraAvailabilityMonitor = cameraAvailabilityMonitor;
        this.timeSource = timeSource;
        this.devicePolicyManager = devicePolicyManagerWrapper;
        this.audioRestrictionController = audioRestrictionController;
        this.cameraInteropConfig = cameraInteropConfig;
        this.threads = threads;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x012f A[Catch: all -> 0x005e, TRY_ENTER, TryCatch #2 {all -> 0x005e, blocks: (B:14:0x004e, B:68:0x0259, B:70:0x0261, B:72:0x0269, B:35:0x0115, B:39:0x012f, B:41:0x0135, B:43:0x013d, B:46:0x0147, B:48:0x0169, B:51:0x0175, B:53:0x0181, B:57:0x018f, B:59:0x019e, B:61:0x01a6, B:65:0x022d, B:22:0x0082), top: B:84:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x019e A[Catch: all -> 0x005e, TryCatch #2 {all -> 0x005e, blocks: (B:14:0x004e, B:68:0x0259, B:70:0x0261, B:72:0x0269, B:35:0x0115, B:39:0x012f, B:41:0x0135, B:43:0x013d, B:46:0x0147, B:48:0x0169, B:51:0x0175, B:53:0x0181, B:57:0x018f, B:59:0x019e, B:61:0x01a6, B:65:0x022d, B:22:0x0082), top: B:84:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x022b  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX WARN: Type inference failed for: r5v1, types: [java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r5v18 */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:66:0x0256 -> B:16:0x0056). Please report as a decompilation issue!!! */
    @Override // androidx.camera.camera2.pipe.compat.RetryingCameraStateOpener
    /* JADX INFO: renamed from: openCameraWithRetry-aeCOTgg */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object mo1745openCameraWithRetryaeCOTgg(java.lang.String r32, androidx.camera.camera2.pipe.compat.Camera2DeviceCloser r33, kotlin.jvm.functions.Function1<? super kotlin.Unit, java.lang.Boolean> r34, kotlin.coroutines.Continuation<? super androidx.camera.camera2.pipe.compat.OpenCameraResult> r35) throws java.lang.Exception {
        /*
            Method dump skipped, instruction units count: 661
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.compat.RetryingCameraStateOpenerImpl.mo1745openCameraWithRetryaeCOTgg(java.lang.String, androidx.camera.camera2.pipe.compat.Camera2DeviceCloser, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // androidx.camera.camera2.pipe.compat.RetryingCameraStateOpener
    /* JADX INFO: renamed from: openAndAwaitCameraWithRetry-0r8Bogc */
    public AwaitOpenCameraResult mo1744openAndAwaitCameraWithRetry0r8Bogc(String cameraId, Camera2DeviceCloser camera2DeviceCloser) {
        if (Log.INSTANCE.getDEBUG_LOGGABLE()) {
            android.util.Log.d("CXCP", this + "#openAndAwaitCameraWithRetry(" + ((Object) CameraId.m1501toStringimpl(cameraId)) + ')');
        }
        return (AwaitOpenCameraResult) BuildersKt.runBlocking(this.threads.getBlockingDispatcher(), new RetryingCameraStateOpenerImpl$openAndAwaitCameraWithRetry$2(this, cameraId, camera2DeviceCloser, null));
    }

    @Override // androidx.camera.camera2.pipe.compat.RetryingCameraStateOpener
    public void cancelOpen() {
        this.cameraStateOpener.cancelOpen$camera_camera2_pipe();
    }

    @Metadata(m876d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\t\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003JE\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00052\b\b\u0002\u0010\r\u001a\u00020\u00052\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000bH\u0000¢\u0006\u0004\b\u000f\u0010\u0010J\u001f\u0010\u0011\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0000¢\u0006\u0004\b\u0012\u0010\u0013J#\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u00052\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000bH\u0000¢\u0006\u0004\b\u0016\u0010\u0017J\u001f\u0010\u0018\u001a\u00020\u00192\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u0005H\u0000¢\u0006\u0004\b\u001a\u0010\u001bJ!\u0010\u001c\u001a\u00020\u000b2\u0006\u0010\u001d\u001a\u00020\u000b2\b\u0010\u001e\u001a\u0004\u0018\u00010\u000bH\u0002¢\u0006\u0004\b\u001f\u0010 ¨\u0006!"}, m877d2 = {"Landroidx/camera/camera2/pipe/compat/RetryingCameraStateOpenerImpl$Companion;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "shouldRetry", _UrlKt.FRAGMENT_ENCODE_SET, "errorCode", "Landroidx/camera/camera2/pipe/CameraError;", "attempts", _UrlKt.FRAGMENT_ENCODE_SET, "elapsedNs", "Landroidx/camera/camera2/pipe/core/DurationNs;", "camerasDisabledByDevicePolicy", "isForeground", "cameraOpenRetryMaxTimeoutNs", "shouldRetry-rbpwgO0$camera_camera2_pipe", "(IIJZZLandroidx/camera/camera2/pipe/core/DurationNs;)Z", "shouldActivateActiveResume", "shouldActivateActiveResume-8PWMtlg$camera_camera2_pipe", "(ZI)Z", "getRetryTimeoutNs", "activeResumeActivated", "getRetryTimeoutNs-om-7c1s$camera_camera2_pipe", "(ZLandroidx/camera/camera2/pipe/core/DurationNs;)J", "getRetryDelayMs", _UrlKt.FRAGMENT_ENCODE_SET, "getRetryDelayMs-t8DbYm4$camera_camera2_pipe", "(JZ)J", "min", "d1", "d2", "min-L1EDjxI", "(JLandroidx/camera/camera2/pipe/core/DurationNs;)J", "camera-camera2-pipe"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    @SourceDebugExtension({"SMAP\nRetryingCameraStateOpener.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RetryingCameraStateOpener.kt\nandroidx/camera/camera2/pipe/compat/RetryingCameraStateOpenerImpl$Companion\n+ 2 Log.kt\nandroidx/camera/camera2/pipe/core/Log\n*L\n1#1,665:1\n50#2,2:666\n82#2,2:668\n*S KotlinDebug\n*F\n+ 1 RetryingCameraStateOpener.kt\nandroidx/camera/camera2/pipe/compat/RetryingCameraStateOpenerImpl$Companion\n*L\n528#1:666,2\n614#1:668,2\n*E\n"})
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: renamed from: shouldRetry-rbpwgO0$camera_camera2_pipe, reason: not valid java name */
        public final boolean m1750shouldRetryrbpwgO0$camera_camera2_pipe(int errorCode, int attempts, long elapsedNs, boolean camerasDisabledByDevicePolicy, boolean isForeground, DurationNs cameraOpenRetryMaxTimeoutNs) {
            boolean zM1749shouldActivateActiveResume8PWMtlg$camera_camera2_pipe = m1749shouldActivateActiveResume8PWMtlg$camera_camera2_pipe(isForeground, errorCode);
            if (zM1749shouldActivateActiveResume8PWMtlg$camera_camera2_pipe && Log.INSTANCE.getDEBUG_LOGGABLE()) {
                android.util.Log.d("CXCP", "shouldRetry: Active resume mode is activated");
            }
            if (DurationNs.m1764compareTozYRVrok(elapsedNs, m1748getRetryTimeoutNsom7c1s$camera_camera2_pipe(zM1749shouldActivateActiveResume8PWMtlg$camera_camera2_pipe, cameraOpenRetryMaxTimeoutNs)) > 0) {
                return false;
            }
            CameraError.Companion companion = CameraError.INSTANCE;
            if (CameraError.m1447equalsimpl0(errorCode, companion.m1467getERROR_UNDETERMINEDv7Vf74A())) {
                return attempts <= 1;
            }
            if (CameraError.m1447equalsimpl0(errorCode, companion.m1458getERROR_CAMERA_IN_USEv7Vf74A())) {
                return Build.VERSION.SDK_INT >= 29 || attempts <= 1;
            }
            if (CameraError.m1447equalsimpl0(errorCode, companion.m1459getERROR_CAMERA_LIMIT_EXCEEDEDv7Vf74A())) {
                return true;
            }
            if (CameraError.m1447equalsimpl0(errorCode, companion.m1456getERROR_CAMERA_DISABLEDv7Vf74A())) {
                return !camerasDisabledByDevicePolicy || attempts <= 1;
            }
            if (CameraError.m1447equalsimpl0(errorCode, companion.m1455getERROR_CAMERA_DEVICEv7Vf74A()) || CameraError.m1447equalsimpl0(errorCode, companion.m1462getERROR_CAMERA_SERVICEv7Vf74A()) || CameraError.m1447equalsimpl0(errorCode, companion.m1457getERROR_CAMERA_DISCONNECTEDv7Vf74A()) || CameraError.m1447equalsimpl0(errorCode, companion.m1465getERROR_ILLEGAL_ARGUMENT_EXCEPTIONv7Vf74A())) {
                return true;
            }
            if (CameraError.m1447equalsimpl0(errorCode, companion.m1466getERROR_SECURITY_EXCEPTIONv7Vf74A())) {
                return attempts <= 1;
            }
            if (CameraError.m1447equalsimpl0(errorCode, companion.m1463getERROR_DO_NOT_DISTURB_ENABLEDv7Vf74A())) {
                return false;
            }
            if (CameraError.m1447equalsimpl0(errorCode, companion.m1468getERROR_UNKNOWN_EXCEPTIONv7Vf74A())) {
                return attempts <= 1;
            }
            if (Log.INSTANCE.getERROR_LOGGABLE()) {
                android.util.Log.e("CXCP", "Unexpected CameraError: " + RetryingCameraStateOpenerImpl.INSTANCE);
            }
            return false;
        }

        /* JADX INFO: renamed from: shouldActivateActiveResume-8PWMtlg$camera_camera2_pipe, reason: not valid java name */
        public final boolean m1749shouldActivateActiveResume8PWMtlg$camera_camera2_pipe(boolean isForeground, int errorCode) {
            int i;
            if (!isForeground || 29 > (i = Build.VERSION.SDK_INT) || i >= 33) {
                return false;
            }
            CameraError.Companion companion = CameraError.INSTANCE;
            return CameraError.m1447equalsimpl0(errorCode, companion.m1458getERROR_CAMERA_IN_USEv7Vf74A()) || CameraError.m1447equalsimpl0(errorCode, companion.m1459getERROR_CAMERA_LIMIT_EXCEEDEDv7Vf74A()) || CameraError.m1447equalsimpl0(errorCode, companion.m1457getERROR_CAMERA_DISCONNECTEDv7Vf74A());
        }

        /* JADX INFO: renamed from: getRetryTimeoutNs-om-7c1s$camera_camera2_pipe, reason: not valid java name */
        public final long m1748getRetryTimeoutNsom7c1s$camera_camera2_pipe(boolean activeResumeActivated, DurationNs cameraOpenRetryMaxTimeoutNs) {
            return !activeResumeActivated ? m1746minL1EDjxI(RetryingCameraStateOpenerKt.defaultCameraRetryTimeoutNs, cameraOpenRetryMaxTimeoutNs) : m1746minL1EDjxI(RetryingCameraStateOpenerKt.activeResumeCameraRetryTimeoutNs, cameraOpenRetryMaxTimeoutNs);
        }

        /* JADX INFO: renamed from: getRetryDelayMs-t8DbYm4$camera_camera2_pipe, reason: not valid java name */
        public final long m1747getRetryDelayMst8DbYm4$camera_camera2_pipe(long elapsedNs, boolean activeResumeActivated) {
            if (activeResumeActivated && DurationNs.m1764compareTozYRVrok(elapsedNs, RetryingCameraStateOpenerKt.activeResumeCameraRetryThresholds[0].getValue()) >= 0) {
                return DurationNs.m1764compareTozYRVrok(elapsedNs, RetryingCameraStateOpenerKt.activeResumeCameraRetryThresholds[1].getValue()) < 0 ? 2000L : 4000L;
            }
            return 500L;
        }

        /* JADX INFO: renamed from: min-L1EDjxI, reason: not valid java name */
        private final long m1746minL1EDjxI(long d1, DurationNs d2) {
            return (d2 == null || DurationNs.m1764compareTozYRVrok(d1, d2.getValue()) == -1) ? d1 : d2.getValue();
        }
    }
}
