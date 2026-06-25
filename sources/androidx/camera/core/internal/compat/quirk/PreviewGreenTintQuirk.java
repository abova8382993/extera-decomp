package androidx.camera.core.internal.compat.quirk;

import android.annotation.SuppressLint;
import android.os.Build;
import androidx.camera.core.UseCase;
import androidx.camera.core.impl.Quirk;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0007\u001a\u00020\u0005H\u0007J\u001e\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0007J\u001e\u0010\u000e\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0002R\u0014\u0010\u0004\u001a\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0006¨\u0006\u000f"}, m877d2 = {"Landroidx/camera/core/internal/compat/quirk/PreviewGreenTintQuirk;", "Landroidx/camera/core/impl/Quirk;", "<init>", "()V", "isMotoE20", _UrlKt.FRAGMENT_ENCODE_SET, "()Z", "load", "shouldForceEnableStreamSharing", "cameraId", _UrlKt.FRAGMENT_ENCODE_SET, "appUseCases", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/core/UseCase;", "shouldForceEnableStreamSharingForMotoE20", "camera-core"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SuppressLint({"CameraXQuirksClassDetector"})
@SourceDebugExtension({"SMAP\nPreviewGreenTintQuirk.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PreviewGreenTintQuirk.kt\nandroidx/camera/core/internal/compat/quirk/PreviewGreenTintQuirk\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,70:1\n1761#2,3:71\n1761#2,3:74\n*S KotlinDebug\n*F\n+ 1 PreviewGreenTintQuirk.kt\nandroidx/camera/core/internal/compat/quirk/PreviewGreenTintQuirk\n*L\n61#1:71,3\n63#1:74,3\n*E\n"})
public final class PreviewGreenTintQuirk implements Quirk {
    public static final PreviewGreenTintQuirk INSTANCE = new PreviewGreenTintQuirk();

    private PreviewGreenTintQuirk() {
    }

    private final boolean isMotoE20() {
        return StringsKt.equals("motorola", Build.BRAND, true) && StringsKt.equals("moto e20", Build.MODEL, true);
    }

    @JvmStatic
    public static final boolean load() {
        return INSTANCE.isMotoE20();
    }

    @JvmStatic
    public static final boolean shouldForceEnableStreamSharing(String cameraId, Collection<? extends UseCase> appUseCases) {
        PreviewGreenTintQuirk previewGreenTintQuirk = INSTANCE;
        if (previewGreenTintQuirk.isMotoE20()) {
            return previewGreenTintQuirk.shouldForceEnableStreamSharingForMotoE20(cameraId, appUseCases);
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0070 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final boolean shouldForceEnableStreamSharingForMotoE20(java.lang.String r5, java.util.Collection<? extends androidx.camera.core.UseCase> r6) {
        /*
            r4 = this;
            java.lang.String r4 = "0"
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r4)
            r5 = 0
            if (r4 == 0) goto L73
            int r4 = r6.size()
            r0 = 2
            if (r4 == r0) goto L11
            goto L73
        L11:
            java.lang.Iterable r6 = (java.lang.Iterable) r6
            boolean r4 = r6 instanceof java.util.Collection
            r0 = 1
            if (r4 == 0) goto L23
            r1 = r6
            java.util.Collection r1 = (java.util.Collection) r1
            boolean r1 = r1.isEmpty()
            if (r1 == 0) goto L23
        L21:
            r1 = r5
            goto L38
        L23:
            java.util.Iterator r1 = r6.iterator()
        L27:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L21
            java.lang.Object r2 = r1.next()
            androidx.camera.core.UseCase r2 = (androidx.camera.core.UseCase) r2
            boolean r2 = r2 instanceof androidx.camera.core.Preview
            if (r2 == 0) goto L27
            r1 = r0
        L38:
            if (r4 == 0) goto L45
            r4 = r6
            java.util.Collection r4 = (java.util.Collection) r4
            boolean r4 = r4.isEmpty()
            if (r4 == 0) goto L45
        L43:
            r4 = r5
            goto L6e
        L45:
            java.util.Iterator r4 = r6.iterator()
        L49:
            boolean r6 = r4.hasNext()
            if (r6 == 0) goto L43
            java.lang.Object r6 = r4.next()
            androidx.camera.core.UseCase r6 = (androidx.camera.core.UseCase) r6
            androidx.camera.core.impl.UseCaseConfig r2 = r6.getCurrentConfig()
            androidx.camera.core.impl.Config$Option<androidx.camera.core.impl.UseCaseConfigFactory$CaptureType> r3 = androidx.camera.core.impl.UseCaseConfig.OPTION_CAPTURE_TYPE
            boolean r2 = r2.containsOption(r3)
            if (r2 == 0) goto L49
            androidx.camera.core.impl.UseCaseConfig r6 = r6.getCurrentConfig()
            androidx.camera.core.impl.UseCaseConfigFactory$CaptureType r6 = r6.getCaptureType()
            androidx.camera.core.impl.UseCaseConfigFactory$CaptureType r2 = androidx.camera.core.impl.UseCaseConfigFactory.CaptureType.VIDEO_CAPTURE
            if (r6 != r2) goto L49
            r4 = r0
        L6e:
            if (r1 == 0) goto L73
            if (r4 == 0) goto L73
            return r0
        L73:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.internal.compat.quirk.PreviewGreenTintQuirk.shouldForceEnableStreamSharingForMotoE20(java.lang.String, java.util.Collection):boolean");
    }
}
