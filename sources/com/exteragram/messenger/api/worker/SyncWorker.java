package com.exteragram.messenger.api.worker;

import android.content.Context;
import androidx.work.CoroutineWorker;
import androidx.work.WorkerParameters;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: loaded from: classes.dex */
public final class SyncWorker extends CoroutineWorker {

    /* JADX INFO: renamed from: com.exteragram.messenger.api.worker.SyncWorker$doWork$1 */
    /* JADX INFO: loaded from: classes4.dex */
    @DebugMetadata(m1083c = "com.exteragram.messenger.api.worker.SyncWorker", m1084f = "SyncWorker.kt", m1085l = {14}, m1086m = "doWork", m1087v = 1)
    static final class C10531 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C10531(Continuation<? super C10531> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return SyncWorker.this.doWork(this);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SyncWorker(Context appContext, WorkerParameters workerParams) {
        super(appContext, workerParams);
        Intrinsics.checkNotNullParameter(appContext, "appContext");
        Intrinsics.checkNotNullParameter(workerParams, "workerParams");
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
    @Override // androidx.work.CoroutineWorker
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object doWork(kotlin.coroutines.Continuation<? super androidx.work.ListenableWorker.Result> r8) {
        /*
            r7 = this;
            boolean r0 = r8 instanceof com.exteragram.messenger.api.worker.SyncWorker.C10531
            if (r0 == 0) goto L14
            r0 = r8
            com.exteragram.messenger.api.worker.SyncWorker$doWork$1 r0 = (com.exteragram.messenger.api.worker.SyncWorker.C10531) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L14
            int r1 = r1 - r2
            r0.label = r1
        L12:
            r4 = r0
            goto L1a
        L14:
            com.exteragram.messenger.api.worker.SyncWorker$doWork$1 r0 = new com.exteragram.messenger.api.worker.SyncWorker$doWork$1
            r0.<init>(r8)
            goto L12
        L1a:
            java.lang.Object r8 = r4.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r4.label
            r2 = 1
            if (r1 == 0) goto L33
            if (r1 != r2) goto L2b
            kotlin.ResultKt.throwOnFailure(r8)
            goto L45
        L2b:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r0)
            throw r8
        L33:
            kotlin.ResultKt.throwOnFailure(r8)
            com.exteragram.messenger.api.ApiController r1 = com.exteragram.messenger.api.ApiController.INSTANCE
            r4.label = r2
            r2 = 0
            r3 = 0
            r5 = 3
            r6 = 0
            java.lang.Object r8 = com.exteragram.messenger.api.ApiController.performSync$default(r1, r2, r3, r4, r5, r6)
            if (r8 != r0) goto L45
            return r0
        L45:
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L55
            androidx.work.ListenableWorker$Result r8 = androidx.work.ListenableWorker.Result.success()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r8)
            return r8
        L55:
            androidx.work.ListenableWorker$Result r8 = androidx.work.ListenableWorker.Result.retry()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r8)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.api.worker.SyncWorker.doWork(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
