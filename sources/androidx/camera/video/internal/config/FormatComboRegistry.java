package androidx.camera.video.internal.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SourceDebugExtension;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0010\u000e\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0001\u000fB1\b\u0002\u0012&\u0010\u0007\u001a\"\u0012\u0004\u0012\u00020\u0003\u0012\u0018\u0012\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00020\u0002¢\u0006\u0004\b\b\u0010\tJ\u001d\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\u000b2\b\u0010\n\u001a\u0004\u0018\u00010\u0004¢\u0006\u0004\b\f\u0010\rR4\u0010\u0007\u001a\"\u0012\u0004\u0012\u00020\u0003\u0012\u0018\u0012\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0007\u0010\u000e¨\u0006\u0010"}, m877d2 = {"Landroidx/camera/video/internal/config/FormatComboRegistry;", _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/video/internal/config/FormatCombo;", "formatComboMapping", "<init>", "(Ljava/util/Map;)V", "videoMime", _UrlKt.FRAGMENT_ENCODE_SET, "getCombosForVideo", "(Ljava/lang/String;)Ljava/util/List;", "Ljava/util/Map;", "Builder", "camera-video"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nFormatComboRegistry.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FormatComboRegistry.kt\nandroidx/camera/video/internal/config/FormatComboRegistry\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,181:1\n774#2:182\n865#2,2:183\n774#2:186\n865#2,2:187\n1#3:185\n*S KotlinDebug\n*F\n+ 1 FormatComboRegistry.kt\nandroidx/camera/video/internal/config/FormatComboRegistry\n*L\n86#1:182\n86#1:183,2\n117#1:186\n117#1:187,2\n*E\n"})
public final class FormatComboRegistry {
    private final Map<Integer, Map<String, Set<FormatCombo>>> formatComboMapping;

    public /* synthetic */ FormatComboRegistry(Map map, DefaultConstructorMarker defaultConstructorMarker) {
        this(map);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private FormatComboRegistry(Map<Integer, ? extends Map<String, ? extends Set<FormatCombo>>> map) {
        this.formatComboMapping = map;
    }

    public final List<FormatCombo> getCombosForVideo(String videoMime) {
        ArrayList arrayList = new ArrayList();
        Iterator<Map<String, Set<FormatCombo>>> it = this.formatComboMapping.values().iterator();
        while (it.hasNext()) {
            Set<FormatCombo> set = it.next().get(videoMime);
            if (set != null) {
                arrayList.addAll(set);
            }
        }
        return arrayList;
    }

    @Metadata(m876d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0010\b\n\u0002\u0010\u000e\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0013B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J'\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00062\u0017\u0010\r\u001a\u0013\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000b0\u000e¢\u0006\u0002\b\u0010J\u0006\u0010\u0011\u001a\u00020\u0012R.\u0010\u0004\u001a\"\u0012\u0004\u0012\u00020\u0006\u0012\u0018\u0012\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u00050\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, m877d2 = {"Landroidx/camera/video/internal/config/FormatComboRegistry$Builder;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "formatComboMapping", _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/video/internal/config/FormatCombo;", "container", _UrlKt.FRAGMENT_ENCODE_SET, "format", "block", "Lkotlin/Function1;", "Landroidx/camera/video/internal/config/FormatComboRegistry$Builder$ContainerScope;", "Lkotlin/ExtensionFunctionType;", "build", "Landroidx/camera/video/internal/config/FormatComboRegistry;", "ContainerScope", "camera-video"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    @SourceDebugExtension({"SMAP\nFormatComboRegistry.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FormatComboRegistry.kt\nandroidx/camera/video/internal/config/FormatComboRegistry$Builder\n+ 2 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,181:1\n384#2,7:182\n*S KotlinDebug\n*F\n+ 1 FormatComboRegistry.kt\nandroidx/camera/video/internal/config/FormatComboRegistry$Builder\n*L\n138#1:182,7\n*E\n"})
    public static final class Builder {
        private final Map<Integer, Map<String, Set<FormatCombo>>> formatComboMapping = new LinkedHashMap();

        public final void container(int format, Function1<? super ContainerScope, Unit> block) {
            Map<Integer, Map<String, Set<FormatCombo>>> map = this.formatComboMapping;
            Integer numValueOf = Integer.valueOf(format);
            Map<String, Set<FormatCombo>> linkedHashMap = map.get(numValueOf);
            if (linkedHashMap == null) {
                linkedHashMap = new LinkedHashMap<>();
                map.put(numValueOf, linkedHashMap);
            }
            block.invoke(new ContainerScope(format, linkedHashMap));
        }

        @Metadata(m876d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u001a\u0010\u0004\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0005¢\u0006\u0004\b\t\u0010\nJ\"\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010\u0004\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, m877d2 = {"Landroidx/camera/video/internal/config/FormatComboRegistry$Builder$ContainerScope;", _UrlKt.FRAGMENT_ENCODE_SET, "container", _UrlKt.FRAGMENT_ENCODE_SET, "videoMap", _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/video/internal/config/FormatCombo;", "<init>", "(ILjava/util/Map;)V", "support", _UrlKt.FRAGMENT_ENCODE_SET, "videoMimes", _UrlKt.FRAGMENT_ENCODE_SET, "audioMimes", "camera-video"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
        @SourceDebugExtension({"SMAP\nFormatComboRegistry.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FormatComboRegistry.kt\nandroidx/camera/video/internal/config/FormatComboRegistry$Builder$ContainerScope\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,181:1\n1869#2:182\n1869#2,2:190\n1870#2:192\n1869#2,2:200\n384#3,7:183\n384#3,7:193\n*S KotlinDebug\n*F\n+ 1 FormatComboRegistry.kt\nandroidx/camera/video/internal/config/FormatComboRegistry$Builder$ContainerScope\n*L\n158#1:182\n161#1:190,2\n158#1:192\n171#1:200,2\n160#1:183,7\n170#1:193,7\n*E\n"})
        public static final class ContainerScope {
            private final int container;
            private final Map<String, Set<FormatCombo>> videoMap;

            public ContainerScope(int i, Map<String, Set<FormatCombo>> map) {
                this.container = i;
                this.videoMap = map;
            }

            public final void support(List<String> videoMimes, List<String> audioMimes) {
                for (String str : videoMimes) {
                    Map<String, Set<FormatCombo>> map = this.videoMap;
                    Set<FormatCombo> linkedHashSet = map.get(str);
                    if (linkedHashSet == null) {
                        linkedHashSet = new LinkedHashSet<>();
                        map.put(str, linkedHashSet);
                    }
                    Set<FormatCombo> set = linkedHashSet;
                    Iterator<T> it = audioMimes.iterator();
                    while (it.hasNext()) {
                        set.add(new FormatCombo(this.container, str, (String) it.next()));
                    }
                    set.add(new FormatCombo(this.container, str, null));
                }
                Map<String, Set<FormatCombo>> map2 = this.videoMap;
                Set<FormatCombo> linkedHashSet2 = map2.get(null);
                if (linkedHashSet2 == null) {
                    linkedHashSet2 = new LinkedHashSet<>();
                    map2.put(null, linkedHashSet2);
                }
                Set<FormatCombo> set2 = linkedHashSet2;
                Iterator<T> it2 = audioMimes.iterator();
                while (it2.hasNext()) {
                    set2.add(new FormatCombo(this.container, null, (String) it2.next()));
                }
            }
        }

        public final FormatComboRegistry build() {
            return new FormatComboRegistry(this.formatComboMapping, null);
        }
    }
}
