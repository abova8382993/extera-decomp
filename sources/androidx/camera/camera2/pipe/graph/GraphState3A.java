package androidx.camera.camera2.pipe.graph;

import android.hardware.camera2.CaptureRequest;
import androidx.camera.camera2.pipe.AeMode;
import androidx.camera.camera2.pipe.AfMode;
import androidx.camera.camera2.pipe.AwbMode;
import androidx.camera.camera2.pipe.FlashMode;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u0001\u0018\u00002\u00020\u0001B\t\b\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0097\u0001\u0010\u0018\u001a\u00020\u00152\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\n2\u0010\b\u0002\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f2\u0010\b\u0002\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f2\u0010\b\u0002\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f2\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00112\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00112\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0011¢\u0006\u0004\b\u0016\u0010\u0017J\u001d\u0010\u001b\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001a\u0012\u0004\u0012\u00020\u00010\u0019¢\u0006\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u001f\u0010 R$\u0010&\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\u001e8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%¨\u0006'"}, m877d2 = {"Landroidx/camera/camera2/pipe/graph/GraphState3A;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "Landroidx/camera/camera2/pipe/AeMode;", "aeMode", "Landroidx/camera/camera2/pipe/AfMode;", "afMode", "Landroidx/camera/camera2/pipe/AwbMode;", "awbMode", "Landroidx/camera/camera2/pipe/FlashMode;", "flashMode", _UrlKt.FRAGMENT_ENCODE_SET, "Landroid/hardware/camera2/params/MeteringRectangle;", "aeRegions", "afRegions", "awbRegions", _UrlKt.FRAGMENT_ENCODE_SET, "aeLock", "afLock", "awbLock", _UrlKt.FRAGMENT_ENCODE_SET, "update-7jOEVJU", "(Landroidx/camera/camera2/pipe/AeMode;Landroidx/camera/camera2/pipe/AfMode;Landroidx/camera/camera2/pipe/AwbMode;Landroidx/camera/camera2/pipe/FlashMode;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;)V", "update", _UrlKt.FRAGMENT_ENCODE_SET, "Landroid/hardware/camera2/CaptureRequest$Key;", "toCaptureRequestParametersMap", "()Ljava/util/Map;", "Lkotlinx/atomicfu/AtomicRef;", "Landroidx/camera/camera2/pipe/graph/State3A;", "_state", "Lkotlinx/atomicfu/AtomicRef;", "value", "getCurrent", "()Landroidx/camera/camera2/pipe/graph/State3A;", "setCurrent", "(Landroidx/camera/camera2/pipe/graph/State3A;)V", "current", "camera-camera2-pipe"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nGraphState3A.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GraphState3A.kt\nandroidx/camera/camera2/pipe/graph/GraphState3A\n+ 2 AtomicFU.common.kt\nkotlinx/atomicfu/AtomicFU_commonKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,154:1\n164#2,3:155\n167#2:159\n1#3:158\n*S KotlinDebug\n*F\n+ 1 GraphState3A.kt\nandroidx/camera/camera2/pipe/graph/GraphState3A\n*L\n134#1:155,3\n134#1:159\n*E\n"})
public final class GraphState3A {
    private final AtomicRef<State3A> _state = AtomicFU.atomic(new State3A(null, null, null, null, null, null, null, null, null, null, 1023, null));

    public final State3A getCurrent() {
        return this._state.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: update-7jOEVJU$default, reason: not valid java name */
    public static /* synthetic */ void m1794update7jOEVJU$default(GraphState3A graphState3A, AeMode aeMode, AfMode afMode, AwbMode awbMode, FlashMode flashMode, List list, List list2, List list3, Boolean bool, Boolean bool2, Boolean bool3, int i, Object obj) {
        if ((i & 1) != 0) {
            aeMode = null;
        }
        if ((i & 2) != 0) {
            afMode = null;
        }
        if ((i & 4) != 0) {
            awbMode = null;
        }
        if ((i & 8) != 0) {
            flashMode = null;
        }
        if ((i & 16) != 0) {
            list = null;
        }
        if ((i & 32) != 0) {
            list2 = null;
        }
        if ((i & 64) != 0) {
            list3 = null;
        }
        if ((i & 128) != 0) {
            bool = null;
        }
        if ((i & 256) != 0) {
            bool2 = null;
        }
        if ((i & 512) != 0) {
            bool3 = null;
        }
        graphState3A.m1795update7jOEVJU(aeMode, afMode, awbMode, flashMode, list, list2, list3, bool, bool2, bool3);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0068  */
    /* JADX INFO: renamed from: update-7jOEVJU, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void m1795update7jOEVJU(androidx.camera.camera2.pipe.AeMode r14, androidx.camera.camera2.pipe.AfMode r15, androidx.camera.camera2.pipe.AwbMode r16, androidx.camera.camera2.pipe.FlashMode r17, java.util.List<android.hardware.camera2.params.MeteringRectangle> r18, java.util.List<android.hardware.camera2.params.MeteringRectangle> r19, java.util.List<android.hardware.camera2.params.MeteringRectangle> r20, java.lang.Boolean r21, java.lang.Boolean r22, java.lang.Boolean r23) {
        /*
            r13 = this;
            kotlinx.atomicfu.AtomicRef<androidx.camera.camera2.pipe.graph.State3A> r13 = r13._state
        L2:
            java.lang.Object r0 = r13.getValue()
            r1 = r0
            androidx.camera.camera2.pipe.graph.State3A r1 = (androidx.camera.camera2.pipe.graph.State3A) r1
            if (r14 != 0) goto L10
            androidx.camera.camera2.pipe.AeMode r2 = r1.getAeMode()
            goto L11
        L10:
            r2 = r14
        L11:
            if (r15 != 0) goto L18
            androidx.camera.camera2.pipe.AfMode r3 = r1.getAfMode()
            goto L19
        L18:
            r3 = r15
        L19:
            if (r16 != 0) goto L20
            androidx.camera.camera2.pipe.AwbMode r4 = r1.getAwbMode()
            goto L22
        L20:
            r4 = r16
        L22:
            if (r17 != 0) goto L29
            androidx.camera.camera2.pipe.FlashMode r5 = r1.getFlashMode()
            goto L2b
        L29:
            r5 = r17
        L2b:
            r6 = 0
            if (r18 == 0) goto L3d
            r7 = r18
            java.util.Collection r7 = (java.util.Collection) r7
            boolean r8 = r7.isEmpty()
            if (r8 == 0) goto L39
            r7 = r6
        L39:
            java.util.List r7 = (java.util.List) r7
            if (r7 != 0) goto L41
        L3d:
            java.util.List r7 = r1.getAeRegions()
        L41:
            if (r19 == 0) goto L52
            r8 = r19
            java.util.Collection r8 = (java.util.Collection) r8
            boolean r9 = r8.isEmpty()
            if (r9 == 0) goto L4e
            r8 = r6
        L4e:
            java.util.List r8 = (java.util.List) r8
            if (r8 != 0) goto L56
        L52:
            java.util.List r8 = r1.getAfRegions()
        L56:
            if (r20 == 0) goto L68
            r9 = r20
            java.util.Collection r9 = (java.util.Collection) r9
            boolean r10 = r9.isEmpty()
            if (r10 == 0) goto L63
            goto L64
        L63:
            r6 = r9
        L64:
            java.util.List r6 = (java.util.List) r6
            if (r6 != 0) goto L6c
        L68:
            java.util.List r6 = r1.getAwbRegions()
        L6c:
            if (r21 != 0) goto L73
            java.lang.Boolean r9 = r1.getAeLock()
            goto L75
        L73:
            r9 = r21
        L75:
            if (r22 != 0) goto L7c
            java.lang.Boolean r10 = r1.getAfLock()
            goto L7e
        L7c:
            r10 = r22
        L7e:
            if (r23 != 0) goto L89
            java.lang.Boolean r11 = r1.getAwbLock()
            r12 = r8
            r8 = r6
            r6 = r7
            r7 = r12
            goto L8f
        L89:
            r11 = r8
            r8 = r6
            r6 = r7
            r7 = r11
            r11 = r23
        L8f:
            androidx.camera.camera2.pipe.graph.State3A r1 = r1.m1801copy7jOEVJU(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
            boolean r0 = r13.compareAndSet(r0, r1)
            if (r0 == 0) goto L2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.graph.GraphState3A.m1795update7jOEVJU(androidx.camera.camera2.pipe.AeMode, androidx.camera.camera2.pipe.AfMode, androidx.camera.camera2.pipe.AwbMode, androidx.camera.camera2.pipe.FlashMode, java.util.List, java.util.List, java.util.List, java.lang.Boolean, java.lang.Boolean, java.lang.Boolean):void");
    }

    public final Map<CaptureRequest.Key<?>, Object> toCaptureRequestParametersMap() {
        return GraphState3AKt.toCaptureRequestParameterMap(getCurrent());
    }
}
