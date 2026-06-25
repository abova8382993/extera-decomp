package androidx.camera.camera2.pipe.compat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import androidx.camera.camera2.pipe.CameraId;
import androidx.camera.camera2.pipe.compat.Camera2DeviceCache;
import androidx.camera.camera2.pipe.core.Log;
import androidx.camera.camera2.pipe.core.Threads;
import androidx.camera.camera2.pipe.internal.CameraErrorListener;
import androidx.camera.camera2.pipe.internal.CameraPipeLifetime;
import androidx.camera.featurecombinationquery.CameraDeviceSetupCompat;
import androidx.camera.featurecombinationquery.CameraDeviceSetupCompatFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Provider;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.CoroutineName;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.channels.ChannelsKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import okhttp3.internal.url._UrlKt;
import okio.Segment$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000\u009a\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u000e\b\u0001\u0018\u00002\u00020\u0001BY\b\u0007\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0001\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0006\u0010\f\u001a\u00020\u000b\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u0002\u0012\u0006\u0010\u0010\u001a\u00020\u000f\u0012\b\b\u0001\u0010\u0012\u001a\u00020\u0011¢\u0006\u0004\b\u0013\u0010\u0014J\u001b\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u00160\u0015H\u0002¢\u0006\u0004\b\u0018\u0010\u0019J/\u0010 \u001a\u00020\u001f*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u00160\u001a2\u0006\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001e\u001a\u00020\u001dH\u0002¢\u0006\u0004\b \u0010!J7\u0010$\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u00162\u000e\u0010\"\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u00162\u000e\u0010#\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u0016H\u0002¢\u0006\u0004\b$\u0010%J-\u0010'\u001a\u00020\u001f*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u00160\u001a2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u0002¢\u0006\u0004\b'\u0010(J\u0017\u0010)\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u0016H\u0002¢\u0006\u0004\b)\u0010*J\u0017\u0010,\u001a\u00020+2\u0006\u0010\n\u001a\u00020\tH\u0002¢\u0006\u0004\b,\u0010-J\u001d\u0010.\u001a\u00020\u001d2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u0002¢\u0006\u0004\b.\u0010/J\u001a\u00103\u001a\u0004\u0018\u0001002\u0006\u0010\u001c\u001a\u00020\u0017H\u0086@¢\u0006\u0004\b1\u00102J\u001a\u00106\u001a\u0004\u0018\u0001042\u0006\u0010\u001c\u001a\u00020\u0017H\u0087@¢\u0006\u0004\b5\u00102J\u0015\u00107\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u0016¢\u0006\u0004\b7\u0010*J\u001b\u00109\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001708\u0018\u000108¢\u0006\u0004\b9\u0010:J\r\u0010;\u001a\u00020\u001f¢\u0006\u0004\b;\u0010<R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0004\u0010=R\u0014\u0010\u0006\u001a\u00020\u00058\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0006\u0010>R\u0014\u0010\b\u001a\u00020\u00078\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\b\u0010?R\u0014\u0010\f\u001a\u00020\u000b8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\f\u0010@R\u001a\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u000e\u0010=R\u0014\u0010B\u001a\u00020A8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bB\u0010CR\u0014\u0010D\u001a\u00020\u00018\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bD\u0010ER\u001e\u0010F\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u00168\u0002@\u0002X\u0083\u000e¢\u0006\u0006\n\u0004\bF\u0010GR$\u0010H\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001708\u0018\u0001088\u0002@\u0002X\u0083\u000e¢\u0006\u0006\n\u0004\bH\u0010IR(\u0010L\u001a\u0016\u0012\u0004\u0012\u00020\u0017\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u0001000K0J8\u0002X\u0083\u0004¢\u0006\u0006\n\u0004\bL\u0010MR(\u0010N\u001a\u0016\u0012\u0004\u0012\u00020\u0017\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u0001040K0J8\u0002X\u0083\u0004¢\u0006\u0006\n\u0004\bN\u0010MR\u0014\u0010O\u001a\u00020+8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bO\u0010PR#\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u00160\u00158\u0006¢\u0006\f\n\u0004\b&\u0010Q\u001a\u0004\bR\u0010\u0019R#\u0010X\u001a\n S*\u0004\u0018\u00010\r0\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bT\u0010U\u001a\u0004\bV\u0010W¨\u0006Y"}, m877d2 = {"Landroidx/camera/camera2/pipe/compat/Camera2DeviceCache;", _UrlKt.FRAGMENT_ENCODE_SET, "Ljavax/inject/Provider;", "Landroid/hardware/camera2/CameraManager;", "cameraManager", "Landroidx/camera/camera2/pipe/core/Threads;", "threads", "Landroid/content/Context;", "context", "Landroid/content/pm/PackageManager;", "packageManager", "Landroidx/camera/camera2/pipe/internal/CameraErrorListener;", "cameraErrorListener", "Landroidx/camera/featurecombinationquery/CameraDeviceSetupCompatFactory;", "cameraDeviceSetupCompatFactoryProvider", "Landroidx/camera/camera2/pipe/internal/CameraPipeLifetime;", "cameraPipeLifetime", "Lkotlinx/coroutines/Job;", "cameraPipeJob", "<init>", "(Ljavax/inject/Provider;Landroidx/camera/camera2/pipe/core/Threads;Landroid/content/Context;Landroid/content/pm/PackageManager;Landroidx/camera/camera2/pipe/internal/CameraErrorListener;Ljavax/inject/Provider;Landroidx/camera/camera2/pipe/internal/CameraPipeLifetime;Lkotlinx/coroutines/Job;)V", "Lkotlinx/coroutines/flow/Flow;", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/camera2/pipe/CameraId;", "createCameraIdListFlow", "()Lkotlinx/coroutines/flow/Flow;", "Lkotlinx/coroutines/channels/ProducerScope;", _UrlKt.FRAGMENT_ENCODE_SET, "cameraId", _UrlKt.FRAGMENT_ENCODE_SET, "isAvailable", _UrlKt.FRAGMENT_ENCODE_SET, "onCameraAvailabilityChanged", "(Lkotlinx/coroutines/channels/ProducerScope;Ljava/lang/String;Z)V", "cachedCameraIds", "cameraIdsRead", "getUpdatedCameraIds", "(Ljava/util/List;Ljava/util/List;)Ljava/util/List;", "cameraIds", "sendCameraIdList", "(Lkotlinx/coroutines/channels/ProducerScope;Ljava/util/List;)V", "readCameraIds", "()Ljava/util/List;", _UrlKt.FRAGMENT_ENCODE_SET, "estimateMinInternalCameraCount", "(Landroid/content/pm/PackageManager;)I", "isValidCameraIds", "(Ljava/util/List;)Z", "Landroidx/camera/featurecombinationquery/CameraDeviceSetupCompat;", "getOrInitializeDeviceSetupCompat-0r8Bogc", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getOrInitializeDeviceSetupCompat", "Landroidx/camera/camera2/pipe/compat/Camera2DeviceSetupWrapper;", "getOrInitializeDeviceSetupWrapper-0r8Bogc", "getOrInitializeDeviceSetupWrapper", "awaitCameraIds", _UrlKt.FRAGMENT_ENCODE_SET, "awaitConcurrentCameraIds", "()Ljava/util/Set;", "shutdown", "()V", "Ljavax/inject/Provider;", "Landroidx/camera/camera2/pipe/core/Threads;", "Landroid/content/Context;", "Landroidx/camera/camera2/pipe/internal/CameraErrorListener;", "Lkotlinx/coroutines/CoroutineScope;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "lock", "Ljava/lang/Object;", "openableCameras", "Ljava/util/List;", "concurrentCameras", "Ljava/util/Set;", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/Deferred;", "cameraDeviceSetupCache", "Ljava/util/Map;", "camera2DeviceSetupWrapperCache", "minimumCameraCount", "I", "Lkotlinx/coroutines/flow/Flow;", "getCameraIds", "kotlin.jvm.PlatformType", "cameraDeviceSetupCompatFactory$delegate", "Lkotlin/Lazy;", "getCameraDeviceSetupCompatFactory", "()Landroidx/camera/featurecombinationquery/CameraDeviceSetupCompatFactory;", "cameraDeviceSetupCompatFactory", "camera-camera2-pipe"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nCamera2DeviceCache.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Camera2DeviceCache.kt\nandroidx/camera/camera2/pipe/compat/Camera2DeviceCache\n+ 2 Log.kt\nandroidx/camera/camera2/pipe/core/Log\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 5 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 6 Channel.kt\nkotlinx/coroutines/channels/ChannelKt\n+ 7 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,391:1\n50#2,2:392\n50#2,2:401\n50#2,2:411\n59#2,2:416\n59#2,2:421\n50#2,2:423\n82#2,2:426\n75#2,2:429\n75#2,2:431\n75#2,2:433\n59#2,2:448\n71#2,2:450\n50#2,2:452\n75#2,2:454\n384#3,7:394\n384#3,7:404\n1#4:403\n1#4:445\n1740#5,3:413\n1761#5,3:418\n1563#5:456\n1634#5,3:457\n544#6:425\n545#6:428\n11546#7,9:435\n13472#7:444\n13473#7:446\n11555#7:447\n*S KotlinDebug\n*F\n+ 1 Camera2DeviceCache.kt\nandroidx/camera/camera2/pipe/compat/Camera2DeviceCache\n*L\n93#1:392,2\n133#1:401,2\n172#1:411,2\n247#1:416,2\n253#1:421,2\n282#1:423,2\n284#1:426,2\n297#1:429,2\n301#1:431,2\n309#1:433,2\n319#1:448,2\n321#1:450,2\n376#1:452,2\n379#1:454,2\n118#1:394,7\n149#1:404,7\n315#1:445\n246#1:413,3\n251#1:418,3\n383#1:456\n383#1:457,3\n283#1:425\n283#1:428\n315#1:435,9\n315#1:444\n315#1:446\n315#1:447\n*E\n"})
public final class Camera2DeviceCache {
    private final Map<CameraId, Deferred<Camera2DeviceSetupWrapper>> camera2DeviceSetupWrapperCache;
    private final Map<CameraId, Deferred<CameraDeviceSetupCompat>> cameraDeviceSetupCache;

    /* JADX INFO: renamed from: cameraDeviceSetupCompatFactory$delegate, reason: from kotlin metadata */
    private final Lazy cameraDeviceSetupCompatFactory;
    private final Provider<CameraDeviceSetupCompatFactory> cameraDeviceSetupCompatFactoryProvider;
    private final CameraErrorListener cameraErrorListener;
    private final Flow<List<CameraId>> cameraIds;
    private final Provider<CameraManager> cameraManager;
    private Set<? extends Set<CameraId>> concurrentCameras;
    private final Context context;
    private final Object lock;
    private final int minimumCameraCount;
    private List<CameraId> openableCameras;
    private final CoroutineScope scope;
    private final Threads threads;

    public Camera2DeviceCache(Provider<CameraManager> provider, Threads threads, Context context, PackageManager packageManager, CameraErrorListener cameraErrorListener, Provider<CameraDeviceSetupCompatFactory> provider2, CameraPipeLifetime cameraPipeLifetime, Job job) {
        this.cameraManager = provider;
        this.threads = threads;
        this.context = context;
        this.cameraErrorListener = cameraErrorListener;
        this.cameraDeviceSetupCompatFactoryProvider = provider2;
        CoroutineScope CoroutineScope = CoroutineScopeKt.CoroutineScope(SupervisorKt.SupervisorJob(job).plus(threads.getLightweightDispatcher()).plus(new CoroutineName("Camera2DeviceCache")));
        this.scope = CoroutineScope;
        this.lock = new Object();
        this.cameraDeviceSetupCache = new LinkedHashMap();
        this.camera2DeviceSetupWrapperCache = new LinkedHashMap();
        this.minimumCameraCount = estimateMinInternalCameraCount(packageManager);
        if (Log.INSTANCE.getDEBUG_LOGGABLE()) {
            android.util.Log.d("CXCP", "Camera2DeviceCache: Expected minimum camera count = " + this.minimumCameraCount);
        }
        cameraPipeLifetime.addShutdownAction(CameraPipeLifetime.ShutdownType.SCOPE, new Runnable() { // from class: androidx.camera.camera2.pipe.compat.Camera2DeviceCache$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CoroutineScopeKt.cancel$default(this.f$0.scope, null, 1, null);
            }
        });
        this.cameraIds = FlowKt.shareIn(FlowKt.distinctUntilChanged(createCameraIdListFlow()), CoroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.INSTANCE, 0L, 0L, 3, null), 1);
        this.cameraDeviceSetupCompatFactory = LazyKt.lazy(new Function0() { // from class: androidx.camera.camera2.pipe.compat.Camera2DeviceCache$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.cameraDeviceSetupCompatFactoryProvider.get();
            }
        });
    }

    public final Flow<List<CameraId>> getCameraIds() {
        return this.cameraIds;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final CameraDeviceSetupCompatFactory getCameraDeviceSetupCompatFactory() {
        return (CameraDeviceSetupCompatFactory) this.cameraDeviceSetupCompatFactory.getValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX INFO: renamed from: getOrInitializeDeviceSetupCompat-0r8Bogc, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object m1712getOrInitializeDeviceSetupCompat0r8Bogc(java.lang.String r14, kotlin.coroutines.Continuation<? super androidx.camera.featurecombinationquery.CameraDeviceSetupCompat> r15) {
        /*
            r13 = this;
            boolean r0 = r15 instanceof androidx.camera.camera2.pipe.compat.Camera2DeviceCache$getOrInitializeDeviceSetupCompat$1
            if (r0 == 0) goto L13
            r0 = r15
            androidx.camera.camera2.pipe.compat.Camera2DeviceCache$getOrInitializeDeviceSetupCompat$1 r0 = (androidx.camera.camera2.pipe.compat.Camera2DeviceCache$getOrInitializeDeviceSetupCompat$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.camera.camera2.pipe.compat.Camera2DeviceCache$getOrInitializeDeviceSetupCompat$1 r0 = new androidx.camera.camera2.pipe.compat.Camera2DeviceCache$getOrInitializeDeviceSetupCompat$1
            r0.<init>(r13, r15)
        L18:
            java.lang.Object r15 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L3a
            if (r2 != r4) goto L34
            java.lang.Object r14 = r0.L$1
            kotlinx.coroutines.Deferred r14 = (kotlinx.coroutines.Deferred) r14
            java.lang.Object r0 = r0.L$0
            java.lang.String r0 = (java.lang.String) r0
            kotlin.ResultKt.throwOnFailure(r15)
            r2 = r14
            r14 = r0
            goto L7f
        L34:
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r13)
            return r3
        L3a:
            kotlin.ResultKt.throwOnFailure(r15)
            int r15 = android.os.Build.VERSION.SDK_INT
            r2 = 35
            if (r15 >= r2) goto L44
            return r3
        L44:
            java.lang.Object r15 = r13.lock
            monitor-enter(r15)
            java.util.Map<androidx.camera.camera2.pipe.CameraId, kotlinx.coroutines.Deferred<androidx.camera.featurecombinationquery.CameraDeviceSetupCompat>> r2 = r13.cameraDeviceSetupCache     // Catch: java.lang.Throwable -> L6b
            androidx.camera.camera2.pipe.CameraId r5 = androidx.camera.camera2.pipe.CameraId.m1496boximpl(r14)     // Catch: java.lang.Throwable -> L6b
            java.lang.Object r6 = r2.get(r5)     // Catch: java.lang.Throwable -> L6b
            if (r6 != 0) goto L6e
            kotlinx.coroutines.CoroutineScope r7 = r13.scope     // Catch: java.lang.Throwable -> L6b
            androidx.camera.camera2.pipe.core.Threads r6 = r13.threads     // Catch: java.lang.Throwable -> L6b
            kotlinx.coroutines.CoroutineDispatcher r8 = r6.getBackgroundDispatcher()     // Catch: java.lang.Throwable -> L6b
            androidx.camera.camera2.pipe.compat.Camera2DeviceCache$getOrInitializeDeviceSetupCompat$deferred$1$1$1 r10 = new androidx.camera.camera2.pipe.compat.Camera2DeviceCache$getOrInitializeDeviceSetupCompat$deferred$1$1$1     // Catch: java.lang.Throwable -> L6b
            r10.<init>(r14, r13, r3)     // Catch: java.lang.Throwable -> L6b
            r11 = 2
            r12 = 0
            r9 = 0
            kotlinx.coroutines.Deferred r6 = kotlinx.coroutines.BuildersKt.async$default(r7, r8, r9, r10, r11, r12)     // Catch: java.lang.Throwable -> L6b
            r2.put(r5, r6)     // Catch: java.lang.Throwable -> L6b
            goto L6e
        L6b:
            r0 = move-exception
            r13 = r0
            goto Lb5
        L6e:
            r2 = r6
            kotlinx.coroutines.Deferred r2 = (kotlinx.coroutines.Deferred) r2     // Catch: java.lang.Throwable -> L6b
            monitor-exit(r15)
            r0.L$0 = r14
            r0.L$1 = r2
            r0.label = r4
            java.lang.Object r15 = r2.await(r0)
            if (r15 != r1) goto L7f
            return r1
        L7f:
            androidx.camera.featurecombinationquery.CameraDeviceSetupCompat r15 = (androidx.camera.featurecombinationquery.CameraDeviceSetupCompat) r15
            if (r15 != 0) goto Lb4
            androidx.camera.camera2.pipe.core.Log r0 = androidx.camera.camera2.pipe.core.Log.INSTANCE
            boolean r0 = r0.getDEBUG_LOGGABLE()
            if (r0 == 0) goto La2
            java.lang.String r0 = "CXCP"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r3 = "Removing null CameraDeviceSetupCompat from cache for "
            r1.<init>(r3)
            java.lang.String r3 = androidx.camera.camera2.pipe.CameraId.m1501toStringimpl(r14)
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r0, r1)
        La2:
            java.lang.Object r1 = r13.lock
            monitor-enter(r1)
            java.util.Map<androidx.camera.camera2.pipe.CameraId, kotlinx.coroutines.Deferred<androidx.camera.featurecombinationquery.CameraDeviceSetupCompat>> r13 = r13.cameraDeviceSetupCache     // Catch: java.lang.Throwable -> Lb0
            androidx.camera.camera2.pipe.CameraId r14 = androidx.camera.camera2.pipe.CameraId.m1496boximpl(r14)     // Catch: java.lang.Throwable -> Lb0
            r13.remove(r14, r2)     // Catch: java.lang.Throwable -> Lb0
            monitor-exit(r1)
            return r15
        Lb0:
            r0 = move-exception
            r13 = r0
            monitor-exit(r1)
            throw r13
        Lb4:
            return r15
        Lb5:
            monitor-exit(r15)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.compat.Camera2DeviceCache.m1712getOrInitializeDeviceSetupCompat0r8Bogc(java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX INFO: renamed from: getOrInitializeDeviceSetupWrapper-0r8Bogc, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object m1713getOrInitializeDeviceSetupWrapper0r8Bogc(java.lang.String r14, kotlin.coroutines.Continuation<? super androidx.camera.camera2.pipe.compat.Camera2DeviceSetupWrapper> r15) {
        /*
            r13 = this;
            boolean r0 = r15 instanceof androidx.camera.camera2.pipe.compat.Camera2DeviceCache$getOrInitializeDeviceSetupWrapper$1
            if (r0 == 0) goto L13
            r0 = r15
            androidx.camera.camera2.pipe.compat.Camera2DeviceCache$getOrInitializeDeviceSetupWrapper$1 r0 = (androidx.camera.camera2.pipe.compat.Camera2DeviceCache$getOrInitializeDeviceSetupWrapper$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.camera.camera2.pipe.compat.Camera2DeviceCache$getOrInitializeDeviceSetupWrapper$1 r0 = new androidx.camera.camera2.pipe.compat.Camera2DeviceCache$getOrInitializeDeviceSetupWrapper$1
            r0.<init>(r13, r15)
        L18:
            java.lang.Object r15 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L3a
            if (r2 != r4) goto L34
            java.lang.Object r14 = r0.L$1
            kotlinx.coroutines.Deferred r14 = (kotlinx.coroutines.Deferred) r14
            java.lang.Object r0 = r0.L$0
            java.lang.String r0 = (java.lang.String) r0
            kotlin.ResultKt.throwOnFailure(r15)
            r2 = r14
            r14 = r0
            goto L78
        L34:
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r13)
            return r3
        L3a:
            kotlin.ResultKt.throwOnFailure(r15)
            java.lang.Object r15 = r13.lock
            monitor-enter(r15)
            java.util.Map<androidx.camera.camera2.pipe.CameraId, kotlinx.coroutines.Deferred<androidx.camera.camera2.pipe.compat.Camera2DeviceSetupWrapper>> r2 = r13.camera2DeviceSetupWrapperCache     // Catch: java.lang.Throwable -> L64
            androidx.camera.camera2.pipe.CameraId r5 = androidx.camera.camera2.pipe.CameraId.m1496boximpl(r14)     // Catch: java.lang.Throwable -> L64
            java.lang.Object r6 = r2.get(r5)     // Catch: java.lang.Throwable -> L64
            if (r6 != 0) goto L67
            kotlinx.coroutines.CoroutineScope r7 = r13.scope     // Catch: java.lang.Throwable -> L64
            androidx.camera.camera2.pipe.core.Threads r6 = r13.threads     // Catch: java.lang.Throwable -> L64
            kotlinx.coroutines.CoroutineDispatcher r8 = r6.getBackgroundDispatcher()     // Catch: java.lang.Throwable -> L64
            androidx.camera.camera2.pipe.compat.Camera2DeviceCache$getOrInitializeDeviceSetupWrapper$deferred$1$1$1 r10 = new androidx.camera.camera2.pipe.compat.Camera2DeviceCache$getOrInitializeDeviceSetupWrapper$deferred$1$1$1     // Catch: java.lang.Throwable -> L64
            r10.<init>(r14, r13, r3)     // Catch: java.lang.Throwable -> L64
            r11 = 2
            r12 = 0
            r9 = 0
            kotlinx.coroutines.Deferred r6 = kotlinx.coroutines.BuildersKt.async$default(r7, r8, r9, r10, r11, r12)     // Catch: java.lang.Throwable -> L64
            r2.put(r5, r6)     // Catch: java.lang.Throwable -> L64
            goto L67
        L64:
            r0 = move-exception
            r13 = r0
            goto Lae
        L67:
            r2 = r6
            kotlinx.coroutines.Deferred r2 = (kotlinx.coroutines.Deferred) r2     // Catch: java.lang.Throwable -> L64
            monitor-exit(r15)
            r0.L$0 = r14
            r0.L$1 = r2
            r0.label = r4
            java.lang.Object r15 = r2.await(r0)
            if (r15 != r1) goto L78
            return r1
        L78:
            androidx.camera.camera2.pipe.compat.Camera2DeviceSetupWrapper r15 = (androidx.camera.camera2.pipe.compat.Camera2DeviceSetupWrapper) r15
            if (r15 != 0) goto Lad
            androidx.camera.camera2.pipe.core.Log r0 = androidx.camera.camera2.pipe.core.Log.INSTANCE
            boolean r0 = r0.getDEBUG_LOGGABLE()
            if (r0 == 0) goto L9b
            java.lang.String r0 = "CXCP"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r3 = "Removing null camera2DeviceSetupWrapper from cache for "
            r1.<init>(r3)
            java.lang.String r3 = androidx.camera.camera2.pipe.CameraId.m1501toStringimpl(r14)
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r0, r1)
        L9b:
            java.lang.Object r1 = r13.lock
            monitor-enter(r1)
            java.util.Map<androidx.camera.camera2.pipe.CameraId, kotlinx.coroutines.Deferred<androidx.camera.camera2.pipe.compat.Camera2DeviceSetupWrapper>> r13 = r13.camera2DeviceSetupWrapperCache     // Catch: java.lang.Throwable -> La9
            androidx.camera.camera2.pipe.CameraId r14 = androidx.camera.camera2.pipe.CameraId.m1496boximpl(r14)     // Catch: java.lang.Throwable -> La9
            r13.remove(r14, r2)     // Catch: java.lang.Throwable -> La9
            monitor-exit(r1)
            return r15
        La9:
            r0 = move-exception
            r13 = r0
            monitor-exit(r1)
            throw r13
        Lad:
            return r15
        Lae:
            monitor-exit(r15)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.compat.Camera2DeviceCache.m1713getOrInitializeDeviceSetupWrapper0r8Bogc(java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final List<CameraId> awaitCameraIds() {
        List<CameraId> list;
        synchronized (this.lock) {
            list = this.openableCameras;
        }
        return list != null ? list : readCameraIds();
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.pipe.compat.Camera2DeviceCache$createCameraIdListFlow$1 */
    @Metadata(m876d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/channels/ProducerScope;", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/camera2/pipe/CameraId;"}, m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.pipe.compat.Camera2DeviceCache$createCameraIdListFlow$1", m896f = "Camera2DeviceCache.kt", m897i = {}, m898l = {235}, m899m = "invokeSuspend", m900n = {}, m902s = {}, m903v = 1)
    @SourceDebugExtension({"SMAP\nCamera2DeviceCache.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Camera2DeviceCache.kt\nandroidx/camera/camera2/pipe/compat/Camera2DeviceCache$createCameraIdListFlow$1\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,391:1\n1#2:392\n*E\n"})
    public static final class C02031 extends SuspendLambda implements Function2<ProducerScope<? super List<? extends CameraId>>, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        public C02031(Continuation<? super C02031> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C02031 c02031 = Camera2DeviceCache.this.new C02031(continuation);
            c02031.L$0 = obj;
            return c02031;
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(ProducerScope<? super List<? extends CameraId>> producerScope, Continuation<? super Unit> continuation) {
            return invoke2((ProducerScope<? super List<CameraId>>) producerScope, continuation);
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final Object invoke2(ProducerScope<? super List<CameraId>> producerScope, Continuation<? super Unit> continuation) {
            return ((C02031) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v1, types: [android.hardware.camera2.CameraManager$AvailabilityCallback, androidx.camera.camera2.pipe.compat.Camera2DeviceCache$createCameraIdListFlow$1$callback$1] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            List list;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                final Camera2DeviceCache camera2DeviceCache = Camera2DeviceCache.this;
                final ?? r1 = new CameraManager.AvailabilityCallback() { // from class: androidx.camera.camera2.pipe.compat.Camera2DeviceCache$createCameraIdListFlow$1$callback$1
                    @Override // android.hardware.camera2.CameraManager.AvailabilityCallback
                    public void onCameraAvailable(String cameraId) {
                        camera2DeviceCache.onCameraAvailabilityChanged(producerScope, cameraId, true);
                    }

                    @Override // android.hardware.camera2.CameraManager.AvailabilityCallback
                    public void onCameraUnavailable(String cameraId) {
                        camera2DeviceCache.onCameraAvailabilityChanged(producerScope, cameraId, false);
                    }
                };
                final CameraManager cameraManager = (CameraManager) Camera2DeviceCache.this.cameraManager.get();
                cameraManager.registerAvailabilityCallback((CameraManager.AvailabilityCallback) r1, Camera2DeviceCache.this.threads.getCamera2Handler());
                Object obj2 = Camera2DeviceCache.this.lock;
                Camera2DeviceCache camera2DeviceCache2 = Camera2DeviceCache.this;
                synchronized (obj2) {
                    list = camera2DeviceCache2.openableCameras;
                }
                Camera2DeviceCache camera2DeviceCache3 = Camera2DeviceCache.this;
                if (list != null) {
                    camera2DeviceCache3.sendCameraIdList(producerScope, list);
                } else {
                    List cameraIds = camera2DeviceCache3.readCameraIds();
                    if (cameraIds != null) {
                        Camera2DeviceCache.this.sendCameraIdList(producerScope, cameraIds);
                    }
                }
                Function0 function0 = new Function0() { // from class: androidx.camera.camera2.pipe.compat.Camera2DeviceCache$createCameraIdListFlow$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return Camera2DeviceCache.C02031.$r8$lambda$iZGqJgau6RNOY255ntk5AHacrdw(cameraManager, r1);
                    }
                };
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, function0, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    Segment$$ExternalSyntheticBUOutline1.m992m("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        public static Unit $r8$lambda$iZGqJgau6RNOY255ntk5AHacrdw(CameraManager cameraManager, Camera2DeviceCache$createCameraIdListFlow$1$callback$1 camera2DeviceCache$createCameraIdListFlow$1$callback$1) {
            cameraManager.unregisterAvailabilityCallback(camera2DeviceCache$createCameraIdListFlow$1$callback$1);
            return Unit.INSTANCE;
        }
    }

    private final Flow<List<CameraId>> createCameraIdListFlow() {
        return FlowKt.callbackFlow(new C02031(null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0095  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCameraAvailabilityChanged(kotlinx.coroutines.channels.ProducerScope<? super java.util.List<androidx.camera.camera2.pipe.CameraId>> r4, java.lang.String r5, boolean r6) {
        /*
            r3 = this;
            java.lang.Object r0 = r3.lock
            monitor-enter(r0)
            java.util.List<androidx.camera.camera2.pipe.CameraId> r1 = r3.openableCameras     // Catch: java.lang.Throwable -> Lbf
            monitor-exit(r0)
            r0 = 1
            r2 = 0
            if (r6 != r0) goto L5e
            if (r1 == 0) goto L39
            r6 = r1
            java.lang.Iterable r6 = (java.lang.Iterable) r6
            boolean r0 = r6 instanceof java.util.Collection
            if (r0 == 0) goto L1d
            r0 = r6
            java.util.Collection r0 = (java.util.Collection) r0
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L1d
            goto L39
        L1d:
            java.util.Iterator r6 = r6.iterator()
        L21:
            boolean r0 = r6.hasNext()
            if (r0 == 0) goto L39
            java.lang.Object r0 = r6.next()
            androidx.camera.camera2.pipe.CameraId r0 = (androidx.camera.camera2.pipe.CameraId) r0
            java.lang.String r0 = r0.getValue()
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r5)
            if (r0 == 0) goto L21
            goto Lb1
        L39:
            androidx.camera.camera2.pipe.core.Log r6 = androidx.camera.camera2.pipe.core.Log.INSTANCE
            boolean r6 = r6.getINFO_LOGGABLE()
            if (r6 == 0) goto L59
            java.lang.String r6 = "CXCP"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "New camera "
            r0.<init>(r2)
            r0.append(r5)
            java.lang.String r5 = " detected"
            r0.append(r5)
            java.lang.String r5 = r0.toString()
            android.util.Log.i(r6, r5)
        L59:
            java.util.List r2 = r3.readCameraIds()
            goto Lb1
        L5e:
            if (r6 != 0) goto Lbb
            if (r1 == 0) goto L8d
            r6 = r1
            java.lang.Iterable r6 = (java.lang.Iterable) r6
            boolean r0 = r6 instanceof java.util.Collection
            if (r0 == 0) goto L73
            r0 = r6
            java.util.Collection r0 = (java.util.Collection) r0
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L73
            goto Lb1
        L73:
            java.util.Iterator r6 = r6.iterator()
        L77:
            boolean r0 = r6.hasNext()
            if (r0 == 0) goto Lb1
            java.lang.Object r0 = r6.next()
            androidx.camera.camera2.pipe.CameraId r0 = (androidx.camera.camera2.pipe.CameraId) r0
            java.lang.String r0 = r0.getValue()
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r5)
            if (r0 == 0) goto L77
        L8d:
            androidx.camera.camera2.pipe.core.Log r6 = androidx.camera.camera2.pipe.core.Log.INSTANCE
            boolean r6 = r6.getINFO_LOGGABLE()
            if (r6 == 0) goto Lad
            java.lang.String r6 = "CXCP"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "Unavailable camera "
            r0.<init>(r2)
            r0.append(r5)
            java.lang.String r5 = " detected"
            r0.append(r5)
            java.lang.String r5 = r0.toString()
            android.util.Log.i(r6, r5)
        Lad:
            java.util.List r2 = r3.readCameraIds()
        Lb1:
            java.util.List r5 = r3.getUpdatedCameraIds(r1, r2)
            if (r5 == 0) goto Lba
            r3.sendCameraIdList(r4, r5)
        Lba:
            return
        Lbb:
            kotlin.LazyKt__LazyJVMKt$$ExternalSyntheticBUOutline0.m874m()
            return
        Lbf:
            r3 = move-exception
            monitor-exit(r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.compat.Camera2DeviceCache.onCameraAvailabilityChanged(kotlinx.coroutines.channels.ProducerScope, java.lang.String, boolean):void");
    }

    private final List<CameraId> getUpdatedCameraIds(List<CameraId> cachedCameraIds, List<CameraId> cameraIdsRead) {
        return (cameraIdsRead == null || !(isValidCameraIds(cameraIdsRead) || cachedCameraIds == null)) ? cachedCameraIds : cameraIdsRead;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void sendCameraIdList(ProducerScope<? super List<CameraId>> producerScope, List<CameraId> list) {
        Log log = Log.INSTANCE;
        if (log.getDEBUG_LOGGABLE()) {
            android.util.Log.d("CXCP", "Emitting camera ID list: " + list);
        }
        Object objTrySendBlocking = ChannelsKt.trySendBlocking(producerScope, list);
        if (objTrySendBlocking instanceof ChannelResult.Failed) {
            ChannelResult.m5016exceptionOrNullimpl(objTrySendBlocking);
            if (log.getERROR_LOGGABLE()) {
                android.util.Log.e("CXCP", "Failed to send camera ID list: " + list + '!');
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<CameraId> readCameraIds() {
        try {
            String[] cameraIdList = this.cameraManager.get().getCameraIdList();
            ArrayList arrayList = new ArrayList();
            for (String str : cameraIdList) {
                String strM1497constructorimpl = CameraId.m1497constructorimpl(str);
                CameraId cameraIdM1496boximpl = strM1497constructorimpl != null ? CameraId.m1496boximpl(strM1497constructorimpl) : null;
                if (cameraIdM1496boximpl != null) {
                    arrayList.add(cameraIdM1496boximpl);
                }
            }
            if (isValidCameraIds(arrayList)) {
                synchronized (this.lock) {
                    this.openableCameras = arrayList;
                    Unit unit = Unit.INSTANCE;
                }
                if (Log.INSTANCE.getINFO_LOGGABLE()) {
                    android.util.Log.i("CXCP", "Loaded CameraIdList " + arrayList);
                    return arrayList;
                }
            } else if (Log.INSTANCE.getWARN_LOGGABLE()) {
                android.util.Log.w("CXCP", "Failed to query camera ID list: Invalid list returned: " + arrayList + '.');
            }
            return arrayList;
        } catch (CameraAccessException e) {
            if (Log.INSTANCE.getWARN_LOGGABLE()) {
                android.util.Log.w("CXCP", "Failed to query CameraManager#getCameraIdList!", e);
            }
            return null;
        } catch (ArrayIndexOutOfBoundsException e2) {
            if (Log.INSTANCE.getWARN_LOGGABLE()) {
                android.util.Log.w("CXCP", "Failed to query CameraManager#getCameraIdList!Unexpected ArrayIndexOutOfBoundsException thrown by framework.", e2);
            }
            return null;
        } catch (NullPointerException e3) {
            if (Log.INSTANCE.getWARN_LOGGABLE()) {
                android.util.Log.w("CXCP", "Failed to query CameraManager#getCameraIdList!Null was returned by framework.", e3);
            }
            return null;
        }
    }

    private final int estimateMinInternalCameraCount(PackageManager packageManager) {
        boolean zHasSystemFeature = packageManager.hasSystemFeature("android.hardware.camera");
        return packageManager.hasSystemFeature("android.hardware.camera.front") ? (zHasSystemFeature ? 1 : 0) + 1 : zHasSystemFeature ? 1 : 0;
    }

    private final boolean isValidCameraIds(List<CameraId> cameraIds) {
        return cameraIds.size() >= this.minimumCameraCount;
    }

    public final Set<Set<CameraId>> awaitConcurrentCameraIds() {
        Set set;
        if (Build.VERSION.SDK_INT < 30) {
            return SetsKt.emptySet();
        }
        synchronized (this.lock) {
            set = this.concurrentCameras;
        }
        if (set != null && !set.isEmpty()) {
            return set;
        }
        try {
            Set<Set<String>> concurrentCameraIds = Api30Compat.getConcurrentCameraIds(this.cameraManager.get());
            if (Log.INSTANCE.getDEBUG_LOGGABLE()) {
                android.util.Log.d("CXCP", "Loaded ConcurrentCameraIdsSet " + concurrentCameraIds);
            }
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(concurrentCameraIds, 10));
            Iterator<T> it = concurrentCameraIds.iterator();
            while (it.hasNext()) {
                Set set2 = (Set) it.next();
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(set2, 10));
                Iterator it2 = set2.iterator();
                while (it2.hasNext()) {
                    arrayList2.add(CameraId.m1496boximpl(CameraId.m1497constructorimpl((String) it2.next())));
                }
                arrayList.add(CollectionsKt.toSet(arrayList2));
            }
            return CollectionsKt.toSet(arrayList);
        } catch (CameraAccessException e) {
            if (!Log.INSTANCE.getWARN_LOGGABLE()) {
                return null;
            }
            android.util.Log.w("CXCP", "Failed to query CameraManager#getConcurrentStreamingCameraIds", e);
            return null;
        }
    }

    public final void shutdown() {
        CoroutineScopeKt.cancel$default(this.scope, null, 1, null);
    }
}
