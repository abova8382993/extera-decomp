package androidx.camera.camera2.pipe.internal;

import androidx.camera.camera2.pipe.core.Token;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CompletableJob;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexKt;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0001\u0018\u00002\u00020\u0001B\t\b\u0007¢\u0006\u0004\b\u0002\u0010\u0003JL\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000b\"\u0004\b\u0000\u0010\u00042\u0006\u0010\u0006\u001a\u00020\u00052'\u0010\n\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\b\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0007¢\u0006\u0002\b\tH\u0002¢\u0006\u0004\b\f\u0010\rJ>\u0010\u000f\u001a\u00028\u0000\"\u0004\b\u0000\u0010\u0004*\u00020\u000e2\"\u0010\n\u001a\u001e\b\u0001\u0012\u0004\u0012\u00020\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\b\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0007H\u0082@¢\u0006\u0004\b\u000f\u0010\u0010J\u0010\u0010\u0013\u001a\u00020\u000eH\u0080@¢\u0006\u0004\b\u0011\u0010\u0012J\\\u0010\u0019\u001a\b\u0012\u0004\u0012\u00028\u00000\u000b\"\u0004\b\u0000\u0010\u00042\u0006\u0010\u0006\u001a\u00020\u000527\u0010\u0017\u001a3\b\u0001\u0012\u0013\u0012\u00110\u000e¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u000b0\b\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0007H\u0000¢\u0006\u0004\b\u0018\u0010\rR\u0014\u0010\u001b\u001a\u00020\u001a8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u001b\u0010\u001c¨\u0006\u001d"}, m877d2 = {"Landroidx/camera/camera2/pipe/internal/GraphSessionLock;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "T", "Lkotlinx/coroutines/CoroutineScope;", "scope", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "Lkotlin/ExtensionFunctionType;", "block", "Lkotlinx/coroutines/Deferred;", "asyncUndispatched", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/Deferred;", "Landroidx/camera/camera2/pipe/core/Token;", "use", "(Landroidx/camera/camera2/pipe/core/Token;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "acquireToken$camera_camera2_pipe", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "acquireToken", "Lkotlin/ParameterName;", "name", "token", "action", "withTokenInAsync$camera_camera2_pipe", "withTokenInAsync", "Lkotlinx/coroutines/sync/Mutex;", "mutex", "Lkotlinx/coroutines/sync/Mutex;", "camera-camera2-pipe"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nGraphSessionLock.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GraphSessionLock.kt\nandroidx/camera/camera2/pipe/internal/GraphSessionLock\n+ 2 Mutexes.kt\nandroidx/camera/camera2/pipe/core/MutexesKt\n*L\n1#1,104:1\n98#2,2:105\n*S KotlinDebug\n*F\n+ 1 GraphSessionLock.kt\nandroidx/camera/camera2/pipe/internal/GraphSessionLock\n*L\n43#1:105,2\n*E\n"})
public final class GraphSessionLock {
    private final Mutex mutex = MutexKt.Mutex$default(false, 1, null);

    /* JADX INFO: renamed from: androidx.camera.camera2.pipe.internal.GraphSessionLock$use$1 */
    @Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.pipe.internal.GraphSessionLock", m896f = "GraphSessionLock.kt", m897i = {0}, m898l = {98}, m899m = "use", m900n = {"$this$use"}, m902s = {"L$0"}, m903v = 1)
    public static final class C02431<T> extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C02431(Continuation<? super C02431> continuation) {
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
    public final java.lang.Object acquireToken$camera_camera2_pipe(kotlin.coroutines.Continuation<? super androidx.camera.camera2.pipe.core.Token> r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof androidx.camera.camera2.pipe.internal.GraphSessionLock$acquireToken$1
            if (r0 == 0) goto L13
            r0 = r6
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
            r0.<init>(r5, r6)
        L18:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L34
            if (r2 != r4) goto L2e
            java.lang.Object r5 = r0.L$0
            kotlinx.coroutines.sync.Mutex r5 = (kotlinx.coroutines.sync.Mutex) r5
            kotlin.ResultKt.throwOnFailure(r6)
            goto L44
        L2e:
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r5)
            return r3
        L34:
            kotlin.ResultKt.throwOnFailure(r6)
            kotlinx.coroutines.sync.Mutex r5 = r5.mutex
            r0.L$0 = r5
            r0.label = r4
            java.lang.Object r6 = kotlinx.coroutines.sync.Mutex.DefaultImpls.lock$default(r5, r3, r0, r4, r3)
            if (r6 != r1) goto L44
            return r1
        L44:
            androidx.camera.camera2.pipe.core.MutexToken r6 = new androidx.camera.camera2.pipe.core.MutexToken
            r6.<init>(r5)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.internal.GraphSessionLock.acquireToken$camera_camera2_pipe(kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final <T> Deferred<T> withTokenInAsync$camera_camera2_pipe(CoroutineScope scope, Function2<? super Token, ? super Continuation<? super Deferred<? extends T>>, ? extends Object> action) {
        return asyncUndispatched(scope, new GraphSessionLock$withTokenInAsync$1(this, action, null));
    }

    private final <T> Deferred<T> asyncUndispatched(CoroutineScope scope, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block) {
        final CompletableJob completableJobJob = JobKt.Job((Job) scope.getCoroutineContext().get(Job.INSTANCE));
        Deferred<T> deferredAsync = BuildersKt.async(scope, scope.getCoroutineContext().plus(completableJobJob), CoroutineStart.UNDISPATCHED, new GraphSessionLock$asyncUndispatched$result$1(block, null));
        deferredAsync.invokeOnCompletion(new Function1() { // from class: androidx.camera.camera2.pipe.internal.GraphSessionLock$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return GraphSessionLock.m1831$r8$lambda$Rh7Chtw743aTwAubUmFZvrz3kQ(completableJobJob, (Throwable) obj);
            }
        });
        return deferredAsync;
    }

    /* JADX INFO: renamed from: $r8$lambda$Rh7Chtw743aTwAubUmFZvrz-3kQ, reason: not valid java name */
    public static Unit m1831$r8$lambda$Rh7Chtw743aTwAubUmFZvrz3kQ(CompletableJob completableJob, Throwable th) {
        completableJob.complete();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final <T> java.lang.Object use(androidx.camera.camera2.pipe.core.Token r5, kotlin.jvm.functions.Function2<? super androidx.camera.camera2.pipe.core.Token, ? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> r6, kotlin.coroutines.Continuation<? super T> r7) {
        /*
            r4 = this;
            boolean r0 = r7 instanceof androidx.camera.camera2.pipe.internal.GraphSessionLock.C02431
            if (r0 == 0) goto L13
            r0 = r7
            androidx.camera.camera2.pipe.internal.GraphSessionLock$use$1 r0 = (androidx.camera.camera2.pipe.internal.GraphSessionLock.C02431) r0
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
            java.lang.Object r4 = r0.result
            java.lang.Object r7 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r0.label
            r2 = 1
            if (r1 == 0) goto L36
            if (r1 != r2) goto L2f
            java.lang.Object r5 = r0.L$0
            androidx.camera.camera2.pipe.core.Token r5 = (androidx.camera.camera2.pipe.core.Token) r5
            kotlin.ResultKt.throwOnFailure(r4)     // Catch: java.lang.Throwable -> L2d
            goto L44
        L2d:
            r4 = move-exception
            goto L48
        L2f:
            java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r4)
            r4 = 0
            return r4
        L36:
            kotlin.ResultKt.throwOnFailure(r4)
            r0.L$0 = r5     // Catch: java.lang.Throwable -> L2d
            r0.label = r2     // Catch: java.lang.Throwable -> L2d
            java.lang.Object r4 = r6.invoke(r5, r0)     // Catch: java.lang.Throwable -> L2d
            if (r4 != r7) goto L44
            return r7
        L44:
            r5.release()
            return r4
        L48:
            r5.release()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.internal.GraphSessionLock.use(androidx.camera.camera2.pipe.core.Token, kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
