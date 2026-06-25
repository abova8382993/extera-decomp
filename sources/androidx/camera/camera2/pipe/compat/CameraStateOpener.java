package androidx.camera.camera2.pipe.compat;

import androidx.camera.camera2.pipe.CameraPipe;
import androidx.camera.camera2.pipe.core.Threads;
import androidx.camera.camera2.pipe.core.TimeSource;
import androidx.camera.camera2.pipe.internal.CameraErrorListener;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredKt;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0000\u0018\u0000 %2\u00020\u0001:\u0001%BC\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0004\b\u0010\u0010\u0011J8\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0080@¢\u0006\u0004\b!\u0010\"J\r\u0010#\u001a\u00020\u0014H\u0000¢\u0006\u0002\b$R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006&"}, m877d2 = {"Landroidx/camera/camera2/pipe/compat/CameraStateOpener;", _UrlKt.FRAGMENT_ENCODE_SET, "cameraOpener", "Landroidx/camera/camera2/pipe/compat/CameraOpener;", "camera2MetadataProvider", "Landroidx/camera/camera2/pipe/compat/Camera2MetadataProvider;", "cameraErrorListener", "Landroidx/camera/camera2/pipe/internal/CameraErrorListener;", "camera2Quirks", "Landroidx/camera/camera2/pipe/compat/Camera2Quirks;", "timeSource", "Landroidx/camera/camera2/pipe/core/TimeSource;", "cameraInteropConfig", "Landroidx/camera/camera2/pipe/CameraPipe$CameraInteropConfig;", "threads", "Landroidx/camera/camera2/pipe/core/Threads;", "<init>", "(Landroidx/camera/camera2/pipe/compat/CameraOpener;Landroidx/camera/camera2/pipe/compat/Camera2MetadataProvider;Landroidx/camera/camera2/pipe/internal/CameraErrorListener;Landroidx/camera/camera2/pipe/compat/Camera2Quirks;Landroidx/camera/camera2/pipe/core/TimeSource;Landroidx/camera/camera2/pipe/CameraPipe$CameraInteropConfig;Landroidx/camera/camera2/pipe/core/Threads;)V", "cameraOpenCancelled", "Lkotlinx/coroutines/CompletableDeferred;", _UrlKt.FRAGMENT_ENCODE_SET, "tryOpenCamera", "Landroidx/camera/camera2/pipe/compat/OpenCameraResult;", "cameraId", "Landroidx/camera/camera2/pipe/CameraId;", "attempts", _UrlKt.FRAGMENT_ENCODE_SET, "requestTimestamp", "Landroidx/camera/camera2/pipe/core/TimestampNs;", "camera2DeviceCloser", "Landroidx/camera/camera2/pipe/compat/Camera2DeviceCloser;", "audioRestrictionController", "Landroidx/camera/camera2/pipe/compat/AudioRestrictionController;", "tryOpenCamera-7pD7j80$camera_camera2_pipe", "(Ljava/lang/String;IJLandroidx/camera/camera2/pipe/compat/Camera2DeviceCloser;Landroidx/camera/camera2/pipe/compat/AudioRestrictionController;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "cancelOpen", "cancelOpen$camera_camera2_pipe", "Companion", "camera-camera2-pipe"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
public final class CameraStateOpener {
    private static final Companion Companion = new Companion(null);
    private final Camera2MetadataProvider camera2MetadataProvider;
    private final Camera2Quirks camera2Quirks;
    private final CameraErrorListener cameraErrorListener;
    private final CameraPipe.CameraInteropConfig cameraInteropConfig;
    private CompletableDeferred<Unit> cameraOpenCancelled = CompletableDeferredKt.CompletableDeferred$default(null, 1, null);
    private final CameraOpener cameraOpener;
    private final Threads threads;
    private final TimeSource timeSource;

    public CameraStateOpener(CameraOpener cameraOpener, Camera2MetadataProvider camera2MetadataProvider, CameraErrorListener cameraErrorListener, Camera2Quirks camera2Quirks, TimeSource timeSource, CameraPipe.CameraInteropConfig cameraInteropConfig, Threads threads) {
        this.cameraOpener = cameraOpener;
        this.camera2MetadataProvider = camera2MetadataProvider;
        this.cameraErrorListener = cameraErrorListener;
        this.camera2Quirks = camera2Quirks;
        this.timeSource = timeSource;
        this.cameraInteropConfig = cameraInteropConfig;
        this.threads = threads;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX INFO: renamed from: tryOpenCamera-7pD7j80$camera_camera2_pipe, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object m1731tryOpenCamera7pD7j80$camera_camera2_pipe(java.lang.String r25, int r26, long r27, androidx.camera.camera2.pipe.compat.Camera2DeviceCloser r29, androidx.camera.camera2.pipe.compat.AudioRestrictionController r30, kotlin.coroutines.Continuation<? super androidx.camera.camera2.pipe.compat.OpenCameraResult> r31) {
        /*
            r24 = this;
            r0 = r24
            r1 = r25
            r2 = r31
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
            r8 = 0
            if (r5 == 0) goto L54
            if (r5 == r7) goto L39
            if (r5 != r6) goto L33
            kotlin.ResultKt.throwOnFailure(r2)
            return r2
        L33:
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r0)
            return r8
        L39:
            long r9 = r3.J$0
            int r1 = r3.I$0
            java.lang.Object r5 = r3.L$2
            androidx.camera.camera2.pipe.compat.AudioRestrictionController r5 = (androidx.camera.camera2.pipe.compat.AudioRestrictionController) r5
            java.lang.Object r7 = r3.L$1
            androidx.camera.camera2.pipe.compat.Camera2DeviceCloser r7 = (androidx.camera.camera2.pipe.compat.Camera2DeviceCloser) r7
            java.lang.Object r11 = r3.L$0
            java.lang.String r11 = (java.lang.String) r11
            kotlin.ResultKt.throwOnFailure(r2)
            r12 = r1
            r20 = r5
            r17 = r7
            r13 = r9
            r10 = r11
            goto L7b
        L54:
            kotlin.ResultKt.throwOnFailure(r2)
            androidx.camera.camera2.pipe.compat.Camera2MetadataProvider r2 = r0.camera2MetadataProvider
            r3.L$0 = r1
            r5 = r29
            r3.L$1 = r5
            r9 = r30
            r3.L$2 = r9
            r10 = r26
            r3.I$0 = r10
            r11 = r27
            r3.J$0 = r11
            r3.label = r7
            java.lang.Object r2 = r2.mo1726getCameraMetadata0r8Bogc(r1, r3)
            if (r2 != r4) goto L74
            goto Lc0
        L74:
            r17 = r5
            r20 = r9
            r13 = r11
            r12 = r10
            r10 = r1
        L7b:
            r11 = r2
            androidx.camera.camera2.pipe.CameraMetadata r11 = (androidx.camera.camera2.pipe.CameraMetadata) r11
            androidx.camera.camera2.pipe.compat.AndroidCameraState r9 = new androidx.camera.camera2.pipe.compat.AndroidCameraState
            androidx.camera.camera2.pipe.core.TimeSource r15 = r0.timeSource
            androidx.camera.camera2.pipe.internal.CameraErrorListener r1 = r0.cameraErrorListener
            androidx.camera.camera2.pipe.compat.Camera2Quirks r2 = r0.camera2Quirks
            androidx.camera.camera2.pipe.core.Threads r5 = r0.threads
            androidx.camera.camera2.pipe.CameraPipe$CameraInteropConfig r7 = r0.cameraInteropConfig
            if (r7 == 0) goto L93
            android.hardware.camera2.CameraDevice$StateCallback r7 = r7.getCameraDeviceStateCallback()
            r21 = r7
            goto L95
        L93:
            r21 = r8
        L95:
            androidx.camera.camera2.pipe.CameraPipe$CameraInteropConfig r7 = r0.cameraInteropConfig
            if (r7 == 0) goto La0
            androidx.camera.camera2.pipe.CameraInterop$CaptureSessionListener r7 = r7.getCameraCaptureSessionListener()
            r22 = r7
            goto La2
        La0:
            r22 = r8
        La2:
            r23 = 0
            r16 = r1
            r18 = r2
            r19 = r5
            r9.<init>(r10, r11, r12, r13, r15, r16, r17, r18, r19, r20, r21, r22, r23)
            androidx.camera.camera2.pipe.compat.CameraStateOpener$tryOpenCamera$2 r1 = new androidx.camera.camera2.pipe.compat.CameraStateOpener$tryOpenCamera$2
            r1.<init>(r0, r10, r9, r8)
            r3.L$0 = r8
            r3.L$1 = r8
            r3.L$2 = r8
            r3.label = r6
            java.lang.Object r0 = kotlinx.coroutines.SupervisorKt.supervisorScope(r1, r3)
            if (r0 != r4) goto Lc1
        Lc0:
            return r4
        Lc1:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.compat.CameraStateOpener.m1731tryOpenCamera7pD7j80$camera_camera2_pipe(java.lang.String, int, long, androidx.camera.camera2.pipe.compat.Camera2DeviceCloser, androidx.camera.camera2.pipe.compat.AudioRestrictionController, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void cancelOpen$camera_camera2_pipe() {
        this.cameraOpenCancelled.complete(Unit.INSTANCE);
    }

    @Metadata(m876d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\b\u0082\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0007"}, m877d2 = {"Landroidx/camera/camera2/pipe/compat/CameraStateOpener$Companion;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "CAMERA_OPEN_TIMEOUT_MS", _UrlKt.FRAGMENT_ENCODE_SET, "CAMERA_OPEN_CANCEL_TIMEOUT_MS", "camera-camera2-pipe"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
