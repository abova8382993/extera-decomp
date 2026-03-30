package androidx.camera.camera2.pipe.graph;

import androidx.camera.camera2.pipe.AeMode;
import androidx.camera.camera2.pipe.AfMode;
import androidx.camera.camera2.pipe.AwbMode;
import androidx.camera.camera2.pipe.FlashMode;
import java.util.List;
import java.util.Map;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;

/* JADX INFO: loaded from: classes3.dex */
public final class GraphState3A {
    private final AtomicRef _state = AtomicFU.atomic(new State3A(null, null, null, null, null, null, null, null, null, null, 1023, null));

    public final State3A getCurrent() {
        return (State3A) this._state.getValue();
    }

    /* JADX INFO: renamed from: update-7jOEVJU$default, reason: not valid java name */
    public static /* synthetic */ void m1909update7jOEVJU$default(GraphState3A graphState3A, AeMode aeMode, AfMode afMode, AwbMode awbMode, FlashMode flashMode, List list, List list2, List list3, Boolean bool, Boolean bool2, Boolean bool3, int i, Object obj) {
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
        graphState3A.m1910update7jOEVJU(aeMode, afMode, awbMode, flashMode, list, list2, list3, bool, bool2, bool3);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0069  */
    /* JADX INFO: renamed from: update-7jOEVJU, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void m1910update7jOEVJU(androidx.camera.camera2.pipe.AeMode r15, androidx.camera.camera2.pipe.AfMode r16, androidx.camera.camera2.pipe.AwbMode r17, androidx.camera.camera2.pipe.FlashMode r18, java.util.List r19, java.util.List r20, java.util.List r21, java.lang.Boolean r22, java.lang.Boolean r23, java.lang.Boolean r24) {
        /*
            r14 = this;
            kotlinx.atomicfu.AtomicRef r0 = r14._state
        L2:
            java.lang.Object r1 = r0.getValue()
            r2 = r1
            androidx.camera.camera2.pipe.graph.State3A r2 = (androidx.camera.camera2.pipe.graph.State3A) r2
            if (r15 != 0) goto L10
            androidx.camera.camera2.pipe.AeMode r3 = r2.m1917getAeModeO_cDUUs()
            goto L11
        L10:
            r3 = r15
        L11:
            if (r16 != 0) goto L18
            androidx.camera.camera2.pipe.AfMode r4 = r2.m1918getAfMode32_E3BI()
            goto L1a
        L18:
            r4 = r16
        L1a:
            if (r17 != 0) goto L21
            androidx.camera.camera2.pipe.AwbMode r5 = r2.m1919getAwbModeaLFtWSU()
            goto L23
        L21:
            r5 = r17
        L23:
            if (r18 != 0) goto L2a
            androidx.camera.camera2.pipe.FlashMode r6 = r2.m1920getFlashModecL19HE()
            goto L2c
        L2a:
            r6 = r18
        L2c:
            r7 = 0
            if (r19 == 0) goto L3e
            r8 = r19
            java.util.Collection r8 = (java.util.Collection) r8
            boolean r9 = r8.isEmpty()
            if (r9 == 0) goto L3a
            r8 = r7
        L3a:
            java.util.List r8 = (java.util.List) r8
            if (r8 != 0) goto L42
        L3e:
            java.util.List r8 = r2.getAeRegions()
        L42:
            if (r20 == 0) goto L53
            r9 = r20
            java.util.Collection r9 = (java.util.Collection) r9
            boolean r10 = r9.isEmpty()
            if (r10 == 0) goto L4f
            r9 = r7
        L4f:
            java.util.List r9 = (java.util.List) r9
            if (r9 != 0) goto L57
        L53:
            java.util.List r9 = r2.getAfRegions()
        L57:
            if (r21 == 0) goto L69
            r10 = r21
            java.util.Collection r10 = (java.util.Collection) r10
            boolean r11 = r10.isEmpty()
            if (r11 == 0) goto L64
            goto L65
        L64:
            r7 = r10
        L65:
            java.util.List r7 = (java.util.List) r7
            if (r7 != 0) goto L6d
        L69:
            java.util.List r7 = r2.getAwbRegions()
        L6d:
            if (r22 != 0) goto L74
            java.lang.Boolean r10 = r2.getAeLock()
            goto L76
        L74:
            r10 = r22
        L76:
            if (r23 != 0) goto L7d
            java.lang.Boolean r11 = r2.getAfLock()
            goto L7f
        L7d:
            r11 = r23
        L7f:
            if (r24 != 0) goto L8a
            java.lang.Boolean r12 = r2.getAwbLock()
            r13 = r9
            r9 = r7
            r7 = r8
            r8 = r13
            goto L90
        L8a:
            r12 = r9
            r9 = r7
            r7 = r8
            r8 = r12
            r12 = r24
        L90:
            androidx.camera.camera2.pipe.graph.State3A r2 = r2.m1916copy7jOEVJU(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)
            boolean r1 = r0.compareAndSet(r1, r2)
            if (r1 == 0) goto L2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.graph.GraphState3A.m1910update7jOEVJU(androidx.camera.camera2.pipe.AeMode, androidx.camera.camera2.pipe.AfMode, androidx.camera.camera2.pipe.AwbMode, androidx.camera.camera2.pipe.FlashMode, java.util.List, java.util.List, java.util.List, java.lang.Boolean, java.lang.Boolean, java.lang.Boolean):void");
    }

    public final Map toCaptureRequestParametersMap() {
        return GraphState3AKt.toCaptureRequestParameterMap(getCurrent());
    }
}
