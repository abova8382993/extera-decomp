package androidx.camera.camera2.pipe.internal;

import androidx.camera.camera2.pipe.core.Token;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;

/* JADX INFO: Add missing generic type declarations: [T] */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000\b\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", "T", "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
@DebugMetadata(m895c = "androidx.camera.camera2.pipe.internal.GraphSessionLock$withTokenInAsync$1", m896f = "GraphSessionLock.kt", m897i = {0, 0, 1}, m898l = {105, 64, 67}, m899m = "invokeSuspend", m900n = {"$this$asyncUndispatched", "$this$acquireTokenAndSuspend$iv", "$this$asyncUndispatched"}, m902s = {"L$0", "L$1", "L$0"}, m903v = 1)
@SourceDebugExtension({"SMAP\nGraphSessionLock.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GraphSessionLock.kt\nandroidx/camera/camera2/pipe/internal/GraphSessionLock$withTokenInAsync$1\n+ 2 Mutexes.kt\nandroidx/camera/camera2/pipe/core/MutexesKt\n*L\n1#1,104:1\n107#2,2:105\n*S KotlinDebug\n*F\n+ 1 GraphSessionLock.kt\nandroidx/camera/camera2/pipe/internal/GraphSessionLock$withTokenInAsync$1\n*L\n64#1:105,2\n*E\n"})
public final class GraphSessionLock$withTokenInAsync$1<T> extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super T>, Object> {
    final /* synthetic */ Function2<Token, Continuation<? super Deferred<? extends T>>, Object> $action;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ GraphSessionLock this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public GraphSessionLock$withTokenInAsync$1(GraphSessionLock graphSessionLock, Function2<? super Token, ? super Continuation<? super Deferred<? extends T>>, ? extends Object> function2, Continuation<? super GraphSessionLock$withTokenInAsync$1> continuation) {
        super(2, continuation);
        this.this$0 = graphSessionLock;
        this.$action = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        GraphSessionLock$withTokenInAsync$1 graphSessionLock$withTokenInAsync$1 = new GraphSessionLock$withTokenInAsync$1(this.this$0, this.$action, continuation);
        graphSessionLock$withTokenInAsync$1.L$0 = obj;
        return graphSessionLock$withTokenInAsync$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super T> continuation) {
        return ((GraphSessionLock$withTokenInAsync$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0081 A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r9.label
            r2 = 3
            r3 = 2
            r4 = 1
            r5 = 0
            if (r1 == 0) goto L36
            if (r1 == r4) goto L25
            if (r1 == r3) goto L1d
            if (r1 != r2) goto L16
            kotlin.ResultKt.throwOnFailure(r10)
            return r10
        L16:
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r9)
            r9 = 0
            return r9
        L1d:
            java.lang.Object r1 = r9.L$0
            kotlinx.coroutines.CoroutineScope r1 = (kotlinx.coroutines.CoroutineScope) r1
            kotlin.ResultKt.throwOnFailure(r10)
            goto L71
        L25:
            java.lang.Object r1 = r9.L$2
            androidx.camera.camera2.pipe.internal.GraphSessionLock r1 = (androidx.camera.camera2.pipe.internal.GraphSessionLock) r1
            java.lang.Object r4 = r9.L$1
            kotlinx.coroutines.sync.Mutex r4 = (kotlinx.coroutines.sync.Mutex) r4
            java.lang.Object r6 = r9.L$0
            kotlinx.coroutines.CoroutineScope r6 = (kotlinx.coroutines.CoroutineScope) r6
            kotlin.ResultKt.throwOnFailure(r10)
            r10 = r6
            goto L53
        L36:
            kotlin.ResultKt.throwOnFailure(r10)
            java.lang.Object r10 = r9.L$0
            kotlinx.coroutines.CoroutineScope r10 = (kotlinx.coroutines.CoroutineScope) r10
            androidx.camera.camera2.pipe.internal.GraphSessionLock r1 = r9.this$0
            kotlinx.coroutines.sync.Mutex r6 = androidx.camera.camera2.pipe.internal.GraphSessionLock.access$getMutex$p(r1)
            r9.L$0 = r10
            r9.L$1 = r6
            r9.L$2 = r1
            r9.label = r4
            java.lang.Object r4 = androidx.camera.camera2.pipe.core.MutexesKt.access$lockAndSuspend(r6, r9)
            if (r4 != r0) goto L52
            goto L80
        L52:
            r4 = r6
        L53:
            androidx.camera.camera2.pipe.core.MutexToken r6 = new androidx.camera.camera2.pipe.core.MutexToken
            r6.<init>(r4)
            androidx.camera.camera2.pipe.internal.GraphSessionLock$withTokenInAsync$1$deferred$1 r4 = new androidx.camera.camera2.pipe.internal.GraphSessionLock$withTokenInAsync$1$deferred$1
            kotlin.jvm.functions.Function2<androidx.camera.camera2.pipe.core.Token, kotlin.coroutines.Continuation<? super kotlinx.coroutines.Deferred<? extends T>>, java.lang.Object> r7 = r9.$action
            r4.<init>(r7, r5)
            r9.L$0 = r10
            r9.L$1 = r5
            r9.L$2 = r5
            r9.label = r3
            java.lang.Object r1 = androidx.camera.camera2.pipe.internal.GraphSessionLock.access$use(r1, r6, r4, r9)
            if (r1 != r0) goto L6e
            goto L80
        L6e:
            r8 = r1
            r1 = r10
            r10 = r8
        L71:
            kotlinx.coroutines.Deferred r10 = (kotlinx.coroutines.Deferred) r10
            kotlinx.coroutines.CoroutineScopeKt.ensureActive(r1)
            r9.L$0 = r5
            r9.label = r2
            java.lang.Object r9 = r10.await(r9)
            if (r9 != r0) goto L81
        L80:
            return r0
        L81:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.internal.GraphSessionLock$withTokenInAsync$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
