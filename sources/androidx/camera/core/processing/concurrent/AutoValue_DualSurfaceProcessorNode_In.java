package androidx.camera.core.processing.concurrent;

import androidx.camera.core.processing.SurfaceEdge;
import androidx.camera.core.processing.concurrent.DualSurfaceProcessorNode;
import java.util.List;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
final class AutoValue_DualSurfaceProcessorNode_In extends DualSurfaceProcessorNode.AbstractC0298In {
    private final List<DualOutConfig> outConfigs;
    private final SurfaceEdge primarySurfaceEdge;
    private final SurfaceEdge secondarySurfaceEdge;

    public AutoValue_DualSurfaceProcessorNode_In(SurfaceEdge surfaceEdge, SurfaceEdge surfaceEdge2, List<DualOutConfig> list) {
        if (surfaceEdge == null) {
            g$$ExternalSyntheticBUOutline2.m208m("Null primarySurfaceEdge");
            throw null;
        }
        this.primarySurfaceEdge = surfaceEdge;
        if (surfaceEdge2 == null) {
            g$$ExternalSyntheticBUOutline2.m208m("Null secondarySurfaceEdge");
            throw null;
        }
        this.secondarySurfaceEdge = surfaceEdge2;
        if (list == null) {
            g$$ExternalSyntheticBUOutline2.m208m("Null outConfigs");
            throw null;
        }
        this.outConfigs = list;
    }

    @Override // androidx.camera.core.processing.concurrent.DualSurfaceProcessorNode.AbstractC0298In
    public SurfaceEdge getPrimarySurfaceEdge() {
        return this.primarySurfaceEdge;
    }

    @Override // androidx.camera.core.processing.concurrent.DualSurfaceProcessorNode.AbstractC0298In
    public SurfaceEdge getSecondarySurfaceEdge() {
        return this.secondarySurfaceEdge;
    }

    @Override // androidx.camera.core.processing.concurrent.DualSurfaceProcessorNode.AbstractC0298In
    public List<DualOutConfig> getOutConfigs() {
        return this.outConfigs;
    }

    public String toString() {
        return "In{primarySurfaceEdge=" + this.primarySurfaceEdge + ", secondarySurfaceEdge=" + this.secondarySurfaceEdge + ", outConfigs=" + this.outConfigs + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof DualSurfaceProcessorNode.AbstractC0298In) {
            DualSurfaceProcessorNode.AbstractC0298In abstractC0298In = (DualSurfaceProcessorNode.AbstractC0298In) obj;
            if (this.primarySurfaceEdge.equals(abstractC0298In.getPrimarySurfaceEdge()) && this.secondarySurfaceEdge.equals(abstractC0298In.getSecondarySurfaceEdge()) && this.outConfigs.equals(abstractC0298In.getOutConfigs())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.outConfigs.hashCode() ^ ((((this.primarySurfaceEdge.hashCode() ^ 1000003) * 1000003) ^ this.secondarySurfaceEdge.hashCode()) * 1000003);
    }
}
