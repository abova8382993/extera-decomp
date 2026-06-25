package androidx.camera.camera2.pipe.core;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.atomicfu.AtomicBoolean;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.channels.ChannelResult;
import okhttp3.internal.url._UrlKt;
import okio.Segment$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u0000 '*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0001'BW\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\u001a\b\u0002\u0010\b\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u0012(\u0010\f\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\n\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u00020\t¢\u0006\u0004\b\r\u0010\u000eJ\u0010\u0010\u000f\u001a\u00020\u0007H\u0082@¢\u0006\u0004\b\u000f\u0010\u0010J\u0019\u0010\u0013\u001a\u00020\u00072\b\u0010\u0012\u001a\u0004\u0018\u00010\u0011H\u0002¢\u0006\u0004\b\u0013\u0010\u0014J\u0015\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0015\u001a\u00028\u0000¢\u0006\u0004\b\u0017\u0010\u0018R\u0017\u0010\u0004\u001a\u00020\u00038\u0006¢\u0006\f\n\u0004\b\u0004\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR&\u0010\b\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0006\u0012\u0004\u0012\u00020\u00070\u00058\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\b\u0010\u001cR6\u0010\f\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\n\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u00020\t8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\f\u0010\u001dR\u0014\u0010\u001f\u001a\u00020\u001e8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u001f\u0010 R\u001a\u0010\"\u001a\b\u0012\u0004\u0012\u00028\u00000!8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\"\u0010#R\u001a\u0010%\u001a\b\u0012\u0004\u0012\u00028\u00000$8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b%\u0010&¨\u0006("}, m877d2 = {"Landroidx/camera/camera2/pipe/core/ProcessingQueue;", "T", _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, "capacity", "Lkotlin/Function1;", _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, "onUnprocessedElements", "Lkotlin/Function2;", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlin/coroutines/Continuation;", "process", "<init>", "(ILkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)V", "processingLoop", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", _UrlKt.FRAGMENT_ENCODE_SET, "cause", "releaseUnprocessedElements", "(Ljava/lang/Throwable;)V", "element", _UrlKt.FRAGMENT_ENCODE_SET, "tryEmit", "(Ljava/lang/Object;)Z", "I", "getCapacity", "()I", "Lkotlin/jvm/functions/Function1;", "Lkotlin/jvm/functions/Function2;", "Lkotlinx/atomicfu/AtomicBoolean;", "started", "Lkotlinx/atomicfu/AtomicBoolean;", "Lkotlinx/coroutines/channels/Channel;", "channel", "Lkotlinx/coroutines/channels/Channel;", "Lkotlin/collections/ArrayDeque;", "queue", "Lkotlin/collections/ArrayDeque;", "Companion", "camera-camera2-pipe"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nProcessingQueue.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ProcessingQueue.kt\nandroidx/camera/camera2/pipe/core/ProcessingQueue\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,174:1\n1#2:175\n*E\n"})
public final class ProcessingQueue<T> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final int capacity;
    private final Channel<T> channel;
    private final Function1<List<? extends T>, Unit> onUnprocessedElements;
    private final Function2<List<T>, Continuation<? super Unit>, Object> process;
    private final ArrayDeque<T> queue;
    private final AtomicBoolean started;

    /* JADX INFO: renamed from: androidx.camera.camera2.pipe.core.ProcessingQueue$processingLoop$1 */
    @Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.pipe.core.ProcessingQueue", m896f = "ProcessingQueue.kt", m897i = {1}, m898l = {102, 117}, m899m = "processingLoop", m900n = {"size"}, m902s = {"I$0"}, m903v = 1)
    public static final class C02311 extends ContinuationImpl {
        int I$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ ProcessingQueue<T> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C02311(ProcessingQueue<T> processingQueue, Continuation<? super C02311> continuation) {
            super(continuation);
            this.this$0 = processingQueue;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.processingLoop(this);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ProcessingQueue(int i, Function1<? super List<? extends T>, Unit> function1, Function2<? super List<T>, ? super Continuation<? super Unit>, ? extends Object> function2) {
        this.capacity = i;
        this.onUnprocessedElements = function1;
        this.process = function2;
        this.started = AtomicFU.atomic(false);
        this.channel = ChannelKt.Channel$default(i, null, new Function1() { // from class: androidx.camera.camera2.pipe.core.ProcessingQueue$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ProcessingQueue.$r8$lambda$S7l7sqMHhvUaxQ5VH8QW7tCGrXU(this.f$0, obj);
            }
        }, 2, null);
        this.queue = new ArrayDeque<>();
    }

    public /* synthetic */ ProcessingQueue(int i, Function1 function1, Function2 function2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? Integer.MAX_VALUE : i, (i2 & 2) != 0 ? new Function1() { // from class: androidx.camera.camera2.pipe.core.ProcessingQueue$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Unit.INSTANCE;
            }
        } : function1, function2);
    }

    public static Unit $r8$lambda$S7l7sqMHhvUaxQ5VH8QW7tCGrXU(ProcessingQueue processingQueue, Object obj) {
        processingQueue.queue.add(obj);
        return Unit.INSTANCE;
    }

    public final boolean tryEmit(T element) {
        return ChannelResult.m5021isSuccessimpl(this.channel.mo5010trySendJP2dKIU(element));
    }

    /* JADX WARN: Code restructure failed: missing block: B:79:0x0082, code lost:
    
        if (r7.invoke(r5, r0) == r1) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x008b, code lost:
    
        if (r2 == r6.queue.size()) goto L67;
     */
    /* JADX WARN: Path cross not found for [B:67:0x003e, B:71:0x004e], limit reached: 41 */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0013  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x003e A[Catch: all -> 0x002e, TRY_ENTER, TryCatch #0 {all -> 0x002e, blocks: (B:58:0x002a, B:81:0x0085, B:71:0x004e, B:73:0x0056, B:74:0x005c, B:77:0x0064, B:78:0x0072, B:67:0x003e, B:70:0x0049, B:64:0x0037), top: B:86:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0049 A[Catch: all -> 0x002e, TryCatch #0 {all -> 0x002e, blocks: (B:58:0x002a, B:81:0x0085, B:71:0x004e, B:73:0x0056, B:74:0x005c, B:77:0x0064, B:78:0x0072, B:67:0x003e, B:70:0x0049, B:64:0x0037), top: B:86:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0056 A[Catch: all -> 0x002e, TryCatch #0 {all -> 0x002e, blocks: (B:58:0x002a, B:81:0x0085, B:71:0x004e, B:73:0x0056, B:74:0x005c, B:77:0x0064, B:78:0x0072, B:67:0x003e, B:70:0x0049, B:64:0x0037), top: B:86:0x0022 }] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:79:0x0082 -> B:81:0x0085). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object processingLoop(kotlin.coroutines.Continuation<? super kotlin.Unit> r7) {
        /*
            r6 = this;
            boolean r0 = r7 instanceof androidx.camera.camera2.pipe.core.ProcessingQueue.C02311
            if (r0 == 0) goto L13
            r0 = r7
            androidx.camera.camera2.pipe.core.ProcessingQueue$processingLoop$1 r0 = (androidx.camera.camera2.pipe.core.ProcessingQueue.C02311) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.camera.camera2.pipe.core.ProcessingQueue$processingLoop$1 r0 = new androidx.camera.camera2.pipe.core.ProcessingQueue$processingLoop$1
            r0.<init>(r6, r7)
        L18:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L3b
            if (r2 == r4) goto L37
            if (r2 != r3) goto L30
            int r2 = r0.I$0
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L2e
            goto L85
        L2e:
            r7 = move-exception
            goto L8e
        L30:
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r6)
            r6 = 0
            return r6
        L37:
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L2e
            goto L49
        L3b:
            kotlin.ResultKt.throwOnFailure(r7)
        L3e:
            kotlinx.coroutines.channels.Channel<T> r7 = r6.channel     // Catch: java.lang.Throwable -> L2e
            r0.label = r4     // Catch: java.lang.Throwable -> L2e
            java.lang.Object r7 = r7.receive(r0)     // Catch: java.lang.Throwable -> L2e
            if (r7 != r1) goto L49
            goto L84
        L49:
            kotlin.collections.ArrayDeque<T> r2 = r6.queue     // Catch: java.lang.Throwable -> L2e
            r2.add(r7)     // Catch: java.lang.Throwable -> L2e
        L4e:
            kotlin.collections.ArrayDeque<T> r7 = r6.queue     // Catch: java.lang.Throwable -> L2e
            boolean r7 = r7.isEmpty()     // Catch: java.lang.Throwable -> L2e
            if (r7 != 0) goto L3e
            kotlinx.coroutines.channels.Channel<T> r7 = r6.channel     // Catch: java.lang.Throwable -> L2e
            java.lang.Object r7 = r7.mo5009tryReceivePtdJZtk()     // Catch: java.lang.Throwable -> L2e
        L5c:
            boolean r2 = kotlinx.coroutines.channels.ChannelResult.m5021isSuccessimpl(r7)     // Catch: java.lang.Throwable -> L2e
            kotlin.collections.ArrayDeque<T> r5 = r6.queue
            if (r2 == 0) goto L72
            java.lang.Object r7 = kotlinx.coroutines.channels.ChannelResult.m5018getOrThrowimpl(r7)     // Catch: java.lang.Throwable -> L2e
            r5.add(r7)     // Catch: java.lang.Throwable -> L2e
            kotlinx.coroutines.channels.Channel<T> r7 = r6.channel     // Catch: java.lang.Throwable -> L2e
            java.lang.Object r7 = r7.mo5009tryReceivePtdJZtk()     // Catch: java.lang.Throwable -> L2e
            goto L5c
        L72:
            int r2 = r5.size()     // Catch: java.lang.Throwable -> L2e
            kotlin.jvm.functions.Function2<java.util.List<T>, kotlin.coroutines.Continuation<? super kotlin.Unit>, java.lang.Object> r7 = r6.process     // Catch: java.lang.Throwable -> L2e
            kotlin.collections.ArrayDeque<T> r5 = r6.queue     // Catch: java.lang.Throwable -> L2e
            r0.I$0 = r2     // Catch: java.lang.Throwable -> L2e
            r0.label = r3     // Catch: java.lang.Throwable -> L2e
            java.lang.Object r7 = r7.invoke(r5, r0)     // Catch: java.lang.Throwable -> L2e
            if (r7 != r1) goto L85
        L84:
            return r1
        L85:
            kotlin.collections.ArrayDeque<T> r7 = r6.queue     // Catch: java.lang.Throwable -> L2e
            int r7 = r7.size()     // Catch: java.lang.Throwable -> L2e
            if (r2 != r7) goto L4e
            goto L3e
        L8e:
            r6.releaseUnprocessedElements(r7)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.core.ProcessingQueue.processingLoop(kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void releaseUnprocessedElements(Throwable cause) {
        ArrayDeque<T> arrayDeque;
        if (this.channel.close(cause)) {
            Object objMo5009tryReceivePtdJZtk = this.channel.mo5009tryReceivePtdJZtk();
            while (true) {
                boolean zM5021isSuccessimpl = ChannelResult.m5021isSuccessimpl(objMo5009tryReceivePtdJZtk);
                arrayDeque = this.queue;
                if (!zM5021isSuccessimpl) {
                    break;
                }
                arrayDeque.add((T) ChannelResult.m5018getOrThrowimpl(objMo5009tryReceivePtdJZtk));
                objMo5009tryReceivePtdJZtk = this.channel.mo5009tryReceivePtdJZtk();
            }
            if (arrayDeque.isEmpty()) {
                return;
            }
            this.onUnprocessedElements.invoke(CollectionsKt.toMutableList((Collection) this.queue));
            this.queue.clear();
        }
    }

    @Metadata(m876d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0080\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J$\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0001\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\u00052\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, m877d2 = {"Landroidx/camera/camera2/pipe/core/ProcessingQueue$Companion;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "processIn", "Landroidx/camera/camera2/pipe/core/ProcessingQueue;", "T", "scope", "Lkotlinx/coroutines/CoroutineScope;", "camera-camera2-pipe"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final <T> ProcessingQueue<T> processIn(ProcessingQueue<T> processingQueue, CoroutineScope coroutineScope) {
            if (!((ProcessingQueue) processingQueue).started.compareAndSet(false, true)) {
                Segment$$ExternalSyntheticBUOutline1.m992m("ProcessingQueue cannot be re-started!");
                return null;
            }
            if (BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new ProcessingQueue$Companion$processIn$job$1(processingQueue, null), 3, null).isCancelled()) {
                processingQueue.releaseUnprocessedElements(null);
            }
            return processingQueue;
        }
    }
}
