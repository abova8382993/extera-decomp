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
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.SourceDebugExtension;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes3.dex */
@Metadata(m876d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ.\u0010\u0010\u001a\u00020\u000b2\u001e\u0010\u0011\u001a\u001a\b\u0001\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00140\u0013\u0012\u0006\u0012\u0004\u0018\u00010\u00150\u0012H\u0096@¢\u0006\u0002\u0010\u0016J\u0013\u0010\u0017\u001a\u0004\u0018\u00010\u000b*\u00020\u0014H\u0003¢\u0006\u0002\u0010\u0018J\b\u0010\u0019\u001a\u00020\u000bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\n\u001a\u00020\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\r¨\u0006\u001a"}, m877d2 = {"Landroidx/camera/camera2/compat/workaround/UseTorchAsFlashImpl;", "Landroidx/camera/camera2/compat/workaround/UseTorchAsFlash;", "cameraQuirks", "Landroidx/camera/camera2/compat/quirk/CameraQuirks;", "cameraDevices", "Landroidx/camera/camera2/pipe/CameraDevices;", "intrinsicZoomCalculator", "Landroidx/camera/camera2/internal/IntrinsicZoomCalculator;", "<init>", "(Landroidx/camera/camera2/compat/quirk/CameraQuirks;Landroidx/camera/camera2/pipe/CameraDevices;Landroidx/camera/camera2/internal/IntrinsicZoomCalculator;)V", "hasUwCameraUnderexposedFlashCaptureQuirk", _UrlKt.FRAGMENT_ENCODE_SET, "getHasUwCameraUnderexposedFlashCaptureQuirk", "()Z", "hasUwCameraUnderexposedFlashCaptureQuirk$delegate", "Lkotlin/Lazy;", "shouldUseTorchAsFlash", "frameMetadata", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "Landroidx/camera/camera2/pipe/FrameMetadata;", _UrlKt.FRAGMENT_ENCODE_SET, "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isUltraWideCamera", "(Landroidx/camera/camera2/pipe/FrameMetadata;)Ljava/lang/Boolean;", "shouldDisableAePrecapture", "camera-camera2"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nUseTorchAsFlash.kt\nKotlin\n*S Kotlin\n*F\n+ 1 UseTorchAsFlash.kt\nandroidx/camera/camera2/compat/workaround/UseTorchAsFlashImpl\n+ 2 Log.kt\nandroidx/camera/camera2/pipe/core/Log\n*L\n1#1,171:1\n50#2,2:172\n71#2,2:174\n71#2,2:176\n71#2,2:178\n71#2,2:180\n71#2,2:182\n50#2,2:184\n*S KotlinDebug\n*F\n+ 1 UseTorchAsFlash.kt\nandroidx/camera/camera2/compat/workaround/UseTorchAsFlashImpl\n*L\n91#1:172,2\n106#1:174,2\n116#1:176,2\n131#1:178,2\n141#1:180,2\n148#1:182,2\n152#1:184,2\n*E\n"})
public final class UseTorchAsFlashImpl implements UseTorchAsFlash {
    private final CameraDevices cameraDevices;
    private final CameraQuirks cameraQuirks;

    /* JADX INFO: renamed from: hasUwCameraUnderexposedFlashCaptureQuirk$delegate, reason: from kotlin metadata */
    private final Lazy hasUwCameraUnderexposedFlashCaptureQuirk = LazyKt.lazy(new Function0() { // from class: androidx.camera.camera2.compat.workaround.UseTorchAsFlashImpl$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Boolean.valueOf(this.f$0.cameraQuirks.getQuirks().contains(UltraWideFlashCaptureUnderexposureQuirk.class));
        }
    });
    private final IntrinsicZoomCalculator intrinsicZoomCalculator;

    /* JADX INFO: renamed from: androidx.camera.camera2.compat.workaround.UseTorchAsFlashImpl$shouldUseTorchAsFlash$1 */
    @Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.compat.workaround.UseTorchAsFlashImpl", m896f = "UseTorchAsFlash.kt", m897i = {}, m898l = {113}, m899m = "shouldUseTorchAsFlash", m900n = {}, m902s = {}, m903v = 1)
    public static final class C01241 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C01241(Continuation<? super C01241> continuation) {
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
        this.cameraQuirks = cameraQuirks;
        this.cameraDevices = cameraDevices;
        this.intrinsicZoomCalculator = intrinsicZoomCalculator;
    }

    public final boolean getHasUwCameraUnderexposedFlashCaptureQuirk() {
        return ((Boolean) this.hasUwCameraUnderexposedFlashCaptureQuirk.getValue()).booleanValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x0013  */
    @Override // androidx.camera.camera2.compat.workaround.UseTorchAsFlash
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object shouldUseTorchAsFlash(kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super androidx.camera.camera2.pipe.FrameMetadata>, ? extends java.lang.Object> r7, kotlin.coroutines.Continuation<? super java.lang.Boolean> r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof androidx.camera.camera2.compat.workaround.UseTorchAsFlashImpl.C01241
            if (r0 == 0) goto L13
            r0 = r8
            androidx.camera.camera2.compat.workaround.UseTorchAsFlashImpl$shouldUseTorchAsFlash$1 r0 = (androidx.camera.camera2.compat.workaround.UseTorchAsFlashImpl.C01241) r0
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
            if (r2 == 0) goto L32
            if (r2 != r4) goto L2b
            kotlin.ResultKt.throwOnFailure(r8)
            goto L7e
        L2b:
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r6)
            r6 = 0
            return r6
        L32:
            kotlin.ResultKt.throwOnFailure(r8)
            androidx.camera.camera2.pipe.core.Log r8 = androidx.camera.camera2.pipe.core.Log.INSTANCE
            boolean r2 = r8.getDEBUG_LOGGABLE()
            if (r2 == 0) goto L53
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r5 = "shouldUseTorchAsFlash: hasUwCameraUnderexposedFlashCaptureQuirk = "
            r2.<init>(r5)
            boolean r5 = access$getHasUwCameraUnderexposedFlashCaptureQuirk(r6)
            r2.append(r5)
            java.lang.String r2 = r2.toString()
            android.util.Log.d(r3, r2)
        L53:
            boolean r2 = r6.getHasUwCameraUnderexposedFlashCaptureQuirk()
            if (r2 != 0) goto L5e
            java.lang.Boolean r6 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r4)
            return r6
        L5e:
            int r2 = android.os.Build.VERSION.SDK_INT
            r5 = 29
            if (r2 >= r5) goto L75
            boolean r6 = r8.getWARN_LOGGABLE()
            if (r6 == 0) goto L70
            java.lang.String r6 = "shouldUseTorchAsFlash: API level is too low to know if it's ultra wide camera, defaulting to workaround for safety."
            android.util.Log.w(r3, r6)
        L70:
            java.lang.Boolean r6 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r4)
            return r6
        L75:
            r0.label = r4
            java.lang.Object r8 = r7.invoke(r0)
            if (r8 != r1) goto L7e
            return r1
        L7e:
            androidx.camera.camera2.pipe.FrameMetadata r8 = (androidx.camera.camera2.pipe.FrameMetadata) r8
            if (r8 != 0) goto L95
            androidx.camera.camera2.pipe.core.Log r6 = androidx.camera.camera2.pipe.core.Log.INSTANCE
            boolean r6 = r6.getWARN_LOGGABLE()
            if (r6 == 0) goto L90
            java.lang.String r6 = "shouldUseTorchAsFlash: frameMetadata is null, defaulting to workaround for safety."
            android.util.Log.w(r3, r6)
        L90:
            java.lang.Boolean r6 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r4)
            return r6
        L95:
            java.lang.Boolean r6 = r6.isUltraWideCamera(r8)
            if (r6 == 0) goto L9f
            boolean r4 = r6.booleanValue()
        L9f:
            java.lang.Boolean r6 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r4)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.compat.workaround.UseTorchAsFlashImpl.shouldUseTorchAsFlash(kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final Boolean isUltraWideCamera(FrameMetadata frameMetadata) {
        String str = (String) frameMetadata.get(CaptureResult.LOGICAL_MULTI_CAMERA_ACTIVE_PHYSICAL_ID);
        if (str == null) {
            if (Log.INSTANCE.getWARN_LOGGABLE()) {
                android.util.Log.w("CXCP", "isUltraWideCamera: could not get active physical camera ID to identify if it's ultra wide camera.");
            }
            return null;
        }
        CameraMetadata cameraMetadataM1437awaitCameraMetadataFpsL5FU$default = CameraDevices.m1437awaitCameraMetadataFpsL5FU$default(this.cameraDevices, CameraId.m1497constructorimpl(str), null, 2, null);
        if (cameraMetadataM1437awaitCameraMetadataFpsL5FU$default == null) {
            if (Log.INSTANCE.getWARN_LOGGABLE()) {
                android.util.Log.w("CXCP", "isUltraWideCamera: failed to get CameraMetadata for ".concat(str));
            }
            return null;
        }
        Float fCalculateIntrinsicZoomRatio = this.intrinsicZoomCalculator.calculateIntrinsicZoomRatio(cameraMetadataM1437awaitCameraMetadataFpsL5FU$default);
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
