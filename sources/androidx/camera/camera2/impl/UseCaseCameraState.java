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
import androidx.camera.camera2.pipe.FrameMetadata;
import androidx.camera.camera2.pipe.Request;
import androidx.camera.camera2.pipe.RequestFailure;
import androidx.camera.camera2.pipe.RequestMetadata;
import androidx.camera.camera2.pipe.RequestTemplate;
import androidx.camera.core.Logger;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicInt;
import kotlinx.coroutines.CompletableDeferred;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes3.dex */
public final class UseCaseCameraState {
    private final Map currentInternalParameters;
    private final Set currentListeners;
    private final Map currentParameters;
    private final Set currentStreams;
    private RequestTemplate currentTemplate;
    private AeMode lastAeMode;
    private AfMode lastAfMode;
    private AwbMode lastAwbMode;
    private final Object lock;
    private final AtomicInt pendingSignalCount;
    private final RequestListener requestListener;
    private final AtomicInt submittedRequestCounter;
    private final TemplateParamsOverride templateParamsOverride;
    private CompletableDeferred updateSignal;
    private ArrayDeque updateSignals;
    private boolean updating;
    private final UseCaseGraphContext useCaseGraphContext;

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.UseCaseCameraState$submitLatest$1 */
    static final class C01761 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C01761(Continuation continuation) {
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
        Intrinsics.checkNotNullParameter(useCaseGraphContext, "useCaseGraphContext");
        Intrinsics.checkNotNullParameter(templateParamsOverride, "templateParamsOverride");
        this.useCaseGraphContext = useCaseGraphContext;
        this.templateParamsOverride = templateParamsOverride;
        this.lock = new Object();
        this.submittedRequestCounter = AtomicFU.atomic(0);
        this.updateSignals = new ArrayDeque();
        this.currentParameters = new LinkedHashMap();
        this.currentInternalParameters = new LinkedHashMap();
        this.currentStreams = new LinkedHashSet();
        this.currentListeners = new LinkedHashSet();
        this.requestListener = new RequestListener();
        this.pendingSignalCount = AtomicFU.atomic(0);
    }

    public static final class RequestSignal {
        private final int requestNo;
        private final CompletableDeferred signal;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof RequestSignal)) {
                return false;
            }
            RequestSignal requestSignal = (RequestSignal) obj;
            return this.requestNo == requestSignal.requestNo && Intrinsics.areEqual(this.signal, requestSignal.signal);
        }

        public int hashCode() {
            return (this.requestNo * 31) + this.signal.hashCode();
        }

        public String toString() {
            return "RequestSignal(requestNo=" + this.requestNo + ", signal=" + this.signal + ')';
        }

        public RequestSignal(int i, CompletableDeferred signal) {
            Intrinsics.checkNotNullParameter(signal, "signal");
            this.requestNo = i;
            this.signal = signal;
        }

        public final int getRequestNo() {
            return this.requestNo;
        }

        public final CompletableDeferred getSignal() {
            return this.signal;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX INFO: renamed from: updateAsync-Tp9XwKQ, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object m1479updateAsyncTp9XwKQ(java.util.Map r8, boolean r9, java.util.Map r10, boolean r11, java.util.Set r12, androidx.camera.camera2.pipe.RequestTemplate r13, java.util.Set r14, kotlin.coroutines.Continuation r15) {
        /*
            Method dump skipped, instruction units count: 233
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.UseCaseCameraState.m1479updateAsyncTp9XwKQ(java.util.Map, boolean, java.util.Map, boolean, java.util.Set, androidx.camera.camera2.pipe.RequestTemplate, java.util.Set, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final Object tryStartRepeating(Continuation continuation) {
        Object objSubmitLatest = submitLatest(continuation);
        return objSubmitLatest == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objSubmitLatest : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:65:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0169 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object submitLatest(kotlin.coroutines.Continuation r19) {
        /*
            Method dump skipped, instruction units count: 399
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.UseCaseCameraState.submitLatest(kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void close() {
        synchronized (this.lock) {
            try {
                if (this.updating) {
                    this.updating = false;
                    CompletableDeferred completableDeferred = this.updateSignal;
                    if (completableDeferred != null) {
                        completableDeferred.completeExceptionally(new CancellationException("UseCaseCameraState closed"));
                    }
                    this.updateSignal = null;
                }
                while (!this.updateSignals.isEmpty()) {
                    ((RequestSignal) this.updateSignals.removeFirst()).getSignal().completeExceptionally(new CancellationException("UseCaseCameraState closed"));
                    this.pendingSignalCount.decrementAndGet();
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private final void update3A(CameraGraph.Session session, Map map) {
        CaptureRequest.Key CONTROL_AE_MODE = CaptureRequest.CONTROL_AE_MODE;
        Intrinsics.checkNotNullExpressionValue(CONTROL_AE_MODE, "CONTROL_AE_MODE");
        Integer intOrNull = getIntOrNull(map, CONTROL_AE_MODE);
        AeMode aeModeM1492fromIntOrNullkQd0u18 = intOrNull != null ? AeMode.Companion.m1492fromIntOrNullkQd0u18(intOrNull.intValue()) : null;
        CaptureRequest.Key CONTROL_AF_MODE = CaptureRequest.CONTROL_AF_MODE;
        Intrinsics.checkNotNullExpressionValue(CONTROL_AF_MODE, "CONTROL_AF_MODE");
        Integer intOrNull2 = getIntOrNull(map, CONTROL_AF_MODE);
        AfMode afModeM1503fromIntOrNullMKXwA8g = intOrNull2 != null ? AfMode.Companion.m1503fromIntOrNullMKXwA8g(intOrNull2.intValue()) : null;
        CaptureRequest.Key CONTROL_AWB_MODE = CaptureRequest.CONTROL_AWB_MODE;
        Intrinsics.checkNotNullExpressionValue(CONTROL_AWB_MODE, "CONTROL_AWB_MODE");
        Integer intOrNull3 = getIntOrNull(map, CONTROL_AWB_MODE);
        AwbMode awbModeM1522fromIntOrNullSaEiwI = intOrNull3 != null ? AwbMode.Companion.m1522fromIntOrNullSaEiwI(intOrNull3.intValue()) : null;
        boolean z = false;
        boolean z2 = (aeModeM1492fromIntOrNullkQd0u18 == null || Intrinsics.areEqual(aeModeM1492fromIntOrNullkQd0u18, this.lastAeMode)) ? false : true;
        boolean z3 = (afModeM1503fromIntOrNullMKXwA8g == null || Intrinsics.areEqual(afModeM1503fromIntOrNullMKXwA8g, this.lastAfMode)) ? false : true;
        if (awbModeM1522fromIntOrNullSaEiwI != null && !Intrinsics.areEqual(awbModeM1522fromIntOrNullSaEiwI, this.lastAwbMode)) {
            z = true;
        }
        if (z2 || z3 || z) {
            Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
            if (Logger.isDebugEnabled("CXCP")) {
                Log.d(Camera2Logger.TRUNCATED_TAG, "UseCaseCameraState: Updating 3A modes: AE(" + aeModeM1492fromIntOrNullkQd0u18 + ", changed=" + z2 + "), AF(" + afModeM1503fromIntOrNullMKXwA8g + ", changed=" + z3 + "), AWB(" + awbModeM1522fromIntOrNullSaEiwI + ", changed=" + z + ')');
            }
            CameraControls3A.CC.m1541update3AydBZfZg$default(session, aeModeM1492fromIntOrNullkQd0u18, afModeM1503fromIntOrNullMKXwA8g, awbModeM1522fromIntOrNullSaEiwI, null, null, null, 56, null);
            if (aeModeM1492fromIntOrNullkQd0u18 != null) {
                this.lastAeMode = aeModeM1492fromIntOrNullkQd0u18;
            }
            if (afModeM1503fromIntOrNullMKXwA8g != null) {
                this.lastAfMode = afModeM1503fromIntOrNullMKXwA8g;
            }
            if (awbModeM1522fromIntOrNullSaEiwI != null) {
                this.lastAwbMode = awbModeM1522fromIntOrNullSaEiwI;
            }
        }
    }

    private final Integer getIntOrNull(Map map, CaptureRequest.Key key) {
        Object obj = map != null ? map.get(key) : null;
        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        return null;
    }

    public final class RequestListener implements Request.Listener {
        @Override // androidx.camera.camera2.pipe.Request.Listener
        public /* synthetic */ void onAborted(Request request) {
            Intrinsics.checkNotNullParameter(request, "request");
        }

        @Override // androidx.camera.camera2.pipe.Request.Listener
        /* JADX INFO: renamed from: onBufferLost-DlC0U5Y */
        public /* synthetic */ void mo1387onBufferLostDlC0U5Y(RequestMetadata requestMetadata, long j, int i) {
            Intrinsics.checkNotNullParameter(requestMetadata, "requestMetadata");
        }

        @Override // androidx.camera.camera2.pipe.Request.Listener
        /* JADX INFO: renamed from: onBufferLost-iiEMlm4 */
        public /* synthetic */ void mo1388onBufferLostiiEMlm4(RequestMetadata requestMetadata, long j, int i, int i2) {
            Intrinsics.checkNotNullParameter(requestMetadata, "requestMetadata");
        }

        @Override // androidx.camera.camera2.pipe.Request.Listener
        public /* synthetic */ void onCaptureProgress(RequestMetadata requestMetadata, int i) {
            Intrinsics.checkNotNullParameter(requestMetadata, "requestMetadata");
        }

        @Override // androidx.camera.camera2.pipe.Request.Listener
        /* JADX INFO: renamed from: onComplete-CcXjc1I */
        public /* synthetic */ void mo1389onCompleteCcXjc1I(RequestMetadata requestMetadata, long j, FrameInfo frameInfo) {
            Request.Listener.CC.m1740$default$onCompleteCcXjc1I(this, requestMetadata, j, frameInfo);
        }

        @Override // androidx.camera.camera2.pipe.Request.Listener
        /* JADX INFO: renamed from: onPartialCaptureResult-CcXjc1I */
        public /* synthetic */ void mo1391onPartialCaptureResultCcXjc1I(RequestMetadata requestMetadata, long j, FrameMetadata frameMetadata) {
            Request.Listener.CC.m1742$default$onPartialCaptureResultCcXjc1I(this, requestMetadata, j, frameMetadata);
        }

        @Override // androidx.camera.camera2.pipe.Request.Listener
        /* JADX INFO: renamed from: onReadoutStarted-mP9r-9w */
        public /* synthetic */ void mo1392onReadoutStartedmP9r9w(RequestMetadata requestMetadata, long j, long j2) {
            Intrinsics.checkNotNullParameter(requestMetadata, "requestMetadata");
        }

        @Override // androidx.camera.camera2.pipe.Request.Listener
        public /* synthetic */ void onRequestSequenceAborted(RequestMetadata requestMetadata) {
            Intrinsics.checkNotNullParameter(requestMetadata, "requestMetadata");
        }

        @Override // androidx.camera.camera2.pipe.Request.Listener
        /* JADX INFO: renamed from: onRequestSequenceCompleted-RuT0dZU */
        public /* synthetic */ void mo1393onRequestSequenceCompletedRuT0dZU(RequestMetadata requestMetadata, long j) {
            Intrinsics.checkNotNullParameter(requestMetadata, "requestMetadata");
        }

        @Override // androidx.camera.camera2.pipe.Request.Listener
        public /* synthetic */ void onRequestSequenceCreated(RequestMetadata requestMetadata) {
            Intrinsics.checkNotNullParameter(requestMetadata, "requestMetadata");
        }

        @Override // androidx.camera.camera2.pipe.Request.Listener
        public /* synthetic */ void onRequestSequenceSubmitted(RequestMetadata requestMetadata) {
            Intrinsics.checkNotNullParameter(requestMetadata, "requestMetadata");
        }

        @Override // androidx.camera.camera2.pipe.Request.Listener
        /* JADX INFO: renamed from: onStarted-uGKBvU4 */
        public /* synthetic */ void mo1394onStarteduGKBvU4(RequestMetadata requestMetadata, long j, long j2) {
            Intrinsics.checkNotNullParameter(requestMetadata, "requestMetadata");
        }

        public RequestListener() {
        }

        @Override // androidx.camera.camera2.pipe.Request.Listener
        /* JADX INFO: renamed from: onTotalCaptureResult-CcXjc1I */
        public void mo1395onTotalCaptureResultCcXjc1I(RequestMetadata requestMetadata, long j, FrameInfo totalCaptureResult) {
            Integer num;
            Intrinsics.checkNotNullParameter(requestMetadata, "requestMetadata");
            Intrinsics.checkNotNullParameter(totalCaptureResult, "totalCaptureResult");
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
        public void mo1390onFailedCcXjc1I(RequestMetadata requestMetadata, long j, RequestFailure requestFailure) {
            Intrinsics.checkNotNullParameter(requestMetadata, "requestMetadata");
            Intrinsics.checkNotNullParameter(requestFailure, "requestFailure");
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
                        ArrayDeque arrayDeque = useCaseCameraState.updateSignals;
                        StringBuilder sb = new StringBuilder();
                        sb.append("Failed in framework level");
                        if (requestFailure != null) {
                            str = " with CaptureFailure.reason = " + requestFailure.getReason();
                            if (str == null) {
                                str = _UrlKt.FRAGMENT_ENCODE_SET;
                            }
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

        private final void complete(ArrayDeque arrayDeque, int i) {
            while (!arrayDeque.isEmpty() && ((RequestSignal) arrayDeque.first()).getRequestNo() <= i) {
                ((RequestSignal) arrayDeque.first()).getSignal().complete(Unit.INSTANCE);
                CollectionsKt.removeFirst(arrayDeque);
                UseCaseCameraState.this.pendingSignalCount.decrementAndGet();
            }
        }

        private final void completeExceptionally(ArrayDeque arrayDeque, int i, Throwable th) {
            while (!arrayDeque.isEmpty() && ((RequestSignal) arrayDeque.first()).getRequestNo() <= i) {
                ((RequestSignal) arrayDeque.first()).getSignal().completeExceptionally(th);
                CollectionsKt.removeFirst(arrayDeque);
                UseCaseCameraState.this.pendingSignalCount.decrementAndGet();
            }
        }
    }
}
