package androidx.camera.video.internal.utils;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.util.LruCache;
import androidx.camera.video.internal.encoder.EncoderConfig;
import androidx.camera.video.internal.encoder.InvalidConfigException;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.SourceDebugExtension;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0017\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0007¢\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\tH\u0007¢\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u0007\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\tH\u0002¢\u0006\u0004\b\u0007\u0010\u000eR \u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u000b0\u000f8\u0002X\u0083\u0004¢\u0006\u0006\n\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, m877d2 = {"Landroidx/camera/video/internal/utils/CodecUtil;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "Landroidx/camera/video/internal/encoder/EncoderConfig;", "encoderConfig", "Landroid/media/MediaCodec;", "createCodec", "(Landroidx/camera/video/internal/encoder/EncoderConfig;)Landroid/media/MediaCodec;", _UrlKt.FRAGMENT_ENCODE_SET, "mimeType", "Landroid/media/MediaCodecInfo;", "findCodecAndGetCodecInfo", "(Ljava/lang/String;)Landroid/media/MediaCodecInfo;", "(Ljava/lang/String;)Landroid/media/MediaCodec;", "Landroid/util/LruCache;", "codecInfoCache", "Landroid/util/LruCache;", "camera-video"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nCodecUtil.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CodecUtil.kt\nandroidx/camera/video/internal/utils/CodecUtil\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,138:1\n3829#2:139\n4344#2,2:140\n1#3:142\n1374#4:143\n1460#4,5:144\n774#4:149\n865#4,2:150\n774#4:152\n865#4,2:153\n*S KotlinDebug\n*F\n+ 1 CodecUtil.kt\nandroidx/camera/video/internal/utils/CodecUtil\n*L\n46#1:139\n46#1:140,2\n54#1:143\n54#1:144,5\n63#1:149\n63#1:150,2\n71#1:152\n71#1:153,2\n*E\n"})
public final class CodecUtil {
    public static final CodecUtil INSTANCE = new CodecUtil();
    private static final LruCache<String, MediaCodecInfo> codecInfoCache = new LruCache<>(10);

    private CodecUtil() {
    }

    @JvmStatic
    public static final MediaCodec createCodec(EncoderConfig encoderConfig) {
        return INSTANCE.createCodec(encoderConfig.getMimeType());
    }

    @JvmStatic
    public static final MediaCodecInfo findCodecAndGetCodecInfo(String mimeType) {
        MediaCodecInfo mediaCodecInfo;
        LruCache<String, MediaCodecInfo> lruCache = codecInfoCache;
        synchronized (lruCache) {
            mediaCodecInfo = lruCache.get(mimeType);
            Unit unit = Unit.INSTANCE;
        }
        if (mediaCodecInfo != null) {
            return mediaCodecInfo;
        }
        MediaCodec mediaCodecCreateCodec = null;
        try {
            mediaCodecCreateCodec = INSTANCE.createCodec(mimeType);
            MediaCodecInfo codecInfo = mediaCodecCreateCodec.getCodecInfo();
            synchronized (lruCache) {
                lruCache.put(mimeType, codecInfo);
            }
            mediaCodecCreateCodec.release();
            return codecInfo;
        } catch (Throwable th) {
            if (mediaCodecCreateCodec != null) {
                mediaCodecCreateCodec.release();
            }
            throw th;
        }
    }

    private final MediaCodec createCodec(String mimeType) throws InvalidConfigException {
        try {
            return MediaCodec.createEncoderByType(mimeType);
        } catch (IOException e) {
            throw new InvalidConfigException(e);
        } catch (IllegalArgumentException e2) {
            throw new InvalidConfigException(e2);
        }
    }
}
