package com.exteragram.messenger.api;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.api.worker.SyncWorker;
import java.util.concurrent.TimeUnit;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.p029ui.Components.ForegroundDetector;
import p022j$.time.Duration;

/* JADX INFO: loaded from: classes.dex */
public final class ApiController {
    private static long lastBoostySyncTime;
    private static long lastExchangeRatesSyncTime;
    private static long lastProfilesSyncTime;
    public static final ApiController INSTANCE = new ApiController();
    private static final CoroutineScope scope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO());

    /* JADX INFO: renamed from: com.exteragram.messenger.api.ApiController$performSync$1 */
    @DebugMetadata(m1083c = "com.exteragram.messenger.api.ApiController", m1084f = "ApiController.kt", m1085l = {77, 79, 94, 96, 103, 106}, m1086m = "performSync", m1087v = 1)
    static final class C10371 extends ContinuationImpl {
        int I$0;
        long J$0;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        C10371(Continuation<? super C10371> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ApiController.this.performSync(null, false, this);
        }
    }

    public static final void sync() {
        sync$default(null, false, 3, null);
    }

    public static final void sync(SharedPreferences preferences) {
        Intrinsics.checkNotNullParameter(preferences, "preferences");
        sync$default(preferences, false, 2, null);
    }

    private ApiController() {
    }

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

    public static /* synthetic */ void sync$default(SharedPreferences preferences, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            preferences = ExteraConfig.preferences;
            Intrinsics.checkNotNullExpressionValue(preferences, "preferences");
        }
        if ((i & 2) != 0) {
            z = false;
        }
        sync(preferences, z);
    }

    public static final void sync(SharedPreferences preferences, boolean z) {
        Intrinsics.checkNotNullParameter(preferences, "preferences");
        long jCurrentTimeMillis = System.currentTimeMillis();
        boolean z2 = true;
        boolean z3 = z || jCurrentTimeMillis - lastProfilesSyncTime >= Duration.ofMinutes(8L).toMillis();
        boolean z4 = z || jCurrentTimeMillis - lastBoostySyncTime >= Duration.ofMinutes(40L).toMillis();
        if (!z && jCurrentTimeMillis - lastExchangeRatesSyncTime < Duration.ofHours(2L).toMillis()) {
            z2 = false;
        }
        if (z3 || z4 || z2) {
            BuildersKt__Builders_commonKt.launch$default(scope, null, null, new C10381(preferences, z, null), 3, null);
        }
    }

    /* JADX INFO: renamed from: com.exteragram.messenger.api.ApiController$sync$1 */
    @DebugMetadata(m1083c = "com.exteragram.messenger.api.ApiController$sync$1", m1084f = "ApiController.kt", m1085l = {66}, m1086m = "invokeSuspend", m1087v = 1)
    static final class C10381 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ boolean $force;
        final /* synthetic */ SharedPreferences $preferences;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C10381(SharedPreferences sharedPreferences, boolean z, Continuation<? super C10381> continuation) {
            super(2, continuation);
            this.$preferences = sharedPreferences;
            this.$force = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C10381(this.$preferences, this.$force, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C10381) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ApiController apiController = ApiController.INSTANCE;
                SharedPreferences sharedPreferences = this.$preferences;
                boolean z = this.$force;
                this.label = 1;
                if (apiController.performSync(sharedPreferences, z, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public static /* synthetic */ Object performSync$default(ApiController apiController, SharedPreferences preferences, boolean z, Continuation continuation, int i, Object obj) {
        if ((i & 1) != 0) {
            preferences = ExteraConfig.preferences;
            Intrinsics.checkNotNullExpressionValue(preferences, "preferences");
        }
        if ((i & 2) != 0) {
            z = false;
        }
        return apiController.performSync(preferences, z, continuation);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 7, insn: 0x00d4: MOVE (r19 I:??[long, double]) = (r7 I:??[long, double]), block:B:30:0x00d4 */
    /* JADX WARN: Not initialized variable reg: 9, insn: 0x00d8: MOVE (r5 I:??[OBJECT, ARRAY]) = (r9 I:??[OBJECT, ARRAY]), block:B:30:0x00d4 */
    /* JADX WARN: Removed duplicated region for block: B:113:0x02e9 A[Catch: all -> 0x024c, TryCatch #4 {all -> 0x024c, blocks: (B:120:0x032c, B:111:0x02e0, B:113:0x02e9, B:92:0x023e, B:94:0x0248, B:97:0x024f, B:99:0x0259, B:101:0x0279, B:102:0x028f, B:104:0x0295, B:105:0x02a7), top: B:137:0x023e }] */
    /* JADX WARN: Removed duplicated region for block: B:126:0x034b  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x034e  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0138 A[Catch: all -> 0x0165, TryCatch #1 {all -> 0x0165, blocks: (B:53:0x0162, B:46:0x0130, B:48:0x0138, B:50:0x013e), top: B:131:0x0130 }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0172 A[PHI: r1 r5 r7 r8
  0x0172: PHI (r1v3 ??) = (r1v27 ??), (r1v28 ??), (r1v29 ??), (r1v30 ??), (r1v31 ??) binds: [B:61:0x016f, B:39:0x010e, B:47:0x0136, B:49:0x013c, B:53:0x0162] A[DONT_GENERATE, DONT_INLINE]
  0x0172: PHI (r5v3 android.content.SharedPreferences) = 
  (r5v2 android.content.SharedPreferences)
  (r5v13 android.content.SharedPreferences)
  (r5v14 android.content.SharedPreferences)
  (r5v14 android.content.SharedPreferences)
  (r5v18 android.content.SharedPreferences)
 binds: [B:61:0x016f, B:39:0x010e, B:47:0x0136, B:49:0x013c, B:53:0x0162] A[DONT_GENERATE, DONT_INLINE]
  0x0172: PHI (r7v3 int) = (r7v2 int), (r7v27 int), (r7v28 int), (r7v28 int), (r7v32 int) binds: [B:61:0x016f, B:39:0x010e, B:47:0x0136, B:49:0x013c, B:53:0x0162] A[DONT_GENERATE, DONT_INLINE]
  0x0172: PHI (r8v2 long) = (r8v1 long), (r8v10 long), (r8v11 long), (r8v11 long), (r8v14 long) binds: [B:61:0x016f, B:39:0x010e, B:47:0x0136, B:49:0x013c, B:53:0x0162] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0198 A[Catch: all -> 0x005d, TryCatch #5 {all -> 0x005d, blocks: (B:13:0x0056, B:18:0x0084, B:21:0x009d, B:80:0x01f3, B:81:0x01f6, B:83:0x01fc, B:85:0x0202, B:87:0x0215, B:88:0x0226, B:90:0x022c, B:24:0x00b4, B:74:0x01c0, B:68:0x018c, B:70:0x0198, B:76:0x01cb), top: B:130:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01cb A[Catch: all -> 0x005d, TryCatch #5 {all -> 0x005d, blocks: (B:13:0x0056, B:18:0x0084, B:21:0x009d, B:80:0x01f3, B:81:0x01f6, B:83:0x01fc, B:85:0x0202, B:87:0x0215, B:88:0x0226, B:90:0x022c, B:24:0x00b4, B:74:0x01c0, B:68:0x018c, B:70:0x0198, B:76:0x01cb), top: B:130:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x01fc A[Catch: all -> 0x005d, TryCatch #5 {all -> 0x005d, blocks: (B:13:0x0056, B:18:0x0084, B:21:0x009d, B:80:0x01f3, B:81:0x01f6, B:83:0x01fc, B:85:0x0202, B:87:0x0215, B:88:0x0226, B:90:0x022c, B:24:0x00b4, B:74:0x01c0, B:68:0x018c, B:70:0x0198, B:76:0x01cb), top: B:130:0x002b }] */
    /* JADX WARN: Type inference failed for: r1v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v14, types: [boolean] */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v18 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v20 */
    /* JADX WARN: Type inference failed for: r1v26 */
    /* JADX WARN: Type inference failed for: r1v27 */
    /* JADX WARN: Type inference failed for: r1v28 */
    /* JADX WARN: Type inference failed for: r1v29 */
    /* JADX WARN: Type inference failed for: r1v3, types: [boolean] */
    /* JADX WARN: Type inference failed for: r1v30 */
    /* JADX WARN: Type inference failed for: r1v31 */
    /* JADX WARN: Type inference failed for: r1v32 */
    /* JADX WARN: Type inference failed for: r5v25 */
    /* JADX WARN: Type inference failed for: r5v26 */
    /* JADX WARN: Type inference failed for: r5v27 */
    /* JADX WARN: Type inference failed for: r5v28 */
    /* JADX WARN: Type inference failed for: r5v29 */
    /* JADX WARN: Type inference failed for: r5v30 */
    /* JADX WARN: Type inference failed for: r5v4 */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v7, types: [boolean] */
    /* JADX WARN: Type inference failed for: r5v8 */
    /* JADX WARN: Type inference failed for: r5v9, types: [boolean] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object performSync(android.content.SharedPreferences r22, boolean r23, kotlin.coroutines.Continuation<? super java.lang.Boolean> r24) {
        /*
            Method dump skipped, instruction units count: 870
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.api.ApiController.performSync(android.content.SharedPreferences, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static final void scheduleWorker() {
        PeriodicWorkRequest periodicWorkRequest = (PeriodicWorkRequest) ((PeriodicWorkRequest.Builder) new PeriodicWorkRequest.Builder(SyncWorker.class, 1L, TimeUnit.HOURS).setConstraints(new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())).build();
        WorkManager.Companion companion = WorkManager.Companion;
        Context applicationContext = ApplicationLoader.applicationContext;
        Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
        companion.getInstance(applicationContext).enqueueUniquePeriodicWork("api_sync_work", ExistingPeriodicWorkPolicy.UPDATE, periodicWorkRequest);
    }
}
