package androidx.camera.lifecycle;

import android.content.Context;
import androidx.arch.core.util.Function;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraFilter;
import androidx.camera.core.CameraIdentifier;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraPresenceListener;
import androidx.camera.core.CameraProvider;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraX;
import androidx.camera.core.CameraXConfig;
import androidx.camera.core.CompositionSettings;
import androidx.camera.core.LegacySessionConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.SessionConfig;
import androidx.camera.core.UseCase;
import androidx.camera.core.impl.AdapterCameraInfo;
import androidx.camera.core.impl.CameraConfig;
import androidx.camera.core.impl.CameraConfigProvider;
import androidx.camera.core.impl.CameraConfigs;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.CameraPresenceProvider;
import androidx.camera.core.impl.ExtendedCameraConfigProviderStore;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.impl.utils.ContextUtil;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.AsyncFunction;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.FutureChain;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.core.internal.CameraUseCaseAdapter;
import androidx.camera.lifecycle.LifecycleCameraRepository;
import androidx.core.util.Preconditions;
import androidx.lifecycle.LifecycleOwner;
import androidx.tracing.Trace;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import p022j$.util.Objects;

/* JADX INFO: loaded from: classes4.dex */
public final class LifecycleCameraProviderImpl implements CameraProvider, CameraPresenceListener {
    private final Map cameraInfoMap;
    private CameraX cameraX;
    private CameraXConfig.Provider cameraXConfigProvider;
    private ListenableFuture cameraXInitializeFuture;
    private ListenableFuture cameraXShutdownFuture;
    private Context context;
    private final HashSet lifecycleCameraKeys;
    private LifecycleCameraRepository lifecycleCameraRepository;
    private final Object lock = new Object();

    @Override // androidx.camera.core.CameraPresenceListener
    public void onCamerasAdded(Set addedCameraIds) {
        Intrinsics.checkNotNullParameter(addedCameraIds, "addedCameraIds");
    }

    public LifecycleCameraProviderImpl() {
        ListenableFuture listenableFutureImmediateFuture = Futures.immediateFuture(null);
        Intrinsics.checkNotNullExpressionValue(listenableFutureImmediateFuture, "immediateFuture(...)");
        this.cameraXShutdownFuture = listenableFutureImmediateFuture;
        this.cameraInfoMap = new HashMap();
        this.lifecycleCameraKeys = new HashSet();
    }

    public final CameraXConfig.Provider getCameraXConfigProvider$camera_lifecycle() {
        return this.cameraXConfigProvider;
    }

    public final void setCameraXConfigProvider$camera_lifecycle(CameraXConfig.Provider provider) {
        this.cameraXConfigProvider = provider;
    }

    private final boolean isInitialized() {
        return this.cameraX != null;
    }

    public final Context getContext$camera_lifecycle() {
        return this.context;
    }

    public final ListenableFuture initAsync$camera_lifecycle(final Context context, CameraXConfig cameraXConfig) {
        Intrinsics.checkNotNullParameter(context, "context");
        synchronized (this.lock) {
            this.lifecycleCameraRepository = LifecycleCameraRepositories.getInstance$camera_lifecycle(ContextUtil.getDeviceId(context));
            ListenableFuture listenableFuture = this.cameraXInitializeFuture;
            if (listenableFuture != null) {
                Intrinsics.checkNotNull(listenableFuture, "null cannot be cast to non-null type com.google.common.util.concurrent.ListenableFuture<java.lang.Void>");
                return listenableFuture;
            }
            if (cameraXConfig != null) {
                configure$camera_lifecycle(cameraXConfig);
            }
            final CameraX cameraX = new CameraX(context, this.cameraXConfigProvider);
            FutureChain futureChainFrom = FutureChain.from(this.cameraXShutdownFuture);
            final Function1 function1 = new Function1() { // from class: androidx.camera.lifecycle.LifecycleCameraProviderImpl$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return LifecycleCameraProviderImpl.initAsync$lambda$0$1(cameraX, (Void) obj);
                }
            };
            FutureChain futureChainTransformAsync = futureChainFrom.transformAsync(new AsyncFunction() { // from class: androidx.camera.lifecycle.LifecycleCameraProviderImpl$$ExternalSyntheticLambda2
                @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
                public final ListenableFuture apply(Object obj) {
                    return LifecycleCameraProviderImpl.initAsync$lambda$0$2(function1, obj);
                }
            }, CameraXExecutors.directExecutor());
            final Function1 function12 = new Function1() { // from class: androidx.camera.lifecycle.LifecycleCameraProviderImpl$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return LifecycleCameraProviderImpl.initAsync$lambda$0$3(this.f$0, cameraX, context, (Void) obj);
                }
            };
            FutureChain futureChainTransform = futureChainTransformAsync.transform(new Function() { // from class: androidx.camera.lifecycle.LifecycleCameraProviderImpl$$ExternalSyntheticLambda4
                @Override // androidx.arch.core.util.Function
                public final Object apply(Object obj) {
                    return LifecycleCameraProviderImpl.initAsync$lambda$0$4(function12, obj);
                }
            }, CameraXExecutors.directExecutor());
            Intrinsics.checkNotNullExpressionValue(futureChainTransform, "transform(...)");
            this.cameraXInitializeFuture = futureChainTransform;
            Futures.addCallback(futureChainTransform, new FutureCallback() { // from class: androidx.camera.lifecycle.LifecycleCameraProviderImpl$initAsync$1$2
                @Override // androidx.camera.core.impl.utils.futures.FutureCallback
                public void onSuccess(Void r1) {
                }

                @Override // androidx.camera.core.impl.utils.futures.FutureCallback
                public void onFailure(Throwable t) {
                    Intrinsics.checkNotNullParameter(t, "t");
                    this.this$0.shutdownAsync$camera_lifecycle(false);
                }
            }, CameraXExecutors.directExecutor());
            ListenableFuture listenableFutureNonCancellationPropagating = Futures.nonCancellationPropagating(futureChainTransform);
            Intrinsics.checkNotNullExpressionValue(listenableFutureNonCancellationPropagating, "nonCancellationPropagating(...)");
            return listenableFutureNonCancellationPropagating;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ListenableFuture initAsync$lambda$0$1(CameraX cameraX, Void r1) {
        return cameraX.getInitializeFuture();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ListenableFuture initAsync$lambda$0$2(Function1 function1, Object obj) {
        return (ListenableFuture) function1.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Void initAsync$lambda$0$4(Function1 function1, Object obj) {
        return (Void) function1.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Void initAsync$lambda$0$3(LifecycleCameraProviderImpl lifecycleCameraProviderImpl, CameraX cameraX, Context context, Void r3) {
        lifecycleCameraProviderImpl.initInternal(cameraX, ContextUtil.getPersistentApplicationContext(context));
        return r3;
    }

    private final void shutdownInternal() {
        initInternal(null, null);
    }

    private final void initInternal(CameraX cameraX, Context context) {
        CameraPresenceProvider cameraAvailabilityProvider;
        synchronized (this.lock) {
            this.cameraX = cameraX;
            this.context = context;
            if (cameraX != null && (cameraAvailabilityProvider = cameraX.getCameraAvailabilityProvider()) != null) {
                ScheduledExecutorService scheduledExecutorServiceMainThreadExecutor = CameraXExecutors.mainThreadExecutor();
                Intrinsics.checkNotNullExpressionValue(scheduledExecutorServiceMainThreadExecutor, "mainThreadExecutor(...)");
                cameraAvailabilityProvider.addCameraPresenceListener(this, scheduledExecutorServiceMainThreadExecutor);
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    public final void configure$camera_lifecycle(final CameraXConfig cameraXConfig) {
        Intrinsics.checkNotNullParameter(cameraXConfig, "cameraXConfig");
        Trace.beginSection("CX:configureInstanceInternal");
        try {
            synchronized (this.lock) {
                Preconditions.checkNotNull(cameraXConfig);
                Preconditions.checkState(getCameraXConfigProvider$camera_lifecycle() == null, "CameraX has already been configured. To use a different configuration, shutdown() must be called.");
                setCameraXConfigProvider$camera_lifecycle(new CameraXConfig.Provider() { // from class: androidx.camera.lifecycle.LifecycleCameraProviderImpl$configure$1$1$1
                    @Override // androidx.camera.core.CameraXConfig.Provider
                    public final CameraXConfig getCameraXConfig() {
                        return cameraXConfig;
                    }
                });
                Unit unit = Unit.INSTANCE;
            }
        } finally {
            Trace.endSection();
        }
    }

    public final ListenableFuture shutdownAsync$camera_lifecycle(boolean z) {
        ListenableFuture listenableFutureImmediateFuture;
        Threads.runOnMainSync(new Runnable() { // from class: androidx.camera.lifecycle.LifecycleCameraProviderImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                LifecycleCameraProviderImpl.shutdownAsync$lambda$0(this.f$0);
            }
        });
        if (isInitialized()) {
            CameraX cameraX = this.cameraX;
            Intrinsics.checkNotNull(cameraX);
            cameraX.getCameraAvailabilityProvider().removeCameraPresenceListener(this);
            CameraX cameraX2 = this.cameraX;
            Intrinsics.checkNotNull(cameraX2);
            listenableFutureImmediateFuture = cameraX2.shutdown();
        } else {
            listenableFutureImmediateFuture = Futures.immediateFuture(null);
        }
        Intrinsics.checkNotNull(listenableFutureImmediateFuture);
        synchronized (this.lock) {
            if (z) {
                try {
                    this.cameraXConfigProvider = null;
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.cameraXInitializeFuture = null;
            this.cameraXShutdownFuture = listenableFutureImmediateFuture;
            this.cameraInfoMap.clear();
            this.lifecycleCameraKeys.clear();
            Unit unit = Unit.INSTANCE;
        }
        shutdownInternal();
        return listenableFutureImmediateFuture;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void shutdownAsync$lambda$0(LifecycleCameraProviderImpl lifecycleCameraProviderImpl) {
        if (lifecycleCameraProviderImpl.isInitialized()) {
            lifecycleCameraProviderImpl.unbindAll();
            LifecycleCameraRepository lifecycleCameraRepository = lifecycleCameraProviderImpl.lifecycleCameraRepository;
            Intrinsics.checkNotNull(lifecycleCameraRepository);
            lifecycleCameraRepository.removeLifecycleCameras(lifecycleCameraProviderImpl.lifecycleCameraKeys);
        }
    }

    public void unbindAll() {
        Trace.beginSection("CX:unbindAll");
        try {
            Threads.checkMainThread();
            setCameraOperatingMode(0);
            LifecycleCameraRepository lifecycleCameraRepository = this.lifecycleCameraRepository;
            Intrinsics.checkNotNull(lifecycleCameraRepository);
            lifecycleCameraRepository.unbindAll(this.lifecycleCameraKeys);
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.endSection();
        }
    }

    public boolean hasCamera(CameraSelector cameraSelector) {
        boolean z;
        Intrinsics.checkNotNullParameter(cameraSelector, "cameraSelector");
        Trace.beginSection("CX:hasCamera");
        try {
            CameraX cameraX = this.cameraX;
            Intrinsics.checkNotNull(cameraX);
            cameraSelector.select(cameraX.getCameraRepository().getCameras());
            z = true;
        } catch (IllegalArgumentException unused) {
            z = false;
        } catch (Throwable th) {
            Trace.endSection();
            throw th;
        }
        Trace.endSection();
        return z;
    }

    public Camera bindToLifecycle(LifecycleOwner lifecycleOwner, CameraSelector cameraSelector, UseCase... useCases) {
        Intrinsics.checkNotNullParameter(lifecycleOwner, "lifecycleOwner");
        Intrinsics.checkNotNullParameter(cameraSelector, "cameraSelector");
        Intrinsics.checkNotNullParameter(useCases, "useCases");
        Trace.beginSection("CX:bindToLifecycle");
        try {
            if (getCameraOperatingMode() != 2) {
                setCameraOperatingMode(1);
                Camera cameraBindToLifecycleInternal$default = bindToLifecycleInternal$default(this, lifecycleOwner, cameraSelector, null, null, null, new LegacySessionConfig(ArraysKt.filterNotNull(useCases), null, null, 6, null), 28, null);
                Trace.endSection();
                return cameraBindToLifecycleInternal$default;
            }
            throw new UnsupportedOperationException("bindToLifecycle for single camera is not supported in concurrent camera mode, call unbindAll() first");
        } catch (Throwable th) {
            Trace.endSection();
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x01e3 A[Catch: all -> 0x00f5, TryCatch #1 {all -> 0x00f5, blocks: (B:3:0x000e, B:5:0x0015, B:7:0x001b, B:10:0x0050, B:12:0x0056, B:14:0x0064, B:16:0x0079, B:18:0x008f, B:19:0x00bb, B:21:0x00c1, B:22:0x00d6, B:24:0x00dc, B:26:0x00f1, B:29:0x00f8, B:30:0x010b, B:67:0x026a, B:31:0x0125, B:32:0x012c, B:33:0x012d, B:34:0x0132, B:35:0x0133, B:37:0x0146, B:39:0x014c, B:40:0x0151, B:41:0x0167, B:43:0x0179, B:46:0x0184, B:47:0x018b, B:48:0x018c, B:51:0x01a7, B:53:0x01b5, B:55:0x01da, B:61:0x01f2, B:66:0x0267, B:57:0x01e3, B:59:0x01e9, B:62:0x022b, B:63:0x022f, B:65:0x0235, B:70:0x0273, B:71:0x027a, B:72:0x027b, B:73:0x0280, B:74:0x0281, B:75:0x0288, B:76:0x0289, B:77:0x0290, B:78:0x0291, B:79:0x0298), top: B:84:0x000e, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0235 A[Catch: all -> 0x00f5, LOOP:2: B:63:0x022f->B:65:0x0235, LOOP_END, TryCatch #1 {all -> 0x00f5, blocks: (B:3:0x000e, B:5:0x0015, B:7:0x001b, B:10:0x0050, B:12:0x0056, B:14:0x0064, B:16:0x0079, B:18:0x008f, B:19:0x00bb, B:21:0x00c1, B:22:0x00d6, B:24:0x00dc, B:26:0x00f1, B:29:0x00f8, B:30:0x010b, B:67:0x026a, B:31:0x0125, B:32:0x012c, B:33:0x012d, B:34:0x0132, B:35:0x0133, B:37:0x0146, B:39:0x014c, B:40:0x0151, B:41:0x0167, B:43:0x0179, B:46:0x0184, B:47:0x018b, B:48:0x018c, B:51:0x01a7, B:53:0x01b5, B:55:0x01da, B:61:0x01f2, B:66:0x0267, B:57:0x01e3, B:59:0x01e9, B:62:0x022b, B:63:0x022f, B:65:0x0235, B:70:0x0273, B:71:0x027a, B:72:0x027b, B:73:0x0280, B:74:0x0281, B:75:0x0288, B:76:0x0289, B:77:0x0290, B:78:0x0291, B:79:0x0298), top: B:84:0x000e, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public androidx.camera.core.ConcurrentCamera bindToLifecycle(java.util.List r16) {
        /*
            Method dump skipped, instruction units count: 669
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.lifecycle.LifecycleCameraProviderImpl.bindToLifecycle(java.util.List):androidx.camera.core.ConcurrentCamera");
    }

    public List getAvailableCameraInfos() {
        Trace.beginSection("CX:getAvailableCameraInfos");
        try {
            ArrayList arrayList = new ArrayList();
            CameraX cameraX = this.cameraX;
            Intrinsics.checkNotNull(cameraX);
            LinkedHashSet cameras = cameraX.getCameraRepository().getCameras();
            Intrinsics.checkNotNullExpressionValue(cameras, "getCameras(...)");
            Iterator it = cameras.iterator();
            while (it.hasNext()) {
                CameraInfo cameraInfo = ((CameraInternal) it.next()).getCameraInfo();
                Intrinsics.checkNotNullExpressionValue(cameraInfo, "getCameraInfo(...)");
                arrayList.add(cameraInfo);
            }
            return arrayList;
        } finally {
            Trace.endSection();
        }
    }

    public List getAvailableConcurrentCameraInfos() {
        Trace.beginSection("CX:getAvailableConcurrentCameraInfos");
        try {
            Objects.requireNonNull(this.cameraX);
            CameraX cameraX = this.cameraX;
            Intrinsics.checkNotNull(cameraX);
            Objects.requireNonNull(cameraX.getCameraFactory().getCameraCoordinator());
            CameraX cameraX2 = this.cameraX;
            Intrinsics.checkNotNull(cameraX2);
            List<List> concurrentCameraSelectors = cameraX2.getCameraFactory().getCameraCoordinator().getConcurrentCameraSelectors();
            Intrinsics.checkNotNullExpressionValue(concurrentCameraSelectors, "getConcurrentCameraSelectors(...)");
            ArrayList arrayList = new ArrayList();
            for (List<CameraSelector> list : concurrentCameraSelectors) {
                ArrayList arrayList2 = new ArrayList();
                for (CameraSelector cameraSelector : list) {
                    try {
                        Intrinsics.checkNotNull(cameraSelector);
                        arrayList2.add(getCameraInfo(cameraSelector));
                    } catch (IllegalArgumentException unused) {
                    }
                }
                arrayList.add(arrayList2);
            }
            Trace.endSection();
            return arrayList;
        } catch (Throwable th) {
            Trace.endSection();
            throw th;
        }
    }

    static /* synthetic */ Camera bindToLifecycleInternal$default(LifecycleCameraProviderImpl lifecycleCameraProviderImpl, LifecycleOwner lifecycleOwner, CameraSelector cameraSelector, CameraSelector cameraSelector2, CompositionSettings DEFAULT, CompositionSettings DEFAULT2, SessionConfig sessionConfig, int i, Object obj) {
        if ((i & 4) != 0) {
            cameraSelector2 = null;
        }
        CameraSelector cameraSelector3 = cameraSelector2;
        if ((i & 8) != 0) {
            DEFAULT = CompositionSettings.DEFAULT;
            Intrinsics.checkNotNullExpressionValue(DEFAULT, "DEFAULT");
        }
        CompositionSettings compositionSettings = DEFAULT;
        if ((i & 16) != 0) {
            DEFAULT2 = CompositionSettings.DEFAULT;
            Intrinsics.checkNotNullExpressionValue(DEFAULT2, "DEFAULT");
        }
        return lifecycleCameraProviderImpl.bindToLifecycleInternal(lifecycleOwner, cameraSelector, cameraSelector3, compositionSettings, DEFAULT2, sessionConfig);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Camera bindToLifecycleInternal(LifecycleOwner lifecycleOwner, CameraSelector cameraSelector, CameraSelector cameraSelector2, CompositionSettings compositionSettings, CompositionSettings compositionSettings2, SessionConfig sessionConfig) {
        CameraInternal cameraInternal;
        AdapterCameraInfo adapterCameraInfo;
        Trace.beginSection("CX:bindToLifecycle-internal");
        try {
            Threads.checkMainThread();
            Pair selectorsWithSessionFilter = getSelectorsWithSessionFilter(sessionConfig, cameraSelector, cameraSelector2);
            CameraSelector cameraSelector3 = (CameraSelector) selectorsWithSessionFilter.component1();
            CameraSelector cameraSelector4 = (CameraSelector) selectorsWithSessionFilter.component2();
            CameraX cameraX = this.cameraX;
            Intrinsics.checkNotNull(cameraX);
            CameraInternal cameraInternalSelect = cameraSelector3.select(cameraX.getCameraRepository().getCameras());
            Intrinsics.checkNotNullExpressionValue(cameraInternalSelect, "select(...)");
            cameraInternalSelect.setPrimary(true);
            CameraInfo cameraInfo = getCameraInfo(cameraSelector3);
            Intrinsics.checkNotNull(cameraInfo, "null cannot be cast to non-null type androidx.camera.core.impl.AdapterCameraInfo");
            AdapterCameraInfo adapterCameraInfo2 = (AdapterCameraInfo) cameraInfo;
            char c = 0;
            if (cameraSelector4 != null) {
                CameraX cameraX2 = this.cameraX;
                Intrinsics.checkNotNull(cameraX2);
                CameraInternal cameraInternalSelect2 = cameraSelector4.select(cameraX2.getCameraRepository().getCameras());
                cameraInternalSelect2.setPrimary(false);
                CameraInfo cameraInfo2 = getCameraInfo(cameraSelector4);
                Intrinsics.checkNotNull(cameraInfo2, "null cannot be cast to non-null type androidx.camera.core.impl.AdapterCameraInfo");
                adapterCameraInfo = (AdapterCameraInfo) cameraInfo2;
                cameraInternal = cameraInternalSelect2;
            } else {
                cameraInternal = null;
                adapterCameraInfo = null;
            }
            CameraIdentifier cameraIdentifierFromAdapterInfos = CameraIdentifier.Factory.fromAdapterInfos(adapterCameraInfo2, adapterCameraInfo);
            LifecycleCameraRepository lifecycleCameraRepository = this.lifecycleCameraRepository;
            Intrinsics.checkNotNull(lifecycleCameraRepository);
            LifecycleCamera lifecycleCamera = lifecycleCameraRepository.getLifecycleCamera(lifecycleOwner, cameraIdentifierFromAdapterInfos);
            LifecycleCameraRepository lifecycleCameraRepository2 = this.lifecycleCameraRepository;
            Intrinsics.checkNotNull(lifecycleCameraRepository2);
            Collection lifecycleCameras = lifecycleCameraRepository2.getLifecycleCameras();
            for (UseCase useCase : sessionConfig.getUseCases()) {
                for (Object obj : lifecycleCameras) {
                    char c2 = c;
                    Intrinsics.checkNotNullExpressionValue(obj, "next(...)");
                    LifecycleCamera lifecycleCamera2 = (LifecycleCamera) obj;
                    if (lifecycleCamera2.isBound(useCase) && !Intrinsics.areEqual(lifecycleCamera2.getLifecycleOwner(), lifecycleOwner)) {
                        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                        Object[] objArr = new Object[1];
                        objArr[c2] = useCase;
                        String str = String.format("Use case %s already bound to a different lifecycle.", Arrays.copyOf(objArr, 1));
                        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
                        throw new IllegalStateException(str);
                    }
                    c = c2;
                }
            }
            if (lifecycleCamera == null) {
                LifecycleCameraRepository lifecycleCameraRepository3 = this.lifecycleCameraRepository;
                Intrinsics.checkNotNull(lifecycleCameraRepository3);
                CameraX cameraX3 = this.cameraX;
                Intrinsics.checkNotNull(cameraX3);
                CameraUseCaseAdapter cameraUseCaseAdapterProvide = cameraX3.getCameraUseCaseAdapterProvider().provide(cameraInternalSelect, cameraInternal, adapterCameraInfo2, adapterCameraInfo, compositionSettings, compositionSettings2);
                CameraX cameraX4 = this.cameraX;
                Intrinsics.checkNotNull(cameraX4);
                lifecycleCamera = lifecycleCameraRepository3.createLifecycleCamera(lifecycleOwner, cameraUseCaseAdapterProvide, cameraX4.getRotationProvider());
            }
            if (!sessionConfig.getUseCases().isEmpty()) {
                LifecycleCameraRepository lifecycleCameraRepository4 = this.lifecycleCameraRepository;
                Intrinsics.checkNotNull(lifecycleCameraRepository4);
                Intrinsics.checkNotNull(lifecycleCamera);
                CameraX cameraX5 = this.cameraX;
                Intrinsics.checkNotNull(cameraX5);
                lifecycleCameraRepository4.bindToLifecycleCamera(lifecycleCamera, sessionConfig, cameraX5.getCameraFactory().getCameraCoordinator());
                this.lifecycleCameraKeys.add(LifecycleCameraRepository.Key.create(lifecycleOwner, cameraIdentifierFromAdapterInfos));
            } else {
                Intrinsics.checkNotNull(lifecycleCamera);
            }
            Trace.endSection();
            return lifecycleCamera;
        } catch (Throwable th) {
            Trace.endSection();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Pair getSelectorsWithSessionFilter(SessionConfig sessionConfig, CameraSelector cameraSelector, CameraSelector cameraSelector2) {
        CameraFilter cameraFilter = sessionConfig.getCameraFilter();
        if (cameraFilter == null) {
            return TuplesKt.m1081to(cameraSelector, cameraSelector2);
        }
        CameraSelector cameraSelectorBuild = CameraSelector.Builder.fromSelector(cameraSelector).addCameraFilter(cameraFilter).build();
        Intrinsics.checkNotNullExpressionValue(cameraSelectorBuild, "build(...)");
        return TuplesKt.m1081to(cameraSelectorBuild, cameraSelector2 != null ? CameraSelector.Builder.fromSelector(cameraSelector2).addCameraFilter(cameraFilter).build() : null);
    }

    public CameraInfo getCameraInfo(CameraSelector cameraSelector) {
        Object adapterCameraInfo;
        Intrinsics.checkNotNullParameter(cameraSelector, "cameraSelector");
        Trace.beginSection("CX:getCameraInfo");
        try {
            CameraX cameraX = this.cameraX;
            Intrinsics.checkNotNull(cameraX);
            CameraInfoInternal cameraInfoInternal = cameraSelector.select(cameraX.getCameraRepository().getCameras()).getCameraInfoInternal();
            Intrinsics.checkNotNullExpressionValue(cameraInfoInternal, "getCameraInfoInternal(...)");
            CameraConfig cameraConfig = getCameraConfig(cameraSelector, cameraInfoInternal);
            String cameraId = cameraInfoInternal.getCameraId();
            Intrinsics.checkNotNullExpressionValue(cameraId, "getCameraId(...)");
            CameraIdentifier cameraIdentifierCreate = CameraIdentifier.Factory.create(cameraId, null, cameraConfig.getCompatibilityId());
            synchronized (this.lock) {
                try {
                    adapterCameraInfo = this.cameraInfoMap.get(cameraIdentifierCreate);
                    if (adapterCameraInfo == null) {
                        adapterCameraInfo = new AdapterCameraInfo(cameraInfoInternal, cameraConfig);
                        this.cameraInfoMap.put(cameraIdentifierCreate, adapterCameraInfo);
                    }
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return (AdapterCameraInfo) adapterCameraInfo;
        } finally {
            Trace.endSection();
        }
    }

    @Override // androidx.camera.core.CameraPresenceListener
    public void onCamerasRemoved(Set removedCameraIds) {
        Intrinsics.checkNotNullParameter(removedCameraIds, "removedCameraIds");
        Threads.checkMainThread();
        synchronized (this.lock) {
            try {
                Iterator it = removedCameraIds.iterator();
                while (it.hasNext()) {
                    CameraIdentifier cameraIdentifier = (CameraIdentifier) it.next();
                    Set setKeySet = this.cameraInfoMap.keySet();
                    ArrayList arrayList = new ArrayList();
                    for (Object obj : setKeySet) {
                        if (Intrinsics.areEqual(((CameraIdentifier) obj).getCameraIds(), cameraIdentifier.getCameraIds())) {
                            arrayList.add(obj);
                        }
                    }
                    int size = arrayList.size();
                    int i = 0;
                    while (i < size) {
                        Object obj2 = arrayList.get(i);
                        i++;
                        this.cameraInfoMap.remove((CameraIdentifier) obj2);
                    }
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isVideoCapture(UseCase useCase) {
        return useCase.getCurrentConfig().containsOption(UseCaseConfig.OPTION_CAPTURE_TYPE) && useCase.getCurrentConfig().getCaptureType() == UseCaseConfigFactory.CaptureType.VIDEO_CAPTURE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isPreview(UseCase useCase) {
        return useCase instanceof Preview;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final CameraConfig getCameraConfig(CameraSelector cameraSelector, CameraInfo cameraInfo) {
        Iterator it = cameraSelector.getCameraFilterSet().iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        CameraConfig cameraConfig = null;
        while (it.hasNext()) {
            Object next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            CameraFilter cameraFilter = (CameraFilter) next;
            if (!Intrinsics.areEqual(cameraFilter.getIdentifier(), CameraFilter.DEFAULT_ID)) {
                CameraConfigProvider configProvider = ExtendedCameraConfigProviderStore.getConfigProvider(cameraFilter.getIdentifier());
                Context context = this.context;
                Intrinsics.checkNotNull(context);
                CameraConfig config = configProvider.getConfig(cameraInfo, context);
                if (config == null) {
                    continue;
                } else {
                    if (cameraConfig != null) {
                        throw new IllegalArgumentException("Cannot apply multiple extended camera configs at the same time.");
                    }
                    cameraConfig = config;
                }
            }
        }
        return cameraConfig == null ? CameraConfigs.defaultConfig() : cameraConfig;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getCameraOperatingMode() {
        if (!isInitialized()) {
            return 0;
        }
        CameraX cameraX = this.cameraX;
        Intrinsics.checkNotNull(cameraX);
        return cameraX.getCameraFactory().getCameraCoordinator().getCameraOperatingMode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setCameraOperatingMode(int i) {
        if (isInitialized()) {
            CameraX cameraX = this.cameraX;
            Intrinsics.checkNotNull(cameraX);
            cameraX.getCameraFactory().getCameraCoordinator().setCameraOperatingMode(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List getActiveConcurrentCameraInfos() {
        if (!isInitialized()) {
            return CollectionsKt.emptyList();
        }
        CameraX cameraX = this.cameraX;
        Intrinsics.checkNotNull(cameraX);
        List activeConcurrentCameraInfos = cameraX.getCameraFactory().getCameraCoordinator().getActiveConcurrentCameraInfos();
        Intrinsics.checkNotNullExpressionValue(activeConcurrentCameraInfos, "getActiveConcurrentCameraInfos(...)");
        return activeConcurrentCameraInfos;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setActiveConcurrentCameraInfos(List list) {
        if (isInitialized()) {
            CameraX cameraX = this.cameraX;
            Intrinsics.checkNotNull(cameraX);
            cameraX.getCameraFactory().getCameraCoordinator().setActiveConcurrentCameraInfos(list);
        }
    }
}
