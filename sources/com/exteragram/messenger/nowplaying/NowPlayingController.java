package com.exteragram.messenger.nowplaying;

import androidx.collection.LruCache;
import com.exteragram.messenger.api.dto.NowPlayingDTO;
import com.exteragram.messenger.api.dto.NowPlayingInfoDTO;
import com.exteragram.messenger.api.p010db.DatabaseHelper;
import com.exteragram.messenger.nowplaying.NowPlayingController;
import com.exteragram.messenger.nowplaying.p013ui.components.NowPlayingCardData;
import com.exteragram.messenger.utils.chats.ChatUtils;
import com.exteragram.messenger.utils.network.ExteraHttpClient;
import com.exteragram.messenger.utils.network.RemoteUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.SerializationStrategy;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.CompositeEncoder;
import kotlinx.serialization.internal.ArrayListSerializer;
import kotlinx.serialization.internal.LongSerializer;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import kotlinx.serialization.json.Json;
import kotlinx.serialization.json.JsonBuilder;
import kotlinx.serialization.json.JsonKt;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.tgnet.TLRPC;
import p022j$.util.function.Consumer$CC;

/* JADX INFO: loaded from: classes.dex */
public final class NowPlayingController {
    private static final OkHttpClient httpClient;
    private static final Json jsonParser;
    public static final NowPlayingController INSTANCE = new NowPlayingController();
    private static final Regex ARTISTS_SPLITTER = new Regex("(?i)\\s*(?:,|&|\\bfeat\\b\\.?|\\bft\\b\\.?)\\s*");
    private static final CoroutineScope scope = CoroutineScopeKt.CoroutineScope(SupervisorKt.SupervisorJob$default(null, 1, null).plus(Dispatchers.getMain()));
    private static final LruCache itunesCache = new LruCache(50);

    /* JADX INFO: renamed from: com.exteragram.messenger.nowplaying.NowPlayingController$fetchItunesTrack$1 */
    static final class C11431 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        /* synthetic */ Object result;

        C11431(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return NowPlayingController.this.fetchItunesTrack(null, null, this);
        }
    }

    /* JADX INFO: renamed from: com.exteragram.messenger.nowplaying.NowPlayingController$processSavedMusic$1 */
    static final class C11481 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        /* synthetic */ Object result;

        C11481(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return NowPlayingController.this.processSavedMusic(null, this);
        }
    }

    private NowPlayingController() {
    }

    static {
        OkHttpClient.Builder builderNewBuilder = ExteraHttpClient.INSTANCE.getClient().newBuilder();
        TimeUnit timeUnit = TimeUnit.SECONDS;
        httpClient = builderNewBuilder.connectTimeout(5L, timeUnit).readTimeout(5L, timeUnit).writeTimeout(5L, timeUnit).build();
        jsonParser = JsonKt.Json$default(null, new Function1() { // from class: com.exteragram.messenger.nowplaying.NowPlayingController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NowPlayingController.jsonParser$lambda$0((JsonBuilder) obj);
            }
        }, 1, null);
    }

    public static final boolean shouldShowCard(NowPlayingCardData nowPlayingCardData) {
        if (nowPlayingCardData == null) {
            return false;
        }
        return (Intrinsics.areEqual(nowPlayingCardData.getNowPlayingDTO().getPlatform(), "TELEGRAM") && isSeparateStylesSupported()) ? false : true;
    }

    public static final boolean isSeparateStylesSupported() {
        Boolean booleanConfigValue = RemoteUtils.getBooleanConfigValue("separate_music_styles", false);
        Intrinsics.checkNotNullExpressionValue(booleanConfigValue, "getBooleanConfigValue(...)");
        return booleanConfigValue.booleanValue();
    }

    /* JADX INFO: renamed from: com.exteragram.messenger.nowplaying.NowPlayingController$getCurrentPlayingTrack$1 */
    static final class C11441 extends SuspendLambda implements Function2 {
        final /* synthetic */ BiConsumer $callback;
        final /* synthetic */ boolean $checkApi;
        final /* synthetic */ TLRPC.Document $savedMusic;
        final /* synthetic */ long $userId;
        long J$0;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C11441(boolean z, long j, TLRPC.Document document, BiConsumer biConsumer, Continuation continuation) {
            super(2, continuation);
            this.$checkApi = z;
            this.$userId = j;
            this.$savedMusic = document;
            this.$callback = biConsumer;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C11441 c11441 = new C11441(this.$checkApi, this.$userId, this.$savedMusic, this.$callback, continuation);
            c11441.L$0 = obj;
            return c11441;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C11441) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:23:0x00d1, code lost:
        
            if (r15 == r0) goto L28;
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x010a, code lost:
        
            if (kotlinx.coroutines.BuildersKt.withContext(r15, r8, r14) != r0) goto L29;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r15) {
            /*
                Method dump skipped, instruction units count: 272
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.nowplaying.NowPlayingController.C11441.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: renamed from: com.exteragram.messenger.nowplaying.NowPlayingController$getCurrentPlayingTrack$1$1, reason: invalid class name */
        static final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ BiConsumer $callback;
            final /* synthetic */ NowPlayingDTO $finalTrack;
            final /* synthetic */ long $startTime;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(BiConsumer biConsumer, NowPlayingDTO nowPlayingDTO, long j, Continuation continuation) {
                super(2, continuation);
                this.$callback = biConsumer;
                this.$finalTrack = nowPlayingDTO;
                this.$startTime = j;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$callback, this.$finalTrack, this.$startTime, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.$callback.accept(this.$finalTrack, Boxing.boxLong(System.currentTimeMillis() - this.$startTime));
                return Unit.INSTANCE;
            }
        }
    }

    public static final Job getCurrentPlayingTrack(long j, TLRPC.Document document, boolean z, BiConsumer callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        return BuildersKt__Builders_commonKt.launch$default(scope, null, null, new C11441(z, j, document, callback, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object processSavedMusic(org.telegram.tgnet.TLRPC.Document r21, kotlin.coroutines.Continuation r22) {
        /*
            Method dump skipped, instruction units count: 440
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.nowplaying.NowPlayingController.processSavedMusic(org.telegram.tgnet.TLRPC$Document, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final Pair hasCommonArtist(List list, String str) {
        if (str == null || StringsKt.isBlank(str)) {
            return TuplesKt.m1081to(Boolean.FALSE, list);
        }
        boolean z = false;
        List listSplit = ARTISTS_SPLITTER.split(str, 0);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit, 10));
        Iterator it = listSplit.iterator();
        while (it.hasNext()) {
            arrayList.add(StringsKt.trim((String) it.next()).toString());
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            if (((String) obj).length() > 0) {
                arrayList2.add(obj);
            }
        }
        List list2 = list;
        if (list2 == null || list2.isEmpty()) {
            return TuplesKt.m1081to(Boolean.FALSE, arrayList2);
        }
        List list3 = list;
        if (!(list3 instanceof Collection) || !list3.isEmpty()) {
            Iterator it2 = list3.iterator();
            loop2: while (it2.hasNext()) {
                String lowerCase = ((String) it2.next()).toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                String strTranslitSafe = AndroidUtilities.translitSafe(lowerCase);
                Intrinsics.checkNotNullExpressionValue(strTranslitSafe, "translitSafe(...)");
                if (!arrayList2.isEmpty()) {
                    int size2 = arrayList2.size();
                    int i2 = 0;
                    while (i2 < size2) {
                        Object obj2 = arrayList2.get(i2);
                        i2++;
                        String lowerCase2 = ((String) obj2).toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
                        String strTranslitSafe2 = AndroidUtilities.translitSafe(lowerCase2);
                        Intrinsics.checkNotNullExpressionValue(strTranslitSafe2, "translitSafe(...)");
                        if (Intrinsics.areEqual(lowerCase2, lowerCase) || Intrinsics.areEqual(lowerCase2, strTranslitSafe) || Intrinsics.areEqual(strTranslitSafe2, lowerCase)) {
                            z = true;
                            break loop2;
                        }
                    }
                }
            }
        }
        if (z) {
            list = arrayList2;
        }
        return TuplesKt.m1081to(Boolean.valueOf(z), list);
    }

    /* JADX INFO: renamed from: com.exteragram.messenger.nowplaying.NowPlayingController$getNowPlayingInfo$1 */
    /* JADX INFO: loaded from: classes4.dex */
    static final class C11471 extends SuspendLambda implements Function2 {
        final /* synthetic */ Consumer $callback;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C11471(Consumer consumer, Continuation continuation) {
            super(2, continuation);
            this.$callback = consumer;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C11471(this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C11471) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                NowPlayingController nowPlayingController = NowPlayingController.INSTANCE;
                this.label = 1;
                obj = nowPlayingController.getNowPlayingInfoInternal(this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            this.$callback.m940v((NowPlayingInfoDTO) obj);
            return Unit.INSTANCE;
        }
    }

    public static final void getNowPlayingInfo(Consumer callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        BuildersKt__Builders_commonKt.launch$default(scope, null, null, new C11471(callback, null), 3, null);
    }

    public static /* synthetic */ void updateNowPlayingInfo$default(NowPlayingInfoDTO nowPlayingInfoDTO, boolean z, Consumer consumer, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        updateNowPlayingInfo(nowPlayingInfoDTO, z, consumer);
    }

    /* JADX INFO: renamed from: com.exteragram.messenger.nowplaying.NowPlayingController$updateNowPlayingInfo$1 */
    /* JADX INFO: loaded from: classes4.dex */
    static final class C11491 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $cache;
        final /* synthetic */ Consumer $callback;
        final /* synthetic */ NowPlayingInfoDTO $newNowPlaying;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C11491(NowPlayingInfoDTO nowPlayingInfoDTO, boolean z, Consumer consumer, Continuation continuation) {
            super(2, continuation);
            this.$newNowPlaying = nowPlayingInfoDTO;
            this.$cache = z;
            this.$callback = consumer;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C11491(this.$newNowPlaying, this.$cache, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C11491) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                NowPlayingController nowPlayingController = NowPlayingController.INSTANCE;
                NowPlayingInfoDTO nowPlayingInfoDTO = this.$newNowPlaying;
                boolean z = this.$cache;
                this.label = 1;
                obj = nowPlayingController.updateNowPlayingInfoInternal(nowPlayingInfoDTO, z, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            this.$callback.m940v(Boxing.boxBoolean(((Boolean) obj).booleanValue()));
            return Unit.INSTANCE;
        }
    }

    public static final void updateNowPlayingInfo(NowPlayingInfoDTO nowPlayingInfoDTO, boolean z, Consumer callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        BuildersKt__Builders_commonKt.launch$default(scope, null, null, new C11491(nowPlayingInfoDTO, z, callback, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object getNowPlayingInfoInternal(Continuation continuation) {
        return dbGetNowPlaying(ChatUtils.getInstance().getUserConfig().getClientUserId(), continuation);
    }

    /* JADX INFO: renamed from: com.exteragram.messenger.nowplaying.NowPlayingController$updateNowPlayingInfoInternal$2 */
    /* JADX INFO: loaded from: classes4.dex */
    static final class C11502 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $cache;
        final /* synthetic */ NowPlayingInfoDTO $newNowPlaying;
        int I$0;
        Object L$0;
        Object L$1;
        boolean Z$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C11502(NowPlayingInfoDTO nowPlayingInfoDTO, boolean z, Continuation continuation) {
            super(2, continuation);
            this.$newNowPlaying = nowPlayingInfoDTO;
            this.$cache = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C11502(this.$newNowPlaying, this.$cache, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C11502) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:31:0x00c9, code lost:
        
            if (r10 == r0) goto L32;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r10) {
            /*
                Method dump skipped, instruction units count: 229
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.nowplaying.NowPlayingController.C11502.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object updateNowPlayingInfoInternal(NowPlayingInfoDTO nowPlayingInfoDTO, boolean z, Continuation continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new C11502(nowPlayingInfoDTO, z, null), continuation);
    }

    public static final class ItunesSearchResponse {
        private final int resultCount;
        private final List results;
        public static final Companion Companion = new Companion(null);
        private static final Lazy[] $childSerializers = {null, LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, new Function0() { // from class: com.exteragram.messenger.nowplaying.NowPlayingController$ItunesSearchResponse$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return NowPlayingController.ItunesSearchResponse._childSerializers$_anonymous_();
            }
        })};

        /* JADX INFO: Access modifiers changed from: private */
        public static final /* synthetic */ KSerializer _childSerializers$_anonymous_() {
            return new ArrayListSerializer(NowPlayingController$ItunesTrack$$serializer.INSTANCE);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ItunesSearchResponse)) {
                return false;
            }
            ItunesSearchResponse itunesSearchResponse = (ItunesSearchResponse) obj;
            return this.resultCount == itunesSearchResponse.resultCount && Intrinsics.areEqual(this.results, itunesSearchResponse.results);
        }

        public int hashCode() {
            return (this.resultCount * 31) + this.results.hashCode();
        }

        public String toString() {
            return "ItunesSearchResponse(resultCount=" + this.resultCount + ", results=" + this.results + ')';
        }

        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final KSerializer serializer() {
                return NowPlayingController$ItunesSearchResponse$$serializer.INSTANCE;
            }
        }

        public /* synthetic */ ItunesSearchResponse(int i, int i2, List list, SerializationConstructorMarker serializationConstructorMarker) {
            if (3 != (i & 3)) {
                PluginExceptionsKt.throwMissingFieldException(i, 3, NowPlayingController$ItunesSearchResponse$$serializer.INSTANCE.getDescriptor());
            }
            this.resultCount = i2;
            this.results = list;
        }

        public static final /* synthetic */ void write$Self$TMessagesProj_fullAfatRelease(ItunesSearchResponse itunesSearchResponse, CompositeEncoder compositeEncoder, SerialDescriptor serialDescriptor) {
            Lazy[] lazyArr = $childSerializers;
            compositeEncoder.encodeIntElement(serialDescriptor, 0, itunesSearchResponse.resultCount);
            compositeEncoder.encodeSerializableElement(serialDescriptor, 1, (SerializationStrategy) lazyArr[1].getValue(), itunesSearchResponse.results);
        }

        public final List getResults() {
            return this.results;
        }
    }

    public static final class ItunesTrack {
        public static final Companion Companion = new Companion(null);
        private final String artistName;
        private final String artworkUrl100;
        private final String collectionName;
        private final String previewUrl;
        private final String trackName;
        private final Long trackTimeMillis;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ItunesTrack)) {
                return false;
            }
            ItunesTrack itunesTrack = (ItunesTrack) obj;
            return Intrinsics.areEqual(this.trackName, itunesTrack.trackName) && Intrinsics.areEqual(this.artistName, itunesTrack.artistName) && Intrinsics.areEqual(this.collectionName, itunesTrack.collectionName) && Intrinsics.areEqual(this.artworkUrl100, itunesTrack.artworkUrl100) && Intrinsics.areEqual(this.previewUrl, itunesTrack.previewUrl) && Intrinsics.areEqual(this.trackTimeMillis, itunesTrack.trackTimeMillis);
        }

        public int hashCode() {
            String str = this.trackName;
            int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
            String str2 = this.artistName;
            int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
            String str3 = this.collectionName;
            int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
            String str4 = this.artworkUrl100;
            int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
            String str5 = this.previewUrl;
            int iHashCode5 = (iHashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31;
            Long l = this.trackTimeMillis;
            return iHashCode5 + (l != null ? l.hashCode() : 0);
        }

        public String toString() {
            return "ItunesTrack(trackName=" + this.trackName + ", artistName=" + this.artistName + ", collectionName=" + this.collectionName + ", artworkUrl100=" + this.artworkUrl100 + ", previewUrl=" + this.previewUrl + ", trackTimeMillis=" + this.trackTimeMillis + ')';
        }

        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final KSerializer serializer() {
                return NowPlayingController$ItunesTrack$$serializer.INSTANCE;
            }
        }

        public /* synthetic */ ItunesTrack(int i, String str, String str2, String str3, String str4, String str5, Long l, SerializationConstructorMarker serializationConstructorMarker) {
            if ((i & 1) == 0) {
                this.trackName = null;
            } else {
                this.trackName = str;
            }
            if ((i & 2) == 0) {
                this.artistName = null;
            } else {
                this.artistName = str2;
            }
            if ((i & 4) == 0) {
                this.collectionName = null;
            } else {
                this.collectionName = str3;
            }
            if ((i & 8) == 0) {
                this.artworkUrl100 = null;
            } else {
                this.artworkUrl100 = str4;
            }
            if ((i & 16) == 0) {
                this.previewUrl = null;
            } else {
                this.previewUrl = str5;
            }
            if ((i & 32) == 0) {
                this.trackTimeMillis = null;
            } else {
                this.trackTimeMillis = l;
            }
        }

        public static final /* synthetic */ void write$Self$TMessagesProj_fullAfatRelease(ItunesTrack itunesTrack, CompositeEncoder compositeEncoder, SerialDescriptor serialDescriptor) {
            if (compositeEncoder.shouldEncodeElementDefault(serialDescriptor, 0) || itunesTrack.trackName != null) {
                compositeEncoder.encodeNullableSerializableElement(serialDescriptor, 0, StringSerializer.INSTANCE, itunesTrack.trackName);
            }
            if (compositeEncoder.shouldEncodeElementDefault(serialDescriptor, 1) || itunesTrack.artistName != null) {
                compositeEncoder.encodeNullableSerializableElement(serialDescriptor, 1, StringSerializer.INSTANCE, itunesTrack.artistName);
            }
            if (compositeEncoder.shouldEncodeElementDefault(serialDescriptor, 2) || itunesTrack.collectionName != null) {
                compositeEncoder.encodeNullableSerializableElement(serialDescriptor, 2, StringSerializer.INSTANCE, itunesTrack.collectionName);
            }
            if (compositeEncoder.shouldEncodeElementDefault(serialDescriptor, 3) || itunesTrack.artworkUrl100 != null) {
                compositeEncoder.encodeNullableSerializableElement(serialDescriptor, 3, StringSerializer.INSTANCE, itunesTrack.artworkUrl100);
            }
            if (compositeEncoder.shouldEncodeElementDefault(serialDescriptor, 4) || itunesTrack.previewUrl != null) {
                compositeEncoder.encodeNullableSerializableElement(serialDescriptor, 4, StringSerializer.INSTANCE, itunesTrack.previewUrl);
            }
            if (!compositeEncoder.shouldEncodeElementDefault(serialDescriptor, 5) && itunesTrack.trackTimeMillis == null) {
                return;
            }
            compositeEncoder.encodeNullableSerializableElement(serialDescriptor, 5, LongSerializer.INSTANCE, itunesTrack.trackTimeMillis);
        }

        public final String getTrackName() {
            return this.trackName;
        }

        public final String getArtistName() {
            return this.artistName;
        }

        public final String getCollectionName() {
            return this.collectionName;
        }

        public final String getArtworkUrl100() {
            return this.artworkUrl100;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit jsonParser$lambda$0(JsonBuilder Json) {
        Intrinsics.checkNotNullParameter(Json, "$this$Json");
        Json.setIgnoreUnknownKeys(true);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00c2 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object fetchItunesTrack(java.lang.String r9, java.lang.String r10, kotlin.coroutines.Continuation r11) {
        /*
            Method dump skipped, instruction units count: 305
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.nowplaying.NowPlayingController.fetchItunesTrack(java.lang.String, java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final Object dbGetNowPlaying(long j, Continuation continuation) {
        final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        DatabaseHelper.getNowPlaying(j, new Consumer() { // from class: com.exteragram.messenger.nowplaying.NowPlayingController$dbGetNowPlaying$2$1
            public /* synthetic */ Consumer andThen(Consumer consumer) {
                return Consumer$CC.$default$andThen(this, consumer);
            }

            @Override // java.util.function.Consumer
            /* JADX INFO: renamed from: accept, reason: merged with bridge method [inline-methods] */
            public final void m940v(NowPlayingInfoDTO nowPlayingInfoDTO) {
                cancellableContinuationImpl.resumeWith(Result.m3604constructorimpl(nowPlayingInfoDTO));
            }
        });
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object dbUpdateNowPlaying(long j, NowPlayingInfoDTO nowPlayingInfoDTO, Continuation continuation) {
        final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        DatabaseHelper.updateNowPlaying(j, nowPlayingInfoDTO, new Consumer() { // from class: com.exteragram.messenger.nowplaying.NowPlayingController$dbUpdateNowPlaying$2$1
            public /* synthetic */ Consumer andThen(Consumer consumer) {
                return Consumer$CC.$default$andThen(this, consumer);
            }

            @Override // java.util.function.Consumer
            /* JADX INFO: renamed from: accept, reason: merged with bridge method [inline-methods] */
            public final void m940v(Integer updatedRows) {
                Intrinsics.checkNotNullParameter(updatedRows, "updatedRows");
                cancellableContinuationImpl.resumeWith(Result.m3604constructorimpl(updatedRows));
            }
        });
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }

    public final Object await(final Call call, Continuation continuation) {
        final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        cancellableContinuationImpl.invokeOnCancellation(new Function1() { // from class: com.exteragram.messenger.nowplaying.NowPlayingController$await$2$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((Throwable) obj);
                return Unit.INSTANCE;
            }

            public final void invoke(Throwable th) {
                call.cancel();
            }
        });
        call.enqueue(new Callback() { // from class: com.exteragram.messenger.nowplaying.NowPlayingController$await$2$2
            @Override // okhttp3.Callback
            public void onResponse(Call call2, Response response) {
                Intrinsics.checkNotNullParameter(call2, "call");
                Intrinsics.checkNotNullParameter(response, "response");
                cancellableContinuationImpl.resumeWith(Result.m3604constructorimpl(response));
            }

            @Override // okhttp3.Callback
            public void onFailure(Call call2, IOException e) {
                Intrinsics.checkNotNullParameter(call2, "call");
                Intrinsics.checkNotNullParameter(e, "e");
                if (cancellableContinuationImpl.isCancelled()) {
                    return;
                }
                CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
                Result.Companion companion = Result.Companion;
                cancellableContinuation.resumeWith(Result.m3604constructorimpl(ResultKt.createFailure(e)));
            }
        });
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }
}
