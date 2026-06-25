package androidx.camera.camera2.compat.workaround;

import kotlin.Metadata;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes3.dex */
@Metadata(m876d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0016¨\u0006\u0007"}, m877d2 = {"Landroidx/camera/camera2/compat/workaround/NoOpAutoFlashAEModeDisabler;", "Landroidx/camera/camera2/compat/workaround/AutoFlashAEModeDisabler;", "<init>", "()V", "getCorrectedAeMode", _UrlKt.FRAGMENT_ENCODE_SET, "aeMode", "camera-camera2"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
public final class NoOpAutoFlashAEModeDisabler implements AutoFlashAEModeDisabler {
    public static final NoOpAutoFlashAEModeDisabler INSTANCE = new NoOpAutoFlashAEModeDisabler();

    @Override // androidx.camera.camera2.compat.workaround.AutoFlashAEModeDisabler
    public int getCorrectedAeMode(int aeMode) {
        return aeMode;
    }

    private NoOpAutoFlashAEModeDisabler() {
    }
}
