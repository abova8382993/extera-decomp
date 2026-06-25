package androidx.camera.camera2.impl;

import android.hardware.camera2.CaptureRequest;
import android.util.Log;
import androidx.camera.camera2.compat.workaround.TemplateParamsOverride;
import androidx.camera.camera2.config.UseCaseGraphContext;
import androidx.camera.camera2.pipe.AeMode;
import androidx.camera.camera2.pipe.AfMode;
import androidx.camera.camera2.pipe.AwbMode;
import androidx.camera.camera2.pipe.CameraControls3A;
import androidx.camera.camera2.pipe.CameraGraph;
import androidx.camera.camera2.pipe.FrameInfo;
import androidx.camera.camera2.pipe.Metadata;
import androidx.camera.camera2.pipe.Request;
import androidx.camera.camera2.pipe.RequestFailure;
import androidx.camera.camera2.pipe.RequestMetadata;
import androidx.camera.camera2.pipe.RequestTemplate;
import androidx.camera.camera2.pipe.StreamId;
import androidx.camera.core.Logger;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicInt;
import kotlinx.coroutines.CompletableDeferred;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes3.dex */
@Metadata(m876d1 = {"\u0000®\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001:\u0002OPB\u0019\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\t\u001a\u00020\bH\u0082@¢\u0006\u0004\b\t\u0010\nJ-\u0010\u000f\u001a\u00020\b*\u00020\u000b2\u0018\u0010\u000e\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\r\u0012\u0004\u0012\u00020\u0001\u0018\u00010\fH\u0002¢\u0006\u0004\b\u000f\u0010\u0010J3\u0010\u0013\u001a\u0004\u0018\u00010\u0012*\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\r\u0012\u0004\u0012\u00020\u0001\u0018\u00010\f2\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\rH\u0002¢\u0006\u0004\b\u0013\u0010\u0014J\u0092\u0001\u0010$\u001a\b\u0012\u0004\u0012\u00020\b0!2\u001a\b\u0002\u0010\u000e\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\r\u0012\u0004\u0012\u00020\u0001\u0018\u00010\f2\b\b\u0002\u0010\u0016\u001a\u00020\u00152\u001a\b\u0002\u0010\u0018\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0017\u0012\u0004\u0012\u00020\u0001\u0018\u00010\f2\b\b\u0002\u0010\u0019\u001a\u00020\u00152\u0010\b\u0002\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u001b\u0018\u00010\u001a2\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u001d2\u0010\b\u0002\u0010 \u001a\n\u0012\u0004\u0012\u00020\u001f\u0018\u00010\u001aH\u0086@¢\u0006\u0004\b\"\u0010#J\u0010\u0010%\u001a\u00020\bH\u0086@¢\u0006\u0004\b%\u0010\nJ\r\u0010&\u001a\u00020\b¢\u0006\u0004\b&\u0010'R\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010(R\u0014\u0010\u0005\u001a\u00020\u00048\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0005\u0010)R\u0014\u0010*\u001a\u00020\u00018\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b*\u0010+R\u001e\u0010-\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010,8\u0002@\u0002X\u0083\u000e¢\u0006\u0006\n\u0004\b-\u0010.R\u0014\u00100\u001a\u00020/8\u0002X\u0083\u0004¢\u0006\u0006\n\u0004\b0\u00101R\u001c\u00104\u001a\b\u0012\u0004\u0012\u000203028\u0002@\u0002X\u0083\u000e¢\u0006\u0006\n\u0004\b4\u00105R\u0016\u00106\u001a\u00020\u00158\u0002@\u0002X\u0083\u000e¢\u0006\u0006\n\u0004\b6\u00107R$\u00109\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\r\u0012\u0004\u0012\u00020\u0001088\u0002X\u0083\u0004¢\u0006\u0006\n\u0004\b9\u0010:R$\u0010;\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0017\u0012\u0004\u0012\u00020\u0001088\u0002X\u0083\u0004¢\u0006\u0006\n\u0004\b;\u0010:R\u001a\u0010=\u001a\b\u0012\u0004\u0012\u00020\u001b0<8\u0002X\u0083\u0004¢\u0006\u0006\n\u0004\b=\u0010>R\u001a\u0010?\u001a\b\u0012\u0004\u0012\u00020\u001f0<8\u0002X\u0083\u0004¢\u0006\u0006\n\u0004\b?\u0010>R\u0018\u0010@\u001a\u0004\u0018\u00010\u001d8\u0002@\u0002X\u0083\u000e¢\u0006\u0006\n\u0004\b@\u0010AR\u0018\u0010C\u001a\u0004\u0018\u00010B8\u0002@\u0002X\u0083\u000e¢\u0006\u0006\n\u0004\bC\u0010DR\u0018\u0010F\u001a\u0004\u0018\u00010E8\u0002@\u0002X\u0083\u000e¢\u0006\u0006\n\u0004\bF\u0010GR\u0018\u0010I\u001a\u0004\u0018\u00010H8\u0002@\u0002X\u0083\u000e¢\u0006\u0006\n\u0004\bI\u0010JR\u0018\u0010L\u001a\u00060KR\u00020\u00008\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bL\u0010MR\u0014\u0010N\u001a\u00020/8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bN\u00101¨\u0006Q"}, m877d2 = {"Landroidx/camera/camera2/impl/UseCaseCameraState;", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/camera2/config/UseCaseGraphContext;", "useCaseGraphContext", "Landroidx/camera/camera2/compat/workaround/TemplateParamsOverride;", "templateParamsOverride", "<init>", "(Landroidx/camera/camera2/config/UseCaseGraphContext;Landroidx/camera/camera2/compat/workaround/TemplateParamsOverride;)V", _UrlKt.FRAGMENT_ENCODE_SET, "submitLatest", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Landroidx/camera/camera2/pipe/CameraGraph$Session;", _UrlKt.FRAGMENT_ENCODE_SET, "Landroid/hardware/camera2/CaptureRequest$Key;", "parameters", "update3A", "(Landroidx/camera/camera2/pipe/CameraGraph$Session;Ljava/util/Map;)V", "key", _UrlKt.FRAGMENT_ENCODE_SET, "getIntOrNull", "(Ljava/util/Map;Landroid/hardware/camera2/CaptureRequest$Key;)Ljava/lang/Integer;", _UrlKt.FRAGMENT_ENCODE_SET, "appendParameters", "Landroidx/camera/camera2/pipe/Metadata$Key;", "internalParameters", "appendInternalParameters", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/camera2/pipe/StreamId;", "streams", "Landroidx/camera/camera2/pipe/RequestTemplate;", "template", "Landroidx/camera/camera2/pipe/Request$Listener;", "listeners", "Lkotlinx/coroutines/Deferred;", "updateAsync-Tp9XwKQ", "(Ljava/util/Map;ZLjava/util/Map;ZLjava/util/Set;Landroidx/camera/camera2/pipe/RequestTemplate;Ljava/util/Set;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateAsync", "tryStartRepeating", "close", "()V", "Landroidx/camera/camera2/config/UseCaseGraphContext;", "Landroidx/camera/camera2/compat/workaround/TemplateParamsOverride;", "lock", "Ljava/lang/Object;", "Lkotlinx/coroutines/CompletableDeferred;", "updateSignal", "Lkotlinx/coroutines/CompletableDeferred;", "Lkotlinx/atomicfu/AtomicInt;", "submittedRequestCounter", "Lkotlinx/atomicfu/AtomicInt;", "Lkotlin/collections/ArrayDeque;", "Landroidx/camera/camera2/impl/UseCaseCameraState$RequestSignal;", "updateSignals", "Lkotlin/collections/ArrayDeque;", "updating", "Z", _UrlKt.FRAGMENT_ENCODE_SET, "currentParameters", "Ljava/util/Map;", "currentInternalParameters", _UrlKt.FRAGMENT_ENCODE_SET, "currentStreams", "Ljava/util/Set;", "currentListeners", "currentTemplate", "Landroidx/camera/camera2/pipe/RequestTemplate;", "Landroidx/camera/camera2/pipe/AeMode;", "lastAeMode", "Landroidx/camera/camera2/pipe/AeMode;", "Landroidx/camera/camera2/pipe/AfMode;", "lastAfMode", "Landroidx/camera/camera2/pipe/AfMode;", "Landroidx/camera/camera2/pipe/AwbMode;", "lastAwbMode", "Landroidx/camera/camera2/pipe/AwbMode;", "Landroidx/camera/camera2/impl/UseCaseCameraState$RequestListener;", "requestListener", "Landroidx/camera/camera2/impl/UseCaseCameraState$RequestListener;", "pendingSignalCount", "RequestSignal", "RequestListener", "camera-camera2"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nUseCaseCameraState.kt\nKotlin\n*S Kotlin\n*F\n+ 1 UseCaseCameraState.kt\nandroidx/camera/camera2/impl/UseCaseCameraState\n+ 2 Camera2Logger.kt\nandroidx/camera/camera2/impl/Camera2Logger\n+ 3 UseCaseCameraConfig.kt\nandroidx/camera/camera2/config/UseCaseGraphContext\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,399:1\n166#1:400\n167#1,2:403\n171#1,24:406\n85#2,2:401\n88#2:405\n85#2,4:430\n85#2,4:436\n95#2,4:440\n85#2,4:444\n242#3:434\n1#4:435\n*S KotlinDebug\n*F\n+ 1 UseCaseCameraState.kt\nandroidx/camera/camera2/impl/UseCaseCameraState\n*L\n128#1:400\n128#1:403,2\n128#1:406,24\n128#1:401,2\n128#1:405\n166#1:430,4\n255#1:436,4\n261#1:440,4\n316#1:444,4\n213#1:434\n213#1:435\n*E\n"})
public final class UseCaseCameraState {
    private RequestTemplate currentTemplate;
    private AeMode lastAeMode;
    private AfMode lastAfMode;
    private AwbMode lastAwbMode;
    private final TemplateParamsOverride templateParamsOverride;
    private CompletableDeferred<Unit> updateSignal;
    private boolean updating;
    private final UseCaseGraphContext useCaseGraphContext;
    private final Object lock = new Object();
    private final AtomicInt submittedRequestCounter = AtomicFU.atomic(0);
    private ArrayDeque<RequestSignal> updateSignals = new ArrayDeque<>();
    private final Map<CaptureRequest.Key<?>, Object> currentParameters = new LinkedHashMap();
    private final Map<Metadata.Key<?>, Object> currentInternalParameters = new LinkedHashMap();
    private final Set<StreamId> currentStreams = new LinkedHashSet();
    private final Set<Request.Listener> currentListeners = new LinkedHashSet();
    private final RequestListener requestListener = new RequestListener();
    private final AtomicInt pendingSignalCount = AtomicFU.atomic(0);

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.UseCaseCameraState$submitLatest$1 */
    @kotlin.Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.UseCaseCameraState", m896f = "UseCaseCameraState.kt", m897i = {0}, m898l = {400}, m899m = "submitLatest", m900n = {"signalToComplete"}, m902s = {"L$0"}, m903v = 1)
    public static final class C01771 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C01771(Continuation<? super C01771> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return UseCaseCameraState.this.submitLatest(this);
        }
    }

    public UseCaseCameraState(UseCaseGraphContext useCaseGraphContext, TemplateParamsOverride templateParamsOverride) {
        this.useCaseGraphContext = useCaseGraphContext;
        this.templateParamsOverride = templateParamsOverride;
    }

    @kotlin.Metadata(m876d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0004\b\u0007\u0010\bJ\u0010\u0010\n\u001a\u00020\tHÖ\u0001¢\u0006\u0004\b\n\u0010\u000bJ\u0010\u0010\f\u001a\u00020\u0002HÖ\u0001¢\u0006\u0004\b\f\u0010\rJ\u001a\u0010\u0010\u001a\u00020\u000f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n\u0004\b\u0003\u0010\u0012\u001a\u0004\b\u0013\u0010\rR\u001d\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006¢\u0006\f\n\u0004\b\u0006\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u0017"}, m877d2 = {"Landroidx/camera/camera2/impl/UseCaseCameraState$RequestSignal;", _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, "requestNo", "Lkotlinx/coroutines/CompletableDeferred;", _UrlKt.FRAGMENT_ENCODE_SET, "signal", "<init>", "(ILkotlinx/coroutines/CompletableDeferred;)V", _UrlKt.FRAGMENT_ENCODE_SET, "toString", "()Ljava/lang/String;", "hashCode", "()I", "other", _UrlKt.FRAGMENT_ENCODE_SET, "equals", "(Ljava/lang/Object;)Z", "I", "getRequestNo", "Lkotlinx/coroutines/CompletableDeferred;", "getSignal", "()Lkotlinx/coroutines/CompletableDeferred;", "camera-camera2"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    public static final /* data */ class RequestSignal {
        private final int requestNo;
        private final CompletableDeferred<Unit> signal;

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof RequestSignal)) {
                return false;
            }
            RequestSignal requestSignal = (RequestSignal) other;
            return this.requestNo == requestSignal.requestNo && Intrinsics.areEqual(this.signal, requestSignal.signal);
        }

        public int hashCode() {
            return (Integer.hashCode(this.requestNo) * 31) + this.signal.hashCode();
        }

        public String toString() {
            return "RequestSignal(requestNo=" + this.requestNo + ", signal=" + this.signal + ')';
        }

        public RequestSignal(int i, CompletableDeferred<Unit> completableDeferred) {
            this.requestNo = i;
            this.signal = completableDeferred;
        }

        public final int getRequestNo() {
            return this.requestNo;
        }

        public final CompletableDeferred<Unit> getSignal() {
            return this.signal;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x001f  */
    /* JADX WARN: Type inference failed for: r0v3, types: [T, kotlinx.coroutines.CompletableDeferred<kotlin.Unit>] */
    /* JADX INFO: renamed from: updateAsync-Tp9XwKQ, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object m1373updateAsyncTp9XwKQ(java.util.Map<android.hardware.camera2.CaptureRequest.Key<?>, ? extends java.lang.Object> r15, boolean r16, java.util.Map<androidx.camera.camera2.pipe.Metadata.Key<?>, ? extends java.lang.Object> r17, boolean r18, java.util.Set<androidx.camera.camera2.pipe.StreamId> r19, androidx.camera.camera2.pipe.RequestTemplate r20, java.util.Set<? extends androidx.camera.camera2.pipe.Request.Listener> r21, kotlin.coroutines.Continuation<? super kotlinx.coroutines.Deferred<kotlin.Unit>> r22) {
        /*
            Method dump skipped, instruction units count: 233
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.UseCaseCameraState.m1373updateAsyncTp9XwKQ(java.util.Map, boolean, java.util.Map, boolean, java.util.Set, androidx.camera.camera2.pipe.RequestTemplate, java.util.Set, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final Object tryStartRepeating(Continuation<? super Unit> continuation) throws Exception {
        Object objSubmitLatest = submitLatest(continuation);
        return objSubmitLatest == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objSubmitLatest : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:65:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0162 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v10, types: [T, kotlinx.coroutines.CompletableDeferred<kotlin.Unit>] */
    /* JADX WARN: Type inference failed for: r10v10, types: [T, kotlinx.coroutines.CompletableDeferred<kotlin.Unit>] */
    /* JADX WARN: Type inference failed for: r10v9, types: [T, androidx.camera.camera2.pipe.Request] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object submitLatest(kotlin.coroutines.Continuation<? super kotlin.Unit> r20) throws java.lang.Exception {
        /*
            Method dump skipped, instruction units count: 392
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.UseCaseCameraState.submitLatest(kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void close() {
        synchronized (this.lock) {
            try {
                if (this.updating) {
                    this.updating = false;
                    CompletableDeferred<Unit> completableDeferred = this.updateSignal;
                    if (completableDeferred != null) {
                        completableDeferred.completeExceptionally(new CancellationException("UseCaseCameraState closed"));
                    }
                    this.updateSignal = null;
                }
                while (!this.updateSignals.isEmpty()) {
                    this.updateSignals.removeFirst().getSignal().completeExceptionally(new CancellationException("UseCaseCameraState closed"));
                    this.pendingSignalCount.decrementAndGet();
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private final void update3A(CameraGraph.Session session, Map<CaptureRequest.Key<?>, ? extends Object> map) {
        Integer intOrNull = getIntOrNull(map, CaptureRequest.CONTROL_AE_MODE);
        AeMode aeModeM1386fromIntOrNullkQd0u18 = intOrNull != null ? AeMode.INSTANCE.m1386fromIntOrNullkQd0u18(intOrNull.intValue()) : null;
        Integer intOrNull2 = getIntOrNull(map, CaptureRequest.CONTROL_AF_MODE);
        AfMode afModeM1397fromIntOrNullMKXwA8g = intOrNull2 != null ? AfMode.INSTANCE.m1397fromIntOrNullMKXwA8g(intOrNull2.intValue()) : null;
        Integer intOrNull3 = getIntOrNull(map, CaptureRequest.CONTROL_AWB_MODE);
        AwbMode awbModeM1416fromIntOrNullSaEiwI = intOrNull3 != null ? AwbMode.INSTANCE.m1416fromIntOrNullSaEiwI(intOrNull3.intValue()) : null;
        boolean z = false;
        boolean z2 = (aeModeM1386fromIntOrNullkQd0u18 == null || Intrinsics.areEqual(aeModeM1386fromIntOrNullkQd0u18, this.lastAeMode)) ? false : true;
        boolean z3 = (afModeM1397fromIntOrNullMKXwA8g == null || Intrinsics.areEqual(afModeM1397fromIntOrNullMKXwA8g, this.lastAfMode)) ? false : true;
        if (awbModeM1416fromIntOrNullSaEiwI != null && !Intrinsics.areEqual(awbModeM1416fromIntOrNullSaEiwI, this.lastAwbMode)) {
            z = true;
        }
        if (z2 || z3 || z) {
            Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
            if (Logger.isDebugEnabled("CXCP")) {
                Log.d(Camera2Logger.TRUNCATED_TAG, "UseCaseCameraState: Updating 3A modes: AE(" + aeModeM1386fromIntOrNullkQd0u18 + ", changed=" + z2 + "), AF(" + afModeM1397fromIntOrNullMKXwA8g + ", changed=" + z3 + "), AWB(" + awbModeM1416fromIntOrNullSaEiwI + ", changed=" + z + ')');
            }
            CameraControls3A.m1433update3AydBZfZg$default(session, aeModeM1386fromIntOrNullkQd0u18, afModeM1397fromIntOrNullMKXwA8g, awbModeM1416fromIntOrNullSaEiwI, null, null, null, 56, null);
            if (aeModeM1386fromIntOrNullkQd0u18 != null) {
                this.lastAeMode = aeModeM1386fromIntOrNullkQd0u18;
            }
            if (afModeM1397fromIntOrNullMKXwA8g != null) {
                this.lastAfMode = afModeM1397fromIntOrNullMKXwA8g;
            }
            if (awbModeM1416fromIntOrNullSaEiwI != null) {
                this.lastAwbMode = awbModeM1416fromIntOrNullSaEiwI;
            }
        }
    }

    private final Integer getIntOrNull(Map<CaptureRequest.Key<?>, ? extends Object> map, CaptureRequest.Key<?> key) {
        Object obj = map != null ? map.get(key) : null;
        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        return null;
    }

    @kotlin.Metadata(m876d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0003\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J'\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0004\b\f\u0010\rJ'\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016¢\u0006\u0004\b\u0011\u0010\u0012J\u001c\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0002J\u001a\u0010\u0014\u001a\u00020\u0005*\b\u0012\u0004\u0012\u00020\u00160\u00152\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\"\u0010\u0013\u001a\u00020\u0005*\b\u0012\u0004\u0012\u00020\u00160\u00152\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0002¨\u0006\u001b"}, m877d2 = {"Landroidx/camera/camera2/impl/UseCaseCameraState$RequestListener;", "Landroidx/camera/camera2/pipe/Request$Listener;", "<init>", "(Landroidx/camera/camera2/impl/UseCaseCameraState;)V", "onTotalCaptureResult", _UrlKt.FRAGMENT_ENCODE_SET, "requestMetadata", "Landroidx/camera/camera2/pipe/RequestMetadata;", "frameNumber", "Landroidx/camera/camera2/pipe/FrameNumber;", "totalCaptureResult", "Landroidx/camera/camera2/pipe/FrameInfo;", "onTotalCaptureResult-CcXjc1I", "(Landroidx/camera/camera2/pipe/RequestMetadata;JLandroidx/camera/camera2/pipe/FrameInfo;)V", "onFailed", "requestFailure", "Landroidx/camera/camera2/pipe/RequestFailure;", "onFailed-CcXjc1I", "(Landroidx/camera/camera2/pipe/RequestMetadata;JLandroidx/camera/camera2/pipe/RequestFailure;)V", "completeExceptionally", "complete", "Lkotlin/collections/ArrayDeque;", "Landroidx/camera/camera2/impl/UseCaseCameraState$RequestSignal;", "requestNo", _UrlKt.FRAGMENT_ENCODE_SET, "throwable", _UrlKt.FRAGMENT_ENCODE_SET, "camera-camera2"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    @SourceDebugExtension({"SMAP\nUseCaseCameraState.kt\nKotlin\n*S Kotlin\n*F\n+ 1 UseCaseCameraState.kt\nandroidx/camera/camera2/impl/UseCaseCameraState$RequestListener\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,399:1\n1#2:400\n*E\n"})
    public final class RequestListener implements Request.Listener {
        public RequestListener() {
        }

        @Override // androidx.camera.camera2.pipe.Request.Listener
        /* JADX INFO: renamed from: onTotalCaptureResult-CcXjc1I */
        public void mo1284onTotalCaptureResultCcXjc1I(RequestMetadata requestMetadata, long frameNumber, FrameInfo totalCaptureResult) {
            Integer num;
            if (UseCaseCameraState.this.pendingSignalCount.getValue() == 0 || (num = (Integer) requestMetadata.get(TagsKt.getUSE_CASE_CAMERA_STATE_CUSTOM_TAG())) == null) {
                return;
            }
            UseCaseCameraState useCaseCameraState = UseCaseCameraState.this;
            int iIntValue = num.intValue();
            synchronized (useCaseCameraState.lock) {
                complete(useCaseCameraState.updateSignals, iIntValue);
                Unit unit = Unit.INSTANCE;
            }
        }

        @Override // androidx.camera.camera2.pipe.Request.Listener
        /* JADX INFO: renamed from: onFailed-CcXjc1I */
        public void mo1283onFailedCcXjc1I(RequestMetadata requestMetadata, long frameNumber, RequestFailure requestFailure) {
            if (UseCaseCameraState.this.pendingSignalCount.getValue() == 0) {
                return;
            }
            completeExceptionally(requestMetadata, requestFailure);
        }

        private final void completeExceptionally(RequestMetadata requestMetadata, RequestFailure requestFailure) {
            String str;
            Integer num = (Integer) requestMetadata.get(TagsKt.getUSE_CASE_CAMERA_STATE_CUSTOM_TAG());
            if (num != null) {
                UseCaseCameraState useCaseCameraState = UseCaseCameraState.this;
                int iIntValue = num.intValue();
                synchronized (useCaseCameraState.lock) {
                    try {
                        ArrayDeque<RequestSignal> arrayDeque = useCaseCameraState.updateSignals;
                        StringBuilder sb = new StringBuilder("Failed in framework level");
                        if (requestFailure != null) {
                            str = " with CaptureFailure.reason = " + requestFailure.getReason();
                        } else {
                            str = _UrlKt.FRAGMENT_ENCODE_SET;
                        }
                        sb.append(str);
                        completeExceptionally(arrayDeque, iIntValue, new Throwable(sb.toString()));
                        Unit unit = Unit.INSTANCE;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        private final void complete(ArrayDeque<RequestSignal> arrayDeque, int i) {
            while (!arrayDeque.isEmpty() && arrayDeque.first().getRequestNo() <= i) {
                arrayDeque.first().getSignal().complete(Unit.INSTANCE);
                CollectionsKt.removeFirst(arrayDeque);
                UseCaseCameraState.this.pendingSignalCount.decrementAndGet();
            }
        }

        private final void completeExceptionally(ArrayDeque<RequestSignal> arrayDeque, int i, Throwable th) {
            while (!arrayDeque.isEmpty() && arrayDeque.first().getRequestNo() <= i) {
                arrayDeque.first().getSignal().completeExceptionally(th);
                CollectionsKt.removeFirst(arrayDeque);
                UseCaseCameraState.this.pendingSignalCount.decrementAndGet();
            }
        }
    }
}
