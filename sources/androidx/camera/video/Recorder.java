package androidx.camera.video;

import android.net.Uri;
import android.util.Range;
import android.view.Surface;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.DynamicRange;
import androidx.camera.core.Logger;
import androidx.camera.core.SurfaceRequest;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.MutableStateObservable;
import androidx.camera.core.impl.Observable;
import androidx.camera.core.impl.StateObservable;
import androidx.camera.core.impl.Timebase;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.core.internal.utils.ArrayRingBuffer;
import androidx.camera.core.internal.utils.RingBuffer;
import androidx.camera.video.MediaSpec;
import androidx.camera.video.Recorder;
import androidx.camera.video.StreamInfo;
import androidx.camera.video.VideoOutput;
import androidx.camera.video.VideoSpec;
import androidx.camera.video.internal.OutputStorage;
import androidx.camera.video.internal.VideoValidatedEncoderProfilesProxy;
import androidx.camera.video.internal.config.VideoConfigUtil;
import androidx.camera.video.internal.encoder.EncodeException;
import androidx.camera.video.internal.encoder.EncodedData;
import androidx.camera.video.internal.encoder.Encoder;
import androidx.camera.video.internal.encoder.EncoderCallback;
import androidx.camera.video.internal.encoder.EncoderConfig;
import androidx.camera.video.internal.encoder.EncoderFactory;
import androidx.camera.video.internal.encoder.EncoderImpl;
import androidx.camera.video.internal.encoder.OutputConfig;
import androidx.camera.video.internal.encoder.VideoEncoderConfig;
import androidx.camera.video.internal.encoder.VideoEncoderInfo;
import androidx.camera.video.internal.encoder.VideoEncoderInfoImpl;
import androidx.camera.video.internal.muxer.MuxerFactory;
import androidx.camera.video.internal.utils.StorageUtil;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Consumer;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.LongCompanionObject;
import okhttp3.CertificatePinner$$ExternalSyntheticBUOutline0;
import okio.Buffer$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
public final class Recorder implements VideoOutput {
    private static final Executor AUDIO_EXECUTOR;
    static final EncoderFactory DEFAULT_ENCODER_FACTORY;
    private static final MuxerFactory DEFAULT_MUXER_FACTORY;
    public static final QualitySelector DEFAULT_QUALITY_SELECTOR;
    private static final VideoEncoderInfo.Finder DEFAULT_VIDEO_ENCODER_INFO_FINDER;
    private static final MediaSpec MEDIA_SPEC_DEFAULT;
    private static final OutputStorage.Factory OUTPUT_STORAGE_FACTORY_DEFAULT;
    private static final Exception PENDING_RECORDING_ERROR_CAUSE_SOURCE_INACTIVE;
    private static final Set<State> PENDING_STATES = Collections.unmodifiableSet(EnumSet.of(State.PENDING_RECORDING, State.PENDING_PAUSED));
    private static final Set<State> VALID_NON_PENDING_STATES_WHILE_PENDING = Collections.unmodifiableSet(EnumSet.of(State.CONFIGURING, State.IDLING, State.RESETTING, State.STOPPING, State.ERROR));
    private static final VideoSpec VIDEO_SPEC_DEFAULT;
    static long sRetrySetupVideoDelayMs;
    static int sRetrySetupVideoMaxCount;
    private final EncoderFactory mAudioEncoderFactory;
    private final Executor mExecutor;
    private final MutableStateObservable<Boolean> mIsRecording;
    SurfaceRequest mLatestSurfaceRequest;
    final MutableStateObservable<MediaSpec> mMediaSpec;
    private final MuxerFactory mMuxerFactory;
    private final OutputStorage.Factory mOutputStorageFactory;
    private final long mRequiredFreeStorageBytes;
    final Executor mSequentialExecutor;
    private final MutableStateObservable<StreamInfo> mStreamInfo;
    private final Executor mUserProvidedExecutor;
    private final int mVideoCapabilitiesSource;
    private final EncoderFactory mVideoEncoderFactory;
    VideoEncoderSession mVideoEncoderSession;
    Timebase mVideoSourceTimebase;
    private final Object mLock = new Object();
    private final MutableStateObservable<Range<Integer>> mVideoEncoderBitrateRange = MutableStateObservable.withInitialState(null);
    private State mState = State.CONFIGURING;
    private State mNonPendingState = null;
    int mStreamId = 0;
    private long mLastGeneratedRecordingId = 0;
    boolean mInProgressRecordingStopping = false;
    private SurfaceRequest.TransformationInfo mInProgressTransformationInfo = null;
    private SurfaceRequest.TransformationInfo mSourceTransformationInfo = null;
    private VideoValidatedEncoderProfilesProxy mResolvedEncoderProfiles = null;
    final List<ListenableFuture<Void>> mEncodingFutures = new ArrayList();
    Integer mAudioTrackIndex = null;
    Integer mVideoTrackIndex = null;
    Surface mLatestSurface = null;
    Surface mActiveSurface = null;
    Encoder mVideoEncoder = null;
    OutputConfig mVideoOutputConfig = null;
    Encoder mAudioEncoder = null;
    OutputConfig mAudioOutputConfig = null;
    AudioState mAudioState = AudioState.INITIALIZING;
    Uri mOutputUri = Uri.EMPTY;
    long mRecordingBytes = 0;
    long mRecordingAudioBytes = 0;
    long mRecordingDurationUs = 0;
    long mFirstRecordingVideoDataTimeUs = LongCompanionObject.MAX_VALUE;
    int mFirstRecordingVideoBitrate = 0;
    long mFirstRecordingAudioDataTimeUs = LongCompanionObject.MAX_VALUE;
    long mPreviousRecordingVideoDataTimeUs = LongCompanionObject.MAX_VALUE;
    long mPreviousRecordingAudioDataTimeUs = LongCompanionObject.MAX_VALUE;
    long mFileSizeLimitInBytes = 0;
    long mDurationLimitUs = 0;
    int mRecordingStopError = 1;
    Throwable mRecordingStopErrorCause = null;
    EncodedData mPendingFirstVideoData = null;
    final RingBuffer<EncodedData> mPendingAudioRingBuffer = new ArrayRingBuffer(60);
    Throwable mAudioErrorCause = null;
    boolean mIsAudioSourceSilenced = false;
    VideoOutput.SourceState mSourceState = VideoOutput.SourceState.INACTIVE;
    ScheduledFuture<?> mSourceNonStreamingTimeout = null;
    private boolean mNeedsResetBeforeNextStart = false;
    private VideoEncoderConfig mVideoEncoderConfig = null;
    VideoEncoderSession mVideoEncoderSessionToRelease = null;
    double mAudioAmplitude = 0.0d;
    private boolean mShouldSendResumeEvent = false;
    private SetupVideoTask mSetupVideoTask = null;
    private OutputStorage mOutputStorage = null;
    private long mAvailableBytesAboveRequired = LongCompanionObject.MAX_VALUE;
    private boolean mHasGlProcessing = false;

    public enum AudioState {
        INITIALIZING,
        IDLING,
        DISABLED,
        ENABLED,
        ERROR_ENCODER,
        ERROR_SOURCE
    }

    public static abstract class RecordingRecord implements AutoCloseable {
    }

    public enum State {
        CONFIGURING,
        PENDING_RECORDING,
        PENDING_PAUSED,
        IDLING,
        RECORDING,
        PAUSED,
        STOPPING,
        RESETTING,
        ERROR
    }

    public boolean isPersistentRecordingInProgress() {
        return false;
    }

    public void updateInProgressStatusEvent(boolean z) {
    }

    static {
        Quality quality = Quality.FHD;
        QualitySelector qualitySelectorFromOrderedList = QualitySelector.fromOrderedList(Arrays.asList(quality, Quality.f30HD, Quality.f31SD), FallbackStrategy.higherQualityOrLowerThan(quality));
        DEFAULT_QUALITY_SELECTOR = qualitySelectorFromOrderedList;
        VideoSpec videoSpecBuild = VideoSpec.builder().setQualitySelector(qualitySelectorFromOrderedList).setAspectRatio(-1).build();
        VIDEO_SPEC_DEFAULT = videoSpecBuild;
        MEDIA_SPEC_DEFAULT = MediaSpec.builder().setOutputFormat(-1).setVideoSpec(videoSpecBuild).build();
        PENDING_RECORDING_ERROR_CAUSE_SOURCE_INACTIVE = new RuntimeException("The video frame producer became inactive before any data was received.");
        DEFAULT_ENCODER_FACTORY = new EncoderFactory() { // from class: androidx.camera.video.Recorder$$ExternalSyntheticLambda10
            @Override // androidx.camera.video.internal.encoder.EncoderFactory
            public final Encoder createEncoder(Executor executor, EncoderConfig encoderConfig, int i) {
                return new EncoderImpl(executor, encoderConfig, i);
            }
        };
        DEFAULT_VIDEO_ENCODER_INFO_FINDER = VideoEncoderInfoImpl.FINDER;
        DEFAULT_MUXER_FACTORY = new MuxerFactory() { // from class: androidx.camera.video.Recorder$$ExternalSyntheticLambda11
        };
        OUTPUT_STORAGE_FACTORY_DEFAULT = new OutputStorage.Factory() { // from class: androidx.camera.video.Recorder$$ExternalSyntheticLambda12
        };
        AUDIO_EXECUTOR = CameraXExecutors.newSequentialExecutor(CameraXExecutors.ioExecutor());
        sRetrySetupVideoMaxCount = 3;
        sRetrySetupVideoDelayMs = 1000L;
    }

    public Recorder(Executor executor, MediaSpec mediaSpec, int i, EncoderFactory encoderFactory, EncoderFactory encoderFactory2, MuxerFactory muxerFactory, OutputStorage.Factory factory, long j) {
        this.mUserProvidedExecutor = executor;
        executor = executor == null ? CameraXExecutors.ioExecutor() : executor;
        this.mExecutor = executor;
        Executor executorNewSequentialExecutor = CameraXExecutors.newSequentialExecutor(executor);
        this.mSequentialExecutor = executorNewSequentialExecutor;
        this.mMediaSpec = MutableStateObservable.withInitialState(composeRecorderMediaSpec(mediaSpec));
        this.mVideoCapabilitiesSource = i;
        this.mStreamInfo = MutableStateObservable.withInitialState(StreamInfo.m108of(this.mStreamId, internalStateToStreamState(this.mState)));
        this.mIsRecording = MutableStateObservable.withInitialState(Boolean.FALSE);
        this.mVideoEncoderFactory = encoderFactory;
        this.mAudioEncoderFactory = encoderFactory2;
        this.mMuxerFactory = muxerFactory;
        this.mOutputStorageFactory = factory;
        this.mVideoEncoderSession = new VideoEncoderSession(encoderFactory, executorNewSequentialExecutor, executor);
        j = j == -1 ? 52428800L : j;
        this.mRequiredFreeStorageBytes = j;
        Logger.m74d("Recorder", "mRequiredFreeStorageBytes = " + StorageUtil.formatSize(j));
    }

    @Override // androidx.camera.video.VideoOutput
    public void onSurfaceRequested(SurfaceRequest surfaceRequest) {
        onSurfaceRequested(surfaceRequest, Timebase.UPTIME, false);
    }

    @Override // androidx.camera.video.VideoOutput
    public void onSurfaceRequested(final SurfaceRequest surfaceRequest, final Timebase timebase, final boolean z) {
        synchronized (this.mLock) {
            try {
                Logger.m74d("Recorder", "Surface is requested in state: " + this.mState + ", Current surface: " + this.mStreamId);
                if (this.mState == State.ERROR) {
                    setState(State.CONFIGURING);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mSequentialExecutor.execute(new Runnable() { // from class: androidx.camera.video.Recorder$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.onSurfaceRequestedInternal(surfaceRequest, timebase, z);
            }
        });
    }

    @Override // androidx.camera.video.VideoOutput
    public Observable<MediaSpec> getMediaSpec() {
        return this.mMediaSpec;
    }

    @Override // androidx.camera.video.VideoOutput
    public Observable<StreamInfo> getStreamInfo() {
        return this.mStreamInfo;
    }

    @Override // androidx.camera.video.VideoOutput
    public Observable<Boolean> isSourceStreamRequired() {
        return this.mIsRecording;
    }

    @Override // androidx.camera.video.VideoOutput
    public void onSourceStateChanged(final VideoOutput.SourceState sourceState) {
        this.mSequentialExecutor.execute(new Runnable() { // from class: androidx.camera.video.Recorder$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.onSourceStateChangedInternal(sourceState);
            }
        });
    }

    @Override // androidx.camera.video.VideoOutput
    public VideoCapabilities getMediaCapabilities(CameraInfo cameraInfo, int i) {
        return getVideoCapabilitiesInternal(i == 1 ? 2 : 1, cameraInfo, this.mVideoCapabilitiesSource, ((MediaSpec) getObservableData(this.mMediaSpec)).getVideoSpec().getMimeType());
    }

    public QualitySelector getQualitySelector() {
        return ((MediaSpec) getObservableData(this.mMediaSpec)).getVideoSpec().getQualitySelector();
    }

    @Override // androidx.camera.video.VideoOutput
    public boolean isQualitySelectorDefault() {
        return getQualitySelector() == DEFAULT_QUALITY_SELECTOR;
    }

    public void onSurfaceRequestedInternal(SurfaceRequest surfaceRequest, Timebase timebase, boolean z) {
        SurfaceRequest surfaceRequest2 = this.mLatestSurfaceRequest;
        if (surfaceRequest2 != null && !surfaceRequest2.isServiced()) {
            this.mLatestSurfaceRequest.willNotProvideSurface();
        }
        this.mHasGlProcessing = z;
        this.mLatestSurfaceRequest = surfaceRequest;
        this.mVideoSourceTimebase = timebase;
        configureInternal(surfaceRequest, timebase, true);
    }

    public void onSourceStateChangedInternal(VideoOutput.SourceState sourceState) {
        ScheduledFuture<?> scheduledFuture;
        Encoder encoder;
        VideoOutput.SourceState sourceState2 = this.mSourceState;
        this.mSourceState = sourceState;
        if (sourceState2 != sourceState) {
            Logger.m74d("Recorder", "Video source has transitioned to state: " + sourceState);
            if (sourceState == VideoOutput.SourceState.INACTIVE) {
                if (this.mActiveSurface == null) {
                    SetupVideoTask setupVideoTask = this.mSetupVideoTask;
                    if (setupVideoTask != null) {
                        setupVideoTask.cancelFailedRetry();
                        this.mSetupVideoTask = null;
                    }
                    requestReset(4, null, false);
                    return;
                }
                this.mNeedsResetBeforeNextStart = true;
                return;
            }
            if (sourceState != VideoOutput.SourceState.ACTIVE_NON_STREAMING || (scheduledFuture = this.mSourceNonStreamingTimeout) == null || !scheduledFuture.cancel(false) || (encoder = this.mVideoEncoder) == null) {
                return;
            }
            notifyEncoderSourceStopped(encoder);
            return;
        }
        Logger.m74d("Recorder", "Video source transitions to the same state: " + sourceState);
    }

    public void requestReset(int i, Throwable th, boolean z) {
        boolean z2;
        boolean z3;
        synchronized (this.mLock) {
            try {
                z2 = true;
                z3 = false;
                switch (this.mState) {
                    case CONFIGURING:
                    case IDLING:
                    case ERROR:
                        break;
                    case PENDING_RECORDING:
                    case PENDING_PAUSED:
                        updateNonPendingState(State.RESETTING);
                        break;
                    case RECORDING:
                    case PAUSED:
                        Preconditions.checkState(false, "In-progress recording shouldn't be null when in state " + this.mState);
                        if (!isPersistentRecordingInProgress()) {
                            setState(State.RESETTING);
                            z3 = true;
                            z2 = false;
                        }
                        break;
                    case STOPPING:
                        setState(State.RESETTING);
                        z2 = false;
                        break;
                    case RESETTING:
                    default:
                        z2 = false;
                        break;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        if (!z2) {
            if (z3) {
                stopInternal(null, -1L, i, th);
            }
        } else if (z) {
            resetVideo();
        } else {
            reset();
        }
    }

    private void configureInternal(SurfaceRequest surfaceRequest, Timebase timebase, boolean z) {
        if (surfaceRequest.isServiced()) {
            Logger.m79w("Recorder", "Ignore the SurfaceRequest since it is already served.");
            return;
        }
        surfaceRequest.setTransformationInfoListener(this.mSequentialExecutor, new SurfaceRequest.TransformationInfoListener() { // from class: androidx.camera.video.Recorder$$ExternalSyntheticLambda1
            @Override // androidx.camera.core.SurfaceRequest.TransformationInfoListener
            public final void onTransformationInfoUpdate(SurfaceRequest.TransformationInfo transformationInfo) {
                this.f$0.mSourceTransformationInfo = transformationInfo;
            }
        });
        this.mResolvedEncoderProfiles = getEncoderProfilesResolver(surfaceRequest.getCamera().getCameraInfo(), surfaceRequest.getSessionType()).findNearestHigherSupportedEncoderProfilesFor(surfaceRequest.getResolution(), surfaceRequest.getDynamicRange());
        Logger.m74d("Recorder", "mResolvedEncoderProfiles = " + this.mResolvedEncoderProfiles);
        SetupVideoTask setupVideoTask = this.mSetupVideoTask;
        if (setupVideoTask != null) {
            setupVideoTask.cancelFailedRetry();
        }
        SetupVideoTask setupVideoTask2 = new SetupVideoTask(surfaceRequest, timebase, this.mHasGlProcessing, z ? sRetrySetupVideoMaxCount : 0);
        this.mSetupVideoTask = setupVideoTask2;
        setupVideoTask2.start();
    }

    public class SetupVideoTask {
        private final int mMaxRetryCount;
        private final SurfaceRequest mSurfaceRequest;
        private final Timebase mTimebase;
        private boolean mIsFailedRetryCanceled = false;
        private int mRetryCount = 0;
        private ScheduledFuture<?> mRetryFuture = null;

        public static /* synthetic */ int access$608(SetupVideoTask setupVideoTask) {
            int i = setupVideoTask.mRetryCount;
            setupVideoTask.mRetryCount = i + 1;
            return i;
        }

        public SetupVideoTask(SurfaceRequest surfaceRequest, Timebase timebase, boolean z, int i) {
            this.mSurfaceRequest = surfaceRequest;
            this.mTimebase = timebase;
            Recorder.this.mHasGlProcessing = z;
            this.mMaxRetryCount = i;
        }

        public void start() {
            setupVideo(this.mSurfaceRequest, this.mTimebase);
        }

        public void cancelFailedRetry() {
            if (this.mIsFailedRetryCanceled) {
                return;
            }
            this.mIsFailedRetryCanceled = true;
            ScheduledFuture<?> scheduledFuture = this.mRetryFuture;
            if (scheduledFuture != null) {
                scheduledFuture.cancel(false);
                this.mRetryFuture = null;
            }
        }

        public void setupVideo(final SurfaceRequest surfaceRequest, final Timebase timebase) {
            Recorder.this.safeToCloseVideoEncoder().addListener(new Runnable() { // from class: androidx.camera.video.Recorder$SetupVideoTask$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Recorder.SetupVideoTask.m1900$r8$lambda$wczg8Yls85xyXF3E2b7y2H0JVo(this.f$0, surfaceRequest, timebase);
                }
            }, Recorder.this.mSequentialExecutor);
        }

        /* JADX INFO: renamed from: $r8$lambda$w-czg8Yls85xyXF3E2b7y2H0JVo */
        public static /* synthetic */ void m1900$r8$lambda$wczg8Yls85xyXF3E2b7y2H0JVo(SetupVideoTask setupVideoTask, SurfaceRequest surfaceRequest, Timebase timebase) {
            setupVideoTask.getClass();
            if (!surfaceRequest.isServiced() && (!Recorder.this.mVideoEncoderSession.isConfiguredSurfaceRequest(surfaceRequest) || Recorder.this.isPersistentRecordingInProgress())) {
                EncoderFactory encoderFactory = Recorder.this.mVideoEncoderFactory;
                Recorder recorder = Recorder.this;
                VideoEncoderSession videoEncoderSession = new VideoEncoderSession(encoderFactory, recorder.mSequentialExecutor, recorder.mExecutor);
                Recorder recorder2 = Recorder.this;
                MediaSpec mediaSpec = (MediaSpec) recorder2.getObservableData(recorder2.mMediaSpec);
                DynamicRange dynamicRange = surfaceRequest.getDynamicRange();
                VideoEncoderConfig videoEncoderConfigWorkaroundDataSpaceIfRequired = VideoConfigUtil.workaroundDataSpaceIfRequired(VideoConfigUtil.resolveVideoEncoderConfig(VideoConfigUtil.resolveVideoMimeInfo(mediaSpec, dynamicRange, Recorder.this.mResolvedEncoderProfiles), timebase, mediaSpec.getVideoSpec(), surfaceRequest.getResolution(), dynamicRange, surfaceRequest.getExpectedFrameRate()), Recorder.this.mHasGlProcessing);
                Recorder.this.mVideoEncoderConfig = videoEncoderConfigWorkaroundDataSpaceIfRequired;
                ListenableFuture<Encoder> listenableFutureConfigure = videoEncoderSession.configure(surfaceRequest, videoEncoderConfigWorkaroundDataSpaceIfRequired);
                Recorder.this.mVideoEncoderSession = videoEncoderSession;
                Futures.addCallback(listenableFutureConfigure, setupVideoTask.new C03101(videoEncoderSession), Recorder.this.mSequentialExecutor);
                return;
            }
            Logger.m79w("Recorder", "Ignore the SurfaceRequest " + surfaceRequest + " isServiced: " + surfaceRequest.isServiced() + " VideoEncoderSession: " + Recorder.this.mVideoEncoderSession + " has been configured with a persistent in-progress recording.");
        }

        /* JADX INFO: renamed from: androidx.camera.video.Recorder$SetupVideoTask$1 */
        public class C03101 implements FutureCallback<Encoder> {
            final /* synthetic */ VideoEncoderSession val$videoEncoderSession;

            public C03101(VideoEncoderSession videoEncoderSession) {
                this.val$videoEncoderSession = videoEncoderSession;
            }

            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onSuccess(Encoder encoder) {
                Logger.m74d("Recorder", "VideoEncoder is created. " + encoder);
                if (encoder == null) {
                    return;
                }
                Preconditions.checkState(Recorder.this.mVideoEncoderSession == this.val$videoEncoderSession);
                Preconditions.checkState(Recorder.this.mVideoEncoder == null);
                Recorder.this.onVideoEncoderReady(this.val$videoEncoderSession);
                Recorder.this.onConfigured();
            }

            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onFailure(Throwable th) {
                Logger.m80w("Recorder", "VideoEncoder Setup error: " + th, th);
                int i = SetupVideoTask.this.mRetryCount;
                int i2 = SetupVideoTask.this.mMaxRetryCount;
                SetupVideoTask setupVideoTask = SetupVideoTask.this;
                if (i < i2) {
                    SetupVideoTask.access$608(setupVideoTask);
                    SetupVideoTask.this.mRetryFuture = Recorder.scheduleTask(new Runnable() { // from class: androidx.camera.video.Recorder$SetupVideoTask$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            Recorder.SetupVideoTask.C03101.$r8$lambda$SyvAFBNl1CeWx7vKUwGrkmTny00(this.f$0);
                        }
                    }, Recorder.this.mSequentialExecutor, Recorder.sRetrySetupVideoDelayMs, TimeUnit.MILLISECONDS);
                    return;
                }
                Recorder.this.onEncoderSetupError(th);
            }

            public static /* synthetic */ void $r8$lambda$SyvAFBNl1CeWx7vKUwGrkmTny00(C03101 c03101) {
                if (SetupVideoTask.this.mIsFailedRetryCanceled) {
                    return;
                }
                Logger.m74d("Recorder", "Retry setupVideo #" + SetupVideoTask.this.mRetryCount);
                SetupVideoTask setupVideoTask = SetupVideoTask.this;
                setupVideoTask.setupVideo(setupVideoTask.mSurfaceRequest, SetupVideoTask.this.mTimebase);
            }
        }
    }

    public ListenableFuture<Void> safeToCloseVideoEncoder() {
        Logger.m74d("Recorder", "Try to safely release video encoder: " + this.mVideoEncoder);
        return this.mVideoEncoderSession.signalTermination();
    }

    public void onVideoEncoderReady(VideoEncoderSession videoEncoderSession) {
        Encoder encoder = (Encoder) Preconditions.checkNotNull(videoEncoderSession.getVideoEncoder());
        this.mVideoEncoder = encoder;
        this.mVideoEncoderBitrateRange.setState(((VideoEncoderInfo) encoder.getEncoderInfo()).getSupportedBitrateRange());
        this.mFirstRecordingVideoBitrate = this.mVideoEncoder.getConfiguredBitrate();
        Surface activeSurface = videoEncoderSession.getActiveSurface();
        this.mActiveSurface = activeSurface;
        setLatestSurface(activeSurface);
        Futures.addCallback(videoEncoderSession.getReadyToReleaseFuture(), new FutureCallback<Encoder>() { // from class: androidx.camera.video.Recorder.1
            final /* synthetic */ VideoEncoderSession val$videoEncoderSession;

            public C03061(VideoEncoderSession videoEncoderSession2) {
                videoEncoderSession = videoEncoderSession2;
            }

            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onSuccess(Encoder encoder2) {
                Encoder encoder3;
                Logger.m74d("Recorder", "VideoEncoder can be released: " + encoder2);
                if (encoder2 == null) {
                    return;
                }
                ScheduledFuture<?> scheduledFuture = Recorder.this.mSourceNonStreamingTimeout;
                if (scheduledFuture != null && scheduledFuture.cancel(false) && (encoder3 = Recorder.this.mVideoEncoder) != null && encoder3 == encoder2) {
                    Recorder.notifyEncoderSourceStopped(encoder3);
                }
                Recorder recorder = Recorder.this;
                recorder.mVideoEncoderSessionToRelease = videoEncoderSession;
                recorder.setLatestSurface(null);
                Recorder recorder2 = Recorder.this;
                recorder2.requestReset(4, null, recorder2.isPersistentRecordingInProgress());
            }

            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onFailure(Throwable th) {
                Logger.m74d("Recorder", "Error in ReadyToReleaseFuture: " + th);
            }
        }, this.mSequentialExecutor);
    }

    /* JADX INFO: renamed from: androidx.camera.video.Recorder$1 */
    public class C03061 implements FutureCallback<Encoder> {
        final /* synthetic */ VideoEncoderSession val$videoEncoderSession;

        public C03061(VideoEncoderSession videoEncoderSession2) {
            videoEncoderSession = videoEncoderSession2;
        }

        @Override // androidx.camera.core.impl.utils.futures.FutureCallback
        public void onSuccess(Encoder encoder2) {
            Encoder encoder3;
            Logger.m74d("Recorder", "VideoEncoder can be released: " + encoder2);
            if (encoder2 == null) {
                return;
            }
            ScheduledFuture<?> scheduledFuture = Recorder.this.mSourceNonStreamingTimeout;
            if (scheduledFuture != null && scheduledFuture.cancel(false) && (encoder3 = Recorder.this.mVideoEncoder) != null && encoder3 == encoder2) {
                Recorder.notifyEncoderSourceStopped(encoder3);
            }
            Recorder recorder = Recorder.this;
            recorder.mVideoEncoderSessionToRelease = videoEncoderSession;
            recorder.setLatestSurface(null);
            Recorder recorder2 = Recorder.this;
            recorder2.requestReset(4, null, recorder2.isPersistentRecordingInProgress());
        }

        @Override // androidx.camera.core.impl.utils.futures.FutureCallback
        public void onFailure(Throwable th) {
            Logger.m74d("Recorder", "Error in ReadyToReleaseFuture: " + th);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x004d A[Catch: all -> 0x0019, TryCatch #0 {all -> 0x0019, blocks: (B:41:0x0005, B:42:0x000d, B:61:0x005d, B:44:0x0011, B:47:0x001b, B:48:0x0022, B:50:0x0024, B:51:0x0030, B:52:0x0043, B:55:0x0047, B:57:0x004d, B:58:0x0051, B:59:0x0057), top: B:72:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0051 A[Catch: all -> 0x0019, TryCatch #0 {all -> 0x0019, blocks: (B:41:0x0005, B:42:0x000d, B:61:0x005d, B:44:0x0011, B:47:0x001b, B:48:0x0022, B:50:0x0024, B:51:0x0030, B:52:0x0043, B:55:0x0047, B:57:0x004d, B:58:0x0051, B:59:0x0057), top: B:72:0x0005 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onConfigured() {
        /*
            r6 = this;
            java.lang.String r0 = "Incorrectly invoke onConfigured() in state "
            java.lang.Object r1 = r6.mLock
            monitor-enter(r1)
            androidx.camera.video.Recorder$State r2 = r6.mState     // Catch: java.lang.Throwable -> L19
            int r2 = r2.ordinal()     // Catch: java.lang.Throwable -> L19
            r3 = 1
            r4 = 0
            switch(r2) {
                case 0: goto L57;
                case 1: goto L46;
                case 2: goto L44;
                case 3: goto L30;
                case 4: goto L24;
                case 5: goto L23;
                case 6: goto L1b;
                case 7: goto L30;
                case 8: goto L11;
                default: goto L10;
            }     // Catch: java.lang.Throwable -> L19
        L10:
            goto L5c
        L11:
            java.lang.String r0 = "Recorder"
            java.lang.String r2 = "onConfigured() was invoked when the Recorder had encountered error"
            androidx.camera.core.Logger.m76e(r0, r2)     // Catch: java.lang.Throwable -> L19
            goto L5c
        L19:
            r6 = move-exception
            goto L77
        L1b:
            java.lang.AssertionError r6 = new java.lang.AssertionError     // Catch: java.lang.Throwable -> L19
            java.lang.String r0 = "Unexpectedly invoke onConfigured() in a STOPPING state when it's not waiting for a new surface."
            r6.<init>(r0)     // Catch: java.lang.Throwable -> L19
            throw r6     // Catch: java.lang.Throwable -> L19
        L23:
            r4 = r3
        L24:
            boolean r0 = r6.isPersistentRecordingInProgress()     // Catch: java.lang.Throwable -> L19
            java.lang.String r2 = "Unexpectedly invoke onConfigured() when there's a non-persistent in-progress recording"
            androidx.core.util.Preconditions.checkState(r0, r2)     // Catch: java.lang.Throwable -> L19
            r0 = r4
            r4 = r3
            goto L5d
        L30:
            java.lang.AssertionError r2 = new java.lang.AssertionError     // Catch: java.lang.Throwable -> L19
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L19
            r3.<init>(r0)     // Catch: java.lang.Throwable -> L19
            androidx.camera.video.Recorder$State r6 = r6.mState     // Catch: java.lang.Throwable -> L19
            r3.append(r6)     // Catch: java.lang.Throwable -> L19
            java.lang.String r6 = r3.toString()     // Catch: java.lang.Throwable -> L19
            r2.<init>(r6)     // Catch: java.lang.Throwable -> L19
            throw r2     // Catch: java.lang.Throwable -> L19
        L44:
            r0 = r3
            goto L47
        L46:
            r0 = r4
        L47:
            androidx.camera.video.VideoOutput$SourceState r2 = r6.mSourceState     // Catch: java.lang.Throwable -> L19
            androidx.camera.video.VideoOutput$SourceState r5 = androidx.camera.video.VideoOutput.SourceState.INACTIVE     // Catch: java.lang.Throwable -> L19
            if (r2 != r5) goto L51
            r6.restoreNonPendingState()     // Catch: java.lang.Throwable -> L19
            goto L5d
        L51:
            androidx.camera.video.Recorder$State r2 = r6.mState     // Catch: java.lang.Throwable -> L19
            r6.makePendingRecordingActiveLocked(r2)     // Catch: java.lang.Throwable -> L19
            goto L5d
        L57:
            androidx.camera.video.Recorder$State r0 = androidx.camera.video.Recorder.State.IDLING     // Catch: java.lang.Throwable -> L19
            r6.setState(r0)     // Catch: java.lang.Throwable -> L19
        L5c:
            r0 = r4
        L5d:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L19
            if (r4 == 0) goto L76
            r1 = 0
            r6.updateEncoderCallbacks(r1, r3)
            androidx.camera.video.internal.encoder.Encoder r2 = r6.mVideoEncoder
            r2.start()
            boolean r2 = r6.mShouldSendResumeEvent
            if (r2 != 0) goto L75
            if (r0 == 0) goto L76
            androidx.camera.video.internal.encoder.Encoder r6 = r6.mVideoEncoder
            r6.pause()
            return
        L75:
            throw r1
        L76:
            return
        L77:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L19
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.video.Recorder.onConfigured():void");
    }

    private MediaSpec composeRecorderMediaSpec(MediaSpec mediaSpec) {
        MediaSpec.Builder builder = mediaSpec.toBuilder();
        if (mediaSpec.getVideoSpec().getAspectRatio() == -1) {
            builder.configureVideo(new Consumer() { // from class: androidx.camera.video.Recorder$$ExternalSyntheticLambda9
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    ((VideoSpec.Builder) obj).setAspectRatio(Recorder.VIDEO_SPEC_DEFAULT.getAspectRatio());
                }
            });
        }
        return builder.build();
    }

    public void onEncoderSetupError(Throwable th) {
        synchronized (this.mLock) {
            try {
                switch (this.mState) {
                    case CONFIGURING:
                    case PENDING_RECORDING:
                    case PENDING_PAUSED:
                        setStreamId(-1);
                        setState(State.ERROR);
                        break;
                    case IDLING:
                    case RECORDING:
                    case PAUSED:
                    case STOPPING:
                    case RESETTING:
                        throw new AssertionError("Encountered encoder setup error while in unexpected state " + this.mState + ": " + th);
                }
            } finally {
            }
        }
    }

    public void setupAndStartMuxer(RecordingRecord recordingRecord) {
        if (isAudioEnabled() && this.mPendingAudioRingBuffer.isEmpty()) {
            Buffer$$ExternalSyntheticBUOutline2.m976m("Audio is enabled but no audio sample is ready. Cannot start muxer.");
            return;
        }
        EncodedData encodedData = this.mPendingFirstVideoData;
        if (encodedData == null) {
            Buffer$$ExternalSyntheticBUOutline2.m976m("Muxer cannot be started without an encoded video frame.");
            return;
        }
        try {
            this.mPendingFirstVideoData = null;
            List<EncodedData> audioDataToWriteAndClearCache = getAudioDataToWriteAndClearCache(encodedData.getPresentationTimeUs());
            long size = encodedData.size();
            Iterator<EncodedData> it = audioDataToWriteAndClearCache.iterator();
            while (it.hasNext()) {
                size += it.next().size();
            }
            long j = this.mFileSizeLimitInBytes;
            if (j != 0 && size > j) {
                Logger.m74d("Recorder", String.format("Initial data exceeds file size limit %d > %d", Long.valueOf(size), Long.valueOf(this.mFileSizeLimitInBytes)));
                onInProgressRecordingInternalError(recordingRecord, 2, null);
                encodedData.close();
                return;
            }
            try {
                MediaSpec mediaSpec = (MediaSpec) getObservableData(this.mMediaSpec);
                if (mediaSpec.getOutputFormat() == -1) {
                    supportedMuxerFormatOrDefaultFrom(this.mResolvedEncoderProfiles, MediaSpec.outputFormatToMuxerFormat(MEDIA_SPEC_DEFAULT.getOutputFormat()));
                } else {
                    MediaSpec.outputFormatToMuxerFormat(mediaSpec.getOutputFormat());
                }
                new Consumer() { // from class: androidx.camera.video.Recorder$$ExternalSyntheticLambda8
                    @Override // androidx.core.util.Consumer
                    public final void accept(Object obj) {
                        this.f$0.mOutputUri = (Uri) obj;
                    }
                };
                throw null;
            } catch (IOException e) {
                onInProgressRecordingInternalError(recordingRecord, hasInsufficientStorageOrException(e) ? 3 : 5, e);
                encodedData.close();
            }
        } catch (Throwable th) {
            if (encodedData != null) {
                try {
                    encodedData.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private List<EncodedData> getAudioDataToWriteAndClearCache(long j) {
        ArrayList arrayList = new ArrayList();
        while (!this.mPendingAudioRingBuffer.isEmpty()) {
            EncodedData encodedDataDequeue = this.mPendingAudioRingBuffer.dequeue();
            if (encodedDataDequeue.getPresentationTimeUs() >= j) {
                arrayList.add(encodedDataDequeue);
            }
        }
        return arrayList;
    }

    private void updateEncoderCallbacks(final RecordingRecord recordingRecord, boolean z) {
        if (!this.mEncodingFutures.isEmpty()) {
            ListenableFuture listenableFutureAllAsList = Futures.allAsList(this.mEncodingFutures);
            if (!listenableFutureAllAsList.isDone()) {
                listenableFutureAllAsList.cancel(true);
            }
            this.mEncodingFutures.clear();
        }
        this.mEncodingFutures.add(CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver(recordingRecord) { // from class: androidx.camera.video.Recorder$$ExternalSyntheticLambda5
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return Recorder.m1897$r8$lambda$M45O7_hkWfx8GwBJi61ZgT4oLU(this.f$0, null, completer);
            }
        }));
        if (isAudioEnabled() && !z) {
            this.mEncodingFutures.add(CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver(recordingRecord) { // from class: androidx.camera.video.Recorder$$ExternalSyntheticLambda6
                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                    return Recorder.m1898$r8$lambda$tLGVVgTO0zFBpDvYtj_t37rM8(this.f$0, null, completer);
                }
            }));
        }
        Futures.addCallback(Futures.allAsList(this.mEncodingFutures), new FutureCallback<List<Void>>() { // from class: androidx.camera.video.Recorder.6
            public C03096() {
            }

            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onSuccess(List<Void> list) {
                Logger.m74d("Recorder", "Encodings end successfully.");
                Recorder recorder = Recorder.this;
                recorder.finalizeInProgressRecording(recorder.mRecordingStopError, recorder.mRecordingStopErrorCause);
            }

            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onFailure(Throwable th) {
                Recorder.this.getClass();
                Preconditions.checkState(false, "In-progress recording shouldn't be null");
                Recorder.this.getClass();
                throw null;
            }
        }, CameraXExecutors.directExecutor());
    }

    /* JADX INFO: renamed from: androidx.camera.video.Recorder$3 */
    public class C03073 implements EncoderCallback {
        final /* synthetic */ CallbackToFutureAdapter.Completer val$completer;

        @Override // androidx.camera.video.internal.encoder.EncoderCallback
        public void onEncodeStart() {
        }

        public C03073(CallbackToFutureAdapter.Completer completer, RecordingRecord recordingRecord) {
            this.val$completer = completer;
        }

        @Override // androidx.camera.video.internal.encoder.EncoderCallback
        public void onEncodeStop() {
            this.val$completer.set(null);
        }

        @Override // androidx.camera.video.internal.encoder.EncoderCallback
        public void onEncodeError(EncodeException encodeException) {
            this.val$completer.setException(encodeException);
        }

        @Override // androidx.camera.video.internal.encoder.EncoderCallback
        public void onEncodedData(EncodedData encodedData) {
            boolean z;
            Recorder.this.getClass();
            Recorder recorder = Recorder.this;
            if (!recorder.mInProgressRecordingStopping) {
                EncodedData encodedData2 = recorder.mPendingFirstVideoData;
                if (encodedData2 != null) {
                    encodedData2.close();
                    Recorder.this.mPendingFirstVideoData = null;
                    z = true;
                } else {
                    z = false;
                }
                if (encodedData.isKeyFrame()) {
                    Recorder recorder2 = Recorder.this;
                    recorder2.mPendingFirstVideoData = encodedData;
                    if (!recorder2.isAudioEnabled() || !Recorder.this.mPendingAudioRingBuffer.isEmpty()) {
                        Logger.m74d("Recorder", "Received video keyframe. Starting muxer...");
                        Recorder.this.setupAndStartMuxer(null);
                        return;
                    } else if (z) {
                        Logger.m74d("Recorder", "Replaced cached video keyframe with newer keyframe.");
                        return;
                    } else {
                        Logger.m74d("Recorder", "Cached video keyframe while we wait for first audio sample before starting muxer.");
                        return;
                    }
                }
                if (z) {
                    Logger.m74d("Recorder", "Dropped cached keyframe since we have new video data and have not yet received audio data.");
                }
                Logger.m74d("Recorder", "Dropped video data since muxer has not yet started and data is not a keyframe.");
                Recorder.this.mVideoEncoder.requestKeyFrame();
                encodedData.close();
                return;
            }
            Logger.m74d("Recorder", "Drop video data since recording is stopping.");
            encodedData.close();
        }

        @Override // androidx.camera.video.internal.encoder.EncoderCallback
        public void onOutputConfigUpdate(OutputConfig outputConfig) {
            Recorder.this.mVideoOutputConfig = outputConfig;
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$M45O7_hkWfx-8GwBJi61ZgT4oLU */
    public static /* synthetic */ Object m1897$r8$lambda$M45O7_hkWfx8GwBJi61ZgT4oLU(Recorder recorder, RecordingRecord recordingRecord, CallbackToFutureAdapter.Completer completer) {
        recorder.mVideoEncoder.setEncoderCallback(new EncoderCallback(completer, recordingRecord) { // from class: androidx.camera.video.Recorder.3
            final /* synthetic */ CallbackToFutureAdapter.Completer val$completer;

            @Override // androidx.camera.video.internal.encoder.EncoderCallback
            public void onEncodeStart() {
            }

            public C03073(CallbackToFutureAdapter.Completer completer2, RecordingRecord recordingRecord2) {
                this.val$completer = completer2;
            }

            @Override // androidx.camera.video.internal.encoder.EncoderCallback
            public void onEncodeStop() {
                this.val$completer.set(null);
            }

            @Override // androidx.camera.video.internal.encoder.EncoderCallback
            public void onEncodeError(EncodeException encodeException) {
                this.val$completer.setException(encodeException);
            }

            @Override // androidx.camera.video.internal.encoder.EncoderCallback
            public void onEncodedData(EncodedData encodedData) {
                boolean z;
                Recorder.this.getClass();
                Recorder recorder2 = Recorder.this;
                if (!recorder2.mInProgressRecordingStopping) {
                    EncodedData encodedData2 = recorder2.mPendingFirstVideoData;
                    if (encodedData2 != null) {
                        encodedData2.close();
                        Recorder.this.mPendingFirstVideoData = null;
                        z = true;
                    } else {
                        z = false;
                    }
                    if (encodedData.isKeyFrame()) {
                        Recorder recorder22 = Recorder.this;
                        recorder22.mPendingFirstVideoData = encodedData;
                        if (!recorder22.isAudioEnabled() || !Recorder.this.mPendingAudioRingBuffer.isEmpty()) {
                            Logger.m74d("Recorder", "Received video keyframe. Starting muxer...");
                            Recorder.this.setupAndStartMuxer(null);
                            return;
                        } else if (z) {
                            Logger.m74d("Recorder", "Replaced cached video keyframe with newer keyframe.");
                            return;
                        } else {
                            Logger.m74d("Recorder", "Cached video keyframe while we wait for first audio sample before starting muxer.");
                            return;
                        }
                    }
                    if (z) {
                        Logger.m74d("Recorder", "Dropped cached keyframe since we have new video data and have not yet received audio data.");
                    }
                    Logger.m74d("Recorder", "Dropped video data since muxer has not yet started and data is not a keyframe.");
                    Recorder.this.mVideoEncoder.requestKeyFrame();
                    encodedData.close();
                    return;
                }
                Logger.m74d("Recorder", "Drop video data since recording is stopping.");
                encodedData.close();
            }

            @Override // androidx.camera.video.internal.encoder.EncoderCallback
            public void onOutputConfigUpdate(OutputConfig outputConfig) {
                Recorder.this.mVideoOutputConfig = outputConfig;
            }
        }, recorder.mSequentialExecutor);
        return "videoEncodingFuture";
    }

    /* JADX INFO: renamed from: $r8$lambda$tLGVVgTO0zFBp-DvY-tj_t37rM8 */
    public static /* synthetic */ Object m1898$r8$lambda$tLGVVgTO0zFBpDvYtj_t37rM8(final Recorder recorder, RecordingRecord recordingRecord, final CallbackToFutureAdapter.Completer completer) {
        recorder.getClass();
        new Object() { // from class: androidx.camera.video.Recorder.4
            final /* synthetic */ Consumer val$audioErrorConsumer;

            public C03084(Consumer consumer) {
                consumer = consumer;
            }
        };
        throw null;
    }

    /* JADX INFO: renamed from: $r8$lambda$H8VJSEfNGf3oVz0-ib8TVeQ31p4 */
    public static /* synthetic */ void m1896$r8$lambda$H8VJSEfNGf3oVz0ib8TVeQ31p4(Recorder recorder, CallbackToFutureAdapter.Completer completer, Throwable th) {
        if (recorder.mAudioErrorCause == null) {
            if (th instanceof EncodeException) {
                recorder.setAudioState(AudioState.ERROR_ENCODER);
            } else {
                recorder.setAudioState(AudioState.ERROR_SOURCE);
            }
            recorder.mAudioErrorCause = th;
            recorder.updateInProgressStatusEvent(true);
            completer.set(null);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.video.Recorder$4 */
    public class C03084 {
        final /* synthetic */ Consumer val$audioErrorConsumer;

        public C03084(Consumer consumer) {
            consumer = consumer;
        }
    }

    /* JADX INFO: renamed from: androidx.camera.video.Recorder$6 */
    public class C03096 implements FutureCallback<List<Void>> {
        public C03096() {
        }

        @Override // androidx.camera.core.impl.utils.futures.FutureCallback
        public void onSuccess(List<Void> list) {
            Logger.m74d("Recorder", "Encodings end successfully.");
            Recorder recorder = Recorder.this;
            recorder.finalizeInProgressRecording(recorder.mRecordingStopError, recorder.mRecordingStopErrorCause);
        }

        @Override // androidx.camera.core.impl.utils.futures.FutureCallback
        public void onFailure(Throwable th) {
            Recorder.this.getClass();
            Preconditions.checkState(false, "In-progress recording shouldn't be null");
            Recorder.this.getClass();
            throw null;
        }
    }

    public void stopInternal(RecordingRecord recordingRecord, long j, int i, Throwable th) {
        if (this.mInProgressRecordingStopping) {
            return;
        }
        this.mInProgressRecordingStopping = true;
        this.mRecordingStopError = i;
        this.mRecordingStopErrorCause = th;
        if (isAudioEnabled()) {
            clearPendingAudioRingBuffer();
            this.mAudioEncoder.stop(j);
        }
        EncodedData encodedData = this.mPendingFirstVideoData;
        if (encodedData != null) {
            encodedData.close();
            this.mPendingFirstVideoData = null;
        }
        if (this.mSourceState != VideoOutput.SourceState.ACTIVE_NON_STREAMING) {
            this.mSourceNonStreamingTimeout = scheduleTask(new Runnable() { // from class: androidx.camera.video.Recorder$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    Logger.m74d("Recorder", "The source didn't become non-streaming before timeout. Waited 1000ms");
                }
            }, this.mSequentialExecutor, 1000L, TimeUnit.MILLISECONDS);
        } else {
            notifyEncoderSourceStopped(this.mVideoEncoder);
        }
        this.mVideoEncoder.stop(j);
    }

    public static void notifyEncoderSourceStopped(Encoder encoder) {
        if (encoder instanceof EncoderImpl) {
            ((EncoderImpl) encoder).signalSourceStopped();
        }
    }

    private void clearPendingAudioRingBuffer() {
        while (!this.mPendingAudioRingBuffer.isEmpty()) {
            this.mPendingAudioRingBuffer.dequeue().close();
        }
    }

    private void reset() {
        if (this.mAudioEncoder != null) {
            Logger.m74d("Recorder", "Releasing audio encoder.");
            this.mAudioEncoder.release();
            this.mAudioEncoder = null;
            this.mAudioOutputConfig = null;
        }
        setAudioState(AudioState.INITIALIZING);
        resetVideo();
    }

    private void tryReleaseVideoEncoder() {
        VideoEncoderSession videoEncoderSession = this.mVideoEncoderSessionToRelease;
        if (videoEncoderSession != null) {
            Preconditions.checkState(videoEncoderSession.getVideoEncoder() == this.mVideoEncoder);
            Logger.m74d("Recorder", "Releasing video encoder: " + this.mVideoEncoder);
            this.mVideoEncoderSessionToRelease.terminateNow();
            this.mVideoEncoderSessionToRelease = null;
            this.mVideoEncoder = null;
            this.mVideoOutputConfig = null;
            setLatestSurface(null);
            return;
        }
        safeToCloseVideoEncoder();
    }

    private void onResetVideo() {
        boolean z;
        SurfaceRequest surfaceRequest;
        synchronized (this.mLock) {
            try {
                switch (this.mState.ordinal()) {
                    case 1:
                    case 2:
                        updateNonPendingState(State.CONFIGURING);
                        z = true;
                        break;
                    case 4:
                    case 5:
                    case 8:
                        if (isPersistentRecordingInProgress()) {
                            z = false;
                            break;
                        }
                    case 3:
                    case 6:
                    case 7:
                        setState(State.CONFIGURING);
                        z = true;
                        break;
                    default:
                        z = true;
                        break;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mNeedsResetBeforeNextStart = false;
        if (!z || (surfaceRequest = this.mLatestSurfaceRequest) == null || surfaceRequest.isServiced()) {
            return;
        }
        configureInternal(this.mLatestSurfaceRequest, this.mVideoSourceTimebase, false);
    }

    private void resetVideo() {
        if (this.mVideoEncoder != null) {
            Logger.m74d("Recorder", "Releasing video encoder.");
            tryReleaseVideoEncoder();
        }
        onResetVideo();
    }

    private StreamInfo.StreamState internalStateToStreamState(State state) {
        return (state == State.RECORDING || state == State.STOPPING) ? StreamInfo.StreamState.ACTIVE : StreamInfo.StreamState.INACTIVE;
    }

    public boolean isAudioEnabled() {
        return this.mAudioState == AudioState.ENABLED;
    }

    private boolean hasInsufficientStorage(long j) {
        return j < this.mRequiredFreeStorageBytes;
    }

    private boolean hasInsufficientStorageOrException(Throwable th) {
        if (StorageUtil.isStorageFullException(th)) {
            return true;
        }
        return hasInsufficientStorage(((OutputStorage) Preconditions.checkNotNull(this.mOutputStorage)).getAvailableBytes());
    }

    public void finalizeInProgressRecording(int i, Throwable th) {
        throw new AssertionError("Attempted to finalize in-progress recording, but no recording is in progress.");
    }

    public void onInProgressRecordingInternalError(RecordingRecord recordingRecord, int i, Throwable th) {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = false;
                switch (this.mState) {
                    case CONFIGURING:
                    case IDLING:
                    case ERROR:
                        throw new AssertionError("In-progress recording error occurred while in unexpected state: " + this.mState);
                    case RECORDING:
                    case PAUSED:
                        setState(State.STOPPING);
                        z = true;
                        break;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        if (z) {
            stopInternal(recordingRecord, -1L, i, th);
        }
    }

    private RecordingRecord makePendingRecordingActiveLocked(State state) {
        if (state != State.PENDING_PAUSED && state != State.PENDING_RECORDING) {
            throw new AssertionError("makePendingRecordingActiveLocked() can only be called from a pending state.");
        }
        throw new AssertionError("Pending recording should exist when in a PENDING state.");
    }

    public <T> T getObservableData(StateObservable<T> stateObservable) {
        try {
            return stateObservable.fetchData().get();
        } catch (InterruptedException | ExecutionException e) {
            Recorder$$ExternalSyntheticBUOutline0.m107m(e);
            return null;
        }
    }

    public void setState(State state) {
        if (this.mState == state) {
            throw new AssertionError("Attempted to transition to state " + state + ", but Recorder is already in state " + state);
        }
        Logger.m74d("Recorder", "Transitioning Recorder internal state: " + this.mState + " --> " + state);
        Set<State> set = PENDING_STATES;
        StreamInfo.StreamState streamStateInternalStateToStreamState = null;
        if (set.contains(state)) {
            if (!set.contains(this.mState)) {
                boolean zContains = VALID_NON_PENDING_STATES_WHILE_PENDING.contains(this.mState);
                State state2 = this.mState;
                if (!zContains) {
                    throw new AssertionError("Invalid state transition. Should not be transitioning to a PENDING state from state " + state2);
                }
                this.mNonPendingState = state2;
                streamStateInternalStateToStreamState = internalStateToStreamState(state2);
            }
        } else if (this.mNonPendingState != null) {
            this.mNonPendingState = null;
        }
        this.mState = state;
        if (streamStateInternalStateToStreamState == null) {
            streamStateInternalStateToStreamState = internalStateToStreamState(state);
        }
        this.mStreamInfo.setState(StreamInfo.m109of(this.mStreamId, streamStateInternalStateToStreamState, this.mInProgressTransformationInfo));
    }

    public void setLatestSurface(Surface surface) {
        int iHashCode;
        if (this.mLatestSurface == surface) {
            return;
        }
        this.mLatestSurface = surface;
        synchronized (this.mLock) {
            if (surface != null) {
                try {
                    iHashCode = surface.hashCode();
                } catch (Throwable th) {
                    throw th;
                }
            } else {
                iHashCode = 0;
            }
            setStreamId(iHashCode);
        }
    }

    private void setStreamId(int i) {
        if (this.mStreamId == i) {
            return;
        }
        Logger.m74d("Recorder", "Transitioning streamId: " + this.mStreamId + " --> " + i);
        this.mStreamId = i;
        this.mStreamInfo.setState(StreamInfo.m109of(i, internalStateToStreamState(this.mState), this.mInProgressTransformationInfo));
    }

    private void updateNonPendingState(State state) {
        if (!PENDING_STATES.contains(this.mState)) {
            CertificatePinner$$ExternalSyntheticBUOutline0.m953m("Can only updated non-pending state from a pending state, but state is ", this.mState);
            return;
        }
        if (!VALID_NON_PENDING_STATES_WHILE_PENDING.contains(state)) {
            throw new AssertionError("Invalid state transition. State is not a valid non-pending state while in a pending state: " + state);
        }
        if (this.mNonPendingState != state) {
            this.mNonPendingState = state;
            this.mStreamInfo.setState(StreamInfo.m109of(this.mStreamId, internalStateToStreamState(state), this.mInProgressTransformationInfo));
        }
    }

    private void restoreNonPendingState() {
        if (!PENDING_STATES.contains(this.mState)) {
            CertificatePinner$$ExternalSyntheticBUOutline0.m953m("Cannot restore non-pending state when in state ", this.mState);
        } else {
            setState(this.mNonPendingState);
        }
    }

    public void setAudioState(AudioState audioState) {
        Logger.m74d("Recorder", "Transitioning audio state: " + this.mAudioState + " --> " + audioState);
        this.mAudioState = audioState;
    }

    public static ScheduledFuture<?> scheduleTask(final Runnable runnable, final Executor executor, long j, TimeUnit timeUnit) {
        return CameraXExecutors.mainThreadExecutor().schedule(new Runnable() { // from class: androidx.camera.video.Recorder$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                executor.execute(runnable);
            }
        }, j, timeUnit);
    }

    private static int supportedMuxerFormatOrDefaultFrom(VideoValidatedEncoderProfilesProxy videoValidatedEncoderProfilesProxy, int i) {
        if (videoValidatedEncoderProfilesProxy != null) {
            int recommendedFileFormat = videoValidatedEncoderProfilesProxy.getRecommendedFileFormat();
            if (recommendedFileFormat == 1) {
                return 2;
            }
            if (recommendedFileFormat == 2) {
                return 0;
            }
            if (recommendedFileFormat == 9) {
                return 1;
            }
        }
        return i;
    }

    public static VideoCapabilities getVideoCapabilities(CameraInfo cameraInfo) {
        return getVideoCapabilitiesInternal(1, cameraInfo, 0, "video/*");
    }

    private static VideoCapabilities getVideoCapabilitiesInternal(int i, CameraInfo cameraInfo, int i2, String str) {
        CameraInfoInternal cameraInfoInternal = (CameraInfoInternal) cameraInfo;
        if ("video/*".equals(str)) {
            return new RecorderVideoCapabilities(getEncoderProfilesResolverInternal(i, cameraInfo, i2), cameraInfoInternal);
        }
        return new MimeMatchedVideoCapabilities(str, cameraInfoInternal, DEFAULT_VIDEO_ENCODER_INFO_FINDER);
    }

    @Override // androidx.camera.video.VideoOutput
    public EncoderProfilesResolver getEncoderProfilesResolver(CameraInfo cameraInfo, int i) {
        return getEncoderProfilesResolverInternal(i == 1 ? 2 : 1, cameraInfo, this.mVideoCapabilitiesSource);
    }

    private static EncoderProfilesResolver getEncoderProfilesResolverInternal(int i, CameraInfo cameraInfo, int i2) {
        return EncoderProfilesResolverFactory.getResolver(cameraInfo, i, i2, DEFAULT_VIDEO_ENCODER_INFO_FINDER);
    }

    public static final class Builder {
        private EncoderFactory mAudioEncoderFactory;
        private final MediaSpec.Builder mMediaSpecBuilder;
        private MuxerFactory mMuxerFactory;
        private OutputStorage.Factory mOutputStorageFactory;
        private long mRequiredFreeStorageBytes;
        private EncoderFactory mVideoEncoderFactory;
        private int mVideoCapabilitiesSource = 0;
        private Executor mExecutor = null;

        public Builder() {
            EncoderFactory encoderFactory = Recorder.DEFAULT_ENCODER_FACTORY;
            this.mVideoEncoderFactory = encoderFactory;
            this.mAudioEncoderFactory = encoderFactory;
            this.mMuxerFactory = Recorder.DEFAULT_MUXER_FACTORY;
            this.mOutputStorageFactory = Recorder.OUTPUT_STORAGE_FACTORY_DEFAULT;
            this.mRequiredFreeStorageBytes = -1L;
            this.mMediaSpecBuilder = Recorder.MEDIA_SPEC_DEFAULT.toBuilder();
        }

        public Builder setQualitySelector(final QualitySelector qualitySelector) {
            Preconditions.checkNotNull(qualitySelector, "The specified quality selector can't be null.");
            this.mMediaSpecBuilder.configureVideo(new Consumer() { // from class: androidx.camera.video.Recorder$Builder$$ExternalSyntheticLambda0
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    ((VideoSpec.Builder) obj).setQualitySelector(qualitySelector);
                }
            });
            return this;
        }

        public Recorder build() {
            return new Recorder(this.mExecutor, this.mMediaSpecBuilder.build(), this.mVideoCapabilitiesSource, this.mVideoEncoderFactory, this.mAudioEncoderFactory, this.mMuxerFactory, this.mOutputStorageFactory, this.mRequiredFreeStorageBytes);
        }
    }
}
