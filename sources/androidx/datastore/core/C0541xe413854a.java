package androidx.datastore.core;

import androidx.datastore.core.MultiProcessCoordinator;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* JADX INFO: renamed from: androidx.datastore.core.MultiProcessCoordinator$Companion$getExclusiveFileLockWithRetryIfDeadlock$1 */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m878k = 3, m879mv = {1, 8, 0}, m881xi = 48)
@DebugMetadata(m895c = "androidx.datastore.core.MultiProcessCoordinator$Companion", m896f = "MultiProcessCoordinator.android.kt", m897i = {0, 0}, m898l = {182}, m899m = "getExclusiveFileLockWithRetryIfDeadlock", m900n = {"lockFileStream", "backoff"}, m902s = {"L$0", "J$0"})
public final class C0541xe413854a extends ContinuationImpl {
    long J$0;
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ MultiProcessCoordinator.Companion this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0541xe413854a(MultiProcessCoordinator.Companion companion, Continuation<? super C0541xe413854a> continuation) {
        super(continuation);
        this.this$0 = companion;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.getExclusiveFileLockWithRetryIfDeadlock(null, this);
    }
}
