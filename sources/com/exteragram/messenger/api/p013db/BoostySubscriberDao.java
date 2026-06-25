package com.exteragram.messenger.api.p013db;

import com.exteragram.messenger.api.dto.BoostySubscriberDTO;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\bg\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H§@¢\u0006\u0002\u0010\u0007J\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H§@¢\u0006\u0002\u0010\tJ\u000e\u0010\n\u001a\u00020\u0003H§@¢\u0006\u0002\u0010\tJ\u001c\u0010\u000b\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0097@¢\u0006\u0002\u0010\u0007¨\u0006\fÀ\u0006\u0003"}, m877d2 = {"Lcom/exteragram/messenger/api/db/BoostySubscriberDao;", _UrlKt.FRAGMENT_ENCODE_SET, "insertAll", _UrlKt.FRAGMENT_ENCODE_SET, "subscribers", _UrlKt.FRAGMENT_ENCODE_SET, "Lcom/exteragram/messenger/api/dto/BoostySubscriberDTO;", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAll", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAll", "replaceSubscribers", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
public interface BoostySubscriberDao {

    /* JADX INFO: renamed from: com.exteragram.messenger.api.db.BoostySubscriberDao$replaceSubscribers$1 */
    @Metadata(m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
    @DebugMetadata(m895c = "com.exteragram.messenger.api.db.BoostySubscriberDao", m896f = "BoostySubscriberDao.kt", m897i = {0, 0, 1, 1}, m898l = {34, 35}, m899m = "replaceSubscribers$suspendImpl", m900n = {"$this", "subscribers", "$this", "subscribers"}, m902s = {"L$0", "L$1", "L$0", "L$1"}, m903v = 1)
    public static final class C10541 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C10541(Continuation<? super C10541> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return BoostySubscriberDao.replaceSubscribers$suspendImpl(BoostySubscriberDao.this, null, this);
        }
    }

    Object deleteAll(Continuation<? super Unit> continuation);

    Object getAll(Continuation<? super List<BoostySubscriberDTO>> continuation);

    Object insertAll(List<BoostySubscriberDTO> list, Continuation<? super Unit> continuation);

    default Object replaceSubscribers(List<BoostySubscriberDTO> list, Continuation<? super Unit> continuation) {
        return replaceSubscribers$suspendImpl(this, list, continuation);
    }

    /* JADX INFO: loaded from: classes4.dex */
    @Metadata(m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
    public static final class DefaultImpls {
        @Deprecated
        public static Object replaceSubscribers(BoostySubscriberDao boostySubscriberDao, List<BoostySubscriberDTO> list, Continuation<? super Unit> continuation) {
            return BoostySubscriberDao.super.replaceSubscribers(list, continuation);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x006a, code lost:
    
        if (r5.insertAll(r6, r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static /* synthetic */ java.lang.Object replaceSubscribers$suspendImpl(com.exteragram.messenger.api.p013db.BoostySubscriberDao r5, java.util.List<com.exteragram.messenger.api.dto.BoostySubscriberDTO> r6, kotlin.coroutines.Continuation<? super kotlin.Unit> r7) {
        /*
            boolean r0 = r7 instanceof com.exteragram.messenger.api.p013db.BoostySubscriberDao.C10541
            if (r0 == 0) goto L13
            r0 = r7
            com.exteragram.messenger.api.db.BoostySubscriberDao$replaceSubscribers$1 r0 = (com.exteragram.messenger.api.p013db.BoostySubscriberDao.C10541) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.exteragram.messenger.api.db.BoostySubscriberDao$replaceSubscribers$1 r0 = new com.exteragram.messenger.api.db.BoostySubscriberDao$replaceSubscribers$1
            r0.<init>(r7)
        L18:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L48
            if (r2 == r4) goto L3b
            if (r2 != r3) goto L34
            java.lang.Object r5 = r0.L$1
            java.util.List r5 = (java.util.List) r5
            java.lang.Object r5 = r0.L$0
            com.exteragram.messenger.api.db.BoostySubscriberDao r5 = (com.exteragram.messenger.api.p013db.BoostySubscriberDao) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L6d
        L34:
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r5)
            r5 = 0
            return r5
        L3b:
            java.lang.Object r5 = r0.L$1
            r6 = r5
            java.util.List r6 = (java.util.List) r6
            java.lang.Object r5 = r0.L$0
            com.exteragram.messenger.api.db.BoostySubscriberDao r5 = (com.exteragram.messenger.api.p013db.BoostySubscriberDao) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L58
        L48:
            kotlin.ResultKt.throwOnFailure(r7)
            r0.L$0 = r5
            r0.L$1 = r6
            r0.label = r4
            java.lang.Object r7 = r5.deleteAll(r0)
            if (r7 != r1) goto L58
            goto L6c
        L58:
            java.lang.Object r7 = kotlin.coroutines.jvm.internal.SpillingKt.nullOutSpilledVariable(r5)
            r0.L$0 = r7
            java.lang.Object r7 = kotlin.coroutines.jvm.internal.SpillingKt.nullOutSpilledVariable(r6)
            r0.L$1 = r7
            r0.label = r3
            java.lang.Object r5 = r5.insertAll(r6, r0)
            if (r5 != r1) goto L6d
        L6c:
            return r1
        L6d:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.api.p013db.BoostySubscriberDao.replaceSubscribers$suspendImpl(com.exteragram.messenger.api.db.BoostySubscriberDao, java.util.List, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
