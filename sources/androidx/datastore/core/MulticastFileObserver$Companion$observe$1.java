package androidx.datastore.core;

import java.io.File;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProducerScope;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u0002H\u008a@"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/channels/ProducerScope;"}, m878k = 3, m879mv = {1, 8, 0}, m881xi = 48)
@DebugMetadata(m895c = "androidx.datastore.core.MulticastFileObserver$Companion$observe$1", m896f = "MulticastFileObserver.android.kt", m897i = {0, 0}, m898l = {84, 85}, m899m = "invokeSuspend", m900n = {"$this$channelFlow", "disposeListener"}, m902s = {"L$0", "L$1"})
public final class MulticastFileObserver$Companion$observe$1 extends SuspendLambda implements Function2<ProducerScope<? super Unit>, Continuation<? super Unit>, Object> {
    final /* synthetic */ File $file;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MulticastFileObserver$Companion$observe$1(File file, Continuation<? super MulticastFileObserver$Companion$observe$1> continuation) {
        super(2, continuation);
        this.$file = file;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        MulticastFileObserver$Companion$observe$1 multicastFileObserver$Companion$observe$1 = new MulticastFileObserver$Companion$observe$1(this.$file, continuation);
        multicastFileObserver$Companion$observe$1.L$0 = obj;
        return multicastFileObserver$Companion$observe$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(ProducerScope<? super Unit> producerScope, Continuation<? super Unit> continuation) {
        return ((MulticastFileObserver$Companion$observe$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x005e, code lost:
    
        if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r4, r8, r7) == r0) goto L16;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r7.label
            r2 = 0
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L25
            if (r1 == r4) goto L19
            if (r1 != r3) goto L13
            kotlin.ResultKt.throwOnFailure(r8)
            goto L61
        L13:
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r7)
            return r2
        L19:
            java.lang.Object r1 = r7.L$1
            kotlinx.coroutines.DisposableHandle r1 = (kotlinx.coroutines.DisposableHandle) r1
            java.lang.Object r4 = r7.L$0
            kotlinx.coroutines.channels.ProducerScope r4 = (kotlinx.coroutines.channels.ProducerScope) r4
            kotlin.ResultKt.throwOnFailure(r8)
            goto L4f
        L25:
            kotlin.ResultKt.throwOnFailure(r8)
            java.lang.Object r8 = r7.L$0
            kotlinx.coroutines.channels.ProducerScope r8 = (kotlinx.coroutines.channels.ProducerScope) r8
            androidx.datastore.core.MulticastFileObserver$Companion$observe$1$flowObserver$1 r1 = new androidx.datastore.core.MulticastFileObserver$Companion$observe$1$flowObserver$1
            java.io.File r5 = r7.$file
            r1.<init>()
            androidx.datastore.core.MulticastFileObserver$Companion r5 = androidx.datastore.core.MulticastFileObserver.INSTANCE
            java.io.File r6 = r7.$file
            java.io.File r6 = r6.getParentFile()
            kotlinx.coroutines.DisposableHandle r1 = androidx.datastore.core.MulticastFileObserver.Companion.access$observe(r5, r6, r1)
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            r7.L$0 = r8
            r7.L$1 = r1
            r7.label = r4
            java.lang.Object r4 = r8.send(r5, r7)
            if (r4 != r0) goto L4e
            goto L60
        L4e:
            r4 = r8
        L4f:
            androidx.datastore.core.MulticastFileObserver$Companion$observe$1$1 r8 = new androidx.datastore.core.MulticastFileObserver$Companion$observe$1$1
            r8.<init>()
            r7.L$0 = r2
            r7.L$1 = r2
            r7.label = r3
            java.lang.Object r7 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r4, r8, r7)
            if (r7 != r0) goto L61
        L60:
            return r0
        L61:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.MulticastFileObserver$Companion$observe$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
