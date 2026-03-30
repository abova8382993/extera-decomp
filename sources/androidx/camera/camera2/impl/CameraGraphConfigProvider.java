package androidx.camera.camera2.impl;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.params.DynamicRangeProfiles;
import android.os.Build;
import android.util.Log;
import android.util.Size;
import androidx.camera.camera2.adapter.GraphStateToCameraStateAdapter;
import androidx.camera.camera2.adapter.ZslControl;
import androidx.camera.camera2.compat.DynamicRangeProfilesCompat;
import androidx.camera.camera2.compat.quirk.CameraQuirks;
import androidx.camera.camera2.compat.quirk.CaptureSessionStuckQuirk;
import androidx.camera.camera2.compat.quirk.DeviceQuirks;
import androidx.camera.camera2.compat.quirk.DisableAbortCapturesOnStopQuirk;
import androidx.camera.camera2.compat.quirk.DisableAbortCapturesOnStopWithSessionProcessorQuirk;
import androidx.camera.camera2.compat.quirk.FinalizeSessionOnCloseQuirk;
import androidx.camera.camera2.compat.quirk.QuickSuccessiveImageCaptureFailsRepeatingRequestQuirk;
import androidx.camera.camera2.compat.workaround.CloseCameraOnCameraGraphClose;
import androidx.camera.camera2.compat.workaround.TemplateParamsOverride;
import androidx.camera.camera2.config.CameraConfig;
import androidx.camera.camera2.internal.DynamicRangeConversions;
import androidx.camera.camera2.pipe.CameraGraph;
import androidx.camera.camera2.pipe.CameraId;
import androidx.camera.camera2.pipe.CameraMetadata;
import androidx.camera.camera2.pipe.CameraStream;
import androidx.camera.camera2.pipe.OutputStream;
import androidx.camera.camera2.pipe.StreamFormat;
import androidx.camera.core.CameraXConfig;
import androidx.camera.core.DynamicRange;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.SessionConfig;
import java.util.Map;
import kotlin.collections.ArraysKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.telegram.messenger.MediaDataController;

/* JADX INFO: loaded from: classes3.dex */
public final class CameraGraphConfigProvider {
    private final CameraCallbackMap callbackMap;
    private final CameraConfig cameraConfig;
    private final CameraInteropStateCallbackRepository cameraInteropStateCallbackRepository;
    private final CameraMetadata cameraMetadata;
    private final CameraQuirks cameraQuirks;
    private final CameraXConfig cameraXConfig;
    private final CloseCameraOnCameraGraphClose closeCameraOnCameraGraphClose;
    private final ComboRequestListener requestListener;
    private final DynamicRangeProfiles supportedDynamicRangeProfiles;
    private final TemplateParamsOverride templateParamsOverride;
    private final ZslControl zslControl;

    public CameraGraphConfigProvider(CameraCallbackMap callbackMap, ComboRequestListener requestListener, CameraConfig cameraConfig, CameraQuirks cameraQuirks, ZslControl zslControl, TemplateParamsOverride templateParamsOverride, CameraMetadata cameraMetadata, CameraXConfig cameraXConfig, CameraInteropStateCallbackRepository cameraInteropStateCallbackRepository) {
        DynamicRangeProfilesCompat dynamicRangeProfilesCompatFromCameraMetaData;
        Intrinsics.checkNotNullParameter(callbackMap, "callbackMap");
        Intrinsics.checkNotNullParameter(requestListener, "requestListener");
        Intrinsics.checkNotNullParameter(cameraConfig, "cameraConfig");
        Intrinsics.checkNotNullParameter(cameraQuirks, "cameraQuirks");
        Intrinsics.checkNotNullParameter(zslControl, "zslControl");
        Intrinsics.checkNotNullParameter(templateParamsOverride, "templateParamsOverride");
        this.callbackMap = callbackMap;
        this.requestListener = requestListener;
        this.cameraConfig = cameraConfig;
        this.cameraQuirks = cameraQuirks;
        this.zslControl = zslControl;
        this.templateParamsOverride = templateParamsOverride;
        this.cameraMetadata = cameraMetadata;
        this.cameraXConfig = cameraXConfig;
        this.cameraInteropStateCallbackRepository = cameraInteropStateCallbackRepository;
        this.closeCameraOnCameraGraphClose = new CloseCameraOnCameraGraphClose();
        DynamicRangeProfiles dynamicRangeProfiles = null;
        if (Build.VERSION.SDK_INT >= 33 && cameraMetadata != null && (dynamicRangeProfilesCompatFromCameraMetaData = DynamicRangeProfilesCompat.Companion.fromCameraMetaData(cameraMetadata)) != null) {
            dynamicRangeProfiles = dynamicRangeProfilesCompatFromCameraMetaData.toDynamicRangeProfiles();
        }
        this.supportedDynamicRangeProfiles = dynamicRangeProfiles;
    }

    public /* synthetic */ CameraGraphConfigProvider(CameraCallbackMap cameraCallbackMap, ComboRequestListener comboRequestListener, CameraConfig cameraConfig, CameraQuirks cameraQuirks, ZslControl zslControl, TemplateParamsOverride templateParamsOverride, CameraMetadata cameraMetadata, CameraXConfig cameraXConfig, CameraInteropStateCallbackRepository cameraInteropStateCallbackRepository, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(cameraCallbackMap, comboRequestListener, cameraConfig, cameraQuirks, zslControl, templateParamsOverride, cameraMetadata, (i & 128) != 0 ? null : cameraXConfig, (i & 256) != 0 ? null : cameraInteropStateCallbackRepository);
    }

    public static final class CameraGraphCreationResult {
        private final CameraGraph.Config config;
        private final Map streamConfigMap;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CameraGraphCreationResult)) {
                return false;
            }
            CameraGraphCreationResult cameraGraphCreationResult = (CameraGraphCreationResult) obj;
            return Intrinsics.areEqual(this.config, cameraGraphCreationResult.config) && Intrinsics.areEqual(this.streamConfigMap, cameraGraphCreationResult.streamConfigMap);
        }

        public int hashCode() {
            return (this.config.hashCode() * 31) + this.streamConfigMap.hashCode();
        }

        public String toString() {
            return "CameraGraphCreationResult(config=" + this.config + ", streamConfigMap=" + this.streamConfigMap + ')';
        }

        public CameraGraphCreationResult(CameraGraph.Config config, Map streamConfigMap) {
            Intrinsics.checkNotNullParameter(config, "config");
            Intrinsics.checkNotNullParameter(streamConfigMap, "streamConfigMap");
            this.config = config;
            this.streamConfigMap = streamConfigMap;
        }

        public final CameraGraph.Config getConfig() {
            return this.config;
        }

        public final Map getStreamConfigMap() {
            return this.streamConfigMap;
        }
    }

    /* JADX INFO: renamed from: create-79VDu0o$default */
    public static /* synthetic */ CameraGraphCreationResult m1428create79VDu0o$default(CameraGraphConfigProvider cameraGraphConfigProvider, int i, SessionConfig sessionConfig, boolean z, GraphStateToCameraStateAdapter graphStateToCameraStateAdapter, Integer num, Map map, Map map2, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            graphStateToCameraStateAdapter = null;
        }
        if ((i2 & 16) != 0) {
            num = null;
        }
        if ((i2 & 32) != 0) {
            map = MapsKt.emptyMap();
        }
        if ((i2 & 64) != 0) {
            map2 = MapsKt.emptyMap();
        }
        return cameraGraphConfigProvider.m1432create79VDu0o(i, sessionConfig, z, graphStateToCameraStateAdapter, num, map, map2);
    }

    /* JADX WARN: Removed duplicated region for block: B:153:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x014c  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0169  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0192  */
    /* JADX INFO: renamed from: create-79VDu0o */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final androidx.camera.camera2.impl.CameraGraphConfigProvider.CameraGraphCreationResult m1432create79VDu0o(int r37, androidx.camera.core.impl.SessionConfig r38, boolean r39, androidx.camera.camera2.adapter.GraphStateToCameraStateAdapter r40, java.lang.Integer r41, java.util.Map r42, java.util.Map r43) {
        /*
            Method dump skipped, instruction units count: 810
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CameraGraphConfigProvider.m1432create79VDu0o(int, androidx.camera.core.impl.SessionConfig, boolean, androidx.camera.camera2.adapter.GraphStateToCameraStateAdapter, java.lang.Integer, java.util.Map, java.util.Map):androidx.camera.camera2.impl.CameraGraphConfigProvider$CameraGraphCreationResult");
    }

    private final CameraStream.Config createPostviewStream(SessionConfig.OutputConfig outputConfig, String str) {
        OutputStream.MirrorMode mirrorModeM1708boximpl;
        OutputStream.MirrorMode mirrorMode;
        DeferrableSurface surface = outputConfig.getSurface();
        Intrinsics.checkNotNullExpressionValue(surface, "getSurface(...)");
        String physicalCameraId = str == null ? outputConfig.getPhysicalCameraId() : str;
        int mirrorMode2 = outputConfig.getMirrorMode();
        OutputStream.Config.Companion companion = OutputStream.Config.Companion;
        Size prescribedSize = surface.getPrescribedSize();
        Intrinsics.checkNotNullExpressionValue(prescribedSize, "getPrescribedSize(...)");
        int iM1773constructorimpl = StreamFormat.m1773constructorimpl(surface.getPrescribedStreamFormat());
        String strM1603constructorimpl = physicalCameraId == null ? null : CameraId.m1603constructorimpl(physicalCameraId);
        if (mirrorMode2 == 0) {
            mirrorModeM1708boximpl = OutputStream.MirrorMode.m1708boximpl(OutputStream.MirrorMode.m1709constructorimpl(1));
        } else if (mirrorMode2 == 1) {
            mirrorModeM1708boximpl = OutputStream.MirrorMode.m1708boximpl(OutputStream.MirrorMode.m1709constructorimpl(2));
        } else {
            mirrorMode = null;
            return CameraStream.Config.Companion.create$default(CameraStream.Config.Companion, OutputStream.Config.Companion.m1698createvBYXiEU$default(companion, prescribedSize, iM1773constructorimpl, strM1603constructorimpl, null, mirrorMode, null, null, null, null, null, MediaDataController.MAX_STYLE_RUNS_COUNT, null), null, 2, null);
        }
        mirrorMode = mirrorModeM1708boximpl;
        return CameraStream.Config.Companion.create$default(CameraStream.Config.Companion, OutputStream.Config.Companion.m1698createvBYXiEU$default(companion, prescribedSize, iM1773constructorimpl, strM1603constructorimpl, null, mirrorMode, null, null, null, null, null, MediaDataController.MAX_STYLE_RUNS_COUNT, null), null, 2, null);
    }

    /* JADX INFO: renamed from: getStreamUseCase-MhLBY4I */
    private final OutputStream.StreamUseCase m1429getStreamUseCaseMhLBY4I(DeferrableSurface deferrableSurface, Map map, CameraMetadata cameraMetadata) {
        Long l = (Long) map.get(deferrableSurface);
        OutputStream.StreamUseCase streamUseCaseM1716boximpl = l != null ? OutputStream.StreamUseCase.m1716boximpl(OutputStream.StreamUseCase.m1717constructorimpl(l.longValue())) : null;
        if (Build.VERSION.SDK_INT >= 33 && streamUseCaseM1716boximpl != null && cameraMetadata != null) {
            CameraCharacteristics.Key SCALER_AVAILABLE_STREAM_USE_CASES = CameraCharacteristics.SCALER_AVAILABLE_STREAM_USE_CASES;
            Intrinsics.checkNotNullExpressionValue(SCALER_AVAILABLE_STREAM_USE_CASES, "SCALER_AVAILABLE_STREAM_USE_CASES");
            long[] jArr = (long[]) cameraMetadata.get(SCALER_AVAILABLE_STREAM_USE_CASES);
            if (jArr != null && ArraysKt.contains(jArr, streamUseCaseM1716boximpl.m1722unboximpl())) {
                return streamUseCaseM1716boximpl;
            }
        }
        Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
        if (Logger.isWarnEnabled("CXCP")) {
            Log.w(Camera2Logger.TRUNCATED_TAG, "Expected stream use case for " + deferrableSurface + ", " + streamUseCaseM1716boximpl + " cannot be set!");
        }
        return null;
    }

    /* JADX INFO: renamed from: getStreamUseHint-kVKJKLA */
    private final OutputStream.StreamUseHint m1430getStreamUseHintkVKJKLA(DeferrableSurface deferrableSurface, Map map) {
        Long l = (Long) map.get(deferrableSurface);
        if (l != null) {
            return OutputStream.StreamUseHint.m1726boximpl(OutputStream.StreamUseHint.m1727constructorimpl(l.longValue()));
        }
        return null;
    }

    private final CameraGraph.Flags createCameraGraphFlags(CameraQuirks cameraQuirks, boolean z) {
        if (cameraQuirks.getQuirks().contains(CaptureSessionStuckQuirk.class)) {
            Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
            if (Logger.isDebugEnabled("CXCP")) {
                Log.d(Camera2Logger.TRUNCATED_TAG, "CameraPipe should be enabling CaptureSessionStuckQuirk by default");
            }
        }
        int iM1410getBehaviorBm6Tfm4 = FinalizeSessionOnCloseQuirk.Companion.m1410getBehaviorBm6Tfm4();
        boolean zShouldCloseCameraDevice = this.closeCameraOnCameraGraphClose.shouldCloseCameraDevice(z);
        boolean z2 = false;
        if ((!z || DeviceQuirks.INSTANCE.get(DisableAbortCapturesOnStopWithSessionProcessorQuirk.class) == null) && DeviceQuirks.INSTANCE.get(DisableAbortCapturesOnStopQuirk.class) == null && Build.VERSION.SDK_INT >= 30) {
            z2 = true;
        }
        boolean z3 = z2;
        boolean zContains = cameraQuirks.getQuirks().contains(QuickSuccessiveImageCaptureFailsRepeatingRequestQuirk.class);
        return new CameraGraph.Flags(false, z3, new CameraGraph.RepeatingRequestRequirementsBeforeCapture(zContains ? 1 : 0, CameraGraph.RepeatingRequestRequirementsBeforeCapture.CompletionBehavior.AT_LEAST, null), null, iM1410getBehaviorBm6Tfm4, true, zShouldCloseCameraDevice, true, 9, null);
    }

    /* JADX INFO: renamed from: toDynamicRangeProfile--zsJmt4 */
    private final OutputStream.DynamicRangeProfile m1431toDynamicRangeProfilezsJmt4(DynamicRange dynamicRange) {
        if (Build.VERSION.SDK_INT < 33) {
            return null;
        }
        OutputStream.DynamicRangeProfile dynamicRangeProfileM1700boximpl = OutputStream.DynamicRangeProfile.m1700boximpl(OutputStream.DynamicRangeProfile.Companion.m1707getSTANDARDfFAQAUE());
        DynamicRangeProfiles dynamicRangeProfiles = this.supportedDynamicRangeProfiles;
        if (dynamicRangeProfiles != null) {
            Long lDynamicRangeToFirstSupportedProfile = DynamicRangeConversions.INSTANCE.dynamicRangeToFirstSupportedProfile(dynamicRange, dynamicRangeProfiles);
            if (lDynamicRangeToFirstSupportedProfile != null) {
                return OutputStream.DynamicRangeProfile.m1700boximpl(OutputStream.DynamicRangeProfile.m1701constructorimpl(lDynamicRangeToFirstSupportedProfile.longValue()));
            }
            Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
            if (Logger.isErrorEnabled("CXCP")) {
                Log.e(Camera2Logger.TRUNCATED_TAG, "Requested dynamic range is not supported. Defaulting to STANDARD dynamic range profile.\nRequested dynamic range:\n " + dynamicRange);
            }
        }
        return dynamicRangeProfileM1700boximpl;
    }

    private final Camera2ImplConfig toCamera2ImplConfig(SessionConfig sessionConfig) {
        Config implementationOptions = sessionConfig.getImplementationOptions();
        Intrinsics.checkNotNullExpressionValue(implementationOptions, "getImplementationOptions(...)");
        return new Camera2ImplConfig(implementationOptions);
    }

    private final Integer getVideoStabilizationModeFromCaptureConfig(CaptureConfig captureConfig) {
        int previewStabilizationMode = captureConfig.getPreviewStabilizationMode();
        int videoStabilizationMode = captureConfig.getVideoStabilizationMode();
        if (previewStabilizationMode == 1 || videoStabilizationMode == 1) {
            return 0;
        }
        if (previewStabilizationMode == 2) {
            return 2;
        }
        return videoStabilizationMode == 2 ? 1 : null;
    }

    public String toString() {
        return "CameraGraphConfigProvider<" + ((Object) CameraId.m1607toStringimpl(this.cameraConfig.m1417getCameraIdDz_R5H8())) + '>';
    }
}
