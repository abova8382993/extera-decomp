package androidx.camera.core.imagecapture;

import android.graphics.Rect;
import android.util.Size;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.impl.Quirks;
import androidx.camera.core.impl.utils.Exif;
import androidx.camera.core.impl.utils.TransformUtils;
import androidx.camera.core.internal.compat.workaround.JpegMetadataCorrector;
import androidx.camera.core.internal.utils.ImageUtil;
import androidx.camera.core.processing.Operation;
import androidx.camera.core.processing.Packet;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;

/* JADX INFO: loaded from: classes4.dex */
final class Image2JpegBytes implements Operation<AbstractC0261In, Packet<byte[]>> {
    private final JpegMetadataCorrector mJpegMetadataCorrector;

    public Image2JpegBytes(Quirks quirks) {
        this.mJpegMetadataCorrector = new JpegMetadataCorrector(quirks);
    }

    @Override // androidx.camera.core.processing.Operation
    public Packet<byte[]> apply(AbstractC0261In abstractC0261In) {
        Packet<byte[]> packetProcessYuvImage;
        try {
            int format = abstractC0261In.getPacket().getFormat();
            if (format != 35) {
                if (format != 256 && format != 4101) {
                    throw new IllegalArgumentException("Unexpected format: " + format);
                }
                packetProcessYuvImage = processJpegImage(abstractC0261In, format);
            } else {
                packetProcessYuvImage = processYuvImage(abstractC0261In);
            }
            abstractC0261In.getPacket().getData().close();
            return packetProcessYuvImage;
        } catch (Throwable th) {
            abstractC0261In.getPacket().getData().close();
            throw th;
        }
    }

    private Packet<byte[]> processJpegImage(AbstractC0261In abstractC0261In, int i) {
        Packet<ImageProxy> packet = abstractC0261In.getPacket();
        byte[] bArrJpegImageToJpegByteArray = this.mJpegMetadataCorrector.jpegImageToJpegByteArray(packet.getData());
        Exif exif = packet.getExif();
        Objects.requireNonNull(exif);
        return Packet.m96of(bArrJpegImageToJpegByteArray, exif, i, packet.getSize(), packet.getCropRect(), packet.getRotationDegrees(), packet.getSensorToBufferTransform(), packet.getCameraCaptureResult());
    }

    private Packet<byte[]> processYuvImage(AbstractC0261In abstractC0261In) throws ImageCaptureException {
        Packet<ImageProxy> packet = abstractC0261In.getPacket();
        ImageProxy data = packet.getData();
        Rect cropRect = packet.getCropRect();
        try {
            byte[] bArrYuvImageToJpegByteArray = ImageUtil.yuvImageToJpegByteArray(data, cropRect, abstractC0261In.getJpegQuality(), packet.getRotationDegrees());
            return Packet.m96of(bArrYuvImageToJpegByteArray, extractExif(bArrYuvImageToJpegByteArray), 256, new Size(cropRect.width(), cropRect.height()), new Rect(0, 0, cropRect.width(), cropRect.height()), packet.getRotationDegrees(), TransformUtils.updateSensorToBufferTransform(packet.getSensorToBufferTransform(), cropRect), packet.getCameraCaptureResult());
        } catch (ImageUtil.CodecFailedException e) {
            throw new ImageCaptureException(1, "Failed to encode the image to JPEG.", e);
        }
    }

    private static Exif extractExif(byte[] bArr) throws ImageCaptureException {
        try {
            return Exif.createFromInputStream(new ByteArrayInputStream(bArr));
        } catch (IOException e) {
            throw new ImageCaptureException(0, "Failed to extract Exif from YUV-generated JPEG", e);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.core.imagecapture.Image2JpegBytes$In */
    public static abstract class AbstractC0261In {
        public abstract int getJpegQuality();

        public abstract Packet<ImageProxy> getPacket();

        /* JADX INFO: renamed from: of */
        public static AbstractC0261In m88of(Packet<ImageProxy> packet, int i) {
            return new AutoValue_Image2JpegBytes_In(packet, i);
        }
    }
}
