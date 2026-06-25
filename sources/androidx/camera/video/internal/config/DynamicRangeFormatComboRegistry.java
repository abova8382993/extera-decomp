package androidx.camera.video.internal.config;

import android.os.Build;
import androidx.camera.core.DynamicRange;
import androidx.camera.video.internal.config.FormatComboRegistry;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.SourceDebugExtension;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.MediaController;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010$\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\b\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000f\u0010\u0005\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\u0005\u0010\u0006J\u000f\u0010\u0007\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\u0007\u0010\u0006J\u000f\u0010\b\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\b\u0010\u0006J\u000f\u0010\t\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\t\u0010\u0006J\u000f\u0010\n\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\n\u0010\u0006J\u001d\u0010\u000e\u001a\u0004\u0018\u00010\u000b*\u00020\u000b2\u0006\u0010\r\u001a\u00020\fH\u0002¢\u0006\u0004\b\u000e\u0010\u000fJ\u001b\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0010\u001a\u00020\u000b¢\u0006\u0004\b\u0013\u0010\u0014R\u0016\u0010\u0015\u001a\u0004\u0018\u00010\u000b8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0015\u0010\u0016R\u0016\u0010\u0017\u001a\u0004\u0018\u00010\u000b8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0017\u0010\u0016R\u0016\u0010\u0018\u001a\u0004\u0018\u00010\u000b8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0018\u0010\u0016R\u0016\u0010\u0019\u001a\u0004\u0018\u00010\u000b8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0019\u0010\u0016R\u0016\u0010\u001a\u001a\u0004\u0018\u00010\u000b8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u001a\u0010\u0016R\u0016\u0010\u001b\u001a\u0004\u0018\u00010\u000b8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u001b\u0010\u0016R'\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00040\u001c8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R!\u0010&\u001a\b\u0012\u0004\u0012\u00020\u000b0\"8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b#\u0010\u001e\u001a\u0004\b$\u0010%R!\u0010)\u001a\b\u0012\u0004\u0012\u00020\u000b0\"8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b'\u0010\u001e\u001a\u0004\b(\u0010%¨\u0006*"}, m877d2 = {"Landroidx/camera/video/internal/config/DynamicRangeFormatComboRegistry;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "Landroidx/camera/video/internal/config/FormatComboRegistry;", "buildSdrRegistry", "()Landroidx/camera/video/internal/config/FormatComboRegistry;", "buildHlgRegistry", "buildHdr10Registry", "buildHdr10PlusRegistry", "buildDolbyVisionRegistry", _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, "minSdk", "takeIf", "(Ljava/lang/String;I)Ljava/lang/String;", "videoMime", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/core/DynamicRange;", "getDynamicRangesForVideoMime", "(Ljava/lang/String;)Ljava/util/Set;", "MIMETYPE_VIDEO_HEVC_GATED", "Ljava/lang/String;", "MIMETYPE_VIDEO_VP9_GATED", "MIMETYPE_AUDIO_OPUS_GATED", "MIMETYPE_VIDEO_DOLBY_VISION_GATED", "MIMETYPE_VIDEO_AV1_GATED", "MIMETYPE_VIDEO_APV_GATED", _UrlKt.FRAGMENT_ENCODE_SET, "registries$delegate", "Lkotlin/Lazy;", "getRegistries", "()Ljava/util/Map;", "registries", _UrlKt.FRAGMENT_ENCODE_SET, "standardMp4Audios$delegate", "getStandardMp4Audios", "()Ljava/util/List;", "standardMp4Audios", "standardWebmAudios$delegate", "getStandardWebmAudios", "standardWebmAudios", "camera-video"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nDynamicRangeFormatComboRegistry.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DynamicRangeFormatComboRegistry.kt\nandroidx/camera/video/internal/config/DynamicRangeFormatComboRegistry\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,228:1\n1#2:229\n*E\n"})
public final class DynamicRangeFormatComboRegistry {
    public static final DynamicRangeFormatComboRegistry INSTANCE;
    private static final String MIMETYPE_AUDIO_OPUS_GATED;
    private static final String MIMETYPE_VIDEO_APV_GATED;
    private static final String MIMETYPE_VIDEO_AV1_GATED;
    private static final String MIMETYPE_VIDEO_DOLBY_VISION_GATED;
    private static final String MIMETYPE_VIDEO_HEVC_GATED;
    private static final String MIMETYPE_VIDEO_VP9_GATED;

    /* JADX INFO: renamed from: registries$delegate, reason: from kotlin metadata */
    private static final Lazy registries;

    /* JADX INFO: renamed from: standardMp4Audios$delegate, reason: from kotlin metadata */
    private static final Lazy standardMp4Audios;

    /* JADX INFO: renamed from: standardWebmAudios$delegate, reason: from kotlin metadata */
    private static final Lazy standardWebmAudios;

    private DynamicRangeFormatComboRegistry() {
    }

    static {
        DynamicRangeFormatComboRegistry dynamicRangeFormatComboRegistry = new DynamicRangeFormatComboRegistry();
        INSTANCE = dynamicRangeFormatComboRegistry;
        MIMETYPE_VIDEO_HEVC_GATED = dynamicRangeFormatComboRegistry.takeIf("video/hevc", 24);
        MIMETYPE_VIDEO_VP9_GATED = dynamicRangeFormatComboRegistry.takeIf("video/x-vnd.on2.vp9", 24);
        MIMETYPE_AUDIO_OPUS_GATED = dynamicRangeFormatComboRegistry.takeIf("audio/opus", 29);
        MIMETYPE_VIDEO_DOLBY_VISION_GATED = dynamicRangeFormatComboRegistry.takeIf("video/dolby-vision", 33);
        MIMETYPE_VIDEO_AV1_GATED = dynamicRangeFormatComboRegistry.takeIf("video/av01", 34);
        MIMETYPE_VIDEO_APV_GATED = dynamicRangeFormatComboRegistry.takeIf("video/apv", 36);
        registries = LazyKt.lazy(new Function0() { // from class: androidx.camera.video.internal.config.DynamicRangeFormatComboRegistry$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return DynamicRangeFormatComboRegistry.m1908$r8$lambda$gOliYEZvgXRStXGSsEm7KAwCmk();
            }
        });
        standardMp4Audios = LazyKt.lazy(new Function0() { // from class: androidx.camera.video.internal.config.DynamicRangeFormatComboRegistry$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CollectionsKt.listOfNotNull((Object[]) new String[]{MediaController.AUDIO_MIME_TYPE, "audio/3gpp", "audio/amr-wb"});
            }
        });
        standardWebmAudios = LazyKt.lazy(new Function0() { // from class: androidx.camera.video.internal.config.DynamicRangeFormatComboRegistry$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CollectionsKt.listOfNotNull((Object[]) new String[]{"audio/vorbis", DynamicRangeFormatComboRegistry.MIMETYPE_AUDIO_OPUS_GATED});
            }
        });
    }

    private final Map<DynamicRange, FormatComboRegistry> getRegistries() {
        return (Map) registries.getValue();
    }

    /* JADX INFO: renamed from: $r8$lambda$gO-liYEZvgXRStXGSsEm7KAwCmk, reason: not valid java name */
    public static Map m1908$r8$lambda$gOliYEZvgXRStXGSsEm7KAwCmk() {
        DynamicRange dynamicRange = DynamicRange.SDR;
        DynamicRangeFormatComboRegistry dynamicRangeFormatComboRegistry = INSTANCE;
        return MapsKt.mutableMapOf(TuplesKt.m884to(dynamicRange, dynamicRangeFormatComboRegistry.buildSdrRegistry()), TuplesKt.m884to(DynamicRange.HLG_10_BIT, dynamicRangeFormatComboRegistry.buildHlgRegistry()), TuplesKt.m884to(DynamicRange.HDR10_10_BIT, dynamicRangeFormatComboRegistry.buildHdr10Registry()), TuplesKt.m884to(DynamicRange.HDR10_PLUS_10_BIT, dynamicRangeFormatComboRegistry.buildHdr10PlusRegistry()), TuplesKt.m884to(DynamicRange.DOLBY_VISION_8_BIT, dynamicRangeFormatComboRegistry.buildDolbyVisionRegistry()), TuplesKt.m884to(DynamicRange.DOLBY_VISION_10_BIT, dynamicRangeFormatComboRegistry.buildDolbyVisionRegistry()));
    }

    private final List<String> getStandardMp4Audios() {
        return (List) standardMp4Audios.getValue();
    }

    private final List<String> getStandardWebmAudios() {
        return (List) standardWebmAudios.getValue();
    }

    public final Set<DynamicRange> getDynamicRangesForVideoMime(String videoMime) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (Map.Entry<DynamicRange, FormatComboRegistry> entry : getRegistries().entrySet()) {
            DynamicRange key = entry.getKey();
            if (!entry.getValue().getCombosForVideo(videoMime).isEmpty()) {
                linkedHashSet.add(key);
            }
        }
        return linkedHashSet;
    }

    private final FormatComboRegistry buildSdrRegistry() {
        FormatComboRegistry.Builder builder = new FormatComboRegistry.Builder();
        builder.container(0, new Function1() { // from class: androidx.camera.video.internal.config.DynamicRangeFormatComboRegistry$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return DynamicRangeFormatComboRegistry.buildSdrRegistry$lambda$0$0((FormatComboRegistry.Builder.ContainerScope) obj);
            }
        });
        builder.container(1, new Function1() { // from class: androidx.camera.video.internal.config.DynamicRangeFormatComboRegistry$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return DynamicRangeFormatComboRegistry.buildSdrRegistry$lambda$0$1((FormatComboRegistry.Builder.ContainerScope) obj);
            }
        });
        return builder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildSdrRegistry$lambda$0$0(FormatComboRegistry.Builder.ContainerScope containerScope) {
        containerScope.support(CollectionsKt.listOfNotNull((Object[]) new String[]{MediaController.VIDEO_MIME_TYPE, "video/mp4v-es", "video/3gpp", MIMETYPE_VIDEO_HEVC_GATED, MIMETYPE_VIDEO_DOLBY_VISION_GATED, MIMETYPE_VIDEO_AV1_GATED, MIMETYPE_VIDEO_APV_GATED}), INSTANCE.getStandardMp4Audios());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildSdrRegistry$lambda$0$1(FormatComboRegistry.Builder.ContainerScope containerScope) {
        containerScope.support(CollectionsKt.listOfNotNull((Object[]) new String[]{"video/x-vnd.on2.vp8", MIMETYPE_VIDEO_VP9_GATED}), INSTANCE.getStandardWebmAudios());
        return Unit.INSTANCE;
    }

    private final FormatComboRegistry buildHlgRegistry() {
        FormatComboRegistry.Builder builder = new FormatComboRegistry.Builder();
        builder.container(0, new Function1() { // from class: androidx.camera.video.internal.config.DynamicRangeFormatComboRegistry$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return DynamicRangeFormatComboRegistry.buildHlgRegistry$lambda$0$0((FormatComboRegistry.Builder.ContainerScope) obj);
            }
        });
        return builder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildHlgRegistry$lambda$0$0(FormatComboRegistry.Builder.ContainerScope containerScope) {
        containerScope.support(CollectionsKt.listOfNotNull((Object[]) new String[]{MIMETYPE_VIDEO_HEVC_GATED, MIMETYPE_VIDEO_AV1_GATED, MIMETYPE_VIDEO_APV_GATED}), INSTANCE.getStandardMp4Audios());
        return Unit.INSTANCE;
    }

    private final FormatComboRegistry buildHdr10Registry() {
        FormatComboRegistry.Builder builder = new FormatComboRegistry.Builder();
        builder.container(0, new Function1() { // from class: androidx.camera.video.internal.config.DynamicRangeFormatComboRegistry$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return DynamicRangeFormatComboRegistry.buildHdr10Registry$lambda$0$0((FormatComboRegistry.Builder.ContainerScope) obj);
            }
        });
        builder.container(1, new Function1() { // from class: androidx.camera.video.internal.config.DynamicRangeFormatComboRegistry$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return DynamicRangeFormatComboRegistry.buildHdr10Registry$lambda$0$1((FormatComboRegistry.Builder.ContainerScope) obj);
            }
        });
        return builder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildHdr10Registry$lambda$0$0(FormatComboRegistry.Builder.ContainerScope containerScope) {
        containerScope.support(CollectionsKt.listOfNotNull((Object[]) new String[]{MIMETYPE_VIDEO_HEVC_GATED, MIMETYPE_VIDEO_AV1_GATED, MIMETYPE_VIDEO_APV_GATED}), INSTANCE.getStandardMp4Audios());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildHdr10Registry$lambda$0$1(FormatComboRegistry.Builder.ContainerScope containerScope) {
        containerScope.support(CollectionsKt.listOfNotNull(MIMETYPE_VIDEO_VP9_GATED), INSTANCE.getStandardWebmAudios());
        return Unit.INSTANCE;
    }

    private final FormatComboRegistry buildHdr10PlusRegistry() {
        FormatComboRegistry.Builder builder = new FormatComboRegistry.Builder();
        builder.container(0, new Function1() { // from class: androidx.camera.video.internal.config.DynamicRangeFormatComboRegistry$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return DynamicRangeFormatComboRegistry.buildHdr10PlusRegistry$lambda$0$0((FormatComboRegistry.Builder.ContainerScope) obj);
            }
        });
        return builder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildHdr10PlusRegistry$lambda$0$0(FormatComboRegistry.Builder.ContainerScope containerScope) {
        containerScope.support(CollectionsKt.listOfNotNull((Object[]) new String[]{MIMETYPE_VIDEO_HEVC_GATED, MIMETYPE_VIDEO_AV1_GATED}), INSTANCE.getStandardMp4Audios());
        return Unit.INSTANCE;
    }

    private final FormatComboRegistry buildDolbyVisionRegistry() {
        FormatComboRegistry.Builder builder = new FormatComboRegistry.Builder();
        builder.container(0, new Function1() { // from class: androidx.camera.video.internal.config.DynamicRangeFormatComboRegistry$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return DynamicRangeFormatComboRegistry.buildDolbyVisionRegistry$lambda$0$0((FormatComboRegistry.Builder.ContainerScope) obj);
            }
        });
        return builder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDolbyVisionRegistry$lambda$0$0(FormatComboRegistry.Builder.ContainerScope containerScope) {
        containerScope.support(CollectionsKt.listOfNotNull(MIMETYPE_VIDEO_DOLBY_VISION_GATED), INSTANCE.getStandardMp4Audios());
        return Unit.INSTANCE;
    }

    private final String takeIf(String str, int i) {
        if (Build.VERSION.SDK_INT >= i) {
            return str;
        }
        return null;
    }
}
