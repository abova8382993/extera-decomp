package androidx.camera.camera2.impl;

import android.util.Log;
import android.view.Surface;
import androidx.camera.camera2.adapter.SessionConfigAdapter;
import androidx.camera.camera2.compat.workaround.InactiveSurfaceCloser;
import androidx.camera.camera2.pipe.CameraGraph;
import androidx.camera.camera2.pipe.CameraPipe;
import androidx.camera.camera2.pipe.CameraSurfaceManager;
import androidx.camera.camera2.pipe.StreamId;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.DeferrableSurfaces;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.ListenableFutureKt;
import com.android.p006dx.p009io.Opcodes;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Job;
import okhttp3.internal.url._UrlKt;
import okio.ByteString$$ExternalSyntheticBUOutline0;
import okio.Segment$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes3.dex */
@Metadata(m876d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010 \n\u0002\b\u0004\b\u0017\u0018\u00002\u00020\u0001B)\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0004\b\n\u0010\u000bJ:\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\b\u001a\u00020\t2\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u001e0\u00162\b\b\u0002\u0010\u001f\u001a\u00020 J\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00190\u000fJ\u000e\u0010\"\u001a\u00020\u0010H\u0096@¢\u0006\u0002\u0010#J\u0010\u0010$\u001a\u00020\u00192\u0006\u0010%\u001a\u00020\u0013H\u0016J\u0010\u0010&\u001a\u00020\u00192\u0006\u0010%\u001a\u00020\u0013H\u0016J\b\u0010'\u001a\u00020\u0019H\u0003J\b\u0010(\u001a\u00020\u0019H\u0003J,\u0010)\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130*2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00140*2\u0006\u0010\u001f\u001a\u00020 H\u0082@¢\u0006\u0002\u0010,J\u0014\u0010-\u001a\u00020\u0010*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130*H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000f8\u0002@\u0002X\u0083\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\u00128\u0002X\u0083\u0004¢\u0006\u0002\n\u0000R \u0010\u0015\u001a\u0010\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u0014\u0018\u00010\u00168\u0002@\u0002X\u0083\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\n\u0012\u0004\u0012\u00020\u0019\u0018\u00010\u00188\u0002@\u0002X\u0083\u000e¢\u0006\u0002\n\u0000¨\u0006."}, m877d2 = {"Landroidx/camera/camera2/impl/UseCaseSurfaceManager;", "Landroidx/camera/camera2/pipe/CameraSurfaceManager$SurfaceListener;", "threads", "Landroidx/camera/camera2/impl/UseCaseThreads;", "cameraPipe", "Landroidx/camera/camera2/pipe/CameraPipe;", "inactiveSurfaceCloser", "Landroidx/camera/camera2/compat/workaround/InactiveSurfaceCloser;", "sessionConfigAdapter", "Landroidx/camera/camera2/adapter/SessionConfigAdapter;", "<init>", "(Landroidx/camera/camera2/impl/UseCaseThreads;Landroidx/camera/camera2/pipe/CameraPipe;Landroidx/camera/camera2/compat/workaround/InactiveSurfaceCloser;Landroidx/camera/camera2/adapter/SessionConfigAdapter;)V", "lock", _UrlKt.FRAGMENT_ENCODE_SET, "setupDeferred", "Lkotlinx/coroutines/Deferred;", _UrlKt.FRAGMENT_ENCODE_SET, "activeSurfaceMap", _UrlKt.FRAGMENT_ENCODE_SET, "Landroid/view/Surface;", "Landroidx/camera/core/impl/DeferrableSurface;", "configuredSurfaceMap", _UrlKt.FRAGMENT_ENCODE_SET, "stopDeferred", "Lkotlinx/coroutines/CompletableDeferred;", _UrlKt.FRAGMENT_ENCODE_SET, "setupAsync", "graph", "Landroidx/camera/camera2/pipe/CameraGraph;", "surfaceToStreamMap", "Landroidx/camera/camera2/pipe/StreamId;", "timeoutMillis", _UrlKt.FRAGMENT_ENCODE_SET, "stopAsync", "awaitSetupCompletion", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onSurfaceActive", "surface", "onSurfaceInactive", "setSurfaceListener", "tryClearSurfaceListener", "getSurfaces", _UrlKt.FRAGMENT_ENCODE_SET, "deferrableSurfaces", "(Ljava/util/List;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "areValid", "camera-camera2"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nUseCaseSurfaceManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 UseCaseSurfaceManager.kt\nandroidx/camera/camera2/impl/UseCaseSurfaceManager\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 Camera2Logger.kt\nandroidx/camera/camera2/impl/Camera2Logger\n*L\n1#1,269:1\n1#2:270\n119#3,4:271\n119#3,4:275\n119#3,4:279\n85#3,4:283\n129#3,4:287\n85#3,4:291\n129#3,4:295\n85#3,4:299\n*S KotlinDebug\n*F\n+ 1 UseCaseSurfaceManager.kt\nandroidx/camera/camera2/impl/UseCaseSurfaceManager\n*L\n82#1:271,4\n158#1:275,4\n195#1:279,4\n204#1:283,4\n209#1:287,4\n222#1:291,4\n227#1:295,4\n243#1:299,4\n*E\n"})
public class UseCaseSurfaceManager implements CameraSurfaceManager.SurfaceListener {
    private final CameraPipe cameraPipe;
    private Map<Surface, ? extends DeferrableSurface> configuredSurfaceMap;
    private final InactiveSurfaceCloser inactiveSurfaceCloser;
    private final SessionConfigAdapter sessionConfigAdapter;
    private Deferred<Boolean> setupDeferred;
    private CompletableDeferred<Unit> stopDeferred;
    private final UseCaseThreads threads;
    private final Object lock = new Object();
    private final Map<Surface, DeferrableSurface> activeSurfaceMap = new LinkedHashMap();

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.UseCaseSurfaceManager$awaitSetupCompletion$1 */
    @Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.UseCaseSurfaceManager", m896f = "UseCaseSurfaceManager.kt", m897i = {}, m898l = {193}, m899m = "awaitSetupCompletion$suspendImpl", m900n = {}, m902s = {}, m903v = 1)
    public static final class C01781 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C01781(Continuation<? super C01781> continuation) {
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
    @Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.UseCaseSurfaceManager", m896f = "UseCaseSurfaceManager.kt", m897i = {}, m898l = {Opcodes.CONST_METHOD_HANDLE}, m899m = "getSurfaces", m900n = {}, m902s = {}, m903v = 1)
    public static final class C01791 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C01791(Continuation<? super C01791> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return UseCaseSurfaceManager.this.getSurfaces(null, 0L, this);
        }
    }

    public Object awaitSetupCompletion(Continuation<? super Boolean> continuation) {
        return awaitSetupCompletion$suspendImpl(this, continuation);
    }

    public UseCaseSurfaceManager(UseCaseThreads useCaseThreads, CameraPipe cameraPipe, InactiveSurfaceCloser inactiveSurfaceCloser, SessionConfigAdapter sessionConfigAdapter) {
        this.threads = useCaseThreads;
        this.cameraPipe = cameraPipe;
        this.inactiveSurfaceCloser = inactiveSurfaceCloser;
        this.sessionConfigAdapter = sessionConfigAdapter;
    }

    public static /* synthetic */ Deferred setupAsync$default(UseCaseSurfaceManager useCaseSurfaceManager, CameraGraph cameraGraph, SessionConfigAdapter sessionConfigAdapter, Map map, long j, int i, Object obj) {
        if (obj != null) {
            ByteString$$ExternalSyntheticBUOutline0.m979m("Super calls with default arguments not supported in this target, function: setupAsync");
            return null;
        }
        if ((i & 8) != 0) {
            j = 5000;
        }
        return useCaseSurfaceManager.setupAsync(cameraGraph, sessionConfigAdapter, map, j);
    }

    public final Deferred<Boolean> setupAsync(CameraGraph graph, SessionConfigAdapter sessionConfigAdapter, Map<DeferrableSurface, StreamId> surfaceToStreamMap, long timeoutMillis) {
        Deferred<Boolean> deferredCompletableDeferred;
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
                final List<DeferrableSurface> deferrableSurfaces = sessionConfigAdapter.getDeferrableSurfaces();
                try {
                    DeferrableSurfaces.incrementAll(deferrableSurfaces);
                    deferredCompletableDeferred = BuildersKt__Builders_commonKt.async$default(this.threads.getScope(), null, null, new UseCaseSurfaceManager$setupAsync$1$deferred$1(sessionConfigAdapter, this, deferrableSurfaces, timeoutMillis, surfaceToStreamMap, graph, null), 3, null);
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

    public static final Unit setupAsync$lambda$0$3$0(List list, Throwable th) {
        DeferrableSurfaces.decrementAll(list);
        return Unit.INSTANCE;
    }

    public final Deferred<Unit> stopAsync() {
        CompletableDeferred<Unit> completableDeferredCompletableDeferred$default;
        synchronized (this.lock) {
            try {
                completableDeferredCompletableDeferred$default = this.stopDeferred;
                if (completableDeferredCompletableDeferred$default != null) {
                    Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
                    if (Logger.isWarnEnabled("CXCP")) {
                        Log.w(Camera2Logger.TRUNCATED_TAG, "UseCaseSurfaceManager is already stopping!");
                    }
                } else {
                    Deferred<Boolean> deferred = this.setupDeferred;
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

    /* JADX WARN: Removed duplicated region for block: B:49:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static /* synthetic */ java.lang.Object awaitSetupCompletion$suspendImpl(androidx.camera.camera2.impl.UseCaseSurfaceManager r5, kotlin.coroutines.Continuation<? super java.lang.Boolean> r6) {
        /*
            boolean r0 = r6 instanceof androidx.camera.camera2.impl.UseCaseSurfaceManager.C01781
            if (r0 == 0) goto L13
            r0 = r6
            androidx.camera.camera2.impl.UseCaseSurfaceManager$awaitSetupCompletion$1 r0 = (androidx.camera.camera2.impl.UseCaseSurfaceManager.C01781) r0
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
            if (r2 == 0) goto L31
            if (r2 != r4) goto L2a
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: java.util.concurrent.CancellationException -> L4b
            return r6
        L2a:
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r5)
            r5 = 0
            return r5
        L31:
            kotlin.ResultKt.throwOnFailure(r6)
            java.lang.Object r6 = r5.lock
            monitor-enter(r6)
            kotlinx.coroutines.Deferred<java.lang.Boolean> r2 = r5.setupDeferred     // Catch: java.lang.Throwable -> L63
            if (r2 == 0) goto L65
            kotlinx.coroutines.CompletableDeferred<kotlin.Unit> r5 = r5.stopDeferred     // Catch: java.lang.Throwable -> L63
            if (r5 == 0) goto L40
            goto L65
        L40:
            monitor-exit(r6)
            r0.label = r4     // Catch: java.util.concurrent.CancellationException -> L4b
            java.lang.Object r5 = r2.await(r0)     // Catch: java.util.concurrent.CancellationException -> L4b
            if (r5 != r1) goto L4a
            return r1
        L4a:
            return r5
        L4b:
            androidx.camera.camera2.impl.Camera2Logger r5 = androidx.camera.camera2.impl.Camera2Logger.INSTANCE
            java.lang.String r5 = "CXCP"
            boolean r5 = androidx.camera.core.Logger.isWarnEnabled(r5)
            if (r5 == 0) goto L5e
            java.lang.String r5 = androidx.camera.camera2.impl.Camera2Logger.access$getTRUNCATED_TAG$p()
            java.lang.String r6 = "Surface setup was cancelled"
            android.util.Log.w(r5, r6)
        L5e:
            java.lang.Boolean r5 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r3)
            return r5
        L63:
            r5 = move-exception
            goto L6b
        L65:
            java.lang.Boolean r5 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r3)     // Catch: java.lang.Throwable -> L63
            monitor-exit(r6)
            return r5
        L6b:
            monitor-exit(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.UseCaseSurfaceManager.awaitSetupCompletion$suspendImpl(androidx.camera.camera2.impl.UseCaseSurfaceManager, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // androidx.camera.camera2.pipe.CameraSurfaceManager.SurfaceListener
    public void onSurfaceActive(Surface surface) {
        DeferrableSurface deferrableSurface;
        synchronized (this.lock) {
            try {
                Map<Surface, ? extends DeferrableSurface> map = this.configuredSurfaceMap;
                if (map != null && (deferrableSurface = map.get(surface)) != null) {
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
                            this.sessionConfigAdapter.reportSurfaceInvalid(e.getDeferrableSurface());
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
        synchronized (this.lock) {
            try {
                DeferrableSurface deferrableSurfaceRemove = this.activeSurfaceMap.remove(surface);
                if (deferrableSurfaceRemove != null) {
                    Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
                    if (Logger.isDebugEnabled("CXCP")) {
                        Log.d(Camera2Logger.TRUNCATED_TAG, "SurfaceInactive " + deferrableSurfaceRemove + " in " + this);
                    }
                    this.inactiveSurfaceCloser.onSurfaceInactive(deferrableSurfaceRemove);
                    try {
                        deferrableSurfaceRemove.decrementUseCount();
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
                    CompletableDeferred<Unit> completableDeferred = this.stopDeferred;
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

    /* JADX WARN: Removed duplicated region for block: B:29:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getSurfaces(java.util.List<? extends androidx.camera.core.impl.DeferrableSurface> r5, long r6, kotlin.coroutines.Continuation<? super java.util.List<? extends android.view.Surface>> r8) {
        /*
            r4 = this;
            boolean r0 = r8 instanceof androidx.camera.camera2.impl.UseCaseSurfaceManager.C01791
            if (r0 == 0) goto L13
            r0 = r8
            androidx.camera.camera2.impl.UseCaseSurfaceManager$getSurfaces$1 r0 = (androidx.camera.camera2.impl.UseCaseSurfaceManager.C01791) r0
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
            java.lang.Object r4 = r0.result
            java.lang.Object r8 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r0.label
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L30
            if (r1 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r4)
            goto L41
        L2a:
            java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r4)
            return r2
        L30:
            kotlin.ResultKt.throwOnFailure(r4)
            androidx.camera.camera2.impl.UseCaseSurfaceManager$getSurfaces$2 r4 = new androidx.camera.camera2.impl.UseCaseSurfaceManager$getSurfaces$2
            r4.<init>(r5, r2)
            r0.label = r3
            java.lang.Object r4 = kotlinx.coroutines.TimeoutKt.withTimeoutOrNull(r6, r4, r0)
            if (r4 != r8) goto L41
            return r8
        L41:
            java.util.List r4 = (java.util.List) r4
            if (r4 != 0) goto L49
            java.util.List r4 = kotlin.collections.CollectionsKt.emptyList()
        L49:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.UseCaseSurfaceManager.getSurfaces(java.util.List, long, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.UseCaseSurfaceManager$getSurfaces$2 */
    @Metadata(m876d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\u0010\u0000\u001a&\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00020\u0002 \u0003*\u0012\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00020\u0002\u0018\u00010\u00040\u0001*\u00020\u0005H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Landroid/view/Surface;", "kotlin.jvm.PlatformType", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.UseCaseSurfaceManager$getSurfaces$2", m896f = "UseCaseSurfaceManager.kt", m897i = {}, m898l = {258}, m899m = "invokeSuspend", m900n = {}, m902s = {}, m903v = 1)
    @SourceDebugExtension({"SMAP\nUseCaseSurfaceManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 UseCaseSurfaceManager.kt\nandroidx/camera/camera2/impl/UseCaseSurfaceManager$getSurfaces$2\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,269:1\n1563#2:270\n1634#2,3:271\n*S KotlinDebug\n*F\n+ 1 UseCaseSurfaceManager.kt\nandroidx/camera/camera2/impl/UseCaseSurfaceManager$getSurfaces$2\n*L\n256#1:270\n256#1:271,3\n*E\n"})
    public static final class C01802 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super List<Surface>>, Object> {
        final /* synthetic */ List<DeferrableSurface> $deferrableSurfaces;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public C01802(List<? extends DeferrableSurface> list, Continuation<? super C01802> continuation) {
            super(2, continuation);
            this.$deferrableSurfaces = list;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C01802(this.$deferrableSurfaces, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super List<Surface>> continuation) {
            return ((C01802) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i != 0) {
                if (i == 1) {
                    ResultKt.throwOnFailure(obj);
                    return obj;
                }
                Segment$$ExternalSyntheticBUOutline1.m992m("call to 'resume' before 'invoke' with coroutine");
                return null;
            }
            ResultKt.throwOnFailure(obj);
            List<DeferrableSurface> list = this.$deferrableSurfaces;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(Futures.nonCancellationPropagating(((DeferrableSurface) it.next()).getSurface()));
            }
            ListenableFuture listenableFutureSuccessfulAsList = Futures.successfulAsList(arrayList);
            this.label = 1;
            Object objAwait = ListenableFutureKt.await(listenableFutureSuccessfulAsList, this);
            return objAwait == coroutine_suspended ? coroutine_suspended : objAwait;
        }
    }

    public final boolean areValid(List<? extends Surface> list) {
        return (list.isEmpty() || list.contains(null)) ? false : true;
    }
}
