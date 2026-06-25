package androidx.camera.core.imagecapture;

import androidx.camera.core.imagecapture.ProcessingNode;
import androidx.camera.core.processing.Edge;
import java.util.List;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
final class AutoValue_ProcessingNode_In extends ProcessingNode.AbstractC0263In {
    private final Edge<ProcessingNode.InputPacket> edge;
    private final int inputFormat;
    private final List<Integer> outputFormats;
    private final Edge<ProcessingNode.InputPacket> postviewEdge;

    public AutoValue_ProcessingNode_In(Edge<ProcessingNode.InputPacket> edge, Edge<ProcessingNode.InputPacket> edge2, int i, List<Integer> list) {
        if (edge == null) {
            g$$ExternalSyntheticBUOutline2.m208m("Null edge");
            throw null;
        }
        this.edge = edge;
        if (edge2 == null) {
            g$$ExternalSyntheticBUOutline2.m208m("Null postviewEdge");
            throw null;
        }
        this.postviewEdge = edge2;
        this.inputFormat = i;
        if (list == null) {
            g$$ExternalSyntheticBUOutline2.m208m("Null outputFormats");
            throw null;
        }
        this.outputFormats = list;
    }

    @Override // androidx.camera.core.imagecapture.ProcessingNode.AbstractC0263In
    public Edge<ProcessingNode.InputPacket> getEdge() {
        return this.edge;
    }

    @Override // androidx.camera.core.imagecapture.ProcessingNode.AbstractC0263In
    public Edge<ProcessingNode.InputPacket> getPostviewEdge() {
        return this.postviewEdge;
    }

    @Override // androidx.camera.core.imagecapture.ProcessingNode.AbstractC0263In
    public int getInputFormat() {
        return this.inputFormat;
    }

    @Override // androidx.camera.core.imagecapture.ProcessingNode.AbstractC0263In
    public List<Integer> getOutputFormats() {
        return this.outputFormats;
    }

    public String toString() {
        return "In{edge=" + this.edge + ", postviewEdge=" + this.postviewEdge + ", inputFormat=" + this.inputFormat + ", outputFormats=" + this.outputFormats + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ProcessingNode.AbstractC0263In) {
            ProcessingNode.AbstractC0263In abstractC0263In = (ProcessingNode.AbstractC0263In) obj;
            if (this.edge.equals(abstractC0263In.getEdge()) && this.postviewEdge.equals(abstractC0263In.getPostviewEdge()) && this.inputFormat == abstractC0263In.getInputFormat() && this.outputFormats.equals(abstractC0263In.getOutputFormats())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.outputFormats.hashCode() ^ ((((((this.edge.hashCode() ^ 1000003) * 1000003) ^ this.postviewEdge.hashCode()) * 1000003) ^ this.inputFormat) * 1000003);
    }
}
