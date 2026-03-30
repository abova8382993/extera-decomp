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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;

/* JADX INFO: loaded from: classes3.dex */
public final class RetryingCameraStateOpenerImpl implements RetryingCameraStateOpener {
    public static final Companion Companion = new Companion(null);
    private final AudioRestrictionController audioRestrictionController;
    private final CameraAvailabilityMonitor cameraAvailabilityMonitor;
    private final CameraErrorListener cameraErrorListener;
    private final CameraPipe.CameraInteropConfig cameraInteropConfig;
    private final CameraStateOpener cameraStateOpener;
    private final DevicePolicyManagerWrapper devicePolicyManager;
    private final Threads threads;
    private final TimeSource timeSource;

    public RetryingCameraStateOpenerImpl(CameraStateOpener cameraStateOpener, CameraErrorListener cameraErrorListener, CameraAvailabilityMonitor cameraAvailabilityMonitor, TimeSource timeSource, DevicePolicyManagerWrapper devicePolicyManager, AudioRestrictionController audioRestrictionController, CameraPipe.CameraInteropConfig cameraInteropConfig, Threads threads) {
        Intrinsics.checkNotNullParameter(cameraStateOpener, "cameraStateOpener");
        Intrinsics.checkNotNullParameter(cameraErrorListener, "cameraErrorListener");
        Intrinsics.checkNotNullParameter(cameraAvailabilityMonitor, "cameraAvailabilityMonitor");
        Intrinsics.checkNotNullParameter(timeSource, "timeSource");
        Intrinsics.checkNotNullParameter(devicePolicyManager, "devicePolicyManager");
        Intrinsics.checkNotNullParameter(audioRestrictionController, "audioRestrictionController");
        Intrinsics.checkNotNullParameter(threads, "threads");
        this.cameraStateOpener = cameraStateOpener;
        this.cameraErrorListener = cameraErrorListener;
        this.cameraAvailabilityMonitor = cameraAvailabilityMonitor;
        this.timeSource = timeSource;
        this.devicePolicyManager = devicePolicyManager;
        this.audioRestrictionController = audioRestrictionController;
        this.cameraInteropConfig = cameraInteropConfig;
        this.threads = threads;
    }

    /* JADX WARN: Code restructure failed: missing block: B:67:0x024f, code lost:
    
        if (r6 == r4) goto L68;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0127 A[Catch: all -> 0x005d, TRY_ENTER, TryCatch #2 {all -> 0x005d, blocks: (B:14:0x004d, B:69:0x0252, B:71:0x025a, B:73:0x0262, B:35:0x010d, B:40:0x0127, B:42:0x012d, B:44:0x0135, B:47:0x013e, B:49:0x0160, B:52:0x016c, B:54:0x0178, B:58:0x0182, B:60:0x0191, B:62:0x0199, B:66:0x0225, B:22:0x0083), top: B:85:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0191 A[Catch: all -> 0x005d, TryCatch #2 {all -> 0x005d, blocks: (B:14:0x004d, B:69:0x0252, B:71:0x025a, B:73:0x0262, B:35:0x010d, B:40:0x0127, B:42:0x012d, B:44:0x0135, B:47:0x013e, B:49:0x0160, B:52:0x016c, B:54:0x0178, B:58:0x0182, B:60:0x0191, B:62:0x0199, B:66:0x0225, B:22:0x0083), top: B:85:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0224  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX WARN: Type inference failed for: r5v0, types: [int] */
    /* JADX WARN: Type inference failed for: r5v1, types: [java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:67:0x024f -> B:16:0x0053). Please report as a decompilation issue!!! */
    @Override // androidx.camera.camera2.pipe.compat.RetryingCameraStateOpener
    /* JADX INFO: renamed from: openCameraWithRetry-aeCOTgg */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object mo1859openCameraWithRetryaeCOTgg(java.lang.String r32, androidx.camera.camera2.pipe.compat.Camera2DeviceCloser r33, kotlin.jvm.functions.Function1 r34, kotlin.coroutines.Continuation r35) {
        /*
            Method dump skipped, instruction units count: 655
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.compat.RetryingCameraStateOpenerImpl.mo1859openCameraWithRetryaeCOTgg(java.lang.String, androidx.camera.camera2.pipe.compat.Camera2DeviceCloser, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // androidx.camera.camera2.pipe.compat.RetryingCameraStateOpener
    /* JADX INFO: renamed from: openAndAwaitCameraWithRetry-0r8Bogc */
    public AwaitOpenCameraResult mo1858openAndAwaitCameraWithRetry0r8Bogc(String cameraId, Camera2DeviceCloser camera2DeviceCloser) {
        Intrinsics.checkNotNullParameter(cameraId, "cameraId");
        Intrinsics.checkNotNullParameter(camera2DeviceCloser, "camera2DeviceCloser");
        if (Log.INSTANCE.getDEBUG_LOGGABLE()) {
            android.util.Log.d("CXCP", this + "#openAndAwaitCameraWithRetry(" + ((Object) CameraId.m1607toStringimpl(cameraId)) + ')');
        }
        return (AwaitOpenCameraResult) BuildersKt.runBlocking(this.threads.getBlockingDispatcher(), new RetryingCameraStateOpenerImpl$openAndAwaitCameraWithRetry$2(this, cameraId, camera2DeviceCloser, null));
    }

    @Override // androidx.camera.camera2.pipe.compat.RetryingCameraStateOpener
    public void cancelOpen() {
        this.cameraStateOpener.cancelOpen$camera_camera2_pipe();
    }

    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: renamed from: shouldRetry-rbpwgO0$camera_camera2_pipe, reason: not valid java name */
        public final boolean m1865shouldRetryrbpwgO0$camera_camera2_pipe(int i, int i2, long j, boolean z, boolean z2, DurationNs durationNs) {
            boolean zM1864shouldActivateActiveResume8PWMtlg$camera_camera2_pipe = m1864shouldActivateActiveResume8PWMtlg$camera_camera2_pipe(z2, i);
            if (zM1864shouldActivateActiveResume8PWMtlg$camera_camera2_pipe && Log.INSTANCE.getDEBUG_LOGGABLE()) {
                android.util.Log.d("CXCP", "shouldRetry: Active resume mode is activated");
            }
            if (DurationNs.m1879compareTozYRVrok(j, m1863getRetryTimeoutNsom7c1s$camera_camera2_pipe(zM1864shouldActivateActiveResume8PWMtlg$camera_camera2_pipe, durationNs)) > 0) {
                return false;
            }
            CameraError.Companion companion = CameraError.Companion;
            if (CameraError.m1553equalsimpl0(i, companion.m1573getERROR_UNDETERMINEDv7Vf74A())) {
                return i2 <= 1;
            }
            if (CameraError.m1553equalsimpl0(i, companion.m1564getERROR_CAMERA_IN_USEv7Vf74A())) {
                return Build.VERSION.SDK_INT >= 29 || i2 <= 1;
            }
            if (CameraError.m1553equalsimpl0(i, companion.m1565getERROR_CAMERA_LIMIT_EXCEEDEDv7Vf74A())) {
                return true;
            }
            if (CameraError.m1553equalsimpl0(i, companion.m1562getERROR_CAMERA_DISABLEDv7Vf74A())) {
                return !z || i2 <= 1;
            }
            if (CameraError.m1553equalsimpl0(i, companion.m1561getERROR_CAMERA_DEVICEv7Vf74A()) || CameraError.m1553equalsimpl0(i, companion.m1568getERROR_CAMERA_SERVICEv7Vf74A()) || CameraError.m1553equalsimpl0(i, companion.m1563getERROR_CAMERA_DISCONNECTEDv7Vf74A()) || CameraError.m1553equalsimpl0(i, companion.m1571getERROR_ILLEGAL_ARGUMENT_EXCEPTIONv7Vf74A())) {
                return true;
            }
            if (CameraError.m1553equalsimpl0(i, companion.m1572getERROR_SECURITY_EXCEPTIONv7Vf74A())) {
                return i2 <= 1;
            }
            if (CameraError.m1553equalsimpl0(i, companion.m1569getERROR_DO_NOT_DISTURB_ENABLEDv7Vf74A())) {
                return false;
            }
            if (CameraError.m1553equalsimpl0(i, companion.m1574getERROR_UNKNOWN_EXCEPTIONv7Vf74A())) {
                return i2 <= 1;
            }
            if (Log.INSTANCE.getERROR_LOGGABLE()) {
                android.util.Log.e("CXCP", "Unexpected CameraError: " + RetryingCameraStateOpenerImpl.Companion);
            }
            return false;
        }

        /* JADX INFO: renamed from: shouldActivateActiveResume-8PWMtlg$camera_camera2_pipe, reason: not valid java name */
        public final boolean m1864shouldActivateActiveResume8PWMtlg$camera_camera2_pipe(boolean z, int i) {
            int i2;
            if (!z || 29 > (i2 = Build.VERSION.SDK_INT) || i2 >= 33) {
                return false;
            }
            CameraError.Companion companion = CameraError.Companion;
            return CameraError.m1553equalsimpl0(i, companion.m1564getERROR_CAMERA_IN_USEv7Vf74A()) || CameraError.m1553equalsimpl0(i, companion.m1565getERROR_CAMERA_LIMIT_EXCEEDEDv7Vf74A()) || CameraError.m1553equalsimpl0(i, companion.m1563getERROR_CAMERA_DISCONNECTEDv7Vf74A());
        }

        /* JADX INFO: renamed from: getRetryTimeoutNs-om-7c1s$camera_camera2_pipe, reason: not valid java name */
        public final long m1863getRetryTimeoutNsom7c1s$camera_camera2_pipe(boolean z, DurationNs durationNs) {
            return !z ? m1861minL1EDjxI(RetryingCameraStateOpenerKt.defaultCameraRetryTimeoutNs, durationNs) : m1861minL1EDjxI(RetryingCameraStateOpenerKt.activeResumeCameraRetryTimeoutNs, durationNs);
        }

        /* JADX INFO: renamed from: getRetryDelayMs-t8DbYm4$camera_camera2_pipe, reason: not valid java name */
        public final long m1862getRetryDelayMst8DbYm4$camera_camera2_pipe(long j, boolean z) {
            if (z && DurationNs.m1879compareTozYRVrok(j, RetryingCameraStateOpenerKt.activeResumeCameraRetryThresholds[0].m1884unboximpl()) >= 0) {
                return DurationNs.m1879compareTozYRVrok(j, RetryingCameraStateOpenerKt.activeResumeCameraRetryThresholds[1].m1884unboximpl()) < 0 ? 2000L : 4000L;
            }
            return 500L;
        }

        /* JADX INFO: renamed from: min-L1EDjxI, reason: not valid java name */
        private final long m1861minL1EDjxI(long j, DurationNs durationNs) {
            return (durationNs == null || DurationNs.m1879compareTozYRVrok(j, durationNs.m1884unboximpl()) == -1) ? j : durationNs.m1884unboximpl();
        }
    }
}
