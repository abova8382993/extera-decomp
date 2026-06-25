package androidx.camera.camera2.adapter;

import android.graphics.Rect;
import android.hardware.camera2.CameraCharacteristics;
import android.os.Build;
import android.util.Log;
import android.util.Range;
import android.util.Size;
import androidx.camera.camera2.compat.DynamicRangeProfilesCompat;
import androidx.camera.camera2.compat.StreamConfigurationMapCompat;
import androidx.camera.camera2.compat.quirk.CameraQuirks;
import androidx.camera.camera2.compat.workaround.FlashAvailabilityCheckerKt;
import androidx.camera.camera2.config.CameraConfig;
import androidx.camera.camera2.impl.Camera2Logger;
import androidx.camera.camera2.impl.CameraCallbackMap;
import androidx.camera.camera2.impl.CameraPipeCameraProperties;
import androidx.camera.camera2.impl.CameraProperties;
import androidx.camera.camera2.impl.DeviceInfoLogger;
import androidx.camera.camera2.impl.FocusMeteringControl;
import androidx.camera.camera2.internal.IntrinsicZoomCalculator;
import androidx.camera.camera2.interop.Camera2CameraInfo;
import androidx.camera.camera2.pipe.CameraId;
import androidx.camera.camera2.pipe.CameraMetadata;
import androidx.camera.camera2.pipe.UnsafeWrapper;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraState;
import androidx.camera.core.DynamicRange;
import androidx.camera.core.Logger;
import androidx.camera.core.ZoomState;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.EncoderProfilesProvider;
import androidx.camera.core.impl.Quirks;
import androidx.camera.core.impl.Timebase;
import androidx.camera.core.impl.utils.CameraOrientationUtil;
import androidx.camera.core.internal.StreamSpecsCalculator;
import androidx.view.LiveData;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KClass;
import okhttp3.internal.url._UrlKt;
import org.scilab.forge.jlatexmath.TeXSymbolParser;

/* JADX INFO: loaded from: classes3.dex */
@Metadata(m876d1 = {"\u0000è\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u000e\n\u0002\u0010#\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\b\u0007\u0018\u0000 z2\u00020\u00012\u00020\u0002:\u0001zBa\b\u0007\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0006\u0010\f\u001a\u00020\u000b\u0012\u0006\u0010\u000e\u001a\u00020\r\u0012\u0006\u0010\u0010\u001a\u00020\u000f\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u0012\u0006\u0010\u0016\u001a\u00020\u0015\u0012\u0006\u0010\u0018\u001a\u00020\u0017¢\u0006\u0004\b\u0019\u0010\u001aJ\u0017\u0010\u001d\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001bH\u0003¢\u0006\u0004\b\u001d\u0010\u001eJ\u0015\u0010!\u001a\b\u0012\u0004\u0012\u00020 0\u001fH\u0016¢\u0006\u0004\b!\u0010\"J\u000f\u0010$\u001a\u00020#H\u0016¢\u0006\u0004\b$\u0010%J\u000f\u0010&\u001a\u00020\u001bH\u0016¢\u0006\u0004\b&\u0010'J\u000f\u0010)\u001a\u00020(H\u0017¢\u0006\u0004\b)\u0010*J\u000f\u0010,\u001a\u00020+H\u0016¢\u0006\u0004\b,\u0010-J\u000f\u0010.\u001a\u00020\u001bH\u0016¢\u0006\u0004\b.\u0010'J\u000f\u0010/\u001a\u00020(H\u0016¢\u0006\u0004\b/\u0010*J\u0017\u0010.\u001a\u00020\u001b2\u0006\u00100\u001a\u00020\u001bH\u0016¢\u0006\u0004\b.\u0010\u001eJ\u0015\u00103\u001a\b\u0012\u0004\u0012\u00020201H\u0016¢\u0006\u0004\b3\u00104J\u0015\u00106\u001a\b\u0012\u0004\u0012\u00020501H\u0016¢\u0006\u0004\b6\u00104J\u000f\u00107\u001a\u00020\u0011H\u0016¢\u0006\u0004\b7\u00108J\u000f\u0010:\u001a\u000209H\u0016¢\u0006\u0004\b:\u0010;J\u0015\u0010<\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001fH\u0016¢\u0006\u0004\b<\u0010\"J\u001d\u0010@\u001a\b\u0012\u0004\u0012\u00020?0>2\u0006\u0010=\u001a\u00020\u001bH\u0016¢\u0006\u0004\b@\u0010AJ\u001d\u0010B\u001a\b\u0012\u0004\u0012\u00020?0>2\u0006\u0010=\u001a\u00020\u001bH\u0016¢\u0006\u0004\bB\u0010AJ)\u0010G\u001a\u0004\u0018\u00018\u0000\"\b\b\u0000\u0010D*\u00020C2\f\u0010F\u001a\b\u0012\u0004\u0012\u00028\u00000EH\u0017¢\u0006\u0004\bG\u0010HJ\u000f\u0010I\u001a\u00020#H\u0016¢\u0006\u0004\bI\u0010%J\u000f\u0010K\u001a\u00020JH\u0016¢\u0006\u0004\bK\u0010LJ\u001b\u0010N\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0M0\u001fH\u0016¢\u0006\u0004\bN\u0010\"J\u0015\u0010P\u001a\b\u0012\u0004\u0012\u00020O0\u001fH\u0016¢\u0006\u0004\bP\u0010\"J\u000f\u0010Q\u001a\u00020(H\u0016¢\u0006\u0004\bQ\u0010*J\u001b\u0010R\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0M0\u001fH\u0016¢\u0006\u0004\bR\u0010\"J\u0015\u0010S\u001a\b\u0012\u0004\u0012\u00020?0>H\u0016¢\u0006\u0004\bS\u0010TJ#\u0010V\u001a\b\u0012\u0004\u0012\u00020?0>2\f\u0010U\u001a\b\u0012\u0004\u0012\u00020\u001b0MH\u0016¢\u0006\u0004\bV\u0010WJ\u000f\u0010Y\u001a\u00020XH\u0016¢\u0006\u0004\bY\u0010ZJ\u000f\u0010[\u001a\u00020(H\u0016¢\u0006\u0004\b[\u0010*J\u000f\u0010\\\u001a\u00020(H\u0016¢\u0006\u0004\b\\\u0010*J\u000f\u0010^\u001a\u00020]H\u0016¢\u0006\u0004\b^\u0010_J\u0015\u0010`\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001fH\u0016¢\u0006\u0004\b`\u0010\"R\u0014\u0010\u0004\u001a\u00020\u00038\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0004\u0010aR\u0014\u0010\u0006\u001a\u00020\u00058\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0006\u0010bR\u0014\u0010\b\u001a\u00020\u00078\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\b\u0010cR\u0014\u0010\n\u001a\u00020\t8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\n\u0010dR\u0014\u0010\f\u001a\u00020\u000b8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\f\u0010eR\u0014\u0010\u000e\u001a\u00020\r8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u000e\u0010fR\u0014\u0010\u0010\u001a\u00020\u000f8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0010\u0010gR\u0014\u0010\u0012\u001a\u00020\u00118\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0012\u0010hR\u0014\u0010\u0014\u001a\u00020\u00138\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0014\u0010iR\u0014\u0010\u0016\u001a\u00020\u00158\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0016\u0010jR\u0014\u0010\u0018\u001a\u00020\u00178\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0018\u0010kR!\u0010p\u001a\b\u0012\u0004\u0012\u00020 0l8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bm\u0010n\u001a\u0004\bo\u0010\"R\u001b\u0010r\u001a\u00020(8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bq\u0010n\u001a\u0004\br\u0010*R!\u0010y\u001a\u00020s8@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\bt\u0010n\u0012\u0004\bw\u0010x\u001a\u0004\bu\u0010v¨\u0006{"}, m877d2 = {"Landroidx/camera/camera2/adapter/CameraInfoAdapter;", "Landroidx/camera/core/impl/CameraInfoInternal;", "Landroidx/camera/camera2/pipe/UnsafeWrapper;", "Landroidx/camera/camera2/impl/CameraProperties;", "cameraProperties", "Landroidx/camera/camera2/config/CameraConfig;", "cameraConfig", "Landroidx/camera/camera2/adapter/CameraStateAdapter;", "cameraStateAdapter", "Landroidx/camera/camera2/adapter/CameraControlStateAdapter;", "cameraControlStateAdapter", "Landroidx/camera/camera2/impl/CameraCallbackMap;", "cameraCallbackMap", "Landroidx/camera/camera2/impl/FocusMeteringControl;", "focusMeteringControl", "Landroidx/camera/camera2/compat/quirk/CameraQuirks;", "cameraQuirks", "Landroidx/camera/core/impl/EncoderProfilesProvider;", "encoderProfilesProvider", "Landroidx/camera/camera2/compat/StreamConfigurationMapCompat;", "streamConfigurationMapCompat", "Landroidx/camera/camera2/internal/IntrinsicZoomCalculator;", "intrinsicZoomCalculator", "Landroidx/camera/core/internal/StreamSpecsCalculator;", "streamSpecsCalculator", "<init>", "(Landroidx/camera/camera2/impl/CameraProperties;Landroidx/camera/camera2/config/CameraConfig;Landroidx/camera/camera2/adapter/CameraStateAdapter;Landroidx/camera/camera2/adapter/CameraControlStateAdapter;Landroidx/camera/camera2/impl/CameraCallbackMap;Landroidx/camera/camera2/impl/FocusMeteringControl;Landroidx/camera/camera2/compat/quirk/CameraQuirks;Landroidx/camera/core/impl/EncoderProfilesProvider;Landroidx/camera/camera2/compat/StreamConfigurationMapCompat;Landroidx/camera/camera2/internal/IntrinsicZoomCalculator;Landroidx/camera/core/internal/StreamSpecsCalculator;)V", _UrlKt.FRAGMENT_ENCODE_SET, "lensFacingInt", "getCameraSelectorLensFacing", "(I)I", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/core/CameraInfo;", "getPhysicalCameraInfos", "()Ljava/util/Set;", _UrlKt.FRAGMENT_ENCODE_SET, "getCameraId", "()Ljava/lang/String;", "getLensFacing", "()I", _UrlKt.FRAGMENT_ENCODE_SET, "isExternalCamera", "()Z", "Landroid/hardware/camera2/CameraCharacteristics;", "getCameraCharacteristics", "()Landroid/hardware/camera2/CameraCharacteristics;", "getSensorRotationDegrees", "hasFlashUnit", "relativeRotation", "Landroidx/lifecycle/LiveData;", "Landroidx/camera/core/ZoomState;", "getZoomState", "()Landroidx/lifecycle/LiveData;", "Landroidx/camera/core/CameraState;", "getCameraState", "getEncoderProfilesProvider", "()Landroidx/camera/core/impl/EncoderProfilesProvider;", "Landroidx/camera/core/impl/Timebase;", "getTimebase", "()Landroidx/camera/core/impl/Timebase;", "getSupportedOutputFormats", "format", _UrlKt.FRAGMENT_ENCODE_SET, "Landroid/util/Size;", "getSupportedResolutions", "(I)Ljava/util/List;", "getSupportedHighResolutions", _UrlKt.FRAGMENT_ENCODE_SET, "T", "Lkotlin/reflect/KClass;", TeXSymbolParser.TYPE_ATTR, "unwrapAs", "(Lkotlin/reflect/KClass;)Ljava/lang/Object;", "toString", "Landroidx/camera/core/impl/Quirks;", "getCameraQuirks", "()Landroidx/camera/core/impl/Quirks;", "Landroid/util/Range;", "getSupportedFrameRateRanges", "Landroidx/camera/core/DynamicRange;", "getSupportedDynamicRanges", "isHighSpeedSupported", "getSupportedHighSpeedFrameRateRanges", "getSupportedHighSpeedResolutions", "()Ljava/util/List;", "fpsRange", "getSupportedHighSpeedResolutionsFor", "(Landroid/util/Range;)Ljava/util/List;", "Landroid/graphics/Rect;", "getSensorRect", "()Landroid/graphics/Rect;", "isPreviewStabilizationSupported", "isVideoStabilizationSupported", _UrlKt.FRAGMENT_ENCODE_SET, "getIntrinsicZoomRatio", "()F", "getAvailableCapabilities", "Landroidx/camera/camera2/impl/CameraProperties;", "Landroidx/camera/camera2/config/CameraConfig;", "Landroidx/camera/camera2/adapter/CameraStateAdapter;", "Landroidx/camera/camera2/adapter/CameraControlStateAdapter;", "Landroidx/camera/camera2/impl/CameraCallbackMap;", "Landroidx/camera/camera2/impl/FocusMeteringControl;", "Landroidx/camera/camera2/compat/quirk/CameraQuirks;", "Landroidx/camera/core/impl/EncoderProfilesProvider;", "Landroidx/camera/camera2/compat/StreamConfigurationMapCompat;", "Landroidx/camera/camera2/internal/IntrinsicZoomCalculator;", "Landroidx/camera/core/internal/StreamSpecsCalculator;", _UrlKt.FRAGMENT_ENCODE_SET, "_physicalCameraInfos$delegate", "Lkotlin/Lazy;", "get_physicalCameraInfos", "_physicalCameraInfos", "isLegacyDevice$delegate", "isLegacyDevice", "Landroidx/camera/camera2/interop/Camera2CameraInfo;", "camera2CameraInfo$delegate", "getCamera2CameraInfo$camera_camera2", "()Landroidx/camera/camera2/interop/Camera2CameraInfo;", "getCamera2CameraInfo$camera_camera2$annotations", "()V", "camera2CameraInfo", "Companion", "camera-camera2"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nCameraInfoAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CameraInfoAdapter.kt\nandroidx/camera/camera2/adapter/CameraInfoAdapter\n+ 2 CameraDevices.kt\nandroidx/camera/camera2/pipe/CameraId$Companion\n+ 3 Camera2Logger.kt\nandroidx/camera/camera2/impl/Camera2Logger\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,407:1\n172#2:408\n119#3,4:409\n119#3,4:413\n95#3,4:417\n1634#4,3:421\n*S KotlinDebug\n*F\n+ 1 CameraInfoAdapter.kt\nandroidx/camera/camera2/adapter/CameraInfoAdapter\n*L\n146#1:408\n162#1:409,4\n355#1:413,4\n375#1:417,4\n106#1:421,3\n*E\n"})
public final class CameraInfoAdapter implements CameraInfoInternal, UnsafeWrapper {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX INFO: renamed from: _physicalCameraInfos$delegate, reason: from kotlin metadata */
    private final Lazy _physicalCameraInfos;

    /* JADX INFO: renamed from: camera2CameraInfo$delegate, reason: from kotlin metadata */
    private final Lazy camera2CameraInfo;
    private final CameraCallbackMap cameraCallbackMap;
    private final CameraConfig cameraConfig;
    private final CameraControlStateAdapter cameraControlStateAdapter;
    private final CameraProperties cameraProperties;
    private final CameraQuirks cameraQuirks;
    private final CameraStateAdapter cameraStateAdapter;
    private final EncoderProfilesProvider encoderProfilesProvider;
    private final FocusMeteringControl focusMeteringControl;
    private final IntrinsicZoomCalculator intrinsicZoomCalculator;

    /* JADX INFO: renamed from: isLegacyDevice$delegate, reason: from kotlin metadata */
    private final Lazy isLegacyDevice;
    private final StreamConfigurationMapCompat streamConfigurationMapCompat;
    private final StreamSpecsCalculator streamSpecsCalculator;

    public CameraInfoAdapter(CameraProperties cameraProperties, CameraConfig cameraConfig, CameraStateAdapter cameraStateAdapter, CameraControlStateAdapter cameraControlStateAdapter, CameraCallbackMap cameraCallbackMap, FocusMeteringControl focusMeteringControl, CameraQuirks cameraQuirks, EncoderProfilesProvider encoderProfilesProvider, StreamConfigurationMapCompat streamConfigurationMapCompat, IntrinsicZoomCalculator intrinsicZoomCalculator, StreamSpecsCalculator streamSpecsCalculator) {
        this.cameraProperties = cameraProperties;
        this.cameraConfig = cameraConfig;
        this.cameraStateAdapter = cameraStateAdapter;
        this.cameraControlStateAdapter = cameraControlStateAdapter;
        this.cameraCallbackMap = cameraCallbackMap;
        this.focusMeteringControl = focusMeteringControl;
        this.cameraQuirks = cameraQuirks;
        this.encoderProfilesProvider = encoderProfilesProvider;
        this.streamConfigurationMapCompat = streamConfigurationMapCompat;
        this.intrinsicZoomCalculator = intrinsicZoomCalculator;
        this.streamSpecsCalculator = streamSpecsCalculator;
        DeviceInfoLogger.INSTANCE.logDeviceInfo(cameraProperties);
        this._physicalCameraInfos = LazyKt.lazy(new Function0() { // from class: androidx.camera.camera2.adapter.CameraInfoAdapter$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CameraInfoAdapter.m1274$r8$lambda$8g70NABhA3_822vIurrdbS08FA(this.f$0);
            }
        });
        this.isLegacyDevice = LazyKt.lazy(new Function0() { // from class: androidx.camera.camera2.adapter.CameraInfoAdapter$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(CameraMetadata.INSTANCE.isHardwareLevelLegacy(this.f$0.cameraProperties.getMetadata()));
            }
        });
        this.camera2CameraInfo = LazyKt.lazy(new Function0() { // from class: androidx.camera.camera2.adapter.CameraInfoAdapter$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Camera2CameraInfo.INSTANCE.create(this.f$0.cameraProperties);
            }
        });
    }

    private final Set<CameraInfo> get_physicalCameraInfos() {
        return (Set) this._physicalCameraInfos.getValue();
    }

    /* JADX INFO: renamed from: $r8$lambda$8g70NABhA3_822vIurrdbS-08FA */
    public static Set m1274$r8$lambda$8g70NABhA3_822vIurrdbS08FA(CameraInfoAdapter cameraInfoAdapter) {
        Set<CameraId> physicalCameraIds = cameraInfoAdapter.cameraProperties.getMetadata().getPhysicalCameraIds();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<T> it = physicalCameraIds.iterator();
        while (it.hasNext()) {
            String value = ((CameraId) it.next()).getValue();
            linkedHashSet.add(new PhysicalCameraInfoAdapter(new CameraPipeCameraProperties(new CameraConfig(value, null), cameraInfoAdapter.cameraProperties.getMetadata().mo1505awaitPhysicalMetadataEfqyGwQ(value))));
        }
        return linkedHashSet;
    }

    public final Camera2CameraInfo getCamera2CameraInfo$camera_camera2() {
        return (Camera2CameraInfo) this.camera2CameraInfo.getValue();
    }

    @Override // androidx.camera.core.CameraInfo
    public Set<CameraInfo> getPhysicalCameraInfos() {
        return get_physicalCameraInfos();
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public String getCameraId() {
        return this.cameraConfig.getCameraId();
    }

    @Override // androidx.camera.core.CameraInfo
    public int getLensFacing() {
        return getCameraSelectorLensFacing(((Number) this.cameraProperties.getMetadata().get(CameraCharacteristics.LENS_FACING)).intValue());
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public boolean isExternalCamera() {
        if (getLensFacing() == 2) {
            return true;
        }
        Integer num = (Integer) this.cameraProperties.getMetadata().get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
        return num != null && num.intValue() == 4;
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public CameraCharacteristics getCameraCharacteristics() {
        return (CameraCharacteristics) this.cameraProperties.getMetadata().unwrapAs(Reflection.getOrCreateKotlinClass(CameraCharacteristics.class));
    }

    private final int getCameraSelectorLensFacing(int lensFacingInt) {
        if (lensFacingInt == 0) {
            return 0;
        }
        if (lensFacingInt == 1) {
            return 1;
        }
        if (lensFacingInt == 2) {
            return 2;
        }
        Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
        if (Logger.isWarnEnabled("CXCP")) {
            Log.w(Camera2Logger.TRUNCATED_TAG, "Unrecognized lens facing: " + lensFacingInt + '!');
        }
        return -1;
    }

    @Override // androidx.camera.core.CameraInfo
    public int getSensorRotationDegrees() {
        return getSensorRotationDegrees(0);
    }

    @Override // androidx.camera.core.CameraInfo
    public boolean hasFlashUnit() {
        return FlashAvailabilityCheckerKt.isFlashAvailable$default(this.cameraProperties, false, 1, null);
    }

    @Override // androidx.camera.core.CameraInfo
    public int getSensorRotationDegrees(int relativeRotation) {
        return CameraOrientationUtil.getRelativeImageRotation(CameraOrientationUtil.surfaceRotationToDegrees(relativeRotation), ((Number) this.cameraProperties.getMetadata().get(CameraCharacteristics.SENSOR_ORIENTATION)).intValue(), 1 == getLensFacing());
    }

    @Override // androidx.camera.core.CameraInfo
    public LiveData<ZoomState> getZoomState() {
        return this.cameraControlStateAdapter.getZoomStateLiveData();
    }

    @Override // androidx.camera.core.CameraInfo
    public LiveData<CameraState> getCameraState() {
        return this.cameraStateAdapter.getCameraState$camera_camera2();
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public EncoderProfilesProvider getEncoderProfilesProvider() {
        return this.encoderProfilesProvider;
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public Timebase getTimebase() {
        int iIntValue = ((Number) this.cameraProperties.getMetadata().get(CameraCharacteristics.SENSOR_INFO_TIMESTAMP_SOURCE)).intValue();
        if (iIntValue == 0) {
            return Timebase.UPTIME;
        }
        if (iIntValue == 1) {
            return Timebase.REALTIME;
        }
        return Timebase.UPTIME;
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public Set<Integer> getSupportedOutputFormats() {
        Set<Integer> set;
        Integer[] outputFormats = this.streamConfigurationMapCompat.getOutputFormats();
        return (outputFormats == null || (set = ArraysKt.toSet(outputFormats)) == null) ? SetsKt.emptySet() : set;
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public List<Size> getSupportedResolutions(int format) {
        List<Size> list;
        Size[] outputSizes = this.streamConfigurationMapCompat.getOutputSizes(format);
        return (outputSizes == null || (list = ArraysKt.toList(outputSizes)) == null) ? CollectionsKt.emptyList() : list;
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public List<Size> getSupportedHighResolutions(int format) {
        List<Size> list;
        Size[] highResolutionOutputSizes = this.streamConfigurationMapCompat.getHighResolutionOutputSizes(format);
        return (highResolutionOutputSizes == null || (list = ArraysKt.toList(highResolutionOutputSizes)) == null) ? CollectionsKt.emptyList() : list;
    }

    @Override // androidx.camera.camera2.pipe.UnsafeWrapper
    public <T> T unwrapAs(KClass<T> kClass) {
        if (Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(Camera2CameraInfo.class))) {
            return (T) getCamera2CameraInfo$camera_camera2();
        }
        if (Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(CameraProperties.class))) {
            return (T) this.cameraProperties;
        }
        boolean zAreEqual = Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(CameraMetadata.class));
        CameraProperties cameraProperties = this.cameraProperties;
        if (zAreEqual) {
            return (T) cameraProperties.getMetadata();
        }
        return (T) cameraProperties.getMetadata().unwrapAs(kClass);
    }

    public String toString() {
        return "CameraInfoAdapter<" + this.cameraConfig + ".cameraId>";
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public Quirks getCameraQuirks() {
        return this.cameraQuirks.getQuirks();
    }

    @Override // androidx.camera.core.CameraInfo
    public Set<Range<Integer>> getSupportedFrameRateRanges() {
        Set<Range<Integer>> set;
        Range[] rangeArr = (Range[]) this.cameraProperties.getMetadata().get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
        return (rangeArr == null || (set = ArraysKt.toSet(rangeArr)) == null) ? SetsKt.emptySet() : set;
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public Set<DynamicRange> getSupportedDynamicRanges() {
        return DynamicRangeProfilesCompat.INSTANCE.fromCameraMetaData(this.cameraProperties.getMetadata()).getSupportedDynamicRanges();
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public boolean isHighSpeedSupported() {
        return CameraMetadata.INSTANCE.getSupportsHighSpeedVideo(this.cameraProperties.getMetadata());
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public Set<Range<Integer>> getSupportedHighSpeedFrameRateRanges() {
        Set<Range<Integer>> set;
        Range<Integer>[] highSpeedVideoFpsRanges = this.streamConfigurationMapCompat.getHighSpeedVideoFpsRanges();
        return (highSpeedVideoFpsRanges == null || (set = ArraysKt.toSet(highSpeedVideoFpsRanges)) == null) ? SetsKt.emptySet() : set;
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public List<Size> getSupportedHighSpeedResolutions() {
        List<Size> list;
        Size[] highSpeedVideoSizes = this.streamConfigurationMapCompat.getHighSpeedVideoSizes();
        return (highSpeedVideoSizes == null || (list = ArraysKt.toList(highSpeedVideoSizes)) == null) ? CollectionsKt.emptyList() : list;
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public List<Size> getSupportedHighSpeedResolutionsFor(Range<Integer> fpsRange) {
        Object objM3494constructorimpl;
        try {
            Result.Companion companion = Result.INSTANCE;
            Size[] highSpeedVideoSizesFor = this.streamConfigurationMapCompat.getHighSpeedVideoSizesFor(fpsRange);
            objM3494constructorimpl = Result.m3494constructorimpl(highSpeedVideoSizesFor != null ? ArraysKt.toList(highSpeedVideoSizesFor) : null);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM3494constructorimpl = Result.m3494constructorimpl(ResultKt.createFailure(th));
        }
        List<Size> list = (List) (Result.m3500isFailureimpl(objM3494constructorimpl) ? null : objM3494constructorimpl);
        return list == null ? CollectionsKt.emptyList() : list;
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public Rect getSensorRect() {
        Rect rect = (Rect) this.cameraProperties.getMetadata().get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
        return (Intrinsics.areEqual("robolectric", Build.FINGERPRINT) && rect == null) ? new Rect(0, 0, 4000, 3000) : rect;
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public boolean isPreviewStabilizationSupported() {
        return CameraMetadata.INSTANCE.getSupportsPreviewStabilization(this.cameraProperties.getMetadata());
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public boolean isVideoStabilizationSupported() {
        int[] iArr = (int[]) this.cameraProperties.getMetadata().get(CameraCharacteristics.CONTROL_AVAILABLE_VIDEO_STABILIZATION_MODES);
        return iArr != null && ArraysKt.contains(iArr, 1);
    }

    @Override // androidx.camera.core.CameraInfo
    public float getIntrinsicZoomRatio() {
        Float fCalculateIntrinsicZoomRatio = this.intrinsicZoomCalculator.calculateIntrinsicZoomRatio(this.cameraProperties.getMetadata());
        if (fCalculateIntrinsicZoomRatio != null) {
            return fCalculateIntrinsicZoomRatio.floatValue();
        }
        Camera2Logger camera2Logger = Camera2Logger.INSTANCE;
        if (!Logger.isWarnEnabled("CXCP")) {
            return 1.0f;
        }
        Log.w(Camera2Logger.TRUNCATED_TAG, "Failed to calculate intrinsic zoom ratio for " + ((Object) CameraId.m1501toStringimpl(this.cameraProperties.mo1333getCameraIdDz_R5H8())));
        return 1.0f;
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public Set<Integer> getAvailableCapabilities() {
        Set<Integer> set;
        int[] iArr = (int[]) this.cameraProperties.getMetadata().get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES);
        return (iArr == null || (set = ArraysKt.toSet(iArr)) == null) ? SetsKt.emptySet() : set;
    }

    @Metadata(m876d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J)\u0010\u0004\u001a\u0004\u0018\u0001H\u0005\"\b\b\u0000\u0010\u0005*\u00020\u0001*\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00050\b¢\u0006\u0002\u0010\tR\u0017\u0010\n\u001a\u0004\u0018\u00010\u000b*\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, m877d2 = {"Landroidx/camera/camera2/adapter/CameraInfoAdapter$Companion;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "unwrapAs", "T", "Landroidx/camera/core/CameraInfo;", TeXSymbolParser.TYPE_ATTR, "Lkotlin/reflect/KClass;", "(Landroidx/camera/core/CameraInfo;Lkotlin/reflect/KClass;)Ljava/lang/Object;", "cameraId", "Landroidx/camera/camera2/pipe/CameraId;", "getCameraId-zjxgSG8", "(Landroidx/camera/core/CameraInfo;)Ljava/lang/String;", "camera-camera2"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final <T> T unwrapAs(CameraInfo cameraInfo, KClass<T> kClass) {
            if (cameraInfo instanceof UnsafeWrapper) {
                return (T) ((UnsafeWrapper) cameraInfo).unwrapAs(kClass);
            }
            if (cameraInfo instanceof CameraInfoInternal) {
                CameraInfoInternal cameraInfoInternal = (CameraInfoInternal) cameraInfo;
                if (cameraInfoInternal.getImplementation() != cameraInfo) {
                    return (T) unwrapAs(cameraInfoInternal.getImplementation(), kClass);
                }
            }
            return null;
        }

        /* JADX INFO: renamed from: getCameraId-zjxgSG8 */
        public final String m1277getCameraIdzjxgSG8(CameraInfo cameraInfo) {
            CameraMetadata cameraMetadata = (CameraMetadata) unwrapAs(cameraInfo, Reflection.getOrCreateKotlinClass(CameraMetadata.class));
            if (cameraMetadata != null) {
                return cameraMetadata.getCamera();
            }
            return null;
        }
    }
}
