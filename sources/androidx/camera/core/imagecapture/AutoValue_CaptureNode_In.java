package androidx.camera.core.imagecapture;

import android.util.Size;
import androidx.camera.core.ImageReaderProxyProvider;
import androidx.camera.core.imagecapture.CaptureNode;
import androidx.camera.core.imagecapture.TakePictureManager;
import androidx.camera.core.processing.Edge;
import java.util.List;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
final class AutoValue_CaptureNode_In extends CaptureNode.AbstractC0260In {
    private final Edge<TakePictureManager.CaptureError> errorEdge;
    private final int inputFormat;
    private final List<Integer> outputFormats;
    private final PostviewSettings postviewSettings;
    private final Edge<ProcessingRequest> requestEdge;
    private final Size size;
    private final boolean virtualCamera;

    @Override // androidx.camera.core.imagecapture.CaptureNode.AbstractC0260In
    public ImageReaderProxyProvider getImageReaderProxyProvider() {
        return null;
    }

    public AutoValue_CaptureNode_In(Size size, int i, List<Integer> list, boolean z, ImageReaderProxyProvider imageReaderProxyProvider, PostviewSettings postviewSettings, Edge<ProcessingRequest> edge, Edge<TakePictureManager.CaptureError> edge2) {
        if (size == null) {
            g$$ExternalSyntheticBUOutline2.m208m("Null size");
            throw null;
        }
        this.size = size;
        this.inputFormat = i;
        if (list == null) {
            g$$ExternalSyntheticBUOutline2.m208m("Null outputFormats");
            throw null;
        }
        this.outputFormats = list;
        this.virtualCamera = z;
        this.postviewSettings = postviewSettings;
        if (edge == null) {
            g$$ExternalSyntheticBUOutline2.m208m("Null requestEdge");
            throw null;
        }
        this.requestEdge = edge;
        if (edge2 == null) {
            g$$ExternalSyntheticBUOutline2.m208m("Null errorEdge");
            throw null;
        }
        this.errorEdge = edge2;
    }

    @Override // androidx.camera.core.imagecapture.CaptureNode.AbstractC0260In
    public Size getSize() {
        return this.size;
    }

    @Override // androidx.camera.core.imagecapture.CaptureNode.AbstractC0260In
    public int getInputFormat() {
        return this.inputFormat;
    }

    @Override // androidx.camera.core.imagecapture.CaptureNode.AbstractC0260In
    public List<Integer> getOutputFormats() {
        return this.outputFormats;
    }

    @Override // androidx.camera.core.imagecapture.CaptureNode.AbstractC0260In
    public boolean isVirtualCamera() {
        return this.virtualCamera;
    }

    @Override // androidx.camera.core.imagecapture.CaptureNode.AbstractC0260In
    public PostviewSettings getPostviewSettings() {
        return this.postviewSettings;
    }

    @Override // androidx.camera.core.imagecapture.CaptureNode.AbstractC0260In
    public Edge<ProcessingRequest> getRequestEdge() {
        return this.requestEdge;
    }

    @Override // androidx.camera.core.imagecapture.CaptureNode.AbstractC0260In
    public Edge<TakePictureManager.CaptureError> getErrorEdge() {
        return this.errorEdge;
    }

    public String toString() {
        return "In{size=" + this.size + ", inputFormat=" + this.inputFormat + ", outputFormats=" + this.outputFormats + ", virtualCamera=" + this.virtualCamera + ", imageReaderProxyProvider=null, postviewSettings=" + this.postviewSettings + ", requestEdge=" + this.requestEdge + ", errorEdge=" + this.errorEdge + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof CaptureNode.AbstractC0260In) {
            CaptureNode.AbstractC0260In abstractC0260In = (CaptureNode.AbstractC0260In) obj;
            if (this.size.equals(abstractC0260In.getSize()) && this.inputFormat == abstractC0260In.getInputFormat() && this.outputFormats.equals(abstractC0260In.getOutputFormats()) && this.virtualCamera == abstractC0260In.isVirtualCamera()) {
                abstractC0260In.getImageReaderProxyProvider();
                abstractC0260In.getPostviewSettings();
                if (this.requestEdge.equals(abstractC0260In.getRequestEdge()) && this.errorEdge.equals(abstractC0260In.getErrorEdge())) {
                    return true;
                }
            }
        }
        return false;
    }

    public int hashCode() {
        return this.errorEdge.hashCode() ^ ((((((((((((this.size.hashCode() ^ 1000003) * 1000003) ^ this.inputFormat) * 1000003) ^ this.outputFormats.hashCode()) * 1000003) ^ (this.virtualCamera ? 1231 : 1237)) * (-721379959)) ^ 0) * 1000003) ^ this.requestEdge.hashCode()) * 1000003);
    }
}
