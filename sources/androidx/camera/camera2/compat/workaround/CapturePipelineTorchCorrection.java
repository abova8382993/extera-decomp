package androidx.camera.camera2.compat.workaround;

import androidx.camera.camera2.adapter.CaptureConfigAdapter;
import androidx.camera.camera2.compat.quirk.DeviceQuirks;
import androidx.camera.camera2.compat.quirk.TorchIsClosedAfterImageCapturingQuirk;
import androidx.camera.camera2.impl.CameraProperties;
import androidx.camera.camera2.impl.CapturePipeline;
import androidx.camera.camera2.impl.CapturePipelineImpl;
import androidx.camera.camera2.impl.TorchControl;
import androidx.camera.camera2.impl.UseCaseThreads;
import androidx.camera.camera2.pipe.CameraMetadata;
import androidx.camera.core.impl.CaptureConfig;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.inject.Provider;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: loaded from: classes3.dex */
public final class CapturePipelineTorchCorrection implements CapturePipeline {
    public static final Companion Companion = new Companion(null);
    private static final boolean isEnabled;
    private final Lazy capturePipelineImpl$delegate;
    private final Provider capturePipelineImplProvider;
    private final Lazy isLegacyDevice$delegate;
    private int template;
    private final UseCaseThreads threads;
    private final TorchControl torchControl;

    public CapturePipelineTorchCorrection(final CameraProperties cameraProperties, Provider capturePipelineImplProvider, UseCaseThreads threads, TorchControl torchControl) {
        Intrinsics.checkNotNullParameter(cameraProperties, "cameraProperties");
        Intrinsics.checkNotNullParameter(capturePipelineImplProvider, "capturePipelineImplProvider");
        Intrinsics.checkNotNullParameter(threads, "threads");
        Intrinsics.checkNotNullParameter(torchControl, "torchControl");
        this.capturePipelineImplProvider = capturePipelineImplProvider;
        this.threads = threads;
        this.torchControl = torchControl;
        this.isLegacyDevice$delegate = LazyKt.lazy(new Function0() { // from class: androidx.camera.camera2.compat.workaround.CapturePipelineTorchCorrection$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(CapturePipelineTorchCorrection.isLegacyDevice_delegate$lambda$0(cameraProperties));
            }
        });
        this.capturePipelineImpl$delegate = LazyKt.lazy(new Function0() { // from class: androidx.camera.camera2.compat.workaround.CapturePipelineTorchCorrection$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CapturePipelineTorchCorrection.capturePipelineImpl_delegate$lambda$0(this.f$0);
            }
        });
        this.template = 1;
    }

    private final boolean isLegacyDevice() {
        return ((Boolean) this.isLegacyDevice$delegate.getValue()).booleanValue();
    }

    public static final boolean isLegacyDevice_delegate$lambda$0(CameraProperties cameraProperties) {
        return CameraMetadata.Companion.isHardwareLevelLegacy(cameraProperties.getMetadata());
    }

    public static final CapturePipelineImpl capturePipelineImpl_delegate$lambda$0(CapturePipelineTorchCorrection capturePipelineTorchCorrection) {
        return (CapturePipelineImpl) capturePipelineTorchCorrection.capturePipelineImplProvider.get();
    }

    private final CapturePipelineImpl getCapturePipelineImpl() {
        return (CapturePipelineImpl) this.capturePipelineImpl$delegate.getValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0016  */
    @Override // androidx.camera.camera2.impl.CapturePipeline
    /* JADX INFO: renamed from: submitStillCaptures-BvXKQx0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object mo1413submitStillCapturesBvXKQx0(java.util.List r11, int r12, androidx.camera.core.impl.Config r13, int r14, int r15, int r16, kotlin.coroutines.Continuation r17) {
        /*
            r10 = this;
            r0 = r17
            boolean r1 = r0 instanceof androidx.camera.camera2.compat.workaround.CapturePipelineTorchCorrection$submitStillCaptures$1
            if (r1 == 0) goto L16
            r1 = r0
            androidx.camera.camera2.compat.workaround.CapturePipelineTorchCorrection$submitStillCaptures$1 r1 = (androidx.camera.camera2.compat.workaround.CapturePipelineTorchCorrection$submitStillCaptures$1) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L16
            int r2 = r2 - r3
            r1.label = r2
        L14:
            r9 = r1
            goto L1c
        L16:
            androidx.camera.camera2.compat.workaround.CapturePipelineTorchCorrection$submitStillCaptures$1 r1 = new androidx.camera.camera2.compat.workaround.CapturePipelineTorchCorrection$submitStillCaptures$1
            r1.<init>(r10, r0)
            goto L14
        L1c:
            java.lang.Object r0 = r9.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r9.label
            r3 = 1
            if (r2 == 0) goto L37
            if (r2 != r3) goto L2f
            boolean r1 = r9.Z$0
            kotlin.ResultKt.throwOnFailure(r0)
            goto L56
        L2f:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L37:
            kotlin.ResultKt.throwOnFailure(r0)
            boolean r0 = r10.m1412isCorrectionRequired0UCm73U(r11, r12)
            androidx.camera.camera2.impl.CapturePipelineImpl r2 = r10.getCapturePipelineImpl()
            r9.Z$0 = r0
            r9.label = r3
            r3 = r11
            r4 = r12
            r5 = r13
            r6 = r14
            r7 = r15
            r8 = r16
            java.lang.Object r2 = r2.mo1413submitStillCapturesBvXKQx0(r3, r4, r5, r6, r7, r8, r9)
            if (r2 != r1) goto L54
            return r1
        L54:
            r1 = r0
            r0 = r2
        L56:
            java.util.List r0 = (java.util.List) r0
            if (r1 == 0) goto L74
            androidx.camera.camera2.impl.UseCaseThreads r1 = r10.threads
            kotlinx.coroutines.CoroutineScope r1 = r1.getSequentialScope()
            androidx.camera.camera2.compat.workaround.CapturePipelineTorchCorrection$submitStillCaptures$2 r2 = new androidx.camera.camera2.compat.workaround.CapturePipelineTorchCorrection$submitStillCaptures$2
            r3 = 0
            r2.<init>(r0, r10, r3)
            r3 = 3
            r4 = 0
            r5 = 0
            r6 = 0
            r11 = r1
            r14 = r2
            r15 = r3
            r16 = r4
            r12 = r5
            r13 = r6
            kotlinx.coroutines.BuildersKt.launch$default(r11, r12, r13, r14, r15, r16)
        L74:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.compat.workaround.CapturePipelineTorchCorrection.mo1413submitStillCapturesBvXKQx0(java.util.List, int, androidx.camera.core.impl.Config, int, int, int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // androidx.camera.camera2.impl.CapturePipeline
    public void setTemplate(int i) {
        getCapturePipelineImpl().setTemplate(i);
        this.template = i;
    }

    /* JADX INFO: renamed from: isCorrectionRequired-0UCm73U */
    private final boolean m1412isCorrectionRequired0UCm73U(List list, int i) {
        List list2 = list;
        if ((list2 instanceof Collection) && list2.isEmpty()) {
            return false;
        }
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            if (CaptureConfigAdapter.Companion.m1386getStillCaptureTemplateCMLptTo$camera_camera2((CaptureConfig) it.next(), i, isLegacyDevice()) == 2) {
                return isTorchOn();
            }
        }
        return false;
    }

    private final boolean isTorchOn() {
        Integer num = (Integer) this.torchControl.getTorchStateLiveData().getValue();
        return num != null && num.intValue() == 1;
    }

    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean isEnabled() {
            return CapturePipelineTorchCorrection.isEnabled;
        }
    }

    static {
        isEnabled = DeviceQuirks.INSTANCE.get(TorchIsClosedAfterImageCapturingQuirk.class) != null;
    }
}
