package androidx.camera.video;

import androidx.camera.core.CameraInfo;
import androidx.camera.core.SurfaceRequest;
import androidx.camera.core.impl.ConstantObservable;
import androidx.camera.core.impl.Observable;
import androidx.camera.core.impl.Timebase;

/* JADX INFO: loaded from: classes4.dex */
public interface VideoOutput {

    public enum SourceState {
        ACTIVE_STREAMING,
        ACTIVE_NON_STREAMING,
        INACTIVE
    }

    default boolean isQualitySelectorDefault() {
        return true;
    }

    default void onSourceStateChanged(SourceState sourceState) {
    }

    void onSurfaceRequested(SurfaceRequest surfaceRequest);

    default void onSurfaceRequested(SurfaceRequest surfaceRequest, Timebase timebase, boolean z) {
        onSurfaceRequested(surfaceRequest);
    }

    default Observable<StreamInfo> getStreamInfo() {
        return StreamInfo.ALWAYS_ACTIVE_OBSERVABLE;
    }

    default Observable<MediaSpec> getMediaSpec() {
        return ConstantObservable.withValue(null);
    }

    default Observable<Boolean> isSourceStreamRequired() {
        return ConstantObservable.withValue(Boolean.FALSE);
    }

    default VideoCapabilities getMediaCapabilities(CameraInfo cameraInfo, int i) {
        return VideoCapabilities.EMPTY;
    }

    default EncoderProfilesResolver getEncoderProfilesResolver(CameraInfo cameraInfo, int i) {
        return EncoderProfilesResolver.EMPTY;
    }
}
