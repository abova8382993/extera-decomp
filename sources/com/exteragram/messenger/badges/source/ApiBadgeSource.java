package com.exteragram.messenger.badges.source;

import com.exteragram.messenger.api.dto.BadgeDTO;
import com.exteragram.messenger.api.model.ProfileStatus;
import com.exteragram.messenger.api.p013db.ProfileDao;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function2;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0004\b\u0004\u0010\u0005J!\u0010\u000b\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\bH\u0016¢\u0006\u0004\b\u000b\u0010\fJ\u0015\u0010\r\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\r\u0010\u000eJ\u0015\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\u000f\u0010\u000eJ\"\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0007\u001a\u00020\u00062\b\u0010\u0010\u001a\u0004\u0018\u00010\nH\u0086@¢\u0006\u0004\b\u0012\u0010\u0013J\u0010\u0010\u0014\u001a\u00020\u0011H\u0086@¢\u0006\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010\u0016R \u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00180\u00178\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0019\u0010\u001a¨\u0006\u001b"}, m877d2 = {"Lcom/exteragram/messenger/badges/source/ApiBadgeSource;", _UrlKt.FRAGMENT_ENCODE_SET, "Lcom/exteragram/messenger/api/db/ProfileDao;", "profileDao", "<init>", "(Lcom/exteragram/messenger/api/db/ProfileDao;)V", _UrlKt.FRAGMENT_ENCODE_SET, "id", _UrlKt.FRAGMENT_ENCODE_SET, "isUser", "Lcom/exteragram/messenger/api/dto/BadgeDTO;", "getBadge", "(JZ)Lcom/exteragram/messenger/api/dto/BadgeDTO;", "isDeveloper", "(J)Z", "canChangeBadge", "badge", _UrlKt.FRAGMENT_ENCODE_SET, "updateLocalBadge", "(JLcom/exteragram/messenger/api/dto/BadgeDTO;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadToCache", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lcom/exteragram/messenger/api/db/ProfileDao;", "Ljava/util/concurrent/ConcurrentHashMap;", "Lcom/exteragram/messenger/badges/source/BadgeInfo;", "cache", "Ljava/util/concurrent/ConcurrentHashMap;", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
public final class ApiBadgeSource {
    private final ConcurrentHashMap<Long, BadgeInfo> cache = new ConcurrentHashMap<>();
    private final ProfileDao profileDao;

    /* JADX INFO: renamed from: com.exteragram.messenger.badges.source.ApiBadgeSource$loadToCache$1 */
    @Metadata(m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
    @DebugMetadata(m895c = "com.exteragram.messenger.badges.source.ApiBadgeSource", m896f = "ApiBadgeSource.kt", m897i = {}, m898l = {38}, m899m = "loadToCache", m900n = {}, m902s = {}, m903v = 1)
    public static final class C10701 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C10701(Continuation<? super C10701> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ApiBadgeSource.this.loadToCache(this);
        }
    }

    public ApiBadgeSource(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    public BadgeDTO getBadge(long id, boolean isUser) {
        BadgeInfo badgeInfo = this.cache.get(Long.valueOf(id));
        if (badgeInfo != null) {
            return badgeInfo.getBadge();
        }
        return null;
    }

    public final boolean isDeveloper(long id) {
        BadgeInfo badgeInfo = this.cache.get(Long.valueOf(id));
        return (badgeInfo != null ? badgeInfo.getStatus() : null) == ProfileStatus.DEVELOPER;
    }

    public final boolean canChangeBadge(long id) {
        BadgeInfo badgeInfo = this.cache.get(Long.valueOf(id));
        return (badgeInfo != null && badgeInfo.getCanChangeBadge()) || isDeveloper(id);
    }

    public static BadgeInfo $r8$lambda$k56Kf1gLoZpo2cEpNa8MMgWDGfI(Function2 function2, Object obj, Object obj2) {
        return (BadgeInfo) function2.invoke(obj, obj2);
    }

    public final Object updateLocalBadge(long j, final BadgeDTO badgeDTO, Continuation<? super Unit> continuation) {
        ConcurrentHashMap<Long, BadgeInfo> concurrentHashMap = this.cache;
        Long lBoxLong = Boxing.boxLong(j);
        final Function2 function2 = new Function2() { // from class: com.exteragram.messenger.badges.source.ApiBadgeSource$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return BadgeInfo.copy$default((BadgeInfo) obj2, badgeDTO, null, false, 6, null);
            }
        };
        concurrentHashMap.computeIfPresent(lBoxLong, new BiFunction() { // from class: com.exteragram.messenger.badges.source.ApiBadgeSource$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ApiBadgeSource.$r8$lambda$k56Kf1gLoZpo2cEpNa8MMgWDGfI(function2, obj, obj2);
            }
        });
        Object objUpdateBadge = this.profileDao.updateBadge(j, badgeDTO, continuation);
        return objUpdateBadge == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objUpdateBadge : Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object loadToCache(kotlin.coroutines.Continuation<? super kotlin.Unit> r9) {
        /*
            r8 = this;
            boolean r0 = r9 instanceof com.exteragram.messenger.badges.source.ApiBadgeSource.C10701
            if (r0 == 0) goto L13
            r0 = r9
            com.exteragram.messenger.badges.source.ApiBadgeSource$loadToCache$1 r0 = (com.exteragram.messenger.badges.source.ApiBadgeSource.C10701) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.exteragram.messenger.badges.source.ApiBadgeSource$loadToCache$1 r0 = new com.exteragram.messenger.badges.source.ApiBadgeSource$loadToCache$1
            r0.<init>(r9)
        L18:
            java.lang.Object r9 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L30
            if (r2 != r3) goto L29
            kotlin.ResultKt.throwOnFailure(r9)
            goto L3e
        L29:
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r8)
            r8 = 0
            return r8
        L30:
            kotlin.ResultKt.throwOnFailure(r9)
            com.exteragram.messenger.api.db.ProfileDao r9 = r8.profileDao
            r0.label = r3
            java.lang.Object r9 = r9.getAll(r0)
            if (r9 != r1) goto L3e
            return r1
        L3e:
            java.util.List r9 = (java.util.List) r9
            java.util.Iterator r9 = r9.iterator()
        L44:
            boolean r0 = r9.hasNext()
            if (r0 == 0) goto L77
            java.lang.Object r0 = r9.next()
            com.exteragram.messenger.api.dto.ProfileDTO r0 = (com.exteragram.messenger.api.dto.ProfileDTO) r0
            java.util.concurrent.ConcurrentHashMap<java.lang.Long, com.exteragram.messenger.badges.source.BadgeInfo> r1 = r8.cache
            long r4 = r0.getId()
            java.lang.Long r2 = kotlin.coroutines.jvm.internal.Boxing.boxLong(r4)
            com.exteragram.messenger.badges.source.BadgeInfo r4 = new com.exteragram.messenger.badges.source.BadgeInfo
            com.exteragram.messenger.api.dto.BadgeDTO r5 = r0.getBadge()
            com.exteragram.messenger.api.model.ProfileStatus r6 = r0.getStatus()
            java.lang.Boolean r0 = r0.getCanChangeBadge()
            java.lang.Boolean r7 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r3)
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r7)
            r4.<init>(r5, r6, r0)
            r1.put(r2, r4)
            goto L44
        L77:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.badges.source.ApiBadgeSource.loadToCache(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
