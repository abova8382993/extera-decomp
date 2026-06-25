package androidx.room.coroutines;

import androidx.room.TransactionScope;
import androidx.room.Transactor;
import androidx.room.concurrent.ThreadLocal_jvmAndroidKt;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import kotlin.Metadata;
import kotlin.collections.ArrayDeque;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.time.InstantKt$$ExternalSyntheticBUOutline0;
import okhttp3.internal.url._UrlKt;
import org.scilab.forge.jlatexmath.TeXSymbolParser;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002:\u000389:B\u001f\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0004\b\t\u0010\nJO\u0010\u0014\u001a\u00028\u0000\"\u0004\b\u0000\u0010\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\f2-\u0010\u0013\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000e¢\u0006\u0002\b\u0012H\u0082@¢\u0006\u0004\b\u0014\u0010\u0015J\u0018\u0010\u0017\u001a\u00020\u00162\u0006\u0010\r\u001a\u00020\fH\u0082@¢\u0006\u0004\b\u0017\u0010\u0018J\u0018\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u0007H\u0082@¢\u0006\u0004\b\u001a\u0010\u001bJ2\u0010 \u001a\u00028\u0000\"\u0004\b\u0000\u0010\u000b2\u0006\u0010\u001d\u001a\u00020\u001c2\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00028\u00000\u001eH\u0096@¢\u0006\u0004\b \u0010!JM\u0010\"\u001a\u00028\u0000\"\u0004\b\u0000\u0010\u000b2\u0006\u0010\r\u001a\u00020\f2-\u0010\u0013\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000e¢\u0006\u0002\b\u0012H\u0096@¢\u0006\u0004\b\"\u0010\u0015J\u0010\u0010#\u001a\u00020\u0007H\u0096@¢\u0006\u0004\b#\u0010$J\r\u0010%\u001a\u00020\u0016¢\u0006\u0004\b%\u0010&R\u0017\u0010\u0004\u001a\u00020\u00038\u0006¢\u0006\f\n\u0004\b\u0004\u0010'\u001a\u0004\b(\u0010)R\u0017\u0010\u0006\u001a\u00020\u00058\u0006¢\u0006\f\n\u0004\b\u0006\u0010*\u001a\u0004\b+\u0010,R\u0017\u0010\b\u001a\u00020\u00078\u0006¢\u0006\f\n\u0004\b\b\u0010-\u001a\u0004\b\b\u0010.R\u001a\u00101\u001a\b\u0012\u0004\u0012\u0002000/8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b1\u00102R\u0016\u00103\u001a\u00020\u00078\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b3\u0010-R\u0014\u00107\u001a\u0002048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b5\u00106¨\u0006;"}, m877d2 = {"Landroidx/room/coroutines/PooledConnectionImpl;", "Landroidx/room/Transactor;", "Landroidx/room/coroutines/RawConnectionAccessor;", "Landroidx/room/coroutines/ConnectionElementKey;", "connectionElementKey", "Landroidx/room/coroutines/ConnectionWithLock;", "delegate", _UrlKt.FRAGMENT_ENCODE_SET, "isReadOnly", "<init>", "(Landroidx/room/coroutines/ConnectionElementKey;Landroidx/room/coroutines/ConnectionWithLock;Z)V", "R", "Landroidx/room/Transactor$SQLiteTransactionType;", TeXSymbolParser.TYPE_ATTR, "Lkotlin/Function2;", "Landroidx/room/TransactionScope;", "Lkotlin/coroutines/Continuation;", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlin/ExtensionFunctionType;", "block", "transaction", "(Landroidx/room/Transactor$SQLiteTransactionType;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", _UrlKt.FRAGMENT_ENCODE_SET, "beginTransaction", "(Landroidx/room/Transactor$SQLiteTransactionType;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "success", "endTransaction", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", _UrlKt.FRAGMENT_ENCODE_SET, "sql", "Lkotlin/Function1;", "Landroidx/sqlite/SQLiteStatement;", "usePrepared", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "withTransaction", "inTransaction", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markRecycled", "()V", "Landroidx/room/coroutines/ConnectionElementKey;", "getConnectionElementKey", "()Landroidx/room/coroutines/ConnectionElementKey;", "Landroidx/room/coroutines/ConnectionWithLock;", "getDelegate", "()Landroidx/room/coroutines/ConnectionWithLock;", "Z", "()Z", "Lkotlin/collections/ArrayDeque;", "Landroidx/room/coroutines/PooledConnectionImpl$TransactionItem;", "transactionStack", "Lkotlin/collections/ArrayDeque;", "isRecycled", "Landroidx/sqlite/SQLiteConnection;", "getRawConnection", "()Landroidx/sqlite/SQLiteConnection;", "rawConnection", "TransactionItem", "TransactionImpl", "StatementWrapper", "room-runtime"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nConnectionPoolImpl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ConnectionPoolImpl.kt\nandroidx/room/coroutines/PooledConnectionImpl\n+ 2 Mutex.kt\nkotlinx/coroutines/sync/MutexKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,624:1\n551#1,11:625\n551#1,11:646\n551#1,11:657\n116#2,7:636\n124#2,2:644\n116#2,10:668\n116#2,10:678\n1#3:643\n*S KotlinDebug\n*F\n+ 1 ConnectionPoolImpl.kt\nandroidx/room/coroutines/PooledConnectionImpl\n*L\n434#1:625,11\n443#1:646,11\n445#1:657,11\n435#1:636,7\n435#1:644,2\n490#1:668,10\n507#1:678,10\n*E\n"})
final class PooledConnectionImpl implements Transactor, RawConnectionAccessor {
    private final ConnectionElementKey connectionElementKey;
    private final ConnectionWithLock delegate;
    private final boolean isReadOnly;
    private volatile boolean isRecycled;
    private final ArrayDeque<TransactionItem> transactionStack = new ArrayDeque<>();

    @Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Transactor.SQLiteTransactionType.values().length];
            try {
                iArr[Transactor.SQLiteTransactionType.DEFERRED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Transactor.SQLiteTransactionType.IMMEDIATE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Transactor.SQLiteTransactionType.EXCLUSIVE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: renamed from: androidx.room.coroutines.PooledConnectionImpl$beginTransaction$1 */
    @Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.room.coroutines.PooledConnectionImpl", m896f = "ConnectionPoolImpl.kt", m897i = {0, 0}, m898l = {629}, m899m = "beginTransaction", m900n = {TeXSymbolParser.TYPE_ATTR, "$this$withLock_u24default$iv"}, m902s = {"L$0", "L$1"})
    public static final class C07951 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C07951(Continuation<? super C07951> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PooledConnectionImpl.this.beginTransaction(null, this);
        }
    }

    /* JADX INFO: renamed from: androidx.room.coroutines.PooledConnectionImpl$endTransaction$1 */
    @Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.room.coroutines.PooledConnectionImpl", m896f = "ConnectionPoolImpl.kt", m897i = {0, 0}, m898l = {629}, m899m = "endTransaction", m900n = {"$this$withLock_u24default$iv", "success"}, m902s = {"L$0", "Z$0"})
    public static final class C07961 extends ContinuationImpl {
        Object L$0;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        public C07961(Continuation<? super C07961> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PooledConnectionImpl.this.endTransaction(false, this);
        }
    }

    /* JADX INFO: renamed from: androidx.room.coroutines.PooledConnectionImpl$transaction$1 */
    @Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.room.coroutines.PooledConnectionImpl", m896f = "ConnectionPoolImpl.kt", m897i = {0, 1, 4}, m898l = {464, 468, 482, 482, 482}, m899m = "transaction", m900n = {"block", "success", "exception"}, m902s = {"L$0", "I$0", "L$0"})
    public static final class C07971<R> extends ContinuationImpl {
        int I$0;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C07971(Continuation<? super C07971> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PooledConnectionImpl.this.transaction(null, null, this);
        }
    }

    /* JADX INFO: renamed from: androidx.room.coroutines.PooledConnectionImpl$usePrepared$1 */
    @Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.room.coroutines.PooledConnectionImpl", m896f = "ConnectionPoolImpl.kt", m897i = {0, 0, 0}, m898l = {640}, m899m = "usePrepared", m900n = {"sql", "block", "$this$withLock_u24default$iv"}, m902s = {"L$0", "L$1", "L$2"})
    public static final class C07981<R> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public C07981(Continuation<? super C07981> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PooledConnectionImpl.this.usePrepared(null, null, this);
        }
    }

    public PooledConnectionImpl(ConnectionElementKey connectionElementKey, ConnectionWithLock connectionWithLock, boolean z) {
        this.connectionElementKey = connectionElementKey;
        this.delegate = connectionWithLock;
        this.isReadOnly = z;
    }

    public final ConnectionElementKey getConnectionElementKey() {
        return this.connectionElementKey;
    }

    public final ConnectionWithLock getDelegate() {
        return this.delegate;
    }

    /* JADX INFO: renamed from: isReadOnly, reason: from getter */
    public final boolean getIsReadOnly() {
        return this.isReadOnly;
    }

    @Override // androidx.room.coroutines.RawConnectionAccessor
    public SQLiteConnection getRawConnection() {
        return this.delegate;
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x0013  */
    @Override // androidx.room.PooledConnection
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public <R> java.lang.Object usePrepared(java.lang.String r7, kotlin.jvm.functions.Function1<? super androidx.sqlite.SQLiteStatement, ? extends R> r8, kotlin.coroutines.Continuation<? super R> r9) {
        /*
            r6 = this;
            boolean r0 = r9 instanceof androidx.room.coroutines.PooledConnectionImpl.C07981
            if (r0 == 0) goto L13
            r0 = r9
            androidx.room.coroutines.PooledConnectionImpl$usePrepared$1 r0 = (androidx.room.coroutines.PooledConnectionImpl.C07981) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.room.coroutines.PooledConnectionImpl$usePrepared$1 r0 = new androidx.room.coroutines.PooledConnectionImpl$usePrepared$1
            r0.<init>(r9)
        L18:
            java.lang.Object r9 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L3e
            if (r2 != r3) goto L38
            java.lang.Object r7 = r0.L$2
            kotlinx.coroutines.sync.Mutex r7 = (kotlinx.coroutines.sync.Mutex) r7
            java.lang.Object r8 = r0.L$1
            kotlin.jvm.functions.Function1 r8 = (kotlin.jvm.functions.Function1) r8
            java.lang.Object r0 = r0.L$0
            java.lang.String r0 = (java.lang.String) r0
            kotlin.ResultKt.throwOnFailure(r9)
            r9 = r7
            r7 = r0
            goto L70
        L38:
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r6)
            return r4
        L3e:
            kotlin.ResultKt.throwOnFailure(r9)
            boolean r9 = access$isRecycled$p(r6)
            r2 = 21
            if (r9 != 0) goto L9c
            kotlin.coroutines.CoroutineContext r9 = r0.getContext()
            androidx.room.coroutines.ConnectionElementKey r5 = r6.getConnectionElementKey()
            kotlin.coroutines.CoroutineContext$Element r9 = r9.get(r5)
            androidx.room.coroutines.ConnectionElement r9 = (androidx.room.coroutines.ConnectionElement) r9
            if (r9 == 0) goto L93
            androidx.room.coroutines.PooledConnectionImpl r9 = r9.getConnectionWrapper()
            if (r9 != r6) goto L93
            androidx.room.coroutines.ConnectionWithLock r9 = r6.delegate
            r0.L$0 = r7
            r0.L$1 = r8
            r0.L$2 = r9
            r0.label = r3
            java.lang.Object r0 = r9.lock(r4, r0)
            if (r0 != r1) goto L70
            return r1
        L70:
            androidx.room.coroutines.PooledConnectionImpl$StatementWrapper r0 = new androidx.room.coroutines.PooledConnectionImpl$StatementWrapper     // Catch: java.lang.Throwable -> L86
            androidx.room.coroutines.ConnectionWithLock r1 = r6.delegate     // Catch: java.lang.Throwable -> L86
            androidx.sqlite.SQLiteStatement r7 = r1.prepare(r7)     // Catch: java.lang.Throwable -> L86
            r0.<init>(r7)     // Catch: java.lang.Throwable -> L86
            java.lang.Object r6 = r8.invoke(r0)     // Catch: java.lang.Throwable -> L88
            kotlin.jdk7.AutoCloseableKt.closeFinally(r0, r4)     // Catch: java.lang.Throwable -> L86
            r9.unlock(r4)
            return r6
        L86:
            r6 = move-exception
            goto L8f
        L88:
            r6 = move-exception
            throw r6     // Catch: java.lang.Throwable -> L8a
        L8a:
            r7 = move-exception
            kotlin.jdk7.AutoCloseableKt.closeFinally(r0, r6)     // Catch: java.lang.Throwable -> L86
            throw r7     // Catch: java.lang.Throwable -> L86
        L8f:
            r9.unlock(r4)
            throw r6
        L93:
            java.lang.String r6 = "Attempted to use connection on a different coroutine"
            androidx.sqlite.SQLite.throwSQLiteException(r2, r6)
            kotlin.time.InstantKt$$ExternalSyntheticBUOutline0.m948m()
            return r4
        L9c:
            java.lang.String r6 = "Connection is recycled"
            androidx.sqlite.SQLite.throwSQLiteException(r2, r6)
            kotlin.time.InstantKt$$ExternalSyntheticBUOutline0.m948m()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.coroutines.PooledConnectionImpl.usePrepared(java.lang.String, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void markRecycled() {
        if (this.isRecycled) {
            return;
        }
        this.isRecycled = true;
        if (this.delegate.inTransaction()) {
            SQLite.execSQL(this.delegate, "ROLLBACK TRANSACTION");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:111:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0093 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:125:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final <R> java.lang.Object transaction(androidx.room.Transactor.SQLiteTransactionType r11, kotlin.jvm.functions.Function2<? super androidx.room.TransactionScope<R>, ? super kotlin.coroutines.Continuation<? super R>, ? extends java.lang.Object> r12, kotlin.coroutines.Continuation<? super R> r13) throws java.lang.Throwable {
        /*
            r10 = this;
            boolean r0 = r13 instanceof androidx.room.coroutines.PooledConnectionImpl.C07971
            if (r0 == 0) goto L13
            r0 = r13
            androidx.room.coroutines.PooledConnectionImpl$transaction$1 r0 = (androidx.room.coroutines.PooledConnectionImpl.C07971) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.room.coroutines.PooledConnectionImpl$transaction$1 r0 = new androidx.room.coroutines.PooledConnectionImpl$transaction$1
            r0.<init>(r13)
        L18:
            java.lang.Object r13 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            r4 = 0
            r5 = 5
            r6 = 3
            r7 = 2
            r8 = 1
            if (r2 == 0) goto L60
            if (r2 == r8) goto L57
            if (r2 == r7) goto L4f
            if (r2 == r6) goto L49
            r10 = 4
            if (r2 == r10) goto L49
            if (r2 == r5) goto L39
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r10)
            return r3
        L39:
            java.lang.Object r10 = r0.L$1
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            java.lang.Object r11 = r0.L$0
            java.lang.Throwable r11 = (java.lang.Throwable) r11
            kotlin.ResultKt.throwOnFailure(r13)     // Catch: android.database.SQLException -> L46
            goto Lae
        L46:
            r12 = move-exception
            goto La9
        L49:
            java.lang.Object r10 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r13)
            return r10
        L4f:
            int r11 = r0.I$0
            kotlin.ResultKt.throwOnFailure(r13)     // Catch: java.lang.Throwable -> L55
            goto L85
        L55:
            r11 = move-exception
            goto L94
        L57:
            java.lang.Object r11 = r0.L$0
            r12 = r11
            kotlin.jvm.functions.Function2 r12 = (kotlin.jvm.functions.Function2) r12
            kotlin.ResultKt.throwOnFailure(r13)
            goto L72
        L60:
            kotlin.ResultKt.throwOnFailure(r13)
            if (r11 != 0) goto L67
            androidx.room.Transactor$SQLiteTransactionType r11 = androidx.room.Transactor.SQLiteTransactionType.DEFERRED
        L67:
            r0.L$0 = r12
            r0.label = r8
            java.lang.Object r11 = r10.beginTransaction(r11, r0)
            if (r11 != r1) goto L72
            goto La2
        L72:
            androidx.room.coroutines.PooledConnectionImpl$TransactionImpl r11 = new androidx.room.coroutines.PooledConnectionImpl$TransactionImpl     // Catch: java.lang.Throwable -> L55
            r11.<init>()     // Catch: java.lang.Throwable -> L55
            r0.L$0 = r3     // Catch: java.lang.Throwable -> L55
            r0.I$0 = r8     // Catch: java.lang.Throwable -> L55
            r0.label = r7     // Catch: java.lang.Throwable -> L55
            java.lang.Object r13 = r12.invoke(r11, r0)     // Catch: java.lang.Throwable -> L55
            if (r13 != r1) goto L84
            goto La2
        L84:
            r11 = r8
        L85:
            if (r11 == 0) goto L88
            r4 = r8
        L88:
            r0.L$0 = r13
            r0.label = r6
            java.lang.Object r10 = r10.endTransaction(r4, r0)
            if (r10 != r1) goto L93
            goto La2
        L93:
            return r13
        L94:
            throw r11     // Catch: java.lang.Throwable -> L95
        L95:
            r12 = move-exception
            r0.L$0 = r11     // Catch: android.database.SQLException -> La5
            r0.L$1 = r12     // Catch: android.database.SQLException -> La5
            r0.label = r5     // Catch: android.database.SQLException -> La5
            java.lang.Object r10 = r10.endTransaction(r4, r0)     // Catch: android.database.SQLException -> La5
            if (r10 != r1) goto La3
        La2:
            return r1
        La3:
            r10 = r12
            goto Lae
        La5:
            r10 = move-exception
            r9 = r12
            r12 = r10
            r10 = r9
        La9:
            if (r11 == 0) goto Laf
            kotlin.ExceptionsKt.addSuppressed(r11, r12)
        Lae:
            throw r10
        Laf:
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.coroutines.PooledConnectionImpl.transaction(androidx.room.Transactor$SQLiteTransactionType, kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x0015  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object beginTransaction(androidx.room.Transactor.SQLiteTransactionType r7, kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
        /*
            r6 = this;
            java.lang.String r0 = "SAVEPOINT '"
            boolean r1 = r8 instanceof androidx.room.coroutines.PooledConnectionImpl.C07951
            if (r1 == 0) goto L15
            r1 = r8
            androidx.room.coroutines.PooledConnectionImpl$beginTransaction$1 r1 = (androidx.room.coroutines.PooledConnectionImpl.C07951) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L15
            int r2 = r2 - r3
            r1.label = r2
            goto L1a
        L15:
            androidx.room.coroutines.PooledConnectionImpl$beginTransaction$1 r1 = new androidx.room.coroutines.PooledConnectionImpl$beginTransaction$1
            r1.<init>(r8)
        L1a:
            java.lang.Object r8 = r1.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r1.label
            r4 = 1
            r5 = 0
            if (r3 == 0) goto L3c
            if (r3 != r4) goto L36
            java.lang.Object r7 = r1.L$1
            kotlinx.coroutines.sync.Mutex r7 = (kotlinx.coroutines.sync.Mutex) r7
            java.lang.Object r1 = r1.L$0
            androidx.room.Transactor$SQLiteTransactionType r1 = (androidx.room.Transactor.SQLiteTransactionType) r1
            kotlin.ResultKt.throwOnFailure(r8)
            r8 = r7
            r7 = r1
            goto L4e
        L36:
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r6)
            return r5
        L3c:
            kotlin.ResultKt.throwOnFailure(r8)
            androidx.room.coroutines.ConnectionWithLock r8 = r6.delegate
            r1.L$0 = r7
            r1.L$1 = r8
            r1.label = r4
            java.lang.Object r1 = r8.lock(r5, r1)
            if (r1 != r2) goto L4e
            return r2
        L4e:
            kotlin.collections.ArrayDeque<androidx.room.coroutines.PooledConnectionImpl$TransactionItem> r1 = r6.transactionStack     // Catch: java.lang.Throwable -> L74
            int r1 = r1.size()     // Catch: java.lang.Throwable -> L74
            kotlin.collections.ArrayDeque<androidx.room.coroutines.PooledConnectionImpl$TransactionItem> r2 = r6.transactionStack     // Catch: java.lang.Throwable -> L74
            boolean r2 = r2.isEmpty()     // Catch: java.lang.Throwable -> L74
            if (r2 == 0) goto L8c
            int[] r0 = androidx.room.coroutines.PooledConnectionImpl.WhenMappings.$EnumSwitchMapping$0     // Catch: java.lang.Throwable -> L74
            int r7 = r7.ordinal()     // Catch: java.lang.Throwable -> L74
            r7 = r0[r7]     // Catch: java.lang.Throwable -> L74
            if (r7 == r4) goto L84
            r0 = 2
            if (r7 == r0) goto L7c
            r0 = 3
            if (r7 != r0) goto L76
            androidx.room.coroutines.ConnectionWithLock r7 = r6.delegate     // Catch: java.lang.Throwable -> L74
            java.lang.String r0 = "BEGIN EXCLUSIVE TRANSACTION"
            androidx.sqlite.SQLite.execSQL(r7, r0)     // Catch: java.lang.Throwable -> L74
            goto La2
        L74:
            r6 = move-exception
            goto Lb3
        L76:
            kotlin.NoWhenBranchMatchedException r6 = new kotlin.NoWhenBranchMatchedException     // Catch: java.lang.Throwable -> L74
            r6.<init>()     // Catch: java.lang.Throwable -> L74
            throw r6     // Catch: java.lang.Throwable -> L74
        L7c:
            androidx.room.coroutines.ConnectionWithLock r7 = r6.delegate     // Catch: java.lang.Throwable -> L74
            java.lang.String r0 = "BEGIN IMMEDIATE TRANSACTION"
            androidx.sqlite.SQLite.execSQL(r7, r0)     // Catch: java.lang.Throwable -> L74
            goto La2
        L84:
            androidx.room.coroutines.ConnectionWithLock r7 = r6.delegate     // Catch: java.lang.Throwable -> L74
            java.lang.String r0 = "BEGIN DEFERRED TRANSACTION"
            androidx.sqlite.SQLite.execSQL(r7, r0)     // Catch: java.lang.Throwable -> L74
            goto La2
        L8c:
            androidx.room.coroutines.ConnectionWithLock r7 = r6.delegate     // Catch: java.lang.Throwable -> L74
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L74
            r2.<init>(r0)     // Catch: java.lang.Throwable -> L74
            r2.append(r1)     // Catch: java.lang.Throwable -> L74
            r0 = 39
            r2.append(r0)     // Catch: java.lang.Throwable -> L74
            java.lang.String r0 = r2.toString()     // Catch: java.lang.Throwable -> L74
            androidx.sqlite.SQLite.execSQL(r7, r0)     // Catch: java.lang.Throwable -> L74
        La2:
            kotlin.collections.ArrayDeque<androidx.room.coroutines.PooledConnectionImpl$TransactionItem> r6 = r6.transactionStack     // Catch: java.lang.Throwable -> L74
            androidx.room.coroutines.PooledConnectionImpl$TransactionItem r7 = new androidx.room.coroutines.PooledConnectionImpl$TransactionItem     // Catch: java.lang.Throwable -> L74
            r0 = 0
            r7.<init>(r1, r0)     // Catch: java.lang.Throwable -> L74
            r6.addLast(r7)     // Catch: java.lang.Throwable -> L74
            kotlin.Unit r6 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L74
            r8.unlock(r5)
            return r6
        Lb3:
            r8.unlock(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.coroutines.PooledConnectionImpl.beginTransaction(androidx.room.Transactor$SQLiteTransactionType, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x0017  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object endTransaction(boolean r8, kotlin.coroutines.Continuation<? super kotlin.Unit> r9) {
        /*
            r7 = this;
            java.lang.String r0 = "ROLLBACK TRANSACTION TO SAVEPOINT '"
            java.lang.String r1 = "RELEASE SAVEPOINT '"
            boolean r2 = r9 instanceof androidx.room.coroutines.PooledConnectionImpl.C07961
            if (r2 == 0) goto L17
            r2 = r9
            androidx.room.coroutines.PooledConnectionImpl$endTransaction$1 r2 = (androidx.room.coroutines.PooledConnectionImpl.C07961) r2
            int r3 = r2.label
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            r5 = r3 & r4
            if (r5 == 0) goto L17
            int r3 = r3 - r4
            r2.label = r3
            goto L1c
        L17:
            androidx.room.coroutines.PooledConnectionImpl$endTransaction$1 r2 = new androidx.room.coroutines.PooledConnectionImpl$endTransaction$1
            r2.<init>(r9)
        L1c:
            java.lang.Object r9 = r2.result
            java.lang.Object r3 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 1
            r6 = 0
            if (r4 == 0) goto L3a
            if (r4 != r5) goto L34
            boolean r8 = r2.Z$0
            java.lang.Object r2 = r2.L$0
            kotlinx.coroutines.sync.Mutex r2 = (kotlinx.coroutines.sync.Mutex) r2
            kotlin.ResultKt.throwOnFailure(r9)
            goto L4d
        L34:
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r7)
            return r6
        L3a:
            kotlin.ResultKt.throwOnFailure(r9)
            androidx.room.coroutines.ConnectionWithLock r9 = r7.delegate
            r2.L$0 = r9
            r2.Z$0 = r8
            r2.label = r5
            java.lang.Object r2 = r9.lock(r6, r2)
            if (r2 != r3) goto L4c
            return r3
        L4c:
            r2 = r9
        L4d:
            kotlin.collections.ArrayDeque<androidx.room.coroutines.PooledConnectionImpl$TransactionItem> r9 = r7.transactionStack     // Catch: java.lang.Throwable -> L77
            boolean r9 = r9.isEmpty()     // Catch: java.lang.Throwable -> L77
            if (r9 != 0) goto Lbc
            kotlin.collections.ArrayDeque<androidx.room.coroutines.PooledConnectionImpl$TransactionItem> r9 = r7.transactionStack     // Catch: java.lang.Throwable -> L77
            java.lang.Object r9 = kotlin.collections.CollectionsKt.removeLast(r9)     // Catch: java.lang.Throwable -> L77
            androidx.room.coroutines.PooledConnectionImpl$TransactionItem r9 = (androidx.room.coroutines.PooledConnectionImpl.TransactionItem) r9     // Catch: java.lang.Throwable -> L77
            r3 = 39
            if (r8 == 0) goto L90
            boolean r8 = r9.getShouldRollback()     // Catch: java.lang.Throwable -> L77
            if (r8 != 0) goto L90
            kotlin.collections.ArrayDeque<androidx.room.coroutines.PooledConnectionImpl$TransactionItem> r8 = r7.transactionStack     // Catch: java.lang.Throwable -> L77
            boolean r8 = r8.isEmpty()     // Catch: java.lang.Throwable -> L77
            androidx.room.coroutines.ConnectionWithLock r7 = r7.delegate
            if (r8 == 0) goto L79
            java.lang.String r8 = "END TRANSACTION"
            androidx.sqlite.SQLite.execSQL(r7, r8)     // Catch: java.lang.Throwable -> L77
            goto Lb6
        L77:
            r7 = move-exception
            goto Lc4
        L79:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L77
            r8.<init>(r1)     // Catch: java.lang.Throwable -> L77
            int r9 = r9.getId()     // Catch: java.lang.Throwable -> L77
            r8.append(r9)     // Catch: java.lang.Throwable -> L77
            r8.append(r3)     // Catch: java.lang.Throwable -> L77
            java.lang.String r8 = r8.toString()     // Catch: java.lang.Throwable -> L77
            androidx.sqlite.SQLite.execSQL(r7, r8)     // Catch: java.lang.Throwable -> L77
            goto Lb6
        L90:
            kotlin.collections.ArrayDeque<androidx.room.coroutines.PooledConnectionImpl$TransactionItem> r8 = r7.transactionStack     // Catch: java.lang.Throwable -> L77
            boolean r8 = r8.isEmpty()     // Catch: java.lang.Throwable -> L77
            androidx.room.coroutines.ConnectionWithLock r7 = r7.delegate
            if (r8 == 0) goto La0
            java.lang.String r8 = "ROLLBACK TRANSACTION"
            androidx.sqlite.SQLite.execSQL(r7, r8)     // Catch: java.lang.Throwable -> L77
            goto Lb6
        La0:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L77
            r8.<init>(r0)     // Catch: java.lang.Throwable -> L77
            int r9 = r9.getId()     // Catch: java.lang.Throwable -> L77
            r8.append(r9)     // Catch: java.lang.Throwable -> L77
            r8.append(r3)     // Catch: java.lang.Throwable -> L77
            java.lang.String r8 = r8.toString()     // Catch: java.lang.Throwable -> L77
            androidx.sqlite.SQLite.execSQL(r7, r8)     // Catch: java.lang.Throwable -> L77
        Lb6:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L77
            r2.unlock(r6)
            return r7
        Lbc:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L77
            java.lang.String r8 = "Not in a transaction"
            r7.<init>(r8)     // Catch: java.lang.Throwable -> L77
            throw r7     // Catch: java.lang.Throwable -> L77
        Lc4:
            r2.unlock(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.coroutines.PooledConnectionImpl.endTransaction(boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Metadata(m876d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0006\u0010\u0007R\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n\u0004\b\u0003\u0010\b\u001a\u0004\b\t\u0010\nR\"\u0010\u0005\u001a\u00020\u00048\u0006@\u0006X\u0086\u000e¢\u0006\u0012\n\u0004\b\u0005\u0010\u000b\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, m877d2 = {"Landroidx/room/coroutines/PooledConnectionImpl$TransactionItem;", _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, "id", _UrlKt.FRAGMENT_ENCODE_SET, "shouldRollback", "<init>", "(IZ)V", "I", "getId", "()I", "Z", "getShouldRollback", "()Z", "setShouldRollback", "(Z)V", "room-runtime"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    public static final class TransactionItem {
        private final int id;
        private boolean shouldRollback;

        public TransactionItem(int i, boolean z) {
            this.id = i;
            this.shouldRollback = z;
        }

        public final int getId() {
            return this.id;
        }

        public final boolean getShouldRollback() {
            return this.shouldRollback;
        }
    }

    @Metadata(m876d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0082\u0004\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u00022\u00020\u0003B\u0007¢\u0006\u0004\b\u0004\u0010\u0005J2\u0010\f\u001a\u00028\u0001\"\u0004\b\u0001\u0010\u00062\u0006\u0010\b\u001a\u00020\u00072\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00028\u00010\tH\u0096@¢\u0006\u0004\b\f\u0010\rR\u0014\u0010\u0011\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0012"}, m877d2 = {"Landroidx/room/coroutines/PooledConnectionImpl$TransactionImpl;", "T", "Landroidx/room/TransactionScope;", "Landroidx/room/coroutines/RawConnectionAccessor;", "<init>", "(Landroidx/room/coroutines/PooledConnectionImpl;)V", "R", _UrlKt.FRAGMENT_ENCODE_SET, "sql", "Lkotlin/Function1;", "Landroidx/sqlite/SQLiteStatement;", "block", "usePrepared", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Landroidx/sqlite/SQLiteConnection;", "getRawConnection", "()Landroidx/sqlite/SQLiteConnection;", "rawConnection", "room-runtime"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    @SourceDebugExtension({"SMAP\nConnectionPoolImpl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ConnectionPoolImpl.kt\nandroidx/room/coroutines/PooledConnectionImpl$TransactionImpl\n+ 2 ConnectionPoolImpl.kt\nandroidx/room/coroutines/PooledConnectionImpl\n+ 3 Mutex.kt\nkotlinx/coroutines/sync/MutexKt\n*L\n1#1,624:1\n551#2,11:625\n551#2,11:636\n116#3,10:647\n*S KotlinDebug\n*F\n+ 1 ConnectionPoolImpl.kt\nandroidx/room/coroutines/PooledConnectionImpl$TransactionImpl\n*L\n539#1:625,11\n541#1:636,11\n545#1:647,10\n*E\n"})
    public final class TransactionImpl<T> implements TransactionScope<T>, RawConnectionAccessor {
        public TransactionImpl() {
        }

        @Override // androidx.room.coroutines.RawConnectionAccessor
        public SQLiteConnection getRawConnection() {
            return PooledConnectionImpl.this.getRawConnection();
        }

        @Override // androidx.room.PooledConnection
        public <R> Object usePrepared(String str, Function1<? super SQLiteStatement, ? extends R> function1, Continuation<? super R> continuation) {
            return PooledConnectionImpl.this.usePrepared(str, function1, continuation);
        }
    }

    @Override // androidx.room.Transactor
    public Object inTransaction(Continuation<? super Boolean> continuation) {
        if (this.isRecycled) {
            SQLite.throwSQLiteException(21, "Connection is recycled");
            InstantKt$$ExternalSyntheticBUOutline0.m948m();
            return null;
        }
        ConnectionElement connectionElement = (ConnectionElement) continuation.getContext().get(getConnectionElementKey());
        if (connectionElement != null && connectionElement.getConnectionWrapper() == this) {
            return Boxing.boxBoolean(!this.transactionStack.isEmpty() || this.delegate.inTransaction());
        }
        SQLite.throwSQLiteException(21, "Attempted to use connection on a different coroutine");
        InstantKt$$ExternalSyntheticBUOutline0.m948m();
        return null;
    }

    @Override // androidx.room.Transactor
    public <R> Object withTransaction(Transactor.SQLiteTransactionType sQLiteTransactionType, Function2<? super TransactionScope<R>, ? super Continuation<? super R>, ? extends Object> function2, Continuation<? super R> continuation) {
        if (this.isRecycled) {
            SQLite.throwSQLiteException(21, "Connection is recycled");
            InstantKt$$ExternalSyntheticBUOutline0.m948m();
            return null;
        }
        ConnectionElement connectionElement = (ConnectionElement) continuation.getContext().get(getConnectionElementKey());
        if (connectionElement != null && connectionElement.getConnectionWrapper() == this) {
            return transaction(sQLiteTransactionType, function2, continuation);
        }
        SQLite.throwSQLiteException(21, "Attempted to use connection on a different coroutine");
        InstantKt$$ExternalSyntheticBUOutline0.m948m();
        return null;
    }

    @Metadata(m876d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u000f\b\u0082\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0004\b\u0003\u0010\u0004J\u001f\u0010\n\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016¢\u0006\u0004\b\n\u0010\u000bJ\u001f\u0010\r\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\fH\u0016¢\u0006\u0004\b\r\u0010\u000eJ\u001f\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u000fH\u0016¢\u0006\u0004\b\u0010\u0010\u0011J\u0017\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u0005H\u0016¢\u0006\u0004\b\u0012\u0010\u0013J\u0017\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0005H\u0016¢\u0006\u0004\b\u0014\u0010\u0015J\u0017\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0006\u001a\u00020\u0005H\u0016¢\u0006\u0004\b\u0016\u0010\u0017J\u0017\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0006\u001a\u00020\u0005H\u0016¢\u0006\u0004\b\u0018\u0010\u0019J\u0017\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u0006\u001a\u00020\u0005H\u0016¢\u0006\u0004\b\u001b\u0010\u001cJ\u000f\u0010\u001d\u001a\u00020\u0005H\u0016¢\u0006\u0004\b\u001d\u0010\u001eJ\u0017\u0010\u001f\u001a\u00020\u000f2\u0006\u0010\u0006\u001a\u00020\u0005H\u0016¢\u0006\u0004\b\u001f\u0010\u0019J\u000f\u0010 \u001a\u00020\u001aH\u0016¢\u0006\u0004\b \u0010!J\u000f\u0010\"\u001a\u00020\tH\u0016¢\u0006\u0004\b\"\u0010#J\u000f\u0010$\u001a\u00020\tH\u0016¢\u0006\u0004\b$\u0010#J\u000f\u0010%\u001a\u00020\tH\u0016¢\u0006\u0004\b%\u0010#R\u0014\u0010\u0002\u001a\u00020\u00018\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0002\u0010&R\u0014\u0010'\u001a\u00020\f8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b'\u0010(¨\u0006)"}, m877d2 = {"Landroidx/room/coroutines/PooledConnectionImpl$StatementWrapper;", "Landroidx/sqlite/SQLiteStatement;", "delegate", "<init>", "(Landroidx/room/coroutines/PooledConnectionImpl;Landroidx/sqlite/SQLiteStatement;)V", _UrlKt.FRAGMENT_ENCODE_SET, "index", _UrlKt.FRAGMENT_ENCODE_SET, "value", _UrlKt.FRAGMENT_ENCODE_SET, "bindBlob", "(I[B)V", _UrlKt.FRAGMENT_ENCODE_SET, "bindLong", "(IJ)V", _UrlKt.FRAGMENT_ENCODE_SET, "bindText", "(ILjava/lang/String;)V", "bindNull", "(I)V", "getBlob", "(I)[B", "getLong", "(I)J", "getText", "(I)Ljava/lang/String;", _UrlKt.FRAGMENT_ENCODE_SET, "isNull", "(I)Z", "getColumnCount", "()I", "getColumnName", "step", "()Z", "reset", "()V", "clearBindings", "close", "Landroidx/sqlite/SQLiteStatement;", "threadId", "J", "room-runtime"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    @SourceDebugExtension({"SMAP\nConnectionPoolImpl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ConnectionPoolImpl.kt\nandroidx/room/coroutines/PooledConnectionImpl$StatementWrapper\n*L\n1#1,624:1\n611#1,10:625\n611#1,10:635\n611#1,10:645\n611#1,10:655\n611#1,10:665\n611#1,10:675\n611#1,10:685\n611#1,10:695\n611#1,10:705\n611#1,10:715\n611#1,10:725\n611#1,10:735\n611#1,10:745\n611#1,10:755\n611#1,10:765\n611#1,10:775\n611#1,10:785\n*S KotlinDebug\n*F\n+ 1 ConnectionPoolImpl.kt\nandroidx/room/coroutines/PooledConnectionImpl$StatementWrapper\n*L\n568#1:625,10\n572#1:635,10\n576#1:645,10\n580#1:655,10\n584#1:665,10\n586#1:675,10\n588#1:685,10\n590#1:695,10\n592#1:705,10\n594#1:715,10\n596#1:725,10\n598#1:735,10\n600#1:745,10\n602#1:755,10\n604#1:765,10\n606#1:775,10\n608#1:785,10\n*E\n"})
    public final class StatementWrapper implements SQLiteStatement {
        private final SQLiteStatement delegate;
        private final long threadId = ThreadLocal_jvmAndroidKt.currentThreadId();

        public StatementWrapper(SQLiteStatement sQLiteStatement) {
            this.delegate = sQLiteStatement;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public void bindBlob(int index, byte[] value) {
            if (PooledConnectionImpl.this.isRecycled) {
                SQLite.throwSQLiteException(21, "Statement is recycled");
                InstantKt$$ExternalSyntheticBUOutline0.m948m();
            } else if (this.threadId == ThreadLocal_jvmAndroidKt.currentThreadId()) {
                this.delegate.bindBlob(index, value);
            } else {
                SQLite.throwSQLiteException(21, "Attempted to use statement on a different thread");
                InstantKt$$ExternalSyntheticBUOutline0.m948m();
            }
        }

        @Override // androidx.sqlite.SQLiteStatement
        public void bindLong(int index, long value) {
            if (PooledConnectionImpl.this.isRecycled) {
                SQLite.throwSQLiteException(21, "Statement is recycled");
                InstantKt$$ExternalSyntheticBUOutline0.m948m();
            } else if (this.threadId == ThreadLocal_jvmAndroidKt.currentThreadId()) {
                this.delegate.bindLong(index, value);
            } else {
                SQLite.throwSQLiteException(21, "Attempted to use statement on a different thread");
                InstantKt$$ExternalSyntheticBUOutline0.m948m();
            }
        }

        @Override // androidx.sqlite.SQLiteStatement
        public void bindNull(int index) {
            if (PooledConnectionImpl.this.isRecycled) {
                SQLite.throwSQLiteException(21, "Statement is recycled");
                InstantKt$$ExternalSyntheticBUOutline0.m948m();
            } else if (this.threadId == ThreadLocal_jvmAndroidKt.currentThreadId()) {
                this.delegate.bindNull(index);
            } else {
                SQLite.throwSQLiteException(21, "Attempted to use statement on a different thread");
                InstantKt$$ExternalSyntheticBUOutline0.m948m();
            }
        }

        @Override // androidx.sqlite.SQLiteStatement
        public void bindText(int index, String value) {
            if (PooledConnectionImpl.this.isRecycled) {
                SQLite.throwSQLiteException(21, "Statement is recycled");
                InstantKt$$ExternalSyntheticBUOutline0.m948m();
            } else if (this.threadId == ThreadLocal_jvmAndroidKt.currentThreadId()) {
                this.delegate.bindText(index, value);
            } else {
                SQLite.throwSQLiteException(21, "Attempted to use statement on a different thread");
                InstantKt$$ExternalSyntheticBUOutline0.m948m();
            }
        }

        @Override // androidx.sqlite.SQLiteStatement
        public void clearBindings() {
            if (PooledConnectionImpl.this.isRecycled) {
                SQLite.throwSQLiteException(21, "Statement is recycled");
                InstantKt$$ExternalSyntheticBUOutline0.m948m();
            } else if (this.threadId == ThreadLocal_jvmAndroidKt.currentThreadId()) {
                this.delegate.clearBindings();
            } else {
                SQLite.throwSQLiteException(21, "Attempted to use statement on a different thread");
                InstantKt$$ExternalSyntheticBUOutline0.m948m();
            }
        }

        @Override // androidx.sqlite.SQLiteStatement, java.lang.AutoCloseable
        public void close() {
            if (PooledConnectionImpl.this.isRecycled) {
                SQLite.throwSQLiteException(21, "Statement is recycled");
                InstantKt$$ExternalSyntheticBUOutline0.m948m();
            } else if (this.threadId == ThreadLocal_jvmAndroidKt.currentThreadId()) {
                this.delegate.close();
            } else {
                SQLite.throwSQLiteException(21, "Attempted to use statement on a different thread");
                InstantKt$$ExternalSyntheticBUOutline0.m948m();
            }
        }

        @Override // androidx.sqlite.SQLiteStatement
        public byte[] getBlob(int index) {
            if (PooledConnectionImpl.this.isRecycled) {
                SQLite.throwSQLiteException(21, "Statement is recycled");
                InstantKt$$ExternalSyntheticBUOutline0.m948m();
                return null;
            }
            if (this.threadId == ThreadLocal_jvmAndroidKt.currentThreadId()) {
                return this.delegate.getBlob(index);
            }
            SQLite.throwSQLiteException(21, "Attempted to use statement on a different thread");
            InstantKt$$ExternalSyntheticBUOutline0.m948m();
            return null;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public int getColumnCount() {
            if (PooledConnectionImpl.this.isRecycled) {
                SQLite.throwSQLiteException(21, "Statement is recycled");
                InstantKt$$ExternalSyntheticBUOutline0.m948m();
                return 0;
            }
            if (this.threadId == ThreadLocal_jvmAndroidKt.currentThreadId()) {
                return this.delegate.getColumnCount();
            }
            SQLite.throwSQLiteException(21, "Attempted to use statement on a different thread");
            InstantKt$$ExternalSyntheticBUOutline0.m948m();
            return 0;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public String getColumnName(int index) {
            if (PooledConnectionImpl.this.isRecycled) {
                SQLite.throwSQLiteException(21, "Statement is recycled");
                InstantKt$$ExternalSyntheticBUOutline0.m948m();
                return null;
            }
            if (this.threadId == ThreadLocal_jvmAndroidKt.currentThreadId()) {
                return this.delegate.getColumnName(index);
            }
            SQLite.throwSQLiteException(21, "Attempted to use statement on a different thread");
            InstantKt$$ExternalSyntheticBUOutline0.m948m();
            return null;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public long getLong(int index) {
            if (PooledConnectionImpl.this.isRecycled) {
                SQLite.throwSQLiteException(21, "Statement is recycled");
                InstantKt$$ExternalSyntheticBUOutline0.m948m();
                return 0L;
            }
            if (this.threadId == ThreadLocal_jvmAndroidKt.currentThreadId()) {
                return this.delegate.getLong(index);
            }
            SQLite.throwSQLiteException(21, "Attempted to use statement on a different thread");
            InstantKt$$ExternalSyntheticBUOutline0.m948m();
            return 0L;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public String getText(int index) {
            if (PooledConnectionImpl.this.isRecycled) {
                SQLite.throwSQLiteException(21, "Statement is recycled");
                InstantKt$$ExternalSyntheticBUOutline0.m948m();
                return null;
            }
            if (this.threadId == ThreadLocal_jvmAndroidKt.currentThreadId()) {
                return this.delegate.getText(index);
            }
            SQLite.throwSQLiteException(21, "Attempted to use statement on a different thread");
            InstantKt$$ExternalSyntheticBUOutline0.m948m();
            return null;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public boolean isNull(int index) {
            if (PooledConnectionImpl.this.isRecycled) {
                SQLite.throwSQLiteException(21, "Statement is recycled");
                InstantKt$$ExternalSyntheticBUOutline0.m948m();
                return false;
            }
            if (this.threadId == ThreadLocal_jvmAndroidKt.currentThreadId()) {
                return this.delegate.isNull(index);
            }
            SQLite.throwSQLiteException(21, "Attempted to use statement on a different thread");
            InstantKt$$ExternalSyntheticBUOutline0.m948m();
            return false;
        }

        @Override // androidx.sqlite.SQLiteStatement
        public void reset() {
            if (PooledConnectionImpl.this.isRecycled) {
                SQLite.throwSQLiteException(21, "Statement is recycled");
                InstantKt$$ExternalSyntheticBUOutline0.m948m();
            } else if (this.threadId == ThreadLocal_jvmAndroidKt.currentThreadId()) {
                this.delegate.reset();
            } else {
                SQLite.throwSQLiteException(21, "Attempted to use statement on a different thread");
                InstantKt$$ExternalSyntheticBUOutline0.m948m();
            }
        }

        @Override // androidx.sqlite.SQLiteStatement
        public boolean step() {
            if (PooledConnectionImpl.this.isRecycled) {
                SQLite.throwSQLiteException(21, "Statement is recycled");
                InstantKt$$ExternalSyntheticBUOutline0.m948m();
                return false;
            }
            if (this.threadId == ThreadLocal_jvmAndroidKt.currentThreadId()) {
                return this.delegate.step();
            }
            SQLite.throwSQLiteException(21, "Attempted to use statement on a different thread");
            InstantKt$$ExternalSyntheticBUOutline0.m948m();
            return false;
        }
    }
}
