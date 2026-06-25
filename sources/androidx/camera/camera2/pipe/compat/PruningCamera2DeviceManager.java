package androidx.camera.camera2.pipe.compat;

import androidx.camera.camera2.pipe.CameraError;
import androidx.camera.camera2.pipe.CameraId;
import androidx.camera.camera2.pipe.GraphState;
import androidx.camera.camera2.pipe.core.Log;
import androidx.camera.camera2.pipe.core.Permissions;
import androidx.camera.camera2.pipe.core.PruningProcessingQueue;
import androidx.camera.camera2.pipe.core.Threads;
import androidx.camera.camera2.pipe.core.Token;
import androidx.camera.camera2.pipe.graph.GraphListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.LazyKt__LazyJVMKt$$ExternalSyntheticBUOutline0;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000Ê\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\b\b\u0001\u0018\u00002\u00020\u0001:\u0003efgB1\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\u000b\u001a\u00020\n¢\u0006\u0004\b\f\u0010\rJ\u0013\u0010\u0010\u001a\u00020\u000f*\u00020\u000eH\u0002¢\u0006\u0004\b\u0010\u0010\u0011J\u0018\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u000eH\u0082@¢\u0006\u0004\b\u0013\u0010\u0014J\u0018\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0015H\u0082@¢\u0006\u0004\b\u0016\u0010\u0017J\u0018\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0018H\u0082@¢\u0006\u0004\b\u0019\u0010\u001aJ\u0018\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u001bH\u0082@¢\u0006\u0004\b\u001c\u0010\u001dJ\u0018\u0010 \u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020\u001eH\u0082@¢\u0006\u0004\b \u0010!J \u0010(\u001a\u00020%2\u0006\u0010#\u001a\u00020\"2\u0006\u0010$\u001a\u00020\u0015H\u0082@¢\u0006\u0004\b&\u0010'JB\u00103\u001a\u0002002\u0006\u0010#\u001a\u00020\"2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\"0)2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020,0+2\u0006\u0010/\u001a\u00020.H\u0082@¢\u0006\u0004\b1\u00102J\u001e\u00106\u001a\u00020\u000f2\f\u00105\u001a\b\u0012\u0004\u0012\u00020\"04H\u0082@¢\u0006\u0004\b6\u00107J\u001e\u0010:\u001a\u00020\u000f2\f\u00109\u001a\b\u0012\u0004\u0012\u0002080)H\u0082@¢\u0006\u0004\b:\u0010;J3\u0010@\u001a\b\u0012\u0004\u0012\u00028\u00000)\"\u0004\b\u0000\u0010<*\b\u0012\u0004\u0012\u00028\u00000=2\f\u0010?\u001a\b\u0012\u0004\u0012\u00020>04H\u0002¢\u0006\u0004\b@\u0010AJK\u0010H\u001a\u0004\u0018\u00010E2\u0006\u0010#\u001a\u00020\"2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\"0)2\u0006\u0010C\u001a\u00020B2\u0006\u0010D\u001a\u00020,2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020,0+H\u0016¢\u0006\u0004\bF\u0010GJ\u001d\u0010L\u001a\b\u0012\u0004\u0012\u00020\u000f0I2\u0006\u0010#\u001a\u00020\"H\u0016¢\u0006\u0004\bJ\u0010KJ\u001d\u0010N\u001a\b\u0012\u0004\u0012\u00020\u000f0I2\u0006\u0010M\u001a\u00020,H\u0016¢\u0006\u0004\bN\u0010OJ\u001d\u0010S\u001a\u00020\u000f2\f\u0010P\u001a\b\u0012\u0004\u0012\u00020\u000e0=H\u0001¢\u0006\u0004\bQ\u0010RR\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010TR\u0014\u0010\u0005\u001a\u00020\u00048\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0005\u0010UR\u0014\u0010\u0007\u001a\u00020\u00068\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0007\u0010VR\u0014\u0010\t\u001a\u00020\b8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\t\u0010WR\u0017\u0010\u000b\u001a\u00020\n8\u0006¢\u0006\f\n\u0004\b\u000b\u0010X\u001a\u0004\bY\u0010ZR\u0014\u0010/\u001a\u00020.8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b/\u0010[R\u001a\u0010]\u001a\b\u0012\u0004\u0012\u00020\u000e0\\8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b]\u0010^R\u001a\u0010a\u001a\b\u0012\u0004\u0012\u00020`0_8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\ba\u0010bR\u001a\u0010c\u001a\b\u0012\u0004\u0012\u0002080=8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bc\u0010d¨\u0006h"}, m877d2 = {"Landroidx/camera/camera2/pipe/compat/PruningCamera2DeviceManager;", "Landroidx/camera/camera2/pipe/compat/Camera2DeviceManager;", "Landroidx/camera/camera2/pipe/core/Permissions;", "permissions", "Landroidx/camera/camera2/pipe/compat/RetryingCameraStateOpener;", "retryingCameraStateOpener", "Landroidx/camera/camera2/pipe/compat/Camera2DeviceCloser;", "camera2DeviceCloser", "Landroidx/camera/camera2/pipe/compat/Camera2ErrorProcessor;", "camera2ErrorProcessor", "Landroidx/camera/camera2/pipe/core/Threads;", "threads", "<init>", "(Landroidx/camera/camera2/pipe/core/Permissions;Landroidx/camera/camera2/pipe/compat/RetryingCameraStateOpener;Landroidx/camera/camera2/pipe/compat/Camera2DeviceCloser;Landroidx/camera/camera2/pipe/compat/Camera2ErrorProcessor;Landroidx/camera/camera2/pipe/core/Threads;)V", "Landroidx/camera/camera2/pipe/compat/CameraRequest;", _UrlKt.FRAGMENT_ENCODE_SET, "onRemoved", "(Landroidx/camera/camera2/pipe/compat/CameraRequest;)V", "request", "process", "(Landroidx/camera/camera2/pipe/compat/CameraRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Landroidx/camera/camera2/pipe/compat/RequestOpen;", "processRequestOpen", "(Landroidx/camera/camera2/pipe/compat/RequestOpen;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Landroidx/camera/camera2/pipe/compat/RequestClose;", "processRequestClose", "(Landroidx/camera/camera2/pipe/compat/RequestClose;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Landroidx/camera/camera2/pipe/compat/RequestCloseById;", "processRequestCloseById", "(Landroidx/camera/camera2/pipe/compat/RequestCloseById;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Landroidx/camera/camera2/pipe/compat/RequestCloseAll;", "requestCloseAll", "processRequestCloseAll", "(Landroidx/camera/camera2/pipe/compat/RequestCloseAll;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Landroidx/camera/camera2/pipe/CameraId;", "cameraId", "requestOpen", "Landroidx/camera/camera2/pipe/compat/PruningCamera2DeviceManager$RetrieveActiveCameraResult;", "retrieveActiveCamera-RzXb1QE", "(Ljava/lang/String;Landroidx/camera/camera2/pipe/compat/RequestOpen;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "retrieveActiveCamera", _UrlKt.FRAGMENT_ENCODE_SET, "sharedCameraIds", "Lkotlin/Function1;", _UrlKt.FRAGMENT_ENCODE_SET, "isForegroundObserver", "Lkotlinx/coroutines/CoroutineScope;", "scope", "Landroidx/camera/camera2/pipe/compat/PruningCamera2DeviceManager$OpenVirtualCameraResult;", "openCameraWithRetry-zDSwpeU", "(Ljava/lang/String;Ljava/util/List;Lkotlin/jvm/functions/Function1;Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "openCameraWithRetry", _UrlKt.FRAGMENT_ENCODE_SET, "cameraIds", "connectPendingRequestOpens", "(Ljava/util/Set;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Landroidx/camera/camera2/pipe/compat/PruningCamera2DeviceManager$PendingRequestOpen;", "pendingRequestOpensToDisconnect", "disconnectPendingRequestOpens", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "T", _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, "indices", "removeIndices", "(Ljava/util/List;Ljava/util/Set;)Ljava/util/List;", "Landroidx/camera/camera2/pipe/graph/GraphListener;", "graphListener", "isPrewarm", "Landroidx/camera/camera2/pipe/compat/VirtualCamera;", "open-zDSwpeU", "(Ljava/lang/String;Ljava/util/List;Landroidx/camera/camera2/pipe/graph/GraphListener;ZLkotlin/jvm/functions/Function1;)Landroidx/camera/camera2/pipe/compat/VirtualCamera;", "open", "Lkotlinx/coroutines/Deferred;", "close-EfqyGwQ", "(Ljava/lang/String;)Lkotlinx/coroutines/Deferred;", "close", "forceCancelOpen", "closeAll", "(Z)Lkotlinx/coroutines/Deferred;", "requests", "prune$camera_camera2_pipe", "(Ljava/util/List;)V", "prune", "Landroidx/camera/camera2/pipe/core/Permissions;", "Landroidx/camera/camera2/pipe/compat/RetryingCameraStateOpener;", "Landroidx/camera/camera2/pipe/compat/Camera2DeviceCloser;", "Landroidx/camera/camera2/pipe/compat/Camera2ErrorProcessor;", "Landroidx/camera/camera2/pipe/core/Threads;", "getThreads", "()Landroidx/camera/camera2/pipe/core/Threads;", "Lkotlinx/coroutines/CoroutineScope;", "Landroidx/camera/camera2/pipe/core/PruningProcessingQueue;", "queue", "Landroidx/camera/camera2/pipe/core/PruningProcessingQueue;", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/camera2/pipe/compat/ActiveCamera;", "activeCameras", "Ljava/util/Set;", "pendingRequestOpens", "Ljava/util/List;", "PendingRequestOpen", "RetrieveActiveCameraResult", "OpenVirtualCameraResult", "camera-camera2-pipe"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nCamera2DeviceManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Camera2DeviceManager.kt\nandroidx/camera/camera2/pipe/compat/PruningCamera2DeviceManager\n+ 2 Log.kt\nandroidx/camera/camera2/pipe/core/Log\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,656:1\n627#1,6:673\n627#1,6:679\n82#2,2:657\n82#2,2:659\n82#2,2:661\n50#2,2:685\n59#2,2:689\n82#2,2:700\n71#2,2:702\n59#2,2:710\n59#2,2:715\n59#2,2:722\n59#2,2:724\n59#2,2:726\n59#2,2:728\n50#2,2:730\n774#3:663\n865#3,2:664\n388#3,7:666\n1869#3,2:687\n774#3:691\n865#3,2:692\n774#3:694\n865#3,2:695\n774#3:697\n865#3,2:698\n1740#3,2:704\n1761#3,3:706\n1742#3:709\n774#3:712\n865#3,2:713\n774#3:717\n865#3,2:718\n295#3,2:720\n774#3:732\n865#3,2:733\n1740#3,2:735\n1761#3,3:737\n1742#3:740\n*S KotlinDebug\n*F\n+ 1 Camera2DeviceManager.kt\nandroidx/camera/camera2/pipe/compat/PruningCamera2DeviceManager\n*L\n317#1:673,6\n339#1:679,6\n221#1:657,2\n247#1:659,2\n259#1:661,2\n346#1:685,2\n376#1:689,2\n407#1:700,2\n412#1:702,2\n456#1:710,2\n474#1:715,2\n491#1:722,2\n546#1:724,2\n549#1:726,2\n559#1:728,2\n574#1:730,2\n268#1:663\n268#1:664,2\n276#1:666,7\n356#1:687,2\n380#1:691\n380#1:692,2\n384#1:694\n384#1:695,2\n392#1:697\n392#1:698,2\n429#1:704,2\n430#1:706,3\n429#1:709\n466#1:712\n466#1:713,2\n479#1:717\n479#1:718,2\n481#1:720,2\n597#1:732\n597#1:733,2\n604#1:735,2\n604#1:737,3\n604#1:740\n*E\n"})
public final class PruningCamera2DeviceManager implements Camera2DeviceManager {
    private final Set<ActiveCamera> activeCameras;
    private final Camera2DeviceCloser camera2DeviceCloser;
    private final Camera2ErrorProcessor camera2ErrorProcessor;
    private final List<PendingRequestOpen> pendingRequestOpens;
    private final Permissions permissions;
    private final PruningProcessingQueue<CameraRequest> queue;
    private final RetryingCameraStateOpener retryingCameraStateOpener;
    private final CoroutineScope scope;
    private final Threads threads;

    /* JADX INFO: renamed from: androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager$connectPendingRequestOpens$1 */
    @Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager", m896f = "Camera2DeviceManager.kt", m897i = {0}, m898l = {606}, m899m = "connectPendingRequestOpens", m900n = {"pendingRequestOpen"}, m902s = {"L$1"}, m903v = 1)
    public static final class C02121 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C02121(Continuation<? super C02121> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PruningCamera2DeviceManager.this.connectPendingRequestOpens(null, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager$processRequestClose$1 */
    @Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager", m896f = "Camera2DeviceManager.kt", m897i = {0}, m898l = {465, 469}, m899m = "processRequestClose", m900n = {"request"}, m902s = {"L$0"}, m903v = 1)
    public static final class C02131 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C02131(Continuation<? super C02131> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PruningCamera2DeviceManager.this.processRequestClose(null, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager$processRequestCloseAll$1 */
    @Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager", m896f = "Camera2DeviceManager.kt", m897i = {0, 1}, m898l = {493, 498}, m899m = "processRequestCloseAll", m900n = {"requestCloseAll", "requestCloseAll"}, m902s = {"L$0", "L$0"}, m903v = 1)
    public static final class C02141 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C02141(Continuation<? super C02141> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PruningCamera2DeviceManager.this.processRequestCloseAll(null, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager$processRequestCloseById$1 */
    @Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager", m896f = "Camera2DeviceManager.kt", m897i = {0, 0, 1}, m898l = {478, 485}, m899m = "processRequestCloseById", m900n = {"request", "cameraId", "request"}, m902s = {"L$0", "L$1", "L$0"}, m903v = 1)
    public static final class C02151 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C02151(Continuation<? super C02151> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PruningCamera2DeviceManager.this.processRequestCloseById(null, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager$processRequestOpen$1 */
    @Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager", m896f = "Camera2DeviceManager.kt", m897i = {0, 0, 0, 1, 1, 2, 2, 3}, m898l = {391, 398, 404, 436, 437, 445}, m899m = "processRequestOpen", m900n = {"request", "cameraIdToOpen", "camerasToClose", "request", "cameraIdToOpen", "request", "cameraIdToOpen", "request"}, m902s = {"L$0", "L$1", "L$2", "L$0", "L$1", "L$0", "L$1", "L$0"}, m903v = 1)
    public static final class C02161 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public C02161(Continuation<? super C02161> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PruningCamera2DeviceManager.this.processRequestOpen(null, this);
        }
    }

    public PruningCamera2DeviceManager(Permissions permissions, RetryingCameraStateOpener retryingCameraStateOpener, Camera2DeviceCloser camera2DeviceCloser, Camera2ErrorProcessor camera2ErrorProcessor, Threads threads) {
        this.permissions = permissions;
        this.retryingCameraStateOpener = retryingCameraStateOpener;
        this.camera2DeviceCloser = camera2DeviceCloser;
        this.camera2ErrorProcessor = camera2ErrorProcessor;
        this.threads = threads;
        CoroutineScope cameraPipeScope = threads.getCameraPipeScope();
        this.scope = cameraPipeScope;
        this.queue = PruningProcessingQueue.INSTANCE.processIn(new PruningProcessingQueue(0, new PruningCamera2DeviceManager$queue$1(this), null, new PruningCamera2DeviceManager$queue$2(this, null), 5, null), cameraPipeScope);
        this.activeCameras = new LinkedHashSet();
        this.pendingRequestOpens = new ArrayList();
    }

    @Metadata(m876d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u0002\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, m877d2 = {"Landroidx/camera/camera2/pipe/compat/PruningCamera2DeviceManager$PendingRequestOpen;", _UrlKt.FRAGMENT_ENCODE_SET, "request", "Landroidx/camera/camera2/pipe/compat/RequestOpen;", "activeCamera", "Landroidx/camera/camera2/pipe/compat/ActiveCamera;", "token", "Landroidx/camera/camera2/pipe/core/Token;", "<init>", "(Landroidx/camera/camera2/pipe/compat/RequestOpen;Landroidx/camera/camera2/pipe/compat/ActiveCamera;Landroidx/camera/camera2/pipe/core/Token;)V", "getRequest", "()Landroidx/camera/camera2/pipe/compat/RequestOpen;", "getActiveCamera", "()Landroidx/camera/camera2/pipe/compat/ActiveCamera;", "getToken", "()Landroidx/camera/camera2/pipe/core/Token;", "camera-camera2-pipe"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    public static final class PendingRequestOpen {
        private final ActiveCamera activeCamera;
        private final RequestOpen request;
        private final Token token;

        public PendingRequestOpen(RequestOpen requestOpen, ActiveCamera activeCamera, Token token) {
            this.request = requestOpen;
            this.activeCamera = activeCamera;
            this.token = token;
        }

        public final RequestOpen getRequest() {
            return this.request;
        }

        public final ActiveCamera getActiveCamera() {
            return this.activeCamera;
        }

        public final Token getToken() {
            return this.token;
        }
    }

    @Override // androidx.camera.camera2.pipe.compat.Camera2DeviceManager
    /* JADX INFO: renamed from: open-zDSwpeU */
    public VirtualCamera mo1715openzDSwpeU(String cameraId, List<CameraId> sharedCameraIds, GraphListener graphListener, boolean isPrewarm, Function1<? super Unit, Boolean> isForegroundObserver) {
        VirtualCameraState virtualCameraState = new VirtualCameraState(cameraId, graphListener, this.scope, null);
        if (this.queue.tryEmit(new RequestOpen(virtualCameraState, sharedCameraIds, graphListener, isPrewarm, isForegroundObserver))) {
            return virtualCameraState;
        }
        if (Log.INSTANCE.getERROR_LOGGABLE()) {
            android.util.Log.e("CXCP", "Camera open request failed for " + ((Object) CameraId.m1501toStringimpl(cameraId)) + '!');
        }
        graphListener.onGraphError(new GraphState.GraphStateError(CameraError.INSTANCE.m1460getERROR_CAMERA_OPENERv7Vf74A(), false, null));
        return null;
    }

    @Override // androidx.camera.camera2.pipe.compat.Camera2DeviceManager
    /* JADX INFO: renamed from: close-EfqyGwQ */
    public Deferred<Unit> mo1714closeEfqyGwQ(String cameraId) {
        RequestCloseById requestCloseById = new RequestCloseById(cameraId, null);
        if (!this.queue.tryEmit(requestCloseById)) {
            if (Log.INSTANCE.getERROR_LOGGABLE()) {
                android.util.Log.e("CXCP", "Camera close by ID request failed for " + ((Object) CameraId.m1501toStringimpl(cameraId)) + '!');
            }
            requestCloseById.getDeferred().complete(Unit.INSTANCE);
        }
        return requestCloseById.getDeferred();
    }

    @Override // androidx.camera.camera2.pipe.compat.Camera2DeviceManager
    public Deferred<Unit> closeAll(boolean forceCancelOpen) {
        if (forceCancelOpen) {
            this.retryingCameraStateOpener.cancelOpen();
        }
        RequestCloseAll requestCloseAll = new RequestCloseAll();
        if (!this.queue.tryEmit(requestCloseAll)) {
            if (Log.INSTANCE.getERROR_LOGGABLE()) {
                android.util.Log.e("CXCP", "Camera close all request failed!");
            }
            requestCloseAll.getDeferred().complete(Unit.INSTANCE);
        }
        return requestCloseAll.getDeferred();
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x0140  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void prune$camera_camera2_pipe(java.util.List<androidx.camera.camera2.pipe.compat.CameraRequest> r18) {
        /*
            Method dump skipped, instruction units count: 492
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager.prune$camera_camera2_pipe(java.util.List):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit prune$lambda$2$0(CompletableDeferred completableDeferred, Throwable th) {
        Unit unit = Unit.INSTANCE;
        completableDeferred.complete(unit);
        return unit;
    }

    /* JADX INFO: renamed from: $r8$lambda$-mG8_AgNMPKBsIwSXjNb-j0kE9U, reason: not valid java name */
    public static Unit m1733$r8$lambda$mG8_AgNMPKBsIwSXjNbj0kE9U(CameraRequest cameraRequest, Throwable th) {
        CompletableDeferred<Unit> deferred = ((RequestCloseById) cameraRequest).getDeferred();
        Unit unit = Unit.INSTANCE;
        deferred.complete(unit);
        return unit;
    }

    private final void onRemoved(CameraRequest cameraRequest) {
        if (cameraRequest instanceof RequestOpen) {
            VirtualCamera.m1752disconnectTPqeGZw$default(((RequestOpen) cameraRequest).getVirtualCamera(), null, 1, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object process(CameraRequest cameraRequest, Continuation<? super Unit> continuation) {
        if (cameraRequest instanceof RequestOpen) {
            Object objProcessRequestOpen = processRequestOpen((RequestOpen) cameraRequest, continuation);
            return objProcessRequestOpen == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objProcessRequestOpen : Unit.INSTANCE;
        }
        if (cameraRequest instanceof RequestClose) {
            Object objProcessRequestClose = processRequestClose((RequestClose) cameraRequest, continuation);
            return objProcessRequestClose == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objProcessRequestClose : Unit.INSTANCE;
        }
        if (cameraRequest instanceof RequestCloseById) {
            Object objProcessRequestCloseById = processRequestCloseById((RequestCloseById) cameraRequest, continuation);
            return objProcessRequestCloseById == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objProcessRequestCloseById : Unit.INSTANCE;
        }
        if (cameraRequest instanceof RequestCloseAll) {
            Object objProcessRequestCloseAll = processRequestCloseAll((RequestCloseAll) cameraRequest, continuation);
            return objProcessRequestCloseAll == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objProcessRequestCloseAll : Unit.INSTANCE;
        }
        LazyKt__LazyJVMKt$$ExternalSyntheticBUOutline0.m874m();
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x02c0, code lost:
    
        if (connectPendingRequestOpens(r11, r0) != r1) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x02df, code lost:
    
        if (r12.connectTo(r10, r0, r2) == r1) goto L117;
     */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0164 A[LOOP:3: B:49:0x015e->B:51:0x0164, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0178  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01ad  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01b7  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0214  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object processRequestOpen(androidx.camera.camera2.pipe.compat.RequestOpen r11, kotlin.coroutines.Continuation<? super kotlin.Unit> r12) {
        /*
            Method dump skipped, instruction units count: 770
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager.processRequestOpen(androidx.camera.camera2.pipe.compat.RequestOpen, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00cc, code lost:
    
        if (r9.awaitClosed(r0) == r1) goto L33;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object processRequestClose(androidx.camera.camera2.pipe.compat.RequestClose r10, kotlin.coroutines.Continuation<? super kotlin.Unit> r11) {
        /*
            Method dump skipped, instruction units count: 210
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager.processRequestClose(androidx.camera.camera2.pipe.compat.RequestClose, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object processRequestCloseById(androidx.camera.camera2.pipe.compat.RequestCloseById r10, kotlin.coroutines.Continuation<? super kotlin.Unit> r11) {
        /*
            Method dump skipped, instruction units count: 246
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager.processRequestCloseById(androidx.camera.camera2.pipe.compat.RequestCloseById, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x005f, code lost:
    
        if (disconnectPendingRequestOpens(r7, r0) == r1) goto L31;
     */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object processRequestCloseAll(androidx.camera.camera2.pipe.compat.RequestCloseAll r6, kotlin.coroutines.Continuation<? super kotlin.Unit> r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager.C02141
            if (r0 == 0) goto L13
            r0 = r7
            androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager$processRequestCloseAll$1 r0 = (androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager.C02141) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager$processRequestCloseAll$1 r0 = new androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager$processRequestCloseAll$1
            r0.<init>(r7)
        L18:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L43
            if (r2 == r4) goto L3b
            if (r2 != r3) goto L34
            java.lang.Object r6 = r0.L$1
            java.util.Iterator r6 = (java.util.Iterator) r6
            java.lang.Object r2 = r0.L$0
            androidx.camera.camera2.pipe.compat.RequestCloseAll r2 = (androidx.camera.camera2.pipe.compat.RequestCloseAll) r2
            kotlin.ResultKt.throwOnFailure(r7)
            goto L80
        L34:
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r5)
            r5 = 0
            return r5
        L3b:
            java.lang.Object r6 = r0.L$0
            androidx.camera.camera2.pipe.compat.RequestCloseAll r6 = (androidx.camera.camera2.pipe.compat.RequestCloseAll) r6
            kotlin.ResultKt.throwOnFailure(r7)
            goto L62
        L43:
            kotlin.ResultKt.throwOnFailure(r7)
            androidx.camera.camera2.pipe.core.Log r7 = androidx.camera.camera2.pipe.core.Log.INSTANCE
            boolean r7 = r7.getINFO_LOGGABLE()
            if (r7 == 0) goto L55
            java.lang.String r7 = "CXCP"
            java.lang.String r2 = "PruningCamera2DeviceManager#processRequestCloseAll()"
            android.util.Log.i(r7, r2)
        L55:
            java.util.List<androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager$PendingRequestOpen> r7 = r5.pendingRequestOpens
            r0.L$0 = r6
            r0.label = r4
            java.lang.Object r7 = r5.disconnectPendingRequestOpens(r7, r0)
            if (r7 != r1) goto L62
            goto L98
        L62:
            java.util.Set<androidx.camera.camera2.pipe.compat.ActiveCamera> r7 = r5.activeCameras
            java.util.Iterator r7 = r7.iterator()
        L68:
            boolean r2 = r7.hasNext()
            if (r2 == 0) goto L78
            java.lang.Object r2 = r7.next()
            androidx.camera.camera2.pipe.compat.ActiveCamera r2 = (androidx.camera.camera2.pipe.compat.ActiveCamera) r2
            r2.close()
            goto L68
        L78:
            java.util.Set<androidx.camera.camera2.pipe.compat.ActiveCamera> r7 = r5.activeCameras
            java.util.Iterator r7 = r7.iterator()
            r2 = r6
            r6 = r7
        L80:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L99
            java.lang.Object r7 = r6.next()
            androidx.camera.camera2.pipe.compat.ActiveCamera r7 = (androidx.camera.camera2.pipe.compat.ActiveCamera) r7
            r0.L$0 = r2
            r0.L$1 = r6
            r0.label = r3
            java.lang.Object r7 = r7.awaitClosed(r0)
            if (r7 != r1) goto L80
        L98:
            return r1
        L99:
            java.util.Set<androidx.camera.camera2.pipe.compat.ActiveCamera> r5 = r5.activeCameras
            r5.clear()
            kotlinx.coroutines.CompletableDeferred r5 = r2.getDeferred()
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            r5.complete(r6)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager.processRequestCloseAll(androidx.camera.camera2.pipe.compat.RequestCloseAll, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0076, code lost:
    
        r0 = r15.acquire();
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x007a, code lost:
    
        if (r0 == null) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x007d, code lost:
    
        r15.close();
        r11.L$0 = r7;
        r11.L$1 = r13;
        r11.L$2 = r14;
        r11.L$3 = r15;
        r11.label = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x008e, code lost:
    
        if (r15.awaitClosed(r11) != r1) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0091, code lost:
    
        r2 = r13;
        r13 = r15;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0169  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x009a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:27:0x0091 -> B:28:0x0093). Please report as a decompilation issue!!! */
    /* JADX INFO: renamed from: retrieveActiveCamera-RzXb1QE, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object m1738retrieveActiveCameraRzXb1QE(java.lang.String r13, androidx.camera.camera2.pipe.compat.RequestOpen r14, kotlin.coroutines.Continuation<? super androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager.RetrieveActiveCameraResult> r15) {
        /*
            Method dump skipped, instruction units count: 367
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager.m1738retrieveActiveCameraRzXb1QE(java.lang.String, androidx.camera.camera2.pipe.compat.RequestOpen, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX INFO: renamed from: openCameraWithRetry-zDSwpeU, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object m1737openCameraWithRetryzDSwpeU(java.lang.String r6, java.util.List<androidx.camera.camera2.pipe.CameraId> r7, kotlin.jvm.functions.Function1<? super kotlin.Unit, java.lang.Boolean> r8, kotlinx.coroutines.CoroutineScope r9, kotlin.coroutines.Continuation<? super androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager.OpenVirtualCameraResult> r10) {
        /*
            r5 = this;
            boolean r0 = r10 instanceof androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager$openCameraWithRetry$1
            if (r0 == 0) goto L13
            r0 = r10
            androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager$openCameraWithRetry$1 r0 = (androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager$openCameraWithRetry$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager$openCameraWithRetry$1 r0 = new androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager$openCameraWithRetry$1
            r0.<init>(r5, r10)
        L18:
            java.lang.Object r10 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L3e
            if (r2 != r4) goto L38
            java.lang.Object r6 = r0.L$2
            r9 = r6
            kotlinx.coroutines.CoroutineScope r9 = (kotlinx.coroutines.CoroutineScope) r9
            java.lang.Object r6 = r0.L$1
            r7 = r6
            java.util.List r7 = (java.util.List) r7
            java.lang.Object r6 = r0.L$0
            java.lang.String r6 = (java.lang.String) r6
            kotlin.ResultKt.throwOnFailure(r10)
            goto L78
        L38:
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r5)
            return r3
        L3e:
            kotlin.ResultKt.throwOnFailure(r10)
            androidx.camera.camera2.pipe.core.Log r10 = androidx.camera.camera2.pipe.core.Log.INSTANCE
            boolean r10 = r10.getDEBUG_LOGGABLE()
            if (r10 == 0) goto L65
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r2 = "Opening "
            r10.<init>(r2)
            java.lang.String r2 = androidx.camera.camera2.pipe.CameraId.m1501toStringimpl(r6)
            r10.append(r2)
            java.lang.String r2 = " with retries..."
            r10.append(r2)
            java.lang.String r10 = r10.toString()
            java.lang.String r2 = "CXCP"
            android.util.Log.d(r2, r10)
        L65:
            androidx.camera.camera2.pipe.compat.RetryingCameraStateOpener r10 = r5.retryingCameraStateOpener
            androidx.camera.camera2.pipe.compat.Camera2DeviceCloser r2 = r5.camera2DeviceCloser
            r0.L$0 = r6
            r0.L$1 = r7
            r0.L$2 = r9
            r0.label = r4
            java.lang.Object r10 = r10.mo1745openCameraWithRetryaeCOTgg(r6, r2, r8, r0)
            if (r10 != r1) goto L78
            return r1
        L78:
            androidx.camera.camera2.pipe.compat.OpenCameraResult r10 = (androidx.camera.camera2.pipe.compat.OpenCameraResult) r10
            androidx.camera.camera2.pipe.compat.AndroidCameraState r8 = r10.getCameraState()
            if (r8 != 0) goto L8a
            androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager$OpenVirtualCameraResult$Error r5 = new androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager$OpenVirtualCameraResult$Error
            androidx.camera.camera2.pipe.CameraError r6 = r10.getErrorCode()
            r5.<init>(r6, r3)
            return r5
        L8a:
            androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager$OpenVirtualCameraResult$Success r8 = new androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager$OpenVirtualCameraResult$Success
            androidx.camera.camera2.pipe.compat.ActiveCamera r0 = new androidx.camera.camera2.pipe.compat.ActiveCamera
            androidx.camera.camera2.pipe.compat.AndroidCameraState r10 = r10.getCameraState()
            java.util.Collection r7 = (java.util.Collection) r7
            androidx.camera.camera2.pipe.CameraId r6 = androidx.camera.camera2.pipe.CameraId.m1496boximpl(r6)
            java.util.List r6 = kotlin.collections.CollectionsKt.plus(r7, r6)
            java.lang.Iterable r6 = (java.lang.Iterable) r6
            java.util.Set r6 = kotlin.collections.CollectionsKt.toSet(r6)
            androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager$$ExternalSyntheticLambda2 r7 = new androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager$$ExternalSyntheticLambda2
            r7.<init>()
            r0.<init>(r10, r6, r9, r7)
            r8.<init>(r0)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager.m1737openCameraWithRetryzDSwpeU(java.lang.String, java.util.List, kotlin.jvm.functions.Function1, kotlinx.coroutines.CoroutineScope, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static Unit $r8$lambda$vP9kzpFdONuWMUT974T0jXAWwcs(PruningCamera2DeviceManager pruningCamera2DeviceManager, ActiveCamera activeCamera) {
        pruningCamera2DeviceManager.queue.tryEmit(new RequestClose(activeCamera));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00ea, code lost:
    
        okio.Segment$$ExternalSyntheticBUOutline1.m992m("Check failed.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00ef, code lost:
    
        return null;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:44:0x0106 -> B:46:0x0109). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object connectPendingRequestOpens(java.util.Set<androidx.camera.camera2.pipe.CameraId> r10, kotlin.coroutines.Continuation<? super kotlin.Unit> r11) {
        /*
            Method dump skipped, instruction units count: 275
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.compat.PruningCamera2DeviceManager.connectPendingRequestOpens(java.util.Set, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final Object disconnectPendingRequestOpens(List<PendingRequestOpen> list, Continuation<? super Unit> continuation) {
        for (PendingRequestOpen pendingRequestOpen : list) {
            pendingRequestOpen.getToken().release();
            this.pendingRequestOpens.remove(pendingRequestOpen);
        }
        return Unit.INSTANCE;
    }

    private final <T> List<T> removeIndices(List<T> list, Set<Integer> set) {
        ArrayList arrayList = new ArrayList();
        Iterator it = CollectionsKt.sorted(set).iterator();
        while (it.hasNext()) {
            arrayList.add(list.remove(((Number) it.next()).intValue() - arrayList.size()));
        }
        return arrayList;
    }

    @Metadata(m876d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\br\u0018\u00002\u00020\u0001:\u0002\u0002\u0003\u0082\u0001\u0002\u0004\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0006À\u0006\u0001"}, m877d2 = {"Landroidx/camera/camera2/pipe/compat/PruningCamera2DeviceManager$RetrieveActiveCameraResult;", _UrlKt.FRAGMENT_ENCODE_SET, "Success", "Error", "Landroidx/camera/camera2/pipe/compat/PruningCamera2DeviceManager$RetrieveActiveCameraResult$Error;", "Landroidx/camera/camera2/pipe/compat/PruningCamera2DeviceManager$RetrieveActiveCameraResult$Success;", "camera-camera2-pipe"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    public interface RetrieveActiveCameraResult {

        @Metadata(m876d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\t\u001a\u00020\bHÖ\u0001¢\u0006\u0004\b\t\u0010\nJ\u0010\u0010\f\u001a\u00020\u000bHÖ\u0001¢\u0006\u0004\b\f\u0010\rJ\u001a\u0010\u0011\u001a\u00020\u00102\b\u0010\u000f\u001a\u0004\u0018\u00010\u000eHÖ\u0003¢\u0006\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n\u0004\b\u0003\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0005\u001a\u00020\u00048\u0006¢\u0006\f\n\u0004\b\u0005\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018¨\u0006\u0019"}, m877d2 = {"Landroidx/camera/camera2/pipe/compat/PruningCamera2DeviceManager$RetrieveActiveCameraResult$Success;", "Landroidx/camera/camera2/pipe/compat/PruningCamera2DeviceManager$RetrieveActiveCameraResult;", "Landroidx/camera/camera2/pipe/compat/ActiveCamera;", "activeCamera", "Landroidx/camera/camera2/pipe/core/Token;", "token", "<init>", "(Landroidx/camera/camera2/pipe/compat/ActiveCamera;Landroidx/camera/camera2/pipe/core/Token;)V", _UrlKt.FRAGMENT_ENCODE_SET, "toString", "()Ljava/lang/String;", _UrlKt.FRAGMENT_ENCODE_SET, "hashCode", "()I", _UrlKt.FRAGMENT_ENCODE_SET, "other", _UrlKt.FRAGMENT_ENCODE_SET, "equals", "(Ljava/lang/Object;)Z", "Landroidx/camera/camera2/pipe/compat/ActiveCamera;", "getActiveCamera", "()Landroidx/camera/camera2/pipe/compat/ActiveCamera;", "Landroidx/camera/camera2/pipe/core/Token;", "getToken", "()Landroidx/camera/camera2/pipe/core/Token;", "camera-camera2-pipe"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
        public static final /* data */ class Success implements RetrieveActiveCameraResult {
            private final ActiveCamera activeCamera;
            private final Token token;

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                if (!(other instanceof Success)) {
                    return false;
                }
                Success success = (Success) other;
                return Intrinsics.areEqual(this.activeCamera, success.activeCamera) && Intrinsics.areEqual(this.token, success.token);
            }

            public int hashCode() {
                return (this.activeCamera.hashCode() * 31) + this.token.hashCode();
            }

            public String toString() {
                return "Success(activeCamera=" + this.activeCamera + ", token=" + this.token + ')';
            }

            public Success(ActiveCamera activeCamera, Token token) {
                this.activeCamera = activeCamera;
                this.token = token;
            }

            public final ActiveCamera getActiveCamera() {
                return this.activeCamera;
            }

            public final Token getToken() {
                return this.token;
            }
        }

        @Metadata(m876d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0086\b\u0018\u00002\u00020\u0001B\u0011\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0007\u001a\u00020\u0006HÖ\u0001¢\u0006\u0004\b\u0007\u0010\bJ\u0010\u0010\n\u001a\u00020\tHÖ\u0001¢\u0006\u0004\b\n\u0010\u000bJ\u001a\u0010\u000f\u001a\u00020\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\fHÖ\u0003¢\u0006\u0004\b\u000f\u0010\u0010R\u0019\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006¢\u0006\f\n\u0004\b\u0003\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0014"}, m877d2 = {"Landroidx/camera/camera2/pipe/compat/PruningCamera2DeviceManager$RetrieveActiveCameraResult$Error;", "Landroidx/camera/camera2/pipe/compat/PruningCamera2DeviceManager$RetrieveActiveCameraResult;", "Landroidx/camera/camera2/pipe/CameraError;", "lastCameraError", "<init>", "(Landroidx/camera/camera2/pipe/CameraError;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", _UrlKt.FRAGMENT_ENCODE_SET, "toString", "()Ljava/lang/String;", _UrlKt.FRAGMENT_ENCODE_SET, "hashCode", "()I", _UrlKt.FRAGMENT_ENCODE_SET, "other", _UrlKt.FRAGMENT_ENCODE_SET, "equals", "(Ljava/lang/Object;)Z", "Landroidx/camera/camera2/pipe/CameraError;", "getLastCameraError-mVEW8x0", "()Landroidx/camera/camera2/pipe/CameraError;", "camera-camera2-pipe"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
        public static final /* data */ class Error implements RetrieveActiveCameraResult {
            private final CameraError lastCameraError;

            public /* synthetic */ Error(CameraError cameraError, DefaultConstructorMarker defaultConstructorMarker) {
                this(cameraError);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                return (other instanceof Error) && Intrinsics.areEqual(this.lastCameraError, ((Error) other).lastCameraError);
            }

            public int hashCode() {
                CameraError cameraError = this.lastCameraError;
                if (cameraError == null) {
                    return 0;
                }
                return CameraError.m1448hashCodeimpl(cameraError.getValue());
            }

            public String toString() {
                return "Error(lastCameraError=" + this.lastCameraError + ')';
            }

            private Error(CameraError cameraError) {
                this.lastCameraError = cameraError;
            }

            /* JADX INFO: renamed from: getLastCameraError-mVEW8x0, reason: not valid java name and from getter */
            public final CameraError getLastCameraError() {
                return this.lastCameraError;
            }
        }
    }

    @Metadata(m876d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\br\u0018\u00002\u00020\u0001:\u0002\u0002\u0003\u0082\u0001\u0002\u0004\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0006À\u0006\u0001"}, m877d2 = {"Landroidx/camera/camera2/pipe/compat/PruningCamera2DeviceManager$OpenVirtualCameraResult;", _UrlKt.FRAGMENT_ENCODE_SET, "Success", "Error", "Landroidx/camera/camera2/pipe/compat/PruningCamera2DeviceManager$OpenVirtualCameraResult$Error;", "Landroidx/camera/camera2/pipe/compat/PruningCamera2DeviceManager$OpenVirtualCameraResult$Success;", "camera-camera2-pipe"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    public interface OpenVirtualCameraResult {

        @Metadata(m876d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0007\u001a\u00020\u0006HÖ\u0001¢\u0006\u0004\b\u0007\u0010\bJ\u0010\u0010\n\u001a\u00020\tHÖ\u0001¢\u0006\u0004\b\n\u0010\u000bJ\u001a\u0010\u000f\u001a\u00020\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\fHÖ\u0003¢\u0006\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n\u0004\b\u0003\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0014"}, m877d2 = {"Landroidx/camera/camera2/pipe/compat/PruningCamera2DeviceManager$OpenVirtualCameraResult$Success;", "Landroidx/camera/camera2/pipe/compat/PruningCamera2DeviceManager$OpenVirtualCameraResult;", "Landroidx/camera/camera2/pipe/compat/ActiveCamera;", "activeCamera", "<init>", "(Landroidx/camera/camera2/pipe/compat/ActiveCamera;)V", _UrlKt.FRAGMENT_ENCODE_SET, "toString", "()Ljava/lang/String;", _UrlKt.FRAGMENT_ENCODE_SET, "hashCode", "()I", _UrlKt.FRAGMENT_ENCODE_SET, "other", _UrlKt.FRAGMENT_ENCODE_SET, "equals", "(Ljava/lang/Object;)Z", "Landroidx/camera/camera2/pipe/compat/ActiveCamera;", "getActiveCamera", "()Landroidx/camera/camera2/pipe/compat/ActiveCamera;", "camera-camera2-pipe"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
        public static final /* data */ class Success implements OpenVirtualCameraResult {
            private final ActiveCamera activeCamera;

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                return (other instanceof Success) && Intrinsics.areEqual(this.activeCamera, ((Success) other).activeCamera);
            }

            public int hashCode() {
                return this.activeCamera.hashCode();
            }

            public String toString() {
                return "Success(activeCamera=" + this.activeCamera + ')';
            }

            public Success(ActiveCamera activeCamera) {
                this.activeCamera = activeCamera;
            }

            public final ActiveCamera getActiveCamera() {
                return this.activeCamera;
            }
        }

        @Metadata(m876d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0086\b\u0018\u00002\u00020\u0001B\u0011\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0007\u001a\u00020\u0006HÖ\u0001¢\u0006\u0004\b\u0007\u0010\bJ\u0010\u0010\n\u001a\u00020\tHÖ\u0001¢\u0006\u0004\b\n\u0010\u000bJ\u001a\u0010\u000f\u001a\u00020\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\fHÖ\u0003¢\u0006\u0004\b\u000f\u0010\u0010R\u0019\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006¢\u0006\f\n\u0004\b\u0003\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0014"}, m877d2 = {"Landroidx/camera/camera2/pipe/compat/PruningCamera2DeviceManager$OpenVirtualCameraResult$Error;", "Landroidx/camera/camera2/pipe/compat/PruningCamera2DeviceManager$OpenVirtualCameraResult;", "Landroidx/camera/camera2/pipe/CameraError;", "lastCameraError", "<init>", "(Landroidx/camera/camera2/pipe/CameraError;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", _UrlKt.FRAGMENT_ENCODE_SET, "toString", "()Ljava/lang/String;", _UrlKt.FRAGMENT_ENCODE_SET, "hashCode", "()I", _UrlKt.FRAGMENT_ENCODE_SET, "other", _UrlKt.FRAGMENT_ENCODE_SET, "equals", "(Ljava/lang/Object;)Z", "Landroidx/camera/camera2/pipe/CameraError;", "getLastCameraError-mVEW8x0", "()Landroidx/camera/camera2/pipe/CameraError;", "camera-camera2-pipe"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
        public static final /* data */ class Error implements OpenVirtualCameraResult {
            private final CameraError lastCameraError;

            public /* synthetic */ Error(CameraError cameraError, DefaultConstructorMarker defaultConstructorMarker) {
                this(cameraError);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                return (other instanceof Error) && Intrinsics.areEqual(this.lastCameraError, ((Error) other).lastCameraError);
            }

            public int hashCode() {
                CameraError cameraError = this.lastCameraError;
                if (cameraError == null) {
                    return 0;
                }
                return CameraError.m1448hashCodeimpl(cameraError.getValue());
            }

            public String toString() {
                return "Error(lastCameraError=" + this.lastCameraError + ')';
            }

            private Error(CameraError cameraError) {
                this.lastCameraError = cameraError;
            }

            /* JADX INFO: renamed from: getLastCameraError-mVEW8x0, reason: not valid java name and from getter */
            public final CameraError getLastCameraError() {
                return this.lastCameraError;
            }
        }
    }
}
