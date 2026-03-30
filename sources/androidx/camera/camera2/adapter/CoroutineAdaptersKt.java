package androidx.camera.camera2.adapter;

import androidx.arch.core.util.Function;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureChain;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.CancellationException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Job;

/* JADX INFO: loaded from: classes3.dex */
public abstract class CoroutineAdaptersKt {

    /* JADX INFO: renamed from: androidx.camera.camera2.adapter.CoroutineAdaptersKt$awaitUntil$1 */
    static final class C01091 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C01091(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CoroutineAdaptersKt.awaitUntil(null, 0L, this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Void asVoidListenableFuture$lambda$0(Object obj) {
        return null;
    }

    public static /* synthetic */ ListenableFuture asListenableFuture$default(Job job, Object obj, int i, Object obj2) {
        if ((i & 1) != 0) {
            obj = "Job.asListenableFuture";
        }
        return asListenableFuture(job, obj);
    }

    public static final ListenableFuture asListenableFuture(final Job job, final Object obj) {
        Intrinsics.checkNotNullParameter(job, "<this>");
        ListenableFuture future = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.adapter.CoroutineAdaptersKt$$ExternalSyntheticLambda0
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return CoroutineAdaptersKt.asListenableFuture$lambda$0(job, obj, completer);
            }
        });
        Intrinsics.checkNotNullExpressionValue(future, "getFuture(...)");
        return future;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Object asListenableFuture$lambda$0(Job job, Object obj, final CallbackToFutureAdapter.Completer completer) {
        Intrinsics.checkNotNullParameter(completer, "completer");
        job.invokeOnCompletion(new Function1() { // from class: androidx.camera.camera2.adapter.CoroutineAdaptersKt$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return CoroutineAdaptersKt.asListenableFuture$lambda$0$0(completer, (Throwable) obj2);
            }
        });
        return obj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit asListenableFuture$lambda$0$0(CallbackToFutureAdapter.Completer completer, Throwable th) {
        if (th != null) {
            if (th instanceof CancellationException) {
                completer.setCancelled();
            } else {
                completer.setException(th);
            }
        } else {
            completer.set(null);
        }
        return Unit.INSTANCE;
    }

    public static /* synthetic */ ListenableFuture asListenableFuture$default(Deferred deferred, Object obj, int i, Object obj2) {
        if ((i & 1) != 0) {
            obj = "Deferred.asListenableFuture";
        }
        return asListenableFuture(deferred, obj);
    }

    public static final ListenableFuture asListenableFuture(final Deferred deferred, final Object obj) {
        Intrinsics.checkNotNullParameter(deferred, "<this>");
        ListenableFuture future = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.adapter.CoroutineAdaptersKt$$ExternalSyntheticLambda4
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return CoroutineAdaptersKt.asListenableFuture$lambda$1(deferred, obj, completer);
            }
        });
        Intrinsics.checkNotNullExpressionValue(future, "getFuture(...)");
        return future;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Object asListenableFuture$lambda$1(final Deferred deferred, Object obj, final CallbackToFutureAdapter.Completer completer) {
        Intrinsics.checkNotNullParameter(completer, "completer");
        deferred.invokeOnCompletion(new Function1() { // from class: androidx.camera.camera2.adapter.CoroutineAdaptersKt$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return CoroutineAdaptersKt.asListenableFuture$lambda$1$0(completer, deferred, (Throwable) obj2);
            }
        });
        return obj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit asListenableFuture$lambda$1$0(CallbackToFutureAdapter.Completer completer, Deferred deferred, Throwable th) {
        if (th != null) {
            if (th instanceof CancellationException) {
                completer.setCancelled();
            } else {
                completer.setException(th);
            }
        } else {
            completer.set(deferred.getCompleted());
        }
        return Unit.INSTANCE;
    }

    public static final ListenableFuture asVoidListenableFuture(Deferred deferred) {
        Intrinsics.checkNotNullParameter(deferred, "<this>");
        FutureChain futureChainTransform = FutureChain.from(asListenableFuture$default(deferred, (Object) null, 1, (Object) null)).transform(new Function() { // from class: androidx.camera.camera2.adapter.CoroutineAdaptersKt$$ExternalSyntheticLambda1
            @Override // androidx.arch.core.util.Function
            public final Object apply(Object obj) {
                return CoroutineAdaptersKt.asVoidListenableFuture$lambda$0(obj);
            }
        }, CameraXExecutors.directExecutor());
        Intrinsics.checkNotNullExpressionValue(futureChainTransform, "transform(...)");
        return futureChainTransform;
    }

    public static final void propagateTo(final Deferred deferred, final CompletableDeferred destination) {
        Intrinsics.checkNotNullParameter(deferred, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        deferred.invokeOnCompletion(new Function1() { // from class: androidx.camera.camera2.adapter.CoroutineAdaptersKt$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return CoroutineAdaptersKt.propagateTo$lambda$0(deferred, destination, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit propagateTo$lambda$0(Deferred deferred, CompletableDeferred completableDeferred, Throwable th) {
        propagateCompletion(deferred, completableDeferred, th);
        return Unit.INSTANCE;
    }

    public static final void propagateTo(final Deferred deferred, final CompletableDeferred destination, final Function1 transform) {
        Intrinsics.checkNotNullParameter(deferred, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(transform, "transform");
        deferred.invokeOnCompletion(new Function1() { // from class: androidx.camera.camera2.adapter.CoroutineAdaptersKt$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return CoroutineAdaptersKt.propagateTo$lambda$1(deferred, destination, transform, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit propagateTo$lambda$1(Deferred deferred, CompletableDeferred completableDeferred, Function1 function1, Throwable th) {
        propagateCompletion(deferred, completableDeferred, th, function1);
        return Unit.INSTANCE;
    }

    public static final void propagateCompletion(Deferred deferred, CompletableDeferred destination, Throwable th) {
        Intrinsics.checkNotNullParameter(deferred, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        if (th != null) {
            completeFailing(destination, th);
        } else {
            destination.complete(deferred.getCompleted());
        }
    }

    public static final void propagateCompletion(Deferred deferred, CompletableDeferred destination, Throwable th, Function1 transform) {
        Intrinsics.checkNotNullParameter(deferred, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(transform, "transform");
        if (th != null) {
            completeFailing(destination, th);
        } else {
            destination.complete(transform.invoke(deferred.getCompleted()));
        }
    }

    public static final void completeFailing(CompletableDeferred completableDeferred, Throwable cause) {
        Intrinsics.checkNotNullParameter(completableDeferred, "<this>");
        Intrinsics.checkNotNullParameter(cause, "cause");
        if (cause instanceof CancellationException) {
            completableDeferred.cancel((CancellationException) cause);
        } else {
            completableDeferred.completeExceptionally(cause);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.adapter.CoroutineAdaptersKt$awaitUntil$2 */
    static final class C01102 extends SuspendLambda implements Function2 {
        final /* synthetic */ Deferred $this_awaitUntil;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01102(Deferred deferred, Continuation continuation) {
            super(2, continuation);
            this.$this_awaitUntil = deferred;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C01102(this.$this_awaitUntil, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01102) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            ResultKt.throwOnFailure(obj);
            Deferred deferred = this.$this_awaitUntil;
            this.label = 1;
            Object objAwait = deferred.await(this);
            return objAwait == coroutine_suspended ? coroutine_suspended : objAwait;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object awaitUntil(kotlinx.coroutines.Deferred r4, long r5, kotlin.coroutines.Continuation r7) {
        /*
            boolean r0 = r7 instanceof androidx.camera.camera2.adapter.CoroutineAdaptersKt.C01091
            if (r0 == 0) goto L13
            r0 = r7
            androidx.camera.camera2.adapter.CoroutineAdaptersKt$awaitUntil$1 r0 = (androidx.camera.camera2.adapter.CoroutineAdaptersKt.C01091) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.camera.camera2.adapter.CoroutineAdaptersKt$awaitUntil$1 r0 = new androidx.camera.camera2.adapter.CoroutineAdaptersKt$awaitUntil$1
            r0.<init>(r7)
        L18:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L31
            if (r2 != r3) goto L29
            kotlin.ResultKt.throwOnFailure(r7)
            goto L43
        L29:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L31:
            kotlin.ResultKt.throwOnFailure(r7)
            androidx.camera.camera2.adapter.CoroutineAdaptersKt$awaitUntil$2 r7 = new androidx.camera.camera2.adapter.CoroutineAdaptersKt$awaitUntil$2
            r2 = 0
            r7.<init>(r4, r2)
            r0.label = r3
            java.lang.Object r7 = kotlinx.coroutines.TimeoutKt.withTimeoutOrNull(r5, r7, r0)
            if (r7 != r1) goto L43
            return r1
        L43:
            if (r7 == 0) goto L46
            goto L47
        L46:
            r3 = 0
        L47:
            java.lang.Boolean r4 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r3)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.adapter.CoroutineAdaptersKt.awaitUntil(kotlinx.coroutines.Deferred, long, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
