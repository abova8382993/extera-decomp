package androidx.camera.core.imagecapture;

import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.impl.utils.Exif;
import androidx.camera.core.internal.compat.workaround.InvalidJpegDataParser;
import androidx.camera.core.processing.Operation;
import androidx.camera.core.processing.Packet;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

/* JADX INFO: loaded from: classes4.dex */
public class JpegBytes2Disk implements Operation<AbstractC0262In, ImageCapture.OutputFileResults> {

    /* JADX INFO: renamed from: androidx.camera.core.imagecapture.JpegBytes2Disk$In */
    public static abstract class AbstractC0262In {
        public abstract ImageCapture.OutputFileOptions getOutputFileOptions();

        public abstract Packet<byte[]> getPacket();
    }

    @Override // androidx.camera.core.processing.Operation
    public ImageCapture.OutputFileResults apply(AbstractC0262In abstractC0262In) throws ImageCaptureException {
        Packet<byte[]> packet = abstractC0262In.getPacket();
        abstractC0262In.getOutputFileOptions();
        File fileCreateTempFile = FileUtil.createTempFile(null);
        writeBytesToFile(fileCreateTempFile, packet.getData());
        Exif exif = packet.getExif();
        Objects.requireNonNull(exif);
        FileUtil.updateFileExif(fileCreateTempFile, exif, null, packet.getRotationDegrees());
        return new ImageCapture.OutputFileResults(FileUtil.moveFileToTarget(fileCreateTempFile, null), 256);
    }

    public static void writeBytesToFile(File file, byte[] bArr) throws ImageCaptureException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                fileOutputStream.write(bArr, 0, new InvalidJpegDataParser().getValidDataLength(bArr));
                fileOutputStream.close();
            } finally {
            }
        } catch (IOException e) {
            throw new ImageCaptureException(1, "Failed to write to temp file", e);
        }
    }
}
