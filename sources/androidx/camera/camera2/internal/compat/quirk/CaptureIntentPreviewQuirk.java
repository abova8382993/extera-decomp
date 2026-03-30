package androidx.camera.camera2.internal.compat.quirk;

import androidx.camera.core.impl.Quirk;
import androidx.camera.core.impl.Quirks;
import java.util.Iterator;

/* JADX INFO: loaded from: classes3.dex */
public interface CaptureIntentPreviewQuirk extends Quirk {
    boolean workaroundByCaptureIntentPreview();

    /* JADX INFO: renamed from: androidx.camera.camera2.internal.compat.quirk.CaptureIntentPreviewQuirk$-CC, reason: invalid class name */
    public abstract /* synthetic */ class CC {
        public static boolean $default$workaroundByCaptureIntentPreview(CaptureIntentPreviewQuirk captureIntentPreviewQuirk) {
            return true;
        }

        public static boolean workaroundByCaptureIntentPreview(Quirks quirks) {
            Iterator it = quirks.getAll(CaptureIntentPreviewQuirk.class).iterator();
            while (it.hasNext()) {
                if (((CaptureIntentPreviewQuirk) it.next()).workaroundByCaptureIntentPreview()) {
                    return true;
                }
            }
            return false;
        }
    }
}
