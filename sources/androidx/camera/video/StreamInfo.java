package androidx.camera.video;

import androidx.camera.core.SurfaceRequest;
import androidx.camera.core.impl.ConstantObservable;
import androidx.camera.core.impl.Observable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes4.dex */
public abstract class StreamInfo {
    static final StreamInfo STREAM_INFO_ANY_INACTIVE = m108of(0, StreamState.INACTIVE);
    static final Set<Integer> NON_SURFACE_STREAM_ID = Collections.unmodifiableSet(new HashSet(Arrays.asList(0, -1)));
    static final Observable<StreamInfo> ALWAYS_ACTIVE_OBSERVABLE = ConstantObservable.withValue(m108of(0, StreamState.ACTIVE));

    public enum StreamState {
        ACTIVE,
        INACTIVE
    }

    public abstract int getId();

    public abstract SurfaceRequest.TransformationInfo getInProgressTransformationInfo();

    public abstract StreamState getStreamState();

    /* JADX INFO: renamed from: of */
    public static StreamInfo m108of(int i, StreamState streamState) {
        return new AutoValue_StreamInfo(i, streamState, null);
    }

    /* JADX INFO: renamed from: of */
    public static StreamInfo m109of(int i, StreamState streamState, SurfaceRequest.TransformationInfo transformationInfo) {
        return new AutoValue_StreamInfo(i, streamState, transformationInfo);
    }
}
