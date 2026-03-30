package androidx.camera.camera2.pipe.compat;

import androidx.camera.camera2.pipe.CameraPipe;
import androidx.camera.camera2.pipe.core.Threads;
import androidx.camera.camera2.pipe.core.TimeSource;
import androidx.camera.camera2.pipe.internal.CameraErrorListener;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredKt;

/* JADX INFO: loaded from: classes3.dex */
public final class CameraStateOpener {
    private static final Companion Companion = new Companion(null);
    private final Camera2MetadataProvider camera2MetadataProvider;
    private final Camera2Quirks camera2Quirks;
    private final CameraErrorListener cameraErrorListener;
    private final CameraPipe.CameraInteropConfig cameraInteropConfig;
    private CompletableDeferred cameraOpenCancelled;
    private final CameraOpener cameraOpener;
    private final Threads threads;
    private final TimeSource timeSource;

    public CameraStateOpener(CameraOpener cameraOpener, Camera2MetadataProvider camera2MetadataProvider, CameraErrorListener cameraErrorListener, Camera2Quirks camera2Quirks, TimeSource timeSource, CameraPipe.CameraInteropConfig cameraInteropConfig, Threads threads) {
        Intrinsics.checkNotNullParameter(cameraOpener, "cameraOpener");
        Intrinsics.checkNotNullParameter(camera2MetadataProvider, "camera2MetadataProvider");
        Intrinsics.checkNotNullParameter(cameraErrorListener, "cameraErrorListener");
        Intrinsics.checkNotNullParameter(camera2Quirks, "camera2Quirks");
        Intrinsics.checkNotNullParameter(timeSource, "timeSource");
        Intrinsics.checkNotNullParameter(threads, "threads");
        this.cameraOpener = cameraOpener;
        this.camera2MetadataProvider = camera2MetadataProvider;
        this.cameraErrorListener = cameraErrorListener;
        this.camera2Quirks = camera2Quirks;
        this.timeSource = timeSource;
        this.cameraInteropConfig = cameraInteropConfig;
        this.threads = threads;
        this.cameraOpenCancelled = CompletableDeferredKt.CompletableDeferred$default(null, 1, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX INFO: renamed from: tryOpenCamera-7pD7j80$camera_camera2_pipe, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object m1847tryOpenCamera7pD7j80$camera_camera2_pipe(java.lang.String r23, int r24, long r25, androidx.camera.camera2.pipe.compat.Camera2DeviceCloser r27, androidx.camera.camera2.pipe.compat.AudioRestrictionController r28, kotlin.coroutines.Continuation r29) {
        /*
            r22 = this;
            r0 = r22
            r1 = r23
            r2 = r29
            boolean r3 = r2 instanceof androidx.camera.camera2.pipe.compat.CameraStateOpener$tryOpenCamera$1
            if (r3 == 0) goto L19
            r3 = r2
            androidx.camera.camera2.pipe.compat.CameraStateOpener$tryOpenCamera$1 r3 = (androidx.camera.camera2.pipe.compat.CameraStateOpener$tryOpenCamera$1) r3
            int r4 = r3.label
            r5 = -2147483648(0xffffffff80000000, float:-0.0)
            r6 = r4 & r5
            if (r6 == 0) goto L19
            int r4 = r4 - r5
            r3.label = r4
            goto L1e
        L19:
            androidx.camera.camera2.pipe.compat.CameraStateOpener$tryOpenCamera$1 r3 = new androidx.camera.camera2.pipe.compat.CameraStateOpener$tryOpenCamera$1
            r3.<init>(r0, r2)
        L1e:
            java.lang.Object r2 = r3.result
            java.lang.Object r4 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r5 = r3.label
            r6 = 2
            r7 = 1
            if (r5 == 0) goto L54
            if (r5 == r7) goto L3a
            if (r5 != r6) goto L32
            kotlin.ResultKt.throwOnFailure(r2)
            return r2
        L32:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r1.<init>(r2)
            throw r1
        L3a:
            long r7 = r3.J$0
            int r1 = r3.I$0
            java.lang.Object r5 = r3.L$2
            androidx.camera.camera2.pipe.compat.AudioRestrictionController r5 = (androidx.camera.camera2.pipe.compat.AudioRestrictionController) r5
            java.lang.Object r9 = r3.L$1
            androidx.camera.camera2.pipe.compat.Camera2DeviceCloser r9 = (androidx.camera.camera2.pipe.compat.Camera2DeviceCloser) r9
            java.lang.Object r10 = r3.L$0
            java.lang.String r10 = (java.lang.String) r10
            kotlin.ResultKt.throwOnFailure(r2)
            r18 = r5
            r11 = r7
            r15 = r9
            r8 = r10
            r10 = r1
            goto L7a
        L54:
            kotlin.ResultKt.throwOnFailure(r2)
            androidx.camera.camera2.pipe.compat.Camera2MetadataProvider r2 = r0.camera2MetadataProvider
            r3.L$0 = r1
            r5 = r27
            r3.L$1 = r5
            r8 = r28
            r3.L$2 = r8
            r9 = r24
            r3.I$0 = r9
            r10 = r25
            r3.J$0 = r10
            r3.label = r7
            java.lang.Object r2 = r2.mo1842getCameraMetadata0r8Bogc(r1, r3)
            if (r2 != r4) goto L74
            goto Lbf
        L74:
            r15 = r5
            r18 = r8
            r11 = r10
            r8 = r1
            r10 = r9
        L7a:
            r9 = r2
            androidx.camera.camera2.pipe.CameraMetadata r9 = (androidx.camera.camera2.pipe.CameraMetadata) r9
            androidx.camera.camera2.pipe.compat.AndroidCameraState r7 = new androidx.camera.camera2.pipe.compat.AndroidCameraState
            androidx.camera.camera2.pipe.core.TimeSource r13 = r0.timeSource
            androidx.camera.camera2.pipe.internal.CameraErrorListener r14 = r0.cameraErrorListener
            androidx.camera.camera2.pipe.compat.Camera2Quirks r1 = r0.camera2Quirks
            androidx.camera.camera2.pipe.core.Threads r2 = r0.threads
            androidx.camera.camera2.pipe.CameraPipe$CameraInteropConfig r5 = r0.cameraInteropConfig
            r6 = 0
            if (r5 == 0) goto L93
            android.hardware.camera2.CameraDevice$StateCallback r5 = r5.getCameraDeviceStateCallback()
            r19 = r5
            goto L95
        L93:
            r19 = r6
        L95:
            androidx.camera.camera2.pipe.CameraPipe$CameraInteropConfig r5 = r0.cameraInteropConfig
            if (r5 == 0) goto La0
            androidx.camera.camera2.pipe.CameraInterop$CaptureSessionListener r5 = r5.getCameraCaptureSessionListener()
            r20 = r5
            goto La2
        La0:
            r20 = r6
        La2:
            r21 = 0
            r16 = r1
            r17 = r2
            r7.<init>(r8, r9, r10, r11, r13, r14, r15, r16, r17, r18, r19, r20, r21)
            androidx.camera.camera2.pipe.compat.CameraStateOpener$tryOpenCamera$2 r1 = new androidx.camera.camera2.pipe.compat.CameraStateOpener$tryOpenCamera$2
            r1.<init>(r0, r8, r7, r6)
            r3.L$0 = r6
            r3.L$1 = r6
            r3.L$2 = r6
            r2 = 2
            r3.label = r2
            java.lang.Object r1 = kotlinx.coroutines.SupervisorKt.supervisorScope(r1, r3)
            if (r1 != r4) goto Lc0
        Lbf:
            return r4
        Lc0:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.compat.CameraStateOpener.m1847tryOpenCamera7pD7j80$camera_camera2_pipe(java.lang.String, int, long, androidx.camera.camera2.pipe.compat.Camera2DeviceCloser, androidx.camera.camera2.pipe.compat.AudioRestrictionController, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void cancelOpen$camera_camera2_pipe() {
        this.cameraOpenCancelled.complete(Unit.INSTANCE);
    }

    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
