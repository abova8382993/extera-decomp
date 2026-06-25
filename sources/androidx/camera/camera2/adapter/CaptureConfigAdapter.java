package androidx.camera.camera2.adapter;

import android.hardware.camera2.CaptureRequest;
import android.media.Image;
import androidx.camera.camera2.compat.workaround.TemplateParamsOverride;
import androidx.camera.camera2.config.UseCaseGraphContext;
import androidx.camera.camera2.impl.Camera2ImplConfig;
import androidx.camera.camera2.impl.Camera2ImplConfigKt;
import androidx.camera.camera2.impl.CameraCallbackMap;
import androidx.camera.camera2.impl.CameraProperties;
import androidx.camera.camera2.impl.TagsKt;
import androidx.camera.camera2.impl.UseCaseThreads;
import androidx.camera.camera2.pipe.CameraMetadata;
import androidx.camera.camera2.pipe.FrameInfo;
import androidx.camera.camera2.pipe.InputRequest;
import androidx.camera.camera2.pipe.Request;
import androidx.camera.camera2.pipe.RequestFailure;
import androidx.camera.camera2.pipe.RequestMetadata;
import androidx.camera.camera2.pipe.RequestTemplate;
import androidx.camera.camera2.pipe.StreamId;
import androidx.camera.camera2.pipe.media.AndroidImage;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CameraCaptureResult;
import androidx.camera.core.impl.CameraCaptureResults;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.DeferrableSurface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import okhttp3.OkHttpClient$$ExternalSyntheticBUOutline0;
import okhttp3.internal.url._UrlKt;
import okio.Segment$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes3.dex */
@Metadata(m876d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000  2\u00020\u0001:\u0001 B1\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0004\b\f\u0010\rJ7\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u000e\b\u0002\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019H\u0007¢\u0006\u0004\b\u001b\u0010\u001cJ\u0010\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!"}, m877d2 = {"Landroidx/camera/camera2/adapter/CaptureConfigAdapter;", _UrlKt.FRAGMENT_ENCODE_SET, "cameraProperties", "Landroidx/camera/camera2/impl/CameraProperties;", "useCaseGraphContext", "Landroidx/camera/camera2/config/UseCaseGraphContext;", "zslControl", "Landroidx/camera/camera2/adapter/ZslControl;", "threads", "Landroidx/camera/camera2/impl/UseCaseThreads;", "templateParamsOverride", "Landroidx/camera/camera2/compat/workaround/TemplateParamsOverride;", "<init>", "(Landroidx/camera/camera2/impl/CameraProperties;Landroidx/camera/camera2/config/UseCaseGraphContext;Landroidx/camera/camera2/adapter/ZslControl;Landroidx/camera/camera2/impl/UseCaseThreads;Landroidx/camera/camera2/compat/workaround/TemplateParamsOverride;)V", "isLegacyDevice", _UrlKt.FRAGMENT_ENCODE_SET, "mapToRequest", "Landroidx/camera/camera2/pipe/Request;", "captureConfig", "Landroidx/camera/core/impl/CaptureConfig;", "requestTemplate", "Landroidx/camera/camera2/pipe/RequestTemplate;", "sessionConfigOptions", "Landroidx/camera/core/impl/Config;", "additionalListeners", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/camera2/pipe/Request$Listener;", "mapToRequest-nAberiA", "(Landroidx/camera/core/impl/CaptureConfig;ILandroidx/camera/core/impl/Config;Ljava/util/List;)Landroidx/camera/camera2/pipe/Request;", "buildImageClosingRequestListener", "imageProxy", "Landroidx/camera/core/ImageProxy;", "Companion", "camera-camera2"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nCaptureConfigAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CaptureConfigAdapter.kt\nandroidx/camera/camera2/adapter/CaptureConfigAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,238:1\n1563#2:239\n1634#2,3:240\n1869#2,2:243\n1#3:245\n*S KotlinDebug\n*F\n+ 1 CaptureConfigAdapter.kt\nandroidx/camera/camera2/adapter/CaptureConfigAdapter\n*L\n83#1:239\n83#1:240,3\n91#1:243,2\n*E\n"})
public final class CaptureConfigAdapter {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final boolean isLegacyDevice;
    private final TemplateParamsOverride templateParamsOverride;
    private final UseCaseThreads threads;
    private final UseCaseGraphContext useCaseGraphContext;
    private final ZslControl zslControl;

    public CaptureConfigAdapter(CameraProperties cameraProperties, UseCaseGraphContext useCaseGraphContext, ZslControl zslControl, UseCaseThreads useCaseThreads, TemplateParamsOverride templateParamsOverride) {
        this.useCaseGraphContext = useCaseGraphContext;
        this.zslControl = zslControl;
        this.threads = useCaseThreads;
        this.templateParamsOverride = templateParamsOverride;
        this.isLegacyDevice = CameraMetadata.INSTANCE.isHardwareLevelLegacy(cameraProperties.getMetadata());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: mapToRequest-nAberiA */
    public final Request m1280mapToRequestnAberiA(CaptureConfig captureConfig, int requestTemplate, Config sessionConfigOptions, List<? extends Request.Listener> additionalListeners) {
        InputRequest inputRequest;
        ImageProxy imageProxyDequeueImageFromBuffer;
        Object objBuildImageClosingRequestListener;
        List<DeferrableSurface> surfaces = captureConfig.getSurfaces();
        Object inputRequest2 = null;
        if (surfaces.isEmpty()) {
            OkHttpClient$$ExternalSyntheticBUOutline0.m961m("Attempted to issue a capture without surfaces using ", captureConfig);
            return null;
        }
        List<DeferrableSurface> list = surfaces;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (DeferrableSurface deferrableSurface : list) {
            StreamId streamId = this.useCaseGraphContext.getSurfaceToStreamMap().get(deferrableSurface);
            if (streamId == null) {
                OkHttpClient$$ExternalSyntheticBUOutline0.m961m("Attempted to issue a capture with an unrecognized surface: ", deferrableSurface);
                return null;
            }
            arrayList.add(StreamId.m1670boximpl(streamId.getValue()));
        }
        CameraCallbackMap cameraCallbackMap = new CameraCallbackMap();
        Iterator<T> it = captureConfig.getCameraCaptureCallbacks().iterator();
        while (it.hasNext()) {
            cameraCallbackMap.addCaptureCallback((CameraCaptureCallback) it.next(), this.threads.getSequentialExecutor());
        }
        Config implementationOptions = captureConfig.getImplementationOptions();
        Camera2ImplConfig.Builder builder = new Camera2ImplConfig.Builder();
        builder.insertAllOptions(sessionConfigOptions);
        builder.insertAllOptions(implementationOptions);
        Config.Option<Integer> option = CaptureConfig.OPTION_ROTATION;
        if (implementationOptions.containsOption(option)) {
            builder.setCaptureRequestOption(CaptureRequest.JPEG_ORIENTATION, implementationOptions.retrieveOption(option));
        }
        Config.Option<Integer> option2 = CaptureConfig.OPTION_JPEG_QUALITY;
        if (implementationOptions.containsOption(option2)) {
            builder.setCaptureRequestOption(CaptureRequest.JPEG_QUALITY, Byte.valueOf((byte) ((Number) implementationOptions.retrieveOption(option2)).intValue()));
        }
        int iM1640constructorimpl = RequestTemplate.m1640constructorimpl(captureConfig.getTemplateType());
        if (captureConfig.getTemplateType() != 5 || this.zslControl.getIsZslDisabledByUseCaseConfig() || this.zslControl.getIsZslDisabledByFlashMode() || (imageProxyDequeueImageFromBuffer = this.zslControl.dequeueImageFromBuffer()) == null) {
            inputRequest = 0;
        } else {
            CameraCaptureResult cameraCaptureResultRetrieveCameraCaptureResult = CameraCaptureResults.retrieveCameraCaptureResult(imageProxyDequeueImageFromBuffer.getImageInfo());
            if (cameraCaptureResultRetrieveCameraCaptureResult == null) {
                objBuildImageClosingRequestListener = null;
            } else {
                if (!(cameraCaptureResultRetrieveCameraCaptureResult instanceof CaptureResultAdapter)) {
                    OkHttpClient$$ExternalSyntheticBUOutline0.m961m("Unexpected capture result type: ", cameraCaptureResultRetrieveCameraCaptureResult.getClass());
                    return null;
                }
                Image image = imageProxyDequeueImageFromBuffer.getImage();
                if (image != null) {
                    AndroidImage androidImage = new AndroidImage(image);
                    Object objUnwrapAs = ((CaptureResultAdapter) cameraCaptureResultRetrieveCameraCaptureResult).unwrapAs(Reflection.getOrCreateKotlinClass(FrameInfo.class));
                    if (objUnwrapAs == null) {
                        Segment$$ExternalSyntheticBUOutline1.m992m("Required value was null.");
                        return null;
                    }
                    inputRequest2 = new InputRequest(androidImage, (FrameInfo) objUnwrapAs);
                    objBuildImageClosingRequestListener = buildImageClosingRequestListener(imageProxyDequeueImageFromBuffer);
                } else {
                    Segment$$ExternalSyntheticBUOutline1.m992m("Required value was null.");
                    return null;
                }
            }
            inputRequest = inputRequest2;
            inputRequest2 = objBuildImageClosingRequestListener;
        }
        if (inputRequest == 0) {
            iM1640constructorimpl = INSTANCE.m1281getStillCaptureTemplateCMLptTo$camera_camera2(captureConfig, requestTemplate, this.isLegacyDevice);
        }
        Map mapPlus = MapsKt.plus(this.templateParamsOverride.mo1304getOverrideParamsxlOpshk(RequestTemplate.m1639boximpl(iM1640constructorimpl)), Camera2ImplConfigKt.toParameters(builder.build()));
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        listCreateListBuilder.add(cameraCallbackMap);
        if (inputRequest2 != null) {
            listCreateListBuilder.add(inputRequest2);
        }
        listCreateListBuilder.addAll(additionalListeners);
        return new Request(arrayList, mapPlus, MapsKt.mapOf(TuplesKt.m884to(TagsKt.getCAMERAX_TAG_BUNDLE(), captureConfig.getTagBundle())), CollectionsKt.build(listCreateListBuilder), RequestTemplate.m1639boximpl(iM1640constructorimpl), inputRequest, null);
    }

    private final Request.Listener buildImageClosingRequestListener(ImageProxy imageProxy) {
        return new Request.Listener() { // from class: androidx.camera.camera2.adapter.CaptureConfigAdapter.buildImageClosingRequestListener.1
            final /* synthetic */ AtomicReference<ImageProxy> $imageProxyToClose;

            public C01091(AtomicReference<ImageProxy> atomicReference) {
                atomicReference = atomicReference;
            }

            @Override // androidx.camera.camera2.pipe.Request.Listener
            /* JADX INFO: renamed from: onComplete-CcXjc1I */
            public void mo1282onCompleteCcXjc1I(RequestMetadata requestMetadata, long frameNumber, FrameInfo result) {
                CaptureConfigAdapter.buildImageClosingRequestListener$closeImageProxy(atomicReference);
            }

            @Override // androidx.camera.camera2.pipe.Request.Listener
            /* JADX INFO: renamed from: onFailed-CcXjc1I */
            public void mo1283onFailedCcXjc1I(RequestMetadata requestMetadata, long frameNumber, RequestFailure requestFailure) {
                CaptureConfigAdapter.buildImageClosingRequestListener$closeImageProxy(atomicReference);
            }

            @Override // androidx.camera.camera2.pipe.Request.Listener
            public void onAborted(Request request) {
                CaptureConfigAdapter.buildImageClosingRequestListener$closeImageProxy(atomicReference);
            }

            @Override // androidx.camera.camera2.pipe.Request.Listener
            /* JADX INFO: renamed from: onTotalCaptureResult-CcXjc1I */
            public void mo1284onTotalCaptureResultCcXjc1I(RequestMetadata requestMetadata, long frameNumber, FrameInfo totalCaptureResult) {
                CaptureConfigAdapter.buildImageClosingRequestListener$closeImageProxy(atomicReference);
            }
        };
    }

    public static final void buildImageClosingRequestListener$closeImageProxy(AtomicReference<ImageProxy> atomicReference) {
        ImageProxy andSet = atomicReference.getAndSet(null);
        if (andSet != null) {
            andSet.close();
        }
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.adapter.CaptureConfigAdapter$buildImageClosingRequestListener$1 */
    @Metadata(m876d1 = {"\u00005\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J'\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016¢\u0006\u0004\b\n\u0010\u000bJ'\u0010\f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000eH\u0016¢\u0006\u0004\b\u000f\u0010\u0010J\u0010\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J'\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\tH\u0016¢\u0006\u0004\b\u0016\u0010\u000b¨\u0006\u0017"}, m877d2 = {"androidx/camera/camera2/adapter/CaptureConfigAdapter$buildImageClosingRequestListener$1", "Landroidx/camera/camera2/pipe/Request$Listener;", "onComplete", _UrlKt.FRAGMENT_ENCODE_SET, "requestMetadata", "Landroidx/camera/camera2/pipe/RequestMetadata;", "frameNumber", "Landroidx/camera/camera2/pipe/FrameNumber;", "result", "Landroidx/camera/camera2/pipe/FrameInfo;", "onComplete-CcXjc1I", "(Landroidx/camera/camera2/pipe/RequestMetadata;JLandroidx/camera/camera2/pipe/FrameInfo;)V", "onFailed", "requestFailure", "Landroidx/camera/camera2/pipe/RequestFailure;", "onFailed-CcXjc1I", "(Landroidx/camera/camera2/pipe/RequestMetadata;JLandroidx/camera/camera2/pipe/RequestFailure;)V", "onAborted", "request", "Landroidx/camera/camera2/pipe/Request;", "onTotalCaptureResult", "totalCaptureResult", "onTotalCaptureResult-CcXjc1I", "camera-camera2"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    public static final class C01091 implements Request.Listener {
        final /* synthetic */ AtomicReference<ImageProxy> $imageProxyToClose;

        public C01091(AtomicReference<ImageProxy> atomicReference) {
            atomicReference = atomicReference;
        }

        @Override // androidx.camera.camera2.pipe.Request.Listener
        /* JADX INFO: renamed from: onComplete-CcXjc1I */
        public void mo1282onCompleteCcXjc1I(RequestMetadata requestMetadata, long frameNumber, FrameInfo result) {
            CaptureConfigAdapter.buildImageClosingRequestListener$closeImageProxy(atomicReference);
        }

        @Override // androidx.camera.camera2.pipe.Request.Listener
        /* JADX INFO: renamed from: onFailed-CcXjc1I */
        public void mo1283onFailedCcXjc1I(RequestMetadata requestMetadata, long frameNumber, RequestFailure requestFailure) {
            CaptureConfigAdapter.buildImageClosingRequestListener$closeImageProxy(atomicReference);
        }

        @Override // androidx.camera.camera2.pipe.Request.Listener
        public void onAborted(Request request) {
            CaptureConfigAdapter.buildImageClosingRequestListener$closeImageProxy(atomicReference);
        }

        @Override // androidx.camera.camera2.pipe.Request.Listener
        /* JADX INFO: renamed from: onTotalCaptureResult-CcXjc1I */
        public void mo1284onTotalCaptureResultCcXjc1I(RequestMetadata requestMetadata, long frameNumber, FrameInfo totalCaptureResult) {
            CaptureConfigAdapter.buildImageClosingRequestListener$closeImageProxy(atomicReference);
        }
    }

    @Metadata(m876d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J#\u0010\u0004\u001a\u00020\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH\u0000¢\u0006\u0004\b\n\u0010\u000b¨\u0006\f"}, m877d2 = {"Landroidx/camera/camera2/adapter/CaptureConfigAdapter$Companion;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "getStillCaptureTemplate", "Landroidx/camera/camera2/pipe/RequestTemplate;", "Landroidx/camera/core/impl/CaptureConfig;", "sessionTemplate", "isLegacyDevice", _UrlKt.FRAGMENT_ENCODE_SET, "getStillCaptureTemplate-CMLptTo$camera_camera2", "(Landroidx/camera/core/impl/CaptureConfig;IZ)I", "camera-camera2"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: renamed from: getStillCaptureTemplate-CMLptTo$camera_camera2 */
        public final int m1281getStillCaptureTemplateCMLptTo$camera_camera2(CaptureConfig captureConfig, int i, boolean z) {
            int i2;
            if (!RequestTemplate.m1642equalsimpl0(i, RequestTemplate.m1640constructorimpl(3)) || z) {
                i2 = (captureConfig.getTemplateType() == -1 || captureConfig.getTemplateType() == 5) ? 2 : -1;
            } else {
                i2 = 4;
            }
            if (i2 != -1) {
                return RequestTemplate.m1640constructorimpl(i2);
            }
            return RequestTemplate.m1640constructorimpl(captureConfig.getTemplateType());
        }
    }
}
