package androidx.camera.camera2.compat.quirk;

import android.annotation.SuppressLint;
import android.os.Build;
import androidx.camera.core.impl.Quirk;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: loaded from: classes3.dex */
@SuppressLint({"CameraXQuirksClassDetector"})
public final class CloseCameraDeviceOnCameraGraphCloseQuirk implements Quirk {
    public static final Companion Companion = new Companion(null);
    private static final boolean isSamsungExynos7570Device;
    private static final boolean isSamsungExynos7870Device;
    private static final boolean isSamsungProblematicDevice;
    private static final boolean isSonyProblematicDevice;
    private static final boolean isXiaomiProblematicDevice;

    public final boolean shouldCloseCameraDevice(boolean z) {
        if (isXiaomiProblematicDevice) {
            return z;
        }
        if (!isSamsungProblematicDevice || isSamsungExynos7570Device || isSamsungExynos7870Device) {
            return true;
        }
        return z;
    }

    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean isEnabled() {
            if (CloseCameraDeviceOnCameraGraphCloseQuirk.isSamsungExynos7570Device || CloseCameraDeviceOnCameraGraphCloseQuirk.isSamsungExynos7870Device) {
                return true;
            }
            int i = Build.VERSION.SDK_INT;
            if (30 <= i && i < 34) {
                Device device = Device.INSTANCE;
                if (device.isOppoDevice() || device.isOnePlusDevice() || device.isRealmeDevice()) {
                    return true;
                }
            }
            return Device.INSTANCE.isVivoDevice() || CloseCameraDeviceOnCameraGraphCloseQuirk.isXiaomiProblematicDevice || CloseCameraDeviceOnCameraGraphCloseQuirk.isSamsungProblematicDevice || CloseCameraDeviceOnCameraGraphCloseQuirk.isSonyProblematicDevice;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0047  */
    static {
        /*
            androidx.camera.camera2.compat.quirk.CloseCameraDeviceOnCameraGraphCloseQuirk$Companion r0 = new androidx.camera.camera2.compat.quirk.CloseCameraDeviceOnCameraGraphCloseQuirk$Companion
            r1 = 0
            r0.<init>(r1)
            androidx.camera.camera2.compat.quirk.CloseCameraDeviceOnCameraGraphCloseQuirk.Companion = r0
            java.lang.String r0 = android.os.Build.HARDWARE
            java.lang.String r1 = "samsungexynos7570"
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
            androidx.camera.camera2.compat.quirk.CloseCameraDeviceOnCameraGraphCloseQuirk.isSamsungExynos7570Device = r1
            java.lang.String r1 = "samsungexynos7870"
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
            androidx.camera.camera2.compat.quirk.CloseCameraDeviceOnCameraGraphCloseQuirk.isSamsungExynos7870Device = r0
            androidx.camera.camera2.compat.quirk.Device r0 = androidx.camera.camera2.compat.quirk.Device.INSTANCE
            boolean r1 = r0.isXiaomiDevice()
            java.lang.String r2 = "DEVICE"
            r3 = 0
            r4 = 1
            if (r1 == 0) goto L47
            java.lang.String r1 = "aurora"
            java.lang.String r5 = "houji"
            java.lang.String[] r1 = new java.lang.String[]{r1, r5}
            java.lang.String r5 = android.os.Build.DEVICE
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r2)
            java.util.Locale r6 = java.util.Locale.ROOT
            java.lang.String r5 = r5.toLowerCase(r6)
            java.lang.String r6 = "toLowerCase(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r6)
            boolean r1 = kotlin.collections.ArraysKt.contains(r1, r5)
            if (r1 == 0) goto L47
            r1 = r4
            goto L48
        L47:
            r1 = r3
        L48:
            androidx.camera.camera2.compat.quirk.CloseCameraDeviceOnCameraGraphCloseQuirk.isXiaomiProblematicDevice = r1
            boolean r0 = r0.isSonyDevice()
            if (r0 == 0) goto L8b
            java.lang.String r0 = "SO"
            java.lang.String r1 = "A301SO"
            java.lang.String r5 = "XQ-DQ"
            java.lang.String[] r0 = new java.lang.String[]{r5, r0, r1}
            java.util.List r0 = kotlin.collections.CollectionsKt.listOf(r0)
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            boolean r1 = r0 instanceof java.util.Collection
            if (r1 == 0) goto L6e
            r1 = r0
            java.util.Collection r1 = (java.util.Collection) r1
            boolean r1 = r1.isEmpty()
            if (r1 == 0) goto L6e
            goto L8b
        L6e:
            java.util.Iterator r0 = r0.iterator()
        L72:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L8b
            java.lang.Object r1 = r0.next()
            java.lang.String r1 = (java.lang.String) r1
            java.lang.String r5 = android.os.Build.DEVICE
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r2)
            boolean r1 = kotlin.text.StringsKt.startsWith(r5, r1, r4)
            if (r1 == 0) goto L72
            r0 = r4
            goto L8c
        L8b:
            r0 = r3
        L8c:
            androidx.camera.camera2.compat.quirk.CloseCameraDeviceOnCameraGraphCloseQuirk.isSonyProblematicDevice = r0
            androidx.camera.camera2.compat.quirk.Device r0 = androidx.camera.camera2.compat.quirk.Device.INSTANCE
            boolean r0 = r0.isSamsungDevice()
            if (r0 == 0) goto La1
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 31
            if (r0 < r1) goto La1
            r1 = 34
            if (r0 > r1) goto La1
            r3 = r4
        La1:
            androidx.camera.camera2.compat.quirk.CloseCameraDeviceOnCameraGraphCloseQuirk.isSamsungProblematicDevice = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.compat.quirk.CloseCameraDeviceOnCameraGraphCloseQuirk.<clinit>():void");
    }
}
