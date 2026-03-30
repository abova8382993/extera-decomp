package androidx.camera.camera2.impl;

import android.util.Log;
import android.view.Surface;
import androidx.camera.camera2.adapter.SessionConfigAdapter;
import androidx.camera.camera2.compat.workaround.InactiveSurfaceCloser;
import androidx.camera.camera2.pipe.CameraGraph;
import androidx.camera.camera2.pipe.CameraPipe;
import androidx.camera.camera2.pipe.CameraSurfaceManager;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.DeferrableSurfaces;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.ListenableFutureKt;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Job;

/* JADX INFO: loaded from: classes3.dex */
public class UseCaseSurfaceManager implements CameraSurfaceManager.SurfaceListener {
    private final Map activeSurfaceMap;
    private final CameraPipe cameraPipe;
    private Map configuredSurfaceMap;
    private final InactiveSurfaceCloser inactiveSurfaceCloser;
    private final Object lock;
    private final SessionConfigAdapter sessionConfigAdapter;
    private Deferred setupDeferred;
    private CompletableDeferred stopDeferred;
    private final UseCaseThreads threads;

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.UseCaseSurfaceManager$awaitSetupCompletion$1 */
    static final class C01771 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C01771(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return UseCaseSurfaceManager.awaitSetupCompletion$suspendImpl(UseCaseSurfaceManager.this, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.UseCaseSurfaceManager$getSurfaces$1 */
    static final class C01781 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C01781(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return UseCaseSurfaceManager.this.getSurfaces(null, 0L, this);
        }
    }

    public Object awaitSetupCompletion(Continuation continuation) {
        return awaitSetupCompletion$suspendImpl(this, continuation);
    }

    public UseCaseSurfaceManager(UseCaseThreads threads, CameraPipe cameraPipe, InactiveSurfaceCloser inactiveSurfaceCloser, SessionConfigAdapter sessionConfigAdapter) {
        Intrinsics.checkNotNullParameter(threads, "threads");
        Intrinsics.checkNotNullParameter(cameraPipe, "cameraPipe");
        Intrinsics.checkNotNullParameter(inactiveSurfaceCloser, "inactiveSurfaceCloser");
        Intrinsics.checkNotNullParameter(sessionConfigAdapter, "sessionConfigAdapter");
        this.threads = threads;
        this.cameraPipe = cameraPipe;
        this.inactiveSurfaceCloser = inactiveSurfaceCloser;
        this.sessionConfigAdapter = sessionConfigAdapter;
        this.lock = new Object();
        this.activeSurfaceMap = new LinkedHashMap();
    }

    public static /* synthetic */ Deferred setupAsync$default(UseCaseSurfaceManager useCaseSurfaceManager, CameraGraph cameraGraph, SessionConfigAdapter sessionConfigAdapter, Map map, long j, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setupAsync");
        }
        if ((i & 8) != 0) {
            j = 5000;
        }
        return useCaseSurfaceManager.setupAsync(cameraGraph, sessionConfigAdapter, map, j);
    }

    public final Deferred setupAsync(CameraGraph graph, SessionConfigAdapter sessionConfigAdapter, Map surfaceToStreamMap, long j) {
        Deferred deferredCompletableDeferred;
        Intrinsics.checkNotNullParameter(graph, "graph");
        Intrinsics.checkNotNullParameter(sessionConfigAdapter, "sessionConfigAdapter");
        Intrinsics.checkNotNullParameter(surfaceToStreamMap, "surfaceToStreamMap");
        synchronized (this.lock) {
            try {
                if (this.setupDeferred != null) {
                    throw new IllegalStateException("Surfaces should only be set up once!");
                }
                if (this.stopDeferred != null) {
                    throw new IllegalStateException("Surfaces being setup after stopped!");
                }
                if (this.configuredSurfaceMap != null) {
                    throw new IllegalStateException("Check failed.");
                }
                final List deferrableSurfaces = sessionConfigAdapter.getDeferrableSurfaces();
                try {
                    DeferrableSurfaces.incrementAll(deferrableSurfaces);
                    deferredCompletableDeferred = BuildersKt__Builders_commonKt.async$default(this.threads.getScope(), null, null, new UseCaseSurfaceManager$setupAsync$1$deferred$1(sessionConfigAdapter, this, deferrableSurfaces, j, surfaceToStreamMap, graph, null), 3, null);
                    deferredCompletableDeferred.invokeOnCompletion(new Function1() { // from class: androidx.camera.camera2.impl.UseCaseSurfaceManager$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return UseCaseSurfaceManager.setupAsync$lambda$0$3$0(deferrableSurfaces, (Throwable) obj);
                        }
                    });
                    this.setupDeferred = deferredCompletableDeferred;
                } catch (DeferrableSurface.SurfaceClosedException e) {
                    Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
                    if (Logger.isWarnEnabled("CXCP")) {
                        Log.w(Camera2Logger.TRUNCATED_TAG, "Failed to increment DeferrableSurfaces: Surfaces closed");
                    }
                    BuildersKt__Builders_commonKt.launch$default(this.threads.getScope(), null, null, new UseCaseSurfaceManager$setupAsync$1$4(sessionConfigAdapter, e, null), 3, null);
                    deferredCompletableDeferred = CompletableDeferredKt.CompletableDeferred(Boolean.FALSE);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return deferredCompletableDeferred;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit setupAsync$lambda$0$3$0(List list, Throwable th) {
        DeferrableSurfaces.decrementAll(list);
        return Unit.INSTANCE;
    }

    public final Deferred stopAsync() {
        CompletableDeferred completableDeferredCompletableDeferred$default;
        synchronized (this.lock) {
            try {
                completableDeferredCompletableDeferred$default = this.stopDeferred;
                if (completableDeferredCompletableDeferred$default != null) {
                    Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
                    if (Logger.isWarnEnabled("CXCP")) {
                        Log.w(Camera2Logger.TRUNCATED_TAG, "UseCaseSurfaceManager is already stopping!");
                    }
                } else {
                    Deferred deferred = this.setupDeferred;
                    if (deferred != null) {
                        Job.DefaultImpls.cancel$default(deferred, null, 1, null);
                    }
                    this.inactiveSurfaceCloser.closeAll();
                    this.configuredSurfaceMap = null;
                    completableDeferredCompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default(null, 1, null);
                    this.stopDeferred = completableDeferredCompletableDeferred$default;
                    tryClearSurfaceListener();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return completableDeferredCompletableDeferred$default;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static /* synthetic */ java.lang.Object awaitSetupCompletion$suspendImpl(androidx.camera.camera2.impl.UseCaseSurfaceManager r5, kotlin.coroutines.Continuation r6) {
        /*
            boolean r0 = r6 instanceof androidx.camera.camera2.impl.UseCaseSurfaceManager.C01771
            if (r0 == 0) goto L13
            r0 = r6
            androidx.camera.camera2.impl.UseCaseSurfaceManager$awaitSetupCompletion$1 r0 = (androidx.camera.camera2.impl.UseCaseSurfaceManager.C01771) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.camera.camera2.impl.UseCaseSurfaceManager$awaitSetupCompletion$1 r0 = new androidx.camera.camera2.impl.UseCaseSurfaceManager$awaitSetupCompletion$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L32
            if (r2 != r4) goto L2a
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: java.util.concurrent.CancellationException -> L4c
            return r6
        L2a:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L32:
            kotlin.ResultKt.throwOnFailure(r6)
            java.lang.Object r6 = r5.lock
            monitor-enter(r6)
            kotlinx.coroutines.Deferred r2 = r5.setupDeferred     // Catch: java.lang.Throwable -> L64
            if (r2 == 0) goto L66
            kotlinx.coroutines.CompletableDeferred r5 = r5.stopDeferred     // Catch: java.lang.Throwable -> L64
            if (r5 == 0) goto L41
            goto L66
        L41:
            monitor-exit(r6)
            r0.label = r4     // Catch: java.util.concurrent.CancellationException -> L4c
            java.lang.Object r5 = r2.await(r0)     // Catch: java.util.concurrent.CancellationException -> L4c
            if (r5 != r1) goto L4b
            return r1
        L4b:
            return r5
        L4c:
            androidx.camera.camera2.impl.Camera2Logger r5 = androidx.camera.camera2.impl.Camera2Logger.INSTANCE
            java.lang.String r5 = "CXCP"
            boolean r5 = androidx.camera.core.Logger.isWarnEnabled(r5)
            if (r5 == 0) goto L5f
            java.lang.String r5 = androidx.camera.camera2.impl.Camera2Logger.access$getTRUNCATED_TAG$p()
            java.lang.String r6 = "Surface setup was cancelled"
            android.util.Log.w(r5, r6)
        L5f:
            java.lang.Boolean r5 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r3)
            return r5
        L64:
            r5 = move-exception
            goto L6c
        L66:
            java.lang.Boolean r5 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r3)     // Catch: java.lang.Throwable -> L64
            monitor-exit(r6)
            return r5
        L6c:
            monitor-exit(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.UseCaseSurfaceManager.awaitSetupCompletion$suspendImpl(androidx.camera.camera2.impl.UseCaseSurfaceManager, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // androidx.camera.camera2.pipe.CameraSurfaceManager.SurfaceListener
    public void onSurfaceActive(Surface surface) {
        DeferrableSurface deferrableSurface;
        Intrinsics.checkNotNullParameter(surface, "surface");
        synchronized (this.lock) {
            try {
                Map map = this.configuredSurfaceMap;
                if (map != null && (deferrableSurface = (DeferrableSurface) map.get(surface)) != null) {
                    if (!this.activeSurfaceMap.containsKey(surface)) {
                        Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
                        if (Logger.isDebugEnabled("CXCP")) {
                            Log.d(Camera2Logger.TRUNCATED_TAG, "SurfaceActive " + deferrableSurface + " in " + this);
                        }
                        this.activeSurfaceMap.put(surface, deferrableSurface);
                        try {
                            deferrableSurface.incrementUseCount();
                        } catch (DeferrableSurface.SurfaceClosedException e) {
                            Camera2Logger camera2Logger2 = Camera2Logger.INSTANCE;
                            if (Logger.isWarnEnabled("CXCP")) {
                                Log.w(Camera2Logger.TRUNCATED_TAG, "Error when " + surface + " going to increase the use count.", e);
                            }
                            SessionConfigAdapter sessionConfigAdapter = this.sessionConfigAdapter;
                            DeferrableSurface deferrableSurface2 = e.getDeferrableSurface();
                            Intrinsics.checkNotNullExpressionValue(deferrableSurface2, "getDeferrableSurface(...)");
                            sessionConfigAdapter.reportSurfaceInvalid(deferrableSurface2);
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // androidx.camera.camera2.pipe.CameraSurfaceManager.SurfaceListener
    public void onSurfaceInactive(Surface surface) {
        Intrinsics.checkNotNullParameter(surface, "surface");
        synchronized (this.lock) {
            try {
                DeferrableSurface deferrableSurface = (DeferrableSurface) this.activeSurfaceMap.remove(surface);
                if (deferrableSurface != null) {
                    Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
                    if (Logger.isDebugEnabled("CXCP")) {
                        Log.d(Camera2Logger.TRUNCATED_TAG, "SurfaceInactive " + deferrableSurface + " in " + this);
                    }
                    this.inactiveSurfaceCloser.onSurfaceInactive(deferrableSurface);
                    try {
                        deferrableSurface.decrementUseCount();
                    } catch (IllegalStateException e) {
                        Camera2Logger camera2Logger2 = Camera2Logger.INSTANCE;
                        if (Logger.isWarnEnabled("CXCP")) {
                            Log.w(Camera2Logger.TRUNCATED_TAG, "Error when " + surface + " going to decrease the use count.", e);
                        }
                    }
                    tryClearSurfaceListener();
                    Unit unit = Unit.INSTANCE;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setSurfaceListener() {
        this.cameraPipe.cameraSurfaceManager().addListener(this);
    }

    private final void tryClearSurfaceListener() {
        synchronized (this.lock) {
            try {
                if (this.activeSurfaceMap.isEmpty() && this.configuredSurfaceMap == null) {
                    Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
                    if (Logger.isDebugEnabled("CXCP")) {
                        Log.d(Camera2Logger.TRUNCATED_TAG, this + " remove surface listener");
                    }
                    this.cameraPipe.cameraSurfaceManager().removeListener(this);
                    CompletableDeferred completableDeferred = this.stopDeferred;
                    if (completableDeferred != null) {
                        completableDeferred.complete(Unit.INSTANCE);
                    }
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getSurfaces(java.util.List r5, long r6, kotlin.coroutines.Continuation r8) {
        /*
            r4 = this;
            boolean r0 = r8 instanceof androidx.camera.camera2.impl.UseCaseSurfaceManager.C01781
            if (r0 == 0) goto L13
            r0 = r8
            androidx.camera.camera2.impl.UseCaseSurfaceManager$getSurfaces$1 r0 = (androidx.camera.camera2.impl.UseCaseSurfaceManager.C01781) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.camera.camera2.impl.UseCaseSurfaceManager$getSurfaces$1 r0 = new androidx.camera.camera2.impl.UseCaseSurfaceManager$getSurfaces$1
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L31
            if (r2 != r3) goto L29
            kotlin.ResultKt.throwOnFailure(r8)
            goto L43
        L29:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L31:
            kotlin.ResultKt.throwOnFailure(r8)
            androidx.camera.camera2.impl.UseCaseSurfaceManager$getSurfaces$2 r8 = new androidx.camera.camera2.impl.UseCaseSurfaceManager$getSurfaces$2
            r2 = 0
            r8.<init>(r5, r2)
            r0.label = r3
            java.lang.Object r8 = kotlinx.coroutines.TimeoutKt.withTimeoutOrNull(r6, r8, r0)
            if (r8 != r1) goto L43
            return r1
        L43:
            java.util.List r8 = (java.util.List) r8
            if (r8 != 0) goto L4c
            java.util.List r5 = kotlin.collections.CollectionsKt.emptyList()
            return r5
        L4c:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.UseCaseSurfaceManager.getSurfaces(java.util.List, long, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.UseCaseSurfaceManager$getSurfaces$2 */
    static final class C01792 extends SuspendLambda implements Function2 {
        final /* synthetic */ List $deferrableSurfaces;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01792(List list, Continuation continuation) {
            super(2, continuation);
            this.$deferrableSurfaces = list;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C01792(this.$deferrableSurfaces, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01792) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            ResultKt.throwOnFailure(obj);
            List list = this.$deferrableSurfaces;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(Futures.nonCancellationPropagating(((DeferrableSurface) it.next()).getSurface()));
            }
            ListenableFuture listenableFutureSuccessfulAsList = Futures.successfulAsList(arrayList);
            Intrinsics.checkNotNullExpressionValue(listenableFutureSuccessfulAsList, "successfulAsList(...)");
            this.label = 1;
            Object objAwait = ListenableFutureKt.await(listenableFutureSuccessfulAsList, this);
            return objAwait == coroutine_suspended ? coroutine_suspended : objAwait;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean areValid(List list) {
        return (list.isEmpty() || list.contains(null)) ? false : true;
    }
}
