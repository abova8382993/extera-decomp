package androidx.camera.camera2.pipe.graph;

import androidx.camera.camera2.pipe.CameraGraphId;
import androidx.camera.camera2.pipe.Request;
import androidx.camera.camera2.pipe.RequestsKt;
import androidx.camera.camera2.pipe.core.Log;
import androidx.camera.camera2.pipe.core.ProcessingQueue;
import androidx.camera.camera2.pipe.graph.GraphCommand;
import java.io.Closeable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.atomicfu.AtomicBoolean;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineName;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;

/* JADX INFO: loaded from: classes3.dex */
public final class GraphLoop implements Closeable {
    public static final Companion Companion = new Companion(null);
    private final AtomicBoolean _captureProcessingEnabled;
    private Map _graph3AParameters;
    private Map _graphParameters;
    private Request _repeatingRequest;
    private List _requestListeners;
    private GraphRequestProcessor _requestProcessor;
    private final CameraGraphId cameraGraphId;
    private volatile boolean closed;
    private Map currentGraph3AParameters;
    private Map currentGraphParameters;
    private Request currentRepeatingRequest;
    private List currentRequestListeners;
    private GraphRequestProcessor currentRequestProcessor;
    private Map currentRequiredParameters;
    private final Map defaultParameters;
    private final CoroutineScope graphLoopScope;
    private final List listeners;
    private final Object lock;
    private final ProcessingQueue processingQueue;
    private final List requiredListeners;
    private final Map requiredParameters;
    private final CoroutineScope shutdownScope;

    public interface Listener {
        void onGraphShutdown();

        void onGraphStopped();

        void onStopRepeating();
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.pipe.graph.GraphLoop$processRequestProcessor$1 */
    static final class C02421 extends ContinuationImpl {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        /* synthetic */ Object result;

        C02421(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return GraphLoop.this.processRequestProcessor(null, 0, null, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.pipe.graph.GraphLoop$processShutdown$1 */
    static final class C02431 extends ContinuationImpl {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        C02431(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return GraphLoop.this.processShutdown(null, this);
        }
    }

    public GraphLoop(CameraGraphId cameraGraphId, Map defaultParameters, Map requiredParameters, List requiredListeners, List listeners, CoroutineScope shutdownScope, CoroutineDispatcher dispatcher) {
        Intrinsics.checkNotNullParameter(cameraGraphId, "cameraGraphId");
        Intrinsics.checkNotNullParameter(defaultParameters, "defaultParameters");
        Intrinsics.checkNotNullParameter(requiredParameters, "requiredParameters");
        Intrinsics.checkNotNullParameter(requiredListeners, "requiredListeners");
        Intrinsics.checkNotNullParameter(listeners, "listeners");
        Intrinsics.checkNotNullParameter(shutdownScope, "shutdownScope");
        Intrinsics.checkNotNullParameter(dispatcher, "dispatcher");
        this.cameraGraphId = cameraGraphId;
        this.defaultParameters = defaultParameters;
        this.requiredParameters = requiredParameters;
        this.requiredListeners = requiredListeners;
        this.listeners = listeners;
        this.shutdownScope = shutdownScope;
        CoroutineScope CoroutineScope = CoroutineScopeKt.CoroutineScope(dispatcher.plus(new CoroutineName("CXCP-GraphLoop")));
        this.graphLoopScope = CoroutineScope;
        this.processingQueue = ProcessingQueue.Companion.processIn(new ProcessingQueue(0, new GraphLoop$processingQueue$1(this), new GraphLoop$processingQueue$2(this), 1, null), CoroutineScope);
        this.lock = new Object();
        this._graphParameters = MapsKt.emptyMap();
        this._graph3AParameters = MapsKt.emptyMap();
        this._requestListeners = CollectionsKt.emptyList();
        this._captureProcessingEnabled = AtomicFU.atomic(true);
        this.currentGraphParameters = MapsKt.emptyMap();
        this.currentGraph3AParameters = MapsKt.emptyMap();
        this.currentRequiredParameters = requiredParameters;
        this.currentRequestListeners = requiredListeners;
    }

    public final void setRequestProcessor(GraphRequestProcessor graphRequestProcessor) {
        synchronized (this.lock) {
            GraphRequestProcessor graphRequestProcessor2 = this._requestProcessor;
            this._requestProcessor = graphRequestProcessor;
            if (this.closed) {
                this._requestProcessor = null;
                if (graphRequestProcessor != null) {
                    BuildersKt__Builders_commonKt.launch$default(this.shutdownScope, null, null, new GraphLoop$requestProcessor$2$1(graphRequestProcessor, null), 3, null);
                }
                return;
            }
            if (graphRequestProcessor2 != graphRequestProcessor) {
                this.processingQueue.tryEmit(new GraphCommand.RequestProcessor(graphRequestProcessor2, graphRequestProcessor));
            }
            Unit unit = Unit.INSTANCE;
            if (graphRequestProcessor == null) {
                int size = this.listeners.size();
                for (int i = 0; i < size; i++) {
                    ((Listener) this.listeners.get(i)).onGraphStopped();
                }
            }
        }
    }

    public final Request getRepeatingRequest() {
        Request request;
        synchronized (this.lock) {
            request = this._repeatingRequest;
        }
        return request;
    }

    public final void setRepeatingRequest(Request request) {
        synchronized (this.lock) {
            try {
                Request request2 = this._repeatingRequest;
                this._repeatingRequest = request;
                if (request2 != null || request != null) {
                    if (request != null) {
                        this.processingQueue.tryEmit(new GraphCommand.Repeat(request));
                    } else {
                        this.processingQueue.tryEmit(GraphCommand.Stop.INSTANCE);
                    }
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
        if (request == null) {
            int size = this.listeners.size();
            for (int i = 0; i < size; i++) {
                ((Listener) this.listeners.get(i)).onStopRepeating();
            }
        }
    }

    public final void setGraphParameters(Map value) {
        Intrinsics.checkNotNullParameter(value, "value");
        synchronized (this.lock) {
            this._graphParameters = value;
            this.processingQueue.tryEmit(new GraphCommand.Parameters(value, this._graph3AParameters));
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void setGraph3AParameters(Map value) {
        Intrinsics.checkNotNullParameter(value, "value");
        synchronized (this.lock) {
            this._graph3AParameters = value;
            this.processingQueue.tryEmit(new GraphCommand.Parameters(this._graphParameters, value));
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void setRequestListeners(List value) {
        Intrinsics.checkNotNullParameter(value, "value");
        synchronized (this.lock) {
            this._requestListeners = value;
            this.processingQueue.tryEmit(new GraphCommand.Listeners(value));
            Unit unit = Unit.INSTANCE;
        }
    }

    public final boolean getCaptureProcessingEnabled() {
        return this._captureProcessingEnabled.getValue();
    }

    public final void setCaptureProcessingEnabled(boolean z) {
        this._captureProcessingEnabled.setValue(z);
        if (z) {
            invalidate();
        }
    }

    public final boolean submit(List requests) {
        Intrinsics.checkNotNullParameter(requests, "requests");
        if (this.processingQueue.tryEmit(new GraphCommand.Capture(requests))) {
            return true;
        }
        abortRequests(requests);
        return false;
    }

    public final boolean trigger(Map parameters) {
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        if (getRepeatingRequest() == null) {
            throw new IllegalStateException("Cannot submit parameters without an active repeating request!");
        }
        return this.processingQueue.tryEmit(new GraphCommand.Trigger(parameters));
    }

    public final void invalidate() {
        this.processingQueue.tryEmit(GraphCommand.Invalidate.INSTANCE);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        synchronized (this.lock) {
            try {
                if (this.closed) {
                    return;
                }
                this.closed = true;
                GraphRequestProcessor graphRequestProcessor = this._requestProcessor;
                if (graphRequestProcessor != null) {
                    BuildersKt__Builders_commonKt.launch$default(this.shutdownScope, null, null, new GraphLoop$close$1$1$1(graphRequestProcessor, null), 3, null);
                }
                this._requestProcessor = null;
                this.processingQueue.tryEmit(GraphCommand.Shutdown.INSTANCE);
                int size = this.listeners.size();
                for (int i = 0; i < size; i++) {
                    ((Listener) this.listeners.get(i)).onGraphShutdown();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object process(List list, Continuation continuation) {
        int iSelectGraphCommand = selectGraphCommand(list);
        GraphCommand graphCommand = (GraphCommand) list.get(iSelectGraphCommand);
        if (Intrinsics.areEqual(graphCommand, GraphCommand.Invalidate.INSTANCE)) {
            list.remove(iSelectGraphCommand);
        } else {
            if (Intrinsics.areEqual(graphCommand, GraphCommand.Shutdown.INSTANCE)) {
                Object objProcessShutdown = processShutdown(list, continuation);
                return objProcessShutdown == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objProcessShutdown : Unit.INSTANCE;
            }
            if (Intrinsics.areEqual(graphCommand, GraphCommand.Abort.INSTANCE)) {
                processAbort(list, iSelectGraphCommand);
            } else if (Intrinsics.areEqual(graphCommand, GraphCommand.Stop.INSTANCE)) {
                processStop(list, iSelectGraphCommand);
            } else {
                if (graphCommand instanceof GraphCommand.RequestProcessor) {
                    Object objProcessRequestProcessor = processRequestProcessor(list, iSelectGraphCommand, (GraphCommand.RequestProcessor) graphCommand, continuation);
                    return objProcessRequestProcessor == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objProcessRequestProcessor : Unit.INSTANCE;
                }
                if (graphCommand instanceof GraphCommand.Capture) {
                    processCapture$default(this, list, iSelectGraphCommand, (GraphCommand.Capture) graphCommand, false, 8, null);
                } else if (graphCommand instanceof GraphCommand.Trigger) {
                    processTrigger(list, iSelectGraphCommand, (GraphCommand.Trigger) graphCommand);
                } else if (graphCommand instanceof GraphCommand.Parameters) {
                    processParameters(list, iSelectGraphCommand, (GraphCommand.Parameters) graphCommand);
                } else if (graphCommand instanceof GraphCommand.Listeners) {
                    processListeners(list, iSelectGraphCommand, (GraphCommand.Listeners) graphCommand);
                } else {
                    if (!(graphCommand instanceof GraphCommand.Repeat)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    processRepeat$default(this, list, iSelectGraphCommand, false, 4, null);
                }
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x004a, code lost:
    
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final int selectGraphCommand(java.util.List r11) {
        /*
            r10 = this;
            int r0 = r11.size()
            r1 = 1
            r2 = 0
            if (r0 != r1) goto L9
            return r2
        L9:
            r0 = r11
            java.util.Collection r0 = (java.util.Collection) r0
            int r1 = r0.size()
            r3 = -1
            int r1 = r1 + r3
            r4 = r3
            if (r1 < 0) goto L4b
        L15:
            int r5 = r1 + (-1)
            java.lang.Object r6 = r11.get(r1)
            androidx.camera.camera2.pipe.graph.GraphCommand r6 = (androidx.camera.camera2.pipe.graph.GraphCommand) r6
            androidx.camera.camera2.pipe.graph.GraphCommand$Abort r7 = androidx.camera.camera2.pipe.graph.GraphCommand.Abort.INSTANCE
            boolean r7 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r7)
            if (r7 != 0) goto L4a
            androidx.camera.camera2.pipe.graph.GraphCommand$Invalidate r7 = androidx.camera.camera2.pipe.graph.GraphCommand.Invalidate.INSTANCE
            boolean r7 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r7)
            if (r7 != 0) goto L4a
            androidx.camera.camera2.pipe.graph.GraphCommand$Stop r7 = androidx.camera.camera2.pipe.graph.GraphCommand.Stop.INSTANCE
            boolean r7 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r7)
            if (r7 != 0) goto L4a
            androidx.camera.camera2.pipe.graph.GraphCommand$Shutdown r7 = androidx.camera.camera2.pipe.graph.GraphCommand.Shutdown.INSTANCE
            boolean r7 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r7)
            if (r7 == 0) goto L3e
            goto L4a
        L3e:
            boolean r6 = r6 instanceof androidx.camera.camera2.pipe.graph.GraphCommand.RequestProcessor
            if (r6 == 0) goto L45
            if (r4 >= 0) goto L45
            r4 = r1
        L45:
            if (r5 >= 0) goto L48
            goto L4b
        L48:
            r1 = r5
            goto L15
        L4a:
            return r1
        L4b:
            if (r4 < 0) goto L4e
            return r4
        L4e:
            int r1 = r0.size()
            r4 = r2
            r5 = r3
            r6 = r5
        L55:
            if (r4 >= r1) goto L71
            java.lang.Object r7 = r11.get(r4)
            androidx.camera.camera2.pipe.graph.GraphCommand r7 = (androidx.camera.camera2.pipe.graph.GraphCommand) r7
            boolean r8 = r7 instanceof androidx.camera.camera2.pipe.graph.GraphCommand.Parameters
            if (r8 == 0) goto L63
            r5 = r4
            goto L6e
        L63:
            boolean r8 = r7 instanceof androidx.camera.camera2.pipe.graph.GraphCommand.Listeners
            if (r8 == 0) goto L69
            r6 = r4
            goto L6e
        L69:
            boolean r7 = r7 instanceof androidx.camera.camera2.pipe.graph.GraphCommand.Repeat
            if (r7 != 0) goto L6e
            goto L71
        L6e:
            int r4 = r4 + 1
            goto L55
        L71:
            if (r5 < 0) goto L74
            return r5
        L74:
            if (r6 < 0) goto L77
            return r6
        L77:
            androidx.camera.camera2.pipe.Request r1 = r10.currentRepeatingRequest
            if (r1 == 0) goto L9b
            boolean r1 = r10.getCaptureProcessingEnabled()
            if (r1 == 0) goto L9b
            int r1 = r0.size()
            r4 = r2
        L86:
            if (r4 >= r1) goto L9b
            java.lang.Object r5 = r11.get(r4)
            androidx.camera.camera2.pipe.graph.GraphCommand r5 = (androidx.camera.camera2.pipe.graph.GraphCommand) r5
            boolean r6 = r5 instanceof androidx.camera.camera2.pipe.graph.GraphCommand.Capture
            if (r6 != 0) goto L9a
            boolean r5 = r5 instanceof androidx.camera.camera2.pipe.graph.GraphCommand.Trigger
            if (r5 == 0) goto L97
            goto L9a
        L97:
            int r4 = r4 + 1
            goto L86
        L9a:
            return r4
        L9b:
            int r0 = r0.size()
            r1 = r3
            r3 = r2
        La1:
            if (r3 >= r0) goto Lb3
            java.lang.Object r4 = r11.get(r3)
            androidx.camera.camera2.pipe.graph.GraphCommand r4 = (androidx.camera.camera2.pipe.graph.GraphCommand) r4
            boolean r4 = r4 instanceof androidx.camera.camera2.pipe.graph.GraphCommand.Repeat
            if (r4 == 0) goto Lb3
            int r1 = r3 + 1
            r9 = r3
            r3 = r1
            r1 = r9
            goto La1
        Lb3:
            if (r1 < 0) goto Lb6
            return r1
        Lb6:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.graph.GraphLoop.selectGraphCommand(java.util.List):int");
    }

    static /* synthetic */ void processCapture$default(GraphLoop graphLoop, List list, int i, GraphCommand.Capture capture, boolean z, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            z = true;
        }
        graphLoop.processCapture(list, i, capture, z);
    }

    private final void processCapture(List list, int i, GraphCommand.Capture capture, boolean z) {
        if (getCaptureProcessingEnabled() && buildAndSubmit$default(this, false, capture.getRequests(), null, 4, null)) {
            list.remove(i);
            return;
        }
        if (!z || i <= 0) {
            return;
        }
        int i2 = i - 1;
        if (!(((GraphCommand) list.get(i2)) instanceof GraphCommand.Repeat)) {
            throw new IllegalStateException("Check failed.");
        }
        processRepeat(list, i2, false);
    }

    private final void processTrigger(List list, int i, GraphCommand.Trigger trigger) {
        Request request = this.currentRepeatingRequest;
        if (request == null && i == 0) {
            list.remove(i);
            return;
        }
        if (getCaptureProcessingEnabled() && request != null && buildAndSubmit(false, CollectionsKt.listOf(request), trigger.getTriggerParameters())) {
            list.remove(i);
        } else if (i > 0) {
            int i2 = i - 1;
            if (!(((GraphCommand) list.get(i2)) instanceof GraphCommand.Repeat)) {
                throw new IllegalStateException("Check failed.");
            }
            processRepeat(list, i2, false);
        }
    }

    static /* synthetic */ void processRepeat$default(GraphLoop graphLoop, List list, int i, boolean z, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z = true;
        }
        graphLoop.processRepeat(list, i, z);
    }

    private final void processRepeat(List list, int i, boolean z) {
        int i2;
        int i3 = i;
        while (true) {
            int i4 = 0;
            if (-1 < i3) {
                GraphCommand graphCommand = (GraphCommand) list.get(i3);
                if (graphCommand instanceof GraphCommand.Repeat) {
                    GraphCommand.Repeat repeat = (GraphCommand.Repeat) graphCommand;
                    if (buildAndSubmit$default(this, true, CollectionsKt.listOf(repeat.getRequest()), null, 4, null)) {
                        this.currentRepeatingRequest = repeat.getRequest();
                        list.remove(i3);
                        while (i4 < i3) {
                            if (((GraphCommand) list.get(i4)) instanceof GraphCommand.Repeat) {
                                list.remove(i4);
                                i3--;
                            } else {
                                i4++;
                            }
                        }
                        return;
                    }
                }
                i3--;
            } else {
                if (!z || (i2 = i + 1) >= list.size()) {
                    return;
                }
                GraphCommand graphCommand2 = (GraphCommand) list.get(i2);
                if (graphCommand2 instanceof GraphCommand.Capture) {
                    processCapture(list, i2, (GraphCommand.Capture) graphCommand2, false);
                    return;
                } else {
                    if (graphCommand2 instanceof GraphCommand.Trigger) {
                        processTrigger(list, i2, (GraphCommand.Trigger) graphCommand2);
                        return;
                    }
                    return;
                }
            }
        }
    }

    private final void processParameters(List list, int i, GraphCommand.Parameters parameters) {
        Map mapBuild;
        this.currentGraphParameters = parameters.getGraphParameters();
        this.currentGraph3AParameters = parameters.getGraph3AParameters();
        if (parameters.getGraph3AParameters().isEmpty()) {
            mapBuild = this.requiredParameters;
        } else {
            Map mapCreateMapBuilder = MapsKt.createMapBuilder();
            RequestsKt.putAllMetadata(mapCreateMapBuilder, parameters.getGraph3AParameters());
            RequestsKt.putAllMetadata(mapCreateMapBuilder, this.requiredParameters);
            mapBuild = MapsKt.build(mapCreateMapBuilder);
        }
        this.currentRequiredParameters = mapBuild;
        list.remove(i);
        int i2 = 0;
        while (i2 < i) {
            if (((GraphCommand) list.get(i2)) instanceof GraphCommand.Parameters) {
                list.remove(i2);
                i--;
            } else {
                i2++;
            }
        }
        reissueRepeatingRequest();
    }

    private final void processListeners(List list, int i, GraphCommand.Listeners listeners) {
        this.currentRequestListeners = CollectionsKt.distinct(CollectionsKt.plus((Collection) listeners.getListeners(), (Iterable) this.requiredListeners));
        list.remove(i);
        int i2 = 0;
        while (i2 < i) {
            if (((GraphCommand) list.get(i2)) instanceof GraphCommand.Listeners) {
                list.remove(i2);
                i--;
            } else {
                i2++;
            }
        }
        reissueRepeatingRequest();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0112  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x013b  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:31:0x00de -> B:37:0x00f9). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:35:0x00f7 -> B:36:0x00f8). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:38:0x0106 -> B:39:0x0107). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object processRequestProcessor(java.util.List r18, int r19, androidx.camera.camera2.pipe.graph.GraphCommand.RequestProcessor r20, kotlin.coroutines.Continuation r21) {
        /*
            Method dump skipped, instruction units count: 341
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.graph.GraphLoop.processRequestProcessor(java.util.List, int, androidx.camera.camera2.pipe.graph.GraphCommand$RequestProcessor, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final void processStop(List list, int i) {
        GraphRequestProcessor graphRequestProcessor = this.currentRequestProcessor;
        if (graphRequestProcessor != null) {
            graphRequestProcessor.stopRepeating$camera_camera2_pipe();
        }
        this.currentRepeatingRequest = null;
        list.remove(i);
        int i2 = 0;
        while (i2 < i) {
            GraphCommand graphCommand = (GraphCommand) list.get(i2);
            if (Intrinsics.areEqual(graphCommand, GraphCommand.Stop.INSTANCE) || (graphCommand instanceof GraphCommand.Repeat)) {
                list.remove(i2);
                i--;
            } else {
                i2++;
            }
        }
    }

    private final void processAbort(List list, int i) {
        GraphRequestProcessor graphRequestProcessor = this.currentRequestProcessor;
        if (graphRequestProcessor != null) {
            graphRequestProcessor.abortCaptures$camera_camera2_pipe();
        }
        this.currentRepeatingRequest = null;
        list.remove(i);
        int i2 = 0;
        while (i2 < i) {
            GraphCommand graphCommand = (GraphCommand) list.get(i2);
            if (!Intrinsics.areEqual(graphCommand, GraphCommand.Stop.INSTANCE) && !Intrinsics.areEqual(graphCommand, GraphCommand.Abort.INSTANCE) && !(graphCommand instanceof GraphCommand.Repeat) && !(graphCommand instanceof GraphCommand.Trigger)) {
                if (graphCommand instanceof GraphCommand.Capture) {
                    abortRequests(((GraphCommand.Capture) graphCommand).getRequests());
                } else {
                    i2++;
                }
            }
            list.remove(i2);
            i--;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0098, code lost:
    
        if (r12.shutdown$camera_camera2_pipe(r0) == r1) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00e9, code lost:
    
        if (r12.shutdown$camera_camera2_pipe(r0) == r1) goto L46;
     */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:32:0x00b0 -> B:48:0x00ee). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:43:0x00d9 -> B:47:0x00ec). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:45:0x00e9 -> B:47:0x00ec). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object processShutdown(java.util.List r11, kotlin.coroutines.Continuation r12) {
        /*
            Method dump skipped, instruction units count: 251
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.graph.GraphLoop.processShutdown(java.util.List, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final boolean reissueRepeatingRequest() {
        GraphRequestProcessor graphRequestProcessor = this.currentRequestProcessor;
        if (graphRequestProcessor == null) {
            return false;
        }
        Request request = this.currentRepeatingRequest;
        return Intrinsics.areEqual(request != null ? Boolean.valueOf(graphRequestProcessor.submit$camera_camera2_pipe(true, CollectionsKt.listOf(request), this.defaultParameters, this.currentGraphParameters, this.currentRequiredParameters, this.currentRequestListeners)) : null, Boolean.TRUE);
    }

    private final void abortRequests(List list) {
        List list2 = list;
        int size = list2.size();
        for (int i = 0; i < size; i++) {
            Request request = (Request) list.get(i);
            int size2 = this.currentRequestListeners.size();
            for (int i2 = 0; i2 < size2; i2++) {
                ((Request.Listener) this.currentRequestListeners.get(i2)).onAborted(request);
            }
        }
        int size3 = list2.size();
        for (int i3 = 0; i3 < size3; i3++) {
            Request request2 = (Request) list.get(i3);
            int size4 = request2.getListeners().size();
            for (int i4 = 0; i4 < size4; i4++) {
                ((Request.Listener) request2.getListeners().get(i4)).onAborted(request2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void finalizeUnprocessedCommands(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            GraphCommand graphCommand = (GraphCommand) it.next();
            if (graphCommand instanceof GraphCommand.Capture) {
                abortRequests(((GraphCommand.Capture) graphCommand).getRequests());
            } else if (graphCommand instanceof GraphCommand.RequestProcessor) {
                BuildersKt__Builders_commonKt.launch$default(this.shutdownScope, null, CoroutineStart.UNDISPATCHED, new C02411(graphCommand, null), 1, null);
            }
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.pipe.graph.GraphLoop$finalizeUnprocessedCommands$1 */
    static final class C02411 extends SuspendLambda implements Function2 {
        final /* synthetic */ GraphCommand $command;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02411(GraphCommand graphCommand, Continuation continuation) {
            super(2, continuation);
            this.$command = graphCommand;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C02411(this.$command, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C02411) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:18:0x0044, code lost:
        
            if (r5.shutdown$camera_camera2_pipe(r4) == r0) goto L19;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r5) {
            /*
                r4 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r4.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L1e
                if (r1 == r3) goto L1a
                if (r1 != r2) goto L12
                kotlin.ResultKt.throwOnFailure(r5)
                goto L47
            L12:
                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r5.<init>(r0)
                throw r5
            L1a:
                kotlin.ResultKt.throwOnFailure(r5)
                goto L34
            L1e:
                kotlin.ResultKt.throwOnFailure(r5)
                androidx.camera.camera2.pipe.graph.GraphCommand r5 = r4.$command
                androidx.camera.camera2.pipe.graph.GraphCommand$RequestProcessor r5 = (androidx.camera.camera2.pipe.graph.GraphCommand.RequestProcessor) r5
                androidx.camera.camera2.pipe.graph.GraphRequestProcessor r5 = r5.getOld()
                if (r5 == 0) goto L34
                r4.label = r3
                java.lang.Object r5 = r5.shutdown$camera_camera2_pipe(r4)
                if (r5 != r0) goto L34
                goto L46
            L34:
                androidx.camera.camera2.pipe.graph.GraphCommand r5 = r4.$command
                androidx.camera.camera2.pipe.graph.GraphCommand$RequestProcessor r5 = (androidx.camera.camera2.pipe.graph.GraphCommand.RequestProcessor) r5
                androidx.camera.camera2.pipe.graph.GraphRequestProcessor r5 = r5.getNew()
                if (r5 == 0) goto L47
                r4.label = r2
                java.lang.Object r5 = r5.shutdown$camera_camera2_pipe(r4)
                if (r5 != r0) goto L47
            L46:
                return r0
            L47:
                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.graph.GraphLoop.C02411.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    static /* synthetic */ boolean buildAndSubmit$default(GraphLoop graphLoop, boolean z, List list, Map map, int i, Object obj) {
        if ((i & 4) != 0) {
            map = MapsKt.emptyMap();
        }
        return graphLoop.buildAndSubmit(z, list, map);
    }

    private final boolean buildAndSubmit(boolean z, List list, Map map) throws Exception {
        Map mapBuild;
        GraphRequestProcessor graphRequestProcessor = this.currentRequestProcessor;
        if (graphRequestProcessor == null) {
            return false;
        }
        Map map2 = this.defaultParameters;
        Map map3 = this.currentGraphParameters;
        if (map.isEmpty()) {
            mapBuild = this.currentRequiredParameters;
        } else {
            Map mapCreateMapBuilder = MapsKt.createMapBuilder();
            RequestsKt.putAllMetadata(mapCreateMapBuilder, this.currentGraph3AParameters);
            RequestsKt.putAllMetadata(mapCreateMapBuilder, map);
            RequestsKt.putAllMetadata(mapCreateMapBuilder, this.requiredParameters);
            Unit unit = Unit.INSTANCE;
            mapBuild = MapsKt.build(mapCreateMapBuilder);
        }
        boolean zSubmit$camera_camera2_pipe = graphRequestProcessor.submit$camera_camera2_pipe(z, list, map2, map3, mapBuild, this.currentRequestListeners);
        if (!zSubmit$camera_camera2_pipe) {
            if (z) {
                if (Log.INSTANCE.getWARN_LOGGABLE()) {
                    android.util.Log.w("CXCP", "Failed to repeat with " + CollectionsKt.single(list));
                    return zSubmit$camera_camera2_pipe;
                }
            } else if (map.isEmpty()) {
                if (Log.INSTANCE.getWARN_LOGGABLE()) {
                    android.util.Log.w("CXCP", "Failed to submit capture with " + list);
                    return zSubmit$camera_camera2_pipe;
                }
            } else if (Log.INSTANCE.getWARN_LOGGABLE()) {
                android.util.Log.w("CXCP", "Failed to trigger with " + CollectionsKt.single(list) + " and " + map);
            }
        }
        return zSubmit$camera_camera2_pipe;
    }

    public String toString() {
        return "GraphLoop(" + this.cameraGraphId + ')';
    }

    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
