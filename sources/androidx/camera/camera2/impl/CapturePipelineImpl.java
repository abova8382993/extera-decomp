package androidx.camera.camera2.impl;

import android.util.Log;
import android.view.Surface;
import androidx.camera.camera2.adapter.CaptureConfigAdapter;
import androidx.camera.camera2.adapter.CaptureResultAdapter;
import androidx.camera.camera2.compat.workaround.FlashAvailabilityCheckerKt;
import androidx.camera.camera2.compat.workaround.UseTorchAsFlash;
import androidx.camera.camera2.config.UseCaseGraphContext;
import androidx.camera.camera2.pipe.FrameInfo;
import androidx.camera.camera2.pipe.FrameMetadata;
import androidx.camera.camera2.pipe.Metadata;
import androidx.camera.camera2.pipe.Request;
import androidx.camera.camera2.pipe.RequestFailure;
import androidx.camera.camera2.pipe.RequestMetadata;
import androidx.camera.camera2.pipe.RequestNumber;
import androidx.camera.camera2.pipe.RequestTemplate;
import androidx.camera.camera2.pipe.StreamId;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.CameraCaptureResult;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.ConvergenceUtils;
import com.android.p006dx.p009io.Opcodes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Provider;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KClass;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import okhttp3.internal.http.HttpStatusCodesKt;
import okhttp3.internal.url._UrlKt;
import okio.Segment$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes3.dex */
@Metadata(m876d1 = {"\u0000Ë\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u001c\n\u0002\b\u0006*\u0001x\b\u0007\u0018\u00002\u00020\u0001:\u0002{|B_\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u0012\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012\u0012\u0006\u0010\u0016\u001a\u00020\u0015¢\u0006\u0004\b\u0017\u0010\u0018J\u0012\u0010\u001a\u001a\u0004\u0018\u00010\u0019H\u0082@¢\u0006\u0004\b\u001a\u0010\u001bJN\u0010'\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010&0%0\u001c2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c2\u0006\u0010 \u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020\u001f2\b\u0010$\u001a\u0004\u0018\u00010#H\u0082@¢\u0006\u0004\b'\u0010(JF\u0010)\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010&0%0\u001c2\b\u0010$\u001a\u0004\u0018\u00010#2\u0006\u0010 \u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u001f2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cH\u0082@¢\u0006\u0004\b)\u0010*JF\u0010+\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010&0%0\u001c2\b\u0010$\u001a\u0004\u0018\u00010#2\u0006\u0010 \u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u001f2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cH\u0082@¢\u0006\u0004\b+\u0010*J>\u0010,\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010&0%0\u001c2\b\u0010$\u001a\u0004\u0018\u00010#2\u0006\u0010 \u001a\u00020\u001f2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cH\u0082@¢\u0006\u0004\b,\u0010-JN\u00102\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010&0%0\u001c2\b\u0010$\u001a\u0004\u0018\u00010#2\u0006\u0010 \u001a\u00020\u001f2\u0006\u0010/\u001a\u00020.2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c2\u0006\u00101\u001a\u000200H\u0082@¢\u0006\u0004\b2\u00103JF\u00104\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010&0%0\u001c2\b\u0010$\u001a\u0004\u0018\u00010#2\u0006\u0010/\u001a\u00020.2\u0006\u0010 \u001a\u00020\u001f2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cH\u0082@¢\u0006\u0004\b4\u00105J>\u00106\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010&0%0\u001c2\b\u0010$\u001a\u0004\u0018\u00010#2\u0006\u0010 \u001a\u00020\u001f2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cH\u0082@¢\u0006\u0004\b6\u0010-J \u0010:\u001a\u0002092\u0006\u00107\u001a\u00020.2\u0006\u00108\u001a\u000200H\u0082@¢\u0006\u0004\b:\u0010;J2\u0010@\u001a\u001d\u0012\u0013\u0012\u00110\u0019¢\u0006\f\b=\u0012\b\b>\u0012\u0004\b\b(?\u0012\u0004\u0012\u0002000<2\u0006\u00108\u001a\u000200H\u0002¢\u0006\u0004\b@\u0010AJ\u0013\u0010C\u001a\u00020B*\u00020\u0019H\u0002¢\u0006\u0004\bC\u0010DJ\u0018\u0010E\u001a\u0002092\u0006\u0010/\u001a\u00020.H\u0082@¢\u0006\u0004\bE\u0010FJ%\u0010H\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010&0%0\u001c2\u0006\u0010G\u001a\u00020#H\u0002¢\u0006\u0004\bH\u0010IJ\u0018\u0010J\u001a\u0002002\u0006\u0010!\u001a\u00020\u001fH\u0082@¢\u0006\u0004\bJ\u0010KJ?\u0010P\u001a\u0004\u0018\u00010M2\u0006\u0010L\u001a\u00020.2#\b\u0002\u0010O\u001a\u001d\u0012\u0013\u0012\u00110M¢\u0006\f\b=\u0012\b\b>\u0012\u0004\b\b(N\u0012\u0004\u0012\u0002000<H\u0082@¢\u0006\u0004\bP\u0010QJ\u0018\u00108\u001a\u0002002\u0006\u0010\"\u001a\u00020\u001fH\u0082@¢\u0006\u0004\b8\u0010KJT\u0010Z\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010&0%0\u001c2\f\u0010S\u001a\b\u0012\u0004\u0012\u00020R0\u001c2\u0006\u0010U\u001a\u00020T2\u0006\u0010W\u001a\u00020V2\u0006\u0010 \u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u001fH\u0096@¢\u0006\u0004\bX\u0010YJ\u0018\u0010\\\u001a\u00020[2\u0006\u0010 \u001a\u00020\u001fH\u0087@¢\u0006\u0004\b\\\u0010KJ\u0018\u0010]\u001a\u00020[2\u0006\u0010 \u001a\u00020\u001fH\u0087@¢\u0006\u0004\b]\u0010KR\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010^R\u0014\u0010\u0005\u001a\u00020\u00048\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0005\u0010_R\u0014\u0010\u0007\u001a\u00020\u00068\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0007\u0010`R\u0014\u0010\t\u001a\u00020\b8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\t\u0010aR\u0014\u0010\u000b\u001a\u00020\n8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u000b\u0010bR\u0014\u0010\r\u001a\u00020\f8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\r\u0010cR\u0014\u0010\u000f\u001a\u00020\u000e8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u000f\u0010dR\u001a\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00130\u00128\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0014\u0010eR\u0014\u0010\u0016\u001a\u00020\u00158\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0016\u0010fR\u001b\u0010k\u001a\u0002008BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bg\u0010h\u001a\u0004\bi\u0010jR#\u0010p\u001a\n l*\u0004\u0018\u00010\u00130\u00138BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bm\u0010h\u001a\u0004\bn\u0010oR\"\u0010q\u001a\u00020\u001f8\u0016@\u0016X\u0096\u000e¢\u0006\u0012\n\u0004\bq\u0010r\u001a\u0004\bs\u0010t\"\u0004\bu\u0010vR\u0018\u0010?\u001a\u0004\u0018\u00010\u00198\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b?\u0010wR\u0014\u0010y\u001a\u00020x8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\by\u0010z¨\u0006}"}, m877d2 = {"Landroidx/camera/camera2/impl/CapturePipelineImpl;", "Landroidx/camera/camera2/impl/CapturePipeline;", "Landroidx/camera/camera2/adapter/CaptureConfigAdapter;", "configAdapter", "Landroidx/camera/camera2/impl/FlashControl;", "flashControl", "Landroidx/camera/camera2/impl/TorchControl;", "torchControl", "Landroidx/camera/camera2/impl/VideoUsageControl;", "videoUsageControl", "Landroidx/camera/camera2/impl/UseCaseThreads;", "threads", "Landroidx/camera/camera2/impl/ComboRequestListener;", "requestListener", "Landroidx/camera/camera2/compat/workaround/UseTorchAsFlash;", "useTorchAsFlash", "Landroidx/camera/camera2/impl/CameraProperties;", "cameraProperties", "Ljavax/inject/Provider;", "Landroidx/camera/camera2/impl/UseCaseCameraState;", "useCaseCameraStateProvider", "Landroidx/camera/camera2/config/UseCaseGraphContext;", "useCaseGraphContext", "<init>", "(Landroidx/camera/camera2/adapter/CaptureConfigAdapter;Landroidx/camera/camera2/impl/FlashControl;Landroidx/camera/camera2/impl/TorchControl;Landroidx/camera/camera2/impl/VideoUsageControl;Landroidx/camera/camera2/impl/UseCaseThreads;Landroidx/camera/camera2/impl/ComboRequestListener;Landroidx/camera/camera2/compat/workaround/UseTorchAsFlash;Landroidx/camera/camera2/impl/CameraProperties;Ljavax/inject/Provider;Landroidx/camera/camera2/config/UseCaseGraphContext;)V", "Landroidx/camera/camera2/pipe/FrameMetadata;", "getFrameMetadata", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/camera2/impl/CapturePipelineImpl$PipelineTask;", "pipelineTasks", _UrlKt.FRAGMENT_ENCODE_SET, "captureMode", "flashMode", "flashType", "Landroidx/camera/camera2/impl/CapturePipelineImpl$MainCaptureParams;", "mainCaptureParams", "Lkotlinx/coroutines/Deferred;", "Ljava/lang/Void;", "invokeCaptureTasks", "(Ljava/util/List;IIILandroidx/camera/camera2/impl/CapturePipelineImpl$MainCaptureParams;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "torchAsFlashCapture", "(Landroidx/camera/camera2/impl/CapturePipelineImpl$MainCaptureParams;IILjava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "defaultCapture", "defaultNoFlashCapture", "(Landroidx/camera/camera2/impl/CapturePipelineImpl$MainCaptureParams;ILjava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", _UrlKt.FRAGMENT_ENCODE_SET, "timeLimitNs", _UrlKt.FRAGMENT_ENCODE_SET, "triggerAePreCapture", "torchApplyCapture", "(Landroidx/camera/camera2/impl/CapturePipelineImpl$MainCaptureParams;IJLjava/util/List;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "aePreCaptureApplyCapture", "(Landroidx/camera/camera2/impl/CapturePipelineImpl$MainCaptureParams;JILjava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "screenFlashCapture", "convergedTimeLimitNs", "isTorchAsFlash", "Landroidx/camera/camera2/pipe/Result3A;", "lockAf", "(JZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "frameMetadata", "getConvergeCondition", "(Z)Lkotlin/jvm/functions/Function1;", "Landroidx/camera/core/impl/CameraCaptureResult;", "toCameraCaptureResult", "(Landroidx/camera/camera2/pipe/FrameMetadata;)Landroidx/camera/core/impl/CameraCaptureResult;", "unlockAf", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "params", "submitRequestInternal", "(Landroidx/camera/camera2/impl/CapturePipelineImpl$MainCaptureParams;)Ljava/util/List;", "isPhysicalFlashRequired", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "waitTimeoutNanos", "Landroidx/camera/camera2/pipe/FrameInfo;", "totalCaptureResult", "checker", "waitForResult", "(JLkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Landroidx/camera/core/impl/CaptureConfig;", "configs", "Landroidx/camera/camera2/pipe/RequestTemplate;", "requestTemplate", "Landroidx/camera/core/impl/Config;", "sessionConfigOptions", "submitStillCaptures-BvXKQx0", "(Ljava/util/List;ILandroidx/camera/core/impl/Config;IIILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "submitStillCaptures", _UrlKt.FRAGMENT_ENCODE_SET, "invokeScreenFlashPreCaptureTasks", "invokeScreenFlashPostCaptureTasks", "Landroidx/camera/camera2/adapter/CaptureConfigAdapter;", "Landroidx/camera/camera2/impl/FlashControl;", "Landroidx/camera/camera2/impl/TorchControl;", "Landroidx/camera/camera2/impl/VideoUsageControl;", "Landroidx/camera/camera2/impl/UseCaseThreads;", "Landroidx/camera/camera2/impl/ComboRequestListener;", "Landroidx/camera/camera2/compat/workaround/UseTorchAsFlash;", "Ljavax/inject/Provider;", "Landroidx/camera/camera2/config/UseCaseGraphContext;", "hasFlashUnit$delegate", "Lkotlin/Lazy;", "getHasFlashUnit", "()Z", "hasFlashUnit", "kotlin.jvm.PlatformType", "useCaseCameraState$delegate", "getUseCaseCameraState", "()Landroidx/camera/camera2/impl/UseCaseCameraState;", "useCaseCameraState", "template", "I", "getTemplate", "()I", "setTemplate", "(I)V", "Landroidx/camera/camera2/pipe/FrameMetadata;", "androidx/camera/camera2/impl/CapturePipelineImpl$emptyRequestMetadata$1", "emptyRequestMetadata", "Landroidx/camera/camera2/impl/CapturePipelineImpl$emptyRequestMetadata$1;", "PipelineTask", "MainCaptureParams", "camera-camera2"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nCapturePipeline.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CapturePipeline.kt\nandroidx/camera/camera2/impl/CapturePipelineImpl\n+ 2 Camera2Logger.kt\nandroidx/camera/camera2/impl/Camera2Logger\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 UseCaseCameraConfig.kt\nandroidx/camera/camera2/config/UseCaseGraphContext\n+ 5 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 6 UseCaseThreads.kt\nandroidx/camera/camera2/impl/UseCaseThreads\n*L\n1#1,870:1\n291#1:912\n292#1,3:917\n295#1:928\n297#1,11:933\n320#1:944\n291#1:949\n292#1,3:954\n295#1:991\n297#1,11:996\n320#1:1007\n291#1:1012\n292#1,3:1017\n295#1:1034\n297#1,11:1039\n320#1:1050\n291#1:1055\n292#1,16:1060\n320#1:1076\n85#2,4:871\n85#2,4:875\n85#2,4:879\n85#2,4:884\n85#2,4:888\n85#2,4:892\n85#2,4:896\n85#2,4:900\n85#2,4:904\n85#2,4:908\n85#2,4:913\n85#2,4:920\n85#2,4:924\n85#2,4:929\n85#2,4:945\n85#2,4:950\n85#2,4:957\n85#2,4:961\n85#2,4:965\n85#2,4:971\n85#2,4:975\n85#2,4:979\n85#2,4:983\n85#2,4:987\n85#2,4:992\n85#2,4:1008\n85#2,4:1013\n85#2,4:1020\n85#2,4:1026\n85#2,4:1030\n85#2,4:1035\n85#2,4:1051\n85#2,4:1056\n85#2,4:1079\n85#2,4:1083\n85#2,4:1087\n85#2,4:1093\n85#2,4:1097\n85#2,4:1105\n112#2,4:1119\n1#3:883\n1#3:970\n1#3:1025\n1#3:1078\n1#3:1092\n1#3:1102\n1#3:1104\n1#3:1123\n242#4:969\n242#4:1024\n242#4:1077\n242#4:1091\n242#4:1101\n242#4:1103\n1617#5,9:1109\n1869#5:1118\n1870#5:1124\n1626#5:1125\n194#6:1126\n*S KotlinDebug\n*F\n+ 1 CapturePipeline.kt\nandroidx/camera/camera2/impl/CapturePipelineImpl\n*L\n373#1:912\n373#1:917,3\n373#1:928\n373#1:933,11\n373#1:944\n403#1:949\n403#1:954,3\n403#1:991\n403#1:996,11\n403#1:1007\n486#1:1012\n486#1:1017,3\n486#1:1034\n486#1:1039,11\n486#1:1050\n526#1:1055\n526#1:1060,16\n526#1:1076\n175#1:871,4\n178#1:875,4\n199#1:879,4\n291#1:884,4\n293#1:888,4\n295#1:892,4\n298#1:896,4\n300#1:900,4\n329#1:904,4\n371#1:908,4\n373#1:913,4\n377#1:920,4\n379#1:924,4\n373#1:929,4\n399#1:945,4\n403#1:950,4\n407#1:957,4\n409#1:961,4\n413#1:965,4\n423#1:971,4\n435#1:975,4\n437#1:979,4\n439#1:983,4\n446#1:987,4\n403#1:992,4\n484#1:1008,4\n486#1:1013,4\n489#1:1020,4\n493#1:1026,4\n500#1:1030,4\n486#1:1035,4\n524#1:1051,4\n526#1:1056,4\n548#1:1079,4\n557#1:1083,4\n566#1:1087,4\n568#1:1093,4\n571#1:1097,4\n652#1:1105,4\n703#1:1119,4\n415#1:970\n492#1:1025\n546#1:1078\n567#1:1092\n584#1:1102\n648#1:1104\n657#1:1123\n415#1:969\n492#1:1024\n546#1:1077\n567#1:1091\n584#1:1101\n648#1:1103\n657#1:1109,9\n657#1:1118\n657#1:1124\n657#1:1125\n723#1:1126\n*E\n"})
public final class CapturePipelineImpl implements CapturePipeline {
    private final CaptureConfigAdapter configAdapter;
    private final FlashControl flashControl;
    private FrameMetadata frameMetadata;

    /* JADX INFO: renamed from: hasFlashUnit$delegate, reason: from kotlin metadata */
    private final Lazy hasFlashUnit;
    private final ComboRequestListener requestListener;
    private final UseCaseThreads threads;
    private final TorchControl torchControl;
    private final Provider<UseCaseCameraState> useCaseCameraStateProvider;
    private final UseCaseGraphContext useCaseGraphContext;
    private final UseTorchAsFlash useTorchAsFlash;
    private final VideoUsageControl videoUsageControl;

    /* JADX INFO: renamed from: useCaseCameraState$delegate, reason: from kotlin metadata */
    private final Lazy useCaseCameraState = LazyKt.lazy(new Function0() { // from class: androidx.camera.camera2.impl.CapturePipelineImpl$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return this.f$0.useCaseCameraStateProvider.get();
        }
    });
    private int template = 1;
    private final CapturePipelineImpl$emptyRequestMetadata$1 emptyRequestMetadata = new RequestMetadata() { // from class: androidx.camera.camera2.impl.CapturePipelineImpl$emptyRequestMetadata$1
        private final int template = RequestTemplate.m1640constructorimpl(0);
        private final Map<StreamId, Surface> streams = MapsKt.emptyMap();
        private final boolean repeating = true;
        private final Request request = new Request(CollectionsKt.emptyList(), null, null, null, null, null, 62, null);
        private final long requestNumber = RequestNumber.m1634constructorimpl(0);

        @Override // androidx.camera.camera2.pipe.Metadata
        public <T> T get(Metadata.Key<T> key) {
            return null;
        }

        @Override // androidx.camera.camera2.pipe.Metadata
        public <T> T getOrDefault(Metadata.Key<T> key, T t) {
            return t;
        }

        @Override // androidx.camera.camera2.pipe.UnsafeWrapper
        public <T> T unwrapAs(KClass<T> type) {
            return null;
        }

        @Override // androidx.camera.camera2.pipe.RequestMetadata
        public Map<StreamId, Surface> getStreams() {
            return this.streams;
        }

        @Override // androidx.camera.camera2.pipe.RequestMetadata
        public boolean getRepeating() {
            return this.repeating;
        }

        @Override // androidx.camera.camera2.pipe.RequestMetadata
        public Request getRequest() {
            return this.request;
        }

        @Override // androidx.camera.camera2.pipe.RequestMetadata
        /* JADX INFO: renamed from: getRequestNumber-my6kx4g, reason: not valid java name and from getter */
        public long getRequestNumber() {
            return this.requestNumber;
        }
    };

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$aePreCaptureApplyCapture$1 */
    @kotlin.Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.CapturePipelineImpl", m896f = "CapturePipeline.kt", m897i = {0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2}, m898l = {887, 494, 499}, m899m = "aePreCaptureApplyCapture", m900n = {"this_$iv", "$this$invoke$iv", "mainCaptureParams$iv", "timeLimitNs", "captureMode", "this_$iv", "$this$invoke$iv", "mainCaptureParams$iv", "captureMode", "this_$iv", "$this$invoke$iv", "mainCaptureParams$iv", "captureMode"}, m902s = {"L$0", "L$1", "L$2", "J$0", "I$0", "L$0", "L$1", "L$2", "I$0", "L$0", "L$1", "L$2", "I$0"}, m903v = 1)
    public static final class C01261 extends ContinuationImpl {
        int I$0;
        long J$0;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        public C01261(Continuation<? super C01261> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CapturePipelineImpl.this.aePreCaptureApplyCapture(null, 0L, 0, null, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$defaultCapture$1 */
    @kotlin.Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.CapturePipelineImpl", m896f = "CapturePipeline.kt", m897i = {0, 0, 0}, m898l = {352, 357, 359, 362}, m899m = "defaultCapture", m900n = {"mainCaptureParams", "pipelineTasks", "captureMode"}, m902s = {"L$0", "L$1", "I$0"}, m903v = 1)
    public static final class C01271 extends ContinuationImpl {
        int I$0;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C01271(Continuation<? super C01271> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CapturePipelineImpl.this.defaultCapture(null, 0, 0, null, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$defaultNoFlashCapture$1 */
    @kotlin.Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.CapturePipelineImpl", m896f = "CapturePipeline.kt", m897i = {0, 0, 0, 0}, m898l = {378}, m899m = "defaultNoFlashCapture", m900n = {"this_$iv", "$this$invoke$iv", "mainCaptureParams$iv", "lock3ARequired"}, m902s = {"L$0", "L$1", "L$2", "I$0"}, m903v = 1)
    public static final class C01281 extends ContinuationImpl {
        int I$0;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public C01281(Continuation<? super C01281> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CapturePipelineImpl.this.defaultNoFlashCapture(null, 0, null, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$getFrameMetadata$1 */
    @kotlin.Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.CapturePipelineImpl", m896f = "CapturePipeline.kt", m897i = {}, m898l = {176}, m899m = "getFrameMetadata", m900n = {}, m902s = {}, m903v = 1)
    public static final class C01291 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C01291(Continuation<? super C01291> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CapturePipelineImpl.this.getFrameMetadata(this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$invokeCaptureTasks$1 */
    @kotlin.Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.CapturePipelineImpl", m896f = "CapturePipeline.kt", m897i = {1, 1, 1, 1}, m898l = {Opcodes.ADD_INT_LIT8, Opcodes.RSUB_INT_LIT8, Opcodes.MUL_INT_LIT8, Opcodes.REM_INT_LIT8}, m899m = "invokeCaptureTasks", m900n = {"pipelineTasks", "mainCaptureParams", "captureMode", "flashMode"}, m902s = {"L$0", "L$1", "I$0", "I$1"}, m903v = 1)
    public static final class C01301 extends ContinuationImpl {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C01301(Continuation<? super C01301> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CapturePipelineImpl.this.invokeCaptureTasks(null, 0, 0, 0, null, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$invokeScreenFlashPostCaptureTasks$1 */
    @kotlin.Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.CapturePipelineImpl", m896f = "CapturePipeline.kt", m897i = {0, 1}, m898l = {563, 875, 570}, m899m = "invokeScreenFlashPostCaptureTasks", m900n = {"captureMode", "captureMode"}, m902s = {"I$0", "I$0"}, m903v = 1)
    public static final class C01311 extends ContinuationImpl {
        int I$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C01311(Continuation<? super C01311> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CapturePipelineImpl.this.invokeScreenFlashPostCaptureTasks(0, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$invokeScreenFlashPreCaptureTasks$1 */
    @kotlin.Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.CapturePipelineImpl", m896f = "CapturePipeline.kt", m897i = {0, 1}, m898l = {544, 871, 551, 556}, m899m = "invokeScreenFlashPreCaptureTasks", m900n = {"captureMode", "captureMode"}, m902s = {"I$0", "I$0"}, m903v = 1)
    public static final class C01321 extends ContinuationImpl {
        int I$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C01321(Continuation<? super C01321> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CapturePipelineImpl.this.invokeScreenFlashPreCaptureTasks(0, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$isPhysicalFlashRequired$1 */
    @kotlin.Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.CapturePipelineImpl", m896f = "CapturePipeline.kt", m897i = {}, m898l = {772}, m899m = "isPhysicalFlashRequired", m900n = {}, m902s = {}, m903v = 1)
    public static final class C01331 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C01331(Continuation<? super C01331> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CapturePipelineImpl.this.isPhysicalFlashRequired(0, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$lockAf$1 */
    @kotlin.Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.CapturePipelineImpl", m896f = "CapturePipeline.kt", m897i = {0, 0}, m898l = {871, 585, 594}, m899m = "lockAf", m900n = {"convergedTimeLimitNs", "isTorchAsFlash"}, m902s = {"J$0", "Z$0"}, m903v = 1)
    public static final class C01351 extends ContinuationImpl {
        long J$0;
        Object L$0;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        public C01351(Continuation<? super C01351> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CapturePipelineImpl.this.lockAf(0L, false, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$screenFlashCapture$1 */
    @kotlin.Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.CapturePipelineImpl", m896f = "CapturePipeline.kt", m897i = {0, 0, 0, 0}, m898l = {528}, m899m = "screenFlashCapture", m900n = {"this_$iv", "$this$invoke$iv", "mainCaptureParams$iv", "captureMode"}, m902s = {"L$0", "L$1", "L$2", "I$0"}, m903v = 1)
    public static final class C01361 extends ContinuationImpl {
        int I$0;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public C01361(Continuation<? super C01361> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CapturePipelineImpl.this.screenFlashCapture(null, 0, null, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$torchApplyCapture$1 */
    @kotlin.Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.CapturePipelineImpl", m896f = "CapturePipeline.kt", m897i = {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5}, m898l = {408, 895, 416, HttpStatusCodesKt.HTTP_MISDIRECTED_REQUEST, 436, 440}, m899m = "torchApplyCapture", m900n = {"this_$iv", "$this$invoke$iv", "mainCaptureParams$iv", "captureMode", "timeLimitNs", "triggerAePreCapture", "torchOnRequired", "lock3ARequired", "this_$iv", "$this$invoke$iv", "mainCaptureParams$iv", "captureMode", "timeLimitNs", "triggerAePreCapture", "torchOnRequired", "lock3ARequired", "this_$iv", "$this$invoke$iv", "mainCaptureParams$iv", "captureMode", "triggerAePreCapture", "torchOnRequired", "lock3ARequired", "this_$iv", "$this$invoke$iv", "mainCaptureParams$iv", "captureMode", "triggerAePreCapture", "torchOnRequired", "lock3ARequired", "this_$iv", "$this$invoke$iv", "mainCaptureParams$iv", "captureMode", "triggerAePreCapture", "torchOnRequired", "lock3ARequired", "this_$iv", "$this$invoke$iv", "mainCaptureParams$iv", "captureMode", "triggerAePreCapture", "torchOnRequired", "lock3ARequired"}, m902s = {"L$0", "L$1", "L$2", "I$0", "J$0", "Z$0", "I$1", "I$2", "L$0", "L$1", "L$2", "I$0", "J$0", "Z$0", "I$1", "I$2", "L$0", "L$1", "L$2", "I$0", "Z$0", "I$1", "I$2", "L$0", "L$1", "L$2", "I$0", "Z$0", "I$1", "I$2", "L$0", "L$1", "L$2", "I$0", "Z$0", "I$1", "I$2", "L$0", "L$1", "L$2", "I$0", "Z$0", "I$1", "I$2"}, m903v = 1)
    public static final class C01381 extends ContinuationImpl {
        int I$0;
        int I$1;
        int I$2;
        long J$0;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        public C01381(Continuation<? super C01381> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CapturePipelineImpl.this.torchApplyCapture(null, 0, 0L, null, false, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$torchAsFlashCapture$1 */
    @kotlin.Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.CapturePipelineImpl", m896f = "CapturePipeline.kt", m897i = {0, 0, 0}, m898l = {330, 331, 341}, m899m = "torchAsFlashCapture", m900n = {"mainCaptureParams", "pipelineTasks", "captureMode"}, m902s = {"L$0", "L$1", "I$0"}, m903v = 1)
    public static final class C01391 extends ContinuationImpl {
        int I$0;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C01391(Continuation<? super C01391> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CapturePipelineImpl.this.torchAsFlashCapture(null, 0, 0, null, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$unlockAf$1 */
    @kotlin.Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.CapturePipelineImpl", m896f = "CapturePipeline.kt", m897i = {0}, m898l = {871, 648, 649}, m899m = "unlockAf", m900n = {"timeLimitNs"}, m902s = {"J$0"}, m903v = 1)
    public static final class C01401 extends ContinuationImpl {
        long J$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C01401(Continuation<? super C01401> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CapturePipelineImpl.this.unlockAf(0L, this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$waitForResult$1 */
    @kotlin.Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.CapturePipelineImpl", m896f = "CapturePipeline.kt", m897i = {0}, m898l = {793}, m899m = "waitForResult", m900n = {"resultListener"}, m902s = {"L$0"}, m903v = 1)
    public static final class C01411 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C01411(Continuation<? super C01411> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CapturePipelineImpl.this.waitForResult(0L, null, this);
        }
    }

    /* JADX WARN: Type inference failed for: r1v6, types: [androidx.camera.camera2.impl.CapturePipelineImpl$emptyRequestMetadata$1] */
    public CapturePipelineImpl(CaptureConfigAdapter captureConfigAdapter, FlashControl flashControl, TorchControl torchControl, VideoUsageControl videoUsageControl, UseCaseThreads useCaseThreads, ComboRequestListener comboRequestListener, UseTorchAsFlash useTorchAsFlash, final CameraProperties cameraProperties, Provider<UseCaseCameraState> provider, UseCaseGraphContext useCaseGraphContext) {
        this.configAdapter = captureConfigAdapter;
        this.flashControl = flashControl;
        this.torchControl = torchControl;
        this.videoUsageControl = videoUsageControl;
        this.threads = useCaseThreads;
        this.requestListener = comboRequestListener;
        this.useTorchAsFlash = useTorchAsFlash;
        this.useCaseCameraStateProvider = provider;
        this.useCaseGraphContext = useCaseGraphContext;
        this.hasFlashUnit = LazyKt.lazy(new Function0() { // from class: androidx.camera.camera2.impl.CapturePipelineImpl$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(FlashAvailabilityCheckerKt.isFlashAvailable$default(cameraProperties, false, 1, null));
            }
        });
    }

    @kotlin.Metadata(m876d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, m877d2 = {"Landroidx/camera/camera2/impl/CapturePipelineImpl$PipelineTask;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "(Ljava/lang/String;I)V", "PRE_CAPTURE", "MAIN_CAPTURE", "POST_CAPTURE", "camera-camera2"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    public static final class PipelineTask extends Enum<PipelineTask> {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ PipelineTask[] $VALUES;
        public static final PipelineTask PRE_CAPTURE = new PipelineTask("PRE_CAPTURE", 0);
        public static final PipelineTask MAIN_CAPTURE = new PipelineTask("MAIN_CAPTURE", 1);
        public static final PipelineTask POST_CAPTURE = new PipelineTask("POST_CAPTURE", 2);

        private static final /* synthetic */ PipelineTask[] $values() {
            return new PipelineTask[]{PRE_CAPTURE, MAIN_CAPTURE, POST_CAPTURE};
        }

        public static PipelineTask valueOf(String str) {
            return (PipelineTask) Enum.valueOf(PipelineTask.class, str);
        }

        public static PipelineTask[] values() {
            return (PipelineTask[]) $VALUES.clone();
        }

        private PipelineTask(String str, int i) {
            super(str, i);
        }

        static {
            PipelineTask[] pipelineTaskArr$values = $values();
            $VALUES = pipelineTaskArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(pipelineTaskArr$values);
        }
    }

    @kotlin.Metadata(m876d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u000b\b\u0082\b\u0018\u00002\u00020\u0001B%\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0004\b\t\u0010\nJ\u0010\u0010\f\u001a\u00020\u000bHÖ\u0001¢\u0006\u0004\b\f\u0010\rJ\u0010\u0010\u000f\u001a\u00020\u000eHÖ\u0001¢\u0006\u0004\b\u000f\u0010\u0010J\u001a\u0010\u0013\u001a\u00020\u00122\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u0013\u0010\u0014R\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006¢\u0006\f\n\u0004\b\u0004\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0006\u001a\u00020\u00058\u0006¢\u0006\f\n\u0004\b\u0006\u0010\u0018\u001a\u0004\b\u0019\u0010\u0010R\u0017\u0010\b\u001a\u00020\u00078\u0006¢\u0006\f\n\u0004\b\b\u0010\u001a\u001a\u0004\b\u001b\u0010\u001c¨\u0006\u001d"}, m877d2 = {"Landroidx/camera/camera2/impl/CapturePipelineImpl$MainCaptureParams;", _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/core/impl/CaptureConfig;", "configs", "Landroidx/camera/camera2/pipe/RequestTemplate;", "requestTemplate", "Landroidx/camera/core/impl/Config;", "sessionConfigOptions", "<init>", "(Ljava/util/List;ILandroidx/camera/core/impl/Config;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", _UrlKt.FRAGMENT_ENCODE_SET, "toString", "()Ljava/lang/String;", _UrlKt.FRAGMENT_ENCODE_SET, "hashCode", "()I", "other", _UrlKt.FRAGMENT_ENCODE_SET, "equals", "(Ljava/lang/Object;)Z", "Ljava/util/List;", "getConfigs", "()Ljava/util/List;", "I", "getRequestTemplate-fGx8uWA", "Landroidx/camera/core/impl/Config;", "getSessionConfigOptions", "()Landroidx/camera/core/impl/Config;", "camera-camera2"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    public static final /* data */ class MainCaptureParams {
        private final List<CaptureConfig> configs;
        private final int requestTemplate;
        private final Config sessionConfigOptions;

        public /* synthetic */ MainCaptureParams(List list, int i, Config config, DefaultConstructorMarker defaultConstructorMarker) {
            this(list, i, config);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof MainCaptureParams)) {
                return false;
            }
            MainCaptureParams mainCaptureParams = (MainCaptureParams) other;
            return Intrinsics.areEqual(this.configs, mainCaptureParams.configs) && RequestTemplate.m1642equalsimpl0(this.requestTemplate, mainCaptureParams.requestTemplate) && Intrinsics.areEqual(this.sessionConfigOptions, mainCaptureParams.sessionConfigOptions);
        }

        public int hashCode() {
            return (((this.configs.hashCode() * 31) + RequestTemplate.m1644hashCodeimpl(this.requestTemplate)) * 31) + this.sessionConfigOptions.hashCode();
        }

        public String toString() {
            return "MainCaptureParams(configs=" + this.configs + ", requestTemplate=" + ((Object) RequestTemplate.m1645toStringimpl(this.requestTemplate)) + ", sessionConfigOptions=" + this.sessionConfigOptions + ')';
        }

        private MainCaptureParams(List<CaptureConfig> list, int i, Config config) {
            this.configs = list;
            this.requestTemplate = i;
            this.sessionConfigOptions = config;
        }

        public final List<CaptureConfig> getConfigs() {
            return this.configs;
        }

        /* JADX INFO: renamed from: getRequestTemplate-fGx8uWA, reason: from getter */
        public final int getRequestTemplate() {
            return this.requestTemplate;
        }

        public final Config getSessionConfigOptions() {
            return this.sessionConfigOptions;
        }
    }

    private final boolean getHasFlashUnit() {
        return ((Boolean) this.hasFlashUnit.getValue()).booleanValue();
    }

    public final UseCaseCameraState getUseCaseCameraState() {
        return (UseCaseCameraState) this.useCaseCameraState.getValue();
    }

    public int getTemplate() {
        return this.template;
    }

    @Override // androidx.camera.camera2.impl.CapturePipeline
    public void setTemplate(int i) {
        this.template = i;
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0014  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x007a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getFrameMetadata(kotlin.coroutines.Continuation<? super androidx.camera.camera2.pipe.FrameMetadata> r11) {
        /*
            r10 = this;
            boolean r0 = r11 instanceof androidx.camera.camera2.impl.CapturePipelineImpl.C01291
            if (r0 == 0) goto L14
            r0 = r11
            androidx.camera.camera2.impl.CapturePipelineImpl$getFrameMetadata$1 r0 = (androidx.camera.camera2.impl.CapturePipelineImpl.C01291) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L14
            int r1 = r1 - r2
            r0.label = r1
        L12:
            r5 = r0
            goto L1a
        L14:
            androidx.camera.camera2.impl.CapturePipelineImpl$getFrameMetadata$1 r0 = new androidx.camera.camera2.impl.CapturePipelineImpl$getFrameMetadata$1
            r0.<init>(r11)
            goto L12
        L1a:
            java.lang.Object r11 = r5.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r5.label
            r8 = 0
            java.lang.String r9 = "CXCP"
            r2 = 1
            if (r1 == 0) goto L39
            if (r1 != r2) goto L33
            java.lang.Object r0 = r5.L$0
            androidx.camera.camera2.impl.CapturePipelineImpl r0 = (androidx.camera.camera2.impl.CapturePipelineImpl) r0
            kotlin.ResultKt.throwOnFailure(r11)
            r1 = r10
            goto L66
        L33:
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r10)
            return r8
        L39:
            kotlin.ResultKt.throwOnFailure(r11)
            androidx.camera.camera2.pipe.FrameMetadata r11 = r10.frameMetadata
            if (r11 != 0) goto L71
            androidx.camera.camera2.impl.Camera2Logger r11 = androidx.camera.camera2.impl.Camera2Logger.INSTANCE
            boolean r11 = androidx.camera.core.Logger.isDebugEnabled(r9)
            if (r11 == 0) goto L51
            java.lang.String r11 = androidx.camera.camera2.impl.Camera2Logger.access$getTRUNCATED_TAG$p()
            java.lang.String r1 = "getFrameMetadata: waiting for result"
            android.util.Log.d(r11, r1)
        L51:
            r11 = r2
            long r2 = androidx.camera.camera2.impl.CapturePipelineKt.access$getCHECK_FLASH_REQUIRED_TIMEOUT_IN_NS$p()
            r5.L$0 = r10
            r5.label = r11
            r4 = 0
            r6 = 2
            r7 = 0
            r1 = r10
            java.lang.Object r11 = waitForResult$default(r1, r2, r4, r5, r6, r7)
            if (r11 != r0) goto L65
            return r0
        L65:
            r0 = r1
        L66:
            androidx.camera.camera2.pipe.FrameInfo r11 = (androidx.camera.camera2.pipe.FrameInfo) r11
            if (r11 == 0) goto L6e
            androidx.camera.camera2.pipe.FrameMetadata r8 = r11.getMetadata()
        L6e:
            r0.frameMetadata = r8
            goto L72
        L71:
            r1 = r10
        L72:
            androidx.camera.camera2.impl.Camera2Logger r10 = androidx.camera.camera2.impl.Camera2Logger.INSTANCE
            boolean r10 = androidx.camera.core.Logger.isDebugEnabled(r9)
            if (r10 == 0) goto L93
            java.lang.String r10 = androidx.camera.camera2.impl.Camera2Logger.access$getTRUNCATED_TAG$p()
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r0 = "getFrameMetadata: frameMetadata = "
            r11.<init>(r0)
            androidx.camera.camera2.pipe.FrameMetadata r0 = access$getFrameMetadata$p(r1)
            r11.append(r0)
            java.lang.String r11 = r11.toString()
            android.util.Log.d(r10, r11)
        L93:
            androidx.camera.camera2.pipe.FrameMetadata r10 = r1.frameMetadata
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl.getFrameMetadata(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeCaptureTasks(java.util.List<? extends androidx.camera.camera2.impl.CapturePipelineImpl.PipelineTask> r10, int r11, int r12, int r13, androidx.camera.camera2.impl.CapturePipelineImpl.MainCaptureParams r14, kotlin.coroutines.Continuation<? super java.util.List<? extends kotlinx.coroutines.Deferred<java.lang.Void>>> r15) throws java.lang.Exception {
        /*
            Method dump skipped, instruction units count: 228
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl.invokeCaptureTasks(java.util.List, int, int, int, androidx.camera.camera2.impl.CapturePipelineImpl$MainCaptureParams, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // androidx.camera.camera2.impl.CapturePipeline
    /* JADX INFO: renamed from: submitStillCaptures-BvXKQx0 */
    public Object mo1302submitStillCapturesBvXKQx0(List<CaptureConfig> list, int i, Config config, int i2, int i3, int i4, Continuation<? super List<? extends Deferred<Void>>> continuation) {
        return invokeCaptureTasks(CollectionsKt.listOf((Object[]) new PipelineTask[]{PipelineTask.PRE_CAPTURE, PipelineTask.MAIN_CAPTURE, PipelineTask.POST_CAPTURE}), i2, i4, i3, new MainCaptureParams(list, i, config, null), continuation);
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x0014  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x00ba A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x00bb A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object torchAsFlashCapture(androidx.camera.camera2.impl.CapturePipelineImpl.MainCaptureParams r10, int r11, int r12, java.util.List<? extends androidx.camera.camera2.impl.CapturePipelineImpl.PipelineTask> r13, kotlin.coroutines.Continuation<? super java.util.List<? extends kotlinx.coroutines.Deferred<java.lang.Void>>> r14) throws java.lang.Exception {
        /*
            r9 = this;
            boolean r0 = r14 instanceof androidx.camera.camera2.impl.CapturePipelineImpl.C01391
            if (r0 == 0) goto L14
            r0 = r14
            androidx.camera.camera2.impl.CapturePipelineImpl$torchAsFlashCapture$1 r0 = (androidx.camera.camera2.impl.CapturePipelineImpl.C01391) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L14
            int r1 = r1 - r2
            r0.label = r1
        L12:
            r8 = r0
            goto L1a
        L14:
            androidx.camera.camera2.impl.CapturePipelineImpl$torchAsFlashCapture$1 r0 = new androidx.camera.camera2.impl.CapturePipelineImpl$torchAsFlashCapture$1
            r0.<init>(r14)
            goto L12
        L1a:
            java.lang.Object r14 = r8.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r8.label
            r2 = 3
            r3 = 2
            r4 = 1
            r5 = 0
            if (r1 == 0) goto L4c
            if (r1 == r4) goto L3c
            if (r1 == r3) goto L38
            if (r1 != r2) goto L32
            kotlin.ResultKt.throwOnFailure(r14)
            return r14
        L32:
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r9)
            return r5
        L38:
            kotlin.ResultKt.throwOnFailure(r14)
            return r14
        L3c:
            int r11 = r8.I$0
            java.lang.Object r10 = r8.L$1
            r13 = r10
            java.util.List r13 = (java.util.List) r13
            java.lang.Object r10 = r8.L$0
            androidx.camera.camera2.impl.CapturePipelineImpl$MainCaptureParams r10 = (androidx.camera.camera2.impl.CapturePipelineImpl.MainCaptureParams) r10
            kotlin.ResultKt.throwOnFailure(r14)
        L4a:
            r6 = r13
            goto L77
        L4c:
            kotlin.ResultKt.throwOnFailure(r14)
            androidx.camera.camera2.impl.Camera2Logger r14 = androidx.camera.camera2.impl.Camera2Logger.INSTANCE
            java.lang.String r14 = "CXCP"
            boolean r14 = androidx.camera.core.Logger.isDebugEnabled(r14)
            if (r14 == 0) goto L62
            java.lang.String r14 = androidx.camera.camera2.impl.Camera2Logger.access$getTRUNCATED_TAG$p()
            java.lang.String r1 = "CapturePipeline#torchAsFlashCapture"
            android.util.Log.d(r14, r1)
        L62:
            boolean r14 = r9.getHasFlashUnit()
            if (r14 == 0) goto Lac
            r8.L$0 = r10
            r8.L$1 = r13
            r8.I$0 = r11
            r8.label = r4
            java.lang.Object r14 = r9.isPhysicalFlashRequired(r12, r8)
            if (r14 != r0) goto L4a
            goto Lba
        L77:
            java.lang.Boolean r14 = (java.lang.Boolean) r14
            boolean r12 = r14.booleanValue()
            if (r12 == 0) goto Laa
            r12 = r4
            r14 = r5
            long r4 = androidx.camera.camera2.impl.CapturePipelineKt.access$getCHECK_3A_WITH_FLASH_TIMEOUT_IN_NS$p()
            androidx.camera.camera2.compat.workaround.UseTorchAsFlash r13 = r9.useTorchAsFlash
            boolean r13 = r13.shouldDisableAePrecapture()
            if (r13 != 0) goto L97
            androidx.camera.camera2.impl.VideoUsageControl r13 = r9.videoUsageControl
            boolean r13 = r13.isInVideoUsage()
            if (r13 != 0) goto L97
        L95:
            r7 = r12
            goto L99
        L97:
            r12 = 0
            goto L95
        L99:
            r8.L$0 = r14
            r8.L$1 = r14
            r8.label = r3
            r1 = r9
            r2 = r10
            r3 = r11
            java.lang.Object r9 = r1.torchApplyCapture(r2, r3, r4, r6, r7, r8)
            if (r9 != r0) goto La9
            goto Lba
        La9:
            return r9
        Laa:
            r3 = r11
            r13 = r6
        Lac:
            r1 = r9
            r14 = r5
            r8.L$0 = r14
            r8.L$1 = r14
            r8.label = r2
            java.lang.Object r9 = r1.defaultNoFlashCapture(r10, r11, r13, r8)
            if (r9 != r0) goto Lbb
        Lba:
            return r0
        Lbb:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl.torchAsFlashCapture(androidx.camera.camera2.impl.CapturePipelineImpl$MainCaptureParams, int, int, java.util.List, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object defaultCapture(androidx.camera.camera2.impl.CapturePipelineImpl.MainCaptureParams r9, int r10, int r11, java.util.List<? extends androidx.camera.camera2.impl.CapturePipelineImpl.PipelineTask> r12, kotlin.coroutines.Continuation<? super java.util.List<? extends kotlinx.coroutines.Deferred<java.lang.Void>>> r13) throws java.lang.Exception {
        /*
            r8 = this;
            boolean r0 = r13 instanceof androidx.camera.camera2.impl.CapturePipelineImpl.C01271
            if (r0 == 0) goto L14
            r0 = r13
            androidx.camera.camera2.impl.CapturePipelineImpl$defaultCapture$1 r0 = (androidx.camera.camera2.impl.CapturePipelineImpl.C01271) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L14
            int r1 = r1 - r2
            r0.label = r1
        L12:
            r7 = r0
            goto L1a
        L14:
            androidx.camera.camera2.impl.CapturePipelineImpl$defaultCapture$1 r0 = new androidx.camera.camera2.impl.CapturePipelineImpl$defaultCapture$1
            r0.<init>(r13)
            goto L12
        L1a:
            java.lang.Object r13 = r7.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r7.label
            r2 = 4
            r3 = 3
            r4 = 2
            r5 = 1
            r6 = 0
            if (r1 == 0) goto L54
            if (r1 == r5) goto L43
            if (r1 == r4) goto L3f
            if (r1 == r3) goto L3b
            if (r1 != r2) goto L35
            kotlin.ResultKt.throwOnFailure(r13)
            return r13
        L35:
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r8)
            return r6
        L3b:
            kotlin.ResultKt.throwOnFailure(r13)
            return r13
        L3f:
            kotlin.ResultKt.throwOnFailure(r13)
            return r13
        L43:
            int r10 = r7.I$0
            java.lang.Object r9 = r7.L$1
            r12 = r9
            java.util.List r12 = (java.util.List) r12
            java.lang.Object r9 = r7.L$0
            androidx.camera.camera2.impl.CapturePipelineImpl$MainCaptureParams r9 = (androidx.camera.camera2.impl.CapturePipelineImpl.MainCaptureParams) r9
            kotlin.ResultKt.throwOnFailure(r13)
        L51:
            r2 = r9
            r5 = r10
            goto L6c
        L54:
            kotlin.ResultKt.throwOnFailure(r13)
            boolean r13 = r8.getHasFlashUnit()
            if (r13 == 0) goto La1
            r7.L$0 = r9
            r7.L$1 = r12
            r7.I$0 = r10
            r7.label = r5
            java.lang.Object r13 = r8.isPhysicalFlashRequired(r11, r7)
            if (r13 != r0) goto L51
            goto Laa
        L6c:
            java.lang.Boolean r13 = (java.lang.Boolean) r13
            boolean r9 = r13.booleanValue()
            if (r9 == 0) goto L79
            long r10 = androidx.camera.camera2.impl.CapturePipelineKt.access$getCHECK_3A_WITH_FLASH_TIMEOUT_IN_NS$p()
            goto L7d
        L79:
            long r10 = androidx.camera.camera2.impl.CapturePipelineKt.access$getCHECK_3A_TIMEOUT_IN_NS$p()
        L7d:
            if (r9 != 0) goto L90
            if (r5 != 0) goto L82
            goto L90
        L82:
            r7.L$0 = r6
            r7.L$1 = r6
            r7.label = r3
            java.lang.Object r8 = r8.defaultNoFlashCapture(r2, r5, r12, r7)
            if (r8 != r0) goto L8f
            goto Laa
        L8f:
            return r8
        L90:
            r7.L$0 = r6
            r7.L$1 = r6
            r7.label = r4
            r1 = r8
            r3 = r10
            r6 = r12
            java.lang.Object r8 = r1.aePreCaptureApplyCapture(r2, r3, r5, r6, r7)
            if (r8 != r0) goto La0
            goto Laa
        La0:
            return r8
        La1:
            r1 = r8
            r7.label = r2
            java.lang.Object r8 = r1.defaultNoFlashCapture(r9, r10, r12, r7)
            if (r8 != r0) goto Lab
        Laa:
            return r0
        Lab:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl.defaultCapture(androidx.camera.camera2.impl.CapturePipelineImpl$MainCaptureParams, int, int, java.util.List, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0017  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object defaultNoFlashCapture(androidx.camera.camera2.impl.CapturePipelineImpl.MainCaptureParams r15, int r16, java.util.List<? extends androidx.camera.camera2.impl.CapturePipelineImpl.PipelineTask> r17, kotlin.coroutines.Continuation<? super java.util.List<? extends kotlinx.coroutines.Deferred<java.lang.Void>>> r18) {
        /*
            Method dump skipped, instruction units count: 314
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl.defaultNoFlashCapture(androidx.camera.camera2.impl.CapturePipelineImpl$MainCaptureParams, int, java.util.List, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:222:0x01c1  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x01dd  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x021e  */
    /* JADX WARN: Removed duplicated region for block: B:236:0x0221  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x0225  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x0228  */
    /* JADX WARN: Removed duplicated region for block: B:243:0x025a  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x027f  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x0281  */
    /* JADX WARN: Removed duplicated region for block: B:251:0x0290  */
    /* JADX WARN: Removed duplicated region for block: B:259:0x02b3  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x02ef  */
    /* JADX WARN: Removed duplicated region for block: B:282:0x0339  */
    /* JADX WARN: Removed duplicated region for block: B:286:0x034f  */
    /* JADX WARN: Removed duplicated region for block: B:291:0x036f  */
    /* JADX WARN: Removed duplicated region for block: B:300:0x039a  */
    /* JADX WARN: Removed duplicated region for block: B:303:0x03aa  */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v26 */
    /* JADX WARN: Type inference failed for: r7v3, types: [java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r7v33 */
    /* JADX WARN: Type inference failed for: r7v35, types: [java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r7v49 */
    /* JADX WARN: Type inference failed for: r7v50 */
    /* JADX WARN: Type inference failed for: r7v51 */
    /* JADX WARN: Type inference failed for: r7v52 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object torchApplyCapture(androidx.camera.camera2.impl.CapturePipelineImpl.MainCaptureParams r26, int r27, long r28, java.util.List<? extends androidx.camera.camera2.impl.CapturePipelineImpl.PipelineTask> r30, boolean r31, kotlin.coroutines.Continuation<? super java.util.List<? extends kotlinx.coroutines.Deferred<java.lang.Void>>> r32) throws java.lang.Exception {
        /*
            Method dump skipped, instruction units count: 1000
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl.torchApplyCapture(androidx.camera.camera2.impl.CapturePipelineImpl$MainCaptureParams, int, long, java.util.List, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(16:114|116|(2:118|(1:120)(1:122))(0)|121|123|(4:(1:(1:(1:(14:128|226|129|182|(1:184)|185|186|(1:188)|189|199|(3:201|(1:203)|(3:205|(1:207)|208)(2:209|210))(1:211)|212|(1:214)|215)(2:133|134))(7:135|216|136|137|178|(12:181|182|(0)|185|186|(0)|189|199|(0)(0)|212|(0)|215)|180))(1:140))(6:141|(1:143)|144|(1:146)|147|(7:149|(1:151)|152|(1:154)|155|(1:158)|180)(6:198|199|(0)(0)|212|(0)|215))|218|174|(2:176|180)(4:177|178|(0)|180))|159|220|160|(2:222|162)|166|(1:168)(1:169)|(1:171)|172|173|(2:(1:225)|(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:192:0x01a7, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:193:0x01a8, code lost:
    
        r15 = r7;
     */
    /* JADX WARN: Removed duplicated region for block: B:122:0x001a  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x0176  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x0182 A[Catch: all -> 0x004d, TryCatch #5 {all -> 0x004d, blocks: (B:129:0x0048, B:182:0x017a, B:184:0x0182, B:185:0x018b), top: B:226:0x0048 }] */
    /* JADX WARN: Removed duplicated region for block: B:188:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x01bd  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x01eb  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x01fc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object aePreCaptureApplyCapture(androidx.camera.camera2.impl.CapturePipelineImpl.MainCaptureParams r22, long r23, int r25, java.util.List<? extends androidx.camera.camera2.impl.CapturePipelineImpl.PipelineTask> r26, kotlin.coroutines.Continuation<? super java.util.List<? extends kotlinx.coroutines.Deferred<java.lang.Void>>> r27) throws java.lang.Exception {
        /*
            Method dump skipped, instruction units count: 539
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl.aePreCaptureApplyCapture(androidx.camera.camera2.impl.CapturePipelineImpl$MainCaptureParams, long, int, java.util.List, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x0013  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x00ee  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object screenFlashCapture(androidx.camera.camera2.impl.CapturePipelineImpl.MainCaptureParams r11, int r12, java.util.List<? extends androidx.camera.camera2.impl.CapturePipelineImpl.PipelineTask> r13, kotlin.coroutines.Continuation<? super java.util.List<? extends kotlinx.coroutines.Deferred<java.lang.Void>>> r14) {
        /*
            Method dump skipped, instruction units count: 257
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl.screenFlashCapture(androidx.camera.camera2.impl.CapturePipelineImpl$MainCaptureParams, int, java.util.List, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:111:0x009a A[Catch: all -> 0x00a5, TryCatch #1 {all -> 0x00a5, blocks: (B:109:0x008f, B:111:0x009a, B:114:0x00a8, B:118:0x00b0), top: B:138:0x008f }] */
    /* JADX WARN: Removed duplicated region for block: B:116:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x00da A[Catch: all -> 0x0044, TryCatch #0 {all -> 0x0044, blocks: (B:88:0x003f, B:126:0x00d0, B:128:0x00da, B:129:0x00ed), top: B:136:0x003f }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x001b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeScreenFlashPreCaptureTasks(int r17, kotlin.coroutines.Continuation<? super kotlin.Unit> r18) throws java.lang.Exception {
        /*
            Method dump skipped, instruction units count: 251
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl.invokeScreenFlashPreCaptureTasks(int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:101:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x00b6 A[Catch: all -> 0x0037, TryCatch #0 {all -> 0x0037, blocks: (B:75:0x0032, B:106:0x00ae, B:108:0x00b6, B:109:0x00c0), top: B:116:0x0032 }] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0013  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x008e A[Catch: all -> 0x0099, TryCatch #1 {all -> 0x0099, blocks: (B:94:0x0083, B:96:0x008e, B:102:0x00a2), top: B:118:0x0083 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeScreenFlashPostCaptureTasks(int r10, kotlin.coroutines.Continuation<? super kotlin.Unit> r11) throws java.lang.Exception {
        /*
            Method dump skipped, instruction units count: 206
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl.invokeScreenFlashPostCaptureTasks(int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:110:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x00d3 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object lockAf(long r27, boolean r29, kotlin.coroutines.Continuation<? super androidx.camera.camera2.pipe.Result3A> r30) throws java.lang.Exception {
        /*
            Method dump skipped, instruction units count: 226
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl.lockAf(long, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final Function1<FrameMetadata, Boolean> getConvergeCondition(final boolean isTorchAsFlash) {
        return new Function1() { // from class: androidx.camera.camera2.impl.CapturePipelineImpl$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(ConvergenceUtils.is3AConverged(this.f$0.toCameraCaptureResult((FrameMetadata) obj), isTorchAsFlash));
            }
        };
    }

    public final CameraCaptureResult toCameraCaptureResult(final FrameMetadata frameMetadata) {
        return new CaptureResultAdapter(this.emptyRequestMetadata, frameMetadata.mo1535getFrameNumberUgla2oM(), new FrameInfo(frameMetadata, this) { // from class: androidx.camera.camera2.impl.CapturePipelineImpl$toCameraCaptureResult$frameInfo$1
            private final String camera;
            private final FrameMetadata frameMetadata;
            private final long frameNumber;
            private final FrameMetadata metadata;
            private final RequestMetadata requestMetadata;

            @Override // androidx.camera.camera2.pipe.UnsafeWrapper
            public <T> T unwrapAs(KClass<T> type) {
                return null;
            }

            {
                this.frameMetadata = frameMetadata;
                this.metadata = frameMetadata;
                this.camera = frameMetadata.getCamera();
                this.frameNumber = frameMetadata.mo1535getFrameNumberUgla2oM();
                this.requestMetadata = this.emptyRequestMetadata;
            }

            @Override // androidx.camera.camera2.pipe.FrameInfo
            public FrameMetadata getMetadata() {
                return this.metadata;
            }
        }, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x0018  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0092 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object unlockAf(long r17, kotlin.coroutines.Continuation<? super androidx.camera.camera2.pipe.Result3A> r19) throws java.lang.Exception {
        /*
            r16 = this;
            r0 = r16
            r1 = r19
            boolean r2 = r1 instanceof androidx.camera.camera2.impl.CapturePipelineImpl.C01401
            if (r2 == 0) goto L18
            r2 = r1
            androidx.camera.camera2.impl.CapturePipelineImpl$unlockAf$1 r2 = (androidx.camera.camera2.impl.CapturePipelineImpl.C01401) r2
            int r3 = r2.label
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            r5 = r3 & r4
            if (r5 == 0) goto L18
            int r3 = r3 - r4
            r2.label = r3
        L16:
            r11 = r2
            goto L1e
        L18:
            androidx.camera.camera2.impl.CapturePipelineImpl$unlockAf$1 r2 = new androidx.camera.camera2.impl.CapturePipelineImpl$unlockAf$1
            r2.<init>(r1)
            goto L16
        L1e:
            java.lang.Object r1 = r11.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r11.label
            r14 = 3
            r4 = 2
            r5 = 1
            r15 = 0
            if (r3 == 0) goto L4f
            if (r3 == r5) goto L48
            if (r3 == r4) goto L3c
            if (r3 != r14) goto L36
            kotlin.ResultKt.throwOnFailure(r1)
            return r1
        L36:
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r0)
            return r15
        L3c:
            java.lang.Object r0 = r11.L$0
            r3 = r0
            java.lang.AutoCloseable r3 = (java.lang.AutoCloseable) r3
            kotlin.ResultKt.throwOnFailure(r1)     // Catch: java.lang.Throwable -> L45
            goto L82
        L45:
            r0 = move-exception
        L46:
            r1 = r0
            goto L96
        L48:
            long r6 = r11.J$0
            kotlin.ResultKt.throwOnFailure(r1)
        L4d:
            r9 = r6
            goto L65
        L4f:
            kotlin.ResultKt.throwOnFailure(r1)
            androidx.camera.camera2.config.UseCaseGraphContext r0 = r0.useCaseGraphContext
            androidx.camera.camera2.pipe.CameraGraph r0 = r0.getGraph()
            r6 = r17
            r11.J$0 = r6
            r11.label = r5
            java.lang.Object r1 = r0.acquireSession(r11)
            if (r1 != r2) goto L4d
            goto L91
        L65:
            java.lang.AutoCloseable r1 = (java.lang.AutoCloseable) r1
            r3 = r1
            androidx.camera.camera2.pipe.CameraGraph$Session r3 = (androidx.camera.camera2.pipe.CameraGraph.Session) r3     // Catch: java.lang.Throwable -> L93
            java.lang.Boolean r5 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r5)     // Catch: java.lang.Throwable -> L93
            r11.L$0 = r1     // Catch: java.lang.Throwable -> L93
            r11.label = r4     // Catch: java.lang.Throwable -> L93
            r4 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r12 = 29
            r13 = 0
            java.lang.Object r0 = androidx.camera.camera2.pipe.CameraGraph.Session.unlock3A$default(r3, r4, r5, r6, r7, r8, r9, r11, r12, r13)     // Catch: java.lang.Throwable -> L93
            if (r0 != r2) goto L80
            goto L91
        L80:
            r3 = r1
            r1 = r0
        L82:
            kotlinx.coroutines.Deferred r1 = (kotlinx.coroutines.Deferred) r1     // Catch: java.lang.Throwable -> L45
            kotlin.jdk7.AutoCloseableKt.closeFinally(r3, r15)
            r11.L$0 = r15
            r11.label = r14
            java.lang.Object r0 = r1.await(r11)
            if (r0 != r2) goto L92
        L91:
            return r2
        L92:
            return r0
        L93:
            r0 = move-exception
            r3 = r1
            goto L46
        L96:
            throw r1     // Catch: java.lang.Throwable -> L97
        L97:
            r0 = move-exception
            kotlin.jdk7.AutoCloseableKt.closeFinally(r3, r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl.unlockAf(long, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final List<Deferred<Void>> submitRequestInternal(MainCaptureParams params) {
        Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
        if (Logger.isDebugEnabled("CXCP")) {
            Log.d(Camera2Logger.TRUNCATED_TAG, "CapturePipeline#submitRequestInternal; Submitting " + params.getConfigs() + " with CameraPipe");
        }
        ArrayList arrayList = new ArrayList();
        List<CaptureConfig> configs = params.getConfigs();
        ArrayList arrayList2 = new ArrayList();
        Iterator<T> it = configs.iterator();
        while (true) {
            Request requestM1280mapToRequestnAberiA = null;
            if (!it.hasNext()) {
                break;
            }
            CaptureConfig captureConfig = (CaptureConfig) it.next();
            final CompletableDeferred completableDeferredCompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default(null, 1, null);
            arrayList.add(completableDeferredCompletableDeferred$default);
            try {
                requestM1280mapToRequestnAberiA = this.configAdapter.m1280mapToRequestnAberiA(captureConfig, params.getRequestTemplate(), params.getSessionConfigOptions(), CollectionsKt.listOf(new Request.Listener() { // from class: androidx.camera.camera2.impl.CapturePipelineImpl$submitRequestInternal$requests$1$1
                    @Override // androidx.camera.camera2.pipe.Request.Listener
                    public void onAborted(Request request) {
                        completableDeferredCompletableDeferred$default.completeExceptionally(new ImageCaptureException(3, "Capture request is cancelled because camera is closed", null));
                    }

                    @Override // androidx.camera.camera2.pipe.Request.Listener
                    /* JADX INFO: renamed from: onTotalCaptureResult-CcXjc1I */
                    public void mo1284onTotalCaptureResultCcXjc1I(RequestMetadata requestMetadata, long frameNumber, FrameInfo totalCaptureResult) {
                        completableDeferredCompletableDeferred$default.complete(null);
                    }

                    @Override // androidx.camera.camera2.pipe.Request.Listener
                    /* JADX INFO: renamed from: onFailed-CcXjc1I */
                    public void mo1283onFailedCcXjc1I(RequestMetadata requestMetadata, long frameNumber, RequestFailure requestFailure) {
                        completableDeferredCompletableDeferred$default.completeExceptionally(new ImageCaptureException(2, "Capture request failed with reason " + requestFailure.getReason(), null));
                    }
                }));
            } catch (IllegalStateException e) {
                Camera2Logger camera2Logger2 = Camera2Logger.INSTANCE;
                if (Logger.isInfoEnabled("CXCP")) {
                    Log.i(Camera2Logger.TRUNCATED_TAG, "CapturePipeline#submitRequestInternal: configAdapter.mapToRequest failed!", e);
                }
                completableDeferredCompletableDeferred$default.completeExceptionally(new ImageCaptureException(2, "Capture request failed with reason " + e.getMessage(), e));
            }
            if (requestM1280mapToRequestnAberiA != null) {
                arrayList2.add(requestM1280mapToRequestnAberiA);
            }
        }
        if (arrayList2.isEmpty()) {
            return arrayList;
        }
        BuildersKt__Builders_commonKt.launch$default(this.threads.getSequentialScope(), null, null, new C0137x5c55b0eb(null, this, arrayList, arrayList2), 3, null);
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x0013  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0045  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object isPhysicalFlashRequired(int r6, kotlin.coroutines.Continuation<? super java.lang.Boolean> r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof androidx.camera.camera2.impl.CapturePipelineImpl.C01331
            if (r0 == 0) goto L13
            r0 = r7
            androidx.camera.camera2.impl.CapturePipelineImpl$isPhysicalFlashRequired$1 r0 = (androidx.camera.camera2.impl.CapturePipelineImpl.C01331) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.camera.camera2.impl.CapturePipelineImpl$isPhysicalFlashRequired$1 r0 = new androidx.camera.camera2.impl.CapturePipelineImpl$isPhysicalFlashRequired$1
            r0.<init>(r7)
        L18:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L31
            if (r2 != r4) goto L2a
            kotlin.ResultKt.throwOnFailure(r7)
            goto L50
        L2a:
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r5)
            r5 = 0
            return r5
        L31:
            kotlin.ResultKt.throwOnFailure(r7)
            if (r6 == 0) goto L47
            if (r6 == r4) goto L45
            r5 = 2
            if (r6 == r5) goto L67
            r5 = 3
            if (r6 != r5) goto L3f
            goto L67
        L3f:
            java.lang.AssertionError r5 = new java.lang.AssertionError
            r5.<init>(r6)
            throw r5
        L45:
            r3 = r4
            goto L67
        L47:
            r0.label = r4
            java.lang.Object r7 = r5.getFrameMetadata(r0)
            if (r7 != r1) goto L50
            return r1
        L50:
            androidx.camera.camera2.pipe.FrameMetadata r7 = (androidx.camera.camera2.pipe.FrameMetadata) r7
            if (r7 == 0) goto L67
            android.hardware.camera2.CaptureResult$Key r5 = android.hardware.camera2.CaptureResult.CONTROL_AE_STATE
            java.lang.Object r5 = r7.get(r5)
            java.lang.Integer r5 = (java.lang.Integer) r5
            if (r5 != 0) goto L5f
            goto L67
        L5f:
            int r5 = r5.intValue()
            r6 = 4
            if (r5 != r6) goto L67
            goto L45
        L67:
            java.lang.Boolean r5 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r3)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl.isPhysicalFlashRequired(int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0019  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object waitForResult(long r17, kotlin.jvm.functions.Function1<? super androidx.camera.camera2.pipe.FrameInfo, java.lang.Boolean> r19, kotlin.coroutines.Continuation<? super androidx.camera.camera2.pipe.FrameInfo> r20) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r3 = r20
            boolean r4 = r3 instanceof androidx.camera.camera2.impl.CapturePipelineImpl.C01411
            if (r4 == 0) goto L19
            r4 = r3
            androidx.camera.camera2.impl.CapturePipelineImpl$waitForResult$1 r4 = (androidx.camera.camera2.impl.CapturePipelineImpl.C01411) r4
            int r5 = r4.label
            r6 = -2147483648(0xffffffff80000000, float:-0.0)
            r7 = r5 & r6
            if (r7 == 0) goto L19
            int r5 = r5 - r6
            r4.label = r5
            goto L1e
        L19:
            androidx.camera.camera2.impl.CapturePipelineImpl$waitForResult$1 r4 = new androidx.camera.camera2.impl.CapturePipelineImpl$waitForResult$1
            r4.<init>(r3)
        L1e:
            java.lang.Object r3 = r4.result
            java.lang.Object r5 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r6 = r4.label
            r7 = 0
            r8 = 1
            if (r6 == 0) goto L3a
            if (r6 != r8) goto L34
            java.lang.Object r1 = r4.L$0
            androidx.camera.camera2.impl.ResultListener r1 = (androidx.camera.camera2.impl.ResultListener) r1
            kotlin.ResultKt.throwOnFailure(r3)
            goto L78
        L34:
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r0)
            return r7
        L3a:
            kotlin.ResultKt.throwOnFailure(r3)
            androidx.camera.camera2.impl.ResultListener r3 = new androidx.camera.camera2.impl.ResultListener
            r6 = r19
            r3.<init>(r1, r6)
            androidx.camera.camera2.impl.ComboRequestListener r6 = r0.requestListener
            androidx.camera.camera2.impl.UseCaseThreads r9 = r0.threads
            java.util.concurrent.Executor r9 = r9.getSequentialExecutor()
            r6.addListener(r3, r9)
            androidx.camera.camera2.impl.UseCaseThreads r6 = r0.threads
            kotlinx.coroutines.CoroutineScope r9 = r6.getSequentialScope()
            androidx.camera.camera2.impl.CapturePipelineImpl$waitForResult$resultListener$1$1 r12 = new androidx.camera.camera2.impl.CapturePipelineImpl$waitForResult$resultListener$1$1
            r12.<init>(r3, r0, r7)
            r13 = 3
            r14 = 0
            r10 = 0
            r11 = 0
            kotlinx.coroutines.BuildersKt.launch$default(r9, r10, r11, r12, r13, r14)
            r9 = 1000000(0xf4240, double:4.940656E-318)
            long r1 = r1 / r9
            androidx.camera.camera2.impl.CapturePipelineImpl$waitForResult$3 r6 = new androidx.camera.camera2.impl.CapturePipelineImpl$waitForResult$3
            r6.<init>(r3, r7)
            r4.L$0 = r3
            r4.label = r8
            java.lang.Object r1 = kotlinx.coroutines.TimeoutKt.withTimeoutOrNull(r1, r6, r4)
            if (r1 != r5) goto L75
            return r5
        L75:
            r15 = r3
            r3 = r1
            r1 = r15
        L78:
            r2 = r3
            androidx.camera.camera2.pipe.FrameInfo r2 = (androidx.camera.camera2.pipe.FrameInfo) r2
            if (r2 != 0) goto L82
            androidx.camera.camera2.impl.ComboRequestListener r0 = r0.requestListener
            r0.removeListener(r1)
        L82:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.CapturePipelineImpl.waitForResult(long, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static boolean $r8$lambda$QUMNp0tfcXRSiLsQB0i9runZEEs(FrameInfo frameInfo) {
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Object waitForResult$default(CapturePipelineImpl capturePipelineImpl, long j, Function1 function1, Continuation continuation, int i, Object obj) {
        if ((i & 2) != 0) {
            function1 = new Function1() { // from class: androidx.camera.camera2.impl.CapturePipelineImpl$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    return Boolean.valueOf(CapturePipelineImpl.$r8$lambda$QUMNp0tfcXRSiLsQB0i9runZEEs((FrameInfo) obj2));
                }
            };
        }
        return capturePipelineImpl.waitForResult(j, function1, continuation);
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$waitForResult$3 */
    @kotlin.Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", "Landroidx/camera/camera2/pipe/FrameInfo;", "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.CapturePipelineImpl$waitForResult$3", m896f = "CapturePipeline.kt", m897i = {}, m898l = {796}, m899m = "invokeSuspend", m900n = {}, m902s = {}, m903v = 1)
    public static final class C01423 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super FrameInfo>, Object> {
        final /* synthetic */ ResultListener $resultListener;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01423(ResultListener resultListener, Continuation<? super C01423> continuation) {
            super(2, continuation);
            this.$resultListener = resultListener;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C01423(this.$resultListener, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super FrameInfo> continuation) {
            return ((C01423) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i != 0) {
                if (i == 1) {
                    ResultKt.throwOnFailure(obj);
                    return obj;
                }
                Segment$$ExternalSyntheticBUOutline1.m992m("call to 'resume' before 'invoke' with coroutine");
                return null;
            }
            ResultKt.throwOnFailure(obj);
            Deferred<FrameInfo> result = this.$resultListener.getResult();
            this.label = 1;
            Object objAwait = result.await(this);
            return objAwait == coroutine_suspended ? coroutine_suspended : objAwait;
        }
    }

    private final Object isTorchAsFlash(int i, Continuation<? super Boolean> continuation) {
        if (getTemplate() != 3 && i != 1) {
            return this.useTorchAsFlash.shouldUseTorchAsFlash(new C01342(null), continuation);
        }
        return Boxing.boxBoolean(true);
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.CapturePipelineImpl$isTorchAsFlash$2 */
    @kotlin.Metadata(m876d1 = {"\u0000\u0006\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001H\n"}, m877d2 = {"<anonymous>", "Landroidx/camera/camera2/pipe/FrameMetadata;"}, m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.impl.CapturePipelineImpl$isTorchAsFlash$2", m896f = "CapturePipeline.kt", m897i = {}, m898l = {808}, m899m = "invokeSuspend", m900n = {}, m902s = {}, m903v = 1)
    public static final class C01342 extends SuspendLambda implements Function1<Continuation<? super FrameMetadata>, Object> {
        int label;

        public C01342(Continuation<? super C01342> continuation) {
            super(1, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Continuation<?> continuation) {
            return CapturePipelineImpl.this.new C01342(continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Continuation<? super FrameMetadata> continuation) {
            return ((C01342) create(continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i != 0) {
                if (i == 1) {
                    ResultKt.throwOnFailure(obj);
                    return obj;
                }
                Segment$$ExternalSyntheticBUOutline1.m992m("call to 'resume' before 'invoke' with coroutine");
                return null;
            }
            ResultKt.throwOnFailure(obj);
            CapturePipelineImpl capturePipelineImpl = CapturePipelineImpl.this;
            this.label = 1;
            Object frameMetadata = capturePipelineImpl.getFrameMetadata(this);
            return frameMetadata == coroutine_suspended ? coroutine_suspended : frameMetadata;
        }
    }
}
