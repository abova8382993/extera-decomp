package androidx.camera.camera2.compat.quirk;

import android.annotation.SuppressLint;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: loaded from: classes3.dex */
@SuppressLint({"CameraXQuirksClassDetector"})
public final class ImageCaptureFlashNotFireQuirk implements UseTorchAsFlashQuirk {
    public static final Companion Companion = new Companion(null);
    private static final List BUILD_MODELS = CollectionsKt.listOf((Object[]) new String[]{"itel w6004", "sm-j700m"});
    private static final List BUILD_MODELS_FRONT_CAMERA = CollectionsKt.listOf((Object[]) new String[]{"sm-j700f", "sm-j710f"});

    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x003c  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean isEnabled(androidx.camera.camera2.pipe.CameraMetadata r9) {
            /*
                r8 = this;
                java.lang.String r0 = "cameraMetadata"
                kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r9, r0)
                java.util.List r0 = androidx.camera.camera2.compat.quirk.ImageCaptureFlashNotFireQuirk.access$getBUILD_MODELS_FRONT_CAMERA$cp()
                java.lang.String r1 = android.os.Build.MODEL
                java.lang.String r2 = "MODEL"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r2)
                java.util.Locale r3 = java.util.Locale.ROOT
                java.lang.String r4 = r1.toLowerCase(r3)
                java.lang.String r5 = "toLowerCase(...)"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r4, r5)
                boolean r0 = r0.contains(r4)
                r4 = 0
                r6 = 1
                if (r0 == 0) goto L3c
                android.hardware.camera2.CameraCharacteristics$Key r0 = android.hardware.camera2.CameraCharacteristics.LENS_FACING
                java.lang.String r7 = "LENS_FACING"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r7)
                java.lang.Object r9 = r9.get(r0)
                java.lang.Integer r9 = (java.lang.Integer) r9
                if (r9 != 0) goto L34
                goto L3c
            L34:
                int r9 = r9.intValue()
                if (r9 != 0) goto L3c
                r9 = r6
                goto L3d
            L3c:
                r9 = r4
            L3d:
                java.util.List r0 = androidx.camera.camera2.compat.quirk.ImageCaptureFlashNotFireQuirk.access$getBUILD_MODELS$cp()
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r2)
                java.lang.String r1 = r1.toLowerCase(r3)
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r5)
                boolean r0 = r0.contains(r1)
                if (r9 != 0) goto L55
                if (r0 == 0) goto L54
                goto L55
            L54:
                return r4
            L55:
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.compat.quirk.ImageCaptureFlashNotFireQuirk.Companion.isEnabled(androidx.camera.camera2.pipe.CameraMetadata):boolean");
        }
    }
}
