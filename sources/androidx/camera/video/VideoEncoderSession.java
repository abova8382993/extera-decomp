package androidx.camera.video;

import android.view.Surface;
import androidx.camera.core.Logger;
import androidx.camera.core.SurfaceRequest;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.video.internal.encoder.Encoder;
import androidx.camera.video.internal.encoder.EncoderFactory;
import androidx.camera.video.internal.encoder.InvalidConfigException;
import androidx.camera.video.internal.encoder.VideoEncoderConfig;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Consumer;
import androidx.view.LiveData$$ExternalSyntheticBUOutline0;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Objects;
import java.util.concurrent.Executor;
import kotlin.properties.NotNullVar$$ExternalSyntheticBUOutline0;

/* JADX INFO: loaded from: classes4.dex */
final class VideoEncoderSession {
    private final Executor mExecutor;
    private final Executor mSequentialExecutor;
    private final EncoderFactory mVideoEncoderFactory;
    private Encoder mVideoEncoder = null;
    private Surface mActiveSurface = null;
    private SurfaceRequest mSurfaceRequest = null;
    private VideoEncoderState mVideoEncoderState = VideoEncoderState.NOT_INITIALIZED;
    private ListenableFuture<Void> mReleasedFuture = Futures.immediateFailedFuture(new IllegalStateException("Cannot close the encoder before configuring."));
    private CallbackToFutureAdapter.Completer<Void> mReleasedCompleter = null;
    private ListenableFuture<Encoder> mReadyToReleaseFuture = Futures.immediateFailedFuture(new IllegalStateException("Cannot close the encoder before configuring."));
    private CallbackToFutureAdapter.Completer<Encoder> mReadyToReleaseCompleter = null;

    public enum VideoEncoderState {
        NOT_INITIALIZED,
        INITIALIZING,
        PENDING_RELEASE,
        READY,
        RELEASED
    }

    public VideoEncoderSession(EncoderFactory encoderFactory, Executor executor, Executor executor2) {
        this.mExecutor = executor2;
        this.mSequentialExecutor = executor;
        this.mVideoEncoderFactory = encoderFactory;
    }

    public ListenableFuture<Encoder> configure(final SurfaceRequest surfaceRequest, final VideoEncoderConfig videoEncoderConfig) {
        if (this.mVideoEncoderState.ordinal() == 0) {
            this.mVideoEncoderState = VideoEncoderState.INITIALIZING;
            this.mSurfaceRequest = surfaceRequest;
            Logger.m74d("VideoEncoderSession", "Create VideoEncoderSession: " + this);
            this.mReleasedFuture = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.video.VideoEncoderSession$$ExternalSyntheticLambda1
                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                    return VideoEncoderSession.m1903$r8$lambda$9aRIKLhGw4HdbXHWbcbCRBROM0(this.f$0, completer);
                }
            });
            this.mReadyToReleaseFuture = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.video.VideoEncoderSession$$ExternalSyntheticLambda2
                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                    return VideoEncoderSession.$r8$lambda$PD_k74jzItzsG1xTdgZ6Wanfs0k(this.f$0, completer);
                }
            });
            ListenableFuture future = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.video.VideoEncoderSession$$ExternalSyntheticLambda3
                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                    return VideoEncoderSession.$r8$lambda$UMKB9ijC89XfXZJjfTuNJgEk90w(this.f$0, surfaceRequest, videoEncoderConfig, completer);
                }
            });
            Futures.addCallback(future, new FutureCallback<Encoder>() { // from class: androidx.camera.video.VideoEncoderSession.1
                @Override // androidx.camera.core.impl.utils.futures.FutureCallback
                public void onSuccess(Encoder encoder) {
                }

                @Override // androidx.camera.core.impl.utils.futures.FutureCallback
                public void onFailure(Throwable th) {
                    Logger.m80w("VideoEncoderSession", "VideoEncoder configuration failed.", th);
                    VideoEncoderSession.this.terminateNow();
                }
            }, this.mSequentialExecutor);
            return Futures.nonCancellationPropagating(future);
        }
        return Futures.immediateFailedFuture(new IllegalStateException("configure() shouldn't be called in " + this.mVideoEncoderState));
    }

    /* JADX INFO: renamed from: $r8$lambda$9aRIKLhGw4HdbXHWbcb-CRBROM0, reason: not valid java name */
    public static /* synthetic */ Object m1903$r8$lambda$9aRIKLhGw4HdbXHWbcbCRBROM0(VideoEncoderSession videoEncoderSession, CallbackToFutureAdapter.Completer completer) {
        videoEncoderSession.mReleasedCompleter = completer;
        return "ReleasedFuture " + videoEncoderSession;
    }

    public static /* synthetic */ Object $r8$lambda$PD_k74jzItzsG1xTdgZ6Wanfs0k(VideoEncoderSession videoEncoderSession, CallbackToFutureAdapter.Completer completer) {
        videoEncoderSession.mReadyToReleaseCompleter = completer;
        return "ReadyToReleaseFuture " + videoEncoderSession;
    }

    public static /* synthetic */ Object $r8$lambda$UMKB9ijC89XfXZJjfTuNJgEk90w(VideoEncoderSession videoEncoderSession, SurfaceRequest surfaceRequest, VideoEncoderConfig videoEncoderConfig, CallbackToFutureAdapter.Completer completer) {
        videoEncoderSession.configureVideoEncoderInternal(surfaceRequest, videoEncoderConfig, completer);
        return "ConfigureVideoEncoderFuture " + videoEncoderSession;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isConfiguredSurfaceRequest(androidx.camera.core.SurfaceRequest r5) {
        /*
            r4 = this;
            androidx.camera.video.VideoEncoderSession$VideoEncoderState r0 = r4.mVideoEncoderState
            int r0 = r0.ordinal()
            r1 = 0
            if (r0 == 0) goto L26
            r2 = 1
            if (r0 == r2) goto L21
            r3 = 2
            if (r0 == r3) goto L26
            r3 = 3
            if (r0 == r3) goto L21
            r5 = 4
            if (r0 != r5) goto L16
            goto L26
        L16:
            androidx.camera.video.VideoEncoderSession$VideoEncoderState r4 = r4.mVideoEncoderState
            java.lang.String r5 = " is not handled"
            java.lang.String r0 = "State "
            kotlin.properties.NotNullVar$$ExternalSyntheticBUOutline0.m935m(r0, r4, r5)
            r4 = 0
            return r4
        L21:
            androidx.camera.core.SurfaceRequest r4 = r4.mSurfaceRequest
            if (r4 != r5) goto L26
            return r2
        L26:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.video.VideoEncoderSession.isConfiguredSurfaceRequest(androidx.camera.core.SurfaceRequest):boolean");
    }

    public ListenableFuture<Encoder> getReadyToReleaseFuture() {
        return Futures.nonCancellationPropagating(this.mReadyToReleaseFuture);
    }

    public ListenableFuture<Void> signalTermination() {
        closeInternal();
        return Futures.nonCancellationPropagating(this.mReleasedFuture);
    }

    public void terminateNow() {
        int iOrdinal = this.mVideoEncoderState.ordinal();
        if (iOrdinal == 0) {
            this.mVideoEncoderState = VideoEncoderState.RELEASED;
            return;
        }
        if (iOrdinal == 1 || iOrdinal == 2 || iOrdinal == 3) {
            this.mVideoEncoderState = VideoEncoderState.RELEASED;
            this.mReadyToReleaseCompleter.set(this.mVideoEncoder);
            this.mSurfaceRequest = null;
            if (this.mVideoEncoder != null) {
                Logger.m74d("VideoEncoderSession", "VideoEncoder is releasing: " + this.mVideoEncoder);
                this.mVideoEncoder.release();
                this.mVideoEncoder.getReleasedFuture().addListener(new Runnable() { // from class: androidx.camera.video.VideoEncoderSession$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.mReleasedCompleter.set(null);
                    }
                }, this.mSequentialExecutor);
                this.mVideoEncoder = null;
                return;
            }
            Logger.m79w("VideoEncoderSession", "There's no VideoEncoder to release! Finish release completer.");
            this.mReleasedCompleter.set(null);
            return;
        }
        VideoEncoderState videoEncoderState = this.mVideoEncoderState;
        if (iOrdinal == 4) {
            Logger.m74d("VideoEncoderSession", "terminateNow in " + videoEncoderState + ", No-op");
            return;
        }
        LiveData$$ExternalSyntheticBUOutline0.m184m("State ", videoEncoderState, " is not handled");
    }

    public Surface getActiveSurface() {
        if (this.mVideoEncoderState != VideoEncoderState.READY) {
            return null;
        }
        return this.mActiveSurface;
    }

    public Encoder getVideoEncoder() {
        return this.mVideoEncoder;
    }

    private void closeInternal() {
        int iOrdinal = this.mVideoEncoderState.ordinal();
        if (iOrdinal == 0 || iOrdinal == 1) {
            terminateNow();
            return;
        }
        if (iOrdinal != 2 && iOrdinal != 3) {
            if (iOrdinal == 4) {
                Logger.m74d("VideoEncoderSession", "closeInternal in RELEASED state, No-op");
                return;
            } else {
                NotNullVar$$ExternalSyntheticBUOutline0.m935m("State ", this.mVideoEncoderState, " is not handled");
                return;
            }
        }
        Logger.m74d("VideoEncoderSession", "closeInternal in " + this.mVideoEncoderState + " state");
        this.mVideoEncoderState = VideoEncoderState.PENDING_RELEASE;
    }

    private void configureVideoEncoderInternal(SurfaceRequest surfaceRequest, VideoEncoderConfig videoEncoderConfig, CallbackToFutureAdapter.Completer<Encoder> completer) {
        try {
            Encoder encoderCreateEncoder = this.mVideoEncoderFactory.createEncoder(this.mExecutor, videoEncoderConfig, surfaceRequest.getSessionType());
            this.mVideoEncoder = encoderCreateEncoder;
            if (!(encoderCreateEncoder.getInput() instanceof Encoder.SurfaceInput)) {
                completer.setException(new AssertionError("The EncoderInput of video isn't a SurfaceInput."));
                return;
            }
            Surface surface = ((Encoder.SurfaceInput) this.mVideoEncoder.getInput()).getSurface();
            this.mActiveSurface = surface;
            Logger.m74d("VideoEncoderSession", "provide surface: " + surface);
            surfaceRequest.provideSurface(surface, this.mSequentialExecutor, new Consumer() { // from class: androidx.camera.video.VideoEncoderSession$$ExternalSyntheticLambda4
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    VideoEncoderSession.$r8$lambda$3VS9N96niRhWMq3xwyNPJVrofi8(this.f$0, (SurfaceRequest.Result) obj);
                }
            });
            this.mVideoEncoderState = VideoEncoderState.READY;
            completer.set(this.mVideoEncoder);
        } catch (InvalidConfigException e) {
            Logger.m77e("VideoEncoderSession", "Unable to initialize video encoder.", e);
            completer.setException(e);
        }
    }

    public static /* synthetic */ void $r8$lambda$3VS9N96niRhWMq3xwyNPJVrofi8(VideoEncoderSession videoEncoderSession, SurfaceRequest.Result result) {
        videoEncoderSession.getClass();
        Logger.m74d("VideoEncoderSession", "Surface can be closed: " + result.getSurface());
        videoEncoderSession.mActiveSurface = null;
        videoEncoderSession.mReadyToReleaseCompleter.set(videoEncoderSession.mVideoEncoder);
        videoEncoderSession.closeInternal();
    }

    public String toString() {
        return "VideoEncoderSession@" + hashCode() + " for " + Objects.toString(this.mSurfaceRequest, "SURFACE_REQUEST_NOT_CONFIGURED");
    }
}
