package androidx.camera.video.internal.encoder;

import android.media.MediaCodecInfo;
import kotlin.Metadata;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\u0019\b\u0000\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0004\b\u0007\u0010\bR\u0014\u0010\n\u001a\u00020\t8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\n\u0010\u000b¨\u0006\f"}, m877d2 = {"Landroidx/camera/video/internal/encoder/AudioEncoderInfoImpl;", "Landroidx/camera/video/internal/encoder/EncoderInfoImpl;", _UrlKt.FRAGMENT_ENCODE_SET, "Landroid/media/MediaCodecInfo;", "codecInfo", _UrlKt.FRAGMENT_ENCODE_SET, "mime", "<init>", "(Landroid/media/MediaCodecInfo;Ljava/lang/String;)V", "Landroid/media/MediaCodecInfo$AudioCapabilities;", "audioCapabilities", "Landroid/media/MediaCodecInfo$AudioCapabilities;", "camera-video"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
public final class AudioEncoderInfoImpl extends EncoderInfoImpl implements EncoderInfo {
    private final MediaCodecInfo.AudioCapabilities audioCapabilities;

    public AudioEncoderInfoImpl(MediaCodecInfo mediaCodecInfo, String str) {
        super(mediaCodecInfo, str);
        this.audioCapabilities = getCodecCapabilities().getAudioCapabilities();
    }
}
