package org.telegram.ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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
import androidx.activity.OnBackPressedDispatcher$$ExternalSyntheticNonNull0;
import androidx.camera.core.Preview;
import androidx.camera.core.SurfaceOrientedMeteringPointFactory;
import androidx.camera.core.SurfaceRequest;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.util.Consumer;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.camera.CameraXSession;
import com.exteragram.messenger.utils.system.SystemUtils;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.sun.jna.Function;
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
import org.mvel2.asm.Opcodes;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.AutoDeleteMediaTask;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.DispatchQueue;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.R;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.VideoEditedInfo;
import org.telegram.messenger.camera.Camera2Session;
import org.telegram.messenger.camera.CameraController;
import org.telegram.messenger.camera.CameraInfo;
import org.telegram.messenger.camera.CameraSession;
import org.telegram.messenger.video.MP4Builder;
import org.telegram.messenger.video.Mp4Movie;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.Components.VideoPlayer;
import org.telegram.ui.Components.blur3.BlurredBackgroundDrawableViewFactory;
import org.telegram.ui.Components.blur3.drawable.BlurredBackgroundDrawable;
import org.telegram.ui.Components.blur3.drawable.color.BlurredBackgroundColorProvider;
import org.telegram.ui.Components.voip.CellFlickerDrawable;
import org.telegram.ui.Stories.recorder.DualCameraView;
import org.telegram.ui.Stories.recorder.FlashViews;
import org.telegram.ui.Stories.recorder.SliderView;
import org.telegram.ui.Stories.recorder.StoryEntry;
import org.webrtc.EglBase;

/* JADX INFO: loaded from: classes5.dex */
public class InstantCameraView extends FrameLayout implements NotificationCenter.NotificationCenterDelegate {
    private static final int[] ALLOW_BIG_CAMERA_WHITELIST = {285904780, -1394191079};
    public boolean WRITE_TO_FILE_IN_BACKGROUND;
    private boolean allowSendingWhileRecording;
    private float animationTranslationY;
    private AnimatorSet animatorSet;
    private org.telegram.messenger.camera.Size aspectRatio;
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
    private byte[] iv;
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
    private org.telegram.messenger.camera.Size oldTexturePreviewSize;
    private FloatBuffer oldTextureTextureBuffer;
    public boolean opened;
    private Paint paint;
    private float panTranslationY;
    private View parentView;
    private org.telegram.messenger.camera.Size pictureSize;
    float pinchStartDistance;
    private int pointerId1;
    private int pointerId2;
    private final int[] position;
    private File previewFile;
    private org.telegram.messenger.camera.Size[] previewSize;
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

    public InstantCameraView(final Context context, Delegate delegate, final Theme.ResourcesProvider resourcesProvider, boolean z) {
        super(context);
        this.currentAccount = UserConfig.selectedAccount;
        this.isFrontface = true;
        this.position = new int[2];
        this.cameraTexture = new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE};
        this.oldCameraTexture = new int[1];
        this.cameraTextureAlpha = 1.0f;
        this.previewSize = new org.telegram.messenger.camera.Size[2];
        this.aspectRatio = SharedConfig.roundCamera16to9 ? new org.telegram.messenger.camera.Size(16, 9) : new org.telegram.messenger.camera.Size(4, 3);
        this.useCamera2 = ExteraConfig.cameraType == 1;
        this.camera2Sessions = new Camera2Session[2];
        this.mMVPMatrix = new float[16];
        this.mSTMatrix = new float[16];
        this.moldSTMatrix = new float[16];
        this.initialCameraZoom = 0.0f;
        this.buttonsSizePx = AndroidUtilities.dp(z ? 24.0f : 28.0f);
        this.WRITE_TO_FILE_IN_BACKGROUND = false;
        this.resourcesProvider = resourcesProvider;
        this.parentView = delegate.getFragmentView();
        setWillNotDraw(false);
        this.delegate = delegate;
        this.recordingGuid = delegate.getClassGuid();
        this.isSecretChat = delegate.isSecretChat();
        AnonymousClass1 anonymousClass1 = new Paint(1) { // from class: org.telegram.ui.Components.InstantCameraView.1
            AnonymousClass1(int i) {
                super(i);
            }

            @Override // android.graphics.Paint
            public void setAlpha(int i) {
                super.setAlpha(i);
                InstantCameraView.this.invalidate();
            }
        };
        this.paint = anonymousClass1;
        anonymousClass1.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeCap(Paint.Cap.ROUND);
        this.paint.setStrokeWidth(AndroidUtilities.dp(3.0f));
        this.paint.setColor(-1);
        this.rect = new RectF();
        FlashViews flashViews = new FlashViews(getContext(), null, this, null);
        this.flashViews = flashViews;
        flashViews.setWarmth(ExteraConfig.flashWarmth);
        flashViews.setIntensity(ExteraConfig.flashIntensity);
        addView(flashViews.backgroundView, LayoutHelper.createFrame(-1, -1, Opcodes.DNEG));
        this.scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() { // from class: org.telegram.ui.Components.InstantCameraView.2
            AnonymousClass2() {
            }

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
        AnonymousClass3 anonymousClass3 = new InstantViewCameraContainer(context) { // from class: org.telegram.ui.Components.InstantCameraView.3
            AnonymousClass3(final Context context2) {
                super(context2);
            }

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
        this.cameraContainer = anonymousClass3;
        anonymousClass3.setOutlineProvider(new ViewOutlineProvider() { // from class: org.telegram.ui.Components.InstantCameraView.4
            AnonymousClass4() {
            }

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
        int i = AndroidUtilities.roundPlayingMessageSize;
        addView(view, new FrameLayout.LayoutParams(i, i, 17));
        addView(flashViews.foregroundView, LayoutHelper.createFrame(-1, -1, Opcodes.DNEG));
        LinearLayout linearLayout = new LinearLayout(context2);
        this.buttonsLayout = linearLayout;
        linearLayout.setPadding(AndroidUtilities.dp(6.0f), AndroidUtilities.dp(6.0f), AndroidUtilities.dp(6.0f), AndroidUtilities.dp(6.0f));
        linearLayout.setOrientation(0);
        addView(linearLayout, LayoutHelper.createFrame(-2, 56.0f, 83, 1.0f, 0.0f, 0.0f, 0.0f));
        FlashViews.ImageViewInvertable imageViewInvertable = new FlashViews.ImageViewInvertable(context2);
        this.switchCameraButton = imageViewInvertable;
        ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER;
        imageViewInvertable.setScaleType(scaleType);
        imageViewInvertable.setContentDescription(LocaleController.getString(R.string.AccDescrSwitchCamera));
        linearLayout.addView(imageViewInvertable, LayoutHelper.createLinear(44, 44));
        imageViewInvertable.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$new$2(context2, view2);
            }
        });
        FlashViews.ImageViewInvertable imageViewInvertable2 = new FlashViews.ImageViewInvertable(context2);
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
        ImageView imageView = new ImageView(context2);
        this.muteImageView = imageView;
        imageView.setScaleType(scaleType);
        this.muteImageView.setImageResource(R.drawable.video_mute);
        this.muteImageView.setAlpha(0.0f);
        addView(this.muteImageView, LayoutHelper.createFrame(48, 48, 17));
        Paint paint = new Paint(1);
        paint.setColor(ColorUtils.setAlphaComponent(-16777216, 40));
        AnonymousClass7 anonymousClass7 = new BackupImageView(getContext()) { // from class: org.telegram.ui.Components.InstantCameraView.7
            CellFlickerDrawable flickerDrawable = new CellFlickerDrawable();
            final /* synthetic */ Paint val$blackoutPaint;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass7(Context context2, Paint paint2) {
                super(context2);
                paint = paint2;
                this.flickerDrawable = new CellFlickerDrawable();
            }

            @Override // org.telegram.ui.Components.BackupImageView, android.view.View
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                if (InstantCameraView.this.needDrawFlickerStub) {
                    this.flickerDrawable.setParentWidth(InstantCameraView.this.textureViewSize);
                    RectF rectF = AndroidUtilities.rectTmp;
                    rectF.set(0.0f, 0.0f, InstantCameraView.this.textureViewSize, InstantCameraView.this.textureViewSize);
                    float fWidth = rectF.width() / 2.0f;
                    canvas.drawRoundRect(rectF, fWidth, fWidth, paint);
                    rectF.inset(AndroidUtilities.dp(1.0f), AndroidUtilities.dp(1.0f));
                    this.flickerDrawable.draw(canvas, rectF, fWidth, null);
                    invalidate();
                }
            }
        };
        this.textureOverlayView = anonymousClass7;
        int i2 = AndroidUtilities.roundPlayingMessageSize;
        addView(anonymousClass7, new FrameLayout.LayoutParams(i2, i2, 17));
        this.setVisibilityFromPause = false;
        setVisibility(4);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.InstantCameraView$1 */
    class AnonymousClass1 extends Paint {
        AnonymousClass1(int i) {
            super(i);
        }

        @Override // android.graphics.Paint
        public void setAlpha(int i) {
            super.setAlpha(i);
            InstantCameraView.this.invalidate();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.InstantCameraView$2 */
    class AnonymousClass2 extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        AnonymousClass2() {
        }

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
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.InstantCameraView$3 */
    class AnonymousClass3 extends InstantViewCameraContainer {
        AnonymousClass3(final Context context2) {
            super(context2);
        }

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
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.InstantCameraView$4 */
    class AnonymousClass4 extends ViewOutlineProvider {
        AnonymousClass4() {
        }

        @Override // android.view.ViewOutlineProvider
        public void getOutline(View view, Outline outline) {
            outline.setOval(0, 0, InstantCameraView.this.textureViewSize, InstantCameraView.this.textureViewSize);
        }
    }

    public /* synthetic */ boolean lambda$new$0(View view, MotionEvent motionEvent) {
        CameraXSession cameraXSession;
        if (motionEvent.getAction() != 0 || (cameraXSession = this.cameraXSession) == null) {
            return false;
        }
        cameraXSession.focusToPoint(motionEvent.getX(), motionEvent.getY());
        return false;
    }

    public /* synthetic */ void lambda$new$2(Context context, View view) {
        if (isCameraReady()) {
            final boolean z = ExteraConfig.cameraType == 2 && DualCameraView.roundDualAvailableStatic(context);
            if (ExteraConfig.cameraType != 2) {
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
            if (ExteraConfig.cameraType != 2 || DualCameraView.roundDualAvailableStatic(context)) {
                this.flipAnimationInProgress = true;
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                valueAnimatorOfFloat.setDuration(450L);
                valueAnimatorOfFloat.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                boolean[] zArr = new boolean[1];
                Runnable runnable = new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda15
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$new$1(z);
                    }
                };
                this.cameraContainer.setCameraDistance(r6.getMeasuredHeight() * 8.0f);
                this.textureOverlayView.setCameraDistance(r6.getMeasuredHeight() * 8.0f);
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.InstantCameraView.5
                    final /* synthetic */ boolean[] val$didSwap;
                    final /* synthetic */ Runnable val$doSwap;

                    AnonymousClass5(boolean[] zArr2, Runnable runnable2) {
                        zArr = zArr2;
                        runnable = runnable2;
                    }

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
                    final /* synthetic */ boolean[] val$didSwap;
                    final /* synthetic */ Runnable val$doSwap;

                    AnonymousClass6(boolean[] zArr2, Runnable runnable2) {
                        zArr = zArr2;
                        runnable = runnable2;
                    }

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

    public /* synthetic */ void lambda$new$1(boolean z) {
        if (this.bothCameras && ExteraConfig.cameraType != 2) {
            switchCamera();
        } else if (z) {
            switchCameraX();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.InstantCameraView$5 */
    class AnonymousClass5 implements ValueAnimator.AnimatorUpdateListener {
        final /* synthetic */ boolean[] val$didSwap;
        final /* synthetic */ Runnable val$doSwap;

        AnonymousClass5(boolean[] zArr2, Runnable runnable2) {
            zArr = zArr2;
            runnable = runnable2;
        }

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
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.InstantCameraView$6 */
    class AnonymousClass6 extends AnimatorListenerAdapter {
        final /* synthetic */ boolean[] val$didSwap;
        final /* synthetic */ Runnable val$doSwap;

        AnonymousClass6(boolean[] zArr2, Runnable runnable2) {
            zArr = zArr2;
            runnable = runnable2;
        }

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
    }

    public /* synthetic */ void lambda$new$3(View view) {
        this.flashing = !this.flashing;
        updateFlash();
    }

    public /* synthetic */ boolean lambda$new$7(Theme.ResourcesProvider resourcesProvider, View view) {
        if (!this.isFrontface || !isCameraReady()) {
            return false;
        }
        final boolean z = this.flashing;
        if (!z) {
            this.flashing = true;
            updateFlash();
        }
        this.itemOptions = ItemOptions.makeOptions(this, resourcesProvider, this.flashButton).addView(new SliderView(getContext(), 1).setValue(ExteraConfig.flashWarmth).setOnValueChange(new Utilities.Callback() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda12
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$new$4((Float) obj);
            }
        })).addSpaceGap().addView(new SliderView(getContext(), 2).setMinMax(0.5f, 1.0f).setValue(ExteraConfig.flashIntensity).setOnValueChange(new Utilities.Callback() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda13
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$new$5((Float) obj);
            }
        })).setOnDismiss(new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$6(z);
            }
        }).setDimAlpha(50).setGravity(5).translate(AndroidUtilities.dp(46.0f), AndroidUtilities.dp(4.0f)).setBackgroundColor(-1155851493).show();
        return true;
    }

    public /* synthetic */ void lambda$new$4(Float f) {
        SharedPreferences.Editor editor = ExteraConfig.editor;
        float fFloatValue = f.floatValue();
        ExteraConfig.flashWarmth = fFloatValue;
        editor.putFloat("flashWarmth", fFloatValue).apply();
        this.flashViews.setWarmth(f.floatValue());
    }

    public /* synthetic */ void lambda$new$5(Float f) {
        SharedPreferences.Editor editor = ExteraConfig.editor;
        float fFloatValue = f.floatValue();
        ExteraConfig.flashIntensity = fFloatValue;
        editor.putFloat("flashIntensity", fFloatValue).apply();
        this.flashViews.setIntensity(f.floatValue());
    }

    public /* synthetic */ void lambda$new$6(boolean z) {
        if (z) {
            return;
        }
        this.flashing = false;
        updateFlash();
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.InstantCameraView$7 */
    class AnonymousClass7 extends BackupImageView {
        CellFlickerDrawable flickerDrawable = new CellFlickerDrawable();
        final /* synthetic */ Paint val$blackoutPaint;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass7(Context context2, Paint paint2) {
            super(context2);
            paint = paint2;
            this.flickerDrawable = new CellFlickerDrawable();
        }

        @Override // org.telegram.ui.Components.BackupImageView, android.view.View
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (InstantCameraView.this.needDrawFlickerStub) {
                this.flickerDrawable.setParentWidth(InstantCameraView.this.textureViewSize);
                RectF rectF = AndroidUtilities.rectTmp;
                rectF.set(0.0f, 0.0f, InstantCameraView.this.textureViewSize, InstantCameraView.this.textureViewSize);
                float fWidth = rectF.width() / 2.0f;
                canvas.drawRoundRect(rectF, fWidth, fWidth, paint);
                rectF.inset(AndroidUtilities.dp(1.0f), AndroidUtilities.dp(1.0f));
                this.flickerDrawable.draw(canvas, rectF, fWidth, null);
                invalidate();
            }
        }
    }

    public void setButtonsBackground(BlurredBackgroundDrawableViewFactory blurredBackgroundDrawableViewFactory, BlurredBackgroundColorProvider blurredBackgroundColorProvider) {
        BlurredBackgroundDrawable blurredBackgroundDrawableCreate = blurredBackgroundDrawableViewFactory.create(this.buttonsLayout, blurredBackgroundColorProvider);
        blurredBackgroundDrawableCreate.setPadding(AndroidUtilities.dp(6.0f));
        blurredBackgroundDrawableCreate.setRadius(AndroidUtilities.dp(21.0f));
        this.buttonsLayout.setBackground(blurredBackgroundDrawableCreate);
    }

    public void updateFlash() {
        CameraXSession cameraXSession;
        boolean z = this.flashing && this.recording && this.isFrontface;
        if (this.frontFlashing != z) {
            this.frontFlashing = z;
            if (z) {
                this.flashViews.flashIn(null);
            } else {
                this.flashViews.flashOut();
            }
        }
        boolean z2 = this.flashing && this.recording;
        if (ExteraConfig.cameraType != 2) {
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
                this.flashButton.setContentDescription(LocaleController.getString(this.flashing ? R.string.AccDescrCameraFlashOff : R.string.AccDescrCameraFlashOn));
                if (!this.flashing) {
                    if (this.flashOnDrawable == null) {
                        int i = R.raw.roundcamera_flash_on;
                        int i2 = this.buttonsSizePx;
                        RLottieDrawable rLottieDrawable = new RLottieDrawable(i, "roundcamera_flash_on", i2, i2);
                        this.flashOnDrawable = rLottieDrawable;
                        rLottieDrawable.setCallback(this.flashButton);
                    }
                    this.flashButton.setImageDrawable(this.flashOnDrawable);
                    if (this.wasFlashing == null) {
                        RLottieDrawable rLottieDrawable2 = this.flashOnDrawable;
                        rLottieDrawable2.setCurrentFrame(rLottieDrawable2.getFramesCount() - 1);
                    } else {
                        this.flashOnDrawable.setCurrentFrame(0);
                        this.flashOnDrawable.start();
                    }
                } else {
                    if (this.flashOffDrawable == null) {
                        int i3 = R.raw.roundcamera_flash_off;
                        int i4 = this.buttonsSizePx;
                        RLottieDrawable rLottieDrawable3 = new RLottieDrawable(i3, "roundcamera_flash_off", i4, i4);
                        this.flashOffDrawable = rLottieDrawable3;
                        rLottieDrawable3.setCallback(this.flashButton);
                    }
                    this.flashButton.setImageDrawable(this.flashOffDrawable);
                    if (this.wasFlashing == null) {
                        RLottieDrawable rLottieDrawable4 = this.flashOffDrawable;
                        rLottieDrawable4.setCurrentFrame(rLottieDrawable4.getFramesCount() - 1);
                    } else {
                        this.flashOffDrawable.setCurrentFrame(0);
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
    protected void onMeasure(int i, int i2) {
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
                ((FrameLayout.LayoutParams) this.muteImageView.getLayoutParams()).topMargin = (this.textureViewSize / 2) - AndroidUtilities.dp(24.0f);
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
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (getVisibility() != 0) {
            this.animationTranslationY = getMeasuredHeight() / 2.0f;
            updateTranslationY();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.fileUploaded);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
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
                this.iv = (byte[]) objArr[4];
            }
        }
    }

    public void destroy(boolean z) {
        if (ExteraConfig.cameraType != 2) {
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
    protected void onDraw(Canvas canvas) {
        float x = this.cameraContainer.getX();
        float y = this.cameraContainer.getY();
        this.rect.set(x - AndroidUtilities.dp(8.0f), y - AndroidUtilities.dp(8.0f), x + this.cameraContainer.getMeasuredWidth() + AndroidUtilities.dp(8.0f), y + this.cameraContainer.getMeasuredHeight() + AndroidUtilities.dp(8.0f));
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
            FileLog.e(e);
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
            int i = R.raw.roundcamera_flip;
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
        if (bitmap != null) {
            this.textureOverlayView.setImageBitmap(bitmap);
        } else {
            this.textureOverlayView.setImageResource(R.drawable.icplaceholder);
        }
        this.cameraReady = false;
        this.selectedCamera = null;
        if (!z) {
            int i3 = ExteraConfig.videoMessagesCamera;
            if (i3 != 2) {
                this.isFrontface = i3 == 0;
            }
            updateFlash();
            this.recordedTime = 0L;
            this.progress = 0.0f;
        }
        this.cancelled = false;
        this.file = null;
        this.encryptedFile = null;
        this.key = null;
        this.iv = null;
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
                    AnonymousClass8(File file, String str) {
                        super(file, str);
                    }

                    @Override // java.io.File
                    public boolean delete() {
                        if (BuildVars.LOGS_ENABLED) {
                            FileLog.e("delete camera file");
                        }
                        return super.delete();
                    }
                };
            }
            SharedConfig.saveConfig();
            AutoDeleteMediaTask.lockFile(this.cameraFile);
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("InstantCamera show round camera " + this.cameraFile.getAbsolutePath());
            }
            if (this.useCamera2) {
                boolean zRoundDualAvailableStatic = DualCameraView.roundDualAvailableStatic(getContext());
                this.bothCameras = zRoundDualAvailableStatic;
                if (zRoundDualAvailableStatic) {
                    int i4 = 0;
                    while (i4 < 2) {
                        Camera2Session[] camera2SessionArr = this.camera2Sessions;
                        if (camera2SessionArr[i4] == null) {
                            camera2SessionArr[i4] = Camera2Session.create(i4 == 0, SystemUtils.getRoundVideoResolution(), SystemUtils.getRoundVideoResolution());
                            Camera2Session camera2Session = this.camera2Sessions[i4];
                            if (camera2Session != null) {
                                camera2Session.setRecordingVideo(true);
                                this.previewSize[i4] = new org.telegram.messenger.camera.Size(this.camera2Sessions[i4].getPreviewWidth(), this.camera2Sessions[i4].getPreviewHeight());
                            }
                        }
                        i4++;
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
                    int i5 = !z3 ? 1 : 0;
                    Camera2Session camera2SessionCreate = Camera2Session.create(z3, SystemUtils.getRoundVideoResolution(), SystemUtils.getRoundVideoResolution());
                    camera2SessionArr3[i5] = camera2SessionCreate;
                    this.camera2SessionCurrent = camera2SessionCreate;
                    if (camera2SessionCreate == null) {
                        return;
                    }
                    camera2SessionCreate.setRecordingVideo(true);
                    this.previewSize[0] = new org.telegram.messenger.camera.Size(this.camera2SessionCurrent.getPreviewWidth(), this.camera2SessionCurrent.getPreviewHeight());
                }
            } else if (ExteraConfig.cameraType == 2) {
                boolean zRoundDualAvailableStatic2 = DualCameraView.roundDualAvailableStatic(getContext());
                this.bothCameras = zRoundDualAvailableStatic2;
                if (zRoundDualAvailableStatic2) {
                    this.surfaceIndex = !this.isFrontface ? 1 : 0;
                }
                if (this.previewSize[0] == null) {
                    int roundVideoResolution = SystemUtils.getRoundVideoResolution();
                    this.previewSize[0] = new org.telegram.messenger.camera.Size(roundVideoResolution, roundVideoResolution);
                }
                org.telegram.messenger.camera.Size[] sizeArr = this.previewSize;
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

                AnonymousClass9() {
                }

                @Override // android.view.TextureView.SurfaceTextureListener
                public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i6, int i7) {
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.d("InstantCamera camera surface available");
                    }
                    if (InstantCameraView.this.cameraThread != null || surfaceTexture == null || InstantCameraView.this.cancelled) {
                        return;
                    }
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.d("InstantCamera start create thread");
                    }
                    InstantCameraView.this.cameraThread = InstantCameraView.this.new CameraGLThread(surfaceTexture, i6, i7);
                }

                @Override // android.view.TextureView.SurfaceTextureListener
                public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i6, int i7) {
                    if (InstantCameraView.this.cameraThread != null) {
                        InstantCameraView.this.cameraThread.surfaceWidth = i6;
                        InstantCameraView.this.cameraThread.surfaceHeight = i7;
                        InstantCameraView.this.cameraThread.updateScale();
                    }
                }

                @Override // android.view.TextureView.SurfaceTextureListener
                public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                    if (InstantCameraView.this.cameraThread != null) {
                        InstantCameraView.this.cameraThread.shutdown(0, true, 0, 0, 0, 0L);
                        InstantCameraView.this.cameraThread = null;
                    }
                    if (ExteraConfig.cameraType != 2) {
                        if (InstantCameraView.this.useCamera2) {
                            for (int i6 = 0; i6 < InstantCameraView.this.camera2Sessions.length; i6++) {
                                if (InstantCameraView.this.camera2Sessions[i6] != null) {
                                    InstantCameraView.this.camera2Sessions[i6].destroy(false);
                                    InstantCameraView.this.camera2Sessions[i6] = null;
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
                        InstantCameraView.this.cameraXSession.closeCamera();
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

    /* JADX INFO: renamed from: org.telegram.ui.Components.InstantCameraView$8 */
    class AnonymousClass8 extends File {
        AnonymousClass8(File file, String str) {
            super(file, str);
        }

        @Override // java.io.File
        public boolean delete() {
            if (BuildVars.LOGS_ENABLED) {
                FileLog.e("delete camera file");
            }
            return super.delete();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.InstantCameraView$9 */
    class AnonymousClass9 implements TextureView.SurfaceTextureListener {
        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }

        AnonymousClass9() {
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i6, int i7) {
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("InstantCamera camera surface available");
            }
            if (InstantCameraView.this.cameraThread != null || surfaceTexture == null || InstantCameraView.this.cancelled) {
                return;
            }
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("InstantCamera start create thread");
            }
            InstantCameraView.this.cameraThread = InstantCameraView.this.new CameraGLThread(surfaceTexture, i6, i7);
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i6, int i7) {
            if (InstantCameraView.this.cameraThread != null) {
                InstantCameraView.this.cameraThread.surfaceWidth = i6;
                InstantCameraView.this.cameraThread.surfaceHeight = i7;
                InstantCameraView.this.cameraThread.updateScale();
            }
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            if (InstantCameraView.this.cameraThread != null) {
                InstantCameraView.this.cameraThread.shutdown(0, true, 0, 0, 0, 0L);
                InstantCameraView.this.cameraThread = null;
            }
            if (ExteraConfig.cameraType != 2) {
                if (InstantCameraView.this.useCamera2) {
                    for (int i6 = 0; i6 < InstantCameraView.this.camera2Sessions.length; i6++) {
                        if (InstantCameraView.this.camera2Sessions[i6] != null) {
                            InstantCameraView.this.camera2Sessions[i6].destroy(false);
                            InstantCameraView.this.camera2Sessions[i6] = null;
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
                InstantCameraView.this.cameraXSession.closeCamera();
                return true;
            } catch (Exception unused2) {
                return true;
            }
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
        float fDp = (z || this.recordedTime <= 300) ? 0.0f : AndroidUtilities.dp(24.0f) - (getMeasuredWidth() / 2.0f);
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(z ? 1.0f : 0.0f, z ? 0.0f : 1.0f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda11
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
        ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(this.paint, (Property<Paint, Integer>) AnimationProperties.PAINT_ALPHA, z ? Function.USE_VARARGS : 0);
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
        animatorSet2.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2, objectAnimatorOfInt, objectAnimatorOfFloat3, objectAnimatorOfFloat4, objectAnimatorOfFloat5, ObjectAnimator.ofFloat(this.cameraContainer, (Property<InstantViewCameraContainer, Float>) property4, fDp), ObjectAnimator.ofFloat(this.textureOverlayView, (Property<BackupImageView, Float>) property, z ? 1.0f : 0.0f), ObjectAnimator.ofFloat(this.textureOverlayView, (Property<BackupImageView, Float>) property2, z ? 1.0f : 0.1f), ObjectAnimator.ofFloat(this.textureOverlayView, (Property<BackupImageView, Float>) property3, z ? 1.0f : 0.1f), ObjectAnimator.ofFloat(this.textureOverlayView, (Property<BackupImageView, Float>) property4, fDp), valueAnimatorOfFloat);
        if (!z) {
            this.animatorSet.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.InstantCameraView.10
                AnonymousClass10() {
                }

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

    public /* synthetic */ void lambda$startAnimation$8(boolean z, ValueAnimator valueAnimator) {
        this.animationTranslationY = z ? 0.0f : (getMeasuredHeight() / 2.0f) * ((Float) valueAnimator.getAnimatedValue()).floatValue();
        updateTranslationY();
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.InstantCameraView$10 */
    class AnonymousClass10 extends AnimatorListenerAdapter {
        AnonymousClass10() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (animator.equals(InstantCameraView.this.animatorSet)) {
                InstantCameraView.this.hideCamera(true);
                InstantCameraView.this.setVisibilityFromPause = false;
                InstantCameraView.this.setVisibility(4);
            }
        }
    }

    private void updateTranslationY() {
        this.textureOverlayView.setTranslationY(this.animationTranslationY + this.panTranslationY);
        this.cameraContainer.setTranslationY(this.animationTranslationY + this.panTranslationY);
    }

    public Rect getCameraRect() {
        this.cameraContainer.getLocationOnScreen(this.position);
        int[] iArr = this.position;
        return new Rect(iArr[0], iArr[1], this.cameraContainer.getWidth(), this.cameraContainer.getHeight());
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
                FileLog.e(new RuntimeException("file not found :( round video"));
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
                this.iv = null;
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
                videoEditedInfo3.bitrate = 1000000;
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
            videoEditedInfo4.iv = this.iv;
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
                InstantCameraView.m8561$r8$lambda$KtyiQd9EHalYVOSGhjvMBJNtes(bitmapCreateScaledBitmap);
            }
        });
    }

    /* JADX INFO: renamed from: $r8$lambda$Kty-iQd9EHalYVOSGhjvMBJNtes */
    public static /* synthetic */ void m8561$r8$lambda$KtyiQd9EHalYVOSGhjvMBJNtes(Bitmap bitmap) {
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
                FileLog.e("delete camera file by cancel");
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
        if (ExteraConfig.rememberLastUsedCamera && ExteraConfig.videoMessagesCamera != 2) {
            SharedPreferences.Editor editor = ExteraConfig.editor;
            boolean z = this.isFrontface;
            ExteraConfig.videoMessagesCamera = z ? 1 : 0;
            editor.putInt("videoMessagesCamera", z ? 1 : 0).apply();
        }
        this.isFrontface = !this.isFrontface;
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
            boolean z2 = this.isFrontface;
            int i = !z2 ? 1 : 0;
            Camera2Session camera2SessionCreate = Camera2Session.create(z2, SystemUtils.getRoundVideoResolution(), SystemUtils.getRoundVideoResolution());
            camera2SessionArr[i] = camera2SessionCreate;
            this.camera2SessionCurrent = camera2SessionCreate;
            if (camera2SessionCreate == null) {
                return;
            }
            camera2SessionCreate.setRecordingVideo(true);
            this.previewSize[0] = new org.telegram.messenger.camera.Size(this.camera2SessionCurrent.getPreviewWidth(), this.camera2SessionCurrent.getPreviewHeight());
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

    /* JADX WARN: Multi-variable type inference failed */
    private void switchCameraX() {
        if (ExteraConfig.cameraType != 2) {
            return;
        }
        CameraXSession cameraXSession = this.cameraXSession;
        byte b = cameraXSession != null && cameraXSession.isDualMode();
        this.isFrontface = true ^ this.isFrontface;
        updateFlash();
        if (b != false) {
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
        if (ExteraConfig.rememberLastUsedCamera && ExteraConfig.videoMessagesCamera != 2) {
            SharedPreferences.Editor editor = ExteraConfig.editor;
            boolean z = this.isFrontface;
            ExteraConfig.videoMessagesCamera = z ? 1 : 0;
            editor.putInt("videoMessagesCamera", z ? 1 : 0).apply();
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
        ArrayList<org.telegram.messenger.camera.Size> previewSizes = cameraInfo3.getPreviewSizes();
        ArrayList<org.telegram.messenger.camera.Size> pictureSizes = this.selectedCamera.getPictureSizes();
        this.previewSize[0] = chooseOptimalSize(previewSizes);
        org.telegram.messenger.camera.Size sizeChooseOptimalSize = chooseOptimalSize(pictureSizes);
        this.pictureSize = sizeChooseOptimalSize;
        if (this.previewSize[0].mWidth != sizeChooseOptimalSize.mWidth) {
            boolean z = false;
            for (int size = previewSizes.size() - 1; size >= 0; size--) {
                org.telegram.messenger.camera.Size size2 = previewSizes.get(size);
                int size3 = pictureSizes.size() - 1;
                while (true) {
                    if (size3 < 0) {
                        break;
                    }
                    org.telegram.messenger.camera.Size size4 = pictureSizes.get(size3);
                    int i4 = size2.mWidth;
                    org.telegram.messenger.camera.Size size5 = this.pictureSize;
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
                    org.telegram.messenger.camera.Size size7 = previewSizes.get(size6);
                    int size8 = pictureSizes.size() - 1;
                    while (true) {
                        if (size8 < 0) {
                            break;
                        }
                        org.telegram.messenger.camera.Size size9 = pictureSizes.get(size8);
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
            FileLog.d("InstantCamera preview w = " + this.previewSize[0].mWidth + " h = " + this.previewSize[0].mHeight);
        }
        return true;
    }

    private org.telegram.messenger.camera.Size chooseOptimalSize(ArrayList arrayList) {
        ArrayList arrayList2 = new ArrayList();
        int i = Build.MANUFACTURER.equalsIgnoreCase("Samsung") ? 1200 : allowBigSizeCamera() ? 1440 : 1200;
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            if (Math.max(((org.telegram.messenger.camera.Size) arrayList.get(i2)).mHeight, ((org.telegram.messenger.camera.Size) arrayList.get(i2)).mWidth) <= i && Math.min(((org.telegram.messenger.camera.Size) arrayList.get(i2)).mHeight, ((org.telegram.messenger.camera.Size) arrayList.get(i2)).mWidth) >= 320) {
                arrayList2.add((org.telegram.messenger.camera.Size) arrayList.get(i2));
            }
        }
        if (arrayList2.isEmpty() || !allowBigSizeCamera()) {
            if (!arrayList2.isEmpty()) {
                arrayList = arrayList2;
            }
            if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
                return CameraController.chooseOptimalSize(arrayList, 640, 480, this.aspectRatio, false);
            }
            return CameraController.chooseOptimalSize(arrayList, 480, 270, this.aspectRatio, false);
        }
        Collections.sort(arrayList2, new Comparator() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda4
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return InstantCameraView.$r8$lambda$BRa4Pcs5xh0fkWGp3KPRvenlfmA((org.telegram.messenger.camera.Size) obj, (org.telegram.messenger.camera.Size) obj2);
            }
        });
        return (org.telegram.messenger.camera.Size) arrayList2.get(0);
    }

    public static /* synthetic */ int $r8$lambda$BRa4Pcs5xh0fkWGp3KPRvenlfmA(org.telegram.messenger.camera.Size size, org.telegram.messenger.camera.Size size2) {
        float fAbs = Math.abs(1.0f - (Math.min(size.mHeight, size.mWidth) / Math.max(size.mHeight, size.mWidth)));
        float fAbs2 = Math.abs(1.0f - (Math.min(size2.mHeight, size2.mWidth) / Math.max(size2.mHeight, size2.mWidth)));
        if (fAbs < fAbs2) {
            return -1;
        }
        return fAbs > fAbs2 ? 1 : 0;
    }

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

    public void createCamera(final int i, final SurfaceTexture surfaceTexture) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$createCamera$16(i, surfaceTexture);
            }
        });
    }

    public /* synthetic */ void lambda$createCamera$16(int i, SurfaceTexture surfaceTexture) {
        CameraXSession cameraXSession;
        if (this.cameraThread == null) {
            return;
        }
        if (BuildVars.LOGS_ENABLED) {
            FileLog.d("InstantCamera create camera session " + i);
        }
        if (ExteraConfig.cameraType != 2) {
            if (!this.useCamera2) {
                if (i == 1) {
                    return;
                }
                surfaceTexture.setDefaultBufferSize(this.previewSize[0].getWidth(), this.previewSize[0].getHeight());
                this.cameraSession = new CameraSession(this.selectedCamera, this.previewSize[0], this.pictureSize, 256, true);
                updateFlash();
                this.cameraThread.setCurrentSession(this.cameraSession);
                CameraController.getInstance().openRound(this.cameraSession, surfaceTexture, new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda17
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$createCamera$11();
                    }
                }, new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda18
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
        Preview.SurfaceProvider surfaceProvider = new Preview.SurfaceProvider() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda19
            @Override // androidx.camera.core.Preview.SurfaceProvider
            public final void onSurfaceRequested(SurfaceRequest surfaceRequest) {
                this.f$0.lambda$createCamera$14(surface, surfaceRequest);
            }
        };
        if (i == 0) {
            surfaceTexture.setDefaultBufferSize(this.previewSize[0].getWidth(), this.previewSize[0].getHeight());
            CameraXSession cameraXSession2 = new CameraXSession(this.camLifecycle, new SurfaceOrientedMeteringPointFactory(this.previewSize[0].getWidth(), this.previewSize[0].getHeight()), surfaceProvider);
            this.cameraXSession = cameraXSession2;
            cameraXSession2.initCamera(getContext(), this.isFrontface, this.bothCameras, new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda20
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
        org.telegram.messenger.camera.Size size = this.previewSize[0];
        if (size != null) {
            surfaceTexture.setDefaultBufferSize(size.getWidth(), this.previewSize[0].getHeight());
        }
    }

    public /* synthetic */ void lambda$createCamera$11() {
        CameraGLThread cameraGLThread;
        if (this.cameraSession != null) {
            updateFlash();
            boolean z = false;
            try {
                Camera.Size currentPreviewSize = this.cameraSession.getCurrentPreviewSize();
                if (currentPreviewSize.width != this.previewSize[0].getWidth() || currentPreviewSize.height != this.previewSize[0].getHeight()) {
                    this.previewSize[0] = new org.telegram.messenger.camera.Size(currentPreviewSize.width, currentPreviewSize.height);
                    FileLog.d("InstantCamera change preview size to w = " + this.previewSize[0].getWidth() + " h = " + this.previewSize[0].getHeight());
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
            try {
                Camera.Size currentPictureSize = this.cameraSession.getCurrentPictureSize();
                if (currentPictureSize.width != this.pictureSize.getWidth() || currentPictureSize.height != this.pictureSize.getHeight()) {
                    this.pictureSize = new org.telegram.messenger.camera.Size(currentPictureSize.width, currentPictureSize.height);
                    FileLog.d("InstantCamera change picture size to w = " + this.pictureSize.getWidth() + " h = " + this.pictureSize.getHeight());
                    z = true;
                }
            } catch (Exception e2) {
                FileLog.e(e2);
            }
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("InstantCamera camera initied");
            }
            this.cameraSession.setInitied();
            if (!z || (cameraGLThread = this.cameraThread) == null) {
                return;
            }
            cameraGLThread.reinitForNewCamera();
        }
    }

    public /* synthetic */ void lambda$createCamera$12() {
        CameraGLThread cameraGLThread = this.cameraThread;
        if (cameraGLThread != null) {
            cameraGLThread.setCurrentSession(this.cameraSession);
        }
    }

    public /* synthetic */ void lambda$createCamera$14(Surface surface, SurfaceRequest surfaceRequest) {
        surfaceRequest.provideSurface(surface, ContextCompat.getMainExecutor(getContext()), new Consumer() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda0
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                InstantCameraView.$r8$lambda$tZf71xbzpSaDnel0YRXy688JHKw((SurfaceRequest.Result) obj);
            }
        });
    }

    public /* synthetic */ void lambda$createCamera$15() {
        CameraGLThread cameraGLThread = this.cameraThread;
        if (cameraGLThread != null) {
            cameraGLThread.setOrientation();
        }
    }

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
            FileLog.e(GLES20.glGetShaderInfoLog(iGlCreateShader));
        }
        GLES20.glDeleteShader(iGlCreateShader);
        return 0;
    }

    public void startProgressTimer() {
        Timer timer = this.progressTimer;
        if (timer != null) {
            try {
                timer.cancel();
                this.progressTimer = null;
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
        Timer timer2 = new Timer();
        this.progressTimer = timer2;
        timer2.schedule(new AnonymousClass11(), 0L, 17L);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.InstantCameraView$11 */
    class AnonymousClass11 extends TimerTask {
        AnonymousClass11() {
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
                FileLog.e(e);
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
                FileLog.e(e);
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

        public void updateScale() {
            if (InstantCameraView.this.previewSize[InstantCameraView.this.surfaceIndex] != null) {
                int width = InstantCameraView.this.previewSize[InstantCameraView.this.surfaceIndex].getWidth();
                float fMin = this.surfaceWidth / Math.min(width, r1);
                int i = (int) (width * fMin);
                int height = (int) (InstantCameraView.this.previewSize[InstantCameraView.this.surfaceIndex].getHeight() * fMin);
                if (i == height) {
                    InstantCameraView.this.scaleX = 1.0f;
                    InstantCameraView.this.scaleY = 1.0f;
                } else if (i > height) {
                    InstantCameraView.this.scaleX = 1.0f;
                    InstantCameraView.this.scaleY = i / this.surfaceHeight;
                } else {
                    InstantCameraView.this.scaleX = height / this.surfaceWidth;
                    InstantCameraView.this.scaleY = 1.0f;
                }
                FileLog.d("InstantCamera camera scaleX = " + InstantCameraView.this.scaleX + " scaleY = " + InstantCameraView.this.scaleY);
            }
        }

        private boolean initGL() {
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("InstantCamera start init gl");
            }
            EGL10 egl10 = (EGL10) EGLContext.getEGL();
            this.egl10 = egl10;
            EGLDisplay eGLDisplayEglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            this.eglDisplay = eGLDisplayEglGetDisplay;
            if (eGLDisplayEglGetDisplay == EGL10.EGL_NO_DISPLAY) {
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.e("InstantCamera eglGetDisplay failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                }
                finish();
                return false;
            }
            if (!this.egl10.eglInitialize(eGLDisplayEglGetDisplay, new int[2])) {
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.e("InstantCamera eglInitialize failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                }
                finish();
                return false;
            }
            int[] iArr = new int[1];
            EGLConfig[] eGLConfigArr = new EGLConfig[1];
            if (!this.egl10.eglChooseConfig(this.eglDisplay, new int[]{12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 0, 12325, 0, 12326, 0, 12344}, eGLConfigArr, 1, iArr)) {
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.e("InstantCamera eglChooseConfig failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
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
                        FileLog.e("InstantCamera eglCreateContext failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                    }
                    finish();
                    return false;
                }
                if (OnBackPressedDispatcher$$ExternalSyntheticNonNull0.m(this.surfaceTexture)) {
                    EGLSurface eGLSurfaceEglCreateWindowSurface = this.egl10.eglCreateWindowSurface(this.eglDisplay, eGLConfig, this.surfaceTexture, null);
                    this.eglSurface = eGLSurfaceEglCreateWindowSurface;
                    if (eGLSurfaceEglCreateWindowSurface == null || eGLSurfaceEglCreateWindowSurface == EGL10.EGL_NO_SURFACE) {
                        if (BuildVars.LOGS_ENABLED) {
                            FileLog.e("InstantCamera createWindowSurface failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                        }
                        finish();
                        return false;
                    }
                    if (!this.egl10.eglMakeCurrent(this.eglDisplay, eGLSurfaceEglCreateWindowSurface, eGLSurfaceEglCreateWindowSurface, this.eglContext)) {
                        if (BuildVars.LOGS_ENABLED) {
                            FileLog.e("InstantCamera eglMakeCurrent failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
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
                                FileLog.e("InstantCamera failed link shader");
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
                            if (ExteraConfig.cameraType != 2 || i == 0 || InstantCameraView.this.bothCameras) {
                                InstantCameraView.this.createCamera(i, this.cameraSurface[i]);
                            }
                        }
                        if (BuildVars.LOGS_ENABLED) {
                            FileLog.e("InstantCamera gl initied");
                        }
                        return true;
                    }
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.e("InstantCamera failed creating shader");
                    }
                    finish();
                    return false;
                }
                finish();
                return false;
            }
            if (BuildVars.LOGS_ENABLED) {
                FileLog.e("InstantCamera eglConfig not initialized");
            }
            finish();
            return false;
        }

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

        /* JADX WARN: Removed duplicated region for block: B:147:0x0103  */
        /* JADX WARN: Removed duplicated region for block: B:152:0x010c  */
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

        public /* synthetic */ void lambda$onDraw$1() {
            InstantCameraView.this.textureOverlayView.animate().setDuration(120L).alpha(0.0f).setInterpolator(new DecelerateInterpolator()).start();
        }

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
                        FileLog.d("InstantCamera set gl renderer session");
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
                    FileLog.d("InstantCamera eglMakeCurrent failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
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

    private static class EncoderHandler extends Handler {
        private WeakReference mWeakEncoder;

        public EncoderHandler(VideoRecorder videoRecorder) {
            this.mWeakEncoder = new WeakReference(videoRecorder);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            VideoRecorder videoRecorder = (VideoRecorder) this.mWeakEncoder.get();
            if (videoRecorder == null) {
                return;
            }
            boolean z = true;
            if (i == 0) {
                try {
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.e("InstantCamera start encoder");
                    }
                    if (message.arg1 != 1) {
                        z = false;
                    }
                    videoRecorder.prepareEncoder(z);
                    return;
                } catch (Exception e) {
                    FileLog.e(e);
                    videoRecorder.handleStopRecording(0, null);
                    Looper.myLooper().quit();
                    return;
                }
            }
            if (i == 1) {
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.e("InstantCamera stop encoder");
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
                    FileLog.e("InstantCamera pause encoder");
                }
                videoRecorder.handlePauseRecording();
            } else {
                if (i != 5) {
                    return;
                }
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.e("InstantCamera resume encoder");
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

    class VideoRecorder implements Runnable {
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
        private ArrayBlockingQueue buffers;
        private ArrayList buffersToWrite;
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
        private ArrayList keyframeThumbs;
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

        /* synthetic */ VideoRecorder(InstantCameraView instantCameraView, InstantCameraViewIA instantCameraViewIA) {
            this();
        }

        private VideoRecorder() {
            this.videoConvertFirstWrite = true;
            this.eglDisplay = EGL14.EGL_NO_DISPLAY;
            this.eglContext = EGL14.EGL_NO_CONTEXT;
            this.eglSurface = EGL14.EGL_NO_SURFACE;
            this.buffersToWrite = new ArrayList();
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
            this.buffers = new ArrayBlockingQueue(10);
            this.keyframeThumbs = new ArrayList();
            this.recorderRunnable = new AnonymousClass1();
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.InstantCameraView$VideoRecorder$1 */
        class AnonymousClass1 implements Runnable {
            AnonymousClass1() {
            }

            /* JADX WARN: Code restructure failed: missing block: B:115:0x003d, code lost:
            
                if (org.telegram.ui.Components.InstantCameraView.VideoRecorder.this.sendWhenDone == 0) goto L185;
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
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.InstantCameraView.VideoRecorder.AnonymousClass1.run():void");
            }

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
                                FileLog.d("InstantCamera fix timestamp enabled");
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

        /* JADX WARN: Code restructure failed: missing block: B:199:0x0165, code lost:
        
            if (org.telegram.messenger.BuildVars.LOGS_ENABLED == false) goto L203;
         */
        /* JADX WARN: Code restructure failed: missing block: B:201:0x0169, code lost:
        
            if (r14 < 60000000) goto L206;
         */
        /* JADX WARN: Code restructure failed: missing block: B:202:0x016b, code lost:
        
            org.telegram.messenger.FileLog.d("InstantCamera stop audio encoding because recorded time more than 60s");
         */
        /* JADX WARN: Code restructure failed: missing block: B:206:0x0175, code lost:
        
            org.telegram.messenger.FileLog.d("InstantCamera stop audio encoding because of stoped video recording at " + r2.offset[r10] + " last video " + r20.videoLast);
         */
        /* JADX WARN: Code restructure failed: missing block: B:207:0x0198, code lost:
        
            r20.audioStopedByTime = true;
            r20.buffersToWrite.clear();
            r0 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:208:0x01a0, code lost:
        
            r2 = null;
            r6 = 0;
            r15 = true;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void handleAudioFrameAvailable(org.telegram.ui.Components.InstantCameraView.AudioBufferInfo r21) {
            /*
                Method dump skipped, instruction units count: 552
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.InstantCameraView.VideoRecorder.handleAudioFrameAvailable(org.telegram.ui.Components.InstantCameraView$AudioBufferInfo):void");
        }

        /* JADX WARN: Removed duplicated region for block: B:138:0x0053  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void handleVideoFrameAvailable(long r26, java.lang.Integer r28) {
            /*
                Method dump skipped, instruction units count: 790
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.InstantCameraView.VideoRecorder.handleVideoFrameAvailable(long, java.lang.Integer):void");
        }

        public /* synthetic */ void lambda$handleVideoFrameAvailable$2() {
            InstantCameraView.this.textureOverlayView.animate().setDuration(120L).alpha(0.0f).setInterpolator(new DecelerateInterpolator()).start();
        }

        public /* synthetic */ void lambda$handleVideoFrameAvailable$3() {
            InstantCameraView.this.textureOverlayView.animate().setDuration(120L).alpha(0.0f).setInterpolator(new DecelerateInterpolator()).start();
        }

        private void createKeyframeThumb() {
            if (this.generateKeyframeThumbsQueue != null && SharedConfig.getDevicePerformanceClass() == 2 && this.frameCount % 33 == 0) {
                this.generateKeyframeThumbsQueue.postRunnable(new GenerateKeyframeThumbTask());
            }
        }

        class GenerateKeyframeThumbTask implements Runnable {
            /* synthetic */ GenerateKeyframeThumbTask(VideoRecorder videoRecorder, InstantCameraViewIA instantCameraViewIA) {
                this();
            }

            private GenerateKeyframeThumbTask() {
            }

            @Override // java.lang.Runnable
            public void run() {
                TextureView textureView = InstantCameraView.this.textureView;
                if (textureView != null) {
                    try {
                        final Bitmap bitmap = textureView.getBitmap(AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
                        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.InstantCameraView$VideoRecorder$GenerateKeyframeThumbTask$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$run$0(bitmap);
                            }
                        });
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
            }

            public /* synthetic */ void lambda$run$0(Bitmap bitmap) {
                if ((bitmap == null || bitmap.getPixel(0, 0) == 0) && VideoRecorder.this.keyframeThumbs.size() > 1) {
                    VideoRecorder.this.keyframeThumbs.add((Bitmap) VideoRecorder.this.keyframeThumbs.get(VideoRecorder.this.keyframeThumbs.size() - 1));
                } else {
                    VideoRecorder.this.keyframeThumbs.add(bitmap);
                }
            }
        }

        public void handlePauseRecording() {
            this.pauseRecorder = true;
            if (InstantCameraView.this.previewFile != null) {
                InstantCameraView.this.previewFile.delete();
                InstantCameraView.this.previewFile = null;
            }
            InstantCameraView instantCameraView = InstantCameraView.this;
            instantCameraView.previewFile = StoryEntry.makeCacheFile(instantCameraView.currentAccount, true);
            try {
                FileLog.d("InstantCamera handlePauseRecording drain encoders");
                drainEncoder(false);
            } catch (Exception e) {
                FileLog.e(e);
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
                        FileLog.e(e3);
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

        public /* synthetic */ void lambda$handlePauseRecording$4(CountDownLatch countDownLatch) {
            try {
                this.mediaMuxer.finishMovie(InstantCameraView.this.previewFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        }

        public /* synthetic */ void lambda$handlePauseRecording$5() {
            InstantCameraView.this.videoEditedInfo = new VideoEditedInfo();
            InstantCameraView.this.videoEditedInfo.roundVideo = true;
            InstantCameraView.this.videoEditedInfo.startTime = -1L;
            InstantCameraView.this.videoEditedInfo.endTime = -1L;
            InstantCameraView.this.videoEditedInfo.file = InstantCameraView.this.file;
            InstantCameraView.this.videoEditedInfo.encryptedFile = InstantCameraView.this.encryptedFile;
            InstantCameraView.this.videoEditedInfo.key = InstantCameraView.this.key;
            InstantCameraView.this.videoEditedInfo.iv = InstantCameraView.this.iv;
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

        public void handleResumeRecording() {
            this.pauseRecorder = false;
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.InstantCameraView$VideoRecorder$2 */
        class AnonymousClass2 implements VideoPlayer.VideoPlayerDelegate {
            @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
            public void onRenderedFirstFrame() {
            }

            @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
            public /* synthetic */ void onRenderedFirstFrame(AnalyticsListener.EventTime eventTime) {
                VideoPlayer.VideoPlayerDelegate.CC.$default$onRenderedFirstFrame(this, eventTime);
            }

            @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
            public /* synthetic */ void onSeekFinished(AnalyticsListener.EventTime eventTime) {
                VideoPlayer.VideoPlayerDelegate.CC.$default$onSeekFinished(this, eventTime);
            }

            @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
            public /* synthetic */ void onSeekStarted(AnalyticsListener.EventTime eventTime) {
                VideoPlayer.VideoPlayerDelegate.CC.$default$onSeekStarted(this, eventTime);
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

            AnonymousClass2() {
            }

            @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
            public void onStateChanged(boolean z, int i) {
                if (InstantCameraView.this.videoPlayer != null && InstantCameraView.this.videoPlayer.isPlaying() && i == 4 && InstantCameraView.this.videoEditedInfo != null) {
                    InstantCameraView.this.videoPlayer.seekTo(InstantCameraView.this.videoEditedInfo.startTime > 0 ? InstantCameraView.this.videoEditedInfo.startTime : 0L);
                }
            }

            @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
            public void onError(VideoPlayer videoPlayer, Exception exc) {
                FileLog.e(exc);
            }
        }

        private void setupVideoPlayer(File file) {
            InstantCameraView.this.videoPlayer = new VideoPlayer();
            InstantCameraView.this.videoPlayer.setDelegate(new VideoPlayer.VideoPlayerDelegate() { // from class: org.telegram.ui.Components.InstantCameraView.VideoRecorder.2
                @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
                public void onRenderedFirstFrame() {
                }

                @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
                public /* synthetic */ void onRenderedFirstFrame(AnalyticsListener.EventTime eventTime) {
                    VideoPlayer.VideoPlayerDelegate.CC.$default$onRenderedFirstFrame(this, eventTime);
                }

                @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
                public /* synthetic */ void onSeekFinished(AnalyticsListener.EventTime eventTime) {
                    VideoPlayer.VideoPlayerDelegate.CC.$default$onSeekFinished(this, eventTime);
                }

                @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
                public /* synthetic */ void onSeekStarted(AnalyticsListener.EventTime eventTime) {
                    VideoPlayer.VideoPlayerDelegate.CC.$default$onSeekStarted(this, eventTime);
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

                AnonymousClass2() {
                }

                @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
                public void onStateChanged(boolean z, int i) {
                    if (InstantCameraView.this.videoPlayer != null && InstantCameraView.this.videoPlayer.isPlaying() && i == 4 && InstantCameraView.this.videoEditedInfo != null) {
                        InstantCameraView.this.videoPlayer.seekTo(InstantCameraView.this.videoEditedInfo.startTime > 0 ? InstantCameraView.this.videoEditedInfo.startTime : 0L);
                    }
                }

                @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
                public void onError(VideoPlayer videoPlayer, Exception exc) {
                    FileLog.e(exc);
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
            animatorSet.playTogether(ObjectAnimator.ofFloat(linearLayout, (Property<LinearLayout, Float>) property, 0.0f), ObjectAnimator.ofInt(InstantCameraView.this.paint, (Property<Paint, Integer>) AnimationProperties.PAINT_ALPHA, 0), ObjectAnimator.ofFloat(InstantCameraView.this.muteImageView, (Property<ImageView, Float>) property, 1.0f));
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
                FileLog.d("InstantCamera handleStopRecording running=false");
                this.sendWhenDone = i;
                this.sendWhenDoneOptions = sendOptions;
                this.running = false;
                return;
            }
            try {
                FileLog.d("InstantCamera handleStopRecording drain encoders");
                drainEncoder(true);
            } catch (Exception e) {
                FileLog.e(e);
            }
            MediaCodec mediaCodec = this.videoEncoder;
            if (mediaCodec != null) {
                try {
                    mediaCodec.stop();
                    this.videoEncoder.release();
                    this.videoEncoder = null;
                } catch (Exception e2) {
                    FileLog.e(e2);
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
                    FileLog.e(e3);
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
                        FileLog.e(e5);
                    }
                }
                FileLog.d("InstantCamera handleStopRecording finish muxer");
                if (this.writingToDifferentFile) {
                    if (this.videoFile.exists()) {
                        try {
                            this.videoFile.delete();
                        } catch (Exception e6) {
                            FileLog.e("InstantCamera copying fileToWrite to videoFile, deleting videoFile error " + this.videoFile);
                            FileLog.e(e6);
                        }
                    }
                    if (!this.fileToWrite.renameTo(this.videoFile)) {
                        FileLog.e("InstantCamera unable to rename file, try move file");
                        try {
                            AndroidUtilities.copyFile(this.fileToWrite, this.videoFile);
                            this.fileToWrite.delete();
                        } catch (IOException e7) {
                            FileLog.e(e7);
                            FileLog.e("InstantCamera unable to move file");
                        }
                    }
                }
            }
            if (i != 2 && (dispatchQueue = this.generateKeyframeThumbsQueue) != null) {
                dispatchQueue.cleanupQueue();
                this.generateKeyframeThumbsQueue.recycle();
                this.generateKeyframeThumbsQueue = null;
            }
            FileLog.d("InstantCamera handleStopRecording send " + i);
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

        public /* synthetic */ void lambda$handleStopRecording$6(SendOptions sendOptions) {
            InstantCameraView.this.videoEditedInfo = new VideoEditedInfo();
            InstantCameraView.this.videoEditedInfo.startTime = -1L;
            InstantCameraView.this.videoEditedInfo.endTime = -1L;
            InstantCameraView.this.videoEditedInfo.estimatedSize = Math.max(1L, InstantCameraView.this.size);
            InstantCameraView.this.videoEditedInfo.roundVideo = true;
            InstantCameraView.this.videoEditedInfo.file = InstantCameraView.this.file;
            InstantCameraView.this.videoEditedInfo.encryptedFile = InstantCameraView.this.encryptedFile;
            InstantCameraView.this.videoEditedInfo.key = InstantCameraView.this.key;
            InstantCameraView.this.videoEditedInfo.iv = InstantCameraView.this.iv;
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

        public /* synthetic */ void lambda$handleStopRecording$7(CountDownLatch countDownLatch) {
            try {
                this.mediaMuxer.finishMovie();
            } catch (Exception e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        }

        public /* synthetic */ void lambda$handleStopRecording$10(int i, final SendOptions sendOptions) {
            if (InstantCameraView.this.videoEditedInfo == null) {
                InstantCameraView.this.videoEditedInfo = new VideoEditedInfo();
                InstantCameraView.this.videoEditedInfo.startTime = -1L;
                InstantCameraView.this.videoEditedInfo.endTime = -1L;
            }
            if (InstantCameraView.this.videoEditedInfo.needConvert()) {
                InstantCameraView.this.file = null;
                InstantCameraView.this.encryptedFile = null;
                InstantCameraView.this.key = null;
                InstantCameraView.this.iv = null;
                double d = InstantCameraView.this.videoEditedInfo.estimatedDuration;
                InstantCameraView.this.videoEditedInfo.estimatedDuration = (InstantCameraView.this.videoEditedInfo.endTime >= 0 ? InstantCameraView.this.videoEditedInfo.endTime : InstantCameraView.this.videoEditedInfo.estimatedDuration) - (InstantCameraView.this.videoEditedInfo.startTime >= 0 ? InstantCameraView.this.videoEditedInfo.startTime : 0L);
                InstantCameraView.this.videoEditedInfo.estimatedSize = Math.max(1L, (long) (InstantCameraView.this.size * (InstantCameraView.this.videoEditedInfo.estimatedDuration / d)));
                InstantCameraView.this.videoEditedInfo.bitrate = 1000000;
                if (InstantCameraView.this.videoEditedInfo.startTime > 0) {
                    InstantCameraView.this.videoEditedInfo.startTime *= 1000;
                }
                if (InstantCameraView.this.videoEditedInfo.endTime > 0) {
                    InstantCameraView.this.videoEditedInfo.endTime *= 1000;
                }
                FileLoader.getInstance(InstantCameraView.this.currentAccount).cancelFileUpload(InstantCameraView.this.cameraFile.getAbsolutePath(), false);
            } else {
                InstantCameraView.this.videoEditedInfo.estimatedSize = Math.max(1L, InstantCameraView.this.size);
            }
            InstantCameraView.this.videoEditedInfo.roundVideo = true;
            InstantCameraView.this.videoEditedInfo.file = InstantCameraView.this.file;
            InstantCameraView.this.videoEditedInfo.encryptedFile = InstantCameraView.this.encryptedFile;
            InstantCameraView.this.videoEditedInfo.key = InstantCameraView.this.key;
            InstantCameraView.this.videoEditedInfo.iv = InstantCameraView.this.iv;
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

        public /* synthetic */ void lambda$handleStopRecording$8(SendOptions sendOptions, VideoEditedInfo videoEditedInfo, boolean z, int i, int i2) {
            MediaController.PhotoEntry photoEntry = new MediaController.PhotoEntry(0, 0, 0L, this.videoFile.getAbsolutePath(), 0, true, 0, 0, 0L);
            if (sendOptions != null) {
                photoEntry.ttl = sendOptions.ttl;
                photoEntry.effectId = sendOptions.effectId;
            }
            InstantCameraView.this.delegate.sendMedia(photoEntry, videoEditedInfo, z || sendOptions == null || sendOptions.notify, i != 0 ? i : sendOptions != null ? sendOptions.scheduleDate : 0, i2 != 0 ? i2 : sendOptions != null ? sendOptions.scheduleRepeatPeriod : 0, false, sendOptions != null ? sendOptions.stars : 0L);
            InstantCameraView.this.startAnimation(false, false);
        }

        public /* synthetic */ void lambda$handleStopRecording$9() {
            InstantCameraView.this.startAnimation(false, false);
        }

        public /* synthetic */ void lambda$handleStopRecording$11() {
            if (this.sentMedia && InstantCameraView.this.videoEditedInfo != null) {
                InstantCameraView.this.videoEditedInfo.notReadyYet = false;
            }
            didWriteData(this.videoFile, 0L, true);
            MediaController.getInstance().requestRecordAudioFocus(false);
        }

        public /* synthetic */ void lambda$handleStopRecording$12() {
            InstantCameraView.this.videoEncoder = null;
        }

        /* JADX WARN: Removed duplicated region for block: B:97:? A[RETURN, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private void setBluetoothScoOn(boolean r4) {
            /*
                r3 = this;
                android.content.Context r0 = org.telegram.messenger.ApplicationLoader.applicationContext
                java.lang.String r1 = "audio"
                java.lang.Object r0 = r0.getSystemService(r1)
                android.media.AudioManager r0 = (android.media.AudioManager) r0
                boolean r1 = org.telegram.messenger.SharedConfig.recordViaSco
                if (r1 == 0) goto L1c
                java.lang.String r1 = "android.permission.BLUETOOTH_CONNECT"
                boolean r1 = org.telegram.ui.Components.PermissionRequest.hasPermission(r1)
                if (r1 != 0) goto L1c
                r1 = 0
                org.telegram.messenger.SharedConfig.recordViaSco = r1
                org.telegram.messenger.SharedConfig.saveConfig()
            L1c:
                boolean r1 = r0.isBluetoothScoAvailableOffCall()
                if (r1 == 0) goto L26
                boolean r1 = org.telegram.messenger.SharedConfig.recordViaSco
                if (r1 != 0) goto L28
            L26:
                if (r4 != 0) goto L66
            L28:
                android.bluetooth.BluetoothAdapter r1 = android.bluetooth.BluetoothAdapter.getDefaultAdapter()
                if (r1 == 0) goto L39
                r2 = 1
                int r1 = r1.getProfileConnectionState(r2)     // Catch: java.lang.Throwable -> L37 java.lang.SecurityException -> L66
                r2 = 2
                if (r1 == r2) goto L3b
                goto L39
            L37:
                r1 = move-exception
                goto L53
            L39:
                if (r4 != 0) goto L66
            L3b:
                if (r4 == 0) goto L47
                boolean r1 = r0.isBluetoothScoOn()     // Catch: java.lang.Throwable -> L37 java.lang.SecurityException -> L66
                if (r1 != 0) goto L47
                r0.startBluetoothSco()     // Catch: java.lang.Throwable -> L37 java.lang.SecurityException -> L66
                return
            L47:
                if (r4 != 0) goto L66
                boolean r1 = r0.isBluetoothScoOn()     // Catch: java.lang.Throwable -> L37 java.lang.SecurityException -> L66
                if (r1 == 0) goto L66
                r0.stopBluetoothSco()     // Catch: java.lang.Throwable -> L37 java.lang.SecurityException -> L66
                return
            L53:
                org.telegram.messenger.FileLog.e(r1)
                if (r4 != 0) goto L66
                boolean r4 = r0.isBluetoothScoOn()     // Catch: java.lang.Exception -> L62
                if (r4 == 0) goto L66
                r0.stopBluetoothSco()     // Catch: java.lang.Exception -> L62
                goto L66
            L62:
                r4 = move-exception
                org.telegram.messenger.FileLog.e(r4)
            L66:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.InstantCameraView.VideoRecorder.setBluetoothScoOn(boolean):void");
        }

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
                    FileLog.d("InstantCamera initied audio record with channels " + this.audioRecorder.getChannelCount() + " sample rate = " + this.audioRecorder.getSampleRate() + " bufferSize = " + i2);
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
                            FileLog.e(th);
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
                    throw new RuntimeException("EGL already set up");
                }
                android.opengl.EGLDisplay eGLDisplayEglGetDisplay = EGL14.eglGetDisplay(0);
                this.eglDisplay = eGLDisplayEglGetDisplay;
                if (eGLDisplayEglGetDisplay == EGL14.EGL_NO_DISPLAY) {
                    throw new RuntimeException("unable to get EGL14 display");
                }
                int[] iArr = new int[2];
                if (!EGL14.eglInitialize(eGLDisplayEglGetDisplay, iArr, 0, iArr, 1)) {
                    this.eglDisplay = null;
                    throw new RuntimeException("unable to initialize EGL14");
                }
                if (this.eglContext == EGL14.EGL_NO_CONTEXT) {
                    android.opengl.EGLConfig[] eGLConfigArr = new android.opengl.EGLConfig[1];
                    if (!EGL14.eglChooseConfig(this.eglDisplay, new int[]{12324, 8, 12323, 8, 12322, 8, 12321, 8, 12352, 4, EglBase.EGL_RECORDABLE_ANDROID, 1, 12344}, 0, eGLConfigArr, 0, 1, new int[1], 0)) {
                        throw new RuntimeException("Unable to find a suitable EGLConfig");
                    }
                    i = 0;
                    this.eglContext = EGL14.eglCreateContext(this.eglDisplay, eGLConfigArr[0], this.sharedEglContext, new int[]{12440, 2, 12344}, 0);
                    this.eglConfig = eGLConfigArr[0];
                } else {
                    i = 0;
                }
                EGL14.eglQueryContext(this.eglDisplay, this.eglContext, 12440, new int[1], i);
                if (this.eglSurface != EGL14.EGL_NO_SURFACE) {
                    throw new IllegalStateException("surface already created");
                }
                android.opengl.EGLSurface eGLSurfaceEglCreateWindowSurface = EGL14.eglCreateWindowSurface(this.eglDisplay, this.eglConfig, this.surface, new int[]{12344}, i);
                this.eglSurface = eGLSurfaceEglCreateWindowSurface;
                if (eGLSurfaceEglCreateWindowSurface == null) {
                    throw new RuntimeException("surface was null");
                }
                if (!EGL14.eglMakeCurrent(this.eglDisplay, eGLSurfaceEglCreateWindowSurface, eGLSurfaceEglCreateWindowSurface, this.eglContext)) {
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.e("eglMakeCurrent failed " + GLUtils.getEGLErrorString(EGL14.eglGetError()));
                    }
                    throw new RuntimeException("eglMakeCurrent failed");
                }
                GLES20.glBlendFunc(770, 771);
                InstantCameraVideoEncoderOverlayHelper instantCameraVideoEncoderOverlayHelper = this.overlayHelper;
                if (instantCameraVideoEncoderOverlayHelper != null) {
                    instantCameraVideoEncoderOverlayHelper.destroy();
                    this.overlayHelper = null;
                }
                this.overlayHelper = new InstantCameraVideoEncoderOverlayHelper(this.videoWidth, this.videoHeight);
                int iLoadShader = InstantCameraView.this.loadShader(35633, "uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n   gl_Position = uMVPMatrix * aPosition;\n   vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n");
                int iLoadShader2 = InstantCameraView.this.loadShader(35632, "#extension GL_OES_EGL_image_external : require\nprecision lowp float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n   gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n");
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
                if (iArr2[0] == 0) {
                    GLES20.glDeleteProgram(this.drawProgram);
                    this.drawProgram = 0;
                    return;
                }
                this.positionHandle = GLES20.glGetAttribLocation(this.drawProgram, "aPosition");
                this.textureHandle = GLES20.glGetAttribLocation(this.drawProgram, "aTextureCoord");
                this.previewSizeHandle = GLES20.glGetUniformLocation(this.drawProgram, "preview");
                this.resolutionHandle = GLES20.glGetUniformLocation(this.drawProgram, "resolution");
                this.alphaHandle = GLES20.glGetUniformLocation(this.drawProgram, "alpha");
                this.vertexMatrixHandle = GLES20.glGetUniformLocation(this.drawProgram, "uMVPMatrix");
                this.textureMatrixHandle = GLES20.glGetUniformLocation(this.drawProgram, "uSTMatrix");
                this.texelSizeHandle = GLES20.glGetUniformLocation(this.drawProgram, "texelSize");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

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
            if (this.videoConvertFirstWrite) {
                FileLoader.getInstance(InstantCameraView.this.currentAccount).uploadFile(file.toString(), InstantCameraView.this.isSecretChat, false, 1L, 33554432, false);
                this.videoConvertFirstWrite = false;
                if (z) {
                    FileLoader.getInstance(InstantCameraView.this.currentAccount).checkUploadNewDataAvailable(file.toString(), InstantCameraView.this.isSecretChat, j, z ? file.length() : 0L);
                    return;
                }
                return;
            }
            FileLoader.getInstance(InstantCameraView.this.currentAccount).checkUploadNewDataAvailable(file.toString(), InstantCameraView.this.isSecretChat, j, z ? file.length() : 0L);
        }

        /* JADX WARN: Code restructure failed: missing block: B:300:0x01ac, code lost:
        
            r1 = r17.audioEncoder.dequeueOutputBuffer(r17.audioBufferInfo, 0);
         */
        /* JADX WARN: Code restructure failed: missing block: B:301:0x01b4, code lost:
        
            if (r1 != (-1)) goto L312;
         */
        /* JADX WARN: Code restructure failed: missing block: B:302:0x01b6, code lost:
        
            if (r18 == false) goto L395;
         */
        /* JADX WARN: Code restructure failed: missing block: B:304:0x01ba, code lost:
        
            if (r17.running != false) goto L307;
         */
        /* JADX WARN: Code restructure failed: missing block: B:306:0x01be, code lost:
        
            if (r17.sendWhenDone == 0) goto L396;
         */
        /* JADX WARN: Code restructure failed: missing block: B:308:0x01c2, code lost:
        
            if (r17.pauseRecorder == false) goto L310;
         */
        /* JADX WARN: Code restructure failed: missing block: B:313:0x01ca, code lost:
        
            if (r1 != (-3)) goto L315;
         */
        /* JADX WARN: Code restructure failed: missing block: B:316:0x01ce, code lost:
        
            if (r1 != (-2)) goto L398;
         */
        /* JADX WARN: Code restructure failed: missing block: B:317:0x01d0, code lost:
        
            r1 = r17.audioEncoder.getOutputFormat();
         */
        /* JADX WARN: Code restructure failed: missing block: B:318:0x01d8, code lost:
        
            if (r17.audioTrackIndex != (-5)) goto L402;
         */
        /* JADX WARN: Code restructure failed: missing block: B:319:0x01da, code lost:
        
            r17.audioTrackIndex = r17.mediaMuxer.addTrack(r1, true);
         */
        /* JADX WARN: Code restructure failed: missing block: B:322:0x01e7, code lost:
        
            if (r1 < 0) goto L408;
         */
        /* JADX WARN: Code restructure failed: missing block: B:323:0x01e9, code lost:
        
            r10 = r17.audioEncoder.getOutputBuffer(r1);
         */
        /* JADX WARN: Code restructure failed: missing block: B:324:0x01ef, code lost:
        
            if (r10 == null) goto L405;
         */
        /* JADX WARN: Code restructure failed: missing block: B:325:0x01f1, code lost:
        
            r13 = r17.audioBufferInfo;
         */
        /* JADX WARN: Code restructure failed: missing block: B:326:0x01f7, code lost:
        
            if ((r13.flags & 2) == 0) goto L328;
         */
        /* JADX WARN: Code restructure failed: missing block: B:327:0x01f9, code lost:
        
            r13.size = 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:329:0x01fd, code lost:
        
            if (r13.size == 0) goto L348;
         */
        /* JADX WARN: Code restructure failed: missing block: B:331:0x0203, code lost:
        
            if (org.telegram.ui.Components.InstantCameraView.this.WRITE_TO_FILE_IN_BACKGROUND == false) goto L338;
         */
        /* JADX WARN: Code restructure failed: missing block: B:332:0x0205, code lost:
        
            r13 = new android.media.MediaCodec.BufferInfo();
            r14 = r17.audioBufferInfo;
            r13.size = r14.size;
            r13.offset = r14.offset;
            r13.flags = r14.flags;
            r13.presentationTimeUs = r14.presentationTimeUs;
            r10 = org.telegram.messenger.AndroidUtilities.cloneByteBuffer(r10);
         */
        /* JADX WARN: Code restructure failed: missing block: B:333:0x0220, code lost:
        
            if (r10 == null) goto L335;
         */
        /* JADX WARN: Code restructure failed: missing block: B:334:0x0222, code lost:
        
            r17.fileWriteQueue.postRunnable(new org.telegram.ui.Components.InstantCameraView$VideoRecorder$$ExternalSyntheticLambda13(r17, r10, r13));
         */
        /* JADX WARN: Code restructure failed: missing block: B:335:0x022c, code lost:
        
            r10 = r17.audioEncoder;
         */
        /* JADX WARN: Code restructure failed: missing block: B:336:0x022e, code lost:
        
            if (r10 == null) goto L351;
         */
        /* JADX WARN: Code restructure failed: missing block: B:337:0x0230, code lost:
        
            r10.releaseOutputBuffer(r1, false);
         */
        /* JADX WARN: Code restructure failed: missing block: B:338:0x0234, code lost:
        
            r13 = r17.mediaMuxer.writeSampleData(r17.audioTrackIndex, r10, r13, false);
         */
        /* JADX WARN: Code restructure failed: missing block: B:339:0x023e, code lost:
        
            if (r13 == 0) goto L345;
         */
        /* JADX WARN: Code restructure failed: missing block: B:341:0x0242, code lost:
        
            if (r17.writingToDifferentFile != false) goto L345;
         */
        /* JADX WARN: Code restructure failed: missing block: B:343:0x024a, code lost:
        
            if (org.telegram.ui.Components.InstantCameraView.this.allowSendingWhileRecording == false) goto L345;
         */
        /* JADX WARN: Code restructure failed: missing block: B:344:0x024c, code lost:
        
            didWriteData(r17.videoFile, r13, false);
         */
        /* JADX WARN: Code restructure failed: missing block: B:345:0x0251, code lost:
        
            r10 = r17.audioEncoder;
         */
        /* JADX WARN: Code restructure failed: missing block: B:346:0x0253, code lost:
        
            if (r10 == null) goto L351;
         */
        /* JADX WARN: Code restructure failed: missing block: B:347:0x0255, code lost:
        
            r10.releaseOutputBuffer(r1, false);
         */
        /* JADX WARN: Code restructure failed: missing block: B:348:0x0259, code lost:
        
            r10 = r17.audioEncoder;
         */
        /* JADX WARN: Code restructure failed: missing block: B:349:0x025b, code lost:
        
            if (r10 == null) goto L351;
         */
        /* JADX WARN: Code restructure failed: missing block: B:350:0x025d, code lost:
        
            r10.releaseOutputBuffer(r1, false);
         */
        /* JADX WARN: Code restructure failed: missing block: B:352:0x0266, code lost:
        
            if ((r17.audioBufferInfo.flags & 4) == 0) goto L409;
         */
        /* JADX WARN: Code restructure failed: missing block: B:353:0x0268, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:355:0x0280, code lost:
        
            throw new java.lang.RuntimeException("encoderOutputBuffer " + r1 + " was null");
         */
        /* JADX WARN: Code restructure failed: missing block: B:413:?, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:414:?, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:415:?, code lost:
        
            return;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void drainEncoder(boolean r18) {
            /*
                Method dump skipped, instruction units count: 665
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.InstantCameraView.VideoRecorder.drainEncoder(boolean):void");
        }

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

        protected void finalize() throws Throwable {
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
        protected void dispatchDraw(Canvas canvas) {
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
                AnonymousClass12() {
                }

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
        if (ExteraConfig.cameraType == 2) {
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

    /* JADX INFO: renamed from: org.telegram.ui.Components.InstantCameraView$12 */
    class AnonymousClass12 extends AnimatorListenerAdapter {
        AnonymousClass12() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (animator.equals(InstantCameraView.this.muteAnimation)) {
                InstantCameraView.this.muteAnimation = null;
            }
        }
    }

    public void finishZoom() {
        if (this.finishZoomTransition != null || ExteraConfig.staticZoom) {
            return;
        }
        if (ExteraConfig.cameraType == 2) {
            CameraXSession cameraXSession = this.cameraXSession;
            if (cameraXSession != null) {
                float zoomRatio = cameraXSession.getZoomRatio();
                if (zoomRatio < 1.0f || zoomRatio > 1.0f) {
                    ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(zoomRatio, 1.0f);
                    this.finishZoomTransition = valueAnimatorOfFloat;
                    valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.InstantCameraView$$ExternalSyntheticLambda5
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            this.f$0.lambda$finishZoom$17(valueAnimator);
                        }
                    });
                    this.finishZoomTransition.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.InstantCameraView.13
                        AnonymousClass13() {
                        }

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
                AnonymousClass14() {
                }

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

    public /* synthetic */ void lambda$finishZoom$17(ValueAnimator valueAnimator) {
        if (this.cameraXSession != null) {
            this.cameraXSession.setZoomRatio(((Float) valueAnimator.getAnimatedValue()).floatValue());
            this.cameraZoom = this.cameraXSession.getLinearZoom();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.InstantCameraView$13 */
    class AnonymousClass13 extends AnimatorListenerAdapter {
        AnonymousClass13() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            InstantCameraView instantCameraView = InstantCameraView.this;
            if (instantCameraView.finishZoomTransition != null) {
                instantCameraView.finishZoomTransition = null;
            }
        }
    }

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

    /* JADX INFO: renamed from: org.telegram.ui.Components.InstantCameraView$14 */
    class AnonymousClass14 extends AnimatorListenerAdapter {
        AnonymousClass14() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            InstantCameraView instantCameraView = InstantCameraView.this;
            if (instantCameraView.finishZoomTransition != null) {
                instantCameraView.finishZoomTransition = null;
            }
        }
    }

    private void adjustZoom(boolean z) {
        float fClamp;
        if (isCameraReady()) {
            ValueAnimator valueAnimator = this.zoomAnimator;
            if (valueAnimator == null || !valueAnimator.isRunning()) {
                float f = 1.0f;
                if (ExteraConfig.cameraType == 2) {
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
                    if (z) {
                        f = this.cameraZoom * fPow2;
                    } else {
                        float f4 = this.cameraZoom / fPow2;
                        if (f4 >= 1.0f) {
                            f = f4;
                        }
                    }
                    fClamp = Utilities.clamp(f, maxZoom, minZoom);
                } else {
                    fClamp = Utilities.clamp(f3 + ((z ? 1 : -1) * 0.125f), 1.0f, 0.0f);
                }
                float f5 = this.cameraZoom;
                if (f5 == fClamp) {
                    return;
                }
                ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(f5, fClamp);
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

    public /* synthetic */ void lambda$adjustZoom$19(ValueAnimator valueAnimator) {
        if (this.cameraXSession != null) {
            this.cameraXSession.setZoomRatio(((Float) valueAnimator.getAnimatedValue()).floatValue());
            this.cameraZoom = this.cameraXSession.getLinearZoom();
        }
    }

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
        if (ExteraConfig.cameraType == 2) {
            CameraXSession cameraXSession = this.cameraXSession;
            zIsCameraSessionInitiated = cameraXSession != null && cameraXSession.isReady();
        } else {
            zIsCameraSessionInitiated = isCameraSessionInitiated();
        }
        return this.cameraReady && zIsCameraSessionInitiated && this.cameraThread != null;
    }

    /* JADX INFO: loaded from: classes3.dex */
    public interface Delegate {
        int getClassGuid();

        long getDialogId();

        View getFragmentView();

        Activity getParentActivity();

        boolean isInScheduleMode();

        boolean isSecretChat();

        void sendMedia(MediaController.PhotoEntry photoEntry, VideoEditedInfo videoEditedInfo, boolean z, int i, int i2, boolean z2, long j);

        /* JADX INFO: renamed from: org.telegram.ui.Components.InstantCameraView$Delegate$-CC */
        /* JADX INFO: loaded from: classes5.dex */
        public abstract /* synthetic */ class CC {
            public static boolean $default$isSecretChat(Delegate delegate) {
                return false;
            }

            public static boolean $default$isInScheduleMode(Delegate delegate) {
                return false;
            }
        }
    }
}
