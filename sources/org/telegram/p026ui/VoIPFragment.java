package org.telegram.p026ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.PowerManager;
import android.text.Layout;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.util.Property;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.ViewCompat;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import okhttp3.internal.url._UrlKt;
import org.mvel2.DataTypes;
import org.mvel2.asm.Opcodes;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.AnimationNotificationsLocker;
import org.telegram.messenger.C2702R;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.pip.PipSource;
import org.telegram.messenger.pip.source.IPipSourceDelegate;
import org.telegram.messenger.pip.utils.PipUtils;
import org.telegram.messenger.voip.EncryptionKeyEmojifier;
import org.telegram.messenger.voip.VideoCapturerDevice;
import org.telegram.messenger.voip.VoIPService;
import org.telegram.messenger.voip.VoIPServiceState;
import org.telegram.messenger.voip.VoipAudioManager;
import org.telegram.p026ui.ActionBar.ActionBar;
import org.telegram.p026ui.ActionBar.AlertDialog;
import org.telegram.p026ui.ActionBar.DarkAlertDialog;
import org.telegram.p026ui.ActionBar.Theme;
import org.telegram.p026ui.Components.AlertsCreator;
import org.telegram.p026ui.Components.AnimatedEmojiDrawable;
import org.telegram.p026ui.Components.AnimatedEmojiSpan;
import org.telegram.p026ui.Components.AvatarDrawable;
import org.telegram.p026ui.Components.AvatarsDrawable;
import org.telegram.p026ui.Components.BackgroundGradientDrawable;
import org.telegram.p026ui.Components.BackupImageView;
import org.telegram.p026ui.Components.CubicBezierInterpolator;
import org.telegram.p026ui.Components.HideViewAfterAnimation;
import org.telegram.p026ui.Components.LayoutHelper;
import org.telegram.p026ui.Components.Premium.boosts.UserSelectorBottomSheet;
import org.telegram.p026ui.Components.ScaleStateListAnimator;
import org.telegram.p026ui.Components.Text;
import org.telegram.p026ui.Components.voip.AcceptDeclineView;
import org.telegram.p026ui.Components.voip.EmojiRationalLayout;
import org.telegram.p026ui.Components.voip.EndCloseLayout;
import org.telegram.p026ui.Components.voip.HideEmojiTextView;
import org.telegram.p026ui.Components.voip.ImageWithWavesView;
import org.telegram.p026ui.Components.voip.PrivateVideoPreviewDialogNew;
import org.telegram.p026ui.Components.voip.RateCallLayout;
import org.telegram.p026ui.Components.voip.VoIPBackgroundProvider;
import org.telegram.p026ui.Components.voip.VoIPButtonsLayout;
import org.telegram.p026ui.Components.voip.VoIPFloatingLayout;
import org.telegram.p026ui.Components.voip.VoIPHelper;
import org.telegram.p026ui.Components.voip.VoIPNotificationsLayout;
import org.telegram.p026ui.Components.voip.VoIPPiPView;
import org.telegram.p026ui.Components.voip.VoIPStatusTextView;
import org.telegram.p026ui.Components.voip.VoIPTextureView;
import org.telegram.p026ui.Components.voip.VoIPToggleButton;
import org.telegram.p026ui.Components.voip.VoIPWindowView;
import org.telegram.p026ui.Components.voip.VoIpCoverView;
import org.telegram.p026ui.Components.voip.VoIpGradientLayout;
import org.telegram.p026ui.Components.voip.VoIpHintView;
import org.telegram.p026ui.Components.voip.VoIpSnowView;
import org.telegram.p026ui.Components.voip.VoIpSwitchLayout;
import org.telegram.p026ui.Stories.recorder.HintView2;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p025tl.TL_phone;
import org.webrtc.EglBase;
import org.webrtc.GlRectDrawer;
import org.webrtc.RendererCommon;
import org.webrtc.TextureViewRenderer;

/* JADX INFO: loaded from: classes3.dex */
public class VoIPFragment implements VoIPService.StateListener, NotificationCenter.NotificationCenterDelegate, IPipSourceDelegate {
    private static VoIPFragment instance;
    private AcceptDeclineView acceptDeclineView;
    private AccessibilityManager accessibilityManager;
    Activity activity;
    private ImageView addIcon;
    private UserSelectorBottomSheet addPeopleSheet;
    private ImageView backIcon;
    private VoIPToggleButton bottomEndCallBtn;
    private VoIpSwitchLayout bottomMuteBtn;
    View bottomShadow;
    private VoIpSwitchLayout bottomSpeakerBtn;
    private VoIpSwitchLayout bottomVideoBtn;
    private VoIPButtonsLayout buttonsLayout;
    TLRPC.User callingUser;
    boolean callingUserIsVideo;
    private VoIPFloatingLayout callingUserMiniFloatingLayout;
    private TextureViewRenderer callingUserMiniTextureRenderer;
    private ImageWithWavesView callingUserPhotoViewMini;
    private VoIPTextureView callingUserTextureView;
    private TextView callingUserTitle;
    boolean cameraForceExpanded;
    private Animator cameraShowingAnimator;
    private boolean canHideUI;
    private boolean canSwitchToPip;
    private boolean canZoomGesture;
    private final int currentAccount;
    private int currentState;
    TLRPC.User currentUser;
    private VoIPFloatingLayout currentUserCameraFloatingLayout;
    private boolean currentUserCameraIsFullscreen;
    boolean currentUserIsVideo;
    private VoIPTextureView currentUserTextureView;
    private boolean deviceIsLocked;
    private boolean emojiExpanded;
    LinearLayout emojiLayout;
    private boolean emojiLoaded;
    LinearLayout emojiRationalLayout;
    TextView emojiRationalTextView;
    TextView emojiRationalTopTextView;
    HintView2 encryptionTooltip;
    EndCloseLayout endCloseLayout;
    boolean enterFromPiP;
    private float enterTransitionProgress;
    float fillNaviagtionBarValue;
    private Runnable firstFrameCallback;
    private ViewGroup fragmentView;
    private VoIpGradientLayout gradientLayout;
    FrameLayout hideEmojiLayout;
    TextView hideEmojiTextView;
    boolean hideUiRunnableWaiting;
    private boolean isFinished;
    private boolean isInPinchToZoomTouchMode;
    private boolean isNearEar;
    boolean isOutgoing;
    private boolean isVideoCall;
    long lastContentTapTime;
    private WindowInsets lastInsets;
    private boolean lockOnScreen;
    VoIPNotificationsLayout notificationsLayout;
    private ConferenceParticipantsView participantsView;
    private float pinchCenterX;
    private float pinchCenterY;
    private float pinchStartCenterX;
    private float pinchStartCenterY;
    private float pinchStartDistance;
    private float pinchTranslationX;
    private float pinchTranslationY;
    private PipSource pipSource;
    private VoIPTextureView pipTextureView;
    private int pointerId1;
    private int pointerId2;
    private PrivateVideoPreviewDialogNew previewDialog;
    private int previousState;
    RateCallLayout rateCallLayout;
    private boolean screenWasWakeup;
    private int selectedRating;
    private boolean signalBarWasReceived;
    private ImageView speakerPhoneIcon;
    private int speakerPhoneIconResId;
    LinearLayout statusLayout;
    private int statusLayoutAnimateToOffset;
    private VoIPStatusTextView statusTextView;
    private boolean switchingToPip;
    HintView2 tapToVideoTooltip;
    View topShadow;
    float touchSlop;
    ValueAnimator uiVisibilityAnimator;
    private VoIpCoverView voIpCoverView;
    private VoIpSnowView voIpSnowView;
    private boolean wasEstablished;
    private VoIPWindowView windowView;
    private boolean windowViewSkipRender;
    ValueAnimator zoomBackAnimator;
    private boolean zoomStarted;
    private final VoIPBackgroundProvider backgroundProvider = new VoIPBackgroundProvider();
    BackupImageView[] emojiViews = new BackupImageView[4];
    Drawable[] emojiDrawables = new Drawable[4];
    Paint overlayPaint = new Paint();
    Paint overlayBottomPaint = new Paint();
    private boolean uiVisible = true;
    float uiVisibilityAlpha = 1.0f;
    AnimationNotificationsLocker notificationsLocker = new AnimationNotificationsLocker();
    ValueAnimator.AnimatorUpdateListener statusbarAnimatorListener = new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda5
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            this.f$0.lambda$new$0(valueAnimator);
        }
    };
    Runnable hideUIRunnable = new Runnable() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda6
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.lambda$new$1();
        }
    };
    Runnable stopAnimatingBgRunnable = new Runnable() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda7
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.lambda$new$2();
        }
    };
    float pinchScale = 1.0f;

    @Override // org.telegram.messenger.voip.VoIPService.StateListener
    public /* synthetic */ void onCameraFirstFrameAvailable() {
        VoIPService.StateListener.CC.$default$onCameraFirstFrameAvailable(this);
    }

    @Override // org.telegram.messenger.voip.VoIPService.StateListener
    public void onScreenOnChange(boolean z) {
    }

    @Override // org.telegram.messenger.pip.source.IPipSourceDelegate
    public /* synthetic */ boolean pipIsAvailable() {
        return IPipSourceDelegate.CC.$default$pipIsAvailable(this);
    }

    @Override // org.telegram.messenger.pip.source.IPipSourceDelegate
    public /* synthetic */ void pipRenderForeground(Canvas canvas) {
        IPipSourceDelegate.CC.$default$pipRenderForeground(this, canvas);
    }

    public /* synthetic */ void lambda$new$0(ValueAnimator valueAnimator) {
        this.uiVisibilityAlpha = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        updateSystemBarColors();
    }

    public /* synthetic */ void lambda$new$1() {
        this.hideUiRunnableWaiting = false;
        HintView2 hintView2 = this.tapToVideoTooltip;
        boolean z = hintView2 != null && hintView2.shown();
        if (!this.canHideUI || !this.uiVisible || this.emojiExpanded || z) {
            return;
        }
        this.lastContentTapTime = System.currentTimeMillis();
        showUi(false);
        this.previousState = this.currentState;
        updateViewState();
    }

    public /* synthetic */ void lambda$new$2() {
        if (this.currentState == 3) {
            this.callingUserPhotoViewMini.setMute(true, false);
            this.gradientLayout.pause();
        }
    }

    public static void show(Activity activity, int i) {
        VoIPFragment voIPFragment = instance;
        if (voIPFragment != null && voIPFragment.windowView.getParent() == null) {
            VoIPFragment voIPFragment2 = instance;
            if (voIPFragment2 != null) {
                voIPFragment2.callingUserTextureView.renderer.release();
                instance.currentUserTextureView.renderer.release();
                instance.callingUserMiniTextureRenderer.release();
                VoIPWindowView voIPWindowView = instance.windowView;
                if (voIPWindowView != null) {
                    voIPWindowView.finishImmediate();
                }
                instance.destroy();
            }
            instance = null;
        }
        if (instance != null || activity.isFinishing()) {
            return;
        }
        boolean z = VoIPPiPView.getInstance() != null;
        if (VoIPService.getSharedState() == null || VoIPService.getSharedState().getUser() == null) {
            return;
        }
        final VoIPFragment voIPFragment3 = new VoIPFragment(i);
        voIPFragment3.activity = activity;
        instance = voIPFragment3;
        C71591 c71591 = new VoIPWindowView(activity, !z) { // from class: org.telegram.ui.VoIPFragment.1
            private final Path clipPath = new Path();
            private final RectF rectF = new RectF();
            final /* synthetic */ VoIPFragment val$fragment;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C71591(Activity activity2, boolean z2, final VoIPFragment voIPFragment32) {
                super(activity2, z2);
                voIPFragment = voIPFragment32;
                this.clipPath = new Path();
                this.rectF = new RectF();
            }

            @Override // android.view.ViewGroup, android.view.View
            public boolean dispatchKeyEvent(KeyEvent keyEvent) {
                VoIPServiceState sharedState;
                if (voIPFragment.isFinished || voIPFragment.switchingToPip) {
                    return false;
                }
                int keyCode = keyEvent.getKeyCode();
                if (keyCode == 4 && keyEvent.getAction() == 1 && !voIPFragment.lockOnScreen) {
                    voIPFragment.onBackPressed();
                    return true;
                }
                if ((keyCode == 25 || keyCode == 24) && voIPFragment.currentState == 15 && (sharedState = VoIPService.getSharedState()) != null) {
                    sharedState.stopRinging();
                    return true;
                }
                return super.dispatchKeyEvent(keyEvent);
            }

            @Override // android.view.View
            public void draw(Canvas canvas) {
                if (voIPFragment.windowViewSkipRender) {
                    return;
                }
                super.draw(canvas);
            }

            @Override // android.view.ViewGroup, android.view.View
            protected void dispatchDraw(Canvas canvas) {
                if (voIPFragment.switchingToPip && getAlpha() != 0.0f) {
                    float width = voIPFragment.callingUserTextureView.getWidth() * voIPFragment.callingUserTextureView.getScaleX();
                    float height = voIPFragment.callingUserTextureView.getHeight() * voIPFragment.callingUserTextureView.getScaleY();
                    float x = voIPFragment.callingUserTextureView.getX() + ((voIPFragment.callingUserTextureView.getWidth() - width) / 2.0f);
                    float y = voIPFragment.callingUserTextureView.getY() + ((voIPFragment.callingUserTextureView.getHeight() - height) / 2.0f);
                    canvas.save();
                    this.clipPath.rewind();
                    this.rectF.set(x, y, width + x, height + y);
                    float fM1081dp = AndroidUtilities.m1081dp(4.0f);
                    this.clipPath.addRoundRect(this.rectF, fM1081dp, fM1081dp, Path.Direction.CW);
                    this.clipPath.close();
                    canvas.clipPath(this.clipPath);
                    super.dispatchDraw(canvas);
                    canvas.restore();
                    return;
                }
                super.dispatchDraw(canvas);
            }
        };
        instance.deviceIsLocked = ((KeyguardManager) activity2.getSystemService("keyguard")).inKeyguardRestrictedInputMode();
        boolean zIsInteractive = ((PowerManager) activity2.getSystemService("power")).isInteractive();
        VoIPFragment voIPFragment4 = instance;
        voIPFragment4.screenWasWakeup = !zIsInteractive;
        c71591.setLockOnScreen(voIPFragment4.deviceIsLocked);
        voIPFragment32.windowView = c71591;
        c71591.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda2
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                return VoIPFragment.m19629$r8$lambda$cjH2LlfFH0h_LscUMZU9s8W89Q(this.f$0, view, windowInsets);
            }
        });
        ((WindowManager) activity2.getSystemService("window")).addView(c71591, c71591.createWindowLayoutParams());
        c71591.addView(voIPFragment32.createView(activity2));
        if (z) {
            voIPFragment32.enterTransitionProgress = 0.0f;
            voIPFragment32.startTransitionFromPiP();
        } else {
            voIPFragment32.enterTransitionProgress = 1.0f;
            voIPFragment32.updateSystemBarColors();
        }
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        if (sharedInstance != null && sharedInstance.getRemoteVideoState() == 2 && PipUtils.checkPermissions(activity2) == 1) {
            VoIPFragment voIPFragment5 = instance;
            voIPFragment5.pipSource = new PipSource.Builder(activity2, voIPFragment5).setTagPrefix("voip-fragment-pip").setContentView(instance.callingUserTextureView.renderer).setPlaceholderView(instance.callingUserTextureView.getPlaceholderView()).build();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.VoIPFragment$1 */
    /* JADX INFO: loaded from: classes6.dex */
    class C71591 extends VoIPWindowView {
        private final Path clipPath = new Path();
        private final RectF rectF = new RectF();
        final /* synthetic */ VoIPFragment val$fragment;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C71591(Activity activity2, boolean z2, final VoIPFragment voIPFragment32) {
            super(activity2, z2);
            voIPFragment = voIPFragment32;
            this.clipPath = new Path();
            this.rectF = new RectF();
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            VoIPServiceState sharedState;
            if (voIPFragment.isFinished || voIPFragment.switchingToPip) {
                return false;
            }
            int keyCode = keyEvent.getKeyCode();
            if (keyCode == 4 && keyEvent.getAction() == 1 && !voIPFragment.lockOnScreen) {
                voIPFragment.onBackPressed();
                return true;
            }
            if ((keyCode == 25 || keyCode == 24) && voIPFragment.currentState == 15 && (sharedState = VoIPService.getSharedState()) != null) {
                sharedState.stopRinging();
                return true;
            }
            return super.dispatchKeyEvent(keyEvent);
        }

        @Override // android.view.View
        public void draw(Canvas canvas) {
            if (voIPFragment.windowViewSkipRender) {
                return;
            }
            super.draw(canvas);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            if (voIPFragment.switchingToPip && getAlpha() != 0.0f) {
                float width = voIPFragment.callingUserTextureView.getWidth() * voIPFragment.callingUserTextureView.getScaleX();
                float height = voIPFragment.callingUserTextureView.getHeight() * voIPFragment.callingUserTextureView.getScaleY();
                float x = voIPFragment.callingUserTextureView.getX() + ((voIPFragment.callingUserTextureView.getWidth() - width) / 2.0f);
                float y = voIPFragment.callingUserTextureView.getY() + ((voIPFragment.callingUserTextureView.getHeight() - height) / 2.0f);
                canvas.save();
                this.clipPath.rewind();
                this.rectF.set(x, y, width + x, height + y);
                float fM1081dp = AndroidUtilities.m1081dp(4.0f);
                this.clipPath.addRoundRect(this.rectF, fM1081dp, fM1081dp, Path.Direction.CW);
                this.clipPath.close();
                canvas.clipPath(this.clipPath);
                super.dispatchDraw(canvas);
                canvas.restore();
                return;
            }
            super.dispatchDraw(canvas);
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$cjH2LlfFH0h_LscUMZ-U9s8W89Q */
    public static /* synthetic */ WindowInsets m19629$r8$lambda$cjH2LlfFH0h_LscUMZU9s8W89Q(VoIPFragment voIPFragment, View view, WindowInsets windowInsets) {
        voIPFragment.setInsets(windowInsets);
        if (Build.VERSION.SDK_INT >= 30) {
            return WindowInsets.CONSUMED;
        }
        return windowInsets.consumeSystemWindowInsets();
    }

    public void onBackPressed() {
        if (this.isFinished || this.switchingToPip) {
            return;
        }
        PrivateVideoPreviewDialogNew privateVideoPreviewDialogNew = this.previewDialog;
        if (privateVideoPreviewDialogNew != null) {
            privateVideoPreviewDialogNew.dismiss(false, false);
            return;
        }
        if (this.callingUserIsVideo && this.currentUserIsVideo && this.cameraForceExpanded) {
            this.cameraForceExpanded = false;
            this.currentUserCameraFloatingLayout.setRelativePosition(this.callingUserMiniFloatingLayout);
            this.currentUserCameraIsFullscreen = false;
            this.previousState = this.currentState;
            updateViewState();
            return;
        }
        if (this.emojiExpanded) {
            expandEmoji(false);
            return;
        }
        if (this.emojiRationalLayout.getVisibility() != 8) {
            return;
        }
        if (this.canSwitchToPip && VoIPService.getSharedInstance() != null && !VoIPService.getSharedInstance().isConverting() && !this.lockOnScreen) {
            if (PipUtils.checkAnyPipPermissions(this.activity)) {
                switchToPip();
                return;
            } else {
                requestInlinePermissions();
                return;
            }
        }
        this.windowView.finish();
    }

    public void finish() {
        this.windowView.finish();
    }

    public static void clearInstance() {
        if (instance != null) {
            if (VoIPService.getSharedInstance() != null) {
                int measuredHeight = instance.windowView.getMeasuredHeight();
                if (instance.canSwitchToPip && !VoIPService.getSharedInstance().isConverting()) {
                    VoIPFragment voIPFragment = instance;
                    VoIPPiPView.show(voIPFragment.activity, voIPFragment.currentAccount, voIPFragment.windowView.getMeasuredWidth(), measuredHeight, 0);
                    WindowInsets windowInsets = instance.lastInsets;
                    if (windowInsets != null) {
                        VoIPPiPView.topInset = windowInsets.getSystemWindowInsetTop();
                        VoIPPiPView.bottomInset = instance.lastInsets.getSystemWindowInsetBottom();
                    }
                }
            }
            instance.callingUserTextureView.renderer.release();
            instance.currentUserTextureView.renderer.release();
            instance.callingUserMiniTextureRenderer.release();
            instance.destroy();
        }
        instance = null;
    }

    public static VoIPFragment getInstance() {
        return instance;
    }

    private void setInsets(WindowInsets windowInsets) {
        this.lastInsets = windowInsets;
        ((FrameLayout.LayoutParams) this.buttonsLayout.getLayoutParams()).bottomMargin = this.lastInsets.getSystemWindowInsetBottom();
        ((FrameLayout.LayoutParams) this.acceptDeclineView.getLayoutParams()).bottomMargin = this.lastInsets.getSystemWindowInsetBottom();
        ((FrameLayout.LayoutParams) this.backIcon.getLayoutParams()).topMargin = this.lastInsets.getSystemWindowInsetTop();
        ((FrameLayout.LayoutParams) this.addIcon.getLayoutParams()).topMargin = this.lastInsets.getSystemWindowInsetTop();
        ((FrameLayout.LayoutParams) this.speakerPhoneIcon.getLayoutParams()).topMargin = AndroidUtilities.m1081dp(56.0f) + this.lastInsets.getSystemWindowInsetTop();
        ((FrameLayout.LayoutParams) this.statusLayout.getLayoutParams()).topMargin = AndroidUtilities.m1081dp(135.0f) + this.lastInsets.getSystemWindowInsetTop();
        ((FrameLayout.LayoutParams) this.emojiLayout.getLayoutParams()).topMargin = AndroidUtilities.m1081dp(17.0f) + this.lastInsets.getSystemWindowInsetTop();
        ((FrameLayout.LayoutParams) this.callingUserPhotoViewMini.getLayoutParams()).topMargin = AndroidUtilities.m1081dp(93.0f) + this.lastInsets.getSystemWindowInsetTop();
        ((FrameLayout.LayoutParams) this.hideEmojiLayout.getLayoutParams()).topMargin = this.lastInsets.getSystemWindowInsetTop();
        ((FrameLayout.LayoutParams) this.emojiRationalLayout.getLayoutParams()).topMargin = AndroidUtilities.m1081dp(118.0f) + this.lastInsets.getSystemWindowInsetTop();
        ((FrameLayout.LayoutParams) this.rateCallLayout.getLayoutParams()).topMargin = AndroidUtilities.m1081dp(380.0f) + this.lastInsets.getSystemWindowInsetTop();
        ((FrameLayout.LayoutParams) this.callingUserMiniFloatingLayout.getLayoutParams()).bottomMargin = this.lastInsets.getSystemWindowInsetBottom();
        ((FrameLayout.LayoutParams) this.notificationsLayout.getLayoutParams()).bottomMargin = this.lastInsets.getSystemWindowInsetBottom();
        this.currentUserCameraFloatingLayout.setInsets(this.lastInsets);
        this.callingUserMiniFloatingLayout.setInsets(this.lastInsets);
        this.fragmentView.requestLayout();
        PrivateVideoPreviewDialogNew privateVideoPreviewDialogNew = this.previewDialog;
        if (privateVideoPreviewDialogNew != null) {
            privateVideoPreviewDialogNew.setBottomPadding(this.lastInsets.getSystemWindowInsetBottom());
        }
    }

    public VoIPFragment(int i) {
        this.currentAccount = i;
        this.currentUser = MessagesController.getInstance(i).getUser(Long.valueOf(UserConfig.getInstance(i).getClientUserId()));
        VoIPServiceState sharedState = VoIPService.getSharedState();
        if (sharedState == null) {
            return;
        }
        this.callingUser = sharedState.getUser();
        if (VoIPService.getSharedInstance() != null) {
            VoIPService.getSharedInstance().registerStateListener(this);
        }
        this.isOutgoing = sharedState.isOutgoing();
        this.previousState = -1;
        this.currentState = sharedState.getCallState();
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.webRtcSpeakerAmplitudeEvent);
        NotificationCenter.getInstance(i).addObserver(this, NotificationCenter.voipServiceCreated);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.emojiLoaded);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.closeInCallActivity);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.nearEarEvent);
    }

    public void destroy() {
        if (VoIPService.getSharedInstance() != null) {
            VoIPService.getSharedInstance().unregisterStateListener(this);
        }
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.webRtcSpeakerAmplitudeEvent);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.voipServiceCreated);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.emojiLoaded);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.closeInCallActivity);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.nearEarEvent);
        PipSource pipSource = this.pipSource;
        if (pipSource != null) {
            pipSource.destroy();
            this.pipSource = null;
        }
        UserSelectorBottomSheet userSelectorBottomSheet = this.addPeopleSheet;
        if (userSelectorBottomSheet != null) {
            userSelectorBottomSheet.lambda$new$0();
            this.addPeopleSheet = null;
        }
    }

    @Override // org.telegram.messenger.voip.VoIPService.StateListener
    public void onStateChanged(int i) {
        int i2 = this.currentState;
        if (i2 != i) {
            this.previousState = i2;
            this.currentState = i;
            if (this.windowView != null) {
                updateViewState();
            }
        }
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.voipServiceCreated) {
            if (this.currentState != 17 || VoIPService.getSharedInstance() == null) {
                return;
            }
            this.currentUserTextureView.renderer.release();
            this.callingUserTextureView.renderer.release();
            this.callingUserMiniTextureRenderer.release();
            initRenderers();
            VoIPService.getSharedInstance().registerStateListener(this);
            return;
        }
        if (i == NotificationCenter.emojiLoaded) {
            updateKeyView(true);
            return;
        }
        if (i == NotificationCenter.closeInCallActivity) {
            this.windowView.finish();
            return;
        }
        if (i == NotificationCenter.webRtcSpeakerAmplitudeEvent) {
            this.callingUserPhotoViewMini.setAmplitude(((Float) objArr[0]).floatValue() * 15.0f);
            return;
        }
        if (i == NotificationCenter.nearEarEvent) {
            boolean zBooleanValue = ((Boolean) objArr[0]).booleanValue();
            this.isNearEar = zBooleanValue;
            if (zBooleanValue) {
                this.callingUserPhotoViewMini.setMute(true, true);
            }
        }
    }

    @Override // org.telegram.messenger.voip.VoIPService.StateListener
    public void onSignalBarsCountChanged(final int i) {
        VoIpGradientLayout voIpGradientLayout;
        if (i > 0) {
            this.signalBarWasReceived = true;
        }
        if (this.statusTextView == null || (voIpGradientLayout = this.gradientLayout) == null || !voIpGradientLayout.isConnectedCalled() || !this.signalBarWasReceived) {
            return;
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onSignalBarsCountChanged$4(i);
            }
        }, 400L);
    }

    public /* synthetic */ void lambda$onSignalBarsCountChanged$4(int i) {
        this.statusTextView.setSignalBarCount(i);
        if (i <= 1) {
            this.gradientLayout.showToBadConnection();
            this.statusTextView.showBadConnection(true, true);
        } else {
            this.gradientLayout.hideBadConnection();
            this.statusTextView.showBadConnection(false, true);
        }
    }

    @Override // org.telegram.messenger.voip.VoIPService.StateListener
    public void onAudioSettingsChanged() {
        updateButtons(true);
    }

    @Override // org.telegram.messenger.voip.VoIPService.StateListener
    public void onMediaStateUpdated(int i, int i2) {
        this.previousState = this.currentState;
        if (i2 == 2 && !this.isVideoCall) {
            this.isVideoCall = true;
        }
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        if (sharedInstance != null && sharedInstance.getRemoteVideoState() == 2) {
            if (this.pipSource == null && PipUtils.checkPermissions(this.activity) == 1) {
                this.pipSource = new PipSource.Builder(this.activity, instance).setTagPrefix("voip-fragment-pip").setContentView(this.callingUserTextureView.renderer).setPlaceholderView(this.callingUserTextureView.getPlaceholderView()).build();
            }
        } else {
            PipSource pipSource = this.pipSource;
            if (pipSource != null) {
                pipSource.destroy();
                this.pipSource = null;
            }
        }
        updateViewState();
    }

    @Override // org.telegram.messenger.voip.VoIPService.StateListener
    public void onCameraSwitch(boolean z) {
        this.previousState = this.currentState;
        updateViewState();
    }

    @Override // org.telegram.messenger.voip.VoIPService.StateListener
    public void onVideoAvailableChange(boolean z) {
        this.previousState = this.currentState;
        if (z && !this.isVideoCall) {
            this.isVideoCall = true;
        }
        updateViewState();
    }

    public View createView(final Context context) {
        this.touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.accessibilityManager = (AccessibilityManager) ContextCompat.getSystemService(context, AccessibilityManager.class);
        C71702 c71702 = new FrameLayout(context) { // from class: org.telegram.ui.VoIPFragment.2
            boolean check;
            long pressedTime;
            float pressedX;
            float pressedY;

            C71702(final Context context2) {
                super(context2);
            }

            @Override // android.view.ViewGroup
            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                if (motionEvent.getActionMasked() == 1) {
                    VoIPFragment.this.callingUserPhotoViewMini.setMute(false, false);
                    VoIPFragment.this.gradientLayout.resume();
                    AndroidUtilities.cancelRunOnUIThread(VoIPFragment.this.stopAnimatingBgRunnable);
                    if (VoIPFragment.this.currentState == 3) {
                        AndroidUtilities.runOnUIThread(VoIPFragment.this.stopAnimatingBgRunnable, 10000L);
                    }
                }
                return super.onInterceptTouchEvent(motionEvent);
            }

            @Override // android.view.View
            public boolean onTouchEvent(MotionEvent motionEvent) {
                HintView2 hintView2;
                if (motionEvent.getActionMasked() == 1) {
                    VoIPFragment.this.callingUserPhotoViewMini.setMute(false, false);
                    VoIPFragment.this.gradientLayout.resume();
                    AndroidUtilities.cancelRunOnUIThread(VoIPFragment.this.stopAnimatingBgRunnable);
                    if (VoIPFragment.this.currentState == 3) {
                        AndroidUtilities.runOnUIThread(VoIPFragment.this.stopAnimatingBgRunnable, 10000L);
                    }
                }
                if (!VoIPFragment.this.canZoomGesture && !VoIPFragment.this.isInPinchToZoomTouchMode && !VoIPFragment.this.zoomStarted && motionEvent.getActionMasked() != 0) {
                    VoIPFragment.this.finishZoom();
                    return false;
                }
                if (motionEvent.getActionMasked() == 0) {
                    VoIPFragment.this.canZoomGesture = false;
                    VoIPFragment.this.isInPinchToZoomTouchMode = false;
                    VoIPFragment.this.zoomStarted = false;
                }
                VoIPTextureView fullscreenTextureView = VoIPFragment.this.getFullscreenTextureView();
                if (motionEvent.getActionMasked() == 0 || motionEvent.getActionMasked() == 5) {
                    if (motionEvent.getActionMasked() == 0) {
                        RectF rectF = AndroidUtilities.rectTmp;
                        rectF.set(fullscreenTextureView.getX(), fullscreenTextureView.getY(), fullscreenTextureView.getX() + fullscreenTextureView.getMeasuredWidth(), fullscreenTextureView.getY() + fullscreenTextureView.getMeasuredHeight());
                        rectF.inset(((fullscreenTextureView.getMeasuredHeight() * fullscreenTextureView.scaleTextureToFill) - fullscreenTextureView.getMeasuredHeight()) / 2.0f, ((fullscreenTextureView.getMeasuredWidth() * fullscreenTextureView.scaleTextureToFill) - fullscreenTextureView.getMeasuredWidth()) / 2.0f);
                        if (!GroupCallActivity.isLandscapeMode) {
                            rectF.top = Math.max(rectF.top, ActionBar.getCurrentActionBarHeight());
                            rectF.bottom = Math.min(rectF.bottom, fullscreenTextureView.getMeasuredHeight() - AndroidUtilities.m1081dp(90.0f));
                        } else {
                            rectF.top = Math.max(rectF.top, ActionBar.getCurrentActionBarHeight());
                            rectF.right = Math.min(rectF.right, fullscreenTextureView.getMeasuredWidth() - AndroidUtilities.m1081dp(90.0f));
                        }
                        VoIPFragment.this.canZoomGesture = rectF.contains(motionEvent.getX(), motionEvent.getY());
                        if (!VoIPFragment.this.canZoomGesture) {
                            VoIPFragment.this.finishZoom();
                        }
                    }
                    if (VoIPFragment.this.canZoomGesture && !VoIPFragment.this.isInPinchToZoomTouchMode && motionEvent.getPointerCount() == 2) {
                        VoIPFragment.this.pinchStartDistance = (float) Math.hypot(motionEvent.getX(1) - motionEvent.getX(0), motionEvent.getY(1) - motionEvent.getY(0));
                        VoIPFragment voIPFragment = VoIPFragment.this;
                        float x = (motionEvent.getX(0) + motionEvent.getX(1)) / 2.0f;
                        voIPFragment.pinchCenterX = x;
                        voIPFragment.pinchStartCenterX = x;
                        VoIPFragment voIPFragment2 = VoIPFragment.this;
                        float y = (motionEvent.getY(0) + motionEvent.getY(1)) / 2.0f;
                        voIPFragment2.pinchCenterY = y;
                        voIPFragment2.pinchStartCenterY = y;
                        VoIPFragment voIPFragment3 = VoIPFragment.this;
                        voIPFragment3.pinchScale = 1.0f;
                        voIPFragment3.pointerId1 = motionEvent.getPointerId(0);
                        VoIPFragment.this.pointerId2 = motionEvent.getPointerId(1);
                        VoIPFragment.this.isInPinchToZoomTouchMode = true;
                    }
                } else if (motionEvent.getActionMasked() == 2 && VoIPFragment.this.isInPinchToZoomTouchMode) {
                    int i = -1;
                    int i2 = -1;
                    for (int i3 = 0; i3 < motionEvent.getPointerCount(); i3++) {
                        if (VoIPFragment.this.pointerId1 == motionEvent.getPointerId(i3)) {
                            i = i3;
                        }
                        if (VoIPFragment.this.pointerId2 == motionEvent.getPointerId(i3)) {
                            i2 = i3;
                        }
                    }
                    if (i == -1 || i2 == -1) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        VoIPFragment.this.finishZoom();
                    } else {
                        VoIPFragment.this.pinchScale = ((float) Math.hypot(motionEvent.getX(i2) - motionEvent.getX(i), motionEvent.getY(i2) - motionEvent.getY(i))) / VoIPFragment.this.pinchStartDistance;
                        VoIPFragment voIPFragment4 = VoIPFragment.this;
                        if (voIPFragment4.pinchScale > 1.005f && !voIPFragment4.zoomStarted) {
                            VoIPFragment.this.pinchStartDistance = (float) Math.hypot(motionEvent.getX(i2) - motionEvent.getX(i), motionEvent.getY(i2) - motionEvent.getY(i));
                            VoIPFragment voIPFragment5 = VoIPFragment.this;
                            float x2 = (motionEvent.getX(i) + motionEvent.getX(i2)) / 2.0f;
                            voIPFragment5.pinchCenterX = x2;
                            voIPFragment5.pinchStartCenterX = x2;
                            VoIPFragment voIPFragment6 = VoIPFragment.this;
                            float y2 = (motionEvent.getY(i) + motionEvent.getY(i2)) / 2.0f;
                            voIPFragment6.pinchCenterY = y2;
                            voIPFragment6.pinchStartCenterY = y2;
                            VoIPFragment voIPFragment7 = VoIPFragment.this;
                            voIPFragment7.pinchScale = 1.0f;
                            voIPFragment7.pinchTranslationX = 0.0f;
                            VoIPFragment.this.pinchTranslationY = 0.0f;
                            getParent().requestDisallowInterceptTouchEvent(true);
                            VoIPFragment.this.zoomStarted = true;
                            VoIPFragment.this.isInPinchToZoomTouchMode = true;
                        }
                        float x3 = (motionEvent.getX(i) + motionEvent.getX(i2)) / 2.0f;
                        float y3 = (motionEvent.getY(i) + motionEvent.getY(i2)) / 2.0f;
                        float f = VoIPFragment.this.pinchStartCenterX - x3;
                        float f2 = VoIPFragment.this.pinchStartCenterY - y3;
                        VoIPFragment voIPFragment8 = VoIPFragment.this;
                        voIPFragment8.pinchTranslationX = (-f) / voIPFragment8.pinchScale;
                        VoIPFragment voIPFragment9 = VoIPFragment.this;
                        voIPFragment9.pinchTranslationY = (-f2) / voIPFragment9.pinchScale;
                        invalidate();
                    }
                } else if (motionEvent.getActionMasked() == 1 || ((motionEvent.getActionMasked() == 6 && VoIPFragment.this.checkPointerIds(motionEvent)) || motionEvent.getActionMasked() == 3)) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    VoIPFragment.this.finishZoom();
                }
                VoIPFragment.this.fragmentView.invalidate();
                int action = motionEvent.getAction();
                if (action == 0) {
                    this.pressedX = motionEvent.getX();
                    this.pressedY = motionEvent.getY();
                    this.check = true;
                    this.pressedTime = System.currentTimeMillis();
                } else if (action != 1) {
                    if (action == 3) {
                        this.check = false;
                    }
                } else if (this.check) {
                    float x4 = motionEvent.getX() - this.pressedX;
                    float y4 = motionEvent.getY() - this.pressedY;
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    float f3 = (x4 * x4) + (y4 * y4);
                    VoIPFragment voIPFragment10 = VoIPFragment.this;
                    float f4 = voIPFragment10.touchSlop;
                    if (f3 < f4 * f4 && jCurrentTimeMillis - this.pressedTime < 300 && jCurrentTimeMillis - voIPFragment10.lastContentTapTime > 300) {
                        voIPFragment10.lastContentTapTime = System.currentTimeMillis();
                        if (VoIPFragment.this.emojiExpanded) {
                            VoIPFragment.this.expandEmoji(false);
                        } else if (VoIPFragment.this.canHideUI) {
                            VoIPFragment.this.showUi(!r14.uiVisible);
                            VoIPFragment voIPFragment11 = VoIPFragment.this;
                            voIPFragment11.previousState = voIPFragment11.currentState;
                            if (!VoIPFragment.this.uiVisible && (hintView2 = VoIPFragment.this.tapToVideoTooltip) != null && hintView2.shown()) {
                                VoIPFragment.this.tapToVideoTooltip.hide();
                            }
                            VoIPFragment.this.updateViewState();
                        }
                    }
                    this.check = false;
                }
                return VoIPFragment.this.canZoomGesture || this.check;
            }

            @Override // android.view.ViewGroup
            protected boolean drawChild(Canvas canvas, View view, long j) {
                if (view == VoIPFragment.this.gradientLayout) {
                    VoIPFragment voIPFragment = VoIPFragment.this;
                    if (voIPFragment.currentUserIsVideo || voIPFragment.callingUserIsVideo) {
                        return false;
                    }
                }
                if ((view == VoIPFragment.this.gradientLayout || view == VoIPFragment.this.callingUserTextureView || (view == VoIPFragment.this.currentUserCameraFloatingLayout && VoIPFragment.this.currentUserCameraIsFullscreen)) && (VoIPFragment.this.zoomStarted || VoIPFragment.this.zoomBackAnimator != null)) {
                    canvas.save();
                    VoIPFragment voIPFragment2 = VoIPFragment.this;
                    float f = voIPFragment2.pinchScale;
                    canvas.scale(f, f, voIPFragment2.pinchCenterX, VoIPFragment.this.pinchCenterY);
                    canvas.translate(VoIPFragment.this.pinchTranslationX, VoIPFragment.this.pinchTranslationY);
                    boolean zDrawChild = super.drawChild(canvas, view, j);
                    canvas.restore();
                    return zDrawChild;
                }
                return super.drawChild(canvas, view, j);
            }
        };
        boolean z = false;
        c71702.setClipToPadding(false);
        c71702.setClipChildren(false);
        c71702.setBackgroundColor(-16777216);
        updateSystemBarColors();
        this.fragmentView = c71702;
        c71702.setFitsSystemWindows(true);
        VoIPServiceState sharedState = VoIPService.getSharedState();
        this.gradientLayout = new VoIpGradientLayout(context2, sharedState != null && sharedState.isConference(), this.backgroundProvider);
        VoIPTextureView voIPTextureView = new VoIPTextureView(context2, false, true, false, false);
        this.callingUserTextureView = voIPTextureView;
        TextureViewRenderer textureViewRenderer = voIPTextureView.renderer;
        RendererCommon.ScalingType scalingType = RendererCommon.ScalingType.SCALE_ASPECT_FIT;
        textureViewRenderer.setScalingType(scalingType);
        this.callingUserTextureView.renderer.setEnableHardwareScaler(true);
        this.callingUserTextureView.renderer.setRotateTextureWithScreen(true);
        this.callingUserTextureView.scaleType = VoIPTextureView.SCALE_TYPE_FIT;
        c71702.addView(this.gradientLayout, LayoutHelper.createFrame(-1, -1.0f));
        VoIpCoverView voIpCoverView = new VoIpCoverView(context2, this.callingUser, this.backgroundProvider);
        this.voIpCoverView = voIpCoverView;
        c71702.addView(voIpCoverView, LayoutHelper.createFrame(-1, -1.0f));
        VoIpSnowView voIpSnowView = new VoIpSnowView(context2);
        this.voIpSnowView = voIpSnowView;
        c71702.addView(voIpSnowView, LayoutHelper.createFrame(-1, 220.0f));
        c71702.addView(this.callingUserTextureView);
        GradientDrawable.Orientation orientation = GradientDrawable.Orientation.TOP_BOTTOM;
        new BackgroundGradientDrawable(orientation, new int[]{-14994098, -14328963}).startDithering(BackgroundGradientDrawable.Sizes.ofDeviceScreen(BackgroundGradientDrawable.Sizes.Orientation.PORTRAIT), new BackgroundGradientDrawable.ListenerAdapter() { // from class: org.telegram.ui.VoIPFragment.3
            C71773() {
            }

            @Override // org.telegram.ui.Components.BackgroundGradientDrawable.ListenerAdapter, org.telegram.ui.Components.BackgroundGradientDrawable.Listener
            public void onAllSizesReady() {
                VoIPFragment.this.gradientLayout.invalidate();
            }
        });
        VoIPFloatingLayout voIPFloatingLayout = new VoIPFloatingLayout(context2);
        this.currentUserCameraFloatingLayout = voIPFloatingLayout;
        voIPFloatingLayout.setDelegate(new VoIPFloatingLayout.VoIPFloatingLayoutDelegate() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda21
            @Override // org.telegram.ui.Components.voip.VoIPFloatingLayout.VoIPFloatingLayoutDelegate
            public final void onChange(float f, boolean z2) {
                this.f$0.lambda$createView$5(f, z2);
            }
        });
        this.currentUserCameraFloatingLayout.setRelativePosition(1.0f, 1.0f);
        this.currentUserCameraIsFullscreen = true;
        VoIPTextureView voIPTextureView2 = new VoIPTextureView(context2, true, false);
        this.currentUserTextureView = voIPTextureView2;
        voIPTextureView2.renderer.setIsCamera(true);
        this.currentUserTextureView.renderer.setUseCameraRotation(true);
        this.currentUserCameraFloatingLayout.setOnTapListener(new View.OnClickListener() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda22
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createView$6(view);
            }
        });
        this.currentUserTextureView.renderer.setMirror(true);
        this.currentUserCameraFloatingLayout.addView(this.currentUserTextureView);
        VoIPFloatingLayout voIPFloatingLayout2 = new VoIPFloatingLayout(context2);
        this.callingUserMiniFloatingLayout = voIPFloatingLayout2;
        voIPFloatingLayout2.alwaysFloating = true;
        voIPFloatingLayout2.setFloatingMode(true, false);
        TextureViewRenderer textureViewRenderer2 = new TextureViewRenderer(context2);
        this.callingUserMiniTextureRenderer = textureViewRenderer2;
        textureViewRenderer2.setEnableHardwareScaler(true);
        this.callingUserMiniTextureRenderer.setIsCamera(false);
        this.callingUserMiniTextureRenderer.setFpsReduction(30.0f);
        this.callingUserMiniTextureRenderer.setScalingType(scalingType);
        this.callingUserMiniFloatingLayout.addView(this.callingUserMiniTextureRenderer, LayoutHelper.createFrame(-1, -2, 17));
        this.callingUserMiniFloatingLayout.setOnTapListener(new View.OnClickListener() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda23
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createView$7(view);
            }
        });
        this.callingUserMiniFloatingLayout.setVisibility(8);
        c71702.addView(this.currentUserCameraFloatingLayout, LayoutHelper.createFrame(-2, -2.0f));
        c71702.addView(this.callingUserMiniFloatingLayout);
        View view = new View(context2);
        this.bottomShadow = view;
        view.setBackground(new GradientDrawable(orientation, new int[]{0, ColorUtils.setAlphaComponent(-16777216, 127)}));
        c71702.addView(this.bottomShadow, LayoutHelper.createFrame(-1, Opcodes.IF_ICMPNE, 80));
        View view2 = new View(context2);
        this.topShadow = view2;
        view2.setBackground(new GradientDrawable(orientation, new int[]{ColorUtils.setAlphaComponent(-16777216, 102), 0}));
        c71702.addView(this.topShadow, LayoutHelper.createFrame(-1, Opcodes.IF_ICMPNE, 48));
        C71784 c71784 = new LinearLayout(context2) { // from class: org.telegram.ui.VoIPFragment.4
            C71784(final Context context2) {
                super(context2);
            }

            @Override // android.view.View
            public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
                accessibilityNodeInfo.setVisibleToUser(VoIPFragment.this.emojiLoaded);
            }
        };
        this.emojiLayout = c71784;
        c71784.setOrientation(0);
        this.emojiLayout.setPadding(0, 0, 0, AndroidUtilities.m1081dp(30.0f));
        this.emojiLayout.setClipToPadding(false);
        this.emojiLayout.setContentDescription(LocaleController.getString(C2702R.string.VoipHintEncryptionKey));
        this.emojiLayout.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda24
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.f$0.lambda$createView$8(view3);
            }
        });
        this.hideEmojiTextView = new HideEmojiTextView(context2, this.backgroundProvider);
        FrameLayout frameLayout = new FrameLayout(context2);
        this.hideEmojiLayout = frameLayout;
        frameLayout.addView(this.hideEmojiTextView, LayoutHelper.createFrame(-2, -2.0f, 3, 0.0f, 16.0f, 0.0f, 0.0f));
        this.hideEmojiLayout.setVisibility(8);
        this.hideEmojiLayout.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda25
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.f$0.lambda$createView$9(view3);
            }
        });
        EmojiRationalLayout emojiRationalLayout = new EmojiRationalLayout(context2, this.backgroundProvider);
        this.emojiRationalLayout = emojiRationalLayout;
        emojiRationalLayout.setOrientation(1);
        TextView textView = new TextView(context2);
        this.emojiRationalTopTextView = textView;
        textView.setText(LocaleController.getString(C2702R.string.VoipCallEncryptionEndToEnd));
        this.emojiRationalTopTextView.setTextSize(1, 15.0f);
        this.emojiRationalTopTextView.setTypeface(AndroidUtilities.bold());
        this.emojiRationalTopTextView.setTextColor(-1);
        this.emojiRationalTopTextView.setGravity(17);
        C71795 c71795 = new TextView(context2) { // from class: org.telegram.ui.VoIPFragment.5
            C71795(final Context context2) {
                super(context2);
            }

            @Override // android.widget.TextView, android.view.View
            protected void onLayout(boolean z2, int i, int i2, int i3, int i4) {
                super.onLayout(z2, i, i2, i3, i4);
                if (z2) {
                    VoIPFragment.this.updateViewState();
                }
            }
        };
        this.emojiRationalTextView = c71795;
        c71795.setTextSize(1, 15.0f);
        this.emojiRationalTextView.setTextColor(-1);
        this.emojiRationalTextView.setGravity(17);
        this.emojiRationalTextView.setText(LocaleController.formatString("CallEmojiKeyTooltip", C2702R.string.CallEmojiKeyTooltip, TextUtils.ellipsize(UserObject.getFirstName(this.callingUser), this.emojiRationalTextView.getPaint(), AndroidUtilities.m1081dp(300.0f), TextUtils.TruncateAt.END)));
        this.emojiRationalLayout.setVisibility(8);
        this.emojiRationalLayout.addView(this.emojiRationalTopTextView);
        this.emojiRationalLayout.addView(this.emojiRationalTextView, LayoutHelper.createLinear(-2, -2, 0.0f, 8.0f, 0.0f, 0.0f));
        this.emojiRationalLayout.setPadding(AndroidUtilities.m1081dp(18.0f), AndroidUtilities.m1081dp(80.0f), AndroidUtilities.m1081dp(18.0f), AndroidUtilities.m1081dp(18.0f));
        int i = 0;
        while (i < 4) {
            this.emojiViews[i] = new BackupImageView(context2);
            this.emojiViews[i].getImageReceiver().setAspectFit(true);
            this.emojiLayout.addView(this.emojiViews[i], LayoutHelper.createLinear(25, 25, i == 0 ? 0.0f : 6.0f, 0.0f, 0.0f, 0.0f));
            i++;
        }
        C71806 c71806 = new LinearLayout(context2) { // from class: org.telegram.ui.VoIPFragment.6
            C71806(final Context context2) {
                super(context2);
            }

            @Override // android.view.View
            public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
                VoIPServiceState sharedState2 = VoIPService.getSharedState();
                CharSequence text = VoIPFragment.this.callingUserTitle.getText();
                if (sharedState2 == null || TextUtils.isEmpty(text)) {
                    return;
                }
                StringBuilder sb = new StringBuilder(text);
                sb.append(", ");
                if (sharedState2.getPrivateCall() != null && sharedState2.getPrivateCall().video) {
                    sb.append(LocaleController.getString(C2702R.string.VoipInVideoCallBranding));
                } else {
                    sb.append(LocaleController.getString(C2702R.string.VoipInCallBranding));
                }
                long callDuration = sharedState2.getCallDuration();
                if (callDuration > 0) {
                    sb.append(", ");
                    sb.append(LocaleController.formatDuration((int) (callDuration / 1000)));
                }
                accessibilityNodeInfo.setText(sb);
            }
        };
        this.statusLayout = c71806;
        c71806.setOrientation(1);
        this.statusLayout.setFocusable(true);
        this.statusLayout.setFocusableInTouchMode(true);
        this.callingUserPhotoViewMini = new ImageWithWavesView(context2);
        AvatarDrawable avatarDrawable = new AvatarDrawable();
        avatarDrawable.setInfo(this.callingUser);
        this.callingUserPhotoViewMini.setImage(ImageLocation.getForUserOrChat(this.callingUser, 0), null, avatarDrawable, this.callingUser);
        this.callingUserPhotoViewMini.setRoundRadius(AndroidUtilities.m1081dp(135.0f) / 2);
        TextView textView2 = new TextView(context2);
        this.callingUserTitle = textView2;
        textView2.setTextSize(1, 28.0f);
        TLRPC.User user = this.callingUser;
        this.callingUserTitle.setText(Emoji.replaceEmoji(ContactsController.formatName(user.first_name, user.last_name), this.callingUserTitle.getPaint().getFontMetricsInt(), false));
        this.callingUserTitle.setMaxLines(2);
        this.callingUserTitle.setEllipsize(TextUtils.TruncateAt.END);
        this.callingUserTitle.setTextColor(-1);
        this.callingUserTitle.setGravity(1);
        this.callingUserTitle.setImportantForAccessibility(2);
        this.statusLayout.addView(this.callingUserTitle, LayoutHelper.createLinear(-2, -2, 1, 8, 0, 8, 6));
        VoIPStatusTextView voIPStatusTextView = new VoIPStatusTextView(context2, this.backgroundProvider);
        this.statusTextView = voIPStatusTextView;
        ViewCompat.setImportantForAccessibility(voIPStatusTextView, 4);
        this.statusLayout.addView(this.statusTextView, LayoutHelper.createLinear(-2, -2, 1, 0, 0, 0, 6));
        if (sharedState != null && sharedState.getUser() != null && sharedState.isConference() && sharedState.getGroupCall() != null) {
            ConferenceParticipantsView conferenceParticipantsView = new ConferenceParticipantsView(context2);
            this.participantsView = conferenceParticipantsView;
            conferenceParticipantsView.set(this.currentAccount, sharedState.getUser().f1775id, sharedState.getGroupParticipants(), sharedState.getGroupCall().participants_count);
            this.statusLayout.addView(this.participantsView, LayoutHelper.createLinear(-1, 30, 0.0f, 22.0f, 0.0f, 0.0f));
        }
        this.statusLayout.setClipChildren(false);
        this.statusLayout.setClipToPadding(false);
        this.statusLayout.setPadding(0, 0, 0, AndroidUtilities.m1081dp(15.0f));
        this.endCloseLayout = new EndCloseLayout(context2);
        this.rateCallLayout = new RateCallLayout(context2, this.backgroundProvider);
        this.endCloseLayout.setAlpha(0.0f);
        this.rateCallLayout.setVisibility(8);
        c71702.addView(this.callingUserPhotoViewMini, LayoutHelper.createFrame(204, 204.0f, 1, 0.0f, 93.0f, 0.0f, 0.0f));
        c71702.addView(this.statusLayout, LayoutHelper.createFrame(-1, -2.0f, 0, 0.0f, 135.0f, 0.0f, 0.0f));
        c71702.addView(this.hideEmojiLayout, LayoutHelper.createFrame(-2, -2.0f, 1, 0.0f, 0.0f, 0.0f, 0.0f));
        c71702.addView(this.emojiRationalLayout, LayoutHelper.createFrame(304, -2.0f, 49, 0.0f, 118.0f, 0.0f, 0.0f));
        c71702.addView(this.emojiLayout, LayoutHelper.createFrame(-2, -2.0f, 1, 0.0f, 0.0f, 0.0f, 0.0f));
        c71702.addView(this.endCloseLayout, LayoutHelper.createFrame(-1, 52.0f, 5, 0.0f, 0.0f, 0.0f, 0.0f));
        c71702.addView(this.rateCallLayout, LayoutHelper.createFrame(-1, -2.0f, 3, 0.0f, 380.0f, 0.0f, 0.0f));
        this.buttonsLayout = new VoIPButtonsLayout(context2);
        this.bottomSpeakerBtn = new VoIpSwitchLayout(context2, this.backgroundProvider);
        this.bottomVideoBtn = new VoIpSwitchLayout(context2, this.backgroundProvider);
        this.bottomMuteBtn = new VoIpSwitchLayout(context2, this.backgroundProvider);
        this.bottomEndCallBtn = new VoIPToggleButton(context2, 52.0f) { // from class: org.telegram.ui.VoIPFragment.7
            C71817(final Context context2, float f) {
                super(context2, f);
            }

            @Override // android.view.ViewGroup, android.view.View
            protected void dispatchSetPressed(boolean z2) {
                super.dispatchSetPressed(z2);
                setPressedBtn(z2);
            }
        };
        this.bottomSpeakerBtn.setTranslationY(AndroidUtilities.m1081dp(100.0f));
        this.bottomSpeakerBtn.setScaleX(0.0f);
        this.bottomSpeakerBtn.setScaleY(0.0f);
        this.bottomSpeakerBtn.animate().setStartDelay(150).translationY(0.0f).scaleY(1.0f).scaleX(1.0f).setDuration(250L).start();
        this.bottomVideoBtn.setTranslationY(AndroidUtilities.m1081dp(100.0f));
        this.bottomVideoBtn.setScaleX(0.0f);
        this.bottomVideoBtn.setScaleY(0.0f);
        this.bottomVideoBtn.animate().setStartDelay(Opcodes.IF_ACMPNE).translationY(0.0f).scaleY(1.0f).scaleX(1.0f).setDuration(250L).start();
        this.bottomMuteBtn.setTranslationY(AndroidUtilities.m1081dp(100.0f));
        this.bottomMuteBtn.setScaleX(0.0f);
        this.bottomMuteBtn.setScaleY(0.0f);
        this.bottomMuteBtn.animate().setStartDelay(Opcodes.INVOKEVIRTUAL).translationY(0.0f).scaleY(1.0f).scaleX(1.0f).setDuration(250L).start();
        this.bottomEndCallBtn.setTranslationY(AndroidUtilities.m1081dp(100.0f));
        this.bottomEndCallBtn.setScaleX(0.0f);
        this.bottomEndCallBtn.setScaleY(0.0f);
        this.bottomEndCallBtn.animate().setStartDelay(Opcodes.IFNULL).translationY(0.0f).scaleY(1.0f).scaleX(1.0f).setDuration(250L).start();
        this.buttonsLayout.addView(this.bottomSpeakerBtn);
        this.buttonsLayout.addView(this.bottomVideoBtn);
        this.buttonsLayout.addView(this.bottomMuteBtn);
        this.buttonsLayout.addView(this.bottomEndCallBtn);
        AcceptDeclineView acceptDeclineView = new AcceptDeclineView(context2);
        this.acceptDeclineView = acceptDeclineView;
        acceptDeclineView.setListener(new C71828());
        this.acceptDeclineView.setScaleX(1.15f);
        this.acceptDeclineView.setScaleY(1.15f);
        c71702.addView(this.buttonsLayout, LayoutHelper.createFrame(-1, -2, 80));
        float f = AndroidUtilities.isTablet() ? 100 : 27;
        c71702.addView(this.acceptDeclineView, LayoutHelper.createFrame(-1, 186.0f, 80, f, 0.0f, f, 0.0f));
        ImageView imageView = new ImageView(context2);
        this.addIcon = imageView;
        imageView.setBackground(Theme.createSelectorDrawable(ColorUtils.setAlphaComponent(-1, 76)));
        this.addIcon.setImageResource(C2702R.drawable.msg_addcontact);
        this.addIcon.setPadding(AndroidUtilities.m1081dp(16.0f), AndroidUtilities.m1081dp(16.0f), AndroidUtilities.m1081dp(16.0f), AndroidUtilities.m1081dp(16.0f));
        c71702.addView(this.addIcon, LayoutHelper.createFrame(56, 56, 53));
        ScaleStateListAnimator.apply(this.addIcon);
        ImageView imageView2 = new ImageView(context2);
        this.backIcon = imageView2;
        imageView2.setBackground(Theme.createSelectorDrawable(ColorUtils.setAlphaComponent(-1, 76)));
        this.backIcon.setImageResource(C2702R.drawable.msg_call_minimize_shadow);
        this.backIcon.setPadding(AndroidUtilities.m1081dp(16.0f), AndroidUtilities.m1081dp(16.0f), AndroidUtilities.m1081dp(16.0f), AndroidUtilities.m1081dp(16.0f));
        this.backIcon.setContentDescription(LocaleController.getString(C2702R.string.Back));
        c71702.addView(this.backIcon, LayoutHelper.createFrame(56, 56, 51));
        ScaleStateListAnimator.apply(this.backIcon);
        C71839 c71839 = new ImageView(context2) { // from class: org.telegram.ui.VoIPFragment.9
            C71839(final Context context2) {
                super(context2);
            }

            @Override // android.view.View
            public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
                accessibilityNodeInfo.setClassName(ToggleButton.class.getName());
                accessibilityNodeInfo.setCheckable(true);
                VoIPService sharedInstance = VoIPService.getSharedInstance();
                if (sharedInstance != null) {
                    accessibilityNodeInfo.setChecked(sharedInstance.isSpeakerphoneOn());
                }
            }
        };
        this.speakerPhoneIcon = c71839;
        c71839.setContentDescription(LocaleController.getString(C2702R.string.VoipSpeaker));
        this.speakerPhoneIcon.setBackground(Theme.createSelectorDrawable(ColorUtils.setAlphaComponent(-1, 76)));
        this.speakerPhoneIcon.setPadding(AndroidUtilities.m1081dp(12.0f), AndroidUtilities.m1081dp(12.0f), AndroidUtilities.m1081dp(12.0f), AndroidUtilities.m1081dp(12.0f));
        c71702.addView(this.speakerPhoneIcon, LayoutHelper.createFrame(56, 56.0f, 53, 0.0f, 56.0f, 0.0f, 0.0f));
        this.speakerPhoneIcon.setAlpha(0.0f);
        this.speakerPhoneIcon.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda26
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.f$0.lambda$createView$10(view3);
            }
        });
        this.backIcon.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda27
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.f$0.lambda$createView$11(view3);
            }
        });
        this.addIcon.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda28
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.f$0.lambda$createView$13(context2, view3);
            }
        });
        if (this.windowView.isLockOnScreen()) {
            this.backIcon.setVisibility(8);
            this.addIcon.setVisibility(8);
        }
        VoIPNotificationsLayout voIPNotificationsLayout = new VoIPNotificationsLayout(context2, this.backgroundProvider);
        this.notificationsLayout = voIPNotificationsLayout;
        voIPNotificationsLayout.setGravity(80);
        this.notificationsLayout.setOnViewsUpdated(new Runnable() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda29
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$createView$14();
            }
        });
        c71702.addView(this.notificationsLayout, LayoutHelper.createFrame(-1, 200.0f, 80, 16.0f, 0.0f, 16.0f, 0.0f));
        HintView2 multilineText = new VoIpHintView(context2, 3, this.backgroundProvider, true).setMultilineText(true);
        Layout.Alignment alignment = Layout.Alignment.ALIGN_CENTER;
        HintView2 rounding = multilineText.setTextAlign(alignment).setDuration(-1L).setOnHiddenListener(new Runnable() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda30
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.startWaitingFoHideUi();
            }
        }).setHideByTouch(true).setMaxWidth(320.0f).useScale(true).setInnerPadding(10.0f, 6.0f, 10.0f, 6.0f).setRounding(8.0f);
        this.tapToVideoTooltip = rounding;
        rounding.setText(LocaleController.getString(C2702R.string.TapToTurnCamera));
        c71702.addView(this.tapToVideoTooltip, LayoutHelper.createFrame(-2, -2.0f, 80, 19.0f, 0.0f, 19.0f, 0.0f));
        HintView2 rounding2 = new VoIpHintView(context2, 1, this.backgroundProvider, false).setMultilineText(true).setTextAlign(alignment).setDuration(4000L).setHideByTouch(true).setMaxWidth(320.0f).useScale(true).setInnerPadding(10.0f, 6.0f, 10.0f, 6.0f).setRounding(8.0f);
        this.encryptionTooltip = rounding2;
        rounding2.setText(LocaleController.getString(C2702R.string.VoipHintEncryptionKey));
        c71702.addView(this.encryptionTooltip, LayoutHelper.createFrame(-2, -2.0f, 1, 0.0f, 0.0f, 0.0f, 0.0f));
        updateViewState();
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        if (sharedInstance != null) {
            if (!this.isVideoCall) {
                TL_phone.PhoneCall phoneCall = sharedInstance.privateCall;
                if (phoneCall != null && phoneCall.video) {
                    z = true;
                }
                this.isVideoCall = z;
            }
            initRenderers();
        }
        return c71702;
    }

    /* JADX INFO: renamed from: org.telegram.ui.VoIPFragment$2 */
    /* JADX INFO: loaded from: classes6.dex */
    class C71702 extends FrameLayout {
        boolean check;
        long pressedTime;
        float pressedX;
        float pressedY;

        C71702(final Context context2) {
            super(context2);
        }

        @Override // android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getActionMasked() == 1) {
                VoIPFragment.this.callingUserPhotoViewMini.setMute(false, false);
                VoIPFragment.this.gradientLayout.resume();
                AndroidUtilities.cancelRunOnUIThread(VoIPFragment.this.stopAnimatingBgRunnable);
                if (VoIPFragment.this.currentState == 3) {
                    AndroidUtilities.runOnUIThread(VoIPFragment.this.stopAnimatingBgRunnable, 10000L);
                }
            }
            return super.onInterceptTouchEvent(motionEvent);
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            HintView2 hintView2;
            if (motionEvent.getActionMasked() == 1) {
                VoIPFragment.this.callingUserPhotoViewMini.setMute(false, false);
                VoIPFragment.this.gradientLayout.resume();
                AndroidUtilities.cancelRunOnUIThread(VoIPFragment.this.stopAnimatingBgRunnable);
                if (VoIPFragment.this.currentState == 3) {
                    AndroidUtilities.runOnUIThread(VoIPFragment.this.stopAnimatingBgRunnable, 10000L);
                }
            }
            if (!VoIPFragment.this.canZoomGesture && !VoIPFragment.this.isInPinchToZoomTouchMode && !VoIPFragment.this.zoomStarted && motionEvent.getActionMasked() != 0) {
                VoIPFragment.this.finishZoom();
                return false;
            }
            if (motionEvent.getActionMasked() == 0) {
                VoIPFragment.this.canZoomGesture = false;
                VoIPFragment.this.isInPinchToZoomTouchMode = false;
                VoIPFragment.this.zoomStarted = false;
            }
            VoIPTextureView fullscreenTextureView = VoIPFragment.this.getFullscreenTextureView();
            if (motionEvent.getActionMasked() == 0 || motionEvent.getActionMasked() == 5) {
                if (motionEvent.getActionMasked() == 0) {
                    RectF rectF = AndroidUtilities.rectTmp;
                    rectF.set(fullscreenTextureView.getX(), fullscreenTextureView.getY(), fullscreenTextureView.getX() + fullscreenTextureView.getMeasuredWidth(), fullscreenTextureView.getY() + fullscreenTextureView.getMeasuredHeight());
                    rectF.inset(((fullscreenTextureView.getMeasuredHeight() * fullscreenTextureView.scaleTextureToFill) - fullscreenTextureView.getMeasuredHeight()) / 2.0f, ((fullscreenTextureView.getMeasuredWidth() * fullscreenTextureView.scaleTextureToFill) - fullscreenTextureView.getMeasuredWidth()) / 2.0f);
                    if (!GroupCallActivity.isLandscapeMode) {
                        rectF.top = Math.max(rectF.top, ActionBar.getCurrentActionBarHeight());
                        rectF.bottom = Math.min(rectF.bottom, fullscreenTextureView.getMeasuredHeight() - AndroidUtilities.m1081dp(90.0f));
                    } else {
                        rectF.top = Math.max(rectF.top, ActionBar.getCurrentActionBarHeight());
                        rectF.right = Math.min(rectF.right, fullscreenTextureView.getMeasuredWidth() - AndroidUtilities.m1081dp(90.0f));
                    }
                    VoIPFragment.this.canZoomGesture = rectF.contains(motionEvent.getX(), motionEvent.getY());
                    if (!VoIPFragment.this.canZoomGesture) {
                        VoIPFragment.this.finishZoom();
                    }
                }
                if (VoIPFragment.this.canZoomGesture && !VoIPFragment.this.isInPinchToZoomTouchMode && motionEvent.getPointerCount() == 2) {
                    VoIPFragment.this.pinchStartDistance = (float) Math.hypot(motionEvent.getX(1) - motionEvent.getX(0), motionEvent.getY(1) - motionEvent.getY(0));
                    VoIPFragment voIPFragment = VoIPFragment.this;
                    float x = (motionEvent.getX(0) + motionEvent.getX(1)) / 2.0f;
                    voIPFragment.pinchCenterX = x;
                    voIPFragment.pinchStartCenterX = x;
                    VoIPFragment voIPFragment2 = VoIPFragment.this;
                    float y = (motionEvent.getY(0) + motionEvent.getY(1)) / 2.0f;
                    voIPFragment2.pinchCenterY = y;
                    voIPFragment2.pinchStartCenterY = y;
                    VoIPFragment voIPFragment3 = VoIPFragment.this;
                    voIPFragment3.pinchScale = 1.0f;
                    voIPFragment3.pointerId1 = motionEvent.getPointerId(0);
                    VoIPFragment.this.pointerId2 = motionEvent.getPointerId(1);
                    VoIPFragment.this.isInPinchToZoomTouchMode = true;
                }
            } else if (motionEvent.getActionMasked() == 2 && VoIPFragment.this.isInPinchToZoomTouchMode) {
                int i = -1;
                int i2 = -1;
                for (int i3 = 0; i3 < motionEvent.getPointerCount(); i3++) {
                    if (VoIPFragment.this.pointerId1 == motionEvent.getPointerId(i3)) {
                        i = i3;
                    }
                    if (VoIPFragment.this.pointerId2 == motionEvent.getPointerId(i3)) {
                        i2 = i3;
                    }
                }
                if (i == -1 || i2 == -1) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    VoIPFragment.this.finishZoom();
                } else {
                    VoIPFragment.this.pinchScale = ((float) Math.hypot(motionEvent.getX(i2) - motionEvent.getX(i), motionEvent.getY(i2) - motionEvent.getY(i))) / VoIPFragment.this.pinchStartDistance;
                    VoIPFragment voIPFragment4 = VoIPFragment.this;
                    if (voIPFragment4.pinchScale > 1.005f && !voIPFragment4.zoomStarted) {
                        VoIPFragment.this.pinchStartDistance = (float) Math.hypot(motionEvent.getX(i2) - motionEvent.getX(i), motionEvent.getY(i2) - motionEvent.getY(i));
                        VoIPFragment voIPFragment5 = VoIPFragment.this;
                        float x2 = (motionEvent.getX(i) + motionEvent.getX(i2)) / 2.0f;
                        voIPFragment5.pinchCenterX = x2;
                        voIPFragment5.pinchStartCenterX = x2;
                        VoIPFragment voIPFragment6 = VoIPFragment.this;
                        float y2 = (motionEvent.getY(i) + motionEvent.getY(i2)) / 2.0f;
                        voIPFragment6.pinchCenterY = y2;
                        voIPFragment6.pinchStartCenterY = y2;
                        VoIPFragment voIPFragment7 = VoIPFragment.this;
                        voIPFragment7.pinchScale = 1.0f;
                        voIPFragment7.pinchTranslationX = 0.0f;
                        VoIPFragment.this.pinchTranslationY = 0.0f;
                        getParent().requestDisallowInterceptTouchEvent(true);
                        VoIPFragment.this.zoomStarted = true;
                        VoIPFragment.this.isInPinchToZoomTouchMode = true;
                    }
                    float x3 = (motionEvent.getX(i) + motionEvent.getX(i2)) / 2.0f;
                    float y3 = (motionEvent.getY(i) + motionEvent.getY(i2)) / 2.0f;
                    float f = VoIPFragment.this.pinchStartCenterX - x3;
                    float f2 = VoIPFragment.this.pinchStartCenterY - y3;
                    VoIPFragment voIPFragment8 = VoIPFragment.this;
                    voIPFragment8.pinchTranslationX = (-f) / voIPFragment8.pinchScale;
                    VoIPFragment voIPFragment9 = VoIPFragment.this;
                    voIPFragment9.pinchTranslationY = (-f2) / voIPFragment9.pinchScale;
                    invalidate();
                }
            } else if (motionEvent.getActionMasked() == 1 || ((motionEvent.getActionMasked() == 6 && VoIPFragment.this.checkPointerIds(motionEvent)) || motionEvent.getActionMasked() == 3)) {
                getParent().requestDisallowInterceptTouchEvent(false);
                VoIPFragment.this.finishZoom();
            }
            VoIPFragment.this.fragmentView.invalidate();
            int action = motionEvent.getAction();
            if (action == 0) {
                this.pressedX = motionEvent.getX();
                this.pressedY = motionEvent.getY();
                this.check = true;
                this.pressedTime = System.currentTimeMillis();
            } else if (action != 1) {
                if (action == 3) {
                    this.check = false;
                }
            } else if (this.check) {
                float x4 = motionEvent.getX() - this.pressedX;
                float y4 = motionEvent.getY() - this.pressedY;
                long jCurrentTimeMillis = System.currentTimeMillis();
                float f3 = (x4 * x4) + (y4 * y4);
                VoIPFragment voIPFragment10 = VoIPFragment.this;
                float f4 = voIPFragment10.touchSlop;
                if (f3 < f4 * f4 && jCurrentTimeMillis - this.pressedTime < 300 && jCurrentTimeMillis - voIPFragment10.lastContentTapTime > 300) {
                    voIPFragment10.lastContentTapTime = System.currentTimeMillis();
                    if (VoIPFragment.this.emojiExpanded) {
                        VoIPFragment.this.expandEmoji(false);
                    } else if (VoIPFragment.this.canHideUI) {
                        VoIPFragment.this.showUi(!r14.uiVisible);
                        VoIPFragment voIPFragment11 = VoIPFragment.this;
                        voIPFragment11.previousState = voIPFragment11.currentState;
                        if (!VoIPFragment.this.uiVisible && (hintView2 = VoIPFragment.this.tapToVideoTooltip) != null && hintView2.shown()) {
                            VoIPFragment.this.tapToVideoTooltip.hide();
                        }
                        VoIPFragment.this.updateViewState();
                    }
                }
                this.check = false;
            }
            return VoIPFragment.this.canZoomGesture || this.check;
        }

        @Override // android.view.ViewGroup
        protected boolean drawChild(Canvas canvas, View view, long j) {
            if (view == VoIPFragment.this.gradientLayout) {
                VoIPFragment voIPFragment = VoIPFragment.this;
                if (voIPFragment.currentUserIsVideo || voIPFragment.callingUserIsVideo) {
                    return false;
                }
            }
            if ((view == VoIPFragment.this.gradientLayout || view == VoIPFragment.this.callingUserTextureView || (view == VoIPFragment.this.currentUserCameraFloatingLayout && VoIPFragment.this.currentUserCameraIsFullscreen)) && (VoIPFragment.this.zoomStarted || VoIPFragment.this.zoomBackAnimator != null)) {
                canvas.save();
                VoIPFragment voIPFragment2 = VoIPFragment.this;
                float f = voIPFragment2.pinchScale;
                canvas.scale(f, f, voIPFragment2.pinchCenterX, VoIPFragment.this.pinchCenterY);
                canvas.translate(VoIPFragment.this.pinchTranslationX, VoIPFragment.this.pinchTranslationY);
                boolean zDrawChild = super.drawChild(canvas, view, j);
                canvas.restore();
                return zDrawChild;
            }
            return super.drawChild(canvas, view, j);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.VoIPFragment$3 */
    /* JADX INFO: loaded from: classes6.dex */
    class C71773 extends BackgroundGradientDrawable.ListenerAdapter {
        C71773() {
        }

        @Override // org.telegram.ui.Components.BackgroundGradientDrawable.ListenerAdapter, org.telegram.ui.Components.BackgroundGradientDrawable.Listener
        public void onAllSizesReady() {
            VoIPFragment.this.gradientLayout.invalidate();
        }
    }

    public /* synthetic */ void lambda$createView$5(float f, boolean z) {
        this.currentUserTextureView.setScreenshareMiniProgress(f, z);
    }

    public /* synthetic */ void lambda$createView$6(View view) {
        if (this.currentUserIsVideo && this.callingUserIsVideo && System.currentTimeMillis() - this.lastContentTapTime > 500) {
            AndroidUtilities.cancelRunOnUIThread(this.hideUIRunnable);
            this.hideUiRunnableWaiting = false;
            this.lastContentTapTime = System.currentTimeMillis();
            this.callingUserMiniFloatingLayout.setRelativePosition(this.currentUserCameraFloatingLayout);
            this.currentUserCameraIsFullscreen = true;
            this.cameraForceExpanded = true;
            this.previousState = this.currentState;
            updateViewState();
        }
    }

    public /* synthetic */ void lambda$createView$7(View view) {
        if (!this.cameraForceExpanded || System.currentTimeMillis() - this.lastContentTapTime <= 500) {
            return;
        }
        AndroidUtilities.cancelRunOnUIThread(this.hideUIRunnable);
        this.hideUiRunnableWaiting = false;
        this.lastContentTapTime = System.currentTimeMillis();
        this.currentUserCameraFloatingLayout.setRelativePosition(this.callingUserMiniFloatingLayout);
        this.currentUserCameraIsFullscreen = false;
        this.cameraForceExpanded = false;
        this.previousState = this.currentState;
        updateViewState();
    }

    /* JADX INFO: renamed from: org.telegram.ui.VoIPFragment$4 */
    /* JADX INFO: loaded from: classes6.dex */
    class C71784 extends LinearLayout {
        C71784(final Context context2) {
            super(context2);
        }

        @Override // android.view.View
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setVisibleToUser(VoIPFragment.this.emojiLoaded);
        }
    }

    public /* synthetic */ void lambda$createView$8(View view) {
        if (System.currentTimeMillis() - this.lastContentTapTime < 500) {
            return;
        }
        this.lastContentTapTime = System.currentTimeMillis();
        boolean z = this.emojiExpanded;
        if (!z && this.emojiLoaded) {
            expandEmoji(!z);
        }
    }

    public /* synthetic */ void lambda$createView$9(View view) {
        if (System.currentTimeMillis() - this.lastContentTapTime < 500) {
            return;
        }
        this.lastContentTapTime = System.currentTimeMillis();
        if (this.emojiLoaded) {
            expandEmoji(!this.emojiExpanded);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.VoIPFragment$5 */
    /* JADX INFO: loaded from: classes6.dex */
    class C71795 extends TextView {
        C71795(final Context context2) {
            super(context2);
        }

        @Override // android.widget.TextView, android.view.View
        protected void onLayout(boolean z2, int i, int i2, int i3, int i4) {
            super.onLayout(z2, i, i2, i3, i4);
            if (z2) {
                VoIPFragment.this.updateViewState();
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.VoIPFragment$6 */
    /* JADX INFO: loaded from: classes6.dex */
    class C71806 extends LinearLayout {
        C71806(final Context context2) {
            super(context2);
        }

        @Override // android.view.View
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            VoIPServiceState sharedState2 = VoIPService.getSharedState();
            CharSequence text = VoIPFragment.this.callingUserTitle.getText();
            if (sharedState2 == null || TextUtils.isEmpty(text)) {
                return;
            }
            StringBuilder sb = new StringBuilder(text);
            sb.append(", ");
            if (sharedState2.getPrivateCall() != null && sharedState2.getPrivateCall().video) {
                sb.append(LocaleController.getString(C2702R.string.VoipInVideoCallBranding));
            } else {
                sb.append(LocaleController.getString(C2702R.string.VoipInCallBranding));
            }
            long callDuration = sharedState2.getCallDuration();
            if (callDuration > 0) {
                sb.append(", ");
                sb.append(LocaleController.formatDuration((int) (callDuration / 1000)));
            }
            accessibilityNodeInfo.setText(sb);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.VoIPFragment$7 */
    /* JADX INFO: loaded from: classes6.dex */
    class C71817 extends VoIPToggleButton {
        C71817(final Context context2, float f) {
            super(context2, f);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchSetPressed(boolean z2) {
            super.dispatchSetPressed(z2);
            setPressedBtn(z2);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.VoIPFragment$8 */
    /* JADX INFO: loaded from: classes6.dex */
    class C71828 implements AcceptDeclineView.Listener {
        C71828() {
        }

        @Override // org.telegram.ui.Components.voip.AcceptDeclineView.Listener
        public void onAccept() {
            if (VoIPFragment.this.currentState == 17) {
                Intent intent = new Intent(VoIPFragment.this.activity, (Class<?>) VoIPService.class);
                intent.putExtra("user_id", VoIPFragment.this.callingUser.f1775id);
                intent.putExtra("is_outgoing", true);
                intent.putExtra("start_incall_activity", false);
                intent.putExtra("video_call", VoIPFragment.this.isVideoCall);
                intent.putExtra("can_video_call", VoIPFragment.this.isVideoCall);
                intent.putExtra("account", VoIPFragment.this.currentAccount);
                try {
                    VoIPFragment.this.activity.startService(intent);
                    return;
                } catch (Throwable th) {
                    FileLog.m1093e(th);
                    return;
                }
            }
            if (VoIPFragment.this.activity.checkSelfPermission("android.permission.RECORD_AUDIO") != 0) {
                VoIPFragment.this.activity.requestPermissions(new String[]{"android.permission.RECORD_AUDIO"}, 101);
            } else if (VoIPService.getSharedState() != null) {
                VoIPFragment.this.runAcceptCallAnimation(new Runnable() { // from class: org.telegram.ui.VoIPFragment$8$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onAccept$0();
                    }
                });
            }
        }

        public /* synthetic */ void lambda$onAccept$0() {
            if (VoIPService.getSharedState() != null) {
                VoIPService.getSharedState().acceptIncomingCall();
                if (!VoIPFragment.this.currentUserIsVideo || VoIPService.getSharedInstance() == null) {
                    return;
                }
                VoIPService.getSharedInstance().requestVideoCall(false);
            }
        }

        @Override // org.telegram.ui.Components.voip.AcceptDeclineView.Listener
        public void onDecline() {
            if (VoIPFragment.this.currentState == 17) {
                VoIPFragment.this.windowView.finish();
            } else if (VoIPService.getSharedState() != null) {
                VoIPService.getSharedState().declineIncomingCall();
            } else {
                VoIPFragment.this.windowView.finish();
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.VoIPFragment$9 */
    /* JADX INFO: loaded from: classes6.dex */
    class C71839 extends ImageView {
        C71839(final Context context2) {
            super(context2);
        }

        @Override // android.view.View
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName(ToggleButton.class.getName());
            accessibilityNodeInfo.setCheckable(true);
            VoIPService sharedInstance = VoIPService.getSharedInstance();
            if (sharedInstance != null) {
                accessibilityNodeInfo.setChecked(sharedInstance.isSpeakerphoneOn());
            }
        }
    }

    public /* synthetic */ void lambda$createView$10(View view) {
        VoIPService sharedInstance;
        int i;
        if (this.speakerPhoneIcon.getTag() == null || (sharedInstance = VoIPService.getSharedInstance()) == null) {
            return;
        }
        startWaitingFoHideUi();
        if (sharedInstance.isBluetoothOn()) {
            i = 2;
        } else {
            i = sharedInstance.isSpeakerphoneOn() ? 0 : 1;
        }
        sharedInstance.toggleSpeakerphoneOrShowRouteSheet(this.activity, false, Integer.valueOf(i));
    }

    public /* synthetic */ void lambda$createView$11(View view) {
        if (this.lockOnScreen) {
            return;
        }
        onBackPressed();
    }

    public /* synthetic */ void lambda$createView$13(Context context, View view) {
        if (this.lockOnScreen) {
            return;
        }
        UserSelectorBottomSheet userSelectorBottomSheet = this.addPeopleSheet;
        if (userSelectorBottomSheet != null) {
            userSelectorBottomSheet.lambda$new$0();
            this.addPeopleSheet = null;
        }
        UserSelectorBottomSheet userSelectorBottomSheet2 = new UserSelectorBottomSheet(context, this.currentAccount, 0L, null, 4, true, new DarkBlueThemeResourcesProvider());
        TLRPC.User user = this.currentUser;
        long j = user != null ? user.f1775id : 0L;
        TLRPC.User user2 = this.callingUser;
        UserSelectorBottomSheet onUsersSelector = userSelectorBottomSheet2.exceptUsers(j, user2 != null ? user2.f1775id : 0L).setOnUsersSelector(new Utilities.Callback2() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda36
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                VoIPFragment.$r8$lambda$MjosVzJyMoINzJh5GOo7HMxa2_k((Boolean) obj, (HashSet) obj2);
            }
        });
        this.addPeopleSheet = onUsersSelector;
        onUsersSelector.show();
    }

    public static /* synthetic */ void $r8$lambda$MjosVzJyMoINzJh5GOo7HMxa2_k(Boolean bool, HashSet hashSet) {
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        if (sharedInstance != null) {
            sharedInstance.convertToConferenceCall(bool.booleanValue(), hashSet);
        }
    }

    public /* synthetic */ void lambda$createView$14() {
        this.previousState = this.currentState;
        updateViewState();
    }

    public void runAcceptCallAnimation(Runnable runnable) {
        if (this.bottomVideoBtn.getVisibility() == 0) {
            this.acceptDeclineView.getLocationOnScreen(new int[2]);
            this.acceptDeclineView.stopAnimations();
            runnable.run();
            return;
        }
        this.bottomEndCallBtn.animate().cancel();
        this.bottomSpeakerBtn.animate().cancel();
        this.bottomMuteBtn.animate().cancel();
        this.bottomVideoBtn.animate().cancel();
        this.acceptDeclineView.getLocationOnScreen(new int[2]);
        this.acceptDeclineView.stopAnimations();
        this.bottomEndCallBtn.setData(C2702R.drawable.calls_decline, -1, -1041108, LocaleController.getString(C2702R.string.VoipEndCall2), false, false);
        this.bottomSpeakerBtn.setType(VoIpSwitchLayout.Type.SPEAKER, false);
        this.bottomMuteBtn.setType(VoIpSwitchLayout.Type.MICRO, false);
        this.bottomVideoBtn.setType(VoIpSwitchLayout.Type.VIDEO, true);
        this.bottomEndCallBtn.setVisibility(0);
        this.bottomSpeakerBtn.setVisibility(0);
        this.bottomMuteBtn.setVisibility(0);
        this.bottomVideoBtn.setVisibility(0);
        this.bottomEndCallBtn.setAlpha(0.0f);
        this.bottomSpeakerBtn.setAlpha(0.0f);
        this.bottomMuteBtn.setAlpha(0.0f);
        this.bottomVideoBtn.setAlpha(0.0f);
        final ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.acceptDeclineView.getLayoutParams();
        final int marginEnd = marginLayoutParams.getMarginEnd();
        AndroidUtilities.m1081dp(52.0f);
        final int iM1081dp = AndroidUtilities.m1081dp(24.0f);
        final int iM1081dp2 = AndroidUtilities.m1081dp(62.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda31
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$runAcceptCallAnimation$15(iM1081dp2, marginEnd, iM1081dp, marginLayoutParams, valueAnimator);
            }
        });
        AcceptDeclineView acceptDeclineView = this.acceptDeclineView;
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(acceptDeclineView, (Property<AcceptDeclineView, Float>) View.SCALE_X, acceptDeclineView.getScaleX(), 1.0f, 1.0f, 1.0f);
        AcceptDeclineView acceptDeclineView2 = this.acceptDeclineView;
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(acceptDeclineView2, (Property<AcceptDeclineView, Float>) View.SCALE_Y, acceptDeclineView2.getScaleY(), 1.0f, 1.0f, 1.0f);
        AcceptDeclineView acceptDeclineView3 = this.acceptDeclineView;
        animatorSet.playTogether(valueAnimatorOfFloat, objectAnimatorOfFloat, objectAnimatorOfFloat2, ObjectAnimator.ofFloat(acceptDeclineView3, (Property<AcceptDeclineView, Float>) View.ALPHA, acceptDeclineView3.getAlpha(), this.acceptDeclineView.getAlpha(), 0.0f, 0.0f));
        animatorSet.setDuration(400L);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.VoIPFragment.10
            final /* synthetic */ Runnable val$after;

            C716010(Runnable runnable2) {
                runnable = runnable2;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                runnable.run();
                VoIPFragment.this.acceptDeclineView.setScaleX(1.15f);
                VoIPFragment.this.acceptDeclineView.setScaleY(1.15f);
                ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) VoIPFragment.this.acceptDeclineView.getLayoutParams();
                marginLayoutParams2.leftMargin = AndroidUtilities.m1081dp(10.0f);
                marginLayoutParams2.rightMargin = AndroidUtilities.m1081dp(10.0f);
                VoIPFragment.this.acceptDeclineView.setVisibility(8);
            }
        });
        animatorSet.start();
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda32
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$runAcceptCallAnimation$16();
            }
        }, 133L);
    }

    public /* synthetic */ void lambda$runAcceptCallAnimation$15(int i, int i2, int i3, ViewGroup.MarginLayoutParams marginLayoutParams, ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.acceptDeclineView.setTranslationY(i * fFloatValue);
        int i4 = (int) (i2 - ((i2 + i3) * fFloatValue));
        marginLayoutParams.leftMargin = i4;
        marginLayoutParams.rightMargin = i4;
        this.acceptDeclineView.requestLayout();
    }

    /* JADX INFO: renamed from: org.telegram.ui.VoIPFragment$10 */
    /* JADX INFO: loaded from: classes6.dex */
    class C716010 extends AnimatorListenerAdapter {
        final /* synthetic */ Runnable val$after;

        C716010(Runnable runnable2) {
            runnable = runnable2;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            runnable.run();
            VoIPFragment.this.acceptDeclineView.setScaleX(1.15f);
            VoIPFragment.this.acceptDeclineView.setScaleY(1.15f);
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) VoIPFragment.this.acceptDeclineView.getLayoutParams();
            marginLayoutParams2.leftMargin = AndroidUtilities.m1081dp(10.0f);
            marginLayoutParams2.rightMargin = AndroidUtilities.m1081dp(10.0f);
            VoIPFragment.this.acceptDeclineView.setVisibility(8);
        }
    }

    public /* synthetic */ void lambda$runAcceptCallAnimation$16() {
        int[] iArr = new int[2];
        this.acceptDeclineView.getLocationOnScreen(iArr);
        int i = iArr[0];
        int i2 = iArr[1];
        this.bottomSpeakerBtn.getLocationOnScreen(iArr);
        this.bottomSpeakerBtn.setTranslationX((i - iArr[0]) + AndroidUtilities.m1081dp(42.0f));
        this.bottomSpeakerBtn.setTranslationY((i2 - iArr[1]) + AndroidUtilities.m1081dp(44.0f));
        this.bottomMuteBtn.getLocationOnScreen(iArr);
        this.bottomMuteBtn.setTranslationX((i - iArr[0]) + AndroidUtilities.m1081dp(42.0f));
        this.bottomMuteBtn.setTranslationY((i2 - iArr[1]) + AndroidUtilities.m1081dp(44.0f));
        this.bottomVideoBtn.getLocationOnScreen(iArr);
        this.bottomVideoBtn.setTranslationX((i - iArr[0]) + AndroidUtilities.m1081dp(42.0f));
        this.bottomVideoBtn.setTranslationY((i2 - iArr[1]) + AndroidUtilities.m1081dp(44.0f));
        this.bottomEndCallBtn.getLocationOnScreen(iArr);
        this.bottomEndCallBtn.setTranslationX((((i + this.acceptDeclineView.getWidth()) - iArr[0]) - AndroidUtilities.m1081dp(49.0f)) - AndroidUtilities.m1081dp(60.0f));
        this.bottomEndCallBtn.setTranslationY((i2 - iArr[1]) + AndroidUtilities.m1081dp(44.0f));
        this.bottomEndCallBtn.setAlpha(1.0f);
        this.bottomSpeakerBtn.setAlpha(1.0f);
        this.bottomMuteBtn.setAlpha(1.0f);
        this.bottomVideoBtn.setAlpha(1.0f);
        ViewPropertyAnimator viewPropertyAnimatorTranslationX = this.bottomEndCallBtn.animate().setStartDelay(0L).translationY(0.0f).setInterpolator(new LinearInterpolator()).translationX(0.0f);
        long j = DataTypes.EMPTY;
        viewPropertyAnimatorTranslationX.setDuration(j).start();
        this.bottomSpeakerBtn.animate().setStartDelay(0L).translationY(0.0f).setInterpolator(new LinearInterpolator()).translationX(0.0f).setDuration(j).start();
        this.bottomMuteBtn.animate().setStartDelay(0L).translationY(0.0f).setInterpolator(new LinearInterpolator()).translationX(0.0f).setDuration(j).start();
        this.bottomVideoBtn.animate().setStartDelay(0L).translationY(0.0f).setInterpolator(new LinearInterpolator()).translationX(0.0f).setDuration(j).start();
    }

    public boolean checkPointerIds(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() < 2) {
            return false;
        }
        if (this.pointerId1 == motionEvent.getPointerId(0) && this.pointerId2 == motionEvent.getPointerId(1)) {
            return true;
        }
        return this.pointerId1 == motionEvent.getPointerId(1) && this.pointerId2 == motionEvent.getPointerId(0);
    }

    public VoIPTextureView getFullscreenTextureView() {
        if (this.callingUserIsVideo) {
            return this.callingUserTextureView;
        }
        return this.currentUserTextureView;
    }

    public void finishZoom() {
        if (this.zoomStarted) {
            this.zoomStarted = false;
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
            this.zoomBackAnimator = valueAnimatorOfFloat;
            final float f = this.pinchScale;
            final float f2 = this.pinchTranslationX;
            final float f3 = this.pinchTranslationY;
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda45
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f$0.lambda$finishZoom$17(f, f2, f3, valueAnimator);
                }
            });
            this.zoomBackAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.VoIPFragment.11
                C716111() {
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    VoIPFragment voIPFragment = VoIPFragment.this;
                    voIPFragment.zoomBackAnimator = null;
                    voIPFragment.pinchScale = 1.0f;
                    voIPFragment.pinchTranslationX = 0.0f;
                    VoIPFragment.this.pinchTranslationY = 0.0f;
                    VoIPFragment.this.fragmentView.invalidate();
                }
            });
            this.zoomBackAnimator.setDuration(350L);
            this.zoomBackAnimator.setInterpolator(CubicBezierInterpolator.DEFAULT);
            this.zoomBackAnimator.start();
        }
        this.canZoomGesture = false;
        this.isInPinchToZoomTouchMode = false;
    }

    public /* synthetic */ void lambda$finishZoom$17(float f, float f2, float f3, ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.pinchScale = (f * fFloatValue) + ((1.0f - fFloatValue) * 1.0f);
        this.pinchTranslationX = f2 * fFloatValue;
        this.pinchTranslationY = f3 * fFloatValue;
        this.fragmentView.invalidate();
    }

    /* JADX INFO: renamed from: org.telegram.ui.VoIPFragment$11 */
    /* JADX INFO: loaded from: classes6.dex */
    class C716111 extends AnimatorListenerAdapter {
        C716111() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            VoIPFragment voIPFragment = VoIPFragment.this;
            voIPFragment.zoomBackAnimator = null;
            voIPFragment.pinchScale = 1.0f;
            voIPFragment.pinchTranslationX = 0.0f;
            VoIPFragment.this.pinchTranslationY = 0.0f;
            VoIPFragment.this.fragmentView.invalidate();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.VoIPFragment$12 */
    /* JADX INFO: loaded from: classes6.dex */
    class C716212 implements RendererCommon.RendererEvents {
        @Override // org.webrtc.RendererCommon.RendererEvents
        public void onFrameResolutionChanged(int i, int i2, int i3) {
        }

        C716212() {
        }

        public /* synthetic */ void lambda$onFirstFrameRendered$0() {
            VoIPFragment.this.updateViewState();
        }

        @Override // org.webrtc.RendererCommon.RendererEvents
        public void onFirstFrameRendered() {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.VoIPFragment$12$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onFirstFrameRendered$0();
                }
            });
        }
    }

    private void initRenderers() {
        this.currentUserTextureView.renderer.init(VideoCapturerDevice.getEglBase().getEglBaseContext(), new C716212());
        this.callingUserTextureView.renderer.init(VideoCapturerDevice.getEglBase().getEglBaseContext(), new C716313(), EglBase.CONFIG_PLAIN, new GlRectDrawer());
        this.callingUserMiniTextureRenderer.init(VideoCapturerDevice.getEglBase().getEglBaseContext(), null);
    }

    /* JADX INFO: renamed from: org.telegram.ui.VoIPFragment$13 */
    /* JADX INFO: loaded from: classes6.dex */
    class C716313 implements RendererCommon.RendererEvents {
        @Override // org.webrtc.RendererCommon.RendererEvents
        public void onFrameResolutionChanged(int i, int i2, int i3) {
        }

        C716313() {
        }

        @Override // org.webrtc.RendererCommon.RendererEvents
        public void onFirstFrameRendered() {
            if (VoIPFragment.this.firstFrameCallback != null) {
                VoIPFragment.this.firstFrameCallback.run();
                VoIPFragment.this.firstFrameCallback = null;
            }
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.VoIPFragment$13$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onFirstFrameRendered$0();
                }
            });
        }

        public /* synthetic */ void lambda$onFirstFrameRendered$0() {
            VoIPFragment.this.updateViewState();
        }
    }

    public void switchToPip() {
        if (this.isFinished || instance == null) {
            return;
        }
        this.isFinished = true;
        if (VoIPService.getSharedInstance() != null) {
            int measuredHeight = instance.windowView.getMeasuredHeight();
            VoIPFragment voIPFragment = instance;
            VoIPPiPView.show(voIPFragment.activity, voIPFragment.currentAccount, voIPFragment.windowView.getMeasuredWidth(), measuredHeight, 1);
            WindowInsets windowInsets = instance.lastInsets;
            if (windowInsets != null) {
                VoIPPiPView.topInset = windowInsets.getSystemWindowInsetTop();
                VoIPPiPView.bottomInset = instance.lastInsets.getSystemWindowInsetBottom();
            }
        }
        if (VoIPPiPView.getInstance() == null) {
            return;
        }
        ViewPropertyAnimator duration = this.speakerPhoneIcon.animate().alpha(0.0f).setDuration(150L);
        CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.DEFAULT;
        duration.setInterpolator(cubicBezierInterpolator).start();
        this.backIcon.animate().alpha(0.0f).setDuration(150L).setInterpolator(cubicBezierInterpolator).start();
        this.addIcon.animate().alpha(0.0f).setDuration(150L).setInterpolator(cubicBezierInterpolator).start();
        this.emojiLayout.animate().alpha(0.0f).setDuration(150L).setInterpolator(cubicBezierInterpolator).start();
        this.statusLayout.animate().alpha(0.0f).setDuration(150L).setInterpolator(cubicBezierInterpolator).start();
        this.buttonsLayout.animate().alpha(0.0f).setDuration(350L).setInterpolator(cubicBezierInterpolator).start();
        this.bottomShadow.animate().alpha(0.0f).setDuration(350L).setInterpolator(cubicBezierInterpolator).start();
        this.topShadow.animate().alpha(0.0f).setDuration(350L).setInterpolator(cubicBezierInterpolator).start();
        this.callingUserMiniFloatingLayout.animate().alpha(0.0f).setDuration(350L).setInterpolator(cubicBezierInterpolator).start();
        this.notificationsLayout.animate().alpha(0.0f).setDuration(350L).setInterpolator(cubicBezierInterpolator).start();
        VoIPPiPView.switchingToPip = true;
        this.switchingToPip = true;
        Animator animatorCreatePiPTransition = createPiPTransition(false);
        this.notificationsLocker.lock();
        animatorCreatePiPTransition.addListener(new C716414());
        animatorCreatePiPTransition.setDuration(350L);
        animatorCreatePiPTransition.setInterpolator(cubicBezierInterpolator);
        animatorCreatePiPTransition.start();
    }

    /* JADX INFO: renamed from: org.telegram.ui.VoIPFragment$14 */
    /* JADX INFO: loaded from: classes6.dex */
    class C716414 extends AnimatorListenerAdapter {
        C716414() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            VoIPPiPView.getInstance().windowView.setAlpha(1.0f);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.VoIPFragment$14$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onAnimationEnd$0();
                }
            }, 200L);
        }

        public /* synthetic */ void lambda$onAnimationEnd$0() {
            VoIPFragment.this.notificationsLocker.unlock();
            VoIPPiPView.getInstance().onTransitionEnd();
            VoIPFragment.this.currentUserCameraFloatingLayout.setCornerRadius(-1.0f);
            VoIPFragment.this.callingUserTextureView.renderer.release();
            VoIPFragment.this.currentUserTextureView.renderer.release();
            VoIPFragment.this.callingUserMiniTextureRenderer.release();
            VoIPFragment.this.destroy();
            VoIPFragment.this.windowView.finishImmediate();
            VoIPPiPView.switchingToPip = false;
            VoIPFragment.this.switchingToPip = false;
            VoIPFragment.instance = null;
        }
    }

    public void startTransitionFromPiP() {
        this.enterFromPiP = true;
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        if (sharedInstance != null && sharedInstance.getVideoState(false) == 2) {
            this.callingUserTextureView.setStub(VoIPPiPView.getInstance().callingUserTextureView);
            this.currentUserTextureView.setStub(VoIPPiPView.getInstance().currentUserTextureView);
        }
        this.windowView.setAlpha(0.0f);
        updateViewState();
        this.switchingToPip = true;
        VoIPPiPView.switchingToPip = true;
        VoIPPiPView.prepareForTransition();
        this.notificationsLocker.lock();
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$startTransitionFromPiP$19();
            }
        }, 32L);
    }

    public /* synthetic */ void lambda$startTransitionFromPiP$19() {
        this.windowView.setAlpha(1.0f);
        this.windowView.invalidate();
        final Animator animatorCreatePiPTransition = createPiPTransition(true);
        this.backIcon.setAlpha(0.0f);
        this.addIcon.setAlpha(0.0f);
        this.emojiLayout.setAlpha(0.0f);
        this.statusLayout.setAlpha(0.0f);
        this.buttonsLayout.setAlpha(0.0f);
        this.bottomShadow.setAlpha(0.0f);
        this.topShadow.setAlpha(0.0f);
        this.speakerPhoneIcon.setAlpha(0.0f);
        this.notificationsLayout.setAlpha(0.0f);
        this.currentUserCameraFloatingLayout.switchingToPip = true;
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda40
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$startTransitionFromPiP$18(animatorCreatePiPTransition);
            }
        }, 32L);
    }

    public /* synthetic */ void lambda$startTransitionFromPiP$18(Animator animator) {
        VoIPPiPView.switchingToPip = false;
        VoIPPiPView.finish();
        ViewPropertyAnimator duration = this.speakerPhoneIcon.animate().setDuration(150L);
        CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.DEFAULT;
        duration.setInterpolator(cubicBezierInterpolator).start();
        this.backIcon.animate().alpha(1.0f).setDuration(150L).setInterpolator(cubicBezierInterpolator).start();
        this.addIcon.animate().alpha(1.0f).setDuration(150L).setInterpolator(cubicBezierInterpolator).start();
        this.emojiLayout.animate().alpha(1.0f).setDuration(150L).setInterpolator(cubicBezierInterpolator).start();
        this.statusLayout.animate().alpha(1.0f).setDuration(150L).setInterpolator(cubicBezierInterpolator).start();
        this.buttonsLayout.animate().alpha(1.0f).setDuration(150L).setInterpolator(cubicBezierInterpolator).start();
        this.bottomShadow.animate().alpha(1.0f).setDuration(350L).setInterpolator(cubicBezierInterpolator).start();
        this.topShadow.animate().alpha(1.0f).setDuration(350L).setInterpolator(cubicBezierInterpolator).start();
        this.notificationsLayout.animate().alpha(1.0f).setDuration(350L).setInterpolator(cubicBezierInterpolator).start();
        animator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.VoIPFragment.15
            C716515() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator2) {
                VoIPFragment.this.notificationsLocker.unlock();
                VoIPFragment.this.currentUserCameraFloatingLayout.setCornerRadius(-1.0f);
                VoIPFragment.this.switchingToPip = false;
                VoIPFragment.this.currentUserCameraFloatingLayout.switchingToPip = false;
                VoIPFragment voIPFragment = VoIPFragment.this;
                voIPFragment.previousState = voIPFragment.currentState;
                VoIPFragment.this.updateViewState();
            }
        });
        animator.setDuration(350L);
        animator.setInterpolator(cubicBezierInterpolator);
        animator.start();
    }

    /* JADX INFO: renamed from: org.telegram.ui.VoIPFragment$15 */
    /* JADX INFO: loaded from: classes6.dex */
    class C716515 extends AnimatorListenerAdapter {
        C716515() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator2) {
            VoIPFragment.this.notificationsLocker.unlock();
            VoIPFragment.this.currentUserCameraFloatingLayout.setCornerRadius(-1.0f);
            VoIPFragment.this.switchingToPip = false;
            VoIPFragment.this.currentUserCameraFloatingLayout.switchingToPip = false;
            VoIPFragment voIPFragment = VoIPFragment.this;
            voIPFragment.previousState = voIPFragment.currentState;
            VoIPFragment.this.updateViewState();
        }
    }

    public Animator createPiPTransition(boolean z) {
        final float measuredWidth;
        final float measuredHeight;
        float f;
        final boolean z2;
        float f2;
        float measuredWidth2;
        float measuredHeight2;
        float measuredWidth3;
        boolean z3;
        this.currentUserCameraFloatingLayout.animate().cancel();
        float f3 = VoIPPiPView.getInstance().windowLayoutParams.x + VoIPPiPView.getInstance().xOffset;
        float f4 = VoIPPiPView.getInstance().windowLayoutParams.y + VoIPPiPView.getInstance().yOffset;
        final float x = this.currentUserCameraFloatingLayout.getX();
        final float y = this.currentUserCameraFloatingLayout.getY();
        final float scaleX = this.currentUserCameraFloatingLayout.getScaleX();
        final float f5 = VoIPPiPView.isExpanding() ? 0.4f : 0.25f;
        final float measuredWidth4 = f3 - ((this.callingUserTextureView.getMeasuredWidth() - (this.callingUserTextureView.getMeasuredWidth() * f5)) / 2.0f);
        final float measuredHeight3 = f4 - ((this.callingUserTextureView.getMeasuredHeight() - (this.callingUserTextureView.getMeasuredHeight() * f5)) / 2.0f);
        if (this.callingUserIsVideo) {
            int measuredWidth5 = this.currentUserCameraFloatingLayout.getMeasuredWidth();
            if (!this.currentUserIsVideo || measuredWidth5 == 0) {
                measuredWidth2 = 1.0f;
                measuredHeight2 = 1.0f;
                measuredWidth3 = 0.0f;
                z3 = false;
            } else {
                measuredWidth3 = (this.windowView.getMeasuredWidth() / measuredWidth5) * f5 * 0.4f;
                measuredWidth2 = (((f3 - ((this.currentUserCameraFloatingLayout.getMeasuredWidth() - (this.currentUserCameraFloatingLayout.getMeasuredWidth() * measuredWidth3)) / 2.0f)) + (VoIPPiPView.getInstance().parentWidth * f5)) - ((VoIPPiPView.getInstance().parentWidth * f5) * 0.4f)) - AndroidUtilities.m1081dp(4.0f);
                measuredHeight2 = (((f4 - ((this.currentUserCameraFloatingLayout.getMeasuredHeight() - (this.currentUserCameraFloatingLayout.getMeasuredHeight() * measuredWidth3)) / 2.0f)) + (VoIPPiPView.getInstance().parentHeight * f5)) - ((VoIPPiPView.getInstance().parentHeight * f5) * 0.4f)) - AndroidUtilities.m1081dp(4.0f);
                z3 = true;
            }
            float f6 = measuredWidth3;
            measuredWidth = measuredWidth2;
            f = f6;
            boolean z4 = z3;
            measuredHeight = measuredHeight2;
            z2 = z4;
        } else {
            measuredWidth = f3 - ((this.currentUserCameraFloatingLayout.getMeasuredWidth() - (this.currentUserCameraFloatingLayout.getMeasuredWidth() * f5)) / 2.0f);
            measuredHeight = f4 - ((this.currentUserCameraFloatingLayout.getMeasuredHeight() - (this.currentUserCameraFloatingLayout.getMeasuredHeight() * f5)) / 2.0f);
            f = f5;
            z2 = true;
        }
        float fM1081dp = this.callingUserIsVideo ? AndroidUtilities.m1081dp(4.0f) : 0.0f;
        final float fM1081dp2 = (AndroidUtilities.m1081dp(4.0f) * 1.0f) / f;
        float f7 = (!this.callingUserIsVideo || VoIPPiPView.isExpanding()) ? 1.0f : 0.0f;
        if (z) {
            if (z2) {
                this.currentUserCameraFloatingLayout.setScaleX(f);
                this.currentUserCameraFloatingLayout.setScaleY(f);
                this.currentUserCameraFloatingLayout.setTranslationX(measuredWidth);
                this.currentUserCameraFloatingLayout.setTranslationY(measuredHeight);
                this.currentUserCameraFloatingLayout.setCornerRadius(fM1081dp2);
                this.currentUserCameraFloatingLayout.setAlpha(f7);
            }
            this.callingUserTextureView.setScaleX(f5);
            this.callingUserTextureView.setScaleY(f5);
            this.callingUserTextureView.setTranslationX(measuredWidth4);
            this.callingUserTextureView.setTranslationY(measuredHeight3);
            f2 = 1.0f;
            this.callingUserTextureView.setRoundCorners((AndroidUtilities.m1081dp(6.0f) * 1.0f) / f5);
        } else {
            f2 = 1.0f;
        }
        final float f8 = f;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(z ? f2 : 0.0f, z ? 0.0f : f2);
        this.enterTransitionProgress = z ? 0.0f : f2;
        updateSystemBarColors();
        final float f9 = fM1081dp;
        final float f10 = f7;
        final float f11 = 1.0f;
        final float f12 = 1.0f;
        final float f13 = 0.0f;
        final float f14 = 0.0f;
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda35
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$createPiPTransition$20(z2, scaleX, f8, x, measuredWidth, y, measuredHeight, f9, fM1081dp2, f11, f10, f12, f5, f13, measuredWidth4, f14, measuredHeight3, valueAnimator);
            }
        });
        return valueAnimatorOfFloat;
    }

    public /* synthetic */ void lambda$createPiPTransition$20(boolean z, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16, ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        float f17 = 1.0f - fFloatValue;
        this.enterTransitionProgress = f17;
        updateSystemBarColors();
        if (z) {
            float f18 = (f * f17) + (f2 * fFloatValue);
            this.currentUserCameraFloatingLayout.setScaleX(f18);
            this.currentUserCameraFloatingLayout.setScaleY(f18);
            this.currentUserCameraFloatingLayout.setTranslationX((f3 * f17) + (f4 * fFloatValue));
            this.currentUserCameraFloatingLayout.setTranslationY((f5 * f17) + (f6 * fFloatValue));
            this.currentUserCameraFloatingLayout.setCornerRadius((f7 * f17) + (f8 * fFloatValue));
            this.currentUserCameraFloatingLayout.setAlpha((f9 * f17) + (f10 * fFloatValue));
        }
        float f19 = (f11 * f17) + (f12 * fFloatValue);
        this.callingUserTextureView.setScaleX(f19);
        this.callingUserTextureView.setScaleY(f19);
        this.callingUserTextureView.setTranslationX((f13 * f17) + (f14 * fFloatValue));
        this.callingUserTextureView.setTranslationY((f15 * f17) + (f16 * fFloatValue));
        this.callingUserTextureView.setRoundCorners(((AndroidUtilities.m1081dp(4.0f) * fFloatValue) * 1.0f) / f19);
        if (!this.currentUserCameraFloatingLayout.measuredAsFloatingMode) {
            this.currentUserTextureView.setScreenshareMiniProgress(fFloatValue, false);
        }
        this.windowView.invalidate();
    }

    public void expandEmoji(boolean z) {
        if (this.emojiLoaded && this.emojiExpanded != z && this.uiVisible) {
            this.emojiExpanded = z;
            this.voIpCoverView.onEmojiExpanded(z);
            if (z) {
                if (SharedConfig.callEncryptionHintDisplayedCount < 2) {
                    SharedConfig.incrementCallEncryptionHintDisplayed(2);
                }
                this.encryptionTooltip.hide();
                AndroidUtilities.cancelRunOnUIThread(this.hideUIRunnable);
                this.hideUiRunnableWaiting = false;
                if (this.callingUserPhotoViewMini.getVisibility() == 0) {
                    this.callingUserPhotoViewMini.animate().setStartDelay(0L).translationY(AndroidUtilities.m1081dp(48.0f)).scaleY(0.1f).scaleX(0.1f).alpha(0.0f).setDuration(200L).setInterpolator(CubicBezierInterpolator.DEFAULT).start();
                }
                this.hideEmojiLayout.animate().setListener(null).cancel();
                this.hideEmojiLayout.setVisibility(0);
                this.hideEmojiLayout.setAlpha(0.0f);
                this.hideEmojiLayout.setScaleX(0.3f);
                this.hideEmojiLayout.setScaleY(0.3f);
                this.hideEmojiLayout.animate().alpha(1.0f).scaleY(1.0f).scaleX(1.0f).setDuration(340L).setInterpolator(CubicBezierInterpolator.EASE_OUT).start();
                ViewPropertyAnimator viewPropertyAnimatorTranslationY = this.emojiLayout.animate().scaleX(1.72f).scaleY(1.72f).translationY(AndroidUtilities.m1081dp(140.0f));
                CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.DEFAULT;
                viewPropertyAnimatorTranslationY.setInterpolator(cubicBezierInterpolator).setDuration(400L).start();
                this.emojiRationalLayout.animate().setListener(null).cancel();
                this.emojiRationalLayout.setVisibility(0);
                this.emojiRationalLayout.setTranslationY(-AndroidUtilities.m1081dp(120.0f));
                this.emojiRationalLayout.setScaleX(0.7f);
                this.emojiRationalLayout.setScaleY(0.7f);
                this.emojiRationalLayout.setAlpha(0.0f);
                this.emojiRationalLayout.animate().alpha(1.0f).translationY(0.0f).scaleX(1.0f).scaleY(1.0f).setDuration(400L).setListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.VoIPFragment.16
                    C716616() {
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        for (BackupImageView backupImageView : VoIPFragment.this.emojiViews) {
                            AnimatedEmojiDrawable animatedEmojiDrawable = backupImageView.animatedEmojiDrawable;
                            if (animatedEmojiDrawable != null && animatedEmojiDrawable.getImageReceiver() != null) {
                                backupImageView.animatedEmojiDrawable.getImageReceiver().setAllowStartAnimation(true);
                                backupImageView.animatedEmojiDrawable.getImageReceiver().startAnimation();
                            }
                        }
                    }
                }).setInterpolator(cubicBezierInterpolator).start();
            } else {
                if (this.callingUserPhotoViewMini.getVisibility() == 0) {
                    this.callingUserPhotoViewMini.animate().setStartDelay(50L).translationY(0.0f).scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(250L).setInterpolator(CubicBezierInterpolator.DEFAULT).start();
                }
                this.hideEmojiLayout.animate().setListener(null).cancel();
                ViewPropertyAnimator duration = this.hideEmojiLayout.animate().alpha(0.0f).scaleY(0.3f).scaleX(0.3f).setDuration(230L);
                CubicBezierInterpolator cubicBezierInterpolator2 = CubicBezierInterpolator.DEFAULT;
                duration.setInterpolator(cubicBezierInterpolator2).setListener(new HideViewAfterAnimation(this.hideEmojiLayout)).start();
                this.emojiLayout.animate().scaleX(1.0f).scaleY(1.0f).translationY(0.0f).setInterpolator(cubicBezierInterpolator2).setDuration(280L).start();
                this.emojiRationalLayout.animate().setListener(null).cancel();
                this.emojiRationalLayout.animate().alpha(0.0f).scaleY(0.7f).scaleX(0.7f).translationY(-AndroidUtilities.m1081dp(120.0f)).setListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.VoIPFragment.17
                    C716717() {
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        VoIPFragment.this.startWaitingFoHideUi();
                        for (BackupImageView backupImageView : VoIPFragment.this.emojiViews) {
                            AnimatedEmojiDrawable animatedEmojiDrawable = backupImageView.animatedEmojiDrawable;
                            if (animatedEmojiDrawable != null && animatedEmojiDrawable.getImageReceiver() != null) {
                                backupImageView.animatedEmojiDrawable.getImageReceiver().setAllowStartAnimation(false);
                                backupImageView.animatedEmojiDrawable.getImageReceiver().stopAnimation();
                            }
                        }
                        VoIPFragment.this.emojiRationalLayout.setVisibility(8);
                    }
                }).setDuration(250L).setInterpolator(cubicBezierInterpolator2).start();
            }
            this.previousState = this.currentState;
            updateViewState();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.VoIPFragment$16 */
    /* JADX INFO: loaded from: classes6.dex */
    class C716616 extends AnimatorListenerAdapter {
        C716616() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            for (BackupImageView backupImageView : VoIPFragment.this.emojiViews) {
                AnimatedEmojiDrawable animatedEmojiDrawable = backupImageView.animatedEmojiDrawable;
                if (animatedEmojiDrawable != null && animatedEmojiDrawable.getImageReceiver() != null) {
                    backupImageView.animatedEmojiDrawable.getImageReceiver().setAllowStartAnimation(true);
                    backupImageView.animatedEmojiDrawable.getImageReceiver().startAnimation();
                }
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.VoIPFragment$17 */
    /* JADX INFO: loaded from: classes6.dex */
    class C716717 extends AnimatorListenerAdapter {
        C716717() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            VoIPFragment.this.startWaitingFoHideUi();
            for (BackupImageView backupImageView : VoIPFragment.this.emojiViews) {
                AnimatedEmojiDrawable animatedEmojiDrawable = backupImageView.animatedEmojiDrawable;
                if (animatedEmojiDrawable != null && animatedEmojiDrawable.getImageReceiver() != null) {
                    backupImageView.animatedEmojiDrawable.getImageReceiver().setAllowStartAnimation(false);
                    backupImageView.animatedEmojiDrawable.getImageReceiver().stopAnimation();
                }
            }
            VoIPFragment.this.emojiRationalLayout.setVisibility(8);
        }
    }

    public void startWaitingFoHideUi() {
        if (VoIPService.getSharedInstance() != null) {
            AndroidUtilities.cancelRunOnUIThread(this.hideUIRunnable);
            this.hideUiRunnableWaiting = false;
            if (this.canHideUI && this.uiVisible) {
                AndroidUtilities.runOnUIThread(this.hideUIRunnable, 3000L);
                this.hideUiRunnableWaiting = true;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:949:0x003b. Please report as an issue. */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:1027:0x0448  */
    /* JADX WARN: Removed duplicated region for block: B:1042:0x0480  */
    /* JADX WARN: Removed duplicated region for block: B:1136:0x05ba  */
    /* JADX WARN: Removed duplicated region for block: B:1144:0x05cc  */
    /* JADX WARN: Removed duplicated region for block: B:1165:0x05f4  */
    /* JADX WARN: Removed duplicated region for block: B:1168:0x05fb  */
    /* JADX WARN: Removed duplicated region for block: B:1185:0x0686  */
    /* JADX WARN: Removed duplicated region for block: B:1211:0x06d8  */
    /* JADX WARN: Removed duplicated region for block: B:1232:0x0726  */
    /* JADX WARN: Removed duplicated region for block: B:1234:0x072d  */
    /* JADX WARN: Removed duplicated region for block: B:1249:0x07a2  */
    /* JADX WARN: Removed duplicated region for block: B:1255:0x07b4  */
    /* JADX WARN: Removed duplicated region for block: B:1266:0x07cc  */
    /* JADX WARN: Removed duplicated region for block: B:1269:0x07d2  */
    /* JADX WARN: Removed duplicated region for block: B:1288:0x0817  */
    /* JADX WARN: Removed duplicated region for block: B:1291:0x0825  */
    /* JADX WARN: Removed duplicated region for block: B:1292:0x0833  */
    /* JADX WARN: Removed duplicated region for block: B:1295:0x0843  */
    /* JADX WARN: Removed duplicated region for block: B:1297:0x0847  */
    /* JADX WARN: Removed duplicated region for block: B:1305:0x085f  */
    /* JADX WARN: Removed duplicated region for block: B:1306:0x087c  */
    /* JADX WARN: Removed duplicated region for block: B:1309:0x0887  */
    /* JADX WARN: Removed duplicated region for block: B:1310:0x08a6  */
    /* JADX WARN: Removed duplicated region for block: B:1318:0x08e1  */
    /* JADX WARN: Removed duplicated region for block: B:1327:0x0932  */
    /* JADX WARN: Removed duplicated region for block: B:1329:0x093a  */
    /* JADX WARN: Removed duplicated region for block: B:1331:0x0941  */
    /* JADX WARN: Removed duplicated region for block: B:1334:0x0968  */
    /* JADX WARN: Removed duplicated region for block: B:1341:0x097c  */
    /* JADX WARN: Removed duplicated region for block: B:1350:0x09fb  */
    /* JADX WARN: Removed duplicated region for block: B:1356:0x0a4d  */
    /* JADX WARN: Removed duplicated region for block: B:1363:0x0a89  */
    /* JADX WARN: Removed duplicated region for block: B:1370:0x0a96  */
    /* JADX WARN: Removed duplicated region for block: B:1378:0x0ab6  */
    /* JADX WARN: Removed duplicated region for block: B:1380:0x0abb  */
    /* JADX WARN: Removed duplicated region for block: B:1383:0x0acf  */
    /* JADX WARN: Removed duplicated region for block: B:1388:0x0aec  */
    /* JADX WARN: Removed duplicated region for block: B:1391:0x0af7  */
    /* JADX WARN: Removed duplicated region for block: B:1395:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:1397:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateViewState() {
        /*
            Method dump skipped, instruction units count: 2830
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.VoIPFragment.updateViewState():void");
    }

    public /* synthetic */ void lambda$updateViewState$23(final VoIPService voIPService) {
        this.endCloseLayout.switchToClose(new View.OnClickListener() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda41
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$updateViewState$22(voIPService, view);
            }
        }, true);
    }

    public /* synthetic */ void lambda$updateViewState$21() {
        this.windowView.finish();
    }

    public /* synthetic */ void lambda$updateViewState$22(VoIPService voIPService, View view) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda44
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateViewState$21();
            }
        });
        int i = this.selectedRating;
        if (i > 0) {
            voIPService.sendCallRating(i);
        }
    }

    public /* synthetic */ void lambda$updateViewState$24() {
        this.windowView.finish();
    }

    public /* synthetic */ void lambda$updateViewState$25(VoIPService voIPService, View view) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda33
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateViewState$24();
            }
        });
        int i = this.selectedRating;
        if (i > 0) {
            voIPService.sendCallRating(i);
        }
    }

    public /* synthetic */ void lambda$updateViewState$26(int i) {
        this.selectedRating = i;
    }

    /* JADX INFO: renamed from: org.telegram.ui.VoIPFragment$18 */
    /* JADX INFO: loaded from: classes6.dex */
    class C716818 extends AnimatorListenerAdapter {
        C716818() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            VoIPFragment.this.callingUserTitle.setText(LocaleController.getString(C2702R.string.VoipCallEnded));
            VoIPFragment.this.callingUserTitle.animate().alpha(1.0f).setDuration(70L).setListener(null).start();
        }
    }

    public /* synthetic */ void lambda$updateViewState$27() {
        this.windowView.finish();
    }

    public /* synthetic */ void lambda$updateViewState$28(AlertDialog alertDialog, int i) {
        this.windowView.finish();
    }

    public /* synthetic */ void lambda$updateViewState$29(boolean[] zArr, AlertDialog alertDialog, int i) {
        zArr[0] = true;
        this.currentState = 17;
        Intent intent = new Intent(this.activity, (Class<?>) VoIPService.class);
        intent.putExtra("user_id", this.callingUser.f1775id);
        intent.putExtra("is_outgoing", true);
        intent.putExtra("start_incall_activity", false);
        intent.putExtra("video_call", false);
        intent.putExtra("can_video_call", false);
        intent.putExtra("account", this.currentAccount);
        try {
            this.activity.startService(intent);
        } catch (Throwable th) {
            FileLog.m1093e(th);
        }
    }

    public /* synthetic */ void lambda$updateViewState$30(boolean[] zArr, DialogInterface dialogInterface) {
        if (zArr[0]) {
            return;
        }
        this.windowView.finish();
    }

    public /* synthetic */ void lambda$updateViewState$31() {
        this.windowView.finish();
    }

    public /* synthetic */ void lambda$updateViewState$32() {
        this.windowView.finish();
    }

    public /* synthetic */ void lambda$updateViewState$33() {
        VoIPFloatingLayout voIPFloatingLayout = this.callingUserMiniFloatingLayout;
        voIPFloatingLayout.isAppearing = false;
        voIPFloatingLayout.invalidate();
    }

    /* JADX INFO: renamed from: org.telegram.ui.VoIPFragment$19 */
    /* JADX INFO: loaded from: classes6.dex */
    class C716919 extends AnimatorListenerAdapter {
        C716919() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (VoIPFragment.this.callingUserMiniFloatingLayout.getTag() == null) {
                VoIPFragment.this.callingUserMiniFloatingLayout.setVisibility(8);
            }
        }
    }

    public void showUi(boolean z) {
        int i;
        ValueAnimator valueAnimator = this.uiVisibilityAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        if (!z && this.uiVisible) {
            ViewPropertyAnimator duration = this.speakerPhoneIcon.animate().alpha(0.0f).translationY(-AndroidUtilities.m1081dp(10.0f)).setDuration(150L);
            CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.DEFAULT;
            duration.setInterpolator(cubicBezierInterpolator).start();
            this.backIcon.animate().alpha(0.0f).translationY(-AndroidUtilities.m1081dp(10.0f)).setDuration(150L).setInterpolator(cubicBezierInterpolator).start();
            this.addIcon.animate().alpha(0.0f).translationY(-AndroidUtilities.m1081dp(10.0f)).setDuration(150L).setInterpolator(cubicBezierInterpolator).start();
            this.emojiLayout.animate().alpha(0.0f).translationY(-AndroidUtilities.m1081dp(10.0f)).setDuration(150L).setInterpolator(cubicBezierInterpolator).start();
            this.callingUserTitle.animate().alpha(0.0f).setDuration(150L).translationY(-AndroidUtilities.m1081dp(10.0f)).setInterpolator(cubicBezierInterpolator).start();
            this.statusTextView.animate().alpha(0.0f).setDuration(150L).translationY(-AndroidUtilities.m1081dp(10.0f)).setInterpolator(cubicBezierInterpolator).start();
            this.buttonsLayout.animate().alpha(0.0f).translationY(AndroidUtilities.m1081dp(10.0f)).setDuration(150L).setInterpolator(cubicBezierInterpolator).start();
            this.bottomShadow.animate().alpha(0.0f).setDuration(150L).setInterpolator(cubicBezierInterpolator).start();
            this.topShadow.animate().alpha(0.0f).setDuration(150L).setInterpolator(cubicBezierInterpolator).start();
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.uiVisibilityAlpha, 0.0f);
            this.uiVisibilityAnimator = valueAnimatorOfFloat;
            valueAnimatorOfFloat.addUpdateListener(this.statusbarAnimatorListener);
            this.uiVisibilityAnimator.setDuration(150L).setInterpolator(cubicBezierInterpolator);
            this.uiVisibilityAnimator.start();
            AndroidUtilities.cancelRunOnUIThread(this.hideUIRunnable);
            this.hideUiRunnableWaiting = false;
            this.buttonsLayout.setEnabled(false);
            this.encryptionTooltip.hide();
            i = 150;
        } else {
            if (z && !this.uiVisible) {
                this.tapToVideoTooltip.hide();
                this.encryptionTooltip.hide();
                ViewPropertyAnimator viewPropertyAnimatorTranslationY = this.callingUserTitle.animate().alpha(1.0f).setDuration(150L).translationY(0.0f);
                CubicBezierInterpolator cubicBezierInterpolator2 = CubicBezierInterpolator.DEFAULT;
                viewPropertyAnimatorTranslationY.setInterpolator(cubicBezierInterpolator2).start();
                this.statusTextView.animate().alpha(1.0f).setDuration(150L).translationY(0.0f).setInterpolator(cubicBezierInterpolator2).start();
                this.speakerPhoneIcon.animate().alpha(1.0f).translationY(0.0f).setDuration(150L).setInterpolator(cubicBezierInterpolator2).start();
                this.backIcon.animate().alpha(1.0f).translationY(0.0f).setDuration(150L).setInterpolator(cubicBezierInterpolator2).start();
                this.addIcon.animate().alpha(1.0f).translationY(0.0f).setDuration(150L).setInterpolator(cubicBezierInterpolator2).start();
                this.emojiLayout.animate().alpha(1.0f).translationY(0.0f).setDuration(150L).setInterpolator(cubicBezierInterpolator2).start();
                this.buttonsLayout.animate().alpha(1.0f).translationY(0.0f).setDuration(150L).setInterpolator(cubicBezierInterpolator2).start();
                this.bottomShadow.animate().alpha(1.0f).setDuration(150L).setInterpolator(cubicBezierInterpolator2).start();
                this.topShadow.animate().alpha(1.0f).setDuration(150L).setInterpolator(cubicBezierInterpolator2).start();
                ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(this.uiVisibilityAlpha, 1.0f);
                this.uiVisibilityAnimator = valueAnimatorOfFloat2;
                valueAnimatorOfFloat2.addUpdateListener(this.statusbarAnimatorListener);
                this.uiVisibilityAnimator.setDuration(150L).setInterpolator(cubicBezierInterpolator2);
                this.uiVisibilityAnimator.start();
                this.buttonsLayout.setEnabled(true);
            }
            i = 0;
        }
        this.uiVisible = z;
        this.windowView.requestFullscreen(!z);
        this.notificationsLayout.animate().translationY((-AndroidUtilities.m1081dp(16.0f)) - (this.uiVisible ? AndroidUtilities.m1081dp(80.0f) : 0)).setDuration(150L).setStartDelay(i).setInterpolator(CubicBezierInterpolator.DEFAULT).start();
    }

    private void showFloatingLayout(int i, boolean z) {
        Animator animator;
        if (this.currentUserCameraFloatingLayout.getTag() == null || ((Integer) this.currentUserCameraFloatingLayout.getTag()).intValue() != 2) {
            this.currentUserCameraFloatingLayout.setUiVisible(this.uiVisible);
        }
        if (!z && (animator = this.cameraShowingAnimator) != null) {
            animator.removeAllListeners();
            this.cameraShowingAnimator.cancel();
        }
        if (i != 0) {
            boolean z2 = (this.currentUserCameraFloatingLayout.getTag() == null || ((Integer) this.currentUserCameraFloatingLayout.getTag()).intValue() == 0) ? false : z;
            if (z) {
                if (this.currentUserCameraFloatingLayout.getTag() != null && ((Integer) this.currentUserCameraFloatingLayout.getTag()).intValue() == 0) {
                    if (this.currentUserCameraFloatingLayout.getVisibility() == 8) {
                        this.currentUserCameraFloatingLayout.setAlpha(0.0f);
                        this.currentUserCameraFloatingLayout.setScaleX(0.7f);
                        this.currentUserCameraFloatingLayout.setScaleY(0.7f);
                        this.currentUserCameraFloatingLayout.setVisibility(0);
                    }
                    Animator animator2 = this.cameraShowingAnimator;
                    if (animator2 != null) {
                        animator2.removeAllListeners();
                        this.cameraShowingAnimator.cancel();
                    }
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(ObjectAnimator.ofFloat(this.currentUserCameraFloatingLayout, (Property<VoIPFloatingLayout, Float>) View.ALPHA, 0.0f, 1.0f), ObjectAnimator.ofFloat(this.currentUserCameraFloatingLayout, (Property<VoIPFloatingLayout, Float>) View.SCALE_X, 0.7f, 1.0f), ObjectAnimator.ofFloat(this.currentUserCameraFloatingLayout, (Property<VoIPFloatingLayout, Float>) View.SCALE_Y, 0.7f, 1.0f));
                    this.cameraShowingAnimator = animatorSet;
                    animatorSet.setDuration(150L).start();
                }
            } else {
                this.currentUserCameraFloatingLayout.setVisibility(0);
            }
            if (this.currentUserCameraFloatingLayout.getTag() == null || ((Integer) this.currentUserCameraFloatingLayout.getTag()).intValue() != 2) {
                VoIPFloatingLayout voIPFloatingLayout = this.currentUserCameraFloatingLayout;
                if (voIPFloatingLayout.relativePositionToSetX < 0.0f) {
                    voIPFloatingLayout.setRelativePosition(1.0f, 1.0f);
                    this.currentUserCameraIsFullscreen = true;
                }
            }
            this.currentUserCameraFloatingLayout.setFloatingMode(i == 2, z2);
            this.currentUserCameraIsFullscreen = i != 2;
        } else if (z) {
            if (this.currentUserCameraFloatingLayout.getTag() != null && ((Integer) this.currentUserCameraFloatingLayout.getTag()).intValue() != 0) {
                Animator animator3 = this.cameraShowingAnimator;
                if (animator3 != null) {
                    animator3.removeAllListeners();
                    this.cameraShowingAnimator.cancel();
                }
                AnimatorSet animatorSet2 = new AnimatorSet();
                VoIPFloatingLayout voIPFloatingLayout2 = this.currentUserCameraFloatingLayout;
                animatorSet2.playTogether(ObjectAnimator.ofFloat(voIPFloatingLayout2, (Property<VoIPFloatingLayout, Float>) View.ALPHA, voIPFloatingLayout2.getAlpha(), 0.0f));
                if (this.currentUserCameraFloatingLayout.getTag() != null && ((Integer) this.currentUserCameraFloatingLayout.getTag()).intValue() == 2) {
                    VoIPFloatingLayout voIPFloatingLayout3 = this.currentUserCameraFloatingLayout;
                    ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(voIPFloatingLayout3, (Property<VoIPFloatingLayout, Float>) View.SCALE_X, voIPFloatingLayout3.getScaleX(), 0.7f);
                    VoIPFloatingLayout voIPFloatingLayout4 = this.currentUserCameraFloatingLayout;
                    animatorSet2.playTogether(objectAnimatorOfFloat, ObjectAnimator.ofFloat(voIPFloatingLayout4, (Property<VoIPFloatingLayout, Float>) View.SCALE_Y, voIPFloatingLayout4.getScaleX(), 0.7f));
                }
                this.cameraShowingAnimator = animatorSet2;
                animatorSet2.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.VoIPFragment.20
                    C717120() {
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator4) {
                        VoIPFragment.this.currentUserCameraFloatingLayout.setTranslationX(0.0f);
                        VoIPFragment.this.currentUserCameraFloatingLayout.setTranslationY(0.0f);
                        VoIPFragment.this.currentUserCameraFloatingLayout.setScaleY(1.0f);
                        VoIPFragment.this.currentUserCameraFloatingLayout.setScaleX(1.0f);
                        VoIPFragment.this.currentUserCameraFloatingLayout.setVisibility(8);
                    }
                });
                this.cameraShowingAnimator.setDuration(250L).setInterpolator(CubicBezierInterpolator.DEFAULT);
                this.cameraShowingAnimator.setStartDelay(50L);
                this.cameraShowingAnimator.start();
            }
        } else {
            this.currentUserCameraFloatingLayout.setVisibility(8);
        }
        this.currentUserCameraFloatingLayout.setTag(Integer.valueOf(i));
    }

    /* JADX INFO: renamed from: org.telegram.ui.VoIPFragment$20 */
    /* JADX INFO: loaded from: classes6.dex */
    class C717120 extends AnimatorListenerAdapter {
        C717120() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator4) {
            VoIPFragment.this.currentUserCameraFloatingLayout.setTranslationX(0.0f);
            VoIPFragment.this.currentUserCameraFloatingLayout.setTranslationY(0.0f);
            VoIPFragment.this.currentUserCameraFloatingLayout.setScaleY(1.0f);
            VoIPFragment.this.currentUserCameraFloatingLayout.setScaleX(1.0f);
            VoIPFragment.this.currentUserCameraFloatingLayout.setVisibility(8);
        }
    }

    private void showCallingUserAvatarMini(boolean z, boolean z2) {
        boolean z3 = (this.currentUserIsVideo || this.callingUserIsVideo) ? false : true;
        if (z) {
            if (z3 && this.callingUserPhotoViewMini.getTag() == null) {
                this.callingUserPhotoViewMini.animate().setListener(null).cancel();
                this.callingUserPhotoViewMini.setVisibility(0);
                if (this.emojiExpanded) {
                    if (z2) {
                        this.callingUserPhotoViewMini.setAlpha(0.0f);
                        this.callingUserPhotoViewMini.setTranslationY(AndroidUtilities.m1081dp(48.0f));
                        this.callingUserPhotoViewMini.setScaleX(0.1f);
                        this.callingUserPhotoViewMini.setScaleY(0.1f);
                    }
                } else if (z2) {
                    this.callingUserPhotoViewMini.setAlpha(0.0f);
                    this.callingUserPhotoViewMini.animate().alpha(1.0f).translationY(0.0f).scaleY(1.0f).scaleX(1.0f).setDuration(150L).setInterpolator(CubicBezierInterpolator.DEFAULT).start();
                } else {
                    this.callingUserPhotoViewMini.setAlpha(0.0f);
                    this.callingUserPhotoViewMini.setTranslationY(-AndroidUtilities.m1081dp(135.0f));
                    this.callingUserPhotoViewMini.animate().alpha(1.0f).translationY(0.0f).scaleY(1.0f).scaleX(1.0f).setDuration(150L).setInterpolator(CubicBezierInterpolator.DEFAULT).start();
                }
            } else if (!z3 && this.callingUserPhotoViewMini.getTag() != null) {
                this.callingUserPhotoViewMini.animate().setListener(null).cancel();
                this.callingUserPhotoViewMini.setTranslationY(0.0f);
                this.callingUserPhotoViewMini.animate().alpha(0.0f).setDuration(150L).scaleX(0.1f).scaleY(0.1f).setInterpolator(CubicBezierInterpolator.DEFAULT).setListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.VoIPFragment.21
                    C717221() {
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        VoIPFragment.this.callingUserPhotoViewMini.setVisibility(8);
                    }
                }).start();
            }
        } else {
            this.callingUserPhotoViewMini.animate().setListener(null).cancel();
            this.callingUserPhotoViewMini.setTranslationY(0.0f);
            this.callingUserPhotoViewMini.setAlpha(1.0f);
            this.callingUserPhotoViewMini.setScaleX(1.0f);
            this.callingUserPhotoViewMini.setScaleY(1.0f);
            this.callingUserPhotoViewMini.setVisibility(z3 ? 0 : 8);
        }
        this.callingUserPhotoViewMini.setTag(z3 ? 1 : null);
    }

    /* JADX INFO: renamed from: org.telegram.ui.VoIPFragment$21 */
    /* JADX INFO: loaded from: classes6.dex */
    class C717221 extends AnimatorListenerAdapter {
        C717221() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            VoIPFragment.this.callingUserPhotoViewMini.setVisibility(8);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v3, types: [android.graphics.drawable.Drawable, org.telegram.messenger.Emoji$EmojiDrawable] */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r4v0, types: [android.graphics.drawable.Drawable[]] */
    /* JADX WARN: Type inference failed for: r4v10, types: [org.telegram.ui.Components.BackupImageView[]] */
    /* JADX WARN: Type inference failed for: r4v11, types: [org.telegram.ui.Components.BackupImageView] */
    private void updateKeyView(boolean z) {
        VoIPService sharedInstance;
        byte[] byteArray;
        AnimatedEmojiDrawable animatedEmojiDrawable;
        if (this.emojiLoaded || (sharedInstance = VoIPService.getSharedInstance()) == null) {
            return;
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(sharedInstance.getEncryptionKey());
            byteArrayOutputStream.write(sharedInstance.getGA());
            byteArray = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            FileLog.m1093e(e);
            byteArray = null;
        }
        if (byteArray == null) {
            return;
        }
        String[] strArrEmojifyForCall = EncryptionKeyEmojifier.emojifyForCall(Utilities.computeSHA256(byteArray, 0, byteArray.length));
        for (int i = 0; i < 4; i++) {
            Emoji.preloadEmoji(strArrEmojifyForCall[i]);
            ?? emojiDrawable = Emoji.getEmojiDrawable(strArrEmojifyForCall[i]);
            if (emojiDrawable != 0) {
                emojiDrawable.setBounds(0, 0, AndroidUtilities.m1081dp(40.0f), AndroidUtilities.m1081dp(40.0f));
                emojiDrawable.preload();
                int[] iArr = new int[1];
                TextPaint textPaint = new TextPaint(1);
                textPaint.setTextSize(AndroidUtilities.m1081dp(28.0f));
                TLRPC.Document documentReplaceEmojiToLottieFrame = replaceEmojiToLottieFrame(Emoji.replaceEmoji((CharSequence) strArrEmojifyForCall[i], textPaint.getFontMetricsInt(), false, iArr), iArr);
                if (documentReplaceEmojiToLottieFrame != null) {
                    Drawable drawable = this.emojiDrawables[i];
                    if ((drawable instanceof AnimatedEmojiDrawable) && ((AnimatedEmojiDrawable) drawable).getDocumentId() == documentReplaceEmojiToLottieFrame.f1618id) {
                        animatedEmojiDrawable = (AnimatedEmojiDrawable) this.emojiDrawables[i];
                    } else {
                        Drawable[] drawableArr = this.emojiDrawables;
                        AnimatedEmojiDrawable animatedEmojiDrawable2 = new AnimatedEmojiDrawable(21, this.currentAccount, documentReplaceEmojiToLottieFrame);
                        drawableArr[i] = animatedEmojiDrawable2;
                        animatedEmojiDrawable = animatedEmojiDrawable2;
                    }
                    animatedEmojiDrawable.setupEmojiThumb(strArrEmojifyForCall[i]);
                    this.emojiViews[i].setAnimatedEmojiDrawable(animatedEmojiDrawable);
                    this.emojiViews[i].getImageReceiver().clearImage();
                    emojiDrawable = animatedEmojiDrawable;
                } else {
                    this.emojiViews[i].setImageDrawable(emojiDrawable);
                    emojiDrawable = emojiDrawable;
                }
                this.emojiViews[i].setVisibility(8);
            }
            this.emojiDrawables[i] = emojiDrawable;
        }
        checkEmojiLoaded(z);
    }

    private boolean isLoaded(Drawable drawable) {
        ImageReceiver imageReceiver;
        if (drawable instanceof Emoji.EmojiDrawable) {
            return ((Emoji.EmojiDrawable) drawable).isLoaded();
        }
        if (!(drawable instanceof AnimatedEmojiDrawable) || (imageReceiver = ((AnimatedEmojiDrawable) drawable).getImageReceiver()) == null) {
            return false;
        }
        Drawable thumb = imageReceiver.getThumb();
        return !(thumb instanceof Emoji.EmojiDrawable) || ((Emoji.EmojiDrawable) thumb).isLoaded();
    }

    private void checkEmojiLoaded(boolean z) {
        int i = 0;
        for (int i2 = 0; i2 < 4; i2++) {
            if (isLoaded(this.emojiDrawables[i2])) {
                i++;
            }
        }
        if (i == 4) {
            this.emojiLoaded = true;
            for (int i3 = 0; i3 < 4; i3++) {
                if (this.emojiViews[i3].getVisibility() != 0) {
                    this.emojiViews[i3].setVisibility(0);
                    if (z) {
                        this.emojiViews[i3].setAlpha(0.0f);
                        this.emojiViews[i3].setScaleX(0.0f);
                        this.emojiViews[i3].setScaleY(0.0f);
                        this.emojiViews[i3].animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setInterpolator(CubicBezierInterpolator.EASE_OUT_BACK).setDuration(250L).start();
                    }
                }
            }
            this.encryptionTooltip.postDelayed(new Runnable() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$checkEmojiLoaded$34();
                }
            }, 1000L);
        }
    }

    public /* synthetic */ void lambda$checkEmojiLoaded$34() {
        if (SharedConfig.callEncryptionHintDisplayedCount < 2) {
            SharedConfig.incrementCallEncryptionHintDisplayed(1);
            this.encryptionTooltip.setTranslationY(this.emojiLayout.getY() + AndroidUtilities.m1081dp(36.0f));
            this.encryptionTooltip.show();
        }
    }

    private void showAcceptDeclineView(boolean z, boolean z2) {
        if (!z2) {
            this.acceptDeclineView.setVisibility(z ? 0 : 8);
        } else {
            if (z && this.acceptDeclineView.getTag() == null) {
                this.acceptDeclineView.animate().setListener(null).cancel();
                if (this.acceptDeclineView.getVisibility() == 8) {
                    this.acceptDeclineView.setVisibility(0);
                    this.acceptDeclineView.setAlpha(0.0f);
                }
                this.acceptDeclineView.animate().alpha(1.0f);
            }
            if (!z && this.acceptDeclineView.getTag() != null) {
                this.acceptDeclineView.animate().setListener(null).cancel();
                this.acceptDeclineView.animate().setListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.VoIPFragment.22
                    C717322() {
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        VoIPFragment.this.acceptDeclineView.setVisibility(8);
                    }
                }).alpha(0.0f);
            }
        }
        this.acceptDeclineView.setEnabled(z);
        this.acceptDeclineView.setTag(z ? 1 : null);
    }

    /* JADX INFO: renamed from: org.telegram.ui.VoIPFragment$22 */
    /* JADX INFO: loaded from: classes6.dex */
    class C717322 extends AnimatorListenerAdapter {
        C717322() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            VoIPFragment.this.acceptDeclineView.setVisibility(8);
        }
    }

    private void updateButtons(boolean z) {
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        if (sharedInstance == null) {
            return;
        }
        if (z) {
            TransitionSet transitionSet = new TransitionSet();
            Transition duration = new Visibility() { // from class: org.telegram.ui.VoIPFragment.23
                C717423() {
                }

                @Override // android.transition.Visibility
                public Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
                    ObjectAnimator objectAnimatorOfPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofFloat((Property<?, Float>) View.TRANSLATION_Y, AndroidUtilities.m1081dp(100.0f), 0.0f), PropertyValuesHolder.ofFloat((Property<?, Float>) View.SCALE_Y, 0.0f, 1.0f), PropertyValuesHolder.ofFloat((Property<?, Float>) View.SCALE_X, 0.0f, 1.0f));
                    if (view instanceof VoIPToggleButton) {
                        view.setTranslationY(AndroidUtilities.m1081dp(100.0f));
                        view.setScaleX(0.0f);
                        view.setScaleY(0.0f);
                        objectAnimatorOfPropertyValuesHolder.setStartDelay(((VoIPToggleButton) view).animationDelay);
                    }
                    if (view instanceof VoIpSwitchLayout) {
                        view.setTranslationY(AndroidUtilities.m1081dp(100.0f));
                        view.setScaleX(0.0f);
                        view.setScaleY(0.0f);
                        objectAnimatorOfPropertyValuesHolder.setStartDelay(((VoIpSwitchLayout) view).animationDelay);
                    }
                    return objectAnimatorOfPropertyValuesHolder;
                }

                @Override // android.transition.Visibility
                public Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
                    return ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofFloat((Property<?, Float>) View.TRANSLATION_Y, view.getTranslationY(), AndroidUtilities.m1081dp(100.0f)), PropertyValuesHolder.ofFloat((Property<?, Float>) View.SCALE_Y, view.getScaleY(), 0.0f), PropertyValuesHolder.ofFloat((Property<?, Float>) View.SCALE_X, view.getScaleX(), 0.0f));
                }
            }.setDuration(250L);
            CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.DEFAULT;
            transitionSet.addTransition(duration.setInterpolator(cubicBezierInterpolator)).addTransition(new ChangeBounds().setDuration(250L).setInterpolator(cubicBezierInterpolator));
            transitionSet.excludeChildren(VoIPToggleButton.class, true);
            transitionSet.excludeChildren(VoIpSwitchLayout.class, true);
            TransitionManager.beginDelayedTransition(this.buttonsLayout, transitionSet);
        }
        int i = this.currentState;
        if (i == 11) {
            this.bottomSpeakerBtn.setVisibility(8);
            this.bottomVideoBtn.setVisibility(8);
            this.bottomMuteBtn.setVisibility(8);
            this.bottomEndCallBtn.setVisibility(8);
            return;
        }
        int i2 = 0;
        if (i == 15 || i == 17) {
            TL_phone.PhoneCall phoneCall = sharedInstance.privateCall;
            if (phoneCall != null && phoneCall.video && i == 15) {
                if (!sharedInstance.isScreencast() && (this.currentUserIsVideo || this.callingUserIsVideo)) {
                    setFrontalCameraAction(this.bottomSpeakerBtn, sharedInstance, z);
                    if (this.uiVisible) {
                        this.speakerPhoneIcon.animate().alpha(1.0f).start();
                    }
                } else {
                    setSpeakerPhoneAction(this.bottomSpeakerBtn, sharedInstance, z);
                    this.speakerPhoneIcon.animate().alpha(0.0f).start();
                }
                setVideoAction(this.bottomVideoBtn, sharedInstance, false);
                setMicrohoneAction(this.bottomMuteBtn, sharedInstance, z);
            } else {
                this.bottomSpeakerBtn.setVisibility(8);
                this.bottomVideoBtn.setVisibility(8);
                this.bottomMuteBtn.setVisibility(8);
            }
            this.bottomEndCallBtn.setVisibility(8);
        } else {
            if (instance == null) {
                return;
            }
            if (!sharedInstance.isScreencast() && (this.currentUserIsVideo || this.callingUserIsVideo)) {
                setFrontalCameraAction(this.bottomSpeakerBtn, sharedInstance, z);
                if (this.uiVisible) {
                    this.speakerPhoneIcon.setTag(1);
                    this.speakerPhoneIcon.animate().alpha(1.0f).start();
                }
            } else {
                setSpeakerPhoneAction(this.bottomSpeakerBtn, sharedInstance, z);
                this.speakerPhoneIcon.setTag(null);
                this.speakerPhoneIcon.animate().alpha(0.0f).start();
            }
            setVideoAction(this.bottomVideoBtn, sharedInstance, false);
            setMicrohoneAction(this.bottomMuteBtn, sharedInstance, z);
            this.bottomEndCallBtn.setData(C2702R.drawable.calls_decline, -1, -1041108, LocaleController.getString(C2702R.string.VoipEndCall2), false, z);
            this.bottomEndCallBtn.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda20
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$updateButtons$35(view);
                }
            });
        }
        if (this.bottomSpeakerBtn.getVisibility() == 0) {
            this.bottomSpeakerBtn.animationDelay = 0;
            i2 = 16;
        }
        if (this.bottomVideoBtn.getVisibility() == 0) {
            this.bottomVideoBtn.animationDelay = i2;
            i2 += 16;
        }
        if (this.bottomMuteBtn.getVisibility() == 0) {
            this.bottomMuteBtn.animationDelay = i2;
            i2 += 16;
        }
        if (this.bottomEndCallBtn.getVisibility() == 0) {
            this.bottomEndCallBtn.animationDelay = i2;
        }
        updateSpeakerPhoneIcon();
    }

    /* JADX INFO: renamed from: org.telegram.ui.VoIPFragment$23 */
    /* JADX INFO: loaded from: classes6.dex */
    class C717423 extends Visibility {
        C717423() {
        }

        @Override // android.transition.Visibility
        public Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
            ObjectAnimator objectAnimatorOfPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofFloat((Property<?, Float>) View.TRANSLATION_Y, AndroidUtilities.m1081dp(100.0f), 0.0f), PropertyValuesHolder.ofFloat((Property<?, Float>) View.SCALE_Y, 0.0f, 1.0f), PropertyValuesHolder.ofFloat((Property<?, Float>) View.SCALE_X, 0.0f, 1.0f));
            if (view instanceof VoIPToggleButton) {
                view.setTranslationY(AndroidUtilities.m1081dp(100.0f));
                view.setScaleX(0.0f);
                view.setScaleY(0.0f);
                objectAnimatorOfPropertyValuesHolder.setStartDelay(((VoIPToggleButton) view).animationDelay);
            }
            if (view instanceof VoIpSwitchLayout) {
                view.setTranslationY(AndroidUtilities.m1081dp(100.0f));
                view.setScaleX(0.0f);
                view.setScaleY(0.0f);
                objectAnimatorOfPropertyValuesHolder.setStartDelay(((VoIpSwitchLayout) view).animationDelay);
            }
            return objectAnimatorOfPropertyValuesHolder;
        }

        @Override // android.transition.Visibility
        public Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
            return ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofFloat((Property<?, Float>) View.TRANSLATION_Y, view.getTranslationY(), AndroidUtilities.m1081dp(100.0f)), PropertyValuesHolder.ofFloat((Property<?, Float>) View.SCALE_Y, view.getScaleY(), 0.0f), PropertyValuesHolder.ofFloat((Property<?, Float>) View.SCALE_X, view.getScaleX(), 0.0f));
        }
    }

    public /* synthetic */ void lambda$updateButtons$35(View view) {
        if (VoIPService.getSharedInstance() != null) {
            AndroidUtilities.cancelRunOnUIThread(this.hideUIRunnable);
            this.hideUiRunnableWaiting = false;
            VoIPService.getSharedInstance().hangUp();
        }
    }

    private void setMicrohoneAction(VoIpSwitchLayout voIpSwitchLayout, VoIPService voIPService, boolean z) {
        voIpSwitchLayout.setType(VoIpSwitchLayout.Type.MICRO, voIPService.isMicMute());
        this.currentUserCameraFloatingLayout.setMuted(voIPService.isMicMute(), z);
        voIpSwitchLayout.setOnBtnClickedListener(new VoIpSwitchLayout.VoIpButtonView.OnBtnClickedListener() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda38
            @Override // org.telegram.ui.Components.voip.VoIpSwitchLayout.VoIpButtonView.OnBtnClickedListener
            public final void onClicked(View view) {
                this.f$0.lambda$setMicrohoneAction$36(view);
            }
        });
    }

    public /* synthetic */ void lambda$setMicrohoneAction$36(View view) {
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        if (sharedInstance != null) {
            AndroidUtilities.cancelRunOnUIThread(this.hideUIRunnable);
            this.hideUiRunnableWaiting = false;
            boolean zIsMicMute = sharedInstance.isMicMute();
            boolean z = !zIsMicMute;
            if (this.accessibilityManager.isTouchExplorationEnabled()) {
                view.announceForAccessibility(LocaleController.getString(!zIsMicMute ? C2702R.string.AccDescrVoipMicOff : C2702R.string.AccDescrVoipMicOn));
            }
            sharedInstance.setMicMute(z, false, true);
            this.previousState = this.currentState;
            updateViewState();
        }
    }

    public void setVideoAction(VoIpSwitchLayout voIpSwitchLayout, VoIPService voIPService, boolean z) {
        if ((this.currentUserIsVideo || this.callingUserIsVideo) ? true : voIPService.isVideoAvailable()) {
            if (this.currentUserIsVideo) {
                if (voIPService.isScreencast()) {
                    voIpSwitchLayout.setType(VoIpSwitchLayout.Type.VIDEO, false, z);
                } else {
                    voIpSwitchLayout.setType(VoIpSwitchLayout.Type.VIDEO, false, z);
                }
            } else {
                voIpSwitchLayout.setType(VoIpSwitchLayout.Type.VIDEO, true, z);
            }
            voIpSwitchLayout.setOnBtnClickedListener(new VoIpSwitchLayout.VoIpButtonView.OnBtnClickedListener() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda43
                @Override // org.telegram.ui.Components.voip.VoIpSwitchLayout.VoIpButtonView.OnBtnClickedListener
                public final void onClicked(View view) {
                    this.f$0.lambda$setVideoAction$37(view);
                }
            });
            voIpSwitchLayout.setEnabled(true);
            return;
        }
        voIpSwitchLayout.setType(VoIpSwitchLayout.Type.VIDEO, true);
        voIpSwitchLayout.setOnClickListener(null);
        voIpSwitchLayout.setEnabled(false);
    }

    public /* synthetic */ void lambda$setVideoAction$37(View view) {
        AndroidUtilities.cancelRunOnUIThread(this.hideUIRunnable);
        this.hideUiRunnableWaiting = false;
        if (this.activity.checkSelfPermission("android.permission.CAMERA") != 0) {
            this.activity.requestPermissions(new String[]{"android.permission.CAMERA"}, 102);
        } else {
            toggleCameraInput();
        }
    }

    private void updateSpeakerPhoneIcon() {
        int i;
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        if (sharedInstance == null) {
            return;
        }
        if (sharedInstance.isBluetoothOn()) {
            i = C2702R.drawable.calls_bluetooth;
        } else if (VoipAudioManager.get().isSpeakerphoneOn()) {
            i = C2702R.drawable.calls_speaker;
        } else if (sharedInstance.isHeadsetPlugged()) {
            i = C2702R.drawable.calls_menu_headset;
        } else {
            i = C2702R.drawable.calls_menu_phone;
        }
        if (this.speakerPhoneIconResId != i) {
            AndroidUtilities.updateImageViewImageAnimated(this.speakerPhoneIcon, i);
        } else {
            this.speakerPhoneIcon.setImageResource(i);
        }
        this.speakerPhoneIconResId = i;
    }

    private void setSpeakerPhoneAction(final VoIpSwitchLayout voIpSwitchLayout, final VoIPService voIPService, boolean z) {
        VoipAudioManager voipAudioManager = VoipAudioManager.get();
        final int i = 0;
        if (voIPService.isBluetoothOn()) {
            voIpSwitchLayout.setType(VoIpSwitchLayout.Type.BLUETOOTH, false);
            i = 2;
        } else if (voipAudioManager.isSpeakerphoneOn()) {
            voIpSwitchLayout.setType(VoIpSwitchLayout.Type.SPEAKER, true);
        } else {
            voIpSwitchLayout.setType(VoIpSwitchLayout.Type.SPEAKER, false);
            i = 1;
        }
        voIpSwitchLayout.setEnabled(true);
        voIpSwitchLayout.setOnBtnClickedListener(new VoIpSwitchLayout.VoIpButtonView.OnBtnClickedListener() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda34
            @Override // org.telegram.ui.Components.voip.VoIpSwitchLayout.VoIpButtonView.OnBtnClickedListener
            public final void onClicked(View view) {
                this.f$0.lambda$setSpeakerPhoneAction$38(i, voIpSwitchLayout, voIPService, view);
            }
        });
    }

    public /* synthetic */ void lambda$setSpeakerPhoneAction$38(int i, VoIpSwitchLayout voIpSwitchLayout, VoIPService voIPService, View view) {
        if (VoIPService.getSharedInstance() != null) {
            AndroidUtilities.cancelRunOnUIThread(this.hideUIRunnable);
            this.hideUiRunnableWaiting = false;
            VoIPService.getSharedInstance().toggleSpeakerphoneOrShowRouteSheet(this.activity, false, Integer.valueOf(i));
            setSpeakerPhoneAction(voIpSwitchLayout, voIPService, true);
        }
    }

    private void setFrontalCameraAction(final VoIpSwitchLayout voIpSwitchLayout, final VoIPService voIPService, boolean z) {
        if (!this.currentUserIsVideo) {
            voIpSwitchLayout.setType(VoIpSwitchLayout.Type.CAMERA, false);
            voIpSwitchLayout.setOnBtnClickedListener(null);
            voIpSwitchLayout.setEnabled(false);
        } else {
            voIpSwitchLayout.setEnabled(true);
            if (voIPService.isFrontFaceCamera()) {
                voIpSwitchLayout.setType(VoIpSwitchLayout.Type.CAMERA, true ^ voIPService.isSwitchingCamera());
            } else {
                voIpSwitchLayout.setType(VoIpSwitchLayout.Type.CAMERA, voIPService.isSwitchingCamera());
            }
            voIpSwitchLayout.setOnBtnClickedListener(new VoIpSwitchLayout.VoIpButtonView.OnBtnClickedListener() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda42
                @Override // org.telegram.ui.Components.voip.VoIpSwitchLayout.VoIpButtonView.OnBtnClickedListener
                public final void onClicked(View view) {
                    this.f$0.lambda$setFrontalCameraAction$39(voIPService, voIpSwitchLayout, view);
                }
            });
        }
    }

    public /* synthetic */ void lambda$setFrontalCameraAction$39(VoIPService voIPService, VoIpSwitchLayout voIpSwitchLayout, View view) {
        String string;
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        if (sharedInstance != null) {
            AndroidUtilities.cancelRunOnUIThread(this.hideUIRunnable);
            this.hideUiRunnableWaiting = false;
            if (this.accessibilityManager.isTouchExplorationEnabled()) {
                if (voIPService.isFrontFaceCamera()) {
                    string = LocaleController.getString(C2702R.string.AccDescrVoipCamSwitchedToBack);
                } else {
                    string = LocaleController.getString(C2702R.string.AccDescrVoipCamSwitchedToFront);
                }
                view.announceForAccessibility(string);
            }
            voIpSwitchLayout.setType(VoIpSwitchLayout.Type.CAMERA, !voIPService.isFrontFaceCamera());
            sharedInstance.switchCamera();
        }
    }

    public void onScreenCastStart() {
        PrivateVideoPreviewDialogNew privateVideoPreviewDialogNew = this.previewDialog;
        if (privateVideoPreviewDialogNew == null) {
            return;
        }
        privateVideoPreviewDialogNew.dismiss(true, true);
    }

    private void toggleCameraInput() {
        String string;
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        if (sharedInstance != null) {
            if (this.accessibilityManager.isTouchExplorationEnabled()) {
                if (!this.currentUserIsVideo) {
                    string = LocaleController.getString(C2702R.string.AccDescrVoipCamOn);
                } else {
                    string = LocaleController.getString(C2702R.string.AccDescrVoipCamOff);
                }
                this.fragmentView.announceForAccessibility(string);
            }
            if (!this.currentUserIsVideo) {
                if (this.previewDialog == null) {
                    sharedInstance.createCaptureDevice(false);
                    if (!sharedInstance.isFrontFaceCamera()) {
                        sharedInstance.switchCamera();
                    }
                    this.windowView.setLockOnScreen(true);
                    this.bottomVideoBtn.getLocationOnScreen(new int[2]);
                    C717524 c717524 = new PrivateVideoPreviewDialogNew(this.fragmentView.getContext(), r0[0], r0[1]) { // from class: org.telegram.ui.VoIPFragment.24
                        C717524(Context context, float f, float f2) {
                            super(context, f, f2);
                        }

                        @Override // org.telegram.p026ui.Components.voip.PrivateVideoPreviewDialogNew
                        public void onDismiss(boolean z, boolean z2) {
                            VoIPFragment.this.previewDialog = null;
                            VoIPService sharedInstance2 = VoIPService.getSharedInstance();
                            VoIPFragment.this.windowView.setLockOnScreen(false);
                            if (z2) {
                                VoIPFragment.this.currentUserIsVideo = true;
                                if (sharedInstance2 != null && !z) {
                                    sharedInstance2.requestVideoCall(false);
                                    sharedInstance2.setVideoState(false, 2);
                                    sharedInstance2.switchToSpeaker();
                                }
                                if (sharedInstance2 != null) {
                                    VoIPFragment voIPFragment = VoIPFragment.this;
                                    voIPFragment.setVideoAction(voIPFragment.bottomVideoBtn, sharedInstance2, true);
                                }
                            } else if (sharedInstance2 != null) {
                                sharedInstance2.setVideoState(false, 0);
                            }
                            VoIPFragment voIPFragment2 = VoIPFragment.this;
                            voIPFragment2.previousState = voIPFragment2.currentState;
                            VoIPFragment.this.updateViewState();
                        }

                        @Override // org.telegram.p026ui.Components.voip.PrivateVideoPreviewDialogNew
                        protected void afterOpened() {
                            VoIPFragment.this.gradientLayout.lockDrawing = true;
                            VoIPFragment.this.gradientLayout.invalidate();
                        }

                        @Override // org.telegram.p026ui.Components.voip.PrivateVideoPreviewDialogNew
                        protected void beforeClosed() {
                            VoIPFragment.this.gradientLayout.lockDrawing = false;
                            VoIPFragment.this.gradientLayout.invalidate();
                        }

                        @Override // org.telegram.p026ui.Components.voip.PrivateVideoPreviewDialogNew
                        protected int[] getFloatingViewLocation() {
                            int[] iArr = new int[2];
                            VoIPFragment.this.currentUserCameraFloatingLayout.getLocationOnScreen(iArr);
                            return new int[]{iArr[0], iArr[1], VoIPFragment.this.currentUserCameraFloatingLayout.getMeasuredWidth()};
                        }

                        @Override // org.telegram.p026ui.Components.voip.PrivateVideoPreviewDialogNew
                        protected boolean isHasVideoOnMainScreen() {
                            return VoIPFragment.this.callingUserIsVideo;
                        }
                    };
                    this.previewDialog = c717524;
                    WindowInsets windowInsets = this.lastInsets;
                    if (windowInsets != null) {
                        c717524.setBottomPadding(windowInsets.getSystemWindowInsetBottom());
                    }
                    this.fragmentView.addView(this.previewDialog);
                    return;
                }
                return;
            }
            this.currentUserTextureView.saveCameraLastBitmap();
            sharedInstance.setVideoState(false, 0);
            sharedInstance.clearCamera();
            this.previousState = this.currentState;
            updateViewState();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.VoIPFragment$24 */
    /* JADX INFO: loaded from: classes6.dex */
    class C717524 extends PrivateVideoPreviewDialogNew {
        C717524(Context context, float f, float f2) {
            super(context, f, f2);
        }

        @Override // org.telegram.p026ui.Components.voip.PrivateVideoPreviewDialogNew
        public void onDismiss(boolean z, boolean z2) {
            VoIPFragment.this.previewDialog = null;
            VoIPService sharedInstance2 = VoIPService.getSharedInstance();
            VoIPFragment.this.windowView.setLockOnScreen(false);
            if (z2) {
                VoIPFragment.this.currentUserIsVideo = true;
                if (sharedInstance2 != null && !z) {
                    sharedInstance2.requestVideoCall(false);
                    sharedInstance2.setVideoState(false, 2);
                    sharedInstance2.switchToSpeaker();
                }
                if (sharedInstance2 != null) {
                    VoIPFragment voIPFragment = VoIPFragment.this;
                    voIPFragment.setVideoAction(voIPFragment.bottomVideoBtn, sharedInstance2, true);
                }
            } else if (sharedInstance2 != null) {
                sharedInstance2.setVideoState(false, 0);
            }
            VoIPFragment voIPFragment2 = VoIPFragment.this;
            voIPFragment2.previousState = voIPFragment2.currentState;
            VoIPFragment.this.updateViewState();
        }

        @Override // org.telegram.p026ui.Components.voip.PrivateVideoPreviewDialogNew
        protected void afterOpened() {
            VoIPFragment.this.gradientLayout.lockDrawing = true;
            VoIPFragment.this.gradientLayout.invalidate();
        }

        @Override // org.telegram.p026ui.Components.voip.PrivateVideoPreviewDialogNew
        protected void beforeClosed() {
            VoIPFragment.this.gradientLayout.lockDrawing = false;
            VoIPFragment.this.gradientLayout.invalidate();
        }

        @Override // org.telegram.p026ui.Components.voip.PrivateVideoPreviewDialogNew
        protected int[] getFloatingViewLocation() {
            int[] iArr = new int[2];
            VoIPFragment.this.currentUserCameraFloatingLayout.getLocationOnScreen(iArr);
            return new int[]{iArr[0], iArr[1], VoIPFragment.this.currentUserCameraFloatingLayout.getMeasuredWidth()};
        }

        @Override // org.telegram.p026ui.Components.voip.PrivateVideoPreviewDialogNew
        protected boolean isHasVideoOnMainScreen() {
            return VoIPFragment.this.callingUserIsVideo;
        }
    }

    public static void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        VoIPFragment voIPFragment = instance;
        if (voIPFragment != null) {
            voIPFragment.onRequestPermissionsResultInternal(i, strArr, iArr);
        }
    }

    private void onRequestPermissionsResultInternal(int i, String[] strArr, int[] iArr) {
        if (i == 101) {
            if (VoIPService.getSharedState() == null) {
                this.windowView.finish();
                return;
            }
            if (iArr.length > 0 && iArr[0] == 0) {
                runAcceptCallAnimation(new Runnable() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        VoIPFragment.$r8$lambda$cA8tJ9pdLH_WrR0PUitJR8pnAKI();
                    }
                });
            } else if (!this.activity.shouldShowRequestPermissionRationale("android.permission.RECORD_AUDIO")) {
                if (VoIPService.getSharedState() != null) {
                    VoIPService.getSharedState().declineIncomingCall();
                }
                VoIPHelper.permissionDenied(this.activity, new Runnable() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onRequestPermissionsResultInternal$41();
                    }
                }, i);
                return;
            }
        }
        if (i == 102) {
            if (VoIPService.getSharedState() == null) {
                this.windowView.finish();
            } else {
                if (iArr.length <= 0 || iArr[0] != 0) {
                    return;
                }
                toggleCameraInput();
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$cA8tJ9pdLH_WrR0PUitJR8pnAKI() {
        if (VoIPService.getSharedState() != null) {
            VoIPService.getSharedState().acceptIncomingCall();
        }
    }

    public /* synthetic */ void lambda$onRequestPermissionsResultInternal$41() {
        this.windowView.finish();
    }

    private void updateSystemBarColors() {
        this.overlayPaint.setColor(ColorUtils.setAlphaComponent(-16777216, (int) (this.uiVisibilityAlpha * 102.0f * this.enterTransitionProgress)));
        this.overlayBottomPaint.setColor(ColorUtils.setAlphaComponent(-16777216, (int) (((this.fillNaviagtionBarValue * 0.5f) + 0.5f) * 255.0f * this.enterTransitionProgress)));
        ViewGroup viewGroup = this.fragmentView;
        if (viewGroup != null) {
            viewGroup.invalidate();
        }
    }

    public static void onPause() {
        VoIPFragment voIPFragment = instance;
        if (voIPFragment != null) {
            voIPFragment.onPauseInternal();
        }
        if (VoIPPiPView.getInstance() != null) {
            VoIPPiPView.getInstance().onPause();
        }
    }

    public static void onResume() {
        VoIPFragment voIPFragment = instance;
        if (voIPFragment != null) {
            voIPFragment.onResumeInternal();
        }
        if (VoIPPiPView.getInstance() != null) {
            VoIPPiPView.getInstance().onResume();
        }
    }

    public void onPauseInternal() {
        VoIPService sharedInstance;
        boolean zIsInteractive = ((PowerManager) this.activity.getSystemService("power")).isInteractive();
        int iCheckPermissions = PipUtils.checkPermissions(this.activity);
        boolean z = iCheckPermissions > 0;
        if (this.canSwitchToPip && VoIPService.getSharedInstance() != null && !VoIPService.getSharedInstance().isConverting() && iCheckPermissions == 2) {
            int measuredHeight = instance.windowView.getMeasuredHeight();
            VoIPFragment voIPFragment = instance;
            VoIPPiPView.show(voIPFragment.activity, voIPFragment.currentAccount, voIPFragment.windowView.getMeasuredWidth(), measuredHeight, 0);
            WindowInsets windowInsets = instance.lastInsets;
            if (windowInsets != null) {
                VoIPPiPView.topInset = windowInsets.getSystemWindowInsetTop();
                VoIPPiPView.bottomInset = instance.lastInsets.getSystemWindowInsetBottom();
            }
        }
        if (this.currentUserIsVideo) {
            if ((z && zIsInteractive) || (sharedInstance = VoIPService.getSharedInstance()) == null) {
                return;
            }
            sharedInstance.setVideoState(false, 1);
        }
    }

    public void onResumeInternal() {
        if (VoIPPiPView.getInstance() != null) {
            VoIPPiPView.finish();
        }
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        if (sharedInstance != null) {
            if (sharedInstance.getVideoState(false) == 1) {
                sharedInstance.setVideoState(false, 2);
            }
            updateViewState();
        } else if (VoIPService.getSharedState() == null) {
            this.windowView.finish();
        }
        this.deviceIsLocked = ((KeyguardManager) this.activity.getSystemService("keyguard")).inKeyguardRestrictedInputMode();
    }

    private void showErrorDialog(CharSequence charSequence) {
        if (this.activity.isFinishing()) {
            return;
        }
        AlertDialog alertDialogShow = new DarkAlertDialog.Builder(this.activity).setTitle(LocaleController.getString(C2702R.string.VoipFailed)).setMessage(charSequence).setPositiveButton(LocaleController.getString(C2702R.string.f1556OK), null).show();
        alertDialogShow.setCanceledOnTouchOutside(true);
        alertDialogShow.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda39
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f$0.lambda$showErrorDialog$42(dialogInterface);
            }
        });
    }

    public /* synthetic */ void lambda$showErrorDialog$42(DialogInterface dialogInterface) {
        this.windowView.finish();
    }

    private void requestInlinePermissions() {
        AlertsCreator.createDrawOverlayPermissionDialog(this.activity, new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda37
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                this.f$0.lambda$requestInlinePermissions$43(alertDialog, i);
            }
        }, true).show();
    }

    public /* synthetic */ void lambda$requestInlinePermissions$43(AlertDialog alertDialog, int i) {
        VoIPWindowView voIPWindowView = this.windowView;
        if (voIPWindowView != null) {
            voIPWindowView.finish();
        }
    }

    public TLRPC.Document replaceEmojiToLottieFrame(CharSequence charSequence, int[] iArr) {
        TLRPC.Document document;
        if (!(charSequence instanceof Spannable)) {
            return null;
        }
        Spannable spannable = (Spannable) charSequence;
        Emoji.EmojiSpan[] emojiSpanArr = (Emoji.EmojiSpan[]) spannable.getSpans(0, spannable.length(), Emoji.EmojiSpan.class);
        AnimatedEmojiSpan[] animatedEmojiSpanArr = (AnimatedEmojiSpan[]) spannable.getSpans(0, spannable.length(), AnimatedEmojiSpan.class);
        if (emojiSpanArr != null) {
            if (((iArr == null ? 0 : iArr[0]) - emojiSpanArr.length) - (animatedEmojiSpanArr == null ? 0 : animatedEmojiSpanArr.length) <= 0) {
                for (Emoji.EmojiSpan emojiSpan : emojiSpanArr) {
                    TLRPC.TL_inputStickerSetShortName tL_inputStickerSetShortName = new TLRPC.TL_inputStickerSetShortName();
                    tL_inputStickerSetShortName.short_name = "StaticEmoji";
                    TLRPC.TL_messages_stickerSet stickerSet = MediaDataController.getInstance(this.currentAccount).getStickerSet(tL_inputStickerSetShortName, 0, false, true, new Utilities.Callback() { // from class: org.telegram.ui.VoIPFragment$$ExternalSyntheticLambda8
                        @Override // org.telegram.messenger.Utilities.Callback
                        public final void run(Object obj) {
                            this.f$0.lambda$replaceEmojiToLottieFrame$44((TLRPC.TL_messages_stickerSet) obj);
                        }
                    });
                    if (stickerSet == null) {
                        return null;
                    }
                    String strReplace = emojiSpan.emoji.replace("️", _UrlKt.FRAGMENT_ENCODE_SET);
                    ArrayList arrayList = stickerSet.documents;
                    int size = arrayList.size();
                    int i = 0;
                    while (true) {
                        if (i >= size) {
                            document = null;
                            break;
                        }
                        Object obj = arrayList.get(i);
                        i++;
                        document = (TLRPC.Document) obj;
                        if (TextUtils.equals(MessageObject.findAnimatedEmojiEmoticon(document, null).replace("️", _UrlKt.FRAGMENT_ENCODE_SET), strReplace)) {
                            break;
                        }
                    }
                    if (document != null) {
                        return document;
                    }
                }
            }
        }
        return null;
    }

    public /* synthetic */ void lambda$replaceEmojiToLottieFrame$44(TLRPC.TL_messages_stickerSet tL_messages_stickerSet) {
        updateKeyView(true);
    }

    @Override // org.telegram.messenger.pip.source.IPipSourceDelegate
    public Bitmap pipCreatePrimaryWindowViewBitmap() {
        VoIPTextureView voIPTextureView = this.callingUserTextureView;
        if (voIPTextureView == null || !voIPTextureView.renderer.isAvailable()) {
            return null;
        }
        return this.callingUserTextureView.renderer.getBitmap();
    }

    @Override // org.telegram.messenger.pip.source.IPipSourceDelegate
    public void pipRenderBackground(Canvas canvas) {
        canvas.drawColor(-14999773);
    }

    @Override // org.telegram.messenger.pip.source.IPipSourceDelegate
    public View pipCreatePictureInPictureView() {
        VoIPTextureView voIPTextureView = new VoIPTextureView(this.activity, false, true, false, false);
        this.pipTextureView = voIPTextureView;
        voIPTextureView.renderer.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FIT);
        this.pipTextureView.renderer.setEnableHardwareScaler(true);
        this.pipTextureView.renderer.setRotateTextureWithScreen(true);
        VoIPTextureView voIPTextureView2 = this.pipTextureView;
        voIPTextureView2.scaleType = VoIPTextureView.SCALE_TYPE_FIT;
        voIPTextureView2.renderer.init(VideoCapturerDevice.getEglBase().getEglBaseContext(), new RendererCommon.RendererEvents() { // from class: org.telegram.ui.VoIPFragment.25
            @Override // org.webrtc.RendererCommon.RendererEvents
            public void onFrameResolutionChanged(int i, int i2, int i3) {
            }

            C717625() {
            }

            @Override // org.webrtc.RendererCommon.RendererEvents
            public void onFirstFrameRendered() {
                if (VoIPFragment.this.firstFrameCallback != null) {
                    VoIPFragment.this.firstFrameCallback.run();
                    VoIPFragment.this.firstFrameCallback = null;
                }
            }
        });
        View view = this.pipTextureView.backgroundView;
        if (view != null) {
            view.setVisibility(8);
        }
        return this.pipTextureView;
    }

    /* JADX INFO: renamed from: org.telegram.ui.VoIPFragment$25 */
    /* JADX INFO: loaded from: classes6.dex */
    class C717625 implements RendererCommon.RendererEvents {
        @Override // org.webrtc.RendererCommon.RendererEvents
        public void onFrameResolutionChanged(int i, int i2, int i3) {
        }

        C717625() {
        }

        @Override // org.webrtc.RendererCommon.RendererEvents
        public void onFirstFrameRendered() {
            if (VoIPFragment.this.firstFrameCallback != null) {
                VoIPFragment.this.firstFrameCallback.run();
                VoIPFragment.this.firstFrameCallback = null;
            }
        }
    }

    @Override // org.telegram.messenger.pip.source.IPipSourceDelegate
    public void pipHidePrimaryWindowView(Runnable runnable) {
        this.firstFrameCallback = runnable;
        VoIPTextureView voIPTextureView = this.callingUserTextureView;
        if (voIPTextureView != null) {
            voIPTextureView.renderer.clearFirstFrame();
        }
        this.windowViewSkipRender = true;
        updateViewState();
        ((WindowManager) this.activity.getSystemService("window")).removeView(this.windowView);
        this.windowView.invalidate();
    }

    @Override // org.telegram.messenger.pip.source.IPipSourceDelegate
    public Bitmap pipCreatePictureInPictureViewBitmap() {
        VoIPTextureView voIPTextureView = this.pipTextureView;
        if (voIPTextureView == null || !voIPTextureView.renderer.isAvailable()) {
            return null;
        }
        return this.pipTextureView.renderer.getBitmap();
    }

    @Override // org.telegram.messenger.pip.source.IPipSourceDelegate
    public void pipShowPrimaryWindowView(Runnable runnable) {
        this.firstFrameCallback = runnable;
        WindowManager windowManager = (WindowManager) this.activity.getSystemService("window");
        VoIPWindowView voIPWindowView = this.windowView;
        windowManager.addView(voIPWindowView, voIPWindowView.createWindowLayoutParams());
        this.windowViewSkipRender = false;
        updateViewState();
        this.windowView.invalidate();
        VoIPTextureView voIPTextureView = this.pipTextureView;
        if (voIPTextureView != null) {
            voIPTextureView.renderer.release();
            this.pipTextureView = null;
        }
    }

    /* JADX INFO: loaded from: classes6.dex */
    private static class ConferenceParticipantsView extends View {
        private final AvatarsDrawable avatarsDrawable;
        private final Paint backgroundPaint;
        private Text text;

        public ConferenceParticipantsView(Context context) {
            super(context);
            Paint paint = new Paint(1);
            this.backgroundPaint = paint;
            paint.setColor(-14538189);
            AvatarsDrawable avatarsDrawable = new AvatarsDrawable(this, false);
            this.avatarsDrawable = avatarsDrawable;
            avatarsDrawable.width = AndroidUtilities.m1081dp(100.0f);
            avatarsDrawable.height = AndroidUtilities.m1081dp(30.0f);
            avatarsDrawable.drawStoriesCircle = false;
            avatarsDrawable.setSize(AndroidUtilities.m1081dp(24.0f));
            avatarsDrawable.setAvatarsTextSize(AndroidUtilities.m1081dp(18.0f));
            avatarsDrawable.setStepFactor(0.58f);
        }

        public void set(int i, long j, ArrayList arrayList, int i2) {
            if (arrayList == null && i2 <= 0) {
                setVisibility(8);
                return;
            }
            int iMax = Math.max(i2, arrayList == null ? 0 : arrayList.size());
            int iMin = Math.min(3, arrayList == null ? 0 : arrayList.size());
            this.avatarsDrawable.setCount(iMin);
            for (int i3 = 0; i3 < iMin; i3++) {
                this.avatarsDrawable.setObject(i3, i, MessagesController.getInstance(i).getUserOrChat(DialogObject.getPeerDialogId(((TLRPC.GroupCallParticipant) arrayList.get(i3)).peer)));
            }
            this.avatarsDrawable.commitTransition(false);
            if (iMax == 1 && (arrayList == null || arrayList.size() == 0 || (arrayList.size() == 1 && DialogObject.getPeerDialogId(((TLRPC.GroupCallParticipant) arrayList.get(0)).peer) == j))) {
                setVisibility(8);
                return;
            }
            this.text = new Text(LocaleController.formatPluralStringComma("Participants", iMax), 14.0f, AndroidUtilities.bold());
            setVisibility(0);
            invalidate();
        }

        @Override // android.view.View
        protected void onMeasure(int i, int i2) {
            setMeasuredDimension(View.MeasureSpec.getSize(i), AndroidUtilities.m1081dp(30.0f));
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            if (this.text == null) {
                return;
            }
            float fM1081dp = AndroidUtilities.m1081dp(4.0f) + this.avatarsDrawable.getUsedWidth() + AndroidUtilities.m1081dp(7.0f) + this.text.getCurrentWidth() + AndroidUtilities.m1081dp(13.0f);
            float fM1081dp2 = AndroidUtilities.m1081dp(30.0f);
            RectF rectF = AndroidUtilities.rectTmp;
            rectF.set((getWidth() - fM1081dp) / 2.0f, 0.0f, (getWidth() + fM1081dp) / 2.0f, getHeight());
            float f = fM1081dp2 / 2.0f;
            canvas.drawRoundRect(rectF, f, f, this.backgroundPaint);
            canvas.save();
            canvas.translate(rectF.left + AndroidUtilities.m1081dp(4.0f), 0.0f);
            this.avatarsDrawable.onDraw(canvas);
            canvas.translate(this.avatarsDrawable.getMaxX() + AndroidUtilities.m1081dp(7.0f), 0.0f);
            this.text.draw(canvas, 0.0f, f, -1, 1.0f);
            canvas.restore();
        }
    }
}
