package androidx.room;

import androidx.room.ObservedTableStates;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.SourceDebugExtension;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/room/TransactionScope;"}, m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
@DebugMetadata(m895c = "androidx.room.TriggerBasedInvalidationTracker$syncTriggers$2$1$1$1", m896f = "InvalidationTracker.kt", m897i = {0, 0, 1, 1}, m898l = {318, 319}, m899m = "invokeSuspend", m900n = {"$this$forEachIndexed$iv", "index$iv", "$this$forEachIndexed$iv", "index$iv"}, m902s = {"L$0", "I$0", "L$0", "I$0"})
@SourceDebugExtension({"SMAP\nInvalidationTracker.kt\nKotlin\n*S Kotlin\n*F\n+ 1 InvalidationTracker.kt\nandroidx/room/TriggerBasedInvalidationTracker$syncTriggers$2$1$1$1\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,640:1\n13537#2,3:641\n*S KotlinDebug\n*F\n+ 1 InvalidationTracker.kt\nandroidx/room/TriggerBasedInvalidationTracker$syncTriggers$2$1$1$1\n*L\n315#1:641,3\n*E\n"})
public final class TriggerBasedInvalidationTracker$syncTriggers$2$1$1$1 extends SuspendLambda implements Function2<TransactionScope<Unit>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Transactor $connection;
    final /* synthetic */ ObservedTableStates.ObserveOp[] $tablesToSync;
    int I$0;
    int I$1;
    int I$2;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ TriggerBasedInvalidationTracker this$0;

    @Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ObservedTableStates.ObserveOp.values().length];
            try {
                iArr[ObservedTableStates.ObserveOp.NO_OP.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ObservedTableStates.ObserveOp.ADD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ObservedTableStates.ObserveOp.REMOVE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TriggerBasedInvalidationTracker$syncTriggers$2$1$1$1(ObservedTableStates.ObserveOp[] observeOpArr, TriggerBasedInvalidationTracker triggerBasedInvalidationTracker, Transactor transactor, Continuation<? super TriggerBasedInvalidationTracker$syncTriggers$2$1$1$1> continuation) {
        super(2, continuation);
        this.$tablesToSync = observeOpArr;
        this.this$0 = triggerBasedInvalidationTracker;
        this.$connection = transactor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new TriggerBasedInvalidationTracker$syncTriggers$2$1$1$1(this.$tablesToSync, this.this$0, this.$connection, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(TransactionScope<Unit> transactionScope, Continuation<? super Unit> continuation) {
        return ((TriggerBasedInvalidationTracker$syncTriggers$2$1$1$1) create(transactionScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0080, code lost:
    
        if (r8.startTrackingTable(r13, r7, r12) == r0) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0067, code lost:
    
        r7 = r13;
        r6 = r10;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0086  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x0083 -> B:27:0x0084). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r13) {
        /*
            r12 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r12.label
            r2 = 0
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L2b
            if (r1 == r4) goto Lf
            if (r1 != r3) goto L25
        Lf:
            int r1 = r12.I$2
            int r5 = r12.I$1
            int r6 = r12.I$0
            java.lang.Object r7 = r12.L$2
            androidx.room.Transactor r7 = (androidx.room.Transactor) r7
            java.lang.Object r8 = r12.L$1
            androidx.room.TriggerBasedInvalidationTracker r8 = (androidx.room.TriggerBasedInvalidationTracker) r8
            java.lang.Object r9 = r12.L$0
            androidx.room.ObservedTableStates$ObserveOp[] r9 = (androidx.room.ObservedTableStates.ObserveOp[]) r9
            kotlin.ResultKt.throwOnFailure(r13)
            goto L67
        L25:
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r12)
            return r2
        L2b:
            kotlin.ResultKt.throwOnFailure(r13)
            androidx.room.ObservedTableStates$ObserveOp[] r13 = r12.$tablesToSync
            androidx.room.TriggerBasedInvalidationTracker r1 = r12.this$0
            androidx.room.Transactor r5 = r12.$connection
            int r6 = r13.length
            r7 = 0
            r9 = r13
            r8 = r1
            r13 = r5
            r1 = r6
            r5 = r7
        L3b:
            if (r5 >= r1) goto L86
            r6 = r9[r5]
            int r10 = r7 + 1
            int[] r11 = androidx.room.TriggerBasedInvalidationTracker$syncTriggers$2$1$1$1.WhenMappings.$EnumSwitchMapping$0
            int r6 = r6.ordinal()
            r6 = r11[r6]
            if (r6 == r4) goto L83
            if (r6 == r3) goto L6e
            r11 = 3
            if (r6 != r11) goto L6a
            r12.L$0 = r9
            r12.L$1 = r8
            r12.L$2 = r13
            r12.I$0 = r10
            r12.I$1 = r5
            r12.I$2 = r1
            r12.label = r3
            java.lang.Object r6 = androidx.room.TriggerBasedInvalidationTracker.access$stopTrackingTable(r8, r13, r7, r12)
            if (r6 != r0) goto L65
            goto L82
        L65:
            r7 = r13
            r6 = r10
        L67:
            r13 = r7
            r7 = r6
            goto L84
        L6a:
            kotlin.LazyKt__LazyJVMKt$$ExternalSyntheticBUOutline0.m874m()
            return r2
        L6e:
            r12.L$0 = r9
            r12.L$1 = r8
            r12.L$2 = r13
            r12.I$0 = r10
            r12.I$1 = r5
            r12.I$2 = r1
            r12.label = r4
            java.lang.Object r6 = androidx.room.TriggerBasedInvalidationTracker.access$startTrackingTable(r8, r13, r7, r12)
            if (r6 != r0) goto L65
        L82:
            return r0
        L83:
            r7 = r10
        L84:
            int r5 = r5 + r4
            goto L3b
        L86:
            kotlin.Unit r12 = kotlin.Unit.INSTANCE
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.TriggerBasedInvalidationTracker$syncTriggers$2$1$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
