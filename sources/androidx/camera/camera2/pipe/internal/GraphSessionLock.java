package androidx.camera.camera2.pipe.internal;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CompletableJob;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexKt;

/* JADX INFO: loaded from: classes3.dex */
public final class GraphSessionLock {
    private final Mutex mutex = MutexKt.Mutex$default(false, 1, null);

    /* JADX INFO: renamed from: androidx.camera.camera2.pipe.internal.GraphSessionLock$use$1 */
    static final class C02451 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C02451(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return GraphSessionLock.this.use(null, null, this);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object acquireToken$camera_camera2_pipe(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof androidx.camera.camera2.pipe.internal.GraphSessionLock$acquireToken$1
            if (r0 == 0) goto L13
            r0 = r5
            androidx.camera.camera2.pipe.internal.GraphSessionLock$acquireToken$1 r0 = (androidx.camera.camera2.pipe.internal.GraphSessionLock$acquireToken$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.camera.camera2.pipe.internal.GraphSessionLock$acquireToken$1 r0 = new androidx.camera.camera2.pipe.internal.GraphSessionLock$acquireToken$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L35
            if (r2 != r3) goto L2d
            java.lang.Object r0 = r0.L$0
            kotlinx.coroutines.sync.Mutex r0 = (kotlinx.coroutines.sync.Mutex) r0
            kotlin.ResultKt.throwOnFailure(r5)
            goto L47
        L2d:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r0)
            throw r5
        L35:
            kotlin.ResultKt.throwOnFailure(r5)
            kotlinx.coroutines.sync.Mutex r5 = r4.mutex
            r0.L$0 = r5
            r0.label = r3
            r2 = 0
            java.lang.Object r0 = kotlinx.coroutines.sync.Mutex.DefaultImpls.lock$default(r5, r2, r0, r3, r2)
            if (r0 != r1) goto L46
            return r1
        L46:
            r0 = r5
        L47:
            androidx.camera.camera2.pipe.core.MutexToken r5 = new androidx.camera.camera2.pipe.core.MutexToken
            r5.<init>(r0)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.internal.GraphSessionLock.acquireToken$camera_camera2_pipe(kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final Deferred withTokenInAsync$camera_camera2_pipe(CoroutineScope scope, Function2 action) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(action, "action");
        return asyncUndispatched(scope, new GraphSessionLock$withTokenInAsync$1(this, action, null));
    }

    private final Deferred asyncUndispatched(CoroutineScope coroutineScope, Function2 function2) {
        final CompletableJob completableJobJob = JobKt.Job((Job) coroutineScope.getCoroutineContext().get(Job.Key));
        Deferred deferredAsync = BuildersKt.async(coroutineScope, coroutineScope.getCoroutineContext().plus(completableJobJob), CoroutineStart.UNDISPATCHED, new GraphSessionLock$asyncUndispatched$result$1(function2, null));
        deferredAsync.invokeOnCompletion(new Function1() { // from class: androidx.camera.camera2.pipe.internal.GraphSessionLock$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return GraphSessionLock.asyncUndispatched$lambda$0(completableJobJob, (Throwable) obj);
            }
        });
        return deferredAsync;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit asyncUndispatched$lambda$0(CompletableJob completableJob, Throwable th) {
        completableJob.complete();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object use(androidx.camera.camera2.pipe.core.Token r5, kotlin.jvm.functions.Function2 r6, kotlin.coroutines.Continuation r7) {
        /*
            r4 = this;
            boolean r0 = r7 instanceof androidx.camera.camera2.pipe.internal.GraphSessionLock.C02451
            if (r0 == 0) goto L13
            r0 = r7
            androidx.camera.camera2.pipe.internal.GraphSessionLock$use$1 r0 = (androidx.camera.camera2.pipe.internal.GraphSessionLock.C02451) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.camera.camera2.pipe.internal.GraphSessionLock$use$1 r0 = new androidx.camera.camera2.pipe.internal.GraphSessionLock$use$1
            r0.<init>(r7)
        L18:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L37
            if (r2 != r3) goto L2f
            java.lang.Object r5 = r0.L$0
            androidx.camera.camera2.pipe.core.Token r5 = (androidx.camera.camera2.pipe.core.Token) r5
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L2d
            goto L45
        L2d:
            r6 = move-exception
            goto L49
        L2f:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L37:
            kotlin.ResultKt.throwOnFailure(r7)
            r0.L$0 = r5     // Catch: java.lang.Throwable -> L2d
            r0.label = r3     // Catch: java.lang.Throwable -> L2d
            java.lang.Object r7 = r6.invoke(r5, r0)     // Catch: java.lang.Throwable -> L2d
            if (r7 != r1) goto L45
            return r1
        L45:
            r5.release()
            return r7
        L49:
            r5.release()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.internal.GraphSessionLock.use(androidx.camera.camera2.pipe.core.Token, kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
