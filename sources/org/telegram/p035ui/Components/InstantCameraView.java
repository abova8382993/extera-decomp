package org.telegram.p035ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.AudioRecord;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.net.Uri;
import android.opengl.EGL14;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Property;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.camera.core.Preview;
import androidx.camera.core.SurfaceOrientedMeteringPointFactory;
import androidx.camera.core.SurfaceRequest;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.util.Consumer;
import com.exteragram.messenger.CameraType;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.VideoMessagesCamera;
import com.exteragram.messenger.camera.CameraDebugUtils;
import com.exteragram.messenger.camera.CameraXSession;
import com.exteragram.messenger.debug.DebugConfig;
import com.exteragram.messenger.debug.DebugOverlayView;
import com.exteragram.messenger.utils.system.SystemUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import kotlin.time.DurationKt;
import okhttp3.HttpUrl$$ExternalSyntheticBUOutline0;
import okio.Segment$$ExternalSyntheticBUOutline1;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.AutoDeleteMediaTask;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.DispatchQueue;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.VideoEditedInfo;
import org.telegram.messenger.camera.Camera2Session;
import org.telegram.messenger.camera.CameraController;
import org.telegram.messenger.camera.CameraInfo;
import org.telegram.messenger.camera.CameraSession;
import org.telegram.messenger.camera.CameraView$VideoRecorder$$ExternalSyntheticBUOutline0;
import org.telegram.messenger.camera.Size;
import org.telegram.messenger.video.MP4Builder;
import org.telegram.messenger.video.Mp4Movie;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Components.AlertsCreator;
import org.telegram.p035ui.Components.VideoPlayer;
import org.telegram.p035ui.Components.blur3.BlurredBackgroundDrawableViewFactory;
import org.telegram.p035ui.Components.blur3.drawable.BlurredBackgroundDrawable;
import org.telegram.p035ui.Components.blur3.drawable.color.BlurredBackgroundColorProvider;
import org.telegram.p035ui.Components.voip.CellFlickerDrawable;
import org.telegram.p035ui.Stories.recorder.DualCameraView;
import org.telegram.p035ui.Stories.recorder.FlashViews;
import org.telegram.p035ui.Stories.recorder.SliderView;
import org.telegram.p035ui.Stories.recorder.StoryEntry;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.webrtc.EglBase;
import org.webrtc.GlShader$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes7.dex */
@SuppressLint({"ViewConstructor"})
public class InstantCameraView extends FrameLayout implements NotificationCenter.NotificationCenterDelegate {
    private static final int[] ALLOW_BIG_CAMERA_WHITELIST = {285904780, -1394191079};
    public boolean WRITE_TO_FILE_IN_BACKGROUND;
    private boolean allowSendingWhileRecording;
    private float animationTranslationY;
    private AnimatorSet animatorSet;
    private Size aspectRatio;
    private boolean bothCameras;
    private final LinearLayout buttonsLayout;
    private final int buttonsSizePx;
    private CameraXSession.CameraLifecycle camLifecycle;
    private Camera2Session camera2SessionCurrent;
    private Camera2Session[] camera2Sessions;
    private InstantViewCameraContainer cameraContainer;
    private File cameraFile;
    private volatile boolean cameraReady;
    private CameraSession cameraSession;
    private final int[] cameraTexture;
    private float cameraTextureAlpha;
    private volatile boolean cameraTextureAvailable;
    private CameraGLThread cameraThread;
    private CameraXSession cameraXSession;
    private float cameraZoom;
    private boolean cancelled;
    private int currentAccount;
    private Delegate delegate;
    private TLRPC.InputEncryptedFile encryptedFile;
    private TLRPC.InputFile file;
    ValueAnimator finishZoomTransition;
    private Bitmap firstFrameThumb;
    private final FlashViews.ImageViewInvertable flashButton;
    private RLottieDrawable flashOffDrawable;
    private RLottieDrawable flashOnDrawable;
    private final FlashViews flashViews;
    private boolean flashing;
    private boolean flipAnimationInProgress;
    private boolean frontFlashing;
    private float initialCameraZoom;
    private int internalPaddingBottom;
    private boolean isFrontface;
    boolean isInPinchToZoomTouchMode;
    private boolean isMessageTransition;
    private boolean isSecretChat;
    private ItemOptions itemOptions;

    /* JADX INFO: renamed from: iv */
    private byte[] f1577iv;
    private byte[] key;
    private Bitmap lastBitmap;
    private final float[] mMVPMatrix;
    private final float[] mSTMatrix;
    boolean maybePinchToZoomTouchMode;
    private final float[] moldSTMatrix;
    private AnimatorSet muteAnimation;
    private ImageView muteImageView;
    private boolean needDrawFlickerStub;
    private final int[] oldCameraTexture;
    private Size oldTexturePreviewSize;
    private FloatBuffer oldTextureTextureBuffer;
    public boolean opened;
    private Paint paint;
    private float panTranslationY;
    private View parentView;
    private Size pictureSize;
    float pinchStartDistance;
    private int pointerId1;
    private int pointerId2;
    private final int[] position;
    private File previewFile;
    private Size[] previewSize;
    private float progress;
    private Timer progressTimer;
    private long recordPlusTime;
    private long recordStartTime;
    private long recordedTime;
    private boolean recording;
    private int recordingGuid;
    private RectF rect;
    private final Theme.ResourcesProvider resourcesProvider;
    private final ScaleGestureDetector scaleGestureDetector;
    private float scaleX;
    private float scaleY;
    private CameraInfo selectedCamera;
    private boolean setVisibilityFromPause;
    private long size;
    private volatile int surfaceIndex;
    private final FlashViews.ImageViewInvertable switchCameraButton;
    private RLottieDrawable switchCameraDrawable;
    private FloatBuffer textureBuffer;
    private BackupImageView textureOverlayView;
    private TextureView textureView;
    private int textureViewSize;
    private boolean updateTextureViewSize;
    private final boolean useCamera2;
    private FloatBuffer vertexBuffer;
    private VideoEditedInfo videoEditedInfo;
    private VideoRecorder videoEncoder;
    private VideoPlayer videoPlayer;
    private Boolean wasFlashing;
    private ValueAnimator zoomAnimator;
    private boolean zoomWas;

    /* JADX INFO: loaded from: classes3.dex */
    public interface Delegate {
        int getClassGuid();

        long getDialogId();

        View getFragmentView();

        Activity getParentActivity();

        default boolean isInScheduleMode() {
            return false;
        }

        default boolean isSecretChat() {
            return false;
        }

        void sendMedia(MediaController.PhotoEntry photoEntry, VideoEditedInfo videoEditedInfo, boolean z, int i, int i2, boolean z2, long j);
    }

    public static /* synthetic */ void $r8$lambda$tZf71xbzpSaDnel0YRXy688JHKw(SurfaceRequest.Result result) {
    }

    private boolean isCameraSessionInitiated() {
        if (this.useCamera2) {
            Camera2Session camera2Session = this.camera2SessionCurrent;
            return camera2Session != null && camera2Session.isInitiated();
        }
        CameraSession cameraSession = this.cameraSession;
        return cameraSession != null && cameraSession.isInitied();
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public InstantCameraView(Context context, Delegate delegate, final Theme.ResourcesProvider resourcesProvider, boolean z) {
        super(context);
        this.currentAccount = UserConfig.selectedAccount;
        int i = 1;
        this.isFrontface = true;
        this.position = new int[2];
        this.cameraTexture = new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE};
        this.oldCameraTexture = new int[1];
        this.cameraTextureAlpha = 1.0f;
        this.previewSize = new Size[2];
        this.aspectRatio = SharedConfig.roundCamera16to9 ? new Size(16, 9) : new Size(4, 3);
        this.useCamera2 = ExteraConfig.getCameraType() == CameraType.CAMERA_2;
        this.camera2Sessions = new Camera2Session[2];
        this.mMVPMatrix = new float[16];
        this.mSTMatrix = new float[16];
        this.moldSTMatrix = new float[16];
        this.initialCameraZoom = 0.0f;
        this.buttonsSizePx = AndroidUtilities.m1036dp(z ? 24.0f : 28.0f);
        this.WRITE_TO_FILE_IN_BACKGROUND = false;
        this.resourcesProvider = resourcesProvider;
        this.parentView = delegate.getFragmentView();
        setWillNotDraw(false);
        this.delegate = delegate;
        this.recordingGuid = delegate.getClassGuid();
        this.isSecretChat = delegate.isSecretChat();
        Paint paint = new Paint(i) { // from class: org.telegram.ui.Components.InstantCameraView.1
            @Override // android.graphics.Paint
            public void setAlpha(int i2) {
                super.setAlpha(i2);
                InstantCameraView.this.invalidate();
            }
        };
        this.paint = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeCap(Paint.Cap.ROUND);
        this.paint.setStrokeWidth(AndroidUtilities.m1036dp(3.0f));
        this.paint.setColor(-1);
        this.rect = new RectF();
        FlashViews flashViews = new FlashViews(getContext(), null, this, null);
        this.flashViews = flashViews;
        flashViews.setWarmth(ExteraConfig.getFlashWarmth());
        flashViews.setIntensity(ExteraConfig.getFlashIntensity());
        addView(flashViews.backgroundView, LayoutHelper.createFrame(-1, -1, 119));
        this.scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() { // from class: org.telegram.ui.Components.InstantCameraView.2
            @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
            public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
                if (InstantCameraView.this.cameraXSession == null) {
                    return true;
                }
                InstantCameraView.this.cameraXSession.setZoom((float) Math.pow(scaleGestureDetector.getScaleFactor(), 2.0d));
                InstantCameraView instantCameraView = InstantCameraView.this;
                instantCameraView.cameraZoom = instantCameraView.cameraXSession.getLinearZoom();
                return true;
            }

            @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
            public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
                InstantCameraView.this.finishZoom();
                super.onScaleEnd(scaleGestureDetector);
            }
        });
        InstantViewCameraContainer instantViewCameraContainer = new InstantViewCameraContainer(context) { // from class: org.telegram.ui.Components.InstantCameraView.3
            @Override // android.view.View
            public void setRotationY(float f) {
                super.setRotationY(f);
                InstantCameraView.this.invalidate();
            }

            @Override // android.view.View
            public void setAlpha(float f) {
                super.setAlpha(f);
                InstantCameraView.this.invalidate();
            }
        };
        this.cameraContainer = instantViewCameraContainer;
        instantViewCameraContainer.setOutlineProvider(new ViewOutlineProvider() { // from class: org.telegram.ui.Components.InstantCameraView.4
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view, Outline outline) {
                outline.setOval(0, 0, InstantCameraView.this.textureViewSize, InstantCameraView.this.textureViewSize);
            }
        });
        this.cameraContainer.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda7
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f$0.lambda$new$0(view, motionEvent);
            }
        });
        this.cameraContainer.setClipToOutline(true);
        this.cameraContainer.setWillNotDraw(false);
        View view = this.cameraContainer;
        int i2 = AndroidUtilities.roundPlayingMessageSize;
        addView(view, new FrameLayout.LayoutParams(i2, i2, 17));
        addView(flashViews.foregroundView, LayoutHelper.createFrame(-1, -1, 119));
        LinearLayout linearLayout = new LinearLayout(context);
        this.buttonsLayout = linearLayout;
        linearLayout.setPadding(AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(6.0f));
        linearLayout.setOrientation(0);
        addView(linearLayout, LayoutHelper.createFrame(-2, 56.0f, 83, 1.0f, 0.0f, 0.0f, 0.0f));
        FlashViews.ImageViewInvertable imageViewInvertable = new FlashViews.ImageViewInvertable(context);
        this.switchCameraButton = imageViewInvertable;
        ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER;
        imageViewInvertable.setScaleType(scaleType);
        imageViewInvertable.setContentDescription(LocaleController.getString(C2797R.string.AccDescrSwitchCamera));
        linearLayout.addView(imageViewInvertable, LayoutHelper.createLinear(44, 44));
        imageViewInvertable.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$new$2(view2);
            }
        });
        FlashViews.ImageViewInvertable imageViewInvertable2 = new FlashViews.ImageViewInvertable(context);
        this.flashButton = imageViewInvertable2;
        imageViewInvertable2.setScaleType(scaleType);
        linearLayout.addView(imageViewInvertable2, LayoutHelper.createLinear(44, 44));
        imageViewInvertable2.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$new$3(view2);
            }
        });
        imageViewInvertable2.setOnLongClickListener(new View.OnLongClickListener() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda10
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view2) {
                return this.f$0.lambda$new$7(resourcesProvider, view2);
            }
        });
        updateFlash();
        if (!z) {
            flashViews.add(imageViewInvertable);
            flashViews.add(imageViewInvertable2);
        } else if (!resourcesProvider.isDark()) {
            imageViewInvertable.setInvert(0.6f);
            imageViewInvertable2.setInvert(0.6f);
        }
        ImageView imageView = new ImageView(context);
        this.muteImageView = imageView;
        imageView.setScaleType(scaleType);
        this.muteImageView.setImageResource(C2797R.drawable.video_mute);
        this.muteImageView.setAlpha(0.0f);
        addView(this.muteImageView, LayoutHelper.createFrame(48, 48, 17));
        final Paint paint2 = new Paint(1);
        paint2.setColor(ColorUtils.setAlphaComponent(-16777216, 40));
        BackupImageView backupImageView = new BackupImageView(getContext()) { // from class: org.telegram.ui.Components.InstantCameraView.7
            CellFlickerDrawable flickerDrawable = new CellFlickerDrawable();

            @Override // org.telegram.p035ui.Components.BackupImageView, android.view.View
            public void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                if (InstantCameraView.this.needDrawFlickerStub) {
                    this.flickerDrawable.setParentWidth(InstantCameraView.this.textureViewSize);
                    RectF rectF = AndroidUtilities.rectTmp;
                    rectF.set(0.0f, 0.0f, InstantCameraView.this.textureViewSize, InstantCameraView.this.textureViewSize);
                    float fWidth = rectF.width() / 2.0f;
                    canvas.drawRoundRect(rectF, fWidth, fWidth, paint2);
                    rectF.inset(AndroidUtilities.m1036dp(1.0f), AndroidUtilities.m1036dp(1.0f));
                    this.flickerDrawable.draw(canvas, rectF, fWidth, null);
                    invalidate();
                }
            }
        };
        this.textureOverlayView = backupImageView;
        int i3 = AndroidUtilities.roundPlayingMessageSize;
        addView(backupImageView, new FrameLayout.LayoutParams(i3, i3, 17));
        if (DebugConfig.getDebugCameraMetrics()) {
            DebugOverlayView debugOverlayView = new DebugOverlayView(context);
            debugOverlayView.setDataSource(new DebugOverlayView.DataSource() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda11
                @Override // com.exteragram.messenger.debug.DebugOverlayView.DataSource
                public final void build(DebugOverlayView.ContentBuilder contentBuilder) {
                    this.f$0.populateCameraDebugOverlay(contentBuilder);
                }
            });
            addView(debugOverlayView, DebugOverlayView.createLayoutParams());
        }
        this.setVisibilityFromPause = false;
        setVisibility(4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$0(View view, MotionEvent motionEvent) {
        CameraXSession cameraXSession;
        if (motionEvent.getAction() != 0 || (cameraXSession = this.cameraXSession) == null) {
            return false;
        }
        cameraXSession.focusToPoint(motionEvent.getX(), motionEvent.getY());
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(View view) {
        CameraXSession cameraXSession;
        if (isCameraReady()) {
            CameraType cameraType = ExteraConfig.getCameraType();
            CameraType cameraType2 = CameraType.CAMERA_X;
            final boolean z = cameraType == cameraType2 && (cameraXSession = this.cameraXSession) != null && cameraXSession.isDualMode();
            if (ExteraConfig.getCameraType() != cameraType2) {
                if (!this.bothCameras) {
                    switchCamera();
                }
            } else if (!z) {
                switchCameraX();
            }
            RLottieDrawable rLottieDrawable = this.switchCameraDrawable;
            if (rLottieDrawable != null) {
                rLottieDrawable.setCurrentFrame(0);
                this.switchCameraDrawable.start();
            }
            if (ExteraConfig.getCameraType() != cameraType2 || z) {
                this.flipAnimationInProgress = true;
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                valueAnimatorOfFloat.setDuration(450L);
                valueAnimatorOfFloat.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                final boolean[] zArr = new boolean[1];
                final Runnable runnable = new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda13
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$new$1(z);
                    }
                };
                this.cameraContainer.setCameraDistance(r6.getMeasuredHeight() * 8.0f);
                this.textureOverlayView.setCameraDistance(r6.getMeasuredHeight() * 8.0f);
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.InstantCameraView.5
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        if (fFloatValue > 0.5f) {
                            boolean[] zArr2 = zArr;
                            if (!zArr2[0]) {
                                zArr2[0] = true;
                                runnable.run();
                            }
                        }
                        if (fFloatValue >= 0.5f) {
                            fFloatValue -= 1.0f;
                        }
                        float f = fFloatValue * 180.0f;
                        InstantCameraView.this.cameraContainer.setRotationY(f);
                        InstantCameraView.this.textureOverlayView.setRotationY(f);
                    }
                });
                valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.InstantCameraView.6
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        boolean[] zArr2 = zArr;
                        if (!zArr2[0]) {
                            zArr2[0] = true;
                            runnable.run();
                        }
                        InstantCameraView.this.cameraContainer.setRotationY(0.0f);
                        InstantCameraView.this.textureOverlayView.setRotationY(0.0f);
                        InstantCameraView.this.flipAnimationInProgress = false;
                        InstantCameraView.this.invalidate();
                    }
                });
                valueAnimatorOfFloat.start();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(boolean z) {
        if (this.bothCameras && ExteraConfig.getCameraType() != CameraType.CAMERA_X) {
            switchCamera();
        } else if (z) {
            switchCameraX();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3(View view) {
        this.flashing = !this.flashing;
        updateFlash();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$7(Theme.ResourcesProvider resourcesProvider, View view) {
        if (!this.isFrontface || !isCameraReady()) {
            return false;
        }
        final boolean z = this.flashing;
        if (!z) {
            this.flashing = true;
            updateFlash();
        }
        this.itemOptions = ItemOptions.makeOptions(this, resourcesProvider, this.flashButton).addView(new SliderView(getContext(), 1).setValue(ExteraConfig.getFlashWarmth()).setOnValueChange(new Utilities.Callback() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda14
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$new$4((Float) obj);
            }
        })).addSpaceGap().addView(new SliderView(getContext(), 2).setMinMax(0.5f, 1.0f).setValue(ExteraConfig.getFlashIntensity()).setOnValueChange(new Utilities.Callback() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda15
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$new$5((Float) obj);
            }
        })).setOnDismiss(new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$6(z);
            }
        }).setDimAlpha(50).setGravity(5).translate(AndroidUtilities.m1036dp(46.0f), AndroidUtilities.m1036dp(4.0f)).setBackgroundColor(-1155851493).show();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$4(Float f) {
        ExteraConfig.setFlashWarmth(f.floatValue());
        this.flashViews.setWarmth(f.floatValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$5(Float f) {
        ExteraConfig.setFlashIntensity(f.floatValue());
        this.flashViews.setIntensity(f.floatValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$6(boolean z) {
        if (z) {
            return;
        }
        this.flashing = false;
        updateFlash();
    }

    public void setButtonsBackground(BlurredBackgroundDrawableViewFactory blurredBackgroundDrawableViewFactory, BlurredBackgroundColorProvider blurredBackgroundColorProvider) {
        BlurredBackgroundDrawable blurredBackgroundDrawableCreate = blurredBackgroundDrawableViewFactory.create(this.buttonsLayout, blurredBackgroundColorProvider);
        blurredBackgroundDrawableCreate.setPadding(AndroidUtilities.m1036dp(6.0f));
        blurredBackgroundDrawableCreate.setRadius(AndroidUtilities.m1036dp(21.0f));
        this.buttonsLayout.setBackground(blurredBackgroundDrawableCreate);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFlash() {
        CameraXSession cameraXSession;
        boolean z = this.flashing && this.recording && this.isFrontface;
        if (this.frontFlashing != z) {
            this.frontFlashing = z;
            FlashViews flashViews = this.flashViews;
            if (z) {
                flashViews.flashIn(null);
            } else {
                flashViews.flashOut();
            }
        }
        boolean z2 = this.flashing && this.recording;
        if (ExteraConfig.getCameraType() != CameraType.CAMERA_X) {
            if (this.useCamera2) {
                Camera2Session camera2Session = this.camera2SessionCurrent;
                if (camera2Session != null) {
                    camera2Session.setFlash(z2 && !this.isFrontface);
                }
            } else {
                CameraSession cameraSession = this.cameraSession;
                if (cameraSession != null) {
                    cameraSession.setTorchEnabled(z2 && !this.isFrontface);
                }
            }
        } else if (isCameraReady() && (cameraXSession = this.cameraXSession) != null) {
            cameraXSession.setTorchEnabled(z2);
        }
        if (this.flashButton != null) {
            Boolean bool = this.wasFlashing;
            if (bool == null || bool.booleanValue() != this.flashing) {
                this.flashButton.setContentDescription(LocaleController.getString(this.flashing ? C2797R.string.AccDescrCameraFlashOff : C2797R.string.AccDescrCameraFlashOn));
                if (!this.flashing) {
                    if (this.flashOnDrawable == null) {
                        int i = C2797R.raw.roundcamera_flash_on;
                        int i2 = this.buttonsSizePx;
                        RLottieDrawable rLottieDrawable = new RLottieDrawable(i, "roundcamera_flash_on", i2, i2);
                        this.flashOnDrawable = rLottieDrawable;
                        rLottieDrawable.setCallback(this.flashButton);
                    }
                    this.flashButton.setImageDrawable(this.flashOnDrawable);
                    Boolean bool2 = this.wasFlashing;
                    RLottieDrawable rLottieDrawable2 = this.flashOnDrawable;
                    if (bool2 == null) {
                        rLottieDrawable2.setCurrentFrame(rLottieDrawable2.getFramesCount() - 1);
                    } else {
                        rLottieDrawable2.setCurrentFrame(0);
                        this.flashOnDrawable.start();
                    }
                } else {
                    if (this.flashOffDrawable == null) {
                        int i3 = C2797R.raw.roundcamera_flash_off;
                        int i4 = this.buttonsSizePx;
                        RLottieDrawable rLottieDrawable3 = new RLottieDrawable(i3, "roundcamera_flash_off", i4, i4);
                        this.flashOffDrawable = rLottieDrawable3;
                        rLottieDrawable3.setCallback(this.flashButton);
                    }
                    this.flashButton.setImageDrawable(this.flashOffDrawable);
                    Boolean bool3 = this.wasFlashing;
                    RLottieDrawable rLottieDrawable4 = this.flashOffDrawable;
                    if (bool3 == null) {
                        rLottieDrawable4.setCurrentFrame(rLottieDrawable4.getFramesCount() - 1);
                    } else {
                        rLottieDrawable4.setCurrentFrame(0);
                        this.flashOffDrawable.start();
                    }
                }
                this.wasFlashing = Boolean.valueOf(this.flashing);
            }
        }
    }

    public void setInternalPadding(int i) {
        this.internalPaddingBottom = i;
        setPadding(0, 0, 0, i);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int i3;
        if (this.updateTextureViewSize) {
            if (View.MeasureSpec.getSize(i2) - getPaddingBottom() > View.MeasureSpec.getSize(i) * 1.3f) {
                i3 = AndroidUtilities.roundPlayingMessageSize;
            } else {
                i3 = AndroidUtilities.roundMessageSize;
            }
            if (i3 != this.textureViewSize) {
                this.textureViewSize = i3;
                ViewGroup.LayoutParams layoutParams = this.textureOverlayView.getLayoutParams();
                ViewGroup.LayoutParams layoutParams2 = this.textureOverlayView.getLayoutParams();
                int i4 = this.textureViewSize;
                layoutParams2.height = i4;
                layoutParams.width = i4;
                ViewGroup.LayoutParams layoutParams3 = this.cameraContainer.getLayoutParams();
                ViewGroup.LayoutParams layoutParams4 = this.cameraContainer.getLayoutParams();
                int i5 = this.textureViewSize;
                layoutParams4.height = i5;
                layoutParams3.width = i5;
                ((FrameLayout.LayoutParams) this.muteImageView.getLayoutParams()).topMargin = (this.textureViewSize / 2) - AndroidUtilities.m1036dp(24.0f);
                this.textureOverlayView.setRoundRadius(this.textureViewSize / 2);
                this.cameraContainer.invalidateOutline();
            }
            this.updateTextureViewSize = false;
        }
        super.onMeasure(i, i2);
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), TLObject.FLAG_30);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), TLObject.FLAG_30);
        this.flashViews.backgroundView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.flashViews.foregroundView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
    }

    private boolean checkPointerIds(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() < 2) {
            return false;
        }
        if (this.pointerId1 == motionEvent.getPointerId(0) && this.pointerId2 == motionEvent.getPointerId(1)) {
            return true;
        }
        return this.pointerId1 == motionEvent.getPointerId(1) && this.pointerId2 == motionEvent.getPointerId(0);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (getVisibility() != 0) {
            this.animationTranslationY = getMeasuredHeight() / 2.0f;
            updateTranslationY();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.fileUploaded);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.fileUploaded);
        FlashViews flashViews = this.flashViews;
        if (flashViews != null) {
            flashViews.flashOut();
        }
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.fileUploaded) {
            String str = (String) objArr[0];
            File file = this.cameraFile;
            if (file == null || !file.getAbsolutePath().equals(str)) {
                return;
            }
            this.file = (TLRPC.InputFile) objArr[1];
            this.encryptedFile = (TLRPC.InputEncryptedFile) objArr[2];
            this.size = ((Long) objArr[5]).longValue();
            if (this.encryptedFile != null) {
                this.key = (byte[]) objArr[3];
                this.f1577iv = (byte[]) objArr[4];
            }
        }
    }

    public void destroy(boolean z) {
        if (ExteraConfig.getCameraType() != CameraType.CAMERA_X) {
            if (!this.useCamera2) {
                CameraSession cameraSession = this.cameraSession;
                if (cameraSession != null) {
                    cameraSession.destroy();
                    CameraController.getInstance().close(this.cameraSession, !z ? new CountDownLatch(1) : null, null);
                    return;
                }
                return;
            }
            int i = 0;
            while (true) {
                Camera2Session[] camera2SessionArr = this.camera2Sessions;
                if (i >= camera2SessionArr.length) {
                    return;
                }
                Camera2Session camera2Session = camera2SessionArr[i];
                if (camera2Session != null) {
                    camera2Session.destroy(z);
                    this.camera2Sessions[i] = null;
                }
                i++;
            }
        } else {
            try {
                this.cameraXSession.closeCamera();
            } catch (Exception unused) {
            }
        }
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        float x = this.cameraContainer.getX();
        float y = this.cameraContainer.getY();
        this.rect.set(x - AndroidUtilities.m1036dp(8.0f), y - AndroidUtilities.m1036dp(8.0f), x + this.cameraContainer.getMeasuredWidth() + AndroidUtilities.m1036dp(8.0f), y + this.cameraContainer.getMeasuredHeight() + AndroidUtilities.m1036dp(8.0f));
        if (this.recording) {
            long jCurrentTimeMillis = (System.currentTimeMillis() - this.recordStartTime) + this.recordPlusTime;
            this.recordedTime = jCurrentTimeMillis;
            this.progress = Math.min(1.0f, jCurrentTimeMillis / 60000.0f);
            invalidate();
        }
        if (this.progress != 0.0f) {
            canvas.save();
            if (!this.flipAnimationInProgress) {
                canvas.scale(this.cameraContainer.getScaleX(), this.cameraContainer.getScaleY(), this.rect.centerX(), this.rect.centerY());
            }
            canvas.drawArc(this.rect, -90.0f, this.progress * 360.0f, false, this.paint);
            canvas.restore();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void populateCameraDebugOverlay(DebugOverlayView.ContentBuilder contentBuilder) {
        DebugOverlayView.ContentBuilder contentBuilderM253kv = contentBuilder.title("InstantCamera").m253kv("front", Boolean.valueOf(this.isFrontface)).m253kv("recording", Boolean.valueOf(this.recording)).m253kv("ready", Boolean.valueOf(this.cameraReady)).m253kv("dual", Boolean.valueOf(this.bothCameras)).m253kv("surface", Integer.valueOf(this.surfaceIndex)).m253kv("flash", Boolean.valueOf(this.flashing)).m253kv("frontFlash", Boolean.valueOf(this.frontFlashing)).m253kv("texture", Boolean.valueOf(this.cameraTextureAvailable));
        StringBuilder sb = new StringBuilder("preview0=");
        boolean z = false;
        sb.append(CameraDebugUtils.formatCameraSize(this.previewSize[0]));
        contentBuilderM253kv.line(sb.toString()).line("preview1=" + CameraDebugUtils.formatCameraSize(this.previewSize[1]));
        if (ExteraConfig.getCameraType() == CameraType.CAMERA_X) {
            contentBuilder.section("CameraX");
            CameraXSession cameraXSession = this.cameraXSession;
            if (cameraXSession == null) {
                contentBuilder.line("session=null");
                return;
            }
            DebugOverlayView.ContentBuilder contentBuilderLine = contentBuilder.m253kv("cx.init", Boolean.valueOf(cameraXSession.isInitiated())).m253kv("cx.ready", Boolean.valueOf(this.cameraXSession.isReady())).m253kv("cx.dual", Boolean.valueOf(this.cameraXSession.isDualMode())).m253kv("cx.front", Boolean.valueOf(this.cameraXSession.isFrontface())).line("cx.zoom=" + CameraDebugUtils.safeCameraXZoomRatio(this.cameraXSession) + " [" + CameraDebugUtils.safeCameraXMinZoomRatio(this.cameraXSession) + ".." + CameraDebugUtils.safeCameraXMaxZoomRatio(this.cameraXSession) + "]");
            StringBuilder sb2 = new StringBuilder("cx.fpsRanges=");
            sb2.append(CameraDebugUtils.getCameraXSupportedFpsRanges(this.cameraXSession));
            DebugOverlayView.ContentBuilder contentBuilderLine2 = contentBuilderLine.line(sb2.toString());
            StringBuilder sb3 = new StringBuilder("bound=");
            sb3.append(CameraDebugUtils.getCameraXBoundCameraList(this.cameraXSession));
            contentBuilderLine2.line(sb3.toString()).line("avail=" + CameraDebugUtils.getCameraXAvailableCameraList(this.cameraXSession)).line("phys=" + CameraDebugUtils.getCameraXPhysicalCameraList(this.cameraXSession));
            return;
        }
        if (this.useCamera2) {
            contentBuilder.section("Camera2");
            Camera2Session camera2Session = this.camera2SessionCurrent;
            if (camera2Session == null) {
                contentBuilder.line("session=null");
                return;
            }
            DebugOverlayView.ContentBuilder contentBuilderLine3 = contentBuilder.m253kv("c2.init", Boolean.valueOf(camera2Session.isInitiated())).m253kv("c2.flash", Boolean.valueOf(this.camera2SessionCurrent.getFlash())).m253kv("c2.both", Boolean.valueOf(this.bothCameras)).line("c2.zoom=" + this.camera2SessionCurrent.getZoom() + " [" + this.camera2SessionCurrent.getMinZoom() + ".." + this.camera2SessionCurrent.getMaxZoom() + "]");
            StringBuilder sb4 = new StringBuilder("c2.fpsRanges=");
            sb4.append(CameraDebugUtils.getCamera2SupportedFpsRanges(this.camera2SessionCurrent));
            DebugOverlayView.ContentBuilder contentBuilderLine4 = contentBuilderLine3.line(sb4.toString());
            StringBuilder sb5 = new StringBuilder("ids=");
            sb5.append(CameraDebugUtils.getCamera2CameraList(getContext()));
            contentBuilderLine4.line(sb5.toString());
            return;
        }
        DebugOverlayView.ContentBuilder contentBuilderSection = contentBuilder.section("Camera1");
        CameraSession cameraSession = this.cameraSession;
        if (cameraSession != null && cameraSession.isInitied()) {
            z = true;
        }
        contentBuilderSection.m253kv("c1.init", Boolean.valueOf(z)).m253kv("c1.zoom", Float.valueOf(this.cameraZoom)).line("c1.fpsRanges=" + CameraDebugUtils.getLegacySupportedFpsRanges(this.cameraSession)).line("ids=" + CameraDebugUtils.getLegacyCameraList());
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        this.buttonsLayout.setAlpha(0.0f);
        this.cameraContainer.setAlpha(0.0f);
        this.textureOverlayView.setAlpha(0.0f);
        this.muteImageView.setAlpha(0.0f);
        this.muteImageView.setScaleX(1.0f);
        this.muteImageView.setScaleY(1.0f);
        this.cameraContainer.setScaleX(this.setVisibilityFromPause ? 1.0f : 0.1f);
        this.cameraContainer.setScaleY(this.setVisibilityFromPause ? 1.0f : 0.1f);
        this.textureOverlayView.setScaleX(this.setVisibilityFromPause ? 1.0f : 0.1f);
        this.textureOverlayView.setScaleY(this.setVisibilityFromPause ? 1.0f : 0.1f);
        if (this.cameraContainer.getMeasuredWidth() != 0) {
            this.cameraContainer.setPivotX(r0.getMeasuredWidth() / 2);
            this.cameraContainer.setPivotY(r0.getMeasuredHeight() / 2);
            this.textureOverlayView.setPivotX(r0.getMeasuredWidth() / 2);
            this.textureOverlayView.setPivotY(r0.getMeasuredHeight() / 2);
        }
        try {
            if (i == 0) {
                ((Activity) getContext()).getWindow().addFlags(128);
            } else {
                ((Activity) getContext()).getWindow().clearFlags(128);
            }
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
    }

    public void togglePause() {
        if (this.recording) {
            this.cancelled = this.recordedTime < 800;
            this.recording = false;
            updateFlash();
            if (this.cameraThread != null) {
                NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.recordStopped, Integer.valueOf(this.recordingGuid), Integer.valueOf(this.cancelled ? 4 : 2));
                saveLastCameraBitmap();
                CameraGLThread cameraGLThread = this.cameraThread;
                boolean z = this.cancelled;
                cameraGLThread.shutdown(z ? 0 : 2, true, 0, 0, z ? 0 : -2, 0L);
                this.cameraThread = null;
            }
            if (this.cancelled) {
                NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.audioRecordTooShort, Integer.valueOf(this.recordingGuid), Boolean.TRUE, Integer.valueOf((int) this.recordedTime));
                startAnimation(false, false);
                MediaController.getInstance().requestRecordAudioFocus(false);
                return;
            }
            this.videoEncoder.pause();
            return;
        }
        VideoRecorder videoRecorder = this.videoEncoder;
        if (videoRecorder != null) {
            videoRecorder.resume();
            hideCamera(false);
            VideoPlayer videoPlayer = this.videoPlayer;
            if (videoPlayer != null) {
                videoPlayer.releasePlayer(true);
                this.videoPlayer = null;
            }
            showCamera(true);
            try {
                performHapticFeedback(3, 2);
            } catch (Exception unused) {
            }
            AndroidUtilities.lockOrientation(this.delegate.getParentActivity());
            invalidate();
            NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.recordResumed, new Object[0]);
        }
    }

    public void setFrontface(boolean z) {
        this.isFrontface = z;
    }

    public boolean isPaused() {
        return !this.recording;
    }

    public void showCamera(boolean z) {
        if (this.textureView != null) {
            return;
        }
        this.camLifecycle = new CameraXSession.CameraLifecycle();
        if (this.switchCameraDrawable == null) {
            int i = C2797R.raw.roundcamera_flip;
            int i2 = this.buttonsSizePx;
            RLottieDrawable rLottieDrawable = new RLottieDrawable(i, "roundcamera_flip", i2, i2);
            this.switchCameraDrawable = rLottieDrawable;
            rLottieDrawable.setCurrentFrame(0);
            this.switchCameraDrawable.setCallback(this.switchCameraButton);
        }
        this.switchCameraButton.setImageDrawable(this.switchCameraDrawable);
        this.textureOverlayView.setAlpha(1.0f);
        this.textureOverlayView.invalidate();
        if (this.lastBitmap == null) {
            try {
                this.lastBitmap = BitmapFactory.decodeFile(new File(ApplicationLoader.getFilesDirFixed(), "icthumb.jpg").getAbsolutePath());
            } catch (Throwable unused) {
            }
        }
        Bitmap bitmap = this.lastBitmap;
        BackupImageView backupImageView = this.textureOverlayView;
        if (bitmap != null) {
            backupImageView.setImageBitmap(bitmap);
        } else {
            backupImageView.setImageResource(C2797R.drawable.icplaceholder);
        }
        this.cameraReady = false;
        this.selectedCamera = null;
        if (!z) {
            if (ExteraConfig.getVideoMessagesCamera() != VideoMessagesCamera.ASK) {
                this.isFrontface = ExteraConfig.getVideoMessagesCamera() == VideoMessagesCamera.FRONT;
            }
            updateFlash();
            this.recordedTime = 0L;
            this.progress = 0.0f;
        }
        this.cancelled = false;
        this.file = null;
        this.encryptedFile = null;
        this.key = null;
        this.f1577iv = null;
        this.needDrawFlickerStub = true;
        if (initCamera()) {
            if (MediaController.getInstance().getPlayingMessageObject() != null) {
                if (MediaController.getInstance().getPlayingMessageObject().isVideo() || MediaController.getInstance().getPlayingMessageObject().isRoundVideo()) {
                    MediaController.getInstance().cleanupPlayer(true, true);
                } else if (SharedConfig.pauseMusicOnRecord) {
                    MediaController.getInstance().pauseByRewind();
                }
            }
            if (!z) {
                this.cameraFile = new File(FileLoader.getDirectory(3), System.currentTimeMillis() + "_" + SharedConfig.getLastLocalId() + ".mp4") { // from class: org.telegram.ui.Components.InstantCameraView.8
                    @Override // java.io.File
                    public boolean delete() {
                        if (BuildVars.LOGS_ENABLED) {
                            FileLog.m1046e("delete camera file");
                        }
                        return super.delete();
                    }
                };
            }
            SharedConfig.saveConfig();
            AutoDeleteMediaTask.lockFile(this.cameraFile);
            if (BuildVars.LOGS_ENABLED) {
                FileLog.m1045d("InstantCamera show round camera " + this.cameraFile.getAbsolutePath());
            }
            if (this.useCamera2) {
                boolean zRoundDualAvailableStatic = DualCameraView.roundDualAvailableStatic(getContext());
                this.bothCameras = zRoundDualAvailableStatic;
                if (zRoundDualAvailableStatic) {
                    int i3 = 0;
                    while (i3 < 2) {
                        Camera2Session[] camera2SessionArr = this.camera2Sessions;
                        if (camera2SessionArr[i3] == null) {
                            camera2SessionArr[i3] = Camera2Session.create(i3 == 0, SystemUtils.getRoundVideoResolution(), SystemUtils.getRoundVideoResolution());
                            Camera2Session camera2Session = this.camera2Sessions[i3];
                            if (camera2Session != null) {
                                camera2Session.setRecordingVideo(true);
                                this.previewSize[i3] = new Size(this.camera2Sessions[i3].getPreviewWidth(), this.camera2Sessions[i3].getPreviewHeight());
                            }
                        }
                        i3++;
                    }
                    updateFlash();
                    Camera2Session[] camera2SessionArr2 = this.camera2Sessions;
                    boolean z2 = this.isFrontface;
                    Camera2Session camera2Session2 = camera2SessionArr2[!z2 ? 1 : 0];
                    this.camera2SessionCurrent = camera2Session2;
                    if (camera2Session2 != null && camera2SessionArr2[z2 ? 1 : 0] == null) {
                        this.bothCameras = false;
                    }
                    if (this.bothCameras) {
                        this.surfaceIndex = !z2 ? 1 : 0;
                    }
                    if (this.camera2SessionCurrent == null) {
                        return;
                    }
                } else {
                    Camera2Session[] camera2SessionArr3 = this.camera2Sessions;
                    boolean z3 = this.isFrontface;
                    int i4 = !z3 ? 1 : 0;
                    Camera2Session camera2SessionCreate = Camera2Session.create(z3, SystemUtils.getRoundVideoResolution(), SystemUtils.getRoundVideoResolution());
                    camera2SessionArr3[i4] = camera2SessionCreate;
                    this.camera2SessionCurrent = camera2SessionCreate;
                    if (camera2SessionCreate == null) {
                        return;
                    }
                    camera2SessionCreate.setRecordingVideo(true);
                    this.previewSize[0] = new Size(this.camera2SessionCurrent.getPreviewWidth(), this.camera2SessionCurrent.getPreviewHeight());
                }
            } else if (ExteraConfig.getCameraType() == CameraType.CAMERA_X) {
                boolean z4 = DualCameraView.roundDualAvailableStatic(getContext()) && CameraXSession.isSeamlessSwitchingAvailable(getContext());
                this.bothCameras = z4;
                if (z4) {
                    this.surfaceIndex = !this.isFrontface ? 1 : 0;
                }
                if (this.previewSize[0] == null) {
                    int roundVideoResolution = SystemUtils.getRoundVideoResolution();
                    this.previewSize[0] = new Size(roundVideoResolution, roundVideoResolution);
                }
                Size[] sizeArr = this.previewSize;
                if (sizeArr[1] == null) {
                    sizeArr[1] = sizeArr[0];
                }
            }
            TextureView textureView = new TextureView(getContext());
            this.textureView = textureView;
            textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() { // from class: org.telegram.ui.Components.InstantCameraView.9
                @Override // android.view.TextureView.SurfaceTextureListener
                public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
                }

                @Override // android.view.TextureView.SurfaceTextureListener
                public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i5, int i6) {
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.m1045d("InstantCamera camera surface available");
                    }
                    if (InstantCameraView.this.cameraThread != null || surfaceTexture == null || InstantCameraView.this.cancelled) {
                        return;
                    }
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.m1045d("InstantCamera start create thread");
                    }
                    InstantCameraView.this.cameraThread = InstantCameraView.this.new CameraGLThread(surfaceTexture, i5, i6);
                }

                @Override // android.view.TextureView.SurfaceTextureListener
                public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i5, int i6) {
                    if (InstantCameraView.this.cameraThread != null) {
                        InstantCameraView.this.cameraThread.surfaceWidth = i5;
                        InstantCameraView.this.cameraThread.surfaceHeight = i6;
                        InstantCameraView.this.cameraThread.updateScale();
                    }
                }

                @Override // android.view.TextureView.SurfaceTextureListener
                public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                    if (InstantCameraView.this.cameraThread != null) {
                        InstantCameraView.this.cameraThread.shutdown(0, true, 0, 0, 0, 0L);
                        InstantCameraView.this.cameraThread = null;
                    }
                    CameraType cameraType = ExteraConfig.getCameraType();
                    CameraType cameraType2 = CameraType.CAMERA_X;
                    InstantCameraView instantCameraView = InstantCameraView.this;
                    if (cameraType != cameraType2) {
                        if (instantCameraView.useCamera2) {
                            for (int i5 = 0; i5 < InstantCameraView.this.camera2Sessions.length; i5++) {
                                if (InstantCameraView.this.camera2Sessions[i5] != null) {
                                    InstantCameraView.this.camera2Sessions[i5].destroy(false);
                                    InstantCameraView.this.camera2Sessions[i5] = null;
                                }
                            }
                            return true;
                        }
                        if (InstantCameraView.this.cameraSession == null) {
                            return true;
                        }
                        CameraController.getInstance().close(InstantCameraView.this.cameraSession, null, null);
                        return true;
                    }
                    try {
                        instantCameraView.cameraXSession.closeCamera();
                        return true;
                    } catch (Exception unused2) {
                        return true;
                    }
                }
            });
            this.cameraContainer.addView(this.textureView, LayoutHelper.createFrame(-1, -1.0f));
            this.updateTextureViewSize = true;
            this.setVisibilityFromPause = z;
            setVisibility(0);
            startAnimation(true, z);
            MediaController.getInstance().requestRecordAudioFocus(true);
        }
    }

    public InstantViewCameraContainer getCameraContainer() {
        return this.cameraContainer;
    }

    public void startAnimation(boolean z, final boolean z2) {
        AnimatorSet animatorSet = this.animatorSet;
        if (animatorSet != null) {
            animatorSet.removeAllListeners();
            this.animatorSet.cancel();
        }
        PipRoundVideoView pipRoundVideoView = PipRoundVideoView.getInstance();
        if (pipRoundVideoView != null) {
            pipRoundVideoView.showTemporary(!z);
        }
        if (z && !this.opened) {
            this.cameraContainer.setTranslationX(0.0f);
            this.textureOverlayView.setTranslationX(0.0f);
            this.animationTranslationY = z2 ? 0.0f : getMeasuredHeight() / 2.0f;
            updateTranslationY();
        }
        this.opened = z;
        View view = this.parentView;
        if (view != null) {
            view.invalidate();
        }
        this.animatorSet = new AnimatorSet();
        float fM1036dp = (z || this.recordedTime <= 300) ? 0.0f : AndroidUtilities.m1036dp(24.0f) - (getMeasuredWidth() / 2.0f);
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(z ? 1.0f : 0.0f, z ? 0.0f : 1.0f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda12
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$startAnimation$8(z2, valueAnimator);
            }
        });
        AnimatorSet animatorSet2 = this.animatorSet;
        LinearLayout linearLayout = this.buttonsLayout;
        float[] fArr = {z ? 1.0f : 0.0f};
        Property property = View.ALPHA;
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(linearLayout, (Property<LinearLayout, Float>) property, fArr);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.muteImageView, (Property<ImageView, Float>) property, 0.0f);
        ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(this.paint, AnimationProperties.PAINT_ALPHA, z ? 255 : 0);
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(this.cameraContainer, (Property<InstantViewCameraContainer, Float>) property, z ? 1.0f : 0.0f);
        InstantViewCameraContainer instantViewCameraContainer = this.cameraContainer;
        float f = z ? 1.0f : 0.1f;
        Property property2 = View.SCALE_X;
        ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(instantViewCameraContainer, (Property<InstantViewCameraContainer, Float>) property2, f);
        InstantViewCameraContainer instantViewCameraContainer2 = this.cameraContainer;
        float f2 = z ? 1.0f : 0.1f;
        Property property3 = View.SCALE_Y;
        ObjectAnimator objectAnimatorOfFloat5 = ObjectAnimator.ofFloat(instantViewCameraContainer2, (Property<InstantViewCameraContainer, Float>) property3, f2);
        Property property4 = View.TRANSLATION_X;
        animatorSet2.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2, objectAnimatorOfInt, objectAnimatorOfFloat3, objectAnimatorOfFloat4, objectAnimatorOfFloat5, ObjectAnimator.ofFloat(this.cameraContainer, (Property<InstantViewCameraContainer, Float>) property4, fM1036dp), ObjectAnimator.ofFloat(this.textureOverlayView, (Property<BackupImageView, Float>) property, z ? 1.0f : 0.0f), ObjectAnimator.ofFloat(this.textureOverlayView, (Property<BackupImageView, Float>) property2, z ? 1.0f : 0.1f), ObjectAnimator.ofFloat(this.textureOverlayView, (Property<BackupImageView, Float>) property3, z ? 1.0f : 0.1f), ObjectAnimator.ofFloat(this.textureOverlayView, (Property<BackupImageView, Float>) property4, fM1036dp), valueAnimatorOfFloat);
        if (!z) {
            this.animatorSet.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.InstantCameraView.10
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (animator.equals(InstantCameraView.this.animatorSet)) {
                        InstantCameraView.this.hideCamera(true);
                        InstantCameraView.this.setVisibilityFromPause = false;
                        InstantCameraView.this.setVisibility(4);
                    }
                }
            });
        } else {
            setTranslationX(0.0f);
        }
        this.animatorSet.setDuration(180L);
        this.animatorSet.setInterpolator(new DecelerateInterpolator());
        this.animatorSet.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startAnimation$8(boolean z, ValueAnimator valueAnimator) {
        this.animationTranslationY = z ? 0.0f : (getMeasuredHeight() / 2.0f) * ((Float) valueAnimator.getAnimatedValue()).floatValue();
        updateTranslationY();
    }

    private void updateTranslationY() {
        this.textureOverlayView.setTranslationY(this.animationTranslationY + this.panTranslationY);
        this.cameraContainer.setTranslationY(this.animationTranslationY + this.panTranslationY);
    }

    public RectOld getCameraRect() {
        this.cameraContainer.getLocationOnScreen(this.position);
        int[] iArr = this.position;
        return new RectOld(iArr[0], iArr[1], this.cameraContainer.getWidth(), this.cameraContainer.getHeight());
    }

    public void changeVideoPreviewState(int i, float f) {
        VideoPlayer videoPlayer = this.videoPlayer;
        if (videoPlayer == null) {
            return;
        }
        if (i == 0) {
            startProgressTimer();
            this.videoPlayer.play();
        } else if (i == 1) {
            stopProgressTimer();
            this.videoPlayer.pause();
        } else if (i == 2) {
            videoPlayer.seekTo((long) (f * videoPlayer.getDuration()));
        }
    }

    public void send(int i, boolean z, int i2, int i3, int i4, long j, long j2) {
        if (this.textureView == null) {
            return;
        }
        stopProgressTimer();
        VideoPlayer videoPlayer = this.videoPlayer;
        if (videoPlayer != null) {
            videoPlayer.releasePlayer(true);
            this.videoPlayer = null;
        }
        if (i == 4) {
            VideoRecorder videoRecorder = this.videoEncoder;
            if (videoRecorder != null && this.recordedTime > 800) {
                videoRecorder.stopRecording(1, new SendOptions(z, i2, i3, i4, j, j2));
                return;
            }
            if (BuildVars.DEBUG_VERSION && !this.cameraFile.exists()) {
                FileLog.m1048e(new RuntimeException("file not found :( round video"));
            }
            if (this.videoEditedInfo == null) {
                VideoEditedInfo videoEditedInfo = new VideoEditedInfo();
                this.videoEditedInfo = videoEditedInfo;
                videoEditedInfo.startTime = -1L;
                videoEditedInfo.endTime = -1L;
            }
            if (this.videoEditedInfo.needConvert()) {
                this.file = null;
                this.encryptedFile = null;
                this.key = null;
                this.f1577iv = null;
                VideoEditedInfo videoEditedInfo2 = this.videoEditedInfo;
                long j3 = videoEditedInfo2.estimatedDuration;
                double d = j3;
                long j4 = videoEditedInfo2.startTime;
                if (j4 < 0) {
                    j4 = 0;
                }
                long j5 = videoEditedInfo2.endTime;
                if (j5 >= 0) {
                    j3 = j5;
                }
                long j6 = j3 - j4;
                videoEditedInfo2.estimatedDuration = j6;
                videoEditedInfo2.estimatedSize = Math.max(1L, (long) (this.size * (j6 / d)));
                VideoEditedInfo videoEditedInfo3 = this.videoEditedInfo;
                videoEditedInfo3.bitrate = DurationKt.NANOS_IN_MILLIS;
                long j7 = videoEditedInfo3.startTime;
                if (j7 > 0) {
                    videoEditedInfo3.startTime = j7 * 1000;
                }
                long j8 = videoEditedInfo3.endTime;
                if (j8 > 0) {
                    videoEditedInfo3.endTime = j8 * 1000;
                }
                FileLoader.getInstance(this.currentAccount).cancelFileUpload(this.cameraFile.getAbsolutePath(), false);
            } else {
                this.videoEditedInfo.estimatedSize = Math.max(1L, this.size);
            }
            VideoEditedInfo videoEditedInfo4 = this.videoEditedInfo;
            videoEditedInfo4.file = this.file;
            videoEditedInfo4.encryptedFile = this.encryptedFile;
            videoEditedInfo4.key = this.key;
            videoEditedInfo4.f1184iv = this.f1577iv;
            MediaController.PhotoEntry photoEntry = new MediaController.PhotoEntry(0, 0, 0L, this.cameraFile.getAbsolutePath(), 0, true, 0, 0, 0L);
            photoEntry.ttl = i4;
            photoEntry.effectId = j;
            this.delegate.sendMedia(photoEntry, this.videoEditedInfo, z, i2, i3, false, j2);
            if (i2 != 0) {
                startAnimation(false, false);
            }
            MediaController.getInstance().requestRecordAudioFocus(false);
            return;
        }
        this.cancelled = this.recordedTime < 800;
        this.recording = false;
        this.flashing = false;
        updateFlash();
        int i5 = this.cancelled ? 4 : i == 3 ? 2 : 5;
        if (this.cameraThread != null) {
            NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.recordStopped, Integer.valueOf(this.recordingGuid), Integer.valueOf(i5));
            int i6 = this.cancelled ? 0 : i == 3 ? 2 : 1;
            saveLastCameraBitmap();
            this.cameraThread.shutdown(i6, z, i2, i3, i4, j);
            this.cameraThread = null;
        }
        if (this.cancelled) {
            NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.audioRecordTooShort, Integer.valueOf(this.recordingGuid), Boolean.TRUE, Integer.valueOf((int) this.recordedTime));
            startAnimation(false, false);
            MediaController.getInstance().requestRecordAudioFocus(false);
        }
    }

    private void saveLastCameraBitmap() {
        Bitmap bitmap = this.textureView.getBitmap();
        if (bitmap == null || bitmap.getPixel(0, 0) == 0) {
            return;
        }
        final Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmap, 50, 50, true);
        this.lastBitmap = bitmapCreateScaledBitmap;
        Utilities.blurBitmap(bitmapCreateScaledBitmap, 7, 1, bitmapCreateScaledBitmap.getWidth(), bitmapCreateScaledBitmap.getHeight(), bitmapCreateScaledBitmap.getRowBytes());
        Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                InstantCameraView.m12201$r8$lambda$KtyiQd9EHalYVOSGhjvMBJNtes(bitmapCreateScaledBitmap);
            }
        });
    }

    /* JADX INFO: renamed from: $r8$lambda$Kty-iQd9EHalYVOSGhjvMBJNtes, reason: not valid java name */
    public static /* synthetic */ void m12201$r8$lambda$KtyiQd9EHalYVOSGhjvMBJNtes(Bitmap bitmap) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(ApplicationLoader.getFilesDirFixed(), "icthumb.jpg"));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 87, fileOutputStream);
            fileOutputStream.close();
        } catch (Throwable unused) {
        }
    }

    public void cancel(boolean z) {
        stopProgressTimer();
        VideoPlayer videoPlayer = this.videoPlayer;
        if (videoPlayer != null) {
            videoPlayer.releasePlayer(true);
            this.videoPlayer = null;
        }
        if (this.textureView == null) {
            return;
        }
        this.cancelled = true;
        this.recording = false;
        this.flashing = false;
        updateFlash();
        NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.recordStopped, Integer.valueOf(this.recordingGuid), Integer.valueOf(z ? 0 : 6));
        if (this.cameraThread != null) {
            saveLastCameraBitmap();
            this.cameraThread.shutdown(0, true, 0, 0, 0, 0L);
            this.cameraThread = null;
        } else {
            VideoRecorder videoRecorder = this.videoEncoder;
            if (videoRecorder != null) {
                videoRecorder.stopRecording(0, new SendOptions(true, 0, 0, 0, 0L, 0L));
            }
        }
        if (this.cameraFile != null) {
            if (BuildVars.LOGS_ENABLED) {
                FileLog.m1046e("delete camera file by cancel");
            }
            this.cameraFile.delete();
            AutoDeleteMediaTask.unlockFile(this.cameraFile);
            this.cameraFile = null;
        }
        MediaController.getInstance().requestRecordAudioFocus(false);
        startAnimation(false, false);
        invalidate();
    }

    public View getButtonsLayout() {
        return this.buttonsLayout;
    }

    public View getMuteImageView() {
        return this.muteImageView;
    }

    public Paint getPaint() {
        return this.paint;
    }

    public void hideCamera(boolean z) {
        ViewGroup viewGroup;
        destroy(z);
        this.cameraContainer.setTranslationX(0.0f);
        this.textureOverlayView.setTranslationX(0.0f);
        this.animationTranslationY = 0.0f;
        updateTranslationY();
        MediaController.getInstance().resumeByRewind();
        TextureView textureView = this.textureView;
        if (textureView != null && (viewGroup = (ViewGroup) textureView.getParent()) != null) {
            viewGroup.removeView(this.textureView);
        }
        this.textureView = null;
        this.cameraContainer.setImageReceiver(null);
    }

    private void switchCamera() {
        if (this.cameraThread == null) {
            return;
        }
        if (!this.useCamera2 || !this.bothCameras) {
            saveLastCameraBitmap();
            Bitmap bitmap = this.lastBitmap;
            if (bitmap != null) {
                this.needDrawFlickerStub = false;
                this.textureOverlayView.setImageBitmap(bitmap);
                this.textureOverlayView.setAlpha(1.0f);
            }
        }
        this.isFrontface = !this.isFrontface;
        if (ExteraConfig.getRememberLastUsedCamera() && ExteraConfig.getVideoMessagesCamera() != VideoMessagesCamera.ASK) {
            ExteraConfig.setVideoMessagesCamera(this.isFrontface ? VideoMessagesCamera.FRONT : VideoMessagesCamera.REAR);
        }
        updateFlash();
        if (this.useCamera2) {
            if (this.bothCameras) {
                this.camera2SessionCurrent = this.camera2Sessions[!this.isFrontface ? 1 : 0];
                this.cameraThread.flipSurfaces();
                return;
            }
            Camera2Session camera2Session = this.camera2SessionCurrent;
            if (camera2Session != null) {
                camera2Session.destroy(false);
                this.camera2SessionCurrent = null;
                this.camera2Sessions[this.isFrontface ? 1 : 0] = null;
            }
            Camera2Session[] camera2SessionArr = this.camera2Sessions;
            boolean z = this.isFrontface;
            int i = !z ? 1 : 0;
            Camera2Session camera2SessionCreate = Camera2Session.create(z, SystemUtils.getRoundVideoResolution(), SystemUtils.getRoundVideoResolution());
            camera2SessionArr[i] = camera2SessionCreate;
            this.camera2SessionCurrent = camera2SessionCreate;
            if (camera2SessionCreate == null) {
                return;
            }
            camera2SessionCreate.setRecordingVideo(true);
            this.previewSize[0] = new Size(this.camera2SessionCurrent.getPreviewWidth(), this.camera2SessionCurrent.getPreviewHeight());
            this.cameraThread.setCurrentSession(this.camera2SessionCurrent);
        } else {
            CameraSession cameraSession = this.cameraSession;
            if (cameraSession != null) {
                cameraSession.destroy();
                CameraController.getInstance().close(this.cameraSession, null, null);
                this.cameraSession = null;
            }
        }
        initCamera();
        this.cameraReady = false;
        this.cameraZoom = 0.0f;
        this.cameraThread.reinitForNewCamera();
    }

    private void switchCameraX() {
        if (ExteraConfig.getCameraType() != CameraType.CAMERA_X) {
            return;
        }
        CameraXSession cameraXSession = this.cameraXSession;
        boolean z = cameraXSession != null && cameraXSession.isDualMode();
        this.isFrontface = true ^ this.isFrontface;
        if (ExteraConfig.getRememberLastUsedCamera() && ExteraConfig.getVideoMessagesCamera() != VideoMessagesCamera.ASK) {
            ExteraConfig.setVideoMessagesCamera(this.isFrontface ? VideoMessagesCamera.FRONT : VideoMessagesCamera.REAR);
        }
        updateFlash();
        if (z) {
            CameraGLThread cameraGLThread = this.cameraThread;
            if (cameraGLThread != null) {
                cameraGLThread.flipSurfaces();
            }
            CameraXSession cameraXSession2 = this.cameraXSession;
            if (cameraXSession2 != null) {
                cameraXSession2.switchCamera();
                return;
            }
            return;
        }
        this.cameraReady = false;
        this.cameraZoom = 0.0f;
        CameraXSession cameraXSession3 = this.cameraXSession;
        if (cameraXSession3 != null) {
            cameraXSession3.switchCamera();
        } else {
            this.cameraThread.reinitForNewCamera();
        }
    }

    @Deprecated
    private boolean initCamera() {
        int i;
        int i2;
        if (this.useCamera2) {
            return true;
        }
        ArrayList<CameraInfo> cameras = CameraController.getInstance().getCameras();
        if (cameras == null) {
            return false;
        }
        CameraInfo cameraInfo = null;
        int i3 = 0;
        while (i3 < cameras.size()) {
            CameraInfo cameraInfo2 = cameras.get(i3);
            if (!cameraInfo2.isFrontface()) {
                cameraInfo = cameraInfo2;
            }
            if ((this.isFrontface && cameraInfo2.isFrontface()) || (!this.isFrontface && !cameraInfo2.isFrontface())) {
                this.selectedCamera = cameraInfo2;
                break;
            }
            i3++;
            cameraInfo = cameraInfo2;
        }
        if (this.selectedCamera == null) {
            this.selectedCamera = cameraInfo;
        }
        CameraInfo cameraInfo3 = this.selectedCamera;
        if (cameraInfo3 == null) {
            return false;
        }
        ArrayList<Size> previewSizes = cameraInfo3.getPreviewSizes();
        ArrayList<Size> pictureSizes = this.selectedCamera.getPictureSizes();
        this.previewSize[0] = chooseOptimalSize(previewSizes);
        Size sizeChooseOptimalSize = chooseOptimalSize(pictureSizes);
        this.pictureSize = sizeChooseOptimalSize;
        if (this.previewSize[0].mWidth != sizeChooseOptimalSize.mWidth) {
            boolean z = false;
            for (int size = previewSizes.size() - 1; size >= 0; size--) {
                Size size2 = previewSizes.get(size);
                int size3 = pictureSizes.size() - 1;
                while (true) {
                    if (size3 < 0) {
                        break;
                    }
                    Size size4 = pictureSizes.get(size3);
                    int i4 = size2.mWidth;
                    Size size5 = this.pictureSize;
                    if (i4 >= size5.mWidth && (i2 = size2.mHeight) >= size5.mHeight && i4 == size4.mWidth && i2 == size4.mHeight) {
                        this.previewSize[0] = size2;
                        this.pictureSize = size4;
                        z = true;
                        break;
                    }
                    size3--;
                }
                if (z) {
                    break;
                }
            }
            if (!z) {
                for (int size6 = previewSizes.size() - 1; size6 >= 0; size6--) {
                    Size size7 = previewSizes.get(size6);
                    int size8 = pictureSizes.size() - 1;
                    while (true) {
                        if (size8 < 0) {
                            break;
                        }
                        Size size9 = pictureSizes.get(size8);
                        int i5 = size7.mWidth;
                        if (i5 >= 360 && (i = size7.mHeight) >= 360 && i5 == size9.mWidth && i == size9.mHeight) {
                            this.previewSize[0] = size7;
                            this.pictureSize = size9;
                            z = true;
                            break;
                        }
                        size8--;
                    }
                    if (z) {
                        break;
                    }
                }
            }
        }
        if (BuildVars.LOGS_ENABLED) {
            FileLog.m1045d("InstantCamera preview w = " + this.previewSize[0].mWidth + " h = " + this.previewSize[0].mHeight);
        }
        return true;
    }

    @Deprecated
    private Size chooseOptimalSize(ArrayList<Size> arrayList) {
        ArrayList<Size> arrayList2 = new ArrayList<>();
        int i = Build.MANUFACTURER.equalsIgnoreCase("Samsung") ? 1200 : allowBigSizeCamera() ? 1440 : 1200;
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            if (Math.max(arrayList.get(i2).mHeight, arrayList.get(i2).mWidth) <= i && Math.min(arrayList.get(i2).mHeight, arrayList.get(i2).mWidth) >= 320) {
                arrayList2.add(arrayList.get(i2));
            }
        }
        if (arrayList2.isEmpty() || !allowBigSizeCamera()) {
            if (!arrayList2.isEmpty()) {
                arrayList = arrayList2;
            }
            boolean zEqualsIgnoreCase = Build.MANUFACTURER.equalsIgnoreCase("Xiaomi");
            Size size = this.aspectRatio;
            if (zEqualsIgnoreCase) {
                return CameraController.chooseOptimalSize(arrayList, 640, 480, size, false);
            }
            return CameraController.chooseOptimalSize(arrayList, 480, 270, size, false);
        }
        Collections.sort(arrayList2, new Comparator() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda4
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return InstantCameraView.$r8$lambda$BRa4Pcs5xh0fkWGp3KPRvenlfmA((Size) obj, (Size) obj2);
            }
        });
        return arrayList2.get(0);
    }

    public static /* synthetic */ int $r8$lambda$BRa4Pcs5xh0fkWGp3KPRvenlfmA(Size size, Size size2) {
        float fAbs = Math.abs(1.0f - (Math.min(size.mHeight, size.mWidth) / Math.max(size.mHeight, size.mWidth)));
        float fAbs2 = Math.abs(1.0f - (Math.min(size2.mHeight, size2.mWidth) / Math.max(size2.mHeight, size2.mWidth)));
        if (fAbs < fAbs2) {
            return -1;
        }
        return fAbs > fAbs2 ? 1 : 0;
    }

    @Deprecated
    private boolean allowBigSizeCamera() {
        if (SharedConfig.bigCameraForRound || SharedConfig.deviceIsAboveAverage() || Math.max(SharedConfig.getDevicePerformanceClass(), SharedConfig.getLegacyDevicePerformanceClass()) == 2) {
            return true;
        }
        int iHashCode = (Build.MANUFACTURER + " " + Build.DEVICE).toUpperCase().hashCode();
        int i = 0;
        while (true) {
            int[] iArr = ALLOW_BIG_CAMERA_WHITELIST;
            if (i >= iArr.length) {
                return false;
            }
            if (iArr[i] == iHashCode) {
                return true;
            }
            i++;
        }
    }

    @Deprecated
    public static boolean allowBigSizeCameraDebug() {
        if (Math.max(SharedConfig.getDevicePerformanceClass(), SharedConfig.getLegacyDevicePerformanceClass()) == 2) {
            return true;
        }
        int iHashCode = (Build.MANUFACTURER + " " + Build.DEVICE).toUpperCase().hashCode();
        int i = 0;
        while (true) {
            int[] iArr = ALLOW_BIG_CAMERA_WHITELIST;
            if (i >= iArr.length) {
                return false;
            }
            if (iArr[i] == iHashCode) {
                return true;
            }
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createCamera(final int i, final SurfaceTexture surfaceTexture) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$createCamera$16(i, surfaceTexture);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createCamera$16(int i, SurfaceTexture surfaceTexture) {
        CameraXSession cameraXSession;
        if (this.cameraThread == null) {
            return;
        }
        if (BuildVars.LOGS_ENABLED) {
            FileLog.m1045d("InstantCamera create camera session " + i);
        }
        if (ExteraConfig.getCameraType() != CameraType.CAMERA_X) {
            if (!this.useCamera2) {
                if (i == 1) {
                    return;
                }
                surfaceTexture.setDefaultBufferSize(this.previewSize[0].getWidth(), this.previewSize[0].getHeight());
                this.cameraSession = new CameraSession(this.selectedCamera, this.previewSize[0], this.pictureSize, 256, true);
                updateFlash();
                this.cameraThread.setCurrentSession(this.cameraSession);
                CameraController.getInstance().openRound(this.cameraSession, surfaceTexture, new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda18
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$createCamera$11();
                    }
                }, new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda19
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$createCamera$12();
                    }
                });
                return;
            }
            if (this.bothCameras) {
                Camera2Session camera2Session = this.camera2Sessions[i];
                if (camera2Session != null) {
                    camera2Session.open(surfaceTexture);
                    return;
                }
                return;
            }
            if (i == 1) {
                return;
            }
            this.cameraThread.setCurrentSession(this.camera2SessionCurrent);
            this.camera2SessionCurrent.open(surfaceTexture);
            return;
        }
        final Surface surface = new Surface(surfaceTexture);
        Preview.SurfaceProvider surfaceProvider = new Preview.SurfaceProvider() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda20
            @Override // androidx.camera.core.Preview.SurfaceProvider
            public final void onSurfaceRequested(SurfaceRequest surfaceRequest) {
                this.f$0.lambda$createCamera$14(surface, surfaceRequest);
            }
        };
        if (i == 0) {
            surfaceTexture.setDefaultBufferSize(this.previewSize[0].getWidth(), this.previewSize[0].getHeight());
            CameraXSession cameraXSession2 = new CameraXSession(this.camLifecycle, new SurfaceOrientedMeteringPointFactory(this.previewSize[0].getWidth(), this.previewSize[0].getHeight()), surfaceProvider);
            this.cameraXSession = cameraXSession2;
            cameraXSession2.initCamera(getContext(), this.isFrontface, this.bothCameras, new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda21
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$createCamera$15();
                }
            });
            this.camLifecycle.start();
            updateFlash();
            return;
        }
        if (!this.bothCameras || (cameraXSession = this.cameraXSession) == null) {
            return;
        }
        cameraXSession.setSecondSurfaceProvider(surfaceProvider);
        Size size = this.previewSize[0];
        if (size != null) {
            surfaceTexture.setDefaultBufferSize(size.getWidth(), this.previewSize[0].getHeight());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createCamera$11() {
        CameraGLThread cameraGLThread;
        if (this.cameraSession != null) {
            updateFlash();
            boolean z = false;
            try {
                Camera.Size currentPreviewSize = this.cameraSession.getCurrentPreviewSize();
                if (currentPreviewSize.width != this.previewSize[0].getWidth() || currentPreviewSize.height != this.previewSize[0].getHeight()) {
                    this.previewSize[0] = new Size(currentPreviewSize.width, currentPreviewSize.height);
                    FileLog.m1045d("InstantCamera change preview size to w = " + this.previewSize[0].getWidth() + " h = " + this.previewSize[0].getHeight());
                }
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
            try {
                Camera.Size currentPictureSize = this.cameraSession.getCurrentPictureSize();
                if (currentPictureSize.width != this.pictureSize.getWidth() || currentPictureSize.height != this.pictureSize.getHeight()) {
                    this.pictureSize = new Size(currentPictureSize.width, currentPictureSize.height);
                    FileLog.m1045d("InstantCamera change picture size to w = " + this.pictureSize.getWidth() + " h = " + this.pictureSize.getHeight());
                    z = true;
                }
            } catch (Exception e2) {
                FileLog.m1048e(e2);
            }
            if (BuildVars.LOGS_ENABLED) {
                FileLog.m1045d("InstantCamera camera initied");
            }
            this.cameraSession.setInitied();
            if (!z || (cameraGLThread = this.cameraThread) == null) {
                return;
            }
            cameraGLThread.reinitForNewCamera();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createCamera$12() {
        CameraGLThread cameraGLThread = this.cameraThread;
        if (cameraGLThread != null) {
            cameraGLThread.setCurrentSession(this.cameraSession);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createCamera$14(Surface surface, SurfaceRequest surfaceRequest) {
        surfaceRequest.provideSurface(surface, ContextCompat.getMainExecutor(getContext()), new Consumer() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda0
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                InstantCameraView.$r8$lambda$tZf71xbzpSaDnel0YRXy688JHKw((SurfaceRequest.Result) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createCamera$15() {
        CameraGLThread cameraGLThread;
        CameraXSession cameraXSession = this.cameraXSession;
        boolean z = cameraXSession != null && cameraXSession.isDualMode();
        if (!z && this.bothCameras && this.surfaceIndex != 0 && (cameraGLThread = this.cameraThread) != null) {
            cameraGLThread.flipSurfaces();
        }
        this.bothCameras = z;
        CameraGLThread cameraGLThread2 = this.cameraThread;
        if (cameraGLThread2 != null) {
            cameraGLThread2.setOrientation();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int loadShader(int i, String str) {
        int iGlCreateShader = GLES20.glCreateShader(i);
        GLES20.glShaderSource(iGlCreateShader, str);
        GLES20.glCompileShader(iGlCreateShader);
        int[] iArr = new int[1];
        GLES20.glGetShaderiv(iGlCreateShader, 35713, iArr, 0);
        if (iArr[0] != 0) {
            return iGlCreateShader;
        }
        if (BuildVars.LOGS_ENABLED) {
            FileLog.m1046e(GLES20.glGetShaderInfoLog(iGlCreateShader));
        }
        GLES20.glDeleteShader(iGlCreateShader);
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startProgressTimer() {
        Timer timer = this.progressTimer;
        if (timer != null) {
            try {
                timer.cancel();
                this.progressTimer = null;
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
        }
        Timer timer2 = new Timer();
        this.progressTimer = timer2;
        timer2.schedule(new C444411(), 0L, 17L);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.InstantCameraView$11 */
    public class C444411 extends TimerTask {
        public C444411() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$11$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$run$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0() {
            try {
                if (InstantCameraView.this.videoPlayer == null || InstantCameraView.this.videoEditedInfo == null) {
                    return;
                }
                if (InstantCameraView.this.videoEditedInfo.endTime <= 0 || InstantCameraView.this.videoPlayer.getCurrentPosition() < InstantCameraView.this.videoEditedInfo.endTime) {
                    return;
                }
                InstantCameraView.this.videoPlayer.seekTo(InstantCameraView.this.videoEditedInfo.startTime > 0 ? InstantCameraView.this.videoEditedInfo.startTime : 0L);
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
        }
    }

    private void stopProgressTimer() {
        Timer timer = this.progressTimer;
        if (timer != null) {
            try {
                timer.cancel();
                this.progressTimer = null;
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
        }
    }

    public void onPanTranslationUpdate(float f) {
        this.panTranslationY = f / 2.0f;
        updateTranslationY();
    }

    public TextureView getTextureView() {
        return this.textureView;
    }

    public void setIsMessageTransition(boolean z) {
        this.isMessageTransition = z;
    }

    public void resetCameraFile() {
        this.cameraFile = null;
    }

    public class CameraGLThread extends DispatchQueue {
        private final int DO_FLIP;
        private final int DO_REINIT_MESSAGE;
        private final int DO_RENDER_MESSAGE;
        private final int DO_SETORIENTATION_MESSAGE;
        private final int DO_SETSESSION_MESSAGE;
        private final int DO_SHUTDOWN_MESSAGE;
        private Integer cameraId;
        private final SurfaceTexture[] cameraSurface;
        private Object currentSession;
        private int drawProgram;
        private EGL10 egl10;
        private EGLContext eglContext;
        private EGLDisplay eglDisplay;
        private EGLSurface eglSurface;
        private boolean initied;
        private int positionHandle;
        private boolean recording;
        private volatile boolean running;
        private int surfaceHeight;
        private SurfaceTexture surfaceTexture;
        private int surfaceWidth;
        private int textureHandle;
        private int textureMatrixHandle;
        private int vertexMatrixHandle;

        public CameraGLThread(SurfaceTexture surfaceTexture, int i, int i2) {
            super("CameraGLThread");
            this.cameraSurface = new SurfaceTexture[2];
            this.DO_RENDER_MESSAGE = 0;
            this.DO_SHUTDOWN_MESSAGE = 1;
            this.DO_REINIT_MESSAGE = 2;
            this.DO_SETSESSION_MESSAGE = 3;
            this.DO_FLIP = 4;
            this.DO_SETORIENTATION_MESSAGE = 5;
            this.cameraId = 0;
            this.running = true;
            this.surfaceTexture = surfaceTexture;
            this.surfaceWidth = i;
            this.surfaceHeight = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateScale() {
            if (InstantCameraView.this.previewSize[InstantCameraView.this.surfaceIndex] != null) {
                int width = InstantCameraView.this.previewSize[InstantCameraView.this.surfaceIndex].getWidth();
                float fMin = this.surfaceWidth / Math.min(width, r1);
                int i = (int) (width * fMin);
                int height = (int) (InstantCameraView.this.previewSize[InstantCameraView.this.surfaceIndex].getHeight() * fMin);
                if (i == height) {
                    InstantCameraView.this.scaleX = 1.0f;
                    InstantCameraView.this.scaleY = 1.0f;
                } else {
                    InstantCameraView instantCameraView = InstantCameraView.this;
                    if (i > height) {
                        instantCameraView.scaleX = 1.0f;
                        InstantCameraView.this.scaleY = i / this.surfaceHeight;
                    } else {
                        instantCameraView.scaleX = height / this.surfaceWidth;
                        InstantCameraView.this.scaleY = 1.0f;
                    }
                }
                FileLog.m1045d("InstantCamera camera scaleX = " + InstantCameraView.this.scaleX + " scaleY = " + InstantCameraView.this.scaleY);
            }
        }

        private boolean initGL() {
            if (BuildVars.LOGS_ENABLED) {
                FileLog.m1045d("InstantCamera start init gl");
            }
            EGL10 egl10 = (EGL10) EGLContext.getEGL();
            this.egl10 = egl10;
            EGLDisplay eGLDisplayEglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            this.eglDisplay = eGLDisplayEglGetDisplay;
            if (eGLDisplayEglGetDisplay == EGL10.EGL_NO_DISPLAY) {
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.m1046e("InstantCamera eglGetDisplay failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                }
                finish();
                return false;
            }
            if (!this.egl10.eglInitialize(eGLDisplayEglGetDisplay, new int[2])) {
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.m1046e("InstantCamera eglInitialize failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                }
                finish();
                return false;
            }
            int[] iArr = new int[1];
            EGLConfig[] eGLConfigArr = new EGLConfig[1];
            if (!this.egl10.eglChooseConfig(this.eglDisplay, new int[]{12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 0, 12325, 0, 12326, 0, 12344}, eGLConfigArr, 1, iArr)) {
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.m1046e("InstantCamera eglChooseConfig failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                }
                finish();
                return false;
            }
            if (iArr[0] > 0) {
                EGLConfig eGLConfig = eGLConfigArr[0];
                EGLContext eGLContextEglCreateContext = this.egl10.eglCreateContext(this.eglDisplay, eGLConfig, EGL10.EGL_NO_CONTEXT, new int[]{12440, 2, 12344});
                this.eglContext = eGLContextEglCreateContext;
                if (eGLContextEglCreateContext == null) {
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.m1046e("InstantCamera eglCreateContext failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                    }
                    finish();
                    return false;
                }
                if (this.surfaceTexture != null) {
                    EGLSurface eGLSurfaceEglCreateWindowSurface = this.egl10.eglCreateWindowSurface(this.eglDisplay, eGLConfig, this.surfaceTexture, null);
                    this.eglSurface = eGLSurfaceEglCreateWindowSurface;
                    if (eGLSurfaceEglCreateWindowSurface == null || eGLSurfaceEglCreateWindowSurface == EGL10.EGL_NO_SURFACE) {
                        if (BuildVars.LOGS_ENABLED) {
                            FileLog.m1046e("InstantCamera createWindowSurface failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                        }
                        finish();
                        return false;
                    }
                    if (!this.egl10.eglMakeCurrent(this.eglDisplay, eGLSurfaceEglCreateWindowSurface, eGLSurfaceEglCreateWindowSurface, this.eglContext)) {
                        if (BuildVars.LOGS_ENABLED) {
                            FileLog.m1046e("InstantCamera eglMakeCurrent failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                        }
                        finish();
                        return false;
                    }
                    updateScale();
                    float f = (1.0f / InstantCameraView.this.scaleX) / 2.0f;
                    float f2 = (1.0f / InstantCameraView.this.scaleY) / 2.0f;
                    float[] fArr = {-1.0f, -1.0f, 0.0f, 1.0f, -1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f};
                    float f3 = 0.5f - f;
                    float f4 = 0.5f - f2;
                    float f5 = f + 0.5f;
                    float f6 = f2 + 0.5f;
                    float[] fArr2 = {f3, f4, f5, f4, f3, f6, f5, f6};
                    if (InstantCameraView.this.videoEncoder == null) {
                        InstantCameraView instantCameraView = InstantCameraView.this;
                        instantCameraView.videoEncoder = new VideoRecorder();
                    }
                    InstantCameraView.this.vertexBuffer = ByteBuffer.allocateDirect(48).order(ByteOrder.nativeOrder()).asFloatBuffer();
                    InstantCameraView.this.vertexBuffer.put(fArr).position(0);
                    InstantCameraView.this.textureBuffer = ByteBuffer.allocateDirect(32).order(ByteOrder.nativeOrder()).asFloatBuffer();
                    InstantCameraView.this.textureBuffer.put(fArr2).position(0);
                    Matrix.setIdentityM(InstantCameraView.this.mSTMatrix, 0);
                    int iLoadShader = InstantCameraView.this.loadShader(35633, "uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n   gl_Position = uMVPMatrix * aPosition;\n   vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n");
                    int iLoadShader2 = InstantCameraView.this.loadShader(35632, "#extension GL_OES_EGL_image_external : require\nprecision lowp float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n   gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n");
                    if (iLoadShader != 0 && iLoadShader2 != 0) {
                        int iGlCreateProgram = GLES20.glCreateProgram();
                        this.drawProgram = iGlCreateProgram;
                        GLES20.glAttachShader(iGlCreateProgram, iLoadShader);
                        GLES20.glAttachShader(this.drawProgram, iLoadShader2);
                        GLES20.glLinkProgram(this.drawProgram);
                        int[] iArr2 = new int[1];
                        GLES20.glGetProgramiv(this.drawProgram, 35714, iArr2, 0);
                        if (iArr2[0] == 0) {
                            if (BuildVars.LOGS_ENABLED) {
                                FileLog.m1046e("InstantCamera failed link shader");
                            }
                            GLES20.glDeleteProgram(this.drawProgram);
                            this.drawProgram = 0;
                        } else {
                            this.positionHandle = GLES20.glGetAttribLocation(this.drawProgram, "aPosition");
                            this.textureHandle = GLES20.glGetAttribLocation(this.drawProgram, "aTextureCoord");
                            this.vertexMatrixHandle = GLES20.glGetUniformLocation(this.drawProgram, "uMVPMatrix");
                            this.textureMatrixHandle = GLES20.glGetUniformLocation(this.drawProgram, "uSTMatrix");
                        }
                        Matrix.setIdentityM(InstantCameraView.this.mMVPMatrix, 0);
                        GLES20.glGenTextures(2, InstantCameraView.this.cameraTexture, 0);
                        for (final int i = 0; i < 2; i++) {
                            GLES20.glBindTexture(36197, InstantCameraView.this.cameraTexture[i]);
                            GLES20.glTexParameteri(36197, 10241, 9729);
                            GLES20.glTexParameteri(36197, 10240, 9729);
                            GLES20.glTexParameteri(36197, 10242, 33071);
                            GLES20.glTexParameteri(36197, 10243, 33071);
                            this.cameraSurface[i] = new SurfaceTexture(InstantCameraView.this.cameraTexture[i]);
                            this.cameraSurface[i].setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() { // from class: org.telegram.ui.Components.InstantCameraView$CameraGLThread$$ExternalSyntheticLambda1
                                @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
                                public final void onFrameAvailable(SurfaceTexture surfaceTexture) {
                                    this.f$0.lambda$initGL$0(i, surfaceTexture);
                                }
                            });
                            if (ExteraConfig.getCameraType() != CameraType.CAMERA_X || i == 0 || InstantCameraView.this.bothCameras) {
                                InstantCameraView.this.createCamera(i, this.cameraSurface[i]);
                            }
                        }
                        if (BuildVars.LOGS_ENABLED) {
                            FileLog.m1046e("InstantCamera gl initied");
                        }
                        return true;
                    }
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.m1046e("InstantCamera failed creating shader");
                    }
                    finish();
                    return false;
                }
                finish();
                return false;
            }
            if (BuildVars.LOGS_ENABLED) {
                FileLog.m1046e("InstantCamera eglConfig not initialized");
            }
            finish();
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$initGL$0(int i, SurfaceTexture surfaceTexture) {
            InstantCameraView.this.cameraTextureAvailable = true;
            requestRender(i == 0, i == 1);
        }

        public void reinitForNewCamera() {
            Handler handler = getHandler();
            if (handler != null) {
                sendMessage(handler.obtainMessage(2), 0);
            }
        }

        public void finish() {
            EGLContext eGLContext;
            if (this.cameraSurface != null) {
                for (int i = 0; i < 2; i++) {
                    SurfaceTexture surfaceTexture = this.cameraSurface[i];
                    if (surfaceTexture != null) {
                        surfaceTexture.release();
                        this.cameraSurface[i] = null;
                    }
                }
            }
            InstantCameraView.this.cameraTextureAvailable = false;
            if (this.eglSurface != null && (eGLContext = this.eglContext) != null) {
                if (!eGLContext.equals(this.egl10.eglGetCurrentContext()) || !this.eglSurface.equals(this.egl10.eglGetCurrentSurface(12377))) {
                    EGL10 egl10 = this.egl10;
                    EGLDisplay eGLDisplay = this.eglDisplay;
                    EGLSurface eGLSurface = this.eglSurface;
                    egl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.eglContext);
                }
                if (InstantCameraView.this.cameraTexture != null && InstantCameraView.this.cameraTexture[0] != Integer.MIN_VALUE) {
                    GLES20.glDeleteTextures(1, InstantCameraView.this.cameraTexture, 0);
                    InstantCameraView.this.cameraTexture[0] = Integer.MIN_VALUE;
                }
                if (InstantCameraView.this.cameraTexture != null && InstantCameraView.this.cameraTexture[1] != Integer.MIN_VALUE) {
                    GLES20.glDeleteTextures(1, InstantCameraView.this.cameraTexture, 1);
                    InstantCameraView.this.cameraTexture[1] = Integer.MIN_VALUE;
                }
            }
            if (this.eglSurface != null) {
                EGL10 egl102 = this.egl10;
                EGLDisplay eGLDisplay2 = this.eglDisplay;
                EGLSurface eGLSurface2 = EGL10.EGL_NO_SURFACE;
                egl102.eglMakeCurrent(eGLDisplay2, eGLSurface2, eGLSurface2, EGL10.EGL_NO_CONTEXT);
                this.egl10.eglDestroySurface(this.eglDisplay, this.eglSurface);
                this.eglSurface = null;
            }
            EGLContext eGLContext2 = this.eglContext;
            if (eGLContext2 != null) {
                this.egl10.eglDestroyContext(this.eglDisplay, eGLContext2);
                this.eglContext = null;
            }
            EGLDisplay eGLDisplay3 = this.eglDisplay;
            if (eGLDisplay3 != null) {
                this.egl10.eglTerminate(eGLDisplay3);
                this.eglDisplay = null;
            }
        }

        public void setCurrentSession(CameraSession cameraSession) {
            Handler handler = getHandler();
            if (handler != null) {
                sendMessage(handler.obtainMessage(3, cameraSession), 0);
            }
        }

        public void setCurrentSession(Camera2Session camera2Session) {
            Handler handler = getHandler();
            if (handler != null) {
                sendMessage(handler.obtainMessage(3, camera2Session), 0);
            }
        }

        public void setOrientation() {
            Handler handler = getHandler();
            if (handler != null) {
                sendMessage(handler.obtainMessage(5), 0);
            }
        }

        public void flipSurfaces() {
            Handler handler = getHandler();
            if (handler != null) {
                sendMessage(handler.obtainMessage(4), 0);
                requestRender(true, true);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:57:0x0103  */
        /* JADX WARN: Removed duplicated region for block: B:62:0x010c  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private void onDraw(java.lang.Integer r10, boolean r11, boolean r12) {
            /*
                Method dump skipped, instruction units count: 525
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.InstantCameraView.CameraGLThread.onDraw(java.lang.Integer, boolean, boolean):void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDraw$1() {
            InstantCameraView.this.textureOverlayView.animate().setDuration(120L).alpha(0.0f).setInterpolator(new DecelerateInterpolator()).start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDraw$2() {
            if (InstantCameraView.this.textureView == null) {
                return;
            }
            if (InstantCameraView.this.firstFrameThumb != null) {
                InstantCameraView.this.firstFrameThumb.recycle();
                InstantCameraView.this.firstFrameThumb = null;
            }
            InstantCameraView instantCameraView = InstantCameraView.this;
            instantCameraView.firstFrameThumb = instantCameraView.textureView.getBitmap();
        }

        @Override // org.telegram.messenger.DispatchQueue, java.lang.Thread, java.lang.Runnable
        public void run() {
            this.initied = initGL();
            super.run();
        }

        @Override // org.telegram.messenger.DispatchQueue
        public void handleMessage(Message message) {
            int worldAngle;
            int i = message.what;
            if (i == 0) {
                Integer numValueOf = Integer.valueOf(message.arg1);
                int i2 = message.arg2;
                onDraw(numValueOf, (i2 & 1) != 0, (i2 & 2) != 0);
                return;
            }
            if (i == 1) {
                synchronized (this) {
                    finish();
                }
                if (this.recording) {
                    Object obj = message.obj;
                    if ((!(obj instanceof SendOptions) || ((SendOptions) obj).ttl != -2) && InstantCameraView.this.videoEncoder != null) {
                        VideoRecorder videoRecorder = InstantCameraView.this.videoEncoder;
                        int i3 = message.arg1;
                        Object obj2 = message.obj;
                        videoRecorder.stopRecording(i3, obj2 instanceof SendOptions ? (SendOptions) obj2 : null);
                    }
                }
                Looper looperMyLooper = Looper.myLooper();
                if (looperMyLooper != null) {
                    looperMyLooper.quitSafely();
                    return;
                }
                return;
            }
            if (i != 2) {
                if (i == 3) {
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.m1045d("InstantCamera set gl renderer session");
                    }
                    Object obj3 = message.obj;
                    Object obj4 = this.currentSession;
                    if (obj4 == obj3) {
                        if (obj4 instanceof CameraSession) {
                            worldAngle = ((CameraSession) obj4).getWorldAngle();
                        } else {
                            worldAngle = obj4 instanceof Camera2Session ? ((Camera2Session) obj4).getWorldAngle() : 0;
                        }
                        Matrix.setIdentityM(InstantCameraView.this.mMVPMatrix, 0);
                        if (worldAngle != 0) {
                            Matrix.rotateM(InstantCameraView.this.mMVPMatrix, 0, worldAngle, 0.0f, 0.0f, 1.0f);
                            return;
                        }
                        return;
                    }
                    this.currentSession = obj3;
                    return;
                }
                if (i != 4) {
                    if (i != 5) {
                        return;
                    }
                    int displayOrientation = InstantCameraView.this.cameraXSession.getDisplayOrientation();
                    Matrix.setIdentityM(InstantCameraView.this.mMVPMatrix, 0);
                    if (displayOrientation != 0) {
                        Matrix.rotateM(InstantCameraView.this.mMVPMatrix, 0, displayOrientation, 0.0f, 0.0f, 1.0f);
                        return;
                    }
                    return;
                }
                InstantCameraView instantCameraView = InstantCameraView.this;
                instantCameraView.surfaceIndex = 1 - instantCameraView.surfaceIndex;
                updateScale();
                float f = (1.0f / InstantCameraView.this.scaleX) / 2.0f;
                float f2 = (1.0f / InstantCameraView.this.scaleY) / 2.0f;
                float f3 = 0.5f - f;
                float f4 = 0.5f - f2;
                float f5 = f + 0.5f;
                float f6 = f2 + 0.5f;
                InstantCameraView.this.textureBuffer = ByteBuffer.allocateDirect(32).order(ByteOrder.nativeOrder()).asFloatBuffer();
                InstantCameraView.this.textureBuffer.put(new float[]{f3, f4, f5, f4, f3, f6, f5, f6}).position(0);
                return;
            }
            EGL10 egl10 = this.egl10;
            EGLDisplay eGLDisplay = this.eglDisplay;
            EGLSurface eGLSurface = this.eglSurface;
            if (!egl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.eglContext)) {
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.m1045d("InstantCamera eglMakeCurrent failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                    return;
                }
                return;
            }
            SurfaceTexture surfaceTexture = this.cameraSurface[0];
            if (surfaceTexture != null) {
                surfaceTexture.getTransformMatrix(InstantCameraView.this.moldSTMatrix);
                this.cameraSurface[0].setOnFrameAvailableListener(null);
                this.cameraSurface[0].release();
                InstantCameraView.this.oldCameraTexture[0] = InstantCameraView.this.cameraTexture[0];
                InstantCameraView.this.cameraTextureAlpha = 0.0f;
                InstantCameraView.this.cameraTexture[0] = 0;
                InstantCameraView instantCameraView2 = InstantCameraView.this;
                instantCameraView2.oldTextureTextureBuffer = instantCameraView2.textureBuffer.duplicate();
                InstantCameraView instantCameraView3 = InstantCameraView.this;
                instantCameraView3.oldTexturePreviewSize = instantCameraView3.previewSize[0];
            }
            this.cameraId = Integer.valueOf(this.cameraId.intValue() + 1);
            InstantCameraView.this.cameraReady = false;
            GLES20.glGenTextures(1, InstantCameraView.this.cameraTexture, 0);
            GLES20.glBindTexture(36197, InstantCameraView.this.cameraTexture[0]);
            GLES20.glTexParameteri(36197, 10241, 9729);
            GLES20.glTexParameteri(36197, 10240, 9729);
            GLES20.glTexParameteri(36197, 10242, 33071);
            GLES20.glTexParameteri(36197, 10243, 33071);
            this.cameraSurface[0] = new SurfaceTexture(InstantCameraView.this.cameraTexture[0]);
            this.cameraSurface[0].setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() { // from class: org.telegram.ui.Components.InstantCameraView$CameraGLThread$$ExternalSyntheticLambda0
                @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
                public final void onFrameAvailable(SurfaceTexture surfaceTexture2) {
                    this.f$0.lambda$handleMessage$3(surfaceTexture2);
                }
            });
            InstantCameraView.this.createCamera(0, this.cameraSurface[0]);
            updateScale();
            float f7 = (1.0f / InstantCameraView.this.scaleX) / 2.0f;
            float f8 = (1.0f / InstantCameraView.this.scaleY) / 2.0f;
            float f9 = 0.5f - f7;
            float f10 = 0.5f - f8;
            float f11 = f7 + 0.5f;
            float f12 = f8 + 0.5f;
            InstantCameraView.this.textureBuffer = ByteBuffer.allocateDirect(32).order(ByteOrder.nativeOrder()).asFloatBuffer();
            InstantCameraView.this.textureBuffer.put(new float[]{f9, f10, f11, f10, f9, f12, f11, f12}).position(0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$3(SurfaceTexture surfaceTexture) {
            requestRender(true, false);
        }

        public void shutdown(int i, boolean z, int i2, int i3, int i4, long j) {
            Handler handler = getHandler();
            if (handler != null) {
                synchronized (this) {
                    try {
                        this.running = false;
                        for (SurfaceTexture surfaceTexture : this.cameraSurface) {
                            if (surfaceTexture != null) {
                                surfaceTexture.setOnFrameAvailableListener(null);
                            }
                        }
                        handler.removeMessages(0);
                        sendMessage(handler.obtainMessage(1, i, 0, new SendOptions(z, i2, i3, i4, j, 0L)), 0);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public void requestRender(boolean z, boolean z2) {
            Handler handler = getHandler();
            if (handler != null) {
                synchronized (this) {
                    try {
                        if (this.running) {
                            sendMessage(handler.obtainMessage(0, this.cameraId.intValue(), (z ? 1 : 0) + (z2 ? 2 : 0)), 0);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }

    public static class EncoderHandler extends Handler {
        private WeakReference<VideoRecorder> mWeakEncoder;

        public EncoderHandler(VideoRecorder videoRecorder) {
            this.mWeakEncoder = new WeakReference<>(videoRecorder);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            VideoRecorder videoRecorder = this.mWeakEncoder.get();
            if (videoRecorder == null) {
                return;
            }
            boolean z = true;
            if (i == 0) {
                try {
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.m1046e("InstantCamera start encoder");
                    }
                    if (message.arg1 != 1) {
                        z = false;
                    }
                    videoRecorder.prepareEncoder(z);
                    return;
                } catch (Exception e) {
                    FileLog.m1048e(e);
                    videoRecorder.handleStopRecording(0, null);
                    Looper.myLooper().quit();
                    return;
                }
            }
            if (i == 1) {
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.m1046e("InstantCamera stop encoder");
                }
                videoRecorder.handleStopRecording(message.arg1, (SendOptions) message.obj);
                return;
            }
            if (i == 2) {
                videoRecorder.handleVideoFrameAvailable((((long) message.arg1) << 32) | (((long) message.arg2) & 4294967295L), (Integer) message.obj);
                return;
            }
            if (i == 3) {
                videoRecorder.handleAudioFrameAvailable((AudioBufferInfo) message.obj);
                return;
            }
            if (i == 4) {
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.m1046e("InstantCamera pause encoder");
                }
                videoRecorder.handlePauseRecording();
            } else {
                if (i != 5) {
                    return;
                }
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.m1046e("InstantCamera resume encoder");
                }
                videoRecorder.handleResumeRecording();
            }
        }

        public void exit() {
            Looper.myLooper().quit();
        }
    }

    public static class SendOptions {
        long effectId;
        boolean notify;
        int scheduleDate;
        int scheduleRepeatPeriod;
        long stars;
        int ttl;

        public SendOptions(boolean z, int i, int i2, int i3, long j, long j2) {
            this.notify = z;
            this.scheduleDate = i;
            this.scheduleRepeatPeriod = i2;
            this.ttl = i3;
            this.effectId = j;
            this.stars = j2;
        }
    }

    public static class AudioBufferInfo {
        public boolean last;
        public int lastWroteBuffer;
        public int results;
        public ByteBuffer[] buffer = new ByteBuffer[10];
        public long[] offset = new long[10];
        public int[] read = new int[10];

        public AudioBufferInfo() {
            for (int i = 0; i < 10; i++) {
                this.buffer[i] = ByteBuffer.allocateDirect(2048);
                this.buffer[i].order(ByteOrder.nativeOrder());
            }
        }
    }

    public class VideoRecorder implements Runnable {
        private int alphaHandle;
        private MediaCodec.BufferInfo audioBufferInfo;
        private long audioDiff;
        private MediaCodec audioEncoder;
        private long audioFirst;
        private long audioLast;
        private long audioLastDt;
        private AudioRecord audioRecorder;
        private long audioStartTime;
        private boolean audioStopedByTime;
        private int audioTrackIndex;
        private boolean blendEnabled;
        private ArrayBlockingQueue<AudioBufferInfo> buffers;
        private ArrayList<AudioBufferInfo> buffersToWrite;
        private long currentTimestamp;
        private long desyncTime;
        private int drawProgram;
        private android.opengl.EGLConfig eglConfig;
        private android.opengl.EGLContext eglContext;
        private android.opengl.EGLDisplay eglDisplay;
        private android.opengl.EGLSurface eglSurface;
        private File fileToWrite;
        DispatchQueue fileWriteQueue;
        private boolean firstEncode;
        private boolean firstVideoFrameSincePause;
        private int frameCount;
        private DispatchQueue generateKeyframeThumbsQueue;
        private volatile EncoderHandler handler;
        private ArrayList<Bitmap> keyframeThumbs;
        private Integer lastCameraId;
        private long lastCommitedFrameTime;
        private long lastTimestamp;
        private MP4Builder mediaMuxer;
        private InstantCameraVideoEncoderOverlayHelper overlayHelper;
        private volatile boolean pauseRecorder;
        private int positionHandle;
        private int prependHeaderSize;
        private long prevAudioLast;
        long prevTimestamp;
        private long prevVideoLast;
        private int previewSizeHandle;
        public volatile boolean ready;
        private Runnable recorderRunnable;
        private int resolutionHandle;
        private volatile boolean running;
        private volatile int sendWhenDone;
        private volatile SendOptions sendWhenDoneOptions;
        private boolean sentMedia;
        private android.opengl.EGLContext sharedEglContext;
        private boolean skippedFirst;
        private long skippedTime;
        private boolean started;
        private Surface surface;
        private final Object sync;
        private int texelSizeHandle;
        private int textureHandle;
        private int textureMatrixHandle;
        private int vertexMatrixHandle;
        private int videoBitrate;
        private MediaCodec.BufferInfo videoBufferInfo;
        private boolean videoConvertFirstWrite;
        private long videoDiff;
        private MediaCodec videoEncoder;
        private File videoFile;
        private long videoFirst;
        private int videoHeight;
        private long videoLast;
        private long videoLastDt;
        private int videoTrackIndex;
        private int videoWidth;
        private boolean writingToDifferentFile;
        private int zeroTimeStamps;

        private VideoRecorder() {
            this.videoConvertFirstWrite = true;
            this.eglDisplay = EGL14.EGL_NO_DISPLAY;
            this.eglContext = EGL14.EGL_NO_CONTEXT;
            this.eglSurface = EGL14.EGL_NO_SURFACE;
            this.buffersToWrite = new ArrayList<>();
            this.videoTrackIndex = -5;
            this.audioTrackIndex = -5;
            this.audioStartTime = -1L;
            this.currentTimestamp = 0L;
            this.lastTimestamp = -1L;
            this.sync = new Object();
            this.videoFirst = -1L;
            this.prevVideoLast = -1L;
            this.audioFirst = -1L;
            this.audioLast = -1L;
            this.audioLastDt = 0L;
            this.prevAudioLast = -1L;
            this.lastCameraId = 0;
            this.buffers = new ArrayBlockingQueue<>(10);
            this.keyframeThumbs = new ArrayList<>();
            this.recorderRunnable = new RunnableC44561();
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.InstantCameraView$VideoRecorder$1 */
        public class RunnableC44561 implements Runnable {
            public RunnableC44561() {
            }

            /* JADX WARN: Code restructure failed: missing block: B:14:0x003d, code lost:
            
                if (r22.this$1.sendWhenDone == 0) goto L84;
             */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void run() {
                /*
                    Method dump skipped, instruction units count: 419
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.InstantCameraView.VideoRecorder.RunnableC44561.run():void");
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$run$0(double d) {
                NotificationCenter.getInstance(InstantCameraView.this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.recordProgressChanged, Integer.valueOf(InstantCameraView.this.recordingGuid), Double.valueOf(d));
            }
        }

        public void startRecording(File file, android.opengl.EGLContext eGLContext) {
            if (this.started && this.handler != null && this.handler.getLooper() != null && this.handler.getLooper().getThread() != null && this.handler.getLooper().getThread().isAlive()) {
                this.sharedEglContext = eGLContext;
                this.handler.sendMessage(this.handler.obtainMessage(0, 1, 0));
            }
            this.started = true;
            int roundVideoResolution = SystemUtils.getRoundVideoResolution();
            int roundVideoBitrate = SystemUtils.getRoundVideoBitrate() * 1024;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$VideoRecorder$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$startRecording$0();
                }
            });
            this.videoFile = file;
            this.videoWidth = roundVideoResolution;
            this.videoHeight = roundVideoResolution;
            this.videoBitrate = roundVideoBitrate;
            this.sharedEglContext = eGLContext;
            synchronized (this.sync) {
                try {
                    if (this.running) {
                        return;
                    }
                    this.running = true;
                    Thread thread = new Thread(this, "TextureMovieEncoder");
                    thread.setPriority(10);
                    thread.start();
                    while (!this.ready) {
                        try {
                            this.sync.wait();
                        } catch (InterruptedException unused) {
                        }
                    }
                    if (InstantCameraView.this.WRITE_TO_FILE_IN_BACKGROUND) {
                        DispatchQueue dispatchQueue = new DispatchQueue("IVR_FileWriteQueue");
                        this.fileWriteQueue = dispatchQueue;
                        dispatchQueue.setPriority(10);
                    }
                    this.keyframeThumbs.clear();
                    this.frameCount = 0;
                    DispatchQueue dispatchQueue2 = this.generateKeyframeThumbsQueue;
                    if (dispatchQueue2 != null) {
                        dispatchQueue2.cleanupQueue();
                        this.generateKeyframeThumbsQueue.recycle();
                    }
                    this.generateKeyframeThumbsQueue = new DispatchQueue("keyframes_thumb_queue");
                    if (this.handler != null) {
                        this.handler.sendMessage(this.handler.obtainMessage(0));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$startRecording$0() {
            NotificationCenter.getInstance(InstantCameraView.this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.stopAllHeavyOperations, 512);
        }

        public void stopRecording(int i, SendOptions sendOptions) {
            if (this.handler != null) {
                this.handler.sendMessage(this.handler.obtainMessage(1, i, 0, sendOptions));
            }
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$VideoRecorder$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$stopRecording$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$stopRecording$1() {
            NotificationCenter.getInstance(InstantCameraView.this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.stopAllHeavyOperations, 512);
        }

        public void pause() {
            if (this.handler != null) {
                this.handler.sendMessage(this.handler.obtainMessage(4));
            }
        }

        public void resume() {
            if (this.handler != null) {
                this.handler.sendMessage(this.handler.obtainMessage(5));
            }
        }

        public void frameAvailable(SurfaceTexture surfaceTexture, Integer num, long j) {
            synchronized (this.sync) {
                try {
                    if (this.ready) {
                        long timestamp = surfaceTexture.getTimestamp();
                        if (timestamp == 0) {
                            int i = this.zeroTimeStamps + 1;
                            this.zeroTimeStamps = i;
                            if (i <= 1) {
                                return;
                            }
                            if (BuildVars.LOGS_ENABLED) {
                                FileLog.m1045d("InstantCamera fix timestamp enabled");
                            }
                        } else {
                            this.zeroTimeStamps = 0;
                            j = timestamp;
                        }
                        this.prevTimestamp = j;
                        if (this.handler != null) {
                            this.handler.sendMessage(this.handler.obtainMessage(2, (int) (j >> 32), (int) j, num));
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            Looper.prepare();
            synchronized (this.sync) {
                this.handler = new EncoderHandler(this);
                this.ready = true;
                this.sync.notify();
            }
            Looper.loop();
            synchronized (this.sync) {
                this.ready = false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleAudioFrameAvailable(AudioBufferInfo audioBufferInfo) {
            int i;
            boolean z;
            long j;
            int i2;
            long j2;
            if (this.pauseRecorder || this.audioStopedByTime) {
                return;
            }
            AudioBufferInfo audioBufferInfo2 = audioBufferInfo;
            this.buffersToWrite.add(audioBufferInfo2);
            int i3 = 0;
            if (this.audioFirst == -1) {
                if (this.videoFirst == -1) {
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.m1045d("InstantCamera video record not yet started");
                        return;
                    }
                    return;
                }
                while (true) {
                    for (int i4 = 0; i4 < audioBufferInfo2.results; i4++) {
                        if (i4 == 0 && Math.abs(this.videoFirst - audioBufferInfo2.offset[i4]) > 10000000) {
                            long j3 = this.videoFirst;
                            long j4 = audioBufferInfo2.offset[i4];
                            this.desyncTime = j3 - j4;
                            this.audioFirst = j4;
                            if (BuildVars.LOGS_ENABLED) {
                                FileLog.m1045d("InstantCamera detected desync between audio and video " + this.desyncTime);
                            }
                        } else {
                            long j5 = audioBufferInfo2.offset[i4];
                            if (j5 >= this.videoFirst) {
                                audioBufferInfo2.lastWroteBuffer = i4;
                                this.audioFirst = j5;
                                if (BuildVars.LOGS_ENABLED) {
                                    FileLog.m1045d("InstantCamera found first audio frame at " + i4 + " timestamp = " + audioBufferInfo2.offset[i4]);
                                }
                            } else {
                                if (BuildVars.LOGS_ENABLED) {
                                    FileLog.m1045d("InstantCamera ignore first audio frame at " + i4 + " timestamp = " + audioBufferInfo2.offset[i4]);
                                }
                            }
                        }
                    }
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.m1045d("InstantCamera first audio frame not found, removing buffers " + audioBufferInfo2.results);
                    }
                    this.buffersToWrite.remove(audioBufferInfo2);
                    if (this.buffersToWrite.isEmpty()) {
                        return;
                    } else {
                        audioBufferInfo2 = this.buffersToWrite.get(0);
                    }
                }
            }
            if (this.audioStartTime == -1) {
                this.audioStartTime = audioBufferInfo2.offset[audioBufferInfo2.lastWroteBuffer];
            }
            boolean z2 = true;
            if (this.buffersToWrite.size() > 1) {
                audioBufferInfo2 = this.buffersToWrite.get(0);
            }
            try {
                drainEncoder(false);
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
            boolean z3 = false;
            while (audioBufferInfo2 != null) {
                try {
                    long j6 = 0;
                    int iDequeueInputBuffer = this.audioEncoder.dequeueInputBuffer(0L);
                    if (iDequeueInputBuffer >= 0) {
                        ByteBuffer inputBuffer = this.audioEncoder.getInputBuffer(iDequeueInputBuffer);
                        long[] jArr = audioBufferInfo2.offset;
                        int i5 = audioBufferInfo2.lastWroteBuffer;
                        long j7 = jArr[i5];
                        while (true) {
                            int i6 = audioBufferInfo2.results;
                            if (i5 > i6) {
                                z = z2;
                                j = j6;
                                i = i3;
                                break;
                            }
                            if (i5 < i6) {
                                j = j6;
                                j2 = audioBufferInfo2.offset[i5] - this.audioStartTime;
                                if (!this.running && (audioBufferInfo2.offset[i5] >= this.videoLast - this.desyncTime || j2 >= 60000000)) {
                                    break;
                                }
                                if (inputBuffer.remaining() < audioBufferInfo2.read[i5]) {
                                    audioBufferInfo2.lastWroteBuffer = i5;
                                    break;
                                }
                                inputBuffer.put(audioBufferInfo2.buffer[i5]);
                            } else {
                                j = j6;
                            }
                            z = true;
                            if (i5 >= audioBufferInfo2.results - 1) {
                                this.buffersToWrite.remove(audioBufferInfo2);
                                if (this.running) {
                                    this.buffers.put(audioBufferInfo2);
                                }
                                if (!this.buffersToWrite.isEmpty()) {
                                    i2 = 0;
                                    audioBufferInfo2 = this.buffersToWrite.get(0);
                                } else {
                                    i = 0;
                                    z3 = audioBufferInfo2.last;
                                    audioBufferInfo2 = null;
                                    break;
                                }
                            } else {
                                i2 = 0;
                            }
                            i5++;
                            i3 = i2;
                            z2 = true;
                            j6 = j;
                        }
                        if (BuildVars.LOGS_ENABLED) {
                            if (j2 >= 60000000) {
                                FileLog.m1045d("InstantCamera stop audio encoding because recorded time more than 60s");
                            } else {
                                FileLog.m1045d("InstantCamera stop audio encoding because of stoped video recording at " + audioBufferInfo2.offset[i5] + " last video " + this.videoLast);
                            }
                        }
                        this.audioStopedByTime = true;
                        this.buffersToWrite.clear();
                        z3 = true;
                        audioBufferInfo2 = null;
                        i = 0;
                        z = true;
                        long j8 = j7 == j ? j : j7 - this.audioStartTime;
                        long j9 = this.prevAudioLast;
                        if (j9 >= j) {
                            j8 += j9;
                        }
                        long j10 = j8;
                        this.audioLastDt = j10 - this.audioLast;
                        this.audioLast = j10;
                        this.audioEncoder.queueInputBuffer(iDequeueInputBuffer, 0, inputBuffer.position(), j10, z3 ? 4 : i);
                    } else {
                        i = i3;
                        z = z2;
                    }
                    i3 = i;
                    z2 = z;
                } catch (Throwable th) {
                    FileLog.m1048e(th);
                    return;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:28:0x0053  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void handleVideoFrameAvailable(long r26, java.lang.Integer r28) {
            /*
                Method dump skipped, instruction units count: 784
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.InstantCameraView.VideoRecorder.handleVideoFrameAvailable(long, java.lang.Integer):void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleVideoFrameAvailable$2() {
            InstantCameraView.this.textureOverlayView.animate().setDuration(120L).alpha(0.0f).setInterpolator(new DecelerateInterpolator()).start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleVideoFrameAvailable$3() {
            InstantCameraView.this.textureOverlayView.animate().setDuration(120L).alpha(0.0f).setInterpolator(new DecelerateInterpolator()).start();
        }

        private void createKeyframeThumb() {
            if (this.generateKeyframeThumbsQueue != null && SharedConfig.getDevicePerformanceClass() == 2 && this.frameCount % 33 == 0) {
                this.generateKeyframeThumbsQueue.postRunnable(new GenerateKeyframeThumbTask());
            }
        }

        public class GenerateKeyframeThumbTask implements Runnable {
            private GenerateKeyframeThumbTask() {
            }

            @Override // java.lang.Runnable
            public void run() {
                TextureView textureView = InstantCameraView.this.textureView;
                if (textureView != null) {
                    try {
                        final Bitmap bitmap = textureView.getBitmap(AndroidUtilities.m1036dp(56.0f), AndroidUtilities.m1036dp(56.0f));
                        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$VideoRecorder$GenerateKeyframeThumbTask$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$run$0(bitmap);
                            }
                        });
                    } catch (Exception e) {
                        FileLog.m1048e(e);
                    }
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$run$0(Bitmap bitmap) {
                if ((bitmap == null || bitmap.getPixel(0, 0) == 0) && VideoRecorder.this.keyframeThumbs.size() > 1) {
                    VideoRecorder.this.keyframeThumbs.add((Bitmap) VideoRecorder.this.keyframeThumbs.get(VideoRecorder.this.keyframeThumbs.size() - 1));
                } else {
                    VideoRecorder.this.keyframeThumbs.add(bitmap);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handlePauseRecording() {
            this.pauseRecorder = true;
            if (InstantCameraView.this.previewFile != null) {
                InstantCameraView.this.previewFile.delete();
                InstantCameraView.this.previewFile = null;
            }
            InstantCameraView instantCameraView = InstantCameraView.this;
            instantCameraView.previewFile = StoryEntry.makeCacheFile(instantCameraView.currentAccount, true);
            try {
                FileLog.m1045d("InstantCamera handlePauseRecording drain encoders");
                drainEncoder(false);
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
            MP4Builder mP4Builder = this.mediaMuxer;
            if (mP4Builder != null) {
                InstantCameraView instantCameraView2 = InstantCameraView.this;
                if (instantCameraView2.WRITE_TO_FILE_IN_BACKGROUND) {
                    final CountDownLatch countDownLatch = new CountDownLatch(1);
                    this.fileWriteQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$VideoRecorder$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$handlePauseRecording$4(countDownLatch);
                        }
                    });
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                } else {
                    try {
                        mP4Builder.finishMovie(instantCameraView2.previewFile);
                    } catch (Exception e3) {
                        FileLog.m1048e(e3);
                    }
                }
            }
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$VideoRecorder$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$handlePauseRecording$5();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handlePauseRecording$4(CountDownLatch countDownLatch) {
            try {
                this.mediaMuxer.finishMovie(InstantCameraView.this.previewFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handlePauseRecording$5() {
            InstantCameraView.this.videoEditedInfo = new VideoEditedInfo();
            InstantCameraView.this.videoEditedInfo.roundVideo = true;
            InstantCameraView.this.videoEditedInfo.startTime = -1L;
            InstantCameraView.this.videoEditedInfo.endTime = -1L;
            InstantCameraView.this.videoEditedInfo.file = InstantCameraView.this.file;
            InstantCameraView.this.videoEditedInfo.encryptedFile = InstantCameraView.this.encryptedFile;
            InstantCameraView.this.videoEditedInfo.key = InstantCameraView.this.key;
            InstantCameraView.this.videoEditedInfo.f1184iv = InstantCameraView.this.f1577iv;
            InstantCameraView.this.videoEditedInfo.estimatedSize = Math.max(1L, InstantCameraView.this.size);
            InstantCameraView.this.videoEditedInfo.framerate = 25;
            VideoEditedInfo videoEditedInfo = InstantCameraView.this.videoEditedInfo;
            InstantCameraView.this.videoEditedInfo.originalWidth = 360;
            videoEditedInfo.resultWidth = 360;
            VideoEditedInfo videoEditedInfo2 = InstantCameraView.this.videoEditedInfo;
            InstantCameraView.this.videoEditedInfo.originalHeight = 360;
            videoEditedInfo2.resultHeight = 360;
            InstantCameraView.this.videoEditedInfo.originalPath = InstantCameraView.this.previewFile.getAbsolutePath();
            setupVideoPlayer(InstantCameraView.this.previewFile);
            InstantCameraView.this.videoEditedInfo.estimatedDuration = InstantCameraView.this.recordedTime;
            NotificationCenter.getInstance(InstantCameraView.this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.audioDidSent, Integer.valueOf(InstantCameraView.this.recordingGuid), InstantCameraView.this.videoEditedInfo, InstantCameraView.this.previewFile.getAbsolutePath(), this.keyframeThumbs);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleResumeRecording() {
            this.pauseRecorder = false;
        }

        private void setupVideoPlayer(File file) {
            InstantCameraView.this.videoPlayer = new VideoPlayer();
            InstantCameraView.this.videoPlayer.setDelegate(new VideoPlayer.VideoPlayerDelegate() { // from class: org.telegram.ui.Components.InstantCameraView.VideoRecorder.2
                @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
                public void onRenderedFirstFrame() {
                }

                @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
                public boolean onSurfaceDestroyed(SurfaceTexture surfaceTexture) {
                    return false;
                }

                @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
                public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
                }

                @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
                public void onVideoSizeChanged(int i, int i2, int i3, float f) {
                }

                @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
                public void onStateChanged(boolean z, int i) {
                    if (InstantCameraView.this.videoPlayer != null && InstantCameraView.this.videoPlayer.isPlaying() && i == 4 && InstantCameraView.this.videoEditedInfo != null) {
                        InstantCameraView.this.videoPlayer.seekTo(InstantCameraView.this.videoEditedInfo.startTime > 0 ? InstantCameraView.this.videoEditedInfo.startTime : 0L);
                    }
                }

                @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
                public void onError(VideoPlayer videoPlayer, Exception exc) {
                    FileLog.m1048e(exc);
                }
            });
            InstantCameraView.this.videoPlayer.setTextureView(InstantCameraView.this.textureView);
            InstantCameraView.this.videoPlayer.preparePlayer(Uri.fromFile(file), "other");
            InstantCameraView.this.videoPlayer.play();
            InstantCameraView.this.videoPlayer.setMute(true);
            InstantCameraView.this.startProgressTimer();
            AnimatorSet animatorSet = new AnimatorSet();
            LinearLayout linearLayout = InstantCameraView.this.buttonsLayout;
            Property property = View.ALPHA;
            animatorSet.playTogether(ObjectAnimator.ofFloat(linearLayout, (Property<LinearLayout, Float>) property, 0.0f), ObjectAnimator.ofInt(InstantCameraView.this.paint, AnimationProperties.PAINT_ALPHA, 0), ObjectAnimator.ofFloat(InstantCameraView.this.muteImageView, (Property<ImageView, Float>) property, 1.0f));
            animatorSet.setDuration(180L);
            animatorSet.setInterpolator(new DecelerateInterpolator());
            animatorSet.start();
            EGL14.eglDestroySurface(this.eglDisplay, this.eglSurface);
            this.eglSurface = EGL14.EGL_NO_SURFACE;
            Surface surface = this.surface;
            if (surface != null) {
                surface.release();
                this.surface = null;
            }
            android.opengl.EGLDisplay eGLDisplay = this.eglDisplay;
            if (eGLDisplay != EGL14.EGL_NO_DISPLAY) {
                android.opengl.EGLSurface eGLSurface = EGL14.EGL_NO_SURFACE;
                EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL14.EGL_NO_CONTEXT);
                EGL14.eglDestroyContext(this.eglDisplay, this.eglContext);
                EGL14.eglReleaseThread();
                EGL14.eglTerminate(this.eglDisplay);
            }
            this.eglDisplay = EGL14.EGL_NO_DISPLAY;
            this.eglContext = EGL14.EGL_NO_CONTEXT;
            this.eglConfig = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleStopRecording(final int i, final SendOptions sendOptions) {
            boolean z;
            DispatchQueue dispatchQueue;
            if (i != 1 || ((InstantCameraView.this.videoEditedInfo != null && InstantCameraView.this.videoEditedInfo.needConvert()) || InstantCameraView.this.delegate.isInScheduleMode())) {
                z = true;
            } else {
                if (!this.sentMedia) {
                    this.sentMedia = true;
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$VideoRecorder$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$handleStopRecording$6(sendOptions);
                        }
                    });
                }
                z = false;
            }
            if (this.running && !this.pauseRecorder) {
                FileLog.m1045d("InstantCamera handleStopRecording running=false");
                this.sendWhenDone = i;
                this.sendWhenDoneOptions = sendOptions;
                this.running = false;
                return;
            }
            try {
                FileLog.m1045d("InstantCamera handleStopRecording drain encoders");
                drainEncoder(true);
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
            MediaCodec mediaCodec = this.videoEncoder;
            if (mediaCodec != null) {
                try {
                    mediaCodec.stop();
                    this.videoEncoder.release();
                    this.videoEncoder = null;
                } catch (Exception e2) {
                    FileLog.m1048e(e2);
                }
            }
            MediaCodec mediaCodec2 = this.audioEncoder;
            if (mediaCodec2 != null) {
                try {
                    mediaCodec2.stop();
                    this.audioEncoder.release();
                    this.audioEncoder = null;
                    setBluetoothScoOn(false);
                } catch (Exception e3) {
                    FileLog.m1048e(e3);
                }
            }
            if (InstantCameraView.this.previewFile != null) {
                InstantCameraView.this.previewFile.delete();
                InstantCameraView.this.previewFile = null;
            }
            MP4Builder mP4Builder = this.mediaMuxer;
            if (mP4Builder != null) {
                if (InstantCameraView.this.WRITE_TO_FILE_IN_BACKGROUND) {
                    final CountDownLatch countDownLatch = new CountDownLatch(1);
                    this.fileWriteQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$VideoRecorder$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$handleStopRecording$7(countDownLatch);
                        }
                    });
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e4) {
                        e4.printStackTrace();
                    }
                } else {
                    try {
                        mP4Builder.finishMovie();
                    } catch (Exception e5) {
                        FileLog.m1048e(e5);
                    }
                }
                FileLog.m1045d("InstantCamera handleStopRecording finish muxer");
                if (this.writingToDifferentFile) {
                    if (this.videoFile.exists()) {
                        try {
                            this.videoFile.delete();
                        } catch (Exception e6) {
                            FileLog.m1046e("InstantCamera copying fileToWrite to videoFile, deleting videoFile error " + this.videoFile);
                            FileLog.m1048e(e6);
                        }
                    }
                    if (!this.fileToWrite.renameTo(this.videoFile)) {
                        FileLog.m1046e("InstantCamera unable to rename file, try move file");
                        try {
                            AndroidUtilities.copyFile(this.fileToWrite, this.videoFile);
                            this.fileToWrite.delete();
                        } catch (IOException e7) {
                            FileLog.m1048e(e7);
                            FileLog.m1046e("InstantCamera unable to move file");
                        }
                    }
                }
            }
            if (i != 2 && (dispatchQueue = this.generateKeyframeThumbsQueue) != null) {
                dispatchQueue.cleanupQueue();
                this.generateKeyframeThumbsQueue.recycle();
                this.generateKeyframeThumbsQueue = null;
            }
            FileLog.m1045d("InstantCamera handleStopRecording send " + i);
            if (i == 0) {
                FileLoader.getInstance(InstantCameraView.this.currentAccount).cancelFileUpload(this.videoFile.getAbsolutePath(), false);
                try {
                    this.fileToWrite.delete();
                } catch (Throwable unused) {
                }
                try {
                    this.videoFile.delete();
                } catch (Throwable unused2) {
                }
            } else {
                if (z && (i != 1 || !this.sentMedia)) {
                    this.sentMedia = true;
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$VideoRecorder$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$handleStopRecording$10(i, sendOptions);
                        }
                    });
                }
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$VideoRecorder$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$handleStopRecording$11();
                    }
                });
            }
            EGL14.eglDestroySurface(this.eglDisplay, this.eglSurface);
            this.eglSurface = EGL14.EGL_NO_SURFACE;
            Surface surface = this.surface;
            if (surface != null) {
                surface.release();
                this.surface = null;
            }
            android.opengl.EGLDisplay eGLDisplay = this.eglDisplay;
            if (eGLDisplay != EGL14.EGL_NO_DISPLAY) {
                android.opengl.EGLSurface eGLSurface = EGL14.EGL_NO_SURFACE;
                EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL14.EGL_NO_CONTEXT);
                EGL14.eglDestroyContext(this.eglDisplay, this.eglContext);
                EGL14.eglReleaseThread();
                EGL14.eglTerminate(this.eglDisplay);
            }
            this.eglDisplay = EGL14.EGL_NO_DISPLAY;
            this.eglContext = EGL14.EGL_NO_CONTEXT;
            this.eglConfig = null;
            if (this.handler != null) {
                this.handler.exit();
            }
            InstantCameraVideoEncoderOverlayHelper instantCameraVideoEncoderOverlayHelper = this.overlayHelper;
            if (instantCameraVideoEncoderOverlayHelper != null) {
                instantCameraVideoEncoderOverlayHelper.destroy();
                this.overlayHelper = null;
            }
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$VideoRecorder$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$handleStopRecording$12();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleStopRecording$6(SendOptions sendOptions) {
            InstantCameraView.this.videoEditedInfo = new VideoEditedInfo();
            InstantCameraView.this.videoEditedInfo.startTime = -1L;
            InstantCameraView.this.videoEditedInfo.endTime = -1L;
            InstantCameraView.this.videoEditedInfo.estimatedSize = Math.max(1L, InstantCameraView.this.size);
            InstantCameraView.this.videoEditedInfo.roundVideo = true;
            InstantCameraView.this.videoEditedInfo.file = InstantCameraView.this.file;
            InstantCameraView.this.videoEditedInfo.encryptedFile = InstantCameraView.this.encryptedFile;
            InstantCameraView.this.videoEditedInfo.key = InstantCameraView.this.key;
            InstantCameraView.this.videoEditedInfo.f1184iv = InstantCameraView.this.f1577iv;
            InstantCameraView.this.videoEditedInfo.framerate = 25;
            VideoEditedInfo videoEditedInfo = InstantCameraView.this.videoEditedInfo;
            InstantCameraView.this.videoEditedInfo.originalWidth = 360;
            videoEditedInfo.resultWidth = 360;
            VideoEditedInfo videoEditedInfo2 = InstantCameraView.this.videoEditedInfo;
            InstantCameraView.this.videoEditedInfo.originalHeight = 360;
            videoEditedInfo2.resultHeight = 360;
            InstantCameraView.this.videoEditedInfo.originalPath = this.videoFile.getAbsolutePath();
            InstantCameraView.this.videoEditedInfo.notReadyYet = true;
            InstantCameraView.this.videoEditedInfo.thumb = InstantCameraView.this.firstFrameThumb;
            InstantCameraView.this.videoEditedInfo.estimatedDuration = InstantCameraView.this.recordedTime;
            InstantCameraView.this.firstFrameThumb = null;
            MediaController.PhotoEntry photoEntry = new MediaController.PhotoEntry(0, 0, 0L, this.videoFile.getAbsolutePath(), 0, true, 0, 0, 0L);
            if (sendOptions != null) {
                photoEntry.ttl = sendOptions.ttl;
                photoEntry.effectId = sendOptions.effectId;
            }
            InstantCameraView.this.delegate.sendMedia(photoEntry, InstantCameraView.this.videoEditedInfo, sendOptions == null || sendOptions.notify, sendOptions != null ? sendOptions.scheduleDate : 0, sendOptions != null ? sendOptions.scheduleRepeatPeriod : 0, false, sendOptions != null ? sendOptions.stars : 0L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleStopRecording$7(CountDownLatch countDownLatch) {
            try {
                this.mediaMuxer.finishMovie();
            } catch (Exception e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleStopRecording$10(int i, final SendOptions sendOptions) {
            if (InstantCameraView.this.videoEditedInfo == null) {
                InstantCameraView.this.videoEditedInfo = new VideoEditedInfo();
                InstantCameraView.this.videoEditedInfo.startTime = -1L;
                InstantCameraView.this.videoEditedInfo.endTime = -1L;
            }
            boolean zNeedConvert = InstantCameraView.this.videoEditedInfo.needConvert();
            InstantCameraView instantCameraView = InstantCameraView.this;
            if (zNeedConvert) {
                instantCameraView.file = null;
                InstantCameraView.this.encryptedFile = null;
                InstantCameraView.this.key = null;
                InstantCameraView.this.f1577iv = null;
                double d = InstantCameraView.this.videoEditedInfo.estimatedDuration;
                long j = InstantCameraView.this.videoEditedInfo.startTime >= 0 ? InstantCameraView.this.videoEditedInfo.startTime : 0L;
                long j2 = InstantCameraView.this.videoEditedInfo.endTime;
                InstantCameraView instantCameraView2 = InstantCameraView.this;
                InstantCameraView.this.videoEditedInfo.estimatedDuration = (j2 >= 0 ? instantCameraView2.videoEditedInfo.endTime : instantCameraView2.videoEditedInfo.estimatedDuration) - j;
                InstantCameraView.this.videoEditedInfo.estimatedSize = Math.max(1L, (long) (InstantCameraView.this.size * (InstantCameraView.this.videoEditedInfo.estimatedDuration / d)));
                InstantCameraView.this.videoEditedInfo.bitrate = DurationKt.NANOS_IN_MILLIS;
                if (InstantCameraView.this.videoEditedInfo.startTime > 0) {
                    InstantCameraView.this.videoEditedInfo.startTime *= 1000;
                }
                if (InstantCameraView.this.videoEditedInfo.endTime > 0) {
                    InstantCameraView.this.videoEditedInfo.endTime *= 1000;
                }
                FileLoader.getInstance(InstantCameraView.this.currentAccount).cancelFileUpload(InstantCameraView.this.cameraFile.getAbsolutePath(), false);
            } else {
                instantCameraView.videoEditedInfo.estimatedSize = Math.max(1L, InstantCameraView.this.size);
            }
            InstantCameraView.this.videoEditedInfo.roundVideo = true;
            InstantCameraView.this.videoEditedInfo.file = InstantCameraView.this.file;
            InstantCameraView.this.videoEditedInfo.encryptedFile = InstantCameraView.this.encryptedFile;
            InstantCameraView.this.videoEditedInfo.key = InstantCameraView.this.key;
            InstantCameraView.this.videoEditedInfo.f1184iv = InstantCameraView.this.f1577iv;
            InstantCameraView.this.videoEditedInfo.framerate = 25;
            VideoEditedInfo videoEditedInfo = InstantCameraView.this.videoEditedInfo;
            InstantCameraView.this.videoEditedInfo.originalWidth = 360;
            videoEditedInfo.resultWidth = 360;
            VideoEditedInfo videoEditedInfo2 = InstantCameraView.this.videoEditedInfo;
            InstantCameraView.this.videoEditedInfo.originalHeight = 360;
            videoEditedInfo2.resultHeight = 360;
            InstantCameraView.this.videoEditedInfo.originalPath = this.videoFile.getAbsolutePath();
            final VideoEditedInfo videoEditedInfo3 = InstantCameraView.this.videoEditedInfo;
            if (i == 1) {
                if (InstantCameraView.this.delegate.isInScheduleMode()) {
                    AlertsCreator.createScheduleDatePickerDialog(InstantCameraView.this.delegate.getParentActivity(), InstantCameraView.this.delegate.getDialogId(), new AlertsCreator.ScheduleDatePickerDelegate() { // from class: org.telegram.ui.Components.InstantCameraView$VideoRecorder$$ExternalSyntheticLambda14
                        @Override // org.telegram.ui.Components.AlertsCreator.ScheduleDatePickerDelegate
                        public final void didSelectDate(boolean z, int i2, int i3) {
                            this.f$0.lambda$handleStopRecording$8(sendOptions, videoEditedInfo3, z, i2, i3);
                        }
                    }, new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$VideoRecorder$$ExternalSyntheticLambda15
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$handleStopRecording$9();
                        }
                    }, InstantCameraView.this.resourcesProvider);
                } else {
                    MediaController.PhotoEntry photoEntry = new MediaController.PhotoEntry(0, 0, 0L, this.videoFile.getAbsolutePath(), 0, true, 0, 0, 0L);
                    if (sendOptions != null) {
                        photoEntry.ttl = sendOptions.ttl;
                        photoEntry.effectId = sendOptions.effectId;
                    }
                    InstantCameraView.this.delegate.sendMedia(photoEntry, videoEditedInfo3, sendOptions == null || sendOptions.notify, sendOptions != null ? sendOptions.scheduleDate : 0, sendOptions != null ? sendOptions.scheduleRepeatPeriod : 0, false, sendOptions != null ? sendOptions.stars : 0L);
                }
                InstantCameraView.this.videoEditedInfo = null;
                return;
            }
            setupVideoPlayer(this.videoFile);
            videoEditedInfo3.estimatedDuration = InstantCameraView.this.recordedTime;
            NotificationCenter.getInstance(InstantCameraView.this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.audioDidSent, Integer.valueOf(InstantCameraView.this.recordingGuid), videoEditedInfo3, this.videoFile.getAbsolutePath(), this.keyframeThumbs);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleStopRecording$8(SendOptions sendOptions, VideoEditedInfo videoEditedInfo, boolean z, int i, int i2) {
            MediaController.PhotoEntry photoEntry = new MediaController.PhotoEntry(0, 0, 0L, this.videoFile.getAbsolutePath(), 0, true, 0, 0, 0L);
            if (sendOptions != null) {
                photoEntry.ttl = sendOptions.ttl;
                photoEntry.effectId = sendOptions.effectId;
            }
            InstantCameraView.this.delegate.sendMedia(photoEntry, videoEditedInfo, z || sendOptions == null || sendOptions.notify, i != 0 ? i : sendOptions != null ? sendOptions.scheduleDate : 0, i2 != 0 ? i2 : sendOptions != null ? sendOptions.scheduleRepeatPeriod : 0, false, sendOptions != null ? sendOptions.stars : 0L);
            InstantCameraView.this.startAnimation(false, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleStopRecording$9() {
            InstantCameraView.this.startAnimation(false, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleStopRecording$11() {
            if (this.sentMedia && InstantCameraView.this.videoEditedInfo != null) {
                InstantCameraView.this.videoEditedInfo.notReadyYet = false;
            }
            didWriteData(this.videoFile, 0L, true);
            MediaController.getInstance().requestRecordAudioFocus(false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleStopRecording$12() {
            InstantCameraView.this.videoEncoder = null;
        }

        /* JADX WARN: Removed duplicated region for block: B:45:? A[RETURN, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private void setBluetoothScoOn(boolean r3) {
            /*
                r2 = this;
                android.content.Context r2 = org.telegram.messenger.ApplicationLoader.applicationContext
                java.lang.String r0 = "audio"
                java.lang.Object r2 = r2.getSystemService(r0)
                android.media.AudioManager r2 = (android.media.AudioManager) r2
                boolean r0 = org.telegram.messenger.SharedConfig.recordViaSco
                if (r0 == 0) goto L1c
                java.lang.String r0 = "android.permission.BLUETOOTH_CONNECT"
                boolean r0 = org.telegram.p035ui.Components.PermissionRequest.hasPermission(r0)
                if (r0 != 0) goto L1c
                r0 = 0
                org.telegram.messenger.SharedConfig.recordViaSco = r0
                org.telegram.messenger.SharedConfig.saveConfig()
            L1c:
                boolean r0 = r2.isBluetoothScoAvailableOffCall()
                if (r0 == 0) goto L26
                boolean r0 = org.telegram.messenger.SharedConfig.recordViaSco
                if (r0 != 0) goto L28
            L26:
                if (r3 != 0) goto L66
            L28:
                android.bluetooth.BluetoothAdapter r0 = android.bluetooth.BluetoothAdapter.getDefaultAdapter()
                if (r0 == 0) goto L39
                r1 = 1
                int r0 = r0.getProfileConnectionState(r1)     // Catch: java.lang.Throwable -> L37 java.lang.SecurityException -> L66
                r1 = 2
                if (r0 == r1) goto L3b
                goto L39
            L37:
                r0 = move-exception
                goto L53
            L39:
                if (r3 != 0) goto L66
            L3b:
                if (r3 == 0) goto L47
                boolean r0 = r2.isBluetoothScoOn()     // Catch: java.lang.Throwable -> L37 java.lang.SecurityException -> L66
                if (r0 != 0) goto L47
                r2.startBluetoothSco()     // Catch: java.lang.Throwable -> L37 java.lang.SecurityException -> L66
                return
            L47:
                if (r3 != 0) goto L66
                boolean r0 = r2.isBluetoothScoOn()     // Catch: java.lang.Throwable -> L37 java.lang.SecurityException -> L66
                if (r0 == 0) goto L66
                r2.stopBluetoothSco()     // Catch: java.lang.Throwable -> L37 java.lang.SecurityException -> L66
                return
            L53:
                org.telegram.messenger.FileLog.m1048e(r0)
                if (r3 != 0) goto L66
                boolean r3 = r2.isBluetoothScoOn()     // Catch: java.lang.Exception -> L62
                if (r3 == 0) goto L66
                r2.stopBluetoothSco()     // Catch: java.lang.Exception -> L62
                goto L66
            L62:
                r2 = move-exception
                org.telegram.messenger.FileLog.m1048e(r2)
            L66:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.InstantCameraView.VideoRecorder.setBluetoothScoOn(boolean):void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void prepareEncoder(final boolean z) {
            long j;
            int i;
            setBluetoothScoOn(true);
            try {
                int minBufferSize = AudioRecord.getMinBufferSize(48000, 16, 2);
                if (minBufferSize <= 0) {
                    minBufferSize = 3584;
                }
                int i2 = 49152 < minBufferSize ? ((minBufferSize / 2048) + 1) * 4096 : 49152;
                this.buffers.clear();
                for (int i3 = 0; i3 < 3; i3++) {
                    this.buffers.add(new AudioBufferInfo());
                }
                if (z) {
                    this.prevVideoLast = this.videoLast + this.videoLastDt;
                    this.prevAudioLast = this.audioLast + this.audioLastDt;
                    this.firstVideoFrameSincePause = true;
                    j = 0;
                } else {
                    this.prevVideoLast = -1L;
                    this.prevAudioLast = -1L;
                    j = 0;
                    this.currentTimestamp = 0L;
                }
                this.lastTimestamp = -1L;
                this.lastCommitedFrameTime = j;
                this.audioStartTime = -1L;
                this.audioFirst = -1L;
                this.videoFirst = -1L;
                this.videoLast = -1L;
                this.videoDiff = -1L;
                this.audioLast = -1L;
                this.audioDiff = -1L;
                this.skippedFirst = false;
                this.skippedTime = 0L;
                AudioRecord audioRecord = new AudioRecord(0, 48000, 16, 2, i2);
                this.audioRecorder = audioRecord;
                audioRecord.startRecording();
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.m1045d("InstantCamera initied audio record with channels " + this.audioRecorder.getChannelCount() + " sample rate = " + this.audioRecorder.getSampleRate() + " bufferSize = " + i2);
                }
                this.pauseRecorder = false;
                Thread thread = new Thread(this.recorderRunnable);
                thread.setPriority(10);
                thread.start();
                this.audioBufferInfo = new MediaCodec.BufferInfo();
                this.videoBufferInfo = new MediaCodec.BufferInfo();
                MediaFormat mediaFormat = new MediaFormat();
                mediaFormat.setString("mime", MediaController.AUDIO_MIME_TYPE);
                mediaFormat.setInteger("sample-rate", 48000);
                mediaFormat.setInteger("channel-count", 1);
                mediaFormat.setInteger("bitrate", SystemUtils.getRoundAudioBitrate() * 1024);
                mediaFormat.setInteger("max-input-size", 20480);
                MediaCodec mediaCodecCreateEncoderByType = MediaCodec.createEncoderByType(MediaController.AUDIO_MIME_TYPE);
                this.audioEncoder = mediaCodecCreateEncoderByType;
                mediaCodecCreateEncoderByType.configure(mediaFormat, (Surface) null, (MediaCrypto) null, 1);
                this.audioEncoder.start();
                this.videoEncoder = MediaCodec.createEncoderByType(MediaController.VIDEO_MIME_TYPE);
                this.firstEncode = true;
                MediaFormat mediaFormatCreateVideoFormat = MediaFormat.createVideoFormat(MediaController.VIDEO_MIME_TYPE, this.videoWidth, this.videoHeight);
                mediaFormatCreateVideoFormat.setInteger("color-format", 2130708361);
                mediaFormatCreateVideoFormat.setInteger("bitrate", this.videoBitrate);
                mediaFormatCreateVideoFormat.setInteger("frame-rate", 30);
                mediaFormatCreateVideoFormat.setInteger("i-frame-interval", 1);
                this.videoEncoder.configure(mediaFormatCreateVideoFormat, (Surface) null, (MediaCrypto) null, 1);
                this.surface = this.videoEncoder.createInputSurface();
                this.videoEncoder.start();
                if (!z) {
                    boolean zIsSdCardPath = ImageLoader.isSdCardPath(this.videoFile);
                    this.fileToWrite = this.videoFile;
                    if (zIsSdCardPath) {
                        try {
                            File file = new File(ApplicationLoader.getFilesDirFixed(), "camera_tmp.mp4");
                            this.fileToWrite = file;
                            if (file.exists()) {
                                this.fileToWrite.delete();
                            }
                            this.writingToDifferentFile = true;
                        } catch (Throwable th) {
                            FileLog.m1048e(th);
                            this.fileToWrite = this.videoFile;
                            this.writingToDifferentFile = false;
                        }
                    }
                    Mp4Movie mp4Movie = new Mp4Movie();
                    mp4Movie.setCacheFile(this.fileToWrite);
                    mp4Movie.setRotation(0);
                    mp4Movie.setSize(this.videoWidth, this.videoHeight);
                    MP4Builder mP4BuilderCreateMovie = new MP4Builder().createMovie(mp4Movie, InstantCameraView.this.isSecretChat, false);
                    this.mediaMuxer = mP4BuilderCreateMovie;
                    InstantCameraView instantCameraView = InstantCameraView.this;
                    boolean zDeviceIsHigh = SharedConfig.deviceIsHigh();
                    instantCameraView.allowSendingWhileRecording = zDeviceIsHigh;
                    mP4BuilderCreateMovie.setAllowSyncFiles(zDeviceIsHigh);
                }
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$VideoRecorder$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$prepareEncoder$13(z);
                    }
                });
                if (this.eglDisplay != EGL14.EGL_NO_DISPLAY) {
                    GlShader$$ExternalSyntheticBUOutline1.m1250m("EGL already set up");
                    return;
                }
                android.opengl.EGLDisplay eGLDisplayEglGetDisplay = EGL14.eglGetDisplay(0);
                this.eglDisplay = eGLDisplayEglGetDisplay;
                if (eGLDisplayEglGetDisplay == EGL14.EGL_NO_DISPLAY) {
                    GlShader$$ExternalSyntheticBUOutline1.m1250m("unable to get EGL14 display");
                    return;
                }
                int[] iArr = new int[2];
                if (!EGL14.eglInitialize(eGLDisplayEglGetDisplay, iArr, 0, iArr, 1)) {
                    this.eglDisplay = null;
                    GlShader$$ExternalSyntheticBUOutline1.m1250m("unable to initialize EGL14");
                    return;
                }
                if (this.eglContext == EGL14.EGL_NO_CONTEXT) {
                    android.opengl.EGLConfig[] eGLConfigArr = new android.opengl.EGLConfig[1];
                    if (!EGL14.eglChooseConfig(this.eglDisplay, new int[]{12324, 8, 12323, 8, 12322, 8, 12321, 8, 12352, 4, EglBase.EGL_RECORDABLE_ANDROID, 1, 12344}, 0, eGLConfigArr, 0, 1, new int[1], 0)) {
                        GlShader$$ExternalSyntheticBUOutline1.m1250m("Unable to find a suitable EGLConfig");
                        return;
                    } else {
                        i = 0;
                        this.eglContext = EGL14.eglCreateContext(this.eglDisplay, eGLConfigArr[0], this.sharedEglContext, new int[]{12440, 2, 12344}, 0);
                        this.eglConfig = eGLConfigArr[0];
                    }
                } else {
                    i = 0;
                }
                EGL14.eglQueryContext(this.eglDisplay, this.eglContext, 12440, new int[1], i);
                if (this.eglSurface != EGL14.EGL_NO_SURFACE) {
                    Segment$$ExternalSyntheticBUOutline1.m992m("surface already created");
                    return;
                }
                android.opengl.EGLSurface eGLSurfaceEglCreateWindowSurface = EGL14.eglCreateWindowSurface(this.eglDisplay, this.eglConfig, this.surface, new int[]{12344}, i);
                this.eglSurface = eGLSurfaceEglCreateWindowSurface;
                if (eGLSurfaceEglCreateWindowSurface == null) {
                    GlShader$$ExternalSyntheticBUOutline1.m1250m("surface was null");
                    return;
                }
                if (!EGL14.eglMakeCurrent(this.eglDisplay, eGLSurfaceEglCreateWindowSurface, eGLSurfaceEglCreateWindowSurface, this.eglContext)) {
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.m1046e("eglMakeCurrent failed " + GLUtils.getEGLErrorString(EGL14.eglGetError()));
                    }
                    GlShader$$ExternalSyntheticBUOutline1.m1250m("eglMakeCurrent failed");
                    return;
                }
                GLES20.glBlendFunc(770, 771);
                InstantCameraVideoEncoderOverlayHelper instantCameraVideoEncoderOverlayHelper = this.overlayHelper;
                if (instantCameraVideoEncoderOverlayHelper != null) {
                    instantCameraVideoEncoderOverlayHelper.destroy();
                    this.overlayHelper = null;
                }
                this.overlayHelper = new InstantCameraVideoEncoderOverlayHelper(this.videoWidth, this.videoHeight);
                InstantCameraView instantCameraView2 = InstantCameraView.this;
                String strCreateFragmentShaderV2 = instantCameraView2.createFragmentShaderV2(instantCameraView2.previewSize[0]);
                int iLoadShader = InstantCameraView.this.loadShader(35633, "uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n   gl_Position = uMVPMatrix * aPosition;\n   vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n");
                int iLoadShader2 = InstantCameraView.this.loadShader(35632, strCreateFragmentShaderV2);
                if (iLoadShader == 0 || iLoadShader2 == 0) {
                    return;
                }
                int iGlCreateProgram = GLES20.glCreateProgram();
                this.drawProgram = iGlCreateProgram;
                GLES20.glAttachShader(iGlCreateProgram, iLoadShader);
                GLES20.glAttachShader(this.drawProgram, iLoadShader2);
                GLES20.glLinkProgram(this.drawProgram);
                int[] iArr2 = new int[1];
                GLES20.glGetProgramiv(this.drawProgram, 35714, iArr2, 0);
                int i4 = iArr2[0];
                int i5 = this.drawProgram;
                if (i4 == 0) {
                    GLES20.glDeleteProgram(i5);
                    this.drawProgram = 0;
                    return;
                }
                this.positionHandle = GLES20.glGetAttribLocation(i5, "aPosition");
                this.textureHandle = GLES20.glGetAttribLocation(this.drawProgram, "aTextureCoord");
                this.previewSizeHandle = GLES20.glGetUniformLocation(this.drawProgram, "preview");
                this.resolutionHandle = GLES20.glGetUniformLocation(this.drawProgram, "resolution");
                this.alphaHandle = GLES20.glGetUniformLocation(this.drawProgram, "alpha");
                this.vertexMatrixHandle = GLES20.glGetUniformLocation(this.drawProgram, "uMVPMatrix");
                this.textureMatrixHandle = GLES20.glGetUniformLocation(this.drawProgram, "uSTMatrix");
                this.texelSizeHandle = GLES20.glGetUniformLocation(this.drawProgram, "texelSize");
            } catch (Exception e) {
                HttpUrl$$ExternalSyntheticBUOutline0.m958m(e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$prepareEncoder$13(boolean z) {
            if (InstantCameraView.this.cancelled) {
                return;
            }
            try {
                InstantCameraView.this.performHapticFeedback(3, 2);
            } catch (Exception unused) {
            }
            AndroidUtilities.lockOrientation(InstantCameraView.this.delegate.getParentActivity());
            InstantCameraView instantCameraView = InstantCameraView.this;
            instantCameraView.recordPlusTime = z ? instantCameraView.recordedTime : 0L;
            InstantCameraView.this.recordStartTime = System.currentTimeMillis();
            InstantCameraView.this.recording = true;
            InstantCameraView.this.updateFlash();
            InstantCameraView.this.invalidate();
            NotificationCenter.getInstance(InstantCameraView.this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.recordStarted, Integer.valueOf(InstantCameraView.this.recordingGuid), Boolean.FALSE);
        }

        private void didWriteData(File file, long j, boolean z) {
            boolean z2 = this.videoConvertFirstWrite;
            InstantCameraView instantCameraView = InstantCameraView.this;
            if (z2) {
                FileLoader.getInstance(instantCameraView.currentAccount).uploadFile(file.toString(), InstantCameraView.this.isSecretChat, false, 1L, 33554432, false);
                this.videoConvertFirstWrite = false;
                if (z) {
                    FileLoader.getInstance(InstantCameraView.this.currentAccount).checkUploadNewDataAvailable(file.toString(), InstantCameraView.this.isSecretChat, j, z ? file.length() : 0L);
                    return;
                }
                return;
            }
            FileLoader.getInstance(instantCameraView.currentAccount).checkUploadNewDataAvailable(file.toString(), InstantCameraView.this.isSecretChat, j, z ? file.length() : 0L);
        }

        public void drainEncoder(boolean z) {
            ByteBuffer byteBufferAllocate;
            ByteBuffer byteBufferAllocate2;
            if (z) {
                this.videoEncoder.signalEndOfInputStream();
            }
            while (true) {
                int iDequeueOutputBuffer = this.videoEncoder.dequeueOutputBuffer(this.videoBufferInfo, 10000L);
                byte b2 = 1;
                if (iDequeueOutputBuffer == -1) {
                    if (!z || this.pauseRecorder) {
                        break;
                    }
                } else if (iDequeueOutputBuffer == -3) {
                    continue;
                } else if (iDequeueOutputBuffer == -2) {
                    MediaFormat outputFormat = this.videoEncoder.getOutputFormat();
                    if (this.videoTrackIndex == -5) {
                        this.videoTrackIndex = this.mediaMuxer.addTrack(outputFormat, false);
                        if (outputFormat.containsKey("prepend-sps-pps-to-idr-frames") && outputFormat.getInteger("prepend-sps-pps-to-idr-frames") == 1) {
                            this.prependHeaderSize = outputFormat.getByteBuffer("csd-0").limit() + outputFormat.getByteBuffer("csd-1").limit();
                        }
                    }
                } else if (iDequeueOutputBuffer < 0) {
                    continue;
                } else {
                    ByteBuffer outputBuffer = this.videoEncoder.getOutputBuffer(iDequeueOutputBuffer);
                    if (outputBuffer == null) {
                        CameraView$VideoRecorder$$ExternalSyntheticBUOutline0.m1092m("encoderOutputBuffer ", iDequeueOutputBuffer, " was null");
                        return;
                    }
                    MediaCodec.BufferInfo bufferInfo = this.videoBufferInfo;
                    int i = bufferInfo.size;
                    if (i > 1) {
                        int i2 = bufferInfo.flags;
                        if ((i2 & 2) == 0) {
                            int i3 = this.prependHeaderSize;
                            if (i3 != 0 && (i2 & 1) != 0) {
                                bufferInfo.offset += i3;
                                bufferInfo.size = i - i3;
                            }
                            if (this.firstEncode && (i2 & 1) != 0) {
                                if (bufferInfo.size > 100) {
                                    outputBuffer.position(bufferInfo.offset);
                                    byte[] bArr = new byte[100];
                                    outputBuffer.get(bArr);
                                    int i4 = 0;
                                    int i5 = 0;
                                    while (true) {
                                        if (i4 < 96) {
                                            if (bArr[i4] == 0 && bArr[i4 + 1] == 0 && bArr[i4 + 2] == 0 && bArr[i4 + 3] == 1 && (i5 = i5 + 1) > 1) {
                                                MediaCodec.BufferInfo bufferInfo2 = this.videoBufferInfo;
                                                bufferInfo2.offset += i4;
                                                bufferInfo2.size -= i4;
                                                break;
                                            }
                                            i4++;
                                        } else {
                                            break;
                                        }
                                    }
                                }
                                this.firstEncode = false;
                            }
                            if (InstantCameraView.this.WRITE_TO_FILE_IN_BACKGROUND) {
                                final MediaCodec.BufferInfo bufferInfo3 = new MediaCodec.BufferInfo();
                                MediaCodec.BufferInfo bufferInfo4 = this.videoBufferInfo;
                                bufferInfo3.size = bufferInfo4.size;
                                bufferInfo3.offset = bufferInfo4.offset;
                                bufferInfo3.flags = bufferInfo4.flags;
                                bufferInfo3.presentationTimeUs = bufferInfo4.presentationTimeUs;
                                final ByteBuffer byteBufferCloneByteBuffer = AndroidUtilities.cloneByteBuffer(outputBuffer);
                                if (byteBufferCloneByteBuffer != null) {
                                    this.fileWriteQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$VideoRecorder$$ExternalSyntheticLambda12
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            this.f$0.lambda$drainEncoder$14(byteBufferCloneByteBuffer, bufferInfo3);
                                        }
                                    });
                                }
                            } else {
                                long jWriteSampleData = this.mediaMuxer.writeSampleData(this.videoTrackIndex, outputBuffer, this.videoBufferInfo, true);
                                if (jWriteSampleData != 0 && !this.writingToDifferentFile && InstantCameraView.this.allowSendingWhileRecording) {
                                    didWriteData(this.videoFile, jWriteSampleData, false);
                                }
                            }
                        } else if (this.videoTrackIndex == -5) {
                            byte[] bArr2 = new byte[i];
                            outputBuffer.limit(bufferInfo.offset + i);
                            outputBuffer.position(this.videoBufferInfo.offset);
                            outputBuffer.get(bArr2);
                            int i6 = this.videoBufferInfo.size - 1;
                            while (i6 >= 0 && i6 > 3) {
                                if (bArr2[i6] == b2 && bArr2[i6 - 1] == 0 && bArr2[i6 - 2] == 0) {
                                    int i7 = i6 - 3;
                                    if (bArr2[i7] == 0) {
                                        byteBufferAllocate = ByteBuffer.allocate(i7);
                                        byteBufferAllocate2 = ByteBuffer.allocate(this.videoBufferInfo.size - i7);
                                        byteBufferAllocate.put(bArr2, 0, i7).position(0);
                                        byteBufferAllocate2.put(bArr2, i7, this.videoBufferInfo.size - i7).position(0);
                                        break;
                                    }
                                }
                                i6--;
                                b2 = 1;
                            }
                            byteBufferAllocate = null;
                            byteBufferAllocate2 = null;
                            MediaFormat mediaFormatCreateVideoFormat = MediaFormat.createVideoFormat(MediaController.VIDEO_MIME_TYPE, this.videoWidth, this.videoHeight);
                            if (byteBufferAllocate != null && byteBufferAllocate2 != null) {
                                mediaFormatCreateVideoFormat.setByteBuffer("csd-0", byteBufferAllocate);
                                mediaFormatCreateVideoFormat.setByteBuffer("csd-1", byteBufferAllocate2);
                            }
                            this.videoTrackIndex = this.mediaMuxer.addTrack(mediaFormatCreateVideoFormat, false);
                        }
                    }
                    this.videoEncoder.releaseOutputBuffer(iDequeueOutputBuffer, false);
                    if ((this.videoBufferInfo.flags & 4) != 0) {
                        break;
                    }
                }
            }
            while (true) {
                int iDequeueOutputBuffer2 = this.audioEncoder.dequeueOutputBuffer(this.audioBufferInfo, 0L);
                if (iDequeueOutputBuffer2 == -1) {
                    if (!z) {
                        return;
                    }
                    if ((!this.running && this.sendWhenDone == 0) || this.pauseRecorder) {
                        return;
                    }
                } else if (iDequeueOutputBuffer2 != -3) {
                    if (iDequeueOutputBuffer2 == -2) {
                        MediaFormat outputFormat2 = this.audioEncoder.getOutputFormat();
                        if (this.audioTrackIndex == -5) {
                            this.audioTrackIndex = this.mediaMuxer.addTrack(outputFormat2, true);
                        }
                    } else if (iDequeueOutputBuffer2 < 0) {
                        continue;
                    } else {
                        ByteBuffer outputBuffer2 = this.audioEncoder.getOutputBuffer(iDequeueOutputBuffer2);
                        if (outputBuffer2 == null) {
                            CameraView$VideoRecorder$$ExternalSyntheticBUOutline0.m1092m("encoderOutputBuffer ", iDequeueOutputBuffer2, " was null");
                            return;
                        }
                        MediaCodec.BufferInfo bufferInfo5 = this.audioBufferInfo;
                        if ((bufferInfo5.flags & 2) != 0) {
                            bufferInfo5.size = 0;
                        }
                        if (bufferInfo5.size != 0) {
                            if (InstantCameraView.this.WRITE_TO_FILE_IN_BACKGROUND) {
                                final MediaCodec.BufferInfo bufferInfo6 = new MediaCodec.BufferInfo();
                                MediaCodec.BufferInfo bufferInfo7 = this.audioBufferInfo;
                                bufferInfo6.size = bufferInfo7.size;
                                bufferInfo6.offset = bufferInfo7.offset;
                                bufferInfo6.flags = bufferInfo7.flags;
                                bufferInfo6.presentationTimeUs = bufferInfo7.presentationTimeUs;
                                final ByteBuffer byteBufferCloneByteBuffer2 = AndroidUtilities.cloneByteBuffer(outputBuffer2);
                                if (byteBufferCloneByteBuffer2 != null) {
                                    this.fileWriteQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$VideoRecorder$$ExternalSyntheticLambda13
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            this.f$0.lambda$drainEncoder$15(byteBufferCloneByteBuffer2, bufferInfo6);
                                        }
                                    });
                                }
                                MediaCodec mediaCodec = this.audioEncoder;
                                if (mediaCodec != null) {
                                    mediaCodec.releaseOutputBuffer(iDequeueOutputBuffer2, false);
                                }
                            } else {
                                long jWriteSampleData2 = this.mediaMuxer.writeSampleData(this.audioTrackIndex, outputBuffer2, bufferInfo5, false);
                                if (jWriteSampleData2 != 0 && !this.writingToDifferentFile && InstantCameraView.this.allowSendingWhileRecording) {
                                    didWriteData(this.videoFile, jWriteSampleData2, false);
                                }
                                MediaCodec mediaCodec2 = this.audioEncoder;
                                if (mediaCodec2 != null) {
                                    mediaCodec2.releaseOutputBuffer(iDequeueOutputBuffer2, false);
                                }
                            }
                        } else {
                            MediaCodec mediaCodec3 = this.audioEncoder;
                            if (mediaCodec3 != null) {
                                mediaCodec3.releaseOutputBuffer(iDequeueOutputBuffer2, false);
                            }
                        }
                        if ((this.audioBufferInfo.flags & 4) != 0) {
                            return;
                        }
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$drainEncoder$14(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
            long jWriteSampleData;
            try {
                jWriteSampleData = this.mediaMuxer.writeSampleData(this.videoTrackIndex, byteBuffer, bufferInfo, true);
            } catch (Exception e) {
                e.printStackTrace();
                jWriteSampleData = 0;
            }
            if (jWriteSampleData == 0 || this.writingToDifferentFile || !InstantCameraView.this.allowSendingWhileRecording) {
                return;
            }
            didWriteData(this.videoFile, jWriteSampleData, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$drainEncoder$15(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
            long jWriteSampleData;
            try {
                jWriteSampleData = this.mediaMuxer.writeSampleData(this.audioTrackIndex, byteBuffer, bufferInfo, false);
            } catch (Exception e) {
                e.printStackTrace();
                jWriteSampleData = 0;
            }
            if (jWriteSampleData == 0 || this.writingToDifferentFile || !InstantCameraView.this.allowSendingWhileRecording) {
                return;
            }
            didWriteData(this.videoFile, jWriteSampleData, false);
        }

        public void finalize() throws Throwable {
            DispatchQueue dispatchQueue = this.fileWriteQueue;
            if (dispatchQueue != null) {
                dispatchQueue.recycle();
                this.fileWriteQueue = null;
            }
            InstantCameraVideoEncoderOverlayHelper instantCameraVideoEncoderOverlayHelper = this.overlayHelper;
            if (instantCameraVideoEncoderOverlayHelper != null) {
                instantCameraVideoEncoderOverlayHelper.destroy();
                this.overlayHelper = null;
            }
            try {
                android.opengl.EGLDisplay eGLDisplay = this.eglDisplay;
                if (eGLDisplay != EGL14.EGL_NO_DISPLAY) {
                    android.opengl.EGLSurface eGLSurface = EGL14.EGL_NO_SURFACE;
                    EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL14.EGL_NO_CONTEXT);
                    EGL14.eglDestroyContext(this.eglDisplay, this.eglContext);
                    EGL14.eglReleaseThread();
                    EGL14.eglTerminate(this.eglDisplay);
                    this.eglDisplay = EGL14.EGL_NO_DISPLAY;
                    this.eglContext = EGL14.EGL_NO_CONTEXT;
                    this.eglConfig = null;
                }
            } finally {
                super.finalize();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String createFragmentShaderV2(Size size) {
        if (SharedConfig.deviceIsLow() || !allowBigSizeCamera()) {
            return "#extension GL_OES_EGL_image_external : require\nprecision highp float;\nvarying vec2 vTextureCoord;\nuniform float alpha;\nuniform vec2 preview;\nuniform vec2 resolution;\nuniform samplerExternalOES sTexture;\nvoid main() {\n   vec4 textColor = texture2D(sTexture, vTextureCoord);\n   gl_FragColor = vec4(textColor.rgb * alpha, alpha);\n}\n";
        }
        if (size != null && Math.max(size.getHeight(), size.getWidth()) * 0.7f < MessagesController.getInstance(this.currentAccount).roundVideoSize) {
            return "#extension GL_OES_EGL_image_external : require\nprecision highp float;\nvarying vec2 vTextureCoord;\nuniform float alpha;\nuniform vec2 preview;\nuniform vec2 resolution;\nuniform samplerExternalOES sTexture;\nvoid main() {\n   vec4 textColor = texture2D(sTexture, vTextureCoord);\n   gl_FragColor = vec4(textColor.rgb * alpha, alpha);\n}\n";
        }
        return "#extension GL_OES_EGL_image_external : require\nprecision highp float;\nvarying vec2 vTextureCoord;\nuniform vec2 resolution;\nuniform vec2 preview;\nuniform float alpha;\nuniform samplerExternalOES sTexture;\nvoid main() {\n   vec2 c_textureSize = preview;\n   vec2 c_onePixel = (1.0 / c_textureSize);\n   vec2 uv = vTextureCoord;\n   vec2 pixel = uv * c_textureSize + 0.5;\n   vec2 frac = fract(pixel);\n   pixel = (floor(pixel) / c_textureSize) - vec2(c_onePixel);\n   vec4 tl = texture2D(sTexture, pixel + vec2(0.0         , 0.0));\n   vec4 tr = texture2D(sTexture, pixel + vec2(c_onePixel.x, 0.0));\n   vec4 bl = texture2D(sTexture, pixel + vec2(0.0         , c_onePixel.y));\n   vec4 br = texture2D(sTexture, pixel + vec2(c_onePixel.x, c_onePixel.y));\n   vec4 x1 = mix(tl, tr, frac.x);\n   vec4 x2 = mix(bl, br, frac.x);\n   gl_FragColor = mix(x1, x2, frac.y) * alpha;\n}\n";
    }

    public class InstantViewCameraContainer extends FrameLayout {
        float imageProgress;
        ImageReceiver imageReceiver;

        public InstantViewCameraContainer(Context context) {
            super(context);
            InstantCameraView.this.setWillNotDraw(false);
        }

        public void setImageReceiver(ImageReceiver imageReceiver) {
            if (this.imageReceiver == null) {
                this.imageProgress = 0.0f;
            }
            this.imageReceiver = imageReceiver;
            invalidate();
        }

        @Override // android.view.ViewGroup, android.view.View
        public void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
            float f = this.imageProgress;
            if (f != 1.0f) {
                float f2 = f + 0.064f;
                this.imageProgress = f2;
                if (f2 > 1.0f) {
                    this.imageProgress = 1.0f;
                }
                invalidate();
            }
            if (this.imageReceiver != null) {
                canvas.save();
                if (this.imageReceiver.getImageWidth() != InstantCameraView.this.textureViewSize) {
                    float imageWidth = InstantCameraView.this.textureViewSize / this.imageReceiver.getImageWidth();
                    canvas.scale(imageWidth, imageWidth);
                }
                canvas.translate(-this.imageReceiver.getImageX(), -this.imageReceiver.getImageY());
                float alpha = this.imageReceiver.getAlpha();
                this.imageReceiver.setAlpha(this.imageProgress);
                this.imageReceiver.draw(canvas);
                this.imageReceiver.setAlpha(alpha);
                canvas.restore();
            }
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Camera2Session camera2Session;
        VideoPlayer videoPlayer;
        if (motionEvent.getAction() == 0 && motionEvent.getY() > getMeasuredHeight() - getPaddingBottom()) {
            return false;
        }
        if (motionEvent.getAction() == 0 && this.delegate != null && (videoPlayer = this.videoPlayer) != null) {
            boolean zIsMuted = videoPlayer.isMuted();
            this.videoPlayer.setMute(!zIsMuted);
            AnimatorSet animatorSet = this.muteAnimation;
            if (animatorSet != null) {
                animatorSet.cancel();
            }
            AnimatorSet animatorSet2 = new AnimatorSet();
            this.muteAnimation = animatorSet2;
            animatorSet2.playTogether(ObjectAnimator.ofFloat(this.muteImageView, (Property<ImageView, Float>) View.ALPHA, !zIsMuted ? 1.0f : 0.0f), ObjectAnimator.ofFloat(this.muteImageView, (Property<ImageView, Float>) View.SCALE_X, !zIsMuted ? 1.0f : 0.5f), ObjectAnimator.ofFloat(this.muteImageView, (Property<ImageView, Float>) View.SCALE_Y, zIsMuted ? 0.5f : 1.0f));
            this.muteAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.InstantCameraView.12
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (animator.equals(InstantCameraView.this.muteAnimation)) {
                        InstantCameraView.this.muteAnimation = null;
                    }
                }
            });
            this.muteAnimation.setDuration(180L);
            this.muteAnimation.setInterpolator(new DecelerateInterpolator());
            this.muteAnimation.start();
        }
        if (ExteraConfig.getCameraType() == CameraType.CAMERA_X) {
            getParent().requestDisallowInterceptTouchEvent(true);
            this.scaleGestureDetector.onTouchEvent(motionEvent);
            return true;
        }
        if (motionEvent.getActionMasked() == 0 || motionEvent.getActionMasked() == 5) {
            if (this.maybePinchToZoomTouchMode && !this.isInPinchToZoomTouchMode && motionEvent.getPointerCount() == 2 && this.finishZoomTransition == null && this.recording) {
                this.pinchStartDistance = (float) Math.hypot(motionEvent.getX(1) - motionEvent.getX(0), motionEvent.getY(1) - motionEvent.getY(0));
                this.pointerId1 = motionEvent.getPointerId(0);
                this.pointerId2 = motionEvent.getPointerId(1);
                this.isInPinchToZoomTouchMode = true;
                this.zoomWas = false;
                this.initialCameraZoom = this.cameraZoom;
            }
            if (motionEvent.getActionMasked() == 0) {
                RectF rectF = AndroidUtilities.rectTmp;
                rectF.set(this.cameraContainer.getX(), this.cameraContainer.getY(), this.cameraContainer.getX() + this.cameraContainer.getMeasuredWidth(), this.cameraContainer.getY() + this.cameraContainer.getMeasuredHeight());
                this.maybePinchToZoomTouchMode = rectF.contains(motionEvent.getX(), motionEvent.getY());
            }
            return true;
        }
        if (motionEvent.getActionMasked() == 2 && this.isInPinchToZoomTouchMode) {
            int i = -1;
            int i2 = -1;
            for (int i3 = 0; i3 < motionEvent.getPointerCount(); i3++) {
                if (this.pointerId1 == motionEvent.getPointerId(i3)) {
                    i = i3;
                }
                if (this.pointerId2 == motionEvent.getPointerId(i3)) {
                    i2 = i3;
                }
            }
            if (i == -1 || i2 == -1) {
                this.isInPinchToZoomTouchMode = false;
                finishZoom();
                return false;
            }
            float fHypot = (float) Math.hypot(motionEvent.getX(i2) - motionEvent.getX(i), motionEvent.getY(i2) - motionEvent.getY(i));
            float maxZoom = 0.002f;
            if (this.useCamera2 && (camera2Session = this.camera2SessionCurrent) != null) {
                maxZoom = 0.002f * (camera2Session.getMaxZoom() - this.camera2SessionCurrent.getMinZoom());
            }
            float f = this.initialCameraZoom + ((fHypot - this.pinchStartDistance) * maxZoom);
            this.cameraZoom = f;
            if (this.useCamera2) {
                Camera2Session camera2Session2 = this.camera2SessionCurrent;
                if (camera2Session2 != null) {
                    float fClamp = Utilities.clamp(f, camera2Session2.getMaxZoom(), this.camera2SessionCurrent.getMinZoom());
                    this.cameraZoom = fClamp;
                    this.camera2SessionCurrent.setZoom(fClamp);
                }
            } else {
                float fClamp2 = Utilities.clamp(f, 1.0f, 0.0f);
                this.cameraZoom = fClamp2;
                CameraSession cameraSession = this.cameraSession;
                if (cameraSession != null) {
                    cameraSession.setZoom(fClamp2);
                }
            }
            this.zoomWas = true;
        } else if ((motionEvent.getActionMasked() == 1 || ((motionEvent.getActionMasked() == 6 && checkPointerIds(motionEvent)) || motionEvent.getActionMasked() == 3)) && this.isInPinchToZoomTouchMode) {
            this.isInPinchToZoomTouchMode = false;
            if (this.zoomWas) {
                finishZoom();
            }
        }
        return true;
    }

    public void finishZoom() {
        if (this.finishZoomTransition != null || ExteraConfig.getStaticZoom()) {
            return;
        }
        if (ExteraConfig.getCameraType() == CameraType.CAMERA_X) {
            CameraXSession cameraXSession = this.cameraXSession;
            if (cameraXSession != null) {
                float zoomRatio = cameraXSession.getZoomRatio();
                float defaultZoomRatio = this.cameraXSession.getDefaultZoomRatio();
                if ((zoomRatio >= 1.0f || defaultZoomRatio < 1.0f || this.cameraXSession.getMinZoomRatio() >= 1.0f) && Math.abs(zoomRatio - defaultZoomRatio) > 0.001f) {
                    ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(zoomRatio, defaultZoomRatio);
                    this.finishZoomTransition = valueAnimatorOfFloat;
                    valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda5
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            this.f$0.lambda$finishZoom$17(valueAnimator);
                        }
                    });
                    this.finishZoomTransition.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.InstantCameraView.13
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            InstantCameraView instantCameraView = InstantCameraView.this;
                            if (instantCameraView.finishZoomTransition != null) {
                                instantCameraView.finishZoomTransition = null;
                            }
                        }
                    });
                    this.finishZoomTransition.setDuration(350L);
                    this.finishZoomTransition.setInterpolator(CubicBezierInterpolator.DEFAULT);
                    this.finishZoomTransition.start();
                    return;
                }
                return;
            }
            return;
        }
        float f = this.cameraZoom;
        if (f > 0.0f) {
            ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(f, this.useCamera2 ? 1.0f : 0.0f);
            this.finishZoomTransition = valueAnimatorOfFloat2;
            valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda6
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f$0.lambda$finishZoom$18(valueAnimator);
                }
            });
            this.finishZoomTransition.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.InstantCameraView.14
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    InstantCameraView instantCameraView = InstantCameraView.this;
                    if (instantCameraView.finishZoomTransition != null) {
                        instantCameraView.finishZoomTransition = null;
                    }
                }
            });
            this.finishZoomTransition.setDuration(350L);
            this.finishZoomTransition.setInterpolator(CubicBezierInterpolator.DEFAULT);
            this.finishZoomTransition.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishZoom$17(ValueAnimator valueAnimator) {
        if (this.cameraXSession != null) {
            this.cameraXSession.setZoomRatio(((Float) valueAnimator.getAnimatedValue()).floatValue());
            this.cameraZoom = this.cameraXSession.getLinearZoom();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishZoom$18(ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.cameraZoom = fFloatValue;
        if (this.useCamera2) {
            Camera2Session camera2Session = this.camera2SessionCurrent;
            if (camera2Session != null) {
                camera2Session.setZoom(fFloatValue);
                return;
            }
            return;
        }
        CameraSession cameraSession = this.cameraSession;
        if (cameraSession != null) {
            cameraSession.setZoom(fFloatValue);
        }
    }

    private void adjustZoom(boolean z) {
        float fClamp;
        if (isCameraReady()) {
            ValueAnimator valueAnimator = this.zoomAnimator;
            if (valueAnimator == null || !valueAnimator.isRunning()) {
                float f = 1.0f;
                if (ExteraConfig.getCameraType() == CameraType.CAMERA_X) {
                    float zoomRatio = this.cameraXSession.getZoomRatio();
                    float minZoomRatio = this.cameraXSession.getMinZoomRatio();
                    float maxZoomRatio = this.cameraXSession.getMaxZoomRatio();
                    double d = maxZoomRatio;
                    int iRound = (int) Math.round(Math.log(d) / Math.log(1.75d));
                    if (iRound < 1) {
                        iRound = 1;
                    }
                    float fPow = (float) Math.pow(d, 1.0d / ((double) iRound));
                    if (z) {
                        if (zoomRatio >= 1.0f) {
                            f = zoomRatio * fPow;
                        }
                    } else if (zoomRatio > 1.0f) {
                        float f2 = zoomRatio / fPow;
                        if (f2 >= 1.0f) {
                            f = f2;
                        }
                    } else {
                        f = minZoomRatio;
                    }
                    ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(zoomRatio, Utilities.clamp(f, maxZoomRatio, minZoomRatio));
                    this.zoomAnimator = valueAnimatorOfFloat;
                    valueAnimatorOfFloat.setDuration(175L);
                    this.zoomAnimator.setInterpolator(CubicBezierInterpolator.DEFAULT);
                    this.zoomAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda2
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            this.f$0.lambda$adjustZoom$19(valueAnimator2);
                        }
                    });
                    this.zoomAnimator.start();
                    return;
                }
                float f3 = this.cameraZoom;
                if (this.useCamera2) {
                    Camera2Session camera2Session = this.camera2SessionCurrent;
                    if (camera2Session == null) {
                        return;
                    }
                    float minZoom = camera2Session.getMinZoom();
                    float maxZoom = this.camera2SessionCurrent.getMaxZoom();
                    double d2 = maxZoom;
                    int iRound2 = (int) Math.round(Math.log(d2) / Math.log(1.75d));
                    if (iRound2 < 1) {
                        iRound2 = 1;
                    }
                    float fPow2 = (float) Math.pow(d2, 1.0d / ((double) iRound2));
                    float f4 = this.cameraZoom;
                    if (z) {
                        f = f4 * fPow2;
                    } else {
                        float f5 = f4 / fPow2;
                        if (f5 >= 1.0f) {
                            f = f5;
                        }
                    }
                    fClamp = Utilities.clamp(f, maxZoom, minZoom);
                } else {
                    fClamp = Utilities.clamp(f3 + ((z ? 1 : -1) * 0.125f), 1.0f, 0.0f);
                }
                float f6 = this.cameraZoom;
                if (f6 == fClamp) {
                    return;
                }
                ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(f6, fClamp);
                this.zoomAnimator = valueAnimatorOfFloat2;
                valueAnimatorOfFloat2.setDuration(175L);
                this.zoomAnimator.setInterpolator(CubicBezierInterpolator.DEFAULT);
                this.zoomAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda3
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        this.f$0.lambda$adjustZoom$20(valueAnimator2);
                    }
                });
                this.zoomAnimator.start();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$adjustZoom$19(ValueAnimator valueAnimator) {
        if (this.cameraXSession != null) {
            this.cameraXSession.setZoomRatio(((Float) valueAnimator.getAnimatedValue()).floatValue());
            this.cameraZoom = this.cameraXSession.getLinearZoom();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$adjustZoom$20(ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.cameraZoom = fFloatValue;
        if (this.useCamera2) {
            Camera2Session camera2Session = this.camera2SessionCurrent;
            if (camera2Session != null) {
                camera2Session.setZoom(fFloatValue);
                return;
            }
            return;
        }
        CameraSession cameraSession = this.cameraSession;
        if (cameraSession != null) {
            cameraSession.setZoom(fFloatValue);
        }
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0 && i == 24) {
            adjustZoom(true);
            return true;
        }
        if (keyEvent.getAction() == 0 && i == 25) {
            adjustZoom(false);
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    public boolean isCameraReady() {
        boolean zIsCameraSessionInitiated;
        if (ExteraConfig.getCameraType() == CameraType.CAMERA_X) {
            CameraXSession cameraXSession = this.cameraXSession;
            zIsCameraSessionInitiated = cameraXSession != null && cameraXSession.isReady();
        } else {
            zIsCameraSessionInitiated = isCameraSessionInitiated();
        }
        return this.cameraReady && zIsCameraSessionInitiated && this.cameraThread != null;
    }
}
