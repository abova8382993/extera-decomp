package com.exteragram.messenger.camera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.util.Range;
import android.util.Size;
import android.view.WindowManager;
import androidx.camera.camera2.interop.Camera2CameraInfo;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ConcurrentCamera;
import androidx.camera.core.DynamicRange;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.MeteringPointFactory;
import androidx.camera.core.Preview;
import androidx.camera.core.SessionConfig;
import androidx.camera.core.UseCase;
import androidx.camera.core.UseCaseGroup;
import androidx.camera.core.ZoomState;
import androidx.camera.core.featuregroup.GroupableFeature;
import androidx.camera.core.resolutionselector.AspectRatioStrategy;
import androidx.camera.core.resolutionselector.ResolutionSelector;
import androidx.camera.extensions.ExtensionsManager;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.video.FallbackStrategy;
import androidx.camera.video.GroupableFeatures;
import androidx.camera.video.Quality;
import androidx.camera.video.QualitySelector;
import androidx.camera.video.Recorder;
import androidx.camera.video.VideoCapture;
import androidx.core.content.ContextCompat;
import androidx.view.Lifecycle;
import androidx.view.LifecycleOwner;
import androidx.view.LifecycleRegistry;
import com.exteragram.messenger.ExteraConfig;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import kotlin.coroutines.Continuation;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.Utilities;

/* JADX INFO: loaded from: classes.dex */
public class CameraXSession {
    private static Boolean isSeamlessSwitchingAvailableDefaultCache;
    Camera camera;
    Camera cameraBack;
    private CameraControl cameraControl;
    private CameraControl cameraControlBack;
    private CameraControl cameraControlFront;
    Camera cameraFront;
    private CameraSelector cameraSelector;
    private boolean isFrontface;
    private final CameraLifecycle lifecycle;
    private final MeteringPointFactory meteringPointFactory;
    private Preview previewUseCase;
    private Preview previewUseCaseBack;
    ProcessCameraProvider provider;
    private final Preview.SurfaceProvider surfaceProviderPrimary;
    private Preview.SurfaceProvider surfaceProviderSecondary;
    private VideoCapture<Recorder> videoCapture;
    private static final Map<CameraSelector, Boolean> stabilizationCache = new HashMap();
    private static final Map<CameraSelector, Range<Integer>[]> fpsRangesCache = new HashMap();
    private static final Range<Integer> EXTENDED_FPS_RANGE = new Range<>(30, 60);
    private static final Map<CameraSelector, Map<Quality, Size>> videoSizesCache = new HashMap();
    private boolean isInitiated = false;
    private boolean isDualMode = false;
    private boolean isBinding = false;
    private boolean isTorchOn = false;

    /* JADX INFO: loaded from: classes4.dex */
    public static class CameraLifecycle implements LifecycleOwner {
        private final LifecycleRegistry lifecycleRegistry;

        public CameraLifecycle() {
            LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
            this.lifecycleRegistry = lifecycleRegistry;
            lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
        }

        public void start() {
            try {
                if (this.lifecycleRegistry.getState() != Lifecycle.State.DESTROYED) {
                    this.lifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);
                }
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
        }

        public void stop() {
            try {
                this.lifecycleRegistry.setCurrentState(Lifecycle.State.DESTROYED);
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
        }

        @Override // androidx.view.LifecycleOwner
        public Lifecycle getLifecycle() {
            return this.lifecycleRegistry;
        }
    }

    public CameraXSession(CameraLifecycle cameraLifecycle, MeteringPointFactory meteringPointFactory, Preview.SurfaceProvider surfaceProvider) {
        this.lifecycle = cameraLifecycle;
        this.meteringPointFactory = meteringPointFactory;
        this.surfaceProviderPrimary = surfaceProvider;
    }

    public void setSecondSurfaceProvider(Preview.SurfaceProvider surfaceProvider) {
        Preview preview;
        this.surfaceProviderSecondary = surfaceProvider;
        if (this.isInitiated && this.isDualMode && (preview = this.previewUseCaseBack) != null) {
            preview.setSurfaceProvider(surfaceProvider);
        }
    }

    public boolean isInitiated() {
        return this.isInitiated;
    }

    public boolean isReady() {
        return (!this.isInitiated || this.isBinding || this.camera == null) ? false : true;
    }

    public boolean isDualMode() {
        return this.isDualMode;
    }

    public boolean isFrontface() {
        return this.isFrontface;
    }

    public void initCamera(final Context context, boolean z, final boolean z2, final Runnable runnable) {
        this.isFrontface = z;
        final ListenableFuture<ProcessCameraProvider> processCameraProvider = ProcessCameraProvider.getInstance(context);
        processCameraProvider.addListener(new Runnable() { // from class: com.exteragram.messenger.camera.CameraXSession$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$initCamera$1(processCameraProvider, z2, context, runnable);
            }
        }, ContextCompat.getMainExecutor(context));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$initCamera$1(ListenableFuture listenableFuture, boolean z, Context context, final Runnable runnable) {
        try {
            this.provider = (ProcessCameraProvider) listenableFuture.get();
            checkConcurrentSupport(z);
            ExtensionsManager.getInstanceAsync(context, this.provider).addListener(new Runnable() { // from class: com.exteragram.messenger.camera.CameraXSession$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$initCamera$0(runnable);
                }
            }, ContextCompat.getMainExecutor(context));
        } catch (InterruptedException | ExecutionException e) {
            FileLog.m1048e(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initCamera$0(Runnable runnable) {
        if (this.lifecycle.getLifecycle().getState() == Lifecycle.State.DESTROYED) {
            return;
        }
        prepareUseCases();
        bindUseCases();
        this.lifecycle.start();
        if (runnable != null) {
            runnable.run();
        }
        this.isInitiated = true;
    }

    private void checkConcurrentSupport(boolean z) {
        if (!z) {
            this.isDualMode = false;
            return;
        }
        ProcessCameraProvider processCameraProvider = this.provider;
        if (processCameraProvider != null) {
            Iterator<List<CameraInfo>> it = processCameraProvider.getAvailableConcurrentCameraInfos().iterator();
            while (it.hasNext()) {
                boolean z2 = false;
                boolean z3 = false;
                for (CameraInfo cameraInfo : it.next()) {
                    if (cameraInfo.getLensFacing() == 0) {
                        z2 = true;
                    }
                    if (cameraInfo.getLensFacing() == 1) {
                        z3 = true;
                    }
                }
                if (z2 && z3) {
                    this.isDualMode = true;
                    return;
                }
            }
        }
        this.isDualMode = false;
    }

    public void switchCamera() {
        boolean z = !this.isFrontface;
        this.isFrontface = z;
        if (this.isDualMode && this.cameraFront != null && this.cameraBack != null) {
            updateActiveControl(z);
            updateTorchState();
        } else {
            prepareUseCases();
            bindUseCases();
        }
    }

    private void updateActiveControl(boolean z) {
        if (z) {
            this.camera = this.cameraFront;
            this.cameraControl = this.cameraControlFront;
        } else {
            this.camera = this.cameraBack;
            this.cameraControl = this.cameraControlBack;
        }
    }

    public void closeCamera() {
        ProcessCameraProvider processCameraProvider = this.provider;
        if (processCameraProvider != null) {
            processCameraProvider.unbindAll();
        }
        this.lifecycle.stop();
        this.isInitiated = false;
    }

    public boolean isFlashAvailable() {
        Camera camera = this.camera;
        if (camera == null) {
            return false;
        }
        return camera.getCameraInfo().hasFlashUnit();
    }

    public void setTorchEnabled(boolean z) {
        this.isTorchOn = z;
        updateTorchState();
    }

    private void updateTorchState() {
        Camera camera;
        try {
            boolean z = true;
            if (this.isDualMode) {
                CameraControl cameraControl = this.cameraControlFront;
                if (cameraControl != null) {
                    cameraControl.enableTorch(false);
                }
                if (this.cameraControlBack == null || (camera = this.cameraBack) == null || !camera.getCameraInfo().hasFlashUnit()) {
                    return;
                }
                CameraControl cameraControl2 = this.cameraControlBack;
                if (!this.isTorchOn || this.isFrontface) {
                    z = false;
                }
                cameraControl2.enableTorch(z);
                return;
            }
            Camera camera2 = this.camera;
            if (camera2 == null || this.cameraControl == null) {
                return;
            }
            if (camera2.getCameraInfo().getLensFacing() == 1 && isFlashAvailable()) {
                this.cameraControl.enableTorch(this.isTorchOn);
            } else if (isFlashAvailable()) {
                this.cameraControl.enableTorch(false);
            }
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
    }

    public void prepareUseCases() {
        if (this.isDualMode) {
            prepareDualUseCases();
        } else {
            prepareSingleUseCases();
        }
    }

    private void prepareSingleUseCases() {
        CameraSelector cameraSelector = this.isFrontface ? CameraSelector.DEFAULT_FRONT_CAMERA : CameraSelector.DEFAULT_BACK_CAMERA;
        this.cameraSelector = cameraSelector;
        Preview previewBuildPreview = buildPreview(cameraSelector);
        this.previewUseCase = previewBuildPreview;
        previewBuildPreview.setSurfaceProvider(this.surfaceProviderPrimary);
        this.videoCapture = buildVideoCapture(this.cameraSelector);
    }

    private void prepareDualUseCases() {
        Preview previewBuildPreview = buildPreview(CameraSelector.DEFAULT_FRONT_CAMERA);
        this.previewUseCase = previewBuildPreview;
        previewBuildPreview.setSurfaceProvider(this.surfaceProviderPrimary);
        this.videoCapture = null;
        Preview previewBuildPreview2 = buildPreview(CameraSelector.DEFAULT_BACK_CAMERA);
        this.previewUseCaseBack = previewBuildPreview2;
        Preview.SurfaceProvider surfaceProvider = this.surfaceProviderSecondary;
        if (surfaceProvider != null) {
            previewBuildPreview2.setSurfaceProvider(surfaceProvider);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [void] */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    private Preview buildPreview(CameraSelector cameraSelector) {
        ProcessCameraProvider processCameraProvider;
        boolean zEquals;
        Preview.Builder resolutionSelector = new Preview.Builder().setResolutionSelector(new ResolutionSelector.Builder().setAspectRatioStrategy(SharedConfig.roundCamera16to9 ? AspectRatioStrategy.RATIO_16_9_FALLBACK_AUTO_STRATEGY : AspectRatioStrategy.RATIO_4_3_FALLBACK_AUTO_STRATEGY).build());
        if (!ExteraConfig.getCameraStabilization()) {
            resolutionSelector.setPreviewStabilizationEnabled(false);
        } else {
            try {
                if (this.isDualMode && cameraSelector != 0 && (processCameraProvider = this.provider) != null && processCameraProvider.hasCamera(cameraSelector)) {
                    Map<CameraSelector, Boolean> map = stabilizationCache;
                    if (map.probeCoroutineSuspended((Continuation<?>) cameraSelector) != 0) {
                        zEquals = Boolean.TRUE.equals(map.get(cameraSelector));
                    } else {
                        boolean zIsStabilizationSupported = Preview.getPreviewCapabilities(this.provider.getCameraInfo(cameraSelector)).isStabilizationSupported();
                        map.put(cameraSelector, Boolean.valueOf(zIsStabilizationSupported));
                        zEquals = zIsStabilizationSupported;
                    }
                    if (zEquals) {
                        resolutionSelector.setPreviewStabilizationEnabled(true);
                    }
                }
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
        }
        if (!ExteraConfig.getCameraMirrorMode()) {
            resolutionSelector.setMirrorMode(0);
        }
        applyUseCaseFpsRange(cameraSelector, resolutionSelector);
        return resolutionSelector.build();
    }

    private VideoCapture<Recorder> buildVideoCapture(CameraSelector cameraSelector) {
        Quality videoQuality = getVideoQuality(this.provider, cameraSelector);
        VideoCapture.Builder builder = new VideoCapture.Builder(new Recorder.Builder().setQualitySelector(QualitySelector.from(videoQuality, FallbackStrategy.higherQualityOrLowerThan(videoQuality))).build());
        if (!ExteraConfig.getCameraStabilization()) {
            builder.setVideoStabilizationEnabled(false);
        }
        return builder.build();
    }

    private void applyUseCaseFpsRange(CameraSelector cameraSelector, Preview.Builder builder) {
        if (this.isDualMode && ExteraConfig.getExtendedFramesPerSecond() && cameraSelector != null) {
            try {
                ProcessCameraProvider processCameraProvider = this.provider;
                if (processCameraProvider == null || !processCameraProvider.hasCamera(cameraSelector) || !isFpsRangeSupported(cameraSelector) || builder == null) {
                    return;
                }
                builder.setTargetFrameRate(EXTENDED_FPS_RANGE);
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
        }
    }

    private boolean isFpsRangeSupported(CameraSelector cameraSelector) {
        return isRangeSupported(getFpsRanges(cameraSelector), EXTENDED_FPS_RANGE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [void] */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    private Range<Integer>[] getFpsRanges(CameraSelector cameraSelector) {
        Map<CameraSelector, Range<Integer>[]> map = fpsRangesCache;
        if (map.probeCoroutineSuspended((Continuation<?>) cameraSelector) != 0) {
            return map.get(cameraSelector);
        }
        try {
            Range<Integer>[] rangeArr = (Range[]) Camera2CameraInfo.from(this.provider.getCameraInfo(cameraSelector)).getCameraCharacteristic(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
            map.put(cameraSelector, rangeArr);
            return rangeArr;
        } catch (Exception e) {
            FileLog.m1048e(e);
            return null;
        }
    }

    private boolean isRangeSupported(Range<Integer>[] rangeArr, Range<Integer> range) {
        if (rangeArr == null) {
            return false;
        }
        for (Range<Integer> range2 : rangeArr) {
            if (range2.equals(range)) {
                return true;
            }
        }
        return false;
    }

    private List<UseCase> getSingleUseCases() {
        ArrayList arrayList = new ArrayList();
        Preview preview = this.previewUseCase;
        if (preview != null) {
            arrayList.add(preview);
        }
        VideoCapture<Recorder> videoCapture = this.videoCapture;
        if (videoCapture != null) {
            arrayList.add(videoCapture);
        }
        return arrayList;
    }

    private SessionConfig buildSessionConfig(boolean z) {
        SessionConfig.Builder builder = new SessionConfig.Builder(getSingleUseCases());
        if (z) {
            List<GroupableFeature> preferredFeatures = getPreferredFeatures();
            if (!preferredFeatures.isEmpty()) {
                builder.setPreferredFeatureGroup((GroupableFeature[]) preferredFeatures.toArray(new GroupableFeature[0]));
            }
        }
        if (ExteraConfig.getExtendedFramesPerSecond() && isSessionFrameRateSupported(builder.build())) {
            builder.setFrameRateRange(EXTENDED_FPS_RANGE);
        }
        return builder.build();
    }

    private boolean isSessionFrameRateSupported(SessionConfig sessionConfig) {
        ProcessCameraProvider processCameraProvider;
        try {
            CameraSelector cameraSelector = this.cameraSelector;
            if (cameraSelector != null && (processCameraProvider = this.provider) != null && processCameraProvider.hasCamera(cameraSelector)) {
                return this.provider.getCameraInfo(this.cameraSelector).getSupportedFrameRateRanges(sessionConfig).contains(EXTENDED_FPS_RANGE);
            }
            return false;
        } catch (Exception e) {
            FileLog.m1048e(e);
            return false;
        }
    }

    private List<GroupableFeature> getPreferredFeatures() {
        ArrayList arrayList = new ArrayList();
        if (ExteraConfig.getCameraStabilization()) {
            arrayList.add(GroupableFeature.PREVIEW_STABILIZATION);
            if (this.videoCapture != null) {
                arrayList.add(GroupableFeatures.VIDEO_STABILIZATION);
            }
        }
        return arrayList;
    }

    private boolean isSessionConfigSupported(SessionConfig sessionConfig) {
        ProcessCameraProvider processCameraProvider;
        try {
            CameraSelector cameraSelector = this.cameraSelector;
            if (cameraSelector != null && (processCameraProvider = this.provider) != null && processCameraProvider.hasCamera(cameraSelector)) {
                if (this.provider.getCameraInfo(this.cameraSelector).isSessionConfigSupported(sessionConfig)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            FileLog.m1048e(e);
            return false;
        }
    }

    private Camera bindSingleSessionConfig(boolean z) {
        SessionConfig sessionConfigBuildSessionConfig = buildSessionConfig(z);
        if (isSessionConfigSupported(sessionConfigBuildSessionConfig)) {
            return this.provider.bindToLifecycle(this.lifecycle, this.cameraSelector, sessionConfigBuildSessionConfig);
        }
        return null;
    }

    public void bindUseCases() {
        if (this.provider == null || this.lifecycle.getLifecycle().getState() == Lifecycle.State.DESTROYED || this.isBinding) {
            return;
        }
        this.isBinding = true;
        this.camera = null;
        try {
            this.provider.unbindAll();
            if (this.isDualMode) {
                bindDualUseCases();
            } else {
                bindSingleUseCases();
            }
            this.isBinding = false;
        } catch (Throwable th) {
            this.isBinding = false;
            throw th;
        }
    }

    private void bindSingleUseCases() {
        try {
            CameraSelector cameraSelector = this.cameraSelector;
            if (cameraSelector != null && this.provider.hasCamera(cameraSelector)) {
                if (this.videoCapture != null) {
                    try {
                        Camera cameraBindSingleSessionConfig = bindSingleSessionConfig(true);
                        this.camera = cameraBindSingleSessionConfig;
                        if (cameraBindSingleSessionConfig == null) {
                            this.camera = bindSingleSessionConfig(false);
                        }
                        if (this.camera == null) {
                            this.camera = this.provider.bindToLifecycle(this.lifecycle, this.cameraSelector, this.previewUseCase, this.videoCapture);
                        }
                    } catch (Exception e) {
                        FileLog.m1048e(e);
                        this.camera = this.provider.bindToLifecycle(this.lifecycle, this.cameraSelector, this.previewUseCase);
                    }
                } else {
                    this.camera = this.provider.bindToLifecycle(this.lifecycle, this.cameraSelector, this.previewUseCase);
                }
                CameraControl cameraControl = this.camera.getCameraControl();
                this.cameraControl = cameraControl;
                applyInitialZoom(this.camera, cameraControl);
                updateTorchState();
                return;
            }
            this.isInitiated = false;
        } catch (Exception e2) {
            FileLog.m1048e(e2);
            this.isInitiated = false;
        }
    }

    private void bindDualUseCases() {
        if (SharedConfig.getDevicePerformanceClass() == 0) {
            bindDualUseCasesFallback();
            return;
        }
        try {
            ConcurrentCamera.SingleCameraConfig singleCameraConfig = new ConcurrentCamera.SingleCameraConfig(CameraSelector.DEFAULT_FRONT_CAMERA, new UseCaseGroup.Builder().addUseCase(this.previewUseCase).build(), this.lifecycle);
            ConcurrentCamera.SingleCameraConfig singleCameraConfig2 = new ConcurrentCamera.SingleCameraConfig(CameraSelector.DEFAULT_BACK_CAMERA, new UseCaseGroup.Builder().addUseCase(this.previewUseCaseBack).build(), this.lifecycle);
            ArrayList arrayList = new ArrayList();
            arrayList.add(singleCameraConfig);
            arrayList.add(singleCameraConfig2);
            for (Camera camera : this.provider.bindToLifecycle(arrayList).getCameras()) {
                if (camera.getCameraInfo().getLensFacing() == 0) {
                    this.cameraFront = camera;
                    CameraControl cameraControl = camera.getCameraControl();
                    this.cameraControlFront = cameraControl;
                    applyInitialZoom(this.cameraFront, cameraControl);
                } else {
                    this.cameraBack = camera;
                    CameraControl cameraControl2 = camera.getCameraControl();
                    this.cameraControlBack = cameraControl2;
                    applyInitialZoom(this.cameraBack, cameraControl2);
                }
            }
            updateActiveControl(this.isFrontface);
            updateTorchState();
        } catch (Exception e) {
            FileLog.m1048e(e);
            bindDualUseCasesFallback();
        }
    }

    private void bindDualUseCasesFallback() {
        try {
            ConcurrentCamera.SingleCameraConfig singleCameraConfig = new ConcurrentCamera.SingleCameraConfig(CameraSelector.DEFAULT_FRONT_CAMERA, new UseCaseGroup.Builder().addUseCase(this.previewUseCase).build(), this.lifecycle);
            ConcurrentCamera.SingleCameraConfig singleCameraConfig2 = new ConcurrentCamera.SingleCameraConfig(CameraSelector.DEFAULT_BACK_CAMERA, new UseCaseGroup.Builder().addUseCase(this.previewUseCaseBack).build(), this.lifecycle);
            ArrayList arrayList = new ArrayList();
            arrayList.add(singleCameraConfig);
            arrayList.add(singleCameraConfig2);
            for (Camera camera : this.provider.bindToLifecycle(arrayList).getCameras()) {
                if (camera.getCameraInfo().getLensFacing() == 0) {
                    this.cameraFront = camera;
                    CameraControl cameraControl = camera.getCameraControl();
                    this.cameraControlFront = cameraControl;
                    applyInitialZoom(this.cameraFront, cameraControl);
                } else {
                    this.cameraBack = camera;
                    CameraControl cameraControl2 = camera.getCameraControl();
                    this.cameraControlBack = cameraControl2;
                    applyInitialZoom(this.cameraBack, cameraControl2);
                }
            }
            updateActiveControl(this.isFrontface);
            updateTorchState();
        } catch (Exception e) {
            FileLog.m1048e(e);
            this.isDualMode = false;
            prepareSingleUseCases();
            bindSingleUseCases();
        }
    }

    private void applyInitialZoom(Camera camera, CameraControl cameraControl) {
        if (cameraControl == null) {
            return;
        }
        cameraControl.setZoomRatio(getDefaultZoomRatio(camera));
    }

    public float getDefaultZoomRatio() {
        return getDefaultZoomRatio(this.camera);
    }

    private float getDefaultZoomRatio(Camera camera) {
        ZoomState value;
        if (!ExteraConfig.getStartWithWideAngleCamera() || camera == null || camera.getCameraInfo().getLensFacing() != 1 || (value = camera.getCameraInfo().getZoomState().getValue()) == null || value.getMinZoomRatio() >= 1.0f) {
            return 1.0f;
        }
        return value.getMinZoomRatio();
    }

    public float getLinearZoom() {
        ZoomState value;
        Camera camera = this.camera;
        if (camera == null || (value = camera.getCameraInfo().getZoomState().getValue()) == null) {
            return 0.0f;
        }
        return value.getLinearZoom();
    }

    public void setZoom(float f) {
        Camera camera;
        ZoomState value;
        if (this.cameraControl == null || (camera = this.camera) == null || (value = camera.getCameraInfo().getZoomState().getValue()) == null) {
            return;
        }
        this.cameraControl.setZoomRatio(Utilities.clamp(value.getZoomRatio() * f, value.getMaxZoomRatio(), value.getMinZoomRatio()));
    }

    public float getZoomRatio() {
        ZoomState value;
        Camera camera = this.camera;
        if (camera == null || (value = camera.getCameraInfo().getZoomState().getValue()) == null) {
            return 1.0f;
        }
        return value.getZoomRatio();
    }

    public void setZoomRatio(float f) {
        CameraControl cameraControl = this.cameraControl;
        if (cameraControl == null) {
            return;
        }
        cameraControl.setZoomRatio(f);
    }

    public float getMinZoomRatio() {
        ZoomState value;
        Camera camera = this.camera;
        if (camera == null || (value = camera.getCameraInfo().getZoomState().getValue()) == null) {
            return 1.0f;
        }
        return value.getMinZoomRatio();
    }

    public float getMaxZoomRatio() {
        ZoomState value;
        Camera camera = this.camera;
        if (camera == null || (value = camera.getCameraInfo().getZoomState().getValue()) == null) {
            return 1.0f;
        }
        return value.getMaxZoomRatio();
    }

    public void focusToPoint(float f, float f2) {
        if (this.cameraControl == null) {
            return;
        }
        this.cameraControl.startFocusAndMetering(new FocusMeteringAction.Builder(this.meteringPointFactory.createPoint(f, f2), 7).build());
    }

    public int getDisplayOrientation() {
        int rotation = ((WindowManager) ApplicationLoader.applicationContext.getSystemService("window")).getDefaultDisplay().getRotation();
        if (rotation == 0) {
            return 0;
        }
        if (rotation == 1) {
            return 90;
        }
        if (rotation != 2) {
            return rotation != 3 ? 0 : 270;
        }
        return 180;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [void] */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public static Map<Quality, Size> getAvailableVideoSizes(CameraSelector cameraSelector, ProcessCameraProvider processCameraProvider) {
        Map<CameraSelector, Map<Quality, Size>> map = videoSizesCache;
        if (map.probeCoroutineSuspended((Continuation<?>) cameraSelector) != 0) {
            return map.get(cameraSelector);
        }
        Map<Quality, Size> map2 = (Map) cameraSelector.filter(processCameraProvider.getAvailableCameraInfos()).stream().findFirst().map(new Function() { // from class: com.exteragram.messenger.camera.CameraXSession$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CameraXSession.m2201$r8$lambda$WFM3GZVFUtiOPETySK3lZGOOCA((CameraInfo) obj);
            }
        }).orElse(new LinkedHashMap());
        map.put(cameraSelector, map2);
        return map2;
    }

    /* JADX INFO: renamed from: $r8$lambda$WFM3GZVFUtiOPET-ySK3lZGOOCA, reason: not valid java name */
    public static /* synthetic */ LinkedHashMap m2201$r8$lambda$WFM3GZVFUtiOPETySK3lZGOOCA(final CameraInfo cameraInfo) {
        return (LinkedHashMap) Recorder.getVideoCapabilities(cameraInfo).getSupportedQualities(DynamicRange.UNSPECIFIED).stream().collect(Collectors.toMap(Function.identity(), new Function() { // from class: com.exteragram.messenger.camera.CameraXSession$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CameraXSession.m2200$r8$lambda$01A6AphNT0EZ3ty8WLlv6PKG4(cameraInfo, (Quality) obj);
            }
        }, new BinaryOperator() { // from class: com.exteragram.messenger.camera.CameraXSession$$ExternalSyntheticLambda9
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return CameraXSession.m2203$r8$lambda$tZn9Vw7ajOvMRMMd42QgG7wMV8((Size) obj, (Size) obj2);
            }
        }, new Supplier() { // from class: com.exteragram.messenger.camera.CameraXSession$$ExternalSyntheticLambda10
            @Override // java.util.function.Supplier
            public final Object get() {
                return new LinkedHashMap();
            }
        }));
    }

    /* JADX INFO: renamed from: $r8$lambda$01A6A-phNT0-EZ3ty8WLlv6PKG4, reason: not valid java name */
    public static /* synthetic */ Size m2200$r8$lambda$01A6AphNT0EZ3ty8WLlv6PKG4(CameraInfo cameraInfo, Quality quality) {
        return (Size) Optional.ofNullable(QualitySelector.getResolution(cameraInfo, quality)).orElse(new Size(0, 0));
    }

    /* JADX INFO: renamed from: $r8$lambda$tZn9Vw-7ajOvMRMMd42QgG7wMV8, reason: not valid java name */
    public static /* synthetic */ Size m2203$r8$lambda$tZn9Vw7ajOvMRMMd42QgG7wMV8(Size size, Size size2) {
        throw new IllegalStateException(String.format("Duplicate key %s", size));
    }

    public static Quality getVideoQuality(ProcessCameraProvider processCameraProvider, CameraSelector cameraSelector) {
        Map<Quality, Size> availableVideoSizes = getAvailableVideoSizes(cameraSelector, processCameraProvider);
        if (availableVideoSizes.isEmpty()) {
            return Quality.f31SD;
        }
        final int suggestedResolution = getSuggestedResolution();
        final int iOrElse = availableVideoSizes.values().stream().mapToInt(new ToIntFunction() { // from class: com.exteragram.messenger.camera.CameraXSession$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((Size) obj).getHeight();
            }
        }).filter(new IntPredicate() { // from class: com.exteragram.messenger.camera.CameraXSession$$ExternalSyntheticLambda1
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return CameraXSession.$r8$lambda$vGDHxvnfSRLnK1Y72oTpmRqaYLU(suggestedResolution, i);
            }
        }).max().orElse(availableVideoSizes.values().stream().mapToInt(new ToIntFunction() { // from class: com.exteragram.messenger.camera.CameraXSession$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((Size) obj).getHeight();
            }
        }).filter(new IntPredicate() { // from class: com.exteragram.messenger.camera.CameraXSession$$ExternalSyntheticLambda2
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return CameraXSession.$r8$lambda$T4tWowZgGUKCS8bI_KCHpQKUyOM(i);
            }
        }).min().orElse(0));
        if (iOrElse == 0) {
            return Quality.LOWEST;
        }
        return (Quality) availableVideoSizes.entrySet().stream().filter(new Predicate() { // from class: com.exteragram.messenger.camera.CameraXSession$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return CameraXSession.$r8$lambda$ObvWR0kql5zK_bEzJdqCswEhh3Y(iOrElse, (Map.Entry) obj);
            }
        }).map(new Function() { // from class: com.exteragram.messenger.camera.CameraXSession$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (Quality) ((Map.Entry) obj).getKey();
            }
        }).findFirst().orElse(Quality.HIGHEST);
    }

    public static /* synthetic */ boolean $r8$lambda$vGDHxvnfSRLnK1Y72oTpmRqaYLU(int i, int i2) {
        return i2 > 0 && i2 <= i;
    }

    public static /* synthetic */ boolean $r8$lambda$T4tWowZgGUKCS8bI_KCHpQKUyOM(int i) {
        return i > 0;
    }

    public static /* synthetic */ boolean $r8$lambda$ObvWR0kql5zK_bEzJdqCswEhh3Y(int i, Map.Entry entry) {
        return ((Size) entry.getValue()).getHeight() == i;
    }

    private static int getSuggestedResolution() {
        int devicePerformanceClass = SharedConfig.getDevicePerformanceClass();
        if (devicePerformanceClass == 0) {
            return 720;
        }
        if (devicePerformanceClass != 1) {
            return devicePerformanceClass != 2 ? 720 : 2160;
        }
        return 1080;
    }

    public static boolean isSeamlessSwitchingAvailable(Context context) {
        Boolean bool = isSeamlessSwitchingAvailableDefaultCache;
        if (bool != null) {
            return bool.booleanValue();
        }
        Boolean boolValueOf = Boolean.valueOf(SharedConfig.getDevicePerformanceClass() >= 1 && android.hardware.Camera.getNumberOfCameras() > 1 && SharedConfig.allowPreparingHevcPlayers() && hasConcurrentFrontBackPair(context));
        isSeamlessSwitchingAvailableDefaultCache = boolValueOf;
        return boolValueOf.booleanValue();
    }

    private static boolean hasConcurrentFrontBackPair(Context context) {
        PackageManager packageManager;
        CameraManager cameraManager;
        if (context == null || Build.VERSION.SDK_INT < 30 || (packageManager = context.getPackageManager()) == null || !packageManager.hasSystemFeature("android.hardware.camera.concurrent") || (cameraManager = (CameraManager) context.getSystemService(CameraManager.class)) == null) {
            return false;
        }
        try {
            Iterator<Set<String>> it = cameraManager.getConcurrentCameraIds().iterator();
            while (it.hasNext()) {
                Iterator<String> it2 = it.next().iterator();
                boolean z = false;
                boolean z2 = false;
                while (it2.hasNext()) {
                    Integer num = (Integer) cameraManager.getCameraCharacteristics(it2.next()).get(CameraCharacteristics.LENS_FACING);
                    if (num != null) {
                        if (num.intValue() == 0) {
                            z = true;
                        } else if (num.intValue() == 1) {
                            z2 = true;
                        }
                    }
                }
                if (z && z2) {
                    return true;
                }
            }
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
        return false;
    }
}
