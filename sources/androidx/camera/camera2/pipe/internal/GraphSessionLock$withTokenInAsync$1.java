package androidx.camera.camera2.pipe.internal;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: loaded from: classes3.dex */
final class GraphSessionLock$withTokenInAsync$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $action;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ GraphSessionLock this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    GraphSessionLock$withTokenInAsync$1(GraphSessionLock graphSessionLock, Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = graphSessionLock;
        this.$action = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        GraphSessionLock$withTokenInAsync$1 graphSessionLock$withTokenInAsync$1 = new GraphSessionLock$withTokenInAsync$1(this.this$0, this.$action, continuation);
        graphSessionLock$withTokenInAsync$1.L$0 = obj;
        return graphSessionLock$withTokenInAsync$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((GraphSessionLock$withTokenInAsync$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0082 A[RETURN] */
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
            if (r1 == 0) goto L37
            if (r1 == r4) goto L26
            if (r1 == r3) goto L1e
            if (r1 != r2) goto L16
            kotlin.ResultKt.throwOnFailure(r10)
            return r10
        L16:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r0)
            throw r10
        L1e:
            java.lang.Object r1 = r9.L$0
            kotlinx.coroutines.CoroutineScope r1 = (kotlinx.coroutines.CoroutineScope) r1
            kotlin.ResultKt.throwOnFailure(r10)
            goto L72
        L26:
            java.lang.Object r1 = r9.L$2
            androidx.camera.camera2.pipe.internal.GraphSessionLock r1 = (androidx.camera.camera2.pipe.internal.GraphSessionLock) r1
            java.lang.Object r4 = r9.L$1
            kotlinx.coroutines.sync.Mutex r4 = (kotlinx.coroutines.sync.Mutex) r4
            java.lang.Object r6 = r9.L$0
            kotlinx.coroutines.CoroutineScope r6 = (kotlinx.coroutines.CoroutineScope) r6
            kotlin.ResultKt.throwOnFailure(r10)
            r10 = r6
            goto L54
        L37:
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
            if (r4 != r0) goto L53
            goto L81
        L53:
            r4 = r6
        L54:
            androidx.camera.camera2.pipe.core.MutexToken r6 = new androidx.camera.camera2.pipe.core.MutexToken
            r6.<init>(r4)
            androidx.camera.camera2.pipe.internal.GraphSessionLock$withTokenInAsync$1$deferred$1 r4 = new androidx.camera.camera2.pipe.internal.GraphSessionLock$withTokenInAsync$1$deferred$1
            kotlin.jvm.functions.Function2 r7 = r9.$action
            r4.<init>(r7, r5)
            r9.L$0 = r10
            r9.L$1 = r5
            r9.L$2 = r5
            r9.label = r3
            java.lang.Object r1 = androidx.camera.camera2.pipe.internal.GraphSessionLock.access$use(r1, r6, r4, r9)
            if (r1 != r0) goto L6f
            goto L81
        L6f:
            r8 = r1
            r1 = r10
            r10 = r8
        L72:
            kotlinx.coroutines.Deferred r10 = (kotlinx.coroutines.Deferred) r10
            kotlinx.coroutines.CoroutineScopeKt.ensureActive(r1)
            r9.L$0 = r5
            r9.label = r2
            java.lang.Object r10 = r10.await(r9)
            if (r10 != r0) goto L82
        L81:
            return r0
        L82:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.internal.GraphSessionLock$withTokenInAsync$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
