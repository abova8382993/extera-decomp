package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCharacteristics;
import android.os.Build;
import android.util.Log;
import androidx.camera.camera2.adapter.SupportedSurfaceCombination;
import androidx.camera.camera2.impl.Camera2ImplConfig;
import androidx.camera.camera2.impl.Camera2Logger;
import androidx.camera.camera2.pipe.CameraMetadata;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.AttachedSurfaceInfo;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.ImageCaptureConfig;
import androidx.camera.core.impl.ImageInputConfig;
import androidx.camera.core.impl.MutableOptionsBundle;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.StreamSpec;
import androidx.camera.core.impl.SurfaceConfig;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.streamsharing.StreamSharingConfig;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: loaded from: classes3.dex */
public final class StreamUseCaseUtil {
    public static final StreamUseCaseUtil INSTANCE = new StreamUseCaseUtil();
    private static final Config.Option STREAM_USE_CASE_STREAM_SPEC_OPTION;
    private static final Map STREAM_USE_CASE_TO_ELIGIBLE_CAPTURE_TYPES_MAP;
    private static final Map STREAM_USE_CASE_TO_ELIGIBLE_STREAM_SHARING_CHILDREN_TYPES_MAP;

    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[UseCaseConfigFactory.CaptureType.values().length];
            try {
                iArr[UseCaseConfigFactory.CaptureType.IMAGE_CAPTURE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[UseCaseConfigFactory.CaptureType.VIDEO_CAPTURE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[UseCaseConfigFactory.CaptureType.STREAM_SHARING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[UseCaseConfigFactory.CaptureType.PREVIEW.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[UseCaseConfigFactory.CaptureType.IMAGE_ANALYSIS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private StreamUseCaseUtil() {
    }

    static {
        Class cls = Long.TYPE;
        Intrinsics.checkNotNull(cls);
        Config.Option optionCreate = Config.Option.create("camera2.streamSpec.streamUseCase", cls);
        Intrinsics.checkNotNullExpressionValue(optionCreate, "create(...)");
        STREAM_USE_CASE_STREAM_SPEC_OPTION = optionCreate;
        Map mapCreateMapBuilder = MapsKt.createMapBuilder();
        int i = Build.VERSION.SDK_INT;
        if (i >= 33) {
            UseCaseConfigFactory.CaptureType captureType = UseCaseConfigFactory.CaptureType.PREVIEW;
            UseCaseConfigFactory.CaptureType captureType2 = UseCaseConfigFactory.CaptureType.METERING_REPEATING;
            UseCaseConfigFactory.CaptureType captureType3 = UseCaseConfigFactory.CaptureType.IMAGE_ANALYSIS;
            mapCreateMapBuilder.put(4L, SetsKt.setOf((Object[]) new UseCaseConfigFactory.CaptureType[]{captureType, captureType2, captureType3}));
            mapCreateMapBuilder.put(1L, SetsKt.setOf((Object[]) new UseCaseConfigFactory.CaptureType[]{captureType, captureType2, captureType3}));
            mapCreateMapBuilder.put(2L, SetsKt.setOf(UseCaseConfigFactory.CaptureType.IMAGE_CAPTURE));
            mapCreateMapBuilder.put(3L, SetsKt.setOf(UseCaseConfigFactory.CaptureType.VIDEO_CAPTURE));
        }
        STREAM_USE_CASE_TO_ELIGIBLE_CAPTURE_TYPES_MAP = MapsKt.build(mapCreateMapBuilder);
        Map mapCreateMapBuilder2 = MapsKt.createMapBuilder();
        if (i >= 33) {
            UseCaseConfigFactory.CaptureType captureType4 = UseCaseConfigFactory.CaptureType.PREVIEW;
            UseCaseConfigFactory.CaptureType captureType5 = UseCaseConfigFactory.CaptureType.VIDEO_CAPTURE;
            mapCreateMapBuilder2.put(4L, SetsKt.setOf((Object[]) new UseCaseConfigFactory.CaptureType[]{captureType4, UseCaseConfigFactory.CaptureType.IMAGE_CAPTURE, captureType5}));
            mapCreateMapBuilder2.put(3L, SetsKt.setOf((Object[]) new UseCaseConfigFactory.CaptureType[]{captureType4, captureType5}));
        }
        STREAM_USE_CASE_TO_ELIGIBLE_STREAM_SHARING_CHILDREN_TYPES_MAP = MapsKt.build(mapCreateMapBuilder2);
    }

    public final void populateSurfaceToStreamUseCaseMapping(Collection sessionConfigs, Collection useCaseConfigs, Map streamUseCaseMap) {
        Intrinsics.checkNotNullParameter(sessionConfigs, "sessionConfigs");
        Intrinsics.checkNotNullParameter(useCaseConfigs, "useCaseConfigs");
        Intrinsics.checkNotNullParameter(streamUseCaseMap, "streamUseCaseMap");
        ArrayList arrayList = new ArrayList(useCaseConfigs);
        Iterator it = sessionConfigs.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            SessionConfig sessionConfig = (SessionConfig) it.next();
            Config implementationOptions = sessionConfig.getImplementationOptions();
            Config.Option option = STREAM_USE_CASE_STREAM_SPEC_OPTION;
            if (implementationOptions.containsOption(option) && sessionConfig.getSurfaces().size() != 1) {
                Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
                if (Logger.isErrorEnabled("CXCP")) {
                    Log.e(Camera2Logger.TRUNCATED_TAG, "StreamUseCaseUtil: SessionConfig has stream use case but also contains " + sessionConfig.getSurfaces().size() + " surfaces, abort populateSurfaceToStreamUseCaseMapping().");
                    return;
                }
                return;
            }
            if (sessionConfig.getImplementationOptions().containsOption(option)) {
                Iterator it2 = sessionConfigs.iterator();
                int i = 0;
                while (it2.hasNext()) {
                    SessionConfig sessionConfig2 = (SessionConfig) it2.next();
                    if (((UseCaseConfig) arrayList.get(i)).getCaptureType() == UseCaseConfigFactory.CaptureType.METERING_REPEATING) {
                        Intrinsics.checkNotNullExpressionValue(sessionConfig2.getSurfaces(), "getSurfaces(...)");
                        Preconditions.checkState(!r4.isEmpty(), "MeteringRepeating should contain a surface");
                        streamUseCaseMap.put(sessionConfig2.getSurfaces().get(0), 1L);
                    } else {
                        Config implementationOptions2 = sessionConfig2.getImplementationOptions();
                        Config.Option option2 = STREAM_USE_CASE_STREAM_SPEC_OPTION;
                        if (implementationOptions2.containsOption(option2)) {
                            List surfaces = sessionConfig2.getSurfaces();
                            Intrinsics.checkNotNullExpressionValue(surfaces, "getSurfaces(...)");
                            if (!surfaces.isEmpty()) {
                                Object obj = sessionConfig2.getSurfaces().get(0);
                                Object objRetrieveOption = sessionConfig2.getImplementationOptions().retrieveOption(option2);
                                Intrinsics.checkNotNull(objRetrieveOption);
                                streamUseCaseMap.put(obj, objRetrieveOption);
                            }
                        }
                    }
                    i++;
                }
            }
        }
        Camera2Logger camera2Logger2 = Camera2Logger.INSTANCE;
        if (Logger.isDebugEnabled("CXCP")) {
            Log.d(Camera2Logger.TRUNCATED_TAG, "populateSurfaceToStreamUseCaseMapping() - streamUseCaseMap = " + streamUseCaseMap);
        }
    }

    public final Camera2ImplConfig getStreamSpecImplementationOptions(UseCaseConfig useCaseConfig) {
        Intrinsics.checkNotNullParameter(useCaseConfig, "useCaseConfig");
        MutableOptionsBundle mutableOptionsBundleCreate = MutableOptionsBundle.create();
        Intrinsics.checkNotNullExpressionValue(mutableOptionsBundleCreate, "create(...)");
        Config.Option option = Camera2ImplConfig.STREAM_USE_CASE_OPTION;
        if (useCaseConfig.containsOption(option)) {
            mutableOptionsBundleCreate.insertOption(option, useCaseConfig.retrieveOption(option));
        }
        Config.Option option2 = UseCaseConfig.OPTION_ZSL_DISABLED;
        if (useCaseConfig.containsOption(option2)) {
            mutableOptionsBundleCreate.insertOption(option2, useCaseConfig.retrieveOption(option2));
        }
        Config.Option option3 = ImageCaptureConfig.OPTION_IMAGE_CAPTURE_MODE;
        if (useCaseConfig.containsOption(option3)) {
            mutableOptionsBundleCreate.insertOption(option3, useCaseConfig.retrieveOption(option3));
        }
        Config.Option option4 = ImageInputConfig.OPTION_INPUT_FORMAT;
        if (useCaseConfig.containsOption(option4)) {
            mutableOptionsBundleCreate.insertOption(option4, useCaseConfig.retrieveOption(option4));
        }
        return new Camera2ImplConfig(mutableOptionsBundleCreate);
    }

    public final boolean isStreamUseCaseSupported(CameraMetadata cameraMetadata) {
        Intrinsics.checkNotNullParameter(cameraMetadata, "cameraMetadata");
        if (Build.VERSION.SDK_INT < 33) {
            return false;
        }
        CameraCharacteristics.Key SCALER_AVAILABLE_STREAM_USE_CASES = CameraCharacteristics.SCALER_AVAILABLE_STREAM_USE_CASES;
        Intrinsics.checkNotNullExpressionValue(SCALER_AVAILABLE_STREAM_USE_CASES, "SCALER_AVAILABLE_STREAM_USE_CASES");
        long[] jArr = (long[]) cameraMetadata.get(SCALER_AVAILABLE_STREAM_USE_CASES);
        return (jArr == null || jArr.length == 0) ? false : true;
    }

    public final boolean shouldUseStreamUseCase(SupportedSurfaceCombination.FeatureSettings featureSettings) {
        Intrinsics.checkNotNullParameter(featureSettings, "featureSettings");
        return featureSettings.getCameraMode() == 0 && featureSettings.getRequiredMaxBitDepth() == 8 && !featureSettings.isHighSpeedOn();
    }

    public final boolean populateStreamUseCaseStreamSpecOptionWithInteropOverride(CameraMetadata cameraMetadata, List attachedSurfaces, Map suggestedStreamSpecMap, Map attachedSurfaceStreamSpecMap) {
        Intrinsics.checkNotNullParameter(cameraMetadata, "cameraMetadata");
        Intrinsics.checkNotNullParameter(attachedSurfaces, "attachedSurfaces");
        Intrinsics.checkNotNullParameter(suggestedStreamSpecMap, "suggestedStreamSpecMap");
        Intrinsics.checkNotNullParameter(attachedSurfaceStreamSpecMap, "attachedSurfaceStreamSpecMap");
        int i = 0;
        if (Build.VERSION.SDK_INT < 33) {
            return false;
        }
        ArrayList arrayList = new ArrayList(suggestedStreamSpecMap.keySet());
        Iterator it = attachedSurfaces.iterator();
        while (it.hasNext()) {
            if (((AttachedSurfaceInfo) it.next()).getImplementationOptions() == null) {
                throw new IllegalStateException("Required value was null.");
            }
        }
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            Object obj2 = suggestedStreamSpecMap.get((UseCaseConfig) obj);
            if (obj2 == null) {
                throw new IllegalStateException("Required value was null.");
            }
            if (((StreamSpec) obj2).getImplementationOptions() == null) {
                throw new IllegalStateException("Required value was null.");
            }
        }
        CameraCharacteristics.Key SCALER_AVAILABLE_STREAM_USE_CASES = CameraCharacteristics.SCALER_AVAILABLE_STREAM_USE_CASES;
        Intrinsics.checkNotNullExpressionValue(SCALER_AVAILABLE_STREAM_USE_CASES, "SCALER_AVAILABLE_STREAM_USE_CASES");
        long[] jArr = (long[]) cameraMetadata.get(SCALER_AVAILABLE_STREAM_USE_CASES);
        if (jArr != null && jArr.length != 0) {
            Set hashSet = new HashSet();
            for (long j : jArr) {
                hashSet.add(Long.valueOf(j));
            }
            if (isValidCamera2InteropOverride(attachedSurfaces, arrayList, hashSet)) {
                Iterator it2 = attachedSurfaces.iterator();
                while (it2.hasNext()) {
                    AttachedSurfaceInfo attachedSurfaceInfo = (AttachedSurfaceInfo) it2.next();
                    Config implementationOptions = attachedSurfaceInfo.getImplementationOptions();
                    Intrinsics.checkNotNull(implementationOptions);
                    Config updatedImplementationOptionsWithUseCaseStreamSpecOption = getUpdatedImplementationOptionsWithUseCaseStreamSpecOption(implementationOptions, (Long) implementationOptions.retrieveOption(Camera2ImplConfig.STREAM_USE_CASE_OPTION));
                    if (updatedImplementationOptionsWithUseCaseStreamSpecOption != null) {
                        attachedSurfaceStreamSpecMap.put(attachedSurfaceInfo, attachedSurfaceInfo.toStreamSpec(updatedImplementationOptionsWithUseCaseStreamSpecOption));
                    }
                }
                int size2 = arrayList.size();
                while (i < size2) {
                    Object obj3 = arrayList.get(i);
                    i++;
                    UseCaseConfig useCaseConfig = (UseCaseConfig) obj3;
                    StreamSpec streamSpec = (StreamSpec) suggestedStreamSpecMap.get(useCaseConfig);
                    Intrinsics.checkNotNull(streamSpec);
                    Config implementationOptions2 = streamSpec.getImplementationOptions();
                    Intrinsics.checkNotNull(implementationOptions2);
                    Config updatedImplementationOptionsWithUseCaseStreamSpecOption2 = getUpdatedImplementationOptionsWithUseCaseStreamSpecOption(implementationOptions2, (Long) implementationOptions2.retrieveOption(Camera2ImplConfig.STREAM_USE_CASE_OPTION));
                    if (updatedImplementationOptionsWithUseCaseStreamSpecOption2 != null) {
                        suggestedStreamSpecMap.put(useCaseConfig, streamSpec.toBuilder().setImplementationOptions(updatedImplementationOptionsWithUseCaseStreamSpecOption2).build());
                    }
                }
                return true;
            }
        }
        return false;
    }

    public final boolean areStreamUseCasesAvailableForSurfaceConfigs(CameraMetadata cameraMetadata, List surfaceConfigs) {
        Intrinsics.checkNotNullParameter(cameraMetadata, "cameraMetadata");
        Intrinsics.checkNotNullParameter(surfaceConfigs, "surfaceConfigs");
        if (Build.VERSION.SDK_INT < 33) {
            return false;
        }
        CameraCharacteristics.Key SCALER_AVAILABLE_STREAM_USE_CASES = CameraCharacteristics.SCALER_AVAILABLE_STREAM_USE_CASES;
        Intrinsics.checkNotNullExpressionValue(SCALER_AVAILABLE_STREAM_USE_CASES, "SCALER_AVAILABLE_STREAM_USE_CASES");
        long[] jArr = (long[]) cameraMetadata.get(SCALER_AVAILABLE_STREAM_USE_CASES);
        if (jArr == null || jArr.length == 0) {
            return false;
        }
        HashSet hashSet = new HashSet();
        for (long j : jArr) {
            hashSet.add(Long.valueOf(j));
        }
        Iterator it = surfaceConfigs.iterator();
        while (it.hasNext()) {
            if (!hashSet.contains(Long.valueOf(((SurfaceConfig) it.next()).getStreamUseCase().getValue()))) {
                return false;
            }
        }
        return true;
    }

    private final boolean isEligibleCaptureType(UseCaseConfigFactory.CaptureType captureType, long j, List list) {
        if (Build.VERSION.SDK_INT < 33) {
            return false;
        }
        if (captureType == UseCaseConfigFactory.CaptureType.STREAM_SHARING) {
            Map map = STREAM_USE_CASE_TO_ELIGIBLE_STREAM_SHARING_CHILDREN_TYPES_MAP;
            if (!map.containsKey(Long.valueOf(j))) {
                return false;
            }
            Object obj = map.get(Long.valueOf(j));
            Intrinsics.checkNotNull(obj);
            Set set = (Set) obj;
            if (list.size() != set.size()) {
                return false;
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (!set.contains((UseCaseConfigFactory.CaptureType) it.next())) {
                    return false;
                }
            }
            return true;
        }
        Map map2 = STREAM_USE_CASE_TO_ELIGIBLE_CAPTURE_TYPES_MAP;
        if (map2.containsKey(Long.valueOf(j))) {
            Object obj2 = map2.get(Long.valueOf(j));
            Intrinsics.checkNotNull(obj2);
            if (((Set) obj2).contains(captureType)) {
                return true;
            }
        }
        return false;
    }

    public final boolean areCaptureTypesEligible(Map surfaceConfigIndexAttachedSurfaceInfoMap, Map surfaceConfigIndexUseCaseConfigMap, List surfaceConfigsWithStreamUseCase) {
        List listEmptyList;
        UseCaseConfigFactory.CaptureType captureType;
        Intrinsics.checkNotNullParameter(surfaceConfigIndexAttachedSurfaceInfoMap, "surfaceConfigIndexAttachedSurfaceInfoMap");
        Intrinsics.checkNotNullParameter(surfaceConfigIndexUseCaseConfigMap, "surfaceConfigIndexUseCaseConfigMap");
        Intrinsics.checkNotNullParameter(surfaceConfigsWithStreamUseCase, "surfaceConfigsWithStreamUseCase");
        int size = surfaceConfigsWithStreamUseCase.size();
        for (int i = 0; i < size; i++) {
            long value = ((SurfaceConfig) surfaceConfigsWithStreamUseCase.get(i)).getStreamUseCase().getValue();
            if (surfaceConfigIndexAttachedSurfaceInfoMap.containsKey(Integer.valueOf(i))) {
                AttachedSurfaceInfo attachedSurfaceInfo = (AttachedSurfaceInfo) surfaceConfigIndexAttachedSurfaceInfoMap.get(Integer.valueOf(i));
                Intrinsics.checkNotNull(attachedSurfaceInfo);
                if (attachedSurfaceInfo.getCaptureTypes().size() == 1) {
                    captureType = (UseCaseConfigFactory.CaptureType) attachedSurfaceInfo.getCaptureTypes().get(0);
                } else {
                    captureType = UseCaseConfigFactory.CaptureType.STREAM_SHARING;
                }
                Intrinsics.checkNotNull(captureType);
                List captureTypes = attachedSurfaceInfo.getCaptureTypes();
                Intrinsics.checkNotNullExpressionValue(captureTypes, "getCaptureTypes(...)");
                if (!isEligibleCaptureType(captureType, value, captureTypes)) {
                    return false;
                }
            } else if (surfaceConfigIndexUseCaseConfigMap.containsKey(Integer.valueOf(i))) {
                Object obj = surfaceConfigIndexUseCaseConfigMap.get(Integer.valueOf(i));
                Intrinsics.checkNotNull(obj);
                UseCaseConfig useCaseConfig = (UseCaseConfig) obj;
                UseCaseConfigFactory.CaptureType captureType2 = useCaseConfig.getCaptureType();
                Intrinsics.checkNotNullExpressionValue(captureType2, "getCaptureType(...)");
                if (useCaseConfig.getCaptureType() == UseCaseConfigFactory.CaptureType.STREAM_SHARING) {
                    listEmptyList = ((StreamSharingConfig) useCaseConfig).getCaptureTypes();
                    Intrinsics.checkNotNullExpressionValue(listEmptyList, "getCaptureTypes(...)");
                } else {
                    listEmptyList = CollectionsKt.emptyList();
                }
                if (!isEligibleCaptureType(captureType2, value, listEmptyList)) {
                    return false;
                }
            } else {
                throw new AssertionError("SurfaceConfig does not map to any use case");
            }
        }
        return true;
    }

    public final void populateStreamUseCaseStreamSpecOptionWithSupportedSurfaceConfigs(Map suggestedStreamSpecMap, Map attachedSurfaceStreamSpecMap, Map surfaceConfigIndexAttachedSurfaceInfoMap, Map surfaceConfigIndexUseCaseConfigMap, List surfaceConfigsWithStreamUseCase) {
        Intrinsics.checkNotNullParameter(suggestedStreamSpecMap, "suggestedStreamSpecMap");
        Intrinsics.checkNotNullParameter(attachedSurfaceStreamSpecMap, "attachedSurfaceStreamSpecMap");
        Intrinsics.checkNotNullParameter(surfaceConfigIndexAttachedSurfaceInfoMap, "surfaceConfigIndexAttachedSurfaceInfoMap");
        Intrinsics.checkNotNullParameter(surfaceConfigIndexUseCaseConfigMap, "surfaceConfigIndexUseCaseConfigMap");
        Intrinsics.checkNotNullParameter(surfaceConfigsWithStreamUseCase, "surfaceConfigsWithStreamUseCase");
        int size = surfaceConfigsWithStreamUseCase.size();
        for (int i = 0; i < size; i++) {
            long value = ((SurfaceConfig) surfaceConfigsWithStreamUseCase.get(i)).getStreamUseCase().getValue();
            if (surfaceConfigIndexAttachedSurfaceInfoMap.containsKey(Integer.valueOf(i))) {
                AttachedSurfaceInfo attachedSurfaceInfo = (AttachedSurfaceInfo) surfaceConfigIndexAttachedSurfaceInfoMap.get(Integer.valueOf(i));
                Intrinsics.checkNotNull(attachedSurfaceInfo);
                Config implementationOptions = attachedSurfaceInfo.getImplementationOptions();
                Intrinsics.checkNotNull(implementationOptions);
                Config updatedImplementationOptionsWithUseCaseStreamSpecOption = getUpdatedImplementationOptionsWithUseCaseStreamSpecOption(implementationOptions, Long.valueOf(value));
                if (updatedImplementationOptionsWithUseCaseStreamSpecOption != null) {
                    attachedSurfaceStreamSpecMap.put(attachedSurfaceInfo, attachedSurfaceInfo.toStreamSpec(updatedImplementationOptionsWithUseCaseStreamSpecOption));
                }
            } else if (surfaceConfigIndexUseCaseConfigMap.containsKey(Integer.valueOf(i))) {
                Object obj = surfaceConfigIndexUseCaseConfigMap.get(Integer.valueOf(i));
                Intrinsics.checkNotNull(obj);
                UseCaseConfig useCaseConfig = (UseCaseConfig) obj;
                StreamSpec streamSpec = (StreamSpec) suggestedStreamSpecMap.get(useCaseConfig);
                Intrinsics.checkNotNull(streamSpec);
                Config implementationOptions2 = streamSpec.getImplementationOptions();
                Intrinsics.checkNotNull(implementationOptions2);
                Config updatedImplementationOptionsWithUseCaseStreamSpecOption2 = getUpdatedImplementationOptionsWithUseCaseStreamSpecOption(implementationOptions2, Long.valueOf(value));
                if (updatedImplementationOptionsWithUseCaseStreamSpecOption2 != null) {
                    StreamSpec streamSpecBuild = streamSpec.toBuilder().setImplementationOptions(updatedImplementationOptionsWithUseCaseStreamSpecOption2).build();
                    Intrinsics.checkNotNullExpressionValue(streamSpecBuild, "build(...)");
                    suggestedStreamSpecMap.put(useCaseConfig, streamSpecBuild);
                }
            } else {
                throw new AssertionError("SurfaceConfig does not map to any use case");
            }
        }
    }

    private final Config getUpdatedImplementationOptionsWithUseCaseStreamSpecOption(Config config, Long l) {
        Config.Option option = STREAM_USE_CASE_STREAM_SPEC_OPTION;
        if (config.containsOption(option) && Intrinsics.areEqual(config.retrieveOption(option), l)) {
            return null;
        }
        MutableOptionsBundle mutableOptionsBundleFrom = MutableOptionsBundle.from(config);
        Intrinsics.checkNotNullExpressionValue(mutableOptionsBundleFrom, "from(...)");
        mutableOptionsBundleFrom.insertOption(option, l);
        return new Camera2ImplConfig(mutableOptionsBundleFrom);
    }

    public final boolean containsZslUseCase(List attachedSurfaces, List newUseCaseConfigs) {
        Intrinsics.checkNotNullParameter(attachedSurfaces, "attachedSurfaces");
        Intrinsics.checkNotNullParameter(newUseCaseConfigs, "newUseCaseConfigs");
        Iterator it = attachedSurfaces.iterator();
        while (it.hasNext()) {
            AttachedSurfaceInfo attachedSurfaceInfo = (AttachedSurfaceInfo) it.next();
            List captureTypes = attachedSurfaceInfo.getCaptureTypes();
            Intrinsics.checkNotNullExpressionValue(captureTypes, "getCaptureTypes(...)");
            UseCaseConfigFactory.CaptureType captureType = (UseCaseConfigFactory.CaptureType) captureTypes.get(0);
            Config implementationOptions = attachedSurfaceInfo.getImplementationOptions();
            Intrinsics.checkNotNull(implementationOptions);
            Intrinsics.checkNotNull(captureType);
            if (isZslUseCase(implementationOptions, captureType)) {
                return true;
            }
        }
        Iterator it2 = newUseCaseConfigs.iterator();
        while (it2.hasNext()) {
            UseCaseConfig useCaseConfig = (UseCaseConfig) it2.next();
            UseCaseConfigFactory.CaptureType captureType2 = useCaseConfig.getCaptureType();
            Intrinsics.checkNotNullExpressionValue(captureType2, "getCaptureType(...)");
            if (isZslUseCase(useCaseConfig, captureType2)) {
                return true;
            }
        }
        return false;
    }

    private final boolean isZslUseCase(Config config, UseCaseConfigFactory.CaptureType captureType) {
        Object objRetrieveOption = config.retrieveOption(UseCaseConfig.OPTION_ZSL_DISABLED, Boolean.FALSE);
        Intrinsics.checkNotNull(objRetrieveOption);
        if (((Boolean) objRetrieveOption).booleanValue()) {
            return false;
        }
        Config.Option option = ImageCaptureConfig.OPTION_IMAGE_CAPTURE_MODE;
        if (!config.containsOption(option)) {
            return false;
        }
        Object objRetrieveOption2 = config.retrieveOption(option);
        Intrinsics.checkNotNull(objRetrieveOption2);
        return getSessionConfigTemplateType(captureType, ((Number) objRetrieveOption2).intValue()) == 5;
    }

    private final boolean areStreamUseCasesAvailable(Set set, Set set2) {
        Iterator it = set2.iterator();
        while (it.hasNext()) {
            if (!set.contains(Long.valueOf(((Number) it.next()).longValue()))) {
                return false;
            }
        }
        return true;
    }

    private final void throwInvalidCamera2InteropOverrideException() {
        throw new IllegalArgumentException("Either all use cases must have non-default stream use case assigned or none should have it");
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final boolean isValidCamera2InteropOverride(java.util.List r10, java.util.List r11, java.util.Set r12) {
        /*
            r9 = this;
            java.util.LinkedHashSet r0 = new java.util.LinkedHashSet
            r0.<init>()
            java.util.Iterator r10 = r10.iterator()
            boolean r1 = r10.hasNext()
            r2 = 0
            r4 = 1
            r5 = 0
            if (r1 == 0) goto L47
            java.lang.Object r10 = r10.next()
            androidx.camera.core.impl.AttachedSurfaceInfo r10 = (androidx.camera.core.impl.AttachedSurfaceInfo) r10
            androidx.camera.core.impl.Config r1 = r10.getImplementationOptions()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1)
            androidx.camera.core.impl.Config$Option r6 = androidx.camera.camera2.impl.Camera2ImplConfig.STREAM_USE_CASE_OPTION
            boolean r1 = r1.containsOption(r6)
            if (r1 != 0) goto L2b
        L28:
            r1 = r4
            r10 = r5
            goto L49
        L2b:
            androidx.camera.core.impl.Config r10 = r10.getImplementationOptions()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r10)
            java.lang.Object r10 = r10.retrieveOption(r6)
            kotlin.jvm.internal.Intrinsics.checkNotNull(r10)
            java.lang.Number r10 = (java.lang.Number) r10
            long r6 = r10.longValue()
            int r10 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r10 != 0) goto L44
            goto L28
        L44:
            r10 = r4
            r1 = r5
            goto L49
        L47:
            r10 = r5
            r1 = r10
        L49:
            java.util.Iterator r11 = r11.iterator()
        L4d:
            boolean r6 = r11.hasNext()
            if (r6 == 0) goto L8d
            java.lang.Object r6 = r11.next()
            androidx.camera.core.impl.UseCaseConfig r6 = (androidx.camera.core.impl.UseCaseConfig) r6
            androidx.camera.core.impl.Config$Option r7 = androidx.camera.camera2.impl.Camera2ImplConfig.STREAM_USE_CASE_OPTION
            boolean r8 = r6.containsOption(r7)
            if (r8 != 0) goto L68
            if (r10 == 0) goto L66
            r9.throwInvalidCamera2InteropOverrideException()
        L66:
            r1 = r4
            goto L4d
        L68:
            java.lang.Object r6 = r6.retrieveOption(r7)
            kotlin.jvm.internal.Intrinsics.checkNotNull(r6)
            java.lang.Number r6 = (java.lang.Number) r6
            long r6 = r6.longValue()
            int r8 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r8 != 0) goto L7f
            if (r10 == 0) goto L66
            r9.throwInvalidCamera2InteropOverrideException()
            goto L66
        L7f:
            if (r1 == 0) goto L84
            r9.throwInvalidCamera2InteropOverrideException()
        L84:
            java.lang.Long r10 = java.lang.Long.valueOf(r6)
            r0.add(r10)
            r10 = r4
            goto L4d
        L8d:
            if (r1 != 0) goto L96
            boolean r10 = r9.areStreamUseCasesAvailable(r12, r0)
            if (r10 == 0) goto L96
            return r4
        L96:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.internal.StreamUseCaseUtil.isValidCamera2InteropOverride(java.util.List, java.util.List, java.util.Set):boolean");
    }

    private final int getSessionConfigTemplateType(UseCaseConfigFactory.CaptureType captureType, int i) {
        int i2 = WhenMappings.$EnumSwitchMapping$0[captureType.ordinal()];
        return i2 != 1 ? (i2 == 2 || i2 == 3) ? 3 : 1 : i == 2 ? 5 : 1;
    }
}
