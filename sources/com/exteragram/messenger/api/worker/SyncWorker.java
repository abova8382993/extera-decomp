package com.exteragram.messenger.api.worker;

import android.content.Context;
import androidx.work.CoroutineWorker;
import androidx.work.WorkerParameters;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u000e\u0010\b\u001a\u00020\tH\u0096@¢\u0006\u0002\u0010\n¨\u0006\u000b"}, m877d2 = {"Lcom/exteragram/messenger/api/worker/SyncWorker;", "Landroidx/work/CoroutineWorker;", "appContext", "Landroid/content/Context;", "workerParams", "Landroidx/work/WorkerParameters;", "<init>", "(Landroid/content/Context;Landroidx/work/WorkerParameters;)V", "doWork", "Landroidx/work/ListenableWorker$Result;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
public final class SyncWorker extends CoroutineWorker {

    /* JADX INFO: renamed from: com.exteragram.messenger.api.worker.SyncWorker$doWork$1 */
    /* JADX INFO: loaded from: classes4.dex */
    @Metadata(m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
    @DebugMetadata(m895c = "com.exteragram.messenger.api.worker.SyncWorker", m896f = "SyncWorker.kt", m897i = {}, m898l = {14}, m899m = "doWork", m900n = {}, m902s = {}, m903v = 1)
    public static final class C10671 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C10671(Continuation<? super C10671> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return SyncWorker.this.doWork(this);
        }
    }

    public SyncWorker(Context context, WorkerParameters workerParameters) {
        super(context, workerParameters);
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
            boolean r0 = r8 instanceof com.exteragram.messenger.api.worker.SyncWorker.C10671
            if (r0 == 0) goto L14
            r0 = r8
            com.exteragram.messenger.api.worker.SyncWorker$doWork$1 r0 = (com.exteragram.messenger.api.worker.SyncWorker.C10671) r0
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
            java.lang.Object r7 = r4.result
            java.lang.Object r8 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r0 = r4.label
            r1 = 1
            if (r0 == 0) goto L32
            if (r0 != r1) goto L2b
            kotlin.ResultKt.throwOnFailure(r7)
            goto L45
        L2b:
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r7)
            r7 = 0
            return r7
        L32:
            kotlin.ResultKt.throwOnFailure(r7)
            r7 = r1
            com.exteragram.messenger.api.ApiController r1 = com.exteragram.messenger.api.ApiController.INSTANCE
            r4.label = r7
            r2 = 0
            r3 = 0
            r5 = 3
            r6 = 0
            java.lang.Object r7 = com.exteragram.messenger.api.ApiController.performSync$default(r1, r2, r3, r4, r5, r6)
            if (r7 != r8) goto L45
            return r8
        L45:
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r7 = r7.booleanValue()
            if (r7 == 0) goto L52
            androidx.work.ListenableWorker$Result r7 = androidx.work.ListenableWorker.Result.success()
            return r7
        L52:
            androidx.work.ListenableWorker$Result r7 = androidx.work.ListenableWorker.Result.retry()
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.api.worker.SyncWorker.doWork(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
