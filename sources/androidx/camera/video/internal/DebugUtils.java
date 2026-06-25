package androidx.camera.video.internal;

import android.media.MediaFormat;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import kotlin.time.DurationKt;

/* JADX INFO: loaded from: classes4.dex */
public abstract class DebugUtils {
    public static String readableUs(long j) {
        return readableMs(j / 1000);
    }

    public static String readableMs(long j) {
        return formatInterval(j);
    }

    private static String formatInterval(long j) {
        long j2 = j / DurationKt.MILLIS_IN_HOUR;
        TimeUnit timeUnit = TimeUnit.HOURS;
        long millis = (j - timeUnit.toMillis(j2)) / 60000;
        long millis2 = j - timeUnit.toMillis(j2);
        TimeUnit timeUnit2 = TimeUnit.MINUTES;
        long millis3 = (millis2 - timeUnit2.toMillis(millis)) / 1000;
        return String.format(Locale.US, "%02d:%02d:%02d.%03d", Long.valueOf(j2), Long.valueOf(millis), Long.valueOf(millis3), Long.valueOf(((j - timeUnit.toMillis(j2)) - timeUnit2.toMillis(millis)) - TimeUnit.SECONDS.toMillis(millis3)));
    }

    public static String bytesToHexString(byte[] bArr) {
        if (bArr == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b2 : bArr) {
            sb.append(String.format("%02X ", Byte.valueOf(b2)));
        }
        return sb.toString().trim();
    }

    public static String byteBufferToHex(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            return "null";
        }
        int iPosition = byteBuffer.position();
        try {
            byte[] bArr = new byte[byteBuffer.remaining()];
            byteBuffer.get(bArr);
            return bytesToHexString(bArr);
        } finally {
            byteBuffer.position(iPosition);
        }
    }

    public static String getCsdHex(MediaFormat mediaFormat) {
        StringBuilder sb = new StringBuilder("{csd-0 = ");
        sb.append(byteBufferToHex(mediaFormat.getByteBuffer("csd-0")));
        if (mediaFormat.containsKey("csd-1")) {
            sb.append(", csd-1 = ");
            sb.append(byteBufferToHex(mediaFormat.getByteBuffer("csd-1")));
        }
        if (mediaFormat.containsKey("csd-2")) {
            sb.append(", csd-2 = ");
            sb.append(byteBufferToHex(mediaFormat.getByteBuffer("csd-2")));
        }
        sb.append("}");
        return sb.toString();
    }
}
