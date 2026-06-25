package androidx.camera.camera2.pipe.compat;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", "Landroidx/camera/camera2/pipe/compat/OpenCameraResult;", "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
@DebugMetadata(m895c = "androidx.camera.camera2.pipe.compat.CameraStateOpener$tryOpenCamera$2", m896f = "RetryingCameraStateOpener.kt", m897i = {0, 0, 0, 0, 0}, m898l = {670}, m899m = "invokeSuspend", m900n = {"$this$supervisorScope", "cameraOpenDeferred", "resultDeferred", "timeoutJob", "cameraOpenCancelJob"}, m902s = {"L$0", "L$1", "L$2", "L$3", "L$4"}, m903v = 1)
@SourceDebugExtension({"SMAP\nRetryingCameraStateOpener.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RetryingCameraStateOpener.kt\nandroidx/camera/camera2/pipe/compat/CameraStateOpener$tryOpenCamera$2\n+ 2 Select.kt\nkotlinx/coroutines/selects/SelectKt\n+ 3 Log.kt\nandroidx/camera/camera2/pipe/core/Log\n*L\n1#1,665:1\n54#2,5:666\n59#3,2:671\n86#3,2:673\n*S KotlinDebug\n*F\n+ 1 RetryingCameraStateOpener.kt\nandroidx/camera/camera2/pipe/compat/CameraStateOpener$tryOpenCamera$2\n*L\n324#1:666,5\n358#1:671,2\n368#1:673,2\n*E\n"})
public final class CameraStateOpener$tryOpenCamera$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super OpenCameraResult>, Object> {
    final /* synthetic */ String $cameraId;
    final /* synthetic */ AndroidCameraState $cameraState;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    int label;
    final /* synthetic */ CameraStateOpener this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CameraStateOpener$tryOpenCamera$2(CameraStateOpener cameraStateOpener, String str, AndroidCameraState androidCameraState, Continuation<? super CameraStateOpener$tryOpenCamera$2> continuation) {
        super(2, continuation);
        this.this$0 = cameraStateOpener;
        this.$cameraId = str;
        this.$cameraState = androidCameraState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        CameraStateOpener$tryOpenCamera$2 cameraStateOpener$tryOpenCamera$2 = new CameraStateOpener$tryOpenCamera$2(this.this$0, this.$cameraId, this.$cameraState, continuation);
        cameraStateOpener$tryOpenCamera$2.L$0 = obj;
        return cameraStateOpener$tryOpenCamera$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super OpenCameraResult> continuation) {
        return ((CameraStateOpener$tryOpenCamera$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:109:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0095 A[Catch: all -> 0x0027, TRY_ENTER, TryCatch #0 {all -> 0x0027, blocks: (B:63:0x0022, B:87:0x00fd, B:72:0x0095, B:74:0x00a8, B:75:0x00b4, B:77:0x00ba, B:78:0x00c6, B:80:0x00cc, B:81:0x00d8, B:83:0x00de, B:84:0x00ea, B:89:0x0101, B:91:0x0109, B:92:0x011d, B:94:0x0123, B:95:0x0126, B:97:0x012c, B:98:0x012f, B:100:0x0135, B:101:0x0138, B:103:0x013e), top: B:111:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0101 A[Catch: all -> 0x0027, TryCatch #0 {all -> 0x0027, blocks: (B:63:0x0022, B:87:0x00fd, B:72:0x0095, B:74:0x00a8, B:75:0x00b4, B:77:0x00ba, B:78:0x00c6, B:80:0x00cc, B:81:0x00d8, B:83:0x00de, B:84:0x00ea, B:89:0x0101, B:91:0x0109, B:92:0x011d, B:94:0x0123, B:95:0x0126, B:97:0x012c, B:98:0x012f, B:100:0x0135, B:101:0x0138, B:103:0x013e), top: B:111:0x0022 }] */
    /* JADX WARN: Type inference failed for: r1v2, types: [T, kotlinx.coroutines.Deferred] */
    /* JADX WARN: Type inference failed for: r6v4, types: [T, kotlinx.coroutines.Deferred] */
    /* JADX WARN: Type inference failed for: r6v6, types: [T, kotlinx.coroutines.Job] */
    /* JADX WARN: Type inference failed for: r6v9, types: [T, kotlinx.coroutines.Job] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:85:0x00fa -> B:87:0x00fd). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r14) {
        /*
            Method dump skipped, instruction units count: 352
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.compat.CameraStateOpener$tryOpenCamera$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
