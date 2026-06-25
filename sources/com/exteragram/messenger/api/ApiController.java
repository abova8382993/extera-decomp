package com.exteragram.messenger.api;

import android.content.SharedPreferences;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.api.worker.SyncWorker;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import okhttp3.internal.url._UrlKt;
import okio.Segment$$ExternalSyntheticBUOutline1;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.p035ui.Components.ForegroundDetector;
import p026j$.time.Duration;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0006\u001a\u00020\u0007H\u0007J\u001c\u0010\f\u001a\u00020\u00072\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0007J\"\u0010\u0011\u001a\u00020\u00102\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0086@¢\u0006\u0002\u0010\u0012J\b\u0010\u0013\u001a\u00020\u0007H\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0014"}, m877d2 = {"Lcom/exteragram/messenger/api/ApiController;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "scope", "Lkotlinx/coroutines/CoroutineScope;", "init", _UrlKt.FRAGMENT_ENCODE_SET, "lastProfilesSyncTime", _UrlKt.FRAGMENT_ENCODE_SET, "lastBoostySyncTime", "lastExchangeRatesSyncTime", "sync", "prefs", "Landroid/content/SharedPreferences;", "force", _UrlKt.FRAGMENT_ENCODE_SET, "performSync", "(Landroid/content/SharedPreferences;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "scheduleWorker", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nApiController.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ApiController.kt\ncom/exteragram/messenger/api/ApiController\n+ 2 SharedPreferences.kt\nandroidx/core/content/SharedPreferencesKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 5 PeriodicWorkRequest.kt\nandroidx/work/PeriodicWorkRequestKt\n*L\n1#1,148:1\n41#2,12:149\n41#2,6:175\n47#2,6:182\n3347#3,10:161\n1586#3:171\n1661#3,3:172\n1#4:181\n364#5:188\n*S KotlinDebug\n*F\n+ 1 ApiController.kt\ncom/exteragram/messenger/api/ApiController\n*L\n101#1:149,12\n114#1:175,6\n114#1:182,6\n106#1:161,10\n108#1:171\n108#1:172,3\n138#1:188\n*E\n"})
public final class ApiController {
    private static long lastBoostySyncTime;
    private static long lastExchangeRatesSyncTime;
    private static long lastProfilesSyncTime;
    public static final ApiController INSTANCE = new ApiController();
    private static final CoroutineScope scope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO());

    /* JADX INFO: renamed from: com.exteragram.messenger.api.ApiController$performSync$1 */
    @Metadata(m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
    @DebugMetadata(m895c = "com.exteragram.messenger.api.ApiController", m896f = "ApiController.kt", m897i = {0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5}, m898l = {77, 79, 95, 97, 108, 111}, m899m = "performSync", m900n = {"prefs", "force", "now", "success", "prefs", "boostyResponse", "force", "now", "success", "prefs", "lastSyncTimestamp", "profilesEtag", "apiService", "force", "now", "success", "prefs", "lastSyncTimestamp", "profilesEtag", "apiService", "force", "now", "success", "prefs", "lastSyncTimestamp", "profilesEtag", "apiService", "response", "profiles", "profilesToDelete", "profilesToInsert", "force", "now", "success", "prefs", "lastSyncTimestamp", "profilesEtag", "apiService", "response", "profiles", "profilesToDelete", "profilesToInsert", "force", "now", "success"}, m902s = {"L$0", "Z$0", "J$0", "I$0", "L$0", "L$1", "Z$0", "J$0", "I$0", "L$0", "L$1", "L$2", "L$3", "Z$0", "J$0", "I$0", "L$0", "L$1", "L$2", "L$3", "Z$0", "J$0", "I$0", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "L$7", "Z$0", "J$0", "I$0", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "L$7", "Z$0", "J$0", "I$0"}, m903v = 1)
    public static final class C10511 extends ContinuationImpl {
        int I$0;
        long J$0;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        Object L$7;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        public C10511(Continuation<? super C10511> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ApiController.this.performSync(null, false, this);
        }
    }

    @JvmStatic
    @JvmOverloads
    public static final void sync() {
        sync$default(null, false, 3, null);
    }

    @JvmStatic
    @JvmOverloads
    public static final void sync(SharedPreferences sharedPreferences) {
        sync$default(sharedPreferences, false, 2, null);
    }

    private ApiController() {
    }

    @JvmStatic
    public static final void init() {
        sync$default(null, false, 3, null);
        scheduleWorker();
        ForegroundDetector.getInstance().addListener(new ForegroundDetector.Listener() { // from class: com.exteragram.messenger.api.ApiController.init.1
            @Override // org.telegram.ui.Components.ForegroundDetector.Listener
            public void onBecameBackground() {
            }

            @Override // org.telegram.ui.Components.ForegroundDetector.Listener
            public void onBecameForeground() {
                ApiController.sync$default(null, false, 3, null);
            }
        });
    }

    public static /* synthetic */ void sync$default(SharedPreferences sharedPreferences, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            sharedPreferences = ExteraConfig.getPreferences();
        }
        if ((i & 2) != 0) {
            z = false;
        }
        sync(sharedPreferences, z);
    }

    @JvmStatic
    @JvmOverloads
    public static final void sync(SharedPreferences prefs, boolean force) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        boolean z = true;
        boolean z2 = force || jCurrentTimeMillis - lastProfilesSyncTime >= Duration.ofMinutes(8L).toMillis();
        boolean z3 = force || jCurrentTimeMillis - lastBoostySyncTime >= Duration.ofMinutes(40L).toMillis();
        if (!force && jCurrentTimeMillis - lastExchangeRatesSyncTime < Duration.ofHours(2L).toMillis()) {
            z = false;
        }
        if (z2 || z3 || z) {
            BuildersKt__Builders_commonKt.launch$default(scope, null, null, new C10521(prefs, force, null), 3, null);
        }
    }

    /* JADX INFO: renamed from: com.exteragram.messenger.api.ApiController$sync$1 */
    @Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
    @DebugMetadata(m895c = "com.exteragram.messenger.api.ApiController$sync$1", m896f = "ApiController.kt", m897i = {}, m898l = {66}, m899m = "invokeSuspend", m900n = {}, m902s = {}, m903v = 1)
    public static final class C10521 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ boolean $force;
        final /* synthetic */ SharedPreferences $prefs;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10521(SharedPreferences sharedPreferences, boolean z, Continuation<? super C10521> continuation) {
            super(2, continuation);
            this.$prefs = sharedPreferences;
            this.$force = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C10521(this.$prefs, this.$force, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C10521) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ApiController apiController = ApiController.INSTANCE;
                SharedPreferences sharedPreferences = this.$prefs;
                boolean z = this.$force;
                this.label = 1;
                if (apiController.performSync(sharedPreferences, z, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    Segment$$ExternalSyntheticBUOutline1.m992m("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public static /* synthetic */ Object performSync$default(ApiController apiController, SharedPreferences sharedPreferences, boolean z, Continuation continuation, int i, Object obj) {
        if ((i & 1) != 0) {
            sharedPreferences = ExteraConfig.getPreferences();
        }
        if ((i & 2) != 0) {
            z = false;
        }
        return apiController.performSync(sharedPreferences, z, continuation);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 7, insn: 0x00f8: MOVE (r20 I:??[OBJECT, ARRAY]) = (r7 I:??[OBJECT, ARRAY]), block:B:30:0x00f8 */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0348 A[Catch: all -> 0x029f, TryCatch #5 {all -> 0x029f, blocks: (B:121:0x0391, B:123:0x03a2, B:125:0x03ae, B:126:0x03b1, B:112:0x033e, B:114:0x0348, B:94:0x0291, B:96:0x029b, B:99:0x02a2, B:101:0x02ac, B:103:0x02cf, B:104:0x02e5, B:106:0x02eb, B:107:0x02fd), top: B:143:0x0291 }] */
    /* JADX WARN: Removed duplicated region for block: B:119:0x038c  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x03a2 A[Catch: all -> 0x029f, TryCatch #5 {all -> 0x029f, blocks: (B:121:0x0391, B:123:0x03a2, B:125:0x03ae, B:126:0x03b1, B:112:0x033e, B:114:0x0348, B:94:0x0291, B:96:0x029b, B:99:0x02a2, B:101:0x02ac, B:103:0x02cf, B:104:0x02e5, B:106:0x02eb, B:107:0x02fd), top: B:143:0x0291 }] */
    /* JADX WARN: Removed duplicated region for block: B:132:0x03c2  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x03c5  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x015d A[Catch: all -> 0x0188, TryCatch #2 {all -> 0x0188, blocks: (B:53:0x0184, B:46:0x0155, B:48:0x015d, B:50:0x0163), top: B:137:0x0155 }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01be A[Catch: all -> 0x0061, TryCatch #0 {all -> 0x0061, blocks: (B:13:0x005a, B:18:0x008e, B:21:0x00ad, B:79:0x0226, B:80:0x0229, B:82:0x0231, B:83:0x0248, B:85:0x024e, B:87:0x0254, B:89:0x0264, B:90:0x0277, B:92:0x027d, B:24:0x00d0, B:73:0x01ec, B:67:0x01ae, B:69:0x01be, B:75:0x01f8), top: B:136:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01f8 A[Catch: all -> 0x0061, TryCatch #0 {all -> 0x0061, blocks: (B:13:0x005a, B:18:0x008e, B:21:0x00ad, B:79:0x0226, B:80:0x0229, B:82:0x0231, B:83:0x0248, B:85:0x024e, B:87:0x0254, B:89:0x0264, B:90:0x0277, B:92:0x027d, B:24:0x00d0, B:73:0x01ec, B:67:0x01ae, B:69:0x01be, B:75:0x01f8), top: B:136:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0231 A[Catch: all -> 0x0061, TryCatch #0 {all -> 0x0061, blocks: (B:13:0x005a, B:18:0x008e, B:21:0x00ad, B:79:0x0226, B:80:0x0229, B:82:0x0231, B:83:0x0248, B:85:0x024e, B:87:0x0254, B:89:0x0264, B:90:0x0277, B:92:0x027d, B:24:0x00d0, B:73:0x01ec, B:67:0x01ae, B:69:0x01be, B:75:0x01f8), top: B:136:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0248 A[Catch: all -> 0x0061, TryCatch #0 {all -> 0x0061, blocks: (B:13:0x005a, B:18:0x008e, B:21:0x00ad, B:79:0x0226, B:80:0x0229, B:82:0x0231, B:83:0x0248, B:85:0x024e, B:87:0x0254, B:89:0x0264, B:90:0x0277, B:92:0x027d, B:24:0x00d0, B:73:0x01ec, B:67:0x01ae, B:69:0x01be, B:75:0x01f8), top: B:136:0x002d }] */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v28 */
    /* JADX WARN: Type inference failed for: r1v29 */
    /* JADX WARN: Type inference failed for: r1v30 */
    /* JADX WARN: Type inference failed for: r1v31 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object performSync(android.content.SharedPreferences r23, boolean r24, kotlin.coroutines.Continuation<? super java.lang.Boolean> r25) {
        /*
            Method dump skipped, instruction units count: 990
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.api.ApiController.performSync(android.content.SharedPreferences, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @JvmStatic
    public static final void scheduleWorker() {
        WorkManager.INSTANCE.getInstance(ApplicationLoader.applicationContext).enqueueUniquePeriodicWork("api_sync_work", ExistingPeriodicWorkPolicy.UPDATE, new PeriodicWorkRequest.Builder(SyncWorker.class, 1L, TimeUnit.HOURS).setConstraints(new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()).build());
    }
}
