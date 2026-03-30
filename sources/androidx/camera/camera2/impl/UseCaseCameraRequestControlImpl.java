package androidx.camera.camera2.impl;

import android.hardware.camera2.CaptureRequest;
import android.util.Log;
import androidx.camera.camera2.config.UseCaseGraphContext;
import androidx.camera.camera2.impl.Camera2ImplConfig;
import androidx.camera.camera2.impl.CameraCallbackMap;
import androidx.camera.camera2.impl.UseCaseCameraRequestControl;
import androidx.camera.camera2.pipe.AeMode;
import androidx.camera.camera2.pipe.Lock3ABehavior;
import androidx.camera.camera2.pipe.RequestTemplate;
import androidx.camera.camera2.pipe.Result3A;
import androidx.camera.core.CameraXConfig;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.MutableTagBundle;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.StreamSpec;
import androidx.camera.core.impl.TagBundle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import javax.inject.Provider;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Job;

/* JADX INFO: loaded from: classes3.dex */
public final class UseCaseCameraRequestControlImpl implements UseCaseCameraRequestControl {
    private static final CompletableDeferred canceledResult;
    private final CameraXConfig cameraXConfig;
    private final Lazy capturePipeline$delegate;
    private final Provider capturePipelineProvider;
    private volatile boolean closed;
    private final Map infoBundleMap;
    private final UseCaseThreads threads;
    private final Lazy useCaseCameraState$delegate;
    private final Provider useCaseCameraStateProvider;
    private final UseCaseGraphContext useCaseGraphContext;
    private final Lazy useCaseSurfaceManager$delegate;
    private final Provider useCaseSurfaceManagerProvider;
    public static final Companion Companion = new Companion(null);
    private static final CompletableDeferred submitFailedResult = CompletableDeferredKt.CompletableDeferred(new Result3A(Result3A.Status.Companion.m1769getSUBMIT_FAILEDJvTi9ms(), null, 2, null));

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.UseCaseCameraRequestControlImpl$updateCameraStateAsync$1 */
    static final class C01751 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C01751(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return UseCaseCameraRequestControlImpl.this.updateCameraStateAsync(null, null, this);
        }
    }

    public UseCaseCameraRequestControlImpl(Provider capturePipelineProvider, Provider useCaseCameraStateProvider, UseCaseGraphContext useCaseGraphContext, Provider useCaseSurfaceManagerProvider, UseCaseThreads threads, CameraXConfig cameraXConfig) {
        Intrinsics.checkNotNullParameter(capturePipelineProvider, "capturePipelineProvider");
        Intrinsics.checkNotNullParameter(useCaseCameraStateProvider, "useCaseCameraStateProvider");
        Intrinsics.checkNotNullParameter(useCaseGraphContext, "useCaseGraphContext");
        Intrinsics.checkNotNullParameter(useCaseSurfaceManagerProvider, "useCaseSurfaceManagerProvider");
        Intrinsics.checkNotNullParameter(threads, "threads");
        this.capturePipelineProvider = capturePipelineProvider;
        this.useCaseCameraStateProvider = useCaseCameraStateProvider;
        this.useCaseGraphContext = useCaseGraphContext;
        this.useCaseSurfaceManagerProvider = useCaseSurfaceManagerProvider;
        this.threads = threads;
        this.cameraXConfig = cameraXConfig;
        Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
        if (Logger.isDebugEnabled("CXCP")) {
            Log.d(Camera2Logger.TRUNCATED_TAG, "Configured " + this);
        }
        this.capturePipeline$delegate = LazyKt.lazy(new Function0() { // from class: androidx.camera.camera2.impl.UseCaseCameraRequestControlImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return UseCaseCameraRequestControlImpl.capturePipeline_delegate$lambda$0(this.f$0);
            }
        });
        this.useCaseSurfaceManager$delegate = LazyKt.lazy(new Function0() { // from class: androidx.camera.camera2.impl.UseCaseCameraRequestControlImpl$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return UseCaseCameraRequestControlImpl.useCaseSurfaceManager_delegate$lambda$0(this.f$0);
            }
        });
        this.useCaseCameraState$delegate = LazyKt.lazy(new Function0() { // from class: androidx.camera.camera2.impl.UseCaseCameraRequestControlImpl$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return UseCaseCameraRequestControlImpl.useCaseCameraState_delegate$lambda$0(this.f$0);
            }
        });
        this.infoBundleMap = new LinkedHashMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CapturePipeline capturePipeline_delegate$lambda$0(UseCaseCameraRequestControlImpl useCaseCameraRequestControlImpl) {
        return (CapturePipeline) useCaseCameraRequestControlImpl.capturePipelineProvider.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final CapturePipeline getCapturePipeline() {
        return (CapturePipeline) this.capturePipeline$delegate.getValue();
    }

    private final UseCaseSurfaceManager getUseCaseSurfaceManager() {
        return (UseCaseSurfaceManager) this.useCaseSurfaceManager$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final UseCaseSurfaceManager useCaseSurfaceManager_delegate$lambda$0(UseCaseCameraRequestControlImpl useCaseCameraRequestControlImpl) {
        return (UseCaseSurfaceManager) useCaseCameraRequestControlImpl.useCaseSurfaceManagerProvider.get();
    }

    private final UseCaseCameraState getUseCaseCameraState() {
        return (UseCaseCameraState) this.useCaseCameraState$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final UseCaseCameraState useCaseCameraState_delegate$lambda$0(UseCaseCameraRequestControlImpl useCaseCameraRequestControlImpl) {
        return (UseCaseCameraState) useCaseCameraRequestControlImpl.useCaseCameraStateProvider.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class InfoBundle {
        private final Set listeners;
        private final Camera2ImplConfig.Builder options;
        private final Map tags;
        private RequestTemplate template;

        public /* synthetic */ InfoBundle(Camera2ImplConfig.Builder builder, Map map, Set set, RequestTemplate requestTemplate, DefaultConstructorMarker defaultConstructorMarker) {
            this(builder, map, set, requestTemplate);
        }

        /* JADX INFO: renamed from: copy-0am55g4$default, reason: not valid java name */
        public static /* synthetic */ InfoBundle m1475copy0am55g4$default(InfoBundle infoBundle, Camera2ImplConfig.Builder builder, Map map, Set set, RequestTemplate requestTemplate, int i, Object obj) {
            if ((i & 1) != 0) {
                builder = infoBundle.options;
            }
            if ((i & 2) != 0) {
                map = infoBundle.tags;
            }
            if ((i & 4) != 0) {
                set = infoBundle.listeners;
            }
            if ((i & 8) != 0) {
                requestTemplate = infoBundle.template;
            }
            return infoBundle.m1476copy0am55g4(builder, map, set, requestTemplate);
        }

        /* JADX INFO: renamed from: copy-0am55g4, reason: not valid java name */
        public final InfoBundle m1476copy0am55g4(Camera2ImplConfig.Builder options, Map tags, Set listeners, RequestTemplate requestTemplate) {
            Intrinsics.checkNotNullParameter(options, "options");
            Intrinsics.checkNotNullParameter(tags, "tags");
            Intrinsics.checkNotNullParameter(listeners, "listeners");
            return new InfoBundle(options, tags, listeners, requestTemplate, null);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof InfoBundle)) {
                return false;
            }
            InfoBundle infoBundle = (InfoBundle) obj;
            return Intrinsics.areEqual(this.options, infoBundle.options) && Intrinsics.areEqual(this.tags, infoBundle.tags) && Intrinsics.areEqual(this.listeners, infoBundle.listeners) && Intrinsics.areEqual(this.template, infoBundle.template);
        }

        public int hashCode() {
            int iHashCode = ((((this.options.hashCode() * 31) + this.tags.hashCode()) * 31) + this.listeners.hashCode()) * 31;
            RequestTemplate requestTemplate = this.template;
            return iHashCode + (requestTemplate == null ? 0 : RequestTemplate.m1758hashCodeimpl(requestTemplate.m1760unboximpl()));
        }

        public String toString() {
            return "InfoBundle(options=" + this.options + ", tags=" + this.tags + ", listeners=" + this.listeners + ", template=" + this.template + ')';
        }

        private InfoBundle(Camera2ImplConfig.Builder options, Map tags, Set listeners, RequestTemplate requestTemplate) {
            Intrinsics.checkNotNullParameter(options, "options");
            Intrinsics.checkNotNullParameter(tags, "tags");
            Intrinsics.checkNotNullParameter(listeners, "listeners");
            this.options = options;
            this.tags = tags;
            this.listeners = listeners;
            this.template = requestTemplate;
        }

        public /* synthetic */ InfoBundle(Camera2ImplConfig.Builder builder, Map map, Set set, RequestTemplate requestTemplate, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? new Camera2ImplConfig.Builder() : builder, (i & 2) != 0 ? new LinkedHashMap() : map, (i & 4) != 0 ? new LinkedHashSet() : set, (i & 8) != 0 ? null : requestTemplate, null);
        }

        public final Camera2ImplConfig.Builder getOptions() {
            return this.options;
        }

        public final Map getTags() {
            return this.tags;
        }

        public final Set getListeners() {
            return this.listeners;
        }

        /* JADX INFO: renamed from: getTemplate-ejQnlcg, reason: not valid java name */
        public final RequestTemplate m1477getTemplateejQnlcg() {
            return this.template;
        }

        /* JADX INFO: renamed from: setTemplate-xlOpshk, reason: not valid java name */
        public final void m1478setTemplatexlOpshk(RequestTemplate requestTemplate) {
            this.template = requestTemplate;
        }
    }

    private final InfoBundle withParameters(InfoBundle infoBundle, Map map, Config.OptionPriority optionPriority) {
        Camera2ImplConfig.Builder builder = new Camera2ImplConfig.Builder();
        builder.insertAllOptions(infoBundle.getOptions().getMutableConfig());
        builder.addAllCaptureRequestOptionsWithPriority(map, optionPriority);
        return InfoBundle.m1475copy0am55g4$default(infoBundle, builder, MapsKt.toMutableMap(infoBundle.getTags()), CollectionsKt.toMutableSet(infoBundle.getListeners()), null, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final InfoBundle withoutParameters(InfoBundle infoBundle, List list) {
        Camera2ImplConfig.Builder builder = new Camera2ImplConfig.Builder();
        builder.insertAllOptions(infoBundle.getOptions().getMutableConfig());
        builder.removeCaptureRequestOptions(list);
        return InfoBundle.m1475copy0am55g4$default(infoBundle, builder, MapsKt.toMutableMap(infoBundle.getTags()), CollectionsKt.toMutableSet(infoBundle.getListeners()), null, 8, null);
    }

    @Override // androidx.camera.camera2.impl.UseCaseCameraRequestControl
    public Deferred submitParameters(Map values, UseCaseCameraRequestControl.Type type, Config.OptionPriority optionPriority) {
        Intrinsics.checkNotNullParameter(values, "values");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(optionPriority, "optionPriority");
        if (this.closed) {
            return canceledResult;
        }
        this.threads.checkOnSequentialThread();
        return BuildersKt__Builders_commonKt.async$default(this.threads.getSequentialScope(), null, CoroutineStart.UNDISPATCHED, new C01741(type, values, optionPriority, null), 1, null);
    }

    /* JADX INFO: renamed from: androidx.camera.camera2.impl.UseCaseCameraRequestControlImpl$submitParameters$1 */
    static final class C01741 extends SuspendLambda implements Function2 {
        final /* synthetic */ Config.OptionPriority $optionPriority;
        final /* synthetic */ UseCaseCameraRequestControl.Type $type;
        final /* synthetic */ Map $values;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01741(UseCaseCameraRequestControl.Type type, Map map, Config.OptionPriority optionPriority, Continuation continuation) {
            super(2, continuation);
            this.$type = type;
            this.$values = map;
            this.$optionPriority = optionPriority;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UseCaseCameraRequestControlImpl.this.new C01741(this.$type, this.$values, this.$optionPriority, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01741) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x003a, code lost:
        
            if (((kotlinx.coroutines.Deferred) r7).await(r6) == r0) goto L15;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r7) {
            /*
                r6 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r6.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L1e
                if (r1 == r3) goto L1a
                if (r1 != r2) goto L12
                kotlin.ResultKt.throwOnFailure(r7)
                goto L3d
            L12:
                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r7.<init>(r0)
                throw r7
            L1a:
                kotlin.ResultKt.throwOnFailure(r7)
                goto L32
            L1e:
                kotlin.ResultKt.throwOnFailure(r7)
                androidx.camera.camera2.impl.UseCaseCameraRequestControlImpl r7 = androidx.camera.camera2.impl.UseCaseCameraRequestControlImpl.this
                androidx.camera.camera2.impl.UseCaseCameraRequestControl$Type r1 = r6.$type
                java.util.Map r4 = r6.$values
                androidx.camera.core.impl.Config$OptionPriority r5 = r6.$optionPriority
                r6.label = r3
                java.lang.Object r7 = androidx.camera.camera2.impl.UseCaseCameraRequestControlImpl.access$setParametersInternal(r7, r1, r4, r5, r6)
                if (r7 != r0) goto L32
                goto L3c
            L32:
                kotlinx.coroutines.Deferred r7 = (kotlinx.coroutines.Deferred) r7
                r6.label = r2
                java.lang.Object r7 = r7.await(r6)
                if (r7 != r0) goto L3d
            L3c:
                return r0
            L3d:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.UseCaseCameraRequestControlImpl.C01741.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object setParametersInternal(UseCaseCameraRequestControl.Type type, Map map, Config.OptionPriority optionPriority, Continuation continuation) {
        Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
        if (Logger.isDebugEnabled("CXCP")) {
            Log.d(Camera2Logger.TRUNCATED_TAG, "UseCaseCameraRequestControlImpl#setParametersAsync: [" + type + "] values = " + map + ", optionPriority = " + optionPriority);
        }
        Map map2 = this.infoBundleMap;
        Object obj = map2.get(type);
        if (obj == null) {
            InfoBundle infoBundle = new InfoBundle(null, null, null, null, 15, null);
            map2.put(type, infoBundle);
            obj = infoBundle;
        }
        this.infoBundleMap.put(type, withParameters((InfoBundle) obj, map, optionPriority));
        return updateCameraStateAsync$default(this, merge(this.infoBundleMap), null, continuation, 1, null);
    }

    @Override // androidx.camera.camera2.impl.UseCaseCameraRequestControl
    public Object awaitSurfaceSetup(Continuation continuation) {
        return getUseCaseSurfaceManager().awaitSetupCompletion(continuation);
    }

    @Override // androidx.camera.camera2.impl.UseCaseCameraRequestControl
    public void close() {
        this.closed = true;
        Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
        if (Logger.isDebugEnabled("CXCP")) {
            Log.d(Camera2Logger.TRUNCATED_TAG, "UseCaseCameraRequestControl: closed");
        }
        getUseCaseCameraState().close();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List failedResults(int i, String str) {
        ArrayList arrayList = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            CompletableDeferred completableDeferredCompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default(null, 1, null);
            completableDeferredCompletableDeferred$default.completeExceptionally(new ImageCaptureException(2, str, null));
            arrayList.add(completableDeferredCompletableDeferred$default);
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean hasInvalidSurface(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            CaptureConfig captureConfig = (CaptureConfig) it.next();
            if (captureConfig.getSurfaces().isEmpty()) {
                return true;
            }
            List surfaces = captureConfig.getSurfaces();
            Intrinsics.checkNotNullExpressionValue(surfaces, "getSurfaces(...)");
            Iterator it2 = surfaces.iterator();
            while (it2.hasNext()) {
                if (this.useCaseGraphContext.getSurfaceToStreamMap().get((DeferrableSurface) it2.next()) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final InfoBundle merge(Map map) {
        InfoBundle infoBundle = new InfoBundle(null, null, null, RequestTemplate.m1753boximpl(RequestTemplate.m1754constructorimpl(1)), 7, null);
        Iterator<E> it = UseCaseCameraRequestControl.Type.getEntries().iterator();
        while (it.hasNext()) {
            InfoBundle infoBundle2 = (InfoBundle) map.get((UseCaseCameraRequestControl.Type) it.next());
            if (infoBundle2 != null) {
                infoBundle.getOptions().insertAllOptions(infoBundle2.getOptions().getMutableConfig());
                infoBundle.getTags().putAll(infoBundle2.getTags());
                infoBundle.getListeners().addAll(infoBundle2.getListeners());
                RequestTemplate requestTemplateM1477getTemplateejQnlcg = infoBundle2.m1477getTemplateejQnlcg();
                if (requestTemplateM1477getTemplateejQnlcg != null) {
                    infoBundle.m1478setTemplatexlOpshk(RequestTemplate.m1753boximpl(requestTemplateM1477getTemplateejQnlcg.m1760unboximpl()));
                }
            }
        }
        return infoBundle;
    }

    private final TagBundle toTagBundle(InfoBundle infoBundle) {
        MutableTagBundle mutableTagBundleCreate = MutableTagBundle.create();
        for (Map.Entry entry : infoBundle.getTags().entrySet()) {
            mutableTagBundleCreate.putTag((String) entry.getKey(), entry.getValue());
        }
        Intrinsics.checkNotNullExpressionValue(mutableTagBundleCreate, "also(...)");
        return mutableTagBundleCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:30:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00a1 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object updateCameraStateAsync(androidx.camera.camera2.impl.UseCaseCameraRequestControlImpl.InfoBundle r11, java.util.Set r12, kotlin.coroutines.Continuation r13) {
        /*
            r10 = this;
            boolean r0 = r13 instanceof androidx.camera.camera2.impl.UseCaseCameraRequestControlImpl.C01751
            if (r0 == 0) goto L14
            r0 = r13
            androidx.camera.camera2.impl.UseCaseCameraRequestControlImpl$updateCameraStateAsync$1 r0 = (androidx.camera.camera2.impl.UseCaseCameraRequestControlImpl.C01751) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L14
            int r1 = r1 - r2
            r0.label = r1
        L12:
            r9 = r0
            goto L1a
        L14:
            androidx.camera.camera2.impl.UseCaseCameraRequestControlImpl$updateCameraStateAsync$1 r0 = new androidx.camera.camera2.impl.UseCaseCameraRequestControlImpl$updateCameraStateAsync$1
            r0.<init>(r13)
            goto L12
        L1a:
            java.lang.Object r13 = r9.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r9.label
            r2 = 1
            if (r1 == 0) goto L33
            if (r1 != r2) goto L2b
            kotlin.ResultKt.throwOnFailure(r13)
            goto L98
        L2b:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L33:
            kotlin.ResultKt.throwOnFailure(r13)
            boolean r13 = r10.closed
            if (r13 != 0) goto L9b
            androidx.camera.core.CameraXConfig r13 = r10.cameraXConfig
            if (r13 == 0) goto L41
            androidx.camera.camera2.interop.Camera2CaptureRequestConfiguratorKt.getCamera2CaptureRequestConfigurator(r13)
        L41:
            androidx.camera.camera2.impl.CapturePipeline r13 = r10.getCapturePipeline()
            androidx.camera.camera2.pipe.RequestTemplate r1 = r11.m1477getTemplateejQnlcg()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1)
            int r1 = r1.m1760unboximpl()
            r3 = -1
            if (r1 == r3) goto L5f
            androidx.camera.camera2.pipe.RequestTemplate r1 = r11.m1477getTemplateejQnlcg()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1)
            int r1 = r1.m1760unboximpl()
            goto L60
        L5f:
            r1 = r2
        L60:
            r13.setTemplate(r1)
            androidx.camera.camera2.impl.UseCaseCameraState r1 = r10.getUseCaseCameraState()
            androidx.camera.camera2.impl.Camera2ImplConfig$Builder r13 = r11.getOptions()
            androidx.camera.camera2.impl.Camera2ImplConfig r13 = r13.build()
            java.util.Map r13 = androidx.camera.camera2.impl.Camera2ImplConfigKt.toParameters(r13)
            androidx.camera.camera2.pipe.Metadata$Key r3 = androidx.camera.camera2.impl.TagsKt.getCAMERAX_TAG_BUNDLE()
            androidx.camera.core.impl.TagBundle r4 = r10.toTagBundle(r11)
            kotlin.Pair r3 = kotlin.TuplesKt.m1081to(r3, r4)
            java.util.Map r4 = kotlin.collections.MapsKt.mapOf(r3)
            androidx.camera.camera2.pipe.RequestTemplate r7 = r11.m1477getTemplateejQnlcg()
            java.util.Set r8 = r11.getListeners()
            r9.label = r2
            r3 = 0
            r5 = 0
            r6 = r12
            r2 = r13
            java.lang.Object r13 = r1.m1479updateAsyncTp9XwKQ(r2, r3, r4, r5, r6, r7, r8, r9)
            if (r13 != r0) goto L98
            return r0
        L98:
            kotlinx.coroutines.Deferred r13 = (kotlinx.coroutines.Deferred) r13
            goto L9c
        L9b:
            r13 = 0
        L9c:
            if (r13 != 0) goto La1
            kotlinx.coroutines.CompletableDeferred r11 = androidx.camera.camera2.impl.UseCaseCameraRequestControlImpl.canceledResult
            return r11
        La1:
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.impl.UseCaseCameraRequestControlImpl.updateCameraStateAsync(androidx.camera.camera2.impl.UseCaseCameraRequestControlImpl$InfoBundle, java.util.Set, kotlin.coroutines.Continuation):java.lang.Object");
    }

    static /* synthetic */ Object updateCameraStateAsync$default(UseCaseCameraRequestControlImpl useCaseCameraRequestControlImpl, InfoBundle infoBundle, Set set, Continuation continuation, int i, Object obj) {
        if ((i & 1) != 0) {
            set = null;
        }
        return useCaseCameraRequestControlImpl.updateCameraStateAsync(infoBundle, set, continuation);
    }

    @Override // androidx.camera.camera2.impl.UseCaseCameraRequestControl
    public Deferred cancelFocusAndMeteringAsync() {
        Deferred deferredRunOnSequential = this.closed ? null : runOnSequential(new UseCaseCameraRequestControlImpl$cancelFocusAndMeteringAsync$1$1(this, null));
        return deferredRunOnSequential == null ? submitFailedResult : deferredRunOnSequential;
    }

    @Override // androidx.camera.camera2.impl.UseCaseCameraRequestControl
    public List issueSingleCaptureAsync(List captureSequence, int i, int i2, int i3) {
        List list;
        List listRunOnSequentialList;
        Intrinsics.checkNotNullParameter(captureSequence, "captureSequence");
        if (this.closed) {
            list = captureSequence;
            listRunOnSequentialList = null;
        } else {
            list = captureSequence;
            listRunOnSequentialList = runOnSequentialList(captureSequence.size(), new UseCaseCameraRequestControlImpl$issueSingleCaptureAsync$1$1(this, list, i, i2, i3, null));
        }
        return listRunOnSequentialList == null ? failedResults(list.size(), "Capture request is cancelled on closed CameraGraph") : listRunOnSequentialList;
    }

    @Override // androidx.camera.camera2.impl.UseCaseCameraRequestControl
    public Deferred removeParametersAsync(List keys, UseCaseCameraRequestControl.Type type) {
        Intrinsics.checkNotNullParameter(keys, "keys");
        Intrinsics.checkNotNullParameter(type, "type");
        Deferred deferredRunOnSequential = this.closed ? null : runOnSequential(new UseCaseCameraRequestControlImpl$removeParametersAsync$1$1(this, type, keys, null));
        return deferredRunOnSequential == null ? canceledResult : deferredRunOnSequential;
    }

    @Override // androidx.camera.camera2.impl.UseCaseCameraRequestControl
    public Deferred setParametersAsync(Map values, UseCaseCameraRequestControl.Type type, Config.OptionPriority optionPriority) {
        Intrinsics.checkNotNullParameter(values, "values");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(optionPriority, "optionPriority");
        Deferred deferredRunOnSequential = !this.closed ? runOnSequential(new UseCaseCameraRequestControlImpl$setParametersAsync$1$1(this, type, values, optionPriority, null)) : null;
        return deferredRunOnSequential == null ? canceledResult : deferredRunOnSequential;
    }

    @Override // androidx.camera.camera2.impl.UseCaseCameraRequestControl
    /* JADX INFO: renamed from: setTorchOffAsync-MtizInI */
    public Deferred mo1444setTorchOffAsyncMtizInI(int i) {
        Deferred deferredRunOnSequential = this.closed ? null : runOnSequential(new UseCaseCameraRequestControlImpl$setTorchOffAsync$1$1(this, i, null));
        return deferredRunOnSequential == null ? submitFailedResult : deferredRunOnSequential;
    }

    @Override // androidx.camera.camera2.impl.UseCaseCameraRequestControl
    public Deferred setTorchOnAsync() {
        Deferred deferredRunOnSequential = this.closed ? null : runOnSequential(new UseCaseCameraRequestControlImpl$setTorchOnAsync$1$1(this, null));
        return deferredRunOnSequential == null ? submitFailedResult : deferredRunOnSequential;
    }

    @Override // androidx.camera.camera2.impl.UseCaseCameraRequestControl
    /* JADX INFO: renamed from: startFocusAndMeteringAsync-NxRnBj4 */
    public Deferred mo1445startFocusAndMeteringAsyncNxRnBj4(List list, List list2, List list3, Lock3ABehavior lock3ABehavior, Lock3ABehavior lock3ABehavior2, Lock3ABehavior lock3ABehavior3, AeMode aeMode, long j) {
        Deferred deferredRunOnSequential = !this.closed ? runOnSequential(new UseCaseCameraRequestControlImpl$startFocusAndMeteringAsync$1$1(this, list, list2, list3, lock3ABehavior, lock3ABehavior2, lock3ABehavior3, aeMode, j, null)) : null;
        return deferredRunOnSequential == null ? submitFailedResult : deferredRunOnSequential;
    }

    @Override // androidx.camera.camera2.impl.UseCaseCameraRequestControl
    public Deferred update3aRegions(List list, List list2, List list3) {
        Deferred deferredRunOnSequential = !this.closed ? runOnSequential(new UseCaseCameraRequestControlImpl$update3aRegions$1$1(this, list, list2, list3, null)) : null;
        return deferredRunOnSequential == null ? submitFailedResult : deferredRunOnSequential;
    }

    @Override // androidx.camera.camera2.impl.UseCaseCameraRequestControl
    public Deferred updateCamera2ConfigAsync(Config config, Map tags) {
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(tags, "tags");
        Deferred deferredRunOnSequential = this.closed ? null : runOnSequential(new UseCaseCameraRequestControlImpl$updateCamera2ConfigAsync$1$1(this, config, tags, null));
        return deferredRunOnSequential == null ? canceledResult : deferredRunOnSequential;
    }

    @Override // androidx.camera.camera2.impl.UseCaseCameraRequestControl
    public Deferred updateRepeatingRequestAsync(boolean z, Collection runningUseCases) {
        Intrinsics.checkNotNullParameter(runningUseCases, "runningUseCases");
        Deferred deferredRunOnSequential = this.closed ? null : runOnSequential(new UseCaseCameraRequestControlImpl$updateRepeatingRequestAsync$1$1(runningUseCases, z, this, null));
        return deferredRunOnSequential == null ? canceledResult : deferredRunOnSequential;
    }

    private final Deferred runOnSequential(Function1 function1) {
        CoroutineStart coroutineStartDetermineStartStrategy$camera_camera2 = determineStartStrategy$camera_camera2(this.threads);
        UseCaseThreads useCaseThreads = this.threads;
        CompletableDeferred completableDeferredCompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default(null, 1, null);
        BuildersKt__Builders_commonKt.launch$default(useCaseThreads.getSequentialScope(), null, coroutineStartDetermineStartStrategy$camera_camera2, new C0172x5900dd56(function1, completableDeferredCompletableDeferred$default, null), 1, null);
        return completableDeferredCompletableDeferred$default;
    }

    private final List runOnSequentialList(int i, Function1 function1) {
        CoroutineStart coroutineStartDetermineStartStrategy$camera_camera2 = determineStartStrategy$camera_camera2(this.threads);
        UseCaseThreads useCaseThreads = this.threads;
        ArrayList arrayList = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(CompletableDeferredKt.CompletableDeferred$default(null, 1, null));
        }
        BuildersKt__Builders_commonKt.launch$default(useCaseThreads.getSequentialScope(), null, coroutineStartDetermineStartStrategy$camera_camera2, new C0173x371fa696(function1, arrayList, null), 1, null);
        return arrayList;
    }

    public final CoroutineStart determineStartStrategy$camera_camera2(UseCaseThreads useCaseThreads) {
        Intrinsics.checkNotNullParameter(useCaseThreads, "<this>");
        return useCaseThreads.isOnSequentialThread() ? CoroutineStart.UNDISPATCHED : CoroutineStart.DEFAULT;
    }

    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Camera2ImplConfig.Builder extractCamera2ImplConfigBuilder(SessionConfig sessionConfig) {
            Intrinsics.checkNotNullParameter(sessionConfig, "<this>");
            Camera2ImplConfig.Builder builder = new Camera2ImplConfig.Builder();
            if (!Intrinsics.areEqual(sessionConfig.getExpectedFrameRateRange(), StreamSpec.FRAME_RATE_RANGE_UNSPECIFIED)) {
                CaptureRequest.Key CONTROL_AE_TARGET_FPS_RANGE = CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE;
                Intrinsics.checkNotNullExpressionValue(CONTROL_AE_TARGET_FPS_RANGE, "CONTROL_AE_TARGET_FPS_RANGE");
                builder.setCaptureRequestOption(CONTROL_AE_TARGET_FPS_RANGE, sessionConfig.getExpectedFrameRateRange());
            }
            Config implementationOptions = sessionConfig.getImplementationOptions();
            Intrinsics.checkNotNullExpressionValue(implementationOptions, "getImplementationOptions(...)");
            builder.insertAllOptions(implementationOptions);
            return builder;
        }

        public final Map extractTags(SessionConfig sessionConfig) {
            Intrinsics.checkNotNullParameter(sessionConfig, "<this>");
            TagBundle tagBundle = sessionConfig.getRepeatingCaptureConfig().getTagBundle();
            Intrinsics.checkNotNullExpressionValue(tagBundle, "getTagBundle(...)");
            return MapsKt.toMutableMap(UseCaseCameraRequestControlKt.toMap(tagBundle));
        }

        public final Set extractListeners(SessionConfig sessionConfig, Executor callbackExecutor) {
            Intrinsics.checkNotNullParameter(sessionConfig, "<this>");
            Intrinsics.checkNotNullParameter(callbackExecutor, "callbackExecutor");
            CameraCallbackMap.Companion companion = CameraCallbackMap.Companion;
            List repeatingCameraCaptureCallbacks = sessionConfig.getRepeatingCameraCaptureCallbacks();
            Intrinsics.checkNotNullExpressionValue(repeatingCameraCaptureCallbacks, "getRepeatingCameraCaptureCallbacks(...)");
            return SetsKt.mutableSetOf(companion.createFor(repeatingCameraCaptureCallbacks, callbackExecutor));
        }

        /* JADX INFO: renamed from: extractTemplate-ARED-Gk, reason: not valid java name */
        public final int m1474extractTemplateAREDGk(SessionConfig extractTemplate) {
            Intrinsics.checkNotNullParameter(extractTemplate, "$this$extractTemplate");
            return RequestTemplate.m1754constructorimpl(extractTemplate.getTemplateType());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final InfoBundle toInfoBundle(SessionConfig sessionConfig, Executor executor) {
            return new InfoBundle(extractCamera2ImplConfigBuilder(sessionConfig), extractTags(sessionConfig), extractListeners(sessionConfig, executor), RequestTemplate.m1753boximpl(m1474extractTemplateAREDGk(sessionConfig)), null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Camera2ImplConfig.Builder extractCamera2ImplConfigBuilder(Config config) {
            Camera2ImplConfig.Builder builder = new Camera2ImplConfig.Builder();
            builder.insertAllOptions(config);
            return builder;
        }
    }

    static {
        CompletableDeferred completableDeferredCompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default(null, 1, null);
        Job.DefaultImpls.cancel$default(completableDeferredCompletableDeferred$default, null, 1, null);
        canceledResult = completableDeferredCompletableDeferred$default;
    }
}
