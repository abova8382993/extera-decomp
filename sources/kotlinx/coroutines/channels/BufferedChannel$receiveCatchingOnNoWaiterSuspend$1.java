package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* JADX INFO: loaded from: classes5.dex */
@Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
@DebugMetadata(m895c = "kotlinx.coroutines.channels.BufferedChannel", m896f = "BufferedChannel.kt", m897i = {0, 0, 0, 0}, m898l = {3117}, m899m = "receiveCatchingOnNoWaiterSuspend-GKJJFZk", m900n = {"this", "segment", "index", "r"}, m902s = {"L$0", "L$1", "I$0", "J$0"})
public final class BufferedChannel$receiveCatchingOnNoWaiterSuspend$1 extends ContinuationImpl {
    int I$0;
    long J$0;
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ BufferedChannel<E> this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BufferedChannel$receiveCatchingOnNoWaiterSuspend$1(BufferedChannel<E> bufferedChannel, Continuation<? super BufferedChannel$receiveCatchingOnNoWaiterSuspend$1> continuation) {
        super(continuation);
        this.this$0 = bufferedChannel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        Object objM5007receiveCatchingOnNoWaiterSuspendGKJJFZk = this.this$0.m5007receiveCatchingOnNoWaiterSuspendGKJJFZk(null, 0, 0L, this);
        return objM5007receiveCatchingOnNoWaiterSuspendGKJJFZk == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objM5007receiveCatchingOnNoWaiterSuspendGKJJFZk : ChannelResult.m5013boximpl(objM5007receiveCatchingOnNoWaiterSuspendGKJJFZk);
    }
}
