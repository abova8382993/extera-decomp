package androidx.camera.camera2.pipe.graph;

import android.hardware.camera2.params.OutputConfiguration;
import android.os.Build;
import android.util.Size;
import androidx.appcompat.app.WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.camera.camera2.config.UseCaseGraphContext$$ExternalSyntheticAutoCloseableDispatcher0;
import androidx.camera.camera2.pipe.CameraGraph;
import androidx.camera.camera2.pipe.CameraMetadata;
import androidx.camera.camera2.pipe.CameraStream;
import androidx.camera.camera2.pipe.InputStream;
import androidx.camera.camera2.pipe.InputStreamId;
import androidx.camera.camera2.pipe.OutputId;
import androidx.camera.camera2.pipe.OutputStream;
import androidx.camera.camera2.pipe.StreamFormat;
import androidx.camera.camera2.pipe.StreamGraph;
import androidx.camera.camera2.pipe.StreamId;
import androidx.camera.camera2.pipe.media.ImageSource;
import androidx.camera.camera2.pipe.media.ImageSources;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.inject.Provider;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicInt;

/* JADX INFO: loaded from: classes3.dex */
public final class StreamGraphImpl implements StreamGraph, AutoCloseable {
    private static final Comparator previewFormatComparator;
    private static final List previewFormats;
    private static final List previewOutputTypes;
    private static final Comparator previewOutputTypesComparator;
    private final Map _streamMap;
    private final Provider cameraControllerProvider;
    private final CameraMetadata cameraMetadata;
    private final CameraGraph.Config graphConfig;
    private final Map imageSourceMap;
    private final ImageSources imageSources;
    private final List inputs;
    private final Map outputConfigMap;
    private final List outputConfigs;
    private final List outputs;
    private final Set streamIds$1;
    private final List streams;
    public static final Companion Companion = new Companion(null);
    private static final AtomicInt streamIds = AtomicFU.atomic(0);
    private static final AtomicInt outputIds = AtomicFU.atomic(0);
    private static final AtomicInt inputIds = AtomicFU.atomic(0);
    private static final AtomicInt configIds = AtomicFU.atomic(0);
    private static final AtomicInt groupIds = AtomicFU.atomic(0);

    private final OutputConfiguration getOutputConfigurationOrNull(OutputStream.Config config) {
        return null;
    }

    @Override // androidx.camera.camera2.pipe.StreamGraph
    /* JADX INFO: renamed from: get-aKI5c8E */
    public /* synthetic */ CameraStream mo1782getaKI5c8E(int i) {
        return StreamGraph.CC.m1784$default$getaKI5c8E(this, i);
    }

    @Override // androidx.camera.camera2.pipe.StreamGraph
    /* JADX INFO: renamed from: get-iYJqvbA */
    public /* synthetic */ OutputStream mo1783getiYJqvbA(int i) {
        return StreamGraph.CC.m1785$default$getiYJqvbA(this, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:130:0x00ff  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public StreamGraphImpl(androidx.camera.camera2.pipe.CameraMetadata r29, androidx.camera.camera2.pipe.CameraGraph.Config r30, androidx.camera.camera2.pipe.media.ImageSources r31, javax.inject.Provider r32) {
        /*
            Method dump skipped, instruction units count: 723
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.graph.StreamGraphImpl.<init>(androidx.camera.camera2.pipe.CameraMetadata, androidx.camera.camera2.pipe.CameraGraph$Config, androidx.camera.camera2.pipe.media.ImageSources, javax.inject.Provider):void");
    }

    public final List getOutputConfigs$camera_camera2_pipe() {
        return this.outputConfigs;
    }

    public final Map getOutputConfigMap$camera_camera2_pipe() {
        return this.outputConfigMap;
    }

    public final Map getImageSourceMap$camera_camera2_pipe() {
        return this.imageSourceMap;
    }

    @Override // androidx.camera.camera2.pipe.StreamGraph
    public List getInputs() {
        return this.inputs;
    }

    @Override // androidx.camera.camera2.pipe.StreamGraph
    public List getStreams() {
        return this.streams;
    }

    @Override // androidx.camera.camera2.pipe.StreamGraph
    public List getOutputs() {
        return this.outputs;
    }

    @Override // androidx.camera.camera2.pipe.StreamGraph
    public CameraStream get(CameraStream.Config config) {
        Intrinsics.checkNotNullParameter(config, "config");
        return (CameraStream) this._streamMap.get(config);
    }

    /* JADX INFO: renamed from: getCameraStreamConfig-aKI5c8E */
    public final CameraStream.Config m1921getCameraStreamConfigaKI5c8E(int i) {
        Object next;
        Iterator it = this._streamMap.entrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (StreamId.m1789equalsimpl0(((CameraStream) ((Map.Entry) next).getValue()).m1616getIdptHMqGs(), i)) {
                break;
            }
        }
        Map.Entry entry = (Map.Entry) next;
        if (entry != null) {
            return (CameraStream.Config) entry.getKey();
        }
        return null;
    }

    public static final class OutputConfig {
        private final String camera;
        private final OutputStream.OutputType deferredOutputType;
        private final OutputStream.DynamicRangeProfile dynamicRangeProfile;
        private final OutputConfiguration externalOutputConfig;
        private final int format;
        private final Integer groupNumber;

        /* JADX INFO: renamed from: id */
        private final int f19id;
        private final OutputStream.MirrorMode mirrorMode;
        private final List sensorPixelModes;
        private final Size size;
        private final List streamBuilder;
        private final OutputStream.StreamUseCase streamUseCase;
        private final OutputStream.StreamUseHint streamUseHint;

        public /* synthetic */ OutputConfig(int i, Size size, int i2, String str, Integer num, OutputConfiguration outputConfiguration, OutputStream.OutputType outputType, OutputStream.MirrorMode mirrorMode, OutputStream.TimestampBase timestampBase, OutputStream.DynamicRangeProfile dynamicRangeProfile, OutputStream.StreamUseCase streamUseCase, OutputStream.StreamUseHint streamUseHint, List list, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, size, i2, str, num, outputConfiguration, outputType, mirrorMode, timestampBase, dynamicRangeProfile, streamUseCase, streamUseHint, list);
        }

        /* JADX INFO: renamed from: getTimestampBase-pcPfPbY */
        public final OutputStream.TimestampBase m1932getTimestampBasepcPfPbY() {
            return null;
        }

        private OutputConfig(int i, Size size, int i2, String camera, Integer num, OutputConfiguration outputConfiguration, OutputStream.OutputType outputType, OutputStream.MirrorMode mirrorMode, OutputStream.TimestampBase timestampBase, OutputStream.DynamicRangeProfile dynamicRangeProfile, OutputStream.StreamUseCase streamUseCase, OutputStream.StreamUseHint streamUseHint, List sensorPixelModes) {
            Intrinsics.checkNotNullParameter(size, "size");
            Intrinsics.checkNotNullParameter(camera, "camera");
            Intrinsics.checkNotNullParameter(sensorPixelModes, "sensorPixelModes");
            this.f19id = i;
            this.size = size;
            this.format = i2;
            this.camera = camera;
            this.groupNumber = num;
            this.externalOutputConfig = outputConfiguration;
            this.deferredOutputType = outputType;
            this.mirrorMode = mirrorMode;
            this.dynamicRangeProfile = dynamicRangeProfile;
            this.streamUseCase = streamUseCase;
            this.streamUseHint = streamUseHint;
            this.sensorPixelModes = sensorPixelModes;
            this.streamBuilder = new ArrayList();
        }

        public final Size getSize() {
            return this.size;
        }

        /* JADX INFO: renamed from: getFormat-8FPWQzE */
        public final int m1928getFormat8FPWQzE() {
            return this.format;
        }

        /* JADX INFO: renamed from: getCamera-Dz_R5H8 */
        public final String m1926getCameraDz_R5H8() {
            return this.camera;
        }

        public final Integer getGroupNumber() {
            return this.groupNumber;
        }

        public final OutputConfiguration getExternalOutputConfig() {
            return this.externalOutputConfig;
        }

        public final OutputStream.OutputType getDeferredOutputType() {
            return this.deferredOutputType;
        }

        /* JADX INFO: renamed from: getMirrorMode-dO1_9xk */
        public final OutputStream.MirrorMode m1929getMirrorModedO1_9xk() {
            return this.mirrorMode;
        }

        /* JADX INFO: renamed from: getDynamicRangeProfile-OoVcG5w */
        public final OutputStream.DynamicRangeProfile m1927getDynamicRangeProfileOoVcG5w() {
            return this.dynamicRangeProfile;
        }

        /* JADX INFO: renamed from: getStreamUseCase-8x2ez34 */
        public final OutputStream.StreamUseCase m1930getStreamUseCase8x2ez34() {
            return this.streamUseCase;
        }

        /* JADX INFO: renamed from: getStreamUseHint-HIPxoCc */
        public final OutputStream.StreamUseHint m1931getStreamUseHintHIPxoCc() {
            return this.streamUseHint;
        }

        public final List getSensorPixelModes() {
            return this.sensorPixelModes;
        }

        public final List getStreamBuilder$camera_camera2_pipe() {
            return this.streamBuilder;
        }

        public final List getStreams() {
            return this.streamBuilder;
        }

        public final boolean getDeferrable() {
            return this.deferredOutputType != null;
        }

        public final boolean getSurfaceSharing() {
            return this.streamBuilder.size() > 1;
        }

        public String toString() {
            return OutputConfigId.m1913toStringimpl(this.f19id);
        }
    }

    public static final class OutputStreamImpl implements OutputStream {
        private final String camera;
        private final OutputStream.DynamicRangeProfile dynamicRangeProfile;
        private final int format;

        /* JADX INFO: renamed from: id */
        private final int f20id;
        private final OutputStream.MirrorMode mirrorMode;
        private final OutputStream.OutputType outputType;
        private final Size size;
        public CameraStream stream;
        private final OutputStream.StreamUseCase streamUseCase;
        private final OutputStream.StreamUseHint streamUseHint;

        public /* synthetic */ OutputStreamImpl(int i, Size size, int i2, String str, OutputStream.MirrorMode mirrorMode, OutputStream.TimestampBase timestampBase, OutputStream.DynamicRangeProfile dynamicRangeProfile, OutputStream.StreamUseCase streamUseCase, OutputStream.OutputType outputType, OutputStream.StreamUseHint streamUseHint, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, size, i2, str, mirrorMode, timestampBase, dynamicRangeProfile, streamUseCase, outputType, streamUseHint);
        }

        @Override // androidx.camera.camera2.pipe.OutputStream
        /* JADX INFO: renamed from: getTimestampBase-pcPfPbY */
        public OutputStream.TimestampBase mo1690getTimestampBasepcPfPbY() {
            return null;
        }

        @Override // androidx.camera.camera2.pipe.OutputStream
        public /* synthetic */ boolean isValidForHighSpeedOperatingMode() {
            return OutputStream.CC.$default$isValidForHighSpeedOperatingMode(this);
        }

        private OutputStreamImpl(int i, Size size, int i2, String camera, OutputStream.MirrorMode mirrorMode, OutputStream.TimestampBase timestampBase, OutputStream.DynamicRangeProfile dynamicRangeProfile, OutputStream.StreamUseCase streamUseCase, OutputStream.OutputType outputType, OutputStream.StreamUseHint streamUseHint) {
            Intrinsics.checkNotNullParameter(size, "size");
            Intrinsics.checkNotNullParameter(camera, "camera");
            this.f20id = i;
            this.size = size;
            this.format = i2;
            this.camera = camera;
            this.mirrorMode = mirrorMode;
            this.dynamicRangeProfile = dynamicRangeProfile;
            this.streamUseCase = streamUseCase;
            this.outputType = outputType;
            this.streamUseHint = streamUseHint;
        }

        @Override // androidx.camera.camera2.pipe.OutputStream
        /* JADX INFO: renamed from: getId-4LaLFng */
        public int mo1686getId4LaLFng() {
            return this.f20id;
        }

        @Override // androidx.camera.camera2.pipe.OutputStream
        public Size getSize() {
            return this.size;
        }

        @Override // androidx.camera.camera2.pipe.OutputStream
        /* JADX INFO: renamed from: getFormat-8FPWQzE */
        public int mo1685getFormat8FPWQzE() {
            return this.format;
        }

        @Override // androidx.camera.camera2.pipe.OutputStream
        /* JADX INFO: renamed from: getCamera-Dz_R5H8 */
        public String mo1683getCameraDz_R5H8() {
            return this.camera;
        }

        @Override // androidx.camera.camera2.pipe.OutputStream
        /* JADX INFO: renamed from: getMirrorMode-dO1_9xk */
        public OutputStream.MirrorMode mo1687getMirrorModedO1_9xk() {
            return this.mirrorMode;
        }

        @Override // androidx.camera.camera2.pipe.OutputStream
        /* JADX INFO: renamed from: getDynamicRangeProfile-OoVcG5w */
        public OutputStream.DynamicRangeProfile mo1684getDynamicRangeProfileOoVcG5w() {
            return this.dynamicRangeProfile;
        }

        @Override // androidx.camera.camera2.pipe.OutputStream
        /* JADX INFO: renamed from: getStreamUseCase-8x2ez34 */
        public OutputStream.StreamUseCase mo1688getStreamUseCase8x2ez34() {
            return this.streamUseCase;
        }

        @Override // androidx.camera.camera2.pipe.OutputStream
        public OutputStream.OutputType getOutputType() {
            return this.outputType;
        }

        @Override // androidx.camera.camera2.pipe.OutputStream
        /* JADX INFO: renamed from: getStreamUseHint-HIPxoCc */
        public OutputStream.StreamUseHint mo1689getStreamUseHintHIPxoCc() {
            return this.streamUseHint;
        }

        @Override // androidx.camera.camera2.pipe.OutputStream
        public CameraStream getStream() {
            CameraStream cameraStream = this.stream;
            if (cameraStream != null) {
                return cameraStream;
            }
            Intrinsics.throwUninitializedPropertyAccessException("stream");
            return null;
        }

        public void setStream(CameraStream cameraStream) {
            Intrinsics.checkNotNullParameter(cameraStream, "<set-?>");
            this.stream = cameraStream;
        }

        public String toString() {
            return OutputId.m1670toStringimpl(mo1686getId4LaLFng());
        }
    }

    private static final class InputStreamImpl implements InputStream {
        private final int format;

        /* JADX INFO: renamed from: id */
        private final int f18id;
        private final int maxImages;

        public /* synthetic */ InputStreamImpl(int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, i2, i3);
        }

        private InputStreamImpl(int i, int i2, int i3) {
            this.f18id = i;
            this.maxImages = i2;
            this.format = i3;
        }

        @Override // androidx.camera.camera2.pipe.InputStream
        /* JADX INFO: renamed from: getId-m1bwn9M */
        public int mo1651getIdm1bwn9M() {
            return this.f18id;
        }

        @Override // androidx.camera.camera2.pipe.InputStream
        public int getMaxImages() {
            return this.maxImages;
        }

        @Override // androidx.camera.camera2.pipe.InputStream
        /* JADX INFO: renamed from: getFormat-8FPWQzE */
        public int mo1650getFormat8FPWQzE() {
            return this.format;
        }
    }

    private final int computeNextSurfaceGroupId(CameraGraph.Config config) {
        List existingGroupNumbers = readExistingGroupNumbers(config.getStreams());
        int iNextGroupId$camera_camera2_pipe = Companion.nextGroupId$camera_camera2_pipe();
        while (existingGroupNumbers.contains(Integer.valueOf(iNextGroupId$camera_camera2_pipe))) {
            iNextGroupId$camera_camera2_pipe = Companion.nextGroupId$camera_camera2_pipe();
        }
        return iNextGroupId$camera_camera2_pipe;
    }

    private final List readExistingGroupNumbers(List list) {
        if (Build.VERSION.SDK_INT < 24) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((CameraStream.Config) it.next()).getOutputs());
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            arrayList.get(i);
        }
        ArrayList arrayList3 = new ArrayList();
        Iterator it2 = arrayList2.iterator();
        if (!it2.hasNext()) {
            return arrayList3;
        }
        WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(it2.next());
        throw null;
    }

    private final boolean computeIfDeferredStreamsAreSupported(CameraMetadata cameraMetadata, CameraGraph.Config config) {
        int i = Build.VERSION.SDK_INT;
        if (i < 26 || !CameraGraph.OperatingMode.m1591equalsimpl0(config.m1579getSessionMode2uNL3no(), CameraGraph.OperatingMode.Companion.m1597getNORMAL2uNL3no())) {
            return false;
        }
        CameraMetadata.Companion companion = CameraMetadata.Companion;
        if (companion.isHardwareLevelLegacy(cameraMetadata) || companion.isHardwareLevelLimited(cameraMetadata)) {
            return false;
        }
        return i < 28 || !companion.isHardwareLevelExternal(cameraMetadata);
    }

    public String toString() {
        return "StreamGraph(" + this._streamMap + ')';
    }

    private final List sortOutputsByPreviewStream(List list) {
        boolean z;
        boolean z2;
        List list2 = list;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator it = list2.iterator();
        while (true) {
            boolean z3 = true;
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            List outputs = ((CameraStream) next).getOutputs();
            if ((outputs instanceof Collection) && outputs.isEmpty()) {
                z3 = false;
            } else {
                Iterator it2 = outputs.iterator();
                while (it2.hasNext()) {
                    OutputStream.StreamUseCase streamUseCaseMo1688getStreamUseCase8x2ez34 = ((OutputStream) it2.next()).mo1688getStreamUseCase8x2ez34();
                    if (streamUseCaseMo1688getStreamUseCase8x2ez34 == null ? false : OutputStream.StreamUseCase.m1719equalsimpl0(streamUseCaseMo1688getStreamUseCase8x2ez34.m1722unboximpl(), OutputStream.StreamUseCase.Companion.m1724getPREVIEWvrKr8v8())) {
                        break;
                    }
                }
                z3 = false;
            }
            if (z3) {
                arrayList.add(next);
            } else {
                arrayList2.add(next);
            }
        }
        Pair pair = new Pair(arrayList, arrayList2);
        List list3 = (List) pair.component1();
        List list4 = (List) pair.component2();
        List list5 = list3;
        if (!list5.isEmpty()) {
            return CollectionsKt.plus((Collection) list5, (Iterable) list4);
        }
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        for (Object obj : list2) {
            List outputs2 = ((CameraStream) obj).getOutputs();
            if ((outputs2 instanceof Collection) && outputs2.isEmpty()) {
                z2 = false;
            } else {
                Iterator it3 = outputs2.iterator();
                while (it3.hasNext()) {
                    if (CollectionsKt.contains(previewOutputTypes, ((OutputStream) it3.next()).getOutputType())) {
                        z2 = true;
                        break;
                    }
                }
                z2 = false;
            }
            if (z2) {
                arrayList3.add(obj);
            } else {
                arrayList4.add(obj);
            }
        }
        Pair pair2 = new Pair(arrayList3, arrayList4);
        List list6 = (List) pair2.component1();
        List list7 = (List) pair2.component2();
        if (!list6.isEmpty()) {
            return CollectionsKt.plus((Collection) CollectionsKt.sortedWith(list6, previewOutputTypesComparator), (Iterable) list7);
        }
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = new ArrayList();
        for (Object obj2 : list2) {
            List outputs3 = ((CameraStream) obj2).getOutputs();
            if ((outputs3 instanceof Collection) && outputs3.isEmpty()) {
                z = false;
            } else {
                Iterator it4 = outputs3.iterator();
                while (it4.hasNext()) {
                    if (previewFormats.contains(StreamFormat.m1772boximpl(((OutputStream) it4.next()).mo1685getFormat8FPWQzE()))) {
                        z = true;
                        break;
                    }
                }
                z = false;
            }
            if (z) {
                arrayList5.add(obj2);
            } else {
                arrayList6.add(obj2);
            }
        }
        Pair pair3 = new Pair(arrayList5, arrayList6);
        List list8 = (List) pair3.component1();
        return !list8.isEmpty() ? CollectionsKt.plus((Collection) CollectionsKt.sortedWith(list8, previewFormatComparator), (Iterable) pair3.component2()) : list;
    }

    private final List sortOutputsByVideoStream(List list) {
        boolean z;
        List list2 = list;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator it = list2.iterator();
        while (true) {
            boolean z2 = true;
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            List outputs = ((CameraStream) next).getOutputs();
            if ((outputs instanceof Collection) && outputs.isEmpty()) {
                z2 = false;
            } else {
                Iterator it2 = outputs.iterator();
                while (it2.hasNext()) {
                    OutputStream.StreamUseCase streamUseCaseMo1688getStreamUseCase8x2ez34 = ((OutputStream) it2.next()).mo1688getStreamUseCase8x2ez34();
                    if (streamUseCaseMo1688getStreamUseCase8x2ez34 == null ? false : OutputStream.StreamUseCase.m1719equalsimpl0(streamUseCaseMo1688getStreamUseCase8x2ez34.m1722unboximpl(), OutputStream.StreamUseCase.Companion.m1725getVIDEO_RECORDvrKr8v8())) {
                        break;
                    }
                }
                z2 = false;
            }
            if (z2) {
                arrayList.add(next);
            } else {
                arrayList2.add(next);
            }
        }
        Pair pair = new Pair(arrayList, arrayList2);
        List list3 = (List) pair.component1();
        List list4 = (List) pair.component2();
        if (!list3.isEmpty()) {
            return CollectionsKt.plus((Collection) list4, (Iterable) list3);
        }
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        for (Object obj : list2) {
            List outputs2 = ((CameraStream) obj).getOutputs();
            if ((outputs2 instanceof Collection) && outputs2.isEmpty()) {
                z = false;
            } else {
                Iterator it3 = outputs2.iterator();
                while (it3.hasNext()) {
                    OutputStream.StreamUseHint streamUseHintMo1689getStreamUseHintHIPxoCc = ((OutputStream) it3.next()).mo1689getStreamUseHintHIPxoCc();
                    if (streamUseHintMo1689getStreamUseHintHIPxoCc == null ? false : OutputStream.StreamUseHint.m1729equalsimpl0(streamUseHintMo1689getStreamUseHintHIPxoCc.m1732unboximpl(), OutputStream.StreamUseHint.Companion.m1734getVIDEO_RECORD4VYZOf8())) {
                        z = true;
                        break;
                    }
                }
                z = false;
            }
            if (z) {
                arrayList3.add(obj);
            } else {
                arrayList4.add(obj);
            }
        }
        Pair pair2 = new Pair(arrayList3, arrayList4);
        List list5 = (List) pair2.component1();
        return !list5.isEmpty() ? CollectionsKt.plus((Collection) pair2.component2(), (Iterable) list5) : list;
    }

    @Override // java.lang.AutoCloseable
    public void close() throws Exception {
        Iterator it = this.imageSourceMap.values().iterator();
        while (it.hasNext()) {
            UseCaseGraphContext$$ExternalSyntheticAutoCloseableDispatcher0.m22m((ImageSource) it.next());
        }
    }

    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: renamed from: nextStreamId-ptHMqGs$camera_camera2_pipe */
        public final int m1925nextStreamIdptHMqGs$camera_camera2_pipe() {
            return StreamId.m1787constructorimpl(StreamGraphImpl.streamIds.incrementAndGet());
        }

        /* JADX INFO: renamed from: nextOutputId-4LaLFng$camera_camera2_pipe */
        public final int m1924nextOutputId4LaLFng$camera_camera2_pipe() {
            return OutputId.m1666constructorimpl(StreamGraphImpl.outputIds.incrementAndGet());
        }

        /* JADX INFO: renamed from: nextInputId-m1bwn9M$camera_camera2_pipe */
        public final int m1923nextInputIdm1bwn9M$camera_camera2_pipe() {
            return InputStreamId.m1653constructorimpl(StreamGraphImpl.inputIds.incrementAndGet());
        }

        /* JADX INFO: renamed from: nextConfigId-hoCEiqs$camera_camera2_pipe */
        public final int m1922nextConfigIdhoCEiqs$camera_camera2_pipe() {
            return OutputConfigId.m1912constructorimpl(StreamGraphImpl.configIds.incrementAndGet());
        }

        public final int nextGroupId$camera_camera2_pipe() {
            return StreamGraphImpl.groupIds.incrementAndGet();
        }
    }

    static {
        OutputStream.OutputType.Companion companion = OutputStream.OutputType.Companion;
        previewOutputTypes = CollectionsKt.listOf((Object[]) new OutputStream.OutputType[]{companion.getSURFACE_VIEW(), companion.getSURFACE_TEXTURE()});
        previewOutputTypesComparator = new Comparator() { // from class: androidx.camera.camera2.pipe.graph.StreamGraphImpl$special$$inlined$compareBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                Iterator it = ((CameraStream) obj).getOutputs().iterator();
                if (!it.hasNext()) {
                    throw new NoSuchElementException();
                }
                Integer numValueOf = Integer.valueOf(CollectionsKt.indexOf(StreamGraphImpl.previewOutputTypes, (Object) ((OutputStream) it.next()).getOutputType()));
                while (it.hasNext()) {
                    Integer numValueOf2 = Integer.valueOf(CollectionsKt.indexOf(StreamGraphImpl.previewOutputTypes, (Object) ((OutputStream) it.next()).getOutputType()));
                    if (numValueOf.compareTo(numValueOf2) < 0) {
                        numValueOf = numValueOf2;
                    }
                }
                Iterator it2 = ((CameraStream) obj2).getOutputs().iterator();
                if (it2.hasNext()) {
                    Integer numValueOf3 = Integer.valueOf(CollectionsKt.indexOf(StreamGraphImpl.previewOutputTypes, (Object) ((OutputStream) it2.next()).getOutputType()));
                    while (it2.hasNext()) {
                        Integer numValueOf4 = Integer.valueOf(CollectionsKt.indexOf(StreamGraphImpl.previewOutputTypes, (Object) ((OutputStream) it2.next()).getOutputType()));
                        if (numValueOf3.compareTo(numValueOf4) < 0) {
                            numValueOf3 = numValueOf4;
                        }
                    }
                    return ComparisonsKt.compareValues(numValueOf, numValueOf3);
                }
                throw new NoSuchElementException();
            }
        };
        StreamFormat.Companion companion2 = StreamFormat.Companion;
        previewFormats = CollectionsKt.listOf((Object[]) new StreamFormat[]{StreamFormat.m1772boximpl(companion2.m1781getUNKNOWN8FPWQzE()), StreamFormat.m1772boximpl(companion2.m1780getPRIVATE8FPWQzE())});
        previewFormatComparator = new Comparator() { // from class: androidx.camera.camera2.pipe.graph.StreamGraphImpl$special$$inlined$compareBy$2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                Iterator it = ((CameraStream) obj).getOutputs().iterator();
                if (!it.hasNext()) {
                    throw new NoSuchElementException();
                }
                Integer numValueOf = Integer.valueOf(StreamGraphImpl.previewFormats.indexOf(StreamFormat.m1772boximpl(((OutputStream) it.next()).mo1685getFormat8FPWQzE())));
                while (it.hasNext()) {
                    Integer numValueOf2 = Integer.valueOf(StreamGraphImpl.previewFormats.indexOf(StreamFormat.m1772boximpl(((OutputStream) it.next()).mo1685getFormat8FPWQzE())));
                    if (numValueOf.compareTo(numValueOf2) < 0) {
                        numValueOf = numValueOf2;
                    }
                }
                Iterator it2 = ((CameraStream) obj2).getOutputs().iterator();
                if (it2.hasNext()) {
                    Integer numValueOf3 = Integer.valueOf(StreamGraphImpl.previewFormats.indexOf(StreamFormat.m1772boximpl(((OutputStream) it2.next()).mo1685getFormat8FPWQzE())));
                    while (it2.hasNext()) {
                        Integer numValueOf4 = Integer.valueOf(StreamGraphImpl.previewFormats.indexOf(StreamFormat.m1772boximpl(((OutputStream) it2.next()).mo1685getFormat8FPWQzE())));
                        if (numValueOf3.compareTo(numValueOf4) < 0) {
                            numValueOf3 = numValueOf4;
                        }
                    }
                    return ComparisonsKt.compareValues(numValueOf, numValueOf3);
                }
                throw new NoSuchElementException();
            }
        };
    }
}
