package androidx.camera.camera2.compat.workaround;

import android.hardware.camera2.CaptureResult;
import androidx.camera.camera2.compat.quirk.CameraQuirks;
import androidx.camera.camera2.compat.quirk.UltraWideFlashCaptureUnderexposureQuirk;
import androidx.camera.camera2.internal.IntrinsicZoomCalculator;
import androidx.camera.camera2.pipe.CameraDevices;
import androidx.camera.camera2.pipe.CameraId;
import androidx.camera.camera2.pipe.CameraMetadata;
import androidx.camera.camera2.pipe.FrameMetadata;
import androidx.camera.camera2.pipe.core.Log;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: loaded from: classes3.dex */
public final class UseTorchAsFlashImpl implements UseTorchAsFlash {
    private final CameraDevices cameraDevices;
    private final CameraQuirks cameraQuirks;
    private final Lazy hasUwCameraUnderexposedFlashCaptureQuirk$delegate;
    private final IntrinsicZoomCalculator intrinsicZoomCalculator;

    /* JADX INFO: renamed from: androidx.camera.camera2.compat.workaround.UseTorchAsFlashImpl$shouldUseTorchAsFlash$1 */
    static final class C01231 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C01231(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return UseTorchAsFlashImpl.this.shouldUseTorchAsFlash(null, this);
        }
    }

    public UseTorchAsFlashImpl(CameraQuirks cameraQuirks, CameraDevices cameraDevices, IntrinsicZoomCalculator intrinsicZoomCalculator) {
        Intrinsics.checkNotNullParameter(cameraQuirks, "cameraQuirks");
        Intrinsics.checkNotNullParameter(cameraDevices, "cameraDevices");
        Intrinsics.checkNotNullParameter(intrinsicZoomCalculator, "intrinsicZoomCalculator");
        this.cameraQuirks = cameraQuirks;
        this.cameraDevices = cameraDevices;
        this.intrinsicZoomCalculator = intrinsicZoomCalculator;
        this.hasUwCameraUnderexposedFlashCaptureQuirk$delegate = LazyKt.lazy(new Function0() { // from class: androidx.camera.camera2.compat.workaround.UseTorchAsFlashImpl$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(UseTorchAsFlashImpl.hasUwCameraUnderexposedFlashCaptureQuirk_delegate$lambda$0(this.f$0));
            }
        });
    }

    public final boolean getHasUwCameraUnderexposedFlashCaptureQuirk() {
        return ((Boolean) this.hasUwCameraUnderexposedFlashCaptureQuirk$delegate.getValue()).booleanValue();
    }

    public static final boolean hasUwCameraUnderexposedFlashCaptureQuirk_delegate$lambda$0(UseTorchAsFlashImpl useTorchAsFlashImpl) {
        return useTorchAsFlashImpl.cameraQuirks.getQuirks().contains(UltraWideFlashCaptureUnderexposureQuirk.class);
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x0013  */
    @Override // androidx.camera.camera2.compat.workaround.UseTorchAsFlash
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object shouldUseTorchAsFlash(kotlin.jvm.functions.Function1 r7, kotlin.coroutines.Continuation r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof androidx.camera.camera2.compat.workaround.UseTorchAsFlashImpl.C01231
            if (r0 == 0) goto L13
            r0 = r8
            androidx.camera.camera2.compat.workaround.UseTorchAsFlashImpl$shouldUseTorchAsFlash$1 r0 = (androidx.camera.camera2.compat.workaround.UseTorchAsFlashImpl.C01231) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.camera.camera2.compat.workaround.UseTorchAsFlashImpl$shouldUseTorchAsFlash$1 r0 = new androidx.camera.camera2.compat.workaround.UseTorchAsFlashImpl$shouldUseTorchAsFlash$1
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            java.lang.String r3 = "CXCP"
            r4 = 1
            if (r2 == 0) goto L33
            if (r2 != r4) goto L2b
            kotlin.ResultKt.throwOnFailure(r8)
            goto L82
        L2b:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L33:
            kotlin.ResultKt.throwOnFailure(r8)
            androidx.camera.camera2.pipe.core.Log r8 = androidx.camera.camera2.pipe.core.Log.INSTANCE
            boolean r2 = r8.getDEBUG_LOGGABLE()
            if (r2 == 0) goto L57
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r5 = "shouldUseTorchAsFlash: hasUwCameraUnderexposedFlashCaptureQuirk = "
            r2.append(r5)
            boolean r5 = access$getHasUwCameraUnderexposedFlashCaptureQuirk(r6)
            r2.append(r5)
            java.lang.String r2 = r2.toString()
            android.util.Log.d(r3, r2)
        L57:
            boolean r2 = r6.getHasUwCameraUnderexposedFlashCaptureQuirk()
            if (r2 != 0) goto L62
            java.lang.Boolean r7 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r4)
            return r7
        L62:
            int r2 = android.os.Build.VERSION.SDK_INT
            r5 = 29
            if (r2 >= r5) goto L79
            boolean r7 = r8.getWARN_LOGGABLE()
            if (r7 == 0) goto L74
            java.lang.String r7 = "shouldUseTorchAsFlash: API level is too low to know if it's ultra wide camera, defaulting to workaround for safety."
            android.util.Log.w(r3, r7)
        L74:
            java.lang.Boolean r7 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r4)
            return r7
        L79:
            r0.label = r4
            java.lang.Object r8 = r7.invoke(r0)
            if (r8 != r1) goto L82
            return r1
        L82:
            androidx.camera.camera2.pipe.FrameMetadata r8 = (androidx.camera.camera2.pipe.FrameMetadata) r8
            if (r8 != 0) goto L99
            androidx.camera.camera2.pipe.core.Log r7 = androidx.camera.camera2.pipe.core.Log.INSTANCE
            boolean r7 = r7.getWARN_LOGGABLE()
            if (r7 == 0) goto L94
            java.lang.String r7 = "shouldUseTorchAsFlash: frameMetadata is null, defaulting to workaround for safety."
            android.util.Log.w(r3, r7)
        L94:
            java.lang.Boolean r7 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r4)
            return r7
        L99:
            java.lang.Boolean r7 = r6.isUltraWideCamera(r8)
            if (r7 == 0) goto La3
            boolean r4 = r7.booleanValue()
        La3:
            java.lang.Boolean r7 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r4)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.compat.workaround.UseTorchAsFlashImpl.shouldUseTorchAsFlash(kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final Boolean isUltraWideCamera(FrameMetadata frameMetadata) {
        CaptureResult.Key LOGICAL_MULTI_CAMERA_ACTIVE_PHYSICAL_ID = CaptureResult.LOGICAL_MULTI_CAMERA_ACTIVE_PHYSICAL_ID;
        Intrinsics.checkNotNullExpressionValue(LOGICAL_MULTI_CAMERA_ACTIVE_PHYSICAL_ID, "LOGICAL_MULTI_CAMERA_ACTIVE_PHYSICAL_ID");
        String str = (String) frameMetadata.get(LOGICAL_MULTI_CAMERA_ACTIVE_PHYSICAL_ID);
        if (str == null) {
            if (Log.INSTANCE.getWARN_LOGGABLE()) {
                android.util.Log.w("CXCP", "isUltraWideCamera: could not get active physical camera ID to identify if it's ultra wide camera.");
            }
            return null;
        }
        CameraMetadata cameraMetadataM1547awaitCameraMetadataFpsL5FU$default = CameraDevices.CC.m1547awaitCameraMetadataFpsL5FU$default(this.cameraDevices, CameraId.m1603constructorimpl(str), null, 2, null);
        if (cameraMetadataM1547awaitCameraMetadataFpsL5FU$default == null) {
            if (Log.INSTANCE.getWARN_LOGGABLE()) {
                android.util.Log.w("CXCP", "isUltraWideCamera: failed to get CameraMetadata for " + str);
            }
            return null;
        }
        Float fCalculateIntrinsicZoomRatio = this.intrinsicZoomCalculator.calculateIntrinsicZoomRatio(cameraMetadataM1547awaitCameraMetadataFpsL5FU$default);
        if (fCalculateIntrinsicZoomRatio == null) {
            if (Log.INSTANCE.getWARN_LOGGABLE()) {
                android.util.Log.w("CXCP", "isUltraWideCamera: could not calculate intrinsic zoom ratio.");
            }
            return null;
        }
        float fFloatValue = fCalculateIntrinsicZoomRatio.floatValue();
        if (Log.INSTANCE.getDEBUG_LOGGABLE()) {
            android.util.Log.d("CXCP", "isUltraWideCamera: cameraId = " + str + ", intrinsicZoomRatio = " + fFloatValue);
        }
        return Boolean.valueOf(fFloatValue < 1.0f);
    }

    @Override // androidx.camera.camera2.compat.workaround.UseTorchAsFlash
    public boolean shouldDisableAePrecapture() {
        return !getHasUwCameraUnderexposedFlashCaptureQuirk();
    }
}
