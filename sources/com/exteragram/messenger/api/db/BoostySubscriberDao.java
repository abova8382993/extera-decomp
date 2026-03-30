package com.exteragram.messenger.api.db;

import com.exteragram.messenger.api.dto.BoostySubscriberDTO;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import org.mvel2.Operator;

/* JADX INFO: loaded from: classes4.dex */
public interface BoostySubscriberDao {

    /* JADX INFO: renamed from: com.exteragram.messenger.api.db.BoostySubscriberDao$replaceSubscribers$1, reason: invalid class name */
    @DebugMetadata(c = "com.exteragram.messenger.api.db.BoostySubscriberDao", f = "BoostySubscriberDao.kt", l = {34, Operator.PROJECTION}, m = "replaceSubscribers$suspendImpl", v = 1)
    static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CC.replaceSubscribers$suspendImpl(BoostySubscriberDao.this, null, this);
        }
    }

    Object deleteAll(Continuation<? super Unit> continuation);

    Object getAll(Continuation<? super List<BoostySubscriberDTO>> continuation);

    Object insertAll(List<BoostySubscriberDTO> list, Continuation<? super Unit> continuation);

    Object replaceSubscribers(List<BoostySubscriberDTO> list, Continuation<? super Unit> continuation);

    /* JADX INFO: renamed from: com.exteragram.messenger.api.db.BoostySubscriberDao$-CC, reason: invalid class name */
    public abstract /* synthetic */ class CC {
        /* JADX WARN: Code restructure failed: missing block: B:20:0x006b, code lost:
        
            if (r5.insertAll(r6, r0) == r1) goto L21;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static /* synthetic */ java.lang.Object replaceSubscribers$suspendImpl(com.exteragram.messenger.api.db.BoostySubscriberDao r5, java.util.List r6, kotlin.coroutines.Continuation r7) {
            /*
                boolean r0 = r7 instanceof com.exteragram.messenger.api.db.BoostySubscriberDao.AnonymousClass1
                if (r0 == 0) goto L13
                r0 = r7
                com.exteragram.messenger.api.db.BoostySubscriberDao$replaceSubscribers$1 r0 = (com.exteragram.messenger.api.db.BoostySubscriberDao.AnonymousClass1) r0
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
                if (r2 == 0) goto L49
                if (r2 == r4) goto L3c
                if (r2 != r3) goto L34
                java.lang.Object r5 = r0.L$1
                java.util.List r5 = (java.util.List) r5
                java.lang.Object r5 = r0.L$0
                com.exteragram.messenger.api.db.BoostySubscriberDao r5 = (com.exteragram.messenger.api.db.BoostySubscriberDao) r5
                kotlin.ResultKt.throwOnFailure(r7)
                goto L6e
            L34:
                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                r5.<init>(r6)
                throw r5
            L3c:
                java.lang.Object r5 = r0.L$1
                r6 = r5
                java.util.List r6 = (java.util.List) r6
                java.lang.Object r5 = r0.L$0
                com.exteragram.messenger.api.db.BoostySubscriberDao r5 = (com.exteragram.messenger.api.db.BoostySubscriberDao) r5
                kotlin.ResultKt.throwOnFailure(r7)
                goto L59
            L49:
                kotlin.ResultKt.throwOnFailure(r7)
                r0.L$0 = r5
                r0.L$1 = r6
                r0.label = r4
                java.lang.Object r7 = r5.deleteAll(r0)
                if (r7 != r1) goto L59
                goto L6d
            L59:
                java.lang.Object r7 = kotlin.coroutines.jvm.internal.SpillingKt.nullOutSpilledVariable(r5)
                r0.L$0 = r7
                java.lang.Object r7 = kotlin.coroutines.jvm.internal.SpillingKt.nullOutSpilledVariable(r6)
                r0.L$1 = r7
                r0.label = r3
                java.lang.Object r5 = r5.insertAll(r6, r0)
                if (r5 != r1) goto L6e
            L6d:
                return r1
            L6e:
                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.api.db.BoostySubscriberDao.CC.replaceSubscribers$suspendImpl(com.exteragram.messenger.api.db.BoostySubscriberDao, java.util.List, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    public static final class DefaultImpls {
        @Deprecated
        public static Object replaceSubscribers(BoostySubscriberDao boostySubscriberDao, List<BoostySubscriberDTO> list, Continuation<? super Unit> continuation) {
            return CC.replaceSubscribers$suspendImpl(boostySubscriberDao, list, continuation);
        }
    }
}
