package androidx.camera.camera2.pipe.config;

import androidx.camera.camera2.pipe.CameraGraph;
import androidx.camera.camera2.pipe.CameraSurfaceManager;
import androidx.camera.camera2.pipe.core.SystemClockOffsets;
import androidx.camera.camera2.pipe.core.Threads;
import androidx.camera.camera2.pipe.graph.Listener3A;
import androidx.camera.camera2.pipe.graph.StreamGraphImpl;
import androidx.camera.camera2.pipe.graph.SurfaceGraph;
import androidx.camera.camera2.pipe.internal.FrameDistributor;
import java.util.List;
import javax.inject.Provider;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineName;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;

/* JADX INFO: loaded from: classes3.dex */
public abstract class SharedCameraGraphModules {
    public static final Companion Companion = new Companion(null);

    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final CoroutineScope provideCameraGraphCoroutineScope(Threads threads, Job cameraPipeJob) {
            Intrinsics.checkNotNullParameter(threads, "threads");
            Intrinsics.checkNotNullParameter(cameraPipeJob, "cameraPipeJob");
            return CoroutineScopeKt.CoroutineScope(SupervisorKt.SupervisorJob(cameraPipeJob).plus(threads.getLightweightDispatcher().plus(new CoroutineName("CXCP-Graph"))));
        }

        public final List provideRequestListeners(CameraGraph.Config graphConfig, Listener3A listener3A, FrameDistributor frameDistributor) {
            Intrinsics.checkNotNullParameter(graphConfig, "graphConfig");
            Intrinsics.checkNotNullParameter(listener3A, "listener3A");
            Intrinsics.checkNotNullParameter(frameDistributor, "frameDistributor");
            List listMutableListOf = CollectionsKt.mutableListOf(listener3A);
            listMutableListOf.add(listener3A);
            listMutableListOf.add(frameDistributor);
            listMutableListOf.addAll(graphConfig.getDefaultListeners());
            return listMutableListOf;
        }

        public final SurfaceGraph provideSurfaceGraph(StreamGraphImpl streamGraphImpl, Provider cameraController, CameraSurfaceManager cameraSurfaceManager) {
            Intrinsics.checkNotNullParameter(streamGraphImpl, "streamGraphImpl");
            Intrinsics.checkNotNullParameter(cameraController, "cameraController");
            Intrinsics.checkNotNullParameter(cameraSurfaceManager, "cameraSurfaceManager");
            return new SurfaceGraph(streamGraphImpl, cameraController, cameraSurfaceManager, streamGraphImpl.getImageSourceMap$camera_camera2_pipe());
        }

        /* JADX WARN: Removed duplicated region for block: B:8:0x002f  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final androidx.camera.camera2.pipe.internal.FrameDistributor provideFrameDistributor(androidx.camera.camera2.pipe.graph.StreamGraphImpl r8, androidx.camera.camera2.pipe.internal.FrameCaptureQueue r9, androidx.camera.camera2.pipe.CameraMetadata r10, androidx.camera.camera2.pipe.core.SystemClockOffsets r11) {
            /*
                r7 = this;
                java.lang.String r0 = "streamGraphImpl"
                kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r8, r0)
                java.lang.String r0 = "frameCaptureQueue"
                kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r9, r0)
                java.lang.String r0 = "cameraMetadata"
                kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r10, r0)
                java.lang.String r0 = "systemClockOffsets"
                kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r11, r0)
                android.hardware.camera2.CameraCharacteristics$Key r0 = android.hardware.camera2.CameraCharacteristics.SENSOR_INFO_TIMESTAMP_SOURCE
                java.lang.String r1 = "SENSOR_INFO_TIMESTAMP_SOURCE"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
                java.lang.Object r10 = r10.get(r0)
                java.lang.Integer r10 = (java.lang.Integer) r10
                if (r10 != 0) goto L26
                goto L2f
            L26:
                int r10 = r10.intValue()
                r0 = 1
                if (r10 != r0) goto L2f
            L2d:
                r4 = r0
                goto L31
            L2f:
                r0 = 0
                goto L2d
            L31:
                androidx.camera.camera2.pipe.internal.FrameDistributor r1 = new androidx.camera.camera2.pipe.internal.FrameDistributor
                long r5 = r11.getRealtimeNsToMonotonicNs()
                r2 = r8
                r3 = r9
                r1.<init>(r2, r3, r4, r5)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.config.SharedCameraGraphModules.Companion.provideFrameDistributor(androidx.camera.camera2.pipe.graph.StreamGraphImpl, androidx.camera.camera2.pipe.internal.FrameCaptureQueue, androidx.camera.camera2.pipe.CameraMetadata, androidx.camera.camera2.pipe.core.SystemClockOffsets):androidx.camera.camera2.pipe.internal.FrameDistributor");
        }

        public final SystemClockOffsets provideSystemClockOffsets() {
            return SystemClockOffsets.Companion.estimate();
        }
    }
}
