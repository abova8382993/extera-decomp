package androidx.camera.video;

import androidx.camera.video.VideoSpec;
import androidx.core.util.Consumer;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.MediaController;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\r\b\u0007\u0018\u0000 \u001e2\u00020\u0001:\u0002\u001f\u001eB'\b\u0007\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\b\u0010\tJ\r\u0010\u000b\u001a\u00020\n¢\u0006\u0004\b\u000b\u0010\fJ\u001a\u0010\u000f\u001a\u00020\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\u0001H\u0096\u0002¢\u0006\u0004\b\u000f\u0010\u0010J\u000f\u0010\u0011\u001a\u00020\u0006H\u0016¢\u0006\u0004\b\u0011\u0010\u0012J\u000f\u0010\u0014\u001a\u00020\u0013H\u0016¢\u0006\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n\u0004\b\u0003\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R\u0017\u0010\u0005\u001a\u00020\u00048\u0006¢\u0006\f\n\u0004\b\u0005\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\u0017\u0010\u0007\u001a\u00020\u00068\u0006¢\u0006\f\n\u0004\b\u0007\u0010\u001c\u001a\u0004\b\u001d\u0010\u0012¨\u0006 "}, m877d2 = {"Landroidx/camera/video/MediaSpec;", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/video/VideoSpec;", "videoSpec", "Landroidx/camera/video/AudioSpec;", "audioSpec", _UrlKt.FRAGMENT_ENCODE_SET, "outputFormat", "<init>", "(Landroidx/camera/video/VideoSpec;Landroidx/camera/video/AudioSpec;I)V", "Landroidx/camera/video/MediaSpec$Builder;", "toBuilder", "()Landroidx/camera/video/MediaSpec$Builder;", "other", _UrlKt.FRAGMENT_ENCODE_SET, "equals", "(Ljava/lang/Object;)Z", "hashCode", "()I", _UrlKt.FRAGMENT_ENCODE_SET, "toString", "()Ljava/lang/String;", "Landroidx/camera/video/VideoSpec;", "getVideoSpec", "()Landroidx/camera/video/VideoSpec;", "Landroidx/camera/video/AudioSpec;", "getAudioSpec", "()Landroidx/camera/video/AudioSpec;", "I", "getOutputFormat", "Companion", "Builder", "camera-video"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
public final class MediaSpec {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final AudioSpec audioSpec;
    private final int outputFormat;
    private final VideoSpec videoSpec;

    @JvmStatic
    public static final Builder builder() {
        return INSTANCE.builder();
    }

    @JvmStatic
    @JvmName(name = "outputFormatToMuxerFormat")
    public static final int outputFormatToMuxerFormat(int i) {
        return INSTANCE.outputFormatToMuxerFormat(i);
    }

    @JvmOverloads
    public MediaSpec(VideoSpec videoSpec, AudioSpec audioSpec, int i) {
        this.videoSpec = videoSpec;
        this.audioSpec = audioSpec;
        this.outputFormat = i;
    }

    public final VideoSpec getVideoSpec() {
        return this.videoSpec;
    }

    public final int getOutputFormat() {
        return this.outputFormat;
    }

    public final Builder toBuilder() {
        return new Builder().setVideoSpec(this.videoSpec).setAudioSpec(this.audioSpec).setOutputFormat(this.outputFormat);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MediaSpec)) {
            return false;
        }
        MediaSpec mediaSpec = (MediaSpec) other;
        return Intrinsics.areEqual(this.videoSpec, mediaSpec.videoSpec) && Intrinsics.areEqual(this.audioSpec, mediaSpec.audioSpec) && this.outputFormat == mediaSpec.outputFormat;
    }

    public int hashCode() {
        return Objects.hash(this.videoSpec, this.audioSpec, Integer.valueOf(this.outputFormat));
    }

    public String toString() {
        return "MediaSpec{videoSpec=" + this.videoSpec + ", audioSpec=" + this.audioSpec + ", outputFormat=" + this.outputFormat + '}';
    }

    @Metadata(m876d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0015\u0010\u0006\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0006\u0010\u0007J\u0015\u0010\n\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\b¢\u0006\u0004\b\n\u0010\u000bJ\u0015\u0010\u000e\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\f¢\u0006\u0004\b\u000e\u0010\u000fJ\u001d\u0010\u0013\u001a\u00020\u00002\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0007¢\u0006\u0004\b\u0013\u0010\u0014J\r\u0010\u0016\u001a\u00020\u0015¢\u0006\u0004\b\u0016\u0010\u0017R\u0016\u0010\u0005\u001a\u00020\u00048\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u0005\u0010\u0018R\u0016\u0010\t\u001a\u00020\b8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\t\u0010\u0019R\u001c\u0010\u001a\u001a\u00020\f8\u0002@\u0002X\u0082\u000e¢\u0006\f\n\u0004\b\u001a\u0010\u001b\u0012\u0004\b\u001c\u0010\u0003¨\u0006\u001d"}, m877d2 = {"Landroidx/camera/video/MediaSpec$Builder;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "Landroidx/camera/video/AudioSpec;", "audioSpec", "setAudioSpec", "(Landroidx/camera/video/AudioSpec;)Landroidx/camera/video/MediaSpec$Builder;", "Landroidx/camera/video/VideoSpec;", "videoSpec", "setVideoSpec", "(Landroidx/camera/video/VideoSpec;)Landroidx/camera/video/MediaSpec$Builder;", _UrlKt.FRAGMENT_ENCODE_SET, "format", "setOutputFormat", "(I)Landroidx/camera/video/MediaSpec$Builder;", "Landroidx/core/util/Consumer;", "Landroidx/camera/video/VideoSpec$Builder;", "configBlock", "configureVideo", "(Landroidx/core/util/Consumer;)Landroidx/camera/video/MediaSpec$Builder;", "Landroidx/camera/video/MediaSpec;", "build", "()Landroidx/camera/video/MediaSpec;", "Landroidx/camera/video/AudioSpec;", "Landroidx/camera/video/VideoSpec;", "outputFormat", "I", "getOutputFormat$annotations", "camera-video"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    @SourceDebugExtension({"SMAP\nMediaSpec.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MediaSpec.kt\nandroidx/camera/video/MediaSpec$Builder\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,170:1\n1#2:171\n*E\n"})
    public static final class Builder {
        private AudioSpec audioSpec = AudioSpec.INSTANCE.getDEFAULT();
        private VideoSpec videoSpec = VideoSpec.INSTANCE.getDEFAULT();
        private int outputFormat = -1;

        public final Builder setAudioSpec(AudioSpec audioSpec) {
            this.audioSpec = audioSpec;
            return this;
        }

        public final Builder setVideoSpec(VideoSpec videoSpec) {
            this.videoSpec = videoSpec;
            return this;
        }

        public final Builder setOutputFormat(int format) {
            this.outputFormat = format;
            return this;
        }

        public final Builder configureVideo(Consumer<VideoSpec.Builder> configBlock) {
            VideoSpec.Builder builder = this.videoSpec.toBuilder();
            configBlock.accept(builder);
            this.videoSpec = builder.build();
            return this;
        }

        public final MediaSpec build() {
            return new MediaSpec(this.videoSpec, this.audioSpec, this.outputFormat);
        }
    }

    @Metadata(m876d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0017\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0001¢\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0001¢\u0006\u0004\b\t\u0010\nJ\u000f\u0010\f\u001a\u00020\u000bH\u0007¢\u0006\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u00068\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u00068\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u0010\u0010\u000fR\u0014\u0010\u0011\u001a\u00020\u00068\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u0011\u0010\u000fR\u0014\u0010\u0012\u001a\u00020\u00068\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u0012\u0010\u000fR\u0014\u0010\u0013\u001a\u00020\u00048\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00048\u0006X\u0086T¢\u0006\u0006\n\u0004\b\u0015\u0010\u0014R\u0014\u0010\u0016\u001a\u00020\u00048\u0006X\u0086T¢\u0006\u0006\n\u0004\b\u0016\u0010\u0014R\u0014\u0010\u0017\u001a\u00020\u00048\u0006X\u0086T¢\u0006\u0006\n\u0004\b\u0017\u0010\u0014¨\u0006\u0018"}, m877d2 = {"Landroidx/camera/video/MediaSpec$Companion;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", _UrlKt.FRAGMENT_ENCODE_SET, "outputFormat", _UrlKt.FRAGMENT_ENCODE_SET, "outputFormatToVideoMime", "(I)Ljava/lang/String;", "outputFormatToMuxerFormat", "(I)I", "Landroidx/camera/video/MediaSpec$Builder;", "builder", "()Landroidx/camera/video/MediaSpec$Builder;", "AUDIO_ENCODER_MIME_MPEG4_DEFAULT", "Ljava/lang/String;", "AUDIO_ENCODER_MIME_WEBM_DEFAULT", "VIDEO_ENCODER_MIME_MPEG4_DEFAULT", "VIDEO_ENCODER_MIME_WEBM_DEFAULT", "AAC_DEFAULT_PROFILE", "I", "OUTPUT_FORMAT_UNSPECIFIED", "OUTPUT_FORMAT_MPEG_4", "OUTPUT_FORMAT_WEBM", "camera-video"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        @JvmName(name = "outputFormatToMuxerFormat")
        public final int outputFormatToMuxerFormat(int outputFormat) {
            return outputFormat == 1 ? 1 : 0;
        }

        private Companion() {
        }

        @JvmStatic
        @JvmName(name = "outputFormatToVideoMime")
        public final String outputFormatToVideoMime(int outputFormat) {
            if (outputFormat == 1) {
                return "video/x-vnd.on2.vp8";
            }
            return MediaController.VIDEO_MIME_TYPE;
        }

        @JvmStatic
        public final Builder builder() {
            return new Builder();
        }
    }
}
