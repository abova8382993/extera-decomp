package com.exteragram.messenger.api.p013db;

import com.exteragram.messenger.api.dto.AddedRegDateDTO;
import com.exteragram.messenger.api.dto.BoostySubscriberDTO;
import com.exteragram.messenger.api.dto.NowPlayingInfoDTO;
import com.exteragram.messenger.api.dto.ProfileDTO;
import com.sun.jna.Callback;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import okhttp3.internal.url._UrlKt;
import okio.Segment$$ExternalSyntheticBUOutline1;
import org.telegram.messenger.FileLog;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001c\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0086@¢\u0006\u0002\u0010\u000bJ\u001c\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\tH\u0086@¢\u0006\u0002\u0010\u000bJ \u0010\u0010\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u000f2\u000e\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00140\u0013H\u0007J(\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u000f2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00142\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\r0\u0013H\u0007J\u001e\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00020\u000f2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00190\u0013H\u0007J\u0010\u0010\u001a\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00020\u000fH\u0007J\u001c\u0010\u001b\u001a\u00020\u00072\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\tH\u0086@¢\u0006\u0002\u0010\u000bJ\u001c\u0010\u001e\u001a\u00020\u00072\u0012\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001d0\t0\u0013H\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, m877d2 = {"Lcom/exteragram/messenger/api/db/DatabaseHelper;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "scope", "Lkotlinx/coroutines/CoroutineScope;", "insertProfiles", _UrlKt.FRAGMENT_ENCODE_SET, "profiles", _UrlKt.FRAGMENT_ENCODE_SET, "Lcom/exteragram/messenger/api/dto/ProfileDTO;", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteProfiles", _UrlKt.FRAGMENT_ENCODE_SET, "ids", _UrlKt.FRAGMENT_ENCODE_SET, "getNowPlaying", "id", Callback.METHOD_NAME, "Ljava/util/function/Consumer;", "Lcom/exteragram/messenger/api/dto/NowPlayingInfoDTO;", "updateNowPlaying", "newNowPlaying", "isRegDateAdded", "userId", _UrlKt.FRAGMENT_ENCODE_SET, "setRegDateAdded", "insertBoostySubscribers", "subscribers", "Lcom/exteragram/messenger/api/dto/BoostySubscriberDTO;", "getBoostySubscribers", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
public final class DatabaseHelper {
    public static final DatabaseHelper INSTANCE = new DatabaseHelper();
    private static final CoroutineScope scope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO());

    /* JADX INFO: renamed from: com.exteragram.messenger.api.db.DatabaseHelper$deleteProfiles$1 */
    /* JADX INFO: loaded from: classes4.dex */
    @Metadata(m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
    @DebugMetadata(m895c = "com.exteragram.messenger.api.db.DatabaseHelper", m896f = "DatabaseHelper.kt", m897i = {0}, m898l = {37}, m899m = "deleteProfiles", m900n = {"ids"}, m902s = {"L$0"}, m903v = 1)
    public static final class C10571 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C10571(Continuation<? super C10571> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return DatabaseHelper.this.deleteProfiles(null, this);
        }
    }

    /* JADX INFO: renamed from: com.exteragram.messenger.api.db.DatabaseHelper$insertBoostySubscribers$1 */
    @Metadata(m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
    @DebugMetadata(m895c = "com.exteragram.messenger.api.db.DatabaseHelper", m896f = "DatabaseHelper.kt", m897i = {0}, m898l = {97}, m899m = "insertBoostySubscribers", m900n = {"subscribers"}, m902s = {"L$0"}, m903v = 1)
    public static final class C10611 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C10611(Continuation<? super C10611> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return DatabaseHelper.this.insertBoostySubscribers(null, this);
        }
    }

    /* JADX INFO: renamed from: com.exteragram.messenger.api.db.DatabaseHelper$insertProfiles$1 */
    /* JADX INFO: loaded from: classes4.dex */
    @Metadata(m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
    @DebugMetadata(m895c = "com.exteragram.messenger.api.db.DatabaseHelper", m896f = "DatabaseHelper.kt", m897i = {0}, m898l = {29}, m899m = "insertProfiles", m900n = {"profiles"}, m902s = {"L$0"}, m903v = 1)
    public static final class C10621 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C10621(Continuation<? super C10621> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return DatabaseHelper.this.insertProfiles(null, this);
        }
    }

    private DatabaseHelper() {
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object insertProfiles(java.util.List<com.exteragram.messenger.api.dto.ProfileDTO> r5, kotlin.coroutines.Continuation<? super kotlin.Unit> r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.exteragram.messenger.api.p013db.DatabaseHelper.C10621
            if (r0 == 0) goto L13
            r0 = r6
            com.exteragram.messenger.api.db.DatabaseHelper$insertProfiles$1 r0 = (com.exteragram.messenger.api.p013db.DatabaseHelper.C10621) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.exteragram.messenger.api.db.DatabaseHelper$insertProfiles$1 r0 = new com.exteragram.messenger.api.db.DatabaseHelper$insertProfiles$1
            r0.<init>(r6)
        L18:
            java.lang.Object r4 = r0.result
            java.lang.Object r6 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r0.label
            r2 = 1
            if (r1 == 0) goto L34
            if (r1 != r2) goto L2d
            java.lang.Object r5 = r0.L$0
            java.util.List r5 = (java.util.List) r5
            kotlin.ResultKt.throwOnFailure(r4)     // Catch: java.lang.Throwable -> L50
            goto L54
        L2d:
            java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r4)
            r4 = 0
            return r4
        L34:
            kotlin.ResultKt.throwOnFailure(r4)
            com.exteragram.messenger.api.db.ExteraDatabase$Companion r4 = com.exteragram.messenger.api.p013db.ExteraDatabase.INSTANCE     // Catch: java.lang.Throwable -> L50
            com.exteragram.messenger.api.db.ExteraDatabase r4 = r4.getInstance()     // Catch: java.lang.Throwable -> L50
            com.exteragram.messenger.api.db.ProfileDao r4 = r4.profileDao()     // Catch: java.lang.Throwable -> L50
            java.lang.Object r1 = kotlin.coroutines.jvm.internal.SpillingKt.nullOutSpilledVariable(r5)     // Catch: java.lang.Throwable -> L50
            r0.L$0 = r1     // Catch: java.lang.Throwable -> L50
            r0.label = r2     // Catch: java.lang.Throwable -> L50
            java.lang.Object r4 = r4.insertAll(r5, r0)     // Catch: java.lang.Throwable -> L50
            if (r4 != r6) goto L54
            return r6
        L50:
            r4 = move-exception
            org.telegram.messenger.FileLog.m1048e(r4)
        L54:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.api.p013db.DatabaseHelper.insertProfiles(java.util.List, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object deleteProfiles(java.util.List<java.lang.Long> r5, kotlin.coroutines.Continuation<? super java.lang.Integer> r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.exteragram.messenger.api.p013db.DatabaseHelper.C10571
            if (r0 == 0) goto L13
            r0 = r6
            com.exteragram.messenger.api.db.DatabaseHelper$deleteProfiles$1 r0 = (com.exteragram.messenger.api.p013db.DatabaseHelper.C10571) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.exteragram.messenger.api.db.DatabaseHelper$deleteProfiles$1 r0 = new com.exteragram.messenger.api.db.DatabaseHelper$deleteProfiles$1
            r0.<init>(r6)
        L18:
            java.lang.Object r4 = r0.result
            java.lang.Object r6 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r0.label
            r2 = 1
            if (r1 == 0) goto L34
            if (r1 != r2) goto L2d
            java.lang.Object r5 = r0.L$0
            java.util.List r5 = (java.util.List) r5
            kotlin.ResultKt.throwOnFailure(r4)     // Catch: java.lang.Throwable -> L57
            goto L50
        L2d:
            java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r4)
            r4 = 0
            return r4
        L34:
            kotlin.ResultKt.throwOnFailure(r4)
            com.exteragram.messenger.api.db.ExteraDatabase$Companion r4 = com.exteragram.messenger.api.p013db.ExteraDatabase.INSTANCE     // Catch: java.lang.Throwable -> L57
            com.exteragram.messenger.api.db.ExteraDatabase r4 = r4.getInstance()     // Catch: java.lang.Throwable -> L57
            com.exteragram.messenger.api.db.ProfileDao r4 = r4.profileDao()     // Catch: java.lang.Throwable -> L57
            java.lang.Object r1 = kotlin.coroutines.jvm.internal.SpillingKt.nullOutSpilledVariable(r5)     // Catch: java.lang.Throwable -> L57
            r0.L$0 = r1     // Catch: java.lang.Throwable -> L57
            r0.label = r2     // Catch: java.lang.Throwable -> L57
            java.lang.Object r4 = r4.deleteProfiles(r5, r0)     // Catch: java.lang.Throwable -> L57
            if (r4 != r6) goto L50
            return r6
        L50:
            java.lang.Number r4 = (java.lang.Number) r4     // Catch: java.lang.Throwable -> L57
            int r4 = r4.intValue()     // Catch: java.lang.Throwable -> L57
            goto L5c
        L57:
            r4 = move-exception
            org.telegram.messenger.FileLog.m1048e(r4)
            r4 = 0
        L5c:
            java.lang.Integer r4 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r4)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.api.p013db.DatabaseHelper.deleteProfiles(java.util.List, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: renamed from: com.exteragram.messenger.api.db.DatabaseHelper$getNowPlaying$1 */
    /* JADX INFO: loaded from: classes4.dex */
    @Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
    @DebugMetadata(m895c = "com.exteragram.messenger.api.db.DatabaseHelper$getNowPlaying$1", m896f = "DatabaseHelper.kt", m897i = {}, m898l = {48}, m899m = "invokeSuspend", m900n = {}, m902s = {}, m903v = 1)
    public static final class C10601 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Consumer<NowPlayingInfoDTO> $callback;
        final /* synthetic */ long $id;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10601(long j, Consumer<NowPlayingInfoDTO> consumer, Continuation<? super C10601> continuation) {
            super(2, continuation);
            this.$id = j;
            this.$callback = consumer;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C10601(this.$id, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C10601) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ProfileDao profileDao = ExteraDatabase.INSTANCE.getInstance().profileDao();
                    long j = this.$id;
                    this.label = 1;
                    obj = profileDao.getById(j, this);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i != 1) {
                        Segment$$ExternalSyntheticBUOutline1.m992m("call to 'resume' before 'invoke' with coroutine");
                        return null;
                    }
                    ResultKt.throwOnFailure(obj);
                }
                ProfileDTO profileDTO = (ProfileDTO) obj;
                this.$callback.accept(profileDTO != null ? profileDTO.getNowPlaying() : null);
            } catch (Throwable th) {
                FileLog.m1048e(th);
                this.$callback.accept(null);
            }
            return Unit.INSTANCE;
        }
    }

    @JvmStatic
    public static final void getNowPlaying(long id, Consumer<NowPlayingInfoDTO> consumer) {
        BuildersKt__Builders_commonKt.launch$default(scope, null, null, new C10601(id, consumer, null), 3, null);
    }

    /* JADX INFO: renamed from: com.exteragram.messenger.api.db.DatabaseHelper$updateNowPlaying$1 */
    /* JADX INFO: loaded from: classes4.dex */
    @Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
    @DebugMetadata(m895c = "com.exteragram.messenger.api.db.DatabaseHelper$updateNowPlaying$1", m896f = "DatabaseHelper.kt", m897i = {}, m898l = {62}, m899m = "invokeSuspend", m900n = {}, m902s = {}, m903v = 1)
    public static final class C10651 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Consumer<Integer> $callback;
        final /* synthetic */ long $id;
        final /* synthetic */ NowPlayingInfoDTO $newNowPlaying;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10651(long j, NowPlayingInfoDTO nowPlayingInfoDTO, Consumer<Integer> consumer, Continuation<? super C10651> continuation) {
            super(2, continuation);
            this.$id = j;
            this.$newNowPlaying = nowPlayingInfoDTO;
            this.$callback = consumer;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C10651(this.$id, this.$newNowPlaying, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C10651) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ProfileDao profileDao = ExteraDatabase.INSTANCE.getInstance().profileDao();
                    long j = this.$id;
                    NowPlayingInfoDTO nowPlayingInfoDTO = this.$newNowPlaying;
                    this.label = 1;
                    obj = profileDao.updateNowPlaying(j, nowPlayingInfoDTO, this);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i != 1) {
                        Segment$$ExternalSyntheticBUOutline1.m992m("call to 'resume' before 'invoke' with coroutine");
                        return null;
                    }
                    ResultKt.throwOnFailure(obj);
                }
                this.$callback.accept(Boxing.boxInt(((Number) obj).intValue()));
            } catch (Throwable th) {
                FileLog.m1048e(th);
                this.$callback.accept(Boxing.boxInt(0));
            }
            return Unit.INSTANCE;
        }
    }

    @JvmStatic
    public static final void updateNowPlaying(long id, NowPlayingInfoDTO newNowPlaying, Consumer<Integer> consumer) {
        BuildersKt__Builders_commonKt.launch$default(scope, null, null, new C10651(id, newNowPlaying, consumer, null), 3, null);
    }

    /* JADX INFO: renamed from: com.exteragram.messenger.api.db.DatabaseHelper$isRegDateAdded$1 */
    /* JADX INFO: loaded from: classes4.dex */
    @Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
    @DebugMetadata(m895c = "com.exteragram.messenger.api.db.DatabaseHelper$isRegDateAdded$1", m896f = "DatabaseHelper.kt", m897i = {}, m898l = {75}, m899m = "invokeSuspend", m900n = {}, m902s = {}, m903v = 1)
    public static final class C10631 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Consumer<Boolean> $callback;
        final /* synthetic */ long $userId;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10631(long j, Consumer<Boolean> consumer, Continuation<? super C10631> continuation) {
            super(2, continuation);
            this.$userId = j;
            this.$callback = consumer;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C10631(this.$userId, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C10631) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    AddedRegDateDao addedRegDateDao = ExteraDatabase.INSTANCE.getInstance().addedRegDateDao();
                    long j = this.$userId;
                    this.label = 1;
                    obj = addedRegDateDao.isAdded(j, this);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i != 1) {
                        Segment$$ExternalSyntheticBUOutline1.m992m("call to 'resume' before 'invoke' with coroutine");
                        return null;
                    }
                    ResultKt.throwOnFailure(obj);
                }
                this.$callback.accept(Boxing.boxBoolean(((Boolean) obj).booleanValue()));
            } catch (Throwable th) {
                FileLog.m1048e(th);
                this.$callback.accept(Boxing.boxBoolean(false));
            }
            return Unit.INSTANCE;
        }
    }

    @JvmStatic
    public static final void isRegDateAdded(long userId, Consumer<Boolean> consumer) {
        BuildersKt__Builders_commonKt.launch$default(scope, null, null, new C10631(userId, consumer, null), 3, null);
    }

    /* JADX INFO: renamed from: com.exteragram.messenger.api.db.DatabaseHelper$setRegDateAdded$1 */
    /* JADX INFO: loaded from: classes4.dex */
    @Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
    @DebugMetadata(m895c = "com.exteragram.messenger.api.db.DatabaseHelper$setRegDateAdded$1", m896f = "DatabaseHelper.kt", m897i = {}, m898l = {88}, m899m = "invokeSuspend", m900n = {}, m902s = {}, m903v = 1)
    public static final class C10641 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ long $userId;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10641(long j, Continuation<? super C10641> continuation) {
            super(2, continuation);
            this.$userId = j;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C10641(this.$userId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C10641) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    AddedRegDateDao addedRegDateDao = ExteraDatabase.INSTANCE.getInstance().addedRegDateDao();
                    AddedRegDateDTO addedRegDateDTO = new AddedRegDateDTO(this.$userId);
                    this.label = 1;
                    if (addedRegDateDao.insert(addedRegDateDTO, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i != 1) {
                        Segment$$ExternalSyntheticBUOutline1.m992m("call to 'resume' before 'invoke' with coroutine");
                        return null;
                    }
                    ResultKt.throwOnFailure(obj);
                }
            } catch (Throwable th) {
                FileLog.m1048e(th);
            }
            return Unit.INSTANCE;
        }
    }

    @JvmStatic
    public static final void setRegDateAdded(long userId) {
        BuildersKt__Builders_commonKt.launch$default(scope, null, null, new C10641(userId, null), 3, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object insertBoostySubscribers(java.util.List<com.exteragram.messenger.api.dto.BoostySubscriberDTO> r5, kotlin.coroutines.Continuation<? super kotlin.Unit> r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.exteragram.messenger.api.p013db.DatabaseHelper.C10611
            if (r0 == 0) goto L13
            r0 = r6
            com.exteragram.messenger.api.db.DatabaseHelper$insertBoostySubscribers$1 r0 = (com.exteragram.messenger.api.p013db.DatabaseHelper.C10611) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.exteragram.messenger.api.db.DatabaseHelper$insertBoostySubscribers$1 r0 = new com.exteragram.messenger.api.db.DatabaseHelper$insertBoostySubscribers$1
            r0.<init>(r6)
        L18:
            java.lang.Object r4 = r0.result
            java.lang.Object r6 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r0.label
            r2 = 1
            if (r1 == 0) goto L34
            if (r1 != r2) goto L2d
            java.lang.Object r5 = r0.L$0
            java.util.List r5 = (java.util.List) r5
            kotlin.ResultKt.throwOnFailure(r4)     // Catch: java.lang.Throwable -> L50
            goto L54
        L2d:
            java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r4)
            r4 = 0
            return r4
        L34:
            kotlin.ResultKt.throwOnFailure(r4)
            com.exteragram.messenger.api.db.ExteraDatabase$Companion r4 = com.exteragram.messenger.api.p013db.ExteraDatabase.INSTANCE     // Catch: java.lang.Throwable -> L50
            com.exteragram.messenger.api.db.ExteraDatabase r4 = r4.getInstance()     // Catch: java.lang.Throwable -> L50
            com.exteragram.messenger.api.db.BoostySubscriberDao r4 = r4.boostySubscriberDao()     // Catch: java.lang.Throwable -> L50
            java.lang.Object r1 = kotlin.coroutines.jvm.internal.SpillingKt.nullOutSpilledVariable(r5)     // Catch: java.lang.Throwable -> L50
            r0.L$0 = r1     // Catch: java.lang.Throwable -> L50
            r0.label = r2     // Catch: java.lang.Throwable -> L50
            java.lang.Object r4 = r4.replaceSubscribers(r5, r0)     // Catch: java.lang.Throwable -> L50
            if (r4 != r6) goto L54
            return r6
        L50:
            r4 = move-exception
            org.telegram.messenger.FileLog.m1048e(r4)
        L54:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.api.p013db.DatabaseHelper.insertBoostySubscribers(java.util.List, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: renamed from: com.exteragram.messenger.api.db.DatabaseHelper$getBoostySubscribers$1 */
    @Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
    @DebugMetadata(m895c = "com.exteragram.messenger.api.db.DatabaseHelper$getBoostySubscribers$1", m896f = "DatabaseHelper.kt", m897i = {}, m898l = {107}, m899m = "invokeSuspend", m900n = {}, m902s = {}, m903v = 1)
    @SourceDebugExtension({"SMAP\nDatabaseHelper.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DatabaseHelper.kt\ncom/exteragram/messenger/api/db/DatabaseHelper$getBoostySubscribers$1\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,117:1\n1080#2:118\n*S KotlinDebug\n*F\n+ 1 DatabaseHelper.kt\ncom/exteragram/messenger/api/db/DatabaseHelper$getBoostySubscribers$1\n*L\n108#1:118\n*E\n"})
    public static final class C10581 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Consumer<List<BoostySubscriberDTO>> $callback;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10581(Consumer<List<BoostySubscriberDTO>> consumer, Continuation<? super C10581> continuation) {
            super(2, continuation);
            this.$callback = consumer;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C10581(this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C10581) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    BoostySubscriberDao boostySubscriberDao = ExteraDatabase.INSTANCE.getInstance().boostySubscriberDao();
                    this.label = 1;
                    obj = boostySubscriberDao.getAll(this);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i != 1) {
                        Segment$$ExternalSyntheticBUOutline1.m992m("call to 'resume' before 'invoke' with coroutine");
                        return null;
                    }
                    ResultKt.throwOnFailure(obj);
                }
                this.$callback.accept(CollectionsKt.sortedWith((List) obj, new Comparator() { // from class: com.exteragram.messenger.api.db.DatabaseHelper$getBoostySubscribers$1$invokeSuspend$$inlined$sortedByDescending$1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(T t, T t2) {
                        return ComparisonsKt.compareValues(((BoostySubscriberDTO) t2).getTotalAmountRub(), ((BoostySubscriberDTO) t).getTotalAmountRub());
                    }
                }));
            } catch (Throwable th) {
                FileLog.m1048e(th);
                this.$callback.accept(CollectionsKt.emptyList());
            }
            return Unit.INSTANCE;
        }
    }

    @JvmStatic
    public static final void getBoostySubscribers(Consumer<List<BoostySubscriberDTO>> consumer) {
        BuildersKt__Builders_commonKt.launch$default(scope, null, null, new C10581(consumer, null), 3, null);
    }
}
