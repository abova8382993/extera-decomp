package org.telegram.p026ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.RenderNode;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Property;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.collection.LongSparseArray;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.icons.IconManager;
import com.google.android.material.timepicker.TimeModel;
import de.robv.android.xposed.callbacks.XCallback;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import me.vkryl.android.animator.BoolAnimator;
import me.vkryl.android.animator.FactorAnimator;
import okhttp3.internal.url._UrlKt;
import org.mvel2.DataTypes;
import org.mvel2.asm.Opcodes;
import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.BotWebViewVibrationEffect;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.C2702R;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ChatThemeController$$ExternalSyntheticLambda8;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.DispatchQueue;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.EmojiData;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LiteMode;
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
import org.telegram.messenger.pip.utils.PipUtils;
import org.telegram.messenger.support.LongSparseIntArray;
import org.telegram.messenger.voip.ConferenceCall;
import org.telegram.messenger.voip.GroupCallMessage;
import org.telegram.messenger.voip.GroupCallMessagesController;
import org.telegram.messenger.voip.NativeInstance;
import org.telegram.messenger.voip.VoIPService;
import org.telegram.messenger.voip.VoipAudioManager;
import org.telegram.p026ui.ActionBar.ActionBar;
import org.telegram.p026ui.ActionBar.ActionBarMenuItem;
import org.telegram.p026ui.ActionBar.ActionBarMenuSubItem;
import org.telegram.p026ui.ActionBar.ActionBarPopupWindow;
import org.telegram.p026ui.ActionBar.AlertDialog;
import org.telegram.p026ui.ActionBar.BaseFragment;
import org.telegram.p026ui.ActionBar.BottomSheet;
import org.telegram.p026ui.ActionBar.SimpleTextView;
import org.telegram.p026ui.ActionBar.Theme;
import org.telegram.p026ui.Cells.AccountSelectCell;
import org.telegram.p026ui.Cells.CheckBoxCell;
import org.telegram.p026ui.Cells.GroupCallInvitedCell;
import org.telegram.p026ui.Cells.GroupCallTextCell;
import org.telegram.p026ui.Cells.GroupCallUserCell;
import org.telegram.p026ui.Components.AlertsCreator;
import org.telegram.p026ui.Components.AnimatedEmojiDrawable;
import org.telegram.p026ui.Components.AnimatedEmojiSpan;
import org.telegram.p026ui.Components.AnimatedFloat;
import org.telegram.p026ui.Components.AnimatedTextView;
import org.telegram.p026ui.Components.AnimationProperties;
import org.telegram.p026ui.Components.AudioPlayerAlert;
import org.telegram.p026ui.Components.AvatarDrawable;
import org.telegram.p026ui.Components.BackupImageView;
import org.telegram.p026ui.Components.BlobDrawable;
import org.telegram.p026ui.Components.Bulletin;
import org.telegram.p026ui.Components.BulletinFactory;
import org.telegram.p026ui.Components.CheckBoxSquare;
import org.telegram.p026ui.Components.CubicBezierInterpolator;
import org.telegram.p026ui.Components.EditTextBoldCursor;
import org.telegram.p026ui.Components.EditTextEmoji;
import org.telegram.p026ui.Components.EmojiView;
import org.telegram.p026ui.Components.FillLastGridLayoutManager;
import org.telegram.p026ui.Components.GroupCallFullscreenAdapter;
import org.telegram.p026ui.Components.GroupCallPip;
import org.telegram.p026ui.Components.GroupCallRecordAlert;
import org.telegram.p026ui.Components.GroupVoipInviteAlert;
import org.telegram.p026ui.Components.HintView;
import org.telegram.p026ui.Components.ImageUpdater;
import org.telegram.p026ui.Components.ItemOptions;
import org.telegram.p026ui.Components.JoinCallAlert;
import org.telegram.p026ui.Components.LayoutHelper;
import org.telegram.p026ui.Components.NumberPicker;
import org.telegram.p026ui.Components.Premium.LimitReachedBottomSheet;
import org.telegram.p026ui.Components.Premium.boosts.UserSelectorBottomSheet;
import org.telegram.p026ui.Components.ProfileGalleryView;
import org.telegram.p026ui.Components.RLottieDrawable;
import org.telegram.p026ui.Components.RLottieImageView;
import org.telegram.p026ui.Components.RadialProgressView;
import org.telegram.p026ui.Components.Reactions.CustomEmojiReactionsWindow;
import org.telegram.p026ui.Components.Reactions.ReactionsEffectOverlay;
import org.telegram.p026ui.Components.Reactions.ReactionsLayoutInBubble;
import org.telegram.p026ui.Components.ReactionsContainerLayout;
import org.telegram.p026ui.Components.RecordStatusDrawable;
import org.telegram.p026ui.Components.RecyclerListView;
import org.telegram.p026ui.Components.ScaleStateListAnimator;
import org.telegram.p026ui.Components.ShareAlert;
import org.telegram.p026ui.Components.SizeNotifierFrameLayout;
import org.telegram.p026ui.Components.Text;
import org.telegram.p026ui.Components.TypefaceSpan;
import org.telegram.p026ui.Components.UndoView;
import org.telegram.p026ui.Components.conference.GroupCallActivityButtonsLayout;
import org.telegram.p026ui.Components.conference.message.GroupCallMessageCell;
import org.telegram.p026ui.Components.conference.message.GroupCallMessagesListView;
import org.telegram.p026ui.Components.inset.WindowInsetsStateHolder;
import org.telegram.p026ui.Components.voip.CellFlickerDrawable;
import org.telegram.p026ui.Components.voip.GroupCallGridCell;
import org.telegram.p026ui.Components.voip.GroupCallMiniTextureView;
import org.telegram.p026ui.Components.voip.GroupCallRenderersContainer;
import org.telegram.p026ui.Components.voip.PrivateVideoPreviewDialog;
import org.telegram.p026ui.Components.voip.RTMPStreamPipOverlay;
import org.telegram.p026ui.Components.voip.VoIPTextureView;
import org.telegram.p026ui.Components.voip.VoIPToggleButton;
import org.telegram.p026ui.GroupCallActivity;
import org.telegram.p026ui.PhotoViewer;
import org.telegram.p026ui.PinchToZoomHelper;
import org.telegram.p026ui.Stories.recorder.DominantColors;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.Vector;
import org.telegram.tgnet.p025tl.TL_account;
import org.telegram.tgnet.p025tl.TL_phone;
import org.webrtc.MediaStreamTrack;
import org.webrtc.voiceengine.WebRtcAudioTrack;
import p019j$.util.Collection;
import p019j$.util.Objects;
import p019j$.util.function.Function$CC;
import p019j$.util.stream.Collectors;

/* JADX INFO: loaded from: classes3.dex */
public class GroupCallActivity extends BottomSheet implements NotificationCenter.NotificationCenterDelegate, VoIPService.StateListener, FactorAnimator.Target {
    public static GroupCallActivity groupCallInstance;
    public static boolean groupCallUiVisible;
    public static boolean isLandscapeMode;
    public static boolean isTabletMode;
    public static boolean paused;
    public static volatile DispatchQueue updateTextureLightningQueue = new DispatchQueue("updateTextureLightningQueue");
    private final AccountInstance accountInstance;
    private final AccountSelectCell accountSelectCell;
    private final ActionBar actionBar;
    private AnimatorSet actionBarAnimation;
    private final View actionBarBackground;
    private final View actionBarShadow;
    ObjectAnimator additionalSubtitleYAnimator;
    private ActionBarMenuSubItem adminItem;
    private float amplitude;
    private float animateAmplitudeDiff;
    private float animateToAmplitude;
    private boolean animatingToFullscreenExpand;
    private final BoolAnimator animatorHasVideo;
    private final BoolAnimator animatorHideButtons;
    private final FactorAnimator animatorMessageInputHeight;
    private final BoolAnimator animatorMessageIsEmpty;
    private boolean anyEnterEventSent;
    private final ArrayList attachedRenderers;
    private final ArrayList attachedRenderersTmp;
    private final AvatarPreviewPagerIndicator avatarPagerIndicator;
    private final FrameLayout avatarPreviewContainer;
    private boolean avatarPriviewTransitionInProgress;
    AvatarUpdaterDelegate avatarUpdaterDelegate;
    private boolean avatarsPreviewShowed;
    private final ProfileGalleryView avatarsViewPager;
    private int backgroundColor;
    private final RLottieDrawable bigMicDrawable;
    private final BlobDrawable bigWaveDrawable;
    private final View blurredView;
    private final FrameLayout bulletinContainer;
    private GradientDrawable buttonsBackgroundGradient;
    private final View buttonsBackgroundGradientView;
    private final View buttonsBackgroundGradientView2;
    private final GroupCallActivityButtonsLayout buttonsContainer;
    private int buttonsVisibility;
    private Integer cacheAudioOutputValue;
    public ChatObject.Call call;
    private boolean callInitied;
    private final FrameLayout callMessageEnterContainer;
    private final Paint callMessageEnterContainerBgPaint;
    private final FrameLayout callMessageEnterUnderContainer;
    private final EditTextEmoji callMessageEnterView;
    private final ImageView callMessageHideButton;
    private final ImageView callMessageSendButton;
    private final VoIPToggleButton cameraButton;
    public CellFlickerDrawable cellFlickerDrawable;
    private boolean changingPermissions;
    private float colorProgress;
    private final int[] colorsTmp;
    private boolean contentFullyOverlayed;
    private long creatingServiceTime;
    ImageUpdater currentAvatarUpdater;
    private int currentCallState;
    public TLRPC.Chat currentChat;
    private ViewGroup currentOptionsLayout;
    private WeavingState currentState;
    private boolean delayedGroupCallUpdated;
    private DiffUtil.Callback diffUtilsCallback;
    private final ActionBarMenuSubItem disableComments;
    private boolean drawSpeakingSubtitle;
    public boolean drawingForBlur;
    private final ActionBarMenuSubItem editTitleItem;
    private final ActionBarMenuSubItem enableComments;
    private CallEncryptionCellDrawable encryptionDrawable;
    private boolean enterEventSent;
    private ActionBarMenuSubItem everyoneItem;
    private final ImageView expandOrMinimizeButton;
    private final VoIPToggleButton flipButton;
    private final RLottieDrawable flipIcon;
    private int flipIconCurrentEndFrame;
    GroupCallFullscreenAdapter fullscreenAdapter;
    private final DefaultItemAnimator fullscreenListItemAnimator;
    RecyclerListView fullscreenUsersListView;
    private final int[] gradientColors;
    private final GroupCallMessagesListView groupCallMessagesListView;
    private GroupVoipInviteAlert groupVoipInviteAlert;
    private final RLottieDrawable handDrawables;
    private boolean hasScrimAnchorView;
    private boolean hasVideo;
    private boolean invalidateColors;
    private final ActionBarMenuSubItem inviteItem;
    private String[] invites;
    private boolean isInDrawRenderNodeBlur;
    private boolean isInFullscreen;
    private final GroupCallItemAnimator itemAnimator;
    private long lastUpdateTime;
    private final FillLastGridLayoutManager layoutManager;
    private final VoIPToggleButton leaveButton;
    private final ActionBarMenuSubItem leaveItem;
    private final AnimatedTextView limitTextView;
    private final ListAdapter listAdapter;
    private RecyclerListView listView;
    private final Paint listViewBackgroundPaint;
    private boolean listViewVideoVisibility;
    private ValueAnimator liveLabelBgColorAnimator;
    private final Paint liveLabelPaint;
    private final TextView liveLabelTextView;
    private final int maxGroupCallMessageLength;
    private final LinearLayout menuItemsContainer;
    private final VoIPToggleButton messageButton;
    private final VoIPToggleButton muteButton;
    private final RLottieImageView muteButtonIcon;
    private int muteButtonState;
    private boolean needTextureLightning;
    private final ActionBarMenuSubItem noiseItem;
    private int oldAddMemberRow;
    private int oldConferenceAddPeopleRow;
    private int oldConferenceShareLinkRow;
    private int oldCount;
    private int oldEncryptionRow;
    private final ArrayList oldInvited;
    private int oldInvitedEndRow;
    private int oldInvitedStartRow;
    private final ArrayList oldParticipants;
    private final ArrayList oldShadyJoin;
    private int oldShadyJoinEndRow;
    private int oldShadyJoinStartRow;
    private final ArrayList oldShadyLeft;
    private int oldShadyLeftEndRow;
    private int oldShadyLeftStartRow;
    private int oldUsersEndRow;
    private int oldUsersStartRow;
    private int oldUsersVideoEndRow;
    private int oldUsersVideoStartRow;
    private int oldVideoDividerRow;
    private int oldVideoNotAvailableRow;
    private final ArrayList oldVideoParticipants;
    private final Runnable onUserLeaveHintListener;
    private final ActionBarMenuItem otherItem;
    private Paint paint;
    private Paint paintTmp;
    private LaunchActivity parentActivity;
    private Boolean pendingCommentsEnabled;
    private final ActionBarMenuSubItem permissionItem;
    PinchToZoomHelper pinchToZoomHelper;
    private final ActionBarMenuItem pipItem;
    private PipSource pipSource;
    private boolean playingHandAnimation;
    private int popupAnimationIndex;
    private final Runnable pressRunnable;
    private boolean pressed;
    private WeavingState prevState;
    PrivateVideoPreviewDialog previewDialog;
    private boolean previewTextureTransitionEnabled;
    private float progressToAvatarPreview;
    private RadialGradient radialGradient;
    private final Matrix radialMatrix;
    private final Paint radialPaint;
    private final RadialProgressView radialProgressView;
    private final ImageReceiver reactionEffectImageReceiver;
    private ReactionsContainerLayout reactionsContainerLayout;
    private RecordCallDrawable recordCallDrawable;
    private HintView recordHintView;
    private final ActionBarMenuSubItem recordItem;
    private final RectF rect;
    private HintView reminderHintView;
    private RenderNode renderNodeBlur;
    private float renderNodeBlurScale;
    private GroupCallRenderersContainer renderersContainer;
    ViewTreeObserver.OnPreDrawListener requestFullscreenListener;
    private ValueAnimator scheduleAnimator;
    private TextView scheduleButtonTextView;
    private final boolean scheduleHasFewPeers;
    private TextView scheduleInfoTextView;
    private TLRPC.InputPeer schedulePeer;
    private int scheduleStartAt;
    private SimpleTextView scheduleStartAtTextView;
    private SimpleTextView scheduleStartInTextView;
    private SimpleTextView scheduleTimeTextView;
    private LinearLayout scheduleTimerContainer;
    private boolean scheduled;
    private final String scheduledHash;
    private final ActionBarMenuSubItem screenItem;
    private final ActionBarMenuItem screenShareItem;
    private AnimatorSet scrimAnimatorSet;
    private GroupCallFullscreenAdapter.GroupCallUserCell scrimFullscreenView;
    private GroupCallGridCell scrimGridView;
    private Paint scrimPaint;
    private View scrimPopupLayout;
    private ActionBarPopupWindow scrimPopupWindow;
    private GroupCallMiniTextureView scrimRenderer;
    private GroupCallUserCell scrimView;
    private boolean scrimViewAttached;
    private float scrollOffsetY;
    private TLRPC.Peer selfPeer;
    private final Drawable shadowDrawable;
    private ShareAlert shareAlert;
    private float showLightingProgress;
    private float showWavesProgress;
    private final SizeNotifierFrameLayout sizeNotifierFrameLayout;
    private final VoIPToggleButton soundButton;
    private ActionBarMenuSubItem soundItem;
    private final View soundItemDivider;
    private final GridLayoutManager.SpanSizeLookup spanSizeLookup;
    private final VoIPToggleButton speakerButton;
    private int speakerIcon;
    private final ImageView speakerImageView;
    private boolean startingGroupCall;
    private WeavingState[] states;
    public final ArrayList statusIconPool;
    ObjectAnimator subtitleYAnimator;
    private float switchProgress;
    private float switchToButtonInt2;
    private float switchToButtonProgress;
    GroupCallTabletGridAdapter tabletGridAdapter;
    RecyclerListView tabletVideoGridView;
    private LightningView textureLightningView;
    private float textureLightningViewAnimatingAlpha;
    private final BlobDrawable tinyWaveDrawable;
    private final LinearLayout titleLayout;
    private final AudioPlayerAlert.ClippingTextViewSwitcher titleTextView;
    private final UndoView[] undoView;
    private final Runnable unmuteRunnable;
    private Runnable updateCallRecordRunnable;
    private final Runnable updateSchedeulRunnable;
    private final Runnable updateTextureLightningRunnable;
    private boolean useBlur;
    private TLObject userSwitchObject;
    LongSparseIntArray visiblePeerIds;
    public final ArrayList visibleVideoParticipants;
    private Boolean wasExpandBigSize;
    private Boolean wasNotInLayoutFullscreen;
    private WatchersView watchersView;
    private final WindowInsetsStateHolder windowInsetsStateHolder;

    public static /* synthetic */ void $r8$lambda$8EurkvvsVUrHi4p4ZztICb4ueo0(DialogInterface dialogInterface) {
    }

    @Override // org.telegram.p026ui.ActionBar.BottomSheet
    protected boolean canDismissWithSwipe() {
        return false;
    }

    public boolean getAudioOutputActive(int i) {
        return i != 1;
    }

    @Override // org.telegram.messenger.voip.VoIPService.StateListener
    public /* synthetic */ void onCameraFirstFrameAvailable() {
        VoIPService.StateListener.CC.$default$onCameraFirstFrameAvailable(this);
    }

    @Override // me.vkryl.android.animator.FactorAnimator.Target
    public /* synthetic */ void onFactorChangeFinished(int i, float f, FactorAnimator factorAnimator) {
        FactorAnimator.Target.CC.$default$onFactorChangeFinished(this, i, f, factorAnimator);
    }

    @Override // org.telegram.messenger.voip.VoIPService.StateListener
    public /* synthetic */ void onMediaStateUpdated(int i, int i2) {
        VoIPService.StateListener.CC.$default$onMediaStateUpdated(this, i, i2);
    }

    @Override // org.telegram.messenger.voip.VoIPService.StateListener
    public /* synthetic */ void onScreenOnChange(boolean z) {
        VoIPService.StateListener.CC.$default$onScreenOnChange(this, z);
    }

    @Override // org.telegram.messenger.voip.VoIPService.StateListener
    public /* synthetic */ void onSignalBarsCountChanged(int i) {
        VoIPService.StateListener.CC.$default$onSignalBarsCountChanged(this, i);
    }

    @Override // org.telegram.messenger.voip.VoIPService.StateListener
    public /* synthetic */ void onVideoAvailableChange(boolean z) {
        VoIPService.StateListener.CC.$default$onVideoAvailableChange(this, z);
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$1 */
    /* JADX INFO: loaded from: classes6.dex */
    class RunnableC55471 implements Runnable {
        RunnableC55471() {
        }

        @Override // java.lang.Runnable
        public void run() {
            int i;
            if (GroupCallActivity.this.scheduleTimeTextView == null || GroupCallActivity.this.isDismissed()) {
                return;
            }
            GroupCallActivity groupCallActivity = GroupCallActivity.this;
            ChatObject.Call call = groupCallActivity.call;
            if (call != null) {
                i = call.call.schedule_date;
            } else {
                i = groupCallActivity.scheduleStartAt;
            }
            if (i == 0) {
                return;
            }
            int currentTime = i - GroupCallActivity.this.accountInstance.getConnectionsManager().getCurrentTime();
            if (currentTime >= 86400) {
                GroupCallActivity.this.scheduleTimeTextView.setText(LocaleController.formatPluralString("Days", Math.round(currentTime / 86400.0f), new Object[0]));
            } else {
                GroupCallActivity.this.scheduleTimeTextView.setText(AndroidUtilities.formatFullDuration(Math.abs(currentTime)));
                if (currentTime < 0 && GroupCallActivity.this.scheduleStartInTextView.getTag() == null) {
                    GroupCallActivity.this.scheduleStartInTextView.setTag(1);
                    GroupCallActivity.this.scheduleStartInTextView.setText(LocaleController.getString(C2702R.string.VoipChatLateBy));
                }
            }
            GroupCallActivity.this.scheduleStartAtTextView.setText(LocaleController.formatStartsTime(i, 3));
            AndroidUtilities.runOnUIThread(GroupCallActivity.this.updateSchedeulRunnable, 1000L);
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$_Yrqkqko-hdH6mXwgsnpz0Pkz9U */
    public static /* synthetic */ void m13752$r8$lambda$_YrqkqkohdH6mXwgsnpz0Pkz9U() {
        if (VoIPService.getSharedInstance() == null) {
            return;
        }
        VoIPService.getSharedInstance().setMicMute(false, true, false);
    }

    public void pressRunnableImpl() {
        if (this.call == null || !this.scheduled || VoIPService.getSharedInstance() == null) {
            return;
        }
        try {
            this.muteButton.performHapticFeedback(3, 2);
        } catch (Exception unused) {
        }
        updateMuteButton(1, true);
        AndroidUtilities.runOnUIThread(this.unmuteRunnable, 80L);
        this.scheduled = false;
        this.pressed = true;
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$2 */
    /* JADX INFO: loaded from: classes6.dex */
    class RunnableC55582 implements Runnable {
        RunnableC55582() {
        }

        @Override // java.lang.Runnable
        public void run() {
            GroupCallGridCell groupCallGridCellFindGroupCallGridCell;
            GroupCallMiniTextureView renderer;
            final VoIPTextureView voIPTextureView;
            try {
                if (GroupCallActivity.this.renderersContainer != null && !GroupCallActivity.this.renderersContainer.inFullscreenMode && (groupCallGridCellFindGroupCallGridCell = GroupCallActivity.this.findGroupCallGridCell()) != null && groupCallGridCellFindGroupCallGridCell.isAttachedToWindow() && (renderer = groupCallGridCellFindGroupCallGridCell.getRenderer()) != null && (voIPTextureView = renderer.textureView) != null) {
                    GroupCallActivity.updateTextureLightningQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$2$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$run$1(voIPTextureView);
                        }
                    });
                }
            } catch (Exception e) {
                FileLog.m1093e(e);
            }
        }

        public /* synthetic */ void lambda$run$1(VoIPTextureView voIPTextureView) {
            try {
                Bitmap bitmap = voIPTextureView.blurRenderer.getBitmap(100, 100);
                if (bitmap == null) {
                    return;
                }
                final int[] colorsSync = DominantColors.getColorsSync(false, bitmap, true);
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$2$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$run$0(colorsSync);
                    }
                });
            } catch (Exception e) {
                FileLog.m1093e(e);
            }
        }

        public /* synthetic */ void lambda$run$0(int[] iArr) {
            GroupCallActivity.this.textureLightningView.setNewColors(iArr);
        }
    }

    public GroupCallGridCell findGroupCallGridCell() {
        for (int i = 0; i < this.listView.getChildCount(); i++) {
            View childAt = this.listView.getChildAt(i);
            if (childAt.isAttachedToWindow() && (childAt instanceof GroupCallGridCell) && this.listView.getChildAdapterPosition(childAt) >= 0) {
                return (GroupCallGridCell) childAt;
            }
        }
        return null;
    }

    public void runUpdateTextureLightningRunnable() {
        AndroidUtilities.cancelRunOnUIThread(this.updateTextureLightningRunnable);
        if (!this.needTextureLightning || this.textureLightningView == null || VoIPService.getSharedInstance() == null || !isRtmpStream() || this.listView == null || !LiteMode.isEnabled(512)) {
            return;
        }
        AndroidUtilities.runOnUIThread(this.updateTextureLightningRunnable, 30L);
    }

    /* JADX INFO: loaded from: classes6.dex */
    private static class SmallRecordCallDrawable extends Drawable {
        private long lastUpdateTime;
        private View parentView;
        private int state;
        private Paint paint2 = new Paint(1);
        private float alpha = 1.0f;

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return -2;
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i) {
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
        }

        public SmallRecordCallDrawable(View view) {
            this.parentView = view;
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicWidth() {
            return AndroidUtilities.m1081dp(24.0f);
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicHeight() {
            return AndroidUtilities.m1081dp(24.0f);
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            int iM1081dp;
            int iCenterX = getBounds().centerX();
            int iCenterY = getBounds().centerY();
            if (this.parentView instanceof SimpleTextView) {
                iM1081dp = iCenterY + AndroidUtilities.m1081dp(1.0f);
                iCenterX -= AndroidUtilities.m1081dp(3.0f);
            } else {
                iM1081dp = iCenterY + AndroidUtilities.m1081dp(2.0f);
            }
            this.paint2.setColor(-1147527);
            this.paint2.setAlpha((int) (this.alpha * 255.0f));
            canvas.drawCircle(iCenterX, iM1081dp, AndroidUtilities.m1081dp(4.0f), this.paint2);
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            long j = jElapsedRealtime - this.lastUpdateTime;
            if (j > 17) {
                j = 17;
            }
            this.lastUpdateTime = jElapsedRealtime;
            int i = this.state;
            if (i == 0) {
                float f = this.alpha + (j / 2000.0f);
                this.alpha = f;
                if (f >= 1.0f) {
                    this.alpha = 1.0f;
                    this.state = 1;
                }
            } else if (i == 1) {
                float f2 = this.alpha - (j / 2000.0f);
                this.alpha = f2;
                if (f2 < 0.5f) {
                    this.alpha = 0.5f;
                    this.state = 0;
                }
            }
            this.parentView.invalidate();
        }
    }

    /* JADX INFO: loaded from: classes6.dex */
    private static class RecordCallDrawable extends Drawable {
        private long lastUpdateTime;
        private View parentView;
        private boolean recording;
        private int state;
        private Paint paint = new Paint(1);
        private Paint paint2 = new Paint(1);
        private float alpha = 1.0f;

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return -2;
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i) {
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
        }

        public RecordCallDrawable() {
            this.paint.setColor(-1);
            this.paint.setStyle(Paint.Style.STROKE);
            this.paint.setStrokeWidth(AndroidUtilities.m1081dp(1.5f));
        }

        public void setParentView(View view) {
            this.parentView = view;
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicWidth() {
            return AndroidUtilities.m1081dp(24.0f);
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicHeight() {
            return AndroidUtilities.m1081dp(24.0f);
        }

        public void setRecording(boolean z) {
            this.recording = z;
            this.alpha = 1.0f;
            invalidateSelf();
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            float fCenterX = getBounds().centerX();
            float fCenterY = getBounds().centerY();
            canvas.drawCircle(fCenterX, fCenterY, AndroidUtilities.m1081dp(10.0f), this.paint);
            this.paint2.setColor(this.recording ? -1147527 : -1);
            this.paint2.setAlpha((int) (this.alpha * 255.0f));
            canvas.drawCircle(fCenterX, fCenterY, AndroidUtilities.m1081dp(5.0f), this.paint2);
            if (this.recording) {
                long jElapsedRealtime = SystemClock.elapsedRealtime();
                long j = jElapsedRealtime - this.lastUpdateTime;
                if (j > 17) {
                    j = 17;
                }
                this.lastUpdateTime = jElapsedRealtime;
                int i = this.state;
                if (i == 0) {
                    float f = this.alpha + (j / 2000.0f);
                    this.alpha = f;
                    if (f >= 1.0f) {
                        this.alpha = 1.0f;
                        this.state = 1;
                    }
                } else if (i == 1) {
                    float f2 = this.alpha - (j / 2000.0f);
                    this.alpha = f2;
                    if (f2 < 0.5f) {
                        this.alpha = 0.5f;
                        this.state = 0;
                    }
                }
                this.parentView.invalidate();
            }
        }
    }

    /* JADX INFO: loaded from: classes6.dex */
    private class VolumeSlider extends FrameLayout {
        private boolean captured;
        private float colorChangeProgress;
        private int currentColor;
        private TLRPC.GroupCallParticipant currentParticipant;
        private double currentProgress;
        private boolean dragging;
        private RLottieImageView imageView;
        private long lastUpdateTime;
        private int oldColor;
        private Paint paint;
        private Paint paint2;
        private Path path;
        private float[] radii;
        private RectF rect;
        private RLottieDrawable speakerDrawable;

        /* JADX INFO: renamed from: sx */
        private float f2076sx;

        /* JADX INFO: renamed from: sy */
        private float f2077sy;
        private TextView textView;
        private int thumbX;
        private float[] volumeAlphas;

        public VolumeSlider(Context context, TLRPC.GroupCallParticipant groupCallParticipant) {
            super(context);
            this.paint = new Paint(1);
            this.paint2 = new Paint(1);
            this.path = new Path();
            this.radii = new float[8];
            this.rect = new RectF();
            this.volumeAlphas = new float[3];
            setWillNotDraw(false);
            this.currentParticipant = groupCallParticipant;
            this.currentProgress = ChatObject.getParticipantVolume(groupCallParticipant) / 20000.0f;
            this.colorChangeProgress = 1.0f;
            setPadding(AndroidUtilities.m1081dp(12.0f), 0, AndroidUtilities.m1081dp(12.0f), 0);
            this.speakerDrawable = new RLottieDrawable(C2702R.raw.speaker, _UrlKt.FRAGMENT_ENCODE_SET + C2702R.raw.speaker, AndroidUtilities.m1081dp(24.0f), AndroidUtilities.m1081dp(24.0f), true, null);
            RLottieImageView rLottieImageView = new RLottieImageView(context);
            this.imageView = rLottieImageView;
            rLottieImageView.setScaleType(ImageView.ScaleType.CENTER);
            this.imageView.setAnimation(this.speakerDrawable);
            this.imageView.setTag(this.currentProgress == 0.0d ? 1 : null);
            addView(this.imageView, LayoutHelper.createFrame(-2, 40.0f, (LocaleController.isRTL ? 5 : 3) | 16, 0.0f, 0.0f, 0.0f, 0.0f));
            this.speakerDrawable.setCustomEndFrame(this.currentProgress == 0.0d ? 17 : 34);
            RLottieDrawable rLottieDrawable = this.speakerDrawable;
            rLottieDrawable.setCurrentFrame(rLottieDrawable.getCustomEndFrame() - 1, false, true);
            TextView textView = new TextView(context);
            this.textView = textView;
            textView.setLines(1);
            this.textView.setSingleLine(true);
            this.textView.setGravity(3);
            this.textView.setEllipsize(TextUtils.TruncateAt.END);
            this.textView.setTextColor(Theme.getColor(Theme.key_voipgroup_actionBarItems));
            this.textView.setTextSize(1, 16.0f);
            double participantVolume = ((double) ChatObject.getParticipantVolume(this.currentParticipant)) / 100.0d;
            this.textView.setText(String.format(Locale.US, "%d%%", Integer.valueOf((int) (participantVolume > 0.0d ? Math.max(participantVolume, 1.0d) : 0.0d))));
            this.textView.setPadding(LocaleController.isRTL ? 0 : AndroidUtilities.m1081dp(43.0f), 0, LocaleController.isRTL ? AndroidUtilities.m1081dp(43.0f) : 0, 0);
            addView(this.textView, LayoutHelper.createFrame(-2, -2, (LocaleController.isRTL ? 5 : 3) | 16));
            this.paint2.setStyle(Paint.Style.STROKE);
            this.paint2.setStrokeWidth(AndroidUtilities.m1081dp(1.5f));
            this.paint2.setStrokeCap(Paint.Cap.ROUND);
            this.paint2.setColor(-1);
            int participantVolume2 = (int) (((double) ChatObject.getParticipantVolume(this.currentParticipant)) / 100.0d);
            int i = 0;
            while (true) {
                float[] fArr = this.volumeAlphas;
                if (i >= fArr.length) {
                    return;
                }
                if (participantVolume2 > (i == 0 ? 0 : i == 1 ? 50 : 150)) {
                    fArr[i] = 1.0f;
                } else {
                    fArr[i] = 0.0f;
                }
                i++;
            }
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1081dp(48.0f), TLObject.FLAG_30));
            this.thumbX = (int) (((double) View.MeasureSpec.getSize(i)) * this.currentProgress);
        }

        @Override // android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            return onTouch(motionEvent);
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            return onTouch(motionEvent);
        }

        boolean onTouch(MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0) {
                this.f2076sx = motionEvent.getX();
                this.f2077sy = motionEvent.getY();
                return true;
            }
            if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                this.captured = false;
                if (motionEvent.getAction() == 1) {
                    if (Math.abs(motionEvent.getY() - this.f2077sy) < ViewConfiguration.get(getContext()).getScaledTouchSlop()) {
                        int x = (int) motionEvent.getX();
                        this.thumbX = x;
                        if (x < 0) {
                            this.thumbX = 0;
                        } else if (x > getMeasuredWidth()) {
                            this.thumbX = getMeasuredWidth();
                        }
                        this.dragging = true;
                    }
                }
                if (this.dragging) {
                    if (motionEvent.getAction() == 1) {
                        onSeekBarDrag(((double) this.thumbX) / ((double) getMeasuredWidth()), true);
                    }
                    this.dragging = false;
                    invalidate();
                    return true;
                }
            } else if (motionEvent.getAction() == 2) {
                if (!this.captured) {
                    ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
                    if (Math.abs(motionEvent.getY() - this.f2077sy) <= viewConfiguration.getScaledTouchSlop() && Math.abs(motionEvent.getX() - this.f2076sx) > viewConfiguration.getScaledTouchSlop()) {
                        this.captured = true;
                        getParent().requestDisallowInterceptTouchEvent(true);
                        if (motionEvent.getY() >= 0.0f && motionEvent.getY() <= getMeasuredHeight()) {
                            int x2 = (int) motionEvent.getX();
                            this.thumbX = x2;
                            if (x2 < 0) {
                                this.thumbX = 0;
                            } else if (x2 > getMeasuredWidth()) {
                                this.thumbX = getMeasuredWidth();
                            }
                            this.dragging = true;
                            invalidate();
                            return true;
                        }
                    }
                } else if (this.dragging) {
                    int x3 = (int) motionEvent.getX();
                    this.thumbX = x3;
                    if (x3 < 0) {
                        this.thumbX = 0;
                    } else if (x3 > getMeasuredWidth()) {
                        this.thumbX = getMeasuredWidth();
                    }
                    onSeekBarDrag(((double) this.thumbX) / ((double) getMeasuredWidth()), false);
                    invalidate();
                    return true;
                }
            }
            return false;
        }

        private void onSeekBarDrag(double d, boolean z) {
            TLObject chat;
            if (VoIPService.getSharedInstance() == null) {
                return;
            }
            this.currentProgress = d;
            TLRPC.GroupCallParticipant groupCallParticipant = this.currentParticipant;
            groupCallParticipant.volume = (int) (d * 20000.0d);
            groupCallParticipant.volume_by_admin = false;
            groupCallParticipant.flags |= 128;
            double participantVolume = ((double) ChatObject.getParticipantVolume(groupCallParticipant)) / 100.0d;
            this.textView.setText(String.format(Locale.US, "%d%%", Integer.valueOf((int) (participantVolume > 0.0d ? Math.max(participantVolume, 1.0d) : 0.0d))));
            VoIPService sharedInstance = VoIPService.getSharedInstance();
            TLRPC.GroupCallParticipant groupCallParticipant2 = this.currentParticipant;
            sharedInstance.setParticipantVolume(groupCallParticipant2, groupCallParticipant2.volume);
            if (z) {
                long peerId = MessageObject.getPeerId(this.currentParticipant.peer);
                if (peerId > 0) {
                    chat = GroupCallActivity.this.accountInstance.getMessagesController().getUser(Long.valueOf(peerId));
                } else {
                    chat = GroupCallActivity.this.accountInstance.getMessagesController().getChat(Long.valueOf(-peerId));
                }
                TLObject tLObject = chat;
                if (this.currentParticipant.volume == 0) {
                    if (GroupCallActivity.this.scrimPopupWindow != null) {
                        GroupCallActivity.this.scrimPopupWindow.dismiss();
                        GroupCallActivity.this.scrimPopupWindow = null;
                    }
                    GroupCallActivity.this.dismissAvatarPreview(true);
                    GroupCallActivity groupCallActivity = GroupCallActivity.this;
                    groupCallActivity.processSelectedOption(this.currentParticipant, peerId, groupCallActivity.canManageCall() ? 0 : 5);
                } else {
                    VoIPService.getSharedInstance().editCallMember(tLObject, null, null, Integer.valueOf(this.currentParticipant.volume), null, null);
                }
            }
            Integer num = this.currentProgress == 0.0d ? 1 : null;
            if ((this.imageView.getTag() != null || num == null) && (this.imageView.getTag() == null || num != null)) {
                return;
            }
            this.speakerDrawable.setCustomEndFrame(this.currentProgress == 0.0d ? 17 : 34);
            this.speakerDrawable.setCurrentFrame(this.currentProgress != 0.0d ? 17 : 0);
            this.speakerDrawable.start();
            this.imageView.setTag(num);
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            int i;
            float fM1081dp;
            int i2;
            int i3 = this.currentColor;
            double d = this.currentProgress;
            if (d < 0.25d) {
                this.currentColor = -3385513;
            } else if (d > 0.25d && d < 0.5d) {
                this.currentColor = -3562181;
            } else if (d >= 0.5d && d <= 0.75d) {
                this.currentColor = -11027349;
            } else {
                this.currentColor = -11688225;
            }
            float f = 0.0f;
            float f2 = 1.0f;
            if (i3 == 0) {
                i = this.currentColor;
                this.colorChangeProgress = 1.0f;
            } else {
                int offsetColor = AndroidUtilities.getOffsetColor(this.oldColor, i3, this.colorChangeProgress, 1.0f);
                if (i3 != this.currentColor) {
                    this.colorChangeProgress = 0.0f;
                    this.oldColor = offsetColor;
                }
                i = offsetColor;
            }
            this.paint.setColor(i);
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            long j = jElapsedRealtime - this.lastUpdateTime;
            if (j > 17) {
                j = 17;
            }
            this.lastUpdateTime = jElapsedRealtime;
            float f3 = this.colorChangeProgress;
            if (f3 < 1.0f) {
                float f4 = f3 + (j / 200.0f);
                this.colorChangeProgress = f4;
                if (f4 > 1.0f) {
                    this.colorChangeProgress = 1.0f;
                } else {
                    invalidate();
                }
            }
            this.path.reset();
            float[] fArr = this.radii;
            float f5 = 6.0f;
            float fM1081dp2 = AndroidUtilities.m1081dp(6.0f);
            fArr[7] = fM1081dp2;
            fArr[6] = fM1081dp2;
            int i4 = 1;
            fArr[1] = fM1081dp2;
            int i5 = 0;
            fArr[0] = fM1081dp2;
            float fMax = this.thumbX < AndroidUtilities.m1081dp(12.0f) ? Math.max(0.0f, (this.thumbX - AndroidUtilities.m1081dp(6.0f)) / AndroidUtilities.m1081dp(6.0f)) : 1.0f;
            float[] fArr2 = this.radii;
            float fM1081dp3 = AndroidUtilities.m1081dp(6.0f) * fMax;
            fArr2[5] = fM1081dp3;
            fArr2[4] = fM1081dp3;
            fArr2[3] = fM1081dp3;
            fArr2[2] = fM1081dp3;
            this.rect.set(0.0f, 0.0f, this.thumbX, getMeasuredHeight());
            this.path.addRoundRect(this.rect, this.radii, Path.Direction.CW);
            this.path.close();
            Canvas canvas2 = canvas;
            canvas2.drawPath(this.path, this.paint);
            int participantVolume = (int) (((double) ChatObject.getParticipantVolume(this.currentParticipant)) / 100.0d);
            int left = this.imageView.getLeft() + (this.imageView.getMeasuredWidth() / 2) + AndroidUtilities.m1081dp(5.0f);
            int top = this.imageView.getTop() + (this.imageView.getMeasuredHeight() / 2);
            int i6 = 0;
            while (i6 < this.volumeAlphas.length) {
                if (i6 == 0) {
                    fM1081dp = AndroidUtilities.m1081dp(f5);
                    i2 = i5;
                } else if (i6 == i4) {
                    fM1081dp = AndroidUtilities.m1081dp(10.0f);
                    i2 = 50;
                } else {
                    fM1081dp = AndroidUtilities.m1081dp(14.0f);
                    i2 = 150;
                }
                float fM1081dp4 = AndroidUtilities.m1081dp(2.0f);
                float f6 = f;
                float f7 = this.volumeAlphas[i6];
                float f8 = fM1081dp4 * (f2 - f7);
                float f9 = f2;
                this.paint2.setAlpha((int) (f7 * 255.0f));
                float f10 = left;
                float f11 = top;
                this.rect.set((f10 - fM1081dp) + f8, (f11 - fM1081dp) + f8, (f10 + fM1081dp) - f8, (f11 + fM1081dp) - f8);
                int i7 = i6;
                int i8 = i2;
                canvas2.drawArc(this.rect, -50.0f, 100.0f, false, this.paint2);
                if (participantVolume > i8) {
                    float[] fArr3 = this.volumeAlphas;
                    float f12 = fArr3[i7];
                    if (f12 < f9) {
                        float f13 = f12 + (j / 180.0f);
                        fArr3[i7] = f13;
                        if (f13 > f9) {
                            fArr3[i7] = f9;
                        }
                        invalidate();
                    }
                } else {
                    float[] fArr4 = this.volumeAlphas;
                    float f14 = fArr4[i7];
                    if (f14 > f6) {
                        float f15 = f14 - (j / 180.0f);
                        fArr4[i7] = f15;
                        if (f15 < f6) {
                            fArr4[i7] = f6;
                        }
                        invalidate();
                    }
                }
                i6 = i7 + 1;
                canvas2 = canvas;
                f = f6;
                f2 = f9;
                f5 = 6.0f;
                i4 = 1;
                i5 = 0;
            }
        }
    }

    /* JADX INFO: loaded from: classes6.dex */
    public static class WeavingState {
        public int currentState;
        private float duration;
        public Shader shader;
        private float startX;
        private float startY;
        private float time;
        private float targetX = -1.0f;
        private float targetY = -1.0f;
        private final Matrix matrix = new Matrix();

        public WeavingState(int i) {
            this.currentState = i;
        }

        public void update(int i, int i2, int i3, long j, float f) {
            float f2;
            if (this.shader == null) {
                return;
            }
            float f3 = this.duration;
            if (f3 == 0.0f || this.time >= f3) {
                this.duration = Utilities.random.nextInt(DataTypes.EMPTY) + 1500;
                this.time = 0.0f;
                if (this.targetX == -1.0f) {
                    setTarget();
                }
                this.startX = this.targetX;
                this.startY = this.targetY;
                setTarget();
            }
            float f4 = j;
            float f5 = this.time + ((BlobDrawable.GRADIENT_SPEED_MIN + 0.5f) * f4) + (f4 * BlobDrawable.GRADIENT_SPEED_MAX * 2.0f * f);
            this.time = f5;
            float f6 = this.duration;
            if (f5 > f6) {
                this.time = f6;
            }
            float interpolation = CubicBezierInterpolator.EASE_OUT.getInterpolation(this.time / f6);
            float f7 = i3;
            float f8 = this.startX;
            float f9 = (i2 + ((f8 + ((this.targetX - f8) * interpolation)) * f7)) - 200.0f;
            float f10 = this.startY;
            float f11 = (i + (f7 * (f10 + ((this.targetY - f10) * interpolation)))) - 200.0f;
            if (GroupCallActivity.isGradientState(this.currentState)) {
                f2 = 1.0f;
            } else {
                f2 = this.currentState == 1 ? 4.0f : 2.5f;
            }
            float fM1081dp = (AndroidUtilities.m1081dp(122.0f) / 400.0f) * f2;
            this.matrix.reset();
            this.matrix.postTranslate(f9, f11);
            this.matrix.postScale(fM1081dp, fM1081dp, f9 + 200.0f, f11 + 200.0f);
            this.shader.setLocalMatrix(this.matrix);
        }

        private void setTarget() {
            if (GroupCallActivity.isGradientState(this.currentState)) {
                this.targetX = ((Utilities.random.nextInt(100) * 0.2f) / 100.0f) + 0.85f;
                this.targetY = 1.0f;
            } else if (this.currentState == 1) {
                this.targetX = ((Utilities.random.nextInt(100) * 0.3f) / 100.0f) + 0.2f;
                this.targetY = ((Utilities.random.nextInt(100) * 0.3f) / 100.0f) + 0.7f;
            } else {
                this.targetX = ((Utilities.random.nextInt(100) / 100.0f) * 0.2f) + 0.8f;
                this.targetY = Utilities.random.nextInt(100) / 100.0f;
            }
        }
    }

    public static boolean isGradientState(int i) {
        return !(VoIPService.getSharedInstance() == null || VoIPService.getSharedInstance().groupCall == null || !VoIPService.getSharedInstance().groupCall.call.rtmp_stream) || i == 2 || i == 4 || i == 5 || i == 6 || i == 7;
    }

    private void prepareBlurBitmap() {
        if (this.blurredView == null) {
            return;
        }
        int measuredWidth = (int) ((this.containerView.getMeasuredWidth() - (this.backgroundPaddingLeft * 2)) / 6.0f);
        int measuredHeight = (int) ((this.containerView.getMeasuredHeight() - AndroidUtilities.statusBarHeight) / 6.0f);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.scale(0.16666667f, 0.16666667f);
        canvas.save();
        canvas.translate(0.0f, -AndroidUtilities.statusBarHeight);
        this.parentActivity.getActionBarLayout().getView().draw(canvas);
        canvas.drawColor(ColorUtils.setAlphaComponent(-16777216, 76));
        canvas.restore();
        canvas.save();
        canvas.translate(this.containerView.getX(), -AndroidUtilities.statusBarHeight);
        this.drawingForBlur = true;
        this.containerView.draw(canvas);
        this.drawingForBlur = false;
        Utilities.stackBlurBitmap(bitmapCreateBitmap, Math.max(7, Math.max(measuredWidth, measuredHeight) / Opcodes.GETFIELD));
        this.blurredView.setBackground(new BitmapDrawable(bitmapCreateBitmap));
        this.blurredView.setAlpha(0.0f);
        this.blurredView.setVisibility(0);
        this.blurredView.bringToFront();
    }

    @Override // org.telegram.p026ui.ActionBar.BottomSheet
    protected boolean onCustomOpenAnimation() {
        groupCallUiVisible = true;
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.groupCallVisibilityChanged, new Object[0]);
        GroupCallPip.updateVisibility(getContext());
        return super.onCustomOpenAnimation();
    }

    @Override // org.telegram.p026ui.ActionBar.BottomSheet, android.app.Dialog, android.content.DialogInterface, org.telegram.ui.ActionBar.BaseFragment.AttachedSheet
    /* JADX INFO: renamed from: dismiss */
    public void lambda$new$0() {
        this.parentActivity.removeOnUserLeaveHintListener(this.onUserLeaveHintListener);
        this.parentActivity.setRequestedOrientation(-1);
        groupCallUiVisible = false;
        GroupVoipInviteAlert groupVoipInviteAlert = this.groupVoipInviteAlert;
        if (groupVoipInviteAlert != null) {
            groupVoipInviteAlert.lambda$new$0();
        }
        this.delayedGroupCallUpdated = true;
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.groupCallVisibilityChanged, new Object[0]);
        this.accountInstance.getNotificationCenter().removeObserver(this, NotificationCenter.needShowAlert);
        this.accountInstance.getNotificationCenter().removeObserver(this, NotificationCenter.groupCallUpdated);
        this.accountInstance.getNotificationCenter().removeObserver(this, NotificationCenter.chatInfoDidLoad);
        this.accountInstance.getNotificationCenter().removeObserver(this, NotificationCenter.didLoadChatAdmins);
        this.accountInstance.getNotificationCenter().removeObserver(this, NotificationCenter.applyGroupCallVisibleParticipants);
        this.accountInstance.getNotificationCenter().removeObserver(this, NotificationCenter.userInfoDidLoad);
        this.accountInstance.getNotificationCenter().removeObserver(this, NotificationCenter.mainUserInfoChanged);
        this.accountInstance.getNotificationCenter().removeObserver(this, NotificationCenter.updateInterfaces);
        this.accountInstance.getNotificationCenter().removeObserver(this, NotificationCenter.groupCallScreencastStateChanged);
        this.accountInstance.getNotificationCenter().removeObserver(this, NotificationCenter.groupCallSpeakingUsersUpdated);
        this.accountInstance.getNotificationCenter().removeObserver(this, NotificationCenter.conferenceEmojiUpdated);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.webRtcMicAmplitudeEvent);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.didEndCall);
        super.lambda$new$0();
    }

    public boolean isStillConnecting() {
        int i = this.currentCallState;
        return i == 1 || i == 2 || i == 6 || i == 5;
    }

    public long getChatId() {
        TLRPC.Chat chat = this.currentChat;
        if (chat == null) {
            return 0L;
        }
        return chat.f1610id;
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        ConferenceCall conferenceCall;
        TLRPC.GroupCallParticipant groupCallParticipant;
        TLRPC.GroupCallParticipant groupCallParticipant2;
        String string;
        ChatObject.VideoParticipant videoParticipant;
        VoIPService sharedInstance;
        int i3;
        int i4;
        String[] emojis = null;
        int i5 = 0;
        if (i == NotificationCenter.groupCallUpdated) {
            Long l = (Long) objArr[1];
            ChatObject.Call call = this.call;
            if (call == null || call.call.f1625id != l.longValue()) {
                return;
            }
            ChatObject.Call call2 = this.call;
            if (call2.call instanceof TLRPC.TL_groupCallDiscarded) {
                lambda$new$0();
                return;
            }
            if (this.creatingServiceTime == 0 && (((i4 = this.muteButtonState) == 7 || i4 == 5 || i4 == 6) && !call2.isScheduled())) {
                try {
                    Intent intent = new Intent(this.parentActivity, (Class<?>) VoIPService.class);
                    intent.putExtra("chat_id", getChatId());
                    intent.putExtra("createGroupCall", false);
                    intent.putExtra("hasFewPeers", this.scheduleHasFewPeers);
                    intent.putExtra("peerChannelId", this.schedulePeer.channel_id);
                    intent.putExtra("peerChatId", this.schedulePeer.chat_id);
                    intent.putExtra("peerUserId", this.schedulePeer.user_id);
                    intent.putExtra("hash", this.scheduledHash);
                    intent.putExtra("peerAccessHash", this.schedulePeer.access_hash);
                    intent.putExtra("is_outgoing", true);
                    intent.putExtra("start_incall_activity", false);
                    intent.putExtra("account", this.accountInstance.getCurrentAccount());
                    intent.putExtra("scheduleDate", this.scheduleStartAt);
                    this.parentActivity.startService(intent);
                } catch (Throwable th) {
                    FileLog.m1093e(th);
                }
                this.creatingServiceTime = SystemClock.elapsedRealtime();
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda36
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$didReceivedNotification$1();
                    }
                }, 3000L);
            }
            if (!this.callInitied && VoIPService.getSharedInstance() != null) {
                this.call.addSelfDummyParticipant(false);
                initCreatedGroupCall();
                VoIPService.getSharedInstance().playConnectedSound();
            }
            updateItems();
            int childCount = this.listView.getChildCount();
            for (int i6 = 0; i6 < childCount; i6++) {
                View childAt = this.listView.getChildAt(i6);
                if (childAt instanceof GroupCallUserCell) {
                    ((GroupCallUserCell) childAt).applyParticipantChanges(true);
                }
            }
            if (this.scrimView != null) {
                this.delayedGroupCallUpdated = true;
            } else {
                applyCallParticipantUpdates(true);
            }
            updateSubtitle();
            boolean zBooleanValue = ((Boolean) objArr[2]).booleanValue();
            boolean z = this.muteButtonState == 4;
            updateState(true, zBooleanValue);
            updateTitle(true);
            if (z && ((i3 = this.muteButtonState) == 1 || i3 == 0)) {
                getUndoView().showWithAction(0L, 38, (Runnable) null);
                if (VoIPService.getSharedInstance() != null) {
                    VoIPService.getSharedInstance().playAllowTalkSound();
                }
            }
            if (objArr.length >= 4) {
                Long l2 = (Long) objArr[3];
                long jLongValue = l2.longValue();
                if (jLongValue == 0 || isRtmpStream()) {
                    return;
                }
                if (isConference() && (sharedInstance = VoIPService.getSharedInstance()) != null && jLongValue == sharedInstance.convertingFromCallWithUserId) {
                    return;
                }
                try {
                    ArrayList<TLRPC.Dialog> allDialogs = this.accountInstance.getMessagesController().getAllDialogs();
                    if (allDialogs != null) {
                        int size = allDialogs.size();
                        int i7 = 0;
                        while (true) {
                            if (i7 >= size) {
                                break;
                            }
                            TLRPC.Dialog dialog = allDialogs.get(i7);
                            i7++;
                            if (dialog.f1616id == jLongValue) {
                                i5 = 1;
                                break;
                            }
                        }
                    }
                } catch (Exception unused) {
                }
                if (DialogObject.isUserDialog(jLongValue)) {
                    TLRPC.User user = this.accountInstance.getMessagesController().getUser(l2);
                    if (user != null) {
                        if (this.call.call.participants_count < 250 || UserObject.isContact(user) || user.verified || i5 != 0) {
                            getUndoView().showWithAction(0L, 44, user, this.currentChat, (Runnable) null, (Runnable) null);
                            return;
                        }
                        return;
                    }
                    return;
                }
                TLRPC.Chat chat = this.accountInstance.getMessagesController().getChat(Long.valueOf(-jLongValue));
                if (chat != null) {
                    if (this.call.call.participants_count < 250 || !ChatObject.isNotInChat(chat) || chat.verified || i5 != 0) {
                        getUndoView().showWithAction(0L, 44, chat, this.currentChat, (Runnable) null, (Runnable) null);
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        if (i == NotificationCenter.groupCallSpeakingUsersUpdated) {
            GroupCallRenderersContainer groupCallRenderersContainer = this.renderersContainer;
            if (groupCallRenderersContainer.inFullscreenMode && this.call != null) {
                boolean zAutoPinEnabled = groupCallRenderersContainer.autoPinEnabled();
                ChatObject.Call call3 = this.call;
                if (call3 != null) {
                    GroupCallRenderersContainer groupCallRenderersContainer2 = this.renderersContainer;
                    if (groupCallRenderersContainer2.inFullscreenMode && (videoParticipant = groupCallRenderersContainer2.fullscreenParticipant) != null && call3.participants.get(MessageObject.getPeerId(videoParticipant.participant.peer)) == null) {
                        zAutoPinEnabled = true;
                    }
                }
                if (zAutoPinEnabled) {
                    ChatObject.VideoParticipant videoParticipant2 = null;
                    for (int i8 = 0; i8 < this.visibleVideoParticipants.size(); i8++) {
                        ChatObject.VideoParticipant videoParticipant3 = (ChatObject.VideoParticipant) this.visibleVideoParticipants.get(i8);
                        if (this.call.currentSpeakingPeers.get(MessageObject.getPeerId(videoParticipant3.participant.peer), null) != null) {
                            TLRPC.GroupCallParticipant groupCallParticipant3 = videoParticipant3.participant;
                            if (!groupCallParticipant3.muted_by_you && this.renderersContainer.fullscreenPeerId != MessageObject.getPeerId(groupCallParticipant3.peer)) {
                                videoParticipant2 = videoParticipant3;
                            }
                        }
                    }
                    if (videoParticipant2 != null) {
                        fullscreenFor(videoParticipant2);
                    }
                }
            }
            this.renderersContainer.setVisibleParticipant(true);
            updateSubtitle();
            return;
        }
        if (i == NotificationCenter.webRtcMicAmplitudeEvent) {
            setMicAmplitude(((Float) objArr[0]).floatValue());
            return;
        }
        if (i == NotificationCenter.needShowAlert) {
            if (((Integer) objArr[0]).intValue() == 6) {
                String str = (String) objArr[1];
                if ("GROUPCALL_PARTICIPANTS_TOO_MUCH".equals(str)) {
                    if (ChatObject.isChannelOrGiga(this.currentChat)) {
                        string = LocaleController.getString(C2702R.string.VoipChannelTooMuch);
                    } else {
                        string = LocaleController.getString(C2702R.string.VoipGroupTooMuch);
                    }
                } else if (!"ANONYMOUS_CALLS_DISABLED".equals(str) && !"GROUPCALL_ANONYMOUS_FORBIDDEN".equals(str)) {
                    string = LocaleController.getString(C2702R.string.ErrorOccurred) + "\n" + str;
                } else if (ChatObject.isChannelOrGiga(this.currentChat)) {
                    string = LocaleController.getString(C2702R.string.VoipChannelJoinAnonymousAdmin);
                } else {
                    string = LocaleController.getString(C2702R.string.VoipGroupJoinAnonymousAdmin);
                }
                AlertDialog.Builder builderCreateSimpleAlert = AlertsCreator.createSimpleAlert(getContext(), LocaleController.getString(C2702R.string.VoipGroupVoiceChat), string);
                builderCreateSimpleAlert.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda37
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        this.f$0.lambda$didReceivedNotification$2(dialogInterface);
                    }
                });
                try {
                    builderCreateSimpleAlert.show();
                    return;
                } catch (Exception e) {
                    FileLog.m1093e(e);
                    return;
                }
            }
            return;
        }
        if (i == NotificationCenter.didEndCall) {
            if (VoIPService.getSharedInstance() == null) {
                lambda$new$0();
                return;
            }
            return;
        }
        if (i == NotificationCenter.chatInfoDidLoad) {
            TLRPC.ChatFull chatFull = (TLRPC.ChatFull) objArr[0];
            if (chatFull.f1611id == getChatId()) {
                updateItems();
                updateState(isShowing(), false);
            }
            long peerId = MessageObject.getPeerId(this.selfPeer);
            ChatObject.Call call4 = this.call;
            if (call4 == null || chatFull.f1611id != (-peerId) || (groupCallParticipant2 = (TLRPC.GroupCallParticipant) call4.participants.get(peerId)) == null) {
                return;
            }
            groupCallParticipant2.about = chatFull.about;
            applyCallParticipantUpdates(true);
            AndroidUtilities.updateVisibleRows(this.listView);
            if (this.currentOptionsLayout != null) {
                while (i5 < this.currentOptionsLayout.getChildCount()) {
                    View childAt2 = this.currentOptionsLayout.getChildAt(i5);
                    if ((childAt2 instanceof ActionBarMenuSubItem) && childAt2.getTag() != null && ((Integer) childAt2.getTag()).intValue() == 10) {
                        ((ActionBarMenuSubItem) childAt2).setTextAndIcon(LocaleController.getString(TextUtils.isEmpty(groupCallParticipant2.about) ? C2702R.string.VoipAddDescription : C2702R.string.VoipEditDescription), TextUtils.isEmpty(groupCallParticipant2.about) ? C2702R.drawable.msg_addbio : C2702R.drawable.msg_info);
                    }
                    i5++;
                }
                return;
            }
            return;
        }
        if (i == NotificationCenter.didLoadChatAdmins) {
            if (((Long) objArr[0]).longValue() == getChatId()) {
                updateItems();
                updateState(isShowing(), false);
                return;
            }
            return;
        }
        if (i == NotificationCenter.applyGroupCallVisibleParticipants) {
            int childCount2 = this.listView.getChildCount();
            long jLongValue2 = ((Long) objArr[0]).longValue();
            while (i5 < childCount2) {
                RecyclerView.ViewHolder viewHolderFindContainingViewHolder = this.listView.findContainingViewHolder(this.listView.getChildAt(i5));
                if (viewHolderFindContainingViewHolder != null) {
                    View view = viewHolderFindContainingViewHolder.itemView;
                    if (view instanceof GroupCallUserCell) {
                        GroupCallUserCell groupCallUserCell = (GroupCallUserCell) view;
                        if (groupCallUserCell.getParticipant() != null) {
                            groupCallUserCell.getParticipant().lastVisibleDate = jLongValue2;
                        }
                    }
                }
                i5++;
            }
            return;
        }
        if (i == NotificationCenter.userInfoDidLoad) {
            Long l3 = (Long) objArr[0];
            long peerId2 = MessageObject.getPeerId(this.selfPeer);
            if (this.call == null || peerId2 != l3.longValue() || (groupCallParticipant = (TLRPC.GroupCallParticipant) this.call.participants.get(peerId2)) == null) {
                return;
            }
            groupCallParticipant.about = ((TLRPC.UserFull) objArr[1]).about;
            applyCallParticipantUpdates(true);
            AndroidUtilities.updateVisibleRows(this.listView);
            if (this.currentOptionsLayout != null) {
                while (i5 < this.currentOptionsLayout.getChildCount()) {
                    View childAt3 = this.currentOptionsLayout.getChildAt(i5);
                    if ((childAt3 instanceof ActionBarMenuSubItem) && childAt3.getTag() != null && ((Integer) childAt3.getTag()).intValue() == 10) {
                        ((ActionBarMenuSubItem) childAt3).setTextAndIcon(LocaleController.getString(TextUtils.isEmpty(groupCallParticipant.about) ? C2702R.string.VoipAddBio : C2702R.string.VoipEditBio), TextUtils.isEmpty(groupCallParticipant.about) ? C2702R.drawable.msg_addbio : C2702R.drawable.msg_info);
                    }
                    i5++;
                }
                return;
            }
            return;
        }
        if (i == NotificationCenter.mainUserInfoChanged) {
            applyCallParticipantUpdates(true);
            AndroidUtilities.updateVisibleRows(this.listView);
            return;
        }
        if (i == NotificationCenter.updateInterfaces) {
            int iIntValue = ((Integer) objArr[0]).intValue();
            if ((MessagesController.UPDATE_MASK_CHAT_NAME & iIntValue) != 0) {
                applyCallParticipantUpdates(true);
            }
            if ((MessagesController.UPDATE_MASK_CHAT_NAME & iIntValue) == 0 && (iIntValue & MessagesController.UPDATE_MASK_EMOJI_STATUS) == 0) {
                return;
            }
            AndroidUtilities.updateVisibleRows(this.listView);
            return;
        }
        if (i == NotificationCenter.groupCallScreencastStateChanged) {
            PrivateVideoPreviewDialog privateVideoPreviewDialog = this.previewDialog;
            if (privateVideoPreviewDialog != null) {
                privateVideoPreviewDialog.dismiss(true, true);
            }
            updateItems();
            return;
        }
        if (i == NotificationCenter.conferenceEmojiUpdated) {
            VoIPService sharedInstance2 = VoIPService.getSharedInstance();
            CallEncryptionCellDrawable callEncryptionCellDrawable = this.encryptionDrawable;
            if (sharedInstance2 != null && (conferenceCall = sharedInstance2.conference) != null) {
                emojis = conferenceCall.getEmojis();
            }
            callEncryptionCellDrawable.setEmojis(emojis);
        }
    }

    public /* synthetic */ void lambda$didReceivedNotification$1() {
        if (isStillConnecting()) {
            updateState(true, false);
        }
    }

    public /* synthetic */ void lambda$didReceivedNotification$2(DialogInterface dialogInterface) {
        lambda$new$0();
    }

    private void setMicAmplitude(float f) {
        TLRPC.GroupCallParticipant groupCallParticipant;
        RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition;
        if (VoIPService.getSharedInstance() == null || VoIPService.getSharedInstance().isMicMute()) {
            f = 0.0f;
        }
        setAmplitude(4000.0f * f);
        ChatObject.Call call = this.call;
        if (call == null || this.listView == null || (groupCallParticipant = (TLRPC.GroupCallParticipant) call.participants.get(MessageObject.getPeerId(this.selfPeer))) == null) {
            return;
        }
        if (!this.renderersContainer.inFullscreenMode) {
            int iIndexOf = (this.delayedGroupCallUpdated ? this.oldParticipants : this.call.visibleParticipants).indexOf(groupCallParticipant);
            if (iIndexOf >= 0 && (viewHolderFindViewHolderForAdapterPosition = this.listView.findViewHolderForAdapterPosition(iIndexOf + this.listAdapter.usersStartRow)) != null) {
                View view = viewHolderFindViewHolderForAdapterPosition.itemView;
                if (view instanceof GroupCallUserCell) {
                    ((GroupCallUserCell) view).setAmplitude(f * 15.0f);
                    if (viewHolderFindViewHolderForAdapterPosition.itemView == this.scrimView && !this.contentFullyOverlayed) {
                        this.containerView.invalidate();
                    }
                }
            }
        } else {
            for (int i = 0; i < this.fullscreenUsersListView.getChildCount(); i++) {
                GroupCallFullscreenAdapter.GroupCallUserCell groupCallUserCell = (GroupCallFullscreenAdapter.GroupCallUserCell) this.fullscreenUsersListView.getChildAt(i);
                if (MessageObject.getPeerId(groupCallUserCell.getParticipant().peer) == MessageObject.getPeerId(groupCallParticipant.peer)) {
                    groupCallUserCell.setAmplitude(f * 15.0f);
                }
            }
        }
        this.renderersContainer.setAmplitude(groupCallParticipant, f * 15.0f);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:375:0x023b  */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void applyCallParticipantUpdates(boolean r26) {
        /*
            Method dump skipped, instruction units count: 717
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.GroupCallActivity.applyCallParticipantUpdates(boolean):void");
    }

    private void updateVideoParticipantList() {
        this.visibleVideoParticipants.clear();
        if (isTabletMode) {
            if (this.renderersContainer.inFullscreenMode) {
                this.visibleVideoParticipants.addAll(this.call.visibleVideoParticipants);
                ChatObject.VideoParticipant videoParticipant = this.renderersContainer.fullscreenParticipant;
                if (videoParticipant != null) {
                    this.visibleVideoParticipants.remove(videoParticipant);
                    return;
                }
                return;
            }
            return;
        }
        this.visibleVideoParticipants.addAll(this.call.visibleVideoParticipants);
    }

    private void updateRecordCallText() {
        if (this.call == null) {
            return;
        }
        int currentTime = this.accountInstance.getConnectionsManager().getCurrentTime();
        ChatObject.Call call = this.call;
        int i = currentTime - call.call.record_start_date;
        if (call.recording) {
            this.recordItem.setSubtext(AndroidUtilities.formatDuration(i, false));
        } else {
            this.recordItem.setSubtext(null);
        }
    }

    public void updateItems() {
        TLObject chat;
        ChatObject.Call call;
        TLRPC.GroupCall groupCall;
        TLRPC.GroupCall groupCall2;
        TLRPC.Chat chat2;
        TLRPC.Chat chat3;
        ChatObject.Call call2 = this.call;
        if (call2 == null || call2.isScheduled()) {
            this.pipItem.setVisibility(4);
            this.screenShareItem.setVisibility(8);
            if (this.call == null) {
                this.otherItem.setVisibility(8);
                return;
            }
        }
        if (this.changingPermissions) {
            return;
        }
        TLRPC.Chat chat4 = this.accountInstance.getMessagesController().getChat(Long.valueOf(getChatId()));
        if (chat4 != null) {
            this.currentChat = chat4;
        }
        if (ChatObject.canUserDoAdminAction(this.currentChat, 3) || (((!ChatObject.isChannel(this.currentChat) || ((chat3 = this.currentChat) != null && chat3.megagroup)) && (ChatObject.isPublic(this.currentChat) || ChatObject.canUserDoAdminAction(this.currentChat, 3))) || (ChatObject.isChannel(this.currentChat) && (chat2 = this.currentChat) != null && !chat2.megagroup && ChatObject.isPublic(chat2)))) {
            this.inviteItem.setVisibility(0);
        } else {
            this.inviteItem.setVisibility(8);
        }
        ChatObject.Call call3 = this.call;
        if (call3 != null && (groupCall2 = call3.call) != null && groupCall2.can_change_messages_enabled) {
            this.enableComments.setVisibility(groupCall2.messages_enabled ? 8 : 0);
            this.disableComments.setVisibility(this.call.call.messages_enabled ? 0 : 8);
        } else {
            this.enableComments.setVisibility(8);
            this.disableComments.setVisibility(8);
        }
        TLRPC.GroupCallParticipant groupCallParticipant = (TLRPC.GroupCallParticipant) this.call.participants.get(MessageObject.getPeerId(this.selfPeer));
        ChatObject.Call call4 = this.call;
        if (call4 == null || call4.isScheduled() || (groupCallParticipant != null && !groupCallParticipant.can_self_unmute && groupCallParticipant.muted)) {
            this.noiseItem.setVisibility(8);
        } else {
            this.noiseItem.setVisibility(0);
        }
        this.noiseItem.setIcon(SharedConfig.noiseSupression ? C2702R.drawable.msg_noise_on : C2702R.drawable.msg_noise_off);
        this.noiseItem.setSubtext(LocaleController.getString(SharedConfig.noiseSupression ? C2702R.string.VoipNoiseCancellationEnabled : C2702R.string.VoipNoiseCancellationDisabled));
        boolean z = true;
        if (canManageCall()) {
            this.leaveItem.setVisibility(0);
            this.editTitleItem.setVisibility(0);
            if (isRtmpStream()) {
                this.recordItem.setVisibility(0);
                this.screenItem.setVisibility(8);
            } else if (this.call.isScheduled()) {
                this.recordItem.setVisibility(8);
                this.screenItem.setVisibility(8);
            } else {
                this.recordItem.setVisibility(0);
            }
            if (isConference()) {
                this.recordItem.setVisibility(8);
                this.editTitleItem.setVisibility(8);
            }
            if (!this.call.canRecordVideo() || this.call.isScheduled() || isRtmpStream()) {
                this.screenItem.setVisibility(8);
            } else {
                this.screenItem.setVisibility(0);
            }
            this.screenShareItem.setVisibility(8);
            this.recordCallDrawable.setRecording(this.call.recording);
            if (this.call.recording) {
                if (this.updateCallRecordRunnable == null) {
                    Runnable runnable = new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda45
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$updateItems$3();
                        }
                    };
                    this.updateCallRecordRunnable = runnable;
                    AndroidUtilities.runOnUIThread(runnable, 1000L);
                }
                this.recordItem.setText(LocaleController.getString(C2702R.string.VoipGroupStopRecordCall));
            } else {
                Runnable runnable2 = this.updateCallRecordRunnable;
                if (runnable2 != null) {
                    AndroidUtilities.cancelRunOnUIThread(runnable2);
                    this.updateCallRecordRunnable = null;
                }
                this.recordItem.setText(LocaleController.getString(C2702R.string.VoipGroupRecordCall));
            }
            if (VoIPService.getSharedInstance() != null && VoIPService.getSharedInstance().getVideoState(true) == 2) {
                this.screenItem.setTextAndIcon(LocaleController.getString(C2702R.string.VoipChatStopScreenCapture), C2702R.drawable.msg_screencast_off);
            } else {
                this.screenItem.setTextAndIcon(LocaleController.getString(C2702R.string.VoipChatStartScreenCapture), C2702R.drawable.msg_screencast);
            }
            updateRecordCallText();
        } else {
            boolean z2 = (groupCallParticipant == null || groupCallParticipant.can_self_unmute || !groupCallParticipant.muted || canManageCall()) ? false : true;
            boolean z3 = VoIPService.getSharedInstance() != null && VoIPService.getSharedInstance().getVideoState(true) == 2;
            if (z2 || (!(this.call.canRecordVideo() || z3) || this.call.isScheduled() || isRtmpStream())) {
                this.screenShareItem.setVisibility(8);
                this.screenItem.setVisibility(8);
            } else if (z3) {
                this.screenShareItem.setVisibility(8);
                this.screenItem.setVisibility(0);
                this.screenItem.setTextAndIcon(LocaleController.getString(C2702R.string.VoipChatStopScreenCapture), C2702R.drawable.msg_screencast_off);
                this.screenItem.setContentDescription(LocaleController.getString(C2702R.string.VoipChatStopScreenCapture));
            } else {
                this.screenItem.setTextAndIcon(LocaleController.getString(C2702R.string.VoipChatStartScreenCapture), C2702R.drawable.msg_screencast);
                this.screenItem.setContentDescription(LocaleController.getString(C2702R.string.VoipChatStartScreenCapture));
                this.screenShareItem.setVisibility(8);
                this.screenItem.setVisibility(0);
            }
            this.leaveItem.setVisibility(8);
            this.editTitleItem.setVisibility(8);
            this.recordItem.setVisibility(8);
        }
        if (canManageCall() && this.call.call.can_change_join_muted && !isConference()) {
            this.permissionItem.setVisibility(0);
        } else {
            this.permissionItem.setVisibility(8);
        }
        ActionBarMenuItem actionBarMenuItem = this.otherItem;
        if (isConference() && ((call = this.call) == null || (groupCall = call.call) == null || !groupCall.creator)) {
            z = false;
        }
        actionBarMenuItem.setSubItemShown(4, z);
        this.soundItem.setVisibility((!isRtmpStream() || this.call.isScheduled()) ? 0 : 8);
        if (this.editTitleItem.getVisibility() == 0 || this.permissionItem.getVisibility() == 0 || this.inviteItem.getVisibility() == 0 || this.screenItem.getVisibility() == 0 || this.recordItem.getVisibility() == 0 || this.leaveItem.getVisibility() == 0) {
            this.soundItemDivider.setVisibility(0);
        } else {
            this.soundItemDivider.setVisibility(8);
        }
        if (((VoIPService.getSharedInstance() != null && VoIPService.getSharedInstance().hasFewPeers) || this.scheduleHasFewPeers) && !isRtmpStream() && this.selfPeer != null) {
            this.accountSelectCell.setVisibility(0);
            long peerId = MessageObject.getPeerId(this.selfPeer);
            if (DialogObject.isUserDialog(peerId)) {
                chat = this.accountInstance.getMessagesController().getUser(Long.valueOf(peerId));
            } else {
                chat = this.accountInstance.getMessagesController().getChat(Long.valueOf(-peerId));
            }
            this.accountSelectCell.setObject(chat);
        } else {
            this.accountSelectCell.setVisibility(8);
        }
        TLRPC.Chat chat5 = this.currentChat;
        if (chat5 != null && !ChatObject.isChannelOrGiga(chat5) && isRtmpStream() && this.inviteItem.getVisibility() == 8) {
            this.otherItem.setVisibility(8);
        } else {
            this.otherItem.setVisibility(0);
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.titleLayout.getLayoutParams();
        float f = 96;
        if (layoutParams.rightMargin != AndroidUtilities.m1081dp(f)) {
            layoutParams.rightMargin = AndroidUtilities.m1081dp(f);
            this.titleLayout.requestLayout();
        }
        ((FrameLayout.LayoutParams) this.menuItemsContainer.getLayoutParams()).rightMargin = 0;
        this.actionBar.setTitleRightMargin(AndroidUtilities.m1081dp(48.0f) * 2);
    }

    public /* synthetic */ void lambda$updateItems$3() {
        updateRecordCallText();
        AndroidUtilities.runOnUIThread(this.updateCallRecordRunnable, 1000L);
    }

    protected void makeFocusable(final BottomSheet bottomSheet, final AlertDialog alertDialog, final EditTextBoldCursor editTextBoldCursor, final boolean z) {
        if (this.enterEventSent) {
            return;
        }
        BaseFragment baseFragment = (BaseFragment) this.parentActivity.getActionBarLayout().getFragmentStack().get(this.parentActivity.getActionBarLayout().getFragmentStack().size() - 1);
        if (baseFragment instanceof ChatActivity) {
            boolean zNeedEnterText = ((ChatActivity) baseFragment).needEnterText();
            this.enterEventSent = true;
            this.anyEnterEventSent = true;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda50
                @Override // java.lang.Runnable
                public final void run() {
                    GroupCallActivity.$r8$lambda$Fa3ZPhFXyZdUSkEgn5plm6EMoSA(bottomSheet, editTextBoldCursor, z, alertDialog);
                }
            }, zNeedEnterText ? 200L : 0L);
            return;
        }
        this.enterEventSent = true;
        this.anyEnterEventSent = true;
        if (bottomSheet != null) {
            bottomSheet.setFocusable(true);
        } else if (alertDialog != null) {
            alertDialog.setFocusable(true);
        }
        if (z) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda51
                @Override // java.lang.Runnable
                public final void run() {
                    GroupCallActivity.m13739$r8$lambda$93eSzpgS8XVzTm6hjP7Nm8Wk7U(editTextBoldCursor);
                }
            }, 100L);
        }
    }

    public static /* synthetic */ void $r8$lambda$Fa3ZPhFXyZdUSkEgn5plm6EMoSA(BottomSheet bottomSheet, final EditTextBoldCursor editTextBoldCursor, boolean z, AlertDialog alertDialog) {
        if (bottomSheet != null && !bottomSheet.isDismissed()) {
            bottomSheet.setFocusable(true);
            editTextBoldCursor.requestFocus();
            if (z) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda61
                    @Override // java.lang.Runnable
                    public final void run() {
                        AndroidUtilities.showKeyboard(editTextBoldCursor);
                    }
                });
                return;
            }
            return;
        }
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        alertDialog.setFocusable(true);
        editTextBoldCursor.requestFocus();
        if (z) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda62
                @Override // java.lang.Runnable
                public final void run() {
                    AndroidUtilities.showKeyboard(editTextBoldCursor);
                }
            });
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$93eSzpgS8X-VzTm6hjP7Nm8Wk7U */
    public static /* synthetic */ void m13739$r8$lambda$93eSzpgS8XVzTm6hjP7Nm8Wk7U(EditTextBoldCursor editTextBoldCursor) {
        editTextBoldCursor.requestFocus();
        AndroidUtilities.showKeyboard(editTextBoldCursor);
    }

    public static void create(LaunchActivity launchActivity, AccountInstance accountInstance, TLRPC.Chat chat, TLRPC.InputPeer inputPeer, boolean z, String str) {
        if (groupCallInstance == null) {
            if (inputPeer == null && VoIPService.getSharedInstance() == null) {
                return;
            }
            if (inputPeer != null) {
                groupCallInstance = new GroupCallActivity(launchActivity, accountInstance, accountInstance.getMessagesController().getGroupCall(chat.f1610id, false), chat, inputPeer, z, str);
            } else {
                ChatObject.Call call = VoIPService.getSharedInstance().groupCall;
                if (call == null) {
                    return;
                }
                TLRPC.Chat chat2 = accountInstance.getMessagesController().getChat(Long.valueOf(call.chatId));
                call.addSelfDummyParticipant(true);
                groupCallInstance = new GroupCallActivity(launchActivity, accountInstance, call, chat2, null, z, str);
            }
            groupCallInstance.parentActivity = launchActivity;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    GroupCallActivity.$r8$lambda$D1GP7YTkxWHEzbMV1cSFRN_FLE4();
                }
            });
        }
    }

    public static /* synthetic */ void $r8$lambda$D1GP7YTkxWHEzbMV1cSFRN_FLE4() {
        GroupCallActivity groupCallActivity = groupCallInstance;
        if (groupCallActivity != null) {
            groupCallActivity.show();
        }
    }

    private GroupCallActivity(final Activity activity, final AccountInstance accountInstance, final ChatObject.Call call, final TLRPC.Chat chat, TLRPC.InputPeer inputPeer, boolean z, String str) {
        int i;
        String string;
        int i2;
        final Activity activity2;
        TLRPC.Chat chat2;
        ConferenceCall conferenceCall;
        super(activity, true, true, null);
        this.callMessageEnterContainerBgPaint = new Paint(1);
        this.undoView = new UndoView[2];
        this.visibleVideoParticipants = new ArrayList();
        this.rect = new RectF();
        this.listViewBackgroundPaint = new Paint(1);
        this.oldParticipants = new ArrayList();
        this.oldVideoParticipants = new ArrayList();
        this.oldInvited = new ArrayList();
        this.oldShadyJoin = new ArrayList();
        this.oldShadyLeft = new ArrayList();
        this.windowInsetsStateHolder = new WindowInsetsStateHolder(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.checkInsets();
            }
        });
        this.muteButtonState = 0;
        this.animatingToFullscreenExpand = false;
        this.paint = new Paint(7);
        this.paintTmp = new Paint(7);
        this.states = new WeavingState[8];
        this.switchProgress = 1.0f;
        this.invalidateColors = true;
        this.colorsTmp = new int[4];
        this.attachedRenderers = new ArrayList();
        this.attachedRenderersTmp = new ArrayList();
        this.wasExpandBigSize = Boolean.TRUE;
        this.cellFlickerDrawable = new CellFlickerDrawable();
        this.statusIconPool = new ArrayList();
        this.onUserLeaveHintListener = new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.onUserLeaveHint();
            }
        };
        this.updateSchedeulRunnable = new Runnable() { // from class: org.telegram.ui.GroupCallActivity.1
            RunnableC55471() {
            }

            @Override // java.lang.Runnable
            public void run() {
                int i3;
                if (GroupCallActivity.this.scheduleTimeTextView == null || GroupCallActivity.this.isDismissed()) {
                    return;
                }
                GroupCallActivity groupCallActivity = GroupCallActivity.this;
                ChatObject.Call call2 = groupCallActivity.call;
                if (call2 != null) {
                    i3 = call2.call.schedule_date;
                } else {
                    i3 = groupCallActivity.scheduleStartAt;
                }
                if (i3 == 0) {
                    return;
                }
                int currentTime = i3 - GroupCallActivity.this.accountInstance.getConnectionsManager().getCurrentTime();
                if (currentTime >= 86400) {
                    GroupCallActivity.this.scheduleTimeTextView.setText(LocaleController.formatPluralString("Days", Math.round(currentTime / 86400.0f), new Object[0]));
                } else {
                    GroupCallActivity.this.scheduleTimeTextView.setText(AndroidUtilities.formatFullDuration(Math.abs(currentTime)));
                    if (currentTime < 0 && GroupCallActivity.this.scheduleStartInTextView.getTag() == null) {
                        GroupCallActivity.this.scheduleStartInTextView.setTag(1);
                        GroupCallActivity.this.scheduleStartInTextView.setText(LocaleController.getString(C2702R.string.VoipChatLateBy));
                    }
                }
                GroupCallActivity.this.scheduleStartAtTextView.setText(LocaleController.formatStartsTime(i3, 3));
                AndroidUtilities.runOnUIThread(GroupCallActivity.this.updateSchedeulRunnable, 1000L);
            }
        };
        this.unmuteRunnable = new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda28
            @Override // java.lang.Runnable
            public final void run() {
                GroupCallActivity.m13752$r8$lambda$_YrqkqkohdH6mXwgsnpz0Pkz9U();
            }
        };
        this.pressRunnable = new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda29
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.pressRunnableImpl();
            }
        };
        this.needTextureLightning = false;
        this.updateTextureLightningRunnable = new RunnableC55582();
        this.visiblePeerIds = new LongSparseIntArray();
        this.gradientColors = new int[2];
        this.listViewVideoVisibility = true;
        this.invites = new String[2];
        this.popupAnimationIndex = -1;
        this.diffUtilsCallback = new DiffUtil.Callback() { // from class: org.telegram.ui.GroupCallActivity.65
            @Override // androidx.recyclerview.widget.DiffUtil.Callback
            public boolean areContentsTheSame(int i3, int i4) {
                return true;
            }

            C560865() {
            }

            @Override // androidx.recyclerview.widget.DiffUtil.Callback
            public int getOldListSize() {
                return GroupCallActivity.this.oldCount;
            }

            @Override // androidx.recyclerview.widget.DiffUtil.Callback
            public int getNewListSize() {
                return GroupCallActivity.this.listAdapter.rowsCount;
            }

            @Override // androidx.recyclerview.widget.DiffUtil.Callback
            public boolean areItemsTheSame(int i3, int i4) {
                if (GroupCallActivity.this.listAdapter.addMemberRow >= 0) {
                    if (i3 == GroupCallActivity.this.oldAddMemberRow && i4 == GroupCallActivity.this.listAdapter.addMemberRow) {
                        return true;
                    }
                    if ((i3 == GroupCallActivity.this.oldAddMemberRow && i4 != GroupCallActivity.this.listAdapter.addMemberRow) || (i3 != GroupCallActivity.this.oldAddMemberRow && i4 == GroupCallActivity.this.listAdapter.addMemberRow)) {
                        return false;
                    }
                }
                if (GroupCallActivity.this.listAdapter.conferenceAddPeopleRow >= 0) {
                    if (i3 == GroupCallActivity.this.oldConferenceAddPeopleRow && i4 == GroupCallActivity.this.listAdapter.conferenceAddPeopleRow) {
                        return true;
                    }
                    if ((i3 == GroupCallActivity.this.oldConferenceAddPeopleRow && i4 != GroupCallActivity.this.listAdapter.conferenceAddPeopleRow) || (i3 != GroupCallActivity.this.oldConferenceAddPeopleRow && i4 == GroupCallActivity.this.listAdapter.conferenceAddPeopleRow)) {
                        return false;
                    }
                }
                if (GroupCallActivity.this.listAdapter.conferenceShareLinkRow >= 0) {
                    if (i3 == GroupCallActivity.this.oldConferenceShareLinkRow && i4 == GroupCallActivity.this.listAdapter.conferenceShareLinkRow) {
                        return true;
                    }
                    if ((i3 == GroupCallActivity.this.oldConferenceShareLinkRow && i4 != GroupCallActivity.this.listAdapter.conferenceShareLinkRow) || (i3 != GroupCallActivity.this.oldConferenceShareLinkRow && i4 == GroupCallActivity.this.listAdapter.conferenceShareLinkRow)) {
                        return false;
                    }
                }
                if (GroupCallActivity.this.listAdapter.encryptionRow >= 0) {
                    if (i3 == GroupCallActivity.this.oldEncryptionRow && i4 == GroupCallActivity.this.listAdapter.encryptionRow) {
                        return true;
                    }
                    if ((i3 == GroupCallActivity.this.oldEncryptionRow && i4 != GroupCallActivity.this.listAdapter.encryptionRow) || (i3 != GroupCallActivity.this.oldEncryptionRow && i4 == GroupCallActivity.this.listAdapter.encryptionRow)) {
                        return false;
                    }
                }
                if (GroupCallActivity.this.listAdapter.videoNotAvailableRow >= 0) {
                    if (i3 == GroupCallActivity.this.oldVideoNotAvailableRow && i4 == GroupCallActivity.this.listAdapter.videoNotAvailableRow) {
                        return true;
                    }
                    if ((i3 == GroupCallActivity.this.oldVideoNotAvailableRow && i4 != GroupCallActivity.this.listAdapter.videoNotAvailableRow) || (i3 != GroupCallActivity.this.oldVideoNotAvailableRow && i4 == GroupCallActivity.this.listAdapter.videoNotAvailableRow)) {
                        return false;
                    }
                }
                if (GroupCallActivity.this.listAdapter.videoGridDividerRow >= 0 && GroupCallActivity.this.listAdapter.videoGridDividerRow == i4 && i3 == GroupCallActivity.this.oldVideoDividerRow) {
                    return true;
                }
                if (i3 == GroupCallActivity.this.oldCount - 1 && i4 == GroupCallActivity.this.listAdapter.rowsCount - 1) {
                    return true;
                }
                if (i3 != GroupCallActivity.this.oldCount - 1 && i4 != GroupCallActivity.this.listAdapter.rowsCount - 1) {
                    if (i4 >= GroupCallActivity.this.listAdapter.usersVideoGridStartRow && i4 < GroupCallActivity.this.listAdapter.usersVideoGridEndRow && i3 >= GroupCallActivity.this.oldUsersVideoStartRow && i3 < GroupCallActivity.this.oldUsersVideoEndRow) {
                        ChatObject.VideoParticipant videoParticipant = (ChatObject.VideoParticipant) GroupCallActivity.this.oldVideoParticipants.get(i3 - GroupCallActivity.this.oldUsersVideoStartRow);
                        GroupCallActivity groupCallActivity = GroupCallActivity.this;
                        return videoParticipant.equals((ChatObject.VideoParticipant) groupCallActivity.visibleVideoParticipants.get(i4 - groupCallActivity.listAdapter.usersVideoGridStartRow));
                    }
                    if (i4 >= GroupCallActivity.this.listAdapter.usersStartRow && i4 < GroupCallActivity.this.listAdapter.usersEndRow && i3 >= GroupCallActivity.this.oldUsersStartRow && i3 < GroupCallActivity.this.oldUsersEndRow) {
                        TLRPC.GroupCallParticipant groupCallParticipant = (TLRPC.GroupCallParticipant) GroupCallActivity.this.oldParticipants.get(i3 - GroupCallActivity.this.oldUsersStartRow);
                        GroupCallActivity groupCallActivity2 = GroupCallActivity.this;
                        return MessageObject.getPeerId(groupCallParticipant.peer) == MessageObject.getPeerId(groupCallActivity2.call.visibleParticipants.get(i4 - groupCallActivity2.listAdapter.usersStartRow).peer) && (i3 == i4 || groupCallParticipant.lastActiveDate == ((long) groupCallParticipant.active_date));
                    }
                    if (i4 >= GroupCallActivity.this.listAdapter.invitedStartRow && i4 < GroupCallActivity.this.listAdapter.invitedEndRow && i3 >= GroupCallActivity.this.oldInvitedStartRow && i3 < GroupCallActivity.this.oldInvitedEndRow) {
                        Long l = (Long) GroupCallActivity.this.oldInvited.get(i3 - GroupCallActivity.this.oldInvitedStartRow);
                        GroupCallActivity groupCallActivity3 = GroupCallActivity.this;
                        return l.equals(groupCallActivity3.call.invitedUsers.get(i4 - groupCallActivity3.listAdapter.invitedStartRow));
                    }
                    if (i4 >= GroupCallActivity.this.listAdapter.shadyJoinStartRow && i4 < GroupCallActivity.this.listAdapter.shadyJoinEndRow && i3 >= GroupCallActivity.this.oldShadyJoinStartRow && i3 < GroupCallActivity.this.oldShadyJoinEndRow) {
                        Long l2 = (Long) GroupCallActivity.this.oldShadyJoin.get(i3 - GroupCallActivity.this.oldShadyJoinStartRow);
                        GroupCallActivity groupCallActivity4 = GroupCallActivity.this;
                        return l2.equals(groupCallActivity4.call.shadyJoinParticipants.get(i4 - groupCallActivity4.listAdapter.shadyJoinStartRow));
                    }
                    if (i4 >= GroupCallActivity.this.listAdapter.shadyLeftStartRow && i4 < GroupCallActivity.this.listAdapter.shadyLeftEndRow && i3 >= GroupCallActivity.this.oldShadyLeftStartRow && i3 < GroupCallActivity.this.oldShadyLeftEndRow) {
                        Long l3 = (Long) GroupCallActivity.this.oldShadyLeft.get(i3 - GroupCallActivity.this.oldShadyLeftStartRow);
                        GroupCallActivity groupCallActivity5 = GroupCallActivity.this;
                        return l3.equals(groupCallActivity5.call.shadyLeftParticipants.get(i4 - groupCallActivity5.listAdapter.shadyLeftStartRow));
                    }
                }
                return false;
            }
        };
        CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.DEFAULT;
        this.animatorHideButtons = new BoolAnimator(2, this, cubicBezierInterpolator, 350L);
        this.animatorMessageIsEmpty = new BoolAnimator(3, this, cubicBezierInterpolator, 220L, true);
        this.animatorMessageInputHeight = new FactorAnimator(4, this, cubicBezierInterpolator, 350L);
        final GroupCallActivity groupCallActivity = this;
        groupCallActivity.animatorHasVideo = new BoolAnimator(5, this, cubicBezierInterpolator, 350L);
        AndroidUtilities.enableEdgeToEdge(groupCallActivity.getWindow());
        groupCallActivity.setOpenNoDelay(true);
        groupCallActivity.accountInstance = accountInstance;
        groupCallActivity.call = call;
        groupCallActivity.schedulePeer = inputPeer;
        groupCallActivity.currentChat = chat;
        groupCallActivity.scheduledHash = str;
        groupCallActivity.currentAccount = accountInstance.getCurrentAccount();
        groupCallActivity.scheduleHasFewPeers = z;
        groupCallActivity.resourcesProvider = new DarkBlueThemeResourcesProvider();
        groupCallActivity.smoothKeyboardAnimationEnabled = true;
        groupCallActivity.smoothKeyboardByBottom = true;
        groupCallActivity.maxGroupCallMessageLength = MessagesController.getInstance(groupCallActivity.currentAccount).config.groupCallMessageLengthLimit.get();
        groupCallActivity.fullWidth = true;
        isTabletMode = false;
        isLandscapeMode = false;
        paused = false;
        groupCallActivity.setDelegate(new BottomSheet.BottomSheetDelegateInterface() { // from class: org.telegram.ui.GroupCallActivity.3
            @Override // org.telegram.ui.ActionBar.BottomSheet.BottomSheetDelegateInterface
            public boolean canDismiss() {
                return true;
            }

            C55693() {
            }

            @Override // org.telegram.ui.ActionBar.BottomSheet.BottomSheetDelegateInterface
            public void onOpenAnimationEnd() {
                CountDownLatch groupCallBottomSheetLatch;
                VoIPService sharedInstance = VoIPService.getSharedInstance();
                if (sharedInstance != null && (groupCallBottomSheetLatch = sharedInstance.getGroupCallBottomSheetLatch()) != null) {
                    groupCallBottomSheetLatch.countDown();
                }
                if (GroupCallActivity.this.muteButtonState == 6) {
                    GroupCallActivity.this.showReminderHint();
                }
            }
        });
        groupCallActivity.drawDoubleNavigationBar = true;
        groupCallActivity.drawNavigationBar = true;
        if (Build.VERSION.SDK_INT >= 30) {
            groupCallActivity.getWindow().setNavigationBarColor(-16777216);
        }
        groupCallActivity.scrollNavBar = true;
        groupCallActivity.navBarColorKey = -1;
        groupCallActivity.scrimPaint = new Paint() { // from class: org.telegram.ui.GroupCallActivity.4
            C55804() {
            }

            @Override // android.graphics.Paint
            public void setAlpha(int i3) {
                super.setAlpha(i3);
                if (((BottomSheet) GroupCallActivity.this).containerView != null) {
                    ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
                }
            }
        };
        groupCallActivity.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda30
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f$0.lambda$new$9(dialogInterface);
            }
        });
        groupCallActivity.setDimBehindAlpha(75);
        ListAdapter listAdapter = groupCallActivity.new ListAdapter(activity);
        groupCallActivity.listAdapter = listAdapter;
        RecordStatusDrawable recordStatusDrawable = new RecordStatusDrawable(true);
        int i3 = Theme.key_voipgroup_speakingText;
        recordStatusDrawable.setColor(Theme.getColor(i3));
        recordStatusDrawable.start();
        C55915 c55915 = new ActionBar(activity) { // from class: org.telegram.ui.GroupCallActivity.5
            final /* synthetic */ RecordStatusDrawable val$recordStatusDrawable;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C55915(final Context activity3, RecordStatusDrawable recordStatusDrawable2) {
                super(activity3);
                recordStatusDrawable = recordStatusDrawable2;
            }

            @Override // android.view.View
            public void setAlpha(float f) {
                if (getAlpha() != f) {
                    super.setAlpha(f);
                    ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
                }
            }

            @Override // org.telegram.p026ui.ActionBar.ActionBar, android.view.ViewGroup, android.view.View
            protected void dispatchDraw(Canvas canvas) {
                super.dispatchDraw(canvas);
                if (getAdditionalSubtitleTextView().getVisibility() == 0) {
                    canvas.save();
                    canvas.translate(getSubtitleTextView().getLeft(), getSubtitleTextView().getY() - AndroidUtilities.m1081dp(1.0f));
                    recordStatusDrawable.setAlpha((int) (getAdditionalSubtitleTextView().getAlpha() * 255.0f));
                    recordStatusDrawable.draw(canvas);
                    canvas.restore();
                    invalidate();
                }
            }
        };
        groupCallActivity.actionBar = c55915;
        c55915.setSubtitle(_UrlKt.FRAGMENT_ENCODE_SET);
        c55915.getSubtitleTextView().setVisibility(0);
        c55915.createAdditionalSubtitleTextView();
        c55915.getAdditionalSubtitleTextView().setPadding(AndroidUtilities.m1081dp(24.0f), 0, 0, 0);
        AndroidUtilities.updateViewVisibilityAnimated(c55915.getAdditionalSubtitleTextView(), groupCallActivity.drawSpeakingSubtitle, 1.0f, false);
        c55915.getAdditionalSubtitleTextView().setTextColor(Theme.getColor(i3));
        int i4 = Theme.key_voipgroup_lastSeenTextUnscrolled;
        c55915.setSubtitleColor(Theme.getColor(i4));
        c55915.setBackButtonImage(C2702R.drawable.ic_ab_back);
        c55915.setOccupyStatusBar(false);
        c55915.setAllowOverlayTitle(false);
        int i5 = Theme.key_voipgroup_actionBarItems;
        c55915.setItemsColor(Theme.getColor(i5), false);
        c55915.setItemsBackgroundColor(Theme.getColor(Theme.key_actionBarActionModeDefaultSelector), false);
        c55915.setTitleColor(Theme.getColor(i5));
        c55915.setSubtitleColor(Theme.getColor(i4));
        c55915.setActionBarMenuOnItemClick(groupCallActivity.new C56026(activity3));
        TLRPC.InputPeer groupCallPeer = inputPeer != null ? inputPeer : VoIPService.getSharedInstance().getGroupCallPeer();
        if (groupCallPeer == null) {
            TLRPC.TL_peerUser tL_peerUser = new TLRPC.TL_peerUser();
            groupCallActivity.selfPeer = tL_peerUser;
            tL_peerUser.user_id = accountInstance.getUserConfig().getClientUserId();
            i = i5;
        } else if (groupCallPeer instanceof TLRPC.TL_inputPeerChannel) {
            TLRPC.TL_peerChannel tL_peerChannel = new TLRPC.TL_peerChannel();
            groupCallActivity.selfPeer = tL_peerChannel;
            i = i5;
            tL_peerChannel.channel_id = groupCallPeer.channel_id;
        } else {
            i = i5;
            if (groupCallPeer instanceof TLRPC.TL_inputPeerUser) {
                TLRPC.TL_peerUser tL_peerUser2 = new TLRPC.TL_peerUser();
                groupCallActivity.selfPeer = tL_peerUser2;
                tL_peerUser2.user_id = groupCallPeer.user_id;
            } else if (groupCallPeer instanceof TLRPC.TL_inputPeerChat) {
                TLRPC.TL_peerChat tL_peerChat = new TLRPC.TL_peerChat();
                groupCallActivity.selfPeer = tL_peerChat;
                tL_peerChat.chat_id = groupCallPeer.chat_id;
            }
        }
        VoIPService.audioLevelsCallback = new NativeInstance.AudioLevelsCallback() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda31
            @Override // org.telegram.messenger.voip.NativeInstance.AudioLevelsCallback
            public final void run(int[] iArr, float[] fArr, boolean[] zArr) {
                this.f$0.lambda$new$10(iArr, fArr, zArr);
            }
        };
        accountInstance.getNotificationCenter().addObserver(groupCallActivity, NotificationCenter.groupCallUpdated);
        accountInstance.getNotificationCenter().addObserver(groupCallActivity, NotificationCenter.needShowAlert);
        accountInstance.getNotificationCenter().addObserver(groupCallActivity, NotificationCenter.chatInfoDidLoad);
        accountInstance.getNotificationCenter().addObserver(groupCallActivity, NotificationCenter.didLoadChatAdmins);
        accountInstance.getNotificationCenter().addObserver(groupCallActivity, NotificationCenter.applyGroupCallVisibleParticipants);
        accountInstance.getNotificationCenter().addObserver(groupCallActivity, NotificationCenter.userInfoDidLoad);
        accountInstance.getNotificationCenter().addObserver(groupCallActivity, NotificationCenter.mainUserInfoChanged);
        accountInstance.getNotificationCenter().addObserver(groupCallActivity, NotificationCenter.updateInterfaces);
        accountInstance.getNotificationCenter().addObserver(groupCallActivity, NotificationCenter.groupCallScreencastStateChanged);
        accountInstance.getNotificationCenter().addObserver(groupCallActivity, NotificationCenter.groupCallSpeakingUsersUpdated);
        accountInstance.getNotificationCenter().addObserver(groupCallActivity, NotificationCenter.conferenceEmojiUpdated);
        NotificationCenter.getGlobalInstance().addObserver(groupCallActivity, NotificationCenter.webRtcMicAmplitudeEvent);
        NotificationCenter.getGlobalInstance().addObserver(groupCallActivity, NotificationCenter.didEndCall);
        groupCallActivity.shadowDrawable = activity3.getResources().getDrawable(C2702R.drawable.sheet_shadow_round).mutate();
        RLottieDrawable rLottieDrawable = new RLottieDrawable(C2702R.raw.voip_filled, _UrlKt.FRAGMENT_ENCODE_SET + C2702R.raw.voip_filled, AndroidUtilities.m1081dp(46.0f), AndroidUtilities.m1081dp(46.0f), true, null);
        groupCallActivity.bigMicDrawable = rLottieDrawable;
        groupCallActivity.handDrawables = new RLottieDrawable(C2702R.raw.hand_2, _UrlKt.FRAGMENT_ENCODE_SET + C2702R.raw.hand_2, AndroidUtilities.m1081dp(46.0f), AndroidUtilities.m1081dp(46.0f), true, null);
        C56107 c56107 = new SizeNotifierFrameLayout(activity3) { // from class: org.telegram.ui.GroupCallActivity.7
            private int lastSize;
            boolean localHasVideo;
            private boolean updateRenderers;
            boolean wasLayout;
            private boolean ignoreLayout = false;
            private RectF rect = new RectF();
            HashMap listCells = new HashMap();

            C56107(final Context activity3) {
                super(activity3);
                this.ignoreLayout = false;
                this.rect = new RectF();
                this.listCells = new HashMap();
            }

            @Override // org.telegram.p026ui.Components.SizeNotifierFrameLayout, android.view.ViewGroup, android.view.View
            protected void onAttachedToWindow() {
                super.onAttachedToWindow();
                GroupCallActivity.this.reactionEffectImageReceiver.onAttachedToWindow();
            }

            @Override // org.telegram.p026ui.Components.SizeNotifierFrameLayout, android.view.ViewGroup, android.view.View
            protected void onDetachedFromWindow() {
                super.onDetachedFromWindow();
                GroupCallActivity.this.reactionEffectImageReceiver.onDetachedFromWindow();
            }

            @Override // android.widget.FrameLayout, android.view.View
            protected void onMeasure(int i6, int i7) {
                int iM1081dp;
                float f;
                int iM1081dp2;
                int size = View.MeasureSpec.getSize(i7);
                this.ignoreLayout = true;
                boolean z2 = View.MeasureSpec.getSize(i6) > size && !AndroidUtilities.isTablet();
                GroupCallActivity.this.renderersContainer.listWidth = View.MeasureSpec.getSize(i6);
                boolean z3 = AndroidUtilities.isTablet() && View.MeasureSpec.getSize(i6) > size && !GroupCallActivity.this.isRtmpStream();
                if (GroupCallActivity.isLandscapeMode != z2) {
                    GroupCallActivity.isLandscapeMode = z2;
                    if (GroupCallActivity.this.muteButton.getMeasuredWidth() == 0) {
                        int i8 = GroupCallActivity.this.muteButton.getLayoutParams().width;
                    }
                    GroupCallActivity.this.invalidateLayoutFullscreen();
                    GroupCallActivity.this.layoutManager.setSpanCount(GroupCallActivity.isLandscapeMode ? 6 : 2);
                    GroupCallActivity.this.listView.invalidateItemDecorations();
                    GroupCallActivity.this.fullscreenUsersListView.invalidateItemDecorations();
                    this.updateRenderers = true;
                    if (GroupCallActivity.this.scheduleInfoTextView != null) {
                        GroupCallActivity.this.scheduleInfoTextView.setVisibility(!GroupCallActivity.isLandscapeMode ? 0 : 8);
                    }
                    if (GroupCallActivity.this.isRtmpLandscapeMode() == z2 && GroupCallActivity.this.isRtmpStream() && !GroupCallActivity.this.renderersContainer.inFullscreenMode && !GroupCallActivity.this.call.visibleVideoParticipants.isEmpty()) {
                        GroupCallActivity groupCallActivity2 = GroupCallActivity.this;
                        groupCallActivity2.fullscreenFor(groupCallActivity2.call.visibleVideoParticipants.get(0));
                        GroupCallActivity.this.renderersContainer.delayHideUi();
                    }
                }
                if (GroupCallActivity.isTabletMode != z3) {
                    GroupCallActivity.isTabletMode = z3;
                    GroupCallActivity.this.tabletVideoGridView.setVisibility(z3 ? 0 : 8);
                    GroupCallActivity.this.listView.invalidateItemDecorations();
                    GroupCallActivity.this.fullscreenUsersListView.invalidateItemDecorations();
                    this.updateRenderers = true;
                }
                if (this.updateRenderers) {
                    GroupCallActivity.this.applyCallParticipantUpdates(true);
                    GroupCallActivity.this.listAdapter.notifyDataSetChanged();
                    GroupCallActivity groupCallActivity3 = GroupCallActivity.this;
                    groupCallActivity3.fullscreenAdapter.update(false, groupCallActivity3.tabletVideoGridView);
                    if (GroupCallActivity.isTabletMode) {
                        GroupCallActivity groupCallActivity4 = GroupCallActivity.this;
                        groupCallActivity4.tabletGridAdapter.update(false, groupCallActivity4.tabletVideoGridView);
                    }
                    GroupCallActivity.this.tabletVideoGridView.setVisibility(GroupCallActivity.isTabletMode ? 0 : 8);
                    GroupCallActivity groupCallActivity5 = GroupCallActivity.this;
                    groupCallActivity5.tabletGridAdapter.setVisibility(groupCallActivity5.tabletVideoGridView, GroupCallActivity.isTabletMode && !groupCallActivity5.renderersContainer.inFullscreenMode, true);
                    GroupCallActivity groupCallActivity6 = GroupCallActivity.this;
                    groupCallActivity6.listViewVideoVisibility = !GroupCallActivity.isTabletMode || groupCallActivity6.renderersContainer.inFullscreenMode;
                    boolean z4 = !GroupCallActivity.isTabletMode && GroupCallActivity.this.renderersContainer.inFullscreenMode;
                    GroupCallActivity groupCallActivity7 = GroupCallActivity.this;
                    groupCallActivity7.fullscreenAdapter.setVisibility(groupCallActivity7.fullscreenUsersListView, z4);
                    GroupCallActivity.this.fullscreenUsersListView.setVisibility(z4 ? 0 : 8);
                    GroupCallActivity.this.listView.setVisibility((GroupCallActivity.isTabletMode || !GroupCallActivity.this.renderersContainer.inFullscreenMode) ? 0 : 8);
                    GroupCallActivity.this.layoutManager.setSpanCount(GroupCallActivity.isLandscapeMode ? 6 : 2);
                    GroupCallActivity.this.updateState(false, false);
                    GroupCallActivity.this.listView.invalidateItemDecorations();
                    GroupCallActivity.this.fullscreenUsersListView.invalidateItemDecorations();
                    AndroidUtilities.updateVisibleRows(GroupCallActivity.this.listView);
                    this.updateRenderers = false;
                    GroupCallActivity.this.attachedRenderersTmp.clear();
                    GroupCallActivity.this.attachedRenderersTmp.addAll(GroupCallActivity.this.attachedRenderers);
                    GroupCallActivity.this.renderersContainer.setIsTablet(GroupCallActivity.isTabletMode);
                    for (int i9 = 0; i9 < GroupCallActivity.this.attachedRenderersTmp.size(); i9++) {
                        ((GroupCallMiniTextureView) GroupCallActivity.this.attachedRenderersTmp.get(i9)).updateAttachState(true);
                    }
                }
                int paddingTop = size - getPaddingTop();
                if (GroupCallActivity.this.isRtmpStream()) {
                    iM1081dp = AndroidUtilities.m1081dp(72.0f);
                } else {
                    iM1081dp = AndroidUtilities.m1081dp(245.0f);
                }
                int i10 = paddingTop - iM1081dp;
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) GroupCallActivity.this.renderersContainer.getLayoutParams();
                if (GroupCallActivity.isTabletMode) {
                    layoutParams.topMargin = ActionBar.getCurrentActionBarHeight();
                } else {
                    layoutParams.topMargin = 0;
                }
                for (int i11 = 0; i11 < 2; i11++) {
                    FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) GroupCallActivity.this.undoView[i11].getLayoutParams();
                    if (GroupCallActivity.isTabletMode) {
                        layoutParams2.rightMargin = AndroidUtilities.m1081dp(328.0f);
                    } else {
                        layoutParams2.rightMargin = AndroidUtilities.m1081dp(8.0f);
                    }
                }
                RecyclerListView recyclerListView = GroupCallActivity.this.tabletVideoGridView;
                if (recyclerListView != null) {
                    ((FrameLayout.LayoutParams) recyclerListView.getLayoutParams()).topMargin = ActionBar.getCurrentActionBarHeight();
                }
                if (GroupCallActivity.this.callMessageEnterView.getEmojiView() != null) {
                    ((FrameLayout.LayoutParams) GroupCallActivity.this.callMessageEnterView.getEmojiView().getLayoutParams()).gravity = 80;
                }
                int iM1081dp3 = AndroidUtilities.m1081dp(GroupCallActivity.this.isRtmpStream() ? 40.0f : 90.0f);
                FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) GroupCallActivity.this.listView.getLayoutParams();
                if (GroupCallActivity.isTabletMode) {
                    layoutParams3.gravity = GroupCallActivity.this.hasVideo ? 5 : 1;
                    layoutParams3.width = AndroidUtilities.m1081dp(320.0f);
                    int iM1081dp4 = AndroidUtilities.m1081dp(4.0f);
                    layoutParams3.leftMargin = iM1081dp4;
                    layoutParams3.rightMargin = iM1081dp4;
                    layoutParams3.bottomMargin = iM1081dp3;
                    layoutParams3.topMargin = ActionBar.getCurrentActionBarHeight();
                    iM1081dp2 = AndroidUtilities.m1081dp(60.0f);
                    f = 328.0f;
                } else {
                    f = 328.0f;
                    if (GroupCallActivity.isLandscapeMode) {
                        layoutParams3.gravity = 51;
                        layoutParams3.width = -1;
                        layoutParams3.topMargin = ActionBar.getCurrentActionBarHeight();
                        layoutParams3.bottomMargin = AndroidUtilities.m1081dp(14.0f);
                        layoutParams3.rightMargin = AndroidUtilities.m1081dp(90.0f);
                        layoutParams3.leftMargin = AndroidUtilities.m1081dp(14.0f);
                        iM1081dp2 = 0;
                    } else {
                        layoutParams3.gravity = 51;
                        layoutParams3.width = -1;
                        int iM1081dp5 = AndroidUtilities.m1081dp(60.0f);
                        layoutParams3.bottomMargin = iM1081dp3;
                        layoutParams3.topMargin = ActionBar.getCurrentActionBarHeight() + AndroidUtilities.m1081dp(14.0f);
                        int iM1081dp6 = AndroidUtilities.m1081dp(14.0f);
                        layoutParams3.leftMargin = iM1081dp6;
                        layoutParams3.rightMargin = iM1081dp6;
                        iM1081dp2 = iM1081dp5;
                    }
                }
                if (GroupCallActivity.isLandscapeMode && !GroupCallActivity.isTabletMode) {
                    GroupCallActivity.this.buttonsBackgroundGradientView.setVisibility(8);
                    GroupCallActivity.this.buttonsBackgroundGradientView2.setVisibility(8);
                } else {
                    GroupCallActivity.this.buttonsBackgroundGradientView.setVisibility(0);
                    FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) GroupCallActivity.this.buttonsBackgroundGradientView.getLayoutParams();
                    layoutParams4.bottomMargin = iM1081dp3;
                    if (GroupCallActivity.isTabletMode) {
                        layoutParams4.gravity = GroupCallActivity.this.hasVideo ? 85 : 81;
                        layoutParams4.width = AndroidUtilities.m1081dp(f);
                    } else {
                        layoutParams4.width = -1;
                    }
                    GroupCallActivity.this.buttonsBackgroundGradientView2.setVisibility(0);
                    FrameLayout.LayoutParams layoutParams5 = (FrameLayout.LayoutParams) GroupCallActivity.this.buttonsBackgroundGradientView2.getLayoutParams();
                    layoutParams5.height = iM1081dp3;
                    if (GroupCallActivity.isTabletMode) {
                        layoutParams5.gravity = GroupCallActivity.this.hasVideo ? 85 : 81;
                        layoutParams5.width = AndroidUtilities.m1081dp(f);
                    } else {
                        layoutParams5.width = -1;
                    }
                }
                if (GroupCallActivity.isLandscapeMode) {
                    GroupCallActivity.this.fullscreenUsersListView.setPadding(0, AndroidUtilities.m1081dp(9.0f), 0, AndroidUtilities.m1081dp(9.0f));
                } else {
                    GroupCallActivity.this.fullscreenUsersListView.setPadding(AndroidUtilities.m1081dp(9.0f), 0, AndroidUtilities.m1081dp(9.0f), 0);
                }
                FrameLayout.LayoutParams layoutParams6 = (FrameLayout.LayoutParams) GroupCallActivity.this.buttonsContainer.getLayoutParams();
                if (GroupCallActivity.isTabletMode) {
                    layoutParams6.width = AndroidUtilities.m1081dp(320.0f);
                    layoutParams6.height = AndroidUtilities.m1081dp(120.0f);
                    layoutParams6.gravity = GroupCallActivity.this.hasVideo ? 85 : 81;
                    layoutParams6.rightMargin = 0;
                } else if (GroupCallActivity.isLandscapeMode) {
                    layoutParams6.width = AndroidUtilities.m1081dp(90.0f);
                    layoutParams6.height = -1;
                    layoutParams6.gravity = 53;
                } else {
                    layoutParams6.width = -1;
                    layoutParams6.height = AndroidUtilities.m1081dp(120.0f);
                    layoutParams6.gravity = 81;
                    layoutParams6.rightMargin = 0;
                }
                if (GroupCallActivity.isLandscapeMode && !GroupCallActivity.isTabletMode) {
                    ((FrameLayout.LayoutParams) GroupCallActivity.this.actionBar.getLayoutParams()).rightMargin = AndroidUtilities.m1081dp(90.0f);
                    ((FrameLayout.LayoutParams) GroupCallActivity.this.menuItemsContainer.getLayoutParams()).rightMargin = AndroidUtilities.m1081dp(90.0f);
                    ((FrameLayout.LayoutParams) GroupCallActivity.this.actionBarBackground.getLayoutParams()).rightMargin = AndroidUtilities.m1081dp(90.0f);
                    ((FrameLayout.LayoutParams) GroupCallActivity.this.actionBarShadow.getLayoutParams()).rightMargin = AndroidUtilities.m1081dp(90.0f);
                } else {
                    ((FrameLayout.LayoutParams) GroupCallActivity.this.actionBar.getLayoutParams()).rightMargin = 0;
                    ((FrameLayout.LayoutParams) GroupCallActivity.this.menuItemsContainer.getLayoutParams()).rightMargin = 0;
                    ((FrameLayout.LayoutParams) GroupCallActivity.this.actionBarBackground.getLayoutParams()).rightMargin = 0;
                    ((FrameLayout.LayoutParams) GroupCallActivity.this.actionBarShadow.getLayoutParams()).rightMargin = 0;
                }
                FrameLayout.LayoutParams layoutParams7 = (FrameLayout.LayoutParams) GroupCallActivity.this.fullscreenUsersListView.getLayoutParams();
                if (GroupCallActivity.isLandscapeMode) {
                    if (((LinearLayoutManager) GroupCallActivity.this.fullscreenUsersListView.getLayoutManager()).getOrientation() != 1) {
                        ((LinearLayoutManager) GroupCallActivity.this.fullscreenUsersListView.getLayoutManager()).setOrientation(1);
                    }
                    layoutParams7.height = -1;
                    layoutParams7.width = AndroidUtilities.m1081dp(80.0f);
                    layoutParams7.gravity = 53;
                    layoutParams7.rightMargin = AndroidUtilities.m1081dp(100.0f);
                    layoutParams7.bottomMargin = 0;
                } else {
                    if (((LinearLayoutManager) GroupCallActivity.this.fullscreenUsersListView.getLayoutManager()).getOrientation() != 0) {
                        ((LinearLayoutManager) GroupCallActivity.this.fullscreenUsersListView.getLayoutManager()).setOrientation(0);
                    }
                    layoutParams7.height = AndroidUtilities.m1081dp(80.0f);
                    layoutParams7.width = -1;
                    layoutParams7.gravity = 80;
                    layoutParams7.rightMargin = 0;
                    layoutParams7.bottomMargin = AndroidUtilities.m1081dp(100.0f);
                }
                ((FrameLayout.LayoutParams) GroupCallActivity.this.actionBarShadow.getLayoutParams()).topMargin = ActionBar.getCurrentActionBarHeight();
                GroupCallActivity.this.callMessageEnterUnderContainer.invalidate();
                FrameLayout.LayoutParams layoutParams8 = (FrameLayout.LayoutParams) GroupCallActivity.this.callMessageEnterUnderContainer.getLayoutParams();
                layoutParams8.height = size;
                layoutParams8.topMargin = -getPaddingTop();
                if (GroupCallActivity.this.callMessageEnterView.getEmojiView() != null) {
                    ((FrameLayout.LayoutParams) GroupCallActivity.this.callMessageEnterView.getEmojiView().getLayoutParams()).bottomMargin = -getPaddingBottom();
                }
                int iMax = GroupCallActivity.isTabletMode ? 0 : Math.max(0, (i10 - Math.max(AndroidUtilities.m1081dp(259.0f), (i10 / 5) * 3)) + AndroidUtilities.m1081dp(8.0f));
                if (GroupCallActivity.this.listView.getPaddingTop() != iMax || GroupCallActivity.this.listView.getPaddingBottom() != iM1081dp2) {
                    GroupCallActivity.this.listView.setPadding(0, iMax, 0, iM1081dp2);
                }
                if (GroupCallActivity.this.watchersView != null) {
                    FrameLayout.LayoutParams layoutParams9 = (FrameLayout.LayoutParams) GroupCallActivity.this.watchersView.getLayoutParams();
                    GroupCallGridCell groupCallGridCellFindGroupCallGridCell = GroupCallActivity.this.findGroupCallGridCell();
                    if (groupCallGridCellFindGroupCallGridCell != null) {
                        int top = (GroupCallActivity.this.buttonsContainer.getTop() + (GroupCallActivity.this.buttonsContainer.getMeasuredHeight() / 2)) - (GroupCallActivity.this.leaveButton.getMeasuredHeight() / 2);
                        int currentActionBarHeight = ActionBar.getCurrentActionBarHeight() + iMax + groupCallGridCellFindGroupCallGridCell.getMeasuredHeight();
                        layoutParams9.topMargin = (currentActionBarHeight + ((top - currentActionBarHeight) / 2)) - AndroidUtilities.m1081dp(32.0f);
                        layoutParams9.height = AndroidUtilities.m1081dp(70.0f);
                    }
                }
                if (GroupCallActivity.this.textureLightningView != null) {
                    FrameLayout.LayoutParams layoutParams10 = (FrameLayout.LayoutParams) GroupCallActivity.this.textureLightningView.getLayoutParams();
                    GroupCallGridCell groupCallGridCellFindGroupCallGridCell2 = GroupCallActivity.this.findGroupCallGridCell();
                    if (groupCallGridCellFindGroupCallGridCell2 != null) {
                        layoutParams10.height = groupCallGridCellFindGroupCallGridCell2.getMeasuredHeight() - AndroidUtilities.m1081dp(14.0f);
                        layoutParams10.width = groupCallGridCellFindGroupCallGridCell2.getMeasuredWidth() - AndroidUtilities.m1081dp(7.0f);
                        int iM1081dp7 = AndroidUtilities.m1081dp(16.0f);
                        layoutParams10.leftMargin = iM1081dp7;
                        layoutParams10.rightMargin = iM1081dp7;
                    }
                }
                if (GroupCallActivity.this.scheduleStartAtTextView != null) {
                    int iM1081dp8 = iMax + (((i10 - iMax) + AndroidUtilities.m1081dp(60.0f)) / 2);
                    FrameLayout.LayoutParams layoutParams11 = (FrameLayout.LayoutParams) GroupCallActivity.this.scheduleStartInTextView.getLayoutParams();
                    layoutParams11.topMargin = iM1081dp8 - AndroidUtilities.m1081dp(30.0f);
                    FrameLayout.LayoutParams layoutParams12 = (FrameLayout.LayoutParams) GroupCallActivity.this.scheduleStartAtTextView.getLayoutParams();
                    layoutParams12.topMargin = AndroidUtilities.m1081dp(80.0f) + iM1081dp8;
                    FrameLayout.LayoutParams layoutParams13 = (FrameLayout.LayoutParams) GroupCallActivity.this.scheduleTimeTextView.getLayoutParams();
                    if (layoutParams11.topMargin < ActionBar.getCurrentActionBarHeight() || layoutParams12.topMargin + AndroidUtilities.m1081dp(20.0f) > size - AndroidUtilities.m1081dp(231.0f)) {
                        GroupCallActivity.this.scheduleStartInTextView.setVisibility(4);
                        GroupCallActivity.this.scheduleStartAtTextView.setVisibility(4);
                        layoutParams13.topMargin = iM1081dp8 - AndroidUtilities.m1081dp(20.0f);
                    } else {
                        GroupCallActivity.this.scheduleStartInTextView.setVisibility(0);
                        GroupCallActivity.this.scheduleStartAtTextView.setVisibility(0);
                        layoutParams13.topMargin = iM1081dp8;
                    }
                }
                for (int i12 = 0; i12 < GroupCallActivity.this.attachedRenderers.size(); i12++) {
                    ((GroupCallMiniTextureView) GroupCallActivity.this.attachedRenderers.get(i12)).setFullscreenMode(GroupCallActivity.this.renderersContainer.inFullscreenMode, true);
                }
                this.ignoreLayout = false;
                super.onMeasure(i6, View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30));
                int measuredHeight = getMeasuredHeight() + (getMeasuredWidth() << 16);
                if (measuredHeight != this.lastSize) {
                    this.lastSize = measuredHeight;
                    GroupCallActivity.this.dismissAvatarPreview(false);
                }
                GroupCallActivity.this.cellFlickerDrawable.setParentWidth(getMeasuredWidth());
                GroupCallActivity.this.checkGroupCallUiPositions_MessagesList();
            }

            @Override // org.telegram.p026ui.Components.SizeNotifierFrameLayout, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
            protected void onLayout(boolean z2, int i6, int i7, int i8, int i9) {
                boolean z3;
                float x;
                if (GroupCallActivity.isTabletMode && this.localHasVideo != GroupCallActivity.this.hasVideo && this.wasLayout) {
                    x = GroupCallActivity.this.listView.getX();
                    z3 = true;
                } else {
                    z3 = false;
                    x = 0.0f;
                }
                this.localHasVideo = GroupCallActivity.this.hasVideo;
                GroupCallActivity.this.renderersContainer.inLayout = true;
                super.onLayout(z2, i6, i7, i8, i9);
                GroupCallActivity.this.renderersContainer.inLayout = false;
                GroupCallActivity.this.updateLayout(false);
                this.wasLayout = true;
                if (!z3 || GroupCallActivity.this.listView.getLeft() == x) {
                    return;
                }
                float left = x - GroupCallActivity.this.listView.getLeft();
                GroupCallActivity.this.listView.setTranslationX(left);
                GroupCallActivity.this.buttonsContainer.setTranslationX(left);
                GroupCallActivity.this.buttonsBackgroundGradientView.setTranslationX(left);
                GroupCallActivity.this.buttonsBackgroundGradientView2.setTranslationX(left);
                ViewPropertyAnimator duration = GroupCallActivity.this.listView.animate().translationX(0.0f).setDuration(350L);
                CubicBezierInterpolator cubicBezierInterpolator2 = CubicBezierInterpolator.DEFAULT;
                duration.setInterpolator(cubicBezierInterpolator2).start();
                GroupCallActivity.this.buttonsBackgroundGradientView.animate().translationX(0.0f).setDuration(350L).setInterpolator(cubicBezierInterpolator2).start();
                GroupCallActivity.this.buttonsBackgroundGradientView2.animate().translationX(0.0f).setDuration(350L).setInterpolator(cubicBezierInterpolator2).start();
                GroupCallActivity.this.buttonsContainer.animate().translationX(0.0f).setDuration(350L).setInterpolator(cubicBezierInterpolator2).start();
            }

            @Override // android.view.ViewGroup
            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                if (GroupCallActivity.this.scrimView != null && motionEvent.getAction() == 0) {
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    this.rect.set(GroupCallActivity.this.scrimPopupLayout.getX(), GroupCallActivity.this.scrimPopupLayout.getY(), GroupCallActivity.this.scrimPopupLayout.getX() + GroupCallActivity.this.scrimPopupLayout.getMeasuredWidth(), GroupCallActivity.this.scrimPopupLayout.getY() + GroupCallActivity.this.scrimPopupLayout.getMeasuredHeight());
                    boolean z2 = !this.rect.contains(x, y);
                    this.rect.set(GroupCallActivity.this.avatarPreviewContainer.getX(), GroupCallActivity.this.avatarPreviewContainer.getY(), GroupCallActivity.this.avatarPreviewContainer.getX() + GroupCallActivity.this.avatarPreviewContainer.getMeasuredWidth(), GroupCallActivity.this.avatarPreviewContainer.getY() + GroupCallActivity.this.avatarPreviewContainer.getMeasuredWidth() + GroupCallActivity.this.scrimView.getMeasuredHeight());
                    if (this.rect.contains(x, y)) {
                        z2 = false;
                    }
                    if (z2) {
                        GroupCallActivity.this.dismissAvatarPreview(true);
                        return true;
                    }
                }
                if (motionEvent.getAction() == 0 && GroupCallActivity.this.scrollOffsetY != 0.0f && motionEvent.getY() < GroupCallActivity.this.scrollOffsetY - AndroidUtilities.m1081dp(37.0f) && GroupCallActivity.this.actionBar.getAlpha() == 0.0f && !GroupCallActivity.this.avatarsPreviewShowed) {
                    GroupCallActivity groupCallActivity2 = GroupCallActivity.this;
                    if (groupCallActivity2.previewDialog == null && !groupCallActivity2.renderersContainer.inFullscreenMode) {
                        GroupCallActivity.this.lambda$new$0();
                        return true;
                    }
                }
                return super.onInterceptTouchEvent(motionEvent);
            }

            @Override // android.view.View
            public boolean onTouchEvent(MotionEvent motionEvent) {
                return !GroupCallActivity.this.isDismissed() && super.onTouchEvent(motionEvent);
            }

            @Override // android.view.View, android.view.ViewParent
            public void requestLayout() {
                if (this.ignoreLayout) {
                    return;
                }
                super.requestLayout();
            }

            @Override // android.view.View
            public void setTranslationY(float f) {
                super.setTranslationY(f);
                GroupCallActivity.this.updateTopBulletinY();
            }

            @Override // android.view.View
            protected void onDraw(Canvas canvas) {
                float f;
                int iM1081dp = AndroidUtilities.m1081dp(74.0f);
                float f2 = GroupCallActivity.this.scrollOffsetY - iM1081dp;
                int measuredHeight = getMeasuredHeight() + AndroidUtilities.m1081dp(15.0f) + ((BottomSheet) GroupCallActivity.this).backgroundPaddingTop;
                if (((BottomSheet) GroupCallActivity.this).backgroundPaddingTop + f2 < ActionBar.getCurrentActionBarHeight()) {
                    float fMin = Math.min(1.0f, ((ActionBar.getCurrentActionBarHeight() - f2) - ((BottomSheet) GroupCallActivity.this).backgroundPaddingTop) / ((iM1081dp - ((BottomSheet) GroupCallActivity.this).backgroundPaddingTop) - AndroidUtilities.m1081dp(14.0f)));
                    int currentActionBarHeight = (int) ((ActionBar.getCurrentActionBarHeight() - r1) * fMin);
                    f2 -= currentActionBarHeight;
                    measuredHeight += currentActionBarHeight;
                    f = 1.0f - fMin;
                } else {
                    f = 1.0f;
                }
                float paddingTop = f2 + getPaddingTop();
                GroupCallActivity.this.updateTopBulletinY();
                if (GroupCallActivity.this.renderersContainer.progressToFullscreenMode != 1.0f) {
                    GroupCallActivity.this.shadowDrawable.setBounds(0, (int) paddingTop, getMeasuredWidth(), measuredHeight);
                    GroupCallActivity.this.shadowDrawable.draw(canvas);
                    if (f != 1.0f) {
                        Theme.dialogs_onlineCirclePaint.setColor(GroupCallActivity.this.backgroundColor);
                        this.rect.set(((BottomSheet) GroupCallActivity.this).backgroundPaddingLeft, ((BottomSheet) GroupCallActivity.this).backgroundPaddingTop + paddingTop, getMeasuredWidth() - ((BottomSheet) GroupCallActivity.this).backgroundPaddingLeft, ((BottomSheet) GroupCallActivity.this).backgroundPaddingTop + paddingTop + AndroidUtilities.m1081dp(24.0f));
                        canvas.drawRoundRect(this.rect, AndroidUtilities.m1081dp(12.0f) * f, AndroidUtilities.m1081dp(12.0f) * f, Theme.dialogs_onlineCirclePaint);
                    }
                    Theme.dialogs_onlineCirclePaint.setColor(Color.argb((int) (GroupCallActivity.this.actionBar.getAlpha() * 255.0f), (int) (Color.red(GroupCallActivity.this.backgroundColor) * 0.8f), (int) (Color.green(GroupCallActivity.this.backgroundColor) * 0.8f), (int) (Color.blue(GroupCallActivity.this.backgroundColor) * 0.8f)));
                    canvas.drawRect(((BottomSheet) GroupCallActivity.this).backgroundPaddingLeft, 0.0f, getMeasuredWidth() - ((BottomSheet) GroupCallActivity.this).backgroundPaddingLeft, GroupCallActivity.this.getStatusBarHeight(), Theme.dialogs_onlineCirclePaint);
                    PrivateVideoPreviewDialog privateVideoPreviewDialog = GroupCallActivity.this.previewDialog;
                    if (privateVideoPreviewDialog != null) {
                        Theme.dialogs_onlineCirclePaint.setColor(privateVideoPreviewDialog.getBackgroundColor());
                        canvas.drawRect(((BottomSheet) GroupCallActivity.this).backgroundPaddingLeft, 0.0f, getMeasuredWidth() - ((BottomSheet) GroupCallActivity.this).backgroundPaddingLeft, GroupCallActivity.this.getStatusBarHeight(), Theme.dialogs_onlineCirclePaint);
                    }
                }
                if (GroupCallActivity.this.renderersContainer.progressToFullscreenMode != 0.0f) {
                    Theme.dialogs_onlineCirclePaint.setColor(ColorUtils.setAlphaComponent(Theme.getColor(Theme.key_voipgroup_actionBar), (int) (GroupCallActivity.this.renderersContainer.progressToFullscreenMode * 255.0f)));
                    canvas.drawRect(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight(), Theme.dialogs_onlineCirclePaint);
                }
                if (GroupCallActivity.this.isRtmpStream() && LiteMode.isEnabled(512)) {
                    if (GroupCallActivity.this.renderersContainer.progressToFullscreenMode < 0.15d) {
                        if (!GroupCallActivity.this.needTextureLightning) {
                            GroupCallActivity.this.needTextureLightning = true;
                            GroupCallActivity.this.runUpdateTextureLightningRunnable();
                        }
                    } else if (GroupCallActivity.this.needTextureLightning) {
                        GroupCallActivity.this.needTextureLightning = false;
                        AndroidUtilities.cancelRunOnUIThread(GroupCallActivity.this.updateTextureLightningRunnable);
                    }
                }
                GroupCallActivity.this.reactionEffectImageReceiver.setImageCoords((getMeasuredWidth() / 2.0f) - AndroidUtilities.m1081dp(30.0f), (getMeasuredHeight() / 2.0f) - AndroidUtilities.m1081dp(30.0f), AndroidUtilities.m1081dp(60.0f), AndroidUtilities.m1081dp(60.0f));
                GroupCallActivity.this.reactionEffectImageReceiver.draw(canvas);
            }

            /* JADX WARN: Removed duplicated region for block: B:588:0x07c5  */
            @Override // org.telegram.p026ui.Components.SizeNotifierFrameLayout, android.view.ViewGroup, android.view.View
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            protected void dispatchDraw(android.graphics.Canvas r21) {
                /*
                    Method dump skipped, instruction units count: 2606
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.GroupCallActivity.C56107.dispatchDraw(android.graphics.Canvas):void");
            }

            @Override // android.view.ViewGroup
            protected boolean drawChild(Canvas canvas, View view, long j) {
                if (GroupCallActivity.this.isInDrawRenderNodeBlur) {
                    if (view == GroupCallActivity.this.listView) {
                        int childCount = GroupCallActivity.this.listView.getChildCount();
                        for (int i6 = 0; i6 < childCount; i6++) {
                            View childAt = GroupCallActivity.this.listView.getChildAt(i6);
                            if (childAt.getVisibility() == 0) {
                                canvas.save();
                                canvas.translate(childAt.getX(), childAt.getY());
                                childAt.draw(canvas);
                                canvas.restore();
                            }
                        }
                    }
                    if (view == GroupCallActivity.this.renderersContainer || view == GroupCallActivity.this.buttonsContainer) {
                        return super.drawChild(canvas, view, j);
                    }
                    return true;
                }
                if (!GroupCallActivity.isTabletMode && GroupCallActivity.this.renderersContainer.progressToFullscreenMode == 1.0f && (view == GroupCallActivity.this.actionBar || view == GroupCallActivity.this.actionBarShadow || view == GroupCallActivity.this.actionBarBackground || view == GroupCallActivity.this.titleTextView || view == GroupCallActivity.this.menuItemsContainer || view == GroupCallActivity.this.textureLightningView)) {
                    return true;
                }
                GroupCallActivity groupCallActivity2 = GroupCallActivity.this;
                if (groupCallActivity2.drawingForBlur && view == groupCallActivity2.renderersContainer) {
                    canvas.save();
                    canvas.translate(GroupCallActivity.this.renderersContainer.getX() + GroupCallActivity.this.fullscreenUsersListView.getX(), GroupCallActivity.this.renderersContainer.getY() + GroupCallActivity.this.fullscreenUsersListView.getY());
                    GroupCallActivity.this.fullscreenUsersListView.draw(canvas);
                    canvas.restore();
                    return true;
                }
                if (view == GroupCallActivity.this.avatarPreviewContainer || view == GroupCallActivity.this.scrimPopupLayout || view == GroupCallActivity.this.scrimView) {
                    return true;
                }
                if (GroupCallActivity.this.contentFullyOverlayed && GroupCallActivity.this.useBlur && (view == GroupCallActivity.this.listView || view == GroupCallActivity.this.buttonsContainer || view == GroupCallActivity.this.groupCallMessagesListView)) {
                    return true;
                }
                return super.drawChild(canvas, view, j);
            }

            @Override // android.view.View, android.view.KeyEvent.Callback
            public boolean onKeyDown(int i6, KeyEvent keyEvent) {
                if (GroupCallActivity.this.scrimView != null && i6 == 4) {
                    GroupCallActivity.this.dismissAvatarPreview(true);
                    return true;
                }
                return super.onKeyDown(i6, keyEvent);
            }
        };
        groupCallActivity.sizeNotifierFrameLayout = c56107;
        groupCallActivity.containerView = c56107;
        c56107.setClipToPadding(false);
        groupCallActivity.containerView.setFocusable(true);
        groupCallActivity.containerView.setFocusableInTouchMode(true);
        groupCallActivity.containerView.setWillNotDraw(false);
        ViewGroup viewGroup = groupCallActivity.containerView;
        int i6 = groupCallActivity.backgroundPaddingLeft;
        viewGroup.setPadding(i6, 0, i6, 0);
        groupCallActivity.containerView.setKeepScreenOn(true);
        groupCallActivity.containerView.setClipChildren(false);
        groupCallActivity.reactionEffectImageReceiver = new ImageReceiver(groupCallActivity.containerView);
        if (inputPeer != null) {
            SimpleTextView simpleTextView = new SimpleTextView(activity3);
            groupCallActivity.scheduleStartInTextView = simpleTextView;
            simpleTextView.setGravity(17);
            groupCallActivity.scheduleStartInTextView.setTextColor(-1);
            groupCallActivity.scheduleStartInTextView.setTypeface(AndroidUtilities.bold());
            groupCallActivity.scheduleStartInTextView.setTextSize(18);
            groupCallActivity.scheduleStartInTextView.setText(LocaleController.getString(C2702R.string.VoipChatStartsIn));
            groupCallActivity.containerView.addView(groupCallActivity.scheduleStartInTextView, LayoutHelper.createFrame(-2, -2.0f, 49, 21.0f, 0.0f, 21.0f, 311.0f));
            C56118 c56118 = new SimpleTextView(activity3) { // from class: org.telegram.ui.GroupCallActivity.8
                private float duration;
                private float gradientWidth;
                private int lastTextWidth;
                private long lastUpdateTime;
                private LinearGradient linearGradient;
                private float startX;
                private float time;
                private Matrix matrix = new Matrix();
                private float targetX = -1.0f;

                C56118(final Context activity3) {
                    super(activity3);
                    this.matrix = new Matrix();
                    this.targetX = -1.0f;
                }

                private void setTarget() {
                    this.targetX = ((Utilities.random.nextInt(100) - 50) * 0.2f) / 50.0f;
                }

                @Override // org.telegram.p026ui.ActionBar.SimpleTextView
                protected boolean createLayout(int i7) {
                    boolean zCreateLayout = super.createLayout(i7);
                    int textWidth = getTextWidth();
                    if (textWidth != this.lastTextWidth) {
                        float f = textWidth;
                        this.gradientWidth = 1.3f * f;
                        float textHeight = getTextHeight();
                        float f2 = f * 2.0f;
                        int color = Theme.getColor(Theme.key_voipgroup_mutedByAdminGradient);
                        int color2 = Theme.getColor(Theme.key_voipgroup_mutedByAdminGradient3);
                        int i8 = Theme.key_voipgroup_mutedByAdminGradient2;
                        this.linearGradient = new LinearGradient(0.0f, textHeight, f2, 0.0f, new int[]{color, color2, Theme.getColor(i8), Theme.getColor(i8)}, new float[]{0.0f, 0.38f, 0.76f, 1.0f}, Shader.TileMode.CLAMP);
                        getPaint().setShader(this.linearGradient);
                        this.lastTextWidth = textWidth;
                    }
                    return zCreateLayout;
                }

                /* JADX WARN: Removed duplicated region for block: B:78:0x0040  */
                /* JADX WARN: Removed duplicated region for block: B:81:0x0062  */
                /* JADX WARN: Removed duplicated region for block: B:86:0x0072  */
                /* JADX WARN: Removed duplicated region for block: B:88:0x0089  */
                /* JADX WARN: Removed duplicated region for block: B:92:0x00b3  */
                @Override // org.telegram.p026ui.ActionBar.SimpleTextView, android.view.View
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                protected void onDraw(android.graphics.Canvas r9) {
                    /*
                        Method dump skipped, instruction units count: 221
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.GroupCallActivity.C56118.onDraw(android.graphics.Canvas):void");
                }
            };
            groupCallActivity.scheduleTimeTextView = c56118;
            c56118.setGravity(17);
            groupCallActivity.scheduleTimeTextView.setTextColor(-1);
            groupCallActivity.scheduleTimeTextView.setTypeface(AndroidUtilities.bold());
            groupCallActivity.scheduleTimeTextView.setTextSize(60);
            groupCallActivity.containerView.addView(groupCallActivity.scheduleTimeTextView, LayoutHelper.createFrame(-2, -2.0f, 49, 21.0f, 0.0f, 21.0f, 231.0f));
            SimpleTextView simpleTextView2 = new SimpleTextView(activity3);
            groupCallActivity.scheduleStartAtTextView = simpleTextView2;
            simpleTextView2.setGravity(17);
            groupCallActivity.scheduleStartAtTextView.setTextColor(-1);
            groupCallActivity.scheduleStartAtTextView.setTypeface(AndroidUtilities.bold());
            groupCallActivity.scheduleStartAtTextView.setTextSize(18);
            groupCallActivity.containerView.addView(groupCallActivity.scheduleStartAtTextView, LayoutHelper.createFrame(-2, -2.0f, 49, 21.0f, 0.0f, 21.0f, 201.0f));
        }
        if (groupCallActivity.isRtmpStream()) {
            LightningView lightningView = groupCallActivity.new LightningView(activity3);
            groupCallActivity.textureLightningView = lightningView;
            groupCallActivity.containerView.addView(lightningView, LayoutHelper.createFrame(-1, 80.0f, 51, 0.0f, 44.0f, 0.0f, 0.0f));
        }
        C56129 c56129 = new RecyclerListView(activity3) { // from class: org.telegram.ui.GroupCallActivity.9
            private final LongSparseIntArray visiblePeerTmp = new LongSparseIntArray();

            C56129(final Context activity3) {
                super(activity3);
                this.visiblePeerTmp = new LongSparseIntArray();
            }

            @Override // org.telegram.p026ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
            public boolean drawChild(Canvas canvas, View view, long j) {
                if (view == GroupCallActivity.this.scrimView) {
                    return false;
                }
                return super.drawChild(canvas, view, j);
            }

            /* JADX WARN: Removed duplicated region for block: B:159:0x00b2  */
            @Override // org.telegram.p026ui.Components.RecyclerListView, android.view.ViewGroup, android.view.View
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            protected void dispatchDraw(android.graphics.Canvas r18) {
                /*
                    Method dump skipped, instruction units count: 451
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.GroupCallActivity.C56129.dispatchDraw(android.graphics.Canvas):void");
            }

            @Override // org.telegram.p026ui.Components.RecyclerListView, android.view.View
            public void setVisibility(int i7) {
                if (getVisibility() != i7) {
                    for (int i8 = 0; i8 < getChildCount(); i8++) {
                        View childAt = getChildAt(i8);
                        if (childAt instanceof GroupCallGridCell) {
                            GroupCallActivity.this.attachRenderer((GroupCallGridCell) childAt, childAt.isAttachedToWindow() && i7 == 0);
                        }
                    }
                }
                super.setVisibility(i7);
            }

            @Override // org.telegram.p026ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
            protected void onLayout(boolean z2, int i7, int i8, int i9, int i10) {
                super.onLayout(z2, i7, i8, i9, i10);
                GroupCallActivity.this.itemAnimator.updateBackgroundBeforeAnimation();
            }
        };
        groupCallActivity.listView = c56129;
        c56129.setClipToPadding(false);
        groupCallActivity.listView.setClipChildren(false);
        GroupCallItemAnimator groupCallItemAnimator = new GroupCallItemAnimator();
        groupCallActivity.itemAnimator = groupCallItemAnimator;
        groupCallItemAnimator.setTranslationInterpolator(cubicBezierInterpolator);
        groupCallItemAnimator.setRemoveDuration(350L);
        groupCallItemAnimator.setAddDuration(350L);
        groupCallItemAnimator.setMoveDuration(350L);
        groupCallItemAnimator.setDelayAnimations(false);
        groupCallActivity.listView.setItemAnimator(groupCallItemAnimator);
        groupCallActivity.listView.setOnScrollListener(new RecyclerView.OnScrollListener() { // from class: org.telegram.ui.GroupCallActivity.10
            C554810() {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i7, int i8) {
                GroupCallActivity groupCallActivity2;
                ChatObject.Call call2;
                if (GroupCallActivity.this.listView.getChildCount() <= 0 || (call2 = (groupCallActivity2 = GroupCallActivity.this).call) == null) {
                    return;
                }
                if (!call2.loadingMembers && !call2.membersLoadEndReached && groupCallActivity2.layoutManager.findLastVisibleItemPosition() > GroupCallActivity.this.listAdapter.getItemCount() - 5) {
                    GroupCallActivity.this.call.loadMembers(false);
                }
                GroupCallActivity.this.updateLayout(true);
                if (GroupCallActivity.this.textureLightningView != null) {
                    GroupCallActivity.this.textureLightningView.invalidate();
                }
                ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i7) {
                if (i7 == 0) {
                    if ((GroupCallActivity.this.scrollOffsetY - AndroidUtilities.m1081dp(74.0f)) + ((BottomSheet) GroupCallActivity.this).backgroundPaddingTop >= ActionBar.getCurrentActionBarHeight() || !GroupCallActivity.this.listView.canScrollVertically(1)) {
                        return;
                    }
                    GroupCallActivity.this.listView.getChildAt(0);
                    RecyclerListView.Holder holder = (RecyclerListView.Holder) GroupCallActivity.this.listView.findViewHolderForAdapterPosition(0);
                    if (holder == null || holder.itemView.getTop() <= 0) {
                        return;
                    }
                    GroupCallActivity.this.listView.smoothScrollBy(0, holder.itemView.getTop());
                    return;
                }
                if (GroupCallActivity.this.recordHintView != null) {
                    GroupCallActivity.this.recordHintView.hide();
                }
                if (GroupCallActivity.this.reminderHintView != null) {
                    GroupCallActivity.this.reminderHintView.hide();
                }
            }
        });
        groupCallActivity.listView.setVerticalScrollBarEnabled(false);
        RecyclerListView recyclerListView = groupCallActivity.listView;
        FillLastGridLayoutManager fillLastGridLayoutManager = new FillLastGridLayoutManager(groupCallActivity.getContext(), isLandscapeMode ? 6 : 2, 1, false, 0, groupCallActivity.listView);
        groupCallActivity.layoutManager = fillLastGridLayoutManager;
        recyclerListView.setLayoutManager(fillLastGridLayoutManager);
        C554911 c554911 = new GridLayoutManager.SpanSizeLookup() { // from class: org.telegram.ui.GroupCallActivity.11
            C554911() {
            }

            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int i7) {
                int i8 = GroupCallActivity.isLandscapeMode ? 6 : 2;
                if (GroupCallActivity.isTabletMode || i7 < GroupCallActivity.this.listAdapter.usersVideoGridStartRow || i7 >= GroupCallActivity.this.listAdapter.usersVideoGridEndRow) {
                    return i8;
                }
                int i9 = GroupCallActivity.this.listAdapter.usersVideoGridEndRow - GroupCallActivity.this.listAdapter.usersVideoGridStartRow;
                int i10 = (i7 != GroupCallActivity.this.listAdapter.usersVideoGridEndRow - 1 || (!GroupCallActivity.isLandscapeMode && i9 % 2 == 0)) ? 1 : 2;
                if (!GroupCallActivity.isLandscapeMode) {
                    return i10;
                }
                if (i9 == 1) {
                    return 6;
                }
                return i9 == 2 ? 3 : 2;
            }
        };
        groupCallActivity.spanSizeLookup = c554911;
        fillLastGridLayoutManager.setSpanSizeLookup(c554911);
        groupCallActivity.listView.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: org.telegram.ui.GroupCallActivity.12
            C555012() {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
                int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
                if (childAdapterPosition >= 0) {
                    rect.setEmpty();
                    if (childAdapterPosition < GroupCallActivity.this.listAdapter.usersVideoGridStartRow || childAdapterPosition >= GroupCallActivity.this.listAdapter.usersVideoGridEndRow) {
                        return;
                    }
                    int i7 = childAdapterPosition - GroupCallActivity.this.listAdapter.usersVideoGridStartRow;
                    int i8 = GroupCallActivity.isLandscapeMode ? 6 : 2;
                    int i9 = i7 % i8;
                    if (i9 == 0) {
                        rect.right = AndroidUtilities.m1081dp(2.0f);
                    } else if (i9 == i8 - 1) {
                        rect.left = AndroidUtilities.m1081dp(2.0f);
                    } else {
                        rect.left = AndroidUtilities.m1081dp(1.0f);
                    }
                }
            }
        });
        fillLastGridLayoutManager.setBind(false);
        groupCallActivity.containerView.addView(groupCallActivity.listView, LayoutHelper.createFrame(-1, -1.0f, 51, 14.0f, 14.0f, 14.0f, 231.0f));
        groupCallActivity.listView.setAdapter(listAdapter);
        groupCallActivity.listView.setTopBottomSelectorRadius(13);
        groupCallActivity.listView.setSelectorDrawableColor(Theme.getColor(Theme.key_voipgroup_listSelector));
        groupCallActivity.listView.setOnItemClickListener(new RecyclerListView.OnItemClickListenerExtended() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda32
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public /* synthetic */ boolean hasDoubleTap(View view, int i7) {
                return RecyclerListView.OnItemClickListenerExtended.CC.$default$hasDoubleTap(this, view, i7);
            }

            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public /* synthetic */ void onDoubleTap(View view, int i7, float f, float f2) {
                RecyclerListView.OnItemClickListenerExtended.CC.$default$onDoubleTap(this, view, i7, f, f2);
            }

            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public final void onItemClick(View view, int i7, float f, float f2) {
                this.f$0.lambda$new$24(activity3, call, view, i7, f, f2);
            }
        });
        groupCallActivity.listView.setOnItemLongClickListener(new RecyclerListView.OnItemLongClickListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda33
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListener
            public final boolean onItemClick(View view, int i7) {
                return this.f$0.lambda$new$25(view, i7);
            }
        });
        if (groupCallActivity.isRtmpStream()) {
            WatchersView watchersView = groupCallActivity.new WatchersView(groupCallActivity.getContext());
            groupCallActivity.watchersView = watchersView;
            groupCallActivity.containerView.addView(watchersView, LayoutHelper.createFrame(-1, 0.0f, 49, 0.0f, 0.0f, 0.0f, 0.0f));
        }
        RecyclerListView recyclerListView2 = new RecyclerListView(activity3);
        groupCallActivity.tabletVideoGridView = recyclerListView2;
        groupCallActivity.containerView.addView(recyclerListView2, LayoutHelper.createFrame(-1, -1.0f, 51, 14.0f, 14.0f, 324.0f, 14.0f));
        RecyclerListView recyclerListView3 = groupCallActivity.tabletVideoGridView;
        GroupCallTabletGridAdapter groupCallTabletGridAdapter = new GroupCallTabletGridAdapter(call, groupCallActivity.currentAccount, groupCallActivity);
        groupCallActivity.tabletGridAdapter = groupCallTabletGridAdapter;
        recyclerListView3.setAdapter(groupCallTabletGridAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity3, 6, 1, false);
        groupCallActivity.tabletVideoGridView.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: org.telegram.ui.GroupCallActivity.14
            C555214() {
            }

            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int i7) {
                return GroupCallActivity.this.tabletGridAdapter.getSpanCount(i7);
            }
        });
        groupCallActivity.tabletVideoGridView.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda34
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
            public final void onItemClick(View view, int i7) {
                this.f$0.lambda$new$26(view, i7);
            }
        });
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setDelayAnimations(false);
        defaultItemAnimator.setTranslationInterpolator(cubicBezierInterpolator);
        final TLRPC.InputPeer inputPeer2 = groupCallPeer;
        defaultItemAnimator.setRemoveDuration(350L);
        defaultItemAnimator.setAddDuration(350L);
        defaultItemAnimator.setMoveDuration(350L);
        groupCallActivity.tabletVideoGridView.setItemAnimator(new DefaultItemAnimator() { // from class: org.telegram.ui.GroupCallActivity.15
            C555315() {
            }

            @Override // androidx.recyclerview.widget.DefaultItemAnimator
            protected void onMoveAnimationUpdate(RecyclerView.ViewHolder viewHolder) {
                GroupCallActivity.this.listView.invalidate();
                GroupCallActivity.this.renderersContainer.invalidate();
                ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
                GroupCallActivity.this.updateLayout(true);
            }
        });
        groupCallActivity.tabletVideoGridView.setOnScrollListener(new RecyclerView.OnScrollListener() { // from class: org.telegram.ui.GroupCallActivity.16
            C555416() {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i7, int i8) {
                super.onScrolled(recyclerView, i7, i8);
                ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
            }
        });
        groupCallActivity.tabletGridAdapter.setVisibility(groupCallActivity.tabletVideoGridView, false, false);
        groupCallActivity.tabletVideoGridView.setVisibility(8);
        C555517 c555517 = new GroupCallActivityButtonsLayout(activity3) { // from class: org.telegram.ui.GroupCallActivity.17
            int currentLightColor;
            final OvershootInterpolator overshootInterpolator = new OvershootInterpolator(1.5f);

            C555517(final Context activity3) {
                super(activity3);
                this.overshootInterpolator = new OvershootInterpolator(1.5f);
            }

            /* JADX WARN: Removed duplicated region for block: B:496:0x0163  */
            /* JADX WARN: Removed duplicated region for block: B:575:0x0463  */
            /* JADX WARN: Removed duplicated region for block: B:587:0x04d4  */
            /* JADX WARN: Removed duplicated region for block: B:606:0x06aa  */
            /* JADX WARN: Removed duplicated region for block: B:631:0x0760  */
            /* JADX WARN: Removed duplicated region for block: B:634:0x076c  */
            /* JADX WARN: Removed duplicated region for block: B:635:0x07d8  */
            /* JADX WARN: Removed duplicated region for block: B:643:0x08da  */
            /* JADX WARN: Removed duplicated region for block: B:652:0x0944  */
            /* JADX WARN: Removed duplicated region for block: B:655:0x096d  */
            /* JADX WARN: Removed duplicated region for block: B:664:0x0a0d  */
            /* JADX WARN: Removed duplicated region for block: B:675:0x0b0e  */
            /* JADX WARN: Removed duplicated region for block: B:682:? A[RETURN, SYNTHETIC] */
            @Override // android.view.ViewGroup, android.view.View
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            protected void dispatchDraw(android.graphics.Canvas r28) {
                /*
                    Method dump skipped, instruction units count: 2834
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.GroupCallActivity.C555517.dispatchDraw(android.graphics.Canvas):void");
            }
        };
        groupCallActivity.buttonsContainer = c555517;
        int color = Theme.getColor(Theme.key_voipgroup_unmuteButton2);
        int iRed = Color.red(color);
        int iGreen = Color.green(color);
        int iBlue = Color.blue(color);
        groupCallActivity.radialMatrix = new Matrix();
        groupCallActivity.radialGradient = new RadialGradient(0.0f, 0.0f, AndroidUtilities.m1081dp(72.72727f), new int[]{Color.argb(50, iRed, iGreen, iBlue), Color.argb(0, iRed, iGreen, iBlue)}, (float[]) null, Shader.TileMode.CLAMP);
        Paint paint = new Paint(1);
        groupCallActivity.radialPaint = paint;
        paint.setShader(groupCallActivity.radialGradient);
        BlobDrawable blobDrawable = new BlobDrawable(9);
        groupCallActivity.tinyWaveDrawable = blobDrawable;
        BlobDrawable blobDrawable2 = new BlobDrawable(12);
        groupCallActivity.bigWaveDrawable = blobDrawable2;
        blobDrawable.minRadius = AndroidUtilities.m1081dp(62.0f) * 0.45454547f;
        blobDrawable.maxRadius = AndroidUtilities.m1081dp(72.0f) * 0.45454547f;
        blobDrawable.generateBlob();
        blobDrawable2.minRadius = AndroidUtilities.m1081dp(65.0f) * 0.45454547f;
        blobDrawable2.maxRadius = AndroidUtilities.m1081dp(75.0f) * 0.45454547f;
        blobDrawable2.generateBlob();
        Paint paint2 = blobDrawable.paint;
        int i7 = Theme.key_voipgroup_unmuteButton;
        paint2.setColor(ColorUtils.setAlphaComponent(Theme.getColor(i7), 38));
        blobDrawable2.paint.setColor(ColorUtils.setAlphaComponent(Theme.getColor(i7), 76));
        VoIPToggleButton voIPToggleButton = new VoIPToggleButton(activity3, 50.0f);
        groupCallActivity.soundButton = voIPToggleButton;
        voIPToggleButton.setCheckable(true);
        voIPToggleButton.setTextSize(12);
        c555517.addButton(voIPToggleButton);
        voIPToggleButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda35
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$27(view);
            }
        });
        VoIPToggleButton voIPToggleButton2 = new VoIPToggleButton(activity3, 50.0f);
        groupCallActivity.cameraButton = voIPToggleButton2;
        voIPToggleButton2.setCheckable(true);
        voIPToggleButton2.setTextSize(12);
        voIPToggleButton2.showText(false, false);
        IconManager iconManager = IconManager.INSTANCE;
        voIPToggleButton2.setCrossOffset(-AndroidUtilities.dpf2((iconManager.isBasePackOnly(1) ? 3.5f : 0.0f) + 3.5f));
        voIPToggleButton2.setCrossOffsetY(-AndroidUtilities.dpf2(iconManager.isBasePackOnly(1) ? 3.5f : 0.0f));
        voIPToggleButton2.setData(C2702R.drawable.calls_video, -1, 0, 1.0f, true, LocaleController.getString(C2702R.string.VoipCamera), false, false);
        VoIPToggleButton voIPToggleButton3 = new VoIPToggleButton(activity3, 50.0f);
        groupCallActivity.flipButton = voIPToggleButton3;
        voIPToggleButton3.setCheckable(true);
        voIPToggleButton3.setTextSize(12);
        voIPToggleButton3.showText(false, false);
        RLottieImageView rLottieImageView = new RLottieImageView(activity3);
        voIPToggleButton3.addView(rLottieImageView, LayoutHelper.createFrame(32, 32.0f, 1, 0.0f, 10.0f, 0.0f, 0.0f));
        RLottieDrawable rLottieDrawable2 = new RLottieDrawable(C2702R.raw.camera_flip, _UrlKt.FRAGMENT_ENCODE_SET + C2702R.raw.camera_flip, AndroidUtilities.m1081dp(24.0f), AndroidUtilities.m1081dp(24.0f), true, null);
        groupCallActivity.flipIcon = rLottieDrawable2;
        rLottieImageView.setAnimation(rLottieDrawable2);
        voIPToggleButton3.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$28(view);
            }
        });
        c555517.addButton(voIPToggleButton3);
        VoIPToggleButton voIPToggleButton4 = new VoIPToggleButton(activity3, 50.0f);
        groupCallActivity.speakerButton = voIPToggleButton4;
        voIPToggleButton4.setCheckable(true);
        voIPToggleButton4.setTextSize(12);
        voIPToggleButton4.showText(false, false);
        ImageView imageView = new ImageView(activity3);
        groupCallActivity.speakerImageView = imageView;
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        int i8 = C2702R.drawable.filled_sound_on;
        groupCallActivity.speakerIcon = i8;
        imageView.setImageResource(i8);
        imageView.setColorFilter(new PorterDuffColorFilter(-1, PorterDuff.Mode.SRC_IN));
        imageView.setScaleX(1.11f);
        imageView.setScaleY(1.11f);
        voIPToggleButton4.addView(imageView, LayoutHelper.createFrame(30, 30.0f, 1, 0.0f, 11.0f, 0.0f, 0.0f));
        voIPToggleButton4.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$30(view);
            }
        });
        c555517.addButton(voIPToggleButton4);
        c555517.addButton(voIPToggleButton2);
        VoIPToggleButton voIPToggleButton5 = new VoIPToggleButton(activity3, 50.0f);
        groupCallActivity.leaveButton = voIPToggleButton5;
        voIPToggleButton5.setTextSize(12);
        voIPToggleButton5.setData(C2702R.drawable.calls_decline, -1, Theme.getColor(Theme.key_voipgroup_leaveButton), 0.3f, false, LocaleController.getString(C2702R.string.VoipGroupLeave), false, false);
        voIPToggleButton5.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$31(activity3, view);
            }
        });
        VoIPToggleButton voIPToggleButton6 = new VoIPToggleButton(activity3, 50.0f);
        groupCallActivity.messageButton = voIPToggleButton6;
        voIPToggleButton6.setCheckable(true);
        voIPToggleButton6.setChecked(true, false);
        voIPToggleButton6.setTextSize(12);
        voIPToggleButton6.setData(C2702R.drawable.filled_voice_comment_32, -1, 0, 1.0f, true, LocaleController.getString(C2702R.string.VoipMessage), false, false);
        C555618 c555618 = new RLottieImageView(activity3) { // from class: org.telegram.ui.GroupCallActivity.18
            C555618(final Context activity3) {
                super(activity3);
            }

            /* JADX WARN: Removed duplicated region for block: B:89:0x0032  */
            @Override // android.view.View
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public boolean onTouchEvent(android.view.MotionEvent r13) {
                /*
                    Method dump skipped, instruction units count: 217
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.GroupCallActivity.C555618.onTouchEvent(android.view.MotionEvent):boolean");
            }

            @Override // android.view.View
            public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
                accessibilityNodeInfo.setClassName(Button.class.getName());
                accessibilityNodeInfo.setEnabled(GroupCallActivity.this.muteButtonState == 0 || GroupCallActivity.this.muteButtonState == 1);
                if (GroupCallActivity.this.muteButtonState == 1) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, LocaleController.getString(C2702R.string.VoipMute)));
                }
            }
        };
        groupCallActivity.muteButtonIcon = c555618;
        c555618.setAnimation(rLottieDrawable);
        c555618.setScaleType(ImageView.ScaleType.CENTER);
        VoIPToggleButton voIPToggleButton7 = new VoIPToggleButton(activity3, 50.0f);
        groupCallActivity.muteButton = voIPToggleButton7;
        voIPToggleButton7.setDrawBackground(false);
        voIPToggleButton7.setTextSize(12);
        voIPToggleButton7.setData(0, 0, 0, "Text", false, false);
        voIPToggleButton7.addView(c555618, LayoutHelper.createFrame(50, 50, 49));
        c555517.addButton(voIPToggleButton7);
        voIPToggleButton7.setOnClickListener(groupCallActivity.new ViewOnClickListenerC555719());
        c555517.addButton(voIPToggleButton6);
        c555517.addButton(voIPToggleButton5);
        ImageView imageView2 = new ImageView(activity3);
        groupCallActivity.expandOrMinimizeButton = imageView2;
        imageView2.setVisibility(8);
        imageView2.setImageResource(C2702R.drawable.voice_expand);
        voIPToggleButton7.addView(imageView2, LayoutHelper.createFrame(24, 24.0f, 49, 0.0f, 13.0f, 0.0f, 0.0f));
        if (groupCallActivity.call != null && groupCallActivity.isRtmpStream() && !groupCallActivity.call.isScheduled()) {
            imageView2.setVisibility(0);
            c555618.setVisibility(8);
        }
        RadialProgressView radialProgressView = new RadialProgressView(activity3);
        groupCallActivity.radialProgressView = radialProgressView;
        radialProgressView.setSize(AndroidUtilities.m1081dp(50.0f));
        radialProgressView.setStrokeWidth(2.0f);
        radialProgressView.setProgressColor(Theme.getColor(Theme.key_voipgroup_connectingProgress));
        c55915.setAlpha(0.0f);
        c55915.getBackButton().setScaleX(0.9f);
        c55915.getBackButton().setScaleY(0.9f);
        c55915.getBackButton().setTranslationX(-AndroidUtilities.m1081dp(14.0f));
        c55915.getTitleTextView().setTranslationY(AndroidUtilities.m1081dp(23.0f));
        c55915.getSubtitleTextView().setTranslationY(AndroidUtilities.m1081dp(20.0f));
        c55915.getAdditionalSubtitleTextView().setTranslationY(AndroidUtilities.m1081dp(20.0f));
        ActionBarMenuItem actionBarMenuItem = new ActionBarMenuItem(activity3, null, 0, Theme.getColor(i));
        groupCallActivity.otherItem = actionBarMenuItem;
        actionBarMenuItem.setLongClickEnabled(false);
        actionBarMenuItem.setIcon(C2702R.drawable.ic_ab_other);
        actionBarMenuItem.setContentDescription(LocaleController.getString(C2702R.string.AccDescrMoreOptions));
        actionBarMenuItem.setSubMenuOpenSide(2);
        actionBarMenuItem.setDelegate(new ActionBarMenuItem.ActionBarMenuItemDelegate() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda10
            @Override // org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemDelegate
            public final void onItemClick(int i9) {
                this.f$0.lambda$new$32(i9);
            }
        });
        int i9 = Theme.key_voipgroup_actionBarItemsSelector;
        actionBarMenuItem.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.getColor(i9), 6));
        actionBarMenuItem.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$33(view);
            }
        });
        actionBarMenuItem.setPopupItemsColor(Theme.getColor(i), false);
        actionBarMenuItem.setPopupItemsColor(Theme.getColor(i), true);
        ActionBarMenuItem actionBarMenuItem2 = new ActionBarMenuItem(activity3, null, 0, Theme.getColor(i));
        groupCallActivity.pipItem = actionBarMenuItem2;
        actionBarMenuItem2.setLongClickEnabled(false);
        actionBarMenuItem2.setIcon(C2702R.drawable.msg_voice_pip);
        actionBarMenuItem2.setContentDescription(LocaleController.getString(C2702R.string.AccDescrPipMode));
        actionBarMenuItem2.setBackground(Theme.createSelectorDrawable(Theme.getColor(i9), 6));
        actionBarMenuItem2.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda12
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$34(view);
            }
        });
        ActionBarMenuItem actionBarMenuItem3 = new ActionBarMenuItem(activity3, null, 0, Theme.getColor(i));
        groupCallActivity.screenShareItem = actionBarMenuItem3;
        actionBarMenuItem3.setLongClickEnabled(false);
        actionBarMenuItem3.setIcon(C2702R.drawable.msg_screencast);
        actionBarMenuItem3.setContentDescription(LocaleController.getString(C2702R.string.AccDescrPipMode));
        actionBarMenuItem3.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.getColor(i9), 6));
        actionBarMenuItem3.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$35(view);
            }
        });
        C555920 c555920 = groupCallActivity.new C555920(activity3, activity3);
        groupCallActivity.titleTextView = c555920;
        C556021 c556021 = new View(activity3) { // from class: org.telegram.ui.GroupCallActivity.21
            C556021(final Context activity3) {
                super(activity3);
            }

            @Override // android.view.View
            protected void onMeasure(int i10, int i11) {
                setMeasuredDimension(View.MeasureSpec.getSize(i10), ActionBar.getCurrentActionBarHeight());
            }
        };
        groupCallActivity.actionBarBackground = c556021;
        c556021.setAlpha(0.0f);
        Paint paint3 = new Paint(1);
        groupCallActivity.liveLabelPaint = paint3;
        paint3.setColor(-12761513);
        C556122 c556122 = new TextView(groupCallActivity.getContext()) { // from class: org.telegram.ui.GroupCallActivity.22
            private RectF rect = new RectF();

            C556122(Context context) {
                super(context);
                this.rect = new RectF();
            }

            @Override // android.widget.TextView, android.view.View
            protected void onDraw(Canvas canvas) {
                this.rect.set(0.0f, 0.0f, getWidth(), getHeight());
                canvas.drawRoundRect(this.rect, AndroidUtilities.m1081dp(12.0f), AndroidUtilities.m1081dp(12.0f), GroupCallActivity.this.liveLabelPaint);
                super.onDraw(canvas);
            }
        };
        groupCallActivity.liveLabelTextView = c556122;
        c556122.setTextColor(groupCallActivity.getThemedColor(i));
        c556122.setTextSize(1, 11.0f);
        c556122.setText(LocaleController.getString(C2702R.string.VoipChannelLabelLive));
        c556122.setMaxLines(1);
        c556122.setGravity(17);
        c556122.setTypeface(AndroidUtilities.bold());
        c556122.setPadding(AndroidUtilities.m1081dp(8.0f), AndroidUtilities.m1081dp(0.0f), AndroidUtilities.m1081dp(8.0f), AndroidUtilities.m1081dp(0.0f));
        c556122.setTag(-1);
        if (!groupCallActivity.isRtmpStream()) {
            c556122.setVisibility(8);
        }
        LinearLayout linearLayout = new LinearLayout(groupCallActivity.getContext());
        groupCallActivity.titleLayout = linearLayout;
        linearLayout.setOrientation(0);
        linearLayout.addView(c555920, LayoutHelper.createLinear(0, -2, 1.0f));
        linearLayout.addView(c556122, LayoutHelper.createLinear(-2, 18, 6.0f, 4.0f, 0.0f, 0.0f));
        groupCallActivity.containerView.addView(c556021, LayoutHelper.createFrame(-1, -2.0f, 51, 0.0f, 0.0f, 0.0f, 0.0f));
        groupCallActivity.containerView.addView(linearLayout, LayoutHelper.createFrame(-2, -2.0f, 51, 23.0f, 0.0f, 48.0f, 0.0f));
        groupCallActivity.containerView.addView(c55915, LayoutHelper.createFrame(-1, -2.0f, 51, 0.0f, 0.0f, 0.0f, 0.0f));
        LinearLayout linearLayout2 = new LinearLayout(activity3);
        groupCallActivity.menuItemsContainer = linearLayout2;
        linearLayout2.setOrientation(0);
        linearLayout2.addView(actionBarMenuItem3, LayoutHelper.createLinear(48, 48));
        linearLayout2.addView(actionBarMenuItem2, LayoutHelper.createLinear(48, 48));
        linearLayout2.addView(actionBarMenuItem, LayoutHelper.createLinear(48, 48));
        groupCallActivity.containerView.addView(linearLayout2, LayoutHelper.createFrame(-2, 48, 53));
        View view = new View(activity3);
        groupCallActivity.actionBarShadow = view;
        view.setAlpha(0.0f);
        view.setBackgroundColor(Theme.getColor(Theme.key_dialogShadowLine));
        groupCallActivity.containerView.addView(view, LayoutHelper.createFrame(-1, 1.0f));
        for (int i10 = 0; i10 < 2; i10++) {
            groupCallActivity.undoView[i10] = new UndoView(activity3) { // from class: org.telegram.ui.GroupCallActivity.23
                C556223(final Context activity3) {
                    super(activity3);
                }

                @Override // org.telegram.p026ui.Components.UndoView
                public void showWithAction(long j, int i11, Object obj, Object obj2, Runnable runnable, Runnable runnable2) {
                    if (GroupCallActivity.this.previewDialog != null) {
                        return;
                    }
                    super.showWithAction(j, i11, obj, obj2, runnable, runnable2);
                }
            };
            groupCallActivity.undoView[i10].setAdditionalTranslationY(AndroidUtilities.m1081dp(10.0f));
            groupCallActivity.undoView[i10].setTranslationZ(AndroidUtilities.m1081dp(5.0f));
            groupCallActivity.containerView.addView(groupCallActivity.undoView[i10], LayoutHelper.createFrame(-1, -2.0f, 83, 8.0f, 0.0f, 8.0f, 8.0f));
        }
        AccountSelectCell accountSelectCell = new AccountSelectCell(activity3, true);
        groupCallActivity.accountSelectCell = accountSelectCell;
        accountSelectCell.setTag(C2702R.id.fit_width_tag, 240);
        groupCallActivity.otherItem.addSubItem(8, accountSelectCell, -2, AndroidUtilities.m1081dp(48.0f));
        groupCallActivity.otherItem.setShowSubmenuByMove(false);
        int i11 = Theme.key_voipgroup_listSelector;
        accountSelectCell.setBackground(Theme.createRadSelectorDrawable(Theme.getColor(i11), 6, 6));
        ActionBarMenuSubItem actionBarMenuSubItemAddSubItem = groupCallActivity.otherItem.addSubItem(1, 0, (CharSequence) LocaleController.getString(C2702R.string.VoipGroupAllCanSpeak), true);
        groupCallActivity.everyoneItem = actionBarMenuSubItemAddSubItem;
        actionBarMenuSubItemAddSubItem.updateSelectorBackground(true, false);
        ActionBarMenuSubItem actionBarMenuSubItemAddSubItem2 = groupCallActivity.otherItem.addSubItem(2, 0, (CharSequence) LocaleController.getString(C2702R.string.VoipGroupOnlyAdminsCanSpeak), true);
        groupCallActivity.adminItem = actionBarMenuSubItemAddSubItem2;
        actionBarMenuSubItemAddSubItem2.updateSelectorBackground(false, true);
        ActionBarMenuSubItem actionBarMenuSubItem = groupCallActivity.everyoneItem;
        int i12 = Theme.key_voipgroup_checkMenu;
        actionBarMenuSubItem.setCheckColor(i12);
        groupCallActivity.everyoneItem.setColors(Theme.getColor(i12), Theme.getColor(i12));
        groupCallActivity.adminItem.setCheckColor(i12);
        groupCallActivity.adminItem.setColors(Theme.getColor(i12), Theme.getColor(i12));
        Paint paint4 = new Paint(1);
        int i13 = Theme.key_voipgroup_actionBarItems;
        paint4.setColor(Theme.getColor(i13));
        paint4.setStyle(Paint.Style.STROKE);
        paint4.setStrokeWidth(AndroidUtilities.m1081dp(1.5f));
        paint4.setStrokeCap(Paint.Cap.ROUND);
        ActionBarMenuSubItem actionBarMenuSubItemAddSubItem3 = groupCallActivity.otherItem.addSubItem(10, C2702R.drawable.msg_voice_speaker, null, LocaleController.getString(C2702R.string.VoipGroupAudio), true, false);
        groupCallActivity.soundItem = actionBarMenuSubItemAddSubItem3;
        actionBarMenuSubItemAddSubItem3.setItemHeight(56);
        ActionBarMenuSubItem actionBarMenuSubItemAddSubItem4 = groupCallActivity.otherItem.addSubItem(11, C2702R.drawable.msg_noise_on, null, LocaleController.getString(C2702R.string.VoipNoiseCancellation), true, false);
        groupCallActivity.noiseItem = actionBarMenuSubItemAddSubItem4;
        actionBarMenuSubItemAddSubItem4.setItemHeight(56);
        View viewAddDivider = groupCallActivity.otherItem.addDivider(ColorUtils.blendARGB(Theme.getColor(Theme.key_voipgroup_actionBar), -16777216, 0.3f));
        groupCallActivity.soundItemDivider = viewAddDivider;
        ((ViewGroup.MarginLayoutParams) viewAddDivider.getLayoutParams()).topMargin = 0;
        ((ViewGroup.MarginLayoutParams) viewAddDivider.getLayoutParams()).bottomMargin = 0;
        ActionBarMenuSubItem actionBarMenuSubItemAddSubItem5 = groupCallActivity.otherItem.addSubItem(6, C2702R.drawable.msg_edit, groupCallActivity.recordCallDrawable, LocaleController.getString(ChatObject.isChannelOrGiga(groupCallActivity.currentChat) ? C2702R.string.VoipChannelEditTitle : C2702R.string.VoipGroupEditTitle), true, false);
        groupCallActivity.editTitleItem = actionBarMenuSubItemAddSubItem5;
        ActionBarMenuSubItem actionBarMenuSubItemAddSubItem6 = groupCallActivity.otherItem.addSubItem(7, C2702R.drawable.msg_permissions, groupCallActivity.recordCallDrawable, LocaleController.getString(C2702R.string.VoipGroupEditPermissions), false, false);
        groupCallActivity.permissionItem = actionBarMenuSubItemAddSubItem6;
        ActionBarMenuSubItem actionBarMenuSubItemAddSubItem7 = groupCallActivity.otherItem.addSubItem(3, C2702R.drawable.msg_link, LocaleController.getString(C2702R.string.VoipGroupShareInviteLink));
        groupCallActivity.inviteItem = actionBarMenuSubItemAddSubItem7;
        groupCallActivity.recordCallDrawable = new RecordCallDrawable();
        ActionBarMenuSubItem actionBarMenuSubItemAddSubItem8 = groupCallActivity.otherItem.addSubItem(9, C2702R.drawable.msg_screencast, LocaleController.getString(C2702R.string.VoipChatStartScreenCapture));
        groupCallActivity.screenItem = actionBarMenuSubItemAddSubItem8;
        ActionBarMenuSubItem actionBarMenuSubItemAddSubItem9 = groupCallActivity.otherItem.addSubItem(5, 0, groupCallActivity.recordCallDrawable, LocaleController.getString(C2702R.string.VoipGroupRecordCall), true, false);
        groupCallActivity.recordItem = actionBarMenuSubItemAddSubItem9;
        groupCallActivity.recordCallDrawable.setParentView(actionBarMenuSubItemAddSubItem9.getImageView());
        ActionBarMenuSubItem actionBarMenuSubItemAddSubItem10 = groupCallActivity.otherItem.addSubItem(12, C2702R.drawable.menu_stream_comments_24, LocaleController.getString(C2702R.string.VoipChannelEnableComments));
        groupCallActivity.enableComments = actionBarMenuSubItemAddSubItem10;
        ActionBarMenuSubItem actionBarMenuSubItemAddSubItem11 = groupCallActivity.otherItem.addSubItem(13, C2702R.drawable._menu_stream_comments_off_24, LocaleController.getString(C2702R.string.VoipChannelDisableComments));
        groupCallActivity.disableComments = actionBarMenuSubItemAddSubItem11;
        ActionBarMenuItem actionBarMenuItem4 = groupCallActivity.otherItem;
        int i14 = C2702R.drawable.msg_cancel;
        if (groupCallActivity.isConference()) {
            i2 = i13;
            string = LocaleController.getString(C2702R.string.VoipGroupEndConference);
        } else {
            string = LocaleController.getString(ChatObject.isChannelOrGiga(groupCallActivity.currentChat) ? C2702R.string.VoipChannelEndChat : C2702R.string.VoipGroupEndChat);
            i2 = i13;
        }
        ActionBarMenuSubItem actionBarMenuSubItemAddSubItem12 = actionBarMenuItem4.addSubItem(4, i14, string);
        groupCallActivity.leaveItem = actionBarMenuSubItemAddSubItem12;
        groupCallActivity.otherItem.setPopupItemsSelectorColor(Theme.getColor(i11));
        groupCallActivity.otherItem.getPopupLayout().setFitItems(true);
        actionBarMenuSubItemAddSubItem10.setColors(Theme.getColor(i2), Theme.getColor(i2));
        actionBarMenuSubItemAddSubItem11.setColors(Theme.getColor(i2), Theme.getColor(i2));
        groupCallActivity.soundItem.setColors(Theme.getColor(i2), Theme.getColor(i2));
        actionBarMenuSubItemAddSubItem4.setColors(Theme.getColor(i2), Theme.getColor(i2));
        int i15 = Theme.key_voipgroup_leaveCallMenu;
        actionBarMenuSubItemAddSubItem12.setColors(Theme.getColor(i15), Theme.getColor(i15));
        actionBarMenuSubItemAddSubItem7.setColors(Theme.getColor(i2), Theme.getColor(i2));
        actionBarMenuSubItemAddSubItem5.setColors(Theme.getColor(i2), Theme.getColor(i2));
        actionBarMenuSubItemAddSubItem6.setColors(Theme.getColor(i2), Theme.getColor(i2));
        actionBarMenuSubItemAddSubItem9.setColors(Theme.getColor(i2), Theme.getColor(i2));
        actionBarMenuSubItemAddSubItem8.setColors(Theme.getColor(i2), Theme.getColor(i2));
        if (groupCallActivity.call != null) {
            groupCallActivity.initCreatedGroupCall();
        }
        if (groupCallActivity.isConference()) {
            groupCallActivity.encryptionDrawable = new CallEncryptionCellDrawable(activity3);
            VoIPService sharedInstance = VoIPService.getSharedInstance();
            groupCallActivity.encryptionDrawable.setEmojis((sharedInstance == null || (conferenceCall = sharedInstance.conference) == null) ? null : conferenceCall.getEmojis());
        }
        groupCallActivity.updateTitle(false);
        groupCallActivity.actionBar.getTitleTextView().setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda14
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$new$36(view2);
            }
        });
        groupCallActivity.fullscreenUsersListView = new RecyclerListView(activity3) { // from class: org.telegram.ui.GroupCallActivity.24
            C556324(final Context activity3) {
                super(activity3);
            }

            @Override // org.telegram.p026ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
            public boolean drawChild(Canvas canvas, View view2, long j) {
                GroupCallFullscreenAdapter.GroupCallUserCell groupCallUserCell = (GroupCallFullscreenAdapter.GroupCallUserCell) view2;
                if (!GroupCallActivity.this.renderersContainer.isAnimating() && !GroupCallActivity.this.fullscreenListItemAnimator.isRunning()) {
                    groupCallUserCell.setAlpha(1.0f);
                    groupCallUserCell.setTranslationX(0.0f);
                    groupCallUserCell.setTranslationY(0.0f);
                }
                if (groupCallUserCell.isRemoving(GroupCallActivity.this.fullscreenUsersListView) && groupCallUserCell.getRenderer() != null) {
                    return true;
                }
                if (groupCallUserCell.getTranslationY() != 0.0f && groupCallUserCell.getRenderer() != null && groupCallUserCell.getRenderer().primaryView != null) {
                    float top = GroupCallActivity.this.listView.getTop() - getTop();
                    float f = GroupCallActivity.this.renderersContainer.progressToFullscreenMode;
                    canvas.save();
                    float f2 = 1.0f - f;
                    canvas.clipRect(0.0f, top * f2, getMeasuredWidth(), ((GroupCallActivity.this.listView.getMeasuredHeight() + top) * f2) + (getMeasuredHeight() * f));
                    boolean zDrawChild = super.drawChild(canvas, view2, j);
                    canvas.restore();
                    return zDrawChild;
                }
                return super.drawChild(canvas, view2, j);
            }
        };
        C556425 c556425 = new DefaultItemAnimator() { // from class: org.telegram.ui.GroupCallActivity.25
            C556425() {
            }

            @Override // androidx.recyclerview.widget.DefaultItemAnimator
            protected void onMoveAnimationUpdate(RecyclerView.ViewHolder viewHolder) {
                GroupCallActivity.this.listView.invalidate();
                GroupCallActivity.this.renderersContainer.invalidate();
                ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
                GroupCallActivity.this.updateLayout(true);
            }
        };
        groupCallActivity.fullscreenListItemAnimator = c556425;
        groupCallActivity.fullscreenUsersListView.setClipToPadding(false);
        c556425.setDelayAnimations(false);
        c556425.setTranslationInterpolator(CubicBezierInterpolator.DEFAULT);
        c556425.setRemoveDuration(350L);
        c556425.setAddDuration(350L);
        c556425.setMoveDuration(350L);
        groupCallActivity.fullscreenUsersListView.setItemAnimator(c556425);
        groupCallActivity.fullscreenUsersListView.setOnScrollListener(new RecyclerView.OnScrollListener() { // from class: org.telegram.ui.GroupCallActivity.26
            C556526() {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i16, int i17) {
                super.onScrolled(recyclerView, i16, i17);
                ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
                GroupCallActivity.this.renderersContainer.invalidate();
            }
        });
        groupCallActivity.fullscreenUsersListView.setClipChildren(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity3);
        linearLayoutManager.setOrientation(0);
        groupCallActivity.fullscreenUsersListView.setLayoutManager(linearLayoutManager);
        RecyclerListView recyclerListView4 = groupCallActivity.fullscreenUsersListView;
        GroupCallFullscreenAdapter groupCallFullscreenAdapter = new GroupCallFullscreenAdapter(call, groupCallActivity.currentAccount, groupCallActivity);
        groupCallActivity.fullscreenAdapter = groupCallFullscreenAdapter;
        recyclerListView4.setAdapter(groupCallFullscreenAdapter);
        groupCallActivity.fullscreenAdapter.setVisibility(groupCallActivity.fullscreenUsersListView, false);
        groupCallActivity.fullscreenUsersListView.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda15
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
            public final void onItemClick(View view2, int i16) {
                this.f$0.lambda$new$37(view2, i16);
            }
        });
        groupCallActivity.fullscreenUsersListView.setOnItemLongClickListener(new RecyclerListView.OnItemLongClickListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda16
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListener
            public final boolean onItemClick(View view2, int i16) {
                return this.f$0.lambda$new$38(view2, i16);
            }
        });
        groupCallActivity.fullscreenUsersListView.setVisibility(8);
        groupCallActivity.fullscreenUsersListView.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: org.telegram.ui.GroupCallActivity.27
            C556627() {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view2, RecyclerView recyclerView, RecyclerView.State state) {
                recyclerView.getChildAdapterPosition(view2);
                if (!GroupCallActivity.isLandscapeMode) {
                    rect.set(AndroidUtilities.m1081dp(4.0f), 0, AndroidUtilities.m1081dp(4.0f), 0);
                } else {
                    rect.set(0, AndroidUtilities.m1081dp(4.0f), 0, AndroidUtilities.m1081dp(4.0f));
                }
            }
        });
        C556728 c556728 = new GroupCallRenderersContainer(activity3, groupCallActivity.listView, groupCallActivity.fullscreenUsersListView, groupCallActivity.attachedRenderers, groupCallActivity.call, this) { // from class: org.telegram.ui.GroupCallActivity.28
            C556728(final Context activity3, RecyclerView recyclerView, RecyclerView recyclerView2, ArrayList arrayList, ChatObject.Call call2, final GroupCallActivity this) {
                super(activity3, recyclerView, recyclerView2, arrayList, call2, this);
            }

            @Override // org.telegram.p026ui.Components.voip.GroupCallRenderersContainer
            protected void update() {
                super.update();
                ((BottomSheet) GroupCallActivity.this).navBarColor = AndroidUtilities.getOffsetColor(Theme.getColor(Theme.key_voipgroup_actionBarUnscrolled), Theme.getColor(Theme.key_voipgroup_actionBar), Math.max(GroupCallActivity.this.colorProgress, GroupCallActivity.this.renderersContainer == null ? 0.0f : GroupCallActivity.this.renderersContainer.progressToFullscreenMode), 1.0f);
                ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
                GroupCallActivity groupCallActivity2 = GroupCallActivity.this;
                groupCallActivity2.setColorProgress(groupCallActivity2.colorProgress);
            }

            @Override // org.telegram.p026ui.Components.voip.GroupCallRenderersContainer, android.view.ViewGroup
            protected boolean drawChild(Canvas canvas, View view2, long j) {
                if (view2 == GroupCallActivity.this.scrimRenderer) {
                    return true;
                }
                return super.drawChild(canvas, view2, j);
            }

            @Override // org.telegram.p026ui.Components.voip.GroupCallRenderersContainer
            protected void onFullScreenModeChanged(boolean z2) {
                GroupCallActivity.this.delayedGroupCallUpdated = z2;
                if (GroupCallActivity.isTabletMode) {
                    if (z2 || !GroupCallActivity.this.renderersContainer.inFullscreenMode) {
                        return;
                    }
                    GroupCallActivity groupCallActivity2 = GroupCallActivity.this;
                    groupCallActivity2.tabletGridAdapter.setVisibility(groupCallActivity2.tabletVideoGridView, false, true);
                    return;
                }
                if (z2) {
                    GroupCallActivity.this.undoView[0].hide(false, 1);
                    GroupCallActivity.this.renderersContainer.undoView[0].hide(false, 2);
                    if (!GroupCallActivity.this.renderersContainer.inFullscreenMode) {
                        GroupCallActivity.this.listView.setVisibility(0);
                        GroupCallActivity.this.actionBar.setVisibility(0);
                        if (GroupCallActivity.this.watchersView != null) {
                            GroupCallActivity.this.watchersView.setVisibility(0);
                        }
                    }
                    GroupCallActivity.this.updateState(true, false);
                    GroupCallActivity.this.buttonsContainer.requestLayout();
                    if (GroupCallActivity.this.fullscreenUsersListView.getVisibility() != 0) {
                        GroupCallActivity.this.fullscreenUsersListView.setVisibility(0);
                        GroupCallActivity groupCallActivity3 = GroupCallActivity.this;
                        groupCallActivity3.fullscreenAdapter.setVisibility(groupCallActivity3.fullscreenUsersListView, true);
                        GroupCallActivity groupCallActivity4 = GroupCallActivity.this;
                        groupCallActivity4.fullscreenAdapter.update(false, groupCallActivity4.fullscreenUsersListView);
                    } else {
                        GroupCallActivity groupCallActivity5 = GroupCallActivity.this;
                        groupCallActivity5.fullscreenAdapter.setVisibility(groupCallActivity5.fullscreenUsersListView, true);
                        GroupCallActivity.this.applyCallParticipantUpdates(true);
                    }
                } else {
                    if (!GroupCallActivity.this.renderersContainer.inFullscreenMode) {
                        GroupCallActivity.this.fullscreenUsersListView.setVisibility(8);
                        GroupCallActivity groupCallActivity6 = GroupCallActivity.this;
                        groupCallActivity6.fullscreenAdapter.setVisibility(groupCallActivity6.fullscreenUsersListView, false);
                    } else {
                        GroupCallActivity.this.actionBar.setVisibility(8);
                        GroupCallActivity.this.listView.setVisibility(8);
                        if (GroupCallActivity.this.watchersView != null) {
                            GroupCallActivity.this.watchersView.setVisibility(8);
                        }
                    }
                    if (GroupCallActivity.this.fullscreenUsersListView.getVisibility() == 0) {
                        for (int i16 = 0; i16 < GroupCallActivity.this.fullscreenUsersListView.getChildCount(); i16++) {
                            View childAt = GroupCallActivity.this.fullscreenUsersListView.getChildAt(i16);
                            childAt.setAlpha(1.0f);
                            childAt.setScaleX(1.0f);
                            childAt.setScaleY(1.0f);
                            childAt.setTranslationX(0.0f);
                            childAt.setTranslationY(0.0f);
                            ((GroupCallFullscreenAdapter.GroupCallUserCell) childAt).setProgressToFullscreen(GroupCallActivity.this.renderersContainer.progressToFullscreenMode);
                        }
                    }
                }
                GroupCallActivity.this.buttonsBackgroundGradientView2.setVisibility(z2 ? 0 : 8);
                if (GroupCallActivity.this.delayedGroupCallUpdated) {
                    return;
                }
                GroupCallActivity.this.applyCallParticipantUpdates(true);
            }

            @Override // org.telegram.p026ui.Components.voip.GroupCallRenderersContainer
            public void onUiVisibilityChanged() {
                if (GroupCallActivity.this.renderersContainer == null) {
                    return;
                }
                GroupCallActivity.this.animatorHideButtons.setValue(!GroupCallActivity.this.renderersContainer.isUiVisible(), true);
            }

            @Override // org.telegram.p026ui.Components.voip.GroupCallRenderersContainer
            protected boolean canHideUI() {
                return super.canHideUI() && GroupCallActivity.this.previewDialog == null;
            }

            @Override // org.telegram.p026ui.Components.voip.GroupCallRenderersContainer
            protected void onBackPressed() {
                GroupCallActivity.this.lambda$openCrafting$9();
            }
        };
        groupCallActivity.renderersContainer = c556728;
        c556728.setClipChildren(false);
        groupCallActivity.fullscreenAdapter.setRenderersPool(groupCallActivity.attachedRenderers, groupCallActivity.renderersContainer);
        if (groupCallActivity.tabletVideoGridView != null) {
            groupCallActivity.tabletGridAdapter.setRenderersPool(groupCallActivity.attachedRenderers, groupCallActivity.renderersContainer);
        }
        C556829 c556829 = new AvatarPreviewPagerIndicator(activity3) { // from class: org.telegram.ui.GroupCallActivity.29
            C556829(final Context activity3) {
                super(activity3);
            }

            @Override // org.telegram.p026ui.AvatarPreviewPagerIndicator, org.telegram.ui.Components.ProfileGalleryView.Callback
            public void onPhotosLoaded() {
                super.onPhotosLoaded();
                long dialogId = GroupCallActivity.this.avatarsViewPager.getDialogId();
                if (dialogId > 0) {
                    TLRPC.User user = GroupCallActivity.this.accountInstance.getMessagesController().getUser(Long.valueOf(dialogId));
                    GroupCallActivity.this.avatarsViewPager.initIfEmpty(null, ImageLocation.getForUserOrChat(user, 0), ImageLocation.getForUserOrChat(user, 1), false);
                }
            }
        };
        groupCallActivity.avatarPagerIndicator = c556829;
        C557030 c557030 = new ProfileGalleryView(activity3, groupCallActivity.actionBar, groupCallActivity.listView, c556829) { // from class: org.telegram.ui.GroupCallActivity.30
            C557030(final Context activity3, ActionBar actionBar, RecyclerListView recyclerListView5, ProfileGalleryView.Callback c5568292) {
                super(activity3, actionBar, recyclerListView5, c5568292);
            }

            @Override // android.view.View
            public void invalidate() {
                super.invalidate();
                ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
            }
        };
        groupCallActivity.avatarsViewPager = c557030;
        c557030.setImagesLayerNum(Integer.MAX_VALUE);
        c557030.setInvalidateWithParent(true);
        c5568292.setProfileGalleryView(c557030);
        C557131 c557131 = new FrameLayout(activity3) { // from class: org.telegram.ui.GroupCallActivity.31
            final Rect rect = new Rect();
            final RectF rectF = new RectF();
            final Path path = new Path();

            C557131(final Context activity3) {
                super(activity3);
                this.rect = new Rect();
                this.rectF = new RectF();
                this.path = new Path();
            }

            @Override // android.widget.FrameLayout, android.view.View
            protected void onMeasure(int i16, int i17) {
                int iMin = Math.min(View.MeasureSpec.getSize(i16), View.MeasureSpec.getSize(i17));
                super.onMeasure(View.MeasureSpec.makeMeasureSpec(iMin, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(iMin + getPaddingBottom(), TLObject.FLAG_30));
            }

            @Override // android.view.ViewGroup, android.view.View
            protected void dispatchDraw(Canvas canvas) {
                if (GroupCallActivity.this.progressToAvatarPreview != 1.0f) {
                    if (GroupCallActivity.this.scrimView != null && GroupCallActivity.this.hasScrimAnchorView) {
                        canvas.save();
                        float avatarCorners = ExteraConfig.getAvatarCorners(GroupCallActivity.this.scrimView.getAvatarImageView().getMeasuredHeight(), true) * (getMeasuredHeight() / GroupCallActivity.this.scrimView.getAvatarImageView().getMeasuredHeight());
                        int iM1081dp = (int) (((1.0f - GroupCallActivity.this.progressToAvatarPreview) * avatarCorners) + (AndroidUtilities.m1081dp(13.0f) * GroupCallActivity.this.progressToAvatarPreview));
                        int i16 = (int) (avatarCorners * (1.0f - GroupCallActivity.this.progressToAvatarPreview));
                        GroupCallActivity.this.scrimView.getAvatarWavesDrawable().draw(canvas, GroupCallActivity.this.scrimView.getAvatarImageView().getMeasuredHeight() / 2, GroupCallActivity.this.scrimView.getAvatarImageView().getMeasuredHeight() / 2, this);
                        GroupCallActivity.this.scrimView.getAvatarImageView().getImageReceiver().setImageCoords(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight());
                        GroupCallActivity.this.scrimView.getAvatarImageView().setRoundRadius(iM1081dp, iM1081dp, i16, i16);
                        GroupCallActivity.this.scrimView.getAvatarImageView().getImageReceiver().draw(canvas);
                        GroupCallActivity.this.scrimView.getAvatarImageView().setRoundRadius(ExteraConfig.getAvatarCorners(GroupCallActivity.this.scrimView.getAvatarImageView().getMeasuredHeight(), true));
                        canvas.restore();
                    } else if (GroupCallActivity.this.scrimFullscreenView != null && GroupCallActivity.this.scrimRenderer == null && GroupCallActivity.this.previewTextureTransitionEnabled) {
                        canvas.save();
                        float avatarCorners2 = ExteraConfig.getAvatarCorners(GroupCallActivity.this.scrimFullscreenView.getAvatarImageView().getMeasuredHeight(), true) * (getMeasuredHeight() / GroupCallActivity.this.scrimFullscreenView.getAvatarImageView().getMeasuredHeight());
                        int iM1081dp2 = (int) (((1.0f - GroupCallActivity.this.progressToAvatarPreview) * avatarCorners2) + (AndroidUtilities.m1081dp(13.0f) * GroupCallActivity.this.progressToAvatarPreview));
                        int i17 = (int) (avatarCorners2 * (1.0f - GroupCallActivity.this.progressToAvatarPreview));
                        GroupCallActivity.this.scrimFullscreenView.getAvatarImageView().getImageReceiver().setImageCoords(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight());
                        GroupCallActivity.this.scrimFullscreenView.getAvatarImageView().setRoundRadius(iM1081dp2, iM1081dp2, i17, i17);
                        GroupCallActivity.this.scrimFullscreenView.getAvatarImageView().getImageReceiver().draw(canvas);
                        GroupCallActivity.this.scrimFullscreenView.getAvatarImageView().setRoundRadius(ExteraConfig.getAvatarCorners(GroupCallActivity.this.scrimFullscreenView.getAvatarImageView().getMeasuredHeight(), true));
                        canvas.restore();
                    }
                }
                GroupCallActivity.this.avatarsViewPager.setAlpha(GroupCallActivity.this.progressToAvatarPreview);
                this.path.reset();
                this.rectF.set(0.0f, 0.0f, getMeasuredHeight(), getMeasuredWidth());
                this.path.addRoundRect(this.rectF, new float[]{AndroidUtilities.m1081dp(13.0f), AndroidUtilities.m1081dp(13.0f), AndroidUtilities.m1081dp(13.0f), AndroidUtilities.m1081dp(13.0f), 0.0f, 0.0f, 0.0f, 0.0f}, Path.Direction.CCW);
                canvas.save();
                canvas.clipPath(this.path);
                View viewFindVideoActiveView = GroupCallActivity.this.avatarsViewPager.findVideoActiveView();
                if (viewFindVideoActiveView != null && GroupCallActivity.this.scrimRenderer != null && GroupCallActivity.this.scrimRenderer.isAttached() && !GroupCallActivity.this.drawingForBlur) {
                    canvas.save();
                    this.rect.setEmpty();
                    GroupCallActivity.this.avatarsViewPager.getChildVisibleRect(viewFindVideoActiveView, this.rect, null);
                    int measuredWidth = this.rect.left;
                    if (measuredWidth < (-GroupCallActivity.this.avatarsViewPager.getMeasuredWidth())) {
                        measuredWidth += GroupCallActivity.this.avatarsViewPager.getMeasuredWidth() * 2;
                    } else if (measuredWidth > GroupCallActivity.this.avatarsViewPager.getMeasuredWidth()) {
                        measuredWidth -= GroupCallActivity.this.avatarsViewPager.getMeasuredWidth() * 2;
                    }
                    canvas.translate(measuredWidth, 0.0f);
                    GroupCallActivity.this.scrimRenderer.draw(canvas);
                    canvas.restore();
                }
                super.dispatchDraw(canvas);
                canvas.restore();
            }

            @Override // android.view.View
            public void invalidate() {
                super.invalidate();
                ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
            }
        };
        groupCallActivity.avatarPreviewContainer = c557131;
        c557131.setVisibility(8);
        c557030.setVisibility(0);
        c557030.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: org.telegram.ui.GroupCallActivity.32
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i16) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i16, float f, int i17) {
            }

            C557232() {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i16) {
                GroupCallActivity.this.avatarsViewPager.getRealPosition(i16);
                GroupCallActivity.this.avatarPagerIndicator.saveCurrentPageProgress();
                GroupCallActivity.this.avatarPagerIndicator.invalidate();
            }
        });
        C557333 c557333 = new View(activity3) { // from class: org.telegram.ui.GroupCallActivity.33
            C557333(final Context activity3) {
                super(activity3);
            }

            @Override // android.view.View
            public void setAlpha(float f) {
                if (getAlpha() != f) {
                    super.setAlpha(f);
                    GroupCallActivity.this.checkContentOverlayed();
                }
            }
        };
        groupCallActivity.blurredView = c557333;
        groupCallActivity.containerView.addView(groupCallActivity.renderersContainer);
        groupCallActivity.renderersContainer.addView(groupCallActivity.fullscreenUsersListView, LayoutHelper.createFrame(-1, 80.0f, 80, 0.0f, 0.0f, 0.0f, 100.0f));
        groupCallActivity.buttonsContainer.setWillNotDraw(false);
        View view2 = new View(activity3);
        groupCallActivity.buttonsBackgroundGradientView = view2;
        int[] iArr = groupCallActivity.gradientColors;
        iArr[0] = groupCallActivity.backgroundColor;
        iArr[1] = 0;
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, groupCallActivity.gradientColors);
        groupCallActivity.buttonsBackgroundGradient = gradientDrawable;
        view2.setBackground(gradientDrawable);
        groupCallActivity.containerView.addView(view2, LayoutHelper.createFrame(-1, 60, 83));
        View view3 = new View(activity3);
        groupCallActivity.buttonsBackgroundGradientView2 = view3;
        view3.setBackgroundColor(groupCallActivity.gradientColors[0]);
        groupCallActivity.containerView.addView(view3, LayoutHelper.createFrame(-1, 0, 83));
        GroupCallMessagesListView groupCallMessagesListView = new GroupCallMessagesListView(activity3);
        groupCallActivity.groupCallMessagesListView = groupCallMessagesListView;
        groupCallMessagesListView.setDelegate(new GroupCallMessagesListView.Delegate() { // from class: org.telegram.ui.GroupCallActivity.34
            C557434() {
            }

            @Override // org.telegram.ui.Components.conference.message.GroupCallMessagesListView.Delegate
            public void showReaction(GroupCallMessageCell groupCallMessageCell, ReactionsLayoutInBubble.VisibleReaction visibleReaction) {
                ReactionsEffectOverlay reactionsEffectOverlay = new ReactionsEffectOverlay(GroupCallActivity.this.getContext(), null, GroupCallActivity.this.reactionsContainerLayout, groupCallMessageCell, null, 0.0f, 0.0f, visibleReaction, ((BottomSheet) GroupCallActivity.this).currentAccount, 1, false);
                ReactionsEffectOverlay.currentOverlay = reactionsEffectOverlay;
                reactionsEffectOverlay.windowView.setTag(C2702R.id.parent_tag, 1);
                GroupCallActivity.this.container.addView(reactionsEffectOverlay.windowView);
                reactionsEffectOverlay.started = true;
                reactionsEffectOverlay.startTime = System.currentTimeMillis();
            }
        });
        groupCallMessagesListView.setClickCellDelegate(new GroupCallMessageCell.Delegate() { // from class: org.telegram.ui.GroupCallActivity.35
            C557535() {
            }

            @Override // org.telegram.ui.Components.conference.message.GroupCallMessageCell.Delegate
            public void didClickAvatar(GroupCallMessageCell groupCallMessageCell, GroupCallMessage groupCallMessage, float f, float f2) {
                openSenderProfile(groupCallMessage);
            }

            @Override // org.telegram.ui.Components.conference.message.GroupCallMessageCell.Delegate
            public void didClickSenderName(GroupCallMessageCell groupCallMessageCell, GroupCallMessage groupCallMessage) {
                openSenderProfile(groupCallMessage);
            }

            private void openSenderProfile(GroupCallMessage groupCallMessage) {
                BaseFragment lastFragment = LaunchActivity.getLastFragment();
                if (lastFragment == null) {
                    return;
                }
                if ((lastFragment instanceof ProfileActivity) && ((ProfileActivity) lastFragment).getDialogId() == groupCallMessage.fromId) {
                    GroupCallActivity.this.lambda$new$0();
                    return;
                }
                int iCalculateScrollTopOffset = GroupCallActivity.this.calculateScrollTopOffset();
                Bundle bundle = new Bundle();
                long j = groupCallMessage.fromId;
                if (j > 0) {
                    bundle.putLong("user_id", j);
                } else {
                    bundle.putLong("chat_id", -j);
                }
                boolean z2 = true;
                if (groupCallMessage.fromId == GroupCallActivity.this.accountInstance.getUserConfig().getClientUserId()) {
                    bundle.putBoolean("my_profile", true);
                }
                ProfileActivity profileActivity = new ProfileActivity(bundle);
                if (iCalculateScrollTopOffset > 0 && iCalculateScrollTopOffset != Integer.MAX_VALUE) {
                    z2 = false;
                }
                lastFragment.presentFragment(profileActivity, false, z2);
                GroupCallActivity.this.lambda$new$0();
            }
        });
        if (groupCallActivity.call != null) {
            groupCallMessagesListView.setGroupCall(groupCallActivity.accountInstance.getCurrentAccount(), groupCallActivity.call.getInputGroupCall(false));
        }
        groupCallActivity.containerView.addView(groupCallMessagesListView, LayoutHelper.createFrame(-1, -1.0f, 0, 0.0f, 0.0f, 0.0f, 0.0f));
        AnimatedTextView animatedTextView = new AnimatedTextView(activity3, false, true, true);
        groupCallActivity.limitTextView = animatedTextView;
        animatedTextView.setGravity(17);
        animatedTextView.setTextSize(AndroidUtilities.m1081dp(15.0f));
        animatedTextView.setTextColor(-1);
        animatedTextView.setAnimationProperties(0.4f, 0L, 320L, CubicBezierInterpolator.EASE_OUT_QUINT);
        animatedTextView.setTypeface(AndroidUtilities.bold());
        groupCallActivity.containerView.addView(groupCallActivity.buttonsContainer);
        C557636 c557636 = new EditTextEmoji(activity3, groupCallActivity.sizeNotifierFrameLayout, LaunchActivity.getLastFragment(), 5, true, groupCallActivity.resourcesProvider) { // from class: org.telegram.ui.GroupCallActivity.36
            @Override // org.telegram.p026ui.Components.EditTextEmoji
            protected boolean allowSearch() {
                return true;
            }

            C557636(final Context activity3, SizeNotifierFrameLayout sizeNotifierFrameLayout, BaseFragment baseFragment, int i16, boolean z2, Theme.ResourcesProvider resourcesProvider) {
                super(activity3, sizeNotifierFrameLayout, baseFragment, i16, z2, resourcesProvider);
            }

            @Override // android.widget.FrameLayout, android.view.View
            protected void onMeasure(int i16, int i17) {
                super.onMeasure(i16, i17);
                if (GroupCallActivity.this.animatorMessageInputHeight.getFactor() == 0.0f) {
                    GroupCallActivity.this.animatorMessageInputHeight.forceFactor(getMeasuredHeight());
                } else {
                    GroupCallActivity.this.animatorMessageInputHeight.animateTo(getMeasuredHeight());
                }
            }

            @Override // android.view.ViewGroup
            protected boolean drawChild(Canvas canvas, View view4, long j) {
                if (view4 == getEditText()) {
                    canvas.save();
                    GroupCallActivity.this.callMessageEnterView.getEditText().setTranslationY(view4.getMeasuredHeight() - GroupCallActivity.this.animatorMessageInputHeight.getFactor());
                    boolean zDrawChild = super.drawChild(canvas, view4, j);
                    canvas.restore();
                    return zDrawChild;
                }
                return super.drawChild(canvas, view4, j);
            }

            @Override // org.telegram.p026ui.Components.EditTextEmoji
            protected void updatedEmojiExpanded() {
                super.updatedEmojiExpanded();
                ((BottomSheet) GroupCallActivity.this).containerView.requestApplyInsets();
            }

            @Override // org.telegram.p026ui.Components.EditTextEmoji
            protected void onEmojiKeyboardUpdate() {
                int iMax;
                super.onEmojiKeyboardUpdate();
                if (isPopupShowing()) {
                    iMax = Math.max(0, getEmojiPadding());
                } else {
                    iMax = isWaitingForKeyboardOpen() ? Math.max(0, getKeyboardHeight()) : 0;
                }
                if (iMax > 0) {
                    GroupCallActivity.this.windowInsetsStateHolder.requestInAppKeyboardHeight(iMax);
                } else {
                    GroupCallActivity.this.windowInsetsStateHolder.resetInAppKeyboardHeight(false);
                }
            }

            @Override // org.telegram.p026ui.Components.EditTextEmoji
            protected void createEmojiView() {
                super.createEmojiView();
                EmojiView emojiView = getEmojiView();
                if (emojiView != null) {
                    emojiView.shouldLightenBackground = false;
                    emojiView.fixBottomTabContainerTranslation = false;
                    emojiView.setShouldDrawBackground(false);
                    emojiView.setBottomInset(((BottomSheet) GroupCallActivity.this).containerView.getPaddingBottom());
                }
            }
        };
        groupCallActivity.callMessageEnterView = c557636;
        c557636.includeNavigationBar = true;
        c557636.setFilters(new InputFilter[]{new InputFilter.LengthFilter(groupCallActivity.maxGroupCallMessageLength)});
        c557636.getEditText().setLinkTextColor(-11683585);
        c557636.setHint(LocaleController.getString(C2702R.string.TypeMessage));
        c557636.getEditText().addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.GroupCallActivity.37
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i16, int i17, int i18) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i16, int i17, int i18) {
            }

            C557737() {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                String str2;
                GroupCallActivity.this.animatorMessageIsEmpty.setValue(TextUtils.isEmpty(editable), true);
                int iCodePointCount = Character.codePointCount(editable, 0, editable.length());
                int i16 = GroupCallActivity.this.maxGroupCallMessageLength;
                if (iCodePointCount + 25 > i16) {
                    str2 = _UrlKt.FRAGMENT_ENCODE_SET + (i16 - iCodePointCount);
                } else {
                    str2 = null;
                }
                GroupCallActivity.this.limitTextView.cancelAnimation();
                GroupCallActivity.this.limitTextView.setText(str2);
                GroupCallActivity.this.limitTextView.setTextColor(iCodePointCount >= i16 ? -1280137 : -1);
                if (iCodePointCount > i16) {
                    BotWebViewVibrationEffect.APP_ERROR.vibrate();
                }
            }
        });
        c557636.onResume();
        C557838 c557838 = new FrameLayout(activity3) { // from class: org.telegram.ui.GroupCallActivity.38
            private final RectF tmpRect = new RectF();
            private final RectF tmpRect2 = new RectF();
            private final RectF tmpRect3 = new RectF();
            private final Paint backgroundPaint = new Paint(1);

            C557838(final Context activity3) {
                super(activity3);
                this.tmpRect = new RectF();
                this.tmpRect2 = new RectF();
                this.tmpRect3 = new RectF();
                this.backgroundPaint = new Paint(1);
            }

            @Override // android.view.ViewGroup, android.view.View
            protected void dispatchDraw(Canvas canvas) {
                this.tmpRect.set(0.0f, (GroupCallActivity.this.callMessageEnterContainer.getY() + GroupCallActivity.this.callMessageEnterContainer.getMeasuredHeight()) - GroupCallActivity.this.animatorMessageInputHeight.getFactor(), getMeasuredWidth(), getMeasuredHeight());
                this.tmpRect2.set(0.0f, GroupCallActivity.this.callMessageEnterContainer.getY() + GroupCallActivity.this.callMessageEnterContainer.getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight());
                this.tmpRect3.set(0.0f, (GroupCallActivity.this.callMessageEnterContainer.getY() + GroupCallActivity.this.callMessageEnterContainer.getMeasuredHeight()) - GroupCallActivity.this.animatorMessageInputHeight.getFactor(), getMeasuredWidth(), GroupCallActivity.this.callMessageEnterContainer.getY() + GroupCallActivity.this.callMessageEnterContainer.getMeasuredHeight());
                if (Build.VERSION.SDK_INT >= 29 && GroupCallActivity.this.renderNodeBlur != null && canvas.isHardwareAccelerated()) {
                    this.backgroundPaint.setColor(-14933463);
                    canvas.drawRect(this.tmpRect, this.backgroundPaint);
                    canvas.save();
                    canvas.clipRect(this.tmpRect);
                    canvas.translate(-getX(), -getY());
                    canvas.scale(GroupCallActivity.this.renderNodeBlurScale, GroupCallActivity.this.renderNodeBlurScale);
                    canvas.drawRenderNode(GroupCallActivity.this.renderNodeBlur);
                    canvas.restore();
                    this.backgroundPaint.setColor(234881023);
                    canvas.drawRect(this.tmpRect2, this.backgroundPaint);
                } else {
                    this.backgroundPaint.setColor(-14933463);
                    canvas.drawRect(this.tmpRect3, this.backgroundPaint);
                    this.backgroundPaint.setColor(ColorUtils.compositeColors(234881023, -14933463));
                    canvas.drawRect(this.tmpRect2, this.backgroundPaint);
                }
                super.dispatchDraw(canvas);
            }
        };
        groupCallActivity.callMessageEnterUnderContainer = c557838;
        groupCallActivity.containerView.addView(c557838, LayoutHelper.createFrame(-1, -1.0f));
        C557939 c557939 = new FrameLayout(activity3) { // from class: org.telegram.ui.GroupCallActivity.39
            C557939(final Context activity3) {
                super(activity3);
            }

            @Override // android.view.ViewGroup, android.view.View
            public boolean dispatchTouchEvent(MotionEvent motionEvent) {
                if (getAlpha() <= 0.95f) {
                    return false;
                }
                return super.dispatchTouchEvent(motionEvent);
            }
        };
        groupCallActivity.callMessageEnterContainer = c557939;
        c557939.addView(c557636, LayoutHelper.createFrame(-1, -2.0f, 80, 0.0f, 0.0f, 48.0f, 0.0f));
        c557939.addView(animatedTextView, LayoutHelper.createFrame(52, 16.0f, 85, 0.0f, 0.0f, 0.0f, 32.0f));
        animatedTextView.setTranslationY(-AndroidUtilities.m1081dp(20.0f));
        groupCallActivity.containerView.addView(c557939, LayoutHelper.createFrame(-1, -2, 80));
        ImageView imageView3 = new ImageView(activity3);
        groupCallActivity.callMessageHideButton = imageView3;
        int i16 = Theme.key_listSelector;
        imageView3.setBackground(Theme.createSelectorDrawable(groupCallActivity.getThemedColor(i16)));
        int color2 = Theme.getColor(Theme.key_graySectionText, groupCallActivity.resourcesProvider);
        PorterDuff.Mode mode = PorterDuff.Mode.MULTIPLY;
        imageView3.setColorFilter(new PorterDuffColorFilter(color2, mode));
        ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER;
        imageView3.setScaleType(scaleType);
        imageView3.setImageResource(C2702R.drawable.arrow_more);
        imageView3.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda18
            @Override // android.view.View.OnClickListener
            public final void onClick(View view4) {
                this.f$0.lambda$new$39(view4);
            }
        });
        ImageView imageView4 = new ImageView(activity3);
        groupCallActivity.callMessageSendButton = imageView4;
        imageView4.setBackground(Theme.createSelectorDrawable(groupCallActivity.getThemedColor(i16)));
        imageView4.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_windowBackgroundWhiteBlueIcon, groupCallActivity.resourcesProvider), mode));
        imageView4.setScaleType(scaleType);
        imageView4.setImageResource(C2702R.drawable.ic_send);
        imageView4.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda19
            @Override // android.view.View.OnClickListener
            public final void onClick(View view4) {
                this.f$0.lambda$new$40(view4);
            }
        });
        c557939.addView(imageView3, LayoutHelper.createFrame(48, 48, 85));
        c557939.addView(imageView4, LayoutHelper.createFrame(48, 48, 85));
        groupCallActivity.containerView.addView(c557333);
        c557131.addView(c557030, LayoutHelper.createFrame(-1, -1.0f));
        c557131.addView(c5568292, LayoutHelper.createFrame(-1, -1.0f, 0, 0.0f, 0.0f, 0.0f, 0.0f));
        groupCallActivity.containerView.addView(c557131, LayoutHelper.createFrame(-1, -1.0f, 0, 14.0f, 14.0f, 14.0f, 14.0f));
        groupCallActivity.applyCallParticipantUpdates(false);
        groupCallActivity.listAdapter.notifyDataSetChanged();
        if (isTabletMode) {
            groupCallActivity.tabletGridAdapter.update(false, groupCallActivity.tabletVideoGridView);
        }
        groupCallActivity.oldCount = groupCallActivity.listAdapter.getItemCount();
        if (inputPeer != null) {
            TextView textView = new TextView(activity3);
            groupCallActivity.scheduleInfoTextView = textView;
            textView.setGravity(17);
            groupCallActivity.scheduleInfoTextView.setTextColor(-8682615);
            groupCallActivity.scheduleInfoTextView.setTextSize(1, 14.0f);
            if (ChatObject.isChannel(groupCallActivity.currentChat) && (chat2 = groupCallActivity.currentChat) != null && !chat2.megagroup) {
                groupCallActivity.scheduleInfoTextView.setTag(1);
            }
            groupCallActivity.containerView.addView(groupCallActivity.scheduleInfoTextView, LayoutHelper.createFrame(-2, -2.0f, 81, 21.0f, 0.0f, 21.0f, 100.0f));
            final NumberPicker numberPicker = new NumberPicker(activity3);
            numberPicker.setTextColor(-1);
            numberPicker.setSelectorColor(-9598483);
            numberPicker.setTextOffset(AndroidUtilities.m1081dp(10.0f));
            numberPicker.setItemCount(5);
            final C558140 c558140 = new NumberPicker(activity3) { // from class: org.telegram.ui.GroupCallActivity.40
                C558140(final Context activity3) {
                    super(activity3);
                }

                @Override // org.telegram.p026ui.Components.NumberPicker
                protected CharSequence getContentDescription(int i17) {
                    return LocaleController.formatPluralString("Hours", i17, new Object[0]);
                }
            };
            c558140.setItemCount(5);
            c558140.setTextColor(-1);
            c558140.setSelectorColor(-9598483);
            c558140.setTextOffset(-AndroidUtilities.m1081dp(10.0f));
            final C558241 c558241 = new NumberPicker(activity3) { // from class: org.telegram.ui.GroupCallActivity.41
                C558241(final Context activity3) {
                    super(activity3);
                }

                @Override // org.telegram.p026ui.Components.NumberPicker
                protected CharSequence getContentDescription(int i17) {
                    return LocaleController.formatPluralString("Minutes", i17, new Object[0]);
                }
            };
            c558241.setItemCount(5);
            c558241.setTextColor(-1);
            c558241.setSelectorColor(-9598483);
            c558241.setTextOffset(-AndroidUtilities.m1081dp(34.0f));
            C558342 c558342 = new TextView(activity3) { // from class: org.telegram.ui.GroupCallActivity.42
                private final Paint tmpPaint;
                private final RectF tmpRectF = new RectF();

                C558342(final Context activity3) {
                    super(activity3);
                    this.tmpRectF = new RectF();
                    Paint paint5 = new Paint(1);
                    this.tmpPaint = paint5;
                    paint5.setStyle(Paint.Style.FILL);
                    paint5.setColor(-16711936);
                }

                @Override // android.widget.TextView, android.view.View
                protected void onMeasure(int i17, int i18) {
                    super.onMeasure(i17, i18);
                }

                @Override // android.view.View
                public void draw(Canvas canvas) {
                    super.draw(canvas);
                }

                @Override // android.widget.TextView, android.view.View
                protected void onDraw(Canvas canvas) {
                    this.tmpPaint.setColor(-16711936);
                    this.tmpRectF.set(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight());
                    canvas.drawRoundRect(this.tmpRectF, AndroidUtilities.m1081dp(8.0f), AndroidUtilities.m1081dp(8.0f), this.tmpPaint);
                    super.onDraw(canvas);
                }

                @Override // android.view.View
                protected void dispatchDraw(Canvas canvas) {
                    this.tmpRectF.set(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight());
                    canvas.drawRoundRect(this.tmpRectF, AndroidUtilities.m1081dp(8.0f), AndroidUtilities.m1081dp(8.0f), this.tmpPaint);
                    super.dispatchDraw(canvas);
                }
            };
            groupCallActivity.scheduleButtonTextView = c558342;
            c558342.setLines(1);
            groupCallActivity.scheduleButtonTextView.setSingleLine(true);
            groupCallActivity.scheduleButtonTextView.setEllipsize(TextUtils.TruncateAt.END);
            groupCallActivity.scheduleButtonTextView.setGravity(17);
            groupCallActivity.scheduleButtonTextView.setTextColor(-1);
            groupCallActivity.scheduleButtonTextView.setTypeface(AndroidUtilities.bold());
            groupCallActivity.scheduleButtonTextView.setTextSize(1, 14.0f);
            groupCallActivity.containerView.addView(groupCallActivity.scheduleButtonTextView, LayoutHelper.createFrame(-1, 48.0f, 81, 21.0f, 0.0f, 21.0f, 20.5f));
            groupCallActivity.scheduleButtonTextView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda20
                @Override // android.view.View.OnClickListener
                public final void onClick(View view4) {
                    this.f$0.lambda$new$45(numberPicker, c558140, c558241, chat, accountInstance, inputPeer2, view4);
                }
            });
            C558544 c558544 = new LinearLayout(activity3) { // from class: org.telegram.ui.GroupCallActivity.44
                boolean ignoreLayout = false;
                final /* synthetic */ NumberPicker val$dayPicker;
                final /* synthetic */ NumberPicker val$hourPicker;
                final /* synthetic */ NumberPicker val$minutePicker;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                C558544(final Context activity3, final NumberPicker numberPicker2, final NumberPicker c5581402, final NumberPicker c5582412) {
                    super(activity3);
                    numberPicker = numberPicker2;
                    numberPicker = c5581402;
                    numberPicker = c5582412;
                    this.ignoreLayout = false;
                }

                @Override // android.widget.LinearLayout, android.view.View
                protected void onMeasure(int i17, int i18) {
                    this.ignoreLayout = true;
                    numberPicker.setItemCount(5);
                    numberPicker.setItemCount(5);
                    numberPicker.setItemCount(5);
                    numberPicker.getLayoutParams().height = AndroidUtilities.m1081dp(54.0f) * 5;
                    numberPicker.getLayoutParams().height = AndroidUtilities.m1081dp(54.0f) * 5;
                    numberPicker.getLayoutParams().height = AndroidUtilities.m1081dp(54.0f) * 5;
                    this.ignoreLayout = false;
                    super.onMeasure(i17, i18);
                }

                @Override // android.view.View, android.view.ViewParent
                public void requestLayout() {
                    if (this.ignoreLayout) {
                        return;
                    }
                    super.requestLayout();
                }
            };
            groupCallActivity = this;
            activity2 = activity3;
            groupCallActivity.scheduleTimerContainer = c558544;
            c558544.setWeightSum(1.0f);
            groupCallActivity.scheduleTimerContainer.setOrientation(0);
            groupCallActivity.containerView.addView(groupCallActivity.scheduleTimerContainer, LayoutHelper.createFrame(-1, 270.0f, 51, 0.0f, 50.0f, 0.0f, 0.0f));
            final long jCurrentTimeMillis = System.currentTimeMillis();
            final Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(jCurrentTimeMillis);
            final int i17 = calendar.get(1);
            int i18 = calendar.get(6);
            groupCallActivity.scheduleTimerContainer.addView(numberPicker2, LayoutHelper.createLinear(0, 270, 0.5f));
            numberPicker2.setMinValue(0);
            numberPicker2.setMaxValue(365);
            numberPicker2.setWrapSelectorWheel(false);
            numberPicker2.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda21
                @Override // org.telegram.ui.Components.NumberPicker.Formatter
                public final String format(int i19) {
                    return GroupCallActivity.$r8$lambda$MzXaaBkHop8z0Fg5s2nMkqxc3uM(jCurrentTimeMillis, calendar, i17, i19);
                }
            });
            NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda22
                @Override // org.telegram.ui.Components.NumberPicker.OnValueChangeListener
                public final void onValueChange(NumberPicker numberPicker2, int i19, int i20) {
                    this.f$0.lambda$new$47(numberPicker2, c5581402, c5582412, numberPicker2, i19, i20);
                }
            };
            numberPicker2.setOnValueChangedListener(onValueChangeListener);
            c5581402.setMinValue(0);
            c5581402.setMaxValue(23);
            groupCallActivity.scheduleTimerContainer.addView(c5581402, LayoutHelper.createLinear(0, 270, 0.2f));
            c5581402.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda23
                @Override // org.telegram.ui.Components.NumberPicker.Formatter
                public final String format(int i19) {
                    return String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i19));
                }
            });
            c5581402.setOnValueChangedListener(onValueChangeListener);
            c5582412.setMinValue(0);
            c5582412.setMaxValue(59);
            c5582412.setValue(0);
            c5582412.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda24
                @Override // org.telegram.ui.Components.NumberPicker.Formatter
                public final String format(int i19) {
                    return String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i19));
                }
            });
            groupCallActivity.scheduleTimerContainer.addView(c5582412, LayoutHelper.createLinear(0, 270, 0.3f));
            c5582412.setOnValueChangedListener(onValueChangeListener);
            calendar.setTimeInMillis(jCurrentTimeMillis + 10800000);
            calendar.set(12, 0);
            calendar.set(13, 0);
            calendar.set(14, 0);
            int i19 = calendar.get(6);
            int i20 = calendar.get(12);
            int i21 = calendar.get(11);
            numberPicker2.setValue(i18 == i19 ? 0 : 1);
            c5582412.setValue(i20);
            c5581402.setValue(i21);
            AlertsCreator.checkScheduleDate(groupCallActivity.scheduleButtonTextView, groupCallActivity.scheduleInfoTextView, 604800L, 2, numberPicker2, c5581402, c5582412);
        } else {
            activity2 = activity3;
        }
        C558645 c558645 = new PinchToZoomHelper((ViewGroup) groupCallActivity.getWindow().getDecorView(), groupCallActivity.containerView) { // from class: org.telegram.ui.GroupCallActivity.45
            C558645(ViewGroup viewGroup2, ViewGroup viewGroup3) {
                super(viewGroup2, viewGroup3);
            }

            @Override // org.telegram.p026ui.PinchToZoomHelper
            protected void invalidateViews() {
                super.invalidateViews();
                for (int i22 = 0; i22 < GroupCallActivity.this.avatarsViewPager.getChildCount(); i22++) {
                    GroupCallActivity.this.avatarsViewPager.getChildAt(i22).invalidate();
                }
            }

            @Override // org.telegram.p026ui.PinchToZoomHelper
            protected void drawOverlays(Canvas canvas, float f, float f2, float f3, float f4, float f5) {
                if (f > 0.0f) {
                    float x = GroupCallActivity.this.avatarPreviewContainer.getX() + ((BottomSheet) GroupCallActivity.this).containerView.getX();
                    float y = GroupCallActivity.this.avatarPreviewContainer.getY() + ((BottomSheet) GroupCallActivity.this).containerView.getY();
                    RectF rectF = AndroidUtilities.rectTmp;
                    rectF.set(x, y, GroupCallActivity.this.avatarsViewPager.getMeasuredWidth() + x, GroupCallActivity.this.avatarsViewPager.getMeasuredHeight() + y);
                    canvas.saveLayerAlpha(rectF, (int) (f * 255.0f), 31);
                    canvas.translate(x, y);
                    GroupCallActivity.this.avatarPreviewContainer.draw(canvas);
                    canvas.restore();
                }
            }
        };
        groupCallActivity.pinchToZoomHelper = c558645;
        c558645.setCallback(new PinchToZoomHelper.Callback() { // from class: org.telegram.ui.GroupCallActivity.46
            @Override // org.telegram.ui.PinchToZoomHelper.Callback
            public /* synthetic */ TextureView getCurrentTextureView() {
                return PinchToZoomHelper.Callback.CC.$default$getCurrentTextureView(this);
            }

            C558746() {
            }

            @Override // org.telegram.ui.PinchToZoomHelper.Callback
            public void onZoomStarted(MessageObject messageObject) {
                GroupCallActivity.this.listView.cancelClickRunnables(true);
                GroupCallActivity.this.pinchToZoomHelper.getPhotoImage().setRoundRadius(AndroidUtilities.m1081dp(13.0f), AndroidUtilities.m1081dp(13.0f), 0, 0);
                ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
            }

            @Override // org.telegram.ui.PinchToZoomHelper.Callback
            public void onZoomFinished(MessageObject messageObject) {
                ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
            }
        });
        c557030.setPinchToZoomHelper(groupCallActivity.pinchToZoomHelper);
        groupCallActivity.cameraButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda25
            @Override // android.view.View.OnClickListener
            public final void onClick(View view4) {
                this.f$0.lambda$new$50(activity2, view4);
            }
        });
        groupCallActivity.updateScheduleUI(false);
        groupCallActivity.updateItems();
        groupCallActivity.updateSpeakerPhoneIcon(false);
        groupCallActivity.updateState(false, false);
        groupCallActivity.setColorProgress(0.0f);
        groupCallActivity.updateSubtitle();
        FrameLayout frameLayout = new FrameLayout(activity2);
        groupCallActivity.bulletinContainer = frameLayout;
        groupCallActivity.containerView.addView(frameLayout, LayoutHelper.createFrame(-1, DataTypes.EMPTY, 87));
        groupCallActivity.messageButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda26
            @Override // android.view.View.OnClickListener
            public final void onClick(View view4) {
                this.f$0.lambda$new$51(view4);
            }
        });
        groupCallActivity.checkGroupCallUi();
        ScaleStateListAnimator.apply(groupCallActivity.cameraButton);
        ScaleStateListAnimator.apply(groupCallActivity.soundButton);
        ScaleStateListAnimator.apply(groupCallActivity.flipButton);
        ScaleStateListAnimator.apply(groupCallActivity.speakerButton);
        ScaleStateListAnimator.apply(groupCallActivity.muteButton);
        ScaleStateListAnimator.apply(groupCallActivity.leaveButton);
        ScaleStateListAnimator.apply(groupCallActivity.messageButton);
        ViewCompat.setOnApplyWindowInsetsListener(groupCallActivity.containerView, new OnApplyWindowInsetsListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda27
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view4, WindowInsetsCompat windowInsetsCompat) {
                return this.f$0.onApplyWindowInsets(view4, windowInsetsCompat);
            }
        });
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$3 */
    /* JADX INFO: loaded from: classes6.dex */
    class C55693 implements BottomSheet.BottomSheetDelegateInterface {
        @Override // org.telegram.ui.ActionBar.BottomSheet.BottomSheetDelegateInterface
        public boolean canDismiss() {
            return true;
        }

        C55693() {
        }

        @Override // org.telegram.ui.ActionBar.BottomSheet.BottomSheetDelegateInterface
        public void onOpenAnimationEnd() {
            CountDownLatch groupCallBottomSheetLatch;
            VoIPService sharedInstance = VoIPService.getSharedInstance();
            if (sharedInstance != null && (groupCallBottomSheetLatch = sharedInstance.getGroupCallBottomSheetLatch()) != null) {
                groupCallBottomSheetLatch.countDown();
            }
            if (GroupCallActivity.this.muteButtonState == 6) {
                GroupCallActivity.this.showReminderHint();
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$4 */
    /* JADX INFO: loaded from: classes6.dex */
    class C55804 extends Paint {
        C55804() {
        }

        @Override // android.graphics.Paint
        public void setAlpha(int i3) {
            super.setAlpha(i3);
            if (((BottomSheet) GroupCallActivity.this).containerView != null) {
                ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
            }
        }
    }

    public /* synthetic */ void lambda$new$9(DialogInterface dialogInterface) {
        BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
        if (this.anyEnterEventSent && (safeLastFragment instanceof ChatActivity)) {
            ((ChatActivity) safeLastFragment).onEditTextDialogClose(true, true);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$5 */
    /* JADX INFO: loaded from: classes6.dex */
    class C55915 extends ActionBar {
        final /* synthetic */ RecordStatusDrawable val$recordStatusDrawable;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C55915(final Context activity3, RecordStatusDrawable recordStatusDrawable2) {
            super(activity3);
            recordStatusDrawable = recordStatusDrawable2;
        }

        @Override // android.view.View
        public void setAlpha(float f) {
            if (getAlpha() != f) {
                super.setAlpha(f);
                ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
            }
        }

        @Override // org.telegram.p026ui.ActionBar.ActionBar, android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
            if (getAdditionalSubtitleTextView().getVisibility() == 0) {
                canvas.save();
                canvas.translate(getSubtitleTextView().getLeft(), getSubtitleTextView().getY() - AndroidUtilities.m1081dp(1.0f));
                recordStatusDrawable.setAlpha((int) (getAdditionalSubtitleTextView().getAlpha() * 255.0f));
                recordStatusDrawable.draw(canvas);
                canvas.restore();
                invalidate();
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$6 */
    /* JADX INFO: loaded from: classes6.dex */
    class C56026 extends ActionBar.ActionBarMenuOnItemClick {
        final /* synthetic */ Activity val$context;

        /* JADX INFO: renamed from: $r8$lambda$t-6jC1WSfURy5tDSUywPpDZ0crs */
        public static /* synthetic */ void m13993$r8$lambda$t6jC1WSfURy5tDSUywPpDZ0crs(TLObject tLObject, TLRPC.TL_error tL_error) {
        }

        C56026(Activity activity) {
            this.val$context = activity;
        }

        @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
        public void onItemClick(int i) {
            VoIPService sharedInstance;
            int i2;
            int color;
            if (i == -1) {
                GroupCallActivity.this.lambda$openCrafting$9();
                return;
            }
            if (i == 1) {
                GroupCallActivity groupCallActivity = GroupCallActivity.this;
                groupCallActivity.call.call.join_muted = false;
                groupCallActivity.toggleAdminSpeak();
                return;
            }
            if (i == 2) {
                GroupCallActivity groupCallActivity2 = GroupCallActivity.this;
                groupCallActivity2.call.call.join_muted = true;
                groupCallActivity2.toggleAdminSpeak();
                return;
            }
            if (i == 3) {
                GroupCallActivity.this.getLink(false);
                return;
            }
            if (i == 12) {
                GroupCallActivity.this.setCommentsEnabled(true);
                return;
            }
            if (i == 13) {
                GroupCallActivity.this.setCommentsEnabled(false);
                return;
            }
            if (i == 4) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GroupCallActivity.this.getContext());
                if (ChatObject.isChannelOrGiga(GroupCallActivity.this.currentChat)) {
                    builder.setTitle(LocaleController.getString(C2702R.string.VoipChannelEndAlertTitle));
                    builder.setMessage(LocaleController.getString(C2702R.string.VoipChannelEndAlertText));
                } else {
                    builder.setTitle(LocaleController.getString(C2702R.string.VoipGroupEndAlertTitle));
                    builder.setMessage(LocaleController.getString(C2702R.string.VoipGroupEndAlertText));
                }
                builder.setDialogButtonColorKey(Theme.key_voipgroup_listeningText);
                builder.setPositiveButton(LocaleController.getString(C2702R.string.VoipGroupEnd), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.GroupCallActivity$6$$ExternalSyntheticLambda0
                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                    public final void onClick(AlertDialog alertDialog, int i3) {
                        this.f$0.lambda$onItemClick$1(alertDialog, i3);
                    }
                });
                builder.setNegativeButton(LocaleController.getString(C2702R.string.Cancel), null);
                AlertDialog alertDialogCreate = builder.create();
                alertDialogCreate.setBackgroundColor(Theme.getColor(Theme.key_voipgroup_dialogBackground));
                alertDialogCreate.show();
                TextView textView = (TextView) alertDialogCreate.getButton(-1);
                if (textView != null) {
                    textView.setTextColor(Theme.getColor(Theme.key_voipgroup_leaveCallMenu));
                }
                alertDialogCreate.setTextColor(Theme.getColor(Theme.key_voipgroup_actionBarItems));
                return;
            }
            if (i == 9) {
                GroupCallActivity.this.screenShareItem.callOnClick();
                return;
            }
            if (i == 5) {
                GroupCallActivity groupCallActivity3 = GroupCallActivity.this;
                ChatObject.Call call = groupCallActivity3.call;
                if (call.recording) {
                    final boolean z = call.call.record_video_active;
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(groupCallActivity3.getContext());
                    builder2.setDialogButtonColorKey(Theme.key_voipgroup_listeningText);
                    builder2.setTitle(LocaleController.getString(C2702R.string.VoipGroupStopRecordingTitle));
                    if (ChatObject.isChannelOrGiga(GroupCallActivity.this.currentChat)) {
                        builder2.setMessage(LocaleController.getString(C2702R.string.VoipChannelStopRecordingText));
                    } else {
                        builder2.setMessage(LocaleController.getString(C2702R.string.VoipGroupStopRecordingText));
                    }
                    builder2.setPositiveButton(LocaleController.getString(C2702R.string.Stop), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.GroupCallActivity$6$$ExternalSyntheticLambda1
                        @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                        public final void onClick(AlertDialog alertDialog, int i3) {
                            this.f$0.lambda$onItemClick$2(z, alertDialog, i3);
                        }
                    });
                    builder2.setNegativeButton(LocaleController.getString(C2702R.string.Cancel), null);
                    AlertDialog alertDialogCreate2 = builder2.create();
                    alertDialogCreate2.setBackgroundColor(Theme.getColor(Theme.key_voipgroup_dialogBackground));
                    alertDialogCreate2.show();
                    alertDialogCreate2.setTextColor(Theme.getColor(Theme.key_voipgroup_nameText));
                    return;
                }
                Context context = GroupCallActivity.this.getContext();
                GroupCallActivity groupCallActivity4 = GroupCallActivity.this;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(context, groupCallActivity4.currentChat, groupCallActivity4.hasVideo);
                if (GroupCallActivity.this.isRtmpStream()) {
                    anonymousClass1.onStartRecord(2);
                    return;
                } else {
                    anonymousClass1.show();
                    return;
                }
            }
            if (i == 7) {
                GroupCallActivity.this.changingPermissions = true;
                GroupCallActivity.this.everyoneItem.setVisibility(0);
                GroupCallActivity.this.adminItem.setVisibility(0);
                GroupCallActivity.this.inviteItem.setVisibility(8);
                GroupCallActivity.this.enableComments.setVisibility(8);
                GroupCallActivity.this.disableComments.setVisibility(8);
                GroupCallActivity.this.leaveItem.setVisibility(8);
                GroupCallActivity.this.permissionItem.setVisibility(8);
                GroupCallActivity.this.editTitleItem.setVisibility(8);
                GroupCallActivity.this.recordItem.setVisibility(8);
                GroupCallActivity.this.screenItem.setVisibility(8);
                GroupCallActivity.this.accountSelectCell.setVisibility(8);
                GroupCallActivity.this.soundItem.setVisibility(8);
                GroupCallActivity.this.noiseItem.setVisibility(8);
                GroupCallActivity.this.otherItem.forceUpdatePopupPosition();
                return;
            }
            if (i == 6) {
                GroupCallActivity.this.enterEventSent = false;
                final EditTextBoldCursor editTextBoldCursor = new EditTextBoldCursor(GroupCallActivity.this.getContext());
                editTextBoldCursor.setBackgroundDrawable(Theme.createEditTextDrawable(GroupCallActivity.this.getContext(), true));
                final AlertDialog.Builder builder3 = new AlertDialog.Builder(GroupCallActivity.this.getContext());
                builder3.setDialogButtonColorKey(Theme.key_voipgroup_listeningText);
                if (ChatObject.isChannelOrGiga(GroupCallActivity.this.currentChat)) {
                    builder3.setTitle(LocaleController.getString(C2702R.string.VoipChannelTitle));
                } else {
                    builder3.setTitle(LocaleController.getString(C2702R.string.VoipGroupTitle));
                }
                builder3.setCheckFocusable(false);
                builder3.setNegativeButton(LocaleController.getString(C2702R.string.Cancel), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.GroupCallActivity$6$$ExternalSyntheticLambda2
                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                    public final void onClick(AlertDialog alertDialog, int i3) {
                        AndroidUtilities.hideKeyboard(editTextBoldCursor);
                    }
                });
                LinearLayout linearLayout = new LinearLayout(GroupCallActivity.this.getContext());
                linearLayout.setOrientation(1);
                builder3.setView(linearLayout);
                editTextBoldCursor.setTextSize(1, 16.0f);
                int i3 = Theme.key_voipgroup_nameText;
                editTextBoldCursor.setTextColor(Theme.getColor(i3));
                editTextBoldCursor.setMaxLines(1);
                editTextBoldCursor.setLines(1);
                editTextBoldCursor.setInputType(16385);
                editTextBoldCursor.setGravity(51);
                editTextBoldCursor.setSingleLine(true);
                editTextBoldCursor.setImeOptions(6);
                TLRPC.Chat chat = GroupCallActivity.this.currentChat;
                editTextBoldCursor.setHint(chat != null ? chat.title : _UrlKt.FRAGMENT_ENCODE_SET);
                editTextBoldCursor.setHintTextColor(Theme.getColor(Theme.key_voipgroup_lastSeenText));
                editTextBoldCursor.setCursorColor(Theme.getColor(i3));
                editTextBoldCursor.setCursorSize(AndroidUtilities.m1081dp(20.0f));
                editTextBoldCursor.setCursorWidth(1.5f);
                editTextBoldCursor.setPadding(0, AndroidUtilities.m1081dp(4.0f), 0, 0);
                linearLayout.addView(editTextBoldCursor, LayoutHelper.createLinear(-1, 36, 51, 24, 6, 24, 0));
                editTextBoldCursor.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.telegram.ui.GroupCallActivity$6$$ExternalSyntheticLambda3
                    @Override // android.widget.TextView.OnEditorActionListener
                    public final boolean onEditorAction(TextView textView2, int i4, KeyEvent keyEvent) {
                        return GroupCallActivity.C56026.$r8$lambda$D1ltd_eU8okhssrfndqIA5Jm9ak(builder3, textView2, i4, keyEvent);
                    }
                });
                editTextBoldCursor.addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.GroupCallActivity.6.2
                    boolean ignoreTextChange;
                    final /* synthetic */ EditTextBoldCursor val$editText;

                    @Override // android.text.TextWatcher
                    public void beforeTextChanged(CharSequence charSequence, int i4, int i5, int i6) {
                    }

                    @Override // android.text.TextWatcher
                    public void onTextChanged(CharSequence charSequence, int i4, int i5, int i6) {
                    }

                    AnonymousClass2(final EditTextBoldCursor editTextBoldCursor2) {
                        editTextBoldCursor = editTextBoldCursor2;
                    }

                    @Override // android.text.TextWatcher
                    public void afterTextChanged(Editable editable) {
                        if (!this.ignoreTextChange && editable.length() > 40) {
                            this.ignoreTextChange = true;
                            editable.delete(40, editable.length());
                            AndroidUtilities.shakeView(editTextBoldCursor);
                            try {
                                editTextBoldCursor.performHapticFeedback(3, 2);
                            } catch (Exception unused) {
                            }
                            this.ignoreTextChange = false;
                        }
                    }
                });
                if (!TextUtils.isEmpty(GroupCallActivity.this.call.call.title)) {
                    editTextBoldCursor2.setText(GroupCallActivity.this.call.call.title);
                    editTextBoldCursor2.setSelection(editTextBoldCursor2.length());
                }
                builder3.setPositiveButton(LocaleController.getString(C2702R.string.Save), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.GroupCallActivity$6$$ExternalSyntheticLambda4
                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                    public final void onClick(AlertDialog alertDialog, int i4) {
                        this.f$0.lambda$onItemClick$5(editTextBoldCursor2, builder3, alertDialog, i4);
                    }
                });
                final AlertDialog alertDialogCreate3 = builder3.create();
                alertDialogCreate3.setBackgroundColor(Theme.getColor(Theme.key_voipgroup_inviteMembersBackground));
                alertDialogCreate3.setOnShowListener(new DialogInterface.OnShowListener() { // from class: org.telegram.ui.GroupCallActivity$6$$ExternalSyntheticLambda5
                    @Override // android.content.DialogInterface.OnShowListener
                    public final void onShow(DialogInterface dialogInterface) {
                        this.f$0.lambda$onItemClick$6(alertDialogCreate3, editTextBoldCursor2, dialogInterface);
                    }
                });
                alertDialogCreate3.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.GroupCallActivity$6$$ExternalSyntheticLambda6
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        AndroidUtilities.hideKeyboard(editTextBoldCursor2);
                    }
                });
                alertDialogCreate3.show();
                alertDialogCreate3.setTextColor(Theme.getColor(i3));
                editTextBoldCursor2.requestFocus();
                return;
            }
            if (i == 8) {
                JoinCallAlert.open(GroupCallActivity.this.getContext(), -GroupCallActivity.this.getChatId(), GroupCallActivity.this.accountInstance, null, 2, GroupCallActivity.this.selfPeer, new JoinCallAlert.JoinCallAlertDelegate() { // from class: org.telegram.ui.GroupCallActivity$6$$ExternalSyntheticLambda7
                    @Override // org.telegram.ui.Components.JoinCallAlert.JoinCallAlertDelegate
                    public final void didSelectChat(TLRPC.InputPeer inputPeer, boolean z2, boolean z3, boolean z4) {
                        this.f$0.lambda$onItemClick$9(inputPeer, z2, z3, z4);
                    }
                });
                return;
            }
            if (i == 11) {
                SharedConfig.toggleNoiseSupression();
                VoIPService sharedInstance2 = VoIPService.getSharedInstance();
                if (sharedInstance2 == null) {
                    return;
                }
                sharedInstance2.setNoiseSupressionEnabled(SharedConfig.noiseSupression);
                return;
            }
            if (i != 10 || (sharedInstance = VoIPService.getSharedInstance()) == null) {
                return;
            }
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            final ArrayList arrayList3 = new ArrayList();
            arrayList.add(LocaleController.getString(C2702R.string.VoipAudioRoutingSpeaker));
            arrayList2.add(Integer.valueOf(C2702R.drawable.msg_voice_speaker));
            arrayList3.add(0);
            if (sharedInstance.hasEarpiece()) {
                arrayList.add(LocaleController.getString(sharedInstance.isHeadsetPlugged() ? C2702R.string.VoipAudioRoutingHeadset : C2702R.string.VoipAudioRoutingPhone));
                arrayList2.add(Integer.valueOf(sharedInstance.isHeadsetPlugged() ? C2702R.drawable.msg_voice_headphones : C2702R.drawable.msg_voice_phone));
                arrayList3.add(1);
            }
            if (sharedInstance.isBluetoothHeadsetConnected()) {
                String string = sharedInstance.currentBluetoothDeviceName;
                if (string == null) {
                    string = LocaleController.getString(C2702R.string.VoipAudioRoutingBluetooth);
                }
                arrayList.add(string);
                arrayList2.add(Integer.valueOf(C2702R.drawable.msg_voice_bluetooth));
                arrayList3.add(2);
            }
            int size = arrayList.size();
            CharSequence[] charSequenceArr = new CharSequence[size];
            int[] iArr = new int[size];
            for (int i4 = 0; i4 < size; i4++) {
                charSequenceArr[i4] = (CharSequence) arrayList.get(i4);
                iArr[i4] = ((Integer) arrayList2.get(i4)).intValue();
            }
            BottomSheet.Builder items = new BottomSheet.Builder(this.val$context).setTitle(LocaleController.getString(C2702R.string.VoipSelectAudioOutput), true).setItems(charSequenceArr, iArr, new DialogInterface.OnClickListener() { // from class: org.telegram.ui.GroupCallActivity$6$$ExternalSyntheticLambda8
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i5) {
                    this.f$0.lambda$onItemClick$11(arrayList3, dialogInterface, i5);
                }
            });
            BottomSheet bottomSheetCreate = items.create();
            int i5 = Theme.key_voipgroup_listViewBackgroundUnscrolled;
            bottomSheetCreate.setBackgroundColor(Theme.getColor(i5));
            bottomSheetCreate.fixNavigationBar(Theme.getColor(i5));
            if (sharedInstance.getCurrentAudioRoute() == 1) {
                i2 = 0;
            } else {
                i2 = sharedInstance.getCurrentAudioRoute() == 0 ? 1 : 2;
            }
            items.show();
            bottomSheetCreate.setTitleColor(Theme.getColor(Theme.key_voipgroup_nameText));
            for (int i6 = 0; i6 < bottomSheetCreate.getItemViews().size(); i6++) {
                BottomSheet.BottomSheetCell bottomSheetCell = bottomSheetCreate.getItemViews().get(i6);
                if (i6 == i2) {
                    color = Theme.getColor(Theme.key_voipgroup_listeningText);
                    bottomSheetCell.isSelected = true;
                } else {
                    color = Theme.getColor(Theme.key_voipgroup_nameText);
                }
                bottomSheetCell.setTextColor(color);
                bottomSheetCell.setIconColor(color);
                bottomSheetCell.setBackground(Theme.createSelectorDrawable(ColorUtils.setAlphaComponent(Theme.getColor(Theme.key_voipgroup_actionBarItems), 12), 2));
            }
        }

        public /* synthetic */ void lambda$onItemClick$1(AlertDialog alertDialog, int i) {
            if (GroupCallActivity.this.call.isScheduled()) {
                TLRPC.ChatFull chatFull = GroupCallActivity.this.accountInstance.getMessagesController().getChatFull(GroupCallActivity.this.getChatId());
                if (chatFull != null) {
                    chatFull.flags &= -2097153;
                    chatFull.call = null;
                    GroupCallActivity.this.accountInstance.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.groupCallUpdated, Long.valueOf(GroupCallActivity.this.getChatId()), Long.valueOf(GroupCallActivity.this.call.call.f1625id), Boolean.FALSE);
                }
                TL_phone.discardGroupCall discardgroupcall = new TL_phone.discardGroupCall();
                discardgroupcall.call = GroupCallActivity.this.call.getInputGroupCall();
                GroupCallActivity.this.accountInstance.getConnectionsManager().sendRequest(discardgroupcall, new RequestDelegate() { // from class: org.telegram.ui.GroupCallActivity$6$$ExternalSyntheticLambda10
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                        this.f$0.lambda$onItemClick$0(tLObject, tL_error);
                    }
                });
            } else if (VoIPService.getSharedInstance() != null) {
                VoIPService.getSharedInstance().hangUp(1);
            }
            GroupCallActivity.this.lambda$new$0();
            NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.didStartedCall, new Object[0]);
        }

        public /* synthetic */ void lambda$onItemClick$0(TLObject tLObject, TLRPC.TL_error tL_error) {
            if (tLObject instanceof TLRPC.TL_updates) {
                GroupCallActivity.this.accountInstance.getMessagesController().processUpdates((TLRPC.TL_updates) tLObject, false);
            }
        }

        public /* synthetic */ void lambda$onItemClick$2(boolean z, AlertDialog alertDialog, int i) {
            GroupCallActivity.this.call.toggleRecord(null, 0);
            GroupCallActivity.this.getUndoView().showWithAction(0L, z ? 101 : 40, (Runnable) null);
        }

        /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$6$1 */
        class AnonymousClass1 extends GroupCallRecordAlert {
            AnonymousClass1(Context context, TLRPC.Chat chat, boolean z) {
                super(context, chat, z);
            }

            @Override // org.telegram.p026ui.Components.GroupCallRecordAlert
            public void onStartRecord(final int i) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setDialogButtonColorKey(Theme.key_voipgroup_listeningText);
                GroupCallActivity.this.enterEventSent = false;
                builder.setTitle(LocaleController.getString(C2702R.string.VoipGroupStartRecordingTitle));
                if (i == 0) {
                    builder.setMessage(LocaleController.getString(GroupCallActivity.this.call.call.rtmp_stream ? C2702R.string.VoipGroupStartRecordingRtmpText : C2702R.string.VoipGroupStartRecordingText));
                } else if (ChatObject.isChannelOrGiga(GroupCallActivity.this.currentChat)) {
                    builder.setMessage(LocaleController.getString(GroupCallActivity.this.call.call.rtmp_stream ? C2702R.string.VoipGroupStartRecordingRtmpVideoText : C2702R.string.VoipChannelStartRecordingVideoText));
                } else {
                    builder.setMessage(LocaleController.getString(GroupCallActivity.this.call.call.rtmp_stream ? C2702R.string.VoipGroupStartRecordingRtmpVideoText : C2702R.string.VoipGroupStartRecordingVideoText));
                }
                builder.setCheckFocusable(false);
                final EditTextBoldCursor editTextBoldCursor = new EditTextBoldCursor(getContext());
                editTextBoldCursor.setBackgroundDrawable(Theme.createEditTextDrawable(getContext(), Theme.getColor(Theme.key_voipgroup_windowBackgroundWhiteInputField), Theme.getColor(Theme.key_voipgroup_windowBackgroundWhiteInputFieldActivated)));
                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setOrientation(1);
                builder.setView(linearLayout);
                editTextBoldCursor.setTextSize(1, 16.0f);
                int i2 = Theme.key_voipgroup_nameText;
                editTextBoldCursor.setTextColor(Theme.getColor(i2));
                editTextBoldCursor.setMaxLines(1);
                editTextBoldCursor.setLines(1);
                editTextBoldCursor.setInputType(16385);
                editTextBoldCursor.setGravity(51);
                editTextBoldCursor.setSingleLine(true);
                editTextBoldCursor.setHint(LocaleController.getString(C2702R.string.VoipGroupSaveFileHint));
                editTextBoldCursor.setImeOptions(6);
                editTextBoldCursor.setHintTextColor(Theme.getColor(Theme.key_voipgroup_lastSeenText));
                editTextBoldCursor.setCursorColor(Theme.getColor(i2));
                editTextBoldCursor.setCursorSize(AndroidUtilities.m1081dp(20.0f));
                editTextBoldCursor.setCursorWidth(1.5f);
                editTextBoldCursor.setPadding(0, AndroidUtilities.m1081dp(4.0f), 0, 0);
                linearLayout.addView(editTextBoldCursor, LayoutHelper.createLinear(-1, 36, 51, 24, 0, 24, 12));
                editTextBoldCursor.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.telegram.ui.GroupCallActivity$6$1$$ExternalSyntheticLambda0
                    @Override // android.widget.TextView.OnEditorActionListener
                    public final boolean onEditorAction(TextView textView, int i3, KeyEvent keyEvent) {
                        return GroupCallActivity.C56026.AnonymousClass1.$r8$lambda$AnupSWGd5zEZYNWrLpcnwqxJ9RM(builder, textView, i3, keyEvent);
                    }
                });
                final AlertDialog alertDialogCreate = builder.create();
                alertDialogCreate.setBackgroundColor(Theme.getColor(Theme.key_voipgroup_inviteMembersBackground));
                alertDialogCreate.setOnShowListener(new DialogInterface.OnShowListener() { // from class: org.telegram.ui.GroupCallActivity$6$1$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnShowListener
                    public final void onShow(DialogInterface dialogInterface) {
                        this.f$0.lambda$onStartRecord$1(alertDialogCreate, editTextBoldCursor, dialogInterface);
                    }
                });
                alertDialogCreate.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.GroupCallActivity$6$1$$ExternalSyntheticLambda2
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        AndroidUtilities.hideKeyboard(editTextBoldCursor);
                    }
                });
                builder.setPositiveButton(LocaleController.getString(C2702R.string.Start), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.GroupCallActivity$6$1$$ExternalSyntheticLambda3
                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                    public final void onClick(AlertDialog alertDialog, int i3) {
                        this.f$0.lambda$onStartRecord$3(editTextBoldCursor, i, alertDialog, i3);
                    }
                });
                builder.setNegativeButton(LocaleController.getString(C2702R.string.Cancel), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.GroupCallActivity$6$1$$ExternalSyntheticLambda4
                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                    public final void onClick(AlertDialog alertDialog, int i3) {
                        AndroidUtilities.hideKeyboard(editTextBoldCursor);
                    }
                });
                AlertDialog alertDialogCreate2 = builder.create();
                alertDialogCreate2.setBackgroundColor(Theme.getColor(Theme.key_voipgroup_dialogBackground));
                alertDialogCreate2.show();
                alertDialogCreate2.setTextColor(Theme.getColor(i2));
                editTextBoldCursor.requestFocus();
            }

            public static /* synthetic */ boolean $r8$lambda$AnupSWGd5zEZYNWrLpcnwqxJ9RM(AlertDialog.Builder builder, TextView textView, int i, KeyEvent keyEvent) {
                AndroidUtilities.hideKeyboard(textView);
                builder.create().getButton(-1).callOnClick();
                return false;
            }

            public /* synthetic */ void lambda$onStartRecord$1(AlertDialog alertDialog, EditTextBoldCursor editTextBoldCursor, DialogInterface dialogInterface) {
                GroupCallActivity.this.makeFocusable(null, alertDialog, editTextBoldCursor, true);
            }

            public /* synthetic */ void lambda$onStartRecord$3(EditTextBoldCursor editTextBoldCursor, int i, AlertDialog alertDialog, int i2) {
                GroupCallActivity.this.call.toggleRecord(editTextBoldCursor.getText().toString(), i);
                AndroidUtilities.hideKeyboard(editTextBoldCursor);
                GroupCallActivity.this.getUndoView().showWithAction(0L, i == 0 ? 39 : 100, (Runnable) null);
                if (VoIPService.getSharedInstance() != null) {
                    VoIPService.getSharedInstance().playStartRecordSound();
                }
            }
        }

        public static /* synthetic */ boolean $r8$lambda$D1ltd_eU8okhssrfndqIA5Jm9ak(AlertDialog.Builder builder, TextView textView, int i, KeyEvent keyEvent) {
            AndroidUtilities.hideKeyboard(textView);
            builder.create().getButton(-1).callOnClick();
            return false;
        }

        /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$6$2 */
        class AnonymousClass2 implements TextWatcher {
            boolean ignoreTextChange;
            final /* synthetic */ EditTextBoldCursor val$editText;

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i4, int i5, int i6) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i4, int i5, int i6) {
            }

            AnonymousClass2(final EditTextBoldCursor editTextBoldCursor2) {
                editTextBoldCursor = editTextBoldCursor2;
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (!this.ignoreTextChange && editable.length() > 40) {
                    this.ignoreTextChange = true;
                    editable.delete(40, editable.length());
                    AndroidUtilities.shakeView(editTextBoldCursor);
                    try {
                        editTextBoldCursor.performHapticFeedback(3, 2);
                    } catch (Exception unused) {
                    }
                    this.ignoreTextChange = false;
                }
            }
        }

        public /* synthetic */ void lambda$onItemClick$5(EditTextBoldCursor editTextBoldCursor, AlertDialog.Builder builder, AlertDialog alertDialog, int i) {
            AndroidUtilities.hideKeyboard(editTextBoldCursor);
            GroupCallActivity.this.call.setTitle(editTextBoldCursor.getText().toString());
            builder.getDismissRunnable().run();
        }

        public /* synthetic */ void lambda$onItemClick$6(AlertDialog alertDialog, EditTextBoldCursor editTextBoldCursor, DialogInterface dialogInterface) {
            GroupCallActivity.this.makeFocusable(null, alertDialog, editTextBoldCursor, true);
        }

        public /* synthetic */ void lambda$onItemClick$9(TLRPC.InputPeer inputPeer, boolean z, boolean z2, boolean z3) {
            TLObject chat;
            GroupCallActivity groupCallActivity = GroupCallActivity.this;
            if (groupCallActivity.call == null) {
                return;
            }
            boolean z4 = inputPeer instanceof TLRPC.TL_inputPeerUser;
            if (z4) {
                chat = groupCallActivity.accountInstance.getMessagesController().getUser(Long.valueOf(inputPeer.user_id));
            } else if (inputPeer instanceof TLRPC.TL_inputPeerChat) {
                chat = groupCallActivity.accountInstance.getMessagesController().getChat(Long.valueOf(inputPeer.chat_id));
            } else {
                chat = groupCallActivity.accountInstance.getMessagesController().getChat(Long.valueOf(inputPeer.channel_id));
            }
            TLObject tLObject = chat;
            if (GroupCallActivity.this.call.isScheduled()) {
                GroupCallActivity.this.getUndoView().showWithAction(0L, 37, tLObject, GroupCallActivity.this.currentChat, (Runnable) null, (Runnable) null);
                if (inputPeer instanceof TLRPC.TL_inputPeerChannel) {
                    GroupCallActivity.this.selfPeer = new TLRPC.TL_peerChannel();
                    GroupCallActivity.this.selfPeer.channel_id = inputPeer.channel_id;
                } else if (z4) {
                    GroupCallActivity.this.selfPeer = new TLRPC.TL_peerUser();
                    GroupCallActivity.this.selfPeer.user_id = inputPeer.user_id;
                } else if (inputPeer instanceof TLRPC.TL_inputPeerChat) {
                    GroupCallActivity.this.selfPeer = new TLRPC.TL_peerChat();
                    GroupCallActivity.this.selfPeer.chat_id = inputPeer.chat_id;
                }
                GroupCallActivity.this.schedulePeer = inputPeer;
                TLRPC.ChatFull chatFull = GroupCallActivity.this.accountInstance.getMessagesController().getChatFull(GroupCallActivity.this.getChatId());
                if (chatFull != null) {
                    chatFull.groupcall_default_join_as = GroupCallActivity.this.selfPeer;
                    if (chatFull instanceof TLRPC.TL_chatFull) {
                        chatFull.flags |= 32768;
                    } else {
                        chatFull.flags |= 67108864;
                    }
                }
                TL_phone.saveDefaultGroupCallJoinAs savedefaultgroupcalljoinas = new TL_phone.saveDefaultGroupCallJoinAs();
                savedefaultgroupcalljoinas.peer = MessagesController.getInputPeer(GroupCallActivity.this.currentChat);
                savedefaultgroupcalljoinas.join_as = inputPeer;
                GroupCallActivity.this.accountInstance.getConnectionsManager().sendRequest(savedefaultgroupcalljoinas, new RequestDelegate() { // from class: org.telegram.ui.GroupCallActivity$6$$ExternalSyntheticLambda11
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject2, TLRPC.TL_error tL_error) {
                        GroupCallActivity.C56026.m13993$r8$lambda$t6jC1WSfURy5tDSUywPpDZ0crs(tLObject2, tL_error);
                    }
                });
                GroupCallActivity.this.updateItems();
                return;
            }
            if (VoIPService.getSharedInstance() == null || !z) {
                return;
            }
            GroupCallActivity groupCallActivity2 = GroupCallActivity.this;
            VoIPService.getSharedInstance().setGroupCallPeer(inputPeer);
            GroupCallActivity.this.userSwitchObject = tLObject;
        }

        public /* synthetic */ void lambda$onItemClick$11(ArrayList arrayList, DialogInterface dialogInterface, int i) {
            if (VoIPService.getSharedInstance() == null) {
                return;
            }
            Integer num = (Integer) arrayList.get(i);
            final int iIntValue = num.intValue();
            GroupCallActivity.this.cacheAudioOutputValue = num;
            GroupCallActivity.this.updateState(true, true);
            GroupCallActivity.this.cacheAudioOutputValue = null;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$6$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onItemClick$10(iIntValue);
                }
            });
        }

        public /* synthetic */ void lambda$onItemClick$10(int i) {
            GroupCallActivity.this.setAudioOutputValue(i);
            BulletinFactory.m1194of(GroupCallActivity.this.topBulletinContainer, new DarkBlueThemeResourcesProvider()).createSimpleBulletin(GroupCallActivity.this.getContext().getResources().getDrawable(GroupCallActivity.this.getAudioOutputToastIcon(i)).mutate(), GroupCallActivity.this.getAudioOutputToastText(i)).show(GroupCallActivity.this.isBulletinTop());
        }
    }

    public /* synthetic */ void lambda$new$10(int[] iArr, float[] fArr, boolean[] zArr) {
        RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition;
        for (int i = 0; i < iArr.length; i++) {
            TLRPC.GroupCallParticipant groupCallParticipant = this.call.participantsBySources.get(iArr[i]);
            if (groupCallParticipant != null) {
                if (!this.renderersContainer.inFullscreenMode) {
                    int iIndexOf = (this.delayedGroupCallUpdated ? this.oldParticipants : this.call.visibleParticipants).indexOf(groupCallParticipant);
                    if (iIndexOf >= 0 && (viewHolderFindViewHolderForAdapterPosition = this.listView.findViewHolderForAdapterPosition(iIndexOf + this.listAdapter.usersStartRow)) != null) {
                        View view = viewHolderFindViewHolderForAdapterPosition.itemView;
                        if (view instanceof GroupCallUserCell) {
                            ((GroupCallUserCell) view).setAmplitude(fArr[i] * 15.0f);
                            if (viewHolderFindViewHolderForAdapterPosition.itemView == this.scrimView && !this.contentFullyOverlayed) {
                                this.containerView.invalidate();
                            }
                        }
                    }
                } else {
                    for (int i2 = 0; i2 < this.fullscreenUsersListView.getChildCount(); i2++) {
                        GroupCallFullscreenAdapter.GroupCallUserCell groupCallUserCell = (GroupCallFullscreenAdapter.GroupCallUserCell) this.fullscreenUsersListView.getChildAt(i2);
                        if (MessageObject.getPeerId(groupCallUserCell.getParticipant().peer) == MessageObject.getPeerId(groupCallParticipant.peer)) {
                            groupCallUserCell.setAmplitude(fArr[i] * 15.0f);
                        }
                    }
                }
                this.renderersContainer.setAmplitude(groupCallParticipant, fArr[i] * 15.0f);
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$7 */
    /* JADX INFO: loaded from: classes6.dex */
    class C56107 extends SizeNotifierFrameLayout {
        private int lastSize;
        boolean localHasVideo;
        private boolean updateRenderers;
        boolean wasLayout;
        private boolean ignoreLayout = false;
        private RectF rect = new RectF();
        HashMap listCells = new HashMap();

        C56107(final Context activity3) {
            super(activity3);
            this.ignoreLayout = false;
            this.rect = new RectF();
            this.listCells = new HashMap();
        }

        @Override // org.telegram.p026ui.Components.SizeNotifierFrameLayout, android.view.ViewGroup, android.view.View
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            GroupCallActivity.this.reactionEffectImageReceiver.onAttachedToWindow();
        }

        @Override // org.telegram.p026ui.Components.SizeNotifierFrameLayout, android.view.ViewGroup, android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            GroupCallActivity.this.reactionEffectImageReceiver.onDetachedFromWindow();
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i6, int i7) {
            int iM1081dp;
            float f;
            int iM1081dp2;
            int size = View.MeasureSpec.getSize(i7);
            this.ignoreLayout = true;
            boolean z2 = View.MeasureSpec.getSize(i6) > size && !AndroidUtilities.isTablet();
            GroupCallActivity.this.renderersContainer.listWidth = View.MeasureSpec.getSize(i6);
            boolean z3 = AndroidUtilities.isTablet() && View.MeasureSpec.getSize(i6) > size && !GroupCallActivity.this.isRtmpStream();
            if (GroupCallActivity.isLandscapeMode != z2) {
                GroupCallActivity.isLandscapeMode = z2;
                if (GroupCallActivity.this.muteButton.getMeasuredWidth() == 0) {
                    int i8 = GroupCallActivity.this.muteButton.getLayoutParams().width;
                }
                GroupCallActivity.this.invalidateLayoutFullscreen();
                GroupCallActivity.this.layoutManager.setSpanCount(GroupCallActivity.isLandscapeMode ? 6 : 2);
                GroupCallActivity.this.listView.invalidateItemDecorations();
                GroupCallActivity.this.fullscreenUsersListView.invalidateItemDecorations();
                this.updateRenderers = true;
                if (GroupCallActivity.this.scheduleInfoTextView != null) {
                    GroupCallActivity.this.scheduleInfoTextView.setVisibility(!GroupCallActivity.isLandscapeMode ? 0 : 8);
                }
                if (GroupCallActivity.this.isRtmpLandscapeMode() == z2 && GroupCallActivity.this.isRtmpStream() && !GroupCallActivity.this.renderersContainer.inFullscreenMode && !GroupCallActivity.this.call.visibleVideoParticipants.isEmpty()) {
                    GroupCallActivity groupCallActivity2 = GroupCallActivity.this;
                    groupCallActivity2.fullscreenFor(groupCallActivity2.call.visibleVideoParticipants.get(0));
                    GroupCallActivity.this.renderersContainer.delayHideUi();
                }
            }
            if (GroupCallActivity.isTabletMode != z3) {
                GroupCallActivity.isTabletMode = z3;
                GroupCallActivity.this.tabletVideoGridView.setVisibility(z3 ? 0 : 8);
                GroupCallActivity.this.listView.invalidateItemDecorations();
                GroupCallActivity.this.fullscreenUsersListView.invalidateItemDecorations();
                this.updateRenderers = true;
            }
            if (this.updateRenderers) {
                GroupCallActivity.this.applyCallParticipantUpdates(true);
                GroupCallActivity.this.listAdapter.notifyDataSetChanged();
                GroupCallActivity groupCallActivity3 = GroupCallActivity.this;
                groupCallActivity3.fullscreenAdapter.update(false, groupCallActivity3.tabletVideoGridView);
                if (GroupCallActivity.isTabletMode) {
                    GroupCallActivity groupCallActivity4 = GroupCallActivity.this;
                    groupCallActivity4.tabletGridAdapter.update(false, groupCallActivity4.tabletVideoGridView);
                }
                GroupCallActivity.this.tabletVideoGridView.setVisibility(GroupCallActivity.isTabletMode ? 0 : 8);
                GroupCallActivity groupCallActivity5 = GroupCallActivity.this;
                groupCallActivity5.tabletGridAdapter.setVisibility(groupCallActivity5.tabletVideoGridView, GroupCallActivity.isTabletMode && !groupCallActivity5.renderersContainer.inFullscreenMode, true);
                GroupCallActivity groupCallActivity6 = GroupCallActivity.this;
                groupCallActivity6.listViewVideoVisibility = !GroupCallActivity.isTabletMode || groupCallActivity6.renderersContainer.inFullscreenMode;
                boolean z4 = !GroupCallActivity.isTabletMode && GroupCallActivity.this.renderersContainer.inFullscreenMode;
                GroupCallActivity groupCallActivity7 = GroupCallActivity.this;
                groupCallActivity7.fullscreenAdapter.setVisibility(groupCallActivity7.fullscreenUsersListView, z4);
                GroupCallActivity.this.fullscreenUsersListView.setVisibility(z4 ? 0 : 8);
                GroupCallActivity.this.listView.setVisibility((GroupCallActivity.isTabletMode || !GroupCallActivity.this.renderersContainer.inFullscreenMode) ? 0 : 8);
                GroupCallActivity.this.layoutManager.setSpanCount(GroupCallActivity.isLandscapeMode ? 6 : 2);
                GroupCallActivity.this.updateState(false, false);
                GroupCallActivity.this.listView.invalidateItemDecorations();
                GroupCallActivity.this.fullscreenUsersListView.invalidateItemDecorations();
                AndroidUtilities.updateVisibleRows(GroupCallActivity.this.listView);
                this.updateRenderers = false;
                GroupCallActivity.this.attachedRenderersTmp.clear();
                GroupCallActivity.this.attachedRenderersTmp.addAll(GroupCallActivity.this.attachedRenderers);
                GroupCallActivity.this.renderersContainer.setIsTablet(GroupCallActivity.isTabletMode);
                for (int i9 = 0; i9 < GroupCallActivity.this.attachedRenderersTmp.size(); i9++) {
                    ((GroupCallMiniTextureView) GroupCallActivity.this.attachedRenderersTmp.get(i9)).updateAttachState(true);
                }
            }
            int paddingTop = size - getPaddingTop();
            if (GroupCallActivity.this.isRtmpStream()) {
                iM1081dp = AndroidUtilities.m1081dp(72.0f);
            } else {
                iM1081dp = AndroidUtilities.m1081dp(245.0f);
            }
            int i10 = paddingTop - iM1081dp;
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) GroupCallActivity.this.renderersContainer.getLayoutParams();
            if (GroupCallActivity.isTabletMode) {
                layoutParams.topMargin = ActionBar.getCurrentActionBarHeight();
            } else {
                layoutParams.topMargin = 0;
            }
            for (int i11 = 0; i11 < 2; i11++) {
                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) GroupCallActivity.this.undoView[i11].getLayoutParams();
                if (GroupCallActivity.isTabletMode) {
                    layoutParams2.rightMargin = AndroidUtilities.m1081dp(328.0f);
                } else {
                    layoutParams2.rightMargin = AndroidUtilities.m1081dp(8.0f);
                }
            }
            RecyclerListView recyclerListView = GroupCallActivity.this.tabletVideoGridView;
            if (recyclerListView != null) {
                ((FrameLayout.LayoutParams) recyclerListView.getLayoutParams()).topMargin = ActionBar.getCurrentActionBarHeight();
            }
            if (GroupCallActivity.this.callMessageEnterView.getEmojiView() != null) {
                ((FrameLayout.LayoutParams) GroupCallActivity.this.callMessageEnterView.getEmojiView().getLayoutParams()).gravity = 80;
            }
            int iM1081dp3 = AndroidUtilities.m1081dp(GroupCallActivity.this.isRtmpStream() ? 40.0f : 90.0f);
            FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) GroupCallActivity.this.listView.getLayoutParams();
            if (GroupCallActivity.isTabletMode) {
                layoutParams3.gravity = GroupCallActivity.this.hasVideo ? 5 : 1;
                layoutParams3.width = AndroidUtilities.m1081dp(320.0f);
                int iM1081dp4 = AndroidUtilities.m1081dp(4.0f);
                layoutParams3.leftMargin = iM1081dp4;
                layoutParams3.rightMargin = iM1081dp4;
                layoutParams3.bottomMargin = iM1081dp3;
                layoutParams3.topMargin = ActionBar.getCurrentActionBarHeight();
                iM1081dp2 = AndroidUtilities.m1081dp(60.0f);
                f = 328.0f;
            } else {
                f = 328.0f;
                if (GroupCallActivity.isLandscapeMode) {
                    layoutParams3.gravity = 51;
                    layoutParams3.width = -1;
                    layoutParams3.topMargin = ActionBar.getCurrentActionBarHeight();
                    layoutParams3.bottomMargin = AndroidUtilities.m1081dp(14.0f);
                    layoutParams3.rightMargin = AndroidUtilities.m1081dp(90.0f);
                    layoutParams3.leftMargin = AndroidUtilities.m1081dp(14.0f);
                    iM1081dp2 = 0;
                } else {
                    layoutParams3.gravity = 51;
                    layoutParams3.width = -1;
                    int iM1081dp5 = AndroidUtilities.m1081dp(60.0f);
                    layoutParams3.bottomMargin = iM1081dp3;
                    layoutParams3.topMargin = ActionBar.getCurrentActionBarHeight() + AndroidUtilities.m1081dp(14.0f);
                    int iM1081dp6 = AndroidUtilities.m1081dp(14.0f);
                    layoutParams3.leftMargin = iM1081dp6;
                    layoutParams3.rightMargin = iM1081dp6;
                    iM1081dp2 = iM1081dp5;
                }
            }
            if (GroupCallActivity.isLandscapeMode && !GroupCallActivity.isTabletMode) {
                GroupCallActivity.this.buttonsBackgroundGradientView.setVisibility(8);
                GroupCallActivity.this.buttonsBackgroundGradientView2.setVisibility(8);
            } else {
                GroupCallActivity.this.buttonsBackgroundGradientView.setVisibility(0);
                FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) GroupCallActivity.this.buttonsBackgroundGradientView.getLayoutParams();
                layoutParams4.bottomMargin = iM1081dp3;
                if (GroupCallActivity.isTabletMode) {
                    layoutParams4.gravity = GroupCallActivity.this.hasVideo ? 85 : 81;
                    layoutParams4.width = AndroidUtilities.m1081dp(f);
                } else {
                    layoutParams4.width = -1;
                }
                GroupCallActivity.this.buttonsBackgroundGradientView2.setVisibility(0);
                FrameLayout.LayoutParams layoutParams5 = (FrameLayout.LayoutParams) GroupCallActivity.this.buttonsBackgroundGradientView2.getLayoutParams();
                layoutParams5.height = iM1081dp3;
                if (GroupCallActivity.isTabletMode) {
                    layoutParams5.gravity = GroupCallActivity.this.hasVideo ? 85 : 81;
                    layoutParams5.width = AndroidUtilities.m1081dp(f);
                } else {
                    layoutParams5.width = -1;
                }
            }
            if (GroupCallActivity.isLandscapeMode) {
                GroupCallActivity.this.fullscreenUsersListView.setPadding(0, AndroidUtilities.m1081dp(9.0f), 0, AndroidUtilities.m1081dp(9.0f));
            } else {
                GroupCallActivity.this.fullscreenUsersListView.setPadding(AndroidUtilities.m1081dp(9.0f), 0, AndroidUtilities.m1081dp(9.0f), 0);
            }
            FrameLayout.LayoutParams layoutParams6 = (FrameLayout.LayoutParams) GroupCallActivity.this.buttonsContainer.getLayoutParams();
            if (GroupCallActivity.isTabletMode) {
                layoutParams6.width = AndroidUtilities.m1081dp(320.0f);
                layoutParams6.height = AndroidUtilities.m1081dp(120.0f);
                layoutParams6.gravity = GroupCallActivity.this.hasVideo ? 85 : 81;
                layoutParams6.rightMargin = 0;
            } else if (GroupCallActivity.isLandscapeMode) {
                layoutParams6.width = AndroidUtilities.m1081dp(90.0f);
                layoutParams6.height = -1;
                layoutParams6.gravity = 53;
            } else {
                layoutParams6.width = -1;
                layoutParams6.height = AndroidUtilities.m1081dp(120.0f);
                layoutParams6.gravity = 81;
                layoutParams6.rightMargin = 0;
            }
            if (GroupCallActivity.isLandscapeMode && !GroupCallActivity.isTabletMode) {
                ((FrameLayout.LayoutParams) GroupCallActivity.this.actionBar.getLayoutParams()).rightMargin = AndroidUtilities.m1081dp(90.0f);
                ((FrameLayout.LayoutParams) GroupCallActivity.this.menuItemsContainer.getLayoutParams()).rightMargin = AndroidUtilities.m1081dp(90.0f);
                ((FrameLayout.LayoutParams) GroupCallActivity.this.actionBarBackground.getLayoutParams()).rightMargin = AndroidUtilities.m1081dp(90.0f);
                ((FrameLayout.LayoutParams) GroupCallActivity.this.actionBarShadow.getLayoutParams()).rightMargin = AndroidUtilities.m1081dp(90.0f);
            } else {
                ((FrameLayout.LayoutParams) GroupCallActivity.this.actionBar.getLayoutParams()).rightMargin = 0;
                ((FrameLayout.LayoutParams) GroupCallActivity.this.menuItemsContainer.getLayoutParams()).rightMargin = 0;
                ((FrameLayout.LayoutParams) GroupCallActivity.this.actionBarBackground.getLayoutParams()).rightMargin = 0;
                ((FrameLayout.LayoutParams) GroupCallActivity.this.actionBarShadow.getLayoutParams()).rightMargin = 0;
            }
            FrameLayout.LayoutParams layoutParams7 = (FrameLayout.LayoutParams) GroupCallActivity.this.fullscreenUsersListView.getLayoutParams();
            if (GroupCallActivity.isLandscapeMode) {
                if (((LinearLayoutManager) GroupCallActivity.this.fullscreenUsersListView.getLayoutManager()).getOrientation() != 1) {
                    ((LinearLayoutManager) GroupCallActivity.this.fullscreenUsersListView.getLayoutManager()).setOrientation(1);
                }
                layoutParams7.height = -1;
                layoutParams7.width = AndroidUtilities.m1081dp(80.0f);
                layoutParams7.gravity = 53;
                layoutParams7.rightMargin = AndroidUtilities.m1081dp(100.0f);
                layoutParams7.bottomMargin = 0;
            } else {
                if (((LinearLayoutManager) GroupCallActivity.this.fullscreenUsersListView.getLayoutManager()).getOrientation() != 0) {
                    ((LinearLayoutManager) GroupCallActivity.this.fullscreenUsersListView.getLayoutManager()).setOrientation(0);
                }
                layoutParams7.height = AndroidUtilities.m1081dp(80.0f);
                layoutParams7.width = -1;
                layoutParams7.gravity = 80;
                layoutParams7.rightMargin = 0;
                layoutParams7.bottomMargin = AndroidUtilities.m1081dp(100.0f);
            }
            ((FrameLayout.LayoutParams) GroupCallActivity.this.actionBarShadow.getLayoutParams()).topMargin = ActionBar.getCurrentActionBarHeight();
            GroupCallActivity.this.callMessageEnterUnderContainer.invalidate();
            FrameLayout.LayoutParams layoutParams8 = (FrameLayout.LayoutParams) GroupCallActivity.this.callMessageEnterUnderContainer.getLayoutParams();
            layoutParams8.height = size;
            layoutParams8.topMargin = -getPaddingTop();
            if (GroupCallActivity.this.callMessageEnterView.getEmojiView() != null) {
                ((FrameLayout.LayoutParams) GroupCallActivity.this.callMessageEnterView.getEmojiView().getLayoutParams()).bottomMargin = -getPaddingBottom();
            }
            int iMax = GroupCallActivity.isTabletMode ? 0 : Math.max(0, (i10 - Math.max(AndroidUtilities.m1081dp(259.0f), (i10 / 5) * 3)) + AndroidUtilities.m1081dp(8.0f));
            if (GroupCallActivity.this.listView.getPaddingTop() != iMax || GroupCallActivity.this.listView.getPaddingBottom() != iM1081dp2) {
                GroupCallActivity.this.listView.setPadding(0, iMax, 0, iM1081dp2);
            }
            if (GroupCallActivity.this.watchersView != null) {
                FrameLayout.LayoutParams layoutParams9 = (FrameLayout.LayoutParams) GroupCallActivity.this.watchersView.getLayoutParams();
                GroupCallGridCell groupCallGridCellFindGroupCallGridCell = GroupCallActivity.this.findGroupCallGridCell();
                if (groupCallGridCellFindGroupCallGridCell != null) {
                    int top = (GroupCallActivity.this.buttonsContainer.getTop() + (GroupCallActivity.this.buttonsContainer.getMeasuredHeight() / 2)) - (GroupCallActivity.this.leaveButton.getMeasuredHeight() / 2);
                    int currentActionBarHeight = ActionBar.getCurrentActionBarHeight() + iMax + groupCallGridCellFindGroupCallGridCell.getMeasuredHeight();
                    layoutParams9.topMargin = (currentActionBarHeight + ((top - currentActionBarHeight) / 2)) - AndroidUtilities.m1081dp(32.0f);
                    layoutParams9.height = AndroidUtilities.m1081dp(70.0f);
                }
            }
            if (GroupCallActivity.this.textureLightningView != null) {
                FrameLayout.LayoutParams layoutParams10 = (FrameLayout.LayoutParams) GroupCallActivity.this.textureLightningView.getLayoutParams();
                GroupCallGridCell groupCallGridCellFindGroupCallGridCell2 = GroupCallActivity.this.findGroupCallGridCell();
                if (groupCallGridCellFindGroupCallGridCell2 != null) {
                    layoutParams10.height = groupCallGridCellFindGroupCallGridCell2.getMeasuredHeight() - AndroidUtilities.m1081dp(14.0f);
                    layoutParams10.width = groupCallGridCellFindGroupCallGridCell2.getMeasuredWidth() - AndroidUtilities.m1081dp(7.0f);
                    int iM1081dp7 = AndroidUtilities.m1081dp(16.0f);
                    layoutParams10.leftMargin = iM1081dp7;
                    layoutParams10.rightMargin = iM1081dp7;
                }
            }
            if (GroupCallActivity.this.scheduleStartAtTextView != null) {
                int iM1081dp8 = iMax + (((i10 - iMax) + AndroidUtilities.m1081dp(60.0f)) / 2);
                FrameLayout.LayoutParams layoutParams11 = (FrameLayout.LayoutParams) GroupCallActivity.this.scheduleStartInTextView.getLayoutParams();
                layoutParams11.topMargin = iM1081dp8 - AndroidUtilities.m1081dp(30.0f);
                FrameLayout.LayoutParams layoutParams12 = (FrameLayout.LayoutParams) GroupCallActivity.this.scheduleStartAtTextView.getLayoutParams();
                layoutParams12.topMargin = AndroidUtilities.m1081dp(80.0f) + iM1081dp8;
                FrameLayout.LayoutParams layoutParams13 = (FrameLayout.LayoutParams) GroupCallActivity.this.scheduleTimeTextView.getLayoutParams();
                if (layoutParams11.topMargin < ActionBar.getCurrentActionBarHeight() || layoutParams12.topMargin + AndroidUtilities.m1081dp(20.0f) > size - AndroidUtilities.m1081dp(231.0f)) {
                    GroupCallActivity.this.scheduleStartInTextView.setVisibility(4);
                    GroupCallActivity.this.scheduleStartAtTextView.setVisibility(4);
                    layoutParams13.topMargin = iM1081dp8 - AndroidUtilities.m1081dp(20.0f);
                } else {
                    GroupCallActivity.this.scheduleStartInTextView.setVisibility(0);
                    GroupCallActivity.this.scheduleStartAtTextView.setVisibility(0);
                    layoutParams13.topMargin = iM1081dp8;
                }
            }
            for (int i12 = 0; i12 < GroupCallActivity.this.attachedRenderers.size(); i12++) {
                ((GroupCallMiniTextureView) GroupCallActivity.this.attachedRenderers.get(i12)).setFullscreenMode(GroupCallActivity.this.renderersContainer.inFullscreenMode, true);
            }
            this.ignoreLayout = false;
            super.onMeasure(i6, View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30));
            int measuredHeight = getMeasuredHeight() + (getMeasuredWidth() << 16);
            if (measuredHeight != this.lastSize) {
                this.lastSize = measuredHeight;
                GroupCallActivity.this.dismissAvatarPreview(false);
            }
            GroupCallActivity.this.cellFlickerDrawable.setParentWidth(getMeasuredWidth());
            GroupCallActivity.this.checkGroupCallUiPositions_MessagesList();
        }

        @Override // org.telegram.p026ui.Components.SizeNotifierFrameLayout, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z2, int i6, int i7, int i8, int i9) {
            boolean z3;
            float x;
            if (GroupCallActivity.isTabletMode && this.localHasVideo != GroupCallActivity.this.hasVideo && this.wasLayout) {
                x = GroupCallActivity.this.listView.getX();
                z3 = true;
            } else {
                z3 = false;
                x = 0.0f;
            }
            this.localHasVideo = GroupCallActivity.this.hasVideo;
            GroupCallActivity.this.renderersContainer.inLayout = true;
            super.onLayout(z2, i6, i7, i8, i9);
            GroupCallActivity.this.renderersContainer.inLayout = false;
            GroupCallActivity.this.updateLayout(false);
            this.wasLayout = true;
            if (!z3 || GroupCallActivity.this.listView.getLeft() == x) {
                return;
            }
            float left = x - GroupCallActivity.this.listView.getLeft();
            GroupCallActivity.this.listView.setTranslationX(left);
            GroupCallActivity.this.buttonsContainer.setTranslationX(left);
            GroupCallActivity.this.buttonsBackgroundGradientView.setTranslationX(left);
            GroupCallActivity.this.buttonsBackgroundGradientView2.setTranslationX(left);
            ViewPropertyAnimator duration = GroupCallActivity.this.listView.animate().translationX(0.0f).setDuration(350L);
            CubicBezierInterpolator cubicBezierInterpolator2 = CubicBezierInterpolator.DEFAULT;
            duration.setInterpolator(cubicBezierInterpolator2).start();
            GroupCallActivity.this.buttonsBackgroundGradientView.animate().translationX(0.0f).setDuration(350L).setInterpolator(cubicBezierInterpolator2).start();
            GroupCallActivity.this.buttonsBackgroundGradientView2.animate().translationX(0.0f).setDuration(350L).setInterpolator(cubicBezierInterpolator2).start();
            GroupCallActivity.this.buttonsContainer.animate().translationX(0.0f).setDuration(350L).setInterpolator(cubicBezierInterpolator2).start();
        }

        @Override // android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (GroupCallActivity.this.scrimView != null && motionEvent.getAction() == 0) {
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                this.rect.set(GroupCallActivity.this.scrimPopupLayout.getX(), GroupCallActivity.this.scrimPopupLayout.getY(), GroupCallActivity.this.scrimPopupLayout.getX() + GroupCallActivity.this.scrimPopupLayout.getMeasuredWidth(), GroupCallActivity.this.scrimPopupLayout.getY() + GroupCallActivity.this.scrimPopupLayout.getMeasuredHeight());
                boolean z2 = !this.rect.contains(x, y);
                this.rect.set(GroupCallActivity.this.avatarPreviewContainer.getX(), GroupCallActivity.this.avatarPreviewContainer.getY(), GroupCallActivity.this.avatarPreviewContainer.getX() + GroupCallActivity.this.avatarPreviewContainer.getMeasuredWidth(), GroupCallActivity.this.avatarPreviewContainer.getY() + GroupCallActivity.this.avatarPreviewContainer.getMeasuredWidth() + GroupCallActivity.this.scrimView.getMeasuredHeight());
                if (this.rect.contains(x, y)) {
                    z2 = false;
                }
                if (z2) {
                    GroupCallActivity.this.dismissAvatarPreview(true);
                    return true;
                }
            }
            if (motionEvent.getAction() == 0 && GroupCallActivity.this.scrollOffsetY != 0.0f && motionEvent.getY() < GroupCallActivity.this.scrollOffsetY - AndroidUtilities.m1081dp(37.0f) && GroupCallActivity.this.actionBar.getAlpha() == 0.0f && !GroupCallActivity.this.avatarsPreviewShowed) {
                GroupCallActivity groupCallActivity2 = GroupCallActivity.this;
                if (groupCallActivity2.previewDialog == null && !groupCallActivity2.renderersContainer.inFullscreenMode) {
                    GroupCallActivity.this.lambda$new$0();
                    return true;
                }
            }
            return super.onInterceptTouchEvent(motionEvent);
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            return !GroupCallActivity.this.isDismissed() && super.onTouchEvent(motionEvent);
        }

        @Override // android.view.View, android.view.ViewParent
        public void requestLayout() {
            if (this.ignoreLayout) {
                return;
            }
            super.requestLayout();
        }

        @Override // android.view.View
        public void setTranslationY(float f) {
            super.setTranslationY(f);
            GroupCallActivity.this.updateTopBulletinY();
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            float f;
            int iM1081dp = AndroidUtilities.m1081dp(74.0f);
            float f2 = GroupCallActivity.this.scrollOffsetY - iM1081dp;
            int measuredHeight = getMeasuredHeight() + AndroidUtilities.m1081dp(15.0f) + ((BottomSheet) GroupCallActivity.this).backgroundPaddingTop;
            if (((BottomSheet) GroupCallActivity.this).backgroundPaddingTop + f2 < ActionBar.getCurrentActionBarHeight()) {
                float fMin = Math.min(1.0f, ((ActionBar.getCurrentActionBarHeight() - f2) - ((BottomSheet) GroupCallActivity.this).backgroundPaddingTop) / ((iM1081dp - ((BottomSheet) GroupCallActivity.this).backgroundPaddingTop) - AndroidUtilities.m1081dp(14.0f)));
                int currentActionBarHeight = (int) ((ActionBar.getCurrentActionBarHeight() - r1) * fMin);
                f2 -= currentActionBarHeight;
                measuredHeight += currentActionBarHeight;
                f = 1.0f - fMin;
            } else {
                f = 1.0f;
            }
            float paddingTop = f2 + getPaddingTop();
            GroupCallActivity.this.updateTopBulletinY();
            if (GroupCallActivity.this.renderersContainer.progressToFullscreenMode != 1.0f) {
                GroupCallActivity.this.shadowDrawable.setBounds(0, (int) paddingTop, getMeasuredWidth(), measuredHeight);
                GroupCallActivity.this.shadowDrawable.draw(canvas);
                if (f != 1.0f) {
                    Theme.dialogs_onlineCirclePaint.setColor(GroupCallActivity.this.backgroundColor);
                    this.rect.set(((BottomSheet) GroupCallActivity.this).backgroundPaddingLeft, ((BottomSheet) GroupCallActivity.this).backgroundPaddingTop + paddingTop, getMeasuredWidth() - ((BottomSheet) GroupCallActivity.this).backgroundPaddingLeft, ((BottomSheet) GroupCallActivity.this).backgroundPaddingTop + paddingTop + AndroidUtilities.m1081dp(24.0f));
                    canvas.drawRoundRect(this.rect, AndroidUtilities.m1081dp(12.0f) * f, AndroidUtilities.m1081dp(12.0f) * f, Theme.dialogs_onlineCirclePaint);
                }
                Theme.dialogs_onlineCirclePaint.setColor(Color.argb((int) (GroupCallActivity.this.actionBar.getAlpha() * 255.0f), (int) (Color.red(GroupCallActivity.this.backgroundColor) * 0.8f), (int) (Color.green(GroupCallActivity.this.backgroundColor) * 0.8f), (int) (Color.blue(GroupCallActivity.this.backgroundColor) * 0.8f)));
                canvas.drawRect(((BottomSheet) GroupCallActivity.this).backgroundPaddingLeft, 0.0f, getMeasuredWidth() - ((BottomSheet) GroupCallActivity.this).backgroundPaddingLeft, GroupCallActivity.this.getStatusBarHeight(), Theme.dialogs_onlineCirclePaint);
                PrivateVideoPreviewDialog privateVideoPreviewDialog = GroupCallActivity.this.previewDialog;
                if (privateVideoPreviewDialog != null) {
                    Theme.dialogs_onlineCirclePaint.setColor(privateVideoPreviewDialog.getBackgroundColor());
                    canvas.drawRect(((BottomSheet) GroupCallActivity.this).backgroundPaddingLeft, 0.0f, getMeasuredWidth() - ((BottomSheet) GroupCallActivity.this).backgroundPaddingLeft, GroupCallActivity.this.getStatusBarHeight(), Theme.dialogs_onlineCirclePaint);
                }
            }
            if (GroupCallActivity.this.renderersContainer.progressToFullscreenMode != 0.0f) {
                Theme.dialogs_onlineCirclePaint.setColor(ColorUtils.setAlphaComponent(Theme.getColor(Theme.key_voipgroup_actionBar), (int) (GroupCallActivity.this.renderersContainer.progressToFullscreenMode * 255.0f)));
                canvas.drawRect(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight(), Theme.dialogs_onlineCirclePaint);
            }
            if (GroupCallActivity.this.isRtmpStream() && LiteMode.isEnabled(512)) {
                if (GroupCallActivity.this.renderersContainer.progressToFullscreenMode < 0.15d) {
                    if (!GroupCallActivity.this.needTextureLightning) {
                        GroupCallActivity.this.needTextureLightning = true;
                        GroupCallActivity.this.runUpdateTextureLightningRunnable();
                    }
                } else if (GroupCallActivity.this.needTextureLightning) {
                    GroupCallActivity.this.needTextureLightning = false;
                    AndroidUtilities.cancelRunOnUIThread(GroupCallActivity.this.updateTextureLightningRunnable);
                }
            }
            GroupCallActivity.this.reactionEffectImageReceiver.setImageCoords((getMeasuredWidth() / 2.0f) - AndroidUtilities.m1081dp(30.0f), (getMeasuredHeight() / 2.0f) - AndroidUtilities.m1081dp(30.0f), AndroidUtilities.m1081dp(60.0f), AndroidUtilities.m1081dp(60.0f));
            GroupCallActivity.this.reactionEffectImageReceiver.draw(canvas);
        }

        /* JADX WARN: Removed duplicated region for block: B:588:0x07c5  */
        @Override // org.telegram.p026ui.Components.SizeNotifierFrameLayout, android.view.ViewGroup, android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        protected void dispatchDraw(android.graphics.Canvas r21) {
            /*
                Method dump skipped, instruction units count: 2606
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.GroupCallActivity.C56107.dispatchDraw(android.graphics.Canvas):void");
        }

        @Override // android.view.ViewGroup
        protected boolean drawChild(Canvas canvas, View view, long j) {
            if (GroupCallActivity.this.isInDrawRenderNodeBlur) {
                if (view == GroupCallActivity.this.listView) {
                    int childCount = GroupCallActivity.this.listView.getChildCount();
                    for (int i6 = 0; i6 < childCount; i6++) {
                        View childAt = GroupCallActivity.this.listView.getChildAt(i6);
                        if (childAt.getVisibility() == 0) {
                            canvas.save();
                            canvas.translate(childAt.getX(), childAt.getY());
                            childAt.draw(canvas);
                            canvas.restore();
                        }
                    }
                }
                if (view == GroupCallActivity.this.renderersContainer || view == GroupCallActivity.this.buttonsContainer) {
                    return super.drawChild(canvas, view, j);
                }
                return true;
            }
            if (!GroupCallActivity.isTabletMode && GroupCallActivity.this.renderersContainer.progressToFullscreenMode == 1.0f && (view == GroupCallActivity.this.actionBar || view == GroupCallActivity.this.actionBarShadow || view == GroupCallActivity.this.actionBarBackground || view == GroupCallActivity.this.titleTextView || view == GroupCallActivity.this.menuItemsContainer || view == GroupCallActivity.this.textureLightningView)) {
                return true;
            }
            GroupCallActivity groupCallActivity2 = GroupCallActivity.this;
            if (groupCallActivity2.drawingForBlur && view == groupCallActivity2.renderersContainer) {
                canvas.save();
                canvas.translate(GroupCallActivity.this.renderersContainer.getX() + GroupCallActivity.this.fullscreenUsersListView.getX(), GroupCallActivity.this.renderersContainer.getY() + GroupCallActivity.this.fullscreenUsersListView.getY());
                GroupCallActivity.this.fullscreenUsersListView.draw(canvas);
                canvas.restore();
                return true;
            }
            if (view == GroupCallActivity.this.avatarPreviewContainer || view == GroupCallActivity.this.scrimPopupLayout || view == GroupCallActivity.this.scrimView) {
                return true;
            }
            if (GroupCallActivity.this.contentFullyOverlayed && GroupCallActivity.this.useBlur && (view == GroupCallActivity.this.listView || view == GroupCallActivity.this.buttonsContainer || view == GroupCallActivity.this.groupCallMessagesListView)) {
                return true;
            }
            return super.drawChild(canvas, view, j);
        }

        @Override // android.view.View, android.view.KeyEvent.Callback
        public boolean onKeyDown(int i6, KeyEvent keyEvent) {
            if (GroupCallActivity.this.scrimView != null && i6 == 4) {
                GroupCallActivity.this.dismissAvatarPreview(true);
                return true;
            }
            return super.onKeyDown(i6, keyEvent);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$8 */
    /* JADX INFO: loaded from: classes6.dex */
    class C56118 extends SimpleTextView {
        private float duration;
        private float gradientWidth;
        private int lastTextWidth;
        private long lastUpdateTime;
        private LinearGradient linearGradient;
        private float startX;
        private float time;
        private Matrix matrix = new Matrix();
        private float targetX = -1.0f;

        C56118(final Context activity3) {
            super(activity3);
            this.matrix = new Matrix();
            this.targetX = -1.0f;
        }

        private void setTarget() {
            this.targetX = ((Utilities.random.nextInt(100) - 50) * 0.2f) / 50.0f;
        }

        @Override // org.telegram.p026ui.ActionBar.SimpleTextView
        protected boolean createLayout(int i7) {
            boolean zCreateLayout = super.createLayout(i7);
            int textWidth = getTextWidth();
            if (textWidth != this.lastTextWidth) {
                float f = textWidth;
                this.gradientWidth = 1.3f * f;
                float textHeight = getTextHeight();
                float f2 = f * 2.0f;
                int color = Theme.getColor(Theme.key_voipgroup_mutedByAdminGradient);
                int color2 = Theme.getColor(Theme.key_voipgroup_mutedByAdminGradient3);
                int i8 = Theme.key_voipgroup_mutedByAdminGradient2;
                this.linearGradient = new LinearGradient(0.0f, textHeight, f2, 0.0f, new int[]{color, color2, Theme.getColor(i8), Theme.getColor(i8)}, new float[]{0.0f, 0.38f, 0.76f, 1.0f}, Shader.TileMode.CLAMP);
                getPaint().setShader(this.linearGradient);
                this.lastTextWidth = textWidth;
            }
            return zCreateLayout;
        }

        /* JADX WARN: Removed duplicated region for block: B:78:0x0040  */
        /* JADX WARN: Removed duplicated region for block: B:81:0x0062  */
        /* JADX WARN: Removed duplicated region for block: B:86:0x0072  */
        /* JADX WARN: Removed duplicated region for block: B:88:0x0089  */
        /* JADX WARN: Removed duplicated region for block: B:92:0x00b3  */
        @Override // org.telegram.p026ui.ActionBar.SimpleTextView, android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        protected void onDraw(android.graphics.Canvas r9) {
            /*
                Method dump skipped, instruction units count: 221
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.GroupCallActivity.C56118.onDraw(android.graphics.Canvas):void");
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$9 */
    /* JADX INFO: loaded from: classes6.dex */
    class C56129 extends RecyclerListView {
        private final LongSparseIntArray visiblePeerTmp = new LongSparseIntArray();

        C56129(final Context activity3) {
            super(activity3);
            this.visiblePeerTmp = new LongSparseIntArray();
        }

        @Override // org.telegram.p026ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
        public boolean drawChild(Canvas canvas, View view, long j) {
            if (view == GroupCallActivity.this.scrimView) {
                return false;
            }
            return super.drawChild(canvas, view, j);
        }

        /* JADX WARN: Removed duplicated region for block: B:159:0x00b2  */
        @Override // org.telegram.p026ui.Components.RecyclerListView, android.view.ViewGroup, android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        protected void dispatchDraw(android.graphics.Canvas r18) {
            /*
                Method dump skipped, instruction units count: 451
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.GroupCallActivity.C56129.dispatchDraw(android.graphics.Canvas):void");
        }

        @Override // org.telegram.p026ui.Components.RecyclerListView, android.view.View
        public void setVisibility(int i7) {
            if (getVisibility() != i7) {
                for (int i8 = 0; i8 < getChildCount(); i8++) {
                    View childAt = getChildAt(i8);
                    if (childAt instanceof GroupCallGridCell) {
                        GroupCallActivity.this.attachRenderer((GroupCallGridCell) childAt, childAt.isAttachedToWindow() && i7 == 0);
                    }
                }
            }
            super.setVisibility(i7);
        }

        @Override // org.telegram.p026ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z2, int i7, int i8, int i9, int i10) {
            super.onLayout(z2, i7, i8, i9, i10);
            GroupCallActivity.this.itemAnimator.updateBackgroundBeforeAnimation();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$10 */
    /* JADX INFO: loaded from: classes6.dex */
    class C554810 extends RecyclerView.OnScrollListener {
        C554810() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int i7, int i8) {
            GroupCallActivity groupCallActivity2;
            ChatObject.Call call2;
            if (GroupCallActivity.this.listView.getChildCount() <= 0 || (call2 = (groupCallActivity2 = GroupCallActivity.this).call) == null) {
                return;
            }
            if (!call2.loadingMembers && !call2.membersLoadEndReached && groupCallActivity2.layoutManager.findLastVisibleItemPosition() > GroupCallActivity.this.listAdapter.getItemCount() - 5) {
                GroupCallActivity.this.call.loadMembers(false);
            }
            GroupCallActivity.this.updateLayout(true);
            if (GroupCallActivity.this.textureLightningView != null) {
                GroupCallActivity.this.textureLightningView.invalidate();
            }
            ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int i7) {
            if (i7 == 0) {
                if ((GroupCallActivity.this.scrollOffsetY - AndroidUtilities.m1081dp(74.0f)) + ((BottomSheet) GroupCallActivity.this).backgroundPaddingTop >= ActionBar.getCurrentActionBarHeight() || !GroupCallActivity.this.listView.canScrollVertically(1)) {
                    return;
                }
                GroupCallActivity.this.listView.getChildAt(0);
                RecyclerListView.Holder holder = (RecyclerListView.Holder) GroupCallActivity.this.listView.findViewHolderForAdapterPosition(0);
                if (holder == null || holder.itemView.getTop() <= 0) {
                    return;
                }
                GroupCallActivity.this.listView.smoothScrollBy(0, holder.itemView.getTop());
                return;
            }
            if (GroupCallActivity.this.recordHintView != null) {
                GroupCallActivity.this.recordHintView.hide();
            }
            if (GroupCallActivity.this.reminderHintView != null) {
                GroupCallActivity.this.reminderHintView.hide();
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$11 */
    /* JADX INFO: loaded from: classes6.dex */
    class C554911 extends GridLayoutManager.SpanSizeLookup {
        C554911() {
        }

        @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
        public int getSpanSize(int i7) {
            int i8 = GroupCallActivity.isLandscapeMode ? 6 : 2;
            if (GroupCallActivity.isTabletMode || i7 < GroupCallActivity.this.listAdapter.usersVideoGridStartRow || i7 >= GroupCallActivity.this.listAdapter.usersVideoGridEndRow) {
                return i8;
            }
            int i9 = GroupCallActivity.this.listAdapter.usersVideoGridEndRow - GroupCallActivity.this.listAdapter.usersVideoGridStartRow;
            int i10 = (i7 != GroupCallActivity.this.listAdapter.usersVideoGridEndRow - 1 || (!GroupCallActivity.isLandscapeMode && i9 % 2 == 0)) ? 1 : 2;
            if (!GroupCallActivity.isLandscapeMode) {
                return i10;
            }
            if (i9 == 1) {
                return 6;
            }
            return i9 == 2 ? 3 : 2;
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$12 */
    /* JADX INFO: loaded from: classes6.dex */
    class C555012 extends RecyclerView.ItemDecoration {
        C555012() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
            if (childAdapterPosition >= 0) {
                rect.setEmpty();
                if (childAdapterPosition < GroupCallActivity.this.listAdapter.usersVideoGridStartRow || childAdapterPosition >= GroupCallActivity.this.listAdapter.usersVideoGridEndRow) {
                    return;
                }
                int i7 = childAdapterPosition - GroupCallActivity.this.listAdapter.usersVideoGridStartRow;
                int i8 = GroupCallActivity.isLandscapeMode ? 6 : 2;
                int i9 = i7 % i8;
                if (i9 == 0) {
                    rect.right = AndroidUtilities.m1081dp(2.0f);
                } else if (i9 == i8 - 1) {
                    rect.left = AndroidUtilities.m1081dp(2.0f);
                } else {
                    rect.left = AndroidUtilities.m1081dp(1.0f);
                }
            }
        }
    }

    public /* synthetic */ void lambda$new$24(Activity activity, final ChatObject.Call call, View view, int i, float f, float f2) {
        TLRPC.Chat chat;
        final ChatObject.Call.InvitedUser invitedUser;
        if (view instanceof GroupCallGridCell) {
            fullscreenFor(((GroupCallGridCell) view).getParticipant());
            return;
        }
        if (view instanceof GroupCallUserCell) {
            showMenuForCell((GroupCallUserCell) view);
            return;
        }
        l = null;
        l = null;
        l = null;
        final Long l = null;
        boolean z = false;
        if (view instanceof GroupCallInvitedCell) {
            GroupCallInvitedCell groupCallInvitedCell = (GroupCallInvitedCell) view;
            if (groupCallInvitedCell.getUser() == null) {
                return;
            }
            if (isConference()) {
                if (i - this.listAdapter.shadyJoinStartRow >= 0 && i - this.listAdapter.shadyJoinStartRow < this.call.shadyJoinParticipants.size()) {
                    l = this.call.shadyJoinParticipants.get(i - this.listAdapter.shadyJoinStartRow);
                } else if (i - this.listAdapter.shadyLeftStartRow >= 0 && i - this.listAdapter.shadyLeftStartRow < this.call.shadyLeftParticipants.size()) {
                    l = this.call.shadyLeftParticipants.get(i - this.listAdapter.shadyLeftStartRow);
                } else {
                    int i2 = i - this.listAdapter.invitedStartRow;
                    if (this.delayedGroupCallUpdated) {
                        if (i2 >= 0 && i2 < this.oldInvited.size()) {
                            l = (Long) this.oldInvited.get(i2);
                        }
                    } else if (i2 >= 0 && i2 < this.call.invitedUsers.size()) {
                        l = this.call.invitedUsers.get(i2);
                    }
                    if (z && (invitedUser = this.call.invitedUsersMessageIds.get(l)) != null) {
                        ItemOptions.makeOptions(this.container, this.resourcesProvider, groupCallInvitedCell).addIf(invitedUser.isCalling(), C2702R.drawable.msg_endcall, LocaleController.getString(C2702R.string.GroupCallStopCallingInvite), new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda63
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$new$12(invitedUser, l);
                            }
                        }).add(C2702R.drawable.msg_remove, LocaleController.getString(C2702R.string.GroupCallDiscardInvite), new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda64
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$new$14(invitedUser, l);
                            }
                        }).setScrimViewBackground(Theme.createRoundRectDrawable(AndroidUtilities.m1081dp(8.0f), AndroidUtilities.m1081dp(8.0f), this.listViewBackgroundPaint.getColor())).setDimAlpha(96).show();
                        return;
                    }
                    return;
                }
                z = true;
                if (z) {
                    return;
                }
                ItemOptions.makeOptions(this.container, this.resourcesProvider, groupCallInvitedCell).addIf(invitedUser.isCalling(), C2702R.drawable.msg_endcall, LocaleController.getString(C2702R.string.GroupCallStopCallingInvite), new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda63
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$new$12(invitedUser, l);
                    }
                }).add(C2702R.drawable.msg_remove, LocaleController.getString(C2702R.string.GroupCallDiscardInvite), new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda64
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$new$14(invitedUser, l);
                    }
                }).setScrimViewBackground(Theme.createRoundRectDrawable(AndroidUtilities.m1081dp(8.0f), AndroidUtilities.m1081dp(8.0f), this.listViewBackgroundPaint.getColor())).setDimAlpha(96).show();
                return;
            }
            this.parentActivity.switchToAccount(this.currentAccount, true);
            Bundle bundle = new Bundle();
            bundle.putLong("user_id", groupCallInvitedCell.getUser().f1775id);
            if (groupCallInvitedCell.hasAvatarSet()) {
                bundle.putBoolean("expandPhoto", true);
            }
            this.parentActivity.lambda$runLinkRequest$103(new ProfileActivity(bundle));
            lambda$new$0();
            return;
        }
        if (i == this.listAdapter.addMemberRow) {
            if (ChatObject.isChannel(this.currentChat) && (chat = this.currentChat) != null && !chat.megagroup && ChatObject.isPublic(chat)) {
                getLink(false);
                return;
            }
            TLRPC.ChatFull chatFull = this.accountInstance.getMessagesController().getChatFull(getChatId());
            if (chatFull == null) {
                return;
            }
            this.enterEventSent = false;
            Context context = getContext();
            int currentAccount = this.accountInstance.getCurrentAccount();
            TLRPC.Chat chat2 = this.currentChat;
            ChatObject.Call call2 = this.call;
            GroupVoipInviteAlert groupVoipInviteAlert = new GroupVoipInviteAlert(context, currentAccount, chat2, chatFull, call2.participants, call2.invitedUsersMap);
            this.groupVoipInviteAlert = groupVoipInviteAlert;
            groupVoipInviteAlert.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda65
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    this.f$0.lambda$new$15(dialogInterface);
                }
            });
            this.groupVoipInviteAlert.setDelegate(new GroupVoipInviteAlert.GroupVoipInviteAlertDelegate() { // from class: org.telegram.ui.GroupCallActivity.13
                C555113() {
                }

                @Override // org.telegram.ui.Components.GroupVoipInviteAlert.GroupVoipInviteAlertDelegate
                public void copyInviteLink() {
                    GroupCallActivity.this.getLink(true);
                }

                @Override // org.telegram.ui.Components.GroupVoipInviteAlert.GroupVoipInviteAlertDelegate
                public void inviteUser(long j) {
                    GroupCallActivity.this.inviteUserToCall(j, true);
                }

                @Override // org.telegram.ui.Components.GroupVoipInviteAlert.GroupVoipInviteAlertDelegate
                public void needOpenSearch(MotionEvent motionEvent, EditTextBoldCursor editTextBoldCursor) {
                    if (GroupCallActivity.this.enterEventSent) {
                        return;
                    }
                    if (motionEvent.getX() > editTextBoldCursor.getLeft() && motionEvent.getX() < editTextBoldCursor.getRight() && motionEvent.getY() > editTextBoldCursor.getTop() && motionEvent.getY() < editTextBoldCursor.getBottom()) {
                        GroupCallActivity groupCallActivity = GroupCallActivity.this;
                        groupCallActivity.makeFocusable(groupCallActivity.groupVoipInviteAlert, null, editTextBoldCursor, true);
                    } else {
                        GroupCallActivity groupCallActivity2 = GroupCallActivity.this;
                        groupCallActivity2.makeFocusable(groupCallActivity2.groupVoipInviteAlert, null, editTextBoldCursor, false);
                    }
                }
            });
            this.groupVoipInviteAlert.show();
            return;
        }
        if (i == this.listAdapter.conferenceAddPeopleRow) {
            ChatObject.Call call3 = this.call;
            if (call3 == null || call3.call == null) {
                return;
            }
            UserSelectorBottomSheet userSelectorBottomSheet = new UserSelectorBottomSheet(activity, this.currentAccount, 0L, null, 4, true, new DarkBlueThemeResourcesProvider());
            ChatObject.Call call4 = this.call;
            userSelectorBottomSheet.exceptUsers(call4 != null ? (Collection) Collection.EL.stream(call4.sortedParticipants).map(new Function() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda66
                public /* synthetic */ Function andThen(Function function) {
                    return Function$CC.$default$andThen(this, function);
                }

                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Long.valueOf(DialogObject.getPeerDialogId(((TLRPC.GroupCallParticipant) obj).peer));
                }

                public /* synthetic */ Function compose(Function function) {
                    return Function$CC.$default$compose(this, function);
                }
            }).collect(Collectors.toSet()) : null).setOnShareCallLinkListener(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda67
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.openShareConferenceLink();
                }
            }).setOnUsersSelector(new Utilities.Callback2() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda68
                @Override // org.telegram.messenger.Utilities.Callback2
                public final void run(Object obj, Object obj2) {
                    this.f$0.lambda$new$23(call, (Boolean) obj, (HashSet) obj2);
                }
            }).show();
            return;
        }
        if (i == this.listAdapter.conferenceShareLinkRow) {
            openShareConferenceLink();
        }
    }

    public /* synthetic */ void lambda$new$12(ChatObject.Call.InvitedUser invitedUser, Long l) {
        TL_phone.declineConferenceCallInvite declineconferencecallinvite = new TL_phone.declineConferenceCallInvite();
        declineconferencecallinvite.msg_id = invitedUser.msg_id;
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(declineconferencecallinvite, new RequestDelegate() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda74
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$new$11(tLObject, tL_error);
            }
        });
        ChatObject.Call call = this.call;
        if (call != null) {
            invitedUser.calling = false;
            call.invitedUsersMessageIds.put(l, invitedUser);
            applyCallParticipantUpdates(true);
        }
    }

    public /* synthetic */ void lambda$new$11(TLObject tLObject, TLRPC.TL_error tL_error) {
        if (tLObject instanceof TLRPC.Updates) {
            MessagesController.getInstance(this.currentAccount).processUpdates((TLRPC.Updates) tLObject, false);
        }
    }

    public /* synthetic */ void lambda$new$14(ChatObject.Call.InvitedUser invitedUser, Long l) {
        TL_phone.declineConferenceCallInvite declineconferencecallinvite = new TL_phone.declineConferenceCallInvite();
        declineconferencecallinvite.msg_id = invitedUser.msg_id;
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(declineconferencecallinvite, new RequestDelegate() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda73
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$new$13(tLObject, tL_error);
            }
        });
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(Integer.valueOf(invitedUser.msg_id));
        MessagesController.getInstance(this.currentAccount).deleteMessages(arrayList, null, null, 0L, 0, true, 0);
        ChatObject.Call call = this.call;
        if (call != null) {
            call.invitedUsers.remove(l);
            this.call.invitedUsersMap.remove(l);
            this.call.invitedUsersMessageIds.remove(l);
            applyCallParticipantUpdates(true);
        }
    }

    public /* synthetic */ void lambda$new$13(TLObject tLObject, TLRPC.TL_error tL_error) {
        if (tLObject instanceof TLRPC.Updates) {
            MessagesController.getInstance(this.currentAccount).processUpdates((TLRPC.Updates) tLObject, false);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$13 */
    /* JADX INFO: loaded from: classes6.dex */
    class C555113 implements GroupVoipInviteAlert.GroupVoipInviteAlertDelegate {
        C555113() {
        }

        @Override // org.telegram.ui.Components.GroupVoipInviteAlert.GroupVoipInviteAlertDelegate
        public void copyInviteLink() {
            GroupCallActivity.this.getLink(true);
        }

        @Override // org.telegram.ui.Components.GroupVoipInviteAlert.GroupVoipInviteAlertDelegate
        public void inviteUser(long j) {
            GroupCallActivity.this.inviteUserToCall(j, true);
        }

        @Override // org.telegram.ui.Components.GroupVoipInviteAlert.GroupVoipInviteAlertDelegate
        public void needOpenSearch(MotionEvent motionEvent, EditTextBoldCursor editTextBoldCursor) {
            if (GroupCallActivity.this.enterEventSent) {
                return;
            }
            if (motionEvent.getX() > editTextBoldCursor.getLeft() && motionEvent.getX() < editTextBoldCursor.getRight() && motionEvent.getY() > editTextBoldCursor.getTop() && motionEvent.getY() < editTextBoldCursor.getBottom()) {
                GroupCallActivity groupCallActivity = GroupCallActivity.this;
                groupCallActivity.makeFocusable(groupCallActivity.groupVoipInviteAlert, null, editTextBoldCursor, true);
            } else {
                GroupCallActivity groupCallActivity2 = GroupCallActivity.this;
                groupCallActivity2.makeFocusable(groupCallActivity2.groupVoipInviteAlert, null, editTextBoldCursor, false);
            }
        }
    }

    public /* synthetic */ void lambda$new$15(DialogInterface dialogInterface) {
        this.groupVoipInviteAlert = null;
    }

    public /* synthetic */ void lambda$new$23(final ChatObject.Call call, Boolean bool, HashSet hashSet) {
        TLRPC.GroupCall groupCall;
        VoIPService sharedInstance;
        ChatObject.Call call2 = this.call;
        if (call2 == null || (groupCall = call2.call) == null) {
            return;
        }
        final String str = groupCall.invite_link;
        final int size = hashSet.size();
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        final HashSet hashSet2 = new HashSet();
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            final long jLongValue = ((Long) it.next()).longValue();
            this.call.addInvitedUser(jLongValue);
            TL_phone.inviteConferenceCallParticipant inviteconferencecallparticipant = new TL_phone.inviteConferenceCallParticipant();
            TLRPC.TL_inputGroupCall tL_inputGroupCall = new TLRPC.TL_inputGroupCall();
            inviteconferencecallparticipant.call = tL_inputGroupCall;
            TLRPC.GroupCall groupCall2 = this.call.call;
            tL_inputGroupCall.f1632id = groupCall2.f1625id;
            tL_inputGroupCall.access_hash = groupCall2.access_hash;
            inviteconferencecallparticipant.user_id = MessagesController.getInstance(this.currentAccount).getInputUser(jLongValue);
            inviteconferencecallparticipant.video = bool.booleanValue();
            ConnectionsManager.getInstance(this.currentAccount).sendRequest(inviteconferencecallparticipant, new RequestDelegate() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda75
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    this.f$0.lambda$new$22(jLongValue, hashSet2, atomicInteger, size, call, str, tLObject, tL_error);
                }
            });
        }
        applyCallParticipantUpdates(true);
        if (!bool.booleanValue() || (sharedInstance = VoIPService.getSharedInstance()) == null || sharedInstance.getVideoState(false) == 2 || sharedInstance.getVideoState(false) == 1) {
            return;
        }
        sharedInstance.createCaptureDevice(false);
        if (!sharedInstance.isFrontFaceCamera()) {
            sharedInstance.switchCamera();
        }
        sharedInstance.requestVideoCall(false);
        sharedInstance.setVideoState(false, 2);
        sharedInstance.setMicMute(false, false, true);
        sharedInstance.switchToSpeaker();
        updateState(true, true);
    }

    public /* synthetic */ void lambda$new$22(final long j, final HashSet hashSet, AtomicInteger atomicInteger, int i, final ChatObject.Call call, final String str, TLObject tLObject, TLRPC.TL_error tL_error) {
        if (tLObject instanceof TLRPC.Updates) {
            final TLRPC.Updates updates = (TLRPC.Updates) tLObject;
            MessagesController.getInstance(this.currentAccount).processUpdates(updates, false);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda78
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$new$17(updates, j);
                }
            });
        } else if (tL_error != null && "USER_PRIVACY_RESTRICTED".equalsIgnoreCase(tL_error.text)) {
            hashSet.add(Long.valueOf(j));
        }
        if (atomicInteger.incrementAndGet() != i || hashSet.isEmpty()) {
            return;
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda79
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$21(hashSet, call, str);
            }
        });
    }

    public /* synthetic */ void lambda$new$17(TLRPC.Updates updates, long j) {
        TLRPC.Update update = updates.update;
        int i = 0;
        if (update instanceof TLRPC.TL_updateNewMessage) {
            TLRPC.Message message = ((TLRPC.TL_updateNewMessage) update).message;
            if (message != null && (message.action instanceof TLRPC.TL_messageActionConferenceCall)) {
                i = message.f1636id;
            }
        } else if (update instanceof TLRPC.TL_updateMessageID) {
            i = ((TLRPC.TL_updateMessageID) update).f1764id;
        } else if (updates.updates != null) {
            int i2 = 0;
            while (true) {
                if (i2 >= updates.updates.size()) {
                    break;
                }
                TLRPC.Update update2 = updates.updates.get(i2);
                if (update2 instanceof TLRPC.TL_updateNewMessage) {
                    TLRPC.Message message2 = ((TLRPC.TL_updateNewMessage) update2).message;
                    if (message2 != null && (message2.action instanceof TLRPC.TL_messageActionConferenceCall)) {
                        i = message2.f1636id;
                        break;
                    }
                    i2++;
                } else {
                    if (update2 instanceof TLRPC.TL_updateMessageID) {
                        i = ((TLRPC.TL_updateMessageID) update2).f1764id;
                        break;
                    }
                    i2++;
                }
            }
        }
        ChatObject.Call call = this.call;
        if (call == null || i == 0) {
            return;
        }
        call.invitedUsersMessageIds.put(Long.valueOf(j), ChatObject.Call.InvitedUser.make(i));
        applyCallParticipantUpdates(true);
    }

    public /* synthetic */ void lambda$new$21(HashSet hashSet, ChatObject.Call call, final String str) {
        TL_account.getRequirementsToContact getrequirementstocontact = new TL_account.getRequirementsToContact();
        final ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        final ArrayList arrayList3 = new ArrayList();
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            Long l = (Long) it.next();
            long jLongValue = l.longValue();
            if (call != null) {
                call.removeInvitedUser(jLongValue);
            }
            arrayList.add(MessagesController.getInstance(this.currentAccount).getUser(l));
            getrequirementstocontact.f1789id.add(MessagesController.getInstance(this.currentAccount).getInputUser(jLongValue));
        }
        final Runnable runnable = new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda86
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$18(arrayList, arrayList2, arrayList3, str);
            }
        };
        if (UserConfig.getInstance(this.currentAccount).isPremium()) {
            runnable.run();
        } else {
            ConnectionsManager.getInstance(this.currentAccount).sendRequest(getrequirementstocontact, new RequestDelegate() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda87
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda88
                        @Override // java.lang.Runnable
                        public final void run() {
                            GroupCallActivity.$r8$lambda$VySc520Ees6mNZtPHpJFl8XHJWY(tLObject, arrayList, arrayList, runnable);
                        }
                    });
                }
            });
        }
    }

    public /* synthetic */ void lambda$new$18(ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, String str) {
        BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
        if (safeLastFragment == null) {
            return;
        }
        LimitReachedBottomSheet limitReachedBottomSheet = new LimitReachedBottomSheet(safeLastFragment, safeLastFragment.getContext(), 34, this.currentAccount, new DarkBlueThemeResourcesProvider());
        limitReachedBottomSheet.setRestrictedUsers(null, arrayList, arrayList2, arrayList3, str);
        limitReachedBottomSheet.show();
    }

    public static /* synthetic */ void $r8$lambda$VySc520Ees6mNZtPHpJFl8XHJWY(TLObject tLObject, ArrayList arrayList, ArrayList arrayList2, Runnable runnable) {
        if (tLObject instanceof Vector) {
            Vector vector = (Vector) tLObject;
            for (int i = 0; i < Math.min(arrayList.size(), vector.objects.size()); i++) {
                if (vector.objects.get(i) instanceof TL_account.requirementToContactPremium) {
                    arrayList2.add(Long.valueOf(((TLRPC.User) arrayList.get(i)).f1775id));
                }
            }
        }
        runnable.run();
    }

    public /* synthetic */ boolean lambda$new$25(View view, int i) {
        if (isRtmpStream()) {
            return false;
        }
        if (view instanceof GroupCallGridCell) {
            return showMenuForCell(view);
        }
        if (!(view instanceof GroupCallUserCell)) {
            return false;
        }
        updateItems();
        return ((GroupCallUserCell) view).clickMuteButton();
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$14 */
    /* JADX INFO: loaded from: classes6.dex */
    class C555214 extends GridLayoutManager.SpanSizeLookup {
        C555214() {
        }

        @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
        public int getSpanSize(int i7) {
            return GroupCallActivity.this.tabletGridAdapter.getSpanCount(i7);
        }
    }

    public /* synthetic */ void lambda$new$26(View view, int i) {
        GroupCallGridCell groupCallGridCell = (GroupCallGridCell) view;
        if (groupCallGridCell.getParticipant() != null) {
            fullscreenFor(groupCallGridCell.getParticipant());
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$15 */
    /* JADX INFO: loaded from: classes6.dex */
    class C555315 extends DefaultItemAnimator {
        C555315() {
        }

        @Override // androidx.recyclerview.widget.DefaultItemAnimator
        protected void onMoveAnimationUpdate(RecyclerView.ViewHolder viewHolder) {
            GroupCallActivity.this.listView.invalidate();
            GroupCallActivity.this.renderersContainer.invalidate();
            ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
            GroupCallActivity.this.updateLayout(true);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$16 */
    /* JADX INFO: loaded from: classes6.dex */
    class C555416 extends RecyclerView.OnScrollListener {
        C555416() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int i7, int i8) {
            super.onScrolled(recyclerView, i7, i8);
            ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$17 */
    /* JADX INFO: loaded from: classes6.dex */
    class C555517 extends GroupCallActivityButtonsLayout {
        int currentLightColor;
        final OvershootInterpolator overshootInterpolator = new OvershootInterpolator(1.5f);

        C555517(final Context activity3) {
            super(activity3);
            this.overshootInterpolator = new OvershootInterpolator(1.5f);
        }

        /* JADX WARN: Removed duplicated region for block: B:496:0x0163  */
        /* JADX WARN: Removed duplicated region for block: B:575:0x0463  */
        /* JADX WARN: Removed duplicated region for block: B:587:0x04d4  */
        /* JADX WARN: Removed duplicated region for block: B:606:0x06aa  */
        /* JADX WARN: Removed duplicated region for block: B:631:0x0760  */
        /* JADX WARN: Removed duplicated region for block: B:634:0x076c  */
        /* JADX WARN: Removed duplicated region for block: B:635:0x07d8  */
        /* JADX WARN: Removed duplicated region for block: B:643:0x08da  */
        /* JADX WARN: Removed duplicated region for block: B:652:0x0944  */
        /* JADX WARN: Removed duplicated region for block: B:655:0x096d  */
        /* JADX WARN: Removed duplicated region for block: B:664:0x0a0d  */
        /* JADX WARN: Removed duplicated region for block: B:675:0x0b0e  */
        /* JADX WARN: Removed duplicated region for block: B:682:? A[RETURN, SYNTHETIC] */
        @Override // android.view.ViewGroup, android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        protected void dispatchDraw(android.graphics.Canvas r28) {
            /*
                Method dump skipped, instruction units count: 2834
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.GroupCallActivity.C555517.dispatchDraw(android.graphics.Canvas):void");
        }
    }

    public /* synthetic */ void lambda$new$27(View view) {
        ChatObject.Call call = this.call;
        if (call == null || call.isScheduled() || isRtmpStream()) {
            getLink(false);
        } else {
            if (VoIPService.getSharedInstance() == null) {
                return;
            }
            VoIPService.getSharedInstance().toggleSpeakerphoneOrShowRouteSheet(getContext(), false);
        }
    }

    public /* synthetic */ void lambda$new$28(View view) {
        this.renderersContainer.delayHideUi();
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        if (sharedInstance != null) {
            if (sharedInstance.getVideoState(false) == 2) {
                sharedInstance.switchCamera();
                if (this.flipIconCurrentEndFrame == 18) {
                    RLottieDrawable rLottieDrawable = this.flipIcon;
                    this.flipIconCurrentEndFrame = 39;
                    rLottieDrawable.setCustomEndFrame(39);
                    this.flipIcon.start();
                } else {
                    this.flipIcon.setCurrentFrame(0, false);
                    RLottieDrawable rLottieDrawable2 = this.flipIcon;
                    this.flipIconCurrentEndFrame = 18;
                    rLottieDrawable2.setCustomEndFrame(18);
                    this.flipIcon.start();
                }
                for (int i = 0; i < this.attachedRenderers.size(); i++) {
                    GroupCallMiniTextureView groupCallMiniTextureView = (GroupCallMiniTextureView) this.attachedRenderers.get(i);
                    ChatObject.VideoParticipant videoParticipant = groupCallMiniTextureView.participant;
                    if (videoParticipant.participant.self && !videoParticipant.presentation) {
                        groupCallMiniTextureView.startFlipAnimation();
                    }
                }
            }
        }
    }

    public /* synthetic */ void lambda$new$30(View view) {
        final int nextAudioOutputValue = getNextAudioOutputValue();
        this.cacheAudioOutputValue = Integer.valueOf(nextAudioOutputValue);
        updateState(true, true);
        this.cacheAudioOutputValue = null;
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda42
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$29(nextAudioOutputValue);
            }
        });
    }

    public /* synthetic */ void lambda$new$29(int i) {
        setAudioOutputValue(i);
        BulletinFactory.m1194of(this.topBulletinContainer, new DarkBlueThemeResourcesProvider()).createSimpleBulletin(getContext().getResources().getDrawable(getAudioOutputToastIcon(i)).mutate(), getAudioOutputToastText(i)).show(isBulletinTop());
    }

    public /* synthetic */ void lambda$new$31(Activity activity, View view) {
        this.renderersContainer.delayHideUi();
        ChatObject.Call call = this.call;
        if (call == null || call.isScheduled()) {
            lambda$new$0();
        } else {
            updateItems();
            onLeaveClick(activity, new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda44
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$new$0();
                }
            }, false);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$18 */
    /* JADX INFO: loaded from: classes6.dex */
    class C555618 extends RLottieImageView {
        C555618(final Context activity3) {
            super(activity3);
        }

        /* JADX WARN: Removed duplicated region for block: B:89:0x0032  */
        @Override // android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean onTouchEvent(android.view.MotionEvent r13) {
            /*
                Method dump skipped, instruction units count: 217
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.GroupCallActivity.C555618.onTouchEvent(android.view.MotionEvent):boolean");
        }

        @Override // android.view.View
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName(Button.class.getName());
            accessibilityNodeInfo.setEnabled(GroupCallActivity.this.muteButtonState == 0 || GroupCallActivity.this.muteButtonState == 1);
            if (GroupCallActivity.this.muteButtonState == 1) {
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, LocaleController.getString(C2702R.string.VoipMute)));
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$19 */
    /* JADX INFO: loaded from: classes6.dex */
    class ViewOnClickListenerC555719 implements View.OnClickListener {
        Runnable finishRunnable = new Runnable() { // from class: org.telegram.ui.GroupCallActivity.19.1
            AnonymousClass1() {
            }

            @Override // java.lang.Runnable
            public void run() {
                GroupCallActivity.this.muteButtonIcon.setAnimation(GroupCallActivity.this.bigMicDrawable);
                GroupCallActivity.this.playingHandAnimation = false;
            }
        };

        ViewOnClickListenerC555719() {
        }

        /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$19$1 */
        class AnonymousClass1 implements Runnable {
            AnonymousClass1() {
            }

            @Override // java.lang.Runnable
            public void run() {
                GroupCallActivity.this.muteButtonIcon.setAnimation(GroupCallActivity.this.bigMicDrawable);
                GroupCallActivity.this.playingHandAnimation = false;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:356:0x01b9 A[PHI: r1 r3
  0x01b9: PHI (r1v10 int) = (r1v5 int), (r1v8 int) binds: [B:355:0x01b7, B:361:0x01c8] A[DONT_GENERATE, DONT_INLINE]
  0x01b9: PHI (r3v6 int) = (r3v4 int), (r3v5 int) binds: [B:355:0x01b7, B:361:0x01c8] A[DONT_GENERATE, DONT_INLINE]] */
        @Override // android.view.View.OnClickListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onClick(android.view.View r14) {
            /*
                Method dump skipped, instruction units count: 698
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.GroupCallActivity.ViewOnClickListenerC555719.onClick(android.view.View):void");
        }

        public /* synthetic */ void lambda$onClick$0() {
            GroupCallActivity.this.wasNotInLayoutFullscreen = null;
            GroupCallActivity groupCallActivity = GroupCallActivity.this;
            groupCallActivity.updateMuteButton(groupCallActivity.muteButtonState, true);
        }

        public /* synthetic */ void lambda$onClick$1(TLObject tLObject, TLRPC.TL_error tL_error) {
            if (tLObject != null) {
                GroupCallActivity.this.accountInstance.getMessagesController().processUpdates((TLRPC.Updates) tLObject, false);
            }
        }

        public /* synthetic */ void lambda$onClick$2(TLObject tLObject, TLRPC.TL_error tL_error) {
            if (tLObject != null) {
                GroupCallActivity.this.accountInstance.getMessagesController().processUpdates((TLRPC.Updates) tLObject, false);
            }
        }
    }

    public /* synthetic */ void lambda$new$32(int i) {
        this.actionBar.getActionBarMenuOnItemClick().onItemClick(i);
    }

    public /* synthetic */ void lambda$new$33(View view) {
        ChatObject.Call call = this.call;
        if (call == null || this.renderersContainer.inFullscreenMode) {
            return;
        }
        if (call.call.join_muted) {
            ActionBarMenuSubItem actionBarMenuSubItem = this.everyoneItem;
            int i = Theme.key_voipgroup_actionBarItems;
            actionBarMenuSubItem.setColors(Theme.getColor(i), Theme.getColor(i));
            this.everyoneItem.setChecked(false);
            ActionBarMenuSubItem actionBarMenuSubItem2 = this.adminItem;
            int i2 = Theme.key_voipgroup_checkMenu;
            actionBarMenuSubItem2.setColors(Theme.getColor(i2), Theme.getColor(i2));
            this.adminItem.setChecked(true);
        } else {
            ActionBarMenuSubItem actionBarMenuSubItem3 = this.everyoneItem;
            int i3 = Theme.key_voipgroup_checkMenu;
            actionBarMenuSubItem3.setColors(Theme.getColor(i3), Theme.getColor(i3));
            this.everyoneItem.setChecked(true);
            ActionBarMenuSubItem actionBarMenuSubItem4 = this.adminItem;
            int i4 = Theme.key_voipgroup_actionBarItems;
            actionBarMenuSubItem4.setColors(Theme.getColor(i4), Theme.getColor(i4));
            this.adminItem.setChecked(false);
        }
        this.changingPermissions = false;
        this.otherItem.hideSubItem(1);
        this.otherItem.hideSubItem(2);
        if (VoIPService.getSharedInstance() != null && (VoIPService.getSharedInstance().hasEarpiece() || VoIPService.getSharedInstance().isBluetoothHeadsetConnected())) {
            int currentAudioRoute = VoIPService.getSharedInstance().getCurrentAudioRoute();
            if (currentAudioRoute == 2) {
                this.soundItem.setIcon(C2702R.drawable.msg_voice_bluetooth);
                this.soundItem.setSubtext(VoIPService.getSharedInstance().currentBluetoothDeviceName != null ? VoIPService.getSharedInstance().currentBluetoothDeviceName : LocaleController.getString(C2702R.string.VoipAudioRoutingBluetooth));
            } else if (currentAudioRoute == 0) {
                this.soundItem.setIcon(VoIPService.getSharedInstance().isHeadsetPlugged() ? C2702R.drawable.msg_voice_headphones : C2702R.drawable.msg_voice_phone);
                this.soundItem.setSubtext(LocaleController.getString(VoIPService.getSharedInstance().isHeadsetPlugged() ? C2702R.string.VoipAudioRoutingHeadset : C2702R.string.VoipAudioRoutingPhone));
            } else if (currentAudioRoute == 1) {
                if (VoipAudioManager.get().isSpeakerphoneOn()) {
                    this.soundItem.setIcon(C2702R.drawable.msg_voice_speaker);
                    this.soundItem.setSubtext(LocaleController.getString(C2702R.string.VoipAudioRoutingSpeaker));
                } else {
                    this.soundItem.setIcon(C2702R.drawable.msg_voice_phone);
                    this.soundItem.setSubtext(LocaleController.getString(C2702R.string.VoipAudioRoutingPhone));
                }
            }
        }
        updateItems();
        this.otherItem.toggleSubMenu();
    }

    public /* synthetic */ void lambda$new$34(View view) {
        if (isRtmpStream()) {
            if (PipUtils.checkAnyPipPermissions(this.parentActivity)) {
                RTMPStreamPipOverlay.show(this.parentActivity);
                lambda$new$0();
                return;
            } else {
                AlertsCreator.createDrawOverlayPermissionDialog(this.parentActivity, null, true).show();
                return;
            }
        }
        if (AndroidUtilities.checkInlinePermissions(this.parentActivity)) {
            GroupCallPip.clearForce();
            lambda$new$0();
        } else {
            AlertsCreator.createDrawOverlayGroupCallPermissionDialog(getContext()).show();
        }
    }

    public /* synthetic */ void lambda$new$35(View view) {
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        if (sharedInstance == null) {
            return;
        }
        if (sharedInstance.getVideoState(true) == 2) {
            sharedInstance.stopScreenCapture();
        } else {
            startScreenCapture();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$20 */
    /* JADX INFO: loaded from: classes6.dex */
    class C555920 extends AudioPlayerAlert.ClippingTextViewSwitcher {
        final /* synthetic */ Activity val$context;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C555920(Context context, Activity activity) {
            super(context);
            this.val$context = activity;
        }

        @Override // org.telegram.ui.Components.AudioPlayerAlert.ClippingTextViewSwitcher
        protected TextView createTextView() {
            final TextView textView = new TextView(this.val$context);
            textView.setTextColor(Theme.getColor(Theme.key_voipgroup_actionBarItems));
            textView.setTextSize(1, 20.0f);
            textView.setTypeface(AndroidUtilities.bold());
            textView.setGravity(51);
            textView.setSingleLine(true);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.GroupCallActivity$20$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$createTextView$0(textView, view);
                }
            });
            return textView;
        }

        public /* synthetic */ void lambda$createTextView$0(TextView textView, View view) {
            GroupCallActivity groupCallActivity = GroupCallActivity.this;
            ChatObject.Call call = groupCallActivity.call;
            if (call == null || !call.recording) {
                return;
            }
            groupCallActivity.showRecordHint(textView);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$21 */
    /* JADX INFO: loaded from: classes6.dex */
    class C556021 extends View {
        C556021(final Context activity3) {
            super(activity3);
        }

        @Override // android.view.View
        protected void onMeasure(int i10, int i11) {
            setMeasuredDimension(View.MeasureSpec.getSize(i10), ActionBar.getCurrentActionBarHeight());
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$22 */
    /* JADX INFO: loaded from: classes6.dex */
    class C556122 extends TextView {
        private RectF rect = new RectF();

        C556122(Context context) {
            super(context);
            this.rect = new RectF();
        }

        @Override // android.widget.TextView, android.view.View
        protected void onDraw(Canvas canvas) {
            this.rect.set(0.0f, 0.0f, getWidth(), getHeight());
            canvas.drawRoundRect(this.rect, AndroidUtilities.m1081dp(12.0f), AndroidUtilities.m1081dp(12.0f), GroupCallActivity.this.liveLabelPaint);
            super.onDraw(canvas);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$23 */
    /* JADX INFO: loaded from: classes6.dex */
    class C556223 extends UndoView {
        C556223(final Context activity3) {
            super(activity3);
        }

        @Override // org.telegram.p026ui.Components.UndoView
        public void showWithAction(long j, int i11, Object obj, Object obj2, Runnable runnable, Runnable runnable2) {
            if (GroupCallActivity.this.previewDialog != null) {
                return;
            }
            super.showWithAction(j, i11, obj, obj2, runnable, runnable2);
        }
    }

    public /* synthetic */ void lambda$new$36(View view) {
        ChatObject.Call call = this.call;
        if (call == null || !call.recording) {
            return;
        }
        showRecordHint(this.actionBar.getTitleTextView());
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$24 */
    /* JADX INFO: loaded from: classes6.dex */
    class C556324 extends RecyclerListView {
        C556324(final Context activity3) {
            super(activity3);
        }

        @Override // org.telegram.p026ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
        public boolean drawChild(Canvas canvas, View view2, long j) {
            GroupCallFullscreenAdapter.GroupCallUserCell groupCallUserCell = (GroupCallFullscreenAdapter.GroupCallUserCell) view2;
            if (!GroupCallActivity.this.renderersContainer.isAnimating() && !GroupCallActivity.this.fullscreenListItemAnimator.isRunning()) {
                groupCallUserCell.setAlpha(1.0f);
                groupCallUserCell.setTranslationX(0.0f);
                groupCallUserCell.setTranslationY(0.0f);
            }
            if (groupCallUserCell.isRemoving(GroupCallActivity.this.fullscreenUsersListView) && groupCallUserCell.getRenderer() != null) {
                return true;
            }
            if (groupCallUserCell.getTranslationY() != 0.0f && groupCallUserCell.getRenderer() != null && groupCallUserCell.getRenderer().primaryView != null) {
                float top = GroupCallActivity.this.listView.getTop() - getTop();
                float f = GroupCallActivity.this.renderersContainer.progressToFullscreenMode;
                canvas.save();
                float f2 = 1.0f - f;
                canvas.clipRect(0.0f, top * f2, getMeasuredWidth(), ((GroupCallActivity.this.listView.getMeasuredHeight() + top) * f2) + (getMeasuredHeight() * f));
                boolean zDrawChild = super.drawChild(canvas, view2, j);
                canvas.restore();
                return zDrawChild;
            }
            return super.drawChild(canvas, view2, j);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$25 */
    /* JADX INFO: loaded from: classes6.dex */
    class C556425 extends DefaultItemAnimator {
        C556425() {
        }

        @Override // androidx.recyclerview.widget.DefaultItemAnimator
        protected void onMoveAnimationUpdate(RecyclerView.ViewHolder viewHolder) {
            GroupCallActivity.this.listView.invalidate();
            GroupCallActivity.this.renderersContainer.invalidate();
            ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
            GroupCallActivity.this.updateLayout(true);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$26 */
    /* JADX INFO: loaded from: classes6.dex */
    class C556526 extends RecyclerView.OnScrollListener {
        C556526() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int i16, int i17) {
            super.onScrolled(recyclerView, i16, i17);
            ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
            GroupCallActivity.this.renderersContainer.invalidate();
        }
    }

    public /* synthetic */ void lambda$new$37(View view, int i) {
        GroupCallFullscreenAdapter.GroupCallUserCell groupCallUserCell = (GroupCallFullscreenAdapter.GroupCallUserCell) view;
        if (groupCallUserCell.getVideoParticipant() == null) {
            fullscreenFor(new ChatObject.VideoParticipant(groupCallUserCell.getParticipant(), false, false));
        } else {
            fullscreenFor(groupCallUserCell.getVideoParticipant());
        }
    }

    public /* synthetic */ boolean lambda$new$38(View view, int i) {
        if (showMenuForCell(view)) {
            try {
                this.listView.performHapticFeedback(0);
            } catch (Exception unused) {
            }
        }
        return false;
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$27 */
    /* JADX INFO: loaded from: classes6.dex */
    class C556627 extends RecyclerView.ItemDecoration {
        C556627() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, View view2, RecyclerView recyclerView, RecyclerView.State state) {
            recyclerView.getChildAdapterPosition(view2);
            if (!GroupCallActivity.isLandscapeMode) {
                rect.set(AndroidUtilities.m1081dp(4.0f), 0, AndroidUtilities.m1081dp(4.0f), 0);
            } else {
                rect.set(0, AndroidUtilities.m1081dp(4.0f), 0, AndroidUtilities.m1081dp(4.0f));
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$28 */
    /* JADX INFO: loaded from: classes6.dex */
    class C556728 extends GroupCallRenderersContainer {
        C556728(final Context activity3, RecyclerView recyclerView, RecyclerView recyclerView2, ArrayList arrayList, ChatObject.Call call2, final GroupCallActivity this) {
            super(activity3, recyclerView, recyclerView2, arrayList, call2, this);
        }

        @Override // org.telegram.p026ui.Components.voip.GroupCallRenderersContainer
        protected void update() {
            super.update();
            ((BottomSheet) GroupCallActivity.this).navBarColor = AndroidUtilities.getOffsetColor(Theme.getColor(Theme.key_voipgroup_actionBarUnscrolled), Theme.getColor(Theme.key_voipgroup_actionBar), Math.max(GroupCallActivity.this.colorProgress, GroupCallActivity.this.renderersContainer == null ? 0.0f : GroupCallActivity.this.renderersContainer.progressToFullscreenMode), 1.0f);
            ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
            GroupCallActivity groupCallActivity2 = GroupCallActivity.this;
            groupCallActivity2.setColorProgress(groupCallActivity2.colorProgress);
        }

        @Override // org.telegram.p026ui.Components.voip.GroupCallRenderersContainer, android.view.ViewGroup
        protected boolean drawChild(Canvas canvas, View view2, long j) {
            if (view2 == GroupCallActivity.this.scrimRenderer) {
                return true;
            }
            return super.drawChild(canvas, view2, j);
        }

        @Override // org.telegram.p026ui.Components.voip.GroupCallRenderersContainer
        protected void onFullScreenModeChanged(boolean z2) {
            GroupCallActivity.this.delayedGroupCallUpdated = z2;
            if (GroupCallActivity.isTabletMode) {
                if (z2 || !GroupCallActivity.this.renderersContainer.inFullscreenMode) {
                    return;
                }
                GroupCallActivity groupCallActivity2 = GroupCallActivity.this;
                groupCallActivity2.tabletGridAdapter.setVisibility(groupCallActivity2.tabletVideoGridView, false, true);
                return;
            }
            if (z2) {
                GroupCallActivity.this.undoView[0].hide(false, 1);
                GroupCallActivity.this.renderersContainer.undoView[0].hide(false, 2);
                if (!GroupCallActivity.this.renderersContainer.inFullscreenMode) {
                    GroupCallActivity.this.listView.setVisibility(0);
                    GroupCallActivity.this.actionBar.setVisibility(0);
                    if (GroupCallActivity.this.watchersView != null) {
                        GroupCallActivity.this.watchersView.setVisibility(0);
                    }
                }
                GroupCallActivity.this.updateState(true, false);
                GroupCallActivity.this.buttonsContainer.requestLayout();
                if (GroupCallActivity.this.fullscreenUsersListView.getVisibility() != 0) {
                    GroupCallActivity.this.fullscreenUsersListView.setVisibility(0);
                    GroupCallActivity groupCallActivity3 = GroupCallActivity.this;
                    groupCallActivity3.fullscreenAdapter.setVisibility(groupCallActivity3.fullscreenUsersListView, true);
                    GroupCallActivity groupCallActivity4 = GroupCallActivity.this;
                    groupCallActivity4.fullscreenAdapter.update(false, groupCallActivity4.fullscreenUsersListView);
                } else {
                    GroupCallActivity groupCallActivity5 = GroupCallActivity.this;
                    groupCallActivity5.fullscreenAdapter.setVisibility(groupCallActivity5.fullscreenUsersListView, true);
                    GroupCallActivity.this.applyCallParticipantUpdates(true);
                }
            } else {
                if (!GroupCallActivity.this.renderersContainer.inFullscreenMode) {
                    GroupCallActivity.this.fullscreenUsersListView.setVisibility(8);
                    GroupCallActivity groupCallActivity6 = GroupCallActivity.this;
                    groupCallActivity6.fullscreenAdapter.setVisibility(groupCallActivity6.fullscreenUsersListView, false);
                } else {
                    GroupCallActivity.this.actionBar.setVisibility(8);
                    GroupCallActivity.this.listView.setVisibility(8);
                    if (GroupCallActivity.this.watchersView != null) {
                        GroupCallActivity.this.watchersView.setVisibility(8);
                    }
                }
                if (GroupCallActivity.this.fullscreenUsersListView.getVisibility() == 0) {
                    for (int i16 = 0; i16 < GroupCallActivity.this.fullscreenUsersListView.getChildCount(); i16++) {
                        View childAt = GroupCallActivity.this.fullscreenUsersListView.getChildAt(i16);
                        childAt.setAlpha(1.0f);
                        childAt.setScaleX(1.0f);
                        childAt.setScaleY(1.0f);
                        childAt.setTranslationX(0.0f);
                        childAt.setTranslationY(0.0f);
                        ((GroupCallFullscreenAdapter.GroupCallUserCell) childAt).setProgressToFullscreen(GroupCallActivity.this.renderersContainer.progressToFullscreenMode);
                    }
                }
            }
            GroupCallActivity.this.buttonsBackgroundGradientView2.setVisibility(z2 ? 0 : 8);
            if (GroupCallActivity.this.delayedGroupCallUpdated) {
                return;
            }
            GroupCallActivity.this.applyCallParticipantUpdates(true);
        }

        @Override // org.telegram.p026ui.Components.voip.GroupCallRenderersContainer
        public void onUiVisibilityChanged() {
            if (GroupCallActivity.this.renderersContainer == null) {
                return;
            }
            GroupCallActivity.this.animatorHideButtons.setValue(!GroupCallActivity.this.renderersContainer.isUiVisible(), true);
        }

        @Override // org.telegram.p026ui.Components.voip.GroupCallRenderersContainer
        protected boolean canHideUI() {
            return super.canHideUI() && GroupCallActivity.this.previewDialog == null;
        }

        @Override // org.telegram.p026ui.Components.voip.GroupCallRenderersContainer
        protected void onBackPressed() {
            GroupCallActivity.this.lambda$openCrafting$9();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$29 */
    /* JADX INFO: loaded from: classes6.dex */
    class C556829 extends AvatarPreviewPagerIndicator {
        C556829(final Context activity3) {
            super(activity3);
        }

        @Override // org.telegram.p026ui.AvatarPreviewPagerIndicator, org.telegram.ui.Components.ProfileGalleryView.Callback
        public void onPhotosLoaded() {
            super.onPhotosLoaded();
            long dialogId = GroupCallActivity.this.avatarsViewPager.getDialogId();
            if (dialogId > 0) {
                TLRPC.User user = GroupCallActivity.this.accountInstance.getMessagesController().getUser(Long.valueOf(dialogId));
                GroupCallActivity.this.avatarsViewPager.initIfEmpty(null, ImageLocation.getForUserOrChat(user, 0), ImageLocation.getForUserOrChat(user, 1), false);
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$30 */
    /* JADX INFO: loaded from: classes6.dex */
    class C557030 extends ProfileGalleryView {
        C557030(final Context activity3, ActionBar actionBar, RecyclerListView recyclerListView5, ProfileGalleryView.Callback c5568292) {
            super(activity3, actionBar, recyclerListView5, c5568292);
        }

        @Override // android.view.View
        public void invalidate() {
            super.invalidate();
            ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$31 */
    /* JADX INFO: loaded from: classes6.dex */
    class C557131 extends FrameLayout {
        final Rect rect = new Rect();
        final RectF rectF = new RectF();
        final Path path = new Path();

        C557131(final Context activity3) {
            super(activity3);
            this.rect = new Rect();
            this.rectF = new RectF();
            this.path = new Path();
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i16, int i17) {
            int iMin = Math.min(View.MeasureSpec.getSize(i16), View.MeasureSpec.getSize(i17));
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(iMin, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(iMin + getPaddingBottom(), TLObject.FLAG_30));
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            if (GroupCallActivity.this.progressToAvatarPreview != 1.0f) {
                if (GroupCallActivity.this.scrimView != null && GroupCallActivity.this.hasScrimAnchorView) {
                    canvas.save();
                    float avatarCorners = ExteraConfig.getAvatarCorners(GroupCallActivity.this.scrimView.getAvatarImageView().getMeasuredHeight(), true) * (getMeasuredHeight() / GroupCallActivity.this.scrimView.getAvatarImageView().getMeasuredHeight());
                    int iM1081dp = (int) (((1.0f - GroupCallActivity.this.progressToAvatarPreview) * avatarCorners) + (AndroidUtilities.m1081dp(13.0f) * GroupCallActivity.this.progressToAvatarPreview));
                    int i16 = (int) (avatarCorners * (1.0f - GroupCallActivity.this.progressToAvatarPreview));
                    GroupCallActivity.this.scrimView.getAvatarWavesDrawable().draw(canvas, GroupCallActivity.this.scrimView.getAvatarImageView().getMeasuredHeight() / 2, GroupCallActivity.this.scrimView.getAvatarImageView().getMeasuredHeight() / 2, this);
                    GroupCallActivity.this.scrimView.getAvatarImageView().getImageReceiver().setImageCoords(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight());
                    GroupCallActivity.this.scrimView.getAvatarImageView().setRoundRadius(iM1081dp, iM1081dp, i16, i16);
                    GroupCallActivity.this.scrimView.getAvatarImageView().getImageReceiver().draw(canvas);
                    GroupCallActivity.this.scrimView.getAvatarImageView().setRoundRadius(ExteraConfig.getAvatarCorners(GroupCallActivity.this.scrimView.getAvatarImageView().getMeasuredHeight(), true));
                    canvas.restore();
                } else if (GroupCallActivity.this.scrimFullscreenView != null && GroupCallActivity.this.scrimRenderer == null && GroupCallActivity.this.previewTextureTransitionEnabled) {
                    canvas.save();
                    float avatarCorners2 = ExteraConfig.getAvatarCorners(GroupCallActivity.this.scrimFullscreenView.getAvatarImageView().getMeasuredHeight(), true) * (getMeasuredHeight() / GroupCallActivity.this.scrimFullscreenView.getAvatarImageView().getMeasuredHeight());
                    int iM1081dp2 = (int) (((1.0f - GroupCallActivity.this.progressToAvatarPreview) * avatarCorners2) + (AndroidUtilities.m1081dp(13.0f) * GroupCallActivity.this.progressToAvatarPreview));
                    int i17 = (int) (avatarCorners2 * (1.0f - GroupCallActivity.this.progressToAvatarPreview));
                    GroupCallActivity.this.scrimFullscreenView.getAvatarImageView().getImageReceiver().setImageCoords(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight());
                    GroupCallActivity.this.scrimFullscreenView.getAvatarImageView().setRoundRadius(iM1081dp2, iM1081dp2, i17, i17);
                    GroupCallActivity.this.scrimFullscreenView.getAvatarImageView().getImageReceiver().draw(canvas);
                    GroupCallActivity.this.scrimFullscreenView.getAvatarImageView().setRoundRadius(ExteraConfig.getAvatarCorners(GroupCallActivity.this.scrimFullscreenView.getAvatarImageView().getMeasuredHeight(), true));
                    canvas.restore();
                }
            }
            GroupCallActivity.this.avatarsViewPager.setAlpha(GroupCallActivity.this.progressToAvatarPreview);
            this.path.reset();
            this.rectF.set(0.0f, 0.0f, getMeasuredHeight(), getMeasuredWidth());
            this.path.addRoundRect(this.rectF, new float[]{AndroidUtilities.m1081dp(13.0f), AndroidUtilities.m1081dp(13.0f), AndroidUtilities.m1081dp(13.0f), AndroidUtilities.m1081dp(13.0f), 0.0f, 0.0f, 0.0f, 0.0f}, Path.Direction.CCW);
            canvas.save();
            canvas.clipPath(this.path);
            View viewFindVideoActiveView = GroupCallActivity.this.avatarsViewPager.findVideoActiveView();
            if (viewFindVideoActiveView != null && GroupCallActivity.this.scrimRenderer != null && GroupCallActivity.this.scrimRenderer.isAttached() && !GroupCallActivity.this.drawingForBlur) {
                canvas.save();
                this.rect.setEmpty();
                GroupCallActivity.this.avatarsViewPager.getChildVisibleRect(viewFindVideoActiveView, this.rect, null);
                int measuredWidth = this.rect.left;
                if (measuredWidth < (-GroupCallActivity.this.avatarsViewPager.getMeasuredWidth())) {
                    measuredWidth += GroupCallActivity.this.avatarsViewPager.getMeasuredWidth() * 2;
                } else if (measuredWidth > GroupCallActivity.this.avatarsViewPager.getMeasuredWidth()) {
                    measuredWidth -= GroupCallActivity.this.avatarsViewPager.getMeasuredWidth() * 2;
                }
                canvas.translate(measuredWidth, 0.0f);
                GroupCallActivity.this.scrimRenderer.draw(canvas);
                canvas.restore();
            }
            super.dispatchDraw(canvas);
            canvas.restore();
        }

        @Override // android.view.View
        public void invalidate() {
            super.invalidate();
            ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$32 */
    /* JADX INFO: loaded from: classes6.dex */
    class C557232 implements ViewPager.OnPageChangeListener {
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i16) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i16, float f, int i17) {
        }

        C557232() {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i16) {
            GroupCallActivity.this.avatarsViewPager.getRealPosition(i16);
            GroupCallActivity.this.avatarPagerIndicator.saveCurrentPageProgress();
            GroupCallActivity.this.avatarPagerIndicator.invalidate();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$33 */
    /* JADX INFO: loaded from: classes6.dex */
    class C557333 extends View {
        C557333(final Context activity3) {
            super(activity3);
        }

        @Override // android.view.View
        public void setAlpha(float f) {
            if (getAlpha() != f) {
                super.setAlpha(f);
                GroupCallActivity.this.checkContentOverlayed();
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$34 */
    /* JADX INFO: loaded from: classes6.dex */
    class C557434 implements GroupCallMessagesListView.Delegate {
        C557434() {
        }

        @Override // org.telegram.ui.Components.conference.message.GroupCallMessagesListView.Delegate
        public void showReaction(GroupCallMessageCell groupCallMessageCell, ReactionsLayoutInBubble.VisibleReaction visibleReaction) {
            ReactionsEffectOverlay reactionsEffectOverlay = new ReactionsEffectOverlay(GroupCallActivity.this.getContext(), null, GroupCallActivity.this.reactionsContainerLayout, groupCallMessageCell, null, 0.0f, 0.0f, visibleReaction, ((BottomSheet) GroupCallActivity.this).currentAccount, 1, false);
            ReactionsEffectOverlay.currentOverlay = reactionsEffectOverlay;
            reactionsEffectOverlay.windowView.setTag(C2702R.id.parent_tag, 1);
            GroupCallActivity.this.container.addView(reactionsEffectOverlay.windowView);
            reactionsEffectOverlay.started = true;
            reactionsEffectOverlay.startTime = System.currentTimeMillis();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$35 */
    /* JADX INFO: loaded from: classes6.dex */
    class C557535 implements GroupCallMessageCell.Delegate {
        C557535() {
        }

        @Override // org.telegram.ui.Components.conference.message.GroupCallMessageCell.Delegate
        public void didClickAvatar(GroupCallMessageCell groupCallMessageCell, GroupCallMessage groupCallMessage, float f, float f2) {
            openSenderProfile(groupCallMessage);
        }

        @Override // org.telegram.ui.Components.conference.message.GroupCallMessageCell.Delegate
        public void didClickSenderName(GroupCallMessageCell groupCallMessageCell, GroupCallMessage groupCallMessage) {
            openSenderProfile(groupCallMessage);
        }

        private void openSenderProfile(GroupCallMessage groupCallMessage) {
            BaseFragment lastFragment = LaunchActivity.getLastFragment();
            if (lastFragment == null) {
                return;
            }
            if ((lastFragment instanceof ProfileActivity) && ((ProfileActivity) lastFragment).getDialogId() == groupCallMessage.fromId) {
                GroupCallActivity.this.lambda$new$0();
                return;
            }
            int iCalculateScrollTopOffset = GroupCallActivity.this.calculateScrollTopOffset();
            Bundle bundle = new Bundle();
            long j = groupCallMessage.fromId;
            if (j > 0) {
                bundle.putLong("user_id", j);
            } else {
                bundle.putLong("chat_id", -j);
            }
            boolean z2 = true;
            if (groupCallMessage.fromId == GroupCallActivity.this.accountInstance.getUserConfig().getClientUserId()) {
                bundle.putBoolean("my_profile", true);
            }
            ProfileActivity profileActivity = new ProfileActivity(bundle);
            if (iCalculateScrollTopOffset > 0 && iCalculateScrollTopOffset != Integer.MAX_VALUE) {
                z2 = false;
            }
            lastFragment.presentFragment(profileActivity, false, z2);
            GroupCallActivity.this.lambda$new$0();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$36 */
    /* JADX INFO: loaded from: classes6.dex */
    class C557636 extends EditTextEmoji {
        @Override // org.telegram.p026ui.Components.EditTextEmoji
        protected boolean allowSearch() {
            return true;
        }

        C557636(final Context activity3, SizeNotifierFrameLayout sizeNotifierFrameLayout, BaseFragment baseFragment, int i16, boolean z2, Theme.ResourcesProvider resourcesProvider) {
            super(activity3, sizeNotifierFrameLayout, baseFragment, i16, z2, resourcesProvider);
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i16, int i17) {
            super.onMeasure(i16, i17);
            if (GroupCallActivity.this.animatorMessageInputHeight.getFactor() == 0.0f) {
                GroupCallActivity.this.animatorMessageInputHeight.forceFactor(getMeasuredHeight());
            } else {
                GroupCallActivity.this.animatorMessageInputHeight.animateTo(getMeasuredHeight());
            }
        }

        @Override // android.view.ViewGroup
        protected boolean drawChild(Canvas canvas, View view4, long j) {
            if (view4 == getEditText()) {
                canvas.save();
                GroupCallActivity.this.callMessageEnterView.getEditText().setTranslationY(view4.getMeasuredHeight() - GroupCallActivity.this.animatorMessageInputHeight.getFactor());
                boolean zDrawChild = super.drawChild(canvas, view4, j);
                canvas.restore();
                return zDrawChild;
            }
            return super.drawChild(canvas, view4, j);
        }

        @Override // org.telegram.p026ui.Components.EditTextEmoji
        protected void updatedEmojiExpanded() {
            super.updatedEmojiExpanded();
            ((BottomSheet) GroupCallActivity.this).containerView.requestApplyInsets();
        }

        @Override // org.telegram.p026ui.Components.EditTextEmoji
        protected void onEmojiKeyboardUpdate() {
            int iMax;
            super.onEmojiKeyboardUpdate();
            if (isPopupShowing()) {
                iMax = Math.max(0, getEmojiPadding());
            } else {
                iMax = isWaitingForKeyboardOpen() ? Math.max(0, getKeyboardHeight()) : 0;
            }
            if (iMax > 0) {
                GroupCallActivity.this.windowInsetsStateHolder.requestInAppKeyboardHeight(iMax);
            } else {
                GroupCallActivity.this.windowInsetsStateHolder.resetInAppKeyboardHeight(false);
            }
        }

        @Override // org.telegram.p026ui.Components.EditTextEmoji
        protected void createEmojiView() {
            super.createEmojiView();
            EmojiView emojiView = getEmojiView();
            if (emojiView != null) {
                emojiView.shouldLightenBackground = false;
                emojiView.fixBottomTabContainerTranslation = false;
                emojiView.setShouldDrawBackground(false);
                emojiView.setBottomInset(((BottomSheet) GroupCallActivity.this).containerView.getPaddingBottom());
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$37 */
    /* JADX INFO: loaded from: classes6.dex */
    class C557737 implements TextWatcher {
        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i16, int i17, int i18) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i16, int i17, int i18) {
        }

        C557737() {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            String str2;
            GroupCallActivity.this.animatorMessageIsEmpty.setValue(TextUtils.isEmpty(editable), true);
            int iCodePointCount = Character.codePointCount(editable, 0, editable.length());
            int i16 = GroupCallActivity.this.maxGroupCallMessageLength;
            if (iCodePointCount + 25 > i16) {
                str2 = _UrlKt.FRAGMENT_ENCODE_SET + (i16 - iCodePointCount);
            } else {
                str2 = null;
            }
            GroupCallActivity.this.limitTextView.cancelAnimation();
            GroupCallActivity.this.limitTextView.setText(str2);
            GroupCallActivity.this.limitTextView.setTextColor(iCodePointCount >= i16 ? -1280137 : -1);
            if (iCodePointCount > i16) {
                BotWebViewVibrationEffect.APP_ERROR.vibrate();
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$38 */
    /* JADX INFO: loaded from: classes6.dex */
    class C557838 extends FrameLayout {
        private final RectF tmpRect = new RectF();
        private final RectF tmpRect2 = new RectF();
        private final RectF tmpRect3 = new RectF();
        private final Paint backgroundPaint = new Paint(1);

        C557838(final Context activity3) {
            super(activity3);
            this.tmpRect = new RectF();
            this.tmpRect2 = new RectF();
            this.tmpRect3 = new RectF();
            this.backgroundPaint = new Paint(1);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            this.tmpRect.set(0.0f, (GroupCallActivity.this.callMessageEnterContainer.getY() + GroupCallActivity.this.callMessageEnterContainer.getMeasuredHeight()) - GroupCallActivity.this.animatorMessageInputHeight.getFactor(), getMeasuredWidth(), getMeasuredHeight());
            this.tmpRect2.set(0.0f, GroupCallActivity.this.callMessageEnterContainer.getY() + GroupCallActivity.this.callMessageEnterContainer.getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight());
            this.tmpRect3.set(0.0f, (GroupCallActivity.this.callMessageEnterContainer.getY() + GroupCallActivity.this.callMessageEnterContainer.getMeasuredHeight()) - GroupCallActivity.this.animatorMessageInputHeight.getFactor(), getMeasuredWidth(), GroupCallActivity.this.callMessageEnterContainer.getY() + GroupCallActivity.this.callMessageEnterContainer.getMeasuredHeight());
            if (Build.VERSION.SDK_INT >= 29 && GroupCallActivity.this.renderNodeBlur != null && canvas.isHardwareAccelerated()) {
                this.backgroundPaint.setColor(-14933463);
                canvas.drawRect(this.tmpRect, this.backgroundPaint);
                canvas.save();
                canvas.clipRect(this.tmpRect);
                canvas.translate(-getX(), -getY());
                canvas.scale(GroupCallActivity.this.renderNodeBlurScale, GroupCallActivity.this.renderNodeBlurScale);
                canvas.drawRenderNode(GroupCallActivity.this.renderNodeBlur);
                canvas.restore();
                this.backgroundPaint.setColor(234881023);
                canvas.drawRect(this.tmpRect2, this.backgroundPaint);
            } else {
                this.backgroundPaint.setColor(-14933463);
                canvas.drawRect(this.tmpRect3, this.backgroundPaint);
                this.backgroundPaint.setColor(ColorUtils.compositeColors(234881023, -14933463));
                canvas.drawRect(this.tmpRect2, this.backgroundPaint);
            }
            super.dispatchDraw(canvas);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$39 */
    /* JADX INFO: loaded from: classes6.dex */
    class C557939 extends FrameLayout {
        C557939(final Context activity3) {
            super(activity3);
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            if (getAlpha() <= 0.95f) {
                return false;
            }
            return super.dispatchTouchEvent(motionEvent);
        }
    }

    public /* synthetic */ void lambda$new$39(View view) {
        hideKeyboardOrEmojiView();
    }

    public /* synthetic */ void lambda$new$40(View view) {
        Editable text = this.callMessageEnterView.getText();
        TLRPC.TL_textWithEntities tL_textWithEntities = new TLRPC.TL_textWithEntities();
        tL_textWithEntities.text = text.toString();
        tL_textWithEntities.entities = MediaDataController.getInstance(this.currentAccount).getEntities(new CharSequence[]{text}, true);
        sendGroupCallMessage(tL_textWithEntities);
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$40 */
    /* JADX INFO: loaded from: classes6.dex */
    class C558140 extends NumberPicker {
        C558140(final Context activity3) {
            super(activity3);
        }

        @Override // org.telegram.p026ui.Components.NumberPicker
        protected CharSequence getContentDescription(int i17) {
            return LocaleController.formatPluralString("Hours", i17, new Object[0]);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$41 */
    /* JADX INFO: loaded from: classes6.dex */
    class C558241 extends NumberPicker {
        C558241(final Context activity3) {
            super(activity3);
        }

        @Override // org.telegram.p026ui.Components.NumberPicker
        protected CharSequence getContentDescription(int i17) {
            return LocaleController.formatPluralString("Minutes", i17, new Object[0]);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$42 */
    /* JADX INFO: loaded from: classes6.dex */
    class C558342 extends TextView {
        private final Paint tmpPaint;
        private final RectF tmpRectF = new RectF();

        C558342(final Context activity3) {
            super(activity3);
            this.tmpRectF = new RectF();
            Paint paint5 = new Paint(1);
            this.tmpPaint = paint5;
            paint5.setStyle(Paint.Style.FILL);
            paint5.setColor(-16711936);
        }

        @Override // android.widget.TextView, android.view.View
        protected void onMeasure(int i17, int i18) {
            super.onMeasure(i17, i18);
        }

        @Override // android.view.View
        public void draw(Canvas canvas) {
            super.draw(canvas);
        }

        @Override // android.widget.TextView, android.view.View
        protected void onDraw(Canvas canvas) {
            this.tmpPaint.setColor(-16711936);
            this.tmpRectF.set(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight());
            canvas.drawRoundRect(this.tmpRectF, AndroidUtilities.m1081dp(8.0f), AndroidUtilities.m1081dp(8.0f), this.tmpPaint);
            super.onDraw(canvas);
        }

        @Override // android.view.View
        protected void dispatchDraw(Canvas canvas) {
            this.tmpRectF.set(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight());
            canvas.drawRoundRect(this.tmpRectF, AndroidUtilities.m1081dp(8.0f), AndroidUtilities.m1081dp(8.0f), this.tmpPaint);
            super.dispatchDraw(canvas);
        }
    }

    public /* synthetic */ void lambda$new$45(NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3, final TLRPC.Chat chat, AccountInstance accountInstance, final TLRPC.InputPeer inputPeer, View view) {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.scheduleAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setDuration(600L);
        this.scheduleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda40
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$new$41(valueAnimator);
            }
        });
        this.scheduleAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.GroupCallActivity.43
            C558443() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                GroupCallActivity.this.scheduleAnimator = null;
            }
        });
        this.scheduleAnimator.start();
        if (ChatObject.isChannelOrGiga(this.currentChat)) {
            this.titleTextView.setText(LocaleController.getString(C2702R.string.VoipChannelVoiceChat), true);
        } else {
            this.titleTextView.setText(LocaleController.getString(C2702R.string.VoipGroupVoiceChat), true);
        }
        Calendar calendar = Calendar.getInstance();
        boolean zCheckScheduleDate = AlertsCreator.checkScheduleDate(null, null, 604800L, 3, numberPicker, numberPicker2, numberPicker3);
        calendar.setTimeInMillis(System.currentTimeMillis() + (((long) numberPicker.getValue()) * 86400000));
        calendar.set(11, numberPicker2.getValue());
        calendar.set(12, numberPicker3.getValue());
        if (zCheckScheduleDate) {
            calendar.set(13, 0);
        }
        this.scheduleStartAt = (int) (calendar.getTimeInMillis() / 1000);
        updateScheduleUI(false);
        TL_phone.createGroupCall creategroupcall = new TL_phone.createGroupCall();
        creategroupcall.peer = MessagesController.getInputPeer(chat);
        creategroupcall.random_id = Utilities.random.nextInt();
        creategroupcall.schedule_date = this.scheduleStartAt;
        creategroupcall.flags |= 2;
        accountInstance.getConnectionsManager().sendRequest(creategroupcall, new RequestDelegate() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda41
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$new$44(chat, inputPeer, tLObject, tL_error);
            }
        }, 2);
    }

    public /* synthetic */ void lambda$new$41(ValueAnimator valueAnimator) {
        this.switchToButtonProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        updateScheduleUI(true);
        this.buttonsContainer.invalidate();
        this.listView.invalidate();
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$43 */
    /* JADX INFO: loaded from: classes6.dex */
    class C558443 extends AnimatorListenerAdapter {
        C558443() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            GroupCallActivity.this.scheduleAnimator = null;
        }
    }

    public /* synthetic */ void lambda$new$44(final TLRPC.Chat chat, final TLRPC.InputPeer inputPeer, TLObject tLObject, final TLRPC.TL_error tL_error) {
        if (tLObject != null) {
            TLRPC.Updates updates = (TLRPC.Updates) tLObject;
            int i = 0;
            while (true) {
                if (i >= updates.updates.size()) {
                    break;
                }
                TLRPC.Update update = updates.updates.get(i);
                if (update instanceof TLRPC.TL_updateGroupCall) {
                    final TLRPC.TL_updateGroupCall tL_updateGroupCall = (TLRPC.TL_updateGroupCall) update;
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda57
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$new$42(chat, inputPeer, tL_updateGroupCall);
                        }
                    });
                    break;
                }
                i++;
            }
            this.accountInstance.getMessagesController().processUpdates(updates, false);
            return;
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda58
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$43(tL_error);
            }
        });
    }

    public /* synthetic */ void lambda$new$42(TLRPC.Chat chat, TLRPC.InputPeer inputPeer, TLRPC.TL_updateGroupCall tL_updateGroupCall) {
        ChatObject.Call call = new ChatObject.Call();
        this.call = call;
        call.call = new TLRPC.TL_groupCall();
        ChatObject.Call call2 = this.call;
        TLRPC.GroupCall groupCall = call2.call;
        groupCall.participants_count = 0;
        groupCall.version = 1;
        groupCall.can_start_video = true;
        groupCall.can_change_join_muted = true;
        call2.chatId = chat == null ? 0L : chat.f1610id;
        groupCall.schedule_date = this.scheduleStartAt;
        groupCall.flags |= 128;
        call2.currentAccount = this.accountInstance;
        call2.setSelfPeer(inputPeer);
        ChatObject.Call call3 = this.call;
        TLRPC.GroupCall groupCall2 = call3.call;
        TLRPC.GroupCall groupCall3 = tL_updateGroupCall.call;
        groupCall2.access_hash = groupCall3.access_hash;
        groupCall2.f1625id = groupCall3.f1625id;
        call3.createNoVideoParticipant();
        this.fullscreenAdapter.setGroupCall(this.call);
        this.renderersContainer.setGroupCall(this.call);
        this.tabletGridAdapter.setGroupCall(this.call);
        this.groupCallMessagesListView.setGroupCall(this.accountInstance.getCurrentAccount(), this.call.getInputGroupCall(false));
        MessagesController messagesController = this.accountInstance.getMessagesController();
        ChatObject.Call call4 = this.call;
        messagesController.putGroupCall(call4.chatId, call4);
    }

    public /* synthetic */ void lambda$new$43(TLRPC.TL_error tL_error) {
        this.accountInstance.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.needShowAlert, 6, tL_error.text);
        lambda$new$0();
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$44 */
    /* JADX INFO: loaded from: classes6.dex */
    class C558544 extends LinearLayout {
        boolean ignoreLayout = false;
        final /* synthetic */ NumberPicker val$dayPicker;
        final /* synthetic */ NumberPicker val$hourPicker;
        final /* synthetic */ NumberPicker val$minutePicker;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C558544(final Context activity3, final NumberPicker numberPicker2, final NumberPicker c5581402, final NumberPicker c5582412) {
            super(activity3);
            numberPicker = numberPicker2;
            numberPicker = c5581402;
            numberPicker = c5582412;
            this.ignoreLayout = false;
        }

        @Override // android.widget.LinearLayout, android.view.View
        protected void onMeasure(int i17, int i18) {
            this.ignoreLayout = true;
            numberPicker.setItemCount(5);
            numberPicker.setItemCount(5);
            numberPicker.setItemCount(5);
            numberPicker.getLayoutParams().height = AndroidUtilities.m1081dp(54.0f) * 5;
            numberPicker.getLayoutParams().height = AndroidUtilities.m1081dp(54.0f) * 5;
            numberPicker.getLayoutParams().height = AndroidUtilities.m1081dp(54.0f) * 5;
            this.ignoreLayout = false;
            super.onMeasure(i17, i18);
        }

        @Override // android.view.View, android.view.ViewParent
        public void requestLayout() {
            if (this.ignoreLayout) {
                return;
            }
            super.requestLayout();
        }
    }

    public static /* synthetic */ String $r8$lambda$MzXaaBkHop8z0Fg5s2nMkqxc3uM(long j, Calendar calendar, int i, int i2) {
        if (i2 == 0) {
            return LocaleController.getString(C2702R.string.MessageScheduleToday);
        }
        long j2 = j + (((long) i2) * 86400000);
        calendar.setTimeInMillis(j2);
        if (calendar.get(1) == i) {
            return LocaleController.getInstance().getFormatterWeek().format(j2) + " " + LocaleController.getInstance().getFormatterScheduleDay().format(j2);
        }
        return LocaleController.getInstance().getFormatterScheduleYear().format(j2);
    }

    public /* synthetic */ void lambda$new$47(NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3, NumberPicker numberPicker4, int i, int i2) {
        try {
            this.container.performHapticFeedback(3, 2);
        } catch (Exception unused) {
        }
        AlertsCreator.checkScheduleDate(this.scheduleButtonTextView, this.scheduleInfoTextView, 604800L, 2, numberPicker, numberPicker2, numberPicker3);
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$45 */
    /* JADX INFO: loaded from: classes6.dex */
    class C558645 extends PinchToZoomHelper {
        C558645(ViewGroup viewGroup2, ViewGroup viewGroup3) {
            super(viewGroup2, viewGroup3);
        }

        @Override // org.telegram.p026ui.PinchToZoomHelper
        protected void invalidateViews() {
            super.invalidateViews();
            for (int i22 = 0; i22 < GroupCallActivity.this.avatarsViewPager.getChildCount(); i22++) {
                GroupCallActivity.this.avatarsViewPager.getChildAt(i22).invalidate();
            }
        }

        @Override // org.telegram.p026ui.PinchToZoomHelper
        protected void drawOverlays(Canvas canvas, float f, float f2, float f3, float f4, float f5) {
            if (f > 0.0f) {
                float x = GroupCallActivity.this.avatarPreviewContainer.getX() + ((BottomSheet) GroupCallActivity.this).containerView.getX();
                float y = GroupCallActivity.this.avatarPreviewContainer.getY() + ((BottomSheet) GroupCallActivity.this).containerView.getY();
                RectF rectF = AndroidUtilities.rectTmp;
                rectF.set(x, y, GroupCallActivity.this.avatarsViewPager.getMeasuredWidth() + x, GroupCallActivity.this.avatarsViewPager.getMeasuredHeight() + y);
                canvas.saveLayerAlpha(rectF, (int) (f * 255.0f), 31);
                canvas.translate(x, y);
                GroupCallActivity.this.avatarPreviewContainer.draw(canvas);
                canvas.restore();
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$46 */
    /* JADX INFO: loaded from: classes6.dex */
    class C558746 implements PinchToZoomHelper.Callback {
        @Override // org.telegram.ui.PinchToZoomHelper.Callback
        public /* synthetic */ TextureView getCurrentTextureView() {
            return PinchToZoomHelper.Callback.CC.$default$getCurrentTextureView(this);
        }

        C558746() {
        }

        @Override // org.telegram.ui.PinchToZoomHelper.Callback
        public void onZoomStarted(MessageObject messageObject) {
            GroupCallActivity.this.listView.cancelClickRunnables(true);
            GroupCallActivity.this.pinchToZoomHelper.getPhotoImage().setRoundRadius(AndroidUtilities.m1081dp(13.0f), AndroidUtilities.m1081dp(13.0f), 0, 0);
            ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
        }

        @Override // org.telegram.ui.PinchToZoomHelper.Callback
        public void onZoomFinished(MessageObject messageObject) {
            ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
        }
    }

    public /* synthetic */ void lambda$new$50(Activity activity, View view) {
        LaunchActivity launchActivity = this.parentActivity;
        if (launchActivity != null && launchActivity.checkSelfPermission("android.permission.CAMERA") != 0) {
            this.parentActivity.requestPermissions(new String[]{"android.permission.CAMERA"}, 104);
            return;
        }
        if (VoIPService.getSharedInstance() == null) {
            return;
        }
        if (VoIPService.getSharedInstance().getVideoState(false) != 2) {
            this.undoView[0].hide(false, 1);
            if (this.previewDialog == null) {
                VoIPService sharedInstance = VoIPService.getSharedInstance();
                if (sharedInstance != null) {
                    sharedInstance.createCaptureDevice(false);
                }
                C558847 c558847 = new PrivateVideoPreviewDialog(activity, true, VoIPService.getSharedInstance().getVideoState(true) != 2) { // from class: org.telegram.ui.GroupCallActivity.47
                    C558847(Context activity2, boolean z, boolean z2) {
                        super(activity2, z, z2);
                    }

                    @Override // org.telegram.p026ui.Components.voip.PrivateVideoPreviewDialog
                    public void onDismiss(boolean z, boolean z2) {
                        GroupCallActivity groupCallActivity = GroupCallActivity.this;
                        boolean z3 = groupCallActivity.previewDialog.micEnabled;
                        groupCallActivity.previewDialog = null;
                        VoIPService sharedInstance2 = VoIPService.getSharedInstance();
                        if (!z2) {
                            if (sharedInstance2 != null) {
                                sharedInstance2.setVideoState(false, 0);
                                return;
                            }
                            return;
                        }
                        if (sharedInstance2 != null) {
                            sharedInstance2.setupCaptureDevice(z, z3);
                        }
                        if (z && sharedInstance2 != null) {
                            sharedInstance2.setVideoState(false, 0);
                        }
                        GroupCallActivity.this.updateState(true, false);
                        GroupCallActivity.this.call.sortParticipants();
                        GroupCallActivity.this.applyCallParticipantUpdates(true);
                        GroupCallActivity.this.buttonsContainer.requestLayout();
                    }
                };
                this.previewDialog = c558847;
                c558847.setBottomPadding(this.containerView.getPaddingBottom());
                this.container.addView(this.previewDialog);
                if (sharedInstance == null || sharedInstance.isFrontFaceCamera()) {
                    return;
                }
                sharedInstance.switchCamera();
                return;
            }
            return;
        }
        VoIPService.getSharedInstance().setVideoState(false, 0);
        updateState(true, false);
        updateSpeakerPhoneIcon(false);
        this.call.sortParticipants();
        applyCallParticipantUpdates(true);
        this.buttonsContainer.requestLayout();
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$47 */
    /* JADX INFO: loaded from: classes6.dex */
    class C558847 extends PrivateVideoPreviewDialog {
        C558847(Context activity2, boolean z, boolean z2) {
            super(activity2, z, z2);
        }

        @Override // org.telegram.p026ui.Components.voip.PrivateVideoPreviewDialog
        public void onDismiss(boolean z, boolean z2) {
            GroupCallActivity groupCallActivity = GroupCallActivity.this;
            boolean z3 = groupCallActivity.previewDialog.micEnabled;
            groupCallActivity.previewDialog = null;
            VoIPService sharedInstance2 = VoIPService.getSharedInstance();
            if (!z2) {
                if (sharedInstance2 != null) {
                    sharedInstance2.setVideoState(false, 0);
                    return;
                }
                return;
            }
            if (sharedInstance2 != null) {
                sharedInstance2.setupCaptureDevice(z, z3);
            }
            if (z && sharedInstance2 != null) {
                sharedInstance2.setVideoState(false, 0);
            }
            GroupCallActivity.this.updateState(true, false);
            GroupCallActivity.this.call.sortParticipants();
            GroupCallActivity.this.applyCallParticipantUpdates(true);
            GroupCallActivity.this.buttonsContainer.requestLayout();
        }
    }

    public /* synthetic */ void lambda$new$51(View view) {
        int iCalculateScrollTopOffset = calculateScrollTopOffset();
        if (iCalculateScrollTopOffset > 0 && iCalculateScrollTopOffset != Integer.MAX_VALUE) {
            this.listView.smoothScrollBy(0, iCalculateScrollTopOffset);
        }
        this.callMessageEnterView.openKeyboard();
    }

    public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
        Insets insets2 = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.ime());
        int iMax = Math.max(insets2.bottom, (this.callMessageEnterView.isWaitingForKeyboardOpen() || this.callMessageEnterView.isPopupShowing()) ? this.callMessageEnterView.getKeyboardHeight() : 0);
        ViewGroup.LayoutParams layoutParams = this.blurredView.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = -insets.bottom;
        }
        if (this.callMessageEnterView.getEmojiView() != null) {
            this.callMessageEnterView.getEmojiView().setBottomInset(insets.bottom);
        }
        if (this.isInFullscreen) {
            ViewGroup viewGroup = this.containerView;
            int i = this.backgroundPaddingLeft;
            viewGroup.setPadding(i, 0, i, 0);
        } else {
            ViewGroup viewGroup2 = this.containerView;
            int i2 = this.backgroundPaddingLeft;
            viewGroup2.setPadding(insets.left + i2, insets.top, i2 + insets.right, insets.bottom);
        }
        this.containerView.requestLayout();
        if (iMax == 0 && !this.callMessageEnterView.isWaitingForKeyboardOpen() && !this.callMessageEnterView.isPopupShowing() && !this.callMessageEnterView.isAnimatePopupClosing()) {
            this.callMessageEnterView.hideEmojiView();
        }
        if (iMax > 0) {
            this.reactionsContainerLayout = createReactionsLayout();
        }
        this.callMessageEnterView.onSizeChanged(insets2.bottom, false);
        this.windowInsetsStateHolder.setInsets(windowInsetsCompat);
        return WindowInsetsCompat.CONSUMED;
    }

    public LaunchActivity getParentActivity() {
        return this.parentActivity;
    }

    public void invalidateLayoutFullscreen() {
        int i;
        if (isRtmpStream()) {
            boolean z = (!this.renderersContainer.isUiVisible() && this.renderersContainer.inFullscreenMode && (isLandscapeMode == isRtmpLandscapeMode() || AndroidUtilities.isTablet())) ? false : true;
            Boolean bool = this.wasNotInLayoutFullscreen;
            if (bool == null || z != bool.booleanValue()) {
                int systemUiVisibility = this.containerView.getSystemUiVisibility();
                if (z) {
                    i = systemUiVisibility & (-7);
                    getWindow().clearFlags(1024);
                    setHideSystemVerticalInsets(false);
                } else {
                    setHideSystemVerticalInsets(true);
                    i = systemUiVisibility | 6;
                    getWindow().addFlags(1024);
                }
                this.containerView.setSystemUiVisibility(i);
                this.wasNotInLayoutFullscreen = Boolean.valueOf(z);
                this.isInFullscreen = !z;
                this.containerView.requestApplyInsets();
                return;
            }
            return;
        }
        this.isFullscreen = false;
    }

    public LinearLayout getMenuItemsContainer() {
        return this.menuItemsContainer;
    }

    public void fullscreenFor(ChatObject.VideoParticipant videoParticipant) {
        ChatObject.VideoParticipant videoParticipant2;
        if (videoParticipant == null) {
            this.parentActivity.setRequestedOrientation(-1);
        }
        if (VoIPService.getSharedInstance() == null || this.renderersContainer.isAnimating()) {
            return;
        }
        if (isTabletMode) {
            if (this.requestFullscreenListener != null) {
                this.listView.getViewTreeObserver().removeOnPreDrawListener(this.requestFullscreenListener);
                this.requestFullscreenListener = null;
            }
            final ArrayList arrayList = new ArrayList();
            if (videoParticipant == null) {
                this.attachedRenderersTmp.clear();
                this.attachedRenderersTmp.addAll(this.attachedRenderers);
                for (int i = 0; i < this.attachedRenderersTmp.size(); i++) {
                    GroupCallMiniTextureView groupCallMiniTextureView = (GroupCallMiniTextureView) this.attachedRenderersTmp.get(i);
                    GroupCallGridCell groupCallGridCell = groupCallMiniTextureView.primaryView;
                    if (groupCallGridCell != null) {
                        groupCallGridCell.setRenderer(null);
                        GroupCallFullscreenAdapter.GroupCallUserCell groupCallUserCell = groupCallMiniTextureView.secondaryView;
                        if (groupCallUserCell != null) {
                            groupCallUserCell.setRenderer(null);
                        }
                        GroupCallGridCell groupCallGridCell2 = groupCallMiniTextureView.tabletGridView;
                        if (groupCallGridCell2 != null) {
                            groupCallGridCell2.setRenderer(null);
                        }
                        arrayList.add(groupCallMiniTextureView.participant);
                        groupCallMiniTextureView.forceDetach(false);
                        groupCallMiniTextureView.animate().alpha(0.0f).setListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.GroupCallActivity.48
                            final /* synthetic */ GroupCallMiniTextureView val$miniTextureView;

                            C558948(GroupCallMiniTextureView groupCallMiniTextureView2) {
                                groupCallMiniTextureView = groupCallMiniTextureView2;
                            }

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animator) {
                                if (groupCallMiniTextureView.getParent() != null) {
                                    ((BottomSheet) GroupCallActivity.this).containerView.removeView(groupCallMiniTextureView);
                                }
                            }
                        });
                    }
                }
                this.listViewVideoVisibility = false;
                this.tabletGridAdapter.setVisibility(this.tabletVideoGridView, true, true);
            } else {
                this.attachedRenderersTmp.clear();
                this.attachedRenderersTmp.addAll(this.attachedRenderers);
                for (int i2 = 0; i2 < this.attachedRenderersTmp.size(); i2++) {
                    GroupCallMiniTextureView groupCallMiniTextureView2 = (GroupCallMiniTextureView) this.attachedRenderersTmp.get(i2);
                    if (groupCallMiniTextureView2.tabletGridView != null && ((videoParticipant2 = groupCallMiniTextureView2.participant) == null || !videoParticipant2.equals(videoParticipant))) {
                        arrayList.add(groupCallMiniTextureView2.participant);
                        groupCallMiniTextureView2.forceDetach(false);
                        GroupCallFullscreenAdapter.GroupCallUserCell groupCallUserCell2 = groupCallMiniTextureView2.secondaryView;
                        if (groupCallUserCell2 != null) {
                            groupCallUserCell2.setRenderer(null);
                        }
                        GroupCallGridCell groupCallGridCell3 = groupCallMiniTextureView2.primaryView;
                        if (groupCallGridCell3 != null) {
                            groupCallGridCell3.setRenderer(null);
                        }
                        groupCallMiniTextureView2.animate().alpha(0.0f).setListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.GroupCallActivity.49
                            final /* synthetic */ GroupCallMiniTextureView val$miniTextureView;

                            C559049(GroupCallMiniTextureView groupCallMiniTextureView22) {
                                groupCallMiniTextureView = groupCallMiniTextureView22;
                            }

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animator) {
                                if (groupCallMiniTextureView.getParent() != null) {
                                    ((BottomSheet) GroupCallActivity.this).containerView.removeView(groupCallMiniTextureView);
                                }
                            }
                        });
                    }
                }
                this.listViewVideoVisibility = true;
                this.tabletGridAdapter.setVisibility(this.tabletVideoGridView, false, false);
                if (!arrayList.isEmpty()) {
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$fullscreenFor$52(arrayList);
                        }
                    });
                }
            }
            boolean z = !this.renderersContainer.inFullscreenMode;
            ViewTreeObserver viewTreeObserver = this.listView.getViewTreeObserver();
            ViewTreeObserverOnPreDrawListenerC559250 viewTreeObserverOnPreDrawListenerC559250 = new ViewTreeObserver.OnPreDrawListener() { // from class: org.telegram.ui.GroupCallActivity.50
                final /* synthetic */ boolean val$updateScroll;
                final /* synthetic */ ChatObject.VideoParticipant val$videoParticipant;

                ViewTreeObserverOnPreDrawListenerC559250(ChatObject.VideoParticipant videoParticipant3, boolean z2) {
                    videoParticipant = videoParticipant3;
                    z = z2;
                }

                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    GroupCallActivity.this.listView.getViewTreeObserver().removeOnPreDrawListener(this);
                    GroupCallActivity groupCallActivity = GroupCallActivity.this;
                    groupCallActivity.requestFullscreenListener = null;
                    groupCallActivity.renderersContainer.requestFullscreen(videoParticipant);
                    if (GroupCallActivity.this.delayedGroupCallUpdated) {
                        GroupCallActivity.this.delayedGroupCallUpdated = false;
                        GroupCallActivity.this.applyCallParticipantUpdates(true);
                        if (z && videoParticipant != null) {
                            GroupCallActivity.this.listView.scrollToPosition(0);
                        }
                        GroupCallActivity.this.delayedGroupCallUpdated = true;
                    } else {
                        GroupCallActivity.this.applyCallParticipantUpdates(true);
                    }
                    ((BottomSheet) GroupCallActivity.this).containerView.requestLayout();
                    return false;
                }
            };
            this.requestFullscreenListener = viewTreeObserverOnPreDrawListenerC559250;
            viewTreeObserver.addOnPreDrawListener(viewTreeObserverOnPreDrawListenerC559250);
            return;
        }
        if (this.requestFullscreenListener != null) {
            this.listView.getViewTreeObserver().removeOnPreDrawListener(this.requestFullscreenListener);
            this.requestFullscreenListener = null;
        }
        if (videoParticipant3 != null) {
            if (this.fullscreenUsersListView.getVisibility() != 0) {
                this.fullscreenUsersListView.setVisibility(0);
                this.fullscreenAdapter.update(false, this.fullscreenUsersListView);
                this.delayedGroupCallUpdated = true;
                if (!this.renderersContainer.inFullscreenMode) {
                    this.fullscreenAdapter.scrollTo(videoParticipant3, this.fullscreenUsersListView);
                }
                ViewTreeObserver viewTreeObserver2 = this.listView.getViewTreeObserver();
                ViewTreeObserverOnPreDrawListenerC559351 viewTreeObserverOnPreDrawListenerC559351 = new ViewTreeObserver.OnPreDrawListener() { // from class: org.telegram.ui.GroupCallActivity.51
                    final /* synthetic */ ChatObject.VideoParticipant val$videoParticipant;

                    ViewTreeObserverOnPreDrawListenerC559351(ChatObject.VideoParticipant videoParticipant3) {
                        videoParticipant = videoParticipant3;
                    }

                    @Override // android.view.ViewTreeObserver.OnPreDrawListener
                    public boolean onPreDraw() {
                        GroupCallActivity.this.listView.getViewTreeObserver().removeOnPreDrawListener(this);
                        GroupCallActivity groupCallActivity = GroupCallActivity.this;
                        groupCallActivity.requestFullscreenListener = null;
                        groupCallActivity.renderersContainer.requestFullscreen(videoParticipant);
                        AndroidUtilities.updateVisibleRows(GroupCallActivity.this.fullscreenUsersListView);
                        ((BottomSheet) GroupCallActivity.this).containerView.requestLayout();
                        return false;
                    }
                };
                this.requestFullscreenListener = viewTreeObserverOnPreDrawListenerC559351;
                viewTreeObserver2.addOnPreDrawListener(viewTreeObserverOnPreDrawListenerC559351);
                return;
            }
            this.renderersContainer.requestFullscreen(videoParticipant3);
            AndroidUtilities.updateVisibleRows(this.fullscreenUsersListView);
            return;
        }
        if (this.listView.getVisibility() != 0) {
            this.listView.setVisibility(0);
            applyCallParticipantUpdates(false);
            this.delayedGroupCallUpdated = true;
            ViewTreeObserver viewTreeObserver3 = this.listView.getViewTreeObserver();
            ViewTreeObserverOnPreDrawListenerC559452 viewTreeObserverOnPreDrawListenerC559452 = new ViewTreeObserver.OnPreDrawListener() { // from class: org.telegram.ui.GroupCallActivity.52
                ViewTreeObserverOnPreDrawListenerC559452() {
                }

                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    GroupCallActivity.this.listView.getViewTreeObserver().removeOnPreDrawListener(this);
                    GroupCallActivity.this.renderersContainer.requestFullscreen(null);
                    AndroidUtilities.updateVisibleRows(GroupCallActivity.this.fullscreenUsersListView);
                    ((BottomSheet) GroupCallActivity.this).containerView.requestLayout();
                    return false;
                }
            };
            this.requestFullscreenListener = viewTreeObserverOnPreDrawListenerC559452;
            viewTreeObserver3.addOnPreDrawListener(viewTreeObserverOnPreDrawListenerC559452);
            return;
        }
        ViewTreeObserver viewTreeObserver4 = this.listView.getViewTreeObserver();
        ViewTreeObserverOnPreDrawListenerC559553 viewTreeObserverOnPreDrawListenerC559553 = new ViewTreeObserver.OnPreDrawListener() { // from class: org.telegram.ui.GroupCallActivity.53
            ViewTreeObserverOnPreDrawListenerC559553() {
            }

            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                GroupCallActivity.this.listView.getViewTreeObserver().removeOnPreDrawListener(this);
                GroupCallActivity.this.renderersContainer.requestFullscreen(null);
                AndroidUtilities.updateVisibleRows(GroupCallActivity.this.fullscreenUsersListView);
                ((BottomSheet) GroupCallActivity.this).containerView.requestLayout();
                return false;
            }
        };
        this.requestFullscreenListener = viewTreeObserverOnPreDrawListenerC559553;
        viewTreeObserver4.addOnPreDrawListener(viewTreeObserverOnPreDrawListenerC559553);
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$48 */
    /* JADX INFO: loaded from: classes6.dex */
    class C558948 extends AnimatorListenerAdapter {
        final /* synthetic */ GroupCallMiniTextureView val$miniTextureView;

        C558948(GroupCallMiniTextureView groupCallMiniTextureView2) {
            groupCallMiniTextureView = groupCallMiniTextureView2;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (groupCallMiniTextureView.getParent() != null) {
                ((BottomSheet) GroupCallActivity.this).containerView.removeView(groupCallMiniTextureView);
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$49 */
    /* JADX INFO: loaded from: classes6.dex */
    class C559049 extends AnimatorListenerAdapter {
        final /* synthetic */ GroupCallMiniTextureView val$miniTextureView;

        C559049(GroupCallMiniTextureView groupCallMiniTextureView22) {
            groupCallMiniTextureView = groupCallMiniTextureView22;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (groupCallMiniTextureView.getParent() != null) {
                ((BottomSheet) GroupCallActivity.this).containerView.removeView(groupCallMiniTextureView);
            }
        }
    }

    public /* synthetic */ void lambda$fullscreenFor$52(ArrayList arrayList) {
        for (int i = 0; i < this.attachedRenderers.size(); i++) {
            if (((GroupCallMiniTextureView) this.attachedRenderers.get(i)).participant != null) {
                arrayList.remove(((GroupCallMiniTextureView) this.attachedRenderers.get(i)).participant);
            }
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            ChatObject.VideoParticipant videoParticipant = (ChatObject.VideoParticipant) arrayList.get(i2);
            if (videoParticipant.participant.self) {
                if (VoIPService.getSharedInstance() != null) {
                    VoIPService.getSharedInstance().setLocalSink(null, videoParticipant.presentation);
                }
            } else if (VoIPService.getSharedInstance() != null) {
                VoIPService.getSharedInstance().removeRemoteSink(videoParticipant.participant, videoParticipant.presentation);
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$50 */
    /* JADX INFO: loaded from: classes6.dex */
    class ViewTreeObserverOnPreDrawListenerC559250 implements ViewTreeObserver.OnPreDrawListener {
        final /* synthetic */ boolean val$updateScroll;
        final /* synthetic */ ChatObject.VideoParticipant val$videoParticipant;

        ViewTreeObserverOnPreDrawListenerC559250(ChatObject.VideoParticipant videoParticipant3, boolean z2) {
            videoParticipant = videoParticipant3;
            z = z2;
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            GroupCallActivity.this.listView.getViewTreeObserver().removeOnPreDrawListener(this);
            GroupCallActivity groupCallActivity = GroupCallActivity.this;
            groupCallActivity.requestFullscreenListener = null;
            groupCallActivity.renderersContainer.requestFullscreen(videoParticipant);
            if (GroupCallActivity.this.delayedGroupCallUpdated) {
                GroupCallActivity.this.delayedGroupCallUpdated = false;
                GroupCallActivity.this.applyCallParticipantUpdates(true);
                if (z && videoParticipant != null) {
                    GroupCallActivity.this.listView.scrollToPosition(0);
                }
                GroupCallActivity.this.delayedGroupCallUpdated = true;
            } else {
                GroupCallActivity.this.applyCallParticipantUpdates(true);
            }
            ((BottomSheet) GroupCallActivity.this).containerView.requestLayout();
            return false;
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$51 */
    /* JADX INFO: loaded from: classes6.dex */
    class ViewTreeObserverOnPreDrawListenerC559351 implements ViewTreeObserver.OnPreDrawListener {
        final /* synthetic */ ChatObject.VideoParticipant val$videoParticipant;

        ViewTreeObserverOnPreDrawListenerC559351(ChatObject.VideoParticipant videoParticipant3) {
            videoParticipant = videoParticipant3;
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            GroupCallActivity.this.listView.getViewTreeObserver().removeOnPreDrawListener(this);
            GroupCallActivity groupCallActivity = GroupCallActivity.this;
            groupCallActivity.requestFullscreenListener = null;
            groupCallActivity.renderersContainer.requestFullscreen(videoParticipant);
            AndroidUtilities.updateVisibleRows(GroupCallActivity.this.fullscreenUsersListView);
            ((BottomSheet) GroupCallActivity.this).containerView.requestLayout();
            return false;
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$52 */
    /* JADX INFO: loaded from: classes6.dex */
    class ViewTreeObserverOnPreDrawListenerC559452 implements ViewTreeObserver.OnPreDrawListener {
        ViewTreeObserverOnPreDrawListenerC559452() {
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            GroupCallActivity.this.listView.getViewTreeObserver().removeOnPreDrawListener(this);
            GroupCallActivity.this.renderersContainer.requestFullscreen(null);
            AndroidUtilities.updateVisibleRows(GroupCallActivity.this.fullscreenUsersListView);
            ((BottomSheet) GroupCallActivity.this).containerView.requestLayout();
            return false;
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$53 */
    /* JADX INFO: loaded from: classes6.dex */
    class ViewTreeObserverOnPreDrawListenerC559553 implements ViewTreeObserver.OnPreDrawListener {
        ViewTreeObserverOnPreDrawListenerC559553() {
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            GroupCallActivity.this.listView.getViewTreeObserver().removeOnPreDrawListener(this);
            GroupCallActivity.this.renderersContainer.requestFullscreen(null);
            AndroidUtilities.updateVisibleRows(GroupCallActivity.this.fullscreenUsersListView);
            ((BottomSheet) GroupCallActivity.this).containerView.requestLayout();
            return false;
        }
    }

    public void enableCamera() {
        this.cameraButton.callOnClick();
    }

    public void checkContentOverlayed() {
        boolean z = !this.avatarPriviewTransitionInProgress && this.blurredView.getVisibility() == 0 && this.blurredView.getAlpha() == 1.0f;
        if (this.contentFullyOverlayed != z) {
            this.contentFullyOverlayed = z;
            this.buttonsContainer.invalidate();
            this.containerView.invalidate();
            this.listView.invalidate();
        }
    }

    private void updateScheduleUI(boolean z) {
        float interpolation;
        float f;
        LinearLayout linearLayout = this.scheduleTimerContainer;
        if ((linearLayout == null || this.call != null) && this.scheduleAnimator == null) {
            this.switchToButtonInt2 = 1.0f;
            this.switchToButtonProgress = 1.0f;
            if (linearLayout == null) {
                return;
            }
        }
        if (!z) {
            AndroidUtilities.cancelRunOnUIThread(this.updateSchedeulRunnable);
            this.updateSchedeulRunnable.run();
            ChatObject.Call call = this.call;
            if (call == null || call.isScheduled()) {
                this.listView.setVisibility(4);
            } else {
                this.listView.setVisibility(0);
            }
            if (ChatObject.isChannelOrGiga(this.currentChat)) {
                this.leaveItem.setText(LocaleController.getString(C2702R.string.VoipChannelCancelChat));
            } else {
                this.leaveItem.setText(LocaleController.getString(C2702R.string.VoipGroupCancelChat));
            }
        }
        float f2 = this.switchToButtonProgress;
        if (f2 > 0.6f) {
            interpolation = 1.05f - (CubicBezierInterpolator.DEFAULT.getInterpolation((f2 - 0.6f) / 0.4f) * 0.05f);
            this.switchToButtonInt2 = 1.0f;
            f = 1.0f;
        } else {
            CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.DEFAULT;
            this.switchToButtonInt2 = cubicBezierInterpolator.getInterpolation(f2 / 0.6f);
            interpolation = 1.05f * cubicBezierInterpolator.getInterpolation(this.switchToButtonProgress / 0.6f);
            f = this.switchToButtonProgress / 0.6f;
        }
        updateButtonsVisibility(true);
        float f3 = 1.0f - f;
        this.scheduleTimerContainer.setAlpha(f3);
        this.scheduleStartInTextView.setAlpha(f);
        this.scheduleStartAtTextView.setAlpha(f);
        this.scheduleTimeTextView.setAlpha(f);
        this.scheduleTimeTextView.setScaleX(interpolation);
        this.scheduleTimeTextView.setScaleY(interpolation);
        this.scheduleButtonTextView.setScaleX(f3);
        this.scheduleButtonTextView.setScaleY(f3);
        this.scheduleButtonTextView.setAlpha(f3);
        this.scheduleInfoTextView.setAlpha(f3);
        this.otherItem.setAlpha(f);
        int i = f3 != 0.0f ? 0 : 4;
        if (i != this.scheduleTimerContainer.getVisibility()) {
            this.scheduleTimerContainer.setVisibility(i);
            this.scheduleButtonTextView.setVisibility(i);
        }
    }

    private void initCreatedGroupCall() {
        VoIPService sharedInstance;
        if (this.callInitied || (sharedInstance = VoIPService.getSharedInstance()) == null) {
            return;
        }
        this.callInitied = true;
        this.oldParticipants.addAll(this.call.visibleParticipants);
        this.oldVideoParticipants.addAll(this.visibleVideoParticipants);
        this.oldInvited.addAll(this.call.invitedUsers);
        this.oldShadyJoin.addAll(this.call.shadyJoinParticipants);
        this.oldShadyLeft.addAll(this.call.shadyLeftParticipants);
        this.currentCallState = sharedInstance.getCallState();
        if (this.call == null) {
            ChatObject.Call call = sharedInstance.groupCall;
            this.call = call;
            this.fullscreenAdapter.setGroupCall(call);
            this.renderersContainer.setGroupCall(this.call);
            this.tabletGridAdapter.setGroupCall(this.call);
        }
        GroupCallMessagesListView groupCallMessagesListView = this.groupCallMessagesListView;
        if (groupCallMessagesListView != null) {
            groupCallMessagesListView.setGroupCall(this.accountInstance.getCurrentAccount(), this.call.getInputGroupCall(false));
        }
        this.actionBar.setTitleRightMargin(AndroidUtilities.m1081dp(48.0f) * 2);
        this.call.saveActiveDates();
        VoIPService.getSharedInstance().registerStateListener(this);
        SimpleTextView simpleTextView = this.scheduleTimeTextView;
        if (simpleTextView == null || simpleTextView.getVisibility() != 0) {
            return;
        }
        this.leaveButton.setData(C2702R.drawable.calls_decline, -1, Theme.getColor(Theme.key_voipgroup_leaveButton), 0.3f, false, LocaleController.getString(C2702R.string.VoipGroupLeave), false, true);
        updateSpeakerPhoneIcon(true);
        this.leaveItem.setText(LocaleController.getString(ChatObject.isChannelOrGiga(this.currentChat) ? C2702R.string.VoipChannelEndChat : C2702R.string.VoipGroupEndChat));
        this.listView.setVisibility(0);
        this.pipItem.setVisibility(0);
        AnimatorSet animatorSet = new AnimatorSet();
        RecyclerListView recyclerListView = this.listView;
        Property property = View.ALPHA;
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(recyclerListView, (Property<RecyclerListView, Float>) property, 0.0f, 1.0f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.listView, (Property<RecyclerListView, Float>) View.TRANSLATION_Y, AndroidUtilities.m1081dp(200.0f), 0.0f);
        SimpleTextView simpleTextView2 = this.scheduleTimeTextView;
        Property property2 = View.SCALE_X;
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(simpleTextView2, (Property<SimpleTextView, Float>) property2, 0.0f);
        SimpleTextView simpleTextView3 = this.scheduleTimeTextView;
        Property property3 = View.SCALE_Y;
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2, objectAnimatorOfFloat3, ObjectAnimator.ofFloat(simpleTextView3, (Property<SimpleTextView, Float>) property3, 0.0f), ObjectAnimator.ofFloat(this.scheduleTimeTextView, (Property<SimpleTextView, Float>) property, 0.0f), ObjectAnimator.ofFloat(this.scheduleStartInTextView, (Property<SimpleTextView, Float>) property2, 0.0f), ObjectAnimator.ofFloat(this.scheduleStartInTextView, (Property<SimpleTextView, Float>) property3, 0.0f), ObjectAnimator.ofFloat(this.scheduleStartInTextView, (Property<SimpleTextView, Float>) property, 0.0f), ObjectAnimator.ofFloat(this.scheduleStartAtTextView, (Property<SimpleTextView, Float>) property2, 0.0f), ObjectAnimator.ofFloat(this.scheduleStartAtTextView, (Property<SimpleTextView, Float>) property3, 0.0f), ObjectAnimator.ofFloat(this.scheduleStartAtTextView, (Property<SimpleTextView, Float>) property, 0.0f), ObjectAnimator.ofFloat(this.pipItem, (Property<ActionBarMenuItem, Float>) property2, 0.0f, 1.0f), ObjectAnimator.ofFloat(this.pipItem, (Property<ActionBarMenuItem, Float>) property3, 0.0f, 1.0f), ObjectAnimator.ofFloat(this.pipItem, (Property<ActionBarMenuItem, Float>) property, 0.0f, 1.0f));
        animatorSet.setInterpolator(CubicBezierInterpolator.EASE_OUT);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.GroupCallActivity.54
            C559654() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                GroupCallActivity.this.scheduleTimeTextView.setVisibility(4);
                GroupCallActivity.this.scheduleStartAtTextView.setVisibility(4);
                GroupCallActivity.this.scheduleStartInTextView.setVisibility(4);
            }
        });
        animatorSet.setDuration(300L);
        animatorSet.start();
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$54 */
    /* JADX INFO: loaded from: classes6.dex */
    class C559654 extends AnimatorListenerAdapter {
        C559654() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            GroupCallActivity.this.scheduleTimeTextView.setVisibility(4);
            GroupCallActivity.this.scheduleStartAtTextView.setVisibility(4);
            GroupCallActivity.this.scheduleStartInTextView.setVisibility(4);
        }
    }

    public void updateSubtitle() {
        boolean z;
        WatchersView watchersView;
        if (this.actionBar == null || this.call == null) {
            return;
        }
        SpannableStringBuilder spannableStringBuilder = null;
        int i = 0;
        for (int i2 = 0; i2 < this.call.currentSpeakingPeers.size(); i2++) {
            long jKeyAt = this.call.currentSpeakingPeers.keyAt(i2);
            TLRPC.GroupCallParticipant groupCallParticipant = (TLRPC.GroupCallParticipant) this.call.currentSpeakingPeers.get(jKeyAt);
            if (!groupCallParticipant.self && !this.renderersContainer.isVisible(groupCallParticipant) && this.visiblePeerIds.get(jKeyAt, 0) != 1) {
                long peerId = MessageObject.getPeerId(groupCallParticipant.peer);
                if (spannableStringBuilder == null) {
                    spannableStringBuilder = new SpannableStringBuilder();
                }
                if (i < 2) {
                    TLRPC.User user = peerId > 0 ? MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(peerId)) : null;
                    TLRPC.Chat chat = peerId <= 0 ? MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(peerId)) : null;
                    if (user != null || chat != null) {
                        if (i != 0) {
                            spannableStringBuilder.append((CharSequence) ", ");
                        }
                        if (user != null) {
                            spannableStringBuilder.append(UserObject.getFirstName(user), new TypefaceSpan(AndroidUtilities.bold()), 0);
                        } else {
                            spannableStringBuilder.append(chat.title, new TypefaceSpan(AndroidUtilities.bold()), 0);
                        }
                    }
                }
                i++;
                if (i == 2) {
                    break;
                }
            }
        }
        if (i > 0) {
            String pluralString = LocaleController.getPluralString("MembersAreSpeakingToast", i);
            int iIndexOf = pluralString.indexOf("un1");
            SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(pluralString);
            spannableStringBuilder2.replace(iIndexOf, iIndexOf + 3, (CharSequence) spannableStringBuilder);
            this.actionBar.getAdditionalSubtitleTextView().setText(spannableStringBuilder2);
            z = true;
        } else {
            z = false;
        }
        this.actionBar.getSubtitleTextView().setText(LocaleController.formatPluralString(isRtmpStream() ? "ViewersWatching" : "Participants", this.call.call.participants_count + (this.listAdapter.addSelfToCounter() ? 1 : 0), new Object[0]));
        if (isRtmpStream() && (watchersView = this.watchersView) != null) {
            watchersView.setWatchersCount(this.call.call.participants_count);
        }
        if (z != this.drawSpeakingSubtitle) {
            this.drawSpeakingSubtitle = z;
            this.actionBar.invalidate();
            this.actionBar.getSubtitleTextView().setPivotX(0.0f);
            this.actionBar.getSubtitleTextView().setPivotY(this.actionBar.getMeasuredHeight() >> 1);
            this.actionBar.getSubtitleTextView().animate().scaleX(this.drawSpeakingSubtitle ? 0.98f : 1.0f).scaleY(this.drawSpeakingSubtitle ? 0.9f : 1.0f).alpha(this.drawSpeakingSubtitle ? 0.0f : 1.0f).setDuration(150L);
            AndroidUtilities.updateViewVisibilityAnimated(this.actionBar.getAdditionalSubtitleTextView(), this.drawSpeakingSubtitle);
        }
    }

    @Override // org.telegram.p026ui.ActionBar.BottomSheet, android.app.Dialog
    public void show() {
        super.show();
        if (RTMPStreamPipOverlay.isVisible()) {
            RTMPStreamPipOverlay.dismiss();
        }
    }

    @Override // org.telegram.p026ui.ActionBar.BottomSheet
    public void dismissInternal() {
        if (this.renderersContainer != null) {
            if (this.requestFullscreenListener != null) {
                this.listView.getViewTreeObserver().removeOnPreDrawListener(this.requestFullscreenListener);
                this.requestFullscreenListener = null;
            }
            this.attachedRenderersTmp.clear();
            this.attachedRenderersTmp.addAll(this.attachedRenderers);
            for (int i = 0; i < this.attachedRenderersTmp.size(); i++) {
                ((GroupCallMiniTextureView) this.attachedRenderersTmp.get(i)).saveThumb();
                this.renderersContainer.removeView((View) this.attachedRenderersTmp.get(i));
                ((GroupCallMiniTextureView) this.attachedRenderersTmp.get(i)).release();
                ((GroupCallMiniTextureView) this.attachedRenderersTmp.get(i)).forceDetach(true);
            }
            this.attachedRenderers.clear();
            if (this.renderersContainer.getParent() != null) {
                this.attachedRenderers.clear();
                this.containerView.removeView(this.renderersContainer);
            }
        }
        super.dismissInternal();
        if (VoIPService.getSharedInstance() != null) {
            VoIPService.getSharedInstance().unregisterStateListener(this);
            VoIPService.getSharedInstance().setSinks(null, null);
        }
        if (groupCallInstance == this) {
            groupCallInstance = null;
        }
        groupCallUiVisible = false;
        VoIPService.audioLevelsCallback = null;
        GroupCallPip.updateVisibility(getContext());
        ChatObject.Call call = this.call;
        if (call != null) {
            call.clearVideFramesInfo();
        }
        if (VoIPService.getSharedInstance() != null) {
            VoIPService.getSharedInstance().clearRemoteSinks();
        }
        PipSource pipSource = this.pipSource;
        if (pipSource != null) {
            pipSource.destroy();
            this.pipSource = null;
        }
    }

    private void setAmplitude(double d) {
        float fMin = (float) (Math.min(8500.0d, d) / 8500.0d);
        this.animateToAmplitude = fMin;
        this.animateAmplitudeDiff = (fMin - this.amplitude) / ((BlobDrawable.AMPLITUDE_SPEED * 500.0f) + 100.0f);
    }

    @Override // org.telegram.messenger.voip.VoIPService.StateListener
    public void onStateChanged(int i) {
        this.currentCallState = i;
        updateState(isShowing(), false);
    }

    public UndoView getUndoView() {
        if (!isTabletMode) {
            GroupCallRenderersContainer groupCallRenderersContainer = this.renderersContainer;
            if (groupCallRenderersContainer.inFullscreenMode) {
                return groupCallRenderersContainer.getUndoView();
            }
        }
        if (this.undoView[0].getVisibility() == 0) {
            UndoView[] undoViewArr = this.undoView;
            UndoView undoView = undoViewArr[0];
            undoViewArr[0] = undoViewArr[1];
            undoViewArr[1] = undoView;
            undoView.hide(true, 2);
            this.containerView.removeView(this.undoView[0]);
            this.containerView.addView(this.undoView[0]);
        }
        return this.undoView[0];
    }

    private void updateTitle(boolean z) {
        ChatObject.Call call = this.call;
        if (call == null) {
            if (ChatObject.isChannelOrGiga(this.currentChat)) {
                this.titleTextView.setText(LocaleController.getString(C2702R.string.VoipChannelScheduleVoiceChat), z);
                return;
            } else {
                this.titleTextView.setText(LocaleController.getString(C2702R.string.VoipGroupScheduleVoiceChat), z);
                return;
            }
        }
        if (!TextUtils.isEmpty(call.call.title)) {
            if (!this.call.call.title.equals(this.actionBar.getTitle())) {
                if (z) {
                    this.actionBar.setTitleAnimated(this.call.call.title, true, 180L);
                    this.actionBar.getTitleTextView().setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda46
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f$0.lambda$updateTitle$53(view);
                        }
                    });
                } else {
                    this.actionBar.setTitle(this.call.call.title);
                }
                this.titleTextView.setText(this.call.call.title, z);
            }
        } else {
            TLRPC.Chat chat = this.currentChat;
            if (chat != null && !chat.title.equals(this.actionBar.getTitle())) {
                if (z) {
                    this.actionBar.setTitleAnimated(this.currentChat.title, true, 180L);
                    this.actionBar.getTitleTextView().setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda47
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f$0.lambda$updateTitle$54(view);
                        }
                    });
                } else {
                    this.actionBar.setTitle(this.currentChat.title);
                }
                if (ChatObject.isChannelOrGiga(this.currentChat)) {
                    if (isRtmpStream()) {
                        this.titleTextView.setText(this.currentChat.title, z);
                    } else {
                        this.titleTextView.setText(LocaleController.getString(C2702R.string.VoipChannelVoiceChat), z);
                    }
                } else {
                    this.titleTextView.setText(LocaleController.getString(C2702R.string.VoipGroupVoiceChat), z);
                }
            } else if (this.currentChat == null) {
                this.actionBar.setTitle(LocaleController.getString(C2702R.string.ConferenceChat));
                this.titleTextView.setText(LocaleController.getString(C2702R.string.ConferenceChat), z);
            }
        }
        SimpleTextView titleTextView = this.actionBar.getTitleTextView();
        if (this.call.recording) {
            if (titleTextView.getRightDrawable() == null) {
                titleTextView.setRightDrawable(new SmallRecordCallDrawable(titleTextView));
                TextView textView = this.titleTextView.getTextView();
                textView.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, new SmallRecordCallDrawable(textView), (Drawable) null);
                TextView nextTextView = this.titleTextView.getNextTextView();
                nextTextView.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, new SmallRecordCallDrawable(nextTextView), (Drawable) null);
                return;
            }
            return;
        }
        if (titleTextView.getRightDrawable() != null) {
            titleTextView.setRightDrawable((Drawable) null);
            this.titleTextView.getTextView().setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
            this.titleTextView.getNextTextView().setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
        }
    }

    public /* synthetic */ void lambda$updateTitle$53(View view) {
        ChatObject.Call call = this.call;
        if (call == null || !call.recording) {
            return;
        }
        showRecordHint(this.actionBar.getTitleTextView());
    }

    public /* synthetic */ void lambda$updateTitle$54(View view) {
        ChatObject.Call call = this.call;
        if (call == null || !call.recording) {
            return;
        }
        showRecordHint(this.actionBar.getTitleTextView());
    }

    public void setColorProgress(float f) {
        this.colorProgress = f;
        GroupCallRenderersContainer groupCallRenderersContainer = this.renderersContainer;
        float fMax = Math.max(f, groupCallRenderersContainer == null ? 0.0f : groupCallRenderersContainer.progressToFullscreenMode);
        int i = Theme.key_voipgroup_actionBarUnscrolled;
        int color = Theme.getColor(i);
        int i2 = Theme.key_voipgroup_actionBar;
        int offsetColor = AndroidUtilities.getOffsetColor(color, Theme.getColor(i2), f, 1.0f);
        this.backgroundColor = offsetColor;
        this.actionBarBackground.setBackgroundColor(offsetColor);
        this.otherItem.redrawPopup(-14472653);
        this.shadowDrawable.setColorFilter(new PorterDuffColorFilter(this.backgroundColor, PorterDuff.Mode.MULTIPLY));
        this.navBarColor = AndroidUtilities.getOffsetColor(Theme.getColor(i), Theme.getColor(i2), fMax, 1.0f);
        int offsetColor2 = AndroidUtilities.getOffsetColor(Theme.getColor(Theme.key_voipgroup_listViewBackgroundUnscrolled), Theme.getColor(Theme.key_voipgroup_listViewBackground), f, 1.0f);
        CallEncryptionCellDrawable callEncryptionCellDrawable = this.encryptionDrawable;
        if (callEncryptionCellDrawable != null) {
            callEncryptionCellDrawable.setPaintBackgroundColor(offsetColor2);
        }
        this.listViewBackgroundPaint.setColor(offsetColor2);
        this.callMessageEnterContainerBgPaint.setColor(offsetColor2);
        this.callMessageEnterContainer.invalidate();
        this.listView.setGlowColor(offsetColor2);
        int i3 = this.muteButtonState;
        if (i3 == 3 || isGradientState(i3)) {
            this.muteButton.invalidate();
        }
        View view = this.buttonsBackgroundGradientView;
        if (view != null) {
            int[] iArr = this.gradientColors;
            iArr[0] = this.backgroundColor;
            iArr[1] = 0;
            if (Build.VERSION.SDK_INT > 29) {
                this.buttonsBackgroundGradient.setColors(iArr);
            } else {
                GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, this.gradientColors);
                this.buttonsBackgroundGradient = gradientDrawable;
                view.setBackground(gradientDrawable);
            }
            this.buttonsBackgroundGradientView2.setBackgroundColor(this.gradientColors[0]);
        }
        int offsetColor3 = AndroidUtilities.getOffsetColor(Theme.getColor(Theme.key_voipgroup_leaveButton), Theme.getColor(Theme.key_voipgroup_leaveButtonScrolled), f, 1.0f);
        this.leaveButton.setBackgroundColor(offsetColor3, offsetColor3);
        int offsetColor4 = AndroidUtilities.getOffsetColor(Theme.getColor(Theme.key_voipgroup_lastSeenTextUnscrolled), Theme.getColor(Theme.key_voipgroup_lastSeenText), f, 1.0f);
        int offsetColor5 = AndroidUtilities.getOffsetColor(Theme.getColor(Theme.key_voipgroup_mutedIconUnscrolled), Theme.getColor(Theme.key_voipgroup_mutedIcon), f, 1.0f);
        int color2 = Theme.getColor(Theme.key_featuredStickers_addButton, this.resourcesProvider);
        int childCount = this.listView.getChildCount();
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = this.listView.getChildAt(i4);
            if (childAt instanceof GroupCallTextCell) {
                GroupCallTextCell groupCallTextCell = (GroupCallTextCell) childAt;
                if (isConference()) {
                    groupCallTextCell.setColors(color2, color2);
                } else {
                    groupCallTextCell.setColors(offsetColor5, offsetColor4);
                }
            } else if (childAt instanceof GroupCallUserCell) {
                ((GroupCallUserCell) childAt).setGrayIconColor(this.actionBar.getTag() != null ? Theme.key_voipgroup_mutedIcon : Theme.key_voipgroup_mutedIconUnscrolled, offsetColor5);
            } else if (childAt instanceof GroupCallInvitedCell) {
                ((GroupCallInvitedCell) childAt).setGrayIconColor(this.actionBar.getTag() != null ? Theme.key_voipgroup_mutedIcon : Theme.key_voipgroup_mutedIconUnscrolled, offsetColor5);
            }
        }
        this.containerView.invalidate();
        this.listView.invalidate();
        this.container.invalidate();
    }

    public void getLink(final boolean z) {
        String str;
        TLRPC.TL_chatInviteExported tL_chatInviteExported;
        TLRPC.Chat chat = this.accountInstance.getMessagesController().getChat(Long.valueOf(getChatId()));
        if (chat != null && !ChatObject.isPublic(chat)) {
            final TLRPC.ChatFull chatFull = this.accountInstance.getMessagesController().getChatFull(getChatId());
            String publicUsername = ChatObject.getPublicUsername(this.currentChat);
            if (TextUtils.isEmpty(publicUsername)) {
                str = (chatFull == null || (tL_chatInviteExported = chatFull.exported_invite) == null) ? null : tL_chatInviteExported.link;
            } else {
                str = this.accountInstance.getMessagesController().linkPrefix + "/" + publicUsername;
            }
            if (TextUtils.isEmpty(str)) {
                TLRPC.TL_messages_exportChatInvite tL_messages_exportChatInvite = new TLRPC.TL_messages_exportChatInvite();
                tL_messages_exportChatInvite.peer = MessagesController.getInputPeer(this.currentChat);
                this.accountInstance.getConnectionsManager().sendRequest(tL_messages_exportChatInvite, new RequestDelegate() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda38
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                        this.f$0.lambda$getLink$56(chatFull, z, tLObject, tL_error);
                    }
                });
                return;
            }
            openShareAlert(true, null, str, z);
            return;
        }
        if (this.call == null) {
            return;
        }
        final int i = 0;
        while (i < 2) {
            TL_phone.exportGroupCallInvite exportgroupcallinvite = new TL_phone.exportGroupCallInvite();
            exportgroupcallinvite.call = this.call.getInputGroupCall();
            exportgroupcallinvite.can_self_unmute = i == 1;
            this.accountInstance.getConnectionsManager().sendRequest(exportgroupcallinvite, new RequestDelegate() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda39
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    this.f$0.lambda$getLink$58(i, z, tLObject, tL_error);
                }
            });
            i++;
        }
    }

    public /* synthetic */ void lambda$getLink$56(final TLRPC.ChatFull chatFull, final boolean z, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda52
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$getLink$55(tLObject, chatFull, z);
            }
        });
    }

    public /* synthetic */ void lambda$getLink$55(TLObject tLObject, TLRPC.ChatFull chatFull, boolean z) {
        if (tLObject instanceof TLRPC.TL_chatInviteExported) {
            TLRPC.TL_chatInviteExported tL_chatInviteExported = (TLRPC.TL_chatInviteExported) tLObject;
            if (chatFull != null) {
                chatFull.exported_invite = tL_chatInviteExported;
            } else {
                openShareAlert(true, null, tL_chatInviteExported.link, z);
            }
        }
    }

    public /* synthetic */ void lambda$getLink$58(final int i, final boolean z, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda59
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$getLink$57(tLObject, i, z);
            }
        });
    }

    public /* synthetic */ void lambda$getLink$57(TLObject tLObject, int i, boolean z) {
        if (tLObject instanceof TL_phone.exportedGroupCallInvite) {
            this.invites[i] = ((TL_phone.exportedGroupCallInvite) tLObject).link;
        } else {
            this.invites[i] = _UrlKt.FRAGMENT_ENCODE_SET;
        }
        for (int i2 = 0; i2 < 2; i2++) {
            String str = this.invites[i2];
            if (str == null) {
                return;
            }
            if (str.length() == 0) {
                this.invites[i2] = null;
            }
        }
        if (!z && canManageCall() && !this.call.call.join_muted) {
            this.invites[0] = null;
        }
        String[] strArr = this.invites;
        if (strArr[0] == null && strArr[1] == null && ChatObject.isPublic(this.currentChat)) {
            openShareAlert(true, null, this.accountInstance.getMessagesController().linkPrefix + "/" + ChatObject.getPublicUsername(this.currentChat), z);
            return;
        }
        String[] strArr2 = this.invites;
        openShareAlert(false, strArr2[0], strArr2[1], z);
    }

    /* JADX WARN: Removed duplicated region for block: B:97:0x005e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void openShareAlert(boolean r14, java.lang.String r15, java.lang.String r16, boolean r17) {
        /*
            r13 = this;
            boolean r0 = r13.isRtmpStream()
            r2 = 0
            if (r0 == 0) goto Lb
            if (r15 == 0) goto Lb
            r0 = r2
            goto Ld
        Lb:
            r0 = r16
        Ld:
            if (r17 == 0) goto L2b
            if (r15 == 0) goto L12
            r0 = r15
        L12:
            org.telegram.messenger.AndroidUtilities.addToClipboard(r0)
            boolean r0 = org.telegram.messenger.AndroidUtilities.shouldShowClipboardToast()
            if (r0 == 0) goto L2a
            org.telegram.ui.Components.UndoView r2 = r13.getUndoView()
            r8 = 0
            r9 = 0
            r3 = 0
            r5 = 33
            r6 = 0
            r7 = 0
            r2.showWithAction(r3, r5, r6, r7, r8, r9)
        L2a:
            return
        L2b:
            org.telegram.ui.LaunchActivity r3 = r13.parentActivity
            r4 = 0
            r5 = 1
            if (r3 == 0) goto L5e
            org.telegram.ui.ActionBar.INavigationLayout r3 = r3.getActionBarLayout()
            java.util.List r3 = r3.getFragmentStack()
            org.telegram.ui.LaunchActivity r6 = r13.parentActivity
            org.telegram.ui.ActionBar.INavigationLayout r6 = r6.getActionBarLayout()
            java.util.List r6 = r6.getFragmentStack()
            int r6 = r6.size()
            int r6 = r6 - r5
            java.lang.Object r3 = r3.get(r6)
            org.telegram.ui.ActionBar.BaseFragment r3 = (org.telegram.p026ui.ActionBar.BaseFragment) r3
            boolean r6 = r3 instanceof org.telegram.p026ui.ChatActivity
            if (r6 == 0) goto L5e
            org.telegram.ui.ChatActivity r3 = (org.telegram.p026ui.ChatActivity) r3
            boolean r3 = r3.needEnterText()
            r13.anyEnterEventSent = r5
            r13.enterEventSent = r5
            r12 = r3
            goto L5f
        L5e:
            r12 = r4
        L5f:
            if (r15 == 0) goto L66
            if (r0 != 0) goto L66
            r8 = r15
            r6 = r2
            goto L68
        L66:
            r6 = r15
            r8 = r0
        L68:
            if (r6 != 0) goto L8f
            if (r14 == 0) goto L8f
            org.telegram.tgnet.TLRPC$Chat r0 = r13.currentChat
            boolean r0 = org.telegram.messenger.ChatObject.isChannelOrGiga(r0)
            if (r0 == 0) goto L82
            int r0 = org.telegram.messenger.C2702R.string.VoipChannelInviteText
            java.lang.Object[] r2 = new java.lang.Object[r5]
            r2[r4] = r8
            java.lang.String r3 = "VoipChannelInviteText"
            java.lang.String r0 = org.telegram.messenger.LocaleController.formatString(r3, r0, r2)
        L80:
            r5 = r0
            goto L90
        L82:
            int r0 = org.telegram.messenger.C2702R.string.VoipGroupInviteText
            java.lang.Object[] r2 = new java.lang.Object[r5]
            r2[r4] = r8
            java.lang.String r3 = "VoipGroupInviteText"
            java.lang.String r0 = org.telegram.messenger.LocaleController.formatString(r3, r0, r2)
            goto L80
        L8f:
            r5 = r8
        L90:
            org.telegram.ui.GroupCallActivity$55 r0 = new org.telegram.ui.GroupCallActivity$55
            android.content.Context r2 = r13.getContext()
            r10 = 0
            r11 = 1
            r3 = 0
            r4 = 0
            r7 = 0
            r9 = r6
            r1 = r13
            r0.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
            r13.shareAlert = r0
            org.telegram.ui.GroupCallActivity$56 r2 = new org.telegram.ui.GroupCallActivity$56
            r2.<init>()
            r0.setDelegate(r2)
            org.telegram.ui.Components.ShareAlert r0 = r13.shareAlert
            org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda53 r2 = new org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda53
            r2.<init>()
            r0.setOnDismissListener(r2)
            org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda54 r0 = new org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda54
            r0.<init>()
            if (r12 == 0) goto Lbe
            r2 = 200(0xc8, double:9.9E-322)
            goto Lc0
        Lbe:
            r2 = 0
        Lc0:
            org.telegram.messenger.AndroidUtilities.runOnUIThread(r0, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.GroupCallActivity.openShareAlert(boolean, java.lang.String, java.lang.String, boolean):void");
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$55 */
    /* JADX INFO: loaded from: classes6.dex */
    class DialogC559755 extends ShareAlert {
        DialogC559755(Context context, ChatActivity chatActivity, ArrayList arrayList, String str, String str2, boolean z, String str3, String str4, boolean z2, boolean z3) {
            super(context, chatActivity, arrayList, str, str2, z, str3, str4, z2, z3);
        }

        @Override // org.telegram.p026ui.Components.ShareAlert
        protected void onSend(LongSparseArray longSparseArray, int i, TLRPC.TL_forumTopic tL_forumTopic, boolean z) {
            if (z) {
                if (longSparseArray.size() == 1) {
                    GroupCallActivity.this.getUndoView().showWithAction(((TLRPC.Dialog) longSparseArray.valueAt(0)).f1616id, 41, Integer.valueOf(i));
                } else {
                    GroupCallActivity.this.getUndoView().showWithAction(0L, 41, Integer.valueOf(i), Integer.valueOf(longSparseArray.size()), (Runnable) null, (Runnable) null);
                }
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$56 */
    /* JADX INFO: loaded from: classes6.dex */
    class C559856 implements ShareAlert.ShareAlertDelegate {
        @Override // org.telegram.ui.Components.ShareAlert.ShareAlertDelegate
        public /* synthetic */ void didShare() {
            ShareAlert.ShareAlertDelegate.CC.$default$didShare(this);
        }

        C559856() {
        }

        @Override // org.telegram.ui.Components.ShareAlert.ShareAlertDelegate
        public boolean didCopy() {
            if (!AndroidUtilities.shouldShowClipboardToast()) {
                return true;
            }
            GroupCallActivity.this.getUndoView().showWithAction(0L, 33, (Object) null, (Object) null, (Runnable) null, (Runnable) null);
            return true;
        }
    }

    public /* synthetic */ void lambda$openShareAlert$59(DialogInterface dialogInterface) {
        this.shareAlert = null;
    }

    public /* synthetic */ void lambda$openShareAlert$60() {
        ShareAlert shareAlert = this.shareAlert;
        if (shareAlert != null) {
            shareAlert.show();
        }
    }

    public void inviteUserToCall(final long j, final boolean z) {
        final TLRPC.User user;
        if (this.call == null || (user = this.accountInstance.getMessagesController().getUser(Long.valueOf(j))) == null) {
            return;
        }
        final AlertDialog[] alertDialogArr = {new AlertDialog(getContext(), 3)};
        final TL_phone.inviteToGroupCall invitetogroupcall = new TL_phone.inviteToGroupCall();
        invitetogroupcall.call = this.call.getInputGroupCall();
        TLRPC.TL_inputUser tL_inputUser = new TLRPC.TL_inputUser();
        tL_inputUser.user_id = user.f1775id;
        tL_inputUser.access_hash = user.access_hash;
        invitetogroupcall.users.add(tL_inputUser);
        final int iSendRequest = this.accountInstance.getConnectionsManager().sendRequest(invitetogroupcall, new RequestDelegate() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda80
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$inviteUserToCall$63(j, alertDialogArr, user, z, invitetogroupcall, tLObject, tL_error);
            }
        });
        if (iSendRequest != 0) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda81
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$inviteUserToCall$65(alertDialogArr, iSendRequest);
                }
            }, 500L);
        }
    }

    public /* synthetic */ void lambda$inviteUserToCall$63(final long j, final AlertDialog[] alertDialogArr, final TLRPC.User user, final boolean z, final TL_phone.inviteToGroupCall invitetogroupcall, TLObject tLObject, final TLRPC.TL_error tL_error) {
        if (tLObject != null) {
            this.accountInstance.getMessagesController().processUpdates((TLRPC.Updates) tLObject, false);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda84
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$inviteUserToCall$61(j, alertDialogArr, user);
                }
            });
        } else {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda85
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$inviteUserToCall$62(alertDialogArr, z, tL_error, j, invitetogroupcall);
                }
            });
        }
    }

    public /* synthetic */ void lambda$inviteUserToCall$61(long j, AlertDialog[] alertDialogArr, TLRPC.User user) {
        ChatObject.Call call = this.call;
        if (call == null || this.delayedGroupCallUpdated) {
            return;
        }
        call.addInvitedUser(j);
        applyCallParticipantUpdates(true);
        GroupVoipInviteAlert groupVoipInviteAlert = this.groupVoipInviteAlert;
        if (groupVoipInviteAlert != null) {
            groupVoipInviteAlert.lambda$new$0();
        }
        try {
            alertDialogArr[0].dismiss();
        } catch (Throwable unused) {
        }
        alertDialogArr[0] = null;
        getUndoView().showWithAction(0L, 34, user, this.currentChat, (Runnable) null, (Runnable) null);
    }

    public /* synthetic */ void lambda$inviteUserToCall$62(AlertDialog[] alertDialogArr, boolean z, TLRPC.TL_error tL_error, long j, TL_phone.inviteToGroupCall invitetogroupcall) {
        try {
            alertDialogArr[0].dismiss();
        } catch (Throwable unused) {
        }
        alertDialogArr[0] = null;
        if (z && "USER_NOT_PARTICIPANT".equals(tL_error.text)) {
            processSelectedOption(null, j, 3);
        } else {
            AlertsCreator.processError(this.currentAccount, tL_error, (BaseFragment) this.parentActivity.getActionBarLayout().getFragmentStack().get(this.parentActivity.getActionBarLayout().getFragmentStack().size() - 1), invitetogroupcall, new Object[0]);
        }
    }

    public /* synthetic */ void lambda$inviteUserToCall$65(AlertDialog[] alertDialogArr, final int i) {
        AlertDialog alertDialog = alertDialogArr[0];
        if (alertDialog == null) {
            return;
        }
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda83
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                this.f$0.lambda$inviteUserToCall$64(i, dialogInterface);
            }
        });
        alertDialogArr[0].show();
    }

    public /* synthetic */ void lambda$inviteUserToCall$64(int i, DialogInterface dialogInterface) {
        this.accountInstance.getConnectionsManager().cancelRequest(i, true);
    }

    public void invalidateActionBarAlpha() {
        ActionBar actionBar = this.actionBar;
        actionBar.setAlpha((actionBar.getTag() != null ? 1.0f : 0.0f) * (1.0f - this.renderersContainer.progressToFullscreenMode));
    }

    public int calculateScrollTopOffset() {
        int childCount = this.listView.getChildCount();
        int iMin = Integer.MAX_VALUE;
        for (int i = 0; i < childCount; i++) {
            View childAt = this.listView.getChildAt(i);
            if (this.listView.getChildAdapterPosition(childAt) >= 0) {
                iMin = Math.min(iMin, childAt.getTop());
            }
        }
        return iMin;
    }

    public void updateLayout(boolean z) {
        int childCount = this.listView.getChildCount();
        float paddingTop = 2.1474836E9f;
        for (int i = 0; i < childCount; i++) {
            if (this.listView.getChildAdapterPosition(this.listView.getChildAt(i)) >= 0) {
                paddingTop = Math.min(paddingTop, r6.getTop());
            }
        }
        if (paddingTop < 0.0f || paddingTop == 2.1474836E9f) {
            paddingTop = childCount != 0 ? 0.0f : this.listView.getPaddingTop();
        }
        boolean z2 = paddingTop <= ((float) (ActionBar.getCurrentActionBarHeight() - AndroidUtilities.m1081dp(14.0f)));
        float currentActionBarHeight = paddingTop + ActionBar.getCurrentActionBarHeight() + AndroidUtilities.m1081dp(14.0f);
        if ((z2 && this.actionBar.getTag() == null) || (!z2 && this.actionBar.getTag() != null)) {
            this.actionBar.setTag(z2 ? 1 : null);
            AnimatorSet animatorSet = this.actionBarAnimation;
            if (animatorSet != null) {
                animatorSet.cancel();
                this.actionBarAnimation = null;
            }
            setUseLightStatusBar(this.actionBar.getTag() == null);
            ViewPropertyAnimator duration = this.actionBar.getBackButton().animate().scaleX(z2 ? 1.0f : 0.9f).scaleY(z2 ? 1.0f : 0.9f).translationX(z2 ? 0.0f : -AndroidUtilities.m1081dp(14.0f)).setDuration(300L);
            CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.DEFAULT;
            duration.setInterpolator(cubicBezierInterpolator).start();
            this.actionBar.getTitleTextView().animate().translationY(z2 ? 0.0f : AndroidUtilities.m1081dp(23.0f)).setDuration(300L).setInterpolator(cubicBezierInterpolator).start();
            ObjectAnimator objectAnimator = this.subtitleYAnimator;
            if (objectAnimator != null) {
                objectAnimator.removeAllListeners();
                this.subtitleYAnimator.cancel();
            }
            SimpleTextView subtitleTextView = this.actionBar.getSubtitleTextView();
            Property property = View.TRANSLATION_Y;
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(subtitleTextView, (Property<SimpleTextView, Float>) property, this.actionBar.getSubtitleTextView().getTranslationY(), z2 ? 0.0f : AndroidUtilities.m1081dp(20.0f));
            this.subtitleYAnimator = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setDuration(300L);
            this.subtitleYAnimator.setInterpolator(cubicBezierInterpolator);
            this.subtitleYAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.GroupCallActivity.57
                final /* synthetic */ boolean val$show;

                C559957(boolean z22) {
                    z = z22;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    GroupCallActivity groupCallActivity = GroupCallActivity.this;
                    groupCallActivity.subtitleYAnimator = null;
                    groupCallActivity.actionBar.getSubtitleTextView().setTranslationY(z ? 0.0f : AndroidUtilities.m1081dp(20.0f));
                }
            });
            this.subtitleYAnimator.start();
            ObjectAnimator objectAnimator2 = this.additionalSubtitleYAnimator;
            if (objectAnimator2 != null) {
                objectAnimator2.cancel();
            }
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.actionBar.getAdditionalSubtitleTextView(), (Property<SimpleTextView, Float>) property, z22 ? 0.0f : AndroidUtilities.m1081dp(20.0f));
            this.additionalSubtitleYAnimator = objectAnimatorOfFloat2;
            objectAnimatorOfFloat2.setDuration(300L);
            this.additionalSubtitleYAnimator.setInterpolator(cubicBezierInterpolator);
            this.additionalSubtitleYAnimator.start();
            AnimatorSet animatorSet2 = new AnimatorSet();
            this.actionBarAnimation = animatorSet2;
            animatorSet2.setDuration(140L);
            AnimatorSet animatorSet3 = this.actionBarAnimation;
            ActionBar actionBar = this.actionBar;
            Property property2 = View.ALPHA;
            animatorSet3.playTogether(ObjectAnimator.ofFloat(actionBar, (Property<ActionBar, Float>) property2, z22 ? 1.0f : 0.0f), ObjectAnimator.ofFloat(this.actionBarBackground, (Property<View, Float>) property2, z22 ? 1.0f : 0.0f), ObjectAnimator.ofFloat(this.actionBarShadow, (Property<View, Float>) property2, z22 ? 1.0f : 0.0f));
            this.actionBarAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.GroupCallActivity.58
                C560058() {
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    GroupCallActivity.this.actionBarAnimation = null;
                }
            });
            this.actionBarAnimation.start();
            this.renderersContainer.pipView.setClickable(!z22 || isLandscapeMode);
        }
        if (this.scrollOffsetY != currentActionBarHeight) {
            setScrollOffsetY(currentActionBarHeight);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$57 */
    /* JADX INFO: loaded from: classes6.dex */
    class C559957 extends AnimatorListenerAdapter {
        final /* synthetic */ boolean val$show;

        C559957(boolean z22) {
            z = z22;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            GroupCallActivity groupCallActivity = GroupCallActivity.this;
            groupCallActivity.subtitleYAnimator = null;
            groupCallActivity.actionBar.getSubtitleTextView().setTranslationY(z ? 0.0f : AndroidUtilities.m1081dp(20.0f));
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$58 */
    /* JADX INFO: loaded from: classes6.dex */
    class C560058 extends AnimatorListenerAdapter {
        C560058() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            GroupCallActivity.this.actionBarAnimation = null;
        }
    }

    public void invalidateScrollOffsetY() {
        setScrollOffsetY(this.scrollOffsetY);
    }

    private void setScrollOffsetY(float f) {
        int iM1081dp;
        this.scrollOffsetY = f;
        this.listView.setTopGlowOffset((int) (f - ((FrameLayout.LayoutParams) this.listView.getLayoutParams()).topMargin));
        float fM1081dp = f - AndroidUtilities.m1081dp(74.0f);
        if (this.backgroundPaddingTop + fM1081dp < ActionBar.getCurrentActionBarHeight() * 2) {
            float fMin = Math.min(1.0f, (((ActionBar.getCurrentActionBarHeight() * 2) - fM1081dp) - this.backgroundPaddingTop) / (((r0 - this.backgroundPaddingTop) - AndroidUtilities.m1081dp(14.0f)) + ActionBar.getCurrentActionBarHeight()));
            iM1081dp = (int) (AndroidUtilities.m1081dp(AndroidUtilities.isTablet() ? 17.0f : 13.0f) * fMin);
            LightningView lightningView = this.textureLightningView;
            if (lightningView != null) {
                lightningView.setShadowOffset((int) (AndroidUtilities.m1081dp(8.0f) * fMin));
            }
            if (Math.abs(Math.min(1.0f, fMin) - this.colorProgress) > 1.0E-4f) {
                setColorProgress(Math.min(1.0f, fMin));
            }
            float f2 = 1.0f - ((0.1f * fMin) * 1.2f);
            this.titleTextView.setScaleX(Math.max(0.9f, f2));
            this.titleTextView.setScaleY(Math.max(0.9f, f2));
            float f3 = 1.0f - (fMin * 1.2f);
            this.titleTextView.setAlpha(Math.max(0.0f, f3) * (1.0f - this.renderersContainer.progressToFullscreenMode));
            this.liveLabelTextView.setScaleX(Math.max(0.9f, f2));
            this.liveLabelTextView.setScaleY(Math.max(0.9f, f2));
            this.liveLabelTextView.setAlpha(Math.max(0.0f, f3) * (1.0f - this.renderersContainer.progressToFullscreenMode));
        } else {
            this.titleTextView.setScaleX(1.0f);
            this.titleTextView.setScaleY(1.0f);
            this.titleTextView.setAlpha(1.0f - this.renderersContainer.progressToFullscreenMode);
            this.liveLabelTextView.setScaleX(1.0f);
            this.liveLabelTextView.setScaleY(1.0f);
            this.liveLabelTextView.setAlpha(1.0f - this.renderersContainer.progressToFullscreenMode);
            if (this.colorProgress > 1.0E-4f) {
                setColorProgress(0.0f);
            }
            iM1081dp = 0;
        }
        checkGroupCallUiPositions_MessagesList();
        float f4 = iM1081dp;
        this.menuItemsContainer.setTranslationY(Math.max(AndroidUtilities.m1081dp(4.0f), (f - AndroidUtilities.m1081dp(53.0f)) - f4));
        this.titleLayout.setTranslationY(Math.max(AndroidUtilities.m1081dp(4.0f), (f - AndroidUtilities.m1081dp(44.0f)) - f4));
        LightningView lightningView2 = this.textureLightningView;
        if (lightningView2 != null) {
            lightningView2.setTranslationY(Math.max(AndroidUtilities.m1081dp(4.0f), f - AndroidUtilities.m1081dp(37.0f)));
        }
        LinearLayout linearLayout = this.scheduleTimerContainer;
        if (linearLayout != null) {
            linearLayout.setTranslationY(Math.max(AndroidUtilities.m1081dp(4.0f), (f - AndroidUtilities.m1081dp(44.0f)) - f4));
        }
        this.containerView.invalidate();
        updateTopBulletinY();
    }

    private void cancelMutePress() {
        if (this.scheduled) {
            this.scheduled = false;
            AndroidUtilities.cancelRunOnUIThread(this.pressRunnable);
        }
        if (this.pressed) {
            this.pressed = false;
            MotionEvent motionEventObtain = MotionEvent.obtain(0L, 0L, 3, 0.0f, 0.0f, 0);
            this.muteButton.onTouchEvent(motionEventObtain);
            motionEventObtain.recycle();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:355:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:358:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:361:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:368:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:370:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:372:0x0115 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:378:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:379:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:382:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:383:0x0130  */
    /* JADX WARN: Removed duplicated region for block: B:385:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:386:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:390:0x013b  */
    /* JADX WARN: Removed duplicated region for block: B:393:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:396:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:399:0x014c  */
    /* JADX WARN: Removed duplicated region for block: B:400:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:403:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:404:0x015b  */
    /* JADX WARN: Removed duplicated region for block: B:407:0x0161  */
    /* JADX WARN: Removed duplicated region for block: B:409:0x017e  */
    /* JADX WARN: Removed duplicated region for block: B:411:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:415:0x01d7  */
    /* JADX WARN: Removed duplicated region for block: B:418:0x01e1  */
    /* JADX WARN: Removed duplicated region for block: B:420:0x01e6  */
    /* JADX WARN: Removed duplicated region for block: B:423:0x0207 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:427:0x0224  */
    /* JADX WARN: Removed duplicated region for block: B:444:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateState(boolean r27, boolean r28) {
        /*
            Method dump skipped, instruction units count: 629
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.GroupCallActivity.updateState(boolean, boolean):void");
    }

    private void updateButtonsVisibility(boolean z) {
        boolean zBooleanValue;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        ChatObject.Call call = this.call;
        boolean z6 = false;
        if (call == null || call.isScheduled()) {
            this.buttonsContainer.setButtonVisibility(this.muteButton, this.switchToButtonProgress > 0.1f, z);
            this.buttonsContainer.setButtonVisibility(this.leaveButton, this.switchToButtonProgress > 0.1f, z);
            this.buttonsContainer.setButtonVisibility(this.soundButton, this.switchToButtonProgress > 0.1f, z);
            this.buttonsContainer.setButtonVisibility(this.cameraButton, false, z);
            this.buttonsContainer.setButtonVisibility(this.flipButton, false, z);
            this.buttonsContainer.setButtonVisibility(this.speakerButton, false, z);
            this.buttonsContainer.setButtonVisibility(this.messageButton, false, z);
            return;
        }
        boolean z7 = VoIPService.getSharedInstance() != null && VoIPService.getSharedInstance().getVideoState(false) == 2;
        TLRPC.GroupCallParticipant groupCallParticipant = (TLRPC.GroupCallParticipant) this.call.participants.get(MessageObject.getPeerId(this.selfPeer));
        boolean z8 = (groupCallParticipant == null || groupCallParticipant.can_self_unmute || !groupCallParticipant.muted || canManageCall()) ? false : true;
        Boolean bool = this.pendingCommentsEnabled;
        if (bool != null) {
            zBooleanValue = bool.booleanValue();
        } else {
            TLRPC.GroupCall groupCall = this.call.call;
            zBooleanValue = groupCall != null && groupCall.messages_enabled;
        }
        if (((z8 || !this.call.canRecordVideo()) && !z7) || isRtmpStream()) {
            z2 = true;
            z3 = false;
        } else {
            z3 = true;
            z2 = false;
        }
        if (z7) {
            z5 = true;
            z4 = false;
        } else {
            z4 = !z8;
            z5 = false;
        }
        if (isLandscapeMode) {
            z4 = false;
        } else {
            z6 = z5;
        }
        this.buttonsContainer.setButtonVisibility(this.muteButton, true, z);
        this.buttonsContainer.setButtonVisibility(this.leaveButton, true, z);
        this.buttonsContainer.setButtonVisibility(this.cameraButton, z3, z);
        this.buttonsContainer.setButtonVisibility(this.flipButton, z6, z);
        this.buttonsContainer.setButtonVisibility(this.soundButton, z2, z);
        this.buttonsContainer.setButtonVisibility(this.speakerButton, z4, z);
        this.buttonsContainer.setButtonVisibility(this.messageButton, zBooleanValue, z);
    }

    private void updateLiveLabel(boolean z) {
        if (isRtmpStream()) {
            this.liveLabelTextView.setVisibility(0);
            boolean z2 = ((Integer) this.liveLabelTextView.getTag()).intValue() == 3;
            int i = this.currentCallState;
            final boolean z3 = i == 3;
            this.liveLabelTextView.setTag(Integer.valueOf(i));
            if (z2 != z3) {
                ValueAnimator valueAnimator = this.liveLabelBgColorAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
                if (z) {
                    final int color = this.liveLabelPaint.getColor();
                    final int i2 = z3 ? -1163700 : -12761513;
                    ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                    this.liveLabelBgColorAnimator = valueAnimatorOfFloat;
                    valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda3
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            this.f$0.lambda$updateLiveLabel$66(color, i2, z3, valueAnimator2);
                        }
                    });
                    this.liveLabelBgColorAnimator.setDuration(300L);
                    this.liveLabelBgColorAnimator.setInterpolator(CubicBezierInterpolator.DEFAULT);
                    this.liveLabelBgColorAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.GroupCallActivity.59
                        C560159() {
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            GroupCallActivity.this.liveLabelBgColorAnimator = null;
                            GroupCallActivity.this.liveLabelPaint.setColor(GroupCallActivity.this.currentCallState == 3 ? -1163700 : -12761513);
                            GroupCallActivity.this.liveLabelTextView.invalidate();
                        }
                    });
                    this.liveLabelBgColorAnimator.start();
                    return;
                }
                this.liveLabelPaint.setColor(this.currentCallState == 3 ? -1163700 : -12761513);
                this.liveLabelTextView.invalidate();
                return;
            }
            return;
        }
        this.liveLabelTextView.setVisibility(8);
    }

    public /* synthetic */ void lambda$updateLiveLabel$66(int i, int i2, boolean z, ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.liveLabelPaint.setColor(AndroidUtilities.getOffsetColor(i, i2, fFloatValue, 1.0f));
        this.liveLabelTextView.invalidate();
        if (!z) {
            fFloatValue = 1.0f - fFloatValue;
        }
        this.textureLightningViewAnimatingAlpha = fFloatValue;
        LightningView lightningView = this.textureLightningView;
        if (lightningView == null || !this.needTextureLightning) {
            return;
        }
        lightningView.invalidate();
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$59 */
    /* JADX INFO: loaded from: classes6.dex */
    class C560159 extends AnimatorListenerAdapter {
        C560159() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            GroupCallActivity.this.liveLabelBgColorAnimator = null;
            GroupCallActivity.this.liveLabelPaint.setColor(GroupCallActivity.this.currentCallState == 3 ? -1163700 : -12761513);
            GroupCallActivity.this.liveLabelTextView.invalidate();
        }
    }

    @Override // org.telegram.messenger.voip.VoIPService.StateListener
    public void onAudioSettingsChanged() {
        updateSpeakerPhoneIcon(true);
        if (VoIPService.getSharedInstance() == null || VoIPService.getSharedInstance().isMicMute()) {
            setMicAmplitude(0.0f);
        }
        if (this.listView.getVisibility() == 0) {
            AndroidUtilities.updateVisibleRows(this.listView);
        }
        if (this.fullscreenUsersListView.getVisibility() == 0) {
            AndroidUtilities.updateVisibleRows(this.fullscreenUsersListView);
        }
        this.attachedRenderersTmp.clear();
        this.attachedRenderersTmp.addAll(this.attachedRenderers);
        for (int i = 0; i < this.attachedRenderersTmp.size(); i++) {
            ((GroupCallMiniTextureView) this.attachedRenderersTmp.get(i)).updateAttachState(true);
        }
    }

    private void updateSpeakerPhoneIcon(boolean z) {
        VoIPToggleButton voIPToggleButton = this.soundButton;
        if (voIPToggleButton == null || voIPToggleButton.getVisibility() != 0) {
            return;
        }
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        boolean z2 = false;
        if (sharedInstance == null || isRtmpStream()) {
            this.soundButton.setData(C2702R.drawable.msg_voiceshare, -1, 0, 0.3f, true, LocaleController.getString(C2702R.string.VoipChatShare), false, z);
            this.buttonsContainer.setButtonEnabled(this.soundButton, ChatObject.isPublic(this.currentChat) || (ChatObject.hasAdminRights(this.currentChat) && ChatObject.canAddUsers(this.currentChat)), z);
            this.soundButton.setChecked(true, false);
            return;
        }
        this.buttonsContainer.setButtonEnabled(this.soundButton, true, z);
        boolean z3 = sharedInstance.isBluetoothOn() || sharedInstance.isBluetoothWillOn();
        if (!z3 && sharedInstance.isSpeakerphoneOn()) {
            z2 = true;
        }
        if (z3) {
            this.soundButton.setData(C2702R.drawable.calls_bluetooth, -1, 0, 0.1f, true, LocaleController.getString(C2702R.string.VoipAudioRoutingBluetooth), false, z);
        } else if (z2) {
            this.soundButton.setData(C2702R.drawable.calls_speaker, -1, 0, 0.3f, true, LocaleController.getString(C2702R.string.VoipSpeaker), false, z);
        } else if (sharedInstance.isHeadsetPlugged()) {
            this.soundButton.setData(C2702R.drawable.calls_headphones, -1, 0, 0.1f, true, LocaleController.getString(C2702R.string.VoipAudioRoutingHeadset), false, z);
        } else {
            this.soundButton.setData(C2702R.drawable.calls_speaker, -1, 0, 0.1f, true, LocaleController.getString(C2702R.string.VoipSpeaker), false, z);
        }
        this.soundButton.setChecked(z2, z);
        int audioOutputIcon = getAudioOutputIcon(getAudioOutputValue());
        if (this.speakerIcon != audioOutputIcon) {
            ImageView imageView = this.speakerImageView;
            this.speakerIcon = audioOutputIcon;
            AndroidUtilities.updateImageViewImageAnimated(imageView, audioOutputIcon);
        }
        if (this.speakerButton.getVisibility() == 0) {
            this.speakerButton.setData(0, -1, 0, 1.0f, true, getAudioOutputName(getAudioOutputValue()), false, z);
            this.speakerButton.setChecked(getAudioOutputActive(getAudioOutputValue()), z);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:564:0x01b7  */
    /* JADX WARN: Removed duplicated region for block: B:565:0x01c3  */
    /* JADX WARN: Removed duplicated region for block: B:568:0x01dc A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:579:0x0201  */
    /* JADX WARN: Removed duplicated region for block: B:582:0x020b  */
    /* JADX WARN: Removed duplicated region for block: B:583:0x0220  */
    /* JADX WARN: Removed duplicated region for block: B:586:0x023f  */
    /* JADX WARN: Removed duplicated region for block: B:665:0x036a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateMuteButton(int r22, boolean r23) {
        /*
            Method dump skipped, instruction units count: 966
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.GroupCallActivity.updateMuteButton(int, boolean):void");
    }

    public void fillColors(int i, int[] iArr) {
        if (isRtmpStream()) {
            int i2 = Theme.key_voipgroup_disabledButton;
            iArr[0] = Theme.getColor(i2);
            iArr[1] = AndroidUtilities.getOffsetColor(Theme.getColor(Theme.key_voipgroup_rtmpButton), Theme.getColor(Theme.key_voipgroup_soundButtonActiveScrolled), this.colorProgress, 1.0f);
            iArr[2] = AndroidUtilities.getOffsetColor(Theme.getColor(Theme.key_voipgroup_listViewBackgroundUnscrolled), Theme.getColor(i2), this.colorProgress, 1.0f);
        } else if (i == 0) {
            iArr[0] = Theme.getColor(Theme.key_voipgroup_unmuteButton2);
            iArr[1] = AndroidUtilities.getOffsetColor(Theme.getColor(Theme.key_voipgroup_soundButtonActive), Theme.getColor(Theme.key_voipgroup_soundButtonActiveScrolled), this.colorProgress, 1.0f);
            iArr[2] = Theme.getColor(Theme.key_voipgroup_soundButton);
        } else if (i == 1) {
            iArr[0] = Theme.getColor(Theme.key_voipgroup_muteButton2);
            iArr[1] = AndroidUtilities.getOffsetColor(Theme.getColor(Theme.key_voipgroup_soundButtonActive2), Theme.getColor(Theme.key_voipgroup_soundButtonActive2Scrolled), this.colorProgress, 1.0f);
            iArr[2] = Theme.getColor(Theme.key_voipgroup_soundButton2);
        } else if (isGradientState(i)) {
            iArr[0] = Theme.getColor(Theme.key_voipgroup_mutedByAdminGradient3);
            iArr[1] = Theme.getColor(Theme.key_voipgroup_mutedByAdminMuteButton);
            iArr[2] = Theme.getColor(Theme.key_voipgroup_mutedByAdminMuteButtonDisabled);
        } else {
            int i3 = Theme.key_voipgroup_disabledButton;
            iArr[0] = Theme.getColor(i3);
            iArr[1] = AndroidUtilities.getOffsetColor(Theme.getColor(Theme.key_voipgroup_disabledButtonActive), Theme.getColor(Theme.key_voipgroup_disabledButtonActiveScrolled), this.colorProgress, 1.0f);
            iArr[2] = AndroidUtilities.getOffsetColor(Theme.getColor(Theme.key_voipgroup_listViewBackgroundUnscrolled), Theme.getColor(i3), this.colorProgress, 1.0f);
        }
        if (isGradientState(i)) {
            iArr[3] = ColorUtils.blendARGB(Theme.getColor(Theme.key_voipgroup_mutedByAdminGradient), Theme.getColor(Theme.key_voipgroup_mutedByAdminGradient3), 0.5f);
        } else if (i == 1) {
            iArr[3] = ColorUtils.blendARGB(Theme.getColor(Theme.key_voipgroup_soundButtonActive2), ColorUtils.blendARGB(Theme.getColor(Theme.key_voipgroup_muteButton), Theme.getColor(Theme.key_voipgroup_muteButton3), 0.5f), 0.75f);
        } else {
            iArr[3] = ColorUtils.blendARGB(Theme.getColor(Theme.key_voipgroup_unmuteButton2), Theme.getColor(Theme.key_voipgroup_unmuteButton), 0.5f);
        }
    }

    public void showRecordHint(View view) {
        if (this.recordHintView == null) {
            HintView hintView = new HintView(getContext(), 8, true);
            this.recordHintView = hintView;
            hintView.setAlpha(0.0f);
            this.recordHintView.setVisibility(4);
            this.recordHintView.setShowingDuration(3000L);
            this.containerView.addView(this.recordHintView, LayoutHelper.createFrame(-2, -2.0f, 51, 19.0f, 0.0f, 19.0f, 0.0f));
            if (ChatObject.isChannelOrGiga(this.currentChat)) {
                this.recordHintView.setText(LocaleController.getString(C2702R.string.VoipChannelRecording));
            } else {
                this.recordHintView.setText(LocaleController.getString(C2702R.string.VoipGroupRecording));
            }
            this.recordHintView.setBackgroundColor(-366530760, -1);
        }
        this.recordHintView.setExtraTranslationY(-AndroidUtilities.statusBarHeight);
        this.recordHintView.showForView(view, true);
    }

    public void showReminderHint() {
        SharedPreferences globalMainSettings = MessagesController.getGlobalMainSettings();
        if (globalMainSettings.getBoolean("reminderhint", false)) {
            return;
        }
        globalMainSettings.edit().putBoolean("reminderhint", true).apply();
        if (this.reminderHintView == null) {
            HintView hintView = new HintView(getContext(), 8);
            this.reminderHintView = hintView;
            hintView.setAlpha(0.0f);
            this.reminderHintView.setVisibility(4);
            this.reminderHintView.setShowingDuration(4000L);
            this.containerView.addView(this.reminderHintView, LayoutHelper.createFrame(-2, -2.0f, 51, 19.0f, 0.0f, 19.0f, 0.0f));
            this.reminderHintView.setText(LocaleController.getString(C2702R.string.VoipChatReminderHint));
            this.reminderHintView.setBackgroundColor(-366530760, -1);
        }
        this.reminderHintView.setExtraTranslationY(-AndroidUtilities.statusBarHeight);
        this.reminderHintView.showForView(this.muteButton, true);
    }

    private void updateMuteButtonState(boolean z) {
        boolean z2;
        this.muteButton.invalidate();
        WeavingState[] weavingStateArr = this.states;
        int i = this.muteButtonState;
        if (weavingStateArr[i] == null) {
            weavingStateArr[i] = new WeavingState(i);
            int i2 = this.muteButtonState;
            if (i2 == 3) {
                this.states[i2].shader = null;
            } else if (isGradientState(i2)) {
                this.states[this.muteButtonState].shader = new LinearGradient(0.0f, 400.0f, 400.0f, 0.0f, new int[]{Theme.getColor(Theme.key_voipgroup_mutedByAdminGradient), Theme.getColor(Theme.key_voipgroup_mutedByAdminGradient3), Theme.getColor(Theme.key_voipgroup_mutedByAdminGradient2)}, (float[]) null, Shader.TileMode.CLAMP);
            } else {
                int i3 = this.muteButtonState;
                if (i3 == 1) {
                    this.states[i3].shader = new RadialGradient(200.0f, 200.0f, 200.0f, new int[]{Theme.getColor(Theme.key_voipgroup_muteButton), Theme.getColor(Theme.key_voipgroup_muteButton3)}, (float[]) null, Shader.TileMode.CLAMP);
                } else {
                    this.states[i3].shader = new RadialGradient(200.0f, 200.0f, 200.0f, new int[]{Theme.getColor(Theme.key_voipgroup_unmuteButton2), Theme.getColor(Theme.key_voipgroup_unmuteButton)}, (float[]) null, Shader.TileMode.CLAMP);
                }
            }
        }
        WeavingState weavingState = this.states[this.muteButtonState];
        WeavingState weavingState2 = this.currentState;
        if (weavingState != weavingState2) {
            this.prevState = weavingState2;
            this.currentState = weavingState;
            if (weavingState2 == null || !z) {
                this.switchProgress = 1.0f;
                this.prevState = null;
            } else {
                this.switchProgress = 0.0f;
            }
        }
        if (!z) {
            WeavingState weavingState3 = this.currentState;
            boolean z3 = false;
            if (weavingState3 != null) {
                int i4 = weavingState3.currentState;
                boolean z4 = i4 == 1 || i4 == 0;
                z2 = i4 != 3;
                z3 = z4;
            } else {
                z2 = false;
            }
            this.showWavesProgress = z3 ? 1.0f : 0.0f;
            this.showLightingProgress = z2 ? 1.0f : 0.0f;
        }
        this.buttonsContainer.invalidate();
    }

    private static void processOnLeave(ChatObject.Call call, boolean z, long j, Runnable runnable) {
        if (VoIPService.getSharedInstance() != null) {
            VoIPService.getSharedInstance().hangUp(z ? 1 : 0);
        }
        if (call != null) {
            TLRPC.GroupCallParticipant groupCallParticipant = (TLRPC.GroupCallParticipant) call.participants.get(j);
            if (groupCallParticipant != null) {
                call.participants.delete(j);
                call.sortedParticipants.remove(groupCallParticipant);
                call.visibleParticipants.remove(groupCallParticipant);
                int i = 0;
                while (i < call.visibleVideoParticipants.size()) {
                    if (MessageObject.getPeerId(call.visibleVideoParticipants.get(i).participant.peer) == MessageObject.getPeerId(groupCallParticipant.peer)) {
                        call.visibleVideoParticipants.remove(i);
                        i--;
                    }
                    i++;
                }
                TLRPC.GroupCall groupCall = call.call;
                groupCall.participants_count--;
            }
            for (int i2 = 0; i2 < call.sortedParticipants.size(); i2++) {
                TLRPC.GroupCallParticipant groupCallParticipant2 = call.sortedParticipants.get(i2);
                groupCallParticipant2.lastActiveDate = groupCallParticipant2.lastSpeakTime;
            }
        }
        if (runnable != null) {
            runnable.run();
        }
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.didStartedCall, new Object[0]);
    }

    public static void onLeaveClick(Context context, Runnable runnable, boolean z) {
        onLeaveClick(context, runnable, z, false);
    }

    public static void onLeaveClick(Context context, final Runnable runnable, boolean z, boolean z2) {
        TLRPC.GroupCall groupCall;
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        if (sharedInstance == null) {
            return;
        }
        TLRPC.Chat chat = sharedInstance.getChat();
        final ChatObject.Call call = sharedInstance.groupCall;
        final long selfId = sharedInstance.getSelfId();
        if (z2 || !ChatObject.canManageCalls(chat)) {
            processOnLeave(call, false, selfId, runnable);
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (sharedInstance.isConference()) {
            builder.setTitle(LocaleController.getString(C2702R.string.VoipChannelLeaveConferenceAlertTitle));
            builder.setMessage(LocaleController.getString(C2702R.string.VoipChannelLeaveConferenceAlertText));
        } else if (ChatObject.isChannelOrGiga(chat)) {
            builder.setTitle(LocaleController.getString(C2702R.string.VoipChannelLeaveAlertTitle));
            builder.setMessage(LocaleController.getString(C2702R.string.VoipChannelLeaveAlertText));
        } else {
            builder.setTitle(LocaleController.getString(C2702R.string.VoipGroupLeaveAlertTitle));
            builder.setMessage(LocaleController.getString(C2702R.string.VoipGroupLeaveAlertText));
        }
        sharedInstance.getAccount();
        final CheckBoxCell[] checkBoxCellArr = new CheckBoxCell[1];
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        if (!sharedInstance.isConference() || (call != null && (groupCall = call.call) != null && groupCall.creator)) {
            CheckBoxCell checkBoxCell = new CheckBoxCell(context, 1);
            checkBoxCellArr[0] = checkBoxCell;
            checkBoxCell.setBackground(Theme.getSelectorDrawable(false));
            if (z) {
                checkBoxCellArr[0].setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
            } else {
                checkBoxCellArr[0].setTextColor(Theme.getColor(Theme.key_voipgroup_actionBarItems));
                ((CheckBoxSquare) checkBoxCellArr[0].getCheckBoxView()).setColors(Theme.key_voipgroup_mutedIcon, Theme.key_voipgroup_listeningText, Theme.key_voipgroup_nameText);
            }
            checkBoxCellArr[0].setTag(0);
            if (sharedInstance.isConference()) {
                checkBoxCellArr[0].setText(LocaleController.getString(C2702R.string.VoipChannelLeaveConferenceAlertEndChat), _UrlKt.FRAGMENT_ENCODE_SET, false, false);
            } else if (ChatObject.isChannelOrGiga(chat)) {
                checkBoxCellArr[0].setText(LocaleController.getString(C2702R.string.VoipChannelLeaveAlertEndChat), _UrlKt.FRAGMENT_ENCODE_SET, false, false);
            } else {
                checkBoxCellArr[0].setText(LocaleController.getString(C2702R.string.VoipGroupLeaveAlertEndChat), _UrlKt.FRAGMENT_ENCODE_SET, false, false);
            }
            checkBoxCellArr[0].setPadding(LocaleController.isRTL ? AndroidUtilities.m1081dp(16.0f) : AndroidUtilities.m1081dp(8.0f), 0, LocaleController.isRTL ? AndroidUtilities.m1081dp(8.0f) : AndroidUtilities.m1081dp(16.0f), 0);
            linearLayout.addView(checkBoxCellArr[0], LayoutHelper.createLinear(-1, -2));
            checkBoxCellArr[0].setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    checkBoxCellArr[((Integer) view.getTag()).intValue()].setChecked(!r0[view.intValue()].isChecked(), true);
                }
            });
        }
        builder.setView(linearLayout);
        builder.setDialogButtonColorKey(Theme.key_voipgroup_listeningText);
        builder.setPositiveButton(LocaleController.getString(C2702R.string.VoipGroupLeave), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda2
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                GroupCallActivity.$r8$lambda$5w6tLTwxz_75uKnWsnpVUxhxPJE(call, checkBoxCellArr, selfId, runnable, alertDialog, i);
            }
        });
        builder.setNegativeButton(LocaleController.getString(C2702R.string.Cancel), null);
        if (z) {
            builder.setDimEnabled(false);
        }
        AlertDialog alertDialogCreate = builder.create();
        if (z) {
            if (Build.VERSION.SDK_INT >= 26) {
                alertDialogCreate.getWindow().setType(2038);
            } else {
                alertDialogCreate.getWindow().setType(2003);
            }
            alertDialogCreate.getWindow().clearFlags(2);
        }
        if (!z) {
            alertDialogCreate.setBackgroundColor(Theme.getColor(Theme.key_voipgroup_dialogBackground));
        }
        alertDialogCreate.show();
        if (z) {
            return;
        }
        TextView textView = (TextView) alertDialogCreate.getButton(-1);
        if (textView != null) {
            textView.setTextColor(Theme.getColor(Theme.key_voipgroup_leaveCallMenu));
        }
        alertDialogCreate.setTextColor(Theme.getColor(Theme.key_voipgroup_actionBarItems));
    }

    public static /* synthetic */ void $r8$lambda$5w6tLTwxz_75uKnWsnpVUxhxPJE(ChatObject.Call call, CheckBoxCell[] checkBoxCellArr, long j, Runnable runnable, AlertDialog alertDialog, int i) {
        boolean z = false;
        CheckBoxCell checkBoxCell = checkBoxCellArr[0];
        if (checkBoxCell != null && checkBoxCell.isChecked()) {
            z = true;
        }
        processOnLeave(call, z, j, runnable);
    }

    public void processSelectedOption(TLRPC.GroupCallParticipant groupCallParticipant, final long j, int i) {
        TLObject chat;
        String firstName;
        TextView textView;
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        if (sharedInstance == null) {
            return;
        }
        if (j > 0) {
            chat = this.accountInstance.getMessagesController().getUser(Long.valueOf(j));
        } else {
            chat = this.accountInstance.getMessagesController().getChat(Long.valueOf(-j));
        }
        final TLObject tLObject = chat;
        if (i == 0 || i == 2 || i == 3) {
            if (i == 0) {
                if (VoIPService.getSharedInstance() == null) {
                    return;
                }
                VoIPService.getSharedInstance().editCallMember(tLObject, Boolean.TRUE, null, null, null, null);
                getUndoView().showWithAction(0L, 30, tLObject, (Object) null, (Runnable) null, (Runnable) null);
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setDialogButtonColorKey(Theme.key_voipgroup_listeningText);
            TextView textView2 = new TextView(getContext());
            int i2 = Theme.key_voipgroup_actionBarItems;
            textView2.setTextColor(Theme.getColor(i2));
            textView2.setTextSize(1, 16.0f);
            textView2.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
            FrameLayout frameLayout = new FrameLayout(getContext());
            builder.setView(frameLayout);
            AvatarDrawable avatarDrawable = new AvatarDrawable();
            avatarDrawable.setTextSize(AndroidUtilities.m1081dp(12.0f));
            BackupImageView backupImageView = new BackupImageView(getContext());
            backupImageView.setRoundRadius(AndroidUtilities.m1081dp(20.0f));
            frameLayout.addView(backupImageView, LayoutHelper.createFrame(40, 40.0f, (LocaleController.isRTL ? 5 : 3) | 48, 22.0f, 5.0f, 22.0f, 0.0f));
            avatarDrawable.setInfo(this.currentAccount, tLObject);
            boolean z = tLObject instanceof TLRPC.User;
            if (z) {
                TLRPC.User user = (TLRPC.User) tLObject;
                backupImageView.setForUserOrChat(user, avatarDrawable);
                firstName = UserObject.getFirstName(user);
            } else {
                TLRPC.Chat chat2 = (TLRPC.Chat) tLObject;
                backupImageView.setForUserOrChat(chat2, avatarDrawable);
                firstName = chat2.title;
            }
            TextView textView3 = new TextView(getContext());
            textView3.setTextColor(Theme.getColor(i2));
            textView3.setTextSize(1, 20.0f);
            textView3.setTypeface(AndroidUtilities.bold());
            textView3.setLines(1);
            textView3.setMaxLines(1);
            textView3.setSingleLine(true);
            textView3.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
            textView3.setEllipsize(TextUtils.TruncateAt.END);
            TLRPC.Chat chat3 = this.currentChat;
            String str = chat3 != null ? chat3.title : _UrlKt.FRAGMENT_ENCODE_SET;
            if (i == 2) {
                if (isConference()) {
                    textView3.setText(LocaleController.getString(C2702R.string.VoipConferenceRemoveMemberAlertTitle2));
                    textView2.setText(AndroidUtilities.replaceTags(LocaleController.formatString(C2702R.string.VoipConferenceRemoveMemberAlertText2, firstName)));
                } else {
                    textView3.setText(LocaleController.getString(C2702R.string.VoipGroupRemoveMemberAlertTitle2));
                    if (ChatObject.isChannelOrGiga(this.currentChat)) {
                        textView2.setText(AndroidUtilities.replaceTags(LocaleController.formatString(C2702R.string.VoipChannelRemoveMemberAlertText2, firstName, str)));
                    } else {
                        textView2.setText(AndroidUtilities.replaceTags(LocaleController.formatString(C2702R.string.VoipGroupRemoveMemberAlertText2, firstName, str)));
                    }
                }
            } else {
                textView3.setText(LocaleController.getString(C2702R.string.VoipGroupAddMemberTitle));
                textView2.setText(AndroidUtilities.replaceTags(LocaleController.formatString(C2702R.string.VoipGroupAddMemberText, firstName, str)));
            }
            boolean z2 = LocaleController.isRTL;
            frameLayout.addView(textView3, LayoutHelper.createFrame(-1, -2.0f, (z2 ? 5 : 3) | 48, z2 ? 21 : 76, 11.0f, z2 ? 76 : 21, 0.0f));
            frameLayout.addView(textView2, LayoutHelper.createFrame(-2, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, 24.0f, 57.0f, 24.0f, 9.0f));
            if (i == 2) {
                builder.setPositiveButton(LocaleController.getString(C2702R.string.VoipGroupUserRemove), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda69
                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                    public final void onClick(AlertDialog alertDialog, int i3) {
                        this.f$0.lambda$processSelectedOption$69(tLObject, alertDialog, i3);
                    }
                });
            } else if (z) {
                final TLRPC.User user2 = (TLRPC.User) tLObject;
                builder.setPositiveButton(LocaleController.getString(C2702R.string.VoipGroupAdd), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda70
                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                    public final void onClick(AlertDialog alertDialog, int i3) {
                        this.f$0.lambda$processSelectedOption$71(user2, j, alertDialog, i3);
                    }
                });
            }
            builder.setNegativeButton(LocaleController.getString(C2702R.string.Cancel), null);
            AlertDialog alertDialogCreate = builder.create();
            alertDialogCreate.setBackgroundColor(Theme.getColor(Theme.key_voipgroup_dialogBackground));
            alertDialogCreate.show();
            if (i != 2 || (textView = (TextView) alertDialogCreate.getButton(-1)) == null) {
                return;
            }
            textView.setTextColor(Theme.getColor(Theme.key_voipgroup_leaveCallMenu));
            return;
        }
        if (i == 6) {
            this.parentActivity.switchToAccount(this.currentAccount, true);
            Bundle bundle = new Bundle();
            if (j > 0) {
                bundle.putLong("user_id", j);
            } else {
                bundle.putLong("chat_id", -j);
            }
            this.parentActivity.lambda$runLinkRequest$103(new ChatActivity(bundle));
            lambda$new$0();
            return;
        }
        if (i == 8) {
            this.parentActivity.switchToAccount(this.currentAccount, true);
            BaseFragment baseFragment = (BaseFragment) this.parentActivity.getActionBarLayout().getFragmentStack().get(this.parentActivity.getActionBarLayout().getFragmentStack().size() - 1);
            if ((baseFragment instanceof ChatActivity) && ((ChatActivity) baseFragment).getDialogId() == j) {
                lambda$new$0();
                return;
            }
            Bundle bundle2 = new Bundle();
            if (j > 0) {
                bundle2.putLong("user_id", j);
            } else {
                bundle2.putLong("chat_id", -j);
            }
            this.parentActivity.lambda$runLinkRequest$103(new ChatActivity(bundle2));
            lambda$new$0();
            return;
        }
        if (i == 7) {
            sharedInstance.editCallMember(tLObject, Boolean.TRUE, null, null, Boolean.FALSE, null);
            updateMuteButton(2, true);
            return;
        }
        if (i == 9) {
            ImageUpdater imageUpdater = this.currentAvatarUpdater;
            if (imageUpdater == null || !imageUpdater.isUploadingImage()) {
                TLRPC.User currentUser = this.accountInstance.getUserConfig().getCurrentUser();
                ImageUpdater imageUpdater2 = new ImageUpdater(true, 0, true);
                this.currentAvatarUpdater = imageUpdater2;
                imageUpdater2.setOpenWithFrontfaceCamera(true);
                this.currentAvatarUpdater.setForceDarkTheme(true);
                this.currentAvatarUpdater.setSearchAvailable(true, true);
                this.currentAvatarUpdater.setShowingFromDialog(true);
                this.currentAvatarUpdater.parentFragment = this.parentActivity.getActionBarLayout().getLastFragment();
                ImageUpdater imageUpdater3 = this.currentAvatarUpdater;
                AvatarUpdaterDelegate avatarUpdaterDelegate = new AvatarUpdaterDelegate(j);
                this.avatarUpdaterDelegate = avatarUpdaterDelegate;
                imageUpdater3.setDelegate(avatarUpdaterDelegate);
                ImageUpdater imageUpdater4 = this.currentAvatarUpdater;
                TLRPC.UserProfilePhoto userProfilePhoto = currentUser.photo;
                imageUpdater4.openMenu((userProfilePhoto == null || userProfilePhoto.photo_big == null || (userProfilePhoto instanceof TLRPC.TL_userProfilePhotoEmpty)) ? false : true, new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda71
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$processSelectedOption$72();
                    }
                }, new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda72
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        GroupCallActivity.$r8$lambda$8EurkvvsVUrHi4p4ZztICb4ueo0(dialogInterface);
                    }
                }, 0);
                return;
            }
            return;
        }
        if (i == 10) {
            AlertsCreator.createChangeBioAlert(groupCallParticipant.about, j, getContext(), this.currentAccount);
            return;
        }
        if (i == 11) {
            AlertsCreator.createChangeNameAlert(j, getContext(), this.currentAccount);
            return;
        }
        if (i == 5) {
            sharedInstance.editCallMember(tLObject, Boolean.TRUE, null, null, null, null);
            getUndoView().showWithAction(0L, 35, tLObject);
            sharedInstance.setParticipantVolume(groupCallParticipant, 0);
            return;
        }
        if ((groupCallParticipant.flags & 128) != 0 && groupCallParticipant.volume == 0) {
            groupCallParticipant.volume = XCallback.PRIORITY_HIGHEST;
            groupCallParticipant.volume_by_admin = false;
            sharedInstance.editCallMember(tLObject, Boolean.FALSE, null, Integer.valueOf(XCallback.PRIORITY_HIGHEST), null, null);
        } else {
            sharedInstance.editCallMember(tLObject, Boolean.FALSE, null, null, null, null);
        }
        sharedInstance.setParticipantVolume(groupCallParticipant, ChatObject.getParticipantVolume(groupCallParticipant));
        getUndoView().showWithAction(0L, i == 1 ? 31 : 36, tLObject, (Object) null, (Runnable) null, (Runnable) null);
    }

    public /* synthetic */ void lambda$processSelectedOption$69(TLObject tLObject, AlertDialog alertDialog, int i) {
        ConferenceCall conferenceCall;
        if (isConference()) {
            VoIPService sharedInstance = VoIPService.getSharedInstance();
            if (sharedInstance == null || (conferenceCall = sharedInstance.conference) == null || !(tLObject instanceof TLRPC.User)) {
                return;
            }
            TLRPC.User user = (TLRPC.User) tLObject;
            conferenceCall.kick(user.f1775id);
            this.call.addKickedUser(user.f1775id);
            getUndoView().showWithAction(0L, 102, user, (Object) null, (Runnable) null, (Runnable) null);
            return;
        }
        if (tLObject instanceof TLRPC.User) {
            TLRPC.User user2 = (TLRPC.User) tLObject;
            this.accountInstance.getMessagesController().deleteParticipantFromChat(getChatId(), user2);
            getUndoView().showWithAction(0L, 32, user2, (Object) null, (Runnable) null, (Runnable) null);
        } else {
            TLRPC.Chat chat = (TLRPC.Chat) tLObject;
            this.accountInstance.getMessagesController().deleteParticipantFromChat(getChatId(), (TLRPC.User) null, chat, false, false);
            getUndoView().showWithAction(0L, 32, chat, (Object) null, (Runnable) null, (Runnable) null);
        }
    }

    public /* synthetic */ void lambda$processSelectedOption$71(TLRPC.User user, final long j, AlertDialog alertDialog, int i) {
        this.accountInstance.getMessagesController().addUserToChat(getChatId(), user, 0, null, (BaseFragment) this.parentActivity.getActionBarLayout().getFragmentStack().get(this.parentActivity.getActionBarLayout().getFragmentStack().size() - 1), new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda76
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processSelectedOption$70(j);
            }
        });
    }

    public /* synthetic */ void lambda$processSelectedOption$70(long j) {
        inviteUserToCall(j, false);
    }

    public /* synthetic */ void lambda$processSelectedOption$72() {
        this.accountInstance.getMessagesController().deleteUserPhoto(null);
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$60 */
    /* JADX INFO: loaded from: classes6.dex */
    class ViewOnTouchListenerC560360 implements View.OnTouchListener {
        private int[] pos = new int[2];
        final /* synthetic */ Rect val$rect;

        ViewOnTouchListenerC560360(Rect rect) {
            rect = rect;
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getActionMasked() == 0) {
                if (GroupCallActivity.this.scrimPopupWindow != null && GroupCallActivity.this.scrimPopupWindow.isShowing()) {
                    View contentView = GroupCallActivity.this.scrimPopupWindow.getContentView();
                    contentView.getLocationInWindow(this.pos);
                    Rect rect = rect;
                    int[] iArr = this.pos;
                    int i = iArr[0];
                    rect.set(i, iArr[1], contentView.getMeasuredWidth() + i, this.pos[1] + contentView.getMeasuredHeight());
                    if (!rect.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                        GroupCallActivity.this.scrimPopupWindow.dismiss();
                    }
                }
            } else if (motionEvent.getActionMasked() == 4 && GroupCallActivity.this.scrimPopupWindow != null && GroupCallActivity.this.scrimPopupWindow.isShowing()) {
                GroupCallActivity.this.scrimPopupWindow.dismiss();
            }
            return false;
        }
    }

    public /* synthetic */ void lambda$showMenuForCell$74(KeyEvent keyEvent) {
        ActionBarPopupWindow actionBarPopupWindow;
        if (keyEvent.getKeyCode() == 4 && keyEvent.getRepeatCount() == 0 && (actionBarPopupWindow = this.scrimPopupWindow) != null && actionBarPopupWindow.isShowing()) {
            this.scrimPopupWindow.dismiss();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$61 */
    /* JADX INFO: loaded from: classes6.dex */
    class C560461 extends LinearLayout {
        final /* synthetic */ LinearLayout val$buttonsLayout;
        final /* synthetic */ LinearLayout val$volumeLayout;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C560461(Context context, LinearLayout linearLayout, LinearLayout linearLayout2) {
            super(context);
            linearLayout = linearLayout;
            linearLayout = linearLayout2;
        }

        @Override // android.widget.LinearLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            linearLayout.measure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(0, 0));
            LinearLayout linearLayout = linearLayout;
            if (linearLayout != null) {
                linearLayout.measure(View.MeasureSpec.makeMeasureSpec(linearLayout.getMeasuredWidth(), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(0, 0));
                setMeasuredDimension(linearLayout.getMeasuredWidth(), linearLayout.getMeasuredHeight() + linearLayout.getMeasuredHeight());
            } else {
                setMeasuredDimension(linearLayout.getMeasuredWidth(), linearLayout.getMeasuredHeight());
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$62 */
    /* JADX INFO: loaded from: classes6.dex */
    class C560562 extends ScrollView {
        final /* synthetic */ LinearLayout val$linearLayout;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C560562(Context context, AttributeSet attributeSet, int i, int i2, LinearLayout linearLayout) {
            super(context, attributeSet, i, i2);
            linearLayout = linearLayout;
        }

        @Override // android.widget.ScrollView, android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            setMeasuredDimension(linearLayout.getMeasuredWidth(), getMeasuredHeight());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:738:0x0475  */
    /* JADX WARN: Removed duplicated region for block: B:763:0x051d  */
    /* JADX WARN: Type inference failed for: r27v0 */
    /* JADX WARN: Type inference failed for: r27v1 */
    /* JADX WARN: Type inference failed for: r27v10 */
    /* JADX WARN: Type inference failed for: r27v11 */
    /* JADX WARN: Type inference failed for: r27v12 */
    /* JADX WARN: Type inference failed for: r27v2 */
    /* JADX WARN: Type inference failed for: r27v3 */
    /* JADX WARN: Type inference failed for: r27v4 */
    /* JADX WARN: Type inference failed for: r27v5 */
    /* JADX WARN: Type inference failed for: r27v6 */
    /* JADX WARN: Type inference failed for: r27v7 */
    /* JADX WARN: Type inference failed for: r27v8 */
    /* JADX WARN: Type inference failed for: r27v9 */
    /* JADX WARN: Type inference failed for: r2v17, types: [android.view.View, android.view.ViewGroup, org.telegram.ui.ActionBar.ActionBarPopupWindow$ActionBarPopupWindowLayout] */
    /* JADX WARN: Type inference failed for: r4v53, types: [android.view.View] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean showMenuForCell(android.view.View r34) {
        /*
            Method dump skipped, instruction units count: 2128
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.GroupCallActivity.showMenuForCell(android.view.View):boolean");
    }

    public /* synthetic */ void lambda$showMenuForCell$75(int i, ArrayList arrayList, TLRPC.GroupCallParticipant groupCallParticipant, View view) {
        if (i >= arrayList.size()) {
            return;
        }
        TLRPC.GroupCallParticipant groupCallParticipant2 = (TLRPC.GroupCallParticipant) this.call.participants.get(MessageObject.getPeerId(groupCallParticipant.peer));
        if (groupCallParticipant2 != null) {
            groupCallParticipant = groupCallParticipant2;
        }
        processSelectedOption(groupCallParticipant, MessageObject.getPeerId(groupCallParticipant.peer), ((Integer) arrayList.get(i)).intValue());
        ActionBarPopupWindow actionBarPopupWindow = this.scrimPopupWindow;
        if (actionBarPopupWindow != null) {
            actionBarPopupWindow.dismiss();
        } else {
            if (((Integer) arrayList.get(i)).intValue() == 9 || ((Integer) arrayList.get(i)).intValue() == 10 || ((Integer) arrayList.get(i)).intValue() == 11) {
                return;
            }
            dismissAvatarPreview(true);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$63 */
    /* JADX INFO: loaded from: classes6.dex */
    class C560663 extends ActionBarPopupWindow {
        C560663(View view, int i, int i2) {
            super(view, i, i2);
        }

        @Override // org.telegram.p026ui.ActionBar.ActionBarPopupWindow, android.widget.PopupWindow
        public void dismiss() {
            super.dismiss();
            if (GroupCallActivity.this.scrimPopupWindow != this) {
                return;
            }
            GroupCallActivity.this.scrimPopupWindow = null;
            if (GroupCallActivity.this.scrimAnimatorSet != null) {
                GroupCallActivity.this.scrimAnimatorSet.cancel();
                GroupCallActivity.this.scrimAnimatorSet = null;
            }
            GroupCallActivity.this.layoutManager.setCanScrollVertically(true);
            GroupCallActivity.this.scrimAnimatorSet = new AnimatorSet();
            ArrayList arrayList = new ArrayList();
            arrayList.add(ObjectAnimator.ofInt(GroupCallActivity.this.scrimPaint, (Property<Paint, Integer>) AnimationProperties.PAINT_ALPHA, 0));
            GroupCallActivity.this.scrimAnimatorSet.playTogether(arrayList);
            GroupCallActivity.this.scrimAnimatorSet.setDuration(220L);
            GroupCallActivity.this.scrimAnimatorSet.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.GroupCallActivity.63.1
                AnonymousClass1() {
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    GroupCallActivity.this.clearScrimView();
                    ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
                    GroupCallActivity.this.listView.invalidate();
                    if (GroupCallActivity.this.delayedGroupCallUpdated) {
                        GroupCallActivity.this.delayedGroupCallUpdated = false;
                        GroupCallActivity.this.applyCallParticipantUpdates(true);
                    }
                }
            });
            GroupCallActivity.this.scrimAnimatorSet.start();
        }

        /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$63$1 */
        class AnonymousClass1 extends AnimatorListenerAdapter {
            AnonymousClass1() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                GroupCallActivity.this.clearScrimView();
                ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
                GroupCallActivity.this.listView.invalidate();
                if (GroupCallActivity.this.delayedGroupCallUpdated) {
                    GroupCallActivity.this.delayedGroupCallUpdated = false;
                    GroupCallActivity.this.applyCallParticipantUpdates(true);
                }
            }
        }
    }

    public void clearScrimView() {
        GroupCallMiniTextureView groupCallMiniTextureView = this.scrimRenderer;
        if (groupCallMiniTextureView != null) {
            groupCallMiniTextureView.textureView.setRoundCorners(AndroidUtilities.m1081dp(8.0f));
            this.scrimRenderer.setShowingAsScrimView(false, false);
            this.scrimRenderer.invalidate();
            this.renderersContainer.invalidate();
        }
        GroupCallUserCell groupCallUserCell = this.scrimView;
        if (groupCallUserCell != null && !this.hasScrimAnchorView && groupCallUserCell.getParent() != null) {
            this.containerView.removeView(this.scrimView);
        }
        GroupCallUserCell groupCallUserCell2 = this.scrimView;
        if (groupCallUserCell2 != null) {
            groupCallUserCell2.setProgressToAvatarPreview(0.0f);
            this.scrimView.setAboutVisible(false);
            this.scrimView.getAvatarImageView().setAlpha(1.0f);
        }
        GroupCallFullscreenAdapter.GroupCallUserCell groupCallUserCell3 = this.scrimFullscreenView;
        if (groupCallUserCell3 != null) {
            groupCallUserCell3.getAvatarImageView().setAlpha(1.0f);
        }
        this.scrimView = null;
        this.scrimGridView = null;
        this.scrimFullscreenView = null;
        this.scrimRenderer = null;
    }

    private void startScreenCapture() {
        LaunchActivity launchActivity = this.parentActivity;
        if (launchActivity == null) {
            return;
        }
        this.parentActivity.startActivityForResult(((MediaProjectionManager) launchActivity.getSystemService("media_projection")).createScreenCaptureIntent(), 520);
    }

    /* JADX WARN: Removed duplicated region for block: B:172:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x0166  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void runAvatarPreviewTransition(boolean r17, org.telegram.p026ui.Cells.GroupCallUserCell r18) {
        /*
            Method dump skipped, instruction units count: 616
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.GroupCallActivity.runAvatarPreviewTransition(boolean, org.telegram.ui.Cells.GroupCallUserCell):void");
    }

    public /* synthetic */ void lambda$runAvatarPreviewTransition$76(float f, float f2, float f3, int i, ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.progressToAvatarPreview = fFloatValue;
        this.renderersContainer.progressToScrimView = fFloatValue;
        float f4 = (f * (1.0f - fFloatValue)) + (fFloatValue * 1.0f);
        this.avatarPreviewContainer.setScaleX(f4);
        this.avatarPreviewContainer.setScaleY(f4);
        this.avatarPreviewContainer.setTranslationX(f2 * (1.0f - this.progressToAvatarPreview));
        this.avatarPreviewContainer.setTranslationY(f3 * (1.0f - this.progressToAvatarPreview));
        if (!this.useBlur) {
            this.scrimPaint.setAlpha((int) (this.progressToAvatarPreview * 100.0f));
        }
        GroupCallMiniTextureView groupCallMiniTextureView = this.scrimRenderer;
        if (groupCallMiniTextureView != null) {
            groupCallMiniTextureView.textureView.setRoundCorners(AndroidUtilities.m1081dp(8.0f) * (1.0f - this.progressToAvatarPreview));
        }
        this.avatarPreviewContainer.invalidate();
        this.containerView.invalidate();
        ProfileGalleryView profileGalleryView = this.avatarsViewPager;
        float f5 = i;
        float f6 = this.progressToAvatarPreview;
        profileGalleryView.setRoundRadius((int) ((1.0f - f6) * f5), (int) (f5 * (1.0f - f6)));
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$64 */
    /* JADX INFO: loaded from: classes6.dex */
    class C560764 extends AnimatorListenerAdapter {
        final /* synthetic */ boolean val$enter;
        final /* synthetic */ GroupCallMiniTextureView val$videoRenderer;

        C560764(GroupCallMiniTextureView groupCallMiniTextureView, boolean z) {
            groupCallMiniTextureView = groupCallMiniTextureView;
            z = z;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            GroupCallMiniTextureView groupCallMiniTextureView = groupCallMiniTextureView;
            if (groupCallMiniTextureView != null) {
                groupCallMiniTextureView.animateToScrimView = false;
            }
            GroupCallActivity.this.accountInstance.getNotificationCenter().onAnimationFinish(GroupCallActivity.this.popupAnimationIndex);
            GroupCallActivity.this.avatarPriviewTransitionInProgress = false;
            GroupCallActivity.this.progressToAvatarPreview = z ? 1.0f : 0.0f;
            GroupCallActivity.this.renderersContainer.progressToScrimView = GroupCallActivity.this.progressToAvatarPreview;
            if (!z) {
                GroupCallActivity.this.scrimPaint.setAlpha(0);
                GroupCallActivity.this.clearScrimView();
                if (GroupCallActivity.this.scrimPopupLayout.getParent() != null) {
                    ((BottomSheet) GroupCallActivity.this).containerView.removeView(GroupCallActivity.this.scrimPopupLayout);
                }
                GroupCallActivity.this.scrimPopupLayout = null;
                GroupCallActivity.this.avatarPreviewContainer.setVisibility(8);
                GroupCallActivity.this.avatarsPreviewShowed = false;
                GroupCallActivity.this.layoutManager.setCanScrollVertically(true);
                GroupCallActivity.this.blurredView.setVisibility(8);
                if (GroupCallActivity.this.delayedGroupCallUpdated) {
                    GroupCallActivity.this.delayedGroupCallUpdated = false;
                    GroupCallActivity.this.applyCallParticipantUpdates(true);
                }
                if (GroupCallActivity.this.scrimRenderer != null) {
                    GroupCallActivity.this.scrimRenderer.textureView.setRoundCorners(0.0f);
                }
            } else {
                GroupCallActivity.this.avatarPreviewContainer.setAlpha(1.0f);
                GroupCallActivity.this.avatarPreviewContainer.setScaleX(1.0f);
                GroupCallActivity.this.avatarPreviewContainer.setScaleY(1.0f);
                GroupCallActivity.this.avatarPreviewContainer.setTranslationX(0.0f);
                GroupCallActivity.this.avatarPreviewContainer.setTranslationY(0.0f);
            }
            GroupCallActivity.this.checkContentOverlayed();
            ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
            GroupCallActivity.this.avatarsViewPager.invalidate();
            GroupCallActivity.this.listView.invalidate();
        }
    }

    public void dismissAvatarPreview(boolean z) {
        if (this.avatarPriviewTransitionInProgress || !this.avatarsPreviewShowed) {
            return;
        }
        if (z) {
            this.avatarPriviewTransitionInProgress = true;
            runAvatarPreviewTransition(false, this.scrimView);
            return;
        }
        clearScrimView();
        this.containerView.removeView(this.scrimPopupLayout);
        this.scrimPopupLayout = null;
        this.avatarPreviewContainer.setVisibility(8);
        this.containerView.invalidate();
        this.avatarsPreviewShowed = false;
        this.layoutManager.setCanScrollVertically(true);
        this.listView.invalidate();
        this.blurredView.setVisibility(8);
        if (this.delayedGroupCallUpdated) {
            this.delayedGroupCallUpdated = false;
            applyCallParticipantUpdates(true);
        }
        checkContentOverlayed();
    }

    /* JADX INFO: loaded from: classes6.dex */
    private class ListAdapter extends RecyclerListView.SelectionAdapter {
        private int addMemberRow;
        private int conferenceAddPeopleRow;
        private int conferenceShareLinkRow;
        private int encryptionRow;
        private boolean hasSelfUser;
        private int invitedEndRow;
        private int invitedStartRow;
        private int lastRow;
        private Context mContext;
        private int rowsCount;
        private int shadyJoinEndRow;
        private int shadyJoinStartRow;
        private int shadyLeftEndRow;
        private int shadyLeftStartRow;
        private int usersEndRow;
        private int usersStartRow;
        private int usersVideoGridEndRow;
        private int usersVideoGridStartRow;
        private int videoGridDividerRow;
        private int videoNotAvailableRow;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public boolean addSelfToCounter() {
            if (GroupCallActivity.this.isRtmpStream() || this.hasSelfUser || VoIPService.getSharedInstance() == null) {
                return false;
            }
            return !VoIPService.getSharedInstance().isJoined();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.rowsCount;
        }

        public void updateRows() {
            TLRPC.Chat chat;
            TLRPC.Chat chat2;
            ChatObject.Call call = GroupCallActivity.this.call;
            if (call == null || call.isScheduled() || GroupCallActivity.this.delayedGroupCallUpdated) {
                return;
            }
            this.addMemberRow = -1;
            this.conferenceAddPeopleRow = -1;
            this.conferenceShareLinkRow = -1;
            this.videoGridDividerRow = -1;
            this.videoNotAvailableRow = -1;
            this.encryptionRow = -1;
            this.rowsCount = 0;
            GroupCallActivity groupCallActivity = GroupCallActivity.this;
            this.hasSelfUser = groupCallActivity.call.participants.indexOfKey(MessageObject.getPeerId(groupCallActivity.selfPeer)) >= 0;
            if (GroupCallActivity.this.isConference()) {
                int i = this.rowsCount;
                this.rowsCount = i + 1;
                this.encryptionRow = i;
            }
            int i2 = this.rowsCount;
            this.usersVideoGridStartRow = i2;
            int size = i2 + GroupCallActivity.this.visibleVideoParticipants.size();
            this.rowsCount = size;
            this.usersVideoGridEndRow = size;
            if (GroupCallActivity.this.visibleVideoParticipants.size() > 0) {
                int i3 = this.rowsCount;
                this.rowsCount = i3 + 1;
                this.videoGridDividerRow = i3;
            }
            if (!GroupCallActivity.this.visibleVideoParticipants.isEmpty() && GroupCallActivity.this.canManageCall()) {
                GroupCallActivity groupCallActivity2 = GroupCallActivity.this;
                if (groupCallActivity2.call.call.participants_count > groupCallActivity2.accountInstance.getMessagesController().groupCallVideoMaxParticipants) {
                    int i4 = this.rowsCount;
                    this.rowsCount = i4 + 1;
                    this.videoNotAvailableRow = i4;
                }
            }
            this.usersStartRow = this.rowsCount;
            if (!GroupCallActivity.this.isRtmpStream()) {
                this.rowsCount += GroupCallActivity.this.call.visibleParticipants.size();
            }
            this.usersEndRow = this.rowsCount;
            if (GroupCallActivity.this.call.invitedUsers.isEmpty() || GroupCallActivity.this.isRtmpStream()) {
                this.invitedStartRow = -1;
                this.invitedEndRow = -1;
            } else {
                int i5 = this.rowsCount;
                this.invitedStartRow = i5;
                int size2 = i5 + GroupCallActivity.this.call.invitedUsers.size();
                this.rowsCount = size2;
                this.invitedEndRow = size2;
            }
            if (GroupCallActivity.this.call.shadyJoinParticipants.isEmpty() || GroupCallActivity.this.isRtmpStream()) {
                this.shadyJoinStartRow = -1;
                this.shadyJoinEndRow = -1;
            } else {
                int i6 = this.rowsCount;
                this.shadyJoinStartRow = i6;
                int size3 = i6 + GroupCallActivity.this.call.shadyJoinParticipants.size();
                this.rowsCount = size3;
                this.shadyJoinEndRow = size3;
            }
            if (GroupCallActivity.this.call.shadyLeftParticipants.isEmpty() || GroupCallActivity.this.isRtmpStream()) {
                this.shadyLeftStartRow = -1;
                this.shadyLeftEndRow = -1;
            } else {
                int i7 = this.rowsCount;
                this.shadyLeftStartRow = i7;
                int size4 = i7 + GroupCallActivity.this.call.shadyLeftParticipants.size();
                this.rowsCount = size4;
                this.shadyLeftEndRow = size4;
            }
            if (GroupCallActivity.this.isConference()) {
                int i8 = this.rowsCount;
                this.conferenceAddPeopleRow = i8;
                this.rowsCount = i8 + 2;
                this.conferenceShareLinkRow = i8 + 1;
            } else if (!GroupCallActivity.this.isRtmpStream() && (((!ChatObject.isChannel(GroupCallActivity.this.currentChat) || ((chat2 = GroupCallActivity.this.currentChat) != null && chat2.megagroup)) && ChatObject.canWriteToChat(GroupCallActivity.this.currentChat)) || (ChatObject.isChannel(GroupCallActivity.this.currentChat) && (chat = GroupCallActivity.this.currentChat) != null && !chat.megagroup && ChatObject.isPublic(chat)))) {
                int i9 = this.rowsCount;
                this.rowsCount = i9 + 1;
                this.addMemberRow = i9;
            }
            int i10 = this.rowsCount;
            this.rowsCount = i10 + 1;
            this.lastRow = i10;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void notifyDataSetChanged() {
            updateRows();
            super.notifyDataSetChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void notifyItemChanged(int i) {
            updateRows();
            super.notifyItemChanged(i);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void notifyItemChanged(int i, Object obj) {
            updateRows();
            super.notifyItemChanged(i, obj);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void notifyItemRangeChanged(int i, int i2) {
            updateRows();
            super.notifyItemRangeChanged(i, i2);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void notifyItemRangeChanged(int i, int i2, Object obj) {
            updateRows();
            super.notifyItemRangeChanged(i, i2, obj);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void notifyItemInserted(int i) {
            updateRows();
            super.notifyItemInserted(i);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void notifyItemMoved(int i, int i2) {
            updateRows();
            super.notifyItemMoved(i, i2);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void notifyItemRangeInserted(int i, int i2) {
            updateRows();
            super.notifyItemRangeInserted(i, i2);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void notifyItemRemoved(int i) {
            updateRows();
            super.notifyItemRemoved(i);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void notifyItemRangeRemoved(int i, int i2) {
            updateRows();
            super.notifyItemRangeRemoved(i, i2);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onViewAttachedToWindow(RecyclerView.ViewHolder viewHolder) {
            int itemViewType = viewHolder.getItemViewType();
            if (itemViewType == 1) {
                GroupCallUserCell groupCallUserCell = (GroupCallUserCell) viewHolder.itemView;
                int i = GroupCallActivity.this.actionBar.getTag() != null ? Theme.key_voipgroup_mutedIcon : Theme.key_voipgroup_mutedIconUnscrolled;
                groupCallUserCell.setGrayIconColor(i, Theme.getColor(i));
                groupCallUserCell.setDrawDivider(viewHolder.getAdapterPosition() != getItemCount() - 2);
                return;
            }
            if (itemViewType == 2) {
                GroupCallInvitedCell groupCallInvitedCell = (GroupCallInvitedCell) viewHolder.itemView;
                int i2 = GroupCallActivity.this.actionBar.getTag() != null ? Theme.key_voipgroup_mutedIcon : Theme.key_voipgroup_mutedIconUnscrolled;
                groupCallInvitedCell.setGrayIconColor(i2, Theme.getColor(i2));
                groupCallInvitedCell.setDrawDivider(viewHolder.getAdapterPosition() != getItemCount() - 2);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r10v3, types: [java.lang.Long] */
        /* JADX WARN: Type inference failed for: r14v0, types: [org.telegram.tgnet.TLRPC$FileLocation] */
        /* JADX WARN: Type inference failed for: r8v1, types: [org.telegram.ui.Cells.GroupCallUserCell] */
        /* JADX WARN: Type inference failed for: r8v3, types: [org.telegram.ui.Cells.GroupCallInvitedCell] */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            TLRPC.Chat chat;
            TLRPC.GroupCallParticipant groupCallParticipant;
            TLRPC.GroupCallParticipant groupCallParticipant2;
            AvatarUpdaterDelegate avatarUpdaterDelegate;
            boolean z;
            boolean z2;
            boolean z3;
            ChatObject.VideoParticipant videoParticipant;
            ChatObject.VideoParticipant videoParticipant2;
            AvatarUpdaterDelegate avatarUpdaterDelegate2;
            int itemViewType = viewHolder.getItemViewType();
            if (itemViewType == 0) {
                GroupCallTextCell groupCallTextCell = (GroupCallTextCell) viewHolder.itemView;
                if (i == this.conferenceAddPeopleRow) {
                    int color = Theme.getColor(Theme.key_windowBackgroundWhiteBlueIcon, ((BottomSheet) GroupCallActivity.this).resourcesProvider);
                    groupCallTextCell.setColors(color, color);
                    groupCallTextCell.setTextAndIcon(LocaleController.getString(C2702R.string.VoipConferenceAddPeople), C2702R.drawable.msg_contact_add, true);
                    return;
                } else {
                    if (i == this.conferenceShareLinkRow) {
                        int color2 = Theme.getColor(Theme.key_windowBackgroundWhiteBlueIcon, ((BottomSheet) GroupCallActivity.this).resourcesProvider);
                        groupCallTextCell.setColors(color2, color2);
                        groupCallTextCell.setTextAndIcon(LocaleController.getString(C2702R.string.VoipConferenceShareLink), C2702R.drawable.msg_link2, false);
                        return;
                    }
                    int offsetColor = AndroidUtilities.getOffsetColor(Theme.getColor(Theme.key_voipgroup_lastSeenTextUnscrolled), Theme.getColor(Theme.key_voipgroup_lastSeenText), GroupCallActivity.this.actionBar.getTag() != null ? 1.0f : 0.0f, 1.0f);
                    groupCallTextCell.setColors(offsetColor, offsetColor);
                    if (ChatObject.isChannel(GroupCallActivity.this.currentChat) && (chat = GroupCallActivity.this.currentChat) != null && !chat.megagroup && ChatObject.isPublic(chat)) {
                        groupCallTextCell.setTextAndIcon(LocaleController.getString(C2702R.string.VoipGroupShareLink), C2702R.drawable.msg_link, false);
                        return;
                    } else {
                        groupCallTextCell.setTextAndIcon(LocaleController.getString(C2702R.string.VoipGroupInviteMember), C2702R.drawable.msg_contact_add, false);
                        return;
                    }
                }
            }
            Object obj = null;
            if (itemViewType == 1) {
                ?? r8 = (GroupCallUserCell) viewHolder.itemView;
                int i2 = i - this.usersStartRow;
                if (GroupCallActivity.this.delayedGroupCallUpdated) {
                    if (i2 >= 0 && i2 < GroupCallActivity.this.oldParticipants.size()) {
                        groupCallParticipant = (TLRPC.GroupCallParticipant) GroupCallActivity.this.oldParticipants.get(i2);
                        groupCallParticipant2 = groupCallParticipant;
                    }
                    groupCallParticipant2 = null;
                } else {
                    if (i2 >= 0 && i2 < GroupCallActivity.this.call.visibleParticipants.size()) {
                        groupCallParticipant = GroupCallActivity.this.call.visibleParticipants.get(i2);
                        groupCallParticipant2 = groupCallParticipant;
                    }
                    groupCallParticipant2 = null;
                }
                if (groupCallParticipant2 != null) {
                    long peerId = MessageObject.getPeerId(groupCallParticipant2.peer);
                    long peerId2 = MessageObject.getPeerId(GroupCallActivity.this.selfPeer);
                    if (peerId == peerId2 && (avatarUpdaterDelegate = GroupCallActivity.this.avatarUpdaterDelegate) != null) {
                        obj = avatarUpdaterDelegate.avatar;
                    }
                    ?? r14 = obj;
                    float f = r14 != 0 ? GroupCallActivity.this.avatarUpdaterDelegate.uploadingProgress : 1.0f;
                    boolean z4 = r8.getParticipant() != null && MessageObject.getPeerId(r8.getParticipant().peer) == peerId;
                    r8.setData(GroupCallActivity.this.accountInstance, groupCallParticipant2, GroupCallActivity.this.call, peerId2, r14, z4);
                    r8.setUploadProgress(f, z4);
                    return;
                }
                return;
            }
            if (itemViewType == 2) {
                ?? r82 = (GroupCallInvitedCell) viewHolder.itemView;
                int i3 = i - this.invitedStartRow;
                int i4 = this.shadyJoinStartRow;
                if (i - i4 >= 0 && i - i4 < GroupCallActivity.this.call.shadyJoinParticipants.size()) {
                    obj = (Long) GroupCallActivity.this.call.shadyJoinParticipants.get(i - this.shadyJoinStartRow);
                    z2 = true;
                    z = false;
                    z3 = false;
                } else {
                    int i5 = this.shadyLeftStartRow;
                    if (i - i5 >= 0 && i - i5 < GroupCallActivity.this.call.shadyLeftParticipants.size()) {
                        obj = (Long) GroupCallActivity.this.call.shadyLeftParticipants.get(i - this.shadyLeftStartRow);
                        z3 = true;
                        z = false;
                        z2 = false;
                    } else {
                        if (GroupCallActivity.this.delayedGroupCallUpdated) {
                            if (i3 >= 0 && i3 < GroupCallActivity.this.oldInvited.size()) {
                                obj = (Long) GroupCallActivity.this.oldInvited.get(i3);
                            }
                        } else {
                            if (i3 >= 0 && i3 < GroupCallActivity.this.call.invitedUsers.size()) {
                                obj = (Long) GroupCallActivity.this.call.invitedUsers.get(i3);
                                ChatObject.Call.InvitedUser invitedUser = GroupCallActivity.this.call.invitedUsersMessageIds.get(obj);
                                z = invitedUser != null && invitedUser.isCalling();
                                z2 = false;
                            }
                            z3 = z2;
                        }
                        z = false;
                        z2 = false;
                        z3 = z2;
                    }
                }
                ?? r10 = obj;
                if (r10 != 0) {
                    r82.setData(((BottomSheet) GroupCallActivity.this).currentAccount, r10, z, z2, z3);
                    return;
                }
                return;
            }
            if (itemViewType != 4) {
                return;
            }
            GroupCallGridCell groupCallGridCell = (GroupCallGridCell) viewHolder.itemView;
            ChatObject.VideoParticipant participant = groupCallGridCell.getParticipant();
            int i6 = i - this.usersVideoGridStartRow;
            groupCallGridCell.spanCount = GroupCallActivity.this.spanSizeLookup.getSpanSize(i);
            if (GroupCallActivity.this.delayedGroupCallUpdated) {
                if (i6 >= 0 && i6 < GroupCallActivity.this.oldVideoParticipants.size()) {
                    videoParticipant = (ChatObject.VideoParticipant) GroupCallActivity.this.oldVideoParticipants.get(i6);
                    videoParticipant2 = videoParticipant;
                }
                videoParticipant2 = null;
            } else {
                if (i6 >= 0 && i6 < GroupCallActivity.this.visibleVideoParticipants.size()) {
                    videoParticipant = (ChatObject.VideoParticipant) GroupCallActivity.this.visibleVideoParticipants.get(i6);
                    videoParticipant2 = videoParticipant;
                }
                videoParticipant2 = null;
            }
            if (videoParticipant2 != null) {
                long peerId3 = MessageObject.getPeerId(videoParticipant2.participant.peer);
                long peerId4 = MessageObject.getPeerId(GroupCallActivity.this.selfPeer);
                if (peerId3 == peerId4 && (avatarUpdaterDelegate2 = GroupCallActivity.this.avatarUpdaterDelegate) != null) {
                    obj = avatarUpdaterDelegate2.avatar;
                }
                if (obj != null) {
                    float f2 = GroupCallActivity.this.avatarUpdaterDelegate.uploadingProgress;
                }
                if (groupCallGridCell.getParticipant() != null) {
                    groupCallGridCell.getParticipant().equals(videoParticipant2);
                }
                groupCallGridCell.setData(GroupCallActivity.this.accountInstance, videoParticipant2, GroupCallActivity.this.call, peerId4);
            }
            if (participant == null || participant.equals(videoParticipant2) || !groupCallGridCell.attached || groupCallGridCell.getRenderer() == null) {
                return;
            }
            GroupCallActivity.this.attachRenderer(groupCallGridCell, false);
            GroupCallActivity.this.attachRenderer(groupCallGridCell, true);
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            int itemViewType = viewHolder.getItemViewType();
            return (itemViewType == 3 || itemViewType == 4 || itemViewType == 5 || itemViewType == 6) ? false : true;
        }

        /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$ListAdapter$1 */
        class C56281 extends GroupCallTextCell {
            C56281(Context context) {
                super(context);
            }

            @Override // org.telegram.p026ui.Cells.GroupCallTextCell, android.widget.FrameLayout, android.view.View
            protected void onMeasure(int i, int i2) {
                if (AndroidUtilities.isTablet()) {
                    super.onMeasure(View.MeasureSpec.makeMeasureSpec(Math.min(AndroidUtilities.m1081dp(420.0f), View.MeasureSpec.getSize(i)), TLObject.FLAG_30), i2);
                } else {
                    super.onMeasure(i, i2);
                }
            }
        }

        /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$ListAdapter$2 */
        class C56292 extends GroupCallUserCell {
            C56292(Context context) {
                super(context);
            }

            @Override // org.telegram.p026ui.Cells.GroupCallUserCell
            /* JADX INFO: renamed from: onMuteClick */
            public void lambda$new$5(GroupCallUserCell groupCallUserCell) {
                GroupCallActivity.this.showMenuForCell(groupCallUserCell);
            }

            @Override // org.telegram.p026ui.Cells.GroupCallUserCell, android.widget.FrameLayout, android.view.View
            protected void onMeasure(int i, int i2) {
                if (AndroidUtilities.isTablet()) {
                    super.onMeasure(View.MeasureSpec.makeMeasureSpec(Math.min(AndroidUtilities.m1081dp(420.0f), View.MeasureSpec.getSize(i)), TLObject.FLAG_30), i2);
                } else {
                    super.onMeasure(i, i2);
                }
            }
        }

        /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$ListAdapter$3 */
        class C56303 extends GroupCallInvitedCell {
            C56303(Context context) {
                super(context);
            }

            @Override // org.telegram.p026ui.Cells.GroupCallInvitedCell, android.widget.FrameLayout, android.view.View
            protected void onMeasure(int i, int i2) {
                if (AndroidUtilities.isTablet()) {
                    super.onMeasure(View.MeasureSpec.makeMeasureSpec(Math.min(AndroidUtilities.m1081dp(420.0f), View.MeasureSpec.getSize(i)), TLObject.FLAG_30), i2);
                } else {
                    super.onMeasure(i, i2);
                }
            }
        }

        /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$ListAdapter$4 */
        class C56314 extends GroupCallGridCell {
            C56314(Context context, boolean z) {
                super(context, z);
            }

            @Override // org.telegram.p026ui.Components.voip.GroupCallGridCell, android.view.ViewGroup, android.view.View
            protected void onAttachedToWindow() {
                super.onAttachedToWindow();
                if (GroupCallActivity.this.listView.getVisibility() == 0 && GroupCallActivity.this.listViewVideoVisibility) {
                    GroupCallActivity.this.attachRenderer(this, true);
                }
            }

            @Override // org.telegram.p026ui.Components.voip.GroupCallGridCell, android.view.ViewGroup, android.view.View
            protected void onDetachedFromWindow() {
                super.onDetachedFromWindow();
                GroupCallActivity.this.attachRenderer(this, false);
            }
        }

        /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$ListAdapter$5 */
        class C56325 extends View {
            C56325(Context context) {
                super(context);
            }

            @Override // android.view.View
            protected void onMeasure(int i, int i2) {
                super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1081dp(GroupCallActivity.isLandscapeMode ? 0.0f : 8.0f), TLObject.FLAG_30));
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View c56281;
            if (i == 0) {
                c56281 = new GroupCallTextCell(this.mContext) { // from class: org.telegram.ui.GroupCallActivity.ListAdapter.1
                    C56281(Context context) {
                        super(context);
                    }

                    @Override // org.telegram.p026ui.Cells.GroupCallTextCell, android.widget.FrameLayout, android.view.View
                    protected void onMeasure(int i2, int i22) {
                        if (AndroidUtilities.isTablet()) {
                            super.onMeasure(View.MeasureSpec.makeMeasureSpec(Math.min(AndroidUtilities.m1081dp(420.0f), View.MeasureSpec.getSize(i2)), TLObject.FLAG_30), i22);
                        } else {
                            super.onMeasure(i2, i22);
                        }
                    }
                };
            } else if (i == 1) {
                c56281 = new GroupCallUserCell(this.mContext) { // from class: org.telegram.ui.GroupCallActivity.ListAdapter.2
                    C56292(Context context) {
                        super(context);
                    }

                    @Override // org.telegram.p026ui.Cells.GroupCallUserCell
                    /* JADX INFO: renamed from: onMuteClick */
                    public void lambda$new$5(GroupCallUserCell groupCallUserCell) {
                        GroupCallActivity.this.showMenuForCell(groupCallUserCell);
                    }

                    @Override // org.telegram.p026ui.Cells.GroupCallUserCell, android.widget.FrameLayout, android.view.View
                    protected void onMeasure(int i2, int i22) {
                        if (AndroidUtilities.isTablet()) {
                            super.onMeasure(View.MeasureSpec.makeMeasureSpec(Math.min(AndroidUtilities.m1081dp(420.0f), View.MeasureSpec.getSize(i2)), TLObject.FLAG_30), i22);
                        } else {
                            super.onMeasure(i2, i22);
                        }
                    }
                };
            } else if (i == 2) {
                c56281 = new GroupCallInvitedCell(this.mContext) { // from class: org.telegram.ui.GroupCallActivity.ListAdapter.3
                    C56303(Context context) {
                        super(context);
                    }

                    @Override // org.telegram.p026ui.Cells.GroupCallInvitedCell, android.widget.FrameLayout, android.view.View
                    protected void onMeasure(int i2, int i22) {
                        if (AndroidUtilities.isTablet()) {
                            super.onMeasure(View.MeasureSpec.makeMeasureSpec(Math.min(AndroidUtilities.m1081dp(420.0f), View.MeasureSpec.getSize(i2)), TLObject.FLAG_30), i22);
                        } else {
                            super.onMeasure(i2, i22);
                        }
                    }
                };
            } else if (i == 4) {
                c56281 = new GroupCallGridCell(this.mContext, false) { // from class: org.telegram.ui.GroupCallActivity.ListAdapter.4
                    C56314(Context context, boolean z) {
                        super(context, z);
                    }

                    @Override // org.telegram.p026ui.Components.voip.GroupCallGridCell, android.view.ViewGroup, android.view.View
                    protected void onAttachedToWindow() {
                        super.onAttachedToWindow();
                        if (GroupCallActivity.this.listView.getVisibility() == 0 && GroupCallActivity.this.listViewVideoVisibility) {
                            GroupCallActivity.this.attachRenderer(this, true);
                        }
                    }

                    @Override // org.telegram.p026ui.Components.voip.GroupCallGridCell, android.view.ViewGroup, android.view.View
                    protected void onDetachedFromWindow() {
                        super.onDetachedFromWindow();
                        GroupCallActivity.this.attachRenderer(this, false);
                    }
                };
            } else if (i == 5) {
                c56281 = new View(this.mContext) { // from class: org.telegram.ui.GroupCallActivity.ListAdapter.5
                    C56325(Context context) {
                        super(context);
                    }

                    @Override // android.view.View
                    protected void onMeasure(int i2, int i22) {
                        super.onMeasure(i2, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1081dp(GroupCallActivity.isLandscapeMode ? 0.0f : 8.0f), TLObject.FLAG_30));
                    }
                };
            } else if (i == 6) {
                TextView textView = new TextView(this.mContext);
                textView.setTextColor(-8682615);
                textView.setTextSize(1, 13.0f);
                textView.setGravity(1);
                textView.setPadding(0, 0, 0, AndroidUtilities.m1081dp(10.0f));
                if (ChatObject.isChannelOrGiga(GroupCallActivity.this.currentChat)) {
                    textView.setText(LocaleController.formatString(C2702R.string.VoipChannelVideoNotAvailableAdmin, LocaleController.formatPluralString("Participants", GroupCallActivity.this.accountInstance.getMessagesController().groupCallVideoMaxParticipants, new Object[0])));
                } else {
                    textView.setText(LocaleController.formatString(C2702R.string.VoipVideoNotAvailableAdmin, LocaleController.formatPluralString("Members", GroupCallActivity.this.accountInstance.getMessagesController().groupCallVideoMaxParticipants, new Object[0])));
                }
                c56281 = textView;
            } else if (i == 7) {
                if (GroupCallActivity.this.encryptionDrawable == null) {
                    GroupCallActivity.this.encryptionDrawable = new CallEncryptionCellDrawable(this.mContext);
                }
                c56281 = new CallEncryptionCell(this.mContext, GroupCallActivity.this.encryptionDrawable);
            } else {
                c56281 = new View(this.mContext);
            }
            c56281.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            return new RecyclerListView.Holder(c56281);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            if (i == this.lastRow) {
                return 3;
            }
            if (i == this.addMemberRow || i == this.conferenceAddPeopleRow || i == this.conferenceShareLinkRow) {
                return 0;
            }
            if (i == this.videoGridDividerRow) {
                return 5;
            }
            if (i >= this.usersStartRow && i < this.usersEndRow) {
                return 1;
            }
            if (i >= this.usersVideoGridStartRow && i < this.usersVideoGridEndRow) {
                return 4;
            }
            if (i == this.videoNotAvailableRow) {
                return 6;
            }
            return i == this.encryptionRow ? 7 : 2;
        }
    }

    public void attachRenderer(GroupCallGridCell groupCallGridCell, boolean z) {
        if (isDismissed()) {
            return;
        }
        if (z && groupCallGridCell.getRenderer() == null) {
            groupCallGridCell.setRenderer(GroupCallMiniTextureView.getOrCreate(this.attachedRenderers, this.renderersContainer, groupCallGridCell, null, null, groupCallGridCell.getParticipant(), this.call, this));
        } else {
            if (z || groupCallGridCell.getRenderer() == null) {
                return;
            }
            groupCallGridCell.getRenderer().setPrimaryView(null);
            groupCallGridCell.setRenderer(null);
        }
    }

    public void setOldRows(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16) {
        this.oldEncryptionRow = i;
        this.oldAddMemberRow = i2;
        this.oldUsersStartRow = i3;
        this.oldUsersEndRow = i4;
        this.oldInvitedStartRow = i5;
        this.oldInvitedEndRow = i6;
        this.oldShadyJoinStartRow = i7;
        this.oldShadyJoinEndRow = i8;
        this.oldShadyLeftStartRow = i9;
        this.oldShadyLeftEndRow = i10;
        this.oldUsersVideoStartRow = i11;
        this.oldUsersVideoEndRow = i12;
        this.oldVideoDividerRow = i13;
        this.oldVideoNotAvailableRow = i14;
        this.oldConferenceAddPeopleRow = i15;
        this.oldConferenceShareLinkRow = i16;
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$65 */
    /* JADX INFO: loaded from: classes6.dex */
    class C560865 extends DiffUtil.Callback {
        @Override // androidx.recyclerview.widget.DiffUtil.Callback
        public boolean areContentsTheSame(int i3, int i4) {
            return true;
        }

        C560865() {
        }

        @Override // androidx.recyclerview.widget.DiffUtil.Callback
        public int getOldListSize() {
            return GroupCallActivity.this.oldCount;
        }

        @Override // androidx.recyclerview.widget.DiffUtil.Callback
        public int getNewListSize() {
            return GroupCallActivity.this.listAdapter.rowsCount;
        }

        @Override // androidx.recyclerview.widget.DiffUtil.Callback
        public boolean areItemsTheSame(int i3, int i4) {
            if (GroupCallActivity.this.listAdapter.addMemberRow >= 0) {
                if (i3 == GroupCallActivity.this.oldAddMemberRow && i4 == GroupCallActivity.this.listAdapter.addMemberRow) {
                    return true;
                }
                if ((i3 == GroupCallActivity.this.oldAddMemberRow && i4 != GroupCallActivity.this.listAdapter.addMemberRow) || (i3 != GroupCallActivity.this.oldAddMemberRow && i4 == GroupCallActivity.this.listAdapter.addMemberRow)) {
                    return false;
                }
            }
            if (GroupCallActivity.this.listAdapter.conferenceAddPeopleRow >= 0) {
                if (i3 == GroupCallActivity.this.oldConferenceAddPeopleRow && i4 == GroupCallActivity.this.listAdapter.conferenceAddPeopleRow) {
                    return true;
                }
                if ((i3 == GroupCallActivity.this.oldConferenceAddPeopleRow && i4 != GroupCallActivity.this.listAdapter.conferenceAddPeopleRow) || (i3 != GroupCallActivity.this.oldConferenceAddPeopleRow && i4 == GroupCallActivity.this.listAdapter.conferenceAddPeopleRow)) {
                    return false;
                }
            }
            if (GroupCallActivity.this.listAdapter.conferenceShareLinkRow >= 0) {
                if (i3 == GroupCallActivity.this.oldConferenceShareLinkRow && i4 == GroupCallActivity.this.listAdapter.conferenceShareLinkRow) {
                    return true;
                }
                if ((i3 == GroupCallActivity.this.oldConferenceShareLinkRow && i4 != GroupCallActivity.this.listAdapter.conferenceShareLinkRow) || (i3 != GroupCallActivity.this.oldConferenceShareLinkRow && i4 == GroupCallActivity.this.listAdapter.conferenceShareLinkRow)) {
                    return false;
                }
            }
            if (GroupCallActivity.this.listAdapter.encryptionRow >= 0) {
                if (i3 == GroupCallActivity.this.oldEncryptionRow && i4 == GroupCallActivity.this.listAdapter.encryptionRow) {
                    return true;
                }
                if ((i3 == GroupCallActivity.this.oldEncryptionRow && i4 != GroupCallActivity.this.listAdapter.encryptionRow) || (i3 != GroupCallActivity.this.oldEncryptionRow && i4 == GroupCallActivity.this.listAdapter.encryptionRow)) {
                    return false;
                }
            }
            if (GroupCallActivity.this.listAdapter.videoNotAvailableRow >= 0) {
                if (i3 == GroupCallActivity.this.oldVideoNotAvailableRow && i4 == GroupCallActivity.this.listAdapter.videoNotAvailableRow) {
                    return true;
                }
                if ((i3 == GroupCallActivity.this.oldVideoNotAvailableRow && i4 != GroupCallActivity.this.listAdapter.videoNotAvailableRow) || (i3 != GroupCallActivity.this.oldVideoNotAvailableRow && i4 == GroupCallActivity.this.listAdapter.videoNotAvailableRow)) {
                    return false;
                }
            }
            if (GroupCallActivity.this.listAdapter.videoGridDividerRow >= 0 && GroupCallActivity.this.listAdapter.videoGridDividerRow == i4 && i3 == GroupCallActivity.this.oldVideoDividerRow) {
                return true;
            }
            if (i3 == GroupCallActivity.this.oldCount - 1 && i4 == GroupCallActivity.this.listAdapter.rowsCount - 1) {
                return true;
            }
            if (i3 != GroupCallActivity.this.oldCount - 1 && i4 != GroupCallActivity.this.listAdapter.rowsCount - 1) {
                if (i4 >= GroupCallActivity.this.listAdapter.usersVideoGridStartRow && i4 < GroupCallActivity.this.listAdapter.usersVideoGridEndRow && i3 >= GroupCallActivity.this.oldUsersVideoStartRow && i3 < GroupCallActivity.this.oldUsersVideoEndRow) {
                    ChatObject.VideoParticipant videoParticipant = (ChatObject.VideoParticipant) GroupCallActivity.this.oldVideoParticipants.get(i3 - GroupCallActivity.this.oldUsersVideoStartRow);
                    GroupCallActivity groupCallActivity = GroupCallActivity.this;
                    return videoParticipant.equals((ChatObject.VideoParticipant) groupCallActivity.visibleVideoParticipants.get(i4 - groupCallActivity.listAdapter.usersVideoGridStartRow));
                }
                if (i4 >= GroupCallActivity.this.listAdapter.usersStartRow && i4 < GroupCallActivity.this.listAdapter.usersEndRow && i3 >= GroupCallActivity.this.oldUsersStartRow && i3 < GroupCallActivity.this.oldUsersEndRow) {
                    TLRPC.GroupCallParticipant groupCallParticipant = (TLRPC.GroupCallParticipant) GroupCallActivity.this.oldParticipants.get(i3 - GroupCallActivity.this.oldUsersStartRow);
                    GroupCallActivity groupCallActivity2 = GroupCallActivity.this;
                    return MessageObject.getPeerId(groupCallParticipant.peer) == MessageObject.getPeerId(groupCallActivity2.call.visibleParticipants.get(i4 - groupCallActivity2.listAdapter.usersStartRow).peer) && (i3 == i4 || groupCallParticipant.lastActiveDate == ((long) groupCallParticipant.active_date));
                }
                if (i4 >= GroupCallActivity.this.listAdapter.invitedStartRow && i4 < GroupCallActivity.this.listAdapter.invitedEndRow && i3 >= GroupCallActivity.this.oldInvitedStartRow && i3 < GroupCallActivity.this.oldInvitedEndRow) {
                    Long l = (Long) GroupCallActivity.this.oldInvited.get(i3 - GroupCallActivity.this.oldInvitedStartRow);
                    GroupCallActivity groupCallActivity3 = GroupCallActivity.this;
                    return l.equals(groupCallActivity3.call.invitedUsers.get(i4 - groupCallActivity3.listAdapter.invitedStartRow));
                }
                if (i4 >= GroupCallActivity.this.listAdapter.shadyJoinStartRow && i4 < GroupCallActivity.this.listAdapter.shadyJoinEndRow && i3 >= GroupCallActivity.this.oldShadyJoinStartRow && i3 < GroupCallActivity.this.oldShadyJoinEndRow) {
                    Long l2 = (Long) GroupCallActivity.this.oldShadyJoin.get(i3 - GroupCallActivity.this.oldShadyJoinStartRow);
                    GroupCallActivity groupCallActivity4 = GroupCallActivity.this;
                    return l2.equals(groupCallActivity4.call.shadyJoinParticipants.get(i4 - groupCallActivity4.listAdapter.shadyJoinStartRow));
                }
                if (i4 >= GroupCallActivity.this.listAdapter.shadyLeftStartRow && i4 < GroupCallActivity.this.listAdapter.shadyLeftEndRow && i3 >= GroupCallActivity.this.oldShadyLeftStartRow && i3 < GroupCallActivity.this.oldShadyLeftEndRow) {
                    Long l3 = (Long) GroupCallActivity.this.oldShadyLeft.get(i3 - GroupCallActivity.this.oldShadyLeftStartRow);
                    GroupCallActivity groupCallActivity5 = GroupCallActivity.this;
                    return l3.equals(groupCallActivity5.call.shadyLeftParticipants.get(i4 - groupCallActivity5.listAdapter.shadyLeftStartRow));
                }
            }
            return false;
        }
    }

    /* JADX INFO: loaded from: classes6.dex */
    private static class UpdateCallback implements ListUpdateCallback {
        final RecyclerView.Adapter adapter;

        /* synthetic */ UpdateCallback(RecyclerView.Adapter adapter, GroupCallActivityIA groupCallActivityIA) {
            this(adapter);
        }

        private UpdateCallback(RecyclerView.Adapter adapter) {
            this.adapter = adapter;
        }

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onInserted(int i, int i2) {
            this.adapter.notifyItemRangeInserted(i, i2);
        }

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onRemoved(int i, int i2) {
            this.adapter.notifyItemRangeRemoved(i, i2);
        }

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onMoved(int i, int i2) {
            this.adapter.notifyItemMoved(i, i2);
        }

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onChanged(int i, int i2, Object obj) {
            this.adapter.notifyItemRangeChanged(i, i2, obj);
        }
    }

    public void toggleAdminSpeak() {
        ChatObject.Call call = this.call;
        if (call == null || call.call == null) {
            return;
        }
        TL_phone.toggleGroupCallSettings togglegroupcallsettings = new TL_phone.toggleGroupCallSettings();
        togglegroupcallsettings.call = this.call.getInputGroupCall();
        togglegroupcallsettings.join_muted = Boolean.valueOf(this.call.call.join_muted);
        ConnectionsManager connectionsManager = this.accountInstance.getConnectionsManager();
        DispatchQueue dispatchQueue = Utilities.stageQueue;
        Objects.requireNonNull(dispatchQueue);
        connectionsManager.sendRequestTyped(togglegroupcallsettings, new ChatThemeController$$ExternalSyntheticLambda8(dispatchQueue), new Utilities.Callback2() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda56
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                this.f$0.lambda$toggleAdminSpeak$77((TLRPC.Updates) obj, (TLRPC.TL_error) obj2);
            }
        });
    }

    public /* synthetic */ void lambda$toggleAdminSpeak$77(TLRPC.Updates updates, TLRPC.TL_error tL_error) {
        if (updates != null) {
            this.accountInstance.getMessagesController().processUpdates(updates, false);
        }
    }

    public void setCommentsEnabled(boolean z) {
        if (this.call == null) {
            return;
        }
        TL_phone.toggleGroupCallSettings togglegroupcallsettings = new TL_phone.toggleGroupCallSettings();
        togglegroupcallsettings.call = this.call.getInputGroupCall();
        togglegroupcallsettings.messages_enabled = Boolean.valueOf(z);
        this.pendingCommentsEnabled = Boolean.valueOf(z);
        updateButtonsVisibility(true);
        ConnectionsManager connectionsManager = this.accountInstance.getConnectionsManager();
        DispatchQueue dispatchQueue = Utilities.stageQueue;
        Objects.requireNonNull(dispatchQueue);
        connectionsManager.sendRequestTyped(togglegroupcallsettings, new ChatThemeController$$ExternalSyntheticLambda8(dispatchQueue), new Utilities.Callback2() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda55
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                this.f$0.lambda$setCommentsEnabled$79((TLRPC.Updates) obj, (TLRPC.TL_error) obj2);
            }
        });
    }

    public /* synthetic */ void lambda$setCommentsEnabled$79(TLRPC.Updates updates, TLRPC.TL_error tL_error) {
        if (updates != null) {
            this.accountInstance.getMessagesController().processUpdates(updates, false);
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda60
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setCommentsEnabled$78();
            }
        });
    }

    public /* synthetic */ void lambda$setCommentsEnabled$78() {
        this.pendingCommentsEnabled = null;
        updateButtonsVisibility(true);
    }

    @Override // org.telegram.p026ui.ActionBar.BottomSheet
    public ArrayList getThemeDescriptions() {
        return new ArrayList();
    }

    @Override // org.telegram.p026ui.ActionBar.BottomSheet, android.app.Dialog
    /* JADX INFO: renamed from: onBackPressed */
    public void lambda$openCrafting$9() {
        PrivateVideoPreviewDialog privateVideoPreviewDialog = this.previewDialog;
        if (privateVideoPreviewDialog != null) {
            privateVideoPreviewDialog.dismiss(false, false);
            return;
        }
        if (this.avatarsPreviewShowed) {
            dismissAvatarPreview(true);
        } else if (this.renderersContainer.inFullscreenMode) {
            fullscreenFor(null);
        } else {
            super.lambda$openCrafting$9();
        }
    }

    /* JADX INFO: loaded from: classes6.dex */
    class AvatarUpdaterDelegate implements ImageUpdater.ImageUpdaterDelegate {
        private TLRPC.FileLocation avatar;
        private TLRPC.FileLocation avatarBig;
        private final long peerId;
        private ImageLocation uploadingImageLocation;
        public float uploadingProgress;

        /* synthetic */ AvatarUpdaterDelegate(GroupCallActivity groupCallActivity, long j, GroupCallActivityIA groupCallActivityIA) {
            this(j);
        }

        @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
        public /* synthetic */ boolean canFinishFragment() {
            return ImageUpdater.ImageUpdaterDelegate.CC.$default$canFinishFragment(this);
        }

        @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
        public void didStartUpload(boolean z, boolean z2) {
        }

        @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
        public /* synthetic */ void didUploadFailed() {
            ImageUpdater.ImageUpdaterDelegate.CC.$default$didUploadFailed(this);
        }

        @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
        public /* synthetic */ PhotoViewer.PlaceProviderObject getCloseIntoObject() {
            return ImageUpdater.ImageUpdaterDelegate.CC.$default$getCloseIntoObject(this);
        }

        @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
        public /* synthetic */ String getInitialSearchString() {
            return ImageUpdater.ImageUpdaterDelegate.CC.$default$getInitialSearchString(this);
        }

        @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
        public /* synthetic */ boolean supportsBulletin() {
            return ImageUpdater.ImageUpdaterDelegate.CC.$default$supportsBulletin(this);
        }

        private AvatarUpdaterDelegate(long j) {
            this.peerId = j;
        }

        @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
        public void didUploadPhoto(final TLRPC.InputFile inputFile, final TLRPC.InputFile inputFile2, final double d, final String str, final TLRPC.PhotoSize photoSize, final TLRPC.PhotoSize photoSize2, boolean z, final TLRPC.VideoSize videoSize) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$AvatarUpdaterDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$didUploadPhoto$3(inputFile, inputFile2, videoSize, d, str, photoSize2, photoSize);
                }
            });
        }

        public /* synthetic */ void lambda$didUploadPhoto$1(final String str, final TLObject tLObject, final TLRPC.TL_error tL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$AvatarUpdaterDelegate$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$didUploadPhoto$0(tL_error, tLObject, str);
                }
            });
        }

        public /* synthetic */ void lambda$didUploadPhoto$0(TLRPC.TL_error tL_error, TLObject tLObject, String str) {
            if (this.uploadingImageLocation != null) {
                GroupCallActivity.this.avatarsViewPager.removeUploadingImage(this.uploadingImageLocation);
                this.uploadingImageLocation = null;
            }
            if (tL_error == null) {
                TLRPC.User user = GroupCallActivity.this.accountInstance.getMessagesController().getUser(Long.valueOf(GroupCallActivity.this.accountInstance.getUserConfig().getClientUserId()));
                if (user == null) {
                    user = GroupCallActivity.this.accountInstance.getUserConfig().getCurrentUser();
                    if (user == null) {
                        return;
                    } else {
                        GroupCallActivity.this.accountInstance.getMessagesController().putUser(user, false);
                    }
                } else {
                    GroupCallActivity.this.accountInstance.getUserConfig().setCurrentUser(user);
                }
                TLRPC.TL_photos_photo tL_photos_photo = (TLRPC.TL_photos_photo) tLObject;
                ArrayList arrayList = tL_photos_photo.photo.sizes;
                TLRPC.PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(arrayList, 150);
                TLRPC.PhotoSize closestPhotoSizeWithSize2 = FileLoader.getClosestPhotoSizeWithSize(arrayList, 800);
                TLRPC.VideoSize videoSize = tL_photos_photo.photo.video_sizes.isEmpty() ? null : (TLRPC.VideoSize) tL_photos_photo.photo.video_sizes.get(0);
                TLRPC.TL_userProfilePhoto tL_userProfilePhoto = new TLRPC.TL_userProfilePhoto();
                user.photo = tL_userProfilePhoto;
                tL_userProfilePhoto.photo_id = tL_photos_photo.photo.f1642id;
                if (closestPhotoSizeWithSize != null) {
                    tL_userProfilePhoto.photo_small = closestPhotoSizeWithSize.location;
                }
                if (closestPhotoSizeWithSize2 != null) {
                    tL_userProfilePhoto.photo_big = closestPhotoSizeWithSize2.location;
                }
                if (closestPhotoSizeWithSize != null && this.avatar != null) {
                    FileLoader.getInstance(((BottomSheet) GroupCallActivity.this).currentAccount).getPathToAttach(this.avatar, true).renameTo(FileLoader.getInstance(((BottomSheet) GroupCallActivity.this).currentAccount).getPathToAttach(closestPhotoSizeWithSize, true));
                    ImageLoader.getInstance().replaceImageInCache(this.avatar.volume_id + "_" + this.avatar.local_id + "@50_50", closestPhotoSizeWithSize.location.volume_id + "_" + closestPhotoSizeWithSize.location.local_id + "@50_50", ImageLocation.getForUser(user, 1), false);
                }
                if (closestPhotoSizeWithSize2 != null && this.avatarBig != null) {
                    FileLoader.getInstance(((BottomSheet) GroupCallActivity.this).currentAccount).getPathToAttach(this.avatarBig, true).renameTo(FileLoader.getInstance(((BottomSheet) GroupCallActivity.this).currentAccount).getPathToAttach(closestPhotoSizeWithSize2, true));
                }
                if (videoSize != null && str != null) {
                    new File(str).renameTo(FileLoader.getInstance(((BottomSheet) GroupCallActivity.this).currentAccount).getPathToAttach(videoSize, "mp4", true));
                }
                GroupCallActivity.this.accountInstance.getMessagesController().getDialogPhotos(user.f1775id).reset();
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(user);
                GroupCallActivity.this.accountInstance.getMessagesStorage().putUsersAndChats(arrayList2, null, false, true);
                TLRPC.User user2 = GroupCallActivity.this.accountInstance.getMessagesController().getUser(Long.valueOf(this.peerId));
                ImageLocation forUser = ImageLocation.getForUser(user2, 0);
                ImageLocation forUser2 = ImageLocation.getForUser(user2, 1);
                if (ImageLocation.getForLocal(this.avatarBig) == null) {
                    forUser2 = ImageLocation.getForLocal(this.avatar);
                }
                GroupCallActivity.this.avatarsViewPager.setCreateThumbFromParent(false);
                GroupCallActivity.this.avatarsViewPager.initIfEmpty(null, forUser, forUser2, true);
                this.avatar = null;
                this.avatarBig = null;
                AndroidUtilities.updateVisibleRows(GroupCallActivity.this.listView);
                updateAvatarUploadingProgress(1.0f);
            }
            GroupCallActivity.this.accountInstance.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.updateInterfaces, Integer.valueOf(MessagesController.UPDATE_MASK_ALL));
            GroupCallActivity.this.accountInstance.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.mainUserInfoChanged, new Object[0]);
            GroupCallActivity.this.accountInstance.getUserConfig().saveConfig(true);
        }

        public /* synthetic */ void lambda$didUploadPhoto$2() {
            if (this.uploadingImageLocation != null) {
                GroupCallActivity.this.avatarsViewPager.removeUploadingImage(this.uploadingImageLocation);
                this.uploadingImageLocation = null;
            }
            TLRPC.Chat chat = GroupCallActivity.this.accountInstance.getMessagesController().getChat(Long.valueOf(-this.peerId));
            ImageLocation forChat = ImageLocation.getForChat(chat, 0);
            ImageLocation forChat2 = ImageLocation.getForChat(chat, 1);
            if (ImageLocation.getForLocal(this.avatarBig) == null) {
                forChat2 = ImageLocation.getForLocal(this.avatar);
            }
            GroupCallActivity.this.avatarsViewPager.setCreateThumbFromParent(false);
            GroupCallActivity.this.avatarsViewPager.initIfEmpty(null, forChat, forChat2, true);
            this.avatar = null;
            this.avatarBig = null;
            AndroidUtilities.updateVisibleRows(GroupCallActivity.this.listView);
            updateAvatarUploadingProgress(1.0f);
        }

        public /* synthetic */ void lambda$didUploadPhoto$3(TLRPC.InputFile inputFile, TLRPC.InputFile inputFile2, TLRPC.VideoSize videoSize, double d, final String str, TLRPC.PhotoSize photoSize, TLRPC.PhotoSize photoSize2) {
            if (inputFile == null && inputFile2 == null && videoSize == null) {
                this.avatar = photoSize.location;
                TLRPC.FileLocation fileLocation = photoSize2.location;
                this.avatarBig = fileLocation;
                this.uploadingImageLocation = ImageLocation.getForLocal(fileLocation);
                GroupCallActivity.this.avatarsViewPager.addUploadingImage(this.uploadingImageLocation, ImageLocation.getForLocal(this.avatar));
                AndroidUtilities.updateVisibleRows(GroupCallActivity.this.listView);
                return;
            }
            if (this.peerId > 0) {
                TLRPC.TL_photos_uploadProfilePhoto tL_photos_uploadProfilePhoto = new TLRPC.TL_photos_uploadProfilePhoto();
                if (inputFile != null) {
                    tL_photos_uploadProfilePhoto.file = inputFile;
                    tL_photos_uploadProfilePhoto.flags |= 1;
                }
                if (inputFile2 != null) {
                    tL_photos_uploadProfilePhoto.video = inputFile2;
                    int i = tL_photos_uploadProfilePhoto.flags;
                    tL_photos_uploadProfilePhoto.video_start_ts = d;
                    tL_photos_uploadProfilePhoto.flags = i | 6;
                }
                if (videoSize != null) {
                    tL_photos_uploadProfilePhoto.video_emoji_markup = videoSize;
                    tL_photos_uploadProfilePhoto.flags |= 16;
                }
                GroupCallActivity.this.accountInstance.getConnectionsManager().sendRequest(tL_photos_uploadProfilePhoto, new RequestDelegate() { // from class: org.telegram.ui.GroupCallActivity$AvatarUpdaterDelegate$$ExternalSyntheticLambda1
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                        this.f$0.lambda$didUploadPhoto$1(str, tLObject, tL_error);
                    }
                });
                return;
            }
            GroupCallActivity.this.accountInstance.getMessagesController().changeChatAvatar(-this.peerId, null, inputFile, inputFile2, videoSize, d, str, photoSize.location, photoSize2.location, new Runnable() { // from class: org.telegram.ui.GroupCallActivity$AvatarUpdaterDelegate$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$didUploadPhoto$2();
                }
            });
        }

        @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
        public void onUploadProgressChanged(float f) {
            GroupCallActivity.this.avatarsViewPager.setUploadProgress(this.uploadingImageLocation, f);
            updateAvatarUploadingProgress(f);
        }

        public void updateAvatarUploadingProgress(float f) {
            this.uploadingProgress = f;
            if (GroupCallActivity.this.listView == null) {
                return;
            }
            for (int i = 0; i < GroupCallActivity.this.listView.getChildCount(); i++) {
                View childAt = GroupCallActivity.this.listView.getChildAt(i);
                if (childAt instanceof GroupCallUserCell) {
                    GroupCallUserCell groupCallUserCell = (GroupCallUserCell) childAt;
                    if (groupCallUserCell.isSelfUser()) {
                        groupCallUserCell.setUploadProgress(f, true);
                    }
                }
            }
        }
    }

    public View getScrimView() {
        return this.scrimView;
    }

    @Override // org.telegram.messenger.voip.VoIPService.StateListener
    public void onCameraSwitch(boolean z) {
        this.attachedRenderersTmp.clear();
        this.attachedRenderersTmp.addAll(this.attachedRenderers);
        for (int i = 0; i < this.attachedRenderersTmp.size(); i++) {
            ((GroupCallMiniTextureView) this.attachedRenderersTmp.get(i)).updateAttachState(true);
        }
        PrivateVideoPreviewDialog privateVideoPreviewDialog = this.previewDialog;
        if (privateVideoPreviewDialog != null) {
            privateVideoPreviewDialog.update();
        }
    }

    /* JADX INFO: loaded from: classes6.dex */
    class GroupCallItemAnimator extends DefaultItemAnimator {
        HashSet addingHolders;
        public float animationProgress;
        public ValueAnimator animator;
        float outMaxBottom;
        float outMinTop;
        HashSet removingHolders;

        /* synthetic */ GroupCallItemAnimator(GroupCallActivity groupCallActivity, GroupCallActivityIA groupCallActivityIA) {
            this();
        }

        private GroupCallItemAnimator() {
            this.addingHolders = new HashSet();
            this.removingHolders = new HashSet();
        }

        @Override // androidx.recyclerview.widget.DefaultItemAnimator, androidx.recyclerview.widget.RecyclerView.ItemAnimator
        public void endAnimations() {
            super.endAnimations();
            this.removingHolders.clear();
            this.addingHolders.clear();
            this.outMinTop = Float.MAX_VALUE;
            GroupCallActivity.this.listView.invalidate();
        }

        public void updateBackgroundBeforeAnimation() {
            if (this.animator != null) {
                return;
            }
            this.addingHolders.clear();
            this.addingHolders.addAll(this.mPendingAdditions);
            this.removingHolders.clear();
            this.removingHolders.addAll(this.mPendingRemovals);
            this.outMaxBottom = 0.0f;
            this.outMinTop = Float.MAX_VALUE;
            if (this.addingHolders.isEmpty() && this.removingHolders.isEmpty()) {
                return;
            }
            int childCount = GroupCallActivity.this.listView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = GroupCallActivity.this.listView.getChildAt(i);
                RecyclerView.ViewHolder viewHolderFindContainingViewHolder = GroupCallActivity.this.listView.findContainingViewHolder(childAt);
                if (viewHolderFindContainingViewHolder != null && viewHolderFindContainingViewHolder.getItemViewType() != 3 && viewHolderFindContainingViewHolder.getItemViewType() != 4 && viewHolderFindContainingViewHolder.getItemViewType() != 5 && viewHolderFindContainingViewHolder.getItemViewType() != 7 && !this.addingHolders.contains(viewHolderFindContainingViewHolder)) {
                    this.outMaxBottom = Math.max(this.outMaxBottom, childAt.getY() + childAt.getMeasuredHeight());
                    this.outMinTop = Math.min(this.outMinTop, Math.max(0.0f, childAt.getY()));
                }
            }
            this.animationProgress = 0.0f;
            GroupCallActivity.this.listView.invalidate();
        }

        @Override // androidx.recyclerview.widget.DefaultItemAnimator, androidx.recyclerview.widget.RecyclerView.ItemAnimator
        public void runPendingAnimations() {
            boolean zIsEmpty = this.mPendingRemovals.isEmpty();
            boolean zIsEmpty2 = this.mPendingMoves.isEmpty();
            boolean zIsEmpty3 = this.mPendingAdditions.isEmpty();
            ValueAnimator valueAnimator = this.animator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
                this.animator = null;
            }
            if (!zIsEmpty || !zIsEmpty2 || !zIsEmpty3) {
                this.animationProgress = 0.0f;
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                this.animator = valueAnimatorOfFloat;
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.GroupCallActivity$GroupCallItemAnimator$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        this.f$0.lambda$runPendingAnimations$0(valueAnimator2);
                    }
                });
                this.animator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.GroupCallActivity.GroupCallItemAnimator.1
                    C56271() {
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        GroupCallItemAnimator groupCallItemAnimator = GroupCallItemAnimator.this;
                        groupCallItemAnimator.animator = null;
                        GroupCallActivity.this.listView.invalidate();
                        GroupCallActivity.this.renderersContainer.invalidate();
                        ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
                        GroupCallActivity.this.updateLayout(true);
                        GroupCallItemAnimator.this.addingHolders.clear();
                        GroupCallItemAnimator.this.removingHolders.clear();
                    }
                });
                this.animator.setDuration(350L);
                this.animator.setInterpolator(CubicBezierInterpolator.DEFAULT);
                this.animator.start();
                GroupCallActivity.this.listView.invalidate();
                GroupCallActivity.this.renderersContainer.invalidate();
            }
            super.runPendingAnimations();
        }

        public /* synthetic */ void lambda$runPendingAnimations$0(ValueAnimator valueAnimator) {
            this.animationProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            GroupCallActivity.this.listView.invalidate();
            GroupCallActivity.this.renderersContainer.invalidate();
            ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
            GroupCallActivity.this.updateLayout(true);
        }

        /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$GroupCallItemAnimator$1 */
        class C56271 extends AnimatorListenerAdapter {
            C56271() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                GroupCallItemAnimator groupCallItemAnimator = GroupCallItemAnimator.this;
                groupCallItemAnimator.animator = null;
                GroupCallActivity.this.listView.invalidate();
                GroupCallActivity.this.renderersContainer.invalidate();
                ((BottomSheet) GroupCallActivity.this).containerView.invalidate();
                GroupCallActivity.this.updateLayout(true);
                GroupCallItemAnimator.this.addingHolders.clear();
                GroupCallItemAnimator.this.removingHolders.clear();
            }
        }
    }

    @Override // org.telegram.p026ui.ActionBar.BottomSheet
    protected boolean canDismissWithTouchOutside() {
        return !this.renderersContainer.inFullscreenMode;
    }

    public void onUserLeaveHint() {
        if (isRtmpStream() && AndroidUtilities.checkInlinePermissions(this.parentActivity) && !RTMPStreamPipOverlay.isVisible()) {
            lambda$new$0();
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda43
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onUserLeaveHint$80();
                }
            }, 100L);
        }
    }

    public /* synthetic */ void lambda$onUserLeaveHint$80() {
        RTMPStreamPipOverlay.show(this.parentActivity);
    }

    @Override // org.telegram.p026ui.ActionBar.BottomSheet, android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.parentActivity.addOnUserLeaveHintListener(this.onUserLeaveHintListener);
    }

    public void onResume() {
        paused = false;
        this.listAdapter.notifyDataSetChanged();
        if (this.fullscreenUsersListView.getVisibility() == 0) {
            this.fullscreenAdapter.update(false, this.fullscreenUsersListView);
        }
        if (isTabletMode) {
            this.tabletGridAdapter.update(false, this.tabletVideoGridView);
        }
        this.attachedRenderersTmp.clear();
        this.attachedRenderersTmp.addAll(this.attachedRenderers);
        for (int i = 0; i < this.attachedRenderersTmp.size(); i++) {
            ((GroupCallMiniTextureView) this.attachedRenderersTmp.get(i)).updateAttachState(true);
        }
        this.sizeNotifierFrameLayout.onResume();
        this.callMessageEnterView.onResume();
    }

    public void onPause() {
        paused = true;
        this.attachedRenderersTmp.clear();
        this.attachedRenderersTmp.addAll(this.attachedRenderers);
        for (int i = 0; i < this.attachedRenderersTmp.size(); i++) {
            ((GroupCallMiniTextureView) this.attachedRenderersTmp.get(i)).updateAttachState(false);
        }
        this.sizeNotifierFrameLayout.onPause();
        this.callMessageEnterView.onPause();
    }

    public boolean isRtmpLandscapeMode() {
        return isRtmpStream() && !this.call.visibleVideoParticipants.isEmpty() && (this.call.visibleVideoParticipants.get(0).aspectRatio == 0.0f || this.call.visibleVideoParticipants.get(0).aspectRatio >= 1.0f);
    }

    public boolean isRtmpStream() {
        ChatObject.Call call = this.call;
        return call != null && call.call.rtmp_stream;
    }

    public boolean isConference() {
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        return sharedInstance != null && sharedInstance.isConference();
    }

    public boolean canManageCall() {
        TLRPC.GroupCall groupCall;
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        if (sharedInstance != null && sharedInstance.isConference()) {
            ChatObject.Call call = this.call;
            return (call == null || (groupCall = call.call) == null || !groupCall.creator) ? false : true;
        }
        return ChatObject.canManageCalls(this.currentChat);
    }

    @Override // org.telegram.p026ui.ActionBar.BottomSheet, android.app.Dialog, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (this.parentActivity == null) {
            return super.dispatchKeyEvent(keyEvent);
        }
        if (keyEvent.getAction() == 0 && ((keyEvent.getKeyCode() == 24 || keyEvent.getKeyCode() == 25) && VoIPService.getSharedInstance() != null && Build.VERSION.SDK_INT >= 32)) {
            boolean zIsSpeakerMuted = WebRtcAudioTrack.isSpeakerMuted();
            AudioManager audioManager = (AudioManager) this.parentActivity.getSystemService(MediaStreamTrack.AUDIO_TRACK_KIND);
            boolean z = false;
            if (audioManager.getStreamVolume(0) == audioManager.getStreamMinVolume(0) && keyEvent.getKeyCode() == 25) {
                z = true;
            }
            WebRtcAudioTrack.setSpeakerMute(z);
            if (zIsSpeakerMuted != WebRtcAudioTrack.isSpeakerMuted()) {
                getUndoView().showWithAction(0L, z ? 42 : 43, (Runnable) null);
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    /* JADX INFO: loaded from: classes6.dex */
    public class WatchersView extends LinearLayout {
        private float lastWidth;
        private final AnimatedTextView watchersCountTextView;

        public WatchersView(Context context) {
            super(context);
            this.lastWidth = 0.0f;
            setOrientation(1);
            setGravity(17);
            AnimatedTextView animatedTextView = new AnimatedTextView(context, true, false, false);
            this.watchersCountTextView = animatedTextView;
            animatedTextView.setTextColor(-1);
            animatedTextView.setTextSize(AndroidUtilities.m1081dp(46.0f));
            animatedTextView.setTypeface(AndroidUtilities.bold());
            animatedTextView.setGravity(1);
            TextView textView = new TextView(context);
            textView.setTextColor(-1);
            textView.setTextSize(1, 14.0f);
            textView.setTypeface(AndroidUtilities.bold());
            textView.setText(LocaleController.getString(C2702R.string.VoipChannelWatching));
            addView(animatedTextView, LayoutHelper.createLinear(-1, 46));
            addView(textView, LayoutHelper.createLinear(-2, -2));
        }

        void setWatchersCount(int i) {
            String number = LocaleController.formatNumber(i, ',');
            float fMeasureText = this.watchersCountTextView.getPaint().measureText((CharSequence) number, 0, number.length());
            if (this.lastWidth != fMeasureText) {
                this.watchersCountTextView.getPaint().setShader(new LinearGradient(0.0f, 0.0f, fMeasureText, 0.0f, new int[]{GroupCallActivity.this.getThemedColor(Theme.key_premiumGradient1), GroupCallActivity.this.getThemedColor(Theme.key_premiumGradient3)}, new float[]{0.0f, 1.0f}, Shader.TileMode.CLAMP));
                this.lastWidth = fMeasureText;
            }
            this.watchersCountTextView.setText(number);
        }
    }

    /* JADX INFO: loaded from: classes6.dex */
    public class LightningView extends View {
        private int[] currentColors;
        private final Paint paint;
        private int parentBackgroundColor;
        private float shadowOffset;

        public LightningView(Context context) {
            super(context);
            Paint paint = new Paint(1);
            this.paint = paint;
            paint.setStyle(Paint.Style.FILL);
            paint.setAlpha(0);
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (!GroupCallActivity.this.needTextureLightning || GroupCallActivity.this.renderersContainer.progressToFullscreenMode >= 0.1d) {
                return;
            }
            canvas.drawRect(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight(), this.paint);
        }

        public void setNewColors(int[] iArr) {
            int[] iArr2 = this.currentColors;
            boolean z = true;
            boolean z2 = false;
            if (iArr2 == null || iArr[0] != iArr2[0] || iArr[1] != iArr2[1]) {
                if (iArr2 == null) {
                    this.paint.setAlpha(com.sun.jna.Function.USE_VARARGS);
                }
                this.currentColors = iArr;
                if (GroupCallActivity.this.liveLabelBgColorAnimator != null && GroupCallActivity.this.textureLightningViewAnimatingAlpha != 1.0f) {
                    int[] iArr3 = this.currentColors;
                    iArr3[0] = ColorUtils.setAlphaComponent(iArr3[0], (int) (GroupCallActivity.this.textureLightningViewAnimatingAlpha * 255.0f));
                    int[] iArr4 = this.currentColors;
                    iArr4[1] = ColorUtils.setAlphaComponent(iArr4[1], (int) (GroupCallActivity.this.textureLightningViewAnimatingAlpha * 255.0f));
                }
                this.paint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, GroupCallActivity.this.textureLightningView.getMeasuredHeight(), this.currentColors, (float[]) null, Shader.TileMode.CLAMP));
                z2 = true;
            }
            if (this.parentBackgroundColor != GroupCallActivity.this.backgroundColor) {
                this.paint.setShadowLayer(AndroidUtilities.m1081dp(36.0f), 0.0f, this.shadowOffset, GroupCallActivity.this.backgroundColor);
                this.parentBackgroundColor = GroupCallActivity.this.backgroundColor;
            } else {
                z = z2;
            }
            if (z) {
                invalidate();
            }
            GroupCallActivity.this.runUpdateTextureLightningRunnable();
        }

        public void setShadowOffset(int i) {
            float f = i;
            if (this.shadowOffset != f) {
                this.paint.setShadowLayer(AndroidUtilities.m1081dp(36.0f), 0.0f, this.shadowOffset, GroupCallActivity.this.backgroundColor);
                this.shadowOffset = f;
                invalidate();
            }
        }
    }

    public static String getRandomEmoji() {
        String[][] strArr = EmojiData.data;
        String[] strArr2 = strArr[(int) Math.floor(Math.random() * ((double) strArr.length))];
        return strArr2[(int) Math.floor(Math.random() * ((double) strArr2.length))];
    }

    /* JADX INFO: loaded from: classes6.dex */
    public static final class EmojiSlot {
        private String lastEmoji;
        private boolean loaded;
        private final int offset;
        private AnimatedEmojiDrawable real;
        private boolean realAllowed;
        private Drawable realThumb;
        private long startTime;
        private final long INTERVAL = 180;
        private final Drawable[] pool = new Drawable[6];
        private GradientClip clip = new GradientClip();
        private final HashSet parents = new HashSet();
        private boolean attached = false;
        private final AnimatedEmojiSpan.InvalidateHolder invalidate = new AnimatedEmojiSpan.InvalidateHolder() { // from class: org.telegram.ui.GroupCallActivity$EmojiSlot$$ExternalSyntheticLambda1
            @Override // org.telegram.ui.Components.AnimatedEmojiSpan.InvalidateHolder
            public final void invalidate() {
                this.f$0.lambda$new$0();
            }
        };
        private final RectF rectF = new RectF();

        public /* synthetic */ void lambda$new$0() {
            Iterator it = this.parents.iterator();
            while (it.hasNext()) {
                ((View) it.next()).invalidate();
            }
        }

        public EmojiSlot(int i) {
            int i2 = 0;
            this.offset = i;
            while (true) {
                Drawable[] drawableArr = this.pool;
                if (i2 < drawableArr.length) {
                    drawableArr[i2] = Emoji.getEmojiDrawable(GroupCallActivity.getRandomEmoji());
                    i2++;
                } else {
                    this.startTime = System.currentTimeMillis();
                    return;
                }
            }
        }

        public void set(String str) {
            boolean z = this.loaded;
            this.loaded = str != null;
            if (str != null && (this.real == null || !TextUtils.equals(this.lastEmoji, str))) {
                AnimatedEmojiDrawable animatedEmojiDrawable = this.real;
                if (animatedEmojiDrawable != null) {
                    animatedEmojiDrawable.removeView(this.invalidate);
                }
                this.realThumb = Emoji.getEmojiDrawable(str);
                AnimatedEmojiDrawable animatedEmojiDrawable2 = new AnimatedEmojiDrawable(21, UserConfig.getProductionAccount());
                this.real = animatedEmojiDrawable2;
                this.lastEmoji = str;
                animatedEmojiDrawable2.setupEmojiThumb(str);
                updateEmoji();
                if (this.attached) {
                    this.real.addView(this.invalidate);
                }
            }
            if (!this.loaded || z) {
                return;
            }
            this.realAllowed = false;
        }

        private void updateEmoji() {
            TLRPC.Document document;
            if (this.real == null || this.lastEmoji == null) {
                return;
            }
            int productionAccount = UserConfig.getProductionAccount();
            TLRPC.TL_inputStickerSetShortName tL_inputStickerSetShortName = new TLRPC.TL_inputStickerSetShortName();
            tL_inputStickerSetShortName.short_name = "StaticEmoji";
            MediaDataController mediaDataController = MediaDataController.getInstance(productionAccount);
            int i = 0;
            TLRPC.TL_messages_stickerSet stickerSet = mediaDataController.getStickerSet(tL_inputStickerSetShortName, 0, false, true, new Utilities.Callback() { // from class: org.telegram.ui.GroupCallActivity$EmojiSlot$$ExternalSyntheticLambda0
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$updateEmoji$1((TLRPC.TL_messages_stickerSet) obj);
                }
            });
            if (stickerSet == null) {
                return;
            }
            String strReplace = this.lastEmoji.replace("️", _UrlKt.FRAGMENT_ENCODE_SET);
            ArrayList arrayList = stickerSet.documents;
            int size = arrayList.size();
            while (true) {
                document = null;
                if (i >= size) {
                    break;
                }
                Object obj = arrayList.get(i);
                i++;
                TLRPC.Document document2 = (TLRPC.Document) obj;
                if (TextUtils.equals(MessageObject.findAnimatedEmojiEmoticon(document2, null).replace("️", _UrlKt.FRAGMENT_ENCODE_SET), strReplace)) {
                    document = document2;
                    break;
                }
            }
            if (document != null) {
                this.real.setupDocument(document);
                return;
            }
            FileLog.m1091e("emoji \"" + this.lastEmoji + "\" not found in addemoji/" + tL_inputStickerSetShortName.short_name);
        }

        public /* synthetic */ void lambda$updateEmoji$1(TLRPC.TL_messages_stickerSet tL_messages_stickerSet) {
            updateEmoji();
        }

        public void attach(View view) {
            this.parents.add(view);
            checkAttach();
        }

        public void detach(View view) {
            this.parents.remove(view);
            checkAttach();
        }

        private void checkAttach() {
            boolean zIsEmpty = this.parents.isEmpty();
            boolean z = !zIsEmpty;
            if (this.attached != z) {
                this.attached = z;
                if (!zIsEmpty) {
                    onAttached();
                } else {
                    onDetached();
                }
            }
        }

        private void onAttached() {
            AnimatedEmojiDrawable animatedEmojiDrawable = this.real;
            if (animatedEmojiDrawable != null) {
                animatedEmojiDrawable.addView(this.invalidate);
            }
        }

        private void onDetached() {
            AnimatedEmojiDrawable animatedEmojiDrawable = this.real;
            if (animatedEmojiDrawable != null) {
                animatedEmojiDrawable.removeView(this.invalidate);
            }
        }

        public boolean draw(Canvas canvas, RectF rectF, float f) {
            float f2;
            float f3;
            float fM1081dp = AndroidUtilities.m1081dp(6.0f);
            this.rectF.set(rectF);
            float f4 = -fM1081dp;
            this.rectF.inset(f4, f4);
            RectF rectF2 = this.rectF;
            canvas.saveLayerAlpha(rectF2.left, rectF2.top, rectF2.right, rectF2.bottom, com.sun.jna.Function.USE_VARARGS, 31);
            long jCurrentTimeMillis = System.currentTimeMillis() + (((long) this.offset) * 45);
            long j = jCurrentTimeMillis - this.startTime;
            float f5 = j / 180.0f;
            float fMin = Math.min(1.0f, f5);
            boolean z = false;
            if (this.loaded && this.real != null && this.realThumb != null && this.realAllowed) {
                this.rectF.set(rectF);
                this.rectF.offset(0.0f, (fMin - 1.0f) * (rectF.height() + fM1081dp));
                if (f < 1.0f) {
                    canvas.save();
                    f3 = 255.0f;
                    f2 = 0.0f;
                    this.realThumb.setBounds(0, 0, (int) this.rectF.width(), (int) this.rectF.height());
                    RectF rectF3 = this.rectF;
                    canvas.translate(rectF3.left, rectF3.top);
                    this.realThumb.setAlpha((int) ((1.0f - f) * 255.0f));
                    this.realThumb.draw(canvas);
                    this.realThumb.setAlpha(com.sun.jna.Function.USE_VARARGS);
                    canvas.restore();
                } else {
                    f2 = 0.0f;
                    f3 = 255.0f;
                }
                if (f > f2) {
                    canvas.save();
                    this.rectF.inset(AndroidUtilities.m1081dp(-4.0f), AndroidUtilities.m1081dp(-4.0f));
                    this.real.setBounds(0, 0, (int) this.rectF.width(), (int) this.rectF.height());
                    RectF rectF4 = this.rectF;
                    canvas.translate(rectF4.left, rectF4.top);
                    this.real.setAlpha((int) (f * f3));
                    this.real.draw(canvas);
                    this.real.setAlpha(com.sun.jna.Function.USE_VARARGS);
                    canvas.restore();
                }
            } else {
                canvas.save();
                this.rectF.set(rectF);
                this.rectF.offset(0.0f, (fMin - 1.0f) * (rectF.height() + fM1081dp));
                RectF rectF5 = this.rectF;
                canvas.translate(rectF5.left, rectF5.top);
                this.pool[1].setBounds(0, 0, (int) this.rectF.width(), (int) this.rectF.height());
                this.pool[1].setAlpha(127);
                this.pool[1].draw(canvas);
                this.pool[1].setAlpha(com.sun.jna.Function.USE_VARARGS);
                canvas.restore();
            }
            this.rectF.set(rectF);
            this.rectF.offset(0.0f, fMin * (rectF.height() + fM1081dp));
            canvas.save();
            RectF rectF6 = this.rectF;
            canvas.translate(rectF6.left, rectF6.top);
            this.pool[0].setBounds(0, 0, (int) this.rectF.width(), (int) this.rectF.height());
            this.pool[0].setAlpha(127);
            this.pool[0].draw(canvas);
            this.pool[0].setAlpha(com.sun.jna.Function.USE_VARARGS);
            canvas.restore();
            if (f5 >= 1.0f) {
                if (this.loaded && this.realAllowed) {
                    z = true;
                } else {
                    this.startTime = jCurrentTimeMillis - (j % 180);
                    shiftPool();
                    if (this.loaded) {
                        this.realAllowed = true;
                    }
                }
            }
            this.rectF.set(rectF);
            float f6 = (int) f4;
            this.rectF.inset(f6, f6);
            RectF rectF7 = this.rectF;
            float f7 = rectF7.left;
            float f8 = rectF7.top;
            rectF7.set(f7, f8, rectF7.right, f8 + fM1081dp);
            this.clip.draw(canvas, this.rectF, 1, 1.0f);
            this.rectF.set(rectF);
            this.rectF.inset(f6, f6);
            RectF rectF8 = this.rectF;
            float f9 = rectF8.left;
            float f10 = rectF8.bottom;
            rectF8.set(f9, f10 - fM1081dp, rectF8.right, f10);
            this.clip.draw(canvas, this.rectF, 3, 1.0f);
            canvas.restore();
            return !z;
        }

        private void shiftPool() {
            int i = 0;
            while (true) {
                Drawable[] drawableArr = this.pool;
                if (i < drawableArr.length - 1) {
                    int i2 = i + 1;
                    drawableArr[i] = drawableArr[i2];
                    i = i2;
                } else {
                    drawableArr[drawableArr.length - 1] = Emoji.getEmojiDrawable(GroupCallActivity.getRandomEmoji());
                    return;
                }
            }
        }
    }

    /* JADX INFO: loaded from: classes6.dex */
    public static final class CallEncryptionCellDrawable {
        private final Text closeText;
        private int listBackgroundColor;
        private View parentView;
        private final Text text1;
        private final Text text2;
        private final Paint backgroundPaint = new Paint(1);
        private final Paint dividerPaint = new Paint(1);
        private final EmojiSlot[] slots = new EmojiSlot[4];
        private boolean loading = true;
        private final AnimatedFloat loadingAlpha = new AnimatedFloat(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$CallEncryptionCellDrawable$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.invalidate();
            }
        }, 0, 320, CubicBezierInterpolator.EASE_OUT_QUINT);
        private final RectF fromRect = new RectF();
        private final RectF toRect = new RectF();
        private final Path clipPath = new Path();

        public CallEncryptionCellDrawable(Context context) {
            int i = 0;
            while (true) {
                EmojiSlot[] emojiSlotArr = this.slots;
                if (i < emojiSlotArr.length) {
                    emojiSlotArr[i] = new EmojiSlot(i);
                    i++;
                } else {
                    this.backgroundPaint.setColor(Theme.getColor(Theme.key_voipgroup_listViewBackground));
                    this.text1 = new Text(LocaleController.getString(C2702R.string.ConferenceEncrypted), 12.0f, AndroidUtilities.bold());
                    this.text2 = new Text(LocaleController.getString(C2702R.string.ConferenceEncryptedInfo), 11.0f).multiline(99).setMaxWidth(AndroidUtilities.m1081dp(200.0f)).lineSpacing(AndroidUtilities.m1081dp(2.66f));
                    this.closeText = new Text(LocaleController.getString(C2702R.string.ConferenceEncryptedClose), 14.0f, AndroidUtilities.bold());
                    setEmojis(null);
                    return;
                }
            }
        }

        public void invalidate() {
            View view = this.parentView;
            if (view != null) {
                view.invalidate();
            }
        }

        public void setParentView(View view) {
            this.parentView = view;
            int i = 0;
            while (true) {
                EmojiSlot[] emojiSlotArr = this.slots;
                if (i >= emojiSlotArr.length) {
                    return;
                }
                emojiSlotArr[i].attach(view);
                i++;
            }
        }

        public void resetParentView(View view) {
            if (this.parentView == view) {
                return;
            }
            int i = 0;
            while (true) {
                EmojiSlot[] emojiSlotArr = this.slots;
                if (i < emojiSlotArr.length) {
                    emojiSlotArr[i].detach(view);
                    i++;
                } else {
                    this.parentView = null;
                    return;
                }
            }
        }

        public void setEmojis(String[] strArr) {
            this.loading = strArr == null;
            for (int i = 0; i < 4; i++) {
                this.slots[i].set(strArr == null ? null : strArr[i]);
            }
            invalidate();
        }

        public void setPaintBackgroundColor(int i) {
            this.listBackgroundColor = i;
            invalidate();
        }

        public boolean draw(Canvas canvas, float f, float f2) {
            canvas.save();
            this.text1.ellipsize(f - AndroidUtilities.m1081dp(132.0f));
            this.backgroundPaint.setColor(ColorUtils.blendARGB(this.listBackgroundColor, Theme.getColor(Theme.key_voipgroup_listViewBackground), f2));
            float f3 = this.loadingAlpha.set(this.loading);
            float fM1081dp = AndroidUtilities.m1081dp(14.0f) + AndroidUtilities.m1081dp(86.0f) + this.text1.getWidth();
            float fM1081dp2 = AndroidUtilities.m1081dp(28.0f);
            float fM1081dp3 = AndroidUtilities.m1081dp(232.0f);
            float fM1081dp4 = AndroidUtilities.m1081dp(50.0f) + AndroidUtilities.m1081dp(54.0f) + this.text2.getHeight();
            float fLerp = AndroidUtilities.lerp(fM1081dp, fM1081dp3, f2);
            float fLerp2 = AndroidUtilities.lerp(fM1081dp2, fM1081dp4, f2);
            float fLerp3 = AndroidUtilities.lerp(AndroidUtilities.m1081dp(14.0f), AndroidUtilities.m1081dp(16.0f), f2);
            RectF rectF = AndroidUtilities.rectTmp;
            rectF.set((f - fLerp) / 2.0f, 0.0f, (f + fLerp) / 2.0f, fLerp2);
            canvas.drawRoundRect(rectF, fLerp3, fLerp3, this.backgroundPaint);
            this.clipPath.rewind();
            this.clipPath.addRoundRect(rectF, fLerp3, fLerp3, Path.Direction.CW);
            canvas.clipPath(this.clipPath);
            int iM1081dp = AndroidUtilities.m1081dp(18.0f);
            int iM1081dp2 = AndroidUtilities.m1081dp(30.0f);
            int i = iM1081dp / 2;
            float f4 = iM1081dp / 2.0f;
            float fCenterY = ((int) rectF.centerY()) - i;
            float fCenterY2 = ((int) rectF.centerY()) + i;
            this.fromRect.set(((((int) rectF.left) + AndroidUtilities.m1081dp(7.0f)) + AndroidUtilities.m1081dp(10.0f)) - f4, fCenterY, ((int) rectF.left) + AndroidUtilities.m1081dp(7.0f) + AndroidUtilities.m1081dp(10.0f) + f4, fCenterY2);
            float f5 = f / 2.0f;
            float f6 = f5 - (r4 * 2);
            float fM1081dp5 = (int) ((fM1081dp3 - AndroidUtilities.m1081dp(32.0f)) / 4.0f);
            float f7 = (int) ((0.5f * fM1081dp5) + f6);
            float f8 = iM1081dp2 / 2.0f;
            this.toRect.set(f7 - f8, (int) ((rectF.top + AndroidUtilities.m1081dp(27.33f)) - f8), f7 + f8, (int) (rectF.top + AndroidUtilities.m1081dp(27.33f) + f8));
            RectF rectF2 = this.fromRect;
            RectF rectF3 = this.toRect;
            AndroidUtilities.lerpCentered(rectF2, rectF3, f2, rectF3);
            boolean zDraw = this.slots[0].draw(canvas, this.toRect, f2);
            this.fromRect.set(((((int) rectF.left) + AndroidUtilities.m1081dp(27.0f)) + AndroidUtilities.m1081dp(10.0f)) - f4, fCenterY, ((int) rectF.left) + AndroidUtilities.m1081dp(27.0f) + AndroidUtilities.m1081dp(10.0f) + f4, fCenterY2);
            float f9 = (int) ((1.5f * fM1081dp5) + f6);
            boolean z = zDraw;
            this.toRect.set(f9 - f8, (int) ((rectF.top + AndroidUtilities.m1081dp(27.33f)) - f8), f9 + f8, (int) (rectF.top + AndroidUtilities.m1081dp(27.33f) + f8));
            RectF rectF4 = this.fromRect;
            RectF rectF5 = this.toRect;
            AndroidUtilities.lerpCentered(rectF4, rectF5, f2, rectF5);
            if (this.slots[1].draw(canvas, this.toRect, f2)) {
                z = true;
            }
            Text text = this.text1;
            text.draw(canvas, f5 - (text.getWidth() / 2.0f), fM1081dp2 / 2.0f, -1, (1.0f - f2) * AndroidUtilities.lerp(1.0f, 0.75f, f3));
            this.fromRect.set(((((int) rectF.right) - AndroidUtilities.m1081dp(47.0f)) + AndroidUtilities.m1081dp(10.0f)) - f4, fCenterY, (((int) rectF.right) - AndroidUtilities.m1081dp(47.0f)) + AndroidUtilities.m1081dp(10.0f) + f4, fCenterY2);
            float f10 = (int) ((2.5f * fM1081dp5) + f6);
            this.toRect.set(f10 - f8, (int) ((rectF.top + AndroidUtilities.m1081dp(27.33f)) - f8), f10 + f8, (int) (rectF.top + AndroidUtilities.m1081dp(27.33f) + f8));
            RectF rectF6 = this.fromRect;
            RectF rectF7 = this.toRect;
            AndroidUtilities.lerpCentered(rectF6, rectF7, f2, rectF7);
            if (this.slots[2].draw(canvas, this.toRect, f2)) {
                z = true;
            }
            this.fromRect.set(((((int) rectF.right) - AndroidUtilities.m1081dp(27.0f)) + AndroidUtilities.m1081dp(10.0f)) - f4, fCenterY, (((int) rectF.right) - AndroidUtilities.m1081dp(27.0f)) + AndroidUtilities.m1081dp(10.0f) + f4, fCenterY2);
            float f11 = (int) (f6 + (fM1081dp5 * 3.5f));
            this.toRect.set(f11 - f8, (int) ((rectF.top + AndroidUtilities.m1081dp(27.33f)) - f8), f11 + f8, (int) (rectF.top + AndroidUtilities.m1081dp(27.33f) + f8));
            RectF rectF8 = this.fromRect;
            RectF rectF9 = this.toRect;
            AndroidUtilities.lerpCentered(rectF8, rectF9, f2, rectF9);
            boolean z2 = this.slots[3].draw(canvas, this.toRect, f2) ? true : z;
            if (f2 > 0.0f) {
                this.text2.draw(canvas, (rectF.centerX() - (fM1081dp3 / 2.0f)) + AndroidUtilities.m1081dp(16.0f), AndroidUtilities.m1081dp(54.0f), -1, f2);
                this.dividerPaint.setColor(-16777216);
                this.dividerPaint.setAlpha((int) (255.0f * f2));
                canvas.drawRect(rectF.left, fM1081dp4 - AndroidUtilities.m1081dp(40.0f), rectF.right, AndroidUtilities.m1081dp(0.66f) + (fM1081dp4 - AndroidUtilities.m1081dp(40.0f)), this.dividerPaint);
                this.closeText.draw(canvas, rectF.centerX() - (this.closeText.getWidth() / 2.0f), fM1081dp4 - AndroidUtilities.m1081dp(20.0f), -1, f2);
            }
            canvas.restore();
            return z2;
        }
    }

    /* JADX INFO: loaded from: classes6.dex */
    public static final class CallEncryptionCell extends View {
        private final CallEncryptionCellDrawable drawable;

        public CallEncryptionCell(final Context context, CallEncryptionCellDrawable callEncryptionCellDrawable) {
            super(context);
            this.drawable = callEncryptionCellDrawable;
            NotificationCenter.listenEmojiLoading(this);
            setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.GroupCallActivity$CallEncryptionCell$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$new$0(context, view);
                }
            });
        }

        public /* synthetic */ void lambda$new$0(Context context, View view) {
            new EncryptionCallDialog(context, this).show();
        }

        @Override // android.view.View
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            CallEncryptionCellDrawable callEncryptionCellDrawable = this.drawable;
            if (callEncryptionCellDrawable != null) {
                callEncryptionCellDrawable.setParentView(this);
            }
        }

        @Override // android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            CallEncryptionCellDrawable callEncryptionCellDrawable = this.drawable;
            if (callEncryptionCellDrawable != null) {
                callEncryptionCellDrawable.resetParentView(this);
            }
        }

        @Override // android.view.View
        protected void onMeasure(int i, int i2) {
            setMeasuredDimension(View.MeasureSpec.getSize(i), AndroidUtilities.m1081dp(38.0f));
        }

        @Override // android.view.View
        protected void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
            CallEncryptionCellDrawable callEncryptionCellDrawable = this.drawable;
            if (callEncryptionCellDrawable == null || !callEncryptionCellDrawable.draw(canvas, getMeasuredWidth(), 0.0f)) {
                return;
            }
            invalidate();
        }

        static final class EncryptionCallDialog extends Dialog {
            private Bitmap blurBitmap;
            private Paint blurBitmapPaint;
            private BitmapShader blurBitmapShader;
            private final Matrix blurMatrix;
            private final CallEncryptionCell cell;
            private boolean dismissing;
            private float progress;
            private ValueAnimator progressAnimator;
            private final View windowView;

            public EncryptionCallDialog(Context context, final CallEncryptionCell callEncryptionCell) {
                super(context, C2702R.style.TransparentDialog);
                this.blurMatrix = new Matrix();
                this.cell = callEncryptionCell;
                if (callEncryptionCell != null) {
                    callEncryptionCell.setVisibility(4);
                }
                AndroidUtilities.makeGlobalBlurBitmap(new Utilities.Callback() { // from class: org.telegram.ui.GroupCallActivity$CallEncryptionCell$EncryptionCallDialog$$ExternalSyntheticLambda0
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        this.f$0.lambda$new$0(callEncryptionCell, (Bitmap) obj);
                    }
                }, 14.0f);
                C56231 c56231 = new View(context) { // from class: org.telegram.ui.GroupCallActivity.CallEncryptionCell.EncryptionCallDialog.1

                    /* JADX INFO: renamed from: p */
                    private final int[] f2075p = new int[2];
                    final /* synthetic */ CallEncryptionCell val$cell;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    C56231(Context context2, final CallEncryptionCell callEncryptionCell2) {
                        super(context2);
                        callEncryptionCell = callEncryptionCell2;
                        this.f2075p = new int[2];
                    }

                    @Override // android.view.View
                    public void dispatchDraw(Canvas canvas) {
                        Canvas canvas2;
                        if (EncryptionCallDialog.this.progress <= 0.0f || EncryptionCallDialog.this.blurBitmapPaint == null) {
                            canvas2 = canvas;
                        } else {
                            EncryptionCallDialog.this.blurMatrix.reset();
                            float width = getWidth() / EncryptionCallDialog.this.blurBitmap.getWidth();
                            EncryptionCallDialog.this.blurMatrix.postScale(width, width);
                            EncryptionCallDialog.this.blurBitmapShader.setLocalMatrix(EncryptionCallDialog.this.blurMatrix);
                            EncryptionCallDialog.this.blurBitmapPaint.setAlpha((int) (EncryptionCallDialog.this.progress * 255.0f));
                            canvas2 = canvas;
                            canvas2.drawRect(0.0f, 0.0f, getWidth(), getHeight(), EncryptionCallDialog.this.blurBitmapPaint);
                        }
                        CallEncryptionCell callEncryptionCell2 = callEncryptionCell;
                        if (callEncryptionCell2 != null) {
                            if (callEncryptionCell2.isAttachedToWindow() && callEncryptionCell.getAlpha() > 0.5f) {
                                callEncryptionCell.getLocationInWindow(this.f2075p);
                            } else {
                                EncryptionCallDialog.this.dismiss();
                            }
                            canvas2.save();
                            canvas2.translate(this.f2075p[0] - (callEncryptionCell.getMeasuredWidth() * (1.0f - callEncryptionCell.getScaleX())), this.f2075p[1] - (callEncryptionCell.getMeasuredHeight() * (1.0f - callEncryptionCell.getScaleY())));
                            if (callEncryptionCell.drawable.draw(canvas2, callEncryptionCell.getMeasuredWidth(), EncryptionCallDialog.this.progress)) {
                                invalidate();
                            }
                            canvas2.restore();
                        }
                    }
                };
                this.windowView = c56231;
                c56231.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.GroupCallActivity$CallEncryptionCell$EncryptionCallDialog$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$new$1(view);
                    }
                });
            }

            public /* synthetic */ void lambda$new$0(CallEncryptionCell callEncryptionCell, Bitmap bitmap) {
                if (callEncryptionCell != null) {
                    callEncryptionCell.setVisibility(0);
                }
                this.blurBitmap = bitmap;
                Paint paint = new Paint(1);
                this.blurBitmapPaint = paint;
                Bitmap bitmap2 = this.blurBitmap;
                Shader.TileMode tileMode = Shader.TileMode.CLAMP;
                BitmapShader bitmapShader = new BitmapShader(bitmap2, tileMode, tileMode);
                this.blurBitmapShader = bitmapShader;
                paint.setShader(bitmapShader);
                ColorMatrix colorMatrix = new ColorMatrix();
                AndroidUtilities.adjustSaturationColorMatrix(colorMatrix, Theme.isCurrentThemeDark() ? 0.05f : 0.25f);
                AndroidUtilities.adjustBrightnessColorMatrix(colorMatrix, Theme.isCurrentThemeDark() ? -0.02f : -0.04f);
                this.blurBitmapPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
            }

            /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$CallEncryptionCell$EncryptionCallDialog$1 */
            class C56231 extends View {

                /* JADX INFO: renamed from: p */
                private final int[] f2075p = new int[2];
                final /* synthetic */ CallEncryptionCell val$cell;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                C56231(Context context2, final CallEncryptionCell callEncryptionCell2) {
                    super(context2);
                    callEncryptionCell = callEncryptionCell2;
                    this.f2075p = new int[2];
                }

                @Override // android.view.View
                public void dispatchDraw(Canvas canvas) {
                    Canvas canvas2;
                    if (EncryptionCallDialog.this.progress <= 0.0f || EncryptionCallDialog.this.blurBitmapPaint == null) {
                        canvas2 = canvas;
                    } else {
                        EncryptionCallDialog.this.blurMatrix.reset();
                        float width = getWidth() / EncryptionCallDialog.this.blurBitmap.getWidth();
                        EncryptionCallDialog.this.blurMatrix.postScale(width, width);
                        EncryptionCallDialog.this.blurBitmapShader.setLocalMatrix(EncryptionCallDialog.this.blurMatrix);
                        EncryptionCallDialog.this.blurBitmapPaint.setAlpha((int) (EncryptionCallDialog.this.progress * 255.0f));
                        canvas2 = canvas;
                        canvas2.drawRect(0.0f, 0.0f, getWidth(), getHeight(), EncryptionCallDialog.this.blurBitmapPaint);
                    }
                    CallEncryptionCell callEncryptionCell2 = callEncryptionCell;
                    if (callEncryptionCell2 != null) {
                        if (callEncryptionCell2.isAttachedToWindow() && callEncryptionCell.getAlpha() > 0.5f) {
                            callEncryptionCell.getLocationInWindow(this.f2075p);
                        } else {
                            EncryptionCallDialog.this.dismiss();
                        }
                        canvas2.save();
                        canvas2.translate(this.f2075p[0] - (callEncryptionCell.getMeasuredWidth() * (1.0f - callEncryptionCell.getScaleX())), this.f2075p[1] - (callEncryptionCell.getMeasuredHeight() * (1.0f - callEncryptionCell.getScaleY())));
                        if (callEncryptionCell.drawable.draw(canvas2, callEncryptionCell.getMeasuredWidth(), EncryptionCallDialog.this.progress)) {
                            invalidate();
                        }
                        canvas2.restore();
                    }
                }
            }

            public /* synthetic */ void lambda$new$1(View view) {
                dismiss();
            }

            @Override // android.app.Dialog
            protected void onCreate(Bundle bundle) {
                super.onCreate(bundle);
                Window window = getWindow();
                window.setWindowAnimations(C2702R.style.DialogNoAnimation);
                setContentView(this.windowView, new ViewGroup.LayoutParams(-1, -1));
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.width = -1;
                attributes.height = -1;
                attributes.gravity = Opcodes.DNEG;
                attributes.dimAmount = 0.0f;
                int i = attributes.flags & (-3);
                attributes.softInputMode = 48;
                attributes.flags = (-2013069056) | i;
                if (!BuildVars.DEBUG_PRIVATE_VERSION) {
                    attributes.flags = i | (-2013060864);
                    AndroidUtilities.logFlagSecure();
                }
                attributes.flags |= 1152;
                if (Build.VERSION.SDK_INT >= 28) {
                    attributes.layoutInDisplayCutoutMode = 1;
                }
                window.setAttributes(attributes);
            }

            @Override // android.app.Dialog
            public void show() {
                super.show();
                animate(1.0f, null);
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$CallEncryptionCell$EncryptionCallDialog$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$show$2();
                    }
                }, 16L);
            }

            public /* synthetic */ void lambda$show$2() {
                CallEncryptionCell callEncryptionCell = this.cell;
                if (callEncryptionCell != null) {
                    callEncryptionCell.setVisibility(4);
                }
            }

            @Override // android.app.Dialog, android.content.DialogInterface
            public void dismiss() {
                if (this.dismissing) {
                    return;
                }
                this.dismissing = true;
                animate(0.0f, new Runnable() { // from class: org.telegram.ui.GroupCallActivity$CallEncryptionCell$EncryptionCallDialog$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$dismiss$4();
                    }
                });
                try {
                    WindowManager.LayoutParams attributes = getWindow().getAttributes();
                    attributes.flags |= 16;
                    getWindow().setAttributes(attributes);
                } catch (Exception e) {
                    FileLog.m1093e(e);
                }
            }

            public /* synthetic */ void lambda$dismiss$4() {
                CallEncryptionCell callEncryptionCell = this.cell;
                if (callEncryptionCell != null) {
                    callEncryptionCell.setVisibility(0);
                }
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$CallEncryptionCell$EncryptionCallDialog$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$dismiss$3();
                    }
                }, 16L);
            }

            public /* synthetic */ void lambda$dismiss$3() {
                super.dismiss();
            }

            private void animate(float f, Runnable runnable) {
                ValueAnimator valueAnimator = this.progressAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                    this.progressAnimator = null;
                }
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.progress, f);
                this.progressAnimator = valueAnimatorOfFloat;
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.GroupCallActivity$CallEncryptionCell$EncryptionCallDialog$$ExternalSyntheticLambda5
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        this.f$0.lambda$animate$5(valueAnimator2);
                    }
                });
                this.progressAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.GroupCallActivity.CallEncryptionCell.EncryptionCallDialog.2
                    final /* synthetic */ Runnable val$done;
                    final /* synthetic */ float val$to;

                    C56242(float f2, Runnable runnable2) {
                        f = f2;
                        runnable = runnable2;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        EncryptionCallDialog.this.progress = f;
                        EncryptionCallDialog.this.windowView.invalidate();
                        Runnable runnable2 = runnable;
                        if (runnable2 != null) {
                            runnable2.run();
                        }
                    }
                });
                this.progressAnimator.setDuration(420L);
                this.progressAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                this.progressAnimator.start();
            }

            public /* synthetic */ void lambda$animate$5(ValueAnimator valueAnimator) {
                this.progress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                this.windowView.invalidate();
            }

            /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$CallEncryptionCell$EncryptionCallDialog$2 */
            class C56242 extends AnimatorListenerAdapter {
                final /* synthetic */ Runnable val$done;
                final /* synthetic */ float val$to;

                C56242(float f2, Runnable runnable2) {
                    f = f2;
                    runnable = runnable2;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    EncryptionCallDialog.this.progress = f;
                    EncryptionCallDialog.this.windowView.invalidate();
                    Runnable runnable2 = runnable;
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }
            }
        }
    }

    public void openShareConferenceLink() {
        ChatObject.Call call = this.call;
        if (call == null || call.call == null) {
            return;
        }
        final AlertDialog alertDialog = new AlertDialog(getContext(), 3);
        alertDialog.showDelayed(300L);
        final TL_phone.exportGroupCallInvite exportgroupcallinvite = new TL_phone.exportGroupCallInvite();
        TLRPC.TL_inputGroupCall tL_inputGroupCall = new TLRPC.TL_inputGroupCall();
        exportgroupcallinvite.call = tL_inputGroupCall;
        TLRPC.GroupCall groupCall = this.call.call;
        tL_inputGroupCall.f1632id = groupCall.f1625id;
        tL_inputGroupCall.access_hash = groupCall.access_hash;
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(exportgroupcallinvite, new RequestDelegate() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda77
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$openShareConferenceLink$82(alertDialog, exportgroupcallinvite, tLObject, tL_error);
            }
        });
    }

    public /* synthetic */ void lambda$openShareConferenceLink$82(final AlertDialog alertDialog, final TL_phone.exportGroupCallInvite exportgroupcallinvite, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCallActivity$$ExternalSyntheticLambda82
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$openShareConferenceLink$81(alertDialog, tLObject, exportgroupcallinvite, tL_error);
            }
        });
    }

    public /* synthetic */ void lambda$openShareConferenceLink$81(AlertDialog alertDialog, TLObject tLObject, TL_phone.exportGroupCallInvite exportgroupcallinvite, TLRPC.TL_error tL_error) {
        TLRPC.GroupCall groupCall;
        alertDialog.dismiss();
        if (!(tLObject instanceof TL_phone.exportedGroupCallInvite)) {
            if (tL_error != null) {
                BulletinFactory.m1194of(this.topBulletinContainer, new DarkBlueThemeResourcesProvider()).showForError(tL_error);
                return;
            }
            return;
        }
        Context context = getContext();
        int i = this.currentAccount;
        TLRPC.InputGroupCall inputGroupCall = exportgroupcallinvite.call;
        String str = ((TL_phone.exportedGroupCallInvite) tLObject).link;
        Theme.ResourcesProvider resourcesProvider = this.resourcesProvider;
        ChatObject.Call call = this.call;
        CallLogActivity.showCallLinkSheet(context, i, inputGroupCall, str, resourcesProvider, false, (call == null || (groupCall = call.call) == null || !groupCall.creator) ? false : true);
    }

    public int getAudioOutputValue() {
        Integer num = this.cacheAudioOutputValue;
        if (num != null) {
            return num.intValue();
        }
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        if (sharedInstance == null) {
            return 0;
        }
        int currentAudioRoute = sharedInstance.getCurrentAudioRoute();
        if (currentAudioRoute != 0) {
            return currentAudioRoute != 1 ? 2 : 0;
        }
        return 1;
    }

    public void setAudioOutputValue(int i) {
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        if (sharedInstance == null) {
            return;
        }
        sharedInstance.setAudioOutput(i);
        this.cacheAudioOutputValue = Integer.valueOf(i);
    }

    public int getNextAudioOutputValue() {
        if (getAudioOutputActive(getAudioOutputValue())) {
            return 1;
        }
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        return (sharedInstance == null || !sharedInstance.isBluetoothHeadsetConnected()) ? 0 : 2;
    }

    public String getAudioOutputName(int i) {
        String str;
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        if (i == 0) {
            return LocaleController.getString(C2702R.string.VoipAudioRoutingSpeaker);
        }
        if (i == 1) {
            return (sharedInstance == null || !sharedInstance.isHeadsetPlugged()) ? LocaleController.getString(C2702R.string.VoipAudioRoutingPhone) : LocaleController.getString(C2702R.string.VoipAudioRoutingHeadset);
        }
        if (i != 2) {
            return null;
        }
        return (sharedInstance == null || (str = sharedInstance.currentBluetoothDeviceName) == null) ? LocaleController.getString(C2702R.string.VoipAudioRoutingBluetooth) : str;
    }

    public int getAudioOutputIcon(int i) {
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        if (sharedInstance != null && sharedInstance.isBluetoothHeadsetConnected()) {
            return C2702R.drawable.filled_calls_bluetooth_s;
        }
        return C2702R.drawable.filled_sound_on;
    }

    public int getAudioOutputToastIcon(int i) {
        if (i == 2) {
            return C2702R.drawable.msg_voice_bluetooth;
        }
        if (i == 0) {
            return C2702R.drawable.msg_voice_speaker;
        }
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        return (sharedInstance == null || !sharedInstance.isHeadsetPlugged()) ? C2702R.drawable.msg_voice_phone : C2702R.drawable.msg_voice_headphones;
    }

    public String getAudioOutputToastText(int i) {
        String str;
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        if (i == 2) {
            return (sharedInstance == null || (str = sharedInstance.currentBluetoothDeviceName) == null) ? LocaleController.getString(C2702R.string.VoipAudioSwitchedToBluetooth) : LocaleController.formatString(C2702R.string.VoipAudioSwitchedToBluetoothDevice, str);
        }
        if (i == 1) {
            return (sharedInstance == null || !sharedInstance.isHeadsetPlugged()) ? LocaleController.getString(C2702R.string.VoipAudioSwitchedToPhone) : LocaleController.getString(C2702R.string.VoipAudioSwitchedToHeadset);
        }
        return LocaleController.getString(C2702R.string.VoipAudioSwitchedToSpeaker);
    }

    public boolean isBulletinTop() {
        int iM1081dp = AndroidUtilities.m1081dp(74.0f);
        float f = this.scrollOffsetY - iM1081dp;
        return (((((float) this.backgroundPaddingTop) + f) > ((float) ActionBar.getCurrentActionBarHeight()) ? 1 : ((((float) this.backgroundPaddingTop) + f) == ((float) ActionBar.getCurrentActionBarHeight()) ? 0 : -1)) < 0 ? Math.min(1.0f, ((((float) ActionBar.getCurrentActionBarHeight()) - f) - ((float) this.backgroundPaddingTop)) / ((float) ((iM1081dp - this.backgroundPaddingTop) - AndroidUtilities.m1081dp(14.0f)))) : 0.0f) > 0.5f;
    }

    public void updateTopBulletinY() {
        float fMin;
        if (this.topBulletinContainer == null) {
            return;
        }
        int iM1081dp = AndroidUtilities.m1081dp(74.0f);
        float currentActionBarHeight = this.scrollOffsetY - iM1081dp;
        if (this.backgroundPaddingTop + currentActionBarHeight < ActionBar.getCurrentActionBarHeight()) {
            fMin = Math.min(1.0f, ((ActionBar.getCurrentActionBarHeight() - currentActionBarHeight) - this.backgroundPaddingTop) / ((iM1081dp - this.backgroundPaddingTop) - AndroidUtilities.m1081dp(14.0f)));
            currentActionBarHeight -= (int) ((ActionBar.getCurrentActionBarHeight() - r0) * fMin);
        } else {
            fMin = 0.0f;
        }
        FrameLayout frameLayout = this.topBulletinContainer;
        frameLayout.setTranslationY(AndroidUtilities.lerp(((-frameLayout.getTop()) - this.topBulletinContainer.getHeight()) + currentActionBarHeight + this.containerView.getPaddingTop() + AndroidUtilities.m1081dp(10.0f), (-this.topBulletinContainer.getTop()) + this.actionBar.getY() + this.actionBar.getHeight(), fMin));
        Bulletin visibleBulletin = Bulletin.getVisibleBulletin();
        if (visibleBulletin == null || visibleBulletin.getLayout() == null || visibleBulletin.getLayout().getParent() == null || visibleBulletin.getLayout().getParent().getParent() != this.topBulletinContainer) {
            return;
        }
        visibleBulletin.getLayout().setTop(fMin > 0.5f);
    }

    private ReactionsContainerLayout createReactionsLayout() {
        ReactionsContainerLayout reactionsContainerLayout = this.reactionsContainerLayout;
        if (reactionsContainerLayout != null) {
            return reactionsContainerLayout;
        }
        BaseFragment lastFragment = LaunchActivity.getLastFragment();
        if (lastFragment == null) {
            return null;
        }
        ReactionsContainerLayout reactionsContainerLayout2 = new ReactionsContainerLayout(1, lastFragment, getContext(), this.currentAccount, this.resourcesProvider);
        this.reactionsContainerLayout = reactionsContainerLayout2;
        reactionsContainerLayout2.setDelegate(new ReactionsContainerLayout.ReactionsContainerDelegate() { // from class: org.telegram.ui.GroupCallActivity.66
            private final Paint bgPaint;
            private final Path clipPath = new Path();

            @Override // org.telegram.ui.Components.ReactionsContainerLayout.ReactionsContainerDelegate
            public boolean allowLongPress() {
                return false;
            }

            @Override // org.telegram.ui.Components.ReactionsContainerLayout.ReactionsContainerDelegate
            public boolean drawBackground() {
                return true;
            }

            @Override // org.telegram.ui.Components.ReactionsContainerLayout.ReactionsContainerDelegate
            public /* synthetic */ boolean needEnterText() {
                return ReactionsContainerLayout.ReactionsContainerDelegate.CC.$default$needEnterText(this);
            }

            @Override // org.telegram.ui.Components.ReactionsContainerLayout.ReactionsContainerDelegate
            public /* synthetic */ void onEmojiWindowDismissed() {
                ReactionsContainerLayout.ReactionsContainerDelegate.CC.$default$onEmojiWindowDismissed(this);
            }

            C560966() {
                Paint paint = new Paint(1);
                this.bgPaint = paint;
                paint.setColor(-14603467);
            }

            @Override // org.telegram.ui.Components.ReactionsContainerLayout.ReactionsContainerDelegate
            public void drawRoundRect(Canvas canvas, RectF rectF, float f, float f2, float f3, int i, boolean z) {
                if (f > 0.0f) {
                    canvas.drawRoundRect(rectF, f, f, this.bgPaint);
                } else {
                    canvas.drawRect(rectF, this.bgPaint);
                }
                if (Build.VERSION.SDK_INT < 29 || !canvas.isHardwareAccelerated() || GroupCallActivity.this.renderNodeBlur == null) {
                    return;
                }
                canvas.save();
                if (f > 0.0f) {
                    this.clipPath.rewind();
                    this.clipPath.addRoundRect(rectF, f, f, Path.Direction.CW);
                    this.clipPath.close();
                    canvas.clipPath(this.clipPath);
                } else {
                    canvas.clipRect(rectF);
                }
                canvas.translate(-GroupCallActivity.this.reactionsContainerLayout.getX(), -GroupCallActivity.this.reactionsContainerLayout.getY());
                canvas.scale(GroupCallActivity.this.renderNodeBlurScale, GroupCallActivity.this.renderNodeBlurScale);
                canvas.drawRenderNode(GroupCallActivity.this.renderNodeBlur);
                canvas.restore();
            }

            @Override // org.telegram.ui.Components.ReactionsContainerLayout.ReactionsContainerDelegate
            public void onReactionClicked(View view, ReactionsLayoutInBubble.VisibleReaction visibleReaction, boolean z, boolean z2) {
                TLRPC.TL_messageEntityCustomEmoji tL_messageEntityCustomEmoji = new TLRPC.TL_messageEntityCustomEmoji();
                String str = visibleReaction.emojicon;
                if (str == null) {
                    str = "👍";
                }
                TLRPC.TL_textWithEntities tL_textWithEntities = new TLRPC.TL_textWithEntities();
                tL_textWithEntities.text = str;
                long j = visibleReaction.documentId;
                if (j != 0) {
                    tL_messageEntityCustomEmoji.document_id = j;
                    tL_messageEntityCustomEmoji.offset = 0;
                    tL_messageEntityCustomEmoji.length = str.length();
                    tL_textWithEntities.entities.add(tL_messageEntityCustomEmoji);
                }
                GroupCallActivity.this.sendGroupCallMessage(tL_textWithEntities);
                GroupCallActivity.this.hideKeyboardOrEmojiView();
                CustomEmojiReactionsWindow reactionsWindow = GroupCallActivity.this.reactionsContainerLayout.getReactionsWindow();
                if (reactionsWindow == null || !reactionsWindow.isShowing()) {
                    return;
                }
                GroupCallActivity.this.reactionsContainerLayout.getReactionsWindow().dismissWithAlpha();
                GroupCallActivity.this.reactionsContainerLayout.reset();
            }
        });
        this.containerView.addView(this.reactionsContainerLayout, LayoutHelper.createFrame(-2, 52, 81));
        this.reactionsContainerLayout.setMessage(null, null, false);
        this.callMessageEnterUnderContainer.bringToFront();
        this.callMessageEnterContainer.bringToFront();
        return this.reactionsContainerLayout;
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCallActivity$66 */
    /* JADX INFO: loaded from: classes6.dex */
    class C560966 implements ReactionsContainerLayout.ReactionsContainerDelegate {
        private final Paint bgPaint;
        private final Path clipPath = new Path();

        @Override // org.telegram.ui.Components.ReactionsContainerLayout.ReactionsContainerDelegate
        public boolean allowLongPress() {
            return false;
        }

        @Override // org.telegram.ui.Components.ReactionsContainerLayout.ReactionsContainerDelegate
        public boolean drawBackground() {
            return true;
        }

        @Override // org.telegram.ui.Components.ReactionsContainerLayout.ReactionsContainerDelegate
        public /* synthetic */ boolean needEnterText() {
            return ReactionsContainerLayout.ReactionsContainerDelegate.CC.$default$needEnterText(this);
        }

        @Override // org.telegram.ui.Components.ReactionsContainerLayout.ReactionsContainerDelegate
        public /* synthetic */ void onEmojiWindowDismissed() {
            ReactionsContainerLayout.ReactionsContainerDelegate.CC.$default$onEmojiWindowDismissed(this);
        }

        C560966() {
            Paint paint = new Paint(1);
            this.bgPaint = paint;
            paint.setColor(-14603467);
        }

        @Override // org.telegram.ui.Components.ReactionsContainerLayout.ReactionsContainerDelegate
        public void drawRoundRect(Canvas canvas, RectF rectF, float f, float f2, float f3, int i, boolean z) {
            if (f > 0.0f) {
                canvas.drawRoundRect(rectF, f, f, this.bgPaint);
            } else {
                canvas.drawRect(rectF, this.bgPaint);
            }
            if (Build.VERSION.SDK_INT < 29 || !canvas.isHardwareAccelerated() || GroupCallActivity.this.renderNodeBlur == null) {
                return;
            }
            canvas.save();
            if (f > 0.0f) {
                this.clipPath.rewind();
                this.clipPath.addRoundRect(rectF, f, f, Path.Direction.CW);
                this.clipPath.close();
                canvas.clipPath(this.clipPath);
            } else {
                canvas.clipRect(rectF);
            }
            canvas.translate(-GroupCallActivity.this.reactionsContainerLayout.getX(), -GroupCallActivity.this.reactionsContainerLayout.getY());
            canvas.scale(GroupCallActivity.this.renderNodeBlurScale, GroupCallActivity.this.renderNodeBlurScale);
            canvas.drawRenderNode(GroupCallActivity.this.renderNodeBlur);
            canvas.restore();
        }

        @Override // org.telegram.ui.Components.ReactionsContainerLayout.ReactionsContainerDelegate
        public void onReactionClicked(View view, ReactionsLayoutInBubble.VisibleReaction visibleReaction, boolean z, boolean z2) {
            TLRPC.TL_messageEntityCustomEmoji tL_messageEntityCustomEmoji = new TLRPC.TL_messageEntityCustomEmoji();
            String str = visibleReaction.emojicon;
            if (str == null) {
                str = "👍";
            }
            TLRPC.TL_textWithEntities tL_textWithEntities = new TLRPC.TL_textWithEntities();
            tL_textWithEntities.text = str;
            long j = visibleReaction.documentId;
            if (j != 0) {
                tL_messageEntityCustomEmoji.document_id = j;
                tL_messageEntityCustomEmoji.offset = 0;
                tL_messageEntityCustomEmoji.length = str.length();
                tL_textWithEntities.entities.add(tL_messageEntityCustomEmoji);
            }
            GroupCallActivity.this.sendGroupCallMessage(tL_textWithEntities);
            GroupCallActivity.this.hideKeyboardOrEmojiView();
            CustomEmojiReactionsWindow reactionsWindow = GroupCallActivity.this.reactionsContainerLayout.getReactionsWindow();
            if (reactionsWindow == null || !reactionsWindow.isShowing()) {
                return;
            }
            GroupCallActivity.this.reactionsContainerLayout.getReactionsWindow().dismissWithAlpha();
            GroupCallActivity.this.reactionsContainerLayout.reset();
        }
    }

    public void hideKeyboardOrEmojiView() {
        if (this.callMessageEnterView.isPopupVisible()) {
            this.callMessageEnterView.hideEmojiView();
        } else {
            this.callMessageEnterView.closeKeyboard();
        }
    }

    public void sendGroupCallMessage(TLRPC.TL_textWithEntities tL_textWithEntities) {
        TLRPC.InputGroupCall inputGroupCall;
        long peerDialogId;
        this.callMessageEnterView.setText(_UrlKt.FRAGMENT_ENCODE_SET);
        ChatObject.Call call = this.call;
        if (call == null || call.call == null || (inputGroupCall = call.getInputGroupCall()) == null) {
            return;
        }
        ChatObject.Call call2 = this.call;
        long j = call2.call.f1625id;
        TLRPC.Peer peer = call2.selfPeer;
        if (peer != null) {
            peerDialogId = DialogObject.getPeerDialogId(peer);
        } else {
            peerDialogId = UserConfig.getInstance(this.currentAccount).clientUserId;
        }
        GroupCallMessagesController.getInstance(this.currentAccount).sendCallMessage(peerDialogId, tL_textWithEntities, j, inputGroupCall);
    }

    public void checkInsets() {
        checkGroupCallUiPositions_MessagesList();
        checkGroupCallUiAlpha_ReactionsLayout();
        checkGroupCallUiAlpha_EnterView();
        checkGroupCallUiPositions_EnterView();
        checkGroupCallUiPositions_ReactionsLayout();
        this.containerView.invalidate();
    }

    @Override // me.vkryl.android.animator.FactorAnimator.Target
    public void onFactorChanged(int i, float f, float f2, FactorAnimator factorAnimator) {
        if (i == 2) {
            checkGroupCallUiPositions_MessagesList();
            checkGroupCallUiPositions_ButtonsList();
            checkGroupCallUiPositions_FullscreenUsersList();
            checkGroupCallUiAlpha_ButtonsList();
            this.renderersContainer.setProgressToHideUi(f);
            this.fullscreenUsersListView.invalidate();
            this.containerView.invalidate();
            this.buttonsContainer.invalidate();
        }
        if (i == 3) {
            checkGroupCallUiPositions_ReactionsLayout();
            checkGroupCallUiPositions_MessagesList();
            checkGroupCallUiPositions_SendButton();
            checkGroupCallUiAlpha_ReactionsLayout();
            this.containerView.invalidate();
        }
        if (i == 4) {
            checkGroupCallUiPositions_MessagesList();
            this.callMessageEnterUnderContainer.invalidate();
            this.callMessageEnterView.invalidate();
        }
        if (i == 5) {
            checkGroupCallUiPositions_MessagesList();
            this.containerView.invalidate();
        }
    }

    private void checkGroupCallUi() {
        checkGroupCallUiPositions_EnterView();
        checkGroupCallUiPositions_ReactionsLayout();
        checkGroupCallUiPositions_MessagesList();
        checkGroupCallUiPositions_ButtonsList();
        checkGroupCallUiPositions_FullscreenUsersList();
        checkGroupCallUiPositions_SendButton();
        checkGroupCallUiAlpha_ButtonsList();
        checkGroupCallUiAlpha_EnterView();
        checkGroupCallUiAlpha_ReactionsLayout();
    }

    private void checkGroupCallUiPositions_EnterView() {
        this.callMessageEnterContainer.setTranslationY((-this.windowInsetsStateHolder.getAnimatedMaxBottomInset()) + this.containerView.getPaddingBottom());
        this.callMessageEnterUnderContainer.invalidate();
    }

    private void checkGroupCallUiPositions_ReactionsLayout() {
        if (this.reactionsContainerLayout != null) {
            this.reactionsContainerLayout.setTranslationY((-this.windowInsetsStateHolder.getAnimatedMaxBottomInset()) + this.containerView.getPaddingBottom() + ((-this.animatorMessageIsEmpty.getFloatValue()) * AndroidUtilities.m1081dp(64.0f)));
        }
    }

    public void checkGroupCallUiPositions_MessagesList() {
        float floatValue;
        float f = -((this.windowInsetsStateHolder.getAnimatedMaxBottomInset() - this.containerView.getPaddingBottom()) + this.animatorMessageInputHeight.getFactor() + (AndroidUtilities.m1081dp(68.0f) * this.animatorMessageIsEmpty.getFloatValue()) + AndroidUtilities.m1081dp(10.0f));
        if (isTabletMode) {
            floatValue = (1.0f - this.animatorHasVideo.getFloatValue()) * AndroidUtilities.m1081dp(-91.0f);
        } else {
            floatValue = isLandscapeMode ? 0.0f : ((this.animatorHideButtons.getFloatValue() * AndroidUtilities.m1081dp(94.0f)) - (AndroidUtilities.m1081dp(104.0f) * this.renderersContainer.progressToFullscreenMode)) - AndroidUtilities.m1081dp(91.0f);
        }
        float fLerp = AndroidUtilities.lerp(floatValue, f, this.windowInsetsStateHolder.getAnimatedKeyboardVisibility());
        float measuredHeight = ((this.containerView.getMeasuredHeight() - this.scrollOffsetY) + fLerp) - this.backgroundPaddingTop;
        float fMax = Math.max((measuredHeight / 3.0f) * 2.0f, measuredHeight - AndroidUtilities.m1081dp(250.0f));
        this.groupCallMessagesListView.setTranslationY(fLerp);
        this.groupCallMessagesListView.setVisibleHeight((int) fMax);
    }

    private void checkGroupCallUiPositions_ButtonsList() {
        if (isTabletMode) {
            this.buttonsContainer.setTranslationX(0.0f);
            this.buttonsContainer.setTranslationY(0.0f);
        } else if (isLandscapeMode) {
            this.buttonsContainer.setTranslationX(this.animatorHideButtons.getFloatValue() * AndroidUtilities.m1081dp(94.0f));
            this.buttonsContainer.setTranslationY(0.0f);
        } else {
            this.buttonsContainer.setTranslationX(0.0f);
            this.buttonsContainer.setTranslationY(this.animatorHideButtons.getFloatValue() * AndroidUtilities.m1081dp(94.0f));
        }
    }

    private void checkGroupCallUiPositions_FullscreenUsersList() {
        if (isTabletMode) {
            this.fullscreenUsersListView.setTranslationX(0.0f);
            this.fullscreenUsersListView.setTranslationY(0.0f);
        } else if (isLandscapeMode) {
            this.fullscreenUsersListView.setTranslationX(this.animatorHideButtons.getFloatValue() * AndroidUtilities.m1081dp(94.0f));
            this.fullscreenUsersListView.setTranslationY(0.0f);
        } else {
            this.fullscreenUsersListView.setTranslationX(0.0f);
            this.fullscreenUsersListView.setTranslationY(this.animatorHideButtons.getFloatValue() * AndroidUtilities.m1081dp(94.0f));
        }
    }

    private void checkGroupCallUiPositions_SendButton() {
        float floatValue = this.animatorMessageIsEmpty.getFloatValue();
        this.callMessageHideButton.setScaleX(AndroidUtilities.lerp(0.25f, 1.0f, floatValue));
        this.callMessageHideButton.setScaleY(AndroidUtilities.lerp(0.25f, 1.0f, floatValue));
        this.callMessageHideButton.setAlpha(floatValue);
        this.callMessageHideButton.setClickable(floatValue > 0.9f);
        float f = 1.0f - floatValue;
        this.callMessageSendButton.setScaleX(AndroidUtilities.lerp(0.25f, 1.0f, f));
        this.callMessageSendButton.setScaleY(AndroidUtilities.lerp(0.25f, 1.0f, f));
        this.callMessageSendButton.setAlpha(f);
        this.callMessageSendButton.setClickable(f > 0.9f);
    }

    private void checkGroupCallUiAlpha_ButtonsList() {
        this.buttonsContainer.setAlpha(1.0f - this.animatorHideButtons.getFloatValue());
    }

    private void checkGroupCallUiAlpha_EnterView() {
        float animatedKeyboardVisibility = this.windowInsetsStateHolder.getAnimatedKeyboardVisibility();
        int i = animatedKeyboardVisibility > 0.0f ? 0 : 8;
        this.callMessageEnterContainer.setAlpha(animatedKeyboardVisibility);
        this.callMessageEnterUnderContainer.setAlpha(animatedKeyboardVisibility);
        if (this.callMessageEnterContainer.getVisibility() != i) {
            this.callMessageEnterContainer.setVisibility(i);
            this.callMessageEnterUnderContainer.setVisibility(i);
            if (i == 8 && this.callMessageEnterView.isFocused()) {
                this.callMessageEnterView.clearFocus();
            }
        }
    }

    private void checkGroupCallUiAlpha_ReactionsLayout() {
        if (this.reactionsContainerLayout != null) {
            float animatedKeyboardVisibility = this.windowInsetsStateHolder.getAnimatedKeyboardVisibility() * this.animatorMessageIsEmpty.getFloatValue();
            this.reactionsContainerLayout.setAlpha(animatedKeyboardVisibility);
            int i = animatedKeyboardVisibility > 0.0f ? 0 : 8;
            if (this.reactionsContainerLayout.getVisibility() != i) {
                this.reactionsContainerLayout.setVisibility(i);
                if (i == 8) {
                    this.reactionsContainerLayout.reset();
                }
            }
            ReactionsContainerLayout reactionsContainerLayout = this.reactionsContainerLayout;
            if (reactionsContainerLayout.skipEnterAnimation || animatedKeyboardVisibility != 1.0f) {
                return;
            }
            reactionsContainerLayout.skipEnterAnimation = true;
        }
    }
}
