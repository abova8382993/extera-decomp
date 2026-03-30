package androidx.camera.camera2.pipe.graph;

import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import androidx.camera.camera2.pipe.AeMode;
import androidx.camera.camera2.pipe.AfMode;
import androidx.camera.camera2.pipe.AwbMode;
import androidx.camera.camera2.pipe.CameraMetadata;
import androidx.camera.camera2.pipe.FlashMode;
import androidx.camera.camera2.pipe.FrameMetadata;
import androidx.camera.camera2.pipe.Lock3ABehavior;
import androidx.camera.camera2.pipe.Result3A;
import androidx.camera.camera2.pipe.core.Log;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.JobKt__JobKt;

/* JADX INFO: loaded from: classes3.dex */
public final class Controller3A {
    private static final Map aePrecaptureAndAfCancelParams;
    private static final Map aePrecaptureCancelParams;
    private static final List aeUnlockedStateList;
    private static final List afUnlockedStateList;
    private static final List awbUnlockedStateList;
    private static final CompletableDeferred deferredResult3ASubmitFailed;
    private static final Map parameterForAfTriggerCancel;
    private static final Map parameterForAfTriggerStart;
    private static final Map parametersForAePrecapture;
    private static final Map parametersForAePrecaptureAndAfTrigger;
    private static final Function1 unlock3APostCaptureAfUnlockedCondition;
    private static final Map unlock3APostCaptureLockAeAndCancelAfParams;
    private static final Map unlock3APostCaptureLockAeParams;
    private static final Map unlock3APostCaptureUnlockAeParams;
    private final Listener3A graphListener3A;
    private final GraphProcessor graphProcessor;
    private final GraphState3A graphState3A;
    private Deferred lastUpdate3AResult;
    private final CameraMetadata metadata;
    public static final Companion Companion = new Companion(null);
    private static final List aeConvergedStateList = CollectionsKt.listOf((Object[]) new Integer[]{2, 4, 3});
    private static final List awbConvergedStateList = CollectionsKt.listOf((Object[]) new Integer[]{2, 3});
    private static final List afConvergedStateList = CollectionsKt.listOf((Object[]) new Integer[]{2, 6, 4, 5});
    private static final List aeLockedStateList = CollectionsKt.listOf((Object) 3);
    private static final List awbLockedStateList = CollectionsKt.listOf((Object) 3);
    private static final List afLockedStateList = CollectionsKt.listOf((Object[]) new Integer[]{4, 5});
    private static final List aePostPrecaptureStateList = CollectionsKt.listOf((Object[]) new Integer[]{2, 4, 3});
    private static final List awbPostPrecaptureStateList = CollectionsKt.listOf((Object[]) new Integer[]{2, 3});

    public Controller3A(GraphProcessor graphProcessor, CameraMetadata metadata, GraphState3A graphState3A, Listener3A graphListener3A) {
        Intrinsics.checkNotNullParameter(graphProcessor, "graphProcessor");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        Intrinsics.checkNotNullParameter(graphState3A, "graphState3A");
        Intrinsics.checkNotNullParameter(graphListener3A, "graphListener3A");
        this.graphProcessor = graphProcessor;
        this.metadata = metadata;
        this.graphState3A = graphState3A;
        this.graphListener3A = graphListener3A;
    }

    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        CaptureRequest.Key key = CaptureRequest.CONTROL_AF_TRIGGER;
        parameterForAfTriggerStart = MapsKt.mapOf(TuplesKt.m1081to(key, 1));
        parameterForAfTriggerCancel = MapsKt.mapOf(TuplesKt.m1081to(key, 2));
        CaptureRequest.Key key2 = CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER;
        parametersForAePrecapture = MapsKt.mapOf(TuplesKt.m1081to(key2, 1));
        parametersForAePrecaptureAndAfTrigger = MapsKt.mapOf(TuplesKt.m1081to(key, 1), TuplesKt.m1081to(key2, 1));
        deferredResult3ASubmitFailed = CompletableDeferredKt.CompletableDeferred(new Result3A(Result3A.Status.Companion.m1769getSUBMIT_FAILEDJvTi9ms(), null, 2, null));
        aeUnlockedStateList = CollectionsKt.listOf((Object[]) new Integer[]{0, 1, 2, 4});
        List listListOf = CollectionsKt.listOf((Object[]) new Integer[]{0, 3, 1, 2, 6});
        afUnlockedStateList = listListOf;
        awbUnlockedStateList = CollectionsKt.listOf((Object[]) new Integer[]{0, 1, 2});
        CaptureRequest.Key key3 = CaptureRequest.CONTROL_AE_LOCK;
        Boolean bool = Boolean.TRUE;
        unlock3APostCaptureLockAeParams = MapsKt.mapOf(TuplesKt.m1081to(key3, bool));
        unlock3APostCaptureLockAeAndCancelAfParams = MapsKt.mapOf(TuplesKt.m1081to(key, 2), TuplesKt.m1081to(key3, bool));
        unlock3APostCaptureUnlockAeParams = MapsKt.mapOf(TuplesKt.m1081to(key3, Boolean.FALSE));
        aePrecaptureCancelParams = MapsKt.mapOf(TuplesKt.m1081to(key2, 2));
        aePrecaptureAndAfCancelParams = MapsKt.mapOf(TuplesKt.m1081to(key, 2), TuplesKt.m1081to(key2, 2));
        unlock3APostCaptureAfUnlockedCondition = Result3AStateListenerKt.toConditionChecker(MapsKt.mapOf(TuplesKt.m1081to(CaptureResult.CONTROL_AF_STATE, listListOf)));
    }

    /* JADX INFO: renamed from: update3A-169HPGg$default, reason: not valid java name */
    public static /* synthetic */ Deferred m1899update3A169HPGg$default(Controller3A controller3A, AeMode aeMode, AfMode afMode, AwbMode awbMode, FlashMode flashMode, List list, List list2, List list3, int i, Object obj) {
        if ((i & 1) != 0) {
            aeMode = null;
        }
        if ((i & 2) != 0) {
            afMode = null;
        }
        if ((i & 4) != 0) {
            awbMode = null;
        }
        if ((i & 8) != 0) {
            flashMode = null;
        }
        if ((i & 16) != 0) {
            list = null;
        }
        if ((i & 32) != 0) {
            list2 = null;
        }
        if ((i & 64) != 0) {
            list3 = null;
        }
        return controller3A.m1902update3A169HPGg(aeMode, afMode, awbMode, flashMode, list, list2, list3);
    }

    /* JADX INFO: renamed from: update3A-169HPGg, reason: not valid java name */
    public final Deferred m1902update3A169HPGg(AeMode aeMode, AfMode afMode, AwbMode awbMode, FlashMode flashMode, List list, List list2, List list3) {
        if (this.graphProcessor.getRepeatingRequest() == null) {
            GraphState3A.m1909update7jOEVJU$default(this.graphState3A, aeMode, afMode, awbMode, flashMode, list, list2, list3, null, null, null, 896, null);
            this.graphProcessor.update3AParameters(this.graphState3A.toCaptureRequestParametersMap());
            return deferredResult3ASubmitFailed;
        }
        Result3AStateListenerImpl result3AStateListenerImplM1897createListenerFor3AParams0dPwJB0 = m1897createListenerFor3AParams0dPwJB0(aeMode, afMode, awbMode, flashMode);
        this.graphListener3A.addListener(result3AStateListenerImplM1897createListenerFor3AParams0dPwJB0);
        GraphState3A.m1909update7jOEVJU$default(this.graphState3A, aeMode, afMode, awbMode, flashMode, list, list2, list3, null, null, null, 896, null);
        this.graphProcessor.update3AParameters(this.graphState3A.toCaptureRequestParametersMap());
        Deferred result = result3AStateListenerImplM1897createListenerFor3AParams0dPwJB0.getResult();
        synchronized (this) {
            try {
                if (Log.INSTANCE.getDEBUG_LOGGABLE()) {
                    android.util.Log.d("CXCP", "Controller3A#update3A: cancelling previous request " + this.lastUpdate3AResult);
                }
                Deferred deferred = this.lastUpdate3AResult;
                if (deferred != null) {
                    JobKt__JobKt.cancel$default(deferred, "A newer call for 3A state update initiated.", null, 2, null);
                }
                this.lastUpdate3AResult = result;
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
        return result;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x001b  */
    /* JADX INFO: renamed from: lock3A-Qz1gx5w, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object m1900lock3AQz1gx5w(java.util.List r26, java.util.List r27, java.util.List r28, androidx.camera.camera2.pipe.Lock3ABehavior r29, androidx.camera.camera2.pipe.Lock3ABehavior r30, androidx.camera.camera2.pipe.Lock3ABehavior r31, androidx.camera.camera2.pipe.AeMode r32, kotlin.jvm.functions.Function1 r33, kotlin.jvm.functions.Function1 r34, int r35, java.lang.Long r36, java.lang.Long r37, kotlin.coroutines.Continuation r38) {
        /*
            Method dump skipped, instruction units count: 634
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.graph.Controller3A.m1900lock3AQz1gx5w(java.util.List, java.util.List, java.util.List, androidx.camera.camera2.pipe.Lock3ABehavior, androidx.camera.camera2.pipe.Lock3ABehavior, androidx.camera.camera2.pipe.Lock3ABehavior, androidx.camera.camera2.pipe.AeMode, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, int, java.lang.Long, java.lang.Long, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final Deferred unlock3A(Boolean bool, Boolean bool2, Boolean bool3, Function1 function1, int i, Long l) {
        Boolean bool4 = !CameraMetadata.Companion.getSupportsAutoFocusTrigger(this.metadata) ? null : bool2;
        Boolean bool5 = Boolean.TRUE;
        if (!Intrinsics.areEqual(bool, bool5) && !Intrinsics.areEqual(bool4, bool5) && !Intrinsics.areEqual(bool3, bool5)) {
            return CompletableDeferredKt.CompletableDeferred(new Result3A(Result3A.Status.Companion.m1767getOKJvTi9ms(), null, null));
        }
        if (this.graphProcessor.getRepeatingRequest() == null) {
            return deferredResult3ASubmitFailed;
        }
        if (Intrinsics.areEqual(bool4, bool5)) {
            Log log = Log.INSTANCE;
            if (log.getDEBUG_LOGGABLE()) {
                android.util.Log.d("CXCP", "unlock3A - sending a request to unlock af first.");
            }
            if (!this.graphProcessor.trigger(parameterForAfTriggerCancel)) {
                if (log.getDEBUG_LOGGABLE()) {
                    android.util.Log.d("CXCP", "unlock3A - failed to send a request to unlock af first.");
                }
                return deferredResult3ASubmitFailed;
            }
            GraphState3A.m1909update7jOEVJU$default(this.graphState3A, null, null, null, null, null, null, null, null, Boolean.FALSE, null, 767, null);
        }
        Result3AStateListenerImpl result3AStateListenerImpl = new Result3AStateListenerImpl(function1 == null ? Result3AStateListenerKt.toConditionChecker(createUnLocked3AExitConditions(Intrinsics.areEqual(bool, bool5), Intrinsics.areEqual(bool4, bool5), Intrinsics.areEqual(bool3, bool5))) : function1, Integer.valueOf(i), l);
        this.graphListener3A.addListener(result3AStateListenerImpl);
        Boolean bool6 = Intrinsics.areEqual(bool, bool5) ? Boolean.FALSE : null;
        Boolean bool7 = Intrinsics.areEqual(bool3, bool5) ? Boolean.FALSE : null;
        if (bool6 != null || bool7 != null) {
            if (Log.INSTANCE.getDEBUG_LOGGABLE()) {
                android.util.Log.d("CXCP", "unlock3A - updating graph state, aeLock=" + bool6 + ", awbLock=" + bool7);
            }
            GraphState3A.m1909update7jOEVJU$default(this.graphState3A, null, null, null, null, null, null, null, bool6, null, bool7, 383, null);
        }
        this.graphProcessor.update3AParameters(this.graphState3A.toCaptureRequestParametersMap());
        return result3AStateListenerImpl.getResult();
    }

    public final Deferred lock3AForCapture(boolean z, boolean z2, int i, long j) {
        Map map;
        if (z) {
            map = parametersForAePrecaptureAndAfTrigger;
        } else {
            map = parametersForAePrecapture;
        }
        return lock3AForCapture(map, createLock3AForCaptureExitConditions(z, z2), i, j);
    }

    private final Deferred lock3AForCapture(Map map, Function1 function1, int i, long j) {
        if (this.graphProcessor.getRepeatingRequest() != null) {
            if (map == null) {
                map = parametersForAePrecaptureAndAfTrigger;
            }
            Iterator it = map.entrySet().iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (Intrinsics.areEqual(((Map.Entry) it.next()).getValue(), 1)) {
                    z = true;
                }
            }
            if (function1 == null) {
                function1 = createLock3AForCaptureExitConditions(z, false);
            }
            Result3AStateListenerImpl result3AStateListenerImpl = new Result3AStateListenerImpl(function1, Integer.valueOf(i), Long.valueOf(j));
            this.graphListener3A.addListener(result3AStateListenerImpl);
            if (Log.INSTANCE.getDEBUG_LOGGABLE()) {
                android.util.Log.d("CXCP", "lock3AForCapture - sending a request to trigger ae precapture metering and af.");
            }
            if (!this.graphProcessor.trigger(map)) {
                this.graphListener3A.removeListener(result3AStateListenerImpl);
                return deferredResult3ASubmitFailed;
            }
            this.graphProcessor.update3AParameters(this.graphState3A.toCaptureRequestParametersMap());
            return result3AStateListenerImpl.getResult();
        }
        return deferredResult3ASubmitFailed;
    }

    public final Deferred unlock3APostCapture(boolean z) {
        if (this.graphProcessor.getRepeatingRequest() == null) {
            return deferredResult3ASubmitFailed;
        }
        return unlock3APostCaptureAndroidMAndAbove(z);
    }

    private final Deferred unlock3APostCaptureAndroidMAndAbove(boolean z) {
        Result3AStateListenerImpl result3AStateListenerImpl;
        if (Log.INSTANCE.getDEBUG_LOGGABLE()) {
            android.util.Log.d("CXCP", "unlock3APostCapture - sending a request to reset af and ae precapture metering.");
        }
        if (!this.graphProcessor.trigger(z ? aePrecaptureAndAfCancelParams : aePrecaptureCancelParams)) {
            return deferredResult3ASubmitFailed;
        }
        if (z) {
            result3AStateListenerImpl = new Result3AStateListenerImpl(unlock3APostCaptureAfUnlockedCondition, (Integer) null, (Long) null, 6, (DefaultConstructorMarker) null);
        } else {
            result3AStateListenerImpl = new Result3AStateListenerImpl(MapsKt.emptyMap(), (Integer) null, (Long) null, 6, (DefaultConstructorMarker) null);
        }
        this.graphListener3A.addListener(result3AStateListenerImpl);
        this.graphProcessor.update3AParameters(this.graphState3A.toCaptureRequestParametersMap());
        return result3AStateListenerImpl.getResult();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x003b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final kotlinx.coroutines.Deferred setTorchOn() {
        /*
            r11 = this;
            androidx.camera.camera2.pipe.graph.GraphState3A r0 = r11.graphState3A
            androidx.camera.camera2.pipe.graph.State3A r0 = r0.getCurrent()
            androidx.camera.camera2.pipe.AeMode r0 = r0.m1917getAeModeO_cDUUs()
            androidx.camera.camera2.pipe.AeMode$Companion r1 = androidx.camera.camera2.pipe.AeMode.Companion
            int r2 = r1.m1494getONbOjpiJc()
            r3 = 0
            if (r0 != 0) goto L15
            r2 = r3
            goto L1d
        L15:
            int r4 = r0.m1491unboximpl()
            boolean r2 = androidx.camera.camera2.pipe.AeMode.m1487equalsimpl0(r4, r2)
        L1d:
            if (r2 != 0) goto L3b
            int r2 = r1.m1493getOFFbOjpiJc()
            if (r0 != 0) goto L26
            goto L2e
        L26:
            int r0 = r0.m1491unboximpl()
            boolean r3 = androidx.camera.camera2.pipe.AeMode.m1487equalsimpl0(r0, r2)
        L2e:
            if (r3 == 0) goto L31
            goto L3b
        L31:
            int r0 = r1.m1494getONbOjpiJc()
            androidx.camera.camera2.pipe.AeMode r0 = androidx.camera.camera2.pipe.AeMode.m1484boximpl(r0)
        L39:
            r2 = r0
            goto L3d
        L3b:
            r0 = 0
            goto L39
        L3d:
            androidx.camera.camera2.pipe.FlashMode$Companion r0 = androidx.camera.camera2.pipe.FlashMode.Companion
            int r0 = r0.m1637getTORCHLe5xUZU()
            androidx.camera.camera2.pipe.FlashMode r5 = androidx.camera.camera2.pipe.FlashMode.m1630boximpl(r0)
            r9 = 118(0x76, float:1.65E-43)
            r10 = 0
            r3 = 0
            r4 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r1 = r11
            kotlinx.coroutines.Deferred r0 = m1899update3A169HPGg$default(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.graph.Controller3A.setTorchOn():kotlinx.coroutines.Deferred");
    }

    /* JADX INFO: renamed from: setTorchOff-NqN7i0k, reason: not valid java name */
    public final Deferred m1901setTorchOffNqN7i0k(AeMode aeMode) {
        return m1899update3A169HPGg$default(this, aeMode, null, null, FlashMode.m1630boximpl(FlashMode.Companion.m1636getOFFLe5xUZU()), null, null, null, 118, null);
    }

    /* JADX INFO: renamed from: lock3ANow-R6AlCjU, reason: not valid java name */
    private final Deferred m1898lock3ANowR6AlCjU(Lock3ABehavior lock3ABehavior, Lock3ABehavior lock3ABehavior2, Lock3ABehavior lock3ABehavior3, AeMode aeMode, Function1 function1, Integer num, Long l) {
        String str;
        Deferred result;
        AeMode aeMode2 = null;
        Boolean bool = lock3ABehavior == null ? null : Boolean.TRUE;
        Boolean bool2 = lock3ABehavior3 == null ? null : Boolean.TRUE;
        Map mapCreateLocked3AExitConditions = createLocked3AExitConditions(bool != null, lock3ABehavior2 != null, bool2 != null);
        if (function1 == null && mapCreateLocked3AExitConditions.isEmpty()) {
            str = "CXCP";
            result = null;
        } else {
            Result3AStateListenerImpl result3AStateListenerImpl = new Result3AStateListenerImpl(function1 == null ? Result3AStateListenerKt.toConditionChecker(mapCreateLocked3AExitConditions) : function1, num, l);
            this.graphListener3A.addListener(result3AStateListenerImpl);
            str = "CXCP";
            GraphState3A.m1909update7jOEVJU$default(this.graphState3A, null, null, null, null, null, null, null, bool, null, bool2, 383, null);
            if (Log.INSTANCE.getDEBUG_LOGGABLE()) {
                android.util.Log.d(str, "lock3A - submitting request with aeLock=" + bool + " , awbLock=" + bool2);
            }
            this.graphProcessor.update3AParameters(this.graphState3A.toCaptureRequestParametersMap());
            result = result3AStateListenerImpl.getResult();
        }
        if (lock3ABehavior2 == null) {
            Intrinsics.checkNotNull(result);
            return result;
        }
        if (aeMode != null) {
            int iM1491unboximpl = aeMode.m1491unboximpl();
            AeMode aeModeM1917getAeModeO_cDUUs = this.graphState3A.getCurrent().m1917getAeModeO_cDUUs();
            GraphState3A.m1909update7jOEVJU$default(this.graphState3A, AeMode.m1484boximpl(iM1491unboximpl), null, null, null, null, null, null, null, null, null, 1022, null);
            this.graphProcessor.update3AParameters(this.graphState3A.toCaptureRequestParametersMap());
            aeMode2 = aeModeM1917getAeModeO_cDUUs;
        }
        if (Log.INSTANCE.getDEBUG_LOGGABLE()) {
            android.util.Log.d(str, "lock3A - submitting a request to lock af.");
        }
        if (!this.graphProcessor.trigger(parameterForAfTriggerStart)) {
            return deferredResult3ASubmitFailed;
        }
        GraphState3A.m1909update7jOEVJU$default(this.graphState3A, null, null, null, null, null, null, null, null, Boolean.TRUE, null, 767, null);
        if (aeMode2 != null) {
            GraphState3A.m1909update7jOEVJU$default(this.graphState3A, AeMode.m1484boximpl(aeMode2.m1491unboximpl()), null, null, null, null, null, null, null, null, null, 1022, null);
            this.graphProcessor.update3AParameters(this.graphState3A.toCaptureRequestParametersMap());
        }
        Intrinsics.checkNotNull(result);
        return result;
    }

    private final Map createConverged3AExitConditions(boolean z, boolean z2, boolean z3) {
        if (!z && !z2 && !z3) {
            return MapsKt.emptyMap();
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (z) {
            linkedHashMap.put(CaptureResult.CONTROL_AE_STATE, aeConvergedStateList);
        }
        if (z3) {
            linkedHashMap.put(CaptureResult.CONTROL_AWB_STATE, awbConvergedStateList);
        }
        if (z2) {
            linkedHashMap.put(CaptureResult.CONTROL_AF_STATE, afConvergedStateList);
        }
        return linkedHashMap;
    }

    private final Map createLocked3AExitConditions(boolean z, boolean z2, boolean z3) {
        if (!z && !z2 && !z3) {
            return MapsKt.emptyMap();
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (z) {
            linkedHashMap.put(CaptureResult.CONTROL_AE_STATE, aeLockedStateList);
        }
        if (z2) {
            linkedHashMap.put(CaptureResult.CONTROL_AF_STATE, afLockedStateList);
        }
        if (z3) {
            linkedHashMap.put(CaptureResult.CONTROL_AWB_STATE, awbLockedStateList);
        }
        return linkedHashMap;
    }

    private final Function1 createLock3AForCaptureExitConditions(final boolean z, final boolean z2) {
        return new Function1() { // from class: androidx.camera.camera2.pipe.graph.Controller3A$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(Controller3A.createLock3AForCaptureExitConditions$lambda$0(z2, z, (FrameMetadata) obj));
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final boolean createLock3AForCaptureExitConditions$lambda$0(boolean r6, boolean r7, androidx.camera.camera2.pipe.FrameMetadata r8) {
        /*
            Method dump skipped, instruction units count: 268
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.graph.Controller3A.createLock3AForCaptureExitConditions$lambda$0(boolean, boolean, androidx.camera.camera2.pipe.FrameMetadata):boolean");
    }

    private final Map createUnLocked3AExitConditions(boolean z, boolean z2, boolean z3) {
        if (!z && !z2 && !z3) {
            return MapsKt.emptyMap();
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (z) {
            linkedHashMap.put(CaptureResult.CONTROL_AE_STATE, aeUnlockedStateList);
        }
        if (z2) {
            linkedHashMap.put(CaptureResult.CONTROL_AF_STATE, afUnlockedStateList);
        }
        if (z3) {
            linkedHashMap.put(CaptureResult.CONTROL_AWB_STATE, awbUnlockedStateList);
        }
        return linkedHashMap;
    }

    /* JADX INFO: renamed from: createListenerFor3AParams-0dPwJB0, reason: not valid java name */
    private final Result3AStateListenerImpl m1897createListenerFor3AParams0dPwJB0(AeMode aeMode, AfMode afMode, AwbMode awbMode, FlashMode flashMode) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (aeMode != null) {
            int iM1491unboximpl = aeMode.m1491unboximpl();
            CaptureResult.Key CONTROL_AE_MODE = CaptureResult.CONTROL_AE_MODE;
            Intrinsics.checkNotNullExpressionValue(CONTROL_AE_MODE, "CONTROL_AE_MODE");
        }
        if (afMode != null) {
            int iM1502unboximpl = afMode.m1502unboximpl();
            CaptureResult.Key CONTROL_AF_MODE = CaptureResult.CONTROL_AF_MODE;
            Intrinsics.checkNotNullExpressionValue(CONTROL_AF_MODE, "CONTROL_AF_MODE");
        }
        if (awbMode != null) {
            int iM1521unboximpl = awbMode.m1521unboximpl();
            CaptureResult.Key CONTROL_AWB_MODE = CaptureResult.CONTROL_AWB_MODE;
            Intrinsics.checkNotNullExpressionValue(CONTROL_AWB_MODE, "CONTROL_AWB_MODE");
        }
        if (flashMode != null) {
            int iM1635unboximpl = flashMode.m1635unboximpl();
            CaptureResult.Key FLASH_MODE = CaptureResult.FLASH_MODE;
            Intrinsics.checkNotNullExpressionValue(FLASH_MODE, "FLASH_MODE");
        }
        return new Result3AStateListenerImpl(MapsKt.toMap(linkedHashMap), (Integer) null, (Long) null, 6, (DefaultConstructorMarker) null);
    }
}
