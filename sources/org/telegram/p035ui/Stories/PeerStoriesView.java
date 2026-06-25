package org.telegram.p035ui.Stories;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ReplacementSpan;
import android.text.style.URLSpan;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.collection.LongSparseArray;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.inputmethod.InputContentInfoCompat;
import androidx.recyclerview.widget.ChatListItemAnimator;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.utils.system.VibratorUtils;
import com.google.android.exoplayer2.util.Consumer;
import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import okhttp3.internal.url._UrlKt;
import org.scilab.forge.jlatexmath.TeXSymbolParser;
import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.AnimationNotificationsLocker;
import org.telegram.messenger.BotWebViewVibrationEffect;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.ChannelBoostsController;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationsController;
import org.telegram.messenger.NotificationsSettingsFacade;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.VideoEditedInfo;
import org.telegram.messenger.browser.Browser;
import org.telegram.messenger.camera.CameraView;
import org.telegram.p035ui.AccountFrozenAlert;
import org.telegram.p035ui.ActionBar.ActionBar;
import org.telegram.p035ui.ActionBar.ActionBarMenuItem;
import org.telegram.p035ui.ActionBar.ActionBarMenuSubItem;
import org.telegram.p035ui.ActionBar.ActionBarPopupWindow;
import org.telegram.p035ui.ActionBar.AdjustPanLayoutHelper;
import org.telegram.p035ui.ActionBar.AlertDialog;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.ActionBar.BottomSheet;
import org.telegram.p035ui.ActionBar.INavigationLayout;
import org.telegram.p035ui.ActionBar.SimpleTextView;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Cells.TextSelectionHelper;
import org.telegram.p035ui.ChatActivity;
import org.telegram.p035ui.ChooseSpeedLayout;
import org.telegram.p035ui.Components.AlertsCreator;
import org.telegram.p035ui.Components.AnimatedEmojiDrawable;
import org.telegram.p035ui.Components.AnimatedEmojiSpan;
import org.telegram.p035ui.Components.AnimatedFloat;
import org.telegram.p035ui.Components.AnimatedTextView;
import org.telegram.p035ui.Components.AvatarDrawable;
import org.telegram.p035ui.Components.AvatarsImageView;
import org.telegram.p035ui.Components.BackupImageView;
import org.telegram.p035ui.Components.BitmapShaderTools;
import org.telegram.p035ui.Components.Bulletin;
import org.telegram.p035ui.Components.BulletinFactory;
import org.telegram.p035ui.Components.BulletinFactory$$ExternalSyntheticLambda5;
import org.telegram.p035ui.Components.ChatActivityEnterView;
import org.telegram.p035ui.Components.ChatAttachAlert;
import org.telegram.p035ui.Components.ChatAttachAlertDocumentLayout;
import org.telegram.p035ui.Components.ColoredImageSpan;
import org.telegram.p035ui.Components.CombinedDrawable;
import org.telegram.p035ui.Components.CubicBezierInterpolator;
import org.telegram.p035ui.Components.CustomPopupMenu;
import org.telegram.p035ui.Components.EditTextCaption;
import org.telegram.p035ui.Components.EmojiPacksAlert;
import org.telegram.p035ui.Components.HashtagActivity;
import org.telegram.p035ui.Components.HintView;
import org.telegram.p035ui.Components.InstantCameraView;
import org.telegram.p035ui.Components.ItemOptions;
import org.telegram.p035ui.Components.LayoutHelper;
import org.telegram.p035ui.Components.LoadingDrawable;
import org.telegram.p035ui.Components.MediaActivity;
import org.telegram.p035ui.Components.MentionsContainerView;
import org.telegram.p035ui.Components.NumberTextView;
import org.telegram.p035ui.Components.Premium.LimitReachedBottomSheet;
import org.telegram.p035ui.Components.RLottieDrawable;
import org.telegram.p035ui.Components.RLottieImageView;
import org.telegram.p035ui.Components.RadialProgress;
import org.telegram.p035ui.Components.Reactions.AnimatedEmojiEffect;
import org.telegram.p035ui.Components.Reactions.ReactionImageHolder;
import org.telegram.p035ui.Components.Reactions.ReactionsEffectOverlay;
import org.telegram.p035ui.Components.Reactions.ReactionsLayoutInBubble;
import org.telegram.p035ui.Components.Reactions.ReactionsUtils;
import org.telegram.p035ui.Components.ReactionsContainerLayout;
import org.telegram.p035ui.Components.ScaleStateListAnimator;
import org.telegram.p035ui.Components.ShareAlert;
import org.telegram.p035ui.Components.SizeNotifierFrameLayout;
import org.telegram.p035ui.Components.SpeedIconDrawable;
import org.telegram.p035ui.Components.Text;
import org.telegram.p035ui.Components.TranslateAlert2;
import org.telegram.p035ui.Components.URLSpanMono;
import org.telegram.p035ui.Components.URLSpanNoUnderline;
import org.telegram.p035ui.Components.URLSpanReplacement;
import org.telegram.p035ui.Components.URLSpanUserMention;
import org.telegram.p035ui.Components.blur3.BlurredBackgroundDrawableViewFactory;
import org.telegram.p035ui.Components.blur3.drawable.BlurredBackgroundDrawable;
import org.telegram.p035ui.Components.blur3.drawable.color.BlurredBackgroundColorProviderThemed;
import org.telegram.p035ui.Components.blur3.source.BlurredBackgroundSource;
import org.telegram.p035ui.Components.blur3.source.BlurredBackgroundSourceColor;
import org.telegram.p035ui.Components.blur3.source.BlurredBackgroundSourceRenderNode;
import org.telegram.p035ui.Components.chat.ViewPositionWatcher;
import org.telegram.p035ui.Components.chat.layouts.ButtonOnClickListener;
import org.telegram.p035ui.Components.chat.layouts.ChatActivitySideControlsButtonsLayout;
import org.telegram.p035ui.Components.voip.CellFlickerDrawable;
import org.telegram.p035ui.DialogsActivity;
import org.telegram.p035ui.EmojiAnimationsOverlay;
import org.telegram.p035ui.LaunchActivity;
import org.telegram.p035ui.MessageStatisticActivity;
import org.telegram.p035ui.PinchToZoomHelper;
import org.telegram.p035ui.PremiumPreviewFragment;
import org.telegram.p035ui.ProfileActivity;
import org.telegram.p035ui.ReportBottomSheet;
import org.telegram.p035ui.Stars.StarsController;
import org.telegram.p035ui.Stars.StarsIntroActivity;
import org.telegram.p035ui.Stories.DialogStoriesCell;
import org.telegram.p035ui.Stories.PaidReactionButton;
import org.telegram.p035ui.Stories.PeerStoriesView;
import org.telegram.p035ui.Stories.SelfStoriesPreviewView;
import org.telegram.p035ui.Stories.StoriesController;
import org.telegram.p035ui.Stories.StoryCaptionView;
import org.telegram.p035ui.Stories.StoryViewer;
import org.telegram.p035ui.Stories.recorder.CaptionContainerView;
import org.telegram.p035ui.Stories.recorder.DraftsController;
import org.telegram.p035ui.Stories.recorder.HintView2;
import org.telegram.p035ui.Stories.recorder.StoryEntry;
import org.telegram.p035ui.Stories.recorder.StoryPrivacyBottomSheet;
import org.telegram.p035ui.Stories.recorder.StoryRecorder;
import org.telegram.p035ui.WrappedResourceProvider;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p034tl.TL_account;
import org.telegram.tgnet.p034tl.TL_phone;
import org.telegram.tgnet.p034tl.TL_stories;

/* JADX INFO: loaded from: classes7.dex */
public abstract class PeerStoriesView extends SizeNotifierFrameLayout implements NotificationCenter.NotificationCenterDelegate {
    public static boolean DISABLE_STORY_REPOSTING = false;
    private static int activeCount;
    private boolean BIG_SCREEN;
    private ActionBarMenuSubItem albumItem;
    private ViewGroup albumLayout;
    private boolean allowDrawSurface;
    Runnable allowDrawSurfaceRunnable;
    private boolean allowRepost;
    private boolean allowShare;
    private boolean allowShareLink;
    private float alpha;
    boolean animateKeyboardOpening;
    private float animatingKeyboardHeight;
    boolean areLiveCommentsDisabled;
    private boolean attachedToWindow;
    private final AvatarDrawable avatarDrawable;
    private final BitmapShaderTools bitmapShaderTools;
    private BlurredBackgroundColorProviderThemed blurredBackgroundColorProvider;
    private BlurredBackgroundDrawableViewFactory blurredBackgroundDrawableFactory;
    private final BlurredBackgroundSourceColor blurredBackgroundSourceFallback;
    private final BlurredBackgroundSourceRenderNode blurredBackgroundSourceRenderNodeWithSaturation;
    private final BlurredBackgroundSource blurredBackgroundSourceWithSaturation;
    private TL_stories.TL_premium_boostsStatus boostsStatus;
    private final LinearLayout bottomActionsLinearLayout;
    private ChannelBoostsController.CanApplyBoost canApplyBoost;
    private Runnable cancellableViews;
    private ValueAnimator changeBoundAnimator;
    ChatActivityEnterView chatActivityEnterView;
    private ChatAttachAlert chatAttachAlert;
    boolean checkBlackoutMode;
    private int classGuid;
    private final Path clipPath;
    private CommentButton commentButton;
    int count;
    private int currentAccount;
    private long currentImageTime;
    public final StoryItemHolder currentStory;
    ArrayList<Integer> day;
    Delegate delegate;
    private boolean deletedPeer;
    private long dialogId;
    ArrayList<TLRPC.Document> documentsToPrepare;
    private boolean drawAnimatedEmojiAsMovingReaction;
    private boolean drawReactionEffect;
    public boolean editOpened;
    ActionBarMenuSubItem editStoryItem;
    private boolean editedPrivacy;
    private EmojiAnimationsOverlay emojiAnimationsOverlay;
    BlurredBackgroundDrawable emojiKeyboardBackground;
    private AnimatedEmojiEffect emojiReactionEffect;
    private int enterViewBottomOffset;
    private StoryFailView failView;
    private ViewPropertyAnimator failViewAnimator;
    public boolean forceUpdateOffsets;
    PeerHeaderView headerView;
    HintView2 highlightMessageHintView;
    private boolean imageChanged;
    private final ImageReceiver imageReceiver;
    boolean inBlackoutMode;
    Paint inputBackgroundPaint;
    Paint inputBottomBorderPaint;
    BlurredBackgroundDrawable inputFieldBackground;
    Paint inputTopBorderPaint;
    private InstantCameraView instantCameraView;
    boolean isActive;
    private boolean isCaptionPartVisible;
    boolean isChannel;
    private boolean isEditing;
    private boolean isFailed;
    boolean isGroup;
    private boolean isLongPressed;
    boolean isPremiumBlocked;
    private boolean isRecording;
    boolean isSelf;
    private boolean isUploading;
    private boolean isVisible;
    ValueAnimator keyboardAnimator;
    public boolean keyboardVisible;
    float lastAnimatingKeyboardHeight;
    private long lastDrawTime;
    int lastKeyboardHeight;
    private boolean lastNoThumb;
    int lastOpenedKeyboardHeight;
    private final ImageReceiver leftPreloadImageReceiver;
    private final FrameLayout likeButtonContainer;
    private ReactionsContainerLayout likesReactionLayout;
    private float likesReactionShowProgress;
    private boolean likesReactionShowing;
    private AnimatedFloat linesAlpha;
    private int linesCount;
    private int linesPosition;
    private int listPosition;
    public final View liveCommentsShadowView;
    public final LiveCommentsView liveCommentsView;
    private HintView mediaBanTooltip;
    private MentionsContainerView mentionContainer;
    private MentionsContainerView.Delegate mentionsDelegate;
    private boolean messageSent;
    private long messageStars;
    private boolean movingReaction;
    private int movingReactionFromSize;
    private int movingReactionFromX;
    private int movingReactionFromY;
    private float movingReactionProgress;
    private MuteButton muteButton;
    private final FrameLayout muteIconContainer;
    private final RLottieImageView muteIconView;
    private float muteIconViewAlpha;
    private final ImageView noSoundIconView;
    final AnimationNotificationsLocker notificationsLocker;
    private Runnable onImageReceiverThumbLoaded;
    private final ImageView optionsIconView;
    private ValueAnimator outAnimator;
    private float outT;
    RoundRectOutlineProvider outlineProvider;
    private boolean paused;
    public PinchToZoomHelper pinchToZoomHelper;
    private final ImageView pipIconView;
    final VideoPlayerSharedScope playerSharedScope;
    CustomPopupMenu popupMenu;
    private final ArrayList<ReactionImageHolder> preloadReactionHolders;
    private LinearLayout premiumBlockedText;
    private TextView premiumBlockedText1;
    private TextView premiumBlockedText2;
    private float prevToHideProgress;
    private int previousSelectedPotision;
    private final StoryPrivacyButton privacyButton;
    private HintView2 privacyHint;
    float progressToDismiss;
    private AnimatedFloat progressToHideInterface;
    float progressToKeyboard;
    AnimatedFloat progressToRecording;
    float progressToReply;
    AnimatedFloat progressToStickerExpanded;
    AnimatedFloat progressToTextA;
    private ImageReceiver reactionEffectImageReceiver;
    private AnimatedEmojiDrawable reactionMoveDrawable;
    private ImageReceiver reactionMoveImageReceiver;
    private int reactionsContainerIndex;
    ReactionsContainerLayout reactionsContainerLayout;
    private AnimatedTextView.AnimatedTextDrawable reactionsCounter;
    private AnimatedFloat reactionsCounterProgress;
    private boolean reactionsCounterVisible;
    private HintView2 reactionsLongpressTooltip;
    private Runnable reactionsTooltipRunnable;
    private int realKeyboardHeight;
    private TextView replyDisabledTextView;
    private ImageView repostButton;
    private FrameLayout repostButtonContainer;
    private AnimatedTextView.AnimatedTextDrawable repostCounter;
    private AnimatedFloat repostCounterProgress;
    private boolean repostCounterVisible;
    private final Theme.ResourcesProvider resourcesProvider;
    private final ImageReceiver rightPreloadImageReceiver;
    private int selectedPosition;
    private View selfAvatarsContainer;
    private AvatarsImageView selfAvatarsView;
    private TextView selfStatusView;
    private FrameLayout selfView;
    private TLRPC.TL_channels_sendAsPeers sendAsPeersObj;
    public ShareAlert shareAlert;
    private final ImageView shareButton;
    final SharedResources sharedResources;
    private int shiftDp;
    private final Runnable showTapToSoundHint;
    boolean showViewsProgress;
    ChatActivitySideControlsButtonsLayout sideControlsButtonsLayout;
    private HintView2 soundTooltip;
    private ActionBarMenuSubItem speedItem;
    private ChooseSpeedLayout speedLayout;
    private PaidReactionButton starsButton;
    private PaidReactionButton.PaidReactionButtonEffectsView starsButtonEffectsView;
    long starsPriceBlocked;
    private boolean stealthModeIsActive;
    StoriesController storiesController;
    private StoriesLikeButton storiesLikeButton;
    private StoryMediaAreasView storyAreasView;
    private final StoryCaptionView storyCaptionView;
    public FrameLayout storyContainer;
    private CaptionContainerView storyEditCaptionView;
    final ArrayList<TL_stories.StoryItem> storyItems;
    private final StoryLinesDrawable storyLines;
    private StoryPositionView storyPositionView;
    private final StoryViewer storyViewer;
    private boolean switchEventSent;
    private long titleLastDialogId;
    private boolean titleLastLive;
    public FrameLayout topBulletinContainer;
    private int totalStoriesCount;
    public boolean unsupported;
    private FrameLayout unsupportedContainer;
    Runnable updateStealthModeTimer;
    final ArrayList<StoriesController.UploadingStory> uploadingStories;
    ArrayList<Uri> uriesToPrepare;
    private boolean userCanSeeViews;
    TL_stories.PeerStories userStories;
    public long videoDuration;
    private float viewsThumbAlpha;
    private SelfStoriesPreviewView.ImageHolder viewsThumbImageReceiver;
    private float viewsThumbPivotY;
    private float viewsThumbScale;
    private boolean wasBigScreen;
    private int watchersCount;

    public interface Delegate {
        int getKeyboardHeight();

        float getProgressToDismiss();

        boolean isClosed();

        void onPeerSelected(long j, int i);

        void preparePlayer(ArrayList<TLRPC.Document> arrayList, ArrayList<Uri> arrayList2);

        boolean releasePlayer(Runnable runnable);

        void requestAdjust(boolean z);

        void requestPlayer(TLRPC.Document document, Uri uri, long j, VideoPlayerSharedScope videoPlayerSharedScope);

        void requestPlayer(TL_stories.StoryItem storyItem, long j, int i, boolean z, TLRPC.InputGroupCall inputGroupCall, VideoPlayerSharedScope videoPlayerSharedScope);

        void setAllowTouchesByViewPager(boolean z);

        void setBulletinIsVisible(boolean z);

        void setHideEnterViewProgress(float f);

        void setIsCaption(boolean z);

        void setIsCaptionPartVisible(boolean z);

        void setIsHintVisible(boolean z);

        void setIsInPinchToZoom(boolean z);

        void setIsInSelectionMode(boolean z);

        void setIsLikesReaction(boolean z);

        void setIsRecording(boolean z);

        void setIsSwiping(boolean z);

        void setIsWaiting(boolean z);

        void setKeyboardVisible(boolean z);

        void setPopupIsVisible(boolean z);

        void setTranslating(boolean z);

        void shouldSwitchToNext();

        void showDialog(Dialog dialog);

        void switchToNextAndRemoveCurrentPeer();
    }

    public boolean drawLinesAsCounter() {
        return false;
    }

    public boolean hideCaptionWithInterface() {
        return true;
    }

    public abstract boolean isSelectedPeer();

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$1 */
    public class C68931 implements PinchToZoomHelper.Callback {
        public C68931() {
        }

        @Override // org.telegram.ui.PinchToZoomHelper.Callback
        public void onZoomStarted(MessageObject messageObject) {
            PeerStoriesView.this.delegate.setIsInPinchToZoom(true);
        }

        @Override // org.telegram.ui.PinchToZoomHelper.Callback
        public void onZoomFinished(MessageObject messageObject) {
            PeerStoriesView.this.delegate.setIsInPinchToZoom(false);
        }
    }

    public PeerStoriesView(final Context context, final StoryViewer storyViewer, final SharedResources sharedResources, final Theme.ResourcesProvider resourcesProvider) {
        super(context);
        this.allowDrawSurface = true;
        this.preloadReactionHolders = new ArrayList<>();
        this.shiftDp = -5;
        this.alpha = 1.0f;
        this.previousSelectedPotision = -1;
        StoryItemHolder storyItemHolder = new StoryItemHolder();
        this.currentStory = storyItemHolder;
        this.progressToKeyboard = -1.0f;
        this.progressToDismiss = -1.0f;
        this.lastAnimatingKeyboardHeight = -1.0f;
        this.classGuid = ConnectionsManager.generateClassGuid();
        this.progressToHideInterface = new AnimatedFloat(this);
        this.linesAlpha = new AnimatedFloat(this);
        this.pinchToZoomHelper = new PinchToZoomHelper();
        this.muteIconViewAlpha = 1.0f;
        this.updateStealthModeTimer = new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$43();
            }
        };
        this.showTapToSoundHint = new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$51();
            }
        };
        this.uriesToPrepare = new ArrayList<>();
        this.documentsToPrepare = new ArrayList<>();
        this.allowDrawSurfaceRunnable = new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView.34
            public RunnableC691834() {
            }

            @Override // java.lang.Runnable
            public void run() {
                PeerStoriesView peerStoriesView = PeerStoriesView.this;
                if (peerStoriesView.isActive && peerStoriesView.allowDrawSurface) {
                    PeerStoriesView.this.delegate.setIsSwiping(false);
                }
            }
        };
        this.progressToRecording = new AnimatedFloat(this);
        this.progressToTextA = new AnimatedFloat(this);
        this.progressToStickerExpanded = new AnimatedFloat(this);
        this.clipPath = new Path();
        this.pinchToZoomHelper.setCallback(new PinchToZoomHelper.Callback() { // from class: org.telegram.ui.Stories.PeerStoriesView.1
            public C68931() {
            }

            @Override // org.telegram.ui.PinchToZoomHelper.Callback
            public void onZoomStarted(MessageObject messageObject) {
                PeerStoriesView.this.delegate.setIsInPinchToZoom(true);
            }

            @Override // org.telegram.ui.PinchToZoomHelper.Callback
            public void onZoomFinished(MessageObject messageObject) {
                PeerStoriesView.this.delegate.setIsInPinchToZoom(false);
            }
        });
        this.playerSharedScope = new VideoPlayerSharedScope();
        this.notificationsLocker = new AnimationNotificationsLocker();
        this.storyItems = new ArrayList<>();
        this.uploadingStories = new ArrayList<>();
        C69022 c69022 = new ImageReceiver() { // from class: org.telegram.ui.Stories.PeerStoriesView.2
            public C69022() {
            }

            @Override // org.telegram.messenger.ImageReceiver
            public boolean setImageBitmapByKey(Drawable drawable, String str, int i, boolean z, int i2) {
                boolean imageBitmapByKey = super.setImageBitmapByKey(drawable, str, i, z, i2);
                if (i == 1 && PeerStoriesView.this.onImageReceiverThumbLoaded != null) {
                    PeerStoriesView.this.onImageReceiverThumbLoaded.run();
                    PeerStoriesView.this.onImageReceiverThumbLoaded = null;
                }
                return imageBitmapByKey;
            }
        };
        this.imageReceiver = c69022;
        c69022.setCrossfadeWithOldImage(false);
        c69022.setAllowLoadingOnAttachedOnly(true);
        c69022.ignoreNotifications = true;
        c69022.setFileLoadingPriority(0);
        ImageReceiver imageReceiver = new ImageReceiver(this);
        this.reactionEffectImageReceiver = imageReceiver;
        imageReceiver.setAllowLoadingOnAttachedOnly(true);
        ImageReceiver imageReceiver2 = this.reactionEffectImageReceiver;
        imageReceiver2.ignoreNotifications = true;
        imageReceiver2.setFileLoadingPriority(3);
        ImageReceiver imageReceiver3 = new ImageReceiver(this);
        this.reactionMoveImageReceiver = imageReceiver3;
        imageReceiver3.setAllowLoadingOnAttachedOnly(true);
        ImageReceiver imageReceiver4 = this.reactionMoveImageReceiver;
        imageReceiver4.ignoreNotifications = true;
        imageReceiver4.setFileLoadingPriority(3);
        ImageReceiver imageReceiver5 = new ImageReceiver();
        this.leftPreloadImageReceiver = imageReceiver5;
        imageReceiver5.setAllowLoadingOnAttachedOnly(true);
        imageReceiver5.ignoreNotifications = true;
        imageReceiver5.setFileLoadingPriority(0);
        ImageReceiver imageReceiver6 = new ImageReceiver();
        this.rightPreloadImageReceiver = imageReceiver6;
        imageReceiver6.setAllowLoadingOnAttachedOnly(true);
        imageReceiver6.ignoreNotifications = true;
        imageReceiver6.setFileLoadingPriority(0);
        c69022.setPreloadingReceivers(Arrays.asList(imageReceiver5, imageReceiver6));
        this.avatarDrawable = new AvatarDrawable();
        this.storyViewer = storyViewer;
        this.sharedResources = sharedResources;
        this.bitmapShaderTools = sharedResources.bitmapShaderTools;
        this.storiesController = MessagesController.getInstance(UserConfig.selectedAccount).getStoriesController();
        sharedResources.dimPaint.setColor(-16777216);
        this.inputBackgroundPaint = new Paint(1);
        Paint paint = new Paint(1);
        this.inputTopBorderPaint = paint;
        Paint.Style style = Paint.Style.STROKE;
        paint.setStyle(style);
        this.inputTopBorderPaint.setColor(687865855);
        Paint paint2 = new Paint(1);
        this.inputBottomBorderPaint = paint2;
        paint2.setStyle(style);
        this.inputBottomBorderPaint.setColor(352321535);
        this.resourcesProvider = resourcesProvider;
        setClipChildren(false);
        this.storyAreasView = new StoryMediaAreasView(context, this.storyContainer, resourcesProvider) { // from class: org.telegram.ui.Stories.PeerStoriesView.3
            final /* synthetic */ StoryViewer val$storyViewer;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C69133(final Context context2, View view, final Theme.ResourcesProvider resourcesProvider2, final StoryViewer storyViewer2) {
                super(context2, view, resourcesProvider2);
                storyViewer = storyViewer2;
            }

            @Override // org.telegram.p035ui.Stories.StoryMediaAreasView
            public void onHintVisible(boolean z) {
                Delegate delegate = PeerStoriesView.this.delegate;
                if (delegate != null) {
                    delegate.setIsHintVisible(z);
                }
            }

            @Override // org.telegram.p035ui.Stories.StoryMediaAreasView
            public void presentFragment(BaseFragment baseFragment) {
                StoryViewer storyViewer2 = storyViewer;
                if (storyViewer2 != null) {
                    storyViewer2.presentFragment(baseFragment);
                }
            }

            @Override // org.telegram.p035ui.Stories.StoryMediaAreasView, android.view.View
            public boolean onTouchEvent(MotionEvent motionEvent) {
                return super.onTouchEvent(motionEvent);
            }

            @Override // android.view.ViewGroup, android.view.View
            public boolean dispatchTouchEvent(MotionEvent motionEvent) {
                return super.dispatchTouchEvent(motionEvent);
            }

            @Override // org.telegram.p035ui.Stories.StoryMediaAreasView
            public void showEffect(StoryReactionWidgetView storyReactionWidgetView) {
                PeerStoriesView peerStoriesView = PeerStoriesView.this;
                if (!peerStoriesView.isSelf && peerStoriesView.currentStory.storyItem != null) {
                    ReactionsLayoutInBubble.VisibleReaction visibleReactionFromTL = ReactionsLayoutInBubble.VisibleReaction.fromTL(storyReactionWidgetView.mediaArea.reaction);
                    if (!Objects.equals(visibleReactionFromTL, ReactionsLayoutInBubble.VisibleReaction.fromTL(PeerStoriesView.this.currentStory.storyItem.sent_reaction))) {
                        PeerStoriesView.this.likeStory(visibleReactionFromTL);
                    }
                }
                storyReactionWidgetView.performHapticFeedback(3);
                storyReactionWidgetView.playAnimation();
                PeerStoriesView.this.emojiAnimationsOverlay.showAnimationForWidget(storyReactionWidgetView);
            }

            @Override // org.telegram.p035ui.Stories.StoryMediaAreasView
            public Bitmap getPlayingBitmap() {
                return PeerStoriesView.this.getPlayingBitmap();
            }
        };
        this.blurredBackgroundColorProvider = new BlurredBackgroundColorProviderThemed(resourcesProvider2, Theme.key_chat_messagePanelBackground, 0.8f);
        BlurredBackgroundSourceColor blurredBackgroundSourceColor = new BlurredBackgroundSourceColor();
        this.blurredBackgroundSourceFallback = blurredBackgroundSourceColor;
        blurredBackgroundSourceColor.setColor(ColorUtils.blendARGB(-16777216, -1, 0.2f));
        if (Build.VERSION.SDK_INT >= 31 && SharedConfig.canBlurChat()) {
            BlurredBackgroundSourceRenderNode blurredBackgroundSourceRenderNode = new BlurredBackgroundSourceRenderNode(blurredBackgroundSourceColor);
            this.blurredBackgroundSourceRenderNodeWithSaturation = blurredBackgroundSourceRenderNode;
            blurredBackgroundSourceRenderNode.setBlur(AndroidUtilities.m1036dp(8.0f));
            this.blurredBackgroundSourceWithSaturation = blurredBackgroundSourceRenderNode;
        } else {
            this.blurredBackgroundSourceRenderNodeWithSaturation = null;
            this.blurredBackgroundSourceWithSaturation = blurredBackgroundSourceColor;
        }
        BlurredBackgroundDrawableViewFactory blurredBackgroundDrawableViewFactory = new BlurredBackgroundDrawableViewFactory(new ViewPositionWatcher(this), this, this.blurredBackgroundSourceWithSaturation);
        this.blurredBackgroundDrawableFactory = blurredBackgroundDrawableViewFactory;
        this.inputFieldBackground = blurredBackgroundDrawableViewFactory.create(this, this.blurredBackgroundColorProvider);
        BlurredBackgroundDrawable blurredBackgroundDrawableCreate = this.blurredBackgroundDrawableFactory.create(this, this.blurredBackgroundColorProvider);
        this.emojiKeyboardBackground = blurredBackgroundDrawableCreate;
        blurredBackgroundDrawableCreate.setThickness(AndroidUtilities.m1036dp(32.0f));
        C69244 c69244 = new C69244(context2, sharedResources, storyViewer2);
        this.storyContainer = c69244;
        c69244.setClipChildren(false);
        this.emojiAnimationsOverlay = new EmojiAnimationsOverlay(this.storyContainer, this.currentAccount);
        this.storyContainer.addView(this.storyAreasView, LayoutHelper.createFrame(-1, -1.0f));
        C69275 c69275 = new C69275(getContext(), storyViewer2.resourcesProvider, storyViewer2, resourcesProvider2);
        this.storyCaptionView = c69275;
        c69275.captionTextview.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$0(view);
            }
        });
        ImageView imageView = new ImageView(context2);
        this.shareButton = imageView;
        imageView.setImageDrawable(sharedResources.shareDrawable);
        int iM1036dp = AndroidUtilities.m1036dp(8.0f);
        imageView.setPadding(iM1036dp, iM1036dp, iM1036dp, iM1036dp);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$1(view);
            }
        });
        ScaleStateListAnimator.apply(imageView);
        if (!DISABLE_STORY_REPOSTING) {
            ImageView imageView2 = new ImageView(context2);
            this.repostButton = imageView2;
            imageView2.setImageDrawable(sharedResources.repostDrawable);
            this.repostButton.setPadding(iM1036dp, iM1036dp, iM1036dp, iM1036dp);
            C69286 c69286 = new FrameLayout(getContext()) { // from class: org.telegram.ui.Stories.PeerStoriesView.6
                public C69286(Context context2) {
                    super(context2);
                }

                @Override // android.view.ViewGroup, android.view.View
                public void dispatchDraw(Canvas canvas) {
                    super.dispatchDraw(canvas);
                    PeerStoriesView peerStoriesView = PeerStoriesView.this;
                    if (!peerStoriesView.isChannel || peerStoriesView.repostCounter == null) {
                        return;
                    }
                    canvas.save();
                    canvas.translate((getMeasuredWidth() - PeerStoriesView.this.repostCounter.getCurrentWidth()) - AndroidUtilities.m1036dp(6.0f), 0.0f);
                    float f = PeerStoriesView.this.repostCounterProgress.set(PeerStoriesView.this.repostCounterVisible ? 1.0f : 0.0f);
                    canvas.scale(f, f, PeerStoriesView.this.repostCounter.getCurrentWidth() / 2.0f, AndroidUtilities.m1036dp(20.0f));
                    PeerStoriesView.this.repostCounter.setAlpha(255);
                    PeerStoriesView.this.repostCounter.draw(canvas);
                    canvas.restore();
                }

                @Override // android.view.View
                public boolean verifyDrawable(Drawable drawable) {
                    return drawable == PeerStoriesView.this.repostCounter || super.verifyDrawable(drawable);
                }
            };
            this.repostButtonContainer = c69286;
            AnimatedTextView.AnimatedTextDrawable animatedTextDrawable = this.repostCounter;
            if (animatedTextDrawable != null) {
                animatedTextDrawable.setCallback(c69286);
            }
            this.repostButtonContainer.setWillNotDraw(false);
            this.repostButtonContainer.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda9
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$new$2(view);
                }
            });
        }
        C69297 c69297 = new FrameLayout(getContext()) { // from class: org.telegram.ui.Stories.PeerStoriesView.7
            public C69297(Context context2) {
                super(context2);
            }

            @Override // android.view.ViewGroup, android.view.View
            public void dispatchDraw(Canvas canvas) {
                super.dispatchDraw(canvas);
                PeerStoriesView peerStoriesView = PeerStoriesView.this;
                if (!peerStoriesView.isChannel || peerStoriesView.reactionsCounter == null) {
                    return;
                }
                canvas.save();
                canvas.translate((getMeasuredWidth() - PeerStoriesView.this.reactionsCounter.getCurrentWidth()) - AndroidUtilities.m1036dp(6.0f), 0.0f);
                float f = PeerStoriesView.this.reactionsCounterProgress.set(PeerStoriesView.this.reactionsCounterVisible ? 1.0f : 0.0f);
                canvas.scale(f, f, PeerStoriesView.this.reactionsCounter.getCurrentWidth() / 2.0f, AndroidUtilities.m1036dp(20.0f));
                PeerStoriesView.this.reactionsCounter.setAlpha(255);
                PeerStoriesView.this.reactionsCounter.draw(canvas);
                canvas.restore();
            }

            @Override // android.view.View
            public boolean verifyDrawable(Drawable drawable) {
                return drawable == PeerStoriesView.this.reactionsCounter || super.verifyDrawable(drawable);
            }
        };
        this.likeButtonContainer = c69297;
        AnimatedTextView.AnimatedTextDrawable animatedTextDrawable2 = this.reactionsCounter;
        if (animatedTextDrawable2 != null) {
            animatedTextDrawable2.setCallback(c69297);
        }
        c69297.setWillNotDraw(false);
        c69297.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$4(view);
            }
        });
        c69297.setOnLongClickListener(new View.OnLongClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda11
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return this.f$0.lambda$new$5(storyViewer2, view);
            }
        });
        StoriesLikeButton storiesLikeButton = new StoriesLikeButton(context2, sharedResources);
        this.storiesLikeButton = storiesLikeButton;
        storiesLikeButton.setPadding(iM1036dp, iM1036dp, iM1036dp, iM1036dp);
        c69297.addView(this.storiesLikeButton, LayoutHelper.createFrame(40, 40, 3));
        FrameLayout frameLayout = this.repostButtonContainer;
        if (frameLayout != null) {
            frameLayout.addView(this.repostButton, LayoutHelper.createFrame(40, 40, 3));
        }
        ScaleStateListAnimator.apply(c69297, 0.3f, 5.0f);
        FrameLayout frameLayout2 = this.repostButtonContainer;
        if (frameLayout2 != null) {
            ScaleStateListAnimator.apply(frameLayout2, 0.3f, 5.0f);
        }
        c69022.setAllowLoadingOnAttachedOnly(true);
        c69022.setParentView(this.storyContainer);
        RoundRectOutlineProvider roundRectOutlineProvider = new RoundRectOutlineProvider(10);
        this.outlineProvider = roundRectOutlineProvider;
        this.storyContainer.setOutlineProvider(roundRectOutlineProvider);
        this.storyContainer.setClipToOutline(true);
        addView(this.storyContainer);
        PeerHeaderView peerHeaderView = new PeerHeaderView(context2, storyItemHolder);
        this.headerView = peerHeaderView;
        peerHeaderView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda12
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$6(storyViewer2, view);
            }
        });
        this.storyContainer.addView(this.headerView, LayoutHelper.createFrame(-1, -2.0f, 0, 0.0f, 17.0f, 0.0f, 0.0f));
        this.topBulletinContainer = new FrameLayout(context2);
        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.setDuration(150L);
        layoutTransition.disableTransitionType(2);
        layoutTransition.enableTransitionType(4);
        LinearLayout linearLayout = new LinearLayout(context2);
        this.bottomActionsLinearLayout = linearLayout;
        linearLayout.setOrientation(0);
        linearLayout.setLayoutTransition(layoutTransition);
        linearLayout.addView(imageView, LayoutHelper.createLinear(40, 40, 5));
        FrameLayout frameLayout3 = this.repostButtonContainer;
        if (frameLayout3 != null) {
            linearLayout.addView(frameLayout3, LayoutHelper.createLinear(40, 40, 5));
        }
        linearLayout.addView(c69297, LayoutHelper.createLinear(40, 40, 5));
        addView(linearLayout, LayoutHelper.createFrame(-2, -2.0f, 5, 0.0f, 0.0f, 4.0f, 0.0f));
        ImageView imageView3 = new ImageView(context2);
        this.optionsIconView = imageView3;
        imageView3.setImageDrawable(sharedResources.optionsDrawable);
        imageView3.setPadding(AndroidUtilities.m1036dp(8.0f), AndroidUtilities.m1036dp(8.0f), AndroidUtilities.m1036dp(8.0f), AndroidUtilities.m1036dp(8.0f));
        imageView3.setBackground(Theme.createSelectorDrawable(-1));
        this.storyContainer.addView(imageView3, LayoutHelper.createFrame(40, 40.0f, 53, 2.0f, 15.0f, 2.0f, 0.0f));
        ImageView imageView4 = new ImageView(context2);
        this.pipIconView = imageView4;
        imageView4.setImageDrawable(sharedResources.pipDrawable);
        imageView4.setPadding(AndroidUtilities.m1036dp(8.0f), AndroidUtilities.m1036dp(8.0f), AndroidUtilities.m1036dp(8.0f), AndroidUtilities.m1036dp(8.0f));
        imageView4.setBackground(Theme.createSelectorDrawable(-1));
        this.storyContainer.addView(imageView4, LayoutHelper.createFrame(40, 40.0f, 53, 2.0f, 15.0f, 42.0f, 0.0f));
        imageView4.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PeerStoriesView.$r8$lambda$yJHJR1bijBsghFqqIA0QqbKSXoo(storyViewer2, view);
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda14
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$8(resourcesProvider2, storyViewer2, context2, sharedResources, view);
            }
        });
        C69319 c69319 = new FrameLayout(context2) { // from class: org.telegram.ui.Stories.PeerStoriesView.9
            public C69319(final Context context2) {
                super(context2);
            }

            @Override // android.view.View
            public boolean onTouchEvent(MotionEvent motionEvent) {
                return super.onTouchEvent(motionEvent);
            }

            @Override // android.view.ViewGroup, android.view.View
            public boolean dispatchTouchEvent(MotionEvent motionEvent) {
                return super.dispatchTouchEvent(motionEvent);
            }
        };
        this.muteIconContainer = c69319;
        this.storyContainer.addView(c69319, LayoutHelper.createFrame(40, 40.0f, 53, 2.0f, 15.0f, 42.0f, 0.0f));
        RLottieImageView rLottieImageView = new RLottieImageView(context2);
        this.muteIconView = rLottieImageView;
        rLottieImageView.setPadding(AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(6.0f));
        c69319.addView(rLottieImageView);
        ImageView imageView5 = new ImageView(context2);
        this.noSoundIconView = imageView5;
        imageView5.setPadding(AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(6.0f));
        imageView5.setImageDrawable(sharedResources.noSoundDrawable);
        c69319.addView(imageView5);
        imageView5.setVisibility(8);
        StoryPrivacyButton storyPrivacyButton = new StoryPrivacyButton(context2);
        this.privacyButton = storyPrivacyButton;
        storyPrivacyButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$10(view);
            }
        });
        this.storyContainer.addView(storyPrivacyButton, LayoutHelper.createFrame(60, 40.0f, 53, 2.0f, 15.0f, 42.0f, 0.0f));
        c69319.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$11(storyViewer2, view);
            }
        });
        this.storyLines = new StoryLinesDrawable(this, sharedResources);
        this.storyContainer.addView(c69275, LayoutHelper.createFrame(-1, -1.0f, 0, 0.0f, 64.0f, 0.0f, 0.0f));
        View view = new View(context2);
        this.liveCommentsShadowView = view;
        view.setBackground(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{0, -16777216}));
        C689410 c689410 = new LiveCommentsView(context2, storyViewer2, storyViewer2.containerView, view, this.topBulletinContainer) { // from class: org.telegram.ui.Stories.PeerStoriesView.10
            final /* synthetic */ StoryViewer val$storyViewer;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C689410(final Context context2, final StoryViewer storyViewer2, ViewGroup viewGroup, View view2, FrameLayout frameLayout4, final StoryViewer storyViewer22) {
                super(context2, storyViewer22, viewGroup, view2, frameLayout4);
                storyViewer = storyViewer22;
            }

            @Override // android.view.View
            public void setVisibility(int i) {
                super.setVisibility(i);
                PeerStoriesView.this.liveCommentsShadowView.setVisibility(i);
            }

            @Override // org.telegram.p035ui.Stories.LiveCommentsView
            public TLRPC.Peer getDefaultSendAs() {
                LivePlayer livePlayer = storyViewer.livePlayer;
                if (livePlayer != null) {
                    return livePlayer.getDefaultSendAs();
                }
                return null;
            }

            @Override // org.telegram.p035ui.Stories.LiveCommentsView
            public boolean isMe(long j) {
                if (j == UserConfig.getInstance(PeerStoriesView.this.currentAccount).getClientUserId()) {
                    return true;
                }
                LivePlayer livePlayer = storyViewer.livePlayer;
                if (livePlayer != null && j == DialogObject.getPeerDialogId(livePlayer.getDefaultSendAs())) {
                    return true;
                }
                if (PeerStoriesView.this.sendAsPeersObj != null) {
                    for (int i = 0; i < PeerStoriesView.this.sendAsPeersObj.peers.size(); i++) {
                        if (j == DialogObject.getPeerDialogId(PeerStoriesView.this.sendAsPeersObj.peers.get(i).peer)) {
                            return true;
                        }
                    }
                }
                return false;
            }

            @Override // org.telegram.p035ui.Stories.LiveCommentsView
            public void setCollapsed(boolean z, boolean z2) {
                super.setCollapsed(z, z2);
                if (PeerStoriesView.this.commentButton != null) {
                    PeerStoriesView.this.commentButton.setCollapsed(z, z2);
                }
            }

            @Override // org.telegram.p035ui.Stories.LiveCommentsView
            public void onMessagesCountUpdated() {
                if (PeerStoriesView.this.commentButton != null) {
                    PeerStoriesView.this.commentButton.setCount(getUnreadMessagesCount());
                }
            }

            @Override // org.telegram.p035ui.Stories.LiveCommentsView
            public void onStarsCountUpdated() {
                PeerStoriesView.this.starsButton.setCount((int) getStarsCount());
                PeerStoriesView.this.starsButton.setFilled(areSendingStars());
            }

            @Override // org.telegram.p035ui.Stories.LiveCommentsView
            public void onStarsButtonPressed(long j, boolean z) {
                PeerStoriesView peerStoriesView = PeerStoriesView.this;
                if (z) {
                    peerStoriesView.starsButton.playEffect(j);
                } else {
                    peerStoriesView.starsButton.stopEffects();
                }
            }

            @Override // org.telegram.p035ui.Stories.LiveCommentsView
            public void onStarReaction(long j, int i, int i2) {
                if (PeerStoriesView.this.starsButtonEffectsView == null) {
                    return;
                }
                PeerStoriesView.this.starsButtonEffectsView.pushChip(j, i, i2);
            }

            @Override // org.telegram.p035ui.Stories.LiveCommentsView
            public void onCancelledStarReaction(long j) {
                if (PeerStoriesView.this.starsButtonEffectsView == null) {
                    return;
                }
                PeerStoriesView.this.starsButtonEffectsView.removeChipsFrom(j);
            }

            @Override // org.telegram.p035ui.Stories.LiveCommentsView
            public void onStarsButtonCancelled() {
                PeerStoriesView.this.starsButton.stopEffects();
            }
        };
        this.liveCommentsView = c689410;
        this.storyContainer.addView(view2, LayoutHelper.createFrame(-1, 200, 87));
        this.storyContainer.addView(c689410, LayoutHelper.createFrame(-1, -1.0f, 0, 0.0f, 64.0f, 0.0f, 0.0f));
        this.storyContainer.addView(this.topBulletinContainer, LayoutHelper.createFrame(-1, 100.0f, 0, 0.0f, 55.0f, 0.0f, 0.0f));
        c69319.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1036dp(20.0f), 0, ColorUtils.setAlphaComponent(-1, 100)));
        imageView3.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1036dp(20.0f), 0, ColorUtils.setAlphaComponent(-1, 100)));
        imageView4.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1036dp(20.0f), 0, ColorUtils.setAlphaComponent(-1, 100)));
        imageView.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1036dp(20.0f), 0, ColorUtils.setAlphaComponent(-1, 100)));
        c69297.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1036dp(20.0f), 0, ColorUtils.setAlphaComponent(-1, 100)));
        FrameLayout frameLayout4 = this.repostButtonContainer;
        if (frameLayout4 != null) {
            frameLayout4.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1036dp(20.0f), 0, ColorUtils.setAlphaComponent(-1, 100)));
        }
        View overlayView = c69275.textSelectionHelper.getOverlayView(context2);
        if (overlayView != null) {
            AndroidUtilities.removeFromParent(overlayView);
            addView(overlayView);
        }
        c69275.textSelectionHelper.setCallback(new TextSelectionHelper.Callback() { // from class: org.telegram.ui.Stories.PeerStoriesView.11
            public C689511() {
            }

            @Override // org.telegram.ui.Cells.TextSelectionHelper.Callback
            public void onStateChanged(boolean z) {
                PeerStoriesView peerStoriesView = PeerStoriesView.this;
                peerStoriesView.delegate.setIsInSelectionMode(peerStoriesView.storyCaptionView.textSelectionHelper.isInSelectionMode());
            }
        });
        c69275.textSelectionHelper.setParentView(this);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$2 */
    public class C69022 extends ImageReceiver {
        public C69022() {
        }

        @Override // org.telegram.messenger.ImageReceiver
        public boolean setImageBitmapByKey(Drawable drawable, String str, int i, boolean z, int i2) {
            boolean imageBitmapByKey = super.setImageBitmapByKey(drawable, str, i, z, i2);
            if (i == 1 && PeerStoriesView.this.onImageReceiverThumbLoaded != null) {
                PeerStoriesView.this.onImageReceiverThumbLoaded.run();
                PeerStoriesView.this.onImageReceiverThumbLoaded = null;
            }
            return imageBitmapByKey;
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$3 */
    public class C69133 extends StoryMediaAreasView {
        final /* synthetic */ StoryViewer val$storyViewer;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C69133(final Context context2, View view, final Theme.ResourcesProvider resourcesProvider2, final StoryViewer storyViewer22) {
            super(context2, view, resourcesProvider2);
            storyViewer = storyViewer22;
        }

        @Override // org.telegram.p035ui.Stories.StoryMediaAreasView
        public void onHintVisible(boolean z) {
            Delegate delegate = PeerStoriesView.this.delegate;
            if (delegate != null) {
                delegate.setIsHintVisible(z);
            }
        }

        @Override // org.telegram.p035ui.Stories.StoryMediaAreasView
        public void presentFragment(BaseFragment baseFragment) {
            StoryViewer storyViewer2 = storyViewer;
            if (storyViewer2 != null) {
                storyViewer2.presentFragment(baseFragment);
            }
        }

        @Override // org.telegram.p035ui.Stories.StoryMediaAreasView, android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            return super.onTouchEvent(motionEvent);
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            return super.dispatchTouchEvent(motionEvent);
        }

        @Override // org.telegram.p035ui.Stories.StoryMediaAreasView
        public void showEffect(StoryReactionWidgetView storyReactionWidgetView) {
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            if (!peerStoriesView.isSelf && peerStoriesView.currentStory.storyItem != null) {
                ReactionsLayoutInBubble.VisibleReaction visibleReactionFromTL = ReactionsLayoutInBubble.VisibleReaction.fromTL(storyReactionWidgetView.mediaArea.reaction);
                if (!Objects.equals(visibleReactionFromTL, ReactionsLayoutInBubble.VisibleReaction.fromTL(PeerStoriesView.this.currentStory.storyItem.sent_reaction))) {
                    PeerStoriesView.this.likeStory(visibleReactionFromTL);
                }
            }
            storyReactionWidgetView.performHapticFeedback(3);
            storyReactionWidgetView.playAnimation();
            PeerStoriesView.this.emojiAnimationsOverlay.showAnimationForWidget(storyReactionWidgetView);
        }

        @Override // org.telegram.p035ui.Stories.StoryMediaAreasView
        public Bitmap getPlayingBitmap() {
            return PeerStoriesView.this.getPlayingBitmap();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$4 */
    public class C69244 extends HwFrameLayout {
        boolean drawOverlayed;
        final CellFlickerDrawable loadingDrawable;
        final AnimatedFloat loadingDrawableAlpha;
        final AnimatedFloat loadingDrawableAlpha2;
        final AnimatedFloat progressToAudio;
        final AnimatedFloat progressToFullBlackoutA;
        boolean splitDrawing;
        final /* synthetic */ SharedResources val$sharedResources;
        final /* synthetic */ StoryViewer val$storyViewer;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C69244(Context context, SharedResources sharedResources, StoryViewer storyViewer) {
            super(context);
            this.val$sharedResources = sharedResources;
            this.val$storyViewer = storyViewer;
            CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.DEFAULT;
            this.progressToAudio = new AnimatedFloat(this, 150L, cubicBezierInterpolator);
            this.progressToFullBlackoutA = new AnimatedFloat(this, 150L, cubicBezierInterpolator);
            this.loadingDrawable = new CellFlickerDrawable(32, 102, 240);
            AnimatedFloat animatedFloat = new AnimatedFloat(this);
            this.loadingDrawableAlpha2 = animatedFloat;
            AnimatedFloat animatedFloat2 = new AnimatedFloat(this);
            this.loadingDrawableAlpha = animatedFloat2;
            animatedFloat.setDuration(500L);
            animatedFloat2.setDuration(100L);
        }

        /* JADX WARN: Removed duplicated region for block: B:300:0x01c6  */
        /* JADX WARN: Removed duplicated region for block: B:301:0x01c8  */
        /* JADX WARN: Removed duplicated region for block: B:436:0x0602  */
        @Override // android.view.ViewGroup, android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void dispatchDraw(android.graphics.Canvas r19) {
            /*
                Method dump skipped, instruction units count: 1616
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Stories.PeerStoriesView.C69244.dispatchDraw(android.graphics.Canvas):void");
        }

        @Override // android.view.ViewGroup
        public boolean drawChild(Canvas canvas, View view, long j) {
            if (view == PeerStoriesView.this.storyAreasView) {
                return true;
            }
            if (this.splitDrawing) {
                if (Bulletin.getVisibleBulletin() != null && view == Bulletin.getVisibleBulletin().getLayout()) {
                    if (this.drawOverlayed) {
                        return super.drawChild(canvas, view, j);
                    }
                    return true;
                }
                return super.drawChild(canvas, view, j);
            }
            return super.drawChild(canvas, view, j);
        }

        /* JADX WARN: Removed duplicated region for block: B:153:0x0115  */
        /* JADX WARN: Removed duplicated region for block: B:161:0x0136  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private void drawLines(android.graphics.Canvas r21) {
            /*
                Method dump skipped, instruction units count: 590
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Stories.PeerStoriesView.C69244.drawLines(android.graphics.Canvas):void");
        }

        public /* synthetic */ void lambda$drawLines$0() {
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            if (peerStoriesView.delegate != null) {
                if (peerStoriesView.isUploading || PeerStoriesView.this.isEditing || PeerStoriesView.this.isFailed) {
                    boolean zIsVideo = PeerStoriesView.this.currentStory.isVideo();
                    PeerStoriesView peerStoriesView2 = PeerStoriesView.this;
                    if (zIsVideo) {
                        peerStoriesView2.playerSharedScope.player.loopBack();
                        return;
                    } else {
                        peerStoriesView2.currentImageTime = 0L;
                        return;
                    }
                }
                PeerStoriesView.this.delegate.shouldSwitchToNext();
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            PeerStoriesView.this.emojiAnimationsOverlay.onAttachedToWindow();
            Bulletin.addDelegate(this, new Bulletin.Delegate() { // from class: org.telegram.ui.Stories.PeerStoriesView.4.1
                @Override // org.telegram.ui.Components.Bulletin.Delegate
                public boolean clipWithGradient(int i) {
                    return i == 1 || i == 2 || i == 3;
                }

                public AnonymousClass1() {
                }

                @Override // org.telegram.ui.Components.Bulletin.Delegate
                public int getTopOffset(int i) {
                    return AndroidUtilities.m1036dp(58.0f);
                }

                @Override // org.telegram.ui.Components.Bulletin.Delegate
                public void onShow(Bulletin bulletin) {
                    Delegate delegate;
                    if (bulletin == null || bulletin.tag != 2 || (delegate = PeerStoriesView.this.delegate) == null) {
                        return;
                    }
                    delegate.setBulletinIsVisible(true);
                }

                @Override // org.telegram.ui.Components.Bulletin.Delegate
                public void onHide(Bulletin bulletin) {
                    Delegate delegate;
                    if (bulletin == null || bulletin.tag != 2 || (delegate = PeerStoriesView.this.delegate) == null) {
                        return;
                    }
                    delegate.setBulletinIsVisible(false);
                }

                @Override // org.telegram.ui.Components.Bulletin.Delegate
                public int getBottomOffset(int i) {
                    if (PeerStoriesView.this.BIG_SCREEN) {
                        return 0;
                    }
                    return AndroidUtilities.m1036dp(64.0f);
                }
            });
        }

        /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$4$1 */
        public class AnonymousClass1 implements Bulletin.Delegate {
            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public boolean clipWithGradient(int i) {
                return i == 1 || i == 2 || i == 3;
            }

            public AnonymousClass1() {
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public int getTopOffset(int i) {
                return AndroidUtilities.m1036dp(58.0f);
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public void onShow(Bulletin bulletin) {
                Delegate delegate;
                if (bulletin == null || bulletin.tag != 2 || (delegate = PeerStoriesView.this.delegate) == null) {
                    return;
                }
                delegate.setBulletinIsVisible(true);
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public void onHide(Bulletin bulletin) {
                Delegate delegate;
                if (bulletin == null || bulletin.tag != 2 || (delegate = PeerStoriesView.this.delegate) == null) {
                    return;
                }
                delegate.setBulletinIsVisible(false);
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public int getBottomOffset(int i) {
                if (PeerStoriesView.this.BIG_SCREEN) {
                    return 0;
                }
                return AndroidUtilities.m1036dp(64.0f);
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        public void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            PeerStoriesView.this.emojiAnimationsOverlay.onDetachedFromWindow();
            Bulletin.removeDelegate(this);
            Delegate delegate = PeerStoriesView.this.delegate;
            if (delegate != null) {
                delegate.setBulletinIsVisible(false);
            }
        }

        @Override // android.widget.FrameLayout, android.view.View
        public void onMeasure(int i, int i2) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) PeerStoriesView.this.muteIconContainer.getLayoutParams();
            if (PeerStoriesView.this.drawLinesAsCounter()) {
                layoutParams.rightMargin = AndroidUtilities.m1036dp(2.0f);
                layoutParams.topMargin = AndroidUtilities.m1036dp(55.0f);
            } else {
                layoutParams.rightMargin = AndroidUtilities.m1036dp(42.0f);
                layoutParams.topMargin = AndroidUtilities.m1036dp(15.0f);
            }
            super.onMeasure(i, i2);
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            LivePlayer livePlayer;
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            if (peerStoriesView.isActive && !peerStoriesView.unsupported) {
                VideoPlayerSharedScope videoPlayerSharedScope = peerStoriesView.playerSharedScope;
                if (videoPlayerSharedScope.renderView != null && videoPlayerSharedScope != null && (livePlayer = videoPlayerSharedScope.livePlayer) != null && livePlayer.isEmptyStream() && PeerStoriesView.this.playerSharedScope.renderView.dispatchTouchEvent(motionEvent)) {
                    return true;
                }
            }
            return super.dispatchTouchEvent(motionEvent);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$5 */
    public class C69275 extends StoryCaptionView {
        final /* synthetic */ Theme.ResourcesProvider val$resourcesProvider;
        final /* synthetic */ StoryViewer val$storyViewer;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C69275(Context context, Theme.ResourcesProvider resourcesProvider, StoryViewer storyViewer, Theme.ResourcesProvider resourcesProvider2) {
            super(context, resourcesProvider);
            this.val$storyViewer = storyViewer;
            this.val$resourcesProvider = resourcesProvider2;
        }

        @Override // org.telegram.p035ui.Stories.StoryCaptionView, androidx.core.widget.NestedScrollView, android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            return super.onTouchEvent(motionEvent);
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            return super.dispatchTouchEvent(motionEvent);
        }

        @Override // org.telegram.p035ui.Stories.StoryCaptionView
        public void onLinkClick(CharacterStyle characterStyle, View view) {
            if (characterStyle instanceof URLSpanUserMention) {
                TLRPC.User user = MessagesController.getInstance(PeerStoriesView.this.currentAccount).getUser(Utilities.parseLong(((URLSpanUserMention) characterStyle).getURL()));
                if (user != null) {
                    MessagesController.getInstance(PeerStoriesView.this.currentAccount).openChatOrProfileWith(user, null, this.val$storyViewer.fragment, 0, false);
                    return;
                }
                return;
            }
            if (characterStyle instanceof URLSpanNoUnderline) {
                String url = ((URLSpanNoUnderline) characterStyle).getURL();
                if (url != null && (url.startsWith("#") || url.startsWith("$"))) {
                    if (url.contains("@")) {
                        StoryViewer storyViewer = this.val$storyViewer;
                        if (storyViewer != null) {
                            storyViewer.presentFragment(new HashtagActivity(url));
                            return;
                        }
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putInt(TeXSymbolParser.TYPE_ATTR, 3);
                    bundle.putString("hashtag", url);
                    StoryViewer storyViewer2 = this.val$storyViewer;
                    if (storyViewer2 != null) {
                        storyViewer2.presentFragment(new MediaActivity(bundle, null));
                        return;
                    }
                    return;
                }
                String strExtractUsername = Browser.extractUsername(url);
                if (strExtractUsername != null) {
                    String lowerCase = strExtractUsername.toLowerCase();
                    if (url.startsWith("@")) {
                        MessagesController.getInstance(PeerStoriesView.this.currentAccount).openByUserName(lowerCase, this.val$storyViewer.fragment, 0, null);
                        return;
                    } else {
                        processExternalUrl(0, url, characterStyle, false);
                        return;
                    }
                }
                processExternalUrl(0, url, characterStyle, false);
                return;
            }
            if (characterStyle instanceof URLSpan) {
                processExternalUrl(2, ((URLSpan) characterStyle).getURL(), characterStyle, characterStyle instanceof URLSpanReplacement);
                return;
            }
            if (characterStyle instanceof URLSpanMono) {
                ((URLSpanMono) characterStyle).copyToClipboard();
                BulletinFactory.m1142of(PeerStoriesView.this.storyContainer, this.val$resourcesProvider).createCopyBulletin(LocaleController.getString(C2797R.string.TextCopied)).show();
            } else if (characterStyle instanceof ClickableSpan) {
                ((ClickableSpan) characterStyle).onClick(view);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:65:0x006d  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private void processExternalUrl(int r18, java.lang.String r19, android.text.style.CharacterStyle r20, boolean r21) {
            /*
                r17 = this;
                r0 = r17
                r1 = r18
                r2 = r20
                r3 = 2
                r4 = 0
                r5 = 1
                if (r21 != 0) goto L3d
                boolean r6 = org.telegram.messenger.AndroidUtilities.shouldShowUrlInAlert(r19)
                if (r6 == 0) goto L12
                goto L3d
            L12:
                r2 = 0
                if (r1 != 0) goto L21
                android.content.Context r0 = r0.getContext()
                android.net.Uri r1 = android.net.Uri.parse(r19)
                org.telegram.messenger.browser.Browser.openUrl(r0, r1, r5, r5, r2)
                return
            L21:
                if (r1 != r5) goto L2f
                android.content.Context r0 = r0.getContext()
                android.net.Uri r1 = android.net.Uri.parse(r19)
                org.telegram.messenger.browser.Browser.openUrl(r0, r1, r4, r4, r2)
                return
            L2f:
                if (r1 != r3) goto L53
                android.content.Context r0 = r0.getContext()
                android.net.Uri r1 = android.net.Uri.parse(r19)
                org.telegram.messenger.browser.Browser.openUrl(r0, r1, r4, r5, r2)
                return
            L3d:
                if (r1 == 0) goto L54
                if (r1 != r3) goto L42
                goto L54
            L42:
                if (r1 != r5) goto L53
                org.telegram.ui.Stories.StoryViewer r1 = r0.val$storyViewer
                org.telegram.ui.ActionBar.BaseFragment r2 = r1.fragment
                r7 = 0
                org.telegram.ui.ActionBar.Theme$ResourcesProvider r8 = r0.val$resourcesProvider
                r4 = 1
                r5 = 1
                r6 = 0
                r3 = r19
                org.telegram.p035ui.Components.AlertsCreator.showOpenUrlAlert(r2, r3, r4, r5, r6, r7, r8)
            L53:
                return
            L54:
                boolean r1 = r2 instanceof org.telegram.p035ui.Components.URLSpanReplacement
                if (r1 == 0) goto L6d
                r1 = r2
                org.telegram.ui.Components.URLSpanReplacement r1 = (org.telegram.p035ui.Components.URLSpanReplacement) r1
                org.telegram.ui.Components.TextStyleSpan$TextStyleRun r2 = r1.getTextStyleRun()
                if (r2 == 0) goto L6d
                org.telegram.ui.Components.TextStyleSpan$TextStyleRun r1 = r1.getTextStyleRun()
                int r1 = r1.flags
                r1 = r1 & 1024(0x400, float:1.435E-42)
                if (r1 == 0) goto L6d
                r14 = r5
                goto L6e
            L6d:
                r14 = r4
            L6e:
                org.telegram.ui.Stories.StoryViewer r1 = r0.val$storyViewer
                org.telegram.ui.ActionBar.BaseFragment r9 = r1.fragment
                r15 = 0
                org.telegram.ui.ActionBar.Theme$ResourcesProvider r0 = r0.val$resourcesProvider
                r11 = 1
                r12 = 1
                r13 = 1
                r10 = r19
                r16 = r0
                org.telegram.p035ui.Components.AlertsCreator.showOpenUrlAlert(r9, r10, r11, r12, r13, r14, r15, r16)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Stories.PeerStoriesView.C69275.processExternalUrl(int, java.lang.String, android.text.style.CharacterStyle, boolean):void");
        }

        @Override // org.telegram.p035ui.Stories.StoryCaptionView
        public void onLinkLongPress(final URLSpan uRLSpan, final View view, final Runnable runnable) {
            String strDecode;
            final String url = uRLSpan.getURL();
            String url2 = uRLSpan.getURL();
            try {
                try {
                    Uri uri = Uri.parse(url2);
                    url2 = Browser.replaceHostname(uri, Browser.IDN_toUnicode(uri.getHost()), null);
                } catch (Exception e) {
                    FileLog.m1048e(e);
                }
                strDecode = URLDecoder.decode(url2.replaceAll("\\+", "%2b"), "UTF-8");
            } catch (Exception e2) {
                FileLog.m1048e(e2);
                strDecode = url2;
            }
            try {
                performHapticFeedback(VibratorUtils.getType(0), 1);
            } catch (Exception unused) {
            }
            BottomSheet.Builder builder = new BottomSheet.Builder(getContext(), false, this.val$resourcesProvider);
            builder.setTitle(strDecode);
            builder.setTitleMultipleLines(true);
            StoryItemHolder storyItemHolder = PeerStoriesView.this.currentStory;
            CharSequence[] charSequenceArr = (storyItemHolder == null || storyItemHolder.allowScreenshots()) ? new CharSequence[]{LocaleController.getString(C2797R.string.Open), LocaleController.getString(C2797R.string.Copy)} : new CharSequence[]{LocaleController.getString(C2797R.string.Open)};
            final Theme.ResourcesProvider resourcesProvider = this.val$resourcesProvider;
            builder.setItems(charSequenceArr, new DialogInterface.OnClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$5$$ExternalSyntheticLambda5
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$onLinkLongPress$0(uRLSpan, view, url, resourcesProvider, dialogInterface, i);
                }
            });
            builder.setOnPreDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$5$$ExternalSyntheticLambda6
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    runnable.run();
                }
            });
            BottomSheet bottomSheetCreate = builder.create();
            bottomSheetCreate.fixNavigationBar(Theme.getColor(Theme.key_dialogBackground, this.val$resourcesProvider));
            PeerStoriesView.this.delegate.showDialog(bottomSheetCreate);
        }

        public /* synthetic */ void lambda$onLinkLongPress$0(URLSpan uRLSpan, View view, String str, Theme.ResourcesProvider resourcesProvider, DialogInterface dialogInterface, int i) {
            if (i == 0) {
                onLinkClick(uRLSpan, view);
            } else if (i == 1) {
                AndroidUtilities.addToClipboard(str);
                BulletinFactory.m1142of(PeerStoriesView.this.storyContainer, resourcesProvider).createCopyLinkBulletin().show();
            }
        }

        @Override // org.telegram.p035ui.Stories.StoryCaptionView
        public void onReplyClick(View view, final StoryCaptionView.Panel panel) {
            if (panel == null) {
                return;
            }
            final TLRPC.Document document = panel.music;
            if (document != null) {
                int i = C2797R.drawable.msg_saved;
                String string = LocaleController.getString(C2797R.string.StoryAudioAddToSavedMessages);
                final Theme.ResourcesProvider resourcesProvider = this.val$resourcesProvider;
                ItemOptions itemOptionsAddIf = ItemOptions.makeOptions(this.val$storyViewer.containerView, this.val$resourcesProvider, PeerStoriesView.this.storyCaptionView).setGravity(3).translate(-AndroidUtilities.m1036dp(8.0f), 0.0f).addIf(document instanceof TLRPC.TL_document, i, string, new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$5$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onReplyClick$2(document, resourcesProvider);
                    }
                });
                int i2 = C2797R.drawable.msg_tone_add;
                String string2 = LocaleController.getString(C2797R.string.StoryAudioAddToProfile);
                final Theme.ResourcesProvider resourcesProvider2 = this.val$resourcesProvider;
                itemOptionsAddIf.add(i2, string2, new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$5$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onReplyClick$3(document, resourcesProvider2);
                    }
                }).show();
                return;
            }
            if (panel.isRepostMessage && panel.peerId != null && panel.messageId != null) {
                Bundle bundle = new Bundle();
                long jLongValue = panel.peerId.longValue();
                Long l = panel.peerId;
                if (jLongValue >= 0) {
                    bundle.putLong("user_id", l.longValue());
                } else {
                    bundle.putLong("chat_id", -l.longValue());
                }
                bundle.putInt("message_id", panel.messageId.intValue());
                this.val$storyViewer.presentFragment(new ChatActivity(bundle));
                return;
            }
            if (panel.peerId == null || panel.storyId == null) {
                BulletinFactory.m1142of(PeerStoriesView.this.storyContainer, this.val$resourcesProvider).createSimpleBulletin(C2797R.raw.error, LocaleController.getString(C2797R.string.StoryHidAccount)).setTag(3).show(true);
                return;
            }
            StoriesController storiesController = MessagesController.getInstance(PeerStoriesView.this.currentAccount).getStoriesController();
            long jLongValue2 = panel.peerId.longValue();
            int iIntValue = panel.storyId.intValue();
            final StoryViewer storyViewer = this.val$storyViewer;
            final Theme.ResourcesProvider resourcesProvider3 = this.val$resourcesProvider;
            storiesController.resolveStoryLink(jLongValue2, iIntValue, new Consumer() { // from class: org.telegram.ui.Stories.PeerStoriesView$5$$ExternalSyntheticLambda2
                @Override // com.google.android.exoplayer2.util.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$onReplyClick$5(panel, storyViewer, resourcesProvider3, (TL_stories.StoryItem) obj);
                }
            });
        }

        public /* synthetic */ void lambda$onReplyClick$2(TLRPC.Document document, Theme.ResourcesProvider resourcesProvider) {
            SendMessagesHelper sendMessagesHelper = SendMessagesHelper.getInstance(PeerStoriesView.this.currentAccount);
            TLRPC.TL_document tL_document = (TLRPC.TL_document) document;
            long clientUserId = UserConfig.getInstance(PeerStoriesView.this.currentAccount).getClientUserId();
            StoryItemHolder storyItemHolder = PeerStoriesView.this.currentStory;
            sendMessagesHelper.sendMessage(SendMessagesHelper.SendMessageParams.m1080of(tL_document, null, null, clientUserId, null, null, null, null, null, null, false, 0, 0, 0, storyItemHolder != null ? storyItemHolder.storyItem : null, null, false));
            BulletinFactory.m1142of(PeerStoriesView.this.storyContainer, resourcesProvider).createSimpleBulletin(C2797R.raw.saved_messages, AndroidUtilities.replaceSingleTag(LocaleController.getString(C2797R.string.StoryAudioAddToSavedMessagesToast), -1, 2, new BulletinFactory$$ExternalSyntheticLambda5())).show(true);
        }

        public /* synthetic */ void lambda$onReplyClick$3(TLRPC.Document document, Theme.ResourcesProvider resourcesProvider) {
            TLRPC.TL_account_saveMusic tL_account_saveMusic = new TLRPC.TL_account_saveMusic();
            TLRPC.TL_inputDocument tL_inputDocument = new TLRPC.TL_inputDocument();
            tL_account_saveMusic.f1281id = tL_inputDocument;
            tL_inputDocument.f1262id = document.f1253id;
            tL_inputDocument.access_hash = document.access_hash;
            tL_inputDocument.file_reference = document.file_reference;
            if (MediaController.getInstance().currentSavedMusicList != null && MediaController.getInstance().currentSavedMusicList.dialogId == UserConfig.getInstance(PeerStoriesView.this.currentAccount).getClientUserId()) {
                MediaController.getInstance().currentSavedMusicList.add(document);
            }
            ConnectionsManager.getInstance(PeerStoriesView.this.currentAccount).sendRequest(tL_account_saveMusic, null);
            BulletinFactory.m1142of(PeerStoriesView.this.storyContainer, resourcesProvider).createSimpleBulletin(C2797R.raw.ic_save_to_music, LocaleController.getString(C2797R.string.StoryAudioAddToProfileToast)).show(true);
        }

        public /* synthetic */ void lambda$onReplyClick$5(StoryCaptionView.Panel panel, final StoryViewer storyViewer, Theme.ResourcesProvider resourcesProvider, TL_stories.StoryItem storyItem) {
            if (storyItem != null) {
                BaseFragment lastFragment = LaunchActivity.getLastFragment();
                if (lastFragment == null) {
                    return;
                }
                storyItem.dialogId = panel.peerId.longValue();
                StoryViewer storyViewerCreateOverlayStoryViewer = lastFragment.createOverlayStoryViewer();
                storyViewerCreateOverlayStoryViewer.open(getContext(), storyItem, (StoryViewer.PlaceProvider) null);
                storyViewerCreateOverlayStoryViewer.setOnCloseListener(new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$5$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        storyViewer.updatePlayingMode();
                    }
                });
                storyViewer.updatePlayingMode();
                return;
            }
            BulletinFactory.m1142of(PeerStoriesView.this.storyContainer, resourcesProvider).createSimpleBulletin(C2797R.raw.story_bomb2, LocaleController.getString(C2797R.string.StoryNotFound)).setTag(3).show(true);
        }

        @Override // org.telegram.p035ui.Stories.StoryCaptionView
        public void onEmojiClick(AnimatedEmojiSpan animatedEmojiSpan) {
            if (animatedEmojiSpan != null) {
                PeerStoriesView peerStoriesView = PeerStoriesView.this;
                if (peerStoriesView.delegate == null) {
                    return;
                }
                TLRPC.Document documentFindDocument = animatedEmojiSpan.document;
                if (documentFindDocument == null) {
                    documentFindDocument = AnimatedEmojiDrawable.findDocument(peerStoriesView.currentAccount, animatedEmojiSpan.documentId);
                }
                if (documentFindDocument == null) {
                    return;
                }
                BulletinFactory bulletinFactoryM1142of = BulletinFactory.m1142of(PeerStoriesView.this.storyContainer, this.val$resourcesProvider);
                final StoryViewer storyViewer = this.val$storyViewer;
                final Theme.ResourcesProvider resourcesProvider = this.val$resourcesProvider;
                Bulletin bulletinCreateContainsEmojiBulletin = bulletinFactoryM1142of.createContainsEmojiBulletin(documentFindDocument, 2, new Utilities.Callback() { // from class: org.telegram.ui.Stories.PeerStoriesView$5$$ExternalSyntheticLambda3
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        this.f$0.lambda$onEmojiClick$6(storyViewer, resourcesProvider, (TLRPC.InputStickerSet) obj);
                    }
                });
                if (bulletinCreateContainsEmojiBulletin == null) {
                    return;
                }
                bulletinCreateContainsEmojiBulletin.tag = 1;
                bulletinCreateContainsEmojiBulletin.show(true);
            }
        }

        public /* synthetic */ void lambda$onEmojiClick$6(StoryViewer storyViewer, Theme.ResourcesProvider resourcesProvider, TLRPC.InputStickerSet inputStickerSet) {
            ArrayList arrayList = new ArrayList(1);
            arrayList.add(inputStickerSet);
            EmojiPacksAlert emojiPacksAlert = new EmojiPacksAlert(storyViewer.fragment, getContext(), resourcesProvider, arrayList);
            Delegate delegate = PeerStoriesView.this.delegate;
            if (delegate != null) {
                delegate.showDialog(emojiPacksAlert);
            }
        }
    }

    public /* synthetic */ void lambda$new$0(View view) {
        StoryCaptionView storyCaptionView = this.storyCaptionView;
        if (storyCaptionView.expanded) {
            boolean zIsInSelectionMode = storyCaptionView.textSelectionHelper.isInSelectionMode();
            StoryCaptionView storyCaptionView2 = this.storyCaptionView;
            if (!zIsInSelectionMode) {
                storyCaptionView2.collapse();
                return;
            } else {
                storyCaptionView2.checkCancelTextSelection();
                return;
            }
        }
        this.checkBlackoutMode = true;
        storyCaptionView.expand();
    }

    public /* synthetic */ void lambda$new$1(View view) {
        shareStory(true);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$6 */
    public class C69286 extends FrameLayout {
        public C69286(Context context2) {
            super(context2);
        }

        @Override // android.view.ViewGroup, android.view.View
        public void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            if (!peerStoriesView.isChannel || peerStoriesView.repostCounter == null) {
                return;
            }
            canvas.save();
            canvas.translate((getMeasuredWidth() - PeerStoriesView.this.repostCounter.getCurrentWidth()) - AndroidUtilities.m1036dp(6.0f), 0.0f);
            float f = PeerStoriesView.this.repostCounterProgress.set(PeerStoriesView.this.repostCounterVisible ? 1.0f : 0.0f);
            canvas.scale(f, f, PeerStoriesView.this.repostCounter.getCurrentWidth() / 2.0f, AndroidUtilities.m1036dp(20.0f));
            PeerStoriesView.this.repostCounter.setAlpha(255);
            PeerStoriesView.this.repostCounter.draw(canvas);
            canvas.restore();
        }

        @Override // android.view.View
        public boolean verifyDrawable(Drawable drawable) {
            return drawable == PeerStoriesView.this.repostCounter || super.verifyDrawable(drawable);
        }
    }

    public /* synthetic */ void lambda$new$2(View view) {
        tryToOpenRepostStory();
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$7 */
    public class C69297 extends FrameLayout {
        public C69297(Context context2) {
            super(context2);
        }

        @Override // android.view.ViewGroup, android.view.View
        public void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            if (!peerStoriesView.isChannel || peerStoriesView.reactionsCounter == null) {
                return;
            }
            canvas.save();
            canvas.translate((getMeasuredWidth() - PeerStoriesView.this.reactionsCounter.getCurrentWidth()) - AndroidUtilities.m1036dp(6.0f), 0.0f);
            float f = PeerStoriesView.this.reactionsCounterProgress.set(PeerStoriesView.this.reactionsCounterVisible ? 1.0f : 0.0f);
            canvas.scale(f, f, PeerStoriesView.this.reactionsCounter.getCurrentWidth() / 2.0f, AndroidUtilities.m1036dp(20.0f));
            PeerStoriesView.this.reactionsCounter.setAlpha(255);
            PeerStoriesView.this.reactionsCounter.draw(canvas);
            canvas.restore();
        }

        @Override // android.view.View
        public boolean verifyDrawable(Drawable drawable) {
            return drawable == PeerStoriesView.this.reactionsCounter || super.verifyDrawable(drawable);
        }
    }

    public /* synthetic */ void lambda$new$4(View view) {
        TL_stories.StoryItem storyItem = this.currentStory.storyItem;
        if (storyItem != null && storyItem.sent_reaction == null) {
            applyMessageToChat(new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda34
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$new$3();
                }
            });
        } else {
            likeStory(null);
        }
    }

    public /* synthetic */ void lambda$new$3() {
        likeStory(null);
    }

    public /* synthetic */ boolean lambda$new$5(StoryViewer storyViewer, View view) {
        Runnable runnable = this.reactionsTooltipRunnable;
        if (runnable != null) {
            AndroidUtilities.cancelRunOnUIThread(runnable);
            this.reactionsTooltipRunnable = null;
        }
        SharedConfig.setStoriesReactionsLongPressHintUsed(true);
        HintView2 hintView2 = this.reactionsLongpressTooltip;
        if (hintView2 != null) {
            hintView2.hide();
        }
        checkReactionsLayoutForLike();
        storyViewer.windowView.dispatchTouchEvent(AndroidUtilities.emptyMotionEvent());
        showLikesReaction(true);
        return true;
    }

    public /* synthetic */ void lambda$new$6(StoryViewer storyViewer, View view) {
        long j = UserConfig.getInstance(this.currentAccount).clientUserId;
        long j2 = this.dialogId;
        if (j == j2) {
            Bundle bundle = new Bundle();
            bundle.putInt(TeXSymbolParser.TYPE_ATTR, 1);
            bundle.putLong("dialog_id", this.dialogId);
            storyViewer.presentFragment(new MediaActivity(bundle, null));
            return;
        }
        if (j2 > 0) {
            storyViewer.presentFragment(ProfileActivity.m1186of(j2));
        } else {
            storyViewer.presentFragment(ChatActivity.m1139of(j2));
        }
    }

    public static /* synthetic */ void $r8$lambda$yJHJR1bijBsghFqqIA0QqbKSXoo(StoryViewer storyViewer, View view) {
        if (storyViewer != null) {
            storyViewer.switchToPip();
        }
    }

    public /* synthetic */ void lambda$new$8(Theme.ResourcesProvider resourcesProvider, StoryViewer storyViewer, Context context, SharedResources sharedResources, View view) {
        this.delegate.setPopupIsVisible(true);
        this.editStoryItem = null;
        boolean[] zArr = {false};
        if (this.isSelf) {
            MessagesController.getInstance(this.currentAccount).getStoriesController().loadBlocklistAtFirst();
            MessagesController.getInstance(this.currentAccount).getStoriesController().loadSendAs();
            MessagesController.getInstance(this.currentAccount).getStoriesController().getDraftsController().load();
        }
        boolean z = this.isSelf || MessagesController.getInstance(this.currentAccount).getStoriesController().canEditStory(this.currentStory.storyItem);
        C69308 c69308 = new C69308(getContext(), resourcesProvider, true, resourcesProvider, storyViewer, this.currentStory.isVideo, this.isSelf || ((this.isChannel || isBotsPreview()) && z), z, context, sharedResources, zArr);
        this.popupMenu = c69308;
        c69308.show(this.optionsIconView, 0, (-ActionBar.getCurrentActionBarHeight()) + AndroidUtilities.m1036dp(6.0f));
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$8 */
    public class C69308 extends CustomPopupMenu {
        private boolean edit;
        final /* synthetic */ boolean val$canEditStory;
        final /* synthetic */ Context val$context;
        final /* synthetic */ boolean[] val$popupStillVisible;
        final /* synthetic */ Theme.ResourcesProvider val$resourcesProvider;
        final /* synthetic */ SharedResources val$sharedResources;
        final /* synthetic */ boolean val$speedControl;
        final /* synthetic */ StoryViewer val$storyViewer;
        final /* synthetic */ boolean val$userCanEditStory;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C69308(Context context, Theme.ResourcesProvider resourcesProvider, boolean z, Theme.ResourcesProvider resourcesProvider2, StoryViewer storyViewer, boolean z2, boolean z3, boolean z4, Context context2, SharedResources sharedResources, boolean[] zArr) {
            super(context, resourcesProvider, z);
            this.val$resourcesProvider = resourcesProvider2;
            this.val$storyViewer = storyViewer;
            this.val$speedControl = z2;
            this.val$canEditStory = z3;
            this.val$userCanEditStory = z4;
            this.val$context = context2;
            this.val$sharedResources = sharedResources;
            this.val$popupStillVisible = zArr;
        }

        private void addViewStatistics(ActionBarPopupWindow.ActionBarPopupWindowLayout actionBarPopupWindowLayout, final TL_stories.StoryItem storyItem) {
            final TLRPC.Chat chat;
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            if (!peerStoriesView.isChannel || storyItem == null || (storyItem.media instanceof TLRPC.TL_messageMediaVideoStream) || (chat = MessagesController.getInstance(peerStoriesView.currentAccount).getChat(Long.valueOf(-PeerStoriesView.this.dialogId))) == null) {
                return;
            }
            TLRPC.ChatFull chatFull = MessagesController.getInstance(PeerStoriesView.this.currentAccount).getChatFull(chat.f1245id);
            if (chatFull == null) {
                chatFull = MessagesStorage.getInstance(PeerStoriesView.this.currentAccount).loadChatInfo(chat.f1245id, true, new CountDownLatch(1), false, false);
            }
            if (chatFull == null || !chatFull.can_view_stats) {
                return;
            }
            ActionBarMenuSubItem actionBarMenuSubItemAddItem = ActionBarMenuItem.addItem(actionBarPopupWindowLayout, C2797R.drawable.msg_stats, LocaleController.getString(C2797R.string.ViewStatistics), false, this.val$resourcesProvider);
            final StoryViewer storyViewer = this.val$storyViewer;
            actionBarMenuSubItemAddItem.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda51
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$addViewStatistics$0(storyItem, storyViewer, chat, view);
                }
            });
        }

        public /* synthetic */ void lambda$addViewStatistics$0(TL_stories.StoryItem storyItem, StoryViewer storyViewer, TLRPC.Chat chat, View view) {
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
            storyItem.dialogId = PeerStoriesView.this.dialogId;
            storyItem.messageId = storyItem.f1454id;
            MessageObject messageObject = new MessageObject(PeerStoriesView.this.currentAccount, storyItem);
            messageObject.generateThumbs(false);
            storyViewer.presentFragment(new MessageStatisticActivity(messageObject, chat.f1245id, false) { // from class: org.telegram.ui.Stories.PeerStoriesView.8.1
                @Override // org.telegram.p035ui.MessageStatisticActivity, org.telegram.p035ui.ActionBar.BaseFragment
                public boolean isLightStatusBar() {
                    return false;
                }

                public AnonymousClass1(MessageObject messageObject2, long j, boolean z) {
                    super(messageObject2, j, z);
                }

                @Override // org.telegram.p035ui.ActionBar.BaseFragment
                public Theme.ResourcesProvider getResourceProvider() {
                    return new DarkThemeResourceProvider();
                }
            });
        }

        /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$8$1 */
        public class AnonymousClass1 extends MessageStatisticActivity {
            @Override // org.telegram.p035ui.MessageStatisticActivity, org.telegram.p035ui.ActionBar.BaseFragment
            public boolean isLightStatusBar() {
                return false;
            }

            public AnonymousClass1(MessageObject messageObject2, long j, boolean z) {
                super(messageObject2, j, z);
            }

            @Override // org.telegram.p035ui.ActionBar.BaseFragment
            public Theme.ResourcesProvider getResourceProvider() {
                return new DarkThemeResourceProvider();
            }
        }

        private void addSpeedLayout(final ActionBarPopupWindow.ActionBarPopupWindowLayout actionBarPopupWindowLayout, boolean z) {
            PeerStoriesView peerStoriesView;
            StoryItemHolder storyItemHolder;
            if (!this.val$speedControl || ((storyItemHolder = (peerStoriesView = PeerStoriesView.this).currentStory) != null && storyItemHolder.uploadingStory != null)) {
                PeerStoriesView.this.speedLayout = null;
                PeerStoriesView.this.speedItem = null;
                return;
            }
            peerStoriesView.speedLayout = new ChooseSpeedLayout(peerStoriesView.getContext(), actionBarPopupWindowLayout.getSwipeBack(), new ChooseSpeedLayout.Callback() { // from class: org.telegram.ui.Stories.PeerStoriesView.8.2
                final /* synthetic */ ActionBarPopupWindow.ActionBarPopupWindowLayout val$popupLayout;

                public AnonymousClass2(final ActionBarPopupWindow.ActionBarPopupWindowLayout actionBarPopupWindowLayout2) {
                    actionBarPopupWindowLayout = actionBarPopupWindowLayout2;
                }

                @Override // org.telegram.ui.ChooseSpeedLayout.Callback
                public void onSpeedSelected(float f, boolean z2, boolean z3) {
                    StoryViewer storyViewer = C69308.this.val$storyViewer;
                    if (storyViewer != null) {
                        storyViewer.setSpeed(f);
                    }
                    PeerStoriesView.this.updateSpeedItem(z2);
                    if (!z3 || actionBarPopupWindowLayout.getSwipeBack() == null) {
                        return;
                    }
                    actionBarPopupWindowLayout.getSwipeBack().closeForeground();
                }
            });
            PeerStoriesView.this.speedLayout.update(StoryViewer.currentSpeed, true);
            PeerStoriesView.this.speedItem = new ActionBarMenuSubItem(PeerStoriesView.this.getContext(), false, false, false, this.val$resourcesProvider);
            PeerStoriesView.this.speedItem.setTextAndIcon(LocaleController.getString(C2797R.string.Speed), C2797R.drawable.msg_speed, null);
            PeerStoriesView.this.updateSpeedItem(true);
            PeerStoriesView.this.speedItem.setMinimumWidth(AndroidUtilities.m1036dp(196.0f));
            PeerStoriesView.this.speedItem.setRightIcon(C2797R.drawable.msg_arrowright);
            actionBarPopupWindowLayout2.addView(PeerStoriesView.this.speedItem);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) PeerStoriesView.this.speedItem.getLayoutParams();
            if (LocaleController.isRTL) {
                layoutParams.gravity = 5;
            }
            layoutParams.width = -1;
            layoutParams.height = AndroidUtilities.m1036dp(48.0f);
            PeerStoriesView.this.speedItem.setLayoutParams(layoutParams);
            final int iAddViewToSwipeBack = actionBarPopupWindowLayout2.addViewToSwipeBack(PeerStoriesView.this.speedLayout.speedSwipeBackLayout);
            PeerStoriesView.this.speedItem.openSwipeBackLayout = new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda39
                @Override // java.lang.Runnable
                public final void run() {
                    PeerStoriesView.C69308.$r8$lambda$f2mS9OHREAaY0zfxPk0fkyfF6ck(actionBarPopupWindowLayout2, iAddViewToSwipeBack);
                }
            };
            PeerStoriesView.this.speedItem.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda40
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$addSpeedLayout$2(view);
                }
            });
            actionBarPopupWindowLayout2.swipeBackGravityRight = true;
            if (z) {
                ActionBarPopupWindow.GapView gapView = new ActionBarPopupWindow.GapView(PeerStoriesView.this.getContext(), this.val$resourcesProvider, Theme.key_actionBarDefaultSubmenuSeparator);
                gapView.setTag(C2797R.id.fit_width_tag, 1);
                actionBarPopupWindowLayout2.addView((View) gapView, LayoutHelper.createLinear(-1, 8));
            }
        }

        /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$8$2 */
        public class AnonymousClass2 implements ChooseSpeedLayout.Callback {
            final /* synthetic */ ActionBarPopupWindow.ActionBarPopupWindowLayout val$popupLayout;

            public AnonymousClass2(final ActionBarPopupWindow.ActionBarPopupWindowLayout actionBarPopupWindowLayout2) {
                actionBarPopupWindowLayout = actionBarPopupWindowLayout2;
            }

            @Override // org.telegram.ui.ChooseSpeedLayout.Callback
            public void onSpeedSelected(float f, boolean z2, boolean z3) {
                StoryViewer storyViewer = C69308.this.val$storyViewer;
                if (storyViewer != null) {
                    storyViewer.setSpeed(f);
                }
                PeerStoriesView.this.updateSpeedItem(z2);
                if (!z3 || actionBarPopupWindowLayout.getSwipeBack() == null) {
                    return;
                }
                actionBarPopupWindowLayout.getSwipeBack().closeForeground();
            }
        }

        public static /* synthetic */ void $r8$lambda$f2mS9OHREAaY0zfxPk0fkyfF6ck(ActionBarPopupWindow.ActionBarPopupWindowLayout actionBarPopupWindowLayout, int i) {
            if (actionBarPopupWindowLayout.getSwipeBack() != null) {
                actionBarPopupWindowLayout.getSwipeBack().openForeground(i);
            }
        }

        public /* synthetic */ void lambda$addSpeedLayout$2(View view) {
            PeerStoriesView.this.speedItem.openSwipeBack();
        }

        private void addAlbumsLayout(final ActionBarPopupWindow.ActionBarPopupWindowLayout actionBarPopupWindowLayout, boolean z) {
            HashSet hashSet;
            final TL_stories.StoryItem storyItem = PeerStoriesView.this.currentStory.storyItem;
            if (storyItem == null) {
                return;
            }
            if (storyItem.albums != null) {
                hashSet = new HashSet(storyItem.albums);
            } else {
                hashSet = new HashSet();
            }
            final HashSet hashSet2 = hashSet;
            ItemOptions itemOptionsSwipeback = ItemOptions.swipeback(actionBarPopupWindowLayout, this.val$resourcesProvider);
            itemOptionsSwipeback.add(C2797R.drawable.ic_ab_back, LocaleController.getString(C2797R.string.Back), new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda41
                @Override // java.lang.Runnable
                public final void run() {
                    PeerStoriesView.C69308.$r8$lambda$rVsuPxLsudFzomSQr9kQ1d0unxs(actionBarPopupWindowLayout);
                }
            });
            itemOptionsSwipeback.addGap();
            StoriesController.StoriesCollections storyAlbumsList = PeerStoriesView.this.getStoriesController().getStoryAlbumsList(PeerStoriesView.this.dialogId);
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            boolean zCanCreateNewAlbum = peerStoriesView.storiesController.canCreateNewAlbum(peerStoriesView.dialogId);
            final Theme.ResourcesProvider resourcesProvider = this.val$resourcesProvider;
            Runnable runnable = new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda42
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$addAlbumsLayout$6(resourcesProvider, storyItem);
                }
            };
            final Theme.ResourcesProvider resourcesProvider2 = this.val$resourcesProvider;
            ItemOptions.addAlbumsItemOptions(itemOptionsSwipeback, storyAlbumsList, hashSet2, zCanCreateNewAlbum, runnable, new Utilities.Callback() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda43
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$addAlbumsLayout$7(hashSet2, storyItem, resourcesProvider2, (StoriesController.StoryAlbum) obj);
                }
            });
            PeerStoriesView.this.albumLayout = itemOptionsSwipeback.getLinearLayout();
            final int iAddViewToSwipeBack = actionBarPopupWindowLayout.addViewToSwipeBack(PeerStoriesView.this.albumLayout);
            PeerStoriesView.this.albumItem = new ActionBarMenuSubItem(PeerStoriesView.this.getContext(), false, false, false, this.val$resourcesProvider);
            PeerStoriesView.this.albumItem.setTextAndIcon(LocaleController.getString(C2797R.string.StoriesAlbumAddToAlbum), C2797R.drawable.menu_album_add, null);
            PeerStoriesView.this.albumItem.openSwipeBackLayout = new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda44
                @Override // java.lang.Runnable
                public final void run() {
                    PeerStoriesView.C69308.$r8$lambda$kkQekJEdPFrNqPha5yZIXlapoB4(actionBarPopupWindowLayout, iAddViewToSwipeBack);
                }
            };
            PeerStoriesView.this.albumItem.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda45
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$addAlbumsLayout$9(view);
                }
            });
            actionBarPopupWindowLayout.addView(PeerStoriesView.this.albumItem);
            actionBarPopupWindowLayout.swipeBackGravityRight = true;
            if (z) {
                ActionBarPopupWindow.GapView gapView = new ActionBarPopupWindow.GapView(PeerStoriesView.this.getContext(), this.val$resourcesProvider, Theme.key_actionBarDefaultSubmenuSeparator);
                gapView.setTag(C2797R.id.fit_width_tag, 1);
                actionBarPopupWindowLayout.addView((View) gapView, LayoutHelper.createLinear(-1, 8));
            }
        }

        public static /* synthetic */ void $r8$lambda$rVsuPxLsudFzomSQr9kQ1d0unxs(ActionBarPopupWindow.ActionBarPopupWindowLayout actionBarPopupWindowLayout) {
            if (actionBarPopupWindowLayout.getSwipeBack() != null) {
                actionBarPopupWindowLayout.getSwipeBack().closeForeground();
            }
        }

        public /* synthetic */ void lambda$addAlbumsLayout$6(final Theme.ResourcesProvider resourcesProvider, final TL_stories.StoryItem storyItem) {
            AlertsCreator.createStoriesAlbumEnterNameForCreate(PeerStoriesView.this.getContext(), null, resourcesProvider, new MessagesStorage.StringCallback() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda59
                @Override // org.telegram.messenger.MessagesStorage.StringCallback
                public final void run(String str) {
                    this.f$0.lambda$addAlbumsLayout$5(storyItem, resourcesProvider, str);
                }
            });
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
        }

        public /* synthetic */ void lambda$addAlbumsLayout$5(final TL_stories.StoryItem storyItem, final Theme.ResourcesProvider resourcesProvider, String str) {
            PeerStoriesView.this.getStoriesController().createAlbum(PeerStoriesView.this.dialogId, str, new Utilities.Callback() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda7
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$addAlbumsLayout$4(storyItem, resourcesProvider, (StoriesController.StoryAlbum) obj);
                }
            });
        }

        public /* synthetic */ void lambda$addAlbumsLayout$4(TL_stories.StoryItem storyItem, Theme.ResourcesProvider resourcesProvider, StoriesController.StoryAlbum storyAlbum) {
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            peerStoriesView.storiesController.addStoryToAlbum(peerStoriesView.dialogId, storyAlbum.album_id, storyItem);
            BulletinFactory.m1142of(PeerStoriesView.this.storyContainer, resourcesProvider).createSimpleBulletin(C2797R.raw.contact_check, AndroidUtilities.replaceTags(LocaleController.formatString(C2797R.string.StoryAddedToAlbumX, storyAlbum.title))).show();
        }

        public /* synthetic */ void lambda$addAlbumsLayout$7(HashSet hashSet, TL_stories.StoryItem storyItem, Theme.ResourcesProvider resourcesProvider, StoriesController.StoryAlbum storyAlbum) {
            String string;
            boolean zContains = hashSet.contains(Integer.valueOf(storyAlbum.album_id));
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            if (zContains) {
                peerStoriesView.storiesController.addStoryToAlbum(peerStoriesView.dialogId, storyAlbum.album_id, storyItem);
                string = LocaleController.formatString(C2797R.string.StoryAddedToAlbumX, storyAlbum.title);
            } else {
                peerStoriesView.storiesController.removeStoryFromAlbum(peerStoriesView.dialogId, storyAlbum.album_id, storyItem);
                string = LocaleController.formatString(C2797R.string.StoryRemovedFromAlbumX, storyAlbum.title);
            }
            BulletinFactory.m1142of(PeerStoriesView.this.storyContainer, resourcesProvider).createSimpleBulletin(C2797R.raw.contact_check, AndroidUtilities.replaceTags(string)).show();
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
        }

        public static /* synthetic */ void $r8$lambda$kkQekJEdPFrNqPha5yZIXlapoB4(ActionBarPopupWindow.ActionBarPopupWindowLayout actionBarPopupWindowLayout, int i) {
            if (actionBarPopupWindowLayout.getSwipeBack() != null) {
                actionBarPopupWindowLayout.getSwipeBack().openForeground(i);
            }
        }

        public /* synthetic */ void lambda$addAlbumsLayout$9(View view) {
            PeerStoriesView.this.albumItem.openSwipeBack();
        }

        public /* synthetic */ void lambda$onCreate$10(View view) {
            StoriesController.UploadingStory uploadingStory = PeerStoriesView.this.currentStory.uploadingStory;
            if (uploadingStory != null) {
                uploadingStory.cancel();
                PeerStoriesView.this.updateStoryItems();
            }
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
        }

        public /* synthetic */ void lambda$onCreate$11(StoryPrivacyBottomSheet.StoryPrivacy storyPrivacy, TL_stories.StoryItem storyItem, View view) {
            PeerStoriesView.this.editPrivacy(storyPrivacy, storyItem);
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:332:0x0130 A[PHI: r2
  0x0130: PHI (r2v126 boolean) = (r2v112 boolean), (r2v128 boolean) binds: [B:341:0x0143, B:331:0x012e] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:333:0x0132 A[PHI: r2
  0x0132: PHI (r2v115 boolean) = (r2v112 boolean), (r2v112 boolean), (r2v128 boolean), (r2v128 boolean) binds: [B:339:0x013f, B:341:0x0143, B:329:0x012a, B:331:0x012e] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:448:0x045c  */
        /* JADX WARN: Removed duplicated region for block: B:460:0x04ad  */
        /* JADX WARN: Removed duplicated region for block: B:464:0x04b9  */
        /* JADX WARN: Removed duplicated region for block: B:469:0x04c7  */
        /* JADX WARN: Removed duplicated region for block: B:470:0x04ca  */
        /* JADX WARN: Removed duplicated region for block: B:474:0x04e5  */
        /* JADX WARN: Removed duplicated region for block: B:478:0x04f7  */
        /* JADX WARN: Removed duplicated region for block: B:480:0x04fe  */
        /* JADX WARN: Removed duplicated region for block: B:484:0x0521  */
        /* JADX WARN: Removed duplicated region for block: B:488:0x0531  */
        /* JADX WARN: Removed duplicated region for block: B:491:0x053a  */
        /* JADX WARN: Removed duplicated region for block: B:496:0x055e  */
        /* JADX WARN: Removed duplicated region for block: B:499:0x057c  */
        /* JADX WARN: Removed duplicated region for block: B:521:0x05de  */
        /* JADX WARN: Removed duplicated region for block: B:524:0x0600  */
        /* JADX WARN: Removed duplicated region for block: B:537:0x067c  */
        /* JADX WARN: Removed duplicated region for block: B:540:0x06be  */
        /* JADX WARN: Type inference failed for: r14v3 */
        /* JADX WARN: Type inference failed for: r14v4, types: [org.telegram.tgnet.TLRPC$User] */
        /* JADX WARN: Type inference failed for: r14v6 */
        /* JADX WARN: Type inference failed for: r15v1 */
        /* JADX WARN: Type inference failed for: r15v2, types: [org.telegram.tgnet.TLRPC$Chat] */
        /* JADX WARN: Type inference failed for: r15v3 */
        /* JADX WARN: Type inference failed for: r21v0, types: [android.view.ViewGroup, org.telegram.ui.ActionBar.ActionBarPopupWindow$ActionBarPopupWindowLayout] */
        @Override // org.telegram.p035ui.Components.CustomPopupMenu
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onCreate(org.telegram.ui.ActionBar.ActionBarPopupWindow.ActionBarPopupWindowLayout r21) {
            /*
                Method dump skipped, instruction units count: 1962
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Stories.PeerStoriesView.C69308.onCreate(org.telegram.ui.ActionBar.ActionBarPopupWindow$ActionBarPopupWindowLayout):void");
        }

        public /* synthetic */ void lambda$onCreate$17(Theme.ResourcesProvider resourcesProvider, Context context, final StoryViewer storyViewer, final SharedResources sharedResources, View view) {
            if (view.getAlpha() < 1.0f) {
                PeerStoriesView peerStoriesView = PeerStoriesView.this;
                int i = -peerStoriesView.shiftDp;
                peerStoriesView.shiftDp = i;
                AndroidUtilities.shakeViewSpring(view, i);
                BulletinFactory.m1142of(PeerStoriesView.this.storyContainer, resourcesProvider).createErrorBulletin("Wait until current upload is complete").show();
                return;
            }
            final Activity activityFindActivity = AndroidUtilities.findActivity(context);
            if (activityFindActivity == null) {
                return;
            }
            this.edit = true;
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
            Runnable runnable = new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda50
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onCreate$16(activityFindActivity, storyViewer, sharedResources);
                }
            };
            if (PeerStoriesView.this.delegate.releasePlayer(runnable)) {
                return;
            }
            runnable.run();
        }

        public /* synthetic */ void lambda$onCreate$16(Activity activity, StoryViewer storyViewer, final SharedResources sharedResources) {
            File file;
            StoryViewer.VideoPlayerHolder videoPlayerHolder;
            StoryRecorder storyRecorder = StoryRecorder.getInstance(activity, PeerStoriesView.this.currentAccount);
            VideoPlayerSharedScope videoPlayerSharedScope = PeerStoriesView.this.playerSharedScope;
            long j = (videoPlayerSharedScope == null || (videoPlayerHolder = videoPlayerSharedScope.player) == null) ? 0L : videoPlayerHolder.currentPosition;
            DraftsController draftsController = MessagesController.getInstance(PeerStoriesView.this.currentAccount).getStoriesController().getDraftsController();
            TL_stories.StoryItem storyItem = PeerStoriesView.this.currentStory.storyItem;
            StoryEntry forEdit = draftsController.getForEdit(storyItem.dialogId, storyItem);
            if (forEdit == null || forEdit.isRepostMessage || (file = forEdit.file) == null || !file.exists()) {
                forEdit = StoryEntry.fromStoryItem(PeerStoriesView.this.currentStory.getPath(), PeerStoriesView.this.currentStory.storyItem);
                forEdit.editStoryPeerId = PeerStoriesView.this.dialogId;
            }
            StoryEntry storyEntryCopy = forEdit.copy();
            if (PeerStoriesView.this.isBotsPreview()) {
                storyEntryCopy.botId = PeerStoriesView.this.dialogId;
                storyEntryCopy.editingBotPreview = MessagesController.toInputMedia(PeerStoriesView.this.currentStory.storyItem.media);
                StoriesController.StoriesList storiesList = storyViewer.storiesList;
                if (storiesList instanceof StoriesController.BotPreviewsList) {
                    storyEntryCopy.botLang = ((StoriesController.BotPreviewsList) storiesList).lang_code;
                }
            }
            storyRecorder.openEdit(StoryRecorder.SourceView.fromStoryViewer(storyViewer), storyEntryCopy, j, true);
            storyRecorder.setOnFullyOpenListener(new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda57
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onCreate$12();
                }
            });
            storyRecorder.setOnPrepareCloseListener(new Utilities.Callback4() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda58
                @Override // org.telegram.messenger.Utilities.Callback4
                public final void run(Object obj, Object obj2, Object obj3, Object obj4) {
                    this.f$0.lambda$onCreate$15(sharedResources, (Long) obj, (Runnable) obj2, (Boolean) obj3, (Long) obj4);
                }
            });
        }

        public /* synthetic */ void lambda$onCreate$12() {
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            peerStoriesView.editOpened = true;
            peerStoriesView.setActive(false);
        }

        public /* synthetic */ void lambda$onCreate$15(SharedResources sharedResources, Long l, final Runnable runnable, Boolean bool, Long l2) {
            final long jCurrentTimeMillis = System.currentTimeMillis();
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            VideoPlayerSharedScope videoPlayerSharedScope = peerStoriesView.playerSharedScope;
            StoryViewer.VideoPlayerHolder videoPlayerHolder = videoPlayerSharedScope.player;
            if (videoPlayerHolder == null) {
                peerStoriesView.delegate.setPopupIsVisible(false);
                PeerStoriesView.this.setActive(true);
                PeerStoriesView peerStoriesView2 = PeerStoriesView.this;
                peerStoriesView2.editOpened = false;
                peerStoriesView2.onImageReceiverThumbLoaded = new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        PeerStoriesView.C69308.$r8$lambda$44cxg47EmO7wGipUuO4ZEp56bsM(runnable);
                    }
                };
                if (bool.booleanValue()) {
                    PeerStoriesView.this.updatePosition();
                }
                AndroidUtilities.runOnUIThread(runnable, 400L);
                return;
            }
            videoPlayerHolder.firstFrameRendered = false;
            videoPlayerSharedScope.firstFrameRendered = false;
            videoPlayerHolder.setOnReadyListener(new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    PeerStoriesView.C69308.$r8$lambda$93s1RGoVHBMKE1kd2Jx9tC2wRPw(runnable, jCurrentTimeMillis);
                }
            });
            PeerStoriesView.this.delegate.setPopupIsVisible(false);
            if (PeerStoriesView.this.muteIconView != null) {
                PeerStoriesView.this.muteIconView.setAnimation(sharedResources.muteDrawable);
            }
            PeerStoriesView.this.setActive(((PeerStoriesView.this.videoDuration <= 0 || l.longValue() <= PeerStoriesView.this.videoDuration - 1400) ? l : 0L).longValue(), true);
            PeerStoriesView.this.editOpened = false;
            AndroidUtilities.runOnUIThread(runnable, 400L);
            if (bool.booleanValue()) {
                PeerStoriesView.this.updatePosition();
            }
        }

        public static /* synthetic */ void $r8$lambda$44cxg47EmO7wGipUuO4ZEp56bsM(Runnable runnable) {
            AndroidUtilities.cancelRunOnUIThread(runnable);
            AndroidUtilities.runOnUIThread(runnable);
        }

        public static /* synthetic */ void $r8$lambda$93s1RGoVHBMKE1kd2Jx9tC2wRPw(Runnable runnable, long j) {
            AndroidUtilities.cancelRunOnUIThread(runnable);
            AndroidUtilities.runOnUIThread(runnable, Math.max(0L, 32 - (System.currentTimeMillis() - j)));
        }

        public /* synthetic */ void lambda$onCreate$27(Context context, final TL_stories.StoryItem storyItem, final StoryViewer storyViewer, final SharedResources sharedResources, View view) {
            File path = PeerStoriesView.this.currentStory.getPath();
            if (path == null || !path.exists()) {
                PeerStoriesView.this.showDownloadAlert();
                return;
            }
            final Activity activityFindActivity = AndroidUtilities.findActivity(context);
            if (activityFindActivity == null) {
                return;
            }
            this.edit = true;
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
            Runnable runnable = new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda46
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onCreate$26(activityFindActivity, storyItem, storyViewer, sharedResources);
                }
            };
            if (PeerStoriesView.this.delegate.releasePlayer(runnable)) {
                return;
            }
            runnable.run();
        }

        public /* synthetic */ void lambda$onCreate$26(Activity activity, final TL_stories.StoryItem storyItem, StoryViewer storyViewer, final SharedResources sharedResources) {
            StoryViewer.VideoPlayerHolder videoPlayerHolder;
            StoryRecorder storyRecorder = StoryRecorder.getInstance(activity, PeerStoriesView.this.currentAccount);
            VideoPlayerSharedScope videoPlayerSharedScope = PeerStoriesView.this.playerSharedScope;
            long j = (videoPlayerSharedScope == null || (videoPlayerHolder = videoPlayerSharedScope.player) == null) ? 0L : videoPlayerHolder.currentPosition;
            StoryEntry storyEntryFromStoryItem = StoryEntry.fromStoryItem(PeerStoriesView.this.currentStory.getPath(), PeerStoriesView.this.currentStory.storyItem);
            storyEntryFromStoryItem.editStoryPeerId = PeerStoriesView.this.dialogId;
            storyEntryFromStoryItem.cover = StoryEntry.getCoverTime(PeerStoriesView.this.currentStory.storyItem);
            StoryEntry storyEntryCopy = storyEntryFromStoryItem.copy();
            storyEntryCopy.isEditingCover = true;
            final TL_stories.StoryItem storyItem2 = PeerStoriesView.this.currentStory.storyItem;
            storyEntryCopy.editingCoverDocument = storyItem2.media.document;
            storyEntryCopy.updateDocumentRef = new Utilities.Callback() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda54
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$onCreate$21(storyItem2, storyItem, (Utilities.Callback) obj);
                }
            };
            if (PeerStoriesView.this.isBotsPreview()) {
                storyEntryCopy.botId = PeerStoriesView.this.dialogId;
                storyEntryCopy.editingBotPreview = MessagesController.toInputMedia(PeerStoriesView.this.currentStory.storyItem.media);
                StoriesController.StoriesList storiesList = storyViewer.storiesList;
                if (storiesList instanceof StoriesController.BotPreviewsList) {
                    storyEntryCopy.botLang = ((StoriesController.BotPreviewsList) storiesList).lang_code;
                }
            }
            storyRecorder.openEdit(StoryRecorder.SourceView.fromStoryViewer(storyViewer), storyEntryCopy, j, true);
            storyRecorder.setOnFullyOpenListener(new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda55
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onCreate$22();
                }
            });
            storyRecorder.setOnPrepareCloseListener(new Utilities.Callback4() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda56
                @Override // org.telegram.messenger.Utilities.Callback4
                public final void run(Object obj, Object obj2, Object obj3, Object obj4) {
                    this.f$0.lambda$onCreate$25(sharedResources, (Long) obj, (Runnable) obj2, (Boolean) obj3, (Long) obj4);
                }
            });
        }

        public /* synthetic */ void lambda$onCreate$21(final TL_stories.StoryItem storyItem, final TL_stories.StoryItem storyItem2, final Utilities.Callback callback) {
            final StoriesController.BotPreviewsList botPreviewsList;
            if ((storyItem instanceof StoriesController.BotPreview) && (botPreviewsList = ((StoriesController.BotPreview) storyItem).list) != null) {
                botPreviewsList.reload(new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        PeerStoriesView.C69308.m20734$r8$lambda$_yNBXYv7gOPVoHtzsMoD1J64fI(botPreviewsList, storyItem2, callback);
                    }
                });
                return;
            }
            TL_stories.TL_stories_getStoriesByID tL_stories_getStoriesByID = new TL_stories.TL_stories_getStoriesByID();
            tL_stories_getStoriesByID.peer = MessagesController.getInstance(PeerStoriesView.this.currentAccount).getInputPeer(storyItem.dialogId);
            tL_stories_getStoriesByID.f1461id.add(Integer.valueOf(storyItem.f1454id));
            ConnectionsManager.getInstance(PeerStoriesView.this.currentAccount).sendRequest(tL_stories_getStoriesByID, new RequestDelegate() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda6
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    this.f$0.lambda$onCreate$20(storyItem, callback, tLObject, tL_error);
                }
            });
        }

        /* JADX INFO: renamed from: $r8$lambda$_yN-BXYv7gOPVoHtzsMoD1J64fI */
        public static /* synthetic */ void m20734$r8$lambda$_yNBXYv7gOPVoHtzsMoD1J64fI(StoriesController.BotPreviewsList botPreviewsList, TL_stories.StoryItem storyItem, Utilities.Callback callback) {
            TL_stories.StoryItem storyItem2;
            TLRPC.MessageMedia messageMedia;
            TLRPC.Document document;
            TLRPC.Document document2;
            for (int i = 0; i < botPreviewsList.messageObjects.size(); i++) {
                MessageObject messageObject = botPreviewsList.messageObjects.get(i);
                if (messageObject != null && (storyItem2 = messageObject.storyItem) != null && (messageMedia = storyItem2.media) != null && (document = storyItem.media.document) != null && (document2 = messageMedia.document) != null && document2.f1253id == document.f1253id) {
                    callback.run(document2);
                    return;
                }
            }
            callback.run(null);
        }

        public /* synthetic */ void lambda$onCreate$20(final TL_stories.StoryItem storyItem, final Utilities.Callback callback, final TLObject tLObject, TLRPC.TL_error tL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onCreate$19(tLObject, storyItem, callback);
                }
            });
        }

        public /* synthetic */ void lambda$onCreate$19(TLObject tLObject, TL_stories.StoryItem storyItem, Utilities.Callback callback) {
            if (tLObject instanceof TL_stories.TL_stories_stories) {
                TL_stories.TL_stories_stories tL_stories_stories = (TL_stories.TL_stories_stories) tLObject;
                MessagesController.getInstance(PeerStoriesView.this.currentAccount).putUsers(tL_stories_stories.users, false);
                MessagesController.getInstance(PeerStoriesView.this.currentAccount).putChats(tL_stories_stories.chats, false);
                for (int i = 0; i < tL_stories_stories.stories.size(); i++) {
                    if (tL_stories_stories.stories.get(i).f1454id == storyItem.f1454id) {
                        callback.run(tL_stories_stories.stories.get(i).media.document);
                        return;
                    }
                }
            }
            callback.run(null);
        }

        public /* synthetic */ void lambda$onCreate$22() {
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            peerStoriesView.editOpened = true;
            peerStoriesView.setActive(false);
        }

        public /* synthetic */ void lambda$onCreate$25(SharedResources sharedResources, Long l, final Runnable runnable, Boolean bool, Long l2) {
            final long jCurrentTimeMillis = System.currentTimeMillis();
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            VideoPlayerSharedScope videoPlayerSharedScope = peerStoriesView.playerSharedScope;
            StoryViewer.VideoPlayerHolder videoPlayerHolder = videoPlayerSharedScope.player;
            if (videoPlayerHolder == null) {
                peerStoriesView.delegate.setPopupIsVisible(false);
                PeerStoriesView.this.setActive(true);
                PeerStoriesView peerStoriesView2 = PeerStoriesView.this;
                peerStoriesView2.editOpened = false;
                peerStoriesView2.onImageReceiverThumbLoaded = new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        PeerStoriesView.C69308.$r8$lambda$NV2WjvVVPH_8msYrDZ0Kte2fosg(runnable);
                    }
                };
                if (bool.booleanValue()) {
                    PeerStoriesView.this.updatePosition();
                }
                AndroidUtilities.runOnUIThread(runnable, 400L);
                return;
            }
            videoPlayerHolder.firstFrameRendered = false;
            videoPlayerSharedScope.firstFrameRendered = false;
            videoPlayerHolder.setOnReadyListener(new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    PeerStoriesView.C69308.$r8$lambda$VU5a7tw08M0tpjO9YNLN2d5ydoQ(runnable, jCurrentTimeMillis);
                }
            });
            PeerStoriesView.this.delegate.setPopupIsVisible(false);
            if (PeerStoriesView.this.muteIconView != null) {
                PeerStoriesView.this.muteIconView.setAnimation(sharedResources.muteDrawable);
            }
            PeerStoriesView.this.setActive(((PeerStoriesView.this.videoDuration <= 0 || l.longValue() <= PeerStoriesView.this.videoDuration - 1400) ? l : 0L).longValue(), true);
            PeerStoriesView.this.editOpened = false;
            AndroidUtilities.runOnUIThread(runnable, 400L);
            if (bool.booleanValue()) {
                PeerStoriesView.this.updatePosition();
            }
        }

        public static /* synthetic */ void $r8$lambda$NV2WjvVVPH_8msYrDZ0Kte2fosg(Runnable runnable) {
            AndroidUtilities.cancelRunOnUIThread(runnable);
            AndroidUtilities.runOnUIThread(runnable);
        }

        public static /* synthetic */ void $r8$lambda$VU5a7tw08M0tpjO9YNLN2d5ydoQ(Runnable runnable, long j) {
            AndroidUtilities.cancelRunOnUIThread(runnable);
            AndroidUtilities.runOnUIThread(runnable, Math.max(0L, 32 - (System.currentTimeMillis() - j)));
        }

        public /* synthetic */ void lambda$onCreate$29(final TL_stories.StoryItem storyItem, final boolean z, final Theme.ResourcesProvider resourcesProvider, View view) {
            ArrayList<TL_stories.StoryItem> arrayList = new ArrayList<>();
            arrayList.add(storyItem);
            MessagesController.getInstance(PeerStoriesView.this.currentAccount).getStoriesController().updateStoriesPinned(PeerStoriesView.this.dialogId, arrayList, z, new Utilities.Callback() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda38
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$onCreate$28(storyItem, z, resourcesProvider, (Boolean) obj);
                }
            });
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
        }

        public /* synthetic */ void lambda$onCreate$28(TL_stories.StoryItem storyItem, boolean z, Theme.ResourcesProvider resourcesProvider, Boolean bool) {
            if (bool.booleanValue()) {
                storyItem.pinned = z;
                PeerStoriesView peerStoriesView = PeerStoriesView.this;
                if (peerStoriesView.isSelf) {
                    BulletinFactory.m1142of(peerStoriesView.storyContainer, resourcesProvider).createSimpleBulletin(z ? C2797R.raw.contact_check : C2797R.raw.chats_archived, LocaleController.getString(z ? C2797R.string.StoryPinnedToProfile : C2797R.string.StoryArchivedFromProfile)).show();
                    return;
                } else if (z) {
                    BulletinFactory.m1142of(peerStoriesView.storyContainer, resourcesProvider).createSimpleBulletin(C2797R.raw.contact_check, LocaleController.getString(C2797R.string.StoryPinnedToPosts), LocaleController.getString(C2797R.string.StoryPinnedToPostsDescription)).show();
                    return;
                } else {
                    BulletinFactory.m1142of(peerStoriesView.storyContainer, resourcesProvider).createSimpleBulletin(C2797R.raw.chats_archived, LocaleController.getString(C2797R.string.StoryUnpinnedFromPosts)).show();
                    return;
                }
            }
            BulletinFactory.m1142of(PeerStoriesView.this.storyContainer, resourcesProvider).createSimpleBulletin(C2797R.raw.error, LocaleController.getString(C2797R.string.UnknownError)).show();
        }

        public /* synthetic */ void lambda$onCreate$30(View view) {
            PeerStoriesView.this.saveToGallery();
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
        }

        public /* synthetic */ void lambda$onCreate$31(View view) {
            AndroidUtilities.addToClipboard(PeerStoriesView.this.currentStory.createLink());
            PeerStoriesView.this.onLinkCopied();
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
        }

        public /* synthetic */ void lambda$onCreate$32(View view) {
            PeerStoriesView.this.shareStory(false);
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
        }

        public /* synthetic */ void lambda$onCreate$33(boolean z, View view) {
            LivePlayer livePlayer = LivePlayer.recording;
            if (livePlayer != null) {
                livePlayer.setMuted(!z);
            }
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
        }

        public /* synthetic */ void lambda$onCreate$34(View view) {
            LivePlayer livePlayer = LivePlayer.recording;
            if (livePlayer != null) {
                livePlayer.switchCamera();
            }
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
        }

        public /* synthetic */ void lambda$onCreate$35(StoryViewer storyViewer, View view) {
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
            if (storyViewer != null) {
                storyViewer.switchToPip();
            }
        }

        public /* synthetic */ void lambda$onCreate$39(Theme.ResourcesProvider resourcesProvider, StoryViewer storyViewer, View view) {
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
            StoryPrivacyBottomSheet storyPrivacyBottomSheetIsEdit = new StoryPrivacyBottomSheet(PeerStoriesView.this.getContext(), 86400, resourcesProvider).setLive(true).setPeer(MessagesController.getInstance(PeerStoriesView.this.currentAccount).getInputPeer(PeerStoriesView.this.dialogId)).setLiveSettings(true).allowCover(false).setCount(1).isEdit(false);
            LivePlayer livePlayer = storyViewer.livePlayer;
            boolean z = livePlayer != null && livePlayer.areMessagesEnabled();
            boolean zAllowScreenshots = PeerStoriesView.this.currentStory.allowScreenshots();
            TL_stories.StoryItem storyItem = PeerStoriesView.this.currentStory.storyItem;
            boolean z2 = storyItem != null && storyItem.pinned;
            LivePlayer livePlayer2 = storyViewer.livePlayer;
            final StoryPrivacyBottomSheet storyPrivacyBottomSheet = storyPrivacyBottomSheetIsEdit.set(z, zAllowScreenshots, z2, livePlayer2 == null ? 0 : (int) livePlayer2.getSendPaidMessagesStars());
            storyPrivacyBottomSheet.whenSelectedRules(new StoryPrivacyBottomSheet.DoneCallback() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda37
                @Override // org.telegram.ui.Stories.recorder.StoryPrivacyBottomSheet.DoneCallback
                public final void done(StoryPrivacyBottomSheet.StoryPrivacy storyPrivacy, boolean z3, boolean z4, boolean z5, boolean z6, TLRPC.InputPeer inputPeer, int i, Runnable runnable, Runnable runnable2) {
                    this.f$0.lambda$onCreate$38(storyPrivacyBottomSheet, storyPrivacy, z3, z4, z5, z6, inputPeer, i, runnable, runnable2);
                }
            }, false);
            storyPrivacyBottomSheet.show();
        }

        public /* synthetic */ void lambda$onCreate$38(final StoryPrivacyBottomSheet storyPrivacyBottomSheet, StoryPrivacyBottomSheet.StoryPrivacy storyPrivacy, boolean z, boolean z2, boolean z3, boolean z4, TLRPC.InputPeer inputPeer, int i, Runnable runnable, Runnable runnable2) {
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            TL_stories.StoryItem storyItem = peerStoriesView.currentStory.storyItem;
            if ((storyItem != null && storyItem.pinned) != z3) {
                MessagesController.getInstance(peerStoriesView.currentAccount).getStoriesController().updateStoriesPinned(PeerStoriesView.this.dialogId, PeerStoriesView.this.storyItems, z3, null);
            }
            TL_stories.StoryItem storyItem2 = PeerStoriesView.this.currentStory.storyItem;
            if (storyItem2 != null) {
                TLRPC.MessageMedia messageMedia = storyItem2.media;
                if (messageMedia instanceof TLRPC.TL_messageMediaVideoStream) {
                    TLRPC.InputGroupCall inputGroupCall = ((TLRPC.TL_messageMediaVideoStream) messageMedia).call;
                    TL_phone.toggleGroupCallSettings togglegroupcallsettings = new TL_phone.toggleGroupCallSettings();
                    togglegroupcallsettings.call = inputGroupCall;
                    togglegroupcallsettings.messages_enabled = Boolean.valueOf(z);
                    togglegroupcallsettings.send_paid_messages_stars = Long.valueOf(i);
                    ConnectionsManager.getInstance(PeerStoriesView.this.currentAccount).sendRequest(togglegroupcallsettings, new RequestDelegate() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda53
                        @Override // org.telegram.tgnet.RequestDelegate
                        public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                            this.f$0.lambda$onCreate$37(storyPrivacyBottomSheet, tLObject, tL_error);
                        }
                    });
                }
            }
        }

        public /* synthetic */ void lambda$onCreate$37(final StoryPrivacyBottomSheet storyPrivacyBottomSheet, TLObject tLObject, TLRPC.TL_error tL_error) {
            if (tLObject instanceof TLRPC.Updates) {
                MessagesController.getInstance(PeerStoriesView.this.currentAccount).processUpdates((TLRPC.Updates) tLObject, false);
            }
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    storyPrivacyBottomSheet.lambda$new$0();
                }
            });
        }

        public /* synthetic */ void lambda$onCreate$41(Theme.ResourcesProvider resourcesProvider, final StoryViewer storyViewer, View view) {
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
            new AlertDialog.Builder(PeerStoriesView.this.getContext(), resourcesProvider).setTitle(LocaleController.getString(C2797R.string.LiveStoryEndAlertTitle)).setMessage(LocaleController.getString(C2797R.string.LiveStoryEndAlertText)).setPositiveButton(LocaleController.getString(C2797R.string.LiveStoryEndAlertButton), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda49
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i) {
                    this.f$0.lambda$onCreate$40(storyViewer, alertDialog, i);
                }
            }).setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null).makeRed(-1).show();
        }

        public /* synthetic */ void lambda$onCreate$40(StoryViewer storyViewer, AlertDialog alertDialog, int i) {
            LivePlayer livePlayer = storyViewer.livePlayer;
            if (livePlayer != null) {
                livePlayer.end();
            } else {
                PeerStoriesView.this.deleteStory();
            }
        }

        public /* synthetic */ void lambda$onCreate$42(View view) {
            PeerStoriesView.this.deleteStory();
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
        }

        public /* synthetic */ void lambda$onCreate$43(String str, Theme.ResourcesProvider resourcesProvider, TLObject tLObject, String str2, View view) {
            MessagesController.getNotificationsSettings(PeerStoriesView.this.currentAccount).edit().putBoolean(NotificationsSettingsFacade.PROPERTY_STORIES_NOTIFY + str, false).apply();
            NotificationsController.getInstance(PeerStoriesView.this.currentAccount).updateServerNotificationsSettings(PeerStoriesView.this.dialogId, 0L);
            BulletinFactory.m1142of(PeerStoriesView.this.storyContainer, resourcesProvider).createUsersBulletin(Arrays.asList(tLObject), AndroidUtilities.replaceTags(LocaleController.formatString("NotificationsStoryMutedHint", C2797R.string.NotificationsStoryMutedHint, str2))).setTag(2).show();
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
        }

        public /* synthetic */ void lambda$onCreate$44(String str, Theme.ResourcesProvider resourcesProvider, TLObject tLObject, String str2, View view) {
            MessagesController.getNotificationsSettings(PeerStoriesView.this.currentAccount).edit().putBoolean(NotificationsSettingsFacade.PROPERTY_STORIES_NOTIFY + str, true).apply();
            NotificationsController.getInstance(PeerStoriesView.this.currentAccount).updateServerNotificationsSettings(PeerStoriesView.this.dialogId, 0L);
            BulletinFactory.m1142of(PeerStoriesView.this.storyContainer, resourcesProvider).createUsersBulletin(Arrays.asList(tLObject), AndroidUtilities.replaceTags(LocaleController.formatString("NotificationsStoryUnmutedHint", C2797R.string.NotificationsStoryUnmutedHint, str2))).setTag(2).show();
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
        }

        public /* synthetic */ void lambda$onCreate$45(View view) {
            MediaDataController.getInstance(PeerStoriesView.this.currentAccount).removePeer(PeerStoriesView.this.dialogId);
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            peerStoriesView.storiesController.toggleHidden(peerStoriesView.dialogId, true, false, true);
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
        }

        public /* synthetic */ void lambda$onCreate$46(View view) {
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            peerStoriesView.toggleArchiveForStory(peerStoriesView.dialogId);
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
        }

        public /* synthetic */ void lambda$onCreate$47(View view) {
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            peerStoriesView.toggleArchiveForStory(peerStoriesView.dialogId);
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
        }

        public /* synthetic */ void lambda$onCreate$48(StoryViewer storyViewer, View view) {
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
            if (storyViewer != null) {
                storyViewer.switchToPip();
            }
        }

        public /* synthetic */ void lambda$onCreate$49(View view) {
            PeerStoriesView.this.saveToGallery();
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
        }

        public /* synthetic */ void lambda$onCreate$52(View view) {
            AndroidUtilities.addToClipboard(PeerStoriesView.this.currentStory.createLink());
            PeerStoriesView.this.onLinkCopied();
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
        }

        public /* synthetic */ void lambda$onCreate$53(View view) {
            PeerStoriesView.this.shareStory(false);
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
        }

        public /* synthetic */ void lambda$onCreate$54(View view) {
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            peerStoriesView.currentStory.storyItem.translated = false;
            StoriesStorage storiesStorage = MessagesController.getInstance(peerStoriesView.currentAccount).getStoriesController().getStoriesStorage();
            TL_stories.StoryItem storyItem = PeerStoriesView.this.currentStory.storyItem;
            storiesStorage.updateStoryItem(storyItem.dialogId, storyItem);
            PeerStoriesView.this.cancelTextSelection();
            PeerStoriesView.this.updatePosition();
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
        }

        public /* synthetic */ void lambda$onCreate$57(View view) {
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            peerStoriesView.currentStory.storyItem.translated = true;
            peerStoriesView.cancelTextSelection();
            Delegate delegate = PeerStoriesView.this.delegate;
            if (delegate != null) {
                delegate.setTranslating(true);
            }
            StoriesStorage storiesStorage = MessagesController.getInstance(PeerStoriesView.this.currentAccount).getStoriesController().getStoriesStorage();
            TL_stories.StoryItem storyItem = PeerStoriesView.this.currentStory.storyItem;
            storiesStorage.updateStoryItem(storyItem.dialogId, storyItem);
            final long jCurrentTimeMillis = System.currentTimeMillis();
            final Runnable runnable = new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda47
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onCreate$55();
                }
            };
            MessagesController.getInstance(PeerStoriesView.this.currentAccount).getTranslateController().translateStory(PeerStoriesView.this.currentStory.storyItem, new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda48
                @Override // java.lang.Runnable
                public final void run() {
                    AndroidUtilities.runOnUIThread(runnable, Math.max(0L, 500 - (System.currentTimeMillis() - jCurrentTimeMillis)));
                }
            });
            PeerStoriesView.this.updatePosition();
            PeerStoriesView peerStoriesView2 = PeerStoriesView.this;
            peerStoriesView2.checkBlackoutMode = true;
            peerStoriesView2.storyCaptionView.expand(true);
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
        }

        public /* synthetic */ void lambda$onCreate$55() {
            Delegate delegate = PeerStoriesView.this.delegate;
            if (delegate != null) {
                delegate.setTranslating(false);
            }
            PeerStoriesView.this.updatePosition();
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            peerStoriesView.checkBlackoutMode = true;
            peerStoriesView.storyCaptionView.expand(true);
        }

        public /* synthetic */ void lambda$onCreate$59(final StoryViewer storyViewer, Theme.ResourcesProvider resourcesProvider, View view) {
            if (storyViewer != null) {
                storyViewer.setOverlayVisible(true);
            }
            int i = PeerStoriesView.this.currentAccount;
            Context context = PeerStoriesView.this.getContext();
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            ReportBottomSheet.openStory(i, context, peerStoriesView.currentStory.storyItem, BulletinFactory.m1142of(peerStoriesView.storyContainer, resourcesProvider), resourcesProvider, new Utilities.Callback() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda52
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    PeerStoriesView.C69308.m20741$r8$lambda$rv4ddzDfrcC3oInSjhp75oHYjo(storyViewer, (Boolean) obj);
                }
            });
            CustomPopupMenu customPopupMenu = PeerStoriesView.this.popupMenu;
            if (customPopupMenu != null) {
                customPopupMenu.dismiss();
            }
        }

        /* JADX INFO: renamed from: $r8$lambda$rv4ddzDfrcC3oI-nSjhp75oHYjo */
        public static /* synthetic */ void m20741$r8$lambda$rv4ddzDfrcC3oInSjhp75oHYjo(StoryViewer storyViewer, Boolean bool) {
            if (storyViewer != null) {
                storyViewer.setOverlayVisible(false);
            }
        }

        public /* synthetic */ void lambda$onCreate$60(StoryContainsEmojiButton storyContainsEmojiButton, View view) {
            Delegate delegate;
            EmojiPacksAlert alert = storyContainsEmojiButton.getAlert();
            if (alert == null || (delegate = PeerStoriesView.this.delegate) == null) {
                return;
            }
            delegate.showDialog(alert);
            PeerStoriesView.this.popupMenu.dismiss();
        }

        @Override // org.telegram.p035ui.Components.CustomPopupMenu
        public void onDismissed() {
            if (!this.edit && !this.val$popupStillVisible[0]) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$8$$ExternalSyntheticLambda36
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onDismissed$61();
                    }
                });
            }
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            peerStoriesView.popupMenu = null;
            peerStoriesView.editStoryItem = null;
        }

        public /* synthetic */ void lambda$onDismissed$61() {
            PeerStoriesView.this.delegate.setPopupIsVisible(false);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$9 */
    public class C69319 extends FrameLayout {
        public C69319(final Context context2) {
            super(context2);
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            return super.onTouchEvent(motionEvent);
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            return super.dispatchTouchEvent(motionEvent);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:78:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x013c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$new$10(android.view.View r13) {
        /*
            Method dump skipped, instruction units count: 327
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Stories.PeerStoriesView.lambda$new$10(android.view.View):void");
    }

    public /* synthetic */ void lambda$new$9() {
        this.delegate.setIsHintVisible(false);
    }

    public /* synthetic */ void lambda$new$11(StoryViewer storyViewer, View view) {
        if (this.currentStory.hasSound()) {
            storyViewer.toggleSilentMode();
            if (storyViewer.soundEnabled()) {
                MessagesController.getGlobalMainSettings().edit().putInt("taptostorysoundhint", 3).apply();
                return;
            }
            return;
        }
        showNoSoundHint(true);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$10 */
    public class C689410 extends LiveCommentsView {
        final /* synthetic */ StoryViewer val$storyViewer;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C689410(final Context context2, final StoryViewer storyViewer22, ViewGroup viewGroup, View view2, FrameLayout frameLayout4, final StoryViewer storyViewer222) {
            super(context2, storyViewer222, viewGroup, view2, frameLayout4);
            storyViewer = storyViewer222;
        }

        @Override // android.view.View
        public void setVisibility(int i) {
            super.setVisibility(i);
            PeerStoriesView.this.liveCommentsShadowView.setVisibility(i);
        }

        @Override // org.telegram.p035ui.Stories.LiveCommentsView
        public TLRPC.Peer getDefaultSendAs() {
            LivePlayer livePlayer = storyViewer.livePlayer;
            if (livePlayer != null) {
                return livePlayer.getDefaultSendAs();
            }
            return null;
        }

        @Override // org.telegram.p035ui.Stories.LiveCommentsView
        public boolean isMe(long j) {
            if (j == UserConfig.getInstance(PeerStoriesView.this.currentAccount).getClientUserId()) {
                return true;
            }
            LivePlayer livePlayer = storyViewer.livePlayer;
            if (livePlayer != null && j == DialogObject.getPeerDialogId(livePlayer.getDefaultSendAs())) {
                return true;
            }
            if (PeerStoriesView.this.sendAsPeersObj != null) {
                for (int i = 0; i < PeerStoriesView.this.sendAsPeersObj.peers.size(); i++) {
                    if (j == DialogObject.getPeerDialogId(PeerStoriesView.this.sendAsPeersObj.peers.get(i).peer)) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override // org.telegram.p035ui.Stories.LiveCommentsView
        public void setCollapsed(boolean z, boolean z2) {
            super.setCollapsed(z, z2);
            if (PeerStoriesView.this.commentButton != null) {
                PeerStoriesView.this.commentButton.setCollapsed(z, z2);
            }
        }

        @Override // org.telegram.p035ui.Stories.LiveCommentsView
        public void onMessagesCountUpdated() {
            if (PeerStoriesView.this.commentButton != null) {
                PeerStoriesView.this.commentButton.setCount(getUnreadMessagesCount());
            }
        }

        @Override // org.telegram.p035ui.Stories.LiveCommentsView
        public void onStarsCountUpdated() {
            PeerStoriesView.this.starsButton.setCount((int) getStarsCount());
            PeerStoriesView.this.starsButton.setFilled(areSendingStars());
        }

        @Override // org.telegram.p035ui.Stories.LiveCommentsView
        public void onStarsButtonPressed(long j, boolean z) {
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            if (z) {
                peerStoriesView.starsButton.playEffect(j);
            } else {
                peerStoriesView.starsButton.stopEffects();
            }
        }

        @Override // org.telegram.p035ui.Stories.LiveCommentsView
        public void onStarReaction(long j, int i, int i2) {
            if (PeerStoriesView.this.starsButtonEffectsView == null) {
                return;
            }
            PeerStoriesView.this.starsButtonEffectsView.pushChip(j, i, i2);
        }

        @Override // org.telegram.p035ui.Stories.LiveCommentsView
        public void onCancelledStarReaction(long j) {
            if (PeerStoriesView.this.starsButtonEffectsView == null) {
                return;
            }
            PeerStoriesView.this.starsButtonEffectsView.removeChipsFrom(j);
        }

        @Override // org.telegram.p035ui.Stories.LiveCommentsView
        public void onStarsButtonCancelled() {
            PeerStoriesView.this.starsButton.stopEffects();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$11 */
    public class C689511 extends TextSelectionHelper.Callback {
        public C689511() {
        }

        @Override // org.telegram.ui.Cells.TextSelectionHelper.Callback
        public void onStateChanged(boolean z) {
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            peerStoriesView.delegate.setIsInSelectionMode(peerStoriesView.storyCaptionView.textSelectionHelper.isInSelectionMode());
        }
    }

    public void createStealthModeItem(ActionBarPopupWindow.ActionBarPopupWindowLayout actionBarPopupWindowLayout) {
        if (isBotsPreview() || this.currentStory.isLive || !UserConfig.getInstance(this.currentAccount).isPremium()) {
            return;
        }
        ActionBarMenuItem.addItem(actionBarPopupWindowLayout, C2797R.drawable.msg_stories_stealth2, LocaleController.getString(C2797R.string.StealthModeButton), false, this.resourcesProvider).setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda54
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createStealthModeItem$12(view);
            }
        });
    }

    public /* synthetic */ void lambda$createStealthModeItem$12(View view) {
        if (this.stealthModeIsActive) {
            StealthModeAlert.showStealthModeEnabledBulletin();
        } else {
            this.delegate.showDialog(new StealthModeAlert(getContext(), getY() + this.storyContainer.getY(), 0, this.resourcesProvider));
        }
        CustomPopupMenu customPopupMenu = this.popupMenu;
        if (customPopupMenu != null) {
            customPopupMenu.dismiss();
        }
    }

    public void createQualityItem(ActionBarPopupWindow.ActionBarPopupWindowLayout actionBarPopupWindowLayout) {
        final boolean z = MessagesController.getInstance(this.currentAccount).storyQualityFull;
        if (UserConfig.getInstance(this.currentAccount).isPremium()) {
            ActionBarMenuItem.addItem(actionBarPopupWindowLayout, z ? C2797R.drawable.menu_quality_sd : C2797R.drawable.menu_quality_hd, LocaleController.getString(z ? C2797R.string.StoryQualityDecrease : C2797R.string.StoryQualityIncrease), false, this.resourcesProvider).setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda52
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$createQualityItem$14(z, view);
                }
            });
        }
    }

    public /* synthetic */ void lambda$createQualityItem$14(boolean z, View view) {
        MessagesController.getInstance(this.currentAccount).setStoryQuality(!z);
        BulletinFactory.m1142of(this.storyContainer, this.resourcesProvider).createSimpleBulletin(C2797R.raw.chats_infotip, LocaleController.getString(!z ? C2797R.string.StoryQualityIncreasedTitle : C2797R.string.StoryQualityDecreasedTitle), LocaleController.getString(!z ? C2797R.string.StoryQualityIncreasedMessage : C2797R.string.StoryQualityDecreasedMessage)).show();
        CustomPopupMenu customPopupMenu = this.popupMenu;
        if (customPopupMenu != null) {
            customPopupMenu.dismiss();
        }
    }

    public void showLikesReaction(boolean z) {
        if (this.likesReactionShowing == z || this.currentStory.storyItem == null) {
            return;
        }
        this.likesReactionShowing = z;
        if (z) {
            this.likesReactionLayout.setVisibility(0);
        }
        this.likesReactionLayout.setStoryItem(this.currentStory.storyItem);
        this.delegate.setIsLikesReaction(z);
        if (z) {
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.likesReactionShowProgress, z ? 1.0f : 0.0f);
            this.likesReactionLayout.setTransitionProgress(this.likesReactionShowProgress);
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda23
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f$0.lambda$showLikesReaction$17(valueAnimator);
                }
            });
            valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Stories.PeerStoriesView.14
                final /* synthetic */ boolean val$show;

                public C689614(boolean z2) {
                    z = z2;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (z) {
                        return;
                    }
                    PeerStoriesView.this.likesReactionLayout.setVisibility(8);
                    PeerStoriesView.this.likesReactionLayout.reset();
                }
            });
            valueAnimatorOfFloat.setDuration(200L);
            valueAnimatorOfFloat.setInterpolator(CubicBezierInterpolator.EASE_OUT);
            valueAnimatorOfFloat.start();
            return;
        }
        if (this.likesReactionLayout.getReactionsWindow() != null) {
            this.likesReactionLayout.getReactionsWindow().dismissWithAlpha();
        }
        this.likesReactionLayout.animate().alpha(0.0f).setDuration(150L).setListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Stories.PeerStoriesView.15
            public C689715() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                PeerStoriesView.this.likesReactionShowProgress = 0.0f;
                PeerStoriesView.this.likesReactionLayout.setAlpha(1.0f);
                PeerStoriesView.this.likesReactionLayout.setVisibility(8);
                PeerStoriesView.this.likesReactionLayout.reset();
            }
        }).start();
    }

    public /* synthetic */ void lambda$showLikesReaction$17(ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.likesReactionShowProgress = fFloatValue;
        this.likesReactionLayout.setTransitionProgress(fFloatValue);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$14 */
    public class C689614 extends AnimatorListenerAdapter {
        final /* synthetic */ boolean val$show;

        public C689614(boolean z2) {
            z = z2;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (z) {
                return;
            }
            PeerStoriesView.this.likesReactionLayout.setVisibility(8);
            PeerStoriesView.this.likesReactionLayout.reset();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$15 */
    public class C689715 extends AnimatorListenerAdapter {
        public C689715() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            PeerStoriesView.this.likesReactionShowProgress = 0.0f;
            PeerStoriesView.this.likesReactionLayout.setAlpha(1.0f);
            PeerStoriesView.this.likesReactionLayout.setVisibility(8);
            PeerStoriesView.this.likesReactionLayout.reset();
        }
    }

    public void likeStory(ReactionsLayoutInBubble.VisibleReaction visibleReaction) {
        boolean z;
        TLRPC.Reaction reaction;
        TL_stories.StoryItem storyItem = this.currentStory.storyItem;
        if (storyItem == null) {
            return;
        }
        boolean z2 = (storyItem == null || storyItem.sent_reaction == null) ? false : true;
        TLRPC.Reaction reaction2 = storyItem.sent_reaction;
        if (reaction2 != null && visibleReaction == null) {
            animateLikeButton();
            this.storiesController.setStoryReaction(this.dialogId, this.currentStory.storyItem, null);
        } else if (visibleReaction == null) {
            TLRPC.TL_availableReaction tL_availableReaction = MediaDataController.getInstance(this.currentAccount).getReactionsMap().get("❤");
            if (tL_availableReaction != null) {
                this.drawAnimatedEmojiAsMovingReaction = false;
                this.reactionEffectImageReceiver.setImage(ImageLocation.getForDocument(tL_availableReaction.around_animation), ReactionsEffectOverlay.getFilterForAroundAnimation(), null, null, null, 0);
                if (this.reactionEffectImageReceiver.getLottieAnimation() != null) {
                    this.reactionEffectImageReceiver.getLottieAnimation().setCurrentFrame(0, false, true);
                }
                this.drawReactionEffect = true;
                this.storiesController.setStoryReaction(this.dialogId, this.currentStory.storyItem, ReactionsLayoutInBubble.VisibleReaction.fromEmojicon(tL_availableReaction));
            }
        } else {
            animateLikeButton();
            this.storiesController.setStoryReaction(this.dialogId, this.currentStory.storyItem, visibleReaction);
        }
        TL_stories.StoryItem storyItem2 = this.currentStory.storyItem;
        if (storyItem2 == null || (reaction = storyItem2.sent_reaction) == null) {
            this.storiesLikeButton.setReaction(null);
            z = false;
        } else {
            z2 = !z2;
            this.storiesLikeButton.setReaction(ReactionsLayoutInBubble.VisibleReaction.fromTL(reaction));
            try {
                performHapticFeedback(3);
            } catch (Exception unused) {
            }
            z = true;
        }
        if (this.isChannel && z2) {
            TL_stories.StoryItem storyItem3 = this.currentStory.storyItem;
            if (storyItem3.views == null) {
                storyItem3.views = new TL_stories.TL_storyViews();
            }
            TL_stories.StoryViews storyViews = this.currentStory.storyItem.views;
            int i = storyViews.reactions_count + (z ? 1 : -1);
            storyViews.reactions_count = i;
            if (i < 0) {
                storyViews.reactions_count = 0;
            }
        }
        TL_stories.StoryItem storyItem4 = this.currentStory.storyItem;
        ReactionsUtils.applyForStoryViews(reaction2, storyItem4.sent_reaction, storyItem4.views);
        updateUserViews(true);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$16 */
    public class C689816 extends AnimatorListenerAdapter {
        final /* synthetic */ View val$oldLikeButton;

        public C689816(View view) {
            view = view;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            AndroidUtilities.removeFromParent(view);
        }
    }

    private void animateLikeButton() {
        StoriesLikeButton storiesLikeButton = this.storiesLikeButton;
        storiesLikeButton.animate().alpha(0.0f).scaleX(0.8f).scaleY(0.8f).setListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Stories.PeerStoriesView.16
            final /* synthetic */ View val$oldLikeButton;

            public C689816(View storiesLikeButton2) {
                view = storiesLikeButton2;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                AndroidUtilities.removeFromParent(view);
            }
        }).setDuration(150L).start();
        int iM1036dp = AndroidUtilities.m1036dp(8.0f);
        StoriesLikeButton storiesLikeButton2 = new StoriesLikeButton(getContext(), this.sharedResources);
        this.storiesLikeButton = storiesLikeButton2;
        storiesLikeButton2.setPadding(iM1036dp, iM1036dp, iM1036dp, iM1036dp);
        this.storiesLikeButton.setAlpha(0.0f);
        this.storiesLikeButton.setScaleX(0.8f);
        this.storiesLikeButton.setScaleY(0.8f);
        this.storiesLikeButton.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(150L);
        this.likeButtonContainer.addView(this.storiesLikeButton, LayoutHelper.createFrame(40, 40, 3));
        this.drawReactionEffect = false;
    }

    public ArrayList<TLRPC.InputStickerSet> getAnimatedEmojiSets(StoryItemHolder storyItemHolder) {
        StoryEntry storyEntry;
        AnimatedEmojiSpan[] animatedEmojiSpanArr;
        TL_stories.MediaArea mediaArea;
        TLRPC.InputStickerSet inputStickerSet;
        ArrayList<TLRPC.MessageEntity> arrayList;
        TLRPC.InputStickerSet inputStickerSet2;
        if (storyItemHolder == null) {
            return null;
        }
        HashSet hashSet = new HashSet();
        ArrayList<TLRPC.InputStickerSet> arrayList2 = new ArrayList<>();
        TL_stories.StoryItem storyItem = storyItemHolder.storyItem;
        int i = 0;
        if (storyItem != null && storyItem.media_areas != null) {
            for (int i2 = 0; i2 < storyItemHolder.storyItem.media_areas.size(); i2++) {
                TL_stories.MediaArea mediaArea2 = storyItemHolder.storyItem.media_areas.get(i2);
                if (mediaArea2 instanceof TL_stories.TL_mediaAreaSuggestedReaction) {
                    TLRPC.Reaction reaction = mediaArea2.reaction;
                    if (reaction instanceof TLRPC.TL_reactionCustomEmoji) {
                        TLRPC.Document documentFindDocument = AnimatedEmojiDrawable.findDocument(this.currentAccount, ((TLRPC.TL_reactionCustomEmoji) reaction).document_id);
                        if (documentFindDocument != null && (inputStickerSet2 = MessageObject.getInputStickerSet(documentFindDocument)) != null && !hashSet.contains(Long.valueOf(inputStickerSet2.f1270id))) {
                            hashSet.add(Long.valueOf(inputStickerSet2.f1270id));
                            arrayList2.add(inputStickerSet2);
                        }
                    }
                }
            }
        }
        TL_stories.StoryItem storyItem2 = storyItemHolder.storyItem;
        if (storyItem2 != null && (arrayList = storyItem2.entities) != null && !arrayList.isEmpty()) {
            while (i < storyItemHolder.storyItem.entities.size()) {
                TLRPC.MessageEntity messageEntity = storyItemHolder.storyItem.entities.get(i);
                if (messageEntity instanceof TLRPC.TL_messageEntityCustomEmoji) {
                    TLRPC.TL_messageEntityCustomEmoji tL_messageEntityCustomEmoji = (TLRPC.TL_messageEntityCustomEmoji) messageEntity;
                    TLRPC.Document documentFindDocument2 = tL_messageEntityCustomEmoji.document;
                    if (documentFindDocument2 == null) {
                        documentFindDocument2 = AnimatedEmojiDrawable.findDocument(this.currentAccount, tL_messageEntityCustomEmoji.document_id);
                    }
                    if (documentFindDocument2 != null) {
                        TLRPC.InputStickerSet inputStickerSet3 = MessageObject.getInputStickerSet(documentFindDocument2);
                        if (!hashSet.contains(Long.valueOf(inputStickerSet3.f1270id))) {
                            hashSet.add(Long.valueOf(inputStickerSet3.f1270id));
                            arrayList2.add(inputStickerSet3);
                        }
                    }
                }
                i++;
            }
        } else {
            StoriesController.UploadingStory uploadingStory = storyItemHolder.uploadingStory;
            if (uploadingStory != null && (storyEntry = uploadingStory.entry) != null) {
                if (storyEntry.mediaEntities != null) {
                    for (int i3 = 0; i3 < storyItemHolder.uploadingStory.entry.mediaEntities.size(); i3++) {
                        VideoEditedInfo.MediaEntity mediaEntity = storyItemHolder.uploadingStory.entry.mediaEntities.get(i3);
                        if (mediaEntity.type == 4 && (mediaArea = mediaEntity.mediaArea) != null) {
                            TLRPC.Reaction reaction2 = mediaArea.reaction;
                            if (reaction2 instanceof TLRPC.TL_reactionCustomEmoji) {
                                TLRPC.Document documentFindDocument3 = AnimatedEmojiDrawable.findDocument(this.currentAccount, ((TLRPC.TL_reactionCustomEmoji) reaction2).document_id);
                                if (documentFindDocument3 != null && (inputStickerSet = MessageObject.getInputStickerSet(documentFindDocument3)) != null && !hashSet.contains(Long.valueOf(inputStickerSet.f1270id))) {
                                    hashSet.add(Long.valueOf(inputStickerSet.f1270id));
                                    arrayList2.add(inputStickerSet);
                                }
                            }
                        }
                    }
                }
                CharSequence charSequence = storyItemHolder.uploadingStory.entry.caption;
                if ((charSequence instanceof Spanned) && (animatedEmojiSpanArr = (AnimatedEmojiSpan[]) ((Spanned) charSequence).getSpans(0, charSequence.length(), AnimatedEmojiSpan.class)) != null) {
                    while (i < animatedEmojiSpanArr.length) {
                        AnimatedEmojiSpan animatedEmojiSpan = animatedEmojiSpanArr[i];
                        TLRPC.Document documentFindDocument4 = animatedEmojiSpan.document;
                        if (documentFindDocument4 == null) {
                            documentFindDocument4 = AnimatedEmojiDrawable.findDocument(this.currentAccount, animatedEmojiSpan.documentId);
                        }
                        if (documentFindDocument4 != null) {
                            TLRPC.InputStickerSet inputStickerSet4 = MessageObject.getInputStickerSet(documentFindDocument4);
                            if (!hashSet.contains(Long.valueOf(inputStickerSet4.f1270id))) {
                                hashSet.add(Long.valueOf(inputStickerSet4.f1270id));
                                arrayList2.add(inputStickerSet4);
                            }
                        }
                        i++;
                    }
                }
            }
        }
        return arrayList2;
    }

    public void toggleArchiveForStory(final long j) {
        String str;
        boolean z;
        TLObject tLObject;
        int i = this.currentAccount;
        if (j > 0) {
            TLRPC.User user = MessagesController.getInstance(i).getUser(Long.valueOf(j));
            str = user.first_name;
            z = user.stories_hidden;
            tLObject = user;
        } else {
            TLRPC.Chat chat = MessagesController.getInstance(i).getChat(Long.valueOf(-j));
            str = chat.title;
            z = chat.stories_hidden;
            tLObject = chat;
        }
        final boolean z2 = !z;
        final TLObject tLObject2 = tLObject;
        final String str2 = str;
        final MessagesController messagesController = MessagesController.getInstance(this.currentAccount);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda57
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$toggleArchiveForStory$20(messagesController, j, z2, str2, tLObject2);
            }
        }, 200L);
    }

    public /* synthetic */ void lambda$toggleArchiveForStory$20(final MessagesController messagesController, final long j, final boolean z, String str, TLObject tLObject) {
        SpannableStringBuilder spannableStringBuilderReplaceTags;
        messagesController.getStoriesController().toggleHidden(j, z, false, true);
        BulletinFactory.UndoObject undoObject = new BulletinFactory.UndoObject();
        undoObject.onUndo = new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                messagesController.getStoriesController().toggleHidden(j, !z, false, true);
            }
        };
        undoObject.onAction = new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                messagesController.getStoriesController().toggleHidden(j, z, true, true);
            }
        };
        if (!z) {
            spannableStringBuilderReplaceTags = AndroidUtilities.replaceTags(LocaleController.formatString(C2797R.string.StoriesMovedToDialogs, ContactsController.formatName(str, null, 10)));
        } else {
            spannableStringBuilderReplaceTags = AndroidUtilities.replaceTags(LocaleController.formatString(C2797R.string.StoriesMovedToContacts, ContactsController.formatName(str, null, 10)));
        }
        BulletinFactory.m1142of(this.topBulletinContainer, this.resourcesProvider).createUsersBulletin(Arrays.asList(tLObject), spannableStringBuilderReplaceTags, null, undoObject).setTag(2).show(true);
    }

    private void createFailView() {
        if (this.failView != null) {
            return;
        }
        StoryFailView storyFailView = new StoryFailView(getContext(), this.resourcesProvider);
        this.failView = storyFailView;
        storyFailView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda30
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createFailView$21(view);
            }
        });
        this.failView.setAlpha(0.0f);
        this.failView.setVisibility(8);
        addView(this.failView, LayoutHelper.createFrame(-1, -2.0f, 83, 0.0f, 0.0f, 0.0f, 0.0f));
    }

    public /* synthetic */ void lambda$createFailView$21(View view) {
        StoriesController.UploadingStory uploadingStory;
        StoryItemHolder storyItemHolder = this.currentStory;
        if (storyItemHolder == null || (uploadingStory = storyItemHolder.uploadingStory) == null) {
            return;
        }
        uploadingStory.tryAgain();
        updatePosition();
    }

    private void createPremiumBlockedText() {
        if (this.premiumBlockedText != null) {
            return;
        }
        if (this.chatActivityEnterView == null) {
            createEnterView();
        }
        LinearLayout linearLayout = new LinearLayout(getContext());
        this.premiumBlockedText = linearLayout;
        linearLayout.setOrientation(0);
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setScaleX(1.35f);
        imageView.setScaleY(1.35f);
        imageView.setImageResource(C2797R.drawable.mini_switch_lock);
        imageView.setColorFilter(new PorterDuffColorFilter(-8026747, PorterDuff.Mode.SRC_IN));
        TextView textView = new TextView(getContext());
        this.premiumBlockedText1 = textView;
        textView.setTextColor(-8026747);
        this.premiumBlockedText1.setTextSize(1, 16.0f);
        this.premiumBlockedText1.setText(LocaleController.getString(this.isGroup ? C2797R.string.StoryGroupRepliesLocked : C2797R.string.StoryRepliesLocked));
        TextView textView2 = new TextView(getContext());
        this.premiumBlockedText2 = textView2;
        textView2.setTextColor(-1);
        this.premiumBlockedText2.setTextSize(1, 12.0f);
        this.premiumBlockedText2.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1036dp(40.0f), 452984831, 855638015));
        this.premiumBlockedText2.setGravity(17);
        ScaleStateListAnimator.apply(this.premiumBlockedText2);
        this.premiumBlockedText2.setText(LocaleController.getString(C2797R.string.StoryRepliesLockedButton));
        this.premiumBlockedText2.setPadding(AndroidUtilities.m1036dp(7.0f), 0, AndroidUtilities.m1036dp(7.0f), 0);
        this.premiumBlockedText.addView(imageView, LayoutHelper.createLinear(22, 22, 16, 12, 1, 4, 0));
        this.premiumBlockedText.addView(this.premiumBlockedText1, LayoutHelper.createLinear(-2, -2, 16, 0.0f, -0.33f, 0.0f, 0.0f));
        this.premiumBlockedText.addView(this.premiumBlockedText2, LayoutHelper.createLinear(-2, 19, 16, 5.0f, -0.33f, 0.0f, 0.0f));
        this.chatActivityEnterView.addView(this.premiumBlockedText, LayoutHelper.createFrame(-1, -1.0f, 119, 14.0f, 0.0f, 8.0f, 0.0f));
    }

    private void updatePremiumBlockedText() {
        boolean z = this.areLiveCommentsDisabled;
        TextView textView = this.premiumBlockedText1;
        if (z) {
            if (textView != null) {
                textView.setText(LocaleController.getString(C2797R.string.LiveStoryCommentsDisabled));
            }
            TextView textView2 = this.premiumBlockedText2;
            if (textView2 != null) {
                textView2.setVisibility(8);
                return;
            }
            return;
        }
        if (textView != null) {
            textView.setText(LocaleController.getString(this.isGroup ? C2797R.string.StoryGroupRepliesLocked : C2797R.string.StoryRepliesLocked));
        }
        TextView textView3 = this.premiumBlockedText2;
        if (textView3 != null) {
            textView3.setVisibility(0);
            this.premiumBlockedText2.setText(LocaleController.getString(C2797R.string.StoryRepliesLockedButton));
        }
    }

    public Activity findActivity() {
        Activity activityFindActivity;
        StoryViewer storyViewer = this.storyViewer;
        if (storyViewer == null || (activityFindActivity = storyViewer.parentActivity) == null) {
            activityFindActivity = AndroidUtilities.findActivity(getContext());
        }
        return activityFindActivity == null ? LaunchActivity.instance : activityFindActivity;
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$17 */
    public class C689917 extends BaseFragment {
        @Override // org.telegram.p035ui.ActionBar.BaseFragment
        public boolean isLightStatusBar() {
            return false;
        }

        public C689917() {
        }

        @Override // org.telegram.p035ui.ActionBar.BaseFragment
        public Activity getParentActivity() {
            return PeerStoriesView.this.findActivity();
        }

        /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$17$1 */
        public class AnonymousClass1 extends WrappedResourceProvider {
            public AnonymousClass1(Theme.ResourcesProvider resourcesProvider) {
                super(resourcesProvider);
            }

            @Override // org.telegram.p035ui.WrappedResourceProvider
            public void appendColors() {
                this.sparseIntArray.append(Theme.key_dialogBackground, -14737633);
                this.sparseIntArray.append(Theme.key_windowBackgroundGray, -13421773);
            }
        }

        @Override // org.telegram.p035ui.ActionBar.BaseFragment
        public Theme.ResourcesProvider getResourceProvider() {
            return new WrappedResourceProvider(PeerStoriesView.this.resourcesProvider) { // from class: org.telegram.ui.Stories.PeerStoriesView.17.1
                public AnonymousClass1(Theme.ResourcesProvider resourcesProvider) {
                    super(resourcesProvider);
                }

                @Override // org.telegram.p035ui.WrappedResourceProvider
                public void appendColors() {
                    this.sparseIntArray.append(Theme.key_dialogBackground, -14737633);
                    this.sparseIntArray.append(Theme.key_windowBackgroundGray, -13421773);
                }
            };
        }

        @Override // org.telegram.p035ui.ActionBar.BaseFragment
        public boolean presentFragment(BaseFragment baseFragment) {
            if (PeerStoriesView.this.storyViewer == null) {
                return true;
            }
            PeerStoriesView.this.storyViewer.presentFragment(baseFragment);
            return true;
        }

        @Override // org.telegram.p035ui.ActionBar.BaseFragment
        public Dialog showDialog(Dialog dialog) {
            if (PeerStoriesView.this.storyViewer != null) {
                PeerStoriesView.this.storyViewer.showDialog(dialog);
                return dialog;
            }
            if (dialog != null) {
                dialog.show();
            }
            return dialog;
        }
    }

    private BaseFragment fragmentForLimit() {
        return new BaseFragment() { // from class: org.telegram.ui.Stories.PeerStoriesView.17
            @Override // org.telegram.p035ui.ActionBar.BaseFragment
            public boolean isLightStatusBar() {
                return false;
            }

            public C689917() {
            }

            @Override // org.telegram.p035ui.ActionBar.BaseFragment
            public Activity getParentActivity() {
                return PeerStoriesView.this.findActivity();
            }

            /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$17$1 */
            public class AnonymousClass1 extends WrappedResourceProvider {
                public AnonymousClass1(Theme.ResourcesProvider resourcesProvider) {
                    super(resourcesProvider);
                }

                @Override // org.telegram.p035ui.WrappedResourceProvider
                public void appendColors() {
                    this.sparseIntArray.append(Theme.key_dialogBackground, -14737633);
                    this.sparseIntArray.append(Theme.key_windowBackgroundGray, -13421773);
                }
            }

            @Override // org.telegram.p035ui.ActionBar.BaseFragment
            public Theme.ResourcesProvider getResourceProvider() {
                return new WrappedResourceProvider(PeerStoriesView.this.resourcesProvider) { // from class: org.telegram.ui.Stories.PeerStoriesView.17.1
                    public AnonymousClass1(Theme.ResourcesProvider resourcesProvider) {
                        super(resourcesProvider);
                    }

                    @Override // org.telegram.p035ui.WrappedResourceProvider
                    public void appendColors() {
                        this.sparseIntArray.append(Theme.key_dialogBackground, -14737633);
                        this.sparseIntArray.append(Theme.key_windowBackgroundGray, -13421773);
                    }
                };
            }

            @Override // org.telegram.p035ui.ActionBar.BaseFragment
            public boolean presentFragment(BaseFragment baseFragment) {
                if (PeerStoriesView.this.storyViewer == null) {
                    return true;
                }
                PeerStoriesView.this.storyViewer.presentFragment(baseFragment);
                return true;
            }

            @Override // org.telegram.p035ui.ActionBar.BaseFragment
            public Dialog showDialog(Dialog dialog) {
                if (PeerStoriesView.this.storyViewer != null) {
                    PeerStoriesView.this.storyViewer.showDialog(dialog);
                    return dialog;
                }
                if (dialog != null) {
                    dialog.show();
                }
                return dialog;
            }
        };
    }

    public void showPremiumBlockedToast() {
        String userName;
        Bulletin bulletinCreateSimpleBulletin;
        if (this.areLiveCommentsDisabled) {
            return;
        }
        if (this.isGroup) {
            if (this.boostsStatus != null && this.canApplyBoost != null) {
                LimitReachedBottomSheet.openBoostsForRemoveRestrictions(fragmentForLimit(), this.boostsStatus, this.canApplyBoost, this.dialogId, true);
                return;
            }
            StoryViewer storyViewer = this.storyViewer;
            if (storyViewer != null) {
                storyViewer.setOverlayVisible(true);
            }
            MessagesController.getInstance(this.currentAccount).getBoostsController().getBoostsStats(this.dialogId, new Consumer() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda43
                @Override // com.google.android.exoplayer2.util.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$showPremiumBlockedToast$23((TL_stories.TL_premium_boostsStatus) obj);
                }
            });
            return;
        }
        ChatActivityEnterView chatActivityEnterView = this.chatActivityEnterView;
        int i = -this.shiftDp;
        this.shiftDp = i;
        AndroidUtilities.shakeViewSpring(chatActivityEnterView, i);
        BotWebViewVibrationEffect.APP_ERROR.vibrate();
        if (this.dialogId < 0) {
            userName = _UrlKt.FRAGMENT_ENCODE_SET;
        } else {
            userName = UserObject.getUserName(MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(this.dialogId)));
        }
        boolean zPremiumFeaturesBlocked = MessagesController.getInstance(this.currentAccount).premiumFeaturesBlocked();
        FrameLayout frameLayout = this.storyContainer;
        if (zPremiumFeaturesBlocked) {
            bulletinCreateSimpleBulletin = BulletinFactory.m1142of(frameLayout, this.resourcesProvider).createSimpleBulletin(C2797R.raw.star_premium_2, AndroidUtilities.replaceTags(LocaleController.formatString(C2797R.string.UserBlockedRepliesNonPremium, userName)));
        } else {
            bulletinCreateSimpleBulletin = BulletinFactory.m1142of(frameLayout, this.resourcesProvider).createSimpleBulletin(C2797R.raw.star_premium_2, AndroidUtilities.replaceTags(LocaleController.formatString(C2797R.string.UserBlockedRepliesNonPremium, userName)), LocaleController.getString(C2797R.string.UserBlockedNonPremiumButton), new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda44
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$showPremiumBlockedToast$24();
                }
            });
        }
        bulletinCreateSimpleBulletin.show();
    }

    public /* synthetic */ void lambda$showPremiumBlockedToast$23(final TL_stories.TL_premium_boostsStatus tL_premium_boostsStatus) {
        if (tL_premium_boostsStatus == null) {
            StoryViewer storyViewer = this.storyViewer;
            if (storyViewer != null) {
                storyViewer.setOverlayVisible(false);
                return;
            }
            return;
        }
        this.boostsStatus = tL_premium_boostsStatus;
        MessagesController.getInstance(this.currentAccount).getBoostsController().userCanBoostChannel(this.dialogId, tL_premium_boostsStatus, new Consumer() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda47
            @Override // com.google.android.exoplayer2.util.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$showPremiumBlockedToast$22(tL_premium_boostsStatus, (ChannelBoostsController.CanApplyBoost) obj);
            }
        });
    }

    public /* synthetic */ void lambda$showPremiumBlockedToast$22(TL_stories.TL_premium_boostsStatus tL_premium_boostsStatus, ChannelBoostsController.CanApplyBoost canApplyBoost) {
        this.canApplyBoost = canApplyBoost;
        LimitReachedBottomSheet.openBoostsForRemoveRestrictions(fragmentForLimit(), tL_premium_boostsStatus, canApplyBoost, this.dialogId, true);
        StoryViewer storyViewer = this.storyViewer;
        if (storyViewer != null) {
            storyViewer.setOverlayVisible(false);
        }
    }

    public /* synthetic */ void lambda$showPremiumBlockedToast$24() {
        StoryViewer storyViewer = this.storyViewer;
        if (storyViewer != null) {
            storyViewer.presentFragment(new PremiumPreviewFragment("noncontacts"));
        }
    }

    public void updateSpeedItem(boolean z) {
        ActionBarMenuSubItem actionBarMenuSubItem = this.speedItem;
        if (actionBarMenuSubItem == null || this.speedLayout == null || actionBarMenuSubItem.getVisibility() != 0) {
            return;
        }
        if (z) {
            if (Math.abs(StoryViewer.currentSpeed - 0.2f) < 0.05f) {
                this.speedItem.setSubtext(LocaleController.getString(C2797R.string.VideoSpeedVerySlow));
            } else if (Math.abs(StoryViewer.currentSpeed - 0.5f) < 0.05f) {
                this.speedItem.setSubtext(LocaleController.getString(C2797R.string.VideoSpeedSlow));
            } else if (Math.abs(StoryViewer.currentSpeed - 1.0f) < 0.05f) {
                this.speedItem.setSubtext(LocaleController.getString(C2797R.string.VideoSpeedNormal));
            } else if (Math.abs(StoryViewer.currentSpeed - 1.5f) < 0.05f) {
                this.speedItem.setSubtext(LocaleController.getString(C2797R.string.VideoSpeedFast));
            } else {
                float fAbs = Math.abs(StoryViewer.currentSpeed - 2.0f);
                ActionBarMenuSubItem actionBarMenuSubItem2 = this.speedItem;
                if (fAbs < 0.05f) {
                    actionBarMenuSubItem2.setSubtext(LocaleController.getString(C2797R.string.VideoSpeedVeryFast));
                } else {
                    actionBarMenuSubItem2.setSubtext(LocaleController.formatString(C2797R.string.VideoSpeedCustom, SpeedIconDrawable.formatNumber(StoryViewer.currentSpeed) + "x"));
                }
            }
        }
        this.speedLayout.update(StoryViewer.currentSpeed, z);
    }

    private void createCommentButton() {
        if (this.commentButton != null || getContext() == null) {
            return;
        }
        CommentButton commentButton = new CommentButton(getContext(), this.blurredBackgroundColorProvider);
        this.commentButton = commentButton;
        commentButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda25
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createCommentButton$25(view);
            }
        });
        addView(this.commentButton, LayoutHelper.createFrame(46, 42.0f, 83, 7.0f, 0.0f, 7.0f, 3.0f));
    }

    public /* synthetic */ void lambda$createCommentButton$25(View view) {
        this.liveCommentsView.setCollapsed(!r1.isCollapsed(), true);
    }

    private void createPaidReactionsButton() {
        if (this.starsButton != null || getContext() == null) {
            return;
        }
        this.starsButtonEffectsView = new PaidReactionButton.PaidReactionButtonEffectsView(getContext(), this.currentAccount);
        PaidReactionButton paidReactionButton = new PaidReactionButton(getContext(), this.starsButtonEffectsView, this.blurredBackgroundColorProvider);
        this.starsButton = paidReactionButton;
        paidReactionButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda36
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createPaidReactionsButton$26(view);
            }
        });
        this.starsButton.setOnLongClickListener(new View.OnLongClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda37
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return this.f$0.lambda$createPaidReactionsButton$27(view);
            }
        });
        addView(this.starsButton, LayoutHelper.createFrame(46, 42.0f, 85, 7.0f, 0.0f, 7.0f, 3.0f));
        addView(this.starsButtonEffectsView, LayoutHelper.createFrame(200, 200.0f, 85, 0.0f, 0.0f, 0.0f, 0.0f));
    }

    public /* synthetic */ void lambda$createPaidReactionsButton$26(View view) {
        if (disabledPaidFeatures(false)) {
            this.liveCommentsView.openStarsSheet(disabledPaidFeatures(false));
            return;
        }
        StarsController starsController = StarsController.getInstance(this.currentAccount);
        if (starsController.balanceAvailable() && starsController.balance.amount <= 0) {
            this.liveCommentsView.openStarsSheet(disabledPaidFeatures(false));
        } else {
            this.liveCommentsView.sendStars(1L, true);
        }
    }

    public /* synthetic */ boolean lambda$createPaidReactionsButton$27(View view) {
        this.liveCommentsView.openStarsSheet(disabledPaidFeatures(false));
        return true;
    }

    private void createMuteButton() {
        if (this.muteButton != null || getContext() == null) {
            return;
        }
        MuteButton muteButton = new MuteButton(getContext(), this.blurredBackgroundColorProvider);
        this.muteButton = muteButton;
        muteButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda33
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createMuteButton$28(view);
            }
        });
        MuteButton muteButton2 = this.muteButton;
        LivePlayer livePlayer = LivePlayer.recording;
        boolean z = true;
        muteButton2.setMuted(livePlayer != null && livePlayer.isMuted(), false);
        MuteButton muteButton3 = this.muteButton;
        LivePlayer livePlayer2 = LivePlayer.recording;
        if (livePlayer2 != null && !livePlayer2.isConnected()) {
            z = false;
        }
        muteButton3.setConnected(z, false);
        addView(this.muteButton, LayoutHelper.createFrame(46, 42.0f, 85, 7.0f, 0.0f, 7.0f, 3.0f));
    }

    public /* synthetic */ void lambda$createMuteButton$28(View view) {
        LivePlayer livePlayer = LivePlayer.recording;
        if (livePlayer == null) {
            return;
        }
        boolean zIsMuted = livePlayer.isMuted();
        LivePlayer.recording.setMuted(!zIsMuted);
        this.muteButton.setMuted(!zIsMuted, true);
    }

    public boolean disabledPaidFeatures(boolean z) {
        StoryViewer storyViewer;
        LivePlayer livePlayer;
        TLRPC.Peer defaultSendAs;
        LivePlayer livePlayer2;
        long clientUserId = UserConfig.getInstance(this.currentAccount).getClientUserId();
        long j = this.dialogId;
        if (j >= 0 || (livePlayer2 = this.storyViewer.livePlayer) == null) {
            return j >= 0 && (storyViewer = this.storyViewer) != null && (livePlayer = storyViewer.livePlayer) != null && livePlayer.isAdmin() && (!z || (defaultSendAs = this.storyViewer.livePlayer.getDefaultSendAs()) == null || this.dialogId == DialogObject.getPeerDialogId(defaultSendAs) || DialogObject.getPeerDialogId(defaultSendAs) == clientUserId || this.dialogId == clientUserId);
        }
        if (!z) {
            return false;
        }
        TLRPC.Peer defaultSendAs2 = livePlayer2.getDefaultSendAs();
        return (this.storyViewer.livePlayer.isAdmin() || ChatObject.canManageCalls(MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-this.dialogId)))) && (defaultSendAs2 == null || this.dialogId == DialogObject.getPeerDialogId(defaultSendAs2) || DialogObject.getPeerDialogId(defaultSendAs2) == UserConfig.getInstance(this.currentAccount).getClientUserId());
    }

    private void showPaidMessageHint() {
        HintView2 hintView2 = this.highlightMessageHintView;
        if (hintView2 != null) {
            if (hintView2.shown()) {
                return;
            } else {
                removeView(this.highlightMessageHintView);
            }
        }
        if (!disabledPaidFeatures(true) && MessagesController.getGlobalMainSettings().getInt("taptostoryhighlighthint", 0) < 3) {
            MessagesController.getGlobalMainSettings().edit().putInt("taptostoryhighlighthint", MessagesController.getGlobalMainSettings().getInt("taptostoryhighlighthint", 0) + 1).apply();
            final HintView2 hintView22 = new HintView2(getContext(), 3);
            this.highlightMessageHintView = hintView22;
            hintView22.setText(LocaleController.getString(C2797R.string.LiveStoryHighlightHint));
            this.highlightMessageHintView.setPadding(AndroidUtilities.m1036dp(8.0f), 0, AndroidUtilities.m1036dp(8.0f), 0);
            this.highlightMessageHintView.setTextAlign(Layout.Alignment.ALIGN_OPPOSITE);
            this.highlightMessageHintView.setOnHiddenListener(new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda24
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$showPaidMessageHint$29(hintView22);
                }
            });
            addView(this.highlightMessageHintView, LayoutHelper.createFrame(-1, 100, 87));
            this.highlightMessageHintView.show();
            updateViewOffsets();
        }
    }

    public /* synthetic */ void lambda$showPaidMessageHint$29(HintView2 hintView2) {
        removeView(hintView2);
        if (this.highlightMessageHintView == hintView2) {
            this.highlightMessageHintView = null;
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$18 */
    public class C690018 extends WrappedResourceProvider {
        public C690018(Theme.ResourcesProvider resourcesProvider) {
            super(resourcesProvider);
        }

        @Override // org.telegram.p035ui.WrappedResourceProvider
        public void appendColors() {
            this.sparseIntArray.put(Theme.key_chat_emojiPanelBackground, -1071635414);
        }
    }

    private void createEnterView() {
        C690119 c690119 = new C690119(AndroidUtilities.findActivity(getContext()), this, null, true, new WrappedResourceProvider(this.resourcesProvider) { // from class: org.telegram.ui.Stories.PeerStoriesView.18
            public C690018(Theme.ResourcesProvider resourcesProvider) {
                super(resourcesProvider);
            }

            @Override // org.telegram.p035ui.WrappedResourceProvider
            public void appendColors() {
                this.sparseIntArray.put(Theme.key_chat_emojiPanelBackground, -1071635414);
            }
        });
        this.chatActivityEnterView = c690119;
        c690119.getEditField().useAnimatedTextDrawable();
        this.chatActivityEnterView.getEditField().setScaleX(0.0f);
        this.chatActivityEnterView.setOverrideKeyboardAnimation(true);
        this.chatActivityEnterView.setClipChildren(false);
        this.chatActivityEnterView.setDelegate(new C690320());
        setDelegate(this.chatActivityEnterView);
        ChatActivityEnterView chatActivityEnterView = this.chatActivityEnterView;
        chatActivityEnterView.shouldDrawBackground = false;
        chatActivityEnterView.shouldDrawRecordedAudioPanelInParent = true;
        boolean z = this.currentStory.isLive;
        ChatActivityEnterView chatActivityEnterView2 = this.chatActivityEnterView;
        if (z) {
            chatActivityEnterView2.setAllowStickersAndGifs(true, false, false);
        } else {
            chatActivityEnterView2.setAllowStickersAndGifs(true, true, true);
        }
        this.chatActivityEnterView.updateColors();
        ChatActivityEnterView chatActivityEnterView3 = this.chatActivityEnterView;
        chatActivityEnterView3.isStories = true;
        addView(chatActivityEnterView3, LayoutHelper.createFrame(-1, -2.0f, 83, 7.0f, 0.0f, 7.0f, 0.0f));
        if (this.sendAsPeersObj != null) {
            this.chatActivityEnterView.updateSendAsButton(false);
        }
        this.chatActivityEnterView.recordingGuid = this.classGuid;
        this.playerSharedScope.viewsToInvalidate.add(this.storyContainer);
        this.playerSharedScope.viewsToInvalidate.add(this);
        if (this.attachedToWindow) {
            this.chatActivityEnterView.onResume();
        }
        checkStealthMode(false);
        if (isBotsPreview()) {
            this.chatActivityEnterView.setVisibility(8);
        }
        ChatActivitySideControlsButtonsLayout chatActivitySideControlsButtonsLayout = new ChatActivitySideControlsButtonsLayout(getContext(), this.resourcesProvider, this.blurredBackgroundColorProvider, this.blurredBackgroundDrawableFactory);
        this.sideControlsButtonsLayout = chatActivitySideControlsButtonsLayout;
        chatActivitySideControlsButtonsLayout.setOnClickListener(new ButtonOnClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda35
            @Override // org.telegram.p035ui.Components.chat.layouts.ButtonOnClickListener
            public final void onClick(int i, View view) {
                this.f$0.onSideControlButtonOnClick(i, view);
            }
        });
        addView(this.sideControlsButtonsLayout, LayoutHelper.createFrame(57, 300, 85));
        this.sideControlsButtonsLayout.setVisibility(8);
        this.chatActivityEnterView.setSideButtonsForAttach(this.sideControlsButtonsLayout);
        this.reactionsContainerIndex = getChildCount();
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$19 */
    public class C690119 extends ChatActivityEnterView {
        private int chatActivityEnterViewAnimateFromTop;
        int lastContentViewHeight;
        private Animator messageEditTextAnimator;
        int messageEditTextPredrawHeigth;
        int messageEditTextPredrawScrollY;

        public C690119(Activity activity, SizeNotifierFrameLayout sizeNotifierFrameLayout, ChatActivity chatActivity, boolean z, Theme.ResourcesProvider resourcesProvider) {
            super(activity, sizeNotifierFrameLayout, chatActivity, z, resourcesProvider);
        }

        @Override // org.telegram.p035ui.Components.ChatActivityEnterView
        public void updateSendAsButton(boolean z) {
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            super.updateSendAsButton(peerStoriesView.isPremiumBlocked || peerStoriesView.areLiveCommentsDisabled, z);
        }

        @Override // org.telegram.p035ui.Components.ChatActivityEnterView
        public boolean sendMessage() {
            int length;
            if (this.sendButtonContainer.getAlpha() < 0.5f) {
                openKeyboard();
                return false;
            }
            if (PeerStoriesView.this.currentStory.isLive) {
                long jMax = Math.max(PeerStoriesView.this.messageStars, PeerStoriesView.this.getMessageMinPrice());
                TLRPC.TL_textWithEntities textWithEntities = getTextWithEntities();
                CharSequence textWithEntities2 = MessageObject.formatTextWithEntities(textWithEntities, false, new TextPaint());
                if (textWithEntities2.length() > HighlightMessageSheet.getMaxLength(PeerStoriesView.this.currentAccount)) {
                    NumberTextView numberTextView = this.captionLimitView;
                    if (numberTextView != null) {
                        AndroidUtilities.shakeViewSpring(numberTextView, 3.5f);
                        try {
                            this.captionLimitView.performHapticFeedback(3, 2);
                        } catch (Exception unused) {
                        }
                    }
                    return false;
                }
                if (!PeerStoriesView.this.disabledPaidFeatures(true)) {
                    if (textWithEntities2 instanceof Spannable) {
                        Spannable spannable = (Spannable) textWithEntities2;
                        length = ((AnimatedEmojiSpan[]) spannable.getSpans(0, textWithEntities2.length(), AnimatedEmojiSpan.class)).length + ((Emoji.EmojiSpan[]) spannable.getSpans(0, textWithEntities2.length(), Emoji.EmojiSpan.class)).length;
                    } else {
                        length = 0;
                    }
                    int i = (int) jMax;
                    if (length > HighlightMessageSheet.getTierOption(PeerStoriesView.this.currentAccount, i, HighlightMessageSheet.TIER_EMOJIS) || textWithEntities2.length() > HighlightMessageSheet.getTierOption(PeerStoriesView.this.currentAccount, i, HighlightMessageSheet.TIER_LENGTH)) {
                        PeerStoriesView.this.lambda$updatePosition$46();
                        return false;
                    }
                }
                PeerStoriesView.this.liveCommentsView.send(textWithEntities, jMax);
                this.messageEditText.setText(_UrlKt.FRAGMENT_ENCODE_SET);
                AndroidUtilities.hideKeyboard(this);
                PeerStoriesView.this.messageStars = 0L;
                PeerStoriesView.this.checkStealthMode(true);
                checkSendButton(true);
                return true;
            }
            return super.sendMessage();
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            if (!isEnabled()) {
                RectF rectF = AndroidUtilities.rectTmp;
                rectF.set(0.0f, 0.0f, getWidth() + (PeerStoriesView.this.premiumBlockedText2 != null ? this.attachLayoutPaddingTranslationX * 1.5f : 0.0f), getHeight());
                boolean zContains = rectF.contains(motionEvent.getX(), motionEvent.getY());
                if (motionEvent.getAction() == 0) {
                    if (zContains && PeerStoriesView.this.premiumBlockedText2 != null) {
                        PeerStoriesView.this.premiumBlockedText2.setPressed(true);
                    }
                } else if (motionEvent.getAction() == 1) {
                    if (PeerStoriesView.this.premiumBlockedText2 != null) {
                        if (zContains && PeerStoriesView.this.premiumBlockedText2.isPressed()) {
                            PeerStoriesView.this.showPremiumBlockedToast();
                        }
                        PeerStoriesView.this.premiumBlockedText2.setPressed(false);
                    }
                } else if (motionEvent.getAction() == 3 && PeerStoriesView.this.premiumBlockedText2 != null) {
                    PeerStoriesView.this.premiumBlockedText2.setPressed(false);
                }
                return PeerStoriesView.this.premiumBlockedText2 != null && PeerStoriesView.this.premiumBlockedText2.isPressed();
            }
            return super.dispatchTouchEvent(motionEvent);
        }

        @Override // org.telegram.p035ui.Components.ChatActivityEnterView
        public void setHorizontalPadding(float f, float f2, float f3, boolean z) {
            if (PeerStoriesView.this.premiumBlockedText != null) {
                PeerStoriesView.this.premiumBlockedText.setTranslationX((1.0f - f3) * f);
            }
            super.setHorizontalPadding(f, f2, f3, z);
        }

        @Override // org.telegram.p035ui.Components.ChatActivityEnterView
        public boolean showConfirmAlert(Runnable runnable) {
            return PeerStoriesView.this.applyMessageToChat(runnable);
        }

        @Override // org.telegram.p035ui.Components.ChatActivityEnterView
        public void checkAnimation() {
            int backgroundTop = getBackgroundTop();
            int i = this.chatActivityEnterViewAnimateFromTop;
            if (i != 0 && backgroundTop != i) {
                int i2 = (this.animatedTop + i) - backgroundTop;
                setAnimatedTop(i2);
                PeerStoriesView peerStoriesView = PeerStoriesView.this;
                peerStoriesView.forceUpdateOffsets = true;
                if (peerStoriesView.changeBoundAnimator != null) {
                    PeerStoriesView.this.changeBoundAnimator.removeAllListeners();
                    PeerStoriesView.this.changeBoundAnimator.cancel();
                }
                View view = this.topView;
                if (view != null && view.getVisibility() == 0) {
                    this.topView.setTranslationY(this.animatedTop + ((1.0f - getTopViewEnterProgress()) * this.topView.getLayoutParams().height));
                }
                PeerStoriesView.this.invalidate();
                PeerStoriesView.this.changeBoundAnimator = ValueAnimator.ofFloat(i2, 0.0f);
                PeerStoriesView.this.changeBoundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$19$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        this.f$0.lambda$checkAnimation$0(valueAnimator);
                    }
                });
                PeerStoriesView.this.changeBoundAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Stories.PeerStoriesView.19.1
                    public AnonymousClass1() {
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        PeerStoriesView.this.invalidate();
                        C690119.this.setAnimatedTop(0);
                        C690119 c690119 = C690119.this;
                        PeerStoriesView.this.forceUpdateOffsets = true;
                        if (((ChatActivityEnterView) c690119).topView != null && ((ChatActivityEnterView) C690119.this).topView.getVisibility() == 0) {
                            ((ChatActivityEnterView) C690119.this).topView.setTranslationY(((ChatActivityEnterView) C690119.this).animatedTop + ((1.0f - C690119.this.getTopViewEnterProgress()) * ((ChatActivityEnterView) C690119.this).topView.getLayoutParams().height));
                        }
                        PeerStoriesView.this.changeBoundAnimator = null;
                    }
                });
                PeerStoriesView.this.changeBoundAnimator.setDuration(250L);
                PeerStoriesView.this.changeBoundAnimator.setInterpolator(ChatListItemAnimator.DEFAULT_INTERPOLATOR);
                PeerStoriesView.this.changeBoundAnimator.start();
                this.chatActivityEnterViewAnimateFromTop = 0;
            }
            if (this.shouldAnimateEditTextWithBounds) {
                float measuredHeight = (this.messageEditTextPredrawHeigth - this.messageEditText.getMeasuredHeight()) + (this.messageEditTextPredrawScrollY - this.messageEditText.getScrollY());
                EditTextCaption editTextCaption = this.messageEditText;
                editTextCaption.setOffsetY(editTextCaption.getOffsetY() - measuredHeight);
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.messageEditText.getOffsetY(), 0.0f);
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$19$$ExternalSyntheticLambda1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        this.f$0.lambda$checkAnimation$1(valueAnimator);
                    }
                });
                Animator animator = this.messageEditTextAnimator;
                if (animator != null) {
                    animator.cancel();
                }
                this.messageEditTextAnimator = valueAnimatorOfFloat;
                valueAnimatorOfFloat.setDuration(250L);
                valueAnimatorOfFloat.setInterpolator(ChatListItemAnimator.DEFAULT_INTERPOLATOR);
                valueAnimatorOfFloat.start();
                this.shouldAnimateEditTextWithBounds = false;
                PeerStoriesView.this.updateViewOffsets();
            }
            this.lastContentViewHeight = getMeasuredHeight();
        }

        public /* synthetic */ void lambda$checkAnimation$0(ValueAnimator valueAnimator) {
            setAnimatedTop((int) ((Float) valueAnimator.getAnimatedValue()).floatValue());
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            peerStoriesView.forceUpdateOffsets = true;
            peerStoriesView.invalidate();
            invalidate();
        }

        /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$19$1 */
        public class AnonymousClass1 extends AnimatorListenerAdapter {
            public AnonymousClass1() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                PeerStoriesView.this.invalidate();
                C690119.this.setAnimatedTop(0);
                C690119 c690119 = C690119.this;
                PeerStoriesView.this.forceUpdateOffsets = true;
                if (((ChatActivityEnterView) c690119).topView != null && ((ChatActivityEnterView) C690119.this).topView.getVisibility() == 0) {
                    ((ChatActivityEnterView) C690119.this).topView.setTranslationY(((ChatActivityEnterView) C690119.this).animatedTop + ((1.0f - C690119.this.getTopViewEnterProgress()) * ((ChatActivityEnterView) C690119.this).topView.getLayoutParams().height));
                }
                PeerStoriesView.this.changeBoundAnimator = null;
            }
        }

        public /* synthetic */ void lambda$checkAnimation$1(ValueAnimator valueAnimator) {
            this.messageEditText.setOffsetY(((Float) valueAnimator.getAnimatedValue()).floatValue());
        }

        @Override // org.telegram.p035ui.Components.ChatActivityEnterView
        public void onLineCountChanged(int i, int i2) {
            if (PeerStoriesView.this.chatActivityEnterView != null) {
                this.shouldAnimateEditTextWithBounds = true;
                this.messageEditTextPredrawHeigth = this.messageEditText.getMeasuredHeight();
                this.messageEditTextPredrawScrollY = this.messageEditText.getScrollY();
                invalidate();
                PeerStoriesView.this.invalidate();
                this.chatActivityEnterViewAnimateFromTop = PeerStoriesView.this.chatActivityEnterView.getBackgroundTop();
            }
        }

        @Override // org.telegram.p035ui.Components.ChatActivityEnterView
        public void updateRecordInterface(int i, boolean z) {
            super.updateRecordInterface(i, z);
            checkRecording();
        }

        @Override // org.telegram.p035ui.Components.ChatActivityEnterView
        public void isRecordingStateChanged() {
            super.isRecordingStateChanged();
            checkRecording();
        }

        private void checkRecording() {
            FrameLayout frameLayout;
            boolean z = PeerStoriesView.this.isRecording;
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            peerStoriesView.isRecording = peerStoriesView.chatActivityEnterView.isRecordingAudioVideo() || PeerStoriesView.this.chatActivityEnterView.seekbarVisible() || ((frameLayout = this.recordedAudioPanel) != null && frameLayout.getVisibility() == 0);
            if (z != PeerStoriesView.this.isRecording) {
                PeerStoriesView peerStoriesView2 = PeerStoriesView.this;
                if (peerStoriesView2.isActive) {
                    peerStoriesView2.delegate.setIsRecording(peerStoriesView2.isRecording);
                }
                invalidate();
                PeerStoriesView.this.storyContainer.invalidate();
            }
        }

        @Override // org.telegram.p035ui.Components.ChatActivityEnterView
        public void extendActionMode(Menu menu) {
            ChatActivity.fillActionModeMenu(menu, null, false, !PeerStoriesView.this.currentStory.isLive());
        }

        @Override // org.telegram.p035ui.Components.ChatActivityEnterView
        public boolean sendMessageInternal(boolean z, int i, int i2, long j, boolean z2) {
            if (MessagesController.getInstance(PeerStoriesView.this.currentAccount).isFrozen()) {
                AccountFrozenAlert.show(PeerStoriesView.this.currentAccount);
                return false;
            }
            return super.sendMessageInternal(z, i, i2, j, z2);
        }

        @Override // org.telegram.p035ui.Components.ChatActivityEnterView
        public int getMessagesCount() {
            if (PeerStoriesView.this.currentStory.isLive) {
                return 1;
            }
            return super.getMessagesCount();
        }

        @Override // org.telegram.p035ui.Components.ChatActivityEnterView
        public long getStarsPrice() {
            if (PeerStoriesView.this.currentStory.isLive) {
                return Math.max(PeerStoriesView.this.getMessageMinPrice(), PeerStoriesView.this.messageStars);
            }
            return super.getStarsPrice();
        }

        @Override // org.telegram.p035ui.Components.ChatActivityEnterView
        public boolean areLiveCommentsFree() {
            return PeerStoriesView.this.disabledPaidFeatures(true);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$20 */
    public class C690320 implements ChatActivityEnterView.ChatActivityEnterViewDelegate {
        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void needSendTyping() {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void needStartRecordAudio(int i) {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onAttachButtonHidden() {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onAttachButtonShow() {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onAudioVideoInterfaceUpdated() {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onMessageEditEnd(boolean z) {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onPreAudioVideoRecord() {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onSendLongClick() {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onStickersTab(boolean z) {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onSwitchRecordMode(boolean z) {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onTextSelectionChanged(int i, int i2) {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onTextSpansChanged(CharSequence charSequence) {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onUpdateSlowModeButton(View view, boolean z, CharSequence charSequence) {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onWindowSizeChanged(int i) {
        }

        public C690320() {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public TLRPC.TL_channels_sendAsPeers getSendAsPeers() {
            if (!PeerStoriesView.this.currentStory.isLive) {
                return null;
            }
            if (PeerStoriesView.this.storyViewer == null || PeerStoriesView.this.storyViewer.livePlayer == null || !PeerStoriesView.this.storyViewer.livePlayer.sendAsDisabled()) {
                return PeerStoriesView.this.sendAsPeersObj;
            }
            return null;
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public TLRPC.Peer getDefaultSendAs() {
            if (PeerStoriesView.this.storyViewer == null || PeerStoriesView.this.storyViewer.livePlayer == null || PeerStoriesView.this.storyViewer.livePlayer.sendAsDisabled()) {
                return null;
            }
            return PeerStoriesView.this.storyViewer.livePlayer.getDefaultSendAs();
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public boolean setDefaultSendAs(long j, long j2) {
            TL_stories.StoryItem storyItem = PeerStoriesView.this.currentStory.storyItem;
            if (storyItem != null && (storyItem.media instanceof TLRPC.TL_messageMediaVideoStream)) {
                TL_phone.saveDefaultSendAs savedefaultsendas = new TL_phone.saveDefaultSendAs();
                PeerStoriesView peerStoriesView = PeerStoriesView.this;
                savedefaultsendas.call = ((TLRPC.TL_messageMediaVideoStream) peerStoriesView.currentStory.storyItem.media).call;
                savedefaultsendas.send_as = MessagesController.getInstance(peerStoriesView.currentAccount).getInputPeer(j2);
                ConnectionsManager.getInstance(PeerStoriesView.this.currentAccount).sendRequest(savedefaultsendas, null);
                if (PeerStoriesView.this.storyViewer.livePlayer != null) {
                    PeerStoriesView.this.storyViewer.livePlayer.setDefaultSendAs(MessagesController.getInstance(PeerStoriesView.this.currentAccount).getPeer(j2));
                }
                PeerStoriesView.this.checkStealthMode(true);
                PeerStoriesView.this.chatActivityEnterView.updateSendAsButton(true);
                PeerStoriesView.this.chatActivityEnterView.checkSendButton(true);
                PeerStoriesView.this.updatePosition();
            }
            return true;
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public int getContentViewHeight() {
            return PeerStoriesView.this.getHeight();
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onMessageSend(CharSequence charSequence, boolean z, int i, int i2, final long j) {
            if (PeerStoriesView.this.isRecording) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$20$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onMessageSend$0(j);
                    }
                }, 200L);
            } else {
                PeerStoriesView.this.afterMessageSend(j <= 0);
            }
        }

        public /* synthetic */ void lambda$onMessageSend$0(long j) {
            PeerStoriesView.this.afterMessageSend(j <= 0);
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onTextChanged(CharSequence charSequence, boolean z, boolean z2) {
            if (PeerStoriesView.this.mentionContainer == null) {
                PeerStoriesView.this.createMentionsContainer();
            }
            if (PeerStoriesView.this.mentionContainer.getAdapter() != null) {
                PeerStoriesView.this.mentionContainer.setDialogId(PeerStoriesView.this.dialogId);
                boolean z3 = PeerStoriesView.this.currentStory.isLive;
                PeerStoriesView peerStoriesView = PeerStoriesView.this;
                if (z3) {
                    peerStoriesView.mentionContainer.getAdapter().clear(true);
                } else {
                    peerStoriesView.mentionContainer.getAdapter().setUserOrChat(MessagesController.getInstance(PeerStoriesView.this.currentAccount).getUser(Long.valueOf(PeerStoriesView.this.dialogId)), MessagesController.getInstance(PeerStoriesView.this.currentAccount).getChat(Long.valueOf(-PeerStoriesView.this.dialogId)));
                    PeerStoriesView.this.mentionContainer.getAdapter().lambda$searchUsernameOrHashtag$8(charSequence, PeerStoriesView.this.chatActivityEnterView.getCursorPosition(), null, false, false);
                }
            }
            PeerStoriesView.this.invalidate();
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void didPressAttachButton() {
            PeerStoriesView.this.openAttachMenu();
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void didPressSuggestionButton() {
            PeerStoriesView.this.lambda$updatePosition$46();
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void needStartRecordVideo(int i, boolean z, int i2, int i3, int i4, long j, long j2) {
            PeerStoriesView.this.checkInstantCameraView();
            if (PeerStoriesView.this.instantCameraView != null) {
                if (i == 0) {
                    PeerStoriesView.this.instantCameraView.showCamera(false);
                    return;
                }
                if (i == 1 || i == 3 || i == 4) {
                    PeerStoriesView.this.instantCameraView.send(i, z, i2, 0, i4, j, j2);
                } else if (i == 2 || i == 5) {
                    PeerStoriesView.this.instantCameraView.cancel(i == 2);
                }
            }
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void setFrontface(boolean z) {
            if (PeerStoriesView.this.instantCameraView != null) {
                PeerStoriesView.this.instantCameraView.setFrontface(z);
            }
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void toggleVideoRecordingPause() {
            if (PeerStoriesView.this.instantCameraView != null) {
                PeerStoriesView.this.instantCameraView.togglePause();
            }
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public boolean isVideoRecordingPaused() {
            return PeerStoriesView.this.instantCameraView != null && PeerStoriesView.this.instantCameraView.isPaused();
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void needChangeVideoPreviewState(int i, float f) {
            if (PeerStoriesView.this.instantCameraView != null) {
                PeerStoriesView.this.instantCameraView.changeVideoPreviewState(i, f);
            }
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void needShowMediaBanHint() {
            String firstName;
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            if (peerStoriesView.isGroup) {
                peerStoriesView.showPremiumBlockedToast();
                return;
            }
            if (peerStoriesView.mediaBanTooltip == null) {
                PeerStoriesView.this.mediaBanTooltip = new HintView(PeerStoriesView.this.getContext(), 9, PeerStoriesView.this.resourcesProvider);
                PeerStoriesView.this.mediaBanTooltip.setVisibility(8);
                PeerStoriesView peerStoriesView2 = PeerStoriesView.this;
                peerStoriesView2.addView(peerStoriesView2.mediaBanTooltip, LayoutHelper.createFrame(-2, -2.0f, 51, 10.0f, 0.0f, 10.0f, 0.0f));
            }
            long j = PeerStoriesView.this.dialogId;
            PeerStoriesView peerStoriesView3 = PeerStoriesView.this;
            if (j >= 0) {
                firstName = UserObject.getFirstName(MessagesController.getInstance(peerStoriesView3.currentAccount).getUser(Long.valueOf(PeerStoriesView.this.dialogId)));
            } else {
                TLRPC.Chat chat = MessagesController.getInstance(peerStoriesView3.currentAccount).getChat(Long.valueOf(-PeerStoriesView.this.dialogId));
                firstName = chat != null ? chat.title : _UrlKt.FRAGMENT_ENCODE_SET;
            }
            PeerStoriesView.this.mediaBanTooltip.setText(AndroidUtilities.replaceTags(LocaleController.formatString(PeerStoriesView.this.chatActivityEnterView.isInVideoMode() ? C2797R.string.VideoMessagesRestrictedByPrivacy : C2797R.string.VoiceMessagesRestrictedByPrivacy, firstName)));
            PeerStoriesView.this.mediaBanTooltip.showForView(PeerStoriesView.this.chatActivityEnterView.getAudioVideoButtonContainer(), true);
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onStickersExpandedChange() {
            PeerStoriesView.this.requestLayout();
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public TL_stories.StoryItem getReplyToStory() {
            return PeerStoriesView.this.currentStory.storyItem;
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public boolean onceVoiceAvailable() {
            TLRPC.User user;
            return (PeerStoriesView.this.dialogId < 0 || (user = MessagesController.getInstance(PeerStoriesView.this.currentAccount).getUser(Long.valueOf(PeerStoriesView.this.dialogId))) == null || UserObject.isUserSelf(user) || user.bot) ? false : true;
        }
    }

    public void onSideControlButtonOnClick(int i, View view) {
        if (i == 0) {
            openAttachMenu();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$21 */
    public class C690421 extends MentionsContainerView {
        @Override // org.telegram.p035ui.Components.MentionsContainerView
        public boolean isStories() {
            return true;
        }

        public C690421(Context context, long j, long j2, BaseFragment baseFragment, Theme.ResourcesProvider resourcesProvider) {
            super(context, j, j2, baseFragment, resourcesProvider);
        }

        @Override // org.telegram.p035ui.Components.MentionsContainerView
        public void drawRoundRect(Canvas canvas, Rect rect, float f) {
            PeerStoriesView.this.bitmapShaderTools.setBounds(getX(), -getY(), getX() + getMeasuredWidth(), (-getY()) + getMeasuredHeight());
            RectF rectF = AndroidUtilities.rectTmp;
            rectF.set(rect);
            rectF.offset(0.0f, 0.0f);
            canvas.drawRoundRect(rectF, f, f, PeerStoriesView.this.bitmapShaderTools.paint);
            canvas.drawRoundRect(rectF, f, f, PeerStoriesView.this.inputBackgroundPaint);
            if (rectF.top < getMeasuredHeight() - 1) {
                canvas.drawRect(0.0f, getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight() - 1, PeerStoriesView.this.resourcesProvider.getPaint("paintDivider"));
            }
        }
    }

    public void createMentionsContainer() {
        this.mentionContainer = new MentionsContainerView(getContext(), this.dialogId, 0L, this.storyViewer.fragment, this.resourcesProvider) { // from class: org.telegram.ui.Stories.PeerStoriesView.21
            @Override // org.telegram.p035ui.Components.MentionsContainerView
            public boolean isStories() {
                return true;
            }

            public C690421(Context context, long j, long j2, BaseFragment baseFragment, Theme.ResourcesProvider resourcesProvider) {
                super(context, j, j2, baseFragment, resourcesProvider);
            }

            @Override // org.telegram.p035ui.Components.MentionsContainerView
            public void drawRoundRect(Canvas canvas, Rect rect, float f) {
                PeerStoriesView.this.bitmapShaderTools.setBounds(getX(), -getY(), getX() + getMeasuredWidth(), (-getY()) + getMeasuredHeight());
                RectF rectF = AndroidUtilities.rectTmp;
                rectF.set(rect);
                rectF.offset(0.0f, 0.0f);
                canvas.drawRoundRect(rectF, f, f, PeerStoriesView.this.bitmapShaderTools.paint);
                canvas.drawRoundRect(rectF, f, f, PeerStoriesView.this.inputBackgroundPaint);
                if (rectF.top < getMeasuredHeight() - 1) {
                    canvas.drawRect(0.0f, getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight() - 1, PeerStoriesView.this.resourcesProvider.getPaint("paintDivider"));
                }
            }
        };
        C690522 c690522 = new C690522();
        this.mentionsDelegate = c690522;
        this.mentionContainer.withDelegate(c690522);
        addView(this.mentionContainer, LayoutHelper.createFrame(-1, -1, 83));
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$22 */
    public class C690522 implements MentionsContainerView.Delegate {
        public C690522() {
        }

        @Override // org.telegram.ui.Components.MentionsContainerView.Delegate
        public void onStickerSelected(final TLRPC.TL_document tL_document, final String str, final Object obj) {
            AlertsCreator.ensurePaidMessageConfirmation(PeerStoriesView.this.currentAccount, PeerStoriesView.this.dialogId, 1, new Utilities.Callback() { // from class: org.telegram.ui.Stories.PeerStoriesView$22$$ExternalSyntheticLambda1
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj2) {
                    this.f$0.lambda$onStickerSelected$0(tL_document, str, obj, (Long) obj2);
                }
            });
        }

        public /* synthetic */ void lambda$onStickerSelected$0(TLRPC.TL_document tL_document, String str, Object obj, Long l) {
            SendMessagesHelper.getInstance(PeerStoriesView.this.currentAccount).sendSticker(tL_document, str, PeerStoriesView.this.dialogId, null, null, PeerStoriesView.this.currentStory.storyItem, null, null, true, 0, 0, false, obj, null, 0, l.longValue(), PeerStoriesView.this.chatActivityEnterView.getSendMonoForumPeerId(), PeerStoriesView.this.chatActivityEnterView.getSendMessageSuggestionParams());
            PeerStoriesView.this.chatActivityEnterView.addStickerToRecent(tL_document);
            PeerStoriesView.this.chatActivityEnterView.setFieldText(_UrlKt.FRAGMENT_ENCODE_SET);
            PeerStoriesView.this.afterMessageSend(l.longValue() <= 0);
        }

        @Override // org.telegram.ui.Components.MentionsContainerView.Delegate
        public void replaceText(int i, int i2, CharSequence charSequence, boolean z) {
            PeerStoriesView.this.chatActivityEnterView.replaceWithText(i, i2, charSequence, z);
        }

        @Override // org.telegram.ui.Components.MentionsContainerView.Delegate
        public Paint.FontMetricsInt getFontMetrics() {
            return PeerStoriesView.this.chatActivityEnterView.getEditField().getPaint().getFontMetricsInt();
        }

        @Override // org.telegram.ui.Components.MentionsContainerView.Delegate
        public void addEmojiToRecent(String str) {
            PeerStoriesView.this.chatActivityEnterView.addEmojiToRecent(str);
        }

        @Override // org.telegram.ui.Components.MentionsContainerView.Delegate
        public void sendBotInlineResult(final TLRPC.BotInlineResult botInlineResult, final boolean z, final int i) {
            AlertsCreator.ensurePaidMessageConfirmation(PeerStoriesView.this.currentAccount, PeerStoriesView.this.dialogId, 1, new Utilities.Callback() { // from class: org.telegram.ui.Stories.PeerStoriesView$22$$ExternalSyntheticLambda0
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$sendBotInlineResult$1(botInlineResult, z, i, (Long) obj);
                }
            });
        }

        public /* synthetic */ void lambda$sendBotInlineResult$1(TLRPC.BotInlineResult botInlineResult, boolean z, int i, Long l) {
            long contextBotId = PeerStoriesView.this.mentionContainer.getAdapter().getContextBotId();
            HashMap map = new HashMap();
            map.put("id", botInlineResult.f1243id);
            map.put("query_id", _UrlKt.FRAGMENT_ENCODE_SET + botInlineResult.query_id);
            map.put("bot", _UrlKt.FRAGMENT_ENCODE_SET + contextBotId);
            map.put("bot_name", PeerStoriesView.this.mentionContainer.getAdapter().getContextBotName());
            SendMessagesHelper.prepareSendingBotContextResult(PeerStoriesView.this.storyViewer.fragment, PeerStoriesView.this.getAccountInstance(), botInlineResult, map, PeerStoriesView.this.dialogId, null, null, PeerStoriesView.this.currentStory.storyItem, null, z, i, 0, null, 0, l.longValue());
            PeerStoriesView.this.chatActivityEnterView.setFieldText(_UrlKt.FRAGMENT_ENCODE_SET);
            PeerStoriesView.this.afterMessageSend(l.longValue() <= 0);
            MediaDataController.getInstance(PeerStoriesView.this.currentAccount).increaseInlineRating(contextBotId);
        }
    }

    public boolean applyMessageToChat(final Runnable runnable) {
        if (MessagesController.getInstance(this.currentAccount).isFrozen()) {
            AccountFrozenAlert.show(this.currentAccount);
            return true;
        }
        int i = SharedConfig.stealthModeSendMessageConfirm;
        if (i > 0 && this.stealthModeIsActive) {
            int i2 = i - 1;
            SharedConfig.stealthModeSendMessageConfirm = i2;
            SharedConfig.updateStealthModeSendMessageConfirm(i2);
            AlertDialog alertDialog = new AlertDialog(getContext(), 0, this.resourcesProvider);
            alertDialog.setTitle(LocaleController.getString(C2797R.string.StealthModeConfirmTitle));
            alertDialog.setMessage(LocaleController.getString(C2797R.string.StealthModeConfirmMessage));
            alertDialog.setPositiveButton(LocaleController.getString(C2797R.string.Proceed), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda38
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog2, int i3) {
                    runnable.run();
                }
            });
            alertDialog.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda39
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog2, int i3) {
                    alertDialog2.dismiss();
                }
            });
            alertDialog.show();
        } else {
            runnable.run();
        }
        return true;
    }

    public void saveToGallery() {
        StoryItemHolder storyItemHolder = this.currentStory;
        TL_stories.StoryItem storyItem = storyItemHolder.storyItem;
        if ((storyItem == null && storyItemHolder.uploadingStory == null) || (storyItem instanceof TL_stories.TL_storyItemSkipped)) {
            return;
        }
        File path = storyItemHolder.getPath();
        final boolean zIsVideo = this.currentStory.isVideo();
        if (path != null && path.exists()) {
            MediaController.saveFile(path.toString(), getContext(), zIsVideo ? 1 : 0, null, null, new Utilities.Callback() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda59
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$saveToGallery$32(zIsVideo, (Uri) obj);
                }
            });
            return;
        }
        showDownloadAlert();
    }

    public /* synthetic */ void lambda$saveToGallery$32(boolean z, Uri uri) {
        BulletinFactory.createSaveToGalleryBulletin(this.storyContainer, z, this.resourcesProvider).show();
    }

    public void showDownloadAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), this.resourcesProvider);
        builder.setTitle(LocaleController.getString(C2797R.string.AppName));
        builder.setPositiveButton(LocaleController.getString(C2797R.string.f1162OK), null);
        builder.setMessage(LocaleController.getString(C2797R.string.PleaseDownload));
        this.delegate.showDialog(builder.create());
    }

    public long getMessageMinPrice() {
        StoryViewer storyViewer;
        if (!this.currentStory.isLive || (storyViewer = this.storyViewer) == null || storyViewer.livePlayer == null || disabledPaidFeatures(true)) {
            return 0L;
        }
        return this.storyViewer.livePlayer.getSendPaidMessagesStars();
    }

    public void openAttachMenu() {
        if (this.chatActivityEnterView == null) {
            return;
        }
        createChatAttachView();
        this.chatAttachAlert.getPhotoLayout().loadGalleryPhotos();
        this.chatAttachAlert.setMaxSelectedPhotos(-1, true);
        this.chatAttachAlert.setDialogId(this.dialogId);
        this.chatAttachAlert.init();
        this.chatAttachAlert.getCommentView().setText(this.chatActivityEnterView.getFieldText());
        this.delegate.showDialog(this.chatAttachAlert);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$23 */
    public class DialogC690623 extends ChatAttachAlert {
        public DialogC690623(Context context, BaseFragment baseFragment, boolean z, boolean z2, boolean z3, Theme.ResourcesProvider resourcesProvider) {
            super(context, baseFragment, z, z2, z3, resourcesProvider);
        }

        @Override // org.telegram.p035ui.ActionBar.BottomSheet
        public void onDismissAnimationStart() {
            if (PeerStoriesView.this.chatAttachAlert != null) {
                PeerStoriesView.this.chatAttachAlert.setFocusable(false);
            }
            ChatActivityEnterView chatActivityEnterView = PeerStoriesView.this.chatActivityEnterView;
            if (chatActivityEnterView == null || chatActivityEnterView.getEditField() == null) {
                return;
            }
            PeerStoriesView.this.chatActivityEnterView.getEditField().requestFocus();
        }
    }

    private void createChatAttachView() {
        if (this.chatAttachAlert == null) {
            DialogC690623 dialogC690623 = new ChatAttachAlert(getContext(), null, false, false, true, this.resourcesProvider) { // from class: org.telegram.ui.Stories.PeerStoriesView.23
                public DialogC690623(Context context, BaseFragment baseFragment, boolean z, boolean z2, boolean z3, Theme.ResourcesProvider resourcesProvider) {
                    super(context, baseFragment, z, z2, z3, resourcesProvider);
                }

                @Override // org.telegram.p035ui.ActionBar.BottomSheet
                public void onDismissAnimationStart() {
                    if (PeerStoriesView.this.chatAttachAlert != null) {
                        PeerStoriesView.this.chatAttachAlert.setFocusable(false);
                    }
                    ChatActivityEnterView chatActivityEnterView = PeerStoriesView.this.chatActivityEnterView;
                    if (chatActivityEnterView == null || chatActivityEnterView.getEditField() == null) {
                        return;
                    }
                    PeerStoriesView.this.chatActivityEnterView.getEditField().requestFocus();
                }
            };
            this.chatAttachAlert = dialogC690623;
            dialogC690623.setDelegate(new ChatAttachAlert.ChatAttachViewDelegate() { // from class: org.telegram.ui.Stories.PeerStoriesView.24
                public C690724() {
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // org.telegram.ui.Components.ChatAttachAlert.ChatAttachViewDelegate
                public void didPressedButton(int i, boolean z, boolean z2, int i2, int i3, long j, boolean z3, boolean z4, long j2) {
                    String str;
                    if (PeerStoriesView.this.storyViewer.isShowing) {
                        PeerStoriesView peerStoriesView = PeerStoriesView.this;
                        TL_stories.StoryItem storyItem = peerStoriesView.currentStory.storyItem;
                        if (storyItem == null || (storyItem instanceof TL_stories.TL_storyItemSkipped)) {
                            return;
                        }
                        if (i == 8 || i == 7 || (i == 4 && !peerStoriesView.chatAttachAlert.getPhotoLayout().getSelectedPhotos().isEmpty())) {
                            if (i != 8) {
                                PeerStoriesView.this.chatAttachAlert.dismiss(true);
                            }
                            HashMap<Object, Object> selectedPhotos = PeerStoriesView.this.chatAttachAlert.getPhotoLayout().getSelectedPhotos();
                            ArrayList<Object> selectedPhotosOrder = PeerStoriesView.this.chatAttachAlert.getPhotoLayout().getSelectedPhotosOrder();
                            if (selectedPhotos.isEmpty()) {
                                return;
                            }
                            int i4 = 0;
                            int i5 = 0;
                            while (i5 < Math.ceil(selectedPhotos.size() / 10.0f)) {
                                int i6 = i5 * 10;
                                int iMin = Math.min(10, selectedPhotos.size() - i6);
                                ArrayList arrayList = new ArrayList();
                                for (int i7 = i4; i7 < iMin; i7++) {
                                    int i8 = i6 + i7;
                                    if (i8 < selectedPhotosOrder.size()) {
                                        MediaController.PhotoEntry photoEntry = (MediaController.PhotoEntry) selectedPhotos.get(selectedPhotosOrder.get(i8));
                                        SendMessagesHelper.SendingMediaInfo sendingMediaInfo = new SendMessagesHelper.SendingMediaInfo();
                                        boolean z5 = photoEntry.isVideo;
                                        if (!z5 && (str = photoEntry.imagePath) != null) {
                                            sendingMediaInfo.path = str;
                                        } else {
                                            String str2 = photoEntry.path;
                                            if (str2 != null) {
                                                sendingMediaInfo.path = str2;
                                            }
                                        }
                                        sendingMediaInfo.thumbPath = photoEntry.thumbPath;
                                        sendingMediaInfo.coverPath = photoEntry.coverPath;
                                        sendingMediaInfo.isVideo = z5;
                                        CharSequence charSequence = photoEntry.caption;
                                        sendingMediaInfo.caption = charSequence != null ? charSequence.toString() : null;
                                        sendingMediaInfo.entities = photoEntry.entities;
                                        sendingMediaInfo.masks = photoEntry.stickers;
                                        sendingMediaInfo.ttl = photoEntry.ttl;
                                        sendingMediaInfo.videoEditedInfo = photoEntry.editedInfo;
                                        sendingMediaInfo.canDeleteAfter = photoEntry.canDeleteAfter;
                                        sendingMediaInfo.updateStickersOrder = SendMessagesHelper.checkUpdateStickersOrder(photoEntry.caption);
                                        sendingMediaInfo.hasMediaSpoilers = photoEntry.hasSpoiler;
                                        arrayList.add(sendingMediaInfo);
                                        photoEntry.reset();
                                    }
                                }
                                SendMessagesHelper.prepareSendingMedia(PeerStoriesView.this.getAccountInstance(), arrayList, PeerStoriesView.this.dialogId, null, null, storyItem, null, (i == 4 || z4) ? 1 : i4, z, null, z2, i2, i3, 0, i5 == 0 ? ((SendMessagesHelper.SendingMediaInfo) arrayList.get(i4)).updateStickersOrder : i4, null, null, 0, 0L, false, 0L, PeerStoriesView.this.chatActivityEnterView.getSendMonoForumPeerId(), PeerStoriesView.this.chatActivityEnterView.getSendMessageSuggestionParams());
                                i5++;
                                selectedPhotos = selectedPhotos;
                                selectedPhotosOrder = selectedPhotosOrder;
                                i4 = i4;
                            }
                            int i9 = i4;
                            PeerStoriesView.this.chatActivityEnterView.setFieldText(_UrlKt.FRAGMENT_ENCODE_SET);
                            PeerStoriesView.this.afterMessageSend(j2 <= 0 ? 1 : i9);
                            return;
                        }
                        if (PeerStoriesView.this.chatAttachAlert != null) {
                            PeerStoriesView.this.chatAttachAlert.dismissWithButtonClick(i);
                        }
                    }
                }

                @Override // org.telegram.ui.Components.ChatAttachAlert.ChatAttachViewDelegate
                public void onCameraOpened() {
                    PeerStoriesView.this.chatActivityEnterView.closeKeyboard();
                }

                @Override // org.telegram.ui.Components.ChatAttachAlert.ChatAttachViewDelegate
                public void doOnIdle(Runnable runnable) {
                    NotificationCenter.getInstance(PeerStoriesView.this.currentAccount).doOnIdle(runnable);
                }

                @Override // org.telegram.ui.Components.ChatAttachAlert.ChatAttachViewDelegate
                public void sendAudio(ArrayList<MessageObject> arrayList, CharSequence charSequence, boolean z, int i, int i2, long j, boolean z2, long j2) {
                    PeerStoriesView peerStoriesView = PeerStoriesView.this;
                    TL_stories.StoryItem storyItem = peerStoriesView.currentStory.storyItem;
                    if (storyItem == null || (storyItem instanceof TL_stories.TL_storyItemSkipped)) {
                        return;
                    }
                    SendMessagesHelper.prepareSendingAudioDocuments(peerStoriesView.getAccountInstance(), arrayList, charSequence != null ? charSequence : null, PeerStoriesView.this.dialogId, null, null, storyItem, z, i, i2, null, null, 0, j, z2, j2);
                    PeerStoriesView.this.afterMessageSend(j2 <= 0);
                }

                @Override // org.telegram.ui.Components.ChatAttachAlert.ChatAttachViewDelegate
                public boolean needEnterComment() {
                    return PeerStoriesView.this.needEnterText();
                }
            });
            this.chatAttachAlert.getPhotoLayout().loadGalleryPhotos();
            this.chatAttachAlert.setAllowEnterCaption(true);
            this.chatAttachAlert.init();
            this.chatAttachAlert.setDocumentsDelegate(new ChatAttachAlertDocumentLayout.DocumentSelectActivityDelegate() { // from class: org.telegram.ui.Stories.PeerStoriesView.25
                public C690825() {
                }

                @Override // org.telegram.ui.Components.ChatAttachAlertDocumentLayout.DocumentSelectActivityDelegate
                public void didSelectFiles(ArrayList<String> arrayList, String str, ArrayList<TLRPC.MessageEntity> arrayList2, ArrayList<MessageObject> arrayList3, boolean z, int i, int i2, long j, boolean z2, long j2) {
                    PeerStoriesView peerStoriesView = PeerStoriesView.this;
                    TL_stories.StoryItem storyItem = peerStoriesView.currentStory.storyItem;
                    if (storyItem == null || (storyItem instanceof TL_stories.TL_storyItemSkipped)) {
                        return;
                    }
                    SendMessagesHelper.prepareSendingDocuments(peerStoriesView.getAccountInstance(), arrayList, arrayList, (ArrayList<Uri>) null, str, (String) null, PeerStoriesView.this.dialogId, (MessageObject) null, (MessageObject) null, storyItem, (ChatActivity.ReplyQuote) null, (MessageObject) null, z, i, (InputContentInfoCompat) null, (String) null, 0, 0L, false, j2);
                    PeerStoriesView.this.afterMessageSend(j2 <= 0);
                }

                @Override // org.telegram.ui.Components.ChatAttachAlertDocumentLayout.DocumentSelectActivityDelegate
                public void startDocumentSelectActivity() {
                    try {
                        Intent intent = new Intent("android.intent.action.GET_CONTENT");
                        intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", true);
                        intent.setType("*/*");
                        PeerStoriesView.this.storyViewer.startActivityForResult(intent, 21);
                    } catch (Exception e) {
                        FileLog.m1048e(e);
                    }
                }
            });
            this.chatAttachAlert.getCommentView().setText(this.chatActivityEnterView.getFieldText());
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$24 */
    public class C690724 implements ChatAttachAlert.ChatAttachViewDelegate {
        public C690724() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.telegram.ui.Components.ChatAttachAlert.ChatAttachViewDelegate
        public void didPressedButton(int i, boolean z, boolean z2, int i2, int i3, long j, boolean z3, boolean z4, long j2) {
            String str;
            if (PeerStoriesView.this.storyViewer.isShowing) {
                PeerStoriesView peerStoriesView = PeerStoriesView.this;
                TL_stories.StoryItem storyItem = peerStoriesView.currentStory.storyItem;
                if (storyItem == null || (storyItem instanceof TL_stories.TL_storyItemSkipped)) {
                    return;
                }
                if (i == 8 || i == 7 || (i == 4 && !peerStoriesView.chatAttachAlert.getPhotoLayout().getSelectedPhotos().isEmpty())) {
                    if (i != 8) {
                        PeerStoriesView.this.chatAttachAlert.dismiss(true);
                    }
                    HashMap<Object, Object> selectedPhotos = PeerStoriesView.this.chatAttachAlert.getPhotoLayout().getSelectedPhotos();
                    ArrayList<Object> selectedPhotosOrder = PeerStoriesView.this.chatAttachAlert.getPhotoLayout().getSelectedPhotosOrder();
                    if (selectedPhotos.isEmpty()) {
                        return;
                    }
                    int i4 = 0;
                    int i5 = 0;
                    while (i5 < Math.ceil(selectedPhotos.size() / 10.0f)) {
                        int i6 = i5 * 10;
                        int iMin = Math.min(10, selectedPhotos.size() - i6);
                        ArrayList arrayList = new ArrayList();
                        for (int i7 = i4; i7 < iMin; i7++) {
                            int i8 = i6 + i7;
                            if (i8 < selectedPhotosOrder.size()) {
                                MediaController.PhotoEntry photoEntry = (MediaController.PhotoEntry) selectedPhotos.get(selectedPhotosOrder.get(i8));
                                SendMessagesHelper.SendingMediaInfo sendingMediaInfo = new SendMessagesHelper.SendingMediaInfo();
                                boolean z5 = photoEntry.isVideo;
                                if (!z5 && (str = photoEntry.imagePath) != null) {
                                    sendingMediaInfo.path = str;
                                } else {
                                    String str2 = photoEntry.path;
                                    if (str2 != null) {
                                        sendingMediaInfo.path = str2;
                                    }
                                }
                                sendingMediaInfo.thumbPath = photoEntry.thumbPath;
                                sendingMediaInfo.coverPath = photoEntry.coverPath;
                                sendingMediaInfo.isVideo = z5;
                                CharSequence charSequence = photoEntry.caption;
                                sendingMediaInfo.caption = charSequence != null ? charSequence.toString() : null;
                                sendingMediaInfo.entities = photoEntry.entities;
                                sendingMediaInfo.masks = photoEntry.stickers;
                                sendingMediaInfo.ttl = photoEntry.ttl;
                                sendingMediaInfo.videoEditedInfo = photoEntry.editedInfo;
                                sendingMediaInfo.canDeleteAfter = photoEntry.canDeleteAfter;
                                sendingMediaInfo.updateStickersOrder = SendMessagesHelper.checkUpdateStickersOrder(photoEntry.caption);
                                sendingMediaInfo.hasMediaSpoilers = photoEntry.hasSpoiler;
                                arrayList.add(sendingMediaInfo);
                                photoEntry.reset();
                            }
                        }
                        SendMessagesHelper.prepareSendingMedia(PeerStoriesView.this.getAccountInstance(), arrayList, PeerStoriesView.this.dialogId, null, null, storyItem, null, (i == 4 || z4) ? 1 : i4, z, null, z2, i2, i3, 0, i5 == 0 ? ((SendMessagesHelper.SendingMediaInfo) arrayList.get(i4)).updateStickersOrder : i4, null, null, 0, 0L, false, 0L, PeerStoriesView.this.chatActivityEnterView.getSendMonoForumPeerId(), PeerStoriesView.this.chatActivityEnterView.getSendMessageSuggestionParams());
                        i5++;
                        selectedPhotos = selectedPhotos;
                        selectedPhotosOrder = selectedPhotosOrder;
                        i4 = i4;
                    }
                    int i9 = i4;
                    PeerStoriesView.this.chatActivityEnterView.setFieldText(_UrlKt.FRAGMENT_ENCODE_SET);
                    PeerStoriesView.this.afterMessageSend(j2 <= 0 ? 1 : i9);
                    return;
                }
                if (PeerStoriesView.this.chatAttachAlert != null) {
                    PeerStoriesView.this.chatAttachAlert.dismissWithButtonClick(i);
                }
            }
        }

        @Override // org.telegram.ui.Components.ChatAttachAlert.ChatAttachViewDelegate
        public void onCameraOpened() {
            PeerStoriesView.this.chatActivityEnterView.closeKeyboard();
        }

        @Override // org.telegram.ui.Components.ChatAttachAlert.ChatAttachViewDelegate
        public void doOnIdle(Runnable runnable) {
            NotificationCenter.getInstance(PeerStoriesView.this.currentAccount).doOnIdle(runnable);
        }

        @Override // org.telegram.ui.Components.ChatAttachAlert.ChatAttachViewDelegate
        public void sendAudio(ArrayList<MessageObject> arrayList, CharSequence charSequence, boolean z, int i, int i2, long j, boolean z2, long j2) {
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            TL_stories.StoryItem storyItem = peerStoriesView.currentStory.storyItem;
            if (storyItem == null || (storyItem instanceof TL_stories.TL_storyItemSkipped)) {
                return;
            }
            SendMessagesHelper.prepareSendingAudioDocuments(peerStoriesView.getAccountInstance(), arrayList, charSequence != null ? charSequence : null, PeerStoriesView.this.dialogId, null, null, storyItem, z, i, i2, null, null, 0, j, z2, j2);
            PeerStoriesView.this.afterMessageSend(j2 <= 0);
        }

        @Override // org.telegram.ui.Components.ChatAttachAlert.ChatAttachViewDelegate
        public boolean needEnterComment() {
            return PeerStoriesView.this.needEnterText();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$25 */
    public class C690825 implements ChatAttachAlertDocumentLayout.DocumentSelectActivityDelegate {
        public C690825() {
        }

        @Override // org.telegram.ui.Components.ChatAttachAlertDocumentLayout.DocumentSelectActivityDelegate
        public void didSelectFiles(ArrayList<String> arrayList, String str, ArrayList<TLRPC.MessageEntity> arrayList2, ArrayList<MessageObject> arrayList3, boolean z, int i, int i2, long j, boolean z2, long j2) {
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            TL_stories.StoryItem storyItem = peerStoriesView.currentStory.storyItem;
            if (storyItem == null || (storyItem instanceof TL_stories.TL_storyItemSkipped)) {
                return;
            }
            SendMessagesHelper.prepareSendingDocuments(peerStoriesView.getAccountInstance(), arrayList, arrayList, (ArrayList<Uri>) null, str, (String) null, PeerStoriesView.this.dialogId, (MessageObject) null, (MessageObject) null, storyItem, (ChatActivity.ReplyQuote) null, (MessageObject) null, z, i, (InputContentInfoCompat) null, (String) null, 0, 0L, false, j2);
            PeerStoriesView.this.afterMessageSend(j2 <= 0);
        }

        @Override // org.telegram.ui.Components.ChatAttachAlertDocumentLayout.DocumentSelectActivityDelegate
        public void startDocumentSelectActivity() {
            try {
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", true);
                intent.setType("*/*");
                PeerStoriesView.this.storyViewer.startActivityForResult(intent, 21);
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
        }
    }

    public void tryToOpenRepostStory() {
        if (MessagesController.getInstance(this.currentAccount).storiesEnabled()) {
            File path = this.currentStory.getPath();
            if (path != null && path.exists()) {
                ShareAlert shareAlert = this.shareAlert;
                if (shareAlert != null) {
                    shareAlert.lambda$new$0();
                }
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda29
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.openRepostStory();
                    }
                }, 120L);
                return;
            }
            showDownloadAlert();
        }
    }

    public void shareStory(boolean z) {
        StoryItemHolder storyItemHolder = this.currentStory;
        if (storyItemHolder.storyItem == null || this.storyViewer.fragment == null) {
            return;
        }
        String strCreateLink = storyItemHolder.createLink();
        if (z) {
            DialogC691027 dialogC691027 = new ShareAlert(this.storyViewer.fragment.getContext(), null, null, null, strCreateLink, null, false, strCreateLink, null, false, false, !DISABLE_STORY_REPOSTING && MessagesController.getInstance(this.currentAccount).storiesEnabled() && (!(this.isChannel || UserObject.isService(this.dialogId)) || ChatObject.isPublic(this.isChannel ? MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-this.dialogId)) : null)), null, new WrappedResourceProvider(this.resourcesProvider) { // from class: org.telegram.ui.Stories.PeerStoriesView.26
                public C690926(Theme.ResourcesProvider resourcesProvider) {
                    super(resourcesProvider);
                }

                @Override // org.telegram.p035ui.WrappedResourceProvider
                public void appendColors() {
                    this.sparseIntArray.put(Theme.key_chat_emojiPanelBackground, ColorUtils.blendARGB(-16777216, -1, 0.2f));
                    this.sparseIntArray.put(Theme.key_chat_messagePanelIcons, ColorUtils.blendARGB(-16777216, -1, 0.5f));
                }
            }) { // from class: org.telegram.ui.Stories.PeerStoriesView.27
                public DialogC691027(Context context, ChatActivity chatActivity, ArrayList arrayList, String str, String strCreateLink2, String str2, boolean z2, String strCreateLink22, String str3, boolean z3, boolean z4, boolean z5, Integer num, Theme.ResourcesProvider resourcesProvider) {
                    super(context, chatActivity, arrayList, str, strCreateLink22, str2, z2, strCreateLink22, str3, z3, z4, z5, num, resourcesProvider);
                }

                @Override // org.telegram.p035ui.Components.ShareAlert, org.telegram.p035ui.ActionBar.BottomSheet
                public void dismissInternal() {
                    super.dismissInternal();
                    PeerStoriesView.this.shareAlert = null;
                }

                @Override // org.telegram.p035ui.Components.ShareAlert
                public void onShareStory(View view) {
                    PeerStoriesView.this.tryToOpenRepostStory();
                }

                @Override // org.telegram.p035ui.Components.ShareAlert
                public void onSend(LongSparseArray<TLRPC.Dialog> longSparseArray, int i, TLRPC.TL_forumTopic tL_forumTopic, boolean z2) {
                    if (z2) {
                        super.onSend(longSparseArray, i, tL_forumTopic, z2);
                        BulletinFactory bulletinFactoryM1142of = BulletinFactory.m1142of(PeerStoriesView.this.storyContainer, this.resourcesProvider);
                        if (bulletinFactoryM1142of != null) {
                            if (longSparseArray.size() == 1) {
                                long jKeyAt = longSparseArray.keyAt(0);
                                if (jKeyAt == UserConfig.getInstance(this.currentAccount).clientUserId) {
                                    bulletinFactoryM1142of.createSimpleBulletin(C2797R.raw.saved_messages, AndroidUtilities.replaceTags(LocaleController.formatString(C2797R.string.StorySharedToSavedMessages, new Object[0])), 5000).hideAfterBottomSheet(false).show();
                                } else {
                                    int i2 = this.currentAccount;
                                    if (jKeyAt < 0) {
                                        bulletinFactoryM1142of.createSimpleBulletin(C2797R.raw.forward, AndroidUtilities.replaceTags(LocaleController.formatString(C2797R.string.StorySharedTo, tL_forumTopic != null ? tL_forumTopic.title : MessagesController.getInstance(i2).getChat(Long.valueOf(-jKeyAt)).title)), 5000).hideAfterBottomSheet(false).show();
                                    } else {
                                        bulletinFactoryM1142of.createSimpleBulletin(C2797R.raw.forward, AndroidUtilities.replaceTags(LocaleController.formatString(C2797R.string.StorySharedTo, MessagesController.getInstance(i2).getUser(Long.valueOf(jKeyAt)).first_name)), 5000).hideAfterBottomSheet(false).show();
                                    }
                                }
                            } else {
                                bulletinFactoryM1142of.createSimpleBulletin(C2797R.raw.forward, AndroidUtilities.replaceTags(LocaleController.formatPluralString("StorySharedToManyChats", longSparseArray.size(), Integer.valueOf(longSparseArray.size())))).hideAfterBottomSheet(false).show();
                            }
                            try {
                                PeerStoriesView.this.performHapticFeedback(3);
                            } catch (Exception unused) {
                            }
                        }
                    }
                }
            };
            this.shareAlert = dialogC691027;
            dialogC691027.forceDarkThemeForHint = true;
            TL_stories.StoryItem storyItem = this.currentStory.storyItem;
            storyItem.dialogId = this.dialogId;
            dialogC691027.setStoryToShare(storyItem);
            this.shareAlert.setDelegate(new ShareAlert.ShareAlertDelegate() { // from class: org.telegram.ui.Stories.PeerStoriesView.28
                public C691128() {
                }

                @Override // org.telegram.ui.Components.ShareAlert.ShareAlertDelegate
                public boolean didCopy() {
                    PeerStoriesView.this.onLinkCopied();
                    return true;
                }
            });
            this.delegate.showDialog(this.shareAlert);
            return;
        }
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.TEXT", strCreateLink22);
        LaunchActivity.instance.startActivityForResult(Intent.createChooser(intent, LocaleController.getString(C2797R.string.StickersShare)), 500);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$26 */
    public class C690926 extends WrappedResourceProvider {
        public C690926(Theme.ResourcesProvider resourcesProvider) {
            super(resourcesProvider);
        }

        @Override // org.telegram.p035ui.WrappedResourceProvider
        public void appendColors() {
            this.sparseIntArray.put(Theme.key_chat_emojiPanelBackground, ColorUtils.blendARGB(-16777216, -1, 0.2f));
            this.sparseIntArray.put(Theme.key_chat_messagePanelIcons, ColorUtils.blendARGB(-16777216, -1, 0.5f));
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$27 */
    public class DialogC691027 extends ShareAlert {
        public DialogC691027(Context context, ChatActivity chatActivity, ArrayList arrayList, String str, String strCreateLink22, String str2, boolean z2, String strCreateLink222, String str3, boolean z3, boolean z4, boolean z5, Integer num, Theme.ResourcesProvider resourcesProvider) {
            super(context, chatActivity, arrayList, str, strCreateLink222, str2, z2, strCreateLink222, str3, z3, z4, z5, num, resourcesProvider);
        }

        @Override // org.telegram.p035ui.Components.ShareAlert, org.telegram.p035ui.ActionBar.BottomSheet
        public void dismissInternal() {
            super.dismissInternal();
            PeerStoriesView.this.shareAlert = null;
        }

        @Override // org.telegram.p035ui.Components.ShareAlert
        public void onShareStory(View view) {
            PeerStoriesView.this.tryToOpenRepostStory();
        }

        @Override // org.telegram.p035ui.Components.ShareAlert
        public void onSend(LongSparseArray<TLRPC.Dialog> longSparseArray, int i, TLRPC.TL_forumTopic tL_forumTopic, boolean z2) {
            if (z2) {
                super.onSend(longSparseArray, i, tL_forumTopic, z2);
                BulletinFactory bulletinFactoryM1142of = BulletinFactory.m1142of(PeerStoriesView.this.storyContainer, this.resourcesProvider);
                if (bulletinFactoryM1142of != null) {
                    if (longSparseArray.size() == 1) {
                        long jKeyAt = longSparseArray.keyAt(0);
                        if (jKeyAt == UserConfig.getInstance(this.currentAccount).clientUserId) {
                            bulletinFactoryM1142of.createSimpleBulletin(C2797R.raw.saved_messages, AndroidUtilities.replaceTags(LocaleController.formatString(C2797R.string.StorySharedToSavedMessages, new Object[0])), 5000).hideAfterBottomSheet(false).show();
                        } else {
                            int i2 = this.currentAccount;
                            if (jKeyAt < 0) {
                                bulletinFactoryM1142of.createSimpleBulletin(C2797R.raw.forward, AndroidUtilities.replaceTags(LocaleController.formatString(C2797R.string.StorySharedTo, tL_forumTopic != null ? tL_forumTopic.title : MessagesController.getInstance(i2).getChat(Long.valueOf(-jKeyAt)).title)), 5000).hideAfterBottomSheet(false).show();
                            } else {
                                bulletinFactoryM1142of.createSimpleBulletin(C2797R.raw.forward, AndroidUtilities.replaceTags(LocaleController.formatString(C2797R.string.StorySharedTo, MessagesController.getInstance(i2).getUser(Long.valueOf(jKeyAt)).first_name)), 5000).hideAfterBottomSheet(false).show();
                            }
                        }
                    } else {
                        bulletinFactoryM1142of.createSimpleBulletin(C2797R.raw.forward, AndroidUtilities.replaceTags(LocaleController.formatPluralString("StorySharedToManyChats", longSparseArray.size(), Integer.valueOf(longSparseArray.size())))).hideAfterBottomSheet(false).show();
                    }
                    try {
                        PeerStoriesView.this.performHapticFeedback(3);
                    } catch (Exception unused) {
                    }
                }
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$28 */
    public class C691128 implements ShareAlert.ShareAlertDelegate {
        public C691128() {
        }

        @Override // org.telegram.ui.Components.ShareAlert.ShareAlertDelegate
        public boolean didCopy() {
            PeerStoriesView.this.onLinkCopied();
            return true;
        }
    }

    public void openRepostStory() {
        final Activity activityFindActivity = AndroidUtilities.findActivity(getContext());
        if (activityFindActivity == null) {
            return;
        }
        Runnable runnable = new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda41
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$openRepostStory$38(activityFindActivity);
            }
        };
        if (this.delegate.releasePlayer(runnable)) {
            return;
        }
        AndroidUtilities.runOnUIThread(runnable, 80L);
    }

    public /* synthetic */ void lambda$openRepostStory$38(Activity activity) {
        StoryViewer.VideoPlayerHolder videoPlayerHolder;
        final StoryRecorder storyRecorder = StoryRecorder.getInstance(activity, this.currentAccount);
        VideoPlayerSharedScope videoPlayerSharedScope = this.playerSharedScope;
        storyRecorder.openForward(StoryRecorder.SourceView.fromStoryViewer(this.storyViewer), StoryEntry.repostStoryItem(this.currentStory.getPath(), this.currentStory.storyItem), (videoPlayerSharedScope == null || (videoPlayerHolder = videoPlayerSharedScope.player) == null) ? 0L : videoPlayerHolder.currentPosition, true);
        storyRecorder.setOnFullyOpenListener(new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda45
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$openRepostStory$33();
            }
        });
        storyRecorder.setOnPrepareCloseListener(new Utilities.Callback4() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda46
            @Override // org.telegram.messenger.Utilities.Callback4
            public final void run(Object obj, Object obj2, Object obj3, Object obj4) {
                this.f$0.lambda$openRepostStory$37(storyRecorder, (Long) obj, (Runnable) obj2, (Boolean) obj3, (Long) obj4);
            }
        });
    }

    public /* synthetic */ void lambda$openRepostStory$33() {
        this.editOpened = true;
        setActive(false);
    }

    public /* synthetic */ void lambda$openRepostStory$37(final StoryRecorder storyRecorder, Long l, final Runnable runnable, Boolean bool, final Long l2) {
        long j;
        final DialogStoriesCell dialogStoriesCell;
        INavigationLayout parentLayout;
        if (bool.booleanValue()) {
            BaseFragment baseFragment = this.storyViewer.fragment;
            if (baseFragment == null || (parentLayout = baseFragment.getParentLayout()) == null) {
                j = 400;
                dialogStoriesCell = null;
            } else {
                List<BaseFragment> fragmentStack = parentLayout.getFragmentStack();
                ArrayList arrayList = new ArrayList();
                for (int size = fragmentStack.size() - 1; size >= 0; size--) {
                    BaseFragment baseFragment2 = fragmentStack.get(size);
                    if (baseFragment2 instanceof DialogsActivity) {
                        DialogsActivity dialogsActivity = (DialogsActivity) baseFragment2;
                        dialogsActivity.closeSearching();
                        DialogStoriesCell dialogStoriesCell2 = dialogsActivity.dialogStoriesCell;
                        storyCellFindStoryCell = dialogStoriesCell2 != null ? dialogStoriesCell2.findStoryCell(l2.longValue()) : null;
                        for (int i = 0; i < arrayList.size(); i++) {
                            parentLayout.removeFragmentFromStack((BaseFragment) arrayList.get(i));
                        }
                        j = 400;
                        dialogStoriesCell = dialogStoriesCell2;
                    } else {
                        arrayList.add(baseFragment2);
                    }
                }
                j = 400;
                dialogStoriesCell = null;
            }
            BaseFragment baseFragment3 = this.storyViewer.fragment;
            if (baseFragment3 != null) {
                baseFragment3.clearSheets();
            }
            this.storyViewer.instantClose();
            this.editOpened = false;
            if (dialogStoriesCell != null && dialogStoriesCell.scrollTo(l2.longValue())) {
                final DialogStoriesCell.StoryCell storyCell = storyCellFindStoryCell;
                dialogStoriesCell.afterNextLayout(new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda48
                    @Override // java.lang.Runnable
                    public final void run() {
                        PeerStoriesView.$r8$lambda$uMZr2oFJQo_6IPHQMyJ80NyBLRA(storyCell, dialogStoriesCell, l2, storyRecorder, runnable);
                    }
                });
                return;
            } else {
                storyRecorder.replaceSourceView(StoryRecorder.SourceView.fromStoryCell(storyCellFindStoryCell));
                AndroidUtilities.runOnUIThread(runnable, j);
                return;
            }
        }
        final long jCurrentTimeMillis = System.currentTimeMillis();
        VideoPlayerSharedScope videoPlayerSharedScope = this.playerSharedScope;
        if (videoPlayerSharedScope != null && videoPlayerSharedScope.player == null) {
            this.delegate.setPopupIsVisible(false);
            setActive(true);
            this.editOpened = false;
            this.onImageReceiverThumbLoaded = new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda49
                @Override // java.lang.Runnable
                public final void run() {
                    PeerStoriesView.m20560$r8$lambda$dGoH3A6sklctbRIH2lSj7RSHyg(runnable);
                }
            };
            if (bool.booleanValue()) {
                updatePosition();
            }
            AndroidUtilities.runOnUIThread(runnable, 400L);
            return;
        }
        StoryViewer.VideoPlayerHolder videoPlayerHolder = videoPlayerSharedScope.player;
        videoPlayerHolder.firstFrameRendered = false;
        videoPlayerSharedScope.firstFrameRendered = false;
        videoPlayerHolder.setOnReadyListener(new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda50
            @Override // java.lang.Runnable
            public final void run() {
                PeerStoriesView.m20551$r8$lambda$5LKFz3khgZaUzGgbmsjLCOKKIU(runnable, jCurrentTimeMillis);
            }
        });
        this.delegate.setPopupIsVisible(false);
        RLottieImageView rLottieImageView = this.muteIconView;
        if (rLottieImageView != null) {
            rLottieImageView.setAnimation(this.sharedResources.muteDrawable);
        }
        setActive(((this.videoDuration <= 0 || l.longValue() <= this.videoDuration - 1400) ? l : 0L).longValue(), true);
        this.editOpened = false;
        AndroidUtilities.runOnUIThread(runnable, 400L);
        if (bool.booleanValue()) {
            updatePosition();
        }
    }

    public static /* synthetic */ void $r8$lambda$uMZr2oFJQo_6IPHQMyJ80NyBLRA(DialogStoriesCell.StoryCell storyCell, DialogStoriesCell dialogStoriesCell, Long l, StoryRecorder storyRecorder, Runnable runnable) {
        if (storyCell == null) {
            storyCell = dialogStoriesCell.findStoryCell(l.longValue());
        }
        storyRecorder.replaceSourceView(StoryRecorder.SourceView.fromStoryCell(storyCell));
        runnable.run();
    }

    /* JADX INFO: renamed from: $r8$lambda$dGoH3A6sk-lctbRIH2lSj7RSHyg */
    public static /* synthetic */ void m20560$r8$lambda$dGoH3A6sklctbRIH2lSj7RSHyg(Runnable runnable) {
        AndroidUtilities.cancelRunOnUIThread(runnable);
        AndroidUtilities.runOnUIThread(runnable);
    }

    /* JADX INFO: renamed from: $r8$lambda$5LKF-z3khgZaUzGgbmsjLCOKKIU */
    public static /* synthetic */ void m20551$r8$lambda$5LKFz3khgZaUzGgbmsjLCOKKIU(Runnable runnable, long j) {
        AndroidUtilities.cancelRunOnUIThread(runnable);
        AndroidUtilities.runOnUIThread(runnable, Math.max(0L, 32 - (System.currentTimeMillis() - j)));
    }

    public void onLinkCopied() {
        if (this.currentStory.storyItem == null) {
            return;
        }
        TL_stories.TL_stories_exportStoryLink tL_stories_exportStoryLink = new TL_stories.TL_stories_exportStoryLink();
        tL_stories_exportStoryLink.f1459id = this.currentStory.storyItem.f1454id;
        tL_stories_exportStoryLink.peer = MessagesController.getInstance(this.currentAccount).getInputPeer(this.dialogId);
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(tL_stories_exportStoryLink, new RequestDelegate() { // from class: org.telegram.ui.Stories.PeerStoriesView.29
            @Override // org.telegram.tgnet.RequestDelegate
            public void run(TLObject tLObject, TLRPC.TL_error tL_error) {
            }

            public C691229() {
            }
        });
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$29 */
    public class C691229 implements RequestDelegate {
        @Override // org.telegram.tgnet.RequestDelegate
        public void run(TLObject tLObject, TLRPC.TL_error tL_error) {
        }

        public C691229() {
        }
    }

    public void setDay(long j, ArrayList<Integer> arrayList, int i) {
        this.dialogId = j;
        this.day = arrayList;
        bindInternal(i);
    }

    public void setDialogId(long j, int i) {
        if (this.dialogId != j) {
            this.currentStory.clear();
        }
        this.dialogId = j;
        this.day = null;
        bindInternal(i);
        TL_stories.PeerStories peerStories = this.storyViewer.overrideUserStories;
        StoriesController storiesController = this.storiesController;
        if (peerStories != null) {
            storiesController.loadSkippedStories(peerStories, true);
        } else {
            storiesController.loadSkippedStories(j);
        }
    }

    private void setTitle(boolean z, long j, boolean z2) {
        if (!z && j == this.titleLastDialogId && this.titleLastLive == z2) {
            return;
        }
        this.titleLastDialogId = j;
        this.titleLastLive = z2;
        if (j >= 0) {
            if (this.isSelf && !z2) {
                this.headerView.titleView.setText(LocaleController.getString(C2797R.string.SelfStoryTitle));
                this.headerView.titleView.setRightDrawable((Drawable) null);
                return;
            }
            TLRPC.User user = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(j));
            if (user != null && user.verified) {
                Drawable drawableMutate = ContextCompat.getDrawable(getContext(), C2797R.drawable.verified_profile).mutate();
                drawableMutate.setAlpha(255);
                CombinedDrawable combinedDrawable = new CombinedDrawable(drawableMutate, null);
                combinedDrawable.setFullsize(true);
                combinedDrawable.setCustomSize(AndroidUtilities.m1036dp(16.0f), AndroidUtilities.m1036dp(16.0f));
                this.headerView.titleView.setRightDrawable(combinedDrawable);
            } else {
                this.headerView.titleView.setRightDrawable((Drawable) null);
            }
            if (user != null) {
                this.headerView.titleView.setText(Emoji.replaceEmoji(AndroidUtilities.removeDiacritics(ContactsController.formatName(user)), this.headerView.titleView.getPaint().getFontMetricsInt(), false));
                return;
            } else {
                this.headerView.titleView.setText(null);
                return;
            }
        }
        TLRPC.Chat chat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-j));
        this.headerView.titleView.setText(AndroidUtilities.removeDiacritics(chat == null ? _UrlKt.FRAGMENT_ENCODE_SET : chat.title));
        if (chat != null && chat.verified) {
            Drawable drawableMutate2 = ContextCompat.getDrawable(getContext(), C2797R.drawable.verified_profile).mutate();
            drawableMutate2.setAlpha(255);
            CombinedDrawable combinedDrawable2 = new CombinedDrawable(drawableMutate2, null);
            combinedDrawable2.setFullsize(true);
            combinedDrawable2.setCustomSize(AndroidUtilities.m1036dp(16.0f), AndroidUtilities.m1036dp(16.0f));
            this.headerView.titleView.setRightDrawable(combinedDrawable2);
            return;
        }
        this.headerView.titleView.setRightDrawable((Drawable) null);
    }

    private void bindInternal(int i) {
        this.deletedPeer = false;
        this.forceUpdateOffsets = true;
        this.userCanSeeViews = false;
        this.isChannel = false;
        this.isGroup = false;
        long j = this.dialogId;
        if (j >= 0) {
            this.isSelf = j == UserConfig.getInstance(this.currentAccount).getClientUserId();
            TLRPC.User user = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(this.dialogId));
            TL_account.RequirementToContact requirementToContactIsUserContactBlocked = MessagesController.getInstance(this.currentAccount).isUserContactBlocked(this.dialogId);
            this.isPremiumBlocked = !UserConfig.getInstance(this.currentAccount).isPremium() && DialogObject.isPremiumBlocked(requirementToContactIsUserContactBlocked);
            this.starsPriceBlocked = DialogObject.getMessagesStarsPrice(requirementToContactIsUserContactBlocked);
            this.avatarDrawable.setInfo(this.currentAccount, user);
            this.headerView.backupImageView.getImageReceiver().setForUserOrChat(user, this.avatarDrawable);
            setTitle(true, this.dialogId, false);
        } else {
            this.isSelf = false;
            this.isChannel = true;
            if (this.storiesController.canEditStories(j) || BuildVars.DEBUG_PRIVATE_VERSION) {
                this.userCanSeeViews = true;
            }
            TLRPC.Chat chat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-this.dialogId));
            boolean zIsChannelAndNotMegaGroup = ChatObject.isChannelAndNotMegaGroup(chat);
            this.isGroup = !zIsChannelAndNotMegaGroup;
            if (!zIsChannelAndNotMegaGroup && MessagesController.getInstance(this.currentAccount).getChatFull(-this.dialogId) == null) {
                MessagesStorage.getInstance(this.currentAccount).loadChatInfo(-this.dialogId, true, new CountDownLatch(1), false, false);
            }
            this.isPremiumBlocked = this.isGroup && !ChatObject.canSendPlain(chat);
            this.starsPriceBlocked = MessagesController.getInstance(this.currentAccount).getSendPaidMessagesStars(this.dialogId);
            this.avatarDrawable.setInfo(this.currentAccount, chat);
            this.headerView.backupImageView.getImageReceiver().setForUserOrChat(chat, this.avatarDrawable);
            setTitle(true, this.dialogId, false);
        }
        if (this.isActive && (this.isSelf || this.isChannel)) {
            this.storiesController.pollViewsForSelfStories(this.dialogId, true);
        }
        updateStoryItems();
        this.selectedPosition = i;
        if (i < 0) {
            this.selectedPosition = 0;
        }
        this.currentImageTime = 0L;
        this.switchEventSent = false;
        this.boostsStatus = null;
        this.canApplyBoost = null;
        int i2 = 8;
        if (this.isChannel) {
            createSelfPeerView();
            if (this.chatActivityEnterView == null && (this.isGroup || this.currentStory.isLive)) {
                createEnterView();
            }
            if (this.chatActivityEnterView != null) {
                TLRPC.Chat chat2 = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-this.dialogId));
                ChatActivityEnterView chatActivityEnterView = this.chatActivityEnterView;
                if (this.currentStory.isLive || (!isBotsPreview() && this.isGroup && (ChatObject.canSendPlain(chat2) || ChatObject.isPossibleRemoveChatRestrictionsByBoosts(chat2)))) {
                    i2 = 0;
                }
                chatActivityEnterView.setVisibility(i2);
                this.chatActivityEnterView.setLiveComment(this.currentStory.isLive, disabledPaidFeatures(true));
                this.chatActivityEnterView.setSuggestionButtonVisible(this.currentStory.isLive && !disabledPaidFeatures(true) && (this.keyboardVisible || this.chatActivityEnterView.emojiViewVisible), true);
                this.chatActivityEnterView.getEditField().setText(this.storyViewer.getDraft(this.dialogId, this.currentStory.storyItem));
                this.chatActivityEnterView.setDialogId(this.dialogId, this.currentAccount);
                this.chatActivityEnterView.updateRecordButton(chat2, null);
            }
            if (this.reactionsCounter == null) {
                AnimatedTextView.AnimatedTextDrawable animatedTextDrawable = new AnimatedTextView.AnimatedTextDrawable();
                this.reactionsCounter = animatedTextDrawable;
                animatedTextDrawable.setCallback(this.likeButtonContainer);
                this.reactionsCounter.setTextColor(this.resourcesProvider.getColor(Theme.key_windowBackgroundWhiteBlackText));
                this.reactionsCounter.setTextSize(AndroidUtilities.m1036dp(14.0f));
                this.reactionsCounterProgress = new AnimatedFloat(this.likeButtonContainer);
            }
            if (this.repostButtonContainer != null && this.repostCounter == null) {
                AnimatedTextView.AnimatedTextDrawable animatedTextDrawable2 = new AnimatedTextView.AnimatedTextDrawable();
                this.repostCounter = animatedTextDrawable2;
                animatedTextDrawable2.setCallback(this.repostButtonContainer);
                this.repostCounter.setTextColor(this.resourcesProvider.getColor(Theme.key_windowBackgroundWhiteBlackText));
                this.repostCounter.setTextSize(AndroidUtilities.m1036dp(14.0f));
                this.repostCounterProgress = new AnimatedFloat(this.repostButtonContainer);
            }
            if (i == -1) {
                updateSelectedPosition();
            }
            updatePosition();
            this.count = getStoriesCount();
            this.storyContainer.invalidate();
            invalidate();
        } else if (this.isSelf) {
            createSelfPeerView();
            boolean z = this.currentStory.isLive;
            FrameLayout frameLayout = this.selfView;
            if (z) {
                frameLayout.setVisibility(8);
                if (this.chatActivityEnterView == null) {
                    createEnterView();
                }
                this.chatActivityEnterView.setVisibility(0);
            } else {
                frameLayout.setVisibility(0);
                ChatActivityEnterView chatActivityEnterView2 = this.chatActivityEnterView;
                if (chatActivityEnterView2 != null) {
                    chatActivityEnterView2.setVisibility(8);
                }
            }
            ChatActivityEnterView chatActivityEnterView3 = this.chatActivityEnterView;
            if (chatActivityEnterView3 != null) {
                chatActivityEnterView3.setLiveComment(this.currentStory.isLive, disabledPaidFeatures(true));
                this.chatActivityEnterView.setSuggestionButtonVisible(this.currentStory.isLive && !disabledPaidFeatures(true) && (this.keyboardVisible || this.chatActivityEnterView.emojiViewVisible), true);
            }
            if (i == -1) {
                ArrayList<Integer> arrayList = this.day;
                if (arrayList != null) {
                    int iIndexOf = arrayList.indexOf(Integer.valueOf(this.storyViewer.dayStoryId));
                    if (iIndexOf < 0 && !this.day.isEmpty()) {
                        if (this.storyViewer.dayStoryId > this.day.get(0).intValue()) {
                            iIndexOf = 0;
                        } else {
                            int i3 = this.storyViewer.dayStoryId;
                            ArrayList<Integer> arrayList2 = this.day;
                            if (i3 < arrayList2.get(arrayList2.size() - 1).intValue()) {
                                iIndexOf = this.day.size() - 1;
                            }
                        }
                    }
                    this.selectedPosition = Math.max(0, iIndexOf);
                } else if (!this.uploadingStories.isEmpty()) {
                    this.selectedPosition = this.storyItems.size();
                } else {
                    for (int i4 = 0; i4 < this.storyItems.size(); i4++) {
                        if (this.storyItems.get(i4).justUploaded || this.storyItems.get(i4).f1454id > this.storiesController.dialogIdToMaxReadId.get(this.dialogId)) {
                            this.selectedPosition = i4;
                            break;
                        }
                    }
                }
            }
            updatePosition();
            this.storyContainer.invalidate();
            invalidate();
        } else {
            if (this.chatActivityEnterView == null) {
                createEnterView();
            }
            if (this.isPremiumBlocked && this.premiumBlockedText == null) {
                createPremiumBlockedText();
            }
            if (this.premiumBlockedText != null) {
                if (this.isPremiumBlocked || this.areLiveCommentsDisabled) {
                    updatePremiumBlockedText();
                }
                this.premiumBlockedText.setVisibility(((!this.isPremiumBlocked || this.currentStory.isLive) && !this.areLiveCommentsDisabled) ? 8 : 0);
            }
            StoryFailView storyFailView = this.failView;
            if (storyFailView != null) {
                storyFailView.setVisibility(8);
            }
            if (i == -1) {
                updateSelectedPosition();
            }
            updatePosition();
            ChatActivityEnterView chatActivityEnterView4 = this.chatActivityEnterView;
            if (chatActivityEnterView4 != null) {
                chatActivityEnterView4.setVisibility((isBotsPreview() || UserObject.isService(this.dialogId)) ? 8 : 0);
                this.chatActivityEnterView.setLiveComment(this.currentStory.isLive, disabledPaidFeatures(true));
                this.chatActivityEnterView.setSuggestionButtonVisible(this.currentStory.isLive && !disabledPaidFeatures(true) && (this.keyboardVisible || this.chatActivityEnterView.emojiViewVisible), true);
                this.chatActivityEnterView.getEditField().setText(this.storyViewer.getDraft(this.dialogId, this.currentStory.storyItem));
                this.chatActivityEnterView.setDialogId(this.dialogId, this.currentAccount);
                TLRPC.UserFull userFull = MessagesController.getInstance(this.currentAccount).getUserFull(this.dialogId);
                if (userFull != null) {
                    this.chatActivityEnterView.updateRecordButton(null, userFull);
                } else {
                    MessagesController.getInstance(this.currentAccount).loadFullUser(MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(this.dialogId)), this.classGuid, false);
                }
            }
            this.count = getStoriesCount();
            FrameLayout frameLayout2 = this.selfView;
            if (frameLayout2 != null) {
                frameLayout2.setVisibility(8);
            }
            this.storyContainer.invalidate();
            invalidate();
        }
        checkStealthMode(false);
    }

    private void createUnsupportedContainer() {
        if (this.unsupportedContainer != null) {
            return;
        }
        FrameLayout frameLayout = new FrameLayout(getContext());
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(1);
        TextView textView = new TextView(getContext());
        textView.setTypeface(AndroidUtilities.bold());
        textView.setGravity(1);
        textView.setTextSize(1, 16.0f);
        textView.setText(LocaleController.getString(C2797R.string.StoryUnsupported));
        textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText, this.resourcesProvider));
        TextView textView2 = new TextView(getContext());
        ScaleStateListAnimator.apply(textView2);
        textView2.setText(LocaleController.getString(C2797R.string.AppUpdate));
        int i = Theme.key_featuredStickers_buttonText;
        textView2.setTextColor(Theme.getColor(i, this.resourcesProvider));
        textView2.setPadding(AndroidUtilities.m1036dp(16.0f), AndroidUtilities.m1036dp(12.0f), AndroidUtilities.m1036dp(16.0f), AndroidUtilities.m1036dp(12.0f));
        textView2.setGravity(17);
        textView2.setTypeface(AndroidUtilities.bold());
        textView2.setTextSize(1, 15.0f);
        textView2.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1036dp(8.0f), Theme.getColor(Theme.key_featuredStickers_addButton, this.resourcesProvider), ColorUtils.setAlphaComponent(Theme.getColor(i, this.resourcesProvider), 30)));
        textView2.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda28
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createUnsupportedContainer$39(view);
            }
        });
        linearLayout.addView(textView, LayoutHelper.createLinear(-1, -2));
        linearLayout.addView(textView2, LayoutHelper.createLinear(-1, -2, 0.0f, 24.0f, 0.0f, 0.0f));
        frameLayout.addView(linearLayout, LayoutHelper.createFrame(-1, -2.0f, 17, 72.0f, 0.0f, 72.0f, 0.0f));
        this.storyContainer.addView(frameLayout);
        this.unsupportedContainer = frameLayout;
    }

    public /* synthetic */ void lambda$createUnsupportedContainer$39(View view) {
        Browser.openUrl(getContext(), BuildVars.RELEASES_URL);
    }

    public void preloadMainImage(long j) {
        if (this.dialogId == j && this.day == null) {
            return;
        }
        this.dialogId = j;
        updateStoryItems();
        updateSelectedPosition();
        updatePosition(true);
        TL_stories.PeerStories peerStories = this.storyViewer.overrideUserStories;
        StoriesController storiesController = this.storiesController;
        if (peerStories != null) {
            storiesController.loadSkippedStories(peerStories, true);
        } else {
            storiesController.loadSkippedStories(j);
        }
    }

    private void updateSelectedPosition() {
        TL_stories.PeerStories peerStories;
        int size;
        if (this.day != null) {
            ArrayList<StoriesController.UploadingStory> arrayList = this.uploadingStories;
            if (arrayList == null || arrayList.isEmpty()) {
                size = 0;
            } else {
                size = this.uploadingStories.size();
                for (int i = 0; i < this.uploadingStories.size(); i++) {
                    if (Long.hashCode(this.uploadingStories.get(i).random_id) == this.storyViewer.dayStoryId) {
                        this.selectedPosition = i;
                        return;
                    }
                }
            }
            int iIndexOf = this.day.indexOf(Integer.valueOf(this.storyViewer.dayStoryId));
            if (iIndexOf < 0 && !this.day.isEmpty()) {
                if (this.storyViewer.dayStoryId > this.day.get(0).intValue()) {
                    iIndexOf = 0;
                } else {
                    if (this.storyViewer.dayStoryId < this.day.get(r5.size() - 1).intValue()) {
                        iIndexOf = this.day.size() - 1;
                    }
                }
            }
            this.selectedPosition = size + iIndexOf;
        } else {
            int i2 = this.storyViewer.savedPositions.get(this.dialogId, -1);
            this.selectedPosition = i2;
            if (i2 == -1 && !this.storyViewer.isSingleStory && (peerStories = this.userStories) != null && peerStories.max_read_id > 0) {
                int i3 = 0;
                while (true) {
                    if (i3 >= this.storyItems.size()) {
                        break;
                    }
                    if (this.storyItems.get(i3).f1454id > this.userStories.max_read_id) {
                        this.selectedPosition = i3;
                        break;
                    }
                    i3++;
                }
            }
        }
        if (this.selectedPosition == -1) {
            this.selectedPosition = 0;
        }
    }

    public void updateStoryItems() {
        StoriesController.StoriesList storiesList;
        TL_stories.StoryItem storyItem;
        this.storyItems.clear();
        StoryViewer storyViewer = this.storyViewer;
        if (storyViewer.isSingleStory) {
            if (!storyViewer.singleStoryDeleted) {
                this.storyItems.add(storyViewer.singleStory);
            }
        } else {
            int i = 0;
            if (this.day != null && (storiesList = storyViewer.storiesList) != null) {
                if (storiesList instanceof StoriesController.BotPreviewsList) {
                    this.uploadingStories.clear();
                    ArrayList<StoriesController.UploadingStory> uploadingStories = MessagesController.getInstance(this.currentAccount).getStoriesController().getUploadingStories(this.dialogId);
                    String str = ((StoriesController.BotPreviewsList) this.storyViewer.storiesList).lang_code;
                    if (uploadingStories != null) {
                        for (int i2 = 0; i2 < uploadingStories.size(); i2++) {
                            StoriesController.UploadingStory uploadingStory = uploadingStories.get(i2);
                            StoryEntry storyEntry = uploadingStory.entry;
                            if (storyEntry != null && !storyEntry.isEdit && TextUtils.equals(storyEntry.botLang, str)) {
                                this.uploadingStories.add(uploadingStory);
                            }
                        }
                    }
                }
                ArrayList<Integer> arrayList = this.day;
                int size = arrayList.size();
                while (i < size) {
                    Integer num = arrayList.get(i);
                    i++;
                    MessageObject messageObjectFindMessageObject = this.storyViewer.storiesList.findMessageObject(num.intValue());
                    if (messageObjectFindMessageObject != null && (storyItem = messageObjectFindMessageObject.storyItem) != null) {
                        this.storyItems.add(storyItem);
                    }
                }
            } else if (storyViewer.storiesList != null) {
                while (i < this.storyViewer.storiesList.messageObjects.size()) {
                    this.storyItems.add(this.storyViewer.storiesList.messageObjects.get(i).storyItem);
                    i++;
                }
            } else {
                TL_stories.PeerStories peerStories = storyViewer.overrideUserStories;
                if (peerStories != null && DialogObject.getPeerDialogId(peerStories.peer) == this.dialogId) {
                    this.userStories = this.storyViewer.overrideUserStories;
                } else {
                    TL_stories.PeerStories stories = this.storiesController.getStories(this.dialogId);
                    this.userStories = stories;
                    if (stories == null) {
                        this.userStories = this.storiesController.getStoriesFromFullPeer(this.dialogId);
                    }
                }
                this.totalStoriesCount = 0;
                TL_stories.PeerStories peerStories2 = this.userStories;
                if (peerStories2 != null) {
                    this.totalStoriesCount = peerStories2.stories.size();
                    this.storyItems.addAll(this.userStories.stories);
                }
                this.uploadingStories.clear();
                ArrayList<StoriesController.UploadingStory> uploadingStories2 = this.storiesController.getUploadingStories(this.dialogId);
                if (uploadingStories2 != null) {
                    this.uploadingStories.addAll(uploadingStories2);
                }
            }
        }
        this.count = getStoriesCount();
    }

    private void createSelfPeerView() {
        if (this.selfView != null) {
            return;
        }
        C691430 c691430 = new FrameLayout(getContext()) { // from class: org.telegram.ui.Stories.PeerStoriesView.30
            public C691430(Context context) {
                super(context);
            }

            @Override // android.view.ViewGroup, android.view.View
            public void dispatchDraw(Canvas canvas) {
                int x;
                if (PeerStoriesView.this.selfAvatarsContainer.getVisibility() == 0 && PeerStoriesView.this.selfAvatarsContainer.getLayoutParams().width != (x = (int) (((PeerStoriesView.this.selfStatusView.getX() + PeerStoriesView.this.selfStatusView.getMeasuredWidth()) - PeerStoriesView.this.selfAvatarsContainer.getX()) + AndroidUtilities.m1036dp(10.0f)))) {
                    PeerStoriesView.this.selfAvatarsContainer.getLayoutParams().width = x;
                    PeerStoriesView.this.selfAvatarsContainer.invalidate();
                    PeerStoriesView.this.selfAvatarsContainer.requestLayout();
                }
                super.dispatchDraw(canvas);
            }
        };
        this.selfView = c691430;
        c691430.setClickable(true);
        addView(this.selfView, LayoutHelper.createFrame(-1, 48.0f, 48, 0.0f, 0.0f, 136.0f, 0.0f));
        C691531 c691531 = new View(getContext()) { // from class: org.telegram.ui.Stories.PeerStoriesView.31
            LoadingDrawable loadingDrawable = new LoadingDrawable();
            AnimatedFloat animatedFloat = new AnimatedFloat(250, CubicBezierInterpolator.DEFAULT);

            public C691531(Context context) {
                super(context);
                this.loadingDrawable = new LoadingDrawable();
                this.animatedFloat = new AnimatedFloat(250L, CubicBezierInterpolator.DEFAULT);
            }

            @Override // android.view.View
            public void onDraw(Canvas canvas) {
                Canvas canvas2;
                super.onDraw(canvas);
                this.animatedFloat.setParent(this);
                this.animatedFloat.set(PeerStoriesView.this.showViewsProgress ? 1.0f : 0.0f, false);
                if (this.animatedFloat.get() != 0.0f) {
                    if (this.animatedFloat.get() != 1.0f) {
                        canvas2 = canvas;
                        canvas2.saveLayerAlpha(0.0f, 0.0f, getLayoutParams().width, getMeasuredHeight(), (int) (this.animatedFloat.get() * 255.0f), 31);
                    } else {
                        canvas2 = canvas;
                        canvas2.save();
                    }
                    RectF rectF = AndroidUtilities.rectTmp;
                    rectF.set(0.0f, 0.0f, getLayoutParams().width, getMeasuredHeight());
                    this.loadingDrawable.setBounds(rectF);
                    this.loadingDrawable.setRadiiDp(24.0f);
                    this.loadingDrawable.setColors(ColorUtils.setAlphaComponent(-1, 20), ColorUtils.setAlphaComponent(-1, 50), ColorUtils.setAlphaComponent(-1, 50), ColorUtils.setAlphaComponent(-1, 70));
                    this.loadingDrawable.draw(canvas2);
                    invalidate();
                    canvas2.restore();
                }
            }
        };
        this.selfAvatarsContainer = c691531;
        c691531.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda31
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createSelfPeerView$40(view);
            }
        });
        this.selfView.addView(this.selfAvatarsContainer, LayoutHelper.createFrame(-1, 32.0f, 0, 9.0f, 11.0f, 0.0f, 0.0f));
        HwAvatarsImageView hwAvatarsImageView = new HwAvatarsImageView(getContext(), false);
        this.selfAvatarsView = hwAvatarsImageView;
        hwAvatarsImageView.setAvatarsTextSize(AndroidUtilities.m1036dp(18.0f));
        this.selfView.addView(this.selfAvatarsView, LayoutHelper.createFrame(-1, 28.0f, 0, 13.0f, 13.0f, 0.0f, 0.0f));
        TextView textView = new TextView(getContext());
        this.selfStatusView = textView;
        textView.setTextSize(1, 14.0f);
        this.selfStatusView.setTextColor(-1);
        this.selfView.addView(this.selfStatusView, LayoutHelper.createFrame(-2, -2.0f, 0, 0.0f, 16.0f, 0.0f, 9.0f));
        ImageView imageView = new ImageView(getContext());
        imageView.setImageDrawable(this.sharedResources.deleteDrawable);
        this.selfAvatarsContainer.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1036dp(15.0f), 0, ColorUtils.setAlphaComponent(-1, 120)));
        imageView.setBackground(Theme.createCircleSelectorDrawable(ColorUtils.setAlphaComponent(-1, 120), -AndroidUtilities.m1036dp(2.0f), -AndroidUtilities.m1036dp(2.0f)));
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$30 */
    public class C691430 extends FrameLayout {
        public C691430(Context context) {
            super(context);
        }

        @Override // android.view.ViewGroup, android.view.View
        public void dispatchDraw(Canvas canvas) {
            int x;
            if (PeerStoriesView.this.selfAvatarsContainer.getVisibility() == 0 && PeerStoriesView.this.selfAvatarsContainer.getLayoutParams().width != (x = (int) (((PeerStoriesView.this.selfStatusView.getX() + PeerStoriesView.this.selfStatusView.getMeasuredWidth()) - PeerStoriesView.this.selfAvatarsContainer.getX()) + AndroidUtilities.m1036dp(10.0f)))) {
                PeerStoriesView.this.selfAvatarsContainer.getLayoutParams().width = x;
                PeerStoriesView.this.selfAvatarsContainer.invalidate();
                PeerStoriesView.this.selfAvatarsContainer.requestLayout();
            }
            super.dispatchDraw(canvas);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$31 */
    public class C691531 extends View {
        LoadingDrawable loadingDrawable = new LoadingDrawable();
        AnimatedFloat animatedFloat = new AnimatedFloat(250, CubicBezierInterpolator.DEFAULT);

        public C691531(Context context) {
            super(context);
            this.loadingDrawable = new LoadingDrawable();
            this.animatedFloat = new AnimatedFloat(250L, CubicBezierInterpolator.DEFAULT);
        }

        @Override // android.view.View
        public void onDraw(Canvas canvas) {
            Canvas canvas2;
            super.onDraw(canvas);
            this.animatedFloat.setParent(this);
            this.animatedFloat.set(PeerStoriesView.this.showViewsProgress ? 1.0f : 0.0f, false);
            if (this.animatedFloat.get() != 0.0f) {
                if (this.animatedFloat.get() != 1.0f) {
                    canvas2 = canvas;
                    canvas2.saveLayerAlpha(0.0f, 0.0f, getLayoutParams().width, getMeasuredHeight(), (int) (this.animatedFloat.get() * 255.0f), 31);
                } else {
                    canvas2 = canvas;
                    canvas2.save();
                }
                RectF rectF = AndroidUtilities.rectTmp;
                rectF.set(0.0f, 0.0f, getLayoutParams().width, getMeasuredHeight());
                this.loadingDrawable.setBounds(rectF);
                this.loadingDrawable.setRadiiDp(24.0f);
                this.loadingDrawable.setColors(ColorUtils.setAlphaComponent(-1, 20), ColorUtils.setAlphaComponent(-1, 50), ColorUtils.setAlphaComponent(-1, 50), ColorUtils.setAlphaComponent(-1, 70));
                this.loadingDrawable.draw(canvas2);
                invalidate();
                canvas2.restore();
            }
        }
    }

    public /* synthetic */ void lambda$createSelfPeerView$40(View view) {
        showUserViewsDialog();
    }

    public void deleteStory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), this.resourcesProvider);
        builder.setTitle(LocaleController.getString(isBotsPreview() ? C2797R.string.DeleteBotPreviewTitle : C2797R.string.DeleteStoryTitle));
        builder.setMessage(LocaleController.getString(isBotsPreview() ? C2797R.string.DeleteBotPreviewSubtitle : C2797R.string.DeleteStorySubtitle));
        builder.setPositiveButton(LocaleController.getString(C2797R.string.Delete), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda55
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                this.f$0.lambda$deleteStory$41(alertDialog, i);
            }
        });
        builder.setNegativeButton(LocaleController.getString(C2797R.string.Cancel), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda56
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                alertDialog.dismiss();
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        this.delegate.showDialog(alertDialogCreate);
        alertDialogCreate.redPositive();
    }

    public /* synthetic */ void lambda$deleteStory$41(AlertDialog alertDialog, int i) {
        TL_stories.StoryItem storyItem;
        if (this.currentStory.isLive && (storyItem = this.currentStory.storyItem) != null) {
            TLRPC.MessageMedia messageMedia = storyItem.media;
            if (messageMedia instanceof TLRPC.TL_messageMediaVideoStream) {
                TLRPC.InputGroupCall inputGroupCall = ((TLRPC.TL_messageMediaVideoStream) messageMedia).call;
                LivePlayer livePlayer = LivePlayer.recording;
                if (livePlayer != null && livePlayer.equals(inputGroupCall)) {
                    LivePlayer.recording.destroy();
                    if (LivePlayer.recording != null) {
                        LivePlayer.recording = null;
                        NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.liveStoryUpdated, Long.valueOf(LivePlayer.recording.getCallId()));
                    }
                }
            }
        }
        this.currentStory.cancelOrDelete();
        updateStoryItems();
        if (this.isActive && this.count == 0) {
            this.delegate.switchToNextAndRemoveCurrentPeer();
            return;
        }
        int i2 = this.selectedPosition;
        int i3 = this.count;
        if (i2 >= i3) {
            this.selectedPosition = i3 - 1;
        } else if (i2 < 0) {
            this.selectedPosition = 0;
        }
        updatePosition();
        StoryViewer storyViewer = this.storyViewer;
        if (storyViewer != null) {
            storyViewer.checkSelfStoriesView();
        }
    }

    private void showUserViewsDialog() {
        this.storyViewer.openViews();
    }

    @Override // org.telegram.p035ui.Components.SizeNotifierFrameLayout, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.sharedResources.topOverlayGradient.setBounds(0, 0, getMeasuredWidth(), AndroidUtilities.m1036dp(72.0f));
    }

    @Override // org.telegram.p035ui.Components.SizeNotifierFrameLayout, android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        AnimatedTextView.AnimatedTextDrawable animatedTextDrawable;
        AnimatedTextView.AnimatedTextDrawable animatedTextDrawable2;
        updateViewOffsets();
        if (this.isChannel && (animatedTextDrawable2 = this.reactionsCounter) != null) {
            animatedTextDrawable2.setBounds(0, 0, getMeasuredWidth(), AndroidUtilities.m1036dp(40.0f));
        }
        if (this.isChannel && (animatedTextDrawable = this.repostCounter) != null) {
            animatedTextDrawable.setBounds(0, 0, getMeasuredWidth(), AndroidUtilities.m1036dp(40.0f));
        }
        super.dispatchDraw(canvas);
        if (this.movingReaction) {
            float x = this.bottomActionsLinearLayout.getX() + this.likeButtonContainer.getX() + (this.likeButtonContainer.getMeasuredWidth() / 2.0f);
            float y = this.bottomActionsLinearLayout.getY() + this.likeButtonContainer.getY() + (this.likeButtonContainer.getMeasuredHeight() / 2.0f);
            int iM1036dp = AndroidUtilities.m1036dp(24.0f);
            float f = iM1036dp / 2.0f;
            float fLerp = AndroidUtilities.lerp(this.movingReactionFromX, x - f, CubicBezierInterpolator.EASE_OUT.getInterpolation(this.movingReactionProgress));
            float fLerp2 = AndroidUtilities.lerp(this.movingReactionFromY, y - f, this.movingReactionProgress);
            int iLerp = AndroidUtilities.lerp(this.movingReactionFromSize, iM1036dp, this.movingReactionProgress);
            if (this.drawAnimatedEmojiAsMovingReaction) {
                AnimatedEmojiDrawable animatedEmojiDrawable = this.reactionMoveDrawable;
                if (animatedEmojiDrawable != null) {
                    float f2 = iLerp;
                    animatedEmojiDrawable.setBounds((int) fLerp, (int) fLerp2, (int) (fLerp + f2), (int) (fLerp2 + f2));
                    this.reactionMoveDrawable.draw(canvas);
                }
            } else {
                float f3 = iLerp;
                this.reactionMoveImageReceiver.setImageCoords(fLerp, fLerp2, f3, f3);
                this.reactionMoveImageReceiver.draw(canvas);
            }
        }
        if (this.drawReactionEffect) {
            float x2 = this.bottomActionsLinearLayout.getX() + this.likeButtonContainer.getX() + (this.likeButtonContainer.getMeasuredWidth() / 2.0f);
            float y2 = this.bottomActionsLinearLayout.getY() + this.likeButtonContainer.getY() + (this.likeButtonContainer.getMeasuredHeight() / 2.0f);
            int iM1036dp2 = AndroidUtilities.m1036dp(120.0f);
            if (!this.drawAnimatedEmojiAsMovingReaction) {
                float f4 = iM1036dp2;
                float f5 = f4 / 2.0f;
                this.reactionEffectImageReceiver.setImageCoords(x2 - f5, y2 - f5, f4, f4);
                this.reactionEffectImageReceiver.draw(canvas);
                if (this.reactionEffectImageReceiver.getLottieAnimation() != null && this.reactionEffectImageReceiver.getLottieAnimation().isLastFrame()) {
                    this.drawReactionEffect = false;
                }
            } else {
                AnimatedEmojiEffect animatedEmojiEffect = this.emojiReactionEffect;
                if (animatedEmojiEffect != null) {
                    float f6 = iM1036dp2 / 2.0f;
                    animatedEmojiEffect.setBounds((int) (x2 - f6), (int) (y2 - f6), (int) (x2 + f6), (int) (y2 + f6));
                    this.emojiReactionEffect.draw(canvas);
                    if (this.emojiReactionEffect.isDone()) {
                        this.emojiReactionEffect.removeView(this);
                        this.emojiReactionEffect = null;
                        this.drawReactionEffect = false;
                    }
                } else {
                    this.drawReactionEffect = false;
                }
            }
        }
        ChatActivityEnterView chatActivityEnterView = this.chatActivityEnterView;
        if (chatActivityEnterView != null) {
            chatActivityEnterView.drawRecordedPannel(canvas);
        }
    }

    @Override // org.telegram.p035ui.Components.SizeNotifierFrameLayout, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.attachedToWindow = true;
        this.imageReceiver.onAttachedToWindow();
        this.rightPreloadImageReceiver.onAttachedToWindow();
        this.leftPreloadImageReceiver.onAttachedToWindow();
        this.reactionEffectImageReceiver.onAttachedToWindow();
        this.reactionMoveImageReceiver.onAttachedToWindow();
        ChatActivityEnterView chatActivityEnterView = this.chatActivityEnterView;
        if (chatActivityEnterView != null) {
            chatActivityEnterView.onResume();
        }
        for (int i = 0; i < this.preloadReactionHolders.size(); i++) {
            this.preloadReactionHolders.get(i).onAttachedToWindow(true);
        }
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.chatInfoDidLoad);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.liveStoryUpdated);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.storiesUpdated);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.storyQualityUpdate);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.storiesListUpdated);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.stealthModeChanged);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.storiesLimitUpdate);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.userIsPremiumBlockedUpadted);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.didLoadSendAsPeers);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.emojiLoaded);
    }

    @Override // org.telegram.p035ui.Components.SizeNotifierFrameLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.attachedToWindow = false;
        this.imageReceiver.onDetachedFromWindow();
        this.rightPreloadImageReceiver.onDetachedFromWindow();
        this.leftPreloadImageReceiver.onDetachedFromWindow();
        this.reactionEffectImageReceiver.onDetachedFromWindow();
        this.reactionMoveImageReceiver.onDetachedFromWindow();
        ChatActivityEnterView chatActivityEnterView = this.chatActivityEnterView;
        if (chatActivityEnterView != null) {
            chatActivityEnterView.onPause();
            this.chatActivityEnterView.onDestroy();
        }
        AnimatedEmojiDrawable animatedEmojiDrawable = this.reactionMoveDrawable;
        if (animatedEmojiDrawable != null) {
            animatedEmojiDrawable.removeView(this);
            this.reactionMoveDrawable = null;
        }
        AnimatedEmojiEffect animatedEmojiEffect = this.emojiReactionEffect;
        if (animatedEmojiEffect != null) {
            animatedEmojiEffect.removeView(this);
            this.emojiReactionEffect = null;
        }
        for (int i = 0; i < this.preloadReactionHolders.size(); i++) {
            this.preloadReactionHolders.get(i).onAttachedToWindow(false);
        }
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.chatInfoDidLoad);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.liveStoryUpdated);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.storiesUpdated);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.storyQualityUpdate);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.storiesListUpdated);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.stealthModeChanged);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.storiesLimitUpdate);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.userIsPremiumBlockedUpadted);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.didLoadSendAsPeers);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.emojiLoaded);
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        LivePlayer livePlayer;
        if (i != NotificationCenter.storiesUpdated) {
            boolean z = false;
            if (i != NotificationCenter.storiesListUpdated || this.storyViewer.storiesList != objArr[0]) {
                if (i == NotificationCenter.storyQualityUpdate) {
                    updatePosition();
                    return;
                }
                if (i == NotificationCenter.emojiLoaded) {
                    this.storyCaptionView.captionTextview.invalidate();
                    return;
                }
                if (i == NotificationCenter.stealthModeChanged) {
                    checkStealthMode(true);
                    return;
                }
                if (i == NotificationCenter.storiesLimitUpdate) {
                    StoriesController.StoryLimit storyLimitCheckStoryLimit = MessagesController.getInstance(this.currentAccount).getStoriesController().checkStoryLimit();
                    if (storyLimitCheckStoryLimit == null || !storyLimitCheckStoryLimit.active(this.currentAccount) || this.delegate == null) {
                        return;
                    }
                    this.delegate.showDialog(new LimitReachedBottomSheet(fragmentForLimit(), findActivity(), storyLimitCheckStoryLimit.getLimitReachedType(), this.currentAccount, null));
                    return;
                }
                if (i == NotificationCenter.userIsPremiumBlockedUpadted) {
                    TL_account.RequirementToContact requirementToContactIsUserContactBlocked = MessagesController.getInstance(this.currentAccount).isUserContactBlocked(this.dialogId);
                    if (this.dialogId >= 0 && !UserConfig.getInstance(this.currentAccount).isPremium() && DialogObject.isPremiumBlocked(requirementToContactIsUserContactBlocked)) {
                        z = true;
                    }
                    if (this.isPremiumBlocked == z && this.starsPriceBlocked == DialogObject.getMessagesStarsPrice(requirementToContactIsUserContactBlocked)) {
                        return;
                    }
                    this.isPremiumBlocked = z;
                    this.starsPriceBlocked = DialogObject.getMessagesStarsPrice(requirementToContactIsUserContactBlocked);
                    updatePosition();
                    checkStealthMode(true);
                    return;
                }
                if (i == NotificationCenter.chatInfoDidLoad) {
                    Object obj = objArr[0];
                    if ((obj instanceof TLRPC.ChatFull) && this.dialogId == (-((TLRPC.ChatFull) obj).f1246id)) {
                        updatePosition();
                        return;
                    }
                    return;
                }
                if (i == NotificationCenter.liveStoryUpdated) {
                    long jLongValue = ((Long) objArr[0]).longValue();
                    StoryViewer storyViewer = this.storyViewer;
                    if (storyViewer == null || (livePlayer = storyViewer.livePlayer) == null || livePlayer.getCallId() != jLongValue) {
                        return;
                    }
                    updatePosition();
                    ChatActivityEnterView chatActivityEnterView = this.chatActivityEnterView;
                    if (chatActivityEnterView != null) {
                        chatActivityEnterView.checkSendButton(true);
                        this.chatActivityEnterView.updateSendAsButton(true);
                        checkStealthMode(true);
                    }
                    LiveCommentsView liveCommentsView = this.liveCommentsView;
                    if (liveCommentsView != null) {
                        liveCommentsView.updatedMinStars();
                        return;
                    }
                    return;
                }
                if (i == NotificationCenter.didLoadSendAsPeers && ((Boolean) objArr[2]).booleanValue()) {
                    loadSendAsPeers(true);
                    return;
                }
                return;
            }
        }
        Delegate delegate = this.delegate;
        if (delegate == null || !delegate.isClosed()) {
            if (this.isActive) {
                updateStoryItems();
                if (this.count == 0) {
                    if (this.deletedPeer) {
                        return;
                    }
                    this.deletedPeer = true;
                    this.delegate.switchToNextAndRemoveCurrentPeer();
                    return;
                }
                if (this.selectedPosition >= this.storyItems.size() + this.uploadingStories.size()) {
                    this.selectedPosition = (this.storyItems.size() + this.uploadingStories.size()) - 1;
                }
                updatePosition();
                if (this.isSelf || this.isChannel) {
                    updateUserViews(true);
                }
            }
            TL_stories.PeerStories peerStories = this.storyViewer.overrideUserStories;
            if (peerStories != null) {
                this.storiesController.loadSkippedStories(peerStories, true);
            } else {
                long j = this.dialogId;
                if (j != 0) {
                    this.storiesController.loadSkippedStories(j);
                }
            }
            ActionBarMenuSubItem actionBarMenuSubItem = this.editStoryItem;
            if (actionBarMenuSubItem != null) {
                actionBarMenuSubItem.animate().alpha((this.storiesController.hasUploadingStories(this.dialogId) && this.currentStory.isVideo && !SharedConfig.allowPreparingHevcPlayers()) ? 0.5f : 1.0f).start();
            }
        }
    }

    private void loadSendAsPeers(boolean z) {
        LivePlayer livePlayer;
        if (this.sendAsPeersObj != null) {
            return;
        }
        StoryViewer storyViewer = this.storyViewer;
        if (storyViewer == null || (livePlayer = storyViewer.livePlayer) == null || !livePlayer.sendAsDisabled()) {
            TLRPC.TL_channels_sendAsPeers sendAsPeers = MessagesController.getInstance(this.currentAccount).getSendAsPeers(this.dialogId, true);
            this.sendAsPeersObj = sendAsPeers;
            ChatActivityEnterView chatActivityEnterView = this.chatActivityEnterView;
            if (chatActivityEnterView == null || sendAsPeers == null) {
                return;
            }
            chatActivityEnterView.updateSendAsButton(z);
        }
    }

    public /* synthetic */ void lambda$new$43() {
        checkStealthMode(true);
    }

    public void checkStealthMode(boolean z) {
        if (this.chatActivityEnterView != null && this.isVisible && this.attachedToWindow) {
            AndroidUtilities.cancelRunOnUIThread(this.updateStealthModeTimer);
            TL_stories.TL_storiesStealthMode stealthMode = this.storiesController.getStealthMode();
            this.chatActivityEnterView.checkSendButton(true);
            if ((this.isPremiumBlocked && !this.currentStory.isLive) || this.areLiveCommentsDisabled) {
                this.stealthModeIsActive = false;
                this.chatActivityEnterView.setEnabled(false);
                this.chatActivityEnterView.setOverrideHint(" ", z);
                return;
            }
            if (this.starsPriceBlocked > 0) {
                this.stealthModeIsActive = false;
                this.chatActivityEnterView.setEnabled(true);
                this.chatActivityEnterView.setOverrideHint(StarsIntroActivity.replaceStars(LocaleController.formatString(C2797R.string.TypeMessageForStars, LocaleController.formatNumber(this.starsPriceBlocked, ','))), z);
                return;
            }
            if (!this.currentStory.isLive && stealthMode != null) {
                int currentTime = ConnectionsManager.getInstance(this.currentAccount).getCurrentTime();
                int i = stealthMode.active_until_date;
                if (currentTime < i) {
                    this.stealthModeIsActive = true;
                    int currentTime2 = i - ConnectionsManager.getInstance(this.currentAccount).getCurrentTime();
                    int i2 = currentTime2 / 60;
                    int i3 = currentTime2 % 60;
                    int i4 = C2797R.string.StealthModeActiveHintShort;
                    Locale locale = Locale.US;
                    int iMeasureText = (int) this.chatActivityEnterView.getEditField().getPaint().measureText(LocaleController.formatString(i4, String.format(locale, "%02d:%02d", 99, 99)));
                    this.chatActivityEnterView.setEnabled(true);
                    float f = iMeasureText * 1.2f;
                    float measuredWidth = this.chatActivityEnterView.getEditField().getMeasuredWidth();
                    ChatActivityEnterView chatActivityEnterView = this.chatActivityEnterView;
                    if (f >= measuredWidth) {
                        chatActivityEnterView.setOverrideHint(LocaleController.formatString(C2797R.string.StealthModeActiveHintShort, _UrlKt.FRAGMENT_ENCODE_SET), String.format(locale, "%02d:%02d", Integer.valueOf(i2), Integer.valueOf(i3)), z);
                    } else {
                        chatActivityEnterView.setOverrideHint(LocaleController.formatString(C2797R.string.StealthModeActiveHint, String.format(locale, "%02d:%02d", Integer.valueOf(i2), Integer.valueOf(i3))), z);
                    }
                    AndroidUtilities.runOnUIThread(this.updateStealthModeTimer, 1000L);
                    return;
                }
            }
            this.stealthModeIsActive = false;
            this.chatActivityEnterView.setEnabled(true);
            boolean z2 = this.currentStory.isLive;
            ChatActivityEnterView chatActivityEnterView2 = this.chatActivityEnterView;
            if (!z2) {
                chatActivityEnterView2.setOverrideHint(LocaleController.getString(this.isGroup ? C2797R.string.ReplyToGroupStory : C2797R.string.ReplyPrivately), z);
                return;
            }
            long starsPrice = chatActivityEnterView2.getStarsPrice();
            ChatActivityEnterView chatActivityEnterView3 = this.chatActivityEnterView;
            if (starsPrice > 0) {
                chatActivityEnterView3.setOverrideHint(StarsIntroActivity.replaceStars(LocaleController.formatString(C2797R.string.CommentFor, LocaleController.formatNumber((int) starsPrice, ',')), this.chatActivityEnterView.spans), z);
                ColoredImageSpan coloredImageSpan = this.chatActivityEnterView.spans[0];
                if (coloredImageSpan != null) {
                    coloredImageSpan.spaceScaleX = 0.9f;
                    return;
                }
                return;
            }
            chatActivityEnterView3.setOverrideHint(LocaleController.getString(C2797R.string.Comment), z);
        }
    }

    public void updatePosition() {
        updatePosition(false);
    }

    /* JADX WARN: Removed duplicated region for block: B:1071:0x049f A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:1075:0x04b5  */
    /* JADX WARN: Removed duplicated region for block: B:1077:0x04b9  */
    /* JADX WARN: Removed duplicated region for block: B:1080:0x04cd A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:1087:0x04e2  */
    /* JADX WARN: Removed duplicated region for block: B:1089:0x04e5  */
    /* JADX WARN: Removed duplicated region for block: B:1096:0x04f7  */
    /* JADX WARN: Removed duplicated region for block: B:1099:0x04fc  */
    /* JADX WARN: Removed duplicated region for block: B:1104:0x050a  */
    /* JADX WARN: Removed duplicated region for block: B:1107:0x0510  */
    /* JADX WARN: Removed duplicated region for block: B:1111:0x0520 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:1118:0x0532  */
    /* JADX WARN: Removed duplicated region for block: B:1126:0x057d  */
    /* JADX WARN: Removed duplicated region for block: B:1129:0x0586  */
    /* JADX WARN: Removed duplicated region for block: B:1132:0x0599  */
    /* JADX WARN: Removed duplicated region for block: B:1136:0x05ac  */
    /* JADX WARN: Removed duplicated region for block: B:1141:0x05c5 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:1146:0x05d4  */
    /* JADX WARN: Removed duplicated region for block: B:1148:0x05f0  */
    /* JADX WARN: Removed duplicated region for block: B:1154:0x0610  */
    /* JADX WARN: Removed duplicated region for block: B:1215:0x088b  */
    /* JADX WARN: Removed duplicated region for block: B:1230:0x08ce  */
    /* JADX WARN: Removed duplicated region for block: B:1233:0x08d5  */
    /* JADX WARN: Removed duplicated region for block: B:1236:0x08de  */
    /* JADX WARN: Removed duplicated region for block: B:1249:0x08ff  */
    /* JADX WARN: Removed duplicated region for block: B:1252:0x090a  */
    /* JADX WARN: Removed duplicated region for block: B:1254:0x090e  */
    /* JADX WARN: Removed duplicated region for block: B:1259:0x091e  */
    /* JADX WARN: Removed duplicated region for block: B:1268:0x093e  */
    /* JADX WARN: Removed duplicated region for block: B:1271:0x0943  */
    /* JADX WARN: Removed duplicated region for block: B:1295:0x0982  */
    /* JADX WARN: Removed duplicated region for block: B:1306:0x099c  */
    /* JADX WARN: Removed duplicated region for block: B:1315:0x09cb  */
    /* JADX WARN: Removed duplicated region for block: B:1424:0x0b1e  */
    /* JADX WARN: Removed duplicated region for block: B:1435:0x0b3a  */
    /* JADX WARN: Removed duplicated region for block: B:1444:0x0b67  */
    /* JADX WARN: Removed duplicated region for block: B:1471:0x0bb4  */
    /* JADX WARN: Removed duplicated region for block: B:1495:0x0c0b  */
    /* JADX WARN: Removed duplicated region for block: B:1502:0x0c20  */
    /* JADX WARN: Removed duplicated region for block: B:1514:0x0c4f  */
    /* JADX WARN: Removed duplicated region for block: B:1527:0x0c86  */
    /* JADX WARN: Removed duplicated region for block: B:1535:0x0cbb  */
    /* JADX WARN: Removed duplicated region for block: B:1538:0x0cd1  */
    /* JADX WARN: Removed duplicated region for block: B:1543:0x0ce6  */
    /* JADX WARN: Removed duplicated region for block: B:1565:0x0d24  */
    /* JADX WARN: Removed duplicated region for block: B:1583:0x0d74  */
    /* JADX WARN: Removed duplicated region for block: B:1586:0x0d93  */
    /* JADX WARN: Removed duplicated region for block: B:1591:0x0da8  */
    /* JADX WARN: Removed duplicated region for block: B:1599:0x0dd0  */
    /* JADX WARN: Removed duplicated region for block: B:1602:0x0de3  */
    /* JADX WARN: Removed duplicated region for block: B:1611:0x0e1f  */
    /* JADX WARN: Removed duplicated region for block: B:1614:0x0e28  */
    /* JADX WARN: Removed duplicated region for block: B:1622:0x0e3a  */
    /* JADX WARN: Removed duplicated region for block: B:1641:0x0e6c  */
    /* JADX WARN: Removed duplicated region for block: B:1642:0x0e75  */
    /* JADX WARN: Removed duplicated region for block: B:1645:0x0e7b  */
    /* JADX WARN: Removed duplicated region for block: B:1654:0x0ea1  */
    /* JADX WARN: Removed duplicated region for block: B:1663:0x0ee5  */
    /* JADX WARN: Removed duplicated region for block: B:1665:0x0ee9  */
    /* JADX WARN: Removed duplicated region for block: B:1675:0x0f40  */
    /* JADX WARN: Removed duplicated region for block: B:1680:0x0f77  */
    /* JADX WARN: Removed duplicated region for block: B:1683:0x0f8c  */
    /* JADX WARN: Removed duplicated region for block: B:1687:0x0f9c  */
    /* JADX WARN: Removed duplicated region for block: B:1690:0x0fb4  */
    /* JADX WARN: Removed duplicated region for block: B:1701:0x0fd4  */
    /* JADX WARN: Removed duplicated region for block: B:1705:0x0fdd  */
    /* JADX WARN: Removed duplicated region for block: B:1711:0x0ffc  */
    /* JADX WARN: Removed duplicated region for block: B:1714:0x100c  */
    /* JADX WARN: Removed duplicated region for block: B:1727:0x102c  */
    /* JADX WARN: Removed duplicated region for block: B:1735:0x0dc2 A[EDGE_INSN: B:1735:0x0dc2->B:1597:0x0dc2 BREAK  A[LOOP:0: B:1589:0x0d9c->B:1596:0x0dbf], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:1740:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:964:0x0295  */
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
    private void updatePosition(boolean r44) {
        /*
            Method dump skipped, instruction units count: 4161
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Stories.PeerStoriesView.updatePosition(boolean):void");
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$32 */
    public class C691632 extends ReplacementSpan {
        private final RectF rect = new RectF();

        /* JADX INFO: renamed from: bg */
        private final Paint f1790bg = new Paint(1);
        private final Text text = new Text(LocaleController.getString(C2797R.string.LiveStoryBadge), 9.0f, AndroidUtilities.bold());

        public C691632() {
        }

        @Override // android.text.style.ReplacementSpan
        public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
            return (int) (this.text.getWidth() + AndroidUtilities.m1036dp(12.0f));
        }

        @Override // android.text.style.ReplacementSpan
        public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
            float fM1036dp = ((i3 + i5) / 2.0f) + AndroidUtilities.m1036dp(1.33f);
            this.rect.set(f, fM1036dp - AndroidUtilities.m1036dp(7.0f), this.text.getWidth() + f + AndroidUtilities.m1036dp(12.0f), AndroidUtilities.m1036dp(7.0f) + fM1036dp);
            this.f1790bg.setColor(-572850);
            RectF rectF = this.rect;
            canvas.drawRoundRect(rectF, rectF.height() / 2.0f, this.rect.height() / 2.0f, this.f1790bg);
            this.text.draw(canvas, AndroidUtilities.m1036dp(6.0f) + f, fM1036dp, -1, 1.0f);
        }
    }

    public /* synthetic */ void lambda$updatePosition$44(StoryCaptionView.Panel panel, View view) {
        Integer num;
        if (panel.peerId != null) {
            Bundle bundle = new Bundle();
            long jLongValue = panel.peerId.longValue();
            Long l = panel.peerId;
            if (jLongValue >= 0) {
                bundle.putLong("user_id", l.longValue());
            } else {
                bundle.putLong("chat_id", -l.longValue());
            }
            if (panel.isRepostMessage && (num = panel.messageId) != null) {
                bundle.putInt("message_id", num.intValue());
                this.storyViewer.presentFragment(new ChatActivity(bundle));
                return;
            } else {
                this.storyViewer.presentFragment(new ProfileActivity(bundle));
                return;
            }
        }
        BulletinFactory.m1142of(this.storyContainer, this.resourcesProvider).createSimpleBulletin(C2797R.raw.error, LocaleController.getString(C2797R.string.StoryHidAccount)).setTag(3).show(true);
    }

    public /* synthetic */ void lambda$updatePosition$45(long j, View view) {
        Bundle bundle = new Bundle();
        if (j >= 0) {
            bundle.putLong("user_id", j);
        } else {
            bundle.putLong("chat_id", -j);
        }
        this.storyViewer.presentFragment(new ProfileActivity(bundle));
    }

    public /* synthetic */ boolean lambda$updatePosition$48(View view) {
        if (disabledPaidFeatures(true)) {
            return false;
        }
        ItemOptions.makeOptions(this.storyViewer.containerView, this.resourcesProvider, view).add(C2797R.drawable.msg_edit, LocaleController.getString(C2797R.string.LiveStoryMessageEditStars), new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda26
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updatePosition$46();
            }
        }).addIf(this.messageStars > 0, C2797R.drawable.menu_delete_paid, LocaleController.getString(C2797R.string.LiveStoryMessageRemoveStars), new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda27
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updatePosition$47();
            }
        }).setGravity(5).forceTop(true).show();
        return true;
    }

    public /* synthetic */ void lambda$updatePosition$47() {
        this.messageStars = 0L;
        ChatActivityEnterView chatActivityEnterView = this.chatActivityEnterView;
        if (chatActivityEnterView != null) {
            chatActivityEnterView.checkSendButton(true);
            this.chatActivityEnterView.updateSendButtonPaid();
            checkStealthMode(true);
        }
    }

    public /* synthetic */ void lambda$updatePosition$49() {
        this.failView.setVisibility(8);
    }

    public /* synthetic */ void lambda$updatePosition$50() {
        if (this.storyViewer.isShown()) {
            this.reactionsTooltipRunnable = null;
            if (this.reactionsLongpressTooltip == null) {
                HintView2 joint = new HintView2(getContext(), 3).setJoint(1.0f, -22.0f);
                this.reactionsLongpressTooltip = joint;
                joint.setBgColor(ColorUtils.setAlphaComponent(ColorUtils.blendARGB(-16777216, -1, 0.13f), 240));
                this.reactionsLongpressTooltip.setBounce(false);
                this.reactionsLongpressTooltip.setText(LocaleController.getString(C2797R.string.ReactionLongTapHint));
                this.reactionsLongpressTooltip.setPadding(AndroidUtilities.m1036dp(8.0f), 0, AndroidUtilities.m1036dp(8.0f), AndroidUtilities.m1036dp(1.0f));
                this.storyContainer.addView(this.reactionsLongpressTooltip, LayoutHelper.createFrame(-1, -2.0f, 85, 0.0f, 0.0f, 0.0f, this.BIG_SCREEN ? 0.0f : 56.0f));
            }
            this.reactionsLongpressTooltip.show();
            SharedConfig.setStoriesReactionsLongPressHintUsed(true);
        }
    }

    public boolean isEditBotsPreview() {
        TLRPC.User user;
        return isBotsPreview() && (user = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(this.storyViewer.storiesList.dialogId))) != null && user.bot && user.bot_can_edit;
    }

    public boolean isBotsPreview() {
        StoriesController.StoriesList storiesList;
        StoryViewer storyViewer = this.storyViewer;
        return (storyViewer == null || (storiesList = storyViewer.storiesList) == null || storiesList.type != 4) ? false : true;
    }

    public /* synthetic */ void lambda$new$51() {
        showNoSoundHint(false);
        MessagesController.getGlobalMainSettings().edit().putInt("taptostorysoundhint", MessagesController.getGlobalMainSettings().getInt("taptostorysoundhint", 0) + 1).apply();
    }

    /* JADX INFO: renamed from: onHighlightLiveMessage */
    public void lambda$updatePosition$46() {
        MessagesController.getGlobalMainSettings().edit().putInt("taptostoryhighlighthint", 3).apply();
        TLRPC.TL_textWithEntities textWithEntities = this.chatActivityEnterView.getTextWithEntities();
        long clientUserId = UserConfig.getInstance(this.currentAccount).getClientUserId();
        TLRPC.Peer defaultSendAs = this.storyViewer.livePlayer.getDefaultSendAs();
        if (defaultSendAs != null) {
            clientUserId = DialogObject.getPeerDialogId(defaultSendAs);
        }
        Context context = getContext();
        int i = this.currentAccount;
        HighlightMessageSheet.open(context, i, clientUserId, DialogObject.getShortName(i, this.dialogId), textWithEntities, getMessageMinPrice(), this.messageStars, new Utilities.Callback() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda42
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$onHighlightLiveMessage$52((Long) obj);
            }
        }, new DarkThemeResourceProvider());
    }

    public /* synthetic */ void lambda$onHighlightLiveMessage$52(Long l) {
        this.messageStars = l.longValue();
        ChatActivityEnterView chatActivityEnterView = this.chatActivityEnterView;
        if (chatActivityEnterView != null) {
            chatActivityEnterView.checkSendButton(true);
            this.chatActivityEnterView.updateSendButtonPaid();
        }
        checkStealthMode(true);
    }

    private void createReplyDisabledView() {
        if (this.replyDisabledTextView != null) {
            return;
        }
        C691733 c691733 = new TextView(getContext()) { // from class: org.telegram.ui.Stories.PeerStoriesView.33
            public C691733(Context context) {
                super(context);
            }

            @Override // android.view.View
            public void setTranslationY(float f) {
                super.setTranslationY(f);
            }
        };
        this.replyDisabledTextView = c691733;
        c691733.setTextSize(1, 14.0f);
        this.replyDisabledTextView.setTextColor(ColorUtils.blendARGB(-16777216, -1, 0.5f));
        this.replyDisabledTextView.setGravity(19);
        this.replyDisabledTextView.setText(LocaleController.getString(C2797R.string.StoryReplyDisabled));
        addView(this.replyDisabledTextView, LayoutHelper.createFrame(-2, 40.0f, 3, 16.0f, 0.0f, 16.0f, 0.0f));
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$33 */
    public class C691733 extends TextView {
        public C691733(Context context) {
            super(context);
        }

        @Override // android.view.View
        public void setTranslationY(float f) {
            super.setTranslationY(f);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:86:0x0072 A[PHI: r4 r5
  0x0072: PHI (r4v5 int) = (r4v4 int), (r4v17 int) binds: [B:84:0x006b, B:81:0x005c] A[DONT_GENERATE, DONT_INLINE]
  0x0072: PHI (r5v2 org.telegram.messenger.ImageReceiver) = (r5v1 org.telegram.messenger.ImageReceiver), (r5v13 org.telegram.messenger.ImageReceiver) binds: [B:84:0x006b, B:81:0x005c] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void updatePreloadImages() {
        /*
            Method dump skipped, instruction units count: 483
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Stories.PeerStoriesView.updatePreloadImages():void");
    }

    private void setStoryImage(TL_stories.StoryItem storyItem, ImageReceiver imageReceiver, String str) {
        ArrayList<TLRPC.PhotoSize> arrayList;
        StoriesController.UploadingStory uploadingStoryFindEditingStory = this.storiesController.findEditingStory(this.dialogId, storyItem);
        if (uploadingStoryFindEditingStory != null) {
            setStoryImage(uploadingStoryFindEditingStory, imageReceiver, str);
            return;
        }
        TLRPC.MessageMedia messageMedia = storyItem.media;
        boolean zEndsWith = messageMedia != null && MessageObject.isVideoDocument(messageMedia.getDocument());
        String str2 = storyItem.attachPath;
        if (str2 != null) {
            if (storyItem.media == null) {
                zEndsWith = str2.toLowerCase().endsWith(".mp4");
            }
            String str3 = storyItem.attachPath;
            if (zEndsWith) {
                imageReceiver.setImage(ImageLocation.getForPath(str3), str + "_pframe", ImageLocation.getForPath(storyItem.firstFramePath), str, null, null, null, 0L, null, null, 0);
                return;
            }
            imageReceiver.setImage(ImageLocation.getForPath(str3), str, null, null, null, 0L, null, null, 0);
            return;
        }
        TLRPC.MessageMedia messageMedia2 = storyItem.media;
        if (zEndsWith) {
            TLRPC.PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(messageMedia2.getDocument().thumbs, MediaDataController.MAX_STYLE_RUNS_COUNT);
            imageReceiver.setImage(ImageLocation.getForDocument(storyItem.media.getDocument()), str + "_pframe", ImageLocation.getForDocument(closestPhotoSizeWithSize, storyItem.media.getDocument()), str, null, null, null, 0L, null, storyItem, 0);
            return;
        }
        TLRPC.Photo photo = messageMedia2 != null ? messageMedia2.photo : null;
        if (photo != null && (arrayList = photo.sizes) != null) {
            TLRPC.PhotoSize closestPhotoSizeWithSize2 = FileLoader.getClosestPhotoSizeWithSize(arrayList, Integer.MAX_VALUE);
            FileLoader.getClosestPhotoSizeWithSize(photo.sizes, 800);
            imageReceiver.setImage(null, null, ImageLocation.getForPhoto(closestPhotoSizeWithSize2, photo), str, null, null, null, 0L, null, storyItem, 0);
            return;
        }
        imageReceiver.clearImage();
    }

    private void setStoryImage(StoriesController.UploadingStory uploadingStory, ImageReceiver imageReceiver, String str) {
        if (uploadingStory.isVideo) {
            imageReceiver.setImage(null, null, ImageLocation.getForPath(uploadingStory.firstFramePath), str, null, null, null, 0L, null, null, 0);
        } else {
            imageReceiver.setImage(ImageLocation.getForPath(uploadingStory.path), str, null, null, null, 0L, null, null, 0);
        }
    }

    private void cancelWaiting() {
        Runnable runnable = this.cancellableViews;
        if (runnable != null) {
            runnable.run();
            this.cancellableViews = null;
        }
        this.showViewsProgress = false;
        if (this.isActive) {
            this.delegate.setIsWaiting(false);
        }
    }

    public void updateUserViews(boolean z) {
        AvatarsImageView avatarsImageView;
        int i;
        StoryItemHolder storyItemHolder = this.currentStory;
        TL_stories.StoryItem storyItem = storyItemHolder.storyItem;
        if (storyItem == null) {
            storyItem = storyItemHolder.editingSourceItem;
        }
        boolean z2 = this.isChannel;
        if (z2 || this.isSelf) {
            if (storyItem == null) {
                this.selfStatusView.setText(_UrlKt.FRAGMENT_ENCODE_SET);
                this.selfAvatarsContainer.setVisibility(8);
                this.selfAvatarsView.setVisibility(8);
                return;
            }
            TL_stories.StoryViews storyViews = storyItem.views;
            if (z2) {
                if (storyViews == null) {
                    storyItem.views = new TL_stories.TL_storyViews();
                }
                TL_stories.StoryViews storyViews2 = storyItem.views;
                if (storyViews2.views_count <= 0) {
                    storyViews2.views_count = 1;
                }
                AnimatedTextView.AnimatedTextDrawable animatedTextDrawable = this.repostCounter;
                if (animatedTextDrawable != null && (i = storyViews2.forwards_count) > 0) {
                    animatedTextDrawable.setText(Integer.toString(i), z && this.repostCounterVisible);
                    this.repostCounterVisible = true;
                } else {
                    this.repostCounterVisible = false;
                }
                int i2 = storyItem.views.reactions_count;
                if (i2 > 0) {
                    this.reactionsCounter.setText(Integer.toString(i2), z && this.reactionsCounterVisible);
                    this.reactionsCounterVisible = true;
                } else {
                    this.reactionsCounterVisible = false;
                }
                if (!z) {
                    this.reactionsCounterProgress.set(this.reactionsCounterVisible ? 1.0f : 0.0f, true);
                    AnimatedFloat animatedFloat = this.repostCounterProgress;
                    if (animatedFloat != null) {
                        animatedFloat.set(this.repostCounterVisible ? 1.0f : 0.0f, true);
                    }
                }
                TLRPC.Chat chat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-this.dialogId));
                if ((this.isGroup && (ChatObject.canSendPlain(chat) || ChatObject.isPossibleRemoveChatRestrictionsByBoosts(chat))) || storyItem.views.views_count <= 0) {
                    this.selfStatusView.setText(_UrlKt.FRAGMENT_ENCODE_SET);
                } else {
                    this.selfStatusView.setText(LocaleController.getString(this.storyViewer.storiesList == null ? C2797R.string.NobodyViews : C2797R.string.NobodyViewsArchived));
                    this.selfStatusView.setTranslationX(AndroidUtilities.m1036dp(16.0f));
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                    spannableStringBuilder.append((CharSequence) "d  ");
                    spannableStringBuilder.setSpan(new ColoredImageSpan(C2797R.drawable.filled_views), spannableStringBuilder.length() - 3, spannableStringBuilder.length() - 2, 0);
                    spannableStringBuilder.append((CharSequence) AndroidUtilities.formatWholeNumber(storyItem.views.views_count, 0));
                    this.selfStatusView.setText(spannableStringBuilder);
                    FrameLayout frameLayout = this.selfView;
                    if (frameLayout != null) {
                        ((FrameLayout.LayoutParams) frameLayout.getLayoutParams()).rightMargin = (int) (AndroidUtilities.m1036dp(80.0f) + this.selfStatusView.getPaint().measureText(spannableStringBuilder.toString()));
                    }
                }
                this.likeButtonContainer.getLayoutParams().width = (int) (AndroidUtilities.m1036dp(40.0f) + (this.reactionsCounterVisible ? this.reactionsCounter.getAnimateToWidth() + AndroidUtilities.m1036dp(4.0f) : 0.0f));
                ((ViewGroup.MarginLayoutParams) this.selfView.getLayoutParams()).rightMargin = AndroidUtilities.m1036dp(40.0f) + this.likeButtonContainer.getLayoutParams().width;
                FrameLayout frameLayout2 = this.repostButtonContainer;
                if (frameLayout2 != null) {
                    frameLayout2.getLayoutParams().width = (int) (AndroidUtilities.m1036dp(40.0f) + (this.repostCounterVisible ? this.repostCounter.getAnimateToWidth() + AndroidUtilities.m1036dp(4.0f) : 0.0f));
                    ((ViewGroup.MarginLayoutParams) this.selfView.getLayoutParams()).rightMargin += this.repostButtonContainer.getLayoutParams().width;
                    this.repostButtonContainer.requestLayout();
                }
                this.selfView.requestLayout();
                this.likeButtonContainer.requestLayout();
                this.selfAvatarsView.setVisibility(8);
                this.selfAvatarsContainer.setVisibility(8);
                this.storyAreasView.onStoryItemUpdated(this.currentStory.storyItem, z);
                return;
            }
            if (storyViews != null && storyViews.views_count > 0) {
                int i3 = 0;
                for (int i4 = 0; i4 < storyItem.views.recent_viewers.size(); i4++) {
                    TLObject userOrChat = MessagesController.getInstance(this.currentAccount).getUserOrChat(storyItem.views.recent_viewers.get(i4).longValue());
                    if (userOrChat != null) {
                        this.selfAvatarsView.setObject(i3, this.currentAccount, userOrChat);
                        i3++;
                    }
                    if (i3 >= 3) {
                        break;
                    }
                }
                int i5 = i3;
                while (true) {
                    avatarsImageView = this.selfAvatarsView;
                    if (i5 >= 3) {
                        break;
                    }
                    avatarsImageView.setObject(i5, this.currentAccount, null);
                    i5++;
                }
                avatarsImageView.commitTransition(false);
                SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(LocaleController.formatPluralStringComma("Views", storyItem.views.views_count));
                if (storyItem.views.reactions_count > 0) {
                    spannableStringBuilder2.append((CharSequence) "  d ");
                    ColoredImageSpan coloredImageSpan = new ColoredImageSpan(C2797R.drawable.mini_views_likes);
                    coloredImageSpan.setOverrideColor(-53704);
                    coloredImageSpan.setTopOffset(AndroidUtilities.m1036dp(0.2f));
                    spannableStringBuilder2.setSpan(coloredImageSpan, spannableStringBuilder2.length() - 2, spannableStringBuilder2.length() - 1, 0);
                    spannableStringBuilder2.append((CharSequence) String.valueOf(storyItem.views.reactions_count));
                }
                if (storyItem.views.forwards_count > 0) {
                    spannableStringBuilder2.append((CharSequence) "  d ");
                    ColoredImageSpan coloredImageSpan2 = new ColoredImageSpan(C2797R.drawable.mini_repost_story);
                    coloredImageSpan2.setOverrideColor(-14161823);
                    coloredImageSpan2.setTopOffset(AndroidUtilities.m1036dp(0.2f));
                    spannableStringBuilder2.setSpan(coloredImageSpan2, spannableStringBuilder2.length() - 2, spannableStringBuilder2.length() - 1, 0);
                    spannableStringBuilder2.append((CharSequence) String.valueOf(storyItem.views.forwards_count));
                }
                this.selfStatusView.setText(spannableStringBuilder2);
                AvatarsImageView avatarsImageView2 = this.selfAvatarsView;
                if (i3 == 0) {
                    avatarsImageView2.setVisibility(8);
                    this.selfStatusView.setTranslationX(AndroidUtilities.m1036dp(16.0f));
                } else {
                    avatarsImageView2.setVisibility(0);
                    this.selfStatusView.setTranslationX(AndroidUtilities.m1036dp(13.0f) + AndroidUtilities.m1036dp(24.0f) + (AndroidUtilities.m1036dp(20.0f) * (i3 - 1)) + AndroidUtilities.m1036dp(10.0f));
                }
                this.selfAvatarsContainer.setVisibility(0);
            } else {
                this.selfStatusView.setText(LocaleController.getString(this.storyViewer.storiesList == null ? C2797R.string.NobodyViews : C2797R.string.NobodyViewsArchived));
                this.selfStatusView.setTranslationX(AndroidUtilities.m1036dp(16.0f));
                this.selfAvatarsView.setVisibility(8);
                this.selfAvatarsContainer.setVisibility(8);
            }
            this.likeButtonContainer.getLayoutParams().width = AndroidUtilities.m1036dp(40.0f);
            this.bottomActionsLinearLayout.requestLayout();
        }
    }

    private void requestVideoPlayer(long j) {
        TLRPC.Document document;
        Uri uri;
        TLRPC.Document document2;
        if (this.isActive) {
            if (this.currentStory.isLive()) {
                Delegate delegate = this.delegate;
                TL_stories.StoryItem storyItem = this.currentStory.storyItem;
                long j2 = this.dialogId;
                int i = storyItem.f1454id;
                TLRPC.MessageMedia messageMedia = storyItem.media;
                delegate.requestPlayer(storyItem, j2, i, ((TLRPC.TL_messageMediaVideoStream) messageMedia).rtmp_stream, ((TLRPC.TL_messageMediaVideoStream) messageMedia).call, this.playerSharedScope);
                this.storyContainer.invalidate();
                return;
            }
            if (this.currentStory.isVideo()) {
                if (this.currentStory.getLocalPath() != null && new File(this.currentStory.getLocalPath()).exists()) {
                    Uri uriFromFile = Uri.fromFile(new File(this.currentStory.getLocalPath()));
                    FileLog.m1045d("StoryViewer requestVideoPlayer(" + j + "): playing from attachPath " + uriFromFile);
                    this.videoDuration = 0L;
                    uri = uriFromFile;
                    document = null;
                } else {
                    TL_stories.StoryItem storyItem2 = this.currentStory.storyItem;
                    if (storyItem2 != null) {
                        storyItem2.dialogId = this.dialogId;
                        try {
                            document2 = storyItem2.media.getDocument();
                        } catch (Exception unused) {
                            document2 = null;
                        }
                        try {
                            TL_stories.StoryItem storyItem3 = this.currentStory.storyItem;
                            if (storyItem3.fileReference == 0) {
                                storyItem3.fileReference = FileLoader.getInstance(this.currentAccount).getFileReference(this.currentStory.storyItem);
                            }
                            StringBuilder sb = new StringBuilder("?account=");
                            sb.append(this.currentAccount);
                            sb.append("&id=");
                            sb.append(document2.f1253id);
                            sb.append("&hash=");
                            sb.append(document2.access_hash);
                            sb.append("&dc=");
                            sb.append(document2.dc_id);
                            sb.append("&size=");
                            sb.append(document2.size);
                            sb.append("&mime=");
                            sb.append(URLEncoder.encode(document2.mime_type, "UTF-8"));
                            sb.append("&rid=");
                            sb.append(this.currentStory.storyItem.fileReference);
                            sb.append("&name=");
                            sb.append(URLEncoder.encode(FileLoader.getDocumentFileName(document2), "UTF-8"));
                            sb.append("&reference=");
                            byte[] bArr = document2.file_reference;
                            if (bArr == null) {
                                bArr = new byte[0];
                            }
                            sb.append(Utilities.bytesToHex(bArr));
                            sb.append("&sid=");
                            sb.append(this.currentStory.storyItem.f1454id);
                            sb.append("&did=");
                            sb.append(this.currentStory.storyItem.dialogId);
                            Uri uri2 = Uri.parse("tg://" + FileLoader.getAttachFileName(document2) + sb.toString());
                            FileLog.m1045d("StoryViewer requestVideoPlayer(" + j + "): playing from " + uri2);
                            this.videoDuration = (long) (MessageObject.getDocumentDuration(document2) * 1000.0d);
                            uri = uri2;
                            document = document2;
                        } catch (Exception unused2) {
                            document = document2;
                            uri = null;
                        }
                    } else {
                        document = null;
                        uri = null;
                    }
                }
                if (uri == null) {
                    FileLog.m1045d("PeerStoriesView.requestVideoPlayer(" + j + "): playing from null?");
                }
                this.delegate.requestPlayer(document, uri, j, this.playerSharedScope);
                this.storyContainer.invalidate();
                return;
            }
            FileLog.m1045d("PeerStoriesView.requestVideoPlayer(" + j + "): null, not a video");
            this.delegate.requestPlayer(null, null, 0L, this.playerSharedScope);
            VideoPlayerSharedScope videoPlayerSharedScope = this.playerSharedScope;
            videoPlayerSharedScope.renderView = null;
            videoPlayerSharedScope.firstFrameRendered = false;
            return;
        }
        this.playerSharedScope.renderView = null;
    }

    public boolean switchToNext(boolean z) {
        if (this.storyViewer.reversed) {
            z = !z;
        }
        int i = this.selectedPosition;
        if (z) {
            if (i >= getStoriesCount() - 1) {
                return false;
            }
            this.selectedPosition++;
            updatePosition();
            return true;
        }
        if (i <= 0) {
            return false;
        }
        this.selectedPosition = i - 1;
        updatePosition();
        return true;
    }

    public void setDelegate(Delegate delegate) {
        this.delegate = delegate;
    }

    public void drawPlayingBitmap(int i, int i2, Canvas canvas) {
        TextureView textureView;
        VideoPlayerSharedScope videoPlayerSharedScope = this.playerSharedScope;
        View view = videoPlayerSharedScope.renderView;
        if (view != null && videoPlayerSharedScope.surfaceView != null) {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
            AndroidUtilities.getBitmapFromSurface(this.playerSharedScope.surfaceView, bitmapCreateBitmap);
            if (bitmapCreateBitmap != null) {
                canvas.drawBitmap(bitmapCreateBitmap, 0.0f, 0.0f, (Paint) null);
                return;
            }
            return;
        }
        if (view != null && (textureView = videoPlayerSharedScope.textureView) != null) {
            Bitmap bitmap = textureView.getBitmap(i, i2);
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
                return;
            }
            return;
        }
        canvas.save();
        canvas.scale(i / this.storyContainer.getMeasuredWidth(), i2 / this.storyContainer.getMeasuredHeight());
        this.imageReceiver.draw(canvas);
        canvas.restore();
    }

    public Bitmap getPlayingBitmap() {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.storyContainer.getWidth(), this.storyContainer.getHeight(), Bitmap.Config.ARGB_8888);
        drawPlayingBitmap(bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight(), new Canvas(bitmapCreateBitmap));
        return bitmapCreateBitmap;
    }

    public void createBlurredBitmap(Canvas canvas, Bitmap bitmap) {
        drawPlayingBitmap(bitmap.getWidth(), bitmap.getHeight(), canvas);
        if (AndroidUtilities.computePerceivedBrightness(AndroidUtilities.getDominantColor(bitmap)) < 0.15f) {
            canvas.drawColor(ColorUtils.setAlphaComponent(-1, 102));
        }
        Utilities.blurBitmap(bitmap, 3, 1, bitmap.getWidth(), bitmap.getHeight(), bitmap.getRowBytes());
        Utilities.blurBitmap(bitmap, 3, 1, bitmap.getWidth(), bitmap.getHeight(), bitmap.getRowBytes());
    }

    public void stopPlaying(boolean z) {
        ImageReceiver imageReceiver = this.imageReceiver;
        if (z) {
            imageReceiver.stopAnimation();
            this.imageReceiver.setAllowStartAnimation(false);
        } else {
            imageReceiver.startAnimation();
            this.imageReceiver.setAllowStartAnimation(true);
        }
    }

    public long getCurrentPeer() {
        return this.dialogId;
    }

    public ArrayList<Integer> getCurrentDay() {
        return this.day;
    }

    public void setPaused(boolean z) {
        if (this.paused != z) {
            this.paused = z;
            stopPlaying(z);
            this.lastDrawTime = 0L;
            this.storyContainer.invalidate();
        }
    }

    public int getSelectedPosition() {
        return this.selectedPosition;
    }

    public boolean closeKeyboardOrEmoji() {
        if (this.likesReactionShowing) {
            if (this.likesReactionLayout.getReactionsWindow() != null) {
                int i = this.realKeyboardHeight;
                ReactionsContainerLayout reactionsContainerLayout = this.likesReactionLayout;
                if (i > 0) {
                    AndroidUtilities.hideKeyboard(reactionsContainerLayout.getReactionsWindow().windowView);
                } else {
                    reactionsContainerLayout.getReactionsWindow().dismiss();
                }
                return true;
            }
            showLikesReaction(false);
            return true;
        }
        StoryMediaAreasView storyMediaAreasView = this.storyAreasView;
        if (storyMediaAreasView != null) {
            storyMediaAreasView.closeHint();
        }
        if (this.storyCaptionView.textSelectionHelper.isInSelectionMode()) {
            this.storyCaptionView.textSelectionHelper.clear(false);
            return true;
        }
        HintView2 hintView2 = this.privacyHint;
        if (hintView2 != null) {
            hintView2.hide();
        }
        HintView2 hintView22 = this.soundTooltip;
        if (hintView22 != null) {
            hintView22.hide();
        }
        HintView hintView = this.mediaBanTooltip;
        if (hintView != null) {
            hintView.hide(true);
        }
        CaptionContainerView captionContainerView = this.storyEditCaptionView;
        if (captionContainerView != null && captionContainerView.onBackPressed()) {
            return true;
        }
        CustomPopupMenu customPopupMenu = this.popupMenu;
        if (customPopupMenu != null && customPopupMenu.isShowing()) {
            this.popupMenu.dismiss();
            return true;
        }
        if (checkRecordLocked(false)) {
            return true;
        }
        ReactionsContainerLayout reactionsContainerLayout2 = this.reactionsContainerLayout;
        if (reactionsContainerLayout2 != null && reactionsContainerLayout2.getReactionsWindow() != null && this.reactionsContainerLayout.getReactionsWindow().isShowing()) {
            this.reactionsContainerLayout.getReactionsWindow().dismiss();
            return true;
        }
        ChatActivityEnterView chatActivityEnterView = this.chatActivityEnterView;
        if (chatActivityEnterView != null && chatActivityEnterView.isPopupShowing()) {
            int i2 = this.realKeyboardHeight;
            ChatActivityEnterView chatActivityEnterView2 = this.chatActivityEnterView;
            if (i2 > 0) {
                AndroidUtilities.hideKeyboard(chatActivityEnterView2.getEmojiView());
            } else {
                chatActivityEnterView2.hidePopup(true, false);
            }
            return true;
        }
        if (getKeyboardHeight() >= AndroidUtilities.m1036dp(20.0f)) {
            ChatActivityEnterView chatActivityEnterView3 = this.chatActivityEnterView;
            if (chatActivityEnterView3 != null) {
                this.storyViewer.saveDraft(this.dialogId, this.currentStory.storyItem, chatActivityEnterView3.getEditText());
            }
            AndroidUtilities.hideKeyboard(this.chatActivityEnterView);
            return true;
        }
        if (this.storyCaptionView.getVisibility() != 0 || this.storyCaptionView.getProgressToBlackout() <= 0.0f) {
            return false;
        }
        this.storyCaptionView.collapse();
        this.inBlackoutMode = false;
        this.storyContainer.invalidate();
        return true;
    }

    public boolean findClickableView(ViewGroup viewGroup, float f, float f2, boolean z) {
        ChatActivityEnterView chatActivityEnterView;
        VideoPlayerSharedScope videoPlayerSharedScope;
        if (viewGroup == null) {
            return false;
        }
        HintView2 hintView2 = this.privacyHint;
        if (hintView2 != null && hintView2.shown()) {
            return true;
        }
        HintView2 hintView22 = this.soundTooltip;
        if (hintView22 != null && hintView22.shown()) {
            return true;
        }
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt.getVisibility() == 0) {
                if (childAt == this.storyCaptionView) {
                    Rect rect = AndroidUtilities.rectTmp2;
                    childAt.getHitRect(rect);
                    if (rect.contains((int) f, (int) f2) && this.storyCaptionView.allowInterceptTouchEvent(f, f2 - childAt.getTop())) {
                        return true;
                    }
                }
                Rect rect2 = AndroidUtilities.rectTmp2;
                childAt.getHitRect(rect2);
                if (childAt == this.storyContainer && (videoPlayerSharedScope = this.playerSharedScope) != null) {
                    View view = videoPlayerSharedScope.renderView;
                    if ((view instanceof ViewGroup) && findClickableView((ViewGroup) view, f - childAt.getX(), f2 - childAt.getY(), z)) {
                        return true;
                    }
                }
                if (childAt.isClickable() && rect2.contains((int) f, (int) f2)) {
                    return true;
                }
                StoryMediaAreasView storyMediaAreasView = this.storyAreasView;
                if (childAt == storyMediaAreasView && !storyMediaAreasView.hasSelected() && (f < AndroidUtilities.m1036dp(60.0f) || f > viewGroup.getMeasuredWidth() - AndroidUtilities.m1036dp(60.0f))) {
                    if (this.storyAreasView.hasClickableViews(f, f2)) {
                        return true;
                    }
                } else {
                    LiveCommentsView liveCommentsView = this.liveCommentsView;
                    if (childAt == liveCommentsView) {
                        liveCommentsView.topListView.getHitRect(rect2);
                        if (rect2.contains((int) ((f - this.liveCommentsView.getX()) - this.liveCommentsView.topListView.getX()), (int) ((f2 - this.liveCommentsView.getY()) - this.liveCommentsView.topListView.getY()))) {
                            return true;
                        }
                        if (!this.liveCommentsView.isCollapsed()) {
                            if (!this.keyboardVisible && f2 <= this.liveCommentsView.getY() + this.liveCommentsView.top()) {
                                LiveCommentsView liveCommentsView2 = this.liveCommentsView;
                                if (liveCommentsView2.listView.findChildViewUnder(f, (f2 - liveCommentsView2.getY()) - this.liveCommentsView.listView.getY()) != null) {
                                }
                            }
                            return true;
                        }
                        continue;
                    } else {
                        if (this.keyboardVisible && childAt == this.chatActivityEnterView && f2 > rect2.top) {
                            return true;
                        }
                        if (!z && rect2.contains((int) f, (int) f2) && (((childAt.isClickable() || childAt == this.reactionsContainerLayout) && childAt.isEnabled()) || ((chatActivityEnterView = this.chatActivityEnterView) != null && childAt == chatActivityEnterView.getRecordCircle()))) {
                            return true;
                        }
                        if (childAt.isEnabled() && (childAt instanceof ViewGroup) && findClickableView((ViewGroup) childAt, f - childAt.getX(), f2 - childAt.getY(), z)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void setAccount(int i) {
        this.currentAccount = i;
        this.storiesController = MessagesController.getInstance(i).storiesController;
        this.emojiAnimationsOverlay.setAccount(i);
        ReactionsContainerLayout reactionsContainerLayout = this.reactionsContainerLayout;
        if (reactionsContainerLayout != null) {
            reactionsContainerLayout.setCurrentAccount(i);
            this.reactionsContainerLayout.setMessage(null, null, true);
        }
        ReactionsContainerLayout reactionsContainerLayout2 = this.likesReactionLayout;
        if (reactionsContainerLayout2 != null) {
            reactionsContainerLayout2.setCurrentAccount(i);
        }
    }

    public void setActive(boolean z) {
        setActive(0L, z);
    }

    public void setActive(long j, boolean z) {
        if (this.isActive != z) {
            activeCount += z ? 1 : -1;
            this.isActive = z;
            if (z) {
                if (useSurfaceInViewPagerWorkAround()) {
                    this.delegate.setIsSwiping(true);
                    AndroidUtilities.cancelRunOnUIThread(this.allowDrawSurfaceRunnable);
                    AndroidUtilities.runOnUIThread(this.allowDrawSurfaceRunnable, 100L);
                }
                requestVideoPlayer(j);
                updatePreloadImages();
                this.muteIconView.setAnimation(this.sharedResources.muteDrawable);
                this.isActive = true;
                this.headerView.backupImageView.getImageReceiver().setVisible(true, true);
                if (this.currentStory.storyItem != null) {
                    FileLog.m1045d("StoryViewer displayed story dialogId=" + this.dialogId + " storyId=" + this.currentStory.storyItem.f1454id + " " + this.currentStory.getMediaDebugString());
                }
            } else {
                cancelTextSelection();
                this.muteIconView.clearAnimationDrawable();
                this.viewsThumbImageReceiver = null;
                this.isLongPressed = false;
                this.progressToHideInterface.set(0.0f, true);
                this.storyContainer.invalidate();
                invalidate();
                cancelWaiting();
                this.delegate.setIsRecording(false);
            }
            this.imageReceiver.setFileLoadingPriority(this.isActive ? 3 : 2);
            this.leftPreloadImageReceiver.setFileLoadingPriority(this.isActive ? 2 : 0);
            this.rightPreloadImageReceiver.setFileLoadingPriority(this.isActive ? 2 : 0);
            if (this.isSelf || this.isChannel) {
                this.storiesController.pollViewsForSelfStories(this.dialogId, this.isActive);
            }
        }
    }

    public void progressToDismissUpdated() {
        if (this.BIG_SCREEN) {
            invalidate();
        }
    }

    public void reset() {
        this.headerView.backupImageView.getImageReceiver().setVisible(true, true);
        if (this.changeBoundAnimator != null) {
            this.chatActivityEnterView.reset();
            this.chatActivityEnterView.setAlpha(1.0f - this.outT);
        }
        ReactionsContainerLayout reactionsContainerLayout = this.reactionsContainerLayout;
        if (reactionsContainerLayout != null) {
            reactionsContainerLayout.reset();
        }
        ReactionsContainerLayout reactionsContainerLayout2 = this.likesReactionLayout;
        if (reactionsContainerLayout2 != null) {
            reactionsContainerLayout2.reset();
        }
        InstantCameraView instantCameraView = this.instantCameraView;
        if (instantCameraView != null) {
            AndroidUtilities.removeFromParent(instantCameraView);
            this.instantCameraView.hideCamera(true);
            this.instantCameraView = null;
        }
        setActive(false);
        setIsVisible(false);
        this.isLongPressed = false;
        this.progressToHideInterface.set(0.0f, false);
        this.viewsThumbImageReceiver = null;
        this.messageSent = false;
        cancelTextSelection();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 == -1) {
            if (i == 0 || i == 2) {
                createChatAttachView();
                ChatAttachAlert chatAttachAlert = this.chatAttachAlert;
                if (chatAttachAlert != null) {
                    chatAttachAlert.getPhotoLayout().onActivityResultFragment(i, intent, null);
                    return;
                }
                return;
            }
            if (i == 21) {
                if (intent == null) {
                    showAttachmentError();
                    return;
                }
                if (intent.getData() != null) {
                    sendUriAsDocument(intent.getData());
                } else if (intent.getClipData() != null) {
                    ClipData clipData = intent.getClipData();
                    for (int i3 = 0; i3 < clipData.getItemCount(); i3++) {
                        sendUriAsDocument(clipData.getItemAt(i3).getUri());
                    }
                } else {
                    showAttachmentError();
                }
                ChatAttachAlert chatAttachAlert2 = this.chatAttachAlert;
                if (chatAttachAlert2 != null) {
                    chatAttachAlert2.lambda$new$0();
                }
                afterMessageSend(true);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x0044  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void sendUriAsDocument(android.net.Uri r22) {
        /*
            r21 = this;
            r1 = r21
            if (r22 != 0) goto L6
            goto La6
        L6:
            org.telegram.ui.Stories.PeerStoriesView$StoryItemHolder r0 = r1.currentStory
            org.telegram.tgnet.tl.TL_stories$StoryItem r11 = r0.storyItem
            if (r11 == 0) goto La6
            boolean r0 = r11 instanceof org.telegram.tgnet.tl.TL_stories.TL_storyItemSkipped
            if (r0 == 0) goto L12
            goto La6
        L12:
            java.lang.String r0 = r22.toString()
            java.lang.String r2 = "com.google.android.apps.photos.contentprovider"
            boolean r2 = r0.contains(r2)
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L44
            java.lang.String r2 = "/1/"
            java.lang.String[] r0 = r0.split(r2)     // Catch: java.lang.Exception -> L40
            r0 = r0[r4]     // Catch: java.lang.Exception -> L40
            java.lang.String r2 = "/ACTUAL"
            int r2 = r0.indexOf(r2)     // Catch: java.lang.Exception -> L40
            r5 = -1
            if (r2 == r5) goto L44
            java.lang.String r0 = r0.substring(r3, r2)     // Catch: java.lang.Exception -> L40
            java.lang.String r2 = "UTF-8"
            java.lang.String r0 = java.net.URLDecoder.decode(r0, r2)     // Catch: java.lang.Exception -> L40
            android.net.Uri r0 = android.net.Uri.parse(r0)     // Catch: java.lang.Exception -> L40
            goto L46
        L40:
            r0 = move-exception
            org.telegram.messenger.FileLog.m1048e(r0)
        L44:
            r0 = r22
        L46:
            java.lang.String r2 = org.telegram.messenger.AndroidUtilities.getPath(r0)
            boolean r5 = org.telegram.messenger.BuildVars.NO_SCOPED_STORAGE
            if (r5 != 0) goto L50
        L4e:
            r3 = r2
            goto L6b
        L50:
            if (r2 != 0) goto L69
            java.lang.String r2 = r0.toString()
            java.lang.String r4 = "file"
            java.lang.String r4 = org.telegram.messenger.MediaController.copyFileToCache(r0, r4)
            if (r4 != 0) goto L62
            r1.showAttachmentError()
            return
        L62:
            r20 = r3
            r3 = r2
            r2 = r4
            r4 = r20
            goto L6b
        L69:
            r4 = r3
            goto L4e
        L6b:
            if (r4 == 0) goto L8b
            org.telegram.messenger.AccountInstance r2 = r1.getAccountInstance()
            long r7 = r1.dialogId
            r18 = 0
            r19 = 0
            r1 = r2
            r2 = 0
            r3 = 0
            r5 = 0
            r6 = 0
            r9 = 0
            r10 = 0
            r12 = 0
            r13 = 0
            r14 = 1
            r15 = 0
            r16 = 0
            r17 = 0
            r4 = r0
            org.telegram.messenger.SendMessagesHelper.prepareSendingDocument(r1, r2, r3, r4, r5, r6, r7, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19)
            goto La6
        L8b:
            org.telegram.messenger.AccountInstance r0 = r1.getAccountInstance()
            long r7 = r1.dialogId
            r18 = 0
            r19 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r9 = 0
            r10 = 0
            r12 = 0
            r13 = 0
            r14 = 1
            r15 = 0
            r16 = 0
            r17 = 0
            r1 = r0
            org.telegram.messenger.SendMessagesHelper.prepareSendingDocument(r1, r2, r3, r4, r5, r6, r7, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19)
        La6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Stories.PeerStoriesView.sendUriAsDocument(android.net.Uri):void");
    }

    private void showAttachmentError() {
        BulletinFactory.m1142of(this.storyContainer, this.resourcesProvider).createErrorBulletin(LocaleController.getString(C2797R.string.UnsupportedAttachment), this.resourcesProvider).show();
    }

    public void setLongpressed(boolean z) {
        if (this.isActive) {
            this.isLongPressed = z;
            invalidate();
        }
    }

    public boolean showKeyboard() {
        TextView textView;
        EditTextCaption editField;
        if (this.chatActivityEnterView == null || (((textView = this.replyDisabledTextView) != null && textView.getVisibility() == 0) || (editField = this.chatActivityEnterView.getEditField()) == null)) {
            return false;
        }
        editField.requestFocus();
        AndroidUtilities.showKeyboard(editField);
        return true;
    }

    public void checkPinchToZoom(MotionEvent motionEvent) {
        this.pinchToZoomHelper.checkPinchToZoom(motionEvent, this.storyContainer, null, null, null, null);
    }

    public void setIsVisible(boolean z) {
        if (this.isVisible == z) {
            return;
        }
        this.isVisible = z;
        if (z) {
            this.imageReceiver.setCurrentAlpha(1.0f);
            checkStealthMode(false);
        }
    }

    public ArrayList<TL_stories.StoryItem> getStoryItems() {
        return this.storyItems;
    }

    public void selectPosition(int i) {
        if (this.selectedPosition != i) {
            this.selectedPosition = i;
            updatePosition();
        }
    }

    public void cancelTouch() {
        this.storyCaptionView.cancelTouch();
    }

    public void onActionDown(MotionEvent motionEvent) {
        HintView2 hintView2 = this.privacyHint;
        if (hintView2 != null && hintView2.shown() && this.privacyButton != null && !this.privacyHint.containsTouch(motionEvent, getX() + this.storyContainer.getX() + this.privacyHint.getX(), getY() + this.storyContainer.getY() + this.privacyHint.getY()) && !hitButton(this.privacyButton, motionEvent)) {
            this.privacyHint.hide();
        }
        HintView2 hintView22 = this.soundTooltip;
        if (hintView22 == null || !hintView22.shown() || this.muteIconContainer == null || this.soundTooltip.containsTouch(motionEvent, getX() + this.storyContainer.getX() + this.soundTooltip.getX(), getY() + this.storyContainer.getY() + this.soundTooltip.getY()) || hitButton(this.muteIconContainer, motionEvent)) {
            return;
        }
        this.soundTooltip.hide();
    }

    private boolean hitButton(View view, MotionEvent motionEvent) {
        float x = getX() + this.storyContainer.getX() + view.getX();
        float y = getY() + this.storyContainer.getY() + view.getY();
        return motionEvent.getX() >= x && motionEvent.getX() <= x + ((float) view.getWidth()) && motionEvent.getY() >= y && motionEvent.getY() <= y + ((float) view.getHeight());
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$34 */
    public class RunnableC691834 implements Runnable {
        public RunnableC691834() {
        }

        @Override // java.lang.Runnable
        public void run() {
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            if (peerStoriesView.isActive && peerStoriesView.allowDrawSurface) {
                PeerStoriesView.this.delegate.setIsSwiping(false);
            }
        }
    }

    public void setOffset(float f) {
        boolean z = f == 0.0f;
        if (this.allowDrawSurface != z) {
            this.allowDrawSurface = z;
            this.storyContainer.invalidate();
            if (this.isActive && useSurfaceInViewPagerWorkAround()) {
                Runnable runnable = this.allowDrawSurfaceRunnable;
                if (z) {
                    AndroidUtilities.cancelRunOnUIThread(runnable);
                    AndroidUtilities.runOnUIThread(this.allowDrawSurfaceRunnable, 250L);
                } else {
                    AndroidUtilities.cancelRunOnUIThread(runnable);
                    this.delegate.setIsSwiping(true);
                }
            }
        }
    }

    public boolean useSurfaceInViewPagerWorkAround() {
        return this.storyViewer.USE_SURFACE_VIEW && Build.VERSION.SDK_INT < 33;
    }

    public void showNoSoundHint(boolean z) {
        if (this.soundTooltip == null) {
            HintView2 joint = new HintView2(getContext(), 1).setJoint(1.0f, -56.0f);
            this.soundTooltip = joint;
            joint.setPadding(AndroidUtilities.m1036dp(8.0f), 0, AndroidUtilities.m1036dp(8.0f), 0);
            this.storyContainer.addView(this.soundTooltip, LayoutHelper.createFrame(-1, -2.0f, 55, 0.0f, 52.0f, 0.0f, 0.0f));
        }
        this.soundTooltip.setText(LocaleController.getString(z ? C2797R.string.StoryNoSound : C2797R.string.StoryTapToSound));
        this.soundTooltip.show();
    }

    public boolean checkTextSelectionEvent(MotionEvent motionEvent) {
        if (!this.storyCaptionView.textSelectionHelper.isInSelectionMode()) {
            return false;
        }
        float x = getX();
        float y = getY() + ((View) getParent()).getY();
        motionEvent.offsetLocation(-x, -y);
        if (this.storyCaptionView.textSelectionHelper.getOverlayView(getContext()).onTouchEvent(motionEvent)) {
            return true;
        }
        motionEvent.offsetLocation(x, y);
        return false;
    }

    public void cancelTextSelection() {
        if (this.storyCaptionView.textSelectionHelper.isInSelectionMode()) {
            this.storyCaptionView.textSelectionHelper.clear();
        }
    }

    public boolean checkReactionEvent(MotionEvent motionEvent) {
        ReactionsContainerLayout reactionsContainerLayout = this.likesReactionLayout;
        if (reactionsContainerLayout == null) {
            return false;
        }
        float x = 0.0f;
        float y = 0.0f;
        for (View view = this; view != null && (view.getParent() instanceof View); view = (View) view.getParent()) {
            x += view.getX();
            y += view.getY();
        }
        if (this.likesReactionLayout.getReactionsWindow() != null && this.likesReactionLayout.getReactionsWindow().windowView != null) {
            motionEvent.offsetLocation(-x, (-y) - this.likesReactionLayout.getReactionsWindow().windowView.getTranslationY());
            this.likesReactionLayout.getReactionsWindow().windowView.dispatchTouchEvent(motionEvent);
            return true;
        }
        Rect rect = AndroidUtilities.rectTmp2;
        reactionsContainerLayout.getHitRect(rect);
        rect.offset((int) x, (int) y);
        if (motionEvent.getAction() == 0 && !rect.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
            showLikesReaction(false);
            return true;
        }
        motionEvent.offsetLocation(-rect.left, -rect.top);
        reactionsContainerLayout.dispatchTouchEvent(motionEvent);
        return true;
    }

    public boolean viewsAllowed() {
        if (this.currentStory.isLive) {
            return false;
        }
        if (this.isSelf) {
            return true;
        }
        return this.isChannel && this.userCanSeeViews;
    }

    public static class PeerHeaderView extends FrameLayout {
        public BackupImageView backupImageView;
        private float progressToUploading;
        RadialProgress radialProgress;
        Paint radialProgressPaint;
        StoryItemHolder storyItemHolder;
        private ValueAnimator subtitleAnimator;
        private TextView[] subtitleView;
        public SimpleTextView titleView;
        private boolean uploadedTooFast;
        private boolean uploading;

        public PeerHeaderView(Context context, StoryItemHolder storyItemHolder) {
            super(context);
            this.subtitleView = new TextView[2];
            this.storyItemHolder = storyItemHolder;
            C69321 c69321 = new BackupImageView(context) { // from class: org.telegram.ui.Stories.PeerStoriesView.PeerHeaderView.1
                public C69321(Context context2) {
                    super(context2);
                }

                @Override // org.telegram.p035ui.Components.BackupImageView, android.view.View
                public void onDraw(Canvas canvas) {
                    if (this.imageReceiver.getVisible()) {
                        RectF rectF = AndroidUtilities.rectTmp;
                        rectF.set(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight());
                        PeerHeaderView.this.drawUploadingProgress(canvas, rectF, true, 1.0f);
                    }
                    super.onDraw(canvas);
                }
            };
            this.backupImageView = c69321;
            c69321.setRoundRadius(ExteraConfig.getAvatarCorners(32.0f));
            addView(this.backupImageView, LayoutHelper.createFrame(32, 32.0f, 0, 12.0f, 2.0f, 0.0f, 0.0f));
            setClipChildren(false);
            C69332 c69332 = new SimpleTextView(context2) { // from class: org.telegram.ui.Stories.PeerStoriesView.PeerHeaderView.2
                public C69332(Context context2) {
                    super(context2);
                }

                @Override // org.telegram.p035ui.ActionBar.SimpleTextView, android.view.View
                public void onMeasure(int i, int i2) {
                    super.onMeasure(i, i2);
                    setPivotY(getMeasuredHeight() / 2.0f);
                }
            };
            this.titleView = c69332;
            c69332.setTextSize(14);
            this.titleView.setTypeface(AndroidUtilities.bold());
            this.titleView.setMaxLines(1);
            this.titleView.setEllipsizeByGradient(AndroidUtilities.m1036dp(4.0f));
            this.titleView.setPivotX(0.0f);
            NotificationCenter.listenEmojiLoading(this.titleView);
            addView(this.titleView, LayoutHelper.createFrame(-2, -2.0f, 0, 54.0f, 0.0f, 86.0f, 0.0f));
            for (int i = 0; i < 2; i++) {
                this.subtitleView[i] = new TextView(context2);
                this.subtitleView[i].setTextSize(1, 12.0f);
                this.subtitleView[i].setMaxLines(1);
                this.subtitleView[i].setSingleLine(true);
                this.subtitleView[i].setEllipsize(TextUtils.TruncateAt.MIDDLE);
                this.subtitleView[i].setTextColor(-1);
                this.subtitleView[i].setPadding(AndroidUtilities.m1036dp(3.0f), 0, AndroidUtilities.m1036dp(3.0f), AndroidUtilities.m1036dp(1.0f));
                addView(this.subtitleView[i], LayoutHelper.createFrame(-2, -2.0f, 0, 51.0f, 18.0f, 83.0f, 0.0f));
            }
            this.titleView.setTextColor(-1);
        }

        /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$PeerHeaderView$1 */
        public class C69321 extends BackupImageView {
            public C69321(Context context2) {
                super(context2);
            }

            @Override // org.telegram.p035ui.Components.BackupImageView, android.view.View
            public void onDraw(Canvas canvas) {
                if (this.imageReceiver.getVisible()) {
                    RectF rectF = AndroidUtilities.rectTmp;
                    rectF.set(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight());
                    PeerHeaderView.this.drawUploadingProgress(canvas, rectF, true, 1.0f);
                }
                super.onDraw(canvas);
            }
        }

        /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$PeerHeaderView$2 */
        public class C69332 extends SimpleTextView {
            public C69332(Context context2) {
                super(context2);
            }

            @Override // org.telegram.p035ui.ActionBar.SimpleTextView, android.view.View
            public void onMeasure(int i, int i2) {
                super.onMeasure(i, i2);
                setPivotY(getMeasuredHeight() / 2.0f);
            }
        }

        public void setSubtitle(CharSequence charSequence) {
            setSubtitle(charSequence, false);
        }

        public void setOnSubtitleClick(View.OnClickListener onClickListener) {
            this.subtitleView[0].setOnClickListener(onClickListener);
            this.subtitleView[0].setClickable(onClickListener != null);
            this.subtitleView[0].setBackground(onClickListener == null ? null : Theme.createSelectorDrawable(822083583, 7));
        }

        public void setSubtitle(CharSequence charSequence, boolean z) {
            ValueAnimator valueAnimator = this.subtitleAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
                this.subtitleAnimator = null;
            }
            TextView[] textViewArr = this.subtitleView;
            if (z) {
                textViewArr[1].setOnClickListener(null);
                TextView[] textViewArr2 = this.subtitleView;
                textViewArr2[1].setText(textViewArr2[0].getText());
                this.subtitleView[1].setVisibility(0);
                this.subtitleView[1].setAlpha(1.0f);
                this.subtitleView[1].setTranslationY(0.0f);
                this.subtitleView[0].setText(charSequence);
                this.subtitleView[0].setVisibility(0);
                this.subtitleView[0].setAlpha(0.0f);
                this.subtitleView[0].setTranslationY(-AndroidUtilities.m1036dp(4.0f));
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                this.subtitleAnimator = valueAnimatorOfFloat;
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$PeerHeaderView$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        this.f$0.lambda$setSubtitle$0(valueAnimator2);
                    }
                });
                this.subtitleAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Stories.PeerStoriesView.PeerHeaderView.3
                    public C69343() {
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        PeerHeaderView.this.subtitleView[1].setVisibility(8);
                        PeerHeaderView.this.subtitleView[0].setAlpha(1.0f);
                        PeerHeaderView.this.subtitleView[0].setTranslationY(0.0f);
                    }
                });
                this.subtitleAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                this.subtitleAnimator.setDuration(340L);
                this.subtitleAnimator.start();
                return;
            }
            textViewArr[0].setVisibility(0);
            this.subtitleView[0].setAlpha(1.0f);
            this.subtitleView[0].setText(charSequence);
            this.subtitleView[1].setVisibility(8);
            this.subtitleView[1].setAlpha(0.0f);
        }

        public /* synthetic */ void lambda$setSubtitle$0(ValueAnimator valueAnimator) {
            float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            this.subtitleView[0].setAlpha(fFloatValue);
            float f = 1.0f - fFloatValue;
            this.subtitleView[0].setTranslationY((-AndroidUtilities.m1036dp(4.0f)) * f);
            this.subtitleView[1].setAlpha(f);
            this.subtitleView[1].setTranslationY(fFloatValue * AndroidUtilities.m1036dp(4.0f));
        }

        /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$PeerHeaderView$3 */
        public class C69343 extends AnimatorListenerAdapter {
            public C69343() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                PeerHeaderView.this.subtitleView[1].setVisibility(8);
                PeerHeaderView.this.subtitleView[0].setAlpha(1.0f);
                PeerHeaderView.this.subtitleView[0].setTranslationY(0.0f);
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            if (isEnabled()) {
                return super.dispatchTouchEvent(motionEvent);
            }
            return false;
        }

        public void drawUploadingProgress(Canvas canvas, RectF rectF, boolean z, float f) {
            boolean z2;
            float f2;
            StoriesController.UploadingStory uploadingStory;
            StoryItemHolder storyItemHolder = this.storyItemHolder;
            if ((storyItemHolder == null || storyItemHolder.uploadingStory == null) && this.progressToUploading == 0.0f) {
                return;
            }
            if (storyItemHolder != null && (uploadingStory = storyItemHolder.uploadingStory) != null && !uploadingStory.failed) {
                this.progressToUploading = 1.0f;
                f2 = uploadingStory.progress;
                if (!this.uploading) {
                    this.uploading = true;
                }
                z2 = false;
            } else {
                if (this.uploading) {
                    this.uploading = false;
                    this.uploadedTooFast = this.radialProgress.getAnimatedProgress() < 0.2f;
                }
                if (!this.uploadedTooFast) {
                    this.progressToUploading = Utilities.clamp(this.progressToUploading - ((1000.0f / AndroidUtilities.screenRefreshRate) / 300.0f), 1.0f, 0.0f);
                }
                z2 = true;
                f2 = 1.0f;
            }
            if (this.radialProgress == null) {
                RadialProgress radialProgress = new RadialProgress(this.backupImageView);
                this.radialProgress = radialProgress;
                radialProgress.setBackground(null, true, false);
            }
            this.radialProgress.setDiff(0);
            ImageReceiver imageReceiver = this.backupImageView.getImageReceiver();
            float fM1036dp = AndroidUtilities.m1036dp(3.0f) - (AndroidUtilities.m1036dp(6.0f) * (1.0f - this.progressToUploading));
            this.radialProgress.setProgressRect((int) (rectF.left - fM1036dp), (int) (rectF.top - fM1036dp), (int) (rectF.right + fM1036dp), (int) (rectF.bottom + fM1036dp));
            this.radialProgress.setProgress(z2 ? 1.0f : Utilities.clamp(f2, 1.0f, 0.0f), true);
            if (this.uploadedTooFast && z2 && this.radialProgress.getAnimatedProgress() >= 0.9f) {
                this.progressToUploading = Utilities.clamp(this.progressToUploading - ((1000.0f / AndroidUtilities.screenRefreshRate) / 300.0f), 1.0f, 0.0f);
            }
            if (z) {
                if (f != 1.0f) {
                    Paint unreadCirclePaint = StoriesUtilities.getUnreadCirclePaint(imageReceiver, false);
                    unreadCirclePaint.setAlpha((int) (this.progressToUploading * 255.0f));
                    this.radialProgress.setPaint(unreadCirclePaint);
                    this.radialProgress.draw(canvas);
                }
                if (this.radialProgressPaint == null) {
                    Paint paint = new Paint(1);
                    this.radialProgressPaint = paint;
                    paint.setColor(-1);
                    this.radialProgressPaint.setStrokeWidth(AndroidUtilities.m1036dp(2.0f));
                    this.radialProgressPaint.setStyle(Paint.Style.STROKE);
                    this.radialProgressPaint.setStrokeCap(Paint.Cap.ROUND);
                }
                this.radialProgressPaint.setAlpha((int) (255.0f * f * this.progressToUploading));
                this.radialProgress.setPaint(this.radialProgressPaint);
                this.radialProgress.draw(canvas);
            }
        }
    }

    public int getStoriesCount() {
        return this.uploadingStories.size() + Math.max(this.totalStoriesCount, this.storyItems.size());
    }

    public class StoryItemHolder {
        public CharSequence caption;
        public boolean captionTranslated;
        public TL_stories.StoryItem editingSourceItem;
        private boolean isLive;
        private boolean isVideo;
        private StoryCaptionView.Panel musicPanel;
        private StoryCaptionView.Panel panel;
        boolean skipped;
        public TL_stories.StoryItem storyItem = null;
        public StoriesController.UploadingStory uploadingStory = null;

        public StoryItemHolder() {
        }

        public boolean isLive() {
            return this.isLive;
        }

        public boolean isThisCall(long j) {
            TL_stories.StoryItem storyItem = this.storyItem;
            if (storyItem == null) {
                return false;
            }
            TLRPC.MessageMedia messageMedia = storyItem.media;
            return (messageMedia instanceof TLRPC.TL_messageMediaVideoStream) && j == ((TLRPC.TL_messageMediaVideoStream) messageMedia).call.f1267id;
        }

        public String getMediaDebugString() {
            TLRPC.MessageMedia messageMedia;
            TL_stories.StoryItem storyItem = this.storyItem;
            if (storyItem != null && (messageMedia = storyItem.media) != null) {
                if (messageMedia.photo != null) {
                    return "photo#" + this.storyItem.media.photo.f1276id + "at" + this.storyItem.media.photo.dc_id + "dc";
                }
                if (messageMedia.document != null) {
                    return "doc#" + this.storyItem.media.document.f1253id + "at" + this.storyItem.media.document.dc_id + "dc";
                }
                return "unknown";
            }
            if (this.uploadingStory != null) {
                return "uploading from " + this.uploadingStory.path;
            }
            return "unknown";
        }

        public StoryCaptionView.Panel getMusic() {
            TL_stories.StoryItem storyItem;
            if (this.musicPanel == null && (storyItem = this.storyItem) != null) {
                this.musicPanel = StoryCaptionView.Panel.from(storyItem.music);
            }
            return this.musicPanel;
        }

        public StoryCaptionView.Panel getReply() {
            if (this.panel == null) {
                if (this.storyItem != null) {
                    this.panel = StoryCaptionView.Panel.from(PeerStoriesView.this.currentAccount, this.storyItem);
                } else {
                    StoriesController.UploadingStory uploadingStory = this.uploadingStory;
                    if (uploadingStory != null) {
                        this.panel = StoryCaptionView.Panel.from(uploadingStory);
                    }
                }
            }
            return this.panel;
        }

        public void updateCaption() {
            int i;
            this.captionTranslated = false;
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            StoryItemHolder storyItemHolder = peerStoriesView.currentStory;
            StoriesController.UploadingStory uploadingStory = storyItemHolder.uploadingStory;
            if (uploadingStory != null) {
                CharSequence charSequence = uploadingStory.entry.caption;
                this.caption = charSequence;
                CharSequence charSequenceReplaceEmoji = Emoji.replaceEmoji(charSequence, peerStoriesView.storyCaptionView.captionTextview.getPaint().getFontMetricsInt(), false);
                this.caption = charSequenceReplaceEmoji;
                SpannableStringBuilder spannableStringBuilder = charSequenceReplaceEmoji == null ? new SpannableStringBuilder() : SpannableStringBuilder.valueOf(charSequenceReplaceEmoji);
                TLRPC.User user = MessagesController.getInstance(PeerStoriesView.this.currentAccount).getUser(Long.valueOf(PeerStoriesView.this.dialogId));
                if (PeerStoriesView.this.dialogId < 0 || MessagesController.getInstance(PeerStoriesView.this.currentAccount).storyEntitiesAllowed(user)) {
                    MessageObject.addLinks(true, spannableStringBuilder);
                    return;
                }
                return;
            }
            TL_stories.StoryItem storyItem = storyItemHolder.storyItem;
            if (storyItem != null) {
                if (storyItem.translated && storyItem.translatedText != null && TextUtils.equals(storyItem.translatedLng, TranslateAlert2.getToLanguage())) {
                    this.captionTranslated = true;
                    PeerStoriesView peerStoriesView2 = PeerStoriesView.this;
                    TLRPC.TL_textWithEntities tL_textWithEntities = peerStoriesView2.currentStory.storyItem.translatedText;
                    String str = tL_textWithEntities.text;
                    this.caption = str;
                    CharSequence charSequenceReplaceEmoji2 = Emoji.replaceEmoji(str, peerStoriesView2.storyCaptionView.captionTextview.getPaint().getFontMetricsInt(), false);
                    this.caption = charSequenceReplaceEmoji2;
                    if (charSequenceReplaceEmoji2 == null || tL_textWithEntities.entities == null) {
                        return;
                    }
                    SpannableStringBuilder spannableStringBuilderValueOf = SpannableStringBuilder.valueOf(MessageObject.replaceAnimatedEmoji(new SpannableStringBuilder(tL_textWithEntities.text), tL_textWithEntities.entities, PeerStoriesView.this.storyCaptionView.captionTextview.getPaint().getFontMetricsInt(), false));
                    SpannableStringBuilder.valueOf(Emoji.replaceEmoji(spannableStringBuilderValueOf, PeerStoriesView.this.storyCaptionView.captionTextview.getPaint().getFontMetricsInt(), false));
                    i = (PeerStoriesView.this.dialogId < 0 || MessagesController.getInstance(PeerStoriesView.this.currentAccount).storyEntitiesAllowed(MessagesController.getInstance(PeerStoriesView.this.currentAccount).getUser(Long.valueOf(PeerStoriesView.this.dialogId)))) ? 1 : 0;
                    if (i != 0) {
                        MessageObject.addLinks(true, spannableStringBuilderValueOf);
                    }
                    MessageObject.addEntitiesToText(spannableStringBuilderValueOf, tL_textWithEntities.entities, false, true, true, false, i ^ 1);
                    this.caption = spannableStringBuilderValueOf;
                    return;
                }
                PeerStoriesView peerStoriesView3 = PeerStoriesView.this;
                String str2 = peerStoriesView3.currentStory.storyItem.caption;
                this.caption = str2;
                CharSequence charSequenceReplaceEmoji3 = Emoji.replaceEmoji(str2, peerStoriesView3.storyCaptionView.captionTextview.getPaint().getFontMetricsInt(), false);
                this.caption = charSequenceReplaceEmoji3;
                if (charSequenceReplaceEmoji3 == null || PeerStoriesView.this.currentStory.storyItem.entities == null) {
                    return;
                }
                SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(PeerStoriesView.this.currentStory.storyItem.caption);
                PeerStoriesView peerStoriesView4 = PeerStoriesView.this;
                SpannableStringBuilder spannableStringBuilderValueOf2 = SpannableStringBuilder.valueOf(MessageObject.replaceAnimatedEmoji(spannableStringBuilder2, peerStoriesView4.currentStory.storyItem.entities, peerStoriesView4.storyCaptionView.captionTextview.getPaint().getFontMetricsInt(), false));
                SpannableStringBuilder.valueOf(Emoji.replaceEmoji(spannableStringBuilderValueOf2, PeerStoriesView.this.storyCaptionView.captionTextview.getPaint().getFontMetricsInt(), false));
                i = (PeerStoriesView.this.dialogId < 0 || MessagesController.getInstance(PeerStoriesView.this.currentAccount).storyEntitiesAllowed(MessagesController.getInstance(PeerStoriesView.this.currentAccount).getUser(Long.valueOf(PeerStoriesView.this.dialogId)))) ? 1 : 0;
                if (i != 0) {
                    MessageObject.addLinks(true, spannableStringBuilderValueOf2);
                }
                MessageObject.addEntitiesToText(spannableStringBuilderValueOf2, PeerStoriesView.this.currentStory.storyItem.entities, false, true, true, false, i ^ 1);
                this.caption = spannableStringBuilderValueOf2;
            }
        }

        public void set(TL_stories.StoryItem storyItem) {
            this.storyItem = storyItem;
            this.panel = null;
            this.musicPanel = null;
            this.uploadingStory = null;
            this.skipped = storyItem instanceof TL_stories.TL_storyItemSkipped;
            this.isVideo = isVideoInternal();
            this.isLive = isLiveInternal();
        }

        private boolean isLiveInternal() {
            TLRPC.MessageMedia messageMedia;
            TL_stories.StoryItem storyItem = this.storyItem;
            return (storyItem == null || (messageMedia = storyItem.media) == null || !(messageMedia instanceof TLRPC.TL_messageMediaVideoStream)) ? false : true;
        }

        private boolean isVideoInternal() {
            String str;
            TLRPC.MessageMedia messageMedia;
            StoriesController.UploadingStory uploadingStory = this.uploadingStory;
            if (uploadingStory != null) {
                return uploadingStory.isVideo;
            }
            TL_stories.StoryItem storyItem = this.storyItem;
            if (storyItem != null && (messageMedia = storyItem.media) != null && messageMedia.getDocument() != null) {
                TLRPC.Document document = this.storyItem.media.getDocument();
                return MessageObject.isVideoDocument(document) || "video/mp4".equals(document.mime_type);
            }
            TL_stories.StoryItem storyItem2 = this.storyItem;
            if (storyItem2 == null || storyItem2.media != null || (str = storyItem2.attachPath) == null) {
                return false;
            }
            return str.toLowerCase().endsWith(".mp4");
        }

        public void set(StoriesController.UploadingStory uploadingStory) {
            this.uploadingStory = uploadingStory;
            this.panel = null;
            this.musicPanel = null;
            this.storyItem = null;
            this.skipped = false;
            this.isVideo = isVideoInternal();
            this.isLive = isLiveInternal();
        }

        public void clear() {
            this.uploadingStory = null;
            this.storyItem = null;
        }

        public void cancelOrDelete() {
            TL_stories.StoryItem storyItem = this.storyItem;
            if (storyItem instanceof StoriesController.BotPreview) {
                ((StoriesController.BotPreview) storyItem).list.delete(storyItem.media);
                return;
            }
            if (storyItem != null) {
                PeerStoriesView peerStoriesView = PeerStoriesView.this;
                peerStoriesView.storiesController.deleteStory(peerStoriesView.dialogId, this.storyItem);
            } else {
                StoriesController.UploadingStory uploadingStory = this.uploadingStory;
                if (uploadingStory != null) {
                    uploadingStory.cancel();
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:69:0x005a, code lost:
        
            if (org.telegram.p035ui.Stories.PeerStoriesView.this.isSelf != false) goto L70;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void checkSendView() {
            /*
                r6 = this;
                org.telegram.ui.Stories.PeerStoriesView r0 = org.telegram.p035ui.Stories.PeerStoriesView.this
                org.telegram.tgnet.tl.TL_stories$PeerStories r1 = r0.userStories
                if (r1 != 0) goto L2a
                org.telegram.ui.Stories.StoriesController r1 = r0.storiesController
                long r2 = org.telegram.p035ui.Stories.PeerStoriesView.m20584$$Nest$fgetdialogId(r0)
                org.telegram.tgnet.tl.TL_stories$PeerStories r1 = r1.getStories(r2)
                if (r1 != 0) goto L2a
                org.telegram.ui.Stories.PeerStoriesView r0 = org.telegram.p035ui.Stories.PeerStoriesView.this
                int r0 = org.telegram.p035ui.Stories.PeerStoriesView.m20582$$Nest$fgetcurrentAccount(r0)
                org.telegram.messenger.MessagesController r0 = org.telegram.messenger.MessagesController.getInstance(r0)
                org.telegram.ui.Stories.PeerStoriesView r2 = org.telegram.p035ui.Stories.PeerStoriesView.this
                long r2 = org.telegram.p035ui.Stories.PeerStoriesView.m20584$$Nest$fgetdialogId(r2)
                org.telegram.tgnet.TLRPC$UserFull r0 = r0.getUserFull(r2)
                if (r0 == 0) goto L2a
                org.telegram.tgnet.tl.TL_stories$PeerStories r1 = r0.stories
            L2a:
                org.telegram.ui.Stories.PeerStoriesView r0 = org.telegram.p035ui.Stories.PeerStoriesView.this
                boolean r0 = r0.isActive
                r2 = 1
                if (r0 == 0) goto L98
                org.telegram.tgnet.tl.TL_stories$StoryItem r0 = r6.storyItem
                if (r0 == 0) goto L98
                if (r1 == 0) goto L98
                boolean r0 = org.telegram.p035ui.Stories.StoriesUtilities.hasExpiredViews(r0)
                if (r0 != 0) goto L56
                org.telegram.tgnet.tl.TL_stories$StoryItem r0 = r6.storyItem
                int r0 = r0.f1454id
                int r1 = r1.max_read_id
                if (r0 > r1) goto L5c
                org.telegram.ui.Stories.PeerStoriesView r1 = org.telegram.p035ui.Stories.PeerStoriesView.this
                org.telegram.ui.Stories.StoriesController r3 = r1.storiesController
                org.telegram.messenger.support.LongSparseIntArray r3 = r3.dialogIdToMaxReadId
                long r4 = org.telegram.p035ui.Stories.PeerStoriesView.m20584$$Nest$fgetdialogId(r1)
                r1 = 0
                int r1 = r3.get(r4, r1)
                if (r0 > r1) goto L5c
            L56:
                org.telegram.ui.Stories.PeerStoriesView r0 = org.telegram.p035ui.Stories.PeerStoriesView.this
                boolean r0 = r0.isSelf
                if (r0 == 0) goto L98
            L5c:
                org.telegram.ui.Stories.PeerStoriesView r0 = org.telegram.p035ui.Stories.PeerStoriesView.this
                org.telegram.ui.Stories.StoryViewer r0 = org.telegram.p035ui.Stories.PeerStoriesView.m20645$$Nest$fgetstoryViewer(r0)
                org.telegram.tgnet.tl.TL_stories$PeerStories r0 = r0.overrideUserStories
                org.telegram.ui.Stories.PeerStoriesView r1 = org.telegram.p035ui.Stories.PeerStoriesView.this
                if (r0 == 0) goto L81
                org.telegram.ui.Stories.StoriesController r0 = r1.storiesController
                org.telegram.ui.Stories.StoryViewer r1 = org.telegram.p035ui.Stories.PeerStoriesView.m20645$$Nest$fgetstoryViewer(r1)
                org.telegram.tgnet.tl.TL_stories$PeerStories r1 = r1.overrideUserStories
                org.telegram.tgnet.tl.TL_stories$StoryItem r3 = r6.storyItem
                boolean r0 = r0.markStoryAsRead(r1, r3, r2)
                if (r0 == 0) goto Lc4
                org.telegram.ui.Stories.PeerStoriesView r6 = org.telegram.p035ui.Stories.PeerStoriesView.this
                org.telegram.ui.Stories.StoryViewer r6 = org.telegram.p035ui.Stories.PeerStoriesView.m20645$$Nest$fgetstoryViewer(r6)
                r6.unreadStateChanged = r2
                return
            L81:
                org.telegram.ui.Stories.StoriesController r0 = r1.storiesController
                long r3 = org.telegram.p035ui.Stories.PeerStoriesView.m20584$$Nest$fgetdialogId(r1)
                org.telegram.tgnet.tl.TL_stories$StoryItem r1 = r6.storyItem
                boolean r0 = r0.markStoryAsRead(r3, r1)
                if (r0 == 0) goto Lc4
                org.telegram.ui.Stories.PeerStoriesView r6 = org.telegram.p035ui.Stories.PeerStoriesView.this
                org.telegram.ui.Stories.StoryViewer r6 = org.telegram.p035ui.Stories.PeerStoriesView.m20645$$Nest$fgetstoryViewer(r6)
                r6.unreadStateChanged = r2
                return
            L98:
                org.telegram.ui.Stories.PeerStoriesView r0 = org.telegram.p035ui.Stories.PeerStoriesView.this
                boolean r1 = r0.isActive
                if (r1 == 0) goto Lc4
                org.telegram.tgnet.tl.TL_stories$StoryItem r1 = r6.storyItem
                if (r1 == 0) goto Lc4
                org.telegram.ui.Stories.StoryViewer r0 = org.telegram.p035ui.Stories.PeerStoriesView.m20645$$Nest$fgetstoryViewer(r0)
                org.telegram.ui.Stories.StoriesController$StoriesList r0 = r0.storiesList
                if (r0 == 0) goto Lc4
                org.telegram.ui.Stories.PeerStoriesView r0 = org.telegram.p035ui.Stories.PeerStoriesView.this
                org.telegram.ui.Stories.StoryViewer r0 = org.telegram.p035ui.Stories.PeerStoriesView.m20645$$Nest$fgetstoryViewer(r0)
                org.telegram.ui.Stories.StoriesController$StoriesList r0 = r0.storiesList
                org.telegram.tgnet.tl.TL_stories$StoryItem r1 = r6.storyItem
                int r1 = r1.f1454id
                boolean r0 = r0.markAsRead(r1)
                if (r0 == 0) goto Lc4
                org.telegram.ui.Stories.PeerStoriesView r6 = org.telegram.p035ui.Stories.PeerStoriesView.this
                org.telegram.ui.Stories.StoryViewer r6 = org.telegram.p035ui.Stories.PeerStoriesView.m20645$$Nest$fgetstoryViewer(r6)
                r6.unreadStateChanged = r2
            Lc4:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Stories.PeerStoriesView.StoryItemHolder.checkSendView():void");
        }

        public String getLocalPath() {
            TL_stories.StoryItem storyItem = this.storyItem;
            if (storyItem != null) {
                return storyItem.attachPath;
            }
            return null;
        }

        public boolean isVideo() {
            return this.isVideo;
        }

        public boolean hasSound() {
            TLRPC.MessageMedia messageMedia;
            TLRPC.Document document;
            if (!this.isVideo) {
                return false;
            }
            TL_stories.StoryItem storyItem = this.storyItem;
            if (storyItem != null && (messageMedia = storyItem.media) != null && (document = messageMedia.getDocument()) != null) {
                for (int i = 0; i < document.attributes.size(); i++) {
                    TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i);
                    if ((documentAttribute instanceof TLRPC.TL_documentAttributeVideo) && documentAttribute.nosound) {
                        return false;
                    }
                }
                return true;
            }
            if (this.uploadingStory != null) {
                return !r5.entry.muted;
            }
            return true;
        }

        public String createLink() {
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            if (peerStoriesView.currentStory.storyItem == null) {
                return null;
            }
            long j = peerStoriesView.dialogId;
            PeerStoriesView peerStoriesView2 = PeerStoriesView.this;
            if (j > 0) {
                TLRPC.User user = MessagesController.getInstance(peerStoriesView2.currentAccount).getUser(Long.valueOf(PeerStoriesView.this.dialogId));
                if (UserObject.getPublicUsername(user) == null) {
                    return null;
                }
                if (!PeerStoriesView.this.currentStory.isLive) {
                    return String.format(Locale.US, "https://t.me/%1$s/s/%2$s", UserObject.getPublicUsername(user), Integer.valueOf(PeerStoriesView.this.currentStory.storyItem.f1454id));
                }
                return String.format(Locale.US, "https://t.me/%1$s/s/live", UserObject.getPublicUsername(user));
            }
            TLRPC.Chat chat = MessagesController.getInstance(peerStoriesView2.currentAccount).getChat(Long.valueOf(-PeerStoriesView.this.dialogId));
            if (ChatObject.getPublicUsername(chat) == null) {
                return null;
            }
            if (!PeerStoriesView.this.currentStory.isLive) {
                return String.format(Locale.US, "https://t.me/%1$s/s/%2$s", ChatObject.getPublicUsername(chat), Integer.valueOf(PeerStoriesView.this.currentStory.storyItem.f1454id));
            }
            return String.format(Locale.US, "https://t.me/%1$s/s/live", ChatObject.getPublicUsername(chat));
        }

        public File getPath() {
            TLRPC.Photo photo;
            if (getLocalPath() != null) {
                return new File(getLocalPath());
            }
            TL_stories.StoryItem storyItem = this.storyItem;
            if (storyItem == null) {
                return null;
            }
            TLRPC.MessageMedia messageMedia = storyItem.media;
            if (messageMedia != null && messageMedia.getDocument() != null) {
                return FileLoader.getInstance(PeerStoriesView.this.currentAccount).getPathToAttach(this.storyItem.media.getDocument());
            }
            TLRPC.MessageMedia messageMedia2 = this.storyItem.media;
            if (messageMedia2 == null || (photo = messageMedia2.photo) == null) {
                return null;
            }
            TLRPC.PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(photo.sizes, Integer.MAX_VALUE);
            File pathToAttach = FileLoader.getInstance(PeerStoriesView.this.currentAccount).getPathToAttach(closestPhotoSizeWithSize, true);
            return !pathToAttach.exists() ? FileLoader.getInstance(PeerStoriesView.this.currentAccount).getPathToAttach(closestPhotoSizeWithSize, false) : pathToAttach;
        }

        public boolean allowScreenshots() {
            StoriesController.UploadingStory uploadingStory = this.uploadingStory;
            if (uploadingStory != null) {
                return uploadingStory.entry.allowScreenshots;
            }
            TL_stories.StoryItem storyItem = this.storyItem;
            if (storyItem == null) {
                return true;
            }
            if (storyItem.noforwards) {
                return false;
            }
            if (!storyItem.pinned) {
                return true;
            }
            TLRPC.Chat chat = MessagesController.getInstance(PeerStoriesView.this.currentAccount).getChat(Long.valueOf(-storyItem.dialogId));
            return chat == null || !chat.noforwards;
        }
    }

    public static int getStoryId(TL_stories.StoryItem storyItem, StoriesController.UploadingStory uploadingStory) {
        StoryEntry storyEntry;
        if (storyItem != null) {
            return storyItem.f1454id;
        }
        if (uploadingStory == null || (storyEntry = uploadingStory.entry) == null) {
            return 0;
        }
        return storyEntry.editStoryId;
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int size;
        char c2;
        ReactionsContainerLayout reactionsContainerLayout;
        MentionsContainerView mentionsContainerView;
        if (this.storyViewer.ATTACH_TO_FRAGMENT) {
            ((FrameLayout.LayoutParams) getLayoutParams()).topMargin = AndroidUtilities.statusBarHeight;
        }
        if (this.isActive && this.shareAlert == null) {
            this.realKeyboardHeight = this.delegate.getKeyboardHeight();
        } else {
            this.realKeyboardHeight = 0;
        }
        if (this.storyViewer.ATTACH_TO_FRAGMENT) {
            size = View.MeasureSpec.getSize(i2);
        } else {
            size = View.MeasureSpec.getSize(i2) + this.realKeyboardHeight;
        }
        int size2 = (int) ((View.MeasureSpec.getSize(i) * 16.0f) / 9.0f);
        if (size <= size2 || size2 > size) {
            size2 = size;
        }
        if (this.realKeyboardHeight < AndroidUtilities.m1036dp(20.0f)) {
            this.realKeyboardHeight = 0;
        }
        int visibleEmojiPadding = this.realKeyboardHeight;
        ReactionsContainerLayout reactionsContainerLayout2 = this.likesReactionLayout;
        if (reactionsContainerLayout2 != null && reactionsContainerLayout2.getReactionsWindow() != null && this.likesReactionLayout.getReactionsWindow().isShowing()) {
            this.likesReactionLayout.getReactionsWindow().windowView.animate().translationY(-this.realKeyboardHeight).setDuration(250L).setInterpolator(AdjustPanLayoutHelper.keyboardInterpolator).start();
            visibleEmojiPadding = 0;
        } else {
            ChatActivityEnterView chatActivityEnterView = this.chatActivityEnterView;
            if (chatActivityEnterView != null && (chatActivityEnterView.isPopupShowing() || this.chatActivityEnterView.isWaitingForKeyboard())) {
                int measuredHeight = this.chatActivityEnterView.getEmojiView().getMeasuredHeight();
                ChatActivityEnterView chatActivityEnterView2 = this.chatActivityEnterView;
                if (measuredHeight == 0) {
                    visibleEmojiPadding = chatActivityEnterView2.getEmojiPadding();
                } else {
                    boolean zIsStickersExpanded = chatActivityEnterView2.isStickersExpanded();
                    ChatActivityEnterView chatActivityEnterView3 = this.chatActivityEnterView;
                    if (zIsStickersExpanded) {
                        chatActivityEnterView3.checkStickresExpandHeight();
                        visibleEmojiPadding = this.chatActivityEnterView.getStickersExpandedHeight();
                    } else {
                        visibleEmojiPadding = chatActivityEnterView3.getVisibleEmojiPadding();
                    }
                }
            }
        }
        boolean z = this.keyboardVisible;
        if (this.lastKeyboardHeight != visibleEmojiPadding) {
            this.keyboardVisible = false;
            if (visibleEmojiPadding > 0 && this.isActive) {
                this.keyboardVisible = true;
                this.messageSent = false;
                this.lastOpenedKeyboardHeight = visibleEmojiPadding;
                checkReactionsLayout();
                ReactionsEffectOverlay.dismissAll();
            } else {
                ChatActivityEnterView chatActivityEnterView4 = this.chatActivityEnterView;
                if (chatActivityEnterView4 != null) {
                    this.storyViewer.saveDraft(this.dialogId, this.currentStory.storyItem, chatActivityEnterView4.getEditText());
                }
            }
            ChatActivityEnterView chatActivityEnterView5 = this.chatActivityEnterView;
            if (chatActivityEnterView5 != null) {
                chatActivityEnterView5.setSuggestionButtonVisible(this.currentStory.isLive && !disabledPaidFeatures(true) && this.keyboardVisible, true);
            }
            if (this.keyboardVisible && (mentionsContainerView = this.mentionContainer) != null) {
                mentionsContainerView.setVisibility(0);
            }
            if (!this.keyboardVisible && (reactionsContainerLayout = this.reactionsContainerLayout) != null) {
                reactionsContainerLayout.reset();
            }
            this.headerView.setEnabled(!this.keyboardVisible);
            ChatActivityEnterView chatActivityEnterView6 = this.chatActivityEnterView;
            if (chatActivityEnterView6 != null) {
                chatActivityEnterView6.checkReactionsButton(!this.keyboardVisible);
            }
            if (this.isActive && this.keyboardVisible) {
                this.delegate.setKeyboardVisible(true);
            }
            this.lastKeyboardHeight = visibleEmojiPadding;
            ValueAnimator valueAnimator = this.keyboardAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            this.notificationsLocker.lock();
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.animatingKeyboardHeight, visibleEmojiPadding);
            this.keyboardAnimator = valueAnimatorOfFloat;
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda22
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    this.f$0.lambda$onMeasure$53(valueAnimator2);
                }
            });
            this.keyboardAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Stories.PeerStoriesView.35
                public C691935() {
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    PeerStoriesView.this.notificationsLocker.unlock();
                    PeerStoriesView.this.animatingKeyboardHeight = r2.lastKeyboardHeight;
                    ChatActivityEnterView chatActivityEnterView7 = PeerStoriesView.this.chatActivityEnterView;
                    if (chatActivityEnterView7 != null) {
                        chatActivityEnterView7.onOverrideAnimationEnd();
                    }
                    PeerStoriesView peerStoriesView = PeerStoriesView.this;
                    if (peerStoriesView.isActive && !peerStoriesView.keyboardVisible) {
                        peerStoriesView.delegate.setKeyboardVisible(false);
                    }
                    PeerStoriesView peerStoriesView2 = PeerStoriesView.this;
                    if (!peerStoriesView2.keyboardVisible && peerStoriesView2.mentionContainer != null) {
                        PeerStoriesView.this.mentionContainer.setVisibility(8);
                    }
                    PeerStoriesView peerStoriesView3 = PeerStoriesView.this;
                    peerStoriesView3.forceUpdateOffsets = true;
                    peerStoriesView3.invalidate();
                }
            });
            boolean z2 = this.keyboardVisible;
            ValueAnimator valueAnimator2 = this.keyboardAnimator;
            if (z2) {
                valueAnimator2.setDuration(250L);
                this.keyboardAnimator.setInterpolator(AdjustPanLayoutHelper.keyboardInterpolator);
                this.storyViewer.cancelSwipeToReply();
            } else {
                valueAnimator2.setDuration(500L);
                this.keyboardAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
            }
            this.keyboardAnimator.start();
            boolean z3 = this.keyboardVisible;
            if (z3 != z) {
                if (z3) {
                    createBlurredBitmap(this.bitmapShaderTools.getCanvas(), this.bitmapShaderTools.getBitmap());
                    if (this.currentStory.isLive) {
                        showPaidMessageHint();
                    }
                } else {
                    ChatActivityEnterView chatActivityEnterView7 = this.chatActivityEnterView;
                    if (chatActivityEnterView7 != null) {
                        chatActivityEnterView7.getEditField().clearFocus();
                    }
                    HintView2 hintView2 = this.highlightMessageHintView;
                    if (hintView2 != null) {
                        hintView2.hide();
                    }
                }
                this.animateKeyboardOpening = true;
            } else {
                this.animateKeyboardOpening = false;
            }
        }
        ChatActivityEnterView chatActivityEnterView8 = this.chatActivityEnterView;
        if (chatActivityEnterView8 != null && chatActivityEnterView8.getEmojiView() != null) {
            ((FrameLayout.LayoutParams) this.chatActivityEnterView.getEmojiView().getLayoutParams()).gravity = 80;
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.storyContainer.getLayoutParams();
        layoutParams.height = size2;
        boolean z4 = size - size2 > AndroidUtilities.m1036dp(64.0f);
        this.BIG_SCREEN = z4;
        int iM1036dp = (size - ((z4 ? AndroidUtilities.m1036dp(64.0f) : 0) + size2)) >> 1;
        layoutParams.topMargin = iM1036dp;
        if (this.BIG_SCREEN) {
            this.enterViewBottomOffset = (((-iM1036dp) + size) - size2) - AndroidUtilities.m1036dp(64.0f);
        } else {
            this.enterViewBottomOffset = ((-iM1036dp) + size) - size2;
        }
        if (this.BIG_SCREEN != this.wasBigScreen) {
            this.storyContainer.setLayoutParams(layoutParams);
        }
        FrameLayout frameLayout = this.selfView;
        if (frameLayout != null) {
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) frameLayout.getLayoutParams();
            if (this.BIG_SCREEN) {
                layoutParams2.topMargin = iM1036dp + size2 + AndroidUtilities.m1036dp(8.0f);
            } else {
                layoutParams2.topMargin = (iM1036dp + size2) - AndroidUtilities.m1036dp(48.0f);
            }
        }
        TextView textView = this.replyDisabledTextView;
        if (textView != null) {
            FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) textView.getLayoutParams();
            boolean z5 = this.BIG_SCREEN;
            TextView textView2 = this.replyDisabledTextView;
            if (!z5) {
                textView2.setTextColor(ColorUtils.setAlphaComponent(-1, 191));
                layoutParams3.topMargin = ((iM1036dp + size2) - AndroidUtilities.m1036dp(12.0f)) - AndroidUtilities.m1036dp(40.0f);
                c2 = 0;
            } else {
                c2 = 0;
                textView2.setTextColor(ColorUtils.blendARGB(-16777216, -1, 0.5f));
                layoutParams3.topMargin = iM1036dp + size2 + AndroidUtilities.m1036dp(12.0f);
            }
        } else {
            c2 = 0;
        }
        InstantCameraView instantCameraView = this.instantCameraView;
        if (instantCameraView != null) {
            FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) instantCameraView.getLayoutParams();
            if (visibleEmojiPadding == 0) {
                layoutParams4.bottomMargin = size - ((iM1036dp + size2) - AndroidUtilities.m1036dp(64.0f));
            } else {
                layoutParams4.bottomMargin = visibleEmojiPadding + AndroidUtilities.m1036dp(64.0f);
            }
        }
        boolean z6 = this.BIG_SCREEN;
        LinearLayout linearLayout = this.bottomActionsLinearLayout;
        if (!z6) {
            ((FrameLayout.LayoutParams) linearLayout.getLayoutParams()).topMargin = ((iM1036dp + size2) - AndroidUtilities.m1036dp(12.0f)) - AndroidUtilities.m1036dp(40.0f);
            int iM1036dp2 = this.isSelf ? AndroidUtilities.m1036dp(40.0f) : AndroidUtilities.m1036dp(56.0f);
            ((FrameLayout.LayoutParams) this.storyCaptionView.getLayoutParams()).bottomMargin = iM1036dp2;
            if (this.wasBigScreen != this.BIG_SCREEN) {
                StoryCaptionView storyCaptionView = this.storyCaptionView;
                storyCaptionView.setLayoutParams((FrameLayout.LayoutParams) storyCaptionView.getLayoutParams());
            }
            this.storyCaptionView.blackoutBottomOffset = iM1036dp2;
        } else {
            ((FrameLayout.LayoutParams) linearLayout.getLayoutParams()).topMargin = iM1036dp + size2 + AndroidUtilities.m1036dp(12.0f);
            ((FrameLayout.LayoutParams) this.storyCaptionView.getLayoutParams()).bottomMargin = AndroidUtilities.m1036dp(8.0f);
            if (this.wasBigScreen != this.BIG_SCREEN) {
                StoryCaptionView storyCaptionView2 = this.storyCaptionView;
                storyCaptionView2.setLayoutParams((FrameLayout.LayoutParams) storyCaptionView2.getLayoutParams());
            }
            this.storyCaptionView.blackoutBottomOffset = AndroidUtilities.m1036dp(8.0f);
        }
        this.forceUpdateOffsets = true;
        float fM1036dp = AndroidUtilities.m1036dp(48.0f);
        if (this.privacyButton.getVisibility() == 0) {
            fM1036dp += AndroidUtilities.m1036dp(60.0f);
        }
        if (this.muteIconContainer.getVisibility() == 0) {
            fM1036dp += AndroidUtilities.m1036dp(40.0f);
        }
        FrameLayout.LayoutParams layoutParams5 = (FrameLayout.LayoutParams) this.headerView.titleView.getLayoutParams();
        if (layoutParams5.rightMargin != fM1036dp) {
            int i3 = (int) fM1036dp;
            layoutParams5.rightMargin = i3;
            ((FrameLayout.LayoutParams) this.headerView.subtitleView[c2].getLayoutParams()).rightMargin = i3;
            ((FrameLayout.LayoutParams) this.headerView.subtitleView[1].getLayoutParams()).rightMargin = i3;
            this.headerView.forceLayout();
        }
        super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30));
        this.wasBigScreen = this.BIG_SCREEN;
    }

    public /* synthetic */ void lambda$onMeasure$53(ValueAnimator valueAnimator) {
        this.animatingKeyboardHeight = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$35 */
    public class C691935 extends AnimatorListenerAdapter {
        public C691935() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            super.onAnimationEnd(animator);
            PeerStoriesView.this.notificationsLocker.unlock();
            PeerStoriesView.this.animatingKeyboardHeight = r2.lastKeyboardHeight;
            ChatActivityEnterView chatActivityEnterView7 = PeerStoriesView.this.chatActivityEnterView;
            if (chatActivityEnterView7 != null) {
                chatActivityEnterView7.onOverrideAnimationEnd();
            }
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            if (peerStoriesView.isActive && !peerStoriesView.keyboardVisible) {
                peerStoriesView.delegate.setKeyboardVisible(false);
            }
            PeerStoriesView peerStoriesView2 = PeerStoriesView.this;
            if (!peerStoriesView2.keyboardVisible && peerStoriesView2.mentionContainer != null) {
                PeerStoriesView.this.mentionContainer.setVisibility(8);
            }
            PeerStoriesView peerStoriesView3 = PeerStoriesView.this;
            peerStoriesView3.forceUpdateOffsets = true;
            peerStoriesView3.invalidate();
        }
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.progressToKeyboard = -1.0f;
        this.forceUpdateOffsets = true;
        invalidate();
    }

    /*  JADX ERROR: NullPointerException in pass: LoopRegionVisitor
        java.lang.NullPointerException
        */
    public void updateViewOffsets() {
        /*
            Method dump skipped, instruction units count: 1316
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Stories.PeerStoriesView.updateViewOffsets():void");
    }

    public float getHideInterfaceAlpha() {
        return (1.0f - this.progressToHideInterface.get()) * (1.0f - this.storyViewer.getProgressToSelfViews());
    }

    @Override // android.view.ViewGroup
    public boolean drawChild(Canvas canvas, View view, long j) {
        Canvas canvas2;
        BlurredBackgroundSourceRenderNode blurredBackgroundSourceRenderNode;
        float fM1036dp;
        if (view == this.mentionContainer) {
            canvas.save();
            canvas.clipRect(0.0f, this.mentionContainer.getY(), getMeasuredWidth(), this.mentionContainer.getY() + this.mentionContainer.getMeasuredHeight());
            boolean zDrawChild = super.drawChild(canvas, view, j);
            canvas.restore();
            return zDrawChild;
        }
        ChatActivityEnterView chatActivityEnterView = this.chatActivityEnterView;
        if (view == chatActivityEnterView) {
            if (this.progressToKeyboard <= 0.0f || this.currentStory.isLive) {
                canvas2 = canvas;
            } else {
                this.sharedResources.dimPaint.setAlpha((int) (this.progressToKeyboard * 63.75f));
                canvas2 = canvas;
                canvas2.drawRect(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight(), this.sharedResources.dimPaint);
            }
            this.sharedResources.rect1.set(this.chatActivityEnterView.getX(), this.chatActivityEnterView.getY() + this.chatActivityEnterView.getAnimatedTop() + AndroidUtilities.m1036dp(1.33f), this.chatActivityEnterView.getX() + this.chatActivityEnterView.getMeasuredWidth(), this.chatActivityEnterView.getY() + this.chatActivityEnterView.getMeasuredHeight());
            float fM1036dp2 = AndroidUtilities.m1036dp(40.0f);
            if (!this.currentStory.isLive) {
                if (this.allowShare) {
                    fM1036dp2 += AndroidUtilities.m1036dp(46.0f);
                }
                if (this.allowRepost && this.isChannel) {
                    fM1036dp2 += AndroidUtilities.m1036dp(46.0f);
                }
                FrameLayout frameLayout = this.likeButtonContainer;
                if (frameLayout != null && frameLayout.getVisibility() == 0) {
                    fM1036dp2 = (fM1036dp2 - AndroidUtilities.m1036dp(40.0f)) + this.likeButtonContainer.getLayoutParams().width;
                }
                fM1036dp = 0.0f;
            } else {
                fM1036dp = AndroidUtilities.m1036dp(46.0f);
                fM1036dp2 = AndroidUtilities.m1036dp(46.0f);
                MuteButton muteButton = this.muteButton;
                if (muteButton != null && muteButton.getVisibility() == 0) {
                    fM1036dp2 += AndroidUtilities.m1036dp(46.0f);
                }
            }
            this.sharedResources.rect2.set(AndroidUtilities.m1036dp(10.0f) + fM1036dp, ((this.chatActivityEnterView.getY() + this.chatActivityEnterView.getMeasuredHeight()) - AndroidUtilities.m1036dp(5.0f)) - AndroidUtilities.m1036dp(38.0f), (getMeasuredWidth() - AndroidUtilities.m1036dp(10.0f)) - fM1036dp2, (this.chatActivityEnterView.getY() + this.chatActivityEnterView.getMeasuredHeight()) - AndroidUtilities.m1036dp(5.0f));
            this.chatActivityEnterView.setTranslationX(fM1036dp * (1.0f - this.progressToKeyboard));
            this.chatActivityEnterView.getEditField().setTranslationY((this.chatActivityEnterView.getMeasuredHeight() > AndroidUtilities.m1036dp(50.0f) ? 0.0f + ((1.0f - this.progressToKeyboard) * (this.chatActivityEnterView.getMeasuredHeight() - AndroidUtilities.m1036dp(50.0f))) : 0.0f) + ((-AndroidUtilities.m1036dp(2.0f)) * (1.0f - this.progressToKeyboard)));
            float fM1036dp3 = AndroidUtilities.m1036dp(50.0f) / 2.0f;
            AndroidUtilities.lerp(this.sharedResources.rect2, this.sharedResources.rect1, this.progressToKeyboard, this.sharedResources.finalRect);
            BlurredBackgroundDrawable blurredBackgroundDrawable = this.inputFieldBackground;
            SharedResources sharedResources = this.sharedResources;
            if (blurredBackgroundDrawable != null) {
                blurredBackgroundDrawable.setBounds((int) sharedResources.finalRect.left, (int) this.sharedResources.finalRect.top, (int) this.sharedResources.finalRect.right, (int) this.sharedResources.finalRect.bottom);
                this.inputFieldBackground.setRadius(fM1036dp3);
                this.inputFieldBackground.setAlpha((int) ((1.0f - this.progressToDismiss) * 255.0f * getHideInterfaceAlpha() * (1.0f - this.outT)));
                this.inputFieldBackground.draw(canvas2);
            } else {
                canvas2.drawRoundRect(sharedResources.finalRect, fM1036dp3, fM1036dp3, this.inputBackgroundPaint);
            }
            if (this.progressToKeyboard < 0.5f) {
                canvas2.save();
                canvas2.clipRect(this.sharedResources.finalRect);
                boolean zDrawChild2 = super.drawChild(canvas2, view, j);
                canvas2.restore();
                return zDrawChild2;
            }
        } else {
            canvas2 = canvas;
            if (chatActivityEnterView != null && chatActivityEnterView.isPopupView(view)) {
                float fM1036dp4 = AndroidUtilities.m1036dp(30.0f);
                this.sharedResources.popupRect.set(0.0f, view.getY() + AndroidUtilities.m1036dp(1.0f), getWidth(), getHeight() + AndroidUtilities.m1036dp(20.0f));
                this.clipPath.rewind();
                this.clipPath.addRoundRect(this.sharedResources.popupRect, fM1036dp4, fM1036dp4, Path.Direction.CW);
                canvas2.save();
                canvas2.clipPath(this.clipPath);
                BlurredBackgroundDrawable blurredBackgroundDrawable2 = this.emojiKeyboardBackground;
                SharedResources sharedResources2 = this.sharedResources;
                if (blurredBackgroundDrawable2 != null) {
                    blurredBackgroundDrawable2.setBounds((int) sharedResources2.popupRect.left, (int) this.sharedResources.popupRect.top, (int) this.sharedResources.popupRect.right, (int) this.sharedResources.popupRect.bottom);
                    this.emojiKeyboardBackground.setRadius(fM1036dp4, fM1036dp4, fM1036dp4, fM1036dp4);
                    this.emojiKeyboardBackground.setAlpha(255);
                    this.emojiKeyboardBackground.draw(canvas2);
                } else {
                    canvas2.drawRoundRect(sharedResources2.popupRect, fM1036dp4, fM1036dp4, this.inputBackgroundPaint);
                }
                boolean zDrawChild3 = super.drawChild(canvas2, view, j);
                canvas2.restore();
                return zDrawChild3;
            }
            if (view == this.reactionsContainerLayout && this.chatActivityEnterView != null) {
                view.setTranslationY(((-r11.getMeasuredHeight()) + (this.chatActivityEnterView.getY() + this.chatActivityEnterView.getAnimatedTop())) - AndroidUtilities.m1036dp(18.0f));
            } else {
                if (view == this.likesReactionLayout) {
                    view.setTranslationY((((-(r11.getMeasuredHeight() - this.likesReactionLayout.getPaddingBottom())) + this.likeButtonContainer.getY()) + this.bottomActionsLinearLayout.getY()) - AndroidUtilities.m1036dp(18.0f));
                } else if (view == this.storyContainer && Build.VERSION.SDK_INT >= 31 && canvas2.isHardwareAccelerated() && (blurredBackgroundSourceRenderNode = this.blurredBackgroundSourceRenderNodeWithSaturation) != null && !blurredBackgroundSourceRenderNode.inRecording()) {
                    RecordingCanvas recordingCanvasBeginRecording = this.blurredBackgroundSourceRenderNodeWithSaturation.beginRecording(getMeasuredWidth(), getMeasuredHeight());
                    recordingCanvasBeginRecording.drawColor(ColorUtils.blendARGB(-16777216, -1, 0.2f));
                    recordingCanvasBeginRecording.translate(this.storyContainer.getX(), this.storyContainer.getY());
                    view.draw(recordingCanvasBeginRecording);
                    this.blurredBackgroundSourceRenderNodeWithSaturation.endRecording();
                }
            }
        }
        return super.drawChild(canvas2, view, j);
    }

    public void checkInstantCameraView() {
        if (this.instantCameraView == null && CameraView.isCameraAllowed()) {
            this.instantCameraView = new InstantCameraView(getContext(), new InstantCameraView.Delegate() { // from class: org.telegram.ui.Stories.PeerStoriesView.36
                public C692036() {
                }

                @Override // org.telegram.ui.Components.InstantCameraView.Delegate
                public View getFragmentView() {
                    return PeerStoriesView.this;
                }

                @Override // org.telegram.ui.Components.InstantCameraView.Delegate
                public void sendMedia(MediaController.PhotoEntry photoEntry, VideoEditedInfo videoEditedInfo, boolean z, int i, int i2, boolean z2, long j) {
                    if (photoEntry == null) {
                        return;
                    }
                    PeerStoriesView peerStoriesView = PeerStoriesView.this;
                    TL_stories.StoryItem storyItem = peerStoriesView.currentStory.storyItem;
                    if (storyItem == null || (storyItem instanceof TL_stories.TL_storyItemSkipped)) {
                        return;
                    }
                    storyItem.dialogId = peerStoriesView.dialogId;
                    if (photoEntry.isVideo) {
                        PeerStoriesView peerStoriesView2 = PeerStoriesView.this;
                        if (videoEditedInfo != null) {
                            SendMessagesHelper.prepareSendingVideo(peerStoriesView2.getAccountInstance(), photoEntry.path, videoEditedInfo, null, null, PeerStoriesView.this.dialogId, null, null, storyItem, null, photoEntry.entities, photoEntry.ttl, null, z, i, i2, z2, photoEntry.hasSpoiler, photoEntry.caption, null, 0, 0L, j);
                        } else {
                            SendMessagesHelper.prepareSendingVideo(peerStoriesView2.getAccountInstance(), photoEntry.path, null, null, null, PeerStoriesView.this.dialogId, null, null, storyItem, null, photoEntry.entities, photoEntry.ttl, null, z, i, i2, z2, photoEntry.hasSpoiler, photoEntry.caption, null, 0, 0L, j);
                        }
                    } else if (photoEntry.imagePath != null) {
                        SendMessagesHelper.prepareSendingPhoto(PeerStoriesView.this.getAccountInstance(), photoEntry.imagePath, photoEntry.thumbPath, null, PeerStoriesView.this.dialogId, null, null, storyItem, null, photoEntry.entities, photoEntry.stickers, null, photoEntry.ttl, null, videoEditedInfo, z, i, i2, z2, photoEntry.caption, null, 0, 0L, j);
                    } else if (photoEntry.path != null) {
                        SendMessagesHelper.prepareSendingPhoto(PeerStoriesView.this.getAccountInstance(), photoEntry.path, photoEntry.thumbPath, null, PeerStoriesView.this.dialogId, null, null, storyItem, null, photoEntry.entities, photoEntry.stickers, null, photoEntry.ttl, null, videoEditedInfo, z, i, i2, z2, photoEntry.caption, null, 0, 0L, j);
                    }
                    PeerStoriesView.this.afterMessageSend(j <= 0);
                }

                @Override // org.telegram.ui.Components.InstantCameraView.Delegate
                public Activity getParentActivity() {
                    return AndroidUtilities.findActivity(PeerStoriesView.this.getContext());
                }

                @Override // org.telegram.ui.Components.InstantCameraView.Delegate
                public int getClassGuid() {
                    return PeerStoriesView.this.classGuid;
                }

                @Override // org.telegram.ui.Components.InstantCameraView.Delegate
                public long getDialogId() {
                    return PeerStoriesView.this.dialogId;
                }
            }, this.resourcesProvider, false);
            addView(this.instantCameraView, Math.min(indexOfChild(this.chatActivityEnterView.getRecordCircle()), indexOfChild(this.chatActivityEnterView.controlsView)), LayoutHelper.createFrame(-1, -1, 51));
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$36 */
    public class C692036 implements InstantCameraView.Delegate {
        public C692036() {
        }

        @Override // org.telegram.ui.Components.InstantCameraView.Delegate
        public View getFragmentView() {
            return PeerStoriesView.this;
        }

        @Override // org.telegram.ui.Components.InstantCameraView.Delegate
        public void sendMedia(MediaController.PhotoEntry photoEntry, VideoEditedInfo videoEditedInfo, boolean z, int i, int i2, boolean z2, long j) {
            if (photoEntry == null) {
                return;
            }
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            TL_stories.StoryItem storyItem = peerStoriesView.currentStory.storyItem;
            if (storyItem == null || (storyItem instanceof TL_stories.TL_storyItemSkipped)) {
                return;
            }
            storyItem.dialogId = peerStoriesView.dialogId;
            if (photoEntry.isVideo) {
                PeerStoriesView peerStoriesView2 = PeerStoriesView.this;
                if (videoEditedInfo != null) {
                    SendMessagesHelper.prepareSendingVideo(peerStoriesView2.getAccountInstance(), photoEntry.path, videoEditedInfo, null, null, PeerStoriesView.this.dialogId, null, null, storyItem, null, photoEntry.entities, photoEntry.ttl, null, z, i, i2, z2, photoEntry.hasSpoiler, photoEntry.caption, null, 0, 0L, j);
                } else {
                    SendMessagesHelper.prepareSendingVideo(peerStoriesView2.getAccountInstance(), photoEntry.path, null, null, null, PeerStoriesView.this.dialogId, null, null, storyItem, null, photoEntry.entities, photoEntry.ttl, null, z, i, i2, z2, photoEntry.hasSpoiler, photoEntry.caption, null, 0, 0L, j);
                }
            } else if (photoEntry.imagePath != null) {
                SendMessagesHelper.prepareSendingPhoto(PeerStoriesView.this.getAccountInstance(), photoEntry.imagePath, photoEntry.thumbPath, null, PeerStoriesView.this.dialogId, null, null, storyItem, null, photoEntry.entities, photoEntry.stickers, null, photoEntry.ttl, null, videoEditedInfo, z, i, i2, z2, photoEntry.caption, null, 0, 0L, j);
            } else if (photoEntry.path != null) {
                SendMessagesHelper.prepareSendingPhoto(PeerStoriesView.this.getAccountInstance(), photoEntry.path, photoEntry.thumbPath, null, PeerStoriesView.this.dialogId, null, null, storyItem, null, photoEntry.entities, photoEntry.stickers, null, photoEntry.ttl, null, videoEditedInfo, z, i, i2, z2, photoEntry.caption, null, 0, 0L, j);
            }
            PeerStoriesView.this.afterMessageSend(j <= 0);
        }

        @Override // org.telegram.ui.Components.InstantCameraView.Delegate
        public Activity getParentActivity() {
            return AndroidUtilities.findActivity(PeerStoriesView.this.getContext());
        }

        @Override // org.telegram.ui.Components.InstantCameraView.Delegate
        public int getClassGuid() {
            return PeerStoriesView.this.classGuid;
        }

        @Override // org.telegram.ui.Components.InstantCameraView.Delegate
        public long getDialogId() {
            return PeerStoriesView.this.dialogId;
        }
    }

    public void afterMessageSend(boolean z) {
        BulletinFactory bulletinFactoryM1142of;
        InstantCameraView instantCameraView = this.instantCameraView;
        if (instantCameraView != null) {
            instantCameraView.resetCameraFile();
            this.instantCameraView.cancel(false);
        }
        this.storyViewer.clearDraft(this.dialogId, this.currentStory.storyItem);
        this.messageSent = true;
        this.storyViewer.closeKeyboardOrEmoji();
        if (z && (bulletinFactoryM1142of = BulletinFactory.m1142of(this.storyContainer, this.resourcesProvider)) != null) {
            bulletinFactoryM1142of.createSimpleBulletin(C2797R.raw.forward, LocaleController.getString(C2797R.string.MessageSent), LocaleController.getString(C2797R.string.ViewInChat), 5000, new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.openChat();
                }
            }).hideAfterBottomSheet(false).show(false);
        }
        MessagesController.getInstance(this.currentAccount).ensureMessagesLoaded(this.dialogId, 0, null);
    }

    public void openChat() {
        Bundle bundle = new Bundle();
        long j = this.dialogId;
        if (j < 0) {
            bundle.putLong("chat_id", -j);
        } else {
            bundle.putLong("user_id", j);
        }
        TLRPC.Dialog dialog = MessagesController.getInstance(this.currentAccount).getDialog(this.dialogId);
        if (dialog != null) {
            bundle.putInt("message_id", dialog.top_message);
        }
        this.storyViewer.presentFragment(new ChatActivity(bundle));
    }

    public AccountInstance getAccountInstance() {
        return AccountInstance.getInstance(this.currentAccount);
    }

    public static class VideoPlayerSharedScope {
        public boolean firstFrameRendered;
        public LivePlayer livePlayer;
        public StoryViewer.VideoPlayerHolder player;
        public View renderView;
        public SurfaceView surfaceView;
        public TextureView textureView;
        public ArrayList<View> viewsToInvalidate = new ArrayList<>();

        public void invalidate() {
            for (int i = 0; i < this.viewsToInvalidate.size(); i++) {
                this.viewsToInvalidate.get(i).invalidate();
            }
        }

        public boolean isBuffering() {
            StoryViewer.VideoPlayerHolder videoPlayerHolder = this.player;
            return videoPlayerHolder != null && videoPlayerHolder.isBuffering();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$37 */
    public class C692137 extends WrappedResourceProvider {
        public C692137(Theme.ResourcesProvider resourcesProvider) {
            super(resourcesProvider);
        }

        @Override // org.telegram.p035ui.WrappedResourceProvider
        public void appendColors() {
            this.sparseIntArray.put(Theme.key_chat_emojiPanelBackground, ColorUtils.setAlphaComponent(-1, 30));
        }
    }

    public void checkReactionsLayout() {
        if (this.reactionsContainerLayout == null) {
            ReactionsContainerLayout reactionsContainerLayout = new ReactionsContainerLayout(1, LaunchActivity.getLastFragment(), getContext(), this.currentAccount, new WrappedResourceProvider(this.resourcesProvider) { // from class: org.telegram.ui.Stories.PeerStoriesView.37
                public C692137(Theme.ResourcesProvider resourcesProvider) {
                    super(resourcesProvider);
                }

                @Override // org.telegram.p035ui.WrappedResourceProvider
                public void appendColors() {
                    this.sparseIntArray.put(Theme.key_chat_emojiPanelBackground, ColorUtils.setAlphaComponent(-1, 30));
                }
            });
            this.reactionsContainerLayout = reactionsContainerLayout;
            reactionsContainerLayout.setHint(LocaleController.getString(this.isGroup ? C2797R.string.StoryGroupReactionsHint : C2797R.string.StoryReactionsHint));
            ReactionsContainerLayout reactionsContainerLayout2 = this.reactionsContainerLayout;
            reactionsContainerLayout2.skipEnterAnimation = true;
            addView(reactionsContainerLayout2, this.reactionsContainerIndex, LayoutHelper.createFrame(-2, 72.0f, 49, 0.0f, 0.0f, 0.0f, 64.0f));
            this.reactionsContainerLayout.setDelegate(new C692238());
            this.reactionsContainerLayout.setMessage(null, null, true);
        }
        this.reactionsContainerLayout.setFragment(LaunchActivity.getLastFragment());
        this.reactionsContainerLayout.setHint(LocaleController.getString(this.isGroup ? C2797R.string.StoryGroupReactionsHint : C2797R.string.StoryReactionsHint));
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$38 */
    public class C692238 implements ReactionsContainerLayout.ReactionsContainerDelegate {
        public C692238() {
        }

        @Override // org.telegram.ui.Components.ReactionsContainerLayout.ReactionsContainerDelegate
        public void onReactionClicked(View view, ReactionsLayoutInBubble.VisibleReaction visibleReaction, boolean z, boolean z2) {
            onReactionClickedInternal(view, visibleReaction, z, z2, !z);
        }

        public void onReactionClickedInternal(View view, ReactionsLayoutInBubble.VisibleReaction visibleReaction, boolean z, final boolean z2, boolean z3) {
            final C692238 c692238;
            final View view2;
            final ReactionsLayoutInBubble.VisibleReaction visibleReaction2;
            final boolean z4;
            if (z3) {
                c692238 = this;
                view2 = view;
                visibleReaction2 = visibleReaction;
                z4 = z;
                if (PeerStoriesView.this.applyMessageToChat(new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$38$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onReactionClickedInternal$0(view2, visibleReaction2, z4, z2);
                    }
                })) {
                    return;
                }
            } else {
                c692238 = this;
                view2 = view;
                visibleReaction2 = visibleReaction;
                z4 = z;
            }
            AlertsCreator.ensurePaidMessageConfirmation(PeerStoriesView.this.currentAccount, PeerStoriesView.this.dialogId, 1, new Utilities.Callback() { // from class: org.telegram.ui.Stories.PeerStoriesView$38$$ExternalSyntheticLambda1
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$onReactionClickedInternal$2(z4, visibleReaction2, view2, (Long) obj);
                }
            });
        }

        public /* synthetic */ void lambda$onReactionClickedInternal$0(View view, ReactionsLayoutInBubble.VisibleReaction visibleReaction, boolean z, boolean z2) {
            onReactionClickedInternal(view, visibleReaction, z, z2, false);
        }

        public /* synthetic */ void lambda$onReactionClickedInternal$2(boolean z, ReactionsLayoutInBubble.VisibleReaction visibleReaction, View view, Long l) {
            ReactionsEffectOverlay reactionsEffectOverlay;
            TLRPC.Document documentFindDocument;
            if (z && visibleReaction.emojicon != null) {
                try {
                    PeerStoriesView.this.performHapticFeedback(0);
                } catch (Exception unused) {
                }
                reactionsEffectOverlay = new ReactionsEffectOverlay(view.getContext(), null, PeerStoriesView.this.reactionsContainerLayout, null, view, r2.getMeasuredWidth() / 2.0f, PeerStoriesView.this.getMeasuredHeight() / 2.0f, visibleReaction, PeerStoriesView.this.currentAccount, 0, true);
            } else {
                reactionsEffectOverlay = new ReactionsEffectOverlay(view.getContext(), null, PeerStoriesView.this.reactionsContainerLayout, null, view, r2.getMeasuredWidth() / 2.0f, PeerStoriesView.this.getMeasuredHeight() / 2.0f, visibleReaction, PeerStoriesView.this.currentAccount, 2, true);
            }
            ReactionsEffectOverlay.currentOverlay = reactionsEffectOverlay;
            reactionsEffectOverlay.windowView.setTag(C2797R.id.parent_tag, 1);
            PeerStoriesView.this.addView(reactionsEffectOverlay.windowView);
            reactionsEffectOverlay.started = true;
            reactionsEffectOverlay.startTime = System.currentTimeMillis();
            String str = visibleReaction.emojicon;
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            if (str != null) {
                documentFindDocument = MediaDataController.getInstance(peerStoriesView.currentAccount).getEmojiAnimatedSticker(visibleReaction.emojicon);
                SendMessagesHelper.SendMessageParams sendMessageParamsM1074of = SendMessagesHelper.SendMessageParams.m1074of(visibleReaction.emojicon, PeerStoriesView.this.dialogId);
                sendMessageParamsM1074of.replyToStoryItem = PeerStoriesView.this.currentStory.storyItem;
                sendMessageParamsM1074of.payStars = l.longValue();
                SendMessagesHelper.getInstance(PeerStoriesView.this.currentAccount).sendMessage(sendMessageParamsM1074of);
            } else {
                documentFindDocument = AnimatedEmojiDrawable.findDocument(peerStoriesView.currentAccount, visibleReaction.documentId);
                String strFindAnimatedEmojiEmoticon = MessageObject.findAnimatedEmojiEmoticon(documentFindDocument, null);
                PeerStoriesView peerStoriesView2 = PeerStoriesView.this;
                if (strFindAnimatedEmojiEmoticon == null) {
                    if (peerStoriesView2.reactionsContainerLayout.getReactionsWindow() != null) {
                        PeerStoriesView.this.reactionsContainerLayout.getReactionsWindow().dismissWithAlpha();
                    }
                    PeerStoriesView.this.closeKeyboardOrEmoji();
                    return;
                }
                SendMessagesHelper.SendMessageParams sendMessageParamsM1074of2 = SendMessagesHelper.SendMessageParams.m1074of(strFindAnimatedEmojiEmoticon, peerStoriesView2.dialogId);
                sendMessageParamsM1074of2.entities = new ArrayList<>();
                TLRPC.TL_messageEntityCustomEmoji tL_messageEntityCustomEmoji = new TLRPC.TL_messageEntityCustomEmoji();
                tL_messageEntityCustomEmoji.document_id = visibleReaction.documentId;
                tL_messageEntityCustomEmoji.offset = 0;
                tL_messageEntityCustomEmoji.length = strFindAnimatedEmojiEmoticon.length();
                sendMessageParamsM1074of2.entities.add(tL_messageEntityCustomEmoji);
                sendMessageParamsM1074of2.replyToStoryItem = PeerStoriesView.this.currentStory.storyItem;
                sendMessageParamsM1074of2.payStars = l.longValue();
                SendMessagesHelper.getInstance(PeerStoriesView.this.currentAccount).sendMessage(sendMessageParamsM1074of2);
            }
            if (l.longValue() <= 0) {
                PeerStoriesView peerStoriesView3 = PeerStoriesView.this;
                BulletinFactory.m1142of(peerStoriesView3.storyContainer, peerStoriesView3.resourcesProvider).createEmojiBulletin(documentFindDocument, LocaleController.getString(C2797R.string.ReactionSent), LocaleController.getString(C2797R.string.ViewInChat), new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$38$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onReactionClickedInternal$1();
                    }
                }).setDuration(5000).show();
            }
            if (PeerStoriesView.this.reactionsContainerLayout.getReactionsWindow() != null) {
                PeerStoriesView.this.reactionsContainerLayout.getReactionsWindow().dismissWithAlpha();
            }
            PeerStoriesView.this.closeKeyboardOrEmoji();
        }

        public /* synthetic */ void lambda$onReactionClickedInternal$1() {
            PeerStoriesView.this.openChat();
        }

        @Override // org.telegram.ui.Components.ReactionsContainerLayout.ReactionsContainerDelegate
        public void drawRoundRect(Canvas canvas, RectF rectF, float f, float f2, float f3, int i, boolean z) {
            float f4 = -f2;
            float f5 = -f3;
            PeerStoriesView.this.bitmapShaderTools.setBounds(f4, f5, PeerStoriesView.this.getMeasuredWidth() + f4, PeerStoriesView.this.getMeasuredHeight() + f5);
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            if (f > 0.0f) {
                canvas.drawRoundRect(rectF, f, f, peerStoriesView.bitmapShaderTools.paint);
                canvas.drawRoundRect(rectF, f, f, PeerStoriesView.this.inputBackgroundPaint);
            } else {
                canvas.drawRect(rectF, peerStoriesView.bitmapShaderTools.paint);
                canvas.drawRect(rectF, PeerStoriesView.this.inputBackgroundPaint);
            }
        }

        @Override // org.telegram.ui.Components.ReactionsContainerLayout.ReactionsContainerDelegate
        public boolean needEnterText() {
            return PeerStoriesView.this.needEnterText();
        }

        @Override // org.telegram.ui.Components.ReactionsContainerLayout.ReactionsContainerDelegate
        public void onEmojiWindowDismissed() {
            PeerStoriesView.this.delegate.requestAdjust(false);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$39 */
    public class C692339 extends WrappedResourceProvider {
        public C692339(Theme.ResourcesProvider resourcesProvider) {
            super(resourcesProvider);
        }

        @Override // org.telegram.p035ui.WrappedResourceProvider
        public void appendColors() {
            this.sparseIntArray.put(Theme.key_chat_emojiPanelBackground, ColorUtils.setAlphaComponent(-1, 30));
        }
    }

    public void checkReactionsLayoutForLike() {
        ReactionsContainerLayout reactionsContainerLayout = this.likesReactionLayout;
        if (reactionsContainerLayout == null) {
            ReactionsContainerLayout reactionsContainerLayout2 = new ReactionsContainerLayout(2, LaunchActivity.getLastFragment(), getContext(), this.currentAccount, new WrappedResourceProvider(this.resourcesProvider) { // from class: org.telegram.ui.Stories.PeerStoriesView.39
                public C692339(Theme.ResourcesProvider resourcesProvider) {
                    super(resourcesProvider);
                }

                @Override // org.telegram.p035ui.WrappedResourceProvider
                public void appendColors() {
                    this.sparseIntArray.put(Theme.key_chat_emojiPanelBackground, ColorUtils.setAlphaComponent(-1, 30));
                }
            });
            this.likesReactionLayout = reactionsContainerLayout2;
            reactionsContainerLayout2.setPadding(0, 0, 0, AndroidUtilities.m1036dp(22.0f));
            addView(this.likesReactionLayout, getChildCount() - 1, LayoutHelper.createFrame(-2, 74.0f, 53, 0.0f, 0.0f, 12.0f, 64.0f));
            this.likesReactionLayout.setVisibility(8);
            this.likesReactionLayout.setDelegate(new C692540());
            this.likesReactionLayout.setMessage(null, null, true);
        } else {
            bringChildToFront(reactionsContainerLayout);
            this.likesReactionLayout.reset();
        }
        this.likesReactionLayout.setFragment(LaunchActivity.getLastFragment());
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$40 */
    public class C692540 implements ReactionsContainerLayout.ReactionsContainerDelegate {
        public C692540() {
        }

        @Override // org.telegram.ui.Components.ReactionsContainerLayout.ReactionsContainerDelegate
        public void onReactionClicked(final View view, final ReactionsLayoutInBubble.VisibleReaction visibleReaction, boolean z, boolean z2) {
            Runnable runnable = new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$40$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onReactionClicked$1(visibleReaction, view);
                }
            };
            if (!z) {
                PeerStoriesView.this.applyMessageToChat(runnable);
            } else {
                runnable.run();
            }
        }

        public /* synthetic */ void lambda$onReactionClicked$1(ReactionsLayoutInBubble.VisibleReaction visibleReaction, View view) {
            TLRPC.TL_availableReaction tL_availableReaction;
            PeerStoriesView.this.movingReaction = true;
            final boolean[] zArr = {false};
            StoriesLikeButton storiesLikeButton = PeerStoriesView.this.storiesLikeButton;
            storiesLikeButton.animate().alpha(0.0f).scaleX(0.8f).scaleY(0.8f).setListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Stories.PeerStoriesView.40.1
                final /* synthetic */ View val$oldLikeButton;

                public AnonymousClass1(View storiesLikeButton2) {
                    view = storiesLikeButton2;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    AndroidUtilities.removeFromParent(view);
                }
            }).setDuration(150L).start();
            int iM1036dp = AndroidUtilities.m1036dp(8.0f);
            PeerStoriesView.this.storiesLikeButton = new StoriesLikeButton(PeerStoriesView.this.getContext(), PeerStoriesView.this.sharedResources);
            PeerStoriesView.this.storiesLikeButton.setPadding(iM1036dp, iM1036dp, iM1036dp, iM1036dp);
            PeerStoriesView.this.likeButtonContainer.addView(PeerStoriesView.this.storiesLikeButton, LayoutHelper.createFrame(40, 40, 3));
            if (PeerStoriesView.this.reactionMoveDrawable != null) {
                PeerStoriesView.this.reactionMoveDrawable.removeView(PeerStoriesView.this);
                PeerStoriesView.this.reactionMoveDrawable = null;
            }
            if (PeerStoriesView.this.emojiReactionEffect != null) {
                PeerStoriesView.this.emojiReactionEffect.removeView(PeerStoriesView.this);
                PeerStoriesView.this.emojiReactionEffect = null;
            }
            PeerStoriesView.this.drawAnimatedEmojiAsMovingReaction = false;
            if (visibleReaction.documentId != 0) {
                PeerStoriesView.this.drawAnimatedEmojiAsMovingReaction = true;
                PeerStoriesView.this.reactionMoveDrawable = new AnimatedEmojiDrawable(2, PeerStoriesView.this.currentAccount, visibleReaction.documentId);
                PeerStoriesView.this.reactionMoveDrawable.addView(PeerStoriesView.this);
            } else if (visibleReaction.emojicon != null && (tL_availableReaction = MediaDataController.getInstance(PeerStoriesView.this.currentAccount).getReactionsMap().get(visibleReaction.emojicon)) != null) {
                PeerStoriesView.this.reactionMoveImageReceiver.setImage(null, null, ImageLocation.getForDocument(tL_availableReaction.select_animation), "60_60", null, null, null, 0L, null, null, 0);
                PeerStoriesView.this.reactionEffectImageReceiver.setImage(ImageLocation.getForDocument(tL_availableReaction.around_animation), ReactionsEffectOverlay.getFilterForAroundAnimation(), null, null, null, 0);
                if (PeerStoriesView.this.reactionEffectImageReceiver.getLottieAnimation() != null) {
                    PeerStoriesView.this.reactionEffectImageReceiver.getLottieAnimation().setCurrentFrame(0, false, true);
                }
            }
            PeerStoriesView.this.storiesLikeButton.setReaction(visibleReaction);
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            if (peerStoriesView.isChannel) {
                TL_stories.StoryItem storyItem = peerStoriesView.currentStory.storyItem;
                if (storyItem.sent_reaction == null) {
                    if (storyItem.views == null) {
                        storyItem.views = new TL_stories.TL_storyViews();
                    }
                    TL_stories.StoryItem storyItem2 = PeerStoriesView.this.currentStory.storyItem;
                    TL_stories.StoryViews storyViews = storyItem2.views;
                    storyViews.reactions_count++;
                    ReactionsUtils.applyForStoryViews(null, storyItem2.sent_reaction, storyViews);
                    PeerStoriesView.this.updateUserViews(true);
                }
            }
            if (visibleReaction.documentId != 0 && PeerStoriesView.this.storiesLikeButton.emojiDrawable != null) {
                PeerStoriesView peerStoriesView2 = PeerStoriesView.this;
                peerStoriesView2.emojiReactionEffect = AnimatedEmojiEffect.createFrom(peerStoriesView2.storiesLikeButton.emojiDrawable, false, true);
                PeerStoriesView.this.emojiReactionEffect.setView(PeerStoriesView.this);
            }
            PeerStoriesView peerStoriesView3 = PeerStoriesView.this;
            peerStoriesView3.storiesController.setStoryReaction(peerStoriesView3.dialogId, PeerStoriesView.this.currentStory.storyItem, visibleReaction);
            int[] iArr = new int[2];
            view.getLocationInWindow(iArr);
            int[] iArr2 = new int[2];
            PeerStoriesView.this.getLocationInWindow(iArr2);
            PeerStoriesView.this.movingReactionFromX = iArr[0] - iArr2[0];
            PeerStoriesView.this.movingReactionFromY = iArr[1] - iArr2[1];
            PeerStoriesView.this.movingReactionFromSize = view.getMeasuredHeight();
            final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            PeerStoriesView.this.movingReactionProgress = 0.0f;
            PeerStoriesView.this.invalidate();
            StoriesLikeButton storiesLikeButton2 = PeerStoriesView.this.storiesLikeButton;
            storiesLikeButton2.setAllowDrawReaction(false);
            storiesLikeButton2.prepareAnimateReaction(visibleReaction);
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$40$$ExternalSyntheticLambda1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f$0.lambda$onReactionClicked$0(valueAnimatorOfFloat, zArr, valueAnimator);
                }
            });
            valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Stories.PeerStoriesView.40.2
                final /* synthetic */ boolean[] val$effectStarted;
                final /* synthetic */ StoriesLikeButton val$storiesLikeButtonFinal;

                public AnonymousClass2(final boolean[] zArr2, StoriesLikeButton storiesLikeButton22) {
                    zArr = zArr2;
                    storiesLikeButton = storiesLikeButton22;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    PeerStoriesView.this.movingReaction = false;
                    PeerStoriesView.this.movingReactionProgress = 1.0f;
                    PeerStoriesView.this.invalidate();
                    boolean[] zArr2 = zArr;
                    if (!zArr2[0]) {
                        zArr2[0] = true;
                        PeerStoriesView.this.drawReactionEffect = true;
                        try {
                            PeerStoriesView.this.performHapticFeedback(3);
                        } catch (Exception unused) {
                        }
                    }
                    storiesLikeButton.setAllowDrawReaction(true);
                    storiesLikeButton.animateVisibleReaction();
                    if (PeerStoriesView.this.reactionMoveDrawable != null) {
                        PeerStoriesView.this.reactionMoveDrawable.removeView(PeerStoriesView.this);
                        PeerStoriesView.this.reactionMoveDrawable = null;
                    }
                }
            });
            valueAnimatorOfFloat.setDuration(220L);
            valueAnimatorOfFloat.start();
            PeerStoriesView.this.showLikesReaction(false);
        }

        /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$40$1 */
        public class AnonymousClass1 extends AnimatorListenerAdapter {
            final /* synthetic */ View val$oldLikeButton;

            public AnonymousClass1(View storiesLikeButton2) {
                view = storiesLikeButton2;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                AndroidUtilities.removeFromParent(view);
            }
        }

        public /* synthetic */ void lambda$onReactionClicked$0(ValueAnimator valueAnimator, boolean[] zArr, ValueAnimator valueAnimator2) {
            PeerStoriesView.this.movingReactionProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            PeerStoriesView.this.invalidate();
            if (PeerStoriesView.this.movingReactionProgress <= 0.8f || zArr[0]) {
                return;
            }
            zArr[0] = true;
            PeerStoriesView.this.drawReactionEffect = true;
            try {
                PeerStoriesView.this.performHapticFeedback(3);
            } catch (Exception unused) {
            }
        }

        /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$40$2 */
        public class AnonymousClass2 extends AnimatorListenerAdapter {
            final /* synthetic */ boolean[] val$effectStarted;
            final /* synthetic */ StoriesLikeButton val$storiesLikeButtonFinal;

            public AnonymousClass2(final boolean[] zArr2, StoriesLikeButton storiesLikeButton22) {
                zArr = zArr2;
                storiesLikeButton = storiesLikeButton22;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                PeerStoriesView.this.movingReaction = false;
                PeerStoriesView.this.movingReactionProgress = 1.0f;
                PeerStoriesView.this.invalidate();
                boolean[] zArr2 = zArr;
                if (!zArr2[0]) {
                    zArr2[0] = true;
                    PeerStoriesView.this.drawReactionEffect = true;
                    try {
                        PeerStoriesView.this.performHapticFeedback(3);
                    } catch (Exception unused) {
                    }
                }
                storiesLikeButton.setAllowDrawReaction(true);
                storiesLikeButton.animateVisibleReaction();
                if (PeerStoriesView.this.reactionMoveDrawable != null) {
                    PeerStoriesView.this.reactionMoveDrawable.removeView(PeerStoriesView.this);
                    PeerStoriesView.this.reactionMoveDrawable = null;
                }
            }
        }

        @Override // org.telegram.ui.Components.ReactionsContainerLayout.ReactionsContainerDelegate
        public boolean needEnterText() {
            PeerStoriesView.this.delegate.requestAdjust(false);
            return false;
        }
    }

    public boolean needEnterText() {
        ChatActivityEnterView chatActivityEnterView = this.chatActivityEnterView;
        if (chatActivityEnterView == null) {
            return false;
        }
        boolean zIsKeyboardVisible = chatActivityEnterView.isKeyboardVisible();
        if (zIsKeyboardVisible) {
            this.chatActivityEnterView.showEmojiView();
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$needEnterText$54();
            }
        }, 300L);
        return zIsKeyboardVisible;
    }

    public /* synthetic */ void lambda$needEnterText$54() {
        this.delegate.requestAdjust(true);
    }

    public void setViewsThumbImageReceiver(float f, float f2, float f3, SelfStoriesPreviewView.ImageHolder imageHolder) {
        this.viewsThumbAlpha = f;
        this.viewsThumbScale = 1.0f / f2;
        this.viewsThumbPivotY = f3;
        if (this.viewsThumbImageReceiver == imageHolder) {
            return;
        }
        this.viewsThumbImageReceiver = imageHolder;
        if (imageHolder == null || imageHolder.receiver.getBitmap() == null) {
            return;
        }
        this.imageReceiver.updateStaticDrawableThump(imageHolder.receiver.getBitmap().copy(Bitmap.Config.ARGB_8888, false));
    }

    public static class SharedResources {
        public final Paint barPaint;
        private final Drawable bottomOverlayGradient;
        public Drawable deleteDrawable;
        private final Paint gradientBackgroundPaint;
        public final Drawable imageBackgroundDrawable;
        public Drawable likeDrawable;
        public Drawable likeDrawableFilled;
        public RLottieDrawable muteDrawable;
        public RLottieDrawable noSoundDrawable;
        public Drawable optionsDrawable;
        public Drawable pipDrawable;
        public Drawable repostDrawable;
        public final Paint selectedBarPaint;
        public Drawable shareDrawable;
        private final Drawable topOverlayGradient;
        public final BitmapShaderTools bitmapShaderTools = new BitmapShaderTools();
        private final RectF rect1 = new RectF();
        private final RectF rect2 = new RectF();
        private final RectF finalRect = new RectF();
        private final RectF borderRect = new RectF();
        private final RectF popupRect = new RectF();
        private final Paint dimPaint = new Paint();

        public SharedResources(Context context) {
            this.shareDrawable = ContextCompat.getDrawable(context, C2797R.drawable.media_share);
            this.likeDrawable = ContextCompat.getDrawable(context, C2797R.drawable.media_like);
            this.repostDrawable = ContextCompat.getDrawable(context, C2797R.drawable.media_repost);
            Drawable drawable = ContextCompat.getDrawable(context, C2797R.drawable.media_like_active);
            this.likeDrawableFilled = drawable;
            drawable.setColorFilter(new PorterDuffColorFilter(-53704, PorterDuff.Mode.MULTIPLY));
            this.optionsDrawable = ContextCompat.getDrawable(context, C2797R.drawable.media_more);
            this.pipDrawable = ContextCompat.getDrawable(context, C2797R.drawable.menu_stream_pip);
            this.deleteDrawable = ContextCompat.getDrawable(context, C2797R.drawable.msg_delete);
            this.muteDrawable = new RLottieDrawable(C2797R.raw.media_mute_unmute, "media_mute_unmute", AndroidUtilities.m1036dp(28.0f), AndroidUtilities.m1036dp(28.0f), true, null);
            RLottieDrawable rLottieDrawable = new RLottieDrawable(C2797R.raw.media_mute_unmute, "media_mute_unmute", AndroidUtilities.m1036dp(28.0f), AndroidUtilities.m1036dp(28.0f), true, null);
            this.noSoundDrawable = rLottieDrawable;
            rLottieDrawable.setCurrentFrame(20, false, true);
            this.noSoundDrawable.stop();
            Paint paint = new Paint(1);
            this.barPaint = paint;
            paint.setColor(1442840575);
            Paint paint2 = new Paint(1);
            this.selectedBarPaint = paint2;
            paint2.setColor(-1);
            int alphaComponent = ColorUtils.setAlphaComponent(-16777216, 102);
            this.topOverlayGradient = ContextCompat.getDrawable(context, C2797R.drawable.shadow_story_top);
            this.bottomOverlayGradient = ContextCompat.getDrawable(context, C2797R.drawable.shadow_story_bottom);
            Paint paint3 = new Paint();
            this.gradientBackgroundPaint = paint3;
            paint3.setColor(alphaComponent);
            this.imageBackgroundDrawable = new ColorDrawable(ColorUtils.blendARGB(-16777216, -1, 0.1f));
        }

        public void setIconMuted(boolean z, boolean z2) {
            if (!z2) {
                this.muteDrawable.setCurrentFrame(z ? 20 : 0, false);
                this.muteDrawable.setCustomEndFrame(z ? 20 : 0);
                return;
            }
            RLottieDrawable rLottieDrawable = this.muteDrawable;
            if (z) {
                if (rLottieDrawable.getCurrentFrame() > 20) {
                    this.muteDrawable.setCurrentFrame(0, false);
                }
                this.muteDrawable.setCustomEndFrame(20);
                this.muteDrawable.start();
                return;
            }
            if (rLottieDrawable.getCurrentFrame() == 0 || this.muteDrawable.getCurrentFrame() >= 43) {
                return;
            }
            this.muteDrawable.setCustomEndFrame(43);
            this.muteDrawable.start();
        }
    }

    public void editPrivacy(StoryPrivacyBottomSheet.StoryPrivacy storyPrivacy, final TL_stories.StoryItem storyItem) {
        this.delegate.showDialog(new StoryPrivacyBottomSheet(getContext(), storyItem.pinned ? Integer.MAX_VALUE : storyItem.expire_date - storyItem.date, this.resourcesProvider).setValue(storyPrivacy).enableSharing(false).isEdit(true).whenSelectedRules(new StoryPrivacyBottomSheet.DoneCallback() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda40
            @Override // org.telegram.ui.Stories.recorder.StoryPrivacyBottomSheet.DoneCallback
            public final void done(StoryPrivacyBottomSheet.StoryPrivacy storyPrivacy2, boolean z, boolean z2, boolean z3, boolean z4, TLRPC.InputPeer inputPeer, int i, Runnable runnable, Runnable runnable2) {
                this.f$0.lambda$editPrivacy$57(storyItem, storyPrivacy2, z, z2, z3, z4, inputPeer, i, runnable, runnable2);
            }
        }, false));
    }

    public /* synthetic */ void lambda$editPrivacy$57(final TL_stories.StoryItem storyItem, final StoryPrivacyBottomSheet.StoryPrivacy storyPrivacy, boolean z, boolean z2, boolean z3, boolean z4, TLRPC.InputPeer inputPeer, int i, final Runnable runnable, Runnable runnable2) {
        TL_stories.TL_stories_editStory tL_stories_editStory = new TL_stories.TL_stories_editStory();
        tL_stories_editStory.peer = MessagesController.getInstance(this.currentAccount).getInputPeer(storyItem.dialogId);
        tL_stories_editStory.f1458id = storyItem.f1454id;
        tL_stories_editStory.flags |= 4;
        tL_stories_editStory.privacy_rules = storyPrivacy.rules;
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(tL_stories_editStory, new RequestDelegate() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda51
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$editPrivacy$56(runnable, storyItem, storyPrivacy, tLObject, tL_error);
            }
        });
    }

    public /* synthetic */ void lambda$editPrivacy$56(final Runnable runnable, final TL_stories.StoryItem storyItem, final StoryPrivacyBottomSheet.StoryPrivacy storyPrivacy, TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda58
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$editPrivacy$55(runnable, tL_error, storyItem, storyPrivacy);
            }
        });
    }

    public /* synthetic */ void lambda$editPrivacy$55(Runnable runnable, TLRPC.TL_error tL_error, TL_stories.StoryItem storyItem, StoryPrivacyBottomSheet.StoryPrivacy storyPrivacy) {
        if (runnable != null) {
            runnable.run();
        }
        if (tL_error == null || "STORY_NOT_MODIFIED".equals(tL_error.text)) {
            storyItem.parsedPrivacy = storyPrivacy;
            storyItem.privacy = storyPrivacy.toValue();
            int i = storyPrivacy.type;
            storyItem.close_friends = i == 1;
            storyItem.contacts = i == 2;
            storyItem.selected_contacts = i == 3;
            MessagesController.getInstance(this.currentAccount).getStoriesController().updateStoryItem(storyItem.dialogId, storyItem, true, true);
            this.editedPrivacy = true;
            int i2 = storyPrivacy.type;
            if (i2 == 4) {
                BulletinFactory.m1142of(this.storyContainer, this.resourcesProvider).createSimpleBulletin(C2797R.raw.contact_check, LocaleController.getString("StorySharedToEveryone")).show();
            } else if (i2 == 1) {
                BulletinFactory.m1142of(this.storyContainer, this.resourcesProvider).createSimpleBulletin(C2797R.raw.contact_check, LocaleController.getString("StorySharedToCloseFriends")).show();
            } else if (i2 == 2) {
                boolean zIsEmpty = storyPrivacy.selectedUserIds.isEmpty();
                FrameLayout frameLayout = this.storyContainer;
                if (zIsEmpty) {
                    BulletinFactory.m1142of(frameLayout, this.resourcesProvider).createSimpleBulletin(C2797R.raw.contact_check, LocaleController.getString("StorySharedToAllContacts")).show();
                } else {
                    BulletinFactory.m1142of(frameLayout, this.resourcesProvider).createSimpleBulletin(C2797R.raw.contact_check, LocaleController.formatPluralString("StorySharedToAllContactsExcluded", storyPrivacy.selectedUserIds.size(), new Object[0])).show();
                }
            } else if (i2 == 3) {
                HashSet hashSet = new HashSet();
                hashSet.addAll(storyPrivacy.selectedUserIds);
                Iterator<ArrayList<Long>> it = storyPrivacy.selectedUserIdsByGroup.values().iterator();
                while (it.hasNext()) {
                    hashSet.addAll(it.next());
                }
                BulletinFactory.m1142of(this.storyContainer, this.resourcesProvider).createSimpleBulletin(C2797R.raw.contact_check, LocaleController.formatPluralString("StorySharedToContacts", hashSet.size(), new Object[0])).show();
            }
        } else {
            BulletinFactory.m1142of(this.storyContainer, this.resourcesProvider).createSimpleBulletin(C2797R.raw.error, LocaleController.getString(C2797R.string.UnknownError)).show();
        }
        updatePosition();
    }

    public boolean checkRecordLocked(final boolean z) {
        ChatActivityEnterView chatActivityEnterView = this.chatActivityEnterView;
        if (chatActivityEnterView == null || !chatActivityEnterView.isRecordLocked()) {
            return false;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), this.resourcesProvider);
        if (this.chatActivityEnterView.isInVideoMode()) {
            builder.setTitle(LocaleController.getString(C2797R.string.DiscardVideoMessageTitle));
            builder.setMessage(LocaleController.getString(C2797R.string.DiscardVideoMessageDescription));
        } else {
            builder.setTitle(LocaleController.getString(C2797R.string.DiscardVoiceMessageTitle));
            builder.setMessage(LocaleController.getString(C2797R.string.DiscardVoiceMessageDescription));
        }
        builder.setPositiveButton(LocaleController.getString(C2797R.string.DiscardVoiceMessageAction), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda2
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                this.f$0.lambda$checkRecordLocked$58(z, alertDialog, i);
            }
        });
        builder.setNegativeButton(LocaleController.getString(C2797R.string.Continue), null);
        this.delegate.showDialog(builder.create());
        return true;
    }

    public /* synthetic */ void lambda$checkRecordLocked$58(boolean z, AlertDialog alertDialog, int i) {
        ChatActivityEnterView chatActivityEnterView = this.chatActivityEnterView;
        if (chatActivityEnterView != null) {
            if (z) {
                this.storyViewer.close(true);
            } else {
                chatActivityEnterView.cancelRecordingAudioVideo();
            }
        }
    }

    public void animateOut(boolean z) {
        ValueAnimator valueAnimator = this.outAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.outT, z ? 1.0f : 0.0f);
        this.outAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Stories.PeerStoriesView$$ExternalSyntheticLambda53
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                this.f$0.lambda$animateOut$59(valueAnimator2);
            }
        });
        this.outAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Stories.PeerStoriesView.41
            final /* synthetic */ boolean val$out;

            public C692641(boolean z2) {
                z = z2;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                PeerStoriesView.this.outT = z ? 1.0f : 0.0f;
                PeerStoriesView.this.headerView.setTranslationY((-AndroidUtilities.m1036dp(8.0f)) * PeerStoriesView.this.outT);
                PeerStoriesView peerStoriesView = PeerStoriesView.this;
                peerStoriesView.headerView.setAlpha(1.0f - peerStoriesView.outT);
                PeerStoriesView.this.optionsIconView.setTranslationY((-AndroidUtilities.m1036dp(8.0f)) * PeerStoriesView.this.outT);
                PeerStoriesView.this.optionsIconView.setAlpha(1.0f - PeerStoriesView.this.outT);
                PeerStoriesView.this.pipIconView.setTranslationY((-AndroidUtilities.m1036dp(8.0f)) * PeerStoriesView.this.outT);
                PeerStoriesView.this.pipIconView.setAlpha(1.0f - PeerStoriesView.this.outT);
                PeerStoriesView.this.muteIconContainer.setTranslationY((-AndroidUtilities.m1036dp(8.0f)) * PeerStoriesView.this.outT);
                PeerStoriesView.this.muteIconContainer.setAlpha(PeerStoriesView.this.muteIconViewAlpha * (1.0f - PeerStoriesView.this.outT));
                if (PeerStoriesView.this.selfView != null) {
                    PeerStoriesView.this.selfView.setTranslationY(AndroidUtilities.m1036dp(8.0f) * PeerStoriesView.this.outT);
                    PeerStoriesView.this.selfView.setAlpha(1.0f - PeerStoriesView.this.outT);
                }
                if (PeerStoriesView.this.privacyButton != null) {
                    PeerStoriesView.this.privacyButton.setTranslationY((-AndroidUtilities.m1036dp(8.0f)) * PeerStoriesView.this.outT);
                    PeerStoriesView.this.privacyButton.setAlpha(1.0f - PeerStoriesView.this.outT);
                }
                PeerStoriesView.this.storyCaptionView.setAlpha(1.0f - PeerStoriesView.this.outT);
                Delegate delegate = PeerStoriesView.this.delegate;
                float progressToDismiss = delegate != null ? delegate.getProgressToDismiss() : 0.0f;
                float hideInterfaceAlpha = PeerStoriesView.this.getHideInterfaceAlpha();
                if (PeerStoriesView.this.likeButtonContainer != null) {
                    PeerStoriesView.this.likeButtonContainer.setAlpha((1.0f - progressToDismiss) * hideInterfaceAlpha * (1.0f - PeerStoriesView.this.outT));
                }
                if (PeerStoriesView.this.shareButton != null) {
                    PeerStoriesView.this.shareButton.setAlpha((1.0f - progressToDismiss) * hideInterfaceAlpha * (1.0f - PeerStoriesView.this.outT));
                }
                if (PeerStoriesView.this.repostButtonContainer != null) {
                    PeerStoriesView.this.repostButtonContainer.setAlpha(hideInterfaceAlpha * (1.0f - progressToDismiss) * (1.0f - PeerStoriesView.this.outT));
                }
                PeerStoriesView peerStoriesView2 = PeerStoriesView.this;
                ChatActivityEnterView chatActivityEnterView = peerStoriesView2.chatActivityEnterView;
                if (chatActivityEnterView != null) {
                    chatActivityEnterView.setAlpha(1.0f - peerStoriesView2.outT);
                    PeerStoriesView.this.invalidate();
                }
                PeerStoriesView.this.storyContainer.invalidate();
            }
        });
        this.outAnimator.setDuration(420L);
        this.outAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
        this.outAnimator.start();
    }

    public /* synthetic */ void lambda$animateOut$59(ValueAnimator valueAnimator) {
        this.outT = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.headerView.setTranslationY((-AndroidUtilities.m1036dp(8.0f)) * this.outT);
        this.headerView.setAlpha(1.0f - this.outT);
        this.optionsIconView.setTranslationY((-AndroidUtilities.m1036dp(8.0f)) * this.outT);
        this.optionsIconView.setAlpha(1.0f - this.outT);
        this.pipIconView.setTranslationY((-AndroidUtilities.m1036dp(8.0f)) * this.outT);
        this.pipIconView.setAlpha(1.0f - this.outT);
        this.muteIconContainer.setTranslationY((-AndroidUtilities.m1036dp(8.0f)) * this.outT);
        this.muteIconContainer.setAlpha(this.muteIconViewAlpha * (1.0f - this.outT));
        FrameLayout frameLayout = this.selfView;
        if (frameLayout != null) {
            frameLayout.setTranslationY(AndroidUtilities.m1036dp(8.0f) * this.outT);
            this.selfView.setAlpha(1.0f - this.outT);
        }
        StoryPrivacyButton storyPrivacyButton = this.privacyButton;
        if (storyPrivacyButton != null) {
            storyPrivacyButton.setTranslationY((-AndroidUtilities.m1036dp(8.0f)) * this.outT);
            this.privacyButton.setAlpha(1.0f - this.outT);
        }
        this.storyCaptionView.setAlpha(1.0f - this.outT);
        Delegate delegate = this.delegate;
        float progressToDismiss = delegate == null ? 0.0f : delegate.getProgressToDismiss();
        float hideInterfaceAlpha = getHideInterfaceAlpha();
        FrameLayout frameLayout2 = this.likeButtonContainer;
        if (frameLayout2 != null) {
            frameLayout2.setAlpha((1.0f - progressToDismiss) * hideInterfaceAlpha * (1.0f - this.outT));
        }
        ImageView imageView = this.shareButton;
        if (imageView != null) {
            imageView.setAlpha((1.0f - progressToDismiss) * hideInterfaceAlpha * (1.0f - this.outT));
        }
        FrameLayout frameLayout3 = this.repostButtonContainer;
        if (frameLayout3 != null) {
            frameLayout3.setAlpha(hideInterfaceAlpha * (1.0f - progressToDismiss) * (1.0f - this.outT));
        }
        ChatActivityEnterView chatActivityEnterView = this.chatActivityEnterView;
        if (chatActivityEnterView != null) {
            chatActivityEnterView.setAlpha(1.0f - this.outT);
            invalidate();
        }
        this.storyContainer.invalidate();
    }

    /* JADX INFO: renamed from: org.telegram.ui.Stories.PeerStoriesView$41 */
    public class C692641 extends AnimatorListenerAdapter {
        final /* synthetic */ boolean val$out;

        public C692641(boolean z2) {
            z = z2;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            PeerStoriesView.this.outT = z ? 1.0f : 0.0f;
            PeerStoriesView.this.headerView.setTranslationY((-AndroidUtilities.m1036dp(8.0f)) * PeerStoriesView.this.outT);
            PeerStoriesView peerStoriesView = PeerStoriesView.this;
            peerStoriesView.headerView.setAlpha(1.0f - peerStoriesView.outT);
            PeerStoriesView.this.optionsIconView.setTranslationY((-AndroidUtilities.m1036dp(8.0f)) * PeerStoriesView.this.outT);
            PeerStoriesView.this.optionsIconView.setAlpha(1.0f - PeerStoriesView.this.outT);
            PeerStoriesView.this.pipIconView.setTranslationY((-AndroidUtilities.m1036dp(8.0f)) * PeerStoriesView.this.outT);
            PeerStoriesView.this.pipIconView.setAlpha(1.0f - PeerStoriesView.this.outT);
            PeerStoriesView.this.muteIconContainer.setTranslationY((-AndroidUtilities.m1036dp(8.0f)) * PeerStoriesView.this.outT);
            PeerStoriesView.this.muteIconContainer.setAlpha(PeerStoriesView.this.muteIconViewAlpha * (1.0f - PeerStoriesView.this.outT));
            if (PeerStoriesView.this.selfView != null) {
                PeerStoriesView.this.selfView.setTranslationY(AndroidUtilities.m1036dp(8.0f) * PeerStoriesView.this.outT);
                PeerStoriesView.this.selfView.setAlpha(1.0f - PeerStoriesView.this.outT);
            }
            if (PeerStoriesView.this.privacyButton != null) {
                PeerStoriesView.this.privacyButton.setTranslationY((-AndroidUtilities.m1036dp(8.0f)) * PeerStoriesView.this.outT);
                PeerStoriesView.this.privacyButton.setAlpha(1.0f - PeerStoriesView.this.outT);
            }
            PeerStoriesView.this.storyCaptionView.setAlpha(1.0f - PeerStoriesView.this.outT);
            Delegate delegate = PeerStoriesView.this.delegate;
            float progressToDismiss = delegate != null ? delegate.getProgressToDismiss() : 0.0f;
            float hideInterfaceAlpha = PeerStoriesView.this.getHideInterfaceAlpha();
            if (PeerStoriesView.this.likeButtonContainer != null) {
                PeerStoriesView.this.likeButtonContainer.setAlpha((1.0f - progressToDismiss) * hideInterfaceAlpha * (1.0f - PeerStoriesView.this.outT));
            }
            if (PeerStoriesView.this.shareButton != null) {
                PeerStoriesView.this.shareButton.setAlpha((1.0f - progressToDismiss) * hideInterfaceAlpha * (1.0f - PeerStoriesView.this.outT));
            }
            if (PeerStoriesView.this.repostButtonContainer != null) {
                PeerStoriesView.this.repostButtonContainer.setAlpha(hideInterfaceAlpha * (1.0f - progressToDismiss) * (1.0f - PeerStoriesView.this.outT));
            }
            PeerStoriesView peerStoriesView2 = PeerStoriesView.this;
            ChatActivityEnterView chatActivityEnterView = peerStoriesView2.chatActivityEnterView;
            if (chatActivityEnterView != null) {
                chatActivityEnterView.setAlpha(1.0f - peerStoriesView2.outT);
                PeerStoriesView.this.invalidate();
            }
            PeerStoriesView.this.storyContainer.invalidate();
        }
    }

    public int getListPosition() {
        return this.listPosition;
    }

    public StoriesController getStoriesController() {
        return MessagesController.getInstance(this.currentAccount).getStoriesController();
    }
}
