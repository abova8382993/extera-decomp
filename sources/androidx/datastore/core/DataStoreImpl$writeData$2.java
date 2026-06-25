package androidx.datastore.core;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: Add missing generic type declarations: [T] */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u008a@"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "T", "Landroidx/datastore/core/WriteScope;"}, m878k = 3, m879mv = {1, 8, 0}, m881xi = 48)
@DebugMetadata(m895c = "androidx.datastore.core.DataStoreImpl$writeData$2", m896f = "DataStoreImpl.kt", m897i = {0}, m898l = {352, 353}, m899m = "invokeSuspend", m900n = {"$this$writeScope"}, m902s = {"L$0"})
public final class DataStoreImpl$writeData$2<T> extends SuspendLambda implements Function2<WriteScope<T>, Continuation<? super Unit>, Object> {
    final /* synthetic */ T $newData;
    final /* synthetic */ Ref.IntRef $newVersion;
    final /* synthetic */ boolean $updateCache;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ DataStoreImpl<T> this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataStoreImpl$writeData$2(Ref.IntRef intRef, DataStoreImpl<T> dataStoreImpl, T t, boolean z, Continuation<? super DataStoreImpl$writeData$2> continuation) {
        super(2, continuation);
        this.$newVersion = intRef;
        this.this$0 = dataStoreImpl;
        this.$newData = t;
        this.$updateCache = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        DataStoreImpl$writeData$2 dataStoreImpl$writeData$2 = new DataStoreImpl$writeData$2(this.$newVersion, this.this$0, this.$newData, this.$updateCache, continuation);
        dataStoreImpl$writeData$2.L$0 = obj;
        return dataStoreImpl$writeData$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(WriteScope<T> writeScope, Continuation<? super Unit> continuation) {
        return ((DataStoreImpl$writeData$2) create(writeScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0058, code lost:
    
        if (r4.writeData(r8, r7) == r0) goto L16;
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
            goto L5b
        L13:
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r7)
            return r2
        L19:
            java.lang.Object r1 = r7.L$1
            kotlin.jvm.internal.Ref$IntRef r1 = (kotlin.jvm.internal.Ref.IntRef) r1
            java.lang.Object r4 = r7.L$0
            androidx.datastore.core.WriteScope r4 = (androidx.datastore.core.WriteScope) r4
            kotlin.ResultKt.throwOnFailure(r8)
            goto L44
        L25:
            kotlin.ResultKt.throwOnFailure(r8)
            java.lang.Object r8 = r7.L$0
            androidx.datastore.core.WriteScope r8 = (androidx.datastore.core.WriteScope) r8
            kotlin.jvm.internal.Ref$IntRef r1 = r7.$newVersion
            androidx.datastore.core.DataStoreImpl<T> r5 = r7.this$0
            androidx.datastore.core.InterProcessCoordinator r5 = androidx.datastore.core.DataStoreImpl.access$getCoordinator(r5)
            r7.L$0 = r8
            r7.L$1 = r1
            r7.label = r4
            java.lang.Object r4 = r5.incrementAndGetVersion(r7)
            if (r4 != r0) goto L41
            goto L5a
        L41:
            r6 = r4
            r4 = r8
            r8 = r6
        L44:
            java.lang.Number r8 = (java.lang.Number) r8
            int r8 = r8.intValue()
            r1.element = r8
            T r8 = r7.$newData
            r7.L$0 = r2
            r7.L$1 = r2
            r7.label = r3
            java.lang.Object r8 = r4.writeData(r8, r7)
            if (r8 != r0) goto L5b
        L5a:
            return r0
        L5b:
            boolean r8 = r7.$updateCache
            if (r8 == 0) goto L7b
            androidx.datastore.core.DataStoreImpl<T> r8 = r7.this$0
            androidx.datastore.core.DataStoreInMemoryCache r8 = androidx.datastore.core.DataStoreImpl.access$getInMemoryCache$p(r8)
            androidx.datastore.core.Data r0 = new androidx.datastore.core.Data
            T r1 = r7.$newData
            if (r1 == 0) goto L70
            int r2 = r1.hashCode()
            goto L71
        L70:
            r2 = 0
        L71:
            kotlin.jvm.internal.Ref$IntRef r7 = r7.$newVersion
            int r7 = r7.element
            r0.<init>(r1, r2, r7)
            r8.tryUpdate(r0)
        L7b:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl$writeData$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
