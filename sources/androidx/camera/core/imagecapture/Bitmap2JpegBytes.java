package androidx.camera.core.imagecapture;

import android.graphics.Bitmap;
import android.os.Build;
import androidx.camera.core.impl.utils.Exif;
import androidx.camera.core.processing.Operation;
import androidx.camera.core.processing.Packet;
import java.io.ByteArrayOutputStream;
import java.util.Objects;

/* JADX INFO: loaded from: classes4.dex */
public class Bitmap2JpegBytes implements Operation<AbstractC0257In, Packet<byte[]>> {
    @Override // androidx.camera.core.processing.Operation
    public Packet<byte[]> apply(AbstractC0257In abstractC0257In) {
        Packet<Bitmap> packet = abstractC0257In.getPacket();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        packet.getData().compress(Bitmap.CompressFormat.JPEG, abstractC0257In.getJpegQuality(), byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        Exif exif = packet.getExif();
        Objects.requireNonNull(exif);
        return Packet.m96of(byteArray, exif, getOutputFormat(packet.getData()), packet.getSize(), packet.getCropRect(), packet.getRotationDegrees(), packet.getSensorToBufferTransform(), packet.getCameraCaptureResult());
    }

    private static int getOutputFormat(Bitmap bitmap) {
        return (Build.VERSION.SDK_INT < 34 || !Api34Impl.hasGainmap(bitmap)) ? 256 : 4101;
    }

    public static class Api34Impl {
        public static boolean hasGainmap(Bitmap bitmap) {
            return bitmap.hasGainmap();
        }
    }

    /* JADX INFO: renamed from: androidx.camera.core.imagecapture.Bitmap2JpegBytes$In */
    public static abstract class AbstractC0257In {
        public abstract int getJpegQuality();

        public abstract Packet<Bitmap> getPacket();

        /* JADX INFO: renamed from: of */
        public static AbstractC0257In m86of(Packet<Bitmap> packet, int i) {
            return new AutoValue_Bitmap2JpegBytes_In(packet, i);
        }
    }
}
