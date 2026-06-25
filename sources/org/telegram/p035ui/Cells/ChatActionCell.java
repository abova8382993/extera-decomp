package org.telegram.p035ui.Cells;

import android.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AlignmentSpan;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.StateSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.core.graphics.ColorUtils;
import com.exteragram.messenger.ExteraConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicReference;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.BotInlineKeyboard;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.DownloadController;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessageSuggestionParams;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.browser.Browser;
import org.telegram.messenger.utils.tlutils.AmountUtils$Amount;
import org.telegram.messenger.utils.tlutils.AmountUtils$Currency;
import org.telegram.p035ui.ActionBar.ActionBar;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Cells.DialogCell;
import org.telegram.p035ui.Components.AlertsCreator;
import org.telegram.p035ui.Components.AnimatedEmojiSpan;
import org.telegram.p035ui.Components.AnimatedFloat;
import org.telegram.p035ui.Components.AvatarDrawable;
import org.telegram.p035ui.Components.BulletinFactory;
import org.telegram.p035ui.Components.ButtonBounce;
import org.telegram.p035ui.Components.CubicBezierInterpolator;
import org.telegram.p035ui.Components.Forum.ForumUtilities;
import org.telegram.p035ui.Components.LoadingDrawable;
import org.telegram.p035ui.Components.Premium.StarParticlesView;
import org.telegram.p035ui.Components.RLottieDrawable;
import org.telegram.p035ui.Components.RadialProgress2;
import org.telegram.p035ui.Components.RadialProgressView;
import org.telegram.p035ui.Components.Reactions.ReactionsLayoutInBubble;
import org.telegram.p035ui.Components.SuggestBirthdayActionLayout;
import org.telegram.p035ui.Components.Text;
import org.telegram.p035ui.Components.TopicSeparator;
import org.telegram.p035ui.Components.TypefaceSpan;
import org.telegram.p035ui.Components.URLSpanNoUnderline;
import org.telegram.p035ui.Components.spoilers.SpoilerEffect;
import org.telegram.p035ui.Gifts.GiftSheet;
import org.telegram.p035ui.GradientClip;
import org.telegram.p035ui.LaunchActivity;
import org.telegram.p035ui.Stars.GiftOfferSheet;
import org.telegram.p035ui.Stars.StarGiftSheet;
import org.telegram.p035ui.Stars.StarGiftUniqueActionLayout;
import org.telegram.p035ui.Stars.StarsIntroActivity;
import org.telegram.p035ui.Stories.StoriesUtilities;
import org.telegram.p035ui.Stories.recorder.HintView2;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p034tl.TL_payments;
import org.telegram.tgnet.p034tl.TL_stars;

/* JADX INFO: loaded from: classes3.dex */
public class ChatActionCell extends BaseCell implements DownloadController.FileDownloadProgressListener, NotificationCenter.NotificationCenterDelegate, IMessageCell {
    private static Map<Integer, String> monthsToEmoticon;
    private int TAG;
    private SpannableStringBuilder accessibilityText;
    private boolean actionPressed;
    private int adaptiveEmojiColor;
    private ColorFilter adaptiveEmojiColorFilter;
    private AnimatedEmojiSpan.EmojiGroupedSpans animatedEmojiStack;
    private boolean attachedToWindow;
    private AvatarDrawable avatarDrawable;
    StoriesUtilities.AvatarStoryParams avatarStoryParams;
    private int backgroundButtonTop;
    private int backgroundHeight;
    private int backgroundLeft;
    private Path backgroundPath;
    private final Path backgroundPath2;
    private RectF backgroundRect;
    private int backgroundRectHeight;
    private int backgroundRight;
    public SuggestBirthdayActionLayout birthdayLayout;
    private final Path botButtonPath;
    private final float[] botButtonRadii;
    private ArrayList<BotButton> botButtons;
    private BotInlineKeyboard.Source botInlineButtons;
    private final ButtonBounce bounce;
    private boolean buttonClickableAsImage;
    private boolean canDrawInParent;
    private GiftSheet.CardBackground cardBackground;
    private Path clipPath;
    private int currentAccount;
    private MessageObject currentMessageObject;
    private ImageLocation currentVideoLocation;
    private int customDate;
    private CharSequence customText;
    private ChatActionCellDelegate delegate;
    private float dimAmount;
    private final Paint dimPaint;
    public boolean firstInChat;
    private boolean forceWasUnread;
    private boolean giftButtonPressed;
    private RectF giftButtonRect;
    private TLRPC.VideoSize giftEffectAnimation;
    private int giftPremiumAdditionalHeight;
    private StaticLayout giftPremiumButtonLayout;
    private float giftPremiumButtonWidth;
    private Text giftPremiumReleasedText;
    private StaticLayout giftPremiumSubtitleLayout;
    private TextLayout giftPremiumText;
    private GradientClip giftPremiumTextClip;
    private boolean giftPremiumTextCollapsed;
    private int giftPremiumTextCollapsedHeight;
    private AnimatedFloat giftPremiumTextExpandedAnimated;
    private Text giftPremiumTextMore;
    private int giftPremiumTextMoreH;
    private int giftPremiumTextMoreX;
    private int giftPremiumTextMoreY;
    private boolean giftPremiumTextUncollapsed;
    private StaticLayout giftPremiumTitleLayout;
    private boolean giftRectEmpty;
    private int giftRectSize;
    private Paint giftReleasedBackgroundPaint;
    private CornerPathEffect giftRibbonPaintEffect;
    private ColorMatrixColorFilter giftRibbonPaintFilter;
    private boolean giftRibbonPaintFilterDark;
    private Path giftRibbonPath;
    private Text giftRibbonText;
    private TLRPC.Document giftSticker;
    private ImageReceiver.ImageReceiverDelegate giftStickerDelegate;
    private TextPaint giftSubtitlePaint;
    private TextPaint giftTextPaint;
    private TextPaint giftTitlePaint;
    private boolean hasReplyMessage;
    private boolean imagePressed;
    private ImageReceiver imageReceiver;
    private boolean invalidateColors;
    private Runnable invalidateListener;
    private boolean invalidatePath;
    private View invalidateWithParent;
    private boolean invalidatesParent;
    public boolean isAllChats;
    public boolean isBotForum;
    public boolean isForum;
    public boolean isMonoForum;
    public boolean isSideMenuEnabled;
    public boolean isSideMenued;
    private boolean isSpoilerRevealing;
    private float lastTouchX;
    private float lastTouchY;
    private ArrayList<Integer> lineHeights;
    private ArrayList<Integer> lineWidths;
    private LoadingDrawable loadingDrawable;
    private boolean offerExpired;
    private View.OnClickListener onActionClick;
    private View.OnLongClickListener onActionLongClick;
    private int overriddenMaxWidth;
    private int overrideBackground;
    private Paint overrideBackgroundPaint;
    private int overrideText;
    private TextPaint overrideTextPaint;
    private int pressedBotButton;
    private URLSpan pressedLink;
    private final int[] pressedState;
    private int previousWidth;
    float progressToProgress;
    RadialProgressView progressView;
    private RadialProgress2 radialProgress;
    private final float[] radii;
    public final ReactionsLayoutInBubble reactionsLayoutInBubble;
    private RectF rect;
    private View rippleView;
    private StaticLayout settingWallpaperLayout;
    TextPaint settingWallpaperPaint;
    private float settingWallpaperProgress;
    private StaticLayout settingWallpaperProgressTextLayout;
    public boolean showTopicSeparator;
    public float sideMenuAlpha;
    public int sideMenuWidth;
    private SpoilerEffect spoilerPressed;
    public List<SpoilerEffect> spoilers;
    private Stack<SpoilerEffect> spoilersPool;
    public final StarGiftUniqueActionLayout starGiftLayout;
    public float starGiftLayoutX;
    public float starGiftLayoutY;
    private StarParticlesView.Drawable starParticlesDrawable;
    private Path starsPath;
    private int starsSize;
    private int stickerSize;
    private int textHeight;
    private StaticLayout textLayout;
    TextPaint textPaint;
    private boolean textPressed;
    private int textWidth;
    private int textX;
    private int textXLeft;
    private int textY;
    private Theme.ResourcesProvider themeDelegate;
    private int titleHeight;
    private StaticLayout titleLayout;
    private int titleXLeft;
    public TopicSeparator topicSeparator;
    private int topicSeparatorTopPadding;
    public final TransitionParams transitionParams;
    private float viewTop;
    private float viewTranslationX;
    private boolean visiblePartSet;
    private Drawable wallpaperPreviewDrawable;
    private boolean wasLayout;

    /* JADX INFO: loaded from: classes6.dex */
    public interface ChatActionCellDelegate {
        default boolean canDrawOutboundsContent() {
            return true;
        }

        default void didClickButton(ChatActionCell chatActionCell) {
        }

        default void didClickImage(ChatActionCell chatActionCell) {
        }

        default boolean didLongPress(ChatActionCell chatActionCell, float f, float f2) {
            return false;
        }

        default void didOpenPremiumGift(ChatActionCell chatActionCell, TLRPC.TL_premiumGiftOption tL_premiumGiftOption, String str, boolean z) {
        }

        default void didOpenPremiumGiftChannel(ChatActionCell chatActionCell, String str, boolean z) {
        }

        default void didPressReaction(ChatActionCell chatActionCell, TLRPC.ReactionCount reactionCount, boolean z, float f, float f2) {
        }

        default void didPressReplyMessage(ChatActionCell chatActionCell, int i) {
        }

        default void didPressTaskLink(ChatActionCell chatActionCell, int i, int i2) {
        }

        default void forceUpdate(ChatActionCell chatActionCell, boolean z) {
        }

        default BaseFragment getBaseFragment() {
            return null;
        }

        default long getDialogId() {
            return 0L;
        }

        default long getTopicId() {
            return 0L;
        }

        default void needOpenInviteLink(TLRPC.TL_chatInviteExported tL_chatInviteExported) {
        }

        default void needOpenUserProfile(long j) {
        }

        default void needShowEffectOverlay(ChatActionCell chatActionCell, TLRPC.Document document, TLRPC.VideoSize videoSize) {
        }

        default void onTopicClick(ChatActionCell chatActionCell) {
        }
    }

    @Override // org.telegram.p035ui.Cells.IMessageCell
    public /* bridge */ /* synthetic */ ImageReceiver getAvatarImage() {
        return super.getAvatarImage();
    }

    @Override // org.telegram.p035ui.Cells.IMessageCell
    public /* bridge */ /* synthetic */ float getCheckBoxTranslation() {
        return super.getCheckBoxTranslation();
    }

    @Override // org.telegram.p035ui.Cells.IMessageCell
    public /* bridge */ /* synthetic */ MessageObject.GroupedMessagePosition getCurrentPosition() {
        return super.getCurrentPosition();
    }

    @Override // org.telegram.p035ui.Cells.IMessageCell
    public float getDeltaBottom() {
        return 0.0f;
    }

    public float getDeltaLeft() {
        return 0.0f;
    }

    public float getDeltaRight() {
        return 0.0f;
    }

    public float getDeltaTop() {
        return 0.0f;
    }

    @Override // org.telegram.p035ui.Cells.IMessageCell
    public /* bridge */ /* synthetic */ int getLayoutHeight() {
        return super.getLayoutHeight();
    }

    @Override // org.telegram.p035ui.Cells.IMessageCell
    public /* bridge */ /* synthetic */ float getSlidingOffsetX() {
        return super.getSlidingOffsetX();
    }

    public boolean isFloating() {
        return false;
    }

    @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
    public void onFailedDownload(String str, boolean z) {
    }

    @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
    public void onProgressDownload(String str, long j, long j2) {
    }

    @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
    public void onProgressUpload(String str, long j, long j2, boolean z) {
    }

    static {
        HashMap map = new HashMap();
        monthsToEmoticon = map;
        map.put(1, "1⃣");
        monthsToEmoticon.put(3, "2⃣");
        monthsToEmoticon.put(6, "3⃣");
        monthsToEmoticon.put(12, "4⃣");
        monthsToEmoticon.put(24, "5⃣");
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        MessageObject messageObject;
        if (i == NotificationCenter.startSpoilers) {
            setSpoilersSuppressed(false);
            return;
        }
        if (i == NotificationCenter.stopSpoilers) {
            setSpoilersSuppressed(true);
            return;
        }
        if (i == NotificationCenter.didUpdatePremiumGiftStickers || i == NotificationCenter.starGiftsLoaded || i == NotificationCenter.didUpdateTonGiftStickers) {
            MessageObject messageObject2 = this.currentMessageObject;
            if (messageObject2 != null) {
                setMessageObject(messageObject2, true);
                return;
            }
            return;
        }
        if (i == NotificationCenter.diceStickersDidLoad && Objects.equals(objArr[0], UserConfig.getInstance(this.currentAccount).premiumGiftsStickerPack) && (messageObject = this.currentMessageObject) != null) {
            setMessageObject(messageObject, true);
        }
    }

    public void setSpoilersSuppressed(boolean z) {
        Iterator<SpoilerEffect> it = this.spoilers.iterator();
        while (it.hasNext()) {
            it.next().setSuppressUpdates(z);
        }
    }

    public void setInvalidateWithParent(View view) {
        this.invalidateWithParent = view;
    }

    public boolean hasButton() {
        MessageObject messageObject = this.currentMessageObject;
        return (messageObject == null || !isButtonLayout(messageObject) || this.giftPremiumButtonLayout == null) ? false : true;
    }

    public void setShowTopic(boolean z) {
        if (this.showTopicSeparator != z) {
            this.showTopicSeparator = z;
            invalidateOutbounds();
            invalidate();
        }
    }

    /* JADX INFO: loaded from: classes6.dex */
    public class TextLayout {
        public AnimatedEmojiSpan.EmojiGroupedSpans emoji;
        public StaticLayout layout;
        public TextPaint paint;
        public int width;

        /* JADX INFO: renamed from: x */
        public float f1498x;

        /* JADX INFO: renamed from: y */
        public float f1499y;
        public List<SpoilerEffect> spoilers = new ArrayList();
        public final AtomicReference<Layout> patchedLayout = new AtomicReference<>();

        public TextLayout() {
        }

        public void setText(CharSequence charSequence, TextPaint textPaint, int i) {
            this.paint = textPaint;
            this.width = i;
            this.layout = new StaticLayout(charSequence, textPaint, i, Layout.Alignment.ALIGN_CENTER, 1.1f, 0.0f, false);
            if (ChatActionCell.this.currentMessageObject == null || !ChatActionCell.this.currentMessageObject.isSpoilersRevealed) {
                SpoilerEffect.addSpoilers(ChatActionCell.this, this.layout, -1, i, null, this.spoilers);
            } else {
                List<SpoilerEffect> list = this.spoilers;
                if (list != null) {
                    list.clear();
                }
            }
            attach();
        }

        public void attach() {
            this.emoji = AnimatedEmojiSpan.update(0, (View) ChatActionCell.this, false, this.emoji, this.layout);
        }

        public void detach() {
            AnimatedEmojiSpan.release(ChatActionCell.this, this.emoji);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ImageReceiver imageReceiver, boolean z, boolean z2, boolean z3) {
        RLottieDrawable lottieAnimation;
        ChatActionCellDelegate chatActionCellDelegate;
        if (!z || (lottieAnimation = this.imageReceiver.getLottieAnimation()) == null) {
            return;
        }
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject != null && !messageObject.playedGiftAnimation) {
            messageObject.playedGiftAnimation = true;
            lottieAnimation.setCurrentFrame(0, false);
            AndroidUtilities.runOnUIThread(new ChatActionCell$$ExternalSyntheticLambda13(lottieAnimation));
            if (messageObject.wasUnread || this.forceWasUnread) {
                messageObject.wasUnread = false;
                this.forceWasUnread = false;
                try {
                    performHapticFeedback(3, 2);
                } catch (Exception unused) {
                }
                if (getContext() instanceof LaunchActivity) {
                    ((LaunchActivity) getContext()).getFireworksOverlay().start();
                }
                TLRPC.VideoSize videoSize = this.giftEffectAnimation;
                if (videoSize == null || (chatActionCellDelegate = this.delegate) == null) {
                    return;
                }
                chatActionCellDelegate.needShowEffectOverlay(this, this.giftSticker, videoSize);
                return;
            }
            return;
        }
        if (lottieAnimation.getCurrentFrame() < 1) {
            lottieAnimation.stop();
            lottieAnimation.setCurrentFrame(lottieAnimation.getFramesCount() - 1, false);
        }
    }

    public ChatActionCell(Context context) {
        this(context, false, null);
    }

    public ChatActionCell(Context context, boolean z, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        this.bounce = new ButtonBounce(this);
        this.currentAccount = UserConfig.selectedAccount;
        this.avatarStoryParams = new StoriesUtilities.AvatarStoryParams(false);
        this.showTopicSeparator = true;
        this.giftButtonRect = new RectF();
        this.spoilers = new ArrayList();
        this.spoilersPool = new Stack<>();
        this.reactionsLayoutInBubble = new ReactionsLayoutInBubble(this);
        this.overrideBackground = -1;
        this.overrideText = -1;
        this.lineWidths = new ArrayList<>();
        this.lineHeights = new ArrayList<>();
        this.backgroundPath = new Path();
        this.rect = new RectF();
        this.invalidatePath = true;
        this.invalidateColors = false;
        this.giftPremiumTextUncollapsed = false;
        this.giftPremiumTextCollapsed = false;
        this.giftPremiumTextExpandedAnimated = new AnimatedFloat(this, 0L, 320L, CubicBezierInterpolator.EASE_OUT_QUINT);
        this.buttonClickableAsImage = true;
        this.giftTitlePaint = new TextPaint(1);
        this.giftTextPaint = new TextPaint(1);
        this.giftSubtitlePaint = new TextPaint(1);
        this.radialProgress = new RadialProgress2(this);
        this.giftStickerDelegate = new ImageReceiver.ImageReceiverDelegate() { // from class: org.telegram.ui.Cells.ChatActionCell$$ExternalSyntheticLambda5
            @Override // org.telegram.messenger.ImageReceiver.ImageReceiverDelegate
            public final void didSetImage(ImageReceiver imageReceiver, boolean z2, boolean z3, boolean z4) {
                this.f$0.lambda$new$0(imageReceiver, z2, z3, z4);
            }
        };
        this.starsPath = new Path();
        this.botButtons = new ArrayList<>();
        this.dimPaint = new Paint(1);
        this.backgroundPath2 = new Path();
        this.radii = new float[8];
        this.botButtonRadii = new float[8];
        this.botButtonPath = new Path();
        this.pressedState = new int[]{R.attr.state_enabled, R.attr.state_pressed};
        this.transitionParams = new TransitionParams();
        this.avatarStoryParams.drawSegments = false;
        this.canDrawInParent = z;
        this.themeDelegate = resourcesProvider;
        ImageReceiver imageReceiver = new ImageReceiver(this);
        this.imageReceiver = imageReceiver;
        imageReceiver.setRoundRadius(ExteraConfig.getAvatarCorners(AndroidUtilities.roundMessageSize, true));
        this.avatarDrawable = new AvatarDrawable();
        this.TAG = DownloadController.getInstance(this.currentAccount).generateObserverTag();
        this.starGiftLayout = new StarGiftUniqueActionLayout(this.currentAccount, this, resourcesProvider);
        this.giftTitlePaint.setTextSize(TypedValue.applyDimension(1, 16.0f, getResources().getDisplayMetrics()));
        this.giftSubtitlePaint.setTextSize(TypedValue.applyDimension(1, 15.0f, getResources().getDisplayMetrics()));
        this.giftTextPaint.setTextSize(TypedValue.applyDimension(1, 15.0f, getResources().getDisplayMetrics()));
        View view = new View(context);
        this.rippleView = view;
        view.setBackground(Theme.createSelectorDrawable(Theme.multAlpha(-16777216, 0.1f), 7, AndroidUtilities.m1036dp(16.0f)));
        this.rippleView.setVisibility(8);
        addView(this.rippleView);
        StarParticlesView.Drawable drawable = new StarParticlesView.Drawable(10);
        this.starParticlesDrawable = drawable;
        drawable.type = 100;
        drawable.isCircle = false;
        drawable.roundEffect = true;
        drawable.useRotate = false;
        drawable.useBlur = true;
        drawable.checkBounds = true;
        drawable.size1 = 1;
        drawable.f1646k3 = 0.98f;
        drawable.f1645k2 = 0.98f;
        drawable.f1644k1 = 0.98f;
        drawable.paused = false;
        drawable.speedScale = 0.0f;
        drawable.minLifeTime = 750L;
        drawable.randLifeTime = 750;
        drawable.init();
    }

    public void setDelegate(ChatActionCellDelegate chatActionCellDelegate) {
        this.delegate = chatActionCellDelegate;
    }

    public ChatActionCellDelegate getDelegate() {
        return this.delegate;
    }

    public void setCustomDate(int i, boolean z, boolean z2) {
        String dateChat;
        int i2 = this.customDate;
        if (i2 == i || i2 / 3600 == i / 3600) {
            return;
        }
        if (!z) {
            dateChat = LocaleController.formatDateChat(i);
        } else if (i == 2147483646) {
            dateChat = LocaleController.getString("MessageScheduledUntilOnline", C2797R.string.MessageScheduledUntilOnline);
        } else {
            dateChat = LocaleController.formatString("MessageScheduledOn", C2797R.string.MessageScheduledOn, LocaleController.formatDateChat(i));
        }
        this.customDate = i;
        CharSequence charSequence = this.customText;
        if (charSequence == null || !TextUtils.equals(dateChat, charSequence)) {
            this.customText = dateChat;
            this.accessibilityText = null;
            updateTextInternal(z2);
        }
    }

    private void updateTextInternal(boolean z) {
        if (getMeasuredWidth() != 0) {
            createLayout(this.customText, getMeasuredWidth());
            invalidate();
        }
        if (this.wasLayout) {
            buildLayout();
        } else if (z) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Cells.ChatActionCell$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.requestLayout();
                }
            });
        } else {
            requestLayout();
        }
    }

    public void setCustomText(CharSequence charSequence) {
        this.customText = charSequence;
        if (charSequence != null) {
            updateTextInternal(false);
        }
    }

    public void setOverrideColor(int i, int i2) {
        this.overrideBackground = i;
        this.overrideText = i2;
    }

    public void setMessageObject(MessageObject messageObject) {
        setMessageObject(messageObject, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x01e0  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0335  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0383  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x03b5  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x044f  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x0497  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x04af  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x04c9  */
    /* JADX WARN: Removed duplicated region for block: B:263:0x060b  */
    /* JADX WARN: Removed duplicated region for block: B:351:0x079f  */
    /* JADX WARN: Removed duplicated region for block: B:371:0x082c  */
    /* JADX WARN: Removed duplicated region for block: B:387:0x08a9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setMessageObject(org.telegram.messenger.MessageObject r35, boolean r36) {
        /*
            Method dump skipped, instruction units count: 2378
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Cells.ChatActionCell.setMessageObject(org.telegram.messenger.MessageObject, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setMessageObject$1() {
        ChatActionCellDelegate chatActionCellDelegate = this.delegate;
        if (chatActionCellDelegate != null) {
            chatActionCellDelegate.onTopicClick(this);
        }
    }

    private float getUploadingInfoProgress(MessageObject messageObject) {
        MessagesController messagesController;
        String str;
        if (messageObject == null) {
            return 1.0f;
        }
        try {
            if (messageObject.type == 22 && (str = (messagesController = MessagesController.getInstance(this.currentAccount)).uploadingWallpaper) != null && TextUtils.equals(messageObject.messageOwner.action.wallpaper.uploadingImage, str)) {
                return messagesController.uploadingWallpaperInfo.uploadingProgress;
            }
            return 1.0f;
        } catch (Exception e) {
            FileLog.m1048e(e);
            return 1.0f;
        }
    }

    @Override // org.telegram.p035ui.Cells.IMessageCell
    public MessageObject getMessageObject() {
        return this.currentMessageObject;
    }

    public ReactionsLayoutInBubble getReactionsLayout() {
        return this.reactionsLayoutInBubble;
    }

    @Override // org.telegram.p035ui.Cells.IMessageCell
    public void didPressReactionFromLayout(TLRPC.ReactionCount reactionCount, boolean z, float f, float f2) {
        ChatActionCellDelegate chatActionCellDelegate = this.delegate;
        if (chatActionCellDelegate != null) {
            chatActionCellDelegate.didPressReaction(this, reactionCount, z, f, f2);
        }
    }

    public ImageReceiver getPhotoImage() {
        return this.imageReceiver;
    }

    public void setVisiblePart(float f, int i) {
        this.visiblePartSet = true;
        this.backgroundHeight = i;
        this.viewTop = f;
        this.viewTranslationX = 0.0f;
    }

    public void setVisiblePart(float f, float f2, int i, float f3) {
        this.visiblePartSet = true;
        this.backgroundHeight = i;
        this.viewTop = f;
        this.viewTranslationX = f2;
        this.dimAmount = f3;
        this.dimPaint.setColor(ColorUtils.setAlphaComponent(-16777216, (int) (f3 * 255.0f)));
        invalidate();
    }

    @Override // org.telegram.p035ui.Cells.BaseCell
    public boolean onLongPress() {
        View.OnLongClickListener onLongClickListener;
        if (this.actionPressed && (onLongClickListener = this.onActionLongClick) != null && onLongClickListener.onLongClick(this)) {
            this.actionPressed = false;
            return true;
        }
        ChatActionCellDelegate chatActionCellDelegate = this.delegate;
        if (chatActionCellDelegate != null) {
            return chatActionCellDelegate.didLongPress(this, this.lastTouchX, this.lastTouchY);
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        View view = this.rippleView;
        RectF rectF = this.giftButtonRect;
        view.layout((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.attachedToWindow = false;
        DownloadController.getInstance(this.currentAccount).removeLoadingFileObserver(this);
        this.imageReceiver.onDetachedFromWindow();
        setStarsPaused(true);
        this.wasLayout = false;
        AnimatedEmojiSpan.release(this, this.animatedEmojiStack);
        TextLayout textLayout = this.giftPremiumText;
        if (textLayout != null) {
            textLayout.detach();
        }
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.didUpdatePremiumGiftStickers);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.didUpdateTonGiftStickers);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.starGiftsLoaded);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.diceStickersDidLoad);
        this.avatarStoryParams.onDetachFromWindow();
        this.transitionParams.onDetach();
        this.starGiftLayout.detach();
        this.reactionsLayoutInBubble.onDetachFromWindow();
        TopicSeparator topicSeparator = this.topicSeparator;
        if (topicSeparator != null) {
            topicSeparator.detach();
        }
        SuggestBirthdayActionLayout suggestBirthdayActionLayout = this.birthdayLayout;
        if (suggestBirthdayActionLayout != null) {
            suggestBirthdayActionLayout.detach();
        }
    }

    public boolean isCellAttachedToWindow() {
        return this.attachedToWindow;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        ChatActionCellDelegate chatActionCellDelegate;
        super.onAttachedToWindow();
        this.attachedToWindow = true;
        this.imageReceiver.onAttachedToWindow();
        setStarsPaused(false);
        this.animatedEmojiStack = AnimatedEmojiSpan.update(0, this, (!this.canDrawInParent || (chatActionCellDelegate = this.delegate) == null || chatActionCellDelegate.canDrawOutboundsContent()) ? false : true, this.animatedEmojiStack, this.textLayout);
        TextLayout textLayout = this.giftPremiumText;
        if (textLayout != null) {
            textLayout.attach();
        }
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.didUpdatePremiumGiftStickers);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.didUpdateTonGiftStickers);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.starGiftsLoaded);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.diceStickersDidLoad);
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject != null && messageObject.type == 21) {
            setMessageObject(messageObject, true);
        }
        this.starGiftLayout.attach();
        this.reactionsLayoutInBubble.onAttachToWindow();
        TopicSeparator topicSeparator = this.topicSeparator;
        if (topicSeparator != null) {
            topicSeparator.attach();
        }
        SuggestBirthdayActionLayout suggestBirthdayActionLayout = this.birthdayLayout;
        if (suggestBirthdayActionLayout != null) {
            suggestBirthdayActionLayout.attach();
        }
    }

    private void setStarsPaused(boolean z) {
        StarParticlesView.Drawable drawable = this.starParticlesDrawable;
        if (z == drawable.paused) {
            return;
        }
        drawable.paused = z;
        if (z) {
            drawable.pausedTime = System.currentTimeMillis();
            return;
        }
        for (int i = 0; i < this.starParticlesDrawable.particles.size(); i++) {
            this.starParticlesDrawable.particles.get(i).lifeTime += System.currentTimeMillis() - this.starParticlesDrawable.pausedTime;
        }
        invalidate();
    }

    /* JADX WARN: Removed duplicated region for block: B:210:0x0340  */
    /* JADX WARN: Removed duplicated region for block: B:319:0x0538  */
    /* JADX WARN: Removed duplicated region for block: B:320:0x053c  */
    @Override // android.view.View
    @android.annotation.SuppressLint({"ClickableViewAccessibility"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r17) {
        /*
            Method dump skipped, instruction units count: 1357
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Cells.ChatActionCell.onTouchEvent(android.view.MotionEvent):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTouchEvent$3() {
        post(new Runnable() { // from class: org.telegram.ui.Cells.ChatActionCell$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onTouchEvent$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTouchEvent$2() {
        this.isSpoilerRevealing = false;
        getMessageObject().isSpoilersRevealed = true;
        List<SpoilerEffect> list = this.giftPremiumText.spoilers;
        if (list != null) {
            list.clear();
        }
        invalidate();
    }

    private void openPremiumGiftChannel() {
        if (this.delegate != null) {
            final TLRPC.TL_messageActionGiftCode tL_messageActionGiftCode = (TLRPC.TL_messageActionGiftCode) this.currentMessageObject.messageOwner.action;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Cells.ChatActionCell$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$openPremiumGiftChannel$4(tL_messageActionGiftCode);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openPremiumGiftChannel$4(TLRPC.TL_messageActionGiftCode tL_messageActionGiftCode) {
        this.delegate.didOpenPremiumGiftChannel(this, tL_messageActionGiftCode.slug, false);
    }

    private boolean isSelfGiftCode() {
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null) {
            return false;
        }
        TLRPC.Message message = messageObject.messageOwner;
        TLRPC.MessageAction messageAction = message.action;
        if (((messageAction instanceof TLRPC.TL_messageActionGiftCode) || (messageAction instanceof TLRPC.TL_messageActionGiftStars)) && (message.from_id instanceof TLRPC.TL_peerUser)) {
            return UserObject.isUserSelf(MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(this.currentMessageObject.messageOwner.from_id.user_id)));
        }
        return false;
    }

    public void setOnActionClickListener(View.OnClickListener onClickListener) {
        this.onActionClick = onClickListener;
    }

    public void setOnActionLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.onActionLongClick = onLongClickListener;
    }

    private boolean isGiftCode() {
        MessageObject messageObject = this.currentMessageObject;
        return messageObject != null && (messageObject.messageOwner.action instanceof TLRPC.TL_messageActionGiftCode);
    }

    private void openPremiumGiftPreview() {
        final TLRPC.TL_premiumGiftOption tL_premiumGiftOption = new TLRPC.TL_premiumGiftOption();
        TLRPC.MessageAction messageAction = this.currentMessageObject.messageOwner.action;
        tL_premiumGiftOption.amount = messageAction.amount;
        tL_premiumGiftOption.months = messageAction.months;
        tL_premiumGiftOption.currency = messageAction.currency;
        final String str = null;
        if (isGiftCode() && !isSelfGiftCode()) {
            str = ((TLRPC.TL_messageActionGiftCode) this.currentMessageObject.messageOwner.action).slug;
        }
        if (this.delegate != null) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Cells.ChatActionCell$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$openPremiumGiftPreview$5(tL_premiumGiftOption, str);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openPremiumGiftPreview$5(TLRPC.TL_premiumGiftOption tL_premiumGiftOption, String str) {
        this.delegate.didOpenPremiumGift(this, tL_premiumGiftOption, str, false);
    }

    private void openStarsGiftTransaction() {
        TLRPC.Message message;
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null || (message = messageObject.messageOwner) == null) {
            return;
        }
        TLRPC.MessageAction messageAction = message.action;
        if (messageAction instanceof TLRPC.TL_messageActionGiftStars) {
            Context context = getContext();
            int i = this.currentAccount;
            TLRPC.Message message2 = this.currentMessageObject.messageOwner;
            StarsIntroActivity.showTransactionSheet(context, i, message2.date, message2.from_id, message2.peer_id, (TLRPC.TL_messageActionGiftStars) message2.action, this.avatarStoryParams.resourcesProvider);
            return;
        }
        if (messageAction instanceof TLRPC.TL_messageActionPrizeStars) {
            Context context2 = getContext();
            int i2 = this.currentAccount;
            TLRPC.Message message3 = this.currentMessageObject.messageOwner;
            StarsIntroActivity.showTransactionSheet(context2, i2, message3.date, message3.from_id, message3.peer_id, (TLRPC.TL_messageActionPrizeStars) message3.action, this.avatarStoryParams.resourcesProvider);
            return;
        }
        if (messageAction instanceof TLRPC.TL_messageActionGiftTon) {
            Context context3 = getContext();
            int i3 = this.currentAccount;
            TLRPC.Message message4 = this.currentMessageObject.messageOwner;
            StarsIntroActivity.showTransactionSheet(context3, i3, message4.date, message4.from_id, message4.peer_id, (TLRPC.TL_messageActionGiftTon) message4.action, this.avatarStoryParams.resourcesProvider);
            return;
        }
        if (messageAction instanceof TLRPC.TL_messageActionStarGift) {
            if (((TLRPC.TL_messageActionStarGift) messageAction).forceIn) {
                return;
            }
            new StarGiftSheet(getContext(), this.currentAccount, this.currentMessageObject.getDialogId(), this.themeDelegate).set(this.currentMessageObject).show();
            return;
        }
        if (messageAction instanceof TLRPC.TL_messageActionStarGiftUnique) {
            if (((TLRPC.TL_messageActionStarGiftUnique) messageAction).gift.burned) {
                BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
                if (safeLastFragment == null) {
                    return;
                }
                BulletinFactory.m1143of(safeLastFragment).createSimpleBulletin(C2797R.raw.fire_on, LocaleController.getString(C2797R.string.UniqueGiftNotFoundBurned)).show();
                return;
            }
            new StarGiftSheet(getContext(), this.currentAccount, this.currentMessageObject.getDialogId(), this.themeDelegate).set(this.currentMessageObject).show();
            return;
        }
        if (messageAction instanceof TLRPC.TL_messageActionSetChatTheme) {
            TLRPC.ChatTheme chatTheme = ((TLRPC.TL_messageActionSetChatTheme) messageAction).theme;
            if (chatTheme instanceof TLRPC.TL_chatThemeUniqueGift) {
                TL_stars.StarGift starGift = ((TLRPC.TL_chatThemeUniqueGift) chatTheme).gift;
                if (starGift instanceof TL_stars.TL_starGiftUnique) {
                    new StarGiftSheet(getContext(), this.currentAccount, this.currentMessageObject.getDialogId(), this.themeDelegate).set(starGift.slug, (TL_stars.TL_starGiftUnique) starGift, null).show();
                }
            }
        }
    }

    private void openStarsNeedSheet() {
        MessageSuggestionParams messageSuggestionParamsObtainSuggestionOffer = this.currentMessageObject.obtainSuggestionOffer();
        AmountUtils$Amount amountUtils$Amount = messageSuggestionParamsObtainSuggestionOffer.amount;
        if (amountUtils$Amount == null || amountUtils$Amount.currency != AmountUtils$Currency.STARS) {
            return;
        }
        new StarsIntroActivity.StarsNeededSheet(getContext(), this.themeDelegate, messageSuggestionParamsObtainSuggestionOffer.amount.asDecimal(), 13, ForumUtilities.getMonoForumTitle(this.currentAccount, this.currentMessageObject.getDialogId(), true), null, this.currentMessageObject.getDialogId()).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openLink(CharacterStyle characterStyle) {
        if (this.delegate == null || !(characterStyle instanceof URLSpan)) {
            return;
        }
        String url = ((URLSpan) characterStyle).getURL();
        if (url.startsWith("task")) {
            this.delegate.didPressTaskLink(this, this.currentMessageObject.getReplyMsgId(), Integer.parseInt(url.substring(5)));
            return;
        }
        if (url.startsWith("topic")) {
            URLSpan uRLSpan = this.pressedLink;
            if (uRLSpan instanceof URLSpanNoUnderline) {
                TLObject object = ((URLSpanNoUnderline) uRLSpan).getObject();
                if (object instanceof TLRPC.TL_forumTopic) {
                    ForumUtilities.openTopic(this.delegate.getBaseFragment(), -this.delegate.getDialogId(), (TLRPC.TL_forumTopic) object, 0);
                    return;
                }
                return;
            }
        }
        if (url.startsWith("invite")) {
            URLSpan uRLSpan2 = this.pressedLink;
            if (uRLSpan2 instanceof URLSpanNoUnderline) {
                TLObject object2 = ((URLSpanNoUnderline) uRLSpan2).getObject();
                if (object2 instanceof TLRPC.TL_chatInviteExported) {
                    this.delegate.needOpenInviteLink((TLRPC.TL_chatInviteExported) object2);
                    return;
                }
                return;
            }
        }
        if (url.startsWith("game")) {
            this.delegate.didPressReplyMessage(this, this.currentMessageObject.getReplyMsgId());
        } else if (url.startsWith("http")) {
            Browser.openUrl(getContext(), url);
        } else {
            this.delegate.needOpenUserProfile(Long.parseLong(url));
        }
    }

    public void setOverrideTextMaxWidth(int i) {
        this.overriddenMaxWidth = i;
    }

    private boolean isMessageActionSuggestedPostApproval() {
        TLRPC.Message message;
        MessageObject messageObject = this.currentMessageObject;
        return (messageObject == null || (message = messageObject.messageOwner) == null || !(message.action instanceof TLRPC.TL_messageActionSuggestedPostApproval)) ? false : true;
    }

    private void createLayout(CharSequence charSequence, int i) {
        TextPaint textPaint;
        CharSequence charSequenceReplaceEmoji;
        ChatActionCellDelegate chatActionCellDelegate;
        TLRPC.Message message;
        MessageObject messageObject;
        int i2;
        int iM1036dp = i - AndroidUtilities.m1036dp(30.0f);
        if (this.isSideMenued) {
            iM1036dp -= AndroidUtilities.m1036dp(64.0f);
        }
        if (isMessageActionSuggestedPostApproval()) {
            iM1036dp = Math.min(iM1036dp - AndroidUtilities.m1036dp(this.isSideMenued ? 28.0f : 82.0f), AndroidUtilities.m1036dp(272.0f));
        }
        if (iM1036dp < 0) {
            return;
        }
        int i3 = this.overriddenMaxWidth;
        if (i3 > 0) {
            iM1036dp = Math.min(i3, iM1036dp);
        }
        int i4 = iM1036dp;
        this.invalidatePath = true;
        if (isMessageActionSuggestedPostApproval() || ((messageObject = this.currentMessageObject) != null && ((i2 = messageObject.type) == 34 || i2 == 35))) {
            textPaint = (TextPaint) getThemedPaint("paintChatActionText3");
        } else if (messageObject != null && messageObject.drawServiceWithDefaultTypeface) {
            textPaint = (TextPaint) getThemedPaint("paintChatActionText2");
        } else {
            textPaint = (TextPaint) getThemedPaint("paintChatActionText");
        }
        TextPaint textPaint2 = textPaint;
        textPaint2.linkColor = textPaint2.getColor();
        if (isMessageActionSuggestedPostApproval()) {
            if (charSequence instanceof Spannable) {
                Spannable spannable = (Spannable) charSequence;
                for (Emoji.EmojiSpan emojiSpan : (Emoji.EmojiSpan[]) spannable.getSpans(0, spannable.length(), Emoji.EmojiSpan.class)) {
                    spannable.removeSpan(emojiSpan);
                }
            }
            charSequenceReplaceEmoji = Emoji.replaceEmoji(charSequence, textPaint2.getFontMetricsInt(), false, null, 0, 0.85f, 0);
        } else {
            charSequenceReplaceEmoji = charSequence;
        }
        StaticLayout staticLayout = new StaticLayout(charSequenceReplaceEmoji, textPaint2, i4, isMessageActionSuggestedPostApproval() ? Layout.Alignment.ALIGN_NORMAL : Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        CharSequence charSequence2 = charSequenceReplaceEmoji;
        this.textLayout = staticLayout;
        this.titleLayout = null;
        MessageObject messageObject2 = this.currentMessageObject;
        if (messageObject2 != null && (message = messageObject2.messageOwner) != null) {
            TLRPC.MessageAction messageAction = message.action;
            if ((messageAction instanceof TLRPC.TL_messageActionSuggestedPostApproval) && !((TLRPC.TL_messageActionSuggestedPostApproval) messageAction).rejected && !((TLRPC.TL_messageActionSuggestedPostApproval) messageAction).balance_too_low) {
                this.titleLayout = new StaticLayout(Emoji.replaceEmoji(AndroidUtilities.replaceTags(LocaleController.getString(C2797R.string.SuggestionAgreementReached)), textPaint2.getFontMetricsInt(), false, null, 0, 1.0f, 0), textPaint2, i4, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
            }
        }
        this.animatedEmojiStack = AnimatedEmojiSpan.update(0, this, (!this.canDrawInParent || (chatActionCellDelegate = this.delegate) == null || chatActionCellDelegate.canDrawOutboundsContent()) ? false : true, this.animatedEmojiStack, this.textLayout);
        this.textHeight = 0;
        this.textWidth = 0;
        this.titleHeight = 0;
        StaticLayout staticLayout2 = this.titleLayout;
        if (staticLayout2 != null) {
            int height = staticLayout2.getHeight();
            this.titleHeight = height;
            this.titleHeight = height + AndroidUtilities.m1036dp(12.0f);
        }
        MessageObject messageObject3 = this.currentMessageObject;
        if (messageObject3 == null || !messageObject3.isRepostPreview) {
            try {
                int lineCount = this.textLayout.getLineCount();
                for (int i5 = 0; i5 < lineCount; i5++) {
                    try {
                        float lineWidth = this.textLayout.getLineWidth(i5);
                        float f = i4;
                        if (lineWidth > f) {
                            lineWidth = f;
                        }
                        this.textHeight = (int) Math.max(this.textHeight, Math.ceil(this.textLayout.getLineBottom(i5)));
                        this.textWidth = (int) Math.max(this.textWidth, Math.ceil(lineWidth));
                    } catch (Exception e) {
                        FileLog.m1048e(e);
                        return;
                    }
                }
            } catch (Exception e2) {
                FileLog.m1048e(e2);
            }
        }
        this.textX = (i - this.textWidth) / 2;
        int iM1036dp2 = AndroidUtilities.m1036dp(7.0f);
        this.textY = iM1036dp2;
        if (this.titleLayout != null) {
            this.textY = iM1036dp2 + this.titleHeight + AndroidUtilities.m1036dp(11.0f);
        }
        this.textXLeft = (i - (isMessageActionSuggestedPostApproval() ? this.textWidth : this.textLayout.getWidth())) / 2;
        this.titleXLeft = (i - i4) / 2;
        this.spoilersPool.addAll(this.spoilers);
        this.spoilers.clear();
        if (charSequence2 instanceof Spannable) {
            StaticLayout staticLayout3 = this.textLayout;
            int i6 = this.textX;
            SpoilerEffect.addSpoilers(this, staticLayout3, i6, i6 + this.textWidth, (Spannable) charSequence2, this.spoilersPool, this.spoilers, null);
        }
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        int iM1036dp;
        float f;
        float f2;
        float fM1036dp;
        float f3;
        float fM1036dp2;
        TLRPC.Message message;
        int i3;
        int i4;
        int i5;
        int iM1036dp2;
        int i6;
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null && this.customText == null) {
            setMeasuredDimension(View.MeasureSpec.getSize(i), this.topicSeparatorTopPadding + this.textHeight + AndroidUtilities.m1036dp(14.0f));
            return;
        }
        if (isButtonLayout(messageObject)) {
            this.giftRectSize = Math.min((int) (AndroidUtilities.isTablet() ? AndroidUtilities.getMinTabletSide() * 0.6f : (AndroidUtilities.displaySize.x * 0.62f) - AndroidUtilities.m1036dp(34.0f)), ((AndroidUtilities.displaySize.y - ActionBar.getCurrentActionBarHeight()) - AndroidUtilities.statusBarHeight) - AndroidUtilities.m1036dp(64.0f));
            if ((!AndroidUtilities.isTablet() && ((i6 = messageObject.type) == 18 || i6 == 30 || isMessageActionSuggestedPostApproval())) || messageObject.type == 35) {
                this.giftRectSize = (int) (this.giftRectSize * 1.2f);
            }
            this.stickerSize = this.giftRectSize - AndroidUtilities.m1036dp(106.0f);
            if (messageObject.type == 31) {
                this.giftRectSize = Math.min(this.giftRectSize, AndroidUtilities.m1036dp(192.0f));
                this.stickerSize = AndroidUtilities.m1036dp(78.0f);
            }
            if (messageObject.type == 33) {
                this.giftRectSize = Math.min(this.giftRectSize, AndroidUtilities.m1036dp(220.0f));
                this.stickerSize = AndroidUtilities.m1036dp(78.0f);
            }
            boolean zIsNewStyleButtonLayout = isNewStyleButtonLayout();
            ImageReceiver imageReceiver = this.imageReceiver;
            if (zIsNewStyleButtonLayout) {
                imageReceiver.setRoundRadius(ExteraConfig.getAvatarCorners(this.stickerSize * 0.7f, true));
            } else {
                imageReceiver.setRoundRadius(0);
            }
        }
        int iMax = Math.max(AndroidUtilities.m1036dp(30.0f), View.MeasureSpec.getSize(i));
        if (this.previousWidth != iMax) {
            this.wasLayout = true;
            this.previousWidth = iMax;
            buildLayout();
        }
        if (messageObject == null) {
            iM1036dp = 0;
        } else {
            if (messageObject.type == 11) {
                i5 = AndroidUtilities.roundMessageSize;
                iM1036dp2 = AndroidUtilities.m1036dp(10.0f);
            } else {
                if (isButtonLayout(messageObject)) {
                    i5 = this.giftRectSize;
                    iM1036dp2 = AndroidUtilities.m1036dp(12.0f);
                }
                iM1036dp = 0;
            }
            iM1036dp = i5 + iM1036dp2;
        }
        if (this.starGiftLayout.has()) {
            iM1036dp = (this.starGiftLayout.repost ? 0 : this.textY + this.textHeight + AndroidUtilities.m1036dp(16.0f)) + ((int) this.starGiftLayout.getHeight()) + AndroidUtilities.m1036dp(8.0f);
            ReactionsLayoutInBubble reactionsLayoutInBubble = this.reactionsLayoutInBubble;
            if (!reactionsLayoutInBubble.isEmpty) {
                reactionsLayoutInBubble.totalHeight = reactionsLayoutInBubble.height + AndroidUtilities.m1036dp(8.0f);
                i4 = this.reactionsLayoutInBubble.totalHeight;
                iM1036dp += i4;
            }
            f = 14.0f;
            f3 = 24.0f;
        } else {
            SuggestBirthdayActionLayout suggestBirthdayActionLayout = this.birthdayLayout;
            if (suggestBirthdayActionLayout != null) {
                iM1036dp = suggestBirthdayActionLayout.height() + AndroidUtilities.m1036dp(12.0f);
                ReactionsLayoutInBubble reactionsLayoutInBubble2 = this.reactionsLayoutInBubble;
                if (!reactionsLayoutInBubble2.isEmpty) {
                    reactionsLayoutInBubble2.totalHeight = reactionsLayoutInBubble2.height + AndroidUtilities.m1036dp(8.0f);
                    i4 = this.reactionsLayoutInBubble.totalHeight;
                    iM1036dp += i4;
                }
            } else if (isButtonLayout(messageObject)) {
                boolean zIsGiftChannel = isGiftChannel(messageObject);
                f = 14.0f;
                int imageSize = getImageSize(messageObject);
                boolean zIsNewStyleButtonLayout2 = isNewStyleButtonLayout();
                int i7 = this.textY;
                if (zIsNewStyleButtonLayout2) {
                    f2 = 10.0f;
                    int iM1036dp3 = i7 + this.textHeight + AndroidUtilities.m1036dp(4.0f);
                    int iM1036dp4 = AndroidUtilities.m1036dp(16.0f);
                    if (imageSize > 0) {
                        iM1036dp4 = (iM1036dp4 * 2) + imageSize;
                    }
                    int i8 = iM1036dp3 + iM1036dp4;
                    TextLayout textLayout = this.giftPremiumText;
                    fM1036dp = i8 + (textLayout == null ? 0 : textLayout.layout.getHeight() + AndroidUtilities.m1036dp(4.0f));
                } else {
                    f2 = 10.0f;
                    fM1036dp = i7 + this.textHeight + (this.giftRectSize * 0.075f) + imageSize + AndroidUtilities.m1036dp(4.0f) + (this.giftPremiumText == null ? 0 : r12.layout.getHeight() + AndroidUtilities.m1036dp(4.0f));
                }
                this.giftPremiumAdditionalHeight = 0;
                if (this.giftPremiumTitleLayout != null) {
                    float height = fM1036dp + r12.getHeight();
                    if (this.giftPremiumTitleLayout.getLineCount() > 1) {
                        f3 = 24.0f;
                        this.giftPremiumAdditionalHeight += this.giftPremiumTitleLayout.getHeight() - this.giftPremiumTitleLayout.getLineTop(1);
                    } else {
                        f3 = 24.0f;
                    }
                    fM1036dp2 = height + AndroidUtilities.m1036dp(zIsGiftChannel ? 6.0f : 0.0f);
                    if (this.giftPremiumSubtitleLayout != null) {
                        fM1036dp2 += r5.getHeight() + AndroidUtilities.m1036dp(9.0f);
                    }
                    if (this.giftPremiumReleasedText != null) {
                        fM1036dp2 += AndroidUtilities.m1036dp(f3);
                    }
                } else {
                    f3 = 24.0f;
                    fM1036dp2 = fM1036dp - AndroidUtilities.m1036dp(12.0f);
                    this.giftPremiumAdditionalHeight -= AndroidUtilities.m1036dp(30.0f);
                }
                TextLayout textLayout2 = this.giftPremiumText;
                int height2 = textLayout2 == null ? 0 : textLayout2.layout.getHeight();
                if (this.giftPremiumText == null) {
                    this.giftPremiumAdditionalHeight = 0;
                } else if (this.giftPremiumSubtitleLayout != null) {
                    this.giftPremiumAdditionalHeight += height2 + AndroidUtilities.m1036dp(f2);
                } else {
                    MessageObject messageObject2 = this.currentMessageObject;
                    if (messageObject2.type == 18 || messageObject2.isStarGiftAction()) {
                        this.giftPremiumAdditionalHeight += height2 - AndroidUtilities.m1036dp(this.giftPremiumButtonLayout == null ? 0.0f : f2);
                    } else if (this.currentMessageObject.type == 30) {
                        this.giftPremiumAdditionalHeight += height2 - AndroidUtilities.m1036dp(20.0f);
                    } else if (this.giftPremiumTextCollapsed) {
                        this.giftPremiumAdditionalHeight += height2;
                    } else if (this.giftPremiumText.layout.getLineCount() > 2) {
                        this.giftPremiumAdditionalHeight += ((this.giftPremiumText.layout.getLineBottom(0) - this.giftPremiumText.layout.getLineTop(0)) * this.giftPremiumText.layout.getLineCount()) - 2;
                    }
                }
                if (this.giftPremiumReleasedText != null) {
                    this.giftPremiumAdditionalHeight += AndroidUtilities.m1036dp(f3);
                }
                int iM1036dp5 = this.giftPremiumAdditionalHeight - AndroidUtilities.m1036dp(zIsGiftChannel ? 14.0f : 0.0f);
                this.giftPremiumAdditionalHeight = iM1036dp5;
                iM1036dp += iM1036dp5;
                int iM1036dp6 = this.textHeight + iM1036dp + AndroidUtilities.m1036dp(14.0f);
                StaticLayout staticLayout = this.giftPremiumButtonLayout;
                if (staticLayout != null) {
                    float height3 = fM1036dp2 + ((((iM1036dp6 - fM1036dp2) - (staticLayout != null ? staticLayout.getHeight() : 0)) - AndroidUtilities.m1036dp(8.0f)) / 2.0f);
                    if (this.currentMessageObject.isStarGiftAction()) {
                        height3 += AndroidUtilities.m1036dp(4.0f);
                    }
                    float f4 = (this.previousWidth - this.giftPremiumButtonWidth) / 2.0f;
                    this.giftButtonRect.set(f4 - AndroidUtilities.m1036dp(18.0f), height3 - AndroidUtilities.m1036dp(8.0f), f4 + this.giftPremiumButtonWidth + AndroidUtilities.m1036dp(18.0f), height3 + (this.giftPremiumButtonLayout != null ? r15.getHeight() : 0) + AndroidUtilities.m1036dp(8.0f));
                } else {
                    iM1036dp -= AndroidUtilities.m1036dp(40.0f);
                    this.giftPremiumAdditionalHeight -= AndroidUtilities.m1036dp(40.0f);
                    MessageObject messageObject3 = this.currentMessageObject;
                    if (messageObject3 != null && (message = messageObject3.messageOwner) != null && (message.action instanceof TLRPC.TL_messageActionStarGift)) {
                        iM1036dp -= AndroidUtilities.m1036dp(8.0f);
                        this.giftPremiumAdditionalHeight -= AndroidUtilities.m1036dp(8.0f);
                    }
                }
                int measuredWidth = getMeasuredWidth() << (getMeasuredHeight() + 16);
                this.starParticlesDrawable.rect.set(this.giftButtonRect);
                this.starParticlesDrawable.rect2.set(this.giftButtonRect);
                if (this.starsSize != measuredWidth) {
                    this.starsSize = measuredWidth;
                    this.starParticlesDrawable.resetPositions();
                }
                if (isNewStyleButtonLayout()) {
                    int iM1036dp7 = this.textY + this.textHeight + AndroidUtilities.m1036dp(4.0f);
                    this.backgroundRectHeight = 0;
                    int iM1036dp8 = AndroidUtilities.m1036dp(16.0f);
                    if (imageSize > 0) {
                        iM1036dp8 = (iM1036dp8 * 2) + imageSize;
                    }
                    this.backgroundRectHeight = iM1036dp8;
                    StaticLayout staticLayout2 = this.giftPremiumSubtitleLayout;
                    if (staticLayout2 != null) {
                        this.backgroundRectHeight = iM1036dp8 + staticLayout2.getHeight() + AndroidUtilities.m1036dp(f2);
                    }
                    if (this.giftPremiumReleasedText != null) {
                        this.backgroundRectHeight += AndroidUtilities.m1036dp(f3);
                    }
                    StaticLayout staticLayout3 = this.giftPremiumTitleLayout;
                    if (staticLayout3 != null) {
                        this.backgroundRectHeight += staticLayout3.getHeight() + AndroidUtilities.m1036dp(4.0f);
                    }
                    int i9 = this.backgroundRectHeight;
                    TextLayout textLayout3 = this.giftPremiumText;
                    int height4 = i9 + (textLayout3 != null ? textLayout3.layout.getHeight() : 0);
                    this.backgroundRectHeight = height4;
                    float f5 = (this.previousWidth - this.giftPremiumButtonWidth) / 2.0f;
                    if (this.giftPremiumButtonLayout != null) {
                        this.backgroundButtonTop = height4 + iM1036dp7 + AndroidUtilities.m1036dp(32.0f);
                        this.giftButtonRect.set(f5 - AndroidUtilities.m1036dp(18.0f), this.backgroundButtonTop, f5 + this.giftPremiumButtonWidth + AndroidUtilities.m1036dp(18.0f), this.backgroundButtonTop + this.giftPremiumButtonLayout.getHeight() + (AndroidUtilities.m1036dp(8.0f) * 2));
                        this.backgroundRectHeight = (int) (this.backgroundRectHeight + AndroidUtilities.m1036dp(32.0f) + this.giftButtonRect.height());
                    } else if (!isMessageActionSuggestedPostApproval() && (messageObject == null || ((i3 = messageObject.type) != 34 && i3 != 33 && i3 != 35))) {
                        this.giftButtonRect.set(f5 - AndroidUtilities.m1036dp(18.0f), this.backgroundButtonTop, f5 + this.giftPremiumButtonWidth + AndroidUtilities.m1036dp(18.0f), this.backgroundButtonTop + AndroidUtilities.m1036dp(17.0f) + (AndroidUtilities.m1036dp(8.0f) * 2));
                        this.backgroundRectHeight += AndroidUtilities.m1036dp(17.0f);
                    }
                    int iM1036dp9 = this.backgroundRectHeight + AndroidUtilities.m1036dp(16.0f);
                    this.backgroundRectHeight = iM1036dp9;
                    int iM1036dp10 = iM1036dp7 + iM1036dp9 + AndroidUtilities.m1036dp(6.0f);
                    ReactionsLayoutInBubble reactionsLayoutInBubble3 = this.reactionsLayoutInBubble;
                    if (!reactionsLayoutInBubble3.isEmpty) {
                        reactionsLayoutInBubble3.totalHeight = reactionsLayoutInBubble3.height + AndroidUtilities.m1036dp(8.0f);
                        iM1036dp10 += this.reactionsLayoutInBubble.totalHeight;
                    }
                    iM1036dp = iM1036dp10;
                    if (this.botInlineButtons != null) {
                        iM1036dp += AndroidUtilities.m1036dp(44.0f);
                    }
                }
            }
            f = 14.0f;
            f3 = 24.0f;
        }
        if (this.currentMessageObject != null) {
            ReactionsLayoutInBubble reactionsLayoutInBubble4 = this.reactionsLayoutInBubble;
            if (!reactionsLayoutInBubble4.isEmpty) {
                reactionsLayoutInBubble4.totalHeight = reactionsLayoutInBubble4.height + AndroidUtilities.m1036dp(8.0f);
                iM1036dp += this.reactionsLayoutInBubble.totalHeight;
            }
        }
        if (isMessageActionSuggestedPostApproval()) {
            iM1036dp += this.titleHeight + AndroidUtilities.m1036dp(f3);
        }
        if (messageObject != null && isNewStyleButtonLayout()) {
            setMeasuredDimension(iMax, this.topicSeparatorTopPadding + iM1036dp);
        } else {
            setMeasuredDimension(iMax, this.topicSeparatorTopPadding + this.textHeight + iM1036dp + AndroidUtilities.m1036dp(f));
        }
        this.reactionsLayoutInBubble.f1657y = (getMeasuredHeight() - getPaddingTop()) - this.reactionsLayoutInBubble.totalHeight;
    }

    private boolean isNewStyleButtonLayout() {
        MessageObject messageObject;
        int i;
        if (this.starGiftLayout.has() || this.birthdayLayout != null || (i = (messageObject = this.currentMessageObject).type) == 31 || i == 33 || i == 35 || i == 34 || i == 21 || i == 22 || messageObject.isStoryMention()) {
            return true;
        }
        TLRPC.Message message = this.currentMessageObject.messageOwner;
        if (message == null) {
            return false;
        }
        TLRPC.MessageAction messageAction = message.action;
        if (messageAction instanceof TLRPC.TL_messageActionSuggestedPostApproval) {
            return ((TLRPC.TL_messageActionSuggestedPostApproval) messageAction).balance_too_low || ((TLRPC.TL_messageActionSuggestedPostApproval) messageAction).rejected;
        }
        return false;
    }

    private int getImageSize(MessageObject messageObject) {
        int i;
        int iM1036dp = this.stickerSize;
        if (messageObject.type == 21 || isNewStyleButtonLayout()) {
            iM1036dp = AndroidUtilities.m1036dp(78.0f);
        }
        if (isMessageActionSuggestedPostApproval() || (i = messageObject.type) == 34 || i == 35) {
            return 0;
        }
        return iM1036dp;
    }

    /* JADX WARN: Removed duplicated region for block: B:165:0x03c4  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x03c6  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x03f1  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x046a  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x0473  */
    /* JADX WARN: Removed duplicated region for block: B:257:0x05e7  */
    /* JADX WARN: Removed duplicated region for block: B:264:0x0606  */
    /* JADX WARN: Removed duplicated region for block: B:267:0x0613  */
    /* JADX WARN: Removed duplicated region for block: B:270:0x0619  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0198  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x01c6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void buildLayout() {
        /*
            Method dump skipped, instruction units count: 2985
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Cells.ChatActionCell.buildLayout():void");
    }

    private CharSequence createOption(String str, int i) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(AndroidUtilities.replaceArrows(str, false, AndroidUtilities.m1036dp(6.0f), -AndroidUtilities.m1036dp(1.3f), 0.8f, i));
        spannableStringBuilder.insert(0, (CharSequence) "*");
        spannableStringBuilder.setSpan(new DialogCell.FixedWidthSpan(AndroidUtilities.m1036dp(18.0f)), 0, 1, 33);
        if (Build.VERSION.SDK_INT >= 29) {
            ChatActionCell$$ExternalSyntheticApiModelOutline1.m1127m();
            spannableStringBuilder.setSpan(ChatActionCell$$ExternalSyntheticApiModelOutline0.m1126m(AndroidUtilities.m1036dp(12.0f)), 0, spannableStringBuilder.length(), 33);
        }
        spannableStringBuilder.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL), 0, spannableStringBuilder.length(), 33);
        return spannableStringBuilder;
    }

    private void createGiftPremiumChannelLayouts() {
        String string;
        SpannableStringBuilder spannableStringBuilder;
        int iM1036dp = this.giftRectSize - AndroidUtilities.m1036dp(16.0f);
        this.giftTitlePaint.setTextSize(AndroidUtilities.m1036dp(14.0f));
        this.giftTextPaint.setTextSize(AndroidUtilities.m1036dp(13.0f));
        TLRPC.TL_messageActionGiftCode tL_messageActionGiftCode = (TLRPC.TL_messageActionGiftCode) this.currentMessageObject.messageOwner.action;
        int i = tL_messageActionGiftCode.months;
        TLRPC.Chat chat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-DialogObject.getPeerDialogId(tL_messageActionGiftCode.boost_peer)));
        String str = chat == null ? null : chat.title;
        boolean z = tL_messageActionGiftCode.via_giveaway;
        if (tL_messageActionGiftCode.unclaimed) {
            string = LocaleController.getString("BoostingUnclaimedPrize", C2797R.string.BoostingUnclaimedPrize);
        } else {
            string = LocaleController.getString("BoostingCongratulations", C2797R.string.BoostingCongratulations);
        }
        String pluralString = i == 12 ? LocaleController.formatPluralString("BoldYears", 1, new Object[0]) : LocaleController.formatPluralString("BoldMonths", i, new Object[0]);
        if (z) {
            if (tL_messageActionGiftCode.unclaimed) {
                spannableStringBuilder = new SpannableStringBuilder(AndroidUtilities.replaceTags(LocaleController.formatString(C2797R.string.BoostingYouHaveUnclaimedPrize, str)));
                spannableStringBuilder.append((CharSequence) "\n\n");
                spannableStringBuilder.append((CharSequence) AndroidUtilities.replaceTags(LocaleController.formatString(C2797R.string.BoostingUnclaimedPrizeDuration, pluralString)));
            } else {
                spannableStringBuilder = new SpannableStringBuilder(AndroidUtilities.replaceTags(LocaleController.formatString(C2797R.string.BoostingReceivedPrizeFrom, str)));
                spannableStringBuilder.append((CharSequence) "\n\n");
                spannableStringBuilder.append((CharSequence) AndroidUtilities.replaceTags(LocaleController.formatString(C2797R.string.BoostingReceivedPrizeDuration, pluralString)));
            }
        } else {
            spannableStringBuilder = new SpannableStringBuilder(AndroidUtilities.replaceTags(str == null ? LocaleController.getString(C2797R.string.BoostingReceivedGiftNoName) : LocaleController.formatString("BoostingReceivedGiftFrom", C2797R.string.BoostingReceivedGiftFrom, str)));
            spannableStringBuilder.append((CharSequence) "\n\n");
            spannableStringBuilder.append((CharSequence) AndroidUtilities.replaceTags(LocaleController.formatString(C2797R.string.BoostingReceivedGiftDuration, pluralString)));
        }
        String string2 = LocaleController.getString("BoostingReceivedGiftOpenBtn", C2797R.string.BoostingReceivedGiftOpenBtn);
        SpannableStringBuilder spannableStringBuilderValueOf = SpannableStringBuilder.valueOf(string);
        spannableStringBuilderValueOf.setSpan(new TypefaceSpan(AndroidUtilities.bold()), 0, spannableStringBuilderValueOf.length(), 33);
        TextPaint textPaint = this.giftTitlePaint;
        Layout.Alignment alignment = Layout.Alignment.ALIGN_CENTER;
        this.giftPremiumTitleLayout = new StaticLayout(spannableStringBuilderValueOf, textPaint, iM1036dp, alignment, 1.1f, 0.0f, false);
        this.giftPremiumSubtitleLayout = null;
        this.giftPremiumReleasedText = null;
        TextLayout textLayout = this.giftPremiumText;
        if (textLayout != null) {
            textLayout.detach();
        }
        TextLayout textLayout2 = new TextLayout();
        this.giftPremiumText = textLayout2;
        textLayout2.setText(spannableStringBuilder, this.giftTextPaint, iM1036dp);
        SpannableStringBuilder spannableStringBuilderValueOf2 = SpannableStringBuilder.valueOf(string2);
        spannableStringBuilderValueOf2.setSpan(new TypefaceSpan(AndroidUtilities.bold()), 0, spannableStringBuilderValueOf2.length(), 33);
        this.giftPremiumTextCollapsed = false;
        this.giftPremiumTextCollapsedHeight = 0;
        this.giftPremiumTextMore = null;
        StaticLayout staticLayout = new StaticLayout(spannableStringBuilderValueOf2, (TextPaint) getThemedPaint("paintChatActionText"), iM1036dp, alignment, 1.0f, 0.0f, false);
        this.giftPremiumButtonLayout = staticLayout;
        this.buttonClickableAsImage = true;
        this.giftPremiumButtonWidth = measureLayoutWidth(staticLayout);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r4v7 */
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
    private void createGiftPremiumLayouts(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, boolean z, CharSequence charSequence5, int i, CharSequence charSequence6, int i2, boolean z2, boolean z3) {
        ?? r4;
        int i3;
        int iCutInFancyHalf;
        CharSequence charSequenceReplaceEmoji = charSequence4;
        int iM1036dp = i2 - AndroidUtilities.m1036dp(16.0f);
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject != null && messageObject.type == 30) {
            iM1036dp -= AndroidUtilities.m1036dp(16.0f);
        }
        int i4 = iM1036dp;
        if (charSequence != null) {
            MessageObject messageObject2 = this.currentMessageObject;
            if (messageObject2 != null && messageObject2.type == 30) {
                this.giftTitlePaint.setTextSize(AndroidUtilities.m1036dp(14.0f));
            } else {
                this.giftTitlePaint.setTextSize(AndroidUtilities.m1036dp(16.0f));
            }
            SpannableStringBuilder spannableStringBuilderValueOf = SpannableStringBuilder.valueOf(charSequence);
            spannableStringBuilderValueOf.setSpan(new TypefaceSpan(AndroidUtilities.bold()), 0, spannableStringBuilderValueOf.length(), 33);
            r4 = 0;
            this.giftPremiumTitleLayout = new StaticLayout(spannableStringBuilderValueOf, this.giftTitlePaint, i4, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        } else {
            r4 = 0;
            this.giftPremiumTitleLayout = null;
        }
        if (charSequence2 != null) {
            this.giftSubtitlePaint.setTextSize(AndroidUtilities.m1036dp(13.0f));
            this.giftPremiumSubtitleLayout = new StaticLayout(charSequence2, this.giftSubtitlePaint, i4, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        } else {
            this.giftPremiumSubtitleLayout = null;
        }
        if (charSequence3 != null) {
            Text text = new Text(charSequence3, 10.0f);
            this.giftPremiumReleasedText = text;
            text.paint.linkColor = -1;
        } else {
            this.giftPremiumReleasedText = null;
        }
        MessageObject messageObject3 = this.currentMessageObject;
        if (messageObject3 != null && messageObject3.type == 35) {
            this.giftTextPaint.setTextSize(AndroidUtilities.m1036dp(14.3f));
        } else if (messageObject3 != null && (isNewStyleButtonLayout() || (i3 = this.currentMessageObject.type) == 30 || i3 == 18 || i3 == 31 || i3 == 33)) {
            this.giftTextPaint.setTextSize(AndroidUtilities.m1036dp(13.0f));
        } else {
            this.giftTextPaint.setTextSize(AndroidUtilities.m1036dp(15.0f));
        }
        int iM1036dp2 = i4 - AndroidUtilities.m1036dp(12.0f);
        MessageObject messageObject4 = this.currentMessageObject;
        if (messageObject4 != null && messageObject4.type == 22 && messageObject4.getDialogId() >= 0 && (iCutInFancyHalf = HintView2.cutInFancyHalf(charSequenceReplaceEmoji, this.giftTextPaint)) < iM1036dp2 && iCutInFancyHalf > iM1036dp2 / 5.0f) {
            iM1036dp2 = iCutInFancyHalf;
        }
        TextLayout textLayout = this.giftPremiumText;
        if (charSequenceReplaceEmoji == 0) {
            if (textLayout != null) {
                textLayout.detach();
                this.giftPremiumText = null;
            }
            this.giftPremiumTextCollapsed = r4;
        } else {
            if (textLayout == null) {
                this.giftPremiumText = new TextLayout();
            }
            try {
                charSequenceReplaceEmoji = Emoji.replaceEmoji(charSequenceReplaceEmoji, this.giftTextPaint.getFontMetricsInt(), r4);
            } catch (Exception unused) {
            }
            this.giftPremiumText.setText(charSequenceReplaceEmoji, this.giftTextPaint, iM1036dp2);
            if (z && this.giftPremiumText.layout.getLineCount() > 3) {
                this.giftPremiumTextCollapsed = !this.giftPremiumTextUncollapsed;
                this.giftPremiumTextCollapsedHeight = this.giftPremiumText.layout.getLineBottom(2);
                this.giftPremiumTextMore = new Text(LocaleController.getString(C2797R.string.Gift2CaptionMore), this.giftTextPaint.getTextSize() / AndroidUtilities.density, AndroidUtilities.bold());
                int lineBottom = this.giftPremiumText.layout.getLineBottom(2);
                this.giftPremiumTextMoreY = lineBottom;
                this.giftPremiumTextMoreH = lineBottom - this.giftPremiumText.layout.getLineTop(2);
                this.giftPremiumTextMoreX = (int) this.giftPremiumText.layout.getLineRight(2);
            } else {
                this.giftPremiumTextCollapsed = r4;
                this.giftPremiumTextExpandedAnimated.set(true, true);
                this.giftPremiumTextCollapsedHeight = r4;
            }
            if (this.giftPremiumTextCollapsed) {
                int lineEnd = this.giftPremiumText.layout.getLineEnd(2) - 1;
                TextLayout textLayout2 = this.giftPremiumText;
                CharSequence charSequenceSubSequence = charSequenceReplaceEmoji;
                if (lineEnd >= 0) {
                    charSequenceSubSequence = charSequenceReplaceEmoji.subSequence(r4, lineEnd);
                }
                textLayout2.setText(charSequenceSubSequence, this.giftTextPaint, iM1036dp2);
            }
        }
        if (charSequence5 != null) {
            SpannableStringBuilder spannableStringBuilderValueOf2 = SpannableStringBuilder.valueOf(charSequence5);
            spannableStringBuilderValueOf2.setSpan(new TypefaceSpan(AndroidUtilities.bold()), r4, spannableStringBuilderValueOf2.length(), 33);
            boolean z4 = 1;
            StaticLayout staticLayout = new StaticLayout(spannableStringBuilderValueOf2, (TextPaint) getThemedPaint("paintChatActionText"), i4, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
            this.giftPremiumButtonLayout = staticLayout;
            if (!z2 || this.giftPremiumTextCollapsed) {
                z4 = r4;
            }
            this.buttonClickableAsImage = z4;
            this.giftPremiumButtonWidth = measureLayoutWidth(staticLayout);
        } else {
            this.giftPremiumButtonLayout = null;
            this.buttonClickableAsImage = r4;
            this.giftPremiumButtonWidth = 0.0f;
        }
        if (charSequence6 != null) {
            if (this.giftRibbonPaintEffect == null) {
                this.giftRibbonPaintEffect = new CornerPathEffect(AndroidUtilities.m1036dp(5.0f));
            }
            if (this.giftRibbonPath == null) {
                Path path = new Path();
                this.giftRibbonPath = path;
                GiftSheet.RibbonDrawable.fillRibbonPath(path, 1.35f, r4);
            }
            Text text2 = new Text(charSequence6, i, AndroidUtilities.bold());
            this.giftRibbonText = text2;
            text2.ellipsize(AndroidUtilities.m1036dp(62.0f));
            return;
        }
        this.giftRibbonPath = null;
        this.giftRibbonText = null;
    }

    private float measureLayoutWidth(Layout layout) {
        float f = 0.0f;
        for (int i = 0; i < layout.getLineCount(); i++) {
            float fCeil = (int) Math.ceil(layout.getLineWidth(i));
            if (fCeil > f) {
                f = fCeil;
            }
        }
        return f;
    }

    public boolean showingCancelButton() {
        RadialProgress2 radialProgress2 = this.radialProgress;
        return radialProgress2 != null && radialProgress2.getIcon() == 3;
    }

    public int getCustomDate() {
        return this.customDate;
    }

    /* JADX WARN: Removed duplicated region for block: B:203:0x0635  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x0645  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x06bc  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x0751  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x075f  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x0775  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x0a77  */
    /* JADX WARN: Removed duplicated region for block: B:298:0x0c0e  */
    /* JADX WARN: Removed duplicated region for block: B:301:0x0c15  */
    /* JADX WARN: Removed duplicated region for block: B:304:0x0c24  */
    /* JADX WARN: Removed duplicated region for block: B:307:0x0c31  */
    /* JADX WARN: Removed duplicated region for block: B:308:0x0c45  */
    /* JADX WARN: Removed duplicated region for block: B:311:0x0c79  */
    /* JADX WARN: Removed duplicated region for block: B:346:0x0d77  */
    /* JADX WARN: Removed duplicated region for block: B:350:0x0d82  */
    /* JADX WARN: Removed duplicated region for block: B:356:0x0d9c  */
    /* JADX WARN: Removed duplicated region for block: B:367:0x0e39  */
    /* JADX WARN: Removed duplicated region for block: B:370:0x0e94  */
    /* JADX WARN: Removed duplicated region for block: B:383:0x0ef4  */
    /* JADX WARN: Removed duplicated region for block: B:384:0x0f0a  */
    /* JADX WARN: Removed duplicated region for block: B:387:0x0f31  */
    /* JADX WARN: Removed duplicated region for block: B:388:0x0f36  */
    /* JADX WARN: Removed duplicated region for block: B:393:0x0f42  */
    /* JADX WARN: Removed duplicated region for block: B:400:0x0f63  */
    /* JADX WARN: Removed duplicated region for block: B:401:0x0f65  */
    /* JADX WARN: Removed duplicated region for block: B:404:0x0f6d  */
    /* JADX WARN: Removed duplicated region for block: B:408:0x0f97  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x01ec  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onDraw(android.graphics.Canvas r44) {
        /*
            Method dump skipped, instruction units count: 4079
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Cells.ChatActionCell.onDraw(android.graphics.Canvas):void");
    }

    @Override // android.view.ViewGroup
    public boolean drawChild(Canvas canvas, View view, long j) {
        if (view == this.rippleView) {
            float scale = this.bounce.getScale(0.02f);
            canvas.save();
            canvas.scale(scale, scale, view.getX() + (view.getMeasuredWidth() / 2.0f), view.getY() + (view.getMeasuredHeight() / 2.0f));
            boolean zDrawChild = super.drawChild(canvas, view, j);
            canvas.restore();
            return zDrawChild;
        }
        return super.drawChild(canvas, view, j);
    }

    private void checkLeftRightBounds() {
        this.backgroundLeft = (int) Math.min(this.backgroundLeft, this.rect.left);
        this.backgroundRight = (int) Math.max(this.backgroundRight, this.rect.right);
    }

    public void drawBackground(Canvas canvas, boolean z) {
        Paint paint;
        float f;
        float f2;
        float f3;
        float f4;
        Paint paint2;
        int alpha;
        int alpha2;
        Canvas canvas2;
        float f5;
        int i;
        TextLayout textLayout;
        float f6;
        int i2;
        int iIntValue;
        Paint paint3;
        float f7;
        int i3;
        int i4;
        int iM1036dp;
        float f8;
        int i5;
        if (this.canDrawInParent) {
            if (hasGradientService() && !z) {
                return;
            }
            if (!hasGradientService() && z) {
                return;
            }
        }
        Paint themedPaint = getThemedPaint("paintChatActionBackground");
        Paint themedPaint2 = getThemedPaint("paintChatActionBackgroundDarken");
        this.textPaint = (TextPaint) getThemedPaint("paintChatActionText");
        int i6 = this.overrideBackground;
        boolean z2 = true;
        if (i6 >= 0) {
            int themedColor = getThemedColor(i6);
            if (this.overrideBackgroundPaint == null) {
                Paint paint4 = new Paint(1);
                this.overrideBackgroundPaint = paint4;
                paint4.setColor(themedColor);
                TextPaint textPaint = new TextPaint(1);
                this.overrideTextPaint = textPaint;
                textPaint.setTypeface(AndroidUtilities.bold());
                this.overrideTextPaint.setTextSize(AndroidUtilities.m1036dp(Math.max(16, SharedConfig.fontSize) - 2));
                this.overrideTextPaint.setColor(getThemedColor(this.overrideText));
            }
            themedPaint = this.overrideBackgroundPaint;
            this.textPaint = this.overrideTextPaint;
        }
        float f9 = 8.0f;
        if (this.invalidatePath) {
            this.invalidatePath = false;
            this.backgroundLeft = getWidth();
            this.backgroundRight = 0;
            this.lineWidths.clear();
            StaticLayout staticLayout = this.textLayout;
            int lineCount = staticLayout == null ? 0 : staticLayout.getLineCount();
            int iM1036dp2 = AndroidUtilities.m1036dp(11.0f);
            int iM1036dp3 = AndroidUtilities.m1036dp(8.0f);
            int i7 = 0;
            int i8 = 0;
            while (i7 < lineCount) {
                boolean z3 = z2;
                int iCeil = (int) Math.ceil(this.textLayout.getLineWidth(i7));
                if (i7 == 0 || (i5 = i8 - iCeil) <= 0) {
                    f8 = f9;
                } else {
                    f8 = f9;
                    if (i5 <= (iM1036dp2 * 1.5f) + iM1036dp3) {
                    }
                    this.lineWidths.add(Integer.valueOf(i8));
                    i7++;
                    z2 = z3;
                    f9 = f8;
                }
                i8 = iCeil;
                this.lineWidths.add(Integer.valueOf(i8));
                i7++;
                z2 = z3;
                f9 = f8;
            }
            f = f9;
            f3 = 6.0f;
            for (int i9 = lineCount - 2; i9 >= 0; i9--) {
                int iIntValue2 = this.lineWidths.get(i9).intValue();
                int i10 = i8 - iIntValue2;
                if (i10 <= 0 || i10 > (iM1036dp2 * 1.5f) + iM1036dp3) {
                    i8 = iIntValue2;
                }
                this.lineWidths.set(i9, Integer.valueOf(i8));
            }
            int iM1036dp4 = AndroidUtilities.m1036dp(4.0f);
            int measuredWidth = getMeasuredWidth() / 2;
            int iM1036dp5 = AndroidUtilities.m1036dp(3.0f);
            int iM1036dp6 = AndroidUtilities.m1036dp(6.0f);
            int i11 = iM1036dp2 - iM1036dp5;
            f4 = 2.0f;
            this.lineHeights.clear();
            this.backgroundPath.reset();
            f2 = 4.0f;
            float f10 = measuredWidth;
            this.backgroundPath.moveTo(f10, iM1036dp4);
            int i12 = 0;
            int i13 = 0;
            while (i12 < lineCount) {
                int iIntValue3 = this.lineWidths.get(i12).intValue();
                int i14 = i13;
                int lineBottom = this.textLayout.getLineBottom(i12);
                int i15 = lineCount - 1;
                float f11 = f10;
                if (i12 < i15) {
                    i2 = lineCount;
                    iIntValue = this.lineWidths.get(i12 + 1).intValue();
                } else {
                    i2 = lineCount;
                    iIntValue = 0;
                }
                int iM1036dp7 = lineBottom - i14;
                if (i12 == 0 || iIntValue3 > i8) {
                    iM1036dp7 += AndroidUtilities.m1036dp(3.0f);
                }
                if (i12 == i15 || iIntValue3 > iIntValue) {
                    iM1036dp7 += AndroidUtilities.m1036dp(3.0f);
                }
                int i16 = iM1036dp7;
                float f12 = f11 + (iIntValue3 / 2.0f);
                int i17 = (i12 == i15 || iIntValue3 >= iIntValue || i12 == 0 || iIntValue3 >= i8) ? iM1036dp3 : iM1036dp6;
                if (i12 == 0 || iIntValue3 > i8) {
                    paint3 = themedPaint2;
                    f7 = f12;
                    i3 = iM1036dp3;
                    i4 = iM1036dp6;
                    this.rect.set((f7 - iM1036dp5) - iM1036dp2, iM1036dp4, f7 + i11, (iM1036dp2 * 2) + iM1036dp4);
                    checkLeftRightBounds();
                    this.backgroundPath.arcTo(this.rect, -90.0f, 90.0f);
                } else if (iIntValue3 < i8) {
                    f7 = f12;
                    i3 = iM1036dp3;
                    float f13 = f7 + i11;
                    i4 = iM1036dp6;
                    paint3 = themedPaint2;
                    this.rect.set(f13, iM1036dp4, (i17 * 2) + f13, r1 + iM1036dp4);
                    checkLeftRightBounds();
                    this.backgroundPath.arcTo(this.rect, -90.0f, -90.0f);
                } else {
                    paint3 = themedPaint2;
                    f7 = f12;
                    i3 = iM1036dp3;
                    i4 = iM1036dp6;
                }
                iM1036dp4 += i16;
                if (i12 == i15 || iIntValue3 >= iIntValue) {
                    iM1036dp = i16;
                } else {
                    iM1036dp4 -= AndroidUtilities.m1036dp(3.0f);
                    iM1036dp = i16 - AndroidUtilities.m1036dp(3.0f);
                }
                if (i12 != 0 && iIntValue3 < i8) {
                    iM1036dp4 -= AndroidUtilities.m1036dp(3.0f);
                    iM1036dp -= AndroidUtilities.m1036dp(3.0f);
                }
                this.lineHeights.add(Integer.valueOf(iM1036dp));
                if (i12 == i15 || iIntValue3 > iIntValue) {
                    this.rect.set((f7 - iM1036dp5) - iM1036dp2, iM1036dp4 - (iM1036dp2 * 2), f7 + i11, iM1036dp4);
                    checkLeftRightBounds();
                    this.backgroundPath.arcTo(this.rect, 0.0f, 90.0f);
                } else if (iIntValue3 < iIntValue) {
                    float f14 = f7 + i11;
                    this.rect.set(f14, iM1036dp4 - r3, (i17 * 2) + f14, iM1036dp4);
                    checkLeftRightBounds();
                    this.backgroundPath.arcTo(this.rect, 180.0f, -90.0f);
                }
                i12++;
                i8 = iIntValue3;
                i13 = lineBottom;
                f10 = f11;
                lineCount = i2;
                iM1036dp3 = i3;
                iM1036dp6 = i4;
                themedPaint2 = paint3;
            }
            paint = themedPaint2;
            float f15 = f10;
            int i18 = iM1036dp3;
            int i19 = iM1036dp6;
            int i20 = lineCount - 1;
            int i21 = i20;
            while (i21 >= 0) {
                int iIntValue4 = i21 != 0 ? this.lineWidths.get(i21 - 1).intValue() : 0;
                int iIntValue5 = this.lineWidths.get(i21).intValue();
                int iIntValue6 = i21 != i20 ? this.lineWidths.get(i21 + 1).intValue() : 0;
                this.textLayout.getLineBottom(i21);
                float f16 = measuredWidth - (iIntValue5 / 2);
                int i22 = (i21 == i20 || iIntValue5 >= iIntValue6 || i21 == 0 || iIntValue5 >= iIntValue4) ? i18 : i19;
                if (i21 == i20 || iIntValue5 > iIntValue6) {
                    f6 = f16;
                    this.rect.set(f6 - i11, iM1036dp4 - (iM1036dp2 * 2), f6 + iM1036dp5 + iM1036dp2, iM1036dp4);
                    checkLeftRightBounds();
                    this.backgroundPath.arcTo(this.rect, 90.0f, 90.0f);
                } else if (iIntValue5 < iIntValue6) {
                    float f17 = f16 - i11;
                    f6 = f16;
                    this.rect.set(f17 - (i22 * 2), iM1036dp4 - r14, f17, iM1036dp4);
                    checkLeftRightBounds();
                    this.backgroundPath.arcTo(this.rect, 90.0f, -90.0f);
                } else {
                    f6 = f16;
                }
                iM1036dp4 -= this.lineHeights.get(i21).intValue();
                if (i21 == 0 || iIntValue5 > iIntValue4) {
                    this.rect.set(f6 - i11, iM1036dp4, f6 + iM1036dp5 + iM1036dp2, (iM1036dp2 * 2) + iM1036dp4);
                    checkLeftRightBounds();
                    this.backgroundPath.arcTo(this.rect, 180.0f, 90.0f);
                } else if (iIntValue5 < iIntValue4) {
                    float f18 = f6 - i11;
                    this.rect.set(f18 - (i22 * 2), iM1036dp4, f18, r10 + iM1036dp4);
                    checkLeftRightBounds();
                    this.backgroundPath.arcTo(this.rect, 0.0f, -90.0f);
                }
                i21--;
            }
            this.backgroundPath.close();
            if (isMessageActionSuggestedPostApproval() && !isNewStyleButtonLayout()) {
                this.rect.left = (f15 - (this.textWidth / 2.0f)) - AndroidUtilities.m1036dp(17.0f);
                RectF rectF = this.rect;
                rectF.top = iM1036dp4;
                rectF.right = f15 + (this.textWidth / 2.0f) + AndroidUtilities.m1036dp(17.0f);
                this.rect.bottom = iM1036dp4 + this.textHeight + this.titleHeight + AndroidUtilities.m1036dp(28.0f);
                this.backgroundPath.reset();
                this.backgroundPath.addRoundRect(this.rect, AndroidUtilities.m1036dp(15.0f), AndroidUtilities.m1036dp(15.0f), Path.Direction.CW);
                this.backgroundPath.close();
            }
        } else {
            paint = themedPaint2;
            f = 8.0f;
            f2 = 4.0f;
            f3 = 6.0f;
            f4 = 2.0f;
        }
        if (!this.visiblePartSet) {
            this.backgroundHeight = ((ViewGroup) getParent()).getMeasuredHeight();
        }
        Theme.ResourcesProvider resourcesProvider = this.themeDelegate;
        if (resourcesProvider != null) {
            resourcesProvider.applyServiceShaderMatrix(getMeasuredWidth(), this.backgroundHeight, this.viewTranslationX, this.viewTop + AndroidUtilities.m1036dp(f2));
        } else {
            Theme.applyServiceShaderMatrix(getMeasuredWidth(), this.backgroundHeight, this.viewTranslationX, this.viewTop + AndroidUtilities.m1036dp(f2));
        }
        if (z && (getAlpha() != 1.0f || isFloating())) {
            alpha = themedPaint.getAlpha();
            alpha2 = paint.getAlpha();
            themedPaint.setAlpha((int) (alpha * getAlpha() * (isFloating() ? 0.75f : 1.0f)));
            paint2 = paint;
            paint2.setAlpha((int) (alpha2 * getAlpha() * (isFloating() ? 0.75f : 1.0f)));
        } else {
            paint2 = paint;
            if (isFloating()) {
                alpha = themedPaint.getAlpha();
                alpha2 = paint2.getAlpha();
                themedPaint.setAlpha((int) (alpha * (isFloating() ? 0.75f : 1.0f)));
                paint2.setAlpha((int) (alpha2 * (isFloating() ? 0.75f : 1.0f)));
            } else {
                alpha = -1;
                alpha2 = -1;
            }
        }
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null || !messageObject.isRepostPreview) {
            canvas2 = canvas;
            canvas2.drawPath(this.backgroundPath, themedPaint);
            if (hasGradientService() && paint2.getAlpha() > 0) {
                canvas2.drawPath(this.backgroundPath, paint2);
            }
            f5 = 0.0f;
            if (this.dimAmount > 0.0f) {
                int alpha3 = this.dimPaint.getAlpha();
                if (z) {
                    this.dimPaint.setAlpha((int) (alpha3 * getAlpha()));
                }
                canvas2.drawPath(this.backgroundPath, this.dimPaint);
                this.dimPaint.setAlpha(alpha3);
            }
        } else {
            canvas2 = canvas;
            f5 = 0.0f;
        }
        MessageObject messageObject2 = this.currentMessageObject;
        if (this.starGiftLayout.has()) {
            float width = this.starGiftLayout.getWidth() + AndroidUtilities.m1036dp(f);
            float width2 = (getWidth() - width) / f4;
            float fM1036dp = this.starGiftLayout.repost ? f5 : this.textY + this.textHeight + AndroidUtilities.m1036dp(12.0f);
            RectF rectF2 = AndroidUtilities.rectTmp;
            rectF2.set(width2, fM1036dp, width + width2, this.starGiftLayout.getHeight() + fM1036dp + AndroidUtilities.m1036dp(f));
            if (this.backgroundRect == null) {
                this.backgroundRect = new RectF();
            }
            this.backgroundRect.set(rectF2);
            canvas2.drawRoundRect(this.backgroundRect, AndroidUtilities.m1036dp(16.0f), AndroidUtilities.m1036dp(16.0f), themedPaint);
            if (hasGradientService()) {
                canvas2.drawRoundRect(this.backgroundRect, AndroidUtilities.m1036dp(16.0f), AndroidUtilities.m1036dp(16.0f), paint2);
            }
        } else {
            SuggestBirthdayActionLayout suggestBirthdayActionLayout = this.birthdayLayout;
            if (suggestBirthdayActionLayout != null) {
                float fWidth = suggestBirthdayActionLayout.width();
                float fHeight = this.birthdayLayout.height();
                float width3 = (getWidth() - fWidth) / f4;
                if (this.backgroundRect == null) {
                    this.backgroundRect = new RectF();
                }
                this.backgroundRect.set(width3, AndroidUtilities.m1036dp(f2), fWidth + width3, AndroidUtilities.m1036dp(f2) + fHeight);
                canvas2.drawRoundRect(this.backgroundRect, AndroidUtilities.m1036dp(16.0f), AndroidUtilities.m1036dp(16.0f), themedPaint);
                if (hasGradientService()) {
                    canvas2.drawRoundRect(this.backgroundRect, AndroidUtilities.m1036dp(16.0f), AndroidUtilities.m1036dp(16.0f), paint2);
                }
            } else if (isButtonLayout(messageObject2)) {
                float width4 = (getWidth() - this.giftRectSize) / f4;
                float f19 = this.textY + this.textHeight;
                if (isNewStyleButtonLayout()) {
                    float fM1036dp2 = f19 + AndroidUtilities.m1036dp(f2);
                    AndroidUtilities.rectTmp.set(width4, fM1036dp2, this.giftRectSize + width4, this.backgroundRectHeight + fM1036dp2);
                } else {
                    float fM1036dp3 = f19 + AndroidUtilities.m1036dp(12.0f);
                    RectF rectF3 = AndroidUtilities.rectTmp;
                    int i23 = this.giftRectSize;
                    rectF3.set(width4, fM1036dp3, i23 + width4, i23 + fM1036dp3 + this.giftPremiumAdditionalHeight);
                }
                if (messageObject2 != null && messageObject2.type == 18 && !this.giftPremiumTextCollapsed && (textLayout = this.giftPremiumText) != null && this.giftPremiumTextCollapsedHeight > 0) {
                    AndroidUtilities.rectTmp.bottom -= (textLayout.layout.getHeight() - this.giftPremiumTextCollapsedHeight) * (1.0f - this.giftPremiumTextExpandedAnimated.get());
                }
                if (this.backgroundRect == null) {
                    this.backgroundRect = new RectF();
                }
                this.backgroundRect.set(AndroidUtilities.rectTmp);
                if (messageObject2 != null && (((i = messageObject2.type) == 33 || i == 35) && this.botInlineButtons != null)) {
                    Arrays.fill(this.radii, AndroidUtilities.m1036dp(16.0f));
                    float[] fArr = this.radii;
                    float fM1036dp4 = AndroidUtilities.m1036dp(f3);
                    fArr[7] = fM1036dp4;
                    fArr[6] = fM1036dp4;
                    fArr[5] = fM1036dp4;
                    fArr[4] = fM1036dp4;
                    this.backgroundPath2.rewind();
                    this.backgroundPath2.addRoundRect(this.backgroundRect, this.radii, Path.Direction.CW);
                    canvas2.drawPath(this.backgroundPath2, themedPaint);
                    if (hasGradientService()) {
                        canvas2.drawPath(this.backgroundPath2, paint2);
                    }
                } else {
                    canvas2.drawRoundRect(this.backgroundRect, AndroidUtilities.m1036dp(16.0f), AndroidUtilities.m1036dp(16.0f), themedPaint);
                    if (hasGradientService()) {
                        canvas2.drawRoundRect(this.backgroundRect, AndroidUtilities.m1036dp(16.0f), AndroidUtilities.m1036dp(16.0f), paint2);
                    }
                }
            }
        }
        if (alpha >= 0) {
            themedPaint.setAlpha(alpha);
            paint2.setAlpha(alpha2);
        }
    }

    private void drawBotButtons(Canvas canvas, ArrayList<BotButton> arrayList) {
        float f;
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        Theme.ResourcesProvider resourcesProvider = this.themeDelegate;
        float f2 = 4.0f;
        if (resourcesProvider != null) {
            resourcesProvider.applyServiceShaderMatrix(getMeasuredWidth(), this.backgroundHeight, this.viewTranslationX, this.viewTop + AndroidUtilities.m1036dp(4.0f));
        } else {
            Theme.applyServiceShaderMatrix(getMeasuredWidth(), this.backgroundHeight, this.viewTranslationX, this.viewTop + AndroidUtilities.m1036dp(4.0f));
        }
        float f3 = 2.0f;
        float width = (getWidth() - this.giftRectSize) / 2.0f;
        float fM1036dp = this.textY + this.textHeight + AndroidUtilities.m1036dp(4.0f) + this.backgroundRectHeight + AndroidUtilities.m1036dp(4.0f);
        float fM1036dp2 = (this.giftRectSize - AndroidUtilities.m1036dp(4.0f)) / 2.0f;
        int i = 0;
        while (i < arrayList.size()) {
            BotButton botButton = arrayList.get(i);
            float pressScale = botButton.getPressScale();
            float fM1036dp3 = ((AndroidUtilities.m1036dp(f2) + fM1036dp2) * i) + width;
            float f4 = fM1036dp3 + fM1036dp2;
            this.rect.set(fM1036dp3, fM1036dp, f4, botButton.height + fM1036dp);
            canvas.save();
            if (pressScale != 1.0f) {
                f = f2;
                canvas.scale(pressScale, pressScale, this.rect.centerX(), this.rect.centerY());
            } else {
                f = f2;
            }
            Arrays.fill(this.botButtonRadii, AndroidUtilities.m1036dp(Math.min(6.75f, SharedConfig.bubbleRadius)));
            if (botButton.hasPositionFlag(9)) {
                float[] fArr = this.botButtonRadii;
                float fM1036dp4 = AndroidUtilities.m1036dp(SharedConfig.bubbleRadius);
                fArr[7] = fM1036dp4;
                fArr[6] = fM1036dp4;
            }
            if (botButton.hasPositionFlag(10)) {
                float[] fArr2 = this.botButtonRadii;
                float fM1036dp5 = AndroidUtilities.m1036dp(SharedConfig.bubbleRadius);
                fArr2[5] = fM1036dp5;
                fArr2[4] = fM1036dp5;
            }
            this.botButtonPath.rewind();
            float f5 = f3;
            this.botButtonPath.addRoundRect(this.rect, this.botButtonRadii, Path.Direction.CW);
            canvas.drawPath(this.botButtonPath, getThemedPaint("paintChatActionBackground"));
            if (hasGradientService()) {
                canvas.drawPath(this.botButtonPath, Theme.chat_actionBackgroundGradientDarkenPaint);
            }
            canvas.save();
            canvas.clipPath(this.botButtonPath);
            Drawable drawable = botButton.selectorDrawable;
            if (drawable != null) {
                int i2 = (int) fM1036dp;
                drawable.setBounds((int) fM1036dp3, i2, (int) f4, botButton.height + i2);
                botButton.selectorDrawable.setAlpha(255);
                botButton.selectorDrawable.draw(canvas);
            }
            canvas.restore();
            canvas.save();
            float fM1036dp6 = botButton.iconDrawable != null ? AndroidUtilities.m1036dp(26.0f) : 0;
            float width2 = fM1036dp3 + (((fM1036dp2 - (botButton.title.getWidth() + (botButton.iconDrawable != null ? AndroidUtilities.m1036dp(f) : 0))) - fM1036dp6) / f5);
            Drawable drawable2 = botButton.iconDrawable;
            if (drawable2 != null) {
                int i3 = (int) width2;
                drawable2.setBounds(i3, (int) (((botButton.height - AndroidUtilities.m1036dp(24.0f)) / f5) + fM1036dp), i3 + AndroidUtilities.m1036dp(24.0f), ((int) (((botButton.height - AndroidUtilities.m1036dp(24.0f)) / f5) + fM1036dp)) + AndroidUtilities.m1036dp(24.0f));
                botButton.iconDrawable.setAlpha(botButton.isLocked ? 128 : 255);
                botButton.iconDrawable.draw(canvas);
                width2 += fM1036dp6;
            }
            botButton.title.ellipsize(Math.max(1, (((int) fM1036dp2) - AndroidUtilities.m1036dp(15.0f)) - r4));
            botButton.title.draw(canvas, width2, (AndroidUtilities.m1036dp(40.0f) / f5) + fM1036dp, botButton.isLocked ? 0.5f : 1.0f);
            canvas.restore();
            canvas.restore();
            i++;
            f2 = f;
            f3 = f5;
        }
    }

    private boolean checkBotButtonMotionEvent(MotionEvent motionEvent) {
        int i;
        BotInlineKeyboard.ButtonCustom buttonCustom;
        if (this.botButtons.isEmpty()) {
            return false;
        }
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        float width = (getWidth() - this.giftRectSize) / 2.0f;
        float fM1036dp = this.textY + this.textHeight + AndroidUtilities.m1036dp(4.0f) + this.backgroundRectHeight + AndroidUtilities.m1036dp(4.0f);
        float fM1036dp2 = (this.giftRectSize - AndroidUtilities.m1036dp(4.0f)) / 2.0f;
        if (motionEvent.getAction() == 0) {
            this.pressedBotButton = -1;
            for (int i2 = 0; i2 < this.botButtons.size(); i2++) {
                BotButton botButton = this.botButtons.get(i2);
                float fM1036dp3 = ((AndroidUtilities.m1036dp(4.0f) + fM1036dp2) * i2) + width;
                RectF rectF = AndroidUtilities.rectTmp;
                rectF.set(fM1036dp3, fM1036dp, fM1036dp3 + fM1036dp2, botButton.height + fM1036dp);
                float f = x;
                float f2 = y;
                if (rectF.contains(f, f2)) {
                    this.pressedBotButton = i2;
                    invalidateOutbounds();
                    if (botButton.selectorDrawable == null) {
                        Drawable drawableCreateRadSelectorDrawable = Theme.createRadSelectorDrawable(getThemedColor(Theme.key_chat_serviceBackgroundSelector), 6, 6);
                        botButton.selectorDrawable = drawableCreateRadSelectorDrawable;
                        drawableCreateRadSelectorDrawable.setCallback(this);
                    }
                    botButton.selectorDrawable.setHotspot(f, f2);
                    botButton.selectorDrawable.setState(this.pressedState);
                    botButton.setPressed(!botButton.isLocked);
                    return true;
                }
            }
        } else if (motionEvent.getAction() == 1) {
            if (this.pressedBotButton != -1) {
                playSoundEffect(0);
                BotButton botButton2 = this.botButtons.get(this.pressedBotButton);
                Drawable drawable = botButton2.selectorDrawable;
                if (drawable != null) {
                    drawable.setState(StateSet.NOTHING);
                }
                botButton2.setPressed(false);
                if (this.delegate != null && !botButton2.isLocked && (buttonCustom = botButton2.buttonCustom) != null) {
                    didPressCustomBotButton(buttonCustom);
                }
                this.pressedBotButton = -1;
                invalidateOutbounds();
                return false;
            }
        } else if (motionEvent.getAction() == 3 && (i = this.pressedBotButton) != -1) {
            BotButton botButton3 = this.botButtons.get(i);
            Drawable drawable2 = botButton3.selectorDrawable;
            if (drawable2 != null) {
                drawable2.setState(StateSet.NOTHING);
            }
            botButton3.setPressed(false);
            this.pressedBotButton = -1;
            invalidateOutbounds();
        }
        return false;
    }

    private void didPressCustomBotButton(BotInlineKeyboard.ButtonCustom buttonCustom) {
        MessageObject messageObject;
        TLRPC.Message message;
        MessageObject messageObject2;
        TLRPC.Message message2;
        TLRPC.Message message3;
        if (getMessageObject() == null) {
            return;
        }
        int i = buttonCustom.f1135id;
        if (i == 5) {
            ChatActionCellDelegate chatActionCellDelegate = this.delegate;
            final BaseFragment baseFragment = chatActionCellDelegate != null ? chatActionCellDelegate.getBaseFragment() : null;
            if (baseFragment == null || this.currentMessageObject == null) {
                return;
            }
            AlertsCreator.showSimpleConfirmAlert(baseFragment, LocaleController.getString(C2797R.string.GiftOfferRejectConfirmTitle), AndroidUtilities.replaceTags(LocaleController.formatString(C2797R.string.GiftOfferRejectConfirmText, DialogObject.getShortName(this.currentMessageObject.getDialogId()))), LocaleController.getString(C2797R.string.GiftOfferRejectConfirmConfirm), true, new Runnable() { // from class: org.telegram.ui.Cells.ChatActionCell$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$didPressCustomBotButton$8(baseFragment);
                }
            });
            return;
        }
        if (i == 6) {
            MessageObject messageObject3 = this.currentMessageObject;
            if (messageObject3 == null || (message3 = messageObject3.messageOwner) == null) {
                return;
            }
            TLRPC.MessageAction messageAction = message3.action;
            if (messageAction instanceof TLRPC.TL_messageActionStarGiftPurchaseOffer) {
                GiftOfferSheet.openOfferAcceptAlert(LaunchActivity.getLastFragment(), getContext(), this.themeDelegate, this.currentAccount, this.currentMessageObject.getDialogId(), this.currentMessageObject.getId(), (TLRPC.TL_messageActionStarGiftPurchaseOffer) messageAction);
                return;
            }
            return;
        }
        if (i == 7) {
            ChatActionCellDelegate chatActionCellDelegate2 = this.delegate;
            BaseFragment baseFragment2 = chatActionCellDelegate2 != null ? chatActionCellDelegate2.getBaseFragment() : null;
            if (baseFragment2 == null || (messageObject2 = this.currentMessageObject) == null || (message2 = messageObject2.messageOwner) == null) {
                return;
            }
            TLRPC.MessageAction messageAction2 = message2.action;
            if (messageAction2 instanceof TLRPC.TL_messageActionNoForwardsRequest) {
                final TLRPC.TL_messageActionNoForwardsRequest tL_messageActionNoForwardsRequest = (TLRPC.TL_messageActionNoForwardsRequest) messageAction2;
                AlertsCreator.showSimpleConfirmAlert(baseFragment2, LocaleController.getString(tL_messageActionNoForwardsRequest.prev_value ? C2797R.string.SharingOfferDisableCancelTitle : C2797R.string.SharingOfferEnableCancelTitle), LocaleController.getString(tL_messageActionNoForwardsRequest.prev_value ? C2797R.string.SharingOfferDisableCancelText : C2797R.string.SharingOfferEnableCancelText), LocaleController.getString(C2797R.string.SharingOfferCancelYes), false, new Runnable() { // from class: org.telegram.ui.Cells.ChatActionCell$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$didPressCustomBotButton$9(tL_messageActionNoForwardsRequest);
                    }
                });
                return;
            }
            return;
        }
        if (i == 8) {
            ChatActionCellDelegate chatActionCellDelegate3 = this.delegate;
            BaseFragment baseFragment3 = chatActionCellDelegate3 != null ? chatActionCellDelegate3.getBaseFragment() : null;
            if (baseFragment3 == null || (messageObject = this.currentMessageObject) == null || (message = messageObject.messageOwner) == null) {
                return;
            }
            TLRPC.MessageAction messageAction3 = message.action;
            if (messageAction3 instanceof TLRPC.TL_messageActionNoForwardsRequest) {
                final TLRPC.TL_messageActionNoForwardsRequest tL_messageActionNoForwardsRequest2 = (TLRPC.TL_messageActionNoForwardsRequest) messageAction3;
                AlertsCreator.showSimpleConfirmAlert(baseFragment3, LocaleController.getString(tL_messageActionNoForwardsRequest2.new_value ? C2797R.string.SharingOfferDisableCancelTitle : C2797R.string.SharingOfferEnableCancelTitle), LocaleController.getString(tL_messageActionNoForwardsRequest2.new_value ? C2797R.string.SharingOfferDisableConfirmText : C2797R.string.SharingOfferEnableConfirmText), LocaleController.getString(C2797R.string.SharingOfferCancelYes), false, new Runnable() { // from class: org.telegram.ui.Cells.ChatActionCell$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$didPressCustomBotButton$10(tL_messageActionNoForwardsRequest2);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didPressCustomBotButton$8(final BaseFragment baseFragment) {
        TL_payments.TL_resolveStarGiftOffer tL_resolveStarGiftOffer = new TL_payments.TL_resolveStarGiftOffer();
        tL_resolveStarGiftOffer.offer_msg_id = getMessageObject().getId();
        tL_resolveStarGiftOffer.decline = true;
        ConnectionsManager.getInstance(this.currentAccount).sendRequestTyped(tL_resolveStarGiftOffer, new Utilities.Callback2() { // from class: org.telegram.ui.Cells.ChatActionCell$$ExternalSyntheticLambda14
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                this.f$0.lambda$didPressCustomBotButton$7(baseFragment, (TLRPC.Updates) obj, (TLRPC.TL_error) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didPressCustomBotButton$7(final BaseFragment baseFragment, TLRPC.Updates updates, final TLRPC.TL_error tL_error) {
        if (updates != null) {
            MessagesController.getInstance(this.currentAccount).processUpdates(updates, false);
        }
        if (tL_error != null) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Cells.ChatActionCell$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    BulletinFactory.m1143of(baseFragment).showForError(tL_error);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didPressCustomBotButton$9(TLRPC.TL_messageActionNoForwardsRequest tL_messageActionNoForwardsRequest) {
        MessagesController.getInstance(this.currentAccount).toggleChatNoForwards(this.currentMessageObject.getDialogId(), this.currentMessageObject.getId(), tL_messageActionNoForwardsRequest.prev_value, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didPressCustomBotButton$10(TLRPC.TL_messageActionNoForwardsRequest tL_messageActionNoForwardsRequest) {
        MessagesController.getInstance(this.currentAccount).toggleChatNoForwards(this.currentMessageObject.getDialogId(), this.currentMessageObject.getId(), tL_messageActionNoForwardsRequest.new_value, null);
    }

    public void drawReactions(Canvas canvas, boolean z, Integer num) {
        if (this.canDrawInParent) {
            if (hasGradientService() && !z) {
                return;
            }
            if (!hasGradientService() && z) {
                return;
            }
        }
        drawReactionsLayout(canvas, z, num);
    }

    public void drawReactionsLayout(Canvas canvas, boolean z, Integer num) {
        Canvas canvas2;
        float alpha = z ? getAlpha() : 1.0f;
        if (alpha <= 0.0f) {
            return;
        }
        Theme.ResourcesProvider resourcesProvider = this.themeDelegate;
        if (resourcesProvider != null) {
            resourcesProvider.applyServiceShaderMatrix(getMeasuredWidth(), this.backgroundHeight, this.viewTranslationX, this.viewTop + AndroidUtilities.m1036dp(4.0f));
        } else {
            Theme.applyServiceShaderMatrix(getMeasuredWidth(), this.backgroundHeight, this.viewTranslationX, this.viewTop + AndroidUtilities.m1036dp(4.0f));
        }
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null || !messageObject.shouldDrawReactions()) {
            return;
        }
        ReactionsLayoutInBubble reactionsLayoutInBubble = this.reactionsLayoutInBubble;
        if (!reactionsLayoutInBubble.isSmall || (this.transitionParams.animateChange && reactionsLayoutInBubble.animateHeight)) {
            reactionsLayoutInBubble.drawServiceShaderBackground = 1.0f;
            if (alpha < 1.0f) {
                canvas2 = canvas;
                canvas2.saveLayerAlpha(0.0f, 0.0f, getWidth(), getHeight(), (int) (alpha * 255.0f), 31);
            } else {
                canvas2 = canvas;
            }
            ReactionsLayoutInBubble reactionsLayoutInBubble2 = this.reactionsLayoutInBubble;
            TransitionParams transitionParams = this.transitionParams;
            reactionsLayoutInBubble2.draw(canvas2, transitionParams.animateChange ? transitionParams.animateChangeProgress : 1.0f, num);
            if (alpha < 1.0f) {
                canvas2.restore();
            }
        }
    }

    public void drawReactionsLayoutOverlay(Canvas canvas, boolean z) {
        Canvas canvas2;
        float alpha = z ? getAlpha() : 1.0f;
        Theme.ResourcesProvider resourcesProvider = this.themeDelegate;
        if (resourcesProvider != null) {
            resourcesProvider.applyServiceShaderMatrix(getMeasuredWidth(), this.backgroundHeight, this.viewTranslationX, this.viewTop + AndroidUtilities.m1036dp(4.0f));
        } else {
            Theme.applyServiceShaderMatrix(getMeasuredWidth(), this.backgroundHeight, this.viewTranslationX, this.viewTop + AndroidUtilities.m1036dp(4.0f));
        }
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null || !messageObject.shouldDrawReactions()) {
            return;
        }
        ReactionsLayoutInBubble reactionsLayoutInBubble = this.reactionsLayoutInBubble;
        if (!reactionsLayoutInBubble.isSmall || (this.transitionParams.animateChange && reactionsLayoutInBubble.animateHeight)) {
            reactionsLayoutInBubble.drawServiceShaderBackground = 1.0f;
            if (alpha < 1.0f) {
                canvas2 = canvas;
                canvas2.saveLayerAlpha(0.0f, 0.0f, getWidth(), getHeight(), (int) (alpha * 255.0f), 31);
            } else {
                canvas2 = canvas;
            }
            ReactionsLayoutInBubble reactionsLayoutInBubble2 = this.reactionsLayoutInBubble;
            TransitionParams transitionParams = this.transitionParams;
            reactionsLayoutInBubble2.drawOverlay(canvas2, transitionParams.animateChange ? transitionParams.animateChangeProgress : 1.0f);
            if (alpha < 1.0f) {
                canvas2.restore();
            }
        }
    }

    @Override // org.telegram.p035ui.Cells.BaseCell
    public int getBoundsLeft() {
        if (this.starGiftLayout.has()) {
            int width = ((int) (getWidth() - (this.starGiftLayout.getWidth() + AndroidUtilities.m1036dp(8.0f)))) / 2;
            return this.starGiftLayout.repost ? width : Math.min(this.backgroundLeft, width);
        }
        if (isButtonLayout(this.currentMessageObject)) {
            return (this.sideMenuWidth / 2) + ((getWidth() - this.giftRectSize) / 2);
        }
        int iMin = this.backgroundLeft;
        ImageReceiver imageReceiver = this.imageReceiver;
        if (imageReceiver != null && imageReceiver.getVisible()) {
            iMin = Math.min((int) this.imageReceiver.getImageX(), iMin);
        }
        return (this.sideMenuWidth / 2) + iMin;
    }

    @Override // org.telegram.p035ui.Cells.BaseCell
    public int getBoundsRight() {
        if (this.starGiftLayout.has()) {
            int width = ((int) (getWidth() + (this.starGiftLayout.getWidth() + AndroidUtilities.m1036dp(8.0f)))) / 2;
            return this.starGiftLayout.repost ? width : Math.max(this.backgroundRight, width);
        }
        if (isButtonLayout(this.currentMessageObject)) {
            return (this.sideMenuWidth / 2) + ((getWidth() + this.giftRectSize) / 2);
        }
        int iMax = this.backgroundRight;
        ImageReceiver imageReceiver = this.imageReceiver;
        if (imageReceiver != null && imageReceiver.getVisible()) {
            iMax = Math.max((int) this.imageReceiver.getImageX2(), iMax);
        }
        return (this.sideMenuWidth / 2) + iMax;
    }

    public boolean hasGradientService() {
        if (this.overrideBackgroundPaint != null) {
            return false;
        }
        Theme.ResourcesProvider resourcesProvider = this.themeDelegate;
        return resourcesProvider != null ? resourcesProvider.hasGradientService() : Theme.hasGradientService();
    }

    @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
    public void onSuccessDownload(String str) {
        TLRPC.PhotoSize photoSize;
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null || messageObject.type != 11) {
            return;
        }
        int size = messageObject.photoThumbs.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                photoSize = null;
                break;
            }
            photoSize = messageObject.photoThumbs.get(i);
            if (photoSize instanceof TLRPC.TL_photoStrippedSize) {
                break;
            } else {
                i++;
            }
        }
        this.imageReceiver.setImage(this.currentVideoLocation, ImageLoader.AUTOPLAY_FILTER, ImageLocation.getForObject(photoSize, messageObject.photoThumbsObject), "50_50_b", this.avatarDrawable, 0L, null, messageObject, 1);
        DownloadController.getInstance(this.currentAccount).removeLoadingFileObserver(this);
    }

    @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
    public int getObserverTag() {
        return this.TAG;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        MessageObject messageObject = this.currentMessageObject;
        if (TextUtils.isEmpty(this.customText) && messageObject == null) {
            return;
        }
        if (this.accessibilityText == null) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(!TextUtils.isEmpty(this.customText) ? this.customText : messageObject.messageText);
            for (final CharacterStyle characterStyle : (CharacterStyle[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), ClickableSpan.class)) {
                int spanStart = spannableStringBuilder.getSpanStart(characterStyle);
                int spanEnd = spannableStringBuilder.getSpanEnd(characterStyle);
                spannableStringBuilder.removeSpan(characterStyle);
                spannableStringBuilder.setSpan(new ClickableSpan() { // from class: org.telegram.ui.Cells.ChatActionCell.1
                    @Override // android.text.style.ClickableSpan
                    public void onClick(View view) {
                        if (ChatActionCell.this.delegate != null) {
                            ChatActionCell.this.openLink(characterStyle);
                        }
                    }
                }, spanStart, spanEnd, 33);
            }
            this.accessibilityText = spannableStringBuilder;
        }
        accessibilityNodeInfo.setText(this.accessibilityText);
        accessibilityNodeInfo.setEnabled(true);
    }

    public void setInvalidateColors(boolean z) {
        if (this.invalidateColors == z) {
            return;
        }
        this.invalidateColors = z;
        invalidate();
    }

    private int getThemedColor(int i) {
        return Theme.getColor(i, this.themeDelegate);
    }

    public Paint getThemedPaint(String str) {
        Theme.ResourcesProvider resourcesProvider = this.themeDelegate;
        Paint paint = resourcesProvider != null ? resourcesProvider.getPaint(str) : null;
        return paint != null ? paint : Theme.getThemePaint(str);
    }

    public void drawOutboundsContent(Canvas canvas) {
        canvas.save();
        canvas.translate(this.sideMenuWidth / 2.0f, getPaddingTop());
        canvas.save();
        canvas.translate(this.textXLeft, this.textY);
        StaticLayout staticLayout = this.textLayout;
        AnimatedEmojiSpan.drawAnimatedEmojis(canvas, staticLayout, this.animatedEmojiStack, 0.0f, this.spoilers, 0.0f, 0.0f, 0.0f, 1.0f, staticLayout != null ? getAdaptiveEmojiColorFilter(staticLayout.getPaint().getColor()) : null);
        canvas.restore();
        if (this.starGiftLayout.has()) {
            canvas.save();
            canvas.translate((getWidth() - this.starGiftLayout.getWidth()) / 2.0f, this.starGiftLayout.repost ? AndroidUtilities.m1036dp(4.0f) : this.textY + this.textHeight + AndroidUtilities.m1036dp(16.0f));
            this.starGiftLayout.drawOutbounds(canvas);
            canvas.restore();
        }
        canvas.restore();
        if (this.topicSeparator != null) {
            float alpha = getAlpha();
            Theme.ResourcesProvider resourcesProvider = this.themeDelegate;
            if (resourcesProvider != null) {
                resourcesProvider.applyServiceShaderMatrix(getMeasuredWidth(), this.backgroundHeight, this.viewTranslationX, this.viewTop + 0.0f);
            } else {
                Theme.applyServiceShaderMatrix(getMeasuredWidth(), this.backgroundHeight, this.viewTranslationX, this.viewTop + 0.0f);
            }
            this.topicSeparator.draw(canvas, getWidth(), this.sideMenuWidth, 0.0f, 1.0f, alpha, this.showTopicSeparator);
        }
        drawBotButtons(canvas, this.botButtons);
    }

    private boolean isButtonLayout(MessageObject messageObject) {
        if (messageObject == null) {
            return false;
        }
        int i = messageObject.type;
        return i == 30 || i == 18 || i == 25 || isNewStyleButtonLayout();
    }

    private boolean isGiftChannel(MessageObject messageObject) {
        return messageObject != null && messageObject.type == 25;
    }

    public void setInvalidatesParent(boolean z) {
        this.invalidatesParent = z;
    }

    public void setInvalidateListener(Runnable runnable) {
        this.invalidateListener = runnable;
    }

    @Override // org.telegram.p035ui.Cells.BaseCell, android.view.View
    public void invalidate() {
        super.invalidate();
        View view = this.invalidateWithParent;
        if (view != null) {
            view.invalidate();
        }
        Runnable runnable = this.invalidateListener;
        if (runnable != null) {
            runnable.run();
        }
        if (!this.invalidatesParent || getParent() == null) {
            return;
        }
        View view2 = (View) getParent();
        if (view2.getParent() != null) {
            view2.invalidate();
            ((View) view2.getParent()).invalidate();
        }
    }

    public void invalidateOutbounds() {
        ChatActionCellDelegate chatActionCellDelegate = this.delegate;
        if (chatActionCellDelegate == null || !chatActionCellDelegate.canDrawOutboundsContent()) {
            if (getParent() instanceof View) {
                ((View) getParent()).invalidate();
                return;
            }
            return;
        }
        super.invalidate();
    }

    @Override // android.view.View
    public void invalidate(Rect rect) {
        super.invalidate(rect);
        View view = this.invalidateWithParent;
        if (view != null) {
            view.invalidate();
        }
        if (!this.invalidatesParent || getParent() == null) {
            return;
        }
        View view2 = (View) getParent();
        if (view2.getParent() != null) {
            view2.invalidate();
            ((View) view2.getParent()).invalidate();
        }
    }

    @Override // android.view.View
    public void invalidate(int i, int i2, int i3, int i4) {
        super.invalidate(i, i2, i3, i4);
        View view = this.invalidateWithParent;
        if (view != null) {
            view.invalidate();
        }
        if (!this.invalidatesParent || getParent() == null) {
            return;
        }
        View view2 = (View) getParent();
        if (view2.getParent() != null) {
            view2.invalidate();
            ((View) view2.getParent()).invalidate();
        }
    }

    @Override // android.view.View
    public boolean verifyDrawable(Drawable drawable) {
        return drawable == this.wallpaperPreviewDrawable || super.verifyDrawable(drawable);
    }

    private ColorFilter getAdaptiveEmojiColorFilter(int i) {
        if (i != this.adaptiveEmojiColor || this.adaptiveEmojiColorFilter == null) {
            this.adaptiveEmojiColor = i;
            this.adaptiveEmojiColorFilter = new PorterDuffColorFilter(i, PorterDuff.Mode.SRC_IN);
        }
        return this.adaptiveEmojiColorFilter;
    }

    public ReactionsLayoutInBubble.ReactionButton getReactionButton(ReactionsLayoutInBubble.VisibleReaction visibleReaction) {
        return this.reactionsLayoutInBubble.getReactionButton(visibleReaction);
    }

    public class TransitionParams {
        public boolean animateChange;
        public float animateChangeProgress = 1.0f;
        public boolean wasDraw;

        public boolean supportChangeAnimation() {
            return true;
        }

        public TransitionParams() {
        }

        public void recordDrawingState() {
            this.wasDraw = true;
            ChatActionCell.this.reactionsLayoutInBubble.recordDrawingState();
        }

        public boolean animateChange() {
            if (this.wasDraw) {
                return ChatActionCell.this.reactionsLayoutInBubble.animateChange();
            }
            return false;
        }

        public void onDetach() {
            this.wasDraw = false;
        }

        public void resetAnimation() {
            this.animateChange = false;
            this.animateChangeProgress = 1.0f;
        }
    }

    public TransitionParams getTransitionParams() {
        return this.transitionParams;
    }

    public void setScrimReaction(Integer num) {
        this.reactionsLayoutInBubble.setScrimReaction(num);
    }

    public void drawScrimReaction(Canvas canvas, Integer num, float f, boolean z) {
        if (this.reactionsLayoutInBubble.isSmall) {
            return;
        }
        Theme.ResourcesProvider resourcesProvider = this.themeDelegate;
        if (resourcesProvider != null) {
            resourcesProvider.applyServiceShaderMatrix(getMeasuredWidth(), this.backgroundHeight, this.viewTranslationX, this.viewTop + AndroidUtilities.m1036dp(4.0f));
        } else {
            Theme.applyServiceShaderMatrix(getMeasuredWidth(), this.backgroundHeight, this.viewTranslationX, this.viewTop + AndroidUtilities.m1036dp(4.0f));
        }
        this.reactionsLayoutInBubble.setScrimProgress(f, z);
        this.reactionsLayoutInBubble.draw(canvas, this.transitionParams.animateChangeProgress, num);
    }

    public void drawScrimReactionPreview(View view, Canvas canvas, int i, Integer num, float f) {
        if (this.reactionsLayoutInBubble.isSmall) {
            return;
        }
        Theme.ResourcesProvider resourcesProvider = this.themeDelegate;
        if (resourcesProvider != null) {
            resourcesProvider.applyServiceShaderMatrix(getMeasuredWidth(), this.backgroundHeight, this.viewTranslationX, this.viewTop + AndroidUtilities.m1036dp(4.0f));
        } else {
            Theme.applyServiceShaderMatrix(getMeasuredWidth(), this.backgroundHeight, this.viewTranslationX, this.viewTop + AndroidUtilities.m1036dp(4.0f));
        }
        this.reactionsLayoutInBubble.setScrimProgress(f);
        this.reactionsLayoutInBubble.drawPreview(view, canvas, i, num);
    }

    public boolean checkUnreadReactions(float f, int i) {
        if (!this.reactionsLayoutInBubble.hasUnreadReactions) {
            return false;
        }
        float y = getY();
        float f2 = y + r3.f1657y;
        return f2 > f && (f2 + ((float) this.reactionsLayoutInBubble.height)) - ((float) AndroidUtilities.m1036dp(16.0f)) < ((float) i);
    }

    public void markReactionsAsRead() {
        this.reactionsLayoutInBubble.hasUnreadReactions = false;
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null) {
            return;
        }
        messageObject.markReactionsAsRead();
    }
}
