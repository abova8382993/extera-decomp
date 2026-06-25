package androidx.camera.core.imagecapture;

import androidx.camera.core.ImageProxy;
import androidx.camera.core.imagecapture.Image2JpegBytes;
import androidx.camera.core.processing.Packet;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
final class AutoValue_Image2JpegBytes_In extends Image2JpegBytes.AbstractC0261In {
    private final int jpegQuality;
    private final Packet<ImageProxy> packet;

    public AutoValue_Image2JpegBytes_In(Packet<ImageProxy> packet, int i) {
        if (packet == null) {
            g$$ExternalSyntheticBUOutline2.m208m("Null packet");
            throw null;
        }
        this.packet = packet;
        this.jpegQuality = i;
    }

    @Override // androidx.camera.core.imagecapture.Image2JpegBytes.AbstractC0261In
    public Packet<ImageProxy> getPacket() {
        return this.packet;
    }

    @Override // androidx.camera.core.imagecapture.Image2JpegBytes.AbstractC0261In
    public int getJpegQuality() {
        return this.jpegQuality;
    }

    public String toString() {
        return "In{packet=" + this.packet + ", jpegQuality=" + this.jpegQuality + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Image2JpegBytes.AbstractC0261In) {
            Image2JpegBytes.AbstractC0261In abstractC0261In = (Image2JpegBytes.AbstractC0261In) obj;
            if (this.packet.equals(abstractC0261In.getPacket()) && this.jpegQuality == abstractC0261In.getJpegQuality()) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.jpegQuality ^ ((this.packet.hashCode() ^ 1000003) * 1000003);
    }
}
