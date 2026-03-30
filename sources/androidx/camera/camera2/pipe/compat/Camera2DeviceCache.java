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
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineName;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.channels.ChannelsKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SharingStarted;

/* JADX INFO: loaded from: classes3.dex */
public final class Camera2DeviceCache {
    private final Map camera2DeviceSetupWrapperCache;
    private final Map cameraDeviceSetupCache;
    private final Lazy cameraDeviceSetupCompatFactory$delegate;
    private final Provider cameraDeviceSetupCompatFactoryProvider;
    private final CameraErrorListener cameraErrorListener;
    private final Flow cameraIds;
    private final Provider cameraManager;
    private Set concurrentCameras;
    private final Context context;
    private final Object lock;
    private final int minimumCameraCount;
    private List openableCameras;
    private final CoroutineScope scope;
    private final Threads threads;

    public Camera2DeviceCache(Provider cameraManager, Threads threads, Context context, PackageManager packageManager, CameraErrorListener cameraErrorListener, Provider cameraDeviceSetupCompatFactoryProvider, CameraPipeLifetime cameraPipeLifetime, Job cameraPipeJob) {
        Intrinsics.checkNotNullParameter(cameraManager, "cameraManager");
        Intrinsics.checkNotNullParameter(threads, "threads");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(packageManager, "packageManager");
        Intrinsics.checkNotNullParameter(cameraErrorListener, "cameraErrorListener");
        Intrinsics.checkNotNullParameter(cameraDeviceSetupCompatFactoryProvider, "cameraDeviceSetupCompatFactoryProvider");
        Intrinsics.checkNotNullParameter(cameraPipeLifetime, "cameraPipeLifetime");
        Intrinsics.checkNotNullParameter(cameraPipeJob, "cameraPipeJob");
        this.cameraManager = cameraManager;
        this.threads = threads;
        this.context = context;
        this.cameraErrorListener = cameraErrorListener;
        this.cameraDeviceSetupCompatFactoryProvider = cameraDeviceSetupCompatFactoryProvider;
        CoroutineScope CoroutineScope = CoroutineScopeKt.CoroutineScope(SupervisorKt.SupervisorJob(cameraPipeJob).plus(threads.getLightweightDispatcher()).plus(new CoroutineName("Camera2DeviceCache")));
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
                Camera2DeviceCache._init_$lambda$1(this.f$0);
            }
        });
        this.cameraIds = FlowKt.shareIn(FlowKt.distinctUntilChanged(createCameraIdListFlow()), CoroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 0L, 0L, 3, null), 1);
        this.cameraDeviceSetupCompatFactory$delegate = LazyKt.lazy(new Function0() { // from class: androidx.camera.camera2.pipe.compat.Camera2DeviceCache$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Camera2DeviceCache.cameraDeviceSetupCompatFactory_delegate$lambda$0(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$1(Camera2DeviceCache camera2DeviceCache) {
        CoroutineScopeKt.cancel$default(camera2DeviceCache.scope, null, 1, null);
    }

    public final Flow getCameraIds() {
        return this.cameraIds;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final CameraDeviceSetupCompatFactory getCameraDeviceSetupCompatFactory() {
        return (CameraDeviceSetupCompatFactory) this.cameraDeviceSetupCompatFactory$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CameraDeviceSetupCompatFactory cameraDeviceSetupCompatFactory_delegate$lambda$0(Camera2DeviceCache camera2DeviceCache) {
        return (CameraDeviceSetupCompatFactory) camera2DeviceCache.cameraDeviceSetupCompatFactoryProvider.get();
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX INFO: renamed from: getOrInitializeDeviceSetupCompat-0r8Bogc, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object m1828getOrInitializeDeviceSetupCompat0r8Bogc(java.lang.String r14, kotlin.coroutines.Continuation r15) {
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
            r3 = 1
            if (r2 == 0) goto L3b
            if (r2 != r3) goto L33
            java.lang.Object r14 = r0.L$1
            kotlinx.coroutines.Deferred r14 = (kotlinx.coroutines.Deferred) r14
            java.lang.Object r0 = r0.L$0
            java.lang.String r0 = (java.lang.String) r0
            kotlin.ResultKt.throwOnFailure(r15)
            r2 = r14
            r14 = r0
            goto L81
        L33:
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
            java.lang.String r15 = "call to 'resume' before 'invoke' with coroutine"
            r14.<init>(r15)
            throw r14
        L3b:
            kotlin.ResultKt.throwOnFailure(r15)
            int r15 = android.os.Build.VERSION.SDK_INT
            r2 = 35
            r4 = 0
            if (r15 >= r2) goto L46
            return r4
        L46:
            java.lang.Object r15 = r13.lock
            monitor-enter(r15)
            java.util.Map r2 = r13.cameraDeviceSetupCache     // Catch: java.lang.Throwable -> L6d
            androidx.camera.camera2.pipe.CameraId r5 = androidx.camera.camera2.pipe.CameraId.m1602boximpl(r14)     // Catch: java.lang.Throwable -> L6d
            java.lang.Object r6 = r2.get(r5)     // Catch: java.lang.Throwable -> L6d
            if (r6 != 0) goto L70
            kotlinx.coroutines.CoroutineScope r7 = r13.scope     // Catch: java.lang.Throwable -> L6d
            androidx.camera.camera2.pipe.core.Threads r6 = r13.threads     // Catch: java.lang.Throwable -> L6d
            kotlinx.coroutines.CoroutineDispatcher r8 = r6.getBackgroundDispatcher()     // Catch: java.lang.Throwable -> L6d
            androidx.camera.camera2.pipe.compat.Camera2DeviceCache$getOrInitializeDeviceSetupCompat$deferred$1$1$1 r10 = new androidx.camera.camera2.pipe.compat.Camera2DeviceCache$getOrInitializeDeviceSetupCompat$deferred$1$1$1     // Catch: java.lang.Throwable -> L6d
            r10.<init>(r14, r13, r4)     // Catch: java.lang.Throwable -> L6d
            r11 = 2
            r12 = 0
            r9 = 0
            kotlinx.coroutines.Deferred r6 = kotlinx.coroutines.BuildersKt.async$default(r7, r8, r9, r10, r11, r12)     // Catch: java.lang.Throwable -> L6d
            r2.put(r5, r6)     // Catch: java.lang.Throwable -> L6d
            goto L70
        L6d:
            r0 = move-exception
            r14 = r0
            goto Lba
        L70:
            r2 = r6
            kotlinx.coroutines.Deferred r2 = (kotlinx.coroutines.Deferred) r2     // Catch: java.lang.Throwable -> L6d
            monitor-exit(r15)
            r0.L$0 = r14
            r0.L$1 = r2
            r0.label = r3
            java.lang.Object r15 = r2.await(r0)
            if (r15 != r1) goto L81
            return r1
        L81:
            androidx.camera.featurecombinationquery.CameraDeviceSetupCompat r15 = (androidx.camera.featurecombinationquery.CameraDeviceSetupCompat) r15
            if (r15 != 0) goto Lb9
            androidx.camera.camera2.pipe.core.Log r0 = androidx.camera.camera2.pipe.core.Log.INSTANCE
            boolean r0 = r0.getDEBUG_LOGGABLE()
            if (r0 == 0) goto La7
            java.lang.String r0 = "CXCP"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "Removing null CameraDeviceSetupCompat from cache for "
            r1.append(r3)
            java.lang.String r3 = androidx.camera.camera2.pipe.CameraId.m1607toStringimpl(r14)
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r0, r1)
        La7:
            java.lang.Object r1 = r13.lock
            monitor-enter(r1)
            java.util.Map r0 = r13.cameraDeviceSetupCache     // Catch: java.lang.Throwable -> Lb5
            androidx.camera.camera2.pipe.CameraId r14 = androidx.camera.camera2.pipe.CameraId.m1602boximpl(r14)     // Catch: java.lang.Throwable -> Lb5
            p022j$.util.Map.EL.remove(r0, r14, r2)     // Catch: java.lang.Throwable -> Lb5
            monitor-exit(r1)
            return r15
        Lb5:
            r0 = move-exception
            r14 = r0
            monitor-exit(r1)
            throw r14
        Lb9:
            return r15
        Lba:
            monitor-exit(r15)
            throw r14
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.compat.Camera2DeviceCache.m1828getOrInitializeDeviceSetupCompat0r8Bogc(java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX INFO: renamed from: getOrInitializeDeviceSetupWrapper-0r8Bogc, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object m1829getOrInitializeDeviceSetupWrapper0r8Bogc(java.lang.String r13, kotlin.coroutines.Continuation r14) {
        /*
            r12 = this;
            boolean r0 = r14 instanceof androidx.camera.camera2.pipe.compat.Camera2DeviceCache$getOrInitializeDeviceSetupWrapper$1
            if (r0 == 0) goto L13
            r0 = r14
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
            r0.<init>(r12, r14)
        L18:
            java.lang.Object r14 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3b
            if (r2 != r3) goto L33
            java.lang.Object r13 = r0.L$1
            kotlinx.coroutines.Deferred r13 = (kotlinx.coroutines.Deferred) r13
            java.lang.Object r0 = r0.L$0
            java.lang.String r0 = (java.lang.String) r0
            kotlin.ResultKt.throwOnFailure(r14)
            r2 = r13
            r13 = r0
            goto L7a
        L33:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r14 = "call to 'resume' before 'invoke' with coroutine"
            r13.<init>(r14)
            throw r13
        L3b:
            kotlin.ResultKt.throwOnFailure(r14)
            java.lang.Object r14 = r12.lock
            monitor-enter(r14)
            java.util.Map r2 = r12.camera2DeviceSetupWrapperCache     // Catch: java.lang.Throwable -> L66
            androidx.camera.camera2.pipe.CameraId r4 = androidx.camera.camera2.pipe.CameraId.m1602boximpl(r13)     // Catch: java.lang.Throwable -> L66
            java.lang.Object r5 = r2.get(r4)     // Catch: java.lang.Throwable -> L66
            if (r5 != 0) goto L69
            kotlinx.coroutines.CoroutineScope r6 = r12.scope     // Catch: java.lang.Throwable -> L66
            androidx.camera.camera2.pipe.core.Threads r5 = r12.threads     // Catch: java.lang.Throwable -> L66
            kotlinx.coroutines.CoroutineDispatcher r7 = r5.getBackgroundDispatcher()     // Catch: java.lang.Throwable -> L66
            androidx.camera.camera2.pipe.compat.Camera2DeviceCache$getOrInitializeDeviceSetupWrapper$deferred$1$1$1 r9 = new androidx.camera.camera2.pipe.compat.Camera2DeviceCache$getOrInitializeDeviceSetupWrapper$deferred$1$1$1     // Catch: java.lang.Throwable -> L66
            r5 = 0
            r9.<init>(r13, r12, r5)     // Catch: java.lang.Throwable -> L66
            r10 = 2
            r11 = 0
            r8 = 0
            kotlinx.coroutines.Deferred r5 = kotlinx.coroutines.BuildersKt.async$default(r6, r7, r8, r9, r10, r11)     // Catch: java.lang.Throwable -> L66
            r2.put(r4, r5)     // Catch: java.lang.Throwable -> L66
            goto L69
        L66:
            r0 = move-exception
            r13 = r0
            goto Lb3
        L69:
            r2 = r5
            kotlinx.coroutines.Deferred r2 = (kotlinx.coroutines.Deferred) r2     // Catch: java.lang.Throwable -> L66
            monitor-exit(r14)
            r0.L$0 = r13
            r0.L$1 = r2
            r0.label = r3
            java.lang.Object r14 = r2.await(r0)
            if (r14 != r1) goto L7a
            return r1
        L7a:
            androidx.camera.camera2.pipe.compat.Camera2DeviceSetupWrapper r14 = (androidx.camera.camera2.pipe.compat.Camera2DeviceSetupWrapper) r14
            if (r14 != 0) goto Lb2
            androidx.camera.camera2.pipe.core.Log r0 = androidx.camera.camera2.pipe.core.Log.INSTANCE
            boolean r0 = r0.getDEBUG_LOGGABLE()
            if (r0 == 0) goto La0
            java.lang.String r0 = "CXCP"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "Removing null camera2DeviceSetupWrapper from cache for "
            r1.append(r3)
            java.lang.String r3 = androidx.camera.camera2.pipe.CameraId.m1607toStringimpl(r13)
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r0, r1)
        La0:
            java.lang.Object r1 = r12.lock
            monitor-enter(r1)
            java.util.Map r0 = r12.camera2DeviceSetupWrapperCache     // Catch: java.lang.Throwable -> Lae
            androidx.camera.camera2.pipe.CameraId r13 = androidx.camera.camera2.pipe.CameraId.m1602boximpl(r13)     // Catch: java.lang.Throwable -> Lae
            p022j$.util.Map.EL.remove(r0, r13, r2)     // Catch: java.lang.Throwable -> Lae
            monitor-exit(r1)
            return r14
        Lae:
            r0 = move-exception
            r13 = r0
            monitor-exit(r1)
            throw r13
        Lb2:
            return r14
        Lb3:
            monitor-exit(r14)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.compat.Camera2DeviceCache.m1829getOrInitializeDeviceSetupWrapper0r8Bogc(java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final List awaitCameraIds() {
        List list;
        synchronized (this.lock) {
            list = this.openableCameras;
        }
        return list != null ? list : readCameraIds();
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.pipe.compat.Camera2DeviceCache$createCameraIdListFlow$1 */
    static final class C02051 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        C02051(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C02051 c02051 = Camera2DeviceCache.this.new C02051(continuation);
            c02051.L$0 = obj;
            return c02051;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope producerScope, Continuation continuation) {
            return ((C02051) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
                        Intrinsics.checkNotNullParameter(cameraId, "cameraId");
                        camera2DeviceCache.onCameraAvailabilityChanged(producerScope, cameraId, true);
                    }

                    @Override // android.hardware.camera2.CameraManager.AvailabilityCallback
                    public void onCameraUnavailable(String cameraId) {
                        Intrinsics.checkNotNullParameter(cameraId, "cameraId");
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
                if (list != null) {
                    Camera2DeviceCache.this.sendCameraIdList(producerScope, list);
                } else {
                    List cameraIds = Camera2DeviceCache.this.readCameraIds();
                    if (cameraIds != null) {
                        Camera2DeviceCache.this.sendCameraIdList(producerScope, cameraIds);
                    }
                }
                Function0 function0 = new Function0() { // from class: androidx.camera.camera2.pipe.compat.Camera2DeviceCache$createCameraIdListFlow$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return Camera2DeviceCache.C02051.invokeSuspend$lambda$1(cameraManager, r1);
                    }
                };
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, function0, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit invokeSuspend$lambda$1(CameraManager cameraManager, Camera2DeviceCache$createCameraIdListFlow$1$callback$1 camera2DeviceCache$createCameraIdListFlow$1$callback$1) {
            cameraManager.unregisterAvailabilityCallback(camera2DeviceCache$createCameraIdListFlow$1$callback$1);
            return Unit.INSTANCE;
        }
    }

    private final Flow createCameraIdListFlow() {
        return FlowKt.callbackFlow(new C02051(null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0098  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCameraAvailabilityChanged(kotlinx.coroutines.channels.ProducerScope r4, java.lang.String r5, boolean r6) {
        /*
            Method dump skipped, instruction units count: 202
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.compat.Camera2DeviceCache.onCameraAvailabilityChanged(kotlinx.coroutines.channels.ProducerScope, java.lang.String, boolean):void");
    }

    private final List getUpdatedCameraIds(List list, List list2) {
        return (list2 == null || !(isValidCameraIds(list2) || list == null)) ? list : list2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void sendCameraIdList(ProducerScope producerScope, List list) {
        Log log = Log.INSTANCE;
        if (log.getDEBUG_LOGGABLE()) {
            android.util.Log.d("CXCP", "Emitting camera ID list: " + list);
        }
        Object objTrySendBlocking = ChannelsKt.trySendBlocking(producerScope, list);
        if (objTrySendBlocking instanceof ChannelResult.Failed) {
            ChannelResult.m3676exceptionOrNullimpl(objTrySendBlocking);
            if (log.getERROR_LOGGABLE()) {
                android.util.Log.e("CXCP", "Failed to send camera ID list: " + list + '!');
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List readCameraIds() {
        try {
            String[] cameraIdList = ((CameraManager) this.cameraManager.get()).getCameraIdList();
            Intrinsics.checkNotNullExpressionValue(cameraIdList, "getCameraIdList(...)");
            ArrayList arrayList = new ArrayList();
            for (String str : cameraIdList) {
                Intrinsics.checkNotNull(str);
                String strM1603constructorimpl = CameraId.m1603constructorimpl(str);
                CameraId cameraIdM1602boximpl = strM1603constructorimpl != null ? CameraId.m1602boximpl(strM1603constructorimpl) : null;
                if (cameraIdM1602boximpl != null) {
                    arrayList.add(cameraIdM1602boximpl);
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

    private final boolean isValidCameraIds(List list) {
        return list.size() >= this.minimumCameraCount;
    }

    public final Set awaitConcurrentCameraIds() {
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
        CameraManager cameraManager = (CameraManager) this.cameraManager.get();
        try {
            Intrinsics.checkNotNull(cameraManager);
            Set<Set> concurrentCameraIds = Api30Compat.getConcurrentCameraIds(cameraManager);
            if (Log.INSTANCE.getDEBUG_LOGGABLE()) {
                android.util.Log.d("CXCP", "Loaded ConcurrentCameraIdsSet " + concurrentCameraIds);
            }
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(concurrentCameraIds, 10));
            for (Set set2 : concurrentCameraIds) {
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(set2, 10));
                Iterator it = set2.iterator();
                while (it.hasNext()) {
                    arrayList2.add(CameraId.m1602boximpl(CameraId.m1603constructorimpl((String) it.next())));
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
