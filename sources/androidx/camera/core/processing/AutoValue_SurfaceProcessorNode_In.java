package androidx.camera.core.processing;

import androidx.camera.core.processing.SurfaceProcessorNode;
import androidx.camera.core.processing.util.OutConfig;
import java.util.List;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
final class AutoValue_SurfaceProcessorNode_In extends SurfaceProcessorNode.AbstractC0296In {
    private final List<OutConfig> outConfigs;
    private final SurfaceEdge surfaceEdge;

    public AutoValue_SurfaceProcessorNode_In(SurfaceEdge surfaceEdge, List<OutConfig> list) {
        if (surfaceEdge == null) {
            g$$ExternalSyntheticBUOutline2.m208m("Null surfaceEdge");
            throw null;
        }
        this.surfaceEdge = surfaceEdge;
        if (list == null) {
            g$$ExternalSyntheticBUOutline2.m208m("Null outConfigs");
            throw null;
        }
        this.outConfigs = list;
    }

    @Override // androidx.camera.core.processing.SurfaceProcessorNode.AbstractC0296In
    public SurfaceEdge getSurfaceEdge() {
        return this.surfaceEdge;
    }

    @Override // androidx.camera.core.processing.SurfaceProcessorNode.AbstractC0296In
    public List<OutConfig> getOutConfigs() {
        return this.outConfigs;
    }

    public String toString() {
        return "In{surfaceEdge=" + this.surfaceEdge + ", outConfigs=" + this.outConfigs + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof SurfaceProcessorNode.AbstractC0296In) {
            SurfaceProcessorNode.AbstractC0296In abstractC0296In = (SurfaceProcessorNode.AbstractC0296In) obj;
            if (this.surfaceEdge.equals(abstractC0296In.getSurfaceEdge()) && this.outConfigs.equals(abstractC0296In.getOutConfigs())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.outConfigs.hashCode() ^ ((this.surfaceEdge.hashCode() ^ 1000003) * 1000003);
    }
}
