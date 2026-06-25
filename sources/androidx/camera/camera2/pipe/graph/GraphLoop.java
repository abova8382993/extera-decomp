package androidx.camera.camera2.pipe.graph;

import androidx.camera.camera2.pipe.CameraGraphId;
import androidx.camera.camera2.pipe.Request;
import androidx.camera.camera2.pipe.RequestsKt;
import androidx.camera.camera2.pipe.core.Log;
import androidx.camera.camera2.pipe.core.ProcessingQueue;
import androidx.camera.camera2.pipe.graph.GraphCommand;
import java.io.Closeable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.LazyKt__LazyJVMKt$$ExternalSyntheticBUOutline0;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.atomicfu.AtomicBoolean;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineName;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;
import okhttp3.internal.url._UrlKt;
import okio.Segment$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000¨\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b&\b\u0000\u0018\u0000 \u0085\u00012\u00020\u0001:\u0004\u0086\u0001\u0085\u0001Bc\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0002\b\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0002\b\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\b\u0012\u0006\u0010\u000e\u001a\u00020\r\u0012\u0006\u0010\u0010\u001a\u00020\u000f¢\u0006\u0004\b\u0011\u0010\u0012J\u001e\u0010\u0017\u001a\u00020\u00162\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H\u0082@¢\u0006\u0004\b\u0017\u0010\u0018J\u001d\u0010\u001a\u001a\u00020\u00192\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H\u0002¢\u0006\u0004\b\u001a\u0010\u001bJ7\u0010!\u001a\u00020\u00162\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020\u001d2\b\b\u0002\u0010 \u001a\u00020\u001fH\u0002¢\u0006\u0004\b!\u0010\"J-\u0010$\u001a\u00020\u00162\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020#H\u0002¢\u0006\u0004\b$\u0010%J/\u0010'\u001a\u00020\u00162\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u001c\u001a\u00020\u00192\b\b\u0002\u0010&\u001a\u00020\u001fH\u0002¢\u0006\u0004\b'\u0010(J-\u0010*\u001a\u00020\u00162\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020)H\u0002¢\u0006\u0004\b*\u0010+J-\u0010-\u001a\u00020\u00162\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020,H\u0002¢\u0006\u0004\b-\u0010.J.\u00100\u001a\u00020\u00162\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020/H\u0082@¢\u0006\u0004\b0\u00101J%\u00102\u001a\u00020\u00162\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u001c\u001a\u00020\u0019H\u0002¢\u0006\u0004\b2\u00103J%\u00104\u001a\u00020\u00162\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u001c\u001a\u00020\u0019H\u0002¢\u0006\u0004\b4\u00103J\u001e\u00105\u001a\u00020\u00162\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H\u0082@¢\u0006\u0004\b5\u0010\u0018J\u000f\u00106\u001a\u00020\u001fH\u0002¢\u0006\u0004\b6\u00107J\u001d\u0010:\u001a\u00020\u00162\f\u00109\u001a\b\u0012\u0004\u0012\u0002080\bH\u0002¢\u0006\u0004\b:\u0010;J\u001d\u0010=\u001a\u00020\u00162\f\u0010<\u001a\b\u0012\u0004\u0012\u00020\u00140\bH\u0002¢\u0006\u0004\b=\u0010;J;\u0010@\u001a\u00020\u001f2\u0006\u0010>\u001a\u00020\u001f2\f\u00109\u001a\b\u0012\u0004\u0012\u0002080\b2\u0014\b\u0002\u0010?\u001a\u000e\u0012\u0002\b\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004H\u0002¢\u0006\u0004\b@\u0010AJ\u001b\u0010B\u001a\u00020\u001f2\f\u00109\u001a\b\u0012\u0004\u0012\u0002080\b¢\u0006\u0004\bB\u0010CJ!\u0010E\u001a\u00020\u001f2\u0012\u0010D\u001a\u000e\u0012\u0002\b\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004¢\u0006\u0004\bE\u0010FJ\r\u0010G\u001a\u00020\u0016¢\u0006\u0004\bG\u0010HJ\u000f\u0010I\u001a\u00020\u0016H\u0016¢\u0006\u0004\bI\u0010HJ\u000f\u0010K\u001a\u00020JH\u0016¢\u0006\u0004\bK\u0010LR\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010MR \u0010\u0006\u001a\u000e\u0012\u0002\b\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00048\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0006\u0010NR \u0010\u0007\u001a\u000e\u0012\u0002\b\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00048\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0007\u0010NR\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\n\u0010OR\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\b8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\f\u0010OR\u0014\u0010\u000e\u001a\u00020\r8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u000e\u0010PR\u0014\u0010Q\u001a\u00020\r8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bQ\u0010PR\u001a\u0010S\u001a\b\u0012\u0004\u0012\u00020\u00140R8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bS\u0010TR\u0014\u0010U\u001a\u00020\u00058\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bU\u0010VR\u0016\u0010W\u001a\u00020\u001f8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\bW\u0010XR\u0018\u0010Z\u001a\u0004\u0018\u00010Y8\u0002@\u0002X\u0083\u000e¢\u0006\u0006\n\u0004\bZ\u0010[R\u0018\u0010\\\u001a\u0004\u0018\u0001088\u0002@\u0002X\u0083\u000e¢\u0006\u0006\n\u0004\b\\\u0010]R\"\u0010^\u001a\u000e\u0012\u0002\b\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00048\u0002@\u0002X\u0083\u000e¢\u0006\u0006\n\u0004\b^\u0010NR\"\u0010_\u001a\u000e\u0012\u0002\b\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00048\u0002@\u0002X\u0083\u000e¢\u0006\u0006\n\u0004\b_\u0010NR\u001c\u0010`\u001a\b\u0012\u0004\u0012\u00020\t0\b8\u0002@\u0002X\u0083\u000e¢\u0006\u0006\n\u0004\b`\u0010OR\u0014\u0010b\u001a\u00020a8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bb\u0010cR\u0018\u0010d\u001a\u0004\u0018\u0001088\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\bd\u0010]R\"\u0010e\u001a\u000e\u0012\u0002\b\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00048\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\be\u0010NR\"\u0010f\u001a\u000e\u0012\u0002\b\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00048\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\bf\u0010NR\"\u0010g\u001a\u000e\u0012\u0002\b\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00048\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\bg\u0010NR\u001c\u0010h\u001a\b\u0012\u0004\u0012\u00020\t0\b8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\bh\u0010OR\u0018\u0010i\u001a\u0004\u0018\u00010Y8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\bi\u0010[R(\u0010o\u001a\u0004\u0018\u00010Y2\b\u0010j\u001a\u0004\u0018\u00010Y8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bk\u0010l\"\u0004\bm\u0010nR(\u0010t\u001a\u0004\u0018\u0001082\b\u0010j\u001a\u0004\u0018\u0001088F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bp\u0010q\"\u0004\br\u0010sR<\u0010y\u001a\u000e\u0012\u0002\b\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00042\u0012\u0010j\u001a\u000e\u0012\u0002\b\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bu\u0010v\"\u0004\bw\u0010xR<\u0010|\u001a\u000e\u0012\u0002\b\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00042\u0012\u0010j\u001a\u000e\u0012\u0002\b\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bz\u0010v\"\u0004\b{\u0010xR1\u0010\u0080\u0001\u001a\b\u0012\u0004\u0012\u00020\t0\b2\f\u0010j\u001a\b\u0012\u0004\u0012\u00020\t0\b8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b}\u0010~\"\u0004\b\u007f\u0010;R(\u0010\u0084\u0001\u001a\u00020\u001f2\u0006\u0010j\u001a\u00020\u001f8F@FX\u0086\u000e¢\u0006\u000f\u001a\u0005\b\u0081\u0001\u00107\"\u0006\b\u0082\u0001\u0010\u0083\u0001¨\u0006\u0087\u0001"}, m877d2 = {"Landroidx/camera/camera2/pipe/graph/GraphLoop;", "Ljava/io/Closeable;", "Landroidx/camera/camera2/pipe/CameraGraphId;", "cameraGraphId", _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, "defaultParameters", "requiredParameters", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/camera2/pipe/Request$Listener;", "requiredListeners", "Landroidx/camera/camera2/pipe/graph/GraphLoop$Listener;", "listeners", "Lkotlinx/coroutines/CoroutineScope;", "shutdownScope", "Lkotlinx/coroutines/CoroutineDispatcher;", "dispatcher", "<init>", "(Landroidx/camera/camera2/pipe/CameraGraphId;Ljava/util/Map;Ljava/util/Map;Ljava/util/List;Ljava/util/List;Lkotlinx/coroutines/CoroutineScope;Lkotlinx/coroutines/CoroutineDispatcher;)V", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/camera2/pipe/graph/GraphCommand;", "commands", _UrlKt.FRAGMENT_ENCODE_SET, "process", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", _UrlKt.FRAGMENT_ENCODE_SET, "selectGraphCommand", "(Ljava/util/List;)I", "idx", "Landroidx/camera/camera2/pipe/graph/GraphCommand$Capture;", "command", _UrlKt.FRAGMENT_ENCODE_SET, "repeatAllowed", "processCapture", "(Ljava/util/List;ILandroidx/camera/camera2/pipe/graph/GraphCommand$Capture;Z)V", "Landroidx/camera/camera2/pipe/graph/GraphCommand$Trigger;", "processTrigger", "(Ljava/util/List;ILandroidx/camera/camera2/pipe/graph/GraphCommand$Trigger;)V", "captureAllowed", "processRepeat", "(Ljava/util/List;IZ)V", "Landroidx/camera/camera2/pipe/graph/GraphCommand$Parameters;", "processParameters", "(Ljava/util/List;ILandroidx/camera/camera2/pipe/graph/GraphCommand$Parameters;)V", "Landroidx/camera/camera2/pipe/graph/GraphCommand$Listeners;", "processListeners", "(Ljava/util/List;ILandroidx/camera/camera2/pipe/graph/GraphCommand$Listeners;)V", "Landroidx/camera/camera2/pipe/graph/GraphCommand$RequestProcessor;", "processRequestProcessor", "(Ljava/util/List;ILandroidx/camera/camera2/pipe/graph/GraphCommand$RequestProcessor;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "processStop", "(Ljava/util/List;I)V", "processAbort", "processShutdown", "reissueRepeatingRequest", "()Z", "Landroidx/camera/camera2/pipe/Request;", "requests", "abortRequests", "(Ljava/util/List;)V", "unprocessedCommands", "finalizeUnprocessedCommands", "isRepeating", "oneTimeRequiredParameters", "buildAndSubmit", "(ZLjava/util/List;Ljava/util/Map;)Z", "submit", "(Ljava/util/List;)Z", "parameters", "trigger", "(Ljava/util/Map;)Z", "invalidate", "()V", "close", _UrlKt.FRAGMENT_ENCODE_SET, "toString", "()Ljava/lang/String;", "Landroidx/camera/camera2/pipe/CameraGraphId;", "Ljava/util/Map;", "Ljava/util/List;", "Lkotlinx/coroutines/CoroutineScope;", "graphLoopScope", "Landroidx/camera/camera2/pipe/core/ProcessingQueue;", "processingQueue", "Landroidx/camera/camera2/pipe/core/ProcessingQueue;", "lock", "Ljava/lang/Object;", "closed", "Z", "Landroidx/camera/camera2/pipe/graph/GraphRequestProcessor;", "_requestProcessor", "Landroidx/camera/camera2/pipe/graph/GraphRequestProcessor;", "_repeatingRequest", "Landroidx/camera/camera2/pipe/Request;", "_graphParameters", "_graph3AParameters", "_requestListeners", "Lkotlinx/atomicfu/AtomicBoolean;", "_captureProcessingEnabled", "Lkotlinx/atomicfu/AtomicBoolean;", "currentRepeatingRequest", "currentGraphParameters", "currentGraph3AParameters", "currentRequiredParameters", "currentRequestListeners", "currentRequestProcessor", "value", "getRequestProcessor", "()Landroidx/camera/camera2/pipe/graph/GraphRequestProcessor;", "setRequestProcessor", "(Landroidx/camera/camera2/pipe/graph/GraphRequestProcessor;)V", "requestProcessor", "getRepeatingRequest", "()Landroidx/camera/camera2/pipe/Request;", "setRepeatingRequest", "(Landroidx/camera/camera2/pipe/Request;)V", "repeatingRequest", "getGraphParameters", "()Ljava/util/Map;", "setGraphParameters", "(Ljava/util/Map;)V", "graphParameters", "getGraph3AParameters", "setGraph3AParameters", "graph3AParameters", "getRequestListeners", "()Ljava/util/List;", "setRequestListeners", "requestListeners", "getCaptureProcessingEnabled", "setCaptureProcessingEnabled", "(Z)V", "captureProcessingEnabled", "Companion", "Listener", "camera-camera2-pipe"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nGraphLoop.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GraphLoop.kt\nandroidx/camera/camera2/pipe/graph/GraphLoop\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 GraphLoop.kt\nandroidx/camera/camera2/pipe/graph/GraphLoop$Companion\n+ 4 Log.kt\nandroidx/camera/camera2/pipe/core/Log\n*L\n1#1,731:1\n1#2:732\n694#3,11:733\n694#3,11:744\n694#3,11:755\n694#3,11:766\n694#3,11:777\n694#3,11:788\n71#4,2:799\n71#4,2:801\n71#4,2:803\n*S KotlinDebug\n*F\n+ 1 GraphLoop.kt\nandroidx/camera/camera2/pipe/graph/GraphLoop\n*L\n417#1:733,11\n453#1:744,11\n465#1:755,11\n476#1:766,11\n517#1:777,11\n535#1:788,11\n668#1:799,2\n671#1:801,2\n673#1:803,2\n*E\n"})
public final class GraphLoop implements Closeable {
    private final AtomicBoolean _captureProcessingEnabled;
    private Map<?, ? extends Object> _graph3AParameters;
    private Map<?, ? extends Object> _graphParameters;
    private Request _repeatingRequest;
    private List<? extends Request.Listener> _requestListeners;
    private GraphRequestProcessor _requestProcessor;
    private final CameraGraphId cameraGraphId;
    private volatile boolean closed;
    private Map<?, ? extends Object> currentGraph3AParameters;
    private Map<?, ? extends Object> currentGraphParameters;
    private Request currentRepeatingRequest;
    private List<? extends Request.Listener> currentRequestListeners;
    private GraphRequestProcessor currentRequestProcessor;
    private Map<?, ? extends Object> currentRequiredParameters;
    private final Map<?, Object> defaultParameters;
    private final CoroutineScope graphLoopScope;
    private final List<Listener> listeners;
    private final Object lock;
    private final ProcessingQueue<GraphCommand> processingQueue;
    private final List<Request.Listener> requiredListeners;
    private final Map<?, Object> requiredParameters;
    private final CoroutineScope shutdownScope;

    @Metadata(m876d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b`\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\b\u0010\u0005\u001a\u00020\u0003H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0006À\u0006\u0001"}, m877d2 = {"Landroidx/camera/camera2/pipe/graph/GraphLoop$Listener;", _UrlKt.FRAGMENT_ENCODE_SET, "onStopRepeating", _UrlKt.FRAGMENT_ENCODE_SET, "onGraphStopped", "onGraphShutdown", "camera-camera2-pipe"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    public interface Listener {
        void onGraphShutdown();

        void onGraphStopped();

        void onStopRepeating();
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.pipe.graph.GraphLoop$processRequestProcessor$1 */
    @Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.pipe.graph.GraphLoop", m896f = "GraphLoop.kt", m897i = {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 2, 2, 2}, m898l = {479, 480, 488}, m899m = "processRequestProcessor", m900n = {"commands", "command", "commandsRemoved", "$this$removeUpTo$iv", "it", "a$iv", "b$iv", "commands", "command", "commandsRemoved", "$this$removeUpTo$iv", "a$iv", "b$iv", "commands", "command", "commandsRemoved"}, m902s = {"L$0", "L$1", "L$2", "L$3", "L$4", "I$0", "I$1", "L$0", "L$1", "L$2", "L$3", "I$0", "I$1", "L$0", "L$1", "L$2"}, m903v = 1)
    public static final class C02401 extends ContinuationImpl {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        /* synthetic */ Object result;

        public C02401(Continuation<? super C02401> continuation) {
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
    @Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.pipe.graph.GraphLoop", m896f = "GraphLoop.kt", m897i = {0, 1, 1, 1, 2, 2}, m898l = {566, 572, 573}, m899m = "processShutdown", m900n = {"commands", "commands", "command", "idx", "commands", "idx"}, m902s = {"L$0", "L$0", "L$1", "I$0", "L$0", "I$0"}, m903v = 1)
    public static final class C02411 extends ContinuationImpl {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C02411(Continuation<? super C02411> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return GraphLoop.this.processShutdown(null, this);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public GraphLoop(CameraGraphId cameraGraphId, Map<?, ? extends Object> map, Map<?, ? extends Object> map2, List<? extends Request.Listener> list, List<? extends Listener> list2, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.cameraGraphId = cameraGraphId;
        this.defaultParameters = map;
        this.requiredParameters = map2;
        this.requiredListeners = list;
        this.listeners = list2;
        this.shutdownScope = coroutineScope;
        CoroutineScope CoroutineScope = CoroutineScopeKt.CoroutineScope(coroutineDispatcher.plus(new CoroutineName("CXCP-GraphLoop")));
        this.graphLoopScope = CoroutineScope;
        this.processingQueue = ProcessingQueue.INSTANCE.processIn(new ProcessingQueue(0, new GraphLoop$processingQueue$1(this), new GraphLoop$processingQueue$2(this), 1, null), CoroutineScope);
        this.lock = new Object();
        this._graphParameters = MapsKt.emptyMap();
        this._graph3AParameters = MapsKt.emptyMap();
        this._requestListeners = CollectionsKt.emptyList();
        this._captureProcessingEnabled = AtomicFU.atomic(true);
        this.currentGraphParameters = MapsKt.emptyMap();
        this.currentGraph3AParameters = MapsKt.emptyMap();
        this.currentRequiredParameters = map2;
        this.currentRequestListeners = list;
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
                    this.listeners.get(i).onGraphStopped();
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
                    ProcessingQueue<GraphCommand> processingQueue = this.processingQueue;
                    if (request != null) {
                        processingQueue.tryEmit(new GraphCommand.Repeat(request));
                    } else {
                        processingQueue.tryEmit(GraphCommand.Stop.INSTANCE);
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
                this.listeners.get(i).onStopRepeating();
            }
        }
    }

    public final void setGraphParameters(Map<?, ? extends Object> map) {
        synchronized (this.lock) {
            this._graphParameters = map;
            this.processingQueue.tryEmit(new GraphCommand.Parameters(map, this._graph3AParameters));
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void setGraph3AParameters(Map<?, ? extends Object> map) {
        synchronized (this.lock) {
            this._graph3AParameters = map;
            this.processingQueue.tryEmit(new GraphCommand.Parameters(this._graphParameters, map));
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void setRequestListeners(List<? extends Request.Listener> list) {
        synchronized (this.lock) {
            this._requestListeners = list;
            this.processingQueue.tryEmit(new GraphCommand.Listeners(list));
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

    public final boolean submit(List<Request> requests) {
        if (this.processingQueue.tryEmit(new GraphCommand.Capture(requests))) {
            return true;
        }
        abortRequests(requests);
        return false;
    }

    public final boolean trigger(Map<?, ? extends Object> parameters) {
        if (getRepeatingRequest() == null) {
            Segment$$ExternalSyntheticBUOutline1.m992m("Cannot submit parameters without an active repeating request!");
            return false;
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
                    this.listeners.get(i).onGraphShutdown();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object process(List<GraphCommand> list, Continuation<? super Unit> continuation) {
        int iSelectGraphCommand = selectGraphCommand(list);
        GraphCommand graphCommand = list.get(iSelectGraphCommand);
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
                        LazyKt__LazyJVMKt$$ExternalSyntheticBUOutline0.m874m();
                        return null;
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
    private final int selectGraphCommand(java.util.List<androidx.camera.camera2.pipe.graph.GraphCommand> r11) {
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
            boolean r10 = r10.getCaptureProcessingEnabled()
            if (r10 == 0) goto L9b
            int r10 = r0.size()
            r1 = r2
        L86:
            if (r1 >= r10) goto L9b
            java.lang.Object r4 = r11.get(r1)
            androidx.camera.camera2.pipe.graph.GraphCommand r4 = (androidx.camera.camera2.pipe.graph.GraphCommand) r4
            boolean r5 = r4 instanceof androidx.camera.camera2.pipe.graph.GraphCommand.Capture
            if (r5 != 0) goto L9a
            boolean r4 = r4 instanceof androidx.camera.camera2.pipe.graph.GraphCommand.Trigger
            if (r4 == 0) goto L97
            goto L9a
        L97:
            int r1 = r1 + 1
            goto L86
        L9a:
            return r1
        L9b:
            int r10 = r0.size()
            r0 = r3
            r3 = r2
        La1:
            if (r3 >= r10) goto Lb3
            java.lang.Object r1 = r11.get(r3)
            androidx.camera.camera2.pipe.graph.GraphCommand r1 = (androidx.camera.camera2.pipe.graph.GraphCommand) r1
            boolean r1 = r1 instanceof androidx.camera.camera2.pipe.graph.GraphCommand.Repeat
            if (r1 == 0) goto Lb3
            int r0 = r3 + 1
            r9 = r3
            r3 = r0
            r0 = r9
            goto La1
        Lb3:
            if (r0 < 0) goto Lb6
            return r0
        Lb6:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.graph.GraphLoop.selectGraphCommand(java.util.List):int");
    }

    public static /* synthetic */ void processCapture$default(GraphLoop graphLoop, List list, int i, GraphCommand.Capture capture, boolean z, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            z = true;
        }
        graphLoop.processCapture(list, i, capture, z);
    }

    private final void processCapture(List<GraphCommand> commands, int idx, GraphCommand.Capture command, boolean repeatAllowed) {
        GraphLoop graphLoop;
        if (getCaptureProcessingEnabled()) {
            graphLoop = this;
            if (buildAndSubmit$default(graphLoop, false, command.getRequests(), null, 4, null)) {
                commands.remove(idx);
                return;
            }
        } else {
            graphLoop = this;
        }
        if (!repeatAllowed || idx <= 0) {
            return;
        }
        int i = idx - 1;
        if (!(commands.get(i) instanceof GraphCommand.Repeat)) {
            Segment$$ExternalSyntheticBUOutline1.m992m("Check failed.");
        } else {
            graphLoop.processRepeat(commands, i, false);
        }
    }

    private final void processTrigger(List<GraphCommand> commands, int idx, GraphCommand.Trigger command) {
        Request request = this.currentRepeatingRequest;
        if (request == null && idx == 0) {
            commands.remove(idx);
            return;
        }
        if (getCaptureProcessingEnabled() && request != null && buildAndSubmit(false, CollectionsKt.listOf(request), command.getTriggerParameters())) {
            commands.remove(idx);
            return;
        }
        if (idx > 0) {
            int i = idx - 1;
            if (!(commands.get(i) instanceof GraphCommand.Repeat)) {
                Segment$$ExternalSyntheticBUOutline1.m992m("Check failed.");
            } else {
                processRepeat(commands, i, false);
            }
        }
    }

    public static /* synthetic */ void processRepeat$default(GraphLoop graphLoop, List list, int i, boolean z, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z = true;
        }
        graphLoop.processRepeat(list, i, z);
    }

    private final void processRepeat(List<GraphCommand> commands, int idx, boolean captureAllowed) {
        int i;
        GraphLoop graphLoop;
        int i2 = idx;
        while (true) {
            int i3 = 0;
            if (-1 < i2) {
                GraphCommand graphCommand = commands.get(i2);
                if (graphCommand instanceof GraphCommand.Repeat) {
                    GraphCommand.Repeat repeat = (GraphCommand.Repeat) graphCommand;
                    graphLoop = this;
                    if (buildAndSubmit$default(graphLoop, true, CollectionsKt.listOf(repeat.getRequest()), null, 4, null)) {
                        graphLoop.currentRepeatingRequest = repeat.getRequest();
                        commands.remove(i2);
                        while (i3 < i2) {
                            if (commands.get(i3) instanceof GraphCommand.Repeat) {
                                commands.remove(i3);
                                i2--;
                            } else {
                                i3++;
                            }
                        }
                        return;
                    }
                } else {
                    graphLoop = this;
                }
                i2--;
                this = graphLoop;
            } else {
                GraphLoop graphLoop2 = this;
                if (!captureAllowed || (i = idx + 1) >= commands.size()) {
                    return;
                }
                GraphCommand graphCommand2 = commands.get(i);
                if (graphCommand2 instanceof GraphCommand.Capture) {
                    graphLoop2.processCapture(commands, i, (GraphCommand.Capture) graphCommand2, false);
                    return;
                } else {
                    if (graphCommand2 instanceof GraphCommand.Trigger) {
                        graphLoop2.processTrigger(commands, i, (GraphCommand.Trigger) graphCommand2);
                        return;
                    }
                    return;
                }
            }
        }
    }

    private final void processParameters(List<GraphCommand> commands, int idx, GraphCommand.Parameters command) {
        Map<?, ? extends Object> mapBuild;
        this.currentGraphParameters = command.getGraphParameters();
        this.currentGraph3AParameters = command.getGraph3AParameters();
        if (command.getGraph3AParameters().isEmpty()) {
            mapBuild = this.requiredParameters;
        } else {
            Map mapCreateMapBuilder = MapsKt.createMapBuilder();
            RequestsKt.putAllMetadata(mapCreateMapBuilder, command.getGraph3AParameters());
            RequestsKt.putAllMetadata(mapCreateMapBuilder, this.requiredParameters);
            mapBuild = MapsKt.build(mapCreateMapBuilder);
        }
        this.currentRequiredParameters = mapBuild;
        commands.remove(idx);
        int i = 0;
        while (i < idx) {
            if (commands.get(i) instanceof GraphCommand.Parameters) {
                commands.remove(i);
                idx--;
            } else {
                i++;
            }
        }
        reissueRepeatingRequest();
    }

    private final void processListeners(List<GraphCommand> commands, int idx, GraphCommand.Listeners command) {
        this.currentRequestListeners = CollectionsKt.distinct(CollectionsKt.plus((Collection) command.getListeners(), (Iterable) this.requiredListeners));
        commands.remove(idx);
        int i = 0;
        while (i < idx) {
            if (commands.get(i) instanceof GraphCommand.Listeners) {
                commands.remove(i);
                idx--;
            } else {
                i++;
            }
        }
        reissueRepeatingRequest();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:31:0x00dc -> B:37:0x00f7). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:35:0x00f5 -> B:36:0x00f6). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:38:0x0104 -> B:39:0x0105). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object processRequestProcessor(java.util.List<androidx.camera.camera2.pipe.graph.GraphCommand> r18, int r19, androidx.camera.camera2.pipe.graph.GraphCommand.RequestProcessor r20, kotlin.coroutines.Continuation<? super kotlin.Unit> r21) {
        /*
            Method dump skipped, instruction units count: 339
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.graph.GraphLoop.processRequestProcessor(java.util.List, int, androidx.camera.camera2.pipe.graph.GraphCommand$RequestProcessor, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final void processStop(List<GraphCommand> commands, int idx) {
        GraphRequestProcessor graphRequestProcessor = this.currentRequestProcessor;
        if (graphRequestProcessor != null) {
            graphRequestProcessor.stopRepeating$camera_camera2_pipe();
        }
        this.currentRepeatingRequest = null;
        commands.remove(idx);
        int i = 0;
        while (i < idx) {
            GraphCommand graphCommand = commands.get(i);
            if (Intrinsics.areEqual(graphCommand, GraphCommand.Stop.INSTANCE) || (graphCommand instanceof GraphCommand.Repeat)) {
                commands.remove(i);
                idx--;
            } else {
                i++;
            }
        }
    }

    private final void processAbort(List<GraphCommand> commands, int idx) {
        GraphRequestProcessor graphRequestProcessor = this.currentRequestProcessor;
        if (graphRequestProcessor != null) {
            graphRequestProcessor.abortCaptures$camera_camera2_pipe();
        }
        this.currentRepeatingRequest = null;
        commands.remove(idx);
        int i = 0;
        while (i < idx) {
            GraphCommand graphCommand = commands.get(i);
            if (!Intrinsics.areEqual(graphCommand, GraphCommand.Stop.INSTANCE) && !Intrinsics.areEqual(graphCommand, GraphCommand.Abort.INSTANCE) && !(graphCommand instanceof GraphCommand.Repeat) && !(graphCommand instanceof GraphCommand.Trigger)) {
                if (graphCommand instanceof GraphCommand.Capture) {
                    abortRequests(((GraphCommand.Capture) graphCommand).getRequests());
                } else {
                    i++;
                }
            }
            commands.remove(i);
            idx--;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0096, code lost:
    
        if (r12.shutdown$camera_camera2_pipe(r0) == r1) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00e7, code lost:
    
        if (r12.shutdown$camera_camera2_pipe(r0) == r1) goto L46;
     */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:32:0x00ae -> B:48:0x00ec). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:43:0x00d7 -> B:47:0x00ea). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:45:0x00e7 -> B:47:0x00ea). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object processShutdown(java.util.List<androidx.camera.camera2.pipe.graph.GraphCommand> r11, kotlin.coroutines.Continuation<? super kotlin.Unit> r12) {
        /*
            Method dump skipped, instruction units count: 249
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

    private final void abortRequests(List<Request> requests) {
        List<Request> list = requests;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Request request = requests.get(i);
            int size2 = this.currentRequestListeners.size();
            for (int i2 = 0; i2 < size2; i2++) {
                this.currentRequestListeners.get(i2).onAborted(request);
            }
        }
        int size3 = list.size();
        for (int i3 = 0; i3 < size3; i3++) {
            Request request2 = requests.get(i3);
            int size4 = request2.getListeners().size();
            for (int i4 = 0; i4 < size4; i4++) {
                request2.getListeners().get(i4).onAborted(request2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void finalizeUnprocessedCommands(List<? extends GraphCommand> unprocessedCommands) {
        for (GraphCommand graphCommand : unprocessedCommands) {
            if (graphCommand instanceof GraphCommand.Capture) {
                abortRequests(((GraphCommand.Capture) graphCommand).getRequests());
            } else if (graphCommand instanceof GraphCommand.RequestProcessor) {
                BuildersKt__Builders_commonKt.launch$default(this.shutdownScope, null, CoroutineStart.UNDISPATCHED, new C02391(graphCommand, null), 1, null);
            }
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.pipe.graph.GraphLoop$finalizeUnprocessedCommands$1 */
    @Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.pipe.graph.GraphLoop$finalizeUnprocessedCommands$1", m896f = "GraphLoop.kt", m897i = {}, m898l = {630, 631}, m899m = "invokeSuspend", m900n = {}, m902s = {}, m903v = 1)
    public static final class C02391 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ GraphCommand $command;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C02391(GraphCommand graphCommand, Continuation<? super C02391> continuation) {
            super(2, continuation);
            this.$command = graphCommand;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C02391(this.$command, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02391) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:18:0x0043, code lost:
        
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
                if (r1 == 0) goto L1d
                if (r1 == r3) goto L19
                if (r1 != r2) goto L12
                kotlin.ResultKt.throwOnFailure(r5)
                goto L46
            L12:
                java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
                okio.Segment$$ExternalSyntheticBUOutline1.m992m(r4)
                r4 = 0
                return r4
            L19:
                kotlin.ResultKt.throwOnFailure(r5)
                goto L33
            L1d:
                kotlin.ResultKt.throwOnFailure(r5)
                androidx.camera.camera2.pipe.graph.GraphCommand r5 = r4.$command
                androidx.camera.camera2.pipe.graph.GraphCommand$RequestProcessor r5 = (androidx.camera.camera2.pipe.graph.GraphCommand.RequestProcessor) r5
                androidx.camera.camera2.pipe.graph.GraphRequestProcessor r5 = r5.getOld()
                if (r5 == 0) goto L33
                r4.label = r3
                java.lang.Object r5 = r5.shutdown$camera_camera2_pipe(r4)
                if (r5 != r0) goto L33
                goto L45
            L33:
                androidx.camera.camera2.pipe.graph.GraphCommand r5 = r4.$command
                androidx.camera.camera2.pipe.graph.GraphCommand$RequestProcessor r5 = (androidx.camera.camera2.pipe.graph.GraphCommand.RequestProcessor) r5
                androidx.camera.camera2.pipe.graph.GraphRequestProcessor r5 = r5.getNew()
                if (r5 == 0) goto L46
                r4.label = r2
                java.lang.Object r4 = r5.shutdown$camera_camera2_pipe(r4)
                if (r4 != r0) goto L46
            L45:
                return r0
            L46:
                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.graph.GraphLoop.C02391.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ boolean buildAndSubmit$default(GraphLoop graphLoop, boolean z, List list, Map map, int i, Object obj) {
        if ((i & 4) != 0) {
            map = MapsKt.emptyMap();
        }
        return graphLoop.buildAndSubmit(z, list, map);
    }

    private final boolean buildAndSubmit(boolean isRepeating, List<Request> requests, Map<?, ? extends Object> oneTimeRequiredParameters) throws Exception {
        Map<?, ? extends Object> mapBuild;
        GraphRequestProcessor graphRequestProcessor = this.currentRequestProcessor;
        if (graphRequestProcessor == null) {
            return false;
        }
        Map<?, ? extends Object> map = this.defaultParameters;
        Map<?, ? extends Object> map2 = this.currentGraphParameters;
        if (oneTimeRequiredParameters.isEmpty()) {
            mapBuild = this.currentRequiredParameters;
        } else {
            Map mapCreateMapBuilder = MapsKt.createMapBuilder();
            RequestsKt.putAllMetadata(mapCreateMapBuilder, this.currentGraph3AParameters);
            RequestsKt.putAllMetadata(mapCreateMapBuilder, oneTimeRequiredParameters);
            RequestsKt.putAllMetadata(mapCreateMapBuilder, this.requiredParameters);
            Unit unit = Unit.INSTANCE;
            mapBuild = MapsKt.build(mapCreateMapBuilder);
        }
        boolean zSubmit$camera_camera2_pipe = graphRequestProcessor.submit$camera_camera2_pipe(isRepeating, requests, map, map2, mapBuild, this.currentRequestListeners);
        if (!zSubmit$camera_camera2_pipe) {
            if (isRepeating) {
                if (Log.INSTANCE.getWARN_LOGGABLE()) {
                    android.util.Log.w("CXCP", "Failed to repeat with " + CollectionsKt.single((List) requests));
                    return zSubmit$camera_camera2_pipe;
                }
            } else if (oneTimeRequiredParameters.isEmpty()) {
                if (Log.INSTANCE.getWARN_LOGGABLE()) {
                    android.util.Log.w("CXCP", "Failed to submit capture with " + requests);
                    return zSubmit$camera_camera2_pipe;
                }
            } else if (Log.INSTANCE.getWARN_LOGGABLE()) {
                android.util.Log.w("CXCP", "Failed to trigger with " + CollectionsKt.single((List) requests) + " and " + oneTimeRequiredParameters);
            }
        }
        return zSubmit$camera_camera2_pipe;
    }

    public String toString() {
        return "GraphLoop(" + this.cameraGraphId + ')';
    }
}
