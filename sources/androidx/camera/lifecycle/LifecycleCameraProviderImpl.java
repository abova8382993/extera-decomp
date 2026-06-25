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
import androidx.camera.lifecycle.LifecycleCameraRepository;
import androidx.core.util.Preconditions;
import androidx.tracing.Trace;
import androidx.view.LifecycleOwner;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.StringCompanionObject;
import okhttp3.internal.url._UrlKt;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000Ø\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0010\b\u0001\u0018\u00002\u00020\u00012\u00020\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0005H\u0003¢\u0006\u0004\b\u0006\u0010\u0004J#\u0010\u000b\u001a\u00020\u00052\b\u0010\b\u001a\u0004\u0018\u00010\u00072\b\u0010\n\u001a\u0004\u0018\u00010\tH\u0003¢\u0006\u0004\b\u000b\u0010\fJG\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u000f2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u000f2\b\b\u0002\u0010\u0013\u001a\u00020\u00122\b\b\u0002\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0015H\u0002¢\u0006\u0004\b\u0018\u0010\u0019J7\u0010\u001b\u001a\u0010\u0012\u0004\u0012\u00020\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u001a2\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0010\u001a\u00020\u000f2\b\u0010\u0011\u001a\u0004\u0018\u00010\u000fH\u0002¢\u0006\u0004\b\u001b\u0010\u001cJ\u0017\u0010 \u001a\u00020\u001f2\u0006\u0010\u001e\u001a\u00020\u001dH\u0002¢\u0006\u0004\b \u0010!J\u0017\u0010\"\u001a\u00020\u001f2\u0006\u0010\u001e\u001a\u00020\u001dH\u0002¢\u0006\u0004\b\"\u0010!J\u001f\u0010'\u001a\u00020&2\u0006\u0010#\u001a\u00020\u000f2\u0006\u0010%\u001a\u00020$H\u0002¢\u0006\u0004\b'\u0010(J)\u00100\u001a\b\u0012\u0004\u0012\u00020-0,2\u0006\u0010)\u001a\u00020\t2\n\b\u0002\u0010+\u001a\u0004\u0018\u00010*H\u0000¢\u0006\u0004\b.\u0010/J\u0017\u00103\u001a\u00020\u00052\u0006\u0010+\u001a\u00020*H\u0000¢\u0006\u0004\b1\u00102J\u001f\u00107\u001a\b\u0012\u0004\u0012\u00020-0,2\b\b\u0002\u00104\u001a\u00020\u001fH\u0000¢\u0006\u0004\b5\u00106J\u000f\u00108\u001a\u00020\u0005H\u0017¢\u0006\u0004\b8\u0010\u0004J\u0017\u00109\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020\u000fH\u0016¢\u0006\u0004\b9\u0010:J7\u0010=\u001a\u00020\u00172\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010#\u001a\u00020\u000f2\u0016\u0010<\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u001d0;\"\u0004\u0018\u00010\u001dH\u0017¢\u0006\u0004\b=\u0010>J'\u0010=\u001a\u00020\u00172\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010#\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u0015H\u0017¢\u0006\u0004\b=\u0010?J\u001f\u0010=\u001a\u00020C2\u000e\u0010B\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010A0@H\u0017¢\u0006\u0004\b=\u0010DJ\u0017\u0010E\u001a\u00020$2\u0006\u0010#\u001a\u00020\u000fH\u0016¢\u0006\u0004\bE\u0010FJ\u001d\u0010J\u001a\u00020\u00052\f\u0010I\u001a\b\u0012\u0004\u0012\u00020H0GH\u0017¢\u0006\u0004\bJ\u0010KJ\u001d\u0010M\u001a\u00020\u00052\f\u0010L\u001a\b\u0012\u0004\u0012\u00020H0GH\u0017¢\u0006\u0004\bM\u0010KR\u0014\u0010N\u001a\u00020\u00018\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bN\u0010OR*\u0010Q\u001a\u0004\u0018\u00010P8\u0000@\u0000X\u0081\u000e¢\u0006\u0018\n\u0004\bQ\u0010R\u0012\u0004\bW\u0010\u0004\u001a\u0004\bS\u0010T\"\u0004\bU\u0010VR\u001e\u0010X\u001a\n\u0012\u0004\u0012\u00020-\u0018\u00010,8\u0002@\u0002X\u0083\u000e¢\u0006\u0006\n\u0004\bX\u0010YR$\u0010[\u001a\u0010\u0012\f\u0012\n Z*\u0004\u0018\u00010-0-0,8\u0002@\u0002X\u0083\u000e¢\u0006\u0006\n\u0004\b[\u0010YR\u0018\u0010\\\u001a\u0004\u0018\u00010\u00078\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\\\u0010]R\u0018\u0010_\u001a\u0004\u0018\u00010^8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b_\u0010`R*\u0010)\u001a\u0004\u0018\u00010\t8\u0000@\u0000X\u0081\u000e¢\u0006\u0018\n\u0004\b)\u0010a\u0012\u0004\bf\u0010\u0004\u001a\u0004\bb\u0010c\"\u0004\bd\u0010eR \u0010i\u001a\u000e\u0012\u0004\u0012\u00020H\u0012\u0004\u0012\u00020h0g8\u0002X\u0083\u0004¢\u0006\u0006\n\u0004\bi\u0010jR$\u0010n\u001a\u0012\u0012\u0004\u0012\u00020l0kj\b\u0012\u0004\u0012\u00020l`m8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bn\u0010oR\u0014\u0010p\u001a\u00020\u001f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bp\u0010qR$\u0010s\u001a\u00020r2\u0006\u0010s\u001a\u00020r8B@BX\u0082\u000e¢\u0006\f\u001a\u0004\bt\u0010u\"\u0004\bv\u0010wR0\u0010}\u001a\b\u0012\u0004\u0012\u00020$0@2\f\u0010x\u001a\b\u0012\u0004\u0012\u00020$0@8B@BX\u0082\u000e¢\u0006\f\u001a\u0004\by\u0010z\"\u0004\b{\u0010|R\u001a\u0010\u007f\u001a\b\u0012\u0004\u0012\u00020$0@8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b~\u0010zR\"\u0010\u0081\u0001\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020$0@0@8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u0080\u0001\u0010z¨\u0006\u0082\u0001"}, m877d2 = {"Landroidx/camera/lifecycle/LifecycleCameraProviderImpl;", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/core/CameraPresenceListener;", "<init>", "()V", _UrlKt.FRAGMENT_ENCODE_SET, "shutdownInternal", "Landroidx/camera/core/CameraX;", "newCameraX", "Landroid/content/Context;", "newContext", "initInternal", "(Landroidx/camera/core/CameraX;Landroid/content/Context;)V", "Landroidx/lifecycle/LifecycleOwner;", "lifecycleOwner", "Landroidx/camera/core/CameraSelector;", "primaryCameraSelector", "secondaryCameraSelector", "Landroidx/camera/core/CompositionSettings;", "primaryCompositionSettings", "secondaryCompositionSettings", "Landroidx/camera/core/SessionConfig;", "sessionConfig", "Landroidx/camera/core/Camera;", "bindToLifecycleInternal", "(Landroidx/lifecycle/LifecycleOwner;Landroidx/camera/core/CameraSelector;Landroidx/camera/core/CameraSelector;Landroidx/camera/core/CompositionSettings;Landroidx/camera/core/CompositionSettings;Landroidx/camera/core/SessionConfig;)Landroidx/camera/core/Camera;", "Lkotlin/Pair;", "getSelectorsWithSessionFilter", "(Landroidx/camera/core/SessionConfig;Landroidx/camera/core/CameraSelector;Landroidx/camera/core/CameraSelector;)Lkotlin/Pair;", "Landroidx/camera/core/UseCase;", "useCase", _UrlKt.FRAGMENT_ENCODE_SET, "isVideoCapture", "(Landroidx/camera/core/UseCase;)Z", "isPreview", "cameraSelector", "Landroidx/camera/core/CameraInfo;", "cameraInfo", "Landroidx/camera/core/impl/CameraConfig;", "getCameraConfig", "(Landroidx/camera/core/CameraSelector;Landroidx/camera/core/CameraInfo;)Landroidx/camera/core/impl/CameraConfig;", "context", "Landroidx/camera/core/CameraXConfig;", "cameraXConfig", "Lcom/google/common/util/concurrent/ListenableFuture;", "Ljava/lang/Void;", "initAsync$camera_lifecycle", "(Landroid/content/Context;Landroidx/camera/core/CameraXConfig;)Lcom/google/common/util/concurrent/ListenableFuture;", "initAsync", "configure$camera_lifecycle", "(Landroidx/camera/core/CameraXConfig;)V", "configure", "clearConfigProvider", "shutdownAsync$camera_lifecycle", "(Z)Lcom/google/common/util/concurrent/ListenableFuture;", "shutdownAsync", "unbindAll", "hasCamera", "(Landroidx/camera/core/CameraSelector;)Z", _UrlKt.FRAGMENT_ENCODE_SET, "useCases", "bindToLifecycle", "(Landroidx/lifecycle/LifecycleOwner;Landroidx/camera/core/CameraSelector;[Landroidx/camera/core/UseCase;)Landroidx/camera/core/Camera;", "(Landroidx/lifecycle/LifecycleOwner;Landroidx/camera/core/CameraSelector;Landroidx/camera/core/SessionConfig;)Landroidx/camera/core/Camera;", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/core/ConcurrentCamera$SingleCameraConfig;", "singleCameraConfigs", "Landroidx/camera/core/ConcurrentCamera;", "(Ljava/util/List;)Landroidx/camera/core/ConcurrentCamera;", "getCameraInfo", "(Landroidx/camera/core/CameraSelector;)Landroidx/camera/core/CameraInfo;", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/core/CameraIdentifier;", "addedCameraIds", "onCamerasAdded", "(Ljava/util/Set;)V", "removedCameraIds", "onCamerasRemoved", "lock", "Ljava/lang/Object;", "Landroidx/camera/core/CameraXConfig$Provider;", "cameraXConfigProvider", "Landroidx/camera/core/CameraXConfig$Provider;", "getCameraXConfigProvider$camera_lifecycle", "()Landroidx/camera/core/CameraXConfig$Provider;", "setCameraXConfigProvider$camera_lifecycle", "(Landroidx/camera/core/CameraXConfig$Provider;)V", "getCameraXConfigProvider$camera_lifecycle$annotations", "cameraXInitializeFuture", "Lcom/google/common/util/concurrent/ListenableFuture;", "kotlin.jvm.PlatformType", "cameraXShutdownFuture", "cameraX", "Landroidx/camera/core/CameraX;", "Landroidx/camera/lifecycle/LifecycleCameraRepository;", "lifecycleCameraRepository", "Landroidx/camera/lifecycle/LifecycleCameraRepository;", "Landroid/content/Context;", "getContext$camera_lifecycle", "()Landroid/content/Context;", "setContext$camera_lifecycle", "(Landroid/content/Context;)V", "getContext$camera_lifecycle$annotations", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/core/impl/AdapterCameraInfo;", "cameraInfoMap", "Ljava/util/Map;", "Ljava/util/HashSet;", "Landroidx/camera/lifecycle/LifecycleCameraRepository$Key;", "Lkotlin/collections/HashSet;", "lifecycleCameraKeys", "Ljava/util/HashSet;", "isInitialized", "()Z", _UrlKt.FRAGMENT_ENCODE_SET, "cameraOperatingMode", "getCameraOperatingMode", "()I", "setCameraOperatingMode", "(I)V", "cameraInfos", "getActiveConcurrentCameraInfos", "()Ljava/util/List;", "setActiveConcurrentCameraInfos", "(Ljava/util/List;)V", "activeConcurrentCameraInfos", "getAvailableCameraInfos", "availableCameraInfos", "getAvailableConcurrentCameraInfos", "availableConcurrentCameraInfos", "camera-lifecycle"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nLifecycleCameraProviderImpl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LifecycleCameraProviderImpl.kt\nandroidx/camera/lifecycle/LifecycleCameraProviderImpl\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 Trace.kt\nandroidx/tracing/TraceKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,850:1\n1#2:851\n27#3,5:852\n27#3,5:857\n27#3,5:862\n27#3,5:867\n27#3,5:872\n27#3,5:877\n27#3,5:882\n27#3,5:887\n27#3,5:892\n27#3,5:897\n27#3,5:902\n27#3,3:907\n31#3:912\n27#3,5:913\n1869#4,2:910\n774#4:918\n865#4,2:919\n*S KotlinDebug\n*F\n+ 1 LifecycleCameraProviderImpl.kt\nandroidx/camera/lifecycle/LifecycleCameraProviderImpl\n*L\n163#1:852,5\n228#1:857,5\n245#1:862,5\n259#1:867,5\n267#1:872,5\n283#1:877,5\n306#1:882,5\n329#1:887,5\n349#1:892,5\n495#1:897,5\n506#1:902,5\n605#1:907,3\n605#1:912\n729#1:913,5\n650#1:910,2\n784#1:918\n784#1:919,2\n*E\n"})
public final class LifecycleCameraProviderImpl implements CameraProvider, CameraPresenceListener {
    private CameraX cameraX;
    private CameraXConfig.Provider cameraXConfigProvider;
    private ListenableFuture<Void> cameraXInitializeFuture;
    private Context context;
    private LifecycleCameraRepository lifecycleCameraRepository;
    private final Object lock = new Object();
    private ListenableFuture<Void> cameraXShutdownFuture = Futures.immediateFuture(null);
    private final Map<CameraIdentifier, AdapterCameraInfo> cameraInfoMap = new HashMap();
    private final HashSet<LifecycleCameraRepository.Key> lifecycleCameraKeys = new HashSet<>();

    @Override // androidx.camera.core.CameraPresenceListener
    public void onCamerasAdded(Set<CameraIdentifier> addedCameraIds) {
    }

    /* JADX INFO: renamed from: getCameraXConfigProvider$camera_lifecycle, reason: from getter */
    public final CameraXConfig.Provider getCameraXConfigProvider() {
        return this.cameraXConfigProvider;
    }

    public final void setCameraXConfigProvider$camera_lifecycle(CameraXConfig.Provider provider) {
        this.cameraXConfigProvider = provider;
    }

    private final boolean isInitialized() {
        return this.cameraX != null;
    }

    /* JADX INFO: renamed from: getContext$camera_lifecycle, reason: from getter */
    public final Context getContext() {
        return this.context;
    }

    public final ListenableFuture<Void> initAsync$camera_lifecycle(final Context context, CameraXConfig cameraXConfig) {
        synchronized (this.lock) {
            try {
                this.lifecycleCameraRepository = LifecycleCameraRepositories.getInstance$camera_lifecycle(ContextUtil.getDeviceId(context));
                ListenableFuture<Void> listenableFuture = this.cameraXInitializeFuture;
                if (listenableFuture != null) {
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
                this.cameraXInitializeFuture = futureChainTransform;
                Futures.addCallback(futureChainTransform, new FutureCallback<Void>() { // from class: androidx.camera.lifecycle.LifecycleCameraProviderImpl$initAsync$1$2
                    @Override // androidx.camera.core.impl.utils.futures.FutureCallback
                    public void onSuccess(Void r1) {
                    }

                    @Override // androidx.camera.core.impl.utils.futures.FutureCallback
                    public void onFailure(Throwable t) {
                        this.this$0.shutdownAsync$camera_lifecycle(false);
                    }
                }, CameraXExecutors.directExecutor());
                return Futures.nonCancellationPropagating(futureChainTransform);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static final ListenableFuture initAsync$lambda$0$1(CameraX cameraX, Void r1) {
        return cameraX.getInitializeFuture();
    }

    public static final ListenableFuture initAsync$lambda$0$2(Function1 function1, Object obj) {
        return (ListenableFuture) function1.invoke(obj);
    }

    public static final Void initAsync$lambda$0$4(Function1 function1, Object obj) {
        return (Void) function1.invoke(obj);
    }

    public static final Void initAsync$lambda$0$3(LifecycleCameraProviderImpl lifecycleCameraProviderImpl, CameraX cameraX, Context context, Void r3) {
        lifecycleCameraProviderImpl.initInternal(cameraX, ContextUtil.getPersistentApplicationContext(context));
        return r3;
    }

    private final void shutdownInternal() {
        initInternal(null, null);
    }

    private final void initInternal(CameraX newCameraX, Context newContext) {
        CameraPresenceProvider cameraAvailabilityProvider;
        synchronized (this.lock) {
            this.cameraX = newCameraX;
            this.context = newContext;
            if (newCameraX != null && (cameraAvailabilityProvider = newCameraX.getCameraAvailabilityProvider()) != null) {
                cameraAvailabilityProvider.addCameraPresenceListener(this, CameraXExecutors.mainThreadExecutor());
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    public final void configure$camera_lifecycle(final CameraXConfig cameraXConfig) {
        Trace.beginSection("CX:configureInstanceInternal");
        try {
            synchronized (this.lock) {
                Preconditions.checkNotNull(cameraXConfig);
                Preconditions.checkState(getCameraXConfigProvider() == null, "CameraX has already been configured. To use a different configuration, shutdown() must be called.");
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

    public final ListenableFuture<Void> shutdownAsync$camera_lifecycle(boolean clearConfigProvider) {
        ListenableFuture<Void> listenableFutureImmediateFuture;
        Threads.runOnMainSync(new Runnable() { // from class: androidx.camera.lifecycle.LifecycleCameraProviderImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                LifecycleCameraProviderImpl.$r8$lambda$cqEKuAajCjqIPjMA5N0LIeIbAfA(this.f$0);
            }
        });
        if (isInitialized()) {
            this.cameraX.getCameraAvailabilityProvider().removeCameraPresenceListener(this);
            listenableFutureImmediateFuture = this.cameraX.shutdown();
        } else {
            listenableFutureImmediateFuture = Futures.immediateFuture(null);
        }
        synchronized (this.lock) {
            if (clearConfigProvider) {
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

    public static void $r8$lambda$cqEKuAajCjqIPjMA5N0LIeIbAfA(LifecycleCameraProviderImpl lifecycleCameraProviderImpl) {
        if (lifecycleCameraProviderImpl.isInitialized()) {
            lifecycleCameraProviderImpl.unbindAll();
            lifecycleCameraProviderImpl.lifecycleCameraRepository.removeLifecycleCameras(lifecycleCameraProviderImpl.lifecycleCameraKeys);
        }
    }

    public void unbindAll() {
        Trace.beginSection("CX:unbindAll");
        try {
            Threads.checkMainThread();
            setCameraOperatingMode(0);
            this.lifecycleCameraRepository.unbindAll(this.lifecycleCameraKeys);
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.endSection();
        }
    }

    public boolean hasCamera(CameraSelector cameraSelector) {
        boolean z;
        Trace.beginSection("CX:hasCamera");
        try {
            cameraSelector.select(this.cameraX.getCameraRepository().getCameras());
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
        Trace.beginSection("CX:bindToLifecycle");
        try {
            if (getCameraOperatingMode() != 2) {
                setCameraOperatingMode(1);
                return bindToLifecycleInternal$default(this, lifecycleOwner, cameraSelector, null, null, null, new LegacySessionConfig(ArraysKt.filterNotNull(useCases), null, null, 6, null), 28, null);
            }
            throw new UnsupportedOperationException("bindToLifecycle for single camera is not supported in concurrent camera mode, call unbindAll() first");
        } finally {
            Trace.endSection();
        }
    }

    public Camera bindToLifecycle(LifecycleOwner lifecycleOwner, CameraSelector cameraSelector, SessionConfig sessionConfig) {
        Trace.beginSection("CX:bindToLifecycle-SessionConfig");
        try {
            if (getCameraOperatingMode() != 2) {
                setCameraOperatingMode(1);
                return bindToLifecycleInternal$default(this, lifecycleOwner, cameraSelector, null, null, null, sessionConfig, 28, null);
            }
            throw new UnsupportedOperationException("bindToLifecycle for single camera is not supported in concurrent camera mode, call unbindAll() first.");
        } finally {
            Trace.endSection();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:149:0x01d8 A[Catch: all -> 0x023a, TryCatch #1 {all -> 0x023a, blocks: (B:93:0x0005, B:95:0x000c, B:97:0x0012, B:100:0x003d, B:102:0x0043, B:104:0x0051, B:106:0x0066, B:108:0x007c, B:109:0x009c, B:111:0x00a2, B:112:0x00b4, B:114:0x00ba, B:116:0x00ca, B:117:0x00ce, B:118:0x00dc, B:154:0x020b, B:119:0x00f6, B:120:0x00fd, B:121:0x00fe, B:122:0x0103, B:123:0x0104, B:125:0x0114, B:127:0x011a, B:128:0x011f, B:129:0x012f, B:131:0x0141, B:134:0x014c, B:135:0x0153, B:136:0x0154, B:138:0x016d, B:140:0x017b, B:142:0x019d, B:148:0x01af, B:153:0x0208, B:144:0x01a3, B:146:0x01a9, B:149:0x01d8, B:150:0x01dc, B:152:0x01e2, B:157:0x0214, B:158:0x021b, B:159:0x021c, B:160:0x0221, B:161:0x0222, B:162:0x0229, B:163:0x022a, B:164:0x0231, B:165:0x0232, B:166:0x0239), top: B:172:0x0005, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public androidx.camera.core.ConcurrentCamera bindToLifecycle(java.util.List<androidx.camera.core.ConcurrentCamera.SingleCameraConfig> r14) {
        /*
            Method dump skipped, instruction units count: 575
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.lifecycle.LifecycleCameraProviderImpl.bindToLifecycle(java.util.List):androidx.camera.core.ConcurrentCamera");
    }

    public List<CameraInfo> getAvailableCameraInfos() {
        Trace.beginSection("CX:getAvailableCameraInfos");
        try {
            ArrayList arrayList = new ArrayList();
            Iterator<CameraInternal> it = this.cameraX.getCameraRepository().getCameras().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getCameraInfo());
            }
            return arrayList;
        } finally {
            Trace.endSection();
        }
    }

    public List<List<CameraInfo>> getAvailableConcurrentCameraInfos() {
        Trace.beginSection("CX:getAvailableConcurrentCameraInfos");
        try {
            Objects.requireNonNull(this.cameraX);
            Objects.requireNonNull(this.cameraX.getCameraFactory().getCameraCoordinator());
            List<List<CameraSelector>> concurrentCameraSelectors = this.cameraX.getCameraFactory().getCameraCoordinator().getConcurrentCameraSelectors();
            ArrayList arrayList = new ArrayList();
            for (List<CameraSelector> list : concurrentCameraSelectors) {
                ArrayList arrayList2 = new ArrayList();
                Iterator<CameraSelector> it = list.iterator();
                while (it.hasNext()) {
                    try {
                        arrayList2.add(getCameraInfo(it.next()));
                    } catch (IllegalArgumentException unused) {
                    }
                }
                arrayList.add(arrayList2);
            }
            return arrayList;
        } finally {
            Trace.endSection();
        }
    }

    public static /* synthetic */ Camera bindToLifecycleInternal$default(LifecycleCameraProviderImpl lifecycleCameraProviderImpl, LifecycleOwner lifecycleOwner, CameraSelector cameraSelector, CameraSelector cameraSelector2, CompositionSettings compositionSettings, CompositionSettings compositionSettings2, SessionConfig sessionConfig, int i, Object obj) {
        if ((i & 4) != 0) {
            cameraSelector2 = null;
        }
        CameraSelector cameraSelector3 = cameraSelector2;
        if ((i & 8) != 0) {
            compositionSettings = CompositionSettings.DEFAULT;
        }
        CompositionSettings compositionSettings3 = compositionSettings;
        if ((i & 16) != 0) {
            compositionSettings2 = CompositionSettings.DEFAULT;
        }
        return lifecycleCameraProviderImpl.bindToLifecycleInternal(lifecycleOwner, cameraSelector, cameraSelector3, compositionSettings3, compositionSettings2, sessionConfig);
    }

    public final Camera bindToLifecycleInternal(LifecycleOwner lifecycleOwner, CameraSelector primaryCameraSelector, CameraSelector secondaryCameraSelector, CompositionSettings primaryCompositionSettings, CompositionSettings secondaryCompositionSettings, SessionConfig sessionConfig) {
        CameraInternal cameraInternal;
        AdapterCameraInfo adapterCameraInfo;
        Trace.beginSection("CX:bindToLifecycle-internal");
        try {
            Threads.checkMainThread();
            Pair selectorsWithSessionFilter = getSelectorsWithSessionFilter(sessionConfig, primaryCameraSelector, secondaryCameraSelector);
            CameraSelector cameraSelector = (CameraSelector) selectorsWithSessionFilter.component1();
            CameraSelector cameraSelector2 = (CameraSelector) selectorsWithSessionFilter.component2();
            CameraInternal cameraInternalSelect = cameraSelector.select(this.cameraX.getCameraRepository().getCameras());
            cameraInternalSelect.setPrimary(true);
            AdapterCameraInfo adapterCameraInfo2 = (AdapterCameraInfo) getCameraInfo(cameraSelector);
            if (cameraSelector2 != null) {
                CameraInternal cameraInternalSelect2 = cameraSelector2.select(this.cameraX.getCameraRepository().getCameras());
                cameraInternalSelect2.setPrimary(false);
                adapterCameraInfo = (AdapterCameraInfo) getCameraInfo(cameraSelector2);
                cameraInternal = cameraInternalSelect2;
            } else {
                cameraInternal = null;
                adapterCameraInfo = null;
            }
            CameraIdentifier cameraIdentifierFromAdapterInfos = CameraIdentifier.Factory.fromAdapterInfos(adapterCameraInfo2, adapterCameraInfo);
            LifecycleCamera lifecycleCamera = this.lifecycleCameraRepository.getLifecycleCamera(lifecycleOwner, cameraIdentifierFromAdapterInfos);
            Collection<LifecycleCamera> lifecycleCameras = this.lifecycleCameraRepository.getLifecycleCameras();
            for (UseCase useCase : sessionConfig.getUseCases()) {
                for (LifecycleCamera lifecycleCamera2 : lifecycleCameras) {
                    if (lifecycleCamera2.isBound(useCase) && !Intrinsics.areEqual(lifecycleCamera2.getLifecycleOwner(), lifecycleOwner)) {
                        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                        throw new IllegalStateException(String.format("Use case %s already bound to a different lifecycle.", Arrays.copyOf(new Object[]{useCase}, 1)));
                    }
                }
            }
            if (lifecycleCamera == null) {
                lifecycleCamera = this.lifecycleCameraRepository.createLifecycleCamera(lifecycleOwner, this.cameraX.getCameraUseCaseAdapterProvider().provide(cameraInternalSelect, cameraInternal, adapterCameraInfo2, adapterCameraInfo, primaryCompositionSettings, secondaryCompositionSettings), this.cameraX.getRotationProvider());
            }
            if (!sessionConfig.getUseCases().isEmpty()) {
                this.lifecycleCameraRepository.bindToLifecycleCamera(lifecycleCamera, sessionConfig, this.cameraX.getCameraFactory().getCameraCoordinator());
                this.lifecycleCameraKeys.add(LifecycleCameraRepository.Key.create(lifecycleOwner, cameraIdentifierFromAdapterInfos));
            }
            return lifecycleCamera;
        } finally {
            Trace.endSection();
        }
    }

    public final Pair<CameraSelector, CameraSelector> getSelectorsWithSessionFilter(SessionConfig sessionConfig, CameraSelector primaryCameraSelector, CameraSelector secondaryCameraSelector) {
        CameraFilter cameraFilter = sessionConfig.getCameraFilter();
        if (cameraFilter == null) {
            return TuplesKt.m884to(primaryCameraSelector, secondaryCameraSelector);
        }
        return TuplesKt.m884to(CameraSelector.Builder.fromSelector(primaryCameraSelector).addCameraFilter(cameraFilter).build(), secondaryCameraSelector != null ? CameraSelector.Builder.fromSelector(secondaryCameraSelector).addCameraFilter(cameraFilter).build() : null);
    }

    public CameraInfo getCameraInfo(CameraSelector cameraSelector) {
        Object adapterCameraInfo;
        Trace.beginSection("CX:getCameraInfo");
        try {
            CameraInfoInternal cameraInfo = cameraSelector.select(this.cameraX.getCameraRepository().getCameras()).getCameraInfo();
            CameraConfig cameraConfig = getCameraConfig(cameraSelector, cameraInfo);
            CameraIdentifier cameraIdentifierCreate = CameraIdentifier.Factory.create(cameraInfo.getCameraId(), null, cameraConfig.getCompatibilityId());
            synchronized (this.lock) {
                try {
                    adapterCameraInfo = this.cameraInfoMap.get(cameraIdentifierCreate);
                    if (adapterCameraInfo == null) {
                        adapterCameraInfo = new AdapterCameraInfo(cameraInfo, cameraConfig);
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
    public void onCamerasRemoved(Set<CameraIdentifier> removedCameraIds) {
        Threads.checkMainThread();
        synchronized (this.lock) {
            try {
                for (CameraIdentifier cameraIdentifier : removedCameraIds) {
                    Set<CameraIdentifier> setKeySet = this.cameraInfoMap.keySet();
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

    public final boolean isVideoCapture(UseCase useCase) {
        return useCase.getCurrentConfig().containsOption(UseCaseConfig.OPTION_CAPTURE_TYPE) && useCase.getCurrentConfig().getCaptureType() == UseCaseConfigFactory.CaptureType.VIDEO_CAPTURE;
    }

    public final boolean isPreview(UseCase useCase) {
        return useCase instanceof Preview;
    }

    public final CameraConfig getCameraConfig(CameraSelector cameraSelector, CameraInfo cameraInfo) {
        CameraConfig config;
        CameraConfig cameraConfig = null;
        for (CameraFilter cameraFilter : cameraSelector.getCameraFilterSet()) {
            if (!Intrinsics.areEqual(cameraFilter.getIdentifier(), CameraFilter.DEFAULT_ID) && (config = ExtendedCameraConfigProviderStore.getConfigProvider(cameraFilter.getIdentifier()).getConfig(cameraInfo, this.context)) != null) {
                if (cameraConfig != null) {
                    g$$ExternalSyntheticBUOutline1.m207m("Cannot apply multiple extended camera configs at the same time.");
                    return null;
                }
                cameraConfig = config;
            }
        }
        return cameraConfig == null ? CameraConfigs.defaultConfig() : cameraConfig;
    }

    public final int getCameraOperatingMode() {
        if (isInitialized()) {
            return this.cameraX.getCameraFactory().getCameraCoordinator().getCameraOperatingMode();
        }
        return 0;
    }

    public final void setCameraOperatingMode(int i) {
        if (isInitialized()) {
            this.cameraX.getCameraFactory().getCameraCoordinator().setCameraOperatingMode(i);
        }
    }

    public final List<CameraInfo> getActiveConcurrentCameraInfos() {
        return isInitialized() ? this.cameraX.getCameraFactory().getCameraCoordinator().getActiveConcurrentCameraInfos() : CollectionsKt.emptyList();
    }

    public final void setActiveConcurrentCameraInfos(List<? extends CameraInfo> list) {
        if (isInitialized()) {
            this.cameraX.getCameraFactory().getCameraCoordinator().setActiveConcurrentCameraInfos(list);
        }
    }
}
