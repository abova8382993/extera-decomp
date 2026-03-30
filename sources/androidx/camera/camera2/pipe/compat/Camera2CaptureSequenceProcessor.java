package androidx.camera.camera2.pipe.compat;

import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Trace;
import android.view.Surface;
import androidx.camera.camera2.config.UseCaseGraphContext$$ExternalSyntheticAutoCloseableDispatcher0;
import androidx.camera.camera2.pipe.CameraStream;
import androidx.camera.camera2.pipe.CaptureSequenceProcessor;
import androidx.camera.camera2.pipe.InputStream;
import androidx.camera.camera2.pipe.OutputId;
import androidx.camera.camera2.pipe.OutputStream;
import androidx.camera.camera2.pipe.Request;
import androidx.camera.camera2.pipe.RequestTemplate;
import androidx.camera.camera2.pipe.StreamFormat;
import androidx.camera.camera2.pipe.StreamGraph;
import androidx.camera.camera2.pipe.StreamId;
import androidx.camera.camera2.pipe.StrictMode;
import androidx.camera.camera2.pipe.core.Debug;
import androidx.camera.camera2.pipe.core.Log;
import androidx.camera.camera2.pipe.core.Threads;
import androidx.camera.camera2.pipe.media.AndroidImageWriter;
import androidx.camera.camera2.pipe.media.ImageWriterWrapper;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* JADX INFO: loaded from: classes3.dex */
public final class Camera2CaptureSequenceProcessor implements CaptureSequenceProcessor {
    public static final Companion Companion = new Companion(null);
    private final boolean awaitRepeatingRequestOnDisconnect;
    private final int debugId;
    private boolean disconnected;
    private final ImageWriterWrapper imageWriter;
    private Camera2CaptureSequence lastSingleRepeatingRequestSequence;
    private final Object lock;
    private final Map outputToSurfaceMap;
    private final CameraCaptureSessionWrapper session;
    private final StreamGraph streamGraph;
    private final Map streamToSurfaceMap;
    private final StrictMode strictMode;
    private final int template;
    private final Threads threads;

    public /* synthetic */ Camera2CaptureSequenceProcessor(CameraCaptureSessionWrapper cameraCaptureSessionWrapper, Threads threads, int i, Map map, Map map2, StreamGraph streamGraph, StrictMode strictMode, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(cameraCaptureSessionWrapper, threads, i, map, map2, streamGraph, strictMode, z);
    }

    private Camera2CaptureSequenceProcessor(CameraCaptureSessionWrapper session, Threads threads, int i, Map streamToSurfaceMap, Map outputToSurfaceMap, StreamGraph streamGraph, StrictMode strictMode, boolean z) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(threads, "threads");
        Intrinsics.checkNotNullParameter(streamToSurfaceMap, "streamToSurfaceMap");
        Intrinsics.checkNotNullParameter(outputToSurfaceMap, "outputToSurfaceMap");
        Intrinsics.checkNotNullParameter(streamGraph, "streamGraph");
        Intrinsics.checkNotNullParameter(strictMode, "strictMode");
        this.session = session;
        this.threads = threads;
        this.template = i;
        this.streamToSurfaceMap = streamToSurfaceMap;
        this.outputToSurfaceMap = outputToSurfaceMap;
        this.streamGraph = streamGraph;
        this.strictMode = strictMode;
        this.awaitRepeatingRequestOnDisconnect = z;
        this.debugId = Camera2CaptureSequenceProcessorKt.getCaptureSequenceProcessorDebugIds().incrementAndGet();
        this.lock = new Object();
        ImageWriterWrapper imageWriterWrapperM1962createU86x6Zg = null;
        if (!streamGraph.getInputs().isEmpty()) {
            InputStream inputStream = (InputStream) CollectionsKt.first(streamGraph.getInputs());
            Surface inputSurface = session.getInputSurface();
            if (inputSurface == null) {
                throw new IllegalStateException("inputSurface is required to create instance of imageWriter.");
            }
            try {
                imageWriterWrapperM1962createU86x6Zg = AndroidImageWriter.Companion.m1962createU86x6Zg(inputSurface, inputStream.mo1651getIdm1bwn9M(), inputStream.getMaxImages(), StreamFormat.m1772boximpl(inputStream.mo1650getFormat8FPWQzE()), threads.getCamera2Handler());
            } catch (RuntimeException e) {
                if (Log.INSTANCE.getERROR_LOGGABLE()) {
                    android.util.Log.e("CXCP", "Failed to create ImageWriter for session " + this.session + "! Reprocessing will not be supported!", e);
                }
            }
            if (imageWriterWrapperM1962createU86x6Zg != null && Log.INSTANCE.getDEBUG_LOGGABLE()) {
                android.util.Log.d("CXCP", "Created ImageWriter " + imageWriterWrapperM1962createU86x6Zg + " for session " + this.session);
            }
        }
        this.imageWriter = imageWriterWrapperM1962createU86x6Zg;
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x0279  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x028e A[LOOP:3: B:81:0x0206->B:109:0x028e, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:111:0x029e  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x02db  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x029c A[EDGE_INSN: B:141:0x029c->B:110:0x029c BREAK  A[LOOP:3: B:81:0x0206->B:109:0x028e], SYNTHETIC] */
    @Override // androidx.camera.camera2.pipe.CaptureSequenceProcessor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public androidx.camera.camera2.pipe.compat.Camera2CaptureSequence build(boolean r27, java.util.List r28, java.util.Map r29, java.util.Map r30, java.util.Map r31, androidx.camera.camera2.pipe.CaptureSequence.CaptureSequenceListener r32, java.util.List r33) {
        /*
            Method dump skipped, instruction units count: 903
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.compat.Camera2CaptureSequenceProcessor.build(boolean, java.util.List, java.util.Map, java.util.Map, java.util.Map, androidx.camera.camera2.pipe.CaptureSequence$CaptureSequenceListener, java.util.List):androidx.camera.camera2.pipe.compat.Camera2CaptureSequence");
    }

    @Override // androidx.camera.camera2.pipe.CaptureSequenceProcessor
    public Integer submit(Camera2CaptureSequence captureSequence) {
        Integer numCaptureBurst;
        Intrinsics.checkNotNullParameter(captureSequence, "captureSequence");
        synchronized (this.lock) {
            if (this.disconnected) {
                if (Log.INSTANCE.getWARN_LOGGABLE()) {
                    android.util.Log.w("CXCP", this + " disconnected. " + captureSequence + " won't be submitted");
                }
                return null;
            }
            if (captureSequence.getCaptureRequestList().size() == 1 && !(this.session instanceof CameraConstrainedHighSpeedCaptureSessionWrapper)) {
                if (captureSequence.getRepeating()) {
                    if (this.awaitRepeatingRequestOnDisconnect) {
                        this.lastSingleRepeatingRequestSequence = captureSequence;
                    }
                    numCaptureBurst = this.session.setRepeatingRequest((CaptureRequest) captureSequence.getCaptureRequestList().get(0), captureSequence);
                } else {
                    numCaptureBurst = this.session.capture((CaptureRequest) captureSequence.getCaptureRequestList().get(0), captureSequence);
                }
            } else if (captureSequence.getRepeating()) {
                numCaptureBurst = this.session.setRepeatingBurst(captureSequence.getCaptureRequestList(), captureSequence);
            } else {
                numCaptureBurst = this.session.captureBurst(captureSequence.getCaptureRequestList(), captureSequence);
            }
            return numCaptureBurst;
        }
    }

    @Override // androidx.camera.camera2.pipe.CaptureSequenceProcessor
    public void abortCaptures() {
        synchronized (this.lock) {
            try {
                if (Log.INSTANCE.getDEBUG_LOGGABLE()) {
                    android.util.Log.d("CXCP", this + "#abortCaptures");
                }
                this.session.abortCaptures();
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // androidx.camera.camera2.pipe.CaptureSequenceProcessor
    public void stopRepeating() {
        synchronized (this.lock) {
            try {
                if (Log.INSTANCE.getDEBUG_LOGGABLE()) {
                    android.util.Log.d("CXCP", this + "#stopRepeating");
                }
                this.session.stopRepeating();
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // androidx.camera.camera2.pipe.CaptureSequenceProcessor
    public Object shutdown(Continuation continuation) {
        disconnect$camera_camera2_pipe();
        return Unit.INSTANCE;
    }

    public final void disconnect$camera_camera2_pipe() {
        Camera2CaptureSequence camera2CaptureSequence;
        Debug debug = Debug.INSTANCE;
        try {
            Trace.beginSection(this + "#disconnect");
            synchronized (this.lock) {
                try {
                    if (this.disconnected) {
                        camera2CaptureSequence = null;
                    } else {
                        this.disconnected = true;
                        ImageWriterWrapper imageWriterWrapper = this.imageWriter;
                        if (imageWriterWrapper != null) {
                            UseCaseGraphContext$$ExternalSyntheticAutoCloseableDispatcher0.m22m(imageWriterWrapper);
                        }
                        Surface inputSurface = this.session.getInputSurface();
                        if (inputSurface != null) {
                            inputSurface.release();
                        }
                        camera2CaptureSequence = this.lastSingleRepeatingRequestSequence;
                    }
                } finally {
                }
            }
            if (this.awaitRepeatingRequestOnDisconnect && camera2CaptureSequence != null) {
                awaitRepeatingRequestStarted(camera2CaptureSequence);
            }
            Unit unit = Unit.INSTANCE;
            Trace.endSection();
        } catch (Throwable th) {
            Trace.endSection();
            throw th;
        }
    }

    public String toString() {
        return "Camera2CaptureSequenceProcessor-" + this.debugId;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void awaitRepeatingRequestStarted(Camera2CaptureSequence camera2CaptureSequence) {
        Log log = Log.INSTANCE;
        if (log.getDEBUG_LOGGABLE()) {
            android.util.Log.d("CXCP", "Waiting for the last repeating request sequence: " + camera2CaptureSequence);
        }
        if (((Unit) this.threads.runBlockingCheckedOrNull(2000L, new C02042(camera2CaptureSequence, null))) == null && log.getERROR_LOGGABLE()) {
            android.util.Log.e("CXCP", this + "#close: awaitStarted on last repeating request timed out, lastSingleRepeatingRequestSequence = " + camera2CaptureSequence);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.pipe.compat.Camera2CaptureSequenceProcessor$awaitRepeatingRequestStarted$2 */
    static final class C02042 extends SuspendLambda implements Function1 {
        final /* synthetic */ Camera2CaptureSequence $captureSequence;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02042(Camera2CaptureSequence camera2CaptureSequence, Continuation continuation) {
            super(1, continuation);
            this.$captureSequence = camera2CaptureSequence;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Continuation continuation) {
            return new C02042(this.$captureSequence, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Continuation continuation) {
            return ((C02042) create(continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Camera2CaptureSequence camera2CaptureSequence = this.$captureSequence;
                this.label = 1;
                if (camera2CaptureSequence.awaitStarted$camera_camera2_pipe(this) == coroutine_suspended) {
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
    }

    /* JADX WARN: Removed duplicated region for block: B:116:0x0175 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:120:? A[LOOP:2: B:57:0x0106->B:120:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:127:0x00b0 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:130:? A[LOOP:4: B:15:0x003b->B:130:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x016f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final boolean validateRequestList(java.util.List r14, androidx.camera.camera2.pipe.compat.CameraCaptureSessionWrapper r15) {
        /*
            Method dump skipped, instruction units count: 527
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.compat.Camera2CaptureSequenceProcessor.validateRequestList(java.util.List, androidx.camera.camera2.pipe.compat.CameraCaptureSessionWrapper):boolean");
    }

    private final boolean buildSurfaceMaps(List list, Map map, Map map2, Map map3) {
        if (list.isEmpty()) {
            throw new IllegalStateException("build(...) should never be called with an empty request list!");
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Request request = (Request) it.next();
            Iterator it2 = request.getStreams().iterator();
            boolean z = false;
            while (it2.hasNext()) {
                int iM1792unboximpl = ((StreamId) it2.next()).m1792unboximpl();
                if (!map3.containsKey(StreamId.m1786boximpl(iM1792unboximpl))) {
                    Surface surface = (Surface) this.streamToSurfaceMap.get(StreamId.m1786boximpl(iM1792unboximpl));
                    if (surface != null) {
                        map.put(surface, StreamId.m1786boximpl(iM1792unboximpl));
                        map3.put(StreamId.m1786boximpl(iM1792unboximpl), surface);
                        CameraStream cameraStreamMo1782getaKI5c8E = this.streamGraph.mo1782getaKI5c8E(iM1792unboximpl);
                        if (cameraStreamMo1782getaKI5c8E == null) {
                            throw new IllegalStateException("Required value was null.");
                        }
                        for (OutputStream outputStream : cameraStreamMo1782getaKI5c8E.getOutputs()) {
                            Object obj = this.outputToSurfaceMap.get(OutputId.m1665boximpl(outputStream.mo1686getId4LaLFng()));
                            if (obj == null) {
                                throw new IllegalStateException("Required value was null.");
                            }
                            map2.put((Surface) obj, OutputId.m1665boximpl(outputStream.mo1686getId4LaLFng()));
                        }
                    } else {
                        continue;
                    }
                }
                z = true;
            }
            if (!z) {
                if (Log.INSTANCE.getINFO_LOGGABLE()) {
                    android.util.Log.i("CXCP", "  Failed to bind any surfaces for " + request + '!');
                }
                return false;
            }
            if (!z) {
                throw new IllegalStateException("Check failed.");
            }
        }
        return true;
    }

    /* JADX INFO: renamed from: buildCaptureRequestBuilder-0UCm73U, reason: not valid java name */
    private final CaptureRequest.Builder m1827buildCaptureRequestBuilder0UCm73U(Request request, int i) {
        CaptureRequest.Builder builderMo1796createCaptureRequest2PPcXtw;
        if (request.getInputRequest() != null) {
            TotalCaptureResult totalCaptureResult = (TotalCaptureResult) request.getInputRequest().getFrameInfo().unwrapAs(Reflection.getOrCreateKotlinClass(TotalCaptureResult.class));
            if (totalCaptureResult == null) {
                throw new IllegalStateException(("Failed to unwrap FrameInfo " + request.getInputRequest().getFrameInfo() + " as TotalCaptureResult").toString());
            }
            builderMo1796createCaptureRequest2PPcXtw = this.session.getDevice().createReprocessCaptureRequest(totalCaptureResult);
        } else {
            builderMo1796createCaptureRequest2PPcXtw = this.session.getDevice().mo1796createCaptureRequest2PPcXtw(i);
        }
        if (builderMo1796createCaptureRequest2PPcXtw != null) {
            return builderMo1796createCaptureRequest2PPcXtw;
        }
        if (request.getInputRequest() != null) {
            if (!Log.INSTANCE.getINFO_LOGGABLE()) {
                return null;
            }
            android.util.Log.i("CXCP", "Failed to create a ReprocessingCaptureRequest.Builder from " + request.getInputRequest().getFrameInfo() + '!');
            return null;
        }
        if (!Log.INSTANCE.getINFO_LOGGABLE()) {
            return null;
        }
        android.util.Log.i("CXCP", "Failed to create a CaptureRequest.Builder from " + ((Object) RequestTemplate.m1759toStringimpl(i)) + '!');
        return null;
    }

    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
