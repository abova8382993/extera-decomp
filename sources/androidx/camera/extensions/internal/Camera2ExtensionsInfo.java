package androidx.camera.extensions.internal;

import android.hardware.camera2.CameraExtensionCharacteristics;
import android.hardware.camera2.CameraManager;
import android.util.Size;
import androidx.camera.camera2.pipe.compat.Camera2MetadataCache$$ExternalSyntheticApiModelOutline0;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SourceDebugExtension;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0004\b\u0004\u0010\u0005J\u0017\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\u0006H\u0017¢\u0006\u0004\b\t\u0010\nR\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010\u000bR\u0014\u0010\f\u001a\u00020\u00018\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\f\u0010\rR \u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\b0\u000e8\u0002X\u0083\u0004¢\u0006\u0006\n\u0004\b\u000f\u0010\u0010R&\u0010\u0013\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00110\u000e8\u0002X\u0083\u0004¢\u0006\u0006\n\u0004\b\u0013\u0010\u0010R&\u0010\u0015\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00110\u000e8\u0002X\u0083\u0004¢\u0006\u0006\n\u0004\b\u0015\u0010\u0010¨\u0006\u0017"}, m877d2 = {"Landroidx/camera/extensions/internal/Camera2ExtensionsInfo;", _UrlKt.FRAGMENT_ENCODE_SET, "Landroid/hardware/camera2/CameraManager;", "cameraManager", "<init>", "(Landroid/hardware/camera2/CameraManager;)V", _UrlKt.FRAGMENT_ENCODE_SET, "cameraId", "Landroid/hardware/camera2/CameraExtensionCharacteristics;", "getExtensionCharacteristics", "(Ljava/lang/String;)Landroid/hardware/camera2/CameraExtensionCharacteristics;", "Landroid/hardware/camera2/CameraManager;", "lock", "Ljava/lang/Object;", _UrlKt.FRAGMENT_ENCODE_SET, "cachedCharacteristics", "Ljava/util/Map;", _UrlKt.FRAGMENT_ENCODE_SET, "Landroid/util/Size;", "cachedSupportedOutputSizes", _UrlKt.FRAGMENT_ENCODE_SET, "cachedSupportedExtensions", "Companion", "camera-extensions"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nCamera2ExtensionsInfo.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Camera2ExtensionsInfo.kt\nandroidx/camera/extensions/internal/Camera2ExtensionsInfo\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,114:1\n1#2:115\n*E\n"})
public final class Camera2ExtensionsInfo {
    private static final Companion Companion = new Companion(null);
    private final CameraManager cameraManager;
    private final Object lock = new Object();
    private final Map<String, CameraExtensionCharacteristics> cachedCharacteristics = new LinkedHashMap();
    private final Map<String, List<Size>> cachedSupportedOutputSizes = new LinkedHashMap();
    private final Map<String, List<Integer>> cachedSupportedExtensions = new LinkedHashMap();

    public Camera2ExtensionsInfo(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }

    public CameraExtensionCharacteristics getExtensionCharacteristics(String cameraId) {
        CameraExtensionCharacteristics cameraExtensionCharacteristicsM64m;
        synchronized (this.lock) {
            cameraExtensionCharacteristicsM64m = Camera2MetadataCache$$ExternalSyntheticApiModelOutline0.m64m(this.cachedCharacteristics.get(cameraId));
            if (cameraExtensionCharacteristicsM64m == null) {
                cameraExtensionCharacteristicsM64m = this.cameraManager.getCameraExtensionCharacteristics(cameraId);
                this.cachedCharacteristics.put(cameraId, cameraExtensionCharacteristicsM64m);
            }
        }
        return cameraExtensionCharacteristicsM64m;
    }

    @Metadata(m876d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0006"}, m877d2 = {"Landroidx/camera/extensions/internal/Camera2ExtensionsInfo$Companion;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "TAG", _UrlKt.FRAGMENT_ENCODE_SET, "camera-extensions"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
